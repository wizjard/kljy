package com.juncsoft.KLJY.InHospitalCase.Fever.PresentHistoryNew;

import java.util.ArrayList;
import java.util.List;

public class Fever_PresentIllnessHistoryN {
	private Long id;
	private Long caseId;
	private String content;
	private String oldOperations;
	private List<Fever_PresentIllnessHistory_Cases> prehis_cases = new ArrayList<Fever_PresentIllnessHistory_Cases>();
	private List<Fever_PresentHistory_MainSymptom> prehis_mainSymptom = new ArrayList<Fever_PresentHistory_MainSymptom>();
	private List<Fever_PresentHistory_otherPositiveSymptom> prehis_otherPositive = new ArrayList<Fever_PresentHistory_otherPositiveSymptom>();
	private List<Fever_PresentHistory_sideSymptom> prehis_sideSysptom = new ArrayList<Fever_PresentHistory_sideSymptom>();
	private List<Fever_PresentHistory_TreatProcess> prehis_treat = new ArrayList<Fever_PresentHistory_TreatProcess>();
	private List<Fever_PresentHistory_CurrentStatus> prehis_currentStatus = new ArrayList<Fever_PresentHistory_CurrentStatus>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Fever_PresentIllnessHistory_Cases> getPrehis_cases() {
		return prehis_cases;
	}

	public void setPrehis_cases(List<Fever_PresentIllnessHistory_Cases> prehis_cases) {
		this.prehis_cases = prehis_cases;
	}

	public List<Fever_PresentHistory_MainSymptom> getPrehis_mainSymptom() {
		return prehis_mainSymptom;
	}

	public void setPrehis_mainSymptom(
			List<Fever_PresentHistory_MainSymptom> prehis_mainSymptom) {
		this.prehis_mainSymptom = prehis_mainSymptom;
	}

	public List<Fever_PresentHistory_otherPositiveSymptom> getPrehis_otherPositive() {
		return prehis_otherPositive;
	}

	public void setPrehis_otherPositive(
			List<Fever_PresentHistory_otherPositiveSymptom> prehis_otherPositive) {
		this.prehis_otherPositive = prehis_otherPositive;
	}

	public List<Fever_PresentHistory_sideSymptom> getPrehis_sideSysptom() {
		return prehis_sideSysptom;
	}

	public void setPrehis_sideSysptom(
			List<Fever_PresentHistory_sideSymptom> prehis_sideSysptom) {
		this.prehis_sideSysptom = prehis_sideSysptom;
	}

	public List<Fever_PresentHistory_TreatProcess> getPrehis_treat() {
		return prehis_treat;
	}

	public void setPrehis_treat(List<Fever_PresentHistory_TreatProcess> prehis_treat) {
		this.prehis_treat = prehis_treat;
	}

	public List<Fever_PresentHistory_CurrentStatus> getPrehis_currentStatus() {
		return prehis_currentStatus;
	}

	public void setPrehis_currentStatus(
			List<Fever_PresentHistory_CurrentStatus> prehis_currentStatus) {
		this.prehis_currentStatus = prehis_currentStatus;
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
}
