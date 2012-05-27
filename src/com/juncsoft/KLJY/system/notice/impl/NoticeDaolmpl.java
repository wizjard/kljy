package com.juncsoft.KLJY.system.notice.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.system.notice.dao.NoticeDAO;
import com.juncsoft.KLJY.system.notice.entity.Notice;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class NoticeDaolmpl implements NoticeDAO {

	public Notice saveOrUpdate(Notice n) throws Exception {
		Long id = n.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date();
			if (id == null || id <= 0) {
				n.setNoticeTim(date);
				id = (Long) session.save(n);
				n.setId(id);
			} else {
				n.setNoticeTim(date);
				session.update(n);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return n;
	}

	public Notice findById(Long id) throws Exception {
		Notice n = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			n = (Notice) session.get(Notice.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return n;
	}

	@SuppressWarnings("unchecked")
	public JSONObject queryAll(Integer start, Integer limit, String searchPara)
			throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List root = null;
			long total = 0l;
			if (searchPara != null) {
				root = session.createCriteria(Notice.class).add(
						Restrictions.like("typeName", "%" + searchPara + "%"))
						.addOrder(Order.desc("noticeTim"))
						.setFirstResult(start).setMaxResults(limit).list();
				total = (Long) session.createQuery(
						"select count(*) from Notice where typeName ='%"
								+ searchPara + "%'").list().iterator().next();
			} else {
				root = session.createCriteria(Notice.class).addOrder(
						Order.desc("noticeTim")).setFirstResult(start)
						.setMaxResults(limit).list();
				total = (Long) session.createQuery(
						"select count(*) from Notice ").list().iterator()
						.next();
			}
			json.put("root", root);
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
	public JSONObject searchByName(String keyword, String typeName,
			Integer start, Integer limit) throws Exception {
		JSONObject json = new JSONObject();
		keyword = "%" + keyword + "%";
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List root = session.createCriteria(Notice.class).add(
					Restrictions.like("typeName", "%" + typeName + "%")).add(
					Restrictions.like("noticeNam", keyword)).addOrder(
					Order.desc("noticeTim")).setFirstResult(start)
					.setMaxResults(limit).list();
			long total = (Long) session.createQuery(
					"select count(*) from Notice where typeName like '%"
							+ typeName + "%' and noticeNam like ?").setString(
					0, keyword).list().iterator().next();
			// JSONArray root = new JSONArray();
			// for (Notice n: list) {
			// root.add(initPatientValue(n));
			// }
			json.put("root", root);
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

	public void delete(Notice n) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();

			session.delete(n);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public Notice save(Notice n) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();

			Date date = new Date();
			n.setNoticeTim(date);
			session.save(n);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return n;
	}

	public String selectNoticeByNam(Long id) throws Exception {
		String content = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "select noticeContent from Notice where id = :id";
			Query query = session.createQuery(hql).setParameter("id", id);
			content = (String) query.uniqueResult();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return content;
	}

	public List<Notice> getSixNoticeList(Integer limit, String typeName)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		List<Notice> list = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from Notice where typeName='" + typeName + "'";
			list = session.createQuery(hql).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public List<Notice> findNewNoticeByTime(String typeName) throws Exception {
		Session session = null;
		Transaction tran = null;
		List<Notice> list = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date currentTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long time = currentTime.getTime();
			long firstTime = time-5000;
			sdf.format(currentTime.getTime());
			String hql = "from Notice where typeName='" + typeName+ "' and noticeTim >= '"+sdf.format(firstTime)+"' and noticeTim <= '"+sdf.format(time)+"'";
			list = session.createQuery(hql).list();
			tran.commit();
		} catch (Exception e) {
			if(null != tran)
			{
				tran.rollback();
			}
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
}
