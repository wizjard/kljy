package com.juncsoft.KLJY.plan.entity;

/**
 * 检查项目实体
 * @author Administrator
 *
 */
public class CheckItem {	
	private Long id;
	private String SLIPNO;
	//项目代码
	private String ORDERCODE;
	//项目名称
	private String ORDERNAMEK;
	private String HCODE;
	private String comment;
	private int circle;//周期
	
	public int getCircle() {
		return circle;
	}
	public void setCircle(int circle) {
		this.circle = circle;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSLIPNO() {
		return SLIPNO;
	}
	public void setSLIPNO(String sLIPNO) {
		SLIPNO = sLIPNO;
	}
	public String getORDERCODE() {
		return ORDERCODE;
	}
	public void setORDERCODE(String oRDERCODE) {
		ORDERCODE = oRDERCODE;
	}
	public String getORDERNAMEK() {
		return ORDERNAMEK;
	}
	public void setORDERNAMEK(String oRDERNAMEK) {
		ORDERNAMEK = oRDERNAMEK;
	}
	public String getHCODE() {
		return HCODE;
	}
	public void setHCODE(String hCODE) {
		HCODE = hCODE;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
