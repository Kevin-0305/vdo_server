package com.daily.english.app.service;

import com.daily.english.app.condition.UserRankListCondition;
import com.daily.english.app.domain.User;
import com.daily.english.app.domain.UserRankList;
import com.daily.english.app.mapper.UserMapper;
import com.daily.english.app.mapper.UserRankListMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserRankListService {
	@Autowired
	 private UserRankListMapper userRankListMapper;
	@Autowired
	private UserMapper userMapper;

	public  List<UserRankList> getBambooRankByPage(UserRankListCondition urc){
		List<UserRankList> ubrList= userRankListMapper.findBambooRankList(urc);
		ubrList.forEach(ubr ->{
			User user = userMapper.findUserById(ubr.getUserId().toString());
			if(user != null){
				ubr.setUserName(user.getNickname());
				ubr.setUserImg(user.getUserImg());
			}
		});
		return ubrList;
	}
	public  List<UserRankList> getExpRankByPage(UserRankListCondition urc){
		List<UserRankList> ubrList= userRankListMapper.findExpRankList(urc);
		ubrList.forEach(ubr ->{
			User user = userMapper.findUserById(ubr.getUserId().toString());
			if(user != null){
				ubr.setUserName(user.getNickname());
				ubr.setUserImg(user.getUserImg());
			}
		});
		return ubrList;
	}
	public Integer getExpRankListCount(UserRankListCondition urc) {
		return userRankListMapper.findExpRankListCount(urc);
	}
	public Integer getBambooRankListCount(UserRankListCondition urc) {
		return userRankListMapper.findBambooRankListCount(urc);
	}
    public  void saveExpRankList(UserRankList url){
		userRankListMapper.insertExpRank(url);
	}
	public  void saveBambooRankList(UserRankList url){
		userRankListMapper.insertBambooRank(url);
	}
	public void deleteExpRankByType(String type,String table){
		userRankListMapper.deleteByTable(type,table);
	}

}
