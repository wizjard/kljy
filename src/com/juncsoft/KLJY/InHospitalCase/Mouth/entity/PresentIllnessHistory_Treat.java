package com.juncsoft.KLJY.InHospitalCase.Mouth.entity;

public class PresentIllnessHistory_Treat {
	private Long id;
	private PresentIllnessHistory pi;
	private String evolution;
	private String seeDoct;
	private String seeDoct0;
	private String examination;
	private String diagnosis;
	private String diagnosis0;
	private String treatment;
	public String getSeeDoct0() {
		return seeDoct0;
	}
	public void setSeeDoct0(String seeDoct0) {
		this.seeDoct0 = seeDoct0;
	}
	public String getDiagnosis0() {
		return diagnosis0;
	}
	public void setDiagnosis0(String diagnosis0) {
		this.diagnosis0 = diagnosis0;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PresentIllnessHistory getPi() {
		return pi;
	}
	public void setPi(PresentIllnessHistory pi) {
		this.pi = pi;
	}
	public String getEvolution() {
		return evolution;
	}
	public void setEvolution(String evolution) {
		this.evolution = evolution;
	}
	public String getSeeDoct() {
		return seeDoct;
	}
	public void setSeeDoct(String seeDoct) {
		this.seeDoct = seeDoct;
	}
	public String getExamination() {
		return examination;
	}
	public void setExamination(String examination) {
		this.examination = examination;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
}
