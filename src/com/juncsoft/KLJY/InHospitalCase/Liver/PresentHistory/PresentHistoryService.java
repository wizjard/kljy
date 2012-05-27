package com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class PresentHistoryService {
	@SuppressWarnings("unchecked")
	public PresentIllnessHistoryN findContent(Long key) throws Exception {
		PresentIllnessHistoryN n = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(PresentIllnessHistoryN.class)
					.add(Restrictions.eq("caseId", key)).list();
			if (list.size() > 0) {
				n = (PresentIllnessHistoryN) list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return n;
	}

	@SuppressWarnings("unchecked")
	public String viewOldPre(Long key) throws Exception {
		String rst = "";
		PresentIllnessHistory n = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(PresentIllnessHistory.class)
					.add(Restrictions.eq("caseId", key)).list();
			if (list.size() > 0) {
				n = (PresentIllnessHistory) list.get(0);
				if (n != null) {
					if (n.getContent1() != null && n.getContent1().length() > 0) {
						rst += n.getContent1();
					}
					if (n.getContent2() != null && n.getContent2().length() > 0) {
						rst += "\n" + n.getContent2();
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}

	public Long saveOrUpdateContent(PresentIllnessHistoryN n) throws Exception {
		Long id = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (n.getId() == null || n.getId() <= 0) {
				id = (Long) session.save(n);
			} else {
				id = n.getId();
				session.update(n);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}

	public void deletePresentIllnessHistoryNx(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			PresentIllnessHistoryNx nx = new PresentIllnessHistoryNx();
			nx.setId(id);
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(nx);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void deleteTreatGrid(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			TreatGrid g = new TreatGrid();
			g.setId(id);
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(g);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}
}
