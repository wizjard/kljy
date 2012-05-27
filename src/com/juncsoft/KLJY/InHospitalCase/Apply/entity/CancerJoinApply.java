package com.juncsoft.KLJY.InHospitalCase.Apply.entity;

/**
 * --肿瘤肝胆介入治疗中心介入诊断治疗申请单--
 */
public class CancerJoinApply {

	private Long id;// bigint,
	private Long patientId;// bigint,--病人Id
	private Long historyCaseId;// bigint,--病历组件Id

	private String patientName;// varchar(100),--病人姓名
	private String gender;// varchar(100),--病人性别
	private String age;// varchar(100),--病人年龄
	private String patientCaseId;// bigint,--病案号
	private String officeAssort;// varchar(200),--科别
	private String bedNum; // bigint,--床号
	private String ctNum; // bigint,--CT号
	private String mrNum;// bigint,--MR号
	private String joinNum;// bigint,--介入号
	private String diagnose;// ntext,--诊断
	// --申请项目--
	private String one;// varchar(50),--1、肝动脉造影
	private String two;// varchar(50);//,--2、DSA下超选择动脉导管介入治疗
	private String three;// varchar(50),--3、脾动脉栓塞
	private String four;// varchar(50),--4、上、下腔静脉造影
	private String five;// varchar(50),--5、DSA下胆道内、外引流
	private String six;// varchar(50),--6、DSA下胆道内支架植入
	private String seven;// varchar(50),--7、DSA下经颈静脉肝内门、体静脉支架分流（TIPS）
	private String eight;// varchar(50),--8、DSA下经皮经肝胃冠状静脉栓塞
	private String nine;// varchar(50),--9、CT引导下射频电极消融
	private String ten;// varchar(50),--10、CT导引下化学消融
	private String eleven;// varchar(50),--11、CT引导下脓肿抽吸术、肿物组织活检
	private String twelve;// varchar(50),--12、其他
	private String otherText;// ntext, --点击其他的隐藏框
	// -------------------------
	private String mainTell;// ntext,--主诉
	private String nowHistory;// ntext,--现病史
	private String pastHistory;// ntext,--既往史
	// --主要体征--
	private String face;// varchar(100),--面色
	private String liverPalms;// varchar(100),--肝掌
	private String spider;// varchar(100),--蜘蛛痣
	private String skin;// varchar(100),--皮肤巩膜黄染
	private String lungSound;// varchar(100),--双肺呼吸音
	private String bosomWater;// varchar(100),--胸水
	private String heartSpeed;// varchar(100),--心率
	private String heartLaw;// varchar(100),--心律
	private String noisy;// varchar(100),--杂音
	private String belly;// varchar(100),--腹部包块
	private String liverRib;// varchar(100),--肝肋下
	private String sword;// varchar(100),--剑下
	private String spleenRib;// varchar(100),--脾肋下
	private String bellyWater;// varchar(100),--腹水
	private String legs;// varchar(100),--双下肢
	// --主要化验--
	private String guBing;// varchar(100),--谷丙转氨酶
	private String zongDanBai;// varchar(100),--总蛋白
	private String xueHong;// varchar(100),--血红蛋白测定
	private String guCao;// varchar(100),--谷草转氨酶
	private String baiDanBai;// varchar(100),--白蛋白
	private String xueXiaoBan;// varchar(100),--血小板计数
	private String zongDanHong;// varchar(100),--总胆红素
	private String jiaTaiDanBai;// varchar(100),--甲胎蛋白
	private String danJianZhiMei;// varchar(100),--胆碱酯酶
	private String zhiDanHong;// varchar(100),--直接胆红素
	private String baiXiBao;// varchar(100),--白细胞计数
	private String ningXue;// varchar(100),--凝血酶原时间
	// --特殊检查
	private String specialCheck;// ntext,--特殊检查
	private String doctorName;// varchar(100),--申请医师
	private String checkDate;// varchar(100),--申请时间

	public CancerJoinApply() {
		super();
	}

