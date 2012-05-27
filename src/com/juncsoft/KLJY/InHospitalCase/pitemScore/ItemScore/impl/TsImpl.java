package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.TsDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Ts;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class TsImpl implements TsDao {

	public void deleTsById(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (id > 0) {
				Ts ts = (Ts) session.get(Ts.class, id);
				if (ts != null) {
					session.delete(ts);
				}
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public Ts findTsById(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		Ts ts = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (id > 0) {
				ts = (Ts) session.get(Ts.class, id);
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ts;
	}

	public Ts tsSaveOrUpdate(Ts ts) throws Exception {
		Session session = null;
		Transaction t = null;
		Long id = -1L;
//		try {
//			session = DatabaseUtil.getSession();
//			t = session.beginTransaction();
//			if (ts.getTsId() != null && ts.getTsId() > 0) {
//				session.update(ts);
//			} else {
//				id = (Long) session.save(ts);
//			}
//			if (id > 0) {
//				ts = (Ts) session.get(Ts.class, id);
//			}
//			t.commit();
//		} catch (Exception e) {
//			t.rollback();
//			e.printStackTrace();
//		} finally {
//			DatabaseUtil.closeResource(session);
//		}
		return ts;
	}

}
