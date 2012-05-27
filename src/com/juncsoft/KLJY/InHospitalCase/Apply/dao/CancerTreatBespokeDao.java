package com.juncsoft.KLJY.InHospitalCase.Apply.dao;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.Apply.entity.CancerTreatBespoke;

/**
 * 肿瘤肝胆介入治疗中心诊治疗预约单Interface
 * */
public interface CancerTreatBespokeDao {
	
	//为前台的下拉菜单查询时间并显示在菜单中
	public JSONArray public_mainMenu(String entityName, Long kid)throws Exception;
	//根据id查询一条信息到前台
	public JSONObject get_Patient_Info(Long id)throws Exception;
	//查询病人的基本信息显示到页面
	public JSONObject get_Main_Info(long kid)throws Exception;
	//该方法保存新增信息或根据相关id修改指定的一条信息
	public String saveOrUpdate(CancerTreatBespoke entity,Long Id,Long patientId)throws Exception;
	//删除一条信息
	public void deleteData(CancerTreatBespoke entity)throws Exception;
	//打印记录时所需要的方法\
	public JSONObject CancerTreatBespoke_print(Long id)throws Exception;
	//查询科室
	public String getOffice(String code)throws Exception;
	//根据传入代码  查询性别 职业 籍贯 与患者关系
	public Map<String,String> getSomeMessage(String gender,String province,String relationship,String occupation)throws Exception;

}
