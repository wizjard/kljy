package com.juncsoft.KLJY.system.module.dao;

import java.util.List;

import net.sf.json.JSONArray;

import com.juncsoft.KLJY.system.module.entity.SysModule;

public interface SysModuleDao {
	public List<SysModule> getBigModules() throws Exception;
	public List<SysModule> getChildModules(String parentModuleCode) throws Exception;
	public JSONArray getJSONModules() throws Exception;
}
