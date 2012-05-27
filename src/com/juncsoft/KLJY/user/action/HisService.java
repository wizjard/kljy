package com.juncsoft.KLJY.user.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.CaseMaster.util.CaseCfgUtil;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyDoctor;
import com.juncsoft.KLJY.outoremergency.impl.OutOrMergencyPatientDaoImpl;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.MD5;

public class HisService {
	private OutOrMergencyPatientDaoImpl hisPatient = new OutOrMergencyPatientDaoImpl();
	public Map<String, Object> login(String DoctorID, String WardCode,
			String PatientID, String Admseqno) throws Exception {
		Map<String, Object> map = Collections
				.synchronizedMap(new HashMap<String, Object>());
		try {
			User user = doUser(DoctorID, WardCode);
			map.put("u", user);
			if (PatientID != null && PatientID.length() > 0) {
				Patient patient = doPatient(PatientID, WardCode);
				map.put("p", patient);
				InHspCaseMaster cm = doCaseMaster(user, patient, WardCode,
						PatientID, Admseqno);
				map.put("c", cm);
			}
		} catch (Exception e) {
			throw e;
		}
		return map;
	}

	public User login_user(String DoctorID, String WardCode) throws Exception {
		User user = null;
		try {
			user = doUser(DoctorID, WardCode);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

	private User doUser(String DoctorID, String WardCode) throws Exception {
		User user = null;
		try {
			user = doUser_isUserExistenceUser(DoctorID, WardCode);
			// if (id == null || id <= 0) {
			// id = doUser_import(DoctorID, WardCode);
			// }
			// user = doUser_findById(id);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}

	private Long doUser_import(String DoctorID, String WardCode)
			throws Exception {
		Long id = null;
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			// DatabaseUtil.test(DoctorID, WardCode);
			// System.out.println("2345");
			conn = DatabaseUtil.getConnetion_His();
			String sql = "select * from \"VIEW_IPD_DOCTOR\" where DOCTORID='"
					+ DoctorID + "' and ORGID='" + WardCode + "'";
			conn = DatabaseUtil.getConnetion_His();
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			if (rs.next()) {
				User user = new User();
				user.setRole(rs.getString("TITLEID"));
				user.setBelong(rs.getString("ORGID"));
				user.setPassword(new MD5().getMD5ofStr("11111111"));
				String name = rs.getString("DOCTORNAME");
				user.setName(name);
				user.setAccount(doUser_createAccount(name));
				user.setValid(1);
				Date date = new Date(Calendar.getInstance().getTimeInMillis());
				user.setCreateDate(date);
				user.setModifyDate(date);
				id = doUser_saveUser(user);
				sql = "insert into t_User_his(DOCTORID,WARDCODE,userid) values('"
						+ DoctorID + "','" + WardCode + "'," + id + ")";
				conn.close();
				conn = DatabaseUtil.getConnection();
				sm = conn.createStatement();
				sm.executeUpdate(sql);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return id;
	}

	private Long doUser_saveUser(User user) throws Exception {
		Long id = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			id = (Long) session.save(user);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}

	private String doUser_createAccount(String name) throws Exception {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			sm = conn
					.prepareStatement("select count(*) from t_User where name=?");
			sm.setString(1, name);
			rs = sm.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					name = name + (count + 1);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return name;
	}

	private User doUser_findById(Long id) throws Exception {
		User user = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			user = (User) session.get(User.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return user;
	}

	// 最新的登录HIS方法
	// public Long doUser_isUserExistenceHIS(String doctorID, String wardCode) {
	// Long id = null;
	// Session session = null;
	// Transaction tran = null;
	// try {
	// session = DatabaseUtil.getSession();
	// tran = session.beginTransaction();
	// String hql = "from User where drdeptname5='"+doctorID+"'";
	// List list = session.createQuery(hql).list();
	// if(list != null && list.size() > 0){
	// User user = (User)list.get(0);
	// execProHisUpdateUser(doctorID,user);
	// } else {
	// id = execuHisSave(doctorID,wardCode);
	// }
	// executeDoctorWardUser(doctorID,wardCode,id);
	// } catch (Exception e) {
	// throw new RuntimeException("执行医生从HIS到电子病历插入与更新失败", e);
	// } finally {
	// DatabaseUtil.closeResource(session);
	// }
	// return id;
	// }

	private Patient doPatient(String PatientID, String WardCode)
			throws Exception {
		Patient p = null;
		try {
			p = doPatient_isUserExistencePatient(PatientID,WardCode);
			if (p == null) {
				p= doPatient_importPatient(PatientID, WardCode);
			}
//			if (id != null)
//				p = doPatient_findById(id);
		} catch (Exception e) {
			throw e;
		}
		return p;
	}

	private Long doPatient_import(String PatientID, String WardCode)
			throws Exception {
		Long id = null;
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select " + "PATIENTNO,SOCIETYNO,PATIENTNAME,"
					+ "SEXID,BIRTHDAY,NATIONALITYID,PEOPLEID,"
					+ "HOMEADDR,HOMEPOSTCODE,HOMETEL,WORKORG,"
					+ "ORGADDR,ORGPOSTCODE,CONTACTNAME,CONTACTTEL " + "from "
					+ DatabaseUtil.Oracle_table_patient + " where PATIENTID=?";
			conn = DatabaseUtil.getConnetion_His();
			sm = conn.prepareStatement(sql);
			sm.setString(1, PatientID);
			rs = sm.executeQuery();
			if (rs.next()) {
				Patient p = new Patient();
				p.setPatientNo(rs.getString("PATIENTNO"));
				p.setIdType("01");
				p.setIdNo(rs.getString("SOCIETYNO"));
				p.setPatientName(rs.getString("PATIENTNAME"));
				Integer sex = rs.getInt("SEXID");
				if (sex == null) {
					p.setGender("9");
				} else if (sex == 1) {
					p.setGender("2");
				} else if (sex == 2) {
					p.setGender("1");
				}
				p.setBirthday(rs.getDate("BIRTHDAY"));
				p.setPatientId(PatientID);
				p.setNationality(rs.getString("NATIONALITYID"));
				p.setPeople(rs.getString("PEOPLEID"));
				p.setHomeAddr(rs.getString("HOMEADDR"));
				p.setHomePostCode(rs.getString("HOMEPOSTCODE"));
				p.setHomeTel(rs.getString("HOMETEL"));
				p.setWorkUnit(rs.getString("WORKORG"));
				p.setWorkUnitAddr(rs.getString("ORGADDR"));
				p.setWorkUnitPostCode(rs.getString("ORGPOSTCODE"));
				p.setContacterName(rs.getString("CONTACTNAME"));
				p.setContacterTel(rs.getString("CONTACTTEL"));
				Date date = new Date(Calendar.getInstance().getTimeInMillis());
				p.setCreateDate(date);
				p.setModifyDate(date);
				p.setCurrentWardCode(WardCode);
				id = doPatient_savePatient(p);
				conn = DatabaseUtil.getConnection();
				sql = "insert into t_Patient_his(PATIENTID,id) values(?,?)";
				sm = conn.prepareStatement(sql);
				sm.setString(1, PatientID);
				sm.setLong(2, id);
				sm.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return id;
	}

	private Patient doPatient_importPatient(String PatientID, String WardCode)
			throws Exception {
		Long id = null;
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		Patient p = null;
		try {
			String sql = "select " + "PATIENTNO,SOCIETYNO,PATIENTNAME,"
					+ "SEXID,BIRTHDAY,NATIONALITYID,PEOPLEID,"
					+ "HOMEADDR,HOMEPOSTCODE,HOMETEL,WORKORG,"
					+ "ORGADDR,ORGPOSTCODE,CONTACTNAME,CONTACTTEL " + "from "
					+ DatabaseUtil.Oracle_table_patient + " where PATIENTID=?";
			conn = DatabaseUtil.getConnetion_His();
			sm = conn.prepareStatement(sql);
			sm.setString(1, PatientID);
			rs = sm.executeQuery();
			if (rs.next()) {
				p= new Patient();
				p.setPatientNo(rs.getString("PATIENTNO"));
				p.setIdType("01");
				p.setIdNo(rs.getString("SOCIETYNO"));
				p.setPatientName(rs.getString("PATIENTNAME"));
				Integer sex = rs.getInt("SEXID");
				if (sex == null) {
					p.setGender("9");
				} else if (sex == 1) {
					p.setGender("2");
				} else if (sex == 2) {
					p.setGender("1");
				}
				p.setBirthday(rs.getDate("BIRTHDAY"));
				p.setPatientId(PatientID);
				p.setNationality(rs.getString("NATIONALITYID"));
				p.setPeople(rs.getString("PEOPLEID"));
				p.setHomeAddr(rs.getString("HOMEADDR"));
				p.setHomePostCode(rs.getString("HOMEPOSTCODE"));
				p.setHomeTel(rs.getString("HOMETEL"));
				p.setWorkUnit(rs.getString("WORKORG"));
				p.setWorkUnitAddr(rs.getString("ORGADDR"));
				p.setWorkUnitPostCode(rs.getString("ORGPOSTCODE"));
				p.setContacterName(rs.getString("CONTACTNAME"));
				p.setContacterTel(rs.getString("CONTACTTEL"));
				Date date = new Date(Calendar.getInstance().getTimeInMillis());
				p.setCreateDate(date);
				p.setModifyDate(date);
				p.setCurrentWardCode(WardCode);
				id = doPatient_savePatient(p);
				conn = DatabaseUtil.getConnection();
				sql = "insert into t_Patient_his(PATIENTID,id) values(?,?)";
				sm = conn.prepareStatement(sql);
				sm.setString(1, PatientID);
				sm.setLong(2, id);
				sm.executeUpdate();
				p.setId(id);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return p;
	}

	private Long doPatient_savePatient(Patient p) throws Exception {
		Long id = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			id = (Long) session.save(p);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}

	private Patient doPatient_findById(Long id) throws Exception {
		Patient p = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			p = (Patient) session.get(Patient.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return p;
	}

	/**
	 * 判断病人是否存在 liugang 2011-07-23 15:00
	 * 
	 * @param paientId
	 * @return
	 */
	public Long doPatient_isUserExistence(String paientId) {
		Long id = null;
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select a.id from t_Patient a "
					+ "where a.patientId=?";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setString(1, paientId);
			rs = sm.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (Exception e) {
			throw new RuntimeException("", e);
		} finally {
			try {
				DatabaseUtil.closeResource(conn, sm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	// private Long doPatient_isUserExistence(String PatientID) throws Exception
	// {
	// Long id = null;
	// Connection conn = null;
	// PreparedStatement sm = null;
	// ResultSet rs = null;
	// try {
	// String sql = "select a.id from t_Patient a "
	// + "left outer join t_Patient_his b on a.id=b.id "
	// + "where b.PATIENTID=?";
	// conn = DatabaseUtil.getConnection();
	// sm = conn.prepareStatement(sql);
	// sm.setString(1, PatientID);
	// rs = sm.executeQuery();
	// if (rs.next()) {
	// id = rs.getLong(1);
	// }
	// if (id == null || id <= 0) {
	// sql = "delete from t_Patient_his where PATIENTID=?";
	// sm = conn.prepareStatement(sql);
	// sm.setString(1, PatientID);
	// sm.executeUpdate();
	// }
	// } catch (Exception e) {
	// throw e;
	// } finally {
	// DatabaseUtil.closeResource(conn, sm, rs);
	// }
	// return id;
	// }

	private InHspCaseMaster doCaseMaster(User user, Patient patient,
			String WardCode, String PatientID, String Admseqno)
			throws Exception {
		InHspCaseMaster cm = null;
		try {
			InHspCaseMaster temp = doCaseMaster_isCaseMasterExistenceUser(
					patient, WardCode, PatientID, Admseqno, user);
			if (temp.getId() != null && temp.getId() > 0) {
				cm = temp;
			} else {
				temp.setPatientId(patient.getId());
				cm = doCaseMaster_import(temp, WardCode);
			}
		} catch (Exception e) {
			throw e;
		}
		return cm;
	}

	private InHspCaseMaster _doCaseMaster(String WardCode, String PatientID,
			String Admseqno) throws Exception {
		InHspCaseMaster cm = null;
		Patient patient = doPatient(PatientID, WardCode);
		try {
			InHspCaseMaster temp = doCaseMaster_isCaseMasterExistence(patient,
					WardCode, PatientID, Admseqno);
			// System.out.println("caseId:" + temp.getId());
			if (temp.getId() != null && temp.getId() > 0) {
				cm = temp;
			} else {
				temp.setPatientId(patient.getId());
				cm = doCaseMaster_import(temp, WardCode);
			}

		} catch (Exception e) {
			throw e;
		}
		return cm;
	}

	private InHspCaseMaster doCaseMaster_import(InHspCaseMaster cm,
			String WardCode) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			cm.setCurrentWordCode(WardCode);
			JSONArray array = CaseCfgUtil.queryMyInHspRecCfg(WardCode);
			if (array.size() == 1) {
				cm.setCaseModuleId(array.getJSONObject(0).getString("code"));
			}
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Long id = (Long) session.save(cm);
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

	@SuppressWarnings("unchecked")
	private InHspCaseMaster doCaseMaster_isCaseMasterExistence(Patient patient,
			String WardCode, String PatientID, String Admseqno)
			throws Exception {
		InHspCaseMaster cm = null;
		Session session = null;
		Transaction tran = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			cm = doCaseMaster_findFromOracle(WardCode, PatientID, Admseqno);
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<InHspCaseMaster> list = session.createCriteria(
					InHspCaseMaster.class).add(
					Restrictions.eq("patientId", patient.getId())).add(
					Restrictions.eq("flag", 0)).list();
			for (InHspCaseMaster c : list) {
				if (df.format(c.getInHspDate()).equals(
						df.format(cm.getInHspDate()))) {
					cm = c;
					break;
				}
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
	private InHspCaseMaster doCaseMaster_isCaseMasterExistenceUser(
			Patient patient, String WardCode, String PatientID,
			String Admseqno, User user) throws Exception {
		InHspCaseMaster cm = null;
		Session session = null;
		Transaction tran = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			cm = doCaseMaster_findFromOracleUser(WardCode, PatientID, Admseqno,
					user);
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<InHspCaseMaster> list = session.createCriteria(
					InHspCaseMaster.class).add(
					Restrictions.eq("patientId", patient.getId())).add(
					Restrictions.eq("flag", 0)).list();
			for (InHspCaseMaster c : list) {
				if (df.format(c.getInHspDate()).equals(
						df.format(cm.getInHspDate()))) {
					cm = c;
					break;
				}
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

	private InHspCaseMaster doCaseMaster_findFromOracle(String WardCode,
			String PatientID, String Admseqno) throws Exception {
		InHspCaseMaster cm = null;
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			Integer admseqno = Integer.parseInt(Admseqno);
			String sql = "select INDATE,INTIME,INWARDCODE,INILLS,DRCODE,INSTATUS,AGE from "
					+ DatabaseUtil.Oracle_table_patient
					+ " where PATIENTID=? and ADMSEQNO=?";
			conn = DatabaseUtil.getConnetion_His();
			sm = conn.prepareStatement(sql);
			sm.setString(1, PatientID);
			sm.setInt(2, admseqno);
			rs = sm.executeQuery();
			if (rs.next()) {
				cm = new InHspCaseMaster();
				cm.setInHspDate(rs.getDate("INDATE"));
				String intime = rs.getString("INTIME");
				if (intime != null) {
					String[] intimes = intime.split(":");
					if (intimes.length == 2) {
						int min = Integer.parseInt(intimes[0].trim());
						int sec = Integer.parseInt(intimes[1].trim());
						String time = (min < 10 ? ("0" + min) : (min + ""))
								+ ":" + (sec < 10 ? ("0" + sec) : (sec + ""));
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						String date = df.format(cm.getInHspDate()) + " " + time;
						df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						cm.setInHspDate(df.parse(date));
					}
				}
				cm.setInWardCode(rs.getString("INWARDCODE"));
				cm.setInIlls(rs.getString("INILLS"));
				String doc = rs.getString("DRCODE");
				if (doc != null && doc.length() > 0) {
					try {
						User user = this.doUser(doc, WardCode);
						if (user != null)
							cm.setResponsibleDoc(user.getId());
					} catch (Exception e) {
					}
				}
				cm.setInStatus(rs.getString("INSTATUS"));
				cm.setInHspTimes(admseqno);
				try {
					String age = rs.getString("AGE");
					Integer ag = 0;
					if (age != null)
						ag = Integer.parseInt(age);
					cm.setAge(ag + "岁");
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return cm;
	}

	private InHspCaseMaster doCaseMaster_findFromOracleUser(String WardCode,
			String PatientID, String Admseqno, User user) throws Exception {
		InHspCaseMaster cm = null;
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			Integer admseqno = Integer.parseInt(Admseqno);
			String sql = "select INDATE,INTIME,INWARDCODE,INILLS,DRCODE,INSTATUS,AGE from "
					+ DatabaseUtil.Oracle_table_patient
					+ " where PATIENTID=? and ADMSEQNO=?";
			conn = DatabaseUtil.getConnetion_His();
			sm = conn.prepareStatement(sql);
			sm.setString(1, PatientID);
			sm.setInt(2, admseqno);
			rs = sm.executeQuery();
			if (rs.next()) {
				cm = new InHspCaseMaster();
				cm.setInHspDate(rs.getDate("INDATE"));
				String intime = rs.getString("INTIME");
				if (intime != null) {
					String[] intimes = intime.split(":");
					if (intimes.length == 2) {
						int min = Integer.parseInt(intimes[0].trim());
						int sec = Integer.parseInt(intimes[1].trim());
						String time = (min < 10 ? ("0" + min) : (min + ""))
								+ ":" + (sec < 10 ? ("0" + sec) : (sec + ""));
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						String date = df.format(cm.getInHspDate()) + " " + time;
						df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						cm.setInHspDate(df.parse(date));
					}
				}
				cm.setInWardCode(rs.getString("INWARDCODE"));
				cm.setInIlls(rs.getString("INILLS"));
				String doc = rs.getString("DRCODE");
				if (doc != null && doc.length() > 0) {
					try {
						if (user != null)
							cm.setResponsibleDoc(user.getId());
					} catch (Exception e) {
					}
				}
				cm.setInStatus(rs.getString("INSTATUS"));
				cm.setInHspTimes(admseqno);
				try {
					String age = rs.getString("AGE");
					Integer ag = 0;
					if (age != null)
						ag = Integer.parseInt(age);
					cm.setAge(ag + "岁");
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return cm;
	}

	// //查找His病人编号，该编号对应lis中的patNo，方法中的参数指病案号
	// public String findPid_His(String patientNo) throws Exception {
	// String pid = null;
	// Connection conn = null;
	// PreparedStatement sm = null;
	// ResultSet rs = null;
	// try {
	// String sql = "select * from " + DatabaseUtil.Oracle_table_patient + "
	// where PATIENTNO = ? ";
	// conn = DatabaseUtil.getConnetion_His();
	// sm = conn.prepareStatement(sql);
	// sm.setString(1, patientNo);
	// rs = sm.executeQuery();
	// if (rs.next()) {
	// pid = rs.getString("PATIENTID");
	// // System.out.println("病人姓名:" + rs.getString("PATIENTNAME"));
	// // System.out.println("His中的病人编号:" + pid);
	// }
	// } catch (Exception e) {
	// throw e;
	// } finally {
	// DatabaseUtil.closeResource(conn, sm, rs);
	// }
	// return pid;
	// }

	public List findPid_His(String patientNo) throws Exception {
		String pid = null;
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<String>(); // 需要引进List和ArrayList
		try {
			String sql = "select * from " + DatabaseUtil.Oracle_table_patient
					+ " where PATIENTNO = ? ";
			conn = DatabaseUtil.getConnetion_His();
			sm = conn.prepareStatement(sql);
			sm.setString(1, patientNo);
			rs = sm.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("PATIENTID"));
				// System.out.println(rs.getString("PATIENTID"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return list;
	}

	// 查找His病案号,根据病人编号或姓名查询
	private String findPNo_His(String pid, String pName) throws Exception {
		String patNo = null;
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + DatabaseUtil.Oracle_table_patient
					+ " where PATIENTID = ? or PATIENTNAME = ?";
			conn = DatabaseUtil.getConnetion_His();
			sm = conn.prepareStatement(sql);
			sm.setString(1, pid);
			sm.setString(2, pName);
			rs = sm.executeQuery();
			if (rs.next()) {
				patNo = rs.getString("PATIENTNO");
				// System.out.println("病人姓名:" + rs.getString("PATIENTNAME"));
				// System.out.println("病案号:" + patNo);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return patNo;
	}

	// 执行更新医生信息
	public void execProHisUpdateUser(String doctorID, User user,String wardCode) {
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnetion_His();
			String procSql = "{call tw_hsp_pmpa.EMR.DRINFO(?,?)}";
			cstmt = conn.prepareCall(procSql);
			cstmt.setString(1, doctorID);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(2);
			if (rs.next()) {
				if(!"null".equals(wardCode)){
					updateUser(user, rs,wardCode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DatabaseUtil.closeResource(conn, cstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Long execuHisSave(String doctorID, String wardCode) {
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Session session = null;
		Long id = null;
		try {
			conn = DatabaseUtil.getConnetion_His();
			String procSql = "{call tw_hsp_pmpa.EMR.DRINFO(?,?)}";
			cstmt = conn.prepareCall(procSql);
			cstmt.setString(1, doctorID);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(2);
			if (rs.next()) {
				saveDoctorHis(rs, wardCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, cstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	public void updateUser(User user, ResultSet rs,String wardCode) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			user.setModifyDate(new Date());
			user.setBelong(wardCode);
			user.setUnkown1(rs.getString("deptcode"));
			user.setDeptcode(rs.getString("deptcodename"));
			user.setDeptcodename(rs.getString("drdept1"));
			user.setDrdept1(rs.getString("drdeptname1"));
			user.setDrdeptname1(rs.getString("drdept2"));
			user.setDrdept2(rs.getString("drdeptname2"));
			user.setDrdeptname2(rs.getString("drdept3"));
			user.setDrdept3(rs.getString("drdeptname3"));
			user.setDrdeptname3(rs.getString("drdept4"));
			user.setDrdept4(rs.getString("drdeptname4"));
			user.setDrdeptname4(rs.getString("drdept5"));
			user.setDrdept5(rs.getString("drdeptname5"));
			user.setDrdeptname5(rs.getString("idnumber"));
			user.setHisDoctorId(rs.getString("gbjaejik"));
			user.setGbjaejik(rs.getString("drgrade"));
			user.setDrgrade(rs.getString("VESTDEPT"));
			user.setVestdept(rs.getString("gbspc"));
			user.setGbspc(rs.getString("gbjupsu"));
			user.setGbjupsu(rs.getString("password"));
			session.update(user);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public Long saveDoctorHis(ResultSet rs, String wardCode) {
		Session session = null;
		Transaction tran = null;
		Long id = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			User user = new User();
			user.setName(rs.getString("name"));
			user.setAccount(rs.getString("name"));
			user.setPassword(new MD5().getMD5ofStr("11111111"));
			user.setValid(1);
			user.setRole("3");
			user.setBelong(wardCode);
			user.setCreateDate(new Date());
			user.setModifyDate(new Date());
			user.setUnkown1(rs.getString("deptcode"));
			user.setDeptcode(rs.getString("deptcodename"));
			user.setDeptcodename(rs.getString("drdept1"));
			user.setDrdept1(rs.getString("drdeptname1"));
			user.setDrdeptname1(rs.getString("drdept2"));
			user.setDrdept2(rs.getString("drdeptname2"));
			user.setDrdeptname2(rs.getString("drdept3"));
			user.setDrdept3(rs.getString("drdeptname3"));
			user.setDrdeptname3(rs.getString("drdept4"));
			user.setDrdept4(rs.getString("drdeptname4"));
			user.setDrdeptname4(rs.getString("drdept5"));
			user.setDrdept5(rs.getString("drdeptname5"));
			user.setDrdeptname5(rs.getString("idnumber"));
			user.setHisDoctorId(rs.getString("gbjaejik"));
			user.setGbjaejik(rs.getString("drgrade"));
			user.setDrgrade(rs.getString("VESTDEPT"));
			user.setVestdept(rs.getString("gbspc"));
			user.setGbspc(rs.getString("gbjupsu"));
			user.setGbjupsu(rs.getString("password"));
			id = (Long) session.save(user);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}

	/**
	 * 维护医生病区与编号中间表
	 * 
	 * @param doctorID
	 * @param wardCode
	 */
	public void executeDoctorWard(String doctorID, String wardCode, User user) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			st = conn.createStatement();
			String tempSql = "select * from t_User_his where DOCTORID='"
					+ doctorID + "' and WARDCODE='" + wardCode + "'";
			rs = (ResultSet) st.executeQuery(tempSql);
			if (!rs.next()) {
				String sql = "insert into t_User_his(DOCTORID,WARDCODE,userid) values('"
						+ doctorID
						+ "','"
						+ wardCode
						+ "',"
						+ user.getId()
						+ ")";
				st.execute(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DatabaseUtil.closeResource(conn, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
//		try {
//			System.out.println("开始");
//			new HisService()._doCaseMaster("S0ll", "0000200765", "2");
//			System.out.println("结束");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	// 最新的从HIS登陆到本系统的方法
	public User doUser_isUserExistenceUser(String doctorID, String wardCode) {
		Session session = null;
		Transaction tran = null;
		User user = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sqlId = "from User where drdeptname5='" + doctorID + "'";
			List list = session.createQuery(sqlId).list();
			if (list != null && list.size() > 0) {
				user = (User) list.get(0);
				execProHisUpdateUser(doctorID, user,wardCode);
			} else {
				execuHisSaveUser(doctorID, wardCode, user);
			}
			executeDoctorWard(doctorID, wardCode, user);
		} catch (Exception e) {
			throw new RuntimeException("执行医生从HIS到电子病历插入与更新失败", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return user;
	}

	// 最新user
	public User execuHisSaveUser(String doctorID, String wardCode, User user) {
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnetion_His();
			String procSql = "{call tw_hsp_pmpa.EMR.DRINFO(?,?)}";
			cstmt = conn.prepareCall(procSql);
			cstmt.setString(1, doctorID);
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(2);
			if (rs.next()) {
				saveDoctorHisUser(rs, wardCode, user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DatabaseUtil.closeResource(conn, cstmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	// 最新user
	public User saveDoctorHisUser(ResultSet rs, String wardCode, User user) {
		Session session = null;
		Transaction tran = null;
		Long id = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			user = new User();
			user.setName(rs.getString("name"));
			user.setAccount(rs.getString("name"));
			user.setPassword(new MD5().getMD5ofStr("11111111"));
			user.setValid(1);
			user.setRole("3");
			user.setBelong(wardCode);
			user.setCreateDate(new Date());
			user.setModifyDate(new Date());
			user.setUnkown1(rs.getString("deptcode"));
			user.setDeptcode(rs.getString("deptcodename"));
			user.setDeptcodename(rs.getString("drdept1"));
			user.setDrdept1(rs.getString("drdeptname1"));
			user.setDrdeptname1(rs.getString("drdept2"));
			user.setDrdept2(rs.getString("drdeptname2"));
			user.setDrdeptname2(rs.getString("drdept3"));
			user.setDrdept3(rs.getString("drdeptname3"));
			user.setDrdeptname3(rs.getString("drdept4"));
			user.setDrdept4(rs.getString("drdeptname4"));
			user.setDrdeptname4(rs.getString("drdept5"));
			user.setDrdept5(rs.getString("drdeptname5"));
			user.setDrdeptname5(rs.getString("idnumber"));
			user.setHisDoctorId(rs.getString("gbjaejik"));
			user.setGbjaejik(rs.getString("drgrade"));
			user.setDrgrade(rs.getString("VESTDEPT"));
			user.setVestdept(rs.getString("gbspc"));
			user.setGbspc(rs.getString("gbjupsu"));
			user.setGbjupsu(rs.getString("password"));
			id = (Long) session.save(user);
			user.setId(id);
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return user;
	}

	// private Long doUser_isUserExistence(String DoctorID, String WardCode)
	// throws Exception {
	// Long id = null;
	// Connection conn = null;
	// PreparedStatement sm = null;
	// ResultSet rs = null;
	// try {
	// String sql = "select a.id from t_User a "
	// + "left outer join t_User_his b on a.id=b.userid "
	// + "where b.DOCTORID=? and b.WARDCODE=?";
	// conn = DatabaseUtil.getConnection();
	// sm = conn.prepareStatement(sql);
	// sm.setString(1, DoctorID);
	// sm.setString(2, WardCode);
	// rs = sm.executeQuery();
	// if (rs.next()) {
	// id = rs.getLong(1);
	// }
	// if (id == null || id <= 0) {
	// sql = "delete from t_User_his where DOCTORID=? and WARDCODE=?";
	// sm = conn.prepareStatement(sql);
	// sm.setString(1, DoctorID);
	// sm.setString(2, WardCode);
	// sm.executeUpdate();
	// }
	// } catch (Exception e) {
	// throw e;
	// } finally {
	// DatabaseUtil.closeResource(conn, sm, rs);
	// }
	// return id;
	// }

	/**
	 * 判断病人是否存在 liugang 2011-07-23 15:00
	 * 
	 * @param paientId
	 * @return
	 */
	public Patient doPatient_isUserExistencePatient(String paientId,String WardCode) {
		Session session = null;
		Transaction tran = null;
		Patient patient = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "from Patient where patientId='" + paientId + "'";
			List list = session.createQuery(sql).list();
			if (list != null && list.size() > 0) {
				patient = (Patient) list.get(0);
				patient.setCurrentWardCode(WardCode);
				session.update(patient);
			}
			tran.commit();
		} catch (Exception e) {
			throw new RuntimeException("", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return patient;
	}
	
//	private Patient executeHISPatientNameByIdnumber(String idnumber,) {
//		Connection con = null;
//		CallableStatement cstmt = null;
//		String patientName = null;
//		ResultSet rs = null;
//		Session session = null;
//		Patient patient = null;
//		try {
//			con = DatabaseUtil.getConnetion_His();// 获取连接HIS的数据连接
//			String procSql = "{call tw_hsp_pmpa.emr.ptinfo(?,?)}";
//			cstmt = con.prepareCall(procSql);
//			cstmt.setString(1, idnumber);
//			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
//			cstmt.execute();
//			rs = (ResultSet) cstmt.getObject(2);
//			
//			session = DatabaseUtil.getSession();
//			String hql = "from Patient where patientName='" + patientName + "'";
//			List list = session.createQuery(hql).list();
//			patient = (Patient) list.get(0);
//			return patient;
//		} catch (Exception e) {
//			throw new RuntimeException("执行查找病人存储过程出错", e);
//		} finally {
//			try {
//				if (session != null) {
//					DatabaseUtil.closeResource(session);
//				}
//				DatabaseUtil.closeResource(con, cstmt, rs);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}