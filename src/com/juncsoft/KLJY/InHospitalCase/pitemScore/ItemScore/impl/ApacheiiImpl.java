package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.ApacheiiDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Apacheii;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class ApacheiiImpl implements ApacheiiDao {

	/**
	 * 保存或修改
	 */
	public Apacheii apaceSaveOrUpdate(Apacheii ap) throws Exception {
		Session session = null;
		Transaction t = null;
		Long id = -1L;
//		try {
//			session = DatabaseUtil.getSession();
//			t = session.beginTransaction();
//			if (ap.getApId() != null && ap.getApId() > 0) {
//				session.update(ap);
//			} else {
//				id = (Long) session.save(ap);
//			}
//			if (id > 0) {
//				ap = (Apacheii) session.get(Apacheii.class, id);
//			}
//			t.commit();
//		} catch (Exception e) {
//			t.rollback();
//			e.printStackTrace();
//		} finally {
//			DatabaseUtil.closeResource(session);
//		}
		return ap;
	}

	/**
	 * 删除
	 */
	public boolean delApacheById(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		boolean struts = false;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (id > 0) {
				Apacheii ap = (Apacheii) session.get(Apacheii.class, id);
				if (ap != null) {
					session.delete(ap);
					struts = true;
				}
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			struts = false;
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return struts;
	}

	/**
	 * 查询
	 */
	public Apacheii findApacheById(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		Apacheii ap = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (id > 0) {
				ap = (Apacheii) session.get(Apacheii.class, id);
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ap;
	}

}
