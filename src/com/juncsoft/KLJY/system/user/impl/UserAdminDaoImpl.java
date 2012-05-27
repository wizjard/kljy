package com.juncsoft.KLJY.system.user.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.system.user.dao.UserAdminDao;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.MD5;

public class UserAdminDaoImpl implements UserAdminDao {

	@SuppressWarnings("unchecked")
	public JSONObject queryAll(Integer start, Integer limit) throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createQuery(
					"from User order by modifyDate desc").setFirstResult(start)
					.setMaxResults(limit).list();
			json.put("root", list);
			long total = (Long) session
					.createQuery("select count(*) from User").list().iterator()
					.next();
			json.put("total", total);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	@SuppressWarnings("unchecked")
	public JSONObject search(String keyword, Integer start, Integer limit)
			throws Exception {
		keyword = "%" + keyword + "%";
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session
					.createQuery(
							"from User where account like ? or name like ? order by modifyDate desc")
					.setString(0, keyword).setString(1, keyword)
					.setFirstResult(start).setMaxResults(limit).list();
			json.put("root", list);
			long total = (Long) session
					.createQuery(
							"select count(*) from User where account like ? or name like ?")
					.setString(0, keyword).setString(1, keyword).list()
					.iterator().next();
			json.put("total", total);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public User saveOrUpdate(User user) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date();
			if (user.getId() == null || user.getId() <= 0) {
				user.setPassword(new MD5().getMD5ofStr(user.getPassword()));
				user.setCreateDate(date);
				user.setModifyDate(date);
				user.setId((Long) session.save(user));
			} else {
				User o = (User) session.get(User.class, user.getId());
				user.setPassword(o.getPassword());
				user.setModifyDate(date);
				session.merge(user);
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

	public User findById(Long id) throws Exception {
		User user = null;
		Session session = null;
		Transaction tran = null;
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

	public void delete(User user) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(user);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

}
