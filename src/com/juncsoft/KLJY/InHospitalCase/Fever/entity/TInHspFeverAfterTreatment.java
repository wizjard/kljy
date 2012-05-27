package com.juncsoft.KLJY.InHospitalCase.Fever.entity;

/**
 * TInHspFeverAfterTreatment entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TInHspFeverAfterTreatment implements java.io.Serializable {

	// Fields

	private Long id;
	private Long caseId;
	private Integer treatProcessFlag;
	private String treatProcessDesc;
	private String unitTimeTypeDesc;
	private String inspectionUnit;
	private String treatTime;
	private Integer treatTimeUnit;
	private Integer treatTimeUnitSuffix;
	private Integer treatFn;
	private String treatFn0;
	private Integer checkRstFlag;
	private String checkRst;
	private Integer diagRstFlag;
	private String diagRst;
	private String diagRst0;
	private Integer treatFlag;
	private String treat;
	private String treatRst;
	private String oldContent;
	// Constructors

	public String getOldContent() {
		return oldContent;
	}

	public void setOldContent(String oldContent) {
		this.oldContent = oldContent;
	}

	/** default constructor */
	public TInHspFeverAfterTreatment() {
	}

	/** minimal constructor */
	public TInHspFeverAfterTreatment(Long id, Long caseId) {
		this.id = id;
		this.caseId = caseId;
	}

	/** full constructor */
	public TInHspFeverAfterTreatment(Long id, Long caseId,
			Integer treatProcessFlag, String treatProcessDesc,
			String unitTimeTypeDesc, String inspectionUnit, String treatTime,
			Integer treatTimeUnit, Integer treatTimeUnitSuffix,
			Integer treatFn, String treatFn0, Integer checkRstFlag,
			String checkRst, Integer diagRstFlag, String diagRst,
			String diagRst0, Integer treatFlag, String treat, String treatRst) {
		this.id = id;
		this.caseId = caseId;
		this.treatProcessFlag = treatProcessFlag;
		this.treatProcessDesc = treatProcessDesc;
		this.unitTimeTypeDesc = unitTimeTypeDesc;
		this.inspectionUnit = inspectionUnit;
		this.treatTime = treatTime;
		this.treatTimeUnit = treatTimeUnit;
		this.treatTimeUnitSuffix = treatTimeUnitSuffix;
		this.treatFn = treatFn;
		this.treatFn0 = treatFn0;
		this.checkRstFlag = checkRstFlag;
		this.checkRst = checkRst;
		this.diagRstFlag = diagRstFlag;
		this.diagRst = diagRst;
		this.diagRst0 = diagRst0;
		this.treatFlag = treatFlag;
		this.treat = treat;
		this.treatRst = treatRst;
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

	public Integer getTreatProcessFlag() {
		return this.treatProcessFlag;
	}

	public void setTreatProcessFlag(Integer treatProcessFlag) {
		this.treatProcessFlag = treatProcessFlag;
	}

	public String getTreatProcessDesc() {
		return this.treatProcessDesc;
	}

	public void setTreatProcessDesc(String treatProcessDesc) {
		this.treatProcessDesc = treatProcessDesc;
	}

	public String getUnitTimeTypeDesc() {
		return this.unitTimeTypeDesc;
	}

	public void setUnitTimeTypeDesc(String unitTimeTypeDesc) {
		this.unitTimeTypeDesc = unitTimeTypeDesc;
	}

	public String getInspectionUnit() {
		return this.inspectionUnit;
	}

	public void setInspectionUnit(String inspectionUnit) {
		this.inspectionUnit = inspectionUnit;
	}

	public String getTreatTime() {
		return this.treatTime;
	}

	public void setTreatTime(String treatTime) {
		this.treatTime = treatTime;
	}

	public Integer getTreatTimeUnit() {
		return this.treatTimeUnit;
	}

	public void setTreatTimeUnit(Integer treatTimeUnit) {
		this.treatTimeUnit = treatTimeUnit;
	}

	public Integer getTreatTimeUnitSuffix() {
		return this.treatTimeUnitSuffix;
	}

	public void setTreatTimeUnitSuffix(Integer treatTimeUnitSuffix) {
		this.treatTimeUnitSuffix = treatTimeUnitSuffix;
	}

	public Integer getTreatFn() {
		return this.treatFn;
	}

	public void setTreatFn(Integer treatFn) {
		this.treatFn = treatFn;
	}

	public String getTreatFn0() {
		return this.treatFn0;
	}

	public void setTreatFn0(String treatFn0) {
		this.treatFn0 = treatFn0;
	}

	public Integer getCheckRstFlag() {
		return this.checkRstFlag;
	}

	public void setCheckRstFlag(Integer checkRstFlag) {
		this.checkRstFlag = checkRstFlag;
	}

	public String getCheckRst() {
		return this.checkRst;
	}

	public void setCheckRst(String checkRst) {
		this.checkRst = checkRst;
	}

	public Integer getDiagRstFlag() {
		return this.diagRstFlag;
	}

	public void setDiagRstFlag(Integer diagRstFlag) {
		this.diagRstFlag = diagRstFlag;
	}

	public String getDiagRst() {
		return this.diagRst;
	}

	public void setDiagRst(String diagRst) {
		this.diagRst = diagRst;
	}

	public String getDiagRst0() {
		return this.diagRst0;
	}

	public void setDiagRst0(String diagRst0) {
		this.diagRst0 = diagRst0;
	}

	public Integer getTreatFlag() {
		return this.treatFlag;
	}

	public void setTreatFlag(Integer treatFlag) {
		this.treatFlag = treatFlag;
	}

	public String getTreat() {
		return this.treat;
	}

	public void setTreat(String treat) {
		this.treat = treat;
	}

	public String getTreatRst() {
		return this.treatRst;
	}

	public void setTreatRst(String treatRst) {
		this.treatRst = treatRst;
	}

}