package com.juncsoft.KLJY.InHospitalCase.Liver.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChiefComplaint {
	private Long id;
	private Long caseId;
	private Date dataGetDate;
	private String narrator;
	private String narrator0;
	private String reliability;
	private String ccContent;
	private String evolution;
	private String evolAccompanyingSysptoms;
	private String evolASTime;
	private String evolASTimeUnit;
	private List<ChiefComplaintSysptom> sysptoms;

	public void addSysptom(ChiefComplaintSysptom sysptom) {
		sysptom.setCc(this);
		this.sysptoms.add(sysptom);
	}

	public ChiefComplaint() {
		sysptoms = new ArrayList<ChiefComplaintSysptom>();
	}

	public String getNarrator0() {
		return narrator0;
	}

	public void setNarrator0(String narrator0) {
		this.narrator0 = narrator0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public Date getDataGetDate() {
		return dataGetDate;
	}

	public void setDataGetDate(Date dataGetDate) {
		this.dataGetDate = dataGetDate;
	}

	public String getNarrator() {
		return narrator;
	}

	public void setNarrator(String narrator) {
		this.narrator = narrator;
	}

	public String getReliability() {
		return reliability;
	}

	public void setReliability(String reliability) {
		this.reliability = reliability;
	}

	public String getCcContent() {
		return ccContent;
	}

	public void setCcContent(String ccContent) {
		this.ccContent = ccContent;
	}

	public String getEvolution() {
		return evolution;
	}

	public void setEvolution(String evolution) {
		this.evolution = evolution;
	}

	public String getEvolAccompanyingSysptoms() {
		return evolAccompanyingSysptoms;
	}

	public void setEvolAccompanyingSysptoms(String evolAccompanyingSysptoms) {
		this.evolAccompanyingSysptoms = evolAccompanyingSysptoms;
	}

	public String getEvolASTime() {
		return evolASTime;
	}

	public void setEvolASTime(String evolASTime) {
		this.evolASTime = evolASTime;
	}

	public String getEvolASTimeUnit() {
		return evolASTimeUnit;
	}

	public void setEvolASTimeUnit(String evolASTimeUnit) {
		this.evolASTimeUnit = evolASTimeUnit;
	}

	public List<ChiefComplaintSysptom> getSysptoms() {
		return sysptoms;
	}

	public void setSysptoms(List<ChiefComplaintSysptom> sysptoms) {
		this.sysptoms = sysptoms;
	}
}
