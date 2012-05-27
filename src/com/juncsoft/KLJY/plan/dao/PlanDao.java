package com.juncsoft.KLJY.plan.dao;

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

public interface PlanDao {
	public Long addCheckItem(CheckItem item) throws Exception;
	public void deleteCheckItem(CheckItem item) throws Exception;
	public void updateCheckItem(CheckItem item) throws Exception;
	public CheckItem getCheckItem(Long id) throws Exception;	
	
	public Long addPlanItem(PlanItem item) throws Exception;
	public void deletePlanItem(PlanItem item) throws Exception;
	public Long updatePlanItem(PlanItem item) throws Exception;
	public PlanItem getPlanItem(Long id) throws Exception;	
	public List listPlanItems(Long planId) throws Exception;
	public List listPlanItems(String hql) throws Exception;
	
	public Long addPlan(Plan plan) throws Exception;	
	public void deletePlan(Long planId) throws Exception;
	public void deletePlanGroup(Long planId, Integer cricle, Integer cricleType) throws Exception;
	
	public void updatePlan(Plan plan) throws Exception;
	public void changeState(int state, Long planId) throws Exception;
	public Plan getPlan(Long id) throws Exception;
	public PlanModule getPlanModule(Long id) throws Exception;
	
	public List<Plan> getAllPlans(Integer start,
			Integer limit, Criterion... criterions) throws Exception;
	public long getTotalPlan(Criterion... criterions) throws Exception;
	public List<CheckItem> getAllCheckItems(Integer start,
			Integer limit, Criterion... criterions) throws Exception;
	public List<PlanItem> getAllPlanItems(Integer start,
			Integer limit, Criterion... criterions) throws Exception;
	public long getTotalChecks(Criterion... criterions) throws Exception;
	public long getTotalItems(Criterion... criterions) throws Exception;
	
//	public Map findAllDayInWeek(Long planId, String date);//deleted by DongCaho, 2011-8-10
	public List<Patient> getPatients(Criterion... criterions) throws Exception;
	public OutOrMergencyCase getOMC(Long id) throws Exception;
	public void updateOMC(OutOrMergencyCase item) throws Exception;
	
	public void saveOrUpdatePlanCount(PlanCount planc) throws Exception;	
	public PlanCount getPlanCount(Long id) throws Exception;
	
	
	public int getPlanTime(Long id);
	
	public List<Plan> getPlansByPatient(Long paId);
	public List<Plan> getPlansByPatient(Long paId, String order);
	public List isInPlan(String sql) throws Exception;
	public List<PlanItemGroup> getListBySql(String sql, int limit, int start) throws Exception;
	public int getTotalBySql(String sql)  throws Exception;
	public List<PlanItem> getPlanItemsByHQL(String hql) throws Exception;
	
	
	public Date getPlanItemPlanDate(PlanItem item) throws Exception;
	public Date getPlanItemPlanDate(Date beginDate, PlanItem item) throws Exception;

	public List getCheckChildren(String code) throws Exception;
	public List getCheckItems(String code) throws Exception;
	
	public Long addPlanModule(PlanModule planModule) throws Exception;
	public Long addPlanModuleItem(PlanModuleItem planModuleItem) throws Exception;
	public Long saveOrUpdatePlanModule(PlanModule planModule) throws Exception;
	public Long saveOrUpdatePlanModuleItem(PlanModuleItem planModuleItem) throws Exception;
	
	public List<PlanModule> getAllPlanModules(Integer start,Integer limit, Criterion... criterions) throws Exception;
	public void deletePlanModule(String moduleIds) throws Exception;
	public void deletePlanModuleItems(Long moduleId,String moduleItemIds) throws Exception;
	public long getTotalModules(Criterion... criterions) throws Exception;
	public List<PlanModuleItem> getAllModuleItems(Integer start,Integer limit, Criterion... criterions) throws Exception;
	public long getTotalModuleItems(Criterion... criterions) throws Exception;
	public List<PlanModuleItem> getItemsByModule(String idStr) throws Exception;
	public void updateModuleItem(String sql) throws Exception;
	//public void updateModule(String sql) throws Exception;
	

}
