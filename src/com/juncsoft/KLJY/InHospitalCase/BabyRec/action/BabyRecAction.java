package com.juncsoft.KLJY.InHospitalCase.BabyRec.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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

import com.juncsoft.KLJY.InHospitalCase.BabyRec.dao.BabyRecDao;
import com.juncsoft.KLJY.InHospitalCase.BabyRec.entity.BabyCourseRec;
import com.juncsoft.KLJY.InHospitalCase.BabyRec.entity.BabyRec;
import com.juncsoft.KLJY.InHospitalCase.BabyRec.impl.BabyRecImpl;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.Consultation;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DailyCourseRec;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DeathCaseDiscuss;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DeathRecord;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.InHspRec24;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.PreviousCaseDiscuss;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.PreviousSurgerySummary;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.SurgeryRecord;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabExamination;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class BabyRecAction extends DispatchAction {

	private BabyRecDao dao = new BabyRecImpl();

	public ActionForward getBabyInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();

		try {
			int pid = Integer.parseInt(request.getParameter("pid"));
			int pcid = Integer.parseInt(request.getParameter("pcid"));
			// System.out.println("pid:" + pid + " " + "pcid:" + pcid);
			List<BabyRec> list = dao.getBabyInfo(pid, pcid);
			if (list != null && list.size() > 0) {
				String data = JSONArray.fromObject(list.iterator().next(),
						JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
						.toString();
				out.println(data);
			} else {
				out.println("[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward saveBabyInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		BabyRec babyRec = new BabyRec();
		String data = "{type:'保存成功！'}";
		try {
			int hasSave = Integer.parseInt(request.getParameter("hasSave"));
			babyRec = (BabyRec) JSONObject.toBean(JSONObject
					.fromObject(request.getParameter("babyInfo")),
					BabyRec.class);
			dao.saveBabyInfo(babyRec, hasSave);
		} catch (Exception e) {
			data = "{tye:'保存失败！'}";
			e.printStackTrace();
		} finally {
			out.println(data);
			out.close();
		}
		return null;
	}

	public ActionForward getBabyCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			int pid = Integer.parseInt(request.getParameter("pid"));
			int pcid = Integer.parseInt(request.getParameter("pcid"));
			long count = dao.getBabyCount(pid, pcid);
			out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}
	
	public ActionForward DailyCourseRec_findAllByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			List<BabyCourseRec> list = dao.DailyCourseRec_findAllByCaseID(id);
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

	public ActionForward DailyCourseRec_Save(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			JSONArray array = JSONArray.fromObject(data);
			for (int i = 0, len = array.size(); i < len; i++) {
				JSONObject json = array.getJSONObject(i);
				BabyCourseRec rec = (BabyCourseRec) JSONObject.toBean(json,
						BabyCourseRec.class);
				dao.DailyCourseRec_saveOrUpdate(rec);
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

	public ActionForward DailyCourseRec_Delete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			BabyCourseRec rec = new BabyCourseRec();
			rec.setId(id);
			dao.DailyCourseRec_delete(rec);
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

	public ActionForward DailyCourseRec_submit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long submiter = Long.parseLong(request.getParameter("submiter"));
			BabyCourseRec rec = dao.DailyCourseRec_findById(id);
			rec.setSubmiter(submiter);
			rec.setVerifyStatus(1);
			rec
					.setSubmitTime(new Date(Calendar.getInstance()
							.getTimeInMillis()));
			rec = dao.DailyCourseRec_saveOrUpdate(rec);
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

	public ActionForward DailyCourseRec_resubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long submiter = Long.parseLong(request.getParameter("submiter"));
			BabyCourseRec rec = dao.DailyCourseRec_findById(id);
			rec.setSubmiter(submiter);
			rec.setVerifyStatus(1);
			rec
					.setSubmitTime(new Date(Calendar.getInstance()
							.getTimeInMillis()));
			rec.setChecker(null);
			rec.setCheckTime(null);
			rec = dao.DailyCourseRec_saveOrUpdate(rec);
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

	public ActionForward DailyCourseRec_cancelSubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			BabyCourseRec rec = dao.DailyCourseRec_findById(id);
			rec.setSubmiter(null);
			rec.setVerifyStatus(0);
			rec.setSubmitTime(null);
			rec = dao.DailyCourseRec_saveOrUpdate(rec);
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

	public ActionForward DailyCourseRec_check(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long checker = Long.parseLong(request.getParameter("checker"));
			int verifyStatus = Integer.parseInt(request
					.getParameter("verifyStatus"));
			BabyCourseRec rec = dao.DailyCourseRec_findById(id);
			rec.setVerifyStatus(verifyStatus);
			rec.setChecker(checker);
			rec.setCheckTime(new Date(Calendar.getInstance()
							.getTimeInMillis()));
			rec = dao.DailyCourseRec_saveOrUpdate(rec);
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

	public ActionForward DailyCourseRec_cancelCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			BabyCourseRec rec = dao.DailyCourseRec_findById(id);
			rec.setVerifyStatus(1);
			rec.setCheckTime(null);
			rec.setChecker(null);
			rec = dao.DailyCourseRec_saveOrUpdate(rec);
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

	public ActionForward DailyCourseRec_treeNodes(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			out.println(dao.DailyCourseRec_treeNodes(id).toString());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward DailyCourseRec_Lab_saveOrUpdate(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			JSONObject data = JSONObject.fromObject(request
					.getParameter("data"));
			LabExamination lab = (LabExamination) JSONObject.toBean(data,
					LabExamination.class);
			dao.DailyCourseRec_Lab_saveOrUpdate(id, lab);
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

	public ActionForward DailyCourseRec_Lab_findByCourseId(
			ActionMapping mapping, ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			LabExamination lab = dao.DailyCourseRec_Lab_findByCourseId(id);
			res.setData(JSONObject.fromObject(lab,
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
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

	public ActionForward DailyCourseRec_NewpageNum_find(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			String rst = dao.DailyCourseRec_NewpageNum_find(id);
			if (rst == null)
				rst = "";
			res.setData(rst);
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

	public ActionForward DailyCourseRec_NewpageNum_saveOrUpdate(
			ActionMapping mapping, ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			String npCfg = request.getParameter("npCfg");
			dao.DailyCourseRec_NewpageNum_saveOrUpdate(id, npCfg);
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


	public ActionForward public_patientInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(dao.public_patientInfo(id).toString());
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

	public ActionForward public_mainMenu(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long kid = Long.parseLong(request.getParameter("kid"));
			String entityName = request.getParameter("entityName");
			res.setData(dao.public_mainMenu(entityName, kid).toString());
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

	public ActionForward public_verify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long submiter = Long.parseLong(request.getParameter("submiter"));
			Long checker = Long.parseLong(request.getParameter("checker"));
			int verifyStatus = Integer.parseInt(request
					.getParameter("verifyStatus"));
			String entity = request.getParameter("entity");
			if (submiter == -1)
				submiter = null;
			if (checker == -1)
				checker = null;
			res.setData(dao.public_verify(id, submiter, checker, verifyStatus,
					entity).toString());
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
	
//	public ActionForward checkSubmitCourseRecording(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		PrintWriter out = response.getWriter();
//		Response res = new Response();
//		try {
//			Long kid = Long.parseLong(request.getParameter("kid"));
//			Long pid = Long.parseLong(request.getParameter("pid"));
//			res.setSuccess(dao.checkSubmitCourseRecording(kid, pid));
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
