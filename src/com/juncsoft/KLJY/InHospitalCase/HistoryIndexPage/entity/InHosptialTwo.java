package com.juncsoft.KLJY.InHospitalCase.HistoryIndexPage.entity;

import java.util.Date;

public class InHosptialTwo {
	private Long id;
	private Long caseId;
	
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
	public String getHunmi() {
		return hunmi;
	}
	public void setHunmi(String hunmi) {
		this.hunmi = hunmi;
	}
	private String hunmi;
	private String hunmi_ruyuanqian_hour;
	private String hunmi_ruyuanqian_minute;
	private String hunmi_ruyuanhou_hour;
	private String hunmi_ruyuanhou_minute;
	private String liyuanType;
	private String shiftTo;
	private String obstetrics;
	private String obs_checkUp;
	private String obs_checkUp_times;
	
	
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	private String deliveryTime;
	
	
	
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
	public void setHunmi_ruyuanqian_hour(String hunmiRuyuanqianHour) {
		hunmi_ruyuanqian_hour = hunmiRuyuanqianHour;
	}
	public String getHunmi_ruyuanqian_minute() {
		return hunmi_ruyuanqian_minute;
	}
	public void setHunmi_ruyuanqian_minute(String hunmiRuyuanqianMinute) {
		hunmi_ruyuanqian_minute = hunmiRuyuanqianMinute;
	}
	public String getHunmi_ruyuanhou_hour() {
		return hunmi_ruyuanhou_hour;
	}
	public void setHunmi_ruyuanhou_hour(String hunmiRuyuanhouHour) {
		hunmi_ruyuanhou_hour = hunmiRuyuanhouHour;
	}
	public String getHunmi_ruyuanhou_minute() {
		return hunmi_ruyuanhou_minute;
	}
	public void setHunmi_ruyuanhou_minute(String hunmiRuyuanhouMinute) {
		hunmi_ruyuanhou_minute = hunmiRuyuanhouMinute;
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
	public void setObs_checkUp(String obsCheckUp) {
		obs_checkUp = obsCheckUp;
	}
	public String getObs_checkUp_times() {
		return obs_checkUp_times;
	}
	public void setObs_checkUp_times(String obsCheckUpTimes) {
		obs_checkUp_times = obsCheckUpTimes;
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
	public void setObs_totalTime_hour(String obsTotalTimeHour) {
		obs_totalTime_hour = obsTotalTimeHour;
	}
	public String getObs_totalTime_minute() {
		return obs_totalTime_minute;
	}
	public void setObs_totalTime_minute(String obsTotalTimeMinute) {
		obs_totalTime_minute = obsTotalTimeMinute;
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
	public void setObs_amountOfBleeding(String obsAmountOfBleeding) {
		obs_amountOfBleeding = obsAmountOfBleeding;
	}
	public String getBirth_position() {
		return birth_position;
	}
	public void setBirth_position(String birthPosition) {
		birth_position = birthPosition;
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
	public void setNeonatus_sex(String neonatusSex) {
		neonatus_sex = neonatusSex;
	}
	public String getNeonatus_ruyuan_weight() {
		return neonatus_ruyuan_weight;
	}
	public void setNeonatus_ruyuan_weight(String neonatusRuyuanWeight) {
		neonatus_ruyuan_weight = neonatusRuyuanWeight;
	}
	public String getNeonatus_chuyuan_weight() {
		return neonatus_chuyuan_weight;
	}
	public void setNeonatus_chuyuan_weight(String neonatusChuyuanWeight) {
		neonatus_chuyuan_weight = neonatusChuyuanWeight;
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
	public void setNeonatus_disease1(String neonatusDisease1) {
		neonatus_disease1 = neonatusDisease1;
	}
	public String getNeonatus_disease2() {
		return neonatus_disease2;
	}
	public void setNeonatus_disease2(String neonatusDisease2) {
		neonatus_disease2 = neonatusDisease2;
	}
	public String getNeonatus_disease3() {
		return neonatus_disease3;
	}
	public void setNeonatus_disease3(String neonatusDisease3) {
		neonatus_disease3 = neonatusDisease3;
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
	public void setOps_treat_checkUp_dia(String opsTreatCheckUpDia) {
		ops_treat_checkUp_dia = opsTreatCheckUpDia;
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
	public void setFuc_timelimit(String fucTimelimit) {
		fuc_timelimit = fucTimelimit;
	}
	public String getFuc_timelimit_unit() {
		return fuc_timelimit_unit;
	}
	public void setFuc_timelimit_unit(String fucTimelimitUnit) {
		fuc_timelimit_unit = fucTimelimitUnit;
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
	public void setTf_variety(String tfVariety) {
		tf_variety = tfVariety;
	}
	public String getRedBloodCell_unit() {
		return redBloodCell_unit;
	}
	public void setRedBloodCell_unit(String redBloodCellUnit) {
		redBloodCell_unit = redBloodCellUnit;
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
	public void setAuto_blood(String autoBlood) {
		auto_blood = autoBlood;
	}
	public String getOther_blood() {
		return other_blood;
	}
	public void setOther_blood(String otherBlood) {
		other_blood = otherBlood;
	}
}
