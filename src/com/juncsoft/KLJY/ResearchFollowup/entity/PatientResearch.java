package com.juncsoft.KLJY.ResearchFollowup.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientResearch {
	private Long id;
	private Long patientId;
	private Date startDate;
	private Date endDate;
	private Long researchId;
	private String subGroup;
	private String serialNum;
	private int random;
	private String caseId;
	private Date followupStart;
	private List<Followup> followup=new ArrayList<Followup>();//此处代码已经没有实际用处，兼容前面的内容
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public List<Followup> getFollowup() {
		return followup;
	}
	public void setFollowup(List<Followup> followup) {
		this.followup = followup;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getResearchId() {
		return researchId;
	}
	public void setResearchId(Long researchId) {
		this.researchId = researchId;
	}
	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public int getRandom() {
		return random;
	}
	public void setRandom(int random) {
		this.random = random;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public Date getFollowupStart() {
		return followupStart;
	}
	public void setFollowupStart(Date followupStart) {
		this.followupStart = followupStart;
	}
	
}
