package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

import java.util.Date;

/**
 * 肝病功能评估实体
 * 
 * @author Administrator
 * 
 */
public class LFunction {
	private Long lfId;
	private int patientID;
	private int caseID;
	private Date opeDate;
	private Date CreateTime;
	private Date LastModifyTime;

	private String CPC1;
	private int CPCS1;
	private int CPC2;
	private int CPCS2;
	private int CPC3;
	private int CPCS3;
	private int CPC4;
	private int CPCS4;
	private int CPC5;
	private int CPCS5;
	private String CPSEx;
	private int CPS;

	private float MELDC1;
	private float MELDC2;
	private float MELDC3;
	private int MELDC4;
	private int MELDS;

	private String shen1;
	private String shen2;
	private String shen3;
	private String shen4;
	private String shen5;
	private String shen6;
	private String shen7;
	private String shen8;
	private String shen9;
	private String shen10;
	private String shen11;
	private String shen12;
	private String shen13;
	private String shen14;
	private String shen15;
	private int shen16;
	private int shen17;
	private String shen18;

	private String xin1;
	private String xin2;
	private String xin3;
	private String xin4;
	private String xin5;
	private int xin6;
	private String xin7;
	private String xin8;

	private String fei1;
	private String fei2;
	private String fei3;
	private String fei4;
	private int fei5;
	private String fei6;

	private String shenjing1;
	private String shenjing2;
	private String shenjing3;
	private String shenjing4;
	private String shenjing5;
	private String shenjing6;
	private String shenjing7;
	private String shenjing8;
	private int shenjing9;
	private int shenjing10;
	private int shenjing11;
	private String shenjing12;

	private String ningxue1;
	private String ningxue2;
	private String ningxue3;
	private String ningxue4;
	private String ningxue5;
	private String ningxue6;
	private String ningxue7;
	private String ningxue8;
	private String ningxue9;
	private String ningxue10;
	private String ningxue11;
	private String ningxue12;

	private String other;

	public Long getLfId() {
		return lfId;
	}

