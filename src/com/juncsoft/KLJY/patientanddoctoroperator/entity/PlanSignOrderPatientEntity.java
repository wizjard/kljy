package com.juncsoft.KLJY.patientanddoctoroperator.entity;

import java.util.Date;

/**
 * 病人预约实体
 * @author liugang
 *
 */
public class PlanSignOrderPatientEntity {
	private Long id;
	private String deptCode;
	private Long doctorId;
	private Long patientId;//导出时显示(HIS中的病人编号)
	private Long bsAPId;//挂号系统上午和下午设置
	private Date planDate;//预约时间
	private Long bsTSId;//预约时间段
	private Date saveDate = new Date();//会员操作时间
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
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public Long getBsAPId() {
		return bsAPId;
	}
	public void setBsAPId(Long bsAPId) {
		this.bsAPId = bsAPId;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Long getBsTSId() {
		return bsTSId;
	}
	public void setBsTSId(Long bsTSId) {
		this.bsTSId = bsTSId;
	}
	public Date getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}
}
