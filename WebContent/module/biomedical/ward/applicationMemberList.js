
var deptcodenameGrounpList = new Array();//转入小组
var deptcodenameList = new Array();
var deptcodeGrounp;
var patientId;
Ext.onReady(function(){
    getAllDeptment();
    patientId=App.util.getHtmlParameters('patientId');
    //alert(patientId);
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

var checkCurrentDeptOrApplication= 0;//申请科室
function createGrid(){
	var _sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var _cm_normal=[
		new Ext.grid.RowNumberer(),
		_sm,
		{header:'会员姓名',dataIndex:'patientName'},
		{header:'当前责任科室',dataIndex:'oldDeptName'},
		{header:'当前责任小组',dataIndex:'grounpName'},
		{header:'申请转入责任科室',dataIndex:'newdeptName'},
		{header:'申请时间',dataIndex:'applicatinDate'},
		{header:'患者意见',renderer:function(value,meta,record,rowIndex,colIndex,store){
			return "<a href='###' onclick='lookMessage("+record.data.id+",0)'>点击查看</a>";
		}},
		{header:'转出科意见',renderer:function(value,meta,record,rowIndex,colIndex,store){
			return "<a href='###' onclick='lookMessage("+record.data.id+",1)'>点击查看</a>";
		}},
		/*
		{header:'转入科意见',renderer:function(value,meta,record,rowIndex,colIndex,store){
			return "<a href='###' onclick='lookMessage("+record.data.id+",2)'>点击查看</a>";
		}},
		*/
		/*
		{header:'操作',renderer:function(value, meta, record, rowIndex, colIndex, store){
			if(checkCurrentDeptOrApplication == 0){
				if(record.data.autoFlag == 1 && record.data.result==0){
					return "<div id='stateLink'><a href='###' onclick='rendToNewDept("+record.data.id+",1,this,\""+record.data.applicationDeptCode+"\")'>同意</a>&nbsp;&nbsp;"+
				 	"<a href='###' onclick='linkToOneQuestion("+record.data.id+",0,this)'>不同意</a></div>"; 
				}else if((record.data.applicationFlag == 1 && record.data.result==1)){
					return "同意";
				}else if(record.data.result==3){
					return "不同意";
				}
			}else if(checkCurrentDeptOrApplication == 1){
				if(record.data.autoFlag == 0){
					if(record.data.applicationDeptCode == record.data.oldDeptCode){
						return "<div id='stateLink'><a href='###' onclick='rendToNewDept("+record.data.id+",1,this,\""+record.data.applicationDeptCode+"\")'>同意</a>&nbsp;&nbsp;"+
			 			"<a href='###' onclick='linkToOneQuestion("+record.data.id+",0,this)'>不同意</a></div>"; 
					}else{
						return "<div id='stateLink'><a href='###' onclick='linkToOneQuestion("+record.data.id+",1,this)'>同意</a>&nbsp;&nbsp;"+
			 		"<a href='###' onclick='linkToOneQuestion("+record.data.id+",0,this)'>不同意</a></div>"; 
					}
				}else if((record.data.autoFlag == 1 && record.data.currentFlag==1)){
					return "同意";
				}else if((record.data.autoFlag == 2 && record.data.currentFlag==1)){
					return "同意";
				}else if(record.data.result==3){
					return "不同意";
				}
			}
		}},*/
		/*判断操作状态时添加
		{header:'申请角色',renderer:function(value,meta,record,rowIndex,colIndex,store){
		    if(record.data.optrole=='member') {
		        return "会员";
		    } else if(record.data.optrole=='doctor') {
		        return "责任医生";
		    } else {
		        return "未明确";
		    }		
		}},*/
		{header:'处理状态', renderer:function(value,meta,record,rowIndex,colIndex,store){
	        	if(record.data.autoFlag == 0){
				    if(record.data.flag ==0 && record.data.optrole != 'doctor') {
						return "<a href='###' onclick='doctorDept("+record.data.id+",1)'>同意</a>&nbsp;&nbsp;&nbsp;&nbsp;" + 
						"<a href='###' onclick='doctorDept("+record.data.id+",2)'>不同意</a>";
					} else if(record.data.flag == 1) {
					    return "同意";
					} else {
					    return "不同意";
					} 
				}else if((record.data.autoFlag == 1 && record.data.currentFlag==1)){
					return "同意";
				}else if((record.data.autoFlag == 2 && record.data.currentFlag==1)){
					return "同意";
				}else if(record.data.result==3){
					return "不同意";
				}
				/*
		    if(record.data.flag=='0'){
		        return "<div id='stateLink'><a href='###' onclick='doctorDept("+record.data.id+",1,this)'>同意</a>&nbsp;&nbsp;"+
			 			"<a href='###' onclick='doctorDept("+record.data.id+",2,this)'>不同意</a></div>"; 
		    } else if(record.data.flag=='1') {
		        return "同意";
		    } else if(record.data.flag=='2') {
		        return "不同意";
		    } else {
		        return "";
		    }*/
		}},
		{header:'状态',renderer:function(value, meta, record, rowIndex, colIndex, store){
			if(record.data.result == 0 && record.data.autoFlag == 0){
				return "<font color='blue'>申请中</font>";
			}else if(record.data.result == 0 && record.data.autoFlag == 1 && record.data.currentFlag == 1){
				return "<font color='blue'>申请中</font>";
			}else if(record.data.result == 3){
				return "<font color='blue'>不同意</font>";
			}else if(record.data.result == 1){
				return "<font color='blue'>申请成功</font>";
			}
		}}
	];
	var _ra_normal=[
		{name:'id'},
		{name:'patientId'},
		{name:'patientName'},
		{name:'oldDeptName'},
		{name:'oldDeptCode'},
		{name:'grounpName'},
		{name:'grounpId'},
		{name:'applicationDeptCode'},
		{name:'newdeptName'},
		{name:'applicatinDate'},
		{name:'autoFlag'},
		{name:'currentFlag'},
		{name:'applicationFlag'},
		{name:'result'},
		{name:'currentDeptBecause'},
		{name:'applicationDeptBecause'},
		{name:'applicationBacuse'},
		{name:'optrole'},
		{name:'flag'}
		
	];
	var _cfg=window.parent.GridCfg;
	var _url=App.App_Info.BasePath+'/DepartmentGrounpAction.do';
	var _baseParams={
		method:'findDeptListApplicationRecord',
		deptCode:top.USER.DeptCode,
		flag:0//申请科室
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
            	  	Ext.getCmp('grid').getStore().reload();
              	  }
              }
              ,
               '-',{
            	  text:'转入申请',handler:function(){
            	  	var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findDeptListApplicationRecord',
      					deptCode:top.USER.DeptCode,
						flag:0//申请科室
					};
					checkCurrentDeptOrApplication = 0;//申请科室
      				_store.load({params:{start:0,limit:15}});
              	  }
              }
              ,
               '-',{
            	  text:'转出申请',handler:function(){
            	  	var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findDeptListApplicationRecord',
      					deptCode:top.USER.DeptCode,
						flag:1//责任科室
					};
					checkCurrentDeptOrApplication = 1;//申请科室
      				_store.load({params:{start:0,limit:15}});
              	  }
              },
              '-'/*, {
                  text:'会员转科',handler:function() {
                      //alert();
                      var o = this;
                      o.sw = new Ext.Window({
                            draggable: false,
                            resizable: false,
                            width: 300,
                            autoHeight: true,
                            closable: false,
                            buttonAlign: 'center',
                            frame: true,
                            items: {
                                xtype: 'form',
                                border: false,
                                bodyStyle: 'padding-top:5px',
                                labelAlign: 'right',
                                labelWidth: 60,
                                defaults: {
                                    xtype: 'textfield',
                                    anchor: '95%'
                                },
                                items: [
                                {
                                  fieldLabel: '转入科室',
				                    xtype: 'combo',
				                    mode: 'local',
				                    store: new Ext.data.SimpleStore({
										 fields:['clinicValue','showClincType'],
										 data: deptcodenameList
									}),
				                    displayField: 'showClincType',
									valueField: 'clinicValue',
				                    triggerAction: 'all',
				                    readOnly: true,
				                    listeners:{
										select:function(){
									      	deptcode =this.getValue();
										}
									}
                                },
                                {
                                    fieldLabel: '申请理由',
				                    xtype: 'textarea',
				                    height: 80,
				                    allowBlank: true,
				                    anchor: '95%',
				                    name: 'applicationBacuse'
                                }
                                ]
                            },
                            buttons:[{
                                text:'保存',
                                handler: function(){
                                    //alert(patientId);
                                    var values = o.sw.items.get(0).getForm().getValues();
                                    values.patientId = patientId;
                                    values.applicationDeptCode = deptcode;
                                    
                                    Ext.Ajax.request({
										url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
										params:{
											method:'saveApplicationRecord',
											data:Ext.encode(values)
										},
										success:function(_response){
											var res=Ext.decode(_response.responseText);
											if(res.success){
												alert("申请已提交");
											}else{
												alert("申请未能成功提交");
											}
										}
									});
                                    o.sw.hide();
                                }
                            }, {
                                text:'取消',
                                handler: function() {
                                    o.sw.hide();
                                }                                
                            }]
                        });
                     o.sw.show();
                  }
              },'-'*/
        ]
	});
	return _grid;
}

