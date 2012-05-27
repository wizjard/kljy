package com.juncsoft.KLJY.InHospitalCase.BabyRec.dao;

import java.util.List;

import com.juncsoft.KLJY.InHospitalCase.BabyRec.entity.BabyCourseRec;
import com.juncsoft.KLJY.InHospitalCase.BabyRec.entity.BabyRec;
import com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity.DailyCourseRec;
import com.juncsoft.KLJY.InHospitalCase.Liver.entity.LabExamination;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface BabyRecDao {

	public List<BabyRec> getBabyInfo(int pid, int pcid);
	public void saveBabyInfo(BabyRec babyRec, int hasSave) throws Exception;
	public long getBabyCount(int pid, int pcid);
	
	//日常病程
	public BabyCourseRec DailyCourseRec_saveOrUpdate(BabyCourseRec record)throws Exception;
	public BabyCourseRec DailyCourseRec_findById(Long id)throws Exception;
	public void DailyCourseRec_delete(BabyCourseRec record)throws Exception;
	public List<BabyCourseRec> DailyCourseRec_findAllByCaseID(Long id) throws Exception;
	public JSONArray DailyCourseRec_treeNodes(Long id)throws Exception;
	public Long DailyCourseRec_Lab_saveOrUpdate(Long recId,LabExamination lab) throws Exception;
	public LabExamination DailyCourseRec_Lab_findByCourseId(Long id)throws Exception;
	public void DailyCourseRec_Lab_deleteByCourseId(Long id)throws Exception;
	public String DailyCourseRec_NewpageNum_find(Long id)throws Exception;
	public void DailyCourseRec_NewpageNum_saveOrUpdate(Long id,String npCfg)throws Exception;
	//病程通用
	public JSONObject public_patientInfo(Long kid)throws Exception;
	public JSONArray public_mainMenu(String entityName,Long kid)throws Exception;
	public JSONObject public_verify(Long id,Long submiter,Long checker,int verifyStatus,String entity)throws Exception;
	//liugang 2011-08-31 start 质控一级医生必须签字
//	public boolean checkSubmitCourseRecording(Long kid,Long patientId);
}
