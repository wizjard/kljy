var deptcode;//科室编码
var doctorId;//诊疗医生HIS中编号
var planOrderStartDate;//诊疗时间即出诊时间
var planOrderEndDate;//结束时间
var deptcodenameList=new Array();//部门列表
var doctorList = null;//医生列表
//load first deparment doctorList
function loadDoctorList(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/user.do',
		params:{
			method:'findUserNameByDeptcode',
			deptCode:deptcode
		},
		sync:true,
		success:function(response){
			doctorList = new Array();
			var list = Ext.util.JSON.decode(response.responseText);
			for(var i=0,size=list.length;i<size;i++){
				var doctorListI = new Array();
				doctorListI.push(list[i].hisDoctorId);
				doctorListI.push(list[i].hisDoctorId+" "+list[i].name);
				doctorList.push(doctorListI);
			}
			doctorId = doctorList[0][0];
		}
	});
}

Ext.onReady(function(){
	getAllDeptment();
	loadDoctorList();
	var simpleForm = new Ext.FormPanel({ 
      labelAlign: 'right', 
      broder:false,
      buttonAlign:'right', 
      bodyStyle:'padding:5px', 
      width: 600, 
      frame:true, 
      layout:'column',
      defaults:{
      	border:false,
      	layout:'form'
      },
      items: [
      	{
      		columnWidth:.2,
      		labelWidth:60,
      		items:[
      			{
		      	   fieldLabel:'诊疗科室',
		      		xtype:'combo',
		      		mode: 'local',
		      		store: new Ext.data.SimpleStore({
		      			fields:['clinicValue','showClincType'],
		                data: deptcodenameList
		           }),
		      	   displayField: 'showClincType',
		      	   valueField: 'clinicValue',
		      	   //value:deptcodenameList[0][1],
		      	   triggerAction: 'all',
		      	   readOnly: true,
				   anchor:'100%',
		      	   listeners:{
			      	   	  select:function(){
      						deptcode =this.getValue();
      						loadDoctorList();
      						var _store=Ext.getCmp('doctor').getStore();
      						_store.loadData(doctorList);
      						var _storeValue=Ext.getCmp('doctor');
      						_storeValue.setValue(doctorList[0][1]);
      						if(planOrderStartDate == null){
								alert("请选择查询的开始时间");
								return;
							}
							if(planOrderEndDate == null){
								alert("请选择查询的结束时间");
								return;
							}
							var _store=Ext.getCmp('grid').getStore();
    						_store.baseParams={method:'findPlanSignOrderPatientHSList',
    								planOrderStartDate:planOrderStartDate,
									planOrderEndDate:planOrderEndDate,
									doctorId:doctorId,
									deptcode:deptcode
									};
    						_store.load({params:{start:0,limit:15}});
			      	   	  }
			      	   }
		      	}
      		]
      	},{
      		columnWidth:.2,
      		labelWidth:80,
      		items:[
      			{
		      		fieldLabel:'诊疗医生',
		      		xtype:'combo',
		      		mode: 'local',
		      		id:'doctor',
		      		store: new Ext.data.SimpleStore({
		      			fields:['clinicValue','showClincType'],
		                data: doctorList
		           }),
		      	   displayField: 'showClincType',
		      	   valueField: 'clinicValue',
		      	   //value:doctorList[0][1],
		      	   triggerAction: 'all',
		      	   readOnly: true,
		      	   anchor:'100%',
		      	   listeners:{
			      	   	select:function(){
      						doctorId =this.getValue();
      						if(planOrderStartDate == null){
								alert("请选择查询的开始时间");
								return;
							}
							if(planOrderEndDate == null){
								alert("请选择查询的结束时间");
								return;
							}
							var _store=Ext.getCmp('grid').getStore();
    						_store.baseParams={method:'findPlanSignOrderPatientHSList',
    								planOrderStartDate:planOrderStartDate,
									planOrderEndDate:planOrderEndDate,
									doctorId:doctorId,
									deptcode:deptcode
									};
    						_store.load({params:{start:0,limit:15}});
			      	   	}
			      }
		      	}
      		]
      	},{
      		columnWidth:.2,
      		labelWidth:80,
      		items:[
      			{
      				fieldLabel:'开始日期',
      				xtype:'datefield',
					anchor:'100%',
					name:'receiveDate',
					id:'receiveDate',
					readOnly:true,
					emptyText:'时间搜索',
					listeners:{
						select:function(){
							planOrderStartDate = this.getValue().format("Y-m-d");
						}
					}	  	      	    
		      	}
      		]
      	},{
      		columnWidth:.2,
      		labelWidth:80,
      		items:[
      			{
		      		fieldLabel:'结束日期',
		      		xtype:'datefield',
					name:'endD',
					id:'enfD',
		      		readOnly:true,
		      	    anchor:'100%',
		      	    emptyText:'时间搜索',
					listeners:{
						select:function(){
							planOrderEndDate = this.getValue().format("Y-m-d");
							if(planOrderStartDate == null){
								alert("请选择查询的开始时间");
								return;
							}
							if(planOrderEndDate == null){
								alert("请选择查询的结束时间");
								return;
							}
							var _store=Ext.getCmp('grid').getStore();
    						_store.baseParams={method:'findPlanSignOrderPatientHSList',
    								planOrderStartDate:planOrderStartDate,
									planOrderEndDate:planOrderEndDate,
									doctorId:doctorId,
									deptcode:deptcode
									};
    						_store.load({params:{start:0,limit:15}});
						}
					}	  	      	  
		      	}
      		]
      	}
      ]
	}); 

	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'north',
				layout:'fit',
				border:false,
				height:50,
				items:simpleForm
			},{
				region:'center',
				layout:'fit',
				autoScroll:true,
				border:false,
				items:createGrid()
			}
		]
	});
});


