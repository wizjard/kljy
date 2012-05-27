package com.juncsoft.KLJY.InHospitalCase.Fever.entity;

/**
 * TInHspFeverOtherSystom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TInHspFeverOtherSystom implements java.io.Serializable {

	// Fields

	private Long id;
	private Long caseId;
	private Integer pqsbs;
	private Integer ppfsy;
	private Integer ppfsyPosi;
	private Integer ppfsyDegree;
	private Integer ppqqk;
	private Integer pfbbk;
	private String pfbbkPosi;
	private String pfbbkSize;
	private Integer pfbbkYd;
	private Integer pfbbkZhd;
	private Integer pfbbkYt;
	private Integer pfbbkZhl;
	private Integer pgqbs;
	private Integer ppqbs;
	private Integer ptykn;
	private Integer ptyknDegree;
	private Integer pchx;
	private String pchxDesc;
	private Integer pxggb;
	private String pxggbDesc;
	private Integer pxwyc;
	private String pxwycDesc;
	private Integer phj;
	private String phjDesc;
	private Integer pjsl;
	private Integer pdxl;
	private String otherPosit;
	private String oldContent;
	// Constructors

	public String getOldContent() {
		return oldContent;
	}

	public void setOldContent(String oldContent) {
		this.oldContent = oldContent;
	}
	// Constructors

	/** default constructor */
	public TInHspFeverOtherSystom() {
	}

	/** minimal constructor */
	public TInHspFeverOtherSystom(Long id, Long caseId) {
		this.id = id;
		this.caseId = caseId;
	}

	/** full constructor */
	public TInHspFeverOtherSystom(Long id, Long caseId, Integer pqsbs,
			Integer ppfsy, Integer ppfsyPosi, Integer ppfsyDegree,
			Integer ppqqk, Integer pfbbk, String pfbbkPosi, String pfbbkSize,
			Integer pfbbkYd, Integer pfbbkZhd, Integer pfbbkYt,
			Integer pfbbkZhl, Integer pgqbs, Integer ppqbs, Integer ptykn,
			Integer ptyknDegree, Integer pchx, String pchxDesc, Integer pxggb,
			String pxggbDesc, Integer pxwyc, String pxwycDesc, Integer phj,
			String phjDesc, Integer pjsl, Integer pdxl, String otherPosit) {
		this.id = id;
		this.caseId = caseId;
		this.pqsbs = pqsbs;
		this.ppfsy = ppfsy;
		this.ppfsyPosi = ppfsyPosi;
		this.ppfsyDegree = ppfsyDegree;
		this.ppqqk = ppqqk;
		this.pfbbk = pfbbk;
		this.pfbbkPosi = pfbbkPosi;
		this.pfbbkSize = pfbbkSize;
		this.pfbbkYd = pfbbkYd;
		this.pfbbkZhd = pfbbkZhd;
		this.pfbbkYt = pfbbkYt;
		this.pfbbkZhl = pfbbkZhl;
		this.pgqbs = pgqbs;
		this.ppqbs = ppqbs;
		this.ptykn = ptykn;
		this.ptyknDegree = ptyknDegree;
		this.pchx = pchx;
		this.pchxDesc = pchxDesc;
		this.pxggb = pxggb;
		this.pxggbDesc = pxggbDesc;
		this.pxwyc = pxwyc;
		this.pxwycDesc = pxwycDesc;
		this.phj = phj;
		this.phjDesc = phjDesc;
		this.pjsl = pjsl;
		this.pdxl = pdxl;
		this.otherPosit = otherPosit;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCaseId() {
		return this.caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public Integer getPqsbs() {
		return this.pqsbs;
	}

	public void setPqsbs(Integer pqsbs) {
		this.pqsbs = pqsbs;
	}

	public Integer getPpfsy() {
		return this.ppfsy;
	}

	public void setPpfsy(Integer ppfsy) {
		this.ppfsy = ppfsy;
	}

	public Integer getPpfsyPosi() {
		return this.ppfsyPosi;
	}

	public void setPpfsyPosi(Integer ppfsyPosi) {
		this.ppfsyPosi = ppfsyPosi;
	}

	public Integer getPpfsyDegree() {
		return this.ppfsyDegree;
	}

	public void setPpfsyDegree(Integer ppfsyDegree) {
		this.ppfsyDegree = ppfsyDegree;
	}

	public Integer getPpqqk() {
		return this.ppqqk;
	}

	public void setPpqqk(Integer ppqqk) {
		this.ppqqk = ppqqk;
	}

	public Integer getPfbbk() {
		return this.pfbbk;
	}

	public void setPfbbk(Integer pfbbk) {
		this.pfbbk = pfbbk;
	}

	public String getPfbbkPosi() {
		return this.pfbbkPosi;
	}

	public void setPfbbkPosi(String pfbbkPosi) {
		this.pfbbkPosi = pfbbkPosi;
	}

	public String getPfbbkSize() {
		return this.pfbbkSize;
	}

	public void setPfbbkSize(String pfbbkSize) {
		this.pfbbkSize = pfbbkSize;
	}

	public Integer getPfbbkYd() {
		return this.pfbbkYd;
	}

	public void setPfbbkYd(Integer pfbbkYd) {
		this.pfbbkYd = pfbbkYd;
	}

	public Integer getPfbbkZhd() {
		return this.pfbbkZhd;
	}

	public void setPfbbkZhd(Integer pfbbkZhd) {
		this.pfbbkZhd = pfbbkZhd;
	}

	public Integer getPfbbkYt() {
		return this.pfbbkYt;
	}

	public void setPfbbkYt(Integer pfbbkYt) {
		this.pfbbkYt = pfbbkYt;
	}

	public Integer getPfbbkZhl() {
		return this.pfbbkZhl;
	}

	public void setPfbbkZhl(Integer pfbbkZhl) {
		this.pfbbkZhl = pfbbkZhl;
	}

	public Integer getPgqbs() {
		return this.pgqbs;
	}

	public void setPgqbs(Integer pgqbs) {
		this.pgqbs = pgqbs;
	}

	public Integer getPpqbs() {
		return this.ppqbs;
	}

	public void setPpqbs(Integer ppqbs) {
		this.ppqbs = ppqbs;
	}

	public Integer getPtykn() {
		return this.ptykn;
	}

	public void setPtykn(Integer ptykn) {
		this.ptykn = ptykn;
	}

	public Integer getPtyknDegree() {
		return this.ptyknDegree;
	}

	public void setPtyknDegree(Integer ptyknDegree) {
		this.ptyknDegree = ptyknDegree;
	}

	public Integer getPchx() {
		return this.pchx;
	}

	public void setPchx(Integer pchx) {
		this.pchx = pchx;
	}

	public String getPchxDesc() {
		return this.pchxDesc;
	}

	public void setPchxDesc(String pchxDesc) {
		this.pchxDesc = pchxDesc;
	}

	public Integer getPxggb() {
		return this.pxggb;
	}

	public void setPxggb(Integer pxggb) {
		this.pxggb = pxggb;
	}

	public String getPxggbDesc() {
		return this.pxggbDesc;
	}

	public void setPxggbDesc(String pxggbDesc) {
		this.pxggbDesc = pxggbDesc;
	}

	public Integer getPxwyc() {
		return this.pxwyc;
	}

	public void setPxwyc(Integer pxwyc) {
		this.pxwyc = pxwyc;
	}

	public String getPxwycDesc() {
		return this.pxwycDesc;
	}

	public void setPxwycDesc(String pxwycDesc) {
		this.pxwycDesc = pxwycDesc;
	}

	public Integer getPhj() {
		return this.phj;
	}

	public void setPhj(Integer phj) {
		this.phj = phj;
	}

	public String getPhjDesc() {
		return this.phjDesc;
	}

	public void setPhjDesc(String phjDesc) {
		this.phjDesc = phjDesc;
	}

	public Integer getPjsl() {
		return this.pjsl;
	}

	public void setPjsl(Integer pjsl) {
		this.pjsl = pjsl;
	}

	public Integer getPdxl() {
		return this.pdxl;
	}

	public void setPdxl(Integer pdxl) {
		this.pdxl = pdxl;
	}

	public String getOtherPosit() {
		return this.otherPosit;
	}

	public void setOtherPosit(String otherPosit) {
		this.otherPosit = otherPosit;
	}

}