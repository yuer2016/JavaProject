package org.flumePlugin.entity;

/**
 * 学员表
 *
 */
public class StudentRowKey {

	/**
	 * 区域编码
	 */
	private String area;
	
	/**
	 * 驾校全称
	 */
//	private String name;
	
	/**
	 * 驾校ID
	 */
//	private String id;
	
	/**
	 * 学习开始时间
	 */
	private String beginTime;
	
	/**
	 * 学员身份证号
	 */
	private String idCard;
	
	/**
	 * 所属教练Id
	 */
	private String tid;
	
	/**
	 * 所属教练车车牌号
	 */
	private String cardNo;
	
	/**
	 * 学习过程id
	 */
	private String srId;
	
	/**
	 * 考试科目
	 */
	private String subject;
	
	/**
	 * 申请时间
	 */
	private String applyNote;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
	
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getSrId() {
		return srId;
	}

	public void setSrId(String srId) {
		this.srId = srId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getApplyNote() {
		return applyNote;
	}

	public void setApplyNote(String applyNote) {
		this.applyNote = applyNote;
	}
	
}
