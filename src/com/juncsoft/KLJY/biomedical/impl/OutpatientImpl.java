package com.juncsoft.KLJY.biomedical.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.biomedical.dao.OutPatientDao;
import com.juncsoft.KLJY.biomedical.entity.OutpatientGenerator;
import com.juncsoft.KLJY.biomedical.entity.OutpatientRecord;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.stongnet.sms.http.Sms;
import com.stongnet.sms.http.SmsOperator;

public class OutpatientImpl implements OutPatientDao {

	public OutpatientRecord get(Long id) throws Exception {
		OutpatientRecord rec = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			rec = (OutpatientRecord) session.get(OutpatientRecord.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rec;
	}

	@SuppressWarnings("unchecked")
	public List<OutpatientRecord> getAll(Long patientId) throws Exception {
		List<OutpatientRecord> list = new ArrayList<OutpatientRecord>();
		Session session = null;
		Transaction tran = null;
		try {
			Patient pat = new Patient();
			pat.setId(patientId);
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(OutpatientRecord.class).add(
					Restrictions.eq("patient", pat)).addOrder(
					Order.asc("comeDate")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	public Long saveGenerator(OutpatientGenerator gene) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = gene.getStartDate();
			String dates = new SimpleDateFormat("yyyy-MM-dd").format(date);
			date.setMonth(date.getMonth() + gene.getCycle());
			gene.setPlanDate(date);
			gene.setStartDate(java.sql.Date.valueOf(dates));
			Long geneId = (Long) session.save(gene);
			gene.setId(geneId);
			OutpatientRecord record = new OutpatientRecord();
			record.setPatient(gene.getPatient());
			record.setGenerator1(gene);
			session.save(record);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return gene.getId();
	}

	public OutpatientGenerator getGenerator(Long id) throws Exception {
		OutpatientGenerator g = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			g = (OutpatientGenerator) session
					.get(OutpatientGenerator.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return g;
	}

	@SuppressWarnings("deprecation")
	public void updateGenerator(OutpatientGenerator gene) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = gene.getStartDate();
			String dates = new SimpleDateFormat("yyyy-MM-dd").format(date);
			date.setMonth(date.getMonth() + gene.getCycle());
			gene.setPlanDate(date);
			gene.setStartDate(java.sql.Date.valueOf(dates));
			session.update(gene);
			Patient pat = gene.getPatient();
			List<Sms> smss = new ArrayList<Sms>();
			if (pat != null) {
				Sms sms = new Sms();
				sms.phone = pat.getMobilePhone();
				sms.content = gene.getSmsContent();
				smss.add(sms);
			}
			this.sendSms(smss);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void update(OutpatientRecord record) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.update(record);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public List<OutpatientGenerator> getAllGenerator(Integer start,
			Integer limit, Criterion... criterions) throws Exception {
		List<OutpatientGenerator> list;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session
					.createCriteria(OutpatientGenerator.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			list = criteria.setFirstResult(start).setMaxResults(limit)
					.addOrder(Order.desc("planDate")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public long getTotalGenerator(Criterion... criterions) throws Exception {
		long rst = 0;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session
					.createCriteria(OutpatientGenerator.class);
			for (Criterion c : criterions) {
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

	public String sendSms(List<Sms> smss) throws Exception {
		String strResult;
		try {
			SmsOperator smsOp = SmsOperator.getInstance();
			strResult = smsOp.sendSms("101100-SJB-HUAX-683842", "OXTBKKMN",
					smss.toArray(new Sms[] {}));
		} catch (Exception e) {
			throw e;
		}
		return strResult;
	}

}
