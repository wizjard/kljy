package com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity;

import java.util.Date;

public class DeathCaseDiscuss{
	private Long id;
	private Long caseId;
	private Date time;
	private String name;
	private String gender;
	private String age;
	private String ward;
	private Date discussTime;
	private String joinPerson;
	private String discussPlace;
	private String chairPerson;
	private String caseSummary;
	private String discussContent;
	private String deathReason;
	private String deathDiag;
	private String discussResult;
	private String recName;
	private Long submiter;
	private Long checker;
	private int verifyStatus;
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
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public Date getDiscussTime() {
		return discussTime;
	}
	public void setDiscussTime(Date discussTime) {
		this.discussTime = discussTime;
	}
	public String getJoinPerson() {
		return joinPerson;
	}
	public void setJoinPerson(String joinPerson) {
		this.joinPerson = joinPerson;
	}
	public String getDiscussPlace() {
		return discussPlace;
	}
	public void setDiscussPlace(String discussPlace) {
		this.discussPlace = discussPlace;
	}
	public String getChairPerson() {
		return chairPerson;
	}
	public void setChairPerson(String chairPerson) {
		this.chairPerson = chairPerson;
	}
	public String getCaseSummary() {
		return caseSummary;
	}
	public void setCaseSummary(String caseSummary) {
		this.caseSummary = caseSummary;
	}
	public String getDiscussContent() {
		return discussContent;
	}
	public void setDiscussContent(String discussContent) {
		this.discussContent = discussContent;
	}
	public String getDeathReason() {
		return deathReason;
	}
	public void setDeathReason(String deathReason) {
		this.deathReason = deathReason;
	}
	public String getDeathDiag() {
		return deathDiag;
	}
	public void setDeathDiag(String deathDiag) {
		this.deathDiag = deathDiag;
	}
	public String getDiscussResult() {
		return discussResult;
	}
	public void setDiscussResult(String discussResult) {
		this.discussResult = discussResult;
	}
	public String getRecName() {
		return recName;
	}
	public void setRecName(String recName) {
		this.recName = recName;
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
}