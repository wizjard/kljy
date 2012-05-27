package com.juncsoft.KLJY.biomedical.member.entity;

import java.util.Date;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;

public class HealthRecord {
	private Long id;//主键
	private MemberInfo mem;//会员
	private Date registDate;//登记日期
	private String symptom;//症状
	private String shiliang;//食量
	private String shuimian;//睡眠
	private String tizhong;//体重
	private String jingshen;//精神
	private String niaoliang;//尿量
	private String dabian;//大便
	private String yanbian;//病情演变
	private String zhiliao;//目前治疗情况
	private String haozhuan;//好转方面
	private String jiuzhen;//就诊目标
	public MemberInfo getMem() {
		return mem;
	}
	public void setMem(MemberInfo mem) {
		this.mem = mem;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	public String getShiliang() {
		return shiliang;
	}
	public void setShiliang(String shiliang) {
		this.shiliang = shiliang;
	}
	public String getShuimian() {
		return shuimian;
	}
	public void setShuimian(String shuimian) {
		this.shuimian = shuimian;
	}
	public String getTizhong() {
		return tizhong;
	}
	public void setTizhong(String tizhong) {
		this.tizhong = tizhong;
	}
	public String getJingshen() {
		return jingshen;
	}
	public void setJingshen(String jingshen) {
		this.jingshen = jingshen;
	}
	public String getNiaoliang() {
		return niaoliang;
	}
	public void setNiaoliang(String niaoliang) {
		this.niaoliang = niaoliang;
	}
	public String getDabian() {
		return dabian;
	}
	public void setDabian(String dabian) {
		this.dabian = dabian;
	}
	public String getYanbian() {
		return yanbian;
	}
	public void setYanbian(String yanbian) {
		this.yanbian = yanbian;
	}
	public String getZhiliao() {
		return zhiliao;
	}
	public void setZhiliao(String zhiliao) {
		this.zhiliao = zhiliao;
	}
	public String getHaozhuan() {
		return haozhuan;
	}
	public void setHaozhuan(String haozhuan) {
		this.haozhuan = haozhuan;
	}
	public String getJiuzhen() {
		return jiuzhen;
	}
	public void setJiuzhen(String jiuzhen) {
		this.jiuzhen = jiuzhen;
	}
}
