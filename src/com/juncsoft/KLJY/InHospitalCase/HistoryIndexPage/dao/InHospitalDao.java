package com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.dao;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.InHosptialOne;
import com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity.InHosptialTwo;
/**
 * 首页接口类
 * @author 1234567890
 *
 */
public interface InHospitalDao {
	/**
	 * 初始化页面值
	 * 包含获取存储过程的值
	 * @param CaseId
	 * @return
	 * @throws Exception
	 */
	public InHosptialOne getInHospOne(Long CaseId)throws Exception;
	public InHosptialOne importInHospOne(Long CaseId)throws Exception;
	/**
	 * 初始化首页_反页面的值
	 * @param CaseId
	 * @return
	 * @throws Exception
	 */
	public InHosptialTwo getInHospTwo(Long CaseId) throws Exception;
	/**
	 * 保存或修改首页面值 
	 * @param one
	 * @return
	 * @throws Exception
	 */
	public InHosptialOne saveOrUpdateInHospOne(InHosptialOne one)throws Exception;
	/**
	 * 保存或修改首页_反页面值
	 * @param Two
	 * @return
	 * @throws Exception
	 */
	public InHosptialTwo saveOrUpdateInHospTwo(InHosptialTwo Two)throws Exception;
	
	/**
	 * 查询首页(继一[正])
	 * 包含首页基本信息(baseInfo)
	 * @param CaseId
	 * @return
	 * @throws Exception
	 */
	public JSONObject getInHospFollowOne(Long CaseId)throws Exception;
	
	/**
	 * 查询首页(继一[反])
	 * @param CaseId
	 * @return
	 * @throws Exception
	 */
	public JSONObject getInHospFollowTwo(Long CaseId)throws Exception;
	
	/**
	 * 保存或修改首页(继一[正]) 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public Boolean saveOrUpdateInHospFollowOne(JSONObject json)throws Exception;
	/**
	 * 保存或修改首页(继一[反])
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public Boolean saveOrUpdateInHospFollowTwo(JSONObject json)throws Exception;
	
	public String getAddress(String addressName)throws Exception;

}
