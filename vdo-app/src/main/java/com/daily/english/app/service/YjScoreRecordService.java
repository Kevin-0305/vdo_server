package com.daily.english.app.service;

import com.daily.english.app.domain.CmsUser;
import com.daily.english.app.domain.YjScoreRecord;
import com.daily.english.app.domain.YjUserBmInfo;
import com.daily.english.app.dto.YjScoreRecordDto;
import com.daily.english.app.mapper.YjScoreRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

@Slf4j
@Service
public class YjScoreRecordService {

    @Autowired
    private YjScoreRecordMapper scoreRecordMapper;
    @Autowired
    private YjUserBmInfoService userBmInfoService;
    @Autowired
    private CmsUserService userService;

    public YjScoreRecord selectScoreRecordById(String id) {
        return scoreRecordMapper.selectById(id);
    }

    /**
     * 评委提交报名解锁以及减少一次评分操作数量
     *
     * @param scoreRecord
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public int submitScoreRecord(YjScoreRecord scoreRecord) {
        /* 减少一次评委的评分次数 begin**/
        CmsUser cmsUser = userService.selectCmsUserById(scoreRecord.getUserId());
        if (cmsUser.getExecCount() == 0) {
            return 2;
        }
        cmsUser.setExecCount(cmsUser.getExecCount() - 1);
        userService.saveCmsUser(cmsUser);
        /* 减少一次评委的评分次数 end**/

        YjScoreRecordDto scoreRecordDto = new YjScoreRecordDto();
        scoreRecordDto.setBmId(scoreRecord.getBmId());
        List<YjScoreRecord> yjScoreRecords = scoreRecordMapper.selectList(scoreRecordDto);
        /* 报名信息处理 begin **/
        YjUserBmInfo userBmInfo = userBmInfoService.selectUserBmInfoById(scoreRecord.getBmId());
        if (userBmInfo != null) {
            //计算平均分
            userBmInfo.setScore(ageScore(yjScoreRecords, scoreRecord.getScore().toString()));
            //每评一次+1
            userBmInfo.setScoreCount(userBmInfo.getScoreCount() + 1);
            if (userBmInfo.getScoreCount() > 1) {//两次评分修改评分状态
                userBmInfo.setScoreStatus(1);
            }
            userBmInfo.setLockStatus(0);//解锁报名信息
            userBmInfoService.saveUserBmInfo(userBmInfo);
        } else {
            return -1;
        }
        /* 报名信息处理 end **/

        /* 添加一条评分记录 begin **/
        scoreRecord.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        scoreRecordMapper.insert(scoreRecord);
        /* 添加一条评分记录 end **/

        return 1;

    }

    /**
     * 计算平均分
     * 如果查询的成绩数据是NULL就直接返回score
     *
     * @param scoreRecords
     * @param score
     * @return
     */
    private String ageScore(List<YjScoreRecord> scoreRecords, String score) {
        float sumScore = 0;
        DecimalFormat df = new DecimalFormat("#0.##");//格式化小，保留两位小数
        df.setRoundingMode(RoundingMode.FLOOR);
        if (scoreRecords != null && scoreRecords.size()>0) {
            for (YjScoreRecord scoreRecord : scoreRecords) {
                sumScore += Float.valueOf(scoreRecord.getScore().toString());
            }
            sumScore += Float.valueOf(score);
            return df.format((sumScore / (scoreRecords.size() + 1)));
        } else {
            return score;
        }
    }

    public PageInfo<YjScoreRecord> selectScoreRecordPage(YjScoreRecordDto scoreRecordDto) {
        PageHelper.startPage(scoreRecordDto.getPageNo(), scoreRecordDto.getPageSize());
        List<YjScoreRecord> yjScoreRecords = scoreRecordMapper.selectList(scoreRecordDto);
        PageInfo<YjScoreRecord> pageInfo = new PageInfo(yjScoreRecords, 5);
        return pageInfo;
    }


}