	public void setLfId(Long lfId) {
		this.lfId = lfId;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public int getCaseID() {
		return caseID;
	}

	public void setCaseID(int caseID) {
		this.caseID = caseID;
	}

	public Date getOpeDate() {
		return opeDate;
	}

	public void setOpeDate(Date opeDate) {
		this.opeDate = opeDate;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

	public Date getLastModifyTime() {
		return LastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		LastModifyTime = lastModifyTime;
	}

	public String getCPC1() {
		return CPC1;
	}

	public void setCPC1(String cpc1) {
		CPC1 = cpc1;
	}

	public int getCPCS1() {
		return CPCS1;
	}

	public void setCPCS1(int cpcs1) {
		CPCS1 = cpcs1;
	}

	public int getCPC2() {
		return CPC2;
	}

	public void setCPC2(int cpc2) {
		CPC2 = cpc2;
	}

	public int getCPCS2() {
		return CPCS2;
	}

	public void setCPCS2(int cpcs2) {
		CPCS2 = cpcs2;
	}

	public int getCPC3() {
		return CPC3;
	}

	public void setCPC3(int cpc3) {
		CPC3 = cpc3;
	}

	public int getCPCS3() {
		return CPCS3;
	}

	public void setCPCS3(int cpcs3) {
		CPCS3 = cpcs3;
	}

	public int getCPC4() {
		return CPC4;
	}

	public void setCPC4(int cpc4) {
		CPC4 = cpc4;
	}

	public int getCPCS4() {
		return CPCS4;
	}

	public void setCPCS4(int cpcs4) {
		CPCS4 = cpcs4;
	}

	public int getCPC5() {
		return CPC5;
	}

	public void setCPC5(int cpc5) {
		CPC5 = cpc5;
	}

	public int getCPCS5() {
		return CPCS5;
	}

	public void setCPCS5(int cpcs5) {
		CPCS5 = cpcs5;
	}

	public String getCPSEx() {
		return CPSEx;
	}

	public void setCPSEx(String ex) {
		CPSEx = ex;
	}

	public int getCPS() {
		return CPS;
	}

	public void setCPS(int cps) {
		CPS = cps;
	}

	public float getMELDC1() {
		return MELDC1;
	}

	public void setMELDC1(float meldc1) {
		MELDC1 = meldc1;
	}

	public float getMELDC2() {
		return MELDC2;
	}

	public void setMELDC2(float meldc2) {
		MELDC2 = meldc2;
	}

	public float getMELDC3() {
		return MELDC3;
	}

	public void setMELDC3(float meldc3) {
		MELDC3 = meldc3;
	}

	public int getMELDC4() {
		return MELDC4;
	}

	public void setMELDC4(int meldc4) {
		MELDC4 = meldc4;
	}

	public int getMELDS() {
		return MELDS;
	}

	public void setMELDS(int melds) {
		MELDS = melds;
	}

	public String getShen1() {
		return shen1;
	}

	public void setShen1(String shen1) {
		this.shen1 = shen1;
	}

	public String getShen2() {
		return shen2;
	}

	public void setShen2(String shen2) {
		this.shen2 = shen2;
	}

	public String getShen3() {
		return shen3;
	}

	public void setShen3(String shen3) {
		this.shen3 = shen3;
	}

	public String getShen4() {
		return shen4;
	}

	public void setShen4(String shen4) {
		this.shen4 = shen4;
	}

	public String getShen5() {
		return shen5;
	}

	public void setShen5(String shen5) {
		this.shen5 = shen5;
	}

	public String getShen6() {
		return shen6;
	}

	public void setShen6(String shen6) {
		this.shen6 = shen6;
	}

	public String getShen7() {
		return shen7;
	}

	public void setShen7(String shen7) {
		this.shen7 = shen7;
	}

	public String getShen8() {
		return shen8;
	}

	public void setShen8(String shen8) {
		this.shen8 = shen8;
	}

	public String getShen9() {
		return shen9;
	}

	public void setShen9(String shen9) {
		this.shen9 = shen9;
	}

	public String getShen10() {
		return shen10;
	}

	public void setShen10(String shen10) {
		this.shen10 = shen10;
	}

	public String getShen11() {
		return shen11;
	}

	public void setShen11(String shen11) {
		this.shen11 = shen11;
	}

	public String getShen12() {
		return shen12;
	}

	public void setShen12(String shen12) {
		this.shen12 = shen12;
	}

	public String getShen13() {
		return shen13;
	}

	public void setShen13(String shen13) {
		this.shen13 = shen13;
	}

	public String getShen14() {
		return shen14;
	}

	public void setShen14(String shen14) {
		this.shen14 = shen14;
	}

	public String getShen15() {
		return shen15;
	}

	public void setShen15(String shen15) {
		this.shen15 = shen15;
	}

	public int getShen16() {
		return shen16;
	}

	public void setShen16(int shen16) {
		this.shen16 = shen16;
	}

	public int getShen17() {
		return shen17;
	}

	public void setShen17(int shen17) {
		this.shen17 = shen17;
	}

	public String getShen18() {
		return shen18;
	}

	public void setShen18(String shen18) {
		this.shen18 = shen18;
	}

	public String getXin1() {
		return xin1;
	}

	public void setXin1(String xin1) {
		this.xin1 = xin1;
	}

	public String getXin2() {
		return xin2;
	}

	public void setXin2(String xin2) {
		this.xin2 = xin2;
	}

	public String getXin3() {
		return xin3;
	}

	public void setXin3(String xin3) {
		this.xin3 = xin3;
	}

	public String getXin4() {
		return xin4;
	}

	public void setXin4(String xin4) {
		this.xin4 = xin4;
	}

	public String getXin5() {
		return xin5;
	}

	public void setXin5(String xin5) {
		this.xin5 = xin5;
	}

	public int getXin6() {
		return xin6;
	}

	public void setXin6(int xin6) {
		this.xin6 = xin6;
	}

	public String getXin7() {
		return xin7;
	}

	public void setXin7(String xin7) {
		this.xin7 = xin7;
	}

	public String getXin8() {
		return xin8;
	}

	public void setXin8(String xin8) {
		this.xin8 = xin8;
	}

	public String getFei1() {
		return fei1;
	}

	public void setFei1(String fei1) {
		this.fei1 = fei1;
	}

	public String getFei2() {
		return fei2;
	}

	public void setFei2(String fei2) {
		this.fei2 = fei2;
	}

	public String getFei3() {
		return fei3;
	}

	public void setFei3(String fei3) {
		this.fei3 = fei3;
	}

	public String getFei4() {
		return fei4;
	}

	public void setFei4(String fei4) {
		this.fei4 = fei4;
	}

	public int getFei5() {
		return fei5;
	}

	public void setFei5(int fei5) {
		this.fei5 = fei5;
	}

	public String getFei6() {
		return fei6;
	}

	public void setFei6(String fei6) {
		this.fei6 = fei6;
	}

	public String getShenjing1() {
		return shenjing1;
	}

	public void setShenjing1(String shenjing1) {
		this.shenjing1 = shenjing1;
	}

	public String getShenjing2() {
		return shenjing2;
	}

	public void setShenjing2(String shenjing2) {
		this.shenjing2 = shenjing2;
	}

	public String getShenjing3() {
		return shenjing3;
	}

	public void setShenjing3(String shenjing3) {
		this.shenjing3 = shenjing3;
	}

	public String getShenjing4() {
		return shenjing4;
	}

	public void setShenjing4(String shenjing4) {
		this.shenjing4 = shenjing4;
	}

	public String getShenjing5() {
		return shenjing5;
	}

	public void setShenjing5(String shenjing5) {
		this.shenjing5 = shenjing5;
	}

	public String getShenjing6() {
		return shenjing6;
	}

	public void setShenjing6(String shenjing6) {
		this.shenjing6 = shenjing6;
	}

	public String getShenjing7() {
		return shenjing7;
	}

	public void setShenjing7(String shenjing7) {
		this.shenjing7 = shenjing7;
	}

	public String getShenjing8() {
		return shenjing8;
	}

	public void setShenjing8(String shenjing8) {
		this.shenjing8 = shenjing8;
	}

	public int getShenjing9() {
		return shenjing9;
	}

	public void setShenjing9(int shenjing9) {
		this.shenjing9 = shenjing9;
	}

	public int getShenjing10() {
		return shenjing10;
	}

	public void setShenjing10(int shenjing10) {
		this.shenjing10 = shenjing10;
	}

	public int getShenjing11() {
		return shenjing11;
	}

	public void setShenjing11(int shenjing11) {
		this.shenjing11 = shenjing11;
	}

	public String getShenjing12() {
		return shenjing12;
	}

	public void setShenjing12(String shenjing12) {
		this.shenjing12 = shenjing12;
	}

	public String getNingxue1() {
		return ningxue1;
	}

	public void setNingxue1(String ningxue1) {
		this.ningxue1 = ningxue1;
	}

	public String getNingxue2() {
		return ningxue2;
	}

	public void setNingxue2(String ningxue2) {
		this.ningxue2 = ningxue2;
	}

	public String getNingxue3() {
		return ningxue3;
	}

	public void setNingxue3(String ningxue3) {
		this.ningxue3 = ningxue3;
	}

	public String getNingxue4() {
		return ningxue4;
	}

	public void setNingxue4(String ningxue4) {
		this.ningxue4 = ningxue4;
	}

	public String getNingxue5() {
		return ningxue5;
	}

	public void setNingxue5(String ningxue5) {
		this.ningxue5 = ningxue5;
	}

	public String getNingxue6() {
		return ningxue6;
	}

	public void setNingxue6(String ningxue6) {
		this.ningxue6 = ningxue6;
	}

	public String getNingxue7() {
		return ningxue7;
	}

	public void setNingxue7(String ningxue7) {
		this.ningxue7 = ningxue7;
	}

	public String getNingxue8() {
		return ningxue8;
	}

	public void setNingxue8(String ningxue8) {
		this.ningxue8 = ningxue8;
	}

	public String getNingxue9() {
		return ningxue9;
	}

	public void setNingxue9(String ningxue9) {
		this.ningxue9 = ningxue9;
	}

	public String getNingxue10() {
		return ningxue10;
	}

	public void setNingxue10(String ningxue10) {
		this.ningxue10 = ningxue10;
	}

	public String getNingxue11() {
		return ningxue11;
	}

	public void setNingxue11(String ningxue11) {
		this.ningxue11 = ningxue11;
	}

	public String getNingxue12() {
		return ningxue12;
	}

	public void setNingxue12(String ningxue12) {
		this.ningxue12 = ningxue12;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
}
