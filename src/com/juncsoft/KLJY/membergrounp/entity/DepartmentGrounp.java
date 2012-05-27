package com.juncsoft.KLJY.membergrounp.entity;

/**
 * 科室下的组别名称，及一些具体的成员名称
 * @author liugang
 *
 */
public class DepartmentGrounp {
	private Long id;
	private String grounpName;//组名
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGrounpName() {
		return grounpName;
	}
	public void setGrounpName(String grounpName) {
		this.grounpName = grounpName;
	}
}


