package com.juncsoft.KLJY.InHospitalCase.ENT.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * 现病史(目前情况，发病记录)
 * @author Administrator
 *
 */
public class PresentIllnessHistory {
	private Long id;
	private Long caseId;
	private String content1;
	
	private String spiritStatu;
	private String positiveSysptom;
	private String eatVolume;
	private String sleep;
	private String piss;
	private String excrement;
	private String bodyWeight;
	private String bodyWeight_time;
	private String bodyWeight_timeUnit;
	private String bodyWeight_kg;
	private List<PresentIllnessHistory_OnSet> onsets;
	
	public PresentIllnessHistory(){
		onsets=new ArrayList<PresentIllnessHistory_OnSet>();
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
	public List<PresentIllnessHistory_OnSet> getOnsets() {
		return onsets;
	}
	public void setOnsets(List<PresentIllnessHistory_OnSet> onsets) {
		this.onsets = onsets;
	}
	public String getSpiritStatu() {
		return spiritStatu;
	}
	public void setSpiritStatu(String spiritStatu) {
		this.spiritStatu = spiritStatu;
	}
	public String getPositiveSysptom() {
		return positiveSysptom;
	}
	public void setPositiveSysptom(String positiveSysptom) {
		this.positiveSysptom = positiveSysptom;
	}
	public String getEatVolume() {
		return eatVolume;
	}
	public void setEatVolume(String eatVolume) {
		this.eatVolume = eatVolume;
	}
	public String getSleep() {
		return sleep;
	}
	public void setSleep(String sleep) {
		this.sleep = sleep;
	}
	public String getPiss() {
		return piss;
	}
	public void setPiss(String piss) {
		this.piss = piss;
	}
	public String getExcrement() {
		return excrement;
	}
	public void setExcrement(String excrement) {
		this.excrement = excrement;
	}
	public String getBodyWeight() {
		return bodyWeight;
	}
	public void setBodyWeight(String bodyWeight) {
		this.bodyWeight = bodyWeight;
	}
	public String getBodyWeight_time() {
		return bodyWeight_time;
	}
	public void setBodyWeight_time(String bodyWeight_time) {
		this.bodyWeight_time = bodyWeight_time;
	}
	public String getBodyWeight_timeUnit() {
		return bodyWeight_timeUnit;
	}
	public void setBodyWeight_timeUnit(String bodyWeight_timeUnit) {
		this.bodyWeight_timeUnit = bodyWeight_timeUnit;
	}
	public String getBodyWeight_kg() {
		return bodyWeight_kg;
	}
	public void setBodyWeight_kg(String bodyWeight_kg) {
		this.bodyWeight_kg = bodyWeight_kg;
	}
}
