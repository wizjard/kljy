package com.juncsoft.KLJY.InHospitalCase.Apply.entity;

import java.util.Date;

/**
 * 院外会诊申请单
 */
public class ConsultationApply {
	private Long id;// bigint,
	private Long patientId;// bigint,--病人Id
	private Long historyCaseId;// bigint,--病历组装件Id
	private String patientName;// varchar(100),--病人姓名
	private String gender;// varchar(50), -- 1 男 --2女
	private String age;// varchar(50),--年龄
	private String patientCaseNum; // varchar(100),--病历号
	private String department;// varchar(200),--科室
	private String bedNum;// varchar(200),--床号
	private String consultationGrade;// varchar(50),--单选 常规 急诊
	private Date checkDate;// datetime,--申请会诊时间
	private String caseHistorySummary;// ntext,--病历摘要
	private String primaryDiagnosis;// ntext,--初步诊断
	private String consultationEnd;// ntext,--会诊理由及目的
	private String applyHospital;// varchar(100),--拟请医院
	private String applyDepartment;// varchar(200),--拟请科室或专业 字典 需要输入
	private String applyJobTitle;// varchar(100), --拟请医师职称 单选 -主任医师 -副主任医师 //
									// -主治医师
	private String specifyExpert;// varchar(100),--点名专家
	private String consultationFashion;// varchar(100),--会诊方式 单选 -请来我院 -病人前往
	private String patientConsultationCost;// varchar(100),--患者承担费用 复选 -患者会诊费
	private String patientTrafficCost;// varchar(100),--患者交通费
	private String patientTravellingCost;// varchar(100),--患者差旅费

	private String hosptalConsultationCost;// varchar(100),--医院承担费用 复选 -医院会诊费
	private String hosptalTrafficCost;// varchar(100),--医院交通费
	private String hosptalTravellingCost;// varchar(100),--医院差旅费
	private String patientSign;// varchar(100),--患者签字
	private String familySign;// varchar(100),--关系人签字
	private String relationship;// varchar(100),--与患者关系
	private String treatDoctor;// varchar(100),--经治疗医师
	private String superiorDoctor;// varchar(100),--上级医师
	private String directorDoctor;// varchar(100),--科主任
	private String departmentPhone;// varchar(100),--科室联系电话
	private String linkman;// varchar(100),--联系人
	private String address;// varchar(200),--地址 给默认值
	private String postNum;// varchar(500),--邮编
	private String sickBayPhone;// varchar(100),--医务部联系电话
	private String faxNum;// varchar(100),--传真号
	private String sickBaySign;// varchar(100),--医务部审核签字(盖章)
	private Date issueDate;// datetime,--发出时间

	public ConsultationApply() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConsultationApply(Long id, Long patientId, Long historyCaseId,
			String patientName, String gender, String age,
			String patientCaseNum, String department, String bedNum,
			String consultationGrade, Date checkDate,
			String caseHistorySummary, String primaryDiagnosis,
			String consultationEnd, String applyHospital,
			String applyDepartment, String applyJobTitle, String specifyExpert,
			String consultationFashion, String patientConsultationCost,
			String patientTrafficCost, String patientTravellingCost,
			String hosptalConsultationCost, String hosptalTrafficCost,
			String hosptalTravellingCost, String patientSign,
			String familySign, String relationship, String treatDoctor,
			String superiorDoctor, String directorDoctor,
			String departmentPhone, String linkman, String address,
			String postNum, String sickBayPhone, String faxNum,
			String sickBaySign, Date issueDate) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.historyCaseId = historyCaseId;
		this.patientName = patientName;
		this.gender = gender;
		this.age = age;
		this.patientCaseNum = patientCaseNum;
		this.department = department;
		this.bedNum = bedNum;
		this.consultationGrade = consultationGrade;
		this.checkDate = checkDate;
		this.caseHistorySummary = caseHistorySummary;
		this.primaryDiagnosis = primaryDiagnosis;
		this.consultationEnd = consultationEnd;
		this.applyHospital = applyHospital;
		this.applyDepartment = applyDepartment;
		this.applyJobTitle = applyJobTitle;
		this.specifyExpert = specifyExpert;
		this.consultationFashion = consultationFashion;
		this.patientConsultationCost = patientConsultationCost;
		this.patientTrafficCost = patientTrafficCost;
		this.patientTravellingCost = patientTravellingCost;
		this.hosptalConsultationCost = hosptalConsultationCost;
		this.hosptalTrafficCost = hosptalTrafficCost;
		this.hosptalTravellingCost = hosptalTravellingCost;
		this.patientSign = patientSign;
		this.familySign = familySign;
		this.relationship = relationship;
		this.treatDoctor = treatDoctor;
		this.superiorDoctor = superiorDoctor;
		this.directorDoctor = directorDoctor;
		this.departmentPhone = departmentPhone;
		this.linkman = linkman;
		this.address = address;
		this.postNum = postNum;
		this.sickBayPhone = sickBayPhone;
		this.faxNum = faxNum;
		this.sickBaySign = sickBaySign;
		this.issueDate = issueDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getHistoryCaseId() {
		return historyCaseId;
	}

