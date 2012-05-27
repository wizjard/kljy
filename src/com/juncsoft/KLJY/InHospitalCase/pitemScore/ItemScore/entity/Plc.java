package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

public class Plc {

	private Long id;
	private Long scId;
	private String caseId;
	private String tumor;
	private String ce1;
	private String ce2;
	private String ce3;
	private String ce4;
	private String ce5;
	private String ce6;
	private String cln;
	private String pm;
	private String cgrade;
	private String stage;
	private String fiag; //��ʶ  N��סԺ,Y������
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getCe1() {
		return ce1;
	}
	public void setCe1(String ce1) {
		this.ce1 = ce1;
	}
	public String getCe2() {
		return ce2;
	}
	public void setCe2(String ce2) {
		this.ce2 = ce2;
	}
	public String getCe3() {
		return ce3;
	}
	public void setCe3(String ce3) {
		this.ce3 = ce3;
	}
	public String getCe4() {
		return ce4;
	}
	public void setCe4(String ce4) {
		this.ce4 = ce4;
	}
	public String getCe5() {
		return ce5;
	}
	public void setCe5(String ce5) {
		this.ce5 = ce5;
	}
	public String getCe6() {
		return ce6;
	}
	public void setCe6(String ce6) {
		this.ce6 = ce6;
	}
	public String getCgrade() {
		return cgrade;
	}
	public void setCgrade(String cgrade) {
		this.cgrade = cgrade;
	}
	public String getCln() {
		return cln;
	}
	public void setCln(String cln) {
		this.cln = cln;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPm() {
		return pm;
	}
	public void setPm(String pm) {
		this.pm = pm;
	}
	public Long getScId() {
		return scId;
	}
	public void setScId(Long scId) {
		this.scId = scId;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getTumor() {
		return tumor;
	}
	public void setTumor(String tumor) {
		this.tumor = tumor;
	}
	public String getFiag() {
		return fiag;
	}
	public void setFiag(String fiag) {
		this.fiag = fiag;
	}
	
}
