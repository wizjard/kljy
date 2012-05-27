package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Ts;

public interface TsDao {
	/**
	 * 保存
	 * 
	 * @param ts
	 * @return
	 * @throws Exception
	 */
	public Ts tsSaveOrUpdate(Ts ts) throws Exception;

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Ts findTsById(Long id) throws Exception;

	/**
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleTsById(Long id) throws Exception;
}
