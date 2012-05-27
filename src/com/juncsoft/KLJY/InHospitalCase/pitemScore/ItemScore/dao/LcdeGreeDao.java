package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Lcdegree;

public interface LcdeGreeDao {
	/**
	 * 保存或修改
	 * 
	 * @param lc
	 * @return
	 * @throws Exception
	 */
	public Lcdegree lcSaveOrUpdate(Lcdegree lc) throws Exception;

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Lcdegree findLcdeById(Long id) throws Exception;

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean deleLcById(Long id) throws Exception;
}
