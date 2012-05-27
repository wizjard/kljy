package com.juncsoft.KLJY.patientanddoctoroperator.impl;

import java.text.DateFormat;
import java.text.ParseException;
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

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.membergrounp.entity.DepartmentGrounp;
import com.juncsoft.KLJY.membergrounp.entity.MemberDeptOrGrounpRecord;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.patientanddoctoroperator.dao.PatientHealthRecordDao;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.DoctorRoundsRecord;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientConsulting;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientHealthRecord;
import com.juncsoft.KLJY.util.BaseService;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;

public class PatientHealthRecordImpl extends BaseService implements
		PatientHealthRecordDao {

	/**
	 * 查找某个病人的所有健康记录，及医生的查房记录
	 */
	public List findPatientHealthRecordByPatientId(Long patientId,
			Date currentDate, int weekFlag, Long doctorId) {
		Session session = null;
		Transaction tran = null;
		List list = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();

			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId ";
			if (doctorId != null) {
				docSql += " and doctorId=" + doctorId;
			}
			// String doctorHqlDate = null;
			// String patientHqlDate = null;
			// if (patientHqlDate == null && doctorHqlDate == null) {
			// if (currentDate == null) {
			// Date date = new Date();
			// date.setDate(date.getDate() - 7);
			// patientHqlDate = " and writeRecordDate>= '"
			// + date.toLocaleString() + "'";
			// doctorHqlDate = " and roundsDate>= '"
			// + date.toLocaleString() + "'";
			// } else {
			// Date date = currentDate;
			// String dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(date);
			// if (weekFlag == 0) {
			// date.setDate(date.getDate() - 7);
			// patientHqlDate = " and writeRecordDate>= '"
			// + date.toLocaleString()
			// + "' and writeRecordDate<= '" + dates + "'";
			// doctorHqlDate = " and roundsDate>= '"
			// + date.toLocaleString()
			// + "' and roundsDate<= '" + dates + "'";
			// } else {
			// date.setDate(date.getDate() + 7);
			// patientHqlDate = " and writeRecordDate<= '"
			// + date.toLocaleString()
			// + "' and writeRecordDate >='" + dates + "'";
			// doctorHqlDate = " and roundsDate<= '"
			// + date.toLocaleString()
			// + "' and roundsDate >='" + dates + "'";
			// }
			// }
			// }
			List docList = session.createSQLQuery(docSql).list();
			if (docList != null && docList.size() > 0) {
				for (int z = 0, sizez = docList.size(); z < sizez; z++) {
					Object[] docObject = (Object[]) docList.get(z);
					String typeFlagHql = "select distinct(typeFlag) from t_PatientHealthRecord where patientId="
							+ patientId
							+ " and deptCode='"
							+ docObject[1]
							+ "' and grounpId=" + docObject[2];
					List typeFlagList = session.createSQLQuery(typeFlagHql)
							.list();
					if (typeFlagList != null && typeFlagList.size() > 0) {
						boolean execuNull = false;
						for (int i = 0, size = typeFlagList.size(); i < size; i++) {
							Object objList = (Object) typeFlagList.get(i);
							String patientHql = null;
							if (objList == null) {
								execuNull = true;
							} else {
								patientHql = "select * from t_PatientHealthRecord where patientId="
										+ patientId
										+ " and typeFlag="
										+ objList
										+ " and deptCode='"
										+ docObject[1]
										+ "' and grounpId="
										+ docObject[2];

								String doctorHql = "from DoctorRoundsRecord where patientId ="
										+ patientId
										+ " and id="
										+ objList
										+ " and deptCode='"
										+ docObject[1]
										+ "' and grounpId=" + docObject[2];
								// liugang 2011-08-06 start
								// if (doctorId != null) {
								// doctorHql += " and doctorId=" + doctorId;
								// }
								// liugang 2011-08-06 end
								// patientHql += patientHqlDate;
								// doctorHql += doctorHqlDate;
								patientHql += " order by writeRecordDate asc";
								List patientList = session.createSQLQuery(
										patientHql).list();
								if (patientList != null
										&& patientList.size() > 0) {
									for (int j = 0, sizej = patientList.size(); j < sizej; j++) {
										Object[] objOne = (Object[]) patientList
												.get(j);
										PatientHealthRecord patientHealthRecord = new PatientHealthRecord();
										patientHealthRecord
												.setId(Long.parseLong(objOne[0]
														.toString()));
										patientHealthRecord
												.setPatientId(Long
														.parseLong(objOne[1]
																.toString()));
										SimpleDateFormat sdf = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");
										patientHealthRecord
												.setWriteRecordDate(sdf
														.parse(objOne[2]
																.toString()));
										patientHealthRecord
												.setSpiritStatus(objOne[3]
														.toString());
										patientHealthRecord.setDiet(objOne[4]
												.toString());
										patientHealthRecord.setSleep(objOne[5]
												.toString());
										patientHealthRecord.setPiss(objOne[6]
												.toString());
										patientHealthRecord
												.setDefecate(objOne[7]
														.toString());
										patientHealthRecord.setWeight(objOne[8]
												.toString());
										patientHealthRecord
												.setHealthStatus(objOne[9]
														.toString());
										patientHealthRecord
												.setTreatmentStatus(objOne[10]
														.toString());
										patientHealthRecord
												.setImproveStatus(objOne[11]
														.toString());
										if (objOne[12] != null
												&& (!"".equals(objOne[12]))) {
											patientHealthRecord
													.setUploadFile(objOne[12]
															.toString());
										} else {
											patientHealthRecord
													.setUploadFile(null);
										}
										patientHealthRecord
												.setSignature(objOne[13]
														.toString());
										if (objOne[14] != null
												&& (!"".equals(objOne[14]))) {
											patientHealthRecord
													.setUploadImage(objOne[14]
															.toString());
										}

										patientHealthRecord
												.setDeptCode(objOne[15]
														.toString());
										String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
												+ objOne[15].toString() + "'";
										List deptList = session.createSQLQuery(
												hqlCode).list();
										patientHealthRecord
												.setEntityName(objOne[17]
														.toString());
										if (objOne[18] != null) {
											patientHealthRecord
													.setTypeFlag(Long
															.parseLong(objOne[18]
																	.toString()));
										} else {
											patientHealthRecord
													.setTypeFlag(null);
										}
										DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
												.get(
														DepartmentGrounp.class,
														Long
																.parseLong(objOne[19]
																		.toString()));
										patientHealthRecord
												.setDeptName(deptList.get(0)
														.toString()
														+ departmentGrounp
																.getGrounpName());
										list.add(patientHealthRecord);
									}
								}
								List doctorList = session
										.createQuery(doctorHql).list();
								if (doctorList != null && doctorList.size() > 0) {
									for (int d = 0, sized = doctorList.size(); d < sized; d++) {
										list
												.add((DoctorRoundsRecord) doctorList
														.get(d));
									}
								}
								if (i < typeFlagList.size() - 1) {
									Object objM = (Object) typeFlagList
											.get(i + 1);
									String doctorRemainHql = null;
									if (objM == null) {
										doctorRemainHql = "from DoctorRoundsRecord where patientId ="
												+ patientId
												+ " and id > "
												+ objList
												+ " and deptCode='"
												+ docObject[1]
												+ "' and grounpId="
												+ docObject[2];
									} else {
										doctorRemainHql = "from DoctorRoundsRecord where patientId ="
												+ patientId
												+ " and id > "
												+ objList
												+ " and id < "
												+ objM
												+ " and deptCode='"
												+ docObject[1]
												+ "' and grounpId="
												+ docObject[2];
									}
									// if (doctorId != null) {
									// doctorRemainHql += " and doctorId="
									// + doctorId;
									// }
									List doctorRemainList = session
											.createQuery(doctorRemainHql)
											.list();
									if (doctorRemainList != null
											&& doctorRemainList.size() > 0) {
										for (int x = 0, sizex = doctorRemainList
												.size(); x < sizex; x++) {
											list
													.add((DoctorRoundsRecord) doctorRemainList
															.get(x));
										}
									}
								} else {
									String doctorRemainHql = "from DoctorRoundsRecord where patientId ="
											+ patientId
											+ " and id > "
											+ objList
											+ " and deptCode='"
											+ docObject[1]
											+ "' and grounpId="
											+ docObject[2];
									// if (doctorId != null) {
									// doctorRemainHql += " and doctorId="
									// + doctorId;
									// }
									List doctorRemainList = session
											.createQuery(doctorRemainHql)
											.list();
									if (doctorRemainList != null
											&& doctorRemainList.size() > 0) {
										for (int x = 0, sizex = doctorRemainList
												.size(); x < sizex; x++) {
											list
													.add((DoctorRoundsRecord) doctorRemainList
															.get(x));
										}
									}
								}
							}
							if (execuNull && i == typeFlagList.size() - 1) {
								patientHql = "select * from t_PatientHealthRecord where patientId="
										+ patientId
										+ " and typeFlag is null and deptCode='"
										+ docObject[1]
										+ "' and grounpId="
										+ docObject[2];

								// patientHql += patientHqlDate;
								patientHql += " order by writeRecordDate asc";
								List patientListNull = session.createSQLQuery(
										patientHql).list();
								if (patientListNull != null
										&& patientListNull.size() > 0) {
									for (int j = 0, sizej = patientListNull
											.size(); j < sizej; j++) {
										Object[] objOne = (Object[]) patientListNull
												.get(j);
										PatientHealthRecord patientHealthRecord = new PatientHealthRecord();
										patientHealthRecord
												.setId(Long.parseLong(objOne[0]
														.toString()));
										patientHealthRecord
												.setPatientId(Long
														.parseLong(objOne[1]
																.toString()));
										SimpleDateFormat sdf = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");
										patientHealthRecord
												.setWriteRecordDate(sdf
														.parse(objOne[2]
																.toString()));
										patientHealthRecord
												.setSpiritStatus(objOne[3]
														.toString());
										patientHealthRecord.setDiet(objOne[4]
												.toString());
										patientHealthRecord.setSleep(objOne[5]
												.toString());
										patientHealthRecord.setPiss(objOne[6]
												.toString());
										patientHealthRecord
												.setDefecate(objOne[7]
														.toString());
										patientHealthRecord.setWeight(objOne[8]
												.toString());
										patientHealthRecord
												.setHealthStatus(objOne[9]
														.toString());
										patientHealthRecord
												.setTreatmentStatus(objOne[10]
														.toString());
										patientHealthRecord
												.setImproveStatus(objOne[11]
														.toString());
										if (objOne[12] != null
												&& (!"".equals(objOne[12]))) {
											patientHealthRecord
													.setUploadFile(objOne[12]
															.toString());
										} else {
											patientHealthRecord
													.setUploadFile(null);
										}
										patientHealthRecord
												.setSignature(objOne[13]
														.toString());
										if (objOne[14] != null
												&& (!"".equals(objOne[14]))) {
											patientHealthRecord
													.setUploadImage(objOne[14]
															.toString());
										}

										patientHealthRecord
												.setDeptCode(objOne[15]
														.toString());
										String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
												+ objOne[15].toString() + "'";
										List deptList = session.createSQLQuery(
												hqlCode).list();
										patientHealthRecord
												.setEntityName(objOne[17]
														.toString());
										if (objOne[18] != null) {
											patientHealthRecord
													.setTypeFlag(Long
															.parseLong(objOne[18]
																	.toString()));
										} else {
											patientHealthRecord
													.setTypeFlag(null);
										}
										DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
												.get(
														DepartmentGrounp.class,
														Long
																.parseLong(objOne[19]
																		.toString()));
										patientHealthRecord
												.setDeptName(deptList.get(0)
														.toString()
														+ departmentGrounp
																.getGrounpName());
										list.add(patientHealthRecord);
									}
								}
							}
						}
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查找某个病人的所有健康记录，及医生的查房记录出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public void savePatientHealthRecord(
			PatientHealthRecord patientHealthRecord, MemberInfo mem) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
					+ mem.getDeptCode() + "'";
			List deptList = session.createSQLQuery(hql).list();
			patientHealthRecord.setDeptName(deptList.get(0).toString());
			session.save(patientHealthRecord);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("保存会员健康记录出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 查找总记录数
	 */
	public Map findPatientHealthRecordCountByPatientId(Long patientId,
			MemberInfo mem) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String patientHql = "from PatientHealthRecord where patientId="
					+ patientId;
			List patientList = session.createQuery(patientHql).list();
			if (patientList != null && patientList.size() > 0) {
				mp.put("patientListCount", patientList.size());
			} else {
				mp.put("patientListCount", 0);
			}
			String hql = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
					+ mem.getDeptCode() + "'";
			List deptList = session.createSQLQuery(hql).list();
			DepartmentGrounp departmentGrounp = (DepartmentGrounp) session.get(
					DepartmentGrounp.class, mem.getGrounpId());
			mp.put("dapartmentAndgrounp", deptList.get(0).toString()
					+ departmentGrounp.getGrounpName());
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查找总记录数出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	public void updatePatientHealthRecord(
			PatientHealthRecord patientHealthRecord) {
		super.updateObject(patientHealthRecord);
	}

	/**
	 * 医生登录查找病人的健康记录条数
	 */
	public JSONArray patientHealthRecord_treeNodes(Long patientId,
			Date currentDate, int weekFlag, Long doctorId) {
		Session session = null;
		Transaction tran = null;
		JSONArray array = new JSONArray();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId";
			if (doctorId != null) {
				docSql += " and doctorId=" + doctorId;
			}
			List docList = session.createSQLQuery(docSql).list();
			String timeHql = "";
//			if (currentDate == null) {
//				Date date = new Date();
//				date.setDate(date.getDate() - 7);
//				timeHql += " and writeRecordDate>= '" + date.toLocaleString()
//						+ "'";
//			} else {
//				Date date = currentDate;
//				String dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//						.format(date);
//				if (weekFlag == 0) {
//					date.setDate(date.getDate() - 7);
//					timeHql += " and writeRecordDate>= '"
//							+ date.toLocaleString()
//							+ "' and writeRecordDate<= '" + dates + "'";
//				} else {
//					date.setDate(date.getDate() + 7);
//					timeHql += " and writeRecordDate<= '"
//							+ date.toLocaleString()
//							+ "' and writeRecordDate >='" + dates + "'";
//				}
//			}
			if (docList != null && docList.size() > 0) {
				for (int j = 0, sizej = docList.size(); j < sizej; j++) {
					Object[] docObject = (Object[]) docList.get(j);
					String hql = "from PatientHealthRecord where patientId ="
							+ patientId + " and deptCode='" + docObject[1]
							+ "' and grounpId=" + docObject[2];
					hql += timeHql + " order by id desc";
					List<PatientHealthRecord> list = session.createQuery(hql)
							.list();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					// liugang 2011-08-06 start
					for ( int zz =0,sizezz = list.size();zz<sizezz;zz++) {
						PatientHealthRecord record = list.get(zz);
						JSONObject object = new JSONObject();
						object.put("id", record.getId());// 显示顺序
						if (record.getWriteRecordDate() != null) {// 时间标题
							object.put("text", sdf.format(record
									.getWriteRecordDate())
									+ "第" + (zz+1) + "次健康记录");
						} else {
							object.put("text", "The time is not define");
						}
						object.put("leaf", true);
						object.put("iconCls", "icon-none");
						object.put("href", "javascript:scrollTo(" + (zz+1) + ")");
						array.add(object);
					}
					// liugang 2011-08-06 end
				}
			}
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("医生登录查找病人的健康记录条数出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return array;
	}

	/**
	 * 查找书写健康记录的病人列表
	 */
	public JSONObject findPatientHealthRecordList(Long doctorId, int start,
			int limit, int weiCha, int yiCha, JSONObject jsonMap) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		List<Map> listAll = new ArrayList<Map>();
		List<Map> listWeiCha = new ArrayList<Map>();// 未查房
		List<Map> listYiCha = new ArrayList<Map>();// 已查房
		// liugang 2011-08-09 start
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// liugang 2011-08-09 end
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			int sizeAll = 0;
			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="
					+ doctorId;
			List docList = session.createSQLQuery(docSql).list();
			if (docList != null && docList.size() > 0) {
				for (int j = 0, sizej = docList.size(); j < sizej; j++) {
					Object[] docObject = (Object[]) docList.get(j);
					String sql = "select distinct(tp.patientId) from t_PatientHealthRecord tp ";
					if (jsonMap != null) {
						if (jsonMap.get("name") != null
								|| jsonMap.get("patientNo") != null
								|| jsonMap.get("writeRecordDate") != null) {
							sql += " inner join t_patient t on t.id=tp.patientId";
						}
						if (jsonMap.get("name") != null
								&& !("".equals(jsonMap.get("name")))) {
							sql += " and t.patientName like '%"
									+ jsonMap.get("name") + "%'";
						}
						if (jsonMap.get("patientNo") != null
								&& !("".equals(jsonMap.get("patientNo")))) {
							sql += " and t.patientNo='"
									+ jsonMap.get("patientNo") + "'";
						}
						if (jsonMap.get("writeRecordDate") != null
								&& !("".equals(jsonMap.get("writeRecordDate")))) {
							String[] temp = jsonMap.get("writeRecordDate")
									.toString().split(" ");
							SimpleDateFormat format = new SimpleDateFormat(
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
								sql += " and tp.writeRecordDate between '"
										+ d[0].toLocaleString() + "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
					sql += " where tp.deptCode in ('" + docObject[1]
							+ "') and tp.grounpId=" + docObject[2];
					List listSize = session.createSQLQuery(sql).list();
					sizeAll += listSize.size();
					List list = session.createSQLQuery(sql).setFirstResult(
							start).setMaxResults(limit).list();
					if (list != null && list.size() > 0) {
						for (int i = 0, size = list.size(); i < size; i++) {
							Map mp = new HashMap();
							Patient patient = (Patient) session.get(
									Patient.class, Long.parseLong(list.get(i)
											.toString()));
							mp.put("id", patient.getId());
							mp.put("patientName", patient.getPatientName());
							mp.put("hisPId", patient.getPatientId());
							mp.put("gender", DictionaryUtil
									.getIndependentDictionaryText("gender_gb",
											patient.getGender()));
							mp.put("patientNo", patient.getPatientNo());
							mp.put("birthday", sdf.format((Date) patient
									.getBirthday()));
							String temp = DictionaryUtil
									.getIndependentDictionaryText("province",
											patient.getProvince());
							if (!(temp.equals("其它") || temp.equals("其他"))) {
								mp.put("province0", temp);
							}
							temp = DictionaryUtil.getIndependentDictionaryText(
									"occupation", patient.getOccupation());
							if (!(temp.equals("其它") || temp.equals("其他"))) {
								mp.put("occupation0", temp);
							}
							temp = DictionaryUtil
									.getIndependentDictionaryText(
											"marrageStatus", patient
													.getMarrageStatus());
							if (!(temp.equals("其它") || temp.equals("其他"))) {
								mp.put("marrageStatus0", temp);
							}
							mp.put("homeTel", patient.getHomeTel());
							mp.put("contacterName", patient.getContacterName());
							mp.put("contacterTel", patient.getContacterTel());
							mp.put("mobilePhone", patient.getMobilePhone());  //add by cheng jiangyu2011-9-29  查出手机号，用于页面上发送短信用
							String hql = "from PatientHealthRecord where patientId ="
									+ patient.getId()
									+ " and deptCode ='"
									+ docObject[1]
									+ "' and grounpId="
									+ docObject[2]
									+ " order by writeRecordDate desc";
							List listHealthRecord = session.createQuery(hql)
									.list();
							PatientHealthRecord patientHealthRecord = (PatientHealthRecord) listHealthRecord
									.get(0);
							mp
									.put("healthRecordCount", listHealthRecord
											.size());
							// liugang 2011-08-06 start
							// 这样查询的目的是如果会员有转科记录，那么在医生查房记录中什么地方都可以看到
							String isHql = "from PatientHealthRecord where isnotupdate is not null and patientId ="
									+ patient.getId();// 是否转科查询
							List isList = session.createQuery(isHql).list();
							if (isList != null && isList.size() > 0) {
								mp.put("isNotUpdate", 1);// 有转科记录
							} else {
								mp.put("isNotUpdate", 0);
							}

							// liugang 2011-08-06 start
							mp.put("writeRecordDate", patientHealthRecord
									.getWriteRecordDate().toLocaleString());
							if (patientHealthRecord.getTypeFlag() == null) {
								mp.put("typeFlag", "未查房");
							} else {
								mp.put("typeFlag", "已查房");
							}
							if (mp.get("typeFlag").equals("未查房")) {
								listWeiCha.add(mp);
							}
							if (mp.get("typeFlag").equals("已查房")) {
								listYiCha.add(mp);
							}
							// listAll.add(mp);
						}
					}
				}
				// int weiCha,int yiCha
				if (weiCha == 0) {
					for (int wei = 0; wei < listWeiCha.size(); wei++) {
						listAll.add(listWeiCha.get(wei));
					}
					json.put("root", listAll);
				} else if (yiCha == 1) {
					for (int yi = 0; yi < listYiCha.size(); yi++) {
						listAll.add(listYiCha.get(yi));
					}
					json.put("root", listAll);
				} else if (weiCha != 0 && yiCha != 1) {
					for (int wei = 0; wei < listWeiCha.size(); wei++) {
						listAll.add(listWeiCha.get(wei));
					}
					for (int yi = 0; yi < listYiCha.size(); yi++) {
						listAll.add(listYiCha.get(yi));
					}
					json.put("root", listAll);
				}
				json.put("total", sizeAll);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查找书写健康记录的病人列表出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	/**
	 * 加载会员所有的健康记录
	 */
	public List findPatientHealthRecordByPatient(Long patientId,
			Date currentDate, int weekFlag) {
		Session session = null;
		Transaction tran = null;
		List list = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			// String doctorHqlDate = null;
			// String patientHqlDate = null;
			// if (patientHqlDate == null && doctorHqlDate == null) {
			// if (currentDate == null) {
			// Date date = new Date();
			// date.setDate(date.getDate() - 7);
			// patientHqlDate = " and writeRecordDate>= '"
			// + date.toLocaleString() + "'";
			// doctorHqlDate = " and roundsDate>= '"
			// + date.toLocaleString() + "'";
			// } else {
			// Date date = currentDate;
			// String dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(date);
			// if (weekFlag == 0) {
			// date.setDate(date.getDate() - 7);
			// patientHqlDate = " and writeRecordDate>= '"
			// + date.toLocaleString()
			// + "' and writeRecordDate<= '" + dates + "'";
			// doctorHqlDate = " and roundsDate>= '"
			// + date.toLocaleString()
			// + "' and roundsDate<= '" + dates + "'";
			// } else {
			// date.setDate(date.getDate() + 7);
			// patientHqlDate = " and writeRecordDate<= '"
			// + date.toLocaleString()
			// + "' and writeRecordDate >='" + dates + "'";
			// doctorHqlDate = " and roundsDate<= '"
			// + date.toLocaleString()
			// + "' and roundsDate >='" + dates + "'";
			// }
			// }
			// }
			String typeFlagHql = "select distinct(typeFlag) from t_PatientHealthRecord where patientId="
					+ patientId;
			List typeFlagList = session.createSQLQuery(typeFlagHql).list();
			if (typeFlagList != null && typeFlagList.size() > 0) {
				boolean execuNull = false;
				for (int i = 0, size = typeFlagList.size(); i < size; i++) {
					Object objList = (Object) typeFlagList.get(i);
					String patientHql = null;
					if (objList == null) {
						execuNull = true;
					} else {
						patientHql = "select * from t_PatientHealthRecord where patientId="
								+ patientId + " and typeFlag=" + objList;

						String doctorHql = "from DoctorRoundsRecord where patientId ="
								+ patientId + " and id=" + objList;
						// patientHql += patientHqlDate;
						// doctorHql += doctorHqlDate;
						patientHql += " order by writeRecordDate asc";
						List patientList = session.createSQLQuery(patientHql)
								.list();
						if (patientList != null && patientList.size() > 0) {
							for (int j = 0, sizej = patientList.size(); j < sizej; j++) {
								Object[] objOne = (Object[]) patientList.get(j);
								PatientHealthRecord patientHealthRecord = new PatientHealthRecord();
								patientHealthRecord.setId(Long
										.parseLong(objOne[0].toString()));
								patientHealthRecord.setPatientId(Long
										.parseLong(objOne[1].toString()));
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								patientHealthRecord.setWriteRecordDate(sdf
										.parse(objOne[2].toString()));
								patientHealthRecord.setSpiritStatus(objOne[3]
										.toString());
								patientHealthRecord.setDiet(objOne[4]
										.toString());
								patientHealthRecord.setSleep(objOne[5]
										.toString());
								patientHealthRecord.setPiss(objOne[6]
										.toString());
								patientHealthRecord.setDefecate(objOne[7]
										.toString());
								patientHealthRecord.setWeight(objOne[8]
										.toString());
								patientHealthRecord.setHealthStatus(objOne[9]
										.toString());
								patientHealthRecord
										.setTreatmentStatus(objOne[10]
												.toString());
								patientHealthRecord.setImproveStatus(objOne[11]
										.toString());
								if (objOne[12] != null
										&& !"".equals(objOne[12].toString())) {
									patientHealthRecord
											.setUploadFile(objOne[12]
													.toString());
								} else {
									patientHealthRecord.setUploadFile(null);
								}
								patientHealthRecord.setSignature(objOne[13]
										.toString());
								if (objOne[14] != null
										&& !"".equals(objOne[14].toString())) {
									patientHealthRecord
											.setUploadImage(objOne[14]
													.toString());
								}

								patientHealthRecord.setDeptCode(objOne[15]
										.toString());
								String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
										+ objOne[15].toString() + "'";
								List deptList = session.createSQLQuery(hqlCode)
										.list();
								patientHealthRecord.setEntityName(objOne[17]
										.toString());
								if (objOne[18] != null) {
									patientHealthRecord.setTypeFlag(Long
											.parseLong(objOne[18].toString()));
								} else {
									patientHealthRecord.setTypeFlag(null);
								}
								DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
										.get(DepartmentGrounp.class, Long
												.parseLong(objOne[19]
														.toString()));
								patientHealthRecord.setDeptName(deptList.get(0)
										.toString()
										+ departmentGrounp.getGrounpName());
								list.add(patientHealthRecord);
							}
						}
						List doctorList = session.createQuery(doctorHql).list();
						if (doctorList != null && doctorList.size() > 0) {
							for (int d = 0, sized = doctorList.size(); d < sized; d++) {
								list
										.add((DoctorRoundsRecord) doctorList
												.get(d));
							}
						}
						if (i < typeFlagList.size() - 1) {
							Object objM = (Object) typeFlagList.get(i + 1);
							String doctorRemainHql = null;
							if (objM == null) {
								doctorRemainHql = "from DoctorRoundsRecord where patientId ="
										+ patientId + " and id > " + objList;
							} else {
								doctorRemainHql = "from DoctorRoundsRecord where patientId ="
										+ patientId
										+ " and id > "
										+ objList
										+ " and id < " + objM;
							}
							List doctorRemainList = session.createQuery(
									doctorRemainHql).list();
							if (doctorRemainList != null
									&& doctorRemainList.size() > 0) {
								for (int x = 0, sizex = doctorRemainList.size(); x < sizex; x++) {
									list
											.add((DoctorRoundsRecord) doctorRemainList
													.get(x));
								}
							}
						} else {
							String doctorRemainHql = "from DoctorRoundsRecord where patientId ="
									+ patientId + " and id > " + objList;
							List doctorRemainList = session.createQuery(
									doctorRemainHql).list();
							if (doctorRemainList != null
									&& doctorRemainList.size() > 0) {
								for (int x = 0, sizex = doctorRemainList.size(); x < sizex; x++) {
									list
											.add((DoctorRoundsRecord) doctorRemainList
													.get(x));
								}
							}
						}
					}
					if (execuNull && i == typeFlagList.size() - 1) {
						patientHql = "select * from t_PatientHealthRecord where patientId="
								+ patientId + " and typeFlag is null";
						// patientHql += patientHqlDate;
						patientHql += " order by writeRecordDate asc";
						List patientListNull = session.createSQLQuery(
								patientHql).list();
						if (patientListNull != null
								&& patientListNull.size() > 0) {
							for (int j = 0, sizej = patientListNull.size(); j < sizej; j++) {
								Object[] objOne = (Object[]) patientListNull
										.get(j);
								PatientHealthRecord patientHealthRecord = new PatientHealthRecord();
								patientHealthRecord.setId(Long
										.parseLong(objOne[0].toString()));
								patientHealthRecord.setPatientId(Long
										.parseLong(objOne[1].toString()));
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								patientHealthRecord.setWriteRecordDate(sdf
										.parse(objOne[2].toString()));
								patientHealthRecord.setSpiritStatus(objOne[3]
										.toString());
								patientHealthRecord.setDiet(objOne[4]
										.toString());
								patientHealthRecord.setSleep(objOne[5]
										.toString());
								patientHealthRecord.setPiss(objOne[6]
										.toString());
								patientHealthRecord.setDefecate(objOne[7]
										.toString());
								patientHealthRecord.setWeight(objOne[8]
										.toString());
								patientHealthRecord.setHealthStatus(objOne[9]
										.toString());
								patientHealthRecord
										.setTreatmentStatus(objOne[10]
												.toString());
								patientHealthRecord.setImproveStatus(objOne[11]
										.toString());
								if (objOne[12] != null
										&& !"".equals(objOne[14])) {
									patientHealthRecord
											.setUploadFile(objOne[12]
													.toString());
								} else {
									patientHealthRecord.setUploadFile(null);
								}
								patientHealthRecord.setSignature(objOne[13]
										.toString());
								if (objOne[14] != null
										&& !"".equals(objOne[14])) {
									patientHealthRecord
											.setUploadImage(objOne[14]
													.toString());
								}
								patientHealthRecord.setDeptCode(objOne[15]
										.toString());
								String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
										+ objOne[15].toString() + "'";
								List deptList = session.createSQLQuery(hqlCode)
										.list();
								patientHealthRecord.setEntityName(objOne[17]
										.toString());
								if (objOne[18] != null) {
									patientHealthRecord.setTypeFlag(Long
											.parseLong(objOne[18].toString()));
								} else {
									patientHealthRecord.setTypeFlag(null);
								}
								DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
										.get(DepartmentGrounp.class, Long
												.parseLong(objOne[19]
														.toString()));
								patientHealthRecord.setDeptName(deptList.get(0)
										.toString()
										+ departmentGrounp.getGrounpName());
								list.add(patientHealthRecord);
							}
						}
					}
				}

			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查找某个病人的所有健康记录，及医生的查房记录出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	// 病人直接查找所有的健康记录树
	public JSONArray patientHealthRecord_treeNodesByPatient(Long patientId,
			Date currentDate, int weekFlag) {
		Session session = null;
		Transaction tran = null;
		JSONArray array = new JSONArray();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String timeHql = "";
			// if (currentDate == null) {
			// Date date = new Date();
			// date.setDate(date.getDate() - 7);
			// timeHql += " and writeRecordDate>= '" + date.toLocaleString()
			// + "'";
			// } else {
			// Date date = currentDate;
			// String dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(date);
			// if (weekFlag == 0) {
			// date.setDate(date.getDate() - 7);
			// timeHql += " and writeRecordDate>= '"
			// + date.toLocaleString()
			// + "' and writeRecordDate<= '" + dates + "'";
			// } else {
			// date.setDate(date.getDate() + 7);
			// timeHql += " and writeRecordDate<= '"
			// + date.toLocaleString()
			// + "' and writeRecordDate >='" + dates + "'";
			// }
			// }

			String hql = "from PatientHealthRecord where patientId ="
					+ patientId;
			hql += timeHql + " order by id desc";
			List<PatientHealthRecord> list = session.createQuery(hql).list();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for (int i=0,sizei=list.size();i<sizei;i++) {
				JSONObject object = new JSONObject();
				PatientHealthRecord record = list.get(i);
				object.put("id", record.getId());// 显示顺序
				if (record.getWriteRecordDate() != null) {// 时间标题
					object.put("text", sdf.format(record.getWriteRecordDate())
							+ "第" + (i+1) + "次健康记录");
				} else {
					object.put("text", "The time is not define");
				}
				object.put("leaf", true);
				object.put("iconCls", "icon-none");
				object.put("href", "javascript:scrollTo(" + (i+1) + ")");
				array.add(object);
			}

		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("病人直接查找所有的健康记录树出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return array;
	}

	/**
	 * 查询医生是否有未查的健康记录
	 */
	public int findCountPatientHealthRecordList(Long doctorId, int weiCha,
			int yiCha, JSONObject jsonMap) {
		Session session = null;
		Transaction tran = null;
		int flag = 0;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			int sizeAll = 0;
			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="
					+ doctorId;
			List docList = session.createSQLQuery(docSql).list();
			if (docList != null && docList.size() > 0) {
				for (int j = 0, sizej = docList.size(); j < sizej; j++) {
					Object[] docObject = (Object[]) docList.get(j);
					String sql = "select distinct(tp.patientId) from t_PatientHealthRecord tp ";
					sql += " where tp.deptCode in ('" + docObject[1]
							+ "') and tp.grounpId=" + docObject[2];
					List listSize = session.createSQLQuery(sql).list();
					List list = session.createSQLQuery(sql).list();
					if (list != null && list.size() > 0) {
						for (int i = 0, size = list.size(); i < size; i++) {
							Map mp = new HashMap();
							Patient patient = (Patient) session.get(
									Patient.class, Long.parseLong(list.get(i)
											.toString()));
							String hql = "from PatientHealthRecord where patientId ="
									+ patient.getId()
									// liugang 2011-08-06 start
									+ " and deptCode ='"
									+ docObject[1]
									+ "' and grounpId="
									+ docObject[2]
									+ "  order by writeRecordDate desc";
							// liugang 2011-08-06 end
							List listHealthRecord = session.createQuery(hql)
									.list();
							PatientHealthRecord PatientHealthRecord = (PatientHealthRecord) listHealthRecord
									.get(0);
							mp
									.put("healthRecordCount", listHealthRecord
											.size());
							mp.put("writeRecordDate", PatientHealthRecord
									.getWriteRecordDate().toLocaleString());
							if (PatientHealthRecord.getTypeFlag() == null) {
								mp.put("typeFlag", "未查房");
								flag = 1;
							} else {
								mp.put("typeFlag", "已查房");
							}
						}
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查找书写健康记录的病人列表出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return flag;
	}

	public int findCountPatientHealthRecordByPatient(Long patientId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			String typeFlagHql = "from PatientHealthRecord where patientId="
					+ patientId + " and typeFlag is null";
			List typeFlagList = session.createQuery(typeFlagHql).list();

			if (typeFlagList != null && typeFlagList.size() > 0) {
				return 0;
			} else {
				String typeFlagSql = "from PatientHealthRecord where patientId="
						+ patientId;
				List typeFlagSqlList = session.createQuery(typeFlagSql).list();
				if (typeFlagList != null && typeFlagList.size() > 0) {
					return 1;
				} else {
					return 0;
				}
			}
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查找某个病人的所有健康记录，及医生的查房记录出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public List findPatientHealthRecordHistory(Long patientId, Long doctorId) {
		// 如果当前会员以前有转科记录
		Session session = null;
		Transaction tran = null;
		List list = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId  and doctorId="
					+ doctorId;
			List docList = session.createSQLQuery(docSql).list();
			if (docList != null && docList.size() > 0) {
				Object[] docObject = (Object[]) docList.get(0);
				String sendHqlID = "select id from t_MemberDeptOrGrounpRecord where patientId ="
						+ patientId +" and DeptCode ='"+docObject[1]+"' and GrounpId="+docObject[2];
				List sendListID = session.createSQLQuery(sendHqlID).list();
				//查询现在科室以前的所有健康记录
				if(sendListID != null && sendListID.size() > 0){
					String sendHql = "from MemberDeptOrGrounpRecord where patientId ="
						+ patientId +" and id <= "+sendListID.get(0);
					List sendList = session.createQuery(sendHql).list();
					
					if (sendList != null && sendList.size() > 0) {
						for (int si = 0, sizeSi = sendList.size(); si < sizeSi; si++) {
							MemberDeptOrGrounpRecord memberDeptOrGrounpRecord = (MemberDeptOrGrounpRecord) sendList
									.get(si);
							String typeFlagHql = "select distinct(typeFlag) from t_PatientHealthRecord where patientId="
									+ patientId
									+ " and deptCode='"
									+ memberDeptOrGrounpRecord.getOldDeptCode()
									+ "' and grounpId="
									+ memberDeptOrGrounpRecord.getOldGrounpId();
							List typeFlagList = session.createSQLQuery(typeFlagHql)
									.list();
							if (typeFlagList != null && typeFlagList.size() > 0) {
								boolean execuNull = false;
								for (int i = 0, size = typeFlagList.size(); i < size; i++) {
									Object objList = (Object) typeFlagList.get(i);
									String patientHql = null;
									if (objList == null) {
										execuNull = true;
									} else {
										patientHql = "select * from t_PatientHealthRecord where patientId="
												+ patientId
												+ " and typeFlag="
												+ objList
												+ " and deptCode='"
												+ memberDeptOrGrounpRecord
														.getOldDeptCode()
												+ "' and grounpId="
												+ memberDeptOrGrounpRecord
														.getOldGrounpId();

										String doctorHql = "from DoctorRoundsRecord where patientId ="
												+ patientId
												+ " and id="
												+ objList
												+ " and deptCode='"
												+ memberDeptOrGrounpRecord
														.getOldDeptCode()
												+ "' and grounpId="
												+ memberDeptOrGrounpRecord
														.getOldGrounpId();
										patientHql += " order by writeRecordDate asc";
										List patientList = session.createSQLQuery(
												patientHql).list();
										if (patientList != null
												&& patientList.size() > 0) {
											for (int j = 0, sizej = patientList
													.size(); j < sizej; j++) {
												Object[] objOne = (Object[]) patientList
														.get(j);
												PatientHealthRecord patientHealthRecord = new PatientHealthRecord();
												patientHealthRecord.setId(Long
														.parseLong(objOne[0]
																.toString()));
												patientHealthRecord
														.setPatientId(Long
																.parseLong(objOne[1]
																		.toString()));
												SimpleDateFormat sdf = new SimpleDateFormat(
														"yyyy-MM-dd HH:mm:ss");
												patientHealthRecord
														.setWriteRecordDate(sdf
																.parse(objOne[2]
																		.toString()));
												patientHealthRecord
														.setSpiritStatus(objOne[3]
																.toString());
												patientHealthRecord
														.setDiet(objOne[4]
																.toString());
												patientHealthRecord
														.setSleep(objOne[5]
																.toString());
												patientHealthRecord
														.setPiss(objOne[6]
																.toString());
												patientHealthRecord
														.setDefecate(objOne[7]
																.toString());
												patientHealthRecord
														.setWeight(objOne[8]
																.toString());
												patientHealthRecord
														.setHealthStatus(objOne[9]
																.toString());
												patientHealthRecord
														.setTreatmentStatus(objOne[10]
																.toString());
												patientHealthRecord
														.setImproveStatus(objOne[11]
																.toString());
												if (objOne[12] != null
														&& (!"".equals(objOne[12]))) {
													patientHealthRecord
															.setUploadFile(objOne[12]
																	.toString());
												} else {
													patientHealthRecord
															.setUploadFile(null);
												}
												patientHealthRecord
														.setSignature(objOne[13]
																.toString());
												if (objOne[14] != null
														&& (!"".equals(objOne[14]))) {
													patientHealthRecord
															.setUploadImage(objOne[14]
																	.toString());
												}

												patientHealthRecord
														.setDeptCode(objOne[15]
																.toString());
												String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
														+ objOne[15].toString()
														+ "'";
												List deptList = session
														.createSQLQuery(hqlCode)
														.list();
												patientHealthRecord
														.setEntityName(objOne[17]
																.toString());
												if (objOne[18] != null) {
													patientHealthRecord
															.setTypeFlag(Long
																	.parseLong(objOne[18]
																			.toString()));
												} else {
													patientHealthRecord
															.setTypeFlag(null);
												}
												DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
														.get(
																DepartmentGrounp.class,
																Long
																		.parseLong(objOne[19]
																				.toString()));
												patientHealthRecord
														.setDeptName(deptList
																.get(0).toString()
																+ departmentGrounp
																		.getGrounpName());
												list.add(patientHealthRecord);
											}
										}
										List doctorList = session.createQuery(
												doctorHql).list();
										if (doctorList != null
												&& doctorList.size() > 0) {
											for (int d = 0, sized = doctorList
													.size(); d < sized; d++) {
												list
														.add((DoctorRoundsRecord) doctorList
																.get(d));
											}
										}
										if (i < typeFlagList.size() - 1) {
											Object objM = (Object) typeFlagList
													.get(i + 1);
											String doctorRemainHql = null;
											if (objM == null) {
												doctorRemainHql = "from DoctorRoundsRecord where patientId ="
														+ patientId
														+ " and id > "
														+ objList
														+ " and deptCode='"
														+ memberDeptOrGrounpRecord
																.getOldDeptCode()
														+ "' and grounpId="
														+ memberDeptOrGrounpRecord
																.getOldGrounpId();
											} else {
												doctorRemainHql = "from DoctorRoundsRecord where patientId ="
														+ patientId
														+ " and id > "
														+ objList
														+ " and id < "
														+ objM
														+ " and deptCode='"
														+ memberDeptOrGrounpRecord
																.getOldDeptCode()
														+ "' and grounpId="
														+ memberDeptOrGrounpRecord
																.getOldGrounpId();
											}
											// if (doctorId != null) {
											// doctorRemainHql += " and doctorId="
											// + doctorId;
											// }
											List doctorRemainList = session
													.createQuery(doctorRemainHql)
													.list();
											if (doctorRemainList != null
													&& doctorRemainList.size() > 0) {
												for (int x = 0, sizex = doctorRemainList
														.size(); x < sizex; x++) {
													list
															.add((DoctorRoundsRecord) doctorRemainList
																	.get(x));
												}
											}
										} else {
											String doctorRemainHql = "from DoctorRoundsRecord where patientId ="
													+ patientId
													+ " and id > "
													+ objList
													+ " and deptCode='"
													+ memberDeptOrGrounpRecord
															.getOldDeptCode()
													+ "' and grounpId="
													+ memberDeptOrGrounpRecord
															.getOldGrounpId();
											// if (doctorId != null) {
											// doctorRemainHql += " and doctorId="
											// + doctorId;
											// }
											List doctorRemainList = session
													.createQuery(doctorRemainHql)
													.list();
											if (doctorRemainList != null
													&& doctorRemainList.size() > 0) {
												for (int x = 0, sizex = doctorRemainList
														.size(); x < sizex; x++) {
													list
															.add((DoctorRoundsRecord) doctorRemainList
																	.get(x));
												}
											}
										}
									}
									if (execuNull && i == typeFlagList.size() - 1) {
										patientHql = "select * from t_PatientHealthRecord where patientId="
												+ patientId
												+ " and typeFlag is null and deptCode='"
												+ memberDeptOrGrounpRecord
														.getOldDeptCode()
												+ "' and grounpId="
												+ memberDeptOrGrounpRecord
														.getOldGrounpId();

										// patientHql += patientHqlDate;
										patientHql += " order by writeRecordDate asc";
										List patientListNull = session
												.createSQLQuery(patientHql).list();
										if (patientListNull != null
												&& patientListNull.size() > 0) {
											for (int j = 0, sizej = patientListNull
													.size(); j < sizej; j++) {
												Object[] objOne = (Object[]) patientListNull
														.get(j);
												PatientHealthRecord patientHealthRecord = new PatientHealthRecord();
												patientHealthRecord.setId(Long
														.parseLong(objOne[0]
																.toString()));
												patientHealthRecord
														.setPatientId(Long
																.parseLong(objOne[1]
																		.toString()));
												SimpleDateFormat sdf = new SimpleDateFormat(
														"yyyy-MM-dd HH:mm:ss");
												patientHealthRecord
														.setWriteRecordDate(sdf
																.parse(objOne[2]
																		.toString()));
												patientHealthRecord
														.setSpiritStatus(objOne[3]
																.toString());
												patientHealthRecord
														.setDiet(objOne[4]
																.toString());
												patientHealthRecord
														.setSleep(objOne[5]
																.toString());
												patientHealthRecord
														.setPiss(objOne[6]
																.toString());
												patientHealthRecord
														.setDefecate(objOne[7]
																.toString());
												patientHealthRecord
														.setWeight(objOne[8]
																.toString());
												patientHealthRecord
														.setHealthStatus(objOne[9]
																.toString());
												patientHealthRecord
														.setTreatmentStatus(objOne[10]
																.toString());
												patientHealthRecord
														.setImproveStatus(objOne[11]
																.toString());
												if (objOne[12] != null
														&& (!"".equals(objOne[12]))) {
													patientHealthRecord
															.setUploadFile(objOne[12]
																	.toString());
												} else {
													patientHealthRecord
															.setUploadFile(null);
												}
												patientHealthRecord
														.setSignature(objOne[13]
																.toString());
												if (objOne[14] != null
														&& (!"".equals(objOne[14]))) {
													patientHealthRecord
															.setUploadImage(objOne[14]
																	.toString());
												}

												patientHealthRecord
														.setDeptCode(objOne[15]
																.toString());
												String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
														+ objOne[15].toString()
														+ "'";
												List deptList = session
														.createSQLQuery(hqlCode)
														.list();
												patientHealthRecord
														.setEntityName(objOne[17]
																.toString());
												if (objOne[18] != null) {
													patientHealthRecord
															.setTypeFlag(Long
																	.parseLong(objOne[18]
																			.toString()));
												} else {
													patientHealthRecord
															.setTypeFlag(null);
												}
												DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
														.get(
																DepartmentGrounp.class,
																Long
																		.parseLong(objOne[19]
																				.toString()));
												patientHealthRecord
														.setDeptName(deptList
																.get(0).toString()
																+ departmentGrounp
																		.getGrounpName());
												list.add(patientHealthRecord);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
}
