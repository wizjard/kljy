package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

import java.util.Date;


public class CheckReportInfoForAdd {

	private String receiveDate = null;
	private String checkProject = null; 
	private String hospitalName = null; 
	private String cname = null; 
	private String sectionNo = null; 
	private String sampleNo = null; 
	private String genderNo = null; 
	private String collectDate = null;
	private String age = null;  
	private String testTypeNo = null; 
	private String sampleTypeNo = null; 
	private String patNo = null; 
	private String formMemo = null; 
	private String doctor = null;
	private String deptNo = null;
	private String bed;
	private String diagNo = null;
	private int home_foreign;
	private String id;//liugang
	private Integer isFromOutHospital;//区分本院与外院手动录入的化验单数据 1：表示外院 null 表示 本院，(程序导入lis中的数据)
	private Integer isPatientOrDoctorWriteZanCun;//区分是会员还是责任医生录入，10:表示会员暂存，20:表示医生暂存，11:表示会员提交给责任医生，21:表示医生把自己录入的化验或者病人录入的化验提交归档
	private Date sheHeDate;// 病人提交给责任医生的时间
	private Date guiDangDate;//医生提交该化验单为归档状态的日期
	private Date luRuDate;//录入日期
	private String collecter;//归档者
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getCheckProject() {
		return checkProject;
	}
	public void setCheckProject(String checkProject) {
		this.checkProject = checkProject;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
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
	public String getGenderNo() {
		return genderNo;
	}
	public void setGenderNo(String genderNo) {
		this.genderNo = genderNo;
	}
	public String getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getTestTypeNo() {
		return testTypeNo;
	}
	public void setTestTypeNo(String testTypeNo) {
		this.testTypeNo = testTypeNo;
	}
	public String getSampleTypeNo() {
		return sampleTypeNo;
	}
	public void setSampleTypeNo(String sampleTypeNo) {
		this.sampleTypeNo = sampleTypeNo;
	}
	public String getPatNo() {
		return patNo;
	}
	public void setPatNo(String patNo) {
		this.patNo = patNo;
	}
	public String getFormMemo() {
		return formMemo;
	}
	public void setFormMemo(String formMemo) {
		this.formMemo = formMemo;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
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
	public int getHome_foreign() {
		return home_foreign;
	}
	public void setHome_foreign(int home_foreign) {
		this.home_foreign = home_foreign;
	}
	
	public Integer getIsPatientOrDoctorWriteZanCun() {
		return isPatientOrDoctorWriteZanCun;
	}
	public void setIsPatientOrDoctorWriteZanCun(Integer isPatientOrDoctorWriteZanCun) {
		this.isPatientOrDoctorWriteZanCun = isPatientOrDoctorWriteZanCun;
	}
	public Integer getIsFromOutHospital() {
		return isFromOutHospital;
	}
	public void setIsFromOutHospital(Integer isFromOutHospital) {
		this.isFromOutHospital = isFromOutHospital;
	}
	public Date getSheHeDate() {
		return sheHeDate;
	}
	public void setSheHeDate(Date sheHeDate) {
		this.sheHeDate = sheHeDate;
	}
	public Date getGuiDangDate() {
		return guiDangDate;
	}
	public void setGuiDangDate(Date guiDangDate) {
		this.guiDangDate = guiDangDate;
	}
	public Date getLuRuDate() {
		return luRuDate;
	}
	public void setLuRuDate(Date luRuDate) {
		this.luRuDate = luRuDate;
	}
	public String getCollecter() {
		return collecter;
	}
	public void setCollecter(String collecter) {
		this.collecter = collecter;
	}
	
}
