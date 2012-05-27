package com.juncsoft.KLJY.system.Research.entity;

import java.util.Date;

public class ResearchTopic {
	private Long id;
	private String name;
	private String serialNum;
	private String responser;
	private String secretLv;
	private String topicType;
	private String belongUnit;
	private String belongWard;
	
	private Date pj_createDate;
	private Date pj_completeDate;
	private String pj_class1;
	private String pj_class2;
	private String pj_status;
	private String pj_localCost;
	private String pj_icpNum;
	private String pj_comInvest;
	private String pj_totalCost;
	private String pj_bankInvest;
	private String pj_centerCost;
	private String pj_otherCost;
	private String pj_desc;
	
	private String tj_subjectClass;
	private String tj_researchType;
	private String tj_inteClass1;
	private String tj_inteClass2;
	private String tj_projectFrom;
	private String tj_cooperType;
	private String tj_societyTarget;
	private String tj_projectPersonNum;
	private String tj_desc;
	public Date getPj_createDate() {
		return pj_createDate;
	}
	public void setPj_createDate(Date pjCreateDate) {
		pj_createDate = pjCreateDate;
	}
	public Date getPj_completeDate() {
		return pj_completeDate;
	}
	public void setPj_completeDate(Date pjCompleteDate) {
		pj_completeDate = pjCompleteDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getResponser() {
		return responser;
	}
	public void setResponser(String responser) {
		this.responser = responser;
	}
	public String getSecretLv() {
		return secretLv;
	}
	public void setSecretLv(String secretLv) {
		this.secretLv = secretLv;
	}
	public String getTopicType() {
		return topicType;
	}
	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}
	public String getBelongUnit() {
		return belongUnit;
	}
	public void setBelongUnit(String belongUnit) {
		this.belongUnit = belongUnit;
	}
	public String getBelongWard() {
		return belongWard;
	}
	public void setBelongWard(String belongWard) {
		this.belongWard = belongWard;
	}
	public String getPj_class1() {
		return pj_class1;
	}
	public void setPj_class1(String pjClass1) {
		pj_class1 = pjClass1;
	}
	public String getPj_class2() {
		return pj_class2;
	}
	public void setPj_class2(String pjClass2) {
		pj_class2 = pjClass2;
	}
	public String getPj_status() {
		return pj_status;
	}
	public void setPj_status(String pjStatus) {
		pj_status = pjStatus;
	}
	public String getPj_localCost() {
		return pj_localCost;
	}
	public void setPj_localCost(String pjLocalCost) {
		pj_localCost = pjLocalCost;
	}
	public String getPj_icpNum() {
		return pj_icpNum;
	}
	public void setPj_icpNum(String pjIcpNum) {
		pj_icpNum = pjIcpNum;
	}
	public String getPj_comInvest() {
		return pj_comInvest;
	}
	public void setPj_comInvest(String pjComInvest) {
		pj_comInvest = pjComInvest;
	}
	public String getPj_totalCost() {
		return pj_totalCost;
	}
	public void setPj_totalCost(String pjTotalCost) {
		pj_totalCost = pjTotalCost;
	}
	public String getPj_bankInvest() {
		return pj_bankInvest;
	}
	public void setPj_bankInvest(String pjBankInvest) {
		pj_bankInvest = pjBankInvest;
	}
	public String getPj_centerCost() {
		return pj_centerCost;
	}
	public void setPj_centerCost(String pjCenterCost) {
		pj_centerCost = pjCenterCost;
	}
	public String getPj_otherCost() {
		return pj_otherCost;
	}
	public void setPj_otherCost(String pjOtherCost) {
		pj_otherCost = pjOtherCost;
	}
	public String getPj_desc() {
		return pj_desc;
	}
	public void setPj_desc(String pjDesc) {
		pj_desc = pjDesc;
	}
	public String getTj_subjectClass() {
		return tj_subjectClass;
	}
	public void setTj_subjectClass(String tjSubjectClass) {
		tj_subjectClass = tjSubjectClass;
	}
	public String getTj_researchType() {
		return tj_researchType;
	}
	public void setTj_researchType(String tjResearchType) {
		tj_researchType = tjResearchType;
	}
	public String getTj_inteClass1() {
		return tj_inteClass1;
	}
	public void setTj_inteClass1(String tjInteClass1) {
		tj_inteClass1 = tjInteClass1;
	}
	public String getTj_inteClass2() {
		return tj_inteClass2;
	}
	public void setTj_inteClass2(String tjInteClass2) {
		tj_inteClass2 = tjInteClass2;
	}
	public String getTj_projectFrom() {
		return tj_projectFrom;
	}
	public void setTj_projectFrom(String tjProjectFrom) {
		tj_projectFrom = tjProjectFrom;
	}
	public String getTj_cooperType() {
		return tj_cooperType;
	}
	public void setTj_cooperType(String tjCooperType) {
		tj_cooperType = tjCooperType;
	}
	public String getTj_societyTarget() {
		return tj_societyTarget;
	}
	public void setTj_societyTarget(String tjSocietyTarget) {
		tj_societyTarget = tjSocietyTarget;
	}
	public String getTj_projectPersonNum() {
		return tj_projectPersonNum;
	}
	public void setTj_projectPersonNum(String tjProjectPersonNum) {
		tj_projectPersonNum = tjProjectPersonNum;
	}
	public String getTj_desc() {
		return tj_desc;
	}
	public void setTj_desc(String tjDesc) {
		tj_desc = tjDesc;
	}
}
