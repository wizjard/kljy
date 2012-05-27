package com.juncsoft.KLJY.InHospitalCase.CourseRecording.dao;

import java.io.OutputStream;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.Consultation;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DailyCourseRec;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DeathCaseDiscuss;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DeathRecord;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.InHspDied24;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.InHspRec24;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.PreviousCaseDiscuss;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.PreviousSurgerySummary;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.SurgeryRecord;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.WardRoundRec;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabExamination;

public interface CourseRecordingDao {
	//日常病程
	public DailyCourseRec findDailyCourseRecById(int id) throws Exception;
	public DailyCourseRec DailyCourseRec_saveOrUpdate(DailyCourseRec record)throws Exception;
	public DailyCourseRec DailyCourseRec_findById(Long id)throws Exception;
	public void DailyCourseRec_delete(DailyCourseRec record)throws Exception;
	public List<DailyCourseRec> DailyCourseRec_findAllByCaseID(Long id) throws Exception;
	public JSONArray DailyCourseRec_treeNodes(Long id)throws Exception;
	public Long DailyCourseRec_Lab_saveOrUpdate(Long recId,LabExamination lab) throws Exception;
	public LabExamination DailyCourseRec_Lab_findByCourseId(Long id)throws Exception;
	public void DailyCourseRec_Lab_deleteByCourseId(Long id)throws Exception;
	public String DailyCourseRec_NewpageNum_find(Long id)throws Exception;
	public void DailyCourseRec_NewpageNum_saveOrUpdate(Long id,String npCfg)throws Exception;
//	liugang update2011-05-08提高效率
	public List<DailyCourseRec> DailyCourseRec_saveOrUpdateRate(JSONArray array)throws Exception;
	//病程通用
	public JSONObject public_patientInfo(Long kid)throws Exception;
	public JSONArray public_mainMenu(String entityName,Long kid)throws Exception;
	public JSONObject public_verify(Long id,Long submiter,Long checker,int verifyStatus,String entity)throws Exception;
	//会诊记录
	public Consultation Consultation_saveOrUpdate(Consultation c)throws Exception;
	public Consultation Consultation_findById(Long id)throws Exception;
	public void Consultation_delete(Consultation c)throws Exception;
	public JSONObject Consultation_print(Long id)throws Exception;
	/*==================09-09=================*/
	public JSONObject getChiefComByCaseId(Long caseId)throws Exception;
	//术前小结
	public PreviousSurgerySummary PreviousSurgerySummary_saveOrUpdate(PreviousSurgerySummary p)throws Exception;
	public PreviousSurgerySummary PreviousSurgerySummary_findById(Long id)throws Exception;
	public void PreviousSurgerySummary_delete(PreviousSurgerySummary p)throws Exception;
	public JSONObject PreviousSurgerySummary_print(Long id)throws Exception;
	//术前病历讨论
	public PreviousCaseDiscuss PreviousCaseDiscuss_saveOrUpdate(PreviousCaseDiscuss p)throws Exception;
	public PreviousCaseDiscuss PreviousCaseDiscuss_findById(Long id)throws Exception;
	public void PreviousCaseDiscuss_delete(PreviousCaseDiscuss p)throws Exception;
	public JSONObject PreviousCaseDiscuss_print(Long id)throws Exception;
	//手术记录
	public SurgeryRecord SurgeryRecord_saveOrUpdate(SurgeryRecord s)throws Exception;
	public SurgeryRecord SurgeryRecord_findById(Long id)throws Exception;
	public void SurgeryRecord_delete(SurgeryRecord s)throws Exception;
	public JSONObject SurgeryRecord_print(Long id)throws Exception;
	//死亡记录
	public DeathRecord DeathRecord_saveOrUpdate(DeathRecord d)throws Exception;
	public DeathRecord DeathRecord_findById(Long id)throws Exception;
	public void DeathRecord_delete(DeathRecord d)throws Exception;
	public JSONObject DeathRecord_print(Long id)throws Exception;
	
	public String findInHspStatuByCaseId(Long kid)throws Exception;
	//死亡病历讨论记录
	public DeathCaseDiscuss DeathCaseDiscuss_saveOrUpdate(DeathCaseDiscuss d)throws Exception;
	public DeathCaseDiscuss DeathCaseDiscuss_findById(Long id)throws Exception;
	public void DeathCaseDiscuss_delete(DeathCaseDiscuss d)throws Exception;
	public JSONObject DeathCaseDiscuss_print(Long id)throws Exception;
	
	//24小时出入院记录
	public InHspRec24 InHspRec24_saveOrUpdate(InHspRec24 d)throws Exception;
	public InHspRec24 InHspRec24_findById(Long id)throws Exception;
	public void InHspRec24_delete(InHspRec24 d)throws Exception;
	public JSONObject InHspRec24_print(Long id)throws Exception;
	//24小时内入院死亡记录
	public InHspDied24 InHspDied24_saveOrUpdate(InHspDied24 d)throws Exception;
	public InHspDied24 InHspDied24_findById(Long id)throws Exception;
	public void InHspDied24_delete(InHspDied24 d)throws Exception;
	public JSONObject InHspDied24_print(Long id)throws Exception;
//	 日常病程打印新实现
	public JSONObject DailyCourseRec_NewPrint(Long caseId, boolean isContinued,
			String continuedNum) throws Exception;
	//查房记录
	public WardRoundRec wardRound_saveOrUpdate(WardRoundRec record)throws Exception;
	public void wardRound_Delete(WardRoundRec record)throws Exception;
	public List<WardRoundRec> wardRound_findAllByID(Long id) throws Exception;
	public JSONArray wardRound_treeNodes(Long id)throws Exception;
	public WardRoundRec wardRound_findById(Long id)throws Exception;
	//liugang 2011-08-31 start 质控一级医生必须签字
	public boolean checkSubmitCourseRecording(Long kid,Long patientId);
}
