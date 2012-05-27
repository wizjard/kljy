package com.juncsoft.KLJY.InHospitalCase.CheckReport.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.juncsoft.KLJY.InHospitalCase.CheckReport.dao.CopyDataDao;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportItemVo;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class CopyDataImplforRemoteofJava2{

	private static final int P_N = 500;
	
	public boolean copyReportInfo() {
		int count = 0;
		Connection con_toRemote = null;
		//创建临时表并插入数据(如果存在则先删除)
		String dropTempTable = "drop table ##tempReportForm2";
		String createTempTable = "create table ##tempReportForm2(id int IDENTITY (1, 1) NOT NULL ,ReceiveDate datetime NOT NULL ," +
				"SectionNo int NOT NULL ,TestTypeNo int NOT NULL ,SampleNo varchar (20) COLLATE Chinese_PRC_CI_AS NOT NULL ," +
				"StatusNo int NOT NULL ,SampleTypeNo int NULL ,PatNo varchar (20) COLLATE Chinese_PRC_CI_AS NULL ,CName varchar (40) " +
				"COLLATE Chinese_PRC_CI_AS NULL ,GenderNo int NULL ,Birthday datetime NULL ,Age float NULL ,AgeUnitNo int NULL ," +
				"FolkNo int NULL ,DistrictNo int NULL ,WardNo int NULL ,Bed varchar (10) COLLATE Chinese_PRC_CI_AS NULL ," +
				"DeptNo int NULL ,Doctor varchar (10) COLLATE Chinese_PRC_CI_AS NULL ,ChargeNo int NULL ,Charge float NULL ," +
				"Collecter varchar (10) COLLATE Chinese_PRC_CI_AS NULL ,CollectDate datetime NULL ,CollectTime datetime NULL ," +
				"FormMemo varchar (40) COLLATE Chinese_PRC_CI_AS NULL ,Technician varchar (10) COLLATE Chinese_PRC_CI_AS NULL ," +
				"TestDate datetime NULL ,TestTime datetime NULL ,Operator varchar (10) COLLATE Chinese_PRC_CI_AS NULL ,OperDate " +
				"datetime NULL ,OperTime datetime NULL ,Checker varchar (10) COLLATE Chinese_PRC_CI_AS NULL ,CheckDate datetime NULL ," +
				"CheckTime datetime NULL ,SerialNo varchar (20) COLLATE Chinese_PRC_CI_AS NULL ,RequestSource varchar (20) COLLATE " +
				"Chinese_PRC_CI_AS NULL ,DiagNo int NULL ,PrintTimes int NULL )";
		String insertTempTable = "insert into ##tempReportForm2 select ReceiveDate,SectionNo,TestTypeNo,SampleNo,StatusNo," +
				"SampleTypeNo,PatNo,CName,GenderNo,Birthday,Age,AgeUnitNo,FolkNo,DistrictNo,WardNo,Bed,DeptNo,Doctor,ChargeNo," +
				"Charge,Collecter,CollectDate,CollectTime,FormMemo,Technician,TestDate,TestTime,Operator,OperDate ,OperTime,Checker," +
				"CheckDate,CheckTime,SerialNo,RequestSource,DiagNo,PrintTimes from ReportForm";
		try {
			//创建临时表并插入数据
			con_toRemote = DatabaseUtil.getRemoteConn();
			con_toRemote.prepareStatement(createTempTable).executeUpdate();
			con_toRemote.prepareStatement(insertTempTable).executeUpdate();
			// 1. 查询远程数据库中数据的总数
			count = getCount();
			// 2. 得到应该分成多少次进行处理
			int times = count / P_N + (count % P_N == 0 ? 0 : 1);
			processReportInfo(times);
			con_toRemote.prepareStatement(dropTempTable).executeUpdate();
			System.out.println("数据复制成功");
		} catch (Exception e) {
			throw new RuntimeException("数据复制失败 " + e.getMessage(), e);
		}finally{
			DatabaseUtil.closeConn(con_toRemote);
		}
		return false;
	}

	public boolean copyReportInfoByDate(String dateFrom, String dateEnd) {
		int count = 0;
		Connection con_toRemote = null;
		PreparedStatement ps = null;
		//创建临时表并插入数据(如果存在则先删除)
		String dropTempTable = "drop table ##tempReportForm2";
		String createTempTable = "create table ##tempReportForm2(id int IDENTITY (1, 1) NOT NULL ,ReceiveDate datetime NOT NULL ," +
				"SectionNo int NOT NULL ,TestTypeNo int NOT NULL ,SampleNo varchar (20) COLLATE Chinese_PRC_CI_AS NOT NULL ," +
				"StatusNo int NOT NULL ,SampleTypeNo int NULL ,PatNo varchar (20) COLLATE Chinese_PRC_CI_AS NULL ,CName varchar (40) " +
				"COLLATE Chinese_PRC_CI_AS NULL ,GenderNo int NULL ,Birthday datetime NULL ,Age float NULL ,AgeUnitNo int NULL ," +
				"FolkNo int NULL ,DistrictNo int NULL ,WardNo int NULL ,Bed varchar (10) COLLATE Chinese_PRC_CI_AS NULL ," +
				"DeptNo int NULL ,Doctor varchar (10) COLLATE Chinese_PRC_CI_AS NULL ,ChargeNo int NULL ,Charge float NULL ," +
				"Collecter varchar (10) COLLATE Chinese_PRC_CI_AS NULL ,CollectDate datetime NULL ,CollectTime datetime NULL ," +
				"FormMemo varchar (40) COLLATE Chinese_PRC_CI_AS NULL ,Technician varchar (10) COLLATE Chinese_PRC_CI_AS NULL ," +
				"TestDate datetime NULL ,TestTime datetime NULL ,Operator varchar (10) COLLATE Chinese_PRC_CI_AS NULL ,OperDate " +
				"datetime NULL ,OperTime datetime NULL ,Checker varchar (10) COLLATE Chinese_PRC_CI_AS NULL ,CheckDate datetime NULL ," +
				"CheckTime datetime NULL ,SerialNo varchar (20) COLLATE Chinese_PRC_CI_AS NULL ,RequestSource varchar (20) COLLATE " +
				"Chinese_PRC_CI_AS NULL ,DiagNo int NULL ,PrintTimes int NULL )";
		String insertTempTable = "insert into ##tempReportForm2 select ReceiveDate,SectionNo,TestTypeNo,SampleNo,StatusNo," +
				"SampleTypeNo,PatNo,CName,GenderNo,Birthday,Age,AgeUnitNo,FolkNo,DistrictNo,WardNo,Bed,DeptNo,Doctor,ChargeNo," +
				"Charge,Collecter,CollectDate,CollectTime,FormMemo,Technician,TestDate,TestTime,Operator,OperDate ,OperTime,Checker," +
				"CheckDate,CheckTime,SerialNo,RequestSource,DiagNo,PrintTimes from ReportForm where receiveDate > ? and receiveDate < ?";
		try {
			//创建临时表并插入数据
			con_toRemote = DatabaseUtil.getRemoteConn();
			con_toRemote.prepareStatement(createTempTable).executeUpdate();
			ps = con_toRemote.prepareStatement(insertTempTable);
			ps.setDate(1, java.sql.Date.valueOf(dateFrom));
			ps.setDate(2, java.sql.Date.valueOf(dateEnd));
			ps.executeUpdate();
			// 1. 查询远程数据库中数据的总数
			count = getCount();
			// 2. 得到应该分成多少次进行处理
			int times = count / P_N + (count % P_N == 0 ? 0 : 1);
			processReportInfo(times);
			con_toRemote.prepareStatement(dropTempTable).executeUpdate();
			System.out.println("数据复制成功");
		} catch (Exception e) {
			throw new RuntimeException("数据复制失败 " + e.getMessage(), e);
		}finally{
			if(ps !=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			DatabaseUtil.closeConn(con_toRemote);
		}
		return false;
	}

	private int getCount() throws SQLException {
		int count = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "select count(*) from ##tempReportForm2 ";
		try {
			connection = DatabaseUtil.getRemoteConn();
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			System.out.println("count:" + count);
		} finally {
			DatabaseUtil.closeRs(resultSet);
			DatabaseUtil.closePs(statement);
			DatabaseUtil.closeConn(connection);
		}
		return count;
	}	
	
	private void processReportInfo(int times) throws Exception {
		Connection con_toLocal = null;
		Connection con_toRemote = null;
		PreparedStatement statement_toLocal = null;
		PreparedStatement statement_toRemote = null;
		ResultSet rs_remote = null;
		String sqlForSelect = null;
//		String sqlForSelect = "select top ? * from ReportForm where receivedate not " +
//				"in (select top ? receivedate from ReportForm order by receivedate) order by receivedate";
		
		String sqlForInsert = "insert into ReportFormforRemote(ReceiveDate, SectionNo, TestTypeNo, SampleNo, " +
				"StatusNo, SampleTypeNo, PatNo, CName, GenderNo, Birthday, Age, AgeUnitNo, FolkNo, DistrictNo, " +
				"WardNo, Bed, DeptNo, Doctor, ChargeNo, Charge, Collecter, CollectDate, CollectTime, " +
				"FormMemo, Technician, TestDate, TestTime, Operator, OperDate, OperTime, Checker, CheckDate, " +
				"CheckTime, SerialNo, RequestSource, DiagNo, PrintTimes, hospitalName, isAdd, home_foreign) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String localSqlForSeslect = "select * from ReportFormforRemote where ReceiveDate = ? and SectionNo = ? and TestTypeNo = ? and SampleNo = ?";
		try {
			con_toRemote = DatabaseUtil.getRemoteConn();
			con_toLocal = DatabaseUtil.getLocalConn();
			con_toLocal.setAutoCommit(false);
			statement_toLocal = con_toLocal.prepareStatement(sqlForInsert);
//			statement_toRemote = con_toRemote.prepareStatement(sqlForSelect);			
			for (int i = 0; i < times; i++) {
//				statement_toRemote.setInt(1, P_N);
//				statement_toRemote.setInt(2, i * P_N);
				sqlForSelect = "select top " + P_N + " * from ##tempReportForm2 where id not " +
					"in (select top " + i * P_N + " id from ##tempReportForm2 order by id) order by id";
				statement_toRemote = con_toRemote.prepareStatement(sqlForSelect);
				rs_remote = statement_toRemote.executeQuery();
				while (rs_remote.next()) {
					//System.out.println("ReceiveDate:" + rs_remote.getDate("ReceiveDate") + ",CName:" + rs_remote.getString("CName"));
					statement_toLocal.setDate(1, rs_remote.getDate("ReceiveDate"));
					statement_toLocal.setInt(2, rs_remote.getInt("SectionNo"));
					statement_toLocal.setInt(3, rs_remote.getInt("TestTypeNo"));
					statement_toLocal.setString(4, rs_remote.getString("SampleNo"));
					statement_toLocal.setInt(5, rs_remote.getInt("StatusNo"));
					statement_toLocal.setInt(6, rs_remote.getInt("SampleTypeNo"));
					statement_toLocal.setString(7, rs_remote.getString("PatNo"));
					statement_toLocal.setString(8, rs_remote.getString("CName"));
					statement_toLocal.setInt(9, rs_remote.getInt("GenderNo"));
					statement_toLocal.setDate(10, rs_remote.getDate("Birthday"));
					statement_toLocal.setFloat(11, rs_remote.getFloat("Age"));
					statement_toLocal.setInt(12, rs_remote.getInt("FolkNo"));
					statement_toLocal.setInt(13, rs_remote.getInt("DistrictNo"));
					statement_toLocal.setInt(14, rs_remote.getInt("WardNo"));
					statement_toLocal.setInt(15, rs_remote.getInt("AgeUnitNo"));
					statement_toLocal.setString(16, rs_remote.getString("Bed"));
					statement_toLocal.setInt(17, rs_remote.getInt("DeptNo"));
					statement_toLocal.setString(18, rs_remote.getString("Doctor"));
					statement_toLocal.setInt(19, rs_remote.getInt("ChargeNo"));
					statement_toLocal.setFloat(20, rs_remote.getFloat("Charge"));
					statement_toLocal.setString(21, rs_remote.getString("Collecter"));
					statement_toLocal.setDate(22, rs_remote.getDate("CollectDate"));
					statement_toLocal.setDate(23, rs_remote.getDate("CollectTime"));
					statement_toLocal.setString(24, rs_remote.getString("FormMemo"));
					statement_toLocal.setString(25, rs_remote.getString("Technician"));
					statement_toLocal.setDate(26, rs_remote.getDate("TestDate"));
					statement_toLocal.setDate(27, rs_remote.getDate("TestTime"));
					statement_toLocal.setString(28, rs_remote.getString("Operator"));
					statement_toLocal.setDate(29, rs_remote.getDate("OperDate"));
					statement_toLocal.setDate(30, rs_remote.getDate("OperTime"));
					statement_toLocal.setString(31, rs_remote.getString("Checker"));
					statement_toLocal.setDate(32, rs_remote.getDate("CheckDate"));
					statement_toLocal.setDate(33, rs_remote.getDate("CheckTime"));
					statement_toLocal.setString(34, rs_remote.getString("SerialNo"));
					statement_toLocal.setString(35, rs_remote.getString("RequestSource"));
					statement_toLocal.setInt(36, rs_remote.getInt("DiagNo"));
					statement_toLocal.setInt(37, rs_remote.getInt("PrintTimes"));
					statement_toLocal.setString(38, "首都医科大学附属北京佑安医院");
					statement_toLocal.setInt(39, 0);
					statement_toLocal.setInt(40, -1);
					//查询本地数据库，目的是防止下次拷贝数据时把已经拷贝过的数据重新插入数据库
					PreparedStatement ps = con_toLocal.prepareStatement(localSqlForSeslect);
					ps.setDate(1, rs_remote.getDate("ReceiveDate"));
					ps.setInt(2, rs_remote.getInt("SectionNo"));
					ps.setInt(3, rs_remote.getInt("TestTypeNo"));
					ps.setString(4, rs_remote.getString("SampleNo"));
					if(!ps.executeQuery().next()){
						statement_toLocal.addBatch();						
						//拷贝ReportItem
						copyReportItem(rs_remote.getDate("ReceiveDate"), rs_remote.getInt("SectionNo"), rs_remote.getInt("TestTypeNo"), rs_remote.getString("SampleNo"), con_toLocal, con_toRemote);
					}										
				}
				statement_toLocal.executeBatch();
				con_toLocal.commit();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			con_toLocal.rollback();
			DatabaseUtil.closeRs(rs_remote);
			DatabaseUtil.closePs(statement_toLocal);
			DatabaseUtil.closePs(statement_toRemote);
			DatabaseUtil.closeConn(con_toLocal);
			DatabaseUtil.closeConn(con_toRemote);
		}
	}
	
	//获得从今天算起往前或后推interval的日期
	public static String DateAdd(Date date, int interval) {
		  java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(date);

		  calendar.add(Calendar.DATE, interval);

		  String mm = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		  if (mm.length() == 1) {
		    mm = "0" + mm;
		  }
		  String day = String.valueOf(calendar.get(Calendar.DATE));
		  if (day.length() == 1) {
		    day = "0" + day;
		  }
		  String returnDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-" + mm
		      + "-" + day;
		
		  return returnDate;
		}
	
	public static boolean copyReportItem(java.sql.Date date, int sectionNo, int testTypeNo, String sampleNo, Connection con_toLocal, Connection con_toRemote) throws Exception{
		PreparedStatement statement_toLocal = null;
		PreparedStatement statement_toRemote = null;
		ResultSet rs_remote = null;
		String sqlForSelect = "select * from ReportItem where ReceiveDate = ? and SectionNo = ? and TestTypeNo = ? and SampleNo = ?";
		String sqlForInsert = "insert into ReportItemforRemote(ReceiveDate, SectionNo, TestTypeNo, SampleNo, ParItemNo, ItemNo, OriginalValue, " +
				"ReportValue, OriginalDesc, ReportDesc, StatusNo, RefRange, EquipNo, Modified, ItemDate, ItemTime, IsMatch, " +
				"ResultStatus, HisValue, HisComp, isReceive, SerialNoParent, CountNodesItemSource, Unit, isAdd, home_foreign" +
				") values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try{
			statement_toLocal = con_toLocal.prepareStatement(sqlForInsert);
			statement_toRemote = con_toRemote.prepareStatement(sqlForSelect);	
			statement_toRemote.setDate(1, date);
			statement_toRemote.setInt(2, sectionNo);
			statement_toRemote.setInt(3, testTypeNo);
			statement_toRemote.setString(4, sampleNo);
			rs_remote = statement_toRemote.executeQuery();
			while(rs_remote.next()){//System.out.println("date:" + date);
				statement_toLocal.setDate(1, date);
				statement_toLocal.setInt(2, sectionNo);
				statement_toLocal.setInt(3, testTypeNo);
				statement_toLocal.setString(4, sampleNo);
				statement_toLocal.setInt(5, rs_remote.getInt("ParItemNo"));
				statement_toLocal.setInt(6, rs_remote.getInt("ItemNo"));
				statement_toLocal.setFloat(7, rs_remote.getFloat("OriginalValue"));
				statement_toLocal.setFloat(8, rs_remote.getFloat("ReportValue"));
				statement_toLocal.setString(9, rs_remote.getString("OriginalDesc"));
				statement_toLocal.setString(10, rs_remote.getString("ReportDesc"));
				statement_toLocal.setInt(11, rs_remote.getInt("StatusNo"));
				statement_toLocal.setString(12, rs_remote.getString("RefRange"));
				statement_toLocal.setInt(13, rs_remote.getInt("EquipNo"));
				statement_toLocal.setInt(14, rs_remote.getInt("Modified"));
				statement_toLocal.setDate(15, rs_remote.getDate("ItemDate"));
				statement_toLocal.setDate(16, rs_remote.getDate("ItemTime"));
				statement_toLocal.setInt(17, rs_remote.getInt("IsMatch"));
				statement_toLocal.setString(18, rs_remote.getString("ResultStatus"));
				statement_toLocal.setString(19, rs_remote.getString("HisValue"));
				statement_toLocal.setString(20, rs_remote.getString("HisComp"));
				statement_toLocal.setInt(21, rs_remote.getInt("isReceive"));
				statement_toLocal.setString(22, rs_remote.getString("SerialNoParent"));
				statement_toLocal.setString(23, rs_remote.getString("CountNodesItemSource"));
				statement_toLocal.setString(24, rs_remote.getString("Unit"));
				statement_toLocal.setInt(25, 0);
				statement_toLocal.setInt(26, -1);
				statement_toLocal.addBatch();
				
				//拷贝GroupItem
				copyGroupItem(rs_remote.getInt("ParItemNo"), rs_remote.getInt("ItemNo"), con_toLocal, con_toRemote);
			}
			statement_toLocal.executeBatch();
		}finally{
			DatabaseUtil.closeRs(rs_remote);
			DatabaseUtil.closePs(statement_toLocal);
			DatabaseUtil.closePs(statement_toRemote);
		}
		return false;
	}
	
	public static boolean copyGroupItem(int parItemNo, int itemNo, Connection conn, Connection con_toRemote) throws Exception{
		PreparedStatement ps = null;
		String sqlForSelect = "select * from GroupItemforRemote where PItemNo = ? and ItemNo = ?";
		String sqlForInsert = "insert into GroupItemforRemote(PItemNo, ItemNo) values(?, ?)";
		try{
			ps = conn.prepareStatement(sqlForSelect);
			ps.setInt(1, parItemNo);
			ps.setInt(2, itemNo);
			if(!ps.executeQuery().next()){
				ps = conn.prepareStatement(sqlForInsert);
				ps.setInt(1, parItemNo);
				ps.setInt(2, itemNo);
				ps.executeUpdate();
				
				copyTestItem(parItemNo, itemNo, conn, con_toRemote);
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new Exception("拷贝GroupItem出错");
		}finally{
			DatabaseUtil.closePs(ps);
		}
		return false;
	}
	
	public static boolean copyTestItem(int parItemNo, int itemNo, Connection con_toLocal, Connection con_toRemote) throws Exception{
		PreparedStatement parps = null;
		PreparedStatement ps = null;
		PreparedStatement remotePs = null;
		ResultSet rs = null;
		String sqlRemoteSelect = "select * from TestItem where ItemNo = ?";
		String sqlForSelect = "select * from TestItemforRemote where ItemNo = ?";
		String sqlForInsert = "insert into TestItemforRemote(ItemNo, CName, EName, ShortName, ShortCode, " +
				"DiagMethod, Unit, IsCalc, Visible, DispOrder, Prec, IsProfile, OrderNo, StandardCode, " +
				"ItemDesc) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try{
			parps = con_toLocal.prepareStatement(sqlForSelect);
			parps.setInt(1, parItemNo);
			rs = parps.executeQuery();
			if(!rs.next()){
				parps = con_toLocal.prepareStatement(sqlForInsert);
				remotePs = con_toRemote.prepareStatement(sqlRemoteSelect);
				remotePs.setInt(1, parItemNo);
				rs = remotePs.executeQuery();
				if(rs.next()){
					parps.setInt(1, parItemNo);
					parps.setString(2, rs.getString("CName"));
					parps.setString(3, rs.getString("EName"));
					parps.setString(4, rs.getString("ShortName"));
					parps.setString(5, rs.getString("ShortCode"));
					parps.setString(6, rs.getString("DiagMethod"));
					parps.setString(7, rs.getString("Unit"));
					parps.setInt(8, rs.getInt("IsCalc"));
					parps.setInt(9, rs.getInt("Visible"));
					parps.setInt(10, rs.getInt("DispOrder"));
					parps.setInt(11, rs.getInt("Prec"));
					parps.setInt(12, rs.getInt("IsProfile"));
					parps.setString(13, rs.getString("OrderNo"));
					parps.setString(14, rs.getString("StandardCode"));
					parps.setString(15, rs.getString("ItemDesc"));
					parps.executeUpdate();
				}				
			}
			
			ps = con_toLocal.prepareStatement(sqlForSelect);
			ps.setInt(1, itemNo);
			rs = ps.executeQuery();
			if(!rs.next()){
				ps = con_toLocal.prepareStatement(sqlForInsert);
				remotePs = con_toRemote.prepareStatement(sqlRemoteSelect);
				remotePs.setInt(1, itemNo);
				rs = remotePs.executeQuery();
				if(rs.next()){
					ps.setInt(1, itemNo);
					ps.setString(2, rs.getString("CName"));
					ps.setString(3, rs.getString("EName"));
					ps.setString(4, rs.getString("ShortName"));
					ps.setString(5, rs.getString("ShortCode"));
					ps.setString(6, rs.getString("DiagMethod"));
					ps.setString(7, rs.getString("Unit"));
					ps.setInt(8, rs.getInt("IsCalc"));
					ps.setInt(9, rs.getInt("Visible"));
					ps.setInt(10, rs.getInt("DispOrder"));
					ps.setInt(11, rs.getInt("Prec"));
					ps.setInt(12, rs.getInt("IsProfile"));
					ps.setString(13, rs.getString("OrderNo"));
					ps.setString(14, rs.getString("StandardCode"));
					ps.setString(15, rs.getString("ItemDesc"));
					ps.executeUpdate();
				}			
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new Exception("拷贝TestItem出错");
		}finally{
			DatabaseUtil.closePs(parps);
			DatabaseUtil.closePs(ps);
			DatabaseUtil.closePs(remotePs);
			if(rs != null){
				rs.close();
			}
		}
		return false;
	}
	
	//拷贝函数名称中包含的表
	private static boolean copyDiagnosis_SampleType_Department_PGroup(){
		Connection conn_Local = null;
		Connection conn_Remote = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlForDiagnosisSelect = "select * from Diagnosis";
		String sqlForDiagnosisDelete = "delete from Diagnosis where stemfrom = 1";
		String sqlForDiagnosisInsert = "insert into Diagnosis(DiagNo, CName, DiagDesc, ShortCode, Visible, DispOrder, HisOrderCode, stemfrom) values(?, ?, ?, ?, ?, ?, ?, ?)";
		
		String sqlForSampleTypeSelect = "select * from SampleType";
		String sqlForSampleTypeDelete = "delete from SampleType where stemfrom = 1";
		String sqlForSampleTypeInsert = "insert into SampleType(SampleTypeNo, CName, ShortCode, Visible, DispOrder, HisOrderCode, stemfrom) values(?, ?, ?, ?, ?, ?, ?)";
		
		String sqlForDepartmentSelect = "select * from Department";
		String sqlForDepartmentDelete = "delete from Department where stemfrom = 1";
		String sqlForDepartmentInsert = "insert into Department(DeptNo, CName, ShortName, ShortCode, Visible, DispOrder, HisOrderCode, stemfrom) values(?, ?, ?, ?, ?, ?, ?, ?)";
		
		String sqlForPGroupSelect = "select * from PGroup";
		String sqlForPGroupDelete = "delete from PGroup where stemfrom = 1";
		String sqlForPGroupInsert = "insert into PGroup(sectionNo, superGroupNo, cname, shortName, shortCode, sectionDesc, sectionType, visible, dispOrder, stemfrom) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try{
			conn_Local = DatabaseUtil.getLocalConn();
			conn_Remote = DatabaseUtil.getRemoteConn();
			
			ps = conn_Local.prepareStatement(sqlForDiagnosisDelete);
			ps.executeUpdate();
			ps = conn_Remote.prepareStatement(sqlForDiagnosisSelect);
			rs = ps.executeQuery();
			while(rs.next()){
				ps = conn_Local.prepareStatement(sqlForDiagnosisInsert);
				ps.setInt(1, rs.getInt("DiagNo"));
				ps.setString(2, rs.getString("CName"));
				ps.setString(3, rs.getString("DiagDesc"));
				ps.setString(4, rs.getString("ShortCode"));
				ps.setInt(5, rs.getInt("Visible"));
				ps.setInt(6, rs.getInt("DispOrder"));
				ps.setString(7, rs.getString("HisOrderCode"));
				ps.setInt(8, 1);
				ps.executeUpdate();
			}
			
			ps = conn_Local.prepareStatement(sqlForSampleTypeDelete);
			ps.executeUpdate();
			ps = conn_Remote.prepareStatement(sqlForSampleTypeSelect);
			rs = ps.executeQuery();
			while(rs.next()){
				ps = conn_Local.prepareStatement(sqlForSampleTypeInsert);
				ps.setInt(1, rs.getInt("SampleTypeNo"));
				ps.setString(2, rs.getString("CName"));
				ps.setString(3, rs.getString("ShortCode"));
				ps.setInt(4, rs.getInt("Visible"));
				ps.setInt(5, rs.getInt("DispOrder"));
				ps.setString(6, rs.getString("HisOrderCode"));
				ps.setInt(7, 1);
				ps.executeUpdate();
			}
			
			ps = conn_Local.prepareStatement(sqlForDepartmentDelete);
			ps.executeUpdate();
			ps = conn_Remote.prepareStatement(sqlForDepartmentSelect);
			rs = ps.executeQuery();
			while(rs.next()){			
				ps = conn_Local.prepareStatement(sqlForDepartmentInsert);
				ps.setInt(1, rs.getInt("DeptNo"));
				ps.setString(2, rs.getString("CName"));
				ps.setString(3, rs.getString("ShortName"));
				ps.setString(4, rs.getString("ShortCode"));
				ps.setInt(5, rs.getInt("Visible"));
				ps.setInt(6, rs.getInt("DispOrder"));
				ps.setString(7, rs.getString("HisOrderCode"));
				ps.setInt(8, 1);
				ps.executeUpdate();
			}
			
			ps = conn_Local.prepareStatement(sqlForPGroupDelete);
			ps.executeUpdate();
			ps = conn_Remote.prepareStatement(sqlForPGroupSelect);
			rs = ps.executeQuery();
			while(rs.next()){			
				ps = conn_Local.prepareStatement(sqlForPGroupInsert);
				ps.setInt(1, rs.getInt("sectionNo"));
				ps.setInt(2, rs.getInt("superGroupNo"));
				ps.setString(3, rs.getString("cname"));
				ps.setString(4, rs.getString("shortName"));
				ps.setString(5, rs.getString("shortCode"));
				ps.setString(6, rs.getString("sectionDesc"));
				ps.setInt(7, rs.getInt("sectionType"));
				ps.setInt(8, rs.getInt("visible"));
				ps.setInt(9, rs.getInt("dispOrder"));
				ps.setInt(10, 1);
				ps.executeUpdate();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DatabaseUtil.closePs(ps);
			DatabaseUtil.closeRs(rs);
			DatabaseUtil.closeConn(conn_Local);
			DatabaseUtil.closeConn(conn_Remote);
		}
		return false;
	}
	
	public static void main(String[] args){
		try {
			
			int interval = -2;
			String dateFrom = "2010-11-18";
			String dateEnd = "2010-12-05";						
			
			Date dateF = java.text.DateFormat.getDateInstance().parse(dateFrom);
			Date dateE = java.text.DateFormat.getDateInstance().parse(dateEnd);
			CopyDataImplforRemoteofJava2 cd = new CopyDataImplforRemoteofJava2();
			
			dateF = java.text.DateFormat.getDateInstance().parse(dateFrom);
			dateE = java.text.DateFormat.getDateInstance().parse(dateEnd);
			dateFrom = DateAdd(dateF, interval);
			dateEnd = DateAdd(dateE, interval);
			System.out.println(dateFrom);
			System.out.println(dateEnd);
				
			cd.copyReportInfoByDate(dateFrom, dateEnd);

//			SimpleDateFormat bartDateFormat =   new SimpleDateFormat("yyyy-MM-dd");  
//			java.util.Date date = bartDateFormat.parse("2010-07-22"); 
//			copyReportItem( new java.sql.Date(date.getTime()), 10, 1, "501");
//			copyTestItem(25, 170001);
//			copyGroupItem(25, 170001);			
//			copyDiagnosis_SampleType_Department_PGroup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
