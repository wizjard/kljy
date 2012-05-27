NoticeInsertSupport = function(){};

NoticeInsertSupport.fileList = [];// 存放文件路径数组


$(function(){
	FormUtil.initBtnCss();//初始化页面按钮
});

function SaveData(){
	var _values=FormUtil.getFormValues('form');
	_values.noticeContent = KE.util.getData("noticeContent");//获取KEdit编辑框中的内容
	var iframe = document.getElementById("uploadFrame1");
	_values.filePathList=iframe.contentWindow.document.getElementById("first").innerHTML;//附件的路径
	_values.typeName = document.getElementById("typeName").value;
	if(!ValidForm(_values))	return;
	var _data=JSON.stringify(_values);
	$.post(
		App.App_Info.BasePath+'/admin/notice.do',
		{
			method:'save',
			data:_data
		},
		function(data){
			if(data.success){
				alert('保存成功');
				window.close();
				window.parent.opener.reloadParent();//刷新父页面
			}else{
				alert('保存失败');
			}
		},
		'json'
	);
}
function ValidForm(_values){
	var notNulls=[
		['noticeNam','公告名称'],
		['noticeContent','公告内容']
	];
	var _return=true;
	$.each(notNulls,function(){
		var _val=_values[this[0]];
		if(_val.length==0){
			alert('< '+this[1]+' > 不能为空');
			_return=false;
			return false;
		}
	});
	return _return;
}

//清空KEditor编辑器
function clearText(){
		
	 KE.clearEditor("noticeContent");
}
