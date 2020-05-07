package com.daily.english.app.service;

import com.daily.english.app.domain.BaseCode;
import com.daily.english.app.mapper.BaseCodeMapper;
import com.daily.english.app.util.Redeem;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * code库业务层
 *
 * @author
 * @create 2019-09-25 10:38
 **/
@Slf4j
@Service
public class BaseCodeService {

    @Autowired
    private BaseCodeMapper baseCodeMapper;

    /**
     * 批量生成code，添加到baseCode表中
     * @param generatorCount
     */
    public void generatorBaseCode(Integer generatorCount) {
        List<String> codeList = Redeem.createCode((byte) 1, generatorCount, 12, Redeem.password);
        for (String s : codeList) {
            BaseCode baseCode = new BaseCode();
            baseCode.setCode(s);
            baseCode.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            baseCodeMapper.insertSelective(baseCode);
        }

    }
}
