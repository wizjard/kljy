package com.juncsoft.KLJY.ResearchFollowup.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.ResearchFollowup.dao.FollowSetUpDao;
import com.juncsoft.KLJY.ResearchFollowup.entity.ResearchFollowSetUp;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class FollowSetUpImpl implements FollowSetUpDao {

	public void deleteFollopupPro(int researchTopicId) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<ResearchFollowSetUp> list = session.createQuery("from " +
					"ResearchFollowSetUp where researchTopicId = ?").setInteger(0, researchTopicId).list();
			for(ResearchFollowSetUp rf : list){
				session.delete(rf);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public List<ResearchFollowSetUp> getFollopupPro(int researchTopicId) throws Exception {
		Session session = null;
		Transaction tran = null;
		List<ResearchFollowSetUp> list = new ArrayList<ResearchFollowSetUp>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from " +
					"ResearchFollowSetUp where researchTopicId = ?").setInteger(0, researchTopicId).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public void saveFollopupPro(List<ResearchFollowSetUp> list) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<ResearchFollowSetUp> listfordel = session.createQuery("from " +
					"ResearchFollowSetUp where researchTopicId = ?").setInteger(0, list.get(0).getResearchTopicId()).list();
			for(ResearchFollowSetUp rf : listfordel){
				session.delete(rf);
			}
			for(ResearchFollowSetUp rf : list){
				session.save(rf);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

}
