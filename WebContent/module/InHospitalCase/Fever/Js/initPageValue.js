	
	/*初始化页面值*/
	function initPage(dictName){
		$.each(FormUtil.getPageDictionary(dictName),function(code){
			$('*[name="'+code+'"]').data('options',this);
			/*发病诱因select组合*/
			if(code =='predisposingfactors'){
				var op = '<option value="" ></option>';
				$.each(this,function(){
					op += '<option value="'+this[0] + '">'+this[1]+'</option>';
				});
				$('#predisposingfactors').append(op);
			}
			
		});
		//Checkbox
		$('input.checkbox,textarea.checkbox').each(function(){
			FormUtil.toCheckbox({el:$(this),columns:3});
		});
		//Select
		$('input.select').each(function(){
			FormUtil.toCombobox({el:$(this)});
		});
		//Radio初始化
		$('input.dict-fld').each(function(){
			FormUtil.initDictRadio($(this));
		});
		//体重事件
		$('*[name="bodyWeight"]').click(function(){
			var txt=getRowValue('bodyWeight',this.value);
			var tr=$(this).parent().parent().next();
			var span=$('#bodyWeight-span');
			if(txt=='增加'){
				tr.show();
				span.text(txt);
			}else if(txt=='减轻'){
				tr.show();
				span.text(txt);
			}else{
				tr.hide();
			}
		});
		
		//checkbox事件
		$('input[type="checkbox"]').filter(function(i){
			return $(this).attr('class')!='noActive';
		}).click(function(){
			var _tr=$(this).parent().parent().next();
			if(this.checked){
				_tr.show();
			}else{
				_tr.hide();
			}
		});
	}