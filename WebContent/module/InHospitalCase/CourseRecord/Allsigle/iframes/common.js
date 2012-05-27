var pid=App.util.getHtmlParameters('pid');
var kid=App.util.getHtmlParameters('kid');
var thisId=App.util.getHtmlParameters('this');
function initPage(_method){
	if (typeof before_init) {
		before_init();
	}
	$('input[name="id"]').val(thisId);
	$('input[name="caseId"]').val(kid);
	if(thisId==-1){
		initPatientInfo();
		return;
	}
	$.post(
		App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
		{
			method:_method,
			id:thisId
		},
		function(data){
			if(data.success){
				var json=JSON.parse(data.data);
				if(json){
					FormUtil.setFormValues('form',json);
					if (typeof initPage_callback) {
						initPage_callback(json);
					}
				}
				initPatientInfo();
			}else{
				alert('页面赋值错误。');
			}
		},
		'json'
	);
}
function initPatientInfo(){
	$.post(
		App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
		{
			method:'public_patientInfo',
			id:kid
		},
		function(data){
			if (data.success) {
				var json=JSON.parse(data.data);
				$.each(json, function(key){
					var fld = $('input[name="' + key + '"]');
					if(fld.size()>0)
						if(fld&&fld.val().length==0){
							fld.val(json[key]);
						}
				});
			}
		},
		'json'
	);
}
function SaveData(){
	var before=before_save();
	if(!before.allowSubmit)
		return;
	$.post(
		App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
		{
			method:before.method,
			data:before.data
		},
		function(data){
			if(data.success){
				alert('保存成功。');
				if(thisId==-1){
					parent.createIndex();
				}
				var json=JSON.parse(data.data);
				$('input[name="id"]').val(json.id);
				thisId=json.id;
				json.title=before.title+'('+json.time+')';
				parent.setAuth(json);
			}else{
				alert('保存失败。');
			}
		},
		'json'
	);
}
