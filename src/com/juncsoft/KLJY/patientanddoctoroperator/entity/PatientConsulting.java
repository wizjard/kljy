package com.juncsoft.KLJY.patientanddoctoroperator.entity;

import java.util.Date;

/**
 * 咨询我的医生实体（病人问题实体）
 * 2011-05-31
 * @author liugang
 *
 */
public class PatientConsulting {
	private Long id;
	private Long patientId;//病人编号
	private Integer consultingCount;//咨询次数
	private Date consultingDate;//咨询时间
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
	private String deptCode;//科室编号 
	private String deptName;//科室名称
	private String mainProblem;//咨询问题
	private String uploadImage;//上传图片
	private Long grounpId;//责任小组
	private Long isNotSend;//是否转发
	private String currentDetptMessage;//当前责任科室附言
	private String applicationDeptMessage;//中转每一个咨询科室附言
	private Integer pcMeetingState;//当前咨询是否已经关闭
	private Integer readCount;//更新已读次数
	//liugang 2011-08-06 start
	private Integer isNotUpdate;//当前会员是否转科
	//liugang 2011-08-06 end
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
	public Integer getConsultingCount() {
		return consultingCount;
	}
	public void setConsultingCount(Integer consultingCount) {
		this.consultingCount = consultingCount;
	}
	public Date getConsultingDate() {
		return consultingDate;
	}
	public void setConsultingDate(Date consultingDate) {
		this.consultingDate = consultingDate;
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
	public String getMainProblem() {
		return mainProblem;
	}
	public void setMainProblem(String mainProblem) {
		this.mainProblem = mainProblem;
	}
	public String getUploadImage() {
		return uploadImage;
	}
	public void setUploadImage(String uploadImage) {
		this.uploadImage = uploadImage;
	}
	public Long getGrounpId() {
		return grounpId;
	}
	public void setGrounpId(Long grounpId) {
		this.grounpId = grounpId;
	}
	public Long getIsNotSend() {
		return isNotSend;
	}
	public void setIsNotSend(Long isNotSend) {
		this.isNotSend = isNotSend;
	}
	public String getCurrentDetptMessage() {
		return currentDetptMessage;
	}
	public void setCurrentDetptMessage(String currentDetptMessage) {
		this.currentDetptMessage = currentDetptMessage;
	}
	public String getApplicationDeptMessage() {
		return applicationDeptMessage;
	}
	public void setApplicationDeptMessage(String applicationDeptMessage) {
		this.applicationDeptMessage = applicationDeptMessage;
	}
	public Integer getPcMeetingState() {
		return pcMeetingState;
	}
	public void setPcMeetingState(Integer pcMeetingState) {
		this.pcMeetingState = pcMeetingState;
	}
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	public Integer getIsNotUpdate() {
		return isNotUpdate;
	}
	public void setIsNotUpdate(Integer isNotUpdate) {
		this.isNotUpdate = isNotUpdate;
	}
}