function getAllDeptment(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
		params:{
			method:'findAllSYS_HIS_DepartmentHISEntity'
		},
		sync:true,
		success:function(response){
			var data = Ext.util.JSON.decode(response.responseText);
			var list = data.department;
			for(var i=0,size=list.length;i<size;i++){
				var deptcodenameList1 = new Array();
				deptcodenameList1.push(list[i].deptCode);
				deptcodenameList1.push(list[i].deptCode+"   "+list[i].deptName);
				deptcodenameList.push(deptcodenameList1);
			}
			deptcode = deptcodenameList[0][0];//科室编码动态改变加载对应的数据
		}
	});
}

function createGrid(){
	//planOrderStartDate = new Date().format('Y/m/d');
	var _sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var _cm_normal=[
		new Ext.grid.RowNumberer(),
		_sm,
		{header:'会员姓名',dataIndex:'patientName'},
		{header:'性别',dataIndex:'gender'},
		{header:'联系电话',dataIndex:'homeTel'},
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
		{name:'bsTSId'}
	];
	var _cfg=window.parent.GridCfg;
	var _url=App.App_Info.BasePath+'/PlanSignOrderAction.do';
	var _baseParams={
		method:'findPlanSignOrderPatientHSList',
		planOrderStartDate:planOrderStartDate,
		planOrderEndDate:planOrderEndDate,
		doctorId:doctorId,
		deptcode:deptcode
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
            emptyMsg: "没有记录"
        }),
        tbar:[
              '-',{
            	  text:'导出会员预约挂号数据',handler:function(){
            	  	  window.open(App.App_Info.BasePath+'/PlanSignOrderAction.do?method=executeExcel&planOrderStartDate='+planOrderStartDate+'&planOrderEndDate='+planOrderEndDate+'&doctorId='+doctorId+'&deptcode='+deptcode);
              	  }
              }
        ]
	});
	return _grid;
}

function getSelectionRows(){
	return Ext.getCmp('grid').getSelectionModel().getSelections();
}


