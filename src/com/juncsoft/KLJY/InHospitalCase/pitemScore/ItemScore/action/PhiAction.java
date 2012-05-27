package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.PhiDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Phi;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl.PhiImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

/**
 * 急诊中心急重病人综合评价(内)
 * 
 * @author Administrator
 * 
 */
public class PhiAction extends DispatchAction {

	PhiDao dao = new PhiImpl();

	public ActionForward getPhiData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Long id = Long.parseLong(request.getParameter("id")) > 0 ? Long
				.parseLong(request.getParameter("id")) : -1L;
		try {
			if (id > 0) {
				out.println(JSONObject.fromObject(
						dao.getPhiData(id),
						JSONValueProcessor.cycleExcludel(new String[] { "p" },
								"yyyy-MM-dd")).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward saveOrUpdataPhi(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		JSONObject json = JSONObject.fromObject(request.getParameter("data"));
		Phi phi = (Phi) JSONObject.toBean(json, Phi.class);
		try {
			if (phi != null) {
				out.println(JSONObject
						.fromObject(
								dao.saveOrUpdataPhi(phi),
								JSONValueProcessor.cycleExcludel(
										new String[] { "opeDate" },
										"yyyy-MM-dd HH:mm")).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward delPhi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response re = new Response();
		Long id = Long.parseLong(request.getParameter("id")) > 0 ? Long
				.parseLong(request.getParameter("id")) : -1L;
		try {
			if (id > 0) {
				dao.delPhi(id);
				re.setSuccess(true);
			}
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.print(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

}