	public void setHistoryCaseId(Long historyCaseId) {
		this.historyCaseId = historyCaseId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPatientCaseNum() {
		return patientCaseNum;
	}

	public void setPatientCaseNum(String patientCaseNum) {
		this.patientCaseNum = patientCaseNum;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getBedNum() {
		return bedNum;
	}

	public void setBedNum(String bedNum) {
		this.bedNum = bedNum;
	}

	public String getConsultationGrade() {
		return consultationGrade;
	}

	public void setConsultationGrade(String consultationGrade) {
		this.consultationGrade = consultationGrade;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCaseHistorySummary() {
		return caseHistorySummary;
	}

	public void setCaseHistorySummary(String caseHistorySummary) {
		this.caseHistorySummary = caseHistorySummary;
	}

	public String getPrimaryDiagnosis() {
		return primaryDiagnosis;
	}

	public void setPrimaryDiagnosis(String primaryDiagnosis) {
		this.primaryDiagnosis = primaryDiagnosis;
	}

	public String getConsultationEnd() {
		return consultationEnd;
	}

	public void setConsultationEnd(String consultationEnd) {
		this.consultationEnd = consultationEnd;
	}

	public String getApplyHospital() {
		return applyHospital;
	}

	public void setApplyHospital(String applyHospital) {
		this.applyHospital = applyHospital;
	}

	public String getApplyDepartment() {
		return applyDepartment;
	}

	public void setApplyDepartment(String applyDepartment) {
		this.applyDepartment = applyDepartment;
	}

	public String getApplyJobTitle() {
		return applyJobTitle;
	}

	public void setApplyJobTitle(String applyJobTitle) {
		this.applyJobTitle = applyJobTitle;
	}

	public String getSpecifyExpert() {
		return specifyExpert;
	}

	public void setSpecifyExpert(String specifyExpert) {
		this.specifyExpert = specifyExpert;
	}

	public String getConsultationFashion() {
		return consultationFashion;
	}

	public void setConsultationFashion(String consultationFashion) {
		this.consultationFashion = consultationFashion;
	}

	public String getPatientConsultationCost() {
		return patientConsultationCost;
	}

	public void setPatientConsultationCost(String patientConsultationCost) {
		this.patientConsultationCost = patientConsultationCost;
	}

	public String getPatientTrafficCost() {
		return patientTrafficCost;
	}

	public void setPatientTrafficCost(String patientTrafficCost) {
		this.patientTrafficCost = patientTrafficCost;
	}

	public String getPatientTravellingCost() {
		return patientTravellingCost;
	}

	public void setPatientTravellingCost(String patientTravellingCost) {
		this.patientTravellingCost = patientTravellingCost;
	}

	public String getHosptalConsultationCost() {
		return hosptalConsultationCost;
	}

	public void setHosptalConsultationCost(String hosptalConsultationCost) {
		this.hosptalConsultationCost = hosptalConsultationCost;
	}

	public String getHosptalTrafficCost() {
		return hosptalTrafficCost;
	}

	public void setHosptalTrafficCost(String hosptalTrafficCost) {
		this.hosptalTrafficCost = hosptalTrafficCost;
	}

	public String getHosptalTravellingCost() {
		return hosptalTravellingCost;
	}

	public void setHosptalTravellingCost(String hosptalTravellingCost) {
		this.hosptalTravellingCost = hosptalTravellingCost;
	}

	public String getPatientSign() {
		return patientSign;
	}

	public void setPatientSign(String patientSign) {
		this.patientSign = patientSign;
	}

	public String getFamilySign() {
		return familySign;
	}

	public void setFamilySign(String familySign) {
		this.familySign = familySign;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getTreatDoctor() {
		return treatDoctor;
	}

	public void setTreatDoctor(String treatDoctor) {
		this.treatDoctor = treatDoctor;
	}

	public String getSuperiorDoctor() {
		return superiorDoctor;
	}

	public void setSuperiorDoctor(String superiorDoctor) {
		this.superiorDoctor = superiorDoctor;
	}

	public String getDirectorDoctor() {
		return directorDoctor;
	}

	public void setDirectorDoctor(String directorDoctor) {
		this.directorDoctor = directorDoctor;
	}

	public String getDepartmentPhone() {
		return departmentPhone;
	}

	public void setDepartmentPhone(String departmentPhone) {
		this.departmentPhone = departmentPhone;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostNum() {
		return postNum;
	}

	public void setPostNum(String postNum) {
		this.postNum = postNum;
	}

	public String getSickBayPhone() {
		return sickBayPhone;
	}

	public void setSickBayPhone(String sickBayPhone) {
		this.sickBayPhone = sickBayPhone;
	}

	public String getFaxNum() {
		return faxNum;
	}

	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}

	public String getSickBaySign() {
		return sickBaySign;
	}

	public void setSickBaySign(String sickBaySign) {
		this.sickBaySign = sickBaySign;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

}
