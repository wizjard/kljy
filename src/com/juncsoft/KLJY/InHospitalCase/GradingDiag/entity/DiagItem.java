package com.juncsoft.KLJY.InHospitalCase.GradingDiag.entity;

public class DiagItem {
	private String code;
	private DiagItem parent;
	private String nodeText;
	private String printText;
	private String multiLine;
	private int leaf;
	private int checkAction;
	private int orderNo;
	private String standard;
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getCheckAction() {
		return checkAction;
	}
	public void setCheckAction(int checkAction) {
		this.checkAction = checkAction;
	}
	public int getLeaf() {
		return leaf;
	}
	public void setLeaf(int leaf) {
		this.leaf = leaf;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public DiagItem getParent() {
		return parent;
	}
	public void setParent(DiagItem parent) {
		this.parent = parent;
	}
	public String getNodeText() {
		return nodeText;
	}
	public void setNodeText(String nodeText) {
		this.nodeText = nodeText;
	}
	public String getPrintText() {
		return printText;
	}
	public void setPrintText(String printText) {
		this.printText = printText;
	}
	public String getMultiLine() {
		return multiLine;
	}
	public void setMultiLine(String multiLine) {
		this.multiLine = multiLine;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	
}
