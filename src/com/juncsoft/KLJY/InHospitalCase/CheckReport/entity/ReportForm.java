package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

import java.util.Date;

public class ReportForm {

	private int id;
	private Date receiveDate;
	private int sectionNo; 
	private int testTypeNo; 
	private String sampleNo; 
	private int statusNo; 
	private int sampleTypeNo; 
	private String patNo; 
	private String cname; 
	private int genderNo; 
	private Date birthday; 
	private float age; 
	private int ageUnitNo; 
	private int folkNo; 
	private int districtNo;
	private int wardNo; 
	private String bed; 
	private int deptNo; 
	private String doctor; 
	private int chargeNo; 
	private float charge; 
	private String collecter;
	private Date collectDate;
	private Date collectTime;
	private String formMemo;
	private String technician;
	private Date testTime;
	private Date testDate;
	private String operator;
	private Date operDate;
	private Date operTime;
	private String checker;
	private Date checkDate;
	private Date checkTime;
	private String serialNo;
	private String requestSource;
	private int diagNo;
	private int printTimes;
	
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
	public int getStatusNo() {
		return statusNo;
	}
	public void setStatusNo(int statusNo) {
		this.statusNo = statusNo;
	}
	public int getSampleTypeNo() {
		return sampleTypeNo;
	}
	public void setSampleTypeNo(int sampleTypeNo) {
		this.sampleTypeNo = sampleTypeNo;
	}
	public String getPatNo() {
		return patNo;
	}
	public void setPatNo(String patNo) {
		this.patNo = patNo;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public int getGenderNo() {
		return genderNo;
	}
	public void setGenderNo(int genderNo) {
		this.genderNo = genderNo;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public float getAge() {
		return age;
	}
	public void setAge(float age) {
		this.age = age;
	}
	public int getAgeUnitNo() {
		return ageUnitNo;
	}
	public void setAgeUnitNo(int ageUnitNo) {
		this.ageUnitNo = ageUnitNo;
	}
	public int getFolkNo() {
		return folkNo;
	}
	public void setFolkNo(int folkNo) {
		this.folkNo = folkNo;
	}
	public int getDistrictNo() {
		return districtNo;
	}
	public void setDistrictNo(int districtNo) {
		this.districtNo = districtNo;
	}
	public int getWardNo() {
		return wardNo;
	}
	public void setWardNo(int wardNo) {
		this.wardNo = wardNo;
	}
	public String getBed() {
		return bed;
	}
	public void setBed(String bed) {
		this.bed = bed;
	}
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public int getChargeNo() {
		return chargeNo;
	}
	public void setChargeNo(int chargeNo) {
		this.chargeNo = chargeNo;
	}
	
	public float getCharge() {
		return charge;
	}
	public void setCharge(float charge) {
		this.charge = charge;
	}
	public String getCollecter() {
		return collecter;
	}
	public void setCollecter(String collecter) {
		this.collecter = collecter;
	}
	public Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getFormMemo() {
		return formMemo;
	}
	public void setFormMemo(String formMemo) {
		this.formMemo = formMemo;
	}
	public String getTechnician() {
		return technician;
	}
	public void setTechnician(String technician) {
		this.technician = technician;
	}
	public Date getTestTime() {
		return testTime;
	}
	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperDate() {
		return operDate;
	}
	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getRequestSource() {
		return requestSource;
	}
	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}
	public int getDiagNo() {
		return diagNo;
	}
	public void setDiagNo(int diagNo) {
		this.diagNo = diagNo;
	}
	public int getPrintTimes() {
		return printTimes;
	}
	public void setPrintTimes(int printTimes) {
		this.printTimes = printTimes;
	}
	
}
