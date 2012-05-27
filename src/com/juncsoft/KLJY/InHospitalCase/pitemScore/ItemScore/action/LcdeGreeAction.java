package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.LcdeGreeDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Lcdegree;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl.LcdeGreeImapl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

/**
 * 肝癌进展程度评估
 * 
 * @author Administrator
 * 
 */
public class LcdeGreeAction extends DispatchAction {

	LcdeGreeDao dao = new LcdeGreeImapl();

	/**
	 * 保存或修改
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward lcSaveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response re = new Response();
		JSONObject json = JSONObject.fromObject(request.getParameter("data"));
		Lcdegree lc = (Lcdegree) JSONObject.toBean(json, Lcdegree.class);
		try {
			if (lc != null) {
				re.setData(JSONObject.fromObject(
						dao.lcSaveOrUpdate(lc),
						JSONValueProcessor.cycleExcludel(new String[] { "op" },
								"yyyy-MM-dd")).toString());
				re.setMsg("保存成功！");
				re.setSuccess(true);
			}
		} catch (Exception e) {
			re.setMsg("保存失败！");
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
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findLcById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response re = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id")) > 0 ? Long
					.parseLong(request.getParameter("id")) : -1L;
			if (id > 0) {
				re.setData(JSONObject.fromObject(
						dao.findLcdeById(id),
						JSONValueProcessor.cycleExcludel(new String[] { "op" },
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
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleLcById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response re = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id")) > 0 ? Long
					.parseLong(request.getParameter("id")) : -1L;
			if (id > 0) {
				boolean struts = dao.deleLcById(id);
				re.setSuccess(struts);
				re.setMsg("删除成功！");
			}
		} catch (Exception e) {
			re.setSuccess(false);
			re.setMsg("删除失败！");
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}
}
