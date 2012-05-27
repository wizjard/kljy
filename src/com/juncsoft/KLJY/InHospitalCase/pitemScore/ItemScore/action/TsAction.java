package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao.TsDao;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Ts;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.impl.TsImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

/**
 * 急诊中心急重病人综合评价(外)
 * 
 * @author Administrator
 * 
 */
public class TsAction extends DispatchAction {
	TsDao dao = new TsImpl();

	public ActionForward getTsData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Long id = Long.parseLong(request.getParameter("id")) > 0 ? Long
				.parseLong(request.getParameter("id")) : -1L;
		Response re = new Response();
		try {
			if (id > 0) {
				re.setData(JSONObject.fromObject(
						dao.findTsById(id),
						JSONValueProcessor.cycleExcludel(new String[] { "op" },
								"yyyy-MM-dd")).toString());
				re.setSuccess(true);
			}
		} catch (Exception e) {
			re.setSuccess(false);
			e.printStackTrace();
		} finally {
			// System.out.println("输出前台：" +
			// JSONObject.fromObject(re).toString());
			out.println(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

	public ActionForward tsSaveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		JSONObject json = JSONObject.fromObject(request.getParameter("data"));
		Response re = new Response();
		Ts ts = (Ts) JSONObject.toBean(json, Ts.class);
		try {
			if (ts != null) {
				re.setData(JSONObject.fromObject(
						dao.tsSaveOrUpdate(ts),
						JSONValueProcessor.cycleExcludel(new String[] { "op" },
								"yyyy-MM-dd")).toString());
				re.setSuccess(true);
				// out.println();
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

	public ActionForward deleTsById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response re = new Response();
		Long id = Long.parseLong(request.getParameter("id")) > 0 ? Long
				.parseLong(request.getParameter("id")) : -1L;
		try {
			if (id > 0) {
				dao.deleTsById(id);
				re.setSuccess(true);
				re.setMsg("删除成功！");
			}
		} catch (Exception e) {
			re.setSuccess(false);
			re.setMsg("删除失败！");
			e.printStackTrace();
		} finally {
			out.print(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}
}
