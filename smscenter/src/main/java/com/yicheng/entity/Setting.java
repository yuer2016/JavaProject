package com.yicheng.entity;

public class Setting {
	
	private int settingId;
	private int type;
	private int serverName;
	private String Template;
	private int BizId;
	
	public int getSettingId() {
		return settingId;
	}
	public void setSettingId(int settingId) {
		this.settingId = settingId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getServerName() {
		return serverName;
	}
	public void setServerName(int serverName) {
		this.serverName = serverName;
	}
	public String getTemplate() {
		return Template;
	}
	public void setTemplate(String template) {
		Template = template;
	}
	public int getBizId() {
		return BizId;
	}
	public void setBizId(int bizId) {
		BizId = bizId;
	}

}
