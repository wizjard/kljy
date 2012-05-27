package com.juncsoft.KLJY.InHospitalCase.Fever.entity;

/**
 * TInHspFeverOthersSystom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TInHspFeverOthersSystom implements java.io.Serializable {

	// Fields

	private Long id;
	private Long caseId;
	private String otherdesease;
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
	public TInHspFeverOthersSystom() {
	}

	/** minimal constructor */
	public TInHspFeverOthersSystom(Long id, Long caseId) {
		this.id = id;
		this.caseId = caseId;
	}

	/** full constructor */
	public TInHspFeverOthersSystom(Long id, Long caseId, String otherdesease) {
		this.id = id;
		this.caseId = caseId;
		this.otherdesease = otherdesease;
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

	public String getOtherdesease() {
		return this.otherdesease;
	}

	public void setOtherdesease(String otherdesease) {
		this.otherdesease = otherdesease;
	}

}