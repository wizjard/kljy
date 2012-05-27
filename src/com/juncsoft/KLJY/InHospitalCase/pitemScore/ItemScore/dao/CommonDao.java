package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.dao;

import java.util.List;

import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.LFunction;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Table;

public interface CommonDao {
	public Integer saveOrUpdate(Table table) throws Exception;

	public void delete(Table table) throws Exception;

	public List<Table> findByPatientID(Table table, Integer PatientID)
			throws Exception;

	public Table findByID(Table table) throws Exception;

	public Patient findPatientByID(Integer patientId) throws Exception;

	/**
	 * 读取肝病功能评估表
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public LFunction getLfun(Long id) throws Exception;

	/**
	 * 保存或修改肝病功能评估
	 * 
	 * @param lfun
	 * @return
	 * @throws Exception
	 */
	public LFunction saveOrUpdateLfunction(LFunction lfun) throws Exception;

	/**
	 * 删除肝病功能评估
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Integer deleLfunction(Long id) throws Exception;
}
