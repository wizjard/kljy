var deptName;
var grounpName;

 //根据医生Id 获得当前责任科室和小组名称  by cheng jiangyu 2011-09-30
function getDoctorDeptmentAndGrounp(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentHISAction.do',
		params:{
			method:'findDoctorDeptAndGroupByDoctorId',
			doctorId:top.USER.id
		},
		sync:true,
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			deptName = list[0].deptName;
			grounpName = list[0].grounpName;
		}
	});	
}

Ext.onReady(function(){
    getDoctorDeptmentAndGrounp();  // 根据医生Id 获得当前责任科室和小组名称  by cheng jiangyu 2011-09-30
    layout();
});
function layout(){
    var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: App.App_Info.BasePath + '/biomedical.do'
        }),
        reader: new Ext.data.JsonReader({
            root: 'root',
            totalProperty: 'total'
        }, [{
            name: 'id'
        }, {
            name: 'patient'
        }, {
            name: 'startDate'
        }, {
            name: 'groupName'
        }, {
            name: 'cycle'
        }, {
            name: 'checkContent'
        }, {
            name: 'planDate'
        }, {
            name: 'noticeDate'
        }, {
            name: 'reserveDate'
        }, {
            name: 'smsContent'
        }, {
            name: 'memo'
        }])
    });
    ds.baseParams = {
        method: 'getAllOutpatientGenerators',
        search: ''
    };
    ds.load({
        params: {
            start: 0,
            limit: 20
        }
    });
    var sm = new Ext.grid.RowSelectionModel({
        singleSelect: false
    });
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
        header: '姓名',
        dataIndex: 'patient',
        renderer: function(value){
            return value.patientName;
        }
    }, {
        header: '病案号',
        dataIndex: 'patient',
        renderer: function(value){
            return value.patientNo;
        }
    }, {
        header: '当前疾病分组',
        dataIndex: 'groupName'
    }, {
        header: '随访起始日期',
        dataIndex: 'startDate',
        renderer: function(value){
            if (value) {
                try {
                    return new Date(value.time).format('Y/m/d')
                } 
                catch (err) {
                }
            }
        }
    }, {
        header: '计划日期',
        dataIndex: 'planDate',
        renderer: function(value){
            if (value) {
                try {
                    return new Date(value.time).format('Y/m/d')
                } 
                catch (err) {
                }
            }
        }
    }, {
        header: '剩余天数',
        dataIndex: 'planDate',
        renderer: function(value, cell, record){
            var start = record.get('startDate').time;
            var end = value.time;
            var current = new Date().getTime();
            if (current < end) {
                return parseInt((end - current) / (3600 * 1000 * 24)) + '天';
            }
        }
    }, {
        header: '通知日期',
        dataIndex: 'noticeDate',
        renderer: function(value){
            if (value) {
                try {
                    return new Date(value.time).format('Y/m/d')
                } 
                catch (err) {
                }
            }
        }
    }
