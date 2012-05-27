package com.juncsoft.KLJY.InHospitalCase.Apply.entity;

import java.util.Date;

/**
 * 骨髓检查申请单实体
 */
public class MarrowApply {
	private Long id;
	private Long patientId;// --病人Id
	private Long historyCaseId;// --病历组件Id
	private String patientName;// --病人姓名
	private String patientSex;// varchar(10),--病人性别 数字‘0’，‘1’
	private String patientAge; // varchar(10),--病人年龄
	private String patientArea;// varchar(50),--病区
	private String patientNum;// bigint, --病历号
	private String checkNum = null;// bigint --检查号
	private String labNum = null;// bigint, --实验室编号
	private String clinicalShow;// ntext, --临床表现
	private String clinicalDiagnosis;// ntext,--临床诊断
	// --末梢血象结果
	// --白细胞
	private String WBC;// varchar(20),
	private String LYM;// varchar(20),
	private String MON;// varchar(20),
	private String NEU;// varchar(20),
	private String EOS;// varchar(20),
	private String BAS;// varchar(20),
	// --红细胞
	private String HGB;// varchar(20),
	private String RBC;// varchar(20),
	private String HCT;// varchar(20),
	private String MCV;// varchar(20),
	private String MCH;// varchar(20),
	private String MCHC;// varchar(20),
	private String RDW;// varchar(20),
	private String Ret;// varchar(20),
	private String LIC;// varchar(20),
	private String redName;// varchar(50), --红细胞部分自添名称
	private String redValue;// varchar(20), --红细胞部分自添值
	private String redOther;// varchar(100),--红细胞部分其他
	// --血小板
	private String PLT;// varchar(20),
	private String MPV;// varchar(20),
	private String PDW;// varchar(20),
	private String PVT;// varchar(20),
	private String plateletOther;// varchar(100),--血小板部分其他

	private String checkEnd;// ntext,--骨髓检查目的
	private String bloodSliceNum;// bigint, -- 血片张数
	private String marrowSliceNum;// bigint,-- 骨髓片张数
	private Date checkDate;// datetime,--送检时间
	private String doctorName;// varchar(20) --送检医师姓名

	public MarrowApply() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MarrowApply(Long id, Long patientId, Long historyCaseId,
			String patientName, String patientSex, String patientAge,
			String patientArea, String patientNum, String checkNum, String labNum,
			String clinicalShow, String clinicalDiagnosis, String wbc,
			String lym, String mon, String neu, String eos, String bas,
			String hgb, String rbc, String hct, String mcv, String mch,
			String mchc, String rdw, String ret, String lic, String redName,
			String redValue, String redOther, String plt, String mpv,
			String pdw, String pvt, String plateletOther, String checkEnd,
			String bloodSliceNum, String marrowSliceNum, Date checkDate,
			String doctorName) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.historyCaseId = historyCaseId;
		this.patientName = patientName;
		this.patientSex = patientSex;
		this.patientAge = patientAge;
		this.patientArea = patientArea;
		this.patientNum = patientNum;
		this.checkNum = checkNum;
		this.labNum = labNum;
		this.clinicalShow = clinicalShow;
		this.clinicalDiagnosis = clinicalDiagnosis;
		WBC = wbc;
		LYM = lym;
		MON = mon;
		NEU = neu;
		EOS = eos;
		BAS = bas;
		HGB = hgb;
		RBC = rbc;
		HCT = hct;
		MCV = mcv;
		MCH = mch;
		MCHC = mchc;
		RDW = rdw;
		Ret = ret;
		LIC = lic;
		this.redName = redName;
		this.redValue = redValue;
		this.redOther = redOther;
		PLT = plt;
		MPV = mpv;
		PDW = pdw;
		PVT = pvt;
		this.plateletOther = plateletOther;
		this.checkEnd = checkEnd;
		this.bloodSliceNum = bloodSliceNum;
		this.marrowSliceNum = marrowSliceNum;
		this.checkDate = checkDate;
		this.doctorName = doctorName;
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

	public String getPatientSex() {
		return patientSex;
	}

	public void setPatientSex(String patientSex) {
		this.patientSex = patientSex;
	}

	public String getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(String patientAge) {
		this.patientAge = patientAge;
	}

