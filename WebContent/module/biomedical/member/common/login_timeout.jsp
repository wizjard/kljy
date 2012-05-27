<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>康乐家园-会员登录超时</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/PUBLIC/Scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/PUBLIC/Scripts/jsloader.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/PUBLIC/Scripts/jsloader_ext.js"></script>
<script type="text/javascript">
Ext.onReady(function(){
	Ext.get('h1').center();
	Ext.select('h1 a').on('click',function(){
		top.location.href='<%=request.getContextPath()%>/module/biomedical/member/login.jsp';
	});
});
</script>
<style type="text/css">
h1{
	position:absolute;
}
</style>
</head>
<body>
<h1 id="h1">登录超时或者未登录，请返回登录界面<a href="###">重新登录</a>。</h1>
</body>
</html>