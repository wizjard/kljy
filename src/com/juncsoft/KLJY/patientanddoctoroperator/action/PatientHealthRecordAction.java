package com.juncsoft.KLJY.patientanddoctoroperator.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.patientanddoctoroperator.dao.PatientHealthRecordDao;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientConsulting;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientHealthRecord;
import com.juncsoft.KLJY.patientanddoctoroperator.impl.PatientHealthRecordImpl;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class PatientHealthRecordAction extends DispatchAction {

	private PatientHealthRecordDao dao = new PatientHealthRecordImpl();

	/**
	 * 会员登录查看自己的健康记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findPatientHealthRecordByPatientId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			if (mem == null) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.removeAttribute("MemberInfo");
					session.invalidate();
				}
				res.setSuccess(false);
			} else {
				String weekString = request.getParameter("weekFlag");
				String currentDate = request.getParameter("currentDate");
				Date date = null;
				int weekFlag = 0;
				if(weekString != null){
					weekFlag = Integer.parseInt(weekString);
				}
				if(currentDate != null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					date = sdf.parse(currentDate);
				}
				List mp = dao.findPatientHealthRecordByPatient(mem.getPatient().getId()
						,date,weekFlag);
				res.setData(JSONArray.fromObject(mp,
						JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
						.toString());
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

	public ActionForward findPatientHealthRecordCountByPatientId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			if (mem == null) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.removeAttribute("MemberInfo");
					session.invalidate();
				}
				res.setSuccess(false);
			} else {
				Map mp = dao.findPatientHealthRecordCountByPatientId(mem
						.getPatient().getId(),mem);
				mp.put("memberName", mem.getPatient().getPatientName());
				res.setData(JSONObject.fromObject(mp,
						JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
						.toString());
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

	/**
	 * 保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String data = request.getParameter("data");
			PatientHealthRecord patientHealthRecord = (PatientHealthRecord) JSONObject
					.toBean(JSONObject.fromObject(data),
							PatientHealthRecord.class);
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			if (mem == null) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.removeAttribute("MemberInfo");
					session.invalidate();
				}
				res.setSuccess(false);
			} else {
				patientHealthRecord.setPatientId(mem.getPatient().getId());
				patientHealthRecord.setDeptCode(mem.getDeptCode());
				patientHealthRecord.setGrounpId(mem.getGrounpId());
				if (patientHealthRecord.getId() == null) {
					dao.savePatientHealthRecord(patientHealthRecord,mem);
				} else {
					dao.updatePatientHealthRecord(patientHealthRecord);
				}
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

	/**
	 * 会员登录加载健康记录树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward patientHealthRecord_treeNodes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			if (mem == null) {
				JSONArray json = null;
				out.println(json);
			} else {
				String weekString = request.getParameter("weekFlag");
				String currentDate = request.getParameter("currentDate");
				Date date = null;
				int weekFlag = 0;
				if(weekString != null){
					weekFlag = Integer.parseInt(weekString);
				}
				if(currentDate != null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					date = sdf.parse(currentDate);
				}
				out.println(dao.patientHealthRecord_treeNodesByPatient(mem.getPatient()
						.getId(),date,weekFlag));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 医生登录查看会员的健康记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findPatientHealthRecordByPatientIdDoctor(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			String weekString = request.getParameter("weekFlag");
			String currentDate = request.getParameter("currentDate");
//			String deptName = request.getParameter("deptName");
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			Date date = null;
			int weekFlag = 0;
			if(weekString != null){
				weekFlag = Integer.parseInt(weekString);
			}
			if(currentDate != null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(currentDate);
			}
			List mp = dao.findPatientHealthRecordByPatientId(patientId,date,weekFlag,doctorId);
			res.setData(JSONArray.fromObject(mp,
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
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

	/**
	 * 会员登录加载健康记录树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward patientHealthRecord_treeNodesDoctor(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			String weekString = request.getParameter("weekFlag");
			String currentDate = request.getParameter("currentDate");
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			Date date = null;
			int weekFlag = 0;
			if(weekString != null){
				weekFlag = Integer.parseInt(weekString);
			}
			if(currentDate != null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(currentDate);
			}
			out.println(dao.patientHealthRecord_treeNodes(patientId,date,weekFlag,doctorId));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 查看书写健康记录的所有病人
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findPatientHealthRecordList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String search = request.getParameter("search");
			JSONObject json = null;
			if(search != null){
				json= JSONObject.fromObject(search);
			}
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			int weiCha =Integer.parseInt(request.getParameter("weiCha"));
			int yiCha=Integer.parseInt(request.getParameter("yiCha"));
			out.println(dao.findPatientHealthRecordList(doctorId,start, limit,weiCha,yiCha,json).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	/**
	 * 医生查看会员的健康记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findPatientHealthRecordByPatientIdDoctorOnly(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			String weekString = request.getParameter("weekFlag");
			String currentDate = request.getParameter("currentDate");
			Date date = null;
			int weekFlag = 0;
			if(weekString != null){
				weekFlag = Integer.parseInt(weekString);
			}
			if(currentDate != null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(currentDate);
			}
			List mp = dao.findPatientHealthRecordByPatient(
					patientId,date,weekFlag);
			res.setData(JSONArray.fromObject(mp,
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
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
	
	
	/**
	 * 医生查看会员的健康加载健康记录树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward patientHealthRecord_treeNodesDoctorOnly(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
				String weekString = request.getParameter("weekFlag");
				String currentDate = request.getParameter("currentDate");
				Long patientId = Long.parseLong(request.getParameter("patientId"));
				Date date = null;
				int weekFlag = 0;
				if(weekString != null){
					weekFlag = Integer.parseInt(weekString);
				}
				if(currentDate != null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					date = sdf.parse(currentDate);
				}
				out.println(dao.patientHealthRecord_treeNodesByPatient(patientId,date,weekFlag));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findCountPatientHealthRecordList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String search = request.getParameter("search");
			JSONObject json = null;
			if(search != null){
				json= JSONObject.fromObject(search);
			}
			String doctorIdS = request.getParameter("doctorId");
			if(doctorIdS != null){
				Long doctorId = Long.parseLong(request.getParameter("doctorId"));
				int weiCha =Integer.parseInt(request.getParameter("weiCha"));
				int yiCha=Integer.parseInt(request.getParameter("yiCha"));
				res.setData(dao.findCountPatientHealthRecordList(doctorId, weiCha, yiCha, json)+"");
			}
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
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findCountPatientHealthRecordByPatient(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			res.setData(dao.findCountPatientHealthRecordByPatient(patientId)+"");
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
	
	// 如果当前会员以前有转科记录
	public ActionForward findPatientHealthRecordHistory(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long patientId = Long.parseLong(request.getParameter("patientId"));
		
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			
			List mp = dao.findPatientHealthRecordHistory(patientId,doctorId);
			res.setData(JSONArray.fromObject(mp,
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
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
}
