package com.juncsoft.KLJY.membergrounp.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.membergrounp.entity.DepartmentGrounp;
import com.juncsoft.KLJY.membergrounp.entity.MemberApplicationRecord;
import com.juncsoft.KLJY.user.entity.User;

/**
 * 会员责任小组
 * @author liugang
 */
public interface DepartmentGrounpDao {
	public List<DepartmentGrounp> findDepartmentGrounpByDeptId(String deptCode);
	
	/**
	 * 获取当前管理员即科主任所在科室下的所有小组及成员分布
	 * @param doctorId
	 * @return
	 */
	public List<Map> findCurrentDoctorDeptmentById(Long grounpId);
	
	public List<Map> findCurrentGrounpList(Long doctorId);
	
	public void saveDepartmentGrounp(Long doctorId,DepartmentGrounp d);
	
	public void updateDepartmentGrounp(DepartmentGrounp d);
	
	public void deleteGrounpUpdateOther(Long id,String deptCode);
	
	/**
	 * 申请转科内容记录
	 * @param memberApplicationRecord
	 */
	public void saveApplicationRecord(MemberApplicationRecord memberApplicationRecord);
	
	/**
	 * 查询本科室中的转科转组会员申请记录
	 * @param doctorId
	 * @return
	 */
	public JSONObject findListApplicationRecord(String deptCode,int flag,int start,int limit);
	
	public JSONObject findListApplicationRecord(String[] deptCodes,int flag,int start,int limit);
	
	public JSONObject findUserHistoryApplicationRecord(Long patientId);
	/**
	 * 科室管理同意或者不同意，更新状态
	 * @param memberApplicationRecord
	 */
	public void updateApplicationRecord(Long id, String because,int state,Long grounpId);
	public void updateApplicationRecord(MemberApplicationRecord memApp);
	/**
	 * 管理员之间查看信息
	 * @param id
	 * @param flag
	 * @return
	 */
	public MemberApplicationRecord findOneMessageById(Long id);
	
	/**
	 * 查看当前是否存在转科或者转组
	 * @param patientId
	 * @return
	 */
	public MemberApplicationRecord findCurrentState(Long patientId);
	
	/**
	 * 查找当前科室下的所有责任医生
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 */
	public JSONObject findAllDeptDoctorList(String deptCode,int start,int limit);
	
	/**
	 * liugang 2011-08-08 start
	 * 新增小组 
	 * @param deptCode
	 * @param grounpName
	 */
	public void addNewGrounp(String deptCode,String grounpName);
	// liugang 2011-08-08 end
	
	/**
	 * liugang 2011-08-06
	 * 查看会员转科转组详细信息  
	 * @param patientId
	 * @return
	 */
	public List findMemberDeptOrGrounpRecordByPatientId(Long patientId);
	
	/**
	 * liugang 2011-08-22
	 * 查找会员的基本信息
	 * @param userId
	 * @return
	 */
	public User findUserByHisUserId(String userId);
	
	/**
	 * 添加责任组成员
	 * @param grounpId
	 * @param doctorId
	 */
	public void findOneUserInsertOneGrounp(Long grounpId,Long doctorId);
	
	public void deleteMemberDoctor(Long doctorId);
	/**
	 * 检查当前医生是否还存在于其他科室中的其他组
	 * @param doctorId
	 * @return
	 */
	public Map checkUserIsOrNotInOtherGrounp(Long doctorId);
	
	/**
	 * 在删除某个组中的组员时，进行组内人数的判断，不能少于两人
	 * @param grounpId
	 * @return
	 */
	public boolean checkUserInOneGrounpCount(Long grounpId);
	
	/**
	 * 删除当前责任组，同时删除组内的所有成员
	 */
	public void deleteGrounp(Long grounpId);
}
