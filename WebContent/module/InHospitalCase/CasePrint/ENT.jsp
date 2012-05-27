<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>入院记录打印（耳鼻喉）</title>
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
		<!-- 体格检查 -->
		<jsp:include page="module/PhysicalExamination_Liver.jsp"></jsp:include>
		
		<div  id="SpecialExamination">
			<table class="conTable" border=0 cellpadding=0 cellspacing=0>
				<tr>
					<td class="td1" width="85" valign="top"><b>专科检查：</b></td>
					<td class="td2">
						<table class="conTable" border="0" cellpadding=0 cellspacing=0>
							<tr>
								<td width="12.5%" class="td1"><img src="../ENT/CaseDetails/specail_erl.jpg"/></td>
								<td class="td2" rowspan="2">
									<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
                                    	<tr>
                                        	<td colspan="6"><b style="font-size:16px">耳廓：</b></td>
                                        </tr>
                                        <tr>
                                        	<td class="td2" width="33%">充血：<span name="erkou_chongxue">&nbsp;</span></td>
                                            <td class="td2" width="33%">水肿：<span name="erkou_shuizhong">&nbsp;</span></td>
                                            <td class="td2" width="33%">&nbsp;<span>&nbsp;</span></td>
                                        </tr>
                                        <tr>
                                        	<td class="td2" colspan="3">肿物：<span name="erkou_zhongwu">&nbsp;</span></td>
                                        </tr>
                                         <tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="erkou_other">&nbsp;</span></td>
                                        </tr>
                                    </table>
								</td>
							</tr>
							<tr>
								<td class="td1"><img src="../ENT/CaseDetails/specail_err.jpg"/></td>
							</tr>
							<tr>
								<td class="t1"><img src="../ENT/CaseDetails/specail_waierl.jpg"/></td>
								<td class="t2" rowspan="2">
									<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
										<tr>
											<td colspan="3"><b style="font-size:16px">外耳道：</b></td>
										</tr>
										<tr>
											<td class="td2" width="33%">充血：<span name="waierdao_chongxue">&nbsp;</span></td>
                                            <td class="td2" width="33%">水肿：<span name="waierdao_shuizhong">&nbsp;</span></td>
                                            <td class="td2" width="33%">疖肿： <span name="waierdao_jiezhong">&nbsp;</span></td>
										</tr>
										<tr>
                                        	<td class="td2" colspan="3">肿物：<span name="waierdao_zhongwu">&nbsp;</span></td>
                                        </tr>
                                        <tr>
                                        	<td class="td2">瘘口：<span name="waierdao_loukou">&nbsp;</span></td>
                                        	<td class="td2">狭窄：<span name="waierdao_xiazhai">&nbsp;</span></td>
                                        	<td class="td2">&nbsp;<span>&nbsp;</span></td>
                                        </tr>
                                        <tr>
                                        	<td class="td2" colspan="3">分泌物：<span name="waierdao_fenbiwu">&nbsp;</span></td>
                                        </tr>
                                        <tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="waierdao_other">&nbsp;</span></td>
                                        </tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="t1"><img src="../ENT/CaseDetails/specail_waierr.jpg"/></td>
							</tr>
							
							<tr>
								<td class="t1"><img src="../ENT/CaseDetails/specail_gumol.jpg"/></td>
								<td class="t2" rowspan="2">
									<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
										<tr>
											<td class="td2" colspan="3"><b style="font-size:16px">鼓膜：</b></td>
										</tr>
										<tr>
											<td class="td2" width="33%">颜色：<span name="gumo_yanse">&nbsp;</span></td>
											<td class="td2" width="33%">标志：<span name="gumo_biaozhi">&nbsp;</span></td>
											<td class="td2" width="33%">充血：<span name="gumo_chongxue">&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2">肿胀：<span name="gumo_zhongzhang">&nbsp;</span></td>
											<td class="td2">内陷：<span name="gumo_neixian">&nbsp;</span></td>
											<td class="td2">增厚：<span name="gumo_zenghou">&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2">鼓室积液：<span name="gumo_jiye">&nbsp;</span></td>
											<td class="td2">鼓室内肉芽：<span name="gumo_rouya">&nbsp;</span></td>
											<td class="td2">胆脂瘤：<span name="gumo_danzhiliu">&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">穿孔：<span name="gumo_chuankong">&nbsp;</span></td>
										</tr>
										<tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="gumo_other">&nbsp;</span></td>
                                        </tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="t1"><img src="../ENT/CaseDetails/specail_gumor.jpg"/></td>
							</tr>
							
							<tr>
								<td class="t1"><img src="../ENT/CaseDetails/specail_biqiang.jpg"/></td>
								<td class="t2">
									<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
										<tr>
											<td colspan="3"><b style="font-size:16px">外鼻：</b></td>
										</tr>
										<tr>
											<td class="td2" width="33%">皮肤：<span name="waibi_pifu">&nbsp;</span></td>
											<td class="td2" width="33%">鼻梁歪曲：<span name="waibi_waiqu">&nbsp;</span></td>
											<td class="td2" width="33%">鞍鼻：<span name="waibi_anbi">&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3"><b style="font-size:16px">鼻前庭：</b></td>
										</tr>
										<tr>
											<td class="td2">红肿：<span name="biqianting_hongzhong">&nbsp;</span></td>
											<td class="td2">隆起：<span name="biqianting_longqi">&nbsp;</span></td>
											<td class="td2">皮肤增厚粗糙：<span name="biqianting_cucao">&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3"><b style="font-size:14px">鼻腔：</b></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">肿物：<span name="biqiang_zhongwu">&nbsp;</span></td>
										</tr>
										<tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="biqian_other">&nbsp;</span></td>
                                        </tr>
										<tr>
											<td class="td2" colspan="3"><b style="font-size:16px">鼻甲：</b></td>
										</tr>
										<tr>
											<td class="td2">肿胀：<span name="bijia_zhongzhang">&nbsp;</span></td>
											<td class="td2">充血：<span name="bijia_chongxue">&nbsp;</span></td>
											<td class="td2">水肿：<span name="bijia_shuizhong">&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2">颜色：<span name="bijia_yanse">&nbsp;</span></td>
											<td class="td2">表面：<span name="bijia_biaomian">&nbsp;</span></td>
											<td class="td2">形态：<span name="bijia_xingtai">&nbsp;</span></td>
										</tr>
										<tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="bijia_other">&nbsp;</span></td>
                                        </tr>
										<tr>
											<td class="td2" colspan="3"><b style="font-size:16px">鼻道：</b></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">分泌物：<span name="bidao_fenbiwu">&nbsp;</span></td>
										</tr>
										<!-- 
										<tr>
											<td class="td2" colspan="3">肿物：<span name="bidao_zhongwu">&nbsp;</span></td>
										</tr>
										 -->
										<tr>
											<td class="td2">钩突肥大：<span name="bidao_feida">&nbsp;</span></td>
											<td class="td2">泡状中甲：<span name="bidao_zhongjia">&nbsp;</span></td>
											<td class="td2">骨嵴/骨棘：<span name="bidao_guji">&nbsp;</span></td>
											<!-- 
											<td class="td2">鼻中隔偏曲：<span name="bidao_pianqu">&nbsp;</span></td>
											 -->
										</tr>
										<tr>
											<td class="td2">粘膜糜烂：<span name="bidao_milan">&nbsp;</span></td>
											<td class="td2">粘膜溃疡：<span name="bidao_kuiyang">&nbsp;</span></td>
											<td class="td2" colspan="3">鼻中隔穿孔：<span name="bidao_chuankong">&nbsp;</span></td>
										</tr>
										
										<tr>
											<td class="td2" colspan="3"><b style="font-size:16px">鼻中隔偏曲：</b></td>
										</tr>
										<tr>
                                        	<td class="td2" colspan="3">鼻中隔偏曲：<span name="bidao_pianqu">&nbsp;</span></td>
                                        </tr>
										<tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="bizhongge_other">&nbsp;</span></td>
                                        </tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="t1"><img src="../ENT/CaseDetails/specail_houbi.jpg"/></td>
								<td class="t2">
									<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
										<tr>
											<td class="td2" colspan="3"><b style="font-size:16px">鼻咽部：</b></td>
										</tr>
										<tr>
											<td class="td2" width="33%">充血：<span name="yanyinwo_chongxue">&nbsp;</span></td>
											<td class="td2" width="33%">水肿：<span name="yanyinwo_shuizhong">&nbsp;</span></td>
											<td class="td2" width="33%">粘膜：<span name="yanyinwo_nianmo">&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">新生物：<span name="yanyinwo_xinshengwu">&nbsp;</span></td>
										</tr>
										<!--  
										<tr>
											<td class="td2" colspan="3"><b style="font-size:16px">咽鼓管圆枕：</b></td>
										</tr>
										<tr>
											<td class="td2">充血：<span name="yggyz_chongxue">&nbsp;</span></td>
											<td class="td2">水肿：<span name="biyandingbi_shuizhong">&nbsp;</span></td>
											<td class="td2">粘膜：<span name="biyandingbi_nianmo">&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">新生物：<span name="yggyz_xinshengwu">&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3"><b style="font-size:16px">鼻咽顶壁：</b></td>
										</tr>
										<tr>
											<td class="td2">充血：<span name="biyandingbi_chongxue">&nbsp;</span></td>
											<td class="td2">水肿：<span name="biyandingbi_shuizhong">&nbsp;</span></td>
											<td class="td2">粘膜：<span name="biyandingbi_nianmo">&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">新生物：<span name="biyandingbi_xinshengwu">&nbsp;</span></td>
										</tr>
										-->
										<tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="biyanbu_other">&nbsp;</span></td>
                                        </tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="t1"><img src="../ENT/CaseDetails/specail_yanbu.jpg"/></td>
								<td class="t2">
									<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
										<tr>
											<td class="td2" colspan="3"><b style="font-size:16px">软腭：</b></td>
										</tr>
										<tr>
											<td class="td2" width="33%">瘫痪：<span name="ruane_tanhuan"></span></td>
											<td class="td2" width="33%">充血：<span name="ruane_chuongxue"></span></td>
											<td class="td2" width="33%">溃疡：<span name="ruane_kuiyan"></span></td>
										</tr>
										<tr>
											<td class="td2">缺损：<span name="ruane_quesun"></span></td>
											<td class="td2">疤痕：<span name="ruane_bahen"></span></td>
											<td class="td2">膨隆：<span name="ruane_penglong"></span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">新生物：<span name="ruane_xinshengwu"></span></td>
										</tr>
										<tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="ruane_other">&nbsp;</span></td>
                                        </tr>
										<tr>
											<td colspan="3"><b style="font-size:16px">悬雍垂：</b></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">悬雍垂：<span name="ruane_xuanyongchui"></span></td>
										</tr>
										<tr>
											<td colspan="3"><b style="font-size:16px">腭扁桃体：</b></td>
										</tr>
										<tr>
											<td class="td2">肿大：<span name="btti_zhongda"></span></td>
											<td class="td2">大小：<span name="btti_daxiao"></span></td>
											<td class="td2">充血：<span name="btti_chongxue"></span></td>
										</tr>
										<tr>
											<td class="td2">脓点：<span name="btti_nongdian"></span></td>
											<td class="td2">溃疡：<span name="btti_kuiyang"></span></td>
											<td class="td2">角化物：<span name="btti_jiaohuawu"></span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">新生物：<span name="btti_xinshengwu"></span></td>
										</tr>
										<tr>
											<td colspan="3"><b style="font-size:16px">咽后壁：</b></td>
										</tr>
										<tr>
											<td class="td2">充血：<span name="yhb_chongxue"></span></td>
											<td class="td2">淋巴滤泡：<span name="yhb_linba"></span></td>
											<td class="td2">粘膜：<span name="yhb_nianmo"></span></td>
										</tr>
										<tr>
											<td class="td2">咽侧索：<span name="yhb_cesuo"></span></td>
											<td class="td2">&nbsp;</td>
											<td class="td2">&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td class="t1"><img src="../ENT/CaseDetails/specail_houbu.jpg"/></td>
								<td class="t2">
									<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
										<tr>
											<td colspan="3"><b style="font-size:16px">会厌：</b></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">充血：<span name="huiyan_chongxue"></span></td>
										</tr>
										<tr>
											<td class="td2" width="33%">肿胀：<span name="huiyan_zhongzhang"></span></td>
											<td class="td2" width="33%">粘膜：<span name="huiyan_nianmo"></span></td>
											<td class="td2" width="33%">抬举：<span name="huiyan_taiju"></span></td>
										</tr>
										<tr>
											<td class="td2"colspan="3">新生物：<span name="huiyan_xinshengwu"></span></td>
										</tr>
										<tr>
											<td colspan="3"><b style="font-size:16px">声带：</b></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">粘膜：<span name="shidai_nianmo"></span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">充血：<span name="shidai_chongxue"></span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">粘膜下出：<span name="shidai_xiachu" >&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">声带肥厚：<span name="shidai_feihou" >&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">声带新生物：<span name="shidai_xinshengwu" >&nbsp;</span></td>
										</tr>
										<tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="shengdai_other">&nbsp;</span></td>
                                        </tr>
										<tr>
											<td colspan="3"><b style="font-size:16px">喉室：</b></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">清晰：<span name="houshi_qingxi" >&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">粘膜：<span name="houshi_nianmo" >&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">新生物：<span name="houshi_xinshengwu" >&nbsp;</span></td>
										</tr>
										<tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="houshi_other">&nbsp;</span></td>
                                        </tr>
										<tr>
											<td colspan="3"><b style="font-size:16px">杓状软骨：</b></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">运动：<span name="shaozhuang_yundong" >&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">充血：<span name="shaozhuang_chongxue" >&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">新生物：<span name="shaozhuang_xinshengwu" >&nbsp;</span></td>
										</tr>
										<tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="shaozhuang_other">&nbsp;</span></td>
                                        </tr>
										<tr>
											<td colspan="3"><b style="font-size:16px">梨状窝：</b></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">粘膜：<span name="lizhuang_nianmo" >&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2">积液：<span name="lizhuang_jiye" >&nbsp;</span></td>
											<td class="td2">形态：<span name="lizhuang_xingtai" >&nbsp;</span></td>
											<td class="td2">&nbsp;</td>
										</tr>
										<tr>
											<td class="td2" colspan="3">肿物：<span name="lizhuang_zhongwu">&nbsp;</span></td>
										</tr>
										<tr>
											<td colspan="3"><b style="font-size:16px">环后隙：</b></td>
										</tr>
										<tr>
											<td class="td2">粘膜：<span name="huanhou_nianmo" >&nbsp;</span></td>
											<td class="td2">积液：<span name="huanhou_jiye" >&nbsp;</span></td>
											<td class="td2">形态：<span name="huanhou_xingtai" >&nbsp;</span></td>
										</tr>
										<tr>
											<td class="td2" colspan="3">声门下区：<span name="huanhou_shengmen" >&nbsp;</span></td>
										</tr>
										<tr>
                                        	<td class="td2" colspan="3">其他异常：<span name="huanhouxi_other">&nbsp;</span></td>
                                        </tr>
                                        <tr>
                                        	<td class="td2" colspan="3">其他：<span name="zuihou_other">&nbsp;</span></td>
                                        </tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
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
		pageInit(3,'入院记录');
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
