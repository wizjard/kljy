package com.juncsoft.KLJY.patient.entity;

import java.util.Date;
/**
 * 注释
 * @author liugang 2011-04-28
 *
 */
public class Patient{
	private Long id;
	private String patientNo;//病案号
	private String patientName;//姓名
	private String gender;//性别(0：未知的性别；1:男；2：女；9：未说明的性别)
	private Date birthday;//出生日期
	private String idType;//证件类型(居民身份证\护照\军官证)
	private String idType0;//没有用到()
	private String idNo;//证件号码
	private String nationality;//国籍
	private String nationality0;//没有用到
	private String people;//民族
	private String people0;//没有用到()
	private String province;//籍贯
	private String province0;//没有用到()
	private String occupation;//职业
	private String occupation0;//没有用到()
	private String homeTel;//家庭电话
	private String homePostCode;//邮政编码
	private String homeAddr;//家庭地址
	private String marrageStatus;//婚姻状态
	private String marrageStatus0;//没有用到()
	private String educationLv;//教育程度
	private String educationLv0;//没有用到()
	private String mobilePhone;//手机
	private String email;//电子邮箱
	private String workUnit;//单位名称
	private String workUnitAddr;//单位地址
	private String workUnitTel;//单位电话
	private String workUnitPostCode;//单位邮政编码
	private String contacterName;//其他联系人姓名
	private String contacterRelationship;//与患者关系
	private String contacterRelationship0;//没有用到()
	private String contacterTel;//其他联系人电话
	private Date createDate;//创建日期
	private Date modifyDate;//修改日期
	private String currentWardCode;//当前科室
	//2011-05-02门诊数据集成时添加字段
	private String patientId;//患者编号 （对应HIS中的患者编号）
	private String outDeptCode;//门诊科室
	private String isOut;//判断是否是门诊病人
	private String diagGrounp;//当前疾病分组
	
	public String getCurrentWardCode() {
		return currentWardCode;
	}
	public void setCurrentWardCode(String currentWardCode) {
		this.currentWardCode = currentWardCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPatientNo() {
		return patientNo;
	}
	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdType0() {
		return idType0;
	}
	public void setIdType0(String idType0) {
		this.idType0 = idType0;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getNationality0() {
		return nationality0;
	}
	public void setNationality0(String nationality0) {
		this.nationality0 = nationality0;
	}
	public String getPeople() {
		return people;
	}
	public void setPeople(String people) {
		this.people = people;
	}
	public String getPeople0() {
		return people0;
	}
	public void setPeople0(String people0) {
		this.people0 = people0;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvince0() {
		return province0;
	}
	public void setProvince0(String province0) {
		this.province0 = province0;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getOccupation0() {
		return occupation0;
	}
	public void setOccupation0(String occupation0) {
		this.occupation0 = occupation0;
	}
	public String getHomeTel() {
		return homeTel;
	}
	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}
	public String getHomePostCode() {
		return homePostCode;
	}
	public void setHomePostCode(String homePostCode) {
		this.homePostCode = homePostCode;
	}
	public String getHomeAddr() {
		return homeAddr;
	}
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}
	public String getMarrageStatus() {
		return marrageStatus;
	}
	public void setMarrageStatus(String marrageStatus) {
		this.marrageStatus = marrageStatus;
	}
	public String getMarrageStatus0() {
		return marrageStatus0;
	}
	public void setMarrageStatus0(String marrageStatus0) {
		this.marrageStatus0 = marrageStatus0;
	}
	public String getEducationLv() {
		return educationLv;
	}
	public void setEducationLv(String educationLv) {
		this.educationLv = educationLv;
	}
	public String getEducationLv0() {
		return educationLv0;
	}
	public void setEducationLv0(String educationLv0) {
		this.educationLv0 = educationLv0;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getWorkUnitAddr() {
		return workUnitAddr;
	}
	public void setWorkUnitAddr(String workUnitAddr) {
		this.workUnitAddr = workUnitAddr;
	}
	public String getWorkUnitTel() {
		return workUnitTel;
	}
	public void setWorkUnitTel(String workUnitTel) {
		this.workUnitTel = workUnitTel;
	}
	public String getWorkUnitPostCode() {
		return workUnitPostCode;
	}
	public void setWorkUnitPostCode(String workUnitPostCode) {
		this.workUnitPostCode = workUnitPostCode;
	}
	public String getContacterName() {
		return contacterName;
	}
	public void setContacterName(String contacterName) {
		this.contacterName = contacterName;
	}
	public String getContacterRelationship() {
		return contacterRelationship;
	}
	public void setContacterRelationship(String contacterRelationship) {
		this.contacterRelationship = contacterRelationship;
	}
	public String getContacterRelationship0() {
		return contacterRelationship0;
	}
	public void setContacterRelationship0(String contacterRelationship0) {
		this.contacterRelationship0 = contacterRelationship0;
	}
	public String getContacterTel() {
		return contacterTel;
	}
	public void setContacterTel(String contacterTel) {
		this.contacterTel = contacterTel;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getOutDeptCode() {
		return outDeptCode;
	}
	public void setOutDeptCode(String outDeptCode) {
		this.outDeptCode = outDeptCode;
	}
	public String getIsOut() {
		return isOut;
	}
	public void setIsOut(String isOut) {
		this.isOut = isOut;
	}
	public String getDiagGrounp() {
		return diagGrounp;
	}
	public void setDiagGrounp(String diagGrounp) {
		this.diagGrounp = diagGrounp;
	}
}
