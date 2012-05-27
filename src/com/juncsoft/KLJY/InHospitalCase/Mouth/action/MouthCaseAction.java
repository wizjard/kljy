package com.juncsoft.KLJY.InHospitalCase.Mouth.action;

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
import com.juncsoft.KLJY.InHospitalCase.Mouth.dao.MouthCaseDao;
import com.juncsoft.KLJY.InHospitalCase.Mouth.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.InHospitalCase.Mouth.entity.PresentIllnessHistory_OnSet;
import com.juncsoft.KLJY.InHospitalCase.Mouth.entity.PresentIllnessHistory_This;
import com.juncsoft.KLJY.InHospitalCase.Mouth.entity.PresentIllnessHistory_Treat;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class MouthCaseAction extends DispatchAction {
	private MouthCaseDao dao = (MouthCaseDao) DaoFactory
			.InstanceImplement(MouthCaseDao.class);

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
}
