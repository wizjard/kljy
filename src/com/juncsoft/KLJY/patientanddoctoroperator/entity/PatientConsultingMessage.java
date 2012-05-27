package com.juncsoft.KLJY.patientanddoctoroperator.entity;

import java.util.Date;

/**
 * 科室会诊附言列表
 * @author liugang
 *
 */
public class PatientConsultingMessage {
	private Long id;
	private Long pcId;
	private String deptCode;
	private Long doctorId;
	private Date mesDdate = new Date();
	private String message;
	private Long grounpId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPcId() {
		return pcId;
	}
	public void setPcId(Long pcId) {
		this.pcId = pcId;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getMesDdate() {
		return mesDdate;
	}
	public void setMesDdate(Date mesDdate) {
		this.mesDdate = mesDdate;
	}
	public Long getGrounpId() {
		return grounpId;
	}
	public void setGrounpId(Long grounpId) {
		this.grounpId = grounpId;
	}
}
