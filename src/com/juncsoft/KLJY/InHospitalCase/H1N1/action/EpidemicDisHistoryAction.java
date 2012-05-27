package com.juncsoft.KLJY.InHospitalCase.H1N1.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.H1N1.dao.EpidemicDisHistoryDao;
import com.juncsoft.KLJY.InHospitalCase.H1N1.entity.H1N1EpidemicDisHistory;
import com.juncsoft.KLJY.InHospitalCase.H1N1.impl.EpidemicDisHistoryImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class EpidemicDisHistoryAction extends DispatchAction {
	EpidemicDisHistoryDao dao = new EpidemicDisHistoryImpl();
	
	public ActionForward findEpidemicDisHistoryByCaseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long caseId = Long.parseLong(request.getParameter("caseId"));
			res.setData(JSONObject.fromObject(dao.findEpidemicDisHistoryByCaseId(caseId),JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
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
	
	public ActionForward saveOrUpdateEpidemicDisHistoryByCaseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			H1N1EpidemicDisHistory hedh = (H1N1EpidemicDisHistory) JSONObject.toBean(JSONObject.fromObject(data), H1N1EpidemicDisHistory.class);
			res.setData(JSONObject.fromObject(dao.saveOrUpdateEpidemicDisHistoryByCaseId(hedh),JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
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
