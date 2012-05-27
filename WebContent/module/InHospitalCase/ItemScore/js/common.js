var	htmlRequest={ 
		QueryString:function(val) 
			{ 
				var uri = window.location.search; 
				var re = new RegExp("" +val+ "=([^&?]*)", "ig"); 
				return ((uri.match(re))?(uri.match(re)[0].substr(val.length+1)):null); 
			},
		UrlString: function(){
			var uri=window.location+'';
			if(uri.indexOf('?')>0){
				return uri.substring(0,uri.indexOf('?'));
			}else{
				return uri;	
			}
		}
	};

	/*获取页面值*/
	function getPageValue(_form){
		var _values={};
		$(_form).find('input').each(function(){
			var _inputType = this.type;
			if(_inputType == 'button'||_inputType=='submit'||_inputType=='reset'){
				return;
			}
			if(_inputType == 'text'){
				_values[this.name] = $(this).val();
			}else if(_inputType == 'hidden'){
				_values[this.name] = $(this).val();
			}else if(_inputType=='checkbox'){
				if(this.name=='apm'||this.name=='acheckbox'||this.name=='d1c'||this.name=='d2c'||this.name=='d3c'||this.name=='d4c'||this.name=='d5c'||this.name=='d6c'||this.name=='d7c'||this.name=='d8c'||this.name=='d9c'||this.name=='d10c'||this.name=='d11c'){
					$('input[name="'+this.name+'"]').each(function(){
						if(this.checked){
							_values[this.name] = this.value;
						}
					});
				}else{
					 if(this.checked){
						_values[this.name] = 1;
					}else {
						_values[this.name] = 0;
					}
				}	
			}else if(_inputType=='radio'){
				$(' input[name="'+this.name+'"]').each(function(){
					if(this.checked){
						_values[this.name]=this.value;
						if(_values[this.name]==this.value){
							this.checked = true;
						}
					}
				});
			
			}
		});
		
		$(_form).find('select').each(function(){
			if(this.name=='CPC1'){
				_values[this.name] = $('select[name="'+this.name+'"] option[selected]').text();
			}else{
				_values[this.name] = $('select[name="'+this.name+'"] option[selected]').val();
			}
		});
		
		$.each($(_form+' textarea'),function(){
			_values[this.id||this.name]=$(this).val();
		});
		return _values;
	}
	
	/*为页面设置值*/
	function setPageVlaues(_form,_json){
		$.each($(_form + ' input'),function(){
			var _inputType = this.type;
			if(_inputType == 'button'||_inputType=='submit'||_inputType=='reset'){
				return;
			}else if(_inputType=='text' ||_inputType=='hidden'){
				/*if(this.name=='patientName'||this.name=='patientNo'||this.name=='sex'||this.name=='age'){
					return;
				}else{
					$(this).val(_json[this.name]);
				}*/
				$(this).val(_json[this.name]);
			}else if(_inputType=='checkbox'){
				var temp = _json[this.name];
				if(this.name=='apm'||this.name=='acheckbox'||this.name=='d1c'||this.name=='d2c'||this.name=='d3c'||this.name=='d4c'||this.name=='d5c'||this.name=='d6c'||this.name=='d7c'||this.name=='d8c'||this.name=='d9c'||this.name=='d10c'||this.name=='d11c'){
					$('input[name="'+this.name+'"]').each(function(){
						if(this.value == temp){
							$(this).attr('checked',true);
						}
					});
				}else {
					if(temp==1){
						$(this).attr('checked',true);
					}
				}
							
			}else if(_inputType=='radio'){
				var _v = _json[this.name];
				$('input[name="'+this.name+'"]').each(function(){
					var te = _v +'';
					if(te.length>0){
						if(this.value==_v){
							this.checked = true;
						}
					}
				});
			}
		});
		
		$(_form).find('select').each(function(){
			if(this.name=='CPC1'){
				var _text = _json[this.name];
				$('select[name="'+this.name+'"]').find('option').each(function(){
					var _te = _text + '';
					if(_te.length>0){
						//alert('text()：'+$(this).text());//Jquery对象获取值
						//alert('innerText：'+this.innerText);//DOM对象获取值
						if(this.innerText==_text){
							this.selected = true;
						}
					}
				});
			}else{
				$('select[name="'+this.name+'"]').val(_json[this.name]);
			}
		});
		
		$.each($(_form + ' textarea'),function(){
			$(this).val(_json[this.name]);
		});
	}
	
	function validata(){
		var rst=false;
		var date=document.getElementById('opeDate').value;
		if(date.trim().length==0){
			alert('日期不能为空！');
			rst=true;
		}
		return rst;
	}
	
	/*公共删除记录函数*/
	function del(id,url,initPrm,flag){
		if(id==-1){
			Ext.Msg.alert('提示！','数据未保存，请不要点击删除按钮。',function(){});
			return;	
		}
		//alert("ID："+ initPrm.id + "\ttitle：" + initPrm.title+ "\tURL：" + initPrm.url);
		Ext.Msg.confirm('警告！','删除后将不可恢复，确定要删除？',function(_btn){
			if(_btn=='yes'){
				Ext.Ajax.request({
					url:App.App_Info.BasePath + url,
					success:function(_response,_options){
						var json = Ext.util.JSON.decode(_response.responseText);
						if(json.success==true){
					//		parent.addTab(initPrm.id,initPrm.title,initPrm.url);
							parent.initTree();
							alert("删除成功！");
							parent.delTab(flag + id);
							parent.delTab(flag + "-1");
						}else {
							alert('删除失败！');
						}
					}
				});
			}else{
				return;
			}							   
		});	
	}
	
	/*查询病人基本信息*/
	function getPatientInfo(){
		$.post(
			App.App_Info.BasePath + '/KLJY/fever.do',
			{
				method:'getPatient',
				kid:kid
			},function(data){
				if(data.success){
					var json = JSON.parse(data.data);
					$('input[name="age"]').val(json.age);
					$('input[name="patientName"]').val(json.patientName);
					$('input[name="sex"]').val(json.sex);
					$('input[name="patientNo"]').val(json.patientNo);
					/*$.each(json,function(key){
						alert(key + '\t' + json[key]);
						$('input[name="'+key+'+"]').val(json[key]);
					});*/
				}
			},'json'
		);
	}
	
	/*=====页面表头信息=====*/
	function before_init(kid,title){
		var rh = new RepoertHeader();
		rh.basePath=App.App_Info.BasePath;
		rh.el=$('#header').get(0);
		rh.cid=kid;
		rh.config.title= title;
		rh.create();
	}
