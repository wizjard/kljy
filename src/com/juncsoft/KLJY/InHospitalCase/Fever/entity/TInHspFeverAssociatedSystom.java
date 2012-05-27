package com.juncsoft.KLJY.InHospitalCase.Fever.entity;

/**
 * TInHspFeverAssociatedSystom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TInHspFeverAssociatedSystom implements java.io.Serializable {

	// Fields

	private Long id;
	private Long caseId;
	private Integer sideXiaohua;
	private String sideXiaohua0;
	private Integer sideXunhuan;
	private String sideXunhuan0;
	private Integer sideHuxi;
	private String sideHuxi0;
	private Integer sideMiniao;
	private String sideMiniao0;
	private Integer sideXueye;
	private String sideXueye0;
	private Integer sideNei;
	private String sideNei0;
	private Integer sideShenjing;
	private String sideShenjing0;
	private Integer sideJirou;
	private String sideJirou0;
	private Integer sideFengshi;
	private String sideFengshi0;
	private Integer sideQita;
	private String sideQita0;
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
	public TInHspFeverAssociatedSystom() {
	}

	/** minimal constructor */
	public TInHspFeverAssociatedSystom(Long id, Long caseId) {
		this.id = id;
		this.caseId = caseId;
	}

	/** full constructor */
	public TInHspFeverAssociatedSystom(Long id, Long caseId,
			Integer sideXiaohua, String sideXiaohua0, Integer sideXunhuan,
			String sideXunhuan0, Integer sideHuxi, String sideHuxi0,
			Integer sideMiniao, String sideMiniao0, Integer sideXueye,
			String sideXueye0, Integer sideNei, String sideNei0,
			Integer sideShenjing, String sideShenjing0, Integer sideJirou,
			String sideJirou0, Integer sideFengshi, String sideFengshi0,
			Integer sideQita, String sideQita0) {
		this.id = id;
		this.caseId = caseId;
		this.sideXiaohua = sideXiaohua;
		this.sideXiaohua0 = sideXiaohua0;
		this.sideXunhuan = sideXunhuan;
		this.sideXunhuan0 = sideXunhuan0;
		this.sideHuxi = sideHuxi;
		this.sideHuxi0 = sideHuxi0;
		this.sideMiniao = sideMiniao;
		this.sideMiniao0 = sideMiniao0;
		this.sideXueye = sideXueye;
		this.sideXueye0 = sideXueye0;
		this.sideNei = sideNei;
		this.sideNei0 = sideNei0;
		this.sideShenjing = sideShenjing;
		this.sideShenjing0 = sideShenjing0;
		this.sideJirou = sideJirou;
		this.sideJirou0 = sideJirou0;
		this.sideFengshi = sideFengshi;
		this.sideFengshi0 = sideFengshi0;
		this.sideQita = sideQita;
		this.sideQita0 = sideQita0;
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

	public Integer getSideXiaohua() {
		return this.sideXiaohua;
	}

	public void setSideXiaohua(Integer sideXiaohua) {
		this.sideXiaohua = sideXiaohua;
	}

	public String getSideXiaohua0() {
		return this.sideXiaohua0;
	}

	public void setSideXiaohua0(String sideXiaohua0) {
		this.sideXiaohua0 = sideXiaohua0;
	}

	public Integer getSideXunhuan() {
		return this.sideXunhuan;
	}

	public void setSideXunhuan(Integer sideXunhuan) {
		this.sideXunhuan = sideXunhuan;
	}

	public String getSideXunhuan0() {
		return this.sideXunhuan0;
	}

	public void setSideXunhuan0(String sideXunhuan0) {
		this.sideXunhuan0 = sideXunhuan0;
	}

	public Integer getSideHuxi() {
		return this.sideHuxi;
	}

	public void setSideHuxi(Integer sideHuxi) {
		this.sideHuxi = sideHuxi;
	}

	public String getSideHuxi0() {
		return this.sideHuxi0;
	}

	public void setSideHuxi0(String sideHuxi0) {
		this.sideHuxi0 = sideHuxi0;
	}

	public Integer getSideMiniao() {
		return this.sideMiniao;
	}

	public void setSideMiniao(Integer sideMiniao) {
		this.sideMiniao = sideMiniao;
	}

	public String getSideMiniao0() {
		return this.sideMiniao0;
	}

	public void setSideMiniao0(String sideMiniao0) {
		this.sideMiniao0 = sideMiniao0;
	}

	public Integer getSideXueye() {
		return this.sideXueye;
	}

	public void setSideXueye(Integer sideXueye) {
		this.sideXueye = sideXueye;
	}

	public String getSideXueye0() {
		return this.sideXueye0;
	}

	public void setSideXueye0(String sideXueye0) {
		this.sideXueye0 = sideXueye0;
	}

	public Integer getSideNei() {
		return this.sideNei;
	}

	public void setSideNei(Integer sideNei) {
		this.sideNei = sideNei;
	}

	public String getSideNei0() {
		return this.sideNei0;
	}

	public void setSideNei0(String sideNei0) {
		this.sideNei0 = sideNei0;
	}

	public Integer getSideShenjing() {
		return this.sideShenjing;
	}

	public void setSideShenjing(Integer sideShenjing) {
		this.sideShenjing = sideShenjing;
	}

	public String getSideShenjing0() {
		return this.sideShenjing0;
	}

	public void setSideShenjing0(String sideShenjing0) {
		this.sideShenjing0 = sideShenjing0;
	}

	public Integer getSideJirou() {
		return this.sideJirou;
	}

	public void setSideJirou(Integer sideJirou) {
		this.sideJirou = sideJirou;
	}

	public String getSideJirou0() {
		return this.sideJirou0;
	}

	public void setSideJirou0(String sideJirou0) {
		this.sideJirou0 = sideJirou0;
	}

	public Integer getSideFengshi() {
		return this.sideFengshi;
	}

	public void setSideFengshi(Integer sideFengshi) {
		this.sideFengshi = sideFengshi;
	}

	public String getSideFengshi0() {
		return this.sideFengshi0;
	}

	public void setSideFengshi0(String sideFengshi0) {
		this.sideFengshi0 = sideFengshi0;
	}

	public Integer getSideQita() {
		return this.sideQita;
	}

	public void setSideQita(Integer sideQita) {
		this.sideQita = sideQita;
	}

	public String getSideQita0() {
		return this.sideQita0;
	}

	public void setSideQita0(String sideQita0) {
		this.sideQita0 = sideQita0;
	}

}