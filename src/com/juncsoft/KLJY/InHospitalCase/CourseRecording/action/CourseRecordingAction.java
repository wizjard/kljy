package com.juncsoft.KLJY.InHospitalCase.CourseRecording.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.juncsoft.KLJY.InHospitalCase.CasePrint.CasePrintService;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.dao.CourseRecordingDao;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.Consultation;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DailyCourseRec;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DeathCaseDiscuss;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DeathRecord;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.InHspDied24;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.InHspRec24;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.PreviousCaseDiscuss;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.PreviousSurgerySummary;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.SurgeryRecord;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.WardRoundRec;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabExamination;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl.ScoreCommentPrintImpl;
import com.juncsoft.KLJY.user.dao.UserDao;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.user.impl.UserDaoImpl;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class CourseRecordingAction extends DispatchAction {

	private CourseRecordingDao dao = (CourseRecordingDao) DaoFactory
			.InstanceImplement(CourseRecordingDao.class);
	private CasePrintService service = new CasePrintService();// �����ӡ

	private UserDao userDao = new UserDaoImpl();// get userInformation

	public ActionForward findDailyCourseRecById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			res.setData(JSONObject.fromObject(dao.findDailyCourseRecById(id),
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

	public ActionForward DailyCourseRec_findAllByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			List<DailyCourseRec> list = dao.DailyCourseRec_findAllByCaseID(id);
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
				DailyCourseRec rec = (DailyCourseRec) JSONObject.toBean(json,
						DailyCourseRec.class);
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
			DailyCourseRec rec = new DailyCourseRec();
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
			DailyCourseRec rec = dao.DailyCourseRec_findById(id);
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
			DailyCourseRec rec = dao.DailyCourseRec_findById(id);
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
			DailyCourseRec rec = dao.DailyCourseRec_findById(id);
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
			DailyCourseRec rec = dao.DailyCourseRec_findById(id);
			rec.setVerifyStatus(verifyStatus);
			rec.setChecker(checker);
			rec
					.setCheckTime(new Date(Calendar.getInstance()
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
			DailyCourseRec rec = dao.DailyCourseRec_findById(id);
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

	public ActionForward Consultation_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			Consultation c = (Consultation) JSONObject.toBean(JSONObject
					.fromObject(data), Consultation.class);
			c = dao.Consultation_saveOrUpdate(c);
			res
					.setData(JSONObject.fromObject(c,
							JSONValueProcessor.formatDate("yyyy年MM月dd日HH时"))
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

	public ActionForward Consultation_findById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Consultation c = dao.Consultation_findById(id);
			
			JSONObject object = JSONObject.fromObject(c, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (c.getTime() != null) {
				object
						.put("time", df.format(c
								.getTime()));
			}
			if (c.getApptime() != null) {
				object.put("apptime", df.format(c.getApptime()));
			}
			if (c.getRectime() != null) {
				object.put("rectime", df.format(c.getRectime()));
			}
			res.setData(object.toString());
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

	public ActionForward Consultation_print(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(dao.Consultation_print(id).toString());
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

	public ActionForward PreviousSurgerySummary_saveOrUpdate(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			PreviousSurgerySummary p = (PreviousSurgerySummary) JSONObject
					.toBean(JSONObject.fromObject(data),
							PreviousSurgerySummary.class);
			p = dao.PreviousSurgerySummary_saveOrUpdate(p);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			if (p.getInhspTime() != null) {
				object.put("inhspTime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p
								.getInhspTime()));
			}
			res.setData(object.toString());
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

	public ActionForward PreviousSurgerySummary_findById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			PreviousSurgerySummary p = dao.PreviousSurgerySummary_findById(id);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			if (p.getInhspTime() != null) {
				object.put("inhspTime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p
								.getInhspTime()));
			}
			if (p.getTime() != null) {
				object.put("time",
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p
								.getTime()));
			}
			if (p.getSurgeryTime() != null) {
				object.put("surgeryTime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p
								.getSurgeryTime()));
			}
			res.setData(object.toString());
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

	public ActionForward PreviousSurgerySummary_print(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(dao.PreviousSurgerySummary_print(id).toString());
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

	public ActionForward PreviousCaseDiscuss_saveOrUpdate(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			PreviousCaseDiscuss p = (PreviousCaseDiscuss) JSONObject.toBean(
					JSONObject.fromObject(data), PreviousCaseDiscuss.class);
			p = dao.PreviousCaseDiscuss_saveOrUpdate(p);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			if (p.getInhspTime() != null) {
				object.put("inhspTime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p
								.getInhspTime()));
			}
			res.setData(object.toString());
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

	public ActionForward PreviousCaseDiscuss_findById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			PreviousCaseDiscuss p = dao.PreviousCaseDiscuss_findById(id);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			if (p.getInhspTime() != null) {
				object.put("inhspTime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p
								.getInhspTime()));
			}
			if (p.getTime() != null) {
				object.put("time",
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p
								.getTime()));
			}
			if (p.getDiscussTime() != null) {
				object.put("discussTime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p
								.getDiscussTime()));
			}
			res.setData(object.toString());
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

	public ActionForward PreviousCaseDiscuss_print(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(dao.PreviousCaseDiscuss_print(id).toString());
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

	public ActionForward SurgeryRecord_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			SurgeryRecord p = (SurgeryRecord) JSONObject.toBean(JSONObject
					.fromObject(data), SurgeryRecord.class);
			p = dao.SurgeryRecord_saveOrUpdate(p);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (p.getSurgeryFromTime() != null) {
				object
						.put("surgeryFromTime", df.format(p
								.getSurgeryFromTime()));
			}
			if (p.getSurgeryToTime() != null) {
				object.put("surgeryToTime", df.format(p.getSurgeryToTime()));
			}
			res.setData(object.toString());
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

	public ActionForward SurgeryRecord_findById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			SurgeryRecord p = dao.SurgeryRecord_findById(id);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (p.getSurgeryFromTime() != null) {
				object
						.put("surgeryFromTime", df.format(p
								.getSurgeryFromTime()));
			}
			if (p.getSurgeryToTime() != null) {
				object.put("surgeryToTime", df.format(p.getSurgeryToTime()));
			}
			if (p.getTime() != null) {
				object.put("time", df.format(p.getTime()));
			}
			res.setData(object.toString());
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

	public ActionForward SurgeryRecord_print(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(dao.SurgeryRecord_print(id).toString());
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

	public ActionForward DeathRecord_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			DeathRecord p = (DeathRecord) JSONObject.toBean(JSONObject
					.fromObject(data), DeathRecord.class);
			p = dao.DeathRecord_saveOrUpdate(p);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (p.getInhspTime() != null) {
				object.put("inhspTime", df.format(p.getInhspTime()));
			}
			if (p.getDeathTime() != null) {
				object.put("deathTime", df.format(p.getDeathTime()));
			}
			res.setData(object.toString());
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

	public ActionForward DeathRecord_findById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			DeathRecord p = dao.DeathRecord_findById(id);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (p.getInhspTime() != null) {
				object.put("inhspTime", df.format(p.getInhspTime()));
			}
			if (p.getDeathTime() != null) {
				object.put("deathTime", df.format(p.getDeathTime()));
			}
			if (p.getTime() != null) {
				object.put("time", df.format(p.getTime()));
			}
			res.setData(object.toString());
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

	/**
	 * 查询死亡记录入院情况
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward DeathRecord_getInHspStatuByCaseId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long kid = Long.parseLong(request.getParameter("kid")) > 0 ? Long
				.parseLong(request.getParameter("kid")) : -1L;
		Response re = new Response();
		PrintWriter out = response.getWriter();
		try {
			if (kid > 0) {
				String cont = dao.findInHspStatuByCaseId(kid);
				re.setData(cont);
				re.setSuccess(true);
			}
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

	public ActionForward DeathRecord_print(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(dao.DeathRecord_print(id).toString());
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

	public ActionForward DeathCaseDiscuss_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			DeathCaseDiscuss p = (DeathCaseDiscuss) JSONObject.toBean(
					JSONObject.fromObject(data), DeathCaseDiscuss.class);
			p = dao.DeathCaseDiscuss_saveOrUpdate(p);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			res.setData(object.toString());
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

	public ActionForward DeathCaseDiscuss_findById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			DeathCaseDiscuss p = dao.DeathCaseDiscuss_findById(id);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy年MM月dd日HH时"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (p.getTime() != null) {
				object.put("time", df.format(p.getTime()));
			}
			if (p.getDiscussTime() != null) {
				object.put("discussTime", df.format(p.getDiscussTime()));
			}
			res.setData(object.toString());
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

	public ActionForward DeathCaseDiscuss_print(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(dao.DeathCaseDiscuss_print(id).toString());
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

	// begin:24小时入院死亡记录维护
	public ActionForward InHspDied24_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			InHspDied24 p = (InHspDied24) JSONObject.toBean(JSONObject
					.fromObject(data), InHspDied24.class);
			p = dao.InHspDied24_saveOrUpdate(p);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy-MM-dd HH:mm"));
			if (p.getTime() != null) {
				object.put("time", new SimpleDateFormat("yyyy年MM月dd日HH时")
						.format(p.getTime()));
			}
			res.setData(object.toString());
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

	public ActionForward InHspDied24_findById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			InHspDied24 p = dao.InHspDied24_findById(id);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy-MM-dd HH:mm"));
			res.setData(object.toString());
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

	public ActionForward InHspDied24_Print(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long id = Long.parseLong(request.getParameter("id"));
		JSONObject json = dao.InHspDied24_print(id);
		response.setContentType("application/pdf");
		String filePath = request.getRealPath("/");
		Document doc = null;
		try {
			doc = new Document(PageSize.A4, 20 * dl, 10 * dl, 5 * dl, 5 * dl);

			// 设置字体
			BaseFont bfEngLish = BaseFont.createFont(filePath
					+ "PUBLIC/print/SIMKAI.TTF", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			BaseFont bfChinese = BaseFont.createFont(filePath
					+ "PUBLIC/print/SIMSUN.TTC,0", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			Font fontChinese; // 大标题
			Font fontEngLish;// 英文标题
			Font smallFont;// 小标题
			Font fontGeneral;// 一般内容
			Font fontBig;// 大标题
			Font fontEveryRecordTitle;// 每次记录标题比一般内容大一号字
			Image logo;
			PdfWriter writer = PdfWriter.getInstance(doc, response
					.getOutputStream());
			logo = Image.getInstance(filePath + "PUBLIC/print/youanLogo.jpg");
			fontChinese = new Font(bfChinese, 16, Font.BOLD); // 大标题
			fontEngLish = new Font(bfEngLish, 9, Font.BOLD);// 英文标题
			smallFont = new Font(bfChinese, 9, Font.NORMAL);// 小标题
			fontBig = new Font(bfChinese, 15, Font.BOLD);// 大标题
			fontEveryRecordTitle = new Font(bfChinese, 13, Font.BOLD);// 每次记录标题比一般内容大一号字
			fontGeneral = new Font(bfChinese, 12, Font.NORMAL);// 一般内容

			PageNumHelper event = new PageNumHelper(bfChinese);
			writer.setPageEvent(event);

			// 设置奇偶页页边距镜像
			doc.setMarginMirroring(true);
			// 打开document
			doc.open();

			PdfPTable table = new PdfPTable(1);
			// 表格跨页显示
			table.setSplitLate(false);
			table.setSplitRows(true);
			// 设置表格宽度100%
			table.setWidthPercentage(100);
			// 设置表格自动拉伸最后一行填满页面
			table.setExtendLastRow(true);
			PdfPCell cell;
			// 页眉
			cell = new PdfPCell();
			cell.setPadding(0);
			cell.setBorderWidth(0);
			cell.setBorderWidthBottom(0.5f);
			cell.addElement(getHeader24(json, logo, fontChinese,
					fontEngLish, smallFont, fontBig));
			table.addCell(cell);
			// 页脚
			cell = new PdfPCell();
			cell.setPadding(0);
			cell.setBorderWidth(0);
			cell.setBorderWidthTop(0.5f);
			cell.addElement(new Paragraph(" "));
			table.addCell(cell);
			table.setHeaderRows(2);
			table.setFooterRows(1);
			// 内容
			cell = new PdfPCell();
			cell.setPadding(0);
			cell.setBorderWidth(0);

			PdfPTable tableContent = new PdfPTable(5);
			tableContent.setSplitLate(false);
			tableContent.setSplitRows(true);
			// 设置表格宽度100%
			tableContent.setWidthPercentage(100);
			tableContent.setWidths(new int[]{16,30,12,21,21});
			PdfPCell cellC = new PdfPCell();
			Paragraph p = null;
			this.blankOne(cellC, p, fontGeneral, tableContent);
			p = new Paragraph("姓名:"+json.getString("patientName"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("patientName"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("职业:"+json.getString("Occupation"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(3);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("Occupation"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			this.blankOne(cellC, p, fontGeneral, tableContent);
			cellC = new PdfPCell();
			p = new Paragraph("性别:"+json.getString("Gender"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("Gender"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("住址:"+json.getString("Address"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(3);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("Address"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			this.blankOne(cellC, p, fontGeneral, tableContent);
			cellC = new PdfPCell();
			p = new Paragraph("年龄:"+json.getString("age"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("age"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("入院日期:"+json.getString("inhspTime"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(3);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("inhspTime"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			this.blankOne(cellC, p, fontGeneral, tableContent);
			cellC = new PdfPCell();
			p = new Paragraph("民族:"+json.getString("People"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("People"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("死亡时间:"+json.getString("outhspTime"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(3);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("outhspTime"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			this.blankOne(cellC, p, fontGeneral, tableContent);
			cellC = new PdfPCell();
			p = new Paragraph("籍贯:"+json.getString("Province"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("Province"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("叙述者:"+json.getString("narrator"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(3);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("narrator"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			
			this.blankOne(cellC, p, fontGeneral, tableContent);
			cellC = new PdfPCell();
			p = new Paragraph("婚姻:"+json.getString("MarrageStatus"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("MarrageStatus"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("可靠性:"+json.getString("reliability"),fontGeneral);
			p.setAlignment(Element.ALIGN_LEFT);
			cellC.setColspan(3);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("reliability"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("主    诉:",fontGeneral);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			cellC.setColspan(4);
			p = new Paragraph(json.getString("chiefComplaint"),fontGeneral);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("入院情况:",fontGeneral);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			cellC.setColspan(4);
			p = new Paragraph(json.getString("inHspStatu"),fontGeneral);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("入院诊断:",fontGeneral);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			cellC.setColspan(4);
			p = new Paragraph(json.getString("inHspDiag"),fontGeneral);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("诊疗经过:",fontGeneral);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			cellC.setColspan(4);
			p = new Paragraph(json.getString("treatProcess"),fontGeneral);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("死亡原因:",fontGeneral);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			cellC.setColspan(4);
			p = new Paragraph(json.getString("diedReason"),fontGeneral);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("死亡诊断:",fontGeneral);
			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			cellC.setColspan(4);
			p = new Paragraph(json.getString("diedOrder"),fontGeneral);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			//2--30级诊断结果
			for(int i=2;i<=30;i++){
				if(!"".equals(json.getString("diedOrder"+i))){
					cellC = new PdfPCell();
					p = new Paragraph("",fontGeneral);
					cellC.addElement(p);
					tableContent.addCell(cellC);
					
					cellC = new PdfPCell();
					cellC.setColspan(4);
					p = new Paragraph(json.getString("diedOrder"+i),fontGeneral);
					cellC.addElement(p);
					tableContent.addCell(cellC);
				}
			}
			
			cellC = new PdfPCell();
			cellC.setColspan(3);
			p = new Paragraph("",fontGeneral);
			p.setAlignment(Element.ALIGN_CENTER);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			cellC.setColspan(2);
			p = new Paragraph("住院医师:"+json.getString("inhspDoctor"),fontGeneral);
			
			p.setIndentationLeft(20);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(,fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			cellC.setColspan(3);
			p = new Paragraph("",fontGeneral);
			p.setAlignment(Element.ALIGN_CENTER);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			p = new Paragraph("主治医师:"+json.getString("mainDoctor"),fontGeneral);
			cellC.setColspan(2);
			p.setIndentationLeft(20);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(json.getString("inhspDoctor"),fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			cellC.setColspan(3);
			p = new Paragraph("",fontGeneral);
			p.setAlignment(Element.ALIGN_CENTER);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
			cellC = new PdfPCell();
			cellC.setColspan(2);
			p = new Paragraph("记录日期:"+json.getString("time"),fontGeneral);
			p.setIndentationLeft(20);
//			p.setAlignment(Element.ALIGN_RIGHT);
			cellC.addElement(p);
			tableContent.addCell(cellC);
			
//			cellC = new PdfPCell();
//			p = new Paragraph(,fontGeneral);
//			cellC.addElement(p);
//			tableContent.addCell(cellC);
			
			for (PdfPRow row : tableContent.getRows()) {
				for (PdfPCell c : row.getCells()) {
					if (c != null)
						c.setBorder(Rectangle.NO_BORDER);
				}
			}
			cell.addElement(tableContent);
			table.addCell(cell);
			doc.add(table);
		} catch (Exception ioe) {
			ioe.printStackTrace();
		} finally {
			doc.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
		return null;
	}

	private PdfPTable getHeader24(JSONObject titleParame, Image logo,
			Font fontChinese, Font fontEngLish, Font smallFont, Font fontBig)
			throws DocumentException {
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 8, 23, 38, 10, 21 });
		// 图片
		PdfPCell cell = new PdfPCell();
		cell.setPadding(1);
		cell.setPaddingLeft(2);
		cell.setRowspan(2);
		cell.addElement(logo);
		table.addCell(cell);
		// 中英文医院名称
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setColspan(4);
		cell.setPaddingTop(12);
		Paragraph p = new Paragraph("首都医科大学附属北京佑安医院", fontChinese);
		cell.addElement(p);
		p = new Paragraph("Beijing YouAn Hospital,Capital Medical University",
				fontEngLish);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 标题
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setPaddingTop(5);
		cell.setPaddingBottom(5);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("24小时入院死亡记录", fontBig);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 姓名
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		p = new Paragraph("姓  名：" + titleParame.getString("patientName"),
				smallFont);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 住院次数
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setPaddingBottom(3);
		cell.setPaddingTop(3);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		p = new Paragraph("第" + titleParame.getString("inHspTimes") + "次住院",
				smallFont);
		cell.addElement(p);
		table.addCell(cell);
		// 页码
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("", smallFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 病案号
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("病案号：" + titleParame.getString("patientNo"),
				smallFont);
		cell.addElement(p);
		table.addCell(cell);
		for (PdfPRow row : table.getRows()) {
			for (PdfPCell c : row.getCells()) {
				if (c != null)
					c.setBorder(Rectangle.NO_BORDER);
			}
		}
		return table;
	}
	// Over!
	public ActionForward InHspRec24_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			InHspRec24 p = (InHspRec24) JSONObject.toBean(JSONObject
					.fromObject(data), InHspRec24.class);
			p = dao.InHspRec24_saveOrUpdate(p);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy-MM-dd HH:mm"));
			if (p.getTime() != null) {
				object.put("time", new SimpleDateFormat("yyyy年MM月dd日HH时")
						.format(p.getTime()));
			}
			res.setData(object.toString());
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

	public ActionForward InHspRec24_findById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			InHspRec24 p = dao.InHspRec24_findById(id);
			JSONObject object = JSONObject.fromObject(p, JSONValueProcessor
					.formatDate("yyyy-MM-dd HH:mm"));
			res.setData(object.toString());
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

	public ActionForward InHspRec24_print(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(dao.InHspRec24_print(id).toString());
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

	public ActionForward getChiefComByCaseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response rs = new Response();
		try {
			Long caseId = Long.parseLong(request.getParameter("caseId")) > 0 ? Long
					.parseLong(request.getParameter("caseId"))
					: -1L;
			if (caseId > 0) {
				rs.setData(dao.getChiefComByCaseId(caseId).toString());
				rs.setSuccess(true);
			}
		} catch (Exception e) {
			rs.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.print(JSONObject.fromObject(rs).toString());
			out.close();
		}
		return null;
	}

	public ActionForward DailyCourseRec_NewPrint(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Boolean isContinued = Boolean.parseBoolean(request
					.getParameter("continued"));
			Long caseId = Long.parseLong(request.getParameter("caseId"));
			String continuedNum = request.getParameter("continuedNum");
			res.setData(dao.DailyCourseRec_NewPrint(caseId, isContinued,
					continuedNum).toString());
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

	public ActionForward wardRound_findAllByID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			List<WardRoundRec> list = dao.wardRound_findAllByID(id);
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

	public ActionForward wardRound_treeNodes(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			out.println(dao.wardRound_treeNodes(id).toString());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward wardRound_Delete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			WardRoundRec rec = new WardRoundRec();
			rec.setId(id);
			dao.wardRound_Delete(rec);
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

	public ActionForward wardRound_Save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			JSONArray array = JSONArray.fromObject(data);
			List<Long> id = new ArrayList<Long>();
			for (int i = 0, len = array.size(); i < len; i++) {
				JSONObject json = array.getJSONObject(i);
				WardRoundRec rec = (WardRoundRec) JSONObject.toBean(json,
						WardRoundRec.class);
				id.add(dao.wardRound_saveOrUpdate(rec).getId());
			}
			res.setData(JSONArray.fromObject(id).toString());
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

	public ActionForward wardRoundRec_submit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long submiter = Long.parseLong(request.getParameter("submiter"));
			WardRoundRec rec = dao.wardRound_findById(id);
			rec.setSubmiter(submiter);
			rec.setVerifyStatus(1);
			rec
					.setSubmitTime(new Date(Calendar.getInstance()
							.getTimeInMillis()));
			rec = dao.wardRound_saveOrUpdate(rec);
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

	public ActionForward wardRound_cancelSubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			WardRoundRec rec = dao.wardRound_findById(id);
			rec.setSubmiter(null);
			rec.setVerifyStatus(0);
			rec.setSubmitTime(null);
			rec = dao.wardRound_saveOrUpdate(rec);
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

	public ActionForward wardRound_resubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long submiter = Long.parseLong(request.getParameter("submiter"));
			WardRoundRec rec = dao.wardRound_findById(id);
			rec.setSubmiter(submiter);
			rec.setVerifyStatus(1);
			rec
					.setSubmitTime(new Date(Calendar.getInstance()
							.getTimeInMillis()));
			rec.setChecker(null);
			rec.setCheckTime(null);
			rec = dao.wardRound_saveOrUpdate(rec);
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

	public ActionForward wardRound_check(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Long checker = Long.parseLong(request.getParameter("checker"));
			int verifyStatus = Integer.parseInt(request
					.getParameter("verifyStatus"));
			WardRoundRec rec = dao.wardRound_findById(id);
			rec.setVerifyStatus(verifyStatus);
			rec.setChecker(checker);
			rec
					.setCheckTime(new Date(Calendar.getInstance()
							.getTimeInMillis()));
			rec = dao.wardRound_saveOrUpdate(rec);
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

	public ActionForward wardRound_cancelCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			WardRoundRec rec = dao.wardRound_findById(id);
			rec.setVerifyStatus(1);
			rec.setCheckTime(null);
			rec.setChecker(null);
			rec = dao.wardRound_saveOrUpdate(rec);
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

	private float dl = 72 / 25.4f;// 1mm的Pdf文档长度
	
	public ActionForward createHTMLToPDF(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		int order = 0;// 当前打印条目
		String filePath = request.getRealPath("/");
		String nextPrintNumString = request.getParameter("nextPrintNum");
		String xuDaNumList[] = { "0", "0" };
		if (null != nextPrintNumString) {
			if (nextPrintNumString.trim().indexOf("-") > -1) {
				xuDaNumList = nextPrintNumString.split("-");
			} else {
				xuDaNumList[0] = nextPrintNumString;
				xuDaNumList[1] = nextPrintNumString;
			}
		}
		Long id = Long.parseLong(request.getParameter("id"));
		List<DailyCourseRec> list = dao.DailyCourseRec_findAllByCaseID(id);
		if (list != null && list.size() > 0) {
			JSONObject titleParame = JSONObject.fromObject(service
					.getPageTableInfo(id));
			Document doc = null;
			try {
				doc = new Document(PageSize.A4, 20 * dl, 10 * dl, 5 * dl,
						5 * dl);

				// 设置字体
				BaseFont bfEngLish = BaseFont.createFont(filePath
						+ "PUBLIC/print/SIMKAI.TTF", BaseFont.IDENTITY_H,
						BaseFont.NOT_EMBEDDED);
				BaseFont bfChinese = BaseFont.createFont(filePath
						+ "PUBLIC/print/SIMSUN.TTC,0", BaseFont.IDENTITY_H,
						BaseFont.NOT_EMBEDDED);
				Font fontChinese; // 大标题
				Font fontEngLish;// 英文标题
				Font smallFont;// 小标题
				Font fontGeneral;// 一般内容
				Font fontBig;// 大标题
				Font fontEveryRecordTitle;// 每次记录标题比一般内容大一号字
				Image logo;
				PdfWriter writer = PdfWriter.getInstance(doc, response.getOutputStream());
				if ("0".equals(xuDaNumList[0])) {
					logo = Image.getInstance(filePath
							+ "PUBLIC/print/youanLogo.jpg");
					fontChinese = new Font(bfChinese, 16, Font.BOLD); // 大标题
					fontEngLish = new Font(bfEngLish, 9, Font.BOLD);// 英文标题
					smallFont = new Font(bfChinese, 9, Font.NORMAL);// 小标题
					fontBig = new Font(bfChinese, 18, Font.BOLD);// 大标题
					fontEveryRecordTitle = new Font(bfChinese, 13, Font.BOLD);// 每次记录标题比一般内容大一号字
					fontGeneral = new Font(bfChinese, 12, Font.NORMAL);// 一般内容
					
					PageNumHelper event = new PageNumHelper(bfChinese);
					writer.setPageEvent(event);
				} else {
					logo = Image.getInstance(filePath
							+ "PUBLIC/print/white.jpg");
					fontChinese = new Font(bfChinese, 16, Font.BOLD,
							BaseColor.WHITE); // 大标题
					fontEngLish = new Font(bfEngLish, 9, Font.BOLD,
							BaseColor.WHITE);// 英文标题
					smallFont = new Font(bfChinese, 9, Font.NORMAL,
							BaseColor.WHITE);// 小标题
					fontBig = new Font(bfChinese, 18, Font.BOLD,
							BaseColor.WHITE);// 大标题
					fontEveryRecordTitle = new Font(bfChinese, 13, Font.BOLD,
							BaseColor.WHITE);// 每次记录标题比一般内容大一号字
					fontGeneral = new Font(bfChinese, 12, Font.NORMAL,
							BaseColor.WHITE);// 一般内容
					
				}
				// 设置奇偶页页边距镜像
				doc.setMarginMirroring(true);
				// 打开document
				doc.open();
				List<List<DailyCourseRec>> newList = new ArrayList<List<DailyCourseRec>>();
				List<DailyCourseRec> everyOne = new ArrayList<DailyCourseRec>();
				for (int i = 0, size = list.size(); i < size; i++) {
					DailyCourseRec dc = list.get(i);
					if ("术后首次病程记录".equals(dc.getTitle().trim())) {
						newList.add(everyOne);
						everyOne = new ArrayList<DailyCourseRec>();
					}
					everyOne.add(dc);
				}
				newList.add(everyOne);
				for (int j = 0, sizej = newList.size(); j < sizej; j++) {
					List<DailyCourseRec> result = newList.get(j);
					
					PdfPTable table = new PdfPTable(1);
					// 表格跨页显示
					table.setSplitLate(false);
					table.setSplitRows(true);
					// 设置表格宽度100%
					table.setWidthPercentage(100);
					// 设置表格自动拉伸最后一行填满页面
					table.setExtendLastRow(true);
					PdfPCell cell;
					// 页眉
					cell = new PdfPCell();
					cell.setPadding(0);
					cell.setBorderWidth(0);
					if("0".equals(xuDaNumList[0])){
						cell.setBorderWidthBottom(0.5f);
					}
					cell.addElement(getHeader(titleParame, logo, fontChinese,
							fontEngLish, smallFont, fontBig));
					table.addCell(cell);
					// 页脚
					cell = new PdfPCell();
					cell.setPadding(0);
					cell.setBorderWidth(0);
					if("0".equals(xuDaNumList[0])){
						cell.setBorderWidthTop(0.5f);
					}
					cell.addElement(new Paragraph(" "));
					table.addCell(cell);
					table.setHeaderRows(2);
					table.setFooterRows(1);
					// 内容
					cell = new PdfPCell();
					cell.setPadding(0);
					cell.setBorderWidth(0);
					for (int i = 0, size = result.size(); i < size; i++) {
						DailyCourseRec dc = result.get(i);
						order++;
					    if (!"0".equals(xuDaNumList[0]) && (Integer.parseInt(xuDaNumList[0]) > order
								|| Integer.parseInt(xuDaNumList[1]) < order)) {
					    	fontEveryRecordTitle = new Font(bfChinese, 13, Font.BOLD,
									BaseColor.WHITE);// 每次记录标题比一般内容大一号字
							fontGeneral = new Font(bfChinese, 12, Font.NORMAL,
									BaseColor.WHITE);// 一般内容
								
							cell.addElement(getRecordTable(dc, doc,
									fontGeneral, fontEveryRecordTitle));
						} else {
							fontEveryRecordTitle = new Font(bfChinese, 13,
									Font.BOLD);// 每次记录标题比一般内容大一号字
							fontGeneral = new Font(bfChinese, 12, Font.NORMAL);// 一般内容
						 	cell.addElement(getRecordTable(dc, doc,
									fontGeneral, fontEveryRecordTitle));
						}
					}
					table.addCell(cell);
					doc.add(table);
					if(j != newList.size() - 1){
						doc.newPage();
					}
				}
			} catch (Exception ioe) {
				ioe.printStackTrace();
			} finally
			{
				doc.close();
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}
		return null;
	}

	private PdfPTable getHeader(JSONObject titleParame, Image logo,
			Font fontChinese, Font fontEngLish, Font smallFont, Font fontBig)
			throws DocumentException {
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 8, 23, 38, 10, 21 });
		// 图片
		PdfPCell cell = new PdfPCell();
		cell.setPadding(1);
		cell.setPaddingLeft(2);
		cell.setRowspan(2);
		cell.addElement(logo);
		table.addCell(cell);
		// 中英文医院名称
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setColspan(4);
		cell.setPaddingTop(12);
		Paragraph p = new Paragraph("首都医科大学附属北京佑安医院", fontChinese);
		cell.addElement(p);
		p = new Paragraph("Beijing YouAn Hospital,Capital Medical University",
				fontEngLish);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 标题
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setPaddingTop(5);
		cell.setPaddingBottom(5);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("病 程 记 录", fontBig);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 姓名
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		p = new Paragraph("姓  名：" + titleParame.getString("patientName"),
				smallFont);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 住院次数
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setPaddingBottom(3);
		cell.setPaddingTop(3);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
		p = new Paragraph("第" + titleParame.getString("inHspTimes") + "次住院",
				smallFont);
		cell.addElement(p);
		table.addCell(cell);
		// 页码
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("", smallFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		table.addCell("");
		// 病案号
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph("病案号：" + titleParame.getString("patientNo"),
				smallFont);
		cell.addElement(p);
		table.addCell(cell);
		for (PdfPRow row : table.getRows()) {
			for (PdfPCell c : row.getCells()) {
				if (c != null)
					c.setBorder(Rectangle.NO_BORDER);
			}
		}
		return table;
	}

	private PdfPTable getRecordTable(DailyCourseRec r, Document doc,
			Font fontGeneral, Font fontEveryRecordTitle) throws Exception {
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		table.setSplitLate(false);
		table.setSplitRows(true);
		PdfPCell cell;
		// 时间
		cell = new PdfPCell();
		cell.setPaddingLeft(7);
		cell.setPaddingTop(8);
		cell.setPaddingBottom(5);
		cell.setBorderWidth(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Paragraph p = new Paragraph(sdf.format(r.getTime()), fontGeneral);
		cell.addElement(p);
		table.addCell(cell);
		// 标题
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setPaddingTop(8);
		cell.setPaddingBottom(5);
		cell.setBorderWidth(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		p = new Paragraph(r.getTitle(), fontEveryRecordTitle);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		table.addCell(cell);
		// 补白
		cell = new PdfPCell();
		cell.setPadding(0);
		cell.setBorderWidth(0);
		cell.addElement(new Paragraph(" "));
		table.addCell(cell);
		// 内容
		cell = new PdfPCell();
		cell.setColspan(3);
		cell.setPaddingLeft(7);
		cell.setPaddingRight(7);
		cell.setPaddingTop(8);
		cell.setBorderWidth(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		for (String str : r.getContent().split("\n")) {
			p = new Paragraph(str, fontGeneral);
			p.setLeading(20);
			cell.addElement(p);
		}
		table.addCell(cell);
		// 签名
		cell = new PdfPCell();
		cell.setColspan(3);
		cell.setPaddingLeft(15);
		 cell.setPaddingTop(8);
		cell.setBorderWidth(0);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		StringBuilder sb = new StringBuilder();
		if (r.getChecker() != null && r.getChecker() > 0) {
			User userChecker = userDao.findUserById(r.getChecker());
			if (userChecker != null) {
				sb.append(userChecker.getName() + "            ");
			}
		}
		if (r.getSubmiter() != null && r.getSubmiter() > 0) {
			User userSubmiter = userDao.findUserById(r.getSubmiter());
			if (userSubmiter != null) {
				sb.append(userSubmiter.getName());
			}
		}
		Paragraph doctor = new Paragraph("医生签字：" + sb.toString(), fontGeneral);
		doctor.setAlignment(Element.ALIGN_RIGHT);
		doctor.setIndentationRight(100);

		cell.addElement(doctor);

		p = new Paragraph(
				"---------------------------------------------------------------------------------",
				fontGeneral);
		cell.addElement(p);
		table.addCell(cell);
		return table;
	}

	class PageNumHelper extends PdfPageEventHelper {
		private BaseFont fontGeneral;

		public PageNumHelper(BaseFont fontGeneral) {
			this.fontGeneral = fontGeneral;
		}

		@Override
		public void onStartPage(PdfWriter writer, Document document) {
				PdfContentByte cb = writer.getDirectContent();
				cb.saveState();
				cb.setFontAndSize(fontGeneral, 9);
				cb.beginText();
				cb.showTextAligned(Element.ALIGN_CENTER, "第 "
						+ writer.getPageNumber() + " 页", document.getPageSize()
						.getWidth() / 2,
						document.getPageSize().getHeight() - 100, 0);
				cb.endText();
				cb.restoreState();
				cb = writer.getDirectContent();
				cb.setLineWidth(1);
				if (document.getPageNumber() % 2 == 0) {
					cb.moveTo(document.getPageSize().getWidth() - 20 * dl,
							5 * dl);
					cb.lineTo(document.getPageSize().getWidth() - 20 * dl,
							document.getPageSize().getHeight() - 10 * dl);
				} else {
					cb.moveTo(20 * dl, 5 * dl);
					cb.lineTo(20 * dl, document.getPageSize().getHeight() - 10
							* dl);
				}
				cb.stroke();
		}

	}
	
	public ActionForward DailyCourseRec_SaveRate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			JSONArray array = JSONArray.fromObject(data);
			res.setData(JSONArray.fromObject(dao.DailyCourseRec_saveOrUpdateRate(array),
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
	
	private void blankOne(PdfPCell cellC, Paragraph p, Font fontGeneral,
			PdfPTable tableContent) {
		cellC = new PdfPCell();
		p = new Paragraph("", fontGeneral);
		p.setAlignment(Element.ALIGN_LEFT);
		cellC.addElement(p);
		tableContent.addCell(cellC);
	}
	
	
	public ActionForward checkSubmitCourseRecording(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long kid = Long.parseLong(request.getParameter("kid"));
			Long pid = Long.parseLong(request.getParameter("pid"));
			res.setSuccess(dao.checkSubmitCourseRecording(kid, pid));
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
	
}