	public CancerJoinApply(Long id, Long patientId, Long historyCaseId,
			String patientName, String gender, String age, String patientCaseId,
			String officeAssort, String bedNum, String ctNum, String mrNum,
			String joinNum, String diagnose, String one, String two,
			String three, String four, String five, String six, String seven,
			String eight, String nine, String ten, String eleven,
			String twelve, String otherText, String mainTell,
			String nowHistory, String pastHistory, String face,
			String liverPalms, String spider, String skin, String lungSound,
			String bosomWater, String heartSpeed, String heartLaw,
			String noisy, String belly, String liverRib, String sword,
			String spleenRib, String bellyWater, String legs, String guBing,
			String zongDanBai, String xueHong, String guCao, String baiDanBai,
			String xueXiaoBan, String zongDanHong, String jiaTaiDanBai,
			String danJianZhiMei, String zhiDanHong, String baiXiBao,
			String ningXue, String specialCheck, String doctorName,
			String checkDate) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.historyCaseId = historyCaseId;
		this.patientName = patientName;
		this.gender = gender;
		this.age = age;
		this.patientCaseId = patientCaseId;
		this.officeAssort = officeAssort;
		this.bedNum = bedNum;
		this.ctNum = ctNum;
		this.mrNum = mrNum;
		this.joinNum = joinNum;
		this.diagnose = diagnose;
		this.one = one;
		this.two = two;
		this.three = three;
		this.four = four;
		this.five = five;
		this.six = six;
		this.seven = seven;
		this.eight = eight;
		this.nine = nine;
		this.ten = ten;
		this.eleven = eleven;
		this.twelve = twelve;
		this.otherText = otherText;
		this.mainTell = mainTell;
		this.nowHistory = nowHistory;
		this.pastHistory = pastHistory;
		this.face = face;
		this.liverPalms = liverPalms;
		this.spider = spider;
		this.skin = skin;
		this.lungSound = lungSound;
		this.bosomWater = bosomWater;
		this.heartSpeed = heartSpeed;
		this.heartLaw = heartLaw;
		this.noisy = noisy;
		this.belly = belly;
		this.liverRib = liverRib;
		this.sword = sword;
		this.spleenRib = spleenRib;
		this.bellyWater = bellyWater;
		this.legs = legs;
		this.guBing = guBing;
		this.zongDanBai = zongDanBai;
		this.xueHong = xueHong;
		this.guCao = guCao;
		this.baiDanBai = baiDanBai;
		this.xueXiaoBan = xueXiaoBan;
		this.zongDanHong = zongDanHong;
		this.jiaTaiDanBai = jiaTaiDanBai;
		this.danJianZhiMei = danJianZhiMei;
		this.zhiDanHong = zhiDanHong;
		this.baiXiBao = baiXiBao;
		this.ningXue = ningXue;
		this.specialCheck = specialCheck;
		this.doctorName = doctorName;
		this.checkDate = checkDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getHistoryCaseId() {
		return historyCaseId;
	}

