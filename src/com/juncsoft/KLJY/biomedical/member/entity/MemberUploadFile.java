package com.juncsoft.KLJY.biomedical.member.entity;

import java.util.Date;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;

public class MemberUploadFile {
	private Long id;//标识
	private Date uploadTime;//上传日期
	private MemberInfo mem;//关联会员
	private String fileName;//文件名称
	private String fileSaveName;//实际文件存储名称
	private String fileSize;//文件大小
	private String memo;//文件说明
	public String getFileSaveName() {
		return fileSaveName;
	}
	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public MemberInfo getMem() {
		return mem;
	}
	public void setMem(MemberInfo mem) {
		this.mem = mem;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
