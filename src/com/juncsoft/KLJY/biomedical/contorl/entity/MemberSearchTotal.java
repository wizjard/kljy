package com.juncsoft.KLJY.biomedical.contorl.entity;

import java.sql.Date;

public class MemberSearchTotal {
   private String patientName;
   private String patientNo;
   private Long id;
   private Date inDate;
   private String inWard;
   private String deptName;
   private String currentGroup;
   private String inDoctor;
   private Long MemberNo;
   private String doctor;
   private Long accessCount;//咨询次数
   private Long answerberCount;
   private Long contentberCount;
   private String groupName;
   private Long inHspTimes;
   private Long outCount;
   private Long outFollowTimes;
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientNo() {
		return patientNo;
	}
	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public String getInWard() {
		return inWard;
	}
	public void setInWard(String inWard) {
		this.inWard = inWard;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getCurrentGroup() {
		return currentGroup;
	}
	public void setCurrentGroup(String currentGroup) {
		this.currentGroup = currentGroup;
	}
	public String getInDoctor() {
		return inDoctor;
	}
	public void setInDoctor(String inDoctor) {
		this.inDoctor = inDoctor;
	}
	public Long getMemberNo() {
		return MemberNo;
	}
	public void setMemberNo(Long memberNo) {
		MemberNo = memberNo;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	
	public Long getAccessCount() {
		return accessCount;
	}
	public void setAccessCount(Long accessCount) {
		this.accessCount = accessCount;
	}
	public Long getAnswerberCount() {
		return answerberCount;
	}
	public void setAnswerberCount(Long answerberCount) {
		this.answerberCount = answerberCount;
	}
	public Long getContentberCount() {
		return contentberCount;
	}
	public void setContentberCount(Long contentberCount) {
		this.contentberCount = contentberCount;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getInHspTimes() {
		return inHspTimes;
	}
	public void setInHspTimes(Long inHspTimes) {
		this.inHspTimes = inHspTimes;
	}
	public Long getOutCount() {
		return outCount;
	}
	public void setOutCount(Long outCount) {
		this.outCount = outCount;
	}
	public Long getOutFollowTimes() {
		return outFollowTimes;
	}
	public void setOutFollowTimes(Long outFollowTimes) {
		this.outFollowTimes = outFollowTimes;
	}


	   
	}
