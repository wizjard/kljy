/**
 * auther:lisht createOrEditDate：Oct 20, 2010   12:00:19 PM
 * projectName：TCMP1  fileLaction： com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.ContinuePage_Ops.java
 */
package com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity;

import java.util.Date;

/**
 * auther:lisht createOrEditDate：Oct 20, 2010 12:00:19 PM 病案首页续页--手术部分实体
 * class、method、Attribute or logic explain：
 * 
 */
public class ContinuePage_Ops {
	private Long id;
	private Long caseId;
	private String operationNo;
	private Date tdate;
	private String surgeryName;
	private String surgeryDocName;
	private String surgeryAssistant1;
	private String surgeryAssistant2;
	private String anesthesiaMode;
	private String cut;
	private String analgesist;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getOperationNo() {
		return operationNo;
	}

	public void setOperationNo(String operationNo) {
		this.operationNo = operationNo;
	}

	public Date getTdate() {
		return tdate;
	}

	public void setTdate(Date tdate) {
		this.tdate = tdate;
	}

	public String getSurgeryName() {
		return surgeryName;
	}

	public void setSurgeryName(String surgeryName) {
		this.surgeryName = surgeryName;
	}

	public String getSurgeryDocName() {
		return surgeryDocName;
	}

	public void setSurgeryDocName(String surgeryDocName) {
		this.surgeryDocName = surgeryDocName;
	}

	public String getSurgeryAssistant1() {
		return surgeryAssistant1;
	}

	public void setSurgeryAssistant1(String surgeryAssistant1) {
		this.surgeryAssistant1 = surgeryAssistant1;
	}

	public String getSurgeryAssistant2() {
		return surgeryAssistant2;
	}

	public void setSurgeryAssistant2(String surgeryAssistant2) {
		this.surgeryAssistant2 = surgeryAssistant2;
	}

	public String getAnesthesiaMode() {
		return anesthesiaMode;
	}

	public void setAnesthesiaMode(String anesthesiaMode) {
		this.anesthesiaMode = anesthesiaMode;
	}

	public String getCut() {
		return cut;
	}

	public void setCut(String cut) {
		this.cut = cut;
	}

	public String getAnalgesist() {
		return analgesist;
	}

	public void setAnalgesist(String analgesist) {
		this.analgesist = analgesist;
	}

}
