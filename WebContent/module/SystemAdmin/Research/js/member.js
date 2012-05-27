Ext.onReady(function(){
	Ext.QuickTips.init();
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'姓名',dataIndex:'name'},
		{header:'性别',dataIndex:'gender'},
		{header:'出生年月',dataIndex:'birthday'},
		{header:'职称',dataIndex:'mem_title'},
		{header:'职务',dataIndex:'mem_positon'},
		{header:'专业',dataIndex:'professional'},
		{header:'工作时间(月/人)',dataIndex:'workVol'},
		{header:'所在单位',dataIndex:'belongUnit'},
		{header:'分担任务',dataIndex:'task'}
	]);
	var ds=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/ResearchAdminAction.do?method=mem_findAll'}),
		reader:new Ext.data.JsonReader({root:'root',totalProperty:'total'},[
			{name:'id'},
			{name:'name'},
			{name:'gender'},
			{name:'birthday'},
			{name:'mem_title'},
			{name:'mem_positon'},
			{name:'professional'},
			{name:'workVol'},
			{name:'task'},
			{name:'belongUnit'}
		])
	});
	ds.load({params:{start:0,limit:20}});
	new Ext.Viewport({
		layout:'fit',
		items:{
			xtype:'grid',
			id:'grid',
			title:'项目组成员列表',
			tbar:createToolbar(),
			bbar:new Ext.PagingToolbar({
				pageSize: 20,
				store: ds,
				displayInfo: true,
				displayMsg: '显示第<font color="red"> {0} </font>条' +
				'到<font color="red"> {1} </font>条记录，' +
				'一共<font color="red"> {2} </font>条',
				emptyMsg: "没有记录"
			}),
			border:false,
			cm:cm,
			sm:sm,
			ds:ds,
			autoScroll: true,
			viewConfig:{
				forceFit:true
			}
		}
	});
});
function createToolbar(){
	var array=[];
	array.push('-');
	array.push({
		text:'刷新',
		handler:function(){
			Ext.getCmp('grid').getStore().reload();
		}
	});
	array.push('-');
	array.push({
		text:'新增',
		handler:function(){
			createEditorWin('新增项目组成员');
		}
	});
	array.push('-');
	array.push({
		text:'编辑',
		handler:function(){
			var ss=SelectionRecord();
			if(!ss)return;
			createEditorWin('编辑项目组成员('+ss.data.name+')',ss);
		}
	});
	array.push('-');
	array.push({
		text:'删除',
		handler:function(){
			var ss=SelectionRecord();
			if(!ss)return;
			if(!confirm('确定要删除？'))	return;
			Ext.Ajax.request({
				url:App.App_Info.BasePath+'/admin/ResearchAdminAction.do',
				params:{method:'mem_delete',id:ss.data.id},
				success:function(_response){
					if(Ext.util.JSON.decode(_response.responseText).success){
						alert('删除成功。');
						Ext.getCmp('grid').getStore().reload();
					}else{
						alert('删除失败。');
					}
				}
			});
		}
	});
	return array;
}
Ext.namespace('Ext.ux');
Ext.ux.MyCombo=Ext.extend(Ext.form.ComboBox,{
	code:'',
	triggerAction:'all',
	valueField:'text',
	displayField:'text',
	onRender:function(ct,position){
		if(!this.store){
			var code=this.code;
			this.store=new Ext.data.SimpleStore({
				url:App.App_Info.BasePath+'/common/CommonAction.do?method=GetDictionarys&pcode=Topic-Info&fcode='+code,
				fields:['value','text']
			})
		}
		Ext.ux.MyCombo.superclass.onRender.call(this, ct, position);
	}
});
Ext.reg('MyCombo',Ext.ux.MyCombo);
Ext.ux.MyCheckbox=Ext.extend(Ext.form.CheckboxGroup,{
	code:'',
	defaults:{
		autoWidth:true
	},
	columns:4,
	setValue:function(_val){
		var _this=this;
		if(_val.length>0){
			Ext.each(_val.split(','),function(_value){
				_this.items.each(function(_item){
					if(_item.boxLabel==_value){
						_item.setValue(true);
						return false;
					}
				});
			});
		}
	},
	getValue:function(){
		var _val=[];
		this.items.each(function(_item){
			if(_item.checked){
				_val.push(_item.boxLabel);
			}
		});
		return _val.join(',');
	},
	onRender:function(ct,position){
		if(!this.items){
			var code=this.code;
			var items=[];
			Ext.Ajax.request({
				url:App.App_Info.BasePath+'/common/CommonAction.do?method=GetDictionarys&pcode=Topic-Info&fcode='+code,
				sync:true,
				success:function(_response){
					Ext.each(Ext.util.JSON.decode(_response.responseText),function(_item,_index){
						items.push({boxLabel:_item[1],inputValue:_item[1],name:code});
					});
				}
			});
			this.items=items;
		}
		Ext.ux.MyCheckbox.superclass.onRender.call(this, ct, position);
	}
});
Ext.reg('MyCheckbox',Ext.ux.MyCheckbox);
function createForm(){
	return {
		xtype:'form',
		frame:true,
		border:false,
		labelAlign:'right',
		labelWidth:100,
		autoScroll:true,
		layout:'column',
		defaults:{
			layout:'form',
			bodyStyle:'padding-top:5px',
			defaults:{
				xtype:'textfield',
				anchor:'90%'
			}
		},
		items:[
			{
				columnWidth:.5,
				items:[
					{
						fieldLabel:'姓名',
						allowBlank:false,
						name:'name'
					},{
						fieldLabel:'出生年月',
						name:'birthday'
					}
				]
			},{
				columnWidth:.5,
				items:[
					{
						xtype:'MyCombo',
						fieldLabel:'性别',
						readOnly:true,
						name:'gender',
						code:'gender'
					},{
						xtype:'MyCombo',
						fieldLabel:'职务',
						name:'mem_positon',
						code:'mem_positon'
					}
				]
			},{
				columnWidth:1,
				items:{
					xtype:'MyCheckbox',
					fieldLabel:'职务',
					name:'mem_title',
					code:'mem_title'
				}
			},{
				columnWidth:.5,
				items:[
					{
						xtype:'MyCombo',
						fieldLabel:'专业',
						readOnly:true,
						name:'professional',
						code:'professional'
					}
				]
			},{
				columnWidth:.5,
				items:[
					{
						xtype:'numberfield',
						fieldLabel:'工作时间(月/人)',
						name:'workVol'
					}
				]
			},{
				columnWidth:1,
				items:[
					{
						xtype:'MyCombo',
						fieldLabel:'所在单位',
						readOnly:true,
						anchor:'95%',
						name:'belongUnit',
						code:'belongUnit'
					},{
						xtype:'textarea',
						fieldLabel:'课题中职务及任务',
						name:'task',
						anchor:'95%',
						height:40
					},{
						xtype:'hidden',
						name:'id',
						value:'-1',
						listeners:{
							render:function(){
								var _form=this.ownerCt.ownerCt;
								if(_form.record){
									_form.form.loadRecord(_form.record);
								}
							}
						}
					}
				]
			}
		]
	};
}
function createEditorWin(_title,_record){
	new Ext.Window({
		title:_title,
		frame:true,
		closable:false,
		closeAction:'close',
		autoScroll:true,
		height:Ext.getBody().getSize().height*0.85,
		width:620,
		buttonAlign:'center',
		buttons:[
			{
				text:'保存',handler:function(){
					var _form=this.ownerCt.items.get(0).form;
					if(_form.isValid()){
						Ext.Ajax.request({
							url:App.App_Info.BasePath+'/admin/ResearchAdminAction.do',
							params:{method:'mem_saveOrUpdate',data:Ext.util.JSON.encode(Ext.apply(_form.getValues(),{mem_title:_form.findField('mem_title').getValue()}))},
							scope:this,
							success:function(_response,_options){
								if(Ext.util.JSON.decode(_response.responseText).success){
									alert('保存成功。');
									this.ownerCt.close();
									Ext.getCmp('grid').getStore().load({params:{start:0,limit:20}});
								}else{
									alert('保存失败，请检查填写后重新提交。');
								}
							}
						});
					}else{
						alert('请先完成表单再提交保存。');
					}
				}
			},{
				text:'关闭',handler:function(){
					this.ownerCt.close();
				}
			}
		],
		layout:'fit',
		items:Ext.apply(createForm(),{
			record:_record
		})
	}).show();
}
function SelectionRecord(){
	var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
	if(ss.length==0){
		alert('至少选择一条记录。');
		return null;
	}else{
		return ss[0];
	}
}