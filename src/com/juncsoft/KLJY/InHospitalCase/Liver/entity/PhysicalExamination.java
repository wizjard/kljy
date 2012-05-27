package com.juncsoft.KLJY.InHospitalCase.Liver.entity;

public class PhysicalExamination {
	private Long id;
	private Long caseId;
	// 生命体征
	private String smtz_tiwen;
	private String smtz_xueya;
	private String smtz_maibo;
	private String smtz_huxi;
	// 一般状况
	private String ybzc_fayu;
	private String ybzc_shenzhi;
	private String ybzc_mianrong;
	private String ybzc_butai;
	private String ybzc_yingyang;
	private String ybzc_biaoqing;
	private String ybzc_tiwei;
	private String ybzc_chati;
	private String ybzc_fayu0;
	private String ybzc_shenzhi0;
	private String ybzc_mianrong0;
	private String ybzc_butai0;
	private String ybzc_yingyang0;
	private String ybzc_biaoqing0;
	private String ybzc_tiwei0;
	private String ybzc_chati0;
	// 皮肤粘膜
	private String pfnm_seze;
	private String pfnm_tanxing;
	private String pfnm_wenshi;
	private String pfnm_maofa;
	private String pfnm_seze0;
	private String pfnm_tanxing0;
	private String pfnm_wenshi0;
	private String pfnm_maofa0;
	private int pfnm_ganzhang;
	private int pfnm_maoxi;
	private int pfnm_pizhen;
	private String pfnm_pizhenDesc;
	private int pfnm_pixia;
	private String pfnm_pixiaDesc;
	private int pfnm_banhen;
	private String pfnm_banhenDesc;
	private int pfnm_shuizhong;
	private String pfnm_shuizhongDesc;
	private int pfnm_qita;
	private String pfnm_qitaDesc;
	/*======09-12新增 蜘蛛痣=========*/
	private Integer pfnm_zhizhu;
	// 淋巴结与淋巴管
	private int linbajie_zhongda;
	private String linbajie_zhongdaDesc;
	private int linbajie_jieyan;
	private String linbajie_jieyantd;
	private int linbajie_jieyanbs;
	// 头部-头颅
	private String head_daxiao;
	private String head_jixing;
	private String head_daxiao0;
	private String head_jixing0;
	private int head_other;
	private String head_otherDesc;
	// 头部-眼睛
	private String eyes_yanjian;
	private String eyes_yanjian0;
	public String getEyes_yanjian0() {
		return eyes_yanjian0;
	}

	public void setEyes_yanjian0(String eyes_yanjian0) {
		this.eyes_yanjian0 = eyes_yanjian0;
	}
	
	private String eyes_jiemo;
	private int eyes_jiaomo;
	private String eyes_jiaomoDesc;
	private String eyes_gongmo;
	private String eyes_yanqiu;
	private String eyes_yanqiu0;
	private int eyes_tongkong;
	private String eyes_tongkongDesc;
	private String eyes_zhijie;
	private String eyes_zhijieDesc;
	private String eyes_jianjie;
	private String eyes_jianjieDesc;
	// 头部-耳
	private String ear_erkuo;
	private String ear_erkuoDesc;
	private int ear_waier;
	private String ear_waierPosi;
	private String ear_waierxingzhi;
	private String ear_waierxingzhi0;
	private String ear_rutu;
	private String ear_tingli;
	// 头部-鼻
	private String nose_waiguan;
	private String nose_waiguanDesc;
	private String nose_zhongge;
	private int nose_bidou;
	private String nose_bidouPosi;
	private int nose_other;
	private String nose_otherDesc;
	// 头部-口-腮腺
	private String mouth_kouchun;
	private String mouth_nianmo;
	private String mouth_shenshe;
	private String mouth_qiwei;
	private String mouth_kouchun0;
	private String mouth_nianmo0;
	private String mouth_shenshe0;
	private String mouth_qiwei0;
	private String mouth_yayin;
	private String mouth_chilie;
	private String mouth_yanbu;
	private String mouth_biantaoti;
	private String mouth_shenyin;
	private String mouth_yayin0;
	private String mouth_chilie0;
	private String mouth_yanbu0;
	private String mouth_biantaoti0;
	private String mouth_shenyin0;
	private int saix_zhongda;
	private String saix_zhongdaDesc;
	// 颈部
	private int neck_dichu;
	private String neck_qiguan;
	private int neck_jmhuiliu;
	private String neck_jingmai;
	private String neck_dongmai;
	private int neck_jiazhx;
	private String neck_jiazhxDesc;
	// 胸部
	private String xiong_kuo;
	private int xiong_rufangDc;
	private int xiong_fufang;
	private String xiong_rufangDesc;
	private int xiong_yatong;
	// 肺部
	private String fei_huxi;
	private String fei_yuchan;
	private String fei_zuokou;
	private String fei_youkou;
	private String fei_zuoxiajie;
	private String fei_youxiajie;
	private int fei_huxiyin;
	private String fei_huxiyinDesc;
	private int fei_xiongmo;
	private String fei_xiongmoDesc;
	private int fei_luoyin;
	private String fei_luoyinxzhi;
	private String fei_luoyinPosi;
	// 心脏
	private int xinz_penglong;
	private int xinz_bodongPosi;
	private String xinz_xinjie;
	private String xinz_xinrate;
	private int xinz_xinbao;
	private String xinz_bodong;
	private String xinz_bodongCM;
	private String xinz_xinlv;
	private String xinz_xinyin;
	private String xinz_zayin;
	// 周围血管征
	private int zhouweixg_zheng;
	private String zhouweixg_zhengDesc;
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
	private String fubu_shenkt0;
	private String fubu_ganshangjie;
	private String fubu_yidong;
	private String fubu_fushui;
	// 腹部-听诊
	private String fubu_changming;
	private int fubu_qishui;
	private int fubu_xueguan;
	private String fubu_xueguanDesc;
	// 生殖器/肛门/直肠
	private int shengzhiqi;
	private String shengzhiqiDesc;
	// 脊柱-四肢
	private String jisi_waixing;
	private int jisi_yatong;
	private int jisi_kouji;
	private String jisi_xiazhi;
	private String jisi_xiazhi0;
	private String jisi_huodong;
	private int jisi_other;
	private String jisi_otherDesc;
	// 神经系统
	private String shenjing_fubi;
	private String shenjing_jiaomo;
	private int shenjing_xijian;
	private String shenjing_xijianPosi;
	private String shenjing_xijianXZ;
	private int shenjing_genjian;
	private String shenjing_genjianPosi;
	private String shenjing_genjianXZ;
	private String shenjing_babinski;
	private String shenjing_kerning;
	private String shenjing_brudzinski;
	private String shenjing_puyi;
	private String shenjing_huai;
	private int shenjing_jili;
	private String shenjing_jilitl;
	private String shenjing_jilitr;
	private String shenjing_jilibl;
	private String shenjing_jilibr;
	private int shenjing_jizhang;
	private String shenjing_jizhangtl;
	private String shenjing_jizhangtr;
	private String shenjing_jizhangbl;
	private String shenjing_jizhangbr;
	//================ add ==================
	private String smtz_xueya2;
	
	private String eyes_other;
	private String eyes_otherDesc;
	
	private String ear_other;
	private String ear_otherDesc;
	
	private String mouth_other;
	private String mouth_otherDesc;
	
	private String neck_other;
	private String neck_otherDesc;
	
	private String xiong_other;
	private String xiong_otherDesc;
	
	private String fei_other;
	private String fei_otherDesc;
	
	private String xinz_other;
	private String xinz_otherDesc;
	
