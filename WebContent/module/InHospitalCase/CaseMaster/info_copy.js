$(function(){
	initField();
	var _kid=App.util.getHtmlParameters('KID');
	var _pid=App.util.getHtmlParameters('PID');
	setFieldValue(_kid,_pid);
});
function setFieldValue(_kid,_pid){
	$.post(
		App.App_Info.BasePath+'/InHospitalCase/CaseMaster.do',
		{
			method:'findById',
			id:_kid
		},
		function(data){
			if(data.success){
				var _obj=JSON.parse(data.data);
				_obj.id=-1;
				_obj.outDate='';
				_obj.outIlls='';
				_obj.outWardCode='';
				FormUtil.setFormValues('form',_obj);
			}
		},
		'json'
	);
}
function initField(){
	//时间选择框
	FormUtil.toDateField({el:$('input[name="inHspDate"]'),dateFormat:'yyyy-MM-dd HH:mm'});
	//科室选择
	$.ajax({
		url:App.App_Info.BasePath+'/common/CommonAction.do',
		data:{
			method:'QueryMyBelongs',
			timestamp:new Date().getTime()
		},
		async:false,
		dataType:'json',
		success:function(_data){
			var _fld=$('input[name="inWardCode"]');
			_fld.data('options',_data);
			FormUtil.toCombobox({el:_fld});
			_fld=$('input[name="currentWordCode"]');
			_fld.data('options',_data);
			FormUtil.toCombobox({el:_fld});
		}
	});
	//入院记录类型
	$.ajax({
		url:App.App_Info.BasePath+'/InHospitalCase/CaseMaster.do',
		data:{
			method:'queryMyInHspRecCfg',
			timestamp:new Date().getTime(),
			id:-1
		},
		async:false,
		dataType:'json',
		success:function(data){
			var _data=[];
			if(data.success){
				$.each(JSON.parse(data.data),function(){
					_data.push([this['code'],this['name']]);
				});
			}
			var _fld=$('input[name="caseModuleId"]');
			_fld.data('options',_data);
			FormUtil.toCombobox({el:_fld});
		}
	});
	//入院状态
	var _fld=$('input[name="inStatus"]');
	_fld.data('options',FormUtil.getIndependentDictionaryList('inStatus'));
	FormUtil.toCombobox({el:_fld});
	//负责医生
	FormUtil.toDictSelect({
		url:App.App_Info.BasePath+'/PUBLIC/Modaldialog/MyDoctorSelect.html',
		hiddenEl:$('input[name="responsibleDoc"]'),
		showEl:$('input[name="responsibleDocShow"]')
	});
	//入院诊断
	FormUtil.toDictSelect({
		url:App.App_Info.BasePath+'/PUBLIC/Modaldialog/All_Ills_Select.html',
		hiddenEl:$('input[name="inIlls"]'),
		showEl:$('input[name="inIllsShow"]'),
		width:380,
		height:523
	});
	//Button
	FormUtil.initBtnCss();
}
function setFormValues_onComplete(){
	var _responsibleDoc=$('input[name="responsibleDoc"]').val();
	var _inIlls=$('input[name="inIlls"]').val();
	if(_responsibleDoc.length>0){
		$('input[name="responsibleDocShow"]').val(FormUtil.getIndependentDictionaryText('userName',_responsibleDoc));
	}
	if(_inIlls.length>0){
		$('input[name="inIllsShow"]').val(FormUtil.getIndependentDictionaryText('ills',_inIlls));
	}
}
function SaveData(){
	var _values=FormUtil.getFormValues('form');
	if(!ValidForm(_values))	return;
	var _data=JSON.stringify(_values);
	$.post(
		App.App_Info.BasePath+'/InHospitalCase/CaseMaster.do',
		{
			method:'saveOrUpdate',
			data:_data
		},
		function(data){
			if(data.success){
				alert('保存成功。');
				var _res=JSON.parse(data.data);
				$('input[name="id"]').val(_res.id);
			}else{
				alert('保存失败。');
			}
		},
		'json'
	);
}
function ValidForm(_values){
	var notNulls=[
		['inHspTimes','入院次数'],
		['age','年龄'],
		['inHspDate','入院日期'],
		['inWardCode','入院科室'],
		['inStatus','入院状态'],
		['currentWordCode','当前科室'],
		['responsibleDoc','负责医生'],
		['inIlls','入院诊断'],
		['caseModuleId','入院记录']
	];
	var _return=true;
	$.each(notNulls,function(){
		var _val=_values[this[0]];
		if(_val.length==0){
			alert('< '+this[1]+' > 不能为空。');
			_return=false;
			return false;
		}
	});
	return _return;
}