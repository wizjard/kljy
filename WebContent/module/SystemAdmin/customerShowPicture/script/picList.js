function CreateNotice(){
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'图片名称',dataIndex:'picTitle'},
		{header:'信息类型',dataIndex:'typeName'}
	]);
	var ds=new Ext.data.Store({
			proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/picture.do'}),
			reader:new Ext.data.JsonReader({root:'root',totalProperty:'total'},
				[
					{name:'id'},
					{name:'picTitle'},
					{name:'typeName'},
				]
			)
		});
	ds.baseParams={method:'queryAll',typeName:'生物医学信息中心'};
	var _grid={
		id:'picture-list-grid',
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
	function renderDate(format) {
	    return function(v) {
	        var JsonDateValue;
	        if (Ext.isEmpty(v))
	            return '';
	        else if (Ext.isEmpty(v.time))
	            JsonDateValue = new Date(v);
	        else
	            JsonDateValue = new Date(v.time);
	        return JsonDateValue.format(format || 'Y-m-d H:i:s');
	    };
	};
	return _grid;
}
