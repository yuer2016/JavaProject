package org.flumePlugin.entity;

/**
 * 从业人员表
 *
 */
public class EmployRowKey {
	
	/**
	 * 区域编码
	 */
	private String area;
	
	/**
	 * 从业人员身份证号
	 */
	private String idCard;
	
	/**
	 * 从业资格证号
	 */
	private String certificate;
	
	/**
	 * 从业资格类别
	 */
	private String professionType;
	
	/**
	 * 继续教育过程开始时间
	 */
	private String startPlayTime;
	
	/**
	 * 继续教育过程结束时间
	 */
	private String playTime;
	
	/**
	 * 考试行业
	 */
	private String professionName;
	
	/**
	 * 学员考试开始时间
	 */
	private String createDate;
	
	/**
	 * 学员考试结束时间
	 */
	private String endExamTime;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getProfessionType() {
		return professionType;
	}

	public void setProfessionType(String professionType) {
		this.professionType = professionType;
	}

	public String getStartPlayTime() {
		return startPlayTime;
	}

	public void setStartPlayTime(String startPlayTime) {
		this.startPlayTime = startPlayTime;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEndExamTime() {
		return endExamTime;
	}

	public void setEndExamTime(String endExamTime) {
		this.endExamTime = endExamTime;
	}
	
}
