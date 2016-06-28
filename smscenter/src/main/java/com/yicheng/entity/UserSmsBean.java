package com.yicheng.entity;

public class UserSmsBean {
	
	private  int  smsId;
	
	private int  userId;
	
    private  int  smsSurplus;
	
	private int  total;

	public int getSmsId() {
		return smsId;
	}

	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSmsSurplus() {
		return smsSurplus;
	}

	public void setSmsSurplus(int smsSurplus) {
		this.smsSurplus = smsSurplus;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
