
function currentTime()
{
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	if(month < 10){
		month = "0"+month;
	}
	var day = date.getDate();
	if(day <10){
		day = "0"+day;
	}
	var hours = date.getHours();
	if(hours <10)
	{
		hours = "0"+hours;
	}
	var minute = date.getMinutes();
	if(minute <10)
	{
		minute = "0"+minute;
	}
	var second = date.getSeconds();
	if(second <10){
		second ="0"+second;
	}
	var currentTime = year+"-"+month+"-"+day;
	return currentTime;
	
}

Ext.onReady(function(){
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'center',
				layout:'fit',
				border:false,
				items:createGrid()
			}
		]
	});
});

function createGrid(){
//	var _sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});选中多行
	 var _sm = new Ext.grid.RowSelectionModel({singleSelect: true}); //选中一行
	var _cm_normal=[
		new Ext.grid.RowNumberer(),
		//_sm,
		{header:'编号',dataIndex:'bianhao'},
//		{header:'会员姓名',dataIndex:'patientName'},
//		{header:'性别',dataIndex:'gender'},
//		{header:'联系电话',dataIndex:'homeTel'},
		{header:'预约科室',dataIndex:'deptName'},
		{header:'预约医生',dataIndex:'doctorName'},
		{header:'预约时间',dataIndex:'planDate'},
		{header:'预约类型',dataIndex:'bsAPId'},
		{header:'门诊时间',dataIndex:'bsTSId'}
	];
	var _ra_normal=[
		{name:'id'},
		{name:'patientName'},
		{name:'gender'},
		{name:'homeTel'},
		{name:'deptName'},
		{name:'doctorName'},
		{name:'planDate'},
		{name:'bsAPId'},
		{name:'bsTSId'},
		{name:'bianhao'}
	];
	var _cfg=window.parent.GridCfg;
	var _url=App.App_Info.BasePath+'/PlanSignOrderAction.do';
	var _baseParams={
		method:'findPlanSignOrderPatientList'
	};
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
	_ds.load({params:{start:0,limit:15}});
	var _grid=new Ext.grid.GridPanel({
		id:'grid',
		border:false,
		cm:_cm,
		sm:_sm,
		ds:_ds,
		autoScroll: true,
		viewConfig:{
			forceFit:true
		},
		bbar:new Ext.PagingToolbar({
            pageSize: 20,
            store: _ds,
            displayInfo: true,
            displayMsg: '显示第<font color="red"> {0} </font>条' +
            '到<font color="red"> {1} </font>条记录，' +
            '一共<font color="red"> {2} </font>条',
            emptyMsg: "<font color='red'>没有记录</font>"
        }),
        tbar:[
              '-',{
            	  text:'刷新',handler:function(){
            	  	Ext.getCmp('grid').getStore().reload();
              	  }
              },
              '-',{
            	  text:'删除',handler:function(){
            	  	var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
            	  	if(_ss.length>1||_ss.length==0){
            	  		alert('请选择一条记录。');
            	  		return;
            	  	}
            	  	if(_ss[0].data.planDate>currentTime()){
            	  		if(window.confirm("您确定删除本次预约吗？")){
	            	  		Ext.Ajax.request({
								url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
								params:{
									method:'deletePatientPlanOrder',
									deleteId:_ss[0].data.id
								},
								sync:true,
								success:function(response){
									var res = Ext.util.JSON.decode(response.responseText);
									if(res.success){
										alert("取消本次预约成功");
										Ext.getCmp('grid').getStore().reload();
									}else{
										alert("取消本次预约失败");
									}
								}
							});
            	  		}
            	  	}else{
            	  		alert("预约时间过期，不能删除！");
            	  	}
              	  }
              }
        ]
	});
	return _grid;
}

function getSelectionRows(){
	return Ext.getCmp('grid').getSelectionModel().getSelections();
}

function linkToOneQuestion(pcid){
	window.location.href=App.App_Info.BasePath+'/module/patientanddoctoroperator/doctorConsultingOne.html?pcId='+pcid;
}