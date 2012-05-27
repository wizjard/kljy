<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>康乐家园-会员首页</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/PUBLIC/Scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/PUBLIC/Scripts/jsloader.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/PUBLIC/Scripts/jsloader_ext.js"></script>
<style type="text/css">
body,form,p,div{
	margin:0;
	font-size:12px;
}
.memberInfo{}
.memberInfo span.label{
	font-size:12px;
	color:blue;
}
.memberInfo span.text{
	font-size:12px;
}
</style>
<script type="text/javascript">
Ext.onReady(function(){
	new Ext.Panel({
		title:'个人信息',
		width:200,
		autoHeight:true,
		margins:'5 5 5 5',
		collapsible:true,
		frame:true,
		style:'margin:5px',
		listeners:{
			render:function(){
				this.body.dom.appendChild(Ext.get('memberInfo').dom);
			}
		}
	}).render(Ext.getBody());
});
</script>
</head>
<body>
<div id="memberInfo">
<%
	MemberInfo mem=(MemberInfo)session.getAttribute("MemberInfo");
	if(mem!=null){
		out.println("<ul class=memberInfo>");
		out.println("<li><span class=label>&nbsp;姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span><span class=text>"+mem.getPatient().getPatientName()+"</span></li>");
		out.println("<li><span class=label>&nbsp;病&nbsp;案&nbsp;号：</span><span class=text>"+mem.getPatient().getPatientNo()+"</span></li>");
		out.println("<li><span class=label>&nbsp;用&nbsp;户&nbsp;名：</span><span class=text>"+mem.getAccount()+"</span></li>");
		out.println("<li><span class=label>会员编号：</span><span class=text>"+mem.getMemberNo()+"</span></li>");
		out.println("</ul>");
	}
%>
</div>
</body>
</html>