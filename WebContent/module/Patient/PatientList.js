Ext.onReady(function(){
	new Ext.Viewport({
		layout:'fit',
		items:createGrid()
	});
});
function createGrid(){
	var _sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var _cm_normal=[
		new Ext.grid.RowNumberer(),
		{header:'姓名',dataIndex:'patientName'},
		{header:'病案号',dataIndex:'patientNo'},
		{header:'性别',dataIndex:'gender'},
		{header:'出生日期',dataIndex:'birthday'},
		{header:'籍贯',dataIndex:'province0'},
		{header:'职业',dataIndex:'occupation0'},
		{header:'婚姻状态',dataIndex:'marrageStatus0'},
		{header:'家庭电话',dataIndex:'homeTel'},
		{header:'联系人姓名',dataIndex:'contacterName'},
		{header:'联系人电话',dataIndex:'contacterTel'},
		{header:'最近所在科室',dataIndex:'currentWardCode0'}
	];
	var _ra_normal=[
		{name:'id'},
		{name:'patientNo'},
		{name:'patientName'},
		{name:'gender'},
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
	];
	var _cfg=window.parent.GridCfg;
	var _url=App.App_Info.BasePath+'/patient.do';
	var _baseParams={method:'queryAll'};
	if(_cfg){
		_url=_cfg.url?_cfg.url:_url;
		_baseParams=_cfg.baseParams?_cfg.baseParams:_baseParams;
		if(_cfg.cm){
			_cm_normal=_cm_normal.concat(_cfg.cm);
		}
	}
	var _cm=new Ext.grid.ColumnModel(_cm_normal);
	var _ds=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:_url}),
		reader:new Ext.data.JsonReader({root:'root',totalProperty:'total'},_ra_normal)
	});
	_ds.baseParams=_baseParams;
	_ds.load({params:{start:0,limit:20}});
	var _grid=new Ext.grid.GridPanel({
		title:'全院病人列表',
		id:'grid',
		border:false,
		cm:_cm,
		sm:_sm,
		ds:_ds,
		autoScroll: true,
		viewConfig:{
			forceFit:true
		},
		tbar:[
			'-',{
				text:'新增',handler:function(){
					App.util.addNewMainTab('/module/Patient/PatientInfo.html','新增病人');
				}
			},'-',{
				text:'编辑'
			},'-',{
				text:'删除'
			},'->',{
				xtype:'textfield',
				emptyText:'姓名或病案号过滤',
				width:120,
				id:'search-keyword'
			},'-',{
				text:'过滤'
			},'-'
		],
		bbar:new Ext.PagingToolbar({
            pageSize: 20,
            store: _ds,
            displayInfo: true,
            displayMsg: '显示第<font color="red"> {0} </font>条' +
            '到<font color="red"> {1} </font>条记录，' +
            '一共<font color="red"> {2} </font>条',
            emptyMsg: "没有记录"
        })
	});
	return _grid;
}
function getSelectionRows(){
	return Ext.getCmp('grid').getSelectionModel().getSelections();
}
