package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

import java.util.Date;

public class ScoreComment {

	private Long id;
	private Long ssmId;
	private String caseId;
	private Date scoreTime;
	private Long scoreStage;
	private Long doc1;
	private Long doc2;
	private Long doc3;
	private String fiag;  //��ʶ N��סԺ,Y������
	public Long getDoc1() {
		return doc1;
	}
	public void setDoc1(Long doc1) {
		this.doc1 = doc1;
	}
	public Long getDoc2() {
		return doc2;
	}
	public void setDoc2(Long doc2) {
		this.doc2 = doc2;
	}
	public Long getDoc3() {
		return doc3;
	}
	public void setDoc3(Long doc3) {
		this.doc3 = doc3;
	}
	
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
	public Long getScoreStage() {
		return scoreStage;
	}
	public void setScoreStage(Long scoreStage) {
		this.scoreStage = scoreStage;
	}
	public Date getScoreTime() {
		return scoreTime;
	}
	public void setScoreTime(Date scoreTime) {
		this.scoreTime = scoreTime;
	}
	public Long getSsmId() {
		return ssmId;
	}
	public void setSsmId(Long ssmId) {
		this.ssmId = ssmId;
	}
	public String getFiag() {
		return fiag;
	}
	public void setFiag(String fiag) {
		this.fiag = fiag;
	}
	
}
