package com.juncsoft.KLJY.checkreport.dao;

import java.util.List;
import java.util.Map;

import com.juncsoft.KLJY.InHospitalCase.CheckReport.entity.ReportFormforPad;
import com.juncsoft.KLJY.checkreport.entity.Maidi;
import com.juncsoft.KLJY.checkreport.entity.Pacs;

public interface CheckReportDao {
	public Long addPacsReport(Pacs report) throws Exception;
	public void deletePacsReport(Pacs report) throws Exception;
	public void updatePacsReport(Pacs report) throws Exception;
	public Pacs getPacsReport(Long pacsId) throws Exception;
	public Maidi getMaidiReport(Long pacsId) throws Exception;
	public List<Pacs> getPacsReportsByPatient(String patientId) throws Exception;
	public List<Maidi> getMaidiReportsByPatient(String patientId) throws Exception;
	
	public List getMaidiReportsByPatientInThirtyDays(String patientId,String beforeThirtyDays,String today) throws Exception;
	public List getPacsReportsByPatientInThirtyDays(String patientId,String beforeThirtyDays,String today) throws Exception;
	public List getCheckReportsByPatientInThirtyDays(String patientNo,String beforeThirtyDays,String today) throws Exception;
	
	/**
	 * 外院新增pacs记录
	 */
	public void savePacs(Pacs pacs);
	/**
	 * 外院新增Maidi记录
	 */
	public void saveMaidi(Maidi maidi);
	
	/**
	 * 
	 */
	public List<Map> getMyGrounpCheckReportsForByMember(String patientId,String search);
	
	public List<Map> getMyGrounpCheckReportsForByMemberMaiDi(String patientId,String search);
	
	public Map getPacsByPatientId(Long pacsId,String patientId);
	
	public Map getMaiDiByPatientId(Long pacsId,String patientId);
	
	
	public void updatePacs(Pacs pacs);
	
	public void savePacsT(Pacs pacs);
	
	public void saveMaidiT(Maidi maidi);
	
	public void updatePacsT(Pacs pacs);
	
	public void updateMaidiT(Maidi maidi);
	
	/**
	 * 提供外院录入化验单我组会员查看列表
	 * @param doctorId
	 * @return
	 */
	public List<Map> getMyGrounpCheckReportsFor(Long doctorId, String search);
	
	public List<Map> getMyGrounpCheckReportsForMaiDi(Long doctorId, String search);
	
	public void shenHeNotPassCheckReport(Long pacsId,Long patientId,Long doctorId);
	
	public void shenHeNotMaiDiCheckReport(Long pacsId,Long patientId,Long doctorId);
	
	public List<Map> getMyGrounpCheckReportsForByMemberGuanLiYuanAll(String patientId, String search);
	public List<Map> getMyGrounpCheckReportsForByMemberGuanLiYuanAllMaiDi(String patientId, String search);
	
	public void cheXiaoPacs(String rfId);
	
	public void cheXiaoMaidi(String rfId);
	
	/**
	 * 查询医生或者会员录入的所有的化验单(外院)
	 * @param patientId
	 * @return
	 */
	public List<Map> getMyGrounpCheckReportsForByMemberDoctor(String deptCode, String search);
	/**
	 * 查询医生或者会员录入的所有的化验单(外院)
	 * @param patientId
	 * @return
	 */
	public List<Map> getMyGrounpCheckReportsForByMemberDoctorMaiDi(String deptCode, String search);
	
	/**
	 * 查询医生或者会员录入的所有的化验单(外院)
	 * @param patientId
	 * @return
	 */
	public List<Map> getMyGrounpCheckReportsForByMemberGuanLiYuan(String patientId, String search);
	
	public List<Map> getMyGrounpCheckReportsForByMemberGuanLiYuanMaiDi(String patientId, String search);
	
	public void deletepacs(Long pacsId);
	
	public void deleteMaidi(Long pacsId);
	
	
	/**
	 * 直接提交
	 * @param pacs
	 */
	public void updatePacsTi(Pacs pacs);
	

	/**
	 * 直接提交
	 * @param pacs
	 */
	public void updateMaidiTi(Maidi maidi);
	
}
