package com.juncsoft.KLJY.patientanddoctoroperator.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction; //liugang 2011-08-06 start
import com.juncsoft.KLJY.biomedical.entity.MemberInfo; //liugang 2011-08-06 end
import com.juncsoft.KLJY.patientanddoctoroperator.dao.DoctorRoundsRecordDao;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.DoctorRoundsRecord;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientHealthRecord;
import com.juncsoft.KLJY.util.BaseService;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class DoctorRoundsRecordImpl extends BaseService implements
		DoctorRoundsRecordDao {

	public List<DoctorRoundsRecord> findAllDoctorRoundsRecordByPatientId(
			Long patientId) {
		return null;
	}

	public Map findDoctorRoundsRecordCountByPatientId(Long patientId,
			Long doctorId) {
		// liugang 2011-08-06 start
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sqlDept = "select td.deptCode,td.grounpId from t_DepartmentAndGrounpMiddle td inner join t_MemberGrounpAndDoctor tm on td.grounpId = tm.grounpId and doctorId ="
					+ doctorId;
			List deptList = session.createSQLQuery(sqlDept).list();
			if (deptList != null && deptList.size() > 0) {
				Object[] obj = (Object[]) deptList.get(0);
				String doctorHql = "from DoctorRoundsRecord where patientId ="
						+ patientId + " and deptCode ='" + obj[0]
						+ "' and grounpId = " + obj[1];
				List doctorList = session.createQuery(doctorHql).list();
				if (doctorList != null && doctorList.size() > 0) {
					mp.put("doctorListCount", doctorList.size());
				} else {
					mp.put("doctorListCount", 0);
				}
			}
			tran.commit();
			// liugang 2011-08-06 end
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	public DoctorRoundsRecord saveDoctorRoundsRecord(
			DoctorRoundsRecord doctorRoundsRecord) {
		Session session = null;
		Transaction tran = null;
		Connection con = null;
		Statement stmt = null;
		try {
			session = DatabaseUtil.getSession();
			con = DatabaseUtil.getConnection();
			stmt = con.createStatement();
			tran = session.beginTransaction();
			// liugang 2011-08-06 start
			String sqlDept = "select td.deptCode,td.grounpId from t_DepartmentAndGrounpMiddle td inner join t_MemberGrounpAndDoctor tm on td.grounpId = tm.grounpId and tm.doctorId ="
					+ doctorRoundsRecord.getDoctorId();
			List list = session.createSQLQuery(sqlDept).list();
			if (list != null && list.size() > 0) {
				Object[] obj = (Object[]) list.get(0);// 一个医生一个责任科室，独立的一个责任小组
				doctorRoundsRecord.setDeptCode(obj[0].toString());
				doctorRoundsRecord.setGrounpId(Long
						.parseLong(obj[1].toString()));
				long docId = Long.parseLong(session.save(doctorRoundsRecord)
						.toString());
				doctorRoundsRecord.setId(docId);
				String sql = "update t_PatientHealthRecord set typeFlag ="
						+ docId + " where typeFlag is null and patientId="
						+ doctorRoundsRecord.getPatientId() + " and deptCode='"
						+ obj[0] + "' and grounpId =" + obj[1];
				stmt.execute(sql);
			}
			// liugang 2011-08-06 end
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("保存医生查房记录失败", e);
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(con, stmt, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return doctorRoundsRecord;
	}

	public void updateDoctorRoundsRecord(DoctorRoundsRecord doctorRoundsRecord) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sqlDept = "select td.deptCode,td.grounpId from t_DepartmentAndGrounpMiddle td inner join t_MemberGrounpAndDoctor tm on td.grounpId = tm.grounpId and tm.doctorId ="
				+ doctorRoundsRecord.getDoctorId();
			List list = session.createSQLQuery(sqlDept).list();
			if (list != null && list.size() > 0) {
				Object[] obj = (Object[]) list.get(0);// 一个医生一个责任科室，独立的一个责任小组
				doctorRoundsRecord.setDeptCode(obj[0].toString());
				doctorRoundsRecord.setGrounpId(Long.parseLong(obj[1].toString()));
			}
			session.update(doctorRoundsRecord);
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
	 * 根据ID查找医生的查房记录
	 */
	public DoctorRoundsRecord findDoctorRoundsRecordById(Long id) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			DoctorRoundsRecord doctorRoundsRecord = (DoctorRoundsRecord) session
					.get(DoctorRoundsRecord.class, id);
			tran.commit();
			return doctorRoundsRecord;
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("根据ID查找医生的查房记录", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public JSONArray doctorRoundsRecord_treeNodes(Long patientId,
			Date currentDate, int weekFlag, Long doctorId) {
		JSONArray array = new JSONArray();
		// liugang 2011-08-06 start
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String memberHql = "select td.deptCode,td.grounpId from t_DepartmentAndGrounpMiddle td inner join t_MemberGrounpAndDoctor tm on td.grounpId = tm.grounpId and tm.doctorId= "
					+ doctorId;
			List memList = session.createSQLQuery(memberHql).list();
			if (memList != null && memList.size() > 0) {
				Object[] obj = (Object[]) memList.get(0);
				String hql = "from DoctorRoundsRecord where patientId ="
						+ patientId + " and deptCode='" + obj[0]
						+ "' and grounpId =" + obj[1];
//				if (currentDate == null) {
//					Date date = new Date();
//					date.setDate(date.getDate() - 7);
//					hql += " and roundsDate>= '" + date.toLocaleString() + "'";
//				} else {
//					Date date = currentDate;
//					String dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//							.format(date);
//					if (weekFlag == 0) {
//						date.setDate(date.getDate() - 7);
//						hql += " and roundsDate>= '" + date.toLocaleString()
//								+ "' and roundsDate<= '" + dates + "'";
//					} else {
//						date.setDate(date.getDate() + 7);
//						hql += " and roundsDate<= '" + date.toLocaleString()
//								+ "' and roundsDate >='" + dates + "'";
//					}
//				}
				List<DoctorRoundsRecord> list = session.createQuery(
						hql + " order by roundsDate asc").list();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				// int i=0;
				for (int i = 0, sizei = list.size(); i < sizei; i++) {
					DoctorRoundsRecord record = list.get(i);
					JSONObject object = new JSONObject();
					object.put("id", record.getId());// 显示顺序
					if (record.getRoundsDate() != null) {// 时间标题
						object.put("text", (i + 1) + "、"
								+ sdf.format(record.getRoundsDate())
								+ record.getRoundsTitle());
					} else {
						object.put("text", "The time is not define");
					}
					object.put("leaf", true);
					object.put("iconCls", "icon-none");
					object.put("href", "javascript:scrollToDoctor(" + (i + 1)
							+ ")");
					array.add(object);
				}
			}
			tran.commit();
			// liugang 2011-08-06 end
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return array;
	}

	/**
	 * liugang 2011-08-06 start
	 */
	public Map findDoctorRoundsRecordCountByMember(MemberInfo mem) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String doctorHql = "from DoctorRoundsRecord where patientId ="
					+ mem.getPatient().getId() + " and deptCode ='"
					+ mem.getDeptCode() + "' and grounpId = "
					+ mem.getGrounpId();
			List doctorList = session.createQuery(doctorHql).list();
			if (doctorList != null && doctorList.size() > 0) {
				mp.put("doctorListCount", doctorList.size());
			} else {
				mp.put("doctorListCount", 0);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	public JSONArray doctorRoundsRecord_treeNodesPatient(Long patientId,
			Date currentDate, int weekFlag) {
		JSONArray array = new JSONArray();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from DoctorRoundsRecord where patientId ="
					+ patientId;
//			if (currentDate == null) {
//				Date date = new Date();
//				date.setDate(date.getDate() - 7);
//				hql += " and roundsDate>= '" + date.toLocaleString() + "'";
//			} else {
//				Date date = currentDate;
//				String dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//						.format(date);
//				if (weekFlag == 0) {
//					date.setDate(date.getDate() - 7);
//					hql += " and roundsDate>= '" + date.toLocaleString()
//							+ "' and roundsDate<= '" + dates + "'";
//				} else {
//					date.setDate(date.getDate() + 7);
//					hql += " and roundsDate<= '" + date.toLocaleString()
//							+ "' and roundsDate >='" + dates + "'";
//				}
//			}
			List<DoctorRoundsRecord> list = session.createQuery(
					hql + " order by roundsDate asc").list();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// int i=0;
			for (int i = 0, sizei = list.size(); i < sizei; i++) {
				DoctorRoundsRecord record = list.get(i);
				JSONObject object = new JSONObject();
				object.put("id", record.getId());// 显示顺序
				if (record.getRoundsDate() != null) {// 时间标题
					object.put("text", (i + 1) + "、"
							+ sdf.format(record.getRoundsDate())
							+ record.getRoundsTitle());
				} else {
					object.put("text", "The time is not define");
				}
				object.put("leaf", true);
				object.put("iconCls", "icon-none");
				object
						.put("href", "javascript:scrollToDoctor(" + (i + 1)
								+ ")");
				array.add(object);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return array;
	}
	// liugang 2011-08-06 start
}
