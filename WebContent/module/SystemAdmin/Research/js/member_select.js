Ext.onReady(function(){
	Ext.QuickTips.init();
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:false});
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
	array.push('->');
	array.push({
		text:'确定',
		handler:function(){
			var ss=SelectionRecord();
			if(!ss)	return;
			window.returnValue=ss;
			window.close();
		}
	});
	array.push('-');
	array.push({
		text:'关闭',
		handler:function(){
			window.close();
		}
	});
	array.push('-');
	return array;
}
function SelectionRecord(){
	var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
	if(ss.length==0){
		alert('至少选择一条记录。');
		return null;
	}else{
		return ss;
	}
}