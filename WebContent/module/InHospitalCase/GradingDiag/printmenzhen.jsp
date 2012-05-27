<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>肝病十二级分类诊断系统结果-打印</title>
<script type="text/javascript">
function print(str){
	document.getElementsByName('data')[0].value=str;
	document.getElementById('form').submit();
}
</script>
</head>
<body>
<form target="_blank" id="form" name="form" action="<%=request.getContextPath()%>/GradingDiagAction.do?method=print1" method="post">
<input type="hidden" name="data" value=""/>
</form>
</body>
</html>