package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

public class TestType {

	private int testTypeNo;
	private String cname;
	private String testTypeDesc;
	private String shortCode;
	private int visible;
	private int dispOrder;
	private String hisOrderCode;
	public int getTestTypeNo() {
		return testTypeNo;
	}
	public void setTestTypeNo(int testTypeNo) {
		this.testTypeNo = testTypeNo;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getTestTypeDesc() {
		return testTypeDesc;
	}
	public void setTestTypeDesc(String testTypeDesc) {
		this.testTypeDesc = testTypeDesc;
	}
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	public int getVisible() {
		return visible;
	}
	public void setVisible(int visible) {
		this.visible = visible;
	}
	public int getDispOrder() {
		return dispOrder;
	}
	public void setDispOrder(int dispOrder) {
		this.dispOrder = dispOrder;
	}
	public String getHisOrderCode() {
		return hisOrderCode;
	}
	public void setHisOrderCode(String hisOrderCode) {
		this.hisOrderCode = hisOrderCode;
	}
	
}
