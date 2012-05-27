var itemName = App.util.getHtmlParameters('nodeText');
var KID=App.util.getHtmlParameters('KID');

var spanContent = parent.document.getElementById('FerverHistory').contentWindow.dbclickedSpan;
Ext.onReady(function(){
	$('*[name="caseId"]').val(KID);

	initPage();
	if(spanContent != null){
		spanContent = $(spanContent).html();
		jqueryObj = $('<div>' + spanContent + '</div>');
		jqueryObj.appendTo($('#composeInfo'));
		spanContent = jqueryObj.find("span").html();
		FormUtil.setFormValues('#contentDiv',JSON.parse(spanContent));
		//showOrHide(JSON.parse(spanContent));
	}
});		
function showOrHide(json){
	if(json.bodyWeight != 0){
		$('input[name="causesFlag"]').parent().parent().next().removeClass("hidden");
	}else{
		$('input[name="bodyWeight_time"]').val('');
		$('input[name="bodyWeight_kg"]').val('');
	}	
	
}		
		
function initPage(){
	//初始化公共字典值
	$.each(FormUtil.getPageDictionary('medicineCurrentState'),function(code){
		$('*[name="'+code+'"]').data('options',this);
	});
	//Select初始化
	$('input.select').each(function(){
		FormUtil.toCombobox({el:$(this)});
	});
	//Radio初始化
	$('input.dict-fld').each(function(){
		FormUtil.initDictRadio($(this));
	});
	//Checkbox初始化
	$('input.checkbox').each(function(){
		FormUtil.toCheckbox({el:$(this)});
	});
	//体重事件
	$('input[name="bodyWeight"]').unbind('click');
	$('input[name="bodyWeight"]').click(function(){
		var _val=$(this).val();
		if(_val==1){
			$(this).parent().parent().next().show();
			$('#bodyWeight-span').text('增加');
		}else if(_val==2){
			$(this).parent().parent().next().show();
			$('#bodyWeight-span').text('减轻');
		}else{
			$(this).parent().parent().next().hide();
		}
	});
	//确定事件
	$('#ok-btn').click(function(){
		var value=getData();
		var oldOperation = JSON.stringify(value);
		var rst='';
		rst+=ComposeCurrentStatu(value);
		$('#oldContent').html(rst);		
		if(rst.length > 0){
			rst = '<span ondblclick="' + "{dbclickedSpan=$(this);$('span').addClass('blackstyle');$(this).removeClass('blackstyle');$(this).addClass('redstyle');parent.hidePreHis();parent.symptomInfoIfr('currentState.html', itemName);}" + '" onclick="' + "{var i; for(i = 0; i < store.getCount(); i++){if(store.getAt(i).get('serialNo') == $(this).attr('name')) break;} Ext.getCmp('symptomItemList').getSelectionModel().clearSelections();if($(this).css('color') == 'red'){$('span').addClass('blackstyle');}else{Ext.getCmp('symptomItemList').getSelectionModel().selectRow(i);$('span').addClass('blackstyle');$(this).removeClass('blackstyle');$(this).addClass('redstyle');}}" + '">' + rst + '<span style="display:none">' + oldOperation + '</span>' + '</span>';
			var iframe = parent.document.getElementById('FerverHistory');
			iframe.contentWindow.itemValue = rst;
			if(iframe.contentWindow.dbclickedSpan){
				iframe.contentWindow.setReturnValue(iframe.contentWindow.dbclickedSpan);
			}else{
				iframe.contentWindow.setReturnValue();
			}
		}
		backToPreIllWindow();
	});
}
	
function backToPreIllWindow(){
	parent.document.getElementById('FerverHistory').contentWindow.dbclickedSpan = null;
	parent.showPreHis();	
}
	
function getData(){
	var value=FormUtil.getFormValues('#contentDiv');
	$.extend(value,FormUtil.getFormValues('#currentStatusDiv'));
	
	return value;
}
 
