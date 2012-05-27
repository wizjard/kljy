package com.juncsoft.KLJY.system.picture.entity;

public class Picture {
	private Long id;
	private String picTitle;//图片标题
	private String filePathList;//图片名称
	private String typeName;//图片类型
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPicTitle() {
		return picTitle;
	}
	public void setPicTitle(String picTitle) {
		this.picTitle = picTitle;
	}
	public String getFilePathList() {
		return filePathList;
	}
	public void setFilePathList(String filePathList) {
		this.filePathList = filePathList;
	}
	
	public Picture(){}
	
	public Picture(String picTitle, String filePathList, String typeName){
		this.picTitle = picTitle;
		this.filePathList = filePathList;
		this.typeName = typeName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
