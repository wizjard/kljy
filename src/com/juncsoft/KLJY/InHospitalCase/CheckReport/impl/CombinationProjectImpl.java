package com.juncsoft.KLJY.InHospitalCase.CheckReport.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.dao.CombinationProjectDao;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.DateVo;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportForm;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportFormforPad;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportFormforRemote;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportItem;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportItemforRemote;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.StrModifyVo;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.TestItem;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.TestItemforRemote;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.user.action.HisService;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class CombinationProjectImpl implements CombinationProjectDao {

	public String getCombinationProject(String patientId, String reportDate) {
		Session session = null;
		Transaction tran = null;
		StringBuffer sb = new StringBuffer();
		String retStr = null;
		try {

			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (patientId.equals("allComProj")) {// 新增记录
				List<TestItem> list = session.createQuery(
						"from TestItem where isProfile = 1").list();
				for (TestItem testItem : list) {
					sb.append("{text:'").append(testItem.getCname()).append(
							"',leaf:true},");
				}
			} else { // 关联历史记录
				// 查找病人病案号
				Patient patient = (Patient) session.get(Patient.class,
						(long) Integer.parseInt(patientId));
				// 查找病人编号（来自HIS）

				// liugang 2011-08-11 start
				List<String> list = new ArrayList<String>();
				list.add(patient.getPatientId());
				// liugang 2011-08-11 end
				List<String> _list = removeDuplicateObj(list);
				if (_list.size() == 0)
					return null;

				StringBuffer _sb = new StringBuffer();
				for (int i = 0; i < _list.size(); i++) {
					if (i == _list.size() - 1) {
						_sb.append("patNo = ?");
					} else {
						_sb.append("patNo = ? or ");
					}
				}

				if (reportDate.equals("allDate")) {// 返回时间结点
					Query query = session
							.createQuery("from ReportFormforRemote where "
									+ _sb.toString()
									+ " and isFromOutHospital is null order by receiveDate ASC");
					for (int i = 0; i < _list.size(); i++) {
						query.setString(i, _list.get(i));
					}
					List<ReportFormforRemote> rflist = query.list();
					List temp = new ArrayList();

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					for (ReportFormforRemote rf : rflist) {
						temp.add(sdf.format(rf.getReceiveDate()));
					}
					List reportDateList = sort(removeDuplicateObj(temp));

					for (int j = 0; j < reportDateList.size(); j++) {
						DateVo dateVo = (DateVo) reportDateList.get(j);
						sb.append("{text:'").append(dateVo.getDate()).append(
								"',leaf:false},");
					}
				} else {// 返回时间节点下的项目结点
					Query query = session
							.createQuery("from ReportFormforRemote where ("
									+ _sb.toString()
									+ ") and receiveDate = ? and isFromOutHospital is null order by receiveDate ASC");
					for (int j = 0; j < _list.size(); j++) {
						query.setString(j, _list.get(j));
					}
					// query.setDate(_list.size(),
					// java.text.DateFormat.getDateInstance().parse(reportDate));原赋值方式
					query.setString(_list.size(), reportDate);
					List<ReportFormforRemote> rflist = query.list();

					// 根据ReportForm表查出的每一条记录循环查询ReportItem表
					for (int i = 0; i < rflist.size(); i++) {
						ReportFormforRemote rf = rflist.get(i);
						List<ReportItemforRemote> rilist = session
								.createQuery(
										"from ReportItemforRemote where receiveDate = ? and sectionNo = ? "
												+ "and testTypeNo = ? and sampleNo = ?")
								.setDate(0, rf.getReceiveDate()).setInteger(1,
										rf.getSectionNo()).setInteger(2,
										rf.getTestTypeNo()).setString(3,
										rf.getSampleNo()).list();
						sb.append(getComProByTimeFirstOne(rilist, session)
								.toString());
					}
				}
			}
			String str = sb.toString();
			if (str.length() > 0) {
				retStr = str.substring(0, str.length() - 1);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return retStr;
	}

	public List<ReportFormforPad> getReportsByPatientForPad(String patientId) {
		Session session = null;
		Transaction tran = null;
		StringBuffer sb = new StringBuffer();
		String retStr = null;
		List<ReportFormforPad> reportResult = new ArrayList<ReportFormforPad>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();

			Patient patient = (Patient) session.get(Patient.class,
					(long) Integer.parseInt(patientId));
			List<String> _list = new ArrayList();
			StringBuffer _sb = new StringBuffer();
			for (int i = 0; i < _list.size(); i++) {
				if (i == _list.size() - 1) {
					_sb.append("patNo = ?");
				} else {
					_sb.append("patNo = ? or ");
				}
			}
			_sb.append("patNo = ?");

			Query query = session
					.createQuery("from ReportFormforRemote where "
							+ _sb.toString()
							+ " and (isPatientOrDoctorWriteZanCun is null or guiDangDate is not NULL)  order by receiveDate ASC");
			for (int i = 0; i < _list.size(); i++) {
				query.setString(i, _list.get(i));
			}
			query.setString(0, patient.getPatientId() + "");
			List<ReportFormforRemote> rflist = query.list();

			// 根据ReportForm表查出的每一条记录循环查询ReportItem表
			for (int i = 0; i < rflist.size(); i++) {
				ReportFormforRemote rf = rflist.get(i);
				List<ReportItemforRemote> rilist = session
						.createQuery(
								"from ReportItemforRemote where receiveDate = ? and sectionNo = ? and testTypeNo = ? and sampleNo = ?")
						.setDate(0, rf.getReceiveDate()).setInteger(1,
								rf.getSectionNo()).setInteger(2,
								rf.getTestTypeNo()).setString(3,
								rf.getSampleNo()).list();

				List temp = new ArrayList();
				// 把查出的每条记录中的ParItemNo字段放到List中，并进行下面的处理，使List中的ParItemNo字段不存在重复
				for (ReportItemforRemote ri : rilist) {
					String _dateStr = ri.getReceiveDate().toString().substring(
							0, 10);
					String _str = ri.getParItemNo() + "," + ri.getIsAdd() + ","
							+ _dateStr + "," + ri.getSectionNo() + ","
							+ ri.getTestTypeNo() + "," + ri.getSampleNo();
					temp.add(_str);
				}
				List pitemList = removeDuplicateObj(temp);
				// 根据ParItemNo字段值查询相关表获得其所对应的CName
				for (int j = 0; j < pitemList.size(); j++) {
					String[] _str = pitemList.get(j).toString().split(",");
					String strofItemNo = _str[0];
					int isAdd = Integer.parseInt(_str[1]);
					String receiveDate = _str[2];
					String sectionNo = _str[3];
					String testTypeNo = _str[4];
					String sampleNo = _str[5];
					String parItemNo = strofItemNo;
					ReportFormforPad tempReport = new ReportFormforPad();
					if (0 == isAdd) {
						List<TestItemforRemote> testItemList = session
								.createQuery(
										"from TestItemforRemote where itemNo = ?")
								.setInteger(0, Integer.parseInt(strofItemNo))
								.list();
						if (testItemList.isEmpty())
							continue;
						TestItemforRemote testItem = testItemList.get(0);
						tempReport.setText(testItem.getCname());
						tempReport.setReceiveDate(receiveDate);
						tempReport.setSectionNo(sectionNo);
						tempReport.setTestTypeNo(testTypeNo);
						tempReport.setSampleNo(sampleNo);
						tempReport.setParItemNo(parItemNo);
						tempReport.setHospital(rf.getHospitalName());
						tempReport.setReportFormforRemoteId(rf.getId());
						if(rf.getGuiDangDate() != null){
							tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
						}else{
							tempReport.setGuiDangDate("");
						}
						
						if (rf.getIsFromOutHospital() != null) {
							tempReport.setIsFromOutHispital(rf
									.getIsFromOutHospital().toString());
						} else {
							tempReport.setIsFromOutHispital("0");
						}
						if (rf.getIsPatientOrDoctorWriteZanCun() != null) {
							tempReport.setIsPatientOrDoctorWriteZanCun(rf
									.getIsPatientOrDoctorWriteZanCun()
									.toString());
						} else {
							tempReport.setIsPatientOrDoctorWriteZanCun("");
						}
						reportResult.add(tempReport);
					} else {
						List<TestItem> testItemList = session.createQuery(
								"from TestItem where itemNo = ?").setInteger(0,
								Integer.parseInt(strofItemNo)).list();
						if (testItemList.isEmpty())
							continue;
						TestItem testItem = testItemList.get(0);
						tempReport.setText(testItem.getCname());
						tempReport.setReceiveDate(receiveDate);
						tempReport.setSectionNo(sectionNo);
						tempReport.setTestTypeNo(testTypeNo);
						tempReport.setSampleNo(sampleNo);
						tempReport.setParItemNo(parItemNo);
						tempReport.setHospital(rf.getHospitalName());
						tempReport.setReportFormforRemoteId(rf.getId());
						if(rf.getGuiDangDate() != null){
							tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
						}else{
							tempReport.setGuiDangDate("");
						}
						if (rf.getIsFromOutHospital() != null) {
							tempReport.setIsFromOutHispital(rf
									.getIsFromOutHospital().toString());
						} else {
							tempReport.setIsFromOutHispital("0");
						}
						if (rf.getIsPatientOrDoctorWriteZanCun() != null) {
							tempReport.setIsPatientOrDoctorWriteZanCun(rf
									.getIsPatientOrDoctorWriteZanCun()
									.toString());
						} else {
							tempReport.setIsPatientOrDoctorWriteZanCun("");
						}
						reportResult.add(tempReport);
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return reportResult;
	}

	// 根据用户选择的时间查询诊断结果
	public String getCombinationProjectByAnytime(String patientId,
			String reportDate) {
		Session session = null;
		Transaction tran = null;
		StringBuffer sb = new StringBuffer();
		String retStr = null;
		try {

			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (patientId.equals("allComProj")) {// 新增记录
				List<TestItem> list = session.createQuery(
						"from TestItem where isProfile = 1").list();
				for (TestItem testItem : list) {
					sb.append("{text:'").append(testItem.getCname()).append(
							"',leaf:true},");
				}
			} else { // 关联历史记录
				// 查找病人病案号
				Patient patient = (Patient) session.get(Patient.class,
						(long) Integer.parseInt(patientId));
				// 查找病人编号（来自HIS）

				List<String> list = new ArrayList<String>();
				list.add(patient.getPatientId());
				List<String> _list = removeDuplicateObj(list);
				if (_list.size() == 0)
					return null;

				StringBuffer _sb = new StringBuffer();
				for (int i = 0; i < _list.size(); i++) {
					if (i == _list.size() - 1) {
						_sb.append("patNo = ?");
					} else {
						_sb.append("patNo = ? or ");
					}
				}

				// 如果reportDate为空默认先前查一个月，
				if (reportDate == null || reportDate.equals("")) {

				}
				// reportDate=no是查询最近一个月诊断结果的标示
				if (reportDate.contains("no") || reportDate.equals("no")) {// (当只点击菜单栏)只返回时间结点(设置树节点leaf=false当用户点击后展开)
					Query query = session
							.createQuery("from ReportFormforRemote where ("
									+ _sb.toString()
									+ ") and receiveDate >= ? and isFromOutHospital is null order by receiveDate ASC");
					for (int i = 0; i < _list.size(); i++) {
						query.setString(i, _list.get(i));
					}
					// 此时的reportDate带有标示必须进行处理
					if (reportDate.equals("no")) {// 说明是查询近一个月的记录
						Date dt = new Date();// 获取当前时间的前一个月
						Calendar cld = Calendar.getInstance();
						cld.setTime(dt);
						cld.add(cld.MONTH, -1);// 提前一个月
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						reportDate = sdf.format(cld.getTime());// 往前推一个月赋值给reportDate
					}
					reportDate = reportDate.replaceAll("no", "");
					// query.setDate(_list.size(),java.text.DateFormat.getDateInstance().parse(reportDate));
					query.setString(_list.size(), reportDate);
					List<ReportFormforRemote> rflist = query.list();
					List temp = new ArrayList();

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					for (ReportFormforRemote rf : rflist) {
						temp.add(sdf.format(rf.getReceiveDate()));
					}
					List reportDateList = sort(removeDuplicateObj(temp));

					for (int j = 0; j < reportDateList.size(); j++) {
						DateVo dateVo = (DateVo) reportDateList.get(j);
						sb.append("{text:'").append(dateVo.getDate()).append(
								"',leaf:false},");
					}
				} else {// 返回时间节点下的项目结点
					Query query = session
							.createQuery("from ReportFormforRemote where ("
									+ _sb.toString()
									+ ") and receiveDate = ? and isFromOutHospital is null order by receiveDate ASC");
					for (int j = 0; j < _list.size(); j++) {
						query.setString(j, _list.get(j));
					}
					query.setDate(_list.size(), java.text.DateFormat
							.getDateInstance().parse(reportDate));
					List<ReportFormforRemote> rflist = query.list();

					// 根据ReportForm表查出的每一条记录循环查询ReportItem表
					for (int i = 0; i < rflist.size(); i++) {
						ReportFormforRemote rf = rflist.get(i);
						List<ReportItemforRemote> rilist = session
								.createQuery(
										"from ReportItemforRemote where receiveDate = ? and sectionNo = ? "
												+ "and testTypeNo = ? and sampleNo = ?")
								.setDate(0, rf.getReceiveDate()).setInteger(1,
										rf.getSectionNo()).setInteger(2,
										rf.getTestTypeNo()).setString(3,
										rf.getSampleNo()).list();
						sb.append(getComProByTimeFirst(rilist, session)
								.toString());
					}
				}
				// System.out.println("imple："+sb+"\n");
			}
			String str = sb.toString();
			if (str.length() > 0) {
				retStr = str.substring(0, str.length() - 1);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return retStr;
	}

	public String getCombinationProjectofOutPatient(String patientId,
			String reportDate) {
		Session session = null;
		Transaction tran = null;
		StringBuffer sb = new StringBuffer();
		String retStr = null;
		try {

			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (patientId.equals("allComProj")) {// 新增记录
				List<TestItem> list = session.createQuery(
						"from TestItem where isProfile = 1").list();
				for (TestItem testItem : list) {
					sb.append("{text:'").append(testItem.getCname()).append(
							"',leaf:true},");
				}
			} else { // 关联历史记录
				// 查找病人病案号
				Patient patient = (Patient) session.get(Patient.class,
						(long) Integer.parseInt(patientId));

				if (reportDate.equals("allDate")) {// 返回时间结点
					List<ReportFormforRemote> rflist = session
							.createQuery(
									"from ReportFormforRemote where patNo = ? and isFromOutHospital is null order by receiveDate ASC")
							.setString(0, patient.getPatientId()).list();

					List temp = new ArrayList();

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					for (ReportFormforRemote rf : rflist) {
						temp.add(sdf.format(rf.getReceiveDate()));
					}
					List reportDateList = sort(removeDuplicateObj(temp));

					for (int j = 0; j < reportDateList.size(); j++) {
						DateVo dateVo = (DateVo) reportDateList.get(j);
						sb.append("{text:'").append(dateVo.getDate()).append(
								"',leaf:false},");
					}
				} else {// 返回时间节点下的项目结点
					List<ReportFormforRemote> rflist = session
							.createQuery(
									"from ReportFormforRemote where patNo = ? and receiveDate = ? and isFromOutHospital is null order by receiveDate ASC")
							.setString(0, patient.getPatientId()).setDate(
									1,
									java.text.DateFormat.getDateInstance()
											.parse(reportDate)).list();

					// 根据ReportForm表查出的每一条记录循环查询ReportItem表
					for (int i = 0; i < rflist.size(); i++) {
						ReportFormforRemote rf = rflist.get(i);
						List<ReportItemforRemote> rilist = session
								.createQuery(
										"from ReportItemforRemote where receiveDate = ? and sectionNo = ? "
												+ "and testTypeNo = ? and sampleNo = ?")
								.setDate(0, rf.getReceiveDate()).setInteger(1,
										rf.getSectionNo()).setInteger(2,
										rf.getTestTypeNo()).setString(3,
										rf.getSampleNo()).list();
						sb.append(getComProByTimeFirst(rilist, session)
								.toString());
					}
				}
			}
			String str = sb.toString();
			if (str.length() > 0) {
				retStr = str.substring(0, str.length() - 1);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return retStr;
	}

	public StringBuffer getComProByTimeFirst(List<ReportItemforRemote> rilist,
			Session session) {
		StringBuffer sb = new StringBuffer();
		List temp = new ArrayList();
		// 把查出的每条记录中的ParItemNo字段放到List中，并进行下面的处理，使List中的ParItemNo字段不存在重复
		for (ReportItemforRemote ri : rilist) {
			String _dateStr = ri.getReceiveDate().toString().substring(0, 10);
			String _str = ri.getParItemNo() + "," + ri.getIsAdd() + ","
					+ _dateStr + "," + ri.getSectionNo() + ","
					+ ri.getTestTypeNo() + "," + ri.getSampleNo();
			temp.add(_str);
		}
		List pitemList = removeDuplicateObj(temp);
		// 根据ParItemNo字段值查询相关表获得其所对应的CName
		for (int i = 0; i < pitemList.size(); i++) {
			String[] _str = pitemList.get(i).toString().split(",");
			String strofItemNo = _str[0];
			int isAdd = Integer.parseInt(_str[1]);
			String receiveDate = _str[2];
			String sectionNo = _str[3];
			String testTypeNo = _str[4];
			String sampleNo = _str[5];
			String parItemNo = strofItemNo;
			if (0 == isAdd) {
				List<TestItemforRemote> testItemList = session.createQuery(
						"from TestItemforRemote where itemNo = ?").setInteger(
						0, Integer.parseInt(strofItemNo)).list();
				if (testItemList.isEmpty())
					continue;
				TestItemforRemote testItem = testItemList.get(0);
				sb.append("{text:'").append(testItem.getCname()).append(
						"',receiveDate:'").append(receiveDate).append(
						"',sectionNo:'").append(sectionNo).append(
						"',testTypeNo:'").append(testTypeNo).append(
						"',sampleNo:'").append(sampleNo)
						.append("',parItemNo:'").append(parItemNo).append(
								"',leaf:true},");
			} else {
				List<TestItem> testItemList = session.createQuery(
						"from TestItem where itemNo = ?").setInteger(0,
						Integer.parseInt(strofItemNo)).list();
				if (testItemList.isEmpty())
					continue;
				TestItem testItem = testItemList.get(0);
				sb.append("{text:'").append(testItem.getCname()).append(
						"',receiveDate:'").append(receiveDate).append(
						"',sectionNo:'").append(sectionNo).append(
						"',testTypeNo:'").append(testTypeNo).append(
						"',sampleNo:'").append(sampleNo)
						.append("',parItemNo:'").append(parItemNo).append(
								"',leaf:true},");
			}

		}
		return sb;
	}
	
	public StringBuffer getComProByTimeFirstOne(List<ReportItemforRemote> rilist,
			Session session) {
		StringBuffer sb = new StringBuffer();
		List temp = new ArrayList();
		// 把查出的每条记录中的ParItemNo字段放到List中，并进行下面的处理，使List中的ParItemNo字段不存在重复
//		for (ReportItemforRemote ri : rilist) {
			ReportItemforRemote ri =rilist.get(0);
			String _dateStr = ri.getReceiveDate().toString().substring(0, 10);
			String _str1 = ri.getParItemNo() + "," + ri.getIsAdd() + ","
					+ _dateStr + "," + ri.getSectionNo() + ","
					+ ri.getTestTypeNo() + "," + ri.getSampleNo();
			temp.add(_str1);
//		}
		List pitemList = removeDuplicateObj(temp);
		// 根据ParItemNo字段值查询相关表获得其所对应的CName
		for (int i = 0; i < pitemList.size(); i++) {
			String[] _str = pitemList.get(i).toString().split(",");
			String strofItemNo = _str[0];
			int isAdd = Integer.parseInt(_str[1]);
			String receiveDate = _str[2];
			String sectionNo = _str[3];
			String testTypeNo = _str[4];
			String sampleNo = _str[5];
			String parItemNo = strofItemNo;
			if (0 == isAdd) {
				List<TestItemforRemote> testItemList = session.createQuery(
						"from TestItemforRemote where itemNo = ?").setInteger(
						0, Integer.parseInt(strofItemNo)).list();
				if (testItemList.isEmpty())
					continue;
				TestItemforRemote testItem = testItemList.get(0);
				sb.append("{text:'").append(testItem.getCname()).append(
						"',receiveDate:'").append(receiveDate).append(
						"',sectionNo:'").append(sectionNo).append(
						"',testTypeNo:'").append(testTypeNo).append(
						"',sampleNo:'").append(sampleNo)
						.append("',parItemNo:'").append(parItemNo).append(
								"',leaf:true},");
			} else {
				List<TestItem> testItemList = session.createQuery(
						"from TestItem where itemNo = ?").setInteger(0,
						Integer.parseInt(strofItemNo)).list();
				if (testItemList.isEmpty())
					continue;
				TestItem testItem = testItemList.get(0);
				sb.append("{text:'").append(testItem.getCname()).append(
						"',receiveDate:'").append(receiveDate).append(
						"',sectionNo:'").append(sectionNo).append(
						"',testTypeNo:'").append(testTypeNo).append(
						"',sampleNo:'").append(sampleNo)
						.append("',parItemNo:'").append(parItemNo).append(
								"',leaf:true},");
			}

		}
		return sb;
	}


	private static List removeDuplicateObj(List list) {
		Set set = new HashSet(list);
		// 将Set中的集合，放到一个临时的链表中(tempList)
		Iterator iterator = set.iterator();
		List tempList = new ArrayList();
		int i = 0;
		while (iterator.hasNext()) {
			tempList.add(iterator.next().toString());
			i++;
		}

		return tempList;
	}

	private static List sort(List list) {
		List<DateVo> retlist = new ArrayList<DateVo>();
		for (int i = 0; i < list.size(); i++) {
			DateVo dateVo = new DateVo();
			dateVo.setDate(list.get(i).toString());
			retlist.add(dateVo);
		}
		Collections.sort(retlist, new Comparator<DateVo>() {
			public int compare(DateVo arg0, DateVo arg1) {
				return arg0.getDate().compareTo(arg1.getDate());
			}
		});
		return retlist;
	}

	private static String modifyStr(String str) {
		JSONArray arr = JSONArray.fromObject("[" + str + "]");
		List<StrModifyVo> list = new ArrayList<StrModifyVo>();
		for (int i = 0; i < arr.size(); i++) {
			JSONObject object = arr.getJSONObject(i);
			StrModifyVo sm = new StrModifyVo();
			sm.setChildren(object.getJSONArray("children"));
			sm.setText(object.getString("text"));
			sm.setLeaf(object.getBoolean("leaf"));
			list.add(sm);
		}
		Collections.sort(list, new Comparator<StrModifyVo>() {
			public int compare(StrModifyVo arg0, StrModifyVo arg1) {
				return arg0.getText().compareTo(arg1.getText());
			}
		});
		StringBuffer sb = new StringBuffer();
		if (list.size() == 1) {
			sb.append("{text:'").append(list.get(0).getText()).append(
					"',leaf:false,children:").append(list.get(0).getChildren())
					.append("}");
		} else {
			int temp = 0;
			for (int i = 0; i < list.size() - 1; i++) {
				StrModifyVo sm = list.get(i);
				StrModifyVo nextsm = list.get(i + 1);
				if (sm.getChildren().size() == 0)
					continue;
				if (sm.getText().equals(nextsm.getText())) {
					temp++;
					String strsm = sm.getChildren().toString()
							.replace("]", ",");
					String strnextsm = nextsm.getChildren().toString().replace(
							"[", "");
					list.get(i + 1).setChildren(
							JSONArray.fromObject(strsm + strnextsm));
					continue;
				}
				if (temp != 0) {
					sb.append("{text:'").append(sm.getText()).append(
							"',leaf:false,children:").append(sm.getChildren())
							.append("},");
				} else {
					sb.append("{text:'").append(sm.getText()).append(
							"',leaf:false,children:").append(sm.getChildren())
							.append("},");
				}
				temp = 0;
			}
			sb.append("{text:'").append(list.get(list.size() - 1).getText())
					.append("',leaf:false,children:").append(
							list.get(list.size() - 1).getChildren())
					.append("}");
		}
		return sb.toString();
	}

	// liugang 2011-09-02 start 根据一次入院记录查找检验检查结果
	public String getCombinationProjectByStartTimeAndEndTime(String patientId,
			String kid, String reportDate) {
		Session session = null;
		Transaction tran = null;
		StringBuffer sb = new StringBuffer();
		String retStr = null;
		try {

			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (patientId.equals("allComProj")) {// 新增记录
				List<TestItem> list = session.createQuery(
						"from TestItem where isProfile = 1").list();
				for (TestItem testItem : list) {
					sb.append("{text:'").append(testItem.getCname()).append(
							"',leaf:true},");
				}
			} else { // 关联历史记录
				// 查找病人病案号
				Patient patient = (Patient) session.get(Patient.class,
						(long) Integer.parseInt(patientId));
				// 查找病人编号（来自HIS）

				List<String> list = new ArrayList<String>();
				list.add(patient.getPatientId());
				List<String> _list = removeDuplicateObj(list);
				if (_list.size() == 0)
					return null;

				StringBuffer _sb = new StringBuffer();
				for (int i = 0; i < _list.size(); i++) {
					if (i == _list.size() - 1) {
						_sb.append("patNo = ?");
					} else {
						_sb.append("patNo = ? or ");
					}
				}
				if (reportDate.contains("no") || reportDate.equals("no")) {
					// 先开始查找选中的住院记录
					InHspCaseMaster caseM = (InHspCaseMaster) session.get(
							InHspCaseMaster.class, Long.parseLong(kid));
					Query query = null;
					if (caseM != null && caseM.getInHspDate() != null
							&& caseM.getOutDate() != null) {
						query = session
								.createQuery("from ReportFormforRemote where ("
										+ _sb.toString()
										+ ") and receiveDate between '"
										+ caseM.getInHspDate()
										+ "' and '"
										+ caseM.getOutDate()
										+ "'and isFromOutHospital is null order by receiveDate ASC");
					} else if (caseM != null && caseM.getInHspDate() != null
							&& caseM.getOutDate() == null) {
						String sql = "select outHspDate from t_InHsp_InHospital_Index_One where caseId ="
								+ kid;
						List outList = session.createSQLQuery(sql).list();
						if (outList != null && outList.size() == 1) {
							query = session
									.createQuery("from ReportFormforRemote where ("
											+ _sb.toString()
											+ ") and receiveDate between '"
											+ caseM.getInHspDate()
											+ "' and '"
											+ outList.get(0)
											+ "' and isFromOutHospital is null order by receiveDate ASC");
						} else {
							query = session
									.createQuery("from ReportFormforRemote where ("
											+ _sb.toString()
											+ ") and receiveDate >= '"
											+ caseM.getInHspDate()
											+ "' and isFromOutHospital is null order by receiveDate ASC");
						}
					}
					for (int i = 0; i < _list.size(); i++) {
						query.setString(i, _list.get(i));
					}
					List<ReportFormforRemote> rflist = query.list();
					List temp = new ArrayList();

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					for (ReportFormforRemote rf : rflist) {
						temp.add(sdf.format(rf.getReceiveDate()));
					}
					List reportDateList = sort(removeDuplicateObj(temp));

					for (int j = 0; j < reportDateList.size(); j++) {
						DateVo dateVo = (DateVo) reportDateList.get(j);
						sb.append("{text:'").append(dateVo.getDate()).append(
								"',leaf:false},");
					}
				} else {
					List<ReportFormforRemote> rflist = session
							.createQuery(
									"from ReportFormforRemote where patNo = ? and receiveDate = ? and isFromOutHospital is null order by receiveDate ASC")
							.setString(0, patient.getPatientId()).setDate(
									1,
									java.text.DateFormat.getDateInstance()
											.parse(reportDate)).list();

					// 根据ReportForm表查出的每一条记录循环查询ReportItem表
					for (int i = 0; i < rflist.size(); i++) {
						ReportFormforRemote rf = rflist.get(i);
						List<ReportItemforRemote> rilist = session
								.createQuery(
										"from ReportItemforRemote where receiveDate = ? and sectionNo = ? "
												+ "and testTypeNo = ? and sampleNo = ?")
								.setDate(0, rf.getReceiveDate()).setInteger(1,
										rf.getSectionNo()).setInteger(2,
										rf.getTestTypeNo()).setString(3,
										rf.getSampleNo()).list();
						sb.append(getComProByTimeFirst(rilist, session)
								.toString());
					}
				}
			}
			String str = sb.toString();
			if (str.length() > 0) {
				retStr = str.substring(0, str.length() - 1);
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return retStr;
	}

	/**
	 * 责任医生登录之后加载除会员暂存以外的所有外院录入的化验单
	 */
	public List<ReportFormforPad> getMyGrounpCheckReportsFor(Long doctorId, String search) {
		Session session = null;
		Transaction tran = null;
		StringBuffer sb = new StringBuffer();
		String retStr = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReportFormforPad> reportResult = new ArrayList<ReportFormforPad>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="
					+ doctorId;
			List docList = session.createSQLQuery(docSql).list();
			StringBuilder sbl = null;
			if (docList != null && docList.size() > 0) {
				Object[] docObject = (Object[]) docList.get(0);
				String memberInfoHql = "select pa.patientId from MemberInfo mem left join t_PlanCount pc on mem.patient= pc.patientId ";
				// liugang 2011-09-14 update 添加病人姓名查询
				memberInfoHql += " inner join t_patient pa on pa.id = mem.patient  where mem.deptCode='"
						+ docObject[1] + "' and mem.grounpId=" + docObject[2];
				List list = session.createSQLQuery(memberInfoHql).list();
				if (list != null && list.size() > 0) {
					sbl = new StringBuilder();
					for (int si = 0, size = list.size(); si < size; si++) {
						if(list.get(si) != null){
							String objS = list.get(si).toString();
							if (si < size - 1) {
								sbl.append("'" + objS + "',");
							} else {
								sbl.append("'" + objS + "'");
							}
						}
					}
				}
			}
			if (sbl != null) {
				conn = DatabaseUtil.getConnection();
				String reportHql = "from ReportFormforRemote where patNo in ("
								+ sbl.toString()
								+ ") and"
								+ " isFromOutHospital =1 ";
				boolean flag = true;
				if (search != null && search.length() > 0) {
					JSONObject obj = JSONObject.fromObject(search);
					if(obj.containsKey("doctor")){
						if (obj.getString("doctor").length() > 0 && !"".equals(obj.getString("doctor"))){
							reportHql += " and doctor like '%"+obj.getString("doctor")+"%' ";
						}
					}
					if(obj.containsKey("collecter")){
						if (obj.getString("collecter").length() > 0 && !"".equals(obj.getString("collecter"))){
							reportHql += " and collecter like '%"+obj.getString("collecter")+"%' ";
						}
					}
					if (obj.containsKey("tijiao")) {
						if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"是".equals(obj.getString("tijiao"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 11";
							flag = false;
						}
					}else{
						flag = true;
					}
					if (obj.containsKey("guidang")) {
						if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"是".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 21";
							flag = false;
						}else if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"否".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 20";
							flag = false;
						}
					}else{
						flag = true;
					}
					if (obj.containsKey("luRuDate")) {
						String value = obj.getString("luRuDate");
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
								reportHql += " and luRuDate between '"
										+ d[0].toLocaleString()
										+ "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
					if (obj.containsKey("guidangDate")) {
						String value = obj.getString("guidangDate");
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
								reportHql += " and guiDangDate between '"
										+ d[0].toLocaleString()
										+ "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
				}
				if(flag){
					reportHql+=" and isPatientOrDoctorWriteZanCun in (11,20,21)";
				}
				reportHql+=" order by luRuDate desc";
				Query query = session
						.createQuery(reportHql);
				List<ReportFormforRemote> rflist = query.list();
				// 根据ReportForm表查出的每一条记录循环查询ReportItem表
				for (int i = 0; i < rflist.size(); i++) {
					ReportFormforRemote rf = rflist.get(i);
					List<ReportItemforRemote> rilist = session
							.createQuery(
									"from ReportItemforRemote where receiveDate = ? and sectionNo = ? and testTypeNo = ? and sampleNo = ?")
							.setDate(0, rf.getReceiveDate()).setInteger(1,
									rf.getSectionNo()).setInteger(2,
									rf.getTestTypeNo()).setString(3,
									rf.getSampleNo()).list();

					List temp = new ArrayList();
					// 把查出的每条记录中的ParItemNo字段放到List中，并进行下面的处理，使List中的ParItemNo字段不存在重复
					for (ReportItemforRemote ri : rilist) {
						String _dateStr = ri.getReceiveDate().toString()
								.substring(0, 10);
						String _str = ri.getParItemNo() + "," + ri.getIsAdd()
								+ "," + _dateStr + "," + ri.getSectionNo()
								+ "," + ri.getTestTypeNo() + ","
								+ ri.getSampleNo();
						temp.add(_str);
					}
					List pitemList = removeDuplicateObj(temp);
					// 根据ParItemNo字段值查询相关表获得其所对应的CName
					for (int j = 0; j < pitemList.size(); j++) {
						String[] _str = pitemList.get(j).toString().split(",");
						String strofItemNo = _str[0];
						int isAdd = Integer.parseInt(_str[1]);
						String receiveDate = _str[2];
						String sectionNo = _str[3];
						String testTypeNo = _str[4];
						String sampleNo = _str[5];
						String parItemNo = strofItemNo;
						ReportFormforPad tempReport = new ReportFormforPad();
						if (0 == isAdd) {
							List<TestItemforRemote> testItemList = session
									.createQuery(
											"from TestItemforRemote where itemNo = ?")
									.setInteger(0,
											Integer.parseInt(strofItemNo))
									.list();
							if (testItemList.isEmpty())
								continue;
							TestItemforRemote testItem = testItemList.get(0);
							tempReport.setText(testItem.getCname());
							tempReport.setReceiveDate(receiveDate);
							tempReport.setSectionNo(sectionNo);
							tempReport.setTestTypeNo(testTypeNo);
							tempReport.setSampleNo(sampleNo);
							tempReport.setParItemNo(parItemNo);
							tempReport.setHospital(rf.getHospitalName());
							tempReport.setReportFormforRemoteId(rf.getId());
							String pat = "from Patient where patientId ='"
									+ rf.getPatNo() + "'";
							Patient pati = (Patient) session.createQuery(pat)
									.list().get(0);
							tempReport.setPatientName(pati.getPatientName());
							tempReport.setPatientNo(pati.getPatientNo());
							tempReport.setPatientId(pati.getId().toString());
							tempReport.setInputName(rf.getDoctor());
							if(rf.getLuRuDate()!= null){
								tempReport.setLuRuDate(rf.getLuRuDate().toLocaleString());
							}else{
								tempReport.setLuRuDate("");
							}
							// liugang 2011-08-06 修改 start
							String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
									+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
									+ " where hsp.patientId = "
									+ pati.getId()
									+ " order by hlg.id desc";
							ps = conn.prepareStatement(curSql);
							rs = ps.executeQuery();
							Date firstDate = null;
							String diag1 = null;
							Date secondDate = null;
							String secondDiag = null;
							if (rs.next()) {
								firstDate = rs.getDate("date");
								diag1 = rs.getString("diag1").replaceAll(
										"<[.[^<]]*>", " ");// 十二分级中的第一级
								diag1 = diag1.replaceAll("[\\n|\\r]", " ");
							}
							String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
									+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
									+ pati.getId() + " order by hlg.id desc";
							ps = conn.prepareStatement(memSql);
							rs = ps.executeQuery();
							if (rs.next()) {
								secondDate = rs.getDate("date");
								secondDiag = rs.getString("diag1").replaceAll(
										"<[.[^<]]*>", " ");// 十二分级中的第一级
								secondDiag = secondDiag.replaceAll("[\\n|\\r]",
										" ");
							}
							if (firstDate != null && secondDate != null) {
								if (firstDate.getTime() > secondDate.getTime()) {
									tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
								} else {
									tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
								}
							} else if (firstDate != null) {
								tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
							} else if (secondDate != null) {
								tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
							} else {
								tempReport.setJiBingGrounp("");// 借用patient表中的字段，暂时显示当前疾病分
							}

							if (rf.getIsFromOutHospital() != null) {
								tempReport.setIsFromOutHispital(rf
										.getIsFromOutHospital().toString());
							} else {
								tempReport.setIsFromOutHispital("0");
							}
							if(rf.getGuiDangDate() != null){
								tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
							}else{
								tempReport.setGuiDangDate("");
							}
							if(rf.getSheHeDate() != null){
								tempReport.setSheHeDate(rf.getSheHeDate().toLocaleString());
							}else{
								tempReport.setSheHeDate("");
							}
							reportResult.add(tempReport);
						} else {
							List<TestItem> testItemList = session.createQuery(
									"from TestItem where itemNo = ?")
									.setInteger(0,
											Integer.parseInt(strofItemNo))
									.list();
							if (testItemList.isEmpty())
								continue;
							TestItem testItem = testItemList.get(0);
							tempReport.setText(testItem.getCname());
							tempReport.setReceiveDate(receiveDate);
							tempReport.setSectionNo(sectionNo);
							tempReport.setTestTypeNo(testTypeNo);
							tempReport.setSampleNo(sampleNo);
							tempReport.setParItemNo(parItemNo);
							tempReport.setHospital(rf.getHospitalName());
							tempReport.setReportFormforRemoteId(rf.getId());
							String pat = "from Patient where patientId ='"
									+ rf.getPatNo() + "'";
							Patient pati = (Patient) session.createQuery(pat)
									.list().get(0);
							tempReport.setPatientName(pati.getPatientName());
							tempReport.setPatientId(pati.getId().toString());
							tempReport.setPatientNo(pati.getPatientNo());
							tempReport.setInputName(rf.getDoctor());
							if(rf.getLuRuDate()!= null){
								tempReport.setLuRuDate(rf.getLuRuDate().toLocaleString());
							}else{
								tempReport.setLuRuDate("");
							}
							
							// liugang 2011-08-06 修改 start
							String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
									+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
									+ " where hsp.patientId = "
									+ pati.getId()
									+ " order by hlg.id desc";
							ps = conn.prepareStatement(curSql);
							rs = ps.executeQuery();
							Date firstDate = null;
							String diag1 = null;
							Date secondDate = null;
							String secondDiag = null;
							if (rs.next()) {
								firstDate = rs.getDate("date");
								diag1 = rs.getString("diag1").replaceAll(
										"<[.[^<]]*>", " ");// 十二分级中的第一级
								diag1 = diag1.replaceAll("[\\n|\\r]", " ");
							}
							String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
									+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
									+ pati.getId() + " order by hlg.id desc";
							ps = conn.prepareStatement(memSql);
							rs = ps.executeQuery();
							if (rs.next()) {
								secondDate = rs.getDate("date");
								secondDiag = rs.getString("diag1").replaceAll(
										"<[.[^<]]*>", " ");// 十二分级中的第一级
								secondDiag = secondDiag.replaceAll("[\\n|\\r]",
										" ");
							}
							if (firstDate != null && secondDate != null) {
								if (firstDate.getTime() > secondDate.getTime()) {
									tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
								} else {
									tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
								}
							} else if (firstDate != null) {
								tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
							} else if (secondDate != null) {
								tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
							} else {
								tempReport.setJiBingGrounp("");// 借用patient表中的字段，暂时显示当前疾病分
							}

							if (rf.getIsFromOutHospital() != null) {
								tempReport.setIsFromOutHispital(rf
										.getIsFromOutHospital().toString());
							} else {
								tempReport.setIsFromOutHispital("0");
							}
							if (rf.getIsPatientOrDoctorWriteZanCun() != null) {
								tempReport.setIsPatientOrDoctorWriteZanCun(rf
										.getIsPatientOrDoctorWriteZanCun()
										.toString());
							} else {
								tempReport.setIsPatientOrDoctorWriteZanCun("");
							}
							if(rf.getGuiDangDate() != null){
								tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
							}else{
								tempReport.setGuiDangDate("");
							}
							if(rf.getSheHeDate() != null){
								tempReport.setSheHeDate(rf.getSheHeDate().toLocaleString());
							}else{
								tempReport.setSheHeDate("");
							}
							reportResult.add(tempReport);
						}
					}
				}
				tran.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	public List<ReportFormforPad> getMyGrounpCheckReportsForByMember(
			String patientId,String search) {
		Session session = null;
		Transaction tran = null;
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
		List<ReportFormforPad> reportResult = new ArrayList<ReportFormforPad>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient pat = (Patient)session.get(Patient.class, Long.parseLong(patientId));
//			conn = DatabaseUtil.getConnection();
			String reportHql = "from ReportFormforRemote where patNo ='"
				+ pat.getPatientId()
				+ "' and"
				+ " isFromOutHospital =1 ";
			boolean flag = true;
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if(obj.containsKey("doctor")){
					if (obj.getString("doctor").length() > 0 && !"".equals(obj.getString("doctor"))){
						reportHql += " and doctor like '%"+obj.getString("doctor")+"%' ";
					}
				}
				if (obj.containsKey("tijiao")) {
					if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"是".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 11";
						flag = false;
					}else if(obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"否".equals(obj.getString("tijiao"))){
						reportHql += " and isPatientOrDoctorWriteZanCun = 10";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("guidang")) {
					if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"是".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 21";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("luRuDate")) {
					String value = obj.getString("luRuDate");
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
							reportHql += " and luRuDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
				if (obj.containsKey("guidangDate")) {
					String value = obj.getString("guidangDate");
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
							reportHql += " and guiDangDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
			}
			if(flag){
				reportHql+=" and isPatientOrDoctorWriteZanCun in (10,11,21)";
			}
			reportHql+=" order by luRuDate desc";
			Query query = session
					.createQuery(reportHql);
			List<ReportFormforRemote> rflist = query.list();
			// 根据ReportForm表查出的每一条记录循环查询ReportItem表
			for (int i = 0; i < rflist.size(); i++) {
				ReportFormforRemote rf = rflist.get(i);
				List<ReportItemforRemote> rilist = session
						.createQuery(
								"from ReportItemforRemote where receiveDate = ? and sectionNo = ? and testTypeNo = ? and sampleNo = ?")
						.setDate(0, rf.getReceiveDate()).setInteger(1,
								rf.getSectionNo()).setInteger(2,
								rf.getTestTypeNo()).setString(3,
								rf.getSampleNo()).list();

				List temp = new ArrayList();
				// 把查出的每条记录中的ParItemNo字段放到List中，并进行下面的处理，使List中的ParItemNo字段不存在重复
				for (ReportItemforRemote ri : rilist) {
					String _dateStr = ri.getReceiveDate().toString().substring(
							0, 10);
					String _str = ri.getParItemNo() + "," + ri.getIsAdd() + ","
							+ _dateStr + "," + ri.getSectionNo() + ","
							+ ri.getTestTypeNo() + "," + ri.getSampleNo();
					temp.add(_str);
				}
				List pitemList = removeDuplicateObj(temp);
				// 根据ParItemNo字段值查询相关表获得其所对应的CName
				for (int j = 0; j < pitemList.size(); j++) {
					String[] _str = pitemList.get(j).toString().split(",");
					String strofItemNo = _str[0];
					int isAdd = Integer.parseInt(_str[1]);
					String receiveDate = _str[2];
					String sectionNo = _str[3];
					String testTypeNo = _str[4];
					String sampleNo = _str[5];
					String parItemNo = strofItemNo;
					ReportFormforPad tempReport = new ReportFormforPad();
					if (0 == isAdd) {
						List<TestItemforRemote> testItemList = session
								.createQuery(
										"from TestItemforRemote where itemNo = ?")
								.setInteger(0, Integer.parseInt(strofItemNo))
								.list();
						if (testItemList.isEmpty())
							continue;
						TestItemforRemote testItem = testItemList.get(0);
						tempReport.setText(testItem.getCname());
						tempReport.setReceiveDate(receiveDate);
						tempReport.setSectionNo(sectionNo);
						tempReport.setTestTypeNo(testTypeNo);
						tempReport.setSampleNo(sampleNo);
						tempReport.setParItemNo(parItemNo);
						tempReport.setHospital(rf.getHospitalName());
						tempReport.setReportFormforRemoteId(rf.getId());
						if (rf.getIsPatientOrDoctorWriteZanCun() != null) {
							tempReport.setIsPatientOrDoctorWriteZanCun(rf
									.getIsPatientOrDoctorWriteZanCun()
									.toString());
						} else {
							tempReport.setIsPatientOrDoctorWriteZanCun("");
						}
						tempReport.setPatientName(pat.getPatientName());
						tempReport.setPatientNo(pat.getPatientNo());
						tempReport.setInputName(rf.getDoctor());
						tempReport.setLuRuDate(rf.getLuRuDate().toLocaleString());
						tempReport.setCheXiaoTrue(rf.getCheXiaoTrue());
						if (rf.getIsFromOutHospital() != null) {
							tempReport.setIsFromOutHispital(rf
									.getIsFromOutHospital().toString());
						} else {
							tempReport.setIsFromOutHispital("0");
						}
						if(rf.getGuiDangDate() != null){
							tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
						}else{
							tempReport.setGuiDangDate("");
						}
						if(rf.getSheHeDate() != null){
							tempReport.setSheHeDate(rf.getSheHeDate().toLocaleString());
						}else{
							tempReport.setSheHeDate("");
						}
						reportResult.add(tempReport);
					} else {
						List<TestItem> testItemList = session.createQuery(
								"from TestItem where itemNo = ?").setInteger(0,
								Integer.parseInt(strofItemNo)).list();
						if (testItemList.isEmpty())
							continue;
						TestItem testItem = testItemList.get(0);
						tempReport.setText(testItem.getCname());
						tempReport.setReceiveDate(receiveDate);
						tempReport.setSectionNo(sectionNo);
						tempReport.setTestTypeNo(testTypeNo);
						tempReport.setSampleNo(sampleNo);
						tempReport.setParItemNo(parItemNo);
						tempReport.setHospital(rf.getHospitalName());
						tempReport.setReportFormforRemoteId(rf.getId());
						tempReport.setPatientName(pat.getPatientName());
						tempReport.setPatientNo(pat.getPatientNo());
						tempReport.setInputName(rf.getDoctor());
						tempReport.setLuRuDate(rf.getLuRuDate().toLocaleString());
						tempReport.setCheXiaoTrue(rf.getCheXiaoTrue());
						if (rf.getIsFromOutHospital() != null) {
							tempReport.setIsFromOutHispital(rf
									.getIsFromOutHospital().toString());
						} else {
							tempReport.setIsFromOutHispital("0");
						}
						if (rf.getIsPatientOrDoctorWriteZanCun() != null) {
							tempReport.setIsPatientOrDoctorWriteZanCun(rf
									.getIsPatientOrDoctorWriteZanCun()
									.toString());
						} else {
							tempReport.setIsPatientOrDoctorWriteZanCun("");
						}
						if(rf.getGuiDangDate() != null){
							tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
						}else{
							tempReport.setGuiDangDate("");
						}
						if(rf.getSheHeDate() != null){
							tempReport.setSheHeDate(rf.getSheHeDate().toLocaleString());
						}else{
							tempReport.setSheHeDate("");
						}
						reportResult.add(tempReport);
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
//			try {
//				DatabaseUtil.closeResource(conn, ps, rs);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return reportResult;
	}

	public List<ReportFormforPad> getMyGrounpCheckReportsForByMemberDoctor(
			String patientId, String search) {
		Session session = null;
		Transaction tran = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReportFormforPad> reportResult = new ArrayList<ReportFormforPad>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient pat = (Patient)session.get(Patient.class, Long.parseLong(patientId));
			conn = DatabaseUtil.getConnection();
			String reportHql = "from ReportFormforRemote where patNo ='"
				+ pat.getPatientId()
				+ "' and"
				+ " isFromOutHospital =1 ";
			boolean flag = true;
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if(obj.containsKey("doctor")){
					if (obj.getString("doctor").length() > 0 && !"".equals(obj.getString("doctor"))){
						reportHql += " and doctor like '%"+obj.getString("doctor")+"%' ";
					}
				}
				if (obj.containsKey("tijiao")) {
					if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"是".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 11";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("guidang")) {
					if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"是".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 21";
						flag = false;
					}else if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"否".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 20";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("luRuDate")) {
					String value = obj.getString("luRuDate");
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
							reportHql += " and luRuDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
				if (obj.containsKey("guidangDate")) {
					String value = obj.getString("guidangDate");
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
							reportHql += " and guiDangDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
			}
			if(flag){
				reportHql+=" and isPatientOrDoctorWriteZanCun in (11,20,21)";
			}
			reportHql+=" order by luRuDate desc";
			Query query = session
					.createQuery(reportHql);
			List<ReportFormforRemote> rflist = query.list();
			// 根据ReportForm表查出的每一条记录循环查询ReportItem表
			for (int i = 0; i < rflist.size(); i++) {
				ReportFormforRemote rf = rflist.get(i);
				List<ReportItemforRemote> rilist = session
						.createQuery(
								"from ReportItemforRemote where receiveDate = ? and sectionNo = ? and testTypeNo = ? and sampleNo = ?")
						.setDate(0, rf.getReceiveDate()).setInteger(1,
								rf.getSectionNo()).setInteger(2,
								rf.getTestTypeNo()).setString(3,
								rf.getSampleNo()).list();

				List temp = new ArrayList();
				// 把查出的每条记录中的ParItemNo字段放到List中，并进行下面的处理，使List中的ParItemNo字段不存在重复
				for (ReportItemforRemote ri : rilist) {
					String _dateStr = ri.getReceiveDate().toString().substring(
							0, 10);
					String _str = ri.getParItemNo() + "," + ri.getIsAdd() + ","
							+ _dateStr + "," + ri.getSectionNo() + ","
							+ ri.getTestTypeNo() + "," + ri.getSampleNo();
					temp.add(_str);
				}
				List pitemList = removeDuplicateObj(temp);
				// 根据ParItemNo字段值查询相关表获得其所对应的CName
				for (int j = 0; j < pitemList.size(); j++) {
					String[] _str = pitemList.get(j).toString().split(",");
					String strofItemNo = _str[0];
					int isAdd = Integer.parseInt(_str[1]);
					String receiveDate = _str[2];
					String sectionNo = _str[3];
					String testTypeNo = _str[4];
					String sampleNo = _str[5];
					String parItemNo = strofItemNo;
					ReportFormforPad tempReport = new ReportFormforPad();
					if (0 == isAdd) {
						List<TestItemforRemote> testItemList = session
								.createQuery(
										"from TestItemforRemote where itemNo = ?")
								.setInteger(0, Integer.parseInt(strofItemNo))
								.list();
						if (testItemList.isEmpty())
							continue;
						TestItemforRemote testItem = testItemList.get(0);
						tempReport.setText(testItem.getCname());
						tempReport.setReceiveDate(receiveDate);
						tempReport.setSectionNo(sectionNo);
						tempReport.setTestTypeNo(testTypeNo);
						tempReport.setSampleNo(sampleNo);
						tempReport.setParItemNo(parItemNo);
						tempReport.setHospital(rf.getHospitalName());
						tempReport.setReportFormforRemoteId(rf.getId());
						if (rf.getIsPatientOrDoctorWriteZanCun() != null) {
							tempReport.setIsPatientOrDoctorWriteZanCun(rf
									.getIsPatientOrDoctorWriteZanCun()
									.toString());
						} else {
							tempReport.setIsPatientOrDoctorWriteZanCun("");
						}
						tempReport.setPatientName(pat.getPatientName());
						tempReport.setPatientNo(pat.getPatientNo());
						tempReport.setInputName(rf.getDoctor());
						tempReport.setLuRuDate(rf.getLuRuDate().toLocaleString());
						// liugang 2011-08-06 修改 start
						String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
								+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
								+ " where hsp.patientId = "
								+ pat.getId()
								+ " order by hlg.id desc";
						ps = conn.prepareStatement(curSql);
						rs = ps.executeQuery();
						Date firstDate = null;
						String diag1 = null;
						Date secondDate = null;
						String secondDiag = null;
						if (rs.next()) {
							firstDate = rs.getDate("date");
							diag1 = rs.getString("diag1").replaceAll(
									"<[.[^<]]*>", " ");// 十二分级中的第一级
							diag1 = diag1.replaceAll("[\\n|\\r]", " ");
						}
						String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
								+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
								+ pat.getId() + " order by hlg.id desc";
						ps = conn.prepareStatement(memSql);
						rs = ps.executeQuery();
						if (rs.next()) {
							secondDate = rs.getDate("date");
							secondDiag = rs.getString("diag1").replaceAll(
									"<[.[^<]]*>", " ");// 十二分级中的第一级
							secondDiag = secondDiag
									.replaceAll("[\\n|\\r]", " ");
						}
						if (firstDate != null && secondDate != null) {
							if (firstDate.getTime() > secondDate.getTime()) {
								tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
							} else {
								tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
							}
						} else if (firstDate != null) {
							tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
						} else if (secondDate != null) {
							tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
						} else {
							tempReport.setJiBingGrounp("");// 借用patient表中的字段，暂时显示当前疾病分
						}

						if (rf.getIsFromOutHospital() != null) {
							tempReport.setIsFromOutHispital(rf
									.getIsFromOutHospital().toString());
						} else {
							tempReport.setIsFromOutHispital("0");
						}
						if(rf.getGuiDangDate() != null){
							tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
						}else{
							tempReport.setGuiDangDate("");
						}
						if(rf.getSheHeDate() != null){
							tempReport.setSheHeDate(rf.getSheHeDate().toLocaleString());
						}else{
							tempReport.setSheHeDate("");
						}
						reportResult.add(tempReport);
					} else {
						List<TestItem> testItemList = session.createQuery(
								"from TestItem where itemNo = ?").setInteger(0,
								Integer.parseInt(strofItemNo)).list();
						if (testItemList.isEmpty())
							continue;
						TestItem testItem = testItemList.get(0);
						tempReport.setText(testItem.getCname());
						tempReport.setReceiveDate(receiveDate);
						tempReport.setSectionNo(sectionNo);
						tempReport.setTestTypeNo(testTypeNo);
						tempReport.setSampleNo(sampleNo);
						tempReport.setParItemNo(parItemNo);
						tempReport.setHospital(rf.getHospitalName());
						tempReport.setReportFormforRemoteId(rf.getId());
						tempReport.setPatientName(pat.getPatientName());
						tempReport.setPatientNo(pat.getPatientNo());
						tempReport.setInputName(rf.getDoctor());
						tempReport.setLuRuDate(rf.getLuRuDate().toLocaleString());
//						// liugang 2011-08-06 修改 start
						String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
								+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
								+ " where hsp.patientId = "
								+ pat.getId()
								+ " order by hlg.id desc";
						ps = conn.prepareStatement(curSql);
						rs = ps.executeQuery();
						Date firstDate = null;
						String diag1 = null;
						Date secondDate = null;
						String secondDiag = null;
						if (rs.next()) {
							firstDate = rs.getDate("date");
							diag1 = rs.getString("diag1").replaceAll(
									"<[.[^<]]*>", " ");// 十二分级中的第一级
							diag1 = diag1.replaceAll("[\\n|\\r]", " ");
						}
						String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
								+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
								+ pat.getId() + " order by hlg.id desc";
						ps = conn.prepareStatement(memSql);
						rs = ps.executeQuery();
						if (rs.next()) {
							secondDate = rs.getDate("date");
							secondDiag = rs.getString("diag1").replaceAll(
									"<[.[^<]]*>", " ");// 十二分级中的第一级
							secondDiag = secondDiag
									.replaceAll("[\\n|\\r]", " ");
						}
						if (firstDate != null && secondDate != null) {
							if (firstDate.getTime() > secondDate.getTime()) {
								tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
							} else {
								tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
							}
						} else if (firstDate != null) {
							tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
						} else if (secondDate != null) {
							tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
						} else {
							tempReport.setJiBingGrounp("");// 借用patient表中的字段，暂时显示当前疾病分
						}

						if (rf.getIsFromOutHospital() != null) {
							tempReport.setIsFromOutHispital(rf
									.getIsFromOutHospital().toString());
						} else {
							tempReport.setIsFromOutHispital("0");
						}
						if (rf.getIsPatientOrDoctorWriteZanCun() != null) {
							tempReport.setIsPatientOrDoctorWriteZanCun(rf
									.getIsPatientOrDoctorWriteZanCun()
									.toString());
						} else {
							tempReport.setIsPatientOrDoctorWriteZanCun("");
						}
						if(rf.getGuiDangDate() != null){
							tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
						}else{
							tempReport.setGuiDangDate("");
						}
						if(rf.getSheHeDate() != null){
							tempReport.setSheHeDate(rf.getSheHeDate().toLocaleString());
						}else{
							tempReport.setSheHeDate("");
						}
						reportResult.add(tempReport);
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	public List<ReportFormforPad> getMyGrounpCheckReportsForByMemberGuanLiYuan(
			String patientId, String search) {
		Session session = null;
		Transaction tran = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReportFormforPad> reportResult = new ArrayList<ReportFormforPad>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient pat = (Patient)session.get(Patient.class, Long.parseLong(patientId));
			conn = DatabaseUtil.getConnection();
			String reportHql = "from ReportFormforRemote where patNo ='"
				+ pat.getPatientId()
				+ "' and"
				+ " isFromOutHospital =1 ";
			boolean flag = true;
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if(obj.containsKey("doctor")){
					if (obj.getString("doctor").length() > 0 && !"".equals(obj.getString("doctor"))){
						reportHql += " and doctor like '%"+obj.getString("doctor")+"%' ";
					}
				}
				if (obj.containsKey("tijiao")) {
					if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"是".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 11";
						flag = false;
					}else if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"否".equals(obj.getString("tijiao"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 10";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("guidang")) {
					if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"是".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 21";
						flag = false;
					}else if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"否".equals(obj.getString("guidang"))) {
						reportHql += " and isPatientOrDoctorWriteZanCun = 20";
						flag = false;
					}
				}else{
					flag = true;
				}
				if (obj.containsKey("luRuDate")) {
					String value = obj.getString("luRuDate");
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
							reportHql += " and luRuDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
				if (obj.containsKey("guidangDate")) {
					String value = obj.getString("guidangDate");
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
							reportHql += " and guiDangDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
			}
			if(flag){
				reportHql+=" and isPatientOrDoctorWriteZanCun in (10,11,20,21)";
			}
			reportHql+=" order by luRuDate desc";
			Query query = session
					.createQuery(reportHql);
			List<ReportFormforRemote> rflist = query.list();
			// 根据ReportForm表查出的每一条记录循环查询ReportItem表
			for (int i = 0; i < rflist.size(); i++) {
				ReportFormforRemote rf = rflist.get(i);
				List<ReportItemforRemote> rilist = session
						.createQuery(
								"from ReportItemforRemote where receiveDate = ? and sectionNo = ? and testTypeNo = ? and sampleNo = ?")
						.setDate(0, rf.getReceiveDate()).setInteger(1,
								rf.getSectionNo()).setInteger(2,
								rf.getTestTypeNo()).setString(3,
								rf.getSampleNo()).list();

				List temp = new ArrayList();
				// 把查出的每条记录中的ParItemNo字段放到List中，并进行下面的处理，使List中的ParItemNo字段不存在重复
				for (ReportItemforRemote ri : rilist) {
					String _dateStr = ri.getReceiveDate().toString().substring(
							0, 10);
					String _str = ri.getParItemNo() + "," + ri.getIsAdd() + ","
							+ _dateStr + "," + ri.getSectionNo() + ","
							+ ri.getTestTypeNo() + "," + ri.getSampleNo();
					temp.add(_str);
				}
				List pitemList = removeDuplicateObj(temp);
				// 根据ParItemNo字段值查询相关表获得其所对应的CName
				for (int j = 0; j < pitemList.size(); j++) {
					String[] _str = pitemList.get(j).toString().split(",");
					String strofItemNo = _str[0];
					int isAdd = Integer.parseInt(_str[1]);
					String receiveDate = _str[2];
					String sectionNo = _str[3];
					String testTypeNo = _str[4];
					String sampleNo = _str[5];
					String parItemNo = strofItemNo;
					ReportFormforPad tempReport = new ReportFormforPad();
					if (0 == isAdd) {
						List<TestItemforRemote> testItemList = session
								.createQuery(
										"from TestItemforRemote where itemNo = ?")
								.setInteger(0, Integer.parseInt(strofItemNo))
								.list();
						if (testItemList.isEmpty())
							continue;
						TestItemforRemote testItem = testItemList.get(0);
						tempReport.setText(testItem.getCname());
						tempReport.setReceiveDate(receiveDate);
						tempReport.setSectionNo(sectionNo);
						tempReport.setTestTypeNo(testTypeNo);
						tempReport.setSampleNo(sampleNo);
						tempReport.setParItemNo(parItemNo);
						tempReport.setHospital(rf.getHospitalName());
						tempReport.setReportFormforRemoteId(rf.getId());
						if (rf.getIsPatientOrDoctorWriteZanCun() != null) {
							tempReport.setIsPatientOrDoctorWriteZanCun(rf
									.getIsPatientOrDoctorWriteZanCun()
									.toString());
						} else {
							tempReport.setIsPatientOrDoctorWriteZanCun("");
						}
						tempReport.setPatientName(pat.getPatientName());
						tempReport.setPatientNo(pat.getPatientNo());
						tempReport.setInputName(rf.getDoctor());
						tempReport.setLuRuDate(rf.getLuRuDate().toLocaleString());
						// liugang 2011-08-06 修改 start
						String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
								+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
								+ " where hsp.patientId = "
								+ pat.getId()
								+ " order by hlg.id desc";
						ps = conn.prepareStatement(curSql);
						rs = ps.executeQuery();
						Date firstDate = null;
						String diag1 = null;
						Date secondDate = null;
						String secondDiag = null;
						if (rs.next()) {
							firstDate = rs.getDate("date");
							diag1 = rs.getString("diag1").replaceAll(
									"<[.[^<]]*>", " ");// 十二分级中的第一级
							diag1 = diag1.replaceAll("[\\n|\\r]", " ");
						}
						String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
								+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
								+ pat.getId() + " order by hlg.id desc";
						ps = conn.prepareStatement(memSql);
						rs = ps.executeQuery();
						if (rs.next()) {
							secondDate = rs.getDate("date");
							secondDiag = rs.getString("diag1").replaceAll(
									"<[.[^<]]*>", " ");// 十二分级中的第一级
							secondDiag = secondDiag
									.replaceAll("[\\n|\\r]", " ");
						}
						if (firstDate != null && secondDate != null) {
							if (firstDate.getTime() > secondDate.getTime()) {
								tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
							} else {
								tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
							}
						} else if (firstDate != null) {
							tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
						} else if (secondDate != null) {
							tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
						} else {
							tempReport.setJiBingGrounp("");// 借用patient表中的字段，暂时显示当前疾病分
						}

						if (rf.getIsFromOutHospital() != null) {
							tempReport.setIsFromOutHispital(rf
									.getIsFromOutHospital().toString());
						} else {
							tempReport.setIsFromOutHispital("0");
						}
						if(rf.getGuiDangDate() != null){
							tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
						}else{
							tempReport.setGuiDangDate("");
						}
						if(rf.getSheHeDate() != null){
							tempReport.setSheHeDate(rf.getSheHeDate().toLocaleString());
						}else{
							tempReport.setSheHeDate("");
						}
						reportResult.add(tempReport);
					} else {
						List<TestItem> testItemList = session.createQuery(
								"from TestItem where itemNo = ?").setInteger(0,
								Integer.parseInt(strofItemNo)).list();
						if (testItemList.isEmpty())
							continue;
						TestItem testItem = testItemList.get(0);
						tempReport.setText(testItem.getCname());
						tempReport.setReceiveDate(receiveDate);
						tempReport.setSectionNo(sectionNo);
						tempReport.setTestTypeNo(testTypeNo);
						tempReport.setSampleNo(sampleNo);
						tempReport.setParItemNo(parItemNo);
						tempReport.setHospital(rf.getHospitalName());
						tempReport.setReportFormforRemoteId(rf.getId());
						tempReport.setPatientName(pat.getPatientName());
						tempReport.setPatientNo(pat.getPatientNo());
						tempReport.setInputName(rf.getDoctor());
						tempReport.setLuRuDate(rf.getLuRuDate().toLocaleString());
//						// liugang 2011-08-06 修改 start
						String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
								+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
								+ " where hsp.patientId = "
								+ pat.getId()
								+ " order by hlg.id desc";
						ps = conn.prepareStatement(curSql);
						rs = ps.executeQuery();
						Date firstDate = null;
						String diag1 = null;
						Date secondDate = null;
						String secondDiag = null;
						if (rs.next()) {
							firstDate = rs.getDate("date");
							diag1 = rs.getString("diag1").replaceAll(
									"<[.[^<]]*>", " ");// 十二分级中的第一级
							diag1 = diag1.replaceAll("[\\n|\\r]", " ");
						}
						String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
								+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
								+ pat.getId() + " order by hlg.id desc";
						ps = conn.prepareStatement(memSql);
						rs = ps.executeQuery();
						if (rs.next()) {
							secondDate = rs.getDate("date");
							secondDiag = rs.getString("diag1").replaceAll(
									"<[.[^<]]*>", " ");// 十二分级中的第一级
							secondDiag = secondDiag
									.replaceAll("[\\n|\\r]", " ");
						}
						if (firstDate != null && secondDate != null) {
							if (firstDate.getTime() > secondDate.getTime()) {
								tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
							} else {
								tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
							}
						} else if (firstDate != null) {
							tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
						} else if (secondDate != null) {
							tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
						} else {
							tempReport.setJiBingGrounp("");// 借用patient表中的字段，暂时显示当前疾病分
						}

						if (rf.getIsFromOutHospital() != null) {
							tempReport.setIsFromOutHispital(rf
									.getIsFromOutHospital().toString());
						} else {
							tempReport.setIsFromOutHispital("0");
						}
						if (rf.getIsPatientOrDoctorWriteZanCun() != null) {
							tempReport.setIsPatientOrDoctorWriteZanCun(rf
									.getIsPatientOrDoctorWriteZanCun()
									.toString());
						} else {
							tempReport.setIsPatientOrDoctorWriteZanCun("");
						}
						if(rf.getGuiDangDate() != null){
							tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
						}else{
							tempReport.setGuiDangDate("");
						}
						if(rf.getSheHeDate() != null){
							tempReport.setSheHeDate(rf.getSheHeDate().toLocaleString());
						}else{
							tempReport.setSheHeDate("");
						}
						reportResult.add(tempReport);
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	public List<ReportFormforPad> getMyGrounpCheckReportsForByMemberGuanLiYuanAll(
			String deptCode, String search) {
		Session session = null;
		Transaction tran = null;
		StringBuffer sb = new StringBuffer();
		String retStr = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReportFormforPad> reportResult = new ArrayList<ReportFormforPad>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();

			StringBuilder sbl = null;
			
				String memberInfoHql = "select pa.patientId from MemberInfo mem left join t_PlanCount pc on mem.patient= pc.patientId ";
				// liugang 2011-09-14 update 添加病人姓名查询
				memberInfoHql += " inner join t_patient pa on pa.id = mem.patient  where mem.deptCode='"
						+ deptCode + "'";
				List list = session.createSQLQuery(memberInfoHql).list();
				if (list != null && list.size() > 0) {
					sbl = new StringBuilder();
					for (int si = 0, size = list.size(); si < size; si++) {
						if(list.get(si) != null){
							String objS = list.get(si).toString();
							if (si < size - 1) {
								sbl.append("'" + objS + "',");
							} else {
								sbl.append("'" + objS + "'");
							}
						}
					}
				}
			
			if (sbl != null) {
				conn = DatabaseUtil.getConnection();
				String reportHql = "from ReportFormforRemote where patNo in ("
								+ sbl.toString()
								+ ") and"
								+ " isFromOutHospital =1 ";
				boolean flag = true;
				if (search != null && search.length() > 0) {
					JSONObject obj = JSONObject.fromObject(search);
					if(obj.containsKey("doctor")){
						if (obj.getString("doctor").length() > 0 && !"".equals(obj.getString("doctor"))){
							reportHql += " and doctor like '%"+obj.getString("doctor")+"%' ";
						}
					}
					if(obj.containsKey("collecter")){
						if (obj.getString("collecter").length() > 0 && !"".equals(obj.getString("collecter"))){
							reportHql += " and collecter like '%"+obj.getString("collecter")+"%' ";
						}
					}
					if (obj.containsKey("tijiao")) {
						if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"是".equals(obj.getString("tijiao"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 11";
							flag = false;
						}else if (obj.getString("tijiao").length() > 0 && !"--请选择--".equals(obj.getString("tijiao"))&&"否".equals(obj.getString("tijiao"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 10";
							flag = false;
						}
					}else{
						flag = true;
					}
					if (obj.containsKey("guidang")) {
						if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"是".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 21";
							flag = false;
						}else if (obj.getString("guidang").length() > 0 && !"--请选择--".equals(obj.getString("guidang"))&&"否".equals(obj.getString("guidang"))) {
							reportHql += " and isPatientOrDoctorWriteZanCun = 20";
							flag = false;
						}
					}else{
						flag = true;
					}
					if (obj.containsKey("luRuDate")) {
						String value = obj.getString("luRuDate");
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
								reportHql += " and luRuDate between '"
										+ d[0].toLocaleString()
										+ "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
					if (obj.containsKey("guidangDate")) {
						String value = obj.getString("guidangDate");
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
								reportHql += " and guiDangDate between '"
										+ d[0].toLocaleString()
										+ "' and '"
										+ d[1].toLocaleString() + "'";
							}
						}
					}
				}
				if(flag){
					reportHql+=" and isPatientOrDoctorWriteZanCun in (10,11,20,21)";
				}
				reportHql+=" order by luRuDate desc";
				Query query = session
						.createQuery(reportHql);
				List<ReportFormforRemote> rflist = query.list();
				// 根据ReportForm表查出的每一条记录循环查询ReportItem表
				for (int i = 0; i < rflist.size(); i++) {
					ReportFormforRemote rf = rflist.get(i);
					List<ReportItemforRemote> rilist = session
							.createQuery(
									"from ReportItemforRemote where receiveDate = ? and sectionNo = ? and testTypeNo = ? and sampleNo = ?")
							.setDate(0, rf.getReceiveDate()).setInteger(1,
									rf.getSectionNo()).setInteger(2,
									rf.getTestTypeNo()).setString(3,
									rf.getSampleNo()).list();

					List temp = new ArrayList();
					// 把查出的每条记录中的ParItemNo字段放到List中，并进行下面的处理，使List中的ParItemNo字段不存在重复
					for (ReportItemforRemote ri : rilist) {
						String _dateStr = ri.getReceiveDate().toString()
								.substring(0, 10);
						String _str = ri.getParItemNo() + "," + ri.getIsAdd()
								+ "," + _dateStr + "," + ri.getSectionNo()
								+ "," + ri.getTestTypeNo() + ","
								+ ri.getSampleNo();
						temp.add(_str);
					}
					List pitemList = removeDuplicateObj(temp);
					// 根据ParItemNo字段值查询相关表获得其所对应的CName
					for (int j = 0; j < pitemList.size(); j++) {
						String[] _str = pitemList.get(j).toString().split(",");
						String strofItemNo = _str[0];
						int isAdd = Integer.parseInt(_str[1]);
						String receiveDate = _str[2];
						String sectionNo = _str[3];
						String testTypeNo = _str[4];
						String sampleNo = _str[5];
						String parItemNo = strofItemNo;
						ReportFormforPad tempReport = new ReportFormforPad();
						if (0 == isAdd) {
							List<TestItemforRemote> testItemList = session
									.createQuery(
											"from TestItemforRemote where itemNo = ?")
									.setInteger(0,
											Integer.parseInt(strofItemNo))
									.list();
							if (testItemList.isEmpty())
								continue;
							TestItemforRemote testItem = testItemList.get(0);
							tempReport.setText(testItem.getCname());
							tempReport.setReceiveDate(receiveDate);
							tempReport.setSectionNo(sectionNo);
							tempReport.setTestTypeNo(testTypeNo);
							tempReport.setSampleNo(sampleNo);
							tempReport.setParItemNo(parItemNo);
							tempReport.setHospital(rf.getHospitalName());
							tempReport.setReportFormforRemoteId(rf.getId());
							String pat = "from Patient where patientId ='"
									+ rf.getPatNo() + "'";
							Patient pati = (Patient) session.createQuery(pat)
									.list().get(0);
							tempReport.setPatientName(pati.getPatientName());
							tempReport.setPatientNo(pati.getPatientNo());
							tempReport.setPatientId(pati.getId().toString());
							tempReport.setInputName(rf.getDoctor());
							tempReport.setLuRuDate(rf.getLuRuDate().toLocaleString());
							tempReport.setCheXiaoFlag(rf.getCheXiaoFlag());
							tempReport.setGuiDangAuthor(rf.getCollecter());
							// liugang 2011-08-06 修改 start
							String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
									+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
									+ " where hsp.patientId = "
									+ pati.getId()
									+ " order by hlg.id desc";
							ps = conn.prepareStatement(curSql);
							rs = ps.executeQuery();
							Date firstDate = null;
							String diag1 = null;
							Date secondDate = null;
							String secondDiag = null;
							if (rs.next()) {
								firstDate = rs.getDate("date");
								diag1 = rs.getString("diag1").replaceAll(
										"<[.[^<]]*>", " ");// 十二分级中的第一级
								diag1 = diag1.replaceAll("[\\n|\\r]", " ");
							}
							String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
									+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
									+ pati.getId() + " order by hlg.id desc";
							ps = conn.prepareStatement(memSql);
							rs = ps.executeQuery();
							if (rs.next()) {
								secondDate = rs.getDate("date");
								secondDiag = rs.getString("diag1").replaceAll(
										"<[.[^<]]*>", " ");// 十二分级中的第一级
								secondDiag = secondDiag.replaceAll("[\\n|\\r]",
										" ");
							}
							if (firstDate != null && secondDate != null) {
								if (firstDate.getTime() > secondDate.getTime()) {
									tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
								} else {
									tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
								}
							} else if (firstDate != null) {
								tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
							} else if (secondDate != null) {
								tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
							} else {
								tempReport.setJiBingGrounp("");// 借用patient表中的字段，暂时显示当前疾病分
							}

							if (rf.getIsFromOutHospital() != null) {
								tempReport.setIsFromOutHispital(rf
										.getIsFromOutHospital().toString());
							} else {
								tempReport.setIsFromOutHispital("0");
							}
							if(rf.getGuiDangDate() != null){
								tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
							}else{
								tempReport.setGuiDangDate("");
							}
							if(rf.getSheHeDate() != null){
								tempReport.setSheHeDate(rf.getSheHeDate().toLocaleString());
							}else{
								tempReport.setSheHeDate("");
							}
							reportResult.add(tempReport);
						} else {
							List<TestItem> testItemList = session.createQuery(
									"from TestItem where itemNo = ?")
									.setInteger(0,
											Integer.parseInt(strofItemNo))
									.list();
							if (testItemList.isEmpty())
								continue;
							TestItem testItem = testItemList.get(0);
							tempReport.setText(testItem.getCname());
							tempReport.setReceiveDate(receiveDate);
							tempReport.setSectionNo(sectionNo);
							tempReport.setTestTypeNo(testTypeNo);
							tempReport.setSampleNo(sampleNo);
							tempReport.setParItemNo(parItemNo);
							tempReport.setHospital(rf.getHospitalName());
							tempReport.setReportFormforRemoteId(rf.getId());
							String pat = "from Patient where patientId ='"
									+ rf.getPatNo() + "'";
							Patient pati = (Patient) session.createQuery(pat)
									.list().get(0);
							tempReport.setPatientName(pati.getPatientName());
							tempReport.setPatientId(pati.getId().toString());
							tempReport.setPatientNo(pati.getPatientNo());
							tempReport.setInputName(rf.getDoctor());
							tempReport.setLuRuDate(rf.getLuRuDate().toLocaleString());
							tempReport.setCheXiaoFlag(rf.getCheXiaoFlag());
							tempReport.setGuiDangAuthor(rf.getCollecter());
							// liugang 2011-08-06 修改 start
							String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
									+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
									+ " where hsp.patientId = "
									+ pati.getId()
									+ " order by hlg.id desc";
							ps = conn.prepareStatement(curSql);
							rs = ps.executeQuery();
							Date firstDate = null;
							String diag1 = null;
							Date secondDate = null;
							String secondDiag = null;
							if (rs.next()) {
								firstDate = rs.getDate("date");
								diag1 = rs.getString("diag1").replaceAll(
										"<[.[^<]]*>", " ");// 十二分级中的第一级
								diag1 = diag1.replaceAll("[\\n|\\r]", " ");
							}
							String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
									+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
									+ pati.getId() + " order by hlg.id desc";
							ps = conn.prepareStatement(memSql);
							rs = ps.executeQuery();
							if (rs.next()) {
								secondDate = rs.getDate("date");
								secondDiag = rs.getString("diag1").replaceAll(
										"<[.[^<]]*>", " ");// 十二分级中的第一级
								secondDiag = secondDiag.replaceAll("[\\n|\\r]",
										" ");
							}
							if (firstDate != null && secondDate != null) {
								if (firstDate.getTime() > secondDate.getTime()) {
									tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
								} else {
									tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
								}
							} else if (firstDate != null) {
								tempReport.setJiBingGrounp(diag1);// 借用patient表中的字段，暂时显示当前疾病分
							} else if (secondDate != null) {
								tempReport.setJiBingGrounp(secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
							} else {
								tempReport.setJiBingGrounp("");// 借用patient表中的字段，暂时显示当前疾病分
							}

							if (rf.getIsFromOutHospital() != null) {
								tempReport.setIsFromOutHispital(rf
										.getIsFromOutHospital().toString());
							} else {
								tempReport.setIsFromOutHispital("0");
							}
							if (rf.getIsPatientOrDoctorWriteZanCun() != null) {
								tempReport.setIsPatientOrDoctorWriteZanCun(rf
										.getIsPatientOrDoctorWriteZanCun()
										.toString());
							} else {
								tempReport.setIsPatientOrDoctorWriteZanCun("");
							}
							if(rf.getGuiDangDate() != null){
								tempReport.setGuiDangDate(rf.getGuiDangDate().toLocaleString());
							}else{
								tempReport.setGuiDangDate("");
							}
							if(rf.getSheHeDate() != null){
								tempReport.setSheHeDate(rf.getSheHeDate().toLocaleString());
							}else{
								tempReport.setSheHeDate("");
							}
							reportResult.add(tempReport);
						}
					}
				}
				tran.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reportResult;
	}

	public void deleteReportFormAndReportItems(int id, String receiveDate,
			int sectionNo, int testTypeNo, String sampleNo) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String delReportForm ="delete from ReportFormforRemote where id ="+id+"" +
					" and receiveDate='"+receiveDate+"' and sectionNo="+sectionNo+" " +
							"and testTypeNo="+testTypeNo+" and sampleNo='"+sampleNo+"'";
			String delReportItems ="delete from ReportItemforRemote where " +
					"receiveDate='"+receiveDate+"' and sectionNo="+sectionNo+" " +
							"and testTypeNo="+testTypeNo+" and sampleNo='"+sampleNo+"'";
			session.createSQLQuery(delReportItems).executeUpdate();
			session.createSQLQuery(delReportForm).executeUpdate();
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
