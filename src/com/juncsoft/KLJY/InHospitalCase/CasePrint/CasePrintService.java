package com.juncsoft.KLJY.InHospitalCase.CasePrint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.sf.json.JSONObject;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.Default.dao.DefaultCaseDao;
import com.juncsoft.KLJY.InHospitalCase.Default.entity.SpecialExamination;
import com.juncsoft.KLJY.InHospitalCase.ENT.dao.ENTCaseDao;
import com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory.PresentHistoryService;
import com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory.PresentIllnessHistoryN;
import com.juncsoft.KLJY.InHospitalCase.Liver.dao.LiverCaseDao;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.ChiefComplaint;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.Diagnosis;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.EpidemicDisHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.FamilyHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabDiagnosticExamination;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MarritalChildbearingHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MenstrualHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.OutHspRec;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PersonalHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PhysicalExamination;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.RevisedDiagnosis;
import com.juncsoft.KLJY.InHospitalCase.Mouth.dao.MouthCaseDao;
import com.juncsoft.KLJY.InHospitalCase.Surgery.dao.SurgeryCaseDao;
import com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.dao.TCMdao;
import com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.TCM4;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;

public class CasePrintService {
	private LiverCaseDao liverDAO = (LiverCaseDao) DaoFactory
			.InstanceImplement(LiverCaseDao.class);

	private DefaultCaseDao defaultDAO = (DefaultCaseDao) DaoFactory
			.InstanceImplement(DefaultCaseDao.class);

	private ENTCaseDao entDao = (ENTCaseDao) DaoFactory
			.InstanceImplement(ENTCaseDao.class);

	private MouthCaseDao mouthDao = (MouthCaseDao) DaoFactory
			.InstanceImplement(MouthCaseDao.class);

	private SurgeryCaseDao surgeryDao = (SurgeryCaseDao) DaoFactory
			.InstanceImplement(SurgeryCaseDao.class);

	private TCMdao tcmDao = (TCMdao) DaoFactory.InstanceImplement(TCMdao.class);

	/**
	 * 打印服务入口调用函数，需要病历类型与病历主键
	 * 
	 * @param caseEnum
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getPrintData(int caseEnum, Long key)
			throws Exception {
		Map<String, String> map = Collections
				.synchronizedMap(new HashMap<String, String>());
		try {
			if (caseEnum == CaseEnum.Default_InHspRec) {
				Default_InHspRec_PrintData(map, key);
			} else if (caseEnum == CaseEnum.Liver_InHspRec) {
				Liver_InHspRec_PrintData(map, key);
			} else if (caseEnum == CaseEnum.Gynecology_InHspRec) {
				Gynecology_InHspRec_PrintData(map, key);
			} else if (caseEnum == CaseEnum.Default_OutHspRec) {
				Liver_OutHspRec_PrintData(map, key);
			} else if (caseEnum == CaseEnum.ENT_InHspRec) {
				ENT_InHspRec_PrintData(map, key);
			} else if (caseEnum == CaseEnum.Mouth_InHspRec) {
				Mouth_InHspRec_PrintData(map, key);
			} else if (caseEnum == CaseEnum.Surgery_InHspRec) {
				Surgery_InHspRec_PrintData(map, key);
			} else if (caseEnum == CaseEnum.TCM_InHspRec) {
				TCM_InHspRec_PrintData(map, key);
			}
		} catch (Exception e) {
			throw e;
		}
		return map;
	}

	/**
	 * 中医病历
	 * 
	 * @param map
	 * @param key
	 * @throws Exception
	 */
	private void TCM_InHspRec_PrintData(Map<String, String> map, Long key)
			throws Exception {
		try {
			// 病历主表与病人信息
			CaseMasterInfo(map, key);
			// 主诉
			Liver_ChiefComplaint(map, key);
			// 肝病病史
			Liver_IllnessHistory(map, key);
			// 中医四诊
			TCM_PhysicalExamination(map, key);
			// 中医辅助检查
			Liver_LabExamination(map, key);
			// 肝病诊断
			Liver_Diagnosis(map, key);
		} catch (Exception e) {
			throw e;
		}
	}

	// 中医辅助检查
	private void TCM_LabExamination(Map<String, String> map, Long key)
			throws Exception {
		try {
			com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.LabDiagnosticExamination lab = tcmDao
					.LabDiagnosticExamination_findByCaseID(key);
			if (lab != null)
				map.put("LabExamination", lab.getResult());
		} catch (Exception e) {
			throw e;
		}
	}

