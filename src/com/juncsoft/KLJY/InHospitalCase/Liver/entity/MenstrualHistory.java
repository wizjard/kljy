package com.juncsoft.KLJY.InHospitalCase.Liver.entity;

import java.util.Date;

public class MenstrualHistory {
	private Long id;
	private Long caseId;
	private String menstrualDesc;
	private String menarcheAge;
	private String menstrualCycleFrom;
	private String menstrualCycleTo;
	private String menstrualPeriodFrom;
	private String menstrualPeriodTo;
	private Date lastMenstrualDate;
	private String menopauseAge;
	private String menstrualVolume;
	private String menstrualDate;
	private String painfulMenstruation;
	private String otherFlag;
	private String otherMenstFlag;
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
	public String getMenstrualDesc() {
		return menstrualDesc;
	}
	public void setMenstrualDesc(String menstrualDesc) {
		this.menstrualDesc = menstrualDesc;
	}
	public String getMenarcheAge() {
		return menarcheAge;
	}
	public void setMenarcheAge(String menarcheAge) {
		this.menarcheAge = menarcheAge;
	}
	public String getMenstrualCycleFrom() {
		return menstrualCycleFrom;
	}
	public void setMenstrualCycleFrom(String menstrualCycleFrom) {
		this.menstrualCycleFrom = menstrualCycleFrom;
	}
	public String getMenstrualCycleTo() {
		return menstrualCycleTo;
	}
	public void setMenstrualCycleTo(String menstrualCycleTo) {
		this.menstrualCycleTo = menstrualCycleTo;
	}
	public String getMenstrualPeriodFrom() {
		return menstrualPeriodFrom;
	}
	public void setMenstrualPeriodFrom(String menstrualPeriodFrom) {
		this.menstrualPeriodFrom = menstrualPeriodFrom;
	}
	public String getMenstrualPeriodTo() {
		return menstrualPeriodTo;
	}
	public void setMenstrualPeriodTo(String menstrualPeriodTo) {
		this.menstrualPeriodTo = menstrualPeriodTo;
	}
	public Date getLastMenstrualDate() {
		return lastMenstrualDate;
	}
	public void setLastMenstrualDate(Date lastMenstrualDate) {
		this.lastMenstrualDate = lastMenstrualDate;
	}
	public String getMenopauseAge() {
		return menopauseAge;
	}
	public void setMenopauseAge(String menopauseAge) {
		this.menopauseAge = menopauseAge;
	}
	public String getMenstrualVolume() {
		return menstrualVolume;
	}
	public void setMenstrualVolume(String menstrualVolume) {
		this.menstrualVolume = menstrualVolume;
	}
	public String getMenstrualDate() {
		return menstrualDate;
	}
	public void setMenstrualDate(String menstrualDate) {
		this.menstrualDate = menstrualDate;
	}
	public String getPainfulMenstruation() {
		return painfulMenstruation;
	}
	public void setPainfulMenstruation(String painfulMenstruation) {
		this.painfulMenstruation = painfulMenstruation;
	}
	public String getOtherFlag() {
		return otherFlag;
	}
	public void setOtherFlag(String otherFlag) {
		this.otherFlag = otherFlag;
	}
	public String getOtherMenstFlag() {
		return otherMenstFlag;
	}
	public void setOtherMenstFlag(String otherMenstFlag) {
		this.otherMenstFlag = otherMenstFlag;
	}
}
