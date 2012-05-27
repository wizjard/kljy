package com.juncsoft.KLJY.user.entity;

import java.util.Date;

public class User {
	private Long id;
	private String name;
	private String account;
	private String password;
	private String role;
	private String belong;
	private Date birthday;
	private Integer sex;
	private String phoneNo;
	private String email;
	private String selfMemo;
	private int valid;
	private Date createDate;
	private Date modifyDate;
	private String unkown1;//无效字段1替死鬼
	private String deptcode;// 科室代码1
	private String deptcodename;// 科室名称1
	private String drdept1;// 科室代码2
	private String drdeptname1;// 科室名称2
	private String drdept2;// 科室代码3
	private String drdeptname2;// 科室名称3
	private String drdept3;// 科室代码4
	private String drdeptname3;// 科室名称4
	private String drdept4;// 科室代码5
	private String drdeptname4;// 科室名称5
	private String drdept5;// 科室代码6
	private String drdeptname5;// 科室名称6
	private String hisDoctorId;//HIS中医生编号
	private String gbjaejik;// 在职状态
	private String drgrade;// 医生职称
	private String vestdept;// 门诊费用归属科室
	private String gbspc;// 是否专家
	private String gbjupsu;// 可否挂号 1：是 0：否
	private String hisPassword;//HIS中的密码�е�����
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSelfMemo() {
		return selfMemo;
	}
	public void setSelfMemo(String selfMemo) {
		this.selfMemo = selfMemo;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public String getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	public String getDeptcodename() {
		return deptcodename;
	}
	public void setDeptcodename(String deptcodename) {
		this.deptcodename = deptcodename;
	}
	public String getDrdept1() {
		return drdept1;
	}
	public void setDrdept1(String drdept1) {
		this.drdept1 = drdept1;
	}
	public String getDrdeptname1() {
		return drdeptname1;
	}
	public void setDrdeptname1(String drdeptname1) {
		this.drdeptname1 = drdeptname1;
	}
	public String getDrdept2() {
		return drdept2;
	}
	public void setDrdept2(String drdept2) {
		this.drdept2 = drdept2;
	}
	public String getDrdeptname2() {
		return drdeptname2;
	}
	public void setDrdeptname2(String drdeptname2) {
		this.drdeptname2 = drdeptname2;
	}
	public String getDrdept3() {
		return drdept3;
	}
	public void setDrdept3(String drdept3) {
		this.drdept3 = drdept3;
	}
	public String getDrdeptname3() {
		return drdeptname3;
	}
	public void setDrdeptname3(String drdeptname3) {
		this.drdeptname3 = drdeptname3;
	}
	public String getDrdept4() {
		return drdept4;
	}
	public void setDrdept4(String drdept4) {
		this.drdept4 = drdept4;
	}
	public String getDrdeptname4() {
		return drdeptname4;
	}
	public void setDrdeptname4(String drdeptname4) {
		this.drdeptname4 = drdeptname4;
	}
	public String getDrdept5() {
		return drdept5;
	}
	public void setDrdept5(String drdept5) {
		this.drdept5 = drdept5;
	}
	public String getDrdeptname5() {
		return drdeptname5;
	}
	public void setDrdeptname5(String drdeptname5) {
		this.drdeptname5 = drdeptname5;
	}
	public String getHisDoctorId() {
		return hisDoctorId;
	}
	public void setHisDoctorId(String hisDoctorId) {
		this.hisDoctorId = hisDoctorId;
	}
	public String getUnkown1() {
		return unkown1;
	}
	public void setUnkown1(String unkown1) {
		this.unkown1 = unkown1;
	}
	public String getGbjaejik() {
		return gbjaejik;
	}
	public void setGbjaejik(String gbjaejik) {
		this.gbjaejik = gbjaejik;
	}
	public String getDrgrade() {
		return drgrade;
	}
	public void setDrgrade(String drgrade) {
		this.drgrade = drgrade;
	}
	public String getVestdept() {
		return vestdept;
	}
	public void setVestdept(String vestdept) {
		this.vestdept = vestdept;
	}
	public String getGbspc() {
		return gbspc;
	}
	public void setGbspc(String gbspc) {
		this.gbspc = gbspc;
	}
	public String getGbjupsu() {
		return gbjupsu;
	}
	public void setGbjupsu(String gbjupsu) {
		this.gbjupsu = gbjupsu;
	}
	public String getHisPassword() {
		return hisPassword;
	}
	public void setHisPassword(String hisPassword) {
		this.hisPassword = hisPassword;
	}
}
