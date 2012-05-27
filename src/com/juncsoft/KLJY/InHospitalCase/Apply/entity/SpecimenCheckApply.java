package com.juncsoft.KLJY.InHospitalCase.Apply.entity;

import java.util.Date;

/**
 * 病理标本检查申请单
 */
public class SpecimenCheckApply {
	private Long id;// bigint,
	private Long patientId;// bigint,--病人ID
	private Long historyCaseId;// bigint,--病历组件ID
	private String patientName;// varchar(100),--病人姓名
	private String nativePlace;// varchar(200),--籍贯
	private String career;// varchar(200),--职业
	private String age;// varchar(100),--年龄
	private String gender;// varchar(50),--性别
	private String sickBay;// varchar(100),--病区
	private String ward;// varchar(100),--病房
	private String patientCaseNum;// varchar(100),--病历号
	private String pathologyNum;// varchar(100),--病理号
	private Date acceptDate;// datetime,--收取时间
	private String clinicSummary;// ntext,--临床摘要与临床所见
	private String operationFindings;// ntext,--手术所见
	// --如系肿瘤请填下列各项--
	private String cancerGrowTime;// varchar(100),--肿瘤生长时间
	private String cancerSize;// varchar(100),--肿瘤大小
	private String cancerPosition;// varchar(200),--肿瘤位置
	private String cancerShift;// varchar(50),--有无转移性瘤 单选框 --有 --无
	private String shiftPosition;// varchar(200),--在何处
	// --如系子宫内膜请填下列各项--
	private String mensesCalender;// varchar(100),--月经历
	private Date menarche;// , datetime;//,--初潮
	private String mensesQuantity;// varchar(100),--月经量
	private String periodPersistTime;// varchar(100),--周期及持续时间
	private Date lastMenstrual;// datetime,--末次月经
	private String hemorrhageQuantity;// varchar(100),--出血量
	private String laborTreat;// varchar(50),--曾否施行人工治疗 单选框 --是 --否
	private Date laborTreatDate;// datetime,--人工治疗时间
	private String dose;// varchar(100),--剂量
	private Date collectionDate;// --标本采取时间
	private String specimenSource;// varchar(200),--标本来源
	private String stationaryLiquid;// varchar(200),--固定液
	private String biopsyPart;// varchar(200),--取材部位
	private String everCheck;// varchar(50), --以前曾做过病理检查 单选框 --是 -- 否
	private String specimenCheckNum;// varchar(100),--其病理标本检查号为
	private String clinicalDiagnosis;// ntext,--临床诊断
	private String doctorName;// varchar(100),--送检医师
	private Date checkDate;// datetime,--送检日期

	public SpecimenCheckApply() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SpecimenCheckApply(Long id, Long patientId, Long historyCaseId,
			String patientName, String nativePlace, String career, String age,
			String gender, String sickBay, String ward, String patientCaseNum,
			String pathologyNum, Date acceptDate, String clinicSummary,
			String operationFindings, String cancerGrowTime, String cancerSize,
			String cancerPosition, String cancerShift, String shiftPosition,
			String mensesCalender, Date menarche, String mensesQuantity,
			String periodPersistTime, Date lastMenstrual,
			String hemorrhageQuantity, String laborTreat, Date laborTreatDate,
			String dose, Date collectionDate, String specimenSource,
			String stationaryLiquid, String biopsyPart, String everCheck,
			String specimenCheckNum, String clinicalDiagnosis,
			String doctorName, Date checkDate) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.historyCaseId = historyCaseId;
		this.patientName = patientName;
		this.nativePlace = nativePlace;
		this.career = career;
		this.age = age;
		this.gender = gender;
		this.sickBay = sickBay;
		this.ward = ward;
		this.patientCaseNum = patientCaseNum;
		this.pathologyNum = pathologyNum;
		this.acceptDate = acceptDate;
		this.clinicSummary = clinicSummary;
		this.operationFindings = operationFindings;
		this.cancerGrowTime = cancerGrowTime;
		this.cancerSize = cancerSize;
		this.cancerPosition = cancerPosition;
		this.cancerShift = cancerShift;
		this.shiftPosition = shiftPosition;
		this.mensesCalender = mensesCalender;
		this.menarche = menarche;
		this.mensesQuantity = mensesQuantity;
		this.periodPersistTime = periodPersistTime;
		this.lastMenstrual = lastMenstrual;
		this.hemorrhageQuantity = hemorrhageQuantity;
		this.laborTreat = laborTreat;
		this.laborTreatDate = laborTreatDate;
		this.dose = dose;
		this.collectionDate = collectionDate;
		this.specimenSource = specimenSource;
		this.stationaryLiquid = stationaryLiquid;
		this.biopsyPart = biopsyPart;
		this.everCheck = everCheck;
		this.specimenCheckNum = specimenCheckNum;
		this.clinicalDiagnosis = clinicalDiagnosis;
		this.doctorName = doctorName;
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

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSickBay() {
		return sickBay;
	}

