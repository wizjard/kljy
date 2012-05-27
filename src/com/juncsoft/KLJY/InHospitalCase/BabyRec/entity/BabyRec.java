package com.juncsoft.KLJY.InHospitalCase.BabyRec.entity;

import java.util.Date;

public class BabyRec {

	private int id;
	private String name;
	private int gender;
	private Date birthDate;
	private String birthWeight;
	private int pid;
	private int pcid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getPcid() {
		return pcid;
	}
	public void setPcid(int pcid) {
		this.pcid = pcid;
	}
	
	public String getBirthWeight() {
		return birthWeight;
	}
	public void setBirthWeight(String birthWeight) {
		this.birthWeight = birthWeight;
	}
	public BabyRec(){
		id = 0;
	}
}
