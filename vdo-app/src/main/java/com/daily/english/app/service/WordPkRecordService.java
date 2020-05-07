package com.daily.english.app.service;

import com.daily.english.app.domain.UserValue;
import com.daily.english.app.domain.WordPkRecord;
import com.daily.english.app.mapper.UserValueMapper;
import com.daily.english.app.mapper.WordPkRecordMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 单词PK记录业务层
 *
 * @author
 * @create 2019-09-16 16:22
 **/
@Slf4j
@Service
public class WordPkRecordService {
    @Autowired
    private WordPkRecordMapper wordPkRecordMapper;
    @Autowired
    private UserValueMapper userValueMapper;

    /**
     * 提交对战信息
     * @param wordPkRecord
     * @return 挑战记录ID，用于用户领取竹子
     */
    public String addWordPkRecord(WordPkRecord wordPkRecord) {
        /** 竹子添加业务逻辑 begin*/
        if (wordPkRecord.getIsVictory().equals(1)){
//            UserValue userValue = userValueMapper.findUserValueByUserId(wordPkRecord.getUserId().toString());
//            userValue.setGetBambooToday(userValue.getGetBambooToday()+ +10);
//            userValue.setBamboo(userValue.getBamboo()+10);
//            userValueMapper.updateUserValue(userValue);
            wordPkRecord.setBambooReward(10);
        }else {
            wordPkRecord.setBambooReward(5);//扣除金币数量
        }
        /** 竹子添加业务逻辑 end*/

        /** 保存对战记录 begin*/
//        wordPkRecord
        wordPkRecord.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        wordPkRecordMapper.insertSelective(wordPkRecord);
        /** 保存对战记录 end*/

        return wordPkRecord.getId().toString();
    }

    /**
     * 挑战胜利领取竹币
     * @param pkRecordId 挑战记录ID
     */
    public void receiveBamboo(String pkRecordId) {
        WordPkRecord wordPkRecord = wordPkRecordMapper.selectById(pkRecordId);
        if (wordPkRecord == null){
            return;
        }
        if (wordPkRecord.getIsVictory().equals(1) && wordPkRecord.getIsReceive().equals(0)) {
            UserValue userValue = userValueMapper.findUserValueByUserId(wordPkRecord.getUserId().toString());
            userValue.setGetBambooToday(userValue.getGetBambooToday() + +wordPkRecord.getBambooReward());
            userValue.setBamboo(userValue.getBamboo() + 10);
            userValueMapper.updateUserValue(userValue);
            wordPkRecord.setIsReceive(1);
            wordPkRecordMapper.updateSelective(wordPkRecord);
        }
    }

    /**
     * 用户今日战绩查询
     *
     * @param userId    用户ID
     * @return
     */
    public Map<String, Object> userTodayRecordCount(Integer userId) {
        //胜率=获胜场次÷ 总比赛场次x100%
        Integer victoryCount = wordPkRecordMapper.selectUserOutcomeCount(userId, 1, 1);
        Integer loserCount = wordPkRecordMapper.selectUserOutcomeCount(userId, 0, 1);
        Double odds = getOdds(victoryCount, loserCount);
        Map<String, Object> result = Maps.newHashMap();
        result.put("odds",odds.intValue());
        result.put("victoryCount",victoryCount);
        result.put("loserCount",loserCount);
        return result;
    }

    /**
     * 用户总战绩查询
     *
     * @param userId    用户ID
     * @return
     */
    public Map<String, Object> userRecordCount(Integer userId) {
        //胜率=获胜场次÷ 总比赛场次x100%
        Integer victoryCount = wordPkRecordMapper.selectUserOutcomeCount(userId, 1, 0);
        Integer loserCount = wordPkRecordMapper.selectUserOutcomeCount(userId, 0, 0);
        Double odds = getOdds(victoryCount, loserCount);
        Map<String, Object> result = Maps.newHashMap();
        result.put("odds",odds.intValue());
        result.put("victoryCount",victoryCount);
        result.put("loserCount",loserCount);
        return result;
    }

    private Double getOdds(Integer victoryCount, Integer loserCount) {
        return victoryCount.doubleValue() / (victoryCount.doubleValue() + loserCount.doubleValue()) * 100;
    }
}
