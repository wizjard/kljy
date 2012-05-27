function initPage(pageCfg){
	var _tab=$("#tabs");
	var _ul=$('<ul></ul>');
	var _content='';
	$.each(pageCfg.tabs,function(_index){
		$('<li><a href="#tabs-'+_index+'">'+this.name+'</a></li>').appendTo(_ul);
		_content+='<div class="tabs-content" id="tabs-'+_index+'"><iframe frameborder="0" scrolling="auto" width="100%" height="100%" src=""></iframe></div>';
	});
	_ul.appendTo(_tab);
	$(_content).appendTo(_tab);
	_tab.tabs({
		show:function(event,ui){
			var $iframe=$(ui.panel).find('iframe');
			var id=ui.panel.id;
			var _index=id.split('-')[1];
			var src=$iframe.attr('src');
			$iframe.height(document.body.clientHeight-$('#tabs ul').outerHeight()-$('#toolbar').outerHeight()-15);
			if(!src||src.length==0){
				$iframe.attr('src',App.App_Info.BasePath+pageCfg.tabs[_index].src+'?button=no&KID='+pageCfg.KID+'&PID='+pageCfg.PID);
			}
		}
	});
	$('button').mouseover(function(){
		$(this).addClass('btn-hover');
	}).mouseout(function(){
		$(this).removeClass('btn-hover');
	});
	$('#save-btn').click(function(){
		var selected = $("#tabs").tabs('option', 'selected');
		$('#tabs-'+selected).find('iframe').get(0).contentWindow.SaveData();
	});
	$('#back-btn').click(function(){
		//location.href=App.App_Info.BasePath+'/module/InHospitalCase/CaseList.html?id='+pageCfg.PID;
		parent.backToHistory();
	});
	$('#print-btn').click(function(){
		window.open($(this).attr('link')+'?id='+pageCfg.KID)
	});
}