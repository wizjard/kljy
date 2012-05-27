var deptcodenameList;//门诊医生科室列表
var deptcode;//科室编码
var gbrep;//诊疗结束标记
var allTotal;//总记录数
var currentTotal;//当前操作的后的记录数
var pId;//电子病历操作的ID

var yizhouDate = null;
deptcodenameList =new Array();
Ext.onReady(function(){
	var list = [['0', '未诊疗'],['1', '已诊疗']];
	getUserInfo();
	deptcode = deptcodenameList[0][0];//科室编码动态改变加载对应的数据
	var simpleForm = new Ext.FormPanel({ 
      labelAlign: 'right', 
      //title:'病人列表',
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
      		labelWidth:100,
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
		      	   value:deptcodenameList[0][1],
		      	   triggerAction: 'all',
		      	   readOnly: true,
		      	   anchor:'100%',
		      	   listeners:{
			      	   	  select:function(){
      						deptcode =this.getValue();
      						if(yizhouDate == null){
      							yizhouDate = new Date().format("Y-m-d");
      						}
      						
      						var _store=Ext.getCmp('grid').getStore();
      						_store.baseParams={method:'getOutOrMergencyMiddlePatientList',deptcode:deptcode,
      								drcode:top.USER.account,gbrep:gbrep,yizhouDate:yizhouDate};
      						_store.load({params:{start:0,limit:20}});
			      	   	  }
			      	   }
		      	}
//		      	{
//                    fieldLabel: '当前总挂号患者数',
//                    xtype: 'textfield',
//                    readOnly: true,
//                    name: 'name2',
//                    anchor:'90%'
//                }
      		]
      	},{
      		columnWidth:.2,
      		labelWidth:100,
      		items:[
      			{
		      		fieldLabel:'诊疗医生',
		      		xtype: 'textfield',
		      		value: top.USER.account+top.USER.name,
	                readOnly: true,
	                name: 'doctorName',
		      	    anchor:'100%'
		      	}
//		      	{
//                    fieldLabel: '当前等待诊疗患者',
//                    xtype:'textfield',
//                    readOnly: true,
//                    name: 'name1',
//                    anchor:'90%'
//                }
      		]
      	},{
      		columnWidth:.2,
      		labelWidth:100,
      		items:[
      			{
      				fieldLabel:'选择患者',
		      		xtype:'combo',
		      		mode: 'local',
		      		store: new Ext.data.SimpleStore({
		      			fields:['clinicValue','showClincType'],
		                data: list
		           }),
		      	   displayField: 'showClincType',
		      	   valueField: 'clinicValue',
		      	   value:list[0][1],
		      	   triggerAction: 'all',
		      	   readOnly: true,
		      	   anchor:'100%',
		      		 listeners:{
			      	   	  select:function(){
      						gbrep =this.getValue(); 
      						if(yizhouDate == null){
      							yizhouDate = new Date().format("Y-m-d");
      						}
      						var _store=Ext.getCmp('grid').getStore();
    						_store.baseParams={method:'getOutOrMergencyMiddlePatientList',deptcode:deptcode,
    								drcode:top.USER.account,gbrep:gbrep,yizhouDate:yizhouDate};
    						_store.load({params:{start:0,limit:20}});
			      	   	  }
			      	   }	      	    
		      	}
//		      	{
//                    fieldLabel: '当前诊疗结束患者',
//                    xtype:'textfield',
//                    readOnly: true,
//                    name: 'name3',
//                    anchor:'90%'
//                }
      		]
      	},{
      		columnWidth:.2,
      		labelWidth:100,
      		items:[
      			{
      				fieldLabel:'门诊日期',
      				xtype:'datefield',
					anchor:'100%',
					name:'receiveDate',
					id:'receiveDate',
					readOnly:true,
					value:new Date().format('Y-m-d'),
					listeners:{
						select:function(){
							yizhouDate = this.getValue().format("Y-m-d");
							var _store=Ext.getCmp('grid').getStore();
    						_store.baseParams={method:'getOutOrMergencyMiddlePatientList',
	    						deptcode:deptcode,
								drcode:top.USER.account,
								gbrep:gbrep,
								yizhouDate:yizhouDate
							 };
    						_store.load({params:{start:0,limit:15}});
						}
					}	  	     
//		      		fieldLabel:'医嘱日期',
//		      		xtype:'textfield',
//		      		mode:'local',
//		      		value:new Date().format('Y/m/d'),
//		      		readOnly:true,
//		      	    anchor:'100%'
		      	}
//		      	{
//                    fieldLabel: '其它及留观患者',
//                    xtype:'textfield',
//                    readOnly: true,
//                    name: 'name4',
//                    anchor:'90%'
//                }
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
				border:false,
				items:createGrid()
			}
		]
	});
});

