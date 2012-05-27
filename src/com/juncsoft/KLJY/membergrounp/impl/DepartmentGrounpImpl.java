package com.juncsoft.KLJY.membergrounp.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.membergrounp.dao.DepartmentGrounpDao;
import com.juncsoft.KLJY.membergrounp.entity.DepartmentAndGrounpMiddle;
import com.juncsoft.KLJY.membergrounp.entity.DepartmentGrounp;
import com.juncsoft.KLJY.membergrounp.entity.MemberApplicationRecord;
import com.juncsoft.KLJY.membergrounp.entity.MemberDeptOrGrounpRecord;
import com.juncsoft.KLJY.membergrounp.entity.MemberGrounpAndDoctor;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DatabaseUtil;

/**
 * 
 * @author liugang
 * 
 */
public class DepartmentGrounpImpl implements DepartmentGrounpDao {

	/**
	 * 根据科室ID查找科室下的责任小组
	 */
	public List<DepartmentGrounp> findDepartmentGrounpByDeptId(String deptCode) {
		Session session = null;
		Transaction tran = null;
		List<DepartmentGrounp> list = new ArrayList<DepartmentGrounp>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select tp.* from t_DepartmentAndGrounpMiddle td inner join t_DepartmentGrounp tp on td.grounpId = tp.id and td.deptCode = '"
					+ deptCode + "'";
			List listM = session.createSQLQuery(sql).list();
			if(listM != null && listM.size() > 0){
				for(int i=0,size=listM.size();i<size;i++){
					Object[] obj = (Object[])listM.get(i);
					DepartmentGrounp departmentGrounp =  new DepartmentGrounp();
					departmentGrounp.setId(Long.parseLong(obj[0].toString()));
					departmentGrounp.setGrounpName(obj[1].toString());
					list.add(departmentGrounp);
				}
			}
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			throw new RuntimeException("根据科室ID查找科室下的责任小组出错", e);
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	/**
	 * 获取当前管理员即科主任所在科室下的所有小组及成员分布
	 */
	public List<Map> findCurrentDoctorDeptmentById(Long grounpId) {
		List<Map> result = new ArrayList<Map>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "select tm.id,tdg.id as grounpId,tdg.grounpName,tm.doctorId, tu.name from t_DepartmentAndGrounpMiddle td" 
				+" inner join t_MemberGrounpAndDoctor tm on td.grounpId = tm.grounpId inner join t_DepartmentGrounp tdg on tdg.id=td.grounpId "
				+"inner join t_user tu on tu.id=tm.doctorId  and tm.grounpId ="+grounpId+"  order by tdg.grounpName desc";
			List listSql = session.createSQLQuery(hql).list();
			for (int j = 0; j < listSql.size(); j++) {
				Map mp = new HashMap();
				Object[] obj = (Object[]) listSql.get(j);
				mp.put("id", obj[0]);
				mp.put("grounpId", obj[1]);
				mp.put("grounpName", obj[2]);
				mp.put("doctorId", obj[3]);
				mp.put("name", obj[4]);
				result.add(mp);
			}
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("获取当前管理员即科主任所在科室下的所有小组及成员分布出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
			return result;
		}
	}

	public List<Map> findCurrentGrounpList(Long doctorId) {
		List<Map> result = new ArrayList<Map>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select td.deptCode from t_DepartmentAndGrounpMiddle td inner join t_MemberGrounpAndDoctor tm on td.grounpId = tm.grounpId and doctorId ="
					+ +doctorId;
			List list = session.createSQLQuery(sql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					String hql = "select td.* from t_DepartmentAndGrounpMiddle tda inner join t_DepartmentGrounp td on tda.grounpId = td.id and tda.deptCode='"
							+ list.get(i) + "'";
					List listSql = session.createSQLQuery(hql).list();
					for (int j = 0; j < listSql.size(); j++) {
						Map mp = new HashMap();
						Object[] obj = (Object[]) listSql.get(j);
						mp.put("id", obj[0]);
						mp.put("grounpName", obj[1]);
						mp.put("deptCode", list.get(i));
						result.add(mp);
					}
				}
			}
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("获取当前管理员即科主任所在科室下的所有小组及成员分布出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
			return result;
		}
	}

