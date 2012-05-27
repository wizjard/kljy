package com.juncsoft.KLJY.patient.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.impl.InHspCaseMasterDaoImpl;
import com.juncsoft.KLJY.InHospitalCase.Liver.impl.LiverCaseDaoImpl;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.biomedical.impl.MemberInfoImpl;
import com.juncsoft.KLJY.message.entity.Message;
import com.juncsoft.KLJY.patient.dao.PatientDao;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.patient.entity.SysZdUserBelong;
import com.juncsoft.KLJY.plan.entity.CheckItem;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class PatientDaoImpl implements PatientDao {

	private MemberInfoImpl memInfoImpl = new MemberInfoImpl();
	private LiverCaseDaoImpl liverCaseDaoImpl = new LiverCaseDaoImpl();

	public Patient saveOrUpdate(Patient patient) throws Exception {
		Long id = patient.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date();
			patient.setModifyDate(date);
			if (id == null || id <= 0) {
				patient.setCreateDate(date);
				id = (Long) session.save(patient);
				patient.setId(id);
			} else {
				patient.setModifyDate(date);
				session.update(patient);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return patient;
	}

	public Patient findById(Long id) throws Exception {
		Patient patient = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			patient = (Patient) session.get(Patient.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return patient;
	}
	
	
	
	public Patient findByPatientId(String patientId) throws Exception {
		Patient patient = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from Patient where patientId = '" + patientId + "'";
			patient = (Patient) session.createQuery(hql).uniqueResult();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return patient;
	}
	
	/*
	 * 
	 * 由会员编号查到会员实体
	 */
	public MemberInfo findMemberInfoByMemNo(String memberNo) throws Exception {
		MemberInfo memberInfo = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			//String hql = "from MemberInfo where memberNo = '" + memberNo + "'";
			//memberInfo = (MemberInfo) session.createQuery(hql).uniqueResult();
			memberInfo = (MemberInfo) session.get(MemberInfo.class, memberNo);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return memberInfo;
	}
	
	
	/*
	 * 
	 * 由patienId查到会员编号 memberNo
	 */
	public List findMemberNoPatientId(String patientId,Session session) throws Exception {
		MemberInfo memberInfo = null;
		List memList = null;
		try {
			String memberSql = "select memberNo from MemberInfo where patient="+patientId;							
			memList = session.createSQLQuery(memberSql).list();
		} catch (Exception e) {
			throw e;
		}
		return memList;
	}
	
	
	/**
	 * 循环多次由会员编号查会员实体
	 */
	public MemberInfo findMemberInfoByMemNo(String memberNo, Session session)
			throws Exception {
		MemberInfo memberInfo = null;
		try {
			memberInfo = (MemberInfo) session.get(MemberInfo.class, memberNo);
		} catch (Exception e) {
			throw e;
		} 
		return memberInfo;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public JSONObject queryAll(Integer start, Integer limit) throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<Patient> patients = session.createCriteria(Patient.class)
					.addOrder(Order.desc("modifyDate")).setFirstResult(start)
					.setMaxResults(limit).list();
			JSONArray root = new JSONArray();
			for (Patient pat : patients) {
				root.add(initPatientValue(pat));
			}
			json.put("root", root);
			long total = (Long) session.createQuery(
					"select count(*) from Patient").list().iterator().next();
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

	private JSONObject initPatientValue(Patient pat) throws Exception {
		JSONObject json = new JSONObject();
		try {
			json = JSONObject.fromObject(pat, JSONValueProcessor
					.formatDate("yyyy-MM-dd"));
			json.put("sex0", DictionaryUtil.getIndependentDictionaryText(
					"gender_gb", pat.getGender()));
			String temp = DictionaryUtil.getIndependentDictionaryText(
					"province", pat.getProvince());
			if (!(temp.equals("其它") || temp.equals("其他"))) {
				json.put("province0", temp);
			}
			temp = DictionaryUtil.getIndependentDictionaryText("occupation",
					pat.getOccupation());
			if (!(temp.equals("其它") || temp.equals("其他"))) {
				json.put("occupation0", temp);
			}
			temp = DictionaryUtil.getIndependentDictionaryText("marrageStatus",
					pat.getMarrageStatus());
			if (!(temp.equals("其它") || temp.equals("其他"))) {
				json.put("marrageStatus0", temp);
			}
			json.put("currentWardCode0", DictionaryUtil
					.getIndependentDictionaryText("belong", pat
							.getCurrentWardCode())
					+ "("
					+ DictionaryUtil.getIndependentDictionaryText(
							"parent_belong", pat.getCurrentWardCode()) + ")");
		} catch (Exception e) {
			throw e;
		}
		return json;
	}

	@SuppressWarnings("unchecked")
	public JSONObject searchByNameOrNo(String keyword, Integer start,
			Integer limit) throws Exception {
		JSONObject json = new JSONObject();
		keyword = "%" + keyword + "%";
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<Patient> list = session.createCriteria(Patient.class).add(
					Restrictions.or(Restrictions.like("patientName", keyword),
							Restrictions.like("patientNo", keyword))).addOrder(
					Order.desc("modifyDate")).setFirstResult(start)
					.setMaxResults(limit).list();
			long total = (Long) session
					.createQuery(
							"select count(*) from Patient where patientName like ? or patientNo like ?")
					.setString(0, keyword).setString(1, keyword).list()
					.iterator().next();
			JSONArray root = new JSONArray();
			for (Patient pat : list) {
				root.add(initPatientValue(pat));
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
			System.out.println(JSONObject.fromObject(
					new PatientDaoImpl().searchByNameOrNo("Test", 0, 20))
					.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject queryByBelong(String belong, Integer start, Integer limit)
			throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<Patient> list = session.createCriteria(Patient.class).add(
					Restrictions.eq("currentWardCode", belong)).addOrder(
					Order.desc("modifyDate")).setFirstResult(start)
					.setMaxResults(limit).list();
			JSONArray root = new JSONArray();
			for (Patient pat : list) {
				root.add(this.initPatientValue(pat));
			}
			json.put("root", root);
			long total = (Long) session.createQuery(
					"select count(*) from Patient where currentWardCode=?")
					.setString(0, belong).list().iterator().next();
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

	public JSONObject searchByCondition(String keyword, String condition,
			Integer start, Integer limit) throws Exception {
		JSONObject json = new JSONObject();
		keyword = "%" + keyword + "%";
		Session session = null;
		Transaction tran = null;
		List<Patient> list = new ArrayList<Patient>();
		long total = 0;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql1 = null;
			String hql2 = null;
			if (condition.equals("1") || condition.equals("2")) {
				if (condition.equals("1")) {
					hql1 = "from Patient where patientName like ?";
					hql2 = "select count(*) from Patient where patientName like ? ";
				}
				if (condition.equals("2")) {
					hql1 = "from Patient where patientNo like ?";
					hql2 = "select count(*) from Patient where patientNo like ?";
				}
				list = session.createQuery(hql1).setString(0, keyword)
						.setFirstResult(start).setMaxResults(limit).list();
				total = (Long) session.createQuery(hql2).setString(0, keyword)
						.list().iterator().next();
			}
			if (condition.equals("3")) {
				List<SysZdUserBelong> _list = session.createQuery(
						"from SysZdUserBelong where name like ?").setString(0,
						keyword).list();
				if (!_list.isEmpty()) {
					StringBuffer sb1 = new StringBuffer();
					StringBuffer sb2 = new StringBuffer();
					sb1.append("from Patient where currentWardCode in (");
					sb2
							.append("select count(*) from Patient where currentWardCode in (");
					for (SysZdUserBelong szub : _list) {
						sb1.append("'").append(szub.getCode()).append("',");
						sb2.append("'").append(szub.getCode()).append("',");
					}
					String str1 = sb1.toString();
					hql1 = str1.substring(0, str1.length() - 1) + ")";

					String str2 = sb2.toString();
					hql2 = str2.substring(0, str2.length() - 1) + ")";
					list = session.createQuery(hql1).setFirstResult(start)
							.setMaxResults(limit).list();
					total = (Long) session.createQuery(hql2).list().iterator()
							.next();
				}
			}

			if (condition.equals("4")) {
				List<InHspCaseMaster> _list = session.createQuery(
						"from InHspCaseMaster where outDate is NULL")
						.setFirstResult(start).setMaxResults(limit).list();
				for (InHspCaseMaster inHspCaseMaster : _list) {
					Patient patient = new Patient();
					long patientId = inHspCaseMaster.getPatientId();
					patient = (Patient) session.get(Patient.class, patientId);
					list.add(patient);
				}
				total = (Long) session
						.createQuery(
								"select count(*) from InHspCaseMaster where outDate is NULL")
						.list().iterator().next();
			}
			if (condition.equals("5")) {
				List<InHspCaseMaster> _list = session.createQuery(
						"from InHspCaseMaster where outDate is not NULL")
						.setFirstResult(start).setMaxResults(limit).list();
				for (InHspCaseMaster inHspCaseMaster : _list) {
					Patient patient = new Patient();
					long patientId = inHspCaseMaster.getPatientId();
					patient = (Patient) session.get(Patient.class, patientId);
					list.add(patient);
				}
				total = (Long) session
						.createQuery(
								"select count(*) from InHspCaseMaster where outDate is not NULL")
						.list().iterator().next();
			}
			if (condition.equals("6")) {
				hql1 = "from Patient where patientName like ? or patientNo like ?";
				hql2 = "select count(*) from Patient where patientName like ? or patientNo like ?";
				list = session.createQuery(hql1).setString(0, keyword)
						.setString(1, keyword).setFirstResult(start)
						.setMaxResults(limit).list();
				total = (Long) session.createQuery(hql2).setString(0, keyword)
						.setString(1, keyword).list().iterator().next();
			}
			if (condition.equals("7")) {
				List<SysZdUserBelong> _list = session.createQuery(
						"from SysZdUserBelong where name like ?").setString(0,
						keyword).list();
				if (!_list.isEmpty()) {
					StringBuffer sb1 = new StringBuffer();
					StringBuffer sb2 = new StringBuffer();
					sb1
							.append("select p from Patient p, InHspCaseMaster ihcm where p.currentWardCode in (");
					sb2
							.append("select count(*) from Patient p, InHspCaseMaster ihcm where p.currentWardCode in (");
					for (SysZdUserBelong szub : _list) {
						sb1.append("'").append(szub.getCode()).append("',");
						sb2.append("'").append(szub.getCode()).append("',");
					}
					String str1 = sb1.toString();
					hql1 = str1.substring(0, str1.length() - 1)
							+ ")"
							+ "and ihcm.outDate is NULL and p.id = ihcm.patientId";

					String str2 = sb2.toString();
					hql2 = str2.substring(0, str2.length() - 1)
							+ ")"
							+ "and ihcm.outDate is NULL and p.id = ihcm.patientId";
					list = session.createQuery(hql1).setFirstResult(start)
							.setMaxResults(limit).list();
					total = (Long) session.createQuery(hql2).list().iterator()
							.next();
				}
			}
			if (condition.equals("8")) {
				List<SysZdUserBelong> _list = session.createQuery(
						"from SysZdUserBelong where name like ?").setString(0,
						keyword).list();
				if (!_list.isEmpty()) {
					StringBuffer sb1 = new StringBuffer();
					StringBuffer sb2 = new StringBuffer();
					sb1
							.append("select p from Patient p, InHspCaseMaster ihcm where p.currentWardCode in (");
					sb2
							.append("select count(*) from Patient p, InHspCaseMaster ihcm where p.currentWardCode in (");
					for (SysZdUserBelong szub : _list) {
						sb1.append("'").append(szub.getCode()).append("',");
						sb2.append("'").append(szub.getCode()).append("',");
					}
					String str1 = sb1.toString();
					hql1 = str1.substring(0, str1.length() - 1)
							+ ")"
							+ "and ihcm.outDate is not NULL and p.id = ihcm.patientId";

					String str2 = sb2.toString();
					hql2 = str2.substring(0, str2.length() - 1)
							+ ")"
							+ "and ihcm.outDate is not NULL and p.id = ihcm.patientId";
					list = session.createQuery(hql1).setFirstResult(start)
							.setMaxResults(limit).list();
					total = (Long) session.createQuery(hql2).list().iterator()
							.next();
				}
			}

			JSONArray root = new JSONArray();
			for (Patient pat : list) {
				root.add(initPatientValue(pat));
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

	/**
	 * 会员入会保存会员信息，同时修改病人基本信息
	 */
	public Patient saveOrUpdatePM(Patient patient, MemberInfo member)
			throws Exception {
		Long id = patient.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date();
			patient.setModifyDate(date);
			if (id == null || id <= 0) {
				patient.setCreateDate(date);
				id = (Long) session.save(patient);
				patient.setId(id);
			} else {
				patient.setModifyDate(date);
				session.update(patient);
			}
			if (!("".equals(member.getMemberNo()))) {
				session.update(member);
			} else {
				member.setMemberNo(memInfoImpl.generatorMemberNo(session));
				session.save(member);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return patient;
	}

	/**
	 * 获取病人和会员的基本信息综合
	 */
	public Map findPatientAndMemberInfo(Long id) {
		Patient patient = null;
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from MemberInfo where patient=" + id;
			List list = session.createQuery(hql).list();
			if (list != null && list.size() > 0) {
				MemberInfo member = (MemberInfo) list.get(0);
				mp.put("memberNo", member.getMemberNo());
				mp.put("account", member.getAccount());
				mp.put("password", member.getPassword());
				mp.put("inDate", member.getInDate());
				mp.put("inWard", member.getInWard());
				mp.put("memberStatus", member.getMemberStatus());
				mp.put("memberType", member.getMemberType());
				mp.put("memo", member.getMemo());
				mp.put("inDoctor", member.getInDoctor());
				mp.put("biaoben", member.getBiaoben());
				mp.put("inHspTimes", member.getInHspTimes());
				mp.put("deptCode", member.getDeptCode());
				mp.put("grounpId", member.getGrounpId());
			}
			patient = (Patient) session.get(Patient.class, id);
			mp.put("patient", patient);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw new RuntimeException("获取病人和会员的基本信息综合出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	public JSONObject queryByBelongKey(String key, String belong,
			Integer start, Integer limit) throws Exception {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from Patient where currentWardCode = '" + belong
					+ "' and (patientName like '%" + key
					+ "%' or patientNo like '%" + key
					+ "%') order by patientName desc";
			List<Patient> list = session.createQuery(hql).setFirstResult(start)
					.setMaxResults(limit).list();
			JSONArray root = new JSONArray();
			for (Patient pat : list) {
				root.add(this.initPatientValue(pat));
			}
			json.put("root", root);
			long total = (Long) session
					.createQuery(
							"select count(*) from Patient where currentWardCode=? and (patientName like ? or patientNo like ?)")
					.setString(0, belong).setString(1, key).setString(2, key)
					.list().iterator().next();
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

	public Patient saveOrUpdatePMCopy(Patient patient, MemberInfo member,
			Long caseId) {
		Long id = patient.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date();
			patient.setModifyDate(date);
			if (id == null || id <= 0) {
				patient.setCreateDate(date);
				id = (Long) session.save(patient);
				patient.setId(id);
			} else {
				patient.setModifyDate(date);
				session.update(patient);
			}
			if (!("".equals(member.getMemberNo()))) {
				session.update(member);
			} else {
				member.setMemberNo(memInfoImpl.generatorMemberNo(session));
				session.save(member);
			}
			liverCaseDaoImpl.memberCaseCopyLiverCaseByCaseId(caseId,session);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return patient;
	}
}
