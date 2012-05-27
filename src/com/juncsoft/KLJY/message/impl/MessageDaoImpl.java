package com.juncsoft.KLJY.message.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.message.dao.MessageDao;
import com.juncsoft.KLJY.message.entity.Message;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class MessageDaoImpl implements MessageDao {

	// 添加一条短信
	public boolean addMessage(Message message) throws Exception {
		Session session = null;
		Transaction tran = null;
		boolean isSuccess = false;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.save(message);
			tran.commit();
			isSuccess = true;
		} catch (Exception e) {
			tran.rollback();
			isSuccess = false;
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return isSuccess;
	}

	/*
	 * 医生获得医生所在科室和小组下的会员的所有短信
	 */
	public JSONObject getMessageByDid(Integer start, Integer limit,
			Long doctorId, String search, String flag, String orderBy,
			String descOrasc) throws Exception {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Message> allList = new ArrayList<Message>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String docSql = "select tda.deptCode,tda.grounpId from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="
					+ doctorId;
			List docList = session.createSQLQuery(docSql).list();
			int allCount = 0;
			if (docList != null && docList.size() > 0) {
				for (int z = 0, sizez = docList.size(); z < sizez; z++) {
					Object[] docObject = (Object[]) docList.get(z);
					String memberInfoHql = "from Message ses where ses.memberInfo.deptCode='"
							+ docObject[0].toString()
							+ "' and ses.memberInfo.grounpId="
							+ Long.parseLong(docObject[1].toString());
					if (search != null && search.length() > 0) {
						JSONObject obj = JSONObject.fromObject(search);

						if (obj.containsKey("name")) {
							if (obj.getString("name").length() > 0) {
								memberInfoHql += " and ses.memberInfo.patient.patientName like '%"
										+ obj.getString("name") + "%'";
							}
						}
						if (obj.containsKey("patientNo")) {
							if (obj.getString("patientNo").length() > 0) {
								memberInfoHql += " and ses.memberInfo.patient.patientNo like '%"
										+ obj.getString("patientNo") + "%'";
							}
						}

						/*
						 * 按查询条件 年龄来筛选 by cheng jiangyu 2011-9-22 页面查询条件框中 写 类似
						 * >60的样式
						 */
						if (obj.containsKey("age")) {
							if (obj.getString("age").length() > 0) {
								String ageCondition = obj.getString("age");
								Calendar cal = Calendar.getInstance();
								int thisYear = cal.get(Calendar.YEAR);
								if (ageCondition.contains(">")) { // 如果查询条件是年龄大于多少岁
									ageCondition = ageCondition
											.replace(">", "");
									memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) > "
											+ ageCondition;// 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
															// 转换
								} else if (ageCondition.contains("<")) { // 如果查询条件是年龄小于多少岁
									ageCondition = ageCondition
											.replace("<", "");
									memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) < "
											+ ageCondition; // 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
															// 转换
								} else { // 如果查询条件是年龄等于多少岁
									memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) = "
											+ ageCondition; // 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
															// 转换
								}
							}
						}
						// -------------------------------------------------------------------------------------------
						if (obj.containsKey("account")) {
							if (obj.getString("account").length() > 0) {
								memberInfoHql += " and ses.memberInfo.account like '%"
										+ obj.getString("account") + "%'";
							}
						}
						if (obj.containsKey("inDate")) {
							String value = obj.getString("inDate");
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
									memberInfoHql += " and ses.memberInfo.inDate between '"
											+ d[0].toLocaleString()
											+ "' and '"
											+ d[1].toLocaleString() + "'";
								}
							}
						}

						if (obj.containsKey("messageDateBegin")) {
							String value = obj.getString("messageDateBegin");
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
									memberInfoHql += " and ses.messageDate between '"
											+ d[0].toLocaleString()
											+ "' and '"
											+ d[1].toLocaleString() + "'";
								}
							}
						}
						if (obj.containsKey("diaggroup")) {
							if (obj.getString("diaggroup").length() > 0
									&& obj.get("diaggroup") != null
									&& !"null".equals(obj.getString("diaggroup"))) {
								memberInfoHql +=" and ses.memberInfo.patient.diagGrounp like '%"+ obj.getString("diaggroup") + "%'";
							//	memberInfoHql += " and ses.diaggroup like '%"
							//			+ obj.getString("diaggroup") + "%'";
							}
						}
						/*
						 * 短信发送时间 按时间段查询 by cheng jiangyu 2011-9-30
						 */
					}
					if (flag != null && flag.equals("zhu")) {
						memberInfoHql += " and ses.memberInfo.inHspTimes is not null";
					} else if (flag != null && flag.equals("men")) {
						memberInfoHql += " and ses.memberInfo.inOutTimesDate is not null";
					}
					if (orderBy != null && descOrasc != null) {
						memberInfoHql += " order by " + orderBy + " "
								+ descOrasc + "";
					}
					memberInfoHql += " order by ses.messageDate desc";
					List attemListSize = session.createQuery(memberInfoHql)
							.list();
					allCount += attemListSize.size();
					List<Message> attemList = session
							.createQuery(memberInfoHql).setFirstResult(start)
							.setMaxResults(limit).list();
					allList.addAll(attemList);
				}
			}
			tran.commit();
			json.put("root", allList);
			json.put("total", allCount);
		} catch (Exception e) {
			throw new RuntimeException("加载某个责任科室下医生的所有的会员信息出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	/*
	 * 医生获得医生所在科室和小组下的会员的所有短信 用于导出excel
	 */
	public List<Message> getMessageByDid(Long doctorId, String search,
			String flag, String orderBy, String descOrasc) throws Exception {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Message> allList = new ArrayList<Message>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String docSql = "select tda.deptCode,tda.grounpId from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="
					+ doctorId;

			List docList = session.createSQLQuery(docSql).list();
			int allCount = 0;
			if (docList != null && docList.size() > 0) {
				for (int z = 0, sizez = docList.size(); z < sizez; z++) {
					Object[] docObject = (Object[]) docList.get(z);
					String memberInfoHql = "from Message ses where ses.memberInfo.deptCode='"
							+ docObject[0].toString()
							+ "' and ses.memberInfo.grounpId="
							+ Long.parseLong(docObject[1].toString());
					if (search != null && search.length() > 0) {
						JSONObject obj = JSONObject.fromObject(search);

						if (obj.containsKey("name")) {
							if (obj.getString("name").length() > 0) {
								memberInfoHql += " and ses.memberInfo.patient.patientName like '%"
										+ obj.getString("name") + "%'";
							}
						}
						if (obj.containsKey("patientNo")) {
							if (obj.getString("patientNo").length() > 0) {
								memberInfoHql += " and ses.memberInfo.patient.patientNo='"
										+ obj.getString("patientNo") + "'";
							}
						}

						/*
						 * 按查询条件 年龄来筛选 by cheng jiangyu 2011-9-22 页面查询条件框中 写 类似
						 * >60的样式
						 */
						if (obj.containsKey("age")) {
							if (obj.getString("age").length() > 0) {
								String ageCondition = obj.getString("age");
								Calendar cal = Calendar.getInstance();
								int thisYear = cal.get(Calendar.YEAR);
								if (ageCondition.contains(">")) { // 如果查询条件是年龄大于多少岁
									ageCondition = ageCondition
											.replace(">", "");
									memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) > "
											+ ageCondition;// 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
															// 转换
								} else if (ageCondition.contains("<")) { // 如果查询条件是年龄小于多少岁
									ageCondition = ageCondition
											.replace("<", "");
									memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) < "
											+ ageCondition; // 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
															// 转换
								} else { // 如果查询条件是年龄等于多少岁
									memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) = "
											+ ageCondition; // 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
															// 转换
								}
							}
						}

						// -------------------------------------------------------------------------------------------
						if (obj.containsKey("account")) {
							if (obj.getString("account").length() > 0) {
								memberInfoHql += " and ses.memberInfo.account='"
										+ obj.getString("account") + "'";
							}
						}
						if (obj.containsKey("inDate")) {
							String value = obj.getString("inDate");
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
									memberInfoHql += " and ses.memberInfo.inDate between '"
											+ d[0].toLocaleString()
											+ "' and '"
											+ d[1].toLocaleString() + "'";
								}
							}
						}

						/*
						 * 短信发送时间 按时间段查询 by cheng jiangyu 2011-9-30
						 */
						if (obj.containsKey("messageDateBegin")) {
							String value = obj.getString("messageDateBegin");
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
									memberInfoHql += " and ses.messageDate between '"
											+ d[0].toLocaleString()
											+ "' and '"
											+ d[1].toLocaleString() + "'";
								}
							}
						}

					}
					if (flag != null && flag.equals("zhu")) {
						memberInfoHql += " and ses.memberInfo.inHspTimes is not null";
					} else if (flag != null && flag.equals("men")) {
						memberInfoHql += " and ses.memberInfo.inOutTimesDate is not null";
					}
					if (orderBy != null && descOrasc != null) {
						memberInfoHql += " order by " + orderBy + " "
								+ descOrasc + "";
					}
					memberInfoHql += " order by ses.messageDate desc";
					List attemListSize = session.createQuery(memberInfoHql)
							.list();
					allCount += attemListSize.size();
					List<Message> attemList = session
							.createQuery(memberInfoHql).list();
					allList.addAll(attemList);
				}
			}
			tran.commit();
		} catch (Exception e) {
			throw new RuntimeException("加载某个责任科室下医生的所有的会员信息出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allList;
	}

	/*
	 * 管理员获得管理员所在科室会员的所有短信
	 */
	public JSONObject getMessageByManageDecode(Integer start, Integer limit,
			String deptCode, String search, String flag, String orderBy,
			String descOrasc) throws Exception {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String memberInfoHql = "";
			if (!"".equals(deptCode)) {
				memberInfoHql = "from Message ses where ses.memberInfo.deptCode='"
						+ deptCode + "'";
			} else {
				memberInfoHql = "from Message ses where 1=1 ";
			}

			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if (obj.containsKey("name")) {
					if (obj.getString("name").length() > 0) {
						memberInfoHql += " and ses.memberInfo.patient.patientName like '%"
								+ obj.getString("name") + "%'";
					}
				}
				if (obj.containsKey("patientNo")) {
					if (obj.getString("patientNo").length() > 0) {
						memberInfoHql += " and ses.memberInfo.patient.patientNo like '%"
								+ obj.getString("patientNo") + "%'";
					}
				}

				/*
				 * 按查询条件 年龄来筛选 by cheng jiangyu 2011-9-22 页面查询条件框中 写 类似 >60的样式
				 */
				if (obj.containsKey("age")) {
					if (obj.getString("age").length() > 0) {
						String ageCondition = obj.getString("age");
						Calendar cal = Calendar.getInstance();
						int thisYear = cal.get(Calendar.YEAR);
						if (ageCondition.contains(">")) { // 如果查询条件是年龄大于多少岁
							ageCondition = ageCondition.replace(">", "");
							memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) > "
									+ ageCondition;// 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
													// 转换
						} else if (ageCondition.contains("<")) { // 如果查询条件是年龄小于多少岁
							ageCondition = ageCondition.replace("<", "");
							memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) < "
									+ ageCondition; // 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
													// 转换
						} else { // 如果查询条件是年龄等于多少岁
							memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) = "
									+ ageCondition; // 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
													// 转换
						}
					}
				}
				// -------------------------------------------------------------------------------------------
				if (obj.containsKey("account")) {
					if (obj.getString("account").length() > 0 && obj.get("account") != null && !"null".equals(obj.getString("account"))) {
						memberInfoHql += " and ses.memberInfo.account like '%"
								+ obj.getString("account") + "%'";
					}
				}
				if (obj.containsKey("inDate")) {
					String value = obj.getString("inDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							memberInfoHql += " and ses.memberInfo.inDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
				if (obj.containsKey("deptcodeValue")) {
					if (obj.getString("deptcodeValue").length() > 0 && obj.get("deptcodeValue") != null && !"null".equals(obj.getString("deptcodeValue"))) {
						memberInfoHql += " and ses.memberInfo.deptCode ='"+obj.getString("deptcodeValue")+"' ";
					}
				}

				/*
				 * 短信发送时间 按时间段查询 by cheng jiangyu 2011-9-30
				 */
				if (obj.containsKey("messageDateBegin")) {
					String value = obj.getString("messageDateBegin");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							memberInfoHql += " and ses.messageDate between '"
									+ d[0].toLocaleString() + "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}

				if (obj.containsKey("grounpId")) {
					if (obj.getString("grounpId").length() > 0
							&& obj.get("grounpId") != null
							&& !"null".equals(obj.getString("grounpId"))) {
						memberInfoHql += " and ses.memberInfo.grounpId="
								+ obj.getString("grounpId");
					}
				}
				if (obj.containsKey("diaggroup")) {
					if (obj.getString("diaggroup").length() > 0
							&& obj.get("diaggroup") != null
							&& !"null".equals(obj.getString("diaggroup"))) {
						memberInfoHql +=" and ses.memberInfo.patient.diagGrounp like '%"+ obj.getString("diaggroup") + "%'";
					//	memberInfoHql += " and ses.diaggroup like '%"
					//			+ obj.getString("diaggroup") + "%'";
					}
				}
			} 
			if (flag != null && flag.equals("zhu")) {
				memberInfoHql += " and ses.memberInfo.inHspTimes is not null";
			} else if (flag != null && flag.equals("men")) {
				memberInfoHql += " and ses.memberInfo.inOutTimesDate is not null";
			}
			if (orderBy != null && descOrasc != null) {
				memberInfoHql += " order by " + orderBy + " " + descOrasc + "";
			}
			memberInfoHql += " order by ses.messageDate desc";
			List attemListSize = session.createQuery(memberInfoHql).list();
			int allCount = attemListSize.size();
			List<Message> allList = new ArrayList<Message>();
			allList = session.createQuery(memberInfoHql).setFirstResult(start)
					.setMaxResults(limit).list();
			// }
			// }
			tran.commit();
			json.put("root", allList);
			json.put("total", allCount);
		} catch (Exception e) {
			throw new RuntimeException("加载某个责任科室下医生的所有的会员信息出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	/*
	 * 管理员获得管理员所在科室会员的所有短信 用于导出excel
	 */
	public List<Message> getMessageByManageDecode(String deptCode,
			String search, String flag, String orderBy, String descOrasc)
			throws Exception {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Message> allList = new ArrayList<Message>();
		int allCount = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String memberInfoHql = "from Message ses where 1=1 ";
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if (obj.containsKey("name")) {
					if (obj.getString("name").length() > 0) {
						memberInfoHql += " and ses.memberInfo.patient.patientName like '%"
								+ obj.getString("name") + "%'";
					}
				}
				if (obj.containsKey("patientNo")) {
					if (obj.getString("patientNo").length() > 0) {
						memberInfoHql += " and ses.memberInfo.patient.patientNo like '%"
								+ obj.getString("patientNo") + "%'";
					}
				}

				/*
				 * 按查询条件 年龄来筛选 by cheng jiangyu 2011-9-22 页面查询条件框中 写 类似 >60的样式
				 */
				if (obj.containsKey("age")) {
					if (obj.getString("age").length() > 0) {
						String ageCondition = obj.getString("age");
						Calendar cal = Calendar.getInstance();
						int thisYear = cal.get(Calendar.YEAR);
						if (ageCondition.contains(">")) { // 如果查询条件是年龄大于多少岁
							ageCondition = ageCondition.replace(">", "");
							memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) > "
									+ ageCondition;// 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
													// 转换
						} else if (ageCondition.contains("<")) { // 如果查询条件是年龄小于多少岁
							ageCondition = ageCondition.replace("<", "");
							memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) < "
									+ ageCondition; // 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
													// 转换
						} else { // 如果查询条件是年龄等于多少岁
							memberInfoHql += " and datediff(year,ses.memberInfo.patient.birthday,getdate()) = "
									+ ageCondition; // 注意这里数据库中birthday字段是datetime类型，模糊查询要用convert
													// 转换
						}
					}
				}
				// -------------------------------------------------------------------------------------------
				if (obj.containsKey("account")) {
					if (obj.getString("account").length() > 0) {
						memberInfoHql += " and ses.memberInfo.account like '%"
								+ obj.getString("account") + "%'";
					}
				}
				if (obj.containsKey("inDate")) {
					String value = obj.getString("inDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							memberInfoHql += " and ses.memberInfo.inDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}

				/*
				 * 短信发送时间 按时间段查询 by cheng jiangyu 2011-9-30
				 */
				if (obj.containsKey("messageDateBegin")) {
					String value = obj.getString("messageDateBegin");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							memberInfoHql += " and ses.messageDate between '"
									+ d[0].toLocaleString() + "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}

				if (obj.containsKey("grounpId")) {
					if (obj.getString("grounpId").length() > 0
							&& obj.get("grounpId") != null
							&& !"null".equals(obj.getString("grounpId"))) {
						memberInfoHql += " and ses.memberInfo.grounpId="
								+ obj.getString("grounpId");
					}
				}
				
				if (obj.containsKey("deptcodeValue")) {
					if (obj.getString("deptcodeValue").length() > 0
							&& obj.get("deptcodeValue") != null
							&& !"null".equals(obj.getString("deptcodeValue"))) {
						memberInfoHql += " and ses.memberInfo.deptCode='"+ obj.getString("deptcodeValue")+"'";
					}
				}
			} else {
				if(!"undefined".equals(deptCode)){
					memberInfoHql += " and ses.memberInfo.deptCode='" + deptCode
					+ "'";
				}
			}
			if (flag != null && flag.equals("zhu")) {
				memberInfoHql += " and ses.memberInfo.inHspTimes is not null";
			} else if (flag != null && flag.equals("men")) {
				memberInfoHql += " and ses.memberInfo.inOutTimesDate is not null";
			}
			if (orderBy != null && descOrasc != null) {
				memberInfoHql += " order by " + orderBy + " " + descOrasc + "";
			}
			memberInfoHql += " order by ses.messageDate desc";
			allList = session.createQuery(memberInfoHql).list();
			tran.commit();
		} catch (Exception e) {
			throw new RuntimeException("加载某个责任科室下医生的所有的会员信息出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allList;
	}

	/*
	 * 管理员登录后选择多条记录导出excel 由ids获得所有短信 用于导出excel
	 */
	public List<Message> getMessageByIds(String ids) throws Exception {
		Session session = null;
		Transaction tran = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Message> allList = new ArrayList<Message>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String manageMessageHql = "from Message ses where id in(" + ids
					+ ") order by messageDate desc";
			allList = session.createQuery(manageMessageHql).list();
			tran.commit();
		} catch (Exception e) {
			throw new RuntimeException("加载某个责任科室下医生的所有的会员信息出错", e);
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return allList;
	}

	/**
	 * 多次发送短信
	 */
	public boolean addMessage(Message message, Session session)
			throws Exception {
		boolean isSuccess = false;
		try {
			session.save(message);
			isSuccess = true;
		} catch (Exception e) {
			isSuccess = false;
			throw e;
		}
		return isSuccess;
	}

}
