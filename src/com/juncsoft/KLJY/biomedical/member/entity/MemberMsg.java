package com.juncsoft.KLJY.biomedical.member.entity;

import java.util.Date;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;

public class MemberMsg {
	private Long id;//主键
	private Date askTime;//申请时间
	private String ask;//申请内容
	private String ward;//申请科室
	private Date answerDate;//回复时间
	private String answer;//回复内容
	private HealthRecord hr;//关联的健康记录
	private MemberInfo mem;//提交申请的会员
	private String doctor;//回复的医生
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getAskTime() {
		return askTime;
	}
	public void setAskTime(Date askTime) {
		this.askTime = askTime;
	}
	public String getAsk() {
		return ask;
	}
	public void setAsk(String ask) {
		this.ask = ask;
	}
	public Date getAnswerDate() {
		return answerDate;
	}
	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public HealthRecord getHr() {
		return hr;
	}
	public void setHr(HealthRecord hr) {
		this.hr = hr;
	}
	public MemberInfo getMem() {
		return mem;
	}
	public void setMem(MemberInfo mem) {
		this.mem = mem;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
}
