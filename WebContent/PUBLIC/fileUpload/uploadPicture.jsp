<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传图片</title>
<script type="text/javascript" src="../../PUBLIC/Scripts/common.js"></script>
<script type="text/javascript" src="../../PUBLIC/Scripts/jsloader.js"></script>
<script type="text/javascript" src="../../PUBLIC/Scripts/jsloader_jquery.js"></script>
<link type="text/css" rel="stylesheet" href="../../PUBLIC/Scripts/MyJavascripts/tableform/table.css"/>
<script type="text/javascript" src="../../PUBLIC/Scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../PUBLIC/Scripts/MyJavascripts/tableform/FormUtil.js"></script>
<script type="text/javascript">
function checkName(){
   var filePath = document.getElementById("file1").value;
		var startNum = filePath.lastIndexOf("\\");
		var fileName = filePath.substring(startNum+1,filePath.toString().length);
		var endNum = fileName.indexOf(".");
		var fileEndName = fileName.substring(endNum+1,fileName.length);
		if("GIF" != fileEndName&&"gif" != fileEndName && "JPG" != fileEndName&&"jpg" != fileEndName && "PNG" != fileEndName&&"png" != fileEndName&&"BMP" != fileEndName&& "bmp" != fileEndName && "JPEG" != fileEndName&&"jpeg" != fileEndName){
			window.alert("请上传图片后缀是gif、jpg、png、bmp、jpeg格式的图片");
			return;
		}
		document.getElementById("uploadForm").submit();
}
</script>
</head>

<%
String newFileNameStr = (String)request.getAttribute("newFileNameStr");
%>
<body>
	<form name="uploadForm" method="post" id="uploadForm" enctype="multipart/form-data" action="/TCMP/UploadAction.do?method=uploadMutiFile&uploadType=image">
		<div id="files" style="">
		<input name="imgFile" type="file" style="width:220px;" id="file1"/><input type="button" value="上传" onclick="checkName()"/>
		</div>
		<div id="first">
		<% if(newFileNameStr != null && newFileNameStr != ""){
		   String pictureName = newFileNameStr.split("\\*")[0];
		   //pictureName = URLEncoder.encode(pictureName);
		   out.print("<img src='/TCMP/image/"+pictureName+"' id='picDiv' style='width:320px;max-height:200px'/>");
		}%>
		</div>
	</form>
	
</body>
</html>
