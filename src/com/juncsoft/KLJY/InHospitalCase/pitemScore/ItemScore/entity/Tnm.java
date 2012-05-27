package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

public class Tnm {

	private Long id;
	private Long scId;
	private String caseId;
	private String tnmt;
	private String tnmn;
	private String tnmm;
	private String tnmStage;
	private String fiag;  //��ʶ N��סԺ,Y������
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getScId() {
		return scId;
	}
	public void setScId(Long scId) {
		this.scId = scId;
	}
	public String getTnmm() {
		return tnmm;
	}
	public void setTnmm(String tnmm) {
		this.tnmm = tnmm;
	}
	public String getTnmn() {
		return tnmn;
	}
	public void setTnmn(String tnmn) {
		this.tnmn = tnmn;
	}
	public String getTnmStage() {
		return tnmStage;
	}
	public void setTnmStage(String tnmStage) {
		this.tnmStage = tnmStage;
	}
	public String getTnmt() {
		return tnmt;
	}
	public void setTnmt(String tnmt) {
		this.tnmt = tnmt;
	}
	public String getFiag() {
		return fiag;
	}
	public void setFiag(String fiag) {
		this.fiag = fiag;
	}
	
}
