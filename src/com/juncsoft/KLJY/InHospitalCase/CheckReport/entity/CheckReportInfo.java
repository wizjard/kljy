package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

import java.util.Date;


public class CheckReportInfo {

	private Date receiveDate;//****
	private String hospitalName;
	private String sectionNo;//从PGroup表中取数据//****
	private String sampleNo; //****
	private String testTypeNo; //从TestType表中取数据(项目当中把数据写死到页面了)//****
	private String cname;	
	private String genderNo; 
	private float age; 
	private String patNo; 		
	private String deptNo; //从Department表中取数据
	private String bed; 	  
	private String diagNo;//从Diagnosis表中取数据
	private String doctor;
	private Date collectDate;	
	private Date testDate;
	private String sampleTypeNo; //从SampleType表中取数据	
	private String formMemo;
	private int home_foreign;
	private Integer isFromOutHospital;//区分本院与外院手动录入的化验单数据 1：表示外院 null 表示 本院，(程序导入lis中的数据)
	private String collecter;//归档者
	
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getSectionNo() {
		return sectionNo;
	}
	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}
	public String getSampleNo() {
		return sampleNo;
	}
	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}
	public String getTestTypeNo() {
		return testTypeNo;
	}
	public void setTestTypeNo(String testTypeNo) {
		this.testTypeNo = testTypeNo;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getGenderNo() {
		return genderNo;
	}
	public void setGenderNo(String genderNo) {
		this.genderNo = genderNo;
	}
	public float getAge() {
		return age;
	}
	public void setAge(float age) {
		this.age = age;
	}
	public String getPatNo() {
		return patNo;
	}
	public void setPatNo(String patNo) {
		this.patNo = patNo;
	}
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	public String getBed() {
		return bed;
	}
	public void setBed(String bed) {
		this.bed = bed;
	}
	public String getDiagNo() {
		return diagNo;
	}
	public void setDiagNo(String diagNo) {
		this.diagNo = diagNo;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}
	public String getSampleTypeNo() {
		return sampleTypeNo;
	}
	public void setSampleTypeNo(String sampleTypeNo) {
		this.sampleTypeNo = sampleTypeNo;
	}
	public String getFormMemo() {
		return formMemo;
	}
	public void setFormMemo(String formMemo) {
		this.formMemo = formMemo;
	}
	public int getHome_foreign() {
		return home_foreign;
	}
	public void setHome_foreign(int home_foreign) {
		this.home_foreign = home_foreign;
	}
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public Integer getIsFromOutHospital() {
		return isFromOutHospital;
	}
	public void setIsFromOutHospital(Integer isFromOutHospital) {
		this.isFromOutHospital = isFromOutHospital;
	}
	public String getCollecter() {
		return collecter;
	}
	public void setCollecter(String collecter) {
		this.collecter = collecter;
	}
	
}
