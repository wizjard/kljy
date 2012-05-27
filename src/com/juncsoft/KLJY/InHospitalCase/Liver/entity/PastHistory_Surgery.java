package com.juncsoft.KLJY.InHospitalCase.Liver.entity;

public class PastHistory_Surgery {
	private Long id;
	private PastHistory ph;
	private String surgeryTime;
	private String surgeryTimeUnit;
	private String surgeryName;
	private String surgeryDesc;
	private String surgeryCause;
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
	public String getSurgeryTime() {
		return surgeryTime;
	}
	public void setSurgeryTime(String surgeryTime) {
		this.surgeryTime = surgeryTime;
	}
	public String getSurgeryTimeUnit() {
		return surgeryTimeUnit;
	}
	public void setSurgeryTimeUnit(String surgeryTimeUnit) {
		this.surgeryTimeUnit = surgeryTimeUnit;
	}
	public String getSurgeryName() {
		return surgeryName;
	}
	public void setSurgeryName(String surgeryName) {
		this.surgeryName = surgeryName;
	}
	public String getSurgeryDesc() {
		return surgeryDesc;
	}
	public void setSurgeryDesc(String surgeryDesc) {
		this.surgeryDesc = surgeryDesc;
	}
	public String getSurgeryCause() {
		return surgeryCause;
	}
	public void setSurgeryCause(String surgeryCause) {
		this.surgeryCause = surgeryCause;
	}
	
}