function getMyData(elId){
	return Ext.getCmp(elId).getData();
}
/*目前状况组合值*/
function ComposeCurrentStatu(value){
	var rst='';
	var tempValue='';
	var leftInputItem = '';
	var rightInputItem = '';
	tempValue=getRowValue('spiritStatu',value.spiritStatu);
	if(tempValue.length>0){
		leftInputItem = '<input name="lrspiritStatu" type="text" value="精神" />';
		rightInputItem = '<input name="lrspiritStatu" type="text" value="，" />';
		if($('input[name="lrspiritStatu"]').size() > 0){
			$('input[name="lrspiritStatu"]').eq(0).wrap("<div id='wrap'></div>");
			leftInputItem = $('#wrap').html();
			$('#wrap').replaceWith($('#wrap').html());		
			$('input[name="lrspiritStatu"]').eq(1).wrap("<div id='wrap'></div>");
			rightInputItem = $('#wrap').html();
			$('#wrap').replaceWith($('#wrap').html());
		}
		if(tempValue=='其它'){
			tempValue=leftInputItem+value.spiritStatu0+rightInputItem;
		}else{
			tempValue=leftInputItem+tempValue+rightInputItem;
		}
		rst+=tempValue;
	}
	
	tempValue=getRowValue('eatVolume',value.eatVolume);
	if(tempValue.length>0){
		leftInputItem = '<input name="lreatVolume" type="text" value="食量" />';
		rightInputItem = '<input name="lreatVolume" type="text" value="，" />';
		if($('input[name="lreatVolume"]').size() > 0){
			$('input[name="lreatVolume"]').eq(0).wrap("<div id='wrap'></div>");
			leftInputItem = $('#wrap').html();
			$('#wrap').replaceWith($('#wrap').html());		
			$('input[name="lreatVolume"]').eq(1).wrap("<div id='wrap'></div>");
			rightInputItem = $('#wrap').html();
			$('#wrap').replaceWith($('#wrap').html());
		}
		if(tempValue=='其它'){
			tempValue=leftInputItem+value.eatVolume0+rightInputItem;
		}else{
			tempValue=leftInputItem+tempValue+rightInputItem;
		}
		rst+=tempValue;
	}
	
	tempValue=getRowValue('sleep',value.sleep);
	if(tempValue.length>0){
		leftInputItem = '<input name="lrsleep" type="text" value="睡眠" />';
		rightInputItem = '<input name="lrsleep" type="text" value="，" />';
		if($('input[name="lrsleep"]').size() > 0){
			$('input[name="lrsleep"]').eq(0).wrap("<div id='wrap'></div>");
			leftInputItem = $('#wrap').html();
			$('#wrap').replaceWith($('#wrap').html());		
			$('input[name="lrsleep"]').eq(1).wrap("<div id='wrap'></div>");
			rightInputItem = $('#wrap').html();
			$('#wrap').replaceWith($('#wrap').html());
		}
		if(tempValue=='其它'){
			tempValue=leftInputItem+value.sleep0+rightInputItem;
		}else{
			tempValue=leftInputItem+tempValue+rightInputItem;
		}
		rst+=tempValue;
	}
	
	tempValue=getRowValue('piss',value.piss);
	if(tempValue.length>0){
		leftInputItem = '<input name="lrpiss" type="text" value="小便" />';
		rightInputItem = '<input name="lrpiss" type="text" value="，" />';
		if($('input[name="lrpiss"]').size() > 0){
			$('input[name="lrpiss"]').eq(0).wrap("<div id='wrap'></div>");
			leftInputItem = $('#wrap').html();
			$('#wrap').replaceWith($('#wrap').html());		
			$('input[name="lrpiss"]').eq(1).wrap("<div id='wrap'></div>");
			rightInputItem = $('#wrap').html();
			$('#wrap').replaceWith($('#wrap').html());
		}
		if(tempValue=='其它'){
			tempValue=leftInputItem+value.piss0+rightInputItem;
		}else{
			tempValue=leftInputItem+tempValue+rightInputItem;
		}
		rst+=tempValue;
	}
	
	tempValue=getRowValue('excrement',value.excrement);
	if(tempValue.length>0){
		leftInputItem = '<input name="lrexcrement" type="text" value="大便" />';
		rightInputItem = '<input name="lrexcrement" type="text" value="，" />';
		if($('input[name="lrexcrement"]').size() > 0){
			$('input[name="lrexcrement"]').eq(0).wrap("<div id='wrap'></div>");
			leftInputItem = $('#wrap').html();
			$('#wrap').replaceWith($('#wrap').html());		
			$('input[name="lrexcrement"]').eq(1).wrap("<div id='wrap'></div>");
			rightInputItem = $('#wrap').html();
			$('#wrap').replaceWith($('#wrap').html());
		}
		if(tempValue=='其它'){
			tempValue=leftInputItem+value.excrement0+rightInputItem;
		}else{
			tempValue=leftInputItem+tempValue+rightInputItem;
		}
		rst+=tempValue;
	}
	
	tempValue=getRowValue('bodyWeight',value.bodyWeight);
	if(tempValue.length>0){
		if(tempValue=='无变化'){
			leftInputItem = '<input name="lrbodyWeight" type="text" value="体重" />';
			rightInputItem = '<input name="lrbodyWeight" type="text" value="，" />';
			if($('input[name="lrbodyWeight"]').size() > 0){
				$('input[name="lrbodyWeight"]').eq(0).wrap("<div id='wrap'></div>");
				leftInputItem = $('#wrap').html();
				$('#wrap').replaceWith($('#wrap').html());		
				$('input[name="lrbodyWeight"]').eq(1).wrap("<div id='wrap'></div>");
				rightInputItem = $('#wrap').html();
				$('#wrap').replaceWith($('#wrap').html());
			}
			rst+=leftInputItem + tempValue + rightInputItem;
		}else if(tempValue=='增加'){
			leftInputItem = '<input name="lrbodyWeight" type="text" value="体重" />';
			rightInputItem = '<input name="lrbodyWeight" type="text" value="增加" />';
			rightInputItem2 = '<input name="lrbodyWeight" type="text" value="Kg，" />';
			if($('input[name="lrbodyWeight"]').size() > 0){
				$('input[name="lrbodyWeight"]').eq(0).wrap("<div id='wrap'></div>");
				leftInputItem = $('#wrap').html();
				$('#wrap').replaceWith($('#wrap').html());		
				$('input[name="lrbodyWeight"]').eq(1).wrap("<div id='wrap'></div>");
				rightInputItem = $('#wrap').html();
				$('#wrap').replaceWith($('#wrap').html());
			}
			tempValue=leftInputItem + value.bodyWeight_time+getRowValue('bodyWeight_timeUnit',value.bodyWeight_timeUnit) + rightInputItem + value.bodyWeight_kg + rightInputItem2;
			rst+=tempValue;
		}else if(tempValue=='减轻'){
			leftInputItem = '<input name="lrbodyWeight" type="text" value="体重" />';
			rightInputItem = '<input name="lrbodyWeight" type="text" value="减轻" />';
			var rightInputItem2 = '<input name="lrbodyWeight" type="text" value="Kg，" />';
			if($('input[name="lrbodyWeight"]').size() > 0){
				$('input[name="lrbodyWeight"]').eq(0).wrap("<div id='wrap'></div>");
				leftInputItem = $('#wrap').html();
				$('#wrap').replaceWith($('#wrap').html());		
				$('input[name="lrbodyWeight"]').eq(1).wrap("<div id='wrap'></div>");
				rightInputItem = $('#wrap').html();
				$('#wrap').replaceWith($('#wrap').html());
			}
			tempValue=leftInputItem + value.bodyWeight_time+getRowValue('bodyWeight_timeUnit',value.bodyWeight_timeUnit) + rightInputItem + value.bodyWeight_kg + rightInputItem2;
			rst+=tempValue;
		}
	}
	//========================================================================================================
	tempValue = getRowValue('mainSysptomEve',value.mainSysptomEve);
	leftInputItem = '<input name="lrmainSysptom" type="text" value="主要症状" />';
	rightInputItem = '<input name="lrmainSysptom" type="text" value="，" />';
	if($('input[name="lrmainSysptom"]').size() > 0){
		$('input[name="lrmainSysptom"]').eq(0).wrap("<div id='wrap'></div>");
		leftInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());		
		$('input[name="lrmainSysptom"]').eq(1).wrap("<div id='wrap'></div>");
		rightInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());
	}
	rst += tempValue.length > 0 ? leftInputItem + tempValue  + rightInputItem: '';
	tempValue = value.newSysptom;
	rightInputItem = '<input name="lrnewSysptom" type="text" value="，" />';
	if($('input[name="lrnewSysptom"]').size() > 0){
		$('input[name="lrnewSysptom"]').eq(0).wrap("<div id='wrap'></div>");
		rightInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());
	}
	rst += tempValue.length > 0 ? tempValue + rightInputItem : ''; 
	tempValue = value.otherCurrent;
	rightInputItem = '<input name="lrotherCurrent" type="text" value="，" />';
	if($('input[name="lrotherCurrent"]').size() > 0){
		$('input[name="lrotherCurrent"]').eq(0).wrap("<div id='wrap'></div>");
		rightInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());
	}
	rst += tempValue.length > 0 ? tempValue + rightInputItem : '';
	leftInputItem = '<input name="lrsummary" type="text" value="患者自发病以来" />';
	if($('input[name="lrsummary"]').size() > 0){
		$('input[name="lrsummary"]').eq(0).wrap("<div id='wrap'></div>");
		leftInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());
	}
	rst=rst.length>0?leftInputItem+rst:'';
	return rst;
}
 