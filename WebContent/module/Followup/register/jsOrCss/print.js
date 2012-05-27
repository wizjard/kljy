function CheckLodop(){
   var oldVersion=LODOP.Version;
       newVerion="5.0.0.3";	
   if (oldVersion==null){
	document.write("<h3><font color='#FF00FF'>打印控件未安装!点击这里<a href='"+App.App_Info.BasePath+"/PUBLIC/Scripts/Lodop/install_lodop.exe'>执行安装</a>,安装后请刷新页面。</font></h3>");
	if (navigator.appName=="Netscape")
	document.write("<h3><font color='#FF00FF'>（Firefox浏览器用户需先点击这里<a href='"+App.App_Info.BasePath+"/PUBLIC/Scripts/Lodop/npActiveXFirefox4x.xpi'>安装运行环境</a>）</font></h3>");
   } else if (oldVersion<newVerion)
	document.write("<h3><font color='#FF00FF'>打印控件需要升级!点击这里<a href='"+App.App_Info.BasePath+"/PUBLIC/Scripts/Lodop/install_lodop.exe'>执行升级</a>,升级后请重新进入。</font></h3>");
}

function CreatePageHeader(title){
	/*var html=	
				'<div class="img"><img src="'+'../../../../PUBLIC/images/youanLogo.jpg"/></div>'+
				'<div class="zh">首都医科大学附属北京佑安医院</div>'+
				'<div class="en">BeiJing YouAn Hospital,Capital Medical University</div>'+
				'<div class="title">'+title+'</div>';*/
	var html =
				'<img style="left:0;top:0;width:50px;height:67px;position:absolute;display:block;" src="'+App.App_Info.BasePath+'/PUBLIC/images/youanLogo.jpg"/>'+
				'<div style="left:50px;top:28px;width:80%;font-size:18px;font-weight:bold;position:absolute;">首都医科大学附属北京佑安医院</div>'+
				'<div style="left:50px;top:50px;width:80%;font-size:11px;font-weight:bold;position:absolute;">BeiJing YouAn Hospital,Capital Medical University</div>'+
				'<div style="left:0;top:67px;width:100%;font-size:22px;font-weight:bold;text-align:center;position:absolute;">'+title+'</div>';
	return html;
}


var LodopCss={
	conTable:{
		'width':'100%',
		'font-size':'14px',
		'line-height':'28px'
	},
	td1:{
		'text-align':'right',
		'vertical-align':'text-top'
	},
	td2:{
		'text-align':'left',
		'vertical-align':'text-top'
	}
}
	/*根据科室和当前日期查询所有科研标本登记信息*/
	function getAllRegister(){
		var c = 'method=loadTableInfo';
		$.ajax({url:App.App_Info.BasePath+'/EMR/register.do',
			type:'post',
			async:false,
			dataType:'json',
			data:c,
			success:function(data){
				var tb=$("#re");
				//清空表,保留第一行
      			tb.find("tr").each(function(b){
        			if (b>0){ 
        				$(this).remove();
      				}else{
      					createTableAll(data);
      				}
      			});
		}
	});
}
	function createTableAll(data){
	if(data!=''){
		var _table=$("#re");
		var i=0;
		$.each(data,function(i){
			var tr = "<tr align='center'><td><input type='checkbox' name='ch' id="+data[i].id+" value="+data[i].id+" /></td><td>"+
			data[i].dateTime.split(" ")[0]+"&nbsp;</td><td>"+data[i].name+"</td><td>"+data[i].scientificRoom+"&nbsp;</td>"+
			"<td>"+data[i].patientName+"&nbsp;</td><td>"+data[i].patientNo+"&nbsp;</td><td>"+data[i].diacrisis+"&nbsp;</td><td>"
			+data[i].sampleType+"&nbsp;</td><td>"+data[i].number+"&nbsp;</td><td>"+data[i].giveSampleP
			+"&nbsp;</td><td>"+data[i].belong+"&nbsp;</td><td>"+data[i].remarks+"&nbsp;</td></tr>";
			$(tr).appendTo(_table);
		})
		}
	}
	
	function getRegisterByTime(){
	
		var d = document.getElementById("inDate").value;
		var o = document.getElementById("outDate").value;
		var n = document.getElementById("name").value;
		var sqlwhere='';
		var a1='';
		if(n!=''){
		 a1=" and name like '%25"+n+"%25' "
		}
		var a2='';
		if(d!=''){
		a2=' and dateTime>=\''+d+'\''; 
		}
		var a3='';
		if(o!=''){
		a3=' and dateTime<=\''+o+'\''; 
		}
		sqlwhere+=a1+a2+a3;
		var c = 'method=getRegisterByTime'+'&sqlwhere='+sqlwhere;
		$.ajax({url:App.App_Info.BasePath+'/EMR/register.do',
				type:'post',
				async:false,
				dataType:'json',
				data:c,
				success:function(data){
					var tb=$("#re");
      				//清空表,保留第一行
      				tb.find("tr").each(function(i){
        				if (i>0) 
        					$(this).remove();
        				else 
        					createTableAll(data);
      				});
	 			}
	 		})
		}
	
	function findRegisterByIdPrint(){
		var values=[];
		 $('input[name="ch"]').each(function(){
		 	if(this.checked)
		 		values.push(this.id);
		 });
		 if(values==''){
			 alert('请选择选项')
			 return ;
		 }
		 window.open('printRegisterf.html?ch='+values);		
	}
