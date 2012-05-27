package com.juncsoft.KLJY.checkreport.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportInfoForAdd;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportFormforPad;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.checkreport.dao.CheckReportDao;
import com.juncsoft.KLJY.checkreport.entity.Maidi;
import com.juncsoft.KLJY.checkreport.entity.Pacs;
import com.juncsoft.KLJY.checkreport.impl.CheckReportDaoImpl;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class CheckReportAction  extends DispatchAction {
	private CheckReportDao dao = new CheckReportDaoImpl();
	
	public ActionForward getCheckReports(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String patientId = request.getParameter("id");
			if(patientId.equals("")){
				MemberInfo mem = (MemberInfo) request.getSession().getAttribute("MemberInfo");
				Patient p = mem.getPatient();
				patientId = p.getPatientId();
				
			}
			List<Pacs> reports = dao.getPacsReportsByPatient(patientId);
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("totle", reports.size());
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward getMaidiCheckReports(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String patientId = request.getParameter("id");
			if(patientId.equals("")){
				MemberInfo mem = (MemberInfo) request.getSession().getAttribute("MemberInfo");
				Patient p = mem.getPatient();
				patientId = p.getPatientId();
				
			}
			List<Maidi> reports = dao.getMaidiReportsByPatient(patientId);
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("totle", reports.size());
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 直接暂存或者二次暂存
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePacs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String pacsInfo = request.getParameter("pacsInfo");
			Pacs pacs = (Pacs) JSONObject.toBean(JSONObject.fromObject(pacsInfo), Pacs.class);
			if(pacs.getId() == null){
				pacs.setLuRuDate(new Date());
				pacs.setIsFromOutHospital(1);
			}
			dao.savePacs(pacs);	
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
	
	/**
	 * 直接暂存或者二次暂存
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveMaidi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String pacsInfo = request.getParameter("pacsInfo");
			Maidi maidi = (Maidi) JSONObject.toBean(JSONObject.fromObject(pacsInfo), Maidi.class);
			if(maidi.getId() == null){
				maidi.setLuRuDate(new Date());
				maidi.setIsFromOutHospital(1);
			}
			dao.saveMaidi(maidi);	
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
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMember(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String patientId = request.getParameter("patientId");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<Map> reports = dao.getMyGrounpCheckReportsForByMember(patientId,search);
			Long total = (long)reports.size();
			
					
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMemberMaiDi(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String patientId = request.getParameter("patientId");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<Map> reports = dao.getMyGrounpCheckReportsForByMemberMaiDi(patientId,search);
			Long total = (long)reports.size();
			
					
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getPacsByPatientId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String patientId = request.getParameter("patientId");
			Long pacsId = Long.parseLong(request.getParameter("pacsId"));
			Map mp = dao.getPacsByPatientId(pacsId,patientId);
			res.setData(JSONObject.fromObject(mp,
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString());
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
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMaiDiByPatientId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String patientId = request.getParameter("patientId");
			Long pacsId = Long.parseLong(request.getParameter("pacsId"));
			Map mp = dao.getMaiDiByPatientId(pacsId,patientId);
			res.setData(JSONObject.fromObject(mp,
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString());
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
	
	public ActionForward updatePacs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String pacsInfo = request.getParameter("pacsInfo");
			Pacs pacs = (Pacs) JSONObject.toBean(JSONObject.fromObject(pacsInfo), Pacs.class);
			dao.updatePacs(pacs);	
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
	
	public ActionForward savePacsT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String pacsInfo = request.getParameter("pacsInfo");
			Pacs pacs = (Pacs) JSONObject.toBean(JSONObject.fromObject(pacsInfo), Pacs.class);
			if(pacs.getIsPatientOrDoctorWriteZanCun() == 11){
				pacs.setSheHeDate(new Date());
			}else if(pacs.getIsPatientOrDoctorWriteZanCun() == 21){
				pacs.setGuiDangDate(new Date());
			}
			pacs.setLuRuDate(new Date());
			pacs.setIsFromOutHospital(1);
			dao.savePacsT(pacs);	
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
	
	public ActionForward saveMaidiT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String pacsInfo = request.getParameter("pacsInfo");
			Maidi maidi = (Maidi) JSONObject.toBean(JSONObject.fromObject(pacsInfo), Maidi.class);
			if(maidi.getIsPatientOrDoctorWriteZanCun() == 11){
				maidi.setSheHeDate(new Date());
			}else if(maidi.getIsPatientOrDoctorWriteZanCun() == 21){
				maidi.setGuiDangDate(new Date());
			}
			maidi.setLuRuDate(new Date());
			maidi.setIsFromOutHospital(1);
			dao.saveMaidiT(maidi);	
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
	
	public ActionForward updatePacsT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String pacsInfo = request.getParameter("pacsInfo");
			Pacs pacs = (Pacs) JSONObject.toBean(JSONObject.fromObject(pacsInfo), Pacs.class);
			dao.updatePacsT(pacs);	
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
	
	public ActionForward updateMaidiT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String pacsInfo = request.getParameter("pacsInfo");
			Maidi maidi = (Maidi) JSONObject.toBean(JSONObject.fromObject(pacsInfo), Maidi.class);
			dao.updateMaidiT(maidi);	
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
	
	public ActionForward updatePacsTi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String pacsInfo = request.getParameter("pacsInfo");
			Pacs pacs = (Pacs) JSONObject.toBean(JSONObject.fromObject(pacsInfo), Pacs.class);
			dao.updatePacsTi(pacs);	
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
	
	public ActionForward updateMaidiTi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String pacsInfo = request.getParameter("pacsInfo");
			Maidi maidi = (Maidi) JSONObject.toBean(JSONObject.fromObject(pacsInfo), Maidi.class);
			dao.updateMaidiTi(maidi);	
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
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsFor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<Map> reports = dao.getMyGrounpCheckReportsFor(doctorId,search);
			Long total = (long)reports.size();
			
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForMaiDi(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<Map> reports = dao.getMyGrounpCheckReportsForMaiDi(doctorId,search);
			Long total = (long)reports.size();
			
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("total", total);
			out.println(object.toString());		
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
			Long pacsId = Long.parseLong(request.getParameter("pacsId"));
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			dao.shenHeNotPassCheckReport(pacsId,patientId,doctorId);	
		} catch (Exception e) {
			str = "操作失败";
			e.printStackTrace();
		} finally {
			out.println("[{isSuccess:'" + str +"'}]");
			out.close();
		}
		return null;
	}
	
	public ActionForward shenHeNotMaiDiCheckReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		String str = "操作成功";
		try {
			Long pacsId = Long.parseLong(request.getParameter("pacsId"));
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			dao.shenHeNotMaiDiCheckReport(pacsId,patientId,doctorId);	
		} catch (Exception e) {
			str = "操作失败";
			e.printStackTrace();
		} finally {
			out.println("[{isSuccess:'" + str +"'}]");
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMemberGuanLiYuanAll(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String deptCode = request.getParameter("deptCode");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<Map> reports = dao.getMyGrounpCheckReportsForByMemberGuanLiYuanAll(deptCode,search);
			Long total = (long)reports.size();
			
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMemberGuanLiYuanAllMaiDi(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String deptCode = request.getParameter("deptCode");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<Map> reports = dao.getMyGrounpCheckReportsForByMemberGuanLiYuanAllMaiDi(deptCode,search);
			Long total = (long)reports.size();
			
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward cheXiaoPacs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String rfId = request.getParameter("rfId");
			dao.cheXiaoPacs(rfId);
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
	
	public ActionForward cheXiaoMaidi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String rfId = request.getParameter("rfId");
			dao.cheXiaoMaidi(rfId);
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
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMemberDoctor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String patientId = request.getParameter("patientId");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<Map> reports = dao.getMyGrounpCheckReportsForByMemberDoctor(patientId,search);
//			Collections.sort(reports);
			Long total = (long)reports.size();
			
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMemberDoctorMaiDi(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String patientId = request.getParameter("patientId");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<Map> reports = dao.getMyGrounpCheckReportsForByMemberDoctorMaiDi(patientId,search);
//			Collections.sort(reports);
			Long total = (long)reports.size();
			
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMemberGuanLiYuan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String patientId = request.getParameter("patientId");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<Map> reports = dao.getMyGrounpCheckReportsForByMemberGuanLiYuan(patientId,search);
//			Collections.sort(reports);
			Long total = (long)reports.size();
			
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*查询医生或者会员录入的所有的化验单(外院)*/
	public ActionForward getMyGrounpCheckReportsForByMemberGuanLiYuanMaiDi(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();			
		try {
			String patientId = request.getParameter("patientId");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			List<Map> reports = dao.getMyGrounpCheckReportsForByMemberGuanLiYuanMaiDi(patientId,search);
//			Collections.sort(reports);
			Long total = (long)reports.size();
			
			JSONObject object = new JSONObject();
			object.put("root", reports);
			object.put("total", total);
			out.println(object.toString());		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward deletepacs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String pacsId = request.getParameter("pacsId");
			dao.deletepacs(Long.parseLong(pacsId));
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
	
	public ActionForward deleteMaidi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String pacsId = request.getParameter("pacsId");
			dao.deleteMaidi(Long.parseLong(pacsId));
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
}