	private String fubu_chu_other;
	private String fubu_chu_otherDesc;
	private String fubu_k_other;
	private String fubu_k_otherDesc;
	private String fubu_tz_other;
	private String fubu_tz_otherDesc;
	
	private String shenjing_other;
	private String shenjing_otherDesc;
	
	private String linbajie_fangwei;
	private String linbajie_buwei;
	private String linbajie_shumu;
	private String linbajie_zhijing;
	private String linbajie_zhidi;
	private String linbajie_yidong;
	private String linbajie_chutong;
	private String linbajie_guan;
	private String linbajie_fenmiwu;
	private String linbajie_banhen;
	
	private String neck_chengdu;
	private String neck_weizhi;
	private String neck_xingzhi;
	private String neck_zhidi;
	private String neck_jiaother;
	
	private String baokuai_weizhi;
	private String baokuai_shumu;
	private String baokuai_zhijing;
	private String baokuai_zhidi;
	private String baokuai_yidong;
	private String baokuai_chutong;
	
	private String ganzang_leixia;
	private String ganzang_jiantuxia;
	private String ganzang_zhidi;
	private String ganzang_yatong;
	private String ganzang_biaomain;
	private String ganzang_bianyuan;
	
	private String dannang_yadong;
	
	private String pi_leixia;
	private String pi_jiantuxia;
	private String pi_zhidi;
	private String pi_yatong;
	private String pi_biaomain;
	private String pi_bianyuan;

	private String shen_weizhi;
	private String shen_yatong;
	private String shen_shuniaoguan;
///////////////////////////////////////////////////////////////////////////	
	private String eyes_jiemo0;
	private String eyes_gongmo0;
	private String ear_rutu0;
	private String ear_tingli0;
	private String neck_jingmai0;
	private String neck_qiguan0;
	private String neck_dongmai0;
	private String xiong_kuo0;
	private String fei_huxi0;
	private String fei_yuchan0;
	private String fei_zuokou0;
	private String fei_youkou0;
	private String xinz_bodong0;
	private String xinz_xinrate0;
	private String xinz_xinyin0;
	private String xinz_zayin0;
	private String jisi_waixing0;
	private String shenjing_fubi0;
	private String shenjing_jiaomo0;
	private String shenjing_babinski0;
	private String shenjing_kerning0;
	private String shenjing_puyi0;
	private String shenjing_huai0;
	private String shenjing_brudzinski0;
	private String xinz_xinjie0;
/////////////////////////////////////////////////////////////////////////////////////	
	
	public String getXinz_xinjie0() {
		return xinz_xinjie0;
	}

	public void setXinz_xinjie0(String xinz_xinjie0) {
		this.xinz_xinjie0 = xinz_xinjie0;
	}

	public String getEyes_jiemo0() {
		return eyes_jiemo0;
	}

	public String getShenjing_brudzinski0() {
		return shenjing_brudzinski0;
	}

	public void setShenjing_brudzinski0(String shenjing_brudzinski0) {
		this.shenjing_brudzinski0 = shenjing_brudzinski0;
	}

	public void setEyes_jiemo0(String eyes_jiemo0) {
		this.eyes_jiemo0 = eyes_jiemo0;
	}

	public String getEyes_gongmo0() {
		return eyes_gongmo0;
	}

	public void setEyes_gongmo0(String eyes_gongmo0) {
		this.eyes_gongmo0 = eyes_gongmo0;
	}

	public String getEar_rutu0() {
		return ear_rutu0;
	}

	public void setEar_rutu0(String ear_rutu0) {
		this.ear_rutu0 = ear_rutu0;
	}

	public String getEar_tingli0() {
		return ear_tingli0;
	}

	public void setEar_tingli0(String ear_tingli0) {
		this.ear_tingli0 = ear_tingli0;
	}

	public String getNeck_jingmai0() {
		return neck_jingmai0;
	}

	public void setNeck_jingmai0(String neck_jingmai0) {
		this.neck_jingmai0 = neck_jingmai0;
	}

	public String getNeck_qiguan0() {
		return neck_qiguan0;
	}

	public void setNeck_qiguan0(String neck_qiguan0) {
		this.neck_qiguan0 = neck_qiguan0;
	}

	public String getNeck_dongmai0() {
		return neck_dongmai0;
	}

	public void setNeck_dongmai0(String neck_dongmai0) {
		this.neck_dongmai0 = neck_dongmai0;
	}

	public String getXiong_kuo0() {
		return xiong_kuo0;
	}

	public void setXiong_kuo0(String xiong_kuo0) {
		this.xiong_kuo0 = xiong_kuo0;
	}

	public String getFei_huxi0() {
		return fei_huxi0;
	}

	public void setFei_huxi0(String fei_huxi0) {
		this.fei_huxi0 = fei_huxi0;
	}

	public String getFei_yuchan0() {
		return fei_yuchan0;
	}

	public void setFei_yuchan0(String fei_yuchan0) {
		this.fei_yuchan0 = fei_yuchan0;
	}

	public String getFei_zuokou0() {
		return fei_zuokou0;
	}

	public void setFei_zuokou0(String fei_zuokou0) {
		this.fei_zuokou0 = fei_zuokou0;
	}

	public String getFei_youkou0() {
		return fei_youkou0;
	}

	public void setFei_youkou0(String fei_youkou0) {
		this.fei_youkou0 = fei_youkou0;
	}

	public String getXinz_bodong0() {
		return xinz_bodong0;
	}

	public void setXinz_bodong0(String xinz_bodong0) {
		this.xinz_bodong0 = xinz_bodong0;
	}

	public String getXinz_xinrate0() {
		return xinz_xinrate0;
	}

	public void setXinz_xinrate0(String xinz_xinrate0) {
		this.xinz_xinrate0 = xinz_xinrate0;
	}

	public String getXinz_xinyin0() {
		return xinz_xinyin0;
	}

	public void setXinz_xinyin0(String xinz_xinyin0) {
		this.xinz_xinyin0 = xinz_xinyin0;
	}

	public String getXinz_zayin0() {
		return xinz_zayin0;
	}

	public void setXinz_zayin0(String xinz_zayin0) {
		this.xinz_zayin0 = xinz_zayin0;
	}

	public String getJisi_waixing0() {
		return jisi_waixing0;
	}

	public void setJisi_waixing0(String jisi_waixing0) {
		this.jisi_waixing0 = jisi_waixing0;
	}

	public String getShenjing_fubi0() {
		return shenjing_fubi0;
	}

	public void setShenjing_fubi0(String shenjing_fubi0) {
		this.shenjing_fubi0 = shenjing_fubi0;
	}

	public String getShenjing_jiaomo0() {
		return shenjing_jiaomo0;
	}

	public void setShenjing_jiaomo0(String shenjing_jiaomo0) {
		this.shenjing_jiaomo0 = shenjing_jiaomo0;
	}

	public String getShenjing_babinski0() {
		return shenjing_babinski0;
	}

	public void setShenjing_babinski0(String shenjing_babinski0) {
		this.shenjing_babinski0 = shenjing_babinski0;
	}

	public String getShenjing_kerning0() {
		return shenjing_kerning0;
	}

	public void setShenjing_kerning0(String shenjing_kerning0) {
		this.shenjing_kerning0 = shenjing_kerning0;
	}

	public String getShenjing_puyi0() {
		return shenjing_puyi0;
	}

	public void setShenjing_puyi0(String shenjing_puyi0) {
		this.shenjing_puyi0 = shenjing_puyi0;
	}

	public String getShenjing_huai0() {
		return shenjing_huai0;
	}

