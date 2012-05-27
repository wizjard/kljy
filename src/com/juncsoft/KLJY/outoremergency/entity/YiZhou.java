package com.juncsoft.KLJY.outoremergency.entity;

import java.util.Date;

public class YiZhou {
	private Long id;
	private String ptno;
	private Date indate;
	private Date bdate;
	private String insertid;
	private String drname;
	private String ordercode;
	private String ordername;
	private String plusname;
	private Integer jsqty;
	private String unit;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPtno() {
		return ptno;
	}
	public void setPtno(String ptno) {
		this.ptno = ptno;
	}
	public Date getIndate() {
		return indate;
	}
	public void setIndate(Date indate) {
		this.indate = indate;
	}
	public Date getBdate() {
		return bdate;
	}
	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}
	public String getInsertid() {
		return insertid;
	}
	public void setInsertid(String insertid) {
		this.insertid = insertid;
	}
	public String getDrname() {
		return drname;
	}
	public void setDrname(String drname) {
		this.drname = drname;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public String getOrdername() {
		return ordername;
	}
	public void setOrdername(String ordername) {
		this.ordername = ordername;
	}
	public String getPlusname() {
		return plusname;
	}
	public void setPlusname(String plusname) {
		this.plusname = plusname;
	}
	
	public Integer getJsqty() {
		return jsqty;
	}
	public void setJsqty(Integer jsqty) {
		this.jsqty = jsqty;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
