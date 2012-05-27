package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;


public class CheckReportList {

	private String projectname; 
	private String esname; 
	private String result; 
	private String refvalue; 
	private String unit; 
	private String historyResult; 
	private String historyRatio;
	//liugang2011-0929
	private String id;
	private String updateResult;
	//liugang2011-0929

	public String getUpdateResult() {
		return updateResult;
	}
	public void setUpdateResult(String updateResult) {
		this.updateResult = updateResult;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getEsname() {
		return esname;
	}
	public void setEsname(String esname) {
		this.esname = esname;
	}
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRefvalue() {
		return refvalue;
	}
	public void setRefvalue(String refvalue) {
		this.refvalue = refvalue;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getHistoryResult() {
		return historyResult;
	}
	public void setHistoryResult(String historyResult) {
		this.historyResult = historyResult;
	}
	public String getHistoryRatio() {
		return historyRatio;
	}
	public void setHistoryRatio(String historyRatio) {
		this.historyRatio = historyRatio;
	}
	
}
