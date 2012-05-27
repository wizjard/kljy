package com.juncsoft.KLJY.patientanddoctoroperator.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientHealthRecord;

/**
 * 病人健康记录管理 2011-05-31
 * 
 * @author liugang
 * 
 */
public interface PatientHealthRecordDao {
	public void savePatientHealthRecord(PatientHealthRecord patientHealthRecord,MemberInfo mem);

	public List findPatientHealthRecordByPatientId(
			Long patientId,Date currentDate,int weekFlag,Long doctorId);
	
	public Map findPatientHealthRecordCountByPatientId(Long patientId,MemberInfo mem);
	
	public void updatePatientHealthRecord(PatientHealthRecord patientHealthRecord);
	
	/**
	 * 加载病人咨询问题记录树
	 * 
	 * @param patientId
	 * @return
	 */
	public JSONArray patientHealthRecord_treeNodes(Long patientId,Date currentDate,int weekFlag,Long doctorId);
	
	/**
	 * 查找书写健康记录的病人列表
	 * @return
	 */
	public JSONObject findPatientHealthRecordList(Long doctorId,int start, int limit,int weiCha,int yiCha,JSONObject jsonMap);
	
	/**
	 * 病人登录直接查找
	 * @param patientId
	 * @param currentDate
	 * @param weekFlag
	 * @return
	 */
	public List findPatientHealthRecordByPatient(Long patientId,Date currentDate,int weekFlag);
	
	/**
	 * 病人直接查找所有的健康记录树
	 * @param patientId
	 * @param currentDate
	 * @param weekFlag
	 * @return
	 */
	public JSONArray patientHealthRecord_treeNodesByPatient(Long patientId,Date currentDate,int weekFlag);
	
	/**
	 * 提醒医生是否有未查的健康记录
	 * @param doctorId
	 * @param start
	 * @param limit
	 * @param weiCha
	 * @param yiCha
	 * @param jsonMap
	 * @return
	 */
	public int findCountPatientHealthRecordList(Long doctorId,int weiCha,int yiCha,JSONObject jsonMap);
	
	/**
	 * 提醒会员是否有未看的查房记录
	 * @param patientId
	 * @param currentDate
	 * @param weekFlag
	 * @return
	 */
	public int findCountPatientHealthRecordByPatient(Long patientId);
	
	/**
	 * 查看历史健康记录
	 * @param patientId
	 * @param doctorId
	 * @return
	 */
	public List findPatientHealthRecordHistory(Long patientId,Long doctorId);
}
