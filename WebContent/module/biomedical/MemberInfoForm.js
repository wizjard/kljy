Ext.ns('Biomedical.MemberInfoWindow');

var deptcodenameList=new Array();//部门列表
var deptcode;//科室编码
var inWard;//入会科室
var grounpName;//会员所在科室的责任小组名称
var deptcodenameGrounpList = new Array();//责任小组List
var deptcodeGrounp;//怎人小组ID
Biomedical.MemberInfoWindow = Ext.extend(Ext.Window, {
    initComponent: function(){
        Ext.QuickTips.init();
        getAllDeptment();
        Ext.form.Field.prototype.msgTarget = 'side';
        var form = new Ext.form.FormPanel({
            bodyStyle: 'padding:5px',
            border: false,
            autoHeight: true,
            labelAlign: 'right',
            labelWidth: 60,
            layout: 'column',
            defaults: {
                columnWidth: .5,
                layout: 'form',
                border: false,
                defaults: {
                    xtype: 'textfield',
                    anchor: '92%',
                    allowBlank: false
                }
            },
            items: [{
                items: [{
                    fieldLabel: '姓名',
                    readOnly: true,
                    allowBlank: true,
                    name: 'name'
                }, {
                    fieldLabel: '会员编号',
                    readOnly: true,
                    allowBlank: true,
                    name: 'memberNo'
                }, {
                    fieldLabel: '用户名',
                    name: 'account'
                }, {
                    fieldLabel: '会员状态',
                    xtype: 'combo',
                    mode: 'local',
                    store: new Ext.data.SimpleStore({
                        data: [['正常'], ['失访'], ['死亡']],
                        fields: ['value']
                    }),
                    displayField: 'value',
                    valueField: 'value',
                    triggerAction: 'all',
                    value:'正常',
                    readOnly: true,
                    hiddenName: 'memberStatus'
                }, {
                	fieldLabel: '入会科室',
                    readOnly: true,
                    allowBlank: true,
                    value:top.USER.单位
                }
                , {
                	fieldLabel: '责任科室',
                    xtype: 'combo',
                    mode: 'local',
                    id:'deptNameObject',
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
					      	inWard = this.getRawValue();
					      	getAllDeptmentGrounp();
					      	var _store=Ext.getCmp('grounp').getStore();
      						_store.loadData(deptcodenameGrounpList);
      						var _storeValue=Ext.getCmp('grounp');
      						_storeValue.setValue(deptcodenameGrounpList[0][1]);
						}
					}
                },
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
					      	grounpName = this.getRawValue();
						}
					}
                }
                ,{
                	xtype:'numberfield',
                	fieldLabel: '住院次数',
                    allowBlank: true,
                    name: 'inHspTimes'
                }]
            }, {
                items: [{
                    fieldLabel: '病案号',
                    readOnly: true,
                    allowBlank: true,
                    name: 'patientNo'
                }, {
                    xtype: 'datefield',
                    format: 'Y-m-d',
                    fieldLabel: '入会日期',
                    name: 'inDate',
                    value:new Date().format('Y-m-d')
                }, {
                    fieldLabel: '密码',
                    name: 'password'
                }, {
                    fieldLabel: '会员类型',
                    xtype: 'combo',
                    mode: 'local',
                    store: new Ext.data.SimpleStore({
                        data: [['普通会员'], ['VIP会员']],
                        fields: ['value']
                    }),
                    displayField: 'value',
                    valueField: 'value',
                    triggerAction: 'all',
                    readOnly: true,
                    value:'普通会员',
                    hiddenName: 'memberType'
                },{
                	fieldLabel: '预留标本',
                    xtype: 'combo',
                    mode: 'local',
                    store: new Ext.data.SimpleStore({
                        data: [['否',0], ['是',1]],
                        fields: ['text','value']
                    }),
                    displayField: 'text',
                    valueField: 'value',
                    triggerAction: 'all',
                    readOnly: true,
                    hiddenName: 'biaoben'
                }
                ,{
                    xtype:'panel',
					layout:'column',
					border:false,
					defaults:{border:false},
					anchor:'100%',
					items:[
						{
							columnWidth:.8,
							layout:'form',
							items:{
								xtype:'textfield',
								fieldLabel: '入会医生',
								readOnly:true,
			                    name: 'inDoctor',
								anchor:'99%'
							}
						},{
							columnWidth:.2,
							listeners:{
								render:function(){
									var panel=this;
									var img=Ext.DomHelper.append(this.body,{
										tag:'a',
										href:'###',
										html:'签字',
										style:'cursor:pointer'
									});
									Ext.get(img).on('click',function(){
										panel.ownerCt.items.get(0).items.get(0).setValue(top.USER.name);
									});
								}
							}
						}
					]
                }
                ,{
                    xtype: 'panel',
                    border:false,
                    anchor:'100%',
                    html: '<p style="line-height:70px"><font color=red>注:</font>住院病人入会时需要填写</p>'
                }]
            }, {
                columnWidth: 1,
                items: [{
                    xtype: 'textarea',
                    fieldLabel: '备注',
                    height: 60,
                    allowBlank: true,
                    anchor: '95%',
                    name: 'memo'
                }, {
                    xtype: 'hidden',
                    name: 'patientId'
                }]
            }]
        });
        Ext.apply(this, {
            title: '入会信息',
            width: 550,
            autoHeight: true,
            buttonAlign: 'center',
            closable: false,
            layout: 'fit',
            items: form,
            buttons: [{
                text: '保存',
                handler: function(){
                    var w = this.ownerCt;
                    if (w.items.get(0).getForm().isValid()) {
                        var values = w.items.get(0).getForm().getValues();
                        values.deptCode= deptcode;
                        values.grounpId = deptcodeGrounp;
                        values.inWard = inWard;
                        values.inWardCode = top.USER.DeptCode
//                        values.grounpName = grounpName;
                        var saveFn = function(){
                            Ext.Ajax.request({
                                url: App.App_Info.BasePath + '/biomedical.do',
                                params: {
                                    data: Ext.encode(values),
                                    method: 'saveOrUpdateMember'
                                },
                                success: function(response){
                                    var res = Ext.decode(response.responseText);
                                    if (res.success) {
										alert('保存成功');
//										loadPatSimInfo();
                                        if (w.saveCallback && typeof w.saveCallback == 'function') {
                                            w.saveCallback(res, w);
                                        }
                                        else {
                                            w.hide();
                                        }
                                        if(!(values.memberNo&&values.memberNo.length>0)){
                                        	//当会员是初次入会的时候，询问是否设置分组
                                        	if(confirm('是否设置会员的随访与分组？')){
                                        		GeneratorRecord(w.items.get(0).getForm().getValues().patientId,w.items.get(0).getForm().getValues().inDate);
                                        	}
                                        }
                                    }
                                    else {
                                        alert('保存失败');
                                    }
                                }
                            });
                        };
                        if (values.memberNo.length > 0) {
                            saveFn();
                        }
                        else {
                            Ext.Ajax.request({
                                url: App.App_Info.BasePath + '/biomedical.do',
                                params: {
                                    account: values.account,
                                    method: 'isAccountExits'
                                },
                                success: function(response){
                                    var res = Ext.decode(response.responseText);
                                    if (res.success) {
                                        if (res.data == 'false') {
                                            saveFn();
                                        }
                                        else {
                                            alert('账号已存在，请更换账号。');
                                        }
                                    }
                                    else {
                                        alert('会员账号检查失败。');
                                    }
                                }
                            });
                        }
                    }
                    else {
                        alert('表单未通过验证，检查填写后再提交。');
                    }
                }
            }, {
                text: '关闭',
                handler: function(){
                    this.ownerCt.hide();
                }
            }]
        });
        Biomedical.MemberInfoWindow.superclass.initComponent.call(this, arguments);
    }
});
function GeneratorRecord(patientId,startDate,saveCallback){
	getAllDeptment();
	var win=Ext.getCmp('GeneratorRecord');
	if (!win) {
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';
		var form = new Ext.form.FormPanel({
			bodyStyle: 'padding:5px',
			border: false,
			autoHeight: true,
			labelAlign: 'right',
			labelWidth: 60,
			defaults: {
				xtype: 'textfield',
				anchor: '95%',
				allowBlank: false
			},
			items: [{
				xtype: 'datefield',
				fieldLabel: '起始日期',
				readOnly: true,
				format: 'Y-m-d',
				value: new Date(),
				name: 'startDate'
			}, {
				fieldLabel: '会员组别',
				name: 'groupName'
			}, {
				xtype: 'numberfield',
				fieldLabel: '随访周期',
				name: 'cycle'
			}, {
				fieldLabel: '检查项目',
				name: 'checkContent',
				allowBlank: true
			}, {
				fieldLabel: '备注',
				height: 60,
				name: 'memo',
				allowBlank: true
			}, {
				xtype:'hidden',
				name: 'patientId'
			}]
		});
		win = new Ext.Window({
			id: 'GeneratorRecord',
			title: '会员分组随访',
			width: 350,
			autoHeight: true,
			buttonAlign: 'center',
			closable: false,
			layout: 'fit',
			items: form,
			buttonAlign: 'center',
			buttons: [{
				text:'选择组别',
				handler:function(){
					var _returnObj=window.showModalDialog(App.App_Info.BasePath+'/module/biomedical/Group2.html','dialogWidth=400px;dialogHeight=600px');
					if(_returnObj){
						form.getForm().findField('groupName').setValue(_returnObj.name);
						form.getForm().findField('cycle').setValue(_returnObj.cycle);
						form.getForm().findField('checkContent').setValue(_returnObj.content);
					}
				}
			},{
				text: '保存',
				handler:function(){
					if(!form.getForm().isValid()){
						alert('表单未通过验证，检查完整后提交。')
					}else{
						Ext.Ajax.request({
							url:App.App_Info.BasePath + '/biomedical.do',
							params:{
								patientId:form.getForm().getValues().patientId,
								data:Ext.encode(form.getForm().getValues()),
								method:'saveGenerator'
							},
							success:function(response){
								var res=Ext.decode(response.responseText);
								if(res.success){
									alert('保存成功。');
									win.hide();
									if(saveCallback)
										saveCallback();
								}else{
									alert('保存失败。');
								}
							}
						});
					}
				}
			}, {
				text: '关闭',handler:function(){
					win.hide();
				}
			}]
		});
	}
	win.show();
	win.items.get(0).getForm().loadRecord(new Ext.data.Record({
		startDate:startDate,
		groupName:'',
		cycle:'',
		checkContent:'',
		memo:'',
		patientId:patientId
	}));
}

//查找所有的科室
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
			}
			deptcode = deptcodenameList[0][0];//科室编码动态改变加载对应的数据
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
			deptCode:deptcode
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




//                  xtype:'panel',
//					layout:'column',
//					border:false,
//					defaults:{border:false},
//					anchor:'100%',
//					items:[
//						{
//							columnWidth:.1,
//							listeners:{
//								render:function(){
//									var panel=this;
//									var img=Ext.DomHelper.append(this.body,{
//										tag:'img',
//										src:App.App_Info.BasePath+'/PUBLIC/images/sign.gif',
//										style:'cursor:pointer'
//									});
//									Ext.get(img).on('click',function(){
//										var obj=window.showModalDialog(App.App_Info.BasePath+'/PUBLIC/Modaldialog/DictSelect.html',{
//											title:'入会科室选择',
//											sql:'SELECT CODE AS svalue, NAME AS stext FROM SYS_ZD_UserBelong WHERE (PCODE = \'40068648-6\')'
//										},'dialogWidth=400px;dialogHeight=400px');
//										if(obj){
//											panel.ownerCt.items.get(0).items.get(0).setValue(obj.text);
//										}
//									});
//								}
//							}
//						}
//					]