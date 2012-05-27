package com.juncsoft.KLJY.system.Research.entity;

public class SYS_ResearchFollowup {
	private Long id;
	private int followTimes;
	private int followCycle;
	private String followContent;
	private long reserchId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getFollowTimes() {
		return followTimes;
	}

	public void setFollowTimes(int followTimes) {
		this.followTimes = followTimes;
	}

	public int getFollowCycle() {
		return followCycle;
	}

	public void setFollowCycle(int followCycle) {
		this.followCycle = followCycle;
	}

	public String getFollowContent() {
		return followContent;
	}

	public void setFollowContent(String followContent) {
		this.followContent = followContent;
	}

	public long getReserchId() {
		return reserchId;
	}

	public void setReserchId(long reserchId) {
		this.reserchId = reserchId;
	}
}
