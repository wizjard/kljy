package com.juncsoft.KLJY.InHospitalCase.Liver.entity;

public class PastHistory_OuterHurt {
	private Long id;
	private PastHistory ph;
	private String hurtTime;
	private String outerHurtTimeUnit;
	private String hurtName;
	private String currentStatu;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PastHistory getPh() {
		return ph;
	}
	public void setPh(PastHistory ph) {
		this.ph = ph;
	}
	public String getHurtTime() {
		return hurtTime;
	}
	public void setHurtTime(String hurtTime) {
		this.hurtTime = hurtTime;
	}
	public String getOuterHurtTimeUnit() {
		return outerHurtTimeUnit;
	}
	public void setOuterHurtTimeUnit(String outerHurtTimeUnit) {
		this.outerHurtTimeUnit = outerHurtTimeUnit;
	}
	public String getHurtName() {
		return hurtName;
	}
	public void setHurtName(String hurtName) {
		this.hurtName = hurtName;
	}
	public String getCurrentStatu() {
		return currentStatu;
	}
	public void setCurrentStatu(String currentStatu) {
		this.currentStatu = currentStatu;
	}
}
