package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

public class ChildPugh {

	private Long id;
	private Long scId;
	private String caseId;
	private Long hepaticEp;
	private Long ascites;
	private Long bilirubin;
	private Long albumin;
	private Long prothrombinTime;
	private String totalScore;
	private String scoreGrade;
	private String fiag;  //��ʶ N��סԺ��Y������
	public Long getAlbumin() {
		return albumin;
	}
	public void setAlbumin(Long albumin) {
		this.albumin = albumin;
	}
	public Long getAscites() {
		return ascites;
	}
	public void setAscites(Long ascites) {
		this.ascites = ascites;
	}
	public Long getBilirubin() {
		return bilirubin;
	}
	public void setBilirubin(Long bilirubin) {
		this.bilirubin = bilirubin;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public Long getHepaticEp() {
		return hepaticEp;
	}
	public void setHepaticEp(Long hepaticEp) {
		this.hepaticEp = hepaticEp;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProthrombinTime() {
		return prothrombinTime;
	}
	public void setProthrombinTime(Long prothrombinTime) {
		this.prothrombinTime = prothrombinTime;
	}
	public Long getScId() {
		return scId;
	}
	public void setScId(Long scId) {
		this.scId = scId;
	}
	public String getScoreGrade() {
		return scoreGrade;
	}
	public void setScoreGrade(String scoreGrade) {
		this.scoreGrade = scoreGrade;
	}
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
	public String getFiag() {
		return fiag;
	}
	public void setFiag(String fiag) {
		this.fiag = fiag;
	}
	
}
