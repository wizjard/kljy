package com.juncsoft.KLJY.InHospitalCase.Fever.PresentHistoryNew;

public class Fever_PresentIllnessHistory_Tree {

	private Long id;
	private String name;
	private String code;
	private Long tier;
	private String href;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getTier() {
		return tier;
	}
	public void setTier(Long tier) {
		this.tier = tier;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
}
