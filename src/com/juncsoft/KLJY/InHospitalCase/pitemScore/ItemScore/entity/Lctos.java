package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

public class Lctos {

	private Long id;
	private Long scId;
	private String caseId;
	private String milanStandard;
	private String ucsfStandard;
	private String fiag; //��ʶ,N��סԺ,Y������
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
	public String getMilanStandard() {
		return milanStandard;
	}
	public void setMilanStandard(String milanStandard) {
		this.milanStandard = milanStandard;
	}
	public Long getScId() {
		return scId;
	}
	public void setScId(Long scId) {
		this.scId = scId;
	}
	public String getUcsfStandard() {
		return ucsfStandard;
	}
	public void setUcsfStandard(String ucsfStandard) {
		this.ucsfStandard = ucsfStandard;
	}
	public String getFiag() {
		return fiag;
	}
	public void setFiag(String fiag) {
		this.fiag = fiag;
	}
	
}
