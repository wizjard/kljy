var LODOP=null;
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
			if(_params.CaseType==6){
				cfg.title = '中医入院记录';
			}
			cfg.renderTo=$('#page-header').get(0);
			CreateViewPageHeader(cfg);
			
			$.each(json,function(_key){
				if(json[_key])
					$('*[name="'+_key+'"]').html(json[_key].replaceAll(' ','<span>&nbsp;</span>').replaceAll('\n','<p>').replaceAll(',','，'));
			});
			if($('td[name="Gender"]').text().indexOf('男')!=-1){
				$('td[name="MenstrualHistory"]').parent().remove();
				$('td[name="tcmp4_menstrual"]').parent().remove();
			}
			if($('span[name="chubu_bianbing"]').text().length>0){
				$('span[name="chubu_bianbing"]').parent().show();
			}else{
				$('span[name="chubu_bianbing"]').parent().hide();
			}
			if($('span[name="chubu_zhizefangyao"]').text().length>0){
				$('span[name="chubu_zhizefangyao"]').parent().show();
			}else{
				$('span[name="chubu_zhizefangyao"]').parent().hide();
			}
		}else{
			alert('后台错误。');
		}
	},'json');
}
function CreateViewPageHeader(cfg){
	var config={
		renderTo:null,
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
	
	var head = new CreatePreviewHead();
	head.renderTo=config.renderTo;
	head.title=config.title;
	head.pName=config.patientName;
	head.pNo=config.patientNo;
	head.pageNo=config.pageNo;
	head.pInhspTimes=config.inHspTimes;
	head.create();
}

	/**
	 * 设置单独页面打印函数
	 */
	function setSinglePageValues(_params,title){
		$.post(
				App.App_Info.BasePath+'/InHospitalCase/CasePrintAction.do',
				_params,
				function(data){
					if(data.success){
						var json=JSON.parse(data.data);
						var cfg={
							patientName:json['PatientName'],
							patientNo:json['PatientNo'],
							inHspTimes:json['InHspTimes']
						};
						if(title){
							cfg.title = title;
						}
						/*======== 修改打印目标 09-15=======*/
						cfg.renderTo=$('#page-header').get(0);
						cfg.image = 1;
						//===================09-15修改===========
						CreateViewPageHeader(cfg);
						$.each(json,function(_key){
							if(json[_key])
								$('*[name="'+_key+'"]').html(json[_key].replaceAll(' ','<span>&nbsp;</span>').replaceAll('\n','<p>').replaceAll(',','，'));
						});
					}
				},'json'
		);
	}