package com.juncsoft.KLJY.InHospitalCase.ENT.entity;

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
	private String eyes_jiemo;
	private int eyes_jiaomo;
	private String eyes_jiaomoDesc;
	private String eyes_gongmo;
	private String eyes_yanqiu;
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
}