function getSelectionRows(){
	return Ext.getCmp('grid').getSelectionModel().getSelections();
}
function getAllDeptment(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PatientConsultingAction.do',
		params:{
			method:'findAllSYS_HIS_DepartmentHISEntity',
			name:'inHospitalType',
			nameValue:1
		},
		sync:true,
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			for(var i=0,size=list.length;i<size;i++){
				var deptcodenameList1 = new Array();
				deptcodenameList1.push(list[i].deptCode);
				deptcodenameList1.push(list[i].deptName);
				deptcodenameList.push(deptcodenameList1);
//				deptNameOption+="<option value="+list[i].deptCode+">"+list[i].deptName+"</option>";
			}
			deptcode = deptcodenameList[0][0];
		}
	});
}
function doctorDept(_this,state) {
    var currentWindow = new Ext.Window({
        title:'转科操作',
        closable:false,
        width:400,
        closeAction:'close',
        buttonAlign:'center',
        autoHeight:true,
        layout:'fit',
        items: {
            xtype:'form',
			bodyStyle:'padding-top:5px;',
			frame:true,
			border:false,
			labelAlign:'right',
			autoHeight:true,
			labelWidth:35,
			items: [
                    {
                        fieldLabel: '意见',
				        xtype: 'textarea',
				        height: 80,
				        allowBlank: true,
				        anchor: '97%',
				        name: 'applicationBacuse'
                    }
            ]
		},
        buttons:[
			{
				text:'保存',handler:function(){
		    		var _form=this.ownerCt.items.get(0).form;
					if(!_form.isValid()){
						alert('表单未通过验证。');
						return;
					}
					var _values = _form.getValues();
					_values.id = _this;
                    _values.flag = state;
					//var _values=Ext.util.JSON.encode(_form.getValues());
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
						params:{
								method:'cacheApplicationRecord',
								data:Ext.util.JSON.encode(_values),
								id:_this,
								state:state
						},
						scope:this,
						success:function(_response){
							var _res=Ext.util.JSON.decode(_response.responseText);
							if(_res.success){
								alert('申请成功。');
								Ext.getCmp('grid').getStore().reload();
								this.ownerCt.close();
							}else{
								alert('申请成功。');
							}
						}
					});
					//alert(_values);
					this.ownerCt.close();
				}
			},{
				text:'关闭',handler:function(){
					this.ownerCt.close();
				}
			}
		]
    }).show();
    //alert(state);
}
//转出科室
function linkToOneQuestion(_this,state,_thisNode){
	var currentWindow =new Ext.Window({
		title:'选择当前科室小组窗口',
		closable:false,
		width:400,
		closeAction:'close',
		buttonAlign:'center',
		autoHeight:true,
		layout:'fit',
		items:{
			xtype:'form',
			bodyStyle:'padding-top:5px;',
			frame:true,
			border:false,
			labelAlign:'right',
			autoHeight:true,
			labelWidth:35,
			items: [
                    {
                        fieldLabel: '意见',
				        xtype: 'textarea',
				        height: 80,
				        allowBlank: true,
				        anchor: '97%',
				        name: 'applicationBacuse'
                    }
            ]
		},
		buttons:[
			{
				text:'保存',handler:function(){
					var _form=this.ownerCt.items.get(0).form;
					if(!_form.isValid()){
						alert('表单未通过验证。');
						return;
					}
					var _values=Ext.util.JSON.encode(_form.getValues());
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
						params:{
								method:'updateApplicationRecord',
								data:_values,
								id:_this,
								state:state
						},
						scope:this,
						success:function(_response){
							var _res=Ext.util.JSON.decode(_response.responseText);
							if(_res.success){
								alert('申请成功。');
								Ext.getCmp('grid').getStore().reload();
								this.ownerCt.close();
							}else{
								alert('申请成功。');
							}
						}
					});
				}
			},{
				text:'关闭',handler:function(){
					this.ownerCt.close();
				}
			}
		]
	}).show();
}

