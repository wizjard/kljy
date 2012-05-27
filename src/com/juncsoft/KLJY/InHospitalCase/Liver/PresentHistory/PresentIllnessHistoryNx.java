package com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory;

import java.util.ArrayList;
import java.util.List;

public class PresentIllnessHistoryNx {
	private Long id;
	private PresentIllnessHistoryN n;
	//小组合
	private String casesDesc;
	private String mainSysptomDesc;
	private String sideSysptomDesc;
	private String treatmentDesc;
	//时间诱因
	private String time;
	private String timeUnit;
	private String timeUnitSuffix;
	private String causesFlag;
	
	private String experunit;
	private String questionDesc;
	
	private String causes;
	private String otherTimeCausesFlag;
	private String otherTimeCauses;
	//主要症状
	private String fali;
	private String fali_performance;
	private String yanyou;
	private String shishao;
	private String shishao_performance;
	private String shishao_reduce;
	private String fansuan;
	private String fansuan_time;
	private String aiqi;
	private String exin;
	private String outu;
	private String outu_shape;
	private String outu_thing;
	private String outu_countDay;
	private String outu_volumeDay;
	private String outu_countTotal;
	private String outu_volumeTotal;
	private String ouxue;
	private String ouxue_shape;
	private String ouxue_countDay;
	private String ouxue_volumeDay;
	private String ouxue_countTotal;
	private String ouxue_volumeTotal;
	private String ouxue_side;
	private String fuzhang;
	private String futong;
	private String futong_cases;
	private String futong_position;
	private String futong_performance;
	private String futong_degree;
	private String futong_rate;
	private String futong_position2;
	private String futong_time;
	private String futong_fangshe;
	private String futong_fangshe0;
	private String futong_change;
	private String fuxie;
	private String fuxie_liji;
	private String fuxie_shape;
	private String fuxie_countDay;
	private String fuxie_volumeDay;
	private String shuizhong;
	private String shuizhong_buwei;
	private String shuizhong_chengdu;
	private String shuizhong_xingzhi;
	private String fare;
	private String fare_type;
	private String fare_tiwen;
	private String fare_topt;
	private String fare_performance;
	private String fare_side;
	private String huangran;
	private String huangran_performance;
	private String huangran_degree;
	private String huangran_side;
	private String huangran_noside;
	private String niaoye;
	private String niaoye_color;
	private String niaoye_qldegree;
	private String niaoye_volume;
	private String niaoye_volumeTotal;
	private String dabian;
	private String dabian_huibai;
	private String dabian_xue;
	private String dabian_color;
	private String dabian_shape;
	private String dabian_countDay;
	private String dabian_volumeDay;
	private String dabian_countTotal;
	private String dabian_volumeTotal;
	private String dabian_side;
	private String shenzhi;
	private String shenzhi_performance;
	private String shenzhi_answer;
	private String xingwei;
	private String xingwei_performance;
	private String otherPosiSysptom;
	private String negaSysptom;
	private String negativeOrder;
	private String positiveOrder;
	//伴随症状
	private String side_xiaohua;
	private String side_xiaohua0;
	private String side_xunhuan;
	private String side_xunhuan0;
	private String side_huxi;
	private String side_huxi0;
	private String side_miniao;
	private String side_miniao0;
	private String side_xueye;
	private String side_xueye0;
	private String side_nei;
	private String side_nei0;
	private String side_shenjing;
	private String side_shenjing0;
	private String side_jirou;
	private String side_jirou0;
	private String side_qita;
	private String side_qita0;
	//诊疗经过
	private String inspectionUnit;
	
