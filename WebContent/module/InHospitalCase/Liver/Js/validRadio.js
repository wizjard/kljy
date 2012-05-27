
	/*
	 *radio验证
	 * _form 循环元素的参数
	 * _values 与指定的input name元素的值是否重复
	 */
	function validRad(_form,_values){
		var _ra = false;
		
		$.each($(_form+' input'),function(){
			var _type = this.type;
			var _class = this.className;
			
			if(_type=='button'||_type=='submit'||_type=='reset'){
				return;
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
					if(!isBool(_values,_name)){
						
					}else{
						var _v = $('input[name="'+_name+'"]').parent().prev().text();
						subString(_v,'未选择！');
						_ra = false;
						return false;
					}
				}else{
					_ra = true;
				}
			}
		});
		
		var st = false;
		if(_ra == true){
			st = true;
		}else{
			st = false;
		}
		return st;
	}