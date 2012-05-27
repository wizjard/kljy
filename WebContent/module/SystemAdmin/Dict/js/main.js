Ext.onReady(function(){
	Ext.QuickTips.init();
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'west',
				id:'west-panel',
				title:'字典模块',
				width:150,
				xtype:'treepanel',
				autoScroll:true,
				containerScroll:true,
				selectedNode:null,
				copyNode:null,
				setCopyNode:function(_node){
					this.copyNode=_node;
				},
				getCopyNode:function(){
					return this.copyNode;
				},
				dataUrl:App.App_Info.BasePath+'/admin/DictAction.do?method=queryAllModulesExtTree',
				root:{
					nodeType:'async',
					text:'字典树',
					draggable:false,
					id:-1
				},
				rootVisible:false,
				listeners:{
					render:function(){
						//this.getRootNode().expand();
						this.expandAll();
					},
					click:function(_node,_e){
						this.selectedNode=_node;
						if(_node.isLeaf()){
							treeNodeClick(_node.id);
						}
					}
				},
				getSelectedNode:function(){
					return this.selectedNode;
				},
				getSelectedNodeId:function(){
					var _node=this.getSelectedNode();
					if(!_node){
						Ext.Msg.show({
							title:'警告',
							msg:'未选择任何节点。',
							buttons:Ext.Msg.OK,
							fn:Ext.emptyFn,
							icon:Ext.MessageBox.WARNING
						});
						return null;
					}else
						return _node.id;
				},
				tbar:[
					{
						text:'节点',menu:[
							{
								text:'同级增加',handler:function(){
									var _id=Ext.getCmp('west-panel').getSelectedNodeId();
									if(!_id)	return;
									showTreeNodeEditWin({
										win_title:'同级节点增加',
										id:-1,
										pid:Ext.getCmp('west-panel').getSelectedNode().parentNode.id
									});
								}
							},{
								text:'下级增加',handler:function(){
									var _id=Ext.getCmp('west-panel').getSelectedNodeId();
									if(!_id)	return;
									showTreeNodeEditWin({
										win_title:'下级节点增加',
										id:-1,
										pid:_id
									});
								}
							},{
								text:'查看信息',handler:function(){
									var _id=Ext.getCmp('west-panel').getSelectedNodeId();
									if(!_id)	return;
									Ext.Ajax.request({
										url:App.App_Info.BasePath+'/admin/DictAction.do?method=treeModule_findById&id='+_id,
										success:function(_response,_options){
											var _res=Ext.util.JSON.decode(_response.responseText);
											if(_res.success){
												var _node=Ext.util.JSON.decode(_res.data);
												showTreeNodeEditWin({
													win_title:'查看节点详细信息',
													id:_node.id,
													pid:_node.pid,
													name:_node.name,
													code:_node.code,
													comment:_node.comment,
													readOnly:true
												});
											}else{
												Ext.Msg.alert('失败！','节点详细信息获取失败。',Ext.emptyFn);
											}
										}
									});
								}
							},{
								text:'编辑',handler:function(){
									var _id=Ext.getCmp('west-panel').getSelectedNodeId();
									if(!_id)	return;
									Ext.Ajax.request({
										url:App.App_Info.BasePath+'/admin/DictAction.do?method=treeModule_findById&id='+_id,
										success:function(_response,_options){
											var _res=Ext.util.JSON.decode(_response.responseText);
											if(_res.success){
												var _node=Ext.util.JSON.decode(_res.data);
												showTreeNodeEditWin({
													win_title:'更新节点信息',
													id:_node.id,
													pid:_node.pid,
													name:_node.name,
													code:_node.code,
													comment:_node.comment,
													readOnly:false
												});
											}else{
												Ext.Msg.alert('失败！','节点详细信息获取失败。',Ext.emptyFn);
											}
										}
									});
								}
							},{
								text:'删除',handler:function(){
									var _id=Ext.getCmp('west-panel').getSelectedNodeId();
									if(!_id)	return;
									Ext.Msg.confirm('警告','节点删除后节点下的所有字典项也将<br/>'
										+'删除并不可恢复，确定要删除？',function(_btn){
										if(_btn=='yes'){
											Ext.Ajax.request({
												url:App.App_Info.BasePath+'/admin/DictAction.do?method=treeModule_delete&id='+_id,
												success:function(_response,_options){
													var _res=Ext.util.JSON.decode(_response.responseText);
													if(_res.success){
														Ext.Msg.alert('成功！','节点删除成功。',function(){
															Ext.getCmp('west-panel').root.reload();
															var _panel=Ext.getCmp('center-panel');
															if(_panel.body){
																var _iframes=_panel.body.query('iframe');
																if(_iframes.length>0){
																	_iframes[0].src='#';
																}
															}
														});
													}else{
														Ext.Msg.alert('失败！','节点删除失败。',Ext.emptyFn);
													}
												}
											});
										}	
									});
								}
							},{
								text:'导出到文件',handler:function(){
									var _id=Ext.getCmp('west-panel').getSelectedNodeId();
									if(!_id)	return;
									var name=window.prompt('请输入导出文件的名称：','node');
									if(!name)return;
									if(name.length==0){
										alert('文件名称不能为空。');
									}
									Ext.Ajax.request({
										url:App.App_Info.BasePath+'/admin/DictAction.do?method=node_export',
										params:{
											name:name,
											id:_id
										},
										success:function(_response,_options){
											var _res=Ext.util.JSON.decode(_response.responseText);
											if(_res.success){
												Ext.Msg.alert('提示','节点数据导出成功。',Ext.emptyFn);
											}else{
												Ext.Msg.alert('提示','节点数据导出失败。',Ext.emptyFn);
											}
										}
									});
								}
							},{
								text:'从文件导入',handler:function(){
									var _id=Ext.getCmp('west-panel').getSelectedNodeId();
									if(!_id)	return;
									var name=window.prompt('请输入导入文件的名称：','node');
									if(!name)return;
									if(name.length==0){
										alert('文件名称不能为空。');
									}
									Ext.Ajax.request({
										url:App.App_Info.BasePath+'/admin/DictAction.do?method=node_import',
										params:{
											name:name,
											id:_id
										},
										success:function(_response,_options){
											var _res=Ext.util.JSON.decode(_response.responseText);
											if(_res.success){
												Ext.Msg.alert('提示','节点数据导入成功。',Ext.emptyFn);
												Ext.getCmp('west-panel').root.reload();
											}else{
												Ext.Msg.alert('提示','节点数据导入失败。',Ext.emptyFn);
											}
										}
									});
								}
							}
						]
					},{
						xtype:'tbseparator'
					},{
						text:'复制',menu:[
							{
								text:'复制节点字典',handler:function(){
									var _tree=Ext.getCmp('west-panel');
									var _id=_tree.getSelectedNodeId();
									if(!_id)	return;
									var _node=_tree.getSelectedNode();
									if(!_node.isLeaf()){
										Ext.Msg.alert('提示','非叶子节点不能执行此操作。',Ext.emptyFn);
										return;
									}
									_tree.setCopyNode(_node);
								}
							},{
								text:'粘贴字典到节点',handler:function(){
									var _tree=Ext.getCmp('west-panel');
									var _id=_tree.getSelectedNodeId();
									if(!_id)	return;
									var _node=_tree.getSelectedNode();
									if(!_node.isLeaf()){
										Ext.Msg.alert('提示','非叶子节点不能执行此操作。',Ext.emptyFn);
										return;
									}
									var _copyNode=_tree.getCopyNode();
									Ext.Ajax.request({
										url:App.App_Info.BasePath+'/admin/DictAction.do?method=treeModule_copy',
										params:{
											oid:_copyNode.id,
											nid:_node.id
										},
										success:function(_response,_options){
											var _res=Ext.util.JSON.decode(_response.responseText);
											if(_res.success){
												Ext.Msg.alert('提示','节点复制成功。',Ext.emptyFn);
												treeNodeClick(_node.id);
											}else{
												Ext.Msg.alert('提示','节点复制失败。',Ext.emptyFn);
											}
										}
									});
								}
							}
						]
					},{
						xtype:'tbseparator'
					},{
						text:'刷新',handler:function(){
							var _tree=Ext.getCmp('west-panel');
							_tree.root.reload();
							_tree.expandAll();
						}
					}
				]
			},{
				region:'center',
				id:'center-panel',
				title:'字典维护主界面 (<a href="#" onclick="javascript:refreshMainFrame()">刷新</a>)',
				html:'<iframe width="100%" height="100%" frameborder="0" scroll="auto" src="#"></iframe>'
			}
		]
	});
});
function showTreeNodeEditWin(_config){
	var _cfg={
		win_title:'同级节点增加',
		id:-1,
		pid:-1,
		name:'',
		code:'',
		comment:'',
		readOnly:false
	};
	Ext.apply(_cfg,_config);
	new Ext.Window({
		title:_cfg.win_title,
		frame:true,
		closable:false,
		closeAction:'close',
		width:400,
		autoHeight:true,
		x:15,
		y:30,
		layout:'fit',
		buttonAlign:'center',
		buttons:[
			{
				text:'保存',handler:function(){
					if(_cfg.readOnly) return;
					var _win=this.ownerCt;
					var _form=_win.items.get(0).form;
					if(_form.isValid()){
						var _json=Ext.util.JSON.encode(_form.getValues());
						Ext.Ajax.request({
							url:App.App_Info.BasePath+'/admin/DictAction.do?method=treeModule_saveOrUpdate',
							params:{data:_json},
							success:function(_response,_options){
								var _res=Ext.util.JSON.decode(_response.responseText);
								if(_res.success){
									Ext.Msg.alert('成功','保存成功。',function(){
										Ext.getCmp('west-panel').root.reload();
										_win.close();
									});
								}else{
									Ext.Msg.show({
										title:'失败',
										msg:'保存失败。',
										buttons:Ext.Msg.OK,
										fn:Ext.emptyFn,
										icon:Ext.MessageBox.WARNING
									});
								}
							}
						});
					}else{
						Ext.Msg.show({
							title:'警告',
							msg:'表单未通过验证，请检查填写。',
							buttons:Ext.Msg.OK,
							fn:Ext.emptyFn,
							icon:Ext.MessageBox.WARNING
						});
					}
				}
			},{
				text:'重置',handler:function(){
					if(_cfg.readOnly) return;
					this.ownerCt.items.get(0).form.reset();
				}
			},{
				text:'取消',handler:function(){
					this.ownerCt.close();
				}
			}
		],
		items:{
			xtype:'form',
			frame:true,
			border:false,
			labelWidth:65,
			autoHeight:true,
			labelAlign:'right',
			items:[
				{
					xtype:'textfield',
					name:'name',
					fieldLabel:'节点名称',
					allowBlank:false,
					readOnly:_cfg.readOnly,
					anchor:'95%',
					value:_cfg.name
				},{
					xtype:'textfield',
					name:'code',
					fieldLabel:'节点代码',
					allowBlank:false,
					readOnly:_cfg.readOnly,
					anchor:'95%',
					value:_cfg.code
				},{
					xtype:'textarea',
					name:'comment',
					fieldLabel:'备注',
					readOnly:_cfg.readOnly,
					anchor:'95%',
					height:60,
					value:_cfg.comment
				},{
					xtype:'hidden',
					name:'id',
					value:_cfg.id
				},{
					xtype:'hidden',
					name:'pid',
					value:_cfg.pid
				}
			]
		}
	}).show();
}
function refreshMainFrame(){
	var _panel=Ext.getCmp('center-panel');
	if(_panel.body){
		var _iframes=_panel.body.query('iframe');
		if(_iframes.length>0){
			_iframes[0].contentWindow.location.reload();
		}
	}
}
function treeNodeClick(_id){
	var _panel=Ext.getCmp('center-panel');
	if(_panel.body){
		var _iframes=_panel.body.query('iframe');
		if(_iframes.length>0){
			_iframes[0].src='Index&Item.html?id='+_id;
		}
	}
}