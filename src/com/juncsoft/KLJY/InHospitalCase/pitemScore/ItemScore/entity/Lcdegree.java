package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity;

import java.util.Date;

/**
 * 肝癌进展程度评估实体
 * 
 * @author Administrator
 */
public class Lcdegree {
	private Long lcDegreeId;
	private int patientID;

	private Date createTime;
	private Date LastModifyTime;
	private Date opeDate;
	private String miLAN;
	private String ucsf;
	private int bclc;
	private int llobe;
	private int rlobe;
	private int caud;

	private int seg1;
	private int seg2;
	private int seg3;
	private int seg4;
	private int seg5;
	private int seg6;
	private int seg7;
	private int seg8;

	private float lolt;
	private float wolt;

	private float lost;
	private float wost;
	private int tmorp;

	private int ascit1;
	private int intra;
	private int uright;
	private int uleft;
	private int umain;
	private int hvein;
	private int ivci;
	private int biled;
	private int usgi;
	private int llobe1;
	private int rlobe1;
	private int caud1;
	private int lolt1;
	private int wolt1;
	private int lost1;
	private int wost1;
	private int tmorp1;
	private int lipiod;
	private int ctivc;
	private int cthvein;
	private int ctleft;
	private int ctright;
	private int ctmain;
	private int lympha;
	private int ctlung;
	private int ctpelv;
	private float tlv;
	private float lrv;
	private int cti;
	private int llobe3;
	private int rlobe3;
	private int caud3;
	private float lolt3;
	private float wolt3;
	private float lost3;
	private float wost3;
	private int tmorp3;
	private int mleet;
	private int mright;
	private int mmain;
	private int mhvein;
	private int mriivc;
	private int mlympha;
	private int mrilung;
	private int mripelv;
	private int mri1;
	private String pathno;
	private int notn;
	private float sltw;
	private float sltl;
	private float diasum;
	private int edmon;
	private int enw_edmo;
	private int pst;
	private int venini;
	private int eggel;
	private int encap;
	private int microsat;
	private int capinv;
	private int lnihr;
	private int lnic;
	private int ntl;
	private int l_inv;
	private int imhbcag;
	private int imhbsag;
	private int intrab;
	private int rightb;
	private int mpv;
	private int mhv;
	private int ivc;
	private int diaph;
	private int duode;
	private int stoma;
	private int adrmn;
	private int kidney;
	private int spleen;
	private int colon;
	private String other6;
	private int second;
	private String secondo;
	private int tstage;
	private int okuda;
	private String pathother;
	private int alinj1;
	private int toce3;
	private int rresect;
	private int ltransp;
	private int sysche;
	private int tmx1;
	private int xeloda;
	private int rfat;
	private int other12;
	private String other120;
	private int excis;
	private int sysche1;
	private int eitmx;
	private int other13;
	private String other130;
	private int excis1;
	private int sysch2;
	private int prtmx;
	private int other14;
	private String other140;
	private String tlor;
	private int rt;
	private int excis2;
	private int chemo;
	private int other15;
	private String other150;
	private int rliver;
	private int rthorax;
	private int rbrain;
	private int rbone;
	private String rother;
	private Date recurda;

	public Long getLcDegreeId() {
		return lcDegreeId;
	}

	public void setLcDegreeId(Long lcDegreeId) {
		this.lcDegreeId = lcDegreeId;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return LastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		LastModifyTime = lastModifyTime;
	}

	public Date getOpeDate() {
		return opeDate;
	}

	public void setOpeDate(Date opeDate) {
		this.opeDate = opeDate;
	}

	public String getMiLAN() {
		return miLAN;
	}

	public void setMiLAN(String miLAN) {
		this.miLAN = miLAN;
	}

	public String getUcsf() {
		return ucsf;
	}

	public void setUcsf(String ucsf) {
		this.ucsf = ucsf;
	}

	public int getBclc() {
		return bclc;
	}

	public void setBclc(int bclc) {
		this.bclc = bclc;
	}

	public int getLlobe() {
		return llobe;
	}

	public void setLlobe(int llobe) {
		this.llobe = llobe;
	}

	public int getRlobe() {
		return rlobe;
	}

