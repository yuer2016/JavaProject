package com.yicheng.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.yicheng.entity.Mail;
import com.yicheng.entity.Response;
import com.yicheng.service.SettingService;
import com.yicheng.smsService.MailService;


@RestController
@RequestMapping(value = "/mail")
public class MailController extends CommonController{
	
	
	@Autowired
	private SettingService settingService;

	
	private MailService mailService = new MailService();


	private Logger logger = Logger.getLogger(MailController.class);

	/**
	 * 群发邮件 
	 * @return
	 */
	@RequestMapping(value = "/sendMailArray", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String sendMailList(HttpServletRequest request,@RequestBody String mailList) {
		Response respon = new Response();
		parseRequest(request, respon);
		String messg = "发送成功";
		try {
			mailService.sendMail(mailList);
		}  catch(JSONException e){
			respon.setCode(201);
			messg = "json格式转换错误";
		} catch(NullPointerException e){
			respon.setCode(202);
			messg = "404错误:java.lang.NullPointerException";
		} catch (Exception e) {
			respon.setCode(-1);
			messg = "发送失败";
			logger.debug(e);
		}
		respon.setMessage(messg);
		return respon.toJSON();
	}
	/**
	 * 发送单个邮件
	 * @return
	 */
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String sendMailOne(HttpServletRequest request,@RequestBody String mailList) {
		Response respon = new Response();
		parseRequest(request, respon);
		String messg = "发送成功";
		try {
			mailService.sendMail(mailList);
		} catch(JSONException e){
			respon.setCode(201);
			messg = "json格式转换错误";
		} catch(NullPointerException e){
			respon.setCode(202);
			messg = "404错误:java.lang.NullPointerException";
		} catch (Exception e) {
			respon.setCode(-1);
			messg = "发送失败";
			logger.debug(e);
		}
		respon.setMessage(messg);
		return respon.toJSON();
	}
	/**
	 * 发送模板邮件
	 * @return
	 */
	@RequestMapping(value = "/sendSettingMail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String sendSettingMail(HttpServletRequest request,@RequestBody String mailList) {
		Response respon = new Response();
		parseRequest(request, respon);
		String messg = "发送成功";
		try {
			Mail mail = paseJsonToSmsContent(mailList);
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("type", "1");
			parms.put("bizId", "2");
			String context = settingService.getSttingTemplate(parms);
			mail.setMailbody(context.replace("{0}", mail.getMailbody()));
			String mailjson = JSON.toJSONString(mail);
			mailService.sendMail(mailjson);
		} catch(JSONException e){
			respon.setCode(201);
			messg = "json格式转换错误";
		} catch(NullPointerException e){
			respon.setCode(202);
			messg = "404错误:java.lang.NullPointerException";
		} catch (Exception e) {
			respon.setCode(-1);
			messg = "发送失败";
			logger.debug(e);
		}
		respon.setMessage(messg);
		return respon.toJSON();
	}
	/**
	 * 发送用户提示邮件
	 * @return
	 */
	@RequestMapping(value = "/sendsetToUserMail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody String sendsetToUserMail(HttpServletRequest request,@RequestBody String mailList) {
		Response respon = new Response();
		parseRequest(request, respon);
		String messg = "发送成功";
		try {
			Mail mail = paseJsonToSmsContent(mailList);
			Map<String, Object> parms = new HashMap<String, Object>();
			parms.put("type", "2");
			parms.put("bizId", "2");
			String context = settingService.getSttingTemplate(parms);
			context = context.replace("{0}", mail.getSetToUser());
			context = context.replace("{1}", mail.getMailbody());
			mail.setMailbody(context);
			String mailjson = JSON.toJSONString(mail);
			mailService.sendMail(mailjson);
		} catch(JSONException e){
			respon.setCode(201);
			messg = "json格式转换错误";
		} catch(NullPointerException e){
			respon.setCode(202);
			messg = "404错误:java.lang.NullPointerException";
		} catch (Exception e) {
			respon.setCode(-1);
			messg = "发送失败";
			logger.debug(e);
		}
		respon.setMessage(messg);
		return respon.toJSON();
	}
	
	
	
	
	
	
	/**
	 * 将客户端传来的json转换为对应的实体类
	 * */
	private Mail paseJsonToSmsContent(String json){
		Mail mail = null;
		try {
			mail = JSON.parseObject(json, Mail.class); 
		} catch (Exception e) {
			logger.info("短信参数有误!解析短信json失败!");
			logger.debug(e);
		}

		return mail; 
	}

	

	

}
