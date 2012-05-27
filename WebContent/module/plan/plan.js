var paId = App.util.getHtmlParameters('paId');
Ext.onReady(function(){
    layout();
});
function layout(){
    var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: App.App_Info.BasePath + '/plan.do'
        }),
        reader: new Ext.data.JsonReader({
            root: 'root',
            totalProperty: 'total'
        }, [{
            name: 'id'
        }, {
            name: 'planTime'
        }, {
            name: 'patient'
        },  {
            name: 'beginDate'
        },/* {
            name: 'circle'
        },*/ {
            name: 'state'
        }/*, {
            name: 'enterDate'
        }, {
            name: 'deptname'
        }, {
            name: 'teamname'
        }*/])
    });
    ds.baseParams = {
        method: 'getAllPlans',
        search: '',
        login:'user',
        paId:paId
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
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),{
        header: '随访次数',
        dataIndex: 'planTime',
        width:30,
        renderer: function(value){
            return value;
        }
    }, {
        header: '患者编号',
        dataIndex: 'patient',
        renderer: function(value){
            return value == null ? "" : value.patientNo;
        }
    }, {
        header: '患者姓名',
        dataIndex: 'patient',
        renderer: function(value){
            return value == null ? "" : value.patientName;
        }
    }, 
    {
        header: '患者性别',
        dataIndex: 'patient',
        renderer: function(value){
    	    if(value == null) {
    	       return "";
    	    }
 
            return  value.gender == 1 ? "男" : "女";
        }
    }, {
        header: '随访起始日期',
        dataIndex: 'beginDate',
        renderer: function(value){
            if (value) {
                try {
                    return new Date(value == null ? "" : value.time).format('Y/m/d')
                } 
                catch (err) {
                }
            }
        }
    }, /*{
        header: '随访周期',
        dataIndex: 'circle',
        renderer: function(value){
            return value;
        }
    },*/ {
        header: '随访状态',
        dataIndex: 'state',
        renderer: function(value, cell, record){
            if(value == 1) {
               return '<font color="#ee9a00">随访执行中</font>';   
            }
            else if(value == 2) {
               return '<font color="green">结果已关联</font>';
            }
            else {
            	return '<font color="red">随访计划中</font>';
            }
        }
    }/*, {
        header: '入会日期',
        dataIndex: 'enterDate',
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
        header: '当前责任科室',
        dataIndex: 'deptname',
        renderer: function(value){
            return value;
        }
    }, {
        header: '当前责任小组',
        dataIndex: 'teamname',
        renderer: function(value){
            return value;
        }
    }*/]);
    
    var grid = new Ext.grid.GridPanel({
        id: 'grid',
        title: '随访计划列表',
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
        }
        ,'-',{
						text:'基本信息',
						handler:function(){
							
							App.util.addNewMainTab('/module/Patient/PatientInfo.html?id='+paId,'病人基本信息');
						}
					}
        ,'-',{
			text:'日历模式查看随访计划',
			handler:function(){
//				var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
//				if(ss.length==0){
//					alert('请先选择一条随访计划。');
//					return;
//				}
				
			App.util.addNewMainTab('/module/plan/showcalendar.html?paId='+paId,'日历模式查看随访计划');
			}
		},'-',{
			text:'删除随访计划',
			handler:function(){
				var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
				if(ss.length==0){
					alert('请先选择一条随访计划。');
					return;
				}
				if(!confirm('确定要删除选中记录?'))return;
				Ext.Ajax.request({
					url:App.App_Info.BasePath+'/plan.do',
					params:{
						method:'deletePlan',
						id:ss[0].get('id')
					},
					success:function(_response){
						if(Ext.decode(_response.responseText).success){
							alert('删除成功。');
							Ext.getCmp('grid').getStore().reload();
						}else{
							alert('删除失败。');
						}
					}
				});
			}

		},'-',{
			text:'编辑随访计划',
			handler:function(){
				var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
				if(ss.length==0){
					alert('请先选择一条记录。');
					return;
				}
				App.util.addNewMainTab('/module/plan/addPlanItem.html?planId='+ss[0].get('id'),'随访计划信息');
			}
		},'-',{
			text:'增加随访计划',
			handler:function(){
			if(!confirm('确定要新增随访计划?'))return;
				App.util.addNewMainTab('/module/plan/addPlanItem.html?pid='+paId,'增加随访计划');
			}
		},'->',{
			xtype:'tbseparator'
		  },{
				xtype:'textfield',
				width:100,
				emptyText:'随访次数',
				id:'order-name'
			  },
			  {
					xtype:'textfield',
					width:200,
					emptyText:'随访起始时间(2010-08-08)',
					id:'beginDate'
				  },{
			text:'搜索随访',
			tooltip:'搜索随访',
			handler:function(){
				var _val2=Ext.getCmp('order-name').getValue().trim();
				var _valDate=Ext.getCmp('beginDate').getValue().trim();
				
				var _store=Ext.getCmp('grid').getStore();
				_store.baseParams = {
						method: 'getAllPlans',
				        search: '',
				        login:'user',
				        paId:paId,
						planTime:_val2,
						beginDate:_valDate
				    };
				_store.load({
				        params: {
				            start: 0,
				            limit: 20
				        }
				    });

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
