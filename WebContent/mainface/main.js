var USER=null;
Ext.onReady(function(){
	LayoutPage();
	check_his_login();
});
function LayoutPage(){
	new Ext.Viewport({
		border:false,
		layout:'border',
		items:[
			{
				region:'north',
				height:110,
				border:false,
				html:'<iframe src="../PUBLIC/CommonPage/top.html" frameborder="0" width="100%" height="110" scroll="no"></iframe>'
			},{
				region:'west',
				title:'<div style="text-indent:0.2em">系统菜单</div>',
				split:true,
                width: 100,
                minSize: 100,
                maxSize: 400,
                collapsible: true,
				border:true,
                margins:'0 0 0 0',
                layout:'accordion',
                layoutConfig:{
                    animate:true
                },
				defaults:{
					autoScroll:true
				},
				listeners:{
					'render':function(_this){
						initMenu(this);
					}
				}
			},{
				region:'center',
				xtype:'tabpanel',
				id:'center-panel',
				activeTab:0,
				items:{
					title:'主页(Home)',
					html:'<iframe src="../module/SystemAdmin/Notice/main.html" frameborder="0" width="100%" height="100%" scroll="auto"></iframe>'
				}
			  }
		]
	});
}
function loadNewTab(_src,_title,_params){
	var _tab=Ext.getCmp('center-panel');
	var _panel=Ext.getCmp(_src);
	if(!_panel){
		_panel=new Ext.Panel({
			title:_title,
			id:_src,
			closable:true,
			html:'<iframe frameborder=0 scrolling=auto width=100% height=100% src="'
				 	+App.App_Info.BasePath+_src+'"></iframe>'
		});
		_tab.add(_panel);
	}
	_tab.setActiveTab(_panel);
}
function initMenu(_left){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/user.do?method=getMyModules',
		success:function(_response,_options){
			var _res=Ext.util.JSON.decode(_response.responseText);
			if(!_res.success){
				_left.body.dom.innerHTML=_res.msg;
				return;
			}
			var _cfgs=Ext.util.JSON.decode(_res.data);
			if(_cfgs){
				var tpl=new Ext.XTemplate('<tpl for=".">','<div class="left-menu-item-con">',
											  '<table width="100%" border=0 cellspacing=0 cellpadding=0>',
												  '<tr>',
													  '<td class="dropShadow">',
														  '<img class="img-normal" src="'+App.App_Info.BasePath+'{icon}" ',
																'onmouseover="this.className=\'img-over\'" ',
																'onmouseout="this.className=\'img-normal\'" ',
																'onclick="{action}"/>',
													  '</td>',
												  '</tr>',
											  '</table>',
											  '<p>{title}</p>',
										  '</div></tpl>'
										 );
				Ext.each(_cfgs,function(_item,_index,_length){
					var _panel=new Ext.Panel({
						title:'<div class="left-menu-item-title">'+_item.title+'</div>',
						border:false
					});	
					_left.add(_panel);
					_left.doLayout();
					tpl.overwrite(_panel.body,_item.children);
				});	
			}
		}
	});	
}
function check_his_login(){
	var search=location.search;//返回URL“？”后面的部分
	if(search.length>0){
		var user=App.util.getHtmlParameters('user');
		var p=App.util.getHtmlParameters('p');
		if(p!=null)
			App.util.addNewMainTab('/module/InHospitalCase/CaseList.html?id='+p,'我的病人');
		else{
			App.util.addNewMainTab('/module/InHospitalCase/MyPatients.html?id='+p,'我的病人');
		}
	}
}

function closeTabPanel(id){
	//alert(id);
		if(id && id.length > 0) {
			Ext.getCmp('center-panel').remove(Ext.getCmp(id));
		}
		else {
		Ext.getCmp('center-panel').remove(Ext.getCmp('center-panel').getActiveTab());
		}
}


