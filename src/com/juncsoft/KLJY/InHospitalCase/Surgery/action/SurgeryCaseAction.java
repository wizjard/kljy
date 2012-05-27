package com.juncsoft.KLJY.InHospitalCase.Surgery.action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.juncsoft.KLJY.InHospitalCase.Surgery.entity.SpecialExamination;
import com.juncsoft.KLJY.InHospitalCase.Surgery.dao.SurgeryCaseDao;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.Response;

public class SurgeryCaseAction extends DispatchAction {
	private SurgeryCaseDao dao = (SurgeryCaseDao) DaoFactory
			.InstanceImplement(SurgeryCaseDao.class);

	public ActionForward se_findByCaseID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(dao.se_findByCaseId(id))
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

	public ActionForward se_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			SpecialExamination se = (SpecialExamination) JSONObject.toBean(
					json, SpecialExamination.class);
			se = dao.se_saveOrUpdate(se);
			res.setData(JSONObject.fromObject(se).toString());
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
