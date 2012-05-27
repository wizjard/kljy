package com.juncsoft.KLJY.checkreport.entity;

import java.util.Date;

public class Maidi {
	private Long id;
	private String patientId;
	private String patientName;
	private String checkNum;
	private String checkAutoNum;
	private String checkDate;//检查日期 1
	private String checkItem;//检查项目 1
	private String equipmentType;
	private String checkPart;
	private String imageView;
	private String reportResult;//检查结果 1
	private String inHspNum;//住院号
	private String outNum;//门诊号
	private String reportName;
	private String reportFileName;
	private String isPositive;
	private String checkCode;
	private String codeSystem;
	private String codeSystemName;
	private String displayName;
	
	private Integer isFromOutHospital;//区分本院与外院手动录入的化验单数据 1：表示外院 null 表示 本院，(程序导入lis中的数据)
	private Integer isPatientOrDoctorWriteZanCun;//区分是会员还是责任医生录入，10:表示会员暂存，20:表示医生暂存，11:表示会员提交给责任医生，21:表示医生把自己录入的化验或者病人录入的化验提交归档
	private Date sheHeDate;// 病人提交给责任医生的时间
	private Date guiDangDate;//医生提交该化验单为归档状态的日期
	private Date luRuDate;//录入日期
	private Integer cheXiaoFlag;//管理员撤销录入的化验单，医生可以开始修改
	private Integer cheXiaoTrue;//第几次撤销 2表示撤销后已经提交，或者归档， 1表示可以正常使用 
	private String luRuAuthor;//录入者 1
	private String duiDangAuthor;//归档者 1
	private String age;//当时检查年龄 1
	private String check_danwei;//检查单位
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}
	public String getCheckAutoNum() {
		return checkAutoNum;
	}
	public void setCheckAutoNum(String checkAutoNum) {
		this.checkAutoNum = checkAutoNum;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getCheckItem() {
		return checkItem;
	}
	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getCheckPart() {
		return checkPart;
	}
	public void setCheckPart(String checkPart) {
		this.checkPart = checkPart;
	}
	public String getImageView() {
		return imageView;
	}
	public void setImageView(String imageView) {
		this.imageView = imageView;
	}
	public String getReportResult() {
		return reportResult;
	}
	public void setReportResult(String reportResult) {
		this.reportResult = reportResult;
	}
	public String getInHspNum() {
		return inHspNum;
	}
	public void setInHspNum(String inHspNum) {
		this.inHspNum = inHspNum;
	}
	public String getOutNum() {
		return outNum;
	}
	public void setOutNum(String outNum) {
		this.outNum = outNum;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportFileName() {
		return reportFileName;
	}
	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
	public String getIsPositive() {
		return isPositive;
	}
	public void setIsPositive(String isPositive) {
		this.isPositive = isPositive;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getCodeSystem() {
		return codeSystem;
	}
	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}
	public String getCodeSystemName() {
		return codeSystemName;
	}
	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Integer getIsFromOutHospital() {
		return isFromOutHospital;
	}
	public void setIsFromOutHospital(Integer isFromOutHospital) {
		this.isFromOutHospital = isFromOutHospital;
	}
	public Integer getIsPatientOrDoctorWriteZanCun() {
		return isPatientOrDoctorWriteZanCun;
	}
	public void setIsPatientOrDoctorWriteZanCun(Integer isPatientOrDoctorWriteZanCun) {
		this.isPatientOrDoctorWriteZanCun = isPatientOrDoctorWriteZanCun;
	}
	public Date getSheHeDate() {
		return sheHeDate;
	}
	public void setSheHeDate(Date sheHeDate) {
		this.sheHeDate = sheHeDate;
	}
	public Date getGuiDangDate() {
		return guiDangDate;
	}
	public void setGuiDangDate(Date guiDangDate) {
		this.guiDangDate = guiDangDate;
	}
	public Date getLuRuDate() {
		return luRuDate;
	}
	public void setLuRuDate(Date luRuDate) {
		this.luRuDate = luRuDate;
	}
	public Integer getCheXiaoFlag() {
		return cheXiaoFlag;
	}
	public void setCheXiaoFlag(Integer cheXiaoFlag) {
		this.cheXiaoFlag = cheXiaoFlag;
	}
	public Integer getCheXiaoTrue() {
		return cheXiaoTrue;
	}
	public void setCheXiaoTrue(Integer cheXiaoTrue) {
		this.cheXiaoTrue = cheXiaoTrue;
	}
	public String getLuRuAuthor() {
		return luRuAuthor;
	}
	public void setLuRuAuthor(String luRuAuthor) {
		this.luRuAuthor = luRuAuthor;
	}
	public String getDuiDangAuthor() {
		return duiDangAuthor;
	}
	public void setDuiDangAuthor(String duiDangAuthor) {
		this.duiDangAuthor = duiDangAuthor;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getCheck_danwei() {
		return check_danwei;
	}
	public void setCheck_danwei(String check_danwei) {
		this.check_danwei = check_danwei;
	}
	

}
