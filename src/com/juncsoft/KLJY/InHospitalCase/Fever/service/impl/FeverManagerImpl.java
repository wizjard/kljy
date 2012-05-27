package com.juncsoft.KLJY.InHospitalCase.Fever.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.juncsoft.KLJY.InHospitalCase.Fever.dao.IFeverDao;
import com.juncsoft.KLJY.InHospitalCase.Fever.dao.impl.feverDaoImpl;
import com.juncsoft.KLJY.InHospitalCase.Fever.entity.SysDictModules;
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
import com.juncsoft.KLJY.InHospitalCase.Fever.service.IFeverManager;

public class FeverManagerImpl implements IFeverManager {
/////////////////////////////发热中医///////////////////////////////////////////
	//发病情况添加或修改
	public TInHspFeverMedicineIncidence feverMedicineIncidence_saveOrUpdate(TInHspFeverMedicineIncidence incidence)throws Exception{
		TInHspFeverMedicineIncidence incidences=feverDao.feverMedicineIncidence_saveOrUpdate(incidence);
		if(incidences != null){
			return incidences;			
		}else{			
			return null;
		}
	}
	//查找发病情况
	public TInHspFeverMedicineIncidence selectFeverMedicineIncidenceByCaseId(Long caseId) throws Exception{
		TInHspFeverMedicineIncidence fever= feverDao.selectFeverMedicineIncidenceByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	//主要症状添加或修改
	public TInHspFeverMedicineFeverHistoryIllness feverMedicineMainSysytom_saveOrUpdate(TInHspFeverMedicineFeverHistoryIllness incidence)throws Exception{
		TInHspFeverMedicineFeverHistoryIllness incidences=feverDao.feverMedicineMainSysytom_saveOrUpdate(incidence);
		if(incidences != null){
			return incidences;			
		}else{			
			return null;
		}
	}
	//查找主要症状
	public TInHspFeverMedicineFeverHistoryIllness selectFeverMedicineMainSystomByCaseId(Long caseId) throws Exception{
		TInHspFeverMedicineFeverHistoryIllness fever= feverDao.selectFeverMedicineMainSystomByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	//诊疗经过添加或修改
	public TInHspFeverMedicineAfterTreatment feverMedicineAfterTreatment_saveOrUpdate(TInHspFeverMedicineAfterTreatment incidence)throws Exception{
		TInHspFeverMedicineAfterTreatment incidences=feverDao.feverMedicineAfterTreatment_saveOrUpdate(incidence);
		if(incidences != null){
			return incidences;			
		}else{			
			return null;
		}
	}
	//查找诊疗经过
	public TInHspFeverMedicineAfterTreatment selectFeverMedicineAfterTreatmentByCaseId(Long caseId) throws Exception{
		TInHspFeverMedicineAfterTreatment fever= feverDao.selectFeverMedicineAfterTreatmentByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	//目前状况添加或修改
	public TInHspFeverMedicineCurrentState feverMedicineCurrentState_saveOrUpdate(TInHspFeverMedicineCurrentState incidence)throws Exception{
		TInHspFeverMedicineCurrentState incidences=feverDao.feverMedicineCurrentState_saveOrUpdate(incidence);
		if(incidences != null){
			return incidences;			
		}else{			
			return null;
		}
	}
	//查找目前状况
	public TInHspFeverMedicineCurrentState selectFeverMedicineCurrentStateByCaseId(Long caseId) throws Exception{
		TInHspFeverMedicineCurrentState incidences=feverDao.selectFeverMedicineCurrentStateByCaseId(caseId);
		if(incidences != null){
			return incidences;			
		}else{			
			return null;
		}
	}
	
	/**
	 * 发热中医查询公共字典信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<SysDictModules> selectFeverMedicine(String name)throws Exception{
		List<SysDictModules> list=feverDao.selectFeverMedicine(name);
		if(list != null && list.size()>0){
			return list;			
		}
		else {
			return null;
		}
	}
	public TInHspFeverFeverHistoryContent feverHistoryContent_saveOrUpdate(TInHspFeverFeverHistoryContent fever)
	throws Exception{
		TInHspFeverFeverHistoryContent feverHistoryContent=feverDao.feverHistoryContent_saveOrUpdate(fever);
		if(feverHistoryContent != null){
			return feverHistoryContent;			
		}else{
			
			return null;
		}
	}
	public TInHspFeverFeverHistoryContent selectFeverHistoryContentByCaseId(Long caseId,int feverType) throws Exception{
		TInHspFeverFeverHistoryContent feverContene= feverDao.selectFeverHistoryContentByCaseId(caseId,feverType);
		if(feverContene != null){
			return feverContene;
		}else {
			return null;
		}
	}
	/**
	 * 
	 * 添加或修改发热现病史信息
	 */
	private IFeverDao feverDao=new feverDaoImpl();
	public TInHspFeverFeverHistoryIllness feverHistory_saveOrUpdate(
			TInHspFeverFeverHistoryIllness fever) throws Exception {
		TInHspFeverFeverHistoryIllness feverHistory=feverDao.feverHistory_saveOrUpdate(fever);
		if(feverHistory != null){
			return feverHistory;			
		}else{
			
			return null;
		}
		
	}
	/**
	 * 添加或修改发病情况
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverIncidence incidence_saveOrUpdate(TInHspFeverIncidence incidence)
	throws Exception{
		TInHspFeverIncidence incidences=feverDao.incidence_saveOrUpdate(incidence);
		if(incidences != null){
			return incidences;			
		}else{			
			return null;
		}
	}
	/**
	 * 添加或修改诊疗经过
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverAfterTreatment afterTreatment_saveOrUpdate(TInHspFeverAfterTreatment afterTreatment)
	throws Exception{
		TInHspFeverAfterTreatment afterTreatments=feverDao.afterTreatment_saveOrUpdate(afterTreatment);
		if(afterTreatments != null){
			return afterTreatments;			
		}else{			
			return null;
		}
	}
	
	/**
	 * 添加或修改伴随症状
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverAssociatedSystom assocatesSystom_saveOrUpdate(TInHspFeverAssociatedSystom assocatesSystom)
	throws Exception{
		TInHspFeverAssociatedSystom assocatesSystoms=feverDao.assocatesSystom_saveOrUpdate(assocatesSystom);
		if(assocatesSystoms != null){
			return assocatesSystoms;			
		}else{			
			return null;
		}
		
	}
	/**
	 * 添加或修改目前症状
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverCurrentState currentState_saveOrUpdate(TInHspFeverCurrentState currentState)
	throws Exception{
		TInHspFeverCurrentState currentStates=feverDao.currentState_saveOrUpdate(currentState);
		if(currentStates != null){
			return currentStates;			
		}else{			
			return null;
		}
	}
	/**
	 * 添加或修改重要阴性症状
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverNegativeSystom negative_saveOrUpdate(TInHspFeverNegativeSystom negative)
	throws Exception{
		TInHspFeverNegativeSystom negatives=feverDao.negative_saveOrUpdate(negative);
		if(negatives != null){
			return negatives;			
		}else{			
			return null;
		}
	}
	/**
	 * 添加或修改其他症状
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverOthersSystom othersSystom_saveOrUpdate(TInHspFeverOthersSystom othersSystom)
	throws Exception{
		TInHspFeverOthersSystom othersSystoms=feverDao.othersSystom_saveOrUpdate(othersSystom);
		if(othersSystoms != null){
			return othersSystoms;			
		}else{			
			return null;
		}
	}
	/**
	 * 添加或修改次要症状
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverOtherSystom otherSystom_saveOrUpdate(TInHspFeverOtherSystom otherSystom)
	throws Exception{
		TInHspFeverOtherSystom otherSystoms=feverDao.otherSystom_saveOrUpdate(otherSystom);
		if(otherSystoms != null){
			return otherSystoms;			
		}else{			
			return null;
		}
	}
	/**
	 * 添加或修改症状病情演变
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverSystomEvolution systomEvolution_saveOrUpdate(TInHspFeverSystomEvolution systomEvolution)
	throws Exception{
		TInHspFeverSystomEvolution systomEvolutions=feverDao.systomEvolution_saveOrUpdate(systomEvolution);
		if(systomEvolutions != null){
			return systomEvolutions;			
		}else{			
			return null;
		}
	}
	
	
	/**
	 * 根据病人查询病例信息（主要症状）
	 */
	public TInHspFeverFeverHistoryIllness selectFeverHistoryByCaseId(Long caseId)
			throws Exception {
		
		TInHspFeverFeverHistoryIllness fever= feverDao.selectFeverHistoryByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	/**
	 * 添加或修改发病情况
	 * @param fever
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverAfterTreatment selectAfterTreatmentByCaseId(Long caseId)
	throws Exception{
		TInHspFeverAfterTreatment fever= feverDao.selectAfterTreatmentByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	/**
	 * 根据病人查询病例信息
	 * @param caseId
	 * @return
	 * @throws Exception
	 */
	public TInHspFeverIncidence selectIncidenceByCaseId(Long caseId) throws Exception{
		TInHspFeverIncidence fever= feverDao.selectIncidenceByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	
	public TInHspFeverAssociatedSystom selectAssocatesSystomByCaseId(Long caseId)
			throws Exception {
		TInHspFeverAssociatedSystom fever= feverDao.selectAssocatesSystomByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	public TInHspFeverCurrentState selectCurrentStateByCaseId(Long caseId) throws Exception {
		TInHspFeverCurrentState fever= feverDao.selectCurrentStateByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	public TInHspFeverNegativeSystom selectNegativeByCaseId(Long caseId)
			throws Exception {
		TInHspFeverNegativeSystom fever= feverDao.selectNegativeByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	public TInHspFeverOtherSystom selectOtherSystomByCaseId(Long caseId)
			throws Exception {
		TInHspFeverOtherSystom fever= feverDao.selectOtherSystomByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	public TInHspFeverOthersSystom selectOthersSystomByCaseId(Long caseId)
			throws Exception {
		TInHspFeverOthersSystom fever= feverDao.selectOthersSystomByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	public TInHspFeverSystomEvolution selectSystomEvolutionByCaseId(Long caseId)
			throws Exception {
		TInHspFeverSystomEvolution fever= feverDao.selectSystomEvolutionByCaseId(caseId);
		if(fever != null){
			return fever;
		}else {
			return null;
		}
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public String showDate(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime=format.format(date);
		return dateTime;
	}
	
	public static void main(String[] args) {
		
		FeverManagerImpl daoImpl=new FeverManagerImpl();
		 System.out.println(daoImpl.showDate());
	}
}
