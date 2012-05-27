Ext.onReady(function(){
	var _id=App.util.getHtmlParameters('id');
	Ext.QuickTips.init();
	new Ext.Viewport({
		layout:'border',
		defaults:{
			border:false,
			autoScroll:true
		},
		items:[createIndexGrid(_id),createItemGrid()]
	});
});
function createIndexGrid(_id){
    var ds = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/DictAction.do?method=fldcode_queryAll&id='+_id}),
		reader:new Ext.data.JsonReader({root:''},
									   [
										{name:'code'},
										{name:'name'},
										{name:'comment'},
										{name:'moduleId'},
										{name:'id'}
									   ])});
	ds.load();
    var grid = new Ext.grid.GridPanel({
    	id:'index-grid',
    	region:'north',
    	border:false,
    	ds: ds,
    	columns:[
    		new Ext.grid.RowNumberer(),//自动行号
    		{header:'代码',dataIndex:'code',renderer:function(value,cellmeta,record,rowIndex,columnIndex,store){
				return '<span onclick="FldCodeClick('+record.data.id+')" style="cursor:pointer">'+value+'</span>';
			}},
    		{header:'名称',dataIndex:'name'},
    		{header:'说明',dataIndex:'comment'}
    	],
    	viewConfig:{
        	forceFit: true,
        	autoFill:true
    	},
    	tbar:[
    		{
    			text:'增加',handler:function(){
    				showIndexEditorWin({
    					title:'新增字典Index',
    					id:-1,
    					moduleId:_id,
    					name:'',
    					code:'',
    					comment:''
    				},this.btnEl);
    			}
    		},{
    			xtype:'tbseparator'
    		},{
    			text:'删除',handler:function(){
    				var records=Ext.getCmp('index-grid').getSelectionModel().getSelections();
    				if(records.lenght==0){
    					Ext.Msg.alert('提示','未选择列。',Ext.emptyFn);
    					return;
    				}
    				Ext.Msg.confirm('警告','code删除后其字典item也将删除，确定执行操作？',function(_btn){
    					if(_btn!='yes')	return;
    					var _data=[];
	    				Ext.each(records,function(_item,_index,_length){
	    					_data.push(_item.data);
	    				});
	    				Ext.Ajax.request({
	    					url:App.App_Info.BasePath+'/admin/DictAction.do?method=fldcode_delete',
							params:{data:Ext.util.JSON.encode(_data)},
							success:function(_response,_options){
								var _res=Ext.util.JSON.decode(_response.responseText);
								if(_res.success){
									Ext.getCmp('index-grid').getStore().reload();
									FldCodeClick(-1);
								}else{
									Ext.Msg.alert('提示','删除失败。',Ext.emptyFn);
								}
							}
	    				});
    				});
    			}
    		}
    	]
    });
    grid.on('rowdblclick',function(_grid,_index,_e){
    	var _rec=_grid.getStore().getAt(_index).data;
    	_rec.title='编辑字典Index-'+_rec.name;
    	showIndexEditorWin(_rec,_grid.body);
    });
    return grid;
}
function FldCodeClick(_id){
	var _ds=Ext.getCmp('item-grid').getStore();
	_ds.baseParams={id:_id};
    _ds.load();
}
function createItemGrid(){
    var ds = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/DictAction.do?method=item_queryAll'}),
		reader:new Ext.data.JsonReader({root:''},
									   [
										{name:'value'},
										{name:'text'},
										{name:'comment'},
										{name:'orderNo'},
										{name:'fldCodeId'},
										{name:'id'}
									   ])});
	ds.baseParams={id:'-1'};
    var grid = new Ext.grid.GridPanel({
    	id:'item-grid',
    	region:'center',
    	border:false,
    	ds: ds,
    	columns:[
    		new Ext.grid.RowNumberer(),//自动行号
    		{header:'实际值',dataIndex:'value'},
    		{header:'显示值',dataIndex:'text'},
    		{header:'说明',dataIndex:'comment'},
    		{header:'排列顺序',dataIndex:'orderNo'}
    	],
    	viewConfig:{
        	forceFit: true,
        	autoFill:true
    	},
    	tbar:[
    		{
    			text:'增加',handler:function(){
    				var _id=Ext.getCmp('item-grid').getStore().baseParams.id;
    				if(_id==-1){
    					Ext.Msg.alert('提示','未选择任何字段。',Ext.emptyFn);
    					return;
    				}else{
    					showItemEditorWin_add(_id,this.btnEl);
    				}
    			}
    		},{
    			xtype:'tbseparator'
    		},{
    			text:'删除',handler:function(){
    				var records=Ext.getCmp('item-grid').getSelectionModel().getSelections();
    				if(records.lenght==0){
    					Ext.Msg.alert('提示','未选择列。',Ext.emptyFn);
    					return;
    				}
    				Ext.Msg.confirm('警告','确定要删除选中字典项目？',function(_btn){
    					if(_btn!='yes')	return;
    					var _data=[];
	    				Ext.each(records,function(_item,_index,_length){
	    					_data.push(_item.data);
	    				});
	    				Ext.Ajax.request({
	    					url:App.App_Info.BasePath+'/admin/DictAction.do?method=item_delete',
							params:{data:Ext.util.JSON.encode(_data)},
							success:function(_response,_options){
								var _res=Ext.util.JSON.decode(_response.responseText);
								if(_res.success){
									Ext.getCmp('item-grid').getStore().reload();
								}else{
									Ext.Msg.alert('提示','删除失败。',Ext.emptyFn);
								}
							}
	    				});
    				});
    			}
    		}
    	],
    	listeners:{
			render:function(){
				this.ownerCt.items.get(0).setHeight(Ext.getBody().getSize().height/2);
			},
			rowdblclick:function(_grid,_index,_e){
				showItemEditorWin_edit(_grid.getStore().getAt(_index).data,_grid.body);
			}
		}
    });
    return grid;
}
function showItemEditorWin_edit(_record,_an){
	new Ext.Window({
		title:'字典单项Item编辑',
		frame:true,
		closable:false,
		animCollapse:true,
		animateTarget:_an,
		closeAction:'close',
		width:400,
		autoHeight:true,
		layout:'fit',
		buttonAlign:'center',
		buttons:[
			{
				text:'保存',handler:function(){
					var _win=this.ownerCt;
					var _form=_win.items.get(0).form;
					if(_form.isValid()){
						Ext.Ajax.request({
							url:App.App_Info.BasePath+'/admin/DictAction.do?method=item_update',
							params:{data:Ext.util.JSON.encode(_form.getValues())},
							success:function(_response,_options){
								var _res=Ext.util.JSON.decode(_response.responseText);
								if(_res.success){
									Ext.getCmp('item-grid').getStore().reload();
									_win.close();
								}else{
									Ext.Msg.alert('提示','保存失败。',Ext.emptyFn);
								}
							}
						});
					}else{
						Ext.Msg.alert('提示','表单未通过验证，请检查填写。',Ext.emptyFn);
					}
				}
			},{
				text:'重置',handler:function(){
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
			labelWidth:50,
			autoHeight:true,
			labelAlign:'right',
			items:[
				{
					xtype:'textfield',
					name:'text',
					fieldLabel:'显示值',
					anchor:'95%',
					allowBlank:false,
					value:_record.text
				},{
					xtype:'textfield',
					name:'value',
					fieldLabel:'实际值',
					allowBlank:false,
					anchor:'95%',
					value:_record.value
				},{
					xtype:'numberfield',
					name:'orderNo',
					fieldLabel:'序号',
					allowBlank:false,
					anchor:'95%',
					value:_record.orderNo
				},{
					xtype:'textarea',
					name:'comment',
					fieldLabel:'备注',
					anchor:'95%',
					height:60,
					value:_record.comment
				},{
					xtype:'hidden',
					name:'id',
					value:_record.id
				},{
					xtype:'hidden',
					name:'fldCodeId',
					value:_record.fldCodeId
				}
			]
		}
	}).show();
}
function showItemEditorWin_add(_id,_an){
	new Ext.Window({
		title:'字典候选列表编辑窗口',
		frame:true,
		closable:false,
		animCollapse:true,
		animateTarget:_an,
		closeAction:'close',
		width:400,
		autoHeight:true,
		layout:'fit',
		buttonAlign:'center',
		buttons:[
			{
				text:'保存',handler:function(){
					var _win=this.ownerCt;
					var _form=_win.items.get(0).form;
					var _text=_form.findField('text').getValue().trim();
					var _value=_form.findField('value').getValue().trim();
					if(_text.length==0&&_value.length==0){
						Ext.Msg.alert('提示','无内容需要保存。',Ext.emptyFn);
						return;
					}
					if(_text.length==0){
						_text=_value;
					}else if(_value.length==0){
						_value=_text;
					}
					var _ts=_text.split('\n');
					var _vs=_value.split('\n');
					if(_ts.length!=_vs.length){
						Ext.Msg.alert('提示','显示与实际值长度不符，请检查内容。',Ext.emptyFn);
						return;
					}
					var _data=[];
					var _fldCodeId=Ext.getCmp('item-grid').getStore().baseParams.id;
					if(_ts.length==1){
						_data.push({
							id:-1,
							fldCodeId:_fldCodeId,
							text:_ts[0].trim(),
							value:_vs[0].trim(),
							comment:_form.findField('comment').getValue().trim(),
							orderNo:_form.findField('orderNo').getValue()
						});
					}else{
						Ext.each(_ts,function(_item,_index,_length){
							_data.push({
								id:-1,
								fldCodeId:_fldCodeId,
								text:_item.trim(),
								value:_vs[_index].trim(),
								comment:'',
								orderNo:_index+1
							});
						});
					}
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/admin/DictAction.do?method=item_add',
						params:{data:Ext.util.JSON.encode(_data)},
						success:function(_response,_options){
							var _res=Ext.util.JSON.decode(_response.responseText);
							if(_res.success){
								Ext.getCmp('item-grid').getStore().reload();
								_win.close();
							}else{
								Ext.Msg.alert('提示','数据保存失败。',Ext.emptyFn);
							}
						}
					});
				}
			},{
				text:'重置',handler:function(){
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
			labelWidth:50,
			autoHeight:true,
			labelAlign:'right',
			items:[
				{
					xtype:'textarea',
					name:'text',
					fieldLabel:'显示值',
					anchor:'95%',
					height:100
				},{
					xtype:'textarea',
					name:'value',
					fieldLabel:'实际值',
					anchor:'95%',
					height:100
				},{
					xtype:'numberfield',
					name:'orderNo',
					fieldLabel:'序号',
					anchor:'95%'
				},{
					xtype:'textarea',
					name:'comment',
					fieldLabel:'备注',
					anchor:'95%',
					height:60
				},{
					xtype:'textarea',
					name:'show',
					fieldLabel:'说明',
					anchor:'95%',
					readOnly:true,
					height:90,
					value:'1、此操作可单个增加，也可批量增加。\n'+
						  '2、批量增加时项目之间以换行符隔开。\n'+
						  '3、若显示与实际值有任何一方为空，则赋值为另一方的值。\n'+
						  '4、批量增加时备注和序号将被忽略。'
				}
			]
		}
	}).show();
}
function showIndexEditorWin(_rec,_an){
	new Ext.Window({
		title:_rec.title,
		frame:true,
		closable:false,
		animCollapse:true,
		animateTarget:_an,
		closeAction:'close',
		width:400,
		autoHeight:true,
		layout:'fit',
		buttonAlign:'center',
		buttons:[
			{
				text:'保存',handler:function(){
					var _win=this.ownerCt;
					var _form=_win.items.get(0).form;
					if(_form.isValid()){
						var _json=Ext.util.JSON.encode(_form.getValues());
						Ext.Ajax.request({
							url:App.App_Info.BasePath+'/admin/DictAction.do?method=fldcode_saveOrUpdate',
							params:{data:_json},
							success:function(_response,_options){
								var _res=Ext.util.JSON.decode(_response.responseText);
								if(_res.success){
									Ext.Msg.alert('成功','保存成功。',function(){
										Ext.getCmp('index-grid').getStore().reload();
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
			labelWidth:50,
			autoHeight:true,
			labelAlign:'right',
			items:[
				{
					xtype:'textfield',
					name:'name',
					fieldLabel:'名称',
					allowBlank:false,
					anchor:'95%',
					value:_rec.name
				},{
					xtype:'textfield',
					name:'code',
					fieldLabel:'代码',
					allowBlank:false,
					anchor:'95%',
					value:_rec.code
				},{
					xtype:'textarea',
					name:'comment',
					fieldLabel:'备注',
					anchor:'95%',
					height:60,
					value:_rec.comment
				},{
					xtype:'hidden',
					name:'id',
					value:_rec.id
				},{
					xtype:'hidden',
					name:'moduleId',
					value:_rec.moduleId
				}
			]
		}
	}).show();
}