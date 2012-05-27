package com.juncsoft.KLJY.planhis.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;

import com.juncsoft.KLJY.plan.entity.Plan;
import com.juncsoft.KLJY.plan.entity.PlanItem;
import com.juncsoft.KLJY.planhis.dao.PlanHisDao;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class PlanHisDaoImpl implements PlanHisDao {
	private static String TABLE_PLAN_ITEM = "PlanItem";
	private static String TABLE_PLAN = "Plan";
	private static String TABLE_PLAN_MODULE = "t_module";
	private static String TABLE_PLAN_MODULE_ITEM = "t_moduleItems";

	public List<PlanItem> findPlanItemByPlanIdAndTime(Long id) throws Exception {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// Date min = df.parse("2006-01-01");
		Date currentDate = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(currentDate);
		cal.add(GregorianCalendar.MONTH, 6);// 在月份上加6
		Date maxDate = cal.getTime();

		GregorianCalendar cal2 = new GregorianCalendar();
		cal2.setTime(currentDate);
		cal2.add(GregorianCalendar.MONTH, -6);// 在月份上-6
		Date minDate = cal2.getTime();

		String hql = "from PlanItem where planId = '" + id+"'";
		Session session = null;
		session = DatabaseUtil.getSession();
		List<PlanItem> pls = session.createQuery(hql).list();
		return pls;
	}

	public List<Plan> findPlanByPlanIdAndTime(String qianhou,Long id) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date currentDate = new Date();
		String nowTime = sdf.format(currentDate);
		
		

		
		String hql = "";
		if("前".equals(qianhou)){
			GregorianCalendar cal2 = new GregorianCalendar();
			cal2.setTime(currentDate);
			cal2.add(GregorianCalendar.MONTH, -6);// 在月份上-6
			Date minDate = cal2.getTime();
			String s_minDate = sdf.format(minDate);
			/* hql = "from Plan where patientid = '" + id
			 + "' and (beginDate between  '" + s_minDate + "' AND '" + nowTime+"')";*/
			 
			 hql = "from Plan p where patientid='"+id+"' and p.id in(select distinct pi.planId from PlanItem pi where planDate between  '" + s_minDate + "' AND '" + nowTime+"')order by p.beginDate";
		}else{
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(currentDate);
			cal.add(GregorianCalendar.MONTH, 6);// 在月份上加6
			Date maxDate = cal.getTime();
			String s_maxDate = sdf.format(maxDate);
			 hql = "from Plan p where patientid='"+id+"' and p.id in(select distinct pi.planId from PlanItem pi where planDate between  '" + nowTime + "' AND '" + s_maxDate+"')order by p.beginDate";
		}
		Session session = null;
		session = DatabaseUtil.getSession();
		List<Plan> pls = session.createQuery(hql).list();
		return pls;
	}

}
