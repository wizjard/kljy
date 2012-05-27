package com.juncsoft.KLJY.InHospitalCase.Apply.entity;

import java.util.Date;

/**
 * LiverCheckApply 肝活病理检查申请单
 */
public class LiverCheckApply {
	private Long id;// bigint,--id主键
	private Long patientId;// bigint,--病人id
	private Long historyCaseId;// bigint,--病历组件id
	private String patientName;// varchar(100),--病人姓名
	private String gender;// varchar(100),--病人性别
	private String age;// varchar(100),--病人年龄
	private String career;// varchar(200),--病人职业
	private String depRoom;// varchar(200),--科室
	private String hosNum;// bigint,--住院号
	private String pathobiologyNum;// bigint,--病理号
	private String phoneNum;// varchar(100),--电话
	private Date acceptDate;// datetime,--收到日期
	private String liverHistory;// ntext,--肝病史简介(体征)
	private String familyHistory;// ntext,--家族史
	private String operationHistory;// ntext,--手术史
	private String drinkYear;// varchar(100),--饮酒时间_____年
	private String everyDayGram;// varchar(100),--每日______克
	private String bloodHistory;// ntext,--输血史
	private String drugHistory;// ntext,--用药史
	private String checkedPathobiologyNum;// bigint,--检查过的病理号
	private String benWaiYuan;// varchar(200),--本院或外院 本院，外院，无

	// --肝脏功能检查部分(肝穿同期)
	private Date dateOne;// datetime,-- 日期1
	private Date dateTwo;// datetime,--日期2
	private String altOne;// varchar(100),--ALT1
	private String altTwo;// varchar(100),--ALT2
	private String astOne;// varchar(100),--AST1
	private String astTwo;// varchar(100),--AST2
	private String alpOne;// varchar(100),--ALP1
	private String alpTwo;// varchar(100),--ALP2
	private String ggtOne;// varchar(100),--GGT1
	private String ggtTwo;// varchar(100),--GGT2
	private String tbilOne;// varchar(100),--TBIL1
	private String tbilTwo;// varchar(100),--TBIL2
	private String dbilOne;// varchar(100),--DBIL1
	private String dbilTwo;// varchar(100),--DBIL2
	private String tbaOne;// varchar(100),--TBA1
	private String tbaTwo;// varchar(100),--TBA2
	private String asOne;// varchar(100),--A1
	private String asTow;// varchar(100),--A2
	private String gsOne;// varchar(100),--G1
	private String gsTwo;// varchar(100),--G2
	private String ptaOne;// varchar(100),--PTA1
	private String ptaTwo;// varchar(100),--PTA2
	private String otherOne;// varchar(100),--其他1
	private String otherTwo;// varchar(100),--其他2
	// --病毒标志物血清学检查
	private String hbsag;// varchar(100),--HBsAG
	private String hbsab;// varchar(100),--HBsAB
	private String hbeab;// varchar(100),--HBeAB
	private String hbeag;// varchar(100),--HBeAg
	private String hbcab;// varchar(100),--HBcAb
	private String pres1ag;// varchar(100),--Pre-S1Ag
	private String hbvdna;// varchar(100),--HBVDNA
	private String khbclgm;// varchar(100),--抗HBc-lgM
	private String khavlgm;// varchar(100),--抗HAV-lgM
	private String khavlgg;// varchar(100),--抗HAV-lgG
	private String khcvlgm;// varchar(100),--抗HCV-lgM
	private String khcvlgg;// varchar(100),--抗HCV-lgG
	private String hcvrna;// varchar(100),--HCVRNA
	private String hdv;// varchar(100),--HDV
	private String khevlgm;// varchar(100),--抗HEV-lgM
	private String khevlgg;// varchar(100),--抗HEV-lgG
	private String ebv;// varchar(100),--嗜肝病毒EBV
	private String cmv;// varchar(100),--嗜肝病毒CMV
	private String virusOther;// ntext,--嗜肝病毒其他
	private String antibody;// ntext,--自身抗体
	private String liverSing;// ntext,--肿瘤标志物
	private String bloodFat;// ntext,--血脂
	// --肝移植专填内容
	private String liverDiagnose;// ntext,--原发肝病病历诊断
	private String concentration;// ntext,--血药浓度
	// --B超结果--
	private String liverDeep;// varchar(100),--肝脏左叶厚度
	private String liverLength;// varchar(100),--肝脏左叶长度
	private String liverSlope;// varchar(100),--肝脏右叶斜
	private String inside;// varchar(100),--门静脉内径
	private String liverReecho;// varchar(100),--肝回声
	private String liverOccupy;// varchar(100),--肝占位
	private String occupySize;// varchar(100),--大小
	private String spleenDeep;// varchar(100),--脾脏厚度
	private String spleenLength;// varchar(100),--脾脏长度
	private String spleenInside;// varchar(100),--脾静脉内径
	private String spleenOther;// ntext,--其他
	private String imageResult;// ntext,--其他影像学结果
	private String clinicalDiagnosis;// ntext,--临床诊断
	private String doctorName;// varchar(200),--送检医师
	private Date checkDate;// datetime,--送检时间


