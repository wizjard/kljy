package com.juncsoft.KLJY.followVisit.entity;
public class Symptomst {
	private int sy_id;
	private int pt_id;
	private int t_symptom_ID;
	private int t_state_ID;
	private String sy_text;

	public String getSy_text() {
		return sy_text;
	}

	public void setSy_text(String sy_text) {
		this.sy_text = sy_text;
	}

	public int getSy_id() {
		return sy_id;
	}

	public void setSy_id(int sy_id) {
		this.sy_id = sy_id;
	}

	public int getPt_id() {
		return pt_id;
	}

	public void setPt_id(int pt_id) {
		this.pt_id = pt_id;
	}

	public int getT_symptom_ID() {
		return t_symptom_ID;
	}

	public void setT_symptom_ID(int t_symptom_id) {
		t_symptom_ID = t_symptom_id;
	}

	public int getT_state_ID() {
		return t_state_ID;
	}

	public void setT_state_ID(int t_state_id) {
		t_state_ID = t_state_id;
	}

}
