package com.juncsoft.KLJY.patientanddoctoroperator.entity;

import java.util.Date;

/**
 * 医生回复和病人提问实体
 * 2011-05-31
 * @author liugang
 *
 */
public class DoctorReplyRecordAndPatientQuestions {
	private Long id;
	private Long pcId;//咨询我的医生实体（病人问题实体）外键
	private Date drAndpqDate;//回复、提问时间
	private String drAndpqContent;//回复、提问内容 
	private Long drAndpqId;//医生ID或者病人ID
	private String drAndpqName;//医生姓名或者病人姓名
	private Integer drAndpqFlag;//是医生回复，还是病人再次提问0:表示医生，1：表示病人
	private Integer drAndpgReadPatient;//病人已读1,0:未读
	private Integer drAndpgCancel;//回复内容是否有用，医生设置1：有用，0没有用
	private String deptCode;//科室编号 
	private Long grounpId;//责任小组
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
	public Date getDrAndpqDate() {
		return drAndpqDate;
	}
	public void setDrAndpqDate(Date drAndpqDate) {
		this.drAndpqDate = drAndpqDate;
	}
	public String getDrAndpqContent() {
		return drAndpqContent;
	}
	public void setDrAndpqContent(String drAndpqContent) {
		this.drAndpqContent = drAndpqContent;
	}
	public Long getDrAndpqId() {
		return drAndpqId;
	}
	public void setDrAndpqId(Long drAndpqId) {
		this.drAndpqId = drAndpqId;
	}
	public String getDrAndpqName() {
		return drAndpqName;
	}
	public void setDrAndpqName(String drAndpqName) {
		this.drAndpqName = drAndpqName;
	}
	public Integer getDrAndpqFlag() {
		return drAndpqFlag;
	}
	public void setDrAndpqFlag(Integer drAndpqFlag) {
		this.drAndpqFlag = drAndpqFlag;
	}
	public Integer getDrAndpgReadPatient() {
		return drAndpgReadPatient;
	}
	public void setDrAndpgReadPatient(Integer drAndpgReadPatient) {
		this.drAndpgReadPatient = drAndpgReadPatient;
	}
	public Integer getDrAndpgCancel() {
		return drAndpgCancel;
	}
	public void setDrAndpgCancel(Integer drAndpgCancel) {
		this.drAndpgCancel = drAndpgCancel;
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
