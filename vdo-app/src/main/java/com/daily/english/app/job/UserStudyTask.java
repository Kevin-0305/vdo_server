package com.daily.english.app.job;

import com.daily.english.app.domain.UserStudy;
import com.daily.english.app.service.UserStudyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * describe: 用户学习相关任务
 * @author: zhc
 **/
@Component
@Configuration
@EnableScheduling
@Slf4j
public class UserStudyTask {
	@Autowired
	private UserStudyService userStudyService;
	/**
	 * 每周一 4 点清空上周学习记录
	 */
	@Scheduled(cron = "0 0 4 ? * MON")
//	@Scheduled(cron = "0/50 * * * * * ")
	private void  weekUserValueClear() {
		List<UserStudy>  userStudyList =userStudyService.queryUserStudy();
		userStudyList.forEach(userStudy -> {
			UserStudy ustudy = UserStudy.builder().studyTimeWeek(0L).studyTimeWeekMax(0L).id(userStudy.getId())
					.finishVdoWeek(0L).newWordsWeek(0L)
					.finishChallengeWeek(0L).averageScoreWeek(0L)
					.weekHour("0")
					.build();
			userStudyService.modifyUserStudy(ustudy);
		});
	}
	/**
	 * 每天 4 点清空昨天学习记录
	 */
	@Scheduled(cron = "0 0 4 * * * ")
//	@Scheduled(cron = "0/20 * * * * * ")
	private void  dayUserValueClear() {
		List<UserStudy>  userStudyList =userStudyService.queryUserStudy();
		userStudyList.forEach(userStudy -> {
			UserStudy ustudy = UserStudy.builder().studyTimeToday(0L).questionAmoutToday(0L)
					.id(userStudy.getId()).build();
			userStudyService.modifyUserStudy(ustudy);
		});
	}
	/**
	 * 每天 1点统计昨天 达成本周学习时长最久的日期
	 */
	@Scheduled(cron = "0 0 1 * * * ")
//	@Scheduled(cron = "0/20 * * * * * ")
	private void  userValueWeekRecording() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		List<UserStudy>  userStudyList =userStudyService.queryUserStudy();
		userStudyList.forEach(userStudy -> {
			Long studyTimeToday = userStudy.getStudyTimeToday();
			Long studyTimeWeekMax = userStudy.getStudyTimeWeekMax();
            Long studyTimeMax = userStudy.getStudyTimeMax();
			UserStudy ustudy = UserStudy.builder().studyTimeAll(studyTimeToday+userStudy.getStudyTimeAll())
					.studyTimeWeekMax(studyTimeToday > studyTimeWeekMax ? studyTimeToday : studyTimeWeekMax)
					.studyTimeWeekMaxDate(studyTimeToday > studyTimeWeekMax ? yesterday:userStudy.getStudyTimeWeekMaxDate())
					.studyTimeMax(studyTimeToday > studyTimeMax ? studyTimeToday : studyTimeMax)
					.studyTimeMaxDate(studyTimeToday > studyTimeMax ? yesterday : userStudy.getStudyTimeMaxDate())
					.studyTimeWeek(studyTimeToday+userStudy.getStudyTimeWeek())
					.studyDayAll(studyTimeToday > 0 ? userStudy.getStudyDayAll()+1 : userStudy.getStudyDayAll())
			        .studyDayContinue(studyTimeToday > 0 ?  userStudy.getStudyDayContinue() + 1 : 0)
					.id(userStudy.getId()).build();
			userStudyService.modifyUserStudy(ustudy);
		});
	}

}
