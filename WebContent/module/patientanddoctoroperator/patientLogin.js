var deptName="";
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
	var _sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var _cm_normal=[
		new Ext.grid.RowNumberer(),
		_sm,
		{header:'咨询时间',dataIndex:'consultingDate',renderer:function(value, meta, record, rowIndex, colIndex, store){
			var replyFlag = 1;
			if(record.data.reply == "未读"){
				replyFlag = 0;
				if(record.data.readCount ==1){
					return record.data.consultingDate;
				}else{
					return "<B>"+record.data.consultingDate+"</B>";
				}
			}else{
				return record.data.consultingDate;
			}
		},width:40,
		  sortable:true	
		},
		{header:'第几次咨询',dataIndex:'consultingCount',renderer:function(value, meta, record, rowIndex, colIndex, store){
			var replyFlag = 1;
			if(record.data.reply == "未读"){
				replyFlag = 0;
				if(record.data.readCount ==1){
					return record.data.consultingCount;
				}else{
					return "<B>"+record.data.consultingCount+"</B>";
				}
			}else{
				return record.data.consultingCount;
			}
		},width:30,
		  sortable:true
		
		},
		{header:'责任科室',dataIndex:'deptName',renderer:function(value, meta, record, rowIndex, colIndex, store){
			var replyFlag = 1;
			if(record.data.reply == "未读"){
				replyFlag = 0;
				if(record.data.readCount ==1){
					return record.data.deptName;
				}else{
					return "<B>"+record.data.deptName+"</B>";
				}
			}else{
				return record.data.deptName;
			}
		},
		width:110,
		sortable:true
		},
		{header:'咨询医生',dataIndex:'doctorName',renderer:function(value, meta, record, rowIndex, colIndex, store){
			var replyFlag = 1;
			if(record.data.reply == "未读"){
				replyFlag = 0;
				if(record.data.readCount ==1){
					return record.data.doctorName;
				}else{
					return "<B>"+record.data.doctorName+"</B>";
				}
			}else{
				return record.data.doctorName;
			}
		},
		width:30,
		sortable:true
		},
		{header:'状态',dataIndex:'reply',renderer:function(value, meta, record, rowIndex, colIndex, store){
			var replyFlag = 1;
			if(record.data.reply == "未读"){
				replyFlag = 0;
				if(record.data.readCount ==1){
				//liugang 2011-08-06 start
//					if(record.data.pcMeetingState == 1){
//						return "<a href='###' onclick='linkToOneQuestion("+record.data.id+","+replyFlag+")'>咨询结束</a>";
//					}else{
						return "<a href='###' onclick='linkToOneQuestion("+record.data.id+","+replyFlag+")'>已回复</a>";
//					}
				}else{
//					if(record.data.pcMeetingState == 1){
//						return "<a href='###' onclick='linkToOneQuestion("+record.data.id+","+replyFlag+")'><B>咨询结束</B></a>";
//					}else{
						return "<a href='###' onclick='linkToOneQuestion("+record.data.id+","+replyFlag+")'><B>已回复</B></a>";
//					}
				}
			}else{
//				if(record.data.pcMeetingState == 1){
//					return "<a href='###' onclick='linkToOneQuestion("+record.data.id+","+replyFlag+")'>咨询结束</a>";
//				}else{
					return "<a href='###' onclick='linkToOneQuestion("+record.data.id+","+replyFlag+")'>"+record.data.reply+"</a>";
//				}
			}
			//liugang 2011-08-06 end
		},
		width:30,
		sortable:true
		}
	];
	var _ra_normal=[
		{name:'id'},
		{name:'patientId'},
		{name:'consultingCount'},
		{name:'deptName'},
		{name:'consultingDate'},
		{name:'doctorName'},
		{name:'reply'},
		{name:'readCount'},
		{name:'pcMeetingState'}
	];
	var _cfg=window.parent.GridCfg;
	var _url=App.App_Info.BasePath+'/PatientConsultingAction.do';
	var _baseParams={
		method:'findPatientConsultingByPatient',
		flagWeiHui:10,
		flagWeiDu:10,
		flagYiDu:10
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
            	  text:'刷新',handler:function(){
            	  	var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findPatientConsultingByPatient',
      					flagWeiHui:10,
						flagWeiDu:10,
						flagYiDu:10
						};
      				_store.load({params:{start:0,limit:15}});
              	  }
              }
              ,'-',{
            	  text:'未回复',handler:function(){
					var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={
      					method:'findPatientConsultingByPatient',
      					flagWeiHui:1,
						flagWeiDu:10,
						flagYiDu:10
					};
      				_store.load({params:{start:0,limit:15}});
              	  }
              }
              ,'-',{
            	  text:'已回复',handler:function(){
					var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findPatientConsultingByPatient',
      					flagWeiHui:10,
						flagWeiDu:0,
						flagYiDu:10
						};
      				_store.load({params:{start:0,limit:15}});
              	  }
              }
              ,'-',{
            	  text:'咨询结束',handler:function(){
					var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findPatientConsultingByPatient',
      					flagWeiHui:10,
						flagWeiDu:10,
						flagYiDu:2
						};
      				_store.load({params:{start:0,limit:15}});
              	  }
              }
//              ,'-',{
//            	  text:'已读',handler:function(){
//					var _store=Ext.getCmp('grid').getStore();
//      				_store.baseParams={method:'findPatientConsultingByPatient',
//      					flagWeiHui:10,
//						flagWeiDu:10,
//						flagYiDu:2
//						};
//      				_store.load({params:{start:0,limit:15}});
//              	  }
//              }
              ,'-',{
            	  text:'返回列表',handler:function(){
					var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findPatientConsultingByPatient',
      					flagWeiHui:10,
						flagWeiDu:10,
						flagYiDu:10
						};
      				_store.load({params:{start:0,limit:15}});
              	  }
              }
               ,
					'-', {
			            text: '录入化验检查结果',
			            handler: function(){
			               if(top.USER != undefined){
			               		window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+patientId+'&doctorName='+top.USER.name+'&doctorOrPatient=1', '', 'width=240px;');
			               }else{
			               	    if(window.confirm("您录入的化验检查结果将直接影响到医生对您病情的判断，为保证医生能够做出正确的判断，请务必认真核对小数点的位置、单位及参考值范围等，确保录入内容真实可靠。")){
			               	    	window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+top.Member.patientId+'&patientName='+top.Member.name, '', 'width=240px;');
			               	    }
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

function linkToOneQuestion(num,replay){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PatientConsultingAction.do',
		params:{
			method:'updateState',
			id:num
		},
		scope:this
	 });
	window.location.href=App.App_Info.BasePath+'/module/patientanddoctoroperator/patientConsultingOne.html?pcId='+num+'&Para='+replay;
}