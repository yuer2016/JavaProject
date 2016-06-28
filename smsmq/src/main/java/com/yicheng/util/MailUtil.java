package com.yicheng.util;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
public class MailUtil
{
	private Logger logger = Logger.getLogger(MailUtil.class);

	private MimeMessage mimeMsg; // MIME邮件对象
	private Session session; // 邮件会话对象
	private Properties props; // 系统属性
	private String username = ""; // smtp认证用户名和密码
	private String password = "";
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成//MimeMessage对象
	public MailUtil(String smtp)
	{
		setSmtpHost(smtp);
		createMimeMessage();
	}

	public void setSmtpHost(String hostName)
	{
		logger.info("设置系统属性：mail.smtp.host = " + hostName);
		if (props == null)
			props = System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", hostName); // 设置SMTP主机
	}
	
	public boolean createMimeMessage()
	{
		boolean flag = true;
		try {
			logger.info("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		}catch (Exception e)
		{
			logger.error("获取邮件会话对象时发生错误！" + e);
			flag = false;
		}
		logger.info("准备创建MIME邮件对象！");
		try {
			mimeMsg = new MimeMessage(session); 
			mp = new MimeMultipart(); 
		}catch (Exception e)
		{
			System.err.println("创建MIME邮件对象失败！" + e);
			flag = false;
		}
		return flag;
	}
	
	public void setNeedAuth(boolean need) {
		logger.info("设置smtp身份认证：mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	public void setNamePass(String name, String pass)
	{
		logger.info("程序得到用户名与密码");
		username = name;
		password = pass;
	}
	
	public boolean setSubject(String mailSubject) {
		boolean flag = false;
		logger.info("设置邮件主题！");
		try {
			mimeMsg.setSubject(mailSubject);
			flag =  true;
		}
		catch (Exception e) {
			logger.error("设置邮件主题发生错误！");
			flag =  false;
		}
		return flag;
	}

	public boolean setBody(String mailBody)
	{
		boolean flag = false;
		try
		{
			logger.info("设置邮件体格式");
			BodyPart bp = new MimeBodyPart();
			bp.setContent(
					"<meta http-equiv=Content-Type content=text/html; charset=gb2312>"
							+ mailBody, "text/html;charset=GB2312");
			mp.addBodyPart(bp);
			flag = true;
		}
		catch (Exception e)
		{
			logger.error("设置邮件正文时发生错误！" + e);
			flag = false;
		}
		return flag ;
	}
	
	public boolean addFileAffix(String filename) {
		boolean flag = false;
		logger.info("增加邮件附件：" + filename);
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			mp.addBodyPart(bp);
			flag =  true;
		}
		catch (Exception e) {
			logger.error("增加邮件附件：" + filename + "发生错误！" + e);
			flag =  false;
		}
		return flag;
	}

	public boolean setFrom(String from) {
		boolean flag = false;
		logger.info("设置发信人！");
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			flag =  true;
		}
		catch (Exception e)
		{
			logger.error("设置发信人时发生错误！" + e);
			flag =  false;
		}
		return flag;
	}
	
	public boolean setTo(String to)
	{
		boolean flag = false;
		logger.info("设置收信人");
		if (to == null)
			flag =  false;
		try
		{
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(to));
			flag =  true;
		}
		catch (Exception e)
		{
			logger.error("设置收信人时发生错误！" + e);
			flag =  false;
		}
		return flag;
	}
	
	public boolean setCopyTo(String copyto)
	{
		boolean flag = false;
		logger.info("发送附件到");
		if (copyto == null)
			flag =  false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC,
					(Address[]) InternetAddress.parse(copyto));
			flag =  true;
		}
		catch (Exception e)
		{
			logger.error("设置附件时发生错误！" + e);
			flag =  false;
		}
		return flag;
	}
	
	public boolean sendout() throws Exception
	{
		boolean flag = false;
		try
		{
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			logger.info("正在发送邮件....");
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp"); 
			transport.connect((String) props.get("mail.smtp.host"), username,password);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			logger.info("发送邮件成功！");
			transport.close();
			flag =  true;
		}
		catch (Exception e)
		{
			logger.error("邮件发送失败！" + e);
			flag =  false;
			throw e;
		}
		return flag;
	}

}
