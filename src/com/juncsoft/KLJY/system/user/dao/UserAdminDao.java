package com.juncsoft.KLJY.system.user.dao;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.user.entity.User;

public interface UserAdminDao {
	public JSONObject queryAll(Integer start,Integer limit) throws Exception;
	public JSONObject search(String keyword,Integer start,Integer limit) throws Exception;
	public User saveOrUpdate(User user) throws Exception;
	public User findById(Long id) throws Exception;
	public void delete(User user) throws Exception;
}
