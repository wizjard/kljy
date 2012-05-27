package com.juncsoft.KLJY.InHospitalCase.Default.entity;

public class SpecialExamination{
	private Long id;
	private Long caseId;
	private String result;
	public Long getCaseId() {
		return caseId;
	}
	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}