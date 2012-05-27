package com.juncsoft.KLJY.user.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.Response;

public interface UserDao {
	public boolean checkAccountIsUsed(String account) throws Exception;
	public User register(User user) throws Exception;
	public User login(Response res,String account,String password) throws Exception;
	public JSONObject getLoginUserInfo(HttpServletRequest reqeust) throws Exception;
	public JSONArray getMyModules(User user) throws Exception;
	public boolean validOldPassword(Long id,String password)throws Exception;
	public void changePassword(Long id,String password)throws Exception;
	
	//liugang add print
	public User findUserById(Long id)throws Exception;
	public List<Map> findUserNameByDeptcode(String deptCode);
	
	/**
	 * ����HIS�û���¼
	 * @param res
	 * @param account
	 * @param password
	 * @return
	 */
	public User loginHis(Response res,String account,String password);
	/**
	 * 2011-12-12校验his密码
	 * @param id
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean validHisOldPassword(Long id,String password)throws Exception;
	
	/**
	 * 2011-12-12修改his密码
	 * @param id
	 * @param password
	 * @throws Exception
	 */
	public void changeHisPassword(Long id,String password)throws Exception;
	
	public JSONObject getLoginUserInfoByAccount(String account) throws Exception;
}
