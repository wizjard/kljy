package com.juncsoft.KLJY.InHospitalCase.Liver.entity;

import java.util.ArrayList;
import java.util.List;

public class PastHistory {
	private Long id;
	private Long caseId;
	private String pastHistoryDesc;
	private String bloodType;
	private String rhType;
	private String normalHealth;
	private int allergy_flag;
	private String allergySource;
	private Integer allergySym;
	private String allergyDesc;
	private int sexDis_flag;
	private int infect_flag;
	private String otherFlag;
	private String otherPasttFlag;
	
//	增加高血压 9-17
	 private String blood;// varchar(20);--有无高血压病史
	 private String byear;// varchar(100);--发病年限时间
	 private String bloodYear;// varchar(100);--发病年限单位
	 private String bloodHeight;// varchar(100);--最高血压
	 private String useDrug;// varchar(100);--治疗情况 用药情况单选
	 private String otherText;// ntext;--规律用药，未规律用药，其他所弹出的文本框
	 private String bloodBefor;// varchar(100);--平时血压波动前文本框
	 private String bloodAfter;// varchar(100);--平时血压波动后文本框
	 //增加糖尿病 9-17
	 private String diabetes;// varchar(20);--有无糖尿病史
	 private String dyear;// varchar(100);--病史时间
	 private String diabetesYear;// varchar(100);--病史时间单位
	 private String limosisDiabetes;// varchar(100);--空腹血糖最高达
	 private String eatDiabetes;// varchar(100);--餐后2小时血糖最高达
	 private String duseDrug;// varchar(100);--治疗情况 用药情况单选
	 private String dotherText;// ntext;--规律用药 未规律用药 其他所弹出的文本框
	 private String peacetimeBefor;// varchar(100);--平时血糖波动前文本框
	 private String peacetimeAfter;// varchar(100);--平时血糖波动后文本框
	 private String eatBefor;// varchar(100);--餐后2小时血糖波动前文本框
	 private String eatAfter;// varchar(100);--餐后2小时血糖波动后文本框
	 //增加心脏病 09-17
	 private String heart;// varchar(100);--有无心脏病史
	 private String heartDiagnose;// ntext;--心脏病诊断
	 private String hyear;// varchar(100);--发病年限时间
	 private String heartYear;// varchar(100);--发病年限时间单位
	 private String huseDrug;// varchar(100);--治疗情况 用药情况单选
	 private String hotherText;// ntext;--规律用药 未规律用药 其他所弹出的文本框
	 private String nowThing;// ntext;--当前情况
	private List<PastHistory_Infect> infects;
	private int noInfect_flag;
	private List<PastHistory_NoInfect> noInfects;
	private int outerHurt_flag;
	private List<PastHistory_OuterHurt> outerHurts;
	private int surgery_flag;
	private List<PastHistory_Surgery> surgerys;
	private int noInfect_other;
	public String getOtherFlag() {
		return otherFlag;
	}

	public void setOtherFlag(String otherFlag) {
		this.otherFlag = otherFlag;
	}
	public int getNoInfect_other() {
		return noInfect_other;
	}

	public void setNoInfect_other(int noInfect_other) {
		this.noInfect_other = noInfect_other;
	}
	public String getOtherPasttFlag() {
		return otherPasttFlag;
	}

	public void setOtherPasttFlag(String otherPasttFlag) {
		this.otherPasttFlag = otherPasttFlag;
	}

	public PastHistory() {
		infects = new ArrayList<PastHistory_Infect>();
		noInfects = new ArrayList<PastHistory_NoInfect>();
		outerHurts = new ArrayList<PastHistory_OuterHurt>();
		surgerys = new ArrayList<PastHistory_Surgery>();
	}

	public void addInfect(PastHistory_Infect infect) {
		infect.setPh(this);
		this.infects.add(infect);
	}

	public void addNoInfect(PastHistory_NoInfect noInfect) {
		noInfect.setPh(this);
		this.noInfects.add(noInfect);
	}

	public void addOuterHurt(PastHistory_OuterHurt hurt) {
		hurt.setPh(this);
		this.outerHurts.add(hurt);
	}

	public void addSurgery(PastHistory_Surgery surgery) {
		surgery.setPh(this);
		this.surgerys.add(surgery);
	}

	public List<PastHistory_OuterHurt> getOuterHurts() {
		return outerHurts;
	}

	public void setOuterHurts(List<PastHistory_OuterHurt> outerHurts) {
		this.outerHurts = outerHurts;
	}

	public List<PastHistory_Surgery> getSurgerys() {
		return surgerys;
	}

	public void setSurgerys(List<PastHistory_Surgery> surgerys) {
		this.surgerys = surgerys;
	}

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

	public String getPastHistoryDesc() {
		return pastHistoryDesc;
	}

