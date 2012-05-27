package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

public class SampleType {

	private int sampleTypeNo; 
	private String cname; 
	private String shortCode; 
	private int visible; 
	private int dispOrder; 
	private String hisOrderCode; 
	private int stemfrom;
	public int getSampleTypeNo() {
		return sampleTypeNo;
	}
	public void setSampleTypeNo(int sampleTypeNo) {
		this.sampleTypeNo = sampleTypeNo;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
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
	public int getStemfrom() {
		return stemfrom;
	}
	public void setStemfrom(int stemfrom) {
		this.stemfrom = stemfrom;
	}
	
}
