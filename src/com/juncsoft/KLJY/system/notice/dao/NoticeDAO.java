package com.juncsoft.KLJY.system.notice.dao;

import java.util.List;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.system.notice.entity.Notice;

public interface NoticeDAO {
	public void delete(Notice n)throws Exception;
	
	public Notice saveOrUpdate(Notice n)throws Exception;
	
	public JSONObject searchByName(String keyword, String typeName, Integer start,
			Integer limit)throws Exception;
	
	public Notice findById(Long id)throws Exception;
	
	public JSONObject queryAll(Integer start, Integer limit ,String searchPara) throws Exception;
	
	public Notice save(Notice n)throws Exception;
	
	public String selectNoticeByNam(Long id)throws Exception;
	
	public List<Notice> getSixNoticeList(Integer limit,String typeName)throws Exception;
	
	public List<Notice> findNewNoticeByTime(String typeName)throws Exception;
}
