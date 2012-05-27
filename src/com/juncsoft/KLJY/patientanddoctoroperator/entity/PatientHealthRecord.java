package com.juncsoft.KLJY.patientanddoctoroperator.entity;

import java.util.Date;

/**
 * 病人健康记录
 * 2011-05-31
 * @author liugang
 *
 */
public class PatientHealthRecord {
	private Long id;
	private Long patientId;//病人编号
	private Date writeRecordDate;//书写日期
	private String spiritStatus;//健康状态
	private String diet;//食量
	private String sleep;//睡眠
	private String piss;//小便
	private String defecate;//大便
	private String weight;//体重
	private String healthStatus;//健康情况
	private String treatmentStatus;//治疗情况
	private String improveStatus;//好转情况
	private String uploadFile;//上传资料
	private String signature;//签名（病人名称）
	private String uploadImage;//上传图片
	private String deptCode;//科室编号 
	private String deptName;//科室名称
	private String entityName="PatientHealthRecord";//实体名称
	private Long typeFlag;//源自DoctorRoundsRecord实体中的ID
	private Long grounpId;//当前责任小组
	//liugang 2011-08-06 start
	private Integer isNotUpdate;//是否转组
	//liugang 2011-08-06 end	
	public Long getGrounpId() {
		return grounpId;
	}
	public void setGrounpId(Long grounpId) {
		this.grounpId = grounpId;
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
	public Date getWriteRecordDate() {
		return writeRecordDate;
	}
	public void setWriteRecordDate(Date writeRecordDate) {
		this.writeRecordDate = writeRecordDate;
	}
	public String getSpiritStatus() {
		return spiritStatus;
	}
	public void setSpiritStatus(String spiritStatus) {
		this.spiritStatus = spiritStatus;
	}
	public String getDiet() {
		return diet;
	}
	public void setDiet(String diet) {
		this.diet = diet;
	}
	public String getSleep() {
		return sleep;
	}
	public void setSleep(String sleep) {
		this.sleep = sleep;
	}
	public String getPiss() {
		return piss;
	}
	public void setPiss(String piss) {
		this.piss = piss;
	}
	public String getDefecate() {
		return defecate;
	}
	public void setDefecate(String defecate) {
		this.defecate = defecate;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getHealthStatus() {
		return healthStatus;
	}
	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}
	public String getTreatmentStatus() {
		return treatmentStatus;
	}
	public void setTreatmentStatus(String treatmentStatus) {
		this.treatmentStatus = treatmentStatus;
	}
	public String getImproveStatus() {
		return improveStatus;
	}
	public void setImproveStatus(String improveStatus) {
		this.improveStatus = improveStatus;
	}
	public String getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getUploadImage() {
		return uploadImage;
	}
	public void setUploadImage(String uploadImage) {
		this.uploadImage = uploadImage;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Long getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(Long typeFlag) {
		this.typeFlag = typeFlag;
	}
	//liugang 2011-08-06 start
	public Integer getIsNotUpdate() {
		return isNotUpdate;
	}
	public void setIsNotUpdate(Integer isNotUpdate) {
		this.isNotUpdate = isNotUpdate;
	}
	//liugang 2011-08-06 end
}
