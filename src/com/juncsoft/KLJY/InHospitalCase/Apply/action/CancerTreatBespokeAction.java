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

import com.juncsoft.KLJY.InHospitalCase.Apply.dao.CancerTreatBespokeDao;
import com.juncsoft.KLJY.InHospitalCase.Apply.entity.CancerTreatBespoke;
import com.juncsoft.KLJY.InHospitalCase.Apply.impl.CancerTreatBespokeDaoImpl;
import com.juncsoft.KLJY.util.Response;

public class CancerTreatBespokeAction extends DispatchAction {
	private CancerTreatBespokeDao dao = new CancerTreatBespokeDaoImpl();

	public ActionForward getCheckTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		System.out.println("The method of getPatientMessage running....");
		PrintWriter out = response.getWriter();
		Response res = new Response();
		Long kid = Long.parseLong(request.getParameter("kid"));
		Long pid = Long.parseLong(request.getParameter("pid"));
		String entityName = request.getParameter("entityName");
		if (pid == null) {
			pid = -1L;
		} else {
			try {
				// 调用查询时间方法，表中相关该病人骨髓检查申请的时间字段
				String checkDate = dao.public_mainMenu(entityName, kid)
						.toString();
				res.setData(checkDate);
				System.out.println(checkDate);
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

	// 查询病人的信息
	public ActionForward get_Patient_Info(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long id = Long.parseLong(request.getParameter("id"));
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			res.setData(JSONObject.fromObject(dao.get_Patient_Info(id))
					.toString());
			res.setSuccess(true);
			System.out.println(res.getData());
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	// 查询病人的基本信息
	public ActionForward get_Main_Info(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long historyCaseId = Long.parseLong(request.getParameter("kid"));
		PrintWriter out = response.getWriter();
		Response res = new Response();
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

	// 该方法负责保存新增记录或根据id修改记录
	public ActionForward saveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("The method of  saveOrUpadate was running.... ");
		String data = request.getParameter("data");
		Long id = Long.parseLong(request.getParameter("id"));
		Long patientId = Long.parseLong(request.getParameter("pid"));
		System.out.println(id);
		PrintWriter out = response.getWriter();
		String backId = null;
		try {
			JSONObject json = JSONObject.fromObject(data);
			CancerTreatBespoke entity = (CancerTreatBespoke) JSONObject.toBean(
					json, CancerTreatBespoke.class);
			backId = dao.saveOrUpdate(entity, id, patientId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(backId);
			out.close();
		}
		return null;
	}

	// 删除一条记录
	public ActionForward deleteData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data = request.getParameter("data");
		System.out.println(data);
		try {
			JSONObject json = JSONObject.fromObject(data);
			CancerTreatBespoke entity = (CancerTreatBespoke) JSONObject.toBean(
					json, CancerTreatBespoke.class);
			dao.deleteData(entity);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出异常了");
		} finally {
			System.out.println("...............");
		}

		return null;
	}
	// 打印
	public ActionForward CancerTreatBespokePrint(ActionMapping mapping,
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
				res.setData(JSONObject.fromObject(dao.CancerTreatBespoke_print(id)).toString

());
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
