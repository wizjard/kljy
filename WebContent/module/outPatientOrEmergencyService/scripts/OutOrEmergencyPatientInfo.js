$(function(){
	FormUtil.toDateField({el:$('input[name="birthday"]')});//初始化生日选择框
	FormUtil.initBtnCss();//初始化页面按钮
	initDictFieldList();//初始化字典选项候选列表
	var _id=App.util.getHtmlParameters('id');
	if(_id&&_id>0){
		setFormValue(_id);
	}else{
		getLoginUserInfo();
	}
});
function getLoginUserInfo(){
	$.post(
		App.App_Info.BasePath+'/user.do',
		{
			method:'getLoginUserInfo'
		},
		function(data){
			$('input[name="currentWardCode"]').val(data['belong']);
			$('input[name="currentWardCodeShow"]').val(data['单位']+' ('+data['机构']+')');
		},
		'json'
	);
}
function setFormValue(_id){
	$.post(
		App.App_Info.BasePath+'/patient.do',
		{
			method:'findById',
			id:_id
		},
		function(data){
			if(data.success){
				var _patient=JSON.parse(data.data);
				FormUtil.setFormValues('form',_patient);
				$('input[name="currentWardCodeShow"]').val(
					FormUtil.getIndependentDictionaryText('belong',$('input[name="currentWardCode"]').val())+' ('+
					FormUtil.getIndependentDictionaryText('parent_belong',$('input[name="currentWardCode"]').val())+')')
			}else{
				alert('提取病人信息失败。');
			}
		},
		'json'
	);
}
function initDictFieldList(){
	var _el=$('input[name="gender"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('gender_gb'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="idType"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('idType'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="nationality"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('nationality'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="province"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('province'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="people"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('people'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="occupation"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('occupation'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="marrageStatus"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('marrageStatus'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="educationLv"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('educationLv'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="contacterRelationship"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('relationship'));
	FormUtil.toCombobox({el:_el});
}
function SaveData(){
	var _values=FormUtil.getFormValues('form');
	_values.patientType= 1;//门(急)诊病人
	if(!ValidForm(_values))	return;
	var _data=JSON.stringify(_values);
	$.post(
		App.App_Info.BasePath+'/patient.do',
		{
			method:'saveOrUpdate',
			data:_data
		},
		function(data){
			if(data.success){
				var _patient=JSON.parse(data.data);
				$('input[name="id"]').val(_patient['id']);
				$('input[name="createDate"]').val(_patient['createDate']);
				$('input[name="modifyDate"]').val(_patient['modifyDate']);
				alert('保存成功。');
			}else{
				alert('保存失败。');
			}
		},
		'json'
	);
}
function ValidForm(_values){
	var notNulls=[
		['patientNo','病案号'],
		['patientName','姓名'],
		['gender','性别'],
		['birthday','出生日期'],
		['nationality','国籍'],
		['people','民族'],
		['province','籍贯'],
		['occupation','职业'],
		['marrageStatus','婚姻状况'],
		['homeAddr','家庭住址'],
		['currentWardCode','当前或上次所在科室']
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