	public void setRlobe(int rlobe) {
		this.rlobe = rlobe;
	}

	public int getCaud() {
		return caud;
	}

	public void setCaud(int caud) {
		this.caud = caud;
	}

	public int getSeg1() {
		return seg1;
	}

	public void setSeg1(int seg1) {
		this.seg1 = seg1;
	}

	public int getSeg2() {
		return seg2;
	}

	public void setSeg2(int seg2) {
		this.seg2 = seg2;
	}

	public int getSeg3() {
		return seg3;
	}

	public void setSeg3(int seg3) {
		this.seg3 = seg3;
	}

	public int getSeg4() {
		return seg4;
	}

	public void setSeg4(int seg4) {
		this.seg4 = seg4;
	}

	public int getSeg5() {
		return seg5;
	}

	public void setSeg5(int seg5) {
		this.seg5 = seg5;
	}

	public int getSeg6() {
		return seg6;
	}

	public void setSeg6(int seg6) {
		this.seg6 = seg6;
	}

	public int getSeg7() {
		return seg7;
	}

	public void setSeg7(int seg7) {
		this.seg7 = seg7;
	}

	public int getSeg8() {
		return seg8;
	}

	public void setSeg8(int seg8) {
		this.seg8 = seg8;
	}

	public float getLolt() {
		return lolt;
	}

	public void setLolt(float lolt) {
		this.lolt = lolt;
	}

	public float getWolt() {
		return wolt;
	}

	public void setWolt(float wolt) {
		this.wolt = wolt;
	}

	public float getLost() {
		return lost;
	}

	public void setLost(float lost) {
		this.lost = lost;
	}

	public float getWost() {
		return wost;
	}

	public void setWost(float wost) {
		this.wost = wost;
	}

	public int getTmorp() {
		return tmorp;
	}

	public void setTmorp(int tmorp) {
		this.tmorp = tmorp;
	}

	public int getAscit1() {
		return ascit1;
	}

	public void setAscit1(int ascit1) {
		this.ascit1 = ascit1;
	}

	public int getIntra() {
		return intra;
	}

	public void setIntra(int intra) {
		this.intra = intra;
	}

	public int getUright() {
		return uright;
	}

	public void setUright(int uright) {
		this.uright = uright;
	}

	public int getUleft() {
		return uleft;
	}

	public void setUleft(int uleft) {
		this.uleft = uleft;
	}

	public int getUmain() {
		return umain;
	}

	public void setUmain(int umain) {
		this.umain = umain;
	}

	public int getHvein() {
		return hvein;
	}

	public void setHvein(int hvein) {
		this.hvein = hvein;
	}

	public int getIvci() {
		return ivci;
	}

	public void setIvci(int ivci) {
		this.ivci = ivci;
	}

	public int getBiled() {
		return biled;
	}

	public void setBiled(int biled) {
		this.biled = biled;
	}

	public int getUsgi() {
		return usgi;
	}

	public void setUsgi(int usgi) {
		this.usgi = usgi;
	}

	public int getLlobe1() {
		return llobe1;
	}

	public void setLlobe1(int llobe1) {
		this.llobe1 = llobe1;
	}

	public int getRlobe1() {
		return rlobe1;
	}

	public void setRlobe1(int rlobe1) {
		this.rlobe1 = rlobe1;
	}

	public int getCaud1() {
		return caud1;
	}

	public void setCaud1(int caud1) {
		this.caud1 = caud1;
	}

	public int getLolt1() {
		return lolt1;
	}

	public void setLolt1(int lolt1) {
		this.lolt1 = lolt1;
	}

	public int getWolt1() {
		return wolt1;
	}

	public void setWolt1(int wolt1) {
		this.wolt1 = wolt1;
	}

	public int getLost1() {
		return lost1;
	}

	public void setLost1(int lost1) {
		this.lost1 = lost1;
	}

	public int getWost1() {
		return wost1;
	}

	public void setWost1(int wost1) {
		this.wost1 = wost1;
	}

	public int getTmorp1() {
		return tmorp1;
	}

	public void setTmorp1(int tmorp1) {
		this.tmorp1 = tmorp1;
	}

	public int getLipiod() {
		return lipiod;
	}

	public void setLipiod(int lipiod) {
		this.lipiod = lipiod;
	}

