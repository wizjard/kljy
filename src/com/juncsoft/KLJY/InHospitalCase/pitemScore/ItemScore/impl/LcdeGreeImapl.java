package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.LcdeGreeDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Lcdegree;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class LcdeGreeImapl implements LcdeGreeDao {

	public boolean deleLcById(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		boolean struts = false;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (id > 0) {
				Lcdegree lc = (Lcdegree) session.get(Lcdegree.class, id);
				if (lc != null) {
					session.delete(lc);
					struts = true;
				}
			}
			t.commit();
		} catch (Exception e) {
			struts = false;
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return struts;
	}

	/**
	 * 查询
	 */
	public Lcdegree findLcdeById(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		Lcdegree lc = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (id > 0) {
				lc = (Lcdegree) session.get(Lcdegree.class, id);
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return lc;
	}

	/**
	 * 保存或修改
	 */
	public Lcdegree lcSaveOrUpdate(Lcdegree lc) throws Exception {
		Session session = null;
		Transaction t = null;
		Long id = -1L;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (lc.getLcDegreeId() != null && lc.getLcDegreeId() > 0) {
				session.update(lc);
			} else {
				id = (Long) session.save(lc);
			}
			if (id > 0) {
				lc = (Lcdegree) session.get(Lcdegree.class, id);
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return lc;
	}

}
