package com.juncsoft.KLJY.InHospitalCase.Surgery.entity;

public class SpecialExamination{
	private Long id;
	private Long caseId;
	// 腹部-视诊
	private String fubu_waixing;
	private int fubu_huxi;
	private int fubu_jingmai;
	private int fubu_shizhen_o;
	private String fubu_shizhen_oDesc;
	// 腹部-触诊
	private String fubu_fubi;
	private String fubu_jijzh;
	private String fubu_yatong;
	private String fubu_fantt;
	private String fubu_fubi0;
	private String fubu_jijzh0;
	private String fubu_yatong0;
	private String fubu_fantt0;
	private int fubu_baokuai;
	private String fubu_baokuaiDesc;
	private int fubu_yebo;
	private int fubu_zhenshui;
	private String fubu_murphy;
	private int fubu_ganzang;
	private String fubu_ganzangDesc;
	private int fubu_dannang;
	private String fubu_dannangDesc;
	private int fubu_pi;
	private String fubu_piDesc;
	private int fubu_shen;
	private String fubu_shenDesc;
	// 腹部-叩诊
	private String fubu_ganzhuo;
	private int fubu_ganqukt;
	private String fubu_shenkt;
	private String fubu_ganshangjie;
	private String fubu_yidong;
	private String fubu_fushui;
	// 腹部-听诊
	private String fubu_changming;
	private int fubu_qishui;
	private int fubu_xueguan;
	private String fubu_xueguanDesc;
	//其它
	private String specialExamOther;
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
	public String getFubu_waixing() {
		return fubu_waixing;
	}
	public void setFubu_waixing(String fubuWaixing) {
		fubu_waixing = fubuWaixing;
	}
	public int getFubu_huxi() {
		return fubu_huxi;
	}
	public void setFubu_huxi(int fubuHuxi) {
		fubu_huxi = fubuHuxi;
	}
	public int getFubu_jingmai() {
		return fubu_jingmai;
	}
	public void setFubu_jingmai(int fubuJingmai) {
		fubu_jingmai = fubuJingmai;
	}
	public int getFubu_shizhen_o() {
		return fubu_shizhen_o;
	}
	public void setFubu_shizhen_o(int fubuShizhenO) {
		fubu_shizhen_o = fubuShizhenO;
	}
	public String getFubu_shizhen_oDesc() {
		return fubu_shizhen_oDesc;
	}
	public void setFubu_shizhen_oDesc(String fubuShizhenODesc) {
		fubu_shizhen_oDesc = fubuShizhenODesc;
	}
	public String getFubu_fubi() {
		return fubu_fubi;
	}
	public void setFubu_fubi(String fubuFubi) {
		fubu_fubi = fubuFubi;
	}
	public String getFubu_jijzh() {
		return fubu_jijzh;
	}
	public void setFubu_jijzh(String fubuJijzh) {
		fubu_jijzh = fubuJijzh;
	}
	public String getFubu_yatong() {
		return fubu_yatong;
	}
	public void setFubu_yatong(String fubuYatong) {
		fubu_yatong = fubuYatong;
	}
	public String getFubu_fantt() {
		return fubu_fantt;
	}
	public void setFubu_fantt(String fubuFantt) {
		fubu_fantt = fubuFantt;
	}
	public String getFubu_fubi0() {
		return fubu_fubi0;
	}
	public void setFubu_fubi0(String fubuFubi0) {
		fubu_fubi0 = fubuFubi0;
	}
	public String getFubu_jijzh0() {
		return fubu_jijzh0;
	}
	public void setFubu_jijzh0(String fubuJijzh0) {
		fubu_jijzh0 = fubuJijzh0;
	}
	public String getFubu_yatong0() {
		return fubu_yatong0;
	}
	public void setFubu_yatong0(String fubuYatong0) {
		fubu_yatong0 = fubuYatong0;
	}
	public String getFubu_fantt0() {
		return fubu_fantt0;
	}
	public void setFubu_fantt0(String fubuFantt0) {
		fubu_fantt0 = fubuFantt0;
	}
	public int getFubu_baokuai() {
		return fubu_baokuai;
	}
	public void setFubu_baokuai(int fubuBaokuai) {
		fubu_baokuai = fubuBaokuai;
	}
	public String getFubu_baokuaiDesc() {
		return fubu_baokuaiDesc;
	}
	public void setFubu_baokuaiDesc(String fubuBaokuaiDesc) {
		fubu_baokuaiDesc = fubuBaokuaiDesc;
	}
	public int getFubu_yebo() {
		return fubu_yebo;
	}
	public void setFubu_yebo(int fubuYebo) {
		fubu_yebo = fubuYebo;
	}
	public int getFubu_zhenshui() {
		return fubu_zhenshui;
	}
	public void setFubu_zhenshui(int fubuZhenshui) {
		fubu_zhenshui = fubuZhenshui;
	}
	public String getFubu_murphy() {
		return fubu_murphy;
	}
	public void setFubu_murphy(String fubuMurphy) {
		fubu_murphy = fubuMurphy;
	}
	public int getFubu_ganzang() {
		return fubu_ganzang;
	}
	public void setFubu_ganzang(int fubuGanzang) {
		fubu_ganzang = fubuGanzang;
	}
	public String getFubu_ganzangDesc() {
		return fubu_ganzangDesc;
	}
	public void setFubu_ganzangDesc(String fubuGanzangDesc) {
		fubu_ganzangDesc = fubuGanzangDesc;
	}
	public int getFubu_dannang() {
		return fubu_dannang;
	}
	public void setFubu_dannang(int fubuDannang) {
		fubu_dannang = fubuDannang;
	}
	public String getFubu_dannangDesc() {
		return fubu_dannangDesc;
	}
	public void setFubu_dannangDesc(String fubuDannangDesc) {
		fubu_dannangDesc = fubuDannangDesc;
	}
	public int getFubu_pi() {
		return fubu_pi;
	}
	public void setFubu_pi(int fubuPi) {
		fubu_pi = fubuPi;
	}
	public String getFubu_piDesc() {
		return fubu_piDesc;
	}
	public void setFubu_piDesc(String fubuPiDesc) {
		fubu_piDesc = fubuPiDesc;
	}
	public int getFubu_shen() {
		return fubu_shen;
	}
	public void setFubu_shen(int fubuShen) {
		fubu_shen = fubuShen;
	}
	public String getFubu_shenDesc() {
		return fubu_shenDesc;
	}
	public void setFubu_shenDesc(String fubuShenDesc) {
		fubu_shenDesc = fubuShenDesc;
	}
	public String getFubu_ganzhuo() {
		return fubu_ganzhuo;
	}
	public void setFubu_ganzhuo(String fubuGanzhuo) {
		fubu_ganzhuo = fubuGanzhuo;
	}
	public int getFubu_ganqukt() {
		return fubu_ganqukt;
	}
	public void setFubu_ganqukt(int fubuGanqukt) {
		fubu_ganqukt = fubuGanqukt;
	}
	public String getFubu_shenkt() {
		return fubu_shenkt;
	}
	public void setFubu_shenkt(String fubuShenkt) {
		fubu_shenkt = fubuShenkt;
	}
	public String getFubu_ganshangjie() {
		return fubu_ganshangjie;
	}
	public void setFubu_ganshangjie(String fubuGanshangjie) {
		fubu_ganshangjie = fubuGanshangjie;
	}
	public String getFubu_yidong() {
		return fubu_yidong;
	}
	public void setFubu_yidong(String fubuYidong) {
		fubu_yidong = fubuYidong;
	}
	public String getFubu_fushui() {
		return fubu_fushui;
	}
	public void setFubu_fushui(String fubuFushui) {
		fubu_fushui = fubuFushui;
	}
	public String getFubu_changming() {
		return fubu_changming;
	}
	public void setFubu_changming(String fubuChangming) {
		fubu_changming = fubuChangming;
	}
	public int getFubu_qishui() {
		return fubu_qishui;
	}
	public void setFubu_qishui(int fubuQishui) {
		fubu_qishui = fubuQishui;
	}
	public int getFubu_xueguan() {
		return fubu_xueguan;
	}
	public void setFubu_xueguan(int fubuXueguan) {
		fubu_xueguan = fubuXueguan;
	}
	public String getFubu_xueguanDesc() {
		return fubu_xueguanDesc;
	}
	public void setFubu_xueguanDesc(String fubuXueguanDesc) {
		fubu_xueguanDesc = fubuXueguanDesc;
	}
	public String getSpecialExamOther() {
		return specialExamOther;
	}
	public void setSpecialExamOther(String specialExamOther) {
		this.specialExamOther = specialExamOther;
	}
}