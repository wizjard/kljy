 package com.juncsoft.KLJY.patientanddoctoroperator.entity;

import java.util.Date;

/**
 * 外网预约挂号
 * @author liugang
 *
 */
public class PlanSignOrderEntity {
	private Long id ;
	private Long doctorId;//医生ID(t_user表中的医生在HIS中的编号)
	private String deptCodeId;//科室ID
	private Date currentDate;//出诊时间（精确到天2011-06-14）
	private Long bsAPId;//挂号系统上午和下午设置
	private Date designTime = new Date();//设置时间
	private Long userId;//护士ID给护士站设置一个账号
	private Integer dayNum;//本月中(天)
	private Integer weekDay;//本月中星期几
	public Integer getDayNum() {
		return dayNum;
	}
	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}
	public Integer getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(Integer weekDay) {
		this.weekDay = weekDay;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getDeptCodeId() {
		return deptCodeId;
	}
	public void setDeptCodeId(String deptCodeId) {
		this.deptCodeId = deptCodeId;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public Long getBsAPId() {
		return bsAPId;
	}
	public void setBsAPId(Long bsAPId) {
		this.bsAPId = bsAPId;
	}
	public Date getDesignTime() {
		return designTime;
	}
	public void setDesignTime(Date designTime) {
		this.designTime = designTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
