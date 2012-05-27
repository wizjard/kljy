package com.juncsoft.KLJY.biomedical.member.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.biomedical.entity.OutpatientGenerator;
import com.juncsoft.KLJY.biomedical.member.dao.MemberDao;
import com.juncsoft.KLJY.biomedical.member.entity.HealthRecord;
import com.juncsoft.KLJY.biomedical.member.entity.MemberMsg;
import com.juncsoft.KLJY.biomedical.member.entity.MemberUploadFile;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.MD5;

public class MemberDaoImpl implements MemberDao {

	public MemberInfo login(String account, String password) throws Exception {
		MemberInfo mem = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			mem = (MemberInfo) session.createCriteria(MemberInfo.class).add(
					Restrictions.and(Restrictions.eq("account", account),
							Restrictions.eq("password", password)))
					.setMaxResults(1).uniqueResult();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mem;
	}
	
	//找回用户名和密码
	public MemberInfo findUserNameAndPwd(String idNo, String patientName ) throws Exception {
		MemberInfo mem = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "select m from MemberInfo m where m.patient.idNo=? and m.patient.patientName=?";
			//也可用sql语句执行  在后面用createSqlQuery()方法   String hql = "select * from MemberInfo m inner join t_Patient p on p.id = m.patient  where p.idNo='"+idNo+"' and p.birthday='"+"'";
			List<MemberInfo> list = session.createQuery(hql).setString(0, idNo).setString(1, patientName).list();
			if(list.size()>0){
				mem = (MemberInfo)list.get(0);
			}/*else if(list==null||list.size()==0){
				return null;
			}*/
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mem;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<InHspCaseMaster> getInHspCase(Patient pat) throws Exception {
		List<InHspCaseMaster> list = new ArrayList<InHspCaseMaster>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(InHspCaseMaster.class).add(
					Restrictions.eq("patientId", pat.getId())).add(
					Restrictions.eq("flag", 0)).addOrder(
					Order.asc("inHspTimes")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<HealthRecord> getHealthRecords(int start, int limit,
			Criterion... criterions) throws Exception {
		List<HealthRecord> list = new ArrayList<HealthRecord>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Criteria cs = session.createCriteria(HealthRecord.class);
			for (Criterion c : criterions) {
				cs.add(c);
			}
			list = cs.addOrder(Order.desc("registDate")).setFirstResult(start)
					.setMaxResults(limit).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public void deleteHealthRecord(HealthRecord record) throws Exception {
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

	public HealthRecord getHealthRecord(Long id) throws Exception {
		HealthRecord hr = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			hr = (HealthRecord) session.get(HealthRecord.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return hr;
	}

	public Long saveHealthRecord(HealthRecord record) throws Exception {
		Long id = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			id = (Long) session.save(record);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}

	public void updateHealthRecord(HealthRecord record) throws Exception {
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

	public long getHealthRecordTotal(Criterion... criterions) throws Exception {
		long total = 0;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Criteria cs = session.createCriteria(HealthRecord.class);
			for (Criterion c : criterions) {
				cs.add(c);
			}
			total = (Long) cs.setProjection(Projections.rowCount())
					.uniqueResult();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return total;
	}

	public MemberMsg getMemberMsg(Long id) throws Exception {
		MemberMsg hr = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			hr = (MemberMsg) session.get(MemberMsg.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return hr;
	}

	@SuppressWarnings("unchecked")
	public List<MemberMsg> getMemberMsgs(int start, int limit,
			Criterion... criterions) throws Exception {
		List<MemberMsg> list = new ArrayList<MemberMsg>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Criteria cs = session.createCriteria(MemberMsg.class);
			for (Criterion c : criterions) {
				cs.add(c);
			}
			list = cs.addOrder(Order.desc("askTime")).setFirstResult(start)
					.setMaxResults(limit).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public long getMemberMsgsTotal(Criterion... criterions) throws Exception {
		long total = 0;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Criteria cs = session.createCriteria(MemberMsg.class);
			for (Criterion c : criterions) {
				cs.add(c);
			}
			total = (Long) cs.setProjection(Projections.rowCount())
					.uniqueResult();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return total;
	}

	public Long saveOrUpdateMemberMsg(MemberMsg msg) throws Exception {
		Long id = msg.getId();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (msg.getId() != null && msg.getId() > 0) {
				session.update(msg);
			} else {
				msg.setAskTime(new Date());
				id = (Long) session.save(msg);
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

	@SuppressWarnings("unchecked")
	public List<MemberUploadFile> getMemberUploadFiles(int start, int limit,
			Criterion... criterions) throws Exception {
		List<MemberUploadFile> list = new ArrayList<MemberUploadFile>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Criteria cs = session.createCriteria(MemberUploadFile.class);
			for (Criterion c : criterions) {
				cs.add(c);
			}
			list = cs.addOrder(Order.desc("uploadTime")).setFirstResult(start)
					.setMaxResults(limit).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public long getMemberUploadFilesTotal(Criterion... criterions)
			throws Exception {
		long total = 0;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Criteria cs = session.createCriteria(MemberUploadFile.class);
			for (Criterion c : criterions) {
				cs.add(c);
			}
			total = (Long) cs.setProjection(Projections.rowCount())
					.uniqueResult();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return total;
	}

	public void deleteMemberUploadFile(MemberUploadFile file) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(file);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public Long saveMemberUploadFile(MemberUploadFile file) throws Exception {
		Long id = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			file.setUploadTime(new Date());
			id = (Long) session.save(file);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}

	public MemberUploadFile getMemberUploadFile(Long id) throws Exception {
		MemberUploadFile file = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			file = (MemberUploadFile) session.get(MemberUploadFile.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return file;
	}

	public void updateMemberUploadFile(MemberUploadFile file) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			file.setUploadTime(new Date());
			session.update(file);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject getMyNotices(MemberInfo mem, Integer start, Integer limit)
			throws Exception {
		JSONObject object = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Criteria c = session.createCriteria(OutpatientGenerator.class);
			c.add(Restrictions.eq("patient", mem.getPatient()));
			c.add(Restrictions.isNotNull("noticeDate"));
			List<OutpatientGenerator> genes = c.addOrder(
					Order.desc("noticeDate")).list();
			object.put("root", JSONArray.fromObject(genes, JSONValueProcessor
					.cycleExcludel(new String[] { "patient" }, "yyyy-MM-dd")));
			c = session.createCriteria(OutpatientGenerator.class);
			c.add(Restrictions.eq("patient", mem.getPatient()));
			c.add(Restrictions.isNotNull("noticeDate"));
			object.put("total", (Long) c.setProjection(Projections.rowCount())
					.uniqueResult());
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return object;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getMemberMsgs2(int start, int limit, String ward, int flag)
			throws Exception {
		JSONObject object = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<MemberMsg> msgs = null;
			long total = 0;
			if (flag == 1) {
				msgs = session.createCriteria(MemberMsg.class).add(
						(Restrictions.eq("ward", ward))).add(
						Restrictions.isNull("answerDate")).addOrder(
						Order.desc("askTime")).list();
				total = (Long) session.createCriteria(MemberMsg.class).add(
						(Restrictions.eq("ward", ward))).add(
						Restrictions.isNull("answerDate")).setProjection(
						Projections.rowCount()).uniqueResult();
			} else if (flag == 2) {
				msgs = session.createCriteria(MemberMsg.class).add(
						(Restrictions.eq("ward", ward))).add(
						Restrictions.isNotNull("answerDate")).addOrder(
						Order.desc("askTime")).list();
				total = (Long) session.createCriteria(MemberMsg.class).add(
						(Restrictions.eq("ward", ward))).add(
						Restrictions.isNotNull("answerDate")).setProjection(
						Projections.rowCount()).uniqueResult();
			} else {
				msgs = session.createCriteria(MemberMsg.class).add(
						(Restrictions.eq("ward", ward))).addOrder(
						Order.desc("askTime")).list();
				total = (Long) session.createCriteria(MemberMsg.class).add(
						(Restrictions.eq("ward", ward))).setProjection(
						Projections.rowCount()).uniqueResult();
			}
			object.put("total", total);
			object.put("root", JSONArray.fromObject(msgs, JSONValueProcessor
					.cycleExcludel(new String[] {}, "yyyy-MM-dd")));
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return object;
	}

	public List<MemberUploadFile> getMemberUploadFileByMember(MemberInfo mem,
			Integer s, Integer l) throws Exception {
		List<MemberUploadFile> list = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(MemberUploadFile.class).add(
					Restrictions.eq("mem", mem)).addOrder(
					Order.desc("uploadTime")).setFirstResult(s)
					.setMaxResults(l).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public long getMemberUploadFileByMemberTotal(MemberInfo mem) throws Exception {
		long total = 0;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			total = (Long) session.createCriteria(MemberUploadFile.class).add(
					Restrictions.eq("mem", mem)).setProjection(
					Projections.rowCount()).uniqueResult();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return total;
	}
	
	/**
	 * ��ȡ��Ա��Ϣ
	 */
	public JSONObject getLoginMemberInfo(HttpServletRequest reqeust)
			{
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			MemberInfo mem = (MemberInfo) reqeust.getSession().getAttribute(
					"MemberInfo");
			json.put("name", mem.getPatient().getPatientName());
			json.put("memberNo", mem.getMemberNo());
			//��Ա�鿴����ʱʹ��
			json.put("patientId", mem.getPatient().getId());
			json.put("account", mem.getAccount());
			json.put("deptCode", mem.getDeptCode());
			String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
				+ mem.getDeptCode() + "'";
			List deptList = session.createSQLQuery(hqlCode)
				.list(); 
			json.put("deptName", deptList.get(0));
			json.put("grounpId", mem.getGrounpId());
			String sql = "select tp.grounpName from t_DepartmentAndGrounpMiddle td inner join t_DepartmentGrounp tp on td.grounpId = tp.id and td.deptCode = '"
				+ mem.getDeptCode()
				+ "' and tp.id=" + mem.getGrounpId();
			List listM = session.createSQLQuery(sql).list();
			json.put("grounpName", listM.get(0));
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			throw new RuntimeException("��ȡ��Ա��Ϣ���",e);
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public boolean validOldPassword(String memberNo, String password)
			throws Exception {
		boolean rst = false;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql =" from MemberInfo where memberno='"+memberNo+"' and password='"+password.toLowerCase()+"'";
			List list = session.createQuery(hql).list();
			if(list != null && list.size() >0){
				rst = true;
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}

	public void changePassword(String memberNo, String password)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql ="from MemberInfo where memberNo='"+memberNo+"'";
			List list = session.createQuery(hql).list();
			if(list != null && list.size() > 0){
				MemberInfo mem = (MemberInfo)list.get(0);
				mem.setPassword(password.toLowerCase());
				session.update(mem);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<InHspCaseMaster> getInHspCasePid(Long pid) throws Exception {
		List<InHspCaseMaster> list = new ArrayList<InHspCaseMaster>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(InHspCaseMaster.class).add(
					Restrictions.eq("patientId", pid)).add(
					Restrictions.eq("flag", 0)).addOrder(
					Order.asc("inHspTimes")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
	
	//找回用户名和密码
//	public MemberInfo findUserNameAndPwd(String idNo, String patientName ) throws Exception {
//		MemberInfo mem = null;
//		Session session = null;
//		Transaction tran = null;
//		try {
//			session = DatabaseUtil.getSession();
//			tran = session.beginTransaction();
//			String hql = "select m from MemberInfo m where m.patient.idNo=? and m.patient.patientName=?";
//			//也可用sql语句执行  在后面用createSqlQuery()方法   String hql = "select * from MemberInfo m inner join t_Patient p on p.id = m.patient  where p.idNo='"+idNo+"' and p.birthday='"+"'";
//			List<MemberInfo> list = session.createQuery(hql).setString(0, idNo).setString(1, patientName).list();
//			if(list.size()>0){
//				mem = (MemberInfo)list.get(0);
//			}/*else if(list==null||list.size()==0){
//				return null;
//			}*/
//			tran.commit();
//		} catch (Exception e) {
//			tran.rollback();
//			throw e;
//		} finally {
//			DatabaseUtil.closeResource(session);
//		}
//		return mem;
//	}
	
}
