package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.PhiDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Phi;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class PhiImpl implements PhiDao {

	public void delPhi(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (id > 0) {
				Phi phi = (Phi) session.get(Phi.class, id);
				if (phi != null) {
					session.delete(phi);
				}
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 查询急诊中心急重病人综合评价(内)
	 */
	public Phi getPhiData(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		Phi phi = null;
//		try {
//			session = DatabaseUtil.getSession();
//			t = session.beginTransaction();
//			if (id > 0) {
//				phi = (Phi) session.get(Phi.class, id);
//				System.out.println(phi.getOpeDate());
//			}
//			t.commit();
//		} catch (Exception e) {
//			t.rollback();
//			e.printStackTrace();
//		} finally {
//			DatabaseUtil.closeResource(session);
//		}
		return phi;
	}

	/**
	 * 保存或修改
	 */
	public Phi saveOrUpdataPhi(Phi phi) throws Exception {
		Session session = null;
		Transaction t = null;
		Long id = -1L;
//		try {
//			session = DatabaseUtil.getSession();
//			t = session.beginTransaction();
//			if (phi.getPhiId() != null && phi.getPhiId() > 0) {
//				session.update(phi);
//			} else {
//				id = (Long) session.save(phi);
//			}
//			if (id > 0) {
//				phi = (Phi) session.get(Phi.class, id);
//			}
//			t.commit();
//		} catch (Exception e) {
//			t.rollback();
//			e.printStackTrace();
//		} finally {
//			DatabaseUtil.closeResource(session);
//		}
		return phi;
	}

}
