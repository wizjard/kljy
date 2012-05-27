package com.juncsoft.KLJY.membergrounp.entity;

import java.util.Date;

/**
 * 会员申请转科记录表
 * @author liugang
 *
 */
public class MemberApplicationRecord {
	private Long id;
	private Long patientId;
	private String applicationDeptCode;//申请科室
	private String applicationBacuse;//申请理由
	private Date applicatinDate = new Date();//申请时间
	private String applicationStateContent;//中间结果
	private Integer result;//0:结果进行中,1:结果完成
	private String oldDeptCode;//原来的责任科室
	private Long oldGrounpId;//  原来的责任小组
	private Integer autoFlag;//状态转化中
	private String currentDeptBecause;//当前科室理由
	private String applicationDeptBecause;//申请科室理由
	private Integer currentFlag;//当前科室是否同意
	private Integer applicationFlag;//责任科室是否同意
	private String optrole; //操作角色
	private Integer flag; //三种状态，已申请0；同意1；不同意2；
	private Date appsendDate;
	private String appuserName;
	private Date transappDate;
	private String transappuserName;
	
	public Date getAppsendDate() {
		return appsendDate;
	}
	public void setAppsendDate(Date appsendDate) {
		this.appsendDate = appsendDate;
	}
	public String getAppuserName() {
		return appuserName;
	}
	public void setAppuserName(String appuserName) {
		this.appuserName = appuserName;
	}
	public Date getTransappDate() {
		return transappDate;
	}
	public void setTransappDate(Date transappDate) {
		this.transappDate = transappDate;
	}
	public String getTransappuserName() {
		return transappuserName;
	}
	public void setTransappuserName(String transappuserName) {
		this.transappuserName = transappuserName;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getOptrole() {
		return optrole;
	}
	public void setOptrole(String optrole) {
		this.optrole = optrole;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public String getApplicationDeptCode() {
		return applicationDeptCode;
	}
	public void setApplicationDeptCode(String applicationDeptCode) {
		this.applicationDeptCode = applicationDeptCode;
	}
	public String getApplicationBacuse() {
		return applicationBacuse;
	}
	public void setApplicationBacuse(String applicationBacuse) {
		this.applicationBacuse = applicationBacuse;
	}
	public Date getApplicatinDate() {
		return applicatinDate;
	}
	public void setApplicatinDate(Date applicatinDate) {
		this.applicatinDate = applicatinDate;
	}
	public String getApplicationStateContent() {
		return applicationStateContent;
	}
	public void setApplicationStateContent(String applicationStateContent) {
		this.applicationStateContent = applicationStateContent;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getOldDeptCode() {
		return oldDeptCode;
	}
	public void setOldDeptCode(String oldDeptCode) {
		this.oldDeptCode = oldDeptCode;
	}
	public Integer getAutoFlag() {
		return autoFlag;
	}
	public void setAutoFlag(Integer autoFlag) {
		this.autoFlag = autoFlag;
	}
	public String getCurrentDeptBecause() {
		return currentDeptBecause;
	}
	public void setCurrentDeptBecause(String currentDeptBecause) {
		this.currentDeptBecause = currentDeptBecause;
	}
	public String getApplicationDeptBecause() {
		return applicationDeptBecause;
	}
	public void setApplicationDeptBecause(String applicationDeptBecause) {
		this.applicationDeptBecause = applicationDeptBecause;
	}
	public Integer getCurrentFlag() {
		return currentFlag;
	}
	public void setCurrentFlag(Integer currentFlag) {
		this.currentFlag = currentFlag;
	}
	public Integer getApplicationFlag() {
		return applicationFlag;
	}
	public void setApplicationFlag(Integer applicationFlag) {
		this.applicationFlag = applicationFlag;
	}
	public Long getOldGrounpId() {
		return oldGrounpId;
	}
	public void setOldGrounpId(Long oldGrounpId) {
		this.oldGrounpId = oldGrounpId;
	}
}
