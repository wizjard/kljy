package com.juncsoft.KLJY.InHospitalCase.Apply.dao;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.Apply.entity.Communication;

public interface CommunicationDao {
	//查询时间 初始化下拉菜单
	public JSONArray public_mainMenu(String entityName, Long kid)throws Exception;
	//根据id查询病人的信息
	public JSONObject get_Patient_Info(Long id)throws Exception;
	//查询滨人的基本信息
	public JSONObject get_Main_Info(Long historyCaseId)throws Exception;
	//保存和修改记录
	public String saveOrUpdate(Communication entity,Long id)throws Exception;
	//删除一条信息
	public void deleteData(Communication entity)throws Exception;
	//打印记录时所需要的方法\
	public JSONObject Communication_print(Long id)throws Exception;
	//根据传入代码  查询性别 职业 籍贯 与患者关系
	public Map<String,String> getSomeMessage(String gender,String province,String relationship,String occupation)throws Exception;
}
