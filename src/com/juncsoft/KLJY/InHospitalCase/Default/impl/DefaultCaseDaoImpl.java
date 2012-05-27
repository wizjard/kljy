package com.juncsoft.KLJY.InHospitalCase.Default.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.Default.dao.DefaultCaseDao;
import com.juncsoft.KLJY.InHospitalCase.Default.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.InHospitalCase.Default.entity.SpecialExamination;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class DefaultCaseDaoImpl implements DefaultCaseDao {

	@SuppressWarnings("unchecked")
	public PresentIllnessHistory pi_findByCaseId(Long id) throws Exception {
		PresentIllnessHistory pi = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(PresentIllnessHistory.class)
					.add(Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				pi = (PresentIllnessHistory) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return pi;
	}

	public PresentIllnessHistory pi_saveOrUpdate(PresentIllnessHistory pi)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (pi.getId() == null || pi.getId() <= 0) {
				pi.setId((Long) session.save(pi));
			} else {
				session.update(pi);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return pi;
	}

	@SuppressWarnings("unchecked")
	public SpecialExamination se_findByCaseId(Long id) throws Exception {
		SpecialExamination se = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(SpecialExamination.class).add(
					Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				se = (SpecialExamination) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return se;
	}

	public SpecialExamination se_saveOrUpdate(SpecialExamination se)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (se.getId() == null || se.getId() <= 0) {
				se.setId((Long) session.save(se));
			} else {
				session.update(se);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return se;
	}

}