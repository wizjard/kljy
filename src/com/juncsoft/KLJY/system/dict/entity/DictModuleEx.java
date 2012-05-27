package com.juncsoft.KLJY.system.dict.entity;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class DictModuleEx implements java.io.Serializable {
	private Long id;
	private Long pid;
	private String name;
	private String code;
	private String comment;
	private List<DictFldcodeEx> fldCodes=new ArrayList<DictFldcodeEx>();
	private List<DictModuleEx> childrens=new ArrayList<DictModuleEx>();
	public Long getId() {
		return id;
	}
	public List<DictModuleEx> getChildrens() {
		return childrens;
	}
	public void setChildrens(List<DictModuleEx> childrens) {
		this.childrens = childrens;
	}
	public List<DictFldcodeEx> getFldCodes() {
		return fldCodes;
	}
	public void setFldCodes(List<DictFldcodeEx> fldCodes) {
		this.fldCodes = fldCodes;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
