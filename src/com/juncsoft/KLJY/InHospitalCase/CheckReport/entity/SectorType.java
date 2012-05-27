package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

public class SectorType {

	private int sectorTypeNo; 
	private String sectorName; 
	private String shortCode; 
	private int dispOrder; 
	private String hisOrderCode; 
	private int stemfrom;
	public int getSectorTypeNo() {
		return sectorTypeNo;
	}
	public void setSectorTypeNo(int sectorTypeNo) {
		this.sectorTypeNo = sectorTypeNo;
	}
	public String getSectorName() {
		return sectorName;
	}
	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
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
