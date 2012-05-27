package com.juncsoft.KLJY.patient.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.patient.entity.Patient;

public interface PatientDao {
	public Patient saveOrUpdate(Patient patient) throws Exception;
	public Patient findById(Long id) throws Exception;
//	public Patient findById(Long patientId, Session session)  throws Exception;
	public Patient findByPatientId(String patientId) throws Exception;
	
	public MemberInfo findMemberInfoByMemNo(String memberNo) throws Exception;
	public MemberInfo findMemberInfoByMemNo(String memberNo, Session session) throws Exception;
	/**分页查询全院病人*/
	public JSONObject queryAll(Integer start,Integer limit) throws Exception;
	public JSONObject queryByBelong(String belong,Integer start,Integer limit) throws Exception;
	public JSONObject searchByNameOrNo(String keyword,Integer start,Integer limit) throws Exception;
	
	public JSONObject searchByCondition(String keyword, String condition, Integer start, Integer limit) throws Exception;
	
	public List findMemberNoPatientId(String patientId,Session session) throws Exception; //add by cheng jiangyu 2011-10-08
	/**
	 * 2011-07-08 liugang
	 * ��Ա��ᱣ���Ա��Ϣ��ͬʱ�޸Ĳ��˻���Ϣ
	 * @param patient
	 * @return
	 * @throws Exception
	 */
	public Patient saveOrUpdatePM(Patient patient,MemberInfo member) throws Exception;
	
	/** 
	 * liugang
	 * ��ȡ���˺ͻ�Ա�Ļ���Ϣ�ۺ�
	 */
	public Map findPatientAndMemberInfo(Long id);
	/**
	 * 查询某科室下病人信息集合
	 * liugang
	 * @param key
	 * @param belong
	 * @param start
	 * @param limit
	 * @return
	 */
	public JSONObject queryByBelongKey(String key, String belong,
			Integer start, Integer limit)throws Exception;
	
	/**
	 * 会员入会时复制新增入会病历
	 * @param patient
	 * @param member
	 * @param caseId
	 * @return
	 */
	public Patient saveOrUpdatePMCopy(Patient patient, MemberInfo member,Long caseId);
}
