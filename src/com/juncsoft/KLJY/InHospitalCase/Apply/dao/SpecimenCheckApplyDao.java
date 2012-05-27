package com.juncsoft.KLJY.InHospitalCase.Apply.dao;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.Apply.entity.SpecimenCheckApply;

public interface SpecimenCheckApplyDao {
	//查询时间 初始化下拉菜单
	public JSONArray public_mainMenu(String entityName, Long kid)throws Exception;
	
	// 查询病人的基本信息
	public JSONObject get_Main_Info(Long historyCaseId) throws Exception;

	// 查询出申请单相关信息
	public JSONObject get_Patient_Info(Long id) throws Exception;
	
	//添加和修改信息
	public String saveOrUpdate(SpecimenCheckApply entity,Long id)throws Exception;
	
	//删除一条信息
	public void deleteData(SpecimenCheckApply entity)throws Exception;
	//打印记录时所需要的方法\
	public JSONObject SpecimenCheckApply_print(Long id)throws Exception;
	//查询科室到打印
	public String getOffice(String code)throws Exception;
	
	//根据传入代码  查询性别 职业 籍贯 与患者关系
	public Map<String,String> getSomeMessage(String gender,String province,String relationship,String occupation)throws Exception;
}
