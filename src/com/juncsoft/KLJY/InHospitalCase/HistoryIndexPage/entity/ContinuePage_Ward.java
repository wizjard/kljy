/**
 * auther:lisht createOrEditDate：Oct 20, 2010   12:47:23 PM
 * projectName：TCMP1  fileLaction： com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.ContinuePage_Ward.java
 */
package com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity;

import java.util.Date;

/**
 * auther:lisht createOrEditDate：Oct 20, 2010 12:47:23 PM 病案续页首页--重病监护室部分
 * class、method、Attribute or logic explain：
 * 
 */
public class ContinuePage_Ward {
	private Long id;
	private Long caseId;
	private String icuName;
	private Date icuTurnInto;
	private Date icuDropOut;

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

	public String getIcuName() {
		return icuName;
	}

	public void setIcuName(String icuName) {
		this.icuName = icuName;
	}

	public Date getIcuTurnInto() {
		return icuTurnInto;
	}

	public void setIcuTurnInto(Date icuTurnInto) {
		this.icuTurnInto = icuTurnInto;
	}

	public Date getIcuDropOut() {
		return icuDropOut;
	}

	public void setIcuDropOut(Date icuDropOut) {
		this.icuDropOut = icuDropOut;
	}

}