	public void setPastHistoryDesc(String pastHistoryDesc) {
		this.pastHistoryDesc = pastHistoryDesc;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getRhType() {
		return rhType;
	}

	public void setRhType(String rhType) {
		this.rhType = rhType;
	}

	public String getNormalHealth() {
		return normalHealth;
	}

	public void setNormalHealth(String normalHealth) {
		this.normalHealth = normalHealth;
	}

	public int getAllergy_flag() {
		return allergy_flag;
	}

	public void setAllergy_flag(int allergyFlag) {
		allergy_flag = allergyFlag;
	}

	public String getAllergySource() {
		return allergySource;
	}

	public void setAllergySource(String allergySource) {
		this.allergySource = allergySource;
	}

	public String getAllergyDesc() {
		return allergyDesc;
	}

	public void setAllergyDesc(String allergyDesc) {
		this.allergyDesc = allergyDesc;
	}

	public int getSexDis_flag() {
		return sexDis_flag;
	}

	public void setSexDis_flag(int sexDisFlag) {
		sexDis_flag = sexDisFlag;
	}

	public int getInfect_flag() {
		return infect_flag;
	}

	public void setInfect_flag(int infectFlag) {
		infect_flag = infectFlag;
	}

	public int getNoInfect_flag() {
		return noInfect_flag;
	}

	public void setNoInfect_flag(int noInfectFlag) {
		noInfect_flag = noInfectFlag;
	}

	public int getOuterHurt_flag() {
		return outerHurt_flag;
	}

	public void setOuterHurt_flag(int outerHurtFlag) {
		outerHurt_flag = outerHurtFlag;
	}

	public int getSurgery_flag() {
		return surgery_flag;
	}

	public void setSurgery_flag(int surgeryFlag) {
		surgery_flag = surgeryFlag;
	}

	public List<PastHistory_Infect> getInfects() {
		return infects;
	}

	public void setInfects(List<PastHistory_Infect> infects) {
		this.infects = infects;
	}

	public List<PastHistory_NoInfect> getNoInfects() {
		return noInfects;
	}

	public void setNoInfects(List<PastHistory_NoInfect> noInfects) {
		this.noInfects = noInfects;
	}
	public Integer getAllergySym() {
		return allergySym;
	}
	public void setAllergySym(Integer allergySym) {
		this.allergySym = allergySym;
	}

	public String getBlood() {
		return blood;
	}

	public void setBlood(String blood) {
		this.blood = blood;
	}

	public String getBloodAfter() {
		return bloodAfter;
	}

	public void setBloodAfter(String bloodAfter) {
		this.bloodAfter = bloodAfter;
	}

	public String getBloodBefor() {
		return bloodBefor;
	}

	public void setBloodBefor(String bloodBefor) {
		this.bloodBefor = bloodBefor;
	}

	public String getBloodHeight() {
		return bloodHeight;
	}

	public void setBloodHeight(String bloodHeight) {
		this.bloodHeight = bloodHeight;
	}

	public String getBloodYear() {
		return bloodYear;
	}

	public void setBloodYear(String bloodYear) {
		this.bloodYear = bloodYear;
	}

	public String getByear() {
		return byear;
	}

	public void setByear(String byear) {
		this.byear = byear;
	}

	public String getDiabetes() {
		return diabetes;
	}

	public void setDiabetes(String diabetes) {
		this.diabetes = diabetes;
	}

	public String getDiabetesYear() {
		return diabetesYear;
	}

	public void setDiabetesYear(String diabetesYear) {
		this.diabetesYear = diabetesYear;
	}

	public String getDotherText() {
		return dotherText;
	}

	public void setDotherText(String dotherText) {
		this.dotherText = dotherText;
	}

	public String getDuseDrug() {
		return duseDrug;
	}

	public void setDuseDrug(String duseDrug) {
		this.duseDrug = duseDrug;
	}

	public String getDyear() {
		return dyear;
	}

	public void setDyear(String dyear) {
		this.dyear = dyear;
	}

	public String getEatAfter() {
		return eatAfter;
	}

	public void setEatAfter(String eatAfter) {
		this.eatAfter = eatAfter;
	}

	public String getEatBefor() {
		return eatBefor;
	}

	public void setEatBefor(String eatBefor) {
		this.eatBefor = eatBefor;
	}

	public String getEatDiabetes() {
		return eatDiabetes;
	}

	public void setEatDiabetes(String eatDiabetes) {
		this.eatDiabetes = eatDiabetes;
	}

	public String getHeart() {
		return heart;
	}

	public void setHeart(String heart) {
		this.heart = heart;
	}

	public String getHeartDiagnose() {
		return heartDiagnose;
	}

	public void setHeartDiagnose(String heartDiagnose) {
		this.heartDiagnose = heartDiagnose;
	}

	public String getHeartYear() {
		return heartYear;
	}

	public void setHeartYear(String heartYear) {
		this.heartYear = heartYear;
	}

	public String getHotherText() {
		return hotherText;
	}

	public void setHotherText(String hotherText) {
		this.hotherText = hotherText;
	}

	public String getHuseDrug() {
		return huseDrug;
	}

	public void setHuseDrug(String huseDrug) {
		this.huseDrug = huseDrug;
	}

	public String getHyear() {
		return hyear;
	}

	public void setHyear(String hyear) {
		this.hyear = hyear;
	}

	public String getLimosisDiabetes() {
		return limosisDiabetes;
	}

	public void setLimosisDiabetes(String limosisDiabetes) {
		this.limosisDiabetes = limosisDiabetes;
	}

	public String getNowThing() {
		return nowThing;
	}

	public void setNowThing(String nowThing) {
		this.nowThing = nowThing;
	}

	public String getOtherText() {
		return otherText;
	}

	public void setOtherText(String otherText) {
		this.otherText = otherText;
	}

	public String getPeacetimeAfter() {
		return peacetimeAfter;
	}

	public void setPeacetimeAfter(String peacetimeAfter) {
		this.peacetimeAfter = peacetimeAfter;
	}

	public String getPeacetimeBefor() {
		return peacetimeBefor;
	}

	public void setPeacetimeBefor(String peacetimeBefor) {
		this.peacetimeBefor = peacetimeBefor;
	}

	public String getUseDrug() {
		return useDrug;
	}

	public void setUseDrug(String useDrug) {
		this.useDrug = useDrug;
	}
}
