package com.juncsoft.KLJY.InHospitalCase.Mouth.entity;

import java.util.ArrayList;
import java.util.List;

public class PresentIllnessHistory{
	private Long id;
	private Long caseId;
	private String content1;
	private String content2;
	private List<PresentIllnessHistory_OnSet> onsets;
	private List<PresentIllnessHistory_Treat> treats;
	private PresentIllnessHistory_This illThis;
	public PresentIllnessHistory(){
		onsets=new ArrayList<PresentIllnessHistory_OnSet>();
		treats=new ArrayList<PresentIllnessHistory_Treat>();
	}
	public PresentIllnessHistory_This getIllThis() {
		return illThis;
	}
	public void setIllThis(PresentIllnessHistory_This illThis) {
		this.illThis = illThis;
	}
	public List<PresentIllnessHistory_OnSet> getOnsets() {
		return onsets;
	}
	public void setOnsets(List<PresentIllnessHistory_OnSet> onsets) {
		this.onsets = onsets;
	}
	public List<PresentIllnessHistory_Treat> getTreats() {
		return treats;
	}
	public void setTreats(List<PresentIllnessHistory_Treat> treats) {
		this.treats = treats;
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
	public String getContent1() {
		return content1;
	}
	public void setContent1(String content1) {
		this.content1 = content1;
	}
	public String getContent2() {
		return content2;
	}
	public void setContent2(String content2) {
		this.content2 = content2;
	}
}