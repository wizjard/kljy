/**
 * auther:lisht createOrEditDate：Oct 20, 2010   12:49:42 PM
 * projectName：TCMP1  fileLaction： com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.ContinuePage_Doctor.java
 */
package com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity;

import java.util.Date;

/**
 * auther:lisht createOrEditDate：Oct 20, 2010 12:49:42 PM 病案首页续页--医生部分
 * class、method、Attribute or logic explain：
 * 
 */
public class ContinuePage_Doctor {
	private Long id;
	private Long caseId;
	private String kezhuren_show;
	private String directorDoctorId_show;
	private String treatDoctorId_show;
	private String inhspDoctorId_show;
	private String jinxiu_show;
	private String yanjiusheng_show;
	private String shixi_show;
	private String bianma;
	private String bingan;
	private String qcyishi;
	private String qchushi;
	private Date badate;
	private String state;
	// 基本信息
	private String patientName;
	private String gender;
	private Date birthday;
	private String age;
	private String zycs;
	private String zyh;

	public String getZycs() {
		return zycs;
	}

	public void setZycs(String zycs) {
		this.zycs = zycs;
	}

	public String getZyh() {
		return zyh;
	}

	public void setZyh(String zyh) {
		this.zyh = zyh;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getKezhuren_show() {
		return kezhuren_show;
	}

	public void setKezhuren_show(String kezhuren_show) {
		this.kezhuren_show = kezhuren_show;
	}

	public String getDirectorDoctorId_show() {
		return directorDoctorId_show;
	}

	public void setDirectorDoctorId_show(String directorDoctorId_show) {
		this.directorDoctorId_show = directorDoctorId_show;
	}

	public String getTreatDoctorId_show() {
		return treatDoctorId_show;
	}

	public void setTreatDoctorId_show(String treatDoctorId_show) {
		this.treatDoctorId_show = treatDoctorId_show;
	}

	public String getInhspDoctorId_show() {
		return inhspDoctorId_show;
	}

	public void setInhspDoctorId_show(String inhspDoctorId_show) {
		this.inhspDoctorId_show = inhspDoctorId_show;
	}

	public String getJinxiu_show() {
		return jinxiu_show;
	}

	public void setJinxiu_show(String jinxiu_show) {
		this.jinxiu_show = jinxiu_show;
	}

	public String getYanjiusheng_show() {
		return yanjiusheng_show;
	}

	public void setYanjiusheng_show(String yanjiusheng_show) {
		this.yanjiusheng_show = yanjiusheng_show;
	}

	public String getShixi_show() {
		return shixi_show;
	}

	public void setShixi_show(String shixi_show) {
		this.shixi_show = shixi_show;
	}

	public String getBianma() {
		return bianma;
	}

	public void setBianma(String bianma) {
		this.bianma = bianma;
	}

	public String getBingan() {
		return bingan;
	}

	public void setBingan(String bingan) {
		this.bingan = bingan;
	}

	public String getQcyishi() {
		return qcyishi;
	}

	public void setQcyishi(String qcyishi) {
		this.qcyishi = qcyishi;
	}

	public String getQchushi() {
		return qchushi;
	}

	public void setQchushi(String qchushi) {
		this.qchushi = qchushi;
	}

	public Date getBadate() {
		return badate;
	}

	public void setBadate(Date badate) {
		this.badate = badate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
