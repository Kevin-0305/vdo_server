package com.daily.english.app.job;

import com.daily.english.app.domain.YjUserBmInfo;
import com.daily.english.app.service.YjUserBmInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
@EnableScheduling
@Slf4j
public class YjBmInfoUnlockTask {
    @Autowired
    private YjUserBmInfoService userBmInfoService;

    /**
     * 每日凌晨将加锁数据解锁
     */
    @Scheduled(cron = "0 0 0 * * *")
    private void unlockBmInfo() {
        YjUserBmInfo userBmInfo = YjUserBmInfo.builder().lockStatus(1).build();
        List<YjUserBmInfo> yjUserBmInfos = userBmInfoService.selectUserBmInfoList(userBmInfo);
        for (YjUserBmInfo yjUserBmInfo : yjUserBmInfos) {
            userBmInfoService.unlockBmInfoById(yjUserBmInfo.getId().toString());
        }
    }
}