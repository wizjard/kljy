package com.juncsoft.KLJY.biomedical.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.patient.entity.Patient;

public interface MemberInfoDao {
	public String save(MemberInfo mem) throws Exception;
	public List<Map> getAllMemberForYuanzhang(String search,
			String flag, String descOrasc);

	public String update(MemberInfo mem) throws Exception;

	public void delete(MemberInfo mem) throws Exception;

	public List<MemberInfo> getAll(Integer start, Integer limit,
			Criterion... criterions) throws Exception;

	public List<Patient> getPatients(Criterion... criterions) throws Exception;

	public long getTotal(Criterion... criterions) throws Exception;

	public MemberInfo get(String memberNo) throws Exception;

	public MemberInfo getByPatientId(Long patientId) throws Exception;

	public boolean isAccountExits(String account) throws Exception;

	public String generatorMemberNo(Session session) throws Exception;

	public InHspCaseMaster saveMemberCase(InHspCaseMaster cm) throws Exception;

	public JSONArray memberDataAnalysis(Integer start, Integer limit,
			Criterion... criterions) throws Exception;

	public String memberDataAnalysisToExcel(JSONArray array,String tempPath)
			throws Exception;
	
	public List<Map> getAllMemberByDoctorId(Long doctorId, String search, String flag, String orderBy, String descOrasc);
	public List<Map> getAllMemberByDeptCode(String deptCode, String search, String flag,String descOrasc);
	
	public JSONObject getMemberByDoctorId(Integer start, Integer limit,Long doctorId,String search,String flag,String orderBy,String descOrasc);
	/**
	 * ����Ա�鿴��ǰ�����µ����л�Ա
	 * @param start
	 * @param limit
	 * @param deptCode
	 * @param search
	 * @param flag
	 * @return
	 */
	public JSONObject getMemberByDeptCode(Integer start, Integer limit,String deptCode,String search,String flag,String descOrasc);
	
	public void deleteMemberById(String memberNo);
	
	/**
	 * liugang 2011-08-17
	 * 查找会员健康档案树
	 * @return
	 */
	public JSONArray findMemberHealthRecordByPatientId(Long patientId);
	
	/**
	 * liugang 2011-08-17
	 * 保存会员的健康档案
	 * @param patientId
	 * @return
	 */
	public Map saveMemberHealthRecordByPatientId(Long patientId);
	
	/**
	 * liugang 2011-08-17 
	 * 首次进入会员健康档案页面默认加载会员的首次健康记录
	 * @param patientId
	 * @return
	 */
	public Map firstFindMemberHealthRecordByPatientId(Long patientId);
	
	
	public List<Map> getAllMemberByDeptCode(String ids);
	
	public Map findPatient(Long patientId);
	
	/**
	 * 医务部生物医学信息和院长权限下的所有的会员
	 * @param start
	 * @param limit
	 * @param deptCode
	 * @param search
	 * @param flag
	 * @param descOrasc
	 * @return
	 */
	public JSONObject getYWMemberByDeptCode(Integer start, Integer limit,String search,String flag,String descOrasc);
	
	
}
