package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

import java.util.Date;

public class ReportFormforPad implements java.io.Serializable,Comparable {
	private int reportFormforRemoteId;
	
	private String text;
	private String receiveDate;	
	private String sectionNo;
	private String testTypeNo;
	private String sampleNo;
	private String parItemNo;
	private String hospital;
	private String isFromOutHispital;
	private String isPatientOrDoctorWriteZanCun;
	private String patientName;//病人姓名
	private String patientNo;//病案号
	private String jiBingGrounp;//当前疾病分组
	private String inputName;//录入者
	private String sheHeDate;// 病人提交给责任医生的时间
	private String guiDangDate;//医生提交该化验单为归档状态的日期
	private String luRuDate;//录入日期
	private String patientId;//病人在电子病历中的ID
	private Integer cheXiaoFlag;//管理员撤销录入的化验单，医生可以开始修改
	private String guiDangAuthor;//归档者
	private Integer cheXiaoTrue;//第几次撤销
	public String getIsFromOutHispital() {
		return isFromOutHispital;
	}

	public void setIsFromOutHispital(String isFromOutHispital) {
		this.isFromOutHispital = isFromOutHispital;
	}

	public String getHospital() {
		return hospital;
	}
	
	public int compareTo(Object o) {
		ReportFormforPad reportFormforPad = (ReportFormforPad)o;
		int result = 0;		
		result = - receiveDate.compareTo(reportFormforPad.getReceiveDate());
		if(result == 0){
			return text.compareTo(reportFormforPad.getText());				
		}
		return result;		
	}
	
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getSectionNo() {
		return sectionNo;
	}
	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}
	public String getTestTypeNo() {
		return testTypeNo;
	}
	public void setTestTypeNo(String testTypeNo) {
		this.testTypeNo = testTypeNo;
	}
	public String getSampleNo() {
		return sampleNo;
	}
	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}
	public String getParItemNo() {
		return parItemNo;
	}
	public void setParItemNo(String parItemNo) {
		this.parItemNo = parItemNo;
	}

	public int getReportFormforRemoteId() {
		return reportFormforRemoteId;
	}

	public void setReportFormforRemoteId(int reportFormforRemoteId) {
		this.reportFormforRemoteId = reportFormforRemoteId;
	}


	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientNo() {
		return patientNo;
	}

	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}

	public String getJiBingGrounp() {
		return jiBingGrounp;
	}

	public void setJiBingGrounp(String jiBingGrounp) {
		this.jiBingGrounp = jiBingGrounp;
	}

	public String getIsPatientOrDoctorWriteZanCun() {
		return isPatientOrDoctorWriteZanCun;
	}

	public void setIsPatientOrDoctorWriteZanCun(String isPatientOrDoctorWriteZanCun) {
		this.isPatientOrDoctorWriteZanCun = isPatientOrDoctorWriteZanCun;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getSheHeDate() {
		return sheHeDate;
	}

	public void setSheHeDate(String sheHeDate) {
		this.sheHeDate = sheHeDate;
	}

	public String getGuiDangDate() {
		return guiDangDate;
	}

	public void setGuiDangDate(String guiDangDate) {
		this.guiDangDate = guiDangDate;
	}

	public String getLuRuDate() {
		return luRuDate;
	}

	public void setLuRuDate(String luRuDate) {
		this.luRuDate = luRuDate;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public Integer getCheXiaoFlag() {
		return cheXiaoFlag;
	}

	public void setCheXiaoFlag(Integer cheXiaoFlag) {
		this.cheXiaoFlag = cheXiaoFlag;
	}

	public String getGuiDangAuthor() {
		return guiDangAuthor;
	}

	public void setGuiDangAuthor(String guiDangAuthor) {
		this.guiDangAuthor = guiDangAuthor;
	}

	public Integer getCheXiaoTrue() {
		return cheXiaoTrue;
	}

	public void setCheXiaoTrue(Integer cheXiaoTrue) {
		this.cheXiaoTrue = cheXiaoTrue;
	}

	
	
	
	
}
