package com.juncsoft.KLJY.membergrounp.entity;

/**
 * 小组和成员之间的对照表
 * @author liugang
 *
 */
public class MemberGrounpAndDoctor {
	private Long id;
	private Long grounpId;
	private Long doctorId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public Long getGrounpId() {
		return grounpId;
	}
	public void setGrounpId(Long grounpId) {
		this.grounpId = grounpId;
	}
}
