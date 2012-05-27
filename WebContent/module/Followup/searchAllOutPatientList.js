var pId = null;
var seekList = null;
var seek = null;
Ext.onReady(function(){
	Ext.QuickTips.init();
	seek = [
        ['0', '请选择过滤条件'],
        ['patientId', '病人编号'],
        ['guhaodate', '挂号日期'],
        ['patientName', '病人姓名'],
        ['zhengjianNum', '证件号']
    ];
	seekList = new Ext.data.SimpleStore({
        fields: ['seekValue', 'showSeek'],
        data : seek 
    });
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
	var _sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var _cm_normal=[
		new Ext.grid.RowNumberer(),
		_sm,
		{header:'挂号时间',dataIndex:'jtime',sortable:true,width:140},
		{header:'患者编号',dataIndex:'ptno'},
		{header:'患者姓名',dataIndex:'sname'},
		{header:'证件类型',dataIndex:'jtype'},
		{header:'证件号',dataIndex:'jumin',width:140},
		{header:'性别',dataIndex:'sex',width:40},
		{header:'年龄',dataIndex:'birthdate',width:40},
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
		{name:'jtime'},
		{name:'jtype'},
		{name:'jumin'}
	];
	var _cfg=window.parent.GridCfg;
	var _url=App.App_Info.BasePath+'/OutOrMergencyPatientAction.do';
	var _baseParams={
		method:'executeOnePatientOutCaseListByHis',
		flag:"",
		searchCondition:""
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
            	  		//执行自动产生当前病人的门诊记录
            	  	executePatientByHISPatientId(_ss[0].data.ptno,_ss[0].data.jtime,_ss[0].data.regno,_ss[0].data.deptcode,_ss[0].data.deptname);
					location.href="Followup.html?id="+pId+'&actHISPID='+_ss[0].data.ptno+'&flag=search';
              	  }
              },'->',{
					xtype:'tbseparator'
				},{
					xtype : "combo",
					width:150,									
					id:'seek',
					store: seekList,
                    displayField:'showSeek',
                    valueField:'seekValue',
                    typeAhead: true,
                    value:seek[0][1],
                    mode: 'local',
                    triggerAction: 'all',
                    listeners:{
                    	collapse:function(){
                        	var temp = Ext.getCmp('seek').getValue();
                        	if(temp == 0 || temp == 'patientId' || temp == 'guhaodate' || temp == 'patientName' || temp == 'zhengjianNum'){
                        		Ext.getCmp('search-keyword').setValue('请输入查询关键字');
								Ext.getCmp('search-keyword').show();
                        	}
                        }
                    }
				},{
					xtype:'tbseparator'
				},{
					xtype:'textfield',
					width:150,
					emptyText:'请输入查询关键字',
					id:'search-keyword',
					listeners:{
						focus:function(){
							Ext.getCmp('search-keyword').setValue('');
						}
					}
				},{
					xtype:'tbseparator'
				},{
					text:'搜索病人',
					tooltip:'根据指定的条件搜索病人',
					handler:function(){
						var _condition = Ext.getCmp('seek').getValue();
						if(_condition == 0){
							alert('请选择过滤条件');
							return;
						}						
						var _val=Ext.getCmp('search-keyword').getValue().trim();
						if(_val.length==0 || _val.length=='请输入查询关键字'){
							alert('关键字不能为空。');
							return;
						}
						var _store=Ext.getCmp('grid').getStore();
						_store.baseParams={method:'executeOnePatientOutCaseListByHis',searchCondition:_val,flag:_condition};
						_store.load({params:{start:0,limit:20}});						
						Ext.getCmp('search-keyword').show();
					}
				}
        ]
	});
	return _grid;
}

function getSelectionRows(){
	return Ext.getCmp('grid').getSelectionModel().getSelections();
}

var deptcode =null;
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
						deptcode = doctor.deptcode;//科室编码动态改变加载对应的数据
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

