<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>入院记录打印（中医）</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="../../../PUBLIC/Scripts/common.js"></script>
	<script type="text/javascript" src="../../../PUBLIC/Scripts/jsloader.js"></script>
	<script type="text/javascript" src="../../../PUBLIC/Scripts/jsloader_jquery.js"></script>
	<!-- 引入新的打印函数类 -->
	<script type="text/javascript" src="../../../PUBLIC/Scripts/MyJavascripts/ReportHead.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/print.css"/>
</head>
<body>
<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0"></object>
<!-- 打印按钮 -->
<jsp:include page="module/buttons.jsp"></jsp:include>
<div id="page-con">
	<div style="border-bottom:solid 1px #000;margin-bottom:10px;">确定诊断续打错行调整:输入向下行偏移量后点续打(如需下移1行请输"1"):<input id="rowOffset" type="text" value="" size=1 /><input onclick="testDiag()" type="button" value="确定诊断续打" /></div>
	<div style="border-bottom:solid 1px #000;margin-bottom:10px;">订正诊断续打若有错行:输入向下行偏移量后点续打(如需下移1行请输"1"):<input id="rowOffset2" type="text" value="" size=1 /><input onclick="testDiag2()" type="button" value="订正诊断续打" /></div>
	
	<div id="page-header"></div>
	<div id="content">
		<!-- 病人基本信息 -->
		<jsp:include page="module/PatientInfo.jsp"></jsp:include>
		<!-- 病史 -->
		<jsp:include page="module/CaseHistory.jsp"></jsp:include>
		<!-- 中医四诊 -->
		<div id="TCM4Diag">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0>
				<tr>
					<caption style="font-weight:bold;letter-spacing:1em">中医四诊</caption>
				</tr>
				<tr>
					<td class="td1" width="85"><b>望诊神志：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">神智清晰：<span name="shengzhi_qingxing">&nbsp;</span></td>
								<td class="td2" width="33%">嗜睡：<span name="shengzhi_sheshui">&nbsp;</span></td>
								<td class="td2" width="*">昏聩：<span name="shengzhi_huikui">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">神识恍惚：<span name="shengzhi_huanghu">&nbsp;</span></td>
								<td class="td2">神识昏蒙：<span name="shengzhi_huimeng">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>精神：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">精神萎靡：<span name="jingsheng_jingsheng">&nbsp;</span></td>
								<td class="td2" width="33%">抑郁：<span name="jingsheng_yiyu">&nbsp;</span></td>
								<td class="td2" width="*">烦躁不安：<span name="jingsheng_buan">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">急躁易怒：<span name="jingsheng_yiru">&nbsp;</span></td>
								<td class="td2">谵语：<span name="jingsheng_chanyu">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>形体：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">身体强壮：<span name="xingti_qiangzhuang">&nbsp;</span></td>
								<td class="td2" width="33%">身体瘦弱：<span name="xingti_shouluo">&nbsp;</span></td>
								<td class="td2" width="*">身体肥胖：<span name="xingti_fengpang">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">身体消瘦：<span name="xingti_xiaoshou">&nbsp;</span></td>
								<td class="td2">身重不能转侧：<span name="xingti_zhuangche">&nbsp;</span></td>
								<td class="td2">坐卧不安：<span name="xingti_buan">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">腹壁青筋暴露：<span name="xingti_qingjing">&nbsp;</span></td>
								<td class="td2">腹壁突起：<span name="xingti_tuqi">&nbsp;</span></td>
								<td class="td2">浮肿：<span name="xingti_fuzhong">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">四肢动态：<span name="xingti_dongtai">&nbsp;</span></td>
								<td class="td2">腹部外形：<span name="xingti_waixing">&nbsp;</span></td>
								<td class="td2">甲色：<span name="xingti_jiashe">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>面色：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">面色金黄：<span name="mianshe_jinghuang">&nbsp;</span></td>
								<td class="td2" width="33%">面色暗黄：<span name="mianshe_anhuang">&nbsp;</span></td>
								<td class="td2" width="*">面色萎黄：<span name="mianshe_weihuang">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">面色苍白：<span name="mianshe_changbai">&nbsp;</span></td>
								<td class="td2">面色胱白：<span name="mianshe_guangbai">&nbsp;</span></td>
								<td class="td2">两颧潮红：<span name="mianshe_chaohong">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">面色黧黑：<span name="mianshe_lihei">&nbsp;</span></td>
								<td class="td2">眶周发黑：<span name="mianshe_fahei">&nbsp;</span></td>
								<td class="td2">面色青灰：<span name=mianshe_qinghui>&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">面色青黑：<span name="mianshe_qinghei">&nbsp;</span></td>
								<td class="td2">面青颧赤：<span name="mianshe_echi">&nbsp;</span></td>
								<td class="td2">青赤而晦暗：<span name="mianshe_huian">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>皮肤：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">黄染色鲜明：<span name="pifu_xianming">&nbsp;</span></td>
								<td class="td2" width="33%">黄染色晦暗：<span name="pifu_huian">&nbsp;</span></td>
								<td class="td2" width="*">瘀斑色红：<span name="pifu_shehong">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">瘀斑色暗紫：<span name="pifu_anzhi">&nbsp;</span></td>
								<td class="td2">水肿：<span name="pifu_shuizhong">&nbsp;</span></td>
								<td class="td2">肌肤甲错：<span name="pifu_cuo">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">皮肤弹性：<span name="pifu_tangxing">&nbsp;</span></td>
								<td class="td2">蛛丝赤缕：<span name="pifu_chiluo">&nbsp;</span></td>
								<td class="td2">朱砂掌：<span name="pifu_zhushazhang">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>闻诊：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">语音低微：<span name="wenzheng_diwei">&nbsp;</span></td>
								<td class="td2" width="33%">语音高亢：<span name="wenzheng_gaokang">&nbsp;</span></td>
								<td class="td2" width="*">语言懒言：<span name="wenzheng_laiyan">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">语言多言：<span name="wenzheng_duoyan">&nbsp;</span></td>
								<td class="td2">少气：<span name="wenzheng_shaoqi">&nbsp;</span></td>
								<td class="td2">气短：<span name="wenzheng_qiduan">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">太息：<span name="wenzheng_taixi">&nbsp;</span></td>
								<td class="td2">肝臭：<span name="wenzheng_ganchuo">&nbsp;</span></td>
								<td class="td2">郑声：<span name="wenzheng_zhengsheng">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">独语：<span name="wenzheng_duyu">&nbsp;</span></td>
								<td class="td2">夺气：<span name="wenzheng_duoqi">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>问诊寒热：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">恶寒：<span name="hanre_wuhan">&nbsp;</span></td>
								<td class="td2" width="33%">畏寒：<span name="hanre_weihan">&nbsp;</span></td>
								<td class="td2" width="*">肢冷：<span name="hanre_zhiling">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">局部冷感：<span name="hanre_linggan">&nbsp;</span></td>
								<td class="td2">寒战：<span name="hanre_hanzhang">&nbsp;</span></td>
								<td class="td2">低热：<span name="hanre_dire">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">高热：<span name="hanre_gaore">&nbsp;</span></td>
								<td class="td2">身热不扬：<span name="hanre_buyang">&nbsp;</span></td>
								<td class="td2">自觉发热：<span name="hanre_fare">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">潮热：<span name="hanre_chaore">&nbsp;</span></td>
								<td class="td2">五心烘热：<span name="hanre_wuxinghongre">&nbsp;</span></td>
								<td class="td2">面部烘热：<span name="hanre_mianbuhongre">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">寒热往来：<span name="hanre_hanre">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>肝系症状：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">头昏：<span name="ganxi_touhun">&nbsp;</span></td>
								<td class="td2" width="33%">目干涩：<span name="ganxi_muganse">&nbsp;</span></td>
								<td class="td2" width="*">眩晕：<span name="ganxi_xuanyun">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">视物模糊：<span name="ganxi_mohu">&nbsp;</span></td>
								<td class="td2">四肢抽搐：<span name="ganxi_chouxu">&nbsp;</span></td>
								<td class="td2">烦躁易怒：<span name="ganxi_yiru">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">胁胀：<span name="ganxi_xiezhang">&nbsp;</span></td>
								<td class="td2">善太息：<span name="ganxi_zhantaixi">&nbsp;</span></td>
								<td class="td2">胸胁胀痛：<span name="ganxi_zhangtong">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>脾系症状：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">纳食减少：<span name="pixing_jianshao">&nbsp;</span></td>
								<td class="td2" width="33%">食欲减退：<span name="pixing_jianshu">&nbsp;</span></td>
								<td class="td2" width="*">嗳气：<span name="pixing_aiqi">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">呃逆：<span name="pixing_eli">&nbsp;</span></td>
								<td class="td2">厌油：<span name="pixing_yayou">&nbsp;</span></td>
								<td class="td2">恶心：<span name="pixing_exin">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">呕吐：<span name="pixing_etu">&nbsp;</span></td>
								<td class="td2">脘痞：<span name="pixing_wangpi">&nbsp;</span></td>
								<td class="td2">腹胀：<span name="pixing_fuzhang">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">大便溏薄：<span name="pixing_tangbao">&nbsp;</span></td>
								<td class="td2">大便秘结：<span name="pixing_mijie">&nbsp;</span></td>
								<td class="td2">肌瘦无力：<span name="pixing_wuli">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">肠鸣：<span name="pixing_tangming">&nbsp;</span></td>
								<td class="td2">矢气频作：<span name="pixing_pingzuo">&nbsp;</span></td>
								<td class="td2">脱肛：<span name="pixing_tuogang">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">流涎：<span name="pixing_liuyan">&nbsp;</span></td>
								<td class="td2">口水多：<span name="pixing_koushiuduo">&nbsp;</span></td>
								<td class="td2">周身困重：<span name="pixing_kuizhong">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>肾系症状：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">腰酸：<span name="shenxi_yaoshuan">&nbsp;</span></td>
								<td class="td2" width="33%">腰痛：<span name="shenxi_yaotong">&nbsp;</span></td>
								<td class="td2" width="*">膝软无力：<span name="shenxi_wuli">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">耳鸣：<span name="shenxi_erming">&nbsp;</span></td>
								<td class="td2">阳痿：<span name="shenxi_yangwei">&nbsp;</span></td>
								<td class="td2">抬颈无力：<span name="shenxi_shijing">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">吞咽无力：<span name="shenxi_tunyan">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>心系症状：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">胸闷：<span name="xinxi_xiongmen">&nbsp;</span></td>
								<td class="td2" width="33%">心痛：<span name="xinxi_xintong">&nbsp;</span></td>
								<td class="td2" width="*">惊悸：<span name="xinxi_jingji">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">昏迷：<span name="xinxi_huming">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>肺系症状：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">咳嗽：<span name="feixi_koushou">&nbsp;</span></td>
								<td class="td2" width="33%">咯黄痰：<span name="feixi_kouhuangtan">&nbsp;</span></td>
								<td class="td2" width="*">咯白痰：<span name="feixi_koubaitan">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">咯血：<span name="feixi_kouxie">&nbsp;</span></td>
								<td class="td2">喘促：<span name="feixi_cuanchu">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>汗出：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">自汗：<span name="hanchu_zhihan">&nbsp;</span></td>
								<td class="td2" width="33%">盗汗：<span name="hanchu_daohan">&nbsp;</span></td>
								<td class="td2" width="*">大汗：<span name="hanchu_dahan">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">口渴饮冷：<span name="hanchu_koukou">&nbsp;</span></td>
								<td class="td2">面色苍白：<span name="hanchu_changbai">&nbsp;</span></td>
								<td class="td2">四肢厥冷：<span name="hanchu_jueling">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">手足心汗：<span name="hanchu_xinhan">&nbsp;</span></td>
								<td class="td2">汗出乏力：<span name="hanchu_fali">&nbsp;</span></td>
								<td class="td2">头汗而困重：<span name="hanchu_touhan">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">汗出如油：<span name="hanchu_ruyou">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>口味渴饮：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">口舌生疮：<span name="kouying_koushe">&nbsp;</span></td>
								<td class="td2" width="33%">口臭：<span name="kouying_kouchou">&nbsp;</span></td>
								<td class="td2" width="*">口淡：<span name="kouying_koudan">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">口粘腻：<span name="kouying_koulianning">&nbsp;</span></td>
								<td class="td2">口苦：<span name="kouying_kouku">&nbsp;</span></td>
								<td class="td2">口甜：<span name="kouying_koutian">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">口味与渴饮：<span name="kouying_kouying">&nbsp;</span></td>
								<td class="td2">睡眠：<span name="kouying_shuimian">&nbsp;</span></td>
								<td class="td2">口干：<span name="kouying_kougan">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">口渴不欲饮：<span name="kouying_buyuying">&nbsp;</span></td>
								<td class="td2">口渴喜热饮：<span name="kouying_xireying">&nbsp;</span></td>
								<td class="td2">口渴喜冷饮：<span name="kouying_xilingying">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>睡眠：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">嗜睡：<span name="shuimian_sheshui">&nbsp;</span></td>
								<td class="td2" width="33%">寐差多梦：<span name="shuimian_meica">&nbsp;</span></td>
								<td class="td2" width="*">整夜失眠：<span name="shuimian_shimian">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">不易入睡(>1h)：<span name="shuimian_buyilushui">&nbsp;</span></td>
								<td class="td2">睡后易醒：<span name="shuimian_yixing">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>大小便：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">自利：<span name="daxiaobian_zhili">&nbsp;</span></td>
								<td class="td2" width="33%">小便短赤：<span name="daxiaobian_duanci">&nbsp;</span></td>
								<td class="td2" width="*">小便清长：<span name="daxiaobian_qingchang">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">大便粘滞不爽：<span name="daxiaobian_bushuang">&nbsp;</span></td>
								<td class="td2">排便无力：<span name="daxiaobian_wuli">&nbsp;</span></td>
								<td class="td2">便溏：<span name="daxiaobian_biantang">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">腹泻：<span name="daxiaobian_fuxie">&nbsp;</span></td>
								<td class="td2">矢气不畅：<span name="daxiaobian_shiqi">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>月经：</b></td>
					<td class="td2" name="tcmp4_menstrual">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">月经周期：<span name="yuejing_zhouqi">&nbsp;</span></td>
								<td class="td2" width="33%">月经质：<span name="yuejing_zhi">&nbsp;</span></td>
								<td class="td2" width="*">经期乳房作胀：<span name="yuejing_zhuzhang">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">月经量：<span name="yuejing_liang">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>出血：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">鼻衄：<span name="chuxie_bixie">&nbsp;</span></td>
								<td class="td2" width="33%">齿衄：<span name="chuxie_chixie">&nbsp;</span></td>
								<td class="td2" width="*">便血：<span name="chuxie_bianxie">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">呕血：<span name="chuxie_exie">&nbsp;</span></td>
								<td class="td2">腹泻：<span name="chuxie_fuxie">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>按诊：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">身热：<span name="anzhen_shenre">&nbsp;</span></td>
								<td class="td2" width="33%">身寒：<span name="anzhen_shenghan">&nbsp;</span></td>
								<td class="td2" width="*">喜按：<span name="anzhen_xian">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">拒按：<span name="anzhen_juan">&nbsp;</span></td>
								<td class="td2">下肢水肿：<span name="anzhen_shuizhong">&nbsp;</span></td>
								<td class="td2">腹部压痛：<span name="anzhen_fubuyatong">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="3">腹部反跳痛：<span name="anzhen_fatiaotong">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="3">腹部胀满，按之充实，叩声重浊：<span name="anzhen_zhongzuo">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="3">腹部胀满，按之充实，叩之空声：<span name="anzhen_kongsheng">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>舌诊舌质：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">舌质淡：<span name="shezhi_shezhidan">&nbsp;</span></td>
								<td class="td2" width="33%">舌质淡红：<span name="shezhi_shezhidanhong">&nbsp;</span></td>
								<td class="td2" width="*">舌质红：<span name="shezhi_shezhihong">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">舌尖红：<span name="shezhi_shejianhong">&nbsp;</span></td>
								<td class="td2">舌质红绛：<span name="shezhi_shezhihongjian">&nbsp;</span></td>
								<td class="td2">舌质暗：<span name="shezhi_shezhihongan">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">舌质青紫：<span name="shezhi_shezhiqingzhi">&nbsp;</span></td>
								<td class="td2">舌质瘀点：<span name="shezhi_shezhiyudian">&nbsp;</span></td>
								<td class="td2">舌质瘀斑：<span name="shezhi_shezhiyuban">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="3">其它：<span name="shezhi_qita">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>舌体：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">舌体胖：<span name="sheti_shetipang">&nbsp;</span></td>
								<td class="td2" width="33%">舌体瘦：<span name="sheti_shetishou">&nbsp;</span></td>
								<td class="td2" width="*">舌体嫩：<span name="sheti_shetire">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">舌有齿痕：<span name="sheti_sheyouchiheng">&nbsp;</span></td>
								<td class="td2">裂纹：<span name="sheti_liewen">&nbsp;</span></td>
								<td class="td2">&nbsp;</td>
							</tr>
							<tr>
								<td class="td2" colspan="3">其它：<span name="sheti_qita">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>舌苔：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">舌苔白：<span name="shetai_shetaibai">&nbsp;</span></td>
								<td class="td2" width="33%">舌苔黄：<span name="shetai_shetaihuang">&nbsp;</span></td>
								<td class="td2" width="*">舌苔薄：<span name="shetai_shetaibo">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">舌苔厚：<span name="shetai_shetaihou">&nbsp;</span></td>
								<td class="td2">舌苔腻：<span name="shetai_shetaier">&nbsp;</span></td>
								<td class="td2">舌苔润：<span name="shetai_shetairen">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">舌苔滑：<span name="shetai_shetaihua">&nbsp;</span></td>
								<td class="td2">舌苔干：<span name="shetai_shetaigan">&nbsp;</span></td>
								<td class="td2">舌苔烦：<span name="shetai_shetaifan">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">舌苔焦：<span name="shetai_shetaijiao">&nbsp;</span></td>
								<td class="td2">舌苔少：<span name="shetai_shetaishao">&nbsp;</span></td>
								<td class="td2">无苔：<span name="shetai_wutai">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">剥脱苔：<span name="shetai_botuotai">&nbsp;</span></td>
								<td class="td2">灰黑苔：<span name="shetai_huiheitai">&nbsp;</span></td>
								<td class="td2">舌苔腐：<span name="shetai_sheyutai">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="3">其它：<span name="shetai_qita">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>舌底脉络：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="50%">舌底脉络青紫：<span name="shedimai_qingzhi">&nbsp;</span></td>
								<td class="td2" width="*">舌底脉络迂曲：<span name="shedimai_yuqu">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>基本脉象：</b></td>
					<td class="td2">
						<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">浮脉：<span name="jibenmai_fumai">&nbsp;</span></td>
								<td class="td2" width="33%">沉脉：<span name="jibenmai_chengmai">&nbsp;</span></td>
								<td class="td2" width="*">迟脉：<span name="jibenmai_chimai">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">数脉：<span name="jibenmai_shumai">&nbsp;</span></td>
								<td class="td2">疾脉：<span name="jibenmai_jimai">&nbsp;</span></td>
								<td class="td2">细脉：<span name="jibenmai_ximai">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">弱脉：<span name="jibenmai_ruomai">&nbsp;</span></td>
								<td class="td2">微脉：<span name="jibenmai_weimai">&nbsp;</span></td>
								<td class="td2">虚脉：<span name="jibenmai_xumai">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">实脉：<span name="jibenmai_shimai">&nbsp;</span></td>
								<td class="td2">缓脉：<span name="jibenmai_huanmai">&nbsp;</span></td>
								<td class="td2">濡脉：<span name="jibenmai_rumai">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">滑脉：<span name="jibenmai_huamai">&nbsp;</span></td>
								<td class="td2">涩脉：<span name="jibenmai_semai">&nbsp;</span></td>
								<td class="td2">弦脉：<span name="jibenmai_xuemai">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">结脉：<span name="jibenmai_jiemai">&nbsp;</span></td>
								<td class="td2">代脉：<span name="jibenmai_daimai">&nbsp;</span></td>
								<td class="td2">促脉：<span name="jibenmai_chumai">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="3">其它：<span name="jibenmai_qita">&nbsp;</span></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td1"><b>四诊分析：</b></td>
					<td class="td2" name="sizhenfenxi">&nbsp;</td>
				</tr>
			</table>
		</div>
		<!-- 辅助检查 -->
		<jsp:include page="module/LabExamination.jsp"></jsp:include>
		<!-- 诊断 -->
		<jsp:include page="module/Diagnosis.jsp"></jsp:include>
		<!-- 订正诊断 -->
		<jsp:include page="module/RevisedDiagnosis.jsp"></jsp:include>
	</div>
	<div id="page-footer"></div>
