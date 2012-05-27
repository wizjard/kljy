package com.juncsoft.KLJY.system.picture.dao;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.system.picture.entity.Picture;

public interface PictureDAO {
	public void delete(Picture p)throws Exception;
	
	public Picture saveOrUpdate(Picture p)throws Exception;
	
	public JSONObject searchByName(String keyword, String typeName,Integer start,
			Integer limit)throws Exception;
	
	public Picture findById(Long id)throws Exception;
	
	public JSONObject queryAll(String typeName,Integer start, Integer limit) throws Exception;
	
	public Picture save(Picture p)throws Exception;
	
	public String selectPictureByNam(Long id)throws Exception;
	
	public String selectAllPictureName(String typeName)throws Exception;
}
