package com.daily.english.app.service;

import com.daily.english.app.domain.WordPk;
import com.daily.english.app.domain.WordPkRobot;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.mapper.WordPkMapper;
import com.daily.english.app.mapper.WordPkRobotMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WordPkService {

    @Autowired
    private WordPkMapper wordPkMapper;
    @Autowired
    private WordPkRobotMapper wordPkRobotMapper;

    public List<WordPk> selectWordPkList(WordPk wordPk) {
        return wordPkMapper.selectList(wordPk);
    }

    public List<WordPk> selectWordPkRandom(Integer size,String level) {
        return wordPkMapper.selectRandomList(size,level);
    }

    public WordPk selectWordPkById(String id) {
        return wordPkMapper.selectById(id);
    }

    public void saveWordPk(WordPk wordPk) {
        if (null != wordPk.getId() && wordPk.getId() > 0) {
            wordPk.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            wordPkMapper.updateSelective(wordPk);
        } else {
            wordPk.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            wordPkMapper.insertSelective(wordPk);
        }
    }

    public PageInfo<WordPk> selectWordPkPage(PageSearchDto pageSearchDto) {
        PageHelper.startPage(pageSearchDto.getPageNo(), pageSearchDto.getPageSize());
        List<WordPk> list = wordPkMapper.selectPageList(pageSearchDto);
        PageInfo<WordPk> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public void deleteWordPkById(String id) {
        wordPkMapper.deleteById(id);
    }

    /**
     * 随机获取一名机器人信息
     *
     * @return
     */
    public WordPkRobot selectRandomRobot() {
        return wordPkRobotMapper.selectRandomRobot();
    }


}
