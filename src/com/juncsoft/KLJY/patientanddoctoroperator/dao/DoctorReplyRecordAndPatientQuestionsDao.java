package com.juncsoft.KLJY.patientanddoctoroperator.dao;

import java.util.List;

import com.juncsoft.KLJY.patientanddoctoroperator.entity.DoctorReplyRecordAndPatientQuestions;

/**
 * 医生回复和病人提问管理
 * 2011-05-31
 * @author liugang
 *
 */
public interface DoctorReplyRecordAndPatientQuestionsDao {

	public List<DoctorReplyRecordAndPatientQuestions> findAllDoctorReplyRecordAndPatientQuestionsByPCId(Long pcId);
	
	public DoctorReplyRecordAndPatientQuestions saveDoctorReplyRecordAndPatientQuestions(DoctorReplyRecordAndPatientQuestions doctorReplyRecordAndPatientQuestions);
	
	public boolean cancelMessageByDoctorOperation(Long pcId);
}
