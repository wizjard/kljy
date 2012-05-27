package com.juncsoft.KLJY.system.picture.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.system.picture.dao.PictureDAO;
import com.juncsoft.KLJY.system.picture.entity.Picture;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class PictureDaolmpl implements PictureDAO {

	public void delete(Picture p) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(p);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}

	}

	public Picture findById(Long id) throws Exception {
		Picture p = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			p = (Picture) session.get(Picture.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return p;
	}

	public JSONObject queryAll(String typeName,Integer start, Integer limit) throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List root = session.createCriteria(Picture.class).add(Restrictions.like("typeName", "%"+typeName+"%"))
					.addOrder(Order.desc("id")).setFirstResult(start)
					.setMaxResults(limit).list();
			json.put("root", root);
			long total = (Long) session.createQuery(
					"select count(*) from Picture where typeName like '%"+typeName+"%'").list().iterator().next();
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

	public Picture save(Picture p) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.save(p);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return p;
	}

	public Picture saveOrUpdate(Picture p) throws Exception {
		Long id = p.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(p);
				p.setId(id);
			} else {
				session.update(p);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return p;
	}

	public JSONObject searchByName(String keyword,String typeName, Integer start, Integer limit)
			throws Exception {
		JSONObject json = new JSONObject();
		keyword = "%" + keyword + "%";
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List root = session.createCriteria(Picture.class).add(Restrictions.like("typeName", "%"+typeName+"%")).add(
					Restrictions.like("picTitle", keyword)
					).addOrder(Order.desc("id")).setFirstResult(start)
					.setMaxResults(limit).list();
			long total = (Long) session
					.createQuery(
							"select count(*) from Picture where typeName like '%"+typeName+"%' and picTitle like ?")
					.setString(0, keyword).list()
					.iterator().next();
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

	public String selectPictureByNam(Long id) throws Exception {
		String content = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "select picTitle from Picture where id = :id";
			Query query = session.createQuery(hql).setParameter("id",id);
			content = (String)query.uniqueResult();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return content;
	}

	public String selectAllPictureName(String typeName) throws Exception {
		StringBuilder sb = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "select filePathList from Picture where typeName= '"+typeName+"'";
			Query query = session.createQuery(hql);
			List list = query.list();
			if(list != null && list.size() > 0)
			{
				sb = new StringBuilder();
				for(int i=0,size=list.size();i<size;i++)
				{
					String filePathList = list.get(i).toString();
					sb.append(filePathList);
				}
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		if(sb != null)
		{
			return sb.toString();
		}
		else
		{
			return "";
		}
	}

}
