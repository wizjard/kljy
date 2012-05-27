package com.juncsoft.KLJY.InHospitalCase.Fever.entity;

/**
 * TInHspFeverSystomEvolution entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TInHspFeverSystomEvolution implements java.io.Serializable {

	// Fields

	private Long id;
	private Long caseId;
	private String evolution;
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
	public TInHspFeverSystomEvolution() {
	}

	/** minimal constructor */
	public TInHspFeverSystomEvolution(Long id, Long caseId) {
		this.id = id;
		this.caseId = caseId;
	}

	/** full constructor */
	public TInHspFeverSystomEvolution(Long id, Long caseId, String evolution) {
		this.id = id;
		this.caseId = caseId;
		this.evolution = evolution;
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

	public String getEvolution() {
		return this.evolution;
	}

	public void setEvolution(String evolution) {
		this.evolution = evolution;
	}

}