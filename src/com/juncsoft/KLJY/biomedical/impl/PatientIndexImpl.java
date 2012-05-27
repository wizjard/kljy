package com.juncsoft.KLJY.biomedical.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import com.juncsoft.KLJY.biomedical.dao.PatientIndexDao;
import com.juncsoft.KLJY.biomedical.entity.PatientIndex;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class PatientIndexImpl implements PatientIndexDao {

	@SuppressWarnings("unchecked")
	public List<PatientIndex> getAll(Integer start, Integer limit,
			Criterion... criterias) throws Exception {
		List<PatientIndex> list;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(PatientIndex.class);
			for (Criterion c : criterias) {
				criteria.add(c);
			}
			list = criteria.setFirstResult(start).setMaxResults(limit).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public long getTotal(Criterion... criterias) throws Exception {
		long rst = 0;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(PatientIndex.class);
			for (Criterion c : criterias) {
				criteria.add(c);
			}
			rst = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();
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
