package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.ApacheiiDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Apacheii;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl.ApacheiiImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

/**
 * 危重病人综合评价
 * @author Administrator
 */
public class ApacheiiAction extends DispatchAction {

	ApacheiiDao dao = new ApacheiiImpl();

	/**
	 * 保存或修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward ApacheSaveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response re = new Response();
		JSONObject json = JSONObject.fromObject(request.getParameter("data"));
		Apacheii ap = (Apacheii) JSONObject.toBean(json, Apacheii.class);
		try {
			if (ap != null) {
				re.setData(JSONObject.fromObject(
						dao.apaceSaveOrUpdate(ap),
						JSONValueProcessor.cycleExcludel(new String[] { "0" },
								"yyyy-MM-dd")).toString());
				re.setSuccess(true);
			}
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

	/**
	 * 查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findApacheById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response re = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id")) > 0 ? Long
					.parseLong(request.getParameter("id")) : -1L;
			if (id > 0) {
				re.setData(JSONObject.fromObject(
						dao.findApacheById(id),
						JSONValueProcessor.cycleExcludel(new String[] { "a" },
								"yyyy-MM-dd")).toString());
				re.setSuccess(true);
			}
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

	/**
	 * 删除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleApacheById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response re = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id")) > 0 ? Long.parseLong(request.getParameter("id")) : -1L;
			if (id > 0) {
				boolean struts = dao.delApacheById(id);
				re.setSuccess(struts);
				re.setMsg("删除成功！");
			}
		} catch (Exception e) {
			re.setMsg("删除失败！");
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}
}
