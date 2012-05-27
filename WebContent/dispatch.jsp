<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>电子病历</title>
  </head>
  <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
  <body>
    <%
    	String queryString=request.getQueryString();
    	String str="";
    	String[] temp=queryString.split("&");
    	for(String s:temp){
    		if(s.split("=").length>1){
    			str+=s+"&";
    		}
    	}
    	str+="from=his";
    	
    %>
    <h3 align="center">====请选择需要登录的版本====</h3>
    <p align="center"><a id="to3" href="javascript:to3('<%=basePath%>index_his.jsp?<%=str%>')">电子病历3.0版</a></p>
    <script type="text/javascript">
    	function to3(url){
    		var href=document.getElementById('to3').href;
    		document.getElementById('to3').href="###";
    		setTimeout(function(){
    			document.getElementById('to3').href=href;
    		},5000);
    		location.href=url;
        }
    </script>
    <p align="center"><a href="http://10.1.1.7:8090/FinalProject/index.jsp?<%=str.replaceAll("PatientID=","").replaceAll("Admseqno=","")%>">*电子病历2.0版(仅限查看)*</a></p>
    <p align="center"><a href="http://10.1.1.7:8090/patientcare/index.jsp?<%=str%>&nodispatch=1">*甲流电子病历*</a></p>
    <p style="color:red;line-height:200%;text-indent:2em">电子病历技术支持：7355(内线电话),15010069034（刘工）<br>	
    </p>
    <h3 align="center">====最新通知====</h3>
    <p style="margin-left:30px"><font color="red"><B>1、下载本地最新版PDF浏览器，供PDF打印使用</B></font>。&nbsp;&nbsp;<a href="http://10.1.1.7:8090/TCMP/AdbeRdr1000_zh_CN.exe" target="_blank">点此下载<a/></p>
    		
    <p style="margin-left:30px"><font color="red"><B>2、下载本地安装旧版本的PDF浏览器</B></font>。&nbsp;&nbsp;<a href="http://10.1.1.7:8090/TCMP/FoxitReader_4.1.1_XiaZaiBa.exe" target="_blank">点此下载<a/>   <br/>
		
    
    <p style="line-height:150%;margin-top:20px;color:green;margin-left:30px;margin-right:30px">
    	重要通知：关于大病历与病程记录打印出现问题的解决方案<br/>
    	如果病历打印过程中出现空的项目或者点打印后打印机没有反应的情况，请按以下方法处理：<br/>
    	(0)删除以下两个文件：(a)C:\WINDOWS\system32\CAOSOFT_WEB_PRINT_lodop.ocx (b)C:\WINDOWS\Prefetch下以"INSTALL_LODOP"开头的文件。<br/>
    	(1)下载打印控件install_lodop.exe。（<a href="http://10.1.1.7:8090/TCMP/PUBLIC/Scripts/Lodop/install_lodop.exe" target="_blank">点此下载<a/>）<br/>
    	(2)关闭所有打开的IE浏览器。<br/>
    	(3)双击第一步中下载的文件——install_lodop.exe，安装打印控件，安装成功后版本号为：5.0.0.3。<br/>
    	(4)如果出现升级失败的情况，请打开任务管理器，检查是否存在名为IEXPLORE.EXE的进程，如果存在则结束此进程后再次安装install_lodop.exe。
    </p>
    <p style="line-height:150%;margin-top:20px;color:green;margin-left:30px;margin-right:30px">通知：关于打开PACS系统，电子病历自动关闭的解决方法<br>
    		PACS打开时设置了关闭浏览器，请打开时按SHIFT键。
    </p>
  </body>
</html>