//转入科室选择
function rendToNewDept(_this,state,_thisNode,appdeptCode){
	getAllDeptmentGrounp(appdeptCode);
	var currentWindow =new Ext.Window({
		title:'转科意见窗口',
		closable:false,
		width:400,
		closeAction:'close',
		buttonAlign:'center',
		autoHeight:true,
		layout:'fit',
		items:{
			xtype:'form',
			frame:true,
			border:false,
			labelAlign:'right',
			autoHeight:true,
			labelWidth:55,
			items: [
				{
				    fieldLabel: '责任小组',
                    xtype: 'combo',
                    mode: 'local',
                    id:'grounp',
                    store: new Ext.data.SimpleStore({
						 fields:['clinicValue','showClincType'],
						 data: deptcodenameGrounpList
					}),
                    displayField: 'showClincType',
					valueField: 'clinicValue',
                    triggerAction: 'all',
                    readOnly: true,
                    listeners:{
						select:function(){
					      	deptcodeGrounp =this.getValue();
						}
					}
                }
                
                ,{
                        fieldLabel: '意见',
				        xtype: 'textarea',
				        height: 80,
				        allowBlank: true,
				        anchor: '97%',
				        name: 'applicationBacuse',
				        hidden:true,
				        hideLabel:true
                }
                
            ]
		},
		buttons:[
			{
				text:'保存',handler:function(){
					var _form=this.ownerCt.items.get(0).form;
					if(!_form.isValid()){
						alert('表单未通过验证。');
						return;
					}
					var _values=Ext.util.JSON.encode(_form.getValues());
					_values.applicationBacuse = "暂无";
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
						params:{
								method:'updateApplicationRecord',
								data:_values,
								deptcodeGrounp:deptcodeGrounp,
								id:_this,
								state:state
						},
						scope:this,
						success:function(_response){
							var _res=Ext.util.JSON.decode(_response.responseText);
							if(_res.success){
								alert('申请成功。');
								Ext.getCmp('grid').getStore().reload();
								this.ownerCt.close();
							}else{
								alert('申请失败。');
							}
						}
					});
				}
			},{
				text:'关闭',handler:function(){
					this.ownerCt.close();
				}
			}
		]
	}).show();
}