	public LiverCheckApply() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public String getHbeag() {
		return hbeag;
	}



	public void setHbeag(String hbeag) {
		this.hbeag = hbeag;
	}



	public LiverCheckApply(Long id, Long patientId, Long historyCaseId,
			String patientName, String gender, String age, String career,
			String depRoom, String hosNum, String pathobiologyNum,
			String phoneNum, Date acceptDate, String liverHistory,
			String familyHistory, String operationHistory, String drinkYear,
			String everyDayGram, String bloodHistory, String drugHistory,
			String checkedPathobiologyNum, String benWaiYuan, Date dateOne,
			Date dateTwo, String altOne, String altTwo, String astOne,
			String astTwo, String alpOne, String alpTwo, String ggtOne,
			String ggtTwo, String tbilOne, String tbilTwo, String dbilOne,
			String dbilTwo, String tbaOne, String tbaTwo, String asOne,
			String asTow, String gsOne, String gsTwo, String ptaOne,
			String ptaTwo, String otherOne, String otherTwo, String hbsag,
			String hbsab, String hbeab, String hbeag, String hbcab,
			String pres1ag, String hbvdna, String khbclgm, String khavlgm,
			String khavlgg, String khcvlgm, String khcvlgg, String hcvrna,
			String hdv, String khevlgm, String khevlgg, String ebv, String cmv,
			String virusOther, String antibody, String liverSing,
			String bloodFat, String liverDiagnose, String concentration,
			String liverDeep, String liverLength, String liverSlope,
			String inside, String liverReecho, String liverOccupy,
			String occupySize, String spleenDeep, String spleenLength,
			String spleenInside, String spleenOther, String imageResult,
			String clinicalDiagnosis, String doctorName, Date checkDate) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.historyCaseId = historyCaseId;
		this.patientName = patientName;
		this.gender = gender;
		this.age = age;
		this.career = career;
		this.depRoom = depRoom;
		this.hosNum = hosNum;
		this.pathobiologyNum = pathobiologyNum;
		this.phoneNum = phoneNum;
		this.acceptDate = acceptDate;
		this.liverHistory = liverHistory;
		this.familyHistory = familyHistory;
		this.operationHistory = operationHistory;
		this.drinkYear = drinkYear;
		this.everyDayGram = everyDayGram;
		this.bloodHistory = bloodHistory;
		this.drugHistory = drugHistory;
		this.checkedPathobiologyNum = checkedPathobiologyNum;
		this.benWaiYuan = benWaiYuan;
		this.dateOne = dateOne;
		this.dateTwo = dateTwo;
		this.altOne = altOne;
		this.altTwo = altTwo;
		this.astOne = astOne;
		this.astTwo = astTwo;
		this.alpOne = alpOne;
		this.alpTwo = alpTwo;
		this.ggtOne = ggtOne;
		this.ggtTwo = ggtTwo;
		this.tbilOne = tbilOne;
		this.tbilTwo = tbilTwo;
		this.dbilOne = dbilOne;
		this.dbilTwo = dbilTwo;
		this.tbaOne = tbaOne;
		this.tbaTwo = tbaTwo;
		this.asOne = asOne;
		this.asTow = asTow;
		this.gsOne = gsOne;
		this.gsTwo = gsTwo;
		this.ptaOne = ptaOne;
		this.ptaTwo = ptaTwo;
		this.otherOne = otherOne;
		this.otherTwo = otherTwo;
		this.hbsag = hbsag;
		this.hbsab = hbsab;
		this.hbeab = hbeab;
		this.hbeag = hbeag;
		this.hbcab = hbcab;
		this.pres1ag = pres1ag;
		this.hbvdna = hbvdna;
		this.khbclgm = khbclgm;
		this.khavlgm = khavlgm;
		this.khavlgg = khavlgg;
		this.khcvlgm = khcvlgm;
		this.khcvlgg = khcvlgg;
		this.hcvrna = hcvrna;
		this.hdv = hdv;
		this.khevlgm = khevlgm;
		this.khevlgg = khevlgg;
		this.ebv = ebv;
		this.cmv = cmv;
		this.virusOther = virusOther;
		this.antibody = antibody;
		this.liverSing = liverSing;
		this.bloodFat = bloodFat;
		this.liverDiagnose = liverDiagnose;
		this.concentration = concentration;
		this.liverDeep = liverDeep;
		this.liverLength = liverLength;
		this.liverSlope = liverSlope;
		this.inside = inside;
		this.liverReecho = liverReecho;
		this.liverOccupy = liverOccupy;
		this.occupySize = occupySize;
		this.spleenDeep = spleenDeep;
		this.spleenLength = spleenLength;
		this.spleenInside = spleenInside;
		this.spleenOther = spleenOther;
		this.imageResult = imageResult;
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

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getDepRoom() {
		return depRoom;
	}

