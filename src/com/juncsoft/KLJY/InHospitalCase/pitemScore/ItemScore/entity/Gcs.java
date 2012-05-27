package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

public class Gcs {

	private Long id;
	private Long scId;
	private String caseId;
	private String openEyeResult;
	private String openEyeScore;
	private String languageRespResult;
	private String languageRespScore;
	private String moveRespResult;
	private String moveRespScore;
	private String gcsScore;
	private String gcsMeaning;
	private String fiag; //��ʶ N��סԺ,Y������
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getGcsScore() {
		return gcsScore;
	}
	public void setGcsScore(String gcsScore) {
		this.gcsScore = gcsScore;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLanguageRespResult() {
		return languageRespResult;
	}
	public void setLanguageRespResult(String languageRespResult) {
		this.languageRespResult = languageRespResult;
	}
	public String getLanguageRespScore() {
		return languageRespScore;
	}
	public void setLanguageRespScore(String languageRespScore) {
		this.languageRespScore = languageRespScore;
	}
	public String getMoveRespResult() {
		return moveRespResult;
	}
	public void setMoveRespResult(String moveRespResult) {
		this.moveRespResult = moveRespResult;
	}
	public String getMoveRespScore() {
		return moveRespScore;
	}
	public void setMoveRespScore(String moveRespScore) {
		this.moveRespScore = moveRespScore;
	}
	public String getOpenEyeResult() {
		return openEyeResult;
	}
	public void setOpenEyeResult(String openEyeResult) {
		this.openEyeResult = openEyeResult;
	}
	public String getOpenEyeScore() {
		return openEyeScore;
	}
	public void setOpenEyeScore(String openEyeScore) {
		this.openEyeScore = openEyeScore;
	}
	public Long getScId() {
		return scId;
	}
	public void setScId(Long scId) {
		this.scId = scId;
	}
	public String getGcsMeaning() {
		return gcsMeaning;
	}
	public void setGcsMeaning(String gcsMeaning) {
		this.gcsMeaning = gcsMeaning;
	}
	public String getFiag() {
		return fiag;
	}
	public void setFiag(String fiag) {
		this.fiag = fiag;
	}
	
}
