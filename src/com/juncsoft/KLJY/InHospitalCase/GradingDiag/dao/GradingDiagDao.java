package com.juncsoft.KLJY.InHospitalCase.GradingDiag.dao;

import java.io.OutputStream;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.GradingDiag.entity.GradingDiag;

public interface GradingDiagDao {
	public Long saveOrUpdate(GradingDiag gd) throws Exception;

	public void delete(GradingDiag gd) throws Exception;
	
//	public GradingDiag findByCaseId(Long id,String fiag) throws Exception ;
	public GradingDiag findByCaseId(Long id) throws Exception;
	public GradingDiag findByCaseId1(Long id) throws Exception;
	public GradingDiag findById(Long id) throws Exception;
	public void deleteGradingDiag(Long id) throws Exception;
	//门诊登记，肝病十二级诊断
	public GradingDiag findByCaseIdmenzhen(Long id) throws Exception;
	
	public void print(OutputStream os, JSONObject data,String rootPath) throws Exception;
	
	public JSONArray getGradings(Long caseId)throws Exception;
	//根据十二级分级查询门诊病人
	public JSONObject getMenzhenDiag(int start, int limit,GradingDiag tg) throws Exception;
	//门诊十二级打印
	public void print1(OutputStream os, JSONObject data, String rootPath) throws Exception;
	
	
	/**
	 * 十二级分级(门诊、病房) 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	 
	public JSONArray findAllGrading(String  account ) throws Exception;
	
	/**
	 * 十二级分级（门诊）
	 * @param account
	 * @return
	 * @throws Exception
	 */
	 
	public JSONArray	getGradingOutIn(Long  account ) throws Exception;
}