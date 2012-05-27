package com.juncsoft.KLJY.plan.entity;

import java.util.Calendar;
import java.util.Date;


public class PlanItem {	
	private Long id;
	private String itemNo;
    private Long planId;
	private String checkItemCode;
	private String checkItemName;
	private int circle;
	private Integer circleType;
	private String reportDate;
	private Date planDate;
	private Integer crossd;
	private Integer visitState;
	private String recordURL;
	private String comment;
	private Date visitDate;
	private String resultURL;
	private CheckItem checkItem;
	private Long outOMCid;
	private Integer messageFlag;  //短信提醒标识   1 表示已提醒  0 表示还未提醒    by cheng jiangyu 2011-9-20
	
	public String getRecordURL() {
		return recordURL;
	}
	public void setRecordURL(String recordURL) {
		this.recordURL = recordURL;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public Integer getCircleType() {
		return circleType;
	}
	public void setCircleType(Integer circleType) {
		if(circleType == null) 
			circleType = Calendar.WEEK_OF_YEAR;
		this.circleType = circleType;
	}
	
	
	public Long getOutOMCid() {
		return outOMCid;
	}
	public void setOutOMCid(Long outOMCid) {
		this.outOMCid = outOMCid;
	}
	public CheckItem getCheckItem() {
		return checkItem;
	}
	public void setCheckItem(CheckItem checkItem) {
		this.checkItem = checkItem;
	}
	private int state;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getResultURL() {
		return resultURL;
	}
	public void setResultURL(String resultURL) {
		this.resultURL = resultURL;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getCheckItemCode() {
		return checkItemCode;
	}
	public void setCheckItemCode(String checkItemCode) {
		this.checkItemCode = checkItemCode;
	}
	public String getCheckItemName() {
		return checkItemName;
	}
	public void setCheckItemName(String checkItemName) {
		this.checkItemName = checkItemName;
	}
	
	public int getCircle() {
		return circle;
	}
	public void setCircle(int circle) {
		this.circle = circle;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setCrossd(Integer crossd) {
		this.crossd = crossd;
	}
	public Integer getCrossd() {
		return crossd;
	}
	public void setVisitState(Integer visitState) {
		this.visitState = visitState;
	}
	public Integer getVisitState() {
		return visitState;
	}
	public Integer getMessageFlag() {
		return messageFlag;
	}
	public void setMessageFlag(Integer messageFlag) {
		this.messageFlag = messageFlag;
	}

}
