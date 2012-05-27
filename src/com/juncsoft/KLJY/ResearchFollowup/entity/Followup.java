package com.juncsoft.KLJY.ResearchFollowup.entity;

import java.util.Date;

public class Followup {
	private Long id;
	private Long patientResearchId;
	private Long patientId;
	private String followTimes;
	private String followCycle;
	private String followContent;
	private Date followupPlanDate;
	private Date noticeDate;
	private Date reserveDate;
	private Date comeDate;
	private String remarkContent;
	public Date getFollowupPlanDate() {
		return followupPlanDate;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public void setFollowupPlanDate(Date followupPlanDate) {
		this.followupPlanDate = followupPlanDate;
	}
	public String getRemarkContent() {
		return remarkContent;
	}
	public void setRemarkContent(String remarkContent) {
		this.remarkContent = remarkContent;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPatientResearchId() {
		return patientResearchId;
	}
	public void setPatientResearchId(Long patientResearchId) {
		this.patientResearchId = patientResearchId;
	}
	
	public String getFollowCycle() {
		return followCycle;
	}
	public void setFollowCycle(String followCycle) {
		this.followCycle = followCycle;
	}
	public String getFollowTimes() {
		return followTimes;
	}
	public void setFollowTimes(String followTimes) {
		this.followTimes = followTimes;
	}
	public String getFollowContent() {
		return followContent;
	}
	public void setFollowContent(String followContent) {
		this.followContent = followContent;
	}
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public Date getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}
	public Date getComeDate() {
		return comeDate;
	}
	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}
}
