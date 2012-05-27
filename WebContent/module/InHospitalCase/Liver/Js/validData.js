	/*去掉空格*/
	String.prototype.trim = function() { return this.replace(/(^\s*)|(\s*$)/g, ""); } 

	/*验证页面必填项或选项*/
	function ValidForm(json,_form){
		var _ch = false;
		var _te = false;
		var _se = false;
		var _ra = false;
		
		var _type = '';
		var _class = '';
		
		var hiName =[];
		hiName.push('xinz_bodongCM');
		/*获取隐藏行中所有input标签的name值*/
		$(_form+' table tr').each(function(){
			var _hClass = this.className;
			if(_hClass=='hidden'){
				$(this).find('input').each(function(){
					var _ty = this.type;
					var _cl = this.className;
					if(_ty == 'radio'||_ty=='text' ||_cl=='checkbox'||_ty=='select'){
						hiName.push(this.name);
					}
				});
			}		
		});
		//alert(hiName);
		
		$.each($(_form+' input'),function(){	
			_type = this.type;
			_class = $(this).get(0).className;
			
			if(_type=='button'||_type=='submit'||_type=='reset'){
				return;
			}
			
			if(_type == 'text'){
				/*处理样式为text的元素*/
				if(_class == 'text' || _class=='text input-text'|| _class=="text line"){
					if($(this).val().length <= 0){
						if(isBool(hiName,this.name)){
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
				if(_class=='checkbox'){
					_value = json[this.name];
					if(_value.length<=0){
						if(isBool(hiName,this.name)){
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
				if(_class=='select'){
					var _val = getRowValue(this.name,json[this.name]);
					if(_val.length <= 0){
						if(isBool(hiName,this.name)){
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
				
			if(_type=='radio'){
				var i = 0;
				var _name = "";
				$('input[name="'+ this.name+'"]').each(function(){
					_name = this.name;
					if(this.checked){
						i +=1;
					}
				});
				if(i==0){
					if(isBool(hiName,_name)){
					}else{
						var _v = $('input[name="'+_name+'"]').parent().prev().text();
						subString(_v,'未选择！');
						_ra = false;
						return false;
					}
					//return false;
				}else{
					_ra = true;
				}
			}
		});
		
		var sum = false;
		//alert(_te + '  ' +_ch  +'  '+  _se +'   '+_ra);
		if(_te==true && _ch==true && _se==true && _ra==true){
			sum = true;
		}else {
			sum = false;
		}
		//alert("是否提交："+sum);
		return sum;
	}
	
	/*_sb td元素text属性值，_st自定义参数*/
	function subString(_sb,_st){
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
			//alert(_start + '    ' + _end);
			_s = _sb.substr(_start+1,_end);
		}else{
			_s = _sb;
		}
		alert('['+_s.trim() +' ]，'+ _st);
	}
	/*判断验证的name值是否与隐藏tr下input name="" 一样*/
	function isBool(_hisName,tempName){
		var _t = false;
		$.each(_hisName,function(i){
			if(tempName==_hisName[i]){
				_t = true;
				return false;
			}
		});
		return _t;
	}
	