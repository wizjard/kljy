package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

public class Meld {

	private Long id;
	private Long scId;
	private String caseId;
	private String creatinine;
	private String bilirubin;
	private String inr;
	private Long etiology;
	private String result;
	private String fiag; //��ʶ N��סԺ,Y������
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
	public String getBilirubin() {
		return bilirubin;
	}
	public void setBilirubin(String bilirubin) {
		this.bilirubin = bilirubin;
	}
	public String getCreatinine() {
		return creatinine;
	}
	public void setCreatinine(String creatinine) {
		this.creatinine = creatinine;
	}
	public Long getEtiology() {
		return etiology;
	}
	public void setEtiology(Long etiology) {
		this.etiology = etiology;
	}
	public String getInr() {
		return inr;
	}
	public void setInr(String inr) {
		this.inr = inr;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getFiag() {
		return fiag;
	}
	public void setFiag(String fiag) {
		this.fiag = fiag;
	}
	
}
