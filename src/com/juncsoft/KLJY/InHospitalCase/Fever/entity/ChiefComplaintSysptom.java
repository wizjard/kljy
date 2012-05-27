package com.juncsoft.KLJY.InHospitalCase.Fever.entity;
/**
 * 肝病主诉
 * @author Administrator
 *
 */
public class ChiefComplaintSysptom {
	private Long id;
	private ChiefComplaint cc;
	private String sysptom;
	private String accompanyingSysptom;
	private String sysptomTime;
	private String sysptomTimeUnit;
	private Integer type;//病例类型0：发热西医1：发热中医

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

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
