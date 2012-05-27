package com.juncsoft.KLJY.InHospitalCase.CheckReport.entity;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ReportFormforRemote implements java.io.Serializable {
	private int id;
	private Date receiveDate;
	private int sectionNo; 
	private int testTypeNo; 
	private String sampleNo; 
	private int statusNo; 
	private int sampleTypeNo; 
	private String patNo; 
	private String cname; 
	private int genderNo; 
	private Date birthday; 
	private float age; 
	private int ageUnitNo; 
	private int folkNo; 
	private int districtNo;
	private int wardNo; 
	private String bed; 
	private int deptNo; 
	private String doctor; 
	private int chargeNo; 
	private float charge; 
	private String collecter;
	private Date collectDate;
	private Date collectTime;
	private String formMemo;
	private String technician;
	private Date testTime;
	private Date testDate;
	private String operator;
	private Date operDate;
	private Date operTime;
	private String checker;
	private Date checkDate;
	private Date checkTime;
	private String serialNo;
	private String requestSource;
	private int diagNo;
	private int printTimes;
	private String hospitalName;
	private int isAdd;
	private int home_foreign;
	private Integer isFromOutHospital;//区分本院与外院手动录入的化验单数据 1：表示外院 null 表示 本院，(程序导入lis中的数据)
	private Integer isPatientOrDoctorWriteZanCun;//区分是会员还是责任医生录入，10:表示会员暂存，20:表示医生暂存，11:表示会员提交给责任医生，21:表示医生把自己录入的化验或者病人录入的化验提交归档
	private Date sheHeDate;// 病人提交给责任医生的时间
	private Date guiDangDate;//医生提交该化验单为归档状态的日期
	private Date luRuDate;//录入日期
	private Integer cheXiaoFlag;//管理员撤销录入的化验单，医生可以开始修改
	private Integer cheXiaoTrue;//第几次撤销 2表示撤销后已经提交，或者归档， 1表示可以正常使用
	
	public Integer getIsFromOutHospital() {
		return isFromOutHospital;
	}
	public void setIsFromOutHospital(Integer isFromOutHospital) {
		this.isFromOutHospital = isFromOutHospital;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public int getSectionNo() {
		return sectionNo;
	}
	public void setSectionNo(int sectionNo) {
		this.sectionNo = sectionNo;
	}
	public int getTestTypeNo() {
		return testTypeNo;
	}
	public void setTestTypeNo(int testTypeNo) {
		this.testTypeNo = testTypeNo;
	}
	public String getSampleNo() {
		return sampleNo;
	}
	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}
	public int getStatusNo() {
		return statusNo;
	}
	public void setStatusNo(int statusNo) {
		this.statusNo = statusNo;
	}
	public int getSampleTypeNo() {
		return sampleTypeNo;
	}
	public void setSampleTypeNo(int sampleTypeNo) {
		this.sampleTypeNo = sampleTypeNo;
	}
	public String getPatNo() {
		return patNo;
	}
	public void setPatNo(String patNo) {
		this.patNo = patNo;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public int getGenderNo() {
		return genderNo;
	}
	public void setGenderNo(int genderNo) {
		this.genderNo = genderNo;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public float getAge() {
		return age;
	}
	public void setAge(float age) {
		this.age = age;
	}
	public int getAgeUnitNo() {
		return ageUnitNo;
	}
	public void setAgeUnitNo(int ageUnitNo) {
		this.ageUnitNo = ageUnitNo;
	}
	public int getFolkNo() {
		return folkNo;
	}
	public void setFolkNo(int folkNo) {
		this.folkNo = folkNo;
	}
	public int getDistrictNo() {
		return districtNo;
	}
	public void setDistrictNo(int districtNo) {
		this.districtNo = districtNo;
	}
	public int getWardNo() {
		return wardNo;
	}
	public void setWardNo(int wardNo) {
		this.wardNo = wardNo;
	}
	public String getBed() {
		return bed;
	}
	public void setBed(String bed) {
		this.bed = bed;
	}
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public int getChargeNo() {
		return chargeNo;
	}
	public void setChargeNo(int chargeNo) {
		this.chargeNo = chargeNo;
	}
	
	public float getCharge() {
		return charge;
	}
	public void setCharge(float charge) {
		this.charge = charge;
	}
	public String getCollecter() {
		return collecter;
	}
	public void setCollecter(String collecter) {
		this.collecter = collecter;
	}
	public Date getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}
	public Date getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
	public String getFormMemo() {
		return formMemo;
	}
	public void setFormMemo(String formMemo) {
		this.formMemo = formMemo;
	}
	public String getTechnician() {
		return technician;
	}
	public void setTechnician(String technician) {
		this.technician = technician;
	}
	public Date getTestTime() {
		return testTime;
	}
	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperDate() {
		return operDate;
	}
	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getRequestSource() {
		return requestSource;
	}
	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}
	public int getDiagNo() {
		return diagNo;
	}
	public void setDiagNo(int diagNo) {
		this.diagNo = diagNo;
	}
	public int getPrintTimes() {
		return printTimes;
	}
	public void setPrintTimes(int printTimes) {
		this.printTimes = printTimes;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public int getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(int isAdd) {
		this.isAdd = isAdd;
	}
	
	public int getHome_foreign() {
		return home_foreign;
	}
	public void setHome_foreign(int home_foreign) {
		this.home_foreign = home_foreign;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getSheHeDate() {
		return sheHeDate;
	}
	public void setSheHeDate(Date sheHeDate) {
		this.sheHeDate = sheHeDate;
	}
	public Date getGuiDangDate() {
		return guiDangDate;
	}
	public void setGuiDangDate(Date guiDangDate) {
		this.guiDangDate = guiDangDate;
	}
	public Integer getIsPatientOrDoctorWriteZanCun() {
		return isPatientOrDoctorWriteZanCun;
	}
	public void setIsPatientOrDoctorWriteZanCun(Integer isPatientOrDoctorWriteZanCun) {
		this.isPatientOrDoctorWriteZanCun = isPatientOrDoctorWriteZanCun;
	}
	public Date getLuRuDate() {
		return luRuDate;
	}
	public void setLuRuDate(Date luRuDate) {
		this.luRuDate = luRuDate;
	}
	public Integer getCheXiaoFlag() {
		return cheXiaoFlag;
	}
	public void setCheXiaoFlag(Integer cheXiaoFlag) {
		this.cheXiaoFlag = cheXiaoFlag;
	}
	public Integer getCheXiaoTrue() {
		return cheXiaoTrue;
	}
	public void setCheXiaoTrue(Integer cheXiaoTrue) {
		this.cheXiaoTrue = cheXiaoTrue;
	}
	
//	public boolean equals(Object obj) {
//        if(obj == this) {
//            return true;
//        }
//        if(!(obj instanceof ReportFormforRemote)) {
//            return false;
//        }
//        ReportFormforRemote reportFormforRemote = (ReportFormforRemote) obj;
//        return new EqualsBuilder()
//                 .append(this.receiveDate, reportFormforRemote.getReceiveDate())
//                 .append(this.sectionNo, reportFormforRemote.getSectionNo())
//                 .append(this.testTypeNo, reportFormforRemote.getTestTypeNo())
//                 .append(this.sampleNo, reportFormforRemote.getSampleNo())
//                 .isEquals();
//
//    }
//	public int hashCode() {
//        return new HashCodeBuilder()
//                 .append(this.receiveDate)
//                 .append(this.sectionNo)
//                 .append(this.testTypeNo)
//                 .append(this.sampleNo)
//                 .toHashCode();
//	}
	

}
