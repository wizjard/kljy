<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'planList.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <table border="1">
  <!-- 表头 -->
	  <THEAD>
						<TR >
							<TD>
								计划来访日期
							</TD>
							<TD>
								实际来访日期 
							</TD>
							<TD >
								全选
							</TD>
							<TD >
								检查项目
							</TD>
							<TD >
								检查结果
							</TD>
							<TD>
								执行状态
							</TD>
						</TR>
	</THEAD>
  		<!--显示数据列表-->
				<TBODY>
					<c:forEach items="${nearPlans}" var="plan">
						<TR CLASS="TableDetail1 template">
							<TD>
								${plan.planDate}&nbsp;
							</TD>
							<TD>
								${plan.visitDate}&nbsp;
							</TD>
							<TD>
								<input type="checkbox"></input>&nbsp;
							</TD>
							
							<TD>
								<c:forEach items="${plan.planItems}" var="item">
								<input type="checkbox"></input>
								${item.checkItemName}<br/> </c:forEach>
							</TD>
							<TD>
								${plan.canChange}&nbsp;
							</TD>
							
							<TD>
								xxx
							</TD>
							
						</TR>
					</c:forEach>
				</TBODY>
  </table>
  </body>
</html>
