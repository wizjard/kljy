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

public class CopyDataImpl implements CopyDataDao {

	private static final int P_N = 500;
	
	public boolean copyReportInfo() {
		int count = 0;
		Connection con_toRemote = null;
		//������ʱ�?�������(����������ɾ��)
		String dropTempTable = "drop table ##tempReportForm";
		String createTempTable = "create table ##tempReportForm(id int IDENTITY (1, 1) NOT NULL ,ReceiveDate datetime NOT NULL ," +
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
		String insertTempTable = "insert into ##tempReportForm select ReceiveDate,SectionNo,TestTypeNo,SampleNo,StatusNo," +
				"SampleTypeNo,PatNo,CName,GenderNo,Birthday,Age,AgeUnitNo,FolkNo,DistrictNo,WardNo,Bed,DeptNo,Doctor,ChargeNo," +
				"Charge,Collecter,CollectDate,CollectTime,FormMemo,Technician,TestDate,TestTime,Operator,OperDate ,OperTime,Checker," +
				"CheckDate,CheckTime,SerialNo,RequestSource,DiagNo,PrintTimes from ReportForm";
		try {
			//������ʱ�?�������
			con_toRemote = DatabaseUtil.getRemoteConn();
			con_toRemote.prepareStatement(createTempTable).executeUpdate();
			con_toRemote.prepareStatement(insertTempTable).executeUpdate();
			// 1. ��ѯԶ����ݿ�����ݵ�����
			count = getCount();
			// 2. �õ�Ӧ�÷ֳɶ��ٴν��д���
			int times = count / P_N + (count % P_N == 0 ? 0 : 1);
			processReportInfo(times);
			con_toRemote.prepareStatement(dropTempTable).executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("��ݸ���ʧ�� " + e.getMessage(), e);
		}finally{
			DatabaseUtil.closeConn(con_toRemote);
		}
		return false;
	}