//查找某个科室下的责任小组
function getAllDeptmentGrounp(appdeptCode){
	deptcodenameGrounpList = new Array();
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
		params:{
			method:'findDepartmentGrounpByDeptId',
			deptCode:appdeptCode
		},
		sync:true,
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			for(var i=0,size=list.length;i<size;i++){
				var deptGrounp = new Array();
				deptGrounp.push(list[i].id);
				deptGrounp.push(list[i].grounpName);
				deptcodenameGrounpList.push(deptGrounp);
			}
			deptcodeGrounp = deptcodenameGrounpList[0][0];
		}
	});
}

//管理之间查看转科留言
function lookMessage(_para1,_para2){
	var currentWindow =new Ext.Window({
		title:'查看转科留言窗口',
		closable:false,
		width:400,
		closeAction:'close',
		buttonAlign:'center',
		autoHeight:true,
		layout:'fit',
		items:{
			xtype:'form',
			bodyStyle:'padding-top:5px;',
			frame:true,
			border:false,
			labelAlign:'right',
			autoHeight:true,
			labelWidth:35,
			items: [
                 {
                        fieldLabel: '意见',
				        xtype: 'textarea',
				        height: 80,
				        allowBlank: true,
				        anchor: '97%',
				        id: 'applicationBacuse',
				        readOnly:true
                 }
            ]
		},
		listeners:{
			render:function(){
				Ext.Ajax.request({
						url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
						params:{
								method:'findOneMessageById',
								id:_para1
						},
						scope:this,
						success:function(_response){
							var _res=Ext.util.JSON.decode(_response.responseText);
							if(_res.success){
								var data = Ext.util.JSON.decode(_res.data);
								if(_para2 == 0){
									document.getElementById("applicationBacuse").value =data.applicationBacuse;
								}else if(_para2 == 1){
									document.getElementById("applicationBacuse").value =  data.currentDeptBecause;
								}else if(_para2 == 2){
									document.getElementById("applicationBacuse").value = data.applicationDeptBecause;
								}
							}else{
								alert('查看意见失败');
							}
						}
					});
			}
		},
		buttons:[
			{
				text:'关闭',handler:function(){
					this.ownerCt.close();
				}
			}
		]
	}).show();
}