	public String getPatientArea() {
		return patientArea;
	}

	public void setPatientArea(String patientArea) {
		this.patientArea = patientArea;
	}

	public String getPatientNum() {
		return patientNum;
	}

	public void setPatientNum(String patientNum) {
		this.patientNum = patientNum;
	}

	public String getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	public String getLabNum() {
		return labNum;
	}

	public void setLabNum(String labNum) {
		this.labNum = labNum;
	}

	public String getClinicalShow() {
		return clinicalShow;
	}

	public void setClinicalShow(String clinicalShow) {
		this.clinicalShow = clinicalShow;
	}

	public String getClinicalDiagnosis() {
		return clinicalDiagnosis;
	}

	public void setClinicalDiagnosis(String clinicalDiagnosis) {
		this.clinicalDiagnosis = clinicalDiagnosis;
	}

	public String getWBC() {
		return WBC;
	}

	public void setWBC(String wbc) {
		WBC = wbc;
	}

	public String getLYM() {
		return LYM;
	}

	public void setLYM(String lym) {
		LYM = lym;
	}

	public String getMON() {
		return MON;
	}

	public void setMON(String mon) {
		MON = mon;
	}

	public String getNEU() {
		return NEU;
	}

	public void setNEU(String neu) {
		NEU = neu;
	}

	public String getEOS() {
		return EOS;
	}

	public void setEOS(String eos) {
		EOS = eos;
	}

	public String getBAS() {
		return BAS;
	}

	public void setBAS(String bas) {
		BAS = bas;
	}

	public String getHGB() {
		return HGB;
	}

	public void setHGB(String hgb) {
		HGB = hgb;
	}

	public String getRBC() {
		return RBC;
	}

	public void setRBC(String rbc) {
		RBC = rbc;
	}

	public String getHCT() {
		return HCT;
	}

	public void setHCT(String hct) {
		HCT = hct;
	}

	public String getMCV() {
		return MCV;
	}

	public void setMCV(String mcv) {
		MCV = mcv;
	}

	public String getMCH() {
		return MCH;
	}

	public void setMCH(String mch) {
		MCH = mch;
	}

	public String getMCHC() {
		return MCHC;
	}

	public void setMCHC(String mchc) {
		MCHC = mchc;
	}

	public String getRDW() {
		return RDW;
	}

	public void setRDW(String rdw) {
		RDW = rdw;
	}

	public String getRet() {
		return this.Ret;
	}

	public void setRet(String ret) {
		this.Ret = ret;
	}

	public String getLIC() {
		return LIC;
	}

	public void setLIC(String lic) {
		LIC = lic;
	}

	public String getRedName() {
		return redName;
	}

	public void setRedName(String redName) {
		this.redName = redName;
	}

	public String getRedValue() {
		return redValue;
	}

	public void setRedValue(String redValue) {
		this.redValue = redValue;
	}

	public String getRedOther() {
		return redOther;
	}

	public void setRedOther(String redOther) {
		this.redOther = redOther;
	}

	public String getPLT() {
		return PLT;
	}

	public void setPLT(String plt) {
		PLT = plt;
	}

	public String getMPV() {
		return MPV;
	}

	public void setMPV(String mpv) {
		MPV = mpv;
	}

	public String getPDW() {
		return PDW;
	}

	public void setPDW(String pdw) {
		PDW = pdw;
	}

	public String getPVT() {
		return PVT;
	}

	public void setPVT(String pvt) {
		PVT = pvt;
	}

	public String getPlateletOther() {
		return plateletOther;
	}

	public void setPlateletOther(String plateletOther) {
		this.plateletOther = plateletOther;
	}

	public String getCheckEnd() {
		return checkEnd;
	}

	public void setCheckEnd(String checkEnd) {
		this.checkEnd = checkEnd;
	}

	public String getBloodSliceNum() {
		return bloodSliceNum;
	}

	public void setBloodSliceNum(String bloodSliceNum) {
		this.bloodSliceNum = bloodSliceNum;
	}

	public String getMarrowSliceNum() {
		return marrowSliceNum;
	}

	public void setMarrowSliceNum(String marrowSliceNum) {
		this.marrowSliceNum = marrowSliceNum;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

}
