package com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory;

import java.util.ArrayList;
import java.util.List;

public class PresentIllnessHistoryN {
	private Long id;
	private Long caseId;
	private String content;
	//目前状况
	private String spiritStatu;
	private String eatVolume;
	private String sleep;
	private String piss;
	private String excrement;
	private String spiritStatu0;
	private String eatVolume0;
	private String sleep0;
	private String piss0;
	private String excrement0;
	private String bodyWeight;
	private String bodyWeight_time;
	private String bodyWeight_timeUnit;
	private String bodyWeight_kg;
	private String mainSysptomEve;
	private String newSysptom;
	private String otherCurrent;
	
	private String otherdesease;
	
	public String getMainSysptomEve() {
		return mainSysptomEve;
	}

	public void setMainSysptomEve(String mainSysptomEve) {
		this.mainSysptomEve = mainSysptomEve;
	}

	public String getNewSysptom() {
		return newSysptom;
	}

	public void setNewSysptom(String newSysptom) {
		this.newSysptom = newSysptom;
	}

	public String getSpiritStatu() {
		return spiritStatu;
	}

	public void setSpiritStatu(String spiritStatu) {
		this.spiritStatu = spiritStatu;
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

	public String getSpiritStatu0() {
		return spiritStatu0;
	}

	public void setSpiritStatu0(String spiritStatu0) {
		this.spiritStatu0 = spiritStatu0;
	}

	public String getEatVolume0() {
		return eatVolume0;
	}

	public void setEatVolume0(String eatVolume0) {
		this.eatVolume0 = eatVolume0;
	}

	public String getSleep0() {
		return sleep0;
	}

	public void setSleep0(String sleep0) {
		this.sleep0 = sleep0;
	}

	public String getPiss0() {
		return piss0;
	}

	public void setPiss0(String piss0) {
		this.piss0 = piss0;
	}

	public String getExcrement0() {
		return excrement0;
	}

	public void setExcrement0(String excrement0) {
		this.excrement0 = excrement0;
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

	public String getOtherCurrent() {
		return otherCurrent;
	}

	public void setOtherCurrent(String otherCurrent) {
		this.otherCurrent = otherCurrent;
	}

	private List<PresentIllnessHistoryNx> nxs = new ArrayList<PresentIllnessHistoryNx>();

	public void addNx(PresentIllnessHistoryNx nx) {
		nx.setN(this);
		nxs.add(nx);
	}

	public List<PresentIllnessHistoryNx> getNxs() {
		return nxs;
	}

	public void setNxs(List<PresentIllnessHistoryNx> nxs) {
		this.nxs = nxs;
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

	public String getOtherdesease() {
		return otherdesease;
	}

	public void setOtherdesease(String otherdesease) {
		this.otherdesease = otherdesease;
	}
}
