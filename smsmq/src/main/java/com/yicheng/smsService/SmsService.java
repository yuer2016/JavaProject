package com.yicheng.smsService;

import java.io.IOException;

import com.yicheng.dao.SmsDao;
import com.yicheng.dao.impl.SmsDaoImpl;
import com.yicheng.util.SendSMSUtil;

public class SmsService {
	
	public int sendSms(String userid,String mobile, String content){
		int result = 0;
		SmsDao smsdao = new SmsDaoImpl(); 
		try {
			result = SendSMSUtil.SmsSends(mobile, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		smsdao.putsms(userid, "phone:"+mobile+"sms"+content);
		return result;
	}

}
