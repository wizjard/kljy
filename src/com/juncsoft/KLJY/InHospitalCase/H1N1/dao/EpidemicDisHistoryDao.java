package com.juncsoft.KLJY.InHospitalCase.H1N1.dao;

import com.juncsoft.KLJY.InHospitalCase.H1N1.entity.H1N1EpidemicDisHistory;

public interface EpidemicDisHistoryDao {

	public H1N1EpidemicDisHistory findEpidemicDisHistoryByCaseId(Long caseId) throws Exception;
	public H1N1EpidemicDisHistory saveOrUpdateEpidemicDisHistoryByCaseId(H1N1EpidemicDisHistory hedh) throws Exception;
}
