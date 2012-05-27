package com.juncsoft.KLJY.outoremergency.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.outoremergency.dao.OutOrMergencyPatientDao;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyCase;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyMiddlePatient;
import com.juncsoft.KLJY.outoremergency.entity.YiZhou;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class OutOrMergencyPatientDaoImpl implements OutOrMergencyPatientDao {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public JSONObject getOutOrMergencyMiddlePatientList(Integer start,
			Integer limit, String deptcode, String drcode, String gbrep,
			String yizhuDate) {
		Connection con = null;// HIS数据连结
		CallableStatement cstmt = null;
		JSONObject json = new JSONObject();
		List<OutOrMergencyMiddlePatient> list = new ArrayList<OutOrMergencyMiddlePatient>();
		ResultSet rs = null;
		int total = 0;
		try {
			con = DatabaseUtil.getConnetion_His();// 获取连接HIS的数据连接
			String procSql = "{call tw_hsp_pmpa.EMR.outptinfo(?,?,?,?)}";
			cstmt = con.prepareCall(procSql);
			cstmt.setString(1, deptcode);
			cstmt.setString(2, drcode);
			// 获取当天凌晨的时间
			// sdf.format(new Date()).toString()+
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cstmt.setString(3, yizhuDate + " 00:00:00");
			cstmt.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(4);
			while (rs.next()) {
				++total;
				OutOrMergencyMiddlePatient outOrMergencyMiddlePatient = new OutOrMergencyMiddlePatient();
				outOrMergencyMiddlePatient.setPtno(rs.getString("ptno"));
				outOrMergencyMiddlePatient.setSname(rs.getString("sname"));
				outOrMergencyMiddlePatient.setSex(rs.getString("sex"));
				int interval = new Date().getYear()
						- sdf.parse(rs.getString("birthdate")).getYear();
				outOrMergencyMiddlePatient.setBirthdate(String
						.valueOf(interval));
				outOrMergencyMiddlePatient.setActdate(rs.getString("actdate"));
				outOrMergencyMiddlePatient
						.setDeptcode(rs.getString("deptcode"));
				outOrMergencyMiddlePatient
						.setDeptname(rs.getString("deptname"));
				outOrMergencyMiddlePatient.setDrcode(rs.getString("drcode"));
				outOrMergencyMiddlePatient.setDrname(rs.getString("drname"));
				outOrMergencyMiddlePatient.setBi(rs.getString("bi"));
				outOrMergencyMiddlePatient
						.setGbchojae(rs.getString("gbchojae"));
				outOrMergencyMiddlePatient.setGbrep(rs.getString("gbrep"));
				outOrMergencyMiddlePatient.setJtime(rs.getString("jtime")
						.substring(0, rs.getString("jtime").length() - 2));
				outOrMergencyMiddlePatient.setRestype(rs.getString("restype"));
				outOrMergencyMiddlePatient.setTel(rs.getString("tel"));
				outOrMergencyMiddlePatient
						.setLastdept(rs.getString("lastdept"));
				outOrMergencyMiddlePatient.setLastdeptname(rs
						.getString("lastdeptname"));
				outOrMergencyMiddlePatient.setLastdate(rs.getDate("lastdate")
						.toString());

				outOrMergencyMiddlePatient.setAmpm(rs.getString("ampm"));
				outOrMergencyMiddlePatient.setJinilsu(rs.getString("jinilsu"));
				outOrMergencyMiddlePatient.setRegno(rs.getString("regno"));// 门诊流水号
				if (" ".equals(rs.getString("delmark"))) {
					outOrMergencyMiddlePatient.setDelmark("正常");
					if (rs.getString(12).equals(gbrep)) {// 加载已诊疗和未诊疗的患者
						list.add(outOrMergencyMiddlePatient);
					}
				} else {
					outOrMergencyMiddlePatient.setDelmark("退号");
				}
			}
			json.put("root", list);
			json.put("total", list.size());
			json.put("allTotal", total);
			return json;
		} catch (Exception e) {
			throw new RuntimeException("执行加载当前医生所在的一个科室下的所有当天的门诊病人存储过程出错", e);
		} finally {
			try {
				DatabaseUtil.closeResource(con, cstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 查找HIS中的病人信息
	public Patient executeHISPatientNameByIdnumber(String idnumber) {
		Connection con = null;
		CallableStatement cstmt = null;
		String patientName = null;
		ResultSet rs = null;
		Session session = null;
		Patient patient = null;
		try {
			con = DatabaseUtil.getConnetion_His();// 获取连接HIS的数据连接
			String procSql = "{call tw_hsp_pmpa.emr.ptinfo(?,?)}";
			cstmt = con.prepareCall(procSql);
			cstmt.setString(1, idnumber);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(2);
			while (rs.next()) {
				patientName = rs.getString(5);
			}
			session = DatabaseUtil.getSession();
			String hql = "from Patient where patientName='" + patientName + "'";
			List list = session.createQuery(hql).list();
			if (list != null && list.size() > 0) {
				patient = (Patient) list.get(0);
			}
			return patient;
		} catch (Exception e) {
			throw new RuntimeException("执行查找病人存储过程出错", e);
		} finally {
			try {
				if (session != null) {
					DatabaseUtil.closeResource(session);
				}
				DatabaseUtil.closeResource(con, cstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 执行单条病人存储过程(门诊)
	public String executeHISPatientByHISPatientid(String patientid,
			String actdate, String regno, String deptcode, String deptname) {
		String patientId = null;
		Connection connD = null;// 电子病历数据连结
		Statement sm = null;
		ResultSet rs = null;
		CallableStatement cstmt = null;
		Session session = null;
		Transaction tran = null;
		try {
			connD = DatabaseUtil.getConnection();
			String sql = "select * from t_Patient where patientId = '"
					+ patientid + "'";// 争取设置为主键
			sm = connD.createStatement();
			rs = sm.executeQuery(sql);
			Patient patient = new Patient();
			// MemberInfo mi = new MemberInfo();
			if (!rs.next()) {
				connD = DatabaseUtil.getConnetion_His();
				String procPatientSql = "{call tw_hsp_pmpa.emr.ptinfo(?,?)}";
				cstmt = connD.prepareCall(procPatientSql);
				cstmt.setString(1, patientid);// 患者编号
				cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
				cstmt.execute();
				// rs = null;//初始化ResultSet
				rs = (ResultSet) cstmt.getObject(2);
				if (rs.next()) {
					patient.setPatientId(rs.getString("patientid"));// 患者编号
					if (rs.getString("patientno") != null) {// 数据库中设置不能为空
						patient.setPatientNo(rs.getString("patientno"));
					} else {
						patient.setPatientNo("");// 门诊中的病案号可能为空
					}
					if (rs.getString("patientname") != null) {
						patient.setPatientName(rs.getString("patientname"));
					} else {
						patient.setPatientName("");// 门诊中的患者姓名肯定不为空
					}
					// 2011-08-29 liugang start
					if ("2".equals(rs.getString("sexid"))) {// 处理HIS与电子病历性别的同步问题his中1代表女，2代表男
						patient.setGender("1");// 1代表男性，2代表女性(电子病历中)
					} else if ("1".equals(rs.getString("sexid"))) {
						patient.setGender("2");// 1代表男性，2代表女性
					}
					// 2011-08-29 liugang end
					patient.setBirthday(sdf.parse(rs.getString("birthday")));
					patient.setIdType(rs.getString("ZJtype"));
					patient.setIdNo(rs.getString("societyno"));
					patient.setNationality(rs.getString("nationalityid"));
					patient.setPeople(rs.getString("peopleid"));
					patient.setHomeAddr(rs.getString("homeaddr"));
					patient.setHomeTel(rs.getString("hometel"));
					patient.setHomePostCode(rs.getString("homepostcode"));
					patient.setWorkUnit(rs.getString("workorg"));
					patient.setWorkUnitAddr(rs.getString("orgaddr"));
					patient.setWorkUnitPostCode(rs.getString("orgpostcode"));
					patient.setContacterName(rs.getString("contactname"));
					patient.setContacterTel(rs.getString("contacttel"));
					patient.setCreateDate(new Date());
					patient.setOutDeptCode(deptcode);
				}
				if (patient != null) {
					session = DatabaseUtil.getSession();// 保存患者信息到电子病历数据库中
					tran = session.beginTransaction();
					patientId = session.save(patient).toString();
//					this.executeMemberInfo(connD, patientId, sm, rs, patient,
//							session, tran, actdate, regno, deptcode, deptname);
				}
			} else {
				patientId = rs.getString("id");
				patient.setId(Long.parseLong(patientId));
				session = DatabaseUtil.getSession();// 保存患者信息到电子病历数据库中
				tran = session.beginTransaction();
				// 维护门诊病人信息
				patient = (Patient) session.get(Patient.class, Long
						.parseLong(patientId));
				patient.setOutDeptCode(deptcode);
				session.save(patient);
//				this.executeMemberInfo(connD, patientId, sm, rs, patient,
//						session, tran, actdate, regno, deptcode, deptname);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("执行单条病人存储过程(门诊)出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				if (cstmt != null) {
					cstmt.close();
				}
				DatabaseUtil.closeResource(connD, sm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return patientId;
	}

	// 判断会员表中的信息然后自动生成门诊记录
	private void executeMemberInfo(Connection connD, String patientId,
			Statement sm, ResultSet rs, Patient patient, Session session,
			Transaction tran, String actdate, String regno, String deptcode,
			String deptname) throws Exception {
		String sqlMemberInfo = "select * from MemberInfo where patient ="
				+ patientId;
		connD = DatabaseUtil.getConnection();
		sm = connD.createStatement();
		rs = sm.executeQuery(sqlMemberInfo);
		OutOrMergencyCase outOrMergencyCase = new OutOrMergencyCase();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int outType = 0;// 门诊类型(非会员门诊)
		if (rs.next()) {
			if ("普通会员".equals(rs.getString("memberType"))
					|| "VIP会员".equals(rs.getString("memberType"))) {
				outType = 1;// 门诊类型(会员门诊)
			}
		}
		String nextOutOrMergencyCaseSql = "select * from t_OutOrMergencyCase where patientId='"
				+ patientId + "' and outRegno='" + regno + "'";
		rs = sm.executeQuery(nextOutOrMergencyCaseSql);
		if (!rs.next()) {
			// String outOrMergencyCaseSql1 = "select * from t_OutOrMergencyCase
			// where patientId='"+patientId+"' and actdate='"+actdate+"' order
			// by id desc";
			// rs1 = sm.executeQuery(outOrMergencyCaseSql1);
			// if(!rs1.next()){
			// String nextOutOrMergencyCaseSql2 = "select * from
			// t_OutOrMergencyCase where patientId='"+patientId+"' and actdate <
			// '"+actdate+"' order by id desc";
			// rs2 = sm.executeQuery(nextOutOrMergencyCaseSql2);
			// if(rs2.next()){
			// outOrMergencyCase.setOutCount(rs2.getInt("outCount")+1);
			// outOrMergencyCase.setOutType(outType);
			// }
			// if(rs.getDate("actdate").compareTo(sdf.parse(actdate)) >
			// 0){//弥补某一天的门诊病历
			// String updateSql = "update t_OutOrMergencyCase set
			// outCount="+rs.getInt("outCount")+1+" where
			// patientId='"+patientId+"' and actdate > '"+actdate+"'";
			// sm.executeUpdate(updateSql);
			// }
			outOrMergencyCase.setPatientId(patientId);
			outOrMergencyCase.setOutType(outType);// 门诊类型(非会员门诊)
			// outOrMergencyCase.setOutTime(new Date());
			// 门诊时间初始化数值取HIS中的挂号时间
			outOrMergencyCase.setOutTime(sdf.parse(actdate));
			outOrMergencyCase.setActdate(sdf.parse(actdate));
			outOrMergencyCase.setOutWriteTime(sdf.parse(actdate));
			outOrMergencyCase.setOutRegno(regno);
			outOrMergencyCase.setOutDeptcode(deptcode);
			outOrMergencyCase.setOutDeptname(deptname);
			session.save(outOrMergencyCase);
		}
		tran.commit();
	}

	// public static void main(String[] args) {
	// OutOrMergencyPatientDaoImpl tt = new OutOrMergencyPatientDaoImpl();
	// tt.executeSql();
	// }

	/**
	 * 单表头信息加载门诊信息
	 */
	public String getOutOrMergencyPatientPageInfo(String patientId) {
		return null;
	}

	public void executeSql() {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "select id from t_patient where patientId is null";
			List list = session.createSQLQuery(hql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					Patient p = (Patient) session.get(Patient.class, Long
							.parseLong(list.get(i).toString()));
					String sql = "select PATIENTID from t_patient_his where id="
							+ list.get(i);
					List listSql = session.createSQLQuery(sql).list();
					if (listSql != null && listSql.size() > 0) {
						p.setPatientId(listSql.get(0).toString());
						session.update(p);
					}
				}
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

	// 查找某科的所有门诊病人
	public JSONObject findAllOutPatients(String deptCode, Integer start,
			Integer limit) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		List<Patient> list = new ArrayList<Patient>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from Patient where outDeptCode is not null order by modifyDate desc";
			list = session.createQuery(hql).setFirstResult(start)
					.setMaxResults(limit).list();
			JSONArray root = new JSONArray();
			for (Patient pat : list) {
				root.add(initPatientValue(pat));
			}
			json.put("root", root);
			long total = (Long) session
					.createQuery(
							"select count(*) from Patient where outDeptCode is not null")
					.list().iterator().next();
			json.put("total", total);
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查找某科的所有门诊病人出错", e);
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
			if (pat.getGender() != null && "1".equals(pat.getGender())) {
				json.put("sex0", "男");
			} else if (pat.getGender() != null && "2".equals(pat.getGender())) {
				json.put("sex0", "女");
			}
			// json.put("sex0", DictionaryUtil.getIndependentDictionaryText(
			// "gender_gb", pat.getGender()));
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
			String hql = "from Patient where outDeptCode is not null and patientName like '"
					+ keyword + "' or patientNo like '" + keyword + "'";
			List<Patient> list = session.createQuery(hql).setFirstResult(start)
					.setMaxResults(limit).list();
			long total = (Long) session
					.createQuery(
							"select count(*) from Patient where outDeptCode is not null and patientName like ? or patientNo like ?")
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

	public void executeHISDoctorByIdnumberAndPassword(String idnumber) {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Session session = null;
		Transaction tran = null;
		try {
			con = DatabaseUtil.getConnetion_His();// 获取连接HIS的数据连接
			String procSql = "{call tw_hsp_pmpa.emr.ptinfo(?,?)}";
			cstmt = con.prepareCall(procSql);
			cstmt.setString(1, idnumber);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(2);
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			while (rs.next()) {
				// Patient patient = new Patient();
				// patient.setPatientId(rs.getString("patientid"));// 患者编号
				// if (rs.getString("patientno") != null) {// 数据库中设置不能为空
				// patient.setPatientNo(rs.getString("patientno"));
				// } else {
				// patient.setPatientNo("");// 门诊中的病案号可能为空
				// }
				// if (rs.getString("patientname") != null) {
				// patient.setPatientName(rs.getString("patientname"));
				// } else {
				// patient.setPatientName("");// 门诊中的患者姓名肯定不为空
				// }
				//
				// patient.setGender(rs.getString("sexid"));
				// patient.setBirthday(sdf.parse(rs.getString("birthday")));
				// patient.setIdType(rs.getString("ZJtype"));
				// patient.setIdNo(rs.getString("societyno"));
				// patient.setNationality(rs.getString("nationalityid"));
				// patient.setPeople(rs.getString("peopleid"));
				// patient.setHomeAddr(rs.getString("homeaddr"));
				// patient.setHomeTel(rs.getString("hometel"));
				// patient.setHomePostCode(rs.getString("homepostcode"));
				// patient.setWorkUnit(rs.getString("workorg"));
				// patient.setWorkUnitAddr(rs.getString("orgaddr"));
				// patient.setWorkUnitPostCode(rs.getString("orgpostcode"));
				// patient.setContacterName(rs.getString("contactname"));
				// patient.setContacterTel(rs.getString("contacttel"));
				// patient.setCreateDate(new Date());
				// session.save(patient);
				// tran.commit();
				System.out.println(rs.getString("patientid"));
				System.out.println(rs.getString("patientname"));
				System.out.println(rs.getString("sexid"));
			}
		} catch (Exception e) {
			throw new RuntimeException("执行查找医生当前科室存储过程出错", e);
		} finally {
			try {
				DatabaseUtil.closeResource(con, cstmt, rs);
				DatabaseUtil.closeResource(session);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// public static void main(String[] args){
	// OutOrMergencyPatientDaoImpl t = new OutOrMergencyPatientDaoImpl();
	// t.executeHISDoctorByIdnumberAndPassword("0000180457");
	// }

	public OutOrMergencyCase findById(Long id) throws Exception {
		OutOrMergencyCase mc = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			mc = (OutOrMergencyCase) session.get(OutOrMergencyCase.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mc;
	}

	// liugang 2011-08-06新增 //患者编号、挂号日期、患者姓名、证件号、jumin:证件编号,jtype：证件类型
	public List<OutOrMergencyCase> executeOnePatientOutCase(Long id,
			String patientid) {
//		Connection con = null;// HIS数据连结
//		CallableStatement cstmt = null;
//		ResultSet rs = null;
		Session session = null;
		Transaction tran = null;
		List<OutOrMergencyCase> list = null;
//		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
//			con = DatabaseUtil.getConnetion_His();// DatabaseUtil.getConnetion_His();//
//			// 获取连接HIS的数据连接
//			if (con != null) {
//				String procSql = "{call tw_hsp_pmpa.EMR.outptinfoall(?,?,?,?,?)}";
//				cstmt = con.prepareCall(procSql);
//				cstmt.setString(1, patientid);
//				cstmt.setString(2, "");
//				cstmt.setString(3, "");
//				cstmt.setString(4, "");
//				cstmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR);
//				cstmt.execute();
//				rs = (ResultSet) cstmt.getObject(5);
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
//				while (rs.next()) {
//					if (" ".equals(rs.getString("delmark"))) {
//						String hql = "from OutOrMergencyCase where patientId='"
//								+ id + "' and actdate='" + rs.getString("jtime")
//								+ "'";
//						List oneOut = session.createQuery(hql).list();
//						if (oneOut != null && oneOut.size() > 0) {
//						} else {
//							OutOrMergencyCase outOrMergencyCase = new OutOrMergencyCase();
//							outOrMergencyCase.setActdate(sd.parse(rs.getString("jtime")));
//							outOrMergencyCase.setOutTime(sd.parse(rs.getString("jtime")));
//							outOrMergencyCase.setOutDeptcode(rs
//									.getString("deptcode"));
//							outOrMergencyCase.setOutDeptname(rs
//									.getString("deptname"));
//							outOrMergencyCase.setPatientId(id + "");
//							outOrMergencyCase
//									.setOutRegno(rs.getString("regno"));
//							outOrMergencyCase.setOutType(1);
//							session.save(outOrMergencyCase);
//						}
//					}
//				}
//			}
//			tran.commit();
			String hqlAll = "from OutOrMergencyCase where patientId='" + id	+ "' order by actdate asc";
			list = session.createQuery(hqlAll).list();
			tran.commit();
		} catch (Exception e) {
			throw new RuntimeException("执行加载当前医生所在的一个科室下的所有当天的门诊病人存储过程出错", e);
		} finally {
			try {
//				DatabaseUtil.closeResource(con, cstmt, rs);
				DatabaseUtil.closeResource(session);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// liugang 2011-08-06新增 //患者编号、挂号日期、患者姓名、证件号、jumin:证件编号,jtype：证件类型
	public JSONObject executeOnePatientOutCaseListByHis(Integer start,
			Integer limit, String flag, String searchCondition) {
		Connection con = null;// HIS数据连结
		CallableStatement cstmt = null;
		ResultSet rs = null;
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<OutOrMergencyMiddlePatient> list = new ArrayList<OutOrMergencyMiddlePatient>();
		int total = 0;
		try {
			con = DatabaseUtil.getConnetion_His();// DatabaseUtil.getConnetion_His();//
			// 获取连接HIS的数据连接
			String procSql = "{call tw_hsp_pmpa.EMR.outptinfoall(?,?,?,?,?)}";
			cstmt = con.prepareCall(procSql);
			if("patientId".equals(flag)){
				cstmt.setString(1, searchCondition);
				cstmt.setString(2, "");
				cstmt.setString(3, "");
				cstmt.setString(4, "");
			}else if("guhaodate".equals(flag)){
				cstmt.setString(1, "");
				cstmt.setString(2, searchCondition);
				cstmt.setString(3, "");
				cstmt.setString(4, "");
			}else if("patientName".equals(flag)){
				cstmt.setString(1, "");
				cstmt.setString(2, "");
				cstmt.setString(3, searchCondition);
				cstmt.setString(4, "");
			}else if("zhengjianNum".equals(flag)){
				cstmt.setString(1, "");
				cstmt.setString(2, "");
				cstmt.setString(3, "");
				cstmt.setString(4, searchCondition);
			}
			cstmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR);
			if(!"".equals(searchCondition)){
				cstmt.execute();
				rs = (ResultSet) cstmt.getObject(5);
				while (rs.next()) {
					++total;
					OutOrMergencyMiddlePatient outOrMergencyMiddlePatient = new OutOrMergencyMiddlePatient();
					outOrMergencyMiddlePatient.setPtno(rs.getString("ptno"));
					outOrMergencyMiddlePatient.setSname(rs.getString("sname"));
					outOrMergencyMiddlePatient.setSex(rs.getString("sex"));
					int interval = new Date().getYear()
							- sdf.parse(rs.getString("birthdate")).getYear();
					outOrMergencyMiddlePatient.setBirthdate(String
							.valueOf(interval));
					outOrMergencyMiddlePatient.setActdate(rs.getString("actdate"));
					outOrMergencyMiddlePatient
							.setDeptcode(rs.getString("deptcode"));
					outOrMergencyMiddlePatient
							.setDeptname(rs.getString("deptname"));
					outOrMergencyMiddlePatient.setDrcode(rs.getString("drcode"));
					outOrMergencyMiddlePatient.setDrname(rs.getString("drname"));
					outOrMergencyMiddlePatient.setBi(rs.getString("bi"));
					outOrMergencyMiddlePatient
							.setGbchojae(rs.getString("gbchojae"));
					outOrMergencyMiddlePatient.setGbrep(rs.getString("gbrep"));
					outOrMergencyMiddlePatient.setJtime(rs.getString("jtime")
							.substring(0, rs.getString("jtime").length() - 2));
					outOrMergencyMiddlePatient.setRestype(rs.getString("restype"));
					outOrMergencyMiddlePatient.setTel(rs.getString("tel"));
					outOrMergencyMiddlePatient
							.setLastdept(rs.getString("lastdept"));
					outOrMergencyMiddlePatient.setLastdeptname(rs
							.getString("lastdeptname"));
					outOrMergencyMiddlePatient.setLastdate(rs.getDate("lastdate")
							.toString());

					outOrMergencyMiddlePatient.setAmpm(rs.getString("ampm"));
					outOrMergencyMiddlePatient.setJinilsu(rs.getString("jinilsu"));
					outOrMergencyMiddlePatient.setRegno(rs.getString("regno"));// 门诊流水号
					outOrMergencyMiddlePatient.setJumin(rs.getString("jumin"));//证件号
					outOrMergencyMiddlePatient.setJtype(rs.getString("zjtype"));//证件类型
					System.out.println(rs.getString("delmark"));
					if (" ".equals(rs.getString("delmark"))) {
						outOrMergencyMiddlePatient.setDelmark("正常");
						list.add(outOrMergencyMiddlePatient);
					} else {
						outOrMergencyMiddlePatient.setDelmark("退号");
					}
				}
			}
			json.put("root", list);
			json.put("total", list.size());
			json.put("allTotal", total);
			return json;
		} catch (Exception e) {
			throw new RuntimeException("执行加载当前医生所在的一个科室下的所有当天的门诊病人存储过程出错", e);
		} finally {
			try {
				DatabaseUtil.closeResource(con, cstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//	public String executeHISPatientByHISPatientidSearch(String patientid,
//			String actdate, String regno, String deptcode) {
//		String patientId = null;
//		Connection connD = null;// 电子病历数据连结
//		Statement sm = null;
//		ResultSet rs = null;
//		CallableStatement cstmt = null;
//		Session session = null;
//		Transaction tran = null;
//		try {
//			connD = DatabaseUtil.getConnection();
//			String sql = "select * from t_Patient where patientId = '"
//					+ patientid + "'";// 争取设置为主键
//			sm = connD.createStatement();
//			rs = sm.executeQuery(sql);
//			Patient patient = new Patient();
//			// MemberInfo mi = new MemberInfo();
//			if (!rs.next()) {
//				connD = DatabaseUtil.getConnetion_His();
//				String procPatientSql = "{call tw_hsp_pmpa.emr.ptinfo(?,?)}";
//				cstmt = connD.prepareCall(procPatientSql);
//				cstmt.setString(1, patientid);// 患者编号
//				cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
//				cstmt.execute();
//				// rs = null;//初始化ResultSet
//				rs = (ResultSet) cstmt.getObject(2);
//				if (rs.next()) {
//					patient.setPatientId(rs.getString("patientid"));// 患者编号
//					if (rs.getString("patientno") != null) {// 数据库中设置不能为空
//						patient.setPatientNo(rs.getString("patientno"));
//					} else {
//						patient.setPatientNo("");// 门诊中的病案号可能为空
//					}
//					if (rs.getString("patientname") != null) {
//						patient.setPatientName(rs.getString("patientname"));
//					} else {
//						patient.setPatientName("");// 门诊中的患者姓名肯定不为空
//					}
//					// 2011-08-29 liugang start
//					if ("2".equals(rs.getString("sexid"))) {// 处理HIS与电子病历性别的同步问题his中1代表女，2代表男
//						patient.setGender("1");// 1代表男性，2代表女性(电子病历中)
//					} else if ("1".equals(rs.getString("sexid"))) {
//						patient.setGender("2");// 1代表男性，2代表女性
//					}
//					// 2011-08-29 liugang end
//					patient.setBirthday(sdf.parse(rs.getString("birthday")));
//					patient.setIdType(rs.getString("ZJtype"));
//					patient.setIdNo(rs.getString("societyno"));
//					patient.setNationality(rs.getString("nationalityid"));
//					patient.setPeople(rs.getString("peopleid"));
//					patient.setHomeAddr(rs.getString("homeaddr"));
//					patient.setHomeTel(rs.getString("hometel"));
//					patient.setHomePostCode(rs.getString("homepostcode"));
//					patient.setWorkUnit(rs.getString("workorg"));
//					patient.setWorkUnitAddr(rs.getString("orgaddr"));
//					patient.setWorkUnitPostCode(rs.getString("orgpostcode"));
//					patient.setContacterName(rs.getString("contactname"));
//					patient.setContacterTel(rs.getString("contacttel"));
//					patient.setCreateDate(new Date());
//					patient.setOutDeptCode(deptcode);
//				}
//				if (patient != null) {
//					session = DatabaseUtil.getSession();// 保存患者信息到电子病历数据库中
//					tran = session.beginTransaction();
//					patientId = session.save(patient).toString();
//				}
//			} else {
//				patientId = rs.getString("id");
//				patient.setId(Long.parseLong(patientId));
//				session = DatabaseUtil.getSession();// 保存患者信息到电子病历数据库中
//				tran = session.beginTransaction();
//				// 维护门诊病人信息
//				patient = (Patient) session.get(Patient.class, Long
//						.parseLong(patientId));
//				patient.setOutDeptCode(deptcode);
//				session.save(patient);//更新操作
//			}
//		} catch (Exception e) {
//			if (tran != null) {
//				tran.rollback();
//			}
//			throw new RuntimeException("执行单条病人存储过程(门诊)出错", e);
//		} finally {
//			DatabaseUtil.closeResource(session);
//			try {
//				if (cstmt != null) {
//					cstmt.close();
//				}
//				DatabaseUtil.closeResource(connD, sm, rs);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return patientId;
//	}
	public void executeYiZhou() {
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Session session = null;
		Transaction tran = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String hql = "select p.patientId,m.inhspDate from t_InHsp_CaseMaster" +
					" m inner join t_patient p on p.id = m.patientId where" +
					" inhspDate between '2011-10-01' and '2011-10-31' ";
			System.out.println("11=="+session);
			session = DatabaseUtil.getSession();
			System.out.println("222=="+session);
			tran = session.beginTransaction();
			List list = session.createSQLQuery(hql).list();
			if(list != null && list.size() > 0){
				con = DatabaseUtil.getConnetion_His();// 获取连接HIS的数据连接
				System.out.println(con);
				for(int i=0;i<list.size();i++){
					Object[] obj = (Object[])list.get(i);
					String procSql = "{call tw_hsp_pmpa.emr.getinptorder(?,?,?,?,?)}";
					cstmt = con.prepareCall(procSql);
					cstmt.setString(1, obj[0].toString());
					cstmt.setString(2, obj[1].toString());
					cstmt.setString(3, "2011-10-01");
					cstmt.setString(4, "2011-10-31");
					cstmt.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR);
					cstmt.execute();
					rs = (ResultSet) cstmt.getObject(5);
					while (rs.next()) {
						YiZhou yz = new YiZhou();
						yz.setPtno(rs.getString("ptno"));
						yz.setIndate(sdf.parse(rs.getString("indate")));
						yz.setBdate(sdf.parse(rs.getString("bdate")));
						yz.setInsertid(rs.getString("insertid"));
						yz.setDrname(rs.getString("drname"));
						yz.setOrdercode(rs.getString("ordercode"));
						yz.setOrdername(rs.getString("ordername"));
						yz.setPlusname(rs.getString("plusname"));
						yz.setJsqty(Integer.parseInt(rs.getString("jsqty")));
						yz.setUnit(rs.getString("unit"));
						session.save(yz);
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			throw new RuntimeException("执行查找病人存储过程出错", e);
		} finally {
			try {
				DatabaseUtil.closeResource(con, cstmt, rs);
				DatabaseUtil.closeResource(session);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
