package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Apacheii;

public interface ApacheiiDao {
	/**
	 * 保存或修改
	 * 
	 * @param ap
	 * @return
	 * @throws Exception
	 */
	public Apacheii apaceSaveOrUpdate(Apacheii ap) throws Exception;

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Apacheii findApacheById(Long id) throws Exception;

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean delApacheById(Long id) throws Exception;
}
