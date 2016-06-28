package com.yicheng.entity;

public class Mail {

	//系统属性：mail.smtp.host,比如： smtp.163.com
	public String  hostName;
	
	//邮件标题
	public String mailSubject;
	
	//邮件内容
	public String mailbody;
	
	//收件人邮箱
	public String setTo;
	
	//发件人邮箱
	public String setFrom;
	
	//发件人用户邮箱
	public String mailUser;
	
	//发件人密码
	public String mailPassword;
	
	//附件
	public String attachments;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailbody() {
		return mailbody;
	}

	public void setMailbody(String mailbody) {
		this.mailbody = mailbody;
	}

	public String getSetTo() {
		return setTo;
	}

	public void setSetTo(String setTo) {
		this.setTo = setTo;
	}

	public String getSetFrom() {
		return setFrom;
	}

	public void setSetFrom(String setFrom) {
		this.setFrom = setFrom;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}
	
}
