package com.daily.english.app.job;

import com.daily.english.app.domain.UserRankList;
import com.daily.english.app.domain.UserValue;
import com.daily.english.app.service.UserRankListService;
import com.daily.english.app.service.UserValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe: 排行榜相关任务
 * creat_user: zhc
 **/
@Component
@Configuration
@EnableScheduling
@Slf4j
public class UserRankTask {
	@Autowired
	private UserValueService userValueService;
	@Autowired
	private UserRankListService userRankListService;
	private final String BAMBOORANKTABLE = "ed_user_bamboo_rank_list";
	private final String EXPRANKTABLE = "ed_user_exp_rank_list";
	/**
	 *  每日统计经验榜和竹子榜 每日凌晨25秒
	 */
//	@Scheduled(cron = "0/10 * * * * * ")
	@Scheduled(cron = "0 0 0 * * * ")
	private void  dayUserRank() {
		List<UserValue> userValues = userValueService.findUserValueList();
		//昨天获得竹子
		List<UserValue> bambooRanks = userValues.stream().sorted((u1, u2) -> u2.getGetBambooToday().compareTo(u1.getGetBambooToday())).collect(Collectors.toList());
		long count = 1;
		userRankListService.deleteExpRankByType("day",BAMBOORANKTABLE);
		for(UserValue bamboo : bambooRanks){
			UserRankList url = UserRankList.builder().rankChange((int)(bamboo.getLastBambooRank() - count))
					.userId(bamboo.getId())
					.Bamboo(bamboo.getGetBambooToday()).type("day")
					.ranks(count).build();
			userRankListService.saveBambooRankList(url);
			//更新当前排名
			userValueService.updateUserValue(UserValue.builder().id(bamboo.getId())
					.getBambooYesterday(bamboo.getGetBambooToday())
					.lastBambooRank(bamboo.getBambooRank())
					.getBambooToday(0L)
					.bambooRank(count)
					.sumWeekBamboo(bamboo.getSumWeekBamboo()+bamboo.getGetBambooToday())
					.build());
			count +=  1;
		}
		count = 1;
		userRankListService.deleteExpRankByType("day",EXPRANKTABLE);
		//昨天获得经验排序
		List<UserValue> expRanks = userValues.stream().sorted((u1, u2) -> u2.getGetExpToday().compareTo(u1.getGetExpToday())).collect(Collectors.toList());
		for(UserValue exp : expRanks){
			UserRankList url = UserRankList.builder().rankChange((int)(exp.getLastExpRank() - count))
					.userId(exp.getId())
					.exp(exp.getGetExpToday()).type("day")
					.ranks(count).build();
			userRankListService.saveExpRankList(url);
			//更新当前排名
			userValueService.updateUserValue(UserValue.builder()
					.getExpYesterday(exp.getGetExpToday())
					.getExpToday(0L)
					.lastExpRank(exp.getExpRank())
					.id(exp.getId()).expRank(count)
					.sumWeekExp(exp.getGetExpToday()+exp.getSumWeekExp())
					.build());
			count +=  1;
		}
	}
	//每周一0点执行
	@Scheduled(cron = "0 0 0 ? * MON")
//	@Scheduled(cron = "0/50 * * * * * ")
	private void  weekUserRank() {
		List<UserValue> userValues = userValueService.findUserValueList();
		//上周获得竹子
		List<UserValue> bambooRanks = userValues.stream().sorted((u1, u2) -> u2.getSumWeekBamboo().compareTo(u1.getSumWeekBamboo())).collect(Collectors.toList());
		long count = 1;
		userRankListService.deleteExpRankByType("week",BAMBOORANKTABLE);
		for(UserValue bamboo : bambooRanks){
			UserRankList url = UserRankList.builder().rankChange((int)(bamboo.getLastBambooWeekRank() - count))
					.userId(bamboo.getId()).Bamboo(bamboo.getSumWeekBamboo())
					.type("week").ranks(count).build();
			userRankListService.saveBambooRankList(url);
			//更新当前排名
			userValueService.updateUserValue(UserValue.builder().id(bamboo.getId())
					.lastBambooWeekRank(bamboo.getBambooRankLastweek())
					.bambooRankLastweek(count).build());
			count +=  1;
		}
		count = 1;
		userRankListService.deleteExpRankByType("week",EXPRANKTABLE);
		//上周获得经验排序
		List<UserValue> expRanks = userValues.stream().sorted((u1, u2) -> u2.getSumWeekExp().compareTo(u1.getSumWeekExp())).collect(Collectors.toList());
		for(UserValue exp : expRanks){
			UserRankList url = UserRankList.builder().rankChange((int)(exp.getLastExpWeekRank() - count))
					.userId(exp.getId()).exp(exp.getSumWeekExp()).type("week")
					.ranks(count).build();
			userRankListService.saveExpRankList(url);
			//更新当前排名
			userValueService.updateUserValue(UserValue.builder().id(exp.getId())
					.lastExpWeekRank(exp.getExpRankLastweek())
					.expRankLastweek(count).build());
			count +=  1;
		}
	}
}