	// 中医四诊
	private void TCM_PhysicalExamination(Map<String, String> map, Long key)
			throws Exception {
		String pageCode = "EMR-TCM-TCM4";
		try {
			TCM4 tcm = tcmDao.TCM4_findByCaseId(key);
			if (tcm == null)
				return;
			Method[] methods = TCM4.class.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getReturnType().getName().equals("int")) {
					String name = method.getName().replace("get", "")
							.toLowerCase();
					String result = method.invoke(tcm, null).toString();
					if (result.equals("1")) {
						map.put(name, "是");
					} else {
						map.put(name, "否");
					}
				}
			}
			map.put("xingti_dongtai", DictionaryUtil.getPublicDictionaryText(
					pageCode, "xingti_dongtai", tcm.getXingti_dongtai()));
			map.put("xingti_waixing", DictionaryUtil.getPublicDictionaryText(
					pageCode, "xingti_waixing", tcm.getXingti_waixing()));
			map.put("xingti_jiashe", DictionaryUtil.getPublicDictionaryText(
					pageCode, "xingti_jiashe", tcm.getXingti_jiashe()));
			map.put("yuejing_zhouqi", DictionaryUtil.getPublicDictionaryText(
					pageCode, "yuejing_zhouqi", tcm.getYuejing_zhouqi()));
			map.put("yuejing_zhi", DictionaryUtil.getPublicDictionaryText(
					pageCode, "yuejing_zhi", tcm.getYuejing_zhi()));
			map.put("yuejing_liang", DictionaryUtil.getPublicDictionaryText(
					pageCode, "yuejing_liang", tcm.getYuejing_liang()));
			if (tcm.getShezhi_qita() == 1) {
				map.put("shezhi_qita", tcm.getShezhi_shuoming());
			} else {
				map.put("shezhi_qita", "无");
			}
			if (tcm.getSheti_qita() == 1) {
				map.put("sheti_qita", tcm.getSheti_shuoming());
			} else {
				map.put("sheti_qita", "无");
			}
			if (tcm.getShetai_qita() == 1) {
				map.put("shetai_qita", tcm.getShetai_shuoming());
			} else {
				map.put("shetai_qita", "无");
			}
			if (tcm.getJibenmai_qita() == 1) {
				map.put("jibenmai_qita", tcm.getJibenmai_shuoming());
			} else {
				map.put("jibenmai_qita", "无");
			}
			map.put("sizhenfenxi", tcm.getSizhenfenxi());
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 外科病历
	 */
	private void Surgery_InHspRec_PrintData(Map<String, String> map, Long key)
			throws Exception {
		try {
			// 病历主表与病人信息
			CaseMasterInfo(map, key);
			// 主诉
			Liver_ChiefComplaint(map, key);
			// 肝病现病史
			Liver_IllnessHistory(map, key);
			// 肝病体格检查
			Liver_PhysicalExamination(map, key);
			// 肝病辅助检查
			Liver_LabExamination(map, key);
			// 外科专科检查
			Surgery_SpecialExamination(map, key);
			// 肝病诊断
			Liver_Diagnosis(map, key);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 口腔科病历
	 */
	private void Mouth_InHspRec_PrintData(Map<String, String> map, Long key)
			throws Exception {
		try {
			// 病历主表与病人信息
			CaseMasterInfo(map, key);
			// 主诉
			Liver_ChiefComplaint(map, key);
			// 肝病现病史
			Mouth_IllnessHistory(map, key);
			// 肝病体格检查
			Liver_PhysicalExamination(map, key);
			// 肝病辅助检查
			Liver_LabExamination(map, key);
			// 专科检查
			Default_SpecialExamination(map, key);
			// 肝病诊断
			Liver_Diagnosis(map, key);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 耳鼻喉科病历
	 * 
	 * @param map
	 * @param key
	 * @throws Exception
	 */
	private void ENT_InHspRec_PrintData(Map<String, String> map, Long key)
			throws Exception {
		try {
			// 病历主表与病人信息
			CaseMasterInfo(map, key);
			// 主诉
			Liver_ChiefComplaint(map, key);
			// 肝病现病史
			Liver_IllnessHistory(map, key);
			ENT_PresentIllnessHistory(map, key);
			// 肝病体格检查
			Liver_PhysicalExamination(map, key);
			// 肝病辅助检查
			Liver_LabExamination(map, key);
			// 耳鼻喉科专科检查
			ENT_SpecialExamination(map, key);
			// 肝病诊断
			Liver_Diagnosis(map, key);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 默认病历
	 * 
	 * @param map
	 * @param key
	 * @throws Exception
	 */
	private void Default_InHspRec_PrintData(Map<String, String> map, Long key)
			throws Exception {
		try {
			// 病历主表与病人信息
			CaseMasterInfo(map, key);
			// 主诉
			Liver_ChiefComplaint(map, key);
			// 肝病现病史
			Default_IllnessHistory(map, key);
			// 肝病体格检查
			Liver_PhysicalExamination(map, key);
			// 肝病辅助检查
			Liver_LabExamination(map, key);
			// 肝病诊断
			Liver_Diagnosis(map, key);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 肝病病历
	 * 
	 * @param map
	 * @param key
	 * @throws Exception
	 */
	private void Liver_InHspRec_PrintData(Map<String, String> map, Long key)
			throws Exception {
		try {
			// 病历主表与病人信息
			CaseMasterInfo(map, key);
			// 主诉
			Liver_ChiefComplaint(map, key);
			// 肝病现病史
			Liver_IllnessHistory(map, key);
			// 肝病体格检查
			Liver_PhysicalExamination(map, key);
			// 肝病辅助检查
			Liver_LabExamination(map, key);
			// 肝病诊断
			Liver_Diagnosis(map, key);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 肝病出院记录
	 */
	private void Liver_OutHspRec_PrintData(Map<String, String> map, Long key)
			throws Exception {
		try {
			// 病历主表与病人信息
			CaseMasterInfo(map, key);
			OutHspRec out = liverDAO.OutHspRec_findByCaseID(key);
			if (out == null)
				return;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date inHspDate = null;
			try {
				inHspDate = df.parse(map.get("InHspDate"));
				if (inHspDate != null && out.getOutHspDate() != null) {
					long time = out.getOutHspDate().getTime()
							- inHspDate.getTime();
					map.put("HspDate2", (new Double(Math.floor(time
							/ (1000 * 3600 * 24))).intValue() + 1)
							+ "");
					df = new SimpleDateFormat("yyyy年MM月dd日");
					map.put("HspDate1", df.format(inHspDate) + " 至 "
							+ df.format(out.getOutHspDate()));
				}
			} catch (Exception ee) {
			}
			map.put("allergyHistory", out.getAllergyHistory());
			map.put("inHspWard", DictionaryUtil.getIndependentDictionaryText(
					"belong", out.getInHspWard()));
			map.put("outHspWard", DictionaryUtil.getIndependentDictionaryText(
					"belong", out.getOutHspWard()));
			map.put("xNo", out.getxNo());
			map.put("ctNo", out.getCtNo());
			map.put("mriNo", out.getMriNo());
			map.put("blNo", out.getBlNo());
			String outIlls = "";
			{
				String temp = out.getOutIlls1();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls2();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls3();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls4();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls5();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls6();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls7();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls8();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls9();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls10();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls11();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls12();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls13();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls14();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls15();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls16();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls17();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls18();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls19();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls20();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls21();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls22();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls23();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls24();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls25();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls26();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls27();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls28();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls29();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
				temp = out.getOutIlls30();
				if (temp != null && temp.length() > 0) {
					outIlls += "<p>" + temp + "</p>";
				}
			}
			map.put("outIlls", outIlls);
			map.put("inHspStatu", out.getInHspStatu());
			map.put("labExaminationResult", out.getLabExaminationResult());
			map.put("treatProcess", out.getTreatProcess());
			map.put("outHspStatu", out.getOutHspStatu());
			map.put("outHspOrders", out.getOutHspOrders());
			map.put("inhspDoctor", DictionaryUtil.getIndependentDictionaryText(
					"userName", out.getInhspDoctorId() + ""));
			map.put("treatDoctor", DictionaryUtil.getIndependentDictionaryText(
					"userName", out.getTreatDoctorId() + ""));
			map.put("directorDoctor", DictionaryUtil
					.getIndependentDictionaryText("userName", out
							.getDirectorDoctorId()
							+ ""));
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 妇科病历
	 */
	private void Gynecology_InHspRec_PrintData(Map<String, String> map, Long key)
			throws Exception {
		try {
			// 病历主表与病人信息
			CaseMasterInfo(map, key);
			// 主诉
			Liver_ChiefComplaint(map, key);
			// 普通病史
			Default_IllnessHistory(map, key);
			// 肝病体格检查
			Liver_PhysicalExamination(map, key);
			// 肝病辅助检查
			Liver_LabExamination(map, key);
			// 肝病诊断
			Liver_Diagnosis(map, key);
			// 专科检查
			Default_SpecialExamination(map, key);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 病历主表信息
	 * 
	 * @param map
	 * @param key
	 * @throws Exception
	 */
	private void CaseMasterInfo(Map<String, String> map, Long key)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			InHspCaseMaster master = (InHspCaseMaster) session.get(
					InHspCaseMaster.class, key);
			tran.commit();
			map.put("Age", master.getAge());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = master.getInHspDate();
			if (date != null)
				map.put("InHspDate", df.format(date));
			map.put("InHspTimes", master.getInHspTimes() + "");
			if (master.getPatientId() != null && master.getPatientId() > 0)
				PatientBasicInfo(map, master.getPatientId());
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 病人基本信息
	 * 
	 * @param map
	 * @param key
	 * @throws Exception
	 */
	private void PatientBasicInfo(Map<String, String> map, Long key)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient patient = (Patient) session.get(Patient.class, key);
			if (patient != null) {
				map.put("PatientName", patient.getPatientName());
				map.put("PatientNo", patient.getPatientNo());
				map.put("Gender", DictionaryUtil.getIndependentDictionaryText(
						"gender_gb", patient.getGender()));
				String people = DictionaryUtil.getIndependentDictionaryText(
						"people", patient.getPeople());
				if (people.equals("其它")) {
					people = patient.getPeople0();
				}
				map.put("People", people);
				String province = DictionaryUtil.getIndependentDictionaryText(
						"province", patient.getProvince());
				if (province.equals("其它")) {
					province = patient.getProvince0();
				}
				map.put("Province", province);
				String marrageStatus = DictionaryUtil
						.getIndependentDictionaryText("marrageStatus", patient
								.getMarrageStatus());
				if (marrageStatus.equals("其它")) {
					marrageStatus = patient.getMarrageStatus0();
				}
				map.put("MarrageStatus", marrageStatus);
				String occupation = DictionaryUtil
						.getIndependentDictionaryText("occupation", patient
								.getOccupation());
				if (occupation.equals("其他")) {
					occupation = patient.getOccupation0();
				}
				map.put("Occupation", occupation);
				map.put("Address", patient.getHomeAddr());
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	// 肝病主诉
	private void Liver_ChiefComplaint(Map<String, String> map, Long key)
			throws Exception {
		String pageCode = "EMR-liver-ChiefComplaint";
		try {
			ChiefComplaint cc = liverDAO.ChiefComplaint_findByCaseID(key);
			if (cc != null) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date date = cc.getDataGetDate();
				if (date != null) {
					map.put("DataGetDate", df.format(date));
				}
				String narrator = DictionaryUtil.getPublicDictionaryText(
						pageCode, "narrator", cc.getNarrator());
				if (narrator.equals("其它")) {
					narrator = cc.getNarrator0();
				}
				map.put("Narrator", narrator);
				map.put("Reliability", DictionaryUtil.getPublicDictionaryText(
						pageCode, "reliability", cc.getReliability()));
				map.put("ChiefComplaint", cc.getCcContent());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	// 肝病病史
	private void Liver_IllnessHistory(Map<String, String> map, Long key)
			throws Exception {
		try {
			/*
			 * PresentIllnessHistory history = liverDAO
			 * .PresentIllnessHistory_findByCaseID(key); if (history != null) {
			 * String content1 = history.getContent1(); if (content1 != null &&
			 * content1.length() > 0) { content1 = "<div>" + content1 +
			 * "</div>"; } else { content1 = ""; } String content2 =
			 * history.getContent2(); if (content2 != null && content2.length()
			 * > 0) { content2 = "<div>" + content2 + "</div>"; } else {
			 * content2 = ""; } map.put("PresentIllnessHistory", content1 +
			 * content2); }
			 */
			PresentIllnessHistoryN history = new PresentHistoryService()
					.findContent(key);
			if (history != null) {
				map.put("PresentIllnessHistory", "<div>" + history.getContent()
						+ "</div>");
			}
			EpidemicDisHistory eHistory = liverDAO
					.EpidemicDis_findByCaseID(key);
			if (eHistory != null) {
				map.put("EpidemicDisHistory", eHistory.getEpidemicDisDesc());
			}
			PastHistory pHistory = liverDAO.PastHistory_findByCaseID(key);
			if (pHistory != null) {
				map.put("PastHistory", pHistory.getPastHistoryDesc());
			}
			PersonalHistory plHistory = liverDAO
					.PersonalHistory_findByCaseID(key);
			if (plHistory != null) {
				map.put("PersonalHistory", plHistory.getPersonalHistoryDesc());
			}
			MenstrualHistory mHistory = liverDAO
					.MenstrualHistory_findByCaseID(key);
			if (mHistory != null) {
				map.put("MenstrualHistory", mHistory.getMenstrualDesc());
			}
			MarritalChildbearingHistory mcHistory = liverDAO
					.MarritalChildbearingHistory_findByCaseID(key);
			if (mcHistory != null) {
				map.put("MarritalChildbearingHistory", mcHistory
						.getMarriageChildDesc());
			}
			FamilyHistory fHistory = liverDAO.FamilyHistory_findByCaseID(key);
			if (fHistory != null) {
				map.put("FamilyHistory", fHistory.getFaimlyHistoryDesc());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	// 肝病体格检查 ====================================
	private void Liver_PhysicalExamination(Map<String, String> map, Long key)
			throws Exception {
		String pageCode = "EMR-liver-PhysicalExamination";
		try {
			PhysicalExamination exam = liverDAO
					.PhysicalExamination_findByCaseID(key);
			if (exam == null)
				return;
			// 生命体征
			map.put("smtz_tiwen", exam.getSmtz_tiwen() + "℃");
			/* ===================================================== */
			if (exam.getSmtz_xueya2() != null
					&& exam.getSmtz_xueya2().trim().length() > 0) {
				map.put("smtz_xueya", exam.getSmtz_xueya() + "/");
				map.put("smtz_xueya2", exam.getSmtz_xueya2() + "mmHg");
			} else {
				map.put("smtz_xueya", exam.getSmtz_xueya() + "mmHg");
			}

			map.put("smtz_maibo", exam.getSmtz_maibo() + "次/分");
			map.put("smtz_huxi", exam.getSmtz_huxi() + "次/分");
			// 一般状况
			String ybzc_fayu = DictionaryUtil.getPublicDictionaryText(pageCode,
					"ybzc_fayu", exam.getYbzc_fayu());
			if (ybzc_fayu.equals("其它"))
				ybzc_fayu = exam.getYbzc_fayu0();
			map.put("ybzc_fayu", ybzc_fayu);
			String ybzc_shenzhi = DictionaryUtil.getPublicDictionaryText(
					pageCode, "ybzc_shenzhi", exam.getYbzc_shenzhi());
			if (ybzc_shenzhi.equals("其它"))
				ybzc_shenzhi = exam.getYbzc_shenzhi0();
			map.put("ybzc_shenzhi", ybzc_shenzhi);
			String ybzc_mianrong = DictionaryUtil.getPublicDictionaryText(
					pageCode, "ybzc_mianrong", exam.getYbzc_mianrong());
			if (ybzc_mianrong.equals("其它"))
				ybzc_mianrong = exam.getYbzc_mianrong0();
			map.put("ybzc_mianrong", ybzc_mianrong);
			String ybzc_butai = DictionaryUtil.getPublicDictionaryText(
					pageCode, "ybzc_butai", exam.getYbzc_butai());
			if (ybzc_butai.equals("其它"))
				ybzc_butai = exam.getYbzc_butai0();
			map.put("ybzc_butai", ybzc_butai);
			String ybzc_yingyang = DictionaryUtil.getPublicDictionaryText(
					pageCode, "ybzc_yingyang", exam.getYbzc_yingyang());
			if (ybzc_yingyang.equals("其它"))
				ybzc_yingyang = exam.getYbzc_yingyang0();
			map.put("ybzc_yingyang", ybzc_yingyang);
			String ybzc_biaoqing = DictionaryUtil.getPublicDictionaryText(
					pageCode, "ybzc_biaoqing", exam.getYbzc_biaoqing());
			if (ybzc_biaoqing.equals("其它"))
				ybzc_biaoqing = exam.getYbzc_biaoqing0();
			map.put("ybzc_biaoqing", ybzc_biaoqing);
			String ybzc_tiwei = DictionaryUtil.getPublicDictionaryText(
					pageCode, "ybzc_tiwei", exam.getYbzc_tiwei());
			if (ybzc_tiwei.equals("其它"))
				ybzc_tiwei = exam.getYbzc_tiwei0();
			map.put("ybzc_tiwei", ybzc_tiwei);
			String ybzc_chati = DictionaryUtil.getPublicDictionaryText(
					pageCode, "ybzc_chati", exam.getYbzc_chati());
			if (ybzc_chati.equals("其它"))
				ybzc_chati = exam.getYbzc_chati0();
			map.put("ybzc_chati", ybzc_chati);
			// 皮肤粘膜
			String temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"pfnm_seze", exam.getPfnm_seze());
			if (temp.equals("其它")) {
				temp = exam.getPfnm_seze0();
			}
			map.put("pfnm_seze", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"pfnm_tanxing", exam.getPfnm_tanxing());
			if (temp.equals("其它")) {
				temp = exam.getPfnm_tanxing0();
			}
			map.put("pfnm_tanxing", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"pfnm_wenshi", exam.getPfnm_wenshi());
			if (temp.equals("其它")) {
				temp = exam.getPfnm_wenshi0();
			}
			map.put("pfnm_wenshi", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"pfnm_maofa", exam.getPfnm_maofa());
			if (temp.equals("其它")) {
				temp = exam.getPfnm_maofa0();
			}
			map.put("pfnm_maofa", temp);
			if (exam.getPfnm_ganzhang() == 1) {
				temp = "阳性";
			} else {
				temp = "阴性";
			}
			map.put("pfnm_ganzhang", temp);
			if (exam.getPfnm_maoxi() == 1) {
				temp = "阳性";
			} else {
				temp = "阴性";
			}
			map.put("pfnm_maoxi", temp);
			if (exam.getPfnm_pizhen() == 1) {
				temp = exam.getPfnm_pizhenDesc();
			} else {
				temp = "无";
			}
			map.put("pfnm_pizhen", temp);
			if (exam.getPfnm_pixia() == 1) {
				temp = exam.getPfnm_pixiaDesc();
			} else {
				temp = "无";
			}
			map.put("pfnm_pixia", temp);
			if (exam.getPfnm_banhen() == 1) {
				temp = exam.getPfnm_banhenDesc();
			} else {
				temp = "无";
			}
			map.put("pfnm_banhen", temp);
			if (exam.getPfnm_shuizhong() == 1) {
				temp = exam.getPfnm_shuizhongDesc();
			} else {
				temp = "无";
			}
			map.put("pfnm_shuizhong", temp);
			if (exam.getPfnm_qita() == 1) {
				temp = exam.getPfnm_qitaDesc();
			} else {
				temp = "无";
			}
			map.put("pfnm_qita", temp);
			/* ===========新增 09-12 ============= */
			if (exam.getPfnm_zhizhu() != null && exam.getPfnm_zhizhu() == 1) {
				temp = "有";
			} else {
				temp = "无";
			}
			map.put("pfnm_zhizhu", temp);
			// 淋巴结与淋巴管
			if (exam.getLinbajie_zhongda() == 1) {
				temp = exam.getLinbajie_zhongdaDesc();
			} else {
				temp = "未触及";
			}
			map.put("linbajie_zhongda", temp);
			if (exam.getLinbajie_jieyan() == 1) {
				String str = DictionaryUtil.getPublicDictionaryText(pageCode,
						"linbajie_jieyantd", exam.getLinbajie_jieyantd());
				if (exam.getLinbajie_jieyanbs() == 0) {
					str += "、不";
				}
				temp = str + "伴全身性高热寒战";
			} else {
				temp = "未触及";
			}
			map.put("linbajie_jieyan", temp);
			// 头部五官-口
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"head_daxiao", exam.getHead_daxiao());
			if (temp.equals("其它")) {
				temp = exam.getHead_daxiao0();
			}
			map.put("head_daxiao", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"head_jixing", exam.getHead_jixing());
			if (temp.equals("其它")) {
				temp = exam.getHead_jixing0();
			}
			map.put("head_jixing", temp);
			if (exam.getHead_other() == 1) {
				temp = exam.getHead_otherDesc();
			} else {
				temp = "无";
			}
			if (exam.getHead_other() == 1) {
				map.put("head_other", exam.getHead_otherDesc());
			} else {
				map.put("head_other", "无");
			}
			// 眼睛
			temp = DictionaryUtil.getPublicDictionaryText(
					pageCode, "eyes_yanjian", exam.getEyes_yanjian());
			if (temp.equals("其它")) {
				temp = exam.getEyes_yanjian0();
			}
			map.put("eyes_yanjian", temp);
			
			if (exam.getEyes_jiaomo() == 1) {
				temp = exam.getEyes_jiaomoDesc();
			} else {
				temp = "正常";
			}
			map.put("eyes_jiaomo", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"eyes_yanqiu", exam.getEyes_yanqiu());
			if (temp.equals("其它")) {
				temp = exam.getEyes_yanqiu0();
			}
			map.put("eyes_yanqiu", temp);
			// map.put("eyes_yanqiu", DictionaryUtil.getPublicDictionaryText(
			// pageCode, "eyes_yanqiu", exam.getEyes_yanqiu()));
			if (exam.getEyes_tongkong() == 1) {
				temp = exam.getEyes_tongkongDesc();
			} else {
				temp = "等圆等大";
			}
			map.put("eyes_tongkong", temp);
			if (exam.getEyes_zhijie().equals("1")) {
				temp = exam.getEyes_zhijieDesc();
			} else {
				temp = "正常";
			}
			map.put("eyes_zhijie", temp);
			if (exam.getEyes_jianjie().equals("1")) {
				temp = exam.getEyes_jianjieDesc();
			} else {
				temp = "正常";
			}
			map.put("eyes_jianjie", temp);
			if (exam.getEyes_other() != null
					&& exam.getEyes_other().equals("1")) {
				temp = exam.getEyes_otherDesc();
			} else {
				temp = "无";
			}
			map.put("eyes_other", temp);
			// 耳
			if (exam.getEar_erkuo().equals("1")) {
				temp = exam.getEar_erkuoDesc();
			} else {
				temp = "正常";
			}
			map.put("ear_erkuo", temp);
			if (exam.getEar_waier() == 1) {
				String posi = DictionaryUtil.getPublicDictionaryText(pageCode,
						"ear_waierPosi", exam.getEar_waierPosi());
				String xz = DictionaryUtil.getPublicDictionaryText(pageCode,
						"ear_waierxingzhi", exam.getEar_waierxingzhi());
				if (xz.equals("其它") || xz.equals("其他")) {
					xz = exam.getEar_waierxingzhi0();
				}
				temp = posi + xz;
			} else {
				temp = "无";
			}
			map.put("ear_waier", temp);
			if (exam.getEar_other() != null && exam.getEar_other().equals("1")) {
				temp = exam.getEar_otherDesc();
			} else {
				temp = "无";
			}
			map.put("ear_other", temp);
			// 鼻
			if (exam.getNose_waiguan().equals("1")) {
				temp = exam.getNose_waiguanDesc();
			} else {
				temp = "正常";
			}
			map.put("nose_waiguan", temp);
			map.put("nose_zhongge", DictionaryUtil.getPublicDictionaryText(
					pageCode, "nose_zhongge", exam.getNose_zhongge()));
			if (exam.getNose_bidou() == 1) {
				temp = exam.getNose_bidouPosi();
			} else {
				temp = "无";
			}
			map.put("nose_bidou", temp);
			if (exam.getNose_other() == 1) {
				temp = exam.getNose_otherDesc();
			} else {
				temp = "无";
			}
			map.put("nose_other", temp);
			// 口
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"mouth_kouchun", exam.getMouth_kouchun());
			if (temp.equals("其它")) {
				temp = exam.getMouth_kouchun0();
			}
			map.put("mouth_kouchun", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"mouth_nianmo", exam.getMouth_nianmo());
			if (temp.equals("其它")) {
				temp = exam.getMouth_nianmo0();
			}
			map.put("mouth_nianmo", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"mouth_shenshe", exam.getMouth_shenshe());
			if (temp.equals("其它")) {
				temp = exam.getMouth_shenshe0();
			}
			map.put("mouth_shenshe", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"mouth_qiwei", exam.getMouth_qiwei());
			if (temp.equals("其它")) {
				temp = exam.getMouth_qiwei0();
			}
			map.put("mouth_qiwei", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"mouth_yayin", exam.getMouth_yayin());
			if (temp.equals("其它")) {
				temp = exam.getMouth_yayin0();
			}
			map.put("mouth_yayin", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"mouth_chilie", exam.getMouth_chilie());
			if (temp.equals("其它")) {
				temp = exam.getMouth_chilie0();
			}
			map.put("mouth_chilie", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"mouth_yanbu", exam.getMouth_yanbu());
			if (temp.equals("其它")) {
				temp = exam.getMouth_yanbu0();
			}
			map.put("mouth_yanbu", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"mouth_biantaoti", exam.getMouth_biantaoti());
			if (temp.equals("其它")) {
				temp = exam.getMouth_biantaoti0();
			}
			map.put("mouth_biantaoti", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"mouth_shenyin", exam.getMouth_shenyin());
			if (temp.equals("其它")) {
				temp = exam.getMouth_shenyin0();
			}
			map.put("mouth_shenyin", temp);
			if (exam.getSaix_zhongda() == 1) {
				temp = exam.getSaix_zhongdaDesc();
			} else {
				temp = "无";
			}
			map.put("saix_zhongda", temp);
			if (exam.getMouth_other() != null
					&& exam.getMouth_other().equals("1")) {
				temp = exam.getMouth_otherDesc();
			} else {
				temp = "无";
			}
			map.put("mouth_other", temp);
			// 颈部
			if (exam.getNeck_dichu() == 1) {
				temp = "有";
			} else {
				temp = "无";
			}
			map.put("neck_dichu", temp);
			map.put("neck_jingmai", DictionaryUtil.getPublicDictionaryText(
					pageCode, "neck_jingmai", exam.getNeck_jingmai()));
			if (exam.getNeck_jmhuiliu() == 1) {
				temp = "阳性";
			} else {
				temp = "阴性";
			}
			map.put("neck_jmhuiliu", temp);
			if (exam.getNeck_jiazhx() == 1) {
				temp = exam.getNeck_jiazhxDesc();
			} else {
				temp = "未触及";
			}
			map.put("neck_jiazhx", temp);
			if (exam.getNeck_other() != null
					&& exam.getNeck_other().equals("1")) {
				temp = exam.getNeck_otherDesc();
			} else {
				temp = "无";
			}
			map.put("neck_other", temp);
			// 胸部
			
			if (exam.getXiong_yatong() == 1) {
				temp = "有";
			} else {
				temp = "无";
			}
			map.put("xiong_yatong", temp);
			if (exam.getXiong_rufangDc() == 1) {
				temp = "不对称";
			} else {
				temp = "对称";
			}
			map.put("xiong_rufangDc", temp);
			if (exam.getXiong_fufang() == 1) {
				temp = exam.getXiong_rufangDesc();
			} else {
				temp = "无";
			}
			map.put("xiong_fufang", temp);
			if (exam.getXiong_other() != null
					&& exam.getXiong_other().equals("1")) {
				temp = exam.getXiong_otherDesc();
			} else {
				temp = "无";
			}
			map.put("xiong_other", temp);
			// 肺部
			
			
			String fei_yuchan = DictionaryUtil.getPublicDictionaryText(
					pageCode, "fei_huxi", exam.getFei_yuchan());
			if (fei_yuchan.equals("其它"))
				fei_yuchan = exam.getFei_yuchan0();
			map.put("fei_yuchan", fei_yuchan);
			
			
			String fei_zuokou = DictionaryUtil.getPublicDictionaryText(
					pageCode, "fei_zuokou", exam.getFei_zuokou());
			if (fei_zuokou.equals("其它"))
				fei_zuokou = exam.getFei_zuokou0();
			map.put("fei_zuokou", fei_zuokou);
//			liugang注意其中的键值，不是每一个都有对应的值fei_zuokou
			String fei_youkou = DictionaryUtil.getPublicDictionaryText(
					pageCode, "fei_zuokou", exam.getFei_youkou());
			if (fei_youkou.equals("其它"))
				fei_youkou = exam.getFei_youkou0();
			map.put("fei_youkou", fei_youkou);

			
			
			map.put("fei_zuoxiajie", exam.getFei_zuoxiajie());
			map.put("fei_youxiajie", exam.getFei_youxiajie());
			if (exam.getFei_huxiyin() == 1) {
				temp = exam.getFei_huxiyinDesc();
			} else {
				temp = "正常";
			}
			map.put("fei_huxiyin", temp);
			if (exam.getFei_xiongmo() == 1) {
				temp = exam.getFei_xiongmoDesc();
			} else {
				temp = "无";
			}
			map.put("fei_xiongmo", temp);
			if (exam.getFei_luoyin() == 1) {
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"ear_waierPosi", exam.getFei_luoyinPosi())
						+ exam.getFei_luoyinxzhi();
			} else {
				temp = "无";
			}
			map.put("fei_luoyin", temp);
			if (exam.getFei_other() != null && exam.getFei_other().equals("1")) {
				temp = exam.getFei_otherDesc();
			} else {
				temp = "无";
			}
			map.put("fei_other", temp);
			// 心脏
			if (exam.getXinz_penglong() == 1) {
				temp = "有";
			} else {
				temp = "无";
			}
			map.put("xinz_penglong", temp);
			String xinz_bodong = DictionaryUtil.getPublicDictionaryText(
					pageCode, "xinz_bodong", exam.getXinz_bodong());
			if (xinz_bodong.equals("其它"))
				xinz_bodong = exam.getXinz_bodong0();
			map.put("xinz_bodong", xinz_bodong);
			
			if (exam.getXinz_bodongPosi() == 0) {
				temp = "正常";
			} else {
				temp = "移位，距左锁骨中线" + exam.getXinz_bodongCM() + "cm";
			}
			map.put("xinz_bodongPosi", temp);
			
			String xinz_xinjie = DictionaryUtil.getPublicDictionaryText(
					pageCode, "xinz_xinjie", exam.getXinz_xinjie());
			if (xinz_xinjie.equals("其它"))
				xinz_xinjie = exam.getXinz_xinjie0();
			map.put("xinz_xinjie", xinz_xinjie);
			
			
			map.put("xinz_xinlv", exam.getXinz_xinlv());
			if (exam.getXinz_xinbao() == 1) {
				temp = "有";
			} else {
				temp = "无";
			}
			map.put("xinz_xinbao", temp);
			if (exam.getXinz_other() != null
					&& exam.getXinz_other().equals("1")) {
				temp = exam.getXinz_otherDesc();
			} else {
				temp = "无";
			}
			map.put("xinz_other", temp);
			// 周围血管征
			if (exam.getZhouweixg_zheng() == 1) {
				temp = exam.getZhouweixg_zhengDesc();
			} else {
				temp = "正常";
			}
			map.put("zhouweixg_zheng", temp);
			// 腹部-视诊
			map.put("fubu_waixing", exam.getFubu_waixing());
			if (exam.getFubu_huxi() == 1) {
				temp = "消失";
			} else {
				temp = "存在";
			}
			map.put("fubu_huxi", temp);
			if (exam.getFubu_jingmai() == 1) {
				temp = "有";
			} else {
				temp = "无";
			}
			map.put("fubu_jingmai", temp);
			if (exam.getFubu_shizhen_o() == 1) {
				temp = exam.getFubu_shizhen_oDesc();
			} else {
				temp = "无";
			}
			map.put("fubu_shizhen_o", temp);
			// 腹部-触诊
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"fubu_fubi", exam.getFubu_fubi());
			if (temp.equals("其它")) {
				temp = exam.getFubu_fubi0();
			}
			map.put("fubu_fubi", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"fubu_jijzh", exam.getFubu_jijzh());
			if (temp.equals("其它")) {
				temp = exam.getFubu_jijzh0();
			}
			map.put("fubu_jijzh", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"fubu_jijzh", exam.getFubu_yatong());
			if (temp.equals("其它")) {
				temp = exam.getFubu_yatong0();
			}
			map.put("fubu_yatong", temp);
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"fubu_jijzh", exam.getFubu_fantt());
			if (temp.equals("其它")) {
				temp = exam.getFubu_fantt0();
			}
			map.put("fubu_fantt", temp);
			if (exam.getFubu_baokuai() == 1) {
				temp = exam.getFubu_baokuaiDesc();
			} else {
				temp = "无";
			}
			map.put("fubu_baokuai", temp);
			if (exam.getFubu_yebo() == 1) {
				temp = "有";
			} else {
				temp = "无";
			}
			map.put("fubu_yebo", temp);
			if (exam.getFubu_zhenshui() == 1) {
				temp = "有";
			} else {
				temp = "无";
			}
			map.put("fubu_zhenshui", temp);
			map.put("fubu_murphy", DictionaryUtil.getPublicDictionaryText(
					pageCode, "fubu_murphy", exam.getFubu_murphy()));
			if (exam.getFubu_ganzang() == 1) {
				temp = exam.getFubu_ganzangDesc();
			} else {
				temp = "未触及";
			}
			map.put("fubu_ganzang", temp);
			if (exam.getFubu_dannang() == 1) {
				temp = exam.getFubu_dannangDesc();
			} else {
				temp = "未触及";
			}
			map.put("fubu_dannang", temp);
			if (exam.getFubu_pi() == 1) {
				temp = exam.getFubu_piDesc();
			} else {
				temp = "未触及";
			}
			map.put("fubu_pi", temp);
			if (exam.getFubu_shen() == 1) {
				temp = exam.getFubu_shenDesc();
			} else {
				temp = "未触及";
			}
			map.put("fubu_shen", temp);
			if (exam.getFubu_chu_other() != null
					&& exam.getFubu_chu_other().equals("1")) {
				temp = exam.getFubu_chu_otherDesc();
			} else {
				temp = "无";
			}
			map.put("fubu_chu_other", temp);
			// 腹部-叩诊
			map.put("fubu_ganzhuo", DictionaryUtil.getPublicDictionaryText(
					pageCode, "fubu_ganzhuo", exam.getFubu_ganzhuo()));
			map.put("fubu_ganshangjie", exam.getFubu_ganshangjie());
			if (exam.getFubu_ganqukt() == 1) {
				temp = "有";
			} else {
				temp = "无";
			}
			map.put("fubu_ganqukt", temp);
			
			String fubu_shenkt = DictionaryUtil.getPublicDictionaryText(
					pageCode, "ear_rutu", exam.getFubu_shenkt());
			if (fubu_shenkt.equals("其它"))
				fubu_shenkt = exam.getFubu_shenkt0();
			map.put("fubu_shenkt", fubu_shenkt);
			
			
			
			
			
			map.put("fubu_yidong", DictionaryUtil.getPublicDictionaryText(
					pageCode, "fubu_murphy", exam.getFubu_yidong()));
			map.put("fubu_fushui", DictionaryUtil.getPublicDictionaryText(
					pageCode, "fubu_fushui", exam.getFubu_fushui()));
			if (exam.getFubu_k_other() != null
					&& exam.getFubu_k_other().equals("1")) {
				temp = exam.getFubu_k_otherDesc();
			} else {
				temp = "无";
			}
			map.put("fubu_k_other", temp);
			// 腹部-听诊
			map.put("fubu_changming", exam.getFubu_changming());
			if (exam.getFubu_qishui() == 1) {
				temp = "有";
			} else {
				temp = "无";
			}
			map.put("fubu_qishui", temp);
			if (exam.getFubu_xueguan() == 1) {
				temp = exam.getFubu_xueguanDesc();
			} else {
				temp = "无";
			}
			map.put("fubu_xueguan", temp);
			if (exam.getFubu_tz_other() != null
					&& exam.getFubu_tz_other().equals("1")) {
				temp = exam.getFubu_tz_otherDesc();
			} else {
				temp = "无";
			}
			map.put("fubu_tz_other", temp);
			// 生殖器/肛门/直肠
			if (exam.getShengzhiqi() == 1) {
				temp = exam.getShengzhiqiDesc();
			} else if (exam.getShengzhiqi() == 0) {
				temp = "正常";
			} else if (exam.getShengzhiqi() == 3) {
				temp = "拒查";
			} else if (exam.getShengzhiqi() == 4) {
				temp = "未查";
			}
			map.put("shengzhiqi", temp);
			// 脊柱-四肢
			
			if (exam.getJisi_yatong() == 1) {
				temp = "有";
			} else if (exam.getJisi_yatong() == 0) {
				temp = "无";
			} else if (exam.getJisi_yatong() == 2) {
				temp = "不合作";
			} else if (exam.getJisi_yatong() == 3) {
				temp = "无法配合";
			}
			map.put("jisi_yatong", temp);
			if (exam.getJisi_kouji() == 1) {
				temp = "有";
			} else if (exam.getJisi_kouji() == 0) {
				temp = "无";
			} else if (exam.getJisi_kouji() == 2) {
				temp = "不合作";
			} else if (exam.getJisi_kouji() == 3) {
				temp = "无法配合";
			}
			map.put("jisi_kouji", temp);
			map.put("jisi_huodong", DictionaryUtil.getPublicDictionaryText(
					pageCode, "jisi_huodong", exam.getJisi_huodong()));
			temp = DictionaryUtil.getPublicDictionaryText(pageCode,
					"jisi_xiazhi", exam.getJisi_xiazhi());
			if (temp.equals("其它")) {
				temp = exam.getJisi_xiazhi0();
			}
			map.put("jisi_xiazhi", temp);
			if (exam.getJisi_other() == 1) {
				temp = exam.getJisi_otherDesc();
			} else {
				temp = "无";
			}
			map.put("jisi_other", temp);
			// 神经系统
			if (exam.getShenjing_xijian() == 1) {
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"ear_waierPosi", exam.getShenjing_xijianPosi())
						+ DictionaryUtil.getPublicDictionaryText(pageCode,
								"shenjing_xijianXZ", exam
										.getShenjing_xijianXZ());
			} else {
				temp = "正常";
			}
			map.put("shenjing_xijian", temp);
			if (exam.getShenjing_genjian() == 1) {
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"ear_waierPosi", exam.getShenjing_genjianPosi())
						+ DictionaryUtil.getPublicDictionaryText(pageCode,
								"shenjing_xijianXZ", exam
										.getShenjing_genjianXZ());
			} else {
				temp = "正常";
			}
			map.put("shenjing_genjian", temp);
			
			String shenjing_kerning = DictionaryUtil.getPublicDictionaryText(
					pageCode, "shenjing_babinski", exam.getShenjing_kerning());
			if (shenjing_kerning.equals("其它"))
				shenjing_kerning = exam.getShenjing_kerning0();
			map.put("shenjing_kerning", shenjing_kerning);
			
			String shenjing_puyi = DictionaryUtil.getPublicDictionaryText(
					pageCode, "shenjing_huai", exam.getShenjing_puyi());
			if (shenjing_puyi.equals("其它"))
				shenjing_puyi = exam.getShenjing_puyi0();
			map.put("shenjing_puyi", shenjing_puyi);
			
			
			String shenjing_brudzinski = DictionaryUtil
			.getPublicDictionaryText(pageCode, "shenjing_babinski",
					exam.getShenjing_brudzinski());
			if (shenjing_brudzinski.equals("其它"))
				shenjing_brudzinski = exam.getShenjing_brudzinski0();
			map.put("shenjing_brudzinski", shenjing_brudzinski);
			
			
			if (exam.getShenjing_jili() == 1) {
				temp = "";
				String str = DictionaryUtil.getPublicDictionaryText(pageCode,
						"shenjing_jilitl", exam.getShenjing_jilitl());
				if (str.length() > 0) {
					temp += "上肢左侧" + str + "、";
				}
				str = DictionaryUtil.getPublicDictionaryText(pageCode,
						"shenjing_jilitl", exam.getShenjing_jilitr());
				if (str.length() > 0) {
					temp += "上肢右侧" + str + "、";
				}
				str = DictionaryUtil.getPublicDictionaryText(pageCode,
						"shenjing_jilitl", exam.getShenjing_jilibl());
				if (str.length() > 0) {
					temp += "下肢左侧" + str + "、";
				}
				str = DictionaryUtil.getPublicDictionaryText(pageCode,
						"shenjing_jilitl", exam.getShenjing_jilibr());
				if (str.length() > 0) {
					temp += "下肢右侧" + str + "、";
				}
				if (temp.length() > 0) {
					temp = temp.substring(0, temp.length() - 1);
				}
			} else if (exam.getShenjing_jili() == 2) {
				temp = "不合作";
			} else if (exam.getShenjing_jili() == 3) {
				temp = "无法配合";
			} else {
				temp = "正常";
			}
			map.put("shenjing_jili", temp);
			if (exam.getShenjing_jizhang() == 1) {
				temp = "";
				String str = DictionaryUtil.getPublicDictionaryText(pageCode,
						"shenjing_jizhangtl", exam.getShenjing_jizhangtl());
				if (str.length() > 0) {
					temp += "上肢左侧" + str + "、";
				}
				str = DictionaryUtil.getPublicDictionaryText(pageCode,
						"shenjing_jizhangtl", exam.getShenjing_jizhangtr());
				if (str.length() > 0) {
					temp += "上肢右侧" + str + "、";
				}
				str = DictionaryUtil.getPublicDictionaryText(pageCode,
						"shenjing_jizhangtl", exam.getShenjing_jizhangbl());
				if (str.length() > 0) {
					temp += "下肢左侧" + str + "、";
				}
				str = DictionaryUtil.getPublicDictionaryText(pageCode,
						"shenjing_jizhangtl", exam.getShenjing_jizhangbr());
				if (str.length() > 0) {
					temp += "下肢右侧" + str + "、";
				}
				if (temp.length() > 0) {
					temp = temp.substring(0, temp.length() - 1);
				}
			} else {
				temp = "正常";
			}
			map.put("shenjing_jizhang", temp);
			if (exam.getShenjing_other() != null
					&& exam.getShenjing_other().equals("1")) {
				temp = exam.getShenjing_otherDesc();
			} else {
				temp = "无";
			}
			map.put("shenjing_other", temp);

			// 2011-04-23上午修改添加其他liugang
			String eyes_jiemo = DictionaryUtil.getPublicDictionaryText(
					pageCode, "eyes_jiemo", exam.getEyes_jiemo());
			if (eyes_jiemo.equals("其它"))
				eyes_jiemo = exam.getEyes_jiemo0();
			map.put("eyes_jiemo", eyes_jiemo);

			String eyes_gongmo = DictionaryUtil.getPublicDictionaryText(
					pageCode, "eyes_gongmo", exam.getEyes_gongmo());
			if (eyes_gongmo.equals("其它"))
				eyes_gongmo = exam.getEyes_gongmo0();
			map.put("eyes_gongmo", eyes_gongmo);

			String ear_rutu = DictionaryUtil.getPublicDictionaryText(pageCode,
					"ear_rutu", exam.getEar_rutu());
			if (ear_rutu.equals("其它"))
				ear_rutu = exam.getEar_rutu0();
			map.put("ear_rutu", ear_rutu);

			String ear_tingli = DictionaryUtil.getPublicDictionaryText(
					pageCode, "ear_rutu", exam.getEar_tingli());
			if (ear_tingli.equals("其它"))
				ear_tingli = exam.getEar_tingli0();
			map.put("ear_tingli", ear_tingli);

			String neck_jingmai = DictionaryUtil.getPublicDictionaryText(
					pageCode, "neck_jingmai", exam.getNeck_jingmai());
			if (neck_jingmai.equals("其它"))
				neck_jingmai = exam.getNeck_jingmai0();
			map.put("neck_jingmai", neck_jingmai);

			String neck_qiguan = DictionaryUtil.getPublicDictionaryText(
					pageCode, "neck_qiguan", exam.getNeck_qiguan());
			if (neck_qiguan.equals("其它"))
				neck_qiguan = exam.getNeck_qiguan0();
			map.put("neck_qiguan", neck_qiguan);

			String neck_dongmai = DictionaryUtil.getPublicDictionaryText(
					pageCode, "neck_dongmai", exam.getNeck_dongmai());
			if (neck_dongmai.equals("其它"))
				neck_dongmai = exam.getNeck_dongmai0();
			map.put("neck_dongmai", neck_dongmai);

			String xiong_kuo = DictionaryUtil.getPublicDictionaryText(pageCode,
					"xiong_kuo", exam.getXiong_kuo());
			if (xiong_kuo.equals("其它"))
				xiong_kuo = exam.getXiong_kuo0();
			map.put("xiong_kuo", xiong_kuo);

			String fei_huxi = DictionaryUtil.getPublicDictionaryText(pageCode,
					"fei_huxi", exam.getFei_huxi());
			if (fei_huxi.equals("其它"))
				fei_huxi = exam.getFei_huxi0();
			map.put("fei_huxi", fei_huxi);
			

			String xinz_xinrate = DictionaryUtil.getPublicDictionaryText(
					pageCode, "xinz_xinrate", exam.getXinz_xinrate());
			if (xinz_xinrate.equals("其它"))
				xinz_xinrate = exam.getXinz_xinrate0();
			map.put("xinz_xinrate", xinz_xinrate);
			String xinz_xinyin = DictionaryUtil.getPublicDictionaryText(
					pageCode, "xinz_xinyin", exam.getXinz_xinyin());
			if (xinz_xinyin.equals("其它")){
				xinz_xinyin = exam.getXinz_xinyin0();
			}
			map.put("xinz_xinyin", xinz_xinyin);

			
			String xinz_zayin = DictionaryUtil.getPublicDictionaryText(
					pageCode, "xinz_zayin", exam.getXinz_zayin());
			if (xinz_zayin.equals("其它"))
				xinz_zayin = exam.getXinz_zayin0();
			map.put("xinz_zayin", xinz_zayin);

			String jisi_waixing = DictionaryUtil.getPublicDictionaryText(
					pageCode, "jisi_waixing", exam.getJisi_waixing());
			if (jisi_waixing.equals("其它"))
				jisi_waixing = exam.getJisi_waixing0();
			map.put("jisi_waixing", jisi_waixing);
			
			
			String shenjing_fubi = DictionaryUtil.getPublicDictionaryText(
					pageCode, "shenjing_fubi", exam.getShenjing_fubi());
			if (shenjing_fubi.equals("其它"))
				shenjing_fubi = exam.getShenjing_fubi0();
			map.put("shenjing_fubi", shenjing_fubi);

			String shenjing_jiaomo = DictionaryUtil.getPublicDictionaryText(
					pageCode, "shenjing_fubi", exam.getShenjing_jiaomo());
			if (shenjing_jiaomo.equals("其它"))
				shenjing_jiaomo = exam.getShenjing_jiaomo0();
			map.put("shenjing_jiaomo", shenjing_jiaomo);

			String shenjing_babinski = DictionaryUtil.getPublicDictionaryText(
					pageCode, "shenjing_babinski", exam.getShenjing_babinski());
			if (shenjing_babinski.equals("其它"))
				shenjing_babinski = exam.getShenjing_babinski0();
			map.put("shenjing_babinski", shenjing_babinski);
			
			

			String shenjing_huai = DictionaryUtil.getPublicDictionaryText(
					pageCode, "shenjing_huai", exam.getShenjing_huai());
			if (shenjing_huai.equals("其它"))
				shenjing_huai = exam.getShenjing_huai0();
			map.put("shenjing_huai", shenjing_huai);

			
			
		} catch (Exception e) {
			throw e;
		}
	}

	// 肝病实验室辅助检查
	private void Liver_LabExamination(Map<String, String> map, Long key)
			throws Exception {
		try {
			LabDiagnosticExamination lab = liverDAO
					.LabDiagnosticExamination_findByCaseID(key);
			if (lab != null)
				map.put("LabExamination", lab.getResult());
		} catch (Exception e) {
			throw e;
		}
	}

	// 肝病诊断
	/*
	 * private void Liver_Diagnosis(Map<String, String> map, Long key) throws
	 * Exception { DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); Date date
	 * = null; try { Diagnosis diag = liverDAO.Diagnosis_findByCaseID(key); if
	 * (diag != null) { date = diag.getChubu_checkdate(); if (date != null)
	 * map.put("chubu_checkdate", df.format(date)); map.put("chubu_diagnosis1",
	 * diag.getChubu_diagnosis1()); map.put("chubu_diagnosis2",
	 * diag.getChubu_diagnosis2()); map.put("chubu_diagnosis3",
	 * diag.getChubu_diagnosis3()); map.put("chubu_diagnosis4",
	 * diag.getChubu_diagnosis4()); map.put("chubu_diagnosis5",
	 * diag.getChubu_diagnosis5()); map.put("chubu_diagnosis6",
	 * diag.getChubu_diagnosis6()); map.put("chubu_diagnosis7",
	 * diag.getChubu_diagnosis7()); map.put("chubu_diagnosis8",
	 * diag.getChubu_diagnosis8()); map.put("chubu_diagnosis9",
	 * diag.getChubu_diagnosis9()); map.put("chubu_diagnosis10",
	 * diag.getChubu_diagnosis10()); map.put("chubu_diagnosis11",
	 * diag.getChubu_diagnosis11()); map.put("chubu_diagnosis12",
	 * diag.getChubu_diagnosis12()); map.put("chubu_diagnosis13",
	 * diag.getChubu_diagnosis13()); map.put("chubu_diagnosis14",
	 * diag.getChubu_diagnosis14()); date = diag.getQueding_checkdate(); if
	 * (date != null) map.put("queding_checkdate", df.format(date));
	 * map.put("queding_diagnosis1", diag.getQueding_diagnosis1());
	 * map.put("queding_diagnosis2", diag.getQueding_diagnosis2());
	 * map.put("queding_diagnosis3", diag.getQueding_diagnosis3());
	 * map.put("queding_diagnosis4", diag.getQueding_diagnosis4());
	 * map.put("queding_diagnosis5", diag.getQueding_diagnosis5());
	 * map.put("queding_diagnosis6", diag.getQueding_diagnosis6());
	 * map.put("queding_diagnosis7", diag.getQueding_diagnosis7());
	 * map.put("queding_diagnosis8", diag.getQueding_diagnosis8());
	 * map.put("queding_diagnosis9", diag.getQueding_diagnosis9());
	 * map.put("queding_diagnosis10", diag.getQueding_diagnosis10());
	 * map.put("queding_diagnosis11", diag.getQueding_diagnosis11());
	 * map.put("queding_diagnosis12", diag.getQueding_diagnosis12());
	 * map.put("queding_diagnosis13", diag.getQueding_diagnosis13());
	 * map.put("queding_diagnosis14", diag.getQueding_diagnosis14());
	 * map.put("chubu_inhspDoctor", DictionaryUtil
	 * .getIndependentDictionaryText("userName", diag .getChubu_inhspDoctorId()
	 * + "")); map.put("chubu_treatDoctor", DictionaryUtil
	 * .getIndependentDictionaryText("userName", diag .getChubu_treatDoctorId()
	 * + "")); date = diag.getChubu_inhspSignDate(); if (date != null)
	 * map.put("chubu_inhspSignDate", df.format(date)); date =
	 * diag.getChubu_treatSignDate(); if (date != null)
	 * map.put("chubu_treatSignDate", df.format(date));
	 * map.put("queding_inhspDoctor", DictionaryUtil
	 * .getIndependentDictionaryText("userName", diag
	 * .getQueding_inhspDoctorId() + "")); map.put("queding_treatDoctor",
	 * DictionaryUtil .getIndependentDictionaryText("userName", diag
	 * .getQueding_treatDoctorId() + "")); map.put("queding_directorDoctor",
	 * DictionaryUtil .getIndependentDictionaryText("userName", diag
	 * .getQueding_directorDoctorId() + "")); date =
	 * diag.getQueding_inhspSignDate(); if (date != null)
	 * map.put("queding_inhspSignDate", df.format(date)); date =
	 * diag.getQueding_treatSignDate(); if (date != null)
	 * map.put("queding_treatSignDate", df.format(date)); date =
	 * diag.getQueding_directorSignDate(); if (date != null)
	 * map.put("queding_directorSignDate", df.format(date)); } RevisedDiagnosis
	 * d = liverDAO.RevisedDiagnosis_findByCaseID(key); if (d != null) { date =
	 * d.getDz_checkdate(); if (date != null) map.put("dz_checkdate",
	 * df.format(date)); map.put("dz_diagnosis1", d.getDz_diagnosis1());
	 * map.put("dz_diagnosis2", d.getDz_diagnosis2()); map.put("dz_diagnosis3",
	 * d.getDz_diagnosis3()); map.put("dz_diagnosis4", d.getDz_diagnosis4());
	 * map.put("dz_diagnosis5", d.getDz_diagnosis5()); map.put("dz_diagnosis6",
	 * d.getDz_diagnosis6()); map.put("dz_diagnosis7", d.getDz_diagnosis7());
	 * map.put("dz_diagnosis8", d.getDz_diagnosis8()); map.put("dz_diagnosis9",
	 * d.getDz_diagnosis9()); map.put("dz_diagnosis10", d.getDz_diagnosis10());
	 * map.put("dz_diagnosis11", d.getDz_diagnosis11());
	 * map.put("dz_diagnosis12", d.getDz_diagnosis12());
	 * map.put("dz_diagnosis13", d.getDz_diagnosis13());
	 * map.put("dz_diagnosis14", d.getDz_diagnosis14());
	 * map.put("dz_inhspDoctor", DictionaryUtil
	 * .getIndependentDictionaryText("userName", d .getDz_inhspDoctorId() +
	 * "")); map.put("dz_treatDoctor", DictionaryUtil
	 * .getIndependentDictionaryText("userName", d .getDz_treatDoctorId() +
	 * "")); map.put("dz_directorDoctor", DictionaryUtil
	 * .getIndependentDictionaryText("userName", d .getDz_directorDoctorId() +
	 * "")); date = d.getDz_inhspSignDate(); if (date != null)
	 * map.put("dz_inhspSignDate", df.format(date)); date =
	 * d.getDz_treatSignDate(); if (date != null) map.put("dz_treatSignDate",
	 * df.format(date)); date = d.getDz_directorSignDate(); if (date != null)
	 * map.put("dz_directorSignDate", df.format(date)); } } catch (Exception e)
	 * { throw e; } }
	 */

	// 肝病诊断
	private void Liver_Diagnosis(Map<String, String> map, Long key)
			throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			Diagnosis diag = liverDAO.Diagnosis_findByCaseID(key);
			if (diag != null) {
				date = diag.getChubu_checkdate();
				if (date != null)
					map.put("chubu_diagnosis_time", df.format(date));
				if (diag.getChubu_bianbing() != null) {
					map.put("chubu_bianbing", diag.getChubu_bianbing());
				}
				if (diag.getChubu_zhizefangyao() != null) {
					map.put("chubu_zhizefangyao", diag.getChubu_zhizefangyao());
				}
				List<String> diagStr = new ArrayList<String>();
				diagStr.add(diag.getChubu_diagnosis1());
				diagStr.add(diag.getChubu_diagnosis2());
				diagStr.add(diag.getChubu_diagnosis3());
				diagStr.add(diag.getChubu_diagnosis4());
				diagStr.add(diag.getChubu_diagnosis5());
				diagStr.add(diag.getChubu_diagnosis6());
				diagStr.add(diag.getChubu_diagnosis7());
				diagStr.add(diag.getChubu_diagnosis8());
				diagStr.add(diag.getChubu_diagnosis9());
				diagStr.add(diag.getChubu_diagnosis10());
				diagStr.add(diag.getChubu_diagnosis11());
				diagStr.add(diag.getChubu_diagnosis12());
				diagStr.add(diag.getChubu_diagnosis13());
				diagStr.add(diag.getChubu_diagnosis14());
				StringBuffer sb = new StringBuffer();
				for (String str : diagStr) {
					if (str.length() > 0) {
						sb.append("<p>" + str + "</p>");
					}
				}
				if (sb.toString().length() > 0) {
					sb.append("<p>住院医师："
							+ DictionaryUtil.getIndependentDictionaryText(
									"userName", diag.getChubu_inhspDoctorId()
											+ "") + "</p>");
					sb.append("<p>主治医师："
							+ DictionaryUtil.getIndependentDictionaryText(
									"userName", diag.getChubu_treatDoctorId()
											+ "") + "</p>");
					map.put("chubu_diagnosis", sb.toString());
				}

				date = diag.getQueding_checkdate();
				if (date != null)
					map.put("queding_diagnosis_time", df.format(date));
				diagStr.clear();
				diagStr.add(diag.getQueding_diagnosis1());
				diagStr.add(diag.getQueding_diagnosis2());
				diagStr.add(diag.getQueding_diagnosis3());
				diagStr.add(diag.getQueding_diagnosis4());
				diagStr.add(diag.getQueding_diagnosis5());
				diagStr.add(diag.getQueding_diagnosis6());
				diagStr.add(diag.getQueding_diagnosis7());
				diagStr.add(diag.getQueding_diagnosis8());
				diagStr.add(diag.getQueding_diagnosis9());
				diagStr.add(diag.getQueding_diagnosis10());
				diagStr.add(diag.getQueding_diagnosis11());
				diagStr.add(diag.getQueding_diagnosis12());
				diagStr.add(diag.getQueding_diagnosis13());
				diagStr.add(diag.getQueding_diagnosis14());
				/* =======新增条数打印修改 09-13====== */
				diagStr.add(diag.getQueding_diagnosis15());
				diagStr.add(diag.getQueding_diagnosis16());
				diagStr.add(diag.getQueding_diagnosis17());
				diagStr.add(diag.getQueding_diagnosis18());
				diagStr.add(diag.getQueding_diagnosis19());
				diagStr.add(diag.getQueding_diagnosis20());

				sb = new StringBuffer();
				for (String str : diagStr) {
					if (str != null && str.length() > 0) {
						sb.append("<p>" + str + "</p>");
					}
				}
				if (sb.toString().length() > 0) {
					sb.append("<p>住院医师："
							+ DictionaryUtil.getIndependentDictionaryText(
									"userName", diag.getQueding_inhspDoctorId()
											+ "") + "</p>");
					sb.append("<p>主治医师："
							+ DictionaryUtil.getIndependentDictionaryText(
									"userName", diag.getQueding_treatDoctorId()
											+ "") + "</p>");
					sb.append("<p>副主任医师："
							+ DictionaryUtil.getIndependentDictionaryText(
									"userName", diag
											.getQueding_directorDoctorId()
											+ "") + "</p>");
					map.put("queding_diagnosis", sb.toString());
				}
			}
			RevisedDiagnosis d = liverDAO.RevisedDiagnosis_findByCaseID(key);
			if (d != null) {
				date = d.getDz_checkdate();
				if (date != null)
					map.put("dz_diagnosis_time", df.format(date));
				List<String> diagStr = new ArrayList<String>();
				diagStr.add(d.getDz_diagnosis1());
				diagStr.add(d.getDz_diagnosis2());
				diagStr.add(d.getDz_diagnosis3());
				diagStr.add(d.getDz_diagnosis4());
				diagStr.add(d.getDz_diagnosis5());
				diagStr.add(d.getDz_diagnosis6());
				diagStr.add(d.getDz_diagnosis7());
				diagStr.add(d.getDz_diagnosis8());
				diagStr.add(d.getDz_diagnosis9());
				diagStr.add(d.getDz_diagnosis10());
				diagStr.add(d.getDz_diagnosis11());
				diagStr.add(d.getDz_diagnosis12());
				diagStr.add(d.getDz_diagnosis13());
				diagStr.add(d.getDz_diagnosis14());
				StringBuffer sb = new StringBuffer();
				String space = "           ";
				for (String str : diagStr) {
					if (str.length() > 0) {
						sb.append("<p>" + space + str + "</p>");
					}
				}
				if (sb.toString().length() > 0) {
					sb.append("<p>"
							+ space
							+ "住院医师："
							+ DictionaryUtil.getIndependentDictionaryText(
									"userName", d.getDz_inhspDoctorId() + "")
							+ "</p>");
					sb.append("<p>"
							+ space
							+ "主治医师："
							+ DictionaryUtil.getIndependentDictionaryText(
									"userName", d.getDz_treatDoctorId() + "")
							+ "</p>");
					sb.append("<p>"
							+ space
							+ "副主任医师："
							+ DictionaryUtil
									.getIndependentDictionaryText("userName", d
											.getDz_directorDoctorId()
											+ "") + "</p>");
					map.put("dz_diagnosis", sb.toString());
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void Mouth_IllnessHistory(Map<String, String> map, Long key)
			throws Exception {
		try {
			com.juncsoft.KLJY.InHospitalCase.Mouth.entity.PresentIllnessHistory history = mouthDao
					.PresentIllnessHistory_findByCaseID(key);
			if (history != null) {
				String content1 = history.getContent1();
				if (content1 != null && content1.length() > 0) {
					content1 = "<div>" + content1 + "</div>";
				} else {
					content1 = "";
				}
				String content2 = history.getContent2();
				if (content2 != null && content2.length() > 0) {
					content2 = "<div>" + content2 + "</div>";
				} else {
					content2 = "";
				}
				map.put("PresentIllnessHistory", content1 + content2);
			}
			EpidemicDisHistory eHistory = liverDAO
					.EpidemicDis_findByCaseID(key);
			if (eHistory != null) {
				map.put("EpidemicDisHistory", eHistory.getEpidemicDisDesc());
			}
			PastHistory pHistory = liverDAO.PastHistory_findByCaseID(key);
			if (pHistory != null) {
				map.put("PastHistory", pHistory.getPastHistoryDesc());
			}
			PersonalHistory plHistory = liverDAO
					.PersonalHistory_findByCaseID(key);
			if (plHistory != null) {
				map.put("PersonalHistory", plHistory.getPersonalHistoryDesc());
			}
			MenstrualHistory mHistory = liverDAO
					.MenstrualHistory_findByCaseID(key);
			if (mHistory != null) {
				map.put("MenstrualHistory", mHistory.getMenstrualDesc());
			}
			MarritalChildbearingHistory mcHistory = liverDAO
					.MarritalChildbearingHistory_findByCaseID(key);
			if (mcHistory != null) {
				map.put("MarritalChildbearingHistory", mcHistory
						.getMarriageChildDesc());
			}
			FamilyHistory fHistory = liverDAO.FamilyHistory_findByCaseID(key);
			if (fHistory != null) {
				map.put("FamilyHistory", fHistory.getFaimlyHistoryDesc());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void Default_IllnessHistory(Map<String, String> map, Long key)
			throws Exception {
		try {
			com.juncsoft.KLJY.InHospitalCase.Default.entity.PresentIllnessHistory history = defaultDAO
					.pi_findByCaseId(key);
			if (history != null) {
				String content1 = history.getContent1();
				if (content1 != null && content1.length() > 0) {
					content1 = "<div>" + content1 + "</div>";
				} else {
					content1 = "";
				}
				String content2 = history.getContent2();
				if (content2 != null && content2.length() > 0) {
					content2 = "<div>" + content2 + "</div>";
				} else {
					content2 = "";
				}
				map.put("PresentIllnessHistory", content1 + content2);
			}
			EpidemicDisHistory eHistory = liverDAO
					.EpidemicDis_findByCaseID(key);
			if (eHistory != null) {
				map.put("EpidemicDisHistory", eHistory.getEpidemicDisDesc());
			}
			PastHistory pHistory = liverDAO.PastHistory_findByCaseID(key);
			if (pHistory != null) {
				map.put("PastHistory", pHistory.getPastHistoryDesc());
			}
			PersonalHistory plHistory = liverDAO
					.PersonalHistory_findByCaseID(key);
			if (plHistory != null) {
				map.put("PersonalHistory", plHistory.getPersonalHistoryDesc());
			}
			MenstrualHistory mHistory = liverDAO
					.MenstrualHistory_findByCaseID(key);
			if (mHistory != null) {
				map.put("MenstrualHistory", mHistory.getMenstrualDesc());
			}
			MarritalChildbearingHistory mcHistory = liverDAO
					.MarritalChildbearingHistory_findByCaseID(key);
			if (mcHistory != null) {
				map.put("MarritalChildbearingHistory", mcHistory
						.getMarriageChildDesc());
			}
			FamilyHistory fHistory = liverDAO.FamilyHistory_findByCaseID(key);
			if (fHistory != null) {
				map.put("FamilyHistory", fHistory.getFaimlyHistoryDesc());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void Default_SpecialExamination(Map<String, String> map, Long key)
			throws Exception {
		try {
			SpecialExamination exam = defaultDAO.se_findByCaseId(key);
			if (exam != null)
				map.put("SpecialExamination", exam.getResult());
		} catch (Exception e) {
			throw e;
		}
	}

	private void Surgery_SpecialExamination(Map<String, String> map, Long key)
			throws Exception {
		try {
			com.juncsoft.KLJY.InHospitalCase.Surgery.entity.SpecialExamination exam = surgeryDao
					.se_findByCaseId(key);
			if (exam != null) {
				String pageCode = "EMR-surgery-SpecialExamination";
				String temp = "";
				// 腹部-视诊
				map.put("fubu_waixing", exam.getFubu_waixing());
				if (exam.getFubu_huxi() == 1) {
					temp = "消失";
				} else {
					temp = "存在";
				}
				map.put("fubu_huxi", temp);
				if (exam.getFubu_jingmai() == 1) {
					temp = "有";
				} else {
					temp = "无";
				}
				map.put("fubu_jingmai", temp);
				if (exam.getFubu_shizhen_o() == 1) {
					temp = exam.getFubu_shizhen_oDesc();
				} else {
					temp = "无";
				}
				map.put("fubu_shizhen_o", temp);
				// 腹部-触诊
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"fubu_fubi", exam.getFubu_fubi());
				if (temp.equals("其它")) {
					temp = exam.getFubu_fubi0();
				}
				map.put("fubu_fubi", temp);
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"fubu_jijzh", exam.getFubu_jijzh());
				if (temp.equals("其它")) {
					temp = exam.getFubu_jijzh0();
				}
				map.put("fubu_jijzh", temp);
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"fubu_jijzh", exam.getFubu_yatong());
				if (temp.equals("其它")) {
					temp = exam.getFubu_yatong0();
				}
				map.put("fubu_yatong", temp);
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"fubu_jijzh", exam.getFubu_fantt());
				if (temp.equals("其它")) {
					temp = exam.getFubu_fantt0();
				}
				map.put("fubu_fantt", temp);
				if (exam.getFubu_baokuai() == 1) {
					temp = exam.getFubu_baokuaiDesc();
				} else {
					temp = "无";
				}
				map.put("fubu_baokuai", temp);
				if (exam.getFubu_yebo() == 1) {
					temp = "有";
				} else {
					temp = "无";
				}
				map.put("fubu_yebo", temp);
				if (exam.getFubu_zhenshui() == 1) {
					temp = "有";
				} else {
					temp = "无";
				}
				map.put("fubu_zhenshui", temp);
				map.put("fubu_murphy", DictionaryUtil.getPublicDictionaryText(
						pageCode, "fubu_murphy", exam.getFubu_murphy()));
				if (exam.getFubu_ganzang() == 1) {
					temp = exam.getFubu_ganzangDesc();
				} else {
					temp = "未触及";
				}
				map.put("fubu_ganzang", temp);
				if (exam.getFubu_dannang() == 1) {
					temp = exam.getFubu_dannangDesc();
				} else {
					temp = "未触及";
				}
				map.put("fubu_dannang", temp);
				if (exam.getFubu_pi() == 1) {
					temp = exam.getFubu_piDesc();
				} else {
					temp = "未触及";
				}
				map.put("fubu_pi", temp);
				if (exam.getFubu_shen() == 1) {
					temp = exam.getFubu_shenDesc();
				} else {
					temp = "未触及";
				}
				map.put("fubu_shen", temp);
				// 腹部-叩诊
				map.put("fubu_ganzhuo", DictionaryUtil.getPublicDictionaryText(
						pageCode, "fubu_ganzhuo", exam.getFubu_ganzhuo()));
				map.put("fubu_ganshangjie", exam.getFubu_ganshangjie());
				if (exam.getFubu_ganqukt() == 1) {
					temp = "有";
				} else {
					temp = "无";
				}
				map.put("fubu_ganqukt", temp);
				map.put("fubu_shenkt", DictionaryUtil.getPublicDictionaryText(
						pageCode, "fubu_shenkt", exam.getFubu_shenkt()));
				map.put("fubu_yidong", DictionaryUtil.getPublicDictionaryText(
						pageCode, "fubu_murphy", exam.getFubu_yidong()));
				map.put("fubu_fushui", DictionaryUtil.getPublicDictionaryText(
						pageCode, "fubu_fushui", exam.getFubu_fushui()));
				// 腹部-听诊
				map.put("fubu_changming", exam.getFubu_changming());
				if (exam.getFubu_qishui() == 1) {
					temp = "有";
				} else {
					temp = "无";
				}
				map.put("fubu_qishui", temp);
				if (exam.getFubu_xueguan() == 1) {
					temp = exam.getFubu_xueguanDesc();
				} else {
					temp = "无";
				}
				map.put("fubu_xueguan", temp);
				map.put("specialExamOther", exam.getSpecialExamOther());
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void ENT_SpecialExamination(Map<String, String> map, Long key)
			throws Exception {
		try {
			com.juncsoft.KLJY.InHospitalCase.ENT.entity.SpecialExamination exam = entDao
					.SpecialExamination_findByCaseId(key);
			String pageCode = "EMR-erbih-specExam";
			if (exam != null) {
				String temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"erkou_chongxue", exam.getErkou_chongxue());
				map.put("erkou_chongxue", temp);
				map.put("erkou_shuizhong", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getErkou_shuizhong()));
				/* 肿物 */
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"erkou_zhongwu", exam.getErkou_zhongwu());
				String value = "";
				if (temp.equals("可见")) {
					value = DictionaryUtil.getPublicDictionaryText(pageCode,
							"erkou_weizhi", exam.getErkou_weizhi())
							+ "，";
					value += "表面"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"erkou_biaomian", exam.getErkou_biaomian())
							+ "，";

					value += "大小" + exam.getErkou_daxiao() + "cm。";
					map.put("erkou_zhongwu", value);
					value = "";
				} else {
					map.put("erkou_zhongwu", temp);
				}
				map.put("erkou_other", exam.getErkou_other());

				map.put("waierdao_chongxue", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getWaierdao_chongxue()));
				map.put("waierdao_shuizhong", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getWaierdao_shuizhong()));
				map.put("waierdao_jiezhong", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getWaierdao_jiezhong()));
				/* 肿物 */
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_zhongwu", exam.getWaierdao_zhongwu());
				if (!temp.equals("未见")) {
					value = temp + "，";
					value += "表面"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"erkou_biaomian", exam
											.getWaierdao_biaomian()) + "，";
					value += "大小" + exam.getWaierdao_daxiao() + "cm。";
					map.put("waierdao_zhongwu", value);
					value = "";
				} else {
					map.put("waierdao_zhongwu", temp);
				}
				map.put("waierdao_loukou", DictionaryUtil
						.getPublicDictionaryText(pageCode, "waierdao_zhongwu",
								exam.getWaierdao_loukou()));
				map.put("waierdao_xiazhai", DictionaryUtil
						.getPublicDictionaryText(pageCode, "waierdao_zhongwu",
								exam.getWaierdao_xiazhai()));
				/* 分泌物 */
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_fenbiwu", exam.getWaierdao_fenbiwu());
				if (!temp.equals("无")) {
					value = temp + "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"waierdao_qiwei", exam.getWaierdao_qiwei())
							+ "气味。";
					map.put("waierdao_fenbiwu", value);
					value = "";
				} else {
					map.put("waierdao_fenbiwu", temp);
				}
				map.put("waierdao_other", exam.getWaierdao_other());

				map.put("gumo_yanse", DictionaryUtil.getPublicDictionaryText(
						pageCode, "gumo_yanse", exam.getGumo_yanse()));
				map.put("gumo_biaozhi", DictionaryUtil.getPublicDictionaryText(
						pageCode, "gumo_biaozhi", exam.getGumo_biaozhi()));
				map.put("gumo_chongxue", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getGumo_chongxue()));
				map.put("gumo_zhongzhang", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getGumo_zhongzhang()));
				map.put("gumo_neixian", DictionaryUtil.getPublicDictionaryText(
						pageCode, "erkou_chongxue", exam.getGumo_neixian()));
				map.put("gumo_zenghou", DictionaryUtil.getPublicDictionaryText(
						pageCode, "erkou_chongxue", exam.getGumo_zenghou()));
				map.put("gumo_jiye", DictionaryUtil.getPublicDictionaryText(
						pageCode, "erkou_chongxue", exam.getGumo_jiye()));
				map.put("gumo_rouya", DictionaryUtil.getPublicDictionaryText(
						pageCode, "erkou_chongxue", exam.getGumo_rouya()));
				map.put("gumo_danzhiliu", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getGumo_danzhiliu()));
				/* 穿孔 */
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"erkou_chongxue", exam.getGumo_chuankong());
				if (!temp.equals("无")) {
					value = temp
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"gumo_weiyu", exam.getGumo_weiyu());
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"gumo_type", exam.getGumo_type())
							+ "穿孔，呈"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"gumo_xingzhuang", exam
											.getGumo_xingzhuang()) + "。";
					map.put("gumo_chuankong", value);
				} else {
					map.put("gumo_chuankong", temp);
				}
				map.put("gumo_other", exam.getGumo_other());
				
				map.put("waibi_pifu", DictionaryUtil.getPublicDictionaryText(
						pageCode, "waibi_pifu", exam.getWaibi_pifu()));
				map.put("waibi_waiqu", DictionaryUtil.getPublicDictionaryText(
						pageCode, "waierdao_qiwei", exam.getWaibi_waiqu()));
				map.put("waibi_anbi", DictionaryUtil.getPublicDictionaryText(
						pageCode, "waierdao_qiwei", exam.getWaibi_anbi()));
				map.put("biqianting_hongzhong", DictionaryUtil
						.getPublicDictionaryText(pageCode, "waierdao_qiwei",
								exam.getBiqianting_hongzhong()));
				map.put("biqianting_longqi", DictionaryUtil
						.getPublicDictionaryText(pageCode, "waierdao_qiwei",
								exam.getBiqianting_longqi()));
				map.put("biqianting_cucao", DictionaryUtil
						.getPublicDictionaryText(pageCode, "waierdao_qiwei",
								exam.getBiqianting_cucao()));
				/* 肿物 */
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"erkou_chongxue", exam.getBiqiang_zhongwu());
				if (!temp.equals("无")) {
					value = temp
							+ "，"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"biqiang_shulian", exam
											.getBiqiang_shulian()) + "，";
					value += "表面"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"biqiang_biaomian", exam
											.getBiqiang_biaomian()) + "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_yanse", exam.getBiqiang_yanse())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_xingzhuang", exam.getBiqiang_xingzhuang())
							+ "，";
					value += "探触"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"biqiang_tanzhongwu", exam
											.getBiqiang_tanzhongwu()) + "，";
					value += "基底"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"biqiang_jidi", exam.getBiqiang_jidi())
							+ "。";
					map.put("biqiang_zhongwu", value);
				} else {
					map.put("biqiang_zhongwu", temp);
				}
				map.put("biqian_other", exam.getBiqian_other());
				
				map.put("bijia_zhongzhang", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getBijia_zhongzhang()));
				map.put("bijia_chongxue", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getBijia_chongxue()));
				map.put("bijia_shuizhong", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getBijia_shuizhong()));
				map.put("bijia_yanse", DictionaryUtil.getPublicDictionaryText(
						pageCode, "bijia_yanse", exam.getBijia_yanse()));
				map.put("bijia_biaomian", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_biaomian",
								exam.getBijia_biaomian()));
				map.put("bijia_xingtai", DictionaryUtil
						.getPublicDictionaryText(pageCode, "bijia_xingtai",
								exam.getBijia_xingtai()));
				map.put("bijia_other", exam.getBijia_other());
				/* 分泌物 */
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"erkou_chongxue", exam.getBidao_fenbiwu());
				if (!temp.equals("无")) {
					value = temp
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"bidao_fenbiwu_weizhi", exam
											.getBidao_fenbiwu_weizhi()) + "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"bidao_yanse", exam.getBidao_yanse())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"bidao_xingzhi", exam.getBidao_xingzhi())
							+ "。";
					map.put("bidao_fenbiwu", value);
				} else {
					map.put("bidao_fenbiwu", temp);
				}
				/* 肿物 */
