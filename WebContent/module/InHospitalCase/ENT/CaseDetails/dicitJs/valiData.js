	/*专科检查*/
	function ValidFormSpecial(json,_form){
		var _se = false;
		var _ra = false;
		
		var _type = '';
		var _class = '';
		
		var hiName =[];
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
		
		$.each($(_form+' input'),function(){	
			_type = this.type;
			_class = $(this).get(0).className;
			
			if(_type=='button'||_type=='submit'||_type=='reset'){
				return;
			}
			
			if(_type == 'text'){
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
			/*if(_type=='radio'){
				var _name = this.name;
				if(isBool(hiName,_name)){
				}else{
					//每个name会读取一次，不是一组 三次  三个radio
					var _checked = $('input[name="'+_name+'"]').attr('checked');
					if(!_checked){
						var _textVal =  $('input[name="'+_name+'"]').parent().prev().text();
						subString(_textVal,'未选择！^_^');
						_ra = false;
						return false;
					}else{
						_ra = true;
					}
				}
			}*/
		});
		
		var sum = false;
		if(_se==true && _ra==true){
			sum = true;
		}else {
			sum = false;
		}
		//alert("是否提交："+sum);
		return sum;
	}