	public void setSickBay(String sickBay) {
		this.sickBay = sickBay;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getPatientCaseNum() {
		return patientCaseNum;
	}

	public void setPatientCaseNum(String patientCaseNum) {
		this.patientCaseNum = patientCaseNum;
	}

	public String getPathologyNum() {
		return pathologyNum;
	}

	public void setPathologyNum(String pathologyNum) {
		this.pathologyNum = pathologyNum;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getClinicSummary() {
		return clinicSummary;
	}

	public void setClinicSummary(String clinicSummary) {
		this.clinicSummary = clinicSummary;
	}

	public String getOperationFindings() {
		return operationFindings;
	}

	public void setOperationFindings(String operationFindings) {
		this.operationFindings = operationFindings;
	}

	public String getCancerGrowTime() {
		return cancerGrowTime;
	}

	public void setCancerGrowTime(String cancerGrowTime) {
		this.cancerGrowTime = cancerGrowTime;
	}

	public String getCancerSize() {
		return cancerSize;
	}

	public void setCancerSize(String cancerSize) {
		this.cancerSize = cancerSize;
	}

	public String getCancerPosition() {
		return cancerPosition;
	}

	public void setCancerPosition(String cancerPosition) {
		this.cancerPosition = cancerPosition;
	}

	public String getCancerShift() {
		return cancerShift;
	}

	public void setCancerShift(String cancerShift) {
		this.cancerShift = cancerShift;
	}

	public String getShiftPosition() {
		return shiftPosition;
	}

	public void setShiftPosition(String shiftPosition) {
		this.shiftPosition = shiftPosition;
	}

	public String getMensesCalender() {
		return mensesCalender;
	}

	public void setMensesCalender(String mensesCalender) {
		this.mensesCalender = mensesCalender;
	}

	public Date getMenarche() {
		return menarche;
	}

	public void setMenarche(Date menarche) {
		this.menarche = menarche;
	}

	public String getMensesQuantity() {
		return mensesQuantity;
	}

	public void setMensesQuantity(String mensesQuantity) {
		this.mensesQuantity = mensesQuantity;
	}

	public String getPeriodPersistTime() {
		return periodPersistTime;
	}

	public void setPeriodPersistTime(String periodPersistTime) {
		this.periodPersistTime = periodPersistTime;
	}

	public Date getLastMenstrual() {
		return lastMenstrual;
	}

	public void setLastMenstrual(Date lastMenstrual) {
		this.lastMenstrual = lastMenstrual;
	}

	public String getHemorrhageQuantity() {
		return hemorrhageQuantity;
	}

	public void setHemorrhageQuantity(String hemorrhageQuantity) {
		this.hemorrhageQuantity = hemorrhageQuantity;
	}

	public String getLaborTreat() {
		return laborTreat;
	}

	public void setLaborTreat(String laborTreat) {
		this.laborTreat = laborTreat;
	}

	public Date getLaborTreatDate() {
		return laborTreatDate;
	}

	public void setLaborTreatDate(Date laborTreatDate) {
		this.laborTreatDate = laborTreatDate;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getSpecimenSource() {
		return specimenSource;
	}

	public void setSpecimenSource(String specimenSource) {
		this.specimenSource = specimenSource;
	}

	public String getStationaryLiquid() {
		return stationaryLiquid;
	}

	public void setStationaryLiquid(String stationaryLiquid) {
		this.stationaryLiquid = stationaryLiquid;
	}

	public String getBiopsyPart() {
		return biopsyPart;
	}

	public void setBiopsyPart(String biopsyPart) {
		this.biopsyPart = biopsyPart;
	}

	public String getEverCheck() {
		return everCheck;
	}

	public void setEverCheck(String everCheck) {
		this.everCheck = everCheck;
	}

	public String getSpecimenCheckNum() {
		return specimenCheckNum;
	}

	public void setSpecimenCheckNum(String specimenCheckNum) {
		this.specimenCheckNum = specimenCheckNum;
	}

	public String getClinicalDiagnosis() {
		return clinicalDiagnosis;
	}

	public void setClinicalDiagnosis(String clinicalDiagnosis) {
		this.clinicalDiagnosis = clinicalDiagnosis;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

}
