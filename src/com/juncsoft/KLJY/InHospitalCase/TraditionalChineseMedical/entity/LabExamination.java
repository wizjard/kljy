package com.juncsoft.KLJY.InHospitalCase.TraditionalChineseMedical.entity;

import java.util.Date;

public class LabExamination {
	private Long id;
	// 血生化
	private int xrt_mark;
	private Date xrt_checkdate;
	private String xrt_wbc;
	private String xrt_neut1;
	private String xrt_neut2;
	private String xrt_plt;
	private String xrt_ly;
	private String xrt_rbc;
	private String xrt_hb;
	private String xrt_abo;
	private String xrt_other;
	// 尿常规
	private int nrt_mark;
	private Date nrt_checkdate;
	private String nrt_color;
	private String nrt_pro;
	private String nrt_blo;
	private String nrt_uro;
	private String nrt_glu;
	private String nrt_ket;
	private String nrt_wbc;
	private String nrt_rbc;
	private String nrt_other;
	private String nrt_danbai;
	private String nrt_putao;
	private String nrt_niaodan;
	private String nrt_danhong;
	// 便常规
	private int brt_mark;
	private Date brt_checkdate;
	private String brt_dlsy;
	private String brt_zbsy;
	private String brt_ob;
	private String brt_color;
	private String brt_xzh;
	private String brt_rbc;
	private String brt_wbc;
	private String brt_other;
	private String brt_rbc2;
	private String brt_wbc2;
	// 肝功
	private int gangong_mark;
	private Date gangong_checkdate;
	private String gangong_alt;
	private String gangong_ast;
	private String gangong_che;
	private String gangong_tp;
	private String gangong_alb;
	private String gangong_tbil;
	private String gangong_dbil;
	private String gangong_cho;
	private String gangong_palb;
	private String gangong_other;
	// 血生化
	private int xuesh_mark;
	private Date xuesh_checkdate;
	private String xuesh_k;
	private String xuesh_bun;
	private String xuesh_na;
	private String xuesh_cl;
	private String xuesh_glu;
	private String xuesh_cr;
	private String xuesh_other;
	private String xuesh_bla;
	// 乙肝
	private int yigan_mark;
	private Date yigan_checkdate;
	private String yigan_hbsag;
	private String yigan_antihbe;
	private String yigan_hbeag;
	private String yigan_antihbs;
	private String yigan_antihbc;
	private String yigan_hbvdna;
	private String yigan_hbvLv;
	// 甲肝
	private int jiagan_mark;
	private Date jiagan_checkdate;
	private String jiagan_antihavlgm;
	// 丙肝
	private int binggan_mark;
	private Date binggan_checkdate;
	private String binggan_antihcvlg;
	private String binggan_antihcvlgg;
	private String binggan_hcvrna;
	// 丁肝
	private int dinggan_mark;
	private Date dinggan_checkdate;
	private String dinggan_hdvab;
	private String dinggan_hdvag;
	// 戊肝
	private int wugan_mark;
	private Date wugan_checkdate;
	private String wugan_hbsag;
	private String wugan_antihbe;
	private String wugan_antihbs;
	private String wugan_antihbc;
	private String wugan_antihevlgm;
	private String wugan_antihevlgg;
	// 庚肝
	private int genggan_mark;
	private Date genggan_checkdate;
	private String genggan_antihiv;
	// 动脉血气
	private int dmxq_mark;
	private Date dmxq_checkdate;
	private String dmxq_po2;
	private String dmxq_pco2;
	private String dmxq_ph;
	private String dmxq_sao;
	private String dmxq_other;
	// 凝血
	private int nx_mark;
	private Date nx_checkdate;
	private String nx_inr;
	private String nx_fib;
	private String nx_aptt;
	private String nx_pta;
	private String nx_pt;
	private String nx_other;
	// 血脂
	private int xuezhi_mark;
	private Date xuezhi_checkdate;
	private String xuezhi_hdlc;
	private String xuezhi_ldlc;
	private String xuezhi_tg;
	private String xuezhi_chol;
	private String xuezhi_other;
	// 肿瘤标记物
	private int zhlbjw_mark;
	private Date zhlbjw_checkdate;
	private String zhlbjw_afp;
	private String zhlbjw_cea;
	private String zhlbjw_ca199;
	private String zhlbjw_ca242;
	private String zhlbjw_other;
	// 血药浓度
	private int xynd_mark;
	private Date xynd_checkdate;
	private String xynd_plkf;
	private String xynd_lpms;
	private String xynd_other;
	// X线平片
	private int xline_mark;
	private Date xline_checkdate;
	private String xline_checkResult;
	// 心电图
	private int heart_mark;
	private Date heart_checkdate;
	private String heart_checkResult;
	// B超
	private int bc_mark;
	private Date bc_checkdate;
	private String bc_checkResult;
	// CT
	private int ct_mark;
	private Date ct_checkdate;
	private String ct_checkResult;
	// MRI
	private int mri_mark;
	private Date mri_checkdate;
	private String mri_checkResult;
	// 消化道造影
	private int xhd_mark;
	private Date xhd_checkdate;
	private String xhd_checkResult;
	// 胃镜
	private int weijing_mark;
	private Date weijing_checkdate;
	private String weijing_checkResult;
	// 结肠镜
	private int jiechang_mark;
	private Date jiechang_checkdate;
	private String jiechang_checkResult;
	// 十二指肠镜
	private int zhichang_mark;
	private Date zhichang_checkdate;
	private String zhichang_checkResult;
	// 胶囊内镜
	private int jiaon_mark;
	private Date jiaon_checkdate;
	private String jiaon_checkResult;
	// 尿素呼气试验
	private int niaosu_mark;
	private Date niaosu_checkdate;
	private String niaosu_checkResult;
	// 病理检查
	private int bingli_mark;
	private String bingli_checkResult;
	private String bingli_fenji;
	public String getNrt_danbai() {
		return nrt_danbai;
	}
	public void setNrt_danbai(String nrtDanbai) {
		nrt_danbai = nrtDanbai;
	}
	public String getNrt_putao() {
		return nrt_putao;
	}
	public void setNrt_putao(String nrtPutao) {
		nrt_putao = nrtPutao;
	}
	public String getNrt_niaodan() {
		return nrt_niaodan;
	}
	public void setNrt_niaodan(String nrtNiaodan) {
		nrt_niaodan = nrtNiaodan;
	}
	public String getNrt_danhong() {
		return nrt_danhong;
	}
	public void setNrt_danhong(String nrtDanhong) {
		nrt_danhong = nrtDanhong;
	}
	public String getBrt_rbc2() {
		return brt_rbc2;
	}
	public void setBrt_rbc2(String brtRbc2) {
		brt_rbc2 = brtRbc2;
	}
	public String getBrt_wbc2() {
		return brt_wbc2;
	}
	public void setBrt_wbc2(String brtWbc2) {
		brt_wbc2 = brtWbc2;
	}
	public String getXuesh_bla() {
		return xuesh_bla;
	}
	public void setXuesh_bla(String xueshBla) {
		xuesh_bla = xueshBla;
	}
	public String getYigan_hbvLv() {
		return yigan_hbvLv;
	}
	public void setYigan_hbvLv(String yiganHbvLv) {
		yigan_hbvLv = yiganHbvLv;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getXrt_mark() {
		return xrt_mark;
	}
	public void setXrt_mark(int xrtMark) {
		xrt_mark = xrtMark;
	}
	public Date getXrt_checkdate() {
		return xrt_checkdate;
	}
	public void setXrt_checkdate(Date xrtCheckdate) {
		xrt_checkdate = xrtCheckdate;
	}
	public String getXrt_wbc() {
		return xrt_wbc;
	}
	public void setXrt_wbc(String xrtWbc) {
		xrt_wbc = xrtWbc;
	}
	public String getXrt_neut1() {
		return xrt_neut1;
	}
	public void setXrt_neut1(String xrtNeut1) {
		xrt_neut1 = xrtNeut1;
	}
	public String getXrt_neut2() {
		return xrt_neut2;
	}
	public void setXrt_neut2(String xrtNeut2) {
		xrt_neut2 = xrtNeut2;
	}
	public String getXrt_plt() {
		return xrt_plt;
	}
	public void setXrt_plt(String xrtPlt) {
		xrt_plt = xrtPlt;
	}
	public String getXrt_ly() {
		return xrt_ly;
	}
	public void setXrt_ly(String xrtLy) {
		xrt_ly = xrtLy;
	}
	public String getXrt_rbc() {
		return xrt_rbc;
	}
	public void setXrt_rbc(String xrtRbc) {
		xrt_rbc = xrtRbc;
	}
	public String getXrt_hb() {
		return xrt_hb;
	}
	public void setXrt_hb(String xrtHb) {
		xrt_hb = xrtHb;
	}
	public String getXrt_abo() {
		return xrt_abo;
	}
	public void setXrt_abo(String xrtAbo) {
		xrt_abo = xrtAbo;
	}
	public String getXrt_other() {
		return xrt_other;
	}
	public void setXrt_other(String xrtOther) {
		xrt_other = xrtOther;
	}
	public int getNrt_mark() {
		return nrt_mark;
	}
	public void setNrt_mark(int nrtMark) {
		nrt_mark = nrtMark;
	}
	public Date getNrt_checkdate() {
		return nrt_checkdate;
	}
	public void setNrt_checkdate(Date nrtCheckdate) {
		nrt_checkdate = nrtCheckdate;
	}
	public String getNrt_color() {
		return nrt_color;
	}
	public void setNrt_color(String nrtColor) {
		nrt_color = nrtColor;
	}
	public String getNrt_pro() {
		return nrt_pro;
	}
	public void setNrt_pro(String nrtPro) {
		nrt_pro = nrtPro;
	}
	public String getNrt_blo() {
		return nrt_blo;
	}
	public void setNrt_blo(String nrtBlo) {
		nrt_blo = nrtBlo;
	}
	public String getNrt_uro() {
		return nrt_uro;
	}
	public void setNrt_uro(String nrtUro) {
		nrt_uro = nrtUro;
	}
	public String getNrt_glu() {
		return nrt_glu;
	}
	public void setNrt_glu(String nrtGlu) {
		nrt_glu = nrtGlu;
	}
	public String getNrt_ket() {
		return nrt_ket;
	}
	public void setNrt_ket(String nrtKet) {
		nrt_ket = nrtKet;
	}
	public String getNrt_wbc() {
		return nrt_wbc;
	}
	public void setNrt_wbc(String nrtWbc) {
		nrt_wbc = nrtWbc;
	}
	public String getNrt_rbc() {
		return nrt_rbc;
	}
	public void setNrt_rbc(String nrtRbc) {
		nrt_rbc = nrtRbc;
	}
	public String getNrt_other() {
		return nrt_other;
	}
	public void setNrt_other(String nrtOther) {
		nrt_other = nrtOther;
	}
	public int getBrt_mark() {
		return brt_mark;
	}
	public void setBrt_mark(int brtMark) {
		brt_mark = brtMark;
	}
	public Date getBrt_checkdate() {
		return brt_checkdate;
	}
	public void setBrt_checkdate(Date brtCheckdate) {
		brt_checkdate = brtCheckdate;
	}
	public String getBrt_dlsy() {
		return brt_dlsy;
	}
	public void setBrt_dlsy(String brtDlsy) {
		brt_dlsy = brtDlsy;
	}
	public String getBrt_zbsy() {
		return brt_zbsy;
	}
	public void setBrt_zbsy(String brtZbsy) {
		brt_zbsy = brtZbsy;
	}
	public String getBrt_ob() {
		return brt_ob;
	}
	public void setBrt_ob(String brtOb) {
		brt_ob = brtOb;
	}
	public String getBrt_color() {
		return brt_color;
	}
	public void setBrt_color(String brtColor) {
		brt_color = brtColor;
	}
	public String getBrt_xzh() {
		return brt_xzh;
	}
	public void setBrt_xzh(String brtXzh) {
		brt_xzh = brtXzh;
	}
	public String getBrt_rbc() {
		return brt_rbc;
	}
	public void setBrt_rbc(String brtRbc) {
		brt_rbc = brtRbc;
	}
	public String getBrt_wbc() {
		return brt_wbc;
	}
	public void setBrt_wbc(String brtWbc) {
		brt_wbc = brtWbc;
	}
	public String getBrt_other() {
		return brt_other;
	}
	public void setBrt_other(String brtOther) {
		brt_other = brtOther;
	}
	public int getGangong_mark() {
		return gangong_mark;
	}
	public void setGangong_mark(int gangongMark) {
		gangong_mark = gangongMark;
	}
	public Date getGangong_checkdate() {
		return gangong_checkdate;
	}
	public void setGangong_checkdate(Date gangongCheckdate) {
		gangong_checkdate = gangongCheckdate;
	}
	public String getGangong_alt() {
		return gangong_alt;
	}
	public void setGangong_alt(String gangongAlt) {
		gangong_alt = gangongAlt;
	}
	public String getGangong_ast() {
		return gangong_ast;
	}
	public void setGangong_ast(String gangongAst) {
		gangong_ast = gangongAst;
	}
	public String getGangong_che() {
		return gangong_che;
	}
	public void setGangong_che(String gangongChe) {
		gangong_che = gangongChe;
	}
	public String getGangong_tp() {
		return gangong_tp;
	}
	public void setGangong_tp(String gangongTp) {
		gangong_tp = gangongTp;
	}
	public String getGangong_alb() {
		return gangong_alb;
	}
	public void setGangong_alb(String gangongAlb) {
		gangong_alb = gangongAlb;
	}
	public String getGangong_tbil() {
		return gangong_tbil;
	}
	public void setGangong_tbil(String gangongTbil) {
		gangong_tbil = gangongTbil;
	}
	public String getGangong_dbil() {
		return gangong_dbil;
	}
	public void setGangong_dbil(String gangongDbil) {
		gangong_dbil = gangongDbil;
	}
	public String getGangong_cho() {
		return gangong_cho;
	}
	public void setGangong_cho(String gangongCho) {
		gangong_cho = gangongCho;
	}
	public String getGangong_palb() {
		return gangong_palb;
	}
	public void setGangong_palb(String gangongPalb) {
		gangong_palb = gangongPalb;
	}
	public String getGangong_other() {
		return gangong_other;
	}
	public void setGangong_other(String gangongOther) {
		gangong_other = gangongOther;
	}
	public int getXuesh_mark() {
		return xuesh_mark;
	}
	public void setXuesh_mark(int xueshMark) {
		xuesh_mark = xueshMark;
	}
	public Date getXuesh_checkdate() {
		return xuesh_checkdate;
	}
	public void setXuesh_checkdate(Date xueshCheckdate) {
		xuesh_checkdate = xueshCheckdate;
	}
	public String getXuesh_k() {
		return xuesh_k;
	}
	public void setXuesh_k(String xueshK) {
		xuesh_k = xueshK;
	}
	public String getXuesh_bun() {
		return xuesh_bun;
	}
	public void setXuesh_bun(String xueshBun) {
		xuesh_bun = xueshBun;
	}
	public String getXuesh_na() {
		return xuesh_na;
	}
	public void setXuesh_na(String xueshNa) {
		xuesh_na = xueshNa;
	}
	public String getXuesh_cl() {
		return xuesh_cl;
	}
	public void setXuesh_cl(String xueshCl) {
		xuesh_cl = xueshCl;
	}
	public String getXuesh_glu() {
		return xuesh_glu;
	}
	public void setXuesh_glu(String xueshGlu) {
		xuesh_glu = xueshGlu;
	}
	public String getXuesh_cr() {
		return xuesh_cr;
	}
	public void setXuesh_cr(String xueshCr) {
		xuesh_cr = xueshCr;
	}
	public String getXuesh_other() {
		return xuesh_other;
	}
	public void setXuesh_other(String xueshOther) {
		xuesh_other = xueshOther;
	}
	public int getYigan_mark() {
		return yigan_mark;
	}
	public void setYigan_mark(int yiganMark) {
		yigan_mark = yiganMark;
	}
	public Date getYigan_checkdate() {
		return yigan_checkdate;
	}
	public void setYigan_checkdate(Date yiganCheckdate) {
		yigan_checkdate = yiganCheckdate;
	}
	public String getYigan_hbsag() {
		return yigan_hbsag;
	}
	public void setYigan_hbsag(String yiganHbsag) {
		yigan_hbsag = yiganHbsag;
	}
	public String getYigan_antihbe() {
		return yigan_antihbe;
	}
	public void setYigan_antihbe(String yiganAntihbe) {
		yigan_antihbe = yiganAntihbe;
	}
	public String getYigan_hbeag() {
		return yigan_hbeag;
	}
	public void setYigan_hbeag(String yiganHbeag) {
		yigan_hbeag = yiganHbeag;
	}
	public String getYigan_antihbs() {
		return yigan_antihbs;
	}
	public void setYigan_antihbs(String yiganAntihbs) {
		yigan_antihbs = yiganAntihbs;
	}
	public String getYigan_antihbc() {
		return yigan_antihbc;
	}
	public void setYigan_antihbc(String yiganAntihbc) {
		yigan_antihbc = yiganAntihbc;
	}
	public String getYigan_hbvdna() {
		return yigan_hbvdna;
	}
	public void setYigan_hbvdna(String yiganHbvdna) {
		yigan_hbvdna = yiganHbvdna;
	}
	public int getJiagan_mark() {
		return jiagan_mark;
	}
	public void setJiagan_mark(int jiaganMark) {
		jiagan_mark = jiaganMark;
	}
	public Date getJiagan_checkdate() {
		return jiagan_checkdate;
	}
	public void setJiagan_checkdate(Date jiaganCheckdate) {
		jiagan_checkdate = jiaganCheckdate;
	}
	public String getJiagan_antihavlgm() {
		return jiagan_antihavlgm;
	}
	public void setJiagan_antihavlgm(String jiaganAntihavlgm) {
		jiagan_antihavlgm = jiaganAntihavlgm;
	}
	public int getBinggan_mark() {
		return binggan_mark;
	}
	public void setBinggan_mark(int bingganMark) {
		binggan_mark = bingganMark;
	}
	public Date getBinggan_checkdate() {
		return binggan_checkdate;
	}
	public void setBinggan_checkdate(Date bingganCheckdate) {
		binggan_checkdate = bingganCheckdate;
	}
	public String getBinggan_antihcvlg() {
		return binggan_antihcvlg;
	}
	public void setBinggan_antihcvlg(String bingganAntihcvlg) {
		binggan_antihcvlg = bingganAntihcvlg;
	}
	public String getBinggan_antihcvlgg() {
		return binggan_antihcvlgg;
	}
	public void setBinggan_antihcvlgg(String bingganAntihcvlgg) {
		binggan_antihcvlgg = bingganAntihcvlgg;
	}
	public String getBinggan_hcvrna() {
		return binggan_hcvrna;
	}
	public void setBinggan_hcvrna(String bingganHcvrna) {
		binggan_hcvrna = bingganHcvrna;
	}
	public int getDinggan_mark() {
		return dinggan_mark;
	}
	public void setDinggan_mark(int dingganMark) {
		dinggan_mark = dingganMark;
	}
	public Date getDinggan_checkdate() {
		return dinggan_checkdate;
	}
	public void setDinggan_checkdate(Date dingganCheckdate) {
		dinggan_checkdate = dingganCheckdate;
	}
	public String getDinggan_hdvab() {
		return dinggan_hdvab;
	}
	public void setDinggan_hdvab(String dingganHdvab) {
		dinggan_hdvab = dingganHdvab;
	}
	public String getDinggan_hdvag() {
		return dinggan_hdvag;
	}
	public void setDinggan_hdvag(String dingganHdvag) {
		dinggan_hdvag = dingganHdvag;
	}
	public int getWugan_mark() {
		return wugan_mark;
	}
	public void setWugan_mark(int wuganMark) {
		wugan_mark = wuganMark;
	}
	public Date getWugan_checkdate() {
		return wugan_checkdate;
	}
	public void setWugan_checkdate(Date wuganCheckdate) {
		wugan_checkdate = wuganCheckdate;
	}
	public String getWugan_hbsag() {
		return wugan_hbsag;
	}
	public void setWugan_hbsag(String wuganHbsag) {
		wugan_hbsag = wuganHbsag;
	}
	public String getWugan_antihbe() {
		return wugan_antihbe;
	}
	public void setWugan_antihbe(String wuganAntihbe) {
		wugan_antihbe = wuganAntihbe;
	}
	public String getWugan_antihbs() {
		return wugan_antihbs;
	}
	public void setWugan_antihbs(String wuganAntihbs) {
		wugan_antihbs = wuganAntihbs;
	}
	public String getWugan_antihbc() {
		return wugan_antihbc;
	}
	public void setWugan_antihbc(String wuganAntihbc) {
		wugan_antihbc = wuganAntihbc;
	}
	public String getWugan_antihevlgm() {
		return wugan_antihevlgm;
	}
	public void setWugan_antihevlgm(String wuganAntihevlgm) {
		wugan_antihevlgm = wuganAntihevlgm;
	}
	public String getWugan_antihevlgg() {
		return wugan_antihevlgg;
	}
	public void setWugan_antihevlgg(String wuganAntihevlgg) {
		wugan_antihevlgg = wuganAntihevlgg;
	}
	public int getGenggan_mark() {
		return genggan_mark;
	}
	public void setGenggan_mark(int gengganMark) {
		genggan_mark = gengganMark;
	}
	public Date getGenggan_checkdate() {
		return genggan_checkdate;
	}
	public void setGenggan_checkdate(Date gengganCheckdate) {
		genggan_checkdate = gengganCheckdate;
	}
	public String getGenggan_antihiv() {
		return genggan_antihiv;
	}
	public void setGenggan_antihiv(String gengganAntihiv) {
		genggan_antihiv = gengganAntihiv;
	}
	public int getDmxq_mark() {
		return dmxq_mark;
	}
	public void setDmxq_mark(int dmxqMark) {
		dmxq_mark = dmxqMark;
	}
	public Date getDmxq_checkdate() {
		return dmxq_checkdate;
	}
	public void setDmxq_checkdate(Date dmxqCheckdate) {
		dmxq_checkdate = dmxqCheckdate;
	}
	public String getDmxq_po2() {
		return dmxq_po2;
	}
	public void setDmxq_po2(String dmxqPo2) {
		dmxq_po2 = dmxqPo2;
	}
	public String getDmxq_pco2() {
		return dmxq_pco2;
	}
	public void setDmxq_pco2(String dmxqPco2) {
		dmxq_pco2 = dmxqPco2;
	}
	public String getDmxq_ph() {
		return dmxq_ph;
	}
	public void setDmxq_ph(String dmxqPh) {
		dmxq_ph = dmxqPh;
	}
	public String getDmxq_sao() {
		return dmxq_sao;
	}
	public void setDmxq_sao(String dmxqSao) {
		dmxq_sao = dmxqSao;
	}
	public String getDmxq_other() {
		return dmxq_other;
	}
	public void setDmxq_other(String dmxqOther) {
		dmxq_other = dmxqOther;
	}
	public int getNx_mark() {
		return nx_mark;
	}
	public void setNx_mark(int nxMark) {
		nx_mark = nxMark;
	}
	public Date getNx_checkdate() {
		return nx_checkdate;
	}
	public void setNx_checkdate(Date nxCheckdate) {
		nx_checkdate = nxCheckdate;
	}
	public String getNx_inr() {
		return nx_inr;
	}
	public void setNx_inr(String nxInr) {
		nx_inr = nxInr;
	}
	public String getNx_fib() {
		return nx_fib;
	}
	public void setNx_fib(String nxFib) {
		nx_fib = nxFib;
	}
	public String getNx_aptt() {
		return nx_aptt;
	}
	public void setNx_aptt(String nxAptt) {
		nx_aptt = nxAptt;
	}
	public String getNx_pta() {
		return nx_pta;
	}
	public void setNx_pta(String nxPta) {
		nx_pta = nxPta;
	}
	public String getNx_pt() {
		return nx_pt;
	}
	public void setNx_pt(String nxPt) {
		nx_pt = nxPt;
	}
	public String getNx_other() {
		return nx_other;
	}
	public void setNx_other(String nxOther) {
		nx_other = nxOther;
	}
	public int getXuezhi_mark() {
		return xuezhi_mark;
	}
	public void setXuezhi_mark(int xuezhiMark) {
		xuezhi_mark = xuezhiMark;
	}
	public Date getXuezhi_checkdate() {
		return xuezhi_checkdate;
	}
	public void setXuezhi_checkdate(Date xuezhiCheckdate) {
		xuezhi_checkdate = xuezhiCheckdate;
	}
	public String getXuezhi_hdlc() {
		return xuezhi_hdlc;
	}
	public void setXuezhi_hdlc(String xuezhiHdlc) {
		xuezhi_hdlc = xuezhiHdlc;
	}
	public String getXuezhi_ldlc() {
		return xuezhi_ldlc;
	}
	public void setXuezhi_ldlc(String xuezhiLdlc) {
		xuezhi_ldlc = xuezhiLdlc;
	}
	public String getXuezhi_tg() {
		return xuezhi_tg;
	}
	public void setXuezhi_tg(String xuezhiTg) {
		xuezhi_tg = xuezhiTg;
	}
	public String getXuezhi_chol() {
		return xuezhi_chol;
	}
	public void setXuezhi_chol(String xuezhiChol) {
		xuezhi_chol = xuezhiChol;
	}
	public String getXuezhi_other() {
		return xuezhi_other;
	}
	public void setXuezhi_other(String xuezhiOther) {
		xuezhi_other = xuezhiOther;
	}
	public int getZhlbjw_mark() {
		return zhlbjw_mark;
	}
	public void setZhlbjw_mark(int zhlbjwMark) {
		zhlbjw_mark = zhlbjwMark;
	}
	public Date getZhlbjw_checkdate() {
		return zhlbjw_checkdate;
	}
	public void setZhlbjw_checkdate(Date zhlbjwCheckdate) {
		zhlbjw_checkdate = zhlbjwCheckdate;
	}
	public String getZhlbjw_afp() {
		return zhlbjw_afp;
	}
	public void setZhlbjw_afp(String zhlbjwAfp) {
		zhlbjw_afp = zhlbjwAfp;
	}
	public String getZhlbjw_cea() {
		return zhlbjw_cea;
	}
	public void setZhlbjw_cea(String zhlbjwCea) {
		zhlbjw_cea = zhlbjwCea;
	}
	public String getZhlbjw_ca199() {
		return zhlbjw_ca199;
	}
	public void setZhlbjw_ca199(String zhlbjwCa199) {
		zhlbjw_ca199 = zhlbjwCa199;
	}
	public String getZhlbjw_ca242() {
		return zhlbjw_ca242;
	}
	public void setZhlbjw_ca242(String zhlbjwCa242) {
		zhlbjw_ca242 = zhlbjwCa242;
	}
	public String getZhlbjw_other() {
		return zhlbjw_other;
	}
	public void setZhlbjw_other(String zhlbjwOther) {
		zhlbjw_other = zhlbjwOther;
	}
	public int getXynd_mark() {
		return xynd_mark;
	}
	public void setXynd_mark(int xyndMark) {
		xynd_mark = xyndMark;
	}
	public Date getXynd_checkdate() {
		return xynd_checkdate;
	}
	public void setXynd_checkdate(Date xyndCheckdate) {
		xynd_checkdate = xyndCheckdate;
	}
	public String getXynd_plkf() {
		return xynd_plkf;
	}
	public void setXynd_plkf(String xyndPlkf) {
		xynd_plkf = xyndPlkf;
	}
	public String getXynd_lpms() {
		return xynd_lpms;
	}
	public void setXynd_lpms(String xyndLpms) {
		xynd_lpms = xyndLpms;
	}
	public String getXynd_other() {
		return xynd_other;
	}
	public void setXynd_other(String xyndOther) {
		xynd_other = xyndOther;
	}
	public int getXline_mark() {
		return xline_mark;
	}
	public void setXline_mark(int xlineMark) {
		xline_mark = xlineMark;
	}
	public Date getXline_checkdate() {
		return xline_checkdate;
	}
	public void setXline_checkdate(Date xlineCheckdate) {
		xline_checkdate = xlineCheckdate;
	}
	public String getXline_checkResult() {
		return xline_checkResult;
	}
	public void setXline_checkResult(String xlineCheckResult) {
		xline_checkResult = xlineCheckResult;
	}
	public int getHeart_mark() {
		return heart_mark;
	}
	public void setHeart_mark(int heartMark) {
		heart_mark = heartMark;
	}
	public Date getHeart_checkdate() {
		return heart_checkdate;
	}
	public void setHeart_checkdate(Date heartCheckdate) {
		heart_checkdate = heartCheckdate;
	}
	public String getHeart_checkResult() {
		return heart_checkResult;
	}
	public void setHeart_checkResult(String heartCheckResult) {
		heart_checkResult = heartCheckResult;
	}
	public int getBc_mark() {
		return bc_mark;
	}
	public void setBc_mark(int bcMark) {
		bc_mark = bcMark;
	}
	public Date getBc_checkdate() {
		return bc_checkdate;
	}
	public void setBc_checkdate(Date bcCheckdate) {
		bc_checkdate = bcCheckdate;
	}
	public String getBc_checkResult() {
		return bc_checkResult;
	}
	public void setBc_checkResult(String bcCheckResult) {
		bc_checkResult = bcCheckResult;
	}
	public int getCt_mark() {
		return ct_mark;
	}
	public void setCt_mark(int ctMark) {
		ct_mark = ctMark;
	}
	public Date getCt_checkdate() {
		return ct_checkdate;
	}
	public void setCt_checkdate(Date ctCheckdate) {
		ct_checkdate = ctCheckdate;
	}
	public String getCt_checkResult() {
		return ct_checkResult;
	}
	public void setCt_checkResult(String ctCheckResult) {
		ct_checkResult = ctCheckResult;
	}
	public int getMri_mark() {
		return mri_mark;
	}
	public void setMri_mark(int mriMark) {
		mri_mark = mriMark;
	}
	public Date getMri_checkdate() {
		return mri_checkdate;
	}
	public void setMri_checkdate(Date mriCheckdate) {
		mri_checkdate = mriCheckdate;
	}
	public String getMri_checkResult() {
		return mri_checkResult;
	}
	public void setMri_checkResult(String mriCheckResult) {
		mri_checkResult = mriCheckResult;
	}
	public int getXhd_mark() {
		return xhd_mark;
	}
	public void setXhd_mark(int xhdMark) {
		xhd_mark = xhdMark;
	}
	public Date getXhd_checkdate() {
		return xhd_checkdate;
	}
	public void setXhd_checkdate(Date xhdCheckdate) {
		xhd_checkdate = xhdCheckdate;
	}
	public String getXhd_checkResult() {
		return xhd_checkResult;
	}
	public void setXhd_checkResult(String xhdCheckResult) {
		xhd_checkResult = xhdCheckResult;
	}
	public int getWeijing_mark() {
		return weijing_mark;
	}
	public void setWeijing_mark(int weijingMark) {
		weijing_mark = weijingMark;
	}
	public Date getWeijing_checkdate() {
		return weijing_checkdate;
	}
	public void setWeijing_checkdate(Date weijingCheckdate) {
		weijing_checkdate = weijingCheckdate;
	}
	public String getWeijing_checkResult() {
		return weijing_checkResult;
	}
	public void setWeijing_checkResult(String weijingCheckResult) {
		weijing_checkResult = weijingCheckResult;
	}
	public int getJiechang_mark() {
		return jiechang_mark;
	}
	public void setJiechang_mark(int jiechangMark) {
		jiechang_mark = jiechangMark;
	}
	public Date getJiechang_checkdate() {
		return jiechang_checkdate;
	}
	public void setJiechang_checkdate(Date jiechangCheckdate) {
		jiechang_checkdate = jiechangCheckdate;
	}
	public String getJiechang_checkResult() {
		return jiechang_checkResult;
	}
	public void setJiechang_checkResult(String jiechangCheckResult) {
		jiechang_checkResult = jiechangCheckResult;
	}
	public int getZhichang_mark() {
		return zhichang_mark;
	}
	public void setZhichang_mark(int zhichangMark) {
		zhichang_mark = zhichangMark;
	}
	public Date getZhichang_checkdate() {
		return zhichang_checkdate;
	}
	public void setZhichang_checkdate(Date zhichangCheckdate) {
		zhichang_checkdate = zhichangCheckdate;
	}
	public String getZhichang_checkResult() {
		return zhichang_checkResult;
	}
	public void setZhichang_checkResult(String zhichangCheckResult) {
		zhichang_checkResult = zhichangCheckResult;
	}
	public int getJiaon_mark() {
		return jiaon_mark;
	}
	public void setJiaon_mark(int jiaonMark) {
		jiaon_mark = jiaonMark;
	}
	public Date getJiaon_checkdate() {
		return jiaon_checkdate;
	}
	public void setJiaon_checkdate(Date jiaonCheckdate) {
		jiaon_checkdate = jiaonCheckdate;
	}
	public String getJiaon_checkResult() {
		return jiaon_checkResult;
	}
	public void setJiaon_checkResult(String jiaonCheckResult) {
		jiaon_checkResult = jiaonCheckResult;
	}
	public int getNiaosu_mark() {
		return niaosu_mark;
	}
	public void setNiaosu_mark(int niaosuMark) {
		niaosu_mark = niaosuMark;
	}
	public Date getNiaosu_checkdate() {
		return niaosu_checkdate;
	}
	public void setNiaosu_checkdate(Date niaosuCheckdate) {
		niaosu_checkdate = niaosuCheckdate;
	}
	public String getNiaosu_checkResult() {
		return niaosu_checkResult;
	}
	public void setNiaosu_checkResult(String niaosuCheckResult) {
		niaosu_checkResult = niaosuCheckResult;
	}
	public int getBingli_mark() {
		return bingli_mark;
	}
	public void setBingli_mark(int bingliMark) {
		bingli_mark = bingliMark;
	}
	public String getBingli_checkResult() {
		return bingli_checkResult;
	}
	public void setBingli_checkResult(String bingliCheckResult) {
		bingli_checkResult = bingliCheckResult;
	}
	public String getBingli_fenji() {
		return bingli_fenji;
	}
	public void setBingli_fenji(String bingliFenji) {
		bingli_fenji = bingliFenji;
	}
}
