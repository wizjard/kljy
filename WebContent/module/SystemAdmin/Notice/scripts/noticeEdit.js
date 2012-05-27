
var filePathList = null;

$(function(){
	FormUtil.initBtnCss();//初始化页面按钮
	var _id=App.util.getHtmlParameters('id');
		setFormValue(_id);
});

function setFormValue(_id){
	$.post(
		App.App_Info.BasePath+'/admin/notice.do',
		{
			method:'findById',
			id:_id
		},
		function(data){
			if(data.success){
				var notice=JSON.parse(data.data);
				FormUtil.setFormValues('form',notice);
				KE.showData("noticeContent", notice.noticeContent);	
				var iframe = document.getElementById("uploadFrame1");
				iframe.contentWindow.document.getElementById("first").innerHTML =notice.filePathList;	
				iframe.contentWindow.document.getElementById("first").style.display="block";
				var typeStatus = document.getElementById("typeName");
				for(var i=0;i<typeStatus.length;i++)
				{
					if(typeStatus[i].value == notice.typeName) typeStatus[i].selected = true;
				}
			}else{
				alert('提取公告信息失败。');
			}
		},
		'json'
	);
}

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
			method:'saveOrUpdate',
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
