package com.juncsoft.KLJY.InHospitalCase.Eye.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.Eye.dao.EyeDao;
import com.juncsoft.KLJY.InHospitalCase.Eye.entity.Eye;
import com.juncsoft.KLJY.InHospitalCase.Eye.impl.EyeImpl;
import com.juncsoft.KLJY.util.Response;

public class EyeAction extends DispatchAction {

	private EyeDao dao = new EyeImpl();
	public ActionForward eye_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(dao.eye_findByCaseID(id)).toString());
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
	
	public ActionForward eye_SaveorUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			Eye eye = (Eye) JSONObject.toBean(json, Eye.class);
			eye = dao.eye_SaveorUpdate(eye);
			res.setData(JSONObject.fromObject(eye).toString());
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