//    , {
//        header: '预约日期',
//        dataIndex: 'reserveDate',
//        renderer: function(value){
//            if (value) {
//                try {
//                    return new Date(value.time).format('Y/m/d')
//                } 
//                catch (err) {
//                }
//            }
//        }
//    }
    ]);
    var SearchButton = Ext.extend(Ext.Button, {
        text: '查询',
        sw: null,
        toggleSw: function(){
            if (this.sw.isVisible()) {
                this.sw.hide();
            }
            else {
                this.sw.show();
                this.setSwPosition();
            }
        },
        /*Init Search Window*/
        initSw: function(){
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
                    items: [{
                        fieldLabel: '姓名/病案号',
                        name: 'name'
                    }, {
                        fieldLabel: '用户名',
                        name: 'groupName'
                    }, {
                        fieldLabel: '计划日期',
                        name: 'planDate'
                    }, {
                        xtype: 'panel',
                        border: false,
                        html: '<p style="font-size:10px;text-align:center">例：(2010/01/02)或(2010/01/01 2010/02/01)不包括括号。</p>'
                    }, {
                        fieldLabel: '起始日期',
                        name: 'startDate'
                    }, {
                        xtype: 'panel',
                        border: false,
                        html: '<p style="font-size:10px;text-align:center">例：(2010/01/02)或(2010/01/01 2010/02/01)不包括括号。</p>'
                    }]
                },
                buttons: [{
                    text: '查询',
                    handler: function(){
                        var values = o.sw.items.get(0).getForm().getValues();
                        var store = Ext.getCmp('grid').getStore();
                        store.baseParams.search = Ext.encode(values);
                        store.load({
                            params: {
                                start: 0,
                                limit: 20
                            }
                        });
                        o.sw.items.get(0).getForm().reset(); //用于清空框中的值 by cheng jiangyu 2011-9-30
                        o.sw.hide();
                    }
                }, {
                    text: '取消',
                    handler: function(){
                        o.sw.items.get(0).getForm().reset(); //用于清空框中的值 by cheng jiangyu 2011-9-30
                        o.sw.hide();
                    }
                }]
            });
        },
        /*ReSet Window's Position*/
        setSwPosition: function(){
            var el = this.el.dom;
            var pos = getElementPos(el);
            var oPos = [pos.x, pos.y];
            var oSize = {
                width: el.offsetWidth,
                height: el.offsetHeight
            };
            var bodySize = Ext.getBody().getSize();
            var sSize = this.sw.getSize();
            var x = oPos[0];
            var y = oPos[1] + oSize.height;
            if ((x + sSize.width) > bodySize.width) {
                x = x - sSize.width + oSize.width;
            }
            if ((y + sSize.height) > bodySize.height) {
                y = y - sSize.height - oSize.height;
            }
            this.sw.setPosition(x, y);
        }
    });
    var grid = new Ext.grid.GridPanel({
        id: 'grid',
        title: '随访提醒列表',
        sm: new Ext.grid.CheckboxSelectionModel({
            sigleSelect: true
        }),
        cm: cm,
        ds: ds,
        autoScroll: true,
        viewConfig: {
            forceFit: true
        },
        sm: sm,
        tbar: ['-', {
            text: '返回列表',
            handler: function(){
                var store = Ext.getCmp('grid').getStore();
                store.baseParams.search = '';
                store.load({
                    params: {
                        start: 0,
                        limit: 20
                    }
                });
            }
        }, '-', new SearchButton({
            handler: function(){
                if (!this.sw) {
                    this.initSw();
                }
                this.toggleSw();
            }
        })
        ,'-',{
						text:'基本信息',
						handler:function(){
							var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
							if(ss.length==0){
								alert('请先选择一个会员。');
								return;
							}
							App.util.addNewMainTab('/module/Patient/PatientInfo.html?id='+ss[0].get('patient').id,'病人基本信息');
						}
					}
        , '-', {
            text: '发送短信',
            handler: function(){
                 var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
				 if (ss.length == 0) {
				     alert('请至少选择一条记录。');
				     return;
				 }
				 var pid=ss[0].get('patient').id;
				 var send=function(id,ctx,callback){
				 	Ext.Ajax.request({
					 	url:App.App_Info.BasePath + '/biomedical.do',
					 	params:{
					 		method:'sendMsgByPatientId',
					 		id:id,
					 		appendix:'北京佑安医院'+deptName+grounpName, //加在短信内容后面的落款
					 		content:ctx
					 	},
					 	success:function(response){
					 		var resObj=Ext.decode(response.responseText);
					 		if(resObj.success){
					 			Ext.Msg.show({
					 				title:'成功',
					 				msg:resObj.msg,
					 				buttons:Ext.Msg.OK,
					 				fn:function(){callback(resObj)},
					 				icon:Ext.Msg.INFO
					 			});
					 		}else{
					 			Ext.Msg.show({
					 				title:'错误',
					 				msg:resObj.msg,
					 				buttons:Ext.Msg.OK,
					 				fn:function(){callback(resObj)},
					 				icon:Ext.Msg.ERROR
					 			});
					 		}
					 	}
					 });
				 }
				 new Ext.Window({
				 	title:'给【'+ss[0].get('patient').patientName+'】发送短信',
				 	closeAction:'close',
				 	width:400,
				 	height:200,
				 	autoHeight:true,
				 	layout:'anchor',
				 	items:[{
				 		xtype:'textarea',
				 		width:390,
				 		height:160,
				 		value:'患者您好,',
				 		style:'color:blue',
				 		emptyText:'<请在这里输入要发送的内容>',
				 		enableKeyEvents:true,
				 		listeners:{'keyup':function(){
				 			var label_ = this.ownerCt.findByType('label')[0];
				 			var textLength = this.getValue().length;
				 			label_.setText('当前字数:'+textLength+'字');
				 		}}
				 	},{
				 		layout:'column',
				 		frame:true,
				 		items:[{
					 			columnWidth:0.3,
						 		xtype:'label',
						 		labelWidth:100,
						 		text:'当前字数:5字'
						 	},{
						 		columnWidth:0.3,
						 		xtype:'label',
						 		width:20,
						 		text:'短信条数:1条'
						 	},{
						 		columnWidth:0.4,
						 		xtype:'label',
						 		width:20
						 		//text:'短信总字数上限:100字'
					 	}]
				 	}],
				 	buttonAlign:'center',
				 	buttons:[
				 		{
				 			text:'发送',
				 			handler:function(){
				 			   if(ss[0].get('patient').mobilePhone==''||ss[0].get('patient').mobilePhone!== null||(ss[0].get('patient').mobilePhone!=''&&!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(ss[0].get('patient').mobilePhone)))){  //如果没有登记手机号码的病人不为空
						 			       alert("会员"+ss[0].get('patient').patientName+"手机号码为空或格式不正确");
						 			       return;
					 			      }
				 			
				 				var win=this.ownerCt;
				 				var value=win.items.get(0).getValue();
				 				var headAndAppendix = '北京佑安医院'+deptName+grounpName+value;  //自动的短信头和落款 加上短信正文
				 			     if(value&&value.length>5){ 
				 			        if(Math.ceil(headAndAppendix.length/64)>1){   //看发送的短信字数是否超过一条短信
						 			       if(confirm('您确定发送吗？该条短信将分'+Math.ceil(headAndAppendix.length/64)+'条发送')){
						 			           send(pid,value,function(data){
					 							   win.close();
					 					  	   });
						 			       }else{
						 			         return;
						 			       }
				 			         }else{
				 			       	    send(pid,value,function(data){
				 							   win.close();
				 					 });
				 				   }
				 			    }else{
				 					 alert('信息正文不能为空。');
				 				 }
				 			}
				 		}
				 	]
				 }).show();
            }
        }],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: ds,
            displayInfo: true,
            displayMsg: '显示第<font color="red"> {0} </font>条' +
            '到<font color="red"> {1} </font>条记录，' +
            '一共<font color="red"> {2} </font>条',
            emptyMsg: "没有记录"
        })
    });
    new Ext.Viewport({
        layout: 'fit',
        items: grid
    });
}

