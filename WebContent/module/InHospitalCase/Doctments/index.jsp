<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="net.sf.json.*"%>
<%
	String path=request.getRealPath("/")+"/module/InHospitalCase/Doctments/files";
	File file=new File(path);
	JSONArray array=new JSONArray();
	if(file.exists()){
		File[] dirs=file.listFiles();
		for(File f:dirs){
			JSONObject object=new JSONObject();
			object.put("name",f.getName());
			if(f.isDirectory()){
				File[] docs=f.listFiles();
				JSONArray docArray=new JSONArray();
				for(File doc:docs){
					JSONObject docObject=new JSONObject();
					docObject.put("name",doc.getName());
					docArray.add(docObject);
				}
				object.put("files",docArray);
			}else if(f.isFile()){
				//object.put("type","file");	
			}
			array.add(object);
		}
		//out.println(array.toString());
	}else{
		out.println("无文件记录");
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>知情文档下载</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="../../../PUBLIC/Scripts/common.js"></script>
		<script type="text/javascript" src="../../../PUBLIC/Scripts/prototype/chineseascii_pack.js"></script>
		<script type="text/javascript" src="../../../PUBLIC/Scripts/prototype/prototype.js"></script>
		<script type="text/javascript" src="../../../PUBLIC/Scripts/jsloader.js"></script>
		<script type="text/javascript" src="../../../PUBLIC/Scripts/jsloader_jquery.js"></script>
		<script type="text/javascript">
			var data=<%=array.toString()%>;
			$(function(){
				var html='<ul class="folder">';
				$.each(data,function(){
					var folder=this.name;
					html+='<li>&nbsp;&nbsp;<span>+</span>&nbsp;&nbsp;<a href="javascript:" onclick="showFiles(this)">'+folder+'</a>';	
					if(this.files.length>0){
						html+='<ul class="file">';
						$.each(this.files,function(){
							html+='<li><a href='+App.App_Info.BasePath+'/module/InHospitalCase/Doctments/files/'+folder+'/'+this.name+'>'+this.name+'</a></li>';	
						});
						html+='</ul>';	
					}
					html+='</li>';
				});
				html+='</ul>';
				$(html).appendTo($('#files'));
				$('#keyword').keyup(filter);
			});
			function showFiles(_this){
					var ul=$(_this).parent().find('ul');
					var span=$(_this).parent().find('span');
					if(span.text()=='+'){
						span.text('-');
						ul.show();
					}else if(span.text()=='-'){
						span.text('+');
						ul.hide();
					}
			}	
			function filter(){
				var keyword=$('#keyword').val();
				var py='';
				if(keyword&&keyword.length>0){
					keyword=keyword.toLowerCase();
					py=getPY(keyword);
				}
				$.each($('ul.folder').children(),function(){
						var flag=$(this).find('span').eq(0).text();
						if(flag=='+'){
							$(this).find('a').eq(0).click();
						}
						$.each($(this).find('li'),function(){
							var _thisTxt=$(this).text();
							var _thisPy=getPY(_thisTxt);
							if((_thisTxt.indexOf(py) >= 0) || (_thisPy.indexOf(py) >= 0))	{
								$(this).show();	
							}else{
								$(this).hide();
							}
						});
				});
			}
			function getPY(_str){
				var rstValue='';
				_str=_str.replaceAll(' ','');
				var temp=_str.split(''),
				i=-1,
				len=_str.length,
				rst=_str.split('');
				while(++i<len){
					temp[i]=(temp[i].charCodeAt()<1000||temp[i].charCodeAt()>60000)?temp[i]:lookUpWord(temp[i]);
					rstValue=rstValue+temp[i].charAt(0); 
				}
				return rstValue;
			}
		</script>
		<style type="text/css">
			a{
				text-decoration:none;
				color:#000;	
			}
			ul{
				margin:0;
				padding:0;
				list-style:none;
			}
			ul.folder{
				border:solid 1px #999;
				autoHeight:true;
			}
			ul.folder li{
				font-weight:bold;
				font-size:14px;
			}
			ul.file{
					display:none;
			}
			ul.file li{
				font-weight:normal;
				text-indent:2em;
				font-size:12px;
			}
			ul.file a:hover{
					color:#F00;
					text-decoration:underline;
			}
			#filter{
				border:solid 1px #000;	
			}
		</style>
  </head>
  
  <body>
  	<div id="filter">名称/拼音过滤：<input type="text" size="32" id="keyword"/></div>
    <div id="files"></div>
  </body>
</html>
