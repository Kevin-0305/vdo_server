package com.daily.english.app.service;

import com.alibaba.fastjson.JSON;
import com.daily.english.app.domain.Answer;
import com.daily.english.app.domain.AnswerEntry;
import com.daily.english.app.domain.AppVersion;
import com.daily.english.app.mapper.AnswerMapper;
import com.daily.english.app.mapper.AppVersionMapper;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AppVersionrService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    public void saveAppVersion(AppVersion appVersion) {
        appVersionMapper.insertAppVersion(appVersion);
    }
    public AppVersion getAppVersionByType(int type) {
        AppVersion appVersion = appVersionMapper.findAppVersionByType(type);
        return appVersion;
    }

    public List<AppVersion> getAppVersionList() {
        List<AppVersion> appVersionList = appVersionMapper.findAppVersionList();
        return appVersionList;
    }


}
