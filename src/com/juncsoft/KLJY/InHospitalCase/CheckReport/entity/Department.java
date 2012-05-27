package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

public class Department {

	private int deptNo;
	private String cname;
	private String shortName;
	private String shortCode;
	private int visible;
	private int dispOrder;
	private String hisOrderCode;
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
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
