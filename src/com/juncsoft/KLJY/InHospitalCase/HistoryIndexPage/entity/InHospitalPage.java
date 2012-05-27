package com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity;

import java.util.Date;

/**
 * 病案首页
 * @author Administrator
 */
public class InHospitalPage {
	private Long id;
	private Long caseId;
	
	private String patientName;
	private String gender;

	private Date birthday;
	private String patientNo;
	private String nationality;
	private String idNo;
	private String people;
	private String occupation;
	private String marrageStatus;
	private String workUnitAddr;
	private String workUnitPostCode;
	private String workUnitTel;
	private String contacterName;
	private String contacterTel;
	private String contacterRelationship;
	
	private String age;
	private String xno;
	private String mriNo;
	private String ctNo;
	private Integer inHspTimes;
	private String homeAddr;
	private String homePostCode;
	private Date inHspDate;
	private Date outHspDate;
	private Integer hspDate2;
	private String inWardCode;
	private String inWard;
	private String outHspWard;
	private String outWard;
	private String inStatus;
	private String inIllsShow;
	private Date queding_checkdate;
	
	private String policymanualNo;
	private String menzhenhao;
	private String typeOfPayment;

	private String birthplace;
	
	/*出生地*/
	private String province;
	private String city;
	private String area;
	
	private String contacterAdd;
	private String menzhen;
	
	private String outIlls1;
	private Integer leaveHospital1;
	private String icd1;

	private String outIlls2;
	private Integer leaveHospital2;
	private String icd2;
	
	private String outIlls3;
	private Integer leaveHospital3;
	private String icd3;
	
	private String outIlls4;
	private Integer leaveHospital4;
	private String icd4;
	
	private String outIlls5;
	private Integer leaveHospital5;
	private String icd5;
	
	private String outIlls6;
	private Integer leaveHospital6;
	private String icd6;
	
	private String outIlls7;
	private Integer leaveHospital7;
	private String icd7;
	
	private String outIlls8;
	private Integer leaveHospital8;
	private String icd8;

	private String ganran;
	private String pathology;
	private String harm;
	private String drugAllergy;
	private String hbsag;
	private String hcvab;
	private String hivab;
	private String contagion;
	private String baoka;
	
	private String menzhen_chuyuan;
	private String ruyuan_chuyuan;
	private String shuqian_shuhou;
	private String linchuang_bingli;
	
	private String fangshe_bingli;
	private String qiangjiu;
	private String succed;
	private String kezhuren_show;
	private String directorDoctorId_show;
	private String treatDoctorId_show;
	private String inhspDoctorId_show;

	private String jinxiu_show;
	private String yanjiusheng_show;
	private String shixi_show;
	private String bianma;
	private String bingan;
	private String qcyishi;
	private String qchushi;
	private Date badate;

	private String operationNo;
	private Date tdate;
	private String surgeryName;
	private String surgeryDocName;
	private String surgeryAssistant1;
	private String surgeryAssistant2;
	private String anesthesiaMode;
	private String cut;
	private String anesthesiologist;
	
	private String operationNo2;
	private Date tdate2;
	private String surgeryName2;
	private String surgeryDocName2;
	private String surgeryAssistant21;
	private String surgeryAssistant22;
	private String anesthesiaMode2;
	private String cut2;
	private String anesthesiologist2;
	
	private String operationNo3;
	private Date tdate3;
	private String surgeryName3;
	private String surgeryDocName3;
	private String surgeryAssistant31;
	private String surgeryAssistant32;
	private String anesthesiaMode3;
	private String cut3;
	private String anesthesiologist3;

	private String operationNo4;
	private Date tdate4;
	private String surgeryName4;
	private String surgeryDocName4;
	private String surgeryAssistant41;
	private String surgeryAssistant42;
	private String anesthesiaMode4;
	private String cut4;
	private String anesthesiologist4;
	
	private String operationNo5;
	private Date tdate5;
	private String surgeryName5;
	private String surgeryDocName5;
	private String surgeryAssistant51;
	private String surgeryAssistant52;
	private String anesthesiaMode5;
	private String cut5;
	private String anesthesiologist5;
	
	/* 护理级别 */
	private String criticalCare;
	private String oneRank;
	private String twoRank;
	private String threeRank;
	private String icu;
	private String ccu;

	/* 监护 */
	private String icuName1;
	private Date icuTurnInto1;
	private Date icuDropOut1;

	private String icuName2;
	private Date icuTurnInto2;
	private Date icuDropOut2;

	private String icuName3;
	private Date icuTurnInto3;
	private Date icuDropOut3;

