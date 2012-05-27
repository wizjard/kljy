package com.juncsoft.KLJY.InHospitalCase.ENT.entity;

public class SpecialExamination{
	private Long id;
	private Long caseId;
	private String specail_erl;
	private String specail_err;
	private String specail_waierl;
	private String specail_waierr;
	private String specail_gumol;
	private String specail_gumor;
	private String specail_biqiang;
	private String specail_houbi;
	private String specail_yanbu;
	private String specail_houbu;
	
	private String erkou_chongxue;
	private String erkou_shuizhong;
	private String erkou_zhongwu;
	private String erkou_weizhi;
	private String erkou_biaomian;
	private int erkou_daxiao;
	private String erkou_other;
	
	private String waierdao_chongxue;
	private String waierdao_shuizhong;
	private String waierdao_zhongwu;
	private String waierdao_biaomian;
	private int waierdao_daxiao;
	private String waierdao_jiezhong;
	private String waierdao_loukou;
	private String waierdao_xiazhai;
	private String waierdao_fenbiwu;
	private String waierdao_qiwei;
	private String waierdao_other;
	
	private String gumo_yanse;
	private String gumo_biaozhi;
	private String gumo_chongxue;
	private String gumo_zhongzhang;
	private String gumo_neixian;
	private String gumo_zenghou;
	private String gumo_jiye;
	private String gumo_rouya;
	private String gumo_danzhiliu;
	private String gumo_chuankong;
	private String gumo_weiyu;
	private String gumo_type;
	private String gumo_xingzhuang;
	
	private String waibi_pifu;
	private String waibi_waiqu;
	private String waibi_anbi;
	
	private String biqianting_hongzhong;
	private String biqianting_longqi;
	private String biqianting_cucao;
	
	private String biqiang_zhongwu;
	private String biqiang_shulian;
	private String biqiang_biaomian;
	private String biqiang_yanse;
	private String biqiang_xingzhuang;
	private String biqiang_tanzhongwu;
	private String biqiang_jidi;
	
	private String bijia_zhongzhang;
	private String bijia_chongxue;
	private String bijia_shuizhong;
	private String bijia_yanse;
	private String bijia_biaomian;
	private String bijia_xingtai;
	
	private String bidao_fenbiwu;
	private String bidao_fenbiwu_weizhi;
	private String bidao_yanse;
	private String bidao_xingzhi;
	private String bidao_zhongwu;//不要
	private String bidao_shulian;
	private String bidao_biaomian;
	private String bidao_zhongwu_yanse;
	private String bidao_xingzhuang;
	private String bidao_tanzhongwu;
	private String bidao_jidi;
	private String bidao_feida;
	private String bidao_zhongjia;
	private String bidao_pianqu;
	private String bidao_guji;
	private String bidao_chuankong;
	private String bidao_chuankong_daxiao;
	private String bidao_chuankong_weizhi;
	private String bidao_milan;
	private String bidao_kuiyang;
	
	private String yanyinwo_chongxue;
	private String yanyinwo_shuizhong;
	private String yanyinwo_nianmo;
	private String yanyinwo_xinshengwu;
	private String yanyinwo_biaomian;
	private String yanyinwo_yanse;
	private String yanyinwo_xingzhuang;
	
	private String yggyz_chongxue;
	private String yggyz_shuizhong;
	private String yggyz_nianmo;
	private String yggyz_xinshengwu;
	private String yggyz_biaomian;
	private String yggyz_yanse;
	private String yggyz_xingzhuang;
	
	private String biyandingbi_chongxue;
	private String biyandingbi_shuizhong;
	private String biyandingbi_nianmo;
	private String biyandingbi_xinshengwu;
	private String biyandingbi_biaomian;
	private String biyandingbi_yanse;
	private String biyandingbi_xingzhuang;
	
	private String ruane_tanhuan;
	private String ruane_chuongxue;
	private String ruane_kuiyan;
	private String ruane_quesun;
	private String ruane_penglong;
	private String ruane_xinshengwu;
	private String ruane_biaomian;
	private String ruane_yanse;
	private String ruane_xingzhuang;
	private String ruane_daxiao;
	private String ruane_bahen;
	private String ruane_xuanyongchui;
	
	private String btti_zhongda;
	private String btti_daxiao;
	private String btti_chongxue;
	private String btti_nongdian;
	private String btti_kuiyang;
	private String btti_jiaohuawu;
	private String btti_xinshengwu;
	private String btti_biaomian;
	private String btti_yanse;
	private String btti_xingzhuang;
	private String btti_xinsheng_daxiao;
	
	private String yhb_chongxue;
	private String yhb_linba;
	private String yhb_nianmo;
	private String yhb_cesuo;
	
	private String huiyan_chongxue;
	private String huiyan_chongxue_chengdu;
	private String huiyan_zhongzhang;
	private String huiyan_nianmo;
	private String huiyan_taiju;
	private String huiyan_xinshengwu;
	private String huiyan_biaomian;
	private String huiyan_yanse;
	private String huiyan_xingzhuang;
	private String huiyan_daxiao;
	private String huiyan_weizhi;
	private String huiyan_cebie;
	
	private String shidai_nianmo;
	private String shidai_chongxue;
	private String shidai_chongxue_cebie;
	private String shidai_chongxue_chengdu;
	private String shidai_xiachu;
	private String shidai_xiachu_chengdu;
	private String shidai_feihou;
	private String shidai_feihou_chengdu;
	private String shidai_xinshengwu;
	private String shidai_biaomian;
	private String shidai_yanse;
	private String shidai_xingzhuang;
	
	private String houshi_qingxi;
	private String houshi_cebie;
	private String houshi_nianmo;
	private String houshi_xinshengwu;
	private String houshi_biaomian;
	private String houshi_yanse;
	private String houshi_xingzhuang;
	
