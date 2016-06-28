package com.yicheng.service;

import java.util.Map;

import com.yicheng.entity.SmsContent;


public interface UserSmsService {
	

	/**
	 * 检查用户发送条数是否足够
	 * */
	boolean checkSmsSurplus(String smsId ,int usecont);
	
	/**
	 * 扣除用户短信发送条数
	 * */
	int deleteSmsSurplus(Map<String, Object> parms);
	/**
	 * 发送短信接口
	 * */
	String sendSms( String smsContent);
	
	/**
	 * 发送模板短信
	 * */
	String sendSttingSms(SmsContent smsContent);
	
	
	
	
	
}
