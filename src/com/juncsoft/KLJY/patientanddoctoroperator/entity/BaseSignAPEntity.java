package com.juncsoft.KLJY.patientanddoctoroperator.entity;

/**
 * 挂号系统上午和下午设置
 * @author liugang
 *
 */
public class BaseSignAPEntity {
	private Long id;
	private String timeAP;//A:上午;P:下午
	private String outType;//门诊类型，诊疗；专家；普通
	private Integer outCount;//预约人数
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTimeAP() {
		return timeAP;
	}
	public void setTimeAP(String timeAP) {
		this.timeAP = timeAP;
	}
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
	}
	public Integer getOutCount() {
		return outCount;
	}
	public void setOutCount(Integer outCount) {
		this.outCount = outCount;
	}
}
