package com.juncsoft.KLJY.InHospitalCase.H1N1.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.H1N1.dao.EpidemicDisHistoryDao;
import com.juncsoft.KLJY.InHospitalCase.H1N1.entity.H1N1EpidemicDisHistory;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class EpidemicDisHistoryImpl implements EpidemicDisHistoryDao{

	public H1N1EpidemicDisHistory findEpidemicDisHistoryByCaseId(Long caseId)
			throws Exception {
		H1N1EpidemicDisHistory hedh = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(H1N1EpidemicDisHistory.class).add(
					Restrictions.eq("caseId", caseId)).list();
			if (list.size() > 0) {
				hedh = (H1N1EpidemicDisHistory) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return hedh;
	}

	public H1N1EpidemicDisHistory saveOrUpdateEpidemicDisHistoryByCaseId(
			H1N1EpidemicDisHistory hedh) throws Exception {
		Long id = hedh.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(hedh);
			} else {
				session.update(hedh);
			}
			hedh = (H1N1EpidemicDisHistory) session.get(H1N1EpidemicDisHistory.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return hedh;
	}
}