	public void setDepRoom(String depRoom) {
		this.depRoom = depRoom;
	}

	public String getHosNum() {
		return hosNum;
	}

	public void setHosNum(String hosNum) {
		this.hosNum = hosNum;
	}

	public String getPathobiologyNum() {
		return pathobiologyNum;
	}

	public void setPathobiologyNum(String pathobiologyNum) {
		this.pathobiologyNum = pathobiologyNum;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getLiverHistory() {
		return liverHistory;
	}

	public void setLiverHistory(String liverHistory) {
		this.liverHistory = liverHistory;
	}

	public String getFamilyHistory() {
		return familyHistory;
	}

	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}

	public String getOperationHistory() {
		return operationHistory;
	}

	public void setOperationHistory(String operationHistory) {
		this.operationHistory = operationHistory;
	}

	public String getDrinkYear() {
		return drinkYear;
	}

	public void setDrinkYear(String drinkYear) {
		this.drinkYear = drinkYear;
	}

	public String getEveryDayGram() {
		return everyDayGram;
	}

	public void setEveryDayGram(String everyDayGram) {
		this.everyDayGram = everyDayGram;
	}

	public String getBloodHistory() {
		return bloodHistory;
	}

	public void setBloodHistory(String bloodHistory) {
		this.bloodHistory = bloodHistory;
	}

	public String getDrugHistory() {
		return drugHistory;
	}

	public void setDrugHistory(String drugHistory) {
		this.drugHistory = drugHistory;
	}

	public String getCheckedPathobiologyNum() {
		return checkedPathobiologyNum;
	}

	public void setCheckedPathobiologyNum(String checkedPathobiologyNum) {
		this.checkedPathobiologyNum = checkedPathobiologyNum;
	}

	public String getBenWaiYuan() {
		return benWaiYuan;
	}

	public void setBenWaiYuan(String benWaiYuan) {
		this.benWaiYuan = benWaiYuan;
	}

	public Date getDateOne() {
		return dateOne;
	}

	public void setDateOne(Date dateOne) {
		this.dateOne = dateOne;
	}

	public Date getDateTwo() {
		return dateTwo;
	}

	public void setDateTwo(Date dateTwo) {
		this.dateTwo = dateTwo;
	}

	public String getAltOne() {
		return altOne;
	}

	public void setAltOne(String altOne) {
		this.altOne = altOne;
	}

	public String getAltTwo() {
		return altTwo;
	}

	public void setAltTwo(String altTwo) {
		this.altTwo = altTwo;
	}

	public String getAstOne() {
		return astOne;
	}

