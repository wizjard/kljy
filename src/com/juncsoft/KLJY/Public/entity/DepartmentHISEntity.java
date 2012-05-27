package com.juncsoft.KLJY.Public.entity;

//HIS 数据库中的科室信息对应的门诊病历类型、住院病历类型
public class DepartmentHISEntity {
	private Long id;
	private String deptCode;//科室编号 
	private String deptName;//科室名称
	private Integer flag;//是否挂号
	private String outType;//门诊病历类型
	private String inHospitalType;//住院病历类型
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
	}
	public String getInHospitalType() {
		return inHospitalType;
	}
	public void setInHospitalType(String inHospitalType) {
		this.inHospitalType = inHospitalType;
	}
}
