package com.juncsoft.KLJY.InHospitalCase.CheckReport.impl;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.InHospitalCase.CheckReport.dao.CheckReportDao;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportInfo;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportInfoForAdd;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportList;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportListForAdd;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.Department;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.DiagnosisClinical;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.GroupItem;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.Pgroup;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportFormforRemote;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportItemforRemote;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.SampleType;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.TestItem;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.TestItemforRemote;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.UpdateReportFormForRemoteRecord;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.message.dao.MessageDao;
import com.juncsoft.KLJY.message.entity.Message;
import com.juncsoft.KLJY.message.impl.MessageDaoImpl;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.user.action.HisService;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.stongnet.sms.http.Sms;
import com.stongnet.sms.http.SmsOperator;

public class CheckReportImpl implements CheckReportDao {
	private MessageDao messDao = new MessageDaoImpl();
	public List<TestItem> getSubCName(String combinationCName) {
		Session session = null;
		Transaction tran = null;
		List<TestItem> testItemList = new ArrayList<TestItem>();
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<TestItem> list = session.createQuery("from TestItem where cname = ?").setString(0, combinationCName).list();
			
			int parItemNo;
			if(list.iterator().hasNext()){
				parItemNo = list.iterator().next().getItemNo();
				List<GroupItem> groupItemList = session.createQuery("from GroupItem where pitemNo = ?").setInteger(0, parItemNo).list();
				for(GroupItem groupItem : groupItemList){
					TestItem testItem = new TestItem();
					testItem = (TestItem) session.createQuery("from TestItem where itemNo = ?").setInteger(0, groupItem.getItemNo()).list().iterator().next();
					testItemList.add(testItem);
				}
			}						
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return testItemList;
	}