	private String hixi;
	private String hunmi_ruyuanqian_hour;
	private String hunmi_ruyuanqian_minute;
	private String hunmi_ruyuanhou_hour;
	private String hunmi_ruyuanhou_minute;
	private String liyuanType;
	private String shiftTo;
	private String obstetrics;
	private String obs_checkUp;
	private String obs_checkUp_times;
	private Date deliveryTime;
	private String weight;
	private String obs_totalTime_hour;
	private String obs_totalTime_minute;
	private String deliveryType;
	private String obs_amountOfBleeding;
	private String birth_position;
	private String apgar;
	private String healthCare;
	private String gcaaoc;

	private String neonatus;
	private String neonatus_sex;
	private String neonatus_ruyuan_weight;
	private String neonatus_chuyuan_weight;
	private String feedType;
	private String bcg;
	private String yigan;
	private String hibg;
	private String neonatus_disease1;
	private String neonatus_disease2;
	private String neonatus_disease3;
	private String autopsy;
	private String ops_treat_checkUp_dia;
	private String followUpClinic;
	private String fuc_timelimit;
	private String fuc_timelimit_unit;
	private String teach;
	private String bloodType;
	private String rh;
	private String transfusion;
	private String tf_variety;
	private String redBloodCell_unit;
	private String bloodCells;
	private String bloodPlasma;
	private String wholeBlood;
	private String auto_blood;
	private String other_blood;

