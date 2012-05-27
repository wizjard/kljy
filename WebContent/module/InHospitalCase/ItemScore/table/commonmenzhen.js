var kid = App.util.getHtmlParameters('kid');
var pid=App.util.getHtmlParameters('pid');
var thisId=App.util.getHtmlParameters('this');
var ssmId = App.util.getHtmlParameters('ssmId');
var subScoreItems = App.util.getHtmlParameters('subScoreItems');
var title = App.util.getHtmlParameters('title');
var array = [];
function initPage(){
	array = [];
	if(subScoreItems){
		array = subScoreItems.split('*');
	}
	for(var i = 0; i < array.length; i++){
		var str = '<iframe src="' + array[i] + '.html?subScoreItem=' + array[i] + '&scId=' + thisId + '&caseId=' + kid + '&pid=' + pid + '" style="width:700px;" id="' + array[i] + '"></iframe>';
		$(str).appendTo('body');
	}
	
	FormUtil.toDateField({el:$('input[name="scoreTime"]'),dateFormat:'yyyy年MM月dd日HH时mm分'});
	
	if (typeof before_init) {
		before_init();
	}
	
	if(thisId==-1){
		parent.createIndex();
	}
}
function SaveData(){
	var before=before_save();
	if(!before.allowSubmit) return;
	$.post(
		App.App_Info.BasePath+'/InHospitalCase/ScoreComment.do',
		{
			method:before.method,
			data:before.data,
			subScoreItems:subScoreItems,
			scoreItemsData:before.scoreItemsData
		},
		function(data){
			if(data.success){
				alert('保存成功');
				if(thisId==-1){
					parent.createIndex();
				}
				var json=JSON.parse(data.data);
				var jsonSc=json.sc;
				$('input[name="id"]').val(jsonSc.id);
				thisId=jsonSc.id;
				jsonSc.title=before.title+'('+jsonSc.scoreTime+')';
				parent.setAuth(jsonSc);
				
				$.each(json,function(code){
					if(json[code]){
						setIframeValue(code,this);
						if(document.getElementById(code)){
							document.getElementById(code).contentWindow.initPage();
						}
					}
				});
			}else{
				alert('保存失败。');
			}
		},
		'json'
	);
}

function before_init(){
	var rh=new RepoertHeader();
	rh.basePath=App.App_Info.BasePath;
	rh.el=$('#header').get(0);
	rh.cid=kid;
	rh.pid=pid;//
	rh.config.title=title;
	rh.create();
}
function initPage_callback(json){
	json.title= title + '(' + json.scoreTime + ')';
	parent.setAuth(json);
}
function before_save(){
	var data=FormUtil.getFormValues('body');
	var scoreItemsData={};
	
	var array = [];
	if(subScoreItems){
		array = subScoreItems.split('*');
	}
	for(var i = 0; i < array.length; i++){
		saveIframeData(array[i]);
		scoreItemsData[array[i]] = getIframeValue(array[i]);
	}
	return {
		allowSubmit:isValid(data),
		method:'scoreItems_saveOrUpdatemenzhen',
		data:JSON.stringify(data),
		scoreItemsData:JSON.stringify(scoreItemsData),
		title:title
	}
}
function isValid(data){
	if($('input[name="scoreTime"]').val() == ""){
		alert('请填写评分时间');
		return false;
	}
	return true;
}
function print(){
	//打印前进行页面验证
	var result = printValidate();
	if(!result) return;
	var doctorResult = doctorSignPrint();
	if(!doctorResult)return;
	var data = 'kid='+kid+'&ssmid='+ssmId+'&scid='+thisId+'';
	window.open(App.App_Info.BasePath+'/InHospitalCase/ScoreComment.do?method=print1&'+data);
}

function printValidate(){
	if("MenzhenOrganFunc" == array[0]){
		return document.getElementById(array[0]).contentWindow.validateForm();
	}
	return true;
}

//医生签字校验
function doctorSignPrint(){
	if("MenzhenOrganFunc" == array[0]){
		if($('input[name="doc1_show"]').val()==""){
			alert("请填写住院医师");
			return false;
		}
		if($('input[name="doc2_show"]').val()==""){
			alert("请填写主治医师");
			return false;
		}
		if($('input[name="doc3_show"]').val()==""){
			alert("请填写主任/副主任医师");
			return false;
		}
	}
	return true;
}


function deleteData(){
	Ext.Ajax.request({
			url:App.App_Info.BasePath+'/InHospitalCase/ScoreComment.do',
			params:{
				method:'deleteData',
				ssmid:ssmId,
				scid:thisId
			},
			success:function(response){
				var data = Ext.util.JSON.decode(response.responseText);
				if(data.success){
					alert("删除成功");
					window.parent.flushParentPage();
				}else{
					alert("删除失败");
				}
			}
	});
}