package com.juncsoft.KLJY.planhis.dao;

import java.util.List;
import java.util.Map;
import java.util.Date;

import org.hibernate.criterion.Criterion;

import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyCase;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.plan.entity.CheckItem;
import com.juncsoft.KLJY.plan.entity.Plan;
import com.juncsoft.KLJY.plan.entity.PlanCount;
import com.juncsoft.KLJY.plan.entity.PlanItem;
import com.juncsoft.KLJY.plan.entity.PlanItemGroup;
import com.juncsoft.KLJY.plan.entity.PlanModule;
import com.juncsoft.KLJY.plan.entity.PlanModuleItem;

public interface PlanHisDao {
	
	public List<PlanItem> findPlanItemByPlanIdAndTime(Long pid) throws Exception;
	public List<Plan> findPlanByPlanIdAndTime(String qianhou,Long pid) throws Exception;
	
	

}
