package com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DeathRecord;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.InHspRec24;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.dao.InHospitalDao;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.Address;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.ContinuePage_Ops;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.ContinuePage_Sysptom;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.ContinuePage_Ward;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.InHosptialOne;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.InHosptialTwo;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.OutHspRec;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;

public class InHospitalImpl implements InHospitalDao {
	
/*	public InHosptialOne getInHospOne(Long CaseId) throws Exception {		
		Session session = null;
		Transaction t = null;
		List<InHosptialOne> list = new ArrayList<InHosptialOne>();
		InHosptialOne one = null;
		try{
			if(CaseId !=null && CaseId > 0){
				String SQL = " from InHosptialOne where caseId =? ";
				session = DatabaseUtil.getSession();
				t = session.beginTransaction();
				list = session.createQuery(SQL).setLong(0, CaseId).list();
				if(list.size() > 0){
					one = list.get(0);
				}
				if(one ==null || list.size()==0){
					one = callPre(CaseId,one);
				}
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return one;
	}*/
////////////
	/////////////////////
	/////////////////////////
	//////////////////////////////////
	/////////////////////////////
	////////////////////////////////
	////////////////////////////////////////////////////
	/////////////////////////////
	///////////////////////////////////////////////
	/////////////////////////////////
	//////////////////////////////////////
////////////
	/////////////////////
	/////////////////////////
	//////////////////////////////////
	/////////////////////////////
	////////////////////////////////
	////////////////////////////////////////////////////
	/////////////////////////////
	///////////////////////////////////////////////
	/////////////////////////////////
	//////////////////////////////////////
	public InHosptialOne getInHospOne(Long caseId) throws Exception {		
		Session session = null;
		Transaction t = null;
		List<InHosptialOne> list = new ArrayList<InHosptialOne>();
		InHosptialOne one = null;
		try{
			if(caseId !=null && caseId > 0){
				session = DatabaseUtil.getSession();
				t = session.beginTransaction();
				list = session.createQuery("from InHosptialOne where caseId = ?").setLong(0, caseId).list();
				InHspCaseMaster caseMaster = getInHspCaseMasterRec(caseId);	
				if(list.size() > 0){
					one = list.get(0);
					one.setInHspDate(caseMaster.getInHspDate());
					one.setAge(caseMaster.getAge());
				}else{
					Patient patient = getPatientRec(caseId);
					InHosptialOne _one = new InHosptialOne();
					one = setInHospOne(patient, caseMaster, _one);
				}
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return one;
	}
	
	public InHosptialOne importInHospOne(Long caseId) throws Exception {		
		Session session = null;
		Transaction t = null;
		List<InHosptialOne> list = new ArrayList<InHosptialOne>();
		InHosptialOne one = null;
		try{
			if(caseId !=null && caseId > 0){
				session = DatabaseUtil.getSession();
				t = session.beginTransaction();
				list = session.createQuery("from InHosptialOne where caseId = ?").setLong(0, caseId).list();
				InHspCaseMaster caseMaster = getInHspCaseMasterRec(caseId);	
				if(list.size() > 0){
					one = list.get(0);
					Patient patient = getPatientRec(caseId);
					one = setInHospOne(patient, caseMaster, one);
				}else{
					Patient patient = getPatientRec(caseId);
					InHosptialOne _one = new InHosptialOne();
					one = setInHospOne(patient, caseMaster, _one);
				}
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return one;
	}
	
	public static InHosptialOne setInHospOne(Patient p, InHspCaseMaster c, InHosptialOne one){
		try {
			one.setPatientName(p.getPatientName());
			one.setGender(p.getGender());
			one.setBirthday(p.getBirthday());
			one.setAge(c.getAge());
			String marriageStatus = getMarriageStatus(p.getMarrageStatus());
			if(marriageStatus.contains("未婚")){
				one.setMarrageStatus("1");
			}
			if(marriageStatus.contains("已婚")){
				one.setMarrageStatus("2");
			}
			if(marriageStatus.contains("离婚")){
				one.setMarrageStatus("3");
			}
			if(marriageStatus.contains("丧偶")){
				one.setMarrageStatus("4");
			}
			
			one.setHomeAddr(p.getHomeAddr());
			one.setOccupation(getOccupation(p.getOccupation()));
			one.setPeople(getPepole(p.getPeople()));
			one.setNationality(getNationality(p.getNationality()));
			one.setIdNo(p.getIdNo());
			
			//2011-02-09修改
			one.setWorkUnitAddr(p.getWorkUnit() + p.getWorkUnitAddr());
			
			one.setWorkUnitTel(p.getWorkUnitTel());
			one.setWorkUnitPostCode(p.getWorkUnitPostCode());
			one.setContacterName(p.getContacterName());
			one.setContacterRelationship(getRelationShip(p.getContacterRelationship()));
			one.setContacterTel(p.getContacterTel());
			one.setInHspDate(c.getInHspDate());
			one.setInWardCode(getWardName(c.getInWardCode()));
			one.setOutHspWard(getWardName(c.getOutWardCode()));
			
//出院时间的数据不再从病历主表调数据，而是一次从死亡记录、24小时出院记录和出院记录对应的功能块里调入数据；相应的住院天数的计算也与此有关		
//			one.setOutHspDate(c.getOutDate());
//			if(c.getInHspDate() != null && c.getOutDate() != null){
//				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//				String inHspDateStr = sf.format(c.getInHspDate());
//				String outHspDateStr = sf.format(c.getOutDate());
//				long inHspDayCount = sf.parse(outHspDateStr).getTime() - sf.parse(inHspDateStr).getTime();
//				
//				inHspDayCount = new Double(Math.floor(inHspDayCount/ (1000 * 3600 * 24))).intValue();
//				if(inHspDayCount == 0){
//					inHspDayCount = inHspDayCount + 1;
//				}
//				one.setHspDate2((int)inHspDayCount);
//			}
			one.setInIllsShow(getInHspCheck(c.getInIlls()));
			
			String InHspStatusName = getInHspStatus(c.getInStatus());
			if(InHspStatusName != ""){			
				if(InHspStatusName.contains("一般")){
					one.setInStatus("3");
				}
				if(InHspStatusName.contains("重")){
					one.setInStatus("2");				
				}
				if(InHspStatusName.contains("危")){
					one.setInStatus("1");
				}
			}		
			Date checkdate = getCheckDate(c.getId());
			if(checkdate != null){
				one.setQueding_checkdate(checkdate);
			}
			one.setPatientNo(p.getPatientNo());
			one.setInHspTimes(c.getInHspTimes());
			OutHspRec o = new OutHspRec();
			o = getOutHspRec(c.getId());
			one.setXno(o.getxNo());
			one.setCtNo(o.getCtNo());
			one.setMriNo(o.getMriNo());	
			
			
			DeathRecord deathRecord = new DeathRecord();
			deathRecord = getDeathRec(c.getId());
			InHspRec24 inHspRec24 = new InHspRec24();
			inHspRec24 = getInHspRec24(c.getId());
			if(deathRecord.getDeathDiag() != null && deathRecord.getDeathDiag() != ""){
				one.setOutHspDate(deathRecord.getDeathTime());
				one.setHspDate2(getInHspCountDay(c.getInHspDate(), deathRecord.getDeathTime()));
				one.setOutIlls1(deathRecord.getDeathDiag());
				one.setOutIlls2(deathRecord.getDeathDiag2());
				one.setOutIlls3(deathRecord.getDeathDiag3());
				one.setOutIlls4(deathRecord.getDeathDiag4());
				one.setOutIlls5(deathRecord.getDeathDiag5());
				one.setOutIlls6(deathRecord.getDeathDiag6());
				one.setOutIlls7(deathRecord.getDeathDiag7());
				one.setOutIlls8(deathRecord.getDeathDiag8());
			}else if(inHspRec24.getOutHspDiag() != null && inHspRec24.getOutHspDiag() != ""){
				one.setOutHspDate(inHspRec24.getOuthspTime());
				one.setHspDate2(getInHspCountDay(c.getInHspDate(), inHspRec24.getOuthspTime()));
				one.setOutIlls1(inHspRec24.getOutHspDiag());
				one.setOutIlls2(inHspRec24.getOutHspDiag2());
				one.setOutIlls3(inHspRec24.getOutHspDiag3());
				one.setOutIlls4(inHspRec24.getOutHspDiag4());
				one.setOutIlls5(inHspRec24.getOutHspDiag5());
				one.setOutIlls6(inHspRec24.getOutHspDiag6());
				one.setOutIlls7(inHspRec24.getOutHspDiag7());
				one.setOutIlls8(inHspRec24.getOutHspDiag8());
			}else{
				if(o.getOutIlls1() != null && o.getOutIlls1() != ""){
					one.setOutHspDate(c.getOutDate());
				}
				
				one.setHspDate2(getInHspCountDay(c.getInHspDate(), c.getOutDate()));
				one.setOutIlls1(o.getOutIlls1());
				one.setOutIlls2(o.getOutIlls2());
				one.setOutIlls3(o.getOutIlls3());
				one.setOutIlls4(o.getOutIlls4());
				one.setOutIlls5(o.getOutIlls5());
				one.setOutIlls6(o.getOutIlls6());
				one.setOutIlls7(o.getOutIlls7());
				one.setOutIlls8(o.getOutIlls8());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return one;
	}
	
	public static int getInHspCountDay(Date inHspDate, Date outHspDate){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		long inHspDayCount = 0;
		if(inHspDate != null && outHspDate != null){
			String inHspDateStr = sf.format(inHspDate);
			String outHspDateStr = sf.format(outHspDate);
			try {
				inHspDayCount = sf.parse(outHspDateStr).getTime() - sf.parse(inHspDateStr).getTime();
				inHspDayCount = new Double(Math.floor(inHspDayCount/ (1000 * 3600 * 24))).intValue();
				if(inHspDayCount == 0){
					inHspDayCount = inHspDayCount + 1;
				}
			} catch (ParseException e) {
				throw new RuntimeException("error of calculating inHspCountDay!");
			}
		}
		return (int)inHspDayCount;
	}
	
	public static OutHspRec getOutHspRec(Long caseId) throws Exception {		
		Session session = null;
		Transaction t = null;
		List<OutHspRec> list = new ArrayList<OutHspRec>();
		OutHspRec outHspRec = new OutHspRec();
		try{
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			list = session.createQuery("from OutHspRec where caseId = ?").setLong(0, caseId).list();
			if(list.size() > 0){
				outHspRec = list.get(0);
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return outHspRec;
	}
	
	public static DeathRecord getDeathRec(Long caseId) throws Exception {		
		Session session = null;
		Transaction t = null;
		List<DeathRecord> list = new ArrayList<DeathRecord>();
		DeathRecord deathRecord = new DeathRecord();
		try{
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			list = session.createQuery("from DeathRecord where caseId = ?").setLong(0, caseId).list();
			if(list.size() > 0){
				deathRecord = list.get(0);
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return deathRecord;
	}
	
	public static InHspRec24 getInHspRec24(Long caseId) throws Exception {		
		Session session = null;
		Transaction t = null;
		List<InHspRec24> list = new ArrayList<InHspRec24>();
		InHspRec24 inHspRec24 = new InHspRec24();
		try{
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			list = session.createQuery("from InHspRec24 where caseId = ?").setLong(0, caseId).list();
			if(list.size() > 0){
				inHspRec24 = list.get(0);
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return inHspRec24;
	}
	
	public static Patient getPatientRec(Long caseId) throws Exception {		
		Session session = null;
		Transaction t = null;
		InHspCaseMaster caseMaster = new InHspCaseMaster();
		Patient patient = new Patient();
		try{
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			caseMaster = (InHspCaseMaster) session.get(InHspCaseMaster.class, caseId);
			patient = (Patient) session.get(Patient.class, caseMaster.getPatientId());
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return patient;
	}
	
	public static InHspCaseMaster getInHspCaseMasterRec(Long caseId) throws Exception {		
		Session session = null;
		Transaction t = null;
		InHspCaseMaster caseMaster = new InHspCaseMaster();
		try{
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			caseMaster = (InHspCaseMaster) session.get(InHspCaseMaster.class, caseId);
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return caseMaster;
	}
	
	public static String getWardName(String wardCode) throws Exception {		
		String wardName = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select name from SYS_ZD_UserBelong where code = ?";
		try{
			conn = DatabaseUtil.getJdbcConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, wardCode);
			rs = ps.executeQuery();
			if(rs.next()){
				wardName = rs.getString("name");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return wardName;
	}
	
	public static String getOccupation(String code) throws Exception {		
		String occupationName = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select name from SYS_ZD_GB_Occupation where code = ?";
		try{
			conn = DatabaseUtil.getJdbcConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, code);
			rs = ps.executeQuery();
			if(rs.next()){
				occupationName = rs.getString("name");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return occupationName;
	}
	
	public static String getPepole(String people) throws Exception {		
		String peopleName = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select name from SYS_ZD_People where code = ?";
		try{
			conn = DatabaseUtil.getJdbcConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, people);
			rs = ps.executeQuery();
			if(rs.next()){
				peopleName = rs.getString("name");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return peopleName;
	}
	
	public static String getNationality(String nationality) throws Exception {		
		String nationalityName = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select name from SYS_ZD_Nationality where code = ?";
		try{
			conn = DatabaseUtil.getJdbcConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, nationality);
			rs = ps.executeQuery();
			if(rs.next()){
				nationalityName = rs.getString("name");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return nationalityName;
	}
	
	public static String getRelationShip(String relation) throws Exception {		
		String relationName = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select name from SYS_ZD_Relationship where code = ?";
		try{
			conn = DatabaseUtil.getJdbcConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, relation);
			rs = ps.executeQuery();
			if(rs.next()){
				relationName = rs.getString("name");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return relationName;
	}
	
	public static String getMarriageStatus(String code) throws Exception {		
		String marriageStatus = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select name from SYS_ZD_GB_MarriageStatus where code = ?";
		try{
			conn = DatabaseUtil.getJdbcConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, code);
			rs = ps.executeQuery();
			if(rs.next()){
				marriageStatus = rs.getString("name");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return marriageStatus;
	}
	
	public static String getInHspCheck(String code) throws Exception {		
		String inHspCheckName = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select name from SYS_ZD_Ills where code = ?";
		try{
			conn = DatabaseUtil.getJdbcConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, code);
			rs = ps.executeQuery();
			if(rs.next()){
				inHspCheckName = rs.getString("name");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return inHspCheckName;
	}
	
	public static String getInHspStatus(String code) throws Exception {		
		String inHspStatusName = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select name from SYS_ZD_InHspStatus where code = ?";
		try{
			conn = DatabaseUtil.getJdbcConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, code);
			rs = ps.executeQuery();
			if(rs.next()){
				inHspStatusName = rs.getString("name");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return inHspStatusName;
	}
	
	public static Date getCheckDate(long caseId) throws Exception {		
		Date checkdate = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select queding_checkdate from t_InHsp_Liver_Diagnosis where caseId = ?";
		try{
			conn = DatabaseUtil.getJdbcConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, caseId);
			rs = ps.executeQuery();
			if(rs.next()){
				checkdate = rs.getDate("queding_checkdate");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return checkdate;
	}
	
	
	
	
	
	

	
	
	
	
	private InHosptialOne callPre(Long CaseId,InHosptialOne one)throws Exception{
		Connection conn = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		List<InHosptialOne> list = new ArrayList<InHosptialOne>();
		try{
			conn = DatabaseUtil.getConnection();
			String SQL = "{ call pre_TheFirstOfCase_Select(?)}";
			if(CaseId!=null && CaseId > 0){
				cs = conn.prepareCall(SQL);
				cs.setLong(1, CaseId);
				rs = cs.executeQuery();
				while(rs.next()){
					InHosptialOne newOne = new InHosptialOne();
					newOne.setCaseId(CaseId);
					newOne.setPatientName(rs.getString("patientName"));
					newOne.setPatientNo(rs.getString("patientNo"));
					newOne.setGender(rs.getString("gender"));
					newOne.setBirthday(rs.getDate("birthday"));
//					newOne.setContacterRelationship(rs.getString("contacterRelationship"));
					newOne.setAge(rs.getString("age"));
					newOne.setXno(rs.getString("xNO"));
					newOne.setMriNo(rs.getString("mriNo"));
					newOne.setCtNo(rs.getString("ctNo"));
					newOne.setInHspTimes(rs.getInt("inHspTimes"));
					newOne.setPeople(rs.getString("people"));
//					newOne.setNationality(rs.getString("nationality"));
/*					String temp = rs.getString("marrageStatus");
					if (temp.equals("20")) {
						newOne.setMarrageStatus("2");
					} else if (temp.equals("10")) {
						newOne.setMarrageStatus("1");
					} else if (temp.equals("30")) {
						newOne.setMarrageStatus("3");
					} else if (temp.equals("40")) {
						newOne.setMarrageStatus("4");
					}
*/
//					newOne.setOccupation(rs.getString("occupation"));
					String idType = rs.getString("idType");
					if (idType.equals("01")) {
						newOne.setIdNo(rs.getString("idNo"));
					}
					newOne.setHomeAddr(rs.getString("homeAddr"));
					newOne.setHomePostCode(rs.getString("homePostCode"));
					newOne.setWorkUnitAddr(rs.getString("workUnitAddr"));
					newOne.setWorkUnitPostCode(rs.getString("workUnitPostCode"));
					newOne.setWorkUnitTel(rs.getString("workUnitTel"));
					newOne.setContacterName(rs.getString("contacterName"));
					newOne.setContacterTel(rs.getString("contacterTel"));
					
					String dt = rs.getDate("inHspDate").toString() + " " + rs.getTime("inHspDate");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date d = sdf.parse(dt);
					newOne.setInHspDate(d);
					
					Date d1 = rs.getDate("inHspDate");
					Date d2 = rs.getDate("outHspDate");
					
					if(rs.getDate("outHspDate")!=null && rs.getTime("outHspDate")!=null){
						dt = rs.getDate("outHspDate").toString() + " " + rs.getTime("outHspDate").toString();
						newOne.setOutHspDate(sdf.parse(dt));
					}
					
					long time = -1L;
					int tm = 0;
					if (d1 != null && d2 != null) {
						time = d2.getTime() - d1.getTime();
						tm = (new Double(Math.floor(time / (1000 * 3600 * 24)))
								.intValue() + 1);
					}
					newOne.setHspDate2(tm);
					newOne.setInWardCode(rs.getString("inWardCode"));
					newOne.setOutHspWard(rs.getString("outHspWard"));

					String inStatus = rs.getString("inStatus");
					if (inStatus.equals("0")) {
						inStatus = "3";
					} else if (inStatus.equals("1")) {
						inStatus = "2";
					} else if (inStatus.equals("2")) {
						inStatus = "1";
					}
					newOne.setInStatus(inStatus);
					newOne.setInIllsShow(rs.getString("inIllsShow"));
					newOne.setQueding_checkdate(rs.getDate("queding_checkdate"));
					newOne.setOutIlls1(rs.getString("outIlls1"));
					newOne.setOutIlls2(rs.getString("outIlls2"));
					newOne.setOutIlls3(rs.getString("outIlls3"));
					newOne.setOutIlls4(rs.getString("outIlls4"));
					newOne.setOutIlls5(rs.getString("outIlls5"));
					newOne.setOutIlls6(rs.getString("outIlls6"));
					newOne.setOutIlls7(rs.getString("outIlls7"));
					newOne.setOutIlls8(rs.getString("outIlls8"));
					list.add(newOne);
				}
				if(list.size() > 0){
					if(one==null){
						one = new InHosptialOne();
					}
					one = list.get(0);
				}
			}
		}catch(Exception e){
			throw e;
		}finally{
			DatabaseUtil.closeResource(conn, cs, rs);
		}
		return one;
	}
	
	/**
	 * 读取首页_反页值
	 */
	public InHosptialTwo getInHospTwo(Long CaseId) throws Exception {
		Session session = null;
		Transaction t = null;
		InHosptialTwo two = null;
		try{
			List<InHosptialTwo> list = new ArrayList<InHosptialTwo>();
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			String SQL = " from InHosptialTwo where caseId = ? ";
			if(CaseId!=null&& CaseId > 0){
				list = session.createQuery(SQL).setLong(0, CaseId).list();
			}
			if(list.size() > 0){
				two = list.get(0);
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return two;
	}
	
	/**
	 * 保存首页
	 */
	public InHosptialOne saveOrUpdateInHospOne(InHosptialOne one)
			throws Exception {
		Session session = null;
		Transaction t = null;
		try{
			Long id = -1L;
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if(one.getId()!=null && one.getId()> 0){
				session.update(one);
			}else{
				id = (Long)session.save(one);
			}
			if(id!=null && id> 0){
				one = (InHosptialOne) session.get(InHosptialOne.class, id);
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return one;
	}
	
	public InHosptialTwo saveOrUpdateInHospTwo(InHosptialTwo Two)
			throws Exception {
		Session session = null;
		Transaction t = null;
		try{
			Long id = -1L;
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if(Two.getId()!=null && Two.getId() > 0){
				session.update(Two);
			}else {
				id = (Long)session.save(Two);
			}
			if(id!= null && id > 0){
				Two = (InHosptialTwo) session.get(InHosptialTwo.class, id);
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return Two;
	}
	
	/**
	 * 查询首页(继一[正])
	 */
////////////
	/////////////////////
	/////////////////////////
	//////////////////////////////////
	/////////////////////////////
	////////////////////////////////
	////////////////////////////////////////////////////
	/////////////////////////////
	///////////////////////////////////////////////
	/////////////////////////////////
	//////////////////////////////////////
////////////
	/////////////////////
	/////////////////////////
	//////////////////////////////////
	/////////////////////////////
	////////////////////////////////
	////////////////////////////////////////////////////
	/////////////////////////////
	///////////////////////////////////////////////
	/////////////////////////////////
	//////////////////////////////////////
	public JSONObject getInHospFollowOne(Long CaseId) throws Exception {
		Session session = null;
		Transaction t = null;
		JSONObject data = new JSONObject();
		try{
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			
			/*读取出院情况数据*/
			if(CaseId!=null && CaseId > 0){
				String SQL = " from ContinuePage_Sysptom where caseId = ? ";
				List<ContinuePage_Sysptom> list = session.createQuery(SQL).setLong(0, CaseId).list();
				JSONArray arry = new JSONArray();
				/**
				 * 如果list集合值小于说明此病人第一次登记病案首页，则查出院记录表
				 */
				if(list.size() <= 0){
					arry = getOutHsp(CaseId,arry);
				}else{
					for(int i=0;i<list.size();i++){
						arry.add(JSONObject.fromObject(list.get(i)));
					}
				}
				if(arry!=null){
					data.put("disCon", arry);
				}
			}
			
			JSONObject inHospInfo = getInHospInfo(CaseId);
			/*读取基本信息*/
			if(inHospInfo!=null){
				data.put("inHospInfo", inHospInfo);
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return data;
	}
	
	/**
	 * 读取出院记录表数据
	 * @return
	 * @throws Exception
	 */
	private JSONArray getOutHsp(Long CaseId,JSONArray array)throws Exception{
		Boolean hasDeathRec = false;
		Boolean hasInHspRec24Rec = false;
		Connection conn = null;
		PreparedStatement prs = null;
		ResultSet rst = null;
		try{
			conn = DatabaseUtil.getConnection();
			if(CaseId!=null && CaseId > 0){
				String deathRecSQL= " select deathDiag9,deathDiag10,deathDiag11,deathDiag12,deathDiag13,"+
				"deathDiag14,deathDiag15,deathDiag16,deathDiag17,deathDiag18,deathDiag19,deathDiag20,deathDiag21,"+
				"deathDiag22,deathDiag23,deathDiag24,deathDiag25,deathDiag26,deathDiag27,deathDiag28,deathDiag29,deathDiag30 " + 
							" from t_CourseRec_DeathRecord where caseid = ? ";
				prs = conn.prepareStatement(deathRecSQL);
				prs.setLong(1, CaseId);
				rst = prs.executeQuery();
				List<ContinuePage_Sysptom> list = new ArrayList<ContinuePage_Sysptom>();
				if(rst.next()){
					hasDeathRec = true;
					ContinuePage_Sysptom disCon = new ContinuePage_Sysptom();
					disCon.setSysptom(rst.getString("deathDiag9"));
					list.add(disCon);
					ContinuePage_Sysptom disCon2 = new ContinuePage_Sysptom();
					disCon2.setSysptom(rst.getString("deathDiag10"));
					list.add(disCon2);
					ContinuePage_Sysptom disCon3 = new ContinuePage_Sysptom();
					disCon3.setSysptom(rst.getString("deathDiag11"));
					list.add(disCon3);
					ContinuePage_Sysptom disCon4 = new ContinuePage_Sysptom();
					disCon4.setSysptom(rst.getString("deathDiag12"));
					list.add(disCon4);
					ContinuePage_Sysptom disCon5 = new ContinuePage_Sysptom();
					disCon5.setSysptom(rst.getString("deathDiag13"));
					list.add(disCon5);
					ContinuePage_Sysptom disCon6 = new ContinuePage_Sysptom();
					disCon6.setSysptom(rst.getString("deathDiag14"));
					list.add(disCon6);
					ContinuePage_Sysptom disCon7 = new ContinuePage_Sysptom();
					disCon7.setSysptom(rst.getString("deathDiag15"));
					list.add(disCon7);
					ContinuePage_Sysptom disCon8 = new ContinuePage_Sysptom();
					disCon8.setSysptom(rst.getString("deathDiag16"));
					list.add(disCon8);
					ContinuePage_Sysptom disCon9 = new ContinuePage_Sysptom();
					disCon9.setSysptom(rst.getString("deathDiag17"));
					list.add(disCon9);
					ContinuePage_Sysptom disCon10 = new ContinuePage_Sysptom();
					disCon10.setSysptom(rst.getString("deathDiag18"));
					list.add(disCon10);
					ContinuePage_Sysptom disCon11 = new ContinuePage_Sysptom();
					disCon11.setSysptom(rst.getString("deathDiag19"));
					list.add(disCon11);
					ContinuePage_Sysptom disCon12 = new ContinuePage_Sysptom();
					disCon12.setSysptom(rst.getString("deathDiag20"));
					list.add(disCon12);
					ContinuePage_Sysptom disCon13 = new ContinuePage_Sysptom();
					disCon13.setSysptom(rst.getString("deathDiag21"));
					list.add(disCon13);
					ContinuePage_Sysptom disCon14 = new ContinuePage_Sysptom();
					disCon14.setSysptom(rst.getString("deathDiag22"));
					list.add(disCon14);
					ContinuePage_Sysptom disCon15 = new ContinuePage_Sysptom();
					disCon15.setSysptom(rst.getString("deathDiag23"));
					list.add(disCon15);
					ContinuePage_Sysptom disCon16 = new ContinuePage_Sysptom();
					disCon16.setSysptom(rst.getString("deathDiag24"));
					list.add(disCon16);
					ContinuePage_Sysptom disCon17 = new ContinuePage_Sysptom();
					disCon17.setSysptom(rst.getString("deathDiag25"));
					list.add(disCon17);
					ContinuePage_Sysptom disCon18 = new ContinuePage_Sysptom();
					disCon18.setSysptom(rst.getString("deathDiag26"));
					list.add(disCon18);
					ContinuePage_Sysptom disCon19 = new ContinuePage_Sysptom();
					disCon19.setSysptom(rst.getString("deathDiag27"));
					list.add(disCon19);
					ContinuePage_Sysptom disCon20 = new ContinuePage_Sysptom();
					disCon20.setSysptom(rst.getString("deathDiag28"));
					list.add(disCon20);
					ContinuePage_Sysptom disCon21 = new ContinuePage_Sysptom();
					disCon21.setSysptom(rst.getString("deathDiag29"));
					list.add(disCon21);
					ContinuePage_Sysptom disCon22 = new ContinuePage_Sysptom();
					disCon22.setSysptom(rst.getString("deathDiag30"));
					list.add(disCon22);
				}
				if(!hasDeathRec){
					String SQL= " select outHspDiag9,outHspDiag10,outHspDiag11,outHspDiag12,outHspDiag13,outHspDiag14,outHspDiag15,outHspDiag16,"+
					"outHspDiag17,outHspDiag18,outHspDiag19,outHspDiag20,outHspDiag21,outHspDiag22,outHspDiag23,outHspDiag24,"+
					"outHspDiag25,outHspDiag26,outHspDiag27,outHspDiag28,outHspDiag29,outHspDiag30 " + 
								" from t_CourseRec_InHspRec24 where caseid = ? ";
					prs = conn.prepareStatement(SQL);
					prs.setLong(1, CaseId);
					rst = prs.executeQuery();
					if(rst.next()){
						hasInHspRec24Rec = true;
						ContinuePage_Sysptom disCon = new ContinuePage_Sysptom();
						disCon.setSysptom(rst.getString("outHspDiag9"));
						list.add(disCon);
						ContinuePage_Sysptom disCon2 = new ContinuePage_Sysptom();
						disCon2.setSysptom(rst.getString("outHspDiag10"));
						list.add(disCon2);
						ContinuePage_Sysptom disCon3 = new ContinuePage_Sysptom();
						disCon3.setSysptom(rst.getString("outHspDiag11"));
						list.add(disCon3);
						ContinuePage_Sysptom disCon4 = new ContinuePage_Sysptom();
						disCon4.setSysptom(rst.getString("outHspDiag12"));
						list.add(disCon4);
						ContinuePage_Sysptom disCon5 = new ContinuePage_Sysptom();
						disCon5.setSysptom(rst.getString("outHspDiag13"));
						list.add(disCon5);
						ContinuePage_Sysptom disCon6 = new ContinuePage_Sysptom();
						disCon6.setSysptom(rst.getString("outHspDiag14"));
						list.add(disCon6);
						ContinuePage_Sysptom disCon7 = new ContinuePage_Sysptom();
						disCon7.setSysptom(rst.getString("outHspDiag15"));
						list.add(disCon7);
						ContinuePage_Sysptom disCon8 = new ContinuePage_Sysptom();
						disCon8.setSysptom(rst.getString("outHspDiag16"));
						list.add(disCon8);
						ContinuePage_Sysptom disCon9 = new ContinuePage_Sysptom();
						disCon9.setSysptom(rst.getString("outHspDiag17"));
						list.add(disCon9);
						ContinuePage_Sysptom disCon10 = new ContinuePage_Sysptom();
						disCon10.setSysptom(rst.getString("outHspDiag18"));
						list.add(disCon10);
						ContinuePage_Sysptom disCon11 = new ContinuePage_Sysptom();
						disCon11.setSysptom(rst.getString("outHspDiag19"));
						list.add(disCon11);
						ContinuePage_Sysptom disCon12 = new ContinuePage_Sysptom();
						disCon12.setSysptom(rst.getString("outHspDiag20"));
						list.add(disCon12);
						ContinuePage_Sysptom disCon13 = new ContinuePage_Sysptom();
						disCon13.setSysptom(rst.getString("outHspDiag21"));
						list.add(disCon13);
						ContinuePage_Sysptom disCon14 = new ContinuePage_Sysptom();
						disCon14.setSysptom(rst.getString("outHspDiag22"));
						list.add(disCon14);
						ContinuePage_Sysptom disCon15 = new ContinuePage_Sysptom();
						disCon15.setSysptom(rst.getString("outHspDiag23"));
						list.add(disCon15);
						ContinuePage_Sysptom disCon16 = new ContinuePage_Sysptom();
						disCon16.setSysptom(rst.getString("outHspDiag24"));
						list.add(disCon16);
						ContinuePage_Sysptom disCon17 = new ContinuePage_Sysptom();
						disCon17.setSysptom(rst.getString("outHspDiag25"));
						list.add(disCon17);
						ContinuePage_Sysptom disCon18 = new ContinuePage_Sysptom();
						disCon18.setSysptom(rst.getString("outHspDiag26"));
						list.add(disCon18);
						ContinuePage_Sysptom disCon19 = new ContinuePage_Sysptom();
						disCon19.setSysptom(rst.getString("outHspDiag27"));
						list.add(disCon19);
						ContinuePage_Sysptom disCon20 = new ContinuePage_Sysptom();
						disCon20.setSysptom(rst.getString("outHspDiag28"));
						list.add(disCon20);
						ContinuePage_Sysptom disCon21 = new ContinuePage_Sysptom();
						disCon21.setSysptom(rst.getString("outHspDiag9"));
						list.add(disCon21);
						ContinuePage_Sysptom disCon22 = new ContinuePage_Sysptom();
						disCon22.setSysptom(rst.getString("outHspDiag30"));
						list.add(disCon22);
					}
				}
				if(!hasDeathRec && !hasInHspRec24Rec){
					String SQL= " select outIlls9,outIlls10,outIlls11,outIlls12,outIlls13,outIlls14,outIlls15,outIlls16,"+
					"outIlls17,outIlls18,outIlls19,outIlls20,outIlls21,outIlls22,outIlls23,outIlls24,"+
					"outIlls25,outIlls26,outIlls27,outIlls28,outIlls29,outIlls30 " + 
								" from t_InHsp_Liver_OutHspRec where caseid = ? ";
					prs = conn.prepareStatement(SQL);
					prs.setLong(1, CaseId);
					rst = prs.executeQuery();
					if(rst.next()){
						ContinuePage_Sysptom disCon = new ContinuePage_Sysptom();
						disCon.setSysptom(rst.getString("outIlls9"));
						list.add(disCon);
						ContinuePage_Sysptom disCon2 = new ContinuePage_Sysptom();
						disCon2.setSysptom(rst.getString("outIlls10"));
						list.add(disCon2);
						ContinuePage_Sysptom disCon3 = new ContinuePage_Sysptom();
						disCon3.setSysptom(rst.getString("outIlls11"));
						list.add(disCon3);
						ContinuePage_Sysptom disCon4 = new ContinuePage_Sysptom();
						disCon4.setSysptom(rst.getString("outIlls12"));
						list.add(disCon4);
						ContinuePage_Sysptom disCon5 = new ContinuePage_Sysptom();
						disCon5.setSysptom(rst.getString("outIlls13"));
						list.add(disCon5);
						ContinuePage_Sysptom disCon6 = new ContinuePage_Sysptom();
						disCon6.setSysptom(rst.getString("outIlls14"));
						list.add(disCon6);
						ContinuePage_Sysptom disCon7 = new ContinuePage_Sysptom();
						disCon7.setSysptom(rst.getString("outIlls15"));
						list.add(disCon7);
						ContinuePage_Sysptom disCon8 = new ContinuePage_Sysptom();
						disCon8.setSysptom(rst.getString("outIlls16"));
						list.add(disCon8);
						ContinuePage_Sysptom disCon9 = new ContinuePage_Sysptom();
						disCon9.setSysptom(rst.getString("outIlls17"));
						list.add(disCon9);
						ContinuePage_Sysptom disCon10 = new ContinuePage_Sysptom();
						disCon10.setSysptom(rst.getString("outIlls18"));
						list.add(disCon10);
						ContinuePage_Sysptom disCon11 = new ContinuePage_Sysptom();
						disCon11.setSysptom(rst.getString("outIlls19"));
						list.add(disCon11);
						ContinuePage_Sysptom disCon12 = new ContinuePage_Sysptom();
						disCon12.setSysptom(rst.getString("outIlls20"));
						list.add(disCon12);
						ContinuePage_Sysptom disCon13 = new ContinuePage_Sysptom();
						disCon13.setSysptom(rst.getString("outIlls21"));
						list.add(disCon13);
						ContinuePage_Sysptom disCon14 = new ContinuePage_Sysptom();
						disCon14.setSysptom(rst.getString("outIlls22"));
						list.add(disCon14);
						ContinuePage_Sysptom disCon15 = new ContinuePage_Sysptom();
						disCon15.setSysptom(rst.getString("outIlls23"));
						list.add(disCon15);
						ContinuePage_Sysptom disCon16 = new ContinuePage_Sysptom();
						disCon16.setSysptom(rst.getString("outIlls24"));
						list.add(disCon16);
						ContinuePage_Sysptom disCon17 = new ContinuePage_Sysptom();
						disCon17.setSysptom(rst.getString("outIlls25"));
						list.add(disCon17);
						ContinuePage_Sysptom disCon18 = new ContinuePage_Sysptom();
						disCon18.setSysptom(rst.getString("outIlls26"));
						list.add(disCon18);
						ContinuePage_Sysptom disCon19 = new ContinuePage_Sysptom();
						disCon19.setSysptom(rst.getString("outIlls27"));
						list.add(disCon19);
						ContinuePage_Sysptom disCon20 = new ContinuePage_Sysptom();
						disCon20.setSysptom(rst.getString("outIlls28"));
						list.add(disCon20);
						ContinuePage_Sysptom disCon21 = new ContinuePage_Sysptom();
						disCon21.setSysptom(rst.getString("outIlls29"));
						list.add(disCon21);
						ContinuePage_Sysptom disCon22 = new ContinuePage_Sysptom();
						disCon22.setSysptom(rst.getString("outIlls30"));
						list.add(disCon22);
					}
				}
				if(list.size() > 0){
					for(ContinuePage_Sysptom dis : list){
						array.add(JSONObject.fromObject(dis));
					}
				}
			}
		}catch(Exception e){
			throw e;
		}finally{
			DatabaseUtil.closeResource(conn, prs, rst);
		}
		return array;
	}
	
	/**
	 * 获取首页基本信息
	 * @param CaseId
	 * @return
	 * @throws Exception
	 */
	private JSONObject getInHospInfo(Long CaseId) throws Exception{
		Session session = null;
		Transaction t = null;
		JSONObject inHospInfo = null;
		try{
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			InHosptialOne one = null;
			if(CaseId !=null && CaseId > 0){
				String SQL = " from InHosptialOne where caseId=? ";
				List<InHosptialOne> list = session.createQuery(SQL).setLong(0,CaseId).list();
				if(list.size() > 0){
					one = list.get(0);
				}
			}
			if(one!=null){
				inHospInfo = JSONObject.fromObject(one,JSONValueProcessor.formatDate("yyyy-MM-dd"));
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return inHospInfo;
	}

	/**
	 * 获取首页(继一[反])数据值
	 * 包含手术记录，监护室 
	 * @param CaseId
	 * @return
	 * @throws Exception
	 */
	public JSONObject getInHospFollowTwo(Long CaseId) throws Exception {
		Session session = null;
		Transaction t = null;
		JSONObject data = new JSONObject();
		try{
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			
			if(CaseId!=null && CaseId > 0){
				/*查询手术记录数据*/
				String SQL=" from ContinuePage_Ops where caseId = ? ";
				List<ContinuePage_Ops> ops = session.createQuery(SQL).setLong(0, CaseId).list();
				JSONArray array = new JSONArray();
				if(ops.size() > 0){
					for(int i=0;i<ops.size();i++){
						/*未对日期类型数据进行格式化*/
						array.add(JSONObject.fromObject(ops.get(i),JSONValueProcessor.cycleExcludel(
								new String[] { "c" }, "yyyy-MM-dd")));
					}
				}
				if(array!=null){
					data.put("ops", array);
				}
				/*监护室*/
				SQL = " from ContinuePage_Ward where caseId = ? ";
				JSONArray ward = new JSONArray();
				List<ContinuePage_Ward> wards = session.createQuery(SQL).setLong(0,CaseId).list();
				if(wards.size() > 0){
					for(int i=0;i<wards.size();i++){
						ward.add(JSONObject.fromObject(wards.get(i),JSONValueProcessor.cycleExcludel(
								new String[] { "c" }, "yyyy-MM-dd HH:mm")));
					}
				}
				if(ward!=null){
					data.put("ward", ward);
				}
			}
			
			JSONObject inHospInfo = getInHospInfo(CaseId);
			/*读取基本信息*/
			if(inHospInfo!=null){
				data.put("inHospInfo", inHospInfo);
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return data;
	}
	
	/**
	 * 保存或修改首页(继页[正])
	 * @param json
	 * @return
	 * @throws
	 */
	public Boolean saveOrUpdateInHospFollowOne(JSONObject json)
			throws Exception {
		Session session = null;
		Transaction t = null;
		boolean struts = false;
		try{
			if(json!=null){
				session = DatabaseUtil.getSession();
				t = session.beginTransaction();
				/*保存或修改出院情况*/
				JSONArray ops = json.getJSONArray("disCon");
				if(ops!=null){
					List<ContinuePage_Sysptom> list = JSONArray.toList(ops,ContinuePage_Sysptom.class);
					if(list.size() > 0){
						for(ContinuePage_Sysptom sys : list){
							if(sys.getId()!=null && sys.getId() > 0){
								session.update(sys);
							}else{
								session.save(sys);
							}
						}
					}
				}
				struts = true;
			}
			t.commit();
		}catch(Exception e){
			struts = false;
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return struts;
	}

	/**
	 * 保存首页(继一[反])
	 * 手术记录值和监护室 值
	 * @param
	 * @return
	 * @throws
	 */
	public Boolean saveOrUpdateInHospFollowTwo(JSONObject json)throws Exception {
		Session session = null;
		Transaction t = null;
		Boolean struts = false;
		try{
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			if(json!=null){
				JSONArray ops = json.getJSONArray("ops");
				if(ops!=null){
					List<ContinuePage_Ops> list = JSONArray.toList(ops,ContinuePage_Ops.class);
					if(list.size() > 0){
						for(ContinuePage_Ops op : list){
							if(op.getId()!=null && op.getId() > 0){
								session.update(op);
							}else{
								session.save(op);
							}
						}
					}
				}
				
				JSONArray wards = json.getJSONArray("ward");
				if(wards!=null){
					List<ContinuePage_Ward> list = JSONArray.toList(wards,ContinuePage_Ward.class);
					if(list.size() > 0){
						for(ContinuePage_Ward ward : list){
							if(ward.getId()!=null && ward.getId() > 0){
								session.update(ward);
							}else{
								session.save(ward);
							}
						}
					}
				}
			}
			struts = true;
			t.commit();
		}catch(Exception e){
			struts = false;
			t.rollback();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return struts;
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		InHospitalDao dao = new InHospitalImpl();
		InHosptialOne one = new InHosptialOne();
		JSONObject js = dao.getInHospFollowTwo(5073L);
	}
	
	public String getAddress(String addressName) throws Exception {
		Session session = null;
		Transaction t = null;
		List<Address> list = new ArrayList<Address>();
		String addressInfo = "";
		String addressNo = "";
		try{
			session = DatabaseUtil.getSession();
			t = session.beginTransaction();
			
			List<Address> _list = session.createQuery("from Address where name = ?").setString(0, addressName).list();
			if(_list.size() > 0){
				addressNo = _list.get(0).getAddressNo();
			}
			List<Address> addressList = session.createQuery("from Address").list();
			
			if(addressName.equals("")){
				for(Address address : addressList){
					if(address.getAddressNo().length() == 2){
						list.add(address);
					};
				}
				addressInfo += "<option value='-1'>请选择省</option>";
			}else{
				if(!addressName.equals("-1")){
					for(Address address : addressList){
						if(address.getAddressNo().length() == addressNo.length() + 2 && address.getAddressNo().substring(0, addressNo.length()).equals(addressNo)){
							list.add(address);
						};
					}
					if(addressNo.length() == 2){
						addressInfo += "<option value='-1'>请选择市</option>";
					}
					if(addressNo.length() == 4){
						addressInfo += "<option value='-1'>请选择县(区)</option>";
					}
				}
			}
			for(Address address : list){
				addressInfo += "<option value='" + address.getName() + "'>" + address.getName() + "</option>";
			}
			if(list.size() == 0){
				addressInfo = "";
			}
			t.commit();
		}catch(Exception e){
			t.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return addressInfo;
	}
}
