package com.juncsoft.KLJY.ResearchFollowup.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.juncsoft.KLJY.ResearchFollowup.dao.FollowReginDao;
import com.juncsoft.KLJY.ResearchFollowup.entity.ChronicLiver;
import com.juncsoft.KLJY.ResearchFollowup.entity.HealthSurvey;
import com.juncsoft.KLJY.ResearchFollowup.entity.OutPutientCase;
import com.juncsoft.KLJY.ResearchFollowup.entity.TCMSysptomScore;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class FollowReginDaoImpl implements FollowReginDao {

	public OutPutientCase OutPutientCase_SaveOrUpdate(OutPutientCase op)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			Long id = op.getId();
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(op);
			} else {
				session.update(op);
			}
			op = (OutPutientCase) session.get(OutPutientCase.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return op;
	}

	@SuppressWarnings("unchecked")
	public OutPutientCase OutPutientCase_findByFollowupId(Long id)
			throws Exception {
		OutPutientCase op = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(OutPutientCase.class).add(
					Restrictions.eq("followupId", id)).list();
			if (list.size() > 0) {
				op = (OutPutientCase) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return op;
	}

	public HealthSurvey HealthSurvey_SaveOrUpdate(HealthSurvey hs)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			Long id = hs.getId();
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				hs.setId((Long) session.save(hs));
			} else {
				session.update(hs);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return hs;
	}

	@SuppressWarnings("unchecked")
	public HealthSurvey HealthSurvey_findByFollowupId(Long id) throws Exception {
		HealthSurvey hs = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(HealthSurvey.class).add(
					Restrictions.eq("followupId", id)).list();
			if (list.size() > 0) {
				hs = (HealthSurvey) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return hs;
	}

	public ChronicLiver ChronicLiver_SaveOrUpdate(ChronicLiver cl)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			Long id = cl.getId();
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				cl.setId((Long) session.save(cl));
			} else {
				session.update(cl);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return cl;
	}

	@SuppressWarnings("unchecked")
	public ChronicLiver ChronicLiver_findByFollowupId(Long id) throws Exception {
		ChronicLiver cl = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(ChronicLiver.class).add(
					Restrictions.eq("followupId", id)).list();
			if (list.size() > 0) {
				cl = (ChronicLiver) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return cl;
	}

	public TCMSysptomScore TCMSysptomScore_SaveOrUpdate(TCMSysptomScore tcm)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			Long id = tcm.getId();
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				tcm.setId((Long) session.save(tcm));
			} else {
				session.update(tcm);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return tcm;
	}

	@SuppressWarnings("unchecked")
	public TCMSysptomScore TCMSysptomScore_findByFollowupId(Long id)
			throws Exception {
		TCMSysptomScore c = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(TCMSysptomScore.class).add(
					Restrictions.eq("followupId", id)).list();
			if (list.size() > 0) {
				c = (TCMSysptomScore) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return c;
	}
}