//				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
//						"erkou_chongxue", exam.getBidao_zhongwu());
//				if (!temp.equals("无")) {
//					value = temp
//							+ "，"
//							+ DictionaryUtil.getPublicDictionaryText(pageCode,
//									"biqiang_shulian", exam.getBidao_shulian())
//							+ "，";
//					value += "表面"
//							+ DictionaryUtil.getPublicDictionaryText(pageCode,
//									"biqiang_biaomian", exam
//											.getBidao_biaomian()) + "，";
//					value += DictionaryUtil.getPublicDictionaryText(pageCode,
//							"biqiang_yanse", exam.getBidao_zhongwu_yanse())
//							+ "，";
//					value += DictionaryUtil.getPublicDictionaryText(pageCode,
//							"biqiang_xingzhuang", exam.getBidao_xingzhuang())
//							+ "，";
//					value += "探触"
//							+ DictionaryUtil.getPublicDictionaryText(pageCode,
//									"biqiang_tanzhongwu", exam
//											.getBidao_tanzhongwu()) + "，";
//					value += "基底"
//							+ DictionaryUtil.getPublicDictionaryText(pageCode,
//									"biqiang_jidi", exam.getBidao_jidi()) + "。";
//					map.put("bidao_zhongwu", value);
//				} else {
//					map.put("bidao_zhongwu", temp);
//				}
				map.put("bidao_feida", DictionaryUtil.getPublicDictionaryText(
						pageCode, "erkou_chongxue", exam.getBidao_feida()));
				map.put("bidao_zhongjia", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getBidao_zhongjia()));
				map.put("bidao_pianqu", DictionaryUtil.getPublicDictionaryText(
						pageCode, "bidao_pianqu", exam.getBidao_pianqu()));
				map.put("bidao_guji", DictionaryUtil.getPublicDictionaryText(
						pageCode, "erkou_chongxue", exam.getBidao_guji()));
				map.put("bidao_milan", DictionaryUtil.getPublicDictionaryText(
						pageCode, "erkou_chongxue", exam.getBidao_milan()));
				map.put("bidao_kuiyang", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getBidao_kuiyang()));
				map.put("bizhongge_other", exam.getBizhongge_other());
				/* 鼻中隔穿孔 */
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getBidao_chuankong());
				if (!temp.equals("无")) {
					value = "大小" + exam.getBidao_chuankong_daxiao() + "cm，";
					value += "位于"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"bidao_chuankong_weizhi", exam
											.getBidao_chuankong_weizhi()) + "。";
					map.put("bidao_chuankong", value);
				} else {
					map.put("bidao_chuankong", temp);
				}
				map.put("yanyinwo_chongxue", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getYanyinwo_chongxue()));
				map.put("yanyinwo_shuizhong", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getYanyinwo_shuizhong()));
				map.put("yanyinwo_nianmo", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_biaomian",
								exam.getYanyinwo_nianmo()));

				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getYanyinwo_xinshengwu());
				if (!temp.equals("无")) {
					value = "表面"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"erkou_biaomian", exam
											.getYanyinwo_biaomian()) + "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_yanse", exam.getYanyinwo_yanse())
							+ "，";
					value += DictionaryUtil
							.getPublicDictionaryText(pageCode,
									"biqiang_xingzhuang", exam
											.getYanyinwo_xingzhuang())
							+ "。";
					map.put("yanyinwo_xinshengwu", value);
				} else {
					map.put("yanyinwo_xinshengwu", temp);
				}
				map.put("biyanbu_other", exam.getBiyanbu_other());
				