	public void setHistoryCaseId(Long historyCaseId) {
		this.historyCaseId = historyCaseId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPatientCaseId() {
		return patientCaseId;
	}

	public void setPatientCaseId(String patientCaseId) {
		this.patientCaseId = patientCaseId;
	}

	public String getOfficeAssort() {
		return officeAssort;
	}

	public void setOfficeAssort(String officeAssort) {
		this.officeAssort = officeAssort;
	}

	public String getBedNum() {
		return bedNum;
	}

	public void setBedNum(String bedNum) {
		this.bedNum = bedNum;
	}

	public String getCtNum() {
		return ctNum;
	}

	public void setCtNum(String ctNum) {
		this.ctNum = ctNum;
	}

	public String getMrNum() {
		return mrNum;
	}

	public void setMrNum(String mrNum) {
		this.mrNum = mrNum;
	}

	public String getJoinNum() {
		return joinNum;
	}

	public void setJoinNum(String joinNum) {
		this.joinNum = joinNum;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public String getTwo() {
		return two;
	}

	public void setTwo(String two) {
		this.two = two;
	}

	public String getThree() {
		return three;
	}

	public void setThree(String three) {
		this.three = three;
	}

	public String getFour() {
		return four;
	}

	public void setFour(String four) {
		this.four = four;
	}

	public String getFive() {
		return five;
	}

	public void setFive(String five) {
		this.five = five;
	}

	public String getSix() {
		return six;
	}

	public void setSix(String six) {
		this.six = six;
	}

	public String getSeven() {
		return seven;
	}

	public void setSeven(String seven) {
		this.seven = seven;
	}

	public String getEight() {
		return eight;
	}

	public void setEight(String eight) {
		this.eight = eight;
	}

	public String getNine() {
		return nine;
	}

	public void setNine(String nine) {
		this.nine = nine;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getEleven() {
		return eleven;
	}

	public void setEleven(String eleven) {
		this.eleven = eleven;
	}

	public String getTwelve() {
		return twelve;
	}

	public void setTwelve(String twelve) {
		this.twelve = twelve;
	}

	public String getOtherText() {
		return otherText;
	}

	public void setOtherText(String otherText) {
		this.otherText = otherText;
	}

	public String getMainTell() {
		return mainTell;
	}

	public void setMainTell(String mainTell) {
		this.mainTell = mainTell;
	}

	public String getNowHistory() {
		return nowHistory;
	}

	public void setNowHistory(String nowHistory) {
		this.nowHistory = nowHistory;
	}

	public String getPastHistory() {
		return pastHistory;
	}

	public void setPastHistory(String pastHistory) {
		this.pastHistory = pastHistory;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getLiverPalms() {
		return liverPalms;
	}

	public void setLiverPalms(String liverPalms) {
		this.liverPalms = liverPalms;
	}

	public String getSpider() {
		return spider;
	}

	public void setSpider(String spider) {
		this.spider = spider;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getLungSound() {
		return lungSound;
	}

	public void setLungSound(String lungSound) {
		this.lungSound = lungSound;
	}

	public String getBosomWater() {
		return bosomWater;
	}

	public void setBosomWater(String bosomWater) {
		this.bosomWater = bosomWater;
	}

	public String getHeartSpeed() {
		return heartSpeed;
	}

	public void setHeartSpeed(String heartSpeed) {
		this.heartSpeed = heartSpeed;
	}

	public String getHeartLaw() {
		return heartLaw;
	}

	public void setHeartLaw(String heartLaw) {
		this.heartLaw = heartLaw;
	}

	public String getNoisy() {
		return noisy;
	}

	public void setNoisy(String noisy) {
		this.noisy = noisy;
	}

	public String getBelly() {
		return belly;
	}

	public void setBelly(String belly) {
		this.belly = belly;
	}

	public String getLiverRib() {
		return liverRib;
	}

	public void setLiverRib(String liverRib) {
		this.liverRib = liverRib;
	}

	public String getSword() {
		return sword;
	}

	public void setSword(String sword) {
		this.sword = sword;
	}

	public String getSpleenRib() {
		return spleenRib;
	}

	public void setSpleenRib(String spleenRib) {
		this.spleenRib = spleenRib;
	}

	public String getBellyWater() {
		return bellyWater;
	}

	public void setBellyWater(String bellyWater) {
		this.bellyWater = bellyWater;
	}

	public String getLegs() {
		return legs;
	}

	public void setLegs(String legs) {
		this.legs = legs;
	}

	public String getGuBing() {
		return guBing;
	}

	public void setGuBing(String guBing) {
		this.guBing = guBing;
	}

	public String getZongDanBai() {
		return zongDanBai;
	}

	public void setZongDanBai(String zongDanBai) {
		this.zongDanBai = zongDanBai;
	}

	public String getXueHong() {
		return xueHong;
	}

	public void setXueHong(String xueHong) {
		this.xueHong = xueHong;
	}

	public String getGuCao() {
		return guCao;
	}

	public void setGuCao(String guCao) {
		this.guCao = guCao;
	}

	public String getBaiDanBai() {
		return baiDanBai;
	}

	public void setBaiDanBai(String baiDanBai) {
		this.baiDanBai = baiDanBai;
	}

	public String getXueXiaoBan() {
		return xueXiaoBan;
	}

	public void setXueXiaoBan(String xueXiaoBan) {
		this.xueXiaoBan = xueXiaoBan;
	}

	public String getZongDanHong() {
		return zongDanHong;
	}

	public void setZongDanHong(String zongDanHong) {
		this.zongDanHong = zongDanHong;
	}

	public String getJiaTaiDanBai() {
		return jiaTaiDanBai;
	}

	public void setJiaTaiDanBai(String jiaTaiDanBai) {
		this.jiaTaiDanBai = jiaTaiDanBai;
	}

	public String getDanJianZhiMei() {
		return danJianZhiMei;
	}

	public void setDanJianZhiMei(String danJianZhiMei) {
		this.danJianZhiMei = danJianZhiMei;
	}

	public String getZhiDanHong() {
		return zhiDanHong;
	}

	public void setZhiDanHong(String zhiDanHong) {
		this.zhiDanHong = zhiDanHong;
	}

	public String getBaiXiBao() {
		return baiXiBao;
	}

	public void setBaiXiBao(String baiXiBao) {
		this.baiXiBao = baiXiBao;
	}

	public String getNingXue() {
		return ningXue;
	}

	public void setNingXue(String ningXue) {
		this.ningXue = ningXue;
	}

	public String getSpecialCheck() {
		return specialCheck;
	}

	public void setSpecialCheck(String specialCheck) {
		this.specialCheck = specialCheck;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

}
