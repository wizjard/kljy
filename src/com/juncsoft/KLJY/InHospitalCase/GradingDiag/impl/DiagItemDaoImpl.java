package com.juncsoft.KLJY.InHospitalCase.GradingDiag.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.GradingDiag.dao.DiagItemDao;
import com.juncsoft.KLJY.InHospitalCase.GradingDiag.entity.DiagItem;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class DiagItemDaoImpl implements DiagItemDao {

	public void addDiagItem(DiagItem item) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.save(item);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void deleteDiagItem(DiagItem item) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<DiagItem> dels = new ArrayList<DiagItem>();
			dels.add(item);
			deleteGetChildren(session, item, dels);
			for (DiagItem i : dels) {
				session.delete(i);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	private void deleteGetChildren(Session session, DiagItem parent,
			List<DiagItem> dels) throws Exception {
		List<DiagItem> children = session.createCriteria(DiagItem.class).add(
				(Restrictions.eq("parent", parent))).list();
		for (DiagItem item : children) {
			dels.add(item);
			deleteGetChildren(session, item, dels);
		}
	}

	@SuppressWarnings("unchecked")
	public List<DiagItem> getChildren(DiagItem item) throws Exception {
		List<DiagItem> list = new ArrayList<DiagItem>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Criterion c;
			if (item == null) {
				c = Restrictions.isNull("parent");
			} else {
				c = Restrictions.eq("parent", item);
			}
			list = session.createCriteria(DiagItem.class).add(c).addOrder(
					Order.asc("orderNo")).addOrder(Order.asc("code")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public DiagItem getDiagItem(String code) throws Exception {
		DiagItem item = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			item = (DiagItem) session.get(DiagItem.class, code);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return item;
	}

	public void updateDiagItem(DiagItem item) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.update(item);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public boolean isCodeExist(String code) throws Exception {
		boolean rst = false;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (session.get(DiagItem.class, code) != null)
				rst = true;
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}

}
