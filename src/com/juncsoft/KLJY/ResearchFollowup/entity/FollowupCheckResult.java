package com.juncsoft.KLJY.ResearchFollowup.entity;

public class FollowupCheckResult {

	private int id;
	private int followupId;
	private String result;
	
	public int getFollowupId() {
		return followupId;
	}
	public void setFollowupId(int followupId) {
		this.followupId = followupId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