	private String treatProcessFlag;
	private String treatProcessDesc;
	private String treatTime;
	private String treatTimeUnit;
	private String treatTimeUnitSuffix;
	private String treatFn;
	private String treatFn0;
	private String checkRstFlag;
	private String checkRst;
	private String diagRstFlag;
	private String diagRst;
	private String diagRst0;
	private String treatFlag;
	private String treat;
	private String treatRst;
	private List<TreatGrid> grids=new ArrayList<TreatGrid>();
	//重要阴性症状
	private String pQsbs;
	private String pPfsy;
	private String pPfsy_posi;
	private String pPfsy_degree;
	private String pPqqk;
	private String pFbbk;
	private String pFbbk_posi;
	private String pFbbk_size;
	private String pFbbk_yd;
	private String pFbbk_zhd;
	private String pFbbk_yt;
	private String pFbbk_zhl;
	private String pGqbs;
	private String pPqbs;
	private String pTykn;
	private String pTykn_degree;
	private String pChx;
	private String pXggb;
	private String pXggb_desc;
	private String pXwyc;
	private String pXwyc_desc;
	private String pHj;
	private String pHj_desc;
	private String pJsl;
	private String pDxl;
	
	private String otherPosit;
	private String side_fengshi;
	private String side_fengshi0;
	
