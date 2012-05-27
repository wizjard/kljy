package com.juncsoft.KLJY.patientanddoctoroperator.entity;

/**
 * 上午\下午时间段分配实体
 * @author liugang
 *
 */
public class BaseSignTimeSegmentEntity {
	private Long id;
	private String timeSegment;
	private String timeAP;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTimeSegment() {
		return timeSegment;
	}
	public void setTimeSegment(String timeSegment) {
		this.timeSegment = timeSegment;
	}
	public String getTimeAP() {
		return timeAP;
	}
	public void setTimeAP(String timeAP) {
		this.timeAP = timeAP;
	}
}
