package com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory;

public class TreatGrid {
	private Long id;
	private PresentIllnessHistoryNx nx;
	private String gridType;
	private String content1;
	private String content2;
	private String content3;
	private int orderNo;
	public String getGridType() {
		return gridType;
	}
	public void setGridType(String gridType) {
		this.gridType = gridType;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PresentIllnessHistoryNx getNx() {
		return nx;
	}
	public void setNx(PresentIllnessHistoryNx nx) {
		this.nx = nx;
	}
	public String getContent1() {
		return content1;
	}
	public void setContent1(String content1) {
		this.content1 = content1;
	}
	public String getContent2() {
		return content2;
	}
	public void setContent2(String content2) {
		this.content2 = content2;
	}
	public String getContent3() {
		return content3;
	}
	public void setContent3(String content3) {
		this.content3 = content3;
	}
}
