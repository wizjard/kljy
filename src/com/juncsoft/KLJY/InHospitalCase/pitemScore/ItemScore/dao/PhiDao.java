package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Phi;

public interface PhiDao {
	/**
	 * 查询急诊中心急重病人综合评价(内)
	 * 
	 * @param id
	 *            表Id
	 * @return
	 * @throws Exception
	 */
	public Phi getPhiData(Long id) throws Exception;

	/**
	 * 保存或修改 急诊中心急重病人综合评价(内)
	 * 
	 * @param phi
	 * @return
	 * @throws Exception
	 */
	public Phi saveOrUpdataPhi(Phi phi) throws Exception;

	/**
	 * 删除急诊中心急重病人综合评价(内)
	 * 
	 * @param id
	 *            表Id
	 * @throws Exception
	 */
	public void delPhi(Long id) throws Exception;

}
