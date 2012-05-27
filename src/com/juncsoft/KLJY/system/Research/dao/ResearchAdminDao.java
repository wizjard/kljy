package com.juncsoft.KLJY.system.Research.dao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.system.Research.entity.ResearchMember;
import com.juncsoft.KLJY.system.Research.entity.ResearchTopic;

public interface ResearchAdminDao {
	public ResearchTopic saveOrUpdate(ResearchTopic t)throws Exception;
	public ResearchTopic findById(Long id)throws Exception;
	public void delete(ResearchTopic t)throws Exception;
	public JSONObject findAll(Integer start,Integer limit)throws Exception;
	
	public JSONArray crf_findAll()throws Exception;
	public JSONArray crf_findById(Long id)throws Exception;
	public void crf_save(Long id,JSONArray crf)throws Exception;
	
	public JSONObject mem_findAll(Integer start,Integer limit)throws Exception;
	public ResearchMember mem_saveOrUpdate(ResearchMember mem)throws Exception;
	public ResearchMember mem_delete(ResearchMember mem)throws Exception;
	public void mem_res_rel_save(Long id,JSONArray mems)throws Exception;
	public JSONObject mem_res_rel_find(Long id)throws Exception;
	
	public JSONArray group_rel_find(Long id)throws Exception;
	public void group_rel_save(Long id,JSONArray array)throws Exception;
}
