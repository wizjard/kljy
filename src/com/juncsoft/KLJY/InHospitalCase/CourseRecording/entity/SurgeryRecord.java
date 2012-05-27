package com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity;

import java.util.Date;

public class SurgeryRecord{
	private Long id;
	private Long caseId;
	private Date time;
	private String name;
	private String gender;
	private String age;
	private String bedNo;
	private String operatingRoom;
	private String previousSurgeryDiag;
	private String afterSurgeryDiag;
	private String surgeryAdapt;
	private String surgeryName;
	private String surgeryDocName;
	private String surgeryAssistant1;
	private String surgeryAssistant2;
	private Date surgeryFromTime;
	private Date surgeryToTime;
	private String anesthesiaMode;
	private String washNurse;
	private String aroundNurse;
	private String bleedingVolumn;
	private String transBloodVolumn;
	private String surgeryProcess;
	private String surgeryResultSample;
	private String sample2Pathology;
	private String sampleNum;
	private String anesthesiaEffect;
	private String surgeryDocSign;
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
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getOperatingRoom() {
		return operatingRoom;
	}
	public void setOperatingRoom(String operatingRoom) {
		this.operatingRoom = operatingRoom;
	}
	public String getPreviousSurgeryDiag() {
		return previousSurgeryDiag;
	}
	public void setPreviousSurgeryDiag(String previousSurgeryDiag) {
		this.previousSurgeryDiag = previousSurgeryDiag;
	}
	public String getAfterSurgeryDiag() {
		return afterSurgeryDiag;
	}
	public void setAfterSurgeryDiag(String afterSurgeryDiag) {
		this.afterSurgeryDiag = afterSurgeryDiag;
	}
	public String getSurgeryAdapt() {
		return surgeryAdapt;
	}
	public void setSurgeryAdapt(String surgeryAdapt) {
		this.surgeryAdapt = surgeryAdapt;
	}
	public String getSurgeryName() {
		return surgeryName;
	}
	public void setSurgeryName(String surgeryName) {
		this.surgeryName = surgeryName;
	}
	public String getSurgeryDocName() {
		return surgeryDocName;
	}
	public void setSurgeryDocName(String surgeryDocName) {
		this.surgeryDocName = surgeryDocName;
	}
	public String getSurgeryAssistant1() {
		return surgeryAssistant1;
	}
	public void setSurgeryAssistant1(String surgeryAssistant1) {
		this.surgeryAssistant1 = surgeryAssistant1;
	}
	public String getSurgeryAssistant2() {
		return surgeryAssistant2;
	}
	public void setSurgeryAssistant2(String surgeryAssistant2) {
		this.surgeryAssistant2 = surgeryAssistant2;
	}
	public Date getSurgeryFromTime() {
		return surgeryFromTime;
	}
	public void setSurgeryFromTime(Date surgeryFromTime) {
		this.surgeryFromTime = surgeryFromTime;
	}
	public Date getSurgeryToTime() {
		return surgeryToTime;
	}
	public void setSurgeryToTime(Date surgeryToTime) {
		this.surgeryToTime = surgeryToTime;
	}
	public String getAnesthesiaMode() {
		return anesthesiaMode;
	}
	public void setAnesthesiaMode(String anesthesiaMode) {
		this.anesthesiaMode = anesthesiaMode;
	}
	public String getWashNurse() {
		return washNurse;
	}
	public void setWashNurse(String washNurse) {
		this.washNurse = washNurse;
	}
	public String getAroundNurse() {
		return aroundNurse;
	}
	public void setAroundNurse(String aroundNurse) {
		this.aroundNurse = aroundNurse;
	}
	public String getBleedingVolumn() {
		return bleedingVolumn;
	}
	public void setBleedingVolumn(String bleedingVolumn) {
		this.bleedingVolumn = bleedingVolumn;
	}
	public String getTransBloodVolumn() {
		return transBloodVolumn;
	}
	public void setTransBloodVolumn(String transBloodVolumn) {
		this.transBloodVolumn = transBloodVolumn;
	}
	public String getSurgeryProcess() {
		return surgeryProcess;
	}
	public void setSurgeryProcess(String surgeryProcess) {
		this.surgeryProcess = surgeryProcess;
	}
	public String getSurgeryResultSample() {
		return surgeryResultSample;
	}
	public void setSurgeryResultSample(String surgeryResultSample) {
		this.surgeryResultSample = surgeryResultSample;
	}
	public String getSample2Pathology() {
		return sample2Pathology;
	}
	public void setSample2Pathology(String sample2Pathology) {
		this.sample2Pathology = sample2Pathology;
	}
	public String getSampleNum() {
		return sampleNum;
	}
	public void setSampleNum(String sampleNum) {
		this.sampleNum = sampleNum;
	}
	public String getAnesthesiaEffect() {
		return anesthesiaEffect;
	}
	public void setAnesthesiaEffect(String anesthesiaEffect) {
		this.anesthesiaEffect = anesthesiaEffect;
	}
	public String getSurgeryDocSign() {
		return surgeryDocSign;
	}
	public void setSurgeryDocSign(String surgeryDocSign) {
		this.surgeryDocSign = surgeryDocSign;
	}
}