//				map.put("yggyz_chongxue", DictionaryUtil
//						.getPublicDictionaryText(pageCode, "erkou_chongxue",
//								exam.getYggyz_chongxue()));
//				map.put("yggyz_shuizhong", DictionaryUtil
//						.getPublicDictionaryText(pageCode, "erkou_chongxue",
//								exam.getYggyz_shuizhong()));
//				map.put("yggyz_nianmo", DictionaryUtil.getPublicDictionaryText(
//						pageCode, "erkou_biaomian", exam.getYggyz_nianmo()));
//				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
//						"waierdao_qiwei", exam.getYggyz_xinshengwu());
//				if (!temp.equals("无")) {
//					value = "表面"
//							+ DictionaryUtil.getPublicDictionaryText(pageCode,
//									"erkou_biaomian", exam.getYggyz_biaomian())
//							+ "，";
//					value += DictionaryUtil.getPublicDictionaryText(pageCode,
//							"biqiang_yanse", exam.getYggyz_yanse())
//							+ "，";
//					value += DictionaryUtil.getPublicDictionaryText(pageCode,
//							"biqiang_xingzhuang", exam.getYggyz_xingzhuang())
//							+ "。";
//					map.put("yggyz_xinshengwu", value);
//				} else {
//					map.put("yggyz_xinshengwu", temp);
//				}

