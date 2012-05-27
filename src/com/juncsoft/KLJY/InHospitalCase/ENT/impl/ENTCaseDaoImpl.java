package com.juncsoft.KLJY.InHospitalCase.ENT.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistoryENT;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistoryENTDetails;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.SpecialExamination;
import com.juncsoft.KLJY.InHospitalCase.ENT.dao.ENTCaseDao;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistory_OnSet;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class ENTCaseDaoImpl implements ENTCaseDao {

	@SuppressWarnings("unchecked")
	public SpecialExamination SpecialExamination_findByCaseId(Long id)
			throws Exception {
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

	public SpecialExamination SpecialExamination_saveOrUpdate(
			SpecialExamination se) throws Exception {
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

	// ---------------------------------------------现病史---------------------------------------------
	/**
	 * 删除起病史
	 */
	public void delePreOnsetsById(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (id > 0) {
				PresentIllnessHistory_OnSet onSet = (PresentIllnessHistory_OnSet) session
						.get(PresentIllnessHistory_OnSet.class, id);
				if (onSet != null) {
					session.delete(onSet);
				}
			} else
				System.out.println("现病史-起病史ID小于0，无法删除");
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 查询现病史
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PresentIllnessHistory getPrentByCaseId(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		PresentIllnessHistory p = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (id > 0) {
				String sql = " from com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistory where caseId=? ";
				List<PresentIllnessHistory> list = session.createQuery(sql)
						.setLong(0, id).list();
				System.out.println("list.size()：" + list.size() + "\tid：" + id);
				if (list.size() > 0) {
					p = list.get(0);
				}
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return p;
	}

	/**
	 * 保存现病史
	 */
	public PresentIllnessHistory prentSaveOrUpdate(PresentIllnessHistory pre)
			throws Exception {
		Session session = null;
		Transaction t = null;
		Long id = -1l;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			System.out.println("现病史ID：" + pre.getId());
			if (pre.getId() != null && pre.getId() > 0) {
				session.merge(pre);
				id = pre.getId();
			} else {
				id = (Long) session.save(pre);
			}
			pre = (PresentIllnessHistory) session.get(
					PresentIllnessHistory.class, id);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return pre;
	}

	@SuppressWarnings("unchecked")
	public PresentIllnessHistoryENT PresentIllness_findByCaseId(Long id)
			throws Exception {
		PresentIllnessHistoryENT se = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List list = session.createCriteria(PresentIllnessHistoryENT.class)
					.add(Restrictions.eq("caseId", id)).list();
			if (list.size() > 0) {
				se = (PresentIllnessHistoryENT) list.get(0);
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

	public PresentIllnessHistoryENT PresentIllness_saveOrUpdate(
			PresentIllnessHistoryENT se) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			Long id = se.getId();
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (se.getId() == null || se.getId() <= 0) {
				id = (Long) session.save(se);
			} else {
				session.update(se);
			}
			if (id != null)
				se=(PresentIllnessHistoryENT) session.get(PresentIllnessHistoryENT.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return se;
	}

	public void PresentIllness_deleteDetails(
			PresentIllnessHistoryENTDetails detail) throws Exception {
		Session session = null;
		Transaction t = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			session.delete(detail);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public PresentIllnessHistoryENTDetails PresentIllnessDetails_findById(
			Long id) throws Exception {
		PresentIllnessHistoryENTDetails d = null;
		Session session = null;
		Transaction t = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			d = (PresentIllnessHistoryENTDetails) session.get(
					PresentIllnessHistoryENTDetails.class, id);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return d;
	}

}