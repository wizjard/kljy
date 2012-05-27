package com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity;

import java.util.Date;

public class PreviousSurgerySummary{
	private Long id;
	private Long caseId;
	private Date time;
	private String name;
	private String gender;
	private String age;
	private Date inhspTime;
	private String diag;
	private String diagBasis;
	private String planSurgery;
	private String surgerySysptom;
	private String anesthesiaMode;
	private Date surgeryTime;
	private String surgeryPrevPrepar;
	private String surgeryAttention;
	private String surgeryAfterDeal;
	private String recName;
	private Long submiter;
	private Long checker;
	private int verifyStatus;
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
	public Date getInhspTime() {
		return inhspTime;
	}
	public void setInhspTime(Date inhspTime) {
		this.inhspTime = inhspTime;
	}
	public String getDiag() {
		return diag;
	}
	public void setDiag(String diag) {
		this.diag = diag;
	}
	public String getDiagBasis() {
		return diagBasis;
	}
	public void setDiagBasis(String diagBasis) {
		this.diagBasis = diagBasis;
	}
	public String getPlanSurgery() {
		return planSurgery;
	}
	public void setPlanSurgery(String planSurgery) {
		this.planSurgery = planSurgery;
	}
	public String getSurgerySysptom() {
		return surgerySysptom;
	}
	public void setSurgerySysptom(String surgerySysptom) {
		this.surgerySysptom = surgerySysptom;
	}
	public String getAnesthesiaMode() {
		return anesthesiaMode;
	}
	public void setAnesthesiaMode(String anesthesiaMode) {
		this.anesthesiaMode = anesthesiaMode;
	}
	public Date getSurgeryTime() {
		return surgeryTime;
	}
	public void setSurgeryTime(Date surgeryTime) {
		this.surgeryTime = surgeryTime;
	}
	public String getSurgeryPrevPrepar() {
		return surgeryPrevPrepar;
	}
	public void setSurgeryPrevPrepar(String surgeryPrevPrepar) {
		this.surgeryPrevPrepar = surgeryPrevPrepar;
	}
	public String getSurgeryAttention() {
		return surgeryAttention;
	}
	public void setSurgeryAttention(String surgeryAttention) {
		this.surgeryAttention = surgeryAttention;
	}
	public String getSurgeryAfterDeal() {
		return surgeryAfterDeal;
	}
	public void setSurgeryAfterDeal(String surgeryAfterDeal) {
		this.surgeryAfterDeal = surgeryAfterDeal;
	}
	public String getRecName() {
		return recName;
	}
	public void setRecName(String recName) {
		this.recName = recName;
	}
}