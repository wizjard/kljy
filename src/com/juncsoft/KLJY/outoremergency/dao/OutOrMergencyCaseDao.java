package com.juncsoft.KLJY.outoremergency.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyCase;

/**
 * 门诊信息管理接口
 * 
 * @author liugang
 * 
 */
public interface OutOrMergencyCaseDao {
	/**
	 * 保存门诊信息
	 * 
	 * @param outOrMergencyCase
	 * @return
	 */
	public OutOrMergencyCase save(OutOrMergencyCase outOrMergencyCase);

	/**
	 * 查找门诊信息
	 * 
	 * @param id
	 * @return
	 */
	public OutOrMergencyCase findById(Long id);

	/**
	 * 查找全部门诊信息
	 * 
	 * @param patientId
	 * @return
	 */
	public List<OutOrMergencyCase> queryAll(Long patientId);

	/**
	 * 通过关键字查找信息
	 * 
	 * @param keyword
	 * @return
	 */
	public JSONObject searchByParame(String keyword);

	/**
	 * 加载门诊病历记录树
	 * 
	 * @param patientId
	 * @return
	 */
	public JSONArray outOrMergencyCase_treeNodes(Long patientId);

	/**
	 * 保存或者修改门诊信息
	 * 
	 * @param array
	 */
	public void saveOrUpdateOutOrMergencyCase(JSONArray array);

	public OutOrMergencyCase updateSubmiterById(Long id, Long submitId);

	/**
	 * 门诊医生取消签字
	 * 
	 * @param id
	 * @return
	 */
	public OutOrMergencyCase updateSubmiterById_cancelSubmit(Long id);

	/**
	 * 门诊医生重签
	 * 
	 * @param id
	 * @param submitId
	 * @return
	 */
	public OutOrMergencyCase updateOutOrMergencyCourse_resubmit(Long id,
			Long submitId);

	/**
	 * 门诊医生签字
	 * 
	 * @param id
	 * @param checkerId
	 * @return
	 */
	public OutOrMergencyCase updateOutOrMergencyCourse_check(Long id,
			Long checkerId, int verifyStatus);
	/**
	 * 查找该医生所在科室下的所有病历类型的病人的门诊病历
	 * @param patientId
	 * @param outNameList
	 * @param outRegno
	 * @return
	 */
	public List<OutOrMergencyCase> findAllOutOrMergencyCase(String patientId,String outNameList,String outRegno);
	
	public List<OutOrMergencyCase> findAllOutOrMergencyCaseByPatientId(String patientId);

	/**
	 * 门诊打印页面进行医生打印审核
	 * @param id
	 * @return
	 */
	public boolean checkIsSubmiter(Long id);
}
