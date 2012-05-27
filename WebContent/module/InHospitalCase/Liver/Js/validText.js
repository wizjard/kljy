
	function validText(_form,_values){
		var _te = false;
		var _type = '';
		var _class = '';
		$.each($(_form+' input'),function(){	
			_type = this.type;
			_class = $(this).get(0).className;
			
			if(_type=='button'||_type=='submit'||_type=='reset'){
				return;
			}
			
			if(_type == 'text'){
				/*处理样式为text的元素*/
				if(_class == 'text' || _class=='text input-text'){
					if($(this).val().length <= 0){
						if(!isBool(_values,this.name)){
						}else{
							/*获取input元素上一层同级的td的文本属性值*/
							var _temp = $(this).parent().prev().text();
							subString(_temp,'不能为空。');
							_te = false;
							return false;
						}
					}else {
						_te = true;
					}
				}
			}
		});
		return _te;
	}
	
	/*验证type="text" class="select"的元素*/
	function validTextSel(_form,_values,json){
		var _se = false;
		var _type = '';
		var _class = '';
		$.each($(_form+' input'),function(){	
			_type = this.type;
			_class = $(this).get(0).className;
			
			if(_type=='button'||_type=='submit'||_type=='reset'){
				return;
			}
			
			if(_type == 'text'){
				/*处理样式为text的元素*/
				if(_class=='select'){
					var _val = getRowValue(this.name,json[this.name]);
					if(_val.length <= 0){
						if(!isBool(_values,this.name)){
						}else{
							var _temp = $(this).parent().prev().text();
							subString(_temp,'未选择！');
							_se = false;
							return false;
						}
					}else{
						_se = true;
					}
				}
			}
		});
		return _se;
	}
	
	/*验证type="text" class="checkbox"的元素*/
	function validTextCheckbox(_form,_values,json){
		var _ch = false;
		var _type = '';
		var _class = '';
		var _value ='';
		$.each($(_form+' input'),function(){	
			_type = this.type;
			_class = $(this).get(0).className;
			
			if(_type=='button'||_type=='submit'||_type=='reset'){
				return;
			}
			
			if(_type == 'text'){
				if(_class=='checkbox'){
					_value = json[this.name];
					if(_value.length<=0){
						if(isBool(_values,this.name)){
						}else{
							var _temp = $(this).parent().prev().text();
							subString(_temp,'不能为空。');
							_ch = false;
							return false;
						}
					}else{
						_ch = true;
					}
				}
			}
		});
		return _ch;
	}
	
	/*验证主诉  一对多验证*/
	/*
	 * _repeat 验证名称是否与tr隐藏下的名称是否相同(过滤隐藏控件的名称)
	 * _json 从页面传入获取页面的值	
	*/
	function validChiefCom(_repeat,_json){
		var result=true;
		$.each(_json,function(index){
			var data=this;
			var keyFlag=false;
			$.each(this,function(key){
				if(!isBool(_repeat,key)){
				
				}else{
					if(!data[key]||data[key].length==0){
						var _temp = $('input[name="'+key+'"]').parent().prev().text();
						subStr('第' + (index +1) + '条',_temp,'不能为空。');
						keyFlag=true;
						return false;
					}
				}
			});
			if(keyFlag){
				result=false;
				return false;
			}
		});
		return result;
	}
	
	/*根据传入的值和控件的名称来判断值是否大于0*/
	function isValue(_json,_name){
		var ret ={
			struts:false,
			num:0
		};
		var struts = false;
		var _s = 0;
		$.each(_json,function(i){
			var data = this;
			var keyFlag=false;
			$.each(_json[i],function(key){
				if(_name == key){
					if(data[key].length==0){
						alert(i+'='+key);
						ret.struts = false;
						ret.num = i;
						keyFlag=true;
						return false;
					}
				}
			});
			if(keyFlag){
				result=false;
				return false;
			}		
		});
		alert('状态：' + JSON.stringify(ret));
		return ret;
	}
	
	/* 一对多_sb td元素text属性值，_st自定义参数*/
	function subStr(_describe,_sb,_st){
		var _s = "";
		if(_sb.length > 0){
			var _start = _sb.indexOf("*");
			if(_start==-1){
				_start =-1;
			}
			var _end = _sb.indexOf("：");
			if(_end==-1){
				_end = _sb.length -1;
			}else{
				_end = _end -1;
			}
			_s = _sb.substr(_start+1,_end);
		}else{
			_s = _sb;
		}
		alert( _describe +'['+_s.trim() +' ]，'+ _st);
	}