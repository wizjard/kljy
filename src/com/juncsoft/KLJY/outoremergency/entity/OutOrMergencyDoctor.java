package com.juncsoft.KLJY.outoremergency.entity;

/**
 * 中间实体用于HIS数据集成,HIS中的医生基本信息
 * 
 * @author liugang
 * 
 */
public class OutOrMergencyDoctor {
	private String idnumber;// 医生编码
	private String name;// 医生名称
	private String password;// 密码
	private String buse;// 部门
	private String gbipd;// 住院医生 1：是 0：否
	private String gbjupsu;// 可否挂号 1：是 0：否
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
	private String telno;// 电话
	private String gbjaejik;// 在职状态
	private String drcode;// 医生号码
	private String drgrade;// 医生职称
	private String vestdept;// 门诊费用归属科室
	private String gbspc;// 是否专家
	private String email;// e-mail
	private String remark;// 医生特长

	public OutOrMergencyDoctor(String idnumber, String name, String password,
			String buse, String gbipd, String gbjupsu, String deptcode,
			String deptcodename, String drdept1, String drdeptname1,
			String drdept2, String drdeptname2, String drdept3,
			String drdeptname3, String drdept4, String drdeptname4,
			String drdept5, String drdeptname5, String telno, String gbjaejik,
			String drcode, String drgrade, String vestdept, String gbspc,
			String email, String remark) {
		this.idnumber = idnumber;
		this.name = name;
		this.password = password;
		this.buse = buse;
		this.gbipd = gbipd;
		this.gbjupsu = gbjupsu;
		this.deptcode = deptcode;
		this.deptcodename = deptcodename;
		this.drdept1 = drdept1;
		this.drdeptname1 = drdeptname1;
		this.drdept2 = drdept2;
		this.drdeptname2 = drdeptname2;
		this.drdept3 = drdept3;
		this.drdeptname3 = drdeptname3;
		this.drdept4 = drdept4;
		this.drdeptname4 = drdeptname4;
		this.drdept5 = drdept5;
		this.drdeptname5 = drdeptname5;
		this.telno = telno;
		this.gbjaejik = gbjaejik;
		this.drcode = drcode;
		this.drgrade = drgrade;
		this.vestdept = vestdept;
		this.gbspc = gbspc;
		this.email = email;
		this.remark = remark;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBuse() {
		return buse;
	}

	public void setBuse(String buse) {
		this.buse = buse;
	}

	public String getGbipd() {
		return gbipd;
	}

	public void setGbipd(String gbipd) {
		this.gbipd = gbipd;
	}

	public String getGbjupsu() {
		return gbjupsu;
	}

	public void setGbjupsu(String gbjupsu) {
		this.gbjupsu = gbjupsu;
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

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getGbjaejik() {
		return gbjaejik;
	}

	public void setGbjaejik(String gbjaejik) {
		this.gbjaejik = gbjaejik;
	}

	public String getDrcode() {
		return drcode;
	}

	public void setDrcode(String drcode) {
		this.drcode = drcode;
	}

	public String getDrgrade() {
		return drgrade;
	}

	public void setDrgrade(String drgrade) {
		this.drgrade = drgrade;
	}

	public String getGbspc() {
		return gbspc;
	}

	public void setGbspc(String gbspc) {
		this.gbspc = gbspc;
	}

	public String getVestdept() {
		return vestdept;
	}

	public void setVestdept(String vestdept) {
		this.vestdept = vestdept;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
