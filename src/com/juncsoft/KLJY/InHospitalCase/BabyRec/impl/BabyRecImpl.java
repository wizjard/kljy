package com.juncsoft.KLJY.InHospitalCase.BabyRec.impl;

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

import com.juncsoft.KLJY.InHospitalCase.BabyRec.dao.BabyRecDao;
import com.juncsoft.KLJY.InHospitalCase.BabyRec.entity.BabyCourseRec;
import com.juncsoft.KLJY.InHospitalCase.BabyRec.entity.BabyRec;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.Consultation;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DailyCourseRec;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DeathCaseDiscuss;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DeathRecord;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.InHspRec24;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.PreviousCaseDiscuss;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.PreviousSurgerySummary;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.SurgeryRecord;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabExamination;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class BabyRecImpl implements BabyRecDao {

	public List<BabyRec> getBabyInfo(int pid, int pcid) {
		Session session = null;
		Transaction tran = null;
		List<BabyRec> list = new ArrayList<BabyRec>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from BabyRec where pid = ? and pcid = ?").setInteger(0, pid).setInteger(1, pcid).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public void saveBabyInfo(BabyRec babyRec, int hasSave) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();			
			if(babyRec.getId() > 0 || hasSave > 0){
				session.createQuery("update BabyRec set name = ?, gender = ?, birthDate = ?, birthWeight = ? where id = ? ")
					.setString(0, babyRec.getName()).setInteger(1, babyRec.getGender()).setDate(2, babyRec.getBirthDate())
					.setString(3, babyRec.getBirthWeight()).setInteger(4, babyRec.getId()).executeUpdate();
			}else{
				session.save(babyRec);
			}	
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public long getBabyCount(int pid, int pcid) {
		Session session = null;
		Transaction tran = null;
		long count = 0;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			count = (Long)session.createQuery("select count(*) from BabyRec where pid = ? and pcid = ?").setInteger(0, pid).setInteger(1, pcid).list().iterator().next();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return count;
	}

	public void DailyCourseRec_delete(BabyCourseRec record) throws Exception {
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
	public List<BabyCourseRec> DailyCourseRec_findAllByCaseID(Long id)
			throws Exception {
		List<BabyCourseRec> list = new ArrayList<BabyCourseRec>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(BabyCourseRec.class).add(
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

	public BabyCourseRec DailyCourseRec_findById(Long id) throws Exception {
		BabyCourseRec record = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			record = (BabyCourseRec) session.get(BabyCourseRec.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return record;
	}

	public BabyCourseRec DailyCourseRec_saveOrUpdate(BabyCourseRec record)
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
			List<BabyCourseRec> list = session.createCriteria(
					BabyCourseRec.class).add(Restrictions.eq("caseId", id))
					.addOrder(Order.asc("time")).list();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			int index = 1;
			for (BabyCourseRec record : list) {
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
			sm = conn.prepareStatement("select LabExaminationID from t_CourseRec_DailyCourseRec_LabExam where DailyCourseRecID=?");
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
			sm=conn.prepareStatement(sql);
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
	
	//liugang 2011-08-31 start 质控一级医生必须签字
//	public boolean checkSubmitCourseRecording(Long kid, Long patientId) {
//		Session session = null;
//		Transaction tran = null;
//		boolean result = false;
//		try {
//			session = DatabaseUtil.getSession();
//			tran = session.beginTransaction();
//			String hql ="from BabyRec where caseId ="+kid+" and patientId ="+patientId;
//			List<DailyCourseRec> list = session.createQuery(hql).list(); 
//			if(list != null && list.size() > 0){
//				for(int i=0,size = list.size();i<size;i++){
//					DailyCourseRec daily = list.get(i);
//					if(daily != null){
//						if(daily.getSubmiter() == null){
//							result = true;
//						}
//					}
//				}
//			}
//			tran.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			DatabaseUtil.closeResource(session);
//		}
//		return result;
//	}
}
