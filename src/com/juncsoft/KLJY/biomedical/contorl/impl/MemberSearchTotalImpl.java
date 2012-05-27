package com.juncsoft.KLJY.biomedical.contorl.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.biomedical.contorl.dao.MemberSearchTotalDao;
import com.juncsoft.KLJY.biomedical.contorl.entity.MemberSearchTotal;
import com.juncsoft.KLJY.biomedical.contorl.entity.MemberSupervision;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class MemberSearchTotalImpl implements MemberSearchTotalDao {
	/*
	 * 会员明细表统计
	 * 
	 */
	@SuppressWarnings("unchecked")
	public JSONObject searchMemberByCondition(int start, int limit) {
		JSONObject json = new JSONObject();
		List<Map> list = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsanswer = null;
		ResultSet rscontent = null;
		ResultSet rsdoctor = null;
		ResultSet grouprs = null;
		Session session = null;
		Transaction tran = null;
		Statement sm = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			conn = DatabaseUtil.getConnection();
			String sql = "select p.patientName,p.patientNo,p.id,m.inDate, m.inWard, m.deptName,m.currentGroup, m.inDoctor," 
					+ "m.memberNo,m.deptName from MemberInfo m,Patient p "
			        + "where m.patient.id = p.id order by m.inDate desc ";
			List resultCount = session.createQuery(sql).list();
			List result = session.createQuery(sql).setFirstResult(start)
					.setMaxResults(limit).list();
			if (result != null && result.size() > 0) {
				for (int i = 0, size = result.size(); i < size; i++) {
					Map map = new HashMap();
					Object[] objs = (Object[]) result.get(i);
					map.put("patientName", objs[0]);// 自己写
					map.put("patientNo", objs[1]);
					map.put("id", objs[2]);
					map.put("inDate", objs[3]);
					map.put("inWard", objs[4]);
					map.put("deptName", objs[5]);
					map.put("currentGroup", objs[6]);
					map.put("inDoctor", objs[7]);
					map.put("memberNo", objs[8]);
					String doctorsql = "select doctor from MemberMsg where memberNo='"
							+ objs[8] + "'";
					ps = conn.prepareStatement(doctorsql);
					rsdoctor = ps.executeQuery();
					if (rsdoctor.next()) {
						if (rsdoctor.getString("doctor") == null) {
							map.put("doctor", "");
						} else {
							map.put("doctor", rsdoctor.getString("doctor"));

						}
					}
					String asksql = "select count(*) from t_PatientConsulting where patientId ='"
							+ objs[2] + "'";
					ps = conn.prepareStatement(asksql);
					rs = ps.executeQuery();
					if (rs.next()) {// 咨询次数
						if (rs.getInt(1) > 0) {
							map.put("accessCount", rs.getInt(1));
						} else {
							map.put("accessCount", 0);
						}
					}
                    String answersql = "select count(*) from t_PatientConsulting tp inner join t_DoctorReplyRecordAndPatientQuestions td on " +
                    		" tp.id=td.pcid and td.drAndpqFlag =0 and tp.patientId='"+ objs[2] +"'";
					ps = conn.prepareStatement(answersql);
					rsanswer = ps.executeQuery();
					if (rsanswer.next()) {//医生回复次数
						if (rsanswer.getInt(1) > 0) {
							map.put("answerberCount", rsanswer.getInt(1));
						} else {
							map.put("answerberCount", 0);
						}
					}
					String contentsql = "select count(*) from t_DoctorRoundsRecord where patientId='"
							+ objs[2] + "'";
					ps = conn.prepareStatement(contentsql);
					rscontent = ps.executeQuery();
					if (rscontent.next()) {// 网上查房数
						if (rscontent.getInt(1) > 0) {
							map.put("contentberCount", rscontent.getInt(1));
						} else {
							map.put("contentberCount", 0);
						}
					}
					String groupsql = "select groupName from OutpatientGenerator where patientId='"
							+ objs[2] + "'";
					ps = conn.prepareStatement(groupsql);
					grouprs = ps.executeQuery();
					if (grouprs.next()) {
						if (grouprs.getString("groupName") == null) {
							map.put("groupName", "");
						} else {
							map.put("groupName", grouprs
											.getString("groupName"));

						}
					}
					list.add(map);
				}
			}

			json.put("root", list);
			if (resultCount != null && resultCount.size() > 0) {
				json.put("total", resultCount.size());
			} else {
				json.put("total", 0);
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw new RuntimeException("查询出错", e);
		} finally {
			// 执行关闭操作
			try {
				if (rsanswer != null) {
					rsanswer.close();
				}
				if (rscontent != null) {
					rscontent.close();
				}
				if (rsdoctor != null) {
					rsdoctor.close();
				}
				if (grouprs != null) {
					grouprs.close();
				}
				if(ps != null){
					ps.close();
				}
				DatabaseUtil.closeResource(conn, sm, rs);
				DatabaseUtil.closeResource(session);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	/***************************************************************************
	 * 
	 * 会员监督统计
	 */
	public JSONObject superviseMember(Integer start, Integer limit)
			throws Exception {
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		long count = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet _rs = null;
		try {
			conn = DatabaseUtil.getJdbcConnection();
			conn.setAutoCommit(false);
			String createTempTable = "CREATE TABLE ##superviseMember ("
					+ "[id] [int] IDENTITY (1, 1) NOT NULL ,"
					+ "[year] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
					+ "[month] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
					+ "[dept] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
					+ "[newMember] [int] NULL )";
			String insertTempTable = "insert into ##superviseMember select year(inDate) as year, month(inDate) as month, deptName as dept, count(*) as newMember from MemberInfo group by year(inDate), month(inDate), deptName order by year(inDate) desc, month(inDate) desc, deptName";

			conn.prepareStatement(createTempTable).executeUpdate();
			conn.prepareStatement(insertTempTable).executeUpdate();
			String sqlforCount = "select count(*) as count from ##superviseMember";
			ps = conn.prepareStatement(sqlforCount);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getLong("count");
			}

			String sql = "select top " + limit
					+ " * from ##superviseMember where id not "
					+ "in (select top " + start
					+ " id from ##superviseMember order by id) order by id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberSupervision ms = new MemberSupervision();
				ms.setYear(rs.getString("year"));
				ms.setMonth(rs.getString("month"));
				ms.setDept(rs.getString("dept"));

				String year_month = "";
				if (rs.getString("month") != ""
						&& rs.getString("month").length() == 1) {
					year_month = rs.getString("year") + "0"
							+ rs.getString("month");
				} else {
					year_month = rs.getString("year") + rs.getString("month");
				}

				// 查房次数
				sql = "select count(*) as webWardCount from t_DoctorRoundsRecord wr join MemberInfo m on wr.patientId = m.patient and m.deptName like '"
						+ rs.getString("dept")
						+ "' and left(CONVERT(varchar(100), m.inDate, 112), 6) = '"
						+ year_month + "'";
				ps = conn.prepareStatement(sql);
			  _rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setWebWardCount(_rs.getLong("webWardCount"));
				}
				//新增会员数
				sql= "select count(deptName) as newMember from MemberInfo where left(CONVERT(varchar(100), inDate, 112), 6) = '"
						+ year_month + "' and deptName like '"+ rs.getString("dept") +"' ";
				ps=conn.prepareStatement(sql);
				_rs=ps.executeQuery();
				if(_rs.next()){
					ms.setNewMember(_rs.getLong("newMember"));
				}
				// 累计会员数

				sql = "select count(*) as totalMember from MemberInfo where left(CONVERT(varchar(100), inDate, 112), 6) <= '"
						+ year_month
						+ "' and deptName like '"
						+ rs.getString("dept") + "'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setTotalMember(_rs.getLong("totalMember"));
				}
				// 当前会员数
				sql = "select (select count(deptName) as totalMember from MemberInfo where left(CONVERT(varchar(100), inDate, 112), 6) <= '"
						+ year_month
						+ "' and deptName like '"
						+ rs.getString("dept") + "')-(select count(deptName) as curMember from MemberInfo where deptName like '"
						+ rs.getString("dept")
						+ "' and left(CONVERT(varchar(100), inDate, 112), 6) = '"
						+ year_month + "') as 'curMember'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setCurMember(_rs.getLong("curMember"));
				}
				// 咨询次数
				sql = "select count(*) as consultCount from t_PatientConsulting mg join MemberInfo m on mg.patientId = m.patient and m.deptName like '"
						+ rs.getString("dept")
						+ "' and left(CONVERT(varchar(100), m.inDate, 112), 6) = '"
						+ year_month + "'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setConsultCount(_rs.getLong("consultCount"));
				}
				// 医生回复次数
				sql = "select count(*) as docReplyCount from t_PatientConsulting tp inner join MemberInfo m on tp.patientId=m.patient and m.deptName like '"
					+ rs.getString("dept") +"'and left(CONVERT(varchar(100), m.inDate, 112), 6) = '"
					+ year_month + "'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setDocReplyCount(_rs.getLong("docReplyCount"));
				}
				JSONObject _object = JSONObject.fromObject(ms);
				array.add(_object);
			}
			MemberSupervision ms = superviseAllMember(conn);
			array.add(JSONObject.fromObject(ms));
        
			object.put("root", array);
			object.put("total", count);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			try{
				if (_rs != null) {
					_rs.close();
				}
				if(ps != null){
					ps.close();
				}
				DatabaseUtil.closeResource(conn, null, rs);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return object;
	}

	public static MemberSupervision superviseAllMember(Connection conn)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "";
		MemberSupervision ms = new MemberSupervision();
		ms.setDept("全院统计");
		try {
			// 年份
			sql = "select min(CONVERT(varchar(100), inDate, 111)) as startYear, max(CONVERT(varchar(100), inDate, 111)) as endYear from MemberInfo";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ms.setYear(rs.getString("startYear") + "至"
						+ rs.getString("endYear"));
			}
			// 月份
			ms.setMonth("1至12");

			// 新增会员数
			java.text.DateFormat df = new SimpleDateFormat("yyyyMM");
			Date date = new Date();
			sql = "select count(deptName) as newMember from MemberInfo ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ms.setNewMember(rs.getLong("newMember"));
			}

			// 累计会员数
			sql = "select count(deptName) as totalMember from MemberInfo";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ms.setTotalMember(rs.getLong("totalMember"));
			}

			// 当前会员数
			sql = "select count(deptName) as curMember from MemberInfo ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ms.setCurMember(rs.getLong("curMember"));
			}

			// 查房次数
			sql = "select count(*) as webWardCount from t_DoctorRoundsRecord wr inner join MemberInfo m on wr.patientId = m.patient ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ms.setWebWardCount(rs.getLong("webWardCount"));
			}
			// 咨询次数
			sql = "select count(*) as consultCount from t_PatientConsulting mg inner join MemberInfo m on mg.patientId = m.patient ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ms.setConsultCount(rs.getLong("consultCount"));
			}
			// 医生回复次数
			sql = "select count(*) as docReplyCount from t_PatientConsulting mg inner join MemberInfo m on mg.patientId = m.patient ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ms.setDocReplyCount(rs.getLong("docReplyCount"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null){
				ps.close();
			}
			if(rs != null){
				rs.close();
			}
			
		}
		return ms;
	}

	
	public static MemberSupervision superviseAllMembertotal(Connection conn)
	throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsanswer = null;
		ResultSet rscontent = null;
		String sql = "";
		MemberSupervision ms = new MemberSupervision();
		ms.setDept("全院统计");
		try {
			// 年份
			sql = "select min(CONVERT(varchar(100), inDate, 111)) as startYear, max(CONVERT(varchar(100), inDate, 111)) as endYear from MemberInfo";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ms.setYear(rs.getString("startYear") + "至"
						+ rs.getString("endYear"));
			}
			// 月份
			ms.setMonth("1至12");
		
			// 新增会员数
			java.text.DateFormat df = new SimpleDateFormat("yyyyMM");
			Date date = new Date();
			sql = "select count(deptName) as newMember from MemberInfo ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ms.setNewMember(rs.getLong("newMember"));
			}
		
			// 累计会员数
			sql = "select count(deptName) as totalMember from MemberInfo";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ms.setTotalMember(rs.getLong("totalMember"));
			}
		
			// 当前会员数
			sql = "select count(deptName) as curMember from MemberInfo ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				ms.setCurMember(rs.getLong("curMember"));
			}
			String outCountsql="select outCount from t_OutOrMergencyCase ";
			ps=conn.prepareStatement(outCountsql);
			rsanswer=ps.executeQuery();
			if(rsanswer.next()){
				ms.setOutCount(rsanswer.getLong("outCount"));//门诊总次数
				}
			String outFollowsql="select outFollowTimes from t_OutOrMergencyCase ";
			ps=conn.prepareStatement(outFollowsql);
			rscontent=ps.executeQuery();
			if(rscontent.next()){
				   ms.setOutCount(rscontent.getLong("outFollowTimes"));//随访门诊总次数
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null){
				ps.close();
			}
			if(rs != null){
				rs.close();
			}
			if(rsanswer != null){
				rsanswer.close();
			}
			if(rscontent != null){
				rscontent.close();
			}
		}
		return ms;
		}
	/**
	 * 会员明细表导出前的查询
	 */

	@SuppressWarnings("unchecked")
	public List<MemberSearchTotal> MemberByCondition() throws Exception {
		List<MemberSearchTotal> list = new ArrayList<MemberSearchTotal>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsanswer = null;
		ResultSet allrs = null;
		ResultSet rscontent = null;
		ResultSet rsdoctor = null;
		ResultSet grouprs = null;
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select distinct p.patientName as patientName,p.patientNo as patientNo,p.id as id,m.inDate as inDate, m.inWard as inWard"
					+ ", m.deptName as deptName,m.currentGroup as currentGroup, m.inDoctor as inDoctor,m.memberNo as memberNo "
					+ "from MemberInfo m,t_Patient p, t_InHsp_CaseMaster tc where m.patient = p.id and p.id = tc.patientId order by m.inDate desc ";
			ps = conn.prepareStatement(sql);
			allrs = ps.executeQuery();
			while (allrs.next()) {
				MemberSearchTotal mst = new MemberSearchTotal();
				mst.setPatientName(allrs.getString("patientName"));
				mst.setPatientNo(allrs.getString("patientNo"));
				mst.setId(allrs.getLong("id"));
				mst.setInDate(allrs.getDate("inDate"));
				mst.setInWard(allrs.getString("inward"));
				mst.setDeptName(allrs.getString("deptName"));
				mst.setCurrentGroup(allrs.getString("currentGroup"));
				mst.setInDoctor(allrs.getString("inDoctor"));
				mst.setMemberNo(allrs.getLong("memberNo"));
				String doctorsql = "select doctor from MemberMsg where memberNo='"
						+ allrs.getLong("memberNo") + "'";
				ps = conn.prepareStatement(doctorsql);
				rsdoctor = ps.executeQuery();
				if (rsdoctor.next()) {
					mst.setDoctor(rsdoctor.getString("doctor"));
				}
				String asksql = "select count(*) as accessCount from t_PatientConsulting where patientId='"
				       + allrs.getLong("id") + "'";
				ps = conn.prepareStatement(asksql);
				rs = ps.executeQuery();
				if (rs.next()) {// 一个结果集用if判断，不要用while
					mst.setAccessCount(rs.getLong("accessCount"));

				}
				String answersql = "select count(*) as answerberCount from t_PatientConsulting tp inner join t_DoctorReplyRecordAndPatientQuestions td on " +
                    		" tp.id=td.pcid and td.drAndpqFlag =0 and tp.patientId='"+ allrs.getLong("id") + "'";
				ps = conn.prepareStatement(answersql);
				rsanswer = ps.executeQuery();
				if (rsanswer.next()) {// 一个结果集用if判断，不要用while
					mst.setAnswerberCount(rsanswer.getLong("answerberCount"));
				}
				String contentsql = "select count(*) as contentberCount from t_DoctorRoundsRecord where patientId='"
						+ allrs.getLong("id") + "'";
				ps = conn.prepareStatement(contentsql);
				rscontent = ps.executeQuery();
				if (rscontent.next()) {// 一个结果集用if判断，不要用while
					mst
							.setContentberCount(rscontent
									.getLong("contentberCount"));
				}
				String groupsql = "select groupName from OutpatientGenerator where patientId='"
						+ allrs.getLong("id") + "'";
				ps = conn.prepareStatement(groupsql);
				grouprs = ps.executeQuery();
				if (grouprs.next()) {
					mst.setGroupName(grouprs.getString("groupName"));
				}
				list.add(mst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 执行关闭操作
			try {
				if (rsanswer != null) {
					rsanswer.close();
				}
				if (rscontent != null) {
					rscontent.close();
				}
				if (rsdoctor != null) {
					rsdoctor.close();
				}
				if (grouprs != null) {
					grouprs.close();
				}
				if(ps != null){
					ps.close();
				}
				if(allrs != null){
					allrs.close();
				}
				DatabaseUtil.closeResource(conn, null, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public void exportSearchDataToExcel(OutputStream out) throws Exception {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			short currentColIndex = 0;
			// 表头公共部分
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "会员名称");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "病案号");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "患者编号");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "入会时间");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "入会科室");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "当前科室");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "入会分组");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "当前分组");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "咨询次数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "医生回复次数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "网上查房数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "入会医生");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "随访医生");
			currentColIndex++;
			List<MemberSearchTotal> list = MemberByCondition();
			for (int i = 0; i < list.size(); i++) {
				MemberSearchTotal mst = list.get(i);
				currentColIndex = 0;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getPatientName());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getPatientNo());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getId() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getInDate() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getInWard() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getDeptName() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getGroupName() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getCurrentGroup() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getAccessCount() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getAnswerberCount() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getContentberCount() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getInDoctor() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getDoctor() + "");
			}
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 
	 * 
	 * 会员统计导出前的查询
	 */

	public List<MemberSupervision> superviseAllMember() throws Exception {
		List<MemberSupervision> list = new ArrayList<MemberSupervision>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet _rs = null;
		try {
			conn = DatabaseUtil.getJdbcConnection();
			conn.setAutoCommit(false);
			String sql = "select year(inDate) as year, month(inDate) as month, deptName as dept, count(*) as newMember from MemberInfo group by year(inDate), month(inDate), deptName order by year(inDate) desc, month(inDate) desc, deptName";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberSupervision ms = new MemberSupervision();
				ms.setYear(rs.getString("year"));
				ms.setMonth(rs.getString("month"));
				ms.setDept(rs.getString("dept"));

				String year_month = "";
				if (rs.getString("month") != ""
						&& rs.getString("month").length() == 1) {
					year_month = rs.getString("year") + "0"
							+ rs.getString("month");
				} else {
					year_month = rs.getString("year") + rs.getString("month");
				}

				// 查房次数
				sql = "select count(*) as webWardCount from t_DoctorRoundsRecord wr join MemberInfo m on wr.patientId = m.patient and m.deptName like '"
						+ rs.getString("dept")
						+ "' and left(CONVERT(varchar(100), m.inDate, 112), 6) = '"
						+ year_month + "'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setWebWardCount(_rs.getLong("webWardCount"));
				}
				//新增会员数
				sql= "select count(deptName) as newMember from MemberInfo where left(CONVERT(varchar(100), inDate, 112), 6) = '"
						+ year_month + "' and deptName like '"+ rs.getString("dept") +"' ";
				ps=conn.prepareStatement(sql);
				_rs=ps.executeQuery();
				if(_rs.next()){
					ms.setNewMember(_rs.getLong("newMember"));
				}
				// 累计会员数

				sql = "select count(*) as totalMember from MemberInfo where left(CONVERT(varchar(100), inDate, 112), 6) <= '"
						+ year_month
						+ "' and deptName like '"
						+ rs.getString("dept") + "'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setTotalMember(_rs.getLong("totalMember"));
				}
				// 当前会员数
				sql = "select (select count(*) as totalMember from MemberInfo where left(CONVERT(varchar(100), inDate, 112), 6) <= '"
						+ year_month
						+ "' and deptName like '"
						+ rs.getString("dept") + "')-(select count(*) as curMember from MemberInfo where deptName like '"
						+ rs.getString("dept")
						+ "' and left(CONVERT(varchar(100), inDate, 112), 6) = '"
						+ year_month + "') as 'curMember'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setCurMember(_rs.getLong("curMember"));
				}
				// 咨询次数
				sql = "select count(*) as consultCount from t_PatientConsulting mg join MemberInfo m on mg.patientId = m.patient and m.deptName like '"
						+ rs.getString("dept")
						+ "' and left(CONVERT(varchar(100), m.inDate, 112), 6) = '"
						+ year_month + "'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setDocReplyCount(_rs.getLong("consultCount"));
					
				}
				// 医生回复次数
				sql = "select count(*) as docReplyCount from t_PatientConsulting tp inner join MemberInfo m on tp.patientId=m.patient and m.deptName like '"
					+ rs.getString("dept") +"'and left(CONVERT(varchar(100), m.inDate, 112), 6) = '"
					+ year_month + "'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setDocReplyCount(_rs.getLong("docReplyCount"));
				}
				list.add(ms);
			}
			MemberSupervision mbs = superviseAllMember(conn);
			list.add(mbs);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			try{
				if (_rs != null) {
					_rs.close();
				}
				if(ps !=null){
					ps.close();
				}
				DatabaseUtil.closeResource(conn, null, rs);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;
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

//	public static void main(String[] args) {
//		try {
//			MemberSearchTotalImpl ms = new MemberSearchTotalImpl();
//			ms.MemberInByCondition();
////			ms.exportSearchInDataToExcel();
//			// ms.searchMemberByCondition(0, 901);
////			ms.exportSearchDataToExcel("D:\\");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	/**
	 * 会员统计导出
	 */
	public void exportDataToExcel(OutputStream out) throws Exception {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			short currentColIndex = 0;
			// 表头公共部分
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "年度");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "月份");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "科室名称");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "新增会员数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "累计入会数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "当前会员数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "咨询次数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "网上查房数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "医生回复次数");

			List<MemberSupervision> list = superviseAllMember();
			for (int i = 0; i < list.size(); i++) {
				MemberSupervision ms = list.get(i);
				currentColIndex = 0;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getYear());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getMonth());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getDept());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getNewMember() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getTotalMember() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getCurMember() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getConsultCount() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getWebWardCount() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getDocReplyCount() + "");
			}
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			throw e;
		}
	}
	//liugang内网全院会员明细表fffffffff
	@SuppressWarnings("unchecked")
	public JSONObject searchInMemberByCondition(int start, int limit, String search)
			throws Exception {
		JSONObject json = new JSONObject();
		List<Map> list=new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsanswer = null;
		ResultSet rscontent = null;
		Session session = null;
		Transaction tran = null;
		try{
			session=DatabaseUtil.getSession();
			tran = session.beginTransaction();
			conn=DatabaseUtil.getConnection();
			String sql="select distinct tp.patientName,tp.patientNo,tp.id,mb.inDate,mb.inWard,mb.currentGroup,mb.inDoctor,mb.memberNo,mb.deptName,mb.inDoctor "+
                   " from Patient tp,MemberInfo mb,InHspCaseMaster tc where tp.id=mb.patient.id and tc.patientId=tp.id ";
			if(search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				if(obj.containsKey("patientName")) {
					if (obj.getString("patientName").length() > 0) {
				    	sql += "and tp.patientName like '%" + obj.getString("patientName") + "%' ";
					}
				}
				if(obj.containsKey("inWard")) {
					if(obj.getString("inWard").length() > 0) {
				    	sql += "and mb.inWard like '%" + obj.getString("inWard") + "%' ";
					}
				}
				if(obj.containsKey("inDate")) {
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
							sql += " and mb.inDate between '"
									+ d[0].toLocaleString()
									+ "' and '"
									+ d[1].toLocaleString() + "'";
						}
					}
				}
			}
			sql += " order by mb.inDate desc";
			List resultCount = session.createQuery(sql).list();
			List result = session.createQuery(sql).setFirstResult(start).setMaxResults(limit).list();
			if(result != null && result.size() > 0){
				for(int i=0,size=result.size();i<size;i++){
					Map map = new HashMap();
					Object[] objs = (Object[])result.get(i);
					map.put("patientName",objs[0]);//自己写
					map.put("patientNo",objs[1]);
					map.put("id",objs[2]);
					map.put("inDate",objs[3]);
					map.put("inWard",objs[4]);
					map.put("currentGroup", objs[5]);
					map.put("inDoctor", objs[6]);
					map.put("memberNo", objs[7]);
					map.put("deptName", objs[8]);
					map.put("inDoctor",objs[9]);//入会医生
					String inHspTimesSql="select inHspTimes from t_InHsp_CaseMaster where patientId='"+objs[2]+"' order by inHspTimes desc";
					ps=conn.prepareStatement(inHspTimesSql);
					rs=ps.executeQuery();
					if(rs.next()){//一个结果集用if判断，不要用while
						if(rs.getInt(1)>0){
							map.put("inHspTimes",rs.getInt(1));//住院次数
						}else{
							map.put("inHspTimes", 0);
						}
					}
					String outCountsql="select outCount from t_OutOrMergencyCase where patientId='"+objs[2]+"'";
					ps=conn.prepareStatement(outCountsql);
					rsanswer=ps.executeQuery();
					if(rsanswer.next()){
						if(rsanswer.getInt(1)>0){
							map.put("outCount", rsanswer.getInt(1));
						}else{
							map.put("outCount", 0);//门诊总次数
						}
					}else{
						map.put("outCount", 0);
					}
					String outFollowsql="select outFollowTimes from t_OutOrMergencyCase where patientId='"+objs[2]+"'";
					ps=conn.prepareStatement(outFollowsql);
					rscontent=ps.executeQuery();
					if(rscontent.next()){
						if(rscontent.getInt(1)>0){
							map.put("outFollowTimes", rscontent.getInt(1));
						}else{
							map.put("outFollowTimes", 0);//随访门诊总次数
						}
					}else{
						map.put("outFollowTimes", 0);
					}
					list.add(map);
				}
			}
			json.put("root", list);
			if(resultCount != null && resultCount.size() > 0){
				json.put("total", resultCount.size());
			}else{
				json.put("total", 0);
			}
			tran.commit();
		}catch(Exception e){
			if(tran != null){
				tran.rollback();
			}
			throw new RuntimeException("查询出错",e);
		}finally{
			//执行关闭操作
			try {
					if(rsanswer != null){
					  rsanswer.close();
					}
					if(rscontent != null){
						rscontent.close();
					}
					if(ps != null){
						ps.close();
					}
					DatabaseUtil.closeResource(conn, null, rs);
					DatabaseUtil.closeResource(session);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
		return json;
	}

	
	/***************************************************************************
	 * 
	 * liugang内网全院会员明细表
	 */
	public JSONObject superviseInMember(Integer start, Integer limit)
	throws Exception {
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		long count = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsanswer = null;
		ResultSet rscontent = null;
		ResultSet _rs = null;
		try {
			conn = DatabaseUtil.getJdbcConnection();
			conn.setAutoCommit(false);
			String createTempTable = "CREATE TABLE ##superviseMember ("
					+ "[id] [int] IDENTITY (1, 1) NOT NULL ,"
					+ "[year] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
					+ "[month] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
					+ "[dept] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,"
					+ "[newMember] [int] NULL )";
			String insertTempTable = "insert into ##superviseMember select year(inDate) as year, month(inDate) as month, deptName as dept, count(*) as newMember from MemberInfo group by year(inDate), month(inDate), deptName order by year(inDate) desc, month(inDate) desc, deptName";

			conn.prepareStatement(createTempTable).executeUpdate();
			conn.prepareStatement(insertTempTable).executeUpdate();
			String sqlforCount = "select count(*) as count from ##superviseMember";
			ps = conn.prepareStatement(sqlforCount);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getLong("count");
			}

			String sql = "select top " + limit
					+ " * from ##superviseMember where id not "
					+ "in (select top " + start
					+ " id from ##superviseMember order by id) order by id";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberSupervision ms = new MemberSupervision();
				ms.setYear(rs.getString("year"));
				ms.setMonth(rs.getString("month"));
				ms.setDept(rs.getString("dept"));

				String year_month = "";
				if (rs.getString("month") != ""
						&& rs.getString("month").length() == 1) {
					year_month = rs.getString("year") + "0"
							+ rs.getString("month");
				} else {
					year_month = rs.getString("year") + rs.getString("month");
				}
//				String deptSql = "select deptCode from sys_his_DepartmentHISEntity deptName = '"+rs.getString("dept")+"'";
//				ps = conn.prepareStatement(deptSql);
//				deptRs = ps.executeQuery();
//				if(deptRs.next()){
//					String inHspCountSql = "select count(*) from MemberInfo mi,t_InHsp_CaseMaster tic where year(mi.inDate)="+rs.getString("year")+" and month(mi.inDate)="+rs.getString("month")+" and year(tic.inHspDate)="+rs.getString("year")+" and month(tic.inHspDate)="+rs.getString("month")+" and " +
//							"tic.patientId=mi.patient and mi.deptName='"+rs.getString("dept")+"' and tic.currentWordCode='"+deptRs.getString("deptCode")+"'";
//					ps = conn.prepareStatement(inHspCountSql);
//					inHspRs = ps.executeQuery();
//					if(inHspRs.next()){
//						object.put("inHspCount", inHspRs.getString(1));//住院次数
//					}else{
//						object.put("inHspCount", 0);//住院次数
//					}
//				}else{
//					object.put("inHspCount", 0);//住院次数
//				}
				//新增会员数
				sql= "select count(deptName) as newMember from MemberInfo where left(CONVERT(varchar(100), inDate, 112), 6) = '"
						+ year_month + "' and deptName like '"+ rs.getString("dept") +"' ";
				ps=conn.prepareStatement(sql);
				 _rs = ps.executeQuery();
				_rs=ps.executeQuery();
				if(_rs.next()){
					ms.setNewMember(_rs.getLong("newMember"));
				}
				// 累计会员数

				sql = "select count(*) as totalMember from MemberInfo where left(CONVERT(varchar(100), inDate, 112), 6) <= '"
						+ year_month
						+ "' and deptName like '"
						+ rs.getString("dept") + "'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setTotalMember(_rs.getLong("totalMember"));
				}
				String outCountsql="select outCount from t_OutOrMergencyCase tm join MemberInfo m on tm.patientId=m.patient and m.deptName like '"
						+ rs.getString("dept")
						+ "' and left(CONVERT(varchar(100), m.inDate, 112), 6) = '"
						+ year_month + "'";
				ps=conn.prepareStatement(outCountsql);
				rsanswer=ps.executeQuery();
				if(rsanswer.next()){
						object.put("outCount", rsanswer.getString(1));
					}else{
						object.put("outCount", 0);//门诊总次数
					}
				String outFollowsql="select outFollowTimes from t_OutOrMergencyCase tc join MemberInfo m on tc.patientId=m.patient and m.deptName like '"
						+ rs.getString("dept")
						+ "' and left(CONVERT(varchar(100), m.inDate, 112), 6) = '"
						+ year_month + "'";
				ps=conn.prepareStatement(outFollowsql);
				rscontent=ps.executeQuery();
				if(rscontent.next()){
					   object.put("outFollowTimes", rsanswer.getString(1));
					}else{
						object.put("outFollowTimes", 0);//随访门诊总次数
					}
				JSONObject _object = JSONObject.fromObject(ms);
				array.add(_object);
			}
			
			MemberSupervision ms = superviseAllMembertotal(conn);
			array.add(JSONObject.fromObject(ms));

			object.put("root", array);
			object.put("total", count);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			try{
				if (_rs != null) {
					_rs.close();
				}
				if(rsanswer != null){
					rsanswer.close();
				}
				if(rscontent!= null){
					rscontent.close();
				}
				if(ps != null){
					ps.close();
				}
				DatabaseUtil.closeResource(conn, null, rs);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return object;
	}
	
	/**
	 * 会员明细表导出前的查询(内网)
	 */

	@SuppressWarnings("unchecked")
	public List<MemberSearchTotal> MemberInByCondition() throws Exception {
		List<MemberSearchTotal> list = new ArrayList<MemberSearchTotal>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet allrs = null;
		ResultSet rsanswer = null;
		ResultSet rscontent = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "select p.patientName as patientName,p.patientNo as patientNo,p.id as id,m.inDate as inDate, m.inWard as inWard ,m.deptName as deptName "
					+ ", m.currentWard as currentWard,m.currentGroup as currentGroup, m.inDoctor as inDoctor,m.memberNo as memberNo ,m.inDoctor as inDoctor "
					+ "from MemberInfo m,t_Patient p where m.patient = p.id ";
			ps = conn.prepareStatement(sql);
			allrs = ps.executeQuery();
			while (allrs.next()) {
				MemberSearchTotal mst = new MemberSearchTotal();
				mst.setPatientName(allrs.getString("patientName"));
				mst.setPatientNo(allrs.getString("patientNo"));
				mst.setId(allrs.getLong("id"));
				mst.setInDate(allrs.getDate("inDate"));
				mst.setInWard(allrs.getString("inward"));
				mst.setDeptName(allrs.getString("deptName"));
				mst.setCurrentGroup(allrs.getString("currentGroup"));
				mst.setInDoctor(allrs.getString("inDoctor"));
				mst.setMemberNo(allrs.getLong("memberNo"));
				mst.setInDoctor(allrs.getString("inDoctor"));
				String inHspTimesSql="select inHspTimes from t_InHsp_CaseMaster where patientId='"+allrs.getLong("id")+"' order by inHspTimes desc";
				ps=conn.prepareStatement(inHspTimesSql);
				rs=ps.executeQuery();
				if (rs.next()) {
					mst.setInHspTimes(rs.getLong("inHspTimes"));
				}
				String outCountsql="select outCount from t_OutOrMergencyCase where patientId='"+allrs.getLong("id")+"'";
				ps=conn.prepareStatement(outCountsql);
				rsanswer=ps.executeQuery();
				if(rsanswer.next()){
					mst.setOutCount(rsanswer.getLong("outCount"));
				}
				String outFollowsql="select outFollowTimes from t_OutOrMergencyCase where patientId='"+allrs.getLong("id")+"'";
				ps=conn.prepareStatement(outFollowsql);
				rscontent=ps.executeQuery();
				if(rscontent.next()){
					mst.setOutFollowTimes(rscontent.getLong("outFollowTimes"));
				}
				list.add(mst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 执行关闭操作
			try {
				if (allrs != null) {
					allrs.close();
				}
				if(rsanswer != null){
					rsanswer.close();
				}
				if(rscontent != null){
					rscontent.close();
				}
				if(ps != null){
					ps.close();
				}
				DatabaseUtil.closeResource(conn, null, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}	
	public void exportSearchInDataToExcel(OutputStream out) throws Exception {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			short currentColIndex = 0;
			// 表头公共部分
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "会员名称");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "病案号");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "患者编号");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "入会时间");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "入会科室");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "当前科室");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "当前分组");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "门诊总次数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "随访门诊");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "住院总次数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "随访医生");
			currentColIndex++;
			List<MemberSearchTotal> list = MemberInByCondition();
			for (int i = 0; i < list.size(); i++) {
				MemberSearchTotal mst = list.get(i);
				currentColIndex = 0;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getPatientName());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getPatientNo());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getId() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getInDate() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getInWard() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getDeptName() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getCurrentGroup() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getOutCount() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getOutFollowTimes() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getInHspTimes() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						mst.getInDoctor() + "");
				
			}
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			throw e;
		}
	}
	
	/*
	 * 会员明细表导出前的查询(内网)
	 */
	public List<MemberSupervision> superviseInMember() throws Exception {
		List<MemberSupervision> list = new ArrayList<MemberSupervision>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet _rs = null;
		try {
			conn = DatabaseUtil.getJdbcConnection();
			conn.setAutoCommit(false);
			String sql = "select year(inDate) as year, month(inDate) as month, deptName as dept, count(*) as newMember from MemberInfo group by year(inDate), month(inDate), deptName order by year(inDate) desc, month(inDate) desc, deptName";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				MemberSupervision ms = new MemberSupervision();
				ms.setYear(rs.getString("year"));
				ms.setMonth(rs.getString("month"));
				ms.setDept(rs.getString("dept"));

				String year_month = "";
				if (rs.getString("month") != ""
						&& rs.getString("month").length() == 1) {
					year_month = rs.getString("year") + "0"
							+ rs.getString("month");
				} else {
					year_month = rs.getString("year") + rs.getString("month");
				}

				// 查房次数
				sql = "select count(*) as webWardCount from t_DoctorRoundsRecord wr join MemberInfo m on wr.patientId = m.patient and m.deptName like '"
						+ rs.getString("dept")
						+ "' and left(CONVERT(varchar(100), m.inDate, 112), 6) = '"
						+ year_month + "'";
				 _rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setWebWardCount(_rs.getLong("webWardCount"));
				}
				//新增会员数
				sql= "select count(deptName) as newMember from MemberInfo where left(CONVERT(varchar(100), inDate, 112), 6) = '"
						+ year_month + "' and deptName like '"+ rs.getString("dept") +"' ";
				ps=conn.prepareStatement(sql);
				_rs=ps.executeQuery();
				if(_rs.next()){
					ms.setNewMember(_rs.getLong("newMember"));
				}
				// 累计会员数

				sql = "select count(*) as totalMember from MemberInfo where left(CONVERT(varchar(100), inDate, 112), 6) <= '"
						+ year_month
						+ "' and deptName like '"
						+ rs.getString("dept") + "'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setTotalMember(_rs.getLong("totalMember"));
				}
				// 当前会员数
				sql = "select (select count(*) as totalMember from MemberInfo where left(CONVERT(varchar(100), inDate, 112), 6) <= '"
						+ year_month
						+ "' and deptName like '"
						+ rs.getString("dept") + "')-(select count(*) as curMember from MemberInfo where deptName like '"
						+ rs.getString("dept")
						+ "' and left(CONVERT(varchar(100), inDate, 112), 6) = '"
						+ year_month + "') as 'curMember'";
				ps = conn.prepareStatement(sql);
				_rs = ps.executeQuery();
				if (_rs.next()) {
					ms.setCurMember(_rs.getLong("curMember"));
				}
				String outCountsql="select outCount from t_OutOrMergencyCase tm join MemberInfo m on tm.patientId=m.patient and m.deptName like '"
					+ rs.getString("dept")
					+ "' and left(CONVERT(varchar(100), m.inDate, 112), 6) = '"
					+ year_month + "'";
				ps=conn.prepareStatement(outCountsql);
				_rs=ps.executeQuery();
				if(_rs.next()){
					ms.setOutCount(_rs.getLong("outCount"));
					}
				String outFollowsql="select outFollowTimes from t_OutOrMergencyCase tc join MemberInfo m on tc.patientId=m.patient and m.deptName like '"
						+ rs.getString("dept")
						+ "' and left(CONVERT(varchar(100), m.inDate, 112), 6) = '"
						+ year_month + "'";
				ps=conn.prepareStatement(outFollowsql);
				_rs=ps.executeQuery();
				if(_rs.next()){
					ms.setOutFollowTimes(_rs.getLong("outFollowTimes"));//随访门诊总次数
					}
				list.add(ms);
			}
			MemberSupervision mbs = superviseAllMember(conn);
			list.add(mbs);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (_rs != null) {
				_rs.close();
			}
			if(ps != null){
				ps.close();
			}
			DatabaseUtil.closeResource(conn, null, rs);
		}
		return list;
	}
	public void exportsuperviseInMemberExcel(OutputStream out) throws Exception {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			short currentColIndex = 0;
			// 表头公共部分
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "年度");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "月份");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "科室名称");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "新增会员数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "累计入会数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "门诊总次数");
			currentColIndex++;
			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "随访门诊次数");
			currentColIndex++;
//			exportDataToExcel_setCellValue(sheet, 0, currentColIndex, "住院人数");
			List<MemberSupervision> list = superviseAllMember();
			for (int i = 0; i < list.size(); i++) {
				MemberSupervision ms = list.get(i);
				currentColIndex = 0;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getYear());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getMonth());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getDept());
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getNewMember() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getTotalMember() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getOutCount() + "");
				currentColIndex++;
				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
						ms.getOutFollowTimes() + "");
				currentColIndex++;
//				exportDataToExcel_setCellValue(sheet, i + 1, currentColIndex,
//						ms.getInHspCount() + "");
				
			}
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			throw e;
		}
	}
}