	public void setShenjing_huai0(String shenjing_huai0) {
		this.shenjing_huai0 = shenjing_huai0;
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

	public String getSmtz_tiwen() {
		return smtz_tiwen;
	}

	public void setSmtz_tiwen(String smtzTiwen) {
		smtz_tiwen = smtzTiwen;
	}

	public String getSmtz_xueya() {
		return smtz_xueya;
	}

	public void setSmtz_xueya(String smtzXueya) {
		smtz_xueya = smtzXueya;
	}

	public String getSmtz_maibo() {
		return smtz_maibo;
	}

	public void setSmtz_maibo(String smtzMaibo) {
		smtz_maibo = smtzMaibo;
	}

	public String getSmtz_huxi() {
		return smtz_huxi;
	}

	public void setSmtz_huxi(String smtzHuxi) {
		smtz_huxi = smtzHuxi;
	}

	public String getYbzc_fayu() {
		return ybzc_fayu;
	}

	public void setYbzc_fayu(String ybzcFayu) {
		ybzc_fayu = ybzcFayu;
	}

	public String getYbzc_shenzhi() {
		return ybzc_shenzhi;
	}

	public void setYbzc_shenzhi(String ybzcShenzhi) {
		ybzc_shenzhi = ybzcShenzhi;
	}

	public String getYbzc_mianrong() {
		return ybzc_mianrong;
	}

	public void setYbzc_mianrong(String ybzcMianrong) {
		ybzc_mianrong = ybzcMianrong;
	}

	public String getYbzc_butai() {
		return ybzc_butai;
	}

	public void setYbzc_butai(String ybzcButai) {
		ybzc_butai = ybzcButai;
	}

	public String getYbzc_yingyang() {
		return ybzc_yingyang;
	}

	public void setYbzc_yingyang(String ybzcYingyang) {
		ybzc_yingyang = ybzcYingyang;
	}

	public String getYbzc_biaoqing() {
		return ybzc_biaoqing;
	}

	public void setYbzc_biaoqing(String ybzcBiaoqing) {
		ybzc_biaoqing = ybzcBiaoqing;
	}

	public String getYbzc_tiwei() {
		return ybzc_tiwei;
	}

	public void setYbzc_tiwei(String ybzcTiwei) {
		ybzc_tiwei = ybzcTiwei;
	}

	public String getYbzc_chati() {
		return ybzc_chati;
	}

	public void setYbzc_chati(String ybzcChati) {
		ybzc_chati = ybzcChati;
	}

	public String getYbzc_fayu0() {
		return ybzc_fayu0;
	}

	public void setYbzc_fayu0(String ybzcFayu0) {
		ybzc_fayu0 = ybzcFayu0;
	}

	public String getYbzc_shenzhi0() {
		return ybzc_shenzhi0;
	}

	public void setYbzc_shenzhi0(String ybzcShenzhi0) {
		ybzc_shenzhi0 = ybzcShenzhi0;
	}

	public String getYbzc_mianrong0() {
		return ybzc_mianrong0;
	}

	public void setYbzc_mianrong0(String ybzcMianrong0) {
		ybzc_mianrong0 = ybzcMianrong0;
	}

	public String getYbzc_butai0() {
		return ybzc_butai0;
	}

	public void setYbzc_butai0(String ybzcButai0) {
		ybzc_butai0 = ybzcButai0;
	}

	public String getYbzc_yingyang0() {
		return ybzc_yingyang0;
	}

	public void setYbzc_yingyang0(String ybzcYingyang0) {
		ybzc_yingyang0 = ybzcYingyang0;
	}

	public String getYbzc_biaoqing0() {
		return ybzc_biaoqing0;
	}

	public void setYbzc_biaoqing0(String ybzcBiaoqing0) {
		ybzc_biaoqing0 = ybzcBiaoqing0;
	}

	public String getYbzc_tiwei0() {
		return ybzc_tiwei0;
	}

	public void setYbzc_tiwei0(String ybzcTiwei0) {
		ybzc_tiwei0 = ybzcTiwei0;
	}

	public String getYbzc_chati0() {
		return ybzc_chati0;
	}

	public void setYbzc_chati0(String ybzcChati0) {
		ybzc_chati0 = ybzcChati0;
	}

	public String getPfnm_seze() {
		return pfnm_seze;
	}

	public void setPfnm_seze(String pfnmSeze) {
		pfnm_seze = pfnmSeze;
	}

	public String getPfnm_tanxing() {
		return pfnm_tanxing;
	}

	public void setPfnm_tanxing(String pfnmTanxing) {
		pfnm_tanxing = pfnmTanxing;
	}

	public String getPfnm_wenshi() {
		return pfnm_wenshi;
	}

	public void setPfnm_wenshi(String pfnmWenshi) {
		pfnm_wenshi = pfnmWenshi;
	}

	public String getPfnm_maofa() {
		return pfnm_maofa;
	}

	public void setPfnm_maofa(String pfnmMaofa) {
		pfnm_maofa = pfnmMaofa;
	}

	public String getPfnm_seze0() {
		return pfnm_seze0;
	}

	public void setPfnm_seze0(String pfnmSeze0) {
		pfnm_seze0 = pfnmSeze0;
	}

	public String getPfnm_tanxing0() {
		return pfnm_tanxing0;
	}

	public void setPfnm_tanxing0(String pfnmTanxing0) {
		pfnm_tanxing0 = pfnmTanxing0;
	}

	public String getPfnm_wenshi0() {
		return pfnm_wenshi0;
	}

	public void setPfnm_wenshi0(String pfnmWenshi0) {
		pfnm_wenshi0 = pfnmWenshi0;
	}

	public String getPfnm_maofa0() {
		return pfnm_maofa0;
	}

	public void setPfnm_maofa0(String pfnmMaofa0) {
		pfnm_maofa0 = pfnmMaofa0;
	}

	public int getPfnm_ganzhang() {
		return pfnm_ganzhang;
	}

	public void setPfnm_ganzhang(int pfnmGanzhang) {
		pfnm_ganzhang = pfnmGanzhang;
	}

	public int getPfnm_maoxi() {
		return pfnm_maoxi;
	}

	public void setPfnm_maoxi(int pfnmMaoxi) {
		pfnm_maoxi = pfnmMaoxi;
	}

	public int getPfnm_pizhen() {
		return pfnm_pizhen;
	}

	public void setPfnm_pizhen(int pfnmPizhen) {
		pfnm_pizhen = pfnmPizhen;
	}

	public String getPfnm_pizhenDesc() {
		return pfnm_pizhenDesc;
	}

	public void setPfnm_pizhenDesc(String pfnmPizhenDesc) {
		pfnm_pizhenDesc = pfnmPizhenDesc;
	}

	public int getPfnm_pixia() {
		return pfnm_pixia;
	}

	public void setPfnm_pixia(int pfnmPixia) {
		pfnm_pixia = pfnmPixia;
	}

	public String getPfnm_pixiaDesc() {
		return pfnm_pixiaDesc;
	}

	public void setPfnm_pixiaDesc(String pfnmPixiaDesc) {
		pfnm_pixiaDesc = pfnmPixiaDesc;
	}

	public int getPfnm_banhen() {
		return pfnm_banhen;
	}

	public void setPfnm_banhen(int pfnmBanhen) {
		pfnm_banhen = pfnmBanhen;
	}

	public String getPfnm_banhenDesc() {
		return pfnm_banhenDesc;
	}

	public void setPfnm_banhenDesc(String pfnmBanhenDesc) {
		pfnm_banhenDesc = pfnmBanhenDesc;
	}

	public int getPfnm_shuizhong() {
		return pfnm_shuizhong;
	}

	public void setPfnm_shuizhong(int pfnmShuizhong) {
		pfnm_shuizhong = pfnmShuizhong;
	}

	public String getPfnm_shuizhongDesc() {
		return pfnm_shuizhongDesc;
	}

	public void setPfnm_shuizhongDesc(String pfnmShuizhongDesc) {
		pfnm_shuizhongDesc = pfnmShuizhongDesc;
	}

	public int getPfnm_qita() {
		return pfnm_qita;
	}

	public void setPfnm_qita(int pfnmQita) {
		pfnm_qita = pfnmQita;
	}

	public String getPfnm_qitaDesc() {
		return pfnm_qitaDesc;
	}

	public void setPfnm_qitaDesc(String pfnmQitaDesc) {
		pfnm_qitaDesc = pfnmQitaDesc;
	}

	public int getLinbajie_zhongda() {
		return linbajie_zhongda;
	}

	public void setLinbajie_zhongda(int linbajieZhongda) {
		linbajie_zhongda = linbajieZhongda;
	}

	public String getLinbajie_zhongdaDesc() {
		return linbajie_zhongdaDesc;
	}

	public void setLinbajie_zhongdaDesc(String linbajieZhongdaDesc) {
		linbajie_zhongdaDesc = linbajieZhongdaDesc;
	}

	public int getLinbajie_jieyan() {
		return linbajie_jieyan;
	}

	public void setLinbajie_jieyan(int linbajieJieyan) {
		linbajie_jieyan = linbajieJieyan;
	}

	public String getLinbajie_jieyantd() {
		return linbajie_jieyantd;
	}

	public void setLinbajie_jieyantd(String linbajieJieyantd) {
		linbajie_jieyantd = linbajieJieyantd;
	}

	public int getLinbajie_jieyanbs() {
		return linbajie_jieyanbs;
	}

	public void setLinbajie_jieyanbs(int linbajieJieyanbs) {
		linbajie_jieyanbs = linbajieJieyanbs;
	}

	public String getHead_daxiao() {
		return head_daxiao;
	}

	public void setHead_daxiao(String headDaxiao) {
		head_daxiao = headDaxiao;
	}

	public String getHead_jixing() {
		return head_jixing;
	}

	public void setHead_jixing(String headJixing) {
		head_jixing = headJixing;
	}

	public String getHead_daxiao0() {
		return head_daxiao0;
	}

	public void setHead_daxiao0(String headDaxiao0) {
		head_daxiao0 = headDaxiao0;
	}

	public String getHead_jixing0() {
		return head_jixing0;
	}

	public void setHead_jixing0(String headJixing0) {
		head_jixing0 = headJixing0;
	}

	public int getHead_other() {
		return head_other;
	}

	public void setHead_other(int headOther) {
		head_other = headOther;
	}

	public String getHead_otherDesc() {
		return head_otherDesc;
	}

	public void setHead_otherDesc(String headOtherDesc) {
		head_otherDesc = headOtherDesc;
	}

	public String getEyes_yanjian() {
		return eyes_yanjian;
	}

	public void setEyes_yanjian(String eyesYanjian) {
		eyes_yanjian = eyesYanjian;
	}

	public String getEyes_jiemo() {
		return eyes_jiemo;
	}

	public void setEyes_jiemo(String eyesJiemo) {
		eyes_jiemo = eyesJiemo;
	}

	public int getEyes_jiaomo() {
		return eyes_jiaomo;
	}

	public void setEyes_jiaomo(int eyesJiaomo) {
		eyes_jiaomo = eyesJiaomo;
	}

	public String getEyes_jiaomoDesc() {
		return eyes_jiaomoDesc;
	}

	public void setEyes_jiaomoDesc(String eyesJiaomoDesc) {
		eyes_jiaomoDesc = eyesJiaomoDesc;
	}

	public String getEyes_gongmo() {
		return eyes_gongmo;
	}

	public void setEyes_gongmo(String eyesGongmo) {
		eyes_gongmo = eyesGongmo;
	}

	public String getEyes_yanqiu() {
		return eyes_yanqiu;
	}

	public void setEyes_yanqiu(String eyesYanqiu) {
		eyes_yanqiu = eyesYanqiu;
	}

	public int getEyes_tongkong() {
		return eyes_tongkong;
	}

	public void setEyes_tongkong(int eyesTongkong) {
		eyes_tongkong = eyesTongkong;
	}

	public String getEyes_tongkongDesc() {
		return eyes_tongkongDesc;
	}

	public void setEyes_tongkongDesc(String eyesTongkongDesc) {
		eyes_tongkongDesc = eyesTongkongDesc;
	}

	public String getEyes_zhijie() {
		return eyes_zhijie;
	}

	public void setEyes_zhijie(String eyesZhijie) {
		eyes_zhijie = eyesZhijie;
	}

	public String getEyes_zhijieDesc() {
		return eyes_zhijieDesc;
	}

	public void setEyes_zhijieDesc(String eyesZhijieDesc) {
		eyes_zhijieDesc = eyesZhijieDesc;
	}

	public String getEyes_jianjie() {
		return eyes_jianjie;
	}

	public void setEyes_jianjie(String eyesJianjie) {
		eyes_jianjie = eyesJianjie;
	}

	public String getEyes_jianjieDesc() {
		return eyes_jianjieDesc;
	}

	public void setEyes_jianjieDesc(String eyesJianjieDesc) {
		eyes_jianjieDesc = eyesJianjieDesc;
	}

	public String getEar_erkuo() {
		return ear_erkuo;
	}

	public void setEar_erkuo(String earErkuo) {
		ear_erkuo = earErkuo;
	}

	public String getEar_erkuoDesc() {
		return ear_erkuoDesc;
	}

	public void setEar_erkuoDesc(String earErkuoDesc) {
		ear_erkuoDesc = earErkuoDesc;
	}

	public int getEar_waier() {
		return ear_waier;
	}

	public void setEar_waier(int earWaier) {
		ear_waier = earWaier;
	}

	public String getEar_waierPosi() {
		return ear_waierPosi;
	}

	public void setEar_waierPosi(String earWaierPosi) {
		ear_waierPosi = earWaierPosi;
	}

	public String getEar_waierxingzhi() {
		return ear_waierxingzhi;
	}

	public void setEar_waierxingzhi(String earWaierxingzhi) {
		ear_waierxingzhi = earWaierxingzhi;
	}

	public String getEar_rutu() {
		return ear_rutu;
	}

	public void setEar_rutu(String earRutu) {
		ear_rutu = earRutu;
	}

	public String getEar_tingli() {
		return ear_tingli;
	}

	public void setEar_tingli(String earTingli) {
		ear_tingli = earTingli;
	}

	public String getNose_waiguan() {
		return nose_waiguan;
	}

	public void setNose_waiguan(String noseWaiguan) {
		nose_waiguan = noseWaiguan;
	}

	public String getNose_waiguanDesc() {
		return nose_waiguanDesc;
	}

	public void setNose_waiguanDesc(String noseWaiguanDesc) {
		nose_waiguanDesc = noseWaiguanDesc;
	}

	public String getNose_zhongge() {
		return nose_zhongge;
	}

	public void setNose_zhongge(String noseZhongge) {
		nose_zhongge = noseZhongge;
	}

	public int getNose_bidou() {
		return nose_bidou;
	}

	public void setNose_bidou(int noseBidou) {
		nose_bidou = noseBidou;
	}

	public String getNose_bidouPosi() {
		return nose_bidouPosi;
	}

	public void setNose_bidouPosi(String noseBidouPosi) {
		nose_bidouPosi = noseBidouPosi;
	}

	public int getNose_other() {
		return nose_other;
	}

	public void setNose_other(int noseOther) {
		nose_other = noseOther;
	}

	public String getNose_otherDesc() {
		return nose_otherDesc;
	}

	public void setNose_otherDesc(String noseOtherDesc) {
		nose_otherDesc = noseOtherDesc;
	}

	public String getMouth_kouchun() {
		return mouth_kouchun;
	}

	public void setMouth_kouchun(String mouthKouchun) {
		mouth_kouchun = mouthKouchun;
	}

	public String getMouth_nianmo() {
		return mouth_nianmo;
	}

	public void setMouth_nianmo(String mouthNianmo) {
		mouth_nianmo = mouthNianmo;
	}

	public String getMouth_shenshe() {
		return mouth_shenshe;
	}

	public void setMouth_shenshe(String mouthShenshe) {
		mouth_shenshe = mouthShenshe;
	}

	public String getMouth_qiwei() {
		return mouth_qiwei;
	}

	public void setMouth_qiwei(String mouthQiwei) {
		mouth_qiwei = mouthQiwei;
	}

	public String getMouth_kouchun0() {
		return mouth_kouchun0;
	}

	public void setMouth_kouchun0(String mouthKouchun0) {
		mouth_kouchun0 = mouthKouchun0;
	}

	public String getMouth_nianmo0() {
		return mouth_nianmo0;
	}

	public void setMouth_nianmo0(String mouthNianmo0) {
		mouth_nianmo0 = mouthNianmo0;
	}

	public String getMouth_shenshe0() {
		return mouth_shenshe0;
	}

	public void setMouth_shenshe0(String mouthShenshe0) {
		mouth_shenshe0 = mouthShenshe0;
	}

	public String getMouth_qiwei0() {
		return mouth_qiwei0;
	}

	public void setMouth_qiwei0(String mouthQiwei0) {
		mouth_qiwei0 = mouthQiwei0;
	}

	public String getMouth_yayin() {
		return mouth_yayin;
	}

	public void setMouth_yayin(String mouthYayin) {
		mouth_yayin = mouthYayin;
	}

	public String getMouth_chilie() {
		return mouth_chilie;
	}

	public void setMouth_chilie(String mouthChilie) {
		mouth_chilie = mouthChilie;
	}

	public String getMouth_yanbu() {
		return mouth_yanbu;
	}

	public void setMouth_yanbu(String mouthYanbu) {
		mouth_yanbu = mouthYanbu;
	}

	public String getMouth_biantaoti() {
		return mouth_biantaoti;
	}

	public void setMouth_biantaoti(String mouthBiantaoti) {
		mouth_biantaoti = mouthBiantaoti;
	}

	public String getMouth_shenyin() {
		return mouth_shenyin;
	}

	public void setMouth_shenyin(String mouthShenyin) {
		mouth_shenyin = mouthShenyin;
	}

	public String getMouth_yayin0() {
		return mouth_yayin0;
	}

	public void setMouth_yayin0(String mouthYayin0) {
		mouth_yayin0 = mouthYayin0;
	}

	public String getMouth_chilie0() {
		return mouth_chilie0;
	}

	public void setMouth_chilie0(String mouthChilie0) {
		mouth_chilie0 = mouthChilie0;
	}

	public String getMouth_yanbu0() {
		return mouth_yanbu0;
	}

	public void setMouth_yanbu0(String mouthYanbu0) {
		mouth_yanbu0 = mouthYanbu0;
	}

	public String getMouth_biantaoti0() {
		return mouth_biantaoti0;
	}

	public void setMouth_biantaoti0(String mouthBiantaoti0) {
		mouth_biantaoti0 = mouthBiantaoti0;
	}

	public String getMouth_shenyin0() {
		return mouth_shenyin0;
	}

	public void setMouth_shenyin0(String mouthShenyin0) {
		mouth_shenyin0 = mouthShenyin0;
	}

	public int getSaix_zhongda() {
		return saix_zhongda;
	}

	public void setSaix_zhongda(int saixZhongda) {
		saix_zhongda = saixZhongda;
	}

	public String getSaix_zhongdaDesc() {
		return saix_zhongdaDesc;
	}

	public void setSaix_zhongdaDesc(String saixZhongdaDesc) {
		saix_zhongdaDesc = saixZhongdaDesc;
	}

	public int getNeck_dichu() {
		return neck_dichu;
	}

	public void setNeck_dichu(int neckDichu) {
		neck_dichu = neckDichu;
	}

	public String getNeck_qiguan() {
		return neck_qiguan;
	}

	public void setNeck_qiguan(String neckQiguan) {
		neck_qiguan = neckQiguan;
	}

	public int getNeck_jmhuiliu() {
		return neck_jmhuiliu;
	}

	public void setNeck_jmhuiliu(int neckJmhuiliu) {
		neck_jmhuiliu = neckJmhuiliu;
	}

	public String getNeck_jingmai() {
		return neck_jingmai;
	}

	public void setNeck_jingmai(String neckJingmai) {
		neck_jingmai = neckJingmai;
	}

	public String getNeck_dongmai() {
		return neck_dongmai;
	}

	public void setNeck_dongmai(String neckDongmai) {
		neck_dongmai = neckDongmai;
	}

	public int getNeck_jiazhx() {
		return neck_jiazhx;
	}

	public void setNeck_jiazhx(int neckJiazhx) {
		neck_jiazhx = neckJiazhx;
	}

	public String getNeck_jiazhxDesc() {
		return neck_jiazhxDesc;
	}

	public void setNeck_jiazhxDesc(String neckJiazhxDesc) {
		neck_jiazhxDesc = neckJiazhxDesc;
	}

	public String getXiong_kuo() {
		return xiong_kuo;
	}

	public void setXiong_kuo(String xiongKuo) {
		xiong_kuo = xiongKuo;
	}

	public int getXiong_rufangDc() {
		return xiong_rufangDc;
	}

	public void setXiong_rufangDc(int xiongRufangDc) {
		xiong_rufangDc = xiongRufangDc;
	}

	public int getXiong_fufang() {
		return xiong_fufang;
	}

	public void setXiong_fufang(int xiongFufang) {
		xiong_fufang = xiongFufang;
	}

	public String getXiong_rufangDesc() {
		return xiong_rufangDesc;
	}

	public void setXiong_rufangDesc(String xiongRufangDesc) {
		xiong_rufangDesc = xiongRufangDesc;
	}

	public int getXiong_yatong() {
		return xiong_yatong;
	}

	public void setXiong_yatong(int xiongYatong) {
		xiong_yatong = xiongYatong;
	}

	public String getFei_huxi() {
		return fei_huxi;
	}

	public void setFei_huxi(String feiHuxi) {
		fei_huxi = feiHuxi;
	}

	public String getFei_yuchan() {
		return fei_yuchan;
	}

	public void setFei_yuchan(String feiYuchan) {
		fei_yuchan = feiYuchan;
	}

	public String getFei_zuokou() {
		return fei_zuokou;
	}

	public void setFei_zuokou(String feiZuokou) {
		fei_zuokou = feiZuokou;
	}

	public String getFei_youkou() {
		return fei_youkou;
	}

	public void setFei_youkou(String feiYoukou) {
		fei_youkou = feiYoukou;
	}

	public String getFei_zuoxiajie() {
		return fei_zuoxiajie;
	}

	public void setFei_zuoxiajie(String feiZuoxiajie) {
		fei_zuoxiajie = feiZuoxiajie;
	}

	public String getFei_youxiajie() {
		return fei_youxiajie;
	}

	public void setFei_youxiajie(String feiYouxiajie) {
		fei_youxiajie = feiYouxiajie;
	}

	public int getFei_huxiyin() {
		return fei_huxiyin;
	}

	public void setFei_huxiyin(int feiHuxiyin) {
		fei_huxiyin = feiHuxiyin;
	}

	public String getFei_huxiyinDesc() {
		return fei_huxiyinDesc;
	}

	public void setFei_huxiyinDesc(String feiHuxiyinDesc) {
		fei_huxiyinDesc = feiHuxiyinDesc;
	}

	public int getFei_xiongmo() {
		return fei_xiongmo;
	}

	public void setFei_xiongmo(int feiXiongmo) {
		fei_xiongmo = feiXiongmo;
	}

	public String getFei_xiongmoDesc() {
		return fei_xiongmoDesc;
	}

	public void setFei_xiongmoDesc(String feiXiongmoDesc) {
		fei_xiongmoDesc = feiXiongmoDesc;
	}

	public int getFei_luoyin() {
		return fei_luoyin;
	}

	public void setFei_luoyin(int feiLuoyin) {
		fei_luoyin = feiLuoyin;
	}

	public String getFei_luoyinxzhi() {
		return fei_luoyinxzhi;
	}

	public void setFei_luoyinxzhi(String feiLuoyinxzhi) {
		fei_luoyinxzhi = feiLuoyinxzhi;
	}

	public String getFei_luoyinPosi() {
		return fei_luoyinPosi;
	}

	public void setFei_luoyinPosi(String feiLuoyinPosi) {
		fei_luoyinPosi = feiLuoyinPosi;
	}

	public int getXinz_penglong() {
		return xinz_penglong;
	}

	public void setXinz_penglong(int xinzPenglong) {
		xinz_penglong = xinzPenglong;
	}

	public int getXinz_bodongPosi() {
		return xinz_bodongPosi;
	}

	public void setXinz_bodongPosi(int xinzBodongPosi) {
		xinz_bodongPosi = xinzBodongPosi;
	}

	public String getXinz_xinjie() {
		return xinz_xinjie;
	}

	public void setXinz_xinjie(String xinzXinjie) {
		xinz_xinjie = xinzXinjie;
	}

	public String getXinz_xinrate() {
		return xinz_xinrate;
	}

	public void setXinz_xinrate(String xinzXinrate) {
		xinz_xinrate = xinzXinrate;
	}

	public int getXinz_xinbao() {
		return xinz_xinbao;
	}

	public void setXinz_xinbao(int xinzXinbao) {
		xinz_xinbao = xinzXinbao;
	}

	public String getXinz_bodong() {
		return xinz_bodong;
	}

	public void setXinz_bodong(String xinzBodong) {
		xinz_bodong = xinzBodong;
	}

	public String getXinz_bodongCM() {
		return xinz_bodongCM;
	}

	public void setXinz_bodongCM(String xinzBodongCM) {
		xinz_bodongCM = xinzBodongCM;
	}

	public String getXinz_xinlv() {
		return xinz_xinlv;
	}

	public void setXinz_xinlv(String xinzXinlv) {
		xinz_xinlv = xinzXinlv;
	}

	public String getXinz_xinyin() {
		return xinz_xinyin;
	}

	public void setXinz_xinyin(String xinzXinyin) {
		xinz_xinyin = xinzXinyin;
	}

	public String getXinz_zayin() {
		return xinz_zayin;
	}

	public void setXinz_zayin(String xinzZayin) {
		xinz_zayin = xinzZayin;
	}

	public int getZhouweixg_zheng() {
		return zhouweixg_zheng;
	}

	public void setZhouweixg_zheng(int zhouweixgZheng) {
		zhouweixg_zheng = zhouweixgZheng;
	}

	public String getZhouweixg_zhengDesc() {
		return zhouweixg_zhengDesc;
	}

	public void setZhouweixg_zhengDesc(String zhouweixgZhengDesc) {
		zhouweixg_zhengDesc = zhouweixgZhengDesc;
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
	
	public String getFubu_shenkt0() {
		return fubu_shenkt0;
	}

	public void setFubu_shenkt0(String fubu_shenkt0) {
		this.fubu_shenkt0 = fubu_shenkt0;
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

	public int getShengzhiqi() {
		return shengzhiqi;
	}

	public void setShengzhiqi(int shengzhiqi) {
		this.shengzhiqi = shengzhiqi;
	}

	public String getShengzhiqiDesc() {
		return shengzhiqiDesc;
	}

	public void setShengzhiqiDesc(String shengzhiqiDesc) {
		this.shengzhiqiDesc = shengzhiqiDesc;
	}

	public String getJisi_waixing() {
		return jisi_waixing;
	}

	public void setJisi_waixing(String jisiWaixing) {
		jisi_waixing = jisiWaixing;
	}

	public int getJisi_yatong() {
		return jisi_yatong;
	}

	public void setJisi_yatong(int jisiYatong) {
		jisi_yatong = jisiYatong;
	}

	public int getJisi_kouji() {
		return jisi_kouji;
	}

	public void setJisi_kouji(int jisiKouji) {
		jisi_kouji = jisiKouji;
	}

	public String getJisi_xiazhi() {
		return jisi_xiazhi;
	}

	public void setJisi_xiazhi(String jisiXiazhi) {
		jisi_xiazhi = jisiXiazhi;
	}

	public String getJisi_xiazhi0() {
		return jisi_xiazhi0;
	}

	public void setJisi_xiazhi0(String jisiXiazhi0) {
		jisi_xiazhi0 = jisiXiazhi0;
	}

	public String getJisi_huodong() {
		return jisi_huodong;
	}

	public void setJisi_huodong(String jisiHuodong) {
		jisi_huodong = jisiHuodong;
	}

	public int getJisi_other() {
		return jisi_other;
	}

	public void setJisi_other(int jisiOther) {
		jisi_other = jisiOther;
	}

	public String getJisi_otherDesc() {
		return jisi_otherDesc;
	}

	public void setJisi_otherDesc(String jisiOtherDesc) {
		jisi_otherDesc = jisiOtherDesc;
	}

	public String getShenjing_fubi() {
		return shenjing_fubi;
	}

	public void setShenjing_fubi(String shenjingFubi) {
		shenjing_fubi = shenjingFubi;
	}

	public String getShenjing_jiaomo() {
		return shenjing_jiaomo;
	}

	public void setShenjing_jiaomo(String shenjingJiaomo) {
		shenjing_jiaomo = shenjingJiaomo;
	}

	public int getShenjing_xijian() {
		return shenjing_xijian;
	}

	public void setShenjing_xijian(int shenjingXijian) {
		shenjing_xijian = shenjingXijian;
	}

	public String getShenjing_xijianPosi() {
		return shenjing_xijianPosi;
	}

	public void setShenjing_xijianPosi(String shenjingXijianPosi) {
		shenjing_xijianPosi = shenjingXijianPosi;
	}

	public String getShenjing_xijianXZ() {
		return shenjing_xijianXZ;
	}

	public void setShenjing_xijianXZ(String shenjingXijianXZ) {
		shenjing_xijianXZ = shenjingXijianXZ;
	}

	public int getShenjing_genjian() {
		return shenjing_genjian;
	}

	public void setShenjing_genjian(int shenjingGenjian) {
		shenjing_genjian = shenjingGenjian;
	}

	public String getShenjing_genjianPosi() {
		return shenjing_genjianPosi;
	}

	public void setShenjing_genjianPosi(String shenjingGenjianPosi) {
		shenjing_genjianPosi = shenjingGenjianPosi;
	}

	public String getShenjing_genjianXZ() {
		return shenjing_genjianXZ;
	}

	public void setShenjing_genjianXZ(String shenjingGenjianXZ) {
		shenjing_genjianXZ = shenjingGenjianXZ;
	}

	public String getShenjing_babinski() {
		return shenjing_babinski;
	}

	public void setShenjing_babinski(String shenjingBabinski) {
		shenjing_babinski = shenjingBabinski;
	}

	public String getShenjing_kerning() {
		return shenjing_kerning;
	}

	public void setShenjing_kerning(String shenjingKerning) {
		shenjing_kerning = shenjingKerning;
	}

	public String getShenjing_brudzinski() {
		return shenjing_brudzinski;
	}

	public void setShenjing_brudzinski(String shenjingBrudzinski) {
		shenjing_brudzinski = shenjingBrudzinski;
	}

	public String getShenjing_puyi() {
		return shenjing_puyi;
	}

	public void setShenjing_puyi(String shenjingPuyi) {
		shenjing_puyi = shenjingPuyi;
	}

	public String getShenjing_huai() {
		return shenjing_huai;
	}

	public void setShenjing_huai(String shenjingHuai) {
		shenjing_huai = shenjingHuai;
	}

	public int getShenjing_jili() {
		return shenjing_jili;
	}

	public void setShenjing_jili(int shenjingJili) {
		shenjing_jili = shenjingJili;
	}

	public String getShenjing_jilitl() {
		return shenjing_jilitl;
	}

	public void setShenjing_jilitl(String shenjingJilitl) {
		shenjing_jilitl = shenjingJilitl;
	}

	public String getShenjing_jilitr() {
		return shenjing_jilitr;
	}

	public void setShenjing_jilitr(String shenjingJilitr) {
		shenjing_jilitr = shenjingJilitr;
	}

	public String getShenjing_jilibl() {
		return shenjing_jilibl;
	}
	public void setShenjing_jilibl(String shenjingJilibl) {
		shenjing_jilibl = shenjingJilibl;
	}
	public String getShenjing_jilibr() {
		return shenjing_jilibr;
	}
	public void setShenjing_jilibr(String shenjingJilibr) {
		shenjing_jilibr = shenjingJilibr;
	}
	public int getShenjing_jizhang() {
		return shenjing_jizhang;
	}
	public void setShenjing_jizhang(int shenjingJizhang) {
		shenjing_jizhang = shenjingJizhang;
	}
	public String getShenjing_jizhangtl() {
		return shenjing_jizhangtl;
	}
	public void setShenjing_jizhangtl(String shenjingJizhangtl) {
		shenjing_jizhangtl = shenjingJizhangtl;
	}
	public String getShenjing_jizhangtr() {
		return shenjing_jizhangtr;
	}
	public void setShenjing_jizhangtr(String shenjingJizhangtr) {
		shenjing_jizhangtr = shenjingJizhangtr;
	}
	public String getShenjing_jizhangbl() {
		return shenjing_jizhangbl;
	}
	public void setShenjing_jizhangbl(String shenjingJizhangbl) {
		shenjing_jizhangbl = shenjingJizhangbl;
	}
	public String getShenjing_jizhangbr() {
		return shenjing_jizhangbr;
	}
	public void setShenjing_jizhangbr(String shenjingJizhangbr) {
		shenjing_jizhangbr = shenjingJizhangbr;
	}
	public String getEar_waierxingzhi0() {
		return ear_waierxingzhi0;
	}
	public void setEar_waierxingzhi0(String ear_waierxingzhi0) {
		this.ear_waierxingzhi0 = ear_waierxingzhi0;
	}
	public String getEyes_otherDesc() {
		return eyes_otherDesc;
	}
	public void setEyes_otherDesc(String eyes_otherDesc) {
		this.eyes_otherDesc = eyes_otherDesc;
	}
	public String getEar_otherDesc() {
		return ear_otherDesc;
	}
	public void setEar_otherDesc(String ear_otherDesc) {
		this.ear_otherDesc = ear_otherDesc;
	}
	public String getMouth_otherDesc() {
		return mouth_otherDesc;
	}
	public void setMouth_otherDesc(String mouth_otherDesc) {
		this.mouth_otherDesc = mouth_otherDesc;
	}
	public String getNeck_otherDesc() {
		return neck_otherDesc;
	}
	public void setNeck_otherDesc(String neck_otherDesc) {
		this.neck_otherDesc = neck_otherDesc;
	}
	public String getXiong_otherDesc() {
		return xiong_otherDesc;
	}
	public void setXiong_otherDesc(String xiong_otherDesc) {
		this.xiong_otherDesc = xiong_otherDesc;
	}
	public String getFei_otherDesc() {
		return fei_otherDesc;
	}
	public void setFei_otherDesc(String fei_otherDesc) {
		this.fei_otherDesc = fei_otherDesc;
	}
	public String getXinz_otherDesc() {
		return xinz_otherDesc;
	}
	public void setXinz_otherDesc(String xinz_otherDesc) {
		this.xinz_otherDesc = xinz_otherDesc;
	}
	public String getShenjing_otherDesc() {
		return shenjing_otherDesc;
	}
	public void setShenjing_otherDesc(String shenjing_otherDesc) {
		this.shenjing_otherDesc = shenjing_otherDesc;
	}
	public String getFubu_chu_otherDesc() {
		return fubu_chu_otherDesc;
	}
	public void setFubu_chu_otherDesc(String fubu_chu_otherDesc) {
		this.fubu_chu_otherDesc = fubu_chu_otherDesc;
	}
	public String getFubu_k_otherDesc() {
		return fubu_k_otherDesc;
	}
	public void setFubu_k_otherDesc(String fubu_k_otherDesc) {
		this.fubu_k_otherDesc = fubu_k_otherDesc;
	}
	public String getFubu_tz_otherDesc() {
		return fubu_tz_otherDesc;
	}
	public void setFubu_tz_otherDesc(String fubu_tz_otherDesc) {
		this.fubu_tz_otherDesc = fubu_tz_otherDesc;
	}
	public String getSmtz_xueya2() {
		return smtz_xueya2;
	}
	public void setSmtz_xueya2(String smtz_xueya2) {
		this.smtz_xueya2 = smtz_xueya2;
	}

	public String getEyes_other() {
		return eyes_other;
	}

	public void setEyes_other(String eyes_other) {
		this.eyes_other = eyes_other;
	}

	public String getEar_other() {
		return ear_other;
	}

	public void setEar_other(String ear_other) {
		this.ear_other = ear_other;
	}

	public String getMouth_other() {
		return mouth_other;
	}

	public void setMouth_other(String mouth_other) {
		this.mouth_other = mouth_other;
	}

	public String getNeck_other() {
		return neck_other;
	}

	public void setNeck_other(String neck_other) {
		this.neck_other = neck_other;
	}

	public String getXiong_other() {
		return xiong_other;
	}

	public void setXiong_other(String xiong_other) {
		this.xiong_other = xiong_other;
	}

	public String getFei_other() {
		return fei_other;
	}
	public void setFei_other(String fei_other) {
		this.fei_other = fei_other;
	}
	public String getXinz_other() {
		return xinz_other;
	}
	public void setXinz_other(String xinz_other) {
		this.xinz_other = xinz_other;
	}
	public String getFubu_k_other() {
		return fubu_k_other;
	}
	public void setFubu_k_other(String fubu_k_other) {
		this.fubu_k_other = fubu_k_other;
	}
	public String getFubu_tz_other() {
		return fubu_tz_other;
	}
	public void setFubu_tz_other(String fubu_tz_other) {
		this.fubu_tz_other = fubu_tz_other;
	}
	public String getShenjing_other() {
		return shenjing_other;
	}
	public void setShenjing_other(String shenjing_other) {
		this.shenjing_other = shenjing_other;
	}
	public String getFubu_chu_other() {
		return fubu_chu_other;
	}

	public void setFubu_chu_other(String fubu_chu_other) {
		this.fubu_chu_other = fubu_chu_other;
	}

	public String getBaokuai_chutong() {
		return baokuai_chutong;
	}

	public void setBaokuai_chutong(String baokuai_chutong) {
		this.baokuai_chutong = baokuai_chutong;
	}

	public String getBaokuai_shumu() {
		return baokuai_shumu;
	}

	public void setBaokuai_shumu(String baokuai_shumu) {
		this.baokuai_shumu = baokuai_shumu;
	}

	public String getBaokuai_weizhi() {
		return baokuai_weizhi;
	}

	public void setBaokuai_weizhi(String baokuai_weizhi) {
		this.baokuai_weizhi = baokuai_weizhi;
	}

	public String getBaokuai_yidong() {
		return baokuai_yidong;
	}

	public void setBaokuai_yidong(String baokuai_yidong) {
		this.baokuai_yidong = baokuai_yidong;
	}

	public String getBaokuai_zhidi() {
		return baokuai_zhidi;
	}

	public void setBaokuai_zhidi(String baokuai_zhidi) {
		this.baokuai_zhidi = baokuai_zhidi;
	}

	public String getBaokuai_zhijing() {
		return baokuai_zhijing;
	}

	public void setBaokuai_zhijing(String baokuai_zhijing) {
		this.baokuai_zhijing = baokuai_zhijing;
	}

	public String getDannang_yadong() {
		return dannang_yadong;
	}

	public void setDannang_yadong(String dannang_yadong) {
		this.dannang_yadong = dannang_yadong;
	}

	public String getGanzang_bianyuan() {
		return ganzang_bianyuan;
	}

	public void setGanzang_bianyuan(String ganzang_bianyuan) {
		this.ganzang_bianyuan = ganzang_bianyuan;
	}

	public String getGanzang_biaomain() {
		return ganzang_biaomain;
	}

	public void setGanzang_biaomain(String ganzang_biaomain) {
		this.ganzang_biaomain = ganzang_biaomain;
	}

	public String getGanzang_jiantuxia() {
		return ganzang_jiantuxia;
	}

	public void setGanzang_jiantuxia(String ganzang_jiantuxia) {
		this.ganzang_jiantuxia = ganzang_jiantuxia;
	}

	public String getGanzang_leixia() {
		return ganzang_leixia;
	}

	public void setGanzang_leixia(String ganzang_leixia) {
		this.ganzang_leixia = ganzang_leixia;
	}

	public String getGanzang_yatong() {
		return ganzang_yatong;
	}

	public void setGanzang_yatong(String ganzang_yatong) {
		this.ganzang_yatong = ganzang_yatong;
	}

	public String getGanzang_zhidi() {
		return ganzang_zhidi;
	}

	public void setGanzang_zhidi(String ganzang_zhidi) {
		this.ganzang_zhidi = ganzang_zhidi;
	}

	public String getLinbajie_banhen() {
		return linbajie_banhen;
	}

	public void setLinbajie_banhen(String linbajie_banhen) {
		this.linbajie_banhen = linbajie_banhen;
	}

	public String getLinbajie_buwei() {
		return linbajie_buwei;
	}

	public void setLinbajie_buwei(String linbajie_buwei) {
		this.linbajie_buwei = linbajie_buwei;
	}

	public String getLinbajie_chutong() {
		return linbajie_chutong;
	}

	public void setLinbajie_chutong(String linbajie_chutong) {
		this.linbajie_chutong = linbajie_chutong;
	}

	public String getLinbajie_fangwei() {
		return linbajie_fangwei;
	}

	public void setLinbajie_fangwei(String linbajie_fangwei) {
		this.linbajie_fangwei = linbajie_fangwei;
	}

	public String getLinbajie_fenmiwu() {
		return linbajie_fenmiwu;
	}

	public void setLinbajie_fenmiwu(String linbajie_fenmiwu) {
		this.linbajie_fenmiwu = linbajie_fenmiwu;
	}

	public String getLinbajie_guan() {
		return linbajie_guan;
	}

	public void setLinbajie_guan(String linbajie_guan) {
		this.linbajie_guan = linbajie_guan;
	}

	public String getLinbajie_shumu() {
		return linbajie_shumu;
	}

	public void setLinbajie_shumu(String linbajie_shumu) {
		this.linbajie_shumu = linbajie_shumu;
	}

	public String getLinbajie_yidong() {
		return linbajie_yidong;
	}

	public void setLinbajie_yidong(String linbajie_yidong) {
		this.linbajie_yidong = linbajie_yidong;
	}

	public String getLinbajie_zhidi() {
		return linbajie_zhidi;
	}

	public void setLinbajie_zhidi(String linbajie_zhidi) {
		this.linbajie_zhidi = linbajie_zhidi;
	}

	public String getLinbajie_zhijing() {
		return linbajie_zhijing;
	}

	public void setLinbajie_zhijing(String linbajie_zhijing) {
		this.linbajie_zhijing = linbajie_zhijing;
	}

	public String getNeck_chengdu() {
		return neck_chengdu;
	}

	public void setNeck_chengdu(String neck_chengdu) {
		this.neck_chengdu = neck_chengdu;
	}

	public String getNeck_jiaother() {
		return neck_jiaother;
	}

	public void setNeck_jiaother(String neck_jiaother) {
		this.neck_jiaother = neck_jiaother;
	}

	public String getNeck_weizhi() {
		return neck_weizhi;
	}

	public void setNeck_weizhi(String neck_weizhi) {
		this.neck_weizhi = neck_weizhi;
	}

	public String getNeck_xingzhi() {
		return neck_xingzhi;
	}

	public void setNeck_xingzhi(String neck_xingzhi) {
		this.neck_xingzhi = neck_xingzhi;
	}

	public String getNeck_zhidi() {
		return neck_zhidi;
	}

	public void setNeck_zhidi(String neck_zhidi) {
		this.neck_zhidi = neck_zhidi;
	}

	public String getPi_bianyuan() {
		return pi_bianyuan;
	}

	public void setPi_bianyuan(String pi_bianyuan) {
		this.pi_bianyuan = pi_bianyuan;
	}

	public String getPi_biaomain() {
		return pi_biaomain;
	}

	public void setPi_biaomain(String pi_biaomain) {
		this.pi_biaomain = pi_biaomain;
	}

	public String getPi_jiantuxia() {
		return pi_jiantuxia;
	}

	public void setPi_jiantuxia(String pi_jiantuxia) {
		this.pi_jiantuxia = pi_jiantuxia;
	}

	public String getPi_leixia() {
		return pi_leixia;
	}

	public void setPi_leixia(String pi_leixia) {
		this.pi_leixia = pi_leixia;
	}

	public String getPi_yatong() {
		return pi_yatong;
	}

	public void setPi_yatong(String pi_yatong) {
		this.pi_yatong = pi_yatong;
	}

	public String getPi_zhidi() {
		return pi_zhidi;
	}

	public void setPi_zhidi(String pi_zhidi) {
		this.pi_zhidi = pi_zhidi;
	}

	public String getShen_shuniaoguan() {
		return shen_shuniaoguan;
	}

	public void setShen_shuniaoguan(String shen_shuniaoguan) {
		this.shen_shuniaoguan = shen_shuniaoguan;
	}

	public String getShen_weizhi() {
		return shen_weizhi;
	}

	public void setShen_weizhi(String shen_weizhi) {
		this.shen_weizhi = shen_weizhi;
	}

	public String getShen_yatong() {
		return shen_yatong;
	}

	public void setShen_yatong(String shen_yatong) {
		this.shen_yatong = shen_yatong;
	}

	public Integer getPfnm_zhizhu() {
		return pfnm_zhizhu;
	}

	public void setPfnm_zhizhu(Integer pfnm_zhizhu) {
		this.pfnm_zhizhu = pfnm_zhizhu;
	}

	public String getEyes_yanqiu0() {
		return eyes_yanqiu0;
	}

	public void setEyes_yanqiu0(String eyes_yanqiu0) {
		this.eyes_yanqiu0 = eyes_yanqiu0;
	}
	
}
