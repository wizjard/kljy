package com.juncsoft.KLJY.planhis.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyCase;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.plan.dao.PlanDao;
import com.juncsoft.KLJY.plan.entity.CheckItem;
import com.juncsoft.KLJY.plan.entity.Plan;
import com.juncsoft.KLJY.plan.entity.PlanCount;
import com.juncsoft.KLJY.plan.entity.PlanItem;
import com.juncsoft.KLJY.plan.entity.PlanItemGroup;

import com.juncsoft.KLJY.plan.entity.PlanModule;
import com.juncsoft.KLJY.plan.entity.PlanModuleItem;
import com.juncsoft.KLJY.planhis.services.PlanHisServices;

import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DateUtil;

import com.juncsoft.KLJY.util.DatabaseUtil;
/**
 * 工具类 用以传输复合参数到action
 * @author suyong
 *
 */
public class NearPlan   {
	private Date planDate;	
	private Date visitDate;
	private List<PlanItem> planItems;
	private boolean canChange;
	
	
	public boolean isCanChange() {
		return canChange;
	}
	public void setCanChange(boolean canChange) {
		this.canChange = canChange;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public List<PlanItem> getPlanItems() {
		return planItems;
	}
	public void setPlanItems(List<PlanItem> planItems) {
		this.planItems = planItems;
	}
	
	
	

	
	
}
