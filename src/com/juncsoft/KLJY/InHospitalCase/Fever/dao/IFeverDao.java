package com.juncsoft.KLJY.InHospitalCase.Fever.dao;

import java.util.List;

import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverAfterTreatment;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverAssociatedSystom;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverCurrentState;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverFeverHistoryContent;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverFeverHistoryIllness;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverIncidence;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverMedicineAfterTreatment;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverMedicineCurrentState;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverMedicineFeverHistoryIllness;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverMedicineIncidence;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverNegativeSystom;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverOtherSystom;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverOthersSystom;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.TInHspFeverSystomEvolution;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.ChiefComplaint;

public interface IFeverDao {
	// 主诉
	public ChiefComplaint ChiefComplaint_findByCaseID(Long id) throws Exception;

	public ChiefComplaint ChiefComplaint_saveOrUpdate(ChiefComplaint cc)
			throws Exception;

	public void ChiefComplaint_deleteSysptom(Long id) throws Exception;
	/////////////////////////////发热中医///////////////////////////////////////////
	//发病情况添加或修改
	public TInHspFeverMedicineIncidence feverMedicineIncidence_saveOrUpdate(TInHspFeverMedicineIncidence incidence)throws Exception;
	//查找发病情况
	public TInHspFeverMedicineIncidence selectFeverMedicineIncidenceByCaseId(Long caseId) throws Exception;
	//主要症状添加或修改
	public TInHspFeverMedicineFeverHistoryIllness feverMedicineMainSysytom_saveOrUpdate(TInHspFeverMedicineFeverHistoryIllness incidence)throws Exception;
	//查找主要症状
	public TInHspFeverMedicineFeverHistoryIllness selectFeverMedicineMainSystomByCaseId(Long caseId) throws Exception;
	//诊疗经过添加或修改
	public TInHspFeverMedicineAfterTreatment feverMedicineAfterTreatment_saveOrUpdate(TInHspFeverMedicineAfterTreatment incidence)throws Exception;
	//查找诊疗经过
	public TInHspFeverMedicineAfterTreatment selectFeverMedicineAfterTreatmentByCaseId(Long caseId) throws Exception;
	//目前状况添加或修改
	public TInHspFeverMedicineCurrentState feverMedicineCurrentState_saveOrUpdate(TInHspFeverMedicineCurrentState incidence)throws Exception;
	//查找目前状况
	public TInHspFeverMedicineCurrentState selectFeverMedicineCurrentStateByCaseId(Long caseId) throws Exception;
	/**
	 * 查询发热中医的公共字典
	 */
	public List selectFeverMedicine(String name)throws Exception;
	//////////////////////////////发热西医////////////////////////////////////
	/**
	 * 保留历史信息
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverFeverHistoryContent feverHistoryContent_saveOrUpdate(TInHspFeverFeverHistoryContent fever)
	throws Exception;
	public TInHspFeverFeverHistoryContent selectFeverHistoryContentByCaseId(Long caseId,int feverType) throws Exception;
	/**
	/**
	 * 发热西医
	 * @param cc
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverFeverHistoryIllness feverHistory_saveOrUpdate(TInHspFeverFeverHistoryIllness fever)
	throws Exception;
	/**
	 * 根据病人查询病例信息
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverFeverHistoryIllness selectFeverHistoryByCaseId(Long caseId) throws Exception;
	/**
	 * 添加或修改诊疗经过
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverAfterTreatment afterTreatment_saveOrUpdate(TInHspFeverAfterTreatment afterTreatment)
	throws Exception;
	/**
	 * 添加或修改发病情况
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverAfterTreatment selectAfterTreatmentByCaseId(Long caseId)
	throws Exception;
	/**
	 * 添加或修改发病情况
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverIncidence incidence_saveOrUpdate(TInHspFeverIncidence incidence)
	throws Exception;
	/**
	 * 根据病人查询病例信息
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverIncidence selectIncidenceByCaseId(Long caseId) throws Exception;
	/**
	 * 添加或修改伴随症状
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverAssociatedSystom assocatesSystom_saveOrUpdate(TInHspFeverAssociatedSystom assocatesSystom)
	throws Exception;
	/**
	 * 根据病人查询病例信息
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverAssociatedSystom selectAssocatesSystomByCaseId(Long caseId) throws Exception;
	/**
	 * 添加或修改目前症状
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverCurrentState currentState_saveOrUpdate(TInHspFeverCurrentState currentState)
	throws Exception;
	/**
	 * 根据病人查询病例信息
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverCurrentState selectCurrentStateByCaseId(Long caseId) throws Exception;
	/**
	 * 添加或修改重要阴性症状
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverNegativeSystom negative_saveOrUpdate(TInHspFeverNegativeSystom negative)
	throws Exception;
	/**
	 * 根据病人查询病例信息
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverNegativeSystom selectNegativeByCaseId(Long caseId) throws Exception;
	/**
	 * 添加或修改其他症状
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverOthersSystom othersSystom_saveOrUpdate(TInHspFeverOthersSystom othersSystom)
	throws Exception;
	/**
	 * 根据病人查询病例信息
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverOthersSystom selectOthersSystomByCaseId(Long caseId) throws Exception;
	
	public TInHspFeverOtherSystom otherSystom_saveOrUpdate(TInHspFeverOtherSystom otherSystom)
	throws Exception;
	/**
	 * 根据病人查询病例信息
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverOtherSystom selectOtherSystomByCaseId(Long caseId) throws Exception;
	/**
	 * 添加或修改症状病情演变
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverSystomEvolution systomEvolution_saveOrUpdate(TInHspFeverSystomEvolution systomEvolution)
	throws Exception;
	/**
	 * 根据病人查询病例信息
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverSystomEvolution selectSystomEvolutionByCaseId(Long caseId) throws Exception;
}
