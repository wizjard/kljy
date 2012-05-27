package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

import java.util.Date;

public class ReportItemforRemote {

	private int id;
	private Date receiveDate;
	private int sectionNo;
	private int testTypeNo;
	private String sampleNo;
	private int parItemNo;
	private int itemNo;
	private float originalValue;
	private float reportValue;
	private String originalDesc;
	private String reportDesc;
	private int statusNo;
	private String refRange;
	private int equipNo;
	private int modified;
	private Date itemDate;
	private Date itemTime;
	private int isMatch;
	private String resultStatus;
	private String hisValue;
	private String hisComp;
	private int isReceive;
	private String serialNoParent;
	private String countNodesItemSource;
	private String unit;
	private int isAdd;
	private int home_foreign;
	private String equipCommMemo;//数据库中为空的字段，拿来当录入化验单使用，如果医生或者病人录入的结果为0，则使该列不为空
	private String waiyuan_reportValue;//外院结果值存储
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public float getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(float originalValue) {
		this.originalValue = originalValue;
	}
	public float getReportValue() {
		return reportValue;
	}
	public void setReportValue(float reportValue) {
		this.reportValue = reportValue;
	}
	public String getOriginalDesc() {
		return originalDesc;
	}
	public void setOriginalDesc(String originalDesc) {
		this.originalDesc = originalDesc;
	}
	public String getReportDesc() {
		return reportDesc;
	}
	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}
	public int getStatusNo() {
		return statusNo;
	}
	public void setStatusNo(int statusNo) {
		this.statusNo = statusNo;
	}
	public String getRefRange() {
		return refRange;
	}
	public void setRefRange(String refRange) {
		this.refRange = refRange;
	}
	public int getEquipNo() {
		return equipNo;
	}
	public void setEquipNo(int equipNo) {
		this.equipNo = equipNo;
	}
	public int getModified() {
		return modified;
	}
	public void setModified(int modified) {
		this.modified = modified;
	}
	public Date getItemDate() {
		return itemDate;
	}
	public void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
	}
	public Date getItemTime() {
		return itemTime;
	}
	public void setItemTime(Date itemTime) {
		this.itemTime = itemTime;
	}
	public int getIsMatch() {
		return isMatch;
	}
	public void setIsMatch(int isMatch) {
		this.isMatch = isMatch;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getHisValue() {
		return hisValue;
	}
	public void setHisValue(String hisValue) {
		this.hisValue = hisValue;
	}
	public String getHisComp() {
		return hisComp;
	}
	public void setHisComp(String hisComp) {
		this.hisComp = hisComp;
	}
	public int getIsReceive() {
		return isReceive;
	}
	public void setIsReceive(int isReceive) {
		this.isReceive = isReceive;
	}
	public String getSerialNoParent() {
		return serialNoParent;
	}
	public void setSerialNoParent(String serialNoParent) {
		this.serialNoParent = serialNoParent;
	}
	public String getCountNodesItemSource() {
		return countNodesItemSource;
	}
	public void setCountNodesItemSource(String countNodesItemSource) {
		this.countNodesItemSource = countNodesItemSource;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(int isAdd) {
		this.isAdd = isAdd;
	}
	public int getHome_foreign() {
		return home_foreign;
	}
	public void setHome_foreign(int home_foreign) {
		this.home_foreign = home_foreign;
	}
	public String getEquipCommMemo() {
		return equipCommMemo;
	}
	public void setEquipCommMemo(String equipCommMemo) {
		this.equipCommMemo = equipCommMemo;
	}
	public String getWaiyuan_reportValue() {
		return waiyuan_reportValue;
	}
	public void setWaiyuan_reportValue(String waiyuan_reportValue) {
		this.waiyuan_reportValue = waiyuan_reportValue;
	}
	
}
