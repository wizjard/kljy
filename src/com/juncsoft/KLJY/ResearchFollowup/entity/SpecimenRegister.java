package com.juncsoft.KLJY.ResearchFollowup.entity;

import java.util.Date;

public class SpecimenRegister{
	private Long id;
	private Long patientId;
	private Long researchId;
	private Date registerDate;
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
	public Long getResearchId() {
		return researchId;
	}
	public void setResearchId(Long researchId) {
		this.researchId = researchId;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
}