package com.juncsoft.KLJY.membergrounp.entity;

/**
 * 科室下的小组 对入会病人使用
 * @author liugang
 *
 */
public class DepartmentAndGrounpMiddle {
	private Long id;
	private String deptCode;//科室编号
	private Long grounpId;//组别编号
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public Long getGrounpId() {
		return grounpId;
	}
	public void setGrounpId(Long grounpId) {
		this.grounpId = grounpId;
	}
}
