
var itemName = App.util.getHtmlParameters('nodeText');
var KID=App.util.getHtmlParameters('KID');

var spanContent = parent.document.getElementById('FerverHistory').contentWindow.dbclickedSpan;
$(document).ready(function(){	
	$('*[name="caseId"]').val(KID);
	
	initPage();
	if(spanContent != null){
		spanContent = $(spanContent).html();
		jqueryObj = $('<div>' + spanContent + '</div>');
		jqueryObj.appendTo($('#composeInfo'));
		spanContent = jqueryObj.find("span").html();
		FormUtil.setFormValues('form',JSON.parse(spanContent));//parse:把对象转换成字符串	
	}
	showOrHide(JSON.parse(spanContent));
});
function initPage(){
		var dict=FormUtil.getPageDictionary('incidence');
		//初始化公共字典值
		$.each(dict,function(code){
			$('input[name="'+code+'"]').data('options',this);
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
		//raido显示隐藏事件
		$('input[type="radio"]').filter(function(){
			return $(this).parent().parent().next().attr('class')=='hidden';
		}).each(function(){
			$(this).click(function(){
				var tr=$(this).parent().parent().next();
				var val=0;
				$('*[name="'+$(this).attr('name')+'"]').each(function(){
					if(this.checked){
						val=$(this).val();
						return false;
					}
				});
				if(val==0){
					tr.hide();
				}else{
					tr.show();
				}
		
				$('input[type="radio"]').each(function(){
					$(this).attr("disabled",true);  
				});
				$(this).parent().parent().find('input[type="radio"]').attr("disabled",false);  
			});
		});
		$('input[name="causesFlag"]').unbind('click');
		$('input[name="causesFlag"]').click(function(){
			if($(this).val()==1){
				$(this).parent().parent().next().show();
			}else if($(this).val()==2){
				$(this).parent().parent().next().next().show();
			}else{
				$(this).parent().parent().next().hide();
			}
			$('input[type="radio"]').each(function(){
				$(this).attr("disabled",true);  
			});
			$(this).parent().parent().find('input[type="radio"]').attr("disabled",false);  
		});
		//确定事件
		$('#ok-btn').click(function(){
			//获取页面数据json格式
				var value = FormUtil.getFormValues('form');
				//将json格式转换成字符串
				var oldOperation = JSON.stringify(value);
				var rst = "";
				rst = Compose(value);
				$('#oldContent').html(rst);
				if(rst.length > 0){
					rst = '<span ondblclick="' + "{dbclickedSpan=$(this);$('span').addClass('blackstyle');$(this).removeClass('blackstyle');$(this).addClass('redstyle');parent.hidePreHis();parent.symptomInfoIfr('incidence.html', itemName);}" + '" onclick="' + "{var i; for(i = 0; i < store.getCount(); i++){if(store.getAt(i).get('serialNo') == $(this).attr('name')) break;} Ext.getCmp('symptomItemList').getSelectionModel().clearSelections();if($(this).css('color') == 'red'){$('span').addClass('blackstyle');}else{Ext.getCmp('symptomItemList').getSelectionModel().selectRow(i);$('span').addClass('blackstyle');$(this).removeClass('blackstyle');$(this).addClass('redstyle');}}" + '">' + rst + '<span style="display:none">' + oldOperation + '</span>' + '</span>';
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
	//取消事件
	function backToPreIllWindow(){
		parent.document.getElementById('FerverHistory').contentWindow.dbclickedSpan = null;
		//parent.hideSymptomInfoIfr();
		parent.showPreHis();	
	}
	function Compose(value){
	var rst='';
	var temp='';
	var leftInputItem = '';
	var rightInputItem = '';
	
	rst+=value.time.length>0?value.time:'';
	temp=getRowValue('timeUnit',value.timeUnit);
	rst+=temp.length>0?temp:'';
	temp=getRowValue('timeUnitSuffix',value.timeUnitSuffix);
	rst+=temp.length>0?temp:'';
	leftInputItem = '<input name="illTime" type="text" />';
	rightInputItem = '<input name="illTime" type="text" />';
	if($('input[name="illTime"]').size() > 0){
		$('input[name="illTime"]').eq(0).wrap("<div id='wrap'></div>");
		leftInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());		
		$('input[name="illTime"]').eq(1).wrap("<div id='wrap'></div>");
		rightInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());
	}
	rst = leftInputItem + rst + rightInputItem;
	
	leftInputItem = '<input name="otherCauses" type="text" />';
	rightInputItem = '<input name="otherCauses" type="text" />';
	if($('input[name="otherCauses"]').size() > 0){
		$('input[name="otherCauses"]').eq(0).wrap("<div id='wrap'></div>");
		leftInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());		
		$('input[name="otherCauses"]').eq(1).wrap("<div id='wrap'></div>");
		rightInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());
	}
	
	if(value.otherTimeCausesFlag&&value.otherTimeCausesFlag!=0){
		rst+=leftInputItem + value.otherTimeCauses + rightInputItem;
	}
	
	if(value.medicalFind&&value.medicalFind!=0){
		rst+=$("#medicalquestionDesc").val();
	}
	
	leftInputItem = '<input name="illCauses" type="text" value=""  />';
	rightInputItem = '<input name="illCauses" type="text" value="后"  />';
	if($('input[name="illCauses"]').size() > 0){
		$('input[name="illCauses"]').eq(0).wrap("<div id='wrap'></div>");
		leftInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());		
		$('input[name="illCauses"]').eq(1).wrap("<div id='wrap'></div>");
		rightInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());
	}
	if(value.causesFlag&&value.causesFlag!=0){             
		temp=getRowValue('causesFlag',value.causesFlag); 
		if(value.causes != "" && temp!='无明显诱因'){
			temp=leftInputItem + value.causes + rightInputItem;
			rst+=temp;
		}	
	}
	
	leftInputItem = '<input name="illCauses0" type="text" value="" />';
	rightInputItem = '<input name="illCauses0" type="text" value=""/>';
	if($('input[name="illCauses0"]').size() > 0){
		$('input[name="illCauses0"]').eq(0).wrap("<div id='wrap'></div>");
		leftInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());		
		$('input[name="illCauses0"]').eq(1).wrap("<div id='wrap'></div>");
		rightInputItem = $('#wrap').html();
		$('#wrap').replaceWith($('#wrap').html());
	}
	if(value.causesFlag&&value.causesFlag==0){             
		temp=getRowValue('causesFlag',value.causesFlag);
		temp=leftInputItem + temp + rightInputItem;
		rst+=temp;
	}
	return rst;
}
function showOrHide(json){
	if(json.causesFlag == 1){
		$('input[name="causesFlag"]').parent().parent().next().removeClass("hidden");
	}else{
		$('input[name="causes"]').val('');
	}	
	if(json.medicalFind == 1){
		$('input[name="medicalFind"]').parent().parent().next().removeClass("hidden");
	}else{
		$('input[name="medicalexperunit"]').val('');
		$('textarea[name="medicalquestionDesc"]').val('');
	}
	if(json.otherTimeCausesFlag == 1){
		$('input[name="otherTimeCausesFlag"]').parent().parent().next().removeClass("hidden");
	}else{
		$('input[name="otherTimeCauses"]').val('');
	}
}
	
	
	