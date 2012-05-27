package com.juncsoft.KLJY.InHospitalCase.aids.entity;

public class AidsHistory_Cases {

	private Long id;
	private String time;
	private String timeUnit;
	private String timeUnitSuffix;
	private String causesFlag;
	private String causes;
	private String medicalFind;
	private String medicalexperunit;
	private String medicalquestionDesc;
	private String otherTimeCausesFlag;
	private String otherTimeCauses;
	private AidsHistory aidsHistory;
	public AidsHistory getAidsHistory() {
		return aidsHistory;
	}
	public void setAidsHistory(AidsHistory aidsHistory) {
		this.aidsHistory = aidsHistory;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCauses() {
		return causes;
	}
	public void setCauses(String causes) {
		this.causes = causes;
	}
	public String getCausesFlag() {
		return causesFlag;
	}
	public void setCausesFlag(String causesFlag) {
		this.causesFlag = causesFlag;
	}
	public String getMedicalexperunit() {
		return medicalexperunit;
	}
	public void setMedicalexperunit(String medicalexperunit) {
		this.medicalexperunit = medicalexperunit;
	}
	public String getMedicalFind() {
		return medicalFind;
	}
	public void setMedicalFind(String medicalFind) {
		this.medicalFind = medicalFind;
	}
	public String getMedicalquestionDesc() {
		return medicalquestionDesc;
	}
	public void setMedicalquestionDesc(String medicalquestionDesc) {
		this.medicalquestionDesc = medicalquestionDesc;
	}
	public String getOtherTimeCauses() {
		return otherTimeCauses;
	}
	public void setOtherTimeCauses(String otherTimeCauses) {
		this.otherTimeCauses = otherTimeCauses;
	}
	public String getOtherTimeCausesFlag() {
		return otherTimeCausesFlag;
	}
	public void setOtherTimeCausesFlag(String otherTimeCausesFlag) {
		this.otherTimeCausesFlag = otherTimeCausesFlag;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	public String getTimeUnitSuffix() {
		return timeUnitSuffix;
	}
	public void setTimeUnitSuffix(String timeUnitSuffix) {
		this.timeUnitSuffix = timeUnitSuffix;
	}
	
}
