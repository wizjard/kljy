var pageCode='Topic-Info';
Ext.onReady(function(){
	Ext.QuickTips.init();
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'项目编号',dataIndex:'serialNum'},
		{header:'项目名称',dataIndex:'name'},
		{header:'项目分类',dataIndex:'pj_class1'},
		{header:'单位名称',dataIndex:'belongUnit'},
		{header:'创建日期',dataIndex:'pj_createDate',renderer:function(value){
			if(value&&typeof value=='object'){
				return new Date(value.time).format('Y-m-d');
			}
		}},
		{header:'计划完成日期',dataIndex:'pj_completeDate',renderer:function(value){
			if(value&&typeof value=='object'){
				return new Date(value.time).format('Y-m-d');
			}
		}}
	]);
	var ds=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/ResearchAdminAction.do?method=findAll'}),
		reader:new Ext.data.JsonReader({root:'root',totalProperty:'total'},[
			{name:'id'},
			{name:'name'},
			{name:'serialNum'},
			{name:'responser'},
			{name:'secretLv'},
			{name:'topicType'},
			{name:'belongUnit'},
			{name:'belongWard'},
			{name:'pj_createDate'},
			{name:'pj_completeDate'},
			{name:'pj_class1'},
			{name:'pj_class2'},
			{name:'pj_status'},
			{name:'pj_localCost'},
			{name:'pj_icpNum'},
			{name:'pj_comInvest'},
			{name:'pj_totalCost'},
			{name:'pj_bankInvest'},
			{name:'pj_centerCost'},
			{name:'pj_otherCost'},
			{name:'pj_desc'},
			{name:'tj_subjectClass'},
			{name:'tj_researchType'},
			{name:'tj_inteClass1'},
			{name:'tj_inteClass2'},
			{name:'tj_projectFrom'},
			{name:'tj_cooperType'},
			{name:'tj_societyTarget'},
			{name:'tj_projectPersonNum'},
			{name:'tj_desc'}
		])
	});
	ds.load({params:{start:0,limit:20}});
	new Ext.Viewport({
		layout:'fit',
		items:{
			xtype:'grid',
			id:'grid',
			title:'项目列表',
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
			createEditorWin('新增课题');
		}
	});
	array.push('-');
	array.push({
		text:'编辑',
		handler:function(){
			var ss=SelectionRecord();
			if(!ss)return;
			createEditorWin('编辑课题('+ss.data.name+')',ss);
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
				params:{method:'delete',id:ss.data.id},
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
	
	array.push('-');
	array.push({
		text:'随访方案设置',
		handler:function(){
			var ss=SelectionRecord();
			if(!ss)return;
			setFollowupPlan(ss);
		}
	});
	
	array.push('-');
	array.push({
		text:'关联CRF表',
		handler:function(){
			var ss=SelectionRecord();
			if(!ss)return;
			showCRFWin(ss);
		}
	});
	array.push('-');
	array.push({
		text:'分组列表',
		handler:function(){
			var ss=SelectionRecord();
			if(!ss)return;
			showSubGroupWin(ss.data);
		}
	});
	array.push('-');
	array.push({
		text:'成员列表',
		handler:function(){
			var ss=SelectionRecord();
			if(!ss)return;
			showMemberWin(ss.data.id);
		}
	});
	return array;
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
