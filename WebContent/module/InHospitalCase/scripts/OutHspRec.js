var _KID=null;
var _PID=null;
$(function(){
	_PID=App.util.getHtmlParameters('PID');
	_KID=App.util.getHtmlParameters('KID');
	$('input[name="caseId"]').val(_KID);
	registrationPageHead(_KID,'出院记录','#header');
	initPage();
	setValue();
});
function initPage(){
	$('button').mouseover(function(){
		$(this).addClass('btn-hover');
	}).mouseout(function(){
		$(this).removeClass('btn-hover');
	});
	$('#back-btn').click(function(){
		//location.href=App.App_Info.BasePath+'/module/InHospitalCase/CaseList.html?id='+_PID;
		parent.backToHistory();
	});
	$('#save-btn').click(function(){
		SaveData();
	});
	$('#print-btn').click(function(){
		window.open(App.App_Info.BasePath+'/module/InHospitalCase/Liver/Print/OutHspPrintView.html?id='+_KID);
	});
	//时间选择框
	FormUtil.toDateField({el:$('input[name="outHspDate"]'),dateFormat:'yyyy-MM-dd'});
	FormUtil.toDateField({el:$('input[name="inhspSignDate"]'),dateFormat:'yyyy-MM-dd'});
	FormUtil.toDateField({el:$('input[name="treatSignDate"]'),dateFormat:'yyyy-MM-dd'});
	FormUtil.toDateField({el:$('input[name="directorSignDate"]'),dateFormat:'yyyy-MM-dd'});
	//科室选择
	$.ajax({
		url:App.App_Info.BasePath+'/common/CommonAction.do',
		data:{
			method:'GetSelfQueryList',
			sql:'select CODE,NAME from SYS_ZD_UserBelong where PCODE=(select PCODE from SYS_ZD_UserBelong where CODE=(select currentWordCode from t_InHsp_CaseMaster where id='+_KID+'))'
		},
		async:false,
		dataType:'json',
		success:function(_data){
			var options=[];
			$.each(_data,function(){
				options.push([this.CODE,this.NAME]);
			});
			var _fld=$('input[name="inHspWard"]');
			_fld.data('options',options);
			FormUtil.toCombobox({el:_fld});
			_fld=$('input[name="outHspWard"]');
			_fld.data('options',options);
			FormUtil.toCombobox({el:_fld});
		}
	});
	//医生签字
	FormUtil.toDictSelect({
		url:App.App_Info.BasePath+'/PUBLIC/Modaldialog/MyDoctorSelect.html',
		hiddenEl:$('input[name="inhspDoctorId"]'),
		showEl:$('input[name="inhspDoctorId_show"]')
	});
	FormUtil.toDictSelect({
		url:App.App_Info.BasePath+'/PUBLIC/Modaldialog/MyDoctorSelect.html',
		hiddenEl:$('input[name="treatDoctorId"]'),
		showEl:$('input[name="treatDoctorId_show"]')
	});
	FormUtil.toDictSelect({
		url:App.App_Info.BasePath+'/PUBLIC/Modaldialog/MyDoctorSelect.html',
		hiddenEl:$('input[name="directorDoctorId"]'),
		showEl:$('input[name="directorDoctorId_show"]')
	});
}
function setValue(){
	$.post(
		App.App_Info.BasePath+'/InHospitalCase/LiverCaseAction.do',
		{
			method:'OutHspRec_findByCaseID',
			id:_KID
		},
		function(data){
			if(data.success){
				var _json=JSON.parse(data.data);
				if(_json){
					$('input[name="id"]').data('json',_json);
					if(!_json.outHspWard||_json.outHspWard.length==0){
						_json.outHspWard=top.USER.belong;
					}
					$('input[name="oldOperation"]').val(_json.oldOperation);
					FormUtil.setFormValues('form',_json);
					if(_json.labExaminationResult){
						$('*[name="labExaminationResult"]').val(_json.labExaminationResult);
					}
					
				}
				//replaceTextarea();
				$('textarea').each(function(){
					if($(this).val() != ""){
						$(this).parent().parent().show();
					}
				});
			}else{
				alert('页面赋值错误。');
			}
		},
		'json'
	);
}
function SaveData(){
	var _values=$('input[name="id"]').data('json');
	if(!_values) _values={};
	$.extend(_values,FormUtil.getFormValues('form'));
	_values.labExaminationResult= $('*[name="labExaminationResult"]').val();
	if(!ValidForm(_values))	return;
	var _data=JSON.stringify(_values);
	var hspDoctorIdValue = $('input[name="inhspDoctorId_show"]').val();
	if(hspDoctorIdValue==""){
		alert("请在【住院医师】处签字后保存！");
		return;
	}
	$.post(
		App.App_Info.BasePath+'/InHospitalCase/LiverCaseAction.do',
		{
			method:'OutHspRec_saveOrUpdate',
			data:_data
		},
		function(data){
			if(data.success){
				alert('保存成功。');
				var _json=JSON.parse(data.data);
				$('form input[name="id"]').val(_json.id);
				$('form input[name="id"]').data('josn',_json);
			}else{
				alert('保存失败。');
			}
		},
		'json'
	);
}
function ValidForm(_values){
	return true;
}
function setFormValues_onComplete(){
	var inhspDoctorId=$('input[name="inhspDoctorId"]').val();
	if(inhspDoctorId.length>0){
		$('input[name="inhspDoctorId_show"]').val(FormUtil.getIndependentDictionaryText('userName',inhspDoctorId));
	}
	var treatDoctorId=$('input[name="treatDoctorId"]').val();
	if(treatDoctorId.length>0){
		$('input[name="treatDoctorId_show"]').val(FormUtil.getIndependentDictionaryText('userName',treatDoctorId));
	}
	var directorDoctorId=$('input[name="directorDoctorId"]').val();
	if(directorDoctorId.length>0){
		$('input[name="directorDoctorId_show"]').val(FormUtil.getIndependentDictionaryText('userName',directorDoctorId));
	}
	var memberGroupId=$('input[name="memberGroupId"]').val();
	if(memberGroupId>0){
		$.post(
			App.App_Info.BasePath+'/common/CommonAction.do',
			{method:'GetSelfQueryList',sql:'select SubGroupName from SYS_MemberGroup_Table where id='+memberGroupId},
			function(data){
				if(data.length>0){
					$('input[name="memberGroupId_show"]').val(data[0]['SubGroupName']);
				}
			},
			'json'
		);
	}
}
//function replaceTextarea(){
//	var _width=500;
//	var _height=180;
//	var editor=new FCKeditor('labExaminationResult',_width,_height);
//	editor.BasePath='../../../PUBLIC/Scripts/fckeditor/';
//	editor.ReplaceTextarea();
//	editor=new FCKeditor('treatProcess',_width,_height);
//	editor.BasePath='../../../PUBLIC/Scripts/fckeditor/';
//	editor.ReplaceTextarea();
//	editor=new FCKeditor('outHspStatu',_width,_height);
//	editor.BasePath='../../../PUBLIC/Scripts/fckeditor/';
//	editor.ReplaceTextarea();
//	editor=new FCKeditor('outHspOrders',_width,_height);
//	editor.BasePath='../../../PUBLIC/Scripts/fckeditor/';
//	editor.ReplaceTextarea();
//}
function ShowLabEDialog(){
	var _json=$('input[name="id"]').data('json');
	var _lab={};
	if(_json){
		_lab=_json.lab;
	}else{
		_json={};
	}
	var sheight = screen.height/2;
	var swidth = 620;
	var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:yes;scroll:yes;resizable:yes;center:yes";
	var returnValue=window.showModalDialog('CaseDetails/LabExamination.html',_lab?_lab:null,winoption);
	if(returnValue){
		var _text=returnValue.text;
		var _value=returnValue.value;
		_json.lab=_value;
		$('input[name="id"]').data('json',_json);
		$('textarea[name="labExaminationResult"]').text(_text);
	}
}
function ShowMemberGroupSelect(){
	var sheight = screen.height/2;
	var swidth = 620;
	var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:yes;scroll:yes;resizable:yes;center:yes";
	var returnValue=window.showModalDialog(App.App_Info.BasePath+'/module/MemberGroup/MemberGroupSelect.html',null,winoption);
	if(returnValue){
		$('input[name="memberGroupId"]').val(returnValue.id);
		$('input[name="memberGroupId_show"]').val(returnValue.name);
	}
}
function ShowYiZhu(){
	var sheight = screen.height*0.7;
	var swidth = screen.width/2;
	var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:yes;scroll:yes;resizable:yes;center:yes";
	var returnValue=window.showModalDialog(App.App_Info.BasePath+'/module/InHospitalCase/Liver/CaseDetails/OutHspRec_YiZhu.html',{
		groupId:$('input[name="memberGroupId"]').val(),
		kid:_KID
	},winoption);
	if(returnValue){
		$('textarea[name="outHspOrders"]').val(returnValue);
	}
}

	/*添加表头的函数*/
	function before_init(){
		var rh=new RepoertHeader();
		rh.basePath=App.App_Info.BasePath;
		rh.el=$('#header').get(0);
		rh.cid=_KID;
		rh.config.title='出院记录';
		rh.create();
   }
   
	function RelateCheckReport(){		
		var outHospRecLabEDId = $('input[name="id"]').val();
		var returnValue = window.showModalDialog('../Liver/CheckReport/combinationListofOutHosp.html?patientId=' + _PID + '&KID=' + _KID + '&outHospRecLabEDId=' + outHospRecLabEDId,'','dialogWidth=950px;dialogHeight=650px');
		if(returnValue){
			$('*[name="labExaminationResult"]').val(returnValue.retValue);
			$('#oldOperation').val(returnValue.oldOperation);
		}
	}