var itemName = App.util.getHtmlParameters('nodeText');
var KID=App.util.getHtmlParameters('KID');

var spanContent = parent.document.getElementById('FerverHistory').contentWindow.dbclickedSpan;
$(function(){
	$('*[name="caseId"]').val(KID);

	initPage();
	if(spanContent != null){
		spanContent = $(spanContent).html();
		jqueryObj = $('<div>' + spanContent + '</div>');
		jqueryObj.appendTo($('#composeInfo'));
		spanContent = jqueryObj.find("span").eq(0).html();
		FormUtil.setFormValues('form',JSON.parse(spanContent));
		var treatFunOldOpertion = jqueryObj.find("span").eq(1).html();
		if(treatFunOldOpertion && treatFunOldOpertion != 'undefined'){
			$('img[name="treatImg"]').data("results", JSON.parse(treatFunOldOpertion));
		}
		
	}	
});
function isRadioSelected(radios) {
	for (var i = 0; i < radios.length; i++) {
		if (radios[i].checked == true) {
			return $(radios[i]).val();
		}
	}
	return -111;
}

function initPage(){
	//初始化公共字典值
	$.each(FormUtil.getPageDictionary('afterTreatment'),function(code){
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
	//选择就诊方式后显示的内容
	$('input[name="treatFn"]').click(function(){
		$('#detailTable2').show();
		$(this).parent().parent().next().show();
		var temp = $(this).val();
		$('input[name="treatFlag"]').parent().find('span').each(function(){
			if($(this).html() == "为进一步诊治收治入院"){
				if(temp == 3){
					$(this).hide();
					$(this).prev().hide();
				}else{
					$(this).show();
					$(this).prev().show();
				}
			}
		});
	
		if(isRadioSelected(document.getElementsByName('treatFn')) == 1){
			if(isRadioSelected(document.getElementsByName('treatProcessFlag')) == 5){
				$('input[name="treatFn0"]').val($('input[name="treatTime"]').val() + $('input[name="treatTimeUnit"]').val() + $('input[name="treatTimeUnitSuffix"]').val() + '于我院门诊就诊');
			}
			if(isRadioSelected(document.getElementsByName('treatProcessFlag')) == 6){
				if($('input[name="inspectionUnit"]').val() == ''){
					alert('请选择当地医院/外地医院,并填写具体名称');
					return false;
				}
				$('input[name="treatFn0"]').val($('input[name="treatTime"]').val() + $('input[name="treatTimeUnit"]').val() + $('input[name="treatTimeUnitSuffix"]').val() + '于' + $('input[name="inspectionUnit"]').val() + '门诊就诊');
			}
		}
		if(isRadioSelected(document.getElementsByName('treatFn')) == 2){
			if(isRadioSelected(document.getElementsByName('treatProcessFlag')) == 5){
				$('input[name="treatFn0"]').val($('input[name="treatTime"]').val() + $('input[name="treatTimeUnit"]').val() + $('input[name="treatTimeUnitSuffix"]').val() + '于我院急诊就诊');
			}
			if(isRadioSelected(document.getElementsByName('treatProcessFlag')) == 6){
				if($('input[name="inspectionUnit"]').val() == ''){
					alert('请选择当地医院/外地医院,并填写具体名称');
					return false;
				}
				$('input[name="treatFn0"]').val($('input[name="treatTime"]').val() + $('input[name="treatTimeUnit"]').val() + $('input[name="treatTimeUnitSuffix"]').val() + '于' + $('input[name="inspectionUnit"]').val() + '急诊就诊');
			}
		}
		if(isRadioSelected(document.getElementsByName('treatFn')) ==3){
			if(isRadioSelected(document.getElementsByName('treatProcessFlag')) == 5){
				$('input[name="treatFn0"]').val($('input[name="treatTime"]').val() + $('input[name="treatTimeUnit"]').val() + $('input[name="treatTimeUnitSuffix"]').val() + '于我院住院治疗');
			}
			if(isRadioSelected(document.getElementsByName('treatProcessFlag')) == 6){
				if($('input[name="inspectionUnit"]').val() == ''){
					alert('请选择当地医院/外地医院,并填写具体名称');
					return false;
				}
				$('input[name="treatFn0"]').val($('input[name="treatTime"]').val() + $('input[name="treatTimeUnit"]').val() + $('input[name="treatTimeUnitSuffix"]').val() + '于' + $('input[name="inspectionUnit"]').val() + '住院治疗');
			}																	
		}
		if(isRadioSelected(document.getElementsByName('treatFn')) == 4){
			if(isRadioSelected(document.getElementsByName('treatProcessFlag')) == 5){
				$('input[name="treatFn0"]').val($('input[name="treatTime"]').val() + $('input[name="treatTimeUnit"]').val() + $('input[name="treatTimeUnitSuffix"]').val() + '于我院');
			}
			if(isRadioSelected(document.getElementsByName('treatProcessFlag')) == 6){
				if($('input[name="inspectionUnit"]').val() == ''){
					alert('请选择当地医院/外地医院,并填写具体名称');
					return false;
				}
				$('input[name="treatFn0"]').val($('input[name="treatTime"]').val() + $('input[name="treatTimeUnit"]').val() + $('input[name="treatTimeUnitSuffix"]').val() + '于' + $('input[name="inspectionUnit"]').val());
			}	
		}
	});
	//选择就诊医院后显示的内容
	$('input[name="hospitalName"]').click(function(){
		$(this).parent().parent().next().show();
	});
	//选择检查情况后显示的内容，parent()是获取当前元素的父辈元素，next()是获取紧跟在每个匹配元素之后的同辈元素
	$('input[name="checkRstFlag"]').click(function(){
		$(this).parent().parent().next().show();
	});
	//诊疗经过的显示与隐藏
	$("*[name='treatProcessFlag']").click(function(){
		var val = this.value;
		if(val == 5 || val ==6){
			$('#memoTable').hide();
			$('#detailTable').show();
			if(val == 5){
				$('#unit').hide();
			}else{
				$('#unit').show();
			}
		}else{
			$('#memoTable').show();
			$('#detailTable').hide();
			$('#detailTable2').hide();
			$('#unit').hide();			
			if(val == 1){
				$('textarea[name="treatProcessDesc"]').val('未诊治 ');			
			}
			else if(val == 2){
				$('textarea[name="treatProcessDesc"]').val('未到医院诊治 ');
			}
			else if(val == 3){
				$('textarea[name="treatProcessDesc"]').val('具体诊治情况不详');
			}
			else if(val == 4){
				$('textarea[name="treatProcessDesc"]').val('未到医院诊治，自行服药治疗');
			}
		}
	});
	
	
	
	//诊断结果
	$('*[name="diagRstFlag"]').click(function(){
		if(this.value==1){
			$(this).parent().parent().next().next().hide();
			$(this).parent().parent().next().show();
			$('input[name="diagRst"]').val("未明确诊断");
		}else if(this.value==2){
			$(this).parent().parent().next().next().hide();
			$(this).parent().parent().next().show();
			$('input[name="diagRst"]').val("诊断为");
		}else if(this.value==4){
			$(this).parent().parent().next().next().show();
			$(this).parent().parent().next().hide();
		}else{
			$(this).parent().parent().next().next().hide();
			$(this).parent().parent().next().hide();
		}
	});
	//治疗结果
	$('tr[name="zhiliaomiaoshu"]').hide();
	$('tr[name="zhiliaojieguo"]').hide();
	$('*[name="treatFlag"]').click(function(){
		$('tr[name="zhiliaomiaoshu"]').show();
		$('tr[name="zhiliaojieguo"]').show();
		if(this.value==1||this.value==2){
			$("#treatImg").hide();
		}else{
			$(this).parent().parent().next().show();
			$(this).parent().parent().next().next().show();
		}
		if(this.value==1){
			$('textarea[name="treat"]').val("未行治疗");
		}
		if(this.value==3 || this.value==7){
			$("#treatImg").hide();
			$('textarea[name="treat"]').val("自行服用");
		}
		if(this.value==4){
			$('textarea[name="treat"]').val("为进一步诊治收治入院");
		}
		if(this.value==5){
			$('textarea[name="treat"]').val("");
			$("#treatImg").show();
		}
		if(this.value==2){
			$('textarea[name="treat"]').val("具体治疗不详");
		}
		if(this.value==7){
			$('textarea[name="treat"]').val("");
		}
	});
	
	$('*[name="treatImg"]').each(function() {		
		$(this).click(function() {
			var flag = $(this).attr("flag");
			var value = window.showModalDialog(
					'PastHistory_TreatFunction.html?time='
							+ new Date().getTime(), {
						flag : flag,
						data : $(this).data('results')
					}, "dialogHeight:" + screen.height * 0.5
							+ "px;dialogwidth:1000px;");
			if (value) {
				$(this).data("results", value.data);
				$(this).parent().find('textarea[name="treat"]').val(value.text);
			}
		});
	});
	
	//检查结果显示隐藏事件
	$('*[name="checkRstFlag"]').each(function(){
		$(this).click(function(){
			var val=0;
			$('*[name="'+$(this).attr('name')+'"]').each(function(){
				if(this.checked){
					val=$(this).val();
					return false;
				}
			});
			if(this.value==1){
				$('textarea[name="checkRst"]').val("未行检查");
			}
			if(this.value==2){
				$('textarea[name="checkRst"]').val("检查结果不详");
			}
			if(this.value==3){
				$('textarea[name="checkRst"]').val("检查结果：");
			}
		});
	});
	//确定事件
	$('#ok-btn').click(function(){
		var value=FormUtil.getFormValues('form');
		var oldOperation = JSON.stringify(value);
		var treatFunOldOperation = JSON.stringify( $('img[name="treatImg"]').data("results"));
		var grids=[];
		$('#treatDlg tr,#treatRstDlg tr').filter(function(){
			return $(this).find('td').size()>0;
		}).each(function(){
			grids.push({
				id:$(this).find('*[name="id"]').val(),
				gridType:$(this).find('*[name="gridType"]').val(),
				content1:$(this).find('*[name="content1"]').val(),
				content2:$(this).find('*[name="content2"]').val(),
				content3:$(this).find('*[name="content3"]').val(),
				orderNo:$(this).find('*[name="orderNo"]').val()
			});
		});
		
		value.grids=grids;
		var rst=Compose(value);
		//验证该填写的信息不能为空
		if(rst == false){
			return;
		}else{
			if(rst.length > 0){
				rst = '<span ondblclick="' + "{dbclickedSpan=$(this);$('span').addClass('blackstyle');$(this).removeClass('blackstyle');$(this).addClass('redstyle');parent.hidePreHis();parent.symptomInfoIfr('afterTreatment.html', itemName);}" + '" onclick="' + "{var i; for(i = 0; i < store.getCount(); i++){if(store.getAt(i).get('serialNo') == $(this).attr('name')) break;} Ext.getCmp('symptomItemList').getSelectionModel().clearSelections();if($(this).css('color') == 'red'){$('span').addClass('blackstyle');}else{Ext.getCmp('symptomItemList').getSelectionModel().selectRow(i);$('span').addClass('blackstyle');$(this).removeClass('blackstyle');$(this).addClass('redstyle');}}" + '">' + rst + '<span style="display:none">' + oldOperation + '</span><span style="display:none">' + treatFunOldOperation + '</span>' + '</span>';
				var iframe = parent.document.getElementById('FerverHistory');
				iframe.contentWindow.itemValue = rst;
				if(iframe.contentWindow.dbclickedSpan){
					iframe.contentWindow.setReturnValue(iframe.contentWindow.dbclickedSpan);
				}else{
					iframe.contentWindow.setReturnValue();
				}
			}
			backToPreIllWindow();
		}
	});
}
	
function backToPreIllWindow(){
	parent.document.getElementById('FerverHistory').contentWindow.dbclickedSpan = null;
	parent.showPreHis();	
}
 

function Compose(value){
	var rst='';
	var leftInputItem = '';
	var rightInputItem = '';
	var temp=getRowValue('treatProcessFlag',value.treatProcessFlag);
	if(temp.length==0)return '';
	if(value.treatProcessFlag!=5 && value.treatProcessFlag !=6){
		if(value.treatProcessDesc != ""){
			leftInputItem = '<input name="lrtreatProcessDesc" type="text" value="" />';
			rightInputItem = '<input name="lrtreatProcessDesc" type="text" value="。" />';
			if($('input[name="lrtreatProcessDesc"]').size() > 0){
				$('input[name="lrtreatProcessDesc"]').eq(0).wrap("<div id='wrap'></div>");
				leftInputItem = $('#wrap').html();
				$('#wrap').replaceWith($('#wrap').html());		
				$('input[name="lrtreatProcessDesc"]').eq(1).wrap("<div id='wrap'></div>");
				rightInputItem = $('#wrap').html();
				$('#wrap').replaceWith($('#wrap').html());
			}
			rst=leftInputItem+value.treatProcessDesc+rightInputItem;
		}
	}else {
		if(value.treatFn0 != ""){
			rst+=value.treatFn0 +'，';
		}
		if(value.checkRstFlag == 1 || value.checkRstFlag == 2){
			if(value.checkRst != ""){
				rst+=value.checkRst+'，';
			}
		}
		if(value.checkRstFlag == 3){
			if(value.checkRst == "" || value.checkRst == "检查结果："){
				alert('"检查结果"为必填项');
				return false;
			}
			rst+=value.checkRst+'，';
		}
	
		temp=getRowValue('diagRstFlag',value.diagRstFlag);
		if(temp=='有明确诊断'){
			if(value.diagRst == "诊断为"){
				alert('"诊断为"为必填项');
				return false;
			}
			rst+=value.diagRst+'，';
		}else if(temp=='其它'){
			if(value.diagRst0 != ""){
				rst+=value.diagRst0+'，';
			}
		}else if(temp=='未明确诊断'){
			if(value.diagRst != ""){
				rst+=value.diagRst+'，';
			}
		}
		temp=getRowValue('treatFlag',value.treatFlag);
		if(temp.length>0){
			if(temp=='未行治疗' || temp=='具体治疗不详' || temp=='为进一步诊治收治入院'||temp=='有具体治疗' || temp=='其它'){
				if(value.treat != ""){
					rst+=value.treat + '，';
				}
			}else if(temp=='自行服药治疗'){
				if(value.treat == "自行服用"){
					alert('"自行服药治疗"为必填项');
					return false;
				}
				rst+=value.treat+'，';
			}
		}
		rst=rst.length>0?(rst.substr(0,rst.length-1)+'。'):'';
	}
	return rst;
}