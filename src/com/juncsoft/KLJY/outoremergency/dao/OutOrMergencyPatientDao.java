package com.juncsoft.KLJY.outoremergency.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyCase;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyMiddlePatient;
import com.juncsoft.KLJY.patient.entity.Patient;

/**
 * 加载当前医生所在的一个科室下的所有当天的门诊病人
 * @author liugang
 */
public interface OutOrMergencyPatientDao {
	//执行当前医生所在的一个科室下的所有当天的门诊病人存储过程
	public JSONObject getOutOrMergencyMiddlePatientList(Integer start, Integer limit,String deptcode,String drcode,String gbrep,String yizhuDate);
	
	//执行looksearch用户的查看提供给HIS中绑定查看电子病历
	public Patient executeHISPatientNameByIdnumber(String idnumber);
	
	//执行单条病人存储过程(门诊)
	public String executeHISPatientByHISPatientid(String patientid,String actdate,String regno,String deptcode,String deptname);
	
	//单表头信息加载门诊信息
	public String getOutOrMergencyPatientPageInfo(String patientId);
	
	public JSONObject findAllOutPatients(String deptCode,Integer start, Integer limit);
	
	public JSONObject searchByNameOrNo(String keyword,Integer start,Integer limit) throws Exception;
	
	//lxl
	public OutOrMergencyCase findById(Long id) throws Exception;
	
	/**
	 * 查找会员在HIS中的门诊记录  2011-08-06新增
	 * @param id
	 * @param patientid
	 * @return
	 */
	public List<OutOrMergencyCase> executeOnePatientOutCase(Long id,String patientid);
	
	//执行当前医生所在的一个科室下的所有当天的门诊病人存储过程
	public JSONObject executeOnePatientOutCaseListByHis(Integer start, Integer limit,String flag,String searchCondition);
	
	//执行单条病人存储过程(门诊)
//	public String executeHISPatientByHISPatientidSearch(String patientid,String actdate,String regno,String deptcode);
	public void executeYiZhou();
	
	
}
