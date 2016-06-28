package com.yicheng.smsService;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.yicheng.entity.Mail;
import com.yicheng.util.MailUtil;


public class MailService {
	
	private Logger logger = Logger.getLogger(MailService.class);

	/**
	 * 发送邮件
	 * @return
	 * @throws Exception 
	 */
	public void sendMail(String jsonmail) throws Exception{
		Mail mail = getParam(jsonmail);
		MailUtil themail = new MailUtil("mail.e-trans.com.cn");
		themail.setNeedAuth(true);
		themail.setSubject(mail.getMailSubject());
		//邮件内容 支持html
		themail.setBody(mail.getMailbody());
		//收件人邮箱
		themail.setTo(mail.getSetTo());
		//发件人邮箱
		themail.setFrom("zhangling@e-trans.com.cn");
		//设置附件,附件在本地机子上的绝对路径
		String atts = mail.getAttachments();
		if(atts!=null && !"".equals(atts)){
			String[] attst = atts.split(",");
			if(attst.length>0){
				for(String att :attst){
					themail.addFileAffix(att);
				}
			}
		}
		themail.setNamePass("zhangling", "888888"); // 用户名与密码
		themail.sendout();
	}
	
	private Mail getParam(String jsonmail) throws Exception{
		Mail mail = null;
		try {
			mail =JSON.parseObject(jsonmail, Mail.class);
		} catch (Exception e) {
			logger.info("解析邮件json失败！");
			logger.debug(e);
		}
		return mail;
	}

}
