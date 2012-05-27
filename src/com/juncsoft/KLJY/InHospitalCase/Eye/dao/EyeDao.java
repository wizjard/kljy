package com.juncsoft.KLJY.InHospitalCase.Eye.dao;

import com.juncsoft.KLJY.InHospitalCase.Eye.entity.Eye;

public interface EyeDao {

	public Eye eye_findByCaseID(Long caseId) throws Exception;
	public Eye eye_SaveorUpdate(Eye eye) throws Exception;
}
