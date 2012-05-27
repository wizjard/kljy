package com.juncsoft.KLJY.patient.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.biomedical.dao.MemberInfoDao;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.biomedical.impl.MemberInfoImpl;
import com.juncsoft.KLJY.checkreport.dao.CheckReportDao;
import com.juncsoft.KLJY.checkreport.entity.Maidi;
import com.juncsoft.KLJY.checkreport.entity.Pacs;
import com.juncsoft.KLJY.checkreport.impl.CheckReportDaoImpl;
import com.juncsoft.KLJY.patient.dao.PatientDao;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.plan.dao.PlanDao;
import com.juncsoft.KLJY.plan.entity.CheckItem;
import com.juncsoft.KLJY.plan.entity.Plan;
import com.juncsoft.KLJY.plan.entity.PlanItem;
import com.juncsoft.KLJY.plan.impl.PlanDaoImpl;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.DateUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class PatientAction extends DispatchAction {
	private PatientDao dao = (PatientDao) DaoFactory
			.InstanceImplement(PatientDao.class);

	private MemberInfoDao memDao = new MemberInfoImpl();
	
	private CheckReportDao reportDao = new CheckReportDaoImpl();//add by cheng jiangyu
    private PlanDao planDao = new PlanDaoImpl(); //add by cheng jiangyu
	public ActionForward findById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Patient patient = dao.findById(id) ;
			//patient.setDiagGrounp("测试用的");
			res.setData(JSONObject.fromObject(patient,
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
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

	public ActionForward saveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			Patient patient = (Patient) JSONObject.toBean(JSONObject
					.fromObject(data), Patient.class);
			res.setData(JSONObject.fromObject(dao.saveOrUpdate(patient),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
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
	 * 查询全院病人
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			out.println(dao.queryAll(start, limit).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward queryByBelong(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			User user = (User) request.getSession().getAttribute("user");
			String belong = "";
			if (user != null){
				belong = user.getBelong();
				Integer start = Integer.parseInt(request.getParameter("start"));
				Integer limit = Integer.parseInt(request.getParameter("limit"));
				String data = dao.queryByBelong(belong, start, limit)
						.toString();
				out.println(data);
			}else{
				out.println("{root:[],total:0}");
			}
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
			out.println(dao.searchByNameOrNo(keyword, start, limit).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 新增 09-20
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchByCondition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String keyword = request.getParameter("keyword");
			String condition = request.getParameter("condition");
			//System.out.println("condition:" + condition);
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			out.println(dao.searchByCondition(keyword, condition, start, limit)
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward getPatientPageInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		JSONObject json = new JSONObject();
		try{
			Long patientId = Long.parseLong(request.getParameter("id"));
			Patient patient = dao.findById(patientId);
			if(patient != null){
				json.put("patientName", patient.getPatientName());
				json.put("patientNo", patient.getPatientNo());
			}
			res.setData(json.toString());
			res.setSuccess(true);
		}catch(Exception e){
			res.setSuccess(false);
			e.printStackTrace();
		}finally{
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	/**
	 * 会员入会保存会员信息，同时修改病人基本信息
	 */
	public ActionForward saveOrUpdatePM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject object = JSONObject.fromObject(request.getParameter("data"));
			Patient patient = (Patient) JSONObject.toBean(object, Patient.class);
			MemberInfo member = (MemberInfo) JSONObject.toBean(object,MemberInfo.class);
			Patient pat = new Patient();
			pat.setId(Long.parseLong(object.getString("id")));
			member.setPatient(pat);
			String memberNo = member.getMemberNo();
			if (memberNo != null && memberNo.length() > 0) {
				//MemberInfo m = memDao.get(member.getMemberNo());
				member.setAccount(member.getAccount());
				member.setMemberStatus(member.getMemberStatus());
				member.setInWard(member.getInWard());
				member.setInDate(member.getInDate());
				member.setPassword(member.getPassword());
				member.setMemberType(member.getMemberType());
				member.setMemo(member.getMemo());
				member.setBiaoben(member.getBiaoben());
				member.setInHspTimes(member.getInHspTimes());
				member.setInDoctor(member.getInDoctor());
				member.setInWardCode(member.getInWardCode());
			} else {
				member.setCurrentWard(member.getInWard());
			}
			patient.setPatientId(member.getAccount());
			res.setData(JSONObject.fromObject(
					dao.saveOrUpdatePMCopy(patient, member,Long.parseLong(object.get("caseId").toString())),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
			/*
			 * 增加一个会员时把该会员的所有前30天后30天的检查项目信息关联到一条随访计划   并在随访计划表中新增一条随访计划
			 * 开始 by cheng jiangyu  2011-8-22     
			 */
			if (memberNo == null || memberNo.length() < 1||"".equals(memberNo)) {
				String patientID = patient.getPatientId();
				Plan plan = new Plan();  //new 一个随访计划
				if(patient!= null) {
				   plan.setPatient(patient);
				}
				if(plan.getId() == null || plan.getId() < 1) {
				   plan.setCreateDate(new Date());
				   plan.setBeginDate(new Date());
				   plan.setId(null);
				   plan.setState(0);	
				   plan.setIsUse(1);
				}
				User user = (User) request.getSession().getAttribute("user");
				plan.setUser(user);
				Long planId = planDao.addPlan(plan);  //新增加的planId 
				plan.setId(planId);
				
				String resultURL = null;
				
				String beforeThirtyDays = DateUtil.getXDate(-30);  //得到前30天的日期
				//String afterThirtyDays = DateUtil.getXDate(30);  //得到后30天的日期
				String today = DateUtil.getToday();  //得到今天的日期
				//List<PlanItem> list = new ArrayList<PlanItem>();
				PlanItem item = new PlanItem();  //new 一个随访检查项目
				//item.setCheckItem(checkItem);
				//item.setCircle(checkItem.getCircle());
				item.setPlanId(planId);
				item.setCircleType(Calendar.MONTH);//changed from value of "0" -- 2011-8-11
				//默认7天
				item.setCrossd(7);
				item.setState(2);
				item.setVisitState(4);
				
				//通过patientId获得该病人前三十天或后三十天相应的   检查报告项目
				//查maidi
				List maidiList = reportDao.getMaidiReportsByPatientInThirtyDays(patientID.toString(), beforeThirtyDays, today);
					if(maidiList.size()>0&&maidiList!=null){
						for(int i=0;i<maidiList.size();i++){
							Object[] objArr = (Object[])maidiList.get(i);
							long repId = Long.parseLong(objArr[0].toString());  //maidi的id
							resultURL = "/module/checkreport/reportsheet_pacs.jsp?id=" + repId;
							item.setCheckItemCode(objArr[1].toString());//检查项目编号
							item.setCheckItemName(objArr[2].toString());  //检查项目名
							item.setReportDate(objArr[3].toString());   //检查日期
							item.setResultURL(resultURL);
							Long planIteId = planDao.addPlanItem(item);
							item.setId(planIteId);
							//list.add(item);
						}
					}
					//查pacs
				List pacsList = reportDao.getPacsReportsByPatientInThirtyDays(patientID.toString(), beforeThirtyDays, today);
					if(pacsList.size()>0&&pacsList!=null){
						for(int i=0;i<pacsList.size();i++){
							Object[] objArr = (Object[])pacsList.get(i);
							long repId = Long.parseLong(objArr[0].toString());  //pacs的id
							resultURL = "/module/checkreport/reportsheet_pacs.jsp?id=" + repId;
							item.setCheckItemCode(objArr[1].toString());  //检查项目编号
							item.setCheckItemName(objArr[2].toString());  //检查项目名称 
							item.setReportDate(objArr[3].toString());   //检查日期
							item.setResultURL(resultURL);
							Long planItemId = planDao.addPlanItem(item);
							item.setId(planItemId);
						}
					}
				//查checkReport
				List checkReportList = reportDao.getCheckReportsByPatientInThirtyDays(patient.getPatientNo(), beforeThirtyDays, today);
					if(checkReportList.size()>0&&checkReportList!=null){
						for(int i=0;i<checkReportList.size();i++){
							Object[] objArr = (Object[])checkReportList.get(i);
							resultURL = "/module/InHospitalCase/Liver/CheckReport/checkReport.html?patientId=" + patientID + "&receiveDate=" + objArr[0].toString() + 
							"&sectionNo=" + objArr[1].toString() + "&testTypeNo=" + objArr[2].toString() + "&sampleNo=" + objArr[3].toString() + "&parItemNo=" + objArr[4].toString() + "&itemName=" + objArr[5].toString();
							item.setReportDate(objArr[0].toString());
							item.setCheckItemName(objArr[5].toString());
							item.setResultURL(resultURL);
							Long planItemId = planDao.addPlanItem(item);
							item.setId(planItemId);
						}
					}
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
	
	
	public ActionForward findPatientAndMemberInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Long id = Long.parseLong(request.getParameter("id"));
			String data = JSONObject.fromObject(
					// 参数为1代表查找门诊科室
					dao.findPatientAndMemberInfo(id),JSONValueProcessor.cycleExcludel(
							new String[] { "cm" }, "yyyy-MM-dd")).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward queryByBelongKey(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			User user = (User) request.getSession().getAttribute("user");
			String belong = "";
			if (user != null) {
				belong = user.getBelong();
				String keyword = request.getParameter("keyword");
				Integer start = Integer.parseInt(request.getParameter("start"));
				Integer limit = Integer.parseInt(request.getParameter("limit"));
				String data = dao.queryByBelongKey(keyword,belong, start, limit)
						.toString();
				out.println(data);
			} else {
				out.println("{root:[],total:0}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
}
