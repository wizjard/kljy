package com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.dao.InHospitalPageDao;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.ContinuePage_Doctor;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.ContinuePage_Ops;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.ContinuePage_Sysptom;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.ContinuePage_Ward;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.InHospitalPage;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

/**
 * 病案首页数据实现类
 * 
 * @author Administrator
 */
public class BeInHospitalPageImpl implements InHospitalPageDao {
	/**
	 * 查询病案首页
	 * 
	 * @param id 病例id
	 * @return 病案首页实体
	 * @throws Exception
	 */
	public InHospitalPage findInHospByCaseId(Long id) throws Exception {
		Session session = null;
		Transaction t = null;
		InHospitalPage InHosp = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			String sql = " from com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.InHospitalPage where caseId=? ";
			if (id > 0) {
				List<InHospitalPage> list = session.createQuery(sql).setLong(0,id).list();
				if (list.size() > 0) {
					InHosp = list.get(0);
				}
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return InHosp;
	}

	public InHospitalPage inHospSaveOrUpdate(InHospitalPage inHosp)
			throws Exception {
		Session session = null;
		Transaction t = null;
		Long id = -1L;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if (inHosp.getId() != null && inHosp.getId() > 0) {
				inHosp = (InHospitalPage) session.merge(inHosp);
			} else {
				id = (Long) session.save(inHosp);
				if (id > 0) {
					inHosp = (InHospitalPage) session.get(InHospitalPage.class,id);
				}
			}
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return inHosp;
	}

	/**
	 * 查询病案首页初始值
	 * @param caseid 病历ID
	 * @return
	 * @throws Exception
	 */
	public InHospitalPage findInItPageValue(Long caseid) throws Exception {
		Session session = null;
		Transaction t = null;
		List<InHospitalPage> list = new ArrayList<InHospitalPage>();
		InHospitalPage hosp = null;
		try {
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			Connection con = session.connection();
			ResultSet rs = null;
			CallableStatement cs = con.prepareCall("{ call pre_TheFirstOfCase_Select(?)}");
			cs.setLong(1, caseid);
			rs = cs.executeQuery();
			/* 判断结果集结果条数 */
			while (rs.next()) {
				InHospitalPage inHosp = new InHospitalPage();
				inHosp.setPatientName(rs.getString("patientName"));
				inHosp.setPatientNo(rs.getString("patientNo"));
				inHosp.setGender(rs.getString("gender"));
				inHosp.setBirthday(rs.getDate("birthday"));
				String contacter = rs.getString("contacter");
				if (contacter.trim().length() > 0) {
					inHosp.setContacterRelationship(rs.getString("contacterRelationship"));
				}
				inHosp.setAge(rs.getString("age"));
				inHosp.setXno(rs.getString("xNO"));
				inHosp.setMriNo(rs.getString("mriNo"));
				inHosp.setCtNo(rs.getString("ctNo"));
				inHosp.setInHspTimes(rs.getInt("inHspTimes"));
				inHosp.setPeople(rs.getString("people"));
				inHosp.setNationality(rs.getString("nationality"));
				String temp = rs.getString("marrageStatus");
				if (temp.equals("20")) {
					inHosp.setMarrageStatus("2");
				} else if (temp.equals("10")) {
					inHosp.setMarrageStatus("1");
				} else if (temp.equals("30")) {
					inHosp.setMarrageStatus("3");
				} else if (temp.equals("40")) {
					inHosp.setMarrageStatus("4");
				}
				inHosp.setOccupation(rs.getString("occupation"));
				String idType = rs.getString("idType");
				if (idType.equals("01")) {
					inHosp.setIdNo(rs.getString("idNo"));
				}
				inHosp.setHomeAddr(rs.getString("homeAddr"));
				inHosp.setHomePostCode(rs.getString("homePostCode"));
				inHosp.setWorkUnitAddr(rs.getString("workUnitAddr"));
				inHosp.setWorkUnitPostCode(rs.getString("workUnitPostCode"));
				inHosp.setWorkUnitTel(rs.getString("workUnitTel"));
				inHosp.setContacterName(rs.getString("contacterName"));
				inHosp.setContacterTel(rs.getString("contacterTel"));
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				System.out.println("格式化日期：" + df.format(rs.getDate("inHspDate")));
				
				Date d1 = rs.getDate("inHspDate");
				Date d2 = rs.getDate("outHspDate");
				inHosp.setInHspDate(d1);
				inHosp.setOutHspDate(d2);
				System.out.println("存储过程-->入院日期：" + rs.getDate("inHspDate") + "\t" + d1 + "\t出院日期：" + rs.getDate("outHspDate") + "\t" + d2);
				long time = -1L;
				int tm = 0;
				if (d1 != null && d2 != null) {
					time = d2.getTime() - d1.getTime();
					tm = (new Double(Math.floor(time / (1000 * 3600 * 24)))
							.intValue() + 1);
				}
				inHosp.setHspDate2(tm);
				inHosp.setInWardCode(rs.getString("inWardCode"));
				inHosp.setOutHspWard(rs.getString("outHspWard"));

				String inStatus = rs.getString("inStatus");
				if (inStatus.equals("0")) {
					inStatus = "3";
				} else if (inStatus.equals("1")) {
					inStatus = "2";
				} else if (inStatus.equals("2")) {
					inStatus = "1";
				}
				inHosp.setInStatus(inStatus);
				inHosp.setInIllsShow(rs.getString("inIllsShow"));
				inHosp.setQueding_checkdate(rs.getDate("queding_checkdate"));
				inHosp.setOutIlls1(rs.getString("outIlls1"));
				inHosp.setOutIlls2(rs.getString("outIlls2"));
				inHosp.setOutIlls3(rs.getString("outIlls3"));
				inHosp.setOutIlls4(rs.getString("outIlls4"));
				inHosp.setOutIlls5(rs.getString("outIlls5"));
				inHosp.setOutIlls6(rs.getString("outIlls6"));
				inHosp.setOutIlls7(rs.getString("outIlls7"));
				inHosp.setOutIlls8(rs.getString("outIlls8"));
				list.add(inHosp);
			}
			if (list.size() > 0) {
				hosp = list.get(0);
			}
			rs.close();
			t.commit();
		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return hosp;
	}

	public static void main(String[] args) {
		InHospitalPageDao dao = new BeInHospitalPageImpl();
		Long caseid = 5068L;
		try {
			dao.findInHospitalPrintData(caseid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印数据
	 */
	public Map<String, Object> findInHospitalPrintData(Long caseId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			InHospitalPage inHosp = findInHospByCaseId(caseId);
			String dataFormatHH = "yyyy-MM-dd HH:mm";
			String dataFormat = "yyyy-MM-dd";
			DateFormat df = new SimpleDateFormat(dataFormatHH);
			if (inHosp != null) {
				String path = "com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.InHospitalPage";
				dataProcess(map, inHosp, path);
				for (String key : map.keySet()) {
					if (key.equals("birthday")) {
						df = new SimpleDateFormat(dataFormat);
						if(inHosp.getBirthday()!=null){
							map.put(key, df.format(inHosp.getBirthday()));
						}
					} else if (key.equals("inHspDate")) {
						//System.out.println("入院日期：" + inHosp.getInHspDate());
						df = new SimpleDateFormat(dataFormatHH);
						if(inHosp.getInHspDate()!=null){
							map.put(key, df.format(inHosp.getInHspDate()));
						}
					} else if (key.equals("outHspDate")) {
						//System.out.println("出院日期：" + inHosp.getOutHspDate());
						df = new SimpleDateFormat(dataFormatHH);
						if(inHosp.getOutHspDate()!=null){
							map.put(key, df.format(inHosp.getOutHspDate()));
						}
					} else if (key.equals("queding_checkdate")) {
						df = new SimpleDateFormat(dataFormat);
						if(inHosp.getQueding_checkdate()!=null){
							map.put(key, df.format(inHosp.getQueding_checkdate()));
						}
					} else if (key.equals("badate")) {
						df = new SimpleDateFormat(dataFormat);
						if(inHosp.getBadate()!=null){
							map.put(key, df.format(inHosp.getBadate()));
						}
					} else if (key.equals("tdate")) {
						df = new SimpleDateFormat(dataFormat);
						if(inHosp.getTdate()!=null){
							map.put(key, df.format(inHosp.getTdate()));
						}
					} else if (key.equals("tdate2")) {
						df = new SimpleDateFormat(dataFormat);
						if(inHosp.getTdate2()!=null){
							map.put(key, df.format(inHosp.getTdate2()));
						}
					} else if (key.equals("tdate3")) {
						df = new SimpleDateFormat(dataFormat);
						if(inHosp.getTdate3()!=null){
							map.put(key, df.format(inHosp.getTdate3()));
						}
					} else if (key.equals("tdate4")) {
						df = new SimpleDateFormat(dataFormat);
						if(inHosp.getTdate4()!=null){
							map.put(key, df.format(inHosp.getTdate4()));
						}
					}else if(key.equals("tdate5")){
						df = new SimpleDateFormat(dataFormat);
						System.out.println("tdate5：" + inHosp.getTdate5());
						if(inHosp.getTdate5()!=null){
							map.put(key, df.format(inHosp.getTdate5()));
						}
					} else if (key.equals("icuTurnInto1")) {
						df = new SimpleDateFormat(dataFormatHH);
						if(inHosp.getIcuTurnInto1()!=null){
							map.put(key, df.format(inHosp.getIcuTurnInto1()));
						}
					} else if (key.equals("icuTurnInto2")) {
						if(inHosp.getIcuTurnInto2()!=null){
							map.put(key, df.format(inHosp.getIcuTurnInto2()));
						}
					} else if (key.equals("icuTurnInto3")) {
						df = new SimpleDateFormat(dataFormatHH);
						if(inHosp.getIcuTurnInto3()!=null){
							map.put(key, df.format(inHosp.getIcuTurnInto3()));
						}
					} else if (key.equals("icuDropOut1")) {
						df = new SimpleDateFormat(dataFormatHH);
						if(inHosp.getIcuDropOut1()!=null){
							map.put(key, df.format(inHosp.getIcuDropOut1()));
						}
					} else if (key.equals("icuDropOut2")) {
						df = new SimpleDateFormat(dataFormatHH);
						if(inHosp.getIcuDropOut2()!=null){
							map.put(key, df.format(inHosp.getIcuDropOut2()));
						}
					} else if (key.equals("icuDropOut3")) {
						df = new SimpleDateFormat(dataFormatHH);
						if(inHosp.getIcuDropOut3()!=null){
							map.put(key, df.format(inHosp.getIcuDropOut3()));
						}
					} else if (key.equals("deliveryTime")) {
						df = new SimpleDateFormat(dataFormatHH);
						if(inHosp.getDeliveryTime()!=null){
							map.put(key, df.format(inHosp.getDeliveryTime()));
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return map;
	}

	/**
	 * 处理打印数据
	 * @param map
	 * @param t 指定的实体实例
	 * @param path实体类地址
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> dataProcess(Map<String, Object> map, Object t,
			String path) throws Exception {
		try {
			Class cl = Class.forName(path);
			Field[] fields = cl.getDeclaredFields();
			for (Field field : fields) {
				String name = field.getName();
				String methodName = "get" + name.substring(0, 1).toUpperCase()
						+ name.substring(1, name.length());
				Method method = cl
						.getDeclaredMethod(methodName, new Class[] {});
				map.put(name, method.invoke(t, null));
				/*
				 * Method[] methods = cl.getDeclaredMethods(); for(Method method
				 * : methods){ String mName = method.getName();
				 * if(methodName.equals(mName)){ map.put(name,
				 * method.invoke(cl.cast(t), null)); } }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 病案首页续页保存-修改
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject ContinuePage_SaveOrUpDate(JSONArray sysptom,
			JSONArray ops, JSONArray ward, JSONArray doctor, String state)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			if (state == "") {
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
				// 保存诊断信息
				List<ContinuePage_Sysptom> sList = JSONArray.toList(sysptom,
						ContinuePage_Sysptom.class);
				for (int i = 0; i < sList.size(); i++) {
					session.save(sList.get(i));
				}
				// 保存手术信息
				List<ContinuePage_Ops> oList = JSONArray.toList(ops,
						ContinuePage_Ops.class);
				for (int i = 0; i < oList.size(); i++) {
					System.out
							.println("========================================================");
					System.out.println(oList.get(i).getTdate());
					System.out
							.println("========================================================");
					session.save(oList.get(i));
				}
				// 保存重症监护室信息
				List<ContinuePage_Ward> wList = JSONArray.toList(ward,
						ContinuePage_Ward.class);
				for (int i = 0; i < wList.size(); i++) {
					session.save(wList.get(i));
				}
				// 保存医生信息
				List<ContinuePage_Doctor> dList = JSONArray.toList(doctor,
						ContinuePage_Doctor.class);
				for (int i = 0; i < dList.size(); i++) {
					session.save(dList.get(i));
				}
				String s = "\"" + "state" + "\"" + ":" + "\"" + "1" + "\"";

				tran.commit();
				DatabaseUtil.closeResource(session);
			} else if (state.equals("1")) {
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
				// 修改诊断信息
				List<ContinuePage_Sysptom> sList = JSONArray.toList(sysptom,
						ContinuePage_Sysptom.class);
				for (int i = 0; i < sList.size(); i++) {
					session.update(sList.get(i));
				}
				tran.commit();
				DatabaseUtil.closeResource(session);

				// 修改手术信息
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
				List<ContinuePage_Ops> oList = JSONArray.toList(ops,
						ContinuePage_Ops.class);
				for (int i = 0; i < oList.size(); i++) {
					session.update(oList.get(i));
					// session.update(oList.get(i));
				}
				tran.commit();
				DatabaseUtil.closeResource(session);

				// 修改重症监护室信息
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
				List<ContinuePage_Ward> wList = JSONArray.toList(ward,
						ContinuePage_Ward.class);
				for (int i = 0; i < wList.size(); i++) {
					session.update(wList.get(i));
				}
				tran.commit();
				DatabaseUtil.closeResource(session);

				// 修改医生信息
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
				List<ContinuePage_Doctor> dList = JSONArray.toList(doctor,
						ContinuePage_Doctor.class);
				for (int i = 0; i < dList.size(); i++) {
					session.update(dList.get(i));
				}
				tran.commit();
				DatabaseUtil.closeResource(session);
			}
		} catch (Exception e) {
			tran.rollback();
			DatabaseUtil.closeResource(session);
			e.printStackTrace();
		} finally {

		}

		return null;
	}

	/**
	 * 病案首页续页查询
	 */
	@SuppressWarnings("unchecked")
	public JSONObject ContinuePage_FindByCaseId(Long caseId, Long patientId)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		JSONObject data = new JSONObject();
		JSONArray array = new JSONArray();
		ContinuePage_Doctor cpd = new ContinuePage_Doctor();
		ContinuePage_Sysptom cps = null;
		ContinuePage_Ops cpo = new ContinuePage_Ops();
		ContinuePage_Ward cpw = new ContinuePage_Ward();
		ResultSet r = null;
		// sql1负责查询病人的基本信息
		try {

			session = DatabaseUtil.getSession();
			conn = DatabaseUtil.getConnection();
			
			pstmt = conn.prepareStatement("select state from t_InHsp_InHospitalPage_ContinuePage_Doctor where caseId ="
							+ caseId);
			r = pstmt.executeQuery();
			String state = null;
			if (r.next()) {
				state = r.getString("state");
			}
			// 第一次查询查询病人基本信息
			if (state == null) {
				Connection con = session.connection();
				ResultSet rs = null;
				CallableStatement cs = con
						.prepareCall("{ call pre_TheFirstOfCase_Select(?)}");
				cs.setLong(1, caseId);
				rs = cs.executeQuery();
				if (rs.next()) {
					cpd.setZycs(String.valueOf(rs.getInt("inHspTimes")));
					cpd.setZyh(rs.getString("patientNo"));
					cpd.setPatientName(rs.getString("patientName"));
					cpd.setGender(rs.getString("gender"));
					cpd.setAge(rs.getString("age"));
					cpd.setBirthday(rs.getDate("birthday"));
				}

				// 获取诊断数据
				conn = DatabaseUtil.getConnection();
				ResultSet rs2 = null;
				// String sql =
				// "select * from t_InHsp_Liver_Diagnosis where caseid ="
				// + caseId;
				String sql = "select * from t_InHsp_Liver_OutHspRec where caseid ="
						+ caseId;
				pstmt = conn.prepareStatement(sql);
				rs2 = pstmt.executeQuery();
				List list = null;
				if (rs2.next()) {
					list = new ArrayList();

					ContinuePage_Sysptom cps1 = new ContinuePage_Sysptom();
					cps1.setSysptom(rs2.getString("outIlls9"));
					list.add(cps1);

					ContinuePage_Sysptom cps2 = new ContinuePage_Sysptom();
					cps2.setSysptom(rs2.getString("outIlls10"));
					list.add(cps2);

					ContinuePage_Sysptom cps3 = new ContinuePage_Sysptom();
					cps3.setSysptom(rs2.getString("outIlls11"));
					list.add(cps3);

					ContinuePage_Sysptom cps4 = new ContinuePage_Sysptom();
					cps4.setSysptom(rs2.getString("outIlls12"));
					list.add(cps4);

					ContinuePage_Sysptom cps5 = new ContinuePage_Sysptom();
					cps5.setSysptom(rs2.getString("outIlls13"));
					list.add(cps5);

					ContinuePage_Sysptom cps6 = new ContinuePage_Sysptom();
					cps6.setSysptom(rs2.getString("outIlls14"));
					list.add(cps6);
					for (int i = 0; i < list.size(); i++) {
						array.add(JSONObject
										.fromObject((ContinuePage_Sysptom) list
												.get(i)));
					}
				}
				// 从病案首页表中查询病人基本信息
				JSONObject tab99 = null;
				List<InHospitalPage> iList = session.createQuery(
						"from InHospitalPage where caseId="+caseId).list();
				for (int i = 0; i < iList.size(); i++) {
					tab99 = JSONObject.fromObject(iList.get(i),
							JSONValueProcessor.formatDate("yyyy-MM-dd"));
				}
				data.put("tab99", tab99);
				data.put("doctor", JSONObject.fromObject(cpd,
						JSONValueProcessor.formatDate("yyyy-MM-dd")));
				System.out.println(array.toString());
				data.put("sysptom", array);
				data.put("ops", JSONObject.fromObject(cpo, JSONValueProcessor
						.formatDate("yyyy-MM-dd")));
				data.put("ward", JSONObject.fromObject(cpw, JSONValueProcessor
						.formatDate("yyyy-MM-dd HH:mm")));
			} else if (state.equals("1")) {
				// 查询病人基本信息
				Connection con = session.connection();
				ResultSet rs = null;
				CallableStatement cs = con
						.prepareCall("{ call pre_TheFirstOfCase_Select(?)}");
				cs.setLong(1, caseId);
				rs = cs.executeQuery();
				if (rs.next()) {
					cpd.setZycs(String.valueOf(rs.getInt("inHspTimes")));
					cpd.setZyh(rs.getString("patientNo"));
					cpd.setPatientName(rs.getString("patientName"));
					cpd.setGender(rs.getString("gender"));
					cpd.setAge(rs.getString("age"));
					cpd.setBirthday(rs.getDate("birthday"));
				}
				// 医生部分信息
				String sql = "SELECT *, id, caseId, kezhuren_show,"
						+ "directorDoctorId_show, treatDoctorId_show,"
						+ "inhspDoctorId_show, jinxiu_show, yanjiusheng_show,"
						+ "shixi_show, bianma, bingan, qcyishi,"
						+ "qchushi, badate, state "
						+ "FROM t_InHsp_InHospitalPage_ContinuePage_Doctor where caseId="
						+ caseId;
				pstmt = con.prepareStatement(sql);
				ResultSet resDoctor = pstmt.executeQuery();
				if (resDoctor.next()) {
					cpd.setKezhuren_show(resDoctor.getString("kezhuren_show"));
					cpd.setDirectorDoctorId_show(resDoctor
							.getString("directorDoctorId_show"));
					cpd.setTreatDoctorId_show(resDoctor
							.getString("treatDoctorId_show"));
					cpd.setInhspDoctorId_show(resDoctor
							.getString("inhspDoctorId_show"));
					cpd.setJinxiu_show(resDoctor.getString("jinxiu_show"));
					cpd.setYanjiusheng_show(resDoctor
							.getString("yanjiusheng_show"));
					cpd.setId(resDoctor.getLong("id"));
					cpd.setShixi_show(resDoctor.getString("shixi_show"));
					cpd.setBianma(resDoctor.getString("bianma"));
					cpd.setBingan(resDoctor.getString("bingan"));
					cpd.setQcyishi(resDoctor.getString("qcyishi"));
					cpd.setQchushi(resDoctor.getString("qchushi"));
					cpd.setBadate(resDoctor.getDate("badate"));
					cpd.setState(resDoctor.getString("state"));
				}
				data.put("doctor", JSONObject.fromObject(cpd,
						JSONValueProcessor.formatDate("yyyy-MM-dd")));

				// 查询诊断部分
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
				List<ContinuePage_Sysptom> sList = session.createQuery(
						"from ContinuePage_Sysptom where caseId =" + caseId)
						.list();
				JSONArray sysptomArray = new JSONArray();
				for (int i = 0; i < sList.size(); i++) {
					sysptomArray.add(JSONObject.fromObject(sList.get(i)));
				}
				data.put("sysptom", sysptomArray);

				// 查询重症监护室部分
				List<ContinuePage_Ward> wList = session.createQuery(
						"from ContinuePage_Ward where caseId =" + caseId)
						.list();
				JSONArray wardArray = new JSONArray();
				for (int i = 0; i < wList.size(); i++) {
					wardArray.add(JSONObject.fromObject(wList.get(i),
							JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm")));
				}
				data.put("ward", wardArray);

				// 查询手术部分
				List<ContinuePage_Ops> oList = session.createQuery(
						"from ContinuePage_Ops where caseId =" + caseId).list();
				JSONArray opsArray = new JSONArray();
				for (int i = 0; i < oList.size(); i++) {
					opsArray.add(JSONObject.fromObject(oList.get(i),
							JSONValueProcessor.formatDate("yyyy-MM-dd")));
				}
				data.put("ops", opsArray);
				
				// 从病案首页表中查询病人基本信息
				JSONObject tab99 = null;
				List<InHospitalPage> iList = session.createQuery(
						"from InHospitalPage where caseId="+caseId).list();
				for (int i = 0; i < iList.size(); i++) {
					tab99 = JSONObject.fromObject(iList.get(i),
							JSONValueProcessor.formatDate("yyyy-MM-dd"));
				}
				data.put("tab99", tab99);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			DatabaseUtil.closeResource(conn, pstmt, r);
		}
		return data;
	}

	/**
	 * 病案首页续页打印
	 * */
	@SuppressWarnings("unchecked")
	public JSONObject ContinuePage_Print(Long caseId) throws Exception {
		Session session = null;
		Transaction tran = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		JSONObject data = new JSONObject();
		JSONArray array = new JSONArray();
		ResultSet rs = null;
		ContinuePage_Doctor cpd = new ContinuePage_Doctor();
		ContinuePage_Sysptom cps = null;
		ContinuePage_Ops cpo = new ContinuePage_Ops();
		ContinuePage_Ward cpw = new ContinuePage_Ward();
		try {
			// 查询病人基本信息
			session = DatabaseUtil.getSession();
			con = DatabaseUtil.getConnection();
			CallableStatement cs = con
					.prepareCall("{ call pre_TheFirstOfCase_Select(?)}");
			cs.setLong(1, caseId);
			rs = cs.executeQuery();
			if (rs.next()) {
				cpd.setZycs(String.valueOf(rs.getInt("inHspTimes")));
				cpd.setZyh(rs.getString("patientNo"));
				cpd.setPatientName(rs.getString("patientName"));
				cpd.setGender(rs.getString("gender"));
				cpd.setAge(rs.getString("age"));
				cpd.setBirthday(rs.getDate("birthday"));
			}
			// 医生部分信息
			String sql = "SELECT *, id, caseId, kezhuren_show,"
					+ "directorDoctorId_show, treatDoctorId_show,"
					+ "inhspDoctorId_show, jinxiu_show, yanjiusheng_show,"
					+ "shixi_show, bianma, bingan, qcyishi,"
					+ "qchushi, badate, state "
					+ "FROM t_InHsp_InHospitalPage_ContinuePage_Doctor where caseId="
					+ caseId;
			pstmt = con.prepareStatement(sql);
			ResultSet resDoctor = pstmt.executeQuery();
			if (resDoctor.next()) {
				cpd.setKezhuren_show(resDoctor.getString("kezhuren_show"));
				cpd.setDirectorDoctorId_show(resDoctor
						.getString("directorDoctorId_show"));
				cpd.setTreatDoctorId_show(resDoctor
						.getString("treatDoctorId_show"));
				cpd.setInhspDoctorId_show(resDoctor
						.getString("inhspDoctorId_show"));
				cpd.setJinxiu_show(resDoctor.getString("jinxiu_show"));
				cpd
						.setYanjiusheng_show(resDoctor
								.getString("yanjiusheng_show"));
				cpd.setId(resDoctor.getLong("id"));
				cpd.setShixi_show(resDoctor.getString("shixi_show"));
				cpd.setBianma(resDoctor.getString("bianma"));
				cpd.setBingan(resDoctor.getString("bingan"));
				cpd.setQcyishi(resDoctor.getString("qcyishi"));
				cpd.setQchushi(resDoctor.getString("qchushi"));
				cpd.setBadate(resDoctor.getDate("badate"));
				cpd.setState(resDoctor.getString("state"));
			}
			data.put("doctor", JSONObject.fromObject(cpd, JSONValueProcessor
					.formatDate("yyyy-MM-dd")));

			// 查询诊断部分
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<ContinuePage_Sysptom> sList = session.createQuery(
					"from ContinuePage_Sysptom where caseId="+caseId).list();
			JSONArray sysptomArray = new JSONArray();
			for (int i = 0; i < sList.size(); i++) {
				sysptomArray.add(JSONObject.fromObject(sList.get(i)));
			}
			data.put("sysptom", sysptomArray);

			// 查询重症监护室部分
			List<ContinuePage_Ward> wList = session.createQuery(
					"from ContinuePage_Ward where caseId="+caseId).list();
			JSONArray wardArray = new JSONArray();
			for (int i = 0; i < wList.size(); i++) {
				wardArray.add(JSONObject.fromObject(wList.get(i),
						JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm")));
			}
			data.put("ward", wardArray);

			// 查询手术部分
			List<ContinuePage_Ops> oList = session.createQuery(
					"from ContinuePage_Ops where caseId="+caseId).list();
			JSONArray opsArray = new JSONArray();
			for (int i = 0; i < oList.size(); i++) {
				opsArray.add(JSONObject.fromObject(oList.get(i),
						JSONValueProcessor.formatDate("yyyy-MM-dd")));
			}
			data.put("ops", opsArray);
			
			// 从病案首页表中查询病人基本信息
			JSONObject tab99 = null;
			List<InHospitalPage> iList = session.createQuery(
					"from InHospitalPage where caseId="+caseId).list();
			for (int i = 0; i < iList.size(); i++) {
				tab99 = JSONObject.fromObject(iList.get(i),
						JSONValueProcessor.formatDate("yyyy-MM-dd"));
			}
			data.put("tab99", tab99);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			DatabaseUtil.closeResource(con, pstmt, rs);
		}
		return data;
	}
}
