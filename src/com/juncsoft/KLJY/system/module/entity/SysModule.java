package com.juncsoft.KLJY.system.module.entity;

public class SysModule {
	private Long id;
	private String moduleCode;
	private String moduleName;
	private String parentModuleCode;
	private String icon;
	private String onClick;
	private Integer orderNo;
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getParentModuleCode() {
		return parentModuleCode;
	}
	public void setParentModuleCode(String parentModuleCode) {
		this.parentModuleCode = parentModuleCode;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getOnClick() {
		return onClick;
	}
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
}
