<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.io.FileInputStream" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>medexImage</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="">

  </head>
  
  <body>
<%
String filename = request.getParameter("file");
String item = request.getParameter("item");
FileInputStream is = null;
try{
is=new FileInputStream("d:\\medex\\"+item+"\\"+filename);
}catch(Exception e){
	out.println("对不起，没有找到图片。");
}
if(is!=null){
response.reset();
response.setContentType("image/jpeg");         
ServletOutputStream sos = response.getOutputStream();      
byte[] buffer = new byte[1024];
int len=0;
while((len=is.read(buffer))>0){
  sos.write(buffer,0,len); 
}  
  sos.flush();      
  sos.close(); 
}	
%>
  </body>
</html>
