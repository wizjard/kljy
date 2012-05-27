package com.juncsoft.KLJY.system.Research.entity;

public class ResearchMember {
	private Long id;
	private String name;
	private String gender;
	private String birthday;
	private String mem_title;
	private String mem_positon;
	private String professional;
	private String workVol;
	private String task;
	private String belongUnit;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMem_title() {
		return mem_title;
	}
	public void setMem_title(String memTitle) {
		mem_title = memTitle;
	}
	public String getMem_positon() {
		return mem_positon;
	}
	public void setMem_positon(String memPositon) {
		mem_positon = memPositon;
	}
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getWorkVol() {
		return workVol;
	}
	public void setWorkVol(String workVol) {
		this.workVol = workVol;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getBelongUnit() {
		return belongUnit;
	}
	public void setBelongUnit(String belongUnit) {
		this.belongUnit = belongUnit;
	}
}
