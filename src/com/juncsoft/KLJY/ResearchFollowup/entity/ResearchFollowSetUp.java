package com.juncsoft.KLJY.ResearchFollowup.entity;

public class ResearchFollowSetUp {

	private int id;
	private String followTimes;
	private String followCycle;
	private String followContent;
	private int researchTopicId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFollowTimes() {
		return followTimes;
	}
	public void setFollowTimes(String followTimes) {
		this.followTimes = followTimes;
	}
	public String getFollowCycle() {
		return followCycle;
	}
	public void setFollowCycle(String followCycle) {
		this.followCycle = followCycle;
	}
	public String getFollowContent() {
		return followContent;
	}
	public void setFollowContent(String followContent) {
		this.followContent = followContent;
	}
	public int getResearchTopicId() {
		return researchTopicId;
	}
	public void setResearchTopicId(int researchTopicId) {
		this.researchTopicId = researchTopicId;
	}
	
}
