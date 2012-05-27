var deptcodenameGrounpList = new Array();//转入小组
var deptcodeGrounp;
var sendGrounp;
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

var checkCurrentDeptOrApplication= 0;//申请科室
function createGrid(){
	var _sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var _cm_normal=[
		new Ext.grid.RowNumberer(),
		_sm,
		{header:'医生编号',dataIndex:'doctorId'},
		{header:'医生名称',dataIndex:'doctorName'},
		{header:'责任小组',dataIndex:'grounpName'}
	];
	var _ra_normal=[
		{name:'id'},
		{name:'grounpId'},
		{name:'doctorId'},//his中医生的账号
		{name:'grounpName'},
		{name:'doctorName'},
		{name:'docId'}//电子病历中医生的主键
	];
	var _cfg=window.parent.GridCfg;
	var _url=App.App_Info.BasePath+'/DepartmentGrounpAction.do';
	var _baseParams={
		method:'findAllDeptDoctorList',
		deptCode:top.USER.DeptCode
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
            	  text:'新增责任组',handler:function(){
            	  	createAddGrounp();
              	  }
              }
              ,
               '-',{
            	  text:'删除责任组',handler:function(){
            	  	deleteGrounp();
              	  }
              }
              ,
               '-',{
            	  text:'新增组员',handler:function(){
            	  	createAddMember();
              	  }
              }
              ,
               '-',{
            	  text:'删除组员',handler:function(){
            	  	var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
					if(ss.length==0){
						alert('请先选择一个责任医师。');
						return;
					}
					Ext.Ajax.request({
							url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
							params:{
								method:'checkUserInOneGrounpCount',
								grounpId:ss[0].get('grounpId')
							},
							sync:true,
							success:function(response){
								var res = Ext.util.JSON.decode(response.responseText);
								if(res.success){
									alert("当前责任组人数不够两人，不能删除该责任成员！");
									return;
								}else{
									if(window.confirm("您确定要删除该责任成员吗？")){
										Ext.Ajax.request({
											url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
											params:{
												method:'deleteMemberDoctor',
												doctorId:ss[0].get('docId')
											},
											sync:true,
											success:function(response){
												var res = Ext.util.JSON.decode(response.responseText);
												if(res.success){
													alert("删除责任医师成功");
												}else{
													alert("删除责任医师失败");
												}
											}
									});
				      				Ext.getCmp('grid').getStore().reload();
									}
								}
							}
					});
              	  }
              }
        ]
	});
	return _grid;
}

function getSelectionRows(){
	return Ext.getCmp('grid').getSelectionModel().getSelections();
}

//新增责任组窗口
function createAddGrounp(){
   var currentWindow =new Ext.Window({
		title:'新增责任组窗口',
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
			labelWidth:75,
			items: [
                 {
					xtype:'textfield',
					fieldLabel:'责任组名称',
					name:'grounpName',
					allowBlank:false,
					anchor:'100%'
				}
            ]
		},
		buttons:[
					{
					text: '保存',
                    handler: function(){
						var values = this.ownerCt.items.get(0).getForm().getValues();
						values.deptcode=top.USER.DeptCode;
						Ext.Ajax.request({
							url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
							params:{
								method:'addNewGrounp',
								data:Ext.encode(values)
							},
							sync:true,
							success:function(response){
								var res = Ext.util.JSON.decode(response.responseText);
								if(res.success){
									alert("新增责任小组成功");
									Ext.getCmp('grid').getStore().reload();
									this.ownerCt.close();
								}else{
									alert("新增责任小组失败");
								}
							}
						});
                    }
				},
				{
					text:'关闭',handler:function(){
						this.ownerCt.close();
					}
				}
		]
	}).show();
}

