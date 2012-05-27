package com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity;

import java.util.Date;

public class PreviousCaseDiscuss{
	private Long id;
	private Long caseId;
	private Date time;
	private String name;
	private String gender;
	private String age;
	private Date inhspTime;
	private Date discussTime;
	private String joinPerson;
	private String discussPlace;
	private String chairPerson;
	private String discussSubject;
	private String discussContent;
	private String surgeryName;
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
	public Date getInhspTime() {
		return inhspTime;
	}
	public void setInhspTime(Date inhspTime) {
		this.inhspTime = inhspTime;
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
	public String getDiscussSubject() {
		return discussSubject;
	}
	public void setDiscussSubject(String discussSubject) {
		this.discussSubject = discussSubject;
	}
	public String getDiscussContent() {
		return discussContent;
	}
	public void setDiscussContent(String discussContent) {
		this.discussContent = discussContent;
	}
	public String getSurgeryName() {
		return surgeryName;
	}
	public void setSurgeryName(String surgeryName) {
		this.surgeryName = surgeryName;
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