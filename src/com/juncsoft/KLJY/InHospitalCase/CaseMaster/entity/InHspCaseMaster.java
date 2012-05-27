package com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity;

import java.util.Date;

public class InHspCaseMaster {
	private Long id;
	private Long patientId;
	private String age;
	private int inHspTimes;
	private Date inHspDate;
	private Date inHspDateModify;
	private String inWardCode;
	private String inIlls;
	private String inStatus;
	private Date outDate;
	private String outIlls;
	private String outWardCode;
	private Long responsibleDoc;
	private String currentWordCode;
	private String caseModuleId;
	private int flag;//会员的入会病历
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getCaseModuleId() {
		return caseModuleId;
	}
	public void setCaseModuleId(String caseModuleId) {
		this.caseModuleId = caseModuleId;
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
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public int getInHspTimes() {
		return inHspTimes;
	}
	public void setInHspTimes(int inHspTimes) {
		this.inHspTimes = inHspTimes;
	}
	public Date getInHspDate() {
		return inHspDate;
	}
	public void setInHspDate(Date inHspDate) {
		this.inHspDate = inHspDate;
	}
	public Date getInHspDateModify() {
		return inHspDateModify;
	}
	public void setInHspDateModify(Date inHspDateModify) {
		this.inHspDateModify = inHspDateModify;
	}
	public String getInWardCode() {
		return inWardCode;
	}
	public void setInWardCode(String inWardCode) {
		this.inWardCode = inWardCode;
	}
	public String getInIlls() {
		return inIlls;
	}
	public void setInIlls(String inIlls) {
		this.inIlls = inIlls;
	}
	public String getInStatus() {
		return inStatus;
	}
	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public String getOutIlls() {
		return outIlls;
	}
	public void setOutIlls(String outIlls) {
		this.outIlls = outIlls;
	}
	public String getOutWardCode() {
		return outWardCode;
	}
	public void setOutWardCode(String outWardCode) {
		this.outWardCode = outWardCode;
	}
	public Long getResponsibleDoc() {
		return responsibleDoc;
	}
	public void setResponsibleDoc(Long responsibleDoc) {
		this.responsibleDoc = responsibleDoc;
	}
	public String getCurrentWordCode() {
		return currentWordCode;
	}
	public void setCurrentWordCode(String currentWordCode) {
		this.currentWordCode = currentWordCode;
	}
}
