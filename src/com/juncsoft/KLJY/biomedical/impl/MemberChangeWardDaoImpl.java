package com.juncsoft.KLJY.biomedical.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.juncsoft.KLJY.biomedical.dao.MemberChangeWardDao;
import com.juncsoft.KLJY.biomedical.entity.MemberChangeWard;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.config.JunDBPool;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class MemberChangeWardDaoImpl implements MemberChangeWardDao {

	public void delete(MemberChangeWard mc) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			mc = this.get(mc.getId());
			List<MemberChangeWard> list = this.getByMemNo(mc.getMemNo());
			MemberInfo mem = (MemberInfo) session.get(MemberInfo.class, mc
					.getMemNo());
			if (list.size() >= 2) {
				MemberChangeWard c = list.get(list.size() - 2);
				mem.setCurrentWard(c.getWard());
			} else {
				mem.setCurrentWard(null);
			}
			session.update(mem);
			session.delete(mc);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public MemberChangeWard get(Long id) throws Exception {
		MemberChangeWard mc = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			mc = (MemberChangeWard) session.get(MemberChangeWard.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mc;
	}

	@SuppressWarnings("unchecked")
	public List<MemberChangeWard> getByMemNo(String memNo) throws Exception {
		List<MemberChangeWard> list = new ArrayList<MemberChangeWard>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(MemberChangeWard.class).add(
					Restrictions.eq("memNo", memNo)).addOrder(
					Order.asc("changeDate")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public Long save(MemberChangeWard mc) throws Exception {
		Long id = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			id = (Long) session.save(mc);
			MemberInfo mem = (MemberInfo) session.get(MemberInfo.class, mc
					.getMemNo());
			mem.setCurrentWard(mc.getWard());
			session.update(mem);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}

	public void update(MemberChangeWard mc) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.update(mc);
			MemberInfo mem = (MemberInfo) session.get(MemberInfo.class, mc
					.getMemNo());
			mem.setCurrentWard(mc.getWard());
			session.update(mem);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

}
