FormUtil={
	apply:function(_o,_n){
		if(_n){
			for(var key in _n)
				_o[key]=_n[key];
		}
		return _o;
	},
	toDateField:function(cfg){
		Text2DateField(cfg);
	},
	toCombobox:function(cfg){
		if(!cfg.el){
			alert('需要初始化为Select的控件未定义。');
			return;
		}
		var rel=cfg.el.attr('rel');
		if(rel){
			cfg.el.data('options',$('input[name="'+rel+'"]').data('options'));
		}
		if(!cfg.el.data('options')){
			var _name=cfg.el.attr('id')||cfg.el.attr('name');
			alert('[ '+_name+' ] 控件候选列表未定义。');
			return;
		}
		cfg.values=cfg.el.data('options');
		cfg.el.click(function(){
			cfg.el=$(this);
			Text2Combobox(cfg);
		});
	},
	toCheckbox:function(cfg){
		if(!cfg.el){
			alert('需要初始化为Select的控件未定义。');
			return;
		}
		var rel=cfg.el.attr('rel');
		if(rel){
			cfg.el.data('options',$('*[name="'+rel+'"]').data('options'));
		}
		if(!cfg.el.data('options')){
			var _name=cfg.el.attr('id')||cfg.el.attr('name');
			alert('[ '+_name+' ] 控件候选列表未定义。');
			return;
		}
		cfg.values=cfg.el.data('options');
		var _img=$('<img align="bottom" src="'+App.App_Info.BasePath+'/PUBLIC/images/sign.gif" style="cursor:pointer"/>');
		_img.insertAfter(cfg.el);
		_img.click(function(){
			cfg.el=$(this).prev();
			Text2Checkbox(cfg);
		});
	},
	toDictSelect:function(cfg){
		Text2DictSelect(cfg);
	},
	initDictRadio:function(el){
		var _list=el.data('options');
		var _name=el.attr('name');
		if(!_list){
			var _rel=el.attr('rel');
			_list=$('input[name="'+_rel+'"]').data('options');
			el.data('options',_list);
		}
		if(!_list){
			alert('[ '+_name+' ] 控件候选列表未定义。');
			return;
		}
		$.each(_list,function(i){
			if(i==0){
				el.attr('value',this[0]);
				$('<span>'+this[1]+'</span>').insertAfter(el);
			}else{
				$('<input type="radio" name="'+_name+'" class="radio" value="'+this[0]+'"/><span>'+this[1]+'</span>').appendTo(el.parent());
			}
		});
	},
	initBtnCss:function(){
		$.each($('input.btn_mouseout'),function(){
			$(this).mouseover(function(){this.className='btn_mouseover'}).mouseout(function(){this.className='btn_mouseout'});;
		});
	},
	getFormValues:function(_selector_prefix){
		return GetFormValues(_selector_prefix);
	},
	setFormValues:function(_selector_prefix,_values){
		SetFormValues(_selector_prefix,_values);
		try {
			if (typeof(eval(setFormValues_onComplete)) == "function") {
				setFormValues_onComplete();
			}
		}catch (e) {} 
	},
	getIndependentDictionaryList:function(_code){
		var _return=[];
		$.ajax({
			url:App.App_Info.BasePath+'/common/CommonAction.do',
			data:{
				method:'GetIndependentDictionaryList',
				code:_code,
				timestamp:new Date().getTime()
			},
			async:false,
			dataType:'json',
			success:function(_data){
				_return=_data;
			}
		});
		return _return;
	},
	getIndependentDictionaryText:function(_code,_value){
		var _return=[];
		$.ajax({
			url:App.App_Info.BasePath+'/common/CommonAction.do',
			data:{
				method:'GetIndependentDictionaryText',
				code:_code,
				value:_value,
				timestamp:new Date().getTime()
			},
			async:false,
			dataType:'text',
			success:function(_data){
				_return=_data;
			}
		});
		return _return;
	},
	getPageDictionary:function(_pageCode){
		var _data={};
		$.ajax({
			url:App.App_Info.BasePath+'/common/CommonAction.do',
			data:{
				method:'GetPageDictionary',
				code:_pageCode
			},
			async:false,
			dataType:'json',
			success:function(data){
				_data=data;
			}
		});
		return _data;
	},
	getPageDictionaryNew:function(_pageCode){
		var _data={};
		$.ajax({
			url:App.App_Info.BasePath+'/common/CommonAction.do',
			data:{
				method:'GetPageDictionaryNew',
				code:_pageCode
			},
			async:false,
			dataType:'json',
			success:function(data){
				_data=data;
			}
		});
		return _data;
	},
	showJqueryDialog:function(_id){
		var radios={};
		$('#'+_id+' input[type="radio"]').each(function(){
			if(this.checked)
				radios[this.name]=this.value;
		});
		if($('#'+_id).dialog('isOpen')){
			$('#'+_id).dialog('close');
		}else{
			$('#'+_id).dialog('open');
			$.each(radios,function(key){
				$('#'+_id+' input[name="'+key+'"]').each(function(){
					if(this.value==radios[key]){
						this.checked=true;
					}
				});
			});
		}
	},
	valueTool:{
		getComboValue:function(name,scope){
			var _input=scope.find('input[name="'+name+'"]');
			var _val=_input.val();
			$.each(_input.data('options'),function(){
				if(this[0]==_val){
					_input.attr('inputValue',_val);
					_input.val(this[1]);
					if(this[1]=='其他'||this[1]=='其它')
						$('input[name="'+_name+'0"]').show();
				}
			});
		},
		setComboValue:function(el,value){
			$.each(el.data('options'),function(){
				if(this[0]==value){
					el.attr('inputValue',value);
					el.val(this[1]);
					if(this[1]=='其他'||this[1]=='其它')
						$('input[name="'+el.attr('name')+'0"]').show();
				}
			});
		}
	}
}
function SetFormValues(_sp,_values){
	//textarea赋值
	$.each($(_sp+' textarea'),function(){
		var _val=_values[this.id||this.name];
		$(this).val(_val?_val:'');
	});
	var _prxName='';
	$.each($(_sp+' input'),function(){
		//提取input类型
		var _inputType=this.type;
		if(_inputType=='button'||_inputType=='submit'||_inputType=='reset')
			return;
		//提取class类型
		var _class=this.className;
		//处理重名字段
		var _name=this.id||this.name;
		if(_prxName==_name){
			return true;
		}else{
			_prxName=_name;
		}
		var _val=_values[_name]+'';
		if(_val=='undefined'||_val=='null'){
			_val='';
		}
		if(_inputType=='radio'){//Radio字段
			if((_val+'').length>0)
				$('input[name="'+_name+'"]').each(function(){
					if(this.value==_val){
						this.checked=true;
						return false;
					}
				});
		}else if(_inputType=='checkbox'){
			if(_val.length>0){
				$(this).attr("checked",true);//2011-06-08修改////////////////////////////////////////
			}
		}else if(_inputType=='text'){
			if (_class == 'select') {
				var _input=$(this);
				$.each($(this).data('options'),function(){
					if(this[0]==_val){
						_input.attr('inputValue',_val);
						_input.val(this[1]);
						if(this[1]=='其他'||this[1]=='其它')
							$('input[name="'+_name+'0"]').show();
					}
				});
			}else{
				$(this).val(_val);
			}
		}else{
			$(this).val(_val);
		}
	});
	//新增内容
	$.each($(_sp+' select'),function(){
		var _val=_values[this.id||this.name];
		$(this).attr("value",_val);
	});
	
}
function GetFormValues(_sp){
	var _values={};
	var _prxName='';
	$.each($(_sp+' input'),function(){
		//提取input类型
		var _inputType=this.type;
		if(_inputType=='button'||_inputType=='submit'||_inputType=='reset')
			return;
		//提取class类型
		var _class=this.className;
		//处理重名字段
		var _name=this.id||this.name;
		if(_prxName==_name){
			return true;
		}else{
			_prxName=_name;
		}
		if(_inputType=='radio'){//Radio字段
			$(_sp+' input[name="'+_name+'"]').each(function(){
				if(this.checked){
					_values[_name]=this.value;
					return false;
				}
			});
		}else if(_inputType=='checkbox'){//Checkbox字段
			var _temp='';
			$.each($(_sp+' input[name="'+_name+'"][checked]'),function(){
				_temp+='<'+$(this).val()+'>';
			});
			_values[_name]=_temp;
		}else if(_inputType=='text'){//text字段
			if (_class == 'select') {
				if(($(this).val()+'').length==0)
					_values[_name]='';
				else
					_values[_name] = $(this).attr('inputValue');
			}else 
				_values[_name] = $(this).val();
		}else{//其它字段
			_values[_name]=$(this).val();
		}
	});
	$.each($(_sp+' textarea'),function(){
		_values[this.id||this.name]=$(this).val();
	});
	
	//2011-07-07添加，网上查房使用
	$.each($(_sp+' span'),function(){
		_values[this.id||this.name]=$(this).text();
	});
	
	$.each($(_sp+' select'),function(){
		_values[this.id||this.name]=$(this).attr("value");
	});
	return _values;
}
function Text2DateField(cfg){
	var config={
		el:null,
		dateFormat:'yyyy-MM-dd',
		readOnly:true
	};
	if(cfg)
		for(var key in cfg){
			config[key]=cfg[key];
		}
	if(!config.el)	return;
	config.el.attr('readonly',config.readOnly);
	var _img='<img align="bottom" src="'+App.App_Info.BasePath+'/PUBLIC/images/calendar.bmp" style="cursor:pointer"/>';
	$(_img).click(function(){
		WdatePicker({el:config.el.get(0),dateFmt:config.dateFormat});
	}).insertAfter(config.el);
}
function Text2Combobox(cfg){
	var config={
		el:null,
		values:null,
		readOnly:true
	}
	if(cfg)
		for(var key in cfg){
			config[key]=cfg[key];
		}
	config.el.attr('readonly',config.readOnly);
	if(config.readOnly)
		config.el.blur();
	if(config.el.attr('isInit')=='true'){
		Text2Combobox_show(config);
	}else{
		Text2Combobox_init(config);
		Text2Combobox_show(config);
	}
}
function Text2DictSelect(cfg){
	var config={
		url:null,
		params:null,
		showEl:null,
		hiddenEl:null,
		width:315,
		height:600,
		readOnly:true
	};
	if(cfg){
		for(var key in cfg){
			config[key]=cfg[key];
		}
	}
	if(config.readOnly){
		config.showEl.attr('readOnly',true);
	}
	var _img=$('<img align="bottom" src="'+App.App_Info.BasePath+'/PUBLIC/images/sign.gif" style="cursor:pointer"/>');
	_img.insertAfter(config.showEl);
	_img.click(function(){
		var _returnObj=window.showModalDialog(config.url,config.params,'dialogWidth='+config.width+'px;dialogHeight='+config.height+'px');
		if(_returnObj){
			if(config.showEl){
				config.showEl.val(_returnObj.text);
			}
			if(config.hiddenEl){
				config.hiddenEl.val(_returnObj.value);
			}
		}
	});
}
function Text2Combobox_init(cfg){
	var _id='Sel'+new Date().getTime();
	$('<div id="'+_id+'" class="my-select-div"></div>').appendTo(document.body);
	var _child='<div class="my-select-div-item" value=""></div>';;
	$.each(cfg.values,function(){
		_child+='<div class="my-select-div-item" value="'+this[0]+'">'+this[1]+'</div>';
	});
	$(_child).appendTo($('#'+_id));
	//事件添加
	$('#'+_id).bind('mouseleave',function(){
		$(this).hide();
	});
	$.each($('#'+_id).children(),function(){
		$(this).mouseover(function(){
			this.className='my-select-div-item-over';
		}).mouseout(function(){
			this.className='my-select-div-item';
		}).click(function(){
			cfg.el.attr('inputValue',this.value);
			var _txt=this.innerText;
			cfg.el.val(_txt);
			$('#'+_id).hide();
			var _name=cfg.el.attr('name')+'0';
			if(_txt=='其它'||_txt=='其他'){
				$('input[name="'+_name+'"]').show();
			}else{
				var _tempEl=$('input[name="'+_name+'"]');
				_tempEl.hide();
				_tempEl.val('');
			}
		});
	});
	cfg.el.attr('showId',_id);
	cfg.el.attr('isInit',true);
}
function Text2Combobox_show(cfg){
	var offset=cfg.el.offset();
	var width=cfg.el.outerWidth();
	var height=cfg.el.outerHeight();
	var _id=cfg.el.attr('showId');
	if($('#'+_id).css('display')=='block'){
		$('#'+_id).hide();
	}else
		$('#'+_id).css({left:offset.left+'px',top:(offset.top+height)+'px',width:(width-2)+'px',display:'block'});
}
function Text2Checkbox(cfg){
	var config={
		el:null,
		values:null,
		readOnly:false,
		columns:3
	}
	if(cfg)
		for(var key in cfg){
			config[key]=cfg[key];
		}
	config.el.attr('readonly',config.readOnly);
	if(config.readOnly)
		config.el.blur();
	if(config.el.attr('isInit')){
		Text2Checkbox_show(config);
	}else{
		Text2Checkbox_init(config);
		Text2Checkbox_show(config);
	}
}
function Text2Checkbox_init(cfg){
	var _id='Chk'+new Date().getTime();
	$('<div id="'+_id+'" class="my-checkbox-div"></div>').appendTo(document.body);
	var _html='<table width="100%" border=0 cellspacing=0 cellpadding=1>';;
	_html+='<tr><td colspan="'+cfg.columns+'" align="right">&nbsp;</td></tr>';;
	$.each(cfg.values,function(i){
		if((i+1)%cfg.columns==1){
			_html+='<tr><td><input type="checkbox" value="'+this[0]+'"/><span>'+this[1]+'</span></td>';
		}else if((i+1)%cfg.columns==0){
			_html+='<td><input type="checkbox" value="'+this[0]+'"/><span>'+this[1]+'</span></td></tr>';
		}else{
			_html+='<td><input type="checkbox" value="'+this[0]+'"/><span>'+this[1]+'</span></td>';
		}
	});
	_html+='<tr><td colspan="'+cfg.columns+'"><span>自定义&nbsp;</span><input type="text" style="width:80%"/></td></tr>';
	_html+='</table>';
	$(_html).appendTo($('#'+_id));

	$('<button class="btn_mouseout">确定</button>').css({
		'margin-right':'5px'
	}).click(function(){
		var _text=[];
		$('#'+_id).find('input[type="checkbox"]').each(function(){
			if($(this).attr('checked')){
				_text.push($(this).next('span').text());
			}
		});
		var _sText=$('#'+_id).find('input[type="text"]').val();
		if(_sText.length>0)	_text.push(_sText);
		cfg.el.val(_text.join('、'));
		$('#'+_id).hide();
	}).appendTo($('#'+_id).find('td').eq(0));
	$('<button class="btn_mouseout">关闭</button>').css({
		'margin-right':'5px'
	}).click(function(){
		$('#'+_id).hide();
	}).appendTo($('#'+_id).find('td').eq(0));


	cfg.el.attr('showId',_id);
	cfg.el.attr('isInit',true);
}
function Text2Checkbox_show(cfg){
	var offset=cfg.el.offset();
	var width=cfg.el.outerWidth();
	var height=cfg.el.outerHeight();
	var _id=cfg.el.attr('showId');
	if($('#'+_id).css('display')=='block'){
		$('#'+_id).hide();
	}else
		$('#'+_id).css({left:offset.left+'px',top:(offset.top+height)+'px',width:(width-2)+'px',display:'block'});
}
function getRowValue(_name,_value){
	if(typeof _value =='undefined'){
		return '';
	}
	if(_value.length>0)
		$.each($('input[name="'+_name+'"]').eq(0).data('options'),function(){
			if(_value==this[0]){
				_value=this[1];
				return false;
			}
		});
	return _value;
}