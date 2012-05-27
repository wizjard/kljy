package com.juncsoft.KLJY.InHospitalCase.aids.dao;

import com.juncsoft.KLJY.InHospitalCase.aids.entity.AidsHistory;

public interface AidsHistoryDao {

	String getAidsHistoryTree() throws Exception;
	AidsHistory findContent(Long caseId) throws Exception;
	Long saveOrUpdateContent(AidsHistory aidsHistory) throws Exception;
}
