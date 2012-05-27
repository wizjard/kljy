function CheckLodop(_basePath){
   var oldVersion=LODOP.Version;
       newVerion="5.0.0.3";	
   if (oldVersion==null){
	document.write("<h3><font color='#FF00FF'>打印控件未安装!点击这里<a href='"+_basePath+"install_lodop.exe'>执行安装</a>,安装后请刷新页面。</font></h3>");
	if (navigator.appName=="Netscape")
	document.write("<h3><font color='#FF00FF'>（Firefox浏览器用户需先点击这里<a href='"+_basePath+"npActiveXFirefox4x.xpi'>安装运行环境</a>）</font></h3>");
   } else if (oldVersion<newVerion)
	document.write("<h3><font color='#FF00FF'>打印控件需要升级!点击这里<a href='"+_basePath+"install_lodop.exe'>执行升级</a>,升级后请重新进入。</font></h3>");
}
function CreateViewPageHeader(cfg){
	var config={
		patientName:'xxx',
		patientNo:'xxxxxx',
		title:'入院记录',
		inHspTimes:'x',
		pageNo:'x'
	};
	if(cfg){
		for(var key in cfg){
			config[key]=cfg[key];
		}
	}
	var html=	
				'<div class="img"><img src="'+App.App_Info.BasePath+'/PUBLIC/images/youanLogo.jpg"/></div>'+
				'<div class="zh">首都医科大学附属北京佑安医院</div>'+
				'<div class="en">BeiJing YouAn Hospital,Capital Medical University</div>'+
				'<div class="title">'+config.title+'</div>'+
				'<div class="pageInfo">'+
				'	<table width="100%" border=0 cellpadding=0 cellspacing=0>'+
				'		<tr>'+
				'			<td class="inhsptimes">第&nbsp;<span id="inHspTimes">'+config.inHspTimes+'</span>&nbsp;次住院</td>'+
				'			<td class="pageno">第&nbsp;'+config.pageNo+'&nbsp;页</td>'+
				'			<td class="nameno"><p><span style="letter-spacing:1em">姓名</span>：<span id="patientName">'+config.patientName+'</span></p><p>病案号：<span id="patientNo">'+config.patientNo+'</span></p></td>'+
				'		</tr>'+
				'	</table>'+
				'</div>';
	return html;
}
function setValue(_params){
	$.post(App.App_Info.BasePath+'/InHospitalCase/CasePrintAction.do',_params,function(data){
		if(data.success){
			var json=JSON.parse(data.data);
			var cfg={
				patientName:json['PatientName'],
				patientNo:json['PatientNo'],
				inHspTimes:json['InHspTimes']
			};
			if(_params.title){
				cfg.title=_params.title;
			}
			//$('#page-header').html(CreateViewPageHeader(cfg));
			$.each(json,function(_key){
				if(json[_key])
					$('*[name="'+_key+'"]').html(json[_key].replaceAll(' ','&nbsp;').replaceAll('\n','<p>').replaceAll(',','，'));
			});
			if($('td[name="Gender"]').text().indexOf('男')!=-1){
				$('td[name="MenstrualHistory"]').parent().remove();
			}
		}else{
			alert('后台错误。');
		}
	},'json');
}
/*function CreatePrintPage(_init,_title){
	LODOP.PRINT_INIT(_init);
	LODOP.ADD_PRINT_IMAGE(20,80,50,67,'<img width="50" height="67" src="'+App.App_Info.BasePath+'/PUBLIC/images/youanLogo.jpg"/>');
	LODOP.SET_PRINT_STYLEA(1,"ItemType",1);
	LODOP.ADD_PRINT_TEXT(50,130,400,40,"首都医科大学附属北京佑安医院");
	LODOP.SET_PRINT_STYLEA(2,"FontSize",15);
	LODOP.SET_PRINT_STYLEA(2,"Bold",1);
	LODOP.SET_PRINT_STYLEA(2,"ItemType",1);
	LODOP.ADD_PRINT_TEXT(70,130,400,25,"BeiJing YouAn Hospital,Capital Medical University");
	LODOP.SET_PRINT_STYLEA(3,"FontSize",7);
	LODOP.SET_PRINT_STYLEA(3,"Bold",1);
	LODOP.SET_PRINT_STYLEA(3,"ItemType",1);
	LODOP.ADD_PRINT_TEXT(85,315,340,40,_title);
	LODOP.SET_PRINT_STYLEA(4,"FontSize",19);
	LODOP.SET_PRINT_STYLEA(4,"Bold",1);
	LODOP.SET_PRINT_STYLEA(4,"ItemType",1);
	//LODOP.ADD_PRINT_TEXT(115,150,100,20,"第"+document.getElementById('inHspTimes').innerText+"次住院");
	LODOP.ADD_PRINT_TEXT(115,150,100,20,"第"+$('#pInhspTimes').text()+"次住院");
	LODOP.SET_PRINT_STYLEA(5,"ItemType",1);
	//LODOP.ADD_PRINT_TEXT(115,620,150,20,"病案号："+document.getElementById('patientNo').innerText);
	LODOP.ADD_PRINT_TEXT(115,620,150,20,"病案号："+$('#pNo').text() + "");
	LODOP.SET_PRINT_STYLEA(6,"ItemType",1);
	//LODOP.ADD_PRINT_TEXT(100,620,150,20,"姓  名："+document.getElementById('patientName').innerText);
	LODOP.ADD_PRINT_TEXT(100,620,150,20,"姓  名："+ $('#pName').text()+"" );
	LODOP.SET_PRINT_STYLEA(7,"ItemType",1);
	LODOP.ADD_PRINT_LINE(129,80,129,750,0,1);
	LODOP.SET_PRINT_STYLEA(8,"ItemType",1);
	LODOP.ADD_PRINT_TEXT(115,343,110,20,"第 # 页 共 & 页");
	LODOP.SET_PRINT_STYLEA(9,"ItemType",2);
	LODOP.ADD_PRINT_LINE(1055,80,1055,750,0,1);
	LODOP.SET_PRINT_STYLEA(10,"ItemType",1);
	LODOP.ADD_PRINT_HTM(135,80,650,900,document.getElementById('table-div').innerHTML);
	LODOP.SET_PRINT_STYLEA(11,"ItemType",0);
	LODOP.SET_PRINT_STYLEA(11,"HOrient",3);
	LODOP.SET_PRINT_STYLEA(11,"VOrient",0);
	LODOP.ADD_PRINT_LINE(20,78,1100,78,0,2);
	LODOP.SET_PRINT_STYLEA(12,"ItemType",1);
}*/
	function CreatePrintPage(_init,_title){
		LODOP.PRINT_INIT(_init);
		LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
		/*LODOP.ADD_PRINT_IMAGE(20,80,70,86,'<img width="50" height="67" src="'+App.App_Info.BasePath+'/PUBLIC/images/youanLogo.jpg"/>');
		LODOP.SET_PRINT_STYLEA(1,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(36,150,597,40,"首都医科大学附属北京佑安医院");
		LODOP.SET_PRINT_STYLEA(2,"FontSize",17);
		LODOP.SET_PRINT_STYLEA(2,"Bold",1);
		LODOP.SET_PRINT_STYLEA(2,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(63,152,594,25,"BeiJing YouAn Hospital,Capital Medical University");
		LODOP.SET_PRINT_STYLEA(3,"Bold",1);
		LODOP.SET_PRINT_STYLEA(3,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(85,315,340,40,_title);
		LODOP.SET_PRINT_STYLEA(4,"FontSize",19);
		LODOP.SET_PRINT_STYLEA(4,"Bold",1);
		LODOP.SET_PRINT_STYLEA(4,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(115,150,100,20,"第"+ $('#pInhspTimes').text()+"" +"次住院");
		LODOP.SET_PRINT_STYLEA(5,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(115,620,150,20,"病案号："+$('#pNo').text());
		LODOP.SET_PRINT_STYLEA(6,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(100,620,150,20,"姓  名："+$('#pName').text()+"" );*/
		
		LODOP.ADD_PRINT_IMAGE(20,80,50,67,'<img width="50" height="67" src="'+App.App_Info.BasePath+'/PUBLIC/images/youanLogo.jpg"/>');
		LODOP.SET_PRINT_STYLEA(1,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(50,130,400,40,"首都医科大学附属北京佑安医院");
		LODOP.SET_PRINT_STYLEA(2,"FontSize",15);
		LODOP.SET_PRINT_STYLEA(2,"Bold",1);
		LODOP.SET_PRINT_STYLEA(2,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(70,130,400,25,"BeiJing YouAn Hospital,Capital Medical University");
		LODOP.SET_PRINT_STYLEA(3,"FontSize",7);
		LODOP.SET_PRINT_STYLEA(3,"Bold",1);
		LODOP.SET_PRINT_STYLEA(3,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(85,315,340,40,_title);
		LODOP.SET_PRINT_STYLEA(4,"FontSize",19);
		LODOP.SET_PRINT_STYLEA(4,"Bold",1);
		LODOP.SET_PRINT_STYLEA(4,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(115,150,100,20,"第"+$('#pInhspTimes').text()+"次住院");
		LODOP.SET_PRINT_STYLEA(5,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(115,620,150,20,"病案号："+$('#pNo').text() + "");
		LODOP.SET_PRINT_STYLEA(6,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(100,620,150,20,"姓  名："+ $('#pName').text()+"" );
		
		LODOP.SET_PRINT_STYLEA(7,"ItemType",1);
		LODOP.ADD_PRINT_LINE(129,80,129,750,0,1);
		LODOP.SET_PRINT_STYLEA(8,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(115,343,110,20,"第 # 页 共 & 页");
		LODOP.SET_PRINT_STYLEA(9,"ItemType",2);
		LODOP.ADD_PRINT_LINE(1055,80,1055,750,0,1);
		LODOP.SET_PRINT_STYLEA(10,"ItemType",1);
		LODOP.ADD_PRINT_HTM(135,80,650,900,document.getElementById('table-div').innerHTML);
		LODOP.SET_PRINT_STYLEA(11,"ItemType",0);
		LODOP.SET_PRINT_STYLEA(11,"HOrient",3);
		LODOP.SET_PRINT_STYLEA(11,"VOrient",0);
		LODOP.ADD_PRINT_LINE(20,78,1100,78,0,2);
		LODOP.SET_PRINT_STYLEA(12,"ItemType",1);
	}

function CreateQDPrintPage(){
	LODOP.PRINT_INIT("入院记录打印任务");
	LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
	LODOP.ADD_PRINT_HTM(135,80,650,900,document.getElementById('table-div').innerHTML);
	LODOP.SET_PRINT_STYLEA(1,"ItemType",0);
	LODOP.SET_PRINT_STYLEA(1,"HOrient",3);
	LODOP.SET_PRINT_STYLEA(1,"VOrient",0);
}
function PREVIEW(_init,_title){
	CreatePrintPage(_init,_title);
	LODOP.PREVIEW();
}
function DoublePrint(_init,_title){
	CreatePrintPage(_init,_title);
	LODOP.SET_PRINT_MODE("DOUBLE_SIDED_PRINT",true);
	LODOP.PREVIEW();
}
function PrintQdiag(){
	$('table.conTable').addClass('white');
	$('span.queding').addClass('black');
	CreateQDPrintPage();
	LODOP.PREVIEW();
	$('table.conTable').removeClass('white');
	$('span.queding').removeClass('black');
}
function PrintDdiag(){
	$('table.conTable').addClass('white');
	$('span.dingzheng').addClass('black');
	CreateQDPrintPage();
	LODOP.PREVIEW();
	$('table.conTable').removeClass('white');
	$('span.dingzheng').removeClass('black');
}
