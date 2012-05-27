package com.juncsoft.KLJY.InHospitalCase.Apply.entity;

import java.util.Date;

public class Communication {
	private Long id; // bigint,
	private Long patientId; // bigint,--病人ID
	private Long historyCaseId; // bigint,--病历组件ID
	private String patientName; // varchar(100),--病人姓名
	private String gender; // varchar(20),--病人性别
	private String age; // varchar(20),--病人年龄
	private String communicationNum; // bigint,--沟通次数
	private String diagnose; // ntext,--临床诊断
	private String joinPatient; // ntext,--患方
	private String joinDoctor;// ntext, --医方
	// private String connectEnd; // varchar(10)--沟通目的
	
	private String conditionNeed;// 病情需要
	private String patientAsk;// 患者要求
	private String familyAsk;// 家属要求
	private String fandpAsk;// 患者及家属要求
	private String otherNeed;// 其他
	
	private String otherText; // varchar(400),--其他文本框
	private String concreteContent; // ntext,--沟通具体内容
	private String pfIdea; // ntext,--患者/家属意见
	private String patientSign; // varchar(100),--患者签字
	private String patientPhone; // varchar(100),--患者联系电话
	private Date patientDate; // dateTime,--患者签字日期
	private String familySign; // varchar(100),--家属签字
	private String frelation; // varchar(100),--家属与患者关系
	private String familyPhone; // varchar(100),--家属联系电话
	private Date familyDate; // dateTime,--家属签字日期
	private String doctorsign; // varchar(100),--医师签字
	private Date checkDate; // dateTime,--医生签字日期

	public Communication() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Communication(Long id, Long patientId, Long historyCaseId,
			String patientName, String gender, String age,
			String communicationNum, String diagnose, String joinPatient,
			String joinDoctor, String conditionNeed, String patientAsk,
			String familyAsk, String fandpAsk, String otherNeed,
			String otherText, String concreteContent, String pfIdea,
			String patientSign, String patientPhone, Date patientDate,
			String familySign, String frelation, String familyPhone,
			Date familyDate, String doctorsign, Date checkDate) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.historyCaseId = historyCaseId;
		this.patientName = patientName;
		this.gender = gender;
		this.age = age;
		this.communicationNum = communicationNum;
		this.diagnose = diagnose;
		this.joinPatient = joinPatient;
		this.joinDoctor = joinDoctor;
		this.conditionNeed = conditionNeed;
		this.patientAsk = patientAsk;
		this.familyAsk = familyAsk;
		this.fandpAsk = fandpAsk;
		this.otherNeed = otherNeed;
		this.otherText = otherText;
		this.concreteContent = concreteContent;
		this.pfIdea = pfIdea;
		this.patientSign = patientSign;
		this.patientPhone = patientPhone;
		this.patientDate = patientDate;
		this.familySign = familySign;
		this.frelation = frelation;
		this.familyPhone = familyPhone;
		this.familyDate = familyDate;
		this.doctorsign = doctorsign;
		this.checkDate = checkDate;
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

	public String getCommunicationNum() {
		return communicationNum;
	}

	public void setCommunicationNum(String communicationNum) {
		this.communicationNum = communicationNum;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getJoinPatient() {
		return joinPatient;
	}

	public void setJoinPatient(String joinPatient) {
		this.joinPatient = joinPatient;
	}

	public String getJoinDoctor() {
		return joinDoctor;
	}

	public void setJoinDoctor(String joinDoctor) {
		this.joinDoctor = joinDoctor;
	}

	public String getConditionNeed() {
		return conditionNeed;
	}

	public void setConditionNeed(String conditionNeed) {
		this.conditionNeed = conditionNeed;
	}

	public String getPatientAsk() {
		return patientAsk;
	}

	public void setPatientAsk(String patientAsk) {
		this.patientAsk = patientAsk;
	}

	public String getFamilyAsk() {
		return familyAsk;
	}

	public void setFamilyAsk(String familyAsk) {
		this.familyAsk = familyAsk;
	}

	public String getFandpAsk() {
		return fandpAsk;
	}

	public void setFandpAsk(String fandpAsk) {
		this.fandpAsk = fandpAsk;
	}

	public String getOtherNeed() {
		return otherNeed;
	}

	public void setOtherNeed(String otherNeed) {
		this.otherNeed = otherNeed;
	}

	public String getOtherText() {
		return otherText;
	}

	public void setOtherText(String otherText) {
		this.otherText = otherText;
	}

	public String getConcreteContent() {
		return concreteContent;
	}

	public void setConcreteContent(String concreteContent) {
		this.concreteContent = concreteContent;
	}

	public String getPfIdea() {
		return pfIdea;
	}

	public void setPfIdea(String pfIdea) {
		this.pfIdea = pfIdea;
	}

	public String getPatientSign() {
		return patientSign;
	}

	public void setPatientSign(String patientSign) {
		this.patientSign = patientSign;
	}

	public String getPatientPhone() {
		return patientPhone;
	}

	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}

	public Date getPatientDate() {
		return patientDate;
	}

	public void setPatientDate(Date patientDate) {
		this.patientDate = patientDate;
	}

	public String getFamilySign() {
		return familySign;
	}

	public void setFamilySign(String familySign) {
		this.familySign = familySign;
	}

	public String getFrelation() {
		return frelation;
	}

	public void setFrelation(String frelation) {
		this.frelation = frelation;
	}

	public String getFamilyPhone() {
		return familyPhone;
	}

	public void setFamilyPhone(String familyPhone) {
		this.familyPhone = familyPhone;
	}

	public Date getFamilyDate() {
		return familyDate;
	}

	public void setFamilyDate(Date familyDate) {
		this.familyDate = familyDate;
	}

	public String getDoctorsign() {
		return doctorsign;
	}

	public void setDoctorsign(String doctorsign) {
		this.doctorsign = doctorsign;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

}
