package com.juncsoft.KLJY.followVisit.entity;

public class T_symptom {
	private int t_id;
	private String symptomName;
	private String sy_url;
	private int tp_id;

	public String getSy_url() {
		return sy_url;
	}

	public void setSy_url(String sy_url) {
		this.sy_url = sy_url;
	}

	public int getTp_id() {
		return tp_id;
	}

	public void setTp_id(int tp_id) {
		this.tp_id = tp_id;
	}

	public int getT_id() {
		return t_id;
	}

	public void setT_id(int t_id) {
		this.t_id = t_id;
	}

	public String getSymptomName() {
		return symptomName;
	}

	public void setSymptomName(String symptomName) {
		this.symptomName = symptomName;
	}
}
