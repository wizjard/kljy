package com.juncsoft.KLJY.InHospitalCase.Liver.action;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.juncsoft.KLJY.InHospitalCase.Liver.dao.LiverCaseDao;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.ChiefComplaint;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.ChiefComplaintSysptom;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.Diagnosis;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.EpidemicDisHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.FamilyHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabDiagnosticExamination;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MarritalChildbearingHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MenstrualHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.OutHspRec;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.OutHspRecYiZhu;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_Infect;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_NoInfect;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_OuterHurt;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory_Surgery;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PersonalHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PhysicalExamination;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PresentIllnessHistory_OnSet;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PresentIllnessHistory_This;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PresentIllnessHistory_Treat;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.RevisedDiagnosis;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class LiverCaseAction extends DispatchAction {
	private LiverCaseDao dao = (LiverCaseDao) DaoFactory
			.InstanceImplement(LiverCaseDao.class);

	public ActionForward ChiefComplaint_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(
					dao.ChiefComplaint_findByCaseID(id),
					JSONValueProcessor.cycleExcludel(new String[] { "cc" },
							"yyyy-MM-dd HH:mm")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward ChiefComplaint_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			ChiefComplaint cc = (ChiefComplaint) JSONObject.toBean(json,
					ChiefComplaint.class);
			JSONArray array = json.getJSONArray("sysptoms");
			List<ChiefComplaintSysptom> sysptoms = new ArrayList<ChiefComplaintSysptom>();
			for (int i = 0, len = array.size(); i < len; i++) {
				ChiefComplaintSysptom ccs = (ChiefComplaintSysptom) JSONObject
						.toBean(array.getJSONObject(i),
								ChiefComplaintSysptom.class);
				ccs.setCc(cc);
				sysptoms.add(ccs);
			}
			cc.setSysptoms(sysptoms);
			cc = dao.ChiefComplaint_saveOrUpdate(cc);
			res.setData(JSONObject.fromObject(
					cc,
					JSONValueProcessor.cycleExcludel(new String[] { "cc" },
							"yyyy-MM-dd HH:mm")).toString());
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

	public ActionForward ChiefComplaint_deleteSysptom(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			dao.ChiefComplaint_deleteSysptom(Long.parseLong(request
					.getParameter("id")));
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

	public ActionForward EpidemicDis_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(dao.EpidemicDis_findByCaseID(id))
					.toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward EpidemicDis_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			EpidemicDisHistory edh = (EpidemicDisHistory) JSONObject.toBean(
					json, EpidemicDisHistory.class);
			edh = dao.EpidemicDis_saveOrUpdate(edh);
			res.setData(JSONObject.fromObject(edh).toString());
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

	public ActionForward PresentIllnessHistory_findByCaseID(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject
					.fromObject(
							dao.PresentIllnessHistory_findByCaseID(id),
							JSONValueProcessor.cycleExcludel(
									new String[] { "pi" }, "")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward PresentIllnessHistory_saveOrUpdate(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			PresentIllnessHistory ph = (PresentIllnessHistory) JSONObject
					.toBean(json, PresentIllnessHistory.class);

			JSONArray array = json.getJSONArray("onsets");
			List<PresentIllnessHistory_OnSet> onsets = new ArrayList<PresentIllnessHistory_OnSet>();
			for (int i = 0, len = array.size(); i < len; i++) {
				PresentIllnessHistory_OnSet onset = (PresentIllnessHistory_OnSet) JSONObject
						.toBean(array.getJSONObject(i),
								PresentIllnessHistory_OnSet.class);
				onset.setPi(ph);
				onsets.add(onset);
			}
			ph.setOnsets(onsets);

			array = json.getJSONArray("treats");
			List<PresentIllnessHistory_Treat> treats = new ArrayList<PresentIllnessHistory_Treat>();
			for (int i = 0, len = array.size(); i < len; i++) {
				PresentIllnessHistory_Treat treat = (PresentIllnessHistory_Treat) JSONObject
						.toBean(array.getJSONObject(i),
								PresentIllnessHistory_Treat.class);
				treat.setPi(ph);
				treats.add(treat);
			}
			ph.setTreats(treats);

			PresentIllnessHistory_This illThis = (PresentIllnessHistory_This) JSONObject
					.toBean(json.getJSONObject("illThis"),
							PresentIllnessHistory_This.class);
			illThis.setPi(ph);
			ph.setIllThis(illThis);

			ph = dao.PresentIllnessHistory_saveOrUpdate(ph);
			res.setData(JSONObject
					.fromObject(
							ph,
							JSONValueProcessor.cycleExcludel(
									new String[] { "pi" }, "")).toString());
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

	public ActionForward PresentIllnessHistory_deleteOnSet(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			dao.PresentIllnessHistory_deleteOnSet(Long.parseLong(request
					.getParameter("id")));
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

	public ActionForward PresentIllnessHistory_deleteTreat(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			dao.PresentIllnessHistory_deleteTreat(Long.parseLong(request
					.getParameter("id")));
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

	public ActionForward PastHistory_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject
					.fromObject(
							dao.PastHistory_findByCaseID(id),
							JSONValueProcessor.cycleExcludel(
									new String[] { "ph" }, "")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward PastHistory_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));

			PastHistory ph = (PastHistory) JSONObject.toBean(json,
					PastHistory.class);

			List<PastHistory_Infect> infects = new ArrayList<PastHistory_Infect>();
			JSONArray array = json.getJSONArray("infects");
			for (int i = 0, len = array.size(); i < len; i++) {
				PastHistory_Infect item = (PastHistory_Infect) JSONObject
						.toBean(array.getJSONObject(i),
								PastHistory_Infect.class);
				item.setPh(ph);
				infects.add(item);
			}
			ph.setInfects(infects);

			List<PastHistory_NoInfect> noInfects = new ArrayList<PastHistory_NoInfect>();
			array = json.getJSONArray("noInfects");
			for (int i = 0, len = array.size(); i < len; i++) {
				PastHistory_NoInfect item = (PastHistory_NoInfect) JSONObject
						.toBean(array.getJSONObject(i),
								PastHistory_NoInfect.class);
				item.setPh(ph);
				noInfects.add(item);
			}
			ph.setNoInfects(noInfects);

			List<PastHistory_OuterHurt> outerHurts = new ArrayList<PastHistory_OuterHurt>();
			array = json.getJSONArray("outerHurts");
			for (int i = 0, len = array.size(); i < len; i++) {
				PastHistory_OuterHurt item = (PastHistory_OuterHurt) JSONObject
						.toBean(array.getJSONObject(i),
								PastHistory_OuterHurt.class);
				item.setPh(ph);
				outerHurts.add(item);
			}
			ph.setOuterHurts(outerHurts);

			List<PastHistory_Surgery> surgerys = new ArrayList<PastHistory_Surgery>();
			array = json.getJSONArray("surgerys");
			for (int i = 0, len = array.size(); i < len; i++) {
				PastHistory_Surgery item = (PastHistory_Surgery) JSONObject
						.toBean(array.getJSONObject(i),
								PastHistory_Surgery.class);
				item.setPh(ph);
				surgerys.add(item);
			}
			ph.setSurgerys(surgerys);

			res.setData(JSONObject
					.fromObject(
							dao.PastHistory_saveOrUpdate(ph),
							JSONValueProcessor.cycleExcludel(
									new String[] { "ph" }, "")).toString());
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

	public ActionForward PastHistory_delete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			dao.PastHistory_delete(Long.parseLong(request.getParameter("id")),
					request.getParameter("flag"));
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

	public ActionForward PersonalHistory_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(
					dao.PersonalHistory_findByCaseID(id)).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward PersonalHistory_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			PersonalHistory ph = (PersonalHistory) JSONObject.toBean(json,
					PersonalHistory.class);
			ph = dao.PersonalHistory_saveOrUpdate(ph);
			res.setData(JSONObject.fromObject(ph).toString());
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

	public ActionForward MenstrualHistory_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(
					dao.MenstrualHistory_findByCaseID(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward MenstrualHistory_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			MenstrualHistory mh = (MenstrualHistory) JSONObject.toBean(json,
					MenstrualHistory.class);
			mh = dao.MenstrualHistory_saveOrUpdate(mh);
			res.setData(JSONObject.fromObject(mh,
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

	public ActionForward MarritalChildbearingHistory_findByCaseID(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(
					dao.MarritalChildbearingHistory_findByCaseID(id))
					.toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward MarritalChildbearingHistory_saveOrUpdate(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			MarritalChildbearingHistory mh = (MarritalChildbearingHistory) JSONObject
					.toBean(json, MarritalChildbearingHistory.class);
			mh = dao.MarritalChildbearingHistory_saveOrUpdate(mh);
			res.setData(JSONObject.fromObject(mh).toString());
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

	public ActionForward FamilyHistory_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(
					dao.FamilyHistory_findByCaseID(id)).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward FamilyHistory_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			FamilyHistory fh = (FamilyHistory) JSONObject.toBean(json,
					FamilyHistory.class);
			fh = dao.FamilyHistory_saveOrUpdate(fh);
			res.setData(JSONObject.fromObject(fh).toString());
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

	public ActionForward PhysicalExamination_findByCaseID(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(
					dao.PhysicalExamination_findByCaseID(id)).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward PhysicalExamination_saveOrUpdate(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			PhysicalExamination fe = (PhysicalExamination) JSONObject.toBean(
					json, PhysicalExamination.class);
			fe = dao.PhysicalExamination_saveOrUpdate(fe);
			res.setData(fe.getId().toString());
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

	public ActionForward Diagnosis_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(dao.Diagnosis_findByCaseID(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward Diagnosis_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			Diagnosis diag = (Diagnosis) JSONObject.toBean(json,
					Diagnosis.class);
			diag = dao.Diagnosis_saveOrUpdate(diag);
			res.setData(JSONObject.fromObject(diag,
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

	public ActionForward RevisedDiagnosis_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(
					dao.RevisedDiagnosis_findByCaseID(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward RevisedDiagnosis_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			RevisedDiagnosis diag = (RevisedDiagnosis) JSONObject.toBean(json,
					RevisedDiagnosis.class);
			diag = dao.RevisedDiagnosis_saveOrUpdate(diag);
			res.setData(JSONObject.fromObject(diag,
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

	public ActionForward LabDiagnosticExamination_findByCaseID(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(
					dao.LabDiagnosticExamination_findByCaseID(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward LabDiagnosticExamination_saveOrUpdate(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			LabDiagnosticExamination lab = (LabDiagnosticExamination) JSONObject
					.toBean(json, LabDiagnosticExamination.class);
			lab = dao.LabDiagnosticExamination_saveOrUpdate(lab);
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

	public ActionForward OutHspRec_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			OutHspRec o = dao.OutHspRec_findByCaseID(id);
			if (o == null) {
				o = dao.OutHspRec_nullInit(id);
			}
			res.setData(JSONObject.fromObject(o,
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

	public ActionForward OutHspRec_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			OutHspRec outs = (OutHspRec) JSONObject.toBean(json,
					OutHspRec.class);
			outs = dao.OutHspRec_saveOrUpdate(outs);
			res.setData(JSONObject.fromObject(outs,
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

	public ActionForward OutHspRecYiZhu_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			out.println(JSONArray.fromObject(
					dao.OutHspRecYiZhu_findByCaseID(id)).toString());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward OutHspRecYiZhu_delete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			OutHspRecYiZhu yizhu = new OutHspRecYiZhu();
			yizhu.setId(id);
			dao.OutHspRecYiZhu_delete(yizhu);
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

	public ActionForward OutHspRecYiZhu_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			JSONArray array = JSONArray.fromObject(data);
			for (int i = 0, len = array.size(); i < len; i++) {
				OutHspRecYiZhu yizhu = (OutHspRecYiZhu) JSONObject.toBean(array
						.getJSONObject(i), OutHspRecYiZhu.class);
				dao.OutHspRecYiZhu_saveOrUpdate(yizhu);
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
	
	public ActionForward getTreatResult_Dict(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("node");
		PrintWriter out = response.getWriter();
		out.print(dao.getTreatResult_Dict(id));
		return null;
	}
	
	/**
	 * 入院记录中的签字
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkSubmitCase(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long kid = Long.parseLong(request.getParameter("kid"));
			res.setSuccess(dao.checkSubmitCase(kid));
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
