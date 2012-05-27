package com.juncsoft.KLJY.InHospitalCase.Fever.entity;

/**
 * TInHspFeverMedicineFeverHistoryIllness entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TInHspFeverMedicineFeverHistoryIllness implements
		java.io.Serializable {

	// Fields

	private Long id;
	private Long caseId;
	private Integer fare;
	private Integer fareType;
	private Integer fareXing;
	private String fareXingZdy;
	private Integer fareTemperature;
	private String temStart;
	private String temEnd;
	private String temMax;
	private String features;
	private String show;
	private Integer weihan;
	private Integer hanzhan;
	private Integer hanzhanContinue;
	private Integer sweat;
	private Integer daohan;
	private String daohanShowTime;
	private Integer fali;
	private Integer faliShowTime;
	private Integer qsbs;
	private Integer qsbsStatue;
	private Integer pfex;
	private String pfexParts;
	private String pfexPartsZidingyi;
	private Integer pfexColor;
	private Integer pfexColorZidingyi;
	private Integer prexPizhen;
	private String prexParts;
	private String prexColor;
	private String prexSize;
	private Integer prexStatue;
	private String prexStatueZidingyi;
	private Integer prexYazhi;
	private Integer prexZhenfu;
	private Integer prexBansui;
	private String prexSort;
	private String prexGuilv;
	private String prexOther;
	private Integer bisai;
	private Integer bisaiStatue1;
	private Integer bisaiStatue2;
	private Integer liuti;
	private Integer liutiStatue;
	private Integer liulei;
	private Integer liuleiStatue;
	private Integer yanhou;
	private Integer yanhouStatue;
	private Integer kesou;
	private Integer kesouFeature;
	private Integer kesouNature;
	private Integer kesouLevel;
	private Integer kesouSound;
	private Integer kesouTime;
	private Integer ketan;
	private Integer ketanPd;
	private Integer kesouVolume;
	private String kesouVolumeValue1;
	private String kesouVolumeValue2;
	private String kesouVolumeValue3;
	private Integer kesouColor;
	private String kesouColorValue;
	private Integer kesouXz;
	private String kesouXzValue;
	private Integer kesouEx;
	private Integer kesouKechu;
	private Integer xinji;
	private Integer xinjiBecause;
	private String xinjiBecauseValue;
	private String xinjiBecauseYy;
	private Integer xinjiLevel;
	private Integer xinjiPd;
	private String continueTime;
	private String xinjiPdContinueTime;
	private Integer huxikunnan;
	private Integer huxikunnanBecause;
	private String huxikunnanBecauseValue1;
	private Integer huxikunnanFeatures;
	private Integer huxikunnanLevel;
	private String huxikunnanLevelValue;
	private Integer huxikunnanType;
	private Integer huxikunnanTime;
	private String yinsu1;
	private String yinsu2;
	private String yinsu3;
	private Integer xiongtong;
	private Integer xiongtongBecause;
	private String xiongtongBecauseValue;
	private Integer xiongtongLevel;
	private String xiongtongParts;
	private String xiongtongPartsZdy;
	private Integer xiongtongFeatures;
	private String xiongtongFeaturesZdy;
	private Integer xiongtongLocation;
	private Integer xiongtonFs;
	private Integer xiongtongFsParts;
	private String xiongtongFsPartsZdy;
	private Integer xiongtongFsTd;
	private String xiongtongFsTdZdy;
	private String xiongtongFsTime;
	private String xiongtongFsTimeValue;
	private String xtYinsu2;
	private String xtYinsu1;
	private Integer syjt;
	private Integer syjtLevel;
	private Integer syjtDesc;
	private String slPer;
	private Integer exin;
	private Integer exinLevel;
	private Integer outu;
	private Integer outuLevel;
	private Integer outuThing;
	private String outuNum;
	private String outuC;
	private Integer ft;
	private Integer ftBecause;
	private Integer ftBecauseParts;
	private String ftBecausePartsZdy;
	private Integer ftFeature;
	private String ftFeatureZdy;
	private String ftPart;
	private Integer ftXz;
	private String ftXzZdy;
	private Integer ftLevel;
	private Integer ftLocation;
	private Integer ftFs;
	private String ftFsParts;
	private String ftShow;
	private String ftYinsu;
	private String ftDesc;
	private String ftGuanxi;
	private Integer fxjchange;
	private Integer fxjchangeFx;
	private String fxjchangeFxDay;
	private String fxjchangeFxNum;
	private String fxjchangeBmDay;
	private String fxjchangeBmNum;
	private Integer fxBmHuhuan;
	private String fxBmSum;
	private String fxBmNum;
	private Integer fxBmColor;
	private Integer fxBmXz;
	private Integer fxBmZz;
	private Integer fxBmEx;
	private Integer fxBmExParts;
	private Integer fxBmThing;
	private Integer niaopin;
	private Integer niaopinLevel;
	private Integer niaopinBecause;
	private String niaopinBecauseZdy;
	private Integer niaopiPl;
	private String niaopinPlZdy;
	private Integer niaoji;
	private Integer niaotong;
	private Integer niaotongLevel;
	private Integer niaotongTime;
	private Integer niaotongParts;
	private Integer nyChange;
	private Integer nyChangeChange;
	private String nyChangeChangeZdy;
	private Integer nyChangeEx;
	private Integer nyChangeColor;
	private Integer nyChangeDu;
	private Integer nyChangeWeidaoEx;
	private Integer nyChangeWeidaoEx1;
	private Integer sz;
	private Integer szShibing;
	private Integer szParts;
	private String szPartsZdy;
	private Integer szDuichen;
	private Integer szAotuxing;
	private Integer szLevel;
	private String szMingxian;
	private String szJianqing;
	private String szGuanxi;
	private Integer tt;
	private Integer ttBecause;
	private Integer ttFeatures;
	private String ttFeaturesZdy;
	private Integer ttPindu;
	private Integer ttLevel;
	private Integer ttParts;
	private Integer ttFeature;
	private String ttFeatureZdy;
	private String ttJianqing;
	private String ttGuanxi;
	private String ttGuilv;
	private String ttYinsu2;
	private String ttYinsu1;
	private Integer szChange;
	private Integer szChangeLevel;
	private String szChangeLevelZdy;
	private Integer szChangeAnswer;
	private Integer szChangeXgChange;
	private String szChangeXgChangeZdy;
	private Integer szChangeXwEx;
	private String szChangeXwExZdy;
	private Integer szChangeCompute;
	private String szChangeComputeZdy;
	private Integer szChangeOrientation;
	private String szChangeOrientationZdy;
	private Integer szChangeHuanjue;
	private Integer szChangeHuanjue1;
	private String szChangeHuanjue1zdy;
	private Integer ccJingjue;
	private Integer ccJingjueStatue;
	private Integer ccJingjueParts;
	private Integer ccJingjueBansui;
	private String ccJingjueBansuiZdy;
	private Integer ccJingjueTime;
	private String ccJingjueTimeValue;
	private Integer yaotong;
	private Integer yaotongBecause;
	private String yaotongBecauseZdy;
	private Integer yaotongStatue;
	private String yaotongStatueZdy;
	private Integer yaotongLevel;
	private Integer yaotongParts;
	private String yaotongPartsZdy;
	private Integer yaotongFeature;
	private String yaotongFeatureZdy;
	private Integer yaotongWeizhi;
	private Integer yaotongStatues;
	private Integer yaotongBansui;
	private Integer yaotongBansui1;
	private String yaotongBansui1zdy;
	private String yaotongJiazhong;
	private String yaotongJianqing;
	private Integer jrgjt;
	private Integer jrgjtBecause;
	private String jrgjtBecauseZdy;
	private Integer jrgjtStatue;
	private String jrgjtStatueZdy;
	private Integer jrgjtLevel;
	private String jrgjtLevelValue;
	private Integer jrgjtParts;
	private String jrgjtPartsValue;
	private Integer jrgjtFeature;
	private String jrgjtFeatureZdy;
	private Integer jrgjtWeizhi;
	private String jrgjtWeizhiZdy;
	private Integer jrgjtStatues;
	private Integer jrgjtBansui;
	private Integer jrgjtBansui1;
	private String jrgjtBansui1zdy;
	private String jrgjtJiazhong;
	private String jrgjtJianqing;
	private String oldContent;
	private String newContent;
	private String dateTime;

	// Constructors

	/** default constructor */
	public TInHspFeverMedicineFeverHistoryIllness() {
	}

	/** minimal constructor */
	public TInHspFeverMedicineFeverHistoryIllness(Long id, Long caseId) {
		this.id = id;
		this.caseId = caseId;
	}

	/** full constructor */
	public TInHspFeverMedicineFeverHistoryIllness(Long id, Long caseId,
			Integer fare, Integer fareType, Integer fareXing,
			String fareXingZdy, Integer fareTemperature, String temStart,
			String temEnd, String temMax, String features, String show,
			Integer weihan, Integer hanzhan, Integer hanzhanContinue,
			Integer sweat, Integer daohan, String daohanShowTime, Integer fali,
			Integer faliShowTime, Integer qsbs, Integer qsbsStatue,
			Integer pfex, String pfexParts, String pfexPartsZidingyi,
			Integer pfexColor, Integer pfexColorZidingyi, Integer prexPizhen,
			String prexParts, String prexColor, String prexSize,
			Integer prexStatue, String prexStatueZidingyi, Integer prexYazhi,
			Integer prexZhenfu, Integer prexBansui, String prexSort,
			String prexGuilv, String prexOther, Integer bisai,
			Integer bisaiStatue1, Integer bisaiStatue2, Integer liuti,
			Integer liutiStatue, Integer liulei, Integer liuleiStatue,
			Integer yanhou, Integer yanhouStatue, Integer kesou,
			Integer kesouFeature, Integer kesouNature, Integer kesouLevel,
			Integer kesouSound, Integer kesouTime, Integer ketan,
			Integer ketanPd, Integer kesouVolume, String kesouVolumeValue1,
			String kesouVolumeValue2, String kesouVolumeValue3,
			Integer kesouColor, String kesouColorValue, Integer kesouXz,
			String kesouXzValue, Integer kesouEx, Integer kesouKechu,
			Integer xinji, Integer xinjiBecause, String xinjiBecauseValue,
			String xinjiBecauseYy, Integer xinjiLevel, Integer xinjiPd,
			String continueTime, String xinjiPdContinueTime,
			Integer huxikunnan, Integer huxikunnanBecause,
			String huxikunnanBecauseValue1, Integer huxikunnanFeatures,
			Integer huxikunnanLevel, String huxikunnanLevelValue,
			Integer huxikunnanType, Integer huxikunnanTime, String yinsu1,
			String yinsu2, String yinsu3, Integer xiongtong,
			Integer xiongtongBecause, String xiongtongBecauseValue,
			Integer xiongtongLevel, String xiongtongParts,
			String xiongtongPartsZdy, Integer xiongtongFeatures,
			String xiongtongFeaturesZdy, Integer xiongtongLocation,
			Integer xiongtonFs, Integer xiongtongFsParts,
			String xiongtongFsPartsZdy, Integer xiongtongFsTd,
			String xiongtongFsTdZdy, String xiongtongFsTime,
			String xiongtongFsTimeValue, String xtYinsu2, String xtYinsu1,
			Integer syjt, Integer syjtLevel, Integer syjtDesc, String slPer,
			Integer exin, Integer exinLevel, Integer outu, Integer outuLevel,
			Integer outuThing, String outuNum, String outuC, Integer ft,
			Integer ftBecause, Integer ftBecauseParts,
			String ftBecausePartsZdy, Integer ftFeature, String ftFeatureZdy,
			String ftPart, Integer ftXz, String ftXzZdy, Integer ftLevel,
			Integer ftLocation, Integer ftFs, String ftFsParts, String ftShow,
			String ftYinsu, String ftDesc, String ftGuanxi, Integer fxjchange,
			Integer fxjchangeFx, String fxjchangeFxDay, String fxjchangeFxNum,
			String fxjchangeBmDay, String fxjchangeBmNum, Integer fxBmHuhuan,
			String fxBmSum, String fxBmNum, Integer fxBmColor, Integer fxBmXz,
			Integer fxBmZz, Integer fxBmEx, Integer fxBmExParts,
			Integer fxBmThing, Integer niaopin, Integer niaopinLevel,
			Integer niaopinBecause, String niaopinBecauseZdy, Integer niaopiPl,
			String niaopinPlZdy, Integer niaoji, Integer niaotong,
			Integer niaotongLevel, Integer niaotongTime, Integer niaotongParts,
			Integer nyChange, Integer nyChangeChange, String nyChangeChangeZdy,
			Integer nyChangeEx, Integer nyChangeColor, Integer nyChangeDu,
			Integer nyChangeWeidaoEx, Integer nyChangeWeidaoEx1, Integer sz,
			Integer szShibing, Integer szParts, String szPartsZdy,
			Integer szDuichen, Integer szAotuxing, Integer szLevel,
			String szMingxian, String szJianqing, String szGuanxi, Integer tt,
			Integer ttBecause, Integer ttFeatures, String ttFeaturesZdy,
			Integer ttPindu, Integer ttLevel, Integer ttParts,
			Integer ttFeature, String ttFeatureZdy, String ttJianqing,
			String ttGuanxi, String ttGuilv, String ttYinsu2, String ttYinsu1,
			Integer szChange, Integer szChangeLevel, String szChangeLevelZdy,
			Integer szChangeAnswer, Integer szChangeXgChange,
			String szChangeXgChangeZdy, Integer szChangeXwEx,
			String szChangeXwExZdy, Integer szChangeCompute,
			String szChangeComputeZdy, Integer szChangeOrientation,
			String szChangeOrientationZdy, Integer szChangeHuanjue,
			Integer szChangeHuanjue1, String szChangeHuanjue1zdy,
			Integer ccJingjue, Integer ccJingjueStatue, Integer ccJingjueParts,
			Integer ccJingjueBansui, String ccJingjueBansuiZdy,
			Integer ccJingjueTime, String ccJingjueTimeValue, Integer yaotong,
			Integer yaotongBecause, String yaotongBecauseZdy,
			Integer yaotongStatue, String yaotongStatueZdy,
			Integer yaotongLevel, Integer yaotongParts, String yaotongPartsZdy,
			Integer yaotongFeature, String yaotongFeatureZdy,
			Integer yaotongWeizhi, Integer yaotongStatues,
			Integer yaotongBansui, Integer yaotongBansui1,
			String yaotongBansui1zdy, String yaotongJiazhong,
			String yaotongJianqing, Integer jrgjt, Integer jrgjtBecause,
			String jrgjtBecauseZdy, Integer jrgjtStatue, String jrgjtStatueZdy,
			Integer jrgjtLevel, String jrgjtLevelValue, Integer jrgjtParts,
			String jrgjtPartsValue, Integer jrgjtFeature,
			String jrgjtFeatureZdy, Integer jrgjtWeizhi, String jrgjtWeizhiZdy,
			Integer jrgjtStatues, Integer jrgjtBansui, Integer jrgjtBansui1,
			String jrgjtBansui1zdy, String jrgjtJiazhong, String jrgjtJianqing,
			String oldContent, String newContent, String dateTime) {
		this.id = id;
		this.caseId = caseId;
		this.fare = fare;
		this.fareType = fareType;
		this.fareXing = fareXing;
		this.fareXingZdy = fareXingZdy;
		this.fareTemperature = fareTemperature;
		this.temStart = temStart;
		this.temEnd = temEnd;
		this.temMax = temMax;
		this.features = features;
		this.show = show;
		this.weihan = weihan;
		this.hanzhan = hanzhan;
		this.hanzhanContinue = hanzhanContinue;
		this.sweat = sweat;
		this.daohan = daohan;
		this.daohanShowTime = daohanShowTime;
		this.fali = fali;
		this.faliShowTime = faliShowTime;
		this.qsbs = qsbs;
		this.qsbsStatue = qsbsStatue;
		this.pfex = pfex;
		this.pfexParts = pfexParts;
		this.pfexPartsZidingyi = pfexPartsZidingyi;
		this.pfexColor = pfexColor;
		this.pfexColorZidingyi = pfexColorZidingyi;
		this.prexPizhen = prexPizhen;
		this.prexParts = prexParts;
		this.prexColor = prexColor;
		this.prexSize = prexSize;
		this.prexStatue = prexStatue;
		this.prexStatueZidingyi = prexStatueZidingyi;
		this.prexYazhi = prexYazhi;
		this.prexZhenfu = prexZhenfu;
		this.prexBansui = prexBansui;
		this.prexSort = prexSort;
		this.prexGuilv = prexGuilv;
		this.prexOther = prexOther;
		this.bisai = bisai;
		this.bisaiStatue1 = bisaiStatue1;
		this.bisaiStatue2 = bisaiStatue2;
		this.liuti = liuti;
		this.liutiStatue = liutiStatue;
		this.liulei = liulei;
		this.liuleiStatue = liuleiStatue;
		this.yanhou = yanhou;
		this.yanhouStatue = yanhouStatue;
		this.kesou = kesou;
		this.kesouFeature = kesouFeature;
		this.kesouNature = kesouNature;
		this.kesouLevel = kesouLevel;
		this.kesouSound = kesouSound;
		this.kesouTime = kesouTime;
		this.ketan = ketan;
		this.ketanPd = ketanPd;
		this.kesouVolume = kesouVolume;
		this.kesouVolumeValue1 = kesouVolumeValue1;
		this.kesouVolumeValue2 = kesouVolumeValue2;
		this.kesouVolumeValue3 = kesouVolumeValue3;
		this.kesouColor = kesouColor;
		this.kesouColorValue = kesouColorValue;
		this.kesouXz = kesouXz;
		this.kesouXzValue = kesouXzValue;
		this.kesouEx = kesouEx;
		this.kesouKechu = kesouKechu;
		this.xinji = xinji;
		this.xinjiBecause = xinjiBecause;
		this.xinjiBecauseValue = xinjiBecauseValue;
		this.xinjiBecauseYy = xinjiBecauseYy;
		this.xinjiLevel = xinjiLevel;
		this.xinjiPd = xinjiPd;
		this.continueTime = continueTime;
		this.xinjiPdContinueTime = xinjiPdContinueTime;
		this.huxikunnan = huxikunnan;
		this.huxikunnanBecause = huxikunnanBecause;
		this.huxikunnanBecauseValue1 = huxikunnanBecauseValue1;
		this.huxikunnanFeatures = huxikunnanFeatures;
		this.huxikunnanLevel = huxikunnanLevel;
		this.huxikunnanLevelValue = huxikunnanLevelValue;
		this.huxikunnanType = huxikunnanType;
		this.huxikunnanTime = huxikunnanTime;
		this.yinsu1 = yinsu1;
		this.yinsu2 = yinsu2;
		this.yinsu3 = yinsu3;
		this.xiongtong = xiongtong;
		this.xiongtongBecause = xiongtongBecause;
		this.xiongtongBecauseValue = xiongtongBecauseValue;
		this.xiongtongLevel = xiongtongLevel;
		this.xiongtongParts = xiongtongParts;
		this.xiongtongPartsZdy = xiongtongPartsZdy;
		this.xiongtongFeatures = xiongtongFeatures;
		this.xiongtongFeaturesZdy = xiongtongFeaturesZdy;
		this.xiongtongLocation = xiongtongLocation;
		this.xiongtonFs = xiongtonFs;
		this.xiongtongFsParts = xiongtongFsParts;
		this.xiongtongFsPartsZdy = xiongtongFsPartsZdy;
		this.xiongtongFsTd = xiongtongFsTd;
		this.xiongtongFsTdZdy = xiongtongFsTdZdy;
		this.xiongtongFsTime = xiongtongFsTime;
		this.xiongtongFsTimeValue = xiongtongFsTimeValue;
		this.xtYinsu2 = xtYinsu2;
		this.xtYinsu1 = xtYinsu1;
		this.syjt = syjt;
		this.syjtLevel = syjtLevel;
		this.syjtDesc = syjtDesc;
		this.slPer = slPer;
		this.exin = exin;
		this.exinLevel = exinLevel;
		this.outu = outu;
		this.outuLevel = outuLevel;
		this.outuThing = outuThing;
		this.outuNum = outuNum;
		this.outuC = outuC;
		this.ft = ft;
		this.ftBecause = ftBecause;
		this.ftBecauseParts = ftBecauseParts;
		this.ftBecausePartsZdy = ftBecausePartsZdy;
		this.ftFeature = ftFeature;
		this.ftFeatureZdy = ftFeatureZdy;
		this.ftPart = ftPart;
		this.ftXz = ftXz;
		this.ftXzZdy = ftXzZdy;
		this.ftLevel = ftLevel;
		this.ftLocation = ftLocation;
		this.ftFs = ftFs;
		this.ftFsParts = ftFsParts;
		this.ftShow = ftShow;
		this.ftYinsu = ftYinsu;
		this.ftDesc = ftDesc;
		this.ftGuanxi = ftGuanxi;
		this.fxjchange = fxjchange;
		this.fxjchangeFx = fxjchangeFx;
		this.fxjchangeFxDay = fxjchangeFxDay;
		this.fxjchangeFxNum = fxjchangeFxNum;
		this.fxjchangeBmDay = fxjchangeBmDay;
		this.fxjchangeBmNum = fxjchangeBmNum;
		this.fxBmHuhuan = fxBmHuhuan;
		this.fxBmSum = fxBmSum;
		this.fxBmNum = fxBmNum;
		this.fxBmColor = fxBmColor;
		this.fxBmXz = fxBmXz;
		this.fxBmZz = fxBmZz;
		this.fxBmEx = fxBmEx;
		this.fxBmExParts = fxBmExParts;
		this.fxBmThing = fxBmThing;
		this.niaopin = niaopin;
		this.niaopinLevel = niaopinLevel;
		this.niaopinBecause = niaopinBecause;
		this.niaopinBecauseZdy = niaopinBecauseZdy;
		this.niaopiPl = niaopiPl;
		this.niaopinPlZdy = niaopinPlZdy;
		this.niaoji = niaoji;
		this.niaotong = niaotong;
		this.niaotongLevel = niaotongLevel;
		this.niaotongTime = niaotongTime;
		this.niaotongParts = niaotongParts;
		this.nyChange = nyChange;
		this.nyChangeChange = nyChangeChange;
		this.nyChangeChangeZdy = nyChangeChangeZdy;
		this.nyChangeEx = nyChangeEx;
		this.nyChangeColor = nyChangeColor;
		this.nyChangeDu = nyChangeDu;
		this.nyChangeWeidaoEx = nyChangeWeidaoEx;
		this.nyChangeWeidaoEx1 = nyChangeWeidaoEx1;
		this.sz = sz;
		this.szShibing = szShibing;
		this.szParts = szParts;
		this.szPartsZdy = szPartsZdy;
		this.szDuichen = szDuichen;
		this.szAotuxing = szAotuxing;
		this.szLevel = szLevel;
		this.szMingxian = szMingxian;
		this.szJianqing = szJianqing;
		this.szGuanxi = szGuanxi;
		this.tt = tt;
		this.ttBecause = ttBecause;
		this.ttFeatures = ttFeatures;
		this.ttFeaturesZdy = ttFeaturesZdy;
		this.ttPindu = ttPindu;
		this.ttLevel = ttLevel;
		this.ttParts = ttParts;
		this.ttFeature = ttFeature;
		this.ttFeatureZdy = ttFeatureZdy;
		this.ttJianqing = ttJianqing;
		this.ttGuanxi = ttGuanxi;
		this.ttGuilv = ttGuilv;
		this.ttYinsu2 = ttYinsu2;
		this.ttYinsu1 = ttYinsu1;
		this.szChange = szChange;
		this.szChangeLevel = szChangeLevel;
		this.szChangeLevelZdy = szChangeLevelZdy;
		this.szChangeAnswer = szChangeAnswer;
		this.szChangeXgChange = szChangeXgChange;
		this.szChangeXgChangeZdy = szChangeXgChangeZdy;
		this.szChangeXwEx = szChangeXwEx;
		this.szChangeXwExZdy = szChangeXwExZdy;
		this.szChangeCompute = szChangeCompute;
		this.szChangeComputeZdy = szChangeComputeZdy;
		this.szChangeOrientation = szChangeOrientation;
		this.szChangeOrientationZdy = szChangeOrientationZdy;
		this.szChangeHuanjue = szChangeHuanjue;
		this.szChangeHuanjue1 = szChangeHuanjue1;
		this.szChangeHuanjue1zdy = szChangeHuanjue1zdy;
		this.ccJingjue = ccJingjue;
		this.ccJingjueStatue = ccJingjueStatue;
		this.ccJingjueParts = ccJingjueParts;
		this.ccJingjueBansui = ccJingjueBansui;
		this.ccJingjueBansuiZdy = ccJingjueBansuiZdy;
		this.ccJingjueTime = ccJingjueTime;
		this.ccJingjueTimeValue = ccJingjueTimeValue;
		this.yaotong = yaotong;
		this.yaotongBecause = yaotongBecause;
		this.yaotongBecauseZdy = yaotongBecauseZdy;
		this.yaotongStatue = yaotongStatue;
		this.yaotongStatueZdy = yaotongStatueZdy;
		this.yaotongLevel = yaotongLevel;
		this.yaotongParts = yaotongParts;
		this.yaotongPartsZdy = yaotongPartsZdy;
		this.yaotongFeature = yaotongFeature;
		this.yaotongFeatureZdy = yaotongFeatureZdy;
		this.yaotongWeizhi = yaotongWeizhi;
		this.yaotongStatues = yaotongStatues;
		this.yaotongBansui = yaotongBansui;
		this.yaotongBansui1 = yaotongBansui1;
		this.yaotongBansui1zdy = yaotongBansui1zdy;
		this.yaotongJiazhong = yaotongJiazhong;
		this.yaotongJianqing = yaotongJianqing;
		this.jrgjt = jrgjt;
		this.jrgjtBecause = jrgjtBecause;
		this.jrgjtBecauseZdy = jrgjtBecauseZdy;
		this.jrgjtStatue = jrgjtStatue;
		this.jrgjtStatueZdy = jrgjtStatueZdy;
		this.jrgjtLevel = jrgjtLevel;
		this.jrgjtLevelValue = jrgjtLevelValue;
		this.jrgjtParts = jrgjtParts;
		this.jrgjtPartsValue = jrgjtPartsValue;
		this.jrgjtFeature = jrgjtFeature;
		this.jrgjtFeatureZdy = jrgjtFeatureZdy;
		this.jrgjtWeizhi = jrgjtWeizhi;
		this.jrgjtWeizhiZdy = jrgjtWeizhiZdy;
		this.jrgjtStatues = jrgjtStatues;
		this.jrgjtBansui = jrgjtBansui;
		this.jrgjtBansui1 = jrgjtBansui1;
		this.jrgjtBansui1zdy = jrgjtBansui1zdy;
		this.jrgjtJiazhong = jrgjtJiazhong;
		this.jrgjtJianqing = jrgjtJianqing;
		this.oldContent = oldContent;
		this.newContent = newContent;
		this.dateTime = dateTime;
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

	public Integer getFare() {
		return this.fare;
	}

	public void setFare(Integer fare) {
		this.fare = fare;
	}

	public Integer getFareType() {
		return this.fareType;
	}

	public void setFareType(Integer fareType) {
		this.fareType = fareType;
	}

	public Integer getFareXing() {
		return this.fareXing;
	}

	public void setFareXing(Integer fareXing) {
		this.fareXing = fareXing;
	}

	public String getFareXingZdy() {
		return this.fareXingZdy;
	}

	public void setFareXingZdy(String fareXingZdy) {
		this.fareXingZdy = fareXingZdy;
	}

	public Integer getFareTemperature() {
		return this.fareTemperature;
	}

	public void setFareTemperature(Integer fareTemperature) {
		this.fareTemperature = fareTemperature;
	}

	public String getTemStart() {
		return this.temStart;
	}

	public void setTemStart(String temStart) {
		this.temStart = temStart;
	}

	public String getTemEnd() {
		return this.temEnd;
	}

	public void setTemEnd(String temEnd) {
		this.temEnd = temEnd;
	}

	public String getTemMax() {
		return this.temMax;
	}

	public void setTemMax(String temMax) {
		this.temMax = temMax;
	}

	public String getFeatures() {
		return this.features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getShow() {
		return this.show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public Integer getWeihan() {
		return this.weihan;
	}

	public void setWeihan(Integer weihan) {
		this.weihan = weihan;
	}

	public Integer getHanzhan() {
		return this.hanzhan;
	}

	public void setHanzhan(Integer hanzhan) {
		this.hanzhan = hanzhan;
	}

	public Integer getHanzhanContinue() {
		return this.hanzhanContinue;
	}

	public void setHanzhanContinue(Integer hanzhanContinue) {
		this.hanzhanContinue = hanzhanContinue;
	}

	public Integer getSweat() {
		return this.sweat;
	}

	public void setSweat(Integer sweat) {
		this.sweat = sweat;
	}

	public Integer getDaohan() {
		return this.daohan;
	}

	public void setDaohan(Integer daohan) {
		this.daohan = daohan;
	}

	public String getDaohanShowTime() {
		return this.daohanShowTime;
	}

	public void setDaohanShowTime(String daohanShowTime) {
		this.daohanShowTime = daohanShowTime;
	}

	public Integer getFali() {
		return this.fali;
	}

	public void setFali(Integer fali) {
		this.fali = fali;
	}

	public Integer getFaliShowTime() {
		return this.faliShowTime;
	}

	public void setFaliShowTime(Integer faliShowTime) {
		this.faliShowTime = faliShowTime;
	}

	public Integer getQsbs() {
		return this.qsbs;
	}

	public void setQsbs(Integer qsbs) {
		this.qsbs = qsbs;
	}

	public Integer getQsbsStatue() {
		return this.qsbsStatue;
	}

	public void setQsbsStatue(Integer qsbsStatue) {
		this.qsbsStatue = qsbsStatue;
	}

	public Integer getPfex() {
		return this.pfex;
	}

	public void setPfex(Integer pfex) {
		this.pfex = pfex;
	}

	public String getPfexParts() {
		return this.pfexParts;
	}

	public void setPfexParts(String pfexParts) {
		this.pfexParts = pfexParts;
	}

	public String getPfexPartsZidingyi() {
		return this.pfexPartsZidingyi;
	}

	public void setPfexPartsZidingyi(String pfexPartsZidingyi) {
		this.pfexPartsZidingyi = pfexPartsZidingyi;
	}

	public Integer getPfexColor() {
		return this.pfexColor;
	}

	public void setPfexColor(Integer pfexColor) {
		this.pfexColor = pfexColor;
	}

	public Integer getPfexColorZidingyi() {
		return this.pfexColorZidingyi;
	}

	public void setPfexColorZidingyi(Integer pfexColorZidingyi) {
		this.pfexColorZidingyi = pfexColorZidingyi;
	}

	public Integer getPrexPizhen() {
		return this.prexPizhen;
	}

	public void setPrexPizhen(Integer prexPizhen) {
		this.prexPizhen = prexPizhen;
	}

	public String getPrexParts() {
		return this.prexParts;
	}

	public void setPrexParts(String prexParts) {
		this.prexParts = prexParts;
	}

	public String getPrexColor() {
		return this.prexColor;
	}

	public void setPrexColor(String prexColor) {
		this.prexColor = prexColor;
	}

	public String getPrexSize() {
		return this.prexSize;
	}

	public void setPrexSize(String prexSize) {
		this.prexSize = prexSize;
	}

	public Integer getPrexStatue() {
		return this.prexStatue;
	}

	public void setPrexStatue(Integer prexStatue) {
		this.prexStatue = prexStatue;
	}

	public String getPrexStatueZidingyi() {
		return this.prexStatueZidingyi;
	}

	public void setPrexStatueZidingyi(String prexStatueZidingyi) {
		this.prexStatueZidingyi = prexStatueZidingyi;
	}

	public Integer getPrexYazhi() {
		return this.prexYazhi;
	}

	public void setPrexYazhi(Integer prexYazhi) {
		this.prexYazhi = prexYazhi;
	}

	public Integer getPrexZhenfu() {
		return this.prexZhenfu;
	}

	public void setPrexZhenfu(Integer prexZhenfu) {
		this.prexZhenfu = prexZhenfu;
	}

	public Integer getPrexBansui() {
		return this.prexBansui;
	}

	public void setPrexBansui(Integer prexBansui) {
		this.prexBansui = prexBansui;
	}

	public String getPrexSort() {
		return this.prexSort;
	}

	public void setPrexSort(String prexSort) {
		this.prexSort = prexSort;
	}

	public String getPrexGuilv() {
		return this.prexGuilv;
	}

	public void setPrexGuilv(String prexGuilv) {
		this.prexGuilv = prexGuilv;
	}

	public String getPrexOther() {
		return this.prexOther;
	}

	public void setPrexOther(String prexOther) {
		this.prexOther = prexOther;
	}

	public Integer getBisai() {
		return this.bisai;
	}

	public void setBisai(Integer bisai) {
		this.bisai = bisai;
	}

	public Integer getBisaiStatue1() {
		return this.bisaiStatue1;
	}

	public void setBisaiStatue1(Integer bisaiStatue1) {
		this.bisaiStatue1 = bisaiStatue1;
	}

	public Integer getBisaiStatue2() {
		return this.bisaiStatue2;
	}

	public void setBisaiStatue2(Integer bisaiStatue2) {
		this.bisaiStatue2 = bisaiStatue2;
	}

	public Integer getLiuti() {
		return this.liuti;
	}

	public void setLiuti(Integer liuti) {
		this.liuti = liuti;
	}

	public Integer getLiutiStatue() {
		return this.liutiStatue;
	}

	public void setLiutiStatue(Integer liutiStatue) {
		this.liutiStatue = liutiStatue;
	}

	public Integer getLiulei() {
		return this.liulei;
	}

	public void setLiulei(Integer liulei) {
		this.liulei = liulei;
	}

	public Integer getLiuleiStatue() {
		return this.liuleiStatue;
	}

	public void setLiuleiStatue(Integer liuleiStatue) {
		this.liuleiStatue = liuleiStatue;
	}

	public Integer getYanhou() {
		return this.yanhou;
	}

	public void setYanhou(Integer yanhou) {
		this.yanhou = yanhou;
	}

	public Integer getYanhouStatue() {
		return this.yanhouStatue;
	}

	public void setYanhouStatue(Integer yanhouStatue) {
		this.yanhouStatue = yanhouStatue;
	}

	public Integer getKesou() {
		return this.kesou;
	}

	public void setKesou(Integer kesou) {
		this.kesou = kesou;
	}

	public Integer getKesouFeature() {
		return this.kesouFeature;
	}

	public void setKesouFeature(Integer kesouFeature) {
		this.kesouFeature = kesouFeature;
	}

	public Integer getKesouNature() {
		return this.kesouNature;
	}

	public void setKesouNature(Integer kesouNature) {
		this.kesouNature = kesouNature;
	}

	public Integer getKesouLevel() {
		return this.kesouLevel;
	}

	public void setKesouLevel(Integer kesouLevel) {
		this.kesouLevel = kesouLevel;
	}

	public Integer getKesouSound() {
		return this.kesouSound;
	}

	public void setKesouSound(Integer kesouSound) {
		this.kesouSound = kesouSound;
	}

	public Integer getKesouTime() {
		return this.kesouTime;
	}

	public void setKesouTime(Integer kesouTime) {
		this.kesouTime = kesouTime;
	}

	public Integer getKetan() {
		return this.ketan;
	}

	public void setKetan(Integer ketan) {
		this.ketan = ketan;
	}

	public Integer getKetanPd() {
		return this.ketanPd;
	}

	public void setKetanPd(Integer ketanPd) {
		this.ketanPd = ketanPd;
	}

	public Integer getKesouVolume() {
		return this.kesouVolume;
	}

	public void setKesouVolume(Integer kesouVolume) {
		this.kesouVolume = kesouVolume;
	}

	public String getKesouVolumeValue1() {
		return this.kesouVolumeValue1;
	}

	public void setKesouVolumeValue1(String kesouVolumeValue1) {
		this.kesouVolumeValue1 = kesouVolumeValue1;
	}

	public String getKesouVolumeValue2() {
		return this.kesouVolumeValue2;
	}

	public void setKesouVolumeValue2(String kesouVolumeValue2) {
		this.kesouVolumeValue2 = kesouVolumeValue2;
	}

	public String getKesouVolumeValue3() {
		return this.kesouVolumeValue3;
	}

	public void setKesouVolumeValue3(String kesouVolumeValue3) {
		this.kesouVolumeValue3 = kesouVolumeValue3;
	}

	public Integer getKesouColor() {
		return this.kesouColor;
	}

	public void setKesouColor(Integer kesouColor) {
		this.kesouColor = kesouColor;
	}

	public String getKesouColorValue() {
		return this.kesouColorValue;
	}

	public void setKesouColorValue(String kesouColorValue) {
		this.kesouColorValue = kesouColorValue;
	}

	public Integer getKesouXz() {
		return this.kesouXz;
	}

	public void setKesouXz(Integer kesouXz) {
		this.kesouXz = kesouXz;
	}

	public String getKesouXzValue() {
		return this.kesouXzValue;
	}

	public void setKesouXzValue(String kesouXzValue) {
		this.kesouXzValue = kesouXzValue;
	}

	public Integer getKesouEx() {
		return this.kesouEx;
	}

	public void setKesouEx(Integer kesouEx) {
		this.kesouEx = kesouEx;
	}

	public Integer getKesouKechu() {
		return this.kesouKechu;
	}

	public void setKesouKechu(Integer kesouKechu) {
		this.kesouKechu = kesouKechu;
	}

	public Integer getXinji() {
		return this.xinji;
	}

	public void setXinji(Integer xinji) {
		this.xinji = xinji;
	}

	public Integer getXinjiBecause() {
		return this.xinjiBecause;
	}

	public void setXinjiBecause(Integer xinjiBecause) {
		this.xinjiBecause = xinjiBecause;
	}

	public String getXinjiBecauseValue() {
		return this.xinjiBecauseValue;
	}

	public void setXinjiBecauseValue(String xinjiBecauseValue) {
		this.xinjiBecauseValue = xinjiBecauseValue;
	}

	public String getXinjiBecauseYy() {
		return this.xinjiBecauseYy;
	}

	public void setXinjiBecauseYy(String xinjiBecauseYy) {
		this.xinjiBecauseYy = xinjiBecauseYy;
	}

	public Integer getXinjiLevel() {
		return this.xinjiLevel;
	}

	public void setXinjiLevel(Integer xinjiLevel) {
		this.xinjiLevel = xinjiLevel;
	}

	public Integer getXinjiPd() {
		return this.xinjiPd;
	}

	public void setXinjiPd(Integer xinjiPd) {
		this.xinjiPd = xinjiPd;
	}

	public String getContinueTime() {
		return this.continueTime;
	}

	public void setContinueTime(String continueTime) {
		this.continueTime = continueTime;
	}

	public String getXinjiPdContinueTime() {
		return this.xinjiPdContinueTime;
	}

	public void setXinjiPdContinueTime(String xinjiPdContinueTime) {
		this.xinjiPdContinueTime = xinjiPdContinueTime;
	}

	public Integer getHuxikunnan() {
		return this.huxikunnan;
	}

	public void setHuxikunnan(Integer huxikunnan) {
		this.huxikunnan = huxikunnan;
	}

	public Integer getHuxikunnanBecause() {
		return this.huxikunnanBecause;
	}

	public void setHuxikunnanBecause(Integer huxikunnanBecause) {
		this.huxikunnanBecause = huxikunnanBecause;
	}

	public String getHuxikunnanBecauseValue1() {
		return this.huxikunnanBecauseValue1;
	}

	public void setHuxikunnanBecauseValue1(String huxikunnanBecauseValue1) {
		this.huxikunnanBecauseValue1 = huxikunnanBecauseValue1;
	}

	public Integer getHuxikunnanFeatures() {
		return this.huxikunnanFeatures;
	}

	public void setHuxikunnanFeatures(Integer huxikunnanFeatures) {
		this.huxikunnanFeatures = huxikunnanFeatures;
	}

	public Integer getHuxikunnanLevel() {
		return this.huxikunnanLevel;
	}

	public void setHuxikunnanLevel(Integer huxikunnanLevel) {
		this.huxikunnanLevel = huxikunnanLevel;
	}

	public String getHuxikunnanLevelValue() {
		return this.huxikunnanLevelValue;
	}

	public void setHuxikunnanLevelValue(String huxikunnanLevelValue) {
		this.huxikunnanLevelValue = huxikunnanLevelValue;
	}

	public Integer getHuxikunnanType() {
		return this.huxikunnanType;
	}

	public void setHuxikunnanType(Integer huxikunnanType) {
		this.huxikunnanType = huxikunnanType;
	}

	public Integer getHuxikunnanTime() {
		return this.huxikunnanTime;
	}

	public void setHuxikunnanTime(Integer huxikunnanTime) {
		this.huxikunnanTime = huxikunnanTime;
	}

	public String getYinsu1() {
		return this.yinsu1;
	}

	public void setYinsu1(String yinsu1) {
		this.yinsu1 = yinsu1;
	}

	public String getYinsu2() {
		return this.yinsu2;
	}

	public void setYinsu2(String yinsu2) {
		this.yinsu2 = yinsu2;
	}

	public String getYinsu3() {
		return this.yinsu3;
	}

	public void setYinsu3(String yinsu3) {
		this.yinsu3 = yinsu3;
	}

	public Integer getXiongtong() {
		return this.xiongtong;
	}

	public void setXiongtong(Integer xiongtong) {
		this.xiongtong = xiongtong;
	}

	public Integer getXiongtongBecause() {
		return this.xiongtongBecause;
	}

	public void setXiongtongBecause(Integer xiongtongBecause) {
		this.xiongtongBecause = xiongtongBecause;
	}

	public String getXiongtongBecauseValue() {
		return this.xiongtongBecauseValue;
	}

	public void setXiongtongBecauseValue(String xiongtongBecauseValue) {
		this.xiongtongBecauseValue = xiongtongBecauseValue;
	}

	public Integer getXiongtongLevel() {
		return this.xiongtongLevel;
	}

	public void setXiongtongLevel(Integer xiongtongLevel) {
		this.xiongtongLevel = xiongtongLevel;
	}

	public String getXiongtongParts() {
		return this.xiongtongParts;
	}

	public void setXiongtongParts(String xiongtongParts) {
		this.xiongtongParts = xiongtongParts;
	}

	public String getXiongtongPartsZdy() {
		return this.xiongtongPartsZdy;
	}

	public void setXiongtongPartsZdy(String xiongtongPartsZdy) {
		this.xiongtongPartsZdy = xiongtongPartsZdy;
	}

	public Integer getXiongtongFeatures() {
		return this.xiongtongFeatures;
	}

	public void setXiongtongFeatures(Integer xiongtongFeatures) {
		this.xiongtongFeatures = xiongtongFeatures;
	}

	public String getXiongtongFeaturesZdy() {
		return this.xiongtongFeaturesZdy;
	}

	public void setXiongtongFeaturesZdy(String xiongtongFeaturesZdy) {
		this.xiongtongFeaturesZdy = xiongtongFeaturesZdy;
	}

	public Integer getXiongtongLocation() {
		return this.xiongtongLocation;
	}

	public void setXiongtongLocation(Integer xiongtongLocation) {
		this.xiongtongLocation = xiongtongLocation;
	}

	public Integer getXiongtonFs() {
		return this.xiongtonFs;
	}

	public void setXiongtonFs(Integer xiongtonFs) {
		this.xiongtonFs = xiongtonFs;
	}

	public Integer getXiongtongFsParts() {
		return this.xiongtongFsParts;
	}

	public void setXiongtongFsParts(Integer xiongtongFsParts) {
		this.xiongtongFsParts = xiongtongFsParts;
	}

	public String getXiongtongFsPartsZdy() {
		return this.xiongtongFsPartsZdy;
	}

	public void setXiongtongFsPartsZdy(String xiongtongFsPartsZdy) {
		this.xiongtongFsPartsZdy = xiongtongFsPartsZdy;
	}

	public Integer getXiongtongFsTd() {
		return this.xiongtongFsTd;
	}

	public void setXiongtongFsTd(Integer xiongtongFsTd) {
		this.xiongtongFsTd = xiongtongFsTd;
	}

	public String getXiongtongFsTdZdy() {
		return this.xiongtongFsTdZdy;
	}

	public void setXiongtongFsTdZdy(String xiongtongFsTdZdy) {
		this.xiongtongFsTdZdy = xiongtongFsTdZdy;
	}

	public String getXiongtongFsTime() {
		return this.xiongtongFsTime;
	}

	public void setXiongtongFsTime(String xiongtongFsTime) {
		this.xiongtongFsTime = xiongtongFsTime;
	}

	public String getXiongtongFsTimeValue() {
		return this.xiongtongFsTimeValue;
	}

	public void setXiongtongFsTimeValue(String xiongtongFsTimeValue) {
		this.xiongtongFsTimeValue = xiongtongFsTimeValue;
	}

	public String getXtYinsu2() {
		return this.xtYinsu2;
	}

	public void setXtYinsu2(String xtYinsu2) {
		this.xtYinsu2 = xtYinsu2;
	}

	public String getXtYinsu1() {
		return this.xtYinsu1;
	}

	public void setXtYinsu1(String xtYinsu1) {
		this.xtYinsu1 = xtYinsu1;
	}

	public Integer getSyjt() {
		return this.syjt;
	}

	public void setSyjt(Integer syjt) {
		this.syjt = syjt;
	}

	public Integer getSyjtLevel() {
		return this.syjtLevel;
	}

	public void setSyjtLevel(Integer syjtLevel) {
		this.syjtLevel = syjtLevel;
	}

	public Integer getSyjtDesc() {
		return this.syjtDesc;
	}

	public void setSyjtDesc(Integer syjtDesc) {
		this.syjtDesc = syjtDesc;
	}

	public String getSlPer() {
		return this.slPer;
	}

	public void setSlPer(String slPer) {
		this.slPer = slPer;
	}

	public Integer getExin() {
		return this.exin;
	}

	public void setExin(Integer exin) {
		this.exin = exin;
	}

	public Integer getExinLevel() {
		return this.exinLevel;
	}

	public void setExinLevel(Integer exinLevel) {
		this.exinLevel = exinLevel;
	}

	public Integer getOutu() {
		return this.outu;
	}

	public void setOutu(Integer outu) {
		this.outu = outu;
	}

	public Integer getOutuLevel() {
		return this.outuLevel;
	}

	public void setOutuLevel(Integer outuLevel) {
		this.outuLevel = outuLevel;
	}

	public Integer getOutuThing() {
		return this.outuThing;
	}

	public void setOutuThing(Integer outuThing) {
		this.outuThing = outuThing;
	}

	public String getOutuNum() {
		return this.outuNum;
	}

	public void setOutuNum(String outuNum) {
		this.outuNum = outuNum;
	}

	public String getOutuC() {
		return this.outuC;
	}

	public void setOutuC(String outuC) {
		this.outuC = outuC;
	}

	public Integer getFt() {
		return this.ft;
	}

	public void setFt(Integer ft) {
		this.ft = ft;
	}

	public Integer getFtBecause() {
		return this.ftBecause;
	}

	public void setFtBecause(Integer ftBecause) {
		this.ftBecause = ftBecause;
	}

	public Integer getFtBecauseParts() {
		return this.ftBecauseParts;
	}

	public void setFtBecauseParts(Integer ftBecauseParts) {
		this.ftBecauseParts = ftBecauseParts;
	}

	public String getFtBecausePartsZdy() {
		return this.ftBecausePartsZdy;
	}

	public void setFtBecausePartsZdy(String ftBecausePartsZdy) {
		this.ftBecausePartsZdy = ftBecausePartsZdy;
	}

	public Integer getFtFeature() {
		return this.ftFeature;
	}

	public void setFtFeature(Integer ftFeature) {
		this.ftFeature = ftFeature;
	}

	public String getFtFeatureZdy() {
		return this.ftFeatureZdy;
	}

	public void setFtFeatureZdy(String ftFeatureZdy) {
		this.ftFeatureZdy = ftFeatureZdy;
	}

	public String getFtPart() {
		return this.ftPart;
	}

	public void setFtPart(String ftPart) {
		this.ftPart = ftPart;
	}

	public Integer getFtXz() {
		return this.ftXz;
	}

	public void setFtXz(Integer ftXz) {
		this.ftXz = ftXz;
	}

	public String getFtXzZdy() {
		return this.ftXzZdy;
	}

	public void setFtXzZdy(String ftXzZdy) {
		this.ftXzZdy = ftXzZdy;
	}

	public Integer getFtLevel() {
		return this.ftLevel;
	}

	public void setFtLevel(Integer ftLevel) {
		this.ftLevel = ftLevel;
	}

	public Integer getFtLocation() {
		return this.ftLocation;
	}

	public void setFtLocation(Integer ftLocation) {
		this.ftLocation = ftLocation;
	}

	public Integer getFtFs() {
		return this.ftFs;
	}

	public void setFtFs(Integer ftFs) {
		this.ftFs = ftFs;
	}

	public String getFtFsParts() {
		return this.ftFsParts;
	}

	public void setFtFsParts(String ftFsParts) {
		this.ftFsParts = ftFsParts;
	}

	public String getFtShow() {
		return this.ftShow;
	}

	public void setFtShow(String ftShow) {
		this.ftShow = ftShow;
	}

	public String getFtYinsu() {
		return this.ftYinsu;
	}

	public void setFtYinsu(String ftYinsu) {
		this.ftYinsu = ftYinsu;
	}

	public String getFtDesc() {
		return this.ftDesc;
	}

	public void setFtDesc(String ftDesc) {
		this.ftDesc = ftDesc;
	}

	public String getFtGuanxi() {
		return this.ftGuanxi;
	}

	public void setFtGuanxi(String ftGuanxi) {
		this.ftGuanxi = ftGuanxi;
	}

	public Integer getFxjchange() {
		return this.fxjchange;
	}

	public void setFxjchange(Integer fxjchange) {
		this.fxjchange = fxjchange;
	}

	public Integer getFxjchangeFx() {
		return this.fxjchangeFx;
	}

	public void setFxjchangeFx(Integer fxjchangeFx) {
		this.fxjchangeFx = fxjchangeFx;
	}

	public String getFxjchangeFxDay() {
		return this.fxjchangeFxDay;
	}

	public void setFxjchangeFxDay(String fxjchangeFxDay) {
		this.fxjchangeFxDay = fxjchangeFxDay;
	}

	public String getFxjchangeFxNum() {
		return this.fxjchangeFxNum;
	}

	public void setFxjchangeFxNum(String fxjchangeFxNum) {
		this.fxjchangeFxNum = fxjchangeFxNum;
	}

	public String getFxjchangeBmDay() {
		return this.fxjchangeBmDay;
	}

	public void setFxjchangeBmDay(String fxjchangeBmDay) {
		this.fxjchangeBmDay = fxjchangeBmDay;
	}

	public String getFxjchangeBmNum() {
		return this.fxjchangeBmNum;
	}

	public void setFxjchangeBmNum(String fxjchangeBmNum) {
		this.fxjchangeBmNum = fxjchangeBmNum;
	}

	public Integer getFxBmHuhuan() {
		return this.fxBmHuhuan;
	}

	public void setFxBmHuhuan(Integer fxBmHuhuan) {
		this.fxBmHuhuan = fxBmHuhuan;
	}

	public String getFxBmSum() {
		return this.fxBmSum;
	}

	public void setFxBmSum(String fxBmSum) {
		this.fxBmSum = fxBmSum;
	}

	public String getFxBmNum() {
		return this.fxBmNum;
	}

	public void setFxBmNum(String fxBmNum) {
		this.fxBmNum = fxBmNum;
	}

	public Integer getFxBmColor() {
		return this.fxBmColor;
	}

	public void setFxBmColor(Integer fxBmColor) {
		this.fxBmColor = fxBmColor;
	}

	public Integer getFxBmXz() {
		return this.fxBmXz;
	}

	public void setFxBmXz(Integer fxBmXz) {
		this.fxBmXz = fxBmXz;
	}

	public Integer getFxBmZz() {
		return this.fxBmZz;
	}

	public void setFxBmZz(Integer fxBmZz) {
		this.fxBmZz = fxBmZz;
	}

	public Integer getFxBmEx() {
		return this.fxBmEx;
	}

	public void setFxBmEx(Integer fxBmEx) {
		this.fxBmEx = fxBmEx;
	}

	public Integer getFxBmExParts() {
		return this.fxBmExParts;
	}

	public void setFxBmExParts(Integer fxBmExParts) {
		this.fxBmExParts = fxBmExParts;
	}

	public Integer getFxBmThing() {
		return this.fxBmThing;
	}

	public void setFxBmThing(Integer fxBmThing) {
		this.fxBmThing = fxBmThing;
	}

	public Integer getNiaopin() {
		return this.niaopin;
	}

	public void setNiaopin(Integer niaopin) {
		this.niaopin = niaopin;
	}

	public Integer getNiaopinLevel() {
		return this.niaopinLevel;
	}

	public void setNiaopinLevel(Integer niaopinLevel) {
		this.niaopinLevel = niaopinLevel;
	}

	public Integer getNiaopinBecause() {
		return this.niaopinBecause;
	}

	public void setNiaopinBecause(Integer niaopinBecause) {
		this.niaopinBecause = niaopinBecause;
	}

	public String getNiaopinBecauseZdy() {
		return this.niaopinBecauseZdy;
	}

	public void setNiaopinBecauseZdy(String niaopinBecauseZdy) {
		this.niaopinBecauseZdy = niaopinBecauseZdy;
	}

	public Integer getNiaopiPl() {
		return this.niaopiPl;
	}

	public void setNiaopiPl(Integer niaopiPl) {
		this.niaopiPl = niaopiPl;
	}

	public String getNiaopinPlZdy() {
		return this.niaopinPlZdy;
	}

	public void setNiaopinPlZdy(String niaopinPlZdy) {
		this.niaopinPlZdy = niaopinPlZdy;
	}

	public Integer getNiaoji() {
		return this.niaoji;
	}

	public void setNiaoji(Integer niaoji) {
		this.niaoji = niaoji;
	}

	public Integer getNiaotong() {
		return this.niaotong;
	}

	public void setNiaotong(Integer niaotong) {
		this.niaotong = niaotong;
	}

	public Integer getNiaotongLevel() {
		return this.niaotongLevel;
	}

	public void setNiaotongLevel(Integer niaotongLevel) {
		this.niaotongLevel = niaotongLevel;
	}

	public Integer getNiaotongTime() {
		return this.niaotongTime;
	}

	public void setNiaotongTime(Integer niaotongTime) {
		this.niaotongTime = niaotongTime;
	}

	public Integer getNiaotongParts() {
		return this.niaotongParts;
	}

	public void setNiaotongParts(Integer niaotongParts) {
		this.niaotongParts = niaotongParts;
	}

	public Integer getNyChange() {
		return this.nyChange;
	}

	public void setNyChange(Integer nyChange) {
		this.nyChange = nyChange;
	}

	public Integer getNyChangeChange() {
		return this.nyChangeChange;
	}

	public void setNyChangeChange(Integer nyChangeChange) {
		this.nyChangeChange = nyChangeChange;
	}

	public String getNyChangeChangeZdy() {
		return this.nyChangeChangeZdy;
	}

	public void setNyChangeChangeZdy(String nyChangeChangeZdy) {
		this.nyChangeChangeZdy = nyChangeChangeZdy;
	}

	public Integer getNyChangeEx() {
		return this.nyChangeEx;
	}

	public void setNyChangeEx(Integer nyChangeEx) {
		this.nyChangeEx = nyChangeEx;
	}

	public Integer getNyChangeColor() {
		return this.nyChangeColor;
	}

	public void setNyChangeColor(Integer nyChangeColor) {
		this.nyChangeColor = nyChangeColor;
	}

	public Integer getNyChangeDu() {
		return this.nyChangeDu;
	}

	public void setNyChangeDu(Integer nyChangeDu) {
		this.nyChangeDu = nyChangeDu;
	}

	public Integer getNyChangeWeidaoEx() {
		return this.nyChangeWeidaoEx;
	}

	public void setNyChangeWeidaoEx(Integer nyChangeWeidaoEx) {
		this.nyChangeWeidaoEx = nyChangeWeidaoEx;
	}

	public Integer getNyChangeWeidaoEx1() {
		return this.nyChangeWeidaoEx1;
	}

	public void setNyChangeWeidaoEx1(Integer nyChangeWeidaoEx1) {
		this.nyChangeWeidaoEx1 = nyChangeWeidaoEx1;
	}

	public Integer getSz() {
		return this.sz;
	}

	public void setSz(Integer sz) {
		this.sz = sz;
	}

	public Integer getSzShibing() {
		return this.szShibing;
	}

	public void setSzShibing(Integer szShibing) {
		this.szShibing = szShibing;
	}

	public Integer getSzParts() {
		return this.szParts;
	}

	public void setSzParts(Integer szParts) {
		this.szParts = szParts;
	}

	public String getSzPartsZdy() {
		return this.szPartsZdy;
	}

	public void setSzPartsZdy(String szPartsZdy) {
		this.szPartsZdy = szPartsZdy;
	}

	public Integer getSzDuichen() {
		return this.szDuichen;
	}

	public void setSzDuichen(Integer szDuichen) {
		this.szDuichen = szDuichen;
	}

	public Integer getSzAotuxing() {
		return this.szAotuxing;
	}

	public void setSzAotuxing(Integer szAotuxing) {
		this.szAotuxing = szAotuxing;
	}

	public Integer getSzLevel() {
		return this.szLevel;
	}

	public void setSzLevel(Integer szLevel) {
		this.szLevel = szLevel;
	}

	public String getSzMingxian() {
		return this.szMingxian;
	}

	public void setSzMingxian(String szMingxian) {
		this.szMingxian = szMingxian;
	}

	public String getSzJianqing() {
		return this.szJianqing;
	}

	public void setSzJianqing(String szJianqing) {
		this.szJianqing = szJianqing;
	}

	public String getSzGuanxi() {
		return this.szGuanxi;
	}

	public void setSzGuanxi(String szGuanxi) {
		this.szGuanxi = szGuanxi;
	}

	public Integer getTt() {
		return this.tt;
	}

	public void setTt(Integer tt) {
		this.tt = tt;
	}

	public Integer getTtBecause() {
		return this.ttBecause;
	}

	public void setTtBecause(Integer ttBecause) {
		this.ttBecause = ttBecause;
	}

	public Integer getTtFeatures() {
		return this.ttFeatures;
	}

	public void setTtFeatures(Integer ttFeatures) {
		this.ttFeatures = ttFeatures;
	}

	public String getTtFeaturesZdy() {
		return this.ttFeaturesZdy;
	}

	public void setTtFeaturesZdy(String ttFeaturesZdy) {
		this.ttFeaturesZdy = ttFeaturesZdy;
	}

	public Integer getTtPindu() {
		return this.ttPindu;
	}

	public void setTtPindu(Integer ttPindu) {
		this.ttPindu = ttPindu;
	}

	public Integer getTtLevel() {
		return this.ttLevel;
	}

	public void setTtLevel(Integer ttLevel) {
		this.ttLevel = ttLevel;
	}

	public Integer getTtParts() {
		return this.ttParts;
	}

	public void setTtParts(Integer ttParts) {
		this.ttParts = ttParts;
	}

	public Integer getTtFeature() {
		return this.ttFeature;
	}

	public void setTtFeature(Integer ttFeature) {
		this.ttFeature = ttFeature;
	}

	public String getTtFeatureZdy() {
		return this.ttFeatureZdy;
	}

	public void setTtFeatureZdy(String ttFeatureZdy) {
		this.ttFeatureZdy = ttFeatureZdy;
	}

	public String getTtJianqing() {
		return this.ttJianqing;
	}

	public void setTtJianqing(String ttJianqing) {
		this.ttJianqing = ttJianqing;
	}

	public String getTtGuanxi() {
		return this.ttGuanxi;
	}

	public void setTtGuanxi(String ttGuanxi) {
		this.ttGuanxi = ttGuanxi;
	}

	public String getTtGuilv() {
		return this.ttGuilv;
	}

	public void setTtGuilv(String ttGuilv) {
		this.ttGuilv = ttGuilv;
	}

	public String getTtYinsu2() {
		return this.ttYinsu2;
	}

	public void setTtYinsu2(String ttYinsu2) {
		this.ttYinsu2 = ttYinsu2;
	}

	public String getTtYinsu1() {
		return this.ttYinsu1;
	}

	public void setTtYinsu1(String ttYinsu1) {
		this.ttYinsu1 = ttYinsu1;
	}

	public Integer getSzChange() {
		return this.szChange;
	}

	public void setSzChange(Integer szChange) {
		this.szChange = szChange;
	}

	public Integer getSzChangeLevel() {
		return this.szChangeLevel;
	}

	public void setSzChangeLevel(Integer szChangeLevel) {
		this.szChangeLevel = szChangeLevel;
	}

	public String getSzChangeLevelZdy() {
		return this.szChangeLevelZdy;
	}

	public void setSzChangeLevelZdy(String szChangeLevelZdy) {
		this.szChangeLevelZdy = szChangeLevelZdy;
	}

	public Integer getSzChangeAnswer() {
		return this.szChangeAnswer;
	}

	public void setSzChangeAnswer(Integer szChangeAnswer) {
		this.szChangeAnswer = szChangeAnswer;
	}

	public Integer getSzChangeXgChange() {
		return this.szChangeXgChange;
	}

	public void setSzChangeXgChange(Integer szChangeXgChange) {
		this.szChangeXgChange = szChangeXgChange;
	}

	public String getSzChangeXgChangeZdy() {
		return this.szChangeXgChangeZdy;
	}

	public void setSzChangeXgChangeZdy(String szChangeXgChangeZdy) {
		this.szChangeXgChangeZdy = szChangeXgChangeZdy;
	}

	public Integer getSzChangeXwEx() {
		return this.szChangeXwEx;
	}

	public void setSzChangeXwEx(Integer szChangeXwEx) {
		this.szChangeXwEx = szChangeXwEx;
	}

	public String getSzChangeXwExZdy() {
		return this.szChangeXwExZdy;
	}

	public void setSzChangeXwExZdy(String szChangeXwExZdy) {
		this.szChangeXwExZdy = szChangeXwExZdy;
	}

	public Integer getSzChangeCompute() {
		return this.szChangeCompute;
	}

	public void setSzChangeCompute(Integer szChangeCompute) {
		this.szChangeCompute = szChangeCompute;
	}

	public String getSzChangeComputeZdy() {
		return this.szChangeComputeZdy;
	}

	public void setSzChangeComputeZdy(String szChangeComputeZdy) {
		this.szChangeComputeZdy = szChangeComputeZdy;
	}

	public Integer getSzChangeOrientation() {
		return this.szChangeOrientation;
	}

	public void setSzChangeOrientation(Integer szChangeOrientation) {
		this.szChangeOrientation = szChangeOrientation;
	}

	public String getSzChangeOrientationZdy() {
		return this.szChangeOrientationZdy;
	}

	public void setSzChangeOrientationZdy(String szChangeOrientationZdy) {
		this.szChangeOrientationZdy = szChangeOrientationZdy;
	}

	public Integer getSzChangeHuanjue() {
		return this.szChangeHuanjue;
	}

	public void setSzChangeHuanjue(Integer szChangeHuanjue) {
		this.szChangeHuanjue = szChangeHuanjue;
	}

	public Integer getSzChangeHuanjue1() {
		return this.szChangeHuanjue1;
	}

	public void setSzChangeHuanjue1(Integer szChangeHuanjue1) {
		this.szChangeHuanjue1 = szChangeHuanjue1;
	}

	public String getSzChangeHuanjue1zdy() {
		return this.szChangeHuanjue1zdy;
	}

	public void setSzChangeHuanjue1zdy(String szChangeHuanjue1zdy) {
		this.szChangeHuanjue1zdy = szChangeHuanjue1zdy;
	}

	public Integer getCcJingjue() {
		return this.ccJingjue;
	}

	public void setCcJingjue(Integer ccJingjue) {
		this.ccJingjue = ccJingjue;
	}

	public Integer getCcJingjueStatue() {
		return this.ccJingjueStatue;
	}

	public void setCcJingjueStatue(Integer ccJingjueStatue) {
		this.ccJingjueStatue = ccJingjueStatue;
	}

	public Integer getCcJingjueParts() {
		return this.ccJingjueParts;
	}

	public void setCcJingjueParts(Integer ccJingjueParts) {
		this.ccJingjueParts = ccJingjueParts;
	}

	public Integer getCcJingjueBansui() {
		return this.ccJingjueBansui;
	}

	public void setCcJingjueBansui(Integer ccJingjueBansui) {
		this.ccJingjueBansui = ccJingjueBansui;
	}

	public String getCcJingjueBansuiZdy() {
		return this.ccJingjueBansuiZdy;
	}

	public void setCcJingjueBansuiZdy(String ccJingjueBansuiZdy) {
		this.ccJingjueBansuiZdy = ccJingjueBansuiZdy;
	}

	public Integer getCcJingjueTime() {
		return this.ccJingjueTime;
	}

	public void setCcJingjueTime(Integer ccJingjueTime) {
		this.ccJingjueTime = ccJingjueTime;
	}

	public String getCcJingjueTimeValue() {
		return this.ccJingjueTimeValue;
	}

	public void setCcJingjueTimeValue(String ccJingjueTimeValue) {
		this.ccJingjueTimeValue = ccJingjueTimeValue;
	}

	public Integer getYaotong() {
		return this.yaotong;
	}

	public void setYaotong(Integer yaotong) {
		this.yaotong = yaotong;
	}

	public Integer getYaotongBecause() {
		return this.yaotongBecause;
	}

	public void setYaotongBecause(Integer yaotongBecause) {
		this.yaotongBecause = yaotongBecause;
	}

	public String getYaotongBecauseZdy() {
		return this.yaotongBecauseZdy;
	}

	public void setYaotongBecauseZdy(String yaotongBecauseZdy) {
		this.yaotongBecauseZdy = yaotongBecauseZdy;
	}

	public Integer getYaotongStatue() {
		return this.yaotongStatue;
	}

	public void setYaotongStatue(Integer yaotongStatue) {
		this.yaotongStatue = yaotongStatue;
	}

	public String getYaotongStatueZdy() {
		return this.yaotongStatueZdy;
	}

	public void setYaotongStatueZdy(String yaotongStatueZdy) {
		this.yaotongStatueZdy = yaotongStatueZdy;
	}

	public Integer getYaotongLevel() {
		return this.yaotongLevel;
	}

	public void setYaotongLevel(Integer yaotongLevel) {
		this.yaotongLevel = yaotongLevel;
	}

	public Integer getYaotongParts() {
		return this.yaotongParts;
	}

	public void setYaotongParts(Integer yaotongParts) {
		this.yaotongParts = yaotongParts;
	}

	public String getYaotongPartsZdy() {
		return this.yaotongPartsZdy;
	}

	public void setYaotongPartsZdy(String yaotongPartsZdy) {
		this.yaotongPartsZdy = yaotongPartsZdy;
	}

	public Integer getYaotongFeature() {
		return this.yaotongFeature;
	}

	public void setYaotongFeature(Integer yaotongFeature) {
		this.yaotongFeature = yaotongFeature;
	}

	public String getYaotongFeatureZdy() {
		return this.yaotongFeatureZdy;
	}

	public void setYaotongFeatureZdy(String yaotongFeatureZdy) {
		this.yaotongFeatureZdy = yaotongFeatureZdy;
	}

	public Integer getYaotongWeizhi() {
		return this.yaotongWeizhi;
	}

	public void setYaotongWeizhi(Integer yaotongWeizhi) {
		this.yaotongWeizhi = yaotongWeizhi;
	}

	public Integer getYaotongStatues() {
		return this.yaotongStatues;
	}

	public void setYaotongStatues(Integer yaotongStatues) {
		this.yaotongStatues = yaotongStatues;
	}

	public Integer getYaotongBansui() {
		return this.yaotongBansui;
	}

	public void setYaotongBansui(Integer yaotongBansui) {
		this.yaotongBansui = yaotongBansui;
	}

	public Integer getYaotongBansui1() {
		return this.yaotongBansui1;
	}

	public void setYaotongBansui1(Integer yaotongBansui1) {
		this.yaotongBansui1 = yaotongBansui1;
	}

	public String getYaotongBansui1zdy() {
		return this.yaotongBansui1zdy;
	}

	public void setYaotongBansui1zdy(String yaotongBansui1zdy) {
		this.yaotongBansui1zdy = yaotongBansui1zdy;
	}

	public String getYaotongJiazhong() {
		return this.yaotongJiazhong;
	}

	public void setYaotongJiazhong(String yaotongJiazhong) {
		this.yaotongJiazhong = yaotongJiazhong;
	}

	public String getYaotongJianqing() {
		return this.yaotongJianqing;
	}

	public void setYaotongJianqing(String yaotongJianqing) {
		this.yaotongJianqing = yaotongJianqing;
	}

	public Integer getJrgjt() {
		return this.jrgjt;
	}

	public void setJrgjt(Integer jrgjt) {
		this.jrgjt = jrgjt;
	}

	public Integer getJrgjtBecause() {
		return this.jrgjtBecause;
	}

	public void setJrgjtBecause(Integer jrgjtBecause) {
		this.jrgjtBecause = jrgjtBecause;
	}

	public String getJrgjtBecauseZdy() {
		return this.jrgjtBecauseZdy;
	}

	public void setJrgjtBecauseZdy(String jrgjtBecauseZdy) {
		this.jrgjtBecauseZdy = jrgjtBecauseZdy;
	}

	public Integer getJrgjtStatue() {
		return this.jrgjtStatue;
	}

	public void setJrgjtStatue(Integer jrgjtStatue) {
		this.jrgjtStatue = jrgjtStatue;
	}

	public String getJrgjtStatueZdy() {
		return this.jrgjtStatueZdy;
	}

	public void setJrgjtStatueZdy(String jrgjtStatueZdy) {
		this.jrgjtStatueZdy = jrgjtStatueZdy;
	}

	public Integer getJrgjtLevel() {
		return this.jrgjtLevel;
	}

	public void setJrgjtLevel(Integer jrgjtLevel) {
		this.jrgjtLevel = jrgjtLevel;
	}

	public String getJrgjtLevelValue() {
		return this.jrgjtLevelValue;
	}

	public void setJrgjtLevelValue(String jrgjtLevelValue) {
		this.jrgjtLevelValue = jrgjtLevelValue;
	}

	public Integer getJrgjtParts() {
		return this.jrgjtParts;
	}

	public void setJrgjtParts(Integer jrgjtParts) {
		this.jrgjtParts = jrgjtParts;
	}

	public String getJrgjtPartsValue() {
		return this.jrgjtPartsValue;
	}

	public void setJrgjtPartsValue(String jrgjtPartsValue) {
		this.jrgjtPartsValue = jrgjtPartsValue;
	}

	public Integer getJrgjtFeature() {
		return this.jrgjtFeature;
	}

	public void setJrgjtFeature(Integer jrgjtFeature) {
		this.jrgjtFeature = jrgjtFeature;
	}

	public String getJrgjtFeatureZdy() {
		return this.jrgjtFeatureZdy;
	}

	public void setJrgjtFeatureZdy(String jrgjtFeatureZdy) {
		this.jrgjtFeatureZdy = jrgjtFeatureZdy;
	}

	public Integer getJrgjtWeizhi() {
		return this.jrgjtWeizhi;
	}

	public void setJrgjtWeizhi(Integer jrgjtWeizhi) {
		this.jrgjtWeizhi = jrgjtWeizhi;
	}

	public String getJrgjtWeizhiZdy() {
		return this.jrgjtWeizhiZdy;
	}

	public void setJrgjtWeizhiZdy(String jrgjtWeizhiZdy) {
		this.jrgjtWeizhiZdy = jrgjtWeizhiZdy;
	}

	public Integer getJrgjtStatues() {
		return this.jrgjtStatues;
	}

	public void setJrgjtStatues(Integer jrgjtStatues) {
		this.jrgjtStatues = jrgjtStatues;
	}

	public Integer getJrgjtBansui() {
		return this.jrgjtBansui;
	}

	public void setJrgjtBansui(Integer jrgjtBansui) {
		this.jrgjtBansui = jrgjtBansui;
	}

	public Integer getJrgjtBansui1() {
		return this.jrgjtBansui1;
	}

	public void setJrgjtBansui1(Integer jrgjtBansui1) {
		this.jrgjtBansui1 = jrgjtBansui1;
	}

	public String getJrgjtBansui1zdy() {
		return this.jrgjtBansui1zdy;
	}

	public void setJrgjtBansui1zdy(String jrgjtBansui1zdy) {
		this.jrgjtBansui1zdy = jrgjtBansui1zdy;
	}

	public String getJrgjtJiazhong() {
		return this.jrgjtJiazhong;
	}

	public void setJrgjtJiazhong(String jrgjtJiazhong) {
		this.jrgjtJiazhong = jrgjtJiazhong;
	}

	public String getJrgjtJianqing() {
		return this.jrgjtJianqing;
	}

	public void setJrgjtJianqing(String jrgjtJianqing) {
		this.jrgjtJianqing = jrgjtJianqing;
	}

	public String getOldContent() {
		return this.oldContent;
	}

	public void setOldContent(String oldContent) {
		this.oldContent = oldContent;
	}

	public String getNewContent() {
		return this.newContent;
	}

	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}

	public String getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}