//新增责任组组员窗口
function createAddMember(){
   getAllDeptmentGrounp();
   var currentWindow =new Ext.Window({
		title:'新增组员窗口',
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
			labelWidth:95,
			items: [
                 {
					xtype:'textfield',
					fieldLabel:'责任成员工号',
					name:'doctorId',
					id:'doctorId',
					allowBlank:false,
					anchor:'100%',
					emptyText:'请输入医生在HIS中的编号查找到该医生'
				},
				{
				    fieldLabel: '责任小组',
                    xtype: 'combo',
                    mode: 'local',
                    name:'grounpId',
                    id:'grounpId',
                    anchor:'100%',
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
            ]
		},
		buttons:[
					{
					text: '保存',
                    handler: function(){
                  		var grounpName= document.getElementById("grounpId");
                    	if(grounpName.value == ""){
                    		alert("请选择责任小组");
                    		return;
                    	}
						var inputValue= document.getElementById("doctorId").value;
						var firstCode = inputValue.charAt(0);
						if(firstCode >="0" && firstCode <="9"){
							if(inputValue.length < 6 && inputValue.length == 4){
								var result = "00"+inputValue;
							}else if(inputValue.length <6){
								var result="";
								for(var i=0;i<6-inputValue.length;i++){
									result +="0";
								}
								result +=inputValue;
							}
						}
						document.getElementById("doctorId").value = result;
						var values = this.ownerCt.items.get(0).getForm().getValues();
						values.grounpId = deptcodeGrounp;
						Ext.Ajax.request({
							url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
							params:{
								method:'findDoctorInfo',
								data:Ext.encode(values)
							},
							sync:true,
							success:function(response){
								var res = Ext.util.JSON.decode(response.responseText);
								if(res.success){
									var us = Ext.util.JSON.decode(res.data);
									if(us==null){
										alert("您输入的医生编号不正确，请查证");
									}else if(window.confirm("您确定将《"+us.name+"》加入本组吗?")){
										values.doctorId = us.id;
										Ext.Ajax.request({
											url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
											params:{
												method:'checkUserIsOrNotInOtherGrounp',
												data:Ext.encode(values)
											},
											sync:true,
											success:function(response){
												var res = Ext.util.JSON.decode(response.responseText);
												if(res.success){
													var data = Ext.util.JSON.decode(res.data);
													if(data.name!=null && data.name != ""){
														alert("当前医生还在"+data.name+"中，所以不能加入本组！");
														return;
													}else{
														Ext.Ajax.request({
															url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
															params:{
																method:'findOneUserInsertOneGrounp',
																data:Ext.encode(values)
															},
															sync:true,
															success:function(response){
																var res = Ext.util.JSON.decode(response.responseText);
																if(res.success){
																	alert("保存成功");
																	Ext.getCmp('grid').getStore().reload();					
																}else{
																	alert("保存失败");
																}
															}
														});
													}				
												}
											}
										});
									}								
								}
							}
						});
                    }
				},
				{
					text:'关闭',handler:function(){
						this.ownerCt.close();
					}
				}
		]
	}).show();
}

function findAllDoctorByDept(){
	deptcodenameGrounpList = new Array();
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentHISAction.do',
		params:{
			method:'findDoctorGrounpByMemberByDoctorId',
			deptCode:deptcode,
			doctorId:top.USER.id
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
			grounpName = deptcodenameGrounpList[0][1];
		}
	});
}



//查找某个科室下的责任小组
function getAllDeptmentGrounp(){
	deptcodenameGrounpList = new Array();
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
		params:{
			method:'findDepartmentGrounpByDeptId',
			deptCode:top.USER.DeptCode
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

//删除责任组
function deleteGrounp(){
   getAllDeptmentGrounp();
   var currentWindow =new Ext.Window({
		title:'删除责任组窗口--当前删除责任组后，组内的所有成员也将被删除！',
		closable:false,
		width:450,
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
			labelWidth:130,
			items: [
				{
				    fieldLabel: '选择要删除的责任小组',
                    xtype: 'combo',
                    mode: 'local',
                    name:'grounpId',
                    id:'grounpId',
                    anchor:'100%',
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
            ]
		},
		buttons:[
					{
					text: '删除',
                    handler: function(){
                  		var grounpName= document.getElementById("grounpId");
                    	if(grounpName.value == ""){
                    		alert("请选择责任小组");
                    		return;
                    	}
						if(window.confirm("您确定要删除当前责任组吗？")){
							Ext.Ajax.request({
							url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
							params:{
								method:'deleteGrounpUpdateOther',
								id:deptcodeGrounp,
								deptCode:top.USER.DeptCode
							},
							sync:true,
							success:function(response){
								var res = Ext.util.JSON.decode(response.responseText);
								if(res.success){
									alert("删除当前责任组成功！");
									Ext.getCmp('grid').getStore().reload();		
								}else{
									alert("删除当前责任组失败！");
								}
							}	
						});
						}
                    }
				},
				{
					text:'关闭',handler:function(){
						this.ownerCt.close();
					}
				}
		]
	}).show();
}