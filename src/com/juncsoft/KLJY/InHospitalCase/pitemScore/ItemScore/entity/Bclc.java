package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

public class Bclc {

	private Long id;
	private Long scId;
	private String caseId;
	private String pstIllScore;
	private String tumorStage;
	private String okudaStage;
	private String liverFun;
	private String bclcStage;
    private String fiag; //��ʶ N��סԺ��Y������
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

	public String getPstIllScore() {
		return pstIllScore;
	}

	public void setPstIllScore(String pstIllScore) {
		this.pstIllScore = pstIllScore;
	}

	public String getTumorStage() {
		return tumorStage;
	}

	public void setTumorStage(String tumorStage) {
		this.tumorStage = tumorStage;
	}

	public String getOkudaStage() {
		return okudaStage;
	}

	public void setOkudaStage(String okudaStage) {
		this.okudaStage = okudaStage;
	}

	public String getLiverFun() {
		return liverFun;
	}

	public void setLiverFun(String liverFun) {
		this.liverFun = liverFun;
	}

	public String getBclcStage() {
		return bclcStage;
	}

	public void setBclcStage(String bclcStage) {
		this.bclcStage = bclcStage;
	}

	public String getFiag() {
		return fiag;
	}

	public void setFiag(String fiag) {
		this.fiag = fiag;
	}
	
}
