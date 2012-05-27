package com.juncsoft.KLJY.InHospitalCase.pitemScore.fever.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.fever.service.PatientService;
import com.juncsoft.KLJY.util.Response;

public class FeverAction extends DispatchAction {
	private PatientService ps = new PatientService();

	/**
	 * 获取病人信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPatient(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response re = new Response();
		try {
			String id = request.getParameter("kid");
			JSONObject json = ps.findByID(Integer.parseInt(id));
			if(json != null){
				re.setData(json.toString());
				re.setSuccess(true);
			}
		} catch (Exception e) {
			re.setSuccess(false);
			re.setMsg("查询失败！");
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}
}
