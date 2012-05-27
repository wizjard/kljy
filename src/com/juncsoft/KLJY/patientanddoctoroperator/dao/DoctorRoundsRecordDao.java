package com.juncsoft.KLJY.patientanddoctoroperator.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
//liugang 2011-08-06 start
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
//liugang 2011-08-06 end
import com.juncsoft.KLJY.patientanddoctoroperator.entity.DoctorRoundsRecord;

/**
 * 医生网上查房记录管理 2011-05-31
 * 
 * @author liugang
 * 
 */
public interface DoctorRoundsRecordDao {
	public DoctorRoundsRecord saveDoctorRoundsRecord(DoctorRoundsRecord doctorRoundsRecord);

	public List<DoctorRoundsRecord> findAllDoctorRoundsRecordByPatientId(
			Long patientId);
	
	//查房记录数查找
	//liugang 2011-08-06 start
	public Map findDoctorRoundsRecordCountByPatientId(Long patientId,Long doctorId);
	//liugang 2011-08-06 end
	
	public void updateDoctorRoundsRecord(DoctorRoundsRecord doctorRoundsRecord);
	
	public DoctorRoundsRecord findDoctorRoundsRecordById(Long id);
	/**
	 * 加载病人咨询问题记录树
	 * 
	 * @param patientId
	 * @return
	 */
	public JSONArray doctorRoundsRecord_treeNodes(Long patientId,Date currentDate,int weekFlag,Long doctorId);
	
	/**
	 * 会员登录查找医生的健康记录树
	 * @param mem
	 * @return
	 */
	//liugang 2011-08-06 start
	public Map findDoctorRoundsRecordCountByMember(MemberInfo mem);
	
	/**
	 * 病人加载自己的所有健康记录
	 * @param patientId
	 * @return
	 */
	public JSONArray doctorRoundsRecord_treeNodesPatient(Long patientId,Date currentDate,int weekFlag);
	//liugang 2011-08-06 end
	
	
}
