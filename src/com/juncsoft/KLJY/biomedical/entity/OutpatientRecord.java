package com.juncsoft.KLJY.biomedical.entity;

import java.util.Date;

import com.juncsoft.KLJY.patient.entity.Patient;

public class OutpatientRecord {
	private Long id;//主键
	private Patient patient;//关联的病人
	private OutpatientGenerator generator1;//生成本次门诊随访的生成器
	private OutpatientGenerator generator2;//生成下次门诊随访的生成器
	private Date comeDate;//门诊日期
	private int times;//门诊次数
	private String zhusu;//主诉
	private String xianbingshi;//现病史
	private String qitabingshi;//其它病史
	private String tige;//体格检查
	private String fuzhu;//实验室辅助检查
	private String zhenduan;//初步诊断
	private String jianyi;//治疗及建议
	private String beizhu;//备注
	private String yisheng;//就诊医生
	private int biaoben;//是否预留标本
	public int getBiaoben() {
		return biaoben;
	}
	public void setBiaoben(int biaoben) {
		this.biaoben = biaoben;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public String getZhusu() {
		return zhusu;
	}
	public void setZhusu(String zhusu) {
		this.zhusu = zhusu;
	}
	public String getXianbingshi() {
		return xianbingshi;
	}
	public void setXianbingshi(String xianbingshi) {
		this.xianbingshi = xianbingshi;
	}
	public String getQitabingshi() {
		return qitabingshi;
	}
	public void setQitabingshi(String qitabingshi) {
		this.qitabingshi = qitabingshi;
	}
	public String getTige() {
		return tige;
	}
	public void setTige(String tige) {
		this.tige = tige;
	}
	public String getFuzhu() {
		return fuzhu;
	}
	public void setFuzhu(String fuzhu) {
		this.fuzhu = fuzhu;
	}
	public String getZhenduan() {
		return zhenduan;
	}
	public void setZhenduan(String zhenduan) {
		this.zhenduan = zhenduan;
	}
	public String getJianyi() {
		return jianyi;
	}
	public void setJianyi(String jianyi) {
		this.jianyi = jianyi;
	}
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	public String getYisheng() {
		return yisheng;
	}
	public void setYisheng(String yisheng) {
		this.yisheng = yisheng;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public OutpatientGenerator getGenerator1() {
		return generator1;
	}
	public void setGenerator1(OutpatientGenerator generator1) {
		this.generator1 = generator1;
	}
	public OutpatientGenerator getGenerator2() {
		return generator2;
	}
	public void setGenerator2(OutpatientGenerator generator2) {
		this.generator2 = generator2;
	}
	public Date getComeDate() {
		return comeDate;
	}
	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}
	
}