	public void saveDepartmentGrounp(Long doctorId, DepartmentGrounp d) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select td.deptCode from t_DepartmentAndGrounpMiddle td inner join t_MemberGrounpAndDoctor tm on td.grounpId = tm.grounpId and doctorId ="
					+ +doctorId;
			List list = session.createSQLQuery(sql).list();
			Long id = (Long) session.save(d);
			DepartmentAndGrounpMiddle departmentAndGrounpMiddle = new DepartmentAndGrounpMiddle();
			departmentAndGrounpMiddle.setDeptCode(list.get(0).toString());
			departmentAndGrounpMiddle.setGrounpId(id);
			session.save(departmentAndGrounpMiddle);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("保存分组信息失败", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void updateDepartmentGrounp(DepartmentGrounp d) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.update(d);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("修改分组名称失败", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public void deleteGrounpUpdateOther(Long id, String deptCode) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			DepartmentGrounp departmentGrounp = (DepartmentGrounp) session.get(
					DepartmentGrounp.class, id);
			session.delete(departmentGrounp);
			String hql = "from DepartmentAndGrounpMiddle where deptCode='"
					+ deptCode + "' and grounpId=" + id;
			List list = session.createQuery(hql).list();
			if (list != null && list.size() > 0) {
				DepartmentAndGrounpMiddle departmentAndGrounpMiddle = (DepartmentAndGrounpMiddle) list
						.get(0);
				session.delete(departmentAndGrounpMiddle);
			}
			String hqlm = "from MemberGrounpAndDoctor where grounpId=" + id;
			List listm = session.createQuery(hqlm).list();
			if (listm != null && listm.size() > 0) {
				MemberGrounpAndDoctor memberGrounpAndDoctor = (MemberGrounpAndDoctor) list
						.get(0);
				session.delete(memberGrounpAndDoctor);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("删除小组出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 申请转科内容记录
	 */
	public void saveApplicationRecord(
			MemberApplicationRecord memberApplicationRecord) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.save(memberApplicationRecord);
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			throw new RuntimeException("申请转科内容记录出错",e);
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	public JSONObject findUserHistoryApplicationRecord(Long patientId) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		List<Map> result = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String appHql = "from MemberApplicationRecord";
			appHql +=" where patientId=" + patientId;
			
			List appList = session.createQuery(appHql).list();
			for(int i=0,size=appList.size();i<size;i++){
				Map<String, Object> mp = new HashMap();
				MemberApplicationRecord memApp = (MemberApplicationRecord)appList.get(i);
				mp.put("id", memApp.getId());
				mp.put("patientId", memApp.getPatientId());
				Patient patient = (Patient)session.get(Patient.class, memApp.getPatientId());
				mp.put("patientName", patient.getPatientName());
				
				mp.put("applicationDeptCode", memApp.getApplicationDeptCode());
				String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
					+  memApp.getApplicationDeptCode() + "'";
				List deptList = session.createSQLQuery(hqlCode)
						.list();
				if(deptList.size()>0 && deptList.get(0) != null) {
					mp.put("newdeptName", deptList.get(0));
				}
				mp.put("applicationBacuse", memApp.getApplicationBacuse());
				mp.put("applicatinDate", memApp.getApplicatinDate().toLocaleString());			
				String oldHqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
					+ memApp.getOldDeptCode()  + "'";
				List oldDeptList = session.createSQLQuery(oldHqlCode)
				.list();
				if(oldDeptList.size()>0 && oldDeptList.get(0) != null) {
					mp.put("oldDeptName", oldDeptList.get(0));
				}
				mp.put("oldDeptCode", memApp.getOldDeptCode());
				mp.put("grounpId", memApp.getOldGrounpId());
				DepartmentGrounp departmentGrounp = (DepartmentGrounp)session.get(DepartmentGrounp.class, memApp.getOldGrounpId());
				mp.put("grounpName", departmentGrounp.getGrounpName());
				mp.put("autoFlag", memApp.getAutoFlag());
				mp.put("currentFlag", memApp.getCurrentFlag());
				mp.put("applicationFlag", memApp.getApplicationFlag());
				mp.put("result", memApp.getResult());
				mp.put("currentDeptBecause", memApp.getCurrentDeptBecause());
				mp.put("applicationDeptBecause", memApp.getApplicationDeptBecause());
				mp.put("optrole", memApp.getOptrole());
				mp.put("flag", memApp.getFlag());
				mp.put("appsendDate", memApp.getAppsendDate()==null ? "" : memApp.getAppsendDate().toLocaleString());
				mp.put("appuserName", memApp.getAppuserName());
				mp.put("transappDate", memApp.getTransappDate()==null ? "" : memApp.getTransappDate().toLocaleString());
				mp.put("transappuserName", memApp.getTransappuserName());
				result.add(mp);
			}
			json.put("root", result);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("获取当前转科结果出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}
	
	public JSONObject findListApplicationRecord(String[] deptCodes,int flag,int start,int limit) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		List<Map> result = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			int resultCount = 0;
				String appHql = "from MemberApplicationRecord";
				String deptHql = "";
				if(flag==0){//转入申请
					if(deptCodes[0]!=null) {
						deptHql = "applicationDeptCode='" + deptCodes[0].trim() + "'";
				    	for(int i=1; i<deptCodes.length; i++) {
			    			deptHql+=" or applicationDeptCode='" + deptCodes[i].trim() + "'";
			    		}
					}
					appHql +=" where (" + deptHql + ") and currentFlag=1 and applicationFlag in (0,1)";
				}else if(flag==1){//转出申请
					if(deptCodes[0]!=null) {
						deptHql = "oldDeptCode='" + deptCodes[0].trim() + "'";
				    	for(int i=1; i<deptCodes.length; i++) {
			    			deptHql+=" or oldDeptCode='" + deptCodes[i].trim() + "'";
			    		}
					}
					appHql +=" where ("+ deptHql +") and autoFlag in (0,1,2)";
				}
				resultCount = session.createQuery(appHql).list().size();
				List appList = session.createQuery(appHql).setFirstResult(start).setMaxResults(limit).list();
				for(int i=0,size=appList.size();i<size;i++){
					Map<String, Object> mp = new HashMap();
					MemberApplicationRecord memApp = (MemberApplicationRecord)appList.get(i);
					mp.put("id", memApp.getId());
					mp.put("patientId", memApp.getPatientId());
					Patient patient = (Patient)session.get(Patient.class, memApp.getPatientId());
					mp.put("patientName", patient.getPatientName());
					
					mp.put("applicationDeptCode", memApp.getApplicationDeptCode());
					String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+  memApp.getApplicationDeptCode() + "'";
					List deptList = session.createSQLQuery(hqlCode)
							.list();
					if(deptList.size()>0 && deptList.get(0) != null) {
						mp.put("newdeptName", deptList.get(0));
					}
					mp.put("applicationBacuse", memApp.getApplicationBacuse());
					mp.put("applicatinDate", memApp.getApplicatinDate().toLocaleString());			
					String oldHqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+ memApp.getOldDeptCode()  + "'";
					List oldDeptList = session.createSQLQuery(oldHqlCode)
					.list();
					if(oldDeptList.size()>0 && oldDeptList.get(0) != null) {
						mp.put("oldDeptName", oldDeptList.get(0));
					}
					mp.put("oldDeptCode", memApp.getOldDeptCode());
					mp.put("grounpId", memApp.getOldGrounpId());
					DepartmentGrounp departmentGrounp = (DepartmentGrounp)session.get(DepartmentGrounp.class, memApp.getOldGrounpId());
					mp.put("grounpName", departmentGrounp.getGrounpName());
					mp.put("autoFlag", memApp.getAutoFlag());
					mp.put("currentFlag", memApp.getCurrentFlag());
					mp.put("applicationFlag", memApp.getApplicationFlag());
					mp.put("result", memApp.getResult());
					mp.put("currentDeptBecause", memApp.getCurrentDeptBecause());
					mp.put("applicationDeptBecause", memApp.getApplicationDeptBecause());
					mp.put("optrole", memApp.getOptrole());
					mp.put("flag", memApp.getFlag());
					result.add(mp);
			}
			json.put("root", result);
			json.put("total",resultCount);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("获取当前转科结果出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public JSONObject findListApplicationRecord(String deptCode,int flag,int start,int limit) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		List<Map> result = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
//			String sql = "select td.deptCode from t_DepartmentAndGrounpMiddle td inner join t_MemberGrounpAndDoctor tm on td.grounpId = tm.grounpId and doctorId ="
//					+ +doctorId;
//			System.out.println(sql);
//			List list = session.createSQLQuery(sql).list();
			int resultCount = 0;
//			if(list != null && list.size() > 0){
				String appHql = "from MemberApplicationRecord";
				if(flag==0){//转入申请
					appHql +=" where applicationDeptCode='"+deptCode+"' and currentFlag=1 and applicationFlag in (0,1)";
				}else if(flag==1){//转出申请
					appHql +=" where oldDeptCode='"+deptCode+"' and autoFlag in (0,1,2)";
				}
				resultCount = session.createQuery(appHql).list().size();
				List appList = session.createQuery(appHql).setFirstResult(start).setMaxResults(limit).list();
				for(int i=0,size=appList.size();i<size;i++){
					Map<String, Object> mp = new HashMap();
					MemberApplicationRecord memApp = (MemberApplicationRecord)appList.get(i);
					mp.put("id", memApp.getId());
					mp.put("patientId", memApp.getPatientId());
					Patient patient = (Patient)session.get(Patient.class, memApp.getPatientId());
					mp.put("patientName", patient.getPatientName());
					
					mp.put("applicationDeptCode", memApp.getApplicationDeptCode());
					String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+  memApp.getApplicationDeptCode() + "'";
					List deptList = session.createSQLQuery(hqlCode)
							.list();
					if(deptList.size()>0 && deptList.get(0) != null) {
						mp.put("newdeptName", deptList.get(0));
					}
					//mp.put("newdeptName", deptList.get(0));
					mp.put("applicationBacuse", memApp.getApplicationBacuse());
					mp.put("applicatinDate", memApp.getApplicatinDate().toLocaleString());
//					String deptCodeY = "";
//					Long deptGrounpY = 0L;
//					String zhuanru ="from MemberDeptOrGrounpRecord where patientId ="+memApp.getPatientId()+" order by appDate desc";
//					List listY = session.createQuery(zhuanru).list();
//					if(listY != null && listY.size() > 0){
//						MemberDeptOrGrounpRecord memberDeptOrGrounpRecord = (MemberDeptOrGrounpRecord)listY.get(0);
//						deptCodeY = memberDeptOrGrounpRecord.getOldDeptCode();
//						deptGrounpY = memberDeptOrGrounpRecord.getOldGrounpId();
//					}
//					if("".equals(deptCodeY) ){
//						String oldHqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
//							+  memApp.getOldDeptCode() + "'";
//						List oldDeptList = session.createSQLQuery(oldHqlCode)
//						.list();
//						mp.put("oldDeptName", oldDeptList.get(0));
//						mp.put("oldDeptCode", memApp.getOldDeptCode());
//						String grounpHql = "select grounpId from MemberInfo where patient="+memApp.getPatientId();
//						List grounpList = session.createSQLQuery(grounpHql).list();
//						mp.put("grounpId", grounpList.get(0));
//						DepartmentGrounp DepartmentGrounp = (DepartmentGrounp)session.get(DepartmentGrounp.class, Long.parseLong(grounpList.get(0).toString()));
//						mp.put("grounpName", DepartmentGrounp.getGrounpName());
//					}else{
//						String oldHqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
//							+  deptCodeY + "'";
//						List oldDeptList = session.createSQLQuery(oldHqlCode)
//						.list();
//						mp.put("oldDeptName", oldDeptList.get(0));
//						mp.put("oldDeptCode", memApp.getOldDeptCode());
//						mp.put("grounpId", deptGrounpY);
//						DepartmentGrounp DepartmentGrounp = (DepartmentGrounp)session.get(DepartmentGrounp.class, deptGrounpY);
//						mp.put("grounpName", DepartmentGrounp.getGrounpName());
//					}
					String oldHqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+ memApp.getOldDeptCode()  + "'";
					List oldDeptList = session.createSQLQuery(oldHqlCode)
					.list();
					if(oldDeptList.size()>0 && oldDeptList.get(0) != null) {
						mp.put("oldDeptName", oldDeptList.get(0));
					}
					//mp.put("oldDeptName", oldDeptList.get(0));
					mp.put("oldDeptCode", memApp.getOldDeptCode());
					mp.put("grounpId", memApp.getOldGrounpId());
					DepartmentGrounp departmentGrounp = (DepartmentGrounp)session.get(DepartmentGrounp.class, memApp.getOldGrounpId());
					mp.put("grounpName", departmentGrounp.getGrounpName());
					mp.put("autoFlag", memApp.getAutoFlag());
					mp.put("currentFlag", memApp.getCurrentFlag());
					mp.put("applicationFlag", memApp.getApplicationFlag());
					mp.put("result", memApp.getResult());
					mp.put("currentDeptBecause", memApp.getCurrentDeptBecause());
					mp.put("applicationDeptBecause", memApp.getApplicationDeptBecause());
					mp.put("optrole", memApp.getOptrole());
					mp.put("flag", memApp.getFlag());
					result.add(mp);
//				}
			}
			json.put("root", result);
			json.put("total",resultCount);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("获取当前转科结果出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public void updateApplicationRecord(MemberApplicationRecord memApp) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			MemberApplicationRecord memberApplicationRecord = (MemberApplicationRecord)session.get(MemberApplicationRecord.class, memApp.getId());
			memberApplicationRecord.setFlag(memApp.getFlag());
			memberApplicationRecord.setOptrole(memApp.getOptrole());
			memberApplicationRecord.setCurrentDeptBecause(memApp.getCurrentDeptBecause());
			session.update(memberApplicationRecord);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	/**
	 * 更新转科转组计划列表
	 */
	public void updateApplicationRecord(
			Long id, String because,int state,Long grouId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			MemberApplicationRecord memberApplicationRecord = (MemberApplicationRecord)session.get(MemberApplicationRecord.class, id);
			if(memberApplicationRecord.getAutoFlag()==0){
				memberApplicationRecord.setAutoFlag(1);
				memberApplicationRecord.setCurrentFlag(state);
				memberApplicationRecord.setCurrentDeptBecause(because);//当前转科责任医生意见
				if(state==1){
					memberApplicationRecord.setApplicationStateContent("申请中");
					if(memberApplicationRecord.getOldDeptCode().equals(memberApplicationRecord.getApplicationDeptCode())){
						if(grouId != null){
							String memHql = "from MemberInfo where patient = "+memberApplicationRecord.getPatientId();
							List memList = session.createQuery(memHql).list();
							if(memList != null && memList.size() > 0){
								MemberInfo mem = (MemberInfo)memList.get(0);
								mem.setGrounpId(grouId);
								session.update(mem);
							}
							memberApplicationRecord.setAutoFlag(2);
							memberApplicationRecord.setApplicationFlag(state);
							memberApplicationRecord.setApplicationDeptBecause(because);//当前转科责任医生意见
							memberApplicationRecord.setResult(1);
						}
					}
				}else if(state==0){
					memberApplicationRecord.setApplicationStateContent("申请未完成，请咨询");
					memberApplicationRecord.setResult(3);
				}
			}else if(memberApplicationRecord.getAutoFlag()==1){
				memberApplicationRecord.setAutoFlag(2);
				memberApplicationRecord.setApplicationFlag(state);
				memberApplicationRecord.setApplicationDeptBecause(because);//当前转科责任医生意见
				memberApplicationRecord.setResult(1);
				if(state==1){
					memberApplicationRecord.setApplicationStateContent("申请完成");
				}else if(state==0){
					memberApplicationRecord.setApplicationStateContent("申请未完成，请咨询");
					memberApplicationRecord.setResult(3);
				}
			}
			if(!memberApplicationRecord.getOldDeptCode().equals(memberApplicationRecord.getApplicationDeptCode())){
				if(grouId != null){
					String memHql = "from MemberInfo where patient = "+memberApplicationRecord.getPatientId();
					List memList = session.createQuery(memHql).list();
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(memList != null && memList.size() > 0){
						MemberInfo mem = (MemberInfo)memList.get(0);
						//更新会员以前所在科室的所有的健康记录状态为 以转科，以前科室的医生就无需再进行对该病人的管理以及查房
						String  updateHealthHql = "update PatientHealthRecord set isNotUpdate=1 where deptCode ='"+mem.getDeptCode()+"' and grounpId ="+mem.getGrounpId();
						session.createQuery(updateHealthHql).executeUpdate();
						String  updateConsultingHql = "update PatientConsulting set isNotUpdate=1,pcMeetingState=1 where deptCode ='"+mem.getDeptCode()+"' and grounpId ="+mem.getGrounpId();
						session.createQuery(updateConsultingHql).executeUpdate();
						//liugang 2011-08-06 start	转科转组日志记录
						MemberDeptOrGrounpRecord memberDeptOrGrounpRecord = new MemberDeptOrGrounpRecord();
						memberDeptOrGrounpRecord.setAppDate(sd.format(new Date()));
						memberDeptOrGrounpRecord.setOldDeptCode(mem.getDeptCode());
						memberDeptOrGrounpRecord.setOldGrounpId(mem.getGrounpId());
						memberDeptOrGrounpRecord.setDeptCode(memberApplicationRecord.getApplicationDeptCode());
						memberDeptOrGrounpRecord.setGrounpId(grouId);
						memberDeptOrGrounpRecord.setPatientId(mem.getPatient().getId());
						
						//
						mem.setDeptCode(memberApplicationRecord.getApplicationDeptCode());
						mem.setGrounpId(grouId);
						session.save(memberDeptOrGrounpRecord);
						session.update(mem);
						//liugang 2011-08-06 end	转科转组日志记录
					}
				}
			}
			session.update(memberApplicationRecord);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

	public MemberApplicationRecord findOneMessageById(Long id) {
		Session session = null;
		Transaction tran = null;
		MemberApplicationRecord memberApplcationRecord = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			memberApplcationRecord = (MemberApplicationRecord)session.get(MemberApplicationRecord.class, id);
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return memberApplcationRecord;
	}

	public MemberApplicationRecord findCurrentState(Long patientId) {
		Session session = null;
		Transaction tran = null;
		MemberApplicationRecord memberApplcationRecord = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql="from MemberApplicationRecord where patientId ="+patientId+" and result=0";
			List list = session.createQuery(hql).list();
			if(list != null && list.size() > 0){
				memberApplcationRecord = (MemberApplicationRecord)list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return memberApplcationRecord;
	}

	public JSONObject findAllDeptDoctorList(String deptCode, int start,
			int limit) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		List<Map> result = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select mg.id,t.drdeptname5,t.name,dg.grounpName,dg.id as grounpId,t.id as doctorId from t_DepartmentAndGrounpMiddle dag " +
					" inner join t_DepartmentGrounp dg on dg.id = dag.grounpId and" +
					" dag.deptCode='"+deptCode+"' left join t_MemberGrounpAndDoctor mg on mg.grounpId = dg.id" +
					"  left join t_user t on t.id = mg.doctorId order by mg.id asc";
			 int resultCount = session.createSQLQuery(sql).list().size();
			 List appList = session.createSQLQuery(sql).setFirstResult(start).setMaxResults(limit).list();
			 if(appList != null && appList.size() > 0){
				 for (int i = 0, size = appList.size(); i < size; i++) {
					 Map mp = new HashMap();
					 Object[] obj = (Object[])appList.get(i);
					 mp.put("id", obj[0]);
					 mp.put("doctorId", obj[1]);
					 mp.put("doctorName", obj[2]);
					 mp.put("grounpName", obj[3]);
					 mp.put("grounpId", obj[4]);
					 mp.put("docId", obj[5]);
					 result.add(mp);
				 }
			 }
			 json.put("root", result);
			 json.put("total", resultCount);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("获取当前转科结果出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	public void addNewGrounp(String deptCode, String grounpName) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			DepartmentGrounp dg = new DepartmentGrounp();
			dg.setGrounpName(grounpName);
			Long grounpId = Long.parseLong(session.save(dg).toString());
			DepartmentAndGrounpMiddle departmentAndGrounpMiddle = new DepartmentAndGrounpMiddle();
			departmentAndGrounpMiddle.setDeptCode(deptCode);
			departmentAndGrounpMiddle.setGrounpId(grounpId);
			session.save(departmentAndGrounpMiddle);
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

	public List findMemberDeptOrGrounpRecordByPatientId(Long patientId) {
		Session session = null;
		Transaction tran = null;
		List list = new ArrayList();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from MemberDeptOrGrounpRecord where patientId = "+patientId;
			List listHql = session.createQuery(hql).list();
			if(listHql != null && listHql.size() > 0){
				for(int i=0,size= listHql.size();i<size;i++){
					Map mp = new HashMap();
					MemberDeptOrGrounpRecord  memberDeptOrGrounpRecord = (MemberDeptOrGrounpRecord)listHql.get(i);
					mp.put("appDate", memberDeptOrGrounpRecord.getAppDate());
					String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+ memberDeptOrGrounpRecord.getOldDeptCode() + "'";
					List deptList = session.createSQLQuery(hqlCode)
					.list();
					DepartmentGrounp departmentGrounp = (DepartmentGrounp)session.get(DepartmentGrounp.class, memberDeptOrGrounpRecord.getOldGrounpId());
					mp.put("oldDeptCodeG", deptList.get(0)+departmentGrounp.getGrounpName());
					String hqlCodeNew = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+ memberDeptOrGrounpRecord.getDeptCode() + "'";
					List deptListNew = session.createSQLQuery(hqlCodeNew)
					.list();
					DepartmentGrounp departmentGrounpNew = (DepartmentGrounp)session.get(DepartmentGrounp.class, memberDeptOrGrounpRecord.getGrounpId());
					mp.put("DeptCodeG", deptListNew.get(0)+departmentGrounpNew.getGrounpName());
					list.add(mp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public User findUserByHisUserId(String userId) {
		Session session = null;
		Transaction tran = null;
		User user = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = " from User where drdeptname5='"+userId+"' order by id asc";
			List list = session.createQuery(hql).list();
			if(list !=null && list.size() > 0){
				 user = (User)list.get(0);
			}
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return user;
	}

	public void findOneUserInsertOneGrounp(Long grounpId, Long doctorId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			MemberGrounpAndDoctor mg = new MemberGrounpAndDoctor();
			mg.setDoctorId(doctorId);
			mg.setGrounpId(grounpId);
			session.save(mg);
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 删除小组中的会员
	 */
	public void deleteMemberDoctor(Long doctorId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String delSql = "delete from t_MemberGrounpAndDoctor where doctorId ="+doctorId;
			session.createSQLQuery(delSql).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

	public Map checkUserIsOrNotInOtherGrounp(Long doctorId) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="+doctorId;
			List list = session.createSQLQuery(sql).list();
			if(list != null && list.size() > 0){
				Object[] obj = (Object[])list.get(0);
				String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
					+ obj[1] + "'";
				List deptList = session.createSQLQuery(hqlCode)
				.list();
				DepartmentGrounp departmentGrounp = (DepartmentGrounp)session.get(DepartmentGrounp.class, Long.parseLong(obj[2].toString()));
				mp.put("name", deptList.get(0)+departmentGrounp.getGrounpName());
			}else{
				mp.put("name", "");
			}
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	public boolean checkUserInOneGrounpCount(Long grounpId) {
		Session session = null;
		Transaction tran = null;
		boolean result = true;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select count(*) from t_MemberGrounpAndDoctor where grounpId ="+grounpId;
			List list = session.createSQLQuery(sql).list();
			if(list!= null && list.size() <=2){
				result = false;
			}
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

	public void deleteGrounp(Long grounpId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sqlC = "delete from t_MemberGrounpAndDoctor where grounpId ="+grounpId;
			String sqlZ= "delete from t_DepartmentGrounp where id = "+grounpId;
			session.createSQLQuery(sqlC).executeUpdate();
			session.createSQLQuery(sqlZ).executeUpdate();
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

}
