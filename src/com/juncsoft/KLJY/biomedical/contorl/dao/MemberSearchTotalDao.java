package com.juncsoft.KLJY.biomedical.contorl.dao;


import java.io.OutputStream;

import net.sf.json.JSONObject;


/**
 * 会员查询统计
 * @author lixingli
 *
 */
@SuppressWarnings("unchecked")
public interface MemberSearchTotalDao {
	public JSONObject searchMemberByCondition(int start, int limit)throws Exception ;
	public JSONObject superviseMember(Integer start, Integer limit) throws Exception;
	public void exportDataToExcel(OutputStream out)throws Exception;
	public void exportSearchDataToExcel(OutputStream out)throws Exception ;
	
	//liugang内网全院会员明细表
	public JSONObject searchInMemberByCondition(int start, int limit, String search)throws Exception;
	public JSONObject superviseInMember(Integer start, Integer limit)throws Exception;
	public void exportSearchInDataToExcel(OutputStream out) throws Exception;
	public void exportsuperviseInMemberExcel(OutputStream out) throws Exception;
}
