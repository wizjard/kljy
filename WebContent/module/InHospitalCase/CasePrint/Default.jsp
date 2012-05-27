<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>入院记录打印（默认）</title>
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
		pageInit(0,'入院记录');
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
