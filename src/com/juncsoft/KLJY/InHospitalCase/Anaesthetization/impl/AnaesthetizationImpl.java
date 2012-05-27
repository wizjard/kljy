package com.juncsoft.KLJY.InHospitalCase.Anaesthetization.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.Anaesthetization.dao.AnaesthetizationDao;
import com.juncsoft.KLJY.InHospitalCase.Anaesthetization.entity.AnaesthetizationRec;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class AnaesthetizationImpl implements AnaesthetizationDao {

	public JSONArray anaesthetizationRec_treeNodes(long id) throws Exception {
		// TODO Auto-generated method stub
		JSONArray array = new JSONArray();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<AnaesthetizationRec> list = session.createCriteria(AnaesthetizationRec.class).add(Restrictions.eq("caseId", id)).addOrder(Order.asc("time")).list();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			int index = 1;
			for (AnaesthetizationRec record : list) {
				JSONObject object = new JSONObject();
				object.put("id", record.getId());
				if (record.getTime() != null)
					object.put("text", index + "、" + "("
							+ format.format(record.getTime()) + ")"
							+ record.getTitle());
				else
					object.put("text", "The time is not define");
				object.put("leaf", true);
				object.put("href", "javascript:scrollTo(" + record.getId()
						+ ")");
				array.add(object);
				index++;
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return array;
	}

	public List<AnaesthetizationRec> anaesthetizationRec_findAllByCaseID(long id)
			throws Exception {
		List<AnaesthetizationRec> list = new ArrayList<AnaesthetizationRec>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(AnaesthetizationRec.class).add(
					Restrictions.eq("caseId", id)).addOrder(Order.asc("time"))
					.list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public AnaesthetizationRec anaesthetizationRec_saveOrUpdate(AnaesthetizationRec record)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date(Calendar.getInstance().getTimeInMillis());
			if (record.getId() == null || record.getId() <= 0) {
				record.setCreateTime(date);
				record.setModifyTime(date);
				record.setId((Long) session.save(record));
			} else {
				record.setModifyTime(date);
				session.update(record);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return record;
	}

	public void anaesthetizationRec_Delete(AnaesthetizationRec record)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(record);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public AnaesthetizationRec anaesthetizationRec_findById(Long id)
			throws Exception {
		AnaesthetizationRec record = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			record = (AnaesthetizationRec) session.get(AnaesthetizationRec.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return record;
	}
}
