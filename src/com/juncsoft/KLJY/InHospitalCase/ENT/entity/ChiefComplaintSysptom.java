package com.juncsoft.KLJY.InHospitalCase.ENT.entity;

/**
 * 耳鼻喉 主诉
 * @author Administrator
 */
public class ChiefComplaintSysptom {
	private Long id;
	//private PresentIllnessHistory pi;
	private PresentIllnessHistory_OnSet preOn;
	/*耳*/
	private int er;
	private int earache;//耳痛
	private int erCacesthesia;//耳感觉异常
	private String ecWeiZhi;//耳异常-位置
	private String ecFaZuo;//耳异常-发作
	private String ecXingZhi;//耳异常-性质
	private int otorrhea;//耳溢液
	private String eotWeiZhi;//耳溢液-位置
	private String eotChengDu;
	private String eotFaZuo;
	private String eotYanSe;
	private String eotXingZhi;
	private int deaf;//耳聋
	private String edXingZhi;
	private String edFaZuo;
	private int tinnitus;//耳鸣
	private String etXingZhi;
	private String etChengDu;
	private String etFaZuo;
	private int dizziness;
	private String edizzXingZhi;
	private String edizzFaZuo;
	private String edizzChengDu;
	private int erOther;
	private String erOtherValue;
	private int erConcomitant;
	private String econcomitantSymptom;
	/*鼻*/
	private int nose;
	private int noBiSe;
	private String noXingZhi;
	private String noChengDu;
	private String noFaZuo;
	private int snot;
	private String noWeiZhi;
	private String noQiWei;
	private String nosnFaZuo;
	private String nosnYanSe;
	private String noseChengDu;
	private String noseXingZhi;
	private String noseZhengZhuang;
	private int sneeze;
	private String snXingZhi;
	private String snFaZuo;
	private int epistaxis;
	private String epWeiZhi;
	private String epXingZhi;
	private int headache;
	private String heFaZuo;
	private String heXingZhi;
	private int paraosmia;
	private String parWeiZhi;
	private String parFaZuo;
	private String parXingZhi;
	private int noseCacesthesia;
	private String noCaValue;
	private int snuffle;
	private String snuValue;
	private int noseOther;
	private String noseOtherValue;
	private int noSymptom;
	private String noConcomitant;
	/*咽喉*/
	private int throat;
	private int angina;
	private String anWeiZhi;
	private String anChengDu;
	private String anXingZhi;
	private String anFaZuo;
	private String anYanBian;
	private int thCacesthesia;
	private String thcFaZuo;
	private String tchXingZhi;
	private int dysphagia;
	private String dysXingZhi;
	private int articulation;
	private String artXingZhi;
	private int yanPalateFanLiu;
	private String ypflXingZhi;
	private int trachyphonia;
	private String trachXingZhi;
	private String trachFaZuo;
	private int laryngealstridor;
	private String larXingZhi;
	private String larFaZuo;
	private int dyspnea;
	private String dyspneaXingZhi;
	private int hematemesis;
	private String hemaFaZuo;
	private String hemaYanSe;
	private int cough;
	private String couFanZuo;
	private String couXingZhi;
	private int expectoration;
	private String expXingZhi;
	private int throatOther;
	private String throatOtherValue;
	private int throSymptom;
	private String throConcomitant;
	/*头颈颅底*/
	private int neck;
	private int neCervicalMuscle;
	private int impairedVision;
	private int eyeballFacade;
	private int eyeballDyskinesia;
	private int neckBoss;
	private String neboDescribe;
	private int neckStiff;
	private String nestDescribe;
	private int cervicodynia;
	private String cerXingZhi;
	private int neckLouDao;
	private String neckDescribe;
	private int jujue;
	private String jujueDescribe;
	private int neckOther;
	private String neckOtherValue;
	private int neckSymptom;
	private String neckConcomitant;
	public ChiefComplaintSysptom(){
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/*public PresentIllnessHistory getPi() {
		return pi;
	}
	public void setPi(PresentIllnessHistory pi) {
		this.pi = pi;
	}*/
	public PresentIllnessHistory_OnSet getPreOn() {
		return preOn;
	}
	public void setPreOn(PresentIllnessHistory_OnSet preOn) {
		this.preOn = preOn;
	}
	public int getEr() {
		return er;
	}
	public void setEr(int er) {
		this.er = er;
	}
	public int getEarache() {
		return earache;
	}
	public void setEarache(int earache) {
		this.earache = earache;
	}
	public int getErCacesthesia() {
		return erCacesthesia;
	}
	public void setErCacesthesia(int erCacesthesia) {
		this.erCacesthesia = erCacesthesia;
	}
	public String getEcWeiZhi() {
		return ecWeiZhi;
	}
	public void setEcWeiZhi(String ecWeiZhi) {
		this.ecWeiZhi = ecWeiZhi;
	}
	public String getEcFaZuo() {
		return ecFaZuo;
	}
	public void setEcFaZuo(String ecFaZuo) {
		this.ecFaZuo = ecFaZuo;
	}
	public String getEcXingZhi() {
		return ecXingZhi;
	}
	public void setEcXingZhi(String ecXingZhi) {
		this.ecXingZhi = ecXingZhi;
	}
	public int getOtorrhea() {
		return otorrhea;
	}
	public void setOtorrhea(int otorrhea) {
		this.otorrhea = otorrhea;
	}
	public String getEotWeiZhi() {
		return eotWeiZhi;
	}
	public void setEotWeiZhi(String eotWeiZhi) {
		this.eotWeiZhi = eotWeiZhi;
	}
	public String getEotChengDu() {
		return eotChengDu;
	}
	public void setEotChengDu(String eotChengDu) {
		this.eotChengDu = eotChengDu;
	}
	public String getEotFaZuo() {
		return eotFaZuo;
	}
	public void setEotFaZuo(String eotFaZuo) {
		this.eotFaZuo = eotFaZuo;
	}
	public String getEotYanSe() {
		return eotYanSe;
	}
	public void setEotYanSe(String eotYanSe) {
		this.eotYanSe = eotYanSe;
	}
	public String getEotXingZhi() {
		return eotXingZhi;
	}
	public void setEotXingZhi(String eotXingZhi) {
		this.eotXingZhi = eotXingZhi;
	}
	public int getDeaf() {
		return deaf;
	}
	public void setDeaf(int deaf) {
		this.deaf = deaf;
	}
	public String getEdXingZhi() {
		return edXingZhi;
	}
	public void setEdXingZhi(String edXingZhi) {
		this.edXingZhi = edXingZhi;
	}
	public String getEdFaZuo() {
		return edFaZuo;
	}
	public void setEdFaZuo(String edFaZuo) {
		this.edFaZuo = edFaZuo;
	}
	public int getTinnitus() {
		return tinnitus;
	}
	public void setTinnitus(int tinnitus) {
		this.tinnitus = tinnitus;
	}
	public String getEtXingZhi() {
		return etXingZhi;
	}
	public void setEtXingZhi(String etXingZhi) {
		this.etXingZhi = etXingZhi;
	}
	public String getEtChengDu() {
		return etChengDu;
	}
	public void setEtChengDu(String etChengDu) {
		this.etChengDu = etChengDu;
	}
	public String getEtFaZuo() {
		return etFaZuo;
	}
	public void setEtFaZuo(String etFaZuo) {
		this.etFaZuo = etFaZuo;
	}
	public int getDizziness() {
		return dizziness;
	}
	public void setDizziness(int dizziness) {
		this.dizziness = dizziness;
	}
	public String getEdizzXingZhi() {
		return edizzXingZhi;
	}
	public void setEdizzXingZhi(String edizzXingZhi) {
		this.edizzXingZhi = edizzXingZhi;
	}
	public String getEdizzFaZuo() {
		return edizzFaZuo;
	}
	public void setEdizzFaZuo(String edizzFaZuo) {
		this.edizzFaZuo = edizzFaZuo;
	}
	public String getEdizzChengDu() {
		return edizzChengDu;
	}
	public void setEdizzChengDu(String edizzChengDu) {
		this.edizzChengDu = edizzChengDu;
	}
	public int getErOther() {
		return erOther;
	}
	public void setErOther(int erOther) {
		this.erOther = erOther;
	}
	public String getErOtherValue() {
		return erOtherValue;
	}
	public void setErOtherValue(String erOtherValue) {
		this.erOtherValue = erOtherValue;
	}
	public int getErConcomitant() {
		return erConcomitant;
	}
	public void setErConcomitant(int erConcomitant) {
		this.erConcomitant = erConcomitant;
	}
	
	public String getEconcomitantSymptom() {
		return econcomitantSymptom;
	}
	public void setEconcomitantSymptom(String econcomitantSymptom) {
		this.econcomitantSymptom = econcomitantSymptom;
	}
	public int getNose() {
		return nose;
	}
	public void setNose(int nose) {
		this.nose = nose;
	}
	public int getNoBiSe() {
		return noBiSe;
	}
	public void setNoBiSe(int noBiSe) {
		this.noBiSe = noBiSe;
	}
	public String getNoXingZhi() {
		return noXingZhi;
	}
	public void setNoXingZhi(String noXingZhi) {
		this.noXingZhi = noXingZhi;
	}
	public String getNoChengDu() {
		return noChengDu;
	}
	public void setNoChengDu(String noChengDu) {
		this.noChengDu = noChengDu;
	}
	public String getNoFaZuo() {
		return noFaZuo;
	}
	public void setNoFaZuo(String noFaZuo) {
		this.noFaZuo = noFaZuo;
	}
	public int getSnot() {
		return snot;
	}
	public void setSnot(int snot) {
		this.snot = snot;
	}
	public String getNoWeiZhi() {
		return noWeiZhi;
	}
	public void setNoWeiZhi(String noWeiZhi) {
		this.noWeiZhi = noWeiZhi;
	}
	public String getNoQiWei() {
		return noQiWei;
	}
	public void setNoQiWei(String noQiWei) {
		this.noQiWei = noQiWei;
	}
	public String getNosnFaZuo() {
		return nosnFaZuo;
	}
	public void setNosnFaZuo(String nosnFaZuo) {
		this.nosnFaZuo = nosnFaZuo;
	}
	public String getNosnYanSe() {
		return nosnYanSe;
	}
	public void setNosnYanSe(String nosnYanSe) {
		this.nosnYanSe = nosnYanSe;
	}
	public String getNoseChengDu() {
		return noseChengDu;
	}
	public void setNoseChengDu(String noseChengDu) {
		this.noseChengDu = noseChengDu;
	}
	public String getNoseXingZhi() {
		return noseXingZhi;
	}
	public void setNoseXingZhi(String noseXingZhi) {
		this.noseXingZhi = noseXingZhi;
	}
	public String getNoseZhengZhuang() {
		return noseZhengZhuang;
	}
	public void setNoseZhengZhuang(String noseZhengZhuang) {
		this.noseZhengZhuang = noseZhengZhuang;
	}
	public int getSneeze() {
		return sneeze;
	}
	public void setSneeze(int sneeze) {
		this.sneeze = sneeze;
	}
	public String getSnXingZhi() {
		return snXingZhi;
	}
	public void setSnXingZhi(String snXingZhi) {
		this.snXingZhi = snXingZhi;
	}
	public String getSnFaZuo() {
		return snFaZuo;
	}
	public void setSnFaZuo(String snFaZuo) {
		this.snFaZuo = snFaZuo;
	}
	public int getEpistaxis() {
		return epistaxis;
	}
	public void setEpistaxis(int epistaxis) {
		this.epistaxis = epistaxis;
	}
	public String getEpWeiZhi() {
		return epWeiZhi;
	}
	public void setEpWeiZhi(String epWeiZhi) {
		this.epWeiZhi = epWeiZhi;
	}
	public String getEpXingZhi() {
		return epXingZhi;
	}
	public void setEpXingZhi(String epXingZhi) {
		this.epXingZhi = epXingZhi;
	}
	public int getHeadache() {
		return headache;
	}
	public void setHeadache(int headache) {
		this.headache = headache;
	}
	public String getHeFaZuo() {
		return heFaZuo;
	}
	public void setHeFaZuo(String heFaZuo) {
		this.heFaZuo = heFaZuo;
	}
	public String getHeXingZhi() {
		return heXingZhi;
	}
	public void setHeXingZhi(String heXingZhi) {
		this.heXingZhi = heXingZhi;
	}
	public int getParaosmia() {
		return paraosmia;
	}
	public void setParaosmia(int paraosmia) {
		this.paraosmia = paraosmia;
	}
	public String getParWeiZhi() {
		return parWeiZhi;
	}
	public void setParWeiZhi(String parWeiZhi) {
		this.parWeiZhi = parWeiZhi;
	}
	public String getParFaZuo() {
		return parFaZuo;
	}
	public void setParFaZuo(String parFaZuo) {
		this.parFaZuo = parFaZuo;
	}
	public String getParXingZhi() {
		return parXingZhi;
	}
	public void setParXingZhi(String parXingZhi) {
		this.parXingZhi = parXingZhi;
	}
	public int getNoseCacesthesia() {
		return noseCacesthesia;
	}
	public void setNoseCacesthesia(int noseCacesthesia) {
		this.noseCacesthesia = noseCacesthesia;
	}
	public String getNoCaValue() {
		return noCaValue;
	}
	public void setNoCaValue(String noCaValue) {
		this.noCaValue = noCaValue;
	}
	public int getSnuffle() {
		return snuffle;
	}
	public void setSnuffle(int snuffle) {
		this.snuffle = snuffle;
	}
	public String getSnuValue() {
		return snuValue;
	}
	public void setSnuValue(String snuValue) {
		this.snuValue = snuValue;
	}
	public int getNoseOther() {
		return noseOther;
	}
	public void setNoseOther(int noseOther) {
		this.noseOther = noseOther;
	}
	public String getNoseOtherValue() {
		return noseOtherValue;
	}
	public void setNoseOtherValue(String noseOtherValue) {
		this.noseOtherValue = noseOtherValue;
	}
	public int getNoSymptom() {
		return noSymptom;
	}
	public void setNoSymptom(int noSymptom) {
		this.noSymptom = noSymptom;
	}
	public String getNoConcomitant() {
		return noConcomitant;
	}
	public void setNoConcomitant(String noConcomitant) {
		this.noConcomitant = noConcomitant;
	}
	public int getThroat() {
		return throat;
	}
	public void setThroat(int throat) {
		this.throat = throat;
	}
	public int getAngina() {
		return angina;
	}
	public void setAngina(int angina) {
		this.angina = angina;
	}
	public String getAnWeiZhi() {
		return anWeiZhi;
	}
	public void setAnWeiZhi(String anWeiZhi) {
		this.anWeiZhi = anWeiZhi;
	}
	public String getAnChengDu() {
		return anChengDu;
	}
	public void setAnChengDu(String anChengDu) {
		this.anChengDu = anChengDu;
	}
	public String getAnXingZhi() {
		return anXingZhi;
	}
	public void setAnXingZhi(String anXingZhi) {
		this.anXingZhi = anXingZhi;
	}
	public String getAnFaZuo() {
		return anFaZuo;
	}
	public void setAnFaZuo(String anFaZuo) {
		this.anFaZuo = anFaZuo;
	}
	public String getAnYanBian() {
		return anYanBian;
	}
	public void setAnYanBian(String anYanBian) {
		this.anYanBian = anYanBian;
	}
	public int getThCacesthesia() {
		return thCacesthesia;
	}
	public void setThCacesthesia(int thCacesthesia) {
		this.thCacesthesia = thCacesthesia;
	}
	public String getThcFaZuo() {
		return thcFaZuo;
	}
	public void setThcFaZuo(String thcFaZuo) {
		this.thcFaZuo = thcFaZuo;
	}
	public String getTchXingZhi() {
		return tchXingZhi;
	}
	public void setTchXingZhi(String tchXingZhi) {
		this.tchXingZhi = tchXingZhi;
	}
	public int getDysphagia() {
		return dysphagia;
	}
	public void setDysphagia(int dysphagia) {
		this.dysphagia = dysphagia;
	}
	public String getDysXingZhi() {
		return dysXingZhi;
	}
	public void setDysXingZhi(String dysXingZhi) {
		this.dysXingZhi = dysXingZhi;
	}
	public int getArticulation() {
		return articulation;
	}
	public void setArticulation(int articulation) {
		this.articulation = articulation;
	}
	public String getArtXingZhi() {
		return artXingZhi;
	}
	public void setArtXingZhi(String artXingZhi) {
		this.artXingZhi = artXingZhi;
	}
	public int getYanPalateFanLiu() {
		return yanPalateFanLiu;
	}
	public void setYanPalateFanLiu(int yanPalateFanLiu) {
		this.yanPalateFanLiu = yanPalateFanLiu;
	}
	public String getYpflXingZhi() {
		return ypflXingZhi;
	}
	public void setYpflXingZhi(String ypflXingZhi) {
		this.ypflXingZhi = ypflXingZhi;
	}
	public int getTrachyphonia() {
		return trachyphonia;
	}
	public void setTrachyphonia(int trachyphonia) {
		this.trachyphonia = trachyphonia;
	}
	public String getTrachXingZhi() {
		return trachXingZhi;
	}
	public void setTrachXingZhi(String trachXingZhi) {
		this.trachXingZhi = trachXingZhi;
	}
	public String getTrachFaZuo() {
		return trachFaZuo;
	}
	public void setTrachFaZuo(String trachFaZuo) {
		this.trachFaZuo = trachFaZuo;
	}
	public int getLaryngealstridor() {
		return laryngealstridor;
	}
	public void setLaryngealstridor(int laryngealstridor) {
		this.laryngealstridor = laryngealstridor;
	}
	public String getLarXingZhi() {
		return larXingZhi;
	}
	public void setLarXingZhi(String larXingZhi) {
		this.larXingZhi = larXingZhi;
	}
	public String getLarFaZuo() {
		return larFaZuo;
	}
	public void setLarFaZuo(String larFaZuo) {
		this.larFaZuo = larFaZuo;
	}
	public int getDyspnea() {
		return dyspnea;
	}
	public void setDyspnea(int dyspnea) {
		this.dyspnea = dyspnea;
	}
	public String getDyspneaXingZhi() {
		return dyspneaXingZhi;
	}
	public void setDyspneaXingZhi(String dyspneaXingZhi) {
		this.dyspneaXingZhi = dyspneaXingZhi;
	}
	public int getHematemesis() {
		return hematemesis;
	}
	public void setHematemesis(int hematemesis) {
		this.hematemesis = hematemesis;
	}
	public String getHemaFaZuo() {
		return hemaFaZuo;
	}
	public void setHemaFaZuo(String hemaFaZuo) {
		this.hemaFaZuo = hemaFaZuo;
	}
	public String getHemaYanSe() {
		return hemaYanSe;
	}
	public void setHemaYanSe(String hemaYanSe) {
		this.hemaYanSe = hemaYanSe;
	}
	public int getCough() {
		return cough;
	}
	public void setCough(int cough) {
		this.cough = cough;
	}
	public String getCouFanZuo() {
		return couFanZuo;
	}
	public void setCouFanZuo(String couFanZuo) {
		this.couFanZuo = couFanZuo;
	}
	public String getCouXingZhi() {
		return couXingZhi;
	}
	public void setCouXingZhi(String couXingZhi) {
		this.couXingZhi = couXingZhi;
	}
	public int getExpectoration() {
		return expectoration;
	}
	public void setExpectoration(int expectoration) {
		this.expectoration = expectoration;
	}
	public String getExpXingZhi() {
		return expXingZhi;
	}
	public void setExpXingZhi(String expXingZhi) {
		this.expXingZhi = expXingZhi;
	}
	public int getThroatOther() {
		return throatOther;
	}
	public void setThroatOther(int throatOther) {
		this.throatOther = throatOther;
	}
	public String getThroatOtherValue() {
		return throatOtherValue;
	}
	public void setThroatOtherValue(String throatOtherValue) {
		this.throatOtherValue = throatOtherValue;
	}
	public int getThroSymptom() {
		return throSymptom;
	}
	public void setThroSymptom(int throSymptom) {
		this.throSymptom = throSymptom;
	}
	public String getThroConcomitant() {
		return throConcomitant;
	}
	public void setThroConcomitant(String throConcomitant) {
		this.throConcomitant = throConcomitant;
	}
	public int getNeck() {
		return neck;
	}
	public void setNeck(int neck) {
		this.neck = neck;
	}
	public int getNeCervicalMuscle() {
		return neCervicalMuscle;
	}
	public void setNeCervicalMuscle(int neCervicalMuscle) {
		this.neCervicalMuscle = neCervicalMuscle;
	}
	public int getImpairedVision() {
		return impairedVision;
	}
	public void setImpairedVision(int impairedVision) {
		this.impairedVision = impairedVision;
	}
	public int getEyeballFacade() {
		return eyeballFacade;
	}
	public void setEyeballFacade(int eyeballFacade) {
		this.eyeballFacade = eyeballFacade;
	}
	public int getEyeballDyskinesia() {
		return eyeballDyskinesia;
	}
	public void setEyeballDyskinesia(int eyeballDyskinesia) {
		this.eyeballDyskinesia = eyeballDyskinesia;
	}
	public int getNeckBoss() {
		return neckBoss;
	}
	public void setNeckBoss(int neckBoss) {
		this.neckBoss = neckBoss;
	}
	public String getNeboDescribe() {
		return neboDescribe;
	}
	public void setNeboDescribe(String neboDescribe) {
		this.neboDescribe = neboDescribe;
	}
	public int getNeckStiff() {
		return neckStiff;
	}
	public void setNeckStiff(int neckStiff) {
		this.neckStiff = neckStiff;
	}
	public String getNestDescribe() {
		return nestDescribe;
	}
	public void setNestDescribe(String nestDescribe) {
		this.nestDescribe = nestDescribe;
	}
	public int getCervicodynia() {
		return cervicodynia;
	}
	public void setCervicodynia(int cervicodynia) {
		this.cervicodynia = cervicodynia;
	}
	public String getCerXingZhi() {
		return cerXingZhi;
	}
	public void setCerXingZhi(String cerXingZhi) {
		this.cerXingZhi = cerXingZhi;
	}
	public int getNeckLouDao() {
		return neckLouDao;
	}
	public void setNeckLouDao(int neckLouDao) {
		this.neckLouDao = neckLouDao;
	}
	public String getNeckDescribe() {
		return neckDescribe;
	}
	public void setNeckDescribe(String neckDescribe) {
		this.neckDescribe = neckDescribe;
	}
	public int getJujue() {
		return jujue;
	}
	public void setJujue(int jujue) {
		this.jujue = jujue;
	}
	public String getJujueDescribe() {
		return jujueDescribe;
	}
	public void setJujueDescribe(String jujueDescribe) {
		this.jujueDescribe = jujueDescribe;
	}
	public int getNeckOther() {
		return neckOther;
	}
	public void setNeckOther(int neckOther) {
		this.neckOther = neckOther;
	}
	public String getNeckOtherValue() {
		return neckOtherValue;
	}
	public void setNeckOtherValue(String neckOtherValue) {
		this.neckOtherValue = neckOtherValue;
	}
	public int getNeckSymptom() {
		return neckSymptom;
	}
	public void setNeckSymptom(int neckSymptom) {
		this.neckSymptom = neckSymptom;
	}
	public String getNeckConcomitant() {
		return neckConcomitant;
	}
	public void setNeckConcomitant(String neckConcomitant) {
		this.neckConcomitant = neckConcomitant;
	}
}