</div>
</body>
</html>
<script type="text/javascript" src="scripts/init.js"></script>
<script type="text/javascript" src="scripts/button.js"></script>
<script type="text/javascript">
	$(function(){
		KID=App.util.getHtmlParameters('KID');
		pageInit(6,'中医入院记录');
	});
	function testDiag(){
		var val=$("#rowOffset").val();
		if(!isNaN(val) && val > 0){
				var html = '';
				for(var i = 0; i < val; i++){
						html += '<tr name="temp-tr"><td class="td2" colspan="2">&nbsp;</td><td class="td2" colspan="2">&nbsp;</td></tr>';
				}
				$('#Diagnosis table.conTable tbody').prepend($(html));
		}	
		$('#next-Diagnosis').click();
		$('tr[name="temp-tr"]').remove();
	}
	function testDiag2(){
		var val=$("#rowOffset2").val();
		if(!isNaN(val) && val > 0){
				var html = '';
				for(var i = 0; i < val; i++){
						html += '<tr name="temp-tr2"><td class="td2" colspan="2">&nbsp;</td><td class="td2" colspan="2">&nbsp;</td></tr>';
				}
				$('#Diagnosis table.conTable tbody').prepend($(html));
		}	
		$('#next-RevisedDiagnosis').click();
		$('tr[name="temp-tr2"]').remove();
	}
</script>
