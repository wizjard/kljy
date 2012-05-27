package com.juncsoft.KLJY.InHospitalCase.CheckReport.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.CheckReport.dao.CheckReportDao;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportInfo;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportInfoForAdd;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportList;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportListForAdd;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportFormforRemote;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.TestItem;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.impl.CheckReportImpl;
import com.juncsoft.KLJY.checkreport.entity.Pacs;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class CheckReportAction extends DispatchAction{

	private CheckReportDao dao = new CheckReportImpl();
	public ActionForward getCheckReportList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		List<CheckReportList> list = new ArrayList<CheckReportList>();
		
		try {			
			String receiveDate = request.getParameter("receiveDate");
			String sectionNo = request.getParameter("sectionNo");
			String testTypeNo = request.getParameter("testTypeNo");
			String sampleNo = request.getParameter("sampleNo");
			String parItemNo = request.getParameter("parItemNo");
			
			list = dao.getCheckReportList(receiveDate, sectionNo, testTypeNo, sampleNo, parItemNo);
			out.println(JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward getCheckReportListLu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		List<CheckReportList> list = new ArrayList<CheckReportList>();
		
		try {			
			String receiveDate = request.getParameter("receiveDate");
			String sectionNo = request.getParameter("sectionNo");
			String testTypeNo = request.getParameter("testTypeNo");
			String sampleNo = request.getParameter("sampleNo");
			String parItemNo = request.getParameter("parItemNo");
			
			list = dao.getCheckReportListLu(receiveDate, sectionNo, testTypeNo, sampleNo, parItemNo);
			out.println(JSONArray.fromObject(list).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward getCheckReportInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		CheckReportInfo ci = new CheckReportInfo();
		try {
			String patientId = request.getParameter("patientId");
			String receiveDate = request.getParameter("receiveDate");
			String sectionNo = request.getParameter("sectionNo");
			String testTypeNo = request.getParameter("testTypeNo");
			String sampleNo = request.getParameter("sampleNo");
			ci = dao.getCheckReportInfo(patientId, receiveDate, sectionNo, testTypeNo, sampleNo);
			if(ci.getIsFromOutHospital() != null && !"".equals(ci.getIsFromOutHospital())){
				ci.setSampleTypeNo(ci.getSampleTypeNo());
			}else{
				ci.setSampleTypeNo(dao.getSampleTypeName(Integer.parseInt(ci.getSampleTypeNo())));
			}
//			System.out.println(dao.getDepartmentByPatId(Integer.parseInt(patientId)));
			ci.setDeptNo(dao.getDepartmentByPatId(Integer.parseInt(patientId)));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(ci, JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward getCheckReportListForAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		List<CheckReportList> list = new ArrayList<CheckReportList>();		
		try {
			String combinationCName = request.getParameter("childNode");
			List<TestItem> testItemList = dao.getSubCName(combinationCName);
			for(TestItem testItem : testItemList){
				CheckReportList checkReportList = new CheckReportList();
				checkReportList.setEsname(testItem.getShortName());		
				checkReportList.setProjectname(testItem.getCname());					
				checkReportList.setUnit(testItem.getUnit());
				checkReportList.setRefvalue(testItem.getRefRange());
				list.add(checkReportList);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(JSONArray.fromObject(list).toString());
			out.close();
		}
		return null;
	}

	
	public ActionForward getCheckReportInfoForAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Patient patient = new Patient();
		try {
			String patientId = request.getParameter("patientId");
			patient = dao.getCheckReportInfoForAdd(patientId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(patient,JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
			out.close();
		}
		return null;
	}

	public ActionForward saveCheckReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		String checkReportInfo = request.getParameter("checkReportInfo");
		String reportList = request.getParameter("reportList");
		int sectionNo = (int) (Math.random() * 100000);
		CheckReportInfoForAdd cri = (CheckReportInfoForAdd) JSONObject.toBean(JSONObject.fromObject(checkReportInfo), CheckReportInfoForAdd.class);
		cri.setSectionNo( "" + sectionNo + "");
		List<CheckReportListForAdd> list = JSONArray.toList(JSONArray.fromObject(reportList), CheckReportListForAdd.class);
		String str = "操作成功";
		ReportFormforRemote reportFormforRemote = null;
		try {
			reportFormforRemote = dao.saveCheckReport(cri, list);		
		} catch (Exception e) {
			str = "操作失败";
			e.printStackTrace();
		} finally {
			out.println("[{isSuccess:'" + str +"',sectionNo:'" + sectionNo + "',formId:'" + reportFormforRemote.getId() + "',sectionNo:'" + reportFormforRemote.getSectionNo() + "',testTypeNo:'" + reportFormforRemote.getTestTypeNo() + "',sampleNo:'" + reportFormforRemote.getSampleNo() + "'}]");
			out.close();
		}
		return null;
	}
	
	public ActionForward updateCheckReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		String checkReportInfo = request.getParameter("checkReportInfo");
		String reportList = request.getParameter("reportList");
		String receiveDate = request.getParameter("receiveDate");
		int sectionNo = Integer.parseInt(request.getParameter("sectionNo"));
		int testTypeNo = Integer.parseInt(request.getParameter("testTypeNo"));
		String sampleNo = request.getParameter("sampleNo");
		int _sectionNo = (int) (Math.random() * 100000);
		CheckReportInfoForAdd cri = (CheckReportInfoForAdd) JSONObject.toBean(JSONObject.fromObject(checkReportInfo), CheckReportInfoForAdd.class);
		cri.setSectionNo( "" + sectionNo + "");
		List<CheckReportListForAdd> list = JSONArray.toList(JSONArray.fromObject(reportList), CheckReportListForAdd.class);
		String str = "操作成功";
		try {
			dao.updateCheckReportNoDelete(cri, list, receiveDate, sectionNo, testTypeNo, sampleNo);	
		} catch (Exception e) {
			str = "操作失败";
			e.printStackTrace();
		} finally {
			out.println("[{isSuccess:'" + str +"',sectionNo:'" + _sectionNo + "'}]");
			out.close();
		}
		return null;
	}
	
	public ActionForward saveAndTijiaoReportFormForRemote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		String checkReportInfo = request.getParameter("checkReportInfo");
		String reportList = request.getParameter("reportList");
		String receiveDate = request.getParameter("receiveDate");
		int sectionNo = Integer.parseInt(request.getParameter("sectionNo"));
		int testTypeNo = Integer.parseInt(request.getParameter("testTypeNo"));
		String sampleNo = request.getParameter("sampleNo");
		int _sectionNo = (int) (Math.random() * 100000);
		CheckReportInfoForAdd cri = (CheckReportInfoForAdd) JSONObject.toBean(JSONObject.fromObject(checkReportInfo), CheckReportInfoForAdd.class);
		cri.setSectionNo( "" + sectionNo + "");
		List<CheckReportListForAdd> list = JSONArray.toList(JSONArray.fromObject(reportList), CheckReportListForAdd.class);
		String str = "操作成功";
		try {
			dao.saveAndTijiaoReportFormForRemote(cri, list, receiveDate, sectionNo, testTypeNo, sampleNo);	
		} catch (Exception e) {
			str = "操作失败";
			e.printStackTrace();
		} finally {
			out.println("[{isSuccess:'" + str +"',sectionNo:'" + _sectionNo + "'}]");
			out.close();
		}
		return null;
	}
	
	public ActionForward cheXiaoReportFormForRemote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String rfId = request.getParameter("rfId");
			dao.cheXiaoReportFormForRemote(rfId);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward findUpdateRecordList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String itemId = request.getParameter("itemId");
			String data = JSONArray.fromObject(dao.findUpdateRecordList(itemId),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss")).toString();
			out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward shenHeNotPassCheckReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		String str = "操作成功";
		try {
			String checkReportInfo = request.getParameter("checkReportInfo");
			String receiveDate = request.getParameter("receiveDate");
			int sectionNo = Integer.parseInt(request.getParameter("sectionNo"));
			int testTypeNo = Integer.parseInt(request.getParameter("testTypeNo"));
			String sampleNo = request.getParameter("sampleNo");
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			CheckReportInfoForAdd cri = (CheckReportInfoForAdd) JSONObject.toBean(JSONObject.fromObject(checkReportInfo), CheckReportInfoForAdd.class);
			dao.shenHeNotPassCheckReport(cri, receiveDate, sectionNo, testTypeNo, sampleNo,patientId,doctorId);	
		} catch (Exception e) {
			str = "操作失败";
			e.printStackTrace();
		} finally {
			out.println("[{isSuccess:'" + str +"'}]");
			out.close();
		}
		return null;
	}
	
}
