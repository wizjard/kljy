package com.juncsoft.KLJY.patientanddoctoroperator.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientConsulting;

/**
 * 咨询我的医生管理
 * 2011-05-31
 * @author liugang
 *
 */
public interface PatientConsultingDao {
	public JSONObject findPatientConsultingByDoctor(Long doctorId,int start, int limit,int flagDai,int flagYi,int flagEnd,JSONObject jsonMap);
	
	public JSONObject findPatientConsultingByPatient(Long patientId,int start, int limit,int flagWeiHui,int flagWeiDu,int flagYiDu);
	
	public void savePatientConsulting(PatientConsulting patientConsulting);
	
	public List<Map> findAllPatientConsultingByPatientId(Long patientId,Long pcId,Long para,Date currentDate,int weekFlag);//para:设置病人已经查看回复
	
	public List<Map> findAllSYS_HIS_DepartmentHISEntity(String name,Long value);//查找所有科室还是门诊科室
	
	public void updatePatientConsulting(PatientConsulting patientConsulting);
	
	/**
	 * 加载病人咨询问题记录树
	 * 
	 * @param patientId
	 * @return
	 */
	public JSONArray patientConsulting_treeNodes(Long patientId,Date currentDate,int weekFlag);
	
	/**
	 * 查找第几次咨询
	 * @return
	 */
	public Map findNumOne(MemberInfo mem,Long patientId);
	
	public boolean sendToOtherDeparment(Long pcid,String deptCode,Long grounpId);
	
	/**
	 * 判断此条咨询是否已经存在转发
	 * @param pcid
	 * @return
	 */
	public boolean checkTheConIsNotSend(Long pcid);
	
	public PatientConsulting findPatientConsultingById(Long id);
	
	/**
	 * 关闭会诊
	 * @param id
	 * @return
	 */
	public boolean updatePatientConsultingStateMeeting(Long id);
	
	/**
	 * 查找医生有几条未读信息
	 */
	public int findNoRead(Long doctorId, int flagDai, int flagYi, JSONObject jsonMap);
	
	public int findCountPatientConsultingByPatient(Long patientId);
	
	public boolean checkIsNotZe(Long doctorId,String deptCode);
	/**
	 * 设置已经读取
	 * @param pcId
	 */
	public void updateState(Long pcId);
	
	/**
	 * 保存会诊科室之间的所有附言
	 * @param pcId
	 * @param applicationBacuse
	 */
	public void updateApplicationBacuse(Long pcId,String applicationBacuse);
	
	/**
	 *  保存会诊科室之间的所有附言新建一个实体存放所有的会诊附言列表
	 * @param pcId
	 * @param deptCode
	 * @param doctorId
	 * @param message
	 */
	public void savaPatientConsultingMessage(Long pcId,Long doctorId,String message);
	
	/**
	 * 查询所有的附言科室
	 * @param pcid
	 * @return
	 */
	public List findAllPatientConsulting(Long pcid);
	
	/**
	 * 查询所有的附言
	 */
	public List findAllPatientCousultingMessage(Long pcId);
	
	/**
	 * 撤销转发的咨询
	 * @param pcId
	 */
	public void cancelAllSendPatientCousulting(Long pcId);
	/**
	 * 撤销转发咨询前判断
	 * @param pcid
	 * @return
	 */
	public List findAllPatientConsultingByCancel(Long pcid);
}
