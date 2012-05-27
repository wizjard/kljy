package com.juncsoft.KLJY.plan.impl;

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

import com.juncsoft.KLJY.util.DateUtil;

import com.juncsoft.KLJY.util.DatabaseUtil;

public class PlanDaoImpl implements PlanDao {
private static String TABLE_PLAN_ITEM="PlanItem";
private static String TABLE_PLAN="Plan";
private static String TABLE_PLAN_MODULE="t_module";
private static String TABLE_PLAN_MODULE_ITEM="t_moduleItems";

	public Long addCheckItem(CheckItem item) throws Exception {
		Session session = null;
		Transaction tran = null;
		Long id = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			id = (Long) session.save(item);
			item.setId(id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}

	public Long addPlan(Plan plan) throws Exception {
		Session session = null;
		Transaction tran = null;
		Long id = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			
			if(plan.getId() != null && plan.getId() > 0) {
			   session.update(plan);
			   id = plan.getId();
			}
			else {
				id = (Long) session.save(plan);	
			}
		
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}

	public Long addPlanItem(PlanItem item) throws Exception {
		Session session = null;
		Transaction tran = null;
		Long id = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if(item.getId() == null || item.getId() < 1) {
				id = (Long) session.save(item);	
			}
			else {
				session.saveOrUpdate(item);
				id = item.getId();
			}
			
			item.setId(id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}



	public CheckItem getCheckItem(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		CheckItem item = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			item = (CheckItem) session.get(CheckItem.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return item;
	}

	public Plan getPlan(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		Plan plan = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			plan = (Plan) session.get(Plan.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return plan;
	}

	public PlanItem getPlanItem(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		PlanItem item = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			item = (PlanItem) session.get(PlanItem.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return item;
	}
	
	
	
	public PlanModule getPlanModule(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		PlanModule item = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			item = (PlanModule) session.get(PlanModule.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return item;
	}

	public List<PlanItem> getPlanItemsByHQL(String hql) throws Exception {
		Session session = null;
		session = DatabaseUtil.getSession();
		return session.createQuery(hql).list();
	}
	
	public void changeState(int state, Long planId) throws Exception {
		Session session = null;
		Transaction tran = null;
		//plan.setState(state);
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.createSQLQuery("update t_plan set state =" + state + " where id=" + planId).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void deleteCheckItem(CheckItem item) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(item);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}
	
	public void deletePlanGroup(Long planId, Integer cricle, Integer cricleType) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			//pcount - 1
			//Plan plan = this.getPlan(planId);			
			session.createQuery("delete from "+TABLE_PLAN_ITEM+" where planId='"+planId + "' and circle='" + cricle + "' and circleType='" + cricleType + "'").executeUpdate();
			tran.commit();
			List planItems = session.createQuery("from "+TABLE_PLAN_ITEM+" where planId='"+planId + "'").list();
			
			if(planItems.size() < 1) {
				this.deletePlan(planId);
			}
			
			
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void deletePlan(Long planId) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			//pcount - 1
			Plan plan = this.getPlan(planId);
			String paId = plan.getPatient().getId() + "";
			
			session.createSQLQuery("update t_PlanCount set pcount=pcount-1 where patientId='" + paId + "'").executeUpdate();
			//reset planTime
			session.createSQLQuery("update t_Plan set planTime=planTime-1 where patientId='" + paId + "' and planTime > " + plan.getPlanTime()).executeUpdate();
			//session.delete(plan);
			session.createQuery("delete from "+TABLE_PLAN+" where id='"+planId + "'").executeUpdate();
			session.createQuery("delete from "+TABLE_PLAN_ITEM+" where planId='"+planId + "'").executeUpdate();
			
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void deletePlanItem(PlanItem item) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(item);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void updateCheckItem(CheckItem item) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.update(item);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void updatePlan(Plan plan) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.update(plan);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public Long updatePlanItem(PlanItem item) throws Exception {
		Session session = null;
		Transaction tran = null;
		Long id = item.getId();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			
			if(id == null) {
			   id = (Long) session.save(item);
			}
			else {
			   session.saveOrUpdate(item);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
			
		}
		
		return id;
	}
	
	public void updateOMC(OutOrMergencyCase item) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.update(item);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Plan> getAllPlans(Integer start,
			Integer limit, Criterion... criterions) throws Exception {
		List<Plan> list;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session
					.createCriteria(Plan.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			list = criteria.setFirstResult(start).setMaxResults(limit)
					.addOrder(Order.desc("id")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public List isInPlan(String sql) throws Exception {		
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		List list = session.createSQLQuery(sql).list();
        return list;
	}
	
	public long getTotalPlan(Criterion... criterions) throws Exception {
		long rst = 0;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session
					.createCriteria(Plan.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			
			if(criteria == null) {
				return 0;
			}
			
			Criteria cr = criteria.setProjection(Projections.rowCount());
			Object obj = cr.uniqueResult();
			
			if(obj == null) {
				return 0;
			}
			
			rst = (Long) obj;
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}
	
	@SuppressWarnings("unchecked")
	public List<PlanItem> getAllPlanItems(Integer start,
			Integer limit, Criterion... criterions) throws Exception {
		List<PlanItem> list;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			
			Criteria criteria = session
					.createCriteria(PlanItem.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			list = criteria.setFirstResult(start).setMaxResults(limit)
					.addOrder(Order.desc("id")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
	
	
	/*
	 * 
	 * 获得所有的planItem列表   by cheng jiangyu 2011-9-20
	 * 
	 */
	public List<PlanItem> getAllPlanItems() throws Exception {
		List<PlanItem> list;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			list = session.createQuery("from PlanItem").list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
	
	
	

	public long getTotalItems(Criterion... criterions) throws Exception {
		long rst = 0;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session
					.createCriteria(PlanItem.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			
			if(criteria == null) {
				return 0;
			}
			
			Criteria cr = criteria.setProjection(Projections.rowCount());
			Object obj = cr.uniqueResult();
			
			if(obj == null) {
				return 0;
			}
			
			rst = (Long) obj;
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}
	
	@SuppressWarnings("unchecked")
	public List<CheckItem> getAllCheckItems(Integer start,
			Integer limit, Criterion... criterions) throws Exception {
		List<CheckItem> list;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session
					.createCriteria(CheckItem.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			list = criteria.setFirstResult(start).setMaxResults(limit)
					.addOrder(Order.desc("id")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public long getTotalChecks(Criterion... criterions) throws Exception {
		long rst = 0;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session
					.createCriteria(CheckItem.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			
			if(criteria == null) {
				return 0;
			}
			
			Criteria cr = criteria.setProjection(Projections.rowCount());
			Object obj = cr.uniqueResult();
			
			if(obj == null) {
				return 0;
			}
			
			rst = (Long) obj;
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}
	
	public List<Plan> getPlansByPatient(Long paId){
		return this.getPlansByPatient(paId, null);
	}
	
	public List<Plan> getPlansByPatient(Long paId, String order){
		Session session = null;
		Transaction tran = null;
		List<Plan> plans = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
//			String hql = "from Plan where patientId=" + paId;
//			plans = session.createQuery(hql).list();
			Patient patient = (Patient) session.get(Patient.class, paId);
			Criteria criteria = session.createCriteria(Plan.class);			
			criteria.add(Restrictions.eq("patient", patient)).add(Restrictions.eq("isUse", 1));			
			plans = criteria.addOrder(Order.desc((order == null || order.length() < 1) ? "id" : order)).list();
			
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			throw new RuntimeException("根据患者ID获取随访计划时发生异常", e);
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return plans;
	}
/*
 * deleted by DongChao, 2011-8-10
	public Map findAllDayInWeek(Long planId, String date) {
		Session session = null;
		Transaction tran = null;
		
		// date = "2011-06-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			throw new RuntimeException("对一个月份中的每一天按周进行分类出错", e);
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}
*/
	
	
	public Map findPlanDate() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List<Map> dateList = new ArrayList<Map>();
		try {
			con = DatabaseUtil.getConnection();
			st = con.createStatement();
			Date time = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String date = sdf.format(time) + "-01";
			String dateSql = "select * from t_PlanDateList";
			rs = st.executeQuery(dateSql);
			while (rs.next()) {
				Map mp = new HashMap();
				mp.put("dateList", rs.getString("dateList"));
				mp.put("dateValue", rs.getString("dateValue"));
				dateList.add(mp);
			}
			map.put("dateList", dateList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取随访月份列表", e);
		} finally {
			try {
				DatabaseUtil.closeResource(con, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public List listPlanItems(Long planId) throws Exception {
		List items = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Criteria criteria = session
					.createCriteria(PlanItem.class);			
			criteria.add(Restrictions.eq("planId", planId));			
			items = criteria.addOrder(Order.desc("id")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return items;
	}
	
	public List listPlanItems(String hql) throws Exception {
		List items = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			items = session.createQuery(hql).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return items;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Patient> getPatients(Criterion... criterions) throws Exception {
		List<Patient> pat = new ArrayList<Patient>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Criteria criteria = session.createCriteria(Patient.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			pat = criteria.list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return pat;
	}
	
	public OutOrMergencyCase getOMC(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		OutOrMergencyCase item = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			item = (OutOrMergencyCase) session.get(OutOrMergencyCase.class, id);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return item;
	}

	public PlanCount getPlanCount(Long id)throws Exception{
		List items = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			items = session.createQuery("from PlanCount where patientId=" + id).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		
		if(items == null || items.size() < 1) {
			return null;
		}
		return (PlanCount) items.get(0);
	}

	public void saveOrUpdatePlanCount(PlanCount planc)throws Exception{
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.saveOrUpdate(planc);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public int getPlanTime(Long id) {
		List items = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			items = session.createSQLQuery("select planTime from t_Plan where patientId=" + id +" order by planTime desc").list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw new RuntimeException("",e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		if(items == null || items.size() < 1 || items.get(0)==null) {
			return 1;
		}else{
			return Integer.parseInt(items.get(0).toString())+1;
		}
	}
	
	public List<PlanItemGroup> getListBySql(String sql, int limit, int start) throws Exception {
		Session session = DatabaseUtil.getSession();
		return session.createSQLQuery(sql).setFirstResult(start).setMaxResults(limit).list();
	}
	
	public int getTotalBySql(String sql)  throws Exception {
		Session session = DatabaseUtil.getSession();
		//System.out.print("addaaaaaaaaaaa");
		return session.createSQLQuery(sql).list().size();
		
	}
	
	public Date getPlanItemPlanDate(PlanItem item) throws Exception{
		Plan plan = this.getPlan(item.getPlanId());
		Calendar checkDate = Calendar.getInstance();
		checkDate.setTime(plan.getBeginDate());
		Integer circleType = item.getCircleType();
		circleType = circleType == null ? Calendar.WEEK_OF_YEAR : circleType;
		checkDate.add(circleType, item.getCircle());
		return checkDate.getTime();
	}
	
	public Date getPlanItemPlanDate(Date beginDate, PlanItem item) throws Exception{
		Calendar checkDate = Calendar.getInstance();
		checkDate.setTime(beginDate);
		Integer circleType = item.getCircleType();
		circleType = circleType == null ? Calendar.WEEK_OF_YEAR : circleType;
		checkDate.add(circleType, item.getCircle());
		return checkDate.getTime();
	}

	public List getCheckItems(String code) throws Exception {
		Session session = null;
		Transaction tran = null;
		List list = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();

			String sql = "select convert(varchar, id) + '_' + ordercode, ordernamek, ordercode,id from t_checkItem where slipno= '" +  code + "'";
			list = session.createSQLQuery(sql).list();
			
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		
		return list;
	}
	
	public List getCheckChildren(String code) throws Exception {
		if(code == null) {
			return null;
		}
		
		Session session = null;
		Transaction tran = null;
		List list = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			
			String name = "";
			int codeL = 0;
			
			if(code.length() == 0) {
				name = "daebun";
				codeL = 2;
			}
			else if(code.length() == 1) {
				name = "midbun";
				codeL = 3;
			}
			else if(code.length() == 2) {
				name = "lstbun";
				codeL = 5;
			}
			else if(code.length() == 4){
				return this.getCheckItems(code);
			}
		
			//String sql = "select slipnoa, daebun from (select substring(slipno,0,2) as slipnoa, daebun from t_checkType)  group by slipnoa, daebun";
			String sql = "select tt.slipnoa, tt.daebun from (select substring(slipno, 0, " + (codeL) + ") as slipnoa, " + name +" as daebun from t_checkType where substring(slipno, 0, " + (code.length() + 1) + ") = '" + code +"') tt group by tt.slipnoa, tt.daebun";
			list = session.createSQLQuery(sql).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		
		return list;
	}

	public Long addPlanModule(PlanModule planModule) throws Exception {
		Session session = null;
		Transaction tran = null;
		Long id = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			
			if(planModule.getId() != null && planModule.getId() > 0) {
			   session.update(planModule);
			   id = planModule.getId();
			}
			else {
				id = (Long) session.save(planModule);	
			}
		
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}
	
	
	public Long addPlanModuleItem(PlanModuleItem planModuleItem) throws Exception {
		Session session = null;
		Transaction tran = null;
		Long id = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			
			if(planModuleItem.getId() != null && planModuleItem.getId() > 0) {
			   session.update(planModuleItem);
			   id = planModuleItem.getId();
			}
			else {
				id = (Long) session.save(planModuleItem);	
			}
		
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}
	
	
	/*
	 * 添加或修改一个随访计划模板  PlanModule   by cheng jiangyu 2011-09-02
	 */
	public Long saveOrUpdatePlanModule(PlanModule planModule) throws Exception {
		Session session = null;
		Transaction tran = null;
		Long id = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			
			if(planModule.getId() != null && planModule.getId() > 0) {
			   session.update(planModule);
			   id = planModule.getId();
			}
			else {
				id = (Long) session.save(planModule);	
			}
		
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}
	
	/*
	 * 添加或修改一个随访计划模板项目  PlanModuleItem  by cheng jiangyu 2011-09-02
	 */
	public Long saveOrUpdatePlanModuleItem(PlanModuleItem planModuleItem) throws Exception {
		Session session = null;
		Transaction tran = null;
		Long id = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			
			if(planModuleItem.getId() != null && planModuleItem.getId() > 0) {
			   session.update(planModuleItem);
			   id = planModuleItem.getId();
			}
			else {
				id = (Long) session.save(planModuleItem);	
			}
		
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return id;
	}
	
	/*
	 *  获得医生所有的随访计划模板
	 */
	public List<PlanModule> getAllPlanModules(Integer start,
			Integer limit, Criterion... criterions) throws Exception {
		List<PlanModule> list;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session
					.createCriteria(PlanModule.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			list = criteria.setFirstResult(start).setMaxResults(limit)
					.addOrder(Order.desc("id")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
	/*
	 *  获得医生所有的随访计划模板数量
	 */
	public long getTotalModules(Criterion... criterions) throws Exception {
		long rst = 0;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session
					.createCriteria(PlanModule.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			
			if(criteria == null) {
				return 0;
			}
			
			Criteria cr = criteria.setProjection(Projections.rowCount());
			Object obj = cr.uniqueResult();
			
			if(obj == null) {
				return 0;
			}
			
			rst = (Long) obj;
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}
	
	/*
	 * 由某一个模板Id获得该模板的所有模板项目
	 */
	public List<PlanModuleItem> getAllModuleItems(Integer start,
			Integer limit, Criterion... criterions) throws Exception {
		List<PlanModuleItem> list;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			
			Criteria criteria = session.createCriteria(PlanModuleItem.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			list = criteria.setFirstResult(start).setMaxResults(limit)
					.addOrder(Order.desc("id")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
	
	/*
	 * 由一个模板Id获得该模板的所有项目数
	 */
	public long getTotalModuleItems(Criterion... criterions) throws Exception {
		long rst = 0;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session
					.createCriteria(PlanModuleItem.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			
			if(criteria == null) {
				return 0;
			}
			
			Criteria cr = criteria.setProjection(Projections.rowCount());
			Object obj = cr.uniqueResult();
			
			if(obj == null) {
				return 0;
			}
			
			rst = (Long) obj;
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}
	
	/*
	 * 删除随访模板   by cheng jiangyu 2011-09-05
	 * 
	 */
	public void deletePlanModule(String moduleIds) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.createSQLQuery("delete from "+TABLE_PLAN_MODULE+" where id in("+moduleIds+")").executeUpdate();
			session.createSQLQuery("delete from "+TABLE_PLAN_MODULE_ITEM+" where moduleId in("+moduleIds+")").executeUpdate();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}
	
	/*
	 * 删除随访模板项目   by cheng jiangyu 2011-09-05
	 * 
	 */
	public void deletePlanModuleItems(Long moduleId,String moduleItemIds) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.createSQLQuery("delete from "+TABLE_PLAN_MODULE_ITEM+" where moduleId="+moduleId+" and id in("+moduleItemIds+")").executeUpdate();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}
	
	/*
	 * 由模板Ids获得相应的检查项目   by cheng jiangyu 2011-09-06
	 * 
	 */
	public List<PlanModuleItem> getItemsByModule(String idStr) throws Exception {
		Session session = null;
		Transaction tran = null;
		List<PlanModuleItem> list = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from PlanModuleItem where Id in("+idStr+")").list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	/*
	 * 
	 * 动态更改moduleItem或module某一列的值
	 */
	public void updateModuleItem(String sql) throws Exception{
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}
	
	/*
	 * 
	 * 动态更改module某一列的值
	 
	public void updateModule(String sql) throws Exception{
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}*/

	
}
