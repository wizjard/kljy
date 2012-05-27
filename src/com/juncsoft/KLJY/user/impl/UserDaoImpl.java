package com.juncsoft.KLJY.user.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.outoremergency.dao.OutOrMergencyDoctorDao;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyDoctor;
import com.juncsoft.KLJY.outoremergency.impl.OutOrMergencyDoctorDaoImpl;
import com.juncsoft.KLJY.system.module.dao.SysModuleDao;
import com.juncsoft.KLJY.user.dao.UserDao;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;
import com.juncsoft.KLJY.util.MD5;
import com.juncsoft.KLJY.util.Response;

public class UserDaoImpl implements UserDao {

	private OutOrMergencyDoctorDao doctorDao = new OutOrMergencyDoctorDaoImpl();
	
	
	public boolean checkAccountIsUsed(String account) throws Exception {
		boolean bool = true;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			bool = !(session.createCriteria(User.class).add(
					Restrictions.eq("account", account)).list().iterator()
					.hasNext());
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return bool;
	}

	public User register(User user) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			user.setId((Long) session.save(user));
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public User login(Response res, String account, String password)
			throws Exception {
		User user = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Iterator it = session.createCriteria(User.class).add(
					Restrictions.eq("account", account)).list().iterator();
			if (it.hasNext()) {
				user = (User) it.next();
				if (user.getValid() == 0) {
					res.setSuccess(false);
					res.setMsg("账号未通过管理员验证。");
					user = null;
				} else {
					if ("".equals(password)) {
						res.setSuccess(true);
						res.setMsg("登录成功。");
						System.out.println("姓名：" + user.getName() + "\tID:"
								+ user.getId());
					} else {
						if (new MD5().getMD5ofStr(password).equalsIgnoreCase(
								user.getPassword())) {
							res.setSuccess(true);
							res.setMsg("登录成功。");
							System.out.println("姓名：" + user.getName() + "\tID:"
									+ user.getId());
						} else {
							res.setSuccess(false);
							res.setMsg("账号或密码不正确。");
							user = null;
						}
					}
				}
			} else {
				res.setSuccess(false);
				res.setMsg("账号或密码不正确。");
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return user;
	}

	public JSONObject getLoginUserInfo(HttpServletRequest reqeust)
			throws Exception {
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			User user = (User) reqeust.getSession().getAttribute("user");
			if (user == null)
				return json;
			if (user != null && user.getDrdeptname5() != null) {
				json = JSONObject.fromObject(user);
				if(user.getGbjupsu() != null){
					json.put("Flag","普通用户");
				}else{
					json.put("Flag","管理员");
				}
				if (user != null) {
					json.put("account", user.getDrdeptname5());
				}
				if (user.getRole() == null || user.getRole().length() == 0) {
					json.put("角色", "未定义");
				} else {
					json.put("角色", user.getGbjaejik());
//					json.put("角色", DictionaryUtil.getIndependentDictionaryText(
//							"role", user.getRole()));
				}
				if (user.getBelong() == null || user.getBelong().length() == 0) {
					json.put("单位", "未定义");
					json.put("机构", "未定义");
				} else {
					StringBuilder sb = new StringBuilder();
					if (user.getUnkown1() != null) {
						sb.append(user.getDeptcode());
					}
					if (user.getDeptcodename() != null) {
						sb.append("、" + user.getDrdept1());
					}
					if (user.getDrdeptname1() != null) {
						sb.append("、" + user.getDrdept2());
					}
					if (user.getDrdeptname2() != null) {
						sb.append("、" + user.getDrdept3());
					}
					if (user.getDrdeptname3() != null) {
						sb.append("、" + user.getDrdept4());
					}
					if (user.getDrdeptname4() != null) {
						sb.append("、" + user.getDrdept5());
					}
					json.put("单位", sb.toString());
					StringBuilder sb1 = new StringBuilder();
					if (user.getUnkown1() != null) {
						sb1.append(user.getUnkown1());
					}
					if (user.getDeptcodename() != null) {
						sb1.append("," + user.getDeptcodename());
					}
					if (user.getDrdeptname1() != null) {
						sb1.append("," + user.getDrdeptname1());
					}
					if (user.getDrdeptname2() != null) {
						sb1.append("," + user.getDrdeptname2());
					}
					if (user.getDrdeptname3() != null) {
						sb1.append("," + user.getDrdeptname3());
					}
					if (user.getDrdeptname4() != null) {
						sb1.append("," + user.getDrdeptname4());
					}
					json.put("DeptCode", sb1.toString());
					if (conn == null)
						conn = DatabaseUtil.getConnection();
					sm = conn
							.prepareStatement("select PCODE,NAME from SYS_ZD_UserBelong where CODE=?");
					sm.setString(1, user.getBelong());
					rs = sm.executeQuery();
					if (rs.next()) {
						String code = rs.getString(1);
						sm = conn
								.prepareStatement("select NAME from SYS_ZD_UserBelong where CODE=?");
						sm.setString(1, code);
						rs = sm.executeQuery();
						if (rs.next()) {
							json.put("机构", rs.getString(1));
						}
					}
				}
			}else{
				json = JSONObject.fromObject(user);
				if(user.getGbjupsu() != null){
					json.put("Flag","普通用户");
				}else{
					json.put("Flag","管理员");
				}
				if (user.getRole() == null || user.getRole().length() == 0) {
					json.put("角色", "未定义");
				} else {
					json.put("角色", DictionaryUtil.getIndependentDictionaryText(
							"role", user.getRole()));
				}
				if (user.getBelong() == null || user.getBelong().length() == 0) {
					json.put("单位", "未定义");
					json.put("机构", "未定义");
				} else {
					if (conn == null)
						conn = DatabaseUtil.getConnection();
					sm = conn
							.prepareStatement("select PCODE,NAME from SYS_ZD_UserBelong where CODE=?");
					sm.setString(1, user.getBelong());
					rs = sm.executeQuery();
					if (rs.next()) {
						json.put("单位", rs.getString(2));
						String code = rs.getString(1);
						sm = conn
								.prepareStatement("select NAME from SYS_ZD_UserBelong where CODE=?");
						sm.setString(1, code);
						rs = sm.executeQuery();
						if (rs.next()) {
							json.put("机构", rs.getString(1));
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return json;
	}

	public JSONArray getMyModules(User user) throws Exception {
		JSONArray returnArr = new JSONArray();
		JSONArray array = new JSONArray();
		String role = user.getRole();
		if (role == null || role.length() == 0) {
			return array;
		}
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select Modules from SYS_ZD_UserRole where CODE=?";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, role);
			rs = sm.executeQuery();
			if (rs.next()) {
				String modules = rs.getString(1);
				if (modules != null && modules.length() > 0) {
					SysModuleDao dao = (SysModuleDao) DaoFactory
							.InstanceImplement(SysModuleDao.class);
					array = dao.getJSONModules();
					for (Object module : array) {
						JSONArray children = JSONObject.fromObject(module)
								.getJSONArray("children");
						boolean removeFlag = true;
						JSONObject newJSONObject = JSONObject
								.fromObject(module);
						JSONArray newChildren = new JSONArray();
						for (Object module_item : children) {
							String code = JSONObject.fromObject(module_item)
									.getString("code");
							if (isCodeInModules(code, modules)) {
								removeFlag = false;
								newChildren.add(module_item);
							}
						}
						if (!removeFlag) {
							newJSONObject.put("children", newChildren);
							returnArr.add(newJSONObject);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return returnArr;
	}

	private boolean isCodeInModules(String code, String modules) {
		boolean rst = false;
		for (String module : modules.split(",")) {
			if (code.equals(module)) {
				rst = true;
				break;
			}
		}
		return rst;
	}

	public boolean validOldPassword(Long id, String password) throws Exception {
		boolean rst = false;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			rst = session.createCriteria(User.class).add(
					Restrictions.eq("id", id)).add(
							//liugang 2011-12-12 修改用户密码
							Restrictions.eq("password", new MD5().getMD5ofStr(password))
//							Restrictions.eq("gbjupsu", password)
					).list().iterator().hasNext();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}

	
	
	public void changePassword(Long id, String password) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			User user = (User) session.get(User.class, id);
			user.setPassword(new MD5().getMD5ofStr(password));
			session.update(user);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	// liugang add print
	public User findUserById(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		User user = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			user = (User) session.get(User.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return user;
	}

	// 预约挂号根据科室查找医生列表
	public List<Map> findUserNameByDeptcode(String deptCode) {
		Session session = null;
		Transaction tran = null;
		List<Map> listMap = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select distinct(name),drdeptname5 from t_User where unkown1='"
					+ deptCode
					+ "' or deptcodename='"
					+ deptCode
					+ "' or drdeptname1='"
					+ deptCode
					+ "' or drdeptname2='"
					+ deptCode
					+ "' or drdeptname3='"
					+ deptCode
					+ "' or drdeptname4='"
					+ deptCode
					+ "' and gbspc = 1 and hisDoctorId='在职'";
			List list = session.createSQLQuery(sql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					Object[] obj = (Object[]) list.get(i);
					Map mp = new HashMap();
					mp.put("hisDoctorId", obj[1]);
					mp.put("name", obj[0]);
					listMap.add(mp);
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("预约挂号根据科室查找医生列表出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return listMap;
	}

	/**
	 * 兼容HIS用户登录
	 */
	public User loginHis(Response res, String account, String password) {
		User user = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from User where drdeptname5='" + account
					+ "' and gbjupsu='" + password.trim().toLowerCase() + "'";
			List list = session.createQuery(hql).list();
			if (list != null && list.size() > 0) {
				user = (User) list.get(0);			
				if (user.getValid() == 0) {
					res.setSuccess(false);
					res.setMsg("账号未通过管理员验证。");
					user = null;
				} else {
					if (password.equals(user.getGbjupsu())) {
						res.setSuccess(true);
						res.setMsg("登录成功。");
						System.out.println("姓名：" + user.getName() + "\tID:"
								+ user.getId());
					} else {
						res.setSuccess(false);
						res.setMsg("账号或密码不正确。");
						user = null;
					}
				}
			} else {
				res.setSuccess(false);
				res.setMsg("账号或密码不正确。");
			}

			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw new RuntimeException("兼容HIS用户登录出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return user;
	}

	public boolean validHisOldPassword(Long id, String password)
			throws Exception {
		boolean rst = false;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			rst = session.createCriteria(User.class).add(
					Restrictions.eq("id", id)).add(
							//liugang 2011-12-12 修改用户密码
//							Restrictions.eq("password", new MD5().getMD5ofStr(password))
							Restrictions.eq("gbjupsu", password.trim().toLowerCase())
					).list().iterator().hasNext();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}

	public void changeHisPassword(Long id, String password) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			User user = (User) session.get(User.class, id);
			user.setGbjupsu(password.trim().toLowerCase());
			session.update(user);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public JSONObject getLoginUserInfoByAccount(String account)
			throws Exception {
		JSONObject json = new JSONObject();
		User user = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from User where drdeptname5='" + account+"'";
			List list = session.createQuery(hql).list();
			if (list != null && list.size() > 0) {
				user = (User) list.get(0);	
				if(user != null){
					json.put("id", user.getId());
					json.put("name", user.getName());
					json.put("account", user.getDrdeptname5());
				}
			} 
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw new RuntimeException("兼容HIS用户登录出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}
}
