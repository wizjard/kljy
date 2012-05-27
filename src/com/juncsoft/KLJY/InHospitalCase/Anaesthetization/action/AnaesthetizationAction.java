package com.juncsoft.KLJY.InHospitalCase.Anaesthetization.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.Anaesthetization.dao.AnaesthetizationDao;
import com.juncsoft.KLJY.InHospitalCase.Anaesthetization.entity.AnaesthetizationRec;
import com.juncsoft.KLJY.InHospitalCase.Anaesthetization.impl.AnaesthetizationImpl;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DailyCourseRec;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class AnaesthetizationAction extends DispatchAction{

	AnaesthetizationDao dao = new AnaesthetizationImpl();
	public ActionForward anaesthetizationRec_findAllByCaseID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			List<AnaesthetizationRec> list = dao.anaesthetizationRec_findAllByCaseID(id);
			out.println(JSONArray.fromObject(list,
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward anaesthetizationRec_treeNodes(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			out.println(dao.anaesthetizationRec_treeNodes(id).toString());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward anaesthetizationRec_Save(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			JSONArray array = JSONArray.fromObject(data);
			for (int i = 0, len = array.size(); i < len; i++) {
				JSONObject json = array.getJSONObject(i);
				AnaesthetizationRec rec = (AnaesthetizationRec) JSONObject.toBean(json,
						AnaesthetizationRec.class);
				dao.anaesthetizationRec_saveOrUpdate(rec);
			}
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward anaesthetizationRec_Delete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			AnaesthetizationRec rec = new AnaesthetizationRec();
			rec.setId(id);
			dao.anaesthetizationRec_Delete(rec);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward anaesthetizationRec_submit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long submiter = Long.parseLong(request.getParameter("submiter"));
			AnaesthetizationRec rec = dao.anaesthetizationRec_findById(id);
			rec.setSubmiter(submiter);
			rec.setVerifyStatus(1);
			rec
					.setSubmitTime(new Date(Calendar.getInstance()
							.getTimeInMillis()));
			rec = dao.anaesthetizationRec_saveOrUpdate(rec);
			res.setData(JSONObject.fromObject(rec,
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward anaesthetizationRec_resubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long submiter = Long.parseLong(request.getParameter("submiter"));
			AnaesthetizationRec rec = dao.anaesthetizationRec_findById(id);
			rec.setSubmiter(submiter);
			rec.setVerifyStatus(1);
			rec
					.setSubmitTime(new Date(Calendar.getInstance()
							.getTimeInMillis()));
			rec.setChecker(null);
			rec.setCheckTime(null);
			rec = dao.anaesthetizationRec_saveOrUpdate(rec);
			res.setData(JSONObject.fromObject(rec,
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward anaesthetizationRec_cancelSubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			AnaesthetizationRec rec = dao.anaesthetizationRec_findById(id);
			rec.setSubmiter(null);
			rec.setVerifyStatus(0);
			rec.setSubmitTime(null);
			rec = dao.anaesthetizationRec_saveOrUpdate(rec);
			res.setData(JSONObject.fromObject(rec,
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward anaesthetizationRec_check(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long checker = Long.parseLong(request.getParameter("checker"));
			int verifyStatus = Integer.parseInt(request
					.getParameter("verifyStatus"));
			AnaesthetizationRec rec = dao.anaesthetizationRec_findById(id);
			rec.setVerifyStatus(verifyStatus);
			rec.setChecker(checker);
			rec
					.setCheckTime(new Date(Calendar.getInstance()
							.getTimeInMillis()));
			rec = dao.anaesthetizationRec_saveOrUpdate(rec);
			res.setData(JSONObject.fromObject(rec,
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward anaesthetizationRec_cancelCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			AnaesthetizationRec rec = dao.anaesthetizationRec_findById(id);
			rec.setVerifyStatus(1);
			rec.setCheckTime(null);
			rec.setChecker(null);
			rec = dao.anaesthetizationRec_saveOrUpdate(rec);
			res.setData(JSONObject.fromObject(rec,
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
//	public ActionForward anaesthetizationRec_NewpageNum_find(ActionMapping mapping,
//			ActionForm from, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		PrintWriter out = response.getWriter();
//		Response res = new Response();
//		try {
//			Long id = Long.parseLong(request.getParameter("id"));
//			String rst = dao.anaesthetizationRec_NewpageNum_find(id);
//			if (rst == null)
//				rst = "";
//			res.setData(rst);
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
//
//	public ActionForward anaesthetizationRec_NewpageNum_saveOrUpdate(
//			ActionMapping mapping, ActionForm from, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		PrintWriter out = response.getWriter();
//		Response res = new Response();
//		try {
//			Long id = Long.parseLong(request.getParameter("id"));
//			String npCfg = request.getParameter("npCfg");
//			dao.anaesthetizationRec_NewpageNum_saveOrUpdate(id, npCfg);
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
