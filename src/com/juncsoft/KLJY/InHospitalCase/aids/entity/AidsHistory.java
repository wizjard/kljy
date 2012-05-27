package com.juncsoft.KLJY.InHospitalCase.aids.entity;

import java.util.ArrayList;
import java.util.List;


public class AidsHistory {

	private Long id;
	private Long caseId;
	private String content;
	private String oldOperations;
	private List<AidsHistory_Cases> aidsHistory_cases = new ArrayList<AidsHistory_Cases>();
	private List<AidsHistory_MainSymptom> aidsHistory_mainSymptom = new ArrayList<AidsHistory_MainSymptom>();
	private List<AidsHistory_otherPositiveSymptom> aidsHistory_otherPositive = new ArrayList<AidsHistory_otherPositiveSymptom>();
	private List<AidsHistory_sideSymptom> aidsHistory_sideSysptom = new ArrayList<AidsHistory_sideSymptom>();
	private List<AidsHistory_TreatProcess> aidsHistory_treat = new ArrayList<AidsHistory_TreatProcess>();
	private List<AidsHistory_CurrentStatus> aidsHistory_currentStatus = new ArrayList<AidsHistory_CurrentStatus>();
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOldOperations() {
		return oldOperations;
	}
	public void setOldOperations(String oldOperations) {
		this.oldOperations = oldOperations;
	}
	public List<AidsHistory_Cases> getAidsHistory_cases() {
		return aidsHistory_cases;
	}
	public void setAidsHistory_cases(List<AidsHistory_Cases> aidsHistory_cases) {
		this.aidsHistory_cases = aidsHistory_cases;
	}
	public List<AidsHistory_MainSymptom> getAidsHistory_mainSymptom() {
		return aidsHistory_mainSymptom;
	}
	public void setAidsHistory_mainSymptom(
			List<AidsHistory_MainSymptom> aidsHistory_mainSymptom) {
		this.aidsHistory_mainSymptom = aidsHistory_mainSymptom;
	}
	public List<AidsHistory_otherPositiveSymptom> getAidsHistory_otherPositive() {
		return aidsHistory_otherPositive;
	}
	public void setAidsHistory_otherPositive(
			List<AidsHistory_otherPositiveSymptom> aidsHistory_otherPositive) {
		this.aidsHistory_otherPositive = aidsHistory_otherPositive;
	}
	public List<AidsHistory_sideSymptom> getAidsHistory_sideSysptom() {
		return aidsHistory_sideSysptom;
	}
	public void setAidsHistory_sideSysptom(
			List<AidsHistory_sideSymptom> aidsHistory_sideSysptom) {
		this.aidsHistory_sideSysptom = aidsHistory_sideSysptom;
	}
	public List<AidsHistory_TreatProcess> getAidsHistory_treat() {
		return aidsHistory_treat;
	}
	public void setAidsHistory_treat(
			List<AidsHistory_TreatProcess> aidsHistory_treat) {
		this.aidsHistory_treat = aidsHistory_treat;
	}
	public List<AidsHistory_CurrentStatus> getAidsHistory_currentStatus() {
		return aidsHistory_currentStatus;
	}
	public void setAidsHistory_currentStatus(
			List<AidsHistory_CurrentStatus> aidsHistory_currentStatus) {
		this.aidsHistory_currentStatus = aidsHistory_currentStatus;
	}
	
}
