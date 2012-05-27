//申请咨询医生
function ask(){
    var win = Ext.getCmp('AskForm');
    if (!win) {
        win = new Member.AskForm({
            id: 'AskForm'
        });
    }
    win.show();
    win.items.get(0).getForm().loadRecord(new Ext.data.Record({
        ask: '',
        healthRecordId: '',
        ward: currentWard
    }));
}

//查看医生回复
function answer(){
    var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
    if (ss.length == 0) {
        alert('请选择一条记录。');
        return;
    }
    var win = Ext.getCmp('AnswerForm');
    if (!win) {
        win = new Member.AnswerForm({
            id: 'AnswerForm'
        });
    }
    win.show();
    win.items.get(0).getForm().findField('answer').setValue(ss[0].get('answer'));
}

Ext.ns('Member.AskForm');
Member.AskForm = Ext.extend(Ext.Window, {
    initComponent: function(){
        var form = new Ext.form.FormPanel({
            bodyStyle: 'padding-top:5px',
            labelWidth: 80,
            labelAlign: 'right',
            autoHeight: true,
            border: false,
            defaults: {
                anchor: '95%'
            },
            items: [{
                xtype: 'textarea',
                fieldLabel: '申请内容',
                name: 'ask',
                height: 180
            }, {
                xtype: 'textfield',
                fieldLabel: '<!--a href="javascript:RelWard()"-->提交科室<!--/a-->',
                name: 'ward',
                readOnly: true
            }, {
                xtype: 'textfield',
                fieldLabel: '<a href="javascript:RelHealthRecord()">关联健康记录</a>',
                name: 'healthRecordId',
                readOnly: true
            }]
        });
        Ext.apply(this, {
            title: '网上咨询医生',
            closable: false,
            width: 450,
            autoHeight: true,
            buttonAlign: 'center',
            frame: true,
            layout: 'form',
            layout: 'fit',
            items: form,
            buttons: []
        });
        var btn1 = new Ext.Button({
            text: '提交',
            handler: function(){
                var values = form.getForm().getValues();
                if (!values.ask || values.ask.trim().length == 0) {
                    alert('申请内容不能为空。');
                    return;
                }
                if (!values.ward || values.ward.trim().length == 0) {
                    alert('申请科室不能为空。');
                    return;
                }
                Ext.Ajax.request({
                    url: App.App_Info.BasePath + '/module/biomedical/member.do?method=saveMemberMsg',
                    params: values,
                    success: function(response){
                        var res = Ext.decode(response.responseText);
                        if (res.success) {
                            alert('提交成功。');
                            Ext.getCmp('AskForm').hide();
                            Ext.getCmp('grid').getStore().reload();
                        }
                        else {
                            alert('提交失败。');
                        }
                    }
                });
            }
        });
        this.buttons.push(btn1);
        var btn2 = new Ext.Button({
            text: '取消',
            handler: function(){
                this.ownerCt.hide();
            }
        });
        this.buttons.push(btn2);
        Member.AskForm.superclass.initComponent.call(this, arguments);
    }
});
Ext.ns('Member.AnswerForm');
Member.AnswerForm = Ext.extend(Ext.Window, {
    initComponent: function(){
        var form = new Ext.form.FormPanel({
            bodyStyle: 'padding-top:5px',
            labelWidth: 60,
            labelAlign: 'right',
            autoHeight: true,
            border: false,
            defaults: {
                anchor: '95%'
            },
            items: [{
                xtype: 'textarea',
                fieldLabel: '回复内容',
                name: 'answer',
				readOnly:true,
                height: 180
            }]
        });
        Ext.apply(this, {
            title: '医生回复',
            closable: false,
            width: 450,
            autoHeight: true,
            buttonAlign: 'center',
            frame: true,
            layout: 'form',
            layout: 'fit',
            items: form,
            buttons: []
        });
        var btn2 = new Ext.Button({
            text: '关闭',
            handler: function(){
                this.ownerCt.hide();
            }
        });
        this.buttons.push(btn2);
        Member.AnswerForm.superclass.initComponent.call(this, arguments);
    }
});
function RelWard(){
    var obj = window.showModalDialog(App.App_Info.BasePath + '/PUBLIC/Modaldialog/DictSelect.html', {
        sql: 'SELECT CODE AS svalue, NAME AS stext FROM SYS_ZD_UserBelong WHERE (PCODE = \'40068648-6\') ORDER BY CODE'
    }, 'dialogWidth=350px,dialogHeight=400px');
    if (obj) {
        Ext.getCmp('AskForm').items.get(0).getForm().findField('ward').setValue(obj.text);
    }
}

function RelHealthRecord(){
    var win = Ext.getCmp('rel-health-record');
    if (!win) {
        var sm = new Ext.grid.CheckboxSelectionModel({
            singleSelect: true
        });
        var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
            header: '登记日期',
            width: 150,
            dataIndex: 'registDate'
        }, {
            header: '症状',
            id: 'symptomcol',
            dataIndex: 'symptom'
        }, {
            header: '病情演变',
            width: 150,
            dataIndex: 'yanbian'
        }]);
        var store = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: App.App_Info.BasePath + '/module/biomedical/member.do?method=getMyHealthRecords'
            }),
            reader: new Ext.data.JsonReader({
                root: 'root',
                totalPropertity: 'total'
            }, [{
                name: 'id'
            }, {
                name: 'registDate'
            }, {
                name: 'symptom'
            }, {
                name: 'shiliang'
            }, {
                name: 'shuimian'
            }, {
                name: 'tizhong'
            }, {
                name: 'jingshen'
            }, {
                name: 'niaoliang'
            }, {
                name: 'dabian'
            }, {
                name: 'yanbian'
            }, {
                name: 'zhiliao'
            }, {
                name: 'haozhuan'
            }, {
                name: 'jiuzhen'
            }])
        });
        store.load({
            params: {
                start: 0,
                limit: 20
            }
        });
        var _grid = new Ext.grid.GridPanel({
            id: 'grid',
            ds: store,
            cm: cm,
            sm: sm,
            height: 350,
            autoExpandColumn: 'symptomcol',
            border: false,
            viewConfig: {
                forceFit: true
            },
            bbar: new Ext.PagingToolbar({
                pageSize: 10,
                store: store,
                displayInfo: true,
                displayMsg: '显示第<font color="red"> {0} </font>条' +
                '到<font color="red"> {1} </font>条记录，' +
                '一共<font color="red"> {2} </font>条',
                emptyMsg: "没有记录"
            })
        });
        win = new Ext.Window({
            id: 'rel-health-record',
            title: '关联健康记录',
            closable: false,
            width: 450,
            autoHeight: true,
            buttonAlign: 'center',
            frame: true,
            layout: 'form',
            layout: 'fit',
            items: _grid,
            buttons: [{
                text: '确定',
                handler: function(){
                    var ss = _grid.getSelectionModel().getSelections();
                    if (ss.length == 0) {
                        alert('未选择健康记录。');
                        return;
                    }
                    Ext.getCmp('AskForm').items.get(0).getForm().findField('healthRecordId').setValue(ss[0].get('id'));
                    win.hide();
                }
            }, {
                text: '取消',
                handler: function(){
                    this.ownerCt.hide();
                }
            }]
        });
    }
    win.show();
}