	public int getCtivc() {
		return ctivc;
	}

	public void setCtivc(int ctivc) {
		this.ctivc = ctivc;
	}

	public int getCthvein() {
		return cthvein;
	}

	public void setCthvein(int cthvein) {
		this.cthvein = cthvein;
	}

	public int getCtleft() {
		return ctleft;
	}

	public void setCtleft(int ctleft) {
		this.ctleft = ctleft;
	}

	public int getCtright() {
		return ctright;
	}

	public void setCtright(int ctright) {
		this.ctright = ctright;
	}

	public int getCtmain() {
		return ctmain;
	}

	public void setCtmain(int ctmain) {
		this.ctmain = ctmain;
	}

	public int getLympha() {
		return lympha;
	}

	public void setLympha(int lympha) {
		this.lympha = lympha;
	}

	public int getCtlung() {
		return ctlung;
	}

	public void setCtlung(int ctlung) {
		this.ctlung = ctlung;
	}

	public int getCtpelv() {
		return ctpelv;
	}

	public void setCtpelv(int ctpelv) {
		this.ctpelv = ctpelv;
	}

	public float getTlv() {
		return tlv;
	}

	public void setTlv(float tlv) {
		this.tlv = tlv;
	}

	public float getLrv() {
		return lrv;
	}

	public void setLrv(float lrv) {
		this.lrv = lrv;
	}

	public int getCti() {
		return cti;
	}

	public void setCti(int cti) {
		this.cti = cti;
	}

	public int getLlobe3() {
		return llobe3;
	}

	public void setLlobe3(int llobe3) {
		this.llobe3 = llobe3;
	}

	public int getRlobe3() {
		return rlobe3;
	}

	public void setRlobe3(int rlobe3) {
		this.rlobe3 = rlobe3;
	}

	public int getCaud3() {
		return caud3;
	}

	public void setCaud3(int caud3) {
		this.caud3 = caud3;
	}

	public float getLolt3() {
		return lolt3;
	}

	public void setLolt3(float lolt3) {
		this.lolt3 = lolt3;
	}

	public float getWolt3() {
		return wolt3;
	}

	public void setWolt3(float wolt3) {
		this.wolt3 = wolt3;
	}

	public float getLost3() {
		return lost3;
	}

	public void setLost3(float lost3) {
		this.lost3 = lost3;
	}

	public float getWost3() {
		return wost3;
	}

	public void setWost3(float wost3) {
		this.wost3 = wost3;
	}

	public int getTmorp3() {
		return tmorp3;
	}

	public void setTmorp3(int tmorp3) {
		this.tmorp3 = tmorp3;
	}

	public int getMleet() {
		return mleet;
	}

	public void setMleet(int mleet) {
		this.mleet = mleet;
	}

	public int getMright() {
		return mright;
	}

	public void setMright(int mright) {
		this.mright = mright;
	}

	public int getMmain() {
		return mmain;
	}

	public void setMmain(int mmain) {
		this.mmain = mmain;
	}

	public int getMhvein() {
		return mhvein;
	}

	public void setMhvein(int mhvein) {
		this.mhvein = mhvein;
	}

	public int getMriivc() {
		return mriivc;
	}

	public void setMriivc(int mriivc) {
		this.mriivc = mriivc;
	}

	public int getMlympha() {
		return mlympha;
	}

	public void setMlympha(int mlympha) {
		this.mlympha = mlympha;
	}

	public int getMrilung() {
		return mrilung;
	}

	public void setMrilung(int mrilung) {
		this.mrilung = mrilung;
	}

	public int getMripelv() {
		return mripelv;
	}

	public void setMripelv(int mripelv) {
		this.mripelv = mripelv;
	}

	public int getMri1() {
		return mri1;
	}

	public void setMri1(int mri1) {
		this.mri1 = mri1;
	}

	public String getPathno() {
		return pathno;
	}

	public void setPathno(String pathno) {
		this.pathno = pathno;
	}

	public int getNotn() {
		return notn;
	}

	public void setNotn(int notn) {
		this.notn = notn;
	}

	public float getSltw() {
		return sltw;
	}

	public void setSltw(float sltw) {
		this.sltw = sltw;
	}

