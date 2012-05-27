package com.juncsoft.KLJY.patientanddoctoroperator.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.juncsoft.KLJY.patientanddoctoroperator.dao.DoctorRoundsRecordDao;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.DoctorRoundsRecord;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientHealthRecord;
import com.juncsoft.KLJY.patientanddoctoroperator.impl.DoctorRoundsRecordImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class DoctorRoundsRecordAction extends DispatchAction {
	
	private DoctorRoundsRecordDao dao = new DoctorRoundsRecordImpl();
	
	/**
	 * 会员登录加载医生查房总记录数
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findDoctorRoundsRecordCountByPatientId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
				//liugang 2011-08-06 start
				Map mp = dao.findDoctorRoundsRecordCountByMember(mem);
				//liugang 2011-08-06 end
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
	 * 医生登录加载医生查房总记录数
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findDoctorRoundsRecordCountByPatientIdDoctor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			//liugang 2011-08-06 start
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			Map mp = dao.findDoctorRoundsRecordCountByPatientId(patientId,doctorId);
			//liugang 2011-08-06 end
			res.setData(JSONObject.fromObject(mp,
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
	 * 保存医生网上查房记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveOrUpdateDoctorRoundsRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String data = request.getParameter("data");
			DoctorRoundsRecord doctorRoundsRecord = (DoctorRoundsRecord) JSONObject
					.toBean(JSONObject.fromObject(data),
							DoctorRoundsRecord.class);
			if (doctorRoundsRecord.getId() == null) {
				doctorRoundsRecord = dao.saveDoctorRoundsRecord(doctorRoundsRecord);
			} else {
				dao.updateDoctorRoundsRecord(doctorRoundsRecord);
			}
			res.setData(JSONObject.fromObject(doctorRoundsRecord,
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
	 * 保存医生网上查房记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findDoctorRoundsRecordById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(dao.findDoctorRoundsRecordById(id),
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
	 * 医生登录加载健康记录树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doctorRoundsRecord_treeNodes(
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
			if(currentDate != null && (!"null".equals(currentDate))){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(currentDate);
			}
			out.println(dao.doctorRoundsRecord_treeNodes(patientId,date,weekFlag,doctorId));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
	public ActionForward doctorHealthRecord_treeNodes(ActionMapping mapping,
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
				out.println(dao.doctorRoundsRecord_treeNodesPatient(mem.getPatient()
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
	 * 会员登录加载健康记录树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doctorHealthRecord_treeNodesDoctorOnly(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
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
				out.println(dao.doctorRoundsRecord_treeNodes(patientId,date,weekFlag,null));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
}
