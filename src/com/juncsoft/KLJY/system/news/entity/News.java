package com.juncsoft.KLJY.system.news.entity;

import java.util.Date;
import java.util.Set;

public class News {
	private Long id;
	private Date issueDate;
	private String type;
	private String title;
	private String issuepart;
	private String issuer;
	private String content;
	private Set<String> affix;
	private Date createTime;
	private Date modifyTime;
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Set<String> getAffix() {
		return affix;
	}
	public void setAffix(Set<String> affix) {
		this.affix = affix;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIssuepart() {
		return issuepart;
	}
	public void setIssuepart(String issuepart) {
		this.issuepart = issuepart;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