function createGrid(){
	var _sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var _cm_normal=[
		new Ext.grid.RowNumberer(),
		_sm,
		{header:'挂号时间',dataIndex:'jtime',width:140},
		{header:'患者编号',dataIndex:'ptno'},
		{header:'患者姓名',dataIndex:'sname'},
		{header:'性别',dataIndex:'sex'},
		{header:'年龄',dataIndex:'birthdate'},
		{header:'初/再诊',dataIndex:'gbchojae'},
		{header:'保险区分',dataIndex:'bi'},
		{header:'挂号区分(挂号/预约)',dataIndex:'restype'},
		{header:'联系电话',dataIndex:'tel'},
		{header:'最近来院科室',dataIndex:'lastdeptname'},
		{header:'最近来院日期',dataIndex:'lastdate'},
		{header:'挂号情况',dataIndex:'delmark'}
	];
	var _ra_normal=[
		{name:'ptno'},
		{name:'sname'},
		{name:'sex'},
		{name:'birthdate'},
		{name:'gbchojae'},
		{name:'bi'},
		{name:'restype'},
		{name:'tel'},
		{name:'lastdeptname'},
		{name:'lastdate'},
		{name:'delmark'},
		{name:'actdate'},
		{name:'regno'},
		{name:'deptcode'},
		{name:'deptname'},
		{name:'jtime'}
	];
	var _cfg=window.parent.GridCfg;
	var _url=App.App_Info.BasePath+'/OutOrMergencyPatientAction.do';
	var _baseParams={
		method:'getOutOrMergencyMiddlePatientList',
		deptcode:deptcode,
		drcode:top.USER.account,
		gbrep:gbrep,
		yizhouDate:new Date().format("Y-m-d")
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
	_ds.load({params:{start:0,limit:20}});
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
            	  text:'刷新',handler:function(){
            	  	Ext.getCmp('grid').getStore().reload();
              	  }
              }
              
              ,'-',{
            	  text:'门诊登记',handler:function(){
            	  	var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
            	  	if(_ss.length>1||_ss.length==0){
            	  		alert('请选择一条病人记录。');
            	  		return;
            	  	}
            	  	//执行自动产生当前病人的门诊记录
            	  	executePatientByHISPatientId(_ss[0].data.ptno,_ss[0].data.jtime,_ss[0].data.regno,_ss[0].data.deptcode,_ss[0].data.deptname);
					location.href="Followup.html?id="+pId+'&actHISPID='+_ss[0].data.ptno+'&flag=act';
              	  }
              },'->',{
            	  xtype:'tbseparator'
			  }
//			  ,{
//				xtype:'textfield',
//				width:150,
//				emptyText:'病人姓名或病案号',
//				id:'search-keyword'
//			  },{
//				text:'搜索病人',
//				tooltip:'根据病人姓名或病案号搜索病人',
//				handler:function(){
//					var _val=Ext.getCmp('search-keyword').getValue().trim();
//					if(_val.length==0){
//						alert('关键字不能为空。');
//						return;
//					}
//					var _store=Ext.getCmp('grid').getStore();
//					_store.baseParams={method:'searchByNameOrNo',keyword:_val};
//					_store.load({params:{start:0,limit:20}});
//				}
//			  },{
//				xtype:'tbseparator'
//			  }
        ]
	});
	return _grid;
}

function getSelectionRows(){
	return Ext.getCmp('grid').getSelectionModel().getSelections();
}

function getUserInfo(){
	Ext.Ajax.request({
			url:App.App_Info.BasePath+'/OutOrMergencyDoctorAction.do',
			params:{
				method:'executeHISDoctorByIdnumberAndPassword',
				name:top.USER.account
			},
			sync:true,//同步
			success:function(response){
				var data = Ext.util.JSON.decode(response.responseText);
				if(data.success){
					var doctor = data.data;
					doctor= JSON.parse(doctor);
					if(doctor.deptcode != null && doctor.deptcode !="" && doctor.deptcode != undefined){
						var deptcodenameList1 = new Array();
						deptcodenameList1.push(doctor.deptcode);
						deptcodenameList1.push(doctor.deptcodename);
						deptcodenameList.push(deptcodenameList1);
					}
					if(doctor.drdept1 != null && doctor.drdept1 != "" && doctor.drdept1 != undefined){
						var deptcodenameList2 = new Array();
						deptcodenameList2.push(doctor.drdept1);
						deptcodenameList2.push(doctor.drdeptname1);
						deptcodenameList.push(deptcodenameList2);
					}
					if(doctor.drdept2 != null && doctor.drdept2 !="" && doctor.drdept2 != undefined){
						var deptcodenameList3 = new Array();
						deptcodenameList3.push(doctor.drdept2);
						deptcodenameList3.push(doctor.drdeptname2);
						deptcodenameList.push(deptcodenameList3);
					}
					if(doctor.drdept3 != null && doctor.drdept3 != "" && doctor.drdept3 != undefined){
						var deptcodenameList4 = new Array();
						deptcodenameList4.push(doctor.drdept3);
						deptcodenameList4.push(doctor.drdeptname3);
						deptcodenameList.push(deptcodenameList4);
					}
					if(doctor.drdept4 != null && doctor.drdept4 != "" && doctor.drdept4 != undefined){
						var deptcodenameList5 = new Array();
						deptcodenameList5.push(doctor.drdept4);
						deptcodenameList5.push(doctor.drdeptname4);
						deptcodenameList.push(deptcodenameList5);
					}
					if(doctor.drdept5 != null && doctor.drdept5 != "" && doctor.drdept5 != undefined){
						var deptcodenameList6 = new Array();
						deptcodenameList6.push(doctor.drdept5);
						deptcodenameList6.push(doctor.drdeptname5);
						deptcodenameList.push(deptcodenameList6);
					}
				}
			}
		});
}

//执行存储过程判断当前病人是否已经存在
function executePatientByHISPatientId(ptno,actdate,regno,deptcode,deptname){
	Ext.Ajax.request({
			url:App.App_Info.BasePath+'/OutOrMergencyPatientAction.do',
			params:{
				method:'executeHISPatientByHISPatientid',
				patientId:ptno,
				actdate:actdate,
				regno:regno,
				deptcode:deptcode,
				deptname:deptname
			},
			sync:true,//同步
			success:function(response){
				var data = Ext.util.JSON.decode(response.responseText);
				if(data.success){
					var result = Ext.util.JSON.decode(data.data);
					pId = result.patientId;//PID 重新赋值
				}
		}
	});
}

