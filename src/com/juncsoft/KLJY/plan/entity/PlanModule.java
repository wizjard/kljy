package com.juncsoft.KLJY.plan.entity;

import java.util.Date;

import com.juncsoft.KLJY.user.entity.User;

public class PlanModule {
	private Long id;  //计划模板Id
	private String moduleName; //计划模板名称
	private User user;  //是哪个医生的模板
	private String createDate;  //该模板的创建时间   字符串类型  格式为yyyy-MM-dd
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
