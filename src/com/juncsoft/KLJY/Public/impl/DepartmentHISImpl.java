package com.juncsoft.KLJY.Public.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.Public.dao.DepartmentHISDao;
import com.juncsoft.KLJY.Public.entity.DepartmentHISEntity;
import com.juncsoft.KLJY.membergrounp.entity.DepartmentGrounp;
import com.juncsoft.KLJY.membergrounp.entity.MemberGrounpAndDoctor;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.BaseService;
import com.juncsoft.KLJY.util.DatabaseUtil;

/**
 * HIS科室名称管理
 * 
 * @author liugang
 * 
 */
public class DepartmentHISImpl extends BaseService implements DepartmentHISDao {

	// 通过科室名称查找对应的住院或门诊病历类型名称
	public Map findDepartmentHISEntityByDeptName(String deptName) {
		String hql = "from DepartmentHISEntity where deptName in('" + deptName
				+ "')";
		List<DepartmentHISEntity> list = super.findAllObject(hql);
		Map map = new HashMap();
		if (list != null && list.size() > 0) {
			for (int i = 0, size = list.size(); i < size; i++) {
				DepartmentHISEntity depart = list.get(i);
				//liugang 2011-08-06修改
				if (depart != null && depart.getOutType() != null) {
					if (depart.getOutType().indexOf("、") > 0) {
						String[] array = depart.getOutType().split("、");
						for (int j = 0, sizej = array.length; j < sizej; j++) {
							Set set = map.keySet();
							Iterator iterator = set.iterator();
							if(iterator.hasNext()) {
								while(iterator.hasNext()){
									String key = (String) iterator.next();
									if (!array[j].equals(key)) {
										map.put(depart.getOutType(), depart
												.getOutType());
									}
								}
							}else{
								map.put(depart.getOutType(), depart
										.getOutType());
							}
						}
					} else {
						Set set = map.keySet();
						Iterator iterator = set.iterator();
						if (iterator.hasNext()) {
							while(iterator.hasNext()){
								String key = (String) iterator.next();
								if (!depart.getOutType().equals(key)) {
									map.put(depart.getOutType(), depart
											.getOutType());
								}
							}
						} else {
							map.put(depart.getOutType(), depart.getOutType());
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 通过小组编号，获取医生的姓名列表
	 */
	public Map findDoctornameByGrounpId(Long grounpId) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from MemberGrounpAndDoctor where grounpId="
					+ grounpId;
			List list = session.createQuery(hql).list();
			if (list != null && list.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0, sizei = list.size(); i < sizei; i++) {
					MemberGrounpAndDoctor memberGrounpAndDoctor = (MemberGrounpAndDoctor) list
							.get(i);
					User user = (User) session.get(User.class,
							memberGrounpAndDoctor.getDoctorId());
					if (i != (sizei - 1)) {
						sb.append(user.getName() + "、");
					} else {
						sb.append(user.getName());
					}
				}
				mp.put("doctorNameList", sb.toString());
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("通过小组编号，获取医生的姓名列表出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	public List<Map> findDoctorDeptMemberByDoctorId(Long doctorId) {
		Session session = null;
		Transaction tran = null;
		List<Map> result = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select distinct(tda.deptCode) from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and tm.doctorId="
					+ doctorId;
			List list = session.createSQLQuery(sql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map mp = new HashMap();
					String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
							+ list.get(i) + "'";
					List deptList = session.createSQLQuery(hqlCode).list();
					mp.put("deptName", deptList.get(0));
					mp.put("deptCode", list.get(i));
					result.add(mp);
				}
			}

			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("根据医生ID获取医生负责的责任科室出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

	/**
	 * 根据医生ID和当前责任科室加载医生所在的责任小组
	 */
	public List<Map> findDoctorGrounpByMemberByDoctorId(Long doctorId,
			String deptCode) {
		Session session = null;
		Transaction tran = null;
		List<Map> result = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select tda.grounpId from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and tm.doctorId="
					+ doctorId + " and tda.deptCode='" + deptCode + "'";
			List list = session.createSQLQuery(sql).list();
			if (list != null && list.size() > 0) {
				Map mp = new HashMap();
				DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
						.get(DepartmentGrounp.class, Long.parseLong(list.get(0)
								.toString()));
				mp.put("grounpName", departmentGrounp.getGrounpName());
				mp.put("id", departmentGrounp.getId());
				result.add(mp);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("根据医生ID和当前责任科室加载医生所在的责任小组出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}
	
	/**
	 * 通过管理员的deptCode查找管理员所在的小组列表  by cheng jiangyu 2011-9-30
	 */
	public List<Map> findManageDeptGroupBydeptCode(String deptCode) {
		Session session = null;
		Transaction tran = null;
		List<Map> result = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select grounpId from t_DepartmentAndGrounpMiddle where deptCode='" + deptCode + "'";
			List list = session.createSQLQuery(sql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
				Map mp = new HashMap();
				DepartmentGrounp departmentGrounp = (DepartmentGrounp) session.get(DepartmentGrounp.class, Long.parseLong(list.get(i).toString()));
				mp.put("grounpName", departmentGrounp.getGrounpName());
				mp.put("id", departmentGrounp.getId());
				result.add(mp);
			  }
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("根据医生ID和当前责任科室加载医生所在的责任小组出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}
	
	
	
	
	/*
	 * 根据医生Id 获得当前责任科室和小组名称  by cheng jiangyu 2011-09-30
	 * 
	 */
	public List<Map> findDoctorDeptAndGroupByDoctorId(Long doctorId) {
		Session session = null;
		Transaction tran = null;
		List<Map> result = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select distinct(tda.deptCode),tda.grounpId from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and tm.doctorId="+ doctorId;
			List list = session.createSQLQuery(sql).list();
			Object[] obj =null;
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map mp = new HashMap();
					obj = (Object[])list.get(i);
					String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"+ obj[0] + "'";
					List deptList = session.createSQLQuery(hqlCode).list();
					DepartmentGrounp departmentGrounp = (DepartmentGrounp) session.get(DepartmentGrounp.class, Long.parseLong(obj[1].toString()));
					if(deptList!=null&&deptList.size()>0){
						mp.put("deptName", deptList.get(0));
					}
					mp.put("grounpName", departmentGrounp.getGrounpName());
					result.add(mp);
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("根据医生ID获取医生负责的责任科室出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}
	
	
	

	public List<Map> findGrounpByDeptCode(String deptCode) {
		Session session = null;
		Transaction tran = null;
		List<Map> result = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select id from t_DepartmentAndGrounpMiddle where deptCode = '"+deptCode+"'";
			List list = session.createSQLQuery(sql).list();
			if (list != null && list.size() > 0) {
				Map mp = new HashMap();
				DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
						.get(DepartmentGrounp.class, Long.parseLong(list.get(0)
								.toString()));
				mp.put("grounpName", departmentGrounp.getGrounpName());
				mp.put("id", departmentGrounp.getId());
				result.add(mp);
			}

			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("根据医生ID和当前责任科室加载医生所在的责任小组出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

	public List<Map> findManageDeptGroupBydeptCode() {
		Session session = null;
		Transaction tran = null;
		List<Map> result = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select distinct(deptCode) from t_DepartmentAndGrounpMiddle";
			List list = session.createSQLQuery(sql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
				Map mp = new HashMap();
				String deptCode = list.get(i).toString();
				String hql="from DepartmentHISEntity where deptCode = '"+deptCode+"'";
				DepartmentHISEntity departmentHIS = (DepartmentHISEntity)session.createQuery(hql).list().get(0);
				mp.put("deptCode", departmentHIS.getDeptCode());
				mp.put("deptName", departmentHIS.getDeptName());
				result.add(mp);
			  }
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("根据医生ID和当前责任科室加载医生所在的责任科室出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

}
