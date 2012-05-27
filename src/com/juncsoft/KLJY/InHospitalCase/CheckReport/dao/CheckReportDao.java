package com.juncsoft.KLJY.InHospitalCase.CheckReport.dao;

import java.util.List;

import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportInfo;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportInfoForAdd;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportList;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.CheckReportListForAdd;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportFormforRemote;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.TestItem;
import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.UpdateReportFormForRemoteRecord;
import com.juncsoft.KLJY.patient.entity.Patient;

public interface CheckReportDao {

	public List<TestItem> getSubCName(String combinationCName);
	public List<CheckReportList> getCheckReportList(String receiveDate, String sectionNo, String testTypeNo, String sampleNo, String parItemNo);
	public List<CheckReportList> getCheckReportListLu(String receiveDate, String sectionNo, String testTypeNo, String sampleNo, String parItemNo);
	public CheckReportInfo getCheckReportInfo(String patientId, String receiveDate, String sectionNo, String testTypeNo, String sampleNo);
	public Patient getCheckReportInfoForAdd(String patientId);
	public String getSampleTypeName(int sampleTypeNo);
	public String getDepartmentByPatId(int patId);
	public ReportFormforRemote saveCheckReport(CheckReportInfoForAdd cri, List<CheckReportListForAdd> list) throws Exception;
	public void updateCheckReport(CheckReportInfoForAdd cri, List<CheckReportListForAdd> list, String receiveDate, int sectionNo, int testTypeNo, String sampleNo) throws Exception;
	public void updateCheckReportNoDelete(CheckReportInfoForAdd cri, List<CheckReportListForAdd> list, String receiveDate, int sectionNo, int testTypeNo, String sampleNo)throws Exception;
	
	public void cheXiaoReportFormForRemote(String rfId);
	
	/**
	 * 查找修改历史记录
	 * @param itemId
	 * @return
	 */
	public List<UpdateReportFormForRemoteRecord> findUpdateRecordList(String itemId);
	
	/**
	 * 审核不通过
	 * @param cri
	 * @param receiveDate
	 * @param sectionNo
	 * @param testTypeNo
	 * @param sampleNo
	 */
	public void shenHeNotPassCheckReport(CheckReportInfoForAdd cri,String receiveDate, int sectionNo, int testTypeNo, String sampleNo,Long patientId,Long doctorId);
	
	public void saveAndTijiaoReportFormForRemote(CheckReportInfoForAdd cri, List<CheckReportListForAdd> list, String receiveDate, int sectionNo, int testTypeNo, String sampleNo)throws Exception;
}
