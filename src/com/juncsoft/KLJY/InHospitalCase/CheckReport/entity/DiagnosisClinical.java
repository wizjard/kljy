package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

public class DiagnosisClinical {
	private int diagNo; 
	private String cname; 
	private String diagDesc; 
	private String shortCode; 
	private int visible; 
	private int dispOrder; 
	private String hisOrderCode; 
	private int stemfrom;
	public int getDiagNo() {
		return diagNo;
	}
	public void setDiagNo(int diagNo) {
		this.diagNo = diagNo;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getDiagDesc() {
		return diagDesc;
	}
	public void setDiagDesc(String diagDesc) {
		this.diagDesc = diagDesc;
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
