package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

public class Hc {

	private Long id;
	private Long scId;
	private String caseId;
	private String hcbismuth;
	private String hct;
	private String bismuthRst;
	private String hctRst;
	private String fiag; //��ʶ N��סԺ,Y������
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getHcbismuth() {
		return hcbismuth;
	}
	public void setHcbismuth(String hcbismuth) {
		this.hcbismuth = hcbismuth;
	}
	public String getHct() {
		return hct;
	}
	public void setHct(String hct) {
		this.hct = hct;
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
	public String getBismuthRst() {
		return bismuthRst;
	}
	public void setBismuthRst(String bismuthRst) {
		this.bismuthRst = bismuthRst;
	}
	public String getHctRst() {
		return hctRst;
	}
	public void setHctRst(String hctRst) {
		this.hctRst = hctRst;
	}
	public String getFiag() {
		return fiag;
	}
	public void setFiag(String fiag) {
		this.fiag = fiag;
	}
	
}
