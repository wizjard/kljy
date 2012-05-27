package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

import java.sql.Date;

public class ReportItemVo {

	private Date receiveDate;
	private int sectionNo;
	private int testTypeNo;
	private String sampleNo;
	private int parItemNo;
	private int itemNo;
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public int getSectionNo() {
		return sectionNo;
	}
	public void setSectionNo(int sectionNo) {
		this.sectionNo = sectionNo;
	}
	public int getTestTypeNo() {
		return testTypeNo;
	}
	public void setTestTypeNo(int testTypeNo) {
		this.testTypeNo = testTypeNo;
	}
	public String getSampleNo() {
		return sampleNo;
	}
	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}
	public int getParItemNo() {
		return parItemNo;
	}
	public void setParItemNo(int parItemNo) {
		this.parItemNo = parItemNo;
	}
	public int getItemNo() {
		return itemNo;
	}
	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}
	
}
