package com.juncsoft.KLJY.InHospitalCase.Fever.entity;

/**
 * TInHspFeverMedicineIncidence entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TInHspFeverMedicineIncidence implements java.io.Serializable {

	// Fields

	private Long id;
	private Long caseId;
	private String time;
	private String timeUnit;
	private String timeUnitSuffix;
	private Integer causesFlag;
	private String medicalexperunit;
	private String medicalquestionDesc;
	private String otherTimeCauses;
	private Integer otherTimeCausesFlag;
	private String dateTime;
	private String oldContent;

	// Constructors

	/** default constructor */
	public TInHspFeverMedicineIncidence() {
	}

	/** minimal constructor */
	public TInHspFeverMedicineIncidence(Long id, Long caseId) {
		this.id = id;
		this.caseId = caseId;
	}

	/** full constructor */
	public TInHspFeverMedicineIncidence(Long id, Long caseId, String time,
			String timeUnit, String timeUnitSuffix, Integer causesFlag,
			String medicalexperunit, String medicalquestionDesc,
			String otherTimeCauses, Integer otherTimeCausesFlag,
			String dateTime, String oldContent) {
		this.id = id;
		this.caseId = caseId;
		this.time = time;
		this.timeUnit = timeUnit;
		this.timeUnitSuffix = timeUnitSuffix;
		this.causesFlag = causesFlag;
		this.medicalexperunit = medicalexperunit;
		this.medicalquestionDesc = medicalquestionDesc;
		this.otherTimeCauses = otherTimeCauses;
		this.otherTimeCausesFlag = otherTimeCausesFlag;
		this.dateTime = dateTime;
		this.oldContent = oldContent;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCaseId() {
		return this.caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTimeUnit() {
		return this.timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public String getTimeUnitSuffix() {
		return this.timeUnitSuffix;
	}

	public void setTimeUnitSuffix(String timeUnitSuffix) {
		this.timeUnitSuffix = timeUnitSuffix;
	}

	public Integer getCausesFlag() {
		return this.causesFlag;
	}

	public void setCausesFlag(Integer causesFlag) {
		this.causesFlag = causesFlag;
	}

	public String getMedicalexperunit() {
		return this.medicalexperunit;
	}

	public void setMedicalexperunit(String medicalexperunit) {
		this.medicalexperunit = medicalexperunit;
	}

	public String getMedicalquestionDesc() {
		return this.medicalquestionDesc;
	}

	public void setMedicalquestionDesc(String medicalquestionDesc) {
		this.medicalquestionDesc = medicalquestionDesc;
	}

	public String getOtherTimeCauses() {
		return this.otherTimeCauses;
	}

	public void setOtherTimeCauses(String otherTimeCauses) {
		this.otherTimeCauses = otherTimeCauses;
	}

	public Integer getOtherTimeCausesFlag() {
		return this.otherTimeCausesFlag;
	}

	public void setOtherTimeCausesFlag(Integer otherTimeCausesFlag) {
		this.otherTimeCausesFlag = otherTimeCausesFlag;
	}

	public String getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getOldContent() {
		return this.oldContent;
	}

	public void setOldContent(String oldContent) {
		this.oldContent = oldContent;
	}

}