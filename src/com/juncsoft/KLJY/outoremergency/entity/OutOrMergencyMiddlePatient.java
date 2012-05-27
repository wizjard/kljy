package com.juncsoft.KLJY.outoremergency.entity;

/**
 * 中间实体用于HIS数据集成,HIS中的门急诊挂号信息
 * @author liugang
 *
 */
public class OutOrMergencyMiddlePatient {
	private String ptno;// 患者编号
	private String sname; // 患者姓名
	private String sex; // 性别
	private String birthdate; // 生日
	private String actdate; // 挂号日期
	private String deptcode; // 科室编码
	private String deptname; // 科室名称
	private String drcode; // 医生编码
	private String drname; // 医生名称
	private String bi; // 患者身份(保险区分)
	private String gbchojae; // 初/再诊
	private String gbrep; // 诊疗结束标记 0：未诊疗 1：已诊疗
	private String jtime; // 挂号日期时间
	private String restype; // 挂号/预约类型
	private String tel; // 患者联系电话
	private String lastdept; // 最近来院科室编码
	private String lastdeptname; // 最近来院科室名称
	private String lastdate; // 最近来院日期
	private String ampm; // A 上午 P 下午
	private String jinilsu; // 挂号序号
	private String regno;// 挂号流水号(每次门诊都是惟一的)
	private String delmark;// 退号 (*退号,空为正常)
	private String jumin;//证件编号
	private String jtype;//证件类型

//	public OutOrMergencyMiddlePatient(String ptno, String sname, String sex,
//			String birthdate, String actdate, String deptcode, String deptname,
//			String drcode, String drname, String bi, String gbchojae,
//			String gbrep, String jtime, String restype, String tel,
//			String lastdept, String lastdeptname, String lastdate, String ampm,
//			String jinilsu, String regno, String delmark) {
//	}

	public String getDelmark() {
		return delmark;
	}

	public void setDelmark(String delmark) {
		this.delmark = delmark;
	}

	public String getPtno() {
		return ptno;
	}

	public void setPtno(String ptno) {
		this.ptno = ptno;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getActdate() {
		return actdate;
	}

	public void setActdate(String actdate) {
		this.actdate = actdate;
	}

	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getDrcode() {
		return drcode;
	}

	public void setDrcode(String drcode) {
		this.drcode = drcode;
	}

	public String getDrname() {
		return drname;
	}

	public void setDrname(String drname) {
		this.drname = drname;
	}

	public String getBi() {
		return bi;
	}

	public void setBi(String bi) {
		this.bi = bi;
	}

	public String getGbchojae() {
		return gbchojae;
	}

	public void setGbchojae(String gbchojae) {
		this.gbchojae = gbchojae;
	}

	public String getGbrep() {
		return gbrep;
	}

	public void setGbrep(String gbrep) {
		this.gbrep = gbrep;
	}

	public String getJtime() {
		return jtime;
	}

	public void setJtime(String jtime) {
		this.jtime = jtime;
	}

	public String getRestype() {
		return restype;
	}

	public void setRestype(String restype) {
		this.restype = restype;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getLastdept() {
		return lastdept;
	}

	public void setLastdept(String lastdept) {
		this.lastdept = lastdept;
	}

	public String getLastdeptname() {
		return lastdeptname;
	}

	public void setLastdeptname(String lastdeptname) {
		this.lastdeptname = lastdeptname;
	}

	public String getLastdate() {
		return lastdate;
	}

	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}

	public String getAmpm() {
		return ampm;
	}

	public void setAmpm(String ampm) {
		this.ampm = ampm;
	}

	public String getJinilsu() {
		return jinilsu;
	}

	public void setJinilsu(String jinilsu) {
		this.jinilsu = jinilsu;
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public String getJumin() {
		return jumin;
	}

	public void setJumin(String jumin) {
		this.jumin = jumin;
	}

	public String getJtype() {
		return jtype;
	}

	public void setJtype(String jtype) {
		this.jtype = jtype;
	}
}
