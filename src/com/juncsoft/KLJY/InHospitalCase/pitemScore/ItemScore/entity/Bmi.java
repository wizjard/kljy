package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

public class Bmi {

	private Long id;
	private Long scId;
	private String caseId;
	private String weight;
	private String height;
	private String bodyMi;
	private String clinicalValue;
	private String whoClinicalValue;
    private String fiag; //��ʶ N��סԺ,Y������
    
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

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getBodyMi() {
		return bodyMi;
	}

	public void setBodyMi(String bodyMi) {
		this.bodyMi = bodyMi;
	}

	public String getClinicalValue() {
		return clinicalValue;
	}

	public void setClinicalValue(String clinicalValue) {
		this.clinicalValue = clinicalValue;
	}

	public String getWhoClinicalValue() {
		return whoClinicalValue;
	}

	public void setWhoClinicalValue(String whoClinicalValue) {
		this.whoClinicalValue = whoClinicalValue;
	}

	public String getFiag() {
		return fiag;
	}

	public void setFiag(String fiag) {
		this.fiag = fiag;
	}
	
}
