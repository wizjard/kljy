var pid=App.util.getHtmlParameters('PID');
var kid=App.util.getHtmlParameters('KID');
var outDate = App.util.getHtmlParameters('outDate');
var title=" "
var indexId = null;
var entity=App.util.getHtmlParameters('entity');
var subScoreItems = "";
var ssmId = "";
var url = App.App_Info.BasePath+'/InHospitalCase/ScoreComment.do?method=getScoreSetMealmenzhen';
var ifrPrintUrl = "";
var panel=new Ext.tree.TreePanel({
			title:'',
			copyNode:null,
			split:true,
			collapsible:true,
			autoScroll:true,
			containerScroll:true,
			
			loader:new Ext.tree.TreeLoader({dataUrl:url}),
			root:{
				nodeType:'async',
				text:'评分套餐',
				draggable:false,
				itemName:'',
				id:-1
			},
			useArrows:true,
			rootVisible:false,
			listeners:{
				render:function(){
					this.expandAll();
				},						
				click:function(_node,_e){
					if(_node.leaf){
						title = _node.text;
						subScoreItems = _node.attributes.subScoreItems;
						ssmId = _node.attributes.id;
						indexId = null;
						var url = createIndex();
						var height=Ext.getCmp('main').getInnerHeight();
						document.getElementById('scoreItems').innerHTML=
						'<iframe id="iframe" src="'+url+'" width="100%" height="1'+height+'" frameborder="0" srcoll="true"></iframe>'+
						'<iframe id="iframePrint" src="" width="0" height="0" frameborder="0" srcoll="true"></iframe>';
						
					}
				}
			}
		});

Ext.onReady(function(){
	new Ext.Viewport({
		layout:'border',
		items:[{
			region:'west',
			title:'评分套餐',
			width:180,
			split:true,
			collapsible:true,
			autoScroll:true,
			containerScroll:true,
			html:'<div id="treeDiv"></div>'
		},{
			region:'center',
			title:title,
			id:'main',
			autoScroll:true,
			tbar:[
				'-',{
					text:'索引',id:'index-btn',menu:[]
				},'-','->','-',{
					text:'保存',id:'save-btn',handler:function(){
						document.getElementById('iframe').contentWindow.SaveData();
					}
				}
				,'-',{
					text:'删除',id:'delete-btn',handler:function(){
						document.getElementById('iframe').contentWindow.deleteData();
					}
				}
				,'-',{
					text:'打印',id:'print-btn',handler:function(){
						//document.getElementById('iframePrint').src="print/"+ifrPrintUrl;
						document.getElementById('iframe').contentWindow.print();
					}
				},'-',{
					text:'返回',handler:function(){
						parent.backToHistory();
					}
				},'-'
			],
			html:'<div id="scoreItems"></div><div style="width:20px;">&nbsp;</div>'
		}]
	});
	panel.setHeight(document.body.scrollHeight);
	panel.render('treeDiv');
//	document.getElementById("scoreItems").style.height=panel.getInnerHeight();
});
function createIndex(){
	var url='';
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/InHospitalCase/ScoreComment.do',
		params:{
			method:'public_mainMenu',
			ssmId:ssmId,
			kid:kid
		},
		sync:true,
		success:function(_response){
			var btn=Ext.getCmp('index-btn');
			btn.menu=new Ext.menu.Menu({items:[]});
			btn.menu.add(new Ext.menu.Item({text:'新增',id:'-1',handler:indexClick}));
			var res=Ext.decode(_response.responseText);
			if(res.success){
				var menus=Ext.decode(res.data);
				Ext.each(menus,function(_item){
					btn.menu.add(new Ext.menu.Item({text:_item.scoreTime,id:_item.id+"",handler:indexClick}));
				});
				var panel=Ext.getCmp('main');
				var t=title;
				if(btn.menu.items.length==1){
					t=title+'(新增)';
					url=entity+'.html?pid='+pid+'&kid='+kid+'&this=-1&timestamp='+new Date().getTime()+'&outDate='+outDate;
					url = url + '&subScoreItems=' + subScoreItems + '&title=' + title + '&ssmId=' + ssmId; 
				}else{
					var lastMenu=btn.menu.items.get(btn.menu.items.length-1);
					t=title+'('+lastMenu.text+')';
					url=entity+'.html?pid='+pid+'&kid='+kid+'&this='+lastMenu.id+'&timestamp='+new Date().getTime()+'&outDate='+outDate;
					url = url + '&subScoreItems=' + subScoreItems + '&title=' + title + '&ssmId=' + ssmId; 
				}
				if(indexId != -1){
					panel.setTitle(t);
				}
			}else{
				alert('初始化索引菜单失败。');
			}
		}
	});
	ifrPrintUrl = url;
	return url;
}
function setAuth(data){
	Ext.getCmp('main').setTitle(data.title);
}

function indexClick(){
	indexId = this.id;
	setAuth({title:title+'('+this.text+')'});
	var url=entity+'.html?pid='+pid+'&kid='+kid+'&this='+this.id+'&timestamp='+new Date().getTime()+'&outDate='+outDate;
	url = url + '&subScoreItems=' + subScoreItems + '&title=' + title + '&ssmId=' + ssmId; 
	ifrPrintUrl = url;
	document.getElementById('iframe').src=url;
}


//liugang 2011-09-05 start
function flushParentPage(){
	var url = createIndex();
	var height=Ext.getCmp('main').getInnerHeight();
	document.getElementById('scoreItems').innerHTML=
	'<iframe id="iframe" src="'+url+'" width="100%" height="1'+height+'" frameborder="0" srcoll="true"></iframe>'+
	'<iframe id="iframePrint" src="" width="0" height="0" frameborder="0" srcoll="true"></iframe>';
}
//liugang 2011-09-05 end
