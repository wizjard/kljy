function createMemberGrid(_id){
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'姓名',dataIndex:'name'},
		{header:'性别',dataIndex:'gender'},
		//{header:'出生年月',dataIndex:'birthday'},
		//{header:'职称',dataIndex:'mem_title'},
		{header:'职务',dataIndex:'mem_positon'},
		{header:'专业',dataIndex:'professional'},
		//{header:'工作时间(月/人)',dataIndex:'workVol'},
		{header:'所在单位',dataIndex:'belongUnit'}
		//{header:'分担任务',dataIndex:'task'}
	]);
	var ds=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/ResearchAdminAction.do?method=mem_res_rel_find&id='+_id}),
		reader:new Ext.data.JsonReader({root:'root'},[
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
	ds.load();
	var _grid=new Ext.grid.GridPanel({
		id:'mem-grid',
		title:'项目组成员列表',
		tbar:[
			'-',{
				text:'新增',handler:function(){
					showMemberSelect();
				}
			},'-',{
				text:'删除',handler:function(){
					var ss=Ext.getCmp('mem-grid').getSelectionModel().getSelections();
					if(ss.length==0){
						alert('至少选择一条记录。');
						return;
					}
					if(!confirm('确定要删除？'))	return;
					var store=Ext.getCmp('mem-grid').getStore();
					Ext.each(ss,function(record){
						store.remove(record);
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
function showMemberSelect(){
	var sheight = screen.height*0.8;
	var swidth = screen.width*0.8;
	var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:yes;scroll:yes;resizable:yes;center:yes";
	var returnObj=window.showModalDialog('Member_select.html',null,winoption);
	if(returnObj){
		Ext.each(returnObj,function(_record){
			var _flag=true;
			Ext.getCmp('mem-grid').getStore().each(function(_r){
				if(_record.data.id==_r.data.id){
					_flag=false;
					return false;
				}
			});
			if(_flag){
				var record=new Ext.data.Record(_record.data);
				Ext.getCmp('mem-grid').getStore().insert(0,record);
			}
		});
	}
}
function showMemberWin(_id){
	new Ext.Window({
		frame:true,
		closable:false,
		closeAction:'close',
		width:500,
		height:Ext.getBody().getSize().height*0.85,
		layout:'fit',
		buttonAlign:'center',
		buttons:[
			{
				text:'保存',handler:function(){
					var id=[];
					Ext.getCmp('mem-grid').getStore().each(function(_r){
						id.push(_r.data.id);
					});
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/admin/ResearchAdminAction.do',
						params:{
							method:'mem_res_rel_save',
							mem:Ext.util.JSON.encode(id),
							id:_id
						},
						scope:this,
						success:function(_response){
							if(Ext.util.JSON.decode(_response.responseText).success){
								alert('成员 保存成功。');
								this.ownerCt.close();
							}else{
								alert('成员 保存失败。');
							}
						}
					});
				}
			},{
				text:'关闭',handler:function(){this.ownerCt.close();}
			}
		],
		items:createMemberGrid(_id)
	}).show();
}
