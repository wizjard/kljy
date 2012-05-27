function RepoertHeader(){
	this.el=null;
	this.pid=null;
	this.cid=null;
	this.css='#page-header{ position:relative; border-bottom:solid 1px #000; height:95px; } #page-header div.img{ position:absolute; left:0; top:0; width:60px; } #page-header img{ width:60px; } #page-header div.zh{ position:absolute; left:60px; top:5px; font-size:22px; font-weight:bold; } #page-header div.en{ position:absolute; left:62px; top:30px; letter-spacing:1px; font-size:12px; font-weight:bold; } #page-header div.title{ position:absolute; top:45px; font-size:26px; font-weight:bold; letter-spacing:10px; left:0; width:100%; text-align:center; } #page-header div.pageInfo{ position:absolute; bottom:0; left:0; width:100%; font-size:11px; } #page-header div.pageInfo td.inhsptimes{ width:150px; text-align:left; text-indent:60px; vertical-align:text-bottom; } #page-header div.pageInfo td.nameno{ width:150px; text-align:left; vertical-align:text-bottom; } #page-header div.pageInfo td.nameno p{ margin:0; padding:0; } #page-header div.pageInfo td.pageno{ text-align:center; vertical-align:text-bottom; }';
	this.basePath='';
	this.config={
		patientName:'xxx',
		patientNo:'xxxxxx',
		title:'入院记录',
		inHspTimes:'x',
		pageNo:'x'
	};
	this.createXMLHttp=function(){
		var xmlHttp;
		 try
		    {
		   // Firefox, Opera 8.0+, Safari
		    xmlHttp=new XMLHttpRequest();
		    }
		 catch (e)
		    {
		  // Internet Explorer
		   try
		      {
		      xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
		      }
		   catch (e)
		      {
		      try
		         {
		         xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
		         }
		      catch (e)
		         {
		         alert("您的浏览器不支持AJAX！");
		         return false;
		         }
		      }
		    }
		return xmlHttp;
	}
	this.create=function(){
		if(!this.el)return;
		var html=	
					'<div style="position:relative; border-bottom:solid 1px #000; height:105px;">'+
					'<div><img src="'+this.basePath+'/PUBLIC/images/youanLogo.jpg"  style="left:0;top:0;width:50px;height:67px;position:absolute;display:block;"/></div>'+
					'<div style="left:50px;top:28px;width:80%;font-size:18px;font-weight:bold;position:absolute;">首都医科大学附属北京佑安医院</div>'+
					'<div style="left:50px;top:50px;width:80%;font-size:11px;font-weight:bold;position:absolute;">BeiJing YouAn Hospital,Capital Medical University</div>'+
					'<div style="eft:0;top:67px;width:100%;font-size:22px;font-weight:bold;text-align:center;position:absolute;">'+this.config.title+'</div>'+
					'<div style="position:absolute; bottom:0; left:0; width:100%; font-size:11px;top:77px;">'+
					'	<table width="100%" border=0 cellpadding=0 cellspacing=0>'+
					'		<tr>'+
					'			<td style="width:150px; text-align:left; text-indent:60px; vertical-align:text-bottom; font-size:12px;">第&nbsp;<span id="inHspTimes">{inHspTimes}</span>&nbsp;次住院</td>'+
					'			<td style="text-align:center; vertical-align:text-bottom;font-size:12px;">第&nbsp;'+this.config.pageNo+'&nbsp;页</td>'+
					'			<td style="width:150px; text-align:left; vertical-align:text-bottom;font-size:12px;"><p style="margin:0; padding:0;"><span style="letter-spacing:1em">姓名</span>：<span id="patientName">{patientName}</span></p><p style="margin:0; padding:0;">病案号：<span id="patientNo">{patientNo}</span></p></td>'+
					'		</tr>'+
					'	</table>'+
					'</div></div>';;
		var el=this.el;
		var xmlHttp=this.createXMLHttp();
		xmlHttp.onreadystatechange=function()
	      {
	      if(xmlHttp.readyState==4)
	        {
	         var res=xmlHttp.responseText;
			 var obj=eval('('+res+')');
			 html=html.replace('{inHspTimes}',obj.inHspTimes).replace('{patientName}',obj.patientName).replace('{patientNo}',obj.patientNo);
	         el.innerHTML=html;
			}
	      }
	    xmlHttp.open("GET",this.basePath+"/common/CommonAction.do?method=GetHeaderInfo&pid="+this.pid+"&cid="+this.cid+"&timestamp="+new Date().getTime(),true);
	    xmlHttp.send(null);

	}
}
