package com.juncsoft.KLJY.InHospitalCase.CourseRecording.entity;

import java.util.Date;
//24小时内入院死亡记录
public class InHspDied24{
	private Long id;
	private Long caseId;//入院次数
	private Date time;//记录日期
	private String age;//死者年龄
	private Date inhspTime;//入院时间
	private Date outhspTime;//出院时间
	private String narrator;//叙述者
	private String narrator0;//
	private String reliability;//可靠性
	private String chiefComplaint;//主诉
	private String inHspStatu;//入院时情况
	private String inHspDiag;//入院诊断
	private String treatProcess;//诊疗经过
	private String diedReason;//死亡原因

	private Long submiter;//
	private Long checker;//
	private int verifyStatus;//
	private String mainDoctor;//主治医生
	private String inhspDoctor;//入院医生
	
	private String diedOrder;//死亡诊断
	private String diedOrder2;
	private String diedOrder3;
	private String diedOrder4;
	private String diedOrder5;
	private String diedOrder6;
	private String diedOrder7;
	private String diedOrder8;
	private String diedOrder9;
	private String diedOrder10;
	private String diedOrder11;
	private String diedOrder12;
	private String diedOrder13;
	private String diedOrder14;
	private String diedOrder15;
	private String diedOrder16;
	private String diedOrder17;
	private String diedOrder18;
	private String diedOrder19;
	private String diedOrder20;
	private String diedOrder21;
	private String diedOrder22;
	private String diedOrder23;
	private String diedOrder24;
	private String diedOrder25;
	private String diedOrder26;
	private String diedOrder27;
	private String diedOrder28;
	private String diedOrder29;
	private String diedOrder30;
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
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
	public Date getInhspTime() {
		return inhspTime;
	}
	public void setInhspTime(Date inhspTime) {
		this.inhspTime = inhspTime;
	}
	public Date getOuthspTime() {
		return outhspTime;
	}
	public void setOuthspTime(Date outhspTime) {
		this.outhspTime = outhspTime;
	}
	public String getNarrator() {
		return narrator;
	}
	public void setNarrator(String narrator) {
		this.narrator = narrator;
	}
	public String getNarrator0() {
		return narrator0;
	}
	public void setNarrator0(String narrator0) {
		this.narrator0 = narrator0;
	}
	public String getReliability() {
		return reliability;
	}
	public void setReliability(String reliability) {
		this.reliability = reliability;
	}
	public String getChiefComplaint() {
		return chiefComplaint;
	}
	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}
	public String getInHspStatu() {
		return inHspStatu;
	}
	public void setInHspStatu(String inHspStatu) {
		this.inHspStatu = inHspStatu;
	}
	public String getInHspDiag() {
		return inHspDiag;
	}
	public void setInHspDiag(String inHspDiag) {
		this.inHspDiag = inHspDiag;
	}
	public String getTreatProcess() {
		return treatProcess;
	}
	public void setTreatProcess(String treatProcess) {
		this.treatProcess = treatProcess;
	}
	
	public String getDiedReason() {
		return diedReason;
	}
	public void setDiedReason(String diedReason) {
		this.diedReason = diedReason;
	}
	public String getDiedOrder() {
		return diedOrder;
	}
	public void setDiedOrder(String diedOrder) {
		this.diedOrder = diedOrder;
	}

	public Long getSubmiter() {
		return submiter;
	}
	public void setSubmiter(Long submiter) {
		this.submiter = submiter;
	}
	public Long getChecker() {
		return checker;
	}
	public void setChecker(Long checker) {
		this.checker = checker;
	}
	public int getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public String getMainDoctor() {
		return mainDoctor;
	}
	public void setMainDoctor(String mainDoctor) {
		this.mainDoctor = mainDoctor;
	}
	public String getInhspDoctor() {
		return inhspDoctor;
	}
	public void setInhspDoctor(String inhspDoctor) {
		this.inhspDoctor = inhspDoctor;
	}
	public String getDiedOrder2() {
		return diedOrder2;
	}
	public void setDiedOrder2(String diedOrder2) {
		this.diedOrder2 = diedOrder2;
	}
	public String getDiedOrder3() {
		return diedOrder3;
	}
	public void setDiedOrder3(String diedOrder3) {
		this.diedOrder3 = diedOrder3;
	}
	public String getDiedOrder4() {
		return diedOrder4;
	}
	public void setDiedOrder4(String diedOrder4) {
		this.diedOrder4 = diedOrder4;
	}
	public String getDiedOrder5() {
		return diedOrder5;
	}
	public void setDiedOrder5(String diedOrder5) {
		this.diedOrder5 = diedOrder5;
	}
	public String getDiedOrder6() {
		return diedOrder6;
	}
	public void setDiedOrder6(String diedOrder6) {
		this.diedOrder6 = diedOrder6;
	}
	public String getDiedOrder7() {
		return diedOrder7;
	}
	public void setDiedOrder7(String diedOrder7) {
		this.diedOrder7 = diedOrder7;
	}
	public String getDiedOrder8() {
		return diedOrder8;
	}
	public void setDiedOrder8(String diedOrder8) {
		this.diedOrder8 = diedOrder8;
	}
	public String getDiedOrder9() {
		return diedOrder9;
	}
	public void setDiedOrder9(String diedOrder9) {
		this.diedOrder9 = diedOrder9;
	}
	public String getDiedOrder10() {
		return diedOrder10;
	}
	public void setDiedOrder10(String diedOrder10) {
		this.diedOrder10 = diedOrder10;
	}
	public String getDiedOrder11() {
		return diedOrder11;
	}
	public void setDiedOrder11(String diedOrder11) {
		this.diedOrder11 = diedOrder11;
	}
	public String getDiedOrder12() {
		return diedOrder12;
	}
	public void setDiedOrder12(String diedOrder12) {
		this.diedOrder12 = diedOrder12;
	}
	public String getDiedOrder13() {
		return diedOrder13;
	}
	public void setDiedOrder13(String diedOrder13) {
		this.diedOrder13 = diedOrder13;
	}
	public String getDiedOrder14() {
		return diedOrder14;
	}
	public void setDiedOrder14(String diedOrder14) {
		this.diedOrder14 = diedOrder14;
	}
	public String getDiedOrder15() {
		return diedOrder15;
	}
	public void setDiedOrder15(String diedOrder15) {
		this.diedOrder15 = diedOrder15;
	}
	public String getDiedOrder16() {
		return diedOrder16;
	}
	public void setDiedOrder16(String diedOrder16) {
		this.diedOrder16 = diedOrder16;
	}
	public String getDiedOrder17() {
		return diedOrder17;
	}
	public void setDiedOrder17(String diedOrder17) {
		this.diedOrder17 = diedOrder17;
	}
	public String getDiedOrder18() {
		return diedOrder18;
	}
	public void setDiedOrder18(String diedOrder18) {
		this.diedOrder18 = diedOrder18;
	}
	public String getDiedOrder19() {
		return diedOrder19;
	}
	public void setDiedOrder19(String diedOrder19) {
		this.diedOrder19 = diedOrder19;
	}
	public String getDiedOrder20() {
		return diedOrder20;
	}
	public void setDiedOrder20(String diedOrder20) {
		this.diedOrder20 = diedOrder20;
	}
	public String getDiedOrder21() {
		return diedOrder21;
	}
	public void setDiedOrder21(String diedOrder21) {
		this.diedOrder21 = diedOrder21;
	}
	public String getDiedOrder22() {
		return diedOrder22;
	}
	public void setDiedOrder22(String diedOrder22) {
		this.diedOrder22 = diedOrder22;
	}
	public String getDiedOrder23() {
		return diedOrder23;
	}
	public void setDiedOrder23(String diedOrder23) {
		this.diedOrder23 = diedOrder23;
	}
	public String getDiedOrder24() {
		return diedOrder24;
	}
	public void setDiedOrder24(String diedOrder24) {
		this.diedOrder24 = diedOrder24;
	}
	public String getDiedOrder25() {
		return diedOrder25;
	}
	public void setDiedOrder25(String diedOrder25) {
		this.diedOrder25 = diedOrder25;
	}
	public String getDiedOrder26() {
		return diedOrder26;
	}
	public void setDiedOrder26(String diedOrder26) {
		this.diedOrder26 = diedOrder26;
	}
	public String getDiedOrder27() {
		return diedOrder27;
	}
	public void setDiedOrder27(String diedOrder27) {
		this.diedOrder27 = diedOrder27;
	}
	public String getDiedOrder28() {
		return diedOrder28;
	}
	public void setDiedOrder28(String diedOrder28) {
		this.diedOrder28 = diedOrder28;
	}
	public String getDiedOrder29() {
		return diedOrder29;
	}
	public void setDiedOrder29(String diedOrder29) {
		this.diedOrder29 = diedOrder29;
	}
	public String getDiedOrder30() {
		return diedOrder30;
	}
	public void setDiedOrder30(String diedOrder30) {
		this.diedOrder30 = diedOrder30;
	}
	
}