package com.juncsoft.KLJY.InHospitalCase.ENT.dao;

import com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistoryENT;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.PresentIllnessHistoryENTDetails;
import com.juncsoft.KLJY.InHospitalCase.ENT.entity.SpecialExamination;

public interface ENTCaseDao{
	public SpecialExamination SpecialExamination_findByCaseId(Long id)throws Exception;
	public SpecialExamination SpecialExamination_saveOrUpdate(SpecialExamination se)throws Exception;
	public PresentIllnessHistoryENT PresentIllness_findByCaseId(Long id)throws Exception;
	public PresentIllnessHistoryENT PresentIllness_saveOrUpdate(PresentIllnessHistoryENT pi)throws Exception;
	public void PresentIllness_deleteDetails(PresentIllnessHistoryENTDetails detail)throws Exception;
	public PresentIllnessHistoryENTDetails PresentIllnessDetails_findById(Long id)throws Exception;
	//---------------------------------------------现病史---------------------------------------------
	/**
	 * 查询现病史
	 * @param id
	 * @return 
	 * @throws Exception
	 */
	public PresentIllnessHistory getPrentByCaseId(Long id) throws Exception;
	/**
	 * 保存或修改现交史
	 * @param pre
	 * @return
	 * @throws Exception
	 */
	public PresentIllnessHistory prentSaveOrUpdate(PresentIllnessHistory pre)throws Exception;
	/**
	 * 删除起病史
	 * @param id
	 * @throws Exception
	 */
	public void delePreOnsetsById(Long id) throws Exception;
}