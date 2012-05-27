package com.juncsoft.KLJY.InHospitalCase.Liver.dao;

import java.util.List;

import org.hibernate.Session;

import net.sf.json.JSONArray;

import com.juncsoft.KLJY.InHospitalCase.Liver.entity.ChiefComplaint;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.Diagnosis;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.EpidemicDisHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.FamilyHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabDiagnosticExamination;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MarritalChildbearingHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.MenstrualHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.OutHspRec;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.OutHspRecYiZhu;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PastHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PersonalHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PhysicalExamination;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.PresentIllnessHistory;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.RevisedDiagnosis;

public interface LiverCaseDao {
	//主诉
	public ChiefComplaint ChiefComplaint_findByCaseID(Long id)throws Exception;
	public ChiefComplaint ChiefComplaint_saveOrUpdate(ChiefComplaint cc)throws Exception;
	public void ChiefComplaint_deleteSysptom(Long id)throws Exception;
	//流行病史
	public EpidemicDisHistory EpidemicDis_findByCaseID(Long id)throws Exception;
	public EpidemicDisHistory EpidemicDis_saveOrUpdate(EpidemicDisHistory edh)throws Exception;
	//现病史
	public PresentIllnessHistory PresentIllnessHistory_findByCaseID(Long id)throws Exception;
	public PresentIllnessHistory PresentIllnessHistory_saveOrUpdate(PresentIllnessHistory ph)throws Exception;
	public void PresentIllnessHistory_deleteOnSet(Long id)throws Exception;
	public void PresentIllnessHistory_deleteTreat(Long id)throws Exception;
	//既往史
	public PastHistory PastHistory_findByCaseID(Long id)throws Exception;
	public PastHistory PastHistory_saveOrUpdate(PastHistory ph)throws Exception;
	public void PastHistory_delete(Long id,String flag)throws Exception;
	//个人史
	public PersonalHistory PersonalHistory_findByCaseID(Long id)throws Exception;
	public PersonalHistory PersonalHistory_saveOrUpdate(PersonalHistory ph)throws Exception;
	//月经史
	public MenstrualHistory MenstrualHistory_findByCaseID(Long id)throws Exception;
	public MenstrualHistory MenstrualHistory_saveOrUpdate(MenstrualHistory mh)throws Exception;
	//婚育史
	public MarritalChildbearingHistory MarritalChildbearingHistory_findByCaseID(Long id)throws Exception;
	public MarritalChildbearingHistory MarritalChildbearingHistory_saveOrUpdate(MarritalChildbearingHistory mh)throws Exception;
	//家族史
	public FamilyHistory FamilyHistory_findByCaseID(Long id)throws Exception;
	public FamilyHistory FamilyHistory_saveOrUpdate(FamilyHistory fh)throws Exception;
	//体格检查
	public PhysicalExamination PhysicalExamination_findByCaseID(Long id)throws Exception;
	public PhysicalExamination PhysicalExamination_saveOrUpdate(PhysicalExamination fe)throws Exception;
	//诊断
	public Diagnosis Diagnosis_findByCaseID(Long id)throws Exception;
	public Diagnosis Diagnosis_saveOrUpdate(Diagnosis diag)throws Exception;
	//订正诊断
	public RevisedDiagnosis RevisedDiagnosis_findByCaseID(Long id)throws Exception;
	public RevisedDiagnosis RevisedDiagnosis_saveOrUpdate(RevisedDiagnosis diag)throws Exception;
	//实验室检查
	public LabDiagnosticExamination LabDiagnosticExamination_findByCaseID(Long id)throws Exception;
	public LabDiagnosticExamination LabDiagnosticExamination_saveOrUpdate(LabDiagnosticExamination lde)throws Exception;
	//出院记录
	public OutHspRec OutHspRec_findByCaseID(Long id)throws Exception;
	public OutHspRec OutHspRec_saveOrUpdate(OutHspRec out)throws Exception;
	public OutHspRec OutHspRec_nullInit(Long id)throws Exception;
	//出院记录医嘱模板
	public List<OutHspRecYiZhu> OutHspRecYiZhu_findByCaseID(Long id)throws Exception;
	public void OutHspRecYiZhu_delete(OutHspRecYiZhu yizhu)throws Exception;
	public OutHspRecYiZhu OutHspRecYiZhu_saveOrUpdate(OutHspRecYiZhu yizhu)throws Exception;
	
	public JSONArray getTreatResult_Dict(String id) throws Exception;
	//liugang 2011-08-31 start 质控一级医生必须签字
	public boolean checkSubmitCase(Long kid);
	
	/**
	 * liugang 2011-12-21 入会复制新增该患者在入院期间的某一次病历
	 */
	public void memberCaseCopyLiverCaseByCaseId(Long id,Session session);
}
