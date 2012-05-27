package com.juncsoft.KLJY.planhis.action;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.planhis.services.PlanHisServices;
import com.juncsoft.KLJY.planhis.services.impl.PlanHisServicesImpl;
import com.juncsoft.KLJY.planhis.util.NearPlan;


public class PlanHisAction  extends DispatchAction {
	private PlanHisServices phservice = new PlanHisServicesImpl();
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
	 public ActionForward getPlanList(ActionMapping mapping,
		 	ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			
			String pTenId = request.getParameter("patientId");
			System.out.println("根据病人Id获取病人的随访计划，id:"+pTenId);
			List<NearPlan> nearPlans  = phservice.getNearPlanitemsByPId(pTenId);
			request.setAttribute("nearPlans", nearPlans);
			System.out.println("数据找出来了！");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.findForward("list");
	}
}



