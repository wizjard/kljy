function PatientGrid(_cfg){
	this.config={
		cm:null,
		baseParams:{method:'queryAll'},
		start:0,
		limit:20,
		store:null
	};
	if(_cfg){
		Ext.apply(this.config,_cfg);
	}
	if(!this.config.cm)
		this.config.cm=
			[
				new Ext.grid.RowNumberer(),
				{header:'姓名',dataIndex:'patientName'},
				{header:'病案号',dataIndex:'patientNo'},
				{header:'性别',dataIndex:'sex0'},
				{header:'出生日期',dataIndex:'birthday'},
				{header:'籍贯',dataIndex:'province0'},
				{header:'职业',dataIndex:'occupation0'},
				{header:'婚姻状态',dataIndex:'marrageStatus0'},
				{header:'家庭电话',dataIndex:'homeTel'},
				{header:'联系人姓名',dataIndex:'contacterName'},
				{header:'联系人电话',dataIndex:'contacterTel'},
				{header:'最近所在科室',dataIndex:'currentWardCode0'}
			];
	if(!this.config.store)
		this.config.store=new Ext.data.Store({
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
	this.config.store.baseParams=this.config.baseParams;
	var _config=this.config;
	this.createGridPanel=function(){
		var _grid=new Ext.grid.GridPanel({
			id:'patient-list-grid',
			border:false,
			sm:new Ext.grid.CheckboxSelectionModel({sigleSelect:true}),
			cm:new Ext.grid.ColumnModel(_config.cm),
			ds:_config.store,
			autoScroll: true,
			viewConfig:{
				forceFit:true
			},
			bbar:new Ext.PagingToolbar({
	            pageSize: 20,
	            store: _config.store,
	            displayInfo: true,
	            displayMsg: '显示第<font color="red"> {0} </font>条' +
	            '到<font color="red"> {1} </font>条记录，' +
	            '一共<font color="red"> {2} </font>条',
	            emptyMsg: "没有记录"
	        }),
			loadStore:function(_o){
				var _store=this.getStore();
				var _c={
					url:null,
					baseParams:null
				}
				if(_o){
					Ext.apply(_c,_o);
					if(_c.url){
						_store.proxy.conn.url=_c.url;
					}
					if(_c.baseParams){
						_store.baseParams=_c.baseParams;
					}
					_store.load({params:{start:_config.start,limit:_config.limit}});
				}else{
					_store.reload();
				}
			}
		});
		return _grid;
	}
}
