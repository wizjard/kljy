function MyEditPlus(_id,_width,_height){
	this.id=_id;
	this.width=_width;
	this.height=_height;
	this.BasePath='';
	this.dom=null;
	this.style={
		RTC_con:	'background:#EFEFDE;'+
					'padding:0 0 4px 2px;'+
					'border:solid #ADAD9B 1px;',
		RTC:		'padding:4px 2px 2px 2px;'+
					'clear:both;'+
					'overflow-y:scroll;'+
					'background:#FFF;'+
					'border:inset #F9FAE2 1px;'+
					'scrollbar-face-color: #EFEFDE;'+
					'scrollbar-shadow-color: #333333;'+
					'scrollbar-highlight-color: #EFEFDE;'+
					'scrollbar-3dlight-color: #EFEFDE;'+
					'scrollbar-darkshadow-color: #EFEFDE; '+
					'scrollbar-track-color: #EFEFDE; '+
					'scrollbar-arrow-color: #EFEFDE;',
		RTC_toolbar:'margin-bottom:3px;',
		img_item:	'float:left;'+
					'cursor:pointer;'+
					'margin:2px 0 3px 0;'+
					'border:solid #EFEFDE 1px;',
	  img_separator:'float:left;m'+
	  				'argin:2px 0 3px 0;'+
					'border:solid #EFEFDE 1px;'
	};
	var _this=this;
	this.defaultButtons=[
		'-',{
			img:'/images/superscript.gif',
			name:'上标',
			handler:function(){
				_this.execCommand('superscript');
			}
		},{
			img:'/images/subscript.gif',
			name:'下标',
			handler:function(){
				_this.execCommand('subscript');
			}
		},{
			img:'/images/helpinsert.gif',
			name:'辅助输入',
			handler:function(){
				_this.execCommand('helpinsert');
			}
		},{
			img:'/images/clearStyle.gif',
			name:'清除格式',
			handler:function(){
				_this.execCommand('clearStyle');
			}
		}
	];
}
MyEditPlus.prototype.initFace=function(){
	var _con=document.createElement('div');
	_con.style.cssText=this.style.RTC_con;
	_con.id=this.id+'-RTC-con';
	_con.style.width=this.width;
	_con.style.height=this.height;
	
	var _toobar=document.createElement('div');
	_toobar.style.cssText=this.style.RTC_toolbar;
	_toobar.id=this.id+'-RTC-toolbar';
	_con.appendChild(_toobar);
	
	var _rtc=document.createElement('div');
	_rtc.contentEditable=true;
	_rtc.style.cssText=this.style.RTC;
	_rtc.id=this.id+'-RTC';
	_rtc.style.height=this.height-40;
	_con.appendChild(_rtc);
	
	this.dom=_con;
	
	for(var i=0,len=this.defaultButtons.length;i<len;i++){
		this.addButton(this.defaultButtons[i]);
	}
}
MyEditPlus.prototype.addButton=function(_btnCfg){
	if(_btnCfg=='-'){
		this.addSeparator();
		return;
	}
	var _btn=document.createElement('div');
	_btn.style.cssText=this.style.img_item;
	_btn.onmouseover=function(){
		this.style.border='outset #EFEFDE 1px';
	};
	_btn.onmouseout=function(){
		this.style.border='solid #EFEFDE 1px';
	};
	_btn.onclick=function(){
		if(typeof _btnCfg.handler=='function')
			_btnCfg.handler();
	};
	_img=document.createElement('img');
	_img.setAttribute('src',this.BasePath+_btnCfg.img);
	_img.setAttribute('alt',_btnCfg.name);
	_btn.appendChild(_img);
	this.dom.childNodes[0].appendChild(_btn);
}
MyEditPlus.prototype.addSeparator=function(){
	var _btn=document.createElement('div');
	_btn.style.cssText=this.style.img_separator;
	var _img=document.createElement('img');
	_img.setAttribute('src',this.BasePath+'/images/separator.gif');
	_btn.appendChild(_img);
	this.dom.childNodes[0].appendChild(_btn);
}
MyEditPlus.prototype.execCommand=function(_cmd){
	if(_cmd=='superscript'){
		this.superscript();
	}else if(_cmd=='subscript'){
		this.subscript();
	}else if(_cmd=='helpinsert'){
		this.helpinsert();
	}else if(_cmd=='clearStyle'){
		this.clearStyle();
	}
}
MyEditPlus.prototype.superscript=function(){
	var _range=document.selection.createRange();
	if(_range){
		var _parentElement=_range.parentElement();
		var _flag=false;
		while(_parentElement){
			if(_parentElement.id==this.id+'-RTC'){
				_flag=true;
				break;
			}
			_parentElement=_parentElement.parentNode;
		}
		if(_flag){
			_range.pasteHTML('<sup>'+_range.htmlText+'</sup>');
		}
	}
}
MyEditPlus.prototype.subscript=function(){
	var _range=document.selection.createRange();
	if(_range){
		var _parentElement=_range.parentElement();
		var _flag=false;
		while(_parentElement){
			if(_parentElement.id==this.id+'-RTC'){
				_flag=true;
				break;
			}
			_parentElement=_parentElement.parentNode;
		}
		if(_flag){
			_range.pasteHTML('<sub>'+_range.htmlText+'</sub>');
		}
	}
}
MyEditPlus.prototype.helpinsert=function(){
	var _range=document.selection.createRange();
	var _return=window.showModalDialog(
		this.BasePath+'/dialog/InsertHelp.html',
		 null,
		'dialogWidth=420px;dialogHeight=300px');
	if(!_return)return;
	if(_range){
		var _parentElement=_range.parentElement();
		var _flag=false;
		while(_parentElement){
			if(_parentElement.id==this.id+'-RTC'){
				_flag=true;
				break;
			}
			_parentElement=_parentElement.parentNode;
		}
		if(_flag){
			_range.pasteHTML(_return);
			return;
		}
	}
	this.dom.childNodes[1].innerHTML+=_return;
}
MyEditPlus.prototype.clearStyle=function(){
	var _range=document.selection.createRange();
	if(_range){
		var _parentElement=_range.parentElement();
		var _flag=false;
		while(_parentElement){
			if(_parentElement.id==this.id+'-RTC'){
				_flag=true;
				break;
			}
			_parentElement=_parentElement.parentNode;
		}
		if(_flag){
			_range.pasteHTML(_range.text);
		}
	}
}
MyEditPlus.prototype.create=function(){
	this.initFace();
	return this.dom;
}
