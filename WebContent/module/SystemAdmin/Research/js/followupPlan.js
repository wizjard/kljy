var tr = '<tr>' +
			'<td><input name="items" type="checkbox" /></td>' + 
			'<td><input name="count" type="text" style="width:97%;height:100%" class="" /></td>' + 
			'<td><input name="cycle" type="text" style="width:97%;height:100%" class="" /></td>'+ 
			'<td>' + 
				'<table name="subtable" id="subtable" border="1" style="border:1 solid;" cellSpacing=0 borderColorDark=#ffffff cellPadding=2>' + 					
				'</table>' +
			'</td>' +
		 '</tr>';
var subtr = '<tr>' + 
				'<td width="40"><input name="serial" type="text" readonly="readonly" style="width:90%" class="" /></td>' + 
				'<td width="400"><input name="projname" type="text" readonly="readonly" style="width:98%" class="" /></td>' + 
			'</tr>';
var researchTopicId;
function setFollowupPlan(_record){
	new Ext.Window({
		frame:true,
		title:'随访方案',
		id:'window',
		closable:false,
		autoScroll:true,
		closeAction:'close',
		width:700,
		height:500,
		layout:'fit',
		tbar:[
			{
				pressed:true,
				text:'新增',
				id:'add',
				handler:function(){
					addTr();
				}
			},{
				xtype:"tbseparator"
			},{
				pressed:true,
				text:'添加检验项目',
				handler:function(){
					addPro();		
				}
			},{
				xtype:"tbseparator"
			},{
				pressed:true,
				text:'删除',
				id:'delete',
				handler:function(){
					var item = $('input[name="items"]').filter(function(){
						return $(this).attr('checked')==true;
				    });
					if(item.size() == 0){
						alert("请选择要删除的行");
						return;
					}
					item.parent().parent().remove();
					
					var item = $('input[name="items"]').filter(function(){
						return $(this).attr('checked')==true;
				    });
				    if(item.size() == 0){
				    	$('input[name="allinput"]').attr('checked', false);
				    }else{
				    	$('input[name="allinput"]').attr('checked', true);
				    }
				}
			},{
				xtype:"tbfill"
			},{
				pressed:true,
				text:'确定',
				handler:function(){
					savePro();
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
		listeners:{
			show:function(){
				
			}
		},
		html:'<table name="ptable" id="ptable" border="1" style="border:1 solid #000;" cellSpacing=0 borderColorDark=#ffffff cellPadding=2>' +
				'<tr height="20">' +
					'<td width="20"><input name="allinput" type="checkbox" /></td>' +
					'<td width="120"><p align="center">次数</p></td>' +
					'<td width="120"><p align="center">周期</p></td>' +
					'<td width="440"><p align="center">检验项目</p></td>' + 
				'</tr>' +
				
			'</table>'
	}).show();
	
	researchTopicId = _record.data.id;
	getPro(researchTopicId);
	//复选框事件	
	$('input[name="allinput"]').click(function(){
		if($('input[name="allinput"]').attr('checked')){
//			$('input[name="allinput"]').attr('checked',true);
			$('input[name="items"]').each(function(){
				$(this).attr('checked',true);
			});	
		}else{
//			$('input[name="allinput"]').attr('checked',false);
			$('input[name="items"]').each(function(){
				$(this).attr('checked',false);
			});	
		}
	}); 
}
//增加一行
function addTr(trInfo){
	$("#ptable").append(tr);					
	$('input[name="items"]').click(function(){
		var item = $('input[name="items"]').filter(function(){
			return $(this).attr('checked')==true;
	    });
	    if(item.size() == 0){
	    	$('input[name="allinput"]').attr('checked', false);
	    }else{
	    	$('input[name="allinput"]').attr('checked', true);
	    }
	});
	if(trInfo != null){
		$("#ptable").children().find('tr:last td:eq(1)').children().val(trInfo.followTimes);
		$("#ptable").children().find('tr:last td:eq(2)').children().val(trInfo.followCycle);
		
		var item = $("#ptable").children().find('tr:last td:first').children();
		var combinationName = trInfo.followContent;
		addorRecoverPro(combinationName, item);
		//此处为追加subtable的内容////////////////////////////$("#ptable").children().find('tr:last td:last').children().children().find('tr:last td:last')
	}
}
//添加随访方案项目
function addPro(){
	var item = $('input[name="items"]').filter(function(){
		return $(this).attr('checked')==true;
    });
	if(item.size() != 1){
		alert("请选择一行进行操作");
		return;
	}
	var returnValue = window.showModalDialog('combinationList.html','','dialogWidth=500px;dialogHeight=380px');
	addorRecoverPro(returnValue.retValue, item);
}
function addtoSubTable(item, proName){
	item.parent().next().next().next().children().append(subtr);
	if(item.parent().next().next().next().children().children().children().size() == 1){
		item.parents('tr').find('td:eq(3) tr:last td:first').children().val(1);
	}else{
		var prevValue = item.parents('tr').find('td:eq(3) tr:last').prev().children().children().val();
		item.parents('tr').find('td:eq(3) tr:last td:first').children().val(Number(prevValue) + 1);	
	}
	if(proName != null){
		item.parents('tr').find('td:eq(3) tr:last td:last').children().val(proName);
	}	
}

//保存随访项目
function savePro(){
	var trCount = $('#ptable').children().children().size() - 1;
	if(trCount != 0){
		var tr = $('input[name="allinput"]').parent().parent();
		var params = "[";
		for(i = 0; i < trCount; i++){
			params = params + "{";
			tr = tr.next();
			var count = "";
			var cycle = "";
			var combinationName = "";
			count = tr.find('td:eq(1)').children().val();
			if(count == ""){
				alert("随访次数不能为空");
				return;
			}
			cycle = tr.find('td:eq(2)').children().val();
			if(cycle == ""){
				alert("随访周期不能为空");
				return;
			}
			var subTabCount = tr.find('td:eq(3)').children().children().children().size();
			var subTr = tr.find('td:eq(3)').children().children().find('tr:first');
			for(j = 0; j < subTabCount; j++){
				var projectName = "";
				projectName = subTr.find('td:eq(1)').children().val();
				if(projectName != ""){
					combinationName = combinationName + projectName + "*~^";
				}			
				subTr = subTr.next();
			}
			if(combinationName != ""){
				combinationName = combinationName.substr(0, combinationName.length - 3);
			}
			if(count != "" || cycle != "" || combinationName != ""){
				params = params + 'followTimes:"' + count + '",followCycle:"' + cycle + '",followContent:"' + combinationName + '",researchTopicId:"' + researchTopicId + '"},';
			}			
		}
		params = params.substr(0, params.length - 1) + "]";
		
		$.getJSON(App.App_Info.BasePath + '/ResearchFollow/FollowSetUp.do?method=saveFollopupPro',{proInfo:params},function(data){
			
		});
	}else{
		$.getJSON(App.App_Info.BasePath + '/ResearchFollow/FollowSetUp.do?method=deleteFollopupPro&researchTopicId=' + researchTopicId + '&date=' + new Date(), function(data){
			
		});
	}
	Ext.getCmp("window").close();
}

//获取设置的项目
function getPro(researchTopicId){
	$.getJSON(App.App_Info.BasePath + '/ResearchFollow/FollowSetUp.do?method=getFollopupPro&researchTopicId=' + researchTopicId + '&date=' + new Date(), function(data){
		for(var i = 0; i < data.length; i++){
			addTr(data[i]);		
		}
	});
}

function addorRecoverPro(combinationName, item){
	var arr = combinationName.split("*~^");
	for(var i = 0; i < arr.length; i++){
		addtoSubTable(item, arr[i]);
	}
}
