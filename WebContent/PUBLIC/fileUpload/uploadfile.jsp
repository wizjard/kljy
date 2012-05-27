<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>上传文件</title>
<script type="text/javascript" src="../../PUBLIC/Scripts/common.js"></script>
<script type="text/javascript" src="../../PUBLIC/Scripts/jsloader.js"></script>
<script type="text/javascript" src="../../PUBLIC/Scripts/jsloader_jquery.js"></script>
<script type="text/javascript" src="../../PUBLIC/Scripts/jsloader_ext.js"></script>
<link type="text/css" rel="stylesheet" href="../../PUBLIC/Scripts/MyJavascripts/tableform/table.css"/>
<script type="text/javascript" src="../../PUBLIC/Scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../PUBLIC/Scripts/MyJavascripts/tableform/FormUtil.js"></script>

<script type="text/javascript">
var fileCount = 1;
//增加一个文件上传标签
function addFileForm() {
   var input = null;
   
   if(document.all) {
      input = document.createElement("<input name='uploadFile'>");
   }
   else{
      input = document.createElement("input");
   }
   input.type="file"
   document.getElementById("files").appendChild(input);
   input.id = "file" + (fileCount + 1);
   input.name="uploadFile";
   fileCount++;
}
//减少一个文件上传标签
function removeFileForm() {
  var input = document.getElementById("file" + fileCount)
   document.getElementById("files").removeChild(input);
   fileCount--;
}
//检查文件格式
function checkFileType(){
  var fileArr = document.getElementsByName("uploadFile");
  var j=0;
  for(var i=1;i<=fileArr.length;i++){
     var filePath = document.getElementById("file"+i).value;
     if(filePath != null && filePath!=""){
        var startNum = filePath.lastIndexOf("\\");
		var fileName = filePath.substring(startNum+1,filePath.toString().length);
		var endNum = fileName.lastIndexOf(".");
		var fileEndName = fileName.substring(endNum+1,fileName.length);
		if("GIF"!= fileEndName&&"zip" != fileEndName&&"ZIP" != fileEndName && "rar" != fileEndName&&"RAR" != fileEndName&&"JPG" != fileEndName&&"doc" != fileEndName&& "docx" != fileEndName&& "xls" != fileEndName&& "XLSX" != fileEndName&& "XLS" != fileEndName&& "DOC" != fileEndName&& "DOCX" != fileEndName&& "xlsx" != fileEndName&& "PDF" != fileEndName&&"pdf" != fileEndName&& "PNG" != fileEndName&&"png" != fileEndName&& "GIF" != fileEndName&&"jpg" != fileEndName&& "gif" != fileEndName&& "JPEG" != fileEndName&&"jpeg" != fileEndName&& "BMP" != fileEndName&&"bmp" != fileEndName&& "txt" != fileEndName&& "TXT" != fileEndName){
			window.alert("请上传文件后缀是rar或者zip或者图片、WORD、TXT、EXCEL、PDF格式的文件");
			return;
		}
		j++;
    }
 }
  if(j==0){
    alert("请至少选择一个上传文件");
    return;
  }
  document.getElementById("uploadForm").submit();
}

//删除一个已上传的文件事件
function deleteItem(id, fileName){
  var display_attach = document.getElementById("display_attach");
  var reDiv = document.getElementById(id);
  if(reDiv == null) {
    return;
  }
  display_attach.removeChild(reDiv);
  setTimeout(function() {
   document.getElementById("reFiled").value = fileName;
  document.getElementById("refreshForm").submit();
  }, 300);
}

//下载事件
function download(fileName){
	document.getElementById("fileName").value = fileName;
	document.getElementById("fileNameForm").submit();
	//document.getElementById("uploadForm").enctype = "multipart/form-data";
	//document.getElementById("uploadForm").action="/TCMP/UploadAction.do?method=uploadMutiFile";
}

</script>
</head>
<%
String newFileNameStr = (String)request.getAttribute("newFileNameStr");
if(newFileNameStr == null) {
  newFileNameStr = "";
}
String newFileNameStrEncode = URLEncoder.encode(newFileNameStr, "utf-8");
String erroMsg = (String)request.getAttribute("erroMsg");
String erroTip="";
if(!"".equals(erroMsg)&&erroMsg!=null){
       erroTip = erroMsg;
     }
%>

<body>
<form action="/TCMP/DownloadAction.do?method=downloadFile" method="post" id="fileNameForm">
<input type="hidden" name="fileUploadStr" value="<%=newFileNameStr%>" id="fileUploadStr"/>
<input type="hidden" name="fileName" value="" id="fileName"/>
</form>
<form action="/TCMP/UploadAction.do?method=refreshFiled" method="post" id="refreshForm">
<input type="hidden" name="filed" value="<%=newFileNameStr%>" id="filed"/>
<input type="hidden" value="" name="refiled" id="reFiled">
</form>
	<form name="uploadForm" method="post" id="uploadForm" enctype="multipart/form-data" action="/TCMP/UploadAction.do?method=uploadMutiFile&filed=<%=newFileNameStrEncode%>">
		<div id="files" style="">
		<input name="uploadFile" type="file" style="width:220px;" id="file1"/> 
		&nbsp;<input type="button" value="上 传" onclick="checkFileType()"/>
		<font color="red" size="2">(每个上传文件不超过4M)</font>
		</div>
	 <!--  	<input type="button" onclick="addFileForm()" value="增 加">
		<input type="button" onclick="removeFileForm()" value="删 除"> -->
		<div id="display_attach">
		<%if(newFileNameStr!=null&&!newFileNameStr.equals("")){
		  String[] newFileNameStrArr = newFileNameStr.split("\\*");
		for(int i=0;i<newFileNameStrArr.length;i++){
		  int end = newFileNameStrArr[i].lastIndexOf("_");
		  String displayName = newFileNameStrArr[i].substring(0,end);
		  int dianEnd = newFileNameStrArr[i].lastIndexOf(".");
		  String ext = newFileNameStrArr[i].substring(dianEnd+1);
          out.println("<div id='div" + i + "'><a href='javascript:download(\""+newFileNameStrArr[i]+"\")'>"+displayName.concat(".").concat(ext)+"</a>      <font color='red'><a href='javascript:deleteItem(\"div"+i+"\", \"" +newFileNameStrArr[i]  + "\")'> 删除 </a></font> <br>   </div> ");
        }
      } %>
		</div>                  
		<font color="red" size="2"><%=erroTip%></font>
	</form>
</body>
</html>
