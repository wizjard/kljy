<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo"%>
<%
	MemberInfo mem = (MemberInfo) session.getAttribute("MemberInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>我的门诊记录</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/common.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_ext.js"></script>
<script type="text/javascript">
	var pid=<%=mem.getPatient().getId()%>;
	Ext
			.onReady(function() {
				
			});
</script>
</head>
<body>
待实现
</body>
</html>