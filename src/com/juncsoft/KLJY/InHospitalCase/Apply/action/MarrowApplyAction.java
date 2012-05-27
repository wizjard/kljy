package com.juncsoft.KLJY.InHospitalCase.Apply.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.Apply.dao.MarrowApplyDao;
import com.juncsoft.KLJY.InHospitalCase.Apply.entity.MarrowApply;
import com.juncsoft.KLJY.InHospitalCase.Apply.impl.MarrowApplyDaoImpl;
import com.juncsoft.KLJY.util.Response;

public class MarrowApplyAction extends DispatchAction {

	private MarrowApplyDao dao = new MarrowApplyDaoImpl();

	// 初始化下拉菜单
	public ActionForward getCheckTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("The method of getPatientMessage running....");
		PrintWriter out = response.getWriter();
		Response res = new Response();
		JSONObject data = null;
		Long kid = Long.parseLong(request.getParameter("kid"));
		Long pid = Long.parseLong(request.getParameter("pid"));
		String entityName = request.getParameter("entityName");
		System.out.println(pid);
		if (pid == null) {
			pid = -1L;
		} else {
			try {
				// 调用查询时间方法，表中相关该病人骨髓检查申请的时间字段
				String checkDate = dao.public_mainMenu(entityName, kid)
						.toString();
				res.setData(checkDate);
				res.setSuccess(true);
			} catch (Exception e) {
				res.setSuccess(false);
				e.printStackTrace();
				Logger.getRootLogger().error(e.getMessage());
			} finally {
				out.println(JSONObject.fromObject(res).toString());
				out.close();
			}
		}
		return null;
	}

	// 初始化页面时调用该方法返回病人信息
	public ActionForward get_Patient_Message(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		Long kid = Long.parseLong(request.getParameter("kid"));
		System.out.println("dsa dsa dsa sda ds"+id);
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = dao.get_patient_Message(kid, id).toString();
			res.setData(data);
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

	// 返回病人的基本信息
	public ActionForward get_Main_Message(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long historyCaseId = Long.parseLong(request.getParameter("kid"));
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {

			res.setData(dao.get_Main_Message(historyCaseId)
					.toString());
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

	// 添加和修改病人信息
	public ActionForward saveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		System.out.println("The method of saveOrUpdate was running...");
		String data = request.getParameter("data");
		Long id = Long.parseLong(request.getParameter("id"));
		Long kid = Long.parseLong(request.getParameter("kid"));
		Response res = new Response();
		String backId = null;
		try {
			JSONObject json = JSONObject.fromObject(data);
			System.out.println("修改和添加数据时，传回Action的数据" + json);
			MarrowApply ma = (MarrowApply) JSONObject.toBean(json,
					MarrowApply.class);
			backId = dao.saveOrUpdate(ma, id, kid);
			res.setData(backId);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.print(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	// 删除病人信息
	public ActionForward delete_Patient_Message(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("The method of delete was running...");
		Long kid = Long.parseLong(request.getParameter("kid"));
		Long pid = Long.parseLong(request.getParameter("pid"));
		Long id = Long.parseLong(request.getParameter("id"));
		String data = request.getParameter("data");
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		try {
			JSONObject json = JSONObject.fromObject(data);
			MarrowApply entity = (MarrowApply) JSONObject.toBean(json,
					MarrowApply.class);
			message = dao.deletePatientMessage(id, pid, kid, entity);
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.print(message.toString());
			out.close();
		}

		return null;
	}

	// 打印
	public ActionForward marrowApplyPrint(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		PrintWriter out = response.getWriter();
		Response res = new Response();
		System.out.println("要打印数据的id：" + id);
		try {
			if (id == 0) {
				id = -1L;
			} else {
				res.setData(JSONObject.fromObject(dao.MarrowApply_print(id)).toString());
				res.setSuccess(true);
			}
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
