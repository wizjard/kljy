package com.juncsoft.KLJY.patientanddoctoroperator.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//import com.juncsoft.KLJY.FindLisData;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.patientanddoctoroperator.dao.PatientConsultingDao;
import com.juncsoft.KLJY.patientanddoctoroperator.dao.PlanSignOrderDao;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PlanSignOrderEntity;
import com.juncsoft.KLJY.patientanddoctoroperator.impl.PatientConsultingImpl;
import com.juncsoft.KLJY.patientanddoctoroperator.impl.PlanSignOrderImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class PlanSignOrderAction extends DispatchAction {

	private PlanSignOrderDao dao = new PlanSignOrderImpl();

//	private FindLisData fd = new FindLisData();
	
	private PatientConsultingDao daoP = new PatientConsultingImpl();

	/**
	 * 外网查找医院的所有科室
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findAllSYS_HIS_DepartmentHISEntity(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String data = JSONObject.fromObject(
					// 参数为1代表查找门诊科室
					dao.findAllSYS_HIS_DepartmentHISEntity(1),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward findAllDayInWeek(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String date = request.getParameter("date");
			String deptCodeId = request.getParameter("deptCodeId");
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String data = JSONObject.fromObject(
					dao.findAllDayInWeek(deptCodeId, doctorId, date))
					.toString();
			out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward findBaseSignAPEntity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String data = JSONArray.fromObject(dao.findBaseSignAPEntity())
					.toString();
			out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward savePlanSignOrderEntity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String data = request.getParameter("data");
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String deptCodeId = request.getParameter("deptCodeId");
			String doctorDate = request.getParameter("currentDate");
			String[] array = data.split(",");
			List<PlanSignOrderEntity> list = new ArrayList<PlanSignOrderEntity>();
			for (int i = 0, size = array.length; i < size; i++) {
				String attmp = array[i];
				String[] json = attmp.replace("[", "").replace("]", "")
						.replace("\"", "").split("、");
				PlanSignOrderEntity planSignOrderEntity = new PlanSignOrderEntity();
				planSignOrderEntity.setDoctorId(doctorId);
				planSignOrderEntity.setDeptCodeId(deptCodeId);
				planSignOrderEntity.setBsAPId(Long
						.parseLong(json[1].toString()));
				doctorDate = doctorDate.substring(0, 7);
				doctorDate += "-" + json[0].toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				planSignOrderEntity.setCurrentDate(sdf.parse(doctorDate));
				planSignOrderEntity.setDayNum(Integer.parseInt(json[0]));
				planSignOrderEntity.setWeekDay(Integer.parseInt(json[2]));
				list.add(planSignOrderEntity);
			}
			dao.savePlanSignOrderEntity(list);
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
	 * 外网查找医院的所有科室
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findAllSYS_HIS_DepartmentHISEntityList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String data = JSONArray.fromObject(
					daoP.findAllSYS_HIS_DepartmentHISEntity("falg",1L),// 直接查找所有的科室
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			out.println(data);
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
	public ActionForward findAllDoctorNurses(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String deptCodeId = request.getParameter("deptCodeId");
			String outTypeIdList = request.getParameter("outTypeIdList");
			String data = JSONObject.fromObject(
					dao.findAllDoctorNurses(deptCodeId, outTypeIdList))
					.toString();
			out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward savePlanSignOrderPatientEntity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
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
				res.setMsg("loginOut");
			} else {
				Long bsAPId = Long.parseLong(request.getParameter("bsAPId"));
				Long doctorId = Long
						.parseLong(request.getParameter("doctorId"));
				String deptCode = request.getParameter("deptCode");
				String planDate = request.getParameter("planDate");
				Long bsTSId = Long.parseLong(request.getParameter("bsTSId"));
				boolean saveFlag = dao.savePlanSignOrderPatientEntity(deptCode,
						doctorId, mem.getPatient().getId(), bsAPId, planDate,
						bsTSId);
				res.setSuccess(saveFlag);
				res.setMsg("isHave");
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

	// 设置一周，同步一个月出诊设置
	public ActionForward savePlanSignOrderOneMonthEntity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String data = request.getParameter("data");
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String deptCodeId = request.getParameter("deptCodeId");
			String doctorDate = request.getParameter("currentDate");
			String[] array = data.split(",");
			List<PlanSignOrderEntity> list = new ArrayList<PlanSignOrderEntity>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date time = sdf.parse(doctorDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			int dayCount = cal.getActualMaximum(Calendar.DATE);
			for (int i = 0, size = array.length; i < size; i++) {
				String attmp = array[i];
				String[] json = attmp.replace("[", "").replace("]", "")
						.replace("\"", "").split("、");
				for (int j = 0; j < dayCount; j++) {
					int weekInt = time.getDay();
					if (weekInt == Integer.parseInt(json[1])) {
						PlanSignOrderEntity planSignOrderEntity = new PlanSignOrderEntity();
						planSignOrderEntity.setDoctorId(doctorId);
						planSignOrderEntity.setDeptCodeId(deptCodeId);
						planSignOrderEntity.setBsAPId(Long.parseLong(json[0]
								.toString()));
						doctorDate = doctorDate.substring(0, 7);
						doctorDate += "-" + (j + 1);
						planSignOrderEntity.setCurrentDate(sdf
								.parse(doctorDate));
						planSignOrderEntity.setDayNum(j + 1);
						planSignOrderEntity.setWeekDay(Integer
								.parseInt(json[1]));
						list.add(planSignOrderEntity);
					}
					time.setDate(time.getDate() + 1);
				}
				time.setDate(time.getDate() - dayCount);
			}
			dao.savePlanSignOrderOneMonthEntity(doctorDate.substring(0, 7)
					+ "-01", doctorDate.substring(0, 7) + "-" + dayCount, list);
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
	public ActionForward findPlanSignOrderPatient(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String planDate = request.getParameter("planDate");
			Long bsAPId = Long.parseLong(request.getParameter("bsAPId"));
			String deptCode = request.getParameter("deptCode");
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String data = JSONObject.fromObject(
					dao.findPlanSignOrderPatient(deptCode, doctorId, bsAPId,
							planDate)).toString();
			out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 会员登录显示以前所有的预约挂号记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findPlanSignOrderPatientList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
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
				JSONObject json = null;
				out.println(json);
			} else {
				int start = Integer.parseInt(request.getParameter("start"));
				int limit = Integer.parseInt(request.getParameter("limit"));
				out.println(dao.findPlanSignOrderPatientList(mem.getPatient().getId(), start,
						limit,null,null,null,null).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward deletePatientPlanOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long deleteId  = Long.parseLong(request.getParameter("deleteId"));
			dao.deletePatientPlanOrder(deleteId);
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
	 * 护士站登录导出今天之后两天内的所有会员预约信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findPlanSignOrderPatientHSList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			String planOrderStartDate = request.getParameter("planOrderStartDate");
			String planOrderEndDate = request.getParameter("planOrderEndDate");
			String doctorIds = request.getParameter("doctorId");
			Long doctorId = null;
			if(doctorIds != null){
				doctorId= Long.parseLong(doctorIds);
			}
			String deptcode = request.getParameter("deptcode");
			out.println(dao.findPlanSignOrderPatientList(null, start,
					limit,planOrderStartDate,planOrderEndDate,deptcode,doctorId).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward findAllSYS_HIS_DepartmentHISEntityMenList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String name = request.getParameter("name");
			Long nameValue = Long.parseLong(request.getParameter("nameValue"));
			String data = JSONArray.fromObject(
					daoP.findAllSYS_HIS_DepartmentHISEntity(name,nameValue),// 直接查找所有的科室
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward executeExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String planOrderStartDate = request.getParameter("planOrderStartDate");
			String planOrderEndDate = request.getParameter("planOrderEndDate");
			String doctorIds = request.getParameter("doctorId");
			Long doctorId = null;
			if(doctorIds != null){
				doctorId= Long.parseLong(doctorIds);
			}
			String deptcode = request.getParameter("deptcode");
			response.setContentType("APPLICATION/OCTET-STREAM"); 
			response.setHeader("Content-Disposition","attachment; filename=MemberInfoDate.xls");
			dao.exportSearchDataToExcel(null, 
					planOrderStartDate,planOrderEndDate,deptcode,doctorId,response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 查询医生14天的出诊记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findAllDoctorNursesFour(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String deptCodeId = request.getParameter("deptCodeId");
			String outTypeIdList = request.getParameter("outTypeIdList");
			String data = JSONObject.fromObject(
					dao.findAllDoctorNursesFour(deptCodeId, outTypeIdList))
					.toString();
			out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward findGrounpPlanSignOrderPatientList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			String doctorId = request.getParameter("doctorId");
			String search = request.getParameter("search");
			out.println(dao.findGrounpPlanSignOrderPatientList( start,
						limit,search,doctorId).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
}
