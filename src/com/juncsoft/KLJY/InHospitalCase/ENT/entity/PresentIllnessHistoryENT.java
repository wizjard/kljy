package com.juncsoft.KLJY.InHospitalCase.ENT.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 2010年7月2日 
 * @author XieLiang
 * 耳鼻喉科现病史实体
 */
public class PresentIllnessHistoryENT {
	private Long id;//主键
	private Long caseId;//外键，病历主表ID
	private String content;//组合打印内容
	//目前状况字段
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
	//现病史详细
	private List<PresentIllnessHistoryENTDetails> details; 
	public PresentIllnessHistoryENT(){
		details=new ArrayList<PresentIllnessHistoryENTDetails>();
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public void setBodyWeight_time(String bodyWeightTime) {
		bodyWeight_time = bodyWeightTime;
	}
	public String getBodyWeight_timeUnit() {
		return bodyWeight_timeUnit;
	}
	public void setBodyWeight_timeUnit(String bodyWeightTimeUnit) {
		bodyWeight_timeUnit = bodyWeightTimeUnit;
	}
	public String getBodyWeight_kg() {
		return bodyWeight_kg;
	}
	public void setBodyWeight_kg(String bodyWeightKg) {
		bodyWeight_kg = bodyWeightKg;
	}
	public List<PresentIllnessHistoryENTDetails> getDetails() {
		return details;
	}
	public void setDetails(List<PresentIllnessHistoryENTDetails> details) {
		this.details = details;
	}
}
