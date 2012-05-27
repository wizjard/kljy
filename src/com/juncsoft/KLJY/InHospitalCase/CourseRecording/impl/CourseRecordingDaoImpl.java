package com.juncsoft.KLJY.InHospitalCase.CourseRecording.impl;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.dao.CourseRecordingDao;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.Consultation;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DailyCourseRec;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DeathCaseDiscuss;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DeathRecord;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.InHspDied24;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.InHspRec24;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.PreviousCaseDiscuss;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.PreviousSurgerySummary;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.SurgeryRecord;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.WardRoundRec;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.ChiefComplaint;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabExamination;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PhysicalExamination;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class CourseRecordingDaoImpl implements CourseRecordingDao {

	public DailyCourseRec findDailyCourseRecById(int id) throws Exception {
		Session session = null;
		Transaction tran = null;
		DailyCourseRec dc = new DailyCourseRec();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			dc = (DailyCourseRec) session.get(DailyCourseRec.class, (long) id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return dc;
	}

	public void DailyCourseRec_delete(DailyCourseRec record) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(record);
			tran.commit();
			DailyCourseRec_Lab_deleteByCourseId(record.getId());
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public List<DailyCourseRec> DailyCourseRec_findAllByCaseID(Long id)
			throws Exception {
		List<DailyCourseRec> list = new ArrayList<DailyCourseRec>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(DailyCourseRec.class).add(
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

	public DailyCourseRec DailyCourseRec_findById(Long id) throws Exception {
		DailyCourseRec record = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			record = (DailyCourseRec) session.get(DailyCourseRec.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return record;
	}

	public DailyCourseRec DailyCourseRec_saveOrUpdate(DailyCourseRec record)
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

	@SuppressWarnings("unchecked")
	public JSONArray DailyCourseRec_treeNodes(Long id) throws Exception {
		JSONArray array = new JSONArray();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<DailyCourseRec> list = session.createCriteria(
					DailyCourseRec.class).add(Restrictions.eq("caseId", id))
					.addOrder(Order.asc("time")).list();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			int index = 1;
			for (DailyCourseRec record : list) {
				JSONObject object = new JSONObject();
				object.put("id", record.getId());
				if (record.getTime() != null)
					object.put("text", index + "、"
							+ format.format(record.getTime())
							+ record.getTitle());
				else
					object.put("text", "The time is not define");
				object.put("leaf", true);
				object.put("iconCls", "icon-none");
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

	public Long DailyCourseRec_Lab_saveOrUpdate(Long recId, LabExamination lab)
			throws Exception {
		Long id = null;
		Session session = null;
		Transaction tran = null;
		try {
			id = lab.getId();
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (id == null || id <= 0) {
				id = (Long) session.save(lab);
				Connection conn = DatabaseUtil.getConnection();
				PreparedStatement sm = conn
						.prepareStatement("insert into t_CourseRec_DailyCourseRec_LabExam(DailyCourseRecID,LabExaminationID)values(?,?)");
				sm.setLong(1, recId);
				sm.setLong(2, id);
				sm.executeUpdate();
				conn.close();
			} else {
				session.update(lab);
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

	public LabExamination DailyCourseRec_Lab_findByCourseId(Long id)
			throws Exception {
		LabExamination lab = null;
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn
					.prepareStatement("select LabExaminationID from t_CourseRec_DailyCourseRec_LabExam where DailyCourseRecID=?");
			sm.setLong(1, id);
			rs = sm.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
				Session session = DatabaseUtil.getSession();
				Transaction tran = session.beginTransaction();
				lab = (LabExamination) session.get(LabExamination.class, id);
				tran.commit();
				session.close();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return lab;
	}

	public void DailyCourseRec_Lab_deleteByCourseId(Long id) throws Exception {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select LabExaminationID from t_CourseRec_DailyCourseRec_LabExam where DailyCourseRecID=?";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setLong(1, id);
			rs = sm.executeQuery();
			if (rs.next()) {
				Long labId = rs.getLong(1);
				sql = "delete from t_InHsp_Liver_LabExamination where id=?";
				sm = conn.prepareStatement(sql);
				sm.setLong(1, labId);
				sm.executeUpdate();
				sql = "delete from t_CourseRec_DailyCourseRec_LabExam where LabExaminationID=?";
				sm = conn.prepareStatement(sql);
				sm.setLong(1, labId);
				sm.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
	}

	public void Consultation_delete(Consultation c) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(c);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public Consultation Consultation_findById(Long id) throws Exception {
		Consultation c = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			c = (Consultation) session.get(Consultation.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return c;
	}

	public Consultation Consultation_saveOrUpdate(Consultation c)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (c.getId() == null || c.getId() <= 0) {
				c.setSubmiter(null);
				c.setChecker(null);
				c.setVerifyStatus(0);
				c.setId((Long) session.save(c));
			} else {
				Consultation oc = this.Consultation_findById(c.getId());
				c.setSubmiter(oc.getSubmiter());
				c.setChecker(oc.getChecker());
				c.setVerifyStatus(oc.getVerifyStatus());
				session.merge(c);
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

	public JSONObject Consultation_print(Long id) throws Exception {
		JSONObject json = new JSONObject();
		try {
			Consultation c = getConsultPrintData(id);
			json = JSONObject.fromObject(c, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (c.getTime() != null) {
				json.put("time", df.format(c.getTime()));
			}
			if (c.getApptime() != null) {
				json.put("apptime", df.format(c.getApptime()));
			}
			if (c.getRectime() != null) {
				json.put("rectime", df.format(c.getRectime()));
			}
		} catch (Exception e) {
			throw e;
		}
		return json;
	}

	private Consultation getConsultPrintData(Long id) throws Exception {
		Consultation c = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			c = (Consultation) session.get(Consultation.class, id);
			if (c.getConTarget().equals("1")) {
				c.setConTarget("协助诊治");
			} else if (c.getConTarget().equals("2")) {
				c.setConTarget("协助明确诊断");
			} else if (c.getConTarget().equals("3")) {
				c.setConTarget("指导进一步治疗");
			} else if (c.getConTarget().equals("99")) {
				c.setConTarget(c.getConTarget0());
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

	public void PreviousSurgerySummary_delete(PreviousSurgerySummary p)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(p);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public PreviousSurgerySummary PreviousSurgerySummary_findById(Long id)
			throws Exception {
		PreviousSurgerySummary p = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			p = (PreviousSurgerySummary) session.get(
					PreviousSurgerySummary.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return p;
	}

	public JSONObject PreviousSurgerySummary_print(Long id) throws Exception {
		JSONObject json = new JSONObject();
		try {
			PreviousSurgerySummary p = this.PreviousSurgerySummary_findById(id);
			json = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			if (p.getInhspTime() != null) {
				json.put("inhspTime", new SimpleDateFormat("yyyy-MM-dd HH:mm")
						.format(p.getInhspTime()));
				if (p.getTime() != null) {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date date1 = df.parse(df.format(p.getTime()));
					Date date2 = df.parse(df.format(p.getInhspTime()));
					long time = date1.getTime() - date2.getTime();
					long day = time / (3600 * 1000 * 24) + 1;
					json.put("inhspTime2", day);
				}
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (p.getTime() != null) {
				json.put("time", df.format(p.getTime()));
			}
			if (p.getSurgeryTime() != null) {
				json.put("surgeryTime", df.format(p.getSurgeryTime()));
			}
		} catch (Exception e) {
			throw e;
		}
		return json;
	}

	public PreviousSurgerySummary PreviousSurgerySummary_saveOrUpdate(
			PreviousSurgerySummary p) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (p.getId() == null || p.getId() <= 0) {
				p.setSubmiter(null);
				p.setChecker(null);
				p.setVerifyStatus(0);
				p.setId((Long) session.save(p));
			} else {
				PreviousSurgerySummary oc = this
						.PreviousSurgerySummary_findById(p.getId());
				p.setSubmiter(oc.getSubmiter());
				p.setChecker(oc.getChecker());
				p.setVerifyStatus(oc.getVerifyStatus());
				session.merge(p);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return p;
	}

	public void PreviousCaseDiscuss_delete(PreviousCaseDiscuss p)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(p);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public PreviousCaseDiscuss PreviousCaseDiscuss_findById(Long id)
			throws Exception {
		PreviousCaseDiscuss p = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			p = (PreviousCaseDiscuss) session
					.get(PreviousCaseDiscuss.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return p;
	}

	public JSONObject PreviousCaseDiscuss_print(Long id) throws Exception {
		JSONObject json = new JSONObject();
		try {
			PreviousCaseDiscuss p = this.PreviousCaseDiscuss_findById(id);
			json = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			if (p.getInhspTime() != null) {
				json.put("inhspTime", new SimpleDateFormat("yyyy-MM-dd HH:mm")
						.format(p.getInhspTime()));
				if (p.getTime() != null) {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date date1 = df.parse(df.format(p.getTime()));
					Date date2 = df.parse(df.format(p.getInhspTime()));
					long time = date1.getTime() - date2.getTime();
					long day = time / (3600 * 1000 * 24) + 1;
					json.put("inhspTime2", day);
				}
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (p.getTime() != null) {
				json.put("time", df.format(p.getTime()));
			}
			if (p.getDiscussTime() != null) {
				json.put("discussTime", df.format(p.getDiscussTime()));
			}
		} catch (Exception e) {
			throw e;
		}
		return json;
	}

	public PreviousCaseDiscuss PreviousCaseDiscuss_saveOrUpdate(
			PreviousCaseDiscuss p) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (p.getId() == null || p.getId() <= 0) {
				p.setSubmiter(null);
				p.setChecker(null);
				p.setVerifyStatus(0);
				p.setId((Long) session.save(p));
			} else {
				PreviousCaseDiscuss oc = this.PreviousCaseDiscuss_findById(p
						.getId());
				p.setSubmiter(oc.getSubmiter());
				p.setChecker(oc.getChecker());
				p.setVerifyStatus(oc.getVerifyStatus());
				session.merge(p);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return p;
	}

	public void SurgeryRecord_delete(SurgeryRecord s) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(s);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public SurgeryRecord SurgeryRecord_findById(Long id) throws Exception {
		SurgeryRecord s = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			s = (SurgeryRecord) session.get(SurgeryRecord.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return s;
	}

	public JSONObject SurgeryRecord_print(Long id) throws Exception {
		JSONObject json = new JSONObject();
		try {
			SurgeryRecord p = this.SurgeryRecord_findById(id);
			json = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时mm分"));
			if (p.getSurgeryFromTime() != null && p.getSurgeryToTime() != null) {
				long time = p.getSurgeryToTime().getTime()
						- p.getSurgeryFromTime().getTime();
				long hours = time / (3600 * 1000);
				long mins = (time - hours * 3600 * 1000) / (60 * 1000);
				json.put("surgeryTime", json.getString("surgeryFromTime")
						+ "开始&nbsp;至&nbsp;" + json.getString("surgeryToTime")
						+ "完毕&nbsp;&nbsp;共用" + hours + "小时" + mins + "分钟");
			}else{
				json.put("surgeryTime", "");
			}
			if (p.getBleedingVolumn() != null
					&& p.getBleedingVolumn().length() > 0) {
				json.put("bleedingVolumn", p.getBleedingVolumn() + "ml");
			}
			if (p.getTransBloodVolumn() != null
					&& p.getTransBloodVolumn().length() > 0) {
				json.put("transBloodVolumn", p.getTransBloodVolumn() + "ml");
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (p.getTime() != null) {
				json.put("time", df.format(p.getTime()));
			}
			if (p.getSurgeryFromTime() != null) {
				json.put("surgeryFromTime", df.format(p.getSurgeryFromTime()));
			}
			if (p.getSurgeryToTime() != null) {
				json.put("surgeryToTime", df.format(p.getSurgeryToTime()));
			}
		} catch (Exception e) {
			throw e;
		}
		return json;
	}

	public SurgeryRecord SurgeryRecord_saveOrUpdate(SurgeryRecord s)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (s.getId() == null || s.getId() <= 0) {
				s.setSubmiter(null);
				s.setChecker(null);
				s.setVerifyStatus(0);
				s.setId((Long) session.save(s));
			} else {
				SurgeryRecord os = this.SurgeryRecord_findById(s.getId());
				s.setSubmiter(os.getSubmiter());
				s.setChecker(os.getChecker());
				s.setVerifyStatus(os.getVerifyStatus());
				session.merge(s);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return s;
	}

	public void DeathRecord_delete(DeathRecord d) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(d);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public DeathRecord DeathRecord_findById(Long id) throws Exception {
		DeathRecord d = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			d = (DeathRecord) session.get(DeathRecord.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return d;
	}

	public JSONObject DeathRecord_print(Long id) throws Exception {
		JSONObject json = new JSONObject();
		try {
			DeathRecord d = this.DeathRecord_findById(id);
			json = JSONObject.fromObject(d, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时mm分"));
			if (d.getTime() != null) {
				json.put("time", new SimpleDateFormat("yyyy年MM月dd日HH时mm分")
						.format(d.getTime()));
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (d.getInhspTime() != null) {
				json.put("inhspTime", df.format(d.getInhspTime()));
			}
			if (d.getDeathTime() != null) {
				json.put("deathTime", df.format(d.getDeathTime()));
			}
			
		} catch (Exception e) {
			throw e;
		}
		return json;
	}

	public DeathRecord DeathRecord_saveOrUpdate(DeathRecord d) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (d.getId() == null || d.getId() <= 0) {
				d.setSubmiter(null);
				d.setChecker(null);
				d.setVerifyStatus(0);
				d.setId((Long) session.save(d));
			} else {
				DeathRecord os = this.DeathRecord_findById(d.getId());
				d.setSubmiter(os.getSubmiter());
				d.setChecker(os.getChecker());
				d.setVerifyStatus(os.getVerifyStatus());
				session.merge(d);
			}
			
			// 死亡时更新病历主表记录
			if(d.getDeathDiag() != null && !d.getDeathDiag().equals("")){
				InHspCaseMaster master = (InHspCaseMaster) session.get(
						InHspCaseMaster.class, d.getCaseId());
				master.setOutDate(d.getDeathTime());
			}
			
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return d;
	}

	public void DeathCaseDiscuss_delete(DeathCaseDiscuss d) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(d);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public DeathCaseDiscuss DeathCaseDiscuss_findById(Long id) throws Exception {
		DeathCaseDiscuss d = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			d = (DeathCaseDiscuss) session.get(DeathCaseDiscuss.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return d;
	}

	public JSONObject DeathCaseDiscuss_print(Long id) throws Exception {
		JSONObject json = new JSONObject();
		try {
			DeathCaseDiscuss d = this.DeathCaseDiscuss_findById(id);
			json = JSONObject.fromObject(d, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (d.getTime() != null) {
				json.put("time", df.format(d.getTime()));
			}
			if (d.getDiscussTime() != null) {
				json.put("discussTime", df.format(d.getDiscussTime()));
			}
		} catch (Exception e) {
			throw e;
		}
		return json;
	}

	public DeathCaseDiscuss DeathCaseDiscuss_saveOrUpdate(DeathCaseDiscuss d)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (d.getId() == null || d.getId() <= 0) {
				d.setSubmiter(null);
				d.setChecker(null);
				d.setVerifyStatus(0);
				d.setId((Long) session.save(d));
			} else {
				DeathCaseDiscuss os = this.DeathCaseDiscuss_findById(d.getId());
				d.setSubmiter(os.getSubmiter());
				d.setChecker(os.getChecker());
				d.setVerifyStatus(os.getVerifyStatus());
				session.merge(d);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return d;
	}

	@SuppressWarnings("unchecked")
	public JSONArray public_mainMenu(String entityName, Long kid)
			throws Exception {
		JSONArray array = new JSONArray();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<Object> list = session.createQuery(
					"from " + entityName + " where caseId=? order by time")
					.setLong(0, kid).list();
			for (Object object : list) {
				JSONObject json = JSONObject.fromObject(object,
						JSONValueProcessor.formatDate("yyyy年MM月dd日HH时"));
				array.add("{id:" + json.getString("id") + ",time:'"
						+ json.getString("time") + "'}");
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

	public JSONObject public_patientInfo(Long kid) throws Exception {
		JSONObject object = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			InHspCaseMaster cm = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, kid);
			if (cm != null) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				if (cm.getInHspDate() != null) {
					object.put("inhspTime", df.format(cm.getInHspDate()));
				}
				object.put("age", cm.getAge());
				object.put("ward", DictionaryUtil.getIndependentDictionaryText(
						"belong", cm.getCurrentWordCode()));
				Patient p = (Patient) session.get(Patient.class, cm
						.getPatientId());
				if (p != null) {
					object.put("name", p.getPatientName());
					object.put("patientNo", p.getPatientNo());
					object.put("gender", DictionaryUtil
							.getIndependentDictionaryText("gender_gb", p
									.getGender()));
				}
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return object;
	}

	public JSONObject public_verify(Long id, Long submiter, Long checker,
			int verifyStatus, String entity) throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Object object = session
					.get(
							Class
									.forName("com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity."
											+ entity), id);
			if (entity.equalsIgnoreCase("Consultation")) {
				Consultation c = (Consultation) object;
				c.setSubmiter(submiter);
				c.setChecker(checker);
				c.setVerifyStatus(verifyStatus);
				object = c;
			} else if (entity.equalsIgnoreCase("PreviousSurgerySummary")) {
				PreviousSurgerySummary p = (PreviousSurgerySummary) object;
				p.setSubmiter(submiter);
				p.setChecker(checker);
				p.setVerifyStatus(verifyStatus);
				object = p;
			} else if (entity.equalsIgnoreCase("PreviousCaseDiscuss")) {
				PreviousCaseDiscuss p = (PreviousCaseDiscuss) object;
				p.setSubmiter(submiter);
				p.setChecker(checker);
				p.setVerifyStatus(verifyStatus);
				object = p;
			} else if (entity.equalsIgnoreCase("SurgeryRecord")) {
				SurgeryRecord s = (SurgeryRecord) object;
				s.setSubmiter(submiter);
				s.setChecker(checker);
				s.setVerifyStatus(verifyStatus);
				object = s;
			} else if (entity.equalsIgnoreCase("DeathRecord")) {
				DeathRecord d = (DeathRecord) object;
				d.setSubmiter(submiter);
				d.setChecker(checker);
				d.setVerifyStatus(verifyStatus);
				object = d;
			} else if (entity.equalsIgnoreCase("DeathCaseDiscuss")) {
				DeathCaseDiscuss d = (DeathCaseDiscuss) object;
				d.setSubmiter(submiter);
				d.setChecker(checker);
				d.setVerifyStatus(verifyStatus);
				object = d;
			} else if (entity.equalsIgnoreCase("InHspRec24")) {
				InHspRec24 d = (InHspRec24) object;
				d.setSubmiter(submiter);
				d.setChecker(checker);
				d.setVerifyStatus(verifyStatus);
				object = d;
			}
			session.update(object);
			tran.commit();
			json.put("submiter", submiter);
			json.put("checker", checker);
			json.put("verifyStatus", verifyStatus);
			json.put("submiter_name", DictionaryUtil
					.getIndependentDictionaryText("userName", submiter + ""));
			json.put("checker_name", DictionaryUtil
					.getIndependentDictionaryText("userName", checker + ""));
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public void InHspRec24_delete(InHspRec24 d) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(d);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public InHspRec24 InHspRec24_findById(Long id) throws Exception {
		InHspRec24 d = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			d = (InHspRec24) session.get(InHspRec24.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return d;
	}

	public JSONObject InHspRec24_print(Long id) throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			InHspRec24 d = this.InHspRec24_findById(id);
			json = JSONObject.fromObject(d, JSONValueProcessor
					.formatDate("yyyy-MM-dd HH:mm"));
			PatientBasicInfo(json, id);
			String temp = DictionaryUtil.getPublicDictionaryText(
					"EMR-liver-ChiefComplaint", "narrator", d.getNarrator());
			if (temp.equals("其它") || temp.equals("其他")) {
				temp = d.getNarrator0();
			}
			json.put("narrator", temp);
//			if (d.getTime() != null) {
//				json.put("time", new SimpleDateFormat("yyyy年MM月dd日HH时")
//						.format(d.getTime()));
//			}
			json.put("reliability", temp = DictionaryUtil
					.getPublicDictionaryText("EMR-liver-ChiefComplaint",
							"reliability", d.getReliability()));
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			InHspCaseMaster master = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, d.getCaseId());
			Patient patient = (Patient) session.get(Patient.class, master
					.getPatientId());
			json.put("PatientName", patient.getPatientName());
			json.put("PatientNo", patient.getPatientNo());
			json.put("Gender", DictionaryUtil.getIndependentDictionaryText(
					"gender_gb", patient.getGender()));
			String people = DictionaryUtil.getIndependentDictionaryText(
					"people", patient.getPeople());
			if (people.equals("其它")) {
				people = patient.getPeople0();
			}
			json.put("People", people);
			String province = DictionaryUtil.getIndependentDictionaryText(
					"province", patient.getProvince());
			if (province.equals("其它")) {
				province = patient.getProvince0();
			}
			json.put("Province", province);
			String marrageStatus = DictionaryUtil.getIndependentDictionaryText(
					"marrageStatus", patient.getMarrageStatus());
			if (marrageStatus.equals("其它")) {
				marrageStatus = patient.getMarrageStatus0();
			}
			json.put("MarrageStatus", marrageStatus);
			String occupation = DictionaryUtil.getIndependentDictionaryText(
					"occupation", patient.getOccupation());
			if (occupation.equals("其它")) {
				occupation = patient.getOccupation0();
			}
			json.put("Occupation", occupation);
			json.put("Address", patient.getHomeAddr());
			tran.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	private void PatientBasicInfo(JSONObject map, Long key) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient patient = (Patient) session.get(Patient.class, key);
			if (patient != null) {
				map.put("PatientName", patient.getPatientName());
				map.put("PatientNo", patient.getPatientNo());
				map.put("Gender", DictionaryUtil.getIndependentDictionaryText(
						"gender_gb", patient.getGender()));
				String people = DictionaryUtil.getIndependentDictionaryText(
						"people", patient.getPeople());
				if (people.equals("其它")) {
					people = patient.getPeople0();
				}
				map.put("People", people);
				String province = DictionaryUtil.getIndependentDictionaryText(
						"province", patient.getProvince());
				if (province.equals("其它")) {
					province = patient.getProvince0();
				}
				map.put("Province", province);
				String marrageStatus = DictionaryUtil
						.getIndependentDictionaryText("marrageStatus", patient
								.getMarrageStatus());
				if (marrageStatus.equals("其它")) {
					marrageStatus = patient.getMarrageStatus0();
				}
				map.put("MarrageStatus", marrageStatus);
				String occupation = DictionaryUtil
						.getIndependentDictionaryText("occupation", patient
								.getOccupation());
				if (occupation.equals("其它")) {
					occupation = patient.getOccupation0();
				}
				map.put("Occupation", occupation);
				map.put("Address", patient.getHomeAddr());
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public InHspRec24 InHspRec24_saveOrUpdate(InHspRec24 d) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (d.getId() == null || d.getId() <= 0) {
				d.setSubmiter(null);
				d.setChecker(null);
				d.setVerifyStatus(0);
				d.setId((Long) session.save(d));
			} else {
				InHspRec24 os = this.InHspRec24_findById(d.getId());
				d.setSubmiter(os.getSubmiter());
				d.setChecker(os.getChecker());
				d.setVerifyStatus(os.getVerifyStatus());
				session.merge(d);
			}
			
			if(d.getOutHspDiag() != null && !d.getOutHspDiag().equals("")){
//				 24小时出院时更新病历主表记录
				InHspCaseMaster master = (InHspCaseMaster) session.get(
						InHspCaseMaster.class, d.getCaseId());
				master.setOutDate(d.getOuthspTime());
			}
			
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return d;
	}

	public String DailyCourseRec_NewpageNum_find(Long id) throws Exception {
		String rst = null;
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn
					.prepareStatement("select newPageNum from t_CourseRec_DailyCourseRec_NpCfg where dailyCourseRecId=?");
			sm.setLong(1, id);
			rs = sm.executeQuery();
			if (rs.next()) {
				rst = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return rst;
	}

	public void DailyCourseRec_NewpageNum_saveOrUpdate(Long id, String npCfg)
			throws Exception {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select newPageNum from t_CourseRec_DailyCourseRec_NpCfg where dailyCourseRecId=?";
			sm = conn.prepareStatement(sql);
			sm.setLong(1, id);
			rs = sm.executeQuery();
			if (rs.next()) {
				sql = "update t_CourseRec_DailyCourseRec_NpCfg set newPageNum=? where dailyCourseRecId=?";
			} else {
				sql = "insert into t_CourseRec_DailyCourseRec_NpCfg(newPageNum,dailyCourseRecId)values(?,?)";
			}
			sm = conn.prepareStatement(sql);
			sm.setString(1, npCfg);
			sm.setLong(2, id);
			sm.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
	}

	public JSONObject getChiefComByCaseId(Long caseId) throws Exception {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		JSONObject json = new JSONObject();
		try {
			conn = DatabaseUtil.getConnection();
			String sql = " select cc.ccContent,cm.inHspDate from  t_InHsp_Liver_ChiefComplaint cc, t_InHsp_CaseMaster cm where cc.caseid = cm.id and cm.id = ?";
			sm = conn.prepareStatement(sql);
			sm.setLong(1, caseId);
			rs = sm.executeQuery();
			if (rs.next()) {
				String cc = rs.getString("ccContent");
				if (cc.length() > 0) {
					cc = cc.substring(0, cc.length() - 1);
				}
				json.put("ccContent", cc);
				json.put("inHspDate", new SimpleDateFormat("yyyy年MM月dd日")
						.format(rs.getDate("inHspDate")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return json;
	}

	/**
	 * 查询入院时情况 主诉和体格检查
	 * 
	 * @param caseId
	 *            病历ID
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String findInHspStatuByCaseId(Long caseId) throws Exception {
		Session session = null;
		Transaction t = null;
		String cont = "";
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			List<DeathRecord> d = session.createCriteria(DeathRecord.class)
					.add(Restrictions.eq("caseId", caseId)).list();
			if (d.size() > 0) {

			} else {
				/* ========主诉======= */
				List list = session.createCriteria(ChiefComplaint.class).add(
						Restrictions.eq("caseId", caseId)).list();
				if (list.size() > 0) {
					ChiefComplaint cc = (ChiefComplaint) list.get(0);
					if (cc != null) {
						if (cc.getCcContent() != null
								&& cc.getCcContent().length() > 0) {
							cont = "患者主因"
									+ cc.getCcContent().replaceAll("。", "")
									+ "入院。";
						}
					}
				}
				/**
				 * 体格检查
				 */
				list = session.createCriteria(PhysicalExamination.class).add(
						Restrictions.eq("caseId", caseId)).list();
				String rst = "";
				if (list.size() > 0) {
					PhysicalExamination exam = (PhysicalExamination) list
							.get(0);
					if (exam != null) {
						String temp = exam.getSmtz_xueya();
						String temp2 = exam.getSmtz_xueya2();
						rst += "BP：" + temp + "/" + temp2 + "mmHg,";
						temp = DictionaryUtil.getPublicDictionaryText(
								"EMR-liver-PhysicalExamination",
								"ybzc_shenzhi", exam.getYbzc_shenzhi());
						if (temp.equals("其它")) {
							temp = exam.getYbzc_shenzhi0();
						}
						rst += (temp == null || temp.length() == 0) ? ""
								: "神志：" + temp + ",";
						temp = DictionaryUtil.getPublicDictionaryText(
								"EMR-liver-PhysicalExamination", "pfnm_seze",
								exam.getPfnm_seze());
						if (temp.equals("其它")) {
							temp = exam.getPfnm_seze0();
						}
						rst += (temp == null || temp.length() == 0) ? ""
								: "皮肤色泽：" + temp + ",";
						temp = DictionaryUtil.getPublicDictionaryText(
								"EMR-liver-PhysicalExamination", "eyes_gongmo",
								exam.getEyes_gongmo());
						rst += (temp == null || temp.length() == 0) ? ""
								: "巩膜：" + temp + ",";
						if (exam.getFei_huxiyin() == 1) {
							rst += "肺呼吸音：" + exam.getFei_huxiyinDesc() + ",";
						} else {
							rst += "肺呼吸音：正常,";
						}
						temp = exam.getXinz_xinlv();
						rst += (temp == null || temp.length() == 0) ? ""
								: "心率：" + temp + "次/分,";
						temp = DictionaryUtil.getPublicDictionaryText(
								"EMR-liver-PhysicalExamination",
								"xinz_xinrate", exam.getXinz_xinrate());
						rst += (temp == null || temp.length() == 0) ? ""
								: "心律：" + temp + ",";
						temp = DictionaryUtil.getPublicDictionaryText(
								"EMR-liver-PhysicalExamination", "fubu_fubi",
								exam.getFubu_fubi());
						if (temp.equals("其它")) {
							temp = exam.getFubu_fubi0();
						}
						rst += (temp == null || temp.length() == 0) ? ""
								: "腹壁：" + temp + ",";
						temp = DictionaryUtil.getPublicDictionaryText(
								"EMR-liver-PhysicalExamination", "fubu_jijzh",
								exam.getFubu_yatong());
						if (temp.equals("其它")) {
							temp = exam.getFubu_yatong0();
						}
						rst += (temp == null || temp.length() == 0) ? ""
								: "压痛：" + temp + ",";
						temp = DictionaryUtil.getPublicDictionaryText(
								"EMR-liver-PhysicalExamination", "fubu_jijzh",
								exam.getFubu_fantt());
						if (temp.equals("其它")) {
							temp = exam.getFubu_fantt0();
						}
						rst += (temp == null || temp.length() == 0) ? ""
								: "反跳痛：" + temp + ",";
						if (exam.getFubu_ganzang() == 1) {
							rst += "肝脏：" + exam.getFubu_ganzangDesc() + ",";
						} else {
							rst += "肝脏：未触及,";
						}
						if (exam.getFubu_pi() == 1) {
							rst += "脾脏：" + exam.getFubu_piDesc() + ",";
						} else {
							rst += "脾脏：未触及,";
						}
						if (exam.getFubu_ganqukt() == 1) {
							rst += "肝区叩痛：有,";
						} else {
							rst += "肝区叩痛：无,";
						}
						temp = DictionaryUtil.getPublicDictionaryText(
								"EMR-liver-PhysicalExamination", "fubu_murphy",
								exam.getFubu_yidong());
						rst += (temp == null || temp.length() == 0) ? ""
								: "移动性浊音：" + temp + ",";
						temp = DictionaryUtil.getPublicDictionaryText(
								"EMR-liver-PhysicalExamination", "jisi_xiazhi",
								exam.getJisi_xiazhi());
						if (temp.equals("其它")) {
							temp = exam.getJisi_xiazhi0();
						}
						rst += (temp == null || temp.length() == 0) ? ""
								: "下肢水肿：" + temp + ",";
						if (rst.length() > 0) {
							rst = "入院查体：" + rst.substring(0, rst.length() - 1)
									+ "。";
						}
					}
				}
				cont = cont + rst;
			}
		} catch (Exception e) {
			t.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return cont;
	}

	@SuppressWarnings("unchecked")
	public JSONObject DailyCourseRec_NewPrint(Long caseId, boolean isContinued,
			String continuedNum) throws Exception {
		JSONObject object = new JSONObject();
		StringBuffer sb = new StringBuffer();
		sb.append(createPrintCss());

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();

			InHspCaseMaster cm = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, caseId);
			object.put("times", cm.getInHspTimes());
			Patient p = (Patient) session.get(Patient.class, cm.getPatientId());
			object.put("name", p.getPatientName());
			object.put("no", p.getPatientNo());

			List<DailyCourseRec> records = this
					.DailyCourseRec_findAllByCaseID(caseId);
			int fromNum = 0;
			int toNum = records.size();
			if (isContinued) {
				if (continuedNum != null && continuedNum.length() > 0) {
					String[] temp = continuedNum.split("-");
					if (temp.length == 1) {
						try {
							fromNum = Integer.parseInt(temp[0])-1;
							toNum = fromNum;
						} catch (Exception e) {
						}
					} else if (temp.length == 2) {
						try {
							fromNum = Integer.parseInt(temp[0])-1;
							toNum = Integer.parseInt(temp[1])-1;
						} catch (Exception e) {
						}
					}
				}
			}
			for (int i = 0, len = records.size(); i < len; i++) {
				Printer print = new Printer();
				DailyCourseRec record = records.get(i);
				Date time = record.getTime();
				if (time != null) {
					print.time = df.format(time);
				} else {
					print.time = "&nbsp;";
				}
				String title = record.getTitle();
				if (title != null && !title.equals("")) {
					print.title = title;
				} else {
					print.title = "&nbsp;";
				}
				String content = record.getContent();
				if (content != null && !content.equals("")) {
					print.content = content.replaceAll(" ", "&nbsp;&nbsp;").replaceAll("\n", "<br/>");
				} else {
					print.content = "&nbsp;";
				}
				Long userId = record.getSubmiter();
				if (userId != null && userId > 0) {
					List list = session.createSQLQuery(
							"select name from t_User where id=?").setLong(0,
							userId).list();
					if (list.size() > 0) {
						print.user2 = (String) list.get(0);
					} else {
						print.user2 = "&nbsp;";
					}
				} else {
					print.user2 = "&nbsp;";
				}
				userId = record.getChecker();
				if (userId != null && userId > 0) {
					List list = session.createSQLQuery(
							"select name from t_User where id=?").setLong(0,
							userId).list();
					if (list.size() > 0) {
						print.user1 = (String) list.get(0);
					} else {
						print.user1 = "&nbsp;";
					}
				} else {
					print.user1 = "&nbsp;";
				}
				if (i >= fromNum && i <= toNum)
					sb.append(createPrintTable(print, false));
				else
					sb.append(createPrintTable(print, true));
			}
			object.put("html", sb.toString());
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return object;
	}

	private String createPrintCss() {
		StringBuffer sb = new StringBuffer();
		sb.append("<style type=\"text/css\">");
		sb
				.append("body{padding:0;margin:0;font-family:\"宋体\";font-size:11pt;}");
		sb.append("table.item{color:#000;font-size:16px;line-height:30px;}");
		sb.append("table.item2{color:#FFF;font-size:16px;line-height:30px;}");
		sb.append("</style>");
		return sb.toString();
	}

	private String createPrintTable(Printer p, boolean white) {
		return (p.title.equals("术后首次病程记录") ? "<div style=\"page-break-after:always;width:100%;height:0;line-height:0;font-size:0;overflow:hidden;\">&nbsp;</div>"
				: "")
				+ "<table width=\"100%\" border=0 cellpadding=0 cellspacing=0 class=\""
				+ (white ? "item2" : "item")
				+ "\">"
				+ "<tr>"
				+ "<td height=30 width=\"25%\">"
				+ p.time
				+ "</td>"
				+ // 时间
				"<td align=\"center\" width=\"*\">"
				+ p.title
				+ "</td>"
				+ // 题目
				"<td width=\"25%\">&nbsp;</td>"
				+ // 留空
				"</tr>"
				+ "<tr>"
				+ "<td colspan=3 height=\"*\">"
				+ p.content
				+ "</td>"
				+ // 内容
				"</tr>"
				+ "<tr>"
				+ "<td height=30>&nbsp;</td>"
				+ // 留空
				"<td align=\"right\">"
				+ p.user1
				+ "&nbsp;</td>"
				+ // 上级医师签字
				"<td>&nbsp;"
				+ p.user2
				+ "</td>"
				+ // 普通医师签字
				"</tr>"
				+ "<tr>"
				+ "<td colspan=3 height=30><hr style=\"border-top:1px dashed #"
				+ (white ? "FFF" : "000") + ";height:1px;width:100%;margin:0;padding:0;\"></td>" + // 分割线
				"</tr>" + "</table>";
	}
	public void wardRound_Delete(WardRoundRec record) throws Exception {
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

	public List<WardRoundRec> wardRound_findAllByID(Long id) throws Exception {
		List<WardRoundRec> list = new ArrayList<WardRoundRec>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(WardRoundRec.class).add(
					Restrictions.eq("patientId", id)).addOrder(Order.asc("time"))
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

	public WardRoundRec wardRound_saveOrUpdate(WardRoundRec record)
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

	public JSONArray wardRound_treeNodes(Long id) throws Exception {
		JSONArray array = new JSONArray();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<WardRoundRec> list = session.createCriteria(
					WardRoundRec.class).add(Restrictions.eq("patientId", id))
					.addOrder(Order.asc("time")).list();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			int index = 1;
			for (WardRoundRec record : list) {
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

	public WardRoundRec wardRound_findById(Long id) throws Exception {
		WardRoundRec record = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			record = (WardRoundRec) session.get(WardRoundRec.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return record;
	}
//	liugang update2011-05-08提高效率
	public List<DailyCourseRec> DailyCourseRec_saveOrUpdateRate(JSONArray array)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		List<DailyCourseRec> list = new ArrayList<DailyCourseRec>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date(Calendar.getInstance().getTimeInMillis());
			for (int i = 0, len = array.size(); i < len; i++) {
				JSONObject json = array.getJSONObject(i);
				DailyCourseRec rec = (DailyCourseRec) JSONObject.toBean(json,
						DailyCourseRec.class);
				if (rec.getId() == null || rec.getId() <= 0) {
					rec.setCreateTime(date);
					rec.setModifyTime(date);
					rec.setId((Long) session.save(rec));
					list.add(rec);
				} else {
					rec.setModifyTime(date);
					session.update(rec);
				}
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
	public void InHspDied24_delete(InHspDied24 d) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(d);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public InHspDied24 InHspDied24_findById(Long id) throws Exception {
		InHspDied24 d = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			d = (InHspDied24) session.get(InHspDied24.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return d;
	}

	public JSONObject InHspDied24_print(Long id) throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			InHspDied24 d = this.InHspDied24_findById(id);
			json = JSONObject.fromObject(d, JSONValueProcessor
					.formatDate("yyyy-MM-dd HH:mm"));
			PatientBasicInfo(json, id);
			String temp = DictionaryUtil.getPublicDictionaryText(
					"EMR-liver-ChiefComplaint", "narrator", d.getNarrator());
			if (temp.equals("其它") || temp.equals("其他")) {
				temp = d.getNarrator0();
			}
			json.put("narrator", temp);
//			if (d.getTime() != null) {
//				json.put("time", new SimpleDateFormat("yyyy年MM月dd日HH时")
//						.format(d.getTime()));
//			}
			json.put("reliability", temp = DictionaryUtil
					.getPublicDictionaryText("EMR-liver-ChiefComplaint",
							"reliability", d.getReliability()));
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			InHspCaseMaster master = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, d.getCaseId());
			json.put("inHspTimes", master.getInHspTimes());
//			json.put("age", master.getAge());
			Patient patient = (Patient) session.get(Patient.class, master
					.getPatientId());
			json.put("patientName", patient.getPatientName());
			json.put("patientNo", patient.getPatientNo());
			json.put("Gender", DictionaryUtil.getIndependentDictionaryText(
					"gender_gb", patient.getGender()));
			String people = DictionaryUtil.getIndependentDictionaryText(
					"people", patient.getPeople());
			if (people.equals("其它")) {
				people = patient.getPeople0();
			}
			json.put("People", people);
			String province = DictionaryUtil.getIndependentDictionaryText(
					"province", patient.getProvince());
			if (province.equals("其它")) {
				province = patient.getProvince0();
			}
			json.put("Province", province);
			String marrageStatus = DictionaryUtil.getIndependentDictionaryText(
					"marrageStatus", patient.getMarrageStatus());
			if (marrageStatus.equals("其它")) {
				marrageStatus = patient.getMarrageStatus0();
			}
			json.put("MarrageStatus", marrageStatus);
			String occupation = DictionaryUtil.getIndependentDictionaryText(
					"occupation", patient.getOccupation());
			if (occupation.equals("其它")) {
				occupation = patient.getOccupation0();
			}
			json.put("Occupation", occupation);
			json.put("Address", patient.getHomeAddr());
			tran.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public InHspDied24 InHspDied24_saveOrUpdate(InHspDied24 d) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (d.getId() == null || d.getId() <= 0) {
				d.setSubmiter(null);
				d.setChecker(null);
				d.setVerifyStatus(0);
				d.setId((Long) session.save(d));
			} else {
				InHspDied24 os = this.InHspDied24_findById(d.getId());
				d.setSubmiter(os.getSubmiter());
				d.setChecker(os.getChecker());
				d.setVerifyStatus(os.getVerifyStatus());
				session.merge(d);
			}
			
			//if(d.getOutHspDiag() != null && !d.getOutHspDiag().equals("")){
//				 24小时出院时更新病历主表记录
			//	InHspCaseMaster master = (InHspCaseMaster) session.get(
			//			InHspCaseMaster.class, d.getCaseId());
			//	master.setOutDate(d.getOuthspTime());
			//}
			
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return d;
	}
	
	//liugang 2011-08-31 start 质控一级医生必须签字
	public boolean checkSubmitCourseRecording(Long kid, Long patientId) {
		Session session = null;
		Transaction tran = null;
		boolean result = false;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql ="from DailyCourseRec where caseId ="+kid+" and patientId ="+patientId;
			List<DailyCourseRec> list = session.createQuery(hql).list(); 
			if(list != null && list.size() > 0){
				for(int i=0,size = list.size();i<size;i++){
					DailyCourseRec daily = list.get(i);
					if(daily != null){
						if(daily.getSubmiter() == null){
							result = true;
						}
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return result;
	}
}

class Printer {
	public String time;

	public String title;

	public String content;

	public String user1;

	public String user2;
}