	public List<CheckReportList> getCheckReportList(String receiveDate, String sectionNo, 
			String testTypeNo, String sampleNo, String parItemNo){
		Session session = null;
		Transaction tran = null;
		List<ReportItemforRemote> rilist = new ArrayList<ReportItemforRemote>();
		List<CheckReportList> list = new ArrayList<CheckReportList>();
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			rilist = session.createQuery("from ReportItemforRemote where receiveDate = ? and sectionNo = ? and testTypeNo = ? " +
					"and sampleNo = ?").setDate(0, java.text.DateFormat.getDateInstance().parse(receiveDate))
					.setInteger(1, Integer.parseInt(sectionNo)).setInteger(2, Integer.parseInt(testTypeNo)).setString(3, sampleNo)
					.list();	
			for(ReportItemforRemote ri : rilist){
				CheckReportList cr = convertObj(ri);
//				String hql ="from UpdateReportFormForRemoteRecord where itemId="+ri.getId();
//				List listHql = session.createQuery(hql).list();
//				if(listHql != null && listHql.size() >0){
//					cr.setUpdateResult("查看");
//				}else{
//					cr.setUpdateResult("");
//				}
				list.add(cr);
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
	
	public List<CheckReportList> getCheckReportListLu(String receiveDate, String sectionNo, 
			String testTypeNo, String sampleNo, String parItemNo){
		Session session = null;
		Transaction tran = null;
		List<ReportItemforRemote> rilist = new ArrayList<ReportItemforRemote>();
		List<CheckReportList> list = new ArrayList<CheckReportList>();
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			rilist = session.createQuery("from ReportItemforRemote where receiveDate = ? and sectionNo = ? and testTypeNo = ? " +
					"and sampleNo = ? and parItemNo = ?").setDate(0, java.text.DateFormat.getDateInstance().parse(receiveDate))
					.setInteger(1, Integer.parseInt(sectionNo)).setInteger(2, Integer.parseInt(testTypeNo)).setString(3, sampleNo)
					.setInteger(4, Integer.parseInt(parItemNo)).list();	
			for(ReportItemforRemote ri : rilist){
				CheckReportList cr = convertObjLu(ri);
				String hql ="from UpdateReportFormForRemoteRecord where itemId="+ri.getId();
				List listHql = session.createQuery(hql).list();
				if(listHql != null && listHql.size() >0){
					cr.setUpdateResult("查看");
				}else{
					cr.setUpdateResult("");
				}
				list.add(cr);
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	private static int getParItemNoByName(String checkProject){
		int parItemNo = 0;
		Session session = null;
		Transaction tran = null;
		String isAddPro = "";
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			isAddPro = checkProject.substring(0, 1);
			if(isAddPro.equals("!")){
				String addedPro = checkProject.substring(1,checkProject.length());
				List<TestItem> list = session.createQuery("from TestItem where CName = ? and IsProfile = 1").setString(0, addedPro).list();
				if(!list.isEmpty()){
					TestItem ti= list.get(0);
					parItemNo = ti.getItemNo();
				}else{
					List<TestItem> tilist = session.createQuery("from TestItem where CName = ?").setString(0, addedPro).list();
					if(!tilist.isEmpty())
						parItemNo = tilist.get(0).getItemNo();
				}
			}else{
				List<TestItemforRemote> list = session.createQuery("from TestItemforRemote where CName = ? and IsProfile = 1").setString(0, checkProject).list();
				if(!list.isEmpty()){
					TestItemforRemote ti= list.get(0);
					parItemNo = ti.getItemNo();
				}else{
					List<TestItemforRemote> tilist = session.createQuery("from TestItemforRemote where CName = ?").setString(0, checkProject).list();
					if(!tilist.isEmpty())
						parItemNo = tilist.get(0).getItemNo();
				}
			}			
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return parItemNo;
	}
	
	private static TestItemforRemote getTestItemforRemoteById(int itemNo){
		TestItemforRemote ti = new TestItemforRemote();
		Session session = null;
		Transaction tran = null;
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			ti = (TestItemforRemote) session.createQuery("from TestItemforRemote where ItemNo = ?").setInteger(0, itemNo).list().get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return ti;
	}
	
	private static TestItem getTestItemById(int itemNo){
		TestItem ti = new TestItem();
		Session session = null;
		Transaction tran = null;
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			ti = (TestItem) session.createQuery("from TestItem where ItemNo = ?").setInteger(0, itemNo).list().get(0);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return ti;
	}
	
	private static CheckReportList convertObj(ReportItemforRemote ri){		
		CheckReportList cr = new CheckReportList();
		if(1 == ri.getIsAdd()){
			TestItem ti = getTestItemById(ri.getItemNo());
			//注：以下各个set方法关联的内容不一定正确，待检查
			cr.setProjectname(ti.getCname());
			cr.setEsname(ti.getEname());
			cr.setUnit(ti.getUnit());
			//liugang2011-0929
			cr.setId(ri.getId()+"");
			//liugang2011-0929
		}else{
			TestItemforRemote ti = getTestItemforRemoteById(ri.getItemNo());
			//注：以下各个set方法关联的内容不一定正确，待检查
			cr.setProjectname(ti.getCname());
			cr.setEsname(ti.getEname());
//			更改unit
			String unit = ti.getUnit();
			String modifyUnit = "";
			if(unit.contains("*") && unit.contains("^")){
				if(unit.contains("-") || unit.contains("+")){
					modifyUnit = unit.replace("*10", "E").replace("^", "");
				}else{
					modifyUnit = unit.replace("*10", "E").replace("^", "+");
				}	
			}else{
				modifyUnit = unit;
			}
			cr.setUnit(modifyUnit);			
		}
		cr.setHistoryResult(ri.getHisValue());
		cr.setHistoryRatio(ri.getHisComp());
		cr.setRefvalue(ri.getRefRange());	
		float rv = ri.getReportValue();
		String result = "";
		if(rv > 0){
			String refValue = ri.getRefRange();
			if(refValue != null && refValue != ""){
				String[] arr = refValue.split(".");
				if(arr.length > 1){
					int count = arr[arr.length - 1].length();					
					String rvStr = "" + rv;
					String[] rvArr = rvStr.split(".");
					int rvcount = rvArr[rvArr.length - 1].length();
					if(count == 3){
						result = "" + Math.round(rv * 1000) / 1000f;
					}else if(rvcount == 0 || rvcount == 1){
						result = "" + rv;
					}else{
						float temp = ri.getReportValue();
						result = "" + Math.round(rv * 100) / 100f;
					}
				}else{
					result = "" + rv;
				}			
			}else{
				result = "" + rv;
			}
		}else{
			if(ri.getReportDesc() != null && ri.getReportDesc() != ""){
				String reportDesc = ri.getReportDesc();
				String modifyRd = "";
				if(reportDesc.contains("*") && reportDesc.contains("^")){
					if(reportDesc.contains("-") || reportDesc.contains("+")){
						modifyRd = reportDesc.replace("*10", "E").replace("^", "");
					}else{
						modifyRd = reportDesc.replace("*10", "E").replace("^", "+");
					}	
				}else{
					modifyRd = reportDesc;
				}
				result = modifyRd;	
			}				
		}
		if((!"null".equals(ri.getWaiyuan_reportValue())) && ri.getWaiyuan_reportValue() != null){
			result=ri.getWaiyuan_reportValue();
		}
		cr.setResult(result);
		return cr;
	}
	
	private static CheckReportList convertObjLu(ReportItemforRemote ri){		
		CheckReportList cr = new CheckReportList();
		if(1 == ri.getIsAdd()){
			TestItem ti = getTestItemById(ri.getItemNo());
			//注：以下各个set方法关联的内容不一定正确，待检查
			cr.setProjectname(ti.getCname());
			cr.setEsname(ti.getEname());
			cr.setUnit(ti.getUnit());
			//liugang2011-0929
			cr.setId(ri.getId()+"");
			//liugang2011-0929
		}else{
			TestItemforRemote ti = getTestItemforRemoteById(ri.getItemNo());
			//注：以下各个set方法关联的内容不一定正确，待检查
			cr.setProjectname(ti.getCname());
			cr.setEsname(ti.getEname());
//			更改unit
			String unit = ti.getUnit();
			String modifyUnit = "";
			if(unit.contains("*") && unit.contains("^")){
				if(unit.contains("-") || unit.contains("+")){
					modifyUnit = unit.replace("*10", "E").replace("^", "");
				}else{
					modifyUnit = unit.replace("*10", "E").replace("^", "+");
				}	
			}else{
				modifyUnit = unit;
			}
			cr.setUnit(modifyUnit);			
		}
		cr.setHistoryResult(ri.getHisValue());
		cr.setHistoryRatio(ri.getHisComp());
		cr.setRefvalue(ri.getRefRange());	
		float rv = ri.getReportValue();
		String result = "";
		if((!"null".equals(ri.getWaiyuan_reportValue())) && ri.getWaiyuan_reportValue() != null){
			result=ri.getWaiyuan_reportValue();
		}
		cr.setResult(result);
		return cr;
	}
	

	public CheckReportInfo getCheckReportInfo(String patientId, String receiveDate, String sectionNo, String testTypeNo, String sampleNo) {
		Session session = null;
		Transaction tran = null;		
		CheckReportInfo ci = new CheckReportInfo();
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();	
			
			Patient patient = new Patient();
			patient = (Patient) session.get(Patient.class, (long)Integer.parseInt(patientId));
			
			List<ReportFormforRemote> rflist = session.createQuery("from ReportFormforRemote where receiveDate = ? and sectionNo = ? " +
					"and testTypeNo = ? and sampleNo = ?")
					.setDate(0, java.text.DateFormat.getDateInstance().parse(receiveDate))
					.setInteger(1, Integer.parseInt(sectionNo)).setInteger(2, Integer.parseInt(testTypeNo))
					.setString(3, sampleNo).list();
			ReportFormforRemote rf = new ReportFormforRemote();
			if(rflist.size() > 0){
				rf = rflist.get(0);
			}

			String depatrment = "";
			
			ci = convertToCRI(rf, patient, depatrment);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return ci;
	}
	
	private static CheckReportInfo convertToCRI(ReportFormforRemote rf, Patient patient, String department){
		CheckReportInfo cr = new CheckReportInfo();
		cr.setSampleNo(rf.getSampleNo());
		cr.setCname(rf.getCname());
		cr.setPatNo(patient.getPatientNo());
		if(patient.getGender().equals("1")){
			cr.setGenderNo("男");
		}else{
			cr.setGenderNo("女");
		}
		cr.setDeptNo(department);
		cr.setBed(rf.getBed());
		cr.setDoctor(rf.getDoctor());
		cr.setCollectDate(rf.getCollectDate());
		cr.setTestDate(rf.getTestDate());
		cr.setCollecter(rf.getCollecter());
		//liugang 区分本院与外院化验单
		cr.setAge(rf.getAge());
		cr.setIsFromOutHospital(rf.getIsFromOutHospital());
		cr.setSampleTypeNo("" + rf.getSampleTypeNo());
		cr.setFormMemo(rf.getFormMemo());
		
		cr.setHospitalName(rf.getHospitalName());
		cr.setReceiveDate(rf.getReceiveDate());		
		cr.setTestTypeNo(new Integer(rf.getTestTypeNo()).toString());
		cr.setHome_foreign(rf.getHome_foreign());//ReportFormforRemote中home_foreign等于-1表示从list拷贝过来的数据		
		return cr;
	}

	public Patient getCheckReportInfoForAdd(String patientId) {
		Session session = null;
		Transaction tran = null;
		List<ReportItemforRemote> rilist = new ArrayList<ReportItemforRemote>();
		List<CheckReportList> list = new ArrayList<CheckReportList>();
		Patient patient = new Patient();
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();			
			patient = (Patient) session.get(Patient.class, (long)Integer.parseInt(patientId));
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return patient;
	}


	
	//以下各个函数用于新增记录后的保存、更新工作				存在的问题：部分数据没有保存
	public ReportFormforRemote saveCheckReport(CheckReportInfoForAdd cri,List<CheckReportListForAdd> list) throws Exception {
		Session session = null;
		Transaction tran = null;
		ReportFormforRemote rf = null;
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			rf = setRF(cri);						
			
			String pati = "from Patient where patientNo ='"+rf.getPatNo()+"'";
			Patient pat = (Patient)session.createQuery(pati).list().get(0);
			rf.setPatNo(pat.getPatientId());
			//2011-09-28 mengyuzhang start
			rf.setIsFromOutHospital(1);//区分本院与外院手动录入的化验单数据 1：表示外院 null 表示 本院，(程序导入lis中的数据)
			rf.setLuRuDate(new Date());
			if(rf.getIsPatientOrDoctorWriteZanCun() == 11){
				rf.setSheHeDate(new Date());
			}else if(rf.getIsPatientOrDoctorWriteZanCun() == 21){
				rf.setGuiDangDate(new Date());
			}
			//2011-09-28 mengyuzhang end
			rf.setId(Integer.parseInt(session.save(rf).toString()));			
			List<ReportItemforRemote> riList = setRI(rf, cri, list);
			for(ReportItemforRemote ri : riList){
				session.save(ri);
			}	
			tran.commit();
		}catch(Exception e){
			tran.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
			return rf;
		}
	}
	//此处有待修改的地方
	private static ReportFormforRemote setRF(CheckReportInfoForAdd cri){
		ReportFormforRemote rf = new ReportFormforRemote();
		try {
			if(cri.getReceiveDate() != null){
				rf.setReceiveDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC'Z yyyy",Locale.ENGLISH).parse(cri.getReceiveDate()));
			}
			if(cri.getSectionNo() != null){
				rf.setSectionNo(Integer.parseInt(cri.getSectionNo()));
			}
			if(cri.getSampleNo() != null){	
				rf.setSampleNo(cri.getSampleNo());
			}
			if(cri.getTestTypeNo() != null){
				rf.setTestTypeNo(Integer.parseInt(cri.getTestTypeNo()));			
			}
			if(cri.getCname() != null){
				rf.setCname(cri.getCname());
			}
			if(cri.getAge() != null){
				rf.setAge(Integer.parseInt(cri.getAge()));
			}
			if(cri.getGenderNo().equals("男")){
				rf.setGenderNo(1);
			}else if(cri.getGenderNo().equals("女")){
				rf.setGenderNo(2);
			}else{
				rf.setGenderNo(0);
			}
			if(cri.getPatNo() != null){
				rf.setPatNo(cri.getPatNo());
			}
			if(cri.getBed() != null){
				rf.setBed(cri.getBed());
			}
			
			if(cri.getDiagNo() != null && !"".equals(cri.getDiagNo())){
				rf.setDiagNo(Integer.parseInt(cri.getDiagNo()));
			}
			if(cri.getDeptNo() != null && !"".equals(cri.getDeptNo())){
				rf.setDeptNo(Integer.parseInt(cri.getDeptNo()));
			}
			if(cri.getDoctor() != null){
				rf.setDoctor(cri.getDoctor());
			}
			if(cri.getCollecter() != null){
				rf.setCollecter(cri.getCollecter());
			}
			if(cri.getSampleTypeNo() != null && !"".equals(cri.getSampleTypeNo())){
				rf.setSampleTypeNo(Integer.parseInt(cri.getSampleTypeNo()));
			}
			if(cri.getFormMemo()!= null && !"".equals(cri.getFormMemo())){
				rf.setFormMemo(cri.getFormMemo());
			}
			if(cri.getIsPatientOrDoctorWriteZanCun()!= null && !"".equals(cri.getIsPatientOrDoctorWriteZanCun())){
				rf.setIsPatientOrDoctorWriteZanCun(cri.getIsPatientOrDoctorWriteZanCun());
			}
			rf.setHospitalName(cri.getHospitalName());
			rf.setHome_foreign(cri.getHome_foreign());
			rf.setIsAdd(1);
			if(cri.getCollectDate()!= null && !"".equals(cri.getCollectDate())){
				rf.setCollectDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC'Z yyyy",Locale.ENGLISH).parse(cri.getCollectDate()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rf;
	}
	
	private static List<ReportItemforRemote> setRI(ReportFormforRemote rf, CheckReportInfoForAdd cri, List<CheckReportListForAdd> list){
		
		int pitemNo = getItemNo(cri.getCheckProject());
		int itemNo = 0;
		List<ReportItemforRemote> riList = new ArrayList<ReportItemforRemote>();
		for(CheckReportListForAdd cr : list){
			ReportItemforRemote ri = new ReportItemforRemote();
			itemNo = getItemNo(cr.getProjectname());
			ri.setReceiveDate(rf.getReceiveDate());
			ri.setSampleNo(rf.getSampleNo());
			ri.setTestTypeNo(rf.getTestTypeNo());
			ri.setSectionNo(rf.getSectionNo());
			
			ri.setParItemNo(pitemNo);
			ri.setItemNo(itemNo);
			if(cr.getResult() != null && !cr.getResult().equals("")){
//				ri.setReportValue(Float.parseFloat(cr.getResult()));
				ri.setWaiyuan_reportValue(cr.getResult());//处理外院的检查中有时包含的其他符号(+、-)
				ri.setEquipCommMemo("有值");
			}			
			ri.setRefRange(cr.getRefvalue());
			ri.setUnit(cr.getUnit());
			ri.setIsAdd(1);
			ri.setHome_foreign(cri.getHome_foreign());
			riList.add(ri);
		}
		
		return riList;
	}

	private static int getItemNo(String cname){
		Session session = null;
		Transaction tran = null;
		int itemNo = 0;
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<TestItem> list = session.createQuery("from TestItem where CName = ?").setString(0, cname).list();
			if(list.size() > 0){
				itemNo = list.get(0).getItemNo();
			}
			tran.commit();
		}catch(Exception e){
			tran.rollback();
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return itemNo;
	}
	
	public void updateCheckReport(CheckReportInfoForAdd cri, List<CheckReportListForAdd> list, String receiveDate, int sectionNo, int testTypeNo, String sampleNo) throws Exception {
		Session session = null;
		Transaction tran = null;
		List<ReportItemforRemote> rilist = new ArrayList<ReportItemforRemote>();
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<ReportFormforRemote> rflist = session.createQuery("from ReportFormforRemote where receiveDate = ? and sectionNo = ? and testTypeNo = ? and sampleNo = ?")
						.setDate(0, new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC'Z yyyy",Locale.ENGLISH).parse(receiveDate))
						.setInteger(1, sectionNo).setInteger(2, testTypeNo)
						.setString(3, sampleNo).list();
			if(rflist.size() != 0){
				ReportFormforRemote rf = rflist.get(0);
				session.delete(rf);
			}
					
			rilist = session.createQuery("from ReportItemforRemote  where receiveDate = ? and sectionNo = ? and testTypeNo = ? and sampleNo = ?")
						.setDate(0, new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC'Z yyyy",Locale.ENGLISH).parse(receiveDate))
						.setInteger(1, sectionNo).setInteger(2, testTypeNo)
						.setString(3, sampleNo).list();
			for(ReportItemforRemote ri : rilist){
				session.delete(ri);
			}
			cri.setSectionNo( "" + sectionNo + "");
			saveCheckReport(cri, list,session);
			tran.commit();
		}catch(Exception e){
			tran.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}

	public String getSampleTypeName(int sampleTypeNo) {
		Session session = null;
		Transaction tran = null;
		String sampleTypeName = "";
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			SampleType sampleType = (SampleType) session.get(SampleType.class, sampleTypeNo);
			if(sampleType != null){
				sampleTypeName = sampleType.getCname();
			}
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return sampleTypeName;
	}

	public String getDepartmentByPatId(int patId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select ub.name from t_Patient p,t_InHsp_CaseMaster c, SYS_ZD_UserBelong ub where p.id = ? and p.id = c.patientId and c.currentWordCode = ub.code";
		String department = "";
		try{
			conn = DatabaseUtil.getJdbcConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, patId);
			rs = ps.executeQuery();
			if(rs.next()){
				department = rs.getString("name");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				DatabaseUtil.closeResource(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return department;
	}
	
	//以下各个函数用于新增记录后的保存、更新工作				存在的问题：部分数据没有保存
	public void saveCheckReport(CheckReportInfoForAdd cri,List<CheckReportListForAdd> list,Session session) throws Exception {
		try{
			ReportFormforRemote rf = setRF(cri);						
			String pati = "from Patient where patientNo ='"+rf.getPatNo()+"'";
			Patient pat = (Patient)session.createQuery(pati).list().get(0);
			rf.setPatNo(pat.getPatientId());
			//2011-09-28 mengyuzhang start
			rf.setIsFromOutHospital(1);//区分本院与外院手动录入的化验单数据 1：表示外院 null 表示 本院，(程序导入lis中的数据)
			rf.setLuRuDate(new Date());
			if(rf.getIsPatientOrDoctorWriteZanCun() == 11){
				rf.setSheHeDate(new Date());
			}else if(rf.getIsPatientOrDoctorWriteZanCun() == 21){
				rf.setGuiDangDate(new Date());
			}
			//2011-09-28 mengyuzhang end
			session.save(rf);			
			List<ReportItemforRemote> riList = setRI(rf, cri, list,session);
			for(ReportItemforRemote ri : riList){
				session.save(ri);
			}	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	
	private static List<ReportItemforRemote> setRI(ReportFormforRemote rf, CheckReportInfoForAdd cri, List<CheckReportListForAdd> list,Session session){
		
		int pitemNo = getItemNo(cri.getCheckProject(),session);
		int itemNo = 0;
		List<ReportItemforRemote> riList = new ArrayList<ReportItemforRemote>();
		for(CheckReportListForAdd cr : list){
			ReportItemforRemote ri = new ReportItemforRemote();
			itemNo = getItemNo(cr.getProjectname(),session);
			ri.setReceiveDate(rf.getReceiveDate());
			ri.setSampleNo(rf.getSampleNo());
			ri.setTestTypeNo(rf.getTestTypeNo());
			ri.setSectionNo(rf.getSectionNo());
			
			ri.setParItemNo(pitemNo);
			ri.setItemNo(itemNo);
			ri.setRefRange(cr.getRefvalue());
			ri.setUnit(cr.getUnit());
			ri.setIsAdd(1);
			ri.setHome_foreign(cri.getHome_foreign());
			ri.setId(Integer.parseInt(cr.getId()));
			if(cr.getResult() != null && !("".equals(cr.getResult()))){
				ri.setWaiyuan_reportValue(cr.getResult());
			}			
			riList.add(ri);
		}
		
		return riList;
	}
	
	private static List<ReportItemforRemote> setSaveTijiaoRI(ReportFormforRemote rf, CheckReportInfoForAdd cri, List<CheckReportListForAdd> list,Session session){
		
		int pitemNo = getItemNo(cri.getCheckProject(),session);
		int itemNo = 0;
		List<ReportItemforRemote> riList = new ArrayList<ReportItemforRemote>();
		for(CheckReportListForAdd cr : list){
			ReportItemforRemote ri = new ReportItemforRemote();
			itemNo = getItemNo(cr.getProjectname(),session);
			ri.setReceiveDate(rf.getReceiveDate());
			ri.setSampleNo(rf.getSampleNo());
			ri.setTestTypeNo(rf.getTestTypeNo());
			ri.setSectionNo(rf.getSectionNo());
			
			ri.setParItemNo(pitemNo);
			ri.setItemNo(itemNo);
			ri.setRefRange(cr.getRefvalue());
			ri.setUnit(cr.getUnit());
			ri.setIsAdd(1);
			ri.setHome_foreign(cri.getHome_foreign());
			if(cr.getResult() != null && !("".equals(cr.getResult()))){
				ri.setWaiyuan_reportValue(cr.getResult());
			}			
			riList.add(ri);
		}
		
		return riList;
	}

	private static int getItemNo(String cname,Session session){
		int itemNo = 0;
		try{
			List<TestItem> list = session.createQuery("from TestItem where CName = ?").setString(0, cname).list();
			if(list.size() > 0){
				itemNo = list.get(0).getItemNo();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return itemNo;
	}
	
	//liugang 2011-09-30
	public void updateCheckReportNoDelete(CheckReportInfoForAdd cri, List<CheckReportListForAdd> list, String receiveDate, int sectionNo, int testTypeNo, String sampleNo) throws Exception {
		Session session = null;
		Transaction tran = null;
		List<ReportItemforRemote> rilist = new ArrayList<ReportItemforRemote>();
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			cri.setSectionNo( "" + sectionNo + "");
			ReportFormforRemote rf = (ReportFormforRemote)session.get(ReportFormforRemote.class, Integer.parseInt(cri.getId()));
			int attempFlag = rf.getIsPatientOrDoctorWriteZanCun();
			rf = setRF(cri,rf);
//			rf.setId(Integer.parseInt(cri.getId()));
			String pati = "from Patient where patientNo ='"+rf.getPatNo()+"'";
			Patient pat = (Patient)session.createQuery(pati).list().get(0);
			rf.setPatNo(pat.getPatientId());
			//2011-09-28 mengyuzhang start
			rf.setIsFromOutHospital(1);//区分本院与外院手动录入的化验单数据 1：表示外院 null 表示 本院，(程序导入lis中的数据)
			if(rf.getIsPatientOrDoctorWriteZanCun() == 11){
				rf.setSheHeDate(new Date());
				if(rf.getCheXiaoFlag() != null && rf.getCheXiaoFlag() == 1){
					rf.setCheXiaoTrue(2);
				}
			}else if(rf.getIsPatientOrDoctorWriteZanCun() == 21){
				if(attempFlag == 11){
					rf.setIsPatientOrDoctorWriteZanCun(11);
				}
				rf.setGuiDangDate(new Date());
			}
			//2011-09-28 mengyuzhang end
			session.update(rf);		
			
			List<ReportItemforRemote> riList = setRI(rf, cri, list,session);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(ReportItemforRemote ri : riList){
				if(rf.getCheXiaoFlag() != null && rf.getCheXiaoFlag() == 1){//管理员撤消后归档
					UpdateReportFormForRemoteRecord updateReportFormForRemoteRecord = new UpdateReportFormForRemoteRecord();
					updateReportFormForRemoteRecord.setUpdateDate(new Date());
					String sqlOld ="from ReportItemforRemote where id ="+ri.getId()+" and receiveDate ='"+sdf.format(ri.getReceiveDate())+"'";
					ReportItemforRemote rifr = (ReportItemforRemote)session.createQuery(sqlOld).list().get(0);
					updateReportFormForRemoteRecord.setOldValue(rifr.getWaiyuan_reportValue());
					updateReportFormForRemoteRecord.setNewValue(ri.getWaiyuan_reportValue());
					updateReportFormForRemoteRecord.setItemId(ri.getId());
					updateReportFormForRemoteRecord.setUpdateAuthor(cri.getDoctor());
					if(ri.getWaiyuan_reportValue() != null &&!ri.getWaiyuan_reportValue().equals(rifr.getWaiyuan_reportValue())){
						session.save(updateReportFormForRemoteRecord);
					}
				}
				String sql ="update ReportItemforRemote set waiyuan_reportValue ='"+ri.getWaiyuan_reportValue()+"' where id ="+ri.getId()+" and receiveDate ='"+sdf.format(ri.getReceiveDate())+"'";
				session.createSQLQuery(sql).executeUpdate();
				
			}	
			tran.commit();
		}catch(Exception e){
			tran.rollback();
			e.printStackTrace();
			throw e;
		}finally{			
			DatabaseUtil.closeResource(session);			
		}
	}

	public void cheXiaoReportFormForRemote(String rfId) {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			ReportFormforRemote reportFormforRemote = (ReportFormforRemote)session.get(ReportFormforRemote.class, Integer.parseInt(rfId));
			String sql="";
			if(reportFormforRemote.getIsPatientOrDoctorWriteZanCun() != null && reportFormforRemote.getIsPatientOrDoctorWriteZanCun()==21){
				sql = "update ReportFormforRemote set cheXiaoFlag = 1,guiDangDate = NULL,collecter= NUll,isPatientOrDoctorWriteZanCun=20,cheXiaoTrue=1 where id ="+rfId;
			}else if(reportFormforRemote.getIsPatientOrDoctorWriteZanCun() != null && reportFormforRemote.getIsPatientOrDoctorWriteZanCun()==11){
				sql = "update ReportFormforRemote set cheXiaoFlag = 1,guiDangDate = NULL,collecter= NUll,cheXiaoTrue=1 where id ="+rfId;
			}
			session.createSQLQuery(sql).executeUpdate();
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
	
	//此处有待修改的地方
	private static ReportFormforRemote setRF(CheckReportInfoForAdd cri,ReportFormforRemote rf){
		try {
			if(cri.getReceiveDate() != null){
				rf.setReceiveDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC'Z yyyy",Locale.ENGLISH).parse(cri.getReceiveDate()));
			}
			if(cri.getSectionNo() != null){
				rf.setSectionNo(Integer.parseInt(cri.getSectionNo()));
			}
			if(cri.getSampleNo() != null){	
				rf.setSampleNo(cri.getSampleNo());
			}
			if(cri.getTestTypeNo() != null){
				rf.setTestTypeNo(Integer.parseInt(cri.getTestTypeNo()));			
			}
			if(cri.getCname() != null){
				rf.setCname(cri.getCname());
			}
			if(cri.getAge() != null){
				rf.setAge(Integer.parseInt(cri.getAge()));
			}
			if(cri.getGenderNo().equals("男")){
				rf.setGenderNo(1);
			}else if(cri.getGenderNo().equals("女")){
				rf.setGenderNo(2);
			}else{
				rf.setGenderNo(0);
			}
			if(cri.getPatNo() != null){
				rf.setPatNo(cri.getPatNo());
			}
			if(cri.getBed() != null){
				rf.setBed(cri.getBed());
			}
			
			if(cri.getDiagNo() != null && !"".equals(cri.getDiagNo())){
				rf.setDiagNo(Integer.parseInt(cri.getDiagNo()));
			}
			if(cri.getDeptNo() != null && !"".equals(cri.getDeptNo())){
				rf.setDeptNo(Integer.parseInt(cri.getDeptNo()));
			}
			if(cri.getDoctor() != null){
				rf.setDoctor(cri.getDoctor());
			}
			if(cri.getCollecter() != null){
				rf.setCollecter(cri.getCollecter());
			}
			if(cri.getSampleTypeNo() != null && !"".equals(cri.getSampleTypeNo())){
				rf.setSampleTypeNo(Integer.parseInt(cri.getSampleTypeNo()));
			}
			if(cri.getFormMemo()!= null && !"".equals(cri.getFormMemo())){
				rf.setFormMemo(cri.getFormMemo());
			}
			if(cri.getIsPatientOrDoctorWriteZanCun()!= null && !"".equals(cri.getIsPatientOrDoctorWriteZanCun())){
				rf.setIsPatientOrDoctorWriteZanCun(cri.getIsPatientOrDoctorWriteZanCun());
			}
			rf.setHospitalName(cri.getHospitalName());
			rf.setHome_foreign(cri.getHome_foreign());
			rf.setIsAdd(1);
			if(cri.getCollectDate()!= null && !"".equals(cri.getCollectDate())){
				rf.setCollectDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC'Z yyyy",Locale.ENGLISH).parse(cri.getCollectDate()));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rf;
	}

	public List<UpdateReportFormForRemoteRecord> findUpdateRecordList(String itemId) {
		Session session = null;
		Transaction tran = null;
		List<UpdateReportFormForRemoteRecord> result = new ArrayList<UpdateReportFormForRemoteRecord>();
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			String hql ="from UpdateReportFormForRemoteRecord where itemId="+itemId+" order by updateDate desc";
			List listHql = session.createQuery(hql).list();
			if(listHql != null && listHql.size() >0){
				for(int i=0;i<listHql.size();i++){
					UpdateReportFormForRemoteRecord updateReportFormForRemoteRecord = (UpdateReportFormForRemoteRecord)listHql.get(i);
					result.add(updateReportFormForRemoteRecord);
				}
			}
			tran.commit();
		} catch (Exception e) {
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return result;
	}

	public void shenHeNotPassCheckReport(CheckReportInfoForAdd cri,
			String receiveDate, int sectionNo, int testTypeNo, String sampleNo,Long patientId,Long doctorId) {
		Session session = null;
		Transaction tran = null;
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ReportFormforRemote rf = setRF(cri);
			String updateHql="update ReportFormforRemote set isPatientOrDoctorWriteZanCun=10,sheHeDate = NULL where id="+cri.getId()+
							" and receiveDate='"+sdf.format(rf.getReceiveDate())+"' and sectionNo="+sectionNo+" and testTypeNo="+testTypeNo+
							" and sampleNo='"+sampleNo+"'";
			session.createSQLQuery(updateHql).executeUpdate();
			String sql = "select rform.luRuDate,item.cname from ReportFormforRemote rform inner join" +
					" ReportItemforRemote itemF on itemF.receiveDate = rform.receiveDate and " +
					"itemF.sectionNo = rform.sectionNo and itemF.testTypeNo = rform.testTypeNo and" +
					" itemF.sampleNo = rform.sampleNo inner join TestItem item on item.itemno = itemF.paritemNo" +
					" where rform.id="+cri.getId()+" and rform.receiveDate='"+sdf.format(rf.getReceiveDate())+"'" +
					" and rform.sectionNo="+sectionNo+" and rform.testTypeNo="+testTypeNo+
					" and rform.sampleNo='"+sampleNo+"'";
			List sqlList = session.createSQLQuery(sql).list();
			if(sqlList != null && sqlList.size() > 0){
				User user = (User)session.get(User.class, doctorId);
				Object[] obj = (Object[])sqlList.get(0); 
				MemberInfo mem = (MemberInfo)session.createQuery("from MemberInfo where patient="+patientId).list().get(0);
				Sms sms = new Sms();
				Message message = new Message();    //Message object;
				sms.phone = mem.getPatient().getMobilePhone();
				String msgBuffer ="患者您好，您于"+obj[0].toString().substring(0,10)+"录入的"+obj[1]+"化验结果可能录入有误，请您认真核对后进行修改。北京佑安医院";
				sms.content = msgBuffer;
				SmsOperator smsOp = SmsOperator.getInstance();
				smsOp.sendSms("101100-SJB-HUAX-566466", "ALQPPJJI",new Sms[] { sms });
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				message.setMessageContent(msgBuffer); //set message.messageContent;
				message.setMessageDate(new Date());        //set message.messageDate;
				message.setMemberInfo(mem);             //set message.memberInfo;
				message.setUser(user);
				messDao.addMessage(message,session);
			}
			tran.commit();
		}catch(Exception e){
			if(tran != null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{			
			DatabaseUtil.closeResource(session);			
		}
	}

	public void saveAndTijiaoReportFormForRemote(CheckReportInfoForAdd cri,
			List<CheckReportListForAdd> list, String receiveDate,
			int sectionNo, int testTypeNo, String sampleNo) throws Exception {
		Session session = null;
		Transaction tran = null;
		List<ReportItemforRemote> rilist = new ArrayList<ReportItemforRemote>();
		try{
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			cri.setSectionNo( "" + sectionNo + "");
			ReportFormforRemote rf = (ReportFormforRemote)session.get(ReportFormforRemote.class, Integer.parseInt(cri.getId()));
			int attempFlag = rf.getIsPatientOrDoctorWriteZanCun();
			rf = setRF(cri,rf);
//			rf.setId(Integer.parseInt(cri.getId()));
			String pati = "from Patient where patientNo ='"+rf.getPatNo()+"'";
			Patient pat = (Patient)session.createQuery(pati).list().get(0);
			rf.setPatNo(pat.getPatientId());
			//2011-09-28 mengyuzhang start
			rf.setIsFromOutHospital(1);//区分本院与外院手动录入的化验单数据 1：表示外院 null 表示 本院，(程序导入lis中的数据)
			if(rf.getIsPatientOrDoctorWriteZanCun() == 11){
				rf.setSheHeDate(new Date());
				if(rf.getCheXiaoFlag() != null && rf.getCheXiaoFlag() == 1){
					rf.setCheXiaoTrue(2);
				}
			}else if(rf.getIsPatientOrDoctorWriteZanCun() == 21){
				if(attempFlag == 11){
					rf.setIsPatientOrDoctorWriteZanCun(11);
				}
				rf.setGuiDangDate(new Date());
			}
			//2011-09-28 mengyuzhang end
			session.update(rf);		
			
			List<ReportItemforRemote> riList = setSaveTijiaoRI(rf, cri, list,session);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(ReportItemforRemote ri : riList){
				String sql ="update ReportItemforRemote set waiyuan_reportValue ='"+ri.getWaiyuan_reportValue()+"' where receiveDate ='"+sdf.format(ri.getReceiveDate())+"'" +
				"and sectionno = "+rf.getSectionNo()+" and testtypeno="+rf.getTestTypeNo()+" " +
						"and sampleno='"+rf.getSampleNo()+"' and itemNo="+ri.getItemNo();
				session.createSQLQuery(sql).executeUpdate();
			}	
			tran.commit();
		}catch(Exception e){
			tran.rollback();
			e.printStackTrace();
			throw e;
		}finally{			
			DatabaseUtil.closeResource(session);			
		}
		
	}
}
