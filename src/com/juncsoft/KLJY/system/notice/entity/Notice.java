package com.juncsoft.KLJY.system.notice.entity;

import java.util.Date;

/**
 * 公告发布实体
 * 
 * @author liugang
 * 
 */
public class Notice {
	private Long id;
	private String noticeNam;// 公告标题
	private String noticeContent;// 公告内容
	private Date noticeTim;// 发布时间
	private String noticeAuthor;// 发布人
	private String noticeCompany;// 公告发布单位
	private String filePathList;// 附件字符串数组
	private String typeName;// 信息类型(1:公告通知;2:健康教育)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNoticeNam() {
		return noticeNam;
	}

	public void setNoticeNam(String noticeNam) {
		this.noticeNam = noticeNam;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Date getNoticeTim() {
		return noticeTim;
	}

	public void setNoticeTim(Date noticeTim) {
		this.noticeTim = noticeTim;
	}

	public Notice(String noticeNam, String noticeContent, Date noticeTim,
			String noticeAuthor, String noticeCompany, String filePathList,
			String typeName) {
		super();
		this.noticeNam = noticeNam;
		this.noticeContent = noticeContent;
		this.noticeTim = noticeTim;
		this.noticeAuthor = noticeAuthor;
		this.noticeCompany = noticeCompany;
		this.filePathList = filePathList;
		this.typeName = typeName;
	}

	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNoticeAuthor() {
		return noticeAuthor;
	}

	public void setNoticeAuthor(String noticeAuthor) {
		this.noticeAuthor = noticeAuthor;
	}

	public String getNoticeCompany() {
		return noticeCompany;
	}

	public void setNoticeCompany(String noticeCompany) {
		this.noticeCompany = noticeCompany;
	}

	public String getFilePathList() {
		return filePathList;
	}

	public void setFilePathList(String filePathList) {
		this.filePathList = filePathList;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
