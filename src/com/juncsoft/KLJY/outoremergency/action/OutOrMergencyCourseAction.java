package com.juncsoft.KLJY.outoremergency.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DailyCourseRec;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class OutOrMergencyCourseAction extends DispatchAction {

//	private OutOrMergencyCourseDao dao = new OutOrMergencyCourseDaoImpl();
	
//	public ActionForward save(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		PrintWriter out = null;
//		Response res = new Response();
//		try {
//			out = response.getWriter();
//			String data = request.getParameter("data");
//			JSONArray array = JSONArray.fromObject(data);
//			dao.saveOrUpdateOutOrMergencyCourse(array);
//			res.setSuccess(true);
//		} catch (Exception e) {
//			res.setSuccess(false);
//			e.printStackTrace();
//		} finally {
//			out.println(JSONObject.fromObject(res).toString());
//			out.close();
//		}
//		return null;
//	}
	
//	public ActionForward queryAll(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		PrintWriter out = null;
//		try {
//			out = response.getWriter();
//			Long id = Long.parseLong(request.getParameter("id"));
//			String data = JSONArray.fromObject(dao.queryAll(id),
//					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
//					.toString();
//			out.println(data);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			out.close();
//		}
//		return null;
//	}
	
	
	
	
	
	
	
	
	
//	public ActionForward findOutOrMergencyCourseAndOutpatientGenerator(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		PrintWriter out = null;
//		try {
//			out = response.getWriter();
//			Long id = Long.parseLong(request.getParameter("id"));
//			String data = JSONArray.fromObject(dao.findOutOrMergencyCourseAndOutpatientGenerator(id),
//					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
//					.toString();
//			out.println(data);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			out.close();
//		}
//		return null;
//	}
//	
//	public ActionForward checkOutOrMergencyCourseExist(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		PrintWriter out = response.getWriter();
//		Response res = new Response();
//		try {
//			Long caseId = Long.parseLong(request.getParameter("caseId"));
//			Long patientId = Long.parseLong(request.getParameter("patientId"));
//			String outRegno = request.getParameter("outRegno");
//			res.setData(JSONObject.fromObject(dao.checkOutOrMergencyCourseExist(patientId, caseId,outRegno),JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm")).toString());
//			res.setSuccess(true);
//		} catch (Exception e) {
//			res.setSuccess(false);
//			e.printStackTrace();
//			Logger.getRootLogger().error(e.getMessage());
//		} finally {
//			out.println(JSONObject.fromObject(res).toString());
//			out.close();
//		}
//		return null;
//	}
}
