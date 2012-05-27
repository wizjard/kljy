Ext.onReady(function(){
	LayoutPage();
});
function LayoutPage(){
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget='side';
	var _sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'west',
				id:'west-panel',
				title:'机构单位',
				width:180,
				xtype:'treepanel',
				autoScroll:true,
				containerScroll:true,
				dataUrl:App.App_Info.BasePath+'/admin/BelongRoleAction.do?method=belong_findAll',
				root:{
					nodeType:'async',
					text:'字典树',
					draggable:false,
					id:-1
				},
				selectedNode:null,
				getSelectedNode:function(){
					return this.selectedNode;
				},
				rootVisible:false,
				tbar:[
					{
						text:'添加',menu:[
							{
								text:'机构',handler:function(){
									showBelongEditorWin(new Ext.data.Record({
										ID:-1,
										PCODE:null,
										CODE:'',
										NAME:'',
										PYCODE:'',
										MEMO:''
									}));
								}
							},{
								text:'机构单位',handler:function(){
									var _node=Ext.getCmp('west-panel').getSelectedNode();
									if(!_node){
										alert('请先选择一个机构。');
										return;
									}
									if(_node.isLeaf()){
										alert('选中的节点不是机构。');
										return;
									}
									Ext.Ajax.request({
										url:App.App_Info.BasePath+'/admin/BelongRoleAction.do?method=belong_findById&id='+_node.id,
										success:function(_response,_options){
											var _res=Ext.util.JSON.decode(_response.responseText);
											if(_res.success){
												var _data=Ext.util.JSON.decode(_res.data);
												showBelongEditorWin(new Ext.data.Record({
													ID:-1,
													PCODE:_data['CODE'],
													CODE:'',
													NAME:'',
													PYCODE:'',
													MEMO:''
												}));
											}else{
												alert('机构单位信息获取失败。');
											}
										}
									});
								}
							}
						]
					},{
						xtype:'tbseparator'
					},{
						text:'删除',handler:function(){
							var _node=Ext.getCmp('west-panel').getSelectedNode();
							if(!_node){
								alert('请先选择一个机构或单位。');
								return;
							}
							if(!confirm('确定要删除？'))	return;
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/admin/BelongRoleAction.do?method=belong_delete&id='+_node.id,
								success:function(_response,_options){
									var _res=Ext.util.JSON.decode(_response.responseText);
									if(_res.success){
										alert('删除成功。');
										Ext.getCmp('west-panel').root.reload();
									}else{
										alert('删除失败。');
									}
								}
							});
						}
					},{
						xtype:'tbseparator'
					},{
						text:'编辑',handler:function(){
							var _node=Ext.getCmp('west-panel').getSelectedNode();
							if(!_node){
								alert('请先选择一个机构或单位。');
								return;
							}
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/admin/BelongRoleAction.do?method=belong_findById&id='+_node.id,
								success:function(_response,_options){
									var _res=Ext.util.JSON.decode(_response.responseText);
									if(_res.success){
										var _data=Ext.util.JSON.decode(_res.data);
										_data['PCODE']=_data['PCODE']||null;
										showBelongEditorWin(new Ext.data.Record(_data));
									}else{
										alert('机构单位信息获取失败。');
									}
								}
							});
						}
					}
				],
				listeners:{
					render:function(){
						//this.expandAll();
					},
					click:function(_node){
						this.selectedNode=_node;
					},
					dblclick:function(_node){
						TreeNodeDblClick(_node.id);
					}
				}
			},new Ext.grid.EditorGridPanel({
				region:'center',
				id:'grid',
				title:'角色列表',
				autoScroll:true,
				columns:[
					new Ext.grid.RowNumberer(),
					_sm,
					{header:'角色代码',dataIndex:'CODE',editor:new Ext.form.TextField({allowBlank:false})},
					{header:'角色名称',dataIndex:'NAME',editor:new Ext.form.TextField({allowBlank:false})},
					{header:'角色说明',dataIndex:'MEMO',editor:new Ext.form.TextField({allowBlank:true})}
				],
				ds:new Ext.data.Store({
					pruneModifiedRecords:true,
					proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/BelongRoleAction.do?method=role_findAll'}),
					reader:new Ext.data.JsonReader({root:''},[
						{name:'ID'},
						{name:'CODE'},
						{name:'NAME'},
						{name:'MEMO'},
						{name:'Modules'}
					])
				}),
				sm:_sm,
				viewConfig:{
					forceFit:true
				},
				listeners:{
					'render':function(){
						this.getStore().load();
						this.ownerCt.items.get(0).setWidth(Ext.getBody().getSize().width/2);
					}
				},
				tbar:[
					{
						text:'新增',handler:function(){
							Ext.getCmp('grid').getStore().insert(0,new Ext.data.Record({
								ID:-1,
								CODE:'',
								NAME:'',
								MEMO:''
							}));
						}
					},{
						xtype:'tbseparator'
					},{
						text:'删除',handler:function(){
							var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
							if(_ss.length==0){
								alert('请至少选择一行。');
								return;
							}
							if(!confirm('确定要删除角色？'))	return;
							if(_ss[0].data.ID==-1){
								Ext.getCmp('grid').getStore().remove(_ss[0]);
							}else{
								Ext.Ajax.request({
									url:App.App_Info.BasePath+'/admin/BelongRoleAction.do?method=role_delete&id='+_ss[0].data.ID,
									success:function(_response,_options){
										var _res=Ext.util.JSON.decode(_response.responseText);
										if(_res.success){
											Ext.getCmp('grid').getStore().remove(_ss[0]);
										}else{
											alert('删除失败。');
										}
									}
								});
							}
						}
					},{
						xtype:'tbseparator'
					},{
						text:'保存',handler:function(){
							var _store=Ext.getCmp('grid').getStore();
							var _mr=_store.getModifiedRecords();
							if(_mr.length==0){
								alert('无数据需要保存。');
								return;
							}
							var _array=[];
							Ext.each(_mr,function(_item,_index){
								_array.push(_item.data);
							});
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/admin/BelongRoleAction.do?method=role_saveOrUpdate',
								params:{data:Ext.util.JSON.encode(_array)},
								success:function(_response,_options){
									var _res=Ext.util.JSON.decode(_response.responseText);
									if(_res.success){
										alert('保存成功。');
										_store.reload();
									}else{
										alert('保存失败。');
									}
								}
							});
						}
					},{
						xtype:'tbseparator'
					},{
						text:'设置权限',handler:function(){
							var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
							if(_ss.length==0){
								alert('请至少选择一行。');
								return;
							}
							if(_ss[0].data.ID==-1){
								alert('请先保存本行数据。');
								return;
							}
							var returnObj=window.showModalDialog(
								'ModuleSelect.html?check='+_ss[0].data.Modules,
								 null,
								'dialogWidth=350px;dialogHeight=400px');
							if(returnObj||returnObj.length==0){
								_ss[0].data.Modules=returnObj;
								Ext.Ajax.request({
									url:App.App_Info.BasePath+'/admin/BelongRoleAction.do?method=role_updateModules',
									params:{id:_ss[0].data.ID,modules:returnObj},
									success:function(_response,_options){
										var _res=Ext.util.JSON.decode(_response.responseText);
										if(_res.success){
											alert('权限设置成功。');
										}else{
											alert('权限设置失败。');
										}
									}
								});
							}
						}
					}
				]
			})
		]
	});
}
function showBelongEditorWin(_record){
	new Ext.Window({
		title:'机构单位编辑窗口',
		closable:false,
		width:400,
		closeAction:'close',
		buttonAlign:'center',
		autoHeight:true,
		layout:'fit',
		items:{
			xtype:'form',
			bodyStyle:'padding-top:5px;',
			frame:true,
			border:false,
			labelAlign:'right',
			autoHeight:true,
			labelWidth:90,
			items:[
				{
					xtype:'hidden',
					name:'ID'
				},{
					xtype:'textfield',
					fieldLabel:'上级机构(单位)',
					name:'PCODE',
					anchor:'95%',
					setValue:function(_val){
						if(!_val||_val.length==0){
							this.el.dom.readOnly=true;
							this.el.dom.value='';
						}else{
							this.el.dom.value=_val;
						}
					}
				},{
					xtype:'textfield',
					fieldLabel:'机构(单位)代码',
					allowBlank:false,
					name:'CODE',
					anchor:'95%'
				},{
					xtype:'textfield',
					fieldLabel:'机构(单位)名称',
					name:'NAME',
					allowBlank:false,
					anchor:'95%'
				},{
					xtype:'textfield',
					fieldLabel:'拼音码',
					name:'PYCODE',
					anchor:'95%'
				},{
					xtype:'textarea',
					fieldLabel:'机构(单位)说明',
					name:'MEMO',
					height:50,
					anchor:'95%',
					listeners:{
						render:function(){
							this.ownerCt.form.loadRecord(_record);
						}
					}
				}
			]
		},
		buttons:[
			{
				text:'保存',handler:function(){
					var _form=this.ownerCt.items.get(0).form;
					if(!_form.isValid()){
						alert('表单未通过验证。');
						return;
					}
					var _values=Ext.util.JSON.encode(_form.getValues());
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/admin/BelongRoleAction.do?method=belong_saveOrUpdate',
						params:{data:_values},
						scope:this,
						success:function(_response,_options){
							var _res=Ext.util.JSON.decode(_response.responseText);
							if(_res.success){
								alert('保存成功。');
								Ext.getCmp('west-panel').root.reload();
								this.ownerCt.close();
							}else{
								alert('保存失败。');
							}
						}
					});
				}
			},{
				text:'重置',handler:function(){
					this.ownerCt.items.get(0).form.reset();
				}
			},{
				text:'关闭',handler:function(){
					this.ownerCt.close();
				}
			}
		]
	}).show();
}
