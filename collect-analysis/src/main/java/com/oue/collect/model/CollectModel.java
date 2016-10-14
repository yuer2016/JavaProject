package com.oue.collect.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * 采集数据model类
 * @author yuer
 * */
public class CollectModel {
	@NotNull
	@Length(min=10,max=12)
	private String  eid; //企业编号
	@NotNull
	@Length(min=17,max=20)
	private String  mid;//机台编号
	@NotNull
	private String am;//采集器编号
	@NotNull
	@Length(max = 14 , min = 14)
	private String tm;//时间
	@Min(0)
	private int  ct;//产量、增量
	
	private int  st;//采集器状态
	@Min(0)
	private int  r0;//开的周期
	@Min(0)
	private int  r1;//闭的周期
	
	private String entid; //数据采集结果 企业ID
	
	private String cycle; //数据采集结果  机台周期
	

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public int getCt() {
		return ct;
	}

	public void setCt(int ct) {
		this.ct = ct;
	}

	public int getSt() {
		return st;
	}

	public void setSt(int st) {
		this.st = st;
	}

	public int getR0() {
		return r0;
	}

	public void setR0(int r0) {
		this.r0 = r0;
	}

	public int getR1() {
		return r1;
	}

	public void setR1(int r1) {
		this.r1 = r1;
	}

	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}

	public String getTm() {
		return tm;
	}

	public void setTm(String tm) {
		this.tm = tm;
	}
	
	public String getEntid() {
		return entid;
	}

	public void setEntid(String entid) {
		this.entid = entid;
	}
	
}
