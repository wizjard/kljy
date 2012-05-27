package com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistoryNew;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PresentIllnessHistoryN implements Serializable{
	private Long id;
	private Long caseId;
	private String content;
	private String oldOperations;
	private List<PresentIllnessHistory_Cases> prehis_cases = new ArrayList<PresentIllnessHistory_Cases>();
	private List<PresentHistory_MainSymptom> prehis_mainSymptom = new ArrayList<PresentHistory_MainSymptom>();
	private List<PresentHistory_otherPositiveSymptom> prehis_otherPositive = new ArrayList<PresentHistory_otherPositiveSymptom>();
	private List<PresentHistory_sideSymptom> prehis_sideSysptom = new ArrayList<PresentHistory_sideSymptom>();
	private List<PresentHistory_TreatProcess> prehis_treat = new ArrayList<PresentHistory_TreatProcess>();
	private List<PresentHistory_CurrentStatus> prehis_currentStatus = new ArrayList<PresentHistory_CurrentStatus>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<PresentIllnessHistory_Cases> getPrehis_cases() {
		return prehis_cases;
	}

	public void setPrehis_cases(List<PresentIllnessHistory_Cases> prehis_cases) {
		this.prehis_cases = prehis_cases;
	}

	public List<PresentHistory_MainSymptom> getPrehis_mainSymptom() {
		return prehis_mainSymptom;
	}

	public void setPrehis_mainSymptom(
			List<PresentHistory_MainSymptom> prehis_mainSymptom) {
		this.prehis_mainSymptom = prehis_mainSymptom;
	}

	public List<PresentHistory_otherPositiveSymptom> getPrehis_otherPositive() {
		return prehis_otherPositive;
	}

	public void setPrehis_otherPositive(
			List<PresentHistory_otherPositiveSymptom> prehis_otherPositive) {
		this.prehis_otherPositive = prehis_otherPositive;
	}

	public List<PresentHistory_sideSymptom> getPrehis_sideSysptom() {
		return prehis_sideSysptom;
	}

	public void setPrehis_sideSysptom(
			List<PresentHistory_sideSymptom> prehis_sideSysptom) {
		this.prehis_sideSysptom = prehis_sideSysptom;
	}

	public List<PresentHistory_TreatProcess> getPrehis_treat() {
		return prehis_treat;
	}

	public void setPrehis_treat(List<PresentHistory_TreatProcess> prehis_treat) {
		this.prehis_treat = prehis_treat;
	}

	public List<PresentHistory_CurrentStatus> getPrehis_currentStatus() {
		return prehis_currentStatus;
	}

	public void setPrehis_currentStatus(
			List<PresentHistory_CurrentStatus> prehis_currentStatus) {
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
