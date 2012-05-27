package com.juncsoft.KLJY.patientanddoctoroperator.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.Public.entity.DepartmentHISEntity;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.patientanddoctoroperator.dao.PlanSignOrderDao;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.BaseSignAPEntity;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.BaseSignTimeSegmentEntity;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.DoctorReplyRecordAndPatientQuestions;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientConsulting;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PlanSignDateEntity;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PlanSignOrderEntity;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PlanSignOrderPatientEntity;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.BaseService;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.DictionaryUtil;

public class PlanSignOrderImpl extends BaseService implements PlanSignOrderDao {

	public Map findAllSYS_HIS_DepartmentHISEntity(int flag) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> dateList = new ArrayList<Map>();
		try {
			con = DatabaseUtil.getConnection();
			st = con.createStatement();
			String sql = "select * from SYS_HIS_DepartmentHISEntity";
			if (flag != -1) {
				sql += " where flag=" + flag;
			}
			rs = st.executeQuery(sql);
			while (rs.next()) {
				Map mp = new HashMap();
				mp.put("deptCode", rs.getString("deptCode"));
				mp.put("deptName", rs.getString("deptName"));
				list.add(mp);
			}
			map.put("department", list);
			Date time = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String date = sdf.format(time) + "-01";
			String dateSql = "select * from t_PlanSignDateEntity where dateValue >='"
					+ date + "'";
			rs = null;
			rs = st.executeQuery(dateSql);
			while (rs.next()) {
				Map mp = new HashMap();
				mp.put("dateList", rs.getString("dateList"));
				mp.put("dateValue", rs.getString("dateValue"));
				dateList.add(mp);
			}
			map.put("dateList", dateList);
		} catch (Exception e) {
			throw new RuntimeException("外网查找医院的所有科室", e);
		} finally {
			try {
				DatabaseUtil.closeResource(con, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 对一个月份中的每一天按周进行分类
	 */
	public Map findAllDayInWeek(String deptCodeId, Long doctorId, String date) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		// date = "2011-06-01";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date time = sdf.parse(date);
			Date nowTime = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			int dayCount = cal.getActualMaximum(Calendar.DATE);
			String outString = "";
			for (int i = 0; i < dayCount; i++) {
				String hql = "from PlanSignOrderEntity where doctorId="
						+ doctorId + " and deptCodeId='" + deptCodeId
						+ "' and currentDate ='" + date.substring(0, 7) + "-"
						+ (i + 1) + "'";
				List list = session.createQuery(hql).list();
				int weekInt = time.getDay();// 0 = Sunday, 1 = Monday, 2 =
				// Tuesday, 3 = Wednesday, 4 =
				// Thursday, 5 = Friday, 6 =
				// Saturday
				if (i == 0) {
					outString += "<tr style=\"height:70px;text-align:top\">";
				}
				switch (weekInt) {
				case 0:
					outString += this.executeProcess(list, weekInt, session,
							time, i, nowTime.getDate(), nowTime);
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
					}
					break;
				case 1:
					if (i == 0) {
						outString += "<td>&nbsp;</td>";
					}
					outString += this.executeProcess(list, weekInt, session,
							time, i, nowTime.getDate(), nowTime);
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
					}
					break;
				case 2:
					if (i == 0) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td>";
					}
					outString += this.executeProcess(list, weekInt, session,
							time, i, nowTime.getDate(), nowTime);
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
					}
					break;
				case 3:
					if (i == 0) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
					}
					outString += this.executeProcess(list, weekInt, session,
							time, i, nowTime.getDate(), nowTime);
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
					}
					break;
				case 4:
					if (i == 0) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
					}
					outString += this.executeProcess(list, weekInt, session,
							time, i, nowTime.getDate(), nowTime);
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td></tr>";
					}
					break;
				case 5:
					if (i == 0) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
					}
					outString += this.executeProcess(list, weekInt, session,
							time, i, nowTime.getDate(), nowTime);
					if (i == dayCount - 1) {
						outString += "<td>&nbsp;</td></tr>";
					}
					break;
				case 6:
					if (i == 0) {
						outString += "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
					}
					outString += this.executeProcess(list, weekInt, session,
							time, i, nowTime.getDate(), nowTime);
					if (i < dayCount - 1) {
						outString += "</tr><tr style=\"height:70px;text-align:left\">";
					}
					if (i == dayCount - 1) {
						outString += "</tr>";
					}
					break;
				}
				time.setDate(time.getDate() + 1);
			}
			mp.put("weekList", outString);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("对一个月份中的每一天按周进行分类出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	private String executeProcess(List list, int weekInt, Session session,
			Date time, int i, int nowDay, Date nowTime) {
		String outString = "";
		if (list != null && list.size() == 1) {
			PlanSignOrderEntity planSignOrderEntity = (PlanSignOrderEntity) list
					.get(0);
			if (planSignOrderEntity.getWeekDay() == weekInt) {
				String ap = null;
				BaseSignAPEntity baseSignAPEntity = (BaseSignAPEntity) session
						.get(BaseSignAPEntity.class, planSignOrderEntity
								.getBsAPId());
				if (baseSignAPEntity.getTimeAP().equals("A")) {
					ap = "上午";
				} else {
					ap = "下午";
				}
				if (time.getTime() <= nowTime.getTime()) {
					if (i + 1 < nowDay) {
						outString += "<td width=\"110\">" + time.getDate()
								+ "<div style=\"text-align:center\">" + ap
								+ baseSignAPEntity.getOutType() + "</div></td>";
					} else {
						outString += "<td width=\"110\" ondblclick=\"show(this,'"
								+ time.getDate()
								+ "','"
								+ weekInt
								+ "')\">"
								+ time.getDate()
								+ "<div style=\"text-align:center\">"
								+ ap
								+ baseSignAPEntity.getOutType() + "</div></td>";
					}
				} else {
					outString += "<td width=\"110\" ondblclick=\"show(this,'"
							+ time.getDate() + "','" + weekInt + "')\">"
							+ time.getDate()
							+ "<div style=\"text-align:center\">" + ap
							+ baseSignAPEntity.getOutType() + "</div></td>";
				}
			}
		} else if (list != null && list.size() == 2) {
			PlanSignOrderEntity planSignOrderEntity1 = (PlanSignOrderEntity) list
					.get(0);
			if (planSignOrderEntity1.getWeekDay() == weekInt) {
				String ap = null;
				BaseSignAPEntity baseSignAPEntity = (BaseSignAPEntity) session
						.get(BaseSignAPEntity.class, planSignOrderEntity1
								.getBsAPId());
				if (baseSignAPEntity.getTimeAP().equals("A")) {
					ap = "上午";
				} else {
					ap = "下午";
				}
				if (time.getTime() <= nowTime.getTime()) {
					if (i + 1 < nowDay) {
						outString += "<td width=\"110\" >" + time.getDate()
								+ "<div style=\"text-align:center;\">" + ap
								+ baseSignAPEntity.getOutType();
					} else {
						outString += "<td width=\"110\" ondblclick=\"show(this,'"
								+ time.getDate()
								+ "','"
								+ weekInt
								+ "')\">"
								+ time.getDate()
								+ "<div style=\"text-align:center;\">"
								+ ap
								+ baseSignAPEntity.getOutType();
					}
				} else {
					outString += "<td width=\"110\" ondblclick=\"show(this,'"
							+ time.getDate() + "','" + weekInt + "')\">"
							+ time.getDate()
							+ "<div style=\"text-align:center;\" >" + ap
							+ baseSignAPEntity.getOutType();
				}
			}
			PlanSignOrderEntity planSignOrderEntity2 = (PlanSignOrderEntity) list
					.get(1);
			if (planSignOrderEntity2.getWeekDay() == weekInt) {
				String ap = null;
				BaseSignAPEntity baseSignAPEntity = (BaseSignAPEntity) session
						.get(BaseSignAPEntity.class, planSignOrderEntity2
								.getBsAPId());
				if (baseSignAPEntity.getTimeAP().equals("A")) {
					ap = "上午";
				} else {
					ap = "下午";
				}
				outString += "<br/>" + ap + baseSignAPEntity.getOutType()
						+ "</div></td>";
			}
		} else {
			if (time.getTime() <= nowTime.getTime()) {
				if (i + 1 < nowDay) {
					outString += "<td>" + time.getDate() + "</td>";
				} else {
					outString += "<td ondblclick=\"show(this,'"
							+ time.getDate() + "','" + weekInt + "')\">"
							+ time.getDate() + "</td>";
				}
			} else {
				outString += "<td ondblclick=\"show(this,'" + time.getDate()
						+ "','" + weekInt + "')\">" + time.getDate() + "</td>";
			}
		}
		return outString;
	}

	/**
	 * 查找所有的上午或者下午预约类型
	 * 
	 * @return
	 */
	public List<BaseSignAPEntity> findBaseSignAPEntity() {
		String hql = "from BaseSignAPEntity";
		List<BaseSignAPEntity> list = super.findAllObject(hql);
		return list;
	}

	/**
	 * 批量保存PlanSignOrderEntity
	 * 
	 * @param list
	 */
	public void savePlanSignOrderEntity(List<PlanSignOrderEntity> list) {
		Session session = null;
		Transaction tran = null;
		Connection conn = null;
		Statement stat = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			conn = DatabaseUtil.getConnection();
			stat = conn.createStatement();
			for (int i = 0, size = list.size(); i < size; i++) {
				PlanSignOrderEntity planSignOrderEntity = list.get(i);
				String sql = "delete from t_PlanSignOrderEntity where doctorId="
						+ planSignOrderEntity.getDoctorId()
						+ " and deptCodeId='"
						+ planSignOrderEntity.getDeptCodeId()
						+ "' and currentDate = '"
						+ planSignOrderEntity.getCurrentDate().toLocaleString()
						+ "'";
				stat.executeUpdate(sql);
			}
			for (int i = 0, size = list.size(); i < size; i++) {
				PlanSignOrderEntity planSignOrderEntity = list.get(i);
				session.save(planSignOrderEntity);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("护士站批量保存PlanSignOrderEntity出错", e);
		} finally {
			try {
				DatabaseUtil.closeResource(conn, stat, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 根据科室加载医生的出诊列表
	 */
	public Map findAllDoctorNurses(String deptCodeId, String outTypeIdList) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date nowTime = new Date();
			nowTime.setDate(nowTime.getDate() + 2);
			Date nextServenTime = new Date();
			nextServenTime.setDate(nextServenTime.getDate() + 10);
			String hql = "select distinct(doctorId) from t_PlanSignOrderEntity where deptCodeId='"
					+ deptCodeId
					+ "' and currentDate >='"
					+ sdf.format(nowTime)
					+ "' and currentDate <='"
					+ sdf.format(nextServenTime)
					+ "' and bsAPId in ("
					+ outTypeIdList.replaceAll("\"", "") + ")";
			List list = session.createSQLQuery(hql).list();
			StringBuilder outString = new StringBuilder();
			String titleString = "";
			for (int j = 0; j < 7; j++) {
				int weekInt = nowTime.getDay();// 0 = Sunday, 1 = Monday, 2 =
				// Tuesday, 3 = Wednesday, 4 =
				// Thursday, 5 = Friday, 6 =
				String weekChild = ""; // Saturday
				switch (weekInt) {
				case 0:
					weekChild = "日";
					break;
				case 1:
					weekChild = "一";
					break;
				case 2:
					weekChild = "二";
					break;
				case 3:
					weekChild = "三";
					break;
				case 4:
					weekChild = "四";
					break;
				case 5:
					weekChild = "五";
					break;
				case 6:
					weekChild = "六";
					break;

				}
				titleString += "<td width='10%' class='jclb'>"
						+ (nowTime.getMonth() + 1) + "月" + nowTime.getDate()
						+ "号<br/>[星期" + weekChild + "]</center></td>";
				nowTime.setDate(nowTime.getDate() + 1);
			}
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					outString.append("<tr>");
					String attemp = "00"+list.get(i);
					String findUserhql = "from User where drdeptname5='"+ attemp.substring(attemp.length()-6, attemp.length()) + "'";
					List findUserList = session.createQuery(findUserhql).list();
					if (findUserList != null && findUserList.size() > 0) {
						User user = (User) findUserList.get(0);
						Date lastTime = new Date();
						lastTime.setDate(lastTime.getDate() + 2);
						outString
								.append("<td class='jclb_7'><center>"
										+ user.getName()
										+ "</center><a href='#'><center>医生介绍</center></a></td>");
						outString.append("<td class='jclb_7'><center>" + user.getGbjaejik()
								+ "</center></td>");
						for (int j = 0; j < 7; j++) {
							String hqlIns = "from PlanSignOrderEntity where deptCodeId='"
									+ deptCodeId
									+ "' and currentDate ='"
									+ sdf.format(lastTime)
									+ "' and bsAPId in ("
									+ outTypeIdList.replaceAll("\"", "")
									+ ") and doctorId =" + list.get(i);
							List hqlInsList = session.createQuery(hqlIns)
									.list();
							if (hqlInsList != null && hqlInsList.size() == 1) {
								PlanSignOrderEntity planSignOrder = (PlanSignOrderEntity) hqlInsList
										.get(0);
								BaseSignAPEntity baseSignAP = (BaseSignAPEntity) session
										.get(BaseSignAPEntity.class,
												planSignOrder.getBsAPId());
								if(baseSignAP.getOutType().equals("停诊")){
									outString.append("<td class='jclb_7'>");
									if (baseSignAP.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAP.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAP.getOutType()
												+ "</center>");
									}
									outString.append("</td>");
								} else {
									outString
									.append("<td class='jclb_7'><a href='###' onclick=\"showPlanSignOrderPatientEntity('"
											+ user.getDrdeptname5()
											+ "','"
											+ baseSignAP.getId()
											+ "','"
											+ sdf.format(lastTime)
											+ "')\">");
									if (baseSignAP.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAP.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAP.getOutType()
												+ "</center>");
									}
									outString.append("</a></td>");
								}

							} else if (hqlInsList != null
									&& hqlInsList.size() == 2) {
								PlanSignOrderEntity planSignOrder = (PlanSignOrderEntity) hqlInsList
										.get(0);
								BaseSignAPEntity baseSignAP = (BaseSignAPEntity) session
										.get(BaseSignAPEntity.class,
												planSignOrder.getBsAPId());
								if(baseSignAP.getOutType().equals("停诊")){
									outString.append("<td class='jclb_7'>");
									if (baseSignAP.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAP.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAP.getOutType()
												+ "</center>");
									}
									PlanSignOrderEntity planSignOrderTwo = (PlanSignOrderEntity) hqlInsList
											.get(1);
									BaseSignAPEntity baseSignAPTwo = (BaseSignAPEntity) session
											.get(BaseSignAPEntity.class,
													planSignOrderTwo
															.getBsAPId());

									if (baseSignAPTwo.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAPTwo.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAPTwo.getOutType()
												+ "</center>");
									}
									outString.append("、</td>");
								} else {
									outString
									.append("<td class='jclb_7'><a href='###' onclick=\"showPlanSignOrderPatientEntity('"
											+ user.getDrdeptname5()
											+ "','"
											+ baseSignAP.getId()
											+ "','"
											+ sdf.format(lastTime) + "')\">");
									if (baseSignAP.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAP.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAP.getOutType()
												+ "</center>");
									}
									outString.append("</a>");
									PlanSignOrderEntity planSignOrderTwo = (PlanSignOrderEntity) hqlInsList
											.get(1);
									BaseSignAPEntity baseSignAPTwo = (BaseSignAPEntity) session
											.get(BaseSignAPEntity.class,
													planSignOrderTwo
															.getBsAPId());
									outString
											.append("<a href='###' onclick=\"showPlanSignOrderPatientEntity('"
													+ user.getDrdeptname5()
													+ "','"
													+ baseSignAPTwo.getId()
													+ "','"
													+ sdf.format(lastTime)
													+ "')\">");
									if (baseSignAPTwo.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAPTwo.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAPTwo.getOutType()
												+ "</center>");
									}
									outString.append("</a></td>");
								}
							} else {
								outString.append("<td class='jclb_7'>&nbsp;</td>");
							}
							lastTime.setDate(lastTime.getDate() + 1);
						}
					}
					outString.append("<td class='jclb_7'><a href='####' onclick='openNewFourDays()'><center>更多</center></a></td>");
					outString.append("</tr>");
				}
			}
			mp.put("weekList", titleString + "<td width='10%' class='jclb'>后七天</td></tr>" + outString);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查询医生一周出诊情况出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	/**
	 * 外网会员预约挂号
	 */
	public boolean savePlanSignOrderPatientEntity(String deptCode,
			Long doctorId, Long patientId, Long bsAPId, String planDate,
			Long bsTSId) {
		Session session = null;
		Transaction tran = null;
		boolean saveFlag = true;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String checkPatientHql = "from PlanSignOrderPatientEntity where planDate='"
					+ planDate
					+ "' and patientId="
					+ patientId
					+ " and doctorId="
					+ doctorId
					+ " and deptCode='"
					+ deptCode + "'";
			// and bsTSId="+bsTSId
			List checkPatientList = session.createQuery(checkPatientHql).list();
			if (checkPatientList != null && checkPatientList.size() > 0) {
				saveFlag = false;
			}
			if (saveFlag) {
				PlanSignOrderPatientEntity planSignOrderPatientEntity = new PlanSignOrderPatientEntity();
				planSignOrderPatientEntity.setBsAPId(bsAPId);
				planSignOrderPatientEntity.setDeptCode(deptCode);
				planSignOrderPatientEntity.setDoctorId(doctorId);
				planSignOrderPatientEntity.setPatientId(patientId);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				planSignOrderPatientEntity.setPlanDate(sdf.parse(planDate));
				planSignOrderPatientEntity.setBsTSId(bsTSId);
				session.save(planSignOrderPatientEntity);
				saveFlag = true;
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("外网会员预约挂号出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return saveFlag;
	}

	public void savePlanSignOrderOneMonthEntity(String startDate,
			String endDate, List<PlanSignOrderEntity> list) {
		Session session = null;
		Transaction tran = null;
		Connection conn = null;
		Statement stat = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			conn = DatabaseUtil.getConnection();
			stat = conn.createStatement();
			PlanSignOrderEntity planSignOrder = list.get(0);
			String sql = "delete from t_PlanSignOrderEntity where doctorId="
					+ planSignOrder.getDoctorId() + " and deptCodeId='"
					+ planSignOrder.getDeptCodeId()
					+ "' and currentDate between '" + startDate + "' and '"
					+ endDate + "'";
			stat.executeUpdate(sql);

			for (int i = 0, size = list.size(); i < size; i++) {
				PlanSignOrderEntity planSignOrderEntity = list.get(i);
				session.save(planSignOrderEntity);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("护士站批量保存PlanSignOrderEntity出错", e);
		} finally {
			try {
				DatabaseUtil.closeResource(conn, stat, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DatabaseUtil.closeResource(session);
		}
	}

	/**
	 * 外网预约门诊
	 */
	public Map findPlanSignOrderPatient(String deptCode, Long doctorId,
			Long bsAPId, String planDate) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			BaseSignAPEntity baseSignAP = (BaseSignAPEntity) session.get(
					BaseSignAPEntity.class, bsAPId);
			String bsTSHql = "from BaseSignTimeSegmentEntity where timeAP ='"
					+ baseSignAP.getTimeAP() + "'";
			List list = session.createQuery(bsTSHql).list();
			StringBuilder sbSTS = new StringBuilder();// 时间段标题
			// sbSTS.append("<table style='height:98px;width:298px;border:green
			// dotted;text-align:center'><tr><td colspan='5'></td><td><a
			// href='javascript:hidden()'>关闭</a></td></tr><tr
			// style='color:red'><td>预约时间</td>");
			sbSTS
					.append("<table style='height:98px;width:298px;border:teal 4 double;text-align:center'><tr><td colspan='5'></td><td><a href='javascript:hidden()'>关闭</a></td></tr><tr style='text-color:black'><td>预约时间</td>");
			StringBuilder sbStatus = new StringBuilder();// 是否预约显示
			sbStatus
					.append("</tr><tr><td><font color='black'>预约状态</font></td>");
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					BaseSignTimeSegmentEntity baseSignTimeSegment = (BaseSignTimeSegmentEntity) list
							.get(i);
					sbSTS.append("<td>" + baseSignTimeSegment.getTimeSegment()
							+ "</td>");
					String planHql = "from PlanSignOrderPatientEntity where deptCode ='"
							+ deptCode
							+ "' and doctorId="
							+ doctorId
							+ " and planDate='"
							+ planDate
							+ "' and bsAPId="
							+ bsAPId
							+ " and bsTSId="
							+ baseSignTimeSegment.getId() + "";
					List planList = session.createQuery(planHql).list();
					if (planList != null && planList.size() > 0) {
						sbStatus.append("<td style='text-color:red'>已约</td>");
					} else {
						sbStatus
								.append("<td style='text-color:greed'><a href='###' onclick=\"savePlanSignOrderPatientData('"
										+ doctorId
										+ "','"
										+ deptCode
										+ "','"
										+ bsAPId
										+ "','"
										+ baseSignTimeSegment.getId()
										+ "','"
										+ planDate + "')\">预约</a></td>");
					}
				}
			}
			mp.put("returnString", sbSTS.toString() + sbStatus.toString()
					+ "</tr></table>");
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("外网预约门诊出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	/**
	 * 会员查找预约挂号详细信息
	 */
	public JSONObject findPlanSignOrderPatientList(Long patientId, int start,
			int limit, String planOrderStartDate, String planOrderEndDate,
			String deptcode, Long doctorId) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map> listAll = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from PlanSignOrderPatientEntity where 1=1";
			if (patientId != null) {
				hql += " and patientId =" + patientId;
			}
			if (planOrderStartDate != null && planOrderEndDate != null) {
				hql += " and planDate between '" + planOrderStartDate
						+ "' and '" + planOrderEndDate + "'";
			} else if (planOrderStartDate != null) {
				hql += " and planDate >= '" + planOrderStartDate + "'";
			} else if (planOrderEndDate != null) {
				hql += " and planDate <= '" + planOrderEndDate + "'";
			}
			if (deptcode != null) {
				hql += " and deptcode = '" + deptcode + "'";
			}
			if (doctorId != null) {
				hql += " and doctorId =" + doctorId;
			}
			hql +=" order by planDate desc";
			List listSize = session.createQuery(hql).list();
			List list = session.createQuery(hql).setFirstResult(start)
					.setMaxResults(limit).list();
			if (list != null && list.size() > 0) {
				int sizez = list.size();
				for (int i = 0, size = list.size(); i < size; i++) {
					Map mp = new HashMap();
					PlanSignOrderPatientEntity pc = (PlanSignOrderPatientEntity) list
							.get(i);
					mp.put("id", pc.getId());
					mp.put("bianhao",sizez--);
					mp.put("patientId", pc.getPatientId());
					Patient patient = (Patient) session.get(Patient.class, pc
							.getPatientId());
					mp.put("patientName", patient.getPatientName());
					mp.put("gender", DictionaryUtil
							.getIndependentDictionaryText("gender_gb", patient
									.getGender()));
					mp.put("homeTel", patient.getHomeTel());
					mp.put("deptCode", pc.getDeptCode());
					String deptHql = "from DepartmentHISEntity where deptCode = '"
							+ pc.getDeptCode() + "'";
					List deptList = session.createQuery(deptHql).list();
					DepartmentHISEntity departmentHIS = (DepartmentHISEntity) deptList
							.get(0);
					mp.put("deptName", departmentHIS.getDeptName());
					mp.put("doctorId", pc.getDoctorId());
					String temp ="00"+pc.getDoctorId();
					String doctorHql = "from User where drdeptname5='"+temp.substring(temp.length()-6, temp.length())+"'";
					List doctorList = session.createQuery(doctorHql).list();
					User user = (User) doctorList.get(0);
					mp.put("doctorName", user.getName());
					String bsAPHql = "from BaseSignAPEntity where id="
							+ pc.getBsAPId();
					List bsAPList = session.createQuery(bsAPHql).list();
					BaseSignAPEntity baseSignAP = (BaseSignAPEntity) bsAPList
							.get(0);
					String bs = "";
					if (baseSignAP.getTimeAP().equals("A")) {
						bs += "上午";
					} else {
						bs += "下午";
					}
					if (baseSignAP.getOutType().equals("诊疗")) {
						bs += "普通门诊";
					} else if (baseSignAP.getOutType().equals("专家")) {
						bs += baseSignAP.getOutType() + "门诊";
					} else if (baseSignAP.getOutType().equals("普通")) {
						bs += "会员门诊";
					}
					mp.put("bsAPId", bs);
					mp.put("planDate", sdf.format(pc.getPlanDate()));
					BaseSignTimeSegmentEntity baseSignTimeSegmentEntity = (BaseSignTimeSegmentEntity) session
							.get(BaseSignTimeSegmentEntity.class, pc
									.getBsTSId());
					mp
							.put("bsTSId", baseSignTimeSegmentEntity
									.getTimeSegment());
					listAll.add(mp);
				}
			}
			json.put("root", listAll);
			if (listSize != null && listSize.size() > 0) {
				json.put("total", listSize.size());
			} else {
				json.put("total", 0);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("会员查找预约挂号详细信息出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

	/**
	 * 会员取消有效时间内的预约挂号信息
	 */
	public void deletePatientPlanOrder(Long deleteId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			PlanSignOrderPatientEntity planSignOrderPatientEntity = (PlanSignOrderPatientEntity) session
					.get(PlanSignOrderPatientEntity.class, deleteId);
			if (planSignOrderPatientEntity != null) {
				session.delete(planSignOrderPatientEntity);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("会员取消有效时间内的预约挂号信息出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	private void exportDataToExcel_setCellValue(HSSFSheet sheet, int rowIndex,
			short colIndex, String value) {
		HSSFRow row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		HSSFCell cell = row.getCell(colIndex);
		if (cell == null) {
			cell = row.createCell(colIndex);
		}
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(value);
	}

	public void exportSearchDataToExcel(Long patientId,
			String planOrderStartDate, String planOrderEndDate,
			String deptcode, Long doctorId, OutputStream out) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			short currentColIndex = 0;
			// 表头公共部分
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "会员姓名");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "性别");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "联系电话");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "预约科室编号");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "预约科室");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "预约医生编号");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "预约医生");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "预约时间");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "预约类型");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "门诊时间");
			currentColIndex++;

			List<Map> list = findPlanSignOrderPatientAllList(null,
					planOrderStartDate, planOrderEndDate, deptcode, doctorId);
			for (int i = 0; i < list.size(); i++) {
				Map mst = list.get(i);
				currentColIndex = 0;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.get("patientName").toString());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.get("gender").toString());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.get("homeTel").toString());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.get("deptCode").toString());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.get("deptName").toString());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.get("doctorId").toString());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.get("doctorName").toString());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.get("planDate").toString());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.get("bsAPId") + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.get("bsTSId").toString());
			}
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 会员查找预约挂号详细信息
	 */
	private List findPlanSignOrderPatientAllList(Long patientId,
			String planOrderStartDate, String planOrderEndDate,
			String deptcode, Long doctorId) {
		Session session = null;
		Transaction tran = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map> listAll = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from PlanSignOrderPatientEntity where 1=1";
			if (patientId != null) {
				hql += " and patientId =" + patientId;
			}
			if (planOrderStartDate != null && planOrderEndDate != null) {
				hql += " and planDate between '" + planOrderStartDate
						+ "' and '" + planOrderEndDate + "'";
			} else if (planOrderStartDate != null) {
				hql += " and planDate >= '" + planOrderStartDate + "'";
			} else if (planOrderEndDate != null) {
				hql += " and planDate <= '" + planOrderEndDate + "'";
			}
			if (deptcode != null) {
				hql += " and deptcode = '" + deptcode + "'";
			}
			if (doctorId != null) {
				hql += " and doctorId =" + doctorId;
			}
			List listSize = session.createQuery(hql).list();
			List list = session.createQuery(hql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					Map mp = new HashMap();
					PlanSignOrderPatientEntity pc = (PlanSignOrderPatientEntity) list
							.get(i);
					mp.put("id", pc.getId());
					mp.put("patientId", pc.getPatientId());
					Patient patient = (Patient) session.get(Patient.class, pc
							.getPatientId());
					mp.put("patientName", patient.getPatientName());
					mp.put("gender", DictionaryUtil
							.getIndependentDictionaryText("gender_gb", patient
									.getGender()));
					mp.put("homeTel", patient.getHomeTel());
					mp.put("deptCode", pc.getDeptCode());
					String deptHql = "from DepartmentHISEntity where deptCode = '"
							+ pc.getDeptCode() + "'";
					List deptList = session.createQuery(deptHql).list();
					DepartmentHISEntity departmentHIS = (DepartmentHISEntity) deptList
							.get(0);
					mp.put("deptName", departmentHIS.getDeptName());
					mp.put("doctorId", pc.getDoctorId());
					String doctorHql = "from User where drdeptname5='"+pc.getDoctorId()+"'";
					List doctorList = session.createQuery(doctorHql).list();
					User user = (User) doctorList.get(0);
					mp.put("doctorName", user.getName());
					String bsAPHql = "from BaseSignAPEntity where id="
							+ pc.getBsAPId();
					List bsAPList = session.createQuery(bsAPHql).list();
					BaseSignAPEntity baseSignAP = (BaseSignAPEntity) bsAPList
							.get(0);
					String bs = "";
					if (baseSignAP.getTimeAP().equals("A")) {
						bs += "上午";
					} else {
						bs += "下午";
					}
					if (baseSignAP.getOutType().equals("诊疗")) {
						bs += "普通门诊";
					} else if (baseSignAP.getOutType().equals("专家")) {
						bs += baseSignAP.getOutType() + "门诊";
					} else if (baseSignAP.getOutType().equals("普通")) {
						bs += "会员门诊";
					}
					mp.put("bsAPId", bs);
					mp.put("planDate", sdf.format(pc.getPlanDate()));
					BaseSignTimeSegmentEntity baseSignTimeSegmentEntity = (BaseSignTimeSegmentEntity) session
							.get(BaseSignTimeSegmentEntity.class, pc
									.getBsTSId());
					mp
							.put("bsTSId", baseSignTimeSegmentEntity
									.getTimeSegment());
					listAll.add(mp);
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("会员查找预约挂号详细信息出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return listAll;
	}

	/**
	 * 查询医生14天的出诊记录
	 */
	public Map findAllDoctorNursesFour(String deptCodeId, String outTypeIdList) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Date nowTime = new Date();
			nowTime.setDate(nowTime.getDate() + 2);
			Date nextServenTime = new Date();
			nextServenTime.setDate(nextServenTime.getDate() + 17);
			String hql = "select distinct(doctorId) from t_PlanSignOrderEntity where deptCodeId='"
					+ deptCodeId
					+ "' and currentDate >='"
					+ sdf.format(nowTime)
					+ "' and currentDate <='"
					+ sdf.format(nextServenTime)
					+ "' and bsAPId in ("
					+ outTypeIdList.replaceAll("\"", "") + ")";
			List list = session.createSQLQuery(hql).list();
			StringBuilder outString = new StringBuilder();
			String titleString = "";
			for (int j = 0; j < 14; j++) {
				int weekInt = nowTime.getDay();// 0 = Sunday, 1 = Monday, 2 =
				// Tuesday, 3 = Wednesday, 4 =
				// Thursday, 5 = Friday, 6 =
				String weekChild = ""; // Saturday
				switch (weekInt) {
				case 0:
					weekChild = "日";
					break;
				case 1:
					weekChild = "一";
					break;
				case 2:
					weekChild = "二";
					break;
				case 3:
					weekChild = "三";
					break;
				case 4:
					weekChild = "四";
					break;
				case 5:
					weekChild = "五";
					break;
				case 6:
					weekChild = "六";
					break;

				}
				titleString += "<td width='6%' class='jclb'>"
						+ (nowTime.getMonth() + 1) + "月" + nowTime.getDate()
						+ "号<br/>[星期" + weekChild + "]</center></td>";
				nowTime.setDate(nowTime.getDate() + 1);
			}
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					outString.append("<tr>");
					String temp = "00"+list.get(i);
					String findUserhql = "from User where drdeptname5='"+temp.substring(temp.length()-6, temp.length())+"'";
					List findUserList = session.createQuery(findUserhql).list();
					if (findUserList != null && findUserList.size() > 0) {
						User user = (User) findUserList.get(0);
						Date lastTime = new Date();
						lastTime.setDate(lastTime.getDate() + 2);
						outString
								.append("<td class='jclb_7'><center>"
										+ user.getName()
										+ "</center><a href='#'><center>医生介绍</center></a></td>");
						outString.append("<td class='jclb_7'><center>" + user.getGbjaejik()
								+ "</center></td>");
						for (int j = 0; j < 14; j++) {
							String hqlIns = "from PlanSignOrderEntity where deptCodeId='"
									+ deptCodeId
									+ "' and currentDate ='"
									+ sdf.format(lastTime)
									+ "' and bsAPId in ("
									+ outTypeIdList.replaceAll("\"", "")
									+ ") and doctorId =" + list.get(i);
							List hqlInsList = session.createQuery(hqlIns)
									.list();
							if (hqlInsList != null && hqlInsList.size() == 1) {
								PlanSignOrderEntity planSignOrder = (PlanSignOrderEntity) hqlInsList
										.get(0);
								BaseSignAPEntity baseSignAP = (BaseSignAPEntity) session
										.get(BaseSignAPEntity.class,
												planSignOrder.getBsAPId());
								if(baseSignAP.getOutType().equals("停诊")){
									outString.append("<td class='jclb_7'>");
									if (baseSignAP.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAP.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAP.getOutType()
												+ "</center>");
									}
									outString.append("</td>");
								} else {
									outString
									.append("<td class='jclb_7'><a href='###' onclick=\"showPlanSignOrderPatientEntity('"
											+ user.getDrdeptname5()
											+ "','"
											+ baseSignAP.getId()
											+ "','"
											+ sdf.format(lastTime)
											+ "')\">");
									if (baseSignAP.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAP.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAP.getOutType()
												+ "</center>");
									}
									outString.append("</a></td>");
								}

							} else if (hqlInsList != null
									&& hqlInsList.size() == 2) {
								PlanSignOrderEntity planSignOrder = (PlanSignOrderEntity) hqlInsList
										.get(0);
								BaseSignAPEntity baseSignAP = (BaseSignAPEntity) session
										.get(BaseSignAPEntity.class,
												planSignOrder.getBsAPId());
								if(baseSignAP.getOutType().equals("停诊")){
									outString.append("<td class='jclb_7'>");
									if (baseSignAP.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAP.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAP.getOutType()
												+ "</center>");
									}
									PlanSignOrderEntity planSignOrderTwo = (PlanSignOrderEntity) hqlInsList
											.get(1);
									BaseSignAPEntity baseSignAPTwo = (BaseSignAPEntity) session
											.get(BaseSignAPEntity.class,
													planSignOrderTwo
															.getBsAPId());

									if (baseSignAPTwo.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAPTwo.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAPTwo.getOutType()
												+ "</center>");
									}
									outString.append("、</td>");
								} else {
									outString
									.append("<td class='jclb_7'><a href='###' onclick=\"showPlanSignOrderPatientEntity('"
											+ user.getDrdeptname5()
											+ "','"
											+ baseSignAP.getId()
											+ "','"
											+ sdf.format(lastTime) + "')\">");
									if (baseSignAP.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAP.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAP.getOutType()
												+ "</center>");
									}
									outString.append("</a>");
									PlanSignOrderEntity planSignOrderTwo = (PlanSignOrderEntity) hqlInsList
											.get(1);
									BaseSignAPEntity baseSignAPTwo = (BaseSignAPEntity) session
											.get(BaseSignAPEntity.class,
													planSignOrderTwo
															.getBsAPId());
									outString
											.append("<a href='###' onclick=\"showPlanSignOrderPatientEntity('"
													+ user.getDrdeptname5()
													+ "','"
													+ baseSignAPTwo.getId()
													+ "','"
													+ sdf.format(lastTime)
													+ "')\">");
									if (baseSignAPTwo.getTimeAP().equals("A")) {
										outString.append("<center>上午"
												+ baseSignAPTwo.getOutType()
												+ "</center>");
									} else {
										outString.append("<center>下午"
												+ baseSignAPTwo.getOutType()
												+ "</center>");
									}
									outString.append("</a></td>");
								}
							} else {
								outString.append("<td class='jclb_7'>&nbsp;</td>");
							}
							lastTime.setDate(lastTime.getDate() + 1);
						}
					}
					// outString.append("<td><a
					// href='####'><center>更多</center></a></td>");
					outString.append("</tr>");
				}
			}
			// <td bgcolor='#66CCFF'><center>后七天</center></td>
			mp.put("weekList", titleString + "</tr>" + outString);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查询医生一周出诊情况出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	public JSONObject findGrounpPlanSignOrderPatientList(int start, int limit,
			String search, String doctorId) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map> listAll = new ArrayList<Map>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql = "from PlanSignOrderPatientEntity where 1=1 and doctorId ='"+doctorId.substring(2, doctorId.length())+"'";
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if (obj.containsKey("planDate")) {
					String value = obj.getString("planDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(
									t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							hql += " and planDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
			}
			hql +=" order by planDate desc";
			List listSize = session.createQuery(hql).list();
			List list = session.createQuery(hql).setFirstResult(start)
					.setMaxResults(limit).list();
			if (list != null && list.size() > 0) {
				int sizez = list.size();
				for (int i = 0, size = list.size(); i < size; i++) {
					Map mp = new HashMap();
					PlanSignOrderPatientEntity pc = (PlanSignOrderPatientEntity) list
							.get(i);
					mp.put("id", pc.getId());
					mp.put("bianhao",sizez--);
					mp.put("patientId", pc.getPatientId());
					Patient patient = (Patient) session.get(Patient.class, pc
							.getPatientId());
					mp.put("patientName", patient.getPatientName());
					mp.put("gender", DictionaryUtil
							.getIndependentDictionaryText("gender_gb", patient
									.getGender()));
					mp.put("homeTel", patient.getHomeTel());
					mp.put("deptCode", pc.getDeptCode());
					String deptHql = "from DepartmentHISEntity where deptCode = '"
							+ pc.getDeptCode() + "'";
					List deptList = session.createQuery(deptHql).list();
					DepartmentHISEntity departmentHIS = (DepartmentHISEntity) deptList
							.get(0);
					mp.put("deptName", departmentHIS.getDeptName());
					mp.put("doctorId", pc.getDoctorId());
					String temp ="00"+pc.getDoctorId();
					String doctorHql = "from User where drdeptname5='"+temp.substring(temp.length()-6, temp.length())+"'";
					List doctorList = session.createQuery(doctorHql).list();
					User user = (User) doctorList.get(0);
					mp.put("doctorName", user.getName());
					String bsAPHql = "from BaseSignAPEntity where id="
							+ pc.getBsAPId();
					List bsAPList = session.createQuery(bsAPHql).list();
					BaseSignAPEntity baseSignAP = (BaseSignAPEntity) bsAPList
							.get(0);
					String bs = "";
					if (baseSignAP.getTimeAP().equals("A")) {
						bs += "上午";
					} else {
						bs += "下午";
					}
					if (baseSignAP.getOutType().equals("诊疗")) {
						bs += "普通门诊";
					} else if (baseSignAP.getOutType().equals("专家")) {
						bs += baseSignAP.getOutType() + "门诊";
					} else if (baseSignAP.getOutType().equals("普通")) {
						bs += "会员门诊";
					}
					mp.put("bsAPId", bs);
					mp.put("planDate", sdf.format(pc.getPlanDate()));
					BaseSignTimeSegmentEntity baseSignTimeSegmentEntity = (BaseSignTimeSegmentEntity) session
							.get(BaseSignTimeSegmentEntity.class, pc
									.getBsTSId());
					mp
							.put("bsTSId", baseSignTimeSegmentEntity
									.getTimeSegment());
					listAll.add(mp);
				}
			}
			json.put("root", listAll);
			if (listSize != null && listSize.size() > 0) {
				json.put("total", listSize.size());
			} else {
				json.put("total", 0);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("会员查找预约挂号详细信息出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return json;
	}

}
