package com.juncsoft.KLJY.InHospitalCase.CasePrint;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.CourseRecording.dao.CourseRecordingDao;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.impl.CourseRecordingDaoImpl;

public class SinglePagePrint {
	//病程记录
	CourseRecordingDao dao = new CourseRecordingDaoImpl();
	
	public Map<String, String> getPrintData(int caseType,Long kid) throws Exception{
		System.out.println("caseType：" + caseType);
		Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());
		
		if(SinglePageCaseType.CONSULT_CASE ==caseType){
			//会诊记录
			consultPrintData(map,kid);
		}else if(SinglePageCaseType.PREVIOUS_DISCUSS_CASE == caseType){
			//术前病历讨论
			previousCaseDiscuss(map,kid);
		}else if(SinglePageCaseType.PREVIOUS_SURGERY_CASE == caseType){
			//术前小结
			previousSurgerySummary(map,kid);
		}else if(SinglePageCaseType.SURGERY_RECORD_CASE == caseType){
			//手术记录
			surgeryRecord(map,kid);
		}else if(SinglePageCaseType.DEAHT_RECORD_CASE == caseType){
			//死亡记录
			deathRecord(map,kid);
		}else if(SinglePageCaseType.DEATH_CASE == caseType){
			//死亡病历讨论
			deathCaseDiscuss(map,kid);
		}else if(SinglePageCaseType.INHSPREC24_CASE == caseType){
			//24小时出入院
			InHspRc24(map,kid);
		}
		return map;
	}
	
	/**
	 * 会诊记录打印
	 * @param map
	 * @param id
	 * @throws Exception
	 */
	private void consultPrintData(Map<String, String> map,Long id)throws Exception{
		if(id != null && id >0){
			JSONObject json = dao.Consultation_print(id);
			if(json != null){
				getSinglePageTableInfo(map,Long.parseLong(json.getString("caseId")));
				map.put("name", json.getString("name"));
				map.put("gender", json.getString("gender"));
				map.put("age", json.getString("age"));
				map.put("ward", json.getString("ward"));
				map.put("bedNo", json.getString("bedNo"));
				map.put("appDoctors", json.getString("appDoctors"));
				map.put("superDoctors", json.getString("superDoctors"));
				map.put("invitedWard", json.getString("invitedWard"));
				map.put("invitedDoctors", json.getString("invitedDoctors"));
				map.put("conLv", json.getString("conLv"));
				map.put("apptime", json.getString("apptime"));
				map.put("simpleCase", json.getString("simpleCase"));
				map.put("conTarget", json.getString("conTarget"));
				map.put("conAdv", json.getString("conAdv"));
				map.put("recName", json.getString("recName"));
				map.put("rectime", json.getString("rectime"));
			}
		}
	}

	/**
	 * 术前病历讨论
	 * @param map
	 * @param id 术前病历讨论实体ID值
	 * @throws Exception
	 */
	private void previousCaseDiscuss(Map<String, String> map,Long id)throws Exception{
		if(id != null && id >0){
			JSONObject json = dao.PreviousCaseDiscuss_print(id);
			if(json!=null){
				getSinglePageTableInfo(map,Long.parseLong(json.getString("caseId")));
				map.put("time", json.getString("time"));
				map.put("name", json.getString("name"));
				map.put("gender", json.getString("gender"));
				map.put("age", json.getString("age"));
				map.put("inhspTime", json.getString("inhspTime"));
				map.put("inhspTime2", json.getString("inhspTime2"));
				map.put("discussTime", json.getString("discussTime"));
				map.put("joinPerson", json.getString("joinPerson"));
				map.put("discussPlace", json.getString("discussPlace"));
				map.put("chairPerson", json.getString("chairPerson"));
				map.put("discussSubject", json.getString("discussSubject"));
				map.put("discussContent", json.getString("discussContent"));
				map.put("recName", json.getString("recName"));
				map.put("surgeryName", json.getString("surgeryName"));
			}
		}
	}

	/**
	 * 术前小结
	 * @param map
	 * @param id 实体ID值
	 * @throws Exception
	 */
	private void previousSurgerySummary(Map<String, String> map,Long id)throws Exception{
		if(id != null && id >0){
			JSONObject json = dao.PreviousSurgerySummary_print(id);
			if(json != null){
				getSinglePageTableInfo(map,Long.parseLong(json.getString("caseId")));
				map.put("time", json.getString("time"));
				map.put("name", json.getString("name"));
				map.put("gender", json.getString("gender"));
				map.put("age", json.getString("age"));
				map.put("inhspTime", json.getString("inhspTime"));
				map.put("inhspTime2", json.getString("inhspTime2"));
				map.put("diag", json.getString("diag"));
				map.put("diagBasis", json.getString("diagBasis"));
				map.put("planSurgery", json.getString("planSurgery"));
				map.put("surgerySysptom", json.getString("surgerySysptom"));
				map.put("anesthesiaMode", json.getString("anesthesiaMode"));
				map.put("surgeryTime", json.getString("surgeryTime"));
				map.put("surgeryPrevPrepar", json.getString("surgeryPrevPrepar"));
				map.put("surgeryAttention", json.getString("surgeryAttention"));
				map.put("surgeryAfterDeal", json.getString("surgeryAfterDeal"));
				map.put("recName", json.getString("recName"));
			}	
		}
	}

	/**
	 * 手术记录
	 * @param map
	 * @param id 实体ID值
	 * @throws Exception
	 */
	private void surgeryRecord(Map<String, String> map,Long id)throws Exception{
		if(id !=null && id >0){
			JSONObject json = dao.SurgeryRecord_print(id);
			if(json != null){
				getSinglePageTableInfo(map,Long.parseLong(json.getString("caseId")));
				map.put("name", json.getString("name"));
				map.put("gender", json.getString("gender"));
				map.put("age", json.getString("age"));
				map.put("bedNo", json.getString("bedNo"));
				map.put("operatingRoom", json.getString("operatingRoom"));
				map.put("previousSurgeryDiag", json.getString("previousSurgeryDiag"));
				map.put("afterSurgeryDiag", json.getString("afterSurgeryDiag"));
				map.put("surgeryAdapt", json.getString("surgeryAdapt"));
				map.put("surgeryName", json.getString("surgeryName"));
				map.put("surgeryDocName", json.getString("surgeryDocName"));
				map.put("surgeryAssistant1", json.getString("surgeryAssistant1"));
				map.put("surgeryAssistant2", json.getString("surgeryAssistant2"));
				map.put("surgeryTime", json.getString("surgeryTime"));
				map.put("anesthesiaMode", json.getString("anesthesiaMode"));
				map.put("washNurse", json.getString("washNurse"));
				map.put("aroundNurse", json.getString("aroundNurse"));
				map.put("bleedingVolumn", json.getString("bleedingVolumn"));
				map.put("transBloodVolumn", json.getString("transBloodVolumn"));
				map.put("surgeryProcess", json.getString("surgeryProcess"));
				map.put("surgeryResultSample", json.getString("surgeryResultSample"));
				map.put("sample2Pathology", json.getString("sample2Pathology"));
				map.put("sampleNum", json.getString("sampleNum"));
				map.put("anesthesiaEffect", json.getString("anesthesiaEffect"));
				map.put("surgeryDocSign", json.getString("surgeryDocSign"));
			}
		}
	}

	/**
	 * 死亡记录
	 * @param map
	 * @param id
	 * @throws Exception
	 */
	private void deathRecord(Map<String, String> map,Long id)throws Exception{
		if(id !=null && id >0){
			JSONObject json = dao.DeathRecord_print(id);
			if(json != null){
				getSinglePageTableInfo(map,Long.parseLong(json.getString("caseId")));
				map.put("name", json.getString("name"));
				map.put("gender", json.getString("gender"));
				map.put("age", json.getString("age"));
				map.put("ward", json.getString("ward"));
				map.put("inhspTime", json.getString("inhspTime"));
				map.put("deathTime", json.getString("deathTime"));
				map.put("inHspStatu", json.getString("inHspStatu"));
				map.put("inhspDiag", json.getString("inhspDiag"));
				
				String deathDiag = "";
				String temp = json.getString("deathDiag");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag2");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag3");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag4");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag5");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag6");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag7");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag8");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag9");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag10");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag11");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag12");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag13");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag14");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag15");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag16");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag17");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag18");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag19");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag20");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag21");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag22");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag23");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag24");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag25");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag26");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag27");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag28");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag29");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				temp = json.getString("deathDiag30");
				if (temp != null && temp.length() > 0) {
					deathDiag += "<p>" + temp + "</p>";
				}
				map.put("deathDiag", deathDiag);
				
				
				
				map.put("pathologyDiag", json.getString("pathologyDiag"));
				map.put("caseSummary", json.getString("caseSummary"));
				map.put("treatDocName", json.getString("treatDocName"));
				map.put("inhspDocName", json.getString("inhspDocName"));
				map.put("time", json.getString("time"));
			}
		}
	}
	
	/**
	 * 死亡病历讨论
	 * @param map
	 * @param id
	 * @throws Exception
	 */
	private void deathCaseDiscuss(Map<String, String> map,Long id)throws Exception{
		if(id != null && id > 0){
			JSONObject json = dao.DeathCaseDiscuss_print(id);
			if(json != null){
				getSinglePageTableInfo(map,Long.parseLong(json.getString("caseId")));
				map.put("name", json.getString("name"));
				map.put("gender", json.getString("gender"));
				map.put("age", json.getString("age"));
				map.put("ward", json.getString("ward"));
				map.put("discussTime", json.getString("discussTime"));
				map.put("discussPlace", json.getString("discussPlace"));
				map.put("chairPerson", json.getString("chairPerson"));
				map.put("joinPerson", json.getString("joinPerson"));
				map.put("caseSummary", json.getString("caseSummary"));
				map.put("discussContent", json.getString("discussContent"));
				map.put("deathReason", json.getString("deathReason"));
				map.put("deathDiag", json.getString("deathDiag"));
				map.put("discussResult", json.getString("discussResult"));
				map.put("recName", json.getString("recName"));
				map.put("time", json.getString("time"));
			}
		}
	}
	
	/**
	 * 24小时出入院
	 * @param map
	 * @param id
	 * @throws Exception
	 */
	private void InHspRc24(Map<String, String> map,Long id)throws Exception{
		if(id != null && id > 0){
			JSONObject json = dao.InHspRec24_print(id);
			if(json != null){
				getSinglePageTableInfo(map,Long.parseLong(json.getString("caseId")));
				map.put("PatientName", json.getString("PatientName"));
				map.put("Occupation", json.getString("Occupation"));
				map.put("Gender", json.getString("Gender"));
				map.put("Address", json.getString("Address"));
				map.put("age", json.getString("age"));
				map.put("People", json.getString("People"));
				map.put("outhspTime", json.getString("outhspTime"));
				map.put("inhspTime", json.getString("inhspTime"));
				map.put("Province", json.getString("Province"));
				map.put("narrator", json.getString("narrator"));
				map.put("MarrageStatus", json.getString("MarrageStatus"));
				map.put("reliability", json.getString("reliability"));
				map.put("chiefComplaint", json.getString("chiefComplaint"));
				map.put("inHspStatu", json.getString("inHspStatu"));
				map.put("inHspDiag", json.getString("inHspDiag"));
				map.put("treatProcess", json.getString("treatProcess"));
				map.put("outHspStatu", json.getString("outHspStatu"));
				String outHspDiag = "";
				outHspDiag += json.getString("outHspDiag") != "" ? "<p>" + json.getString("outHspDiag") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag2") != "" ? "<p>" + json.getString("outHspDiag2") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag3") != "" ? "<p>" + json.getString("outHspDiag3") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag4") != "" ? "<p>" + json.getString("outHspDiag4") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag5") != "" ? "<p>" + json.getString("outHspDiag5") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag6") != "" ? "<p>" + json.getString("outHspDiag6") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag7") != "" ? "<p>" + json.getString("outHspDiag7") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag8") != "" ? "<p>" + json.getString("outHspDiag8") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag9") != "" ? "<p>" + json.getString("outHspDiag9") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag10") != "" ? "<p>" + json.getString("outHspDiag10") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag11") != "" ? "<p>" + json.getString("outHspDiag11") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag12") != "" ? "<p>" + json.getString("outHspDiag12") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag13") != "" ? "<p>" + json.getString("outHspDiag13") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag14") != "" ? "<p>" + json.getString("outHspDiag14") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag15") != "" ? "<p>" + json.getString("outHspDiag15") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag16") != "" ? "<p>" + json.getString("outHspDiag16") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag17") != "" ? "<p>" + json.getString("outHspDiag17") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag18") != "" ? "<p>" + json.getString("outHspDiag18") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag19") != "" ? "<p>" + json.getString("outHspDiag19") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag20") != "" ? "<p>" + json.getString("outHspDiag20") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag21") != "" ? "<p>" + json.getString("outHspDiag21") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag22") != "" ? "<p>" + json.getString("outHspDiag22") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag23") != "" ? "<p>" + json.getString("outHspDiag23") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag24") != "" ? "<p>" + json.getString("outHspDiag24") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag25") != "" ? "<p>" + json.getString("outHspDiag25") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag26") != "" ? "<p>" + json.getString("outHspDiag26") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag27") != "" ? "<p>" + json.getString("outHspDiag27") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag28") != "" ? "<p>" + json.getString("outHspDiag28") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag29") != "" ? "<p>" + json.getString("outHspDiag29") + "</p>" : "";
				outHspDiag += json.getString("outHspDiag30") != "" ? "<p>" + json.getString("outHspDiag30") + "</p>" : "";
				map.put("outHspDiag", outHspDiag);
				map.put("outHspOrder", json.getString("outHspOrder"));
				map.put("recName", json.getString("recName"));
				map.put("time", json.getString("time"));
			}
		}
	}
	
	/**
	 * 获取单独页面表头信息
	 * @param map
	 * @param kid
	 * @return
	 * @throws Exception
	 */
	private void getSinglePageTableInfo(Map<String, String> map,Long kid)throws Exception{
		if(kid != null && kid > 0){
			CasePrintService service = new CasePrintService();
			String info = service.getPageTableInfo(kid);
			JSONObject json = new JSONObject();
			json = JSONObject.fromObject(info);
			if(json!=null){
				map.put("PatientName", json.getString("patientName"));
				map.put("PatientNo", json.getString("patientNo"));
				map.put("InHspTimes", json.getString("inHspTimes"));
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		SinglePagePrint print = new SinglePagePrint();
		Map<String , String> map = print.getPrintData(1, 717L);
		System.out.println(map.toString());
 	}
	
}
