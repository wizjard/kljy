package com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.dao;

import java.util.Map;

import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.InHospitalPage;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 病案首页Dao
 * 
 * @author Administrator
 * 
 */
public interface InHospitalPageDao {

	/**
	 * 保存或修改病案首页
	 * @param inHosp
	 * @return
	 */
	public InHospitalPage inHospSaveOrUpdate(InHospitalPage inHosp)
			throws Exception;

	/**
	 * 查询病案首页
	 * @param id
	 * @return
	 */
	public InHospitalPage findInHospByCaseId(Long id) throws Exception;

	/**
	 * 查询初始化页面初始值 存储过程
	 * @param caseid病历主ID
	 * @return 病案首页实体类
	 * @throws Exception
	 */
	public InHospitalPage findInItPageValue(Long caseid) throws Exception;
	
	/**
	 * 打印数据
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findInHospitalPrintData(Long caseId)throws Exception;
	
	/**
	 * 保存和修改病案首页续页
	 * 
	 */
	public JSONObject ContinuePage_SaveOrUpDate(JSONArray sysptom,
			JSONArray ops, JSONArray ward, JSONArray doctor,String state) throws Exception;
		
	/**
	 * 病案首页续页查询 caseId
	 */
	public JSONObject ContinuePage_FindByCaseId(Long caseId, Long patientId)
			throws Exception;
	/**
	 * 病案首页续页打印
	 * */
	public JSONObject ContinuePage_Print(Long caseId)throws Exception;
}
