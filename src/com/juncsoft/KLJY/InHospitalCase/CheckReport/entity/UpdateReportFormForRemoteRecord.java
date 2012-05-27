package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

import java.util.Date;

/**
 * 修改化验单字表历史记录实体
 * @author liugang
 *
 */
public class UpdateReportFormForRemoteRecord {
	private Long id;
	private Integer itemId;
	private String updateAuthor;
	private String oldValue;
	private String newValue;
	private Date updateDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getUpdateAuthor() {
		return updateAuthor;
	}
	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