	private String idType;
	private String creditWard;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCaseId() {
		return caseId;
	}
	public void setCaseId(Long caseId) {
		this.caseId = caseId;
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPatientNo() {
		return patientNo;
	}
	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getPeople() {
		return people;
	}
	public void setPeople(String people) {
		this.people = people;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getMarrageStatus() {
		return marrageStatus;
	}
	public void setMarrageStatus(String marrageStatus) {
		this.marrageStatus = marrageStatus;
	}
	public String getWorkUnitAddr() {
		return workUnitAddr;
	}
	public void setWorkUnitAddr(String workUnitAddr) {
		this.workUnitAddr = workUnitAddr;
	}
	public String getWorkUnitPostCode() {
		return workUnitPostCode;
	}
	public void setWorkUnitPostCode(String workUnitPostCode) {
		this.workUnitPostCode = workUnitPostCode;
	}
	public String getWorkUnitTel() {
		return workUnitTel;
	}
	public void setWorkUnitTel(String workUnitTel) {
		this.workUnitTel = workUnitTel;
	}
	public String getContacterName() {
		return contacterName;
	}
	public void setContacterName(String contacterName) {
		this.contacterName = contacterName;
	}
	public String getContacterTel() {
		return contacterTel;
	}
	public void setContacterTel(String contacterTel) {
		this.contacterTel = contacterTel;
	}
	public String getContacterRelationship() {
		return contacterRelationship;
	}
	public void setContacterRelationship(String contacterRelationship) {
		this.contacterRelationship = contacterRelationship;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getXno() {
		return xno;
	}
	public void setXno(String xno) {
		this.xno = xno;
	}
	public String getMriNo() {
		return mriNo;
	}
	public void setMriNo(String mriNo) {
		this.mriNo = mriNo;
	}
	public String getCtNo() {
		return ctNo;
	}
	public void setCtNo(String ctNo) {
		this.ctNo = ctNo;
	}
	public Integer getInHspTimes() {
		return inHspTimes;
	}
	public void setInHspTimes(Integer inHspTimes) {
		this.inHspTimes = inHspTimes;
	}
	public String getHomeAddr() {
		return homeAddr;
	}
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}
	public String getHomePostCode() {
		return homePostCode;
	}
	public void setHomePostCode(String homePostCode) {
		this.homePostCode = homePostCode;
	}
	public Date getInHspDate() {
		return inHspDate;
	}
	public void setInHspDate(Date inHspDate) {
		this.inHspDate = inHspDate;
	}
	public Date getOutHspDate() {
		return outHspDate;
	}
	public void setOutHspDate(Date outHspDate) {
		this.outHspDate = outHspDate;
	}
	public Integer getHspDate2() {
		return hspDate2;
	}
	public void setHspDate2(Integer hspDate2) {
		this.hspDate2 = hspDate2;
	}
	public String getInWardCode() {
		return inWardCode;
	}
	public void setInWardCode(String inWardCode) {
		this.inWardCode = inWardCode;
	}
	public String getInWard() {
		return inWard;
	}
	public void setInWard(String inWard) {
		this.inWard = inWard;
	}
	public String getOutHspWard() {
		return outHspWard;
	}
	public void setOutHspWard(String outHspWard) {
		this.outHspWard = outHspWard;
	}
	public String getOutWard() {
		return outWard;
	}
	public void setOutWard(String outWard) {
		this.outWard = outWard;
	}
	public String getInStatus() {
		return inStatus;
	}
	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
	}
	public String getInIllsShow() {
		return inIllsShow;
	}
	public void setInIllsShow(String inIllsShow) {
		this.inIllsShow = inIllsShow;
	}
	public Date getQueding_checkdate() {
		return queding_checkdate;
	}
	public void setQueding_checkdate(Date queding_checkdate) {
		this.queding_checkdate = queding_checkdate;
	}
	public String getPolicymanualNo() {
		return policymanualNo;
	}
	public void setPolicymanualNo(String policymanualNo) {
		this.policymanualNo = policymanualNo;
	}
	public String getMenzhenhao() {
		return menzhenhao;
	}
	public void setMenzhenhao(String menzhenhao) {
		this.menzhenhao = menzhenhao;
	}
	public String getTypeOfPayment() {
		return typeOfPayment;
	}
	public void setTypeOfPayment(String typeOfPayment) {
		this.typeOfPayment = typeOfPayment;
	}
	public String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	public String getContacterAdd() {
		return contacterAdd;
	}
	public void setContacterAdd(String contacterAdd) {
		this.contacterAdd = contacterAdd;
	}
	public String getMenzhen() {
		return menzhen;
	}
	public void setMenzhen(String menzhen) {
		this.menzhen = menzhen;
	}
	public String getOutIlls1() {
		return outIlls1;
	}
	public void setOutIlls1(String outIlls1) {
		this.outIlls1 = outIlls1;
	}
	public Integer getLeaveHospital1() {
		return leaveHospital1;
	}
	public void setLeaveHospital1(Integer leaveHospital1) {
		this.leaveHospital1 = leaveHospital1;
	}
	public String getIcd1() {
		return icd1;
	}
	public void setIcd1(String icd1) {
		this.icd1 = icd1;
	}
	public String getOutIlls2() {
		return outIlls2;
	}
	public void setOutIlls2(String outIlls2) {
		this.outIlls2 = outIlls2;
	}
	public Integer getLeaveHospital2() {
		return leaveHospital2;
	}
	public void setLeaveHospital2(Integer leaveHospital2) {
		this.leaveHospital2 = leaveHospital2;
	}
	public String getIcd2() {
		return icd2;
	}
	public void setIcd2(String icd2) {
		this.icd2 = icd2;
	}
	public String getOutIlls3() {
		return outIlls3;
	}
	public void setOutIlls3(String outIlls3) {
		this.outIlls3 = outIlls3;
	}
	public Integer getLeaveHospital3() {
		return leaveHospital3;
	}
	public void setLeaveHospital3(Integer leaveHospital3) {
		this.leaveHospital3 = leaveHospital3;
	}
	public String getIcd3() {
		return icd3;
	}
	public void setIcd3(String icd3) {
		this.icd3 = icd3;
	}
	public String getOutIlls4() {
		return outIlls4;
	}
	public void setOutIlls4(String outIlls4) {
		this.outIlls4 = outIlls4;
	}
	public Integer getLeaveHospital4() {
		return leaveHospital4;
	}
	public void setLeaveHospital4(Integer leaveHospital4) {
		this.leaveHospital4 = leaveHospital4;
	}
	public String getIcd4() {
		return icd4;
	}
	public void setIcd4(String icd4) {
		this.icd4 = icd4;
	}
	public String getOutIlls5() {
		return outIlls5;
	}
	public void setOutIlls5(String outIlls5) {
		this.outIlls5 = outIlls5;
	}
	public Integer getLeaveHospital5() {
		return leaveHospital5;
	}
	public void setLeaveHospital5(Integer leaveHospital5) {
		this.leaveHospital5 = leaveHospital5;
	}
	public String getIcd5() {
		return icd5;
	}
	public void setIcd5(String icd5) {
		this.icd5 = icd5;
	}
	public String getOutIlls6() {
		return outIlls6;
	}
	public void setOutIlls6(String outIlls6) {
		this.outIlls6 = outIlls6;
	}
	public Integer getLeaveHospital6() {
		return leaveHospital6;
	}
	public void setLeaveHospital6(Integer leaveHospital6) {
		this.leaveHospital6 = leaveHospital6;
	}
	public String getIcd6() {
		return icd6;
	}
	public void setIcd6(String icd6) {
		this.icd6 = icd6;
	}
	public String getOutIlls7() {
		return outIlls7;
	}
	public void setOutIlls7(String outIlls7) {
		this.outIlls7 = outIlls7;
	}
	public Integer getLeaveHospital7() {
		return leaveHospital7;
	}
	public void setLeaveHospital7(Integer leaveHospital7) {
		this.leaveHospital7 = leaveHospital7;
	}
	public String getIcd7() {
		return icd7;
	}
	public void setIcd7(String icd7) {
		this.icd7 = icd7;
	}
	public String getOutIlls8() {
		return outIlls8;
	}
	public void setOutIlls8(String outIlls8) {
		this.outIlls8 = outIlls8;
	}
	public Integer getLeaveHospital8() {
		return leaveHospital8;
	}
	public void setLeaveHospital8(Integer leaveHospital8) {
		this.leaveHospital8 = leaveHospital8;
	}
	public String getIcd8() {
		return icd8;
	}
	public void setIcd8(String icd8) {
		this.icd8 = icd8;
	}
	public String getGanran() {
		return ganran;
	}
	public void setGanran(String ganran) {
		this.ganran = ganran;
	}
	public String getPathology() {
		return pathology;
	}
	public void setPathology(String pathology) {
		this.pathology = pathology;
	}
	public String getHarm() {
		return harm;
	}
	public void setHarm(String harm) {
		this.harm = harm;
	}
	public String getDrugAllergy() {
		return drugAllergy;
	}
	public void setDrugAllergy(String drugAllergy) {
		this.drugAllergy = drugAllergy;
	}
	public String getHbsag() {
		return hbsag;
	}
	public void setHbsag(String hbsag) {
		this.hbsag = hbsag;
	}
	public String getHcvab() {
		return hcvab;
	}
	public void setHcvab(String hcvab) {
		this.hcvab = hcvab;
	}
	public String getHivab() {
		return hivab;
	}
	public void setHivab(String hivab) {
		this.hivab = hivab;
	}
	public String getContagion() {
		return contagion;
	}
	public void setContagion(String contagion) {
		this.contagion = contagion;
	}
	public String getBaoka() {
		return baoka;
	}
	public void setBaoka(String baoka) {
		this.baoka = baoka;
	}
	public String getFangshe_bingli() {
		return fangshe_bingli;
	}
	public void setFangshe_bingli(String fangshe_bingli) {
		this.fangshe_bingli = fangshe_bingli;
	}
	public String getQiangjiu() {
		return qiangjiu;
	}
	public void setQiangjiu(String qiangjiu) {
		this.qiangjiu = qiangjiu;
	}
	public String getSucced() {
		return succed;
	}
	public void setSucced(String succed) {
		this.succed = succed;
	}
	public String getKezhuren_show() {
		return kezhuren_show;
	}
	public void setKezhuren_show(String kezhuren_show) {
		this.kezhuren_show = kezhuren_show;
	}
	public String getDirectorDoctorId_show() {
		return directorDoctorId_show;
	}
	public void setDirectorDoctorId_show(String directorDoctorId_show) {
		this.directorDoctorId_show = directorDoctorId_show;
	}
	public String getTreatDoctorId_show() {
		return treatDoctorId_show;
	}
	public void setTreatDoctorId_show(String treatDoctorId_show) {
		this.treatDoctorId_show = treatDoctorId_show;
	}
	public String getInhspDoctorId_show() {
		return inhspDoctorId_show;
	}
	public void setInhspDoctorId_show(String inhspDoctorId_show) {
		this.inhspDoctorId_show = inhspDoctorId_show;
	}
	public String getJinxiu_show() {
		return jinxiu_show;
	}
	public void setJinxiu_show(String jinxiu_show) {
		this.jinxiu_show = jinxiu_show;
	}
	public String getYanjiusheng_show() {
		return yanjiusheng_show;
	}
	public void setYanjiusheng_show(String yanjiusheng_show) {
		this.yanjiusheng_show = yanjiusheng_show;
	}
	public String getShixi_show() {
		return shixi_show;
	}
	public void setShixi_show(String shixi_show) {
		this.shixi_show = shixi_show;
	}
	public String getBianma() {
		return bianma;
	}
	public void setBianma(String bianma) {
		this.bianma = bianma;
	}
	public String getBingan() {
		return bingan;
	}
	public void setBingan(String bingan) {
		this.bingan = bingan;
	}
	public String getQcyishi() {
		return qcyishi;
	}
	public void setQcyishi(String qcyishi) {
		this.qcyishi = qcyishi;
	}
	public String getQchushi() {
		return qchushi;
	}
	public void setQchushi(String qchushi) {
		this.qchushi = qchushi;
	}
	public Date getBadate() {
		return badate;
	}
	public void setBadate(Date badate) {
		this.badate = badate;
	}
	public String getOperationNo() {
		return operationNo;
	}
	public void setOperationNo(String operationNo) {
		this.operationNo = operationNo;
	}
	public Date getTdate() {
		return tdate;
	}
	public void setTdate(Date tdate) {
		this.tdate = tdate;
	}
	public String getSurgeryName() {
		return surgeryName;
	}
	public void setSurgeryName(String surgeryName) {
		this.surgeryName = surgeryName;
	}
	public String getSurgeryDocName() {
		return surgeryDocName;
	}
	public void setSurgeryDocName(String surgeryDocName) {
		this.surgeryDocName = surgeryDocName;
	}
	public String getSurgeryAssistant1() {
		return surgeryAssistant1;
	}
	public void setSurgeryAssistant1(String surgeryAssistant1) {
		this.surgeryAssistant1 = surgeryAssistant1;
	}
	public String getSurgeryAssistant2() {
		return surgeryAssistant2;
	}
	public void setSurgeryAssistant2(String surgeryAssistant2) {
		this.surgeryAssistant2 = surgeryAssistant2;
	}
	public String getAnesthesiaMode() {
		return anesthesiaMode;
	}
	public void setAnesthesiaMode(String anesthesiaMode) {
		this.anesthesiaMode = anesthesiaMode;
	}
	public String getCut() {
		return cut;
	}
	public void setCut(String cut) {
		this.cut = cut;
	}
	public String getAnesthesiologist() {
		return anesthesiologist;
	}
	public void setAnesthesiologist(String anesthesiologist) {
		this.anesthesiologist = anesthesiologist;
	}
	public String getOperationNo2() {
		return operationNo2;
	}
	public void setOperationNo2(String operationNo2) {
		this.operationNo2 = operationNo2;
	}
	public Date getTdate2() {
		return tdate2;
	}
	public void setTdate2(Date tdate2) {
		this.tdate2 = tdate2;
	}
	public String getSurgeryName2() {
		return surgeryName2;
	}
	public void setSurgeryName2(String surgeryName2) {
		this.surgeryName2 = surgeryName2;
	}
	public String getSurgeryDocName2() {
		return surgeryDocName2;
	}
	public void setSurgeryDocName2(String surgeryDocName2) {
		this.surgeryDocName2 = surgeryDocName2;
	}
	public String getSurgeryAssistant21() {
		return surgeryAssistant21;
	}
	public void setSurgeryAssistant21(String surgeryAssistant21) {
		this.surgeryAssistant21 = surgeryAssistant21;
	}
	public String getSurgeryAssistant22() {
		return surgeryAssistant22;
	}
	public void setSurgeryAssistant22(String surgeryAssistant22) {
		this.surgeryAssistant22 = surgeryAssistant22;
	}
	public String getAnesthesiaMode2() {
		return anesthesiaMode2;
	}
	public void setAnesthesiaMode2(String anesthesiaMode2) {
		this.anesthesiaMode2 = anesthesiaMode2;
	}
	public String getCut2() {
		return cut2;
	}
	public void setCut2(String cut2) {
		this.cut2 = cut2;
	}
	public String getAnesthesiologist2() {
		return anesthesiologist2;
	}
	public void setAnesthesiologist2(String anesthesiologist2) {
		this.anesthesiologist2 = anesthesiologist2;
	}
	public String getOperationNo3() {
		return operationNo3;
	}
	public void setOperationNo3(String operationNo3) {
		this.operationNo3 = operationNo3;
	}
	public Date getTdate3() {
		return tdate3;
	}
	public void setTdate3(Date tdate3) {
		this.tdate3 = tdate3;
	}
	public String getSurgeryName3() {
		return surgeryName3;
	}
	public void setSurgeryName3(String surgeryName3) {
		this.surgeryName3 = surgeryName3;
	}
	public String getSurgeryDocName3() {
		return surgeryDocName3;
	}
	public void setSurgeryDocName3(String surgeryDocName3) {
		this.surgeryDocName3 = surgeryDocName3;
	}
	public String getSurgeryAssistant31() {
		return surgeryAssistant31;
	}
	public void setSurgeryAssistant31(String surgeryAssistant31) {
		this.surgeryAssistant31 = surgeryAssistant31;
	}
	public String getSurgeryAssistant32() {
		return surgeryAssistant32;
	}
	public void setSurgeryAssistant32(String surgeryAssistant32) {
		this.surgeryAssistant32 = surgeryAssistant32;
	}
	public String getAnesthesiaMode3() {
		return anesthesiaMode3;
	}
	public void setAnesthesiaMode3(String anesthesiaMode3) {
		this.anesthesiaMode3 = anesthesiaMode3;
	}
	public String getCut3() {
		return cut3;
	}
	public void setCut3(String cut3) {
		this.cut3 = cut3;
	}
	public String getAnesthesiologist3() {
		return anesthesiologist3;
	}
	public void setAnesthesiologist3(String anesthesiologist3) {
		this.anesthesiologist3 = anesthesiologist3;
	}
	public String getOperationNo4() {
		return operationNo4;
	}
	public void setOperationNo4(String operationNo4) {
		this.operationNo4 = operationNo4;
	}
	public Date getTdate4() {
		return tdate4;
	}
	public void setTdate4(Date tdate4) {
		this.tdate4 = tdate4;
	}
	public String getSurgeryName4() {
		return surgeryName4;
	}
	public void setSurgeryName4(String surgeryName4) {
		this.surgeryName4 = surgeryName4;
	}
	public String getSurgeryDocName4() {
		return surgeryDocName4;
	}
	public void setSurgeryDocName4(String surgeryDocName4) {
		this.surgeryDocName4 = surgeryDocName4;
	}
	public String getSurgeryAssistant41() {
		return surgeryAssistant41;
	}
	public void setSurgeryAssistant41(String surgeryAssistant41) {
		this.surgeryAssistant41 = surgeryAssistant41;
	}
	public String getSurgeryAssistant42() {
		return surgeryAssistant42;
	}
	public void setSurgeryAssistant42(String surgeryAssistant42) {
		this.surgeryAssistant42 = surgeryAssistant42;
	}
	public String getAnesthesiaMode4() {
		return anesthesiaMode4;
	}
	public void setAnesthesiaMode4(String anesthesiaMode4) {
		this.anesthesiaMode4 = anesthesiaMode4;
	}
	public String getCut4() {
		return cut4;
	}
	public void setCut4(String cut4) {
		this.cut4 = cut4;
	}
	public String getAnesthesiologist4() {
		return anesthesiologist4;
	}
	public void setAnesthesiologist4(String anesthesiologist4) {
		this.anesthesiologist4 = anesthesiologist4;
	}
	public String getCriticalCare() {
		return criticalCare;
	}
	public void setCriticalCare(String criticalCare) {
		this.criticalCare = criticalCare;
	}
	public String getOneRank() {
		return oneRank;
	}
	public void setOneRank(String oneRank) {
		this.oneRank = oneRank;
	}
	public String getTwoRank() {
		return twoRank;
	}
	public void setTwoRank(String twoRank) {
		this.twoRank = twoRank;
	}
	public String getThreeRank() {
		return threeRank;
	}
	public void setThreeRank(String threeRank) {
		this.threeRank = threeRank;
	}
	public String getIcu() {
		return icu;
	}
	public void setIcu(String icu) {
		this.icu = icu;
	}
	public String getCcu() {
		return ccu;
	}
	public void setCcu(String ccu) {
		this.ccu = ccu;
	}
	public String getIcuName1() {
		return icuName1;
	}
	public void setIcuName1(String icuName1) {
		this.icuName1 = icuName1;
	}
	public Date getIcuTurnInto1() {
		return icuTurnInto1;
	}
	public void setIcuTurnInto1(Date icuTurnInto1) {
		this.icuTurnInto1 = icuTurnInto1;
	}
	public Date getIcuDropOut1() {
		return icuDropOut1;
	}
	public void setIcuDropOut1(Date icuDropOut1) {
		this.icuDropOut1 = icuDropOut1;
	}
	public String getIcuName2() {
		return icuName2;
	}
	public void setIcuName2(String icuName2) {
		this.icuName2 = icuName2;
	}
	public Date getIcuTurnInto2() {
		return icuTurnInto2;
	}
	public void setIcuTurnInto2(Date icuTurnInto2) {
		this.icuTurnInto2 = icuTurnInto2;
	}
	public Date getIcuDropOut2() {
		return icuDropOut2;
	}
	public void setIcuDropOut2(Date icuDropOut2) {
		this.icuDropOut2 = icuDropOut2;
	}
	public String getIcuName3() {
		return icuName3;
	}
	public void setIcuName3(String icuName3) {
		this.icuName3 = icuName3;
	}
	public Date getIcuTurnInto3() {
		return icuTurnInto3;
	}
	public void setIcuTurnInto3(Date icuTurnInto3) {
		this.icuTurnInto3 = icuTurnInto3;
	}
	public Date getIcuDropOut3() {
		return icuDropOut3;
	}
	public void setIcuDropOut3(Date icuDropOut3) {
		this.icuDropOut3 = icuDropOut3;
	}
	public String getHixi() {
		return hixi;
	}
	public void setHixi(String hixi) {
		this.hixi = hixi;
	}
	public String getHunmi_ruyuanqian_hour() {
		return hunmi_ruyuanqian_hour;
	}
	public void setHunmi_ruyuanqian_hour(String hunmi_ruyuanqian_hour) {
		this.hunmi_ruyuanqian_hour = hunmi_ruyuanqian_hour;
	}
	public String getHunmi_ruyuanqian_minute() {
		return hunmi_ruyuanqian_minute;
	}
	public void setHunmi_ruyuanqian_minute(String hunmi_ruyuanqian_minute) {
		this.hunmi_ruyuanqian_minute = hunmi_ruyuanqian_minute;
	}
	public String getHunmi_ruyuanhou_hour() {
		return hunmi_ruyuanhou_hour;
	}
	public void setHunmi_ruyuanhou_hour(String hunmi_ruyuanhou_hour) {
		this.hunmi_ruyuanhou_hour = hunmi_ruyuanhou_hour;
	}
	public String getHunmi_ruyuanhou_minute() {
		return hunmi_ruyuanhou_minute;
	}
	public void setHunmi_ruyuanhou_minute(String hunmi_ruyuanhou_minute) {
		this.hunmi_ruyuanhou_minute = hunmi_ruyuanhou_minute;
	}
	public String getLiyuanType() {
		return liyuanType;
	}
	public void setLiyuanType(String liyuanType) {
		this.liyuanType = liyuanType;
	}
	public String getShiftTo() {
		return shiftTo;
	}
	public void setShiftTo(String shiftTo) {
		this.shiftTo = shiftTo;
	}
	public String getObstetrics() {
		return obstetrics;
	}
	public void setObstetrics(String obstetrics) {
		this.obstetrics = obstetrics;
	}
	public String getObs_checkUp() {
		return obs_checkUp;
	}
	public void setObs_checkUp(String obs_checkUp) {
		this.obs_checkUp = obs_checkUp;
	}
	public String getObs_checkUp_times() {
		return obs_checkUp_times;
	}
	public void setObs_checkUp_times(String obs_checkUp_times) {
		this.obs_checkUp_times = obs_checkUp_times;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getObs_totalTime_hour() {
		return obs_totalTime_hour;
	}
	public void setObs_totalTime_hour(String obs_totalTime_hour) {
		this.obs_totalTime_hour = obs_totalTime_hour;
	}
	public String getObs_totalTime_minute() {
		return obs_totalTime_minute;
	}
	public void setObs_totalTime_minute(String obs_totalTime_minute) {
		this.obs_totalTime_minute = obs_totalTime_minute;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getObs_amountOfBleeding() {
		return obs_amountOfBleeding;
	}
	public void setObs_amountOfBleeding(String obs_amountOfBleeding) {
		this.obs_amountOfBleeding = obs_amountOfBleeding;
	}
	public String getBirth_position() {
		return birth_position;
	}
	public void setBirth_position(String birth_position) {
		this.birth_position = birth_position;
	}
	public String getApgar() {
		return apgar;
	}
	public void setApgar(String apgar) {
		this.apgar = apgar;
	}
	public String getHealthCare() {
		return healthCare;
	}
	public void setHealthCare(String healthCare) {
		this.healthCare = healthCare;
	}
	public String getGcaaoc() {
		return gcaaoc;
	}
	public void setGcaaoc(String gcaaoc) {
		this.gcaaoc = gcaaoc;
	}
	public String getNeonatus() {
		return neonatus;
	}
	public void setNeonatus(String neonatus) {
		this.neonatus = neonatus;
	}
	public String getNeonatus_sex() {
		return neonatus_sex;
	}
	public void setNeonatus_sex(String neonatus_sex) {
		this.neonatus_sex = neonatus_sex;
	}
	public String getNeonatus_ruyuan_weight() {
		return neonatus_ruyuan_weight;
	}
	public void setNeonatus_ruyuan_weight(String neonatus_ruyuan_weight) {
		this.neonatus_ruyuan_weight = neonatus_ruyuan_weight;
	}
	public String getNeonatus_chuyuan_weight() {
		return neonatus_chuyuan_weight;
	}
	public void setNeonatus_chuyuan_weight(String neonatus_chuyuan_weight) {
		this.neonatus_chuyuan_weight = neonatus_chuyuan_weight;
	}
	public String getFeedType() {
		return feedType;
	}
	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}
	public String getBcg() {
		return bcg;
	}
	public void setBcg(String bcg) {
		this.bcg = bcg;
	}
	public String getYigan() {
		return yigan;
	}
	public void setYigan(String yigan) {
		this.yigan = yigan;
	}
	public String getHibg() {
		return hibg;
	}
	public void setHibg(String hibg) {
		this.hibg = hibg;
	}
	public String getNeonatus_disease1() {
		return neonatus_disease1;
	}
	public void setNeonatus_disease1(String neonatus_disease1) {
		this.neonatus_disease1 = neonatus_disease1;
	}
	public String getNeonatus_disease2() {
		return neonatus_disease2;
	}
	public void setNeonatus_disease2(String neonatus_disease2) {
		this.neonatus_disease2 = neonatus_disease2;
	}
	public String getNeonatus_disease3() {
		return neonatus_disease3;
	}
	public void setNeonatus_disease3(String neonatus_disease3) {
		this.neonatus_disease3 = neonatus_disease3;
	}
	public String getAutopsy() {
		return autopsy;
	}
	public void setAutopsy(String autopsy) {
		this.autopsy = autopsy;
	}
	public String getOps_treat_checkUp_dia() {
		return ops_treat_checkUp_dia;
	}
	public void setOps_treat_checkUp_dia(String ops_treat_checkUp_dia) {
		this.ops_treat_checkUp_dia = ops_treat_checkUp_dia;
	}
	public String getFollowUpClinic() {
		return followUpClinic;
	}
	public void setFollowUpClinic(String followUpClinic) {
		this.followUpClinic = followUpClinic;
	}
	public String getFuc_timelimit() {
		return fuc_timelimit;
	}
	public void setFuc_timelimit(String fuc_timelimit) {
		this.fuc_timelimit = fuc_timelimit;
	}
	public String getFuc_timelimit_unit() {
		return fuc_timelimit_unit;
	}
	public void setFuc_timelimit_unit(String fuc_timelimit_unit) {
		this.fuc_timelimit_unit = fuc_timelimit_unit;
	}
	public String getTeach() {
		return teach;
	}
	public void setTeach(String teach) {
		this.teach = teach;
	}
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	public String getRh() {
		return rh;
	}
	public void setRh(String rh) {
		this.rh = rh;
	}
	public String getTransfusion() {
		return transfusion;
	}
	public void setTransfusion(String transfusion) {
		this.transfusion = transfusion;
	}
	public String getTf_variety() {
		return tf_variety;
	}
	public void setTf_variety(String tf_variety) {
		this.tf_variety = tf_variety;
	}
	public String getRedBloodCell_unit() {
		return redBloodCell_unit;
	}
	public void setRedBloodCell_unit(String redBloodCell_unit) {
		this.redBloodCell_unit = redBloodCell_unit;
	}
	public String getBloodCells() {
		return bloodCells;
	}
	public void setBloodCells(String bloodCells) {
		this.bloodCells = bloodCells;
	}
	public String getBloodPlasma() {
		return bloodPlasma;
	}
	public void setBloodPlasma(String bloodPlasma) {
		this.bloodPlasma = bloodPlasma;
	}
	public String getWholeBlood() {
		return wholeBlood;
	}
	public void setWholeBlood(String wholeBlood) {
		this.wholeBlood = wholeBlood;
	}
	public String getAuto_blood() {
		return auto_blood;
	}
	public void setAuto_blood(String auto_blood) {
		this.auto_blood = auto_blood;
	}
	public String getOther_blood() {
		return other_blood;
	}
	public void setOther_blood(String other_blood) {
		this.other_blood = other_blood;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getCreditWard() {
		return creditWard;
	}
	public void setCreditWard(String creditWard) {
		this.creditWard = creditWard;
	}
	public String getMenzhen_chuyuan() {
		return menzhen_chuyuan;
	}
	public void setMenzhen_chuyuan(String menzhenChuyuan) {
		menzhen_chuyuan = menzhenChuyuan;
	}
	public String getRuyuan_chuyuan() {
		return ruyuan_chuyuan;
	}
	public void setRuyuan_chuyuan(String ruyuanChuyuan) {
		ruyuan_chuyuan = ruyuanChuyuan;
	}
	public String getShuqian_shuhou() {
		return shuqian_shuhou;
	}
	public void setShuqian_shuhou(String shuqianShuhou) {
		shuqian_shuhou = shuqianShuhou;
	}
	public String getLinchuang_bingli() {
		return linchuang_bingli;
	}
	public void setLinchuang_bingli(String linchuangBingli) {
		linchuang_bingli = linchuangBingli;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getOperationNo5() {
		return operationNo5;
	}
	public void setOperationNo5(String operationNo5) {
		this.operationNo5 = operationNo5;
	}
	public Date getTdate5() {
		return tdate5;
	}
	public void setTdate5(Date tdate5) {
		this.tdate5 = tdate5;
	}
	public String getSurgeryName5() {
		return surgeryName5;
	}
	public void setSurgeryName5(String surgeryName5) {
		this.surgeryName5 = surgeryName5;
	}
	public String getSurgeryDocName5() {
		return surgeryDocName5;
	}
	public void setSurgeryDocName5(String surgeryDocName5) {
		this.surgeryDocName5 = surgeryDocName5;
	}
	public String getSurgeryAssistant51() {
		return surgeryAssistant51;
	}
	public void setSurgeryAssistant51(String surgeryAssistant51) {
		this.surgeryAssistant51 = surgeryAssistant51;
	}
	public String getSurgeryAssistant52() {
		return surgeryAssistant52;
	}
	public void setSurgeryAssistant52(String surgeryAssistant52) {
		this.surgeryAssistant52 = surgeryAssistant52;
	}
	public String getAnesthesiaMode5() {
		return anesthesiaMode5;
	}
	public void setAnesthesiaMode5(String anesthesiaMode5) {
		this.anesthesiaMode5 = anesthesiaMode5;
	}
	public String getCut5() {
		return cut5;
	}
	public void setCut5(String cut5) {
		this.cut5 = cut5;
	}
	public String getAnesthesiologist5() {
		return anesthesiologist5;
	}
	public void setAnesthesiologist5(String anesthesiologist5) {
		this.anesthesiologist5 = anesthesiologist5;
	}
}
