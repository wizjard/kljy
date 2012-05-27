package com.juncsoft.KLJY.biomedical.member.entity;

public class BioNewsFile {
	private Long id;
	private BioNews news;
	private String name;
	private String memo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BioNews getNews() {
		return news;
	}
	public void setNews(BioNews news) {
		this.news = news;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
