package com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.dao;

import com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.LabDiagnosticExamination;
import com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity.TCM4;

public interface TCMdao{
	//中医四诊
	public TCM4 TCM4_saveOrUpdate(TCM4 tcm)throws Exception;
	public TCM4 TCM4_findByCaseId(Long id)throws Exception;
	//实验室检查
	public LabDiagnosticExamination LabDiagnosticExamination_findByCaseID(Long id)throws Exception;
	public LabDiagnosticExamination LabDiagnosticExamination_saveOrUpdate(LabDiagnosticExamination lde)throws Exception;
	//复制西医病历到中医
	public void copyLiver2TCM(Long liverId,Long tcmId)throws Exception;
}
