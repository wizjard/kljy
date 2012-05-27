<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>入院记录打印（外科）</title>
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
		<!-- 专科检查 -->
		<div id="SpecialExamination">
		<table class="conTable" border=0 cellpadding=0 cellspacing=0>
			<tr>
				<caption style="font-weight:bold;letter-spacing:1em">专科检查</caption>
			</tr>
			<tr>
				<td class="td1" width="85"><b>腹部视诊：</b></td>
				<td class="td2">
					<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">外形：<span name="fubu_waixing">&nbsp;</span></td>
								<td class="td2" width="33%">腹壁静脉曲张：<span name="fubu_jingmai">&nbsp;</span></td>
								<td class="td2" width="33%">腹式呼吸：<span name="fubu_huxi">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="3">其它异常：<span name="fubu_shizhen_o">&nbsp;</span></td>
							</tr>
				    </table>
				</td>
			</tr>
			<tr>
				<td class="td1"><b>触诊：</b></td>
				<td class="td2">
					<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="25%">腹壁：<span name="fubu_fubi">&nbsp;</span></td>
								<td class="td2" width="25%">振水音：<span name="fubu_zhenshui">&nbsp;</span></td>
								<td class="td2" width="25%">液波震颤：<span name="fubu_yebo">&nbsp;</span></td>
								<td class="td2" width="25%">肌紧张：<span name="fubu_jijzh">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">压痛：<span name="fubu_yatong">&nbsp;</span></td>
								<td class="td2">反跳痛：<span name="fubu_fantt">&nbsp;</span></td>
								<td class="td2">Murphy's征：<span name="fubu_murphy">&nbsp;</span></td>
								<td class="td2">胆囊：<span name="fubu_dannang">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="4">腹部包块：<span name="fubu_baokuai">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="4">肝脏：<span name="fubu_ganzang">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="4">脾脏：<span name="fubu_pi">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="4">肾脏：<span name="fubu_shen">&nbsp;</span></td>
							</tr>
				    </table>
				</td>
			</tr>
			<tr>
				<td class="td1"><b>叩诊：</b></td>
				<td class="td2">
					<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">肝浊音界：<span name="fubu_ganzhuo">&nbsp;</span></td>
								<td class="td2" width="33%">移动性浊音：<span name="fubu_yidong">&nbsp;</span></td>
								<td class="td2" width="33%">腹水：<span name="fubu_fushui">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2">肝区叩痛：<span name="fubu_ganqukt">&nbsp;</span></td>
								<td class="td2" colspan="2">肾区叩痛：<span name="fubu_shenkt">&nbsp;</span></td>
							</tr>
							<tr>
								<td class="td2" colspan="3">肝上界：肝上界位于右锁骨中线第<span name="fubu_ganshangjie">&nbsp;</span>肋间</td>
							</tr>
				    </table>
				</td>
			</tr>
			<tr>
				<td class="td1"><b>听诊：</b></td>
				<td class="td2">
					<table class="conTable" border=0 cellpadding=0 cellspacing=0 width="100%">
							<tr>
								<td class="td2" width="33%">肠鸣音：<span name="fubu_changming">&nbsp;</span>次/分</td>
								<td class="td2" width="33%">气过水声：<span name="fubu_qishui">&nbsp;</span></td>
								<td class="td2" width="33%">血管杂音：<span name="fubu_xueguan">&nbsp;</span></td>
							</tr>
				    </table>
				</td>
			</tr>
			<tr>
				<td class="td1"><b>其它检查：</b></td>
				<td class="td2" name="specialExamOther">&nbsp;</td>
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
		var tr1=$('tr[title="腹部开始"]');
		var tr2=$('tr[title="腹部结束"]');
		var index1=0;
		var index2=0;
		var trs=$('#PhysicalExamination tr');
		$.each(trs,function(i){
			if($(this).attr('title')==tr1.attr('title')){
				index1=i;
			}else if($(this).attr('title')==tr2.attr('title')){
				index2=i;
				return false;
			}
		});
		trs.filter(function(i){
			if(i>=index1&&i<=index2){
				return true;
			}else{
				return false;
			}
		}).remove();
		pageInit(5,'入院记录');
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
