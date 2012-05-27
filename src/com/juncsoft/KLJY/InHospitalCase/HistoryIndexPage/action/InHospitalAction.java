package com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.dao.InHospitalDao;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.dao.InHospitalPageDao;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.InHospitalPage;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.InHosptialOne;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.InHosptialTwo;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.impl.BeInHospitalPageImpl;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.impl.InHospitalImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

/**
 * 病案首页Action
 * 
 * @author Administrator
 */
public class InHospitalAction extends DispatchAction {
	InHospitalPageDao dao = new BeInHospitalPageImpl();

	/**
	 * 查询病案首页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 病案首页实体
	 * @throws Exception
	 */
	public ActionForward InHospById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("caseId")) > 0 ? Long
					.parseLong(request.getParameter("caseId")) : -1L;
			res.setData(JSONObject.fromObject(
					dao.findInHospByCaseId(id),
					JSONValueProcessor.cycleExcludel(new String[] { "cc" },
							"yyyy-MM-dd HH:mm")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	/**
	 * 保存或修改病案首页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward InHospSaveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			System.out.println("保存或修改病案首页：" + data);
			JSONObject json = JSONObject.fromObject(data);
			InHospitalPage inHosp = (InHospitalPage) JSONObject.toBean(json,
					InHospitalPage.class);
			if (inHosp != null) {
				res.setData(JSONObject.fromObject(
						dao.inHospSaveOrUpdate(inHosp),
						JSONValueProcessor.cycleExcludel(new String[] { "cc" },
								"yyyy-MM-dd HH:mm")).toString());
				System.out.println("保存或修改返回值："
						+ JSONObject.fromObject(
								dao.inHospSaveOrUpdate(inHosp),
								JSONValueProcessor.cycleExcludel(
										new String[] { "cc" },
										"yyyy-MM-dd HH:mm")).toString());
				System.out.println("保存或修改病案首页成功！");
			} else {
				System.out.println("传入病案首页实体为null！");
			}
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			System.out.println("保存或修改病案首页(action)异常....");
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	/**
	 * 查询病案首页页面基本信息 - 存储过程
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPageInfoByCaseId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response rs = new Response();
		Long caseId = Long.parseLong(request.getParameter("caseId")) > 0 ? Long
				.parseLong(request.getParameter("caseId")) : -1L;
		try {
			if (caseId > 0) {
				InHospitalPage hosp = dao.findInItPageValue(caseId);
				if (hosp != null) {
					rs.setData(JSONObject.fromObject(hosp,
							JSONValueProcessor.cycleExcludel(new String[] { "cc" }, "yyyy-MM-dd HH:mm")).toString());
					rs.setSuccess(true);
				}
			}
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setMsg("查询数据失败！");
		} finally {
			out.print(JSONObject.fromObject(rs).toString());
			out.close();
		}
		return null;
	}
	
	/**
	 * 获取打印数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPrintData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long kid = Long.parseLong(request.getParameter("kid"))> 0 ? Long.parseLong(request.getParameter("kid")) : -1L;
		Response re = new Response(); 
		PrintWriter out = response.getWriter();
		try {
			if( kid!=null && kid > 0){
				re.setData(JSONObject.fromObject(dao.findInHospitalPrintData(kid),JSONValueProcessor.cycleExcludel(new String[] {""}, "yyyy-MM-dd HH:mm")).toString());
				System.out.println("打印数据：" + JSONObject.fromObject(dao.findInHospitalPrintData(kid),JSONValueProcessor.cycleExcludel(new String[] {""}, "yyyy-MM-dd HH:mm")).toString());
				re.setSuccess(true);
			}
		} catch (Exception e) {
			re.setSuccess(false);
			re.setMsg("获取打印数据失败！");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally{
			out.println(JSONObject.fromObject(re).toString());
			out.close();
		}
		return null;
	}

	/**
	 * 病案首页续页-保存修改
	 */
	public ActionForward ContinuePage_SaveOrUpdate(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject object = JSONObject.fromObject(request.getParameter("data"));
		String state = request.getParameter("state");
		System.out.println("#################"+state);
		JSONArray sysptom = object.getJSONArray("sysptom");
		JSONArray ops = object.getJSONArray("ops");
		JSONArray ward = object.getJSONArray("ward");
		JSONArray doctor = object.getJSONArray("doctor");
		dao.ContinuePage_SaveOrUpDate(sysptom, ops, ward, doctor,state);
		return null;
	}

	/**
	 * 病案首页续页-查询
	 */
	public ActionForward ContinuePage_FindByCaseId(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long caseId = Long.parseLong(request.getParameter("caseId"));
		Long patientId = Long.parseLong(request.getParameter("patientId"));
		Response res = new Response();
		PrintWriter out = response.getWriter();
		
		try{
			JSONObject data = dao.ContinuePage_FindByCaseId(caseId,patientId);
				res.setData(data.toString());
				res.setSuccess(true);
		}catch(Exception e){
			res.setSuccess(false);
			e.printStackTrace();
		}finally{
			out.print(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward ContinuePage_Print(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Long caseId = Long.parseLong(request.getParameter("caseId"));
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try{
			JSONObject data = dao.ContinuePage_Print(caseId);
			res.setData(data.toString());
			res.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			res.setSuccess(false);
		}finally{
			out.print(JSONObject.fromObject(res).toString());
			out.close();
		}
		
		return null;
	}
	
	//======================================================================================================
	InHospitalDao InHospDao = new InHospitalImpl();
	/**
	 * 读取首页值
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response   
	 * @return null
	 */
	public ActionForward getInHospOneByCaseId(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String par = request.getParameter("kid");
		Long CaseId = Long.parseLong(par) > 0 ? Long.parseLong(par) : -1L;
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try{
			if(CaseId!=null && CaseId > 0){
				InHosptialOne one = InHospDao.getInHospOne(CaseId);
				if (one != null) {
					res.setData(JSONObject.fromObject(one,
							JSONValueProcessor.cycleExcludel(
									new String[] { "cc" }, "yyyy-MM-dd")).toString());
					
				}else{
					res.setMsg("暂无数据！");
				}
				res.setSuccess(true);
			}
		}catch(Exception e){
			res.setSuccess(false);
			res.setMsg("读取数据失败！");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally{
			out.print(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward importInHospOneByCaseId(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String par = request.getParameter("kid");
		Long CaseId = Long.parseLong(par) > 0 ? Long.parseLong(par) : -1L;
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try{
			if(CaseId!=null && CaseId > 0){
				InHosptialOne one = InHospDao.importInHospOne(CaseId);
				if (one != null) {
					res.setData(JSONObject.fromObject(one,
							JSONValueProcessor.cycleExcludel(
									new String[] { "cc" }, "yyyy-MM-dd")).toString());
					
				}else{
					res.setMsg("暂无数据！");
				}
				res.setSuccess(true);
			}
		}catch(Exception e){
			res.setSuccess(false);
			res.setMsg("读取数据失败！");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally{
			out.print(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	/**
	 * 读取首页_反页值
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInHospTwoByCaseId(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		String par = request.getParameter("kid");
		Long CaseId = Long.parseLong(par) > 0 ? Long.parseLong(par) : -1L;
		try{
			if(CaseId != null && CaseId > 0){
				InHosptialTwo two = InHospDao.getInHospTwo(CaseId);
				if(two!=null){
					res.setData(JSONObject.fromObject(two,JSONValueProcessor.cycleExcludel(
									new String[] { "c" }, "yyyy-MM-dd HH:mm")).toString());
				}else{
					res.setMsg("暂无数据！");
				}
				res.setSuccess(true);
			}
		}catch(Exception e){
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
			res.setSuccess(false);
		}finally{
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	/**
	 * 保存或修改首页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOrUpdateInHospOne(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String data = request.getParameter("data");
		JSONObject json = JSONObject.fromObject(data);
		InHosptialOne one = (InHosptialOne) JSONObject.toBean(json,InHosptialOne.class);
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try{
			if (one != null) {
				one = InHospDao.saveOrUpdateInHospOne(one);
				res.setData(JSONObject.fromObject(one,
						JSONValueProcessor.cycleExcludel(new String[] { "a" },
								"yyyy-MM-dd")).toString());
				res.setSuccess(true);
				res.setMsg("保存成功！");
			}
		}catch(Exception e){
			res.setSuccess(false);
			res.setMsg("保存失败！");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally{
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	/**
	 * 保存或修改首页_反页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOrUpdateInHospTwo(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String data = request.getParameter("data");
		JSONObject json = JSONObject.fromObject(data);
		InHosptialTwo two = (InHosptialTwo) JSONObject.toBean(json,InHosptialTwo.class);
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try{
			if(two!=null){
				two = InHospDao.saveOrUpdateInHospTwo(two);
				res.setData(JSONObject.fromObject(two,JSONValueProcessor.cycleExcludel(new String[] { "a" },
				"yyyy-MM-dd HH:mm")).toString());
				res.setSuccess(true);
				res.setMsg("保存成功！");
			}
		}catch(Exception e){
			res.setSuccess(false);
			res.setMsg("保存失败!");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally{
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	/**
	 * 保存首页(继一[正])
	 * @param mapping
	 * @param from
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOrUpdateInHosptFollowOne(ActionMapping mapping,
			ActionForm from, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String data = request.getParameter("data");
		JSONObject json = JSONObject.fromObject(data);
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try{
			if(json!=null){
				Boolean struts = InHospDao.saveOrUpdateInHospFollowOne(json);
				if(struts){
					res.setSuccess(true);
					res.setMsg("保存成功！");
				}else{
					res.setMsg("暂无数据可以保存！");
				}
			}
		}catch(Exception e){
			res.setSuccess(false);
			res.setMsg("保存失败！");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally{
			out.print(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	/**
	 * 保存首页(继[反])
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOrUpdateInHospFollowTwo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data");
		JSONObject json = JSONObject.fromObject(data);
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try{
			if(json!=null){
				Boolean struts = InHospDao.saveOrUpdateInHospFollowTwo(json);
				if(struts){
					res.setMsg("保存成功！");
				}else{
					res.setMsg("暂无数据可以保存");
				}
				res.setSuccess(true);
			}
		}catch(Exception e){
			res.setSuccess(false);
			res.setMsg("保存失败！");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally{
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	/**
	 * 读取首页(继[正])
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInHospFollowOne(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long caseId = Long.parseLong(request.getParameter("kid")) > 0 ? Long.parseLong(request.getParameter("kid")) : -1L;
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try{
			if(caseId!=null && caseId> 0){
				JSONObject json = InHospDao.getInHospFollowOne(caseId);
				if(json!=null){
					res.setData(JSONObject.fromObject(json,JSONValueProcessor.cycleExcludel(
									new String[] { "c" }, "yyyy-MM-dd")).toString());
				}else{
					res.setMsg("暂无数据！");
				}
				res.setSuccess(true);
			}
		}catch(Exception e){
			res.setSuccess(false);
			res.setMsg("读取数据失败！");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally{
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	/**
	 * 读取首页(继[反])
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInHospFollowTwo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long caseId = Long.parseLong(request.getParameter("kid")) > 0 ? Long.parseLong(request.getParameter("kid")) : -1L;
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try{
			if(caseId!=null && caseId > 0){
				JSONObject json = InHospDao.getInHospFollowTwo(caseId);
				if(json!=null){
					res.setData(JSONObject.fromObject(json).toString());
				}
				res.setSuccess(true);
			}
		}catch(Exception e){
			res.setSuccess(false);
			res.setMsg("读取数据失败！");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		}finally{
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward getAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String addressName = request.getParameter("addressName");
			res.setData(InHospDao.getAddress(addressName));
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
}
