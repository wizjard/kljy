package com.juncsoft.KLJY.ResearchFollowup.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.juncsoft.KLJY.ResearchFollowup.dao.PatientResearchDao;
import com.juncsoft.KLJY.ResearchFollowup.entity.Followup;
import com.juncsoft.KLJY.ResearchFollowup.entity.FollowupCheckResult;
import com.juncsoft.KLJY.ResearchFollowup.entity.PatientResearch;
import com.juncsoft.KLJY.ResearchFollowup.entity.ResearchFollowSetUp;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.system.Research.entity.ResearchTopic;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class PatientResearchDaoImpl implements PatientResearchDao {

	public void research_delete(PatientResearch pr) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<Followup> followup = this
					.followup_findByResearchId(pr.getId());
			for (Followup fu : followup) {
				session.delete(fu);
			}
			session.delete(pr);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public List<PatientResearch> research_findByPatientId(Long id)
			throws Exception {
		List<PatientResearch> list = new ArrayList<PatientResearch>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(PatientResearch.class).add(
					Restrictions.eq("patientId", id)).addOrder(
					Order.asc("startDate")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public PatientResearch reserch_saveOrUpdate(PatientResearch pr)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (pr.getId() == null || pr.getId() <= 0) {
				pr.setId((Long) session.save(pr));
				pr = (PatientResearch) session.get(PatientResearch.class, pr.getId());
			} else {
				session.update(pr);
				pr = (PatientResearch) session.get(PatientResearch.class, pr.getId());
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return pr;
	}

	public void followup_delete(Followup fu) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(fu);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Followup> followup_findByResearchId(Long id) throws Exception {
		List<Followup> list = new ArrayList<Followup>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(Followup.class).add(
					Restrictions.eq("patientResearchId", id)).addOrder(
					Order.asc("followTimes")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public Followup followup_saveOrUpdate(Followup fu) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Followup followup = new Followup();		
			session.save(fu);		
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return fu;
	}

	public void followup_saveOrUpdate(List<Followup> list) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();			
			
			if(list.size() > 0){
				Followup followup = list.get(0);
				List<Followup> followupList = session.createQuery("from Followup where " +
						"patientResearchId = ?").setLong(0, followup.getPatientResearchId()).list();
				for(Followup fu : followupList){	
					session.delete(fu);
				}
			}
			
			for(Followup fu : list){
				session.save(fu);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}
	
	public Followup followup_findById(Long id) throws Exception {
		Followup fu = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			fu = (Followup) session.get(Followup.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return fu;
	}

	@SuppressWarnings("unchecked")
	public JSONObject notice_findAll(Integer start, Integer limit, int action)
			throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			JSONArray root = new JSONArray();
			List<Followup> list = new ArrayList<Followup>();
			long total = 0;
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Calendar cal = new GregorianCalendar();
			cal.setTime(new Date());
			cal.add(Calendar.HOUR, -24);
			if (action == 1) {
				list = session.createCriteria(Followup.class).add(
						Restrictions.gt("followupPlanDate", cal.getTime()))
						.add(Restrictions.isNull("noticeDate")).addOrder(
								Order.asc("followupPlanDate")).setFirstResult(
								start).setMaxResults(limit).list();
				total = (Long) session
						.createQuery(
								"select count(*) from Followup where followupPlanDate>? and noticeDate is null")
						.setDate(0, cal.getTime()).list().iterator().next();
			} else if (action == 2) {
				list = session.createCriteria(Followup.class).add(
						Restrictions.isNotNull("noticeDate")).addOrder(
						Order.desc("noticeDate")).setFirstResult(start)
						.setMaxResults(limit).list();
				total = (Long) session
						.createQuery(
								"select count(*) from Followup where noticeDate is not null")
						.list().iterator().next();
			} else if (action == 3) {
				list = session.createCriteria(Followup.class).addOrder(
						Order.desc("id")).list();
				total = (Long) session.createQuery(
						"select count(*) from Followup").list().iterator()
						.next();
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			for (Followup f : list) {
				PatientResearch pr = (PatientResearch) session.get(
						PatientResearch.class, f.getPatientResearchId());
				if (pr == null)
					continue;
				Patient p = (Patient) session.get(Patient.class, pr
						.getPatientId());
				if (p == null)
					continue;
				JSONObject js = new JSONObject();
				js.put("id", f.getId());
				js.put("patientName", p.getPatientName());
				js.put("patientNo", p.getPatientNo());
				js.put("researchId", pr.getResearchId());
				Date date = pr.getFollowupStart();
				if (date != null)
					js.put("followupStart", df.format(date));
				js.put("followTimes", f.getFollowTimes());
				js.put("followCycle", f.getFollowCycle());
				js.put("followContent", f.getFollowContent());
				js.put("remarkContent", f.getRemarkContent());
				date = f.getFollowupPlanDate();
				if (date != null) {
					js.put("followupPlanDate", df.format(date));
					js.put("followupLeast", Math.ceil((date.getTime() - cal
							.getTimeInMillis())
							/ (1000 * 60 * 60 * 24)) + 1);
				}
				if (action != 1) {
					js.put("followupLeast", "");
					date = f.getNoticeDate();
					if (date != null) {
						js.put("noticeDate", df.format(date));
					}
					date = f.getReserveDate();
					if (date != null) {
						js.put("reserveDate", df.format(date));
					}
				}
				root.add(js);
			}
			json.put("root", root);
			json.put("total", total);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public static void main(String[] args) {
		try {
			new PatientResearchDaoImpl().notice_findAll(0, 20, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public JSONArray followRegin_findByPatientId(Long id) throws Exception {
		JSONArray array = new JSONArray();
		List<Followup> fus = new ArrayList<Followup>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			
			fus.addAll(session.createCriteria(Followup.class).add(
					Restrictions.eq("patientId", id)).addOrder(
					Order.asc("comeDate")).list());
			tran.commit();
			array = JSONArray.fromObject(fus, JSONValueProcessor
					.formatDate("yyyy-MM-dd"));
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return array;
	}

	public ResearchTopic getResearchTopic(long researchTopicId) throws Exception {
		Session session = null;
		Transaction tran = null;
		ResearchTopic rt = new ResearchTopic();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			rt = (ResearchTopic) session.get(ResearchTopic.class, researchTopicId);
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rt;
	}

	public List<ResearchFollowSetUp> getResearchFollowSetUp(long researchTopicId) throws Exception {
		Session session = null;
		Transaction tran = null;
		List<ResearchFollowSetUp> list = new ArrayList<ResearchFollowSetUp>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from ResearchFollowSetUp where researchTopicId = ?").setLong(0, researchTopicId).list();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
	
	public void saveFollowupCheckResult(FollowupCheckResult fcr) throws Exception{
		Session session = null;
		Transaction tran = null;
		List<FollowupCheckResult> list = new ArrayList<FollowupCheckResult>();
		Connection conn = null;
		PreparedStatement ps = null;
		try {		
			
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from FollowupCheckResult where followupId = ?").setInteger(0, fcr.getFollowupId()).list();
			
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement("delete from t_Patient_FollowupCheckResult where id = ?");			
			for(FollowupCheckResult fucr : list){
				ps.setInt(1, fucr.getId());
				ps.execute();
			}
			System.out.println("jds");
			session.save(fcr);
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeConn(conn);
			DatabaseUtil.closePs(ps);
			DatabaseUtil.closeResource(session);
		}
	}

	public FollowupCheckResult getFollowupCheckResult(int followupId)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		List<FollowupCheckResult> list = new ArrayList<FollowupCheckResult>();
		FollowupCheckResult fcr = new FollowupCheckResult();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from FollowupCheckResult where followupId = ?").setInteger(0, followupId).list();
			if(list.size() > 0){
				fcr = list.get(0);
			}
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return fcr;
	}
	
	public JSONObject getFirstPatientFollowup(Long patientResearchId) throws Exception {
		JSONObject json = new JSONObject();
		List<Followup> list = new ArrayList<Followup>();
		Followup fu = new Followup();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from Followup where patientResearchId = ? and followTimes = ?").setLong(0, patientResearchId).setLong(1, 0).list();
			if(list.size() > 0){
				fu = list.get(0);
			}
			json = JSONObject.fromObject(fu);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}
}
