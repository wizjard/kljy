package com.juncsoft.KLJY.outoremergency.entity;

import java.util.Date;

public class OutOrMergencyPatient {
	private Long id;//自动流水号
	private String patientid;// 患者编号
	private String patientno;// 病历号
	private String societyno;// 证件号
	private String ZJtype;// 证件类型
	private String patientname;// 患者姓名
	private String sexid;// 患者性别
	private Date birthday;// 出生日期
	private Integer age;// 年龄
	private String nationalityid;// 国籍
	private String peopleid;// 民族
	private String homeaddr;// 住址
	private String homepostcode;// 邮编
	private String hometel;// 电话
	private String workorg;// 工作单位
	private String orgaddr;// 工作地址
	private String orgpostcode;// 工作单位邮编
	private String contactname;// 联系人
	private String contacttel;// 联系人电话
	private String memberType;//是否会员(0:非会员\1:会员)

	public OutOrMergencyPatient(){
		
	}
	
	public OutOrMergencyPatient(Long id,String patientid, String patientno,
			String societyno, String ZJtype, String patientname, String sexid,
			Date birthday, Integer age, String nationalityid, String peopleid,
			String homeaddr, String homepostcode, String hometel,
			String workorg, String orgaddr, String orgpostcode,
			String contactname, String contacttel,String memberType) {
		this.id = id;
		this.patientid = patientid;
		this.patientno = patientno;
		this.societyno = societyno;
		this.ZJtype = ZJtype;
		this.patientname = patientname;
		this.sexid = sexid;
		this.birthday = birthday;
		this.age = age;
		this.nationalityid = nationalityid;
		this.peopleid = peopleid;
		this.homeaddr = homeaddr;
		this.homepostcode = homepostcode;
		this.hometel = hometel;
		this.workorg = workorg;
		this.orgaddr = orgaddr;
		this.orgpostcode = orgpostcode;
		this.contactname = contactname;
		this.contacttel = contacttel;
		this.memberType = memberType;
	}

	public String getPatientid() {
		return patientid;
	}

	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}

	public String getPatientno() {
		return patientno;
	}

	public void setPatientno(String patientno) {
		this.patientno = patientno;
	}

	public String getSocietyno() {
		return societyno;
	}

	public void setSocietyno(String societyno) {
		this.societyno = societyno;
	}

	public String getZJtype() {
		return ZJtype;
	}

	public void setZJtype(String jtype) {
		ZJtype = jtype;
	}

	public String getPatientname() {
		return patientname;
	}

	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}

	public String getSexid() {
		return sexid;
	}

	public void setSexid(String sexid) {
		this.sexid = sexid;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNationalityid() {
		return nationalityid;
	}

	public void setNationalityid(String nationalityid) {
		this.nationalityid = nationalityid;
	}

	public String getPeopleid() {
		return peopleid;
	}

	public void setPeopleid(String peopleid) {
		this.peopleid = peopleid;
	}

	public String getHomeaddr() {
		return homeaddr;
	}

	public void setHomeaddr(String homeaddr) {
		this.homeaddr = homeaddr;
	}

	public String getHomepostcode() {
		return homepostcode;
	}

	public void setHomepostcode(String homepostcode) {
		this.homepostcode = homepostcode;
	}

	public String getHometel() {
		return hometel;
	}

	public void setHometel(String hometel) {
		this.hometel = hometel;
	}

	public String getWorkorg() {
		return workorg;
	}

	public void setWorkorg(String workorg) {
		this.workorg = workorg;
	}

	public String getOrgaddr() {
		return orgaddr;
	}

	public void setOrgaddr(String orgaddr) {
		this.orgaddr = orgaddr;
	}

	public String getOrgpostcode() {
		return orgpostcode;
	}

	public void setOrgpostcode(String orgpostcode) {
		this.orgpostcode = orgpostcode;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	public String getContacttel() {
		return contacttel;
	}

	public void setContacttel(String contacttel) {
		this.contacttel = contacttel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

}

