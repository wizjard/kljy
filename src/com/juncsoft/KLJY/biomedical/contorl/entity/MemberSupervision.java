package com.juncsoft.KLJY.biomedical.contorl.entity;

public class MemberSupervision {

	private String year;
	private String month;
	private String dept;
	private Long newMember;
	private Long totalMember;
	private Long curMember;
	private Long consultCount;
	private Long webWardCount;
	private Long docReplyCount;
	private Long outCount;
	private Long outFollowTimes;
	private Long inHspCount;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Long getNewMember() {
		return newMember;
	}
	public void setNewMember(Long newMember) {
		this.newMember = newMember;
	}
	public Long getTotalMember() {
		return totalMember;
	}
	public void setTotalMember(Long totalMember) {
		this.totalMember = totalMember;
	}
	public Long getCurMember() {
		return curMember;
	}
	public void setCurMember(Long curMember) {
		this.curMember = curMember;
	}
	public Long getConsultCount() {
		return consultCount;
	}
	public void setConsultCount(Long consultCount) {
		this.consultCount = consultCount;
	}
	public Long getWebWardCount() {
		return webWardCount;
	}
	public void setWebWardCount(Long webWardCount) {
		this.webWardCount = webWardCount;
	}
	public Long getDocReplyCount() {
		return docReplyCount;
	}
	public void setDocReplyCount(Long docReplyCount) {
		this.docReplyCount = docReplyCount;
	}
	
	public Long getOutCount() {
		return outCount;
	}
	public void setOutCount(Long outCount) {
		this.outCount = outCount;
	}

	public Long getOutFollowTimes() {
		return outFollowTimes;
	}
	public void setOutFollowTimes(Long outFollowTimes) {
		this.outFollowTimes = outFollowTimes;
	}
	public Long getInHspCount() {
		return inHspCount;
	}
	public void setInHspCount(Long inHspCount) {
		this.inHspCount = inHspCount;
	}

	
}