	private String pchx_desc;
	public String getPchx_desc() {
		return pchx_desc;
	}
	public void setPchx_desc(String pchx_desc) {
		this.pchx_desc = pchx_desc;
	}
	
	
	public String getpQsbs() {
		return pQsbs;
	}
	public void setpQsbs(String pQsbs) {
		this.pQsbs = pQsbs;
	}
	public String getpPfsy() {
		return pPfsy;
	}
	public void setpPfsy(String pPfsy) {
		this.pPfsy = pPfsy;
	}
	public String getpPfsy_posi() {
		return pPfsy_posi;
	}
	public void setpPfsy_posi(String pPfsyPosi) {
		pPfsy_posi = pPfsyPosi;
	}
	public String getpPfsy_degree() {
		return pPfsy_degree;
	}
	public void setpPfsy_degree(String pPfsyDegree) {
		pPfsy_degree = pPfsyDegree;
	}
	public String getpPqqk() {
		return pPqqk;
	}
	public void setpPqqk(String pPqqk) {
		this.pPqqk = pPqqk;
	}
	public String getpFbbk() {
		return pFbbk;
	}
	public void setpFbbk(String pFbbk) {
		this.pFbbk = pFbbk;
	}
	public String getpFbbk_posi() {
		return pFbbk_posi;
	}
	public void setpFbbk_posi(String pFbbkPosi) {
		pFbbk_posi = pFbbkPosi;
	}
	public String getpFbbk_size() {
		return pFbbk_size;
	}
	public void setpFbbk_size(String pFbbkSize) {
		pFbbk_size = pFbbkSize;
	}
	public String getpFbbk_yd() {
		return pFbbk_yd;
	}
	public void setpFbbk_yd(String pFbbkYd) {
		pFbbk_yd = pFbbkYd;
	}
	public String getpFbbk_zhd() {
		return pFbbk_zhd;
	}
	public void setpFbbk_zhd(String pFbbkZhd) {
		pFbbk_zhd = pFbbkZhd;
	}
	public String getpFbbk_yt() {
		return pFbbk_yt;
	}
	public void setpFbbk_yt(String pFbbkYt) {
		pFbbk_yt = pFbbkYt;
	}
	public String getpFbbk_zhl() {
		return pFbbk_zhl;
	}
	public void setpFbbk_zhl(String pFbbkZhl) {
		pFbbk_zhl = pFbbkZhl;
	}
	public String getpGqbs() {
		return pGqbs;
	}
	public void setpGqbs(String pGqbs) {
		this.pGqbs = pGqbs;
	}
	public String getpPqbs() {
		return pPqbs;
	}
	public void setpPqbs(String pPqbs) {
		this.pPqbs = pPqbs;
	}
	public String getpTykn() {
		return pTykn;
	}
	public void setpTykn(String pTykn) {
		this.pTykn = pTykn;
	}
	public String getpTykn_degree() {
		return pTykn_degree;
	}
	public void setpTykn_degree(String pTyknDegree) {
		pTykn_degree = pTyknDegree;
	}
	public String getpChx() {
		return pChx;
	}
	public void setpChx(String pChx) {
		this.pChx = pChx;
	}
	public String getpXggb() {
		return pXggb;
	}
	public void setpXggb(String pXggb) {
		this.pXggb = pXggb;
	}
	public String getpXggb_desc() {
		return pXggb_desc;
	}
	public void setpXggb_desc(String pXggbDesc) {
		pXggb_desc = pXggbDesc;
	}
	public String getpXwyc() {
		return pXwyc;
	}
	public void setpXwyc(String pXwyc) {
		this.pXwyc = pXwyc;
	}
	public String getpXwyc_desc() {
		return pXwyc_desc;
	}
	public void setpXwyc_desc(String pXwycDesc) {
		pXwyc_desc = pXwycDesc;
	}
	public String getpHj() {
		return pHj;
	}
	public void setpHj(String pHj) {
		this.pHj = pHj;
	}
	public String getpHj_desc() {
		return pHj_desc;
	}
	public void setpHj_desc(String pHjDesc) {
		pHj_desc = pHjDesc;
	}
	public String getpJsl() {
		return pJsl;
	}
	public void setpJsl(String pJsl) {
		this.pJsl = pJsl;
	}
	public String getpDxl() {
		return pDxl;
	}
	public void setpDxl(String pDxl) {
		this.pDxl = pDxl;
	}
	public String getOtherPosiSysptom() {
		return otherPosiSysptom;
	}
	public void setOtherPosiSysptom(String otherPosiSysptom) {
		this.otherPosiSysptom = otherPosiSysptom;
	}
	public String getNegaSysptom() {
		return negaSysptom;
	}
	public void setNegaSysptom(String negaSysptom) {
		this.negaSysptom = negaSysptom;
	}
	public List<TreatGrid> getGrids() {
		return grids;
	}
	public void setGrids(List<TreatGrid> grids) {
		this.grids = grids;
	}
	public String getTreatFlag() {
		return treatFlag;
	}
	public void setTreatFlag(String treatFlag) {
		this.treatFlag = treatFlag;
	}
	public String getTreat() {
		return treat;
	}
	public void setTreat(String treat) {
		this.treat = treat;
	}
	public String getTreatRst() {
		return treatRst;
	}
	public void setTreatRst(String treatRst) {
		this.treatRst = treatRst;
	}
	public String getCheckRstFlag() {
		return checkRstFlag;
	}
	public void setCheckRstFlag(String checkRstFlag) {
		this.checkRstFlag = checkRstFlag;
	}
	public String getCheckRst() {
		return checkRst;
	}
	public void setCheckRst(String checkRst) {
		this.checkRst = checkRst;
	}
	public String getDiagRstFlag() {
		return diagRstFlag;
	}
	public void setDiagRstFlag(String diagRstFlag) {
		this.diagRstFlag = diagRstFlag;
	}
	public String getDiagRst() {
		return diagRst;
	}
	public void setDiagRst(String diagRst) {
		this.diagRst = diagRst;
	}
	public String getDiagRst0() {
		return diagRst0;
	}
	public void setDiagRst0(String diagRst0) {
		this.diagRst0 = diagRst0;
	}
	public String getTreatProcessFlag() {
		return treatProcessFlag;
	}
	public void setTreatProcessFlag(String treatProcessFlag) {
		this.treatProcessFlag = treatProcessFlag;
	}
	public String getTreatProcessDesc() {
		return treatProcessDesc;
	}
	public void setTreatProcessDesc(String treatProcessDesc) {
		this.treatProcessDesc = treatProcessDesc;
	}
	public String getTreatTime() {
		return treatTime;
	}
	public void setTreatTime(String treatTime) {
		this.treatTime = treatTime;
	}
	public String getTreatTimeUnit() {
		return treatTimeUnit;
	}
	public void setTreatTimeUnit(String treatTimeUnit) {
		this.treatTimeUnit = treatTimeUnit;
	}
	public String getTreatTimeUnitSuffix() {
		return treatTimeUnitSuffix;
	}
	public void setTreatTimeUnitSuffix(String treatTimeUnitSuffix) {
		this.treatTimeUnitSuffix = treatTimeUnitSuffix;
	}
	public String getTreatFn() {
		return treatFn;
	}
	public void setTreatFn(String treatFn) {
		this.treatFn = treatFn;
	}
	public String getTreatFn0() {
		return treatFn0;
	}
	public void setTreatFn0(String treatFn0) {
		this.treatFn0 = treatFn0;
	}
	public String getSide_xiaohua() {
		return side_xiaohua;
	}
	public void setSide_xiaohua(String sideXiaohua) {
		side_xiaohua = sideXiaohua;
	}
	public String getSide_xiaohua0() {
		return side_xiaohua0;
	}
	public void setSide_xiaohua0(String sideXiaohua0) {
		side_xiaohua0 = sideXiaohua0;
	}
	public String getSide_xunhuan() {
		return side_xunhuan;
	}
	public void setSide_xunhuan(String sideXunhuan) {
		side_xunhuan = sideXunhuan;
	}
	public String getSide_xunhuan0() {
		return side_xunhuan0;
	}
	public void setSide_xunhuan0(String sideXunhuan0) {
		side_xunhuan0 = sideXunhuan0;
	}
	public String getSide_huxi() {
		return side_huxi;
	}
	public void setSide_huxi(String sideHuxi) {
		side_huxi = sideHuxi;
	}
	public String getSide_huxi0() {
		return side_huxi0;
	}
	public void setSide_huxi0(String sideHuxi0) {
		side_huxi0 = sideHuxi0;
	}
	public String getSide_miniao() {
		return side_miniao;
	}
	public void setSide_miniao(String sideMiniao) {
		side_miniao = sideMiniao;
	}
	public String getSide_miniao0() {
		return side_miniao0;
	}
	public void setSide_miniao0(String sideMiniao0) {
		side_miniao0 = sideMiniao0;
	}
	public String getSide_xueye() {
		return side_xueye;
	}
	public void setSide_xueye(String sideXueye) {
		side_xueye = sideXueye;
	}
	public String getSide_xueye0() {
		return side_xueye0;
	}
	public void setSide_xueye0(String sideXueye0) {
		side_xueye0 = sideXueye0;
	}
	public String getSide_nei() {
		return side_nei;
	}
	public void setSide_nei(String sideNei) {
		side_nei = sideNei;
	}
	public String getSide_nei0() {
		return side_nei0;
	}
	public void setSide_nei0(String sideNei0) {
		side_nei0 = sideNei0;
	}
	public String getSide_shenjing() {
		return side_shenjing;
	}
	public void setSide_shenjing(String sideShenjing) {
		side_shenjing = sideShenjing;
	}
	public String getSide_shenjing0() {
		return side_shenjing0;
	}
	public void setSide_shenjing0(String sideShenjing0) {
		side_shenjing0 = sideShenjing0;
	}
	public String getSide_jirou() {
		return side_jirou;
	}
	public void setSide_jirou(String sideJirou) {
		side_jirou = sideJirou;
	}
	public String getSide_jirou0() {
		return side_jirou0;
	}
	public void setSide_jirou0(String sideJirou0) {
		side_jirou0 = sideJirou0;
	}
	public String getSide_qita() {
		return side_qita;
	}
	public void setSide_qita(String sideQita) {
		side_qita = sideQita;
	}
	public String getSide_qita0() {
		return side_qita0;
	}
	public void setSide_qita0(String sideQita0) {
		side_qita0 = sideQita0;
	}
	public String getNegativeOrder() {
		return negativeOrder;
	}
	public void setNegativeOrder(String negativeOrder) {
		this.negativeOrder = negativeOrder;
	}
	public String getPositiveOrder() {
		return positiveOrder;
	}
	public void setPositiveOrder(String positiveOrder) {
		this.positiveOrder = positiveOrder;
	}
	public String getFali() {
		return fali;
	}
	public void setFali(String fali) {
		this.fali = fali;
	}
	public String getFali_performance() {
		return fali_performance;
	}
	public void setFali_performance(String faliPerformance) {
		fali_performance = faliPerformance;
	}
	public String getYanyou() {
		return yanyou;
	}
	public void setYanyou(String yanyou) {
		this.yanyou = yanyou;
	}
	public String getShishao() {
		return shishao;
	}
	public void setShishao(String shishao) {
		this.shishao = shishao;
	}
	public String getShishao_performance() {
		return shishao_performance;
	}
	public void setShishao_performance(String shishaoPerformance) {
		shishao_performance = shishaoPerformance;
	}
	public String getShishao_reduce() {
		return shishao_reduce;
	}
	public void setShishao_reduce(String shishaoReduce) {
		shishao_reduce = shishaoReduce;
	}
	public String getFansuan() {
		return fansuan;
	}
	public void setFansuan(String fansuan) {
		this.fansuan = fansuan;
	}
	public String getFansuan_time() {
		return fansuan_time;
	}
	public void setFansuan_time(String fansuanTime) {
		fansuan_time = fansuanTime;
	}
	public String getAiqi() {
		return aiqi;
	}
	public void setAiqi(String aiqi) {
		this.aiqi = aiqi;
	}
	public String getExin() {
		return exin;
	}
	public void setExin(String exin) {
		this.exin = exin;
	}
	public String getOutu() {
		return outu;
	}
	public void setOutu(String outu) {
		this.outu = outu;
	}
	public String getOutu_shape() {
		return outu_shape;
	}
	public void setOutu_shape(String outuShape) {
		outu_shape = outuShape;
	}
	public String getOutu_thing() {
		return outu_thing;
	}
	public void setOutu_thing(String outuThing) {
		outu_thing = outuThing;
	}
	public String getOutu_countDay() {
		return outu_countDay;
	}
	public void setOutu_countDay(String outuCountDay) {
		outu_countDay = outuCountDay;
	}
	public String getOutu_volumeDay() {
		return outu_volumeDay;
	}
	public void setOutu_volumeDay(String outuVolumeDay) {
		outu_volumeDay = outuVolumeDay;
	}
	public String getOutu_countTotal() {
		return outu_countTotal;
	}
	public void setOutu_countTotal(String outuCountTotal) {
		outu_countTotal = outuCountTotal;
	}
	public String getOutu_volumeTotal() {
		return outu_volumeTotal;
	}
	public void setOutu_volumeTotal(String outuVolumeTotal) {
		outu_volumeTotal = outuVolumeTotal;
	}
	public String getOuxue() {
		return ouxue;
	}
	public void setOuxue(String ouxue) {
		this.ouxue = ouxue;
	}
	public String getOuxue_shape() {
		return ouxue_shape;
	}
	public void setOuxue_shape(String ouxueShape) {
		ouxue_shape = ouxueShape;
	}
	public String getOuxue_countDay() {
		return ouxue_countDay;
	}
	public void setOuxue_countDay(String ouxueCountDay) {
		ouxue_countDay = ouxueCountDay;
	}
	public String getOuxue_volumeDay() {
		return ouxue_volumeDay;
	}
	public void setOuxue_volumeDay(String ouxueVolumeDay) {
		ouxue_volumeDay = ouxueVolumeDay;
	}
	public String getOuxue_countTotal() {
		return ouxue_countTotal;
	}
	public void setOuxue_countTotal(String ouxueCountTotal) {
		ouxue_countTotal = ouxueCountTotal;
	}
	public String getOuxue_volumeTotal() {
		return ouxue_volumeTotal;
	}
	public void setOuxue_volumeTotal(String ouxueVolumeTotal) {
		ouxue_volumeTotal = ouxueVolumeTotal;
	}
	public String getOuxue_side() {
		return ouxue_side;
	}
	public void setOuxue_side(String ouxueSide) {
		ouxue_side = ouxueSide;
	}
	public String getFuzhang() {
		return fuzhang;
	}
	public void setFuzhang(String fuzhang) {
		this.fuzhang = fuzhang;
	}
	public String getFutong() {
		return futong;
	}
	public void setFutong(String futong) {
		this.futong = futong;
	}
	public String getFutong_cases() {
		return futong_cases;
	}
	public void setFutong_cases(String futongCases) {
		futong_cases = futongCases;
	}
	public String getFutong_position() {
		return futong_position;
	}
	public void setFutong_position(String futongPosition) {
		futong_position = futongPosition;
	}
	public String getFutong_performance() {
		return futong_performance;
	}
	public void setFutong_performance(String futongPerformance) {
		futong_performance = futongPerformance;
	}
	public String getFutong_degree() {
		return futong_degree;
	}
	public void setFutong_degree(String futongDegree) {
		futong_degree = futongDegree;
	}
	public String getFutong_rate() {
		return futong_rate;
	}
	public void setFutong_rate(String futongRate) {
		futong_rate = futongRate;
	}
	public String getFutong_position2() {
		return futong_position2;
	}
	public void setFutong_position2(String futongPosition2) {
		futong_position2 = futongPosition2;
	}
	public String getFutong_time() {
		return futong_time;
	}
	public void setFutong_time(String futongTime) {
		futong_time = futongTime;
	}
	public String getFutong_fangshe() {
		return futong_fangshe;
	}
	public void setFutong_fangshe(String futongFangshe) {
		futong_fangshe = futongFangshe;
	}
	public String getFutong_fangshe0() {
		return futong_fangshe0;
	}
	public void setFutong_fangshe0(String futongFangshe0) {
		futong_fangshe0 = futongFangshe0;
	}
	public String getFutong_change() {
		return futong_change;
	}
	public void setFutong_change(String futongChange) {
		futong_change = futongChange;
	}
	public String getFuxie() {
		return fuxie;
	}
	public void setFuxie(String fuxie) {
		this.fuxie = fuxie;
	}
	public String getFuxie_liji() {
		return fuxie_liji;
	}
	public void setFuxie_liji(String fuxieLiji) {
		fuxie_liji = fuxieLiji;
	}
	public String getFuxie_shape() {
		return fuxie_shape;
	}
	public void setFuxie_shape(String fuxieShape) {
		fuxie_shape = fuxieShape;
	}
	public String getFuxie_countDay() {
		return fuxie_countDay;
	}
	public void setFuxie_countDay(String fuxieCountDay) {
		fuxie_countDay = fuxieCountDay;
	}
	public String getFuxie_volumeDay() {
		return fuxie_volumeDay;
	}
	public void setFuxie_volumeDay(String fuxieVolumeDay) {
		fuxie_volumeDay = fuxieVolumeDay;
	}
	public String getShuizhong() {
		return shuizhong;
	}
	public void setShuizhong(String shuizhong) {
		this.shuizhong = shuizhong;
	}
	public String getShuizhong_buwei() {
		return shuizhong_buwei;
	}
	public void setShuizhong_buwei(String shuizhongBuwei) {
		shuizhong_buwei = shuizhongBuwei;
	}
	public String getShuizhong_chengdu() {
		return shuizhong_chengdu;
	}
	public void setShuizhong_chengdu(String shuizhongChengdu) {
		shuizhong_chengdu = shuizhongChengdu;
	}
	public String getShuizhong_xingzhi() {
		return shuizhong_xingzhi;
	}
	public void setShuizhong_xingzhi(String shuizhongXingzhi) {
		shuizhong_xingzhi = shuizhongXingzhi;
	}
	public String getFare() {
		return fare;
	}
	public void setFare(String fare) {
		this.fare = fare;
	}
	public String getFare_type() {
		return fare_type;
	}
	public void setFare_type(String fareType) {
		fare_type = fareType;
	}
	public String getFare_tiwen() {
		return fare_tiwen;
	}
	public void setFare_tiwen(String fareTiwen) {
		fare_tiwen = fareTiwen;
	}
	public String getFare_topt() {
		return fare_topt;
	}
	public void setFare_topt(String fareTopt) {
		fare_topt = fareTopt;
	}
	public String getFare_performance() {
		return fare_performance;
	}
	public void setFare_performance(String farePerformance) {
		fare_performance = farePerformance;
	}
	public String getFare_side() {
		return fare_side;
	}
	public void setFare_side(String fareSide) {
		fare_side = fareSide;
	}
	public String getHuangran() {
		return huangran;
	}
	public void setHuangran(String huangran) {
		this.huangran = huangran;
	}
	public String getHuangran_performance() {
		return huangran_performance;
	}
	public void setHuangran_performance(String huangranPerformance) {
		huangran_performance = huangranPerformance;
	}
	public String getHuangran_degree() {
		return huangran_degree;
	}
	public void setHuangran_degree(String huangranDegree) {
		huangran_degree = huangranDegree;
	}
	public String getHuangran_side() {
		return huangran_side;
	}
	public void setHuangran_side(String huangranSide) {
		huangran_side = huangranSide;
	}
	public String getHuangran_noside() {
		return huangran_noside;
	}
	public void setHuangran_noside(String huangranNoside) {
		huangran_noside = huangranNoside;
	}
	public String getNiaoye() {
		return niaoye;
	}
	public void setNiaoye(String niaoye) {
		this.niaoye = niaoye;
	}
	public String getNiaoye_color() {
		return niaoye_color;
	}
	public void setNiaoye_color(String niaoyeColor) {
		niaoye_color = niaoyeColor;
	}
	public String getNiaoye_qldegree() {
		return niaoye_qldegree;
	}
	public void setNiaoye_qldegree(String niaoyeQldegree) {
		niaoye_qldegree = niaoyeQldegree;
	}
	public String getNiaoye_volume() {
		return niaoye_volume;
	}
	public void setNiaoye_volume(String niaoyeVolume) {
		niaoye_volume = niaoyeVolume;
	}
	public String getNiaoye_volumeTotal() {
		return niaoye_volumeTotal;
	}
	public void setNiaoye_volumeTotal(String niaoyeVolumeTotal) {
		niaoye_volumeTotal = niaoyeVolumeTotal;
	}
	public String getDabian() {
		return dabian;
	}
	public void setDabian(String dabian) {
		this.dabian = dabian;
	}
	public String getDabian_huibai() {
		return dabian_huibai;
	}
	public void setDabian_huibai(String dabianHuibai) {
		dabian_huibai = dabianHuibai;
	}
	public String getDabian_xue() {
		return dabian_xue;
	}
	public void setDabian_xue(String dabianXue) {
		dabian_xue = dabianXue;
	}
	public String getDabian_color() {
		return dabian_color;
	}
	public void setDabian_color(String dabianColor) {
		dabian_color = dabianColor;
	}
	public String getDabian_shape() {
		return dabian_shape;
	}
	public void setDabian_shape(String dabianShape) {
		dabian_shape = dabianShape;
	}
	public String getDabian_countDay() {
		return dabian_countDay;
	}
	public void setDabian_countDay(String dabianCountDay) {
		dabian_countDay = dabianCountDay;
	}
	public String getDabian_volumeDay() {
		return dabian_volumeDay;
	}
	public void setDabian_volumeDay(String dabianVolumeDay) {
		dabian_volumeDay = dabianVolumeDay;
	}
	public String getDabian_countTotal() {
		return dabian_countTotal;
	}
	public void setDabian_countTotal(String dabianCountTotal) {
		dabian_countTotal = dabianCountTotal;
	}
	public String getDabian_volumeTotal() {
		return dabian_volumeTotal;
	}
	public void setDabian_volumeTotal(String dabianVolumeTotal) {
		dabian_volumeTotal = dabianVolumeTotal;
	}
	public String getDabian_side() {
		return dabian_side;
	}
	public void setDabian_side(String dabianSide) {
		dabian_side = dabianSide;
	}
	public String getShenzhi() {
		return shenzhi;
	}
	public void setShenzhi(String shenzhi) {
		this.shenzhi = shenzhi;
	}
	public String getShenzhi_performance() {
		return shenzhi_performance;
	}
	public void setShenzhi_performance(String shenzhiPerformance) {
		shenzhi_performance = shenzhiPerformance;
	}
	public String getShenzhi_answer() {
		return shenzhi_answer;
	}
	public void setShenzhi_answer(String shenzhiAnswer) {
		shenzhi_answer = shenzhiAnswer;
	}
	public String getXingwei() {
		return xingwei;
	}
	public void setXingwei(String xingwei) {
		this.xingwei = xingwei;
	}
	public String getXingwei_performance() {
		return xingwei_performance;
	}
	public void setXingwei_performance(String xingweiPerformance) {
		xingwei_performance = xingweiPerformance;
	}
	public String getCasesDesc() {
		return casesDesc;
	}
	public void setCasesDesc(String casesDesc) {
		this.casesDesc = casesDesc;
	}
	public String getMainSysptomDesc() {
		return mainSysptomDesc;
	}
	public void setMainSysptomDesc(String mainSysptomDesc) {
		this.mainSysptomDesc = mainSysptomDesc;
	}
	public String getSideSysptomDesc() {
		return sideSysptomDesc;
	}
	public void setSideSysptomDesc(String sideSysptomDesc) {
		this.sideSysptomDesc = sideSysptomDesc;
	}
	public String getTreatmentDesc() {
		return treatmentDesc;
	}
	public void setTreatmentDesc(String treatmentDesc) {
		this.treatmentDesc = treatmentDesc;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	public String getTimeUnitSuffix() {
		return timeUnitSuffix;
	}
	public void setTimeUnitSuffix(String timeUnitSuffix) {
		this.timeUnitSuffix = timeUnitSuffix;
	}
	public String getCausesFlag() {
		return causesFlag;
	}
	public void setCausesFlag(String causesFlag) {
		this.causesFlag = causesFlag;
	}
	public String getCauses() {
		return causes;
	}
	public void setCauses(String causes) {
		this.causes = causes;
	}
	public String getOtherTimeCausesFlag() {
		return otherTimeCausesFlag;
	}
	public void setOtherTimeCausesFlag(String otherTimeCausesFlag) {
		this.otherTimeCausesFlag = otherTimeCausesFlag;
	}
	public String getOtherTimeCauses() {
		return otherTimeCauses;
	}
	public void setOtherTimeCauses(String otherTimeCauses) {
		this.otherTimeCauses = otherTimeCauses;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PresentIllnessHistoryN getN() {
		return n;
	}
	public void setN(PresentIllnessHistoryN n) {
		this.n = n;
	}
	public String getOtherPosit() {
		return otherPosit;
	}
	public void setOtherPosit(String otherPosit) {
		this.otherPosit = otherPosit;
	}
	public String getSide_fengshi() {
		return side_fengshi;
	}
	public void setSide_fengshi(String side_fengshi) {
		this.side_fengshi = side_fengshi;
	}
	public String getSide_fengshi0() {
		return side_fengshi0;
	}
	public void setSide_fengshi0(String side_fengshi0) {
		this.side_fengshi0 = side_fengshi0;
	}
	public String getInspectionUnit() {
		return inspectionUnit;
	}
	public void setInspectionUnit(String inspectionUnit) {
		this.inspectionUnit = inspectionUnit;
	}
	public String getExperunit() {
		return experunit;
	}
	public void setExperunit(String experunit) {
		this.experunit = experunit;
	}
	public String getQuestionDesc() {
		return questionDesc;
	}
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}
}
