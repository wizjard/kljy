package com.juncsoft.KLJY.plan.action;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.checkreport.dao.CheckReportDao;
import com.juncsoft.KLJY.checkreport.entity.Maidi;
import com.juncsoft.KLJY.checkreport.entity.Pacs;
import com.juncsoft.KLJY.checkreport.impl.CheckReportDaoImpl;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyCase;
import com.juncsoft.KLJY.patient.dao.PatientDao;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.patient.impl.PatientDaoImpl;
import com.juncsoft.KLJY.plan.dao.PlanDao;
import com.juncsoft.KLJY.plan.entity.CheckItem;
import com.juncsoft.KLJY.plan.entity.Plan;
import com.juncsoft.KLJY.plan.entity.PlanCount;
import com.juncsoft.KLJY.plan.entity.PlanItem;
import com.juncsoft.KLJY.plan.entity.PlanItemGroup;
import com.juncsoft.KLJY.plan.entity.PlanModule;
import com.juncsoft.KLJY.plan.entity.PlanModuleItem;
import com.juncsoft.KLJY.plan.impl.PlanDaoImpl;
import com.juncsoft.KLJY.util.DateUtil;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;
import com.stongnet.sms.http.Sms;
import com.stongnet.sms.http.SmsOperator;

public class PlanAction  extends DispatchAction {
	private PlanDao planDao = new PlanDaoImpl();
	private PatientDao memDao = new PatientDaoImpl();
	private CheckReportDao reportDao = new CheckReportDaoImpl();
	static int iniCircle = 3;
	static int iniCircleType = Calendar.MONTH;
	/**
	 * 随访计划列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllPlans(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("111111111111111111111");
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			List<Criterion> querys = new ArrayList<Criterion>();
			
			
			String login = request.getParameter("login");
			String paId = request.getParameter("paId");
			//@temp 医生id
			
//			if("user".equals(login)) {
//				User user = (User) request.getSession().getAttribute("user");
//				
//				if(user != null) {
//				   querys.add(Restrictions.eq("user", user));
//				}	
//			}
			//else 
			querys.add(Restrictions.eq("isUse", 1));
			if("mem".equals(login)) {

				//@temp 患者	会员		
				MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
				"MemberInfo");
				
				if(mem != null) {
				   querys.add(Restrictions.eq("patient", mem.getPatient()));
				}	
			}
			if(paId != null && paId.length() > 0) {
				Long _paId = new Long(0);
				try {
					_paId = Long.parseLong(paId);
				}
				catch(Exception e) {
					
				}
				
				List<Patient> pats = planDao
				.getPatients(Restrictions.eq("id", _paId));
		        querys.add(Restrictions.in("patient", pats));
			}
			
			String planTime = request.getParameter("planTime");
			String beginDate = request.getParameter("beginDate");
			
			if(planTime != null && planTime.length() > 0) {
				querys.add(Restrictions.like("planTime", "%" + planTime + "%"));
			}
			
			if(beginDate != null && beginDate.length() > 1) {
				Date bDate = new Date();
				SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
				try {
					bDate = formate.parse(beginDate);
				}
				catch(Exception e) {
					
				}
				
				querys.add(Restrictions.eq("beginDate", bDate));
			}
			
			List<Plan> pats = planDao.getAllPlans(start,
					limit, querys.toArray(new Criterion[] {}));
			long total = planDao.getTotalPlan(querys
					.toArray(new Criterion[] {}));
			JSONObject object = new JSONObject();
			object.put("root", pats);
			object.put("total", total);
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward getPlansGroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String login = request.getParameter("login");
			String paId = "";
			if(login!=null&"member".equals(login)){
				MemberInfo mem = (MemberInfo) request.getSession().getAttribute("MemberInfo");
				paId = mem.getPatient().getId().toString();
			}else if(login==null||"null".equals(login)||"".equals(login)){
				paId = request.getParameter("paId");
			}
			String sql = "select convert(int, p2.planTime) as planTime, p2.state as state, p2.beginDate as beginDate, ppt.planId as planId, ppt.circle as circle, ppt.circleType as circleType," +
					" ppt.planDate as planDate from " +
					" (select pt.planId as planId, pt.circle as circle, pt.circleType as circleType, pt.planDate as planDate from t_planItem pt inner join t_plan p on pt.planId=p.id where p.patientId='" + paId + "' and p.isUse=1 group by pt.planId, pt.circle, pt.circleType, pt.planDate) ppt" +
					" left join t_plan p2 on p2.id=ppt.planId where 1=1 ";

			String planTime = request.getParameter("planTime");
			String beginDate = request.getParameter("beginDate");
			String visitDate = request.getParameter("visitDate");
			String cricleT = request.getParameter("cricleT");
			
			if(planTime != null && planTime.trim().length() > 0) {
				sql = sql + " and p2.planTime like '%" + planTime + "%'";				
			}
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			
			if(beginDate != null && beginDate.length() > 1) {
				Date bDate = new Date();
				try {
					bDate = formate.parse(beginDate);
					sql = sql + " and p2.beginDate ='" + beginDate + "'";
				}
				catch(Exception e) {
					
				}
			}
			
			if(visitDate != null && visitDate.length() > 1) {
				try {
					visitDate = formate.format(formate.parse(visitDate));	
				}
                catch(Exception e) {
					
				}
			}
			
			sql = sql + " order by ppt.planTime desc, ppt.planDate";
			
			List<PlanItemGroup> pats = planDao.getListBySql(sql, limit, start);
			
			long total = planDao.getTotalBySql(sql);
				List<PlanItemGroup> plList = new ArrayList<PlanItemGroup>();
				PlanItemGroup pi = null;
				
				for(Object obj : pats) {
					if(obj instanceof Object[]) {
						pi = new PlanItemGroup();
						Object[] objA = (Object[]) obj;
						if(objA[3] == null || objA[4] == null || objA[5] == null) {
							continue;
						}
						
                        if(cricleT != null) {
                          String circleU = objA[4] + "";
                          switch ((Integer) objA[5]) {
	                          case Calendar.YEAR:
	                        	  circleU += "年";
	      						break;
	      					case Calendar.MONTH:
	      						circleU += "月";
	      						break;
	      					case Calendar.WEEK_OF_YEAR:
	      						circleU += "周";
	      						break;
	      					case Calendar.DAY_OF_YEAR:
	      						circleU += "天";
	      						break;
	      					default:
                          }
          				  if(circleU.indexOf(cricleT) < 0)	
                           continue;
                        }
						
						String hql = "from PlanItem where planId='" + objA[3] + "' and circle='" + objA[4] + "' and circleType='" + objA[5] + "'";
						List<PlanItem> items = this.planDao.getPlanItemsByHQL(hql);
                        String vd = this.getVisitDates(items);
                        
                        if(vd != null && visitDate != null && vd.indexOf(visitDate) < 0) {
                           continue;	
                        }
                        
                        pi.setVisitDate(vd);
						 
						pi.setPlanDate(objA[6] == null ? null : (Date) objA[6]);
						pi.setPlanTime(objA[0] == null ? null : Integer.parseInt(objA[0] + ""));
						pi.setBeginDate(objA[2] == null ? null :(Date)objA[2]);
						pi.setCircle(objA[4] == null ? null :(Integer) objA[4]);
						pi.setPlanId(objA[3] == null ? null :(BigInteger) objA[3]);
						pi.setCircleType(objA[5] == null ? null :(Integer) objA[5]);
						//pi.setState(objA[1] == null ? null :(Integer) objA[1]);
						//设置随访状态
                        pi.setVisitState(this.getGroupVisitSate(items));
                        //设置执行情况
                        pi.setStateStr(this.getPlanGroupSate(items));
                        pi.setState(objA[1] == null ? null :(Integer) objA[1]);
                        pi.setResultState(this.getGroupResultSate(items));
                        
						plList.add(pi);
					}					
				}

			JSONObject object = new JSONObject();
			object.put("root", plList);
			object.put("total", total);
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	private String getVisitDates(List<PlanItem> items) {
		if(items == null || items.size() < 1) {
			return null;
		}
		
		String result = "";
		int count = 0;
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");

		for(PlanItem item : items) {
		    if((item.getState() == 2 || item.getState() == 1) && item.getVisitDate() != null) {
		       if(result.contains(formate.format(item.getVisitDate())))	{
		    	  continue;
		       }
		    	
		       result = result + formate.format(item.getVisitDate()) + ",";
		       count++;
		    }
		}
		
		if(result.endsWith(",")) {
			result = result.substring(0, result.length() - 1);
		}
		
		if(result.length() > 1) {
			result = "<span title='" + result + " '>" + result + "</span>";
		}
		
		return result;		
	}
	
	private String getGroupResultSate(List<PlanItem> items) {
		//此处逻辑可能错误 但是没找到  
		if(items == null || items.size() < 1) {
			return null;
		}
		
		String result = "";
		int count = 0;

		for(PlanItem item : items) {
		    if(item.getState() == 0 || item.getState() == 1) {
		    	  String name = item.getCheckItem() == null ? item.getCheckItemName() : item.getCheckItem().getORDERNAMEK();	
			       result = result + name + ",";
		       count++;
		    }
		}
		
		if(result.length() < 1) {
			result = "<span style='color:green' title='所有结果已关联'>所有结果已关联</span>";
		}
		else if(count == items.size()) {
			result = "<span style='color:#EE0000' title='所有结果未关联'>所有结果未关联</span>";
		}
		else {
			result = "<span style='color:#1F9900' title='" + result + " 项目未关联'>" + result + " 项目未关联</span>";	
		}

		return result;
	}
	
	private String getPlanGroupSate(List<PlanItem> items) {
		if(items == null || items.size() < 1) {
			return null;
		}
		
		String result = "";
		int count = 0;

		for(PlanItem item : items) {
		    if(item.getState() == 0) {
		      String name = item.getCheckItem() == null ? item.getCheckItemName() : item.getCheckItem().getORDERNAMEK();	
				       result = result + name + ",";
		       count++;
		    }
		}

		
		if(result.length() < 1) {
			result = "<span style='color:green' title='检查执行完毕'>检查执行完毕</span>";
		}
		else if(count == items.size()) {
			result = "<span style='color:#EE0000' title='所有检查未执行'>所有检查未执行</span>";
		}
		else {
			result = "<span style='color:#1F9900' title='" + result + " 项目未执行'>" + result + " 项目未执行</span>";	
		}
		
		return result;
	}
	
	private int getGroupVisitSate(List<PlanItem> items) {
		if(items == null || items.size() < 1) {
			return 0;
		}

		for(PlanItem item : items) {
			if(item.getVisitState() == null) {
				return 0;
			}
			
		   return item.getVisitState();
		}
		
		return 0;
	}
	
	//==========================changed by Dong Chao, 2011-08-10=====================================
	public ActionForward findAllDayInWeek(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Map mp = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			out = response.getWriter();
			Long paId = Long.parseLong(request.getParameter("paId"));
			String monthDatePra = request.getParameter("date");
			Date monthDate = sdf.parse(monthDatePra);
			Calendar cal = Calendar.getInstance();
			cal.setTime(monthDate);
			int dayCount = cal.getActualMaximum(Calendar.DATE);
			List<Plan> planList = planDao.getPlansByPatient(paId);
			List<Object[]> checkDateAndItemList = new ArrayList<Object[]>();
			for(Plan plan : planList){
				Date beginDate = plan.getBeginDate();
				List<PlanItem> itemList = planDao.listPlanItems(plan.getId());
				List<Date> checkDateList = new ArrayList<Date>();//每个项目计划检查的日期列表
				for(PlanItem item : itemList){
					if(item.getState() == 1 || item.getState() == 2) {
						   Date vDate = item.getVisitDate();
						   
						   if(vDate != null) {
							   Object[] value = new Object[2];
								value[0] = vDate;
								value[1] = item;
								checkDateAndItemList.add(value);
							   continue;
						   }
					}
					
					Calendar checkDate = Calendar.getInstance();
					checkDate.setTime(beginDate);
					Integer circleType = item.getCircleType();
					circleType = circleType == null ? Calendar.WEEK_OF_YEAR : circleType;
					checkDate.add(circleType, item.getCircle());
					Object[] value = new Object[2];
					value[0] = checkDate.getTime();
					value[1] = item;
					checkDateAndItemList.add(value);
				}
			}
			String outString = "";
			for (int i = 0; i < dayCount; i++) {
				int weekInt = monthDate.getDay();//0-6
				if (i == 0) {
					outString += "<tr style=\"height:70px;text-align:top\">";
				}
				switch (weekInt) {
				case 0:
					outString += this.executeProcess(checkDateAndItemList, monthDate, i);
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
					}
					break;
				case 1:
					if (i == 0) {
						outString += "<td>&nbsp;</td>";
					}
					outString += this.executeProcess(checkDateAndItemList, monthDate, i);
							 
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
					}
					break;
				case 2:
					if (i == 0) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td>";
					}
					outString += this.executeProcess(checkDateAndItemList, monthDate, i);
							 
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
					}
					break;
				case 3:
					if (i == 0) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
					}
					outString += this.executeProcess(checkDateAndItemList, monthDate, i);
							 
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
					}
					break;
				case 4:
					if (i == 0) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
					}
					outString += this.executeProcess(checkDateAndItemList, monthDate, i);
							 
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td></tr>";
					}
					break;
				case 5:
					if (i == 0) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
					}
					outString += this.executeProcess(checkDateAndItemList, monthDate, i);
							 
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td></tr>";
					}
					break;
				case 6:
					if (i == 0) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
					}
					outString += this.executeProcess(checkDateAndItemList, monthDate, i);
					if (i < dayCount - 1) {
						outString += "</tr><tr style=\"height:70px;text-align:left\">";
					}
					if (i == dayCount - 1) {
						outString += "</tr>";
					}
					break;
				}
				monthDate.setDate(monthDate.getDate() + 1);
			}
			mp.put("weekList", outString);
			
			String data = JSONObject.fromObject(mp).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	private String executeProcess(List<Object[]> checkDateAndItemList, Date today, int i) {
		String outString = "";
		boolean isPlanDay = false;
		int state = 2;
		for(Object[] objs : checkDateAndItemList){
			Date date = (Date)objs[0];
			PlanItem item = (PlanItem)objs[1];
			if(DateUtil.inSameDate(today, date)){
				isPlanDay = true;
				state = item.getState()<state ? item.getState() : state;
			}
			/*else if(DateUtil.inSameDate(today, item.getVisitDate())) {
				isPlanDay = true;
				state = -2;
			}*/
			
		}

		String color = "";
		switch(state){
		case 0:
			color = "#EE0000";
			break;
		case 1:
			color = "#0968F7";
			break;
		case 2:
			color = "green";
			break;
		case -2 :
			color = "#CDCD00";
			break;
		}
		
		
		if(isPlanDay){
			//实际来访日期
			/*if(state == -2) {
				outString += "<td valign='center' align='center' class='dateNum' " +
				"onmouseover='showItems(event, true);' " +
				"onmouseout='hiddenItems(event);' " +
				"bgcolor='"+color+"'>" + (i+1) + "</td>";
			}
			else */
			outString += "<td valign='center' align='center' class='dateNum' " +
					"onmouseover='showItems(event);' " +
					"onmouseout='hiddenItems(event);' " +
					"bgcolor='"+color+"'>" + (i+1) + "</td>";
		}else{
			outString += "<td valign='center' align='center' class='dateNum'>"+(i+1)+"</td>";
		}
		
		return outString;
	}
	//==========================================End of change=========2011-08-10=======================
	
	//=======================================changed by DongChao, 2008-8-10============================
	public ActionForward findPlanDate(			
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Map map = new HashMap();
		List<Map> dateList = new ArrayList<Map>();
		try {
			out = response.getWriter();
			Long paId = Long.parseLong(request.getParameter("paId"));
			List<Plan> plans = planDao.getPlansByPatient(paId);
			List<Date> checkDateList = new ArrayList<Date>();
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			
			for (Object planobj : plans) {
				Plan plan = (Plan)planobj;
				List items = planDao.listPlanItems(plan.getId());
				//List<Date> checkDateList = new ArrayList<Date>();//每个项目计划检查的日期列表
				for (Object o : items) {
					boolean mut = true;
					PlanItem item = (PlanItem) o;
					Date vDate = null;
					
					if((item.getState() == 1 || item.getState() == 2) && item.getVisitDate() != null) {
					   vDate = item.getVisitDate();
					   
					}
					else {
						Calendar checkDate = Calendar.getInstance();
						checkDate.setTime(plan.getBeginDate());
						
						Integer circleType = item.getCircleType();
						circleType = circleType == null ? Calendar.WEEK_OF_YEAR : circleType;
						
						checkDate.add(circleType, item.getCircle());
						vDate = checkDate.getTime();
					}
					
					for (Date d : checkDateList) {
						if (d.getMonth() == vDate.getMonth()
								&& d.getYear() == vDate.getYear()) {
							mut = false;
							break;
						}
					}
					if (mut) {						
						checkDateList.add(formate.parse(formate.format(vDate)));
					}
				}
				
			}
			
			Collections.sort(checkDateList);
			
			for (Date date : checkDateList) {
				int y = date.getYear();
				y = y > 1000 ? y : y + 1900;
				int m = date.getMonth();
				Map mp = new HashMap();
				mp.put("dateList", y + "年" + (m + 1) + "月");
				mp.put("dateValue", y + "-" + (m + 1) + "-" + 1);
				dateList.add(mp);
			}
			map.put("dateList", dateList);
			String data = JSONObject.fromObject(
					map,
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

	public ActionForward findItemsInDateByPatient(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		long paId = Long.parseLong(request.getParameter("paId"));
		int dateOfMonth = Integer.parseInt(request.getParameter("dateOfMonth"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date showDate;
		PrintWriter out = null;
		try {
			showDate = sdf.parse(request.getParameter("date"));
			String isVisit = (request.getParameter("visit"));
			showDate.setDate(dateOfMonth);
			out = response.getWriter();
			List<Plan> planList = planDao.getPlansByPatient(paId);
			StringBuffer dataAll = new StringBuffer();
			for(Plan plan : planList){
				StringBuffer data = new StringBuffer();
				List items = planDao.listPlanItems(plan.getId());
	//			int state = 2;
	//			for(Object o : items){
	//				PlanItem item = (PlanItem)o;
	//				state = item.getState()<state ? item.getState() : state;
	//			}
				List<Date> checkDateList = new ArrayList<Date>();//每个项目计划检查的日期列表
				Date date = plan.getBeginDate();
	//			String data = JSONArray.fromObject(items).toString();
				data.append("<p>当日随访计划项目 -- 第"+plan.getPlanTime()+"次随访</p>");
				data.append("<p style='text-align:left'>随访计划设置日期："+DateUtil.formatDate(date, 2)+"</p>");
				data.append("<table id='itemtable' border=1 cellpadding=2 cellspacing=0 style='margin-left:20px'>" +
						"<tr>" +
						"<td width=40>序号</td>" +
						"<td width=120>检查项目</td>" +
						"<td width=60>周期 </td>");
	//			if(state!=2){
					data.append("<td width=100>计划访问日期 </td>");
	//			}
	//			if(state==1 || state==2){
					data.append("<td width=100>实际来访日期 </td>");
	//			}
				data.append("<td width=80>备注 </td>");
	//			if(state==2){
					data.append("<td width=80>状态 </td>");
					data.append("<td width=80>查看检查结果 </td>");
	//			}
				data.append("</tr>");
				
				int count = 0;
				int sum = 0;
				for(Object o:items){
					count++;
					PlanItem i = (PlanItem)o;
					Date vDate = null;
					
					if(i.getState() == 1 || i.getState() == 2) {
						   if(i.getVisitDate() != null) {
							   vDate = i.getVisitDate();
						   }						   
						}
					Calendar checkDate = Calendar.getInstance();
					checkDate.setTime(plan.getBeginDate());
					checkDate.add(i.getCircleType(), i.getCircle());
					
					if(vDate == null) {
						vDate = checkDate.getTime();
					}
					
					String trAttr = "";
					if(!DateUtil.inSameDate(("true".equals(isVisit)) ? i.getVisitDate() : vDate, showDate)){
						continue;
					}
					sum++;
					String resultStr = "";

					if(i.getState() == 2) {
						String resultUrl = i.getResultURL();
						resultStr = "<input type='button' value='查看结果' " +
								"onclick=\"javascript:window.open('/TCMP" + resultUrl + "','检查结果','width=950px;height=650px');\" " +
								"style=\"font-variant:normal;color:green;\"></input>";	
					}
					else {
						resultStr = "无检查结果";
					}
					
					String stateStr = "";
					
					switch(i.getVisitState()){
					case 0:
						stateStr = "<font color='#EE0000'>随访计划中</font>";
						break;
					case 1:
						stateStr = "<font color='#0968F7'>提前来访</font>";
						break;
					case 2:
						stateStr = "<font color='#0968F7'>按期来访</font>";
						break;
					case 3:
						stateStr = "<font color='#0968F7'>超期来访</font>";
						break;
					case 4:
						stateStr = "<font color='green'>随访结束</font>";
						break;
					case 5:
						stateStr = "<font color='#EE0000'>超期未来访</font>";
						//stateStr = "<font color='#B8B8B8'>超期未来访</font>";
						break;	
					default:
						stateStr = "未知";
					}
					
					data.append("<tr "+trAttr+"><td>");
					data.append(count);
					data.append("</td><td widtn=120>");
					data.append(i.getCheckItem() == null ? i.getCheckItemName() : i.getCheckItem().getORDERNAMEK());
					data.append("</td><td>");
					String unit;
					switch(i.getCircleType()){
					case Calendar.YEAR:
						unit = "年";
						break;
					case Calendar.MONTH:
						unit = "个月";
						break;
					case Calendar.WEEK_OF_YEAR:
						unit = "周";
						break;
					case Calendar.DAY_OF_YEAR:
						unit = "天";
						break;
					default:
						unit = "";
					}
					data.append(i.getCircle()+unit);
					
					data.append("</td><td>");
	//				if (state!=2) {
						data.append(DateUtil.formatDate(checkDate.getTime(), -1));
						data.append("</td><td>");
	//				}
	//				if (state==1 || state==2) {
						data.append(DateUtil.formatDate(i.getVisitDate(), -1));
						data.append("&nbsp;</td><td>");
	//				}
					data.append(i.getComment()==null||i.getComment().equals("") ? "&nbsp;" : i.getComment());
					data.append("</td>");
					data.append("<td>");
					data.append(stateStr);
					data.append("</td>");
					data.append("<td>");
					data.append(resultStr);
					data.append("</td>");
					data.append("</tr>");
					
				}
				data.append("</table><div>&nbsp;</div>");
				if(sum!=0){
					dataAll.append(data);
				}
			}
			out.println(dataAll.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ActionForward saveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long patientID = new Long(0);
			String patid = request.getParameter("patid");
			
			Plan plan = null;
			
			try {
				patientID = Long.parseLong(patid + "");	;
			}
			catch (Exception e) {
			 // ignore	
			}
			
			if(patientID < 1) {
				String data = request.getParameter("data");
				JSONObject obj = JSONObject.fromObject(data);
				plan = (Plan) JSONObject.toBean(obj, Plan.class);
				
				Object patientId = obj.get("patientId");

				try {
					patientID = Long.parseLong(patientId + "");	
				}
				catch(Exception e) {
					// ignore
				}
			}
			else {
				plan = new Plan();
			}
			
			Patient info = memDao.findById(patientID);
			
			if(info != null) {
			   plan.setPatient(info);
			}
            
			boolean isAdd = true;
			
			if(plan.getId() == null || plan.getId() < 1) {
			   plan.setCreateDate(new Date());
			   plan.setBeginDate(new Date());
			   plan.setId(null);
			   plan.setState(0);	
			   plan.setIsUse(0);
			}
			else {
				Plan _plan = this.planDao.getPlan(plan.getId());
				SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
				
				// 起始时间改变, 机会实际也相应改变
				if(!formate.format(_plan.getBeginDate()).equals(formate.format(plan.getBeginDate()))) {
					String hql = "from PlanItem where planId='" + plan.getId() + "'";
					List<PlanItem> items = this.planDao.listPlanItems(hql);
					Calendar checkDate = Calendar.getInstance();
					//
					Date _bDate = formate.parse(formate.format(plan.getBeginDate()));
					
					for(PlanItem item : items) {						
						checkDate.setTime(_bDate);
						checkDate.add(item.getCircleType(), item.getCircle());
						item.setPlanDate(checkDate.getTime());
						this.planDao.updatePlanItem(item);						
					}
				}
				
				isAdd = false;
				plan.setIsUse(1);
				plan.setModifyDate(new Date());
			}						
			
			User user = (User) request.getSession().getAttribute("user");
			plan.setUser(user);
			
			Long id = planDao.addPlan(plan);
			plan.setId(id);

			Calendar checkDate = Calendar.getInstance();
			
			// 精确到日期
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			Date _bDate = formate.parse(formate.format(plan.getBeginDate()));			
			checkDate.setTime(_bDate);
			//初始化
			checkDate.add(iniCircleType, iniCircle);
			System.out.println(formate.format(checkDate.getTime()) + "--------------");
			//@temp save the checkDate into message
			 res.setMsg(formate.format(checkDate.getTime()));
			
			res.setData(JSONObject.fromObject(plan,
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
	
	//update by cheng jiangyu 2011-8-21
	public ActionForward getAllCheckItems(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("ccccccccccccccccccccccccc");
		PrintWriter out = response.getWriter();
		Integer start = Integer.parseInt(request.getParameter("start"));
		Integer limit = Integer.parseInt(request.getParameter("limit"));
		List<Criterion> querys = new ArrayList<Criterion>();
		Criterion criteria = null;
		try {
			// 医生id		
			//String slipno = request.getParameter("slipno");
			//String ordercode = request.getParameter("ordercode");
			
			/*if(slipno != null && slipno.length() > 0) {
			   querys.add(Restrictions.like("SLIPNO", "%" + slipno + "%"));
			}
			if(ordercode != null && ordercode.length() > 0) 
			querys.add(Restrictions.like("ORDERCODE", "%" + ordercode + "%"));*/
			String orderName = request.getParameter("ordername");
			if(orderName != null && orderName.length() > 0){
				orderName = orderName.toUpperCase();
				orderName = "%"+orderName+"%";
				criteria = Restrictions.or(Restrictions.like("ORDERNAMEK", orderName), Restrictions.like("HCODE", orderName));
				querys.add(criteria);
			}
			List<CheckItem> pats = planDao.getAllCheckItems(start,
					limit, querys.toArray(new Criterion[] {}));
			long total = planDao.getTotalChecks(querys
					.toArray(new Criterion[] {}));
			JSONObject object = new JSONObject();
			object.put("root", pats);
			object.put("total", total);
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;			
	}



	
	public ActionForward getAllPlanItems(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("ddddddddddddddddddddddddddddddd");
	    String planIdStr = request.getParameter("planId");
		
		if(planIdStr == null || planIdStr.length()<0) {
			return null;
		}
		
		PrintWriter out = response.getWriter();
		Integer start = Integer.parseInt(request.getParameter("start"));
		Integer limit = Integer.parseInt(request.getParameter("limit"));
		List<Criterion> querys = new ArrayList<Criterion>();
		Long planId = Long.parseLong(request.getParameter("planId"));
		querys.add(Restrictions.eq("planId", planId));
		
		String circle = request.getParameter("circle");
		String circleType = request.getParameter("circleType");
		
		if(circle != null && circle.length() > 0) {
			querys.add(Restrictions.eq("circle", Integer.parseInt(circle)));
		}
		if(circleType != null && circleType.length() > 0) {
			querys.add(Restrictions.eq("circleType", Integer.parseInt(circleType)));
		}
		
		try {
			List<PlanItem> pats = planDao.getAllPlanItems(start,
					limit, querys.toArray(new Criterion[] {}));
			long total = planDao.getTotalItems(querys.toArray(new Criterion[] {}));
			JSONObject object = new JSONObject();
			object.put("root", pats);
			object.put("total", total);
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;			
	}
	

      //删除随访计划 By cheng jiangyu
	public ActionForward deletePlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("eeeeeeeeeeeeeeeeeeeeeee");
		PrintWriter out = response.getWriter();
		Response res = new Response();
		res.setSuccess(true);
		Long id = Long.parseLong(request.getParameter("id"));
		Integer cricleD = Integer.parseInt(request.getParameter("cricleD"));
		Integer cricleTD = Integer.parseInt(request.getParameter("cricleTD"));
		
		try {
			planDao.deletePlanGroup(id, cricleD, cricleTD);
			
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward findPlanById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("ffffffffffffffffffffffffffff");
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			Plan plan = planDao.getPlan(id);
			res.setData(JSONObject.fromObject(plan,
					JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
			Calendar checkDate = Calendar.getInstance();
			// 精确到日期
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			Date _bDate = formate.parse(formate.format(plan.getBeginDate()));			
			checkDate.setTime(_bDate);
			
			checkDate.add(iniCircleType, iniCircle);
			//@temp save the checkDate into message
			 res.setMsg(formate.format(checkDate.getTime()));
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
	
	
	 public ActionForward checkPlanItem(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PrintWriter out = response.getWriter();
			Response res = new Response();
			try {
				String planId = request.getParameter("planId");
				String checkId = request.getParameter("checkId");
				CheckItem checkItem = null;
				
				if(checkId != null && checkId.length() > 0) {
				   checkItem = planDao.getCheckItem(Long.parseLong(checkId));
				   String hql = "from PlanItem p where p.planId='" + planId + "' and p.checkItem.id = '" + checkId + "'";
				   List items = planDao.listPlanItems(hql);
				   
				   if(items != null && items.size() > 0) {
					  res.setSuccess(false);
					  res.setMsg("检查项目已存在");
					  return null;
				   }
				}
				else if(checkId == null) {
					//res.setSuccess(false);
					//return null;
				}
				
				Calendar checkDate = Calendar.getInstance();
				Plan plan = planDao.getPlan(Long.parseLong(planId));
				
				// 精确到日期
				SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
				Date _bDate = formate.parse(formate.format(plan.getBeginDate()));			
				checkDate.setTime(_bDate);
				
				checkDate.add(Calendar.MONTH, checkItem.getCircle());
				System.out.println(formate.format(checkDate.getTime()) + "--------------");
				//@temp save the checkDate into message
				 res.setMsg(formate.format(checkDate.getTime()));
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
	
     public ActionForward addPlanItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String checkId = request.getParameter("checkId");
			String planId = request.getParameter("planId");
			
			CheckItem checkItem = null;
			
			if(checkId != null && checkId.length() > 0) {
			   checkItem = planDao.getCheckItem(Long.parseLong(checkId));
			   String hql = "from PlanItem p where p.planId='" + planId + "' and p.checkItem.id = '" + checkId + "'";
			   List items = planDao.listPlanItems(hql);
			   
			   if(items != null && items.size() > 0) {
				  res.setSuccess(false);
				  res.setMsg("检查项目已存在");
				  return null;
			   }
			}
			else {
				res.setSuccess(false);
				return null;
			}
			
			PlanItem item = new PlanItem();
			item.setCheckItem(checkItem);
			item.setCircle(checkItem.getCircle());
			item.setCircleType(Calendar.MONTH);//changed from value of "0" -- 2011-8-11
			item.setPlanId(Long.parseLong(planId));
			item.setCheckItemCode(checkItem.getORDERCODE());
			item.setCheckItemName(checkItem.getORDERNAMEK());
			item.setState(0);
			item.setVisitState(0);
			//默认7天
			item.setCrossd(7);
			Calendar checkDate = Calendar.getInstance();
			Plan plan = planDao.getPlan(Long.parseLong(planId));
			
			// 精确到日期
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			Date _bDate = formate.parse(formate.format(plan.getBeginDate()));			
			checkDate.setTime(_bDate);
			
			checkDate.add(Calendar.MONTH, checkItem.getCircle());
			item.setPlanDate(checkDate.getTime());
			Long id = planDao.addPlanItem(item);
			this.refreshPlanState(Long.parseLong(planId), 0);
			item.setId(id);
			res.setData(JSONObject.fromObject(item,JSONValueProcessor.formatDate("yyyy-MM-dd")).toString());
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
	
	public ActionForward removePlanItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			//String checkId = request.getParameter("checkId");
			String planId = request.getParameter("itemId");
			
			if(planId == null || planId.length() < 1) {
				res.setSuccess(false);
				return null;
			}
			PlanItem item = planDao.getPlanItem(Long.parseLong(planId));			
			planDao.deletePlanItem(item);
			this.refreshPlanState(item.getPlanId(), -1);
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
	
	public ActionForward updatePlanItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			//String checkId = request.getParameter("checkId");
			String itemId = request.getParameter("planId");
			String pid = request.getParameter("pid");
			
			
			if(itemId == null || itemId.length() < 1) {
				res.setSuccess(false);
				return null;
			}
			
			Plan plan = planDao.getPlan(Long.parseLong(itemId));
			
			JSONArray changeDate = JSONArray.fromObject(request.getParameter("data"));
			JSONArray addDate = JSONArray.fromObject(request.getParameter("add"));
			JSONArray moveDate = JSONArray.fromObject(request.getParameter("move"));
			
			ArrayList<PlanItem> addDataList = new ArrayList<PlanItem>();
			ArrayList<PlanItem> reDataList = new ArrayList<PlanItem>();
			ArrayList<PlanItem> reDataList2 = new ArrayList<PlanItem>();
			ArrayList<PlanItem> reDataList3 = new ArrayList<PlanItem>();
			HashMap map = new HashMap();
			
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			
			for(int i = 0; i < addDate.size(); i++) {
				JSONObject idObj = (JSONObject) addDate.get(i);
				//PlanItem item = (PlanItem) addDateA[i];
				
				Integer cricle = Integer.parseInt(idObj.get("circle") + "");
				Integer circleType = Integer.parseInt(idObj.get("circleType") + "");
				Integer crossd = Integer.parseInt(idObj.get("crossd") + "");
				String comment = idObj.get("comment") + "";
				String chekItemCode = idObj.get("checkItemCode") + "";
				String chekItemName = idObj.get("checkItemName") + "";
				String checkId = idObj.get("checkId") + "";
				Object _obj = idObj.get("id");
				PlanItem item = new PlanItem();
				item.setCircle(cricle);
				item.setCircleType(circleType);
				item.setComment(comment);
				item.setCrossd(crossd);
				item.setCheckItemCode(chekItemCode);
				item.setCheckItemName(chekItemName);
				item.setState(0);
				item.setVisitState(0);
				item.setPlanId(Long.parseLong(itemId));
				
				if(checkId != null && checkId.length() > 0) {
					Long check_ID = null;
					try {
						check_ID = Long.parseLong(checkId);
						CheckItem citem = this.planDao.getCheckItem(check_ID);
						item.setCheckItem(citem);
					}
					catch(Exception e) {
						//ignore
					}
					
				}
				
				Calendar checkDate = Calendar.getInstance();
				
				Date _bDate = formate.parse(formate.format(plan.getBeginDate()));			
				checkDate.setTime(_bDate);
				checkDate.add(circleType, cricle);
				item.setPlanDate(checkDate.getTime());
				
				if(map.containsKey(chekItemCode + "|" + chekItemName)) {
				   Integer count = Integer.parseInt(map.get(chekItemCode + "|" + chekItemName) + "");
				   map.put(chekItemCode + "|" + chekItemName, count + 1);
				}
				else {
				   map.put(chekItemCode + "|" + chekItemName, 1);
				   addDataList.add(item);
				}
				
				//planDao.updatePlanItem(item);				
			}
			
			for(int i = 0; i < moveDate.size(); i++) {
				JSONObject idObj = (JSONObject) moveDate.get(i);
				Integer cricle = Integer.parseInt(idObj.get("circle") + "");
				Integer circleType = Integer.parseInt(idObj.get("circleType") + "");
				Integer crossd = Integer.parseInt(idObj.get("crossd") + "");
				String comment = idObj.get("comment") + "";
				String chekItemCode = idObj.get("checkItemCode") + "";
				String chekItemName = idObj.get("checkItemName") + "";
				String checkId = idObj.get("checkId") + "";
				Object _obj = idObj.get("id");
				PlanItem item = null;
				Integer count = null;
				
				if(map.containsKey(chekItemCode + "|" + chekItemName)) {
				   count = Integer.parseInt(map.get(chekItemCode + "|" + chekItemName) + "");
				   
				   if(count > 1) {
					   map.put(chekItemCode + "|" + chekItemName, count - 1);
					   continue;
				   }				   
				}

				item = new PlanItem();
				item.setCircle(cricle);
				item.setCircleType(circleType);
				item.setComment(comment);
				item.setCrossd(crossd);
				item.setCheckItemCode(chekItemCode);
				item.setCheckItemName(chekItemName);
				item.setState(0);
				item.setVisitState(0);
				item.setPlanId(Long.parseLong(itemId));
			
				try {
					item.setId(Long.parseLong(idObj.get("id") + ""));
				}
                catch(Exception e) {
                	// ignore
                }
				
				if(checkId != null && checkId.length() > 0) {
					CheckItem citem = this.planDao.getCheckItem(Long.parseLong(checkId));
					item.setCheckItem(citem);
				}
				
				reDataList.add(item);
				
				//planDao.deletePlanItem(item);	
			}
			
			String message = "";
			
			for(PlanItem item : addDataList) {				
				List list = this.planDao.listPlanItems("from PlanItem where planId= '" + itemId + "' and checkItemCode='" + item.getCheckItemCode() + "' and checkItemName='" + item.getCheckItemName() + "'");
                boolean isCon = false;
                PlanItem _reItem = null;
                
			    for(PlanItem reItem : reDataList) {
					// 当reDataList和addDataList都有同一项,看此项是否是原始数据
				   if(reItem.getCheckItemCode().equals(item.getCheckItemCode()) && reItem.getCheckItemName().equals(item.getCheckItemName())) {					  
					  if(list.size() > 0) {
						  reDataList3.add(reItem);
						isCon = true;
					  }
					  else {
						  _reItem = reItem;
					  }
				   }
				   else {
					   _reItem = reItem;
				   }
			    }
			  
			    if(isCon) {
				   continue;
			    }
			   
			    Long id = planDao.updatePlanItem(item);
			    
			    if(message.indexOf(item.getCheckItemName()) < 0) {
					list = this.planDao.getListBySql("select * from t_planItem pi inner join t_plan p on pi.planId=p.id where p.patientId='" + pid + "' and pi.planDate='" + formate.format(item.getPlanDate())  + "' and pi.checkItemCode='" + item.getCheckItemCode() + "' and pi.checkItemName='" + item.getCheckItemName() + "'", 1,2);
					
					if(list != null && list.size() > 0) {
						message = message + item.getCheckItemName() + ",";
					}
				}
			    
			    if(_reItem != null) {
			    	_reItem.setId(id);
			    	reDataList2.add(_reItem);
			    }
			}
			
			 for(PlanItem reItem : reDataList) {
				 boolean isIn2 = false;
				 boolean isIn3 = false;
				 
				 for(PlanItem reItem2 : reDataList2) {
					 if(reItem.getCheckItemCode().equals(reItem2.getCheckItemCode()) && reItem.getCheckItemName().equals(reItem2.getCheckItemName())) {
						 reItem = reItem2;
						 isIn2 = true;
					 }
				 }
				 if(isIn2) {
					 planDao.deletePlanItem(reItem);
					 continue;
				 }
				 else {
					 for(PlanItem reItem3 : reDataList3) {
						 if(reItem.getCheckItemCode().equals(reItem3.getCheckItemCode()) && reItem.getCheckItemName().equals(reItem3.getCheckItemName())) {
							 isIn3 = true;
							 break;
						 }
					 }					 
				 }
				 if(!isIn3) {
					  if(message.indexOf(reItem.getCheckItemName() + ",") > 0) {
							message.replaceAll(reItem.getCheckItemName() + ",", "");
						}
					  else if(message.indexOf(reItem.getCheckItemName()) > 0) {
							message.replaceAll(reItem.getCheckItemName(), "");
						}
					 
					 planDao.deletePlanItem(reItem);
				 }
			 }

			 for(int i = 0; i < changeDate.size(); i++) {
				JSONObject idObj = (JSONObject) changeDate.get(i);
				Integer cricle = Integer.parseInt(idObj.get("circle") + "");
				Integer circleType = Integer.parseInt(idObj.get("circleType") + "");
				Integer crossd = Integer.parseInt(idObj.get("crossd") + "");
				String comment = idObj.get("comment") + "";
				String chekItemCode = idObj.get("checkItemCode") + "";
				String chekItemName = idObj.get("checkItemName") + "";
				PlanItem item = null;
				
				List list = this.planDao.listPlanItems("from PlanItem where planId= '" + itemId + "' and checkItemCode='" + chekItemCode + "' and checkItemName='" + chekItemName + "'");
				   
			   if(list.size() < 1) {
				  continue;
			   }

				item = (PlanItem) list.get(0);

				item.setPlanId(Long.parseLong(itemId));
				item.setCheckItemCode(chekItemCode);
				item.setCheckItemName(chekItemName);
				item.setState(0);
				item.setVisitState(0);
				
				if(cricle ==  7 && circleType == Calendar.DAY_OF_YEAR) {
					cricle = 1;
					circleType = Calendar.WEEK_OF_YEAR;
				}
				else if(cricle ==  12 && circleType == Calendar.MONTH) {
					cricle = 1;
					circleType = Calendar.YEAR;
				}
				
				
				/*
				 * 跟发短信的标示相关   by cheng jiangyu 2011-9-20
				 */
				Date planDate = item.getPlanDate(); //获得该条随访项目原来的访问日期
				int crossDay = item.getCrossd();   //获得原来的 来访前后天数
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
				Date noticeDate = DateUtil.getBeforeAfterDate(sdf.format(planDate),crossDay);  //  获得原来该提醒的日期
				//---------------------------------------------------------------------------
				
				item.setCircle(cricle);
				item.setCircleType(circleType);
				item.setComment(comment);
				item.setCrossd(crossd);
				item.setCheckItemCode(chekItemCode);
				
				Calendar checkDate = Calendar.getInstance();
				// 精确到日期
				
				Date _bDate = formate.parse(formate.format(plan.getBeginDate()));			
				checkDate.setTime(_bDate);
				checkDate.add(circleType, cricle);
				item.setPlanDate(checkDate.getTime());
				item.setCrossd((crossd));
				//item.setState(Integer.parseInt(state));
				
				
				/*
				 * add by chengjiangyu 2011-9-20
				 */
				Date planDateNew = checkDate.getTime(); //获得该条随访项目新的访问日期
				int crossDayNew = crossd;   //获得新的 来访前后天数
				Date noticeDateNew = DateUtil.getBeforeAfterDate(sdf.format(planDateNew),crossDayNew);  //  获得新的该提醒的日期
				//对比两个提醒日期   如果该随访项目以前已经发过短信，并且新的提醒日期比原来的提醒日期要晚，则将item的发短信标识置0
				if(noticeDateNew.after(noticeDate) && 1==item.getMessageFlag()){
					item.setMessageFlag(0);
				}
				//---------------------------------------------------------------------------
				
				
				if(message.indexOf(item.getCheckItemName()) < 0) {
					list = this.planDao.getListBySql("select * from t_planItem pi inner join t_plan p on pi.planId=p.id where p.patientId='" + pid + "' and pi.planDate='" + formate.format(checkDate.getTime())  + "' and pi.checkItemCode='" + chekItemCode + "' and pi.checkItemName='" + chekItemName + "'", 1,2);
					
					if(list != null && list.size() > 0) {
						message = message + item.getCheckItemName() + ",";
					}
				}
				
				planDao.updatePlanItem(item);		
			}
			
			if(message.length() > 0) {
				res.setMsg(message + " 已在当天设定随访");
			}
			
			res.setSuccess(true);
			          
            String isAdd = request.getParameter("isAdd");
            plan.setIsUse(1);
            planDao.addPlan(plan);  
            if("true".equals(isAdd)) {
            	
            	PlanCount countp = planDao.getPlanCount(plan.getPatient().getId());
				 Long patientID = (plan.getPatient().getId());
				if(countp == null){
					countp = new PlanCount();
					countp.setPatientId(patientID);
				}
				
				countp.setPcount(Long.parseLong(plan.getPlanTime()));
				planDao.saveOrUpdatePlanCount(countp);
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
	
	public ActionForward editPlanItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			//String checkId = request.getParameter("checkId");
			String itemId = request.getParameter("planItemId");
			String circle = request.getParameter("circle");
			String comment = request.getParameter("comment");
			String state = request.getParameter("state");
			String visitState = request.getParameter("visitState");
			String circleType = request.getParameter("circleType");
			String crossd = request.getParameter("crossd");
			
			if(itemId == null || itemId.length() < 1) {
				res.setSuccess(false);
				return null;
			}
			
			PlanItem item = planDao.getPlanItem(Long.parseLong(itemId));
			
			if(item == null ) {
				res.setSuccess(false);
				return null;
			}
			Plan plan = planDao.getPlan(item.getPlanId());
			item.setCircle(Integer.parseInt(circle));
			item.setComment(comment);
			if(visitState != null && visitState.length() > 0)
				   item.setVisitState(Integer.parseInt(visitState));
			
			item.setCircleType(Integer.parseInt(circleType));
			Calendar checkDate = Calendar.getInstance();
			// 精确到日期
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			Date _bDate = formate.parse(formate.format(plan.getBeginDate()));			
			checkDate.setTime(_bDate);
			checkDate.add(Integer.parseInt(circleType), Integer.parseInt(circle));
			item.setPlanDate(checkDate.getTime());
			item.setCrossd(Integer.parseInt(crossd));
			
			// 门诊时间设置为来访时间，从plan enterDate中取
			/*if(item.getState() != Integer.parseInt(state) && ("1".equals(state) || "2".equals(state))) {
				Plan plan = planDao.getPlan(item.getPlanId());
			    
				if(plan != null)
				item.setVisitDate(plan.getEnterDate());
			}*/
			
			item.setState(Integer.parseInt(state));
			
			planDao.updatePlanItem(item);
			refreshPlanState(item.getPlanId(), Integer.parseInt(state));
			//planDao.deletePlanItem(planDao.getPlanItem(Long.parseLong(planId)));
			
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
	public ActionForward getPCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String obj =  request.getParameter("pid");
			
			res.setData(JSONObject.fromObject(planDao.getPlanCount(Long.parseLong(obj)),
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
	
	public ActionForward linkReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {

			String type = request.getParameter("type");	
			String repId = request.getParameter("repId");	
			String flag = request.getParameter("flag");
			
			String receiveDate = request.getParameter("receiveDate");
			String sectionNo = request.getParameter("sectionNo");	
			String testTypeNo = request.getParameter("testTypeNo");	
			String sampleNo = request.getParameter("sampleNo");
			String _paId = request.getParameter("paId");
			String parItemNo = request.getParameter("parItemNo");
			String itemName = request.getParameter("itemName");
			
			JSONArray changeDate = JSONArray.fromObject(request.getParameter("data"));
			for(int i = 0; i < changeDate.size(); i++) {
				Object idObj = changeDate.get(i);
				//JSONObject jsonObj = changeDate.getJSONObject(i);
				//String itemId = jsonObj.getString("id");
				PlanItem item = this.planDao.getPlanItem(Long.parseLong(idObj + ""));
				   /* var params = '?patientId=' + patientId + '&is_check_add=' + is_check_add+ '&itemName=' + _node.text 
				+ '&receiveDate=' + _node.attributes.receiveDate + '&sectionNo=' + _node.attributes.sectionNo 
				+ '&testTypeNo=' + _node.attributes.testTypeNo + '&sampleNo=' + _node.attributes.sampleNo 
				+ '&parItemNo=' + _node.attributes.parItemNo;		
	 return window.showModalDialog('../CheckReport/checkReport.html' + params, '', 'dialogWidth=1000px;dialogHeight=700px');*/
				String resultURL = "checkReport".equalsIgnoreCase(type) ? 
					"/module/InHospitalCase/Liver/CheckReport/checkReport.html?patientId=" + _paId + "&receiveDate=" + receiveDate + 
					"&sectionNo=" + sectionNo + "&testTypeNo=" + testTypeNo + "&sampleNo=" + sampleNo + "&parItemNo=" + parItemNo + "&itemName=" + itemName:
					"/module/checkreport/reportsheet_" + type + ".jsp?id=" + repId; 
				
				String orgURL = item.getResultURL();
				int orgState = item.getState();
				String reportDate = "";
				String orgDate = item.getReportDate();
				
				if("maidi".equalsIgnoreCase(type)) {
					 Maidi maidi = reportDao.getMaidiReport(Long.parseLong(repId));
					 reportDate = maidi.getCheckDate();
				}
				else if("Pacs".equalsIgnoreCase(type)){
					Pacs pacs = reportDao.getPacsReport(Long.parseLong(repId));
					reportDate = pacs.getCheckDate();
				}
				else if("checkReport".equalsIgnoreCase(type)) {
					reportDate = receiveDate;
				}
				
				if("add".equals(flag)) {
				  item.setResultURL(resultURL);
				  item.setState(2);
				  item.setVisitState(4);
				  item.setReportDate(reportDate);
				}
				else if("re".equals(flag)) {  //取消关联
				  // 1. item 修改。
					item.setResultURL(null);
				    item.setReportDate(null);
					item.setState(0);
					//item.setVisitState(0);
				  //item.setResultURL();
				  //item.setState();
				  //item.setReportDate();
				  //item.setVisitState(); 根据来访时间设置, 参考如下
					
					if(item.getVisitDate() == null) {
						item.setVisitState(0);
					}
					else {
					 Calendar _planDate = Calendar.getInstance();
					   _planDate.setTime(item.getPlanDate());
					   _planDate.add(Calendar.DATE, item.getCrossd());
					   
					   if(_planDate.getTime().before(item.getVisitDate())) {
						   item.setVisitState(3);
					   }
					   else {
						   _planDate.setTime(item.getPlanDate());
						   _planDate.add(Calendar.DATE, 0 - item.getCrossd());
						   
						   if(_planDate.getTime().after(item.getVisitDate())) {
							   item.setVisitState(1);
						   }
						   else {
							   item.setVisitState(2);
						   }
					   }
					}
					
				  // 2.plan 状态修改, 遍历 item.getplanid ,此plan下所有item..根据所有item情况设置plan状态
				}
				
				planDao.updatePlanItem(item);
				this.refreshPlanState(item.getPlanId(), -1);
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
	
	public static void main(String args[]) {
		PlanAction action = new PlanAction();
		try {
			action.autoPlanItem(null, null, null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ActionForward autoPlanItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			// 病人id
			Long paId = Long.parseLong(request.getParameter("paId"));
			// 化验时间
			String _date = request.getParameter("date");
			Date bDate = new Date();
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			try {
				bDate = formate.parse(_date);
			}
			catch(Exception e) {
				
			}
			// 化验单编号
			String checkCode = request.getParameter("checkCode");			
			String sql = "select pi.* from t_planItem pi inner join t_plan p on pi.planId = p.id inner join t_checkItem ci on pi.checkId=ci.id where p.patientId = '" + paId 
			   + "' and ci.ordercode='" + checkCode + "' and ('" + _date + "' between (select dateadd(day, 0-pi.crossd, pi.planDate)) and (select dateadd(day, pi.crossd, pi.planDate)))";
			List<PlanItem> list = this.planDao.isInPlan(sql);
			String resultURL = null;
			String type = request.getParameter("type");	
			String repId = request.getParameter("repId");	
			String receiveDate = request.getParameter("receiveDate");
			String sectionNo = request.getParameter("sectionNo");	
			String testTypeNo = request.getParameter("testTypeNo");	
			String sampleNo = request.getParameter("sampleNo");
			String _paId = request.getParameter("paId");
			String parItemNo = request.getParameter("parItemNo");
			String itemName = request.getParameter("itemName");
			
			if(list != null && list.size() > 0) {
			   PlanItem item = list.get(0);
			   item.setVisitDate(bDate);
			   item.setState(2);
			   item.setVisitState(4);
			   // resultURL 构建
			   resultURL = "checkReport".equalsIgnoreCase(type) ? 
						"/module/InHospitalCase/Liver/CheckReport/checkReport.html?patientId=" + _paId + "&receiveDate=" + receiveDate + 
						"&sectionNo=" + sectionNo + "&testTypeNo=" + testTypeNo + "&sampleNo=" + sampleNo + "&parItemNo=" + parItemNo + "&itemName=" + itemName:
						"/module/checkreport/reportsheet_" + type + ".jsp?id=" + repId; 
					
				String reportDate = "";
									
				if("maidi".equalsIgnoreCase(type)) {
					 Maidi maidi = reportDao.getMaidiReport(Long.parseLong(repId));
					 reportDate = maidi.getCheckDate();
				}
				else if("Pacs".equalsIgnoreCase(type)){
					Pacs pacs = reportDao.getPacsReport(Long.parseLong(repId));
					reportDate = pacs.getCheckDate();
				}
				else if("checkReport".equalsIgnoreCase(type)) {
					reportDate = receiveDate;
				}
				item.setResultURL(resultURL);
				item.setState(2);
				item.setVisitState(4);
				item.setReportDate(reportDate);
			}
			else {
				// 自动增加,不在item里, 时间最近
				sql = "select pi.* from t_planItem pi inner join t_plan p on pi.planId = p.id  where p.patientId = " + paId 
				   + "  and (" + _date + " between (select dateadd(day, 0-pi.crossd, pi.planDate)) and (select dateadd(day, pi.crossd, pi.planDate)))";
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
	
	public ActionForward saveUse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
            Plan plan = this.planDao.getPlan(id);
            plan.setIsUse(1);
            planDao.addPlan(plan);
            
            String isAdd = request.getParameter("isAdd");
           
            if("true".equals(isAdd)) {
				PlanCount countp = planDao.getPlanCount(plan.getPatient().getId());
				 Long patientID = Long.parseLong(request.getParameter("pid"));
				if(countp == null){
					countp = new PlanCount();
					countp.setPatientId(patientID);
				}
				
				countp.setPcount(Long.parseLong(plan.getPlanTime()));
				planDao.saveOrUpdatePlanCount(countp);
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
	
	public ActionForward linkOMC(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			
			String KID = request.getParameter("KID");
			String outRegno = request.getParameter("outRegno");
			String flag = request.getParameter("flag");
			String omcId = request.getParameter("omcId");
			OutOrMergencyCase omc = this.planDao.getOMC(Long.parseLong(omcId));
			
			String paId = request.getParameter("paId");
			String recordURL = "outCaseMain.html?KID=" + KID + "&PID=" + paId + "&outRegno=" + outRegno; 
			JSONArray changeDate = JSONArray.fromObject(request.getParameter("data"));
			
			for(int i = 0; i < changeDate.size(); i++) {
				Object idObj = changeDate.get(i);
				
				//String itemId = request.getParameter("itemId");
				
				PlanItem item = this.planDao.getPlanItem(Long.parseLong(idObj + ""));
				
				String planIds = omc.getPlanIds();
				String planItemIds = omc.getPlanItemIds();
				String planItems = omc.getPlanItems();
				
				if("add".equals(flag) && (planIds == null || planIds.indexOf("" + item.getPlanId()) < 0)) {
					planIds = planIds + "," + item.getPlanId();	
				}
				else if("re".equals(flag) && (planIds != null && planIds.indexOf("," + item.getPlanId()) > 0)){
					planIds = planIds.replace("," + item.getPlanId(), "");
				}
				
				if("add".equals(flag) && (planItemIds == null || planItemIds.indexOf("" + item.getId()) < 0)) {
					planItemIds = planItemIds + "," + item.getId();	
				}
				else if("re".equals(flag) && (planItemIds != null && planItemIds.indexOf("," + item.getId()) > 0)){
					planItemIds = planItemIds.replace("," + item.getId(), "");
				}
				
				if("add".equals(flag) && (planItems == null || planItems.indexOf("" + item.getCheckItem().getORDERNAMEK()) < 0)) {
					planItems = planItems + "," + item.getCheckItem().getORDERNAMEK();	
				}
				else if("re".equals(flag) && (planItems != null && planItems.indexOf("," + item.getCheckItem().getORDERNAMEK()) > 0)){
					planItems = planItems.replace("," + item.getCheckItem().getORDERNAMEK(), "");
				}
				
				planIds = planIds.replace("null", "");
				planItemIds = planItemIds.replace("null", "");
				planItems = planItems.replace("null", "");
				omc.setPlanIds(planIds);
				omc.setPlanItemIds(planItemIds);
				omc.setPlanItems(planItems);
				
				if("add".equals(flag)) {
				   item.setOutOMCid(omc.getId());
				   item.setState(1);
				   item.setVisitDate(omc.getActdate());
				   item.setRecordURL(recordURL);
				   Calendar _planDate = Calendar.getInstance();
				   _planDate.setTime(item.getPlanDate());
				   _planDate.add(Calendar.DATE, item.getCrossd());
				   
				   if(_planDate.getTime().before(omc.getActdate())) {
					   item.setVisitState(3);
				   }
				   else {
					   _planDate.setTime(item.getPlanDate());
					   _planDate.add(Calendar.DATE, 0 - item.getCrossd());
					   
					   if(_planDate.getTime().after(omc.getActdate())) {
						   item.setVisitState(1);
					   }
					   else {
						   item.setVisitState(2);
					   }
				   }
				}
				// @temp 是否取消关联？
				/*else {
					item.setState(0);
					item.setVisitState(0);
					item.setVisitDate(null);
					item.setOutOMCid(null);	
				}*/
				
				planDao.updatePlanItem(item);
				planDao.updateOMC(omc);
				refreshPlanState(item.getPlanId(), -1);
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
	
	// 随访状态：计划中为0
	/*if(value == 1) {
		return "<font color='#CDCD00'>提前来访</font>";
	}
	else if(value == 2) {
		return "<font color='#CDCD00'>按期来访</font>";
	}
	else if(value == 3) {
		return "<font color='#CDCD00'>超期来访</font>";
	}
	else if(value == 4) {
		return "<font color='green'>随访结束</font>";
	}
	else if(value == 5) {
		return "<font color='#B8B8B8'>超期未来访</font>";
	}		    	    
	else {
		return "<font color='red'>随访计划中</font>";
	}*/
	
	public void refreshPlanState(Long planId, int state) throws Exception {
		if(state == 1) {
			planDao.changeState(1, planId);
		}
		else {
		    List list = planDao.listPlanItems(planId);
		    
		    if(list == null || list.size() < 1) {
		    	return;
		    }
		    
		    int stateCount = 0;
		    
		    for(Object obj : list) {
		    	if(obj instanceof PlanItem) {
		    		PlanItem item = (PlanItem) obj;
		    		
		    		if(item.getState() == 1) {
		    			planDao.changeState(1, planId);
		    			return;
		    		}
		    		else if(item.getState() == 2) {
		    			stateCount++;
		    		}
		    	}
		    }
		    
		    if(stateCount == list.size() && stateCount > 0) {
		    	planDao.changeState(2, planId);
		    }
		    else if(stateCount < list.size() && stateCount > 0) {
		    	planDao.changeState(1, planId);
		    }
		    else {
		    	planDao.changeState(0, planId);
		    }
		}		
	}
	
	public ActionForward getPlanningItems(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Long paId = Long.parseLong(request.getParameter("paId"));
		Integer start = Integer.parseInt(request.getParameter("start"));
		Integer limit = Integer.parseInt(request.getParameter("limit"));
		Patient patient = new PatientDaoImpl().findById(paId);
		List<Criterion> querys = new ArrayList<Criterion>();
		querys.add(Restrictions.eq("isUse", 1));
		querys.add(Restrictions.eq("patient", patient));
		List<Plan> planList = planDao.getAllPlans(0, 1000, querys.toArray(new Criterion[] {}));
		//long planIds[] = new long[planList.size()];
		Map<Long,Plan> planMap = new HashMap<Long,Plan>();
		int i=0;
		Criterion exp = null;
		for(Plan plan : planList){
			//planIds[i] = plan.getId();
			planMap.put(plan.getId(), plan);
			Criterion se = Restrictions.eq("planId", plan.getId());
			if(0==i){
				exp = se;
			}else{
				exp = Restrictions.or(exp, se);
			}
			i++;
		}
		//int[] types = {Calendar.YEAR,Calendar.MONTH,Calendar.WEEK_OF_YEAR,Calendar.DAY_OF_YEAR};
		querys = new ArrayList<Criterion>();
		
		querys.add(exp);
		querys.add(Restrictions.eq("state",0));
		//querys.add(Restrictions.eq("circleType",types));
		List<PlanItem> items = planDao.getAllPlanItems(start, limit, querys.toArray(new Criterion[] {}));
		long total = planDao.getTotalItems(querys.toArray(new Criterion[] {}));
		
		
		JSONArray root = new JSONArray();
		for(PlanItem item : items){
			String circleType = "";
			switch(item.getCircleType()){
			case Calendar.DAY_OF_YEAR:
				circleType = "天";
				break;
			case Calendar.WEEK_OF_YEAR:
				circleType = "周";
				break;
			case Calendar.MONTH:
				circleType = "个月";
				break;
			case Calendar.YEAR:
				circleType = "年";
				break;
			}
			Plan plan = planMap.get(item.getPlanId());
			Calendar planDate = Calendar.getInstance();
			planDate.setTime(plan.getBeginDate());
			planDate.add(item.getCircleType(), item.getCircle());
			JSONObject js = new JSONObject();
			js.put("planTime", plan.getPlanTime());
			js.put("checkItem", item.getCheckItem().getORDERNAMEK());
			js.put("beginDate", plan.getBeginDate());
			js.put("circle", item.getCircle()+circleType);
			js.put("planDate",planDate.getTime());
			js.put("days", (planDate.getTimeInMillis() - plan.getBeginDate().getTime())/(24*3600*1000));
			js.put("comment", item.getComment());
			root.add(js);
		}
	
		JSONObject object = new JSONObject();
		object.put("root", root);
		object.put("total", total);
		out.println(object.toString());
		return null;
	}
	

	public ActionForward getCheckChildren(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = request.getParameter("code");
	
		PrintWriter out = response.getWriter();
		try {
			
			if(code != null && code.equalsIgnoreCase("root")) {
				code = "";
			}
			
			List list = planDao.getCheckChildren(code);
			JSONArray array = new JSONArray();
			JSONObject object = null;
			
			for (Object obj : list) {
				Object[] objArray = null;
				
				if(obj instanceof Object[]) {
					objArray = (Object[]) obj;
				}
				else {
					continue;
				}
				
				boolean isLeaf = this.isCheckLeaf(objArray[0] + "");

				object = new JSONObject();
				object.put("id", objArray[0]);
				object.put("text", objArray[1]);
				
				object.put("leaf", isLeaf);
//				if(isLeaf) {
//			    	object.put("checked", false);
//				}
				
				if(isLeaf){
					object.put("iconCls", "icon-cmp");
				}
				
				array.add(object);
			}
			out.println(array);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}	
	
	private boolean isCheckLeaf(String code) {
		if(code == null || code.length() < 2) {
		   return false;
		}
		
		List list = null;
		
		if(code.length() == 2) {
		   try {
			list = this.planDao.getCheckChildren(code);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(list == null || list.size() < 1) {
				return true;
			}
			
			boolean isLeaf = false;
			
			for(Object obj : list) {
				if(obj instanceof Object[]) {
					Object[] _obja = (Object[]) obj;
					
					if(_obja[1] == null || _obja[1].equals("")) {
						isLeaf = true;
					}
					else {
						isLeaf = false;
					}
				}
			}
			
			return isLeaf;
		}
		
		try {
			list = planDao.getCheckItems(code);
		} catch (Exception e) {
			//ignore
		}
		
		if(list == null || list.size() < 1) {
			return true;
		}
		
		return false;
	}

	/*
	 * 添加随访检查项目到模板的方法   by cheng jiangyu 2011-09-02 
	 */
	public ActionForward addPlanModule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		res.setSuccess(true);
		//String isAdd = (String)request.getParameter("moduleName");  //获得该计划模板的名称参数
		String moduleName = request.getParameter("moduleName");  //获得该计划模板的名称参数
		JSONArray checkItemsIdArr = JSONArray.fromObject(request.getParameter("checkItemsIdArr"));//获得前台传过来的随访检查项目planItemId数组
		
		try {
			PlanModule planModule = null;
				planModule = new PlanModule();
				if(moduleName!=null&&!"".equals(moduleName)){
					planModule.setModuleName(moduleName);
				}
				User user = (User) request.getSession().getAttribute("user");
				if(user!=null&&user.getId()!=null){
					planModule.setUser(user);
				}
				Date date = new Date();
				SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
				planModule.setCreateDate(formate.format(date));
				planDao.saveOrUpdatePlanModule(planModule);
			PlanModuleItem planModuleItem = null;
			
			for(int i = 0; i < checkItemsIdArr.size(); i++) {
				planModuleItem = new PlanModuleItem();
				JSONObject idObj = (JSONObject) checkItemsIdArr.get(i);
				
				try {
					planModuleItem.setCheckId(Long.parseLong(idObj.get("checkId")+ ""));	
				}
				catch(Exception e) {
					planModuleItem.setCheckId(new Long(0));
					// ignore
				}
				 
				planModuleItem.setCheckItemCode(idObj.get("checkItemCode")+ "");
				planModuleItem.setCheckItemName(idObj.get("checkItemName")+ "");
				planModuleItem.setCircle(Integer.parseInt(idObj.get("circle")+ ""));
				planModuleItem.setCircleType(Integer.parseInt(idObj.get("circleType")+ ""));
				planModuleItem.setComment(idObj.get("comment")+ "");
				planModuleItem.setModuleId(planModule.getId());
				planDao.saveOrUpdatePlanModuleItem(planModuleItem);
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
	
	/*
	 * 获得医生所有的随访计划模板    by cheng jiangyu 2011-09-05
	 */
	public ActionForward getAllModules(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Integer start = Integer.parseInt(request.getParameter("start"));
		Integer limit = Integer.parseInt(request.getParameter("limit"));
		String moduleName = request.getParameter("moduleName");
		String createDate = request.getParameter("createDate");
		List<Criterion> querys = new ArrayList<Criterion>();
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		Criterion criteria = null;
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user != null && user.getId()!=null){
				criteria = Restrictions.eq("user", user);
				querys.add(criteria);
			}
			if(moduleName != null && moduleName.length() > 0) {
				querys.add(Restrictions.like("moduleName", "%" + moduleName + "%"));
			}
			if(createDate != null && createDate.length() > 0) {
				String dataStr = null;
				
				try {
					dataStr = formate.format(formate.parse(createDate));
				}
				catch(Exception e) {
					dataStr = createDate;
				}
				
				querys.add(Restrictions.like("createDate", "%" + dataStr + "%"));	
			}
			
			List<PlanModule> pats = planDao.getAllPlanModules(start,limit, querys.toArray(new Criterion[] {}));
			long total = planDao.getTotalModules(querys
					.toArray(new Criterion[] {}));
			JSONObject object = new JSONObject();
			object.put("root", pats);
			object.put("total", total);
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;			
	}
	
	
	//删除随访模板  By cheng jiangyu 2011-09-05
	public ActionForward deleteModule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		res.setSuccess(true);
		JSONArray ids = JSONArray.fromObject(request.getParameter("ids"));//获得前台传过来的随访模板Id参数
		StringBuffer idBuffer = new StringBuffer();
		try {
			for(int i=0;i<ids.size();i++){  //将模板Id拼接成Id字符串 形如 1,2,3，
				Object id = ids.get(i);
				idBuffer.append(id+",");
			}
			String idStr = idBuffer.toString().substring(0, idBuffer.toString().length()-1);
			planDao.deletePlanModule(idStr);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	
	//删除模板项目  By cheng jiangyu 2011-09-05
	public ActionForward deleteModuleItems(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		res.setSuccess(true);
		String moduleId = request.getParameter("moduleId");  //获得该计划模板的id
		JSONArray moduleItemIds = JSONArray.fromObject(request.getParameter("moduleItemIds"));//获得前台传过来的模板项目moduleItemId数组
		StringBuffer idBuffer = new StringBuffer();
		try {
			for(int i=0;i<moduleItemIds.size();i++){  //将模板Id拼接成Id字符串 形如 1,2,3，
				Object id = moduleItemIds.get(i);
				idBuffer.append(id+",");
			}
			String idStr = idBuffer.toString().substring(0, idBuffer.toString().length()-1);
			planDao.deletePlanModuleItems(Long.parseLong(moduleId), idStr);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	
	/*
	 * 获得某一个随访模板的所有模板项目
	 */
	public ActionForward getAllModuleItems(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    String moduleId = request.getParameter("moduleId");  //获得随访模板Id参数
		
		if(moduleId == null || moduleId.length()<0) {
			return null;
		}
		PrintWriter out = response.getWriter();
		Integer start = Integer.parseInt(request.getParameter("start"));
		Integer limit = Integer.parseInt(request.getParameter("limit"));
		List<Criterion> querys = new ArrayList<Criterion>();
		if(moduleId != null && moduleId.length() > 0) {
			querys.add(Restrictions.eq("moduleId", Long.parseLong(moduleId)));
		}
		try {
			List<PlanModuleItem> pats = planDao.getAllModuleItems(start,limit, querys.toArray(new Criterion[] {}));
			long total = planDao.getTotalModuleItems(querys.toArray(new Criterion[] {}));
			JSONObject object = new JSONObject();
			object.put("root", pats);
			object.put("total", total);
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;			
	}

	
	/*
	 * 添加模板到随访项目
	 */
	public ActionForward getCheckItemsByModule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		JSONArray moduleIds = JSONArray.fromObject(request.getParameter("moduleIds")); //获得随访模板Ids参数
		if(moduleIds == null || moduleIds.size()==0) {
			return null;
		}
		StringBuffer idBuffer = new StringBuffer();
		for(int i=0;i<moduleIds.size();i++){  //将模板Id拼接成Id字符串 形如 1,2,3，
				Object id = moduleIds.get(i);
				idBuffer.append(id+",");
			}
		String idStr = idBuffer.toString().substring(0, idBuffer.toString().length()-1);
		
		try {
			List<PlanModuleItem> pats = planDao.getItemsByModule(idStr);
			res.setData(JSONArray.fromObject(pats,JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss")).toString());
			res.setSuccess(true);
			/*JSONObject object = new JSONObject();
			object.put("root", pats);
			out.println(object.toString());*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;			
	}

	/*
	 * 动态修改Module名称
	 */
	public ActionForward updateModule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String id = request.getParameter("id");
			String field = request.getParameter("field");  //修改的字段名称
			String value = request.getParameter("value"); //修改的字段值
			String sql = "";
			if("moduleName".equals(field)){
				sql = "update t_module set moduleName='"+value+"' where id="+Long.parseLong(id);
			}
			planDao.updateModuleItem(sql);
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
	
	
	
	/*
	 * 动态修改ModuleItem
	 */
	public ActionForward updateModuleItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String id = request.getParameter("id");
			String field = request.getParameter("field");  //修改的字段名称
			String value = request.getParameter("value"); //修改的字段值
			String sql = "";
			if("circle".equals(field)){
				sql ="update t_moduleItems set circle="+Integer.parseInt(value)+" where id="+Long.parseLong(id);
			}else if("circleType".equals(field)){
				sql ="update t_moduleItems set circleType="+Integer.parseInt(value)+" where id="+Long.parseLong(id);
			}
			planDao.updateModuleItem(sql);
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