//				map.put("biyandingbi_chongxue", DictionaryUtil
//						.getPublicDictionaryText(pageCode, "erkou_chongxue",
//								exam.getBiyandingbi_chongxue()));
//				map.put("biyandingbi_shuizhong", DictionaryUtil
//						.getPublicDictionaryText(pageCode, "erkou_chongxue",
//								exam.getBiyandingbi_shuizhong()));
//				map.put("biyandingbi_nianmo", DictionaryUtil
//						.getPublicDictionaryText(pageCode, "erkou_biaomian",
//								exam.getBiyandingbi_nianmo()));
//				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
//						"waierdao_qiwei", exam.getBiyandingbi_xinshengwu());
//				if (!temp.equals("无")) {
//					value = "表面"
//							+ DictionaryUtil.getPublicDictionaryText(pageCode,
//									"erkou_biaomian", exam
//											.getBiyandingbi_biaomian()) + "，";
//					value += DictionaryUtil.getPublicDictionaryText(pageCode,
//							"biqiang_yanse", exam.getBiyandingbi_yanse())
//							+ "，";
//					value += DictionaryUtil.getPublicDictionaryText(pageCode,
//							"biyandingbi_xingzhuang", exam
//									.getBiyandingbi_xingzhuang())
//							+ "。";
//					map.put("biyandingbi_xinshengwu", value);
//				} else {
//					map.put("biyandingbi_xinshengwu", temp);
//				}

				map.put("ruane_tanhuan", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getRuane_tanhuan()));
				map.put("ruane_chuongxue", DictionaryUtil
						.getPublicDictionaryText(pageCode, "waierdao_qiwei",
								exam.getRuane_chuongxue()));
				map.put("ruane_kuiyan", DictionaryUtil.getPublicDictionaryText(
						pageCode, "waierdao_qiwei", exam.getRuane_kuiyan()));
				map.put("ruane_quesun", DictionaryUtil.getPublicDictionaryText(
						pageCode, "erkou_chongxue", exam.getRuane_quesun()));
				map.put("ruane_bahen", DictionaryUtil.getPublicDictionaryText(
						pageCode, "erkou_chongxue", exam.getRuane_bahen()));
				map.put("ruane_penglong", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getRuane_penglong()));
				map.put("ruane_other", exam.getRuane_other());
				
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getRuane_xinshengwu());
				if (!temp.equals("无")) {
					value = "表面"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"erkou_biaomian", exam.getRuane_biaomian())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_yanse", exam.getRuane_yanse())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_xingzhuang", exam.getRuane_xingzhuang())
							+ "，";
					value += "大小" + exam.getRuane_daxiao() + "cm。";
					map.put("ruane_xinshengwu", value);
				} else {
					map.put("ruane_xinshengwu", temp);
				}
				map.put("ruane_xuanyongchui", DictionaryUtil
						.getPublicDictionaryText(pageCode,
								"ruane_xuanyongchui", exam
										.getRuane_xingzhuang()));

				map.put("btti_zhongda", DictionaryUtil.getPublicDictionaryText(
						pageCode, "erkou_chongxue", exam.getBtti_zhongda()));
				map.put("btti_daxiao", DictionaryUtil.getPublicDictionaryText(
						pageCode, "btti_daxiao", exam.getBtti_daxiao()));
				map.put("btti_chongxue", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getBtti_chongxue()));
				map.put("btti_nongdian", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getBtti_nongdian()));
				map.put("btti_kuiyang", DictionaryUtil.getPublicDictionaryText(
						pageCode, "erkou_chongxue", exam.getBtti_kuiyang()));
				map.put("btti_jiaohuawu", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_chongxue",
								exam.getBtti_jiaohuawu()));
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"erkou_chongxue", exam.getBtti_xinshengwu());
				if (!temp.equals("无")) {
					value = temp + "，";
					value += "表面"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"btti_biaomian", exam.getBtti_biaomian())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_yanse", exam.getBtti_yanse())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_xingzhuang", exam.getBtti_xingzhuang())
							+ "，";
					value += "大小" + exam.getBtti_daxiao() + "cm。";
					map.put("btti_xinshengwu", value);
				} else {
					map.put("btti_xinshengwu", temp);
				}
				
				map.put("shengdai_other", exam.getShengdai_other());
				map.put("yhb_chongxue", DictionaryUtil.getPublicDictionaryText(
						pageCode, "waierdao_qiwei", exam.getYhb_chongxue()));
				map.put("yhb_linba", DictionaryUtil.getPublicDictionaryText(
						pageCode, "waierdao_qiwei", exam.getYhb_linba()));
				map.put("yhb_nianmo", DictionaryUtil.getPublicDictionaryText(
						pageCode, "yhb_nianmo", exam.getYhb_nianmo()));
				map.put("yhb_cesuo", DictionaryUtil.getPublicDictionaryText(
						pageCode, "yhb_cesuo", exam.getYhb_cesuo()));

				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getHuiyan_chongxue());
				if (!temp.equals("无")) {
					value = DictionaryUtil.getPublicDictionaryText(pageCode,
							"huiyan_chongxue_chengdu", exam
									.getHuiyan_chongxue_chengdu());
					map.put("huiyan_chongxue", value);
				} else {
					map.put("huiyan_chongxue", temp);
				}
				map.put("huiyan_zhongzhang", DictionaryUtil
						.getPublicDictionaryText(pageCode, "waierdao_qiwei",
								exam.getHuiyan_zhongzhang()));
				map.put("huiyan_nianmo", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_biaomian",
								exam.getHuiyan_nianmo()));
				map.put("huiyan_taiju", DictionaryUtil.getPublicDictionaryText(
						pageCode, "huiyan_taiju", exam.getHuiyan_taiju()));
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getHuiyan_xinshengwu());
				if (!temp.equals("无")) {
					value = DictionaryUtil.getPublicDictionaryText(pageCode,
							"huiyan_cebie", exam.getHuiyan_cebie());
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"huiyan_weizhi", exam.getHuiyan_weizhi())
							+ "，";
					value += "表面"
							+ DictionaryUtil
									.getPublicDictionaryText(pageCode,
											"erkou_biaomian", exam
													.getHuiyan_biaomian())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_yanse", exam.getHuiyan_yanse())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_xingzhuang", exam.getHuiyan_xingzhuang())
							+ "，大小" + exam.getHuiyan_daxiao() + "cm。";
					map.put("huiyan_xinshengwu", value);
				} else {
					map.put("huiyan_xinshengwu", temp);
				}
				map.put("shidai_nianmo", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_biaomian",
								exam.getShidai_nianmo()));
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getShidai_chongxue());
				if (!temp.equals("无")) {
					value = DictionaryUtil.getPublicDictionaryText(pageCode,
							"shidai_chongxue_cebie", exam
									.getShidai_chongxue_cebie())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"huiyan_chongxue_chengdu", exam
									.getShidai_chongxue_chengdu())
							+ "。";
					map.put("shidai_chongxue", value);
				} else {
					map.put("shidai_chongxue", temp);
				}
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getShidai_xiachu());
				if (!temp.equals("无")) {
					map.put("shidai_xiachu", DictionaryUtil
							.getPublicDictionaryText(pageCode,
									"huiyan_chongxue_chengdu", exam
											.getShidai_xiachu_chengdu()));
				} else {
					map.put("shidai_xiachu", temp);
				}
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getShidai_feihou());
				if (!temp.equals("无")) {
					map.put("shidai_feihou", DictionaryUtil
							.getPublicDictionaryText(pageCode,
									"huiyan_chongxue_chengdu", exam
											.getShidai_feihou_chengdu()));
				} else {
					map.put("shidai_feihou", temp);
				}
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getShidai_xinshengwu());
				if (!temp.equals("无")) {
					value = "表面"
							+ DictionaryUtil
									.getPublicDictionaryText(pageCode,
											"erkou_biaomian", exam
													.getShidai_biaomian())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_yanse", exam.getShidai_yanse())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_xingzhuang", exam.getShidai_xingzhuang())
							+ "。";
					map.put("shidai_xinshengwu", value);
				} else {
					map.put("shidai_xinshengwu", temp);
				}
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"houshi_qingxi", exam.getHoushi_qingxi());
				if (!temp.equals("否")) {
					map.put("houshi_qingxi", DictionaryUtil
							.getPublicDictionaryText(pageCode,
									"shidai_chongxue_cebie", exam
											.getHoushi_cebie()));
				} else {
					map.put("houshi_qingxi", temp);
				}
				map.put("houshi_nianmo", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_biaomian",
								exam.getHoushi_nianmo()));

				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getHoushi_xinshengwu());
				if (!temp.equals("无")) {
					value = "表面"
							+ DictionaryUtil
									.getPublicDictionaryText(pageCode,
											"erkou_biaomian", exam
													.getHoushi_biaomian())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_yanse", exam.getHoushi_yanse())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_xingzhuang", exam.getHoushi_xingzhuang())
							+ "。";
					map.put("houshi_xinshengwu", value);
				} else {
					map.put("houshi_xinshengwu", temp);
				}
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"shaozhuang_yundong", exam.getShaozhuang_yundong());
				if (!temp.equals("正常")) {
					value = DictionaryUtil
							.getPublicDictionaryText(pageCode,
									"shidai_chongxue_cebie", exam
											.getShaozhuang_cebie())
							+ temp + "，";
					value += "运动对称性"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"shaozhuang_duichen", exam
											.getShaozhuang_duichen()) + "。";
					map.put("shaozhuang_yundong", value);
				} else {
					map.put("shaozhuang_yundong", temp);
				}
				map.put("shaozhuang_chongxue", DictionaryUtil
						.getPublicDictionaryText(pageCode, "waierdao_qiwei",
								exam.getShaozhuang_chongxue()));
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getShaozhuang_xinshengwu());
				if (!temp.equals("无")) {
					value = "表面"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"erkou_biaomian", exam
											.getShaozhuang_biaomian()) + "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_yanse", exam.getShaozhuang_yanse())
							+ "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"biqiang_xingzhuang", exam
									.getShaozhuang_xingzhuang())
							+ "。";
					map.put("shaozhuang_xinshengwu", value);
				} else {
					map.put("shaozhuang_xinshengwu", temp);
				}
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"erkou_biaomian", exam.getLizhuang_nianmo());
				if (!temp.equals("光滑")) {
					map.put("lizhuang_nianmo", DictionaryUtil
							.getPublicDictionaryText(pageCode,
									"shaozhuang_cebie", exam
											.getLizhuang_cebie())
							+ temp);
				} else {
					map.put("lizhuang_nianmo", temp);
				}
				map.put("lizhuang_jiye", DictionaryUtil
						.getPublicDictionaryText(pageCode, "waierdao_qiwei",
								exam.getLizhuang_jiye()));
				map.put("lizhuang_xingtai", DictionaryUtil
						.getPublicDictionaryText(pageCode, "lizhuang_xingtai",
								exam.getLizhuang_xingtai()));
				temp = DictionaryUtil.getPublicDictionaryText(pageCode,
						"waierdao_qiwei", exam.getShaozhuang_xinshengwu());
				if (!temp.equals("无")) {
					value = "表面"
							+ DictionaryUtil.getPublicDictionaryText(pageCode,
									"erkou_biaomian", exam
											.getLizhuang_biaomian()) + "，";
					value += DictionaryUtil.getPublicDictionaryText(pageCode,
							"lizhuang_yanse", exam.getLizhuang_yanse())
							+ "，";
					value += DictionaryUtil
							.getPublicDictionaryText(pageCode,
									"biqiang_xingzhuang", exam
											.getLizhuang_xingzhuang())
							+ "。";
					map.put("lizhuang_zhongwu", value);
				} else {
					map.put("lizhuang_zhongwu", temp);
				}
				map.put("huanhou_nianmo", DictionaryUtil
						.getPublicDictionaryText(pageCode, "erkou_biaomian",
								exam.getHuanhou_nianmo()));
				map.put("huanhou_jiye", DictionaryUtil.getPublicDictionaryText(
						pageCode, "waierdao_qiwei", exam.getHuanhou_jiye()));
				map.put("huanhou_xingtai", DictionaryUtil
						.getPublicDictionaryText(pageCode, "lizhuang_xingtai",
								exam.getHuanhou_xingtai()));
				map.put("huanhou_shengmen", DictionaryUtil
						.getPublicDictionaryText(pageCode, "huanhou_shengmen",
								exam.getHuanhou_shengmen()));
				map.put("houshi_other", exam.getHoushi_other());
				map.put("shaozhuang_other", exam.getShaozhuang_other());
				map.put("huanhouxi_other", exam.getHuanhouxi_other());
				map.put("zuihou_other", exam.getZuihou_other());
				
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void ENT_PresentIllnessHistory(Map<String, String> map, Long key)
			throws Exception {
		try {
			com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistoryENT history = entDao
					.PresentIllness_findByCaseId(key);
			if (history != null) {
				map.put("PresentIllnessHistory", "<div>" + history.getContent()
						+ "</div>");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 单独页表头信息
	 * 
	 * @param kid
	 *            09-20============================
	 * @return
	 * @throws Exception
	 */
	public String getPageTableInfo(Long kid) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONObject json = new JSONObject();
		try {
			String sql = "select patientName,patientNo,inHspTimes from t_inHsp_casemaster cm , t_patient p where p.id = cm.patientId and cm.id = ?";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, kid);
			rs = ps.executeQuery();
			while (rs.next()) {
				json.put("patientName", rs.getString("patientName"));
				json.put("patientNo", rs.getString("patientNo"));
				json.put("inHspTimes", rs.getInt("inHspTimes"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return json.toString();
	}
}
