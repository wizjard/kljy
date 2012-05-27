package com.juncsoft.KLJY.InHospitalCase.Surgery.dao;

import com.juncsoft.KLJY.InHospitalCase.Surgery.entity.SpecialExamination;

public interface SurgeryCaseDao{
	public SpecialExamination se_saveOrUpdate(SpecialExamination se)throws Exception;
	public SpecialExamination se_findByCaseId(Long id)throws Exception;
}