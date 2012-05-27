package com.juncsoft.KLJY.InHospitalCase.Eye.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.Eye.dao.EyeDao;
import com.juncsoft.KLJY.InHospitalCase.Eye.entity.Eye;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class EyeImpl implements EyeDao{

	public Eye eye_SaveorUpdate(Eye eye) throws Exception {
		Long id = eye.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(eye);
			} else {
				session.update(eye);
			}
			eye = (Eye) session.get(Eye.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return eye;
	}

	public Eye eye_findByCaseID(Long caseId) throws Exception {
		Eye eye = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(Eye.class).add(Restrictions.eq("caseId", caseId)).list();
			if (list.size() > 0) {
				eye = (Eye) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return eye;
	}

}
