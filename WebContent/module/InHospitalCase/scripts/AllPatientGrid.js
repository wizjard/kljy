function CreatePatientCard(){
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'姓名',dataIndex:'patientName'},
		{header:'病案号',dataIndex:'patientNo'},
		{header:'性别',dataIndex:'sex0'},
		{header:'出生日期',dataIndex:'birthday'},
		{header:'籍贯',dataIndex:'province0'},
		{header:'职业',dataIndex:'occupation0'},
		{header:'婚姻状态',dataIndex:'marrageStatus0'},
		{header:'家庭电话',dataIndex:'homeTel'},
		{header:'联系人姓名',dataIndex:'contacterName'},
		{header:'联系人电话',dataIndex:'contacterTel'}
	]);
	var ds=new Ext.data.Store({
			proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/patient.do'}),
			reader:new Ext.data.JsonReader({root:'root',totalProperty:'total'},
				[
					{name:'id'},
					{name:'patientNo'},
					{name:'patientName'},
					{name:'gender'},
					{name:'sex0'},
					{name:'birthday'},
					{name:'idType'},
					{name:'idType0'},
					{name:'idNo'},
					{name:'nationality'},
					{name:'nationality0'},
					{name:'people'},
					{name:'people0'},
					{name:'province'},
					{name:'province0'},
					{name:'occupation'},
					{name:'occupation0'},
					{name:'homeTel'},
					{name:'homePostCode'},
					{name:'homeAddr'},
					{name:'marrageStatus'},
					{name:'marrageStatus0'},
					{name:'educationLv'},
					{name:'educationLv0'},
					{name:'mobilePhone'},
					{name:'email'},
					{name:'workUnit'},
					{name:'workUnitAddr'},
					{name:'workUnitTel'},
					{name:'workUnitPostCode'},
					{name:'contacterName'},
					{name:'contacterRelationship'},
					{name:'contacterRelationship0'},
					{name:'contacterTel'},
					{name:'createDate'},
					{name:'modifyDate'},
					{name:'currentWardCode'},
					{name:'currentWardCode0'}
				]
			)
		});
	ds.baseParams={method:'queryAll'};
	var _grid={
		id:'patient-list-grid',
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
