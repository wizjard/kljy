package com.juncsoft.KLJY.InHospitalCase.CaseMaster.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.dao.InHspCaseMasterDao;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.patient.dao.PatientDao;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.DataUtil;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class InHspCaseMasterDaoImpl implements InHspCaseMasterDao {

	public void delete(InHspCaseMaster cm) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(cm);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public InHspCaseMaster findById(Long id) throws Exception {
		InHspCaseMaster cm = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			cm = (InHspCaseMaster) session.get(InHspCaseMaster.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return cm;
	}

	public InHspCaseMaster saveOrUpdate(InHspCaseMaster cm) throws Exception {
		Long id = cm.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				PatientDao dao = (PatientDao) DaoFactory
						.InstanceImplement(PatientDao.class);
				cm.setId((Long) session.save(cm));
				cm.getPatientId();
				//liugang 2011-08-31 update 兼容会员健康档案
				cm.setFlag(0);
				Patient p = dao.findById(cm.getPatientId());
				p.setCurrentWardCode(cm.getCurrentWordCode());
				dao.saveOrUpdate(p);
			} else {
				session.update(cm);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return cm;
	}

	@SuppressWarnings("unchecked")
	public List<InHspCaseMaster> queryAllByPatient(Long id) throws Exception {
		List<InHspCaseMaster> list = new ArrayList<InHspCaseMaster>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(InHspCaseMaster.class).add(
					Restrictions.eq("patientId", id)).add(
					Restrictions.eq("flag", 0)).addOrder(
					Order.desc("inHspDate")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public JSONObject queryPersonalInfoByCaseID(Long id) throws Exception {
		JSONObject json = new JSONObject();
		try {
			InHspCaseMaster cm = this.findById(id);
			PatientDao dao = (PatientDao) DaoFactory
					.InstanceImplement(PatientDao.class);
			Patient patient = dao.findById(cm.getId());
			json.put("InHspTimes", cm.getInHspTimes());
			json.put("PatientName", patient.getPatientName());
			json.put("PatientNo", patient.getPatientNo());
		} catch (Exception e) {
			throw e;
		}
		return json;
	}
	
	public String getAge(Long pid, Date inHspDate) throws Exception {
		String age = "";
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient patient = (Patient) session.get(Patient.class, pid);
			age = DataUtil.getCurrentAge(patient.getBirthday(), inHspDate);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return age;
	}

	public JSONObject getAgeAndSex(int pid, int scId) throws Exception {
		Session session = null;
		Transaction tran = null;
		JSONObject mp = new JSONObject();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "select ca.age,p.gender from t_InHsp_CaseMaster ca inner join" +
					" t_patient p on p.id = ca.patientid and p.id = "+pid+" and  ca.id="+scId;
			List list = session.createSQLQuery(hql).list();
			if(list != null && list.size() > 0){
				Object[] obj = (Object[])list.get(0);
				mp.put("age",obj[0]);
				mp.put("gender", obj[1]);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

}
