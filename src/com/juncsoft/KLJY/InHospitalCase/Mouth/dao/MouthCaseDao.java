package com.juncsoft.KLJY.InHospitalCase.Mouth.dao;

import com.juncsoft.KLJY.InHospitalCase.Mouth.entity.PresentIllnessHistory;

public interface MouthCaseDao {
	//现病史
	public PresentIllnessHistory PresentIllnessHistory_findByCaseID(Long id)throws Exception;
	public PresentIllnessHistory PresentIllnessHistory_saveOrUpdate(PresentIllnessHistory ph)throws Exception;
	public void PresentIllnessHistory_deleteOnSet(Long id)throws Exception;
	public void PresentIllnessHistory_deleteTreat(Long id)throws Exception;
}
