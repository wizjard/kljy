package com.juncsoft.KLJY.system.dict.entity;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DictFldcodeEx implements java.io.Serializable {
	private Long id;
	private Long moduleId;
	private String code;
	private String name;
	private String comment;
	private List<DictItemEx> items=new ArrayList<DictItemEx>();
	public Long getId() {
		return id;
	}
	public List<DictItemEx> getItems() {
		return items;
	}
	public void setItems(List<DictItemEx> items) {
		this.items = items;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
