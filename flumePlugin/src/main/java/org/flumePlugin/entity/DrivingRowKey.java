package org.flumePlugin.entity;

/**
 * 驾校表
 *
 */
public class DrivingRowKey {

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
	private String id;
	
	/**
	 * 教练身份证号
	 */
	private String idCard;
	
	/**
	 * 车牌号
	 */
	private String cardNo;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
}
