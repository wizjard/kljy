package com.juncsoft.KLJY.ResearchFollowup.dao;

import java.util.List;

import com.juncsoft.KLJY.ResearchFollowup.entity.ResearchFollowSetUp;

public interface FollowSetUpDao {

	public List<ResearchFollowSetUp> getFollopupPro(int researchTopicId) throws Exception;
	public void saveFollopupPro(List<ResearchFollowSetUp> list) throws Exception;
	public void deleteFollopupPro(int researchTopicId) throws Exception;
}
