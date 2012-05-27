function ViewNotice(){
	var sm=new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'通知名称',dataIndex:'noticeNam'},
		{header:'通知内容',dataIndex:'noticeContent'},
		{header:'通知日期',dataIndex:'noticeTim'}
	]);
	var ds=new Ext.data.Store({
			proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/notice.do'}),
			reader:new Ext.data.JsonReader({root:'root',totalProperty:'total'},
				[
					{name:'id'},
					{name:'noticeNam'},
					{name:'noticeContent'},
					{name:'noticeTim'}
				]
			)
		});
	ds.baseParams={method:'queryAll'};
	ds.load({params:{start:0,limit:20}});
	
	var _grid={
		id:'list-grid',
		border:false,
		sm:sm,
		cm:cm,
		ds:ds,
		viewConfig:{
			forceFit:true
		},
		bbar:new Ext.PagingToolbar({
            pageSize: 20,
            store: ds,
            displayInfo: true,
            displayMsg: '显示第<font color="red"> {0} </font>条' +
            '到<font color="red"> {1} </font>条记录，' +
            '一共<font color="red"> {2} </font>条',
            emptyMsg: "没有记录"
        })
	};
	return _grid;
}