package com.juncsoft.KLJY.ResearchFollowup.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.ResearchFollowup.entity.Followup;
import com.juncsoft.KLJY.ResearchFollowup.entity.FollowupCheckResult;
import com.juncsoft.KLJY.ResearchFollowup.entity.PatientResearch;
import com.juncsoft.KLJY.ResearchFollowup.entity.ResearchFollowSetUp;
import com.juncsoft.KLJY.system.Research.entity.ResearchTopic;

public interface PatientResearchDao {
	public PatientResearch reserch_saveOrUpdate(PatientResearch pr)throws Exception;
	public void research_delete(PatientResearch pr)throws Exception;
	public List<PatientResearch> research_findByPatientId(Long id)throws Exception;
	
	public void followup_saveOrUpdate(List<Followup> list)throws Exception;
	public Followup followup_saveOrUpdate(Followup fu)throws Exception;
	public Followup followup_findById(Long id)throws Exception;
	public void followup_delete(Followup fu)throws Exception;
	public List<Followup> followup_findByResearchId(Long id)throws Exception;
	
	public JSONObject notice_findAll(Integer start,Integer limit,int action)throws Exception;
	
	public JSONArray followRegin_findByPatientId(Long id)throws Exception;
	public ResearchTopic getResearchTopic(long researchTopicId) throws Exception;
	public List<ResearchFollowSetUp> getResearchFollowSetUp(long researchTopicId) throws Exception;
	
	public void saveFollowupCheckResult(FollowupCheckResult fcr) throws Exception;
	public FollowupCheckResult getFollowupCheckResult(int followupId) throws Exception;
	
	public JSONObject getFirstPatientFollowup(Long patientResearchId)throws Exception;
}
