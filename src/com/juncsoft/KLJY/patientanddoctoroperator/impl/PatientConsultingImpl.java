package com.juncsoft.KLJY.patientanddoctoroperator.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.patientanddoctoroperator.dao.PatientConsultingDao;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.DoctorReplyRecordAndPatientQuestions;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientConsulting;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientConsultingMessage;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.BaseService;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class PatientConsultingImpl extends BaseService implements
		PatientConsultingDao {

	/**
	 * 查找某个病人的所有咨询问题
	 */
	public List<Map> findAllPatientConsultingByPatientId(Long patientId,
			Long pcId, Long para, Date currentDate, int weekFlag) {
		Session session = null;
		Transaction tran = null;
		List<Map> pcList = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from PatientConsulting where 1=1";
			if (patientId != null) {
				hql += " and patientId =" + patientId;
			}
			if (pcId != null) {
				hql += " and id=" + pcId;
			}
			// else {
			// if (currentDate == null) {
			// Date date = new Date();
			// date.setDate(date.getDate() - 7);
			// hql += " and consultingDate>= '" + date.toLocaleString()
			// + "'";
			// } else {
			// Date date = currentDate;
			// String dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			// .format(date);
			// if (weekFlag == 0) {
			// date.setDate(date.getDate() - 7);
			// hql += " and consultingDate>= '"
			// + date.toLocaleString()
			// + "' and consultingDate<= '" + dates + "'";
			// } else {
			// date.setDate(date.getDate() + 7);
			// hql += " and consultingDate<= '"
			// + date.toLocaleString()
			// + "' and consultingDate >='" + dates + "'";
			// }
			// }
			// }
			hql += " order by consultingDate desc";
			List<PatientConsulting> list = session.createQuery(hql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					Map mp = new HashMap();
					PatientConsulting pc = list.get(i);
					String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
							+ pc.getDeptCode() + "'";
					List deptList = session.createSQLQuery(hqlCode).list();
					DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
							.get(DepartmentGrounp.class, pc.getGrounpId());
					mp.put("nameAndGrounp", deptList.get(0).toString()
							+ departmentGrounp.getGrounpName());
					ArrayList zixunList = new ArrayList();// 存放咨询科室列表
					if (pc.getIsNotSend() != null) {// 判断是否有咨询科室
						String ziHql = "from PatientConsulting where isNotSend ="
								+ pc.getIsNotSend();
						List ziList = session.createQuery(ziHql).list();
						if (ziList != null && ziList.size() > 0) {
							for (int zx = 0, sizeZx = ziList.size(); zx < sizeZx; zx++) {
								PatientConsulting patientConsulting = (PatientConsulting) ziList
										.get(zx);
								if (patientConsulting.getPatientId() == null) {
									String hqlCodeInner = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
											+ patientConsulting.getDeptCode()
											+ "'";
									List deptListInner = session
											.createSQLQuery(hqlCodeInner)
											.list();
									DepartmentGrounp departmentGrounpInner = (DepartmentGrounp) session
											.get(DepartmentGrounp.class,
													patientConsulting
															.getGrounpId());
									zixunList.add(deptListInner.get(0)
											.toString()
											+ departmentGrounpInner
													.getGrounpName());
								}
							}
							mp.put("zixundeptName", zixunList);
						}
					} else {
						zixunList.add(deptList.get(0).toString()
								+ departmentGrounp.getGrounpName());
						mp.put("zixundeptName", zixunList);
					}
					mp.put("id", pc.getId());
					mp.put("patientId", pc.getPatientId());
					Patient patient = (Patient) session.get(Patient.class, pc
							.getPatientId());
					mp.put("hisPId", patient.getPatientId());
					mp.put("patientName", patient.getPatientName());
					mp.put("consultingCount", pc.getConsultingCount());
					mp.put("consultingDate", pc.getConsultingDate()
							.toLocaleString());
					mp.put("spiritStatus", pc.getSpiritStatus());
					mp.put("diet", pc.getDiet());
					mp.put("sleep", pc.getSleep());
					mp.put("piss", pc.getPiss());
					mp.put("defecate", pc.getDefecate());
					mp.put("weight", pc.getWeight());
					mp.put("healthStatus", pc.getHealthStatus());
					mp.put("treatmentStatus", pc.getTreatmentStatus());
					mp.put("improveStatus", pc.getImproveStatus());
					mp.put("uploadFile", pc.getUploadFile());
					mp.put("signature", pc.getSignature());
					mp.put("deptCode", pc.getDeptCode());
					mp.put("mainProblem", pc.getMainProblem());
					mp.put("uploadImage", pc.getUploadImage());
					mp.put("isNotSend", pc.getIsNotSend());
					mp.put("pcMeetingState", pc.getPcMeetingState());
					String hqlCount = "from DoctorReplyRecordAndPatientQuestions where pcId="
							+ pc.getId();
					List listCount = session.createQuery(hqlCount).list();
					if (listCount != null) {
						mp.put("count", listCount.size());
					} else {
						mp.put("count", 0);
					}
					pcList.add(mp);
				}
				if (para != null) {
					String updateHql = "from DoctorReplyRecordAndPatientQuestions where pcId="
							+ pcId
							+ " and drAndpgReadPatient = "
							+ para
							+ " and drAndpqFlag = 0";
					List updateList = session.createQuery(updateHql).list();
					if (updateList != null && updateList.size() > 0) {
						for (int j = 0, sizej = updateList.size(); j < sizej; j++) {
							DoctorReplyRecordAndPatientQuestions updateDoctorReplyRecordAndPatientQuestions = (DoctorReplyRecordAndPatientQuestions) updateList
									.get(j);
							updateDoctorReplyRecordAndPatientQuestions
									.setDrAndpgReadPatient(1);
							session
									.update(updateDoctorReplyRecordAndPatientQuestions);
						}
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("根据病人的ID查找病人的所有咨询出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return pcList;
	}

	/**
	 * 医生登录显示病人的咨询问题
	 */
	public JSONObject findPatientConsultingByDoctor(Long doctorId, int start,
			int limit, int flagDai, int flagYi, int flagEnd, JSONObject jsonMap) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		List<Map> listAll = new ArrayList<Map>();
		List<Map> listDai = new ArrayList<Map>();// 待回复列表
		List<Map> listYi = new ArrayList<Map>();// 已回复列表
		List<Map> listEnd = new ArrayList<Map>();// 咨询结束列表
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId ";
			if (doctorId != null) {
				docSql += " and doctorId=" + doctorId;
			}
			List docList = session.createSQLQuery(docSql).list();
			if (docList != null && docList.size() > 0) {
				int sizeCount = 0;
				for (int z = 0, sizez = docList.size(); z < sizez; z++) {
					Object[] docObject = (Object[]) docList.get(z);
					String hql = "select p.id from t_PatientConsulting p ";
					if (jsonMap != null) {
						if (jsonMap.get("name") != null
								&& !("".equals(jsonMap.get("name")))) {
							hql += " inner join t_patient t on t.id=p.patientId and t.patientName like '%"
									+ jsonMap.get("name") + "%'";
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
							if (jsonMap.get("name") != null
									&& !("".equals(jsonMap.get("name")))) {
								hql += " and p.consultingDate between '"
										+ format.format(d[0]) + "' and '"
										+ format.format(d[1]) + "'";
							} else {
								hql += " where p.consultingDate between '"
										+ format.format(d[0]) + "' and '"
										+ format.format(d[1]) + "'";
							}
						}
					}
					if (jsonMap != null) {
						hql += " and p.deptCode='" + docObject[1]
									+ "' and p.grounpId=" + docObject[2]
									+ " order by p.id desc";
					} else {
						hql += " where p.deptCode='" + docObject[1]
								+ "' and p.grounpId=" + docObject[2]
								+ " order by p.id desc";
					}

					List listSize = session.createSQLQuery(hql).list();
					sizeCount += listSize.size();
					List list = session.createSQLQuery(hql).setFirstResult(
							start).setMaxResults(limit).list();
					if (list != null && list.size() > 0) {
						for (int i = 0, size = list.size(); i < size; i++) {
							Map mp = new HashMap();
							PatientConsulting pc = (PatientConsulting) session
									.get(PatientConsulting.class, Long
											.parseLong(list.get(i).toString()));
							if (pc.getPcMeetingState() != null) {
								mp.put("reply", "咨询结束");
								if (pc.getIsNotSend() == null) {
								} else {
									pc = (PatientConsulting) session.get(
											PatientConsulting.class, pc
													.getIsNotSend());
								}
								String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
										+ pc.getDeptCode() + "'";
								List deptList = session.createSQLQuery(hqlCode)
										.list();
								DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
										.get(DepartmentGrounp.class, pc
												.getGrounpId());
								mp.put("deptName", deptList.get(0).toString()
										+ departmentGrounp.getGrounpName());
							} else {
								if (pc.getIsNotSend() == null) {
									String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
											+ pc.getDeptCode() + "'";
									List deptList = session.createSQLQuery(
											hqlCode).list();
									DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
											.get(DepartmentGrounp.class, pc
													.getGrounpId());
									mp.put("zixundeptName", deptList.get(0)
											.toString()
											+ departmentGrounp.getGrounpName());
									// liugang 2011-08-06 start
									String hqlNew = "from DoctorReplyRecordAndPatientQuestions where pcId ="
											+ pc.getId()
											+ " and deptCode='"
											+ docObject[1]
											+ "' and grounpId="
											+ docObject[2]
											+ " order by id desc";
									// liugang 2011-08-06 end
									List listNew = session.createQuery(hqlNew)
											.list();
									if (listNew != null && listNew.size() > 0) {
										DoctorReplyRecordAndPatientQuestions doctorReplyRecordAndPatientQuestions = (DoctorReplyRecordAndPatientQuestions) listNew
												.get(0);
										if (doctorReplyRecordAndPatientQuestions
												.getDrAndpgCancel() == 1) {
											if (doctorReplyRecordAndPatientQuestions
													.getDrAndpqFlag() == 1) {
												mp.put("reply", "待回复");
											} else {
												String hqlNewD = "from DoctorReplyRecordAndPatientQuestions where pcId ="
														+ pc.getId()
														// liugang 2011-08-06
														// start
														+ " and drAndpqFlag=0 and deptCode='"
														+ docObject[1]
														+ "' and grounpId="
														+ docObject[2]
														+ " order by id desc";
												// liugang 2011-08-06 end
												List listNewD = session
														.createQuery(hqlNewD)
														.list();
												if (listNewD != null
														&& listNewD.size() > 0) {
													mp.put("reply", "已回复");
												} else {
													mp.put("reply", "待回复");
												}
											}
										}
										// else {
										// mp.put("reply", "取消回复");
										// }
									} else {
										mp.put("reply", "待回复");
									}
									mp.put("deptName", deptList.get(0)
											.toString()
											+ departmentGrounp.getGrounpName());
								} else {
									String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
											+ pc.getDeptCode() + "'";
									List deptList = session.createSQLQuery(
											hqlCode).list();
									DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
											.get(DepartmentGrounp.class, pc
													.getGrounpId());
									mp.put("zixundeptName", deptList.get(0)
											.toString()
											+ departmentGrounp.getGrounpName());
									pc = (PatientConsulting) session.get(
											PatientConsulting.class, pc
													.getIsNotSend());
									// liugang 2011-08-06 start
									String hqlNewPatient = "select drAndpqDate from DoctorReplyRecordAndPatientQuestions where pcId ="
											+ pc.getId()
											+ " and drAndpqFlag =1 order by id desc";// 此处不需要添加任何科室的判断读取会员最后一次发咨询的时间
									List hqlNewPaList = session.createQuery(
											hqlNewPatient).list();
									String hqlNew = "";
									if (hqlNewPaList != null
											&& hqlNewPaList.size() > 0) {
										hqlNew = "from DoctorReplyRecordAndPatientQuestions where pcId ="
												+ pc.getId()
												+ " and drAndpqDate >='"
												+ hqlNewPaList.get(0)
												+ "' and deptCode='"
												+ docObject[1]
												+ "' and grounpId="
												+ docObject[2]
												+ " and drAndpqFlag = 0";
									} else {
										hqlNew = "from DoctorReplyRecordAndPatientQuestions where pcId ="
												+ pc.getId()
												+ "and deptCode='"
												+ docObject[1]
												+ "' and grounpId="
												+ docObject[2]
												+ " and drAndpqFlag = 0";
									}
									// liugang 2011-08-06 end
									List listNew = session.createQuery(hqlNew)
											.list();
									if (listNew != null && listNew.size() > 0) {
										mp.put("reply", "已回复");
									} else {
										mp.put("reply", "待回复");
									}
									String hqlCodeOld = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
											+ pc.getDeptCode() + "'";
									List deptListOld = session.createSQLQuery(
											hqlCodeOld).list();
									DepartmentGrounp departmentGrounpOld = (DepartmentGrounp) session
											.get(DepartmentGrounp.class, pc
													.getGrounpId());
									mp.put("deptName", deptListOld.get(0)
											.toString()
											+ departmentGrounpOld
													.getGrounpName());
									if (pc.getPcMeetingState() != null) {
										mp.put("reply", "咨询结束");
									}
								}
							}

							mp.put("id", pc.getId());
							mp.put("patientId", pc.getPatientId());
							Patient patient = (Patient) session.get(
									Patient.class, pc.getPatientId());
							mp.put("mobilePhone", patient.getMobilePhone());
							mp.put("hisPId", patient.getPatientId());
							mp.put("isNotSend", pc.getIsNotSend());// 是否转发
							// liugang 2011-08-06 start
							String isHql = "from PatientConsulting where isNotUpdate is not null and patientId ="
									+ patient.getId();// 是否转科查询
							List isList = session.createQuery(isHql).list();
							if (isList != null && isList.size() > 0) {
								mp.put("isNotUpdate", 1);// 有转科记录
							} else {
								mp.put("isNotUpdate", 0);
							}
							// liugang 2011-08-06 end
							mp.put("consultingCount", pc.getConsultingCount());
							mp.put("consultingDate", pc.getConsultingDate()
									.toLocaleString());
							mp.put("signature", pc.getSignature());
							if (mp.get("reply").equals("待回复")) {
								listDai.add(mp);
							} else if (mp.get("reply").equals("已回复")) {
								listYi.add(mp);
							} else if (mp.get("reply").equals("咨询结束")) {
								listEnd.add(mp);
							}
							// 添加责任科室标记
							mp.put("currentDeptCode", docObject[1]);// 每条咨询后面都添加一条当前科室
						}
					}
				}
				// ,int flagDai,int flagYi
				if (flagDai == 0) {
					for (int dai = listDai.size() - 1; dai >= 0; dai--) {
						listAll.add(listDai.get(dai));
					}
					json.put("root", listAll);
					json.put("total", listAll.size());
				} else if (flagYi == 1) {
					for (int yi = 0; yi < listYi.size(); yi++) {
						listAll.add(listYi.get(yi));
					}
					json.put("root", listAll);
					json.put("total", listAll.size());
				} else if (flagEnd == 2) {
					for (int end = 0; end < listEnd.size(); end++) {
						listAll.add(listEnd.get(end));
					}
					json.put("root", listAll);
					json.put("total", listAll.size());
				} else if (flagDai != 0 && flagYi != 1 && flagEnd != 2) {
					for (int dai = listDai.size() - 1; dai >= 0; dai--) {
						listAll.add(listDai.get(dai));
					}
					for (int yi = 0, sizeYi = listYi.size(); yi < sizeYi; yi++) {
						listAll.add(listYi.get(yi));
					}
					for (int end = 0, sizeEnd = listEnd.size(); end < sizeEnd; end++) {
						listAll.add(listEnd.get(end));
					}
					json.put("root", listAll);
					json.put("total", sizeCount);
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("医生登录显示病人的咨询问题出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	/**
	 * 病人登录显示病人的咨询问题
	 */
	public JSONObject findPatientConsultingByPatient(Long patientId, int start,
			int limit, int flagWeiHui, int flagWeiDu, int flagYiDu) {
		JSONObject json = new JSONObject();
		Session session = null;
		Transaction tran = null;
		List<Map> listAll = new ArrayList<Map>();
		List<Map> listWeiHui = new ArrayList<Map>();
		List<Map> listWeiDu = new ArrayList<Map>();
		List<Map> listYiDu = new ArrayList<Map>();

		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from PatientConsulting where patientId = "
					+ patientId + " order by id desc";
			List listSize = session.createQuery(hql).list();
			List list = session.createQuery(hql).setFirstResult(start)
					.setMaxResults(limit).list();
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					Map mp = new HashMap();
					PatientConsulting pc = (PatientConsulting) list.get(i);
					mp.put("id", pc.getId());
					mp.put("patientId", pc.getPatientId());
					mp.put("consultingCount", pc.getConsultingCount());
					mp.put("readCount", pc.getReadCount());// 是否已经读过
					String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
							+ pc.getDeptCode() + "'";
					List deptList = session.createSQLQuery(hqlCode).list();
					DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
							.get(DepartmentGrounp.class, pc.getGrounpId());
					mp.put("deptName", deptList.get(0).toString()
							+ departmentGrounp.getGrounpName());
					mp.put("consultingDate", pc.getConsultingDate()
							.toLocaleString());
					// liugang 2011-08-06 start
					mp.put("pcMeetingState", pc.getPcMeetingState());
					// liugang 2011-08-06 end
					if (pc.getPcMeetingState() != null) {
						mp.put("reply", "咨询结束");// 就是已回复
					} else {
						String hqlNew = "from DoctorReplyRecordAndPatientQuestions where pcId ="
								+ pc.getId() + " order by id desc";
						List listNew = session.createQuery(hqlNew).list();
						if (listNew != null && listNew.size() > 0) {
							DoctorReplyRecordAndPatientQuestions doctorReplyRecordAndPatientQuestions = (DoctorReplyRecordAndPatientQuestions) listNew
									.get(0);
							if (doctorReplyRecordAndPatientQuestions
									.getDrAndpqFlag() == 0) {
								mp.put("doctorName",
										doctorReplyRecordAndPatientQuestions
												.getDrAndpqName());
								// mp.put("reply", "取消回复");
								mp.put("reply", "未读");// 就是已回复
							} else if (doctorReplyRecordAndPatientQuestions
									.getDrAndpqFlag() == 1) {
								mp.put("doctorName", "");
								// mp.put("reply", "取消回复");
								mp.put("reply", "未回复");
							}
							// else if (doctorReplyRecordAndPatientQuestions
							// .getDrAndpgCancel() == 1) {
							// if (doctorReplyRecordAndPatientQuestions
							// .getDrAndpqFlag() == 0
							// && doctorReplyRecordAndPatientQuestions
							// .getDrAndpgReadPatient() == 0) {
							// mp.put("doctorName",
							// doctorReplyRecordAndPatientQuestions
							// .getDrAndpqName());
							// mp.put("reply", "未读");
							// } else {
							// String hqlNewD = "from
							// DoctorReplyRecordAndPatientQuestions where pcId
							// ="
							// + pc.getId()
							// + " and drAndpqFlag=0 order by id desc";
							// List listNewD = session.createQuery(hqlNewD)
							// .list();
							// if (listNewD != null && listNewD.size() > 0) {
							// DoctorReplyRecordAndPatientQuestions
							// doctorReplyRecordAndPatientQuestionsD =
							// (DoctorReplyRecordAndPatientQuestions) listNewD
							// .get(0);
							// mp.put("doctorName",
							// doctorReplyRecordAndPatientQuestionsD
							// .getDrAndpqName());
							// mp.put("reply", "已回复");
							// } else {
							// mp.put("doctorName", "");
							// mp.put("reply", "未回复");
							// }
							// }
							// }
						} else {
							mp.put("doctorName", "");
							mp.put("reply", "未回复");
						}
					}
					if (mp.get("reply").equals("未读")) {
						listWeiDu.add(mp);
					}
					if (mp.get("reply").equals("咨询结束")) {
						listYiDu.add(mp);
					}
					if (mp.get("reply").equals("未回复")) {
						listWeiHui.add(mp);
					}
				}
			}
			if (flagWeiDu == 0) {
				for (int weiDu = 0; weiDu < listWeiDu.size(); weiDu++) {
					listAll.add(listWeiDu.get(weiDu));
				}
				// for (int yiDu = 0; yiDu < listYiDu.size(); yiDu++) {
				// listAll.add(listYiDu.get(yiDu));
				// }
				json.put("root", listAll);
			} else if (flagWeiHui == 1) {
				for (int weiHui = 0; weiHui < listWeiHui.size(); weiHui++) {
					listAll.add(listWeiHui.get(weiHui));
				}
				json.put("root", listAll);
			} else if (flagYiDu == 2) {
				for (int yiDu = 0; yiDu < listYiDu.size(); yiDu++) {
					listAll.add(listYiDu.get(yiDu));
				}
				json.put("root", listAll);
			} else if (flagWeiDu != 0 && flagWeiHui != 1 && flagYiDu != 2) {
				for (int weiDu = 0; weiDu < listWeiDu.size(); weiDu++) {
					listAll.add(listWeiDu.get(weiDu));
				}
				for (int weiHui = 0; weiHui < listWeiHui.size(); weiHui++) {
					listAll.add(listWeiHui.get(weiHui));
				}
				for (int yiDu = 0; yiDu < listYiDu.size(); yiDu++) {
					listAll.add(listYiDu.get(yiDu));
				}
				json.put("root", listAll);
			}
			if (listSize != null && listSize.size() > 0) {
				json.put("total", listSize.size());
			} else {
				json.put("total", 0);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("医生登录显示病人的咨询问题出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public void savePatientConsulting(PatientConsulting patientConsulting) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.save(patientConsulting);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("新增会员留言失败", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 外网查找医院的所有科室
	 */
	public List<Map> findAllSYS_HIS_DepartmentHISEntity(String name, Long value) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		List<Map> list = new ArrayList<Map>();
		try {
			con = DatabaseUtil.getConnection();
			st = con.createStatement();
			String sql = "select * from SYS_HIS_DepartmentHISEntity";
			if (name != null) {
				sql += " where " + name + "=" + value;
			}
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Map mp = new HashMap();
				mp.put("deptCode", rs.getString("deptCode"));
				mp.put("deptName", rs.getString("deptName"));
				list.add(mp);
			}
		} catch (Exception e) {
			throw new RuntimeException("外网查找医院的所有科室", e);
		} finally {
			try {
				DatabaseUtil.closeResource(con, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public void updatePatientConsulting(PatientConsulting patientConsulting) {
		super.updateObject(patientConsulting);
	}

	public JSONArray patientConsulting_treeNodes(Long patientId,
			Date currentDate, int weekFlag) {
		JSONArray array = new JSONArray();
		String hql = "from PatientConsulting where patientId =" + patientId;
		// if (currentDate == null) {
		// Date date = new Date();
		// date.setDate(date.getDate() - 7);
		// hql += " and consultingDate>= '" + date.toLocaleString() + "'";
		// } else {
		// Date date = currentDate;
		// String dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		// .format(date);
		// if (weekFlag == 0) {
		// date.setDate(date.getDate() - 7);
		// hql += " and consultingDate>= '" + date.toLocaleString()
		// + "' and consultingDate<= '" + dates + "'";
		// } else {
		// date.setDate(date.getDate() + 7);
		// hql += " and consultingDate<= '" + date.toLocaleString()
		// + "' and consultingDate >='" + dates + "'";
		// }
		// }
		hql += " order by id desc";
		List<PatientConsulting> list = super.findAllObject(hql);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (PatientConsulting record : list) {
			JSONObject object = new JSONObject();
			object.put("id", record.getId());// 显示顺序
			int consultingCount = record.getConsultingCount();
			if (record.getConsultingDate() != null) {// 时间标题
				object.put("text", sdf.format(record.getConsultingDate()) + "第"
						+ consultingCount + "次咨询");
			} else {
				object.put("text", "The time is not define");
			}
			object.put("leaf", true);
			object.put("iconCls", "icon-none");
			object.put("href", "javascript:scrollTo("
					+ record.getConsultingCount() + ")");
			array.add(object);
		}
		return array;
	}

	public Map findNumOne(MemberInfo mem, Long patientId) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "from PatientConsulting where patientId =" + patientId
					+ " order by consultingDate desc";
			List list = session.createQuery(sql).list();
			if (list != null && list.size() > 0) {
				PatientConsulting patientConsulting = (PatientConsulting) list
						.get(0);
				mp.put("countNum", patientConsulting.getConsultingCount() + 1);
			} else {
				mp.put("countNum", 1);
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
			throw new RuntimeException("执行查找当前科室CODE出错", e);
		} finally {
		}
		return mp;
	}

	/**
	 * 医生判断转发会员的咨询
	 */
	public boolean sendToOtherDeparment(Long pcid, String deptCode,
			Long grounpId) {
		Session session = null;
		Transaction tran = null;
		boolean result = false;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from PatientConsulting where deptCode='" + deptCode
					+ "' and grounpId=" + grounpId + " and isNotSend=" + pcid;
			List list = session.createQuery(hql).list();
			if (list != null && list.size() == 0) {
				PatientConsulting patientConsulting = (PatientConsulting) session
						.get(PatientConsulting.class, pcid);
				patientConsulting.setIsNotSend(patientConsulting.getId());
				session.update(patientConsulting);

				PatientConsulting newPatientConsulting = new PatientConsulting();
				newPatientConsulting.setPatientId(patientConsulting.getPatientId());
				newPatientConsulting.setDeptCode(deptCode);
				newPatientConsulting.setGrounpId(grounpId);
				newPatientConsulting.setConsultingDate(new Date());
				newPatientConsulting.setIsNotSend(patientConsulting.getId());
				session.save(newPatientConsulting);
				result = true;
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("医生判断转发会员的咨询出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

	public boolean checkTheConIsNotSend(Long pcid) {
		return false;
	}

	public PatientConsulting findPatientConsultingById(Long id) {
		Session session = null;
		Transaction tran = null;
		PatientConsulting patientConsulting = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			patientConsulting = (PatientConsulting) session.get(
					PatientConsulting.class, id);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return patientConsulting;
	}

	/**
	 * 关闭会诊
	 */
	public boolean updatePatientConsultingStateMeeting(Long id) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			PatientConsulting patientConsulting = (PatientConsulting) session
					.get(PatientConsulting.class, id);
			patientConsulting.setPcMeetingState(1);
			session.update(patientConsulting);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return true;
	}

	/**
	 * 消息提醒是否存在未回复咨询
	 */
	public int findNoRead(Long doctorId, int flagDai, int flagYi,
			JSONObject jsonMap) {
		Session session = null;
		Transaction tran = null;
		List<Map> listDai = new ArrayList<Map>();// 待回复列表
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId ";
			if (doctorId != null) {
				docSql += " and doctorId=" + doctorId;
			}
			List docList = session.createSQLQuery(docSql).list();
			if (docList != null && docList.size() > 0) {
				for (int z = 0, sizez = docList.size(); z < sizez; z++) {
					Object[] docObject = (Object[]) docList.get(z);
					String hql = "select p.id from t_PatientConsulting p ";
					hql += " where p.deptCode='" + docObject[1]
							+ "' and p.grounpId=" + docObject[2]
							+ " and pcMeetingState is null order by p.id desc";
					List listSize = session.createSQLQuery(hql).list();
					List list = session.createSQLQuery(hql).list();
					if (list != null && list.size() > 0) {
						for (int i = 0, size = list.size(); i < size; i++) {
							Map mp = new HashMap();
							PatientConsulting pc = (PatientConsulting) session
									.get(PatientConsulting.class, Long
											.parseLong(list.get(i).toString()));
							if (pc.getIsNotSend() == null) {
								String hqlNew = "from DoctorReplyRecordAndPatientQuestions where pcId ="
										+ pc.getId() + " order by id desc";
								List listNew = session.createQuery(hqlNew)
										.list();
								if (listNew != null && listNew.size() > 0) {
									DoctorReplyRecordAndPatientQuestions doctorReplyRecordAndPatientQuestions = (DoctorReplyRecordAndPatientQuestions) listNew
											.get(0);
									if (doctorReplyRecordAndPatientQuestions
											.getDrAndpqFlag() == 1) {
										mp.put("reply", "待回复");
										return 1;
									}
								} else {
									mp.put("reply", "待回复");
									return 1;
								}
							} else {
								pc = (PatientConsulting) session.get(
										PatientConsulting.class, pc
												.getIsNotSend());
								String hqlNew = "from DoctorReplyRecordAndPatientQuestions where pcId ="
										+ pc.getId() + " order by id desc";
								List listNew = session.createQuery(hqlNew)
										.list();
								if (listNew != null && listNew.size() > 0) {
									DoctorReplyRecordAndPatientQuestions doctorReplyRecordAndPatientQuestions = (DoctorReplyRecordAndPatientQuestions) listNew
											.get(0);
									if (doctorReplyRecordAndPatientQuestions
											.getDrAndpqFlag() == 1) {
										mp.put("reply", "待回复");
										return 1;
									} else {
										String hqlNewPatient = "select drAndpqDate from DoctorReplyRecordAndPatientQuestions where pcId ="
												+ pc.getId()
												+ " and drAndpqFlag =1 order by id desc";// 此处不需要添加任何科室的判断读取会员最后一次发咨询的时间
										List hqlNewPaList = session
												.createQuery(hqlNewPatient)
												.list();
										if (hqlNewPaList != null
												&& hqlNewPaList.size() > 0) {
											String hqlNewP = "from DoctorReplyRecordAndPatientQuestions where pcId ="
													+ pc.getId()
													+ " and drAndpqDate >='"
													+ hqlNewPaList.get(0)
													+ "' and deptCode='"
													+ docObject[1]
													+ "' and grounpId="
													+ docObject[2]
													+ " and drAndpqFlag = 0";
											List listNewD = session
													.createQuery(hqlNewP)
													.list();
											if (listNewD != null
													&& listNewD.size() > 0) {
												mp.put("reply", "已回复");
											} else {
												mp.put("reply", "待回复");
												return 1;
											}
										} else {
											String hqlNewP = "from DoctorReplyRecordAndPatientQuestions where pcId ="
													+ pc.getId()
													+ " and deptCode='"
													+ docObject[1]
													+ "' and grounpId="
													+ docObject[2]
													+ " and drAndpqFlag = 0";
											List listNewD = session
													.createQuery(hqlNewP)
													.list();
											if (listNewD != null
													&& listNewD.size() > 0) {
												mp.put("reply", "已回复");
											} else {
												mp.put("reply", "待回复");
												return 1;
											}
										}
									}
								} else {
									mp.put("reply", "待回复");
									return 1;
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
			throw new RuntimeException("医生登录显示病人的咨询问题出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return listDai.size();
	}

	public int findCountPatientConsultingByPatient(Long patientId) {
		Session session = null;
		Transaction tran = null;
		int result = 0;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from PatientConsulting where patientId = "
					+ patientId + " order by id desc";
			List list = session.createQuery(hql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					Map mp = new HashMap();
					PatientConsulting pc = (PatientConsulting) list.get(i);
					if (pc.getPcMeetingState() != null) {

					} else {
						String hqlNew = "from DoctorReplyRecordAndPatientQuestions where pcId ="
								+ pc.getId() + " order by id desc";
						List listNew = session.createQuery(hqlNew).list();
						if (listNew != null && listNew.size() > 0) {
							DoctorReplyRecordAndPatientQuestions doctorReplyRecordAndPatientQuestions = (DoctorReplyRecordAndPatientQuestions) listNew
									.get(0);
							if (doctorReplyRecordAndPatientQuestions
									.getDrAndpgCancel() == 0
									&& doctorReplyRecordAndPatientQuestions
											.getDrAndpqFlag() == 0) {
								mp.put("reply", "未读");
								result = 1;
							} else if (doctorReplyRecordAndPatientQuestions
									.getDrAndpgCancel() == 0
									&& doctorReplyRecordAndPatientQuestions
											.getDrAndpqFlag() == 1) {
								mp.put("reply", "未读");
								result = 1;
							} else if (doctorReplyRecordAndPatientQuestions
									.getDrAndpgCancel() == 1) {
								if (doctorReplyRecordAndPatientQuestions
										.getDrAndpqFlag() == 0
										&& doctorReplyRecordAndPatientQuestions
												.getDrAndpgReadPatient() == 0) {
									mp.put("reply", "未读");
									result = 1;
								} else {
									String hqlNewD = "from DoctorReplyRecordAndPatientQuestions where pcId ="
											+ pc.getId()
											+ " and drAndpqFlag=0 order by id desc";
									List listNewD = session
											.createQuery(hqlNewD).list();
									if (listNewD != null && listNewD.size() > 0) {
										DoctorReplyRecordAndPatientQuestions doctorReplyRecordAndPatientQuestionsD = (DoctorReplyRecordAndPatientQuestions) listNewD
												.get(0);
										mp.put("reply", "已读");
									} else {
										mp.put("reply", "未回复");
										result = 0;
									}
								}
							}
						} else {
							mp.put("reply", "未回复");
							result = 0;
						}
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("医生登录显示病人的咨询问题出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

	public boolean checkIsNotZe(Long doctorId, String deptCode) {
		Session session = null;
		Transaction tran = null;
		boolean result = false;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String docSql = "select tda.deptCode from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="
					+ doctorId;
			List list = session.createSQLQuery(docSql).list();
			if (list != null && list.size() > 0) {
				if (deptCode != null) {
					if (deptCode.equals(list.get(0))) {
						result = true;
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

	public void updateState(Long pcId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			PatientConsulting patientConsulting = (PatientConsulting) session
					.get(PatientConsulting.class, pcId);
			patientConsulting.setReadCount(1);
			session.update(patientConsulting);
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

	public void updateApplicationBacuse(Long pcId, String applicationBacuse) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			PatientConsulting patientConsulting = (PatientConsulting) session
					.get(PatientConsulting.class, pcId);
			if (patientConsulting.getApplicationDeptMessage() != null) {
				patientConsulting.setApplicationDeptMessage(patientConsulting
						.getApplicationDeptMessage()
						+ "；" + applicationBacuse);
			} else {
				patientConsulting.setApplicationDeptMessage(applicationBacuse);
			}
			session.update(patientConsulting);
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

	public void savaPatientConsultingMessage(Long pcId, Long doctorId,
			String message) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			PatientConsultingMessage patientConsultingMessage = new PatientConsultingMessage();
			String docSql = "select tda.deptCode,tda.grounpId from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="
					+ doctorId;
			List list = session.createSQLQuery(docSql).list();
			if (list != null && list.size() > 0) {
				Object[] obj = (Object[]) list.get(0);
				patientConsultingMessage.setDeptCode(obj[0].toString());
				patientConsultingMessage.setGrounpId(Long.parseLong(obj[1]
						.toString()));
			}
			patientConsultingMessage.setDoctorId(doctorId);
			patientConsultingMessage.setPcId(pcId);
			patientConsultingMessage.setMessage(message);
			session.save(patientConsultingMessage);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public List findAllPatientConsulting(Long pcid) {
		Session session = null;
		Transaction tran = null;
		List result = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from PatientConsulting where isNotSend=" + pcid + " order by consultingDate asc";
			List list = session.createQuery(hql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					Map mp = new HashMap();
					PatientConsulting pc = (PatientConsulting) list.get(i);
					mp.put("id", i + 1);
					mp.put("consultingDate", pc.getConsultingDate()
							.toLocaleString());
					String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
							+ pc.getDeptCode() + "'";
					List deptList = session.createSQLQuery(hqlCode).list();
					DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
							.get(DepartmentGrounp.class, pc.getGrounpId());
					mp.put("deptNameGrounp", deptList.get(0)
							+ departmentGrounp.getGrounpName());
					String isOrNotReplay = " from DoctorReplyRecordAndPatientQuestions where pcId ="
							+ pcid
							+ " and"
							+ " deptCode='"
							+ pc.getDeptCode()
							+ "' and grounpId =" + pc.getGrounpId();
					List isOrNotList = session.createQuery(isOrNotReplay)
							.list();
					if (isOrNotList != null && isOrNotList.size() > 0) {
						mp.put("state", "已回复");
					} else {
						mp.put("state", "未回复");
					}
					result.add(mp);
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

	public List findAllPatientCousultingMessage(Long pcId) {
		Session session = null;
		Transaction tran = null;
		List result = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from PatientConsultingMessage where pcId=" + pcId
					+ "";
			List list = session.createQuery(hql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					Map mp = new HashMap();
					PatientConsultingMessage pcm = (PatientConsultingMessage) list
							.get(i);
					mp.put("mesDdate", pcm.getMesDdate().toLocaleString());
					String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
							+ pcm.getDeptCode() + "'";
					List deptList = session.createSQLQuery(hqlCode).list();
					DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
							.get(DepartmentGrounp.class, pcm.getGrounpId());
					mp.put("deptNameGrounpName", deptList.get(0)
							+ departmentGrounp.getGrounpName());
					User user = (User) session.get(User.class, pcm
							.getDoctorId());
					mp.put("doctorName", user.getName());
					mp.put("message", pcm.getMessage());
					result.add(mp);
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

	/**
	 * 撤销转发的咨询
	 */
	public void cancelAllSendPatientCousulting(Long pcId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "delete from t_PatientConsulting where isNotSend ="
					+ pcId + " and patientId is null";
			session.createSQLQuery(sql).executeUpdate();
			String updateSql = "update t_PatientConsulting set isNotSend = NULL where id ="+pcId;
			session.createSQLQuery(updateSql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public List findAllPatientConsultingByCancel(Long pcid) {
		Session session = null;
		Transaction tran = null;
		List result = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			PatientConsulting pc = (PatientConsulting) session.get(
					PatientConsulting.class, pcid);

			String isOrNotReplay = " from DoctorReplyRecordAndPatientQuestions where pcId ="
					+ pcid
					+ " and"
					+ " deptCode!='"
					+ pc.getDeptCode()
					+ "' and grounpId !=" + pc.getGrounpId();
			List isOrNotList = session.createQuery(isOrNotReplay).list();
			if(isOrNotList != null && isOrNotList.size() > 0){
				result.add(pc);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}
}
