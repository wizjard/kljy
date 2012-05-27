package com.juncsoft.KLJY.biomedical.entity;

import java.util.Date;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.patient.entity.Patient;

public class MemberInfo {
	private String memberNo;// 会员编号，自动生成，主键
	private String account;// 账号
	private String password;// 密码
	private Date inDate;// 入会日期
	private String inWard;// 入会科室
	private String memberStatus;// 会员状态
	private String memberType;// 会员类型
	private String memo;// 会员备注
	private Patient patient;// 会员关联的病人信息
	private InHspCaseMaster cm;// 住院会员入会大病历
	private String currentGroup;// 当前分组
	private String currentWard;//当前病房
	private String inDoctor;//入会医生
	private int biaoben;//是否预留标本
	private String inHspTimes;//会员入会关联住院病历的住院次数
	private String deptCode;//会员所在科室
	private Long grounpId;//会员所在科室下的小组
	private String deptName;//会员所在科室名称
	private String grounpName;//会员所在科室的责任小组名称
	private String inWardCode;//入会科室Code
	private Date inOutTimesDate;//会员入会关联门诊病历的住院时间

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getGrounpName() {
		return grounpName;
	}

	public void setGrounpName(String grounpName) {
		this.grounpName = grounpName;
	}

	public String getCurrentWard() {
		return currentWard;
	}

	public void setCurrentWard(String currentWard) {
		this.currentWard = currentWard;
	}

	public String getInDoctor() {
		return inDoctor;
	}

	public void setInDoctor(String inDoctor) {
		this.inDoctor = inDoctor;
	}

	public String getInHspTimes() {
		return inHspTimes;
	}

	public void setInHspTimes(String inHspTimes) {
		this.inHspTimes = inHspTimes;
	}

	public int getBiaoben() {
		return biaoben;
	}

	public void setBiaoben(int biaoben) {
		this.biaoben = biaoben;
	}

	public String getInWard() {
		return inWard;
	}

	public void setInWard(String inWard) {
		this.inWard = inWard;
	}

	public String getCurrentGroup() {
		return currentGroup;
	}

	public void setCurrentGroup(String currentGroup) {
		this.currentGroup = currentGroup;
	}

	public InHspCaseMaster getCm() {
		return cm;
	}

	public void setCm(InHspCaseMaster cm) {
		this.cm = cm;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
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

	public String getInWardCode() {
		return inWardCode;
	}

	public void setInWardCode(String inWardCode) {
		this.inWardCode = inWardCode;
	}

	public Date getInOutTimesDate() {
		return inOutTimesDate;
	}

	public void setInOutTimesDate(Date inOutTimesDate) {
		this.inOutTimesDate = inOutTimesDate;
	}
}
