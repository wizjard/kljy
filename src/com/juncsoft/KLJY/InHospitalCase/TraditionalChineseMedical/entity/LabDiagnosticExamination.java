package com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity;

public class LabDiagnosticExamination {
	private Long id;
	private Long caseId;
	private String result;
	private LabExamination lab=new LabExamination();
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public LabExamination getLab() {
		return lab;
	}
	public void setLab(LabExamination lab) {
		this.lab = lab;
	}
}
