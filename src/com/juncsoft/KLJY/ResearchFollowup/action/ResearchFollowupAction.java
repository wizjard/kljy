package com.juncsoft.KLJY.ResearchFollowup.action;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.ResearchFollowup.dao.PatientResearchDao;
import com.juncsoft.KLJY.ResearchFollowup.entity.Followup;
import com.juncsoft.KLJY.ResearchFollowup.entity.FollowupCheckResult;
import com.juncsoft.KLJY.ResearchFollowup.entity.PatientResearch;
import com.juncsoft.KLJY.ResearchFollowup.entity.ResearchFollowSetUp;
import com.juncsoft.KLJY.system.Research.entity.ResearchTopic;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class ResearchFollowupAction extends DispatchAction {
	private PatientResearchDao dao = (PatientResearchDao) DaoFactory
			.InstanceImplement(PatientResearchDao.class);

	public ActionForward research_findByPatientId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			out.println(JSONArray.fromObject(dao.research_findByPatientId(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward research_delete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			PatientResearch pr = new PatientResearch();
			pr.setId(id);
			dao.research_delete(pr);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

/*	public ActionForward research_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			JSONObject json = JSONObject.fromObject(data);
			JSONArray array = json.getJSONArray("followup");
			PatientResearch pr = (PatientResearch) JSONObject.toBean(json,
					PatientResearch.class);
			pr = dao.reserch_saveOrUpdate(pr);
			Date date = pr.getFollowupStart();
			if (date == null)
				date = new Date();
			for (Object object : array) {
				JSONObject fuJson = JSONObject.fromObject(object);
				Long id = fuJson.getLong("id");
				Followup fu;
				if (id == -1) {
					fu = (Followup) JSONObject.toBean(fuJson, Followup.class);
					fu.setPatientResearchId(pr.getId());
				} else {
					fu = dao.followup_findById(id);
				}
				fu.setFollowTimes(fuJson.getInt("followTimes"));
				fu.setFollowCycle(fuJson.getInt("followCycle"));
				fu.setFollowContent(fuJson.getString("followContent"));
				Calendar cal = new GregorianCalendar();
				cal.setTime(date);
				cal.add(Calendar.MONTH, fu.getFollowCycle());
				fu.setFollowupPlanDate(cal.getTime());
				dao.followup_saveOrUpdate(fu);
			}
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}*/
	
	
	//此处代码不太合理，没有把保存操作放到同一个事务里，需要改进
	public ActionForward research_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			JSONObject json = JSONObject.fromObject(data);
			JSONArray array = json.getJSONArray("followup");
			PatientResearch _pr = (PatientResearch) JSONObject.toBean(json,
					PatientResearch.class);
			PatientResearch pr = dao.reserch_saveOrUpdate(_pr);
			
			List<Followup> followupList = new ArrayList<Followup>();
			List<ResearchFollowSetUp> list = dao.getResearchFollowSetUp(pr.getResearchId());
			
			for(ResearchFollowSetUp rfsu : list){
				Followup fu = new Followup();									
				fu.setPatientResearchId(pr.getId());
				
				Date date = pr.getFollowupStart();
				if (date == null){
					date = new Date();
				}
				Calendar cal = new GregorianCalendar();
				cal.setTime(date);
				if(fu.getFollowCycle() != "" && fu.getFollowCycle() != null){
					cal.add(Calendar.MONTH, Integer.parseInt(fu.getFollowCycle().substring(0, 1)));
				}				
				fu.setFollowupPlanDate(cal.getTime());
				fu.setPatientId(pr.getPatientId());
				fu.setPatientResearchId(pr.getId());
				fu.setFollowupPlanDate(pr.getFollowupStart());						
				fu.setFollowTimes(rfsu.getFollowTimes());
				fu.setFollowCycle(rfsu.getFollowCycle());
				
				if(rfsu.getFollowContent() != "" && rfsu.getFollowContent() != null){
					if(rfsu.getFollowContent().contains("*~^")){
						fu.setFollowContent(rfsu.getFollowContent().replace("*~^", "、"));
					}	
				}		
				followupList.add(fu);
			}			
			dao.followup_saveOrUpdate(followupList);
			
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward followup_findByResearchId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			out.println(JSONArray.fromObject(dao.followup_findByResearchId(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward followup_delete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Followup fu = new Followup();
			fu.setId(id);
			dao.followup_delete(fu);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward followup_saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			Followup fu = (Followup) JSONObject.toBean(JSONObject
					.fromObject(data), Followup.class);
			dao.followup_saveOrUpdate(fu);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward notice_findAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			Integer action = Integer.parseInt(request.getParameter("action"));
			out.println(dao.notice_findAll(start, limit, action));
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward notice_save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Followup fu = dao.followup_findById(id);
			String noticeDate = request.getParameter("noticeDate");
			String reserveDate = request.getParameter("reserveDate");
			String remarkContent = request.getParameter("remarkContent");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			if (noticeDate.length() > 0) {
				fu.setNoticeDate(df.parse(noticeDate));
			} else {
				fu.setNoticeDate(null);
			}
			if (reserveDate.length() > 0) {
				fu.setReserveDate(df.parse(reserveDate));
			} else {
				fu.setReserveDate(null);
			}
			fu.setRemarkContent(remarkContent);
			dao.followup_saveOrUpdate(fu);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward followup_findByPatientId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			out.println(dao.followRegin_findByPatientId(id).toString());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward saveFollowupCheckResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		FollowupCheckResult fcr = new FollowupCheckResult();
		try {
			int followupId = Integer.parseInt(request.getParameter("followupId"));
			String result = request.getParameter("proInfo");
			fcr.setFollowupId(followupId);
			fcr.setResult(result);
			dao.saveFollowupCheckResult(fcr);
			out.println("{issuccess:'保存成功'}");
		} catch (Exception e) {
			out.println("{issuccess:'保存失败'}");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward getFollowupCheckResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		FollowupCheckResult fcr = new FollowupCheckResult();
		try {
			int followupId = Integer.parseInt(request.getParameter("followupId"));						
			out.println(JSONObject.fromObject(dao.getFollowupCheckResult(followupId)).toString());
		} catch (Exception e) {
			out.println("{issuccess:'获取信息失败'}");
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward getFirstPatientFollowup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long patientResearchId = Long.parseLong(request.getParameter("patientResearchId"));
			out.println(dao.getFirstPatientFollowup(patientResearchId).toString());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getRootLogger().error(e.getMessage());
		} finally {
			out.close();
		}
		return null;
	}
}
