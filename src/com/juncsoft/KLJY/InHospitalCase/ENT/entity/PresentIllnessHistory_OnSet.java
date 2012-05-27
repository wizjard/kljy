package com.juncsoft.KLJY.InHospitalCase.ENT.entity;
/**
 * 起病情况 一对多
 * @author Administrator
 */
public class PresentIllnessHistory_OnSet {
	private Long id;
	private PresentIllnessHistory pi;
	private ChiefComplaintSysptom ccs;
	private String illTime;
	private String illTimeUnit;
	private String causes;
	private String predisposingfactors;
	private String sysptomMain;
	private String sysptomSide;
	private String evolution;
	private String evolution0;
	private String seeDoct;
	private String seeDoct0;
	private String examination;
	private String diagnosis;
	private String diagnosis0;
	private String treatment;
	
	public PresentIllnessHistory_OnSet(){
		
	}
	public Long getId() {
		return id;
	}
	public String getEvolution0() {
		return evolution0;
	}
	public void setEvolution0(String evolution0) {
		this.evolution0 = evolution0;
	}
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
	public void setId(Long id) {
		this.id = id;
	}
	public PresentIllnessHistory getPi() {
		return pi;
	}
	public void setPi(PresentIllnessHistory pi) {
		this.pi = pi;
	}
	public String getIllTime() {
		return illTime;
	}
	public ChiefComplaintSysptom getCcs() {
		return ccs;
	}
	public void setCcs(ChiefComplaintSysptom ccs) {
		this.ccs = ccs;
	}
	public void setIllTime(String illTime) {
		this.illTime = illTime;
	}
	public String getIllTimeUnit() {
		return illTimeUnit;
	}
	public void setIllTimeUnit(String illTimeUnit) {
		this.illTimeUnit = illTimeUnit;
	}
	public String getCauses() {
		return causes;
	}
	public String getPredisposingfactors() {
		return predisposingfactors;
	}
	public void setPredisposingfactors(String predisposingfactors) {
		this.predisposingfactors = predisposingfactors;
	}
	public void setCauses(String causes) {
		this.causes = causes;
	}
	public String getSysptomMain() {
		return sysptomMain;
	}
	public void setSysptomMain(String sysptomMain) {
		this.sysptomMain = sysptomMain;
	}
	public String getSysptomSide() {
		return sysptomSide;
	}
	public void setSysptomSide(String sysptomSide) {
		this.sysptomSide = sysptomSide;
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
