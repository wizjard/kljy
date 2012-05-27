package com.juncsoft.KLJY.InHospitalCase.Surgery.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.juncsoft.KLJY.InHospitalCase.Surgery.dao.SurgeryCaseDao;
import com.juncsoft.KLJY.InHospitalCase.Surgery.entity.SpecialExamination;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class SurgeryCaseDaoImpl implements SurgeryCaseDao {

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