function noticeMember(){
    var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
    if (ss.length == 0) {
        alert('请至少选择一条记录。');
        return;
    }
    var win = Ext.getCmp('NoticeMemberWindow');
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
                xtype: 'datefield',
                anchor: '92%'
            },
            items: [{
                fieldLabel: '计划日期',
                readOnly: true,
                format: 'Y-m-d',
                name: 'planDate',
                allowBlank: false
            },{
                fieldLabel: '通知日期',
                readOnly: true,
                format: 'Y-m-d',
                name: 'noticeDate',
                allowBlank: false
            }, {
                fieldLabel: '预约日期',
                readOnly: true,
                format: 'Y-m-d',
                name: 'reserveDate'
            },{
				xtype: 'textarea',
                name: 'smsContent',
				fieldLabel:'短信内容',
                height: 80
			}, {
                xtype: 'textarea',
                name: 'memo',
				fieldLabel:'备注',
                height: 60
            },{
				xtype:'hidden',
				name:'id'
			}]
        });
        win = new Ext.Window({
            title: '随访通知',
            width: 450,
            autoHeight: true,
            buttonAlign: 'center',
            closable: false,
            layout: 'fit',
            items: form,
            buttons: [{
                text: '保存',
                handler: function(){
                    if(!form.getForm().isValid()){
						alert('表单未通过验证，检查后再提交。');
						return;
					}
					Ext.Ajax.request({
						url: App.App_Info.BasePath + '/biomedical.do',
						params:Ext.apply(form.getForm().getValues(),{
							method:'noticeUpdate'
						}),
						success:function(response){
							var res=Ext.decode(response.responseText);
							if(res.success){
								Ext.getCmp('grid').getStore().reload();
								alert('保存成功。');
								win.hide();
							}else{
								alert('保存失败。');
							}
						}
					});
                }
            }, {
                text: '关闭',
                handler: function(){
                    this.ownerCt.hide();
                }
            }]
        });
    }
	win.show();
	var id=[];
	Ext.each(ss,function(){
		id.push(this.get('id'));
	});
	var obj={
		id:id.join(','),
		planDate:new Date(ss[0].get('planDate').time),
		smsContent:ss[0].get('smsContent'),
		memo:ss[0].get('memo')
	};
	try{
		obj.noticeDate=new Date(ss[0].get('noticeDate').time);
	}catch(err){
		obj.noticeDate='';
	}
	try{
		obj.reserveDate=new Date(ss[0].get('reserveDate').time);
	}catch(err){
		obj.reserveDate='';
	}
	win.items.get(0).getForm().loadRecord(new Ext.data.Record(obj));
}
