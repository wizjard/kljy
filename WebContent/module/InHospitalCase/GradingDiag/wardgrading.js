var PID = App.util.getHtmlParameters('PID');
Ext.onReady(function(){
	var loader = new Ext.tree.TreeLoader({
            dataUrl: App.App_Info.BasePath+'/GradingDiagAction.do?method=getGradings&CID='+App.util.getHtmlParameters('KID')
        });
        var root = new Ext.tree.AsyncTreeNode({
            id: 'root',
            text: '肝病十二级分类诊断表'
        });
	new Ext.Viewport({
		layout:'border',
		items:[
			 {
	            region:'north',
				border:true,
				height:23,
				html:'<div id="PatSimInfo-DIV"></div>'
         	}
			,{
				region:'west',
				id:'tree',
				width:120,
				xtype:'treepanel',
				root: root,
	            title: '诊断记录',
	            rootVisible: false,
	            autoScroll: true,
	            useArrows: true,
	            border:false,
	            onlyLeafCheckable: true,
	            containerScroll: true,
	            loader: loader,
	            listeners:{
	            	render:function(){
	            		root.expand(true,true,function(node){
	            			if(node.childNodes.length>0)
						    	Ext.get('iframe').dom.src='wardregister3.html?case='+App.util.getHtmlParameters('KID')+'&id='+node.childNodes[0].id+'&PID='+PID;
						    else
						    	Ext.get('iframe').dom.src='';
	            		});
	            	},
	            	click:function(node){
	            		Ext.get('iframe').dom.src='wardregister3.html?case='+App.util.getHtmlParameters('KID')+'&id='+node.id+'&PID='+PID;
	            	}
	            }
			},{
				region:'center',
				border:false,
				listeners:{
					render:function(){
						Ext.DomHelper.append(this.body,{
							id:'iframe',
							tag:'iframe',
							width:'100%',
							height:'100%',
							frameborder:0,
							scrolling:'no',
							src:''
						});
					}
				}
			}
		]
	});
	loadPatSimInfo();
});
function refreshNode(id){
	var tree=Ext.getCmp('tree');
	tree.getLoader().load(tree.root);
	Ext.get('iframe').dom.src='wardregister3.html?case='+App.util.getHtmlParameters('KID')+'&id='+id+'&PID='+PID;
}

function loadPatSimInfo(){
	var _div=Ext.get('PatSimInfo-DIV');
	if (!_div) {
		Ext.DomHelper.append(Ext.getBody(), {
			tag: 'div',
			id: 'PatSimInfo-DIV'
		});
		_div=Ext.get('PatSimInfo-DIV');
	}
	_div.load({
		url:App.App_Info.BasePath+'/module/Patient/PatSimpleInfo.jsp?id='+PID,
		scripts:true,
		text:'正在获取病人基本信息......'
	});
}