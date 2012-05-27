package com.juncsoft.KLJY.Public.dao;

import java.util.List;
import java.util.Map;


/**
 * HIS科室名称管理
 * @author liugang 2011-05-17
 *
 */
public interface DepartmentHISDao {
	//通过科室名称查找对应的住院或门诊病历类型名称
	public Map findDepartmentHISEntityByDeptName(String deptName);
	/**
	 * 通过小组编号，获取医生的姓名列表
	 * @param grounpId
	 * @return
	 */
	public Map findDoctornameByGrounpId(Long grounpId);
	
	/**
	 * 根据医生ID获取医生负责的责任科室
	 * @param doctorId
	 * @return
	 */
	public List<Map> findDoctorDeptMemberByDoctorId(Long doctorId);
	
	/**
	 * 根据医生ID和当前责任科室加载医生所在的责任小组
	 * @param doctorId
	 * @param deptCode
	 * @return
	 */
	public List<Map> findDoctorGrounpByMemberByDoctorId(Long doctorId,String deptCode);
	
	public List<Map> findGrounpByDeptCode(String deptCode);
	
	 //根据医生Id 获得当前责任科室和小组名称  by cheng jiangyu 2011-09-30
	public List<Map> findDoctorDeptAndGroupByDoctorId(Long doctorId);
	
	 //通过管理员的deptCode查找管理员所在的小组列表  by cheng jiangyu 2011-9-30
	public List<Map> findManageDeptGroupBydeptCode(String deptCode);
	
	/**
	 * 全部科室及小组
	 * @return
	 */
	public List<Map> findManageDeptGroupBydeptCode();
}
