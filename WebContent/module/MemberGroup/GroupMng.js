var fldType={
	'int':'整数',
	'varchar':'单行文本',
	'float':'小数',
	'datetime':'时间',
	'ntext':'长文本'
};
var dateFormat=['yyyy-MM-dd','yyyy-MM-dd HH','yyyy-MM-dd HH:mm','yyyy-MM-dd HH:mm:ss'];
var tplType=[['0','治疗建议'],['1','生活指导'],['2','检查']];
var tplCourseUnit=[['',''],['0','月'],['1','周'],['2','天']];
var _consNode=null;
var _contNode=null;
Ext.onReady(function(){
	var sm=new Ext.grid.CheckboxSelectionModel();
	var sm2=new Ext.grid.CheckboxSelectionModel();
	var sm3=new Ext.grid.CheckboxSelectionModel();
	var sm4=new Ext.grid.CheckboxSelectionModel();
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				title:'组别索引',
				region:'west',
				id:'west-panel',
				width:150,
				xtype:'treepanel',
				autoScroll:true,
				split:'true',
				containerScroll:true,
				dataUrl:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do?method=tree_findAll',
				root:{
					nodeType:'async',
					text:'字典树',
					draggable:false,
					id:-1
				},
				rootVisible:false,
				listeners:{
					render:function(){
						this.getRootNode().expand();
					}
				},
				getSelectedNode:function(){
					var _ss=this.getSelectionModel().getSelectedNode();
					if(!_ss){
						alert('请选择一个节点。');
						return null;
					}
					return _ss;
				},
				tbar:[
					'-',{
						text:'节点',menu:[
							{
								text:'新增节点',handler:function(){
									TreeNodeEditWin(null,'-1');
								}
							},
//							{
//								text:'新增亚组',handler:function(){
//									var _node=Ext.getCmp('west-panel').getSelectedNode();
//									if(!_node)return;
//									if(_node.id.split('>')[2]!=-1){
//										alert('选中节点不是大组。');
//										return;
//									}
//									TreeNodeEditWin(null,_node.id.split('>')[0]);
//								}
//							},
							{
								text:'编辑节点',handler:function(){
									var _node=Ext.getCmp('west-panel').getSelectedNode();
									if(!_node)return;
									TreeNodeEditWin(_node);
								}
							},{
								text:'删除节点',handler:function(){
									var _node=Ext.getCmp('west-panel').getSelectedNode();
									if(!_node)return;
									if(!confirm('确定要删除？'))return;
									Ext.Ajax.request({
										url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
										params:{
											method:'tree_delete',
											id:_node.id.split('>')[0]
										},
										success:function(_resonse){
											if(Ext.decode(_resonse.responseText).success){
												alert('删除成功。');
												Ext.getCmp('west-panel').root.reload();
											}else{
												alert('删除失败。');
											}
										}
									});
								}
							}
						]
					},'-',{
						text:'数据表',menu:[
							{
								text:'结构',handler:function(){
									var _node=Ext.getCmp('west-panel').getSelectedNode();
									if(!_node)return;
									_consNode=_node;
									var _panel=Ext.getCmp('center-panel');
									_panel.setActiveTab(_panel.items.get(1));
									var _grid=Ext.getCmp('grid2');
									_grid.setTitle(_node.text);
									_grid.getStore().baseParams={id:_node.id.split('>')[0]};
									_grid.getStore().load();
								}
							},{
								text:'内容',handler:function(){
									var _node=Ext.getCmp('west-panel').getSelectedNode();
									if(!_node)return;
									_contNode=_node;
									Ext.Ajax.request({
										url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
										params:{
											method:'treeFld_findAll',
											id:_node.id.split('>')[0]
										},
										success:function(_response){
											var array=Ext.decode(_response.responseText);
											if(array.length==0){
												alert('数据表结构未定义，请先点击 数据表->结构 定义结构。');
												return;
											}
											var cmarray=[new Ext.grid.RowNumberer(),sm4];
											var readerarray=[{name:'id'},{name:'treeId'}];
											Ext.each(array,function(){
												if(this.fldType=='ntext')
													cmarray.push({header:this.fldName,dataIndex:this.fldCode,renderer:function(value,metadata,record,rowIndex,colIndex,store){
														return '<p style="cursor:pointer;color:blue" onclick="ShowContentDetail('+rowIndex+',\''+colIndex+'\')">查看</p>';
													}});
												else
													cmarray.push({header:this.fldName,dataIndex:this.fldCode});
												readerarray.push({name:this.fldCode});
											});
											var cm=new Ext.grid.ColumnModel(cmarray);
											var ds=new Ext.data.Store({
												proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do'}),
												reader:new Ext.data.JsonReader({root:''},readerarray)
											});
											ds.baseParams={method:'content_findAll',fldCfg:Ext.encode(array),treeId:_node.id.split('>')[0]};
											ds.load();
											var grid=new Ext.grid.GridPanel({
												id:'grid3',
												title:_node.text,
												autoScroll:true,
												cm:cm,
												sm:sm4,
												ds:ds,
												viewConfig:{
													forceFit:true
												},
												getFldCfg:function(){
													return array;
												},
												tbar:[
													'-',{
														text:'刷新表格',handler:function(){
															Ext.getCmp('grid3').getStore().reload();
														}
													},'-',{
														text:'新增数据',handler:function(){
															tableRecordEditWin(null,Ext.getCmp('grid3').getFldCfg());
														}
													},'-',{
														text:'编辑数据',handler:function(){
															var _ss=Ext.getCmp('grid3').getSelectionModel().getSelections();
															if(_ss.length==0||_ss.length>1){
																alert('请选择一条记录。');
																return;
															}
															tableRecordEditWin(_ss[0],Ext.getCmp('grid3').getFldCfg());
														}
													},'-',{
														text:'删除记录',handler:function(){
															var _ss=Ext.getCmp('grid3').getSelectionModel().getSelections();
															if(_ss.length==0||_ss.length>1){
																alert('请选择一条记录。');
																return;
															}
															if(!confirm('确定要删除选中记录?'))return;
															Ext.Ajax.request({
																url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
																params:{
																	method:'content_delete',
																	id:_ss[0].data.id
																},
																success:function(_response){
																	if(Ext.decode(_response.responseText).success){
																		alert('删除成功。');
																		Ext.getCmp('grid3').getStore().reload();
																	}else{
																		alert('删除失败。');
																	}
																}
															});
														}
													},'-',{
														text:'医嘱随访模板',handler:function(){
															var _ss=Ext.getCmp('grid3').getSelectionModel().getSelections();
															if(_ss.length==0||_ss.length>1){
																alert('请选择一条记录。');
																return;
															}
															new Ext.Window({
																frame:true,
																closable:false,
																buttonAlign:'center',
																closeAction:'close',
																width:500,
																height:400,
																autoScroll:true,
																layout:'fit',
																items:{
																	title:'模板列表',
																	xtype:'grid',
																	id:'tpl-grid',
																	autoScroll:true,
																	border:false,
																	columns:[
																		new Ext.grid.RowNumberer(),
																		{header:'类型',dataIndex:'type',width:60,renderer:function(value){
																			Ext.each(tplType,function(){
																				if(value==this[0]){
																					value=this[1];
																					return false;
																				}
																			});
																			return value;
																		}},
																		{header:'名称',dataIndex:'name',width:250},
																		{header:'用量',dataIndex:'perValumon',width:40},
																		{header:'单位',dataIndex:'unit',width:40},
																		{header:'用法',dataIndex:'useFn',width:120},
																		{header:'疗程',dataIndex:'course',width:40,renderer:function(value,metadata,record,rowIndex,colIndex,store){
																			if(value>0){
																				Ext.each(tplCourseUnit,function(){
																					if((record.data.courseUnit+'')==this[0]){
																						value+=this[1];
																						return false;
																					}
																				});
																				return value;
																			}
																			return '';
																		}},
																		{header:'备注',dataIndex:'memo',width:200}
																	],
																	store:new Ext.data.Store({
																		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do'}),
																		reader:new Ext.data.JsonReader({
																			root: ''
																		},[
																			{name:'id'},
																			{name:'groupId'},
																			{name:'type'},
																			{name:'name'},
																			{name:'perValumon'},
																			{name:'unit'},
																			{name:'useFn'},
																			{name:'course'},
																			{name:'courseUnit'},
																			{name:'memo'}
																		])
																	}),
																	sm:new Ext.grid.CheckboxSelectionModel(),
																	listeners:{
																		render:function(){
																			this.getStore().baseParams={method:'tpl_findByGroupId',id:_ss[0].data.id};
																			this.getStore().load();
																		}
																	}
																},
																buttons:[
																	{
																		text:'新增',handler:function(){
																			TplEditWin(null);
																		}
																	},{
																		text:'编辑',handler:function(){
																			var _ss=this.ownerCt.items.get(0).getSelectionModel().getSelections();
																			if(_ss==0||_ss.length>1){
																				alert('请选择一条记录编辑。');
																				return;
																			}
																			TplEditWin(_ss[0]);
																		}
																	},{
																		text:'删除',handler:function(){
																			var _ss=this.ownerCt.items.get(0).getSelectionModel().getSelections();
																			if(_ss==0||_ss.length>1){
																				alert('请选择一条记录编辑。');
																				return;
																			}
																			if(!confirm('确定要删除选中记录？'))return;
																			Ext.Ajax.request({
																				url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
																				params:{
																					method:'tpl_delete',
																					id:_ss[0].data.id
																				},
																				scope:this,
																				success:function(_response){
																					if(Ext.decode(_response.responseText).success){
																						alert('删除成功。');
																						this.ownerCt.items.get(0).getStore().reload();
																					}else{
																						alert('删除失败。');
																					}
																				}
																			});
																		}
																	},{
																		text:'关闭',handler:function(){
																			this.ownerCt.close();
																		}
																	}
																]
															}).show();
														}
													},'-'
												]
											});
											var panel=Ext.getCmp('center-panel').items.get(2);
											panel.removeAll();
											panel.add(grid);
											Ext.getCmp('center-panel').setActiveTab(panel);
											panel.doLayout();
										}
									});
								}
							}
						]
					},'-'
				]
			},{
				region:'center',
				xtype:'tabpanel',
				id:'center-panel',
				activeTab:0,
				defaults:{
					layout:'fit',
					border:false,
					items:{
						border:false
					}
				},
				items:[
					{
						title:'数据表字段',
						items:{
							xtype:'grid',
							id:'grid',
							title:'已定义字段列表',
							border:false,
							autoScroll:true,
							columns:[
								new Ext.grid.RowNumberer(),
								sm,
								{header:'类型',dataIndex:'fldType',renderer:function(value){
									return fldType[value];
								}},
								{header:'名称',dataIndex:'fldName'},
								{header:'长度',dataIndex:'strLen',renderer:function(value){
									if(value>0){
										return value;
									}else{
										return '';
									}
								}},
								{header:'时间格式',dataIndex:'dateFormat'},
								{header:'备注',dataIndex:'comment'},
								{header:'编辑',dataIndex:'allowEdit',renderer:function(value,meta,record,rowIndex){
									if(value==1){
										return '<a href="#" onclick="FldEditorWin('+rowIndex+')">编辑</a>';
									}else{
										return '';
									}
								}}
							],
							sm:sm,
							store:new Ext.data.Store({
								proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do?method=td_findAllFlds'}),
								reader:new Ext.data.JsonReader({root:''},[
									{name:'id'},
									{name:'fldType'},
									{name:'fldName'},
									{name:'fldCode'},
									{name:'strLen'},
									{name:'dateFormat'},
									{name:'comment'},
									{name:'allowEdit'}
								])
							}),
							viewConfig:{
								forceFit:true
							},
							listeners:{
								render:function(){
									this.getStore().load();
								}
							},
							tbar:[
								'-',{
									text:'刷新表格',handler:function(){
										Ext.getCmp('grid').getStore().reload();
									}
								},'-',{
									text:'新增字段',handler:function(){
										FldEditorWin(-1);
									}
								},'-',{
									text:'删除字段',handler:function(){
										var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
										if(_ss.length==0){
											alert('至少选择一条记录。');
											return;
										}
										var array=[];
										for(var i=0,len=_ss.length;i<len;i++){
											if(_ss[i].data.allowEdit==0){
												alert('不能删除默认的字段。');
												return;
											}else{
												array.push(_ss[i].data.id);
											}
										}
										if(!confirm('确定要删除选中字段？'))return;
										Ext.Ajax.request({
											url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
											params:{
												method:'td_delete',
												id:array.join(',')
											},
											success:function(_response){
												if(Ext.decode(_response.responseText).success){
													alert('删除成功。');
													Ext.getCmp('grid').getStore().reload();
												}else{
													alert('删除失败。');
												}
											}
										});
									}
								},'-'
							]
						}
					},{
						title:'数据表结构',
						items: new Ext.grid.EditorGridPanel({
							id: 'grid2',
							title: 'XXX',
							border: false,
							autoScroll: true,
							columns:[
								new Ext.grid.RowNumberer(),
								sm2,
								{header:'名称',dataIndex:'fldName'},
								{header:'类型',dataIndex:'fldType',renderer:function(value){
									return fldType[value];
								}},
								{header:'排序',dataIndex:'orderNo',editor:new Ext.form.NumberField()},
								{header:'备注',dataIndex:'comment',editor:new Ext.form.TextArea()}
							],
							sm:sm2,
							store:new Ext.data.Store({
								proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do?method=treeFld_findAll'}),
								reader:new Ext.data.JsonReader({root:''},[
									{name:'id'},
									{name:'fldName'},
									{name:'fldType'},
									{name:'orderNo'},
									{name:'comment'},
									{name:'allowEdit'},
									{name:'fldId'}
								])
							}),
							viewConfig:{
								forceFit:true
							},
							listeners:{
								render:function(){
									//this.getStore().load();
								}
							},
							tbar:[
								'-',{
									text:'刷新表格',handler:function(){
										if(!_consNode){
											alert('请先选择一个分组进行 数据表->结构 操作。');
											return;
										}
										Ext.getCmp('grid2').getStore().reload();
									}
								},'-',{
									text:'新增字段',handler:function(){
										if(!_consNode){
											alert('请先选择一个分组进行 数据表->结构 操作。');
											return;
										}
										new Ext.Window({
											closable:false,
											width:400,
											height:400,
											layout:'fit',
											buttonAlign:'center',
											frame:true,
											items:{
												xtype:'grid',
												autoScroll:true,
												title:'选择字段',
												columns:[
													new Ext.grid.RowNumberer(),
													sm3,
													{header:'类型',dataIndex:'fldType',renderer:function(value){
														return fldType[value];
													}},
													{header:'名称',dataIndex:'fldName'},
													{header:'长度',dataIndex:'strLen',renderer:function(value){
														if(value>0){
															return value;
														}else{
															return '';
														}
													}},
													{header:'时间格式',dataIndex:'dateFormat'}
												],
												sm:sm3,
												viewConfig:{
													forceFit:true	
												},
												ds:Ext.getCmp('grid').getStore(),
												listeners:{
													render:function(){
														this.getStore().load();
													}
												}
											},
											buttons:[
												{
													text:'确定',handler:function(){
														var _ss=this.ownerCt.items.get(0).getSelectionModel().getSelections();
														if(_ss.length==0){
															alert('未选择列。');
															return;
														}
														var store=Ext.getCmp('grid2').getStore();
														var array=[];
														Ext.each(_ss,function(_item,_i){
															array.push(new Ext.data.Record({
																id:-1,
																fldName:_item.data.fldName,
																fldType:_item.data.fldType,
																orderNo:_i+store.getCount()+1,
																comment:'',
																allowEdit:'1',
																fldId:_item.data.id
															}));
														});
														store.add(array);
														this.ownerCt.close();
													}
												},{
													text:'关闭',handler:function(){
														this.ownerCt.close();
													}
												}
											]
										}).show();
									}
								},'-',{
									text:'删除字段',handler:function(){
										if(!_consNode){
											alert('请先选择一个分组进行 数据表->结构 操作。');
											return;
										}
										var _ss=Ext.getCmp('grid2').getSelectionModel().getSelections();
										if(_ss.length==0){
											alert('至少选择一行。');
											return;
										}
										if(!confirm('确定要删除选中字段？'))return;
										var id=[];
										Ext.each(_ss,function(){
											id.push(this.data.id);
										});
										Ext.Ajax.request({
											url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
											params:{method:'treeFld_delete',id:id.join(',')},
											success:function(_response){
												if(Ext.decode(_response.responseText).success){
													alert('删除成功。');
													var store=Ext.getCmp('grid2').getStore();
													Ext.each(_ss,function(){
														store.remove(this);
													});
												}else{
													alert('删除失败。');
												}
											}
										});
									}
								},'-',{
									text:'保存',handler:function(){
										if(!_consNode){
											alert('请先选择一个分组进行 数据表->结构 操作。');
											return;
										}
										var store=Ext.getCmp('grid2').getStore();
										if(store.getCount()==0){
											alert('表格中无数据。');
											return;
										}
										var json=[];
										store.each(function(){
											json.push(Ext.apply(this.data,{treeId:_consNode.id.split('>')[0]}));
										});
										Ext.Ajax.request({
											url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
											params:{method:'treeFld_saveOrUpdate',data:Ext.encode(json)},
											success:function(_response){
												if(Ext.decode(_response.responseText).success){
													alert('保存成功。');
													store.reload();
												}else{
													alert('保存失败。');
												}
											}
										});
									}
								},'-'
							]
						})
					},{
						title:'数据表内容'
					}
				]
			}
		]
	});
});
function FldEditorWin(_rowIndex){
	new Ext.Window({
		title:'字段定义',
		frame:true,
		closable:false,
		width:400,
		autoHeight:true,
		buttonAlign:'center',
		layout:'fit',
		items:{
			xtype:'form',
			frame:true,
			autoHeight:true,
			labelAlign:'right',
			labelWidth:60,
			defaults:{
				anchor:'90%'
			},
			items:[
				{
					xtype:'combo',
					fieldLabel:'类型',
					hiddenName:'fldType',
					mode:'local',
					readOnly:true,
					triggerAction:'all',
					displayField:'text',
					allowBlank:false,
					valueField:'value',
					value:'varchar',
					listeners:{
						render:function(){
							if(!this.store){
								var data=[];
								for(var key in fldType){
									data.push([key,fldType[key]]);
								};
								this.store=new Ext.data.SimpleStore({
									data:data,
									fields:['value','text']
								});
							}
						}
					}
				},{
					xtype:'textfield',
					fieldLabel:'名称',
					name:'fldName',
					allowBlank:false
				},{
					xtype:'numberfield',
					fieldLabel:'长度',
					name:'strLen',
					emptyText:'当类型为字符串时填写此项'
				},{
					xtype:'combo',
					fieldLabel:'时间格式',
					hiddenName:'dateFormat',
					emptyText:'当类型为时间时填写此项',
					mode:'local',
					readOnly:true,
					triggerAction:'all',
					displayField:'text',
					valueField:'text',
					listeners:{
						render:function(){
							if(!this.store){
								var data=[['']];
								Ext.each(dateFormat,function(){
									data.push([this]);
								});
								this.store=new Ext.data.SimpleStore({
									data:data,
									fields:['text']
								});
							}
						}
					}
				},{
					xtype:'textarea',
					fieldLabel:'备注',
					name:'comment',
					height:60
				},{
					xtype:'hidden',
					name:'fldCode',
					value:''
				},{
					xtype:'hidden',
					name:'allowEdit',
					value:'1'
				},{
					xtype:'hidden',
					name:'id',
					value:'-1',
					listeners:{
						render:function(){
							if(_rowIndex&&_rowIndex>0)
								this.ownerCt.form.loadRecord(Ext.getCmp('grid').getStore().getAt(_rowIndex));
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
					var json=_form.getValues();
					if(json.fldType=='varchar'){
						if(!(json.strLen>0)){
							alert('未定义字符串长度或长度不符合要求。');
							return;
						}
					}else if(json.fldType=='datetime'){
						if(json.dateFormat.length==0){
							alert('未定义时间格式。');
							return;
						}
					}
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
						params:{
							method:'td_saveOrUpdate',
							data:Ext.encode(json)
						},
						scope:this,
						success:function(_response){
							if(Ext.decode(_response.responseText).success){
								alert('保存成功。');
								Ext.getCmp('grid').getStore().reload();
								this.ownerCt.close();
							}else{
								alert('保存失败。');
							}
						}
					});
				}
			},{
				text:'关闭',handler:function(){
					this.ownerCt.close();
				}
			}
		]
	}).show();
}
function TreeNodeEditWin(_node,_pid){
	new Ext.Window({
		title:'节点编辑窗口',
		frame:true,
		closable:false,
		width:400,
		autoHeight:true,
		buttonAlign:'center',
		layout:'fit',
		items:{
			xtype:'form',
			frame:true,
			autoHeight:true,
			labelAlign:'right',
			labelWidth:60,
			bodyStyle:'padding:5px',
			defaults:{
				anchor:'90%'
			},
			items:[
				{
					xtype:'textfield',
					fieldLabel:'组名',
					allowBlank:false,
					name:'name'
				},{
					xtype:'numberfield',
					fieldLabel:'序号',
					allowBlank:false,
					name:'orderNo'
				},{
					xtype:'hidden',
					name:'pid',
					value:_pid?_pid:'-1'
				},{
					xtype:'hidden',
					name:'id',
					value:'-1',
					listeners:{
						render:function(){
							if(_node){
								var id=_node.id.split('>');
								this.ownerCt.form.loadRecord(new Ext.data.Record({
									id:id[0],
									pid:id[2],
									orderNo:id[1],
									name:_node.text
								}));
							}
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
						alert('请完成表单再提交。');
						return;
					}
					var json=_form.getValues();
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
						params:Ext.apply(json,{method:'tree_saveOrUpdate'}),
						scope:this,
						success:function(_response){
							if(Ext.decode(_response.responseText).success){
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
				text:'关闭',handler:function(){
					this.ownerCt.close();
				}
			}
		]
	}).show();
}
function tableRecordEditWin(_record,_fldCfg){
	var fld=[];
	Ext.each(_fldCfg,function(){
		var defaultCfg={
			xtype:'textfield',
			name:this.fldCode,
			fieldLabel:this.fldName,
			anchor:'90%'
		};
		if(this.fldType=='int'||this.fldType=='float'){
			Ext.apply(defaultCfg,{xtype:'numberfield'});
		}else if(this.fldType=='ntext'){
			Ext.apply(defaultCfg,{xtype:'htmleditor',value:'',height:200})
		}
		fld.push(defaultCfg);
	});
	fld.push({
		xtype:'hidden',
		name:'treeId',
		value:_contNode.id.split('>')[0]
	});
	fld.push({
		xtype:'hidden',
		name:'id',
		value:'-1',
		listeners:{
			render:function(){
				if(_record)
					this.ownerCt.form.loadRecord(_record);
			}
		}
	});
	new Ext.Window({
		title:'分组详细编辑窗口',
		frame:true,
		width:Ext.getBody().getSize().width*0.6,
		height:Ext.getBody().getSize().height*0.7,
		closable:false,
		buttonAlign:'center',
		layout:'fit',
		items:{
			xtype:'form',
			frame:true,
			autoScroll:true,
			labelAlign:'right',
			labelWidth:80,
			bodyStyle:'padding:5px 0;',
			defaults:{
				anchor:'95%'
			},
			items:fld
		},
		buttons:[
			{
				text:'保存',handler:function(){
					var _form=this.ownerCt.items.get(0).form;
					if(!_form.isValid())return;
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
						params:{
							method:'content_saveOrUpdate',
							data:Ext.encode(_form.getValues()),
							fldCfg:Ext.encode(_fldCfg)
						},
						scope:this,
						success:function(_response){
							if(Ext.decode(_response.responseText).success){
								alert('保存成功。');
								Ext.getCmp('grid3').getStore().reload();
								this.ownerCt.close();
							}else{
								alert('保存失败。');
							}
						}
					});
				}
			},{
				text:'关闭',handler:function(){
					this.ownerCt.close();
				}
			}
		]
	}).show();
}
function ShowContentDetail(rowIndex,colIndex){
	var _grid=Ext.getCmp('grid3');
	var _dataIndex=_grid.getColumnModel().getDataIndex(colIndex);
	var _content=_grid.getStore().getAt(rowIndex).data[_dataIndex];
	new Ext.Window({
		title:'文本内容查看窗口',
		frame:true,
		closeAction:'close',
		width:Ext.getBody().getSize().width*0.6,
		height:Ext.getBody().getSize().height*0.6,
		autoScroll:true,
		bodyStyle:'padding:5px;background:#FFF',
		html:_content
	}).show();
}
function TplEditWin(_record){
	new Ext.Window({
		title:'模板编辑窗口',
		modal:true,
		frame:true,
		width:Ext.getBody().getSize().width*0.6,
		height:Ext.getBody().getSize().height*0.7,
		closable:false,
		buttonAlign:'center',
		layout:'fit',
		items:{
			xtype:'form',
			frame:true,
			autoScroll:true,
			labelAlign:'right',
			labelWidth:80,
			bodyStyle:'padding:5px 0;',
			defaults:{
				anchor:'95%',
				xtype:'textfield'
			},
			items:[
				{
					xtype:'combo',
					fieldLabel:'医嘱类型',
					mode:'local',
					hiddenName:'type',
					displayField:'text',
					valueField:'value',
					triggerAction:'all',
					value:'0',
					store:new Ext.data.SimpleStore({
						data:tplType,
						fields:['value','text']
					})
				},{
					fieldLabel:'医嘱名称',
					name:'name'
				},{
					xtype:'panel',
					layout:'column',
					defaults:{
						layout:'form'
					},
					items:[
						{
							columnWidth:.5,
							items:{
								xtype:'numberfield',
								fieldLabel:'每次用量',
								name:'perValumon'
							}
						},{
							columnWidth:.5,
							items:{
								xtype:'textfield',
								fieldLabel:'单位',
								name:'unit'
							}
						}
					]
				},{
					fieldLabel:'用法',
					name:'useFn'
				},{
					xtype:'panel',
					layout:'column',
					defaults:{
						layout:'form'
					},
					items:[
						{
							columnWidth:.5,
							items:{
								xtype:'numberfield',
								fieldLabel:'疗程',
								name:'course'
							}
						},{
							columnWidth:.5,
							items:{
								xtype:'combo',
								mode:'local',
								displayField:'text',
								valueField:'value',
								triggerAction:'all',
								store:new Ext.data.SimpleStore({
									data:tplCourseUnit,
									fields:['value','text']
								}),
								fieldLabel:'单位',
								hiddenName:'courseUnit'
							}
						}
					]
				},{
					xtype:'textarea',
					fieldLabel:'备注',
					name:'memo'
				},{
					xtype:'hidden',
					name:'groupId',
					value:'-1',
					listeners:{
						render:function(){
							var _ss=Ext.getCmp('grid3').getSelectionModel().getSelections();
							this.setValue(_ss[0].data.id);
						}
					}
				},{
					xtype:'hidden',
					name:'id',
					value:'-1',
					listeners:{
						render:function(){
							if(_record){
								this.ownerCt.form.loadRecord(_record);
							}
						}
					}
				}
			]
		},
		buttons:[
			{
				text:'保存',handler:function(){
					var _value=this.ownerCt.items.get(0).form.getValues();
					if(_value.name.trim().length==0){
						alert('医嘱名称不能为空。');
						return;
					}
					if(_value.type==2){
						if((_value.course+'').length==0){
							alert('疗程不能为空。');
							return;
						}
						if((_value.courseUnit+'').length==0){
							alert('疗程单位不能为空。');
							return;
						}
					}
					if(_value.type==0){
						if((_value.perValumon+'').length==0){
							alert('每次用量不能为空。');
							return;
						}
						if(_value.unit.length==0){
							alert('用量单位不能为空。');
							return;
						}
						if(_value.useFn.length==0){
							alert('用法不能为空。');
							return;
						}
						if((_value.course+'').length==0){
							alert('疗程不能为空。');
							return;
						}
						if((_value.courseUnit+'').length==0){
							alert('疗程单位不能为空。');
							return;
						}
					}
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/admin/MemberGroupMngAction.do',
						params:{
							method:'tpl_saveOrUpdate',
							data:Ext.encode(_value)
						},
						scope:this,
						success:function(_response){
							if(Ext.decode(_response.responseText).success){
								alert('保存成功。');
								Ext.getCmp('tpl-grid').getStore().reload();
								this.ownerCt.close();
							}else{
								alert('保存失败。');
							}
						}
					});
				}
			},{
				text:'关闭',handler:function(){
					this.ownerCt.close();
				}
			}
		]
	}).show();
}
