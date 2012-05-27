package com.juncsoft.KLJY.patientanddoctoroperator.entity;

import java.util.Date;

/**
 * 医生网上查房记录实体
 * 2011-05-31
 * @author liugang
 *
 */
public class DoctorRoundsRecord {
	private Long id;
	private Long patientId;//病人编号
	private Long phrId;//病人健康记录外键
	private Long doctorId;//医生编号
	private String roundsTitle;//查房标题
	private String roundsContent;//查房内容
	private Date roundsDate;//查房时间
	private String doctorName;//医生签名
	private String entityName="DoctorRoundsRecord";//实体名称
	//liugang 2011-08-06 start
	private String deptCode;//所在科室
	private Long grounpId;//所在小组
	//liugang 2011-08-06 start
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
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
	public Long getPhrId() {
		return phrId;
	}
	public void setPhrId(Long phrId) {
		this.phrId = phrId;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getRoundsTitle() {
		return roundsTitle;
	}
	public void setRoundsTitle(String roundsTitle) {
		this.roundsTitle = roundsTitle;
	}
	public String getRoundsContent() {
		return roundsContent;
	}
	public void setRoundsContent(String roundsContent) {
		this.roundsContent = roundsContent;
	}
	public Date getRoundsDate() {
		return roundsDate;
	}
	public void setRoundsDate(Date roundsDate) {
		this.roundsDate = roundsDate;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
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
}
