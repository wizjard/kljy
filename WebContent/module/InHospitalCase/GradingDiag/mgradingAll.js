var PID = App.util.getHtmlParameters('PID');
Ext.onReady(function(){
	var loader = new Ext.tree.TreeLoader({
            //dataUrl: App.App_Info.BasePath+'/GradingDiagAction.do?method=getAllGradings&account='+App.util.getHtmlParameters('account')
             baseParams:{'account':App.util.getHtmlParameters('account')},
            dataUrl: App.App_Info.BasePath+'/GradingDiagAction.do?method=getAllGradings'
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
				width:140,
				xtype:'treepanel',
				root: root,
	            title: '诊断列表',
	            rootVisible: false,
	            autoScroll: true,
	            useArrows: true,
	            border:false,
	            onlyLeafCheckable: true,
	            containerScroll: true,
	            loader: loader,
	           /** 
	            tbar: [
	            {
	                text: '新增',
	                handler:function(){
	                	Ext.get('iframe').dom.src='register4.html?case='+App.util.getHtmlParameters('KID');
	                }
	            }, '-', {
	                text: '删除',
	                handler:function(){
	                	var url= App.App_Info.BasePath+'/GradingDiagAction.do?method=deleteGrading';
	                	var ss=Ext.getCmp('tree').getSelectionModel().getSelectedNode();
	                	if(ss){
	                		if(confirm('确定要删除选中的诊断？')){
	                			Ext.Ajax.request({
	                				url:url,
	                				params:{id:ss.id},
	                				success:function(response){
	                					if(Ext.decode(response.responseText).success){
	                						var tree=Ext.getCmp('tree');
											ss.remove();
											if(tree.root.childNodes.length>0)
						            			Ext.get('iframe').dom.src='register4.html?case='+App.util.getHtmlParameters('KID')+'&id='+tree.root.childNodes[0].id;
						            		else
						            			Ext.get('iframe').dom.src='';
	                					}else{
	                						alert('删除失败。');
	                					}
	                				}
	                			});
	                		}
	                	}else{
	                		alert('未选择节点。');
	                	}
	                }
	            }],
	           **/
	            listeners:{
	            	render:function(){
	            		root.expand(true,true,function(node){
	            			if(node.childNodes.length>0){
	            				var  id_cid =node.childNodes[0].id.split("_"); 
		            			
		            			var id = id_cid[0];
		            			var cid = id_cid[1];
	            				var  diagnose =node.text.split("--")[1]; 
	            				// alert(diagnose);
								if(diagnose=='门诊'){
						    		Ext.get('iframe').dom.src='register3.html?case='+cid+'&id='+id+'&PID='+PID;
								}else{
									Ext.get('iframe').dom.src='register4.html?case='+cid+'&id='+id+'&PID='+PID;
								}
	            			}
	            			
						    	//Ext.get('iframe').dom.src='register4.html?case='+App.util.getHtmlParameters('KID')+'&id='+node.childNodes[0].id;
						    else
						    	Ext.get('iframe').dom.src='';
	            		});
	            	},
	            	click:function(node){
	            		//Ext.get('iframe').dom.src='register3.html?case='+App.util.getHtmlParameters('KID')+'&id='+node.id+'&PID='+PID;
	            		var  id_cid =node.id.split("_"); 
		            			
		            			var id = id_cid[0];
		            			var cid = id_cid[1];
	            				var  diagnose =node.text.split("--")[1]; 
	            				// alert(diagnose);
								if(diagnose!='门诊'){
						    		Ext.get('iframe').dom.src='register3.html?case='+cid+'&id='+id+'&PID='+PID;
								}else{
									Ext.get('iframe').dom.src='register4.html?case='+cid+'&id='+id+'&PID='+PID;
								}
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
	var  id_cid =node.childNodes[0].id.split("_"); 
		            			
	var id = id_cid[0];
	var cid = id_cid[1];
	var  diagnose =node.text.split("--")[1]; 
	if(diagnose!="门诊"){
	  		Ext.get('iframe').dom.src='register3.html?case='+cid+'&id='+id+'&PID='+PID;
	}else{
		Ext.get('iframe').dom.src='register4.html?case='+cid+'&id='+id+'&PID='+PID;
	}
	
	
	
//	Ext.get('iframe').dom.src='register3.html?case='+App.util.getHtmlParameters('KID')+'&id='+id+'&PID='+PID;
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