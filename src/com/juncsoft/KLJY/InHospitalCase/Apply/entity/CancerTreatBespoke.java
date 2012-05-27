package com.juncsoft.KLJY.InHospitalCase.Apply.entity;

import java.util.Date;

public class CancerTreatBespoke {
	private Long id; // bigint
	private Long patientId;// bigint --病人Id
	private Long historyCaseId;// bigint 病历组件Id
	private String patientName; // varchar(100),--病人姓名
	private String gender;// varchar(20)--病人性别
	private String age;// varchar(50),--病人年龄
	private String patientCaseId;// bigint,--病案号
	private String officeAssort;// varchar(100),--科别
	private String bedNum; // bigint,--床号
	private String ctNum;// bigint,--CT号
	private String mrNum;// bigint,--MR号
	private String joinNum;// bigint,--介入号
	private String diagnose;// ntext,--诊断
	private String joinItemName;// varchar(100),
	private Date checkDate;// datetime,--开始手术时间
	private String doctorName;// varchar(100),--医师
	private Date songDate;// datetime,--提交时间

	public CancerTreatBespoke() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CancerTreatBespoke(Long id, Long patientId, Long historyCaseId,
			String patientName, String gender, String age,
			String patientCaseId, String officeAssort, String bedNum,
			String ctNum, String mrNum, String joinNum, String diagnose,
			String joinItemName, Date checkDate, String doctorName,
			Date songDate) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.historyCaseId = historyCaseId;
		this.patientName = patientName;
		this.gender = gender;
		this.age = age;
		this.patientCaseId = patientCaseId;
		this.officeAssort = officeAssort;
		this.bedNum = bedNum;
		this.ctNum = ctNum;
		this.mrNum = mrNum;
		this.joinNum = joinNum;
		this.diagnose = diagnose;
		this.joinItemName = joinItemName;
		this.checkDate = checkDate;
		this.doctorName = doctorName;
		this.songDate = songDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getHistoryCaseId() {
		return historyCaseId;
	}

	public void setHistoryCaseId(Long historyCaseId) {
		this.historyCaseId = historyCaseId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPatientCaseId() {
		return patientCaseId;
	}

	public void setPatientCaseId(String patientCaseId) {
		this.patientCaseId = patientCaseId;
	}

	public String getOfficeAssort() {
		return officeAssort;
	}

	public void setOfficeAssort(String officeAssort) {
		this.officeAssort = officeAssort;
	}

	public String getBedNum() {
		return bedNum;
	}

	public void setBedNum(String bedNum) {
		this.bedNum = bedNum;
	}

	public String getCtNum() {
		return ctNum;
	}

	public void setCtNum(String ctNum) {
		this.ctNum = ctNum;
	}

	public String getMrNum() {
		return mrNum;
	}

	public void setMrNum(String mrNum) {
		this.mrNum = mrNum;
	}

	public String getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(String joinNum) {
		this.joinNum = joinNum;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getJoinItemName() {
		return joinItemName;
	}

	public void setJoinItemName(String joinItemName) {
		this.joinItemName = joinItemName;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Date getSongDate() {
		return songDate;
	}

	public void setSongDate(Date songDate) {
		this.songDate = songDate;
	}

}