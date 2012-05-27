package com.juncsoft.KLJY.patientanddoctoroperator.entity;

import java.util.Date;

/**
 * 选择日期
 * @author liugang
 *
 */
public class PlanSignDateEntity {
	private Long id;
	private String dateList;
	private Date dateValue;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDateList() {
		return dateList;
	}
	public void setDateList(String dateList) {
		this.dateList = dateList;
	}
	public Date getDateValue() {
		return dateValue;
	}
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
}
