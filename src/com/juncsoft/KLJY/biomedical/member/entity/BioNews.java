package com.juncsoft.KLJY.biomedical.member.entity;

import java.util.Date;
import java.util.List;

public class BioNews {
	private Long id;
	private String title;
	private Date time;
	private String partment;
	private String content;
	private List<BioNewsFile> files;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getPartment() {
		return partment;
	}
	public void setPartment(String partment) {
		this.partment = partment;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<BioNewsFile> getFiles() {
		return files;
	}
	public void setFiles(List<BioNewsFile> files) {
		this.files = files;
	}
}
