package com.juncsoft.KLJY.outoremergency.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.outoremergency.dao.OutOrMergencyCaseDao;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyCase;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.BaseService;
import com.juncsoft.KLJY.util.DatabaseUtil;

/**
 * 门诊记录实现
 * 
 * @author liugang
 * 
 */
public class OutOrMergencyCaseDaoImpl extends BaseService implements
		OutOrMergencyCaseDao {

	public void delete(OutOrMergencyCase outEntity) {
		// super.updateObjectFlag(outEntity);
	}

	public OutOrMergencyCase findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 查找门诊记录列表
	 */
	public List<OutOrMergencyCase> queryAll(Long patientId) {
		String hql = "from OutOrMergencyCase where patientId='" + patientId
				+ "'";
		List<OutOrMergencyCase> list = super.findAllObject(hql);
		return list;
	}

	public OutOrMergencyCase save(OutOrMergencyCase outOrMergencyCase) {
		return (OutOrMergencyCase) super.saveObject(outOrMergencyCase);
	}

	public JSONObject searchByParame(String keyword) {
		return null;
	}

	/**
	 * 加载门诊病历记录树
	 * 
	 * @param patientId
	 * @return
	 */
	public JSONArray outOrMergencyCase_treeNodes(Long patientId) {
		JSONArray array = new JSONArray();
		String hql = "from OutOrMergencyCase where patientId =" + patientId+" order by actdate asc";
		List<OutOrMergencyCase> list = super.findAllObject(hql);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int index = 1;
		for (OutOrMergencyCase record : list) {
			JSONObject object = new JSONObject();
			object.put("id", record.getId());// 显示顺序
			String outTitle = record.getOutTitle();
			if("".equals(outTitle)){
				outTitle = "";
			}
			if(outTitle == null){
				outTitle = "";
			}
			if (record.getOutTime() != null) {// 时间标题
				object.put("text", index + "、" + "("
						+ sdf.format(record.getActdate()) + ")"
						+ outTitle);
			} else {
				object.put("text", "The time is not define");
			}
			object.put("leaf", true);
			object.put("iconCls", "icon-none");
			object.put("href", "javascript:scrollTo(" + record.getId() + ")");
			array.add(object);
			index++;
		}
		return array;
	}

	public void saveOrUpdateOutOrMergencyCase(JSONArray array) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date(Calendar.getInstance().getTimeInMillis());
			for (int i = 0, len = array.size(); i < len; i++) {
				JSONObject json = array.getJSONObject(i);
				OutOrMergencyCase outOrMergencyCase = (OutOrMergencyCase) session
				.get(OutOrMergencyCase.class, json.getLong("id"));
				outOrMergencyCase.setOutTitle(json.getString("outTitle"));
				outOrMergencyCase.setOutContent(json.getString("outContent"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				outOrMergencyCase.setOutWriteTime(sdf.parse(json.getString("outWriteTime")));
				outOrMergencyCase.setOutTime(sdf.parse(json.getString("outWriteTime")));
				outOrMergencyCase.setBiaoben(json.getString("biaoben"));
				outOrMergencyCase.setOutCount(Integer.parseInt(json.getString("orderNo")));
				if (json.getString("suiFangFlag").equals("true")) {
					if (Integer.parseInt(json.getString("outFollowCycle")) > 0
							&& json.getString("groupName") != null) {
						outOrMergencyCase.setOutTime(outOrMergencyCase
								.getOutTime());
						outOrMergencyCase.setOutFollowCycle(Integer
								.parseInt(json.getString("outFollowCycle")));
						outOrMergencyCase.setGroupName(json
								.getString("groupName"));
						outOrMergencyCase.setOutFollowContent(json
								.getString("outFollowContent"));
						outOrMergencyCase.setMemo(json.getString("memo"));
						// 计划日期
						Date planDate = outOrMergencyCase.getOutWriteTime();
						String dates = new SimpleDateFormat("yyyy-MM-dd").format(planDate);
						planDate.setMonth(date.getMonth()
								+ Integer.parseInt(json.getString("outFollowCycle")));
						outOrMergencyCase.setOutPlanDate(planDate);
						outOrMergencyCase.setOutWriteTime(java.sql.Date.valueOf(dates));
						outOrMergencyCase.setOutSuiFangStartTime(java.sql.Date.valueOf(dates));
					}
				}else{
					outOrMergencyCase.setOutTime(outOrMergencyCase
							.getOutTime());
					outOrMergencyCase.setOutFollowCycle(null);
					outOrMergencyCase.setGroupName(null);
					outOrMergencyCase.setOutFollowContent(null);
					outOrMergencyCase.setMemo(null);
					// 计划日期
					outOrMergencyCase.setOutPlanDate(null);
					outOrMergencyCase.setOutSuiFangStartTime(null);
				}
				session.update(outOrMergencyCase);
//				if (outOrMergencyCase.getOutCount() == null) {// 首次修改门诊记录并且添加门诊病历
//					//设置门诊次数
//					String hql = "from OutOrMergencyCase where outTitle ='"
//							+ outOrMergencyCase.getOutTitle()
//							+ "' and patientId='"+outOrMergencyCase.getPatientId()+"' order by id desc";
//					List list = session.createQuery(hql).list();
//					if (list != null && list.size() >= 2) {
//						OutOrMergencyCase outOrMergencyCaseLast = (OutOrMergencyCase) list.get(list.size()-2);
//						if(outOrMergencyCaseLast.getOutCount() == null){
//							OutOrMergencyCase outOrMergencyCaseLast1 = (OutOrMergencyCase) list.get(list.size()-1);
//							outOrMergencyCase.setOutCount(outOrMergencyCaseLast
//									.getOutCount() + 1);
//						}else{
//							outOrMergencyCase.setOutCount(outOrMergencyCaseLast
//								.getOutCount() + 1);
//						}
//					} else {
//						outOrMergencyCase.setOutCount(1);
//					}
					//设置门诊随访次数
//					String followTimesHql = "from OutOrMergencyCase where outTitle ='"
//						+ outOrMergencyCase.getOutTitle()
//						+ "' and patientId='"+outOrMergencyCase.getPatientId()+"' and groupName is not null order by id desc";
//					List followTimesOutOrMergencyCase = session.createQuery(followTimesHql).list();
//					if(followTimesOutOrMergencyCase != null && followTimesOutOrMergencyCase.size() >= 2){
//						OutOrMergencyCase outOrMergencyCaseOld = (OutOrMergencyCase)followTimesOutOrMergencyCase.get(followTimesOutOrMergencyCase.size()-2);
//						outOrMergencyCase.setOutFollowTimes(outOrMergencyCaseOld.getOutFollowTimes()+1);
//					}else{
//						outOrMergencyCase.setOutFollowTimes(0);
//					}
//					session.update(outOrMergencyCase);
//				}
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw new RuntimeException("增加或者修改门诊病历出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public OutOrMergencyCase updateSubmiterById(Long id, Long submitId) {
		Session session = null;
		Transaction tran = null;
		OutOrMergencyCase outOrMergencyCase = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date(Calendar.getInstance().getTimeInMillis());
			outOrMergencyCase = (OutOrMergencyCase) session.get(
					OutOrMergencyCase.class, id);
			outOrMergencyCase.setOutSubmiter(submitId);
			User user= (User) session.get(
					User.class, submitId);
			outOrMergencyCase.setOutMainDoctor(user.getName());
			outOrMergencyCase.setOutVerifyStatus(1);
			outOrMergencyCase.setOutSubmitTime(date);
			session.update(outOrMergencyCase);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("门诊医生签字出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return outOrMergencyCase;
	}

	public OutOrMergencyCase updateSubmiterById_cancelSubmit(Long id) {
		Session session = null;
		Transaction tran = null;
		OutOrMergencyCase outOrMergencyCase = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date(Calendar.getInstance().getTimeInMillis());
			outOrMergencyCase = (OutOrMergencyCase) session.get(
					OutOrMergencyCase.class, id);
			outOrMergencyCase.setOutSubmiter(null);
			outOrMergencyCase.setOutMainDoctor(null);
			outOrMergencyCase.setOutVerifyStatus(0);
			outOrMergencyCase.setOutSubmitTime(null);
			session.update(outOrMergencyCase);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("门诊医生取消签字出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return outOrMergencyCase;
	}

	public OutOrMergencyCase updateOutOrMergencyCourse_resubmit(Long id,
			Long submitId) {
		Session session = null;
		Transaction tran = null;
		OutOrMergencyCase outOrMergencyCase = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date(Calendar.getInstance().getTimeInMillis());
			outOrMergencyCase = (OutOrMergencyCase) session.get(
					OutOrMergencyCase.class, id);
			outOrMergencyCase.setOutSubmiter(submitId);
			outOrMergencyCase.setOutVerifyStatus(1);
			outOrMergencyCase.setOutSubmitTime(date);
			outOrMergencyCase.setOutChecker(null);
			outOrMergencyCase.setOutCheckTime(null);
			session.update(outOrMergencyCase);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("门诊医生取消签字出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return outOrMergencyCase;
	}

	public OutOrMergencyCase updateOutOrMergencyCourse_check(Long id,
			Long checkerId, int verifyStatus) {
		Session session = null;
		Transaction tran = null;
		OutOrMergencyCase outOrMergencyCase = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date date = new Date(Calendar.getInstance().getTimeInMillis());
			outOrMergencyCase = (OutOrMergencyCase) session.get(
					OutOrMergencyCase.class, id);
			outOrMergencyCase.setOutVerifyStatus(verifyStatus);
			session.update(outOrMergencyCase);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("门诊医生取消签字出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return outOrMergencyCase;
	}

	/**
	 * 查找该医生所在科室下的所有病历类型的病人的门诊病历
	 * 
	 * @param patientId
	 * @param outNameList
	 * @param outRegno
	 * @return
	 */
	public List<OutOrMergencyCase> findAllOutOrMergencyCase(String patientId,
			String outNameList, String outRegno) {
		Session session = null;
		Transaction tran = null;
		List<OutOrMergencyCase> list = new ArrayList<OutOrMergencyCase>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from OutOrMergencyCase where patientId='" + patientId+"' order by actdate asc";
//					+ "' and outTitle in ('" + outNameList + "')";
			list = session.createQuery(hql).list();
//			String hqlLast = "from OutOrMergencyCase where patientId='"
//					+ patientId + "' and outRegno='" + outRegno + "'";
//			List<OutOrMergencyCase> listLast = session.createQuery(hqlLast)
//					.list();
//			if (listLast != null && listLast.size() > 0) {
//				OutOrMergencyCase outOrMergencyCase = listLast.get(0);
//				if(outOrMergencyCase.getOutTitle() == null){
//					list.add(outOrMergencyCase);
//				}
//			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查找该医生所在科室下的所有病历类型的病人的门诊病历出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
	//门诊年龄
//	public String getAge(Long pid, Date outDate) throws Exception {
//		String age = "";
//		Session session = null;
//		Transaction tran = null;
//		try {
//			session = DatabaseUtil.getSession();
//			tran = session.beginTransaction();
//			Patient patient = (Patient) session.get(Patient.class, pid);
//			age = DataUtil.getCurrentAge(patient.getBirthday(), outDate);
//			tran.commit();
//		} catch (Exception e) {
//			tran.rollback();
//			throw e;
//		} finally {
//			DatabaseUtil.closeResource(session);
//		}
//		return age;
//	}

	public List<OutOrMergencyCase> findAllOutOrMergencyCaseByPatientId(
			String patientId) {
		Session session = null;
		Transaction tran = null;
		List<OutOrMergencyCase> list = new ArrayList<OutOrMergencyCase>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from OutOrMergencyCase where patientId='" + patientId
					+ "' order by actdate asc";
			list = session.createQuery(hql).list();
			
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查找该医生所在科室下的所有病历类型的病人的门诊病历出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public boolean checkIsSubmiter(Long id) {
		Session session = null;
		Transaction tran = null;
		boolean result = true;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			OutOrMergencyCase outOrMergencyCase = (OutOrMergencyCase)session.get(OutOrMergencyCase.class,id);
			if(outOrMergencyCase != null){
				if(outOrMergencyCase.getOutContent() != null && !"".equals(outOrMergencyCase.getOutContent()) && outOrMergencyCase.getOutSubmiter() != null && !"".equals(outOrMergencyCase.getOutSubmiter())){
					result = false;
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
