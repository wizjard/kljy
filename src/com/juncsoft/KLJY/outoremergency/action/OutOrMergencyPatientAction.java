package com.juncsoft.KLJY.outoremergency.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.outoremergency.dao.OutOrMergencyPatientDao;
import com.juncsoft.KLJY.outoremergency.impl.OutOrMergencyPatientDaoImpl;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class OutOrMergencyPatientAction extends DispatchAction {

	OutOrMergencyPatientDao patientDao = new OutOrMergencyPatientDaoImpl();

	public ActionForward getOutOrMergencyMiddlePatientList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String deptcode = request.getParameter("deptcode");// 科室编码
			String drcode = request.getParameter("drcode");// 医生编码
			String gbrep = request.getParameter("gbrep");//诊疗状态
			String yizhouDate = request.getParameter("yizhouDate");
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			out.println(patientDao.getOutOrMergencyMiddlePatientList(start,
					limit, deptcode, drcode,gbrep,yizhouDate));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	//查找HIS中的病人信息特定帐户使用
	public ActionForward executeHISPatientNameByIdnumber(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String idnumber = request.getParameter("patientId");
			Patient patient = patientDao.executeHISPatientNameByIdnumber(idnumber);//返回电子病历系统中的病人编号ID
			
			res.setData(JSONObject.fromObject(patient,JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss")).toString());
			res.setSuccess(true);
		} catch (IOException e) {
			res.setSuccess(false);
			throw new RuntimeException("调用HIS查找病人信息存储过程出错",e);
		}finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	//执行单条病人存储过程(门诊)
	public ActionForward executeHISPatientByHISPatientid(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String idnumber = request.getParameter("patientId");
			String actdate = request.getParameter("actdate");
			String regno = request.getParameter("regno");
			String deptcode = request.getParameter("deptcode");
			String deptname = request.getParameter("deptname");
			String patientId= patientDao.executeHISPatientByHISPatientid(idnumber,actdate,regno,deptcode,deptname);//返回电子病历系统中的病人编号ID
			Map map = new HashMap();
			map.put("patientId", patientId);
			res.setData(JSONObject.fromObject(map,JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss")).toString());
			res.setSuccess(true);
		} catch (IOException e) {
			res.setSuccess(false);
			throw new RuntimeException("执行单条病人存储过程(门诊)出错",e);
		}finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	//执行单条病人存储过程(门诊)
//	public ActionForward executeHISPatientByHISPatientidSearch(ActionMapping mapping,ActionForm form,
//			HttpServletRequest request,HttpServletResponse response){
//		PrintWriter out = null;
//		Response res = new Response();
//		try {
//			out = response.getWriter();
//			String idnumber = request.getParameter("patientId");
//			String actdate = request.getParameter("actdate");
//			String regno = request.getParameter("regno");
//			String deptcode = request.getParameter("deptcode");
//			String patientId= patientDao.executeHISPatientByHISPatientidSearch(idnumber,actdate,regno,deptcode);//返回电子病历系统中的病人编号ID
//			Map map = new HashMap();
//			map.put("patientId", patientId);
//			res.setData(JSONObject.fromObject(map,JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss")).toString());
//			res.setSuccesss(true);
//		} catch (IOException e) {
//			res.setSuccess(false);
//			throw new RuntimeException("执行单条病人存储过程(门诊)出错",e);
//		}finally {
//			out.println(JSONObject.fromObject(res).toString());
//			out.close();
//		}
//		return null;
//	}
	
	
	public ActionForward findAllOutPatients(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
//			String deptCode = request.getParameter("deptCode");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String data = patientDao.findAllOutPatients("",start,limit)
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward searchByNameOrNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String keyword = request.getParameter("keyword");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			out.println(patientDao.searchByNameOrNo(keyword, start, limit).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	public ActionForward findById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(patientDao.findById(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd"))
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
	
	public ActionForward executeOnePatientOutCaseListByHis(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String flag = request.getParameter("flag");// 科室编码
			String searchCondition = request.getParameter("searchCondition");// 医生编码
			out.println(patientDao.executeOnePatientOutCaseListByHis(start,
					limit, flag, searchCondition));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	public ActionForward oneYiZhou(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
//			Integer start = Integer.parseInt(request.getParameter("start"));
//			Integer limit = Integer.parseInt(request.getParameter("limit"));
//			String deptcode = request.getParameter("deptcode");// 科室编码
//			String drcode = request.getParameter("drcode");// 医生编码
//			String gbrep = request.getParameter("gbrep");//诊疗状态
//			String yizhouDate = request.getParameter("yizhouDate");
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			patientDao.executeYiZhou();
//			out.println(patientDao.getOutOrMergencyMiddlePatientList(start,
//					limit, deptcode, drcode,gbrep,yizhouDate));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
}
