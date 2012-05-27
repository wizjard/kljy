package com.juncsoft.KLJY.system.dict.entity;

@SuppressWarnings("serial")
public class DictItemEx implements java.io.Serializable {
	private Long id;
	private Long fldCodeId;
	private String value;
	private String text;
	private String comment;
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
	public Long getFldCodeId() {
		return fldCodeId;
	}
	public void setFldCodeId(Long fldCodeId) {
		this.fldCodeId = fldCodeId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