	private String shaozhuang_yundong;
	private String shaozhuang_cebie;
	private String shaozhuang_duichen;
	private String shaozhuang_chongxue;
	private String shaozhuang_xinshengwu;
	private String shaozhuang_biaomian;
	private String shaozhuang_yanse;
	private String shaozhuang_xingzhuang;
	
	private String lizhuang_nianmo;
	private String lizhuang_cebie;
	private String lizhuang_jiye;
	private String lizhuang_xingtai;
	private String lizhuang_zhongwu;
	private String lizhuang_biaomian;
	private String lizhuang_yanse;
	private String lizhuang_xingzhuang;
	
	private String huanhou_nianmo;
	private String huanhou_jiye;
	private String huanhou_xingtai;
	private String huanhou_shengmen;
	
	private String gumo_other;
	private String biqian_other;
	private String bijia_other;
	private String bizhongge_other;
	private String biyanbu_other;
	private String ruane_other;
	private String shengdai_other;
	private String houshi_other;
	private String shaozhuang_other;
	private String huanhouxi_other;
	private String zuihou_other;
	
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
	public String getSpecail_erl() {
		return specail_erl;
	}
	public void setSpecail_erl(String specailErl) {
		specail_erl = specailErl;
	}
	public String getSpecail_err() {
		return specail_err;
	}
	public void setSpecail_err(String specailErr) {
		specail_err = specailErr;
	}
	public String getSpecail_waierl() {
		return specail_waierl;
	}
	public void setSpecail_waierl(String specailWaierl) {
		specail_waierl = specailWaierl;
	}
	public String getSpecail_waierr() {
		return specail_waierr;
	}
	public void setSpecail_waierr(String specailWaierr) {
		specail_waierr = specailWaierr;
	}
	public String getSpecail_gumol() {
		return specail_gumol;
	}
	public void setSpecail_gumol(String specailGumol) {
		specail_gumol = specailGumol;
	}
	public String getSpecail_gumor() {
		return specail_gumor;
	}
	public void setSpecail_gumor(String specailGumor) {
		specail_gumor = specailGumor;
	}
	public String getSpecail_biqiang() {
		return specail_biqiang;
	}
	public void setSpecail_biqiang(String specailBiqiang) {
		specail_biqiang = specailBiqiang;
	}
	public String getSpecail_houbi() {
		return specail_houbi;
	}
	public void setSpecail_houbi(String specailHoubi) {
		specail_houbi = specailHoubi;
	}
	public String getSpecail_yanbu() {
		return specail_yanbu;
	}
	public void setSpecail_yanbu(String specailYanbu) {
		specail_yanbu = specailYanbu;
	}
	public String getSpecail_houbu() {
		return specail_houbu;
	}
	public void setSpecail_houbu(String specailHoubu) {
		specail_houbu = specailHoubu;
	}
	public String getBidao_biaomian() {
		return bidao_biaomian;
	}
	public void setBidao_biaomian(String bidao_biaomian) {
		this.bidao_biaomian = bidao_biaomian;
	}
	public String getBidao_chuankong() {
		return bidao_chuankong;
	}
	public void setBidao_chuankong(String bidao_chuankong) {
		this.bidao_chuankong = bidao_chuankong;
	}
	public String getBidao_chuankong_daxiao() {
		return bidao_chuankong_daxiao;
	}
	public void setBidao_chuankong_daxiao(String bidao_chuankong_daxiao) {
		this.bidao_chuankong_daxiao = bidao_chuankong_daxiao;
	}
	public String getBidao_chuankong_weizhi() {
		return bidao_chuankong_weizhi;
	}
	public void setBidao_chuankong_weizhi(String bidao_chuankong_weizhi) {
		this.bidao_chuankong_weizhi = bidao_chuankong_weizhi;
	}
	public String getBidao_feida() {
		return bidao_feida;
	}
	public void setBidao_feida(String bidao_feida) {
		this.bidao_feida = bidao_feida;
	}
	public String getBidao_fenbiwu() {
		return bidao_fenbiwu;
	}
	public void setBidao_fenbiwu(String bidao_fenbiwu) {
		this.bidao_fenbiwu = bidao_fenbiwu;
	}
	public String getBidao_fenbiwu_weizhi() {
		return bidao_fenbiwu_weizhi;
	}
	public void setBidao_fenbiwu_weizhi(String bidao_fenbiwu_weizhi) {
		this.bidao_fenbiwu_weizhi = bidao_fenbiwu_weizhi;
	}
	public String getBidao_guji() {
		return bidao_guji;
	}
	public void setBidao_guji(String bidao_guji) {
		this.bidao_guji = bidao_guji;
	}
	public String getBidao_jidi() {
		return bidao_jidi;
	}
	public void setBidao_jidi(String bidao_jidi) {
		this.bidao_jidi = bidao_jidi;
	}
	public String getBidao_kuiyang() {
		return bidao_kuiyang;
	}
	public void setBidao_kuiyang(String bidao_kuiyang) {
		this.bidao_kuiyang = bidao_kuiyang;
	}
	public String getBidao_milan() {
		return bidao_milan;
	}
	public void setBidao_milan(String bidao_milan) {
		this.bidao_milan = bidao_milan;
	}
	public String getBidao_pianqu() {
		return bidao_pianqu;
	}
	public void setBidao_pianqu(String bidao_pianqu) {
		this.bidao_pianqu = bidao_pianqu;
	}
	public String getBidao_shulian() {
		return bidao_shulian;
	}
	public void setBidao_shulian(String bidao_shulian) {
		this.bidao_shulian = bidao_shulian;
	}
	public String getBidao_tanzhongwu() {
		return bidao_tanzhongwu;
	}
	public void setBidao_tanzhongwu(String bidao_tanzhongwu) {
		this.bidao_tanzhongwu = bidao_tanzhongwu;
	}
	public String getBidao_xingzhi() {
		return bidao_xingzhi;
	}
	public void setBidao_xingzhi(String bidao_xingzhi) {
		this.bidao_xingzhi = bidao_xingzhi;
	}
	public String getBidao_xingzhuang() {
		return bidao_xingzhuang;
	}
	public void setBidao_xingzhuang(String bidao_xingzhuang) {
		this.bidao_xingzhuang = bidao_xingzhuang;
	}
	public String getBidao_yanse() {
		return bidao_yanse;
	}
	public void setBidao_yanse(String bidao_yanse) {
		this.bidao_yanse = bidao_yanse;
	}
	public String getBidao_zhongjia() {
		return bidao_zhongjia;
	}
	public void setBidao_zhongjia(String bidao_zhongjia) {
		this.bidao_zhongjia = bidao_zhongjia;
	}
	public String getBidao_zhongwu() {
		return bidao_zhongwu;
	}
	public void setBidao_zhongwu(String bidao_zhongwu) {
		this.bidao_zhongwu = bidao_zhongwu;
	}
	public String getBidao_zhongwu_yanse() {
		return bidao_zhongwu_yanse;
	}
	public void setBidao_zhongwu_yanse(String bidao_zhongwu_yanse) {
		this.bidao_zhongwu_yanse = bidao_zhongwu_yanse;
	}
	public String getBijia_biaomian() {
		return bijia_biaomian;
	}
	public void setBijia_biaomian(String bijia_biaomian) {
		this.bijia_biaomian = bijia_biaomian;
	}
	public String getBijia_chongxue() {
		return bijia_chongxue;
	}
	public void setBijia_chongxue(String bijia_chongxue) {
		this.bijia_chongxue = bijia_chongxue;
	}
	public String getBijia_shuizhong() {
		return bijia_shuizhong;
	}
	public void setBijia_shuizhong(String bijia_shuizhong) {
		this.bijia_shuizhong = bijia_shuizhong;
	}
	public String getBijia_xingtai() {
		return bijia_xingtai;
	}
	public void setBijia_xingtai(String bijia_xingtai) {
		this.bijia_xingtai = bijia_xingtai;
	}
	public String getBijia_yanse() {
		return bijia_yanse;
	}
	public void setBijia_yanse(String bijia_yanse) {
		this.bijia_yanse = bijia_yanse;
	}
	public String getBijia_zhongzhang() {
		return bijia_zhongzhang;
	}
	public void setBijia_zhongzhang(String bijia_zhongzhang) {
		this.bijia_zhongzhang = bijia_zhongzhang;
	}
	public String getBiqiang_biaomian() {
		return biqiang_biaomian;
	}
	public void setBiqiang_biaomian(String biqiang_biaomian) {
		this.biqiang_biaomian = biqiang_biaomian;
	}
	public String getBiqiang_jidi() {
		return biqiang_jidi;
	}
	public void setBiqiang_jidi(String biqiang_jidi) {
		this.biqiang_jidi = biqiang_jidi;
	}
	public String getBiqiang_shulian() {
		return biqiang_shulian;
	}
	public void setBiqiang_shulian(String biqiang_shulian) {
		this.biqiang_shulian = biqiang_shulian;
	}
	public String getBiqiang_tanzhongwu() {
		return biqiang_tanzhongwu;
	}
	public void setBiqiang_tanzhongwu(String biqiang_tanzhongwu) {
		this.biqiang_tanzhongwu = biqiang_tanzhongwu;
	}
	public String getBiqiang_xingzhuang() {
		return biqiang_xingzhuang;
	}
	public void setBiqiang_xingzhuang(String biqiang_xingzhuang) {
		this.biqiang_xingzhuang = biqiang_xingzhuang;
	}
	public String getBiqiang_yanse() {
		return biqiang_yanse;
	}
	public void setBiqiang_yanse(String biqiang_yanse) {
		this.biqiang_yanse = biqiang_yanse;
	}
	public String getBiqiang_zhongwu() {
		return biqiang_zhongwu;
	}
	public void setBiqiang_zhongwu(String biqiang_zhongwu) {
		this.biqiang_zhongwu = biqiang_zhongwu;
	}
	public String getBiqianting_cucao() {
		return biqianting_cucao;
	}
	public void setBiqianting_cucao(String biqianting_cucao) {
		this.biqianting_cucao = biqianting_cucao;
	}
	public String getBiqianting_hongzhong() {
		return biqianting_hongzhong;
	}
	public void setBiqianting_hongzhong(String biqianting_hongzhong) {
		this.biqianting_hongzhong = biqianting_hongzhong;
	}
	public String getBiqianting_longqi() {
		return biqianting_longqi;
	}
	public void setBiqianting_longqi(String biqianting_longqi) {
		this.biqianting_longqi = biqianting_longqi;
	}
	public String getBiyandingbi_biaomian() {
		return biyandingbi_biaomian;
	}
	public void setBiyandingbi_biaomian(String biyandingbi_biaomian) {
		this.biyandingbi_biaomian = biyandingbi_biaomian;
	}
	public String getBiyandingbi_chongxue() {
		return biyandingbi_chongxue;
	}
	public void setBiyandingbi_chongxue(String biyandingbi_chongxue) {
		this.biyandingbi_chongxue = biyandingbi_chongxue;
	}
	public String getBiyandingbi_nianmo() {
		return biyandingbi_nianmo;
	}
	public void setBiyandingbi_nianmo(String biyandingbi_nianmo) {
		this.biyandingbi_nianmo = biyandingbi_nianmo;
	}
	public String getBiyandingbi_shuizhong() {
		return biyandingbi_shuizhong;
	}
	public void setBiyandingbi_shuizhong(String biyandingbi_shuizhong) {
		this.biyandingbi_shuizhong = biyandingbi_shuizhong;
	}
	public String getBiyandingbi_xingzhuang() {
		return biyandingbi_xingzhuang;
	}
	public void setBiyandingbi_xingzhuang(String biyandingbi_xingzhuang) {
		this.biyandingbi_xingzhuang = biyandingbi_xingzhuang;
	}
	public String getBiyandingbi_xinshengwu() {
		return biyandingbi_xinshengwu;
	}
	public void setBiyandingbi_xinshengwu(String biyandingbi_xinshengwu) {
		this.biyandingbi_xinshengwu = biyandingbi_xinshengwu;
	}
	public String getBiyandingbi_yanse() {
		return biyandingbi_yanse;
	}
	public void setBiyandingbi_yanse(String biyandingbi_yanse) {
		this.biyandingbi_yanse = biyandingbi_yanse;
	}
	public String getBtti_biaomian() {
		return btti_biaomian;
	}
	public void setBtti_biaomian(String btti_biaomian) {
		this.btti_biaomian = btti_biaomian;
	}
	public String getBtti_chongxue() {
		return btti_chongxue;
	}
	public void setBtti_chongxue(String btti_chongxue) {
		this.btti_chongxue = btti_chongxue;
	}
	public String getBtti_daxiao() {
		return btti_daxiao;
	}
	public void setBtti_daxiao(String btti_daxiao) {
		this.btti_daxiao = btti_daxiao;
	}
	public String getBtti_jiaohuawu() {
		return btti_jiaohuawu;
	}
	public void setBtti_jiaohuawu(String btti_jiaohuawu) {
		this.btti_jiaohuawu = btti_jiaohuawu;
	}
	public String getBtti_kuiyang() {
		return btti_kuiyang;
	}
	public void setBtti_kuiyang(String btti_kuiyang) {
		this.btti_kuiyang = btti_kuiyang;
	}
	public String getBtti_nongdian() {
		return btti_nongdian;
	}
	public void setBtti_nongdian(String btti_nongdian) {
		this.btti_nongdian = btti_nongdian;
	}
	public String getBtti_xingzhuang() {
		return btti_xingzhuang;
	}
	public void setBtti_xingzhuang(String btti_xingzhuang) {
		this.btti_xingzhuang = btti_xingzhuang;
	}
	public String getBtti_xinsheng_daxiao() {
		return btti_xinsheng_daxiao;
	}
	public void setBtti_xinsheng_daxiao(String btti_xinsheng_daxiao) {
		this.btti_xinsheng_daxiao = btti_xinsheng_daxiao;
	}
	public String getBtti_xinshengwu() {
		return btti_xinshengwu;
	}
	public void setBtti_xinshengwu(String btti_xinshengwu) {
		this.btti_xinshengwu = btti_xinshengwu;
	}
	public String getBtti_yanse() {
		return btti_yanse;
	}
	public void setBtti_yanse(String btti_yanse) {
		this.btti_yanse = btti_yanse;
	}
	public String getBtti_zhongda() {
		return btti_zhongda;
	}
	public void setBtti_zhongda(String btti_zhongda) {
		this.btti_zhongda = btti_zhongda;
	}
	public String getErkou_biaomian() {
		return erkou_biaomian;
	}
	public void setErkou_biaomian(String erkou_biaomian) {
		this.erkou_biaomian = erkou_biaomian;
	}
	public String getErkou_chongxue() {
		return erkou_chongxue;
	}
	public void setErkou_chongxue(String erkou_chongxue) {
		this.erkou_chongxue = erkou_chongxue;
	}
	public int getErkou_daxiao() {
		return erkou_daxiao;
	}
	public void setErkou_daxiao(int erkou_daxiao) {
		this.erkou_daxiao = erkou_daxiao;
	}
	public String getErkou_other() {
		return erkou_other;
	}
	public void setErkou_other(String erkou_other) {
		this.erkou_other = erkou_other;
	}
	public String getErkou_shuizhong() {
		return erkou_shuizhong;
	}
	public void setErkou_shuizhong(String erkou_shuizhong) {
		this.erkou_shuizhong = erkou_shuizhong;
	}
	public String getErkou_weizhi() {
		return erkou_weizhi;
	}
	public void setErkou_weizhi(String erkou_weizhi) {
		this.erkou_weizhi = erkou_weizhi;
	}
	public String getErkou_zhongwu() {
		return erkou_zhongwu;
	}
	public void setErkou_zhongwu(String erkou_zhongwu) {
		this.erkou_zhongwu = erkou_zhongwu;
	}
	public String getGumo_biaozhi() {
		return gumo_biaozhi;
	}
	public void setGumo_biaozhi(String gumo_biaozhi) {
		this.gumo_biaozhi = gumo_biaozhi;
	}
	public String getGumo_chongxue() {
		return gumo_chongxue;
	}
	public void setGumo_chongxue(String gumo_chongxue) {
		this.gumo_chongxue = gumo_chongxue;
	}
	public String getGumo_chuankong() {
		return gumo_chuankong;
	}
	public void setGumo_chuankong(String gumo_chuankong) {
		this.gumo_chuankong = gumo_chuankong;
	}
	public String getGumo_danzhiliu() {
		return gumo_danzhiliu;
	}
	public void setGumo_danzhiliu(String gumo_danzhiliu) {
		this.gumo_danzhiliu = gumo_danzhiliu;
	}
	public String getGumo_jiye() {
		return gumo_jiye;
	}
	public void setGumo_jiye(String gumo_jiye) {
		this.gumo_jiye = gumo_jiye;
	}
	public String getGumo_neixian() {
		return gumo_neixian;
	}
	public void setGumo_neixian(String gumo_neixian) {
		this.gumo_neixian = gumo_neixian;
	}
	public String getGumo_rouya() {
		return gumo_rouya;
	}
	public void setGumo_rouya(String gumo_rouya) {
		this.gumo_rouya = gumo_rouya;
	}
	public String getGumo_type() {
		return gumo_type;
	}
	public void setGumo_type(String gumo_type) {
		this.gumo_type = gumo_type;
	}
	public String getGumo_weiyu() {
		return gumo_weiyu;
	}
	public void setGumo_weiyu(String gumo_weiyu) {
		this.gumo_weiyu = gumo_weiyu;
	}
	public String getGumo_xingzhuang() {
		return gumo_xingzhuang;
	}
	public void setGumo_xingzhuang(String gumo_xingzhuang) {
		this.gumo_xingzhuang = gumo_xingzhuang;
	}
	public String getGumo_yanse() {
		return gumo_yanse;
	}
	public void setGumo_yanse(String gumo_yanse) {
		this.gumo_yanse = gumo_yanse;
	}
	public String getGumo_zenghou() {
		return gumo_zenghou;
	}
	public void setGumo_zenghou(String gumo_zenghou) {
		this.gumo_zenghou = gumo_zenghou;
	}
	public String getGumo_zhongzhang() {
		return gumo_zhongzhang;
	}
	public void setGumo_zhongzhang(String gumo_zhongzhang) {
		this.gumo_zhongzhang = gumo_zhongzhang;
	}
	public String getHoushi_biaomian() {
		return houshi_biaomian;
	}
	public void setHoushi_biaomian(String houshi_biaomian) {
		this.houshi_biaomian = houshi_biaomian;
	}
	public String getHoushi_cebie() {
		return houshi_cebie;
	}
	public void setHoushi_cebie(String houshi_cebie) {
		this.houshi_cebie = houshi_cebie;
	}
	public String getHoushi_nianmo() {
		return houshi_nianmo;
	}
	public void setHoushi_nianmo(String houshi_nianmo) {
		this.houshi_nianmo = houshi_nianmo;
	}
	public String getHoushi_qingxi() {
		return houshi_qingxi;
	}
	public void setHoushi_qingxi(String houshi_qingxi) {
		this.houshi_qingxi = houshi_qingxi;
	}
	public String getHoushi_xingzhuang() {
		return houshi_xingzhuang;
	}
	public void setHoushi_xingzhuang(String houshi_xingzhuang) {
		this.houshi_xingzhuang = houshi_xingzhuang;
	}
	public String getHoushi_xinshengwu() {
		return houshi_xinshengwu;
	}
	public void setHoushi_xinshengwu(String houshi_xinshengwu) {
		this.houshi_xinshengwu = houshi_xinshengwu;
	}
	public String getHoushi_yanse() {
		return houshi_yanse;
	}
	public void setHoushi_yanse(String houshi_yanse) {
		this.houshi_yanse = houshi_yanse;
	}
	public String getHuanhou_jiye() {
		return huanhou_jiye;
	}
	public void setHuanhou_jiye(String huanhou_jiye) {
		this.huanhou_jiye = huanhou_jiye;
	}
	public String getHuanhou_nianmo() {
		return huanhou_nianmo;
	}
	public void setHuanhou_nianmo(String huanhou_nianmo) {
		this.huanhou_nianmo = huanhou_nianmo;
	}
	public String getHuanhou_shengmen() {
		return huanhou_shengmen;
	}
	public void setHuanhou_shengmen(String huanhou_shengmen) {
		this.huanhou_shengmen = huanhou_shengmen;
	}
	public String getHuanhou_xingtai() {
		return huanhou_xingtai;
	}
	public void setHuanhou_xingtai(String huanhou_xingtai) {
		this.huanhou_xingtai = huanhou_xingtai;
	}
	public String getHuiyan_biaomian() {
		return huiyan_biaomian;
	}
	public void setHuiyan_biaomian(String huiyan_biaomian) {
		this.huiyan_biaomian = huiyan_biaomian;
	}
	public String getHuiyan_cebie() {
		return huiyan_cebie;
	}
	public void setHuiyan_cebie(String huiyan_cebie) {
		this.huiyan_cebie = huiyan_cebie;
	}
	public String getHuiyan_chongxue() {
		return huiyan_chongxue;
	}
	public void setHuiyan_chongxue(String huiyan_chongxue) {
		this.huiyan_chongxue = huiyan_chongxue;
	}
	public String getHuiyan_chongxue_chengdu() {
		return huiyan_chongxue_chengdu;
	}
	public void setHuiyan_chongxue_chengdu(String huiyan_chongxue_chengdu) {
		this.huiyan_chongxue_chengdu = huiyan_chongxue_chengdu;
	}
	public String getHuiyan_daxiao() {
		return huiyan_daxiao;
	}
	public void setHuiyan_daxiao(String huiyan_daxiao) {
		this.huiyan_daxiao = huiyan_daxiao;
	}
	public String getHuiyan_nianmo() {
		return huiyan_nianmo;
	}
	public void setHuiyan_nianmo(String huiyan_nianmo) {
		this.huiyan_nianmo = huiyan_nianmo;
	}
	public String getHuiyan_taiju() {
		return huiyan_taiju;
	}
	public void setHuiyan_taiju(String huiyan_taiju) {
		this.huiyan_taiju = huiyan_taiju;
	}
	public String getHuiyan_weizhi() {
		return huiyan_weizhi;
	}
	public void setHuiyan_weizhi(String huiyan_weizhi) {
		this.huiyan_weizhi = huiyan_weizhi;
	}
	public String getHuiyan_xingzhuang() {
		return huiyan_xingzhuang;
	}
	public void setHuiyan_xingzhuang(String huiyan_xingzhuang) {
		this.huiyan_xingzhuang = huiyan_xingzhuang;
	}
	public String getHuiyan_xinshengwu() {
		return huiyan_xinshengwu;
	}
	public void setHuiyan_xinshengwu(String huiyan_xinshengwu) {
		this.huiyan_xinshengwu = huiyan_xinshengwu;
	}
	public String getHuiyan_yanse() {
		return huiyan_yanse;
	}
	public void setHuiyan_yanse(String huiyan_yanse) {
		this.huiyan_yanse = huiyan_yanse;
	}
	public String getHuiyan_zhongzhang() {
		return huiyan_zhongzhang;
	}
	public void setHuiyan_zhongzhang(String huiyan_zhongzhang) {
		this.huiyan_zhongzhang = huiyan_zhongzhang;
	}
	public String getLizhuang_biaomian() {
		return lizhuang_biaomian;
	}
	public void setLizhuang_biaomian(String lizhuang_biaomian) {
		this.lizhuang_biaomian = lizhuang_biaomian;
	}
	public String getLizhuang_cebie() {
		return lizhuang_cebie;
	}
	public void setLizhuang_cebie(String lizhuang_cebie) {
		this.lizhuang_cebie = lizhuang_cebie;
	}
	public String getLizhuang_jiye() {
		return lizhuang_jiye;
	}
	public void setLizhuang_jiye(String lizhuang_jiye) {
		this.lizhuang_jiye = lizhuang_jiye;
	}
	public String getLizhuang_nianmo() {
		return lizhuang_nianmo;
	}
	public void setLizhuang_nianmo(String lizhuang_nianmo) {
		this.lizhuang_nianmo = lizhuang_nianmo;
	}
	public String getLizhuang_xingtai() {
		return lizhuang_xingtai;
	}
	public void setLizhuang_xingtai(String lizhuang_xingtai) {
		this.lizhuang_xingtai = lizhuang_xingtai;
	}
	public String getLizhuang_xingzhuang() {
		return lizhuang_xingzhuang;
	}
	public void setLizhuang_xingzhuang(String lizhuang_xingzhuang) {
		this.lizhuang_xingzhuang = lizhuang_xingzhuang;
	}
	public String getLizhuang_yanse() {
		return lizhuang_yanse;
	}
	public void setLizhuang_yanse(String lizhuang_yanse) {
		this.lizhuang_yanse = lizhuang_yanse;
	}
	public String getLizhuang_zhongwu() {
		return lizhuang_zhongwu;
	}
	public void setLizhuang_zhongwu(String lizhuang_zhongwu) {
		this.lizhuang_zhongwu = lizhuang_zhongwu;
	}
	public String getRuane_bahen() {
		return ruane_bahen;
	}
	public void setRuane_bahen(String ruane_bahen) {
		this.ruane_bahen = ruane_bahen;
	}
	public String getRuane_biaomian() {
		return ruane_biaomian;
	}
	public void setRuane_biaomian(String ruane_biaomian) {
		this.ruane_biaomian = ruane_biaomian;
	}
	public String getRuane_chuongxue() {
		return ruane_chuongxue;
	}
	public void setRuane_chuongxue(String ruane_chuongxue) {
		this.ruane_chuongxue = ruane_chuongxue;
	}
	public String getRuane_daxiao() {
		return ruane_daxiao;
	}
	public void setRuane_daxiao(String ruane_daxiao) {
		this.ruane_daxiao = ruane_daxiao;
	}
	public String getRuane_kuiyan() {
		return ruane_kuiyan;
	}
	public void setRuane_kuiyan(String ruane_kuiyan) {
		this.ruane_kuiyan = ruane_kuiyan;
	}
	public String getRuane_penglong() {
		return ruane_penglong;
	}
	public void setRuane_penglong(String ruane_penglong) {
		this.ruane_penglong = ruane_penglong;
	}
	public String getRuane_quesun() {
		return ruane_quesun;
	}
	public void setRuane_quesun(String ruane_quesun) {
		this.ruane_quesun = ruane_quesun;
	}
	public String getRuane_tanhuan() {
		return ruane_tanhuan;
	}
	public void setRuane_tanhuan(String ruane_tanhuan) {
		this.ruane_tanhuan = ruane_tanhuan;
	}
	public String getRuane_xingzhuang() {
		return ruane_xingzhuang;
	}
	public void setRuane_xingzhuang(String ruane_xingzhuang) {
		this.ruane_xingzhuang = ruane_xingzhuang;
	}
	public String getRuane_xinshengwu() {
		return ruane_xinshengwu;
	}
	public void setRuane_xinshengwu(String ruane_xinshengwu) {
		this.ruane_xinshengwu = ruane_xinshengwu;
	}
	public String getRuane_xuanyongchui() {
		return ruane_xuanyongchui;
	}
	public void setRuane_xuanyongchui(String ruane_xuanyongchui) {
		this.ruane_xuanyongchui = ruane_xuanyongchui;
	}
	public String getRuane_yanse() {
		return ruane_yanse;
	}
	public void setRuane_yanse(String ruane_yanse) {
		this.ruane_yanse = ruane_yanse;
	}
	public String getShaozhuang_biaomian() {
		return shaozhuang_biaomian;
	}
	public void setShaozhuang_biaomian(String shaozhuang_biaomian) {
		this.shaozhuang_biaomian = shaozhuang_biaomian;
	}
	public String getShaozhuang_cebie() {
		return shaozhuang_cebie;
	}
	public void setShaozhuang_cebie(String shaozhuang_cebie) {
		this.shaozhuang_cebie = shaozhuang_cebie;
	}
	public String getShaozhuang_chongxue() {
		return shaozhuang_chongxue;
	}
	public void setShaozhuang_chongxue(String shaozhuang_chongxue) {
		this.shaozhuang_chongxue = shaozhuang_chongxue;
	}
	public String getShaozhuang_duichen() {
		return shaozhuang_duichen;
	}
	public void setShaozhuang_duichen(String shaozhuang_duichen) {
		this.shaozhuang_duichen = shaozhuang_duichen;
	}
	public String getShaozhuang_xingzhuang() {
		return shaozhuang_xingzhuang;
	}
	public void setShaozhuang_xingzhuang(String shaozhuang_xingzhuang) {
		this.shaozhuang_xingzhuang = shaozhuang_xingzhuang;
	}
	public String getShaozhuang_xinshengwu() {
		return shaozhuang_xinshengwu;
	}
	public void setShaozhuang_xinshengwu(String shaozhuang_xinshengwu) {
		this.shaozhuang_xinshengwu = shaozhuang_xinshengwu;
	}
	public String getShaozhuang_yanse() {
		return shaozhuang_yanse;
	}
	public void setShaozhuang_yanse(String shaozhuang_yanse) {
		this.shaozhuang_yanse = shaozhuang_yanse;
	}
	public String getShaozhuang_yundong() {
		return shaozhuang_yundong;
	}
	public void setShaozhuang_yundong(String shaozhuang_yundong) {
		this.shaozhuang_yundong = shaozhuang_yundong;
	}
	public String getShidai_biaomian() {
		return shidai_biaomian;
	}
	public void setShidai_biaomian(String shidai_biaomian) {
		this.shidai_biaomian = shidai_biaomian;
	}
	public String getShidai_chongxue() {
		return shidai_chongxue;
	}
	public void setShidai_chongxue(String shidai_chongxue) {
		this.shidai_chongxue = shidai_chongxue;
	}
	public String getShidai_chongxue_cebie() {
		return shidai_chongxue_cebie;
	}
	public void setShidai_chongxue_cebie(String shidai_chongxue_cebie) {
		this.shidai_chongxue_cebie = shidai_chongxue_cebie;
	}
	public String getShidai_chongxue_chengdu() {
		return shidai_chongxue_chengdu;
	}
	public void setShidai_chongxue_chengdu(String shidai_chongxue_chengdu) {
		this.shidai_chongxue_chengdu = shidai_chongxue_chengdu;
	}
	public String getShidai_feihou() {
		return shidai_feihou;
	}
	public void setShidai_feihou(String shidai_feihou) {
		this.shidai_feihou = shidai_feihou;
	}
	public String getShidai_feihou_chengdu() {
		return shidai_feihou_chengdu;
	}
	public void setShidai_feihou_chengdu(String shidai_feihou_chengdu) {
		this.shidai_feihou_chengdu = shidai_feihou_chengdu;
	}
	public String getShidai_nianmo() {
		return shidai_nianmo;
	}
	public void setShidai_nianmo(String shidai_nianmo) {
		this.shidai_nianmo = shidai_nianmo;
	}
	public String getShidai_xiachu() {
		return shidai_xiachu;
	}
	public void setShidai_xiachu(String shidai_xiachu) {
		this.shidai_xiachu = shidai_xiachu;
	}
	public String getShidai_xiachu_chengdu() {
		return shidai_xiachu_chengdu;
	}
	public void setShidai_xiachu_chengdu(String shidai_xiachu_chengdu) {
		this.shidai_xiachu_chengdu = shidai_xiachu_chengdu;
	}
	public String getShidai_xingzhuang() {
		return shidai_xingzhuang;
	}
	public void setShidai_xingzhuang(String shidai_xingzhuang) {
		this.shidai_xingzhuang = shidai_xingzhuang;
	}
	public String getShidai_xinshengwu() {
		return shidai_xinshengwu;
	}
	public void setShidai_xinshengwu(String shidai_xinshengwu) {
		this.shidai_xinshengwu = shidai_xinshengwu;
	}
	public String getShidai_yanse() {
		return shidai_yanse;
	}
	public void setShidai_yanse(String shidai_yanse) {
		this.shidai_yanse = shidai_yanse;
	}
	public String getWaibi_anbi() {
		return waibi_anbi;
	}
	public void setWaibi_anbi(String waibi_anbi) {
		this.waibi_anbi = waibi_anbi;
	}
	public String getWaibi_pifu() {
		return waibi_pifu;
	}
	public void setWaibi_pifu(String waibi_pifu) {
		this.waibi_pifu = waibi_pifu;
	}
	public String getWaibi_waiqu() {
		return waibi_waiqu;
	}
	public void setWaibi_waiqu(String waibi_waiqu) {
		this.waibi_waiqu = waibi_waiqu;
	}
	public String getWaierdao_biaomian() {
		return waierdao_biaomian;
	}
	public void setWaierdao_biaomian(String waierdao_biaomian) {
		this.waierdao_biaomian = waierdao_biaomian;
	}
	public String getWaierdao_chongxue() {
		return waierdao_chongxue;
	}
	public void setWaierdao_chongxue(String waierdao_chongxue) {
		this.waierdao_chongxue = waierdao_chongxue;
	}
	public int getWaierdao_daxiao() {
		return waierdao_daxiao;
	}
	public void setWaierdao_daxiao(int waierdao_daxiao) {
		this.waierdao_daxiao = waierdao_daxiao;
	}
	public String getWaierdao_fenbiwu() {
		return waierdao_fenbiwu;
	}
	public void setWaierdao_fenbiwu(String waierdao_fenbiwu) {
		this.waierdao_fenbiwu = waierdao_fenbiwu;
	}
	public String getWaierdao_jiezhong() {
		return waierdao_jiezhong;
	}
	public void setWaierdao_jiezhong(String waierdao_jiezhong) {
		this.waierdao_jiezhong = waierdao_jiezhong;
	}
	public String getWaierdao_loukou() {
		return waierdao_loukou;
	}
	public void setWaierdao_loukou(String waierdao_loukou) {
		this.waierdao_loukou = waierdao_loukou;
	}
	public String getWaierdao_other() {
		return waierdao_other;
	}
	public void setWaierdao_other(String waierdao_other) {
		this.waierdao_other = waierdao_other;
	}
	public String getWaierdao_qiwei() {
		return waierdao_qiwei;
	}
	public void setWaierdao_qiwei(String waierdao_qiwei) {
		this.waierdao_qiwei = waierdao_qiwei;
	}
	public String getWaierdao_shuizhong() {
		return waierdao_shuizhong;
	}
	public void setWaierdao_shuizhong(String waierdao_shuizhong) {
		this.waierdao_shuizhong = waierdao_shuizhong;
	}
	public String getWaierdao_xiazhai() {
		return waierdao_xiazhai;
	}
	public void setWaierdao_xiazhai(String waierdao_xiazhai) {
		this.waierdao_xiazhai = waierdao_xiazhai;
	}
	public String getWaierdao_zhongwu() {
		return waierdao_zhongwu;
	}
	public void setWaierdao_zhongwu(String waierdao_zhongwu) {
		this.waierdao_zhongwu = waierdao_zhongwu;
	}
	public String getYanyinwo_biaomian() {
		return yanyinwo_biaomian;
	}
	public void setYanyinwo_biaomian(String yanyinwo_biaomian) {
		this.yanyinwo_biaomian = yanyinwo_biaomian;
	}
	public String getYanyinwo_chongxue() {
		return yanyinwo_chongxue;
	}
	public void setYanyinwo_chongxue(String yanyinwo_chongxue) {
		this.yanyinwo_chongxue = yanyinwo_chongxue;
	}
	public String getYanyinwo_nianmo() {
		return yanyinwo_nianmo;
	}
	public void setYanyinwo_nianmo(String yanyinwo_nianmo) {
		this.yanyinwo_nianmo = yanyinwo_nianmo;
	}
	public String getYanyinwo_shuizhong() {
		return yanyinwo_shuizhong;
	}
	public void setYanyinwo_shuizhong(String yanyinwo_shuizhong) {
		this.yanyinwo_shuizhong = yanyinwo_shuizhong;
	}
	public String getYanyinwo_xingzhuang() {
		return yanyinwo_xingzhuang;
	}
	public void setYanyinwo_xingzhuang(String yanyinwo_xingzhuang) {
		this.yanyinwo_xingzhuang = yanyinwo_xingzhuang;
	}
	public String getYanyinwo_xinshengwu() {
		return yanyinwo_xinshengwu;
	}
	public void setYanyinwo_xinshengwu(String yanyinwo_xinshengwu) {
		this.yanyinwo_xinshengwu = yanyinwo_xinshengwu;
	}
	public String getYanyinwo_yanse() {
		return yanyinwo_yanse;
	}
	public void setYanyinwo_yanse(String yanyinwo_yanse) {
		this.yanyinwo_yanse = yanyinwo_yanse;
	}
	public String getYggyz_biaomian() {
		return yggyz_biaomian;
	}
	public void setYggyz_biaomian(String yggyz_biaomian) {
		this.yggyz_biaomian = yggyz_biaomian;
	}
	public String getYggyz_chongxue() {
		return yggyz_chongxue;
	}
	public void setYggyz_chongxue(String yggyz_chongxue) {
		this.yggyz_chongxue = yggyz_chongxue;
	}
	public String getYggyz_nianmo() {
		return yggyz_nianmo;
	}
	public void setYggyz_nianmo(String yggyz_nianmo) {
		this.yggyz_nianmo = yggyz_nianmo;
	}
	public String getYggyz_shuizhong() {
		return yggyz_shuizhong;
	}
	public void setYggyz_shuizhong(String yggyz_shuizhong) {
		this.yggyz_shuizhong = yggyz_shuizhong;
	}
	public String getYggyz_xingzhuang() {
		return yggyz_xingzhuang;
	}
	public void setYggyz_xingzhuang(String yggyz_xingzhuang) {
		this.yggyz_xingzhuang = yggyz_xingzhuang;
	}
	public String getYggyz_xinshengwu() {
		return yggyz_xinshengwu;
	}
	public void setYggyz_xinshengwu(String yggyz_xinshengwu) {
		this.yggyz_xinshengwu = yggyz_xinshengwu;
	}
	public String getYggyz_yanse() {
		return yggyz_yanse;
	}
	public void setYggyz_yanse(String yggyz_yanse) {
		this.yggyz_yanse = yggyz_yanse;
	}
	public String getYhb_cesuo() {
		return yhb_cesuo;
	}
	public void setYhb_cesuo(String yhb_cesuo) {
		this.yhb_cesuo = yhb_cesuo;
	}
	public String getYhb_chongxue() {
		return yhb_chongxue;
	}
	public void setYhb_chongxue(String yhb_chongxue) {
		this.yhb_chongxue = yhb_chongxue;
	}
	public String getYhb_linba() {
		return yhb_linba;
	}
	public void setYhb_linba(String yhb_linba) {
		this.yhb_linba = yhb_linba;
	}
	public String getYhb_nianmo() {
		return yhb_nianmo;
	}
	public void setYhb_nianmo(String yhb_nianmo) {
		this.yhb_nianmo = yhb_nianmo;
	}
	public String getGumo_other() {
		return gumo_other;
	}
	public void setGumo_other(String gumo_other) {
		this.gumo_other = gumo_other;
	}
	public String getBiqian_other() {
		return biqian_other;
	}
	public void setBiqian_other(String biqian_other) {
		this.biqian_other = biqian_other;
	}
	public String getBijia_other() {
		return bijia_other;
	}
	public void setBijia_other(String bijia_other) {
		this.bijia_other = bijia_other;
	}
	public String getBizhongge_other() {
		return bizhongge_other;
	}
	public void setBizhongge_other(String bizhongge_other) {
		this.bizhongge_other = bizhongge_other;
	}
	public String getBiyanbu_other() {
		return biyanbu_other;
	}
	public void setBiyanbu_other(String biyanbu_other) {
		this.biyanbu_other = biyanbu_other;
	}
	public String getRuane_other() {
		return ruane_other;
	}
	public void setRuane_other(String ruane_other) {
		this.ruane_other = ruane_other;
	}
	public String getShengdai_other() {
		return shengdai_other;
	}
	public void setShengdai_other(String shengdai_other) {
		this.shengdai_other = shengdai_other;
	}
	public String getHoushi_other() {
		return houshi_other;
	}
	public void setHoushi_other(String houshi_other) {
		this.houshi_other = houshi_other;
	}
	public String getShaozhuang_other() {
		return shaozhuang_other;
	}
	public void setShaozhuang_other(String shaozhuang_other) {
		this.shaozhuang_other = shaozhuang_other;
	}
	public String getHuanhouxi_other() {
		return huanhouxi_other;
	}
	public void setHuanhouxi_other(String huanhouxi_other) {
		this.huanhouxi_other = huanhouxi_other;
	}
	public String getZuihou_other() {
		return zuihou_other;
	}
	public void setZuihou_other(String zuihou_other) {
		this.zuihou_other = zuihou_other;
	}
}