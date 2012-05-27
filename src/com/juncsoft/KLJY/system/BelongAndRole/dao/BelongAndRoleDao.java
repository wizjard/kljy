package com.juncsoft.KLJY.system.BelongAndRole.dao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface BelongAndRoleDao {
	public JSONArray belong_findAll() throws Exception;
	public JSONObject belong_findById(Long id) throws Exception;
	public void belong_delete(Long id) throws Exception;
	public void belong_saveOrUpdate(JSONObject json) throws Exception;
	public JSONArray role_findAll() throws Exception;
	public void role_delete(Long id) throws Exception;
	public void role_saveOrUpdate(JSONArray array) throws Exception;
	public void role_updateModules(Long id,String modules) throws Exception;
}
