package com.juncsoft.KLJY.InHospitalCase.Fever.entity;

/**
 * TInHspFeverCurrentState entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TInHspFeverCurrentState implements java.io.Serializable {

	// Fields

	private Long id;
	private Long caseId;
	private Integer spiritStatu;
	private String spiritStatu0;
	private Integer eatVolume;
	private String eatVolume0;
	private Integer sleep;
	private String sleep0;
	private Integer piss;
	private String piss0;
	private Integer excrement;
	private String excrement0;
	private Integer bodyWeight;
	private String bodyWeightTime;
	private Integer bodyWeightTimeUnit;
	private String bodyWeightKg;
	private Integer mainSysptomEve;
	private String newSysptom;
	private String otherCurrent;
	private String oldContent;
	// Constructors

	public String getOldContent() {
		return oldContent;
	}

	public void setOldContent(String oldContent) {
		this.oldContent = oldContent;
	}
	// Constructors

	/** default constructor */
	public TInHspFeverCurrentState() {
	}

	/** minimal constructor */
	public TInHspFeverCurrentState(Long id, Long caseId) {
		this.id = id;
		this.caseId = caseId;
	}

	/** full constructor */
	public TInHspFeverCurrentState(Long id, Long caseId, Integer spiritStatu,
			String spiritStatu0, Integer eatVolume, String eatVolume0,
			Integer sleep, String sleep0, Integer piss, String piss0,
			Integer excrement, String excrement0, Integer bodyWeight,
			String bodyWeightTime, Integer bodyWeightTimeUnit,
			String bodyWeightKg, Integer mainSysptomEve, String newSysptom,
			String otherCurrent) {
		this.id = id;
		this.caseId = caseId;
		this.spiritStatu = spiritStatu;
		this.spiritStatu0 = spiritStatu0;
		this.eatVolume = eatVolume;
		this.eatVolume0 = eatVolume0;
		this.sleep = sleep;
		this.sleep0 = sleep0;
		this.piss = piss;
		this.piss0 = piss0;
		this.excrement = excrement;
		this.excrement0 = excrement0;
		this.bodyWeight = bodyWeight;
		this.bodyWeightTime = bodyWeightTime;
		this.bodyWeightTimeUnit = bodyWeightTimeUnit;
		this.bodyWeightKg = bodyWeightKg;
		this.mainSysptomEve = mainSysptomEve;
		this.newSysptom = newSysptom;
		this.otherCurrent = otherCurrent;
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

	public Integer getSpiritStatu() {
		return this.spiritStatu;
	}

	public void setSpiritStatu(Integer spiritStatu) {
		this.spiritStatu = spiritStatu;
	}

	public String getSpiritStatu0() {
		return this.spiritStatu0;
	}

	public void setSpiritStatu0(String spiritStatu0) {
		this.spiritStatu0 = spiritStatu0;
	}

	public Integer getEatVolume() {
		return this.eatVolume;
	}

	public void setEatVolume(Integer eatVolume) {
		this.eatVolume = eatVolume;
	}

	public String getEatVolume0() {
		return this.eatVolume0;
	}

	public void setEatVolume0(String eatVolume0) {
		this.eatVolume0 = eatVolume0;
	}

	public Integer getSleep() {
		return this.sleep;
	}

	public void setSleep(Integer sleep) {
		this.sleep = sleep;
	}

	public String getSleep0() {
		return this.sleep0;
	}

	public void setSleep0(String sleep0) {
		this.sleep0 = sleep0;
	}

	public Integer getPiss() {
		return this.piss;
	}

	public void setPiss(Integer piss) {
		this.piss = piss;
	}

	public String getPiss0() {
		return this.piss0;
	}

	public void setPiss0(String piss0) {
		this.piss0 = piss0;
	}

	public Integer getExcrement() {
		return this.excrement;
	}

	public void setExcrement(Integer excrement) {
		this.excrement = excrement;
	}

	public String getExcrement0() {
		return this.excrement0;
	}

	public void setExcrement0(String excrement0) {
		this.excrement0 = excrement0;
	}

	public Integer getBodyWeight() {
		return this.bodyWeight;
	}

	public void setBodyWeight(Integer bodyWeight) {
		this.bodyWeight = bodyWeight;
	}

	public String getBodyWeightTime() {
		return this.bodyWeightTime;
	}

	public void setBodyWeightTime(String bodyWeightTime) {
		this.bodyWeightTime = bodyWeightTime;
	}

	public Integer getBodyWeightTimeUnit() {
		return this.bodyWeightTimeUnit;
	}

	public void setBodyWeightTimeUnit(Integer bodyWeightTimeUnit) {
		this.bodyWeightTimeUnit = bodyWeightTimeUnit;
	}

	public String getBodyWeightKg() {
		return this.bodyWeightKg;
	}

	public void setBodyWeightKg(String bodyWeightKg) {
		this.bodyWeightKg = bodyWeightKg;
	}

	public Integer getMainSysptomEve() {
		return this.mainSysptomEve;
	}

	public void setMainSysptomEve(Integer mainSysptomEve) {
		this.mainSysptomEve = mainSysptomEve;
	}

	public String getNewSysptom() {
		return this.newSysptom;
	}

	public void setNewSysptom(String newSysptom) {
		this.newSysptom = newSysptom;
	}

	public String getOtherCurrent() {
		return this.otherCurrent;
	}

	public void setOtherCurrent(String otherCurrent) {
		this.otherCurrent = otherCurrent;
	}

}