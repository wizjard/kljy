package com.juncsoft.KLJY.patientanddoctoroperator.dao;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.juncsoft.KLJY.patientanddoctoroperator.entity.BaseSignAPEntity;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PlanSignOrderEntity;

/**
 * 预约挂号
 * @author liugang
 *
 */
public interface PlanSignOrderDao {
	/**
	 * 加载医院的所有预约挂号科室
	 * @param flag
	 * @return
	 */
	public Map findAllSYS_HIS_DepartmentHISEntity(int flag);
	
	/**
	 * 根据日期字符串将本月的所有天数进行星期划分
	 * @param date
	 * @return
	 */
	public Map findAllDayInWeek(String deptCodeId,Long doctorId,String date);
	
	/**
	 * 查找所有的上午或者下午预约类型
	 * @return
	 */
	public List<BaseSignAPEntity> findBaseSignAPEntity();
	
	/**
	 * 批量保存PlanSignOrderEntity
	 * @param list
	 */
	public void savePlanSignOrderEntity(List<PlanSignOrderEntity> list);
	
	/**
	 * 根据科室加载医生的出诊列表
	 * @param deptCodeId
	 * @param outTypeIdList
	 * @return
	 */
	public Map findAllDoctorNurses(String deptCodeId,String outTypeIdList);
	/**
	 *  外网会员预约挂号
	 * @param deptCode
	 * @param doctorId
	 * @param patientId
	 * @param bsAPId
	 * @return
	 */
	public boolean savePlanSignOrderPatientEntity(String deptCode,Long doctorId,Long patientId,Long bsAPId,String planDate,Long bsTSId);
	
	/**
	 * 批量保存PlanSignOrderEntity
	 * @param list
	 */
	public void savePlanSignOrderOneMonthEntity(String startDate,String endDate,List<PlanSignOrderEntity> list);
	
	/**
	 * 外网预约门诊
	 * @param deptCode
	 * @param doctorId
	 * @param bsAPId
	 * @param planDate
	 * @return
	 */
	public Map findPlanSignOrderPatient(String deptCode,Long doctorId,Long bsAPId,String planDate);
	
	/**
	 * 会员查找预约挂号详细信息
	 * @param patientId
	 * @param start
	 * @param limit
	 * @return
	 */
	public JSONObject findPlanSignOrderPatientList(Long patientId,int start, int limit,String startSegmentTime,String endSegmentTime,String deptcode,Long doctorId);
	
	/**
	 * 会员取消有效时间内的预约挂号信息
	 * @param deleteId
	 */
	public void deletePatientPlanOrder(Long deleteId);
	
	
	public void exportSearchDataToExcel(Long patientId, String planOrderStartDate,String planOrderEndDate,String deptcode,Long doctorId,OutputStream out);
	/**
	 * 查询医生14天的出诊记录
	 * @param deptCodeId
	 * @param outTypeIdList
	 * @return
	 */
	public Map findAllDoctorNursesFour(String deptCodeId, String outTypeIdList);
	
	/**
	 * 我组预约记录查询
	 * @param patientId
	 * @param start
	 * @param limit
	 * @param startSegmentTime
	 * @param endSegmentTime
	 * @param deptcode
	 * @param doctorId
	 * @return
	 */
	public JSONObject findGrounpPlanSignOrderPatientList(int start, int limit,String search,String doctorId);
}
