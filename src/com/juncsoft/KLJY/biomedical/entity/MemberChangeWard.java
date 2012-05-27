package com.juncsoft.KLJY.biomedical.entity;

import java.util.Date;

public class MemberChangeWard {
	private Long id;
	private String memNo;
	private Date changeDate;
	private String ward;
	private String memo;
	public Long getId() {
		return id;
	}
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
