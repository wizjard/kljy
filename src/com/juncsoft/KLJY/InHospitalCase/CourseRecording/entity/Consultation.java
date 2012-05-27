package com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity;

import java.util.Date;
public class Consultation {
	private Long id;
	private Long caseId;
	private Date time;
	private String name;
	private String patientNo;
	private String gender;
	private String age;
	private String ward;
	private String bedNo;
	private Date apptime;
	private String conLv;
	private String appward;
	private String simpleCase;
	private String conTarget;
	private String conAdv;
	private String recName;
	private Date rectime;
	private Long submiter;
	private Long checker;
	private int verifyStatus;
	
	private String appDoctors;
	private String superDoctors;
	private String invitedWard;
	private String invitedDoctors;
	private String conTarget0;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCaseId() {
		return caseId;
	}
	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPatientNo() {
		return patientNo;
	}
	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
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
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public Date getApptime() {
		return apptime;
	}
	public void setApptime(Date apptime) {
		this.apptime = apptime;
	}
	public String getConLv() {
		return conLv;
	}
	public void setConLv(String conLv) {
		this.conLv = conLv;
	}
	public String getAppward() {
		return appward;
	}
	public void setAppward(String appward) {
		this.appward = appward;
	}
	public String getSimpleCase() {
		return simpleCase;
	}
	public void setSimpleCase(String simpleCase) {
		this.simpleCase = simpleCase;
	}
	public String getConTarget() {
		return conTarget;
	}
	public void setConTarget(String conTarget) {
		this.conTarget = conTarget;
	}
	public String getConAdv() {
		return conAdv;
	}
	public void setConAdv(String conAdv) {
		this.conAdv = conAdv;
	}
	public String getRecName() {
		return recName;
	}
	public void setRecName(String recName) {
		this.recName = recName;
	}
	public Date getRectime() {
		return rectime;
	}
	public void setRectime(Date rectime) {
		this.rectime = rectime;
	}
	public Long getSubmiter() {
		return submiter;
	}
	public void setSubmiter(Long submiter) {
		this.submiter = submiter;
	}
	public Long getChecker() {
		return checker;
	}
	public void setChecker(Long checker) {
		this.checker = checker;
	}
	public int getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public String getAppDoctors() {
		return appDoctors;
	}
	public void setAppDoctors(String appDoctors) {
		this.appDoctors = appDoctors;
	}
	public String getConTarget0() {
		return conTarget0;
	}
	public void setConTarget0(String conTarget0) {
		this.conTarget0 = conTarget0;
	}
	public String getInvitedDoctors() {
		return invitedDoctors;
	}
	public void setInvitedDoctors(String invitedDoctors) {
		this.invitedDoctors = invitedDoctors;
	}
	public String getInvitedWard() {
		return invitedWard;
	}
	public void setInvitedWard(String invitedWard) {
		this.invitedWard = invitedWard;
	}
	public String getSuperDoctors() {
		return superDoctors;
	}
	public void setSuperDoctors(String superDoctors) {
		this.superDoctors = superDoctors;
	}
}