	public float getSltl() {
		return sltl;
	}

	public void setSltl(float sltl) {
		this.sltl = sltl;
	}

	public float getDiasum() {
		return diasum;
	}

	public void setDiasum(float diasum) {
		this.diasum = diasum;
	}

	public int getEdmon() {
		return edmon;
	}

	public void setEdmon(int edmon) {
		this.edmon = edmon;
	}

	public int getEnw_edmo() {
		return enw_edmo;
	}

	public void setEnw_edmo(int enw_edmo) {
		this.enw_edmo = enw_edmo;
	}

	public int getPst() {
		return pst;
	}

	public void setPst(int pst) {
		this.pst = pst;
	}

	public int getVenini() {
		return venini;
	}

	public void setVenini(int venini) {
		this.venini = venini;
	}

	public int getEggel() {
		return eggel;
	}

	public void setEggel(int eggel) {
		this.eggel = eggel;
	}

	public int getEncap() {
		return encap;
	}

	public void setEncap(int encap) {
		this.encap = encap;
	}

	public int getMicrosat() {
		return microsat;
	}

	public void setMicrosat(int microsat) {
		this.microsat = microsat;
	}

	public int getCapinv() {
		return capinv;
	}

	public void setCapinv(int capinv) {
		this.capinv = capinv;
	}

	public int getLnihr() {
		return lnihr;
	}

	public void setLnihr(int lnihr) {
		this.lnihr = lnihr;
	}

	public int getLnic() {
		return lnic;
	}

	public void setLnic(int lnic) {
		this.lnic = lnic;
	}

	public int getNtl() {
		return ntl;
	}

	public void setNtl(int ntl) {
		this.ntl = ntl;
	}

	public int getL_inv() {
		return l_inv;
	}

	public void setL_inv(int l_inv) {
		this.l_inv = l_inv;
	}

	public int getImhbcag() {
		return imhbcag;
	}

	public void setImhbcag(int imhbcag) {
		this.imhbcag = imhbcag;
	}

	public int getImhbsag() {
		return imhbsag;
	}

	public void setImhbsag(int imhbsag) {
		this.imhbsag = imhbsag;
	}

	public int getIntrab() {
		return intrab;
	}

	public void setIntrab(int intrab) {
		this.intrab = intrab;
	}

	public int getRightb() {
		return rightb;
	}

	public void setRightb(int rightb) {
		this.rightb = rightb;
	}

	public int getMpv() {
		return mpv;
	}

	public void setMpv(int mpv) {
		this.mpv = mpv;
	}

	public int getMhv() {
		return mhv;
	}

	public void setMhv(int mhv) {
		this.mhv = mhv;
	}

	public int getIvc() {
		return ivc;
	}

	public void setIvc(int ivc) {
		this.ivc = ivc;
	}

	public int getDiaph() {
		return diaph;
	}

	public void setDiaph(int diaph) {
		this.diaph = diaph;
	}

	public int getDuode() {
		return duode;
	}

	public void setDuode(int duode) {
		this.duode = duode;
	}

	public int getStoma() {
		return stoma;
	}

	public void setStoma(int stoma) {
		this.stoma = stoma;
	}

	public int getAdrmn() {
		return adrmn;
	}

	public void setAdrmn(int adrmn) {
		this.adrmn = adrmn;
	}

	public int getKidney() {
		return kidney;
	}

	public void setKidney(int kidney) {
		this.kidney = kidney;
	}

	public int getSpleen() {
		return spleen;
	}

	public void setSpleen(int spleen) {
		this.spleen = spleen;
	}

	public int getColon() {
		return colon;
	}

	public void setColon(int colon) {
		this.colon = colon;
	}

	public String getOther6() {
		return other6;
	}

