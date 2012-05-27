package com.juncsoft.KLJY.InHospitalCase.Fever.entity;

/**
 * TInHspFeverFeverHistoryContent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TInHspFeverFeverHistoryContent implements java.io.Serializable {

	// Fields

	private Long id;
	private Long caseId;
	private String oldOperations;
	private String content;
	private Integer feverType;
	// Constructors

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/** default constructor */
	public TInHspFeverFeverHistoryContent() {
	}

	/** full constructor */
	public TInHspFeverFeverHistoryContent(Long id, Long caseId,
			String oldOptions) {
		this.id = id;
		this.caseId = caseId;
		this.oldOperations = oldOptions;
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

	public String getOldOperations() {
		return oldOperations;
	}

	public void setOldOperations(String oldOperations) {
		this.oldOperations = oldOperations;
	}

	public Integer getFeverType() {
		return feverType;
	}

	public void setFeverType(Integer feverType) {
		this.feverType = feverType;
	}

	

}