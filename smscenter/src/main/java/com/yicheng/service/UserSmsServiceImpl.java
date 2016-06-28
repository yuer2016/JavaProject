package com.yicheng.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yicheng.dao.UserSmsDao;
import com.yicheng.entity.SmsContent;
import com.yicheng.entity.UserSmsBean;
import com.yicheng.smsService.SmsService;
@Service
public class UserSmsServiceImpl  implements UserSmsService{

	private Logger  logger = Logger.getLogger(UserSmsServiceImpl.class);

	@Autowired
	private UserSmsDao userSmsDao;

//	@Autowired
//	private SettingService settingService;


	@Override
	public boolean checkSmsSurplus(String smsId, int usecont) {
		boolean result = true;
		UserSmsBean userSmsBean = userSmsDao.getAll(smsId);
		if(usecont>userSmsBean.getSmsSurplus()){
			result = false;
			logger.info(userSmsBean.getUserId()+"用户短信发送条数不足！");
		}
		return result;
	}

	@Override
	public String sendSms(String smsContent) {
		String result = "发送成功！";
		SmsContent content = paseJsonToSmsContent(smsContent);
		//获得要发送的短信条数
		int usecount = countsendsms(content.getPhone());
		//检查短信条数是否足够
		if(checkSmsSurplus(content.getUserId(),usecount)){
			SmsService smsService = new SmsService();
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("useCount", usecount);
			parms.put("smsId", content.getUserId());
			int startcount = deleteSmsSurplus(parms);//预发送条数
			//实发条数
			int servercount = smsService.sendSms(content.getUserId(), content.getPhone(), content.getMessage());
			logger.info("发送条数为："+servercount);
			if(servercount>0 && startcount>servercount){
				parms.put("useCount", servercount-startcount);
				deleteSmsSurplus(parms);
				logger.info(parms.get("useCount")+":"+parms.get("smsId"));
			}
		}else{
			result = "你的发送条数不足";
			logger.info("用户"+content.getUserId()+"条数不足！");
		}

		return result;
	}

	@Override
	public int deleteSmsSurplus(Map<String, Object> parms) {
		int count = 0;
		count =userSmsDao.updateSmsSurplus(parms);
		return count;
	}
	/**
	 * 将客户端传来的json转换为对应的实体类
	 * */
	private SmsContent paseJsonToSmsContent(String json){
		SmsContent smsContent = null;
		try {
			smsContent = JSON.parseObject(json, SmsContent.class); 
		} catch (Exception e) {
			logger.info("短信参数有误!解析短信json失败!");
			logger.debug(e);
		}

		return smsContent; 
	}
	/**
	 * 检查短信发送条数
	 * */
	private int countsendsms(String phone){
		int count = 1;
		String[] messages =  phone.split(",");
		count =messages.length;
		return count;
	}

	@Override
	public String sendSttingSms(SmsContent content) {
		String result = "发送成功！";
		//获得要发送的短信条数
		int usecount = countsendsms(content.getPhone());
		//检查短信条数是否足够
		if(checkSmsSurplus(content.getUserId(),usecount)){
			SmsService smsService = new SmsService();
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("useCount", usecount);
			parms.put("smsId", content.getUserId());
			int startcount = deleteSmsSurplus(parms);//预发送条数
			//实发条数
			int servercount = smsService.sendSms(content.getUserId(), content.getPhone(), content.getMessage());
			logger.info("发送条数为："+servercount);
			if(servercount>0 && startcount>servercount){
				parms.put("useCount", servercount-startcount);
				deleteSmsSurplus(parms);
				logger.info(parms.get("useCount")+":"+parms.get("smsId"));
			}
		}else{
			result = "你的发送条数不足";
			logger.info("用户"+content.getUserId()+"条数不足！");
		}
		return result;
	}

}
