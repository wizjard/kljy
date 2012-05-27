package com.juncsoft.KLJY.InHospitalCase.Liver.entity;

public class PastHistory_NoInfect {
	private Long id;
	private PastHistory ph;
	private String happenTime;
	private String noInfectTimeUnit;
	private String disName;
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
	public String getHappenTime() {
		return happenTime;
	}
	public void setHappenTime(String happenTime) {
		this.happenTime = happenTime;
	}
	public String getNoInfectTimeUnit() {
		return noInfectTimeUnit;
	}
	public void setNoInfectTimeUnit(String noInfectTimeUnit) {
		this.noInfectTimeUnit = noInfectTimeUnit;
	}
	public String getDisName() {
		return disName;
	}
	public void setDisName(String disName) {
		this.disName = disName;
	}
	public String getCurrentStatu() {
		return currentStatu;
	}
	public void setCurrentStatu(String currentStatu) {
		this.currentStatu = currentStatu;
	}
}
