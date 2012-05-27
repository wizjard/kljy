function showSubGroupWin(_record){
	new Ext.Window({
		frame:true,
		closable:false,
		closeAction:'close',
		width:500,
		height:Ext.getBody().getSize().height*0.6,
		layout:'fit',
		buttonAlign:'center',
		buttons:[
			{
				text:'保存',handler:function(){
					var store=Ext.getCmp('group-grid').getStore();
					var _array=[];
					store.each(function(_item){
						_array.push(_item.data);
					});
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/admin/ResearchAdminAction.do',
						params:{
							method:'group_rel_save',
							id:_record.id,
							array:Ext.util.JSON.encode(_array)
						},
						success:function(_response){
							if(Ext.util.JSON.decode(_response.responseText).success){
								alert('保存成功。');
								store.reload();
							}else{
								alert('保存失败。');
							}
						}
					});
				}
			},{
				text:'关闭',handler:function(){this.ownerCt.close();}
			}
		],
		items:createSubGroupGrid(_record.id)
	}).show();
}
function createSubGroupGrid(_id){
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'编号',dataIndex:'code',editor:new Ext.form.TextField({allowBlank:false})},
		{header:'组名',dataIndex:'name',editor:new Ext.form.TextField({allowBlank:false})},
		{header:'备注',dataIndex:'s_desc',editor:new Ext.form.TextArea({height:40})}
	]);
	var ds=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/ResearchAdminAction.do?method=group_rel_find&id='+_id}),
		reader:new Ext.data.JsonReader({root:''},[
			{name:'code'},
			{name:'name'},
			{name:'s_desc'}
		])
	});
	ds.load();
	var _grid=new Ext.grid.EditorGridPanel({
		id:'group-grid',
		title:'项目分组列表',
		tbar:[
			'-',{
				text:'新增',handler:function(){
					Ext.getCmp('group-grid').getStore().add(new Ext.data.Record({
						code:'',
						name:'',
						s_desc:''
					}));
				}
			},'-',{
				text:'删除',handler:function(){
					var _ss=Ext.getCmp('group-grid').getSelectionModel().getSelections();
					if(_ss.length==0){
						alert('至少选择一条记录。');
						return;
					};
					if(!confirm('确定要删除？'))	return;
					var store=Ext.getCmp('group-grid').getStore();
					Ext.each(_ss,function(_item){
						store.remove(_item);
					});
				}
			}
		],
		border:false,
		cm:cm,
		sm:sm,
		ds:ds,
		autoScroll: true,
		viewConfig:{
			forceFit:true
		}
	});
	return _grid;
}