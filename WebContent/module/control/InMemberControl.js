function CreateNotice(){
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'会员姓名',dataIndex:'patientName'},
		{header:'病案号',dataIndex:'patientNo'},
		{header:'患者编号',dataIndex:'id'},
		{header:'入会时间',dataIndex:'inDate',renderer:renderDate('Y-m-d')},
		{header:'入会科室',dataIndex:'inWard'},
		{header:'当前科室',dataIndex:'deptName'},
		{header:'当前分组',dataIndex:'currentGroup'},
		{header:'门诊总次数',dataIndex:'outCount'},
		{header:'随访门诊总次数',dataIndex:'suifangCount'},
		{header:'住院总次数',dataIndex:'inHspTimes'},
		{header:'随访医生',dataIndex:'inDoctor'}
	]);
	var ds=new Ext.data.Store({
			proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/MemberSearchTotalAction.do'}),
			reader:new Ext.data.JsonReader({root:'root',totalProperty:'total'},
				[
					{name:'patientName'},
					{name:'patientNo'},
					{name:'id'},
					{name:'inDate'},
					{name:'inWard'},
					{name:'deptName'},
					{name:'currentGroup'},
					{name:'outCount'},
					{name:'suifangCount'},
					{name:'inHspTimes'},
					{name:'inDoctor'}
				]
			)
		});
	ds.baseParams={method:'searchInMemberByCondition'};
	var _grid={
		id:'notice-list-grid',
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
