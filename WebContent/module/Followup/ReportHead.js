function CreatePreviewHead(){
	var self=this;
	this.renderTo=null;
	this.renderCss={
		'positon':'relative',
		'overflow':'hidden',
		'width':'650px',
		'height':'105px'
	};
	this.imgPath=App.App_Info.BasePath + '/PUBLIC/images/youanLogo.jpg';
	this.title='';
	this.pName='';
	this.pNo='';
	this.pageNo='#';
	this.pInhspTimes='';
	this.ctxStyle={
		imgStyle:'left:0;top:0;width:50px;height:67px;position:absolute;display:block;',
		zhNameStyle:'left:50px;top:28px;width:80%;font-size:18px;font-weight:bold;position:absolute;',
		enNameStyle:'left:50px;top:50px;width:80%;font-size:11px;font-weight:bold;position:absolute;',
		titleStyle:'left:0;top:67px;width:100%;font-size:22px;font-weight:bold;text-align:center;position:absolute;',
		pNameStyle:'right:0;top:78px;width:120px;height:12px;font-size:12px;position:absolute;',
		pNoStyle:'right:0;top:92px;width:120px;height:12px;font-size:12px;position:absolute;',
		pageNoStyle:'left:0;top:92px;width:100%;height:12px;font-size:12px;text-align:center;position:absolute;',
		pIntimesStyle:'left:50px;top:92px;width:120px;height:12px;font-size:12px;position:absolute;'
	};
	var initRenderStyle=function(){
		if(self.renderTo&&self.renderCss){
			var style='';
			for(var key in self.renderCss){
				self.renderTo.style[key]=self.renderCss[key];
			}
		}
	}
	
	var tpl='<img style="{imgStyle}" src="{imgPath}"/>'+
			'<div style="{zhNameStyle}">首都医科大学附属北京佑安医院</div>'+
			'<div style="{enNameStyle}">Beijing YouAn Hospital,Capital Medical University</div>'+
			'<div style="{titleStyle}">{title}</div>'+
			'<div style="{pIntimesStyle}">姓名：<span id="pName">{pName}</span></div>'+
			'<div style="{pNoStyle}">病案号：<span id="pNo">{pNo}</span></div>'+
			'<div style="{pageNoStyle}">第 {pageNo} 页</div>';
	this.create=function(){
		initRenderStyle();
		for(var key in self.ctxStyle){
			tpl=tpl.replace('{'+key+'}',self.ctxStyle[key]);
		}
		tpl=tpl.replace('{imgPath}',self.imgPath).replace('{title}',self.title).replace('{pName}',self.pName).replace('{pNo}',self.pNo).replace('{pageNo}',self.pageNo).replace('{pInhspTimes}',self.pInhspTimes);
		self.renderTo.innerHTML=tpl;
	}
}

	/*
	 * 单独打印页面表头
	 * kid 病历主ID
	 * title 标题
	 * target 指定显示位置
	*/
	function singlePageTableInfo(kid,title,target){
		var config = publicSinglePageHeadInfo(kid,title,target);
		var head = new CreatePreviewHead();
		head.ctxStyle.zhNameStyle='left:50px;top:28px;width:80%;font-size:18px;font-weight:bold;position:absolute;';
		head.ctxStyle.enNameStyle='left:50px;top:50px;width:80%;font-size:11px;font-weight:bold;position:absolute;';
		head.renderTo=config.renderTo;
		head.title=config.title;
		head.pName=config.patientName;
		head.pNo=config.patientNo;
		head.pageNo=config.pageNo;
		head.pInhspTimes=config.inHspTimes;
		head.create();
	}
	/**
	 * 单独页面获取表头信息的公共方法
	 * @param kid 病历ID
	 * @param title 打印名称
	 * @param target 打印目标
	 * @return
	 */
	function publicSinglePageHeadInfo(kid,title,target){
		var config={
				renderTo:null,
				patientName:'xxx',
				patientNo:'xxxxxx',
				title:'门诊记录',
				inHspTimes:'x',
				pageNo:'x'
			};
			/*查询表头信息*/
			$.ajax({
				type:'post',
				url: App.App_Info.BasePath + '/patient.do',
				data:{method:'getPatientPageInfo', id:kid},
				async:false,
				dataType:'json',
				success:function(data){
					if(data.success){
						var json = JSON.parse(data.data);
						if(json){
							if(title && target){
								config.title = title;
								config.renderTo = $(target).get(0);
							}
							$.each(json,function(key){
								config[key] = json[key];
							});
						}
					}
				}
			})
		return config;
	}
	
	/**
	 * 单独登记页面表头
	 * @param kid
	 * @param title
	 * @param target
	 * @return
	 */
	function registrationPageHead(kid,title,target){
		var config = publicSinglePageHeadInfo(kid,title,target);
		var head = new CreatePreviewHead();
		head.renderTo=config.renderTo;
		head.title=config.title;
		head.pName=config.patientName;
		head.pNo=config.patientNo;
		head.pageNo=config.pageNo;
		head.pInhspTimes=config.inHspTimes;
		head.create();
	}
