package com.juncsoft.KLJY.InHospitalCase.CheckReport.dao;

import java.util.List;
import java.util.Map;

import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportFormforPad;

public interface CombinationProjectDao {
	public String getCombinationProjectByAnytime(String patientId, String reportDate);
	public String getCombinationProject(String patientId, String reportDate);
	public String getCombinationProjectofOutPatient(String patientId, String reportDate);
	//liugang 2011-09-02 start 根据一次入院记录查找检验检查结果
	public String getCombinationProjectByStartTimeAndEndTime(String patientId,String kid,String reportDate);
	public List<ReportFormforPad> getReportsByPatientForPad(String patientId);
	
	/**
	 * 提供外院录入化验单我组会员查看列表
	 * @param doctorId
	 * @return
	 */
	public List<ReportFormforPad> getMyGrounpCheckReportsFor(Long doctorId, String search);
	
	/**
	 * 查询医生或者会员录入的所有的化验单(外院)
	 * @param patientId
	 * @return
	 */
	public List<ReportFormforPad> getMyGrounpCheckReportsForByMember(String patientId, String search);
	
	/**
	 * 查询医生或者会员录入的所有的化验单(外院)
	 * @param patientId
	 * @return
	 */
	public List<ReportFormforPad> getMyGrounpCheckReportsForByMemberDoctor(String deptCode, String search);
	
	/**
	 * 查询医生或者会员录入的所有的化验单(外院)
	 * @param patientId
	 * @return
	 */
	public List<ReportFormforPad> getMyGrounpCheckReportsForByMemberGuanLiYuan(String patientId, String search);
	/**
	 * ALL
	 * @param patientId
	 * @param search
	 * @return
	 */
	public List<ReportFormforPad> getMyGrounpCheckReportsForByMemberGuanLiYuanAll(String patientId, String search);
	
	public void deleteReportFormAndReportItems(int id,String receiveDate,int sectionNo, int testTypeNo,String sampleNo);
	
}