	public void setOther6(String other6) {
		this.other6 = other6;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public String getSecondo() {
		return secondo;
	}

	public void setSecondo(String secondo) {
		this.secondo = secondo;
	}

	public int getTstage() {
		return tstage;
	}

	public void setTstage(int tstage) {
		this.tstage = tstage;
	}

	public int getOkuda() {
		return okuda;
	}

	public void setOkuda(int okuda) {
		this.okuda = okuda;
	}

	public String getPathother() {
		return pathother;
	}

	public void setPathother(String pathother) {
		this.pathother = pathother;
	}

	public int getAlinj1() {
		return alinj1;
	}

	public void setAlinj1(int alinj1) {
		this.alinj1 = alinj1;
	}

	public int getToce3() {
		return toce3;
	}

	public void setToce3(int toce3) {
		this.toce3 = toce3;
	}

	public int getRresect() {
		return rresect;
	}

	public void setRresect(int rresect) {
		this.rresect = rresect;
	}

	public int getLtransp() {
		return ltransp;
	}

	public void setLtransp(int ltransp) {
		this.ltransp = ltransp;
	}

	public int getSysche() {
		return sysche;
	}

	public void setSysche(int sysche) {
		this.sysche = sysche;
	}

	public int getTmx1() {
		return tmx1;
	}

	public void setTmx1(int tmx1) {
		this.tmx1 = tmx1;
	}

	public int getXeloda() {
		return xeloda;
	}

	public void setXeloda(int xeloda) {
		this.xeloda = xeloda;
	}

	public int getRfat() {
		return rfat;
	}

	public void setRfat(int rfat) {
		this.rfat = rfat;
	}

	public int getOther12() {
		return other12;
	}

	public void setOther12(int other12) {
		this.other12 = other12;
	}

	public String getOther120() {
		return other120;
	}

	public void setOther120(String other120) {
		this.other120 = other120;
	}

	public int getExcis() {
		return excis;
	}

	public void setExcis(int excis) {
		this.excis = excis;
	}

	public int getSysche1() {
		return sysche1;
	}

	public void setSysche1(int sysche1) {
		this.sysche1 = sysche1;
	}

	public int getEitmx() {
		return eitmx;
	}

	public void setEitmx(int eitmx) {
		this.eitmx = eitmx;
	}

	public int getOther13() {
		return other13;
	}

	public void setOther13(int other13) {
		this.other13 = other13;
	}

	public String getOther130() {
		return other130;
	}

	public void setOther130(String other130) {
		this.other130 = other130;
	}

	public int getExcis1() {
		return excis1;
	}

	public void setExcis1(int excis1) {
		this.excis1 = excis1;
	}

	public int getSysch2() {
		return sysch2;
	}

	public void setSysch2(int sysch2) {
		this.sysch2 = sysch2;
	}

	public int getPrtmx() {
		return prtmx;
	}

	public void setPrtmx(int prtmx) {
		this.prtmx = prtmx;
	}

	public int getOther14() {
		return other14;
	}

	public void setOther14(int other14) {
		this.other14 = other14;
	}

	public String getOther140() {
		return other140;
	}

	public void setOther140(String other140) {
		this.other140 = other140;
	}

	public String getTlor() {
		return tlor;
	}

	public void setTlor(String tlor) {
		this.tlor = tlor;
	}

	public int getRt() {
		return rt;
	}

	public void setRt(int rt) {
		this.rt = rt;
	}

	public int getExcis2() {
		return excis2;
	}

	public void setExcis2(int excis2) {
		this.excis2 = excis2;
	}

	public int getChemo() {
		return chemo;
	}

	public void setChemo(int chemo) {
		this.chemo = chemo;
	}

	public int getOther15() {
		return other15;
	}

	public void setOther15(int other15) {
		this.other15 = other15;
	}

	public String getOther150() {
		return other150;
	}

	public void setOther150(String other150) {
		this.other150 = other150;
	}

	public int getRliver() {
		return rliver;
	}

	public void setRliver(int rliver) {
		this.rliver = rliver;
	}

	public int getRthorax() {
		return rthorax;
	}

	public void setRthorax(int rthorax) {
		this.rthorax = rthorax;
	}

	public int getRbrain() {
		return rbrain;
	}

	public void setRbrain(int rbrain) {
		this.rbrain = rbrain;
	}

	public int getRbone() {
		return rbone;
	}

	public void setRbone(int rbone) {
		this.rbone = rbone;
	}

	public String getRother() {
		return rother;
	}

	public void setRother(String rother) {
		this.rother = rother;
	}

	public Date getRecurda() {
		return recurda;
	}

	public void setRecurda(Date recurda) {
		this.recurda = recurda;
	}
}
