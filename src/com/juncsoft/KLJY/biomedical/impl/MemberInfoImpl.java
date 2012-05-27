package com.juncsoft.KLJY.biomedical.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.OutHspRec;
import com.juncsoft.KLJY.biomedical.dao.MemberInfoDao;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.biomedical.entity.OutpatientGenerator;
import com.juncsoft.KLJY.biomedical.entity.OutpatientRecord;
import com.juncsoft.KLJY.membergrounp.entity.DepartmentGrounp;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class MemberInfoImpl implements MemberInfoDao {

	public void delete(MemberInfo mem) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(mem);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public String generatorMemberNo(Session session) throws Exception {
		String no = "";
		try {
			Object[] data = (Object[]) session.createSQLQuery(
					"select cDate,cCount from MemberNoGenerator").addScalar(
					"cDate", Hibernate.DATE).addScalar("cCount",
					Hibernate.INTEGER).setMaxResults(1).uniqueResult();
			Date cDate = (Date) data[0];
			Integer cCount = (Integer) data[1];
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();
			if (format.format(today).equals(format.format(cDate))) {
				cCount++;
			} else {
				cDate = new Date();
				cCount = 1;
			}
			no = format.format(cDate).replaceAll("-", "");
			String[] cCountArr = cCount.toString().split("");
			int noLength = 5;
			String[] count = new String[noLength];
			for (int i = 0; i < noLength; i++) {
				if (i < noLength - cCountArr.length) {
					count[i] = "0";
				} else {
					count[i] = cCountArr[i - (noLength - cCountArr.length)];
				}
			}
			for (String s : count) {
				no += s;
			}
			session.createSQLQuery(
					"update MemberNoGenerator set cDate=?,cCount=?").setDate(0,
					cDate).setInteger(1, cCount).executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		return no;
	}

	@SuppressWarnings("unchecked")
	public List<MemberInfo> getAll(Integer start, Integer limit,
			Criterion... criterias) throws Exception {
		List<MemberInfo> list;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(MemberInfo.class);
			for (Criterion c : criterias) {
				criteria.add(c);
			}
			list = criteria.setFirstResult(start).setMaxResults(limit)
					.addOrder(Order.desc("inDate")).list();
			List<MemberInfo> remove = new ArrayList<MemberInfo>();
			for (MemberInfo mem : list) {
				try {
					String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
							+ mem.getDeptCode() + "'";
					List deptList = session.createSQLQuery(hqlCode).list();
					mem.setDeptName(deptList.get(0).toString());
					DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
							.get(DepartmentGrounp.class, mem.getGrounpId());
					mem.setGrounpName(departmentGrounp.getGrounpName());
					mem.getPatient().getPatientNo();
				} catch (Exception e) {
					remove.add(mem);
					session.createSQLQuery(
							"delete from MemberInfo where memberNo=?")
							.setString(0, mem.getMemberNo()).executeUpdate();
				}
				if (mem.getCm() != null) {
					try {
						mem.getCm().getAge();
					} catch (Exception e) {
						mem.setCm(null);
					}
				}
			}
			list.removeAll(remove);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public long getTotal(Criterion... criterions) throws Exception {
		long rst = 0;
		Session session = DatabaseUtil.getSession();
		Transaction tran = session.beginTransaction();
		try {
			Criteria criteria = session.createCriteria(MemberInfo.class);
			for (Criterion c : criterions) {
				criteria.add(c);
			}
			rst = (Long) criteria.setProjection(Projections.rowCount())
					.uniqueResult();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}

	public boolean isAccountExits(String account) throws Exception {
		boolean rst = false;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if ((Long) session.createCriteria(MemberInfo.class).add(
					Restrictions.eq("account", account)).setProjection(
					Projections.rowCount()).uniqueResult() > 0) {
				rst = true;
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}

	public String save(MemberInfo mem) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			mem.setMemberNo(this.generatorMemberNo(session));
			session.save(mem);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mem.getMemberNo();
	}

	public void insertMemberInfo() {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql = "select tp.* from luona luo inner"
					+ " join t_patient tp on tp.patientName= luo.name"
					+ " and tp.patientNo = luo.mumber and tp.patientId is not null";
			List list = session.createSQLQuery(sql).list();
			if (list != null && list.size() > 0) {
				for (int i = 0, size = list.size(); i < size; i++) {
					Object[] obj = (Object[]) list.get(i);
					Patient patient = (Patient) session.get(Patient.class, Long
							.parseLong(obj[0].toString()));
					String hql = "from MemberInfo where patient = "
							+ patient.getId();
					List listOne = session.createQuery(hql).list();
					if (listOne != null && listOne.size() > 0) {
						MemberInfo mm = (MemberInfo) listOne.get(0);
						mm.setBiaoben(1);
						session.update(mm);
					} else {
						MemberInfo mem = new MemberInfo();
						mem.setMemberNo(this.generatorMemberNo(session));
						mem.setAccount(patient.getPatientId());
						if (patient.getIdNo() != null) {
							mem.setPassword(patient.getIdNo());
						} else {
							mem.setPassword(patient.getPatientId());
						}
						mem.setBiaoben(1);
						mem.setPatient(patient);
						mem.setInDate(new Date());
						mem.setMemberStatus("正常");
						mem.setMemberType("普通会员");
						mem.setMemberType("生物医学中心");
						mem.setCurrentWard("生物医学中心");
						mem.setDeptCode("SWYX");
						mem.setGrounpId(45L);
						mem.setDeptName("会员门诊");
						mem.setGrounpName("张永宏组(第1组)");
						mem.setInWardCode("SWYX");
						session.save(mem);
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public static void main(String[] args) {
		MemberInfoImpl dao = new MemberInfoImpl();
		dao.insertMemberInfo();
	}

	public String update(MemberInfo mem) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.update(mem);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mem.getMemberNo();
	}

	public MemberInfo get(String memberNo) throws Exception {
		MemberInfo mem = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			mem = (MemberInfo) session.get(MemberInfo.class, memberNo);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mem;
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

	@SuppressWarnings("unchecked")
	public InHspCaseMaster saveMemberCase(InHspCaseMaster cm) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<InHspCaseMaster> cms = session.createCriteria(
					InHspCaseMaster.class).add(
					Restrictions.eq("patientId", cm.getPatientId())).add(
					Restrictions.eq("flag", 1)).list();
			if (cms.size() > 0) {
				cm = cms.get(0);
			} else
				cm.setId((Long) session.save(cm));
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return cm;
	}

	public MemberInfo getByPatientId(Long patientId) throws Exception {
		MemberInfo mem = null;
		Session session = null;
		Transaction tran = null;
		try {
			Patient pat = new Patient();
			pat.setId(patientId);
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			mem = (MemberInfo) session.createCriteria(MemberInfo.class).add(
					Restrictions.eq("patient", pat)).setMaxResults(1)
					.uniqueResult();
			if (mem != null && mem.getDeptCode() != null) {
				String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+ mem.getDeptCode() + "'";
				List deptList = session.createSQLQuery(hqlCode).list();
				mem.setDeptName(deptList.get(0).toString());
				DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
						.get(DepartmentGrounp.class, mem.getGrounpId());
				mem.setGrounpName(departmentGrounp.getGrounpName());
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mem;
	}

	@SuppressWarnings("unchecked")
	public JSONArray memberDataAnalysis(Integer start, Integer limit,
			Criterion... criterions) throws Exception {
		JSONArray array = new JSONArray();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Criteria cs = session.createCriteria(MemberInfo.class);
			for (Criterion c : criterions) {
				cs.add(c);
			}
			List<MemberInfo> mems = cs.setFirstResult(start).setMaxResults(
					limit).addOrder(Order.asc("inDate")).list();
			for (MemberInfo mem : mems) {
				JSONObject object = JSONObject.fromObject(mem,
						JSONValueProcessor.cycleExcludel(new String[] { "cm" },
								"yyyy-MM-dd"));
				List<OutpatientRecord> records = session.createCriteria(
						OutpatientRecord.class).add(
						Restrictions.eq("patient", mem.getPatient())).addOrder(
						Order.asc("times")).list();
				// System.out.println("memPatient:" +
				// mem.getPatient().getPatientName() + "-" +
				// mem.getPatient().getId());
				// ***************************************************************begin
				List<InHspCaseMaster> cmList = session.createCriteria(
						InHspCaseMaster.class).add(
						Restrictions.eq("patientId", mem.getPatient().getId()))
						.addOrder(Order.desc("inHspDate")).list();
				OutHspRec outHspRec = new OutHspRec();
				if (cmList != null && cmList.size() != 0) {
					InHspCaseMaster cm = cmList.get(0);
					List<OutHspRec> outHspRecList = session.createCriteria(
							OutHspRec.class).add(
							Restrictions.eq("caseId", cm.getId())).list();
					if (outHspRecList != null && outHspRecList.size() != 0) {
						outHspRec = outHspRecList.get(0);
					}
				}
				JSONObject outHspRecJson = JSONObject.fromObject(outHspRec);
				JSONArray recordArray = JSONArray.fromObject(records,
						JSONValueProcessor.cycleExcludel(
								new String[] { "patient" }, "yyyy/MM/dd"));
				object.put("records", recordArray);
				object.put("outHspRec", outHspRecJson);
				// ***********************************************************end
				// JSONArray recordArray = JSONArray.fromObject(records,
				// JSONValueProcessor.cycleExcludel(
				// new String[] { "patient" }, "yyyy/MM/dd"));
				object.put("records", recordArray);
				array.add(object);
			}
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return array;
	}

	public String memberDataAnalysisToExcel(JSONArray array, String tempPath)
			throws Exception {
		String path = new Date().getTime() + ".xls";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			File file = new File(tempPath + path);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			short currentColIndex = 0;
			// 表头公共部分
			memberDataAnalysisToExcel_setCellValue(sheet, 0, currentColIndex,
					"姓名");
			currentColIndex++;
			memberDataAnalysisToExcel_setCellValue(sheet, 0, currentColIndex,
					"病案号");
			currentColIndex++;
			memberDataAnalysisToExcel_setCellValue(sheet, 0, currentColIndex,
					"入会科室");
			currentColIndex++;
			memberDataAnalysisToExcel_setCellValue(sheet, 0, currentColIndex,
					"入会日期");
			currentColIndex++;
			memberDataAnalysisToExcel_setCellValue(sheet, 0, currentColIndex,
					"首次分组");
			currentColIndex++;
			memberDataAnalysisToExcel_setCellValue(sheet, 0, currentColIndex,
					"当前分组");
			currentColIndex++;
			memberDataAnalysisToExcel_setCellValue(sheet, 0, currentColIndex,
					"随访次数");
			int maxSuifangTimes = 0;
			for (int i = 1, len = array.size() + 1; i < len; i++) {
				JSONObject object = array.getJSONObject(i - 1);
				JSONArray recordArray = object.getJSONArray("records");
				object.remove("records");
				MemberInfo mem = (MemberInfo) JSONObject.toBean(object,
						MemberInfo.class);
				Patient pat = (Patient) JSONObject.toBean(object
						.getJSONObject("patient"), Patient.class);
				List<OutpatientRecord> records = new ArrayList<OutpatientRecord>();
				for (Object obj : recordArray) {
					JSONObject re = JSONObject.fromObject(obj);
					OutpatientRecord record = (OutpatientRecord) JSONObject
							.toBean(re, OutpatientRecord.class);
					OutpatientGenerator g1 = (OutpatientGenerator) JSONObject
							.toBean(re.getJSONObject("generator1"),
									OutpatientGenerator.class);
					record.setGenerator1(g1);
					records.add(record);
				}
				// 姓名
				memberDataAnalysisToExcel_setCellValue(sheet, i, (short) 0, pat
						.getPatientName());
				// 病案号
				memberDataAnalysisToExcel_setCellValue(sheet, i, (short) 1, pat
						.getPatientNo());
				// 入会科室
				memberDataAnalysisToExcel_setCellValue(sheet, i, (short) 2, mem
						.getInWard());
				// 入会日期
				Date temp = mem.getInDate();
				if (temp != null)
					memberDataAnalysisToExcel_setCellValue(sheet, i, (short) 3,
							format.format(temp));
				// 当前分组
				memberDataAnalysisToExcel_setCellValue(sheet, i, (short) 5, mem
						.getCurrentGroup());
				// 首次分组
				String firstGroup = mem.getCurrentGroup();
				if (records.size() > 0) {
					firstGroup = records.get(0).getGenerator1().getGroupName();
				}
				memberDataAnalysisToExcel_setCellValue(sheet, i, (short) 4,
						firstGroup);
				// 随访次数
				memberDataAnalysisToExcel_setCellValue(sheet, i, (short) 6,
						records.size() + "");
				// 随访表头检查
				for (int k = maxSuifangTimes; k < records.size(); k++) {
					memberDataAnalysisToExcel_setCellValue(sheet, 0,
							(short) (6 + k * 3 + 1), "随访" + (k + 1) + "-时间");
					memberDataAnalysisToExcel_setCellValue(sheet, 0,
							(short) (6 + k * 3 + 2), "随访" + (k + 1) + "-周期");
					memberDataAnalysisToExcel_setCellValue(sheet, 0,
							(short) (6 + k * 3 + 3), "随访" + (k + 1) + "-分组");
				}
				maxSuifangTimes = records.size();
				// 填充随访记录
				for (int k = 0, rlength = records.size(); k < rlength; k++) {
					OutpatientGenerator g = records.get(k).getGenerator1();
					memberDataAnalysisToExcel_setCellValue(sheet, i,
							(short) (6 + k * 3 + 1), format.format(g
									.getStartDate()));
					memberDataAnalysisToExcel_setCellValue(sheet, i,
							(short) (6 + k * 3 + 2), g.getCycle() + "");
					memberDataAnalysisToExcel_setCellValue(sheet, i,
							(short) (6 + k * 3 + 3), g.getGroupName());
				}
			}
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			throw e;
		}
		return path;
	}

	private void memberDataAnalysisToExcel_setCellValue(HSSFSheet sheet,
			int rowIndex, short colIndex, String value) {
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

	// 加载某个责任科室下医生的所有的会员信息
	public JSONObject getMemberByDoctorId(Integer start, Integer limit,
			Long doctorId, String search, String flag, String orderBy,
			String descOrasc) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map> allList = new ArrayList<Map>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="
					+ doctorId;
			
			List docList = session.createSQLQuery(docSql).list();
			int allCount = 0;
			if (docList != null && docList.size() > 0) {
				conn = DatabaseUtil.getConnection();
				for (int z = 0, sizez = docList.size(); z < sizez; z++) {
					Object[] docObject = (Object[]) docList.get(z);
					String memberInfoHql = "select mem.*, pc.pcount as pcount,pa.diagGrounp from MemberInfo mem left join t_PlanCount pc on mem.patient= pc.patientId inner join t_patient pa on pa.id = mem.patient";
					// liugang 2011-09-14 update 添加病人姓名查询
					// liugang 2011-09-14 update
					if (search != null && search.length() > 0) {
						JSONObject obj = JSONObject.fromObject(search);
						if (obj.containsKey("name")) {
							if (obj.getString("name").length() > 0) {
								memberInfoHql += " and pa.patientName like '%"
										+ obj.getString("name") + "%'";
							}
						}
						if (obj.containsKey("patientNo")) {
							if (obj.getString("patientNo").length() > 0) {
								memberInfoHql += " and pa.patientNo='"
										+ obj.getString("patientNo") + "'";
							}
						}
						if (obj.containsKey("account")) {
							if (obj.getString("account").length() > 0) {
								memberInfoHql += " and mem.account='"
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
									memberInfoHql += " and mem.inDate between '"
											+ d[0].toLocaleString()
											+ "' and '"
											+ d[1].toLocaleString() + "'";
								}
							}
						}
						if (obj.containsKey("age")) {
							if (obj.getString("age").length() > 0) {
								String ageCondition = obj.getString("age");
								Calendar cal = Calendar.getInstance();
								int thisYear = cal.get(Calendar.YEAR);
								if(ageCondition.contains(">")){  //如果查询条件是年龄大于多少岁
									ageCondition = ageCondition.replace(">", "");
									memberInfoHql += " and datediff(year,pa.birthday,getdate()) > "+ageCondition;//注意这里数据库中birthday字段是datetime类型，模糊查询要用convert 转换
								}else if(ageCondition.contains("<")){  //如果查询条件是年龄小于多少岁
									ageCondition = ageCondition.replace("<", "");
									memberInfoHql += " and datediff(year,pa.birthday,getdate()) < "+ageCondition; //注意这里数据库中birthday字段是datetime类型，模糊查询要用convert 转换
								}else{   //如果查询条件是年龄等于多少岁
									memberInfoHql += " and datediff(year,pa.birthday,getdate()) = "+ageCondition; //注意这里数据库中birthday字段是datetime类型，模糊查询要用convert 转换
								}
							}                           
						}
					
					} 
					memberInfoHql += " where mem.deptCode='" + docObject[1]
								+ "' and mem.grounpId=" + docObject[2];
					
					if (flag != null && flag.equals("zhu")) {
						memberInfoHql += " and mem.inHspTimes is not null";
					} else if (flag != null && flag.equals("men")) {
						memberInfoHql += " and mem.inOutTimesDate is not null";
					}

					// liugang 2011-09-14 update 提供默认排序
					memberInfoHql += " order by mem.inDate desc";
					// liugang 2011-09-14 update 提供默认排序

					List attemListSize = session.createSQLQuery(memberInfoHql)
							.list();
					allCount += attemListSize.size();
					List attemList = session.createSQLQuery(memberInfoHql)
							.setFirstResult(start).setMaxResults(limit).list();
					for (int j = 0, sizej = attemList.size(); j < sizej; j++) {
						Object[] mem = (Object[]) attemList.get(j);
						/*
						for(int x =0; x<mem.length; x++) {
							System.out.println("mem " + x + ":  " + mem[x]);
						}*/
						Map mp = new HashMap();
						mp.put("memberNo", mem[0]);
						mp.put("account", mem[1]);
						mp.put("password", mem[2]);
						mp.put("inDate", sdf.format(mem[3]));
						mp.put("memberStatus", mem[4]);
						mp.put("memberType", mem[5]);
						mp.put("memo", mem[6]);
						Patient patient = (Patient) session.get(Patient.class,
								Long.parseLong(mem[7].toString()));
						mp.put("patient", patient);
						if (patient != null) {
							mp.put("patientName", patient.getPatientName());
							mp.put("patientNo", patient.getPatientNo());
							mp.put("birthday", sdf.format(patient.getBirthday()));
						}
						String jianCha = "select outH.xNo,outH.ctNo,outH.mriNo,outH.blNo from t_InHsp_CaseMaster caseM inner join t_InHsp_Liver_OutHspRec outH on outH.caseId = caseM.id where caseM.patientId="
							+ patient.getId()+" order by caseM.inHspDate desc";
						List jianChaList = session.createSQLQuery(jianCha).list();
						if(jianChaList != null && jianChaList.size() > 0){
							Object[] objJ = (Object[])jianChaList.get(0);
							mp.put("xNo", objJ[0]);
							mp.put("ctNo", objJ[1]);
							mp.put("mriNo", objJ[2]);
							mp.put("blNo", objJ[3]);
						}
						InHspCaseMaster cm = new InHspCaseMaster();
						if (mem[8] != null) {
							cm.setId(Long.parseLong(mem[8].toString()));
						}
						mp.put("cm", cm);
						// liugang 2011-08-06 修改 start
						/*
						String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
								+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
								+ " where hsp.patientId = "
								+ Long.parseLong(mem[7].toString())
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
								+ Long.parseLong(mem[7].toString())
								+ " order by hlg.id desc";
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
								mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
							} else {
								mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
							}
						} else if (firstDate != null) {
							mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
						} else if (secondDate != null) {
							mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
						} else {
							mp.put("currentGroup", "");
						}
						*/
						// liugang 2011-08-06 修改 end
						mp.put("currentGroup", mem[22]);
						mp.put("inWard", mem[10]);
						mp.put("biaoben", mem[11]);
						String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
								+ mem[15] + "'";
						List deptList = session.createSQLQuery(hqlCode).list();
						DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
								.get(DepartmentGrounp.class, Long
										.parseLong(mem[16].toString()));
						mp.put("deptName", deptList.get(0).toString());
						mp.put("inDoctor", mem[13]);
						mp.put("pcount", mem[21]);
						mp.put("grounpName", departmentGrounp.getGrounpName());
						mp.put("deptCode", mem[15]);
						mp.put("grounpId", mem[16]);
						allList.add(mp);
					}
				}
			}
			tran.commit();
			if ("desc".equals(descOrasc)) {
				Comparator com1 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));

					}
				};
				Collections.sort(allList, com1);
			} else if ("asc".equals(descOrasc)) {
				Comparator com2 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return -concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));
					}
				};
				Collections.sort(allList, com2);
			}
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

	// 加载某个责任科室下医生的所有的会员信息 用于导出电子表格
	public List<Map> getAllMemberByDoctorId(Long doctorId, String search,
			String flag, String orderBy, String descOrasc) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map> allList = new ArrayList<Map>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String docSql = "select tda.* from t_DepartmentAndGrounpMiddle tda inner join t_MemberGrounpAndDoctor tm on tda.grounpId = tm.grounpId and doctorId="
					+ doctorId;
			
			List docList = session.createSQLQuery(docSql).list();
			int allCount = 0;
			if (docList != null && docList.size() > 0) {
				conn = DatabaseUtil.getConnection();
				for (int z = 0, sizez = docList.size(); z < sizez; z++) {
					Object[] docObject = (Object[]) docList.get(z);
					String memberInfoHql = "select mem.*, pc.pcount as pcount,pa.diagGrounp from MemberInfo mem left join t_PlanCount pc on mem.patient= pc.patientId inner join t_patient pa on pa.id = mem.patient ";
					// liugang 2011-09-14 update 添加病人姓名查询
					// liugang 2011-09-14 update
					if (search != null && search.length() > 0) {
						JSONObject obj = JSONObject.fromObject(search);
						if (obj.containsKey("name")) {
							if (obj.getString("name").length() > 0) {
								memberInfoHql += " and pa.patientName like '%"
										+ obj.getString("name") + "%'";
							}
						}
						if (obj.containsKey("patientNo")) {
							if (obj.getString("patientNo").length() > 0) {
								memberInfoHql += " and pa.patientNo='"
										+ obj.getString("patientNo") + "'";
							}
						}
						if (obj.containsKey("account")) {
							if (obj.getString("account").length() > 0) {
								memberInfoHql += " and mem.account='"
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
									memberInfoHql += " and mem.inDate between '"
											+ d[0].toLocaleString()
											+ "' and '"
											+ d[1].toLocaleString() + "'";
								}
							}
						}
					}
					memberInfoHql += " where mem.deptCode='" + docObject[1]
								+ "' and mem.grounpId=" + docObject[2];
					
					if (flag != null && flag.equals("zhu")) {
						memberInfoHql += " and mem.inHspTimes is not null";
					} else if (flag != null && flag.equals("men")) {
						memberInfoHql += " and mem.inOutTimesDate is not null";
					}
					// liugang 2011-09-14 update 提供默认排序
					// memberInfoHql += " order by mem.inDate desc";
					// liugang 2011-09-14 update 提供默认排序

					List attemListSize = session.createSQLQuery(memberInfoHql)
							.list();
					for (int j = 0, sizej = attemListSize.size(); j < sizej; j++) {
						Object[] mem = (Object[]) attemListSize.get(j);
						Map mp = new HashMap();
						mp.put("memberNo", mem[0]);
						mp.put("account", mem[1]);
						mp.put("password", mem[2]);
						mp.put("inDate", sdf.format(mem[3]));
						mp.put("memberStatus", mem[4]);
						mp.put("memberType", mem[5]);
						mp.put("memo", mem[6]);
						Patient patient = (Patient) session.get(Patient.class,
								Long.parseLong(mem[7].toString()));
						mp.put("patient", patient);
						if (patient != null) {
							mp.put("patientName", patient.getPatientName());
							mp.put("patientNo", patient.getPatientNo());
						}
						String jianCha = "select outH.xNo,outH.ctNo,outH.mriNo,outH.blNo from t_InHsp_CaseMaster caseM inner join t_InHsp_Liver_OutHspRec outH on outH.caseId = caseM.id where caseM.patientId="
							+ patient.getId()+" order by caseM.inHspDate desc";
						List jianChaList = session.createSQLQuery(jianCha).list();
						if(jianChaList != null && jianChaList.size() > 0){
							Object[] objJ = (Object[])jianChaList.get(0);
							mp.put("xNo", objJ[0]);
							mp.put("ctNo", objJ[1]);
							mp.put("mriNo", objJ[2]);
							mp.put("blNo", objJ[3]);
						}
						InHspCaseMaster cm = new InHspCaseMaster();
						if (mem[8] != null) {
							cm.setId(Long.parseLong(mem[8].toString()));
						}
						mp.put("cm", cm);
						// liugang 2011-08-06 修改 start
						/*
						String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
								+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
								+ " where hsp.patientId = "
								+ Long.parseLong(mem[7].toString())
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
								+ Long.parseLong(mem[7].toString())
								+ " order by hlg.id desc";
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
								mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
							} else {
								mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
							}
						} else if (firstDate != null) {
							mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
						} else if (secondDate != null) {
							mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
						} else {
							mp.put("currentGroup", "");
						}
						*/
						// liugang 2011-08-06 修改 end
						mp.put("currentGroup", mem[22]);
						mp.put("inWard", mem[10]);
						mp.put("biaoben", mem[11]);
						String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
								+ mem[15] + "'";
						List deptList = session.createSQLQuery(hqlCode).list();
						DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
								.get(DepartmentGrounp.class, Long
										.parseLong(mem[16].toString()));
						mp.put("deptName", deptList.get(0).toString());
						mp.put("inDoctor", mem[13]);
						mp.put("pcount", mem[21]);
						mp.put("grounpName", departmentGrounp.getGrounpName());
						allList.add(mp);
					}
				}
			}
			tran.commit();
			if ("desc".equals(descOrasc)) {
				Comparator com1 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));

					}
				};
				Collections.sort(allList, com1);
			} else if ("asc".equals(descOrasc)) {
				Comparator com2 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return -concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));
					}
				};
				Collections.sort(allList, com2);
			}
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
		return allList;
	}

	// 加载所有会员 用于导出电子表格
	public List<Map> getAllMemberForYuanzhang(String search,
			String flag, String descOrasc) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map> allList = new ArrayList<Map>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String memberInfoHql = "select  mem.*, pc.pcount as pcount,pa.diagGrounp from MemberInfo mem left join t_PlanCount pc on mem.patient = pc.patientId inner join t_patient pa on pa.id = mem.patient ";
			// liugang 2011-09-14 update 添加病人姓名查询
			// liugang 2011-09-14 update
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if (obj.containsKey("name")) {
					if (obj.getString("name").length() > 0) {
						memberInfoHql += " and pa.patientName like '%"
								+ obj.getString("name") + "%'";
					}
				}
				if (obj.containsKey("patientNo")) {
					if (obj.getString("patientNo").length() > 0) {
						memberInfoHql += " and pa.patientNo='"
								+ obj.getString("patientNo") + "'";
					}
				}
				if (obj.containsKey("account")) {
					if (obj.getString("account").length() > 0) {
						memberInfoHql += " and mem.account='"
								+ obj.getString("account") + "'";
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
							memberInfoHql += " and mem.inDate between '"
									+ d[0].toLocaleString() + "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}

			/*	memberInfoHql += " and mem.deptCode='" + deptCode + "'";*/

				if (obj.containsKey("grounpId")) {
					if (obj.getString("grounpId").length() > 0 && !"null".equals(obj.getString("grounpId"))) {
						memberInfoHql += " and mem.grounpId="
								+ obj.getString("grounpId");
					}
				}
			} else {
				/*memberInfoHql += " where mem.deptCode='" + deptCode + "'";*/
			}
			if (flag != null && flag.equals("zhu")) {
				memberInfoHql += " and mem.inHspTimes is not null";
			} else if (flag != null && flag.equals("men")) {
				memberInfoHql += " and mem.inOutTimesDate is not null";
			}
			
			// liugang 2011-09-14 update 提供默认排序
			// memberInfoHql += " order by mem.inDate desc";
			// liugang 2011-09-14 update 提供默认排序

			List attemListSize = session.createSQLQuery(memberInfoHql).list();
			int allCount = attemListSize.size();
			List attemList = session.createSQLQuery(memberInfoHql).list();
			conn = DatabaseUtil.getConnection();
			for (int j = 0, sizej = attemList.size(); j < sizej; j++) {
				Object[] mem = (Object[]) attemList.get(j);
				Map mp = new HashMap();
				mp.put("memberNo", mem[0]);
				mp.put("account", mem[1]);
				mp.put("password", mem[2]);
				mp.put("inDate", sdf.format(mem[3]));
				mp.put("memberStatus", mem[4]);
				mp.put("memberType", mem[5]);
				mp.put("memo", mem[6]);
				Patient patient = (Patient) session.get(Patient.class, Long
						.parseLong(mem[7].toString()));
				mp.put("patient", patient);
				if (patient != null) {
					mp.put("patientName", patient.getPatientName());
					mp.put("patientNo", patient.getPatientNo());
				}
				String jianCha = "select outH.xNo,outH.ctNo,outH.mriNo,outH.blNo from t_InHsp_CaseMaster caseM inner join t_InHsp_Liver_OutHspRec outH on outH.caseId = caseM.id where caseM.patientId="
					+ patient.getId()+" order by caseM.inHspDate desc";
				List jianChaList = session.createSQLQuery(jianCha).list();
				if(jianChaList != null && jianChaList.size() > 0){
					Object[] objJ = (Object[])jianChaList.get(0);
					mp.put("xNo", objJ[0]);
					mp.put("ctNo", objJ[1]);
					mp.put("mriNo", objJ[2]);
					mp.put("blNo", objJ[3]);
				}
				InHspCaseMaster cm = new InHspCaseMaster();
				if (mem[8] != null) {
					cm.setId(Long.parseLong(mem[8].toString()));
				}
				mp.put("cm", cm);
				// liugang 2011-08-06 修改 start
				/*
				String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
						+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
						+ " where hsp.patientId = "
						+ Long.parseLong(mem[7].toString())
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(curSql);
				rs = ps.executeQuery();
				Date firstDate = null;
				String diag1 = null;
				Date secondDate = null;
				String secondDiag = null;
				if (rs.next()) {
					firstDate = rs.getDate("date");
					diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>", " ");// 十二分级中的第一级
					diag1 = diag1.replaceAll("[\\n|\\r]", " ");
				}
				String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
						+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
						+ Long.parseLong(mem[7].toString())
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(memSql);
				rs = ps.executeQuery();
				if (rs.next()) {
					secondDate = rs.getDate("date");
					secondDiag = rs.getString("diag1").replaceAll("<[.[^<]]*>",
							" ");// 十二分级中的第一级
					secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
				}
				if (firstDate != null && secondDate != null) {
					if (firstDate.getTime() > secondDate.getTime()) {
						mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
					}
				} else if (firstDate != null) {
					mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
				} else if (secondDate != null) {
					mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
				} else {
					mp.put("currentGroup", "");
				}
				*/
				// liugang 2011-08-06 修改 end
				mp.put("currentGroup", mem[22]);
				mp.put("inWard", mem[10]);
				mp.put("biaoben", mem[11]);
				String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+ mem[15] + "'";
				List deptList = session.createSQLQuery(hqlCode).list();
				DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
						.get(DepartmentGrounp.class, Long.parseLong(mem[16]
								.toString()));
				mp.put("deptName", deptList.get(0).toString());
				mp.put("inDoctor", mem[13]);
				mp.put("pcount", mem[21]);
				mp.put("grounpName", departmentGrounp.getGrounpName());
				allList.add(mp);
			}

			tran.commit();
			if ("desc".equals(descOrasc)) {
				Comparator com1 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));

					}
				};
				Collections.sort(allList, com1);
			} else if ("asc".equals(descOrasc)) {
				Comparator com2 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return -concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));
					}
				};
				Collections.sort(allList, com2);
			}
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
	
	
	// 加载管理员所在科室下的所有会员 用于导出电子表格
	public List<Map> getAllMemberByDeptCode(String deptCode, String search,
			String flag, String descOrasc) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map> allList = new ArrayList<Map>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String memberInfoHql = "select mem.*, pc.pcount as pcount,pa.diagGrounp from MemberInfo mem left join t_PlanCount pc on mem.patient = pc.patientId inner join t_patient pa on pa.id = mem.patient ";
			// liugang 2011-09-14 update 添加病人姓名查询
			// liugang 2011-09-14 update
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if (obj.containsKey("name")) {
					if (obj.getString("name").length() > 0) {
						memberInfoHql += " and pa.patientName like '%"
								+ obj.getString("name") + "%'";
					}
				}
				if (obj.containsKey("patientNo")) {
					if (obj.getString("patientNo").length() > 0) {
						memberInfoHql += " and pa.patientNo='"
								+ obj.getString("patientNo") + "'";
					}
				}
				if (obj.containsKey("account")) {
					if (obj.getString("account").length() > 0) {
						memberInfoHql += " and mem.account='"
								+ obj.getString("account") + "'";
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
							memberInfoHql += " and mem.inDate between '"
									+ d[0].toLocaleString() + "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}

				memberInfoHql += " and mem.deptCode='" + deptCode + "'";

				if (obj.containsKey("grounpId")) {
					if (obj.getString("grounpId").length() > 0 && !"null".equals(obj.getString("grounpId"))) {
						memberInfoHql += " and mem.grounpId="
								+ obj.getString("grounpId");
					}
				}
			} else {
				memberInfoHql += " where mem.deptCode='" + deptCode + "'";
			}
			if (flag != null && flag.equals("zhu")) {
				memberInfoHql += " and mem.inHspTimes is not null";
			} else if (flag != null && flag.equals("men")) {
				memberInfoHql += " and mem.inOutTimesDate is not null";
			}

			// liugang 2011-09-14 update 提供默认排序
			// memberInfoHql += " order by mem.inDate desc";
			// liugang 2011-09-14 update 提供默认排序

			List attemListSize = session.createSQLQuery(memberInfoHql).list();
			int allCount = attemListSize.size();
			List attemList = session.createSQLQuery(memberInfoHql).list();
			conn = DatabaseUtil.getConnection();
			for (int j = 0, sizej = attemList.size(); j < sizej; j++) {
				Object[] mem = (Object[]) attemList.get(j);
				Map mp = new HashMap();
				mp.put("memberNo", mem[0]);
				mp.put("account", mem[1]);
				mp.put("password", mem[2]);
				mp.put("inDate", sdf.format(mem[3]));
				mp.put("memberStatus", mem[4]);
				mp.put("memberType", mem[5]);
				mp.put("memo", mem[6]);
				Patient patient = (Patient) session.get(Patient.class, Long
						.parseLong(mem[7].toString()));
				mp.put("patient", patient);
				if (patient != null) {
					mp.put("patientName", patient.getPatientName());
					mp.put("patientNo", patient.getPatientNo());
				}
				String jianCha = "select outH.xNo,outH.ctNo,outH.mriNo,outH.blNo from t_InHsp_CaseMaster caseM inner join t_InHsp_Liver_OutHspRec outH on outH.caseId = caseM.id where caseM.patientId="
					+ patient.getId()+" order by caseM.inHspDate desc";
				List jianChaList = session.createSQLQuery(jianCha).list();
				if(jianChaList != null && jianChaList.size() > 0){
					Object[] objJ = (Object[])jianChaList.get(0);
					mp.put("xNo", objJ[0]);
					mp.put("ctNo", objJ[1]);
					mp.put("mriNo", objJ[2]);
					mp.put("blNo", objJ[3]);
				}
				InHspCaseMaster cm = new InHspCaseMaster();
				if (mem[8] != null) {
					cm.setId(Long.parseLong(mem[8].toString()));
				}
				mp.put("cm", cm);
				// liugang 2011-08-06 修改 start
				/*
				String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
						+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
						+ " where hsp.patientId = "
						+ Long.parseLong(mem[7].toString())
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(curSql);
				rs = ps.executeQuery();
				Date firstDate = null;
				String diag1 = null;
				Date secondDate = null;
				String secondDiag = null;
				if (rs.next()) {
					firstDate = rs.getDate("date");
					diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>", " ");// 十二分级中的第一级
					diag1 = diag1.replaceAll("[\\n|\\r]", " ");
				}
				String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
						+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
						+ Long.parseLong(mem[7].toString())
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(memSql);
				rs = ps.executeQuery();
				if (rs.next()) {
					secondDate = rs.getDate("date");
					secondDiag = rs.getString("diag1").replaceAll("<[.[^<]]*>",
							" ");// 十二分级中的第一级
					secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
				}
				if (firstDate != null && secondDate != null) {
					if (firstDate.getTime() > secondDate.getTime()) {
						mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
					}
				} else if (firstDate != null) {
					mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
				} else if (secondDate != null) {
					mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
				} else {
					mp.put("currentGroup", "");
				}
				*/
				// liugang 2011-08-06 修改 end
				mp.put("currentGroup", mem[22]);
				mp.put("inWard", mem[10]);
				mp.put("biaoben", mem[11]);
				String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+ mem[15] + "'";
				List deptList = session.createSQLQuery(hqlCode).list();
				DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
						.get(DepartmentGrounp.class, Long.parseLong(mem[16]
								.toString()));
				mp.put("deptName", deptList.get(0).toString());
				mp.put("inDoctor", mem[13]);
				mp.put("pcount", mem[21]);
				mp.put("grounpName", departmentGrounp.getGrounpName());
				allList.add(mp);
			}

			tran.commit();
			if ("desc".equals(descOrasc)) {
				Comparator com1 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));

					}
				};
				Collections.sort(allList, com1);
			} else if ("asc".equals(descOrasc)) {
				Comparator com2 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return -concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));
					}
				};
				Collections.sort(allList, com2);
			}
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

	// 加载管理员所在科室的会员 分页显示
	public JSONObject getMemberByDeptCode(Integer start, Integer limit,
			String deptCode, String search, String flag, String descOrasc) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map> allList = new ArrayList<Map>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String memberInfoHql = "select mem.*, pc.pcount as pcount,pa.diagGrounp from MemberInfo mem left join t_PlanCount pc on mem.patient = pc.patientId inner join t_patient pa on pa.id = mem.patient ";
			// liugang 2011-09-14 update 添加病人姓名查询
			// liugang 2011-09-14 update
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if (obj.containsKey("name")) {
					if (obj.getString("name").length() > 0) {
						memberInfoHql += " and pa.patientName like '%"
								+ obj.getString("name") + "%'";
					}
				}
				if (obj.containsKey("patientNo")) {
					if (obj.getString("patientNo").length() > 0) {
						memberInfoHql += " and pa.patientNo='"
								+ obj.getString("patientNo") + "'";
					}
				}
				if (obj.containsKey("account")) {
					if (obj.getString("account").length() > 0) {
						memberInfoHql += " and mem.account='"
								+ obj.getString("account") + "'";
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
							memberInfoHql += " and mem.inDate between '"
									+ d[0].toLocaleString() + "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
				if (obj.containsKey("age")) {
					if (obj.getString("age").length() > 0) {
						String ageCondition = obj.getString("age");
						Calendar cal = Calendar.getInstance();
						int thisYear = cal.get(Calendar.YEAR);
						if(ageCondition.contains(">")){  //如果查询条件是年龄大于多少岁
							ageCondition = ageCondition.replace(">", "");
							memberInfoHql += " and datediff(year,pa.birthday,getdate()) > "+ageCondition;//注意这里数据库中birthday字段是datetime类型，模糊查询要用convert 转换
						}else if(ageCondition.contains("<")){  //如果查询条件是年龄小于多少岁
							ageCondition = ageCondition.replace("<", "");
							memberInfoHql += " and datediff(year,pa.birthday,getdate()) < "+ageCondition; //注意这里数据库中birthday字段是datetime类型，模糊查询要用convert 转换
						}else{   //如果查询条件是年龄等于多少岁
							memberInfoHql += " and datediff(year,pa.birthday,getdate()) = "+ageCondition; //注意这里数据库中birthday字段是datetime类型，模糊查询要用convert 转换
						}
					}                           
				}
				memberInfoHql += " and mem.deptCode='" + deptCode + "'";

				if (obj.containsKey("grounpId")) {
					if (obj.getString("grounpId").length() > 0 && !"null".equals(obj.getString("grounpId"))) {
						memberInfoHql += " and mem.grounpId="
								+ obj.getString("grounpId");
					}
				}
				if (obj.containsKey("diaggroup")) {
					if (obj.getString("diaggroup").length() > 0 && !"null".equals(obj.getString("diaggroup"))) {
						memberInfoHql += " and pa.diaggrounp like '%" + obj.getString("diaggroup") + "%'";
					}
				}
			} else {
				memberInfoHql += " where mem.deptCode='" + deptCode + "'";
			}
			if (flag != null && flag.equals("zhu")) {
				memberInfoHql += " and mem.inHspTimes is not null";
			} else if (flag != null && flag.equals("men")) {
				memberInfoHql += " and mem.inOutTimesDate is not null";
			}

			// liugang 2011-09-14 update 提供默认排序
			memberInfoHql += " order by mem.inDate desc";
			// liugang 2011-09-14 update 提供默认排序

			List attemListSize = session.createSQLQuery(memberInfoHql).list();
			int allCount = attemListSize.size();
			List attemList = session.createSQLQuery(memberInfoHql)
					.setFirstResult(start).setMaxResults(limit).list();
			conn = DatabaseUtil.getConnection();
			for (int j = 0, sizej = attemList.size(); j < sizej; j++) {
				Object[] mem = (Object[]) attemList.get(j);
				Map mp = new HashMap();
				mp.put("memberNo", mem[0]);
				mp.put("account", mem[1]);
				mp.put("password", mem[2]);
				mp.put("inDate", sdf.format(mem[3]));
				mp.put("memberStatus", mem[4]);
				mp.put("memberType", mem[5]);
				mp.put("memo", mem[6]);
				Patient patient = (Patient) session.get(Patient.class, Long
						.parseLong(mem[7].toString()));
				mp.put("patient", patient);
				String jianCha = "select outH.xNo,outH.ctNo,outH.mriNo,outH.blNo from t_InHsp_CaseMaster caseM inner join t_InHsp_Liver_OutHspRec outH on outH.caseId = caseM.id where caseM.patientId="
						+ patient.getId()+" order by caseM.inHspDate desc";
				List jianChaList = session.createSQLQuery(jianCha).list();
				if(jianChaList != null && jianChaList.size() > 0){
					Object[] objJ = (Object[])jianChaList.get(0);
					mp.put("xNo", objJ[0]);
					mp.put("ctNo", objJ[1]);
					mp.put("mriNo", objJ[2]);
					mp.put("blNo", objJ[3]);
				}
				if (patient != null) {
					mp.put("patientName", patient.getPatientName());
					mp.put("patientNo", patient.getPatientNo());
					mp.put("birthday", sdf.format(patient.getBirthday()));
				}
				InHspCaseMaster cm = new InHspCaseMaster();
				if (mem[8] != null) {
					cm.setId(Long.parseLong(mem[8].toString()));
				}
				mp.put("cm", cm);
				// liugang 2011-08-06 修改 start
				/*
				String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
						+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
						+ " where hsp.patientId = "
						+ Long.parseLong(mem[7].toString())
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(curSql);
				rs = ps.executeQuery();
				Date firstDate = null;
				String diag1 = null;
				Date secondDate = null;
				String secondDiag = null;
				if (rs.next()) {
					firstDate = rs.getDate("date");
					diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>", " ");// 十二分级中的第一级
					diag1 = diag1.replaceAll("[\\n|\\r]", " ");
				}
				String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
						+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
						+ Long.parseLong(mem[7].toString())
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(memSql);
				rs = ps.executeQuery();
				if (rs.next()) {
					secondDate = rs.getDate("date");
					secondDiag = rs.getString("diag1").replaceAll("<[.[^<]]*>",
							" ");// 十二分级中的第一级
					secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
				}
				if (firstDate != null && secondDate != null) {
					if (firstDate.getTime() > secondDate.getTime()) {
						mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
					}
				} else if (firstDate != null) {
					mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
				} else if (secondDate != null) {
					mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
				} else {
					mp.put("currentGroup", "");
				}
				*/
				// liugang 2011-08-06 修改 end
				mp.put("currentGroup", mem[22]);
				mp.put("inWard", mem[10]);
				mp.put("biaoben", mem[11]);
				String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+ mem[15] + "'";
				List deptList = session.createSQLQuery(hqlCode).list();
				DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
						.get(DepartmentGrounp.class, Long.parseLong(mem[16]
								.toString()));
				mp.put("deptName", deptList.get(0).toString());
				mp.put("inDoctor", mem[13]);
				mp.put("pcount", mem[21]);
				mp.put("grounpName", departmentGrounp.getGrounpName());
				allList.add(mp);
			}

			tran.commit();
			if ("desc".equals(descOrasc)) {
				Comparator com1 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));

					}
				};
				Collections.sort(allList, com1);
			} else if ("asc".equals(descOrasc)) {
				Comparator com2 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return -concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));
					}
				};
				Collections.sort(allList, com2);
			}
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

	/**
	 * 删除会员
	 */
	public void deleteMemberById(String memberNo) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = DatabaseUtil.getConnection();
			st = conn.createStatement();
			String sql = "delete from memberInfo where memberNo = '" + memberNo
					+ "'";
			st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DatabaseUtil.closeResource(conn, st, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * liugang 2011-08-17 查找会员健康档案树
	 */
	public JSONArray findMemberHealthRecordByPatientId(Long patientId) {
		JSONArray array = new JSONArray();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String firstHql = "from MemberInfo where patient = " + patientId;
			List firstList = session.createQuery(firstHql).list();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String id = "";
			if (firstList != null && firstList.size() > 0) {
				MemberInfo mem = (MemberInfo) firstList.get(0);
				if (mem.getCm() != null) {
					InHspCaseMaster inHspCaseMaster = (InHspCaseMaster) session
							.get(InHspCaseMaster.class, mem.getCm().getId());
					JSONObject object = new JSONObject();
					object.put("id", inHspCaseMaster.getId());// 显示顺序inHspDate
					object.put("text", "第1次记录"
							+ sdf.format(inHspCaseMaster.getInHspDate()));
					object.put("leaf", true);
					object.put("iconCls", "icon-none");
					object.put("cls", "node");
					object.put("href", "javascript:scrollTo("
							+ inHspCaseMaster.getId() + ")");
					array.add(object);
					id = inHspCaseMaster.getId() + "";
				} else if (mem.getInHspTimes() != null
						&& !("".equals(mem.getInHspTimes()))) {
					JSONObject object = new JSONObject();
					String hql = "from InHspCaseMaster where patientId ="
							+ patientId + " and inHspTimes="
							+ mem.getInHspTimes();
					List oneCase = session.createQuery(hql).list();
					if (oneCase != null && oneCase.size() > 0) {
						InHspCaseMaster inHspCaseMasterOne = (InHspCaseMaster) oneCase
								.get(0);
						object.put("id", inHspCaseMasterOne.getId());// 显示顺序inHspDate
						object
								.put("text", "第1次记录"
										+ sdf.format(inHspCaseMasterOne
												.getInHspDate()));
						object.put("leaf", true);
						object.put("iconCls", "icon-none");
						object.put("cls", "node");
						object.put("href", "javascript:scrollTo("
								+ inHspCaseMasterOne.getId() + ")");
						array.add(object);
						id = inHspCaseMasterOne.getId() + "";
					}
				}
			}
			List<InHspCaseMaster> list = session.createCriteria(
					InHspCaseMaster.class).add(
					Restrictions.eq("patientId", patientId)).add(
					Restrictions.eq("flag", 1)).list();
			int i = 1;
			for (InHspCaseMaster inHsp : list) {
				if ("".equals(id)) {
					JSONObject obj = new JSONObject();
					obj.put("id", inHsp.getId());// 显示顺序inHspDate
					obj.put("text", "第" + i + "次记录"
							+ sdf.format(inHsp.getInHspDate()));
					obj.put("leaf", true);
					obj.put("iconCls", "icon-none");
					obj.put("cls", "node");
					obj.put("href", "javascript:scrollTo(" + inHsp.getId()
							+ ")");
					array.add(obj);
					i++;
				} else if (!id.equals(inHsp.getId().toString())) {
					JSONObject obj = new JSONObject();
					obj.put("id", inHsp.getId());// 显示顺序inHspDate
					obj.put("text", "第" + (i + 1) + "次记录"
							+ sdf.format(inHsp.getInHspDate()));
					obj.put("leaf", true);
					obj.put("iconCls", "icon-none");
					obj.put("cls", "node");
					obj.put("href", "javascript:scrollTo(" + inHsp.getId()
							+ ")");
					array.add(obj);
					i++;
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}

		return array;
	}

	/**
	 * liugang 2011-08-17
	 * 
	 * @param patientId
	 */
	public Map saveMemberHealthRecordByPatientId(Long patientId) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			Patient patient = (Patient) session.get(Patient.class, patientId);
			if (patient != null) {
				// 以下是会员健康记录必须初始化的信息
				InHspCaseMaster inHspCaseMaster = new InHspCaseMaster();
				inHspCaseMaster.setPatientId(patientId);
				inHspCaseMaster.setCaseModuleId("liver");// 默认肝病病历
				inHspCaseMaster.setFlag(1);// 区分住院记录和会员健康记录
				inHspCaseMaster
						.setCurrentWordCode(patient.getCurrentWardCode());
				inHspCaseMaster.setInWardCode(patient.getCurrentWardCode());
				inHspCaseMaster.setInHspDate(new Date());
				Long kid = Long.parseLong(session.save(inHspCaseMaster)
						.toString());
				mp.put("kid", kid);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	public Map firstFindMemberHealthRecordByPatientId(Long patientId) {
		Session session = null;
		Transaction tran = null;
		Map mp = new HashMap();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String firstHql = "from MemberInfo where patient = " + patientId;
			List firstList = session.createQuery(firstHql).list();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String id = "";
			if (firstList != null && firstList.size() > 0) {
				MemberInfo mem = (MemberInfo) firstList.get(0);
				if (mem.getCm() != null) {
					InHspCaseMaster inHspCaseMaster = (InHspCaseMaster) session
							.get(InHspCaseMaster.class, mem.getCm().getId());
					mp.put("kid", inHspCaseMaster.getId());
				} else if (mem.getInHspTimes() != null
						&& !("".equals(mem.getInHspTimes()))) {
					String hql = "from InHspCaseMaster where patientId ="
							+ patientId + " and inHspTimes="
							+ mem.getInHspTimes();
					List oneCase = session.createQuery(hql).list();
					if (oneCase != null && oneCase.size() > 0) {
						InHspCaseMaster inHspCaseMasterOne = (InHspCaseMaster) oneCase
								.get(0);
						mp.put("kid", inHspCaseMasterOne.getId());// 显示顺序inHspDate
					}
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return mp;
	}

	public List<Map> getAllMemberByDeptCode(String ids) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map> allList = new ArrayList<Map>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String memberInfoHql = "select mem.*, pc.pcount as pcount from MemberInfo mem left join t_PlanCount pc on mem.patient = pc.patientId where mem.memberno in ("
					+ ids + ")";
			List attemListSize = session.createSQLQuery(memberInfoHql).list();
			int allCount = attemListSize.size();
			List attemList = session.createSQLQuery(memberInfoHql).list();
			conn = DatabaseUtil.getConnection();
			for (int j = 0, sizej = attemList.size(); j < sizej; j++) {
				Object[] mem = (Object[]) attemList.get(j);
				Map mp = new HashMap();
				mp.put("memberNo", mem[0]);
				mp.put("account", mem[1]);
				mp.put("password", mem[2]);
				mp.put("inDate", sdf.format(mem[3]));
				mp.put("memberStatus", mem[4]);
				mp.put("memberType", mem[5]);
				mp.put("memo", mem[6]);
				Patient patient = (Patient) session.get(Patient.class, Long
						.parseLong(mem[7].toString()));
				mp.put("patient", patient);
				if (patient != null) {
					mp.put("patientName", patient.getPatientName());
					mp.put("patientNo", patient.getPatientNo());
				}
				String jianCha = "select outH.xNo,outH.ctNo,outH.mriNo,outH.blNo from t_InHsp_CaseMaster caseM inner join t_InHsp_Liver_OutHspRec outH on outH.caseId = caseM.id where caseM.patientId="
					+ patient.getId()+" order by caseM.inHspDate desc";
				List jianChaList = session.createSQLQuery(jianCha).list();
				if(jianChaList != null && jianChaList.size() > 0){
					Object[] objJ = (Object[])jianChaList.get(0);
					mp.put("xNo", objJ[0]);
					mp.put("ctNo", objJ[1]);
					mp.put("mriNo", objJ[2]);
					mp.put("blNo", objJ[3]);
				}
				InHspCaseMaster cm = new InHspCaseMaster();
				if (mem[8] != null) {
					cm.setId(Long.parseLong(mem[8].toString()));
				}
				mp.put("cm", cm);
				// liugang 2011-08-06 修改 start
				String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
						+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
						+ " where hsp.patientId = "
						+ Long.parseLong(mem[7].toString())
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(curSql);
				rs = ps.executeQuery();
				Date firstDate = null;
				String diag1 = null;
				Date secondDate = null;
				String secondDiag = null;
				if (rs.next()) {
					firstDate = rs.getDate("date");
					diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>", " ");// 十二分级中的第一级
					diag1 = diag1.replaceAll("[\\n|\\r]", " ");
				}
				String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
						+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
						+ Long.parseLong(mem[7].toString())
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(memSql);
				rs = ps.executeQuery();
				if (rs.next()) {
					secondDate = rs.getDate("date");
					secondDiag = rs.getString("diag1").replaceAll("<[.[^<]]*>",
							" ");// 十二分级中的第一级
					secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
				}
				if (firstDate != null && secondDate != null) {
					if (firstDate.getTime() > secondDate.getTime()) {
						mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
					}
				} else if (firstDate != null) {
					mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
				} else if (secondDate != null) {
					mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
				} else {
					mp.put("currentGroup", "");
				}
				// liugang 2011-08-06 修改 end
				mp.put("inWard", mem[10]);
				mp.put("biaoben", mem[11]);
				String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+ mem[15] + "'";
				List deptList = session.createSQLQuery(hqlCode).list();
				DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
						.get(DepartmentGrounp.class, Long.parseLong(mem[16]
								.toString()));
				mp.put("deptName", deptList.get(0).toString());
				mp.put("inDoctor", mem[13]);
				mp.put("pcount", mem[21]);
				mp.put("grounpName", departmentGrounp.getGrounpName());
				allList.add(mp);
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

	public Map findPatient(Long patientId) {
		Session session = null;
		Transaction tran = null;
		Map map = new HashMap();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String sql ="select p.patientName,p.mobilePhone,mem.memberNo from t_patient p" +
					" inner join memberinfo mem on mem.patient = p.id where p.id ="+patientId;
			List list = session.createSQLQuery(sql).list();
			if(list != null && list.size() > 0){
				Object[] obj = (Object[])list.get(0);
				map.put("patientName", obj[0]);
				map.put("mobilePhone", obj[1]);
				map.put("memberNo", obj[2]);
			}
			tran.commit();
		} catch (Exception e) {
			if(tran !=null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return map;
	}

	public JSONObject getYWMemberByDeptCode(Integer start, Integer limit, String search, String flag, String descOrasc) {
		Session session = null;
		Transaction tran = null;
		JSONObject json = new JSONObject();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map> allList = new ArrayList<Map>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String memberInfoHql = "select mem.*, pc.pcount,pa.diagGrounp from MemberInfo mem left join t_PlanCount pc on mem.patient = pc.patientId inner join t_patient pa on pa.id = mem.patient ";
			// liugang 2011-09-14 update 添加病人姓名查询
			// liugang 2011-09-14 update
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if (obj.containsKey("name")) {
					if (obj.getString("name").length() > 0) {
						memberInfoHql += " and pa.patientName like '%"
								+ obj.getString("name") + "%'";
					}
				}
				if (obj.containsKey("patientNo")) {
					if (obj.getString("patientNo").length() > 0) {
						memberInfoHql += " and pa.patientNo='"
								+ obj.getString("patientNo") + "'";
					}
				}
				if (obj.containsKey("account")) {
					if (obj.getString("account").length() > 0) {
						memberInfoHql += " and mem.account='"
								+ obj.getString("account") + "'";
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
							memberInfoHql += " and mem.inDate between '"
									+ d[0].toLocaleString() + "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}


				if (obj.containsKey("grounpId")) {
					if (obj.getString("grounpId").length() > 0 && !"null".equals(obj.getString("grounpId"))) {
						memberInfoHql += " and mem.grounpId="
								+ obj.getString("grounpId");
					}
				}
				
				if (obj.containsKey("diaggroup")) {
					if (obj.getString("diaggroup").length() > 0 && !"null".equals(obj.getString("diaggroup"))) {
						memberInfoHql += " and pa.diaggrounp like '%" + obj.getString("diaggroup") + "%'";
//						memberInfoHql += " and mem.currentGroup is not null";
					}
				}
			} 
			if (flag != null && flag.equals("zhu")) {
				memberInfoHql += " and mem.inHspTimes is not null";
			} else if (flag != null && flag.equals("men")) {
				memberInfoHql += " and mem.inOutTimesDate is not null";
			}

			// liugang 2011-09-14 update 提供默认排序
			memberInfoHql += " order by mem.inDate desc";
			// liugang 2011-09-14 update 提供默认排序

			List attemListSize = session.createSQLQuery(memberInfoHql).list();
			int allCount = attemListSize.size();
			List attemList = session.createSQLQuery(memberInfoHql)
					.setFirstResult(start).setMaxResults(limit).list();
			conn = DatabaseUtil.getConnection();
			for (int j = 0, sizej = attemList.size(); j < sizej; j++) {
				Object[] mem = (Object[]) attemList.get(j);
				Map mp = new HashMap();
				mp.put("memberNo", mem[0]);
				mp.put("account", mem[1]);
				mp.put("password", mem[2]);
				mp.put("inDate", sdf.format(mem[3]));
				mp.put("memberStatus", mem[4]);
				mp.put("memberType", mem[5]);
				mp.put("memo", mem[6]);
				Patient patient = (Patient) session.get(Patient.class, Long
						.parseLong(mem[7].toString()));
				mp.put("patient", patient);
				String jianCha = "select outH.xNo,outH.ctNo,outH.mriNo,outH.blNo from t_InHsp_CaseMaster caseM inner join t_InHsp_Liver_OutHspRec outH on outH.caseId = caseM.id where caseM.patientId="
						+ patient.getId()+" order by caseM.inHspDate desc";
				List jianChaList = session.createSQLQuery(jianCha).list();
				if(jianChaList != null && jianChaList.size() > 0){
					Object[] objJ = (Object[])jianChaList.get(0);
					mp.put("xNo", objJ[0]);
					mp.put("ctNo", objJ[1]);
					mp.put("mriNo", objJ[2]);
					mp.put("blNo", objJ[3]);
				}
				if (patient != null) {
					mp.put("patientName", patient.getPatientName());
					mp.put("patientNo", patient.getPatientNo());
				}
				InHspCaseMaster cm = new InHspCaseMaster();
				if (mem[8] != null) {
					cm.setId(Long.parseLong(mem[8].toString()));
				}
				mp.put("cm", cm);
				// liugang 2011-08-06 修改 start
				/*
				String curSql = "select hlg.diag1,hlg.date from t_InHsp_CaseMaster hsp inner"
						+ " join t_InHsp_Liver_GradingDiag hlg on hlg.cid = hsp.id"
						+ " where hsp.patientId = "
						+ Long.parseLong(mem[7].toString())
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(curSql);
				rs = ps.executeQuery();
				Date firstDate = null;
				String diag1 = null;
				Date secondDate = null;
				String secondDiag = null;
				if (rs.next()) {
					firstDate = rs.getDate("date");
					diag1 = rs.getString("diag1").replaceAll("<[.[^<]]*>", " ");// 十二分级中的第一级
					diag1 = diag1.replaceAll("[\\n|\\r]", " ");
				}
				String memSql = "select hlg.diag1,hlg.date from t_InHsp_Liver_GradingDiag hlg inner join"
						+ " memberinfo mem on mem.memberNo = hlg.cid where mem.patient = "
						+ Long.parseLong(mem[7].toString())
						+ " order by hlg.id desc";
				ps = conn.prepareStatement(memSql);
				rs = ps.executeQuery();
				if (rs.next()) {
					secondDate = rs.getDate("date");
					secondDiag = rs.getString("diag1").replaceAll("<[.[^<]]*>",
							" ");// 十二分级中的第一级
					secondDiag = secondDiag.replaceAll("[\\n|\\r]", " ");
				}
				if (firstDate != null && secondDate != null) {
					if (firstDate.getTime() > secondDate.getTime()) {
						mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
					} else {
						mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
					}
				} else if (firstDate != null) {
					mp.put("currentGroup", diag1);// 借用patient表中的字段，暂时显示当前疾病分
				} else if (secondDate != null) {
					mp.put("currentGroup", secondDiag);// 借用patient表中的字段，暂时显示当前疾病分
				} else {
					mp.put("currentGroup", "");
				}
				*/
				// liugang 2011-08-06 修改 end
				
				mp.put("currentGroup", mem[22]);
				mp.put("inWard", mem[10]);
				mp.put("biaoben", mem[11]);
				String hqlCode = "select deptName from SYS_HIS_DepartmentHISEntity where deptCode='"
						+ mem[15] + "'";
				List deptList = session.createSQLQuery(hqlCode).list();
				DepartmentGrounp departmentGrounp = (DepartmentGrounp) session
						.get(DepartmentGrounp.class, Long.parseLong(mem[16]
								.toString()));
				mp.put("deptName", deptList.get(0).toString());
				mp.put("inDoctor", mem[13]);
				mp.put("pcount", mem[21]);
				mp.put("grounpName", departmentGrounp.getGrounpName());
				allList.add(mp);
			}

			tran.commit();
			if ("desc".equals(descOrasc)) {
				Comparator com1 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));

					}
				};
				Collections.sort(allList, com1);
			} else if ("asc".equals(descOrasc)) {
				Comparator com2 = new Comparator() {
					private String concatPinyinStringArray(String[] pinyinArray) {
						StringBuffer pinyinStrBuf = new StringBuffer();

						if ((null != pinyinArray) && (pinyinArray.length > 0)) {
							for (int i = 0; i < pinyinArray.length; i++) {
								pinyinStrBuf.append(pinyinArray[i]);
							}
						}
						String outputString = pinyinStrBuf.toString();
						return outputString;
					}

					public int compare(Object arg0, Object arg1) {
						Map mp1 = (Map) arg0;
						Map mp2 = (Map) arg1;
						char c1 = mp1.get("patientName").toString().charAt(0);
						char c2 = mp2.get("patientName").toString().charAt(0);

						return -concatPinyinStringArray(
								PinyinHelper.toHanyuPinyinStringArray(c1))
								.compareTo(
										concatPinyinStringArray(PinyinHelper
												.toHanyuPinyinStringArray(c2)));
					}
				};
				Collections.sort(allList, com2);
			}
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

}
