package com.juncsoft.KLJY.InHospitalCase.Apply.dao;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.Apply.entity.MarrowApply;

public interface MarrowApplyDao {
	//为前台的下拉菜单查询时间并显示在菜单中
	public JSONArray public_mainMenu(String entityName, Long kid)throws Exception;
	//初始化页面的时候调用该方法查询病人信息
	public JSONObject get_patient_Message(Long kid,Long id)throws Exception;
	//查询病人基本信息
	public JSONObject get_Main_Message(Long historyCaseId)throws Exception;
	//该方法用于保存和修改信息，检查id在数据库中的值，如果没有便保存，如果有就修改
	public String saveOrUpdate(MarrowApply entity,Long id,Long kid)throws Exception;
	//用于删除一条根据所传入选定记录，删除后使用病历id（historyCaseId）查询出一条新的记录显示在页面上，如果删除的记录在数据库中只有一条，那么查询基本信息
	public JSONObject deletePatientMessage(Long id,Long patientId,Long historyCaseId,MarrowApply entity)throws Exception;
	//打印记录时所需要的方法\
	public JSONObject MarrowApply_print(Long id)throws Exception;
	//查询科室到打印
	public String getOffice(String code)throws Exception;
	//根据传入代码  查询性别 职业 籍贯 与患者关系
	public Map<String,String> getSomeMessage(String gender,String province,String relationship,String occupation)throws Exception;
}
