package com.juncsoft.KLJY.membergrounp.entity;

import java.util.Date;

/**
 * 会员转科转组日志
 * @author liugang
 *
 */
public class MemberDeptOrGrounpRecord {
	private Long id;
	private String oldDeptCode;
	private Long oldGrounpId;
	private String appDate;
	private String deptCode;
	private Long grounpId;
	private Long patientId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public Long getGrounpId() {
		return grounpId;
	}
	public void setGrounpId(Long grounpId) {
		this.grounpId = grounpId;
	}
	
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getOldDeptCode() {
		return oldDeptCode;
	}
	public void setOldDeptCode(String oldDeptCode) {
		this.oldDeptCode = oldDeptCode;
	}
	public Long getOldGrounpId() {
		return oldGrounpId;
	}
	public void setOldGrounpId(Long oldGrounpId) {
		this.oldGrounpId = oldGrounpId;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	
}
