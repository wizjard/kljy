package com.juncsoft.KLJY.InHospitalCase.Default.dao;

import com.juncsoft.KLJY.InHospitalCase.Default.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.InHospitalCase.Default.entity.SpecialExamination;

public interface DefaultCaseDao{
	public PresentIllnessHistory pi_saveOrUpdate(PresentIllnessHistory pi)throws Exception; 
	public PresentIllnessHistory pi_findByCaseId(Long id)throws Exception;
	public SpecialExamination se_saveOrUpdate(SpecialExamination se)throws Exception;
	public SpecialExamination se_findByCaseId(Long id)throws Exception;
}