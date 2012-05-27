/**
 * auther:lisht createOrEditDate：Oct 20, 2010   11:38:59 AM
 * projectName：TCMP1  fileLaction： com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.ContinuePage_Sysptom.java
 */
package com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity;

/**
 * auther:lisht createOrEditDate：Oct 20, 2010 11:38:59 AM 病案首页续页--出院情况部分实体
 * class、method、Attribute or logic explain：
 * 
 */
public class ContinuePage_Sysptom {
	private Long id;
	private Long caseId;
	private String sysptom;
	private String leaveHospital;
	private String icd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getLeaveHospital() {
		return leaveHospital;
	}

	public void setLeaveHospital(String leaveHospital) {
		this.leaveHospital = leaveHospital;
	}

	public String getIcd() {
		return icd;
	}

	public void setIcd(String icd) {
		this.icd = icd;
	}

	public String getSysptom() {
		return sysptom;
	}

	public void setSysptom(String sysptom) {
		this.sysptom = sysptom;
	}

}
