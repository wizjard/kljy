package com.juncsoft.KLJY.checkreport.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.UpdateReportFormForRemoteRecord;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.checkreport.dao.CheckReportDao;
import com.juncsoft.KLJY.checkreport.entity.Maidi;
import com.juncsoft.KLJY.checkreport.entity.Pacs;
import com.juncsoft.KLJY.message.dao.MessageDao;
import com.juncsoft.KLJY.message.entity.Message;
import com.juncsoft.KLJY.message.impl.MessageDaoImpl;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.stongnet.sms.http.Sms;
import com.stongnet.sms.http.SmsOperator;

public class CheckReportDaoImpl implements CheckReportDao {

	private MessageDao messDao = new MessageDaoImpl();
	public Long addPacsReport(Pacs report) throws Exception {
		Session session = null;
		Transaction tran = null;
		Long id = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			id = (Long) session.save(report);
			report.setId(id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}

	public void deletePacsReport(Pacs report) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(report);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}

	}

	public Pacs getPacsReport(Long pacsId) throws Exception {
		Session session = null;
		Transaction tran = null;
		Pacs report = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			report = (Pacs) session.get(Pacs.class, pacsId);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return report;
	}

	public Maidi getMaidiReport(Long pacsId) throws Exception {
		Session session = null;
		Transaction tran = null;
		Maidi report = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			report = (Maidi) session.get(Maidi.class, pacsId);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return report;
	}

	public List<Pacs> getPacsReportsByPatient(String patientId)
			throws Exception {
		List<Pacs> reports = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			reports = session.createQuery(
					"from Pacs where patientId = '" + patientId + "' and (isPatientOrDoctorWriteZanCun is null or guiDangDate is not NULL)").list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return reports;
	}

	public List<Maidi> getMaidiReportsByPatient(String patientId)
			throws Exception {
		List<Maidi> reports = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			reports = session.createQuery(
					"from Maidi where patientId = '" + patientId + "' and (isPatientOrDoctorWriteZanCun is null or guiDangDate is not NULL)").list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return reports;
	}

	/*
	 * 得到前30天或后30天病人的maidi随访检查报告项目 by cheng jiangyu 注：maidi表中checkDate是
	 * yyyy-MM-dd 格式的
	 * 
	 */
	public List getMaidiReportsByPatientInThirtyDays(String patientId,
			String beforeThirtyDays, String today) throws Exception {
		List<Maidi> reports = null;
		Session session = null;
		Transaction tran = null;
		beforeThirtyDays = beforeThirtyDays.substring(0, 10);
		today = today.substring(0, 10);
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select max(p.id) as id, p.checkNum, p.checkItem, p.checkDate as checkDate  from maidi p inner join (SELECT checkNum,checkItem,max(checkDate) as checkDate from maidi "
					+ "where patientId = '"
					+ patientId
					+ "' and checkDate between '"
					+ beforeThirtyDays
					+ "' and '"
					+ today
					+ "' and checkItem is not null group by checkItem,checkNum) tt on p.checkDate = tt.checkDate and p.checkNum = tt.checkNum "
					+ "and p.checkItem = tt.checkItem group by p.checkItem,p.checkNum,p.checkDate";
			reports = session.createSQLQuery(sql).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return reports;
	}

	/*
	 * 得到前30天或后30天病人的Pacs随访检查报告项目 by cheng jiangyu 注：pacs表中checkDate是 yyyy-MM-dd
	 * HH:mm:ss格式的
	 * 
	 */
	public List getPacsReportsByPatientInThirtyDays(String patientId,
			String beforeThirtyDays, String today) throws Exception {
		List reports = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select max(p.id) as id, p.checkOrderNum, p.checkItem, p.checkDate from pacs p inner join (SELECT checkOrderNum,checkItem,max(checkDate) as checkDate from pacs "
					+ "where patientId = '"
					+ patientId
					+ "' and checkDate between '"
					+ beforeThirtyDays
					+ "' and '"
					+ today
					+ "' and checkItem is not null group by checkItem,checkOrderNum) tt on p.checkDate = tt.checkDate and p.checkOrderNum = tt.checkOrderNum "
					+ "and p.checkItem = tt.checkItem group by p.checkItem,p.checkOrderNum,p.checkDate";
			reports = session.createSQLQuery(sql).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return reports;
	}

	/*
	 * 得到前30天或后30天病人的CheckReports随访检查报告项目 by cheng jiangyu
	 * checkDate格式为2006-09-07 00:00:00.000
	 * 
	 */
	public List getCheckReportsByPatientInThirtyDays(String patientNo,
			String beforeThirtyDays, String today) throws Exception {
		List reports = null;
		Session session = null;
		Transaction tran = null;
		beforeThirtyDays = beforeThirtyDays.substring(0, 10).concat(
				" 00:00:00.000");
		today = today.substring(0, 10).concat(" 00:00:00.000");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select a.ReceiveDate,a.SectionNo,a.TestTypeNo,a.SampleNo,b.ParItemNo,c.CName as ItemName from reportformforremote a,reportitemforremote b,testitemforremote c where PatNo='"
					+ patientNo
					+ "' and a.CheckDate between '"
					+ beforeThirtyDays
					+ "' and '"
					+ today
					+ "' and b.ReceiveDate=a.ReceiveDate and b.SectionNo=a.SectionNo and b.TestTypeNo=a.TestTypeNo and b.SampleNo=a.SampleNo "
					+ " and c.ItemNo=b.ItemNo";
			reports = session.createSQLQuery(sql).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return reports;
	}

	public void updatePacsReport(Pacs report) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.update(report);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}

	}

	/**
	 * 外院新增pacs记录
	 */
	public void savePacs(Pacs pacs) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (pacs.getId() != null && pacs.getId() > 0L) {
				
				Pacs pacsOlde = (Pacs)session.get(Pacs.class, pacs.getId());
				if(pacsOlde != null && pacsOlde.getCheXiaoFlag() != null && pacsOlde.getCheXiaoFlag() == 1){
					UpdateReportFormForRemoteRecord updateReportFormForRemoteRecord = new UpdateReportFormForRemoteRecord();
					updateReportFormForRemoteRecord.setUpdateDate(new Date());
					updateReportFormForRemoteRecord.setOldValue(pacsOlde.getReportResult());
					updateReportFormForRemoteRecord.setNewValue(pacs.getReportResult());
					updateReportFormForRemoteRecord.setItemId(Integer.parseInt(pacs.getId()+""));
					updateReportFormForRemoteRecord.setUpdateAuthor(pacs.getLuRuAuthor());
					if(!(pacsOlde.getReportResult().equals(pacs.getReportResult()))){
						session.save(updateReportFormForRemoteRecord);
					}
				}
				String sql = "update Pacs set checkDate='"
						+ pacs.getCheckDate() + "',reportResult='"
						+ pacs.getReportResult() + "',luRuAuthor='"
						+ pacs.getLuRuAuthor() + "',age='" + pacs.getAge()
						+ "',check_danwei='" + pacs.getCheck_danwei() + "'"
						+ " where id =" + pacs.getId()
						+ " and isFromOutHospital = 1";
				session.createSQLQuery(sql).executeUpdate();
			} else {
				session.save(pacs);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public List<Map> getMyGrounpCheckReportsForByMember(String patientId,
			String search) {
		Session session = null;
		Transaction tran = null;
		List<Map> reportResult = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient pat = (Patient) session.get(Patient.class, Long
					.parseLong(patientId));
			String reportHql = "from Pacs where patientId ='"
					+ pat.getPatientId() + "' and" + " isFromOutHospital =1 ";
			boolean flag = true;
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if (obj.containsKey("luRuAuthor")) {
					if (obj.getString("luRuAuthor").length() > 0
							&& !"".equals(obj.getString("luRuAuthor"))) {
						reportHql += " and luRuAuthor like '%"
								+ obj.getString("luRuAuthor") + "%' ";
					}
				}
				if (obj.containsKey("tijiao")) {
					if (obj.getString("tijiao").length() > 0
							&& !"--请选择--".equals(obj.getString("tijiao"))
							&& "是".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 11";
						flag = false;
					} else if (obj.getString("tijiao").length() > 0
							&& !"--请选择--".equals(obj.getString("tijiao"))
							&& "否".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 10";
						flag = false;
					}
				} else {
					flag = true;
				}
				if (obj.containsKey("guidang")) {
					if (obj.getString("guidang").length() > 0
							&& !"--请选择--".equals(obj.getString("guidang"))
							&& "是".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 21";
						flag = false;
					}
				} else {
					flag = true;
				}
				if (obj.containsKey("luRuDate")) {
					String value = obj.getString("luRuDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and luRuDate between '"
									+ d[0].toLocaleString() + "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
				if (obj.containsKey("guidangDate")) {
					String value = obj.getString("guidangDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and guiDangDate between '"
									+ d[0].toLocaleString() + "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
			}
			if (flag) {
				reportHql += " and isPatientOrDoctorWriteZanCun in (10,11,21)";
			}
			reportHql += " order by luRuDate desc";
			Query query = session.createQuery(reportHql);
			List<Pacs> rflist = query.list();
			// 根据ReportForm表查出的每一条记录循环查询ReportItem表
			for (int i = 0; i < rflist.size(); i++) {
				Map mp = new HashMap();
				Pacs pacs = rflist.get(i);
				mp.put("checkDate", pacs.getCheckDate());
				mp.put("checkItem", pacs.getCheckItem());
				mp.put("reportResult", pacs.getReportResult());
				mp.put("isFromOutHospital", pacs.getIsFromOutHospital());
				mp.put("isPatientOrDoctorWriteZanCun", pacs
						.getIsPatientOrDoctorWriteZanCun());
				mp.put("sheHeDate", pacs.getSheHeDate());
				mp.put("guiDangDate", pacs.getGuiDangDate());
				if (pacs.getLuRuDate() != null) {
					mp.put("luRuDate", pacs.getLuRuDate().toLocaleString());
				} else {
					mp.put("luRuDate", "");
				}
				if (pacs.getCheckDate() != null) {
					mp.put("checkDate", pacs.getCheckDate());
				} else {
					mp.put("checkDate", "");
				}
				if (pacs.getSheHeDate() != null) {
					mp.put("sheHeDate", pacs.getSheHeDate().toLocaleString());
				} else {
					mp.put("sheHeDate", "");
				}
				if (pacs.getGuiDangDate() != null) {
					mp.put("guiDangDate", pacs.getGuiDangDate()
							.toLocaleString());
				} else {
					mp.put("guiDangDate", "");
				}
				mp.put("cheXiaoFlag", pacs.getCheXiaoFlag());
				mp.put("cheXiaoTrue", pacs.getCheXiaoTrue());
				mp.put("luRuAuthor", pacs.getLuRuAuthor());
				mp.put("duiDangAuthor", pacs.getDuiDangAuthor());
				mp.put("age", pacs.getAge());
				mp.put("check_danwei", pacs.getCheck_danwei());
				mp.put("patientId", pat.getId());
				mp.put("patientName", pat.getPatientName());
				mp.put("id", pacs.getId());
				String isOrNotUpdateHql = "from UpdateReportFormForRemoteRecord where itemId="+pacs.getId();
				List isOrNotUpdateList = session.createQuery(isOrNotUpdateHql).list();
				if(isOrNotUpdateList != null && isOrNotUpdateList.size() > 0){
					mp.put("isOrNotUpdate", "查看");
				}else{
					mp.put("isOrNotUpdate", "");
				}
				reportResult.add(mp);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return reportResult;
	}

	public Map getPacsByPatientId(Long pacsId, String patientId) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient patient = (Patient) session.get(Patient.class, Long
					.parseLong(patientId));
			Pacs pacs = (Pacs) session.get(Pacs.class, pacsId);
			mp.put("patientName", patient.getPatientName());
			mp.put("gender", patient.getGender());
			mp.put("patientNo", patient.getPatientNo());
			mp.put("checkItem", pacs.getCheckItem());
			mp.put("luRuAuthor", pacs.getLuRuAuthor());
			mp.put("duiDangAuthor", pacs.getDuiDangAuthor());
			mp.put("age", pacs.getAge());
			mp.put("check_danwei", pacs.getCheck_danwei());
			mp.put("reportResult", pacs.getReportResult());
			mp.put("checkDate", pacs.getCheckDate());
			mp.put("id", pacs.getId());
			mp.put("patientId", patient.getPatientId());
			mp.put("birthday", sdf.format(patient.getBirthday()));
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	public void updatePacs(Pacs pacs) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (pacs.getIsPatientOrDoctorWriteZanCun() == 10) {
				pacs.setSheHeDate(new Date());
				pacs.setIsPatientOrDoctorWriteZanCun(11);
			}
			if(pacs.getId() != null && pacs.getId() > 0){
				Pacs pacsOlde = (Pacs)session.get(Pacs.class, pacs.getId());
				if(pacsOlde != null && pacsOlde.getCheXiaoFlag() != null && pacsOlde.getCheXiaoFlag() == 1){
					UpdateReportFormForRemoteRecord updateReportFormForRemoteRecord = new UpdateReportFormForRemoteRecord();
					updateReportFormForRemoteRecord.setUpdateDate(new Date());
					updateReportFormForRemoteRecord.setOldValue(pacsOlde.getReportResult());
					updateReportFormForRemoteRecord.setNewValue(pacs.getReportResult());
					updateReportFormForRemoteRecord.setItemId(Integer.parseInt(pacs.getId()+""));
					updateReportFormForRemoteRecord.setUpdateAuthor(pacs.getLuRuAuthor());
					if(!(pacsOlde.getReportResult().equals(pacs.getReportResult()))){
						session.save(updateReportFormForRemoteRecord);
					}
				}
			}
			session.update(pacs);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void savePacsT(Pacs pacs) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.save(pacs);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void updatePacsT(Pacs pacs) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql="";
			if(pacs.getIsPatientOrDoctorWriteZanCun() == 11){
				sql = "update Pacs set checkDate='" + pacs.getCheckDate()
				+ "',reportResult='" + pacs.getReportResult()
				+ "',luRuAuthor='" + pacs.getLuRuAuthor() + "',age='"
				+ pacs.getAge() + "',check_danwei='"
				+ pacs.getCheck_danwei() + "'" + ",sheHeDate= '"
				+ sdf.format(new Date())+"',guiDangDate='"+sdf.format(new Date())+"'" +
				",isPatientOrDoctorWriteZanCun=11,duiDangAuthor='"+pacs.getDuiDangAuthor()+"' where id ="
				+ pacs.getId() + " and isFromOutHospital = 1";
			}else if(pacs.getIsPatientOrDoctorWriteZanCun() == 21){
				sql = "update Pacs set checkDate='" + pacs.getCheckDate()
				+ "',reportResult='" + pacs.getReportResult()
				+ "',luRuAuthor='" + pacs.getLuRuAuthor() + "',age='"
				+ pacs.getAge() + "',check_danwei='"
				+ pacs.getCheck_danwei() + "'" + ",sheHeDate= '"
				+ sdf.format(new Date())+"',guiDangDate='"+sdf.format(new Date())+"'"
				+ ",isPatientOrDoctorWriteZanCun=21,duiDangAuthor='"+pacs.getDuiDangAuthor()+"' where id ="
				+ pacs.getId() + " and isFromOutHospital = 1";
			}
			Pacs pacsOlde = (Pacs)session.get(Pacs.class, pacs.getId());
			if(pacsOlde != null && pacsOlde.getCheXiaoFlag() != null && pacsOlde.getCheXiaoFlag() == 1){
				UpdateReportFormForRemoteRecord updateReportFormForRemoteRecord = new UpdateReportFormForRemoteRecord();
				updateReportFormForRemoteRecord.setUpdateDate(new Date());
				updateReportFormForRemoteRecord.setOldValue(pacsOlde.getReportResult());
				updateReportFormForRemoteRecord.setNewValue(pacs.getReportResult());
				updateReportFormForRemoteRecord.setItemId(Integer.parseInt(pacs.getId()+""));
				updateReportFormForRemoteRecord.setUpdateAuthor(pacs.getLuRuAuthor());
				if(!(pacsOlde.getReportResult().equals(pacs.getReportResult()))){
					session.save(updateReportFormForRemoteRecord);
				}
			}
			session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public List<Map> getMyGrounpCheckReportsFor(Long doctorId, String search) {
		Session session = null;
		Transaction tran = null;
		StringBuffer sb = new StringBuffer();
		String retStr = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List reportResult = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="
					+ doctorId;
			List docList = session.createSQLQuery(docSql).list();
			StringBuilder sbl = null;
			if (docList != null && docList.size() > 0) {
				Object[] docObject = (Object[]) docList.get(0);
				String memberInfoHql = "select pa.patientId from MemberInfo mem left join t_PlanCount pc on mem.patient= pc.patientId ";
				// liugang 2011-09-14 update 添加病人姓名查询
				memberInfoHql += " inner join t_patient pa on pa.id = mem.patient  where mem.deptCode='"
						+ docObject[1] + "' and mem.grounpId=" + docObject[2];
				List list = session.createSQLQuery(memberInfoHql).list();
				if (list != null && list.size() > 0) {
					sbl = new StringBuilder();
					for (int si = 0, size = list.size(); si < size; si++) {
						if (list.get(si) != null) {
							String objS = list.get(si).toString();
							if (si < size - 1) {
								sbl.append("'" + objS + "',");
							} else {
								sbl.append("'" + objS + "'");
							}
						}
					}
				}
			}
			if (sbl != null) {
				conn = DatabaseUtil.getConnection();
				String reportHql = "from Pacs where patientId in ("
						+ sbl.toString() + ") and" + " isFromOutHospital =1 ";
				boolean flag = true;
				if (search != null && search.length() > 0) {
					JSONObject obj = JSONObject.fromObject(search);
					if (obj.containsKey("luRuAuthor")) {
						if (obj.getString("luRuAuthor").length() > 0
								&& !"".equals(obj.getString("luRuAuthor"))) {
							reportHql += " and luRuAuthor like '%"
									+ obj.getString("luRuAuthor") + "%' ";
						}
					}
					if (obj.containsKey("duiDangAuthor")) {
						if (obj.getString("duiDangAuthor").length() > 0
								&& !"".equals(obj.getString("duiDangAuthor"))) {
							reportHql += " and duiDangAuthor like '%"
									+ obj.getString("duiDangAuthor") + "%' ";
						}
					}
					if (obj.containsKey("tijiao")) {
						if (obj.getString("tijiao").length() > 0
								&& !"--请选择--".equals(obj.getString("tijiao"))
								&& "是".equals(obj.getString("tijiao"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 11";
							flag = false;
						}
					} else {
						flag = true;
					}
					if (obj.containsKey("guidang")) {
						if (obj.getString("guidang").length() > 0
								&& !"--请选择--".equals(obj.getString("guidang"))
								&& "是".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 21";
							flag = false;
						} else if (obj.getString("guidang").length() > 0
								&& !"--请选择--".equals(obj.getString("guidang"))
								&& "否".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 20";
							flag = false;
						}
					} else {
						flag = true;
					}
					if (obj.containsKey("luRuDate")) {
						String value = obj.getString("luRuDate");
						if (value.length() > 0) {
							String[] temp = value.split(" ");
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd");
							Date[] d = { null, null };
							if (temp.length == 1) {
								Date t = format.parse(temp[0]);
								d[0] = t;
								d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
							} else if (temp.length == 2) {
								d[0] = format.parse(temp[0]);
								d[1] = format.parse(temp[1]);
							}
							if (d[0] != null && d[1] != null) {
								reportHql += " and luRuDate between '"
										+ d[0].toLocaleString() + "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
					if (obj.containsKey("guidangDate")) {
						String value = obj.getString("guidangDate");
						if (value.length() > 0) {
							String[] temp = value.split(" ");
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd");
							Date[] d = { null, null };
							if (temp.length == 1) {
								Date t = format.parse(temp[0]);
								d[0] = t;
								d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
							} else if (temp.length == 2) {
								d[0] = format.parse(temp[0]);
								d[1] = format.parse(temp[1]);
							}
							if (d[0] != null && d[1] != null) {
								reportHql += " and guiDangDate between '"
										+ d[0].toLocaleString() + "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
				}
				if (flag) {
					reportHql += " and isPatientOrDoctorWriteZanCun in (11,20,21)";
				}
				reportHql += " order by luRuDate desc";
				Query query = session.createQuery(reportHql);
				List<Pacs> rflist = query.list();
				// 根据ReportForm表查出的每一条记录循环查询ReportItem表
				for (int i = 0; i < rflist.size(); i++) {
					Pacs rf = (Pacs) rflist.get(i);
					Map mp = new HashMap();
					String pat = "from Patient where patientId ='"
							+ rf.getPatientId() + "'";
					Patient pati = (Patient) session.createQuery(pat).list()
							.get(0);
					mp.put("id",rf.getId());
					mp.put("patientName", pati.getPatientName());
					mp.put("patientNo", pati.getPatientNo());
					mp.put("patientId", pati.getId().toString());
					mp.put("checkDate", rf.getCheckDate());
					mp.put("checkItem", rf.getCheckItem());
					mp.put("luRuAuthor", rf.getLuRuAuthor());
					mp.put("check_danwei", rf.getCheck_danwei());
					mp.put("isPatientOrDoctorWriteZanCun",rf.getIsPatientOrDoctorWriteZanCun());
					if (rf.getLuRuDate() != null) {
						mp.put("luRuDate", rf.getLuRuDate().toLocaleString());
					} else {
						mp.put("luRuDate", "");
					}
					// liugang 2011-08-06 修改 start
					String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
							+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
							+ " where hsp.patientId = "
							+ pati.getId()
							+ " order by hlg.id desc";
					ps = conn.prepareStatement(curSql);
					rs = ps.executeQuery();
					Date firstDate = null;
					String diag1 = null;
					Date secondDate = null;
					String secondDiag = null;
					if (rs.next()) {
						firstDate = rs.getDate("date");
						diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>",
								" ");// 十二分级中的第一级
						diag1 = diag1.replaceAll("[\\n|\\r]", " ");
					}
					String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
							+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
							+ pati.getId() + " order by hlg.id desc";
					ps = conn.prepareStatement(memSql);
					rs = ps.executeQuery();
					if (rs.next()) {
						secondDate = rs.getDate("date");
						secondDiag = rs.getString("diag1").replaceAll(
								"<[.[^<]]*>", " ");// 十二分级中的第一级
						secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
					}
					if (firstDate != null && secondDate != null) {
						if (firstDate.getTime() > secondDate.getTime()) {
							mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
						} else {
							mp.put("jiBingGrounp", secondDiag); // 借用patient表中的字段，暂时显示当前疾病分
						}
					} else if (firstDate != null) {
						mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else if (secondDate != null) {
						mp.put("jiBingGrounp", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("jiBingGrounp", "");// 借用patient表中的字段，暂时显示当前疾病分
					}

					if (rf.getIsFromOutHospital() != null) {
						mp.put("isFromOutHospital", rf.getIsFromOutHospital()
								.toString());
					} else {
						mp.put("isFromOutHospital", 0);
					}
					if (rf.getGuiDangDate() != null) {
						mp.put("guiDangDate", rf.getGuiDangDate()
								.toLocaleString());
					} else {
						mp.put("guiDangDate", "");
					}
					if (rf.getSheHeDate() != null) {
						mp.put("sheHeDate", rf.getSheHeDate().toLocaleString());
					} else {
						mp.put("sheHeDate", "");
					}
					String isOrNotUpdateHql = "from UpdateReportFormForRemoteRecord where itemId="+rf.getId();
					List isOrNotUpdateList = session.createQuery(isOrNotUpdateHql).list();
					if(isOrNotUpdateList != null && isOrNotUpdateList.size() > 0){
						mp.put("isOrNotUpdate", "查看");
					}else{
						mp.put("isOrNotUpdate", "");
					}
					reportResult.add(mp);
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	/**
	 * 撤销审核
	 */
	public void shenHeNotPassCheckReport(Long pacsId,Long patientId,Long doctorId) {
		Session session = null;
		Transaction tran = null;
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String updateHql="update Pacs set isPatientOrDoctorWriteZanCun=10,sheHeDate = NULL where id="+pacsId;
			session.createSQLQuery(updateHql).executeUpdate();
			User user = (User)session.get(User.class, doctorId);
			Pacs pacs = (Pacs)session.get(Pacs.class, pacsId); 
			MemberInfo mem = (MemberInfo)session.createQuery("from MemberInfo where patient="+patientId).list().get(0);
			Sms sms = new Sms();
			Message message = new Message();    //Message object;
			sms.phone = mem.getPatient().getMobilePhone();
			String msgBuffer ="患者您好，您于"+sdf.format(pacs.getLuRuDate())+"录入的"+pacs.getCheckItem()+"化验结果可能录入有误，请您认真核对后进行修改。北京佑安医院";
			sms.content = msgBuffer;
			SmsOperator smsOp = SmsOperator.getInstance();
			smsOp.sendSms("101100-SJB-HUAX-566466", "ALQPPJJI",new Sms[] { sms });
			SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			message.setMessageContent(msgBuffer); //set message.messageContent;
			message.setMessageDate(new Date());        //set message.messageDate;
			message.setMemberInfo(mem);             //set message.memberInfo;
			message.setUser(user);
			messDao.addMessage(message,session);
			tran.commit();
		}catch(Exception e){
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{			
			DatabaseUtil.closeResource(session);			
		}
	}

	public List<Map> getMyGrounpCheckReportsForByMemberGuanLiYuanAll(
			String deptCode, String search) {
		Session session = null;
		Transaction tran = null;
		StringBuffer sb = new StringBuffer();
		String retStr = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List reportResult = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();

			StringBuilder sbl = null;
			
				String memberInfoHql = "select pa.patientId from MemberInfo mem left join t_PlanCount pc on mem.patient= pc.patientId ";
				// liugang 2011-09-14 update 添加病人姓名查询
				memberInfoHql += " inner join t_patient pa on pa.id = mem.patient  where mem.deptCode='"
						+ deptCode + "'";
				List list = session.createSQLQuery(memberInfoHql).list();
				if (list != null && list.size() > 0) {
					sbl = new StringBuilder();
					for (int si = 0, size = list.size(); si < size; si++) {
						if(list.get(si) != null){
							String objS = list.get(si).toString();
							if (si < size - 1) {
								sbl.append("'" + objS + "',");
							} else {
								sbl.append("'" + objS + "'");
							}
						}
					}
				}
			
			if (sbl != null) {
				conn = DatabaseUtil.getConnection();
				String reportHql = "from Pacs where patientId in ("
								+ sbl.toString()
								+ ") and"
								+ " isFromOutHospital =1 ";
				boolean flag = true;
				if (search != null && search.length() > 0) {
					JSONObject obj = JSONObject.fromObject(search);
					if(obj.containsKey("luRuAuthor")){
						if (obj.getString("luRuAuthor").length() > 0 && !"".equals(obj.getString("luRuAuthor"))){
							reportHql += " and luRuAuthor like '%"+obj.getString("luRuAuthor")+"%' ";
						}
					}
					if(obj.containsKey("duiDangAuthor")){
						if (obj.getString("duiDangAuthor").length() > 0 && !"".equals(obj.getString("duiDangAuthor"))){
							reportHql += " and duiDangAuthor like '%"+obj.getString("duiDangAuthor")+"%' ";
						}
					}
					if (obj.containsKey("tijiao")) {
						if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"是".equals(obj.getString("tijiao"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 11";
							flag = false;
						}else if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"否".equals(obj.getString("tijiao"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 10";
							flag = false;
						}
					}else{
						flag = true;
					}
					if (obj.containsKey("guidang")) {
						if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"是".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 21";
							flag = false;
						}else if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"否".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 20";
							flag = false;
						}
					}else{
						flag = true;
					}
					if (obj.containsKey("luRuDate")) {
						String value = obj.getString("luRuDate");
						if (value.length() > 0) {
							String[] temp = value.split(" ");
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd");
							Date[] d = { null, null };
							if (temp.length == 1) {
								Date t = format.parse(temp[0]);
								d[0] = t;
								d[1] = new Date(
										t.getTime() + 3600 * 24 * 1000);
							} else if (temp.length == 2) {
								d[0] = format.parse(temp[0]);
								d[1] = format.parse(temp[1]);
							}
							if (d[0] != null && d[1] != null) {
								reportHql += " and luRuDate between '"
										+ d[0].toLocaleString()
										+ "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
					if (obj.containsKey("guidangDate")) {
						String value = obj.getString("guidangDate");
						if (value.length() > 0) {
							String[] temp = value.split(" ");
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd");
							Date[] d = { null, null };
							if (temp.length == 1) {
								Date t = format.parse(temp[0]);
								d[0] = t;
								d[1] = new Date(
										t.getTime() + 3600 * 24 * 1000);
							} else if (temp.length == 2) {
								d[0] = format.parse(temp[0]);
								d[1] = format.parse(temp[1]);
							}
							if (d[0] != null && d[1] != null) {
								reportHql += " and guiDangDate between '"
										+ d[0].toLocaleString()
										+ "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
				}
				if(flag){
					reportHql+=" and isPatientOrDoctorWriteZanCun in (10,11,20,21)";
				}
				reportHql+=" order by luRuDate desc";
				Query query = session
						.createQuery(reportHql);
				List<Pacs> rflist = query.list();
				// 根据ReportForm表查出的每一条记录循环查询ReportItem表
				for (int i = 0; i < rflist.size(); i++) {
					Pacs rf = (Pacs) rflist.get(i);
					Map mp = new HashMap();
					String pat = "from Patient where patientId ='"
							+ rf.getPatientId() + "'";
					Patient pati = (Patient) session.createQuery(pat).list()
							.get(0);
					mp.put("id",rf.getId());
					mp.put("patientName", pati.getPatientName());
					mp.put("patientNo", pati.getPatientNo());
					mp.put("patientId", pati.getId().toString());
					mp.put("checkDate", rf.getCheckDate());
					mp.put("checkItem", rf.getCheckItem());
					mp.put("luRuAuthor", rf.getLuRuAuthor());
					mp.put("check_danwei", rf.getCheck_danwei());
					mp.put("isPatientOrDoctorWriteZanCun",rf.getIsPatientOrDoctorWriteZanCun());
					if (rf.getLuRuDate() != null) {
						mp.put("luRuDate", rf.getLuRuDate().toLocaleString());
					} else {
						mp.put("luRuDate", "");
					}
					// liugang 2011-08-06 修改 start
					String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
							+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
							+ " where hsp.patientId = "
							+ pati.getId()
							+ " order by hlg.id desc";
					ps = conn.prepareStatement(curSql);
					rs = ps.executeQuery();
					Date firstDate = null;
					String diag1 = null;
					Date secondDate = null;
					String secondDiag = null;
					if (rs.next()) {
						firstDate = rs.getDate("date");
						diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>",
								" ");// 十二分级中的第一级
						diag1 = diag1.replaceAll("[\\n|\\r]", " ");
					}
					String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
							+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
							+ pati.getId() + " order by hlg.id desc";
					ps = conn.prepareStatement(memSql);
					rs = ps.executeQuery();
					if (rs.next()) {
						secondDate = rs.getDate("date");
						secondDiag = rs.getString("diag1").replaceAll(
								"<[.[^<]]*>", " ");// 十二分级中的第一级
						secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
					}
					if (firstDate != null && secondDate != null) {
						if (firstDate.getTime() > secondDate.getTime()) {
							mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
						} else {
							mp.put("jiBingGrounp", secondDiag); // 借用patient表中的字段，暂时显示当前疾病分
						}
					} else if (firstDate != null) {
						mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else if (secondDate != null) {
						mp.put("jiBingGrounp", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("jiBingGrounp", "");// 借用patient表中的字段，暂时显示当前疾病分
					}

					if (rf.getIsFromOutHospital() != null) {
						mp.put("isFromOutHospital", rf.getIsFromOutHospital()
								.toString());
					} else {
						mp.put("isFromOutHospital", 0);
					}
					if (rf.getGuiDangDate() != null) {
						mp.put("guiDangDate", rf.getGuiDangDate()
								.toLocaleString());
					} else {
						mp.put("guiDangDate", "");
					}
					if (rf.getSheHeDate() != null) {
						mp.put("sheHeDate", rf.getSheHeDate().toLocaleString());
					} else {
						mp.put("sheHeDate", "");
					}
					String isOrNotUpdateHql = "from UpdateReportFormForRemoteRecord where itemId="+rf.getId();
					List isOrNotUpdateList = session.createQuery(isOrNotUpdateHql).list();
					if(isOrNotUpdateList != null && isOrNotUpdateList.size() > 0){
						mp.put("isOrNotUpdate", "查看");
					}else{
						mp.put("isOrNotUpdate", "");
					}
					reportResult.add(mp);
					
				}
				tran.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	public void cheXiaoPacs(String rfId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Pacs pacs = (Pacs)session.get(Pacs.class, Long.parseLong(rfId));
			String sql="";
			if(pacs.getIsPatientOrDoctorWriteZanCun() != null && pacs.getIsPatientOrDoctorWriteZanCun()==21){
				sql = "update Pacs set cheXiaoFlag = 1,guiDangDate = NULL,duiDangAuthor= NUll,isPatientOrDoctorWriteZanCun=20,cheXiaoTrue=1 where id ="+rfId;
			}else if(pacs.getIsPatientOrDoctorWriteZanCun() != null && pacs.getIsPatientOrDoctorWriteZanCun()==11){
				sql = "update Pacs set cheXiaoFlag = 1,guiDangDate = NULL,duiDangAuthor= NUll,cheXiaoTrue=1 where id ="+rfId;
			}
			session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

	public List<Map> getMyGrounpCheckReportsForByMemberDoctor(String patientId,
			String search) {
		Session session = null;
		Transaction tran = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List reportResult = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient pat = (Patient)session.get(Patient.class, Long.parseLong(patientId));
			conn = DatabaseUtil.getConnection();
			String reportHql = "from Pacs where patientId ='"
				+ pat.getPatientId()
				+ "' and"
				+ " isFromOutHospital =1 ";
			boolean flag = true;
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if(obj.containsKey("luRuAuthor")){
					if (obj.getString("luRuAuthor").length() > 0 && !"".equals(obj.getString("luRuAuthor"))){
						reportHql += " and luRuAuthor like '%"+obj.getString("luRuAuthor")+"%' ";
					}
				}
				if(obj.containsKey("duiDangAuthor")){
					if (obj.getString("duiDangAuthor").length() > 0 && !"".equals(obj.getString("duiDangAuthor"))){
						reportHql += " and duiDangAuthor like '%"+obj.getString("duiDangAuthor")+"%' ";
					}
				}
				if (obj.containsKey("tijiao")) {
					if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"是".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 11";
						flag = false;
					}else if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"否".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 10";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("guidang")) {
					if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"是".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 21";
						flag = false;
					}else if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"否".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 20";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("luRuDate")) {
					String value = obj.getString("luRuDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(
									t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and luRuDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
				if (obj.containsKey("guidangDate")) {
					String value = obj.getString("guidangDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(
									t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and guiDangDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
			}
			if(flag){
				reportHql+=" and isPatientOrDoctorWriteZanCun in (11,20,21)";
			}
			reportHql+=" order by luRuDate desc";
			Query query = session
					.createQuery(reportHql);
			List<Pacs> rflist = query.list();
			// 根据ReportForm表查出的每一条记录循环查询ReportItem表
			for (int i = 0; i < rflist.size(); i++) {
				Pacs rf = (Pacs) rflist.get(i);
				Map mp = new HashMap();
				String pats = "from Patient where patientId ='"
						+ rf.getPatientId() + "'";
				Patient pati = (Patient) session.createQuery(pats).list()
						.get(0);
				mp.put("id",rf.getId());
				mp.put("patientName", pati.getPatientName());
				mp.put("patientNo", pati.getPatientNo());
				mp.put("patientId", pati.getId().toString());
				mp.put("checkDate", rf.getCheckDate());
				mp.put("checkItem", rf.getCheckItem());
				mp.put("luRuAuthor", rf.getLuRuAuthor());
				mp.put("check_danwei", rf.getCheck_danwei());
				mp.put("isPatientOrDoctorWriteZanCun",rf.getIsPatientOrDoctorWriteZanCun());
				if (rf.getLuRuDate() != null) {
					mp.put("luRuDate", rf.getLuRuDate().toLocaleString());
				} else {
					mp.put("luRuDate", "");
				}
				// liugang 2011-08-06 修改 start
				String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
						+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
						+ " where hsp.patientId = "
						+ pati.getId()
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(curSql);
				rs = ps.executeQuery();
				Date firstDate = null;
				String diag1 = null;
				Date secondDate = null;
				String secondDiag = null;
				if (rs.next()) {
					firstDate = rs.getDate("date");
					diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>",
							" ");// 十二分级中的第一级
					diag1 = diag1.replaceAll("[\\n|\\r]", " ");
				}
				String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
						+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
						+ pati.getId() + " order by hlg.id desc";
				ps = conn.prepareStatement(memSql);
				rs = ps.executeQuery();
				if (rs.next()) {
					secondDate = rs.getDate("date");
					secondDiag = rs.getString("diag1").replaceAll(
							"<[.[^<]]*>", " ");// 十二分级中的第一级
					secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
				}
				if (firstDate != null && secondDate != null) {
					if (firstDate.getTime() > secondDate.getTime()) {
						mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("jiBingGrounp", secondDiag); // 借用patient表中的字段，暂时显示当前疾病分
					}
				} else if (firstDate != null) {
					mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
				} else if (secondDate != null) {
					mp.put("jiBingGrounp", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
				} else {
					mp.put("jiBingGrounp", "");// 借用patient表中的字段，暂时显示当前疾病分
				}

				if (rf.getIsFromOutHospital() != null) {
					mp.put("isFromOutHospital", rf.getIsFromOutHospital()
							.toString());
				} else {
					mp.put("isFromOutHospital", 0);
				}
				if (rf.getGuiDangDate() != null) {
					mp.put("guiDangDate", rf.getGuiDangDate()
							.toLocaleString());
				} else {
					mp.put("guiDangDate", "");
				}
				if (rf.getSheHeDate() != null) {
					mp.put("sheHeDate", rf.getSheHeDate().toLocaleString());
				} else {
					mp.put("sheHeDate", "");
				}
				String isOrNotUpdateHql = "from UpdateReportFormForRemoteRecord where itemId="+rf.getId();
				List isOrNotUpdateList = session.createQuery(isOrNotUpdateHql).list();
				if(isOrNotUpdateList != null && isOrNotUpdateList.size() > 0){
					mp.put("isOrNotUpdate", "查看");
				}else{
					mp.put("isOrNotUpdate", "");
				}
				reportResult.add(mp);
				
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	public List<Map> getMyGrounpCheckReportsForByMemberGuanLiYuan(
			String patientId, String search) {
		Session session = null;
		Transaction tran = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List reportResult = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient pat = (Patient)session.get(Patient.class, Long.parseLong(patientId));
			conn = DatabaseUtil.getConnection();
			String reportHql = "from Pacs where patientId ='"
				+ pat.getPatientId()
				+ "' and"
				+ " isFromOutHospital =1 ";
			boolean flag = true;
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if(obj.containsKey("luRuAuthor")){
					if (obj.getString("luRuAuthor").length() > 0 && !"".equals(obj.getString("luRuAuthor"))){
						reportHql += " and luRuAuthor like '%"+obj.getString("luRuAuthor")+"%' ";
					}
				}
				if(obj.containsKey("duiDangAuthor")){
					if (obj.getString("duiDangAuthor").length() > 0 && !"".equals(obj.getString("duiDangAuthor"))){
						reportHql += " and duiDangAuthor like '%"+obj.getString("duiDangAuthor")+"%' ";
					}
				}
				if (obj.containsKey("tijiao")) {
					if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"是".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 11";
						flag = false;
					}else if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"否".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 10";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("guidang")) {
					if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"是".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 21";
						flag = false;
					}else if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"否".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 20";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("luRuDate")) {
					String value = obj.getString("luRuDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(
									t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and luRuDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
				if (obj.containsKey("guidangDate")) {
					String value = obj.getString("guidangDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(
									t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and guiDangDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
			}
			if(flag){
				reportHql+=" and isPatientOrDoctorWriteZanCun in (10,11,20,21)";
			}
			reportHql+=" order by luRuDate desc";
			Query query = session
					.createQuery(reportHql);
			List<Pacs> rflist = query.list();
			// 根据ReportForm表查出的每一条记录循环查询ReportItem表
			for (int i = 0; i < rflist.size(); i++) {
				Pacs rf = (Pacs) rflist.get(i);
				Map mp = new HashMap();
				String pats = "from Patient where patientId ='"
						+ rf.getPatientId() + "'";
				Patient pati = (Patient) session.createQuery(pats).list()
						.get(0);
				mp.put("id",rf.getId());
				mp.put("patientName", pati.getPatientName());
				mp.put("patientNo", pati.getPatientNo());
				mp.put("patientId", pati.getId().toString());
				mp.put("checkDate", rf.getCheckDate());
				mp.put("checkItem", rf.getCheckItem());
				mp.put("luRuAuthor", rf.getLuRuAuthor());
				mp.put("check_danwei", rf.getCheck_danwei());
				mp.put("isPatientOrDoctorWriteZanCun",rf.getIsPatientOrDoctorWriteZanCun());
				if (rf.getLuRuDate() != null) {
					mp.put("luRuDate", rf.getLuRuDate().toLocaleString());
				} else {
					mp.put("luRuDate", "");
				}
				// liugang 2011-08-06 修改 start
				String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
						+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
						+ " where hsp.patientId = "
						+ pati.getId()
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(curSql);
				rs = ps.executeQuery();
				Date firstDate = null;
				String diag1 = null;
				Date secondDate = null;
				String secondDiag = null;
				if (rs.next()) {
					firstDate = rs.getDate("date");
					diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>",
							" ");// 十二分级中的第一级
					diag1 = diag1.replaceAll("[\\n|\\r]", " ");
				}
				String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
						+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
						+ pati.getId() + " order by hlg.id desc";
				ps = conn.prepareStatement(memSql);
				rs = ps.executeQuery();
				if (rs.next()) {
					secondDate = rs.getDate("date");
					secondDiag = rs.getString("diag1").replaceAll(
							"<[.[^<]]*>", " ");// 十二分级中的第一级
					secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
				}
				if (firstDate != null && secondDate != null) {
					if (firstDate.getTime() > secondDate.getTime()) {
						mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("jiBingGrounp", secondDiag); // 借用patient表中的字段，暂时显示当前疾病分
					}
				} else if (firstDate != null) {
					mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
				} else if (secondDate != null) {
					mp.put("jiBingGrounp", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
				} else {
					mp.put("jiBingGrounp", "");// 借用patient表中的字段，暂时显示当前疾病分
				}

				if (rf.getIsFromOutHospital() != null) {
					mp.put("isFromOutHospital", rf.getIsFromOutHospital()
							.toString());
				} else {
					mp.put("isFromOutHospital", 0);
				}
				if (rf.getGuiDangDate() != null) {
					mp.put("guiDangDate", rf.getGuiDangDate()
							.toLocaleString());
				} else {
					mp.put("guiDangDate", "");
				}
				if (rf.getSheHeDate() != null) {
					mp.put("sheHeDate", rf.getSheHeDate().toLocaleString());
				} else {
					mp.put("sheHeDate", "");
				}
				String isOrNotUpdateHql = "from UpdateReportFormForRemoteRecord where itemId="+rf.getId();
				List isOrNotUpdateList = session.createQuery(isOrNotUpdateHql).list();
				if(isOrNotUpdateList != null && isOrNotUpdateList.size() > 0){
					mp.put("isOrNotUpdate", "查看");
				}else{
					mp.put("isOrNotUpdate", "");
				}
				reportResult.add(mp);
				
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	public void deletepacs(Long pacsId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql ="delete from Pacs where id ="+pacsId;
			session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 修改提交
	 */
	public void updatePacsTi(Pacs pacs) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql= "update Pacs set checkDate='" + pacs.getCheckDate()
				+ "',reportResult='" + pacs.getReportResult()
				+ "',luRuAuthor='" + pacs.getLuRuAuthor() + "',age='"
				+ pacs.getAge() + "',check_danwei='"
				+ pacs.getCheck_danwei() + "',sheHeDate= '"
				+ sdf.format(new Date())
				+ "',isPatientOrDoctorWriteZanCun=11" + " where id ="
				+ pacs.getId() + " and isFromOutHospital = 1";
			
			Pacs pacsOlde = (Pacs)session.get(Pacs.class, pacs.getId());
			if(pacsOlde != null && pacsOlde.getCheXiaoFlag() != null && pacsOlde.getCheXiaoFlag() == 1){
				UpdateReportFormForRemoteRecord updateReportFormForRemoteRecord = new UpdateReportFormForRemoteRecord();
				updateReportFormForRemoteRecord.setUpdateDate(new Date());
				updateReportFormForRemoteRecord.setOldValue(pacsOlde.getReportResult());
				updateReportFormForRemoteRecord.setNewValue(pacs.getReportResult());
				updateReportFormForRemoteRecord.setItemId(Integer.parseInt(pacs.getId()+""));
				updateReportFormForRemoteRecord.setUpdateAuthor(pacs.getLuRuAuthor());
				if(!(pacsOlde.getReportResult().equals(pacs.getReportResult()))){
					session.save(updateReportFormForRemoteRecord);
				}
			}
			session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public List<Map> getMyGrounpCheckReportsForByMemberMaiDi(String patientId,
			String search) {
		Session session = null;
		Transaction tran = null;
		List<Map> reportResult = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient pat = (Patient) session.get(Patient.class, Long
					.parseLong(patientId));
			String reportHql = "from Maidi where patientId ='"
					+ pat.getPatientId() + "' and" + " isFromOutHospital =1 ";
			boolean flag = true;
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if (obj.containsKey("luRuAuthor")) {
					if (obj.getString("luRuAuthor").length() > 0
							&& !"".equals(obj.getString("luRuAuthor"))) {
						reportHql += " and luRuAuthor like '%"
								+ obj.getString("luRuAuthor") + "%' ";
					}
				}
				if (obj.containsKey("tijiao")) {
					if (obj.getString("tijiao").length() > 0
							&& !"--请选择--".equals(obj.getString("tijiao"))
							&& "是".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 11";
						flag = false;
					} else if (obj.getString("tijiao").length() > 0
							&& !"--请选择--".equals(obj.getString("tijiao"))
							&& "否".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 10";
						flag = false;
					}
				} else {
					flag = true;
				}
				if (obj.containsKey("guidang")) {
					if (obj.getString("guidang").length() > 0
							&& !"--请选择--".equals(obj.getString("guidang"))
							&& "是".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 21";
						flag = false;
					}
				} else {
					flag = true;
				}
				if (obj.containsKey("luRuDate")) {
					String value = obj.getString("luRuDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and luRuDate between '"
									+ d[0].toLocaleString() + "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
				if (obj.containsKey("guidangDate")) {
					String value = obj.getString("guidangDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and guiDangDate between '"
									+ d[0].toLocaleString() + "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
			}
			if (flag) {
				reportHql += " and isPatientOrDoctorWriteZanCun in (10,11,21)";
			}
			reportHql += " order by luRuDate desc";
			Query query = session.createQuery(reportHql);
			List<Maidi> rflist = query.list();
			// 根据ReportForm表查出的每一条记录循环查询ReportItem表
			for (int i = 0; i < rflist.size(); i++) {
				Map mp = new HashMap();
				Maidi maidi = rflist.get(i);
				mp.put("checkDate", maidi.getCheckDate());
				mp.put("checkItem", maidi.getCheckItem());
				mp.put("reportResult", maidi.getReportResult());
				mp.put("isFromOutHospital", maidi.getIsFromOutHospital());
				mp.put("isPatientOrDoctorWriteZanCun", maidi
						.getIsPatientOrDoctorWriteZanCun());
				mp.put("sheHeDate", maidi.getSheHeDate());
				mp.put("guiDangDate", maidi.getGuiDangDate());
				if (maidi.getLuRuDate() != null) {
					mp.put("luRuDate", maidi.getLuRuDate().toLocaleString());
				} else {
					mp.put("luRuDate", "");
				}
				if (maidi.getCheckDate() != null) {
					mp.put("checkDate", maidi.getCheckDate());
				} else {
					mp.put("checkDate", "");
				}
				if (maidi.getSheHeDate() != null) {
					mp.put("sheHeDate", maidi.getSheHeDate().toLocaleString());
				} else {
					mp.put("sheHeDate", "");
				}
				if (maidi.getGuiDangDate() != null) {
					mp.put("guiDangDate", maidi.getGuiDangDate()
							.toLocaleString());
				} else {
					mp.put("guiDangDate", "");
				}
				mp.put("cheXiaoFlag", maidi.getCheXiaoFlag());
				mp.put("cheXiaoTrue", maidi.getCheXiaoTrue());
				mp.put("luRuAuthor", maidi.getLuRuAuthor());
				mp.put("duiDangAuthor", maidi.getDuiDangAuthor());
				mp.put("age", maidi.getAge());
				mp.put("check_danwei", maidi.getCheck_danwei());
				mp.put("patientId", pat.getId());
				mp.put("patientName", pat.getPatientName());
				mp.put("id", maidi.getId());
				String isOrNotUpdateHql = "from UpdateReportFormForRemoteRecord where itemId="+maidi.getId();
				List isOrNotUpdateList = session.createQuery(isOrNotUpdateHql).list();
				if(isOrNotUpdateList != null && isOrNotUpdateList.size() > 0){
					mp.put("isOrNotUpdate", "查看");
				}else{
					mp.put("isOrNotUpdate", "");
				}
				reportResult.add(mp);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return reportResult;
	}

	public List<Map> getMyGrounpCheckReportsForByMemberDoctorMaiDi(
			String patientId, String search) {
		Session session = null;
		Transaction tran = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List reportResult = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient pat = (Patient)session.get(Patient.class, Long.parseLong(patientId));
			conn = DatabaseUtil.getConnection();
			String reportHql = "from Maidi where patientId ='"
				+ pat.getPatientId()
				+ "' and"
				+ " isFromOutHospital =1 ";
			boolean flag = true;
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if(obj.containsKey("luRuAuthor")){
					if (obj.getString("luRuAuthor").length() > 0 && !"".equals(obj.getString("luRuAuthor"))){
						reportHql += " and luRuAuthor like '%"+obj.getString("luRuAuthor")+"%' ";
					}
				}
				if(obj.containsKey("duiDangAuthor")){
					if (obj.getString("duiDangAuthor").length() > 0 && !"".equals(obj.getString("duiDangAuthor"))){
						reportHql += " and duiDangAuthor like '%"+obj.getString("duiDangAuthor")+"%' ";
					}
				}
				if (obj.containsKey("tijiao")) {
					if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"是".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 11";
						flag = false;
					}else if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"否".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 10";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("guidang")) {
					if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"是".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 21";
						flag = false;
					}else if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"否".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 20";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("luRuDate")) {
					String value = obj.getString("luRuDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(
									t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and luRuDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
				if (obj.containsKey("guidangDate")) {
					String value = obj.getString("guidangDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(
									t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and guiDangDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
			}
			if(flag){
				reportHql+=" and isPatientOrDoctorWriteZanCun in (11,20,21)";
			}
			reportHql+=" order by luRuDate desc";
			Query query = session
					.createQuery(reportHql);
			List<Maidi> rflist = query.list();
			// 根据ReportForm表查出的每一条记录循环查询ReportItem表
			for (int i = 0; i < rflist.size(); i++) {
				Maidi rf = (Maidi) rflist.get(i);
				Map mp = new HashMap();
				String pats = "from Patient where patientId ='"
						+ rf.getPatientId() + "'";
				Patient pati = (Patient) session.createQuery(pats).list()
						.get(0);
				mp.put("id",rf.getId());
				mp.put("patientName", pati.getPatientName());
				mp.put("patientNo", pati.getPatientNo());
				mp.put("patientId", pati.getId().toString());
				mp.put("checkDate", rf.getCheckDate());
				mp.put("checkItem", rf.getCheckItem());
				mp.put("luRuAuthor", rf.getLuRuAuthor());
				mp.put("check_danwei", rf.getCheck_danwei());
				mp.put("isPatientOrDoctorWriteZanCun",rf.getIsPatientOrDoctorWriteZanCun());
				if (rf.getLuRuDate() != null) {
					mp.put("luRuDate", rf.getLuRuDate().toLocaleString());
				} else {
					mp.put("luRuDate", "");
				}
				// liugang 2011-08-06 修改 start
				String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
						+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
						+ " where hsp.patientId = "
						+ pati.getId()
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(curSql);
				rs = ps.executeQuery();
				Date firstDate = null;
				String diag1 = null;
				Date secondDate = null;
				String secondDiag = null;
				if (rs.next()) {
					firstDate = rs.getDate("date");
					diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>",
							" ");// 十二分级中的第一级
					diag1 = diag1.replaceAll("[\\n|\\r]", " ");
				}
				String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
						+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
						+ pati.getId() + " order by hlg.id desc";
				ps = conn.prepareStatement(memSql);
				rs = ps.executeQuery();
				if (rs.next()) {
					secondDate = rs.getDate("date");
					secondDiag = rs.getString("diag1").replaceAll(
							"<[.[^<]]*>", " ");// 十二分级中的第一级
					secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
				}
				if (firstDate != null && secondDate != null) {
					if (firstDate.getTime() > secondDate.getTime()) {
						mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("jiBingGrounp", secondDiag); // 借用patient表中的字段，暂时显示当前疾病分
					}
				} else if (firstDate != null) {
					mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
				} else if (secondDate != null) {
					mp.put("jiBingGrounp", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
				} else {
					mp.put("jiBingGrounp", "");// 借用patient表中的字段，暂时显示当前疾病分
				}

				if (rf.getIsFromOutHospital() != null) {
					mp.put("isFromOutHospital", rf.getIsFromOutHospital()
							.toString());
				} else {
					mp.put("isFromOutHospital", 0);
				}
				if (rf.getGuiDangDate() != null) {
					mp.put("guiDangDate", rf.getGuiDangDate()
							.toLocaleString());
				} else {
					mp.put("guiDangDate", "");
				}
				if (rf.getSheHeDate() != null) {
					mp.put("sheHeDate", rf.getSheHeDate().toLocaleString());
				} else {
					mp.put("sheHeDate", "");
				}
				String isOrNotUpdateHql = "from UpdateReportFormForRemoteRecord where itemId="+rf.getId();
				List isOrNotUpdateList = session.createQuery(isOrNotUpdateHql).list();
				if(isOrNotUpdateList != null && isOrNotUpdateList.size() > 0){
					mp.put("isOrNotUpdate", "查看");
				}else{
					mp.put("isOrNotUpdate", "");
				}
				reportResult.add(mp);
				
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	public List<Map> getMyGrounpCheckReportsForByMemberGuanLiYuanMaiDi(
			String patientId, String search) {
		Session session = null;
		Transaction tran = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List reportResult = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient pat = (Patient)session.get(Patient.class, Long.parseLong(patientId));
			conn = DatabaseUtil.getConnection();
			String reportHql = "from Maidi where patientId ='"
				+ pat.getPatientId()
				+ "' and"
				+ " isFromOutHospital =1 ";
			boolean flag = true;
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if(obj.containsKey("luRuAuthor")){
					if (obj.getString("luRuAuthor").length() > 0 && !"".equals(obj.getString("luRuAuthor"))){
						reportHql += " and luRuAuthor like '%"+obj.getString("luRuAuthor")+"%' ";
					}
				}
				if(obj.containsKey("duiDangAuthor")){
					if (obj.getString("duiDangAuthor").length() > 0 && !"".equals(obj.getString("duiDangAuthor"))){
						reportHql += " and duiDangAuthor like '%"+obj.getString("duiDangAuthor")+"%' ";
					}
				}
				if (obj.containsKey("tijiao")) {
					if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"是".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 11";
						flag = false;
					}else if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"否".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 10";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("guidang")) {
					if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"是".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 21";
						flag = false;
					}else if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"否".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 20";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("luRuDate")) {
					String value = obj.getString("luRuDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(
									t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and luRuDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
				if (obj.containsKey("guidangDate")) {
					String value = obj.getString("guidangDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(
									t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							reportHql += " and guiDangDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
			}
			if(flag){
				reportHql+=" and isPatientOrDoctorWriteZanCun in (10,11,20,21)";
			}
			reportHql+=" order by luRuDate desc";
			Query query = session
					.createQuery(reportHql);
			List<Maidi> rflist = query.list();
			// 根据ReportForm表查出的每一条记录循环查询ReportItem表
			for (int i = 0; i < rflist.size(); i++) {
				Maidi rf = (Maidi) rflist.get(i);
				Map mp = new HashMap();
				String pats = "from Patient where patientId ='"
						+ rf.getPatientId() + "'";
				Patient pati = (Patient) session.createQuery(pats).list()
						.get(0);
				mp.put("id",rf.getId());
				mp.put("patientName", pati.getPatientName());
				mp.put("patientNo", pati.getPatientNo());
				mp.put("patientId", pati.getId().toString());
				mp.put("checkDate", rf.getCheckDate());
				mp.put("checkItem", rf.getCheckItem());
				mp.put("luRuAuthor", rf.getLuRuAuthor());
				mp.put("check_danwei", rf.getCheck_danwei());
				mp.put("isPatientOrDoctorWriteZanCun",rf.getIsPatientOrDoctorWriteZanCun());
				if (rf.getLuRuDate() != null) {
					mp.put("luRuDate", rf.getLuRuDate().toLocaleString());
				} else {
					mp.put("luRuDate", "");
				}
				// liugang 2011-08-06 修改 start
				String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
						+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
						+ " where hsp.patientId = "
						+ pati.getId()
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(curSql);
				rs = ps.executeQuery();
				Date firstDate = null;
				String diag1 = null;
				Date secondDate = null;
				String secondDiag = null;
				if (rs.next()) {
					firstDate = rs.getDate("date");
					diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>",
							" ");// 十二分级中的第一级
					diag1 = diag1.replaceAll("[\\n|\\r]", " ");
				}
				String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
						+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
						+ pati.getId() + " order by hlg.id desc";
				ps = conn.prepareStatement(memSql);
				rs = ps.executeQuery();
				if (rs.next()) {
					secondDate = rs.getDate("date");
					secondDiag = rs.getString("diag1").replaceAll(
							"<[.[^<]]*>", " ");// 十二分级中的第一级
					secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
				}
				if (firstDate != null && secondDate != null) {
					if (firstDate.getTime() > secondDate.getTime()) {
						mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("jiBingGrounp", secondDiag); // 借用patient表中的字段，暂时显示当前疾病分
					}
				} else if (firstDate != null) {
					mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
				} else if (secondDate != null) {
					mp.put("jiBingGrounp", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
				} else {
					mp.put("jiBingGrounp", "");// 借用patient表中的字段，暂时显示当前疾病分
				}

				if (rf.getIsFromOutHospital() != null) {
					mp.put("isFromOutHospital", rf.getIsFromOutHospital()
							.toString());
				} else {
					mp.put("isFromOutHospital", 0);
				}
				if (rf.getGuiDangDate() != null) {
					mp.put("guiDangDate", rf.getGuiDangDate()
							.toLocaleString());
				} else {
					mp.put("guiDangDate", "");
				}
				if (rf.getSheHeDate() != null) {
					mp.put("sheHeDate", rf.getSheHeDate().toLocaleString());
				} else {
					mp.put("sheHeDate", "");
				}
				String isOrNotUpdateHql = "from UpdateReportFormForRemoteRecord where itemId="+rf.getId();
				List isOrNotUpdateList = session.createQuery(isOrNotUpdateHql).list();
				if(isOrNotUpdateList != null && isOrNotUpdateList.size() > 0){
					mp.put("isOrNotUpdate", "查看");
				}else{
					mp.put("isOrNotUpdate", "");
				}
				reportResult.add(mp);
				
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	public Map getMaiDiByPatientId(Long pacsId, String patientId) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient patient = (Patient) session.get(Patient.class, Long
					.parseLong(patientId));
			Maidi maidi = (Maidi) session.get(Maidi.class, pacsId);
			mp.put("patientName", patient.getPatientName());
			mp.put("gender", patient.getGender());
			mp.put("patientNo", patient.getPatientNo());
			mp.put("checkItem", maidi.getCheckItem());
			mp.put("luRuAuthor", maidi.getLuRuAuthor());
			mp.put("duiDangAuthor", maidi.getDuiDangAuthor());
			mp.put("age", maidi.getAge());
			mp.put("check_danwei", maidi.getCheck_danwei());
			mp.put("reportResult", maidi.getReportResult());
			mp.put("checkDate", maidi.getCheckDate());
			mp.put("id", maidi.getId());
			mp.put("patientId", patient.getPatientId());
			mp.put("birthday", sdf.format(patient.getBirthday()));
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	public void saveMaidi(Maidi maidi) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (maidi.getId() != null && maidi.getId() > 0L) {
				
				Maidi maidiOlde = (Maidi)session.get(Maidi.class, maidi.getId());
				if(maidiOlde != null && maidiOlde.getCheXiaoFlag() != null && maidiOlde.getCheXiaoFlag() == 1){
					UpdateReportFormForRemoteRecord updateReportFormForRemoteRecord = new UpdateReportFormForRemoteRecord();
					updateReportFormForRemoteRecord.setUpdateDate(new Date());
					updateReportFormForRemoteRecord.setOldValue(maidiOlde.getReportResult());
					updateReportFormForRemoteRecord.setNewValue(maidi.getReportResult());
					updateReportFormForRemoteRecord.setItemId(Integer.parseInt(maidi.getId()+""));
					updateReportFormForRemoteRecord.setUpdateAuthor(maidi.getLuRuAuthor());
					if(!(maidiOlde.getReportResult().equals(maidi.getReportResult()))){
						session.save(updateReportFormForRemoteRecord);
					}
				}
				String sql = "update Maidi set checkDate='"
						+ maidi.getCheckDate() + "',reportResult='"
						+ maidi.getReportResult() + "',luRuAuthor='"
						+ maidi.getLuRuAuthor() + "',age='" + maidi.getAge()
						+ "',check_danwei='" + maidi.getCheck_danwei() + "'"
						+ " where id =" + maidi.getId()
						+ " and isFromOutHospital = 1";
				session.createSQLQuery(sql).executeUpdate();
			} else {
				session.save(maidi);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void saveMaidiT(Maidi maidi) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.save(maidi);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void updateMaidiTi(Maidi maidi) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql= "update Maidi set checkDate='" + maidi.getCheckDate()
				+ "',reportResult='" + maidi.getReportResult()
				+ "',luRuAuthor='" + maidi.getLuRuAuthor() + "',age='"
				+ maidi.getAge() + "',check_danwei='"
				+ maidi.getCheck_danwei() + "',sheHeDate= '"
				+ sdf.format(new Date())
				+ "',isPatientOrDoctorWriteZanCun=11" + " where id ="
				+ maidi.getId() + " and isFromOutHospital = 1";
			
			Maidi maidiOlde = (Maidi)session.get(Maidi.class, maidi.getId());
			if(maidiOlde != null && maidiOlde.getCheXiaoFlag() != null && maidiOlde.getCheXiaoFlag() == 1){
				UpdateReportFormForRemoteRecord updateReportFormForRemoteRecord = new UpdateReportFormForRemoteRecord();
				updateReportFormForRemoteRecord.setUpdateDate(new Date());
				updateReportFormForRemoteRecord.setOldValue(maidiOlde.getReportResult());
				updateReportFormForRemoteRecord.setNewValue(maidi.getReportResult());
				updateReportFormForRemoteRecord.setItemId(Integer.parseInt(maidi.getId()+""));
				updateReportFormForRemoteRecord.setUpdateAuthor(maidi.getLuRuAuthor());
				if(!(maidiOlde.getReportResult().equals(maidi.getReportResult()))){
					session.save(updateReportFormForRemoteRecord);
				}
			}
			session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void updateMaidiT(Maidi maidi) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String sql="";
			if(maidi.getIsPatientOrDoctorWriteZanCun() == 11){
				sql = "update Maidi set checkDate='" + maidi.getCheckDate()
				+ "',reportResult='" + maidi.getReportResult()
				+ "',luRuAuthor='" + maidi.getLuRuAuthor() + "',age='"
				+ maidi.getAge() + "',check_danwei='"
				+ maidi.getCheck_danwei() + "'" + ",sheHeDate= '"
				+ sdf.format(new Date())+"',guiDangDate='"+sdf.format(new Date())+"'" +
				",isPatientOrDoctorWriteZanCun=11,duiDangAuthor='"+maidi.getDuiDangAuthor()+"' where id ="
				+ maidi.getId() + " and isFromOutHospital = 1";
			}else if(maidi.getIsPatientOrDoctorWriteZanCun() == 21){
				sql = "update maidi set checkDate='" + maidi.getCheckDate()
				+ "',reportResult='" + maidi.getReportResult()
				+ "',luRuAuthor='" + maidi.getLuRuAuthor() + "',age='"
				+ maidi.getAge() + "',check_danwei='"
				+ maidi.getCheck_danwei() + "'" + ",sheHeDate= '"
				+ sdf.format(new Date())+"',guiDangDate='"+sdf.format(new Date())+"'"
				+ ",isPatientOrDoctorWriteZanCun=21,duiDangAuthor='"+maidi.getDuiDangAuthor()+"' where id ="
				+ maidi.getId() + " and isFromOutHospital = 1";
			}
			Maidi maidiOlde = (Maidi)session.get(Maidi.class, maidi.getId());
			if(maidiOlde != null && maidiOlde.getCheXiaoFlag() != null && maidiOlde.getCheXiaoFlag() == 1){
				UpdateReportFormForRemoteRecord updateReportFormForRemoteRecord = new UpdateReportFormForRemoteRecord();
				updateReportFormForRemoteRecord.setUpdateDate(new Date());
				updateReportFormForRemoteRecord.setOldValue(maidiOlde.getReportResult());
				updateReportFormForRemoteRecord.setNewValue(maidi.getReportResult());
				updateReportFormForRemoteRecord.setItemId(Integer.parseInt(maidi.getId()+""));
				updateReportFormForRemoteRecord.setUpdateAuthor(maidi.getLuRuAuthor());
				if(!(maidiOlde.getReportResult().equals(maidi.getReportResult()))){
					session.save(updateReportFormForRemoteRecord);
				}
			}
			session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void deleteMaidi(Long pacsId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql ="delete from Maidi where id ="+pacsId;
			session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

	public void cheXiaoMaidi(String rfId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Maidi maidi = (Maidi)session.get(Maidi.class, Long.parseLong(rfId));
			String sql="";
			if(maidi.getIsPatientOrDoctorWriteZanCun() != null && maidi.getIsPatientOrDoctorWriteZanCun()==21){
				sql = "update maidi set cheXiaoFlag = 1,guiDangDate = NULL,duiDangAuthor= NUll,isPatientOrDoctorWriteZanCun=20,cheXiaoTrue=1 where id ="+rfId;
			}else if(maidi.getIsPatientOrDoctorWriteZanCun() != null && maidi.getIsPatientOrDoctorWriteZanCun()==11){
				sql = "update maidi set cheXiaoFlag = 1,guiDangDate = NULL,duiDangAuthor= NUll,cheXiaoTrue=1 where id ="+rfId;
			}
			session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

	public List<Map> getMyGrounpCheckReportsForMaiDi(Long doctorId,
			String search) {
		Session session = null;
		Transaction tran = null;
		StringBuffer sb = new StringBuffer();
		String retStr = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List reportResult = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="
					+ doctorId;
			List docList = session.createSQLQuery(docSql).list();
			StringBuilder sbl = null;
			if (docList != null && docList.size() > 0) {
				Object[] docObject = (Object[]) docList.get(0);
				String memberInfoHql = "select pa.patientId from MemberInfo mem left join t_PlanCount pc on mem.patient= pc.patientId ";
				// liugang 2011-09-14 update 添加病人姓名查询
				memberInfoHql += " inner join t_patient pa on pa.id = mem.patient  where mem.deptCode='"
						+ docObject[1] + "' and mem.grounpId=" + docObject[2];
				List list = session.createSQLQuery(memberInfoHql).list();
				if (list != null && list.size() > 0) {
					sbl = new StringBuilder();
					for (int si = 0, size = list.size(); si < size; si++) {
						if (list.get(si) != null) {
							String objS = list.get(si).toString();
							if (si < size - 1) {
								sbl.append("'" + objS + "',");
							} else {
								sbl.append("'" + objS + "'");
							}
						}
					}
				}
			}
			if (sbl != null) {
				conn = DatabaseUtil.getConnection();
				String reportHql = "from Maidi where patientId in ("
						+ sbl.toString() + ") and" + " isFromOutHospital =1 ";
				boolean flag = true;
				if (search != null && search.length() > 0) {
					JSONObject obj = JSONObject.fromObject(search);
					if (obj.containsKey("luRuAuthor")) {
						if (obj.getString("luRuAuthor").length() > 0
								&& !"".equals(obj.getString("luRuAuthor"))) {
							reportHql += " and luRuAuthor like '%"
									+ obj.getString("luRuAuthor") + "%' ";
						}
					}
					if (obj.containsKey("duiDangAuthor")) {
						if (obj.getString("duiDangAuthor").length() > 0
								&& !"".equals(obj.getString("duiDangAuthor"))) {
							reportHql += " and duiDangAuthor like '%"
									+ obj.getString("duiDangAuthor") + "%' ";
						}
					}
					if (obj.containsKey("tijiao")) {
						if (obj.getString("tijiao").length() > 0
								&& !"--请选择--".equals(obj.getString("tijiao"))
								&& "是".equals(obj.getString("tijiao"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 11";
							flag = false;
						}
					} else {
						flag = true;
					}
					if (obj.containsKey("guidang")) {
						if (obj.getString("guidang").length() > 0
								&& !"--请选择--".equals(obj.getString("guidang"))
								&& "是".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 21";
							flag = false;
						} else if (obj.getString("guidang").length() > 0
								&& !"--请选择--".equals(obj.getString("guidang"))
								&& "否".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 20";
							flag = false;
						}
					} else {
						flag = true;
					}
					if (obj.containsKey("luRuDate")) {
						String value = obj.getString("luRuDate");
						if (value.length() > 0) {
							String[] temp = value.split(" ");
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd");
							Date[] d = { null, null };
							if (temp.length == 1) {
								Date t = format.parse(temp[0]);
								d[0] = t;
								d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
							} else if (temp.length == 2) {
								d[0] = format.parse(temp[0]);
								d[1] = format.parse(temp[1]);
							}
							if (d[0] != null && d[1] != null) {
								reportHql += " and luRuDate between '"
										+ d[0].toLocaleString() + "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
					if (obj.containsKey("guidangDate")) {
						String value = obj.getString("guidangDate");
						if (value.length() > 0) {
							String[] temp = value.split(" ");
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd");
							Date[] d = { null, null };
							if (temp.length == 1) {
								Date t = format.parse(temp[0]);
								d[0] = t;
								d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
							} else if (temp.length == 2) {
								d[0] = format.parse(temp[0]);
								d[1] = format.parse(temp[1]);
							}
							if (d[0] != null && d[1] != null) {
								reportHql += " and guiDangDate between '"
										+ d[0].toLocaleString() + "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
				}
				if (flag) {
					reportHql += " and isPatientOrDoctorWriteZanCun in (11,20,21)";
				}
				reportHql += " order by luRuDate desc";
				Query query = session.createQuery(reportHql);
				List<Maidi> rflist = query.list();
				// 根据ReportForm表查出的每一条记录循环查询ReportItem表
				for (int i = 0; i < rflist.size(); i++) {
					Maidi rf = (Maidi) rflist.get(i);
					Map mp = new HashMap();
					String pat = "from Patient where patientId ='"
							+ rf.getPatientId() + "'";
					Patient pati = (Patient) session.createQuery(pat).list()
							.get(0);
					mp.put("id",rf.getId());
					mp.put("patientName", pati.getPatientName());
					mp.put("patientNo", pati.getPatientNo());
					mp.put("patientId", pati.getId().toString());
					mp.put("checkDate", rf.getCheckDate());
					mp.put("checkItem", rf.getCheckItem());
					mp.put("luRuAuthor", rf.getLuRuAuthor());
					mp.put("check_danwei", rf.getCheck_danwei());
					mp.put("isPatientOrDoctorWriteZanCun",rf.getIsPatientOrDoctorWriteZanCun());
					if (rf.getLuRuDate() != null) {
						mp.put("luRuDate", rf.getLuRuDate().toLocaleString());
					} else {
						mp.put("luRuDate", "");
					}
					// liugang 2011-08-06 修改 start
					String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
							+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
							+ " where hsp.patientId = "
							+ pati.getId()
							+ " order by hlg.id desc";
					ps = conn.prepareStatement(curSql);
					rs = ps.executeQuery();
					Date firstDate = null;
					String diag1 = null;
					Date secondDate = null;
					String secondDiag = null;
					if (rs.next()) {
						firstDate = rs.getDate("date");
						diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>",
								" ");// 十二分级中的第一级
						diag1 = diag1.replaceAll("[\\n|\\r]", " ");
					}
					String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
							+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
							+ pati.getId() + " order by hlg.id desc";
					ps = conn.prepareStatement(memSql);
					rs = ps.executeQuery();
					if (rs.next()) {
						secondDate = rs.getDate("date");
						secondDiag = rs.getString("diag1").replaceAll(
								"<[.[^<]]*>", " ");// 十二分级中的第一级
						secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
					}
					if (firstDate != null && secondDate != null) {
						if (firstDate.getTime() > secondDate.getTime()) {
							mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
						} else {
							mp.put("jiBingGrounp", secondDiag); // 借用patient表中的字段，暂时显示当前疾病分
						}
					} else if (firstDate != null) {
						mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else if (secondDate != null) {
						mp.put("jiBingGrounp", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("jiBingGrounp", "");// 借用patient表中的字段，暂时显示当前疾病分
					}

					if (rf.getIsFromOutHospital() != null) {
						mp.put("isFromOutHospital", rf.getIsFromOutHospital()
								.toString());
					} else {
						mp.put("isFromOutHospital", 0);
					}
					if (rf.getGuiDangDate() != null) {
						mp.put("guiDangDate", rf.getGuiDangDate()
								.toLocaleString());
					} else {
						mp.put("guiDangDate", "");
					}
					if (rf.getSheHeDate() != null) {
						mp.put("sheHeDate", rf.getSheHeDate().toLocaleString());
					} else {
						mp.put("sheHeDate", "");
					}
					String isOrNotUpdateHql = "from UpdateReportFormForRemoteRecord where itemId="+rf.getId();
					List isOrNotUpdateList = session.createQuery(isOrNotUpdateHql).list();
					if(isOrNotUpdateList != null && isOrNotUpdateList.size() > 0){
						mp.put("isOrNotUpdate", "查看");
					}else{
						mp.put("isOrNotUpdate", "");
					}
					reportResult.add(mp);
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	public List<Map> getMyGrounpCheckReportsForByMemberGuanLiYuanAllMaiDi(
			String deptCode, String search) {
		Session session = null;
		Transaction tran = null;
		StringBuffer sb = new StringBuffer();
		String retStr = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List reportResult = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();

			StringBuilder sbl = null;
			
				String memberInfoHql = "select pa.patientId from MemberInfo mem left join t_PlanCount pc on mem.patient= pc.patientId ";
				// liugang 2011-09-14 update 添加病人姓名查询
				memberInfoHql += " inner join t_patient pa on pa.id = mem.patient  where mem.deptCode='"
						+ deptCode + "'";
				List list = session.createSQLQuery(memberInfoHql).list();
				if (list != null && list.size() > 0) {
					sbl = new StringBuilder();
					for (int si = 0, size = list.size(); si < size; si++) {
						if(list.get(si) != null){
							String objS = list.get(si).toString();
							if (si < size - 1) {
								sbl.append("'" + objS + "',");
							} else {
								sbl.append("'" + objS + "'");
							}
						}
					}
				}
			
			if (sbl != null) {
				conn = DatabaseUtil.getConnection();
				String reportHql = "from Maidi where patientId in ("
								+ sbl.toString()
								+ ") and"
								+ " isFromOutHospital =1 ";
				boolean flag = true;
				if (search != null && search.length() > 0) {
					JSONObject obj = JSONObject.fromObject(search);
					if(obj.containsKey("luRuAuthor")){
						if (obj.getString("luRuAuthor").length() > 0 && !"".equals(obj.getString("luRuAuthor"))){
							reportHql += " and luRuAuthor like '%"+obj.getString("luRuAuthor")+"%' ";
						}
					}
					if(obj.containsKey("duiDangAuthor")){
						if (obj.getString("duiDangAuthor").length() > 0 && !"".equals(obj.getString("duiDangAuthor"))){
							reportHql += " and duiDangAuthor like '%"+obj.getString("duiDangAuthor")+"%' ";
						}
					}
					if (obj.containsKey("tijiao")) {
						if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"是".equals(obj.getString("tijiao"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 11";
							flag = false;
						}else if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"否".equals(obj.getString("tijiao"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 10";
							flag = false;
						}
					}else{
						flag = true;
					}
					if (obj.containsKey("guidang")) {
						if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"是".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 21";
							flag = false;
						}else if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"否".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 20";
							flag = false;
						}
					}else{
						flag = true;
					}
					if (obj.containsKey("luRuDate")) {
						String value = obj.getString("luRuDate");
						if (value.length() > 0) {
							String[] temp = value.split(" ");
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd");
							Date[] d = { null, null };
							if (temp.length == 1) {
								Date t = format.parse(temp[0]);
								d[0] = t;
								d[1] = new Date(
										t.getTime() + 3600 * 24 * 1000);
							} else if (temp.length == 2) {
								d[0] = format.parse(temp[0]);
								d[1] = format.parse(temp[1]);
							}
							if (d[0] != null && d[1] != null) {
								reportHql += " and luRuDate between '"
										+ d[0].toLocaleString()
										+ "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
					if (obj.containsKey("guidangDate")) {
						String value = obj.getString("guidangDate");
						if (value.length() > 0) {
							String[] temp = value.split(" ");
							DateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd");
							Date[] d = { null, null };
							if (temp.length == 1) {
								Date t = format.parse(temp[0]);
								d[0] = t;
								d[1] = new Date(
										t.getTime() + 3600 * 24 * 1000);
							} else if (temp.length == 2) {
								d[0] = format.parse(temp[0]);
								d[1] = format.parse(temp[1]);
							}
							if (d[0] != null && d[1] != null) {
								reportHql += " and guiDangDate between '"
										+ d[0].toLocaleString()
										+ "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
				}
				if(flag){
					reportHql+=" and isPatientOrDoctorWriteZanCun in (10,11,20,21)";
				}
				reportHql+=" order by luRuDate desc";
				Query query = session
						.createQuery(reportHql);
				List<Maidi> rflist = query.list();
				// 根据ReportForm表查出的每一条记录循环查询ReportItem表
				for (int i = 0; i < rflist.size(); i++) {
					Maidi rf = (Maidi) rflist.get(i);
					Map mp = new HashMap();
					String pat = "from Patient where patientId ='"
							+ rf.getPatientId() + "'";
					Patient pati = (Patient) session.createQuery(pat).list()
							.get(0);
					mp.put("id",rf.getId());
					mp.put("patientName", pati.getPatientName());
					mp.put("patientNo", pati.getPatientNo());
					mp.put("patientId", pati.getId().toString());
					mp.put("checkDate", rf.getCheckDate());
					mp.put("checkItem", rf.getCheckItem());
					mp.put("luRuAuthor", rf.getLuRuAuthor());
					mp.put("check_danwei", rf.getCheck_danwei());
					mp.put("isPatientOrDoctorWriteZanCun",rf.getIsPatientOrDoctorWriteZanCun());
					if (rf.getLuRuDate() != null) {
						mp.put("luRuDate", rf.getLuRuDate().toLocaleString());
					} else {
						mp.put("luRuDate", "");
					}
					// liugang 2011-08-06 修改 start
					String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
							+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
							+ " where hsp.patientId = "
							+ pati.getId()
							+ " order by hlg.id desc";
					ps = conn.prepareStatement(curSql);
					rs = ps.executeQuery();
					Date firstDate = null;
					String diag1 = null;
					Date secondDate = null;
					String secondDiag = null;
					if (rs.next()) {
						firstDate = rs.getDate("date");
						diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>",
								" ");// 十二分级中的第一级
						diag1 = diag1.replaceAll("[\\n|\\r]", " ");
					}
					String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
							+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
							+ pati.getId() + " order by hlg.id desc";
					ps = conn.prepareStatement(memSql);
					rs = ps.executeQuery();
					if (rs.next()) {
						secondDate = rs.getDate("date");
						secondDiag = rs.getString("diag1").replaceAll(
								"<[.[^<]]*>", " ");// 十二分级中的第一级
						secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
					}
					if (firstDate != null && secondDate != null) {
						if (firstDate.getTime() > secondDate.getTime()) {
							mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
						} else {
							mp.put("jiBingGrounp", secondDiag); // 借用patient表中的字段，暂时显示当前疾病分
						}
					} else if (firstDate != null) {
						mp.put("jiBingGrounp", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else if (secondDate != null) {
						mp.put("jiBingGrounp", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("jiBingGrounp", "");// 借用patient表中的字段，暂时显示当前疾病分
					}

					if (rf.getIsFromOutHospital() != null) {
						mp.put("isFromOutHospital", rf.getIsFromOutHospital()
								.toString());
					} else {
						mp.put("isFromOutHospital", 0);
					}
					if (rf.getGuiDangDate() != null) {
						mp.put("guiDangDate", rf.getGuiDangDate()
								.toLocaleString());
					} else {
						mp.put("guiDangDate", "");
					}
					if (rf.getSheHeDate() != null) {
						mp.put("sheHeDate", rf.getSheHeDate().toLocaleString());
					} else {
						mp.put("sheHeDate", "");
					}
					String isOrNotUpdateHql = "from UpdateReportFormForRemoteRecord where itemId="+rf.getId();
					List isOrNotUpdateList = session.createQuery(isOrNotUpdateHql).list();
					if(isOrNotUpdateList != null && isOrNotUpdateList.size() > 0){
						mp.put("isOrNotUpdate", "查看");
					}else{
						mp.put("isOrNotUpdate", "");
					}
					reportResult.add(mp);
					
				}
				tran.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	public void shenHeNotMaiDiCheckReport(Long pacsId,Long patientId,Long doctorId) {
		Session session = null;
		Transaction tran = null;
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String updateHql="update Maidi set isPatientOrDoctorWriteZanCun=10,sheHeDate = NULL where id="+pacsId;
			session.createSQLQuery(updateHql).executeUpdate();
			User user = (User)session.get(User.class, doctorId);
			Maidi maidi = (Maidi)session.get(Maidi.class, pacsId); 
			MemberInfo mem = (MemberInfo)session.createQuery("from MemberInfo where patient="+patientId).list().get(0);
			Sms sms = new Sms();
			Message message = new Message();    //Message object;
			sms.phone = mem.getPatient().getMobilePhone();
			String msgBuffer ="患者您好，您于"+sdf.format(maidi.getLuRuDate())+"录入的"+maidi.getCheckItem()+"化验结果可能录入有误，请您认真核对后进行修改。北京佑安医院";
			sms.content = msgBuffer;
			SmsOperator smsOp = SmsOperator.getInstance();
			smsOp.sendSms("101100-SJB-HUAX-566466", "ALQPPJJI",new Sms[] { sms });
			SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			message.setMessageContent(msgBuffer); //set message.messageContent;
			message.setMessageDate(new Date());        //set message.messageDate;
			message.setMemberInfo(mem);             //set message.memberInfo;
			message.setUser(user);
			messDao.addMessage(message,session);
			tran.commit();
		}catch(Exception e){
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{			
			DatabaseUtil.closeResource(session);			
		}
	}
}
