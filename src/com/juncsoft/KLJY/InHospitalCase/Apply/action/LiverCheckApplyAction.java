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

import com.juncsoft.KLJY.InHospitalCase.Apply.dao.LiverCheckApplyDao;
import com.juncsoft.KLJY.InHospitalCase.Apply.entity.CancerJoinApply;
import com.juncsoft.KLJY.InHospitalCase.Apply.entity.LiverCheckApply;
import com.juncsoft.KLJY.InHospitalCase.Apply.impl.LiverCheckApplyDaoImpl;
import com.juncsoft.KLJY.util.Response;

public class LiverCheckApplyAction extends DispatchAction {
	private LiverCheckApplyDao dao = new LiverCheckApplyDaoImpl();
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
	
	// 查询病人的基本信息
	public ActionForward get_Main_Info(ActionMapping mapping, ActionForm from,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("The method of get_Main_Info was running...");
		Long historyCaseId = Long.parseLong(request.getParameter("kid"));
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try {
			res.setData(dao.get_Main_Info(historyCaseId).toString());
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
	
	// 查询申请单的信息
	public ActionForward get_Patient_Info(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			res.setData(dao.get_Patient_Info(id).toString());
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
	
	// 修改和保存信息
	public ActionForward saveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data = request.getParameter("data");
		Long id = Long.parseLong(request.getParameter("id"));
		PrintWriter out = response.getWriter();
		LiverCheckApply entity = (LiverCheckApply) JSONObject.toBean(JSONObject
				.fromObject(data), LiverCheckApply.class);
		String backId = null;
		try {
			backId = dao.saveOrUpdate(entity, id);
		} catch (Exception e) {

		} finally {
			out.println(backId);
			out.close();
		}
		return null;
	}
	
	// 删除一条信息
	public ActionForward deleteData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data = request.getParameter("data");
		try {
			LiverCheckApply entity = (LiverCheckApply) JSONObject.toBean(
					JSONObject.fromObject(data), LiverCheckApply.class);
			dao.deleteData(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;

	};
	
	// 打印
	public ActionForward LiverCheckApplyPrint(ActionMapping mapping,
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
				res.setData(JSONObject.fromObject(dao.LiverCheckApply_print(id)).toString());
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
