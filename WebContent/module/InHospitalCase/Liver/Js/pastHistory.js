
	tab=$('#tabs');
	
	function addTab(){
		alert(tab);
		var _size=tab.find('li').size();
		alert(_size);
		$('<li><a href="#tabs-'+_size+'">记录'+(_size+1)+'</a></li>').appendTo(tab.find('ul'));
		var _content=$('div.tabs-content').eq(0).clone(true);
		_content.attr('id','tabs-'+_size);
		_content.find('input.select').removeAttr('isInit').removeAttr('showId');
		_content.find('input.checkbox').removeAttr('isInit').removeAttr('showId');
		_content.appendTo(tab);
		tab.tabs('destroy');
		/*if(record){
			$.extend(this.defaultValue,record);
		}*/
		//var _thisRecord=this.defaultValue;
		//this.setContentValue(_content,_thisRecord);
		tab.tabs();
		tab.tabs( "select" , _size ) 
	}
	
	this.setContentValue=function(_content,_thisRecord){
		_content.find('input').each(function(){
			if(this.className=='select'){
				$(this).attr('inputValue',_thisRecord[this.name]);
				$(this).val(getRowValue(this.name,_thisRecord[this.name]));
			}else{
				$(this).val(_thisRecord[this.name]);
			}
		});
		_content.find('textarea').each(function(){
			$(this).val(_thisRecord[this.name]);
		});
	}
	this.removeTab=function(){
		var _select=this.tab.tabs('option', 'selected');
		if(_select==0){
			alert('首次记录不能删除。');
			return;
		}
		if(!confirm('确定 要删除此记录？'))	return;
		var _id=$('#tabs-'+_select).find('input[name="id"]').val();
		if(!isNaN(_id)&&_id>0){
			var _tab=this.tab;
			$.post(
				App.App_Info.BasePath+'/InHospitalCase/LiverCaseAction.do',
				{
					method:'PresentIllnessHistory_deleteOnSet',
					id:_id
				},
				function(data){
					if(data.success){
						_tab.tabs('remove',_select);
					}else{
						alert('删除失败。');
					}
				},
				'json'
			);
		}else{
			this.tab.tabs('remove',_select);
		}
	}
	
	this.ok=function(){
		var _values=[];
		var _compsoe='';
		$('div.tabs-content').each(function(i){
			var json=FormUtil.getFormValues('#tabs-'+i);
			_values.push(json);
			var _temp='患者于'+json.illTime+getRowValue('illTimeUnit',json.illTimeUnit);
			if(json.causes.length>0){
				if(json.causes=='无明显诱因'||json.causes=='体检时'){
					_temp+=json.causes;
				}else{
					_temp+=json.causes+'后';
				}
			}
			_temp+='出现'+json.sysptomMain+'，';
			if(json.sysptomSide.length>0){
				_temp+='伴'+json.sysptomSide+'，';
			}
			if(json.evolution==99){
				if(json.evolution0.length>0)
					_temp+=json.evolution0+'，';
			}else{
				var str=getRowValue('evolution',json.evolution);
				if(str.length>0)
					_temp+=str+'，';
			}
			if(json.seeDoct==99){
				if(json.seeDoct0.length>0)
					_temp+=json.seeDoct0+'，';
			}else{
				var str=getRowValue('seeDoct',json.seeDoct);
				if(str.length>0)
					_temp+=str+'，';
			}
			if(json.examination.length>0){
				_temp+=json.examination+'，';
			}
			if(json.diagnosis==99){
				if(json.diagnosis0.length>0)
					_temp+=json.diagnosis0+'，';
			}else{
				var str=getRowValue('diagnosis',json.diagnosis);
				if(str.length>0)
					_temp+=str+'，';
			}
			if(json.treatment.length>0){
				_temp+=json.treatment+'，';
			}
			if(_temp.length>0){
				_temp=_temp.substr(0,_temp.length-1)+'。';
				if(_compsoe.length>0){
					_compsoe+='\n    '+_temp+'';
				}else
					_compsoe+=_temp;
			}
		});
		window.returnValue={
			values:_values,
			compose:_compsoe
		};
		window.close();
	}