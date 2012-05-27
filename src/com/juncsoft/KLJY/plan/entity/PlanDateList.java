package com.juncsoft.KLJY.plan.entity;

import java.util.Date;

public class PlanDateList {
	private Long id;
	private String dataList;
	private Date dataValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDataList() {
		return dataList;
	}
	public void setDataList(String dataList) {
		this.dataList = dataList;
	}
	public Date getDataValue() {
		return dataValue;
	}
	public void setDataValue(Date dataValue) {
		this.dataValue = dataValue;
	}
	
}
