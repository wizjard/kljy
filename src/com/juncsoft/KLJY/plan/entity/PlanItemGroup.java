package com.juncsoft.KLJY.plan.entity;

import java.math.BigInteger;
import java.util.Date;

public class PlanItemGroup {
   private BigInteger planId;
   private String stateStr;
   private Integer visitState;
   private String resultState;
   private String visitDate;
   private Integer state;
   public Integer getState() {
	return state;
}
public void setState(Integer state) {
	this.state = state;
}
public BigInteger getPlanId() {
	return planId;
}
public void setPlanId(BigInteger planId) {
	this.planId = planId;
}
public Integer getPlanTime() {
	return planTime;
}
public void setPlanTime(Integer planTime) {
	this.planTime = planTime;
}
public Date getBeginDate() {
	return beginDate;
}
public void setBeginDate(Date beginDate) {
	this.beginDate = beginDate;
}
public Integer getCircle() {
	return circle;
}
public void setCircle(Integer circle) {
	this.circle = circle;
}
public Integer getCircleType() {
	return circleType;
}
public void setCircleType(Integer circleType) {
	this.circleType = circleType;
}
public Date getPlanDate() {
	return planDate;
}
public void setPlanDate(Date planDate) {
	this.planDate = planDate;
}
public void setVisitState(Integer visitState) {
	this.visitState = visitState;
}
public Integer getVisitState() {
	return visitState;
}
public void setResultState(String resultState) {
	this.resultState = resultState;
}
public String getResultState() {
	return resultState;
}
public void setVisitDate(String visitDate) {
	this.visitDate = visitDate;
}
public String getVisitDate() {
	return visitDate;
}
public void setStateStr(String stateStr) {
	this.stateStr = stateStr;
}
public String getStateStr() {
	return stateStr;
}
private Integer planTime;
   private Date beginDate;
   private Integer circle;
   private Integer circleType;
   private Date planDate;
}
