package com.juncsoft.KLJY.Public.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.Public.dao.DepartmentHISDao;
import com.juncsoft.KLJY.Public.impl.DepartmentHISImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class DepartmentHISAction extends DispatchAction {
	private DepartmentHISDao dao = new DepartmentHISImpl();
	
	public ActionForward findDepartmentHISEntityByDeptName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String deptName = request.getParameter("deptName");
			Map map = dao.findDepartmentHISEntityByDeptName(deptName);
			res.setData(JSONObject.fromObject(map,JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss")
					).toString());
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
	
	public ActionForward findDoctornameByGrounpId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long grounpId = Long.parseLong(request.getParameter("grounpId"));
			out.println(JSONObject.fromObject(dao.findDoctornameByGrounpId(grounpId)).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward findDoctorDeptMemberByDoctorId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String data = JSONArray.fromObject(dao.findDoctorDeptMemberByDoctorId(doctorId)).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*
	 * 根据医生Id 获得当前责任科室和小组名称  by cheng jiangyu 2011-09-30
	 * 
	 */
	public ActionForward findDoctorDeptAndGroupByDoctorId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String data = JSONArray.fromObject(dao.findDoctorDeptAndGroupByDoctorId(doctorId)).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	
	
	/*
	 *  通过管理员的deptCode查找管理员所在的小组列表  by cheng jiangyu 2011-9-30
	 */
	public ActionForward findManageDeptGroupBydeptCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String deptCode = request.getParameter("deptCode");
			String data = JSONArray.fromObject(dao.findManageDeptGroupBydeptCode(deptCode)).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	
	
	public ActionForward findDoctorGrounpByMemberByDoctorId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String deptCode = request.getParameter("deptCode");
			String data = JSONArray.fromObject(dao.findDoctorGrounpByMemberByDoctorId(doctorId,deptCode)).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward findGrounpByDeptCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String deptCode = request.getParameter("deptCode");
			String data = JSONArray.fromObject(dao.findGrounpByDeptCode(deptCode)).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*
	 *  通过管理员的deptCode查找管理员所在的小组列表  by cheng jiangyu 2011-9-30
	 */
	public ActionForward findManageDeptGroupBydeptCodeAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String data = JSONArray.fromObject(dao.findManageDeptGroupBydeptCode()).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
}
