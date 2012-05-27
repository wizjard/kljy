package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

public class Tree {
	private String treeId;// 自身ID
	private String pId;// 父ID
	private String nodeName;
	private String href;
	private String pageName;
	private String simpleName;

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * 节点父ID
	 * 
	 * @return
	 */
	public String getPId() {
		return pId;
	}

	/**
	 * 设置节点父ID
	 * 
	 * @param id
	 */
	public void setPId(String id) {
		pId = id;
	}

	/**
	 * 节点自身ID
	 * 
	 * @return
	 */
	public String getTreeId() {
		return treeId;
	}

	/**
	 * 设置节点自身ID
	 * 
	 * @param treeId
	 */
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
}
