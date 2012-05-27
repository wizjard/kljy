package com.juncsoft.KLJY.plan.entity;

import java.util.Calendar;
import java.util.Date;

public class PlanModuleItem {
	private Long id;  //计划模板项目Id
	private Long moduleId;  //关联模板的Id
	//private String itemNo;
	private String checkItemCode;
	private String checkItemName;
	private int circle;
	private Integer circleType;
	/*private String reportDate;
	private String planDate;*/
	
	/*private Integer crossd;
	private Integer visitState;
	private String recordURL;
	private String resultURL;
	private CheckItem checkItem;
	private Long outOMCid;*/
	private Long checkId;
	
	//private Integer state;
	private String comment;
	//private String visitDate;
	/*public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	public String getPlanDate() {
		return planDate;
	}*/
	
	public Long getId() {
		return id;
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
	
	public Long getCheckId() {
		return checkId;
	}
	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}
	
	public Integer getCircleType() {
		return circleType;
	}
	public void setCircleType(Integer circleType) {
		if(circleType == null) 
			circleType = Calendar.WEEK_OF_YEAR;
		this.circleType = circleType;
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
	/*public String getRecordURL() {
		return recordURL;
	}
	public void setRecordURL(String recordURL) {
		this.recordURL = recordURL;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}*/
	
	
	
	/*public Long getOutOMCid() {
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
	public int getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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

	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}*/
	
	/*public void setCrossd(Integer crossd) {
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
	}*/
	
	/*public Long getCheckItemId() {
		return checkItemId;
	}
	public void setCheckItemId(Long checkItemId) {
		this.checkItemId = checkItemId;
	}*/

}
