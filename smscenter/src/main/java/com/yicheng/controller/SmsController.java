package com.yicheng.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yicheng.entity.Response;
import com.yicheng.entity.SmsContent;
import com.yicheng.service.SettingService;
import com.yicheng.service.UserSmsService;
import com.yicheng.util.Tools;

@RestController
@RequestMapping(value = "/sms")
public class SmsController extends CommonController {

	@Autowired
	private UserSmsService userSmsService;

	@Autowired
	private SettingService settingService;

	private Logger logger = Logger.getLogger(SmsController.class);

	/**
	 * 发送短信
	 * @return
	 */
	@RequestMapping(value ="/send", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public String send(HttpServletRequest request,@RequestBody String json){
		Response respon = new Response();
		parseRequest(request, respon);
		String result = null;
		try {
			if(Tools.checkNotEmpty(json)){
				result = userSmsService.sendSms(json);
				respon.setResult(result);
			}else {
				result="请传递参数！";
				respon.setMessage(result);
				logger.info("短信发送参数为空");
			}
		} catch (Exception e) {
			errorHandler(e,respon,logger);
		}

		return respon.toJSON();
	}

	/**
	 * 发送短信验证码
	 * @return
	 */
	@RequestMapping(value ="/sendVer", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public String seedVerification(HttpServletRequest request,@RequestBody String json){
		Response respon = new Response();
		parseRequest(request, respon);
		String result = null;
		SmsContent content = null;
		try {
			content = paseJsonToSmsContent(json);
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("type", "1");
			parms.put("bizId", "1");
			String context = settingService.getSttingTemplate(parms);
			content.setMessage(context.replace("{0}", content.getVerificationCode()));
			result = userSmsService.sendSttingSms(content);
			respon.setMessage(content.getMessage());
		} catch (Exception e) {
			errorHandler(e,respon,logger);
		}
		respon.setResult(result);
		return respon.toJSON();
		
	}
	
	/**
	 * 发送用户提示短信
	 * @return
	 */
	@RequestMapping(value ="/sendPro", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public String seedPrompt(HttpServletRequest request,@RequestBody String json){
		Response respon = new Response();
		parseRequest(request, respon);
		String result = null;
		try {
			SmsContent content = paseJsonToSmsContent(json);
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("type", "2");
			parms.put("bizId", "1");
			String context = settingService.getSttingTemplate(parms);
			context = context.replace("{0}", content.getUsername());
			context = context.replace("{1}", content.getMessage());
			content.setMessage(context);
			result = userSmsService.sendSttingSms(content);
			respon.setMessage(context);
		} catch (Exception e) {
			errorHandler(e,respon,logger);
		}
		respon.setResult(result);
		return respon.toJSON();
		
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

}