	public boolean copyReportInfoByDate(Date date, java.sql.Date dateEnd, int interval) {
		int count = 0;
		String dateFrom = DateAdd(date, interval);
		Connection con_toRemote = null;
		PreparedStatement ps = null;
		//������ʱ�?�������(����������ɾ��)
		String dropTempTable = "drop table ##tempReportForm";
		String createTempTable = "create table ##tempReportForm(id int IDENTITY (1, 1) NOT NULL ,ReceiveDate datetime NOT NULL ," +
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
		String insertTempTable = "insert into ##tempReportForm select ReceiveDate,SectionNo,TestTypeNo,SampleNo,StatusNo," +
				"SampleTypeNo,PatNo,CName,GenderNo,Birthday,Age,AgeUnitNo,FolkNo,DistrictNo,WardNo,Bed,DeptNo,Doctor,ChargeNo," +
				"Charge,Collecter,CollectDate,CollectTime,FormMemo,Technician,TestDate,TestTime,Operator,OperDate ,OperTime,Checker," +
				"CheckDate,CheckTime,SerialNo,RequestSource,DiagNo,PrintTimes from ReportForm where receiveDate > ? and receiveDate < ?";
		try {
			//������ʱ�?�������
			con_toRemote = DatabaseUtil.getRemoteConn();
			con_toRemote.prepareStatement(createTempTable).executeUpdate();
			ps = con_toRemote.prepareStatement(insertTempTable);
			ps.setDate(1, java.sql.Date.valueOf(dateFrom));
			ps.setDate(2, dateEnd);
			ps.executeUpdate();
			// 1. ��ѯԶ����ݿ�����ݵ�����
			count = getCount();
			// 2. �õ�Ӧ�÷ֳɶ��ٴν��д���
			int times = count / P_N + (count % P_N == 0 ? 0 : 1);
			processReportInfo(times);
			con_toRemote.prepareStatement(dropTempTable).executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException("��ݸ���ʧ�� " + e.getMessage(), e);
		}finally{
			if(ps != null){
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
		String sql = "select count(*) from ##tempReportForm ";
		try {
			connection = DatabaseUtil.getRemoteConn();
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
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
		
		String sqlForInsert = "insert into ReportForm(ReceiveDate, SectionNo, TestTypeNo, SampleNo, " +
				"StatusNo, SampleTypeNo, PatNo, CName, GenderNo, Birthday, Age, AgeUnitNo, FolkNo, DistrictNo, " +
				"WardNo, Bed, DeptNo, Doctor, ChargeNo, Charge, Collecter, CollectDate, CollectTime, " +
				"FormMemo, Technician, TestDate, TestTime, Operator, OperDate, OperTime, Checker, CheckDate, " +
				"CheckTime, SerialNo, RequestSource, DiagNo, PrintTimes) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
				"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String localSqlForSeslect = "select * from ReportForm where ReceiveDate = ? and SectionNo = ? and TestTypeNo = ? and SampleNo = ?";
		try {
			con_toRemote = DatabaseUtil.getRemoteConn();
			con_toLocal = DatabaseUtil.getLocalConn();
			con_toLocal.setAutoCommit(false);
			statement_toLocal = con_toLocal.prepareStatement(sqlForInsert);
//			statement_toRemote = con_toRemote.prepareStatement(sqlForSelect);			
			for (int i = 0; i < times; i++) {
//				statement_toRemote.setInt(1, P_N);
//				statement_toRemote.setInt(2, i * P_N);
				sqlForSelect = "select top " + P_N + " * from ##tempReportForm where id not " +
					"in (select top " + i * P_N + " id from ##tempReportForm order by receivedate) order by id";
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
					
					PreparedStatement ps = con_toLocal.prepareStatement(localSqlForSeslect);
					ps.setDate(1, rs_remote.getDate("ReceiveDate"));
					ps.setInt(2, rs_remote.getInt("SectionNo"));
					ps.setInt(3, rs_remote.getInt("TestTypeNo"));
					ps.setString(4, rs_remote.getString("SampleNo"));
					if(!ps.executeQuery().next()){
						statement_toLocal.addBatch();						
						//����ReportItem
						copyReportItem(rs_remote.getDate("ReceiveDate"), rs_remote.getInt("SectionNo"), rs_remote.getInt("TestTypeNo"), rs_remote.getString("SampleNo"));
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
	
	//��ôӽ���������ǰ�����interval������
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
	
	public static boolean copyReportItem(java.sql.Date date, int sectionNo, int testTypeNo, String sampleNo) throws Exception{
		List<ReportItemVo> list = new ArrayList<ReportItemVo>();
		Connection con_toLocal = null;
		Connection con_toRemote = null;
		PreparedStatement statement_toLocal = null;
		PreparedStatement statement_toRemote = null;
		ResultSet rs_remote = null;
		String sqlForSelect = "select * from ReportItem where ReceiveDate = ? and SectionNo = ? and TestTypeNo = ? and SampleNo = ?";
		String sqlForInsert = "insert into ReportItem(ReceiveDate, SectionNo, TestTypeNo, SampleNo, ParItemNo, ItemNo, OriginalValue, " +
				"ReportValue, OriginalDesc, ReportDesc, StatusNo, RefRange, EquipNo, Modified, ItemDate, ItemTime, IsMatch, " +
				"ResultStatus, HisValue, HisComp, isReceive, SerialNoParent, CountNodesItemSource, Unit, PlateNo, PositionNo, " +
				"EquipCommMemo) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try{
			con_toRemote = DatabaseUtil.getRemoteConn();
			con_toLocal = DatabaseUtil.getLocalConn();
			con_toLocal.setAutoCommit(false);
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
				statement_toLocal.setInt(25, rs_remote.getInt("PlateNo"));
				statement_toLocal.setInt(26, rs_remote.getInt("PositionNo"));
				statement_toLocal.setString(27, rs_remote.getString("EquipCommMemo"));				
				statement_toLocal.addBatch();
				
				//����Ӧ�ļ�¼�ֶα��浽list��
				ReportItemVo ri = new ReportItemVo();
				ri.setReceiveDate(date);
				ri.setSectionNo(sectionNo);
				ri.setTestTypeNo(testTypeNo);
				ri.setSampleNo(sampleNo);
				ri.setParItemNo(rs_remote.getInt("ParItemNo"));
				ri.setItemNo(rs_remote.getInt("ItemNo"));
				list.add(ri);
			}
			statement_toLocal.executeBatch();
			
			convertToLocal(list);
			con_toLocal.commit();						
		}finally{
			DatabaseUtil.closeRs(rs_remote);
			DatabaseUtil.closePs(statement_toLocal);
			DatabaseUtil.closePs(statement_toRemote);
			DatabaseUtil.closeConn(con_toLocal);
			DatabaseUtil.closeConn(con_toRemote);
		}
		return false;
	}
	
	public static void convertToLocal(List<ReportItemVo> list) throws Exception{
		
		Connection con_toLocal = null;
		Connection con_toRemote = null;
		PreparedStatement statement_toLocal = null;
		PreparedStatement statement_toRemote = null;
		ResultSet rs_remote = null;
		ResultSet rs_local = null;
		String sqlForRemoteSelect = "select CName from TestItem where itemNo = ?";
		String sqlForLocalSelectTByCName = "select ItemNo from TestItem where CName = ?";
		String sqlForLocalSelectGById = "select * from GroupItem where ItemNo = ?";
		String sqlForLocalSelectTById = "select * from TestItem where ItemNo = ?";
		String sqlForLocalDelete = "delete from ReportItem where ReceiveDate = ? and SectionNo = ? and TestTypeNo = ? " +
				"and SampleNo = ? and ParItemNo = ? and ItemNo = ?";
		String sqlForUpdate = "update ReportItem set originalCName = ?, home_foreign = ?, ParItemNo = ?, ItemNo = ? where ReceiveDate = ? " +
				"and SectionNo = ? and TestTypeNo = ? and SampleNo = ? and ParItemNo = ? and ItemNo = ?";
		try{
			con_toRemote = DatabaseUtil.getRemoteConn();
			con_toLocal = DatabaseUtil.getLocalConn();
			con_toLocal.setAutoCommit(false);
			
			for(ReportItemVo ri : list){
				String originalCName = null;
				int pitemNo;
				int itemNo;				//����������Ҫ������ݿ�
				String cname = null;
				List<Map<Integer, String>> parList = new ArrayList<Map<Integer, String>>();
				
				//��������id��ȡ������ԭʼ���
				statement_toRemote = con_toRemote.prepareStatement(sqlForRemoteSelect);
				statement_toRemote.setInt(1, ri.getParItemNo());
				rs_remote = statement_toRemote.executeQuery();
				if(!rs_remote.next()){
					statement_toLocal = con_toLocal.prepareStatement(sqlForLocalDelete);
					statement_toLocal.setDate(1, ri.getReceiveDate());
					statement_toLocal.setInt(2, ri.getSectionNo());
					statement_toLocal.setInt(3, ri.getTestTypeNo());
					statement_toLocal.setString(4, ri.getSampleNo());
					statement_toLocal.setInt(5, ri.getParItemNo());
					statement_toLocal.setInt(6, ri.getItemNo());
					statement_toLocal.executeUpdate();
					continue;
				}				
				originalCName = rs_remote.getString("CName");
				
				//1���������id��Զ��TestItem������Ӧ��CName
				statement_toRemote = con_toRemote.prepareStatement(sqlForRemoteSelect);
				statement_toRemote.setInt(1, ri.getItemNo());
				rs_remote = statement_toRemote.executeQuery();
				if(!rs_remote.next()){
					statement_toLocal = con_toLocal.prepareStatement(sqlForLocalDelete);
					statement_toLocal.setDate(1, ri.getReceiveDate());
					statement_toLocal.setInt(2, ri.getSectionNo());
					statement_toLocal.setInt(3, ri.getTestTypeNo());
					statement_toLocal.setString(4, ri.getSampleNo());
					statement_toLocal.setInt(5, ri.getParItemNo());
					statement_toLocal.setInt(6, ri.getItemNo());
					statement_toLocal.executeUpdate();
					continue;
				}
				cname = rs_remote.getString("CName");
				//2�����1�е�CName�鱾��TestItem������Ӧ��id
				statement_toLocal = con_toLocal.prepareStatement(sqlForLocalSelectTByCName);
				statement_toLocal.setString(1, cname);
				rs_local = statement_toLocal.executeQuery();
				if(!rs_local.next()) continue;				//ʵ����Ŀ����Ҫɾ����д���
				
//��ʱע������һ�д��룬ʵ����Ŀ�����õ�
//				if(!rs_local.next()) throw new Exception("���ؿⲻ������Ӧ��������ڱ��ؿ��������Ӧ������");
				itemNo = rs_local.getInt("ItemNo");
				//3�����2�е�id�鱾��GroupItem����������id,��ʱ���Ŀ�����һ��ֵ���������²����õ���List��Map
				statement_toLocal = con_toLocal.prepareStatement(sqlForLocalSelectGById);
				statement_toLocal.setInt(1, itemNo);
				rs_local = statement_toLocal.executeQuery();
				//4�� ���3�е������id��ѯ����TestItem�������Ӧ��CName
				while(rs_local.next()){
					PreparedStatement ps = con_toLocal.prepareStatement(sqlForLocalSelectTById);
					ResultSet rs = ps.getResultSet();
					ps.setInt(1, rs_local.getInt("PItemNo"));
					rs = ps.executeQuery();
					if(!rs.next()) continue;
					Map<Integer, String> map = new HashMap<Integer, String>();
					map.put(rs.getInt("ItemNo"), rs.getString("CName"));
					parList.add(map);
				}
				if(parList.isEmpty()) continue;
				pitemNo = convertOriginalCName(parList, originalCName);
//��ʱע������һ�д��룬ʵ����Ŀ�����õ�
//				if(0 == pitemNo) throw new Exception("����������Զ���������ڶ�Ӧ��ϵ�����鱾����ݿ�");
				if(0 == pitemNo) continue;																//ʵ����Ŀ����Ҫɾ����д���
				
				statement_toLocal = con_toLocal.prepareStatement(sqlForUpdate);
				statement_toLocal.setString(1, originalCName);
				if(originalCName.contains("���")){
					statement_toLocal.setString(2, "���");
				}else{
					statement_toLocal.setString(2, "���");
				}
				statement_toLocal.setInt(3, pitemNo);
				statement_toLocal.setInt(4, itemNo);
				statement_toLocal.setDate(5, ri.getReceiveDate());
				statement_toLocal.setInt(6, ri.getSectionNo());
				statement_toLocal.setInt(7, ri.getTestTypeNo());
				statement_toLocal.setString(8, ri.getSampleNo());
				statement_toLocal.setInt(9, ri.getParItemNo());
				statement_toLocal.setInt(10, ri.getItemNo());
				statement_toLocal.executeUpdate();
			}
			con_toLocal.commit();
		}finally{
			DatabaseUtil.closeRs(rs_remote);
			if(rs_local != null){
				rs_local.close();
			}
			DatabaseUtil.closePs(statement_toLocal);
			DatabaseUtil.closePs(statement_toRemote);
			DatabaseUtil.closeConn(con_toLocal);
			DatabaseUtil.closeConn(con_toRemote);
		}
	}
	
	public static int convertOriginalCName(List<Map<Integer, String>> list, String originalCName){
		int pitemNo = 0;
		int num = 0;
		String pcname = null;
		for(Map<Integer, String> map : list){
			int temp_pitemNo = 0;
			int temp_num = 0;
			Map.Entry entry = map.entrySet().iterator().next(); 
			temp_pitemNo = Integer.parseInt(entry.getKey().toString());
			pcname = entry.getValue().toString();
			if(originalCName.contains(pcname)){
				pitemNo = temp_pitemNo;
				break;
			}else{
				for(int i = 1; i < pcname.split("").length; i++){
				//	System.out.println(pcname.split("")[i]);
					if(originalCName.contains(pcname.split("")[i])) temp_num++;
				}
				if(temp_num > num){
					num = temp_num;
					pitemNo = temp_pitemNo;
				}
			}
		}		
		return pitemNo;
	}
	
	public static void main(String[] args){
		try {
//			new CopyDataImpl().copyReportInfo();
//			new CopyDataImplforRemote().copyReportInfoByDate(java.text.DateFormat.getDateInstance().parse("2003-03-11"), java.sql.Date.valueOf("2003-5-26"), -1);
//			SimpleDateFormat bartDateFormat =   new SimpleDateFormat("yyyy-MM-dd");  
//			java.util.Date date = bartDateFormat.parse("2010-07-22"); 
//			copyReportItem( new java.sql.Date(date.getTime()), 10, 1, "501");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean copyReportInfoByDate(Date date, Date dateEnd, int interval) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean copyReportInfoByDate(String date, String dateEnd) {
		// TODO Auto-generated method stub
		return false;
	}
}
