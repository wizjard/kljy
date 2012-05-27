package com.juncsoft.KLJY.InHospitalCase.Fever.entity;

/**
 * TInHspFeverNegativeSystom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TInHspFeverNegativeSystom implements java.io.Serializable {

	// Fields

	private Long id;
	private Long caseId;
	private Integer negaSysptom;
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
	public TInHspFeverNegativeSystom() {
	}

	/** minimal constructor */
	public TInHspFeverNegativeSystom(Long id, Long caseId) {
		this.id = id;
		this.caseId = caseId;
	}

	/** full constructor */
	public TInHspFeverNegativeSystom(Long id, Long caseId, Integer negaSysptom) {
		this.id = id;
		this.caseId = caseId;
		this.negaSysptom = negaSysptom;
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

	public Integer getNegaSysptom() {
		return this.negaSysptom;
	}

	public void setNegaSysptom(Integer negaSysptom) {
		this.negaSysptom = negaSysptom;
	}

}