	public void setAstOne(String astOne) {
		this.astOne = astOne;
	}

	public String getAstTwo() {
		return astTwo;
	}

	public void setAstTwo(String astTwo) {
		this.astTwo = astTwo;
	}

	public String getAlpOne() {
		return alpOne;
	}

	public void setAlpOne(String alpOne) {
		this.alpOne = alpOne;
	}

	public String getAlpTwo() {
		return alpTwo;
	}

	public void setAlpTwo(String alpTwo) {
		this.alpTwo = alpTwo;
	}

	public String getGgtOne() {
		return ggtOne;
	}

	public void setGgtOne(String ggtOne) {
		this.ggtOne = ggtOne;
	}

	public String getGgtTwo() {
		return ggtTwo;
	}

	public void setGgtTwo(String ggtTwo) {
		this.ggtTwo = ggtTwo;
	}

	public String getTbilOne() {
		return tbilOne;
	}

	public void setTbilOne(String tbilOne) {
		this.tbilOne = tbilOne;
	}

	public String getTbilTwo() {
		return tbilTwo;
	}

	public void setTbilTwo(String tbilTwo) {
		this.tbilTwo = tbilTwo;
	}

	public String getDbilOne() {
		return dbilOne;
	}

	public void setDbilOne(String dbilOne) {
		this.dbilOne = dbilOne;
	}

	public String getDbilTwo() {
		return dbilTwo;
	}

	public void setDbilTwo(String dbilTwo) {
		this.dbilTwo = dbilTwo;
	}

	public String getTbaOne() {
		return tbaOne;
	}

	public void setTbaOne(String tbaOne) {
		this.tbaOne = tbaOne;
	}

	public String getTbaTwo() {
		return tbaTwo;
	}

	public void setTbaTwo(String tbaTwo) {
		this.tbaTwo = tbaTwo;
	}

	public String getAsOne() {
		return asOne;
	}

	public void setAsOne(String asOne) {
		this.asOne = asOne;
	}

	public String getAsTow() {
		return asTow;
	}

	public void setAsTow(String asTow) {
		this.asTow = asTow;
	}

	public String getGsOne() {
		return gsOne;
	}

	public void setGsOne(String gsOne) {
		this.gsOne = gsOne;
	}

	public String getGsTwo() {
		return gsTwo;
	}

	public void setGsTwo(String gsTwo) {
		this.gsTwo = gsTwo;
	}

	public String getPtaOne() {
		return ptaOne;
	}

	public void setPtaOne(String ptaOne) {
		this.ptaOne = ptaOne;
	}

	public String getPtaTwo() {
		return ptaTwo;
	}

	public void setPtaTwo(String ptaTwo) {
		this.ptaTwo = ptaTwo;
	}

	public String getOtherOne() {
		return otherOne;
	}

	public void setOtherOne(String otherOne) {
		this.otherOne = otherOne;
	}

	public String getOtherTwo() {
		return otherTwo;
	}

	public void setOtherTwo(String otherTwo) {
		this.otherTwo = otherTwo;
	}

	public String getHbsag() {
		return hbsag;
	}

	public void setHbsag(String hbsag) {
		this.hbsag = hbsag;
	}

	public String getHbsab() {
		return hbsab;
	}

	public void setHbsab(String hbsab) {
		this.hbsab = hbsab;
	}

	public String getHbeab() {
		return hbeab;
	}

	public void setHbeab(String hbeab) {
		this.hbeab = hbeab;
	}

	public String getHbcab() {
		return hbcab;
	}

	public void setHbcab(String hbcab) {
		this.hbcab = hbcab;
	}

	public String getPres1ag() {
		return pres1ag;
	}

	public void setPres1ag(String pres1ag) {
		this.pres1ag = pres1ag;
	}

	public String getHbvdna() {
		return hbvdna;
	}

	public void setHbvdna(String hbvdna) {
		this.hbvdna = hbvdna;
	}

	public String getKhbclgm() {
		return khbclgm;
	}

	public void setKhbclgm(String khbclgm) {
		this.khbclgm = khbclgm;
	}

	public String getKhavlgm() {
		return khavlgm;
	}

	public void setKhavlgm(String khavlgm) {
		this.khavlgm = khavlgm;
	}

	public String getKhavlgg() {
		return khavlgg;
	}

	public void setKhavlgg(String khavlgg) {
		this.khavlgg = khavlgg;
	}

	public String getKhcvlgm() {
		return khcvlgm;
	}

	public void setKhcvlgm(String khcvlgm) {
		this.khcvlgm = khcvlgm;
	}

	public String getKhcvlgg() {
		return khcvlgg;
	}

	public void setKhcvlgg(String khcvlgg) {
		this.khcvlgg = khcvlgg;
	}

	public String getHcvrna() {
		return hcvrna;
	}

	public void setHcvrna(String hcvrna) {
		this.hcvrna = hcvrna;
	}

	public String getHdv() {
		return hdv;
	}

	public void setHdv(String hdv) {
		this.hdv = hdv;
	}

	public String getKhevlgm() {
		return khevlgm;
	}

	public void setKhevlgm(String khevlgm) {
		this.khevlgm = khevlgm;
	}

	public String getKhevlgg() {
		return khevlgg;
	}

	public void setKhevlgg(String khevlgg) {
		this.khevlgg = khevlgg;
	}

	public String getEbv() {
		return ebv;
	}

	public void setEbv(String ebv) {
		this.ebv = ebv;
	}

	public String getCmv() {
		return cmv;
	}

	public void setCmv(String cmv) {
		this.cmv = cmv;
	}

	public String getVirusOther() {
		return virusOther;
	}

	public void setVirusOther(String virusOther) {
		this.virusOther = virusOther;
	}

	public String getAntibody() {
		return antibody;
	}

	public void setAntibody(String antibody) {
		this.antibody = antibody;
	}

	public String getLiverSing() {
		return liverSing;
	}

	public void setLiverSing(String liverSing) {
		this.liverSing = liverSing;
	}

	public String getBloodFat() {
		return bloodFat;
	}

	public void setBloodFat(String bloodFat) {
		this.bloodFat = bloodFat;
	}

	public String getLiverDiagnose() {
		return liverDiagnose;
	}

	public void setLiverDiagnose(String liverDiagnose) {
		this.liverDiagnose = liverDiagnose;
	}

	public String getConcentration() {
		return concentration;
	}

	public void setConcentration(String concentration) {
		this.concentration = concentration;
	}

	public String getLiverDeep() {
		return liverDeep;
	}

	public void setLiverDeep(String liverDeep) {
		this.liverDeep = liverDeep;
	}

	public String getLiverLength() {
		return liverLength;
	}

	public void setLiverLength(String liverLength) {
		this.liverLength = liverLength;
	}

	public String getLiverSlope() {
		return liverSlope;
	}

	public void setLiverSlope(String liverSlope) {
		this.liverSlope = liverSlope;
	}

	public String getInside() {
		return inside;
	}

	public void setInside(String inside) {
		this.inside = inside;
	}

	public String getLiverReecho() {
		return liverReecho;
	}

	public void setLiverReecho(String liverReecho) {
		this.liverReecho = liverReecho;
	}

	public String getLiverOccupy() {
		return liverOccupy;
	}

	public void setLiverOccupy(String liverOccupy) {
		this.liverOccupy = liverOccupy;
	}

	public String getOccupySize() {
		return occupySize;
	}

	public void setOccupySize(String occupySize) {
		this.occupySize = occupySize;
	}

	public String getSpleenDeep() {
		return spleenDeep;
	}

	public void setSpleenDeep(String spleenDeep) {
		this.spleenDeep = spleenDeep;
	}

	public String getSpleenLength() {
		return spleenLength;
	}

	public void setSpleenLength(String spleenLength) {
		this.spleenLength = spleenLength;
	}

	public String getSpleenInside() {
		return spleenInside;
	}

	public void setSpleenInside(String spleenInside) {
		this.spleenInside = spleenInside;
	}

	public String getSpleenOther() {
		return spleenOther;
	}

	public void setSpleenOther(String spleenOther) {
		this.spleenOther = spleenOther;
	}

	public String getImageResult() {
		return imageResult;
	}

	public void setImageResult(String imageResult) {
		this.imageResult = imageResult;
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
