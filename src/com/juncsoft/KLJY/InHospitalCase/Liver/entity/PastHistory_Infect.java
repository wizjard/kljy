package com.juncsoft.KLJY.InHospitalCase.Liver.entity;

public class PastHistory_Infect {
	private Long id;
	private PastHistory ph;
	private String happenTime;
	private String infectTimeUnit;
	private String disName;
	private String outCome;
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
	public String getInfectTimeUnit() {
		return infectTimeUnit;
	}
	public void setInfectTimeUnit(String infectTimeUnit) {
		this.infectTimeUnit = infectTimeUnit;
	}
	public String getDisName() {
		return disName;
	}
	public void setDisName(String disName) {
		this.disName = disName;
	}
	public String getOutCome() {
		return outCome;
	}
	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}
}
