var trforCheck = '<tr>' +
			'<td><input name="itemsforCheck" type="checkbox" /></td>' + 
			'<td><input name="countforCheck" type="text" style="width:93%;height:100%" class="" /></td>' + 
			'<td><input name="cycleforCheck" type="text" style="width:93%;height:100%" class="" /></td>'+ 
			'<td colspan=3>' + 
				'<table name="subtableforCheck" id="subtableforCheck" border="1" style="border:1 solid;" cellSpacing=0 borderColorDark=#ffffff cellPadding=2>' + 					
				'</table>' +
			'</td>' +
		 '</tr>';
var subtrforCheck = '<tr width="600">' + 
				'<td width="100"><input name="projnameforCheck" type="text" readonly="readonly" style="width:90%" class="" /></td>' + 
				'<td width="100"><input name="checkdateforCheck" type="text" style="width:98%" class="" /></td>' + 
				'<td width="400"><input name="checkresultforCheck" type="text" style="width:98%" class="" /></td>' + 
			'</tr>';
var followupId;
var checkresult = null;
var PID;
function setFollowupPlan(_record, pid){
	PID = pid;	
	new Ext.Window({
		frame:true,
		title:'随访方案',
		id:'window',
		closable:false,
		autoScroll:true,
		closeAction:'close',
		width:710,
		height:500,
		layout:'fit',
		tbar:[
			{
				pressed:true,
				text:'关联化验结果',
				handler:function(){
					if(checkresult == null){
						alert("请选择要关联的项目");
						return;
					}
					RelateCheckReport();
					checkresult = null;		
				}
			},{
				xtype:"tbfill"
			},{
				pressed:true,
				text:'确定',
				handler:function(){
					saveProforCheck();
					
				}
			},{
				xtype:"tbseparator"
			},{
				pressed:true,
				text:'取消',
				handler:function(){
					Ext.getCmp("window").close();
				}
			}
		],
		html:'<table name="ptableforCheck" id="ptableforCheck" border="1" style="border:1 solid #000;" cellSpacing=0 borderColorDark=#ffffff cellPadding=2>' +
				'<tr height="20">' +
					'<td width="20" rowspan=2><input name="allinputforCheck" type="checkbox" /></td>' +
					'<td width="45" rowspan=2><p align="center">次数</p></td>' +
					'<td width="45" rowspan=2><p align="center">周期</p></td>' +
					'<td width="600" colspan=3><p align="center">检验项目</p></td>' + 
				'</tr>' +
				'<tr>' +
					'<td width="100"><p align="center">项目名称</p></td>' +
					'<td width="100"><p align="center">时间</p></td>' +
					'<td width="400"><p align="center">检验结果</p></td>' +
				'</tr>' +				
			'</table>'
	}).show();
	
	//初始化
	$.getJSON(App.App_Info.BasePath + '/ResearchFollowupAction.do?method=getFirstPatientFollowup&patientResearchId=' + _record.data.id + '&date=' + new Date(), function(data){
		followupId = data.id;
		addTrforCheck(data);
		getProforCheck();
		//复选框事件	
		$('input[name="allinputforCheck"]').click(function(){
			if($('input[name="allinputforCheck"]').attr('checked')){
				$('input[name="itemsforCheck"]').each(function(){
					$(this).attr('checked',true);
				});	
			}else{
				$('input[name="itemsforCheck"]').each(function(){
					$(this).attr('checked',false);
				});	
			}
		}); 
		//结果输入框点击事件
		$('input[name="checkresultforCheck"]').click(function(){
			checkresult = $(this);				
		});		
	});	
}
//增加一行
function addTrforCheck(trInfo){	
	$("#ptableforCheck").append(trforCheck);					
	$('input[name="itemsforCheck"]').click(function(){
		var item = $('input[name="itemsforCheck"]').filter(function(){
			return $(this).attr('checked')==true;
	    });
	    if(item.size() == 0){
	    	$('input[name="allinputforCheck"]').attr('checked', false);
	    }else{
	    	$('input[name="allinputforCheck"]').attr('checked', true);
	    }
	});
	
	$("#ptableforCheck").children().find('tr:last td:eq(1)').children().val(trInfo.followTimes);
	$("#ptableforCheck").children().find('tr:last td:eq(2)').children().val(trInfo.followCycle);
	
	var item = $("#ptableforCheck").children().find('tr:last td:first').children();
	var arr = trInfo.followContent.split('、');	
	for(var i = 0; i < arr.length; i++){
		addtoSubTableforCheck(item, arr[i]);
	}
}

function addtoSubTableforCheck(item, proName){
	item.parent().next().next().next().children().append(subtrforCheck);
	if(proName != null){
		item.parents('tr').find('td:eq(3) tr:last td:first').children().val(proName);
	}	
}

//保存随访项目
function saveProforCheck(){
	var trCount = $('#ptableforCheck').children().children().size() - 1;
	if(trCount != 0){	
		var params = '';
		var count = $('#subtableforCheck').children().children().size();
		var _temp = '';
		for(var i = 0; i < count; i++){
			var item = $("#subtableforCheck").children().children().eq(i);
			var proName = item.find('td:eq(0)').children().val();
			var checkDate = item.find('td:eq(1)').children().val();
			var checkResult = item.find('td:eq(2)').children().val();
			_temp = '{proName:"' + proName + '",checkDate:"' + checkDate + '",checkResult:"' + checkResult + '"}';
			params = params + _temp + ',';
		}	
		params = params.substr(0, params.length - 1);
		$.getJSON(App.App_Info.BasePath + '/ResearchFollowupAction.do?method=saveFollowupCheckResult',{proInfo:params,followupId:followupId},function(data){
			alert(data.issuccess);
			Ext.getCmp("window").close();
		});
	}
}

//获取设置的项目
function getProforCheck(){
	$.getJSON(App.App_Info.BasePath + '/ResearchFollowupAction.do?method=getFollowupCheckResult&followupId=' + followupId + '&date=' + new Date(), function(data){
		var count = $('#subtableforCheck').children().children().size();
		for(var i = 0; i < count; i++){
			var item = $("#subtableforCheck").children().children().eq(i);
			var proName = item.find('td:eq(0)').children().val();
			var obj = eval('(' + '[' + data.result + ']' + ')');
			for(var j = 0; j < obj.length; j++){
				if(proName == obj[i].proName){
					item.find('td:eq(1)').children().val(obj[i].checkDate);
					item.find('td:eq(2)').children().val(obj[i].checkResult);
				}
			}		
		}
	});
}

function RelateCheckReport(){
	var returnValue = window.showModalDialog('../InHospitalCase/Liver/CheckReport/combinationListofFollowup.html?patientId=' + PID,'','dialogWidth=950px;dialogHeight=650px');
	if(returnValue){
		checkresult.val(returnValue.retValue);
		if(returnValue.retValue != ''){
			checkresult.parent().prev().children().val(returnValue.reportDate);
		}		
	}
}


