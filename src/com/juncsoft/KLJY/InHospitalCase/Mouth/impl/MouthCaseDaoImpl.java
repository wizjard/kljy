package com.juncsoft.KLJY.InHospitalCase.Mouth.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.juncsoft.KLJY.InHospitalCase.Mouth.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.InHospitalCase.Mouth.dao.MouthCaseDao;
import com.juncsoft.KLJY.InHospitalCase.Mouth.entity.PresentIllnessHistory_OnSet;
import com.juncsoft.KLJY.InHospitalCase.Mouth.entity.PresentIllnessHistory_Treat;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class MouthCaseDaoImpl implements MouthCaseDao {

	public void PresentIllnessHistory_deleteOnSet(Long id) throws Exception {
		PresentIllnessHistory_OnSet onset = new PresentIllnessHistory_OnSet();
		onset.setId(id);
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(onset);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void PresentIllnessHistory_deleteTreat(Long id) throws Exception {
		PresentIllnessHistory_Treat treat = new PresentIllnessHistory_Treat();
		treat.setId(id);
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(treat);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public PresentIllnessHistory PresentIllnessHistory_findByCaseID(Long id)
			throws Exception {
		PresentIllnessHistory ph = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(PresentIllnessHistory.class)
					.add(Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				ph = (PresentIllnessHistory) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ph;
	}

	public PresentIllnessHistory PresentIllnessHistory_saveOrUpdate(
			PresentIllnessHistory ph) throws Exception {
		Long id = ph.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(ph);
			} else {
				session.update(ph);
			}
			ph = (PresentIllnessHistory) session.get(
					PresentIllnessHistory.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return ph;
	}
}
