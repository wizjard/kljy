package com.juncsoft.KLJY.InHospitalCase.Liver.entity;

public class ChiefComplaintSysptom {
	private Long id;
	private ChiefComplaint cc;
	private String sysptom;
	private String accompanyingSysptom;
	private String sysptomTime;
	private String sysptomTimeUnit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChiefComplaint getCc() {
		return cc;
	}

	public void setCc(ChiefComplaint cc) {
		this.cc = cc;
	}

	public String getSysptom() {
		return sysptom;
	}

	public void setSysptom(String sysptom) {
		this.sysptom = sysptom;
	}

	public String getAccompanyingSysptom() {
		return accompanyingSysptom;
	}

	public void setAccompanyingSysptom(String accompanyingSysptom) {
		this.accompanyingSysptom = accompanyingSysptom;
	}

	public String getSysptomTime() {
		return sysptomTime;
	}

	public void setSysptomTime(String sysptomTime) {
		this.sysptomTime = sysptomTime;
	}

	public String getSysptomTimeUnit() {
		return sysptomTimeUnit;
	}

	public void setSysptomTimeUnit(String sysptomTimeUnit) {
		this.sysptomTimeUnit = sysptomTimeUnit;
	}
}
