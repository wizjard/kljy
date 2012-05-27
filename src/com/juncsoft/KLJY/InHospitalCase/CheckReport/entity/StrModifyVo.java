package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

import net.sf.json.JSONArray;

public class StrModifyVo {

	private String text;
	private JSONArray children;
	private boolean leaf;
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public JSONArray getChildren() {
		return children;
	}
	public void setChildren(JSONArray children) {
		this.children = children;
	}
	
}
