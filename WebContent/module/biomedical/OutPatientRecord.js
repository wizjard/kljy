var recordId = App.util.getHtmlParameters('recordId');
var patientId = App.util.getHtmlParameters('patientId');
Ext.onReady(function(){
    Ext.Ajax.request({
        url: App.App_Info.BasePath + '/biomedical.do',
        params: {
            method: 'getOutPatientRecord',
            recordId: recordId
        },
        success: function(response){
            var res = Ext.decode(response.responseText);
            if (res.success) {
                layout(Ext.decode(res.data));
            }
            else {
                alert('获取记录失败。');
            }
        }
    });
});
function layout(data){
    var form1 = new Ext.form.FormPanel({
        title: '本次随访方案',
        bodyStyle: 'padding:5px',
        border: false,
        frame: true,
        autoHeight: true,
        labelAlign: 'right',
        labelWidth: 60,
        width: 600,
        defaults: {
            xtype: 'textfield',
            anchor: '95%',
            allowBlank: false
        },
        items: [{
            xtype: 'hidden',
            name: 'id'
        }, {
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
        }],
        buttonAlign: 'center',
        buttons: [{
            text: '修改组别',
            handler: function(){
                var _returnObj = window.showModalDialog('Group2.html', 'dialogWidth=400px;dialogHeight=600px');
                if (_returnObj) {
                    form1.getForm().findField('groupName').setValue(_returnObj.name);
                    form1.getForm().findField('cycle').setValue(_returnObj.cycle);
                    form1.getForm().findField('checkContent').setValue(_returnObj.content);
                }
            }
        }, {
            text: '保存',
            handler: function(){
                if (!form1.getForm().isValid()) {
                    alert('表单未通过验证。');
                    return;
                }
                Ext.Ajax.request({
                    url: App.App_Info.BasePath + '/biomedical.do',
                    params: {
                        method: 'updateGenerator',
                        data: Ext.encode(form1.getForm().getValues())
                    },
                    success: function(response){
                        var res = Ext.decode(response.responseText);
                        if (res.success) {
                            alert('保存成功。');
                        }
                        else {
                            alert('保存失败。');
                        }
                    }
                });
            }
        }, {
            text: '返回',
            handler: function(){
                history.go(-1);
            }
        }]
    });
    form1.on('render', function(){
        this.getForm().loadRecord(new Ext.data.Record(data.generator1));
    });
    form1.render(Ext.get('div1'));
    var form2 = new Ext.form.FormPanel({
        title: '本次门诊随访病历记录',
        bodyStyle: 'padding:5px',
        border: false,
        frame: true,
        autoHeight: true,
        labelAlign: 'right',
        labelWidth: 60,
        width: 600,
        defaults: {
            xtype: 'textarea',
            anchor: '95%'
        },
        items: [{
            xtype: 'hidden',
            name: 'id'
        }, {
            xtype: 'numberfield',
            fieldLabel: '随访次数',
            name: 'times',
            allowBlank: false,
            listeners:{
        		change:function(_this, _n, _o){
        			Ext.get('dyaTimes').update(_n);
        		}
        	}
        }, {
            xtype: 'datefield',
            fieldLabel: '来访日期',
            readOnly: true,
            format: 'Y-m-d',
            value: new Date(),
            name: 'comeDate',
            allowBlank: false
        },{
                	fieldLabel: '预留标本',
                    xtype: 'combo',
                    mode: 'local',
                    store: new Ext.data.SimpleStore({
                        data: [['否',0], ['是',1]],
                        fields: ['text','value']
                    }),
                    anchor:'30%',
                    displayField: 'text',
                    valueField: 'value',
                    triggerAction: 'all',
                    readOnly: true,
                    hiddenName: 'biaoben'
                }, {
            fieldLabel: '主诉',
            height: 60,
            name: 'zhusu'
        }, {
            fieldLabel: '现病史',
            height: 60,
            name: 'xianbingshi'
        }, {
            fieldLabel: '其它病史',
            height: 60,
            name: 'qitabingshi'
        }, {
            fieldLabel: '体格检查',
            height: 60,
            name: 'tige'
        }, {
            fieldLabel: '辅助检查',
            height: 60,
            name: 'fuzhu'
        }, {
            fieldLabel: '初步诊断',
            height: 40,
            name: 'zhenduan'
        }, {
            fieldLabel: '治疗建议',
            height: 60,
            name: 'jianyi'
        }, {
            fieldLabel: '备注',
            height: 60,
            name: 'beizhu'
        }, {
            xtype: 'textfield',
            fieldLabel: '就诊医生',
            name: 'yisheng'
        }],
        buttonAlign: 'center',
        buttons: [{
            text: '保存',
            handler: function(){
                if (!form2.getForm().isValid()) {
                    alert('表单未通过验证。');
                    return;
                }
                Ext.Ajax.request({
                    url: App.App_Info.BasePath + '/biomedical.do',
                    params: {
                        method: 'updateOutpatientRecord',
                        data: Ext.encode(form2.getForm().getValues())
                    },
                    success: function(response){
                        var res = Ext.decode(response.responseText);
                        if (res.success) {
                            alert('保存成功。');
                        }
                        else {
                            alert('保存失败。');
                        }
                    }
                });
            }
        }, {
            text: '返回',
            handler: function(){
                history.go(-1);
            }
        }]
    });
    form2.on('render', function(){
        this.getForm().loadRecord(new Ext.data.Record(data));
        setRecordTitle(form2);
    });
    form2.render(Ext.get('div2'));
    var form3 = new Ext.form.FormPanel({
        title: '设置下次随访',
        bodyStyle: 'padding:5px',
        border: false,
        frame: true,
        autoHeight: true,
        labelAlign: 'right',
        labelWidth: 60,
        width: 600,
        defaults: {
            xtype: 'textfield',
            anchor: '95%',
            allowBlank: false
        },
        items: [{
            xtype: 'hidden',
            name: 'id'
        }, {
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
        }],
        buttonAlign: 'center',
        buttons: [{
            text: '修改组别',
            handler: function(){
                var _returnObj = window.showModalDialog('Group2.html', 'dialogWidth=400px;dialogHeight=600px');
                if (_returnObj) {
                    form3.getForm().findField('groupName').setValue(_returnObj.name);
                    form3.getForm().findField('cycle').setValue(_returnObj.cycle);
                    form3.getForm().findField('checkContent').setValue(_returnObj.content);
                }
            }
        }, {
            text: '保存',
            handler: function(){
                if (!form3.getForm().isValid()) {
                    alert('表单未通过验证。');
                    return;
                }
                var method = '';
                var data = form3.getForm().getValues();
                if (data.id && data.id > 0) {
                    method = 'updateGenerator';
                }
                else {
                    method = 'saveGenerator';
                }
                Ext.Ajax.request({
                    url: App.App_Info.BasePath + '/biomedical.do',
                    params: {
                        method: method,
                        data: Ext.encode(data),
                        patientId: patientId,
                        recordId: recordId
                    },
                    success: function(response){
                        var res = Ext.decode(response.responseText);
                        if (res.success) {
                            if (method == 'saveGenerator') {
                                form3.getForm().findField('id').setValue(res.data);
                            }
                            alert('保存成功。');
                        }
                        else {
                            alert('保存失败。');
                        }
                    }
                });
            }
        }, {
            text: '返回',
            handler: function(){
                history.go(-1);
            }
        }]
    });
    form3.on('render', function(){
        if (!data.generator2 && data.generator1) {
            data.generator2 = {
                groupName: data.generator1.groupName,
                cycle: 3
            };
        }
        this.getForm().loadRecord(new Ext.data.Record(data.generator2));
    });
    form3.render(Ext.get('div3'));
}

function setRecordTitle(form){
	Ext.Ajax.request({
		url:App.App_Info.BasePath + '/biomedical.do',
		params:{
			method:'getPatientInfo',
			id:patientId
		},
		success:function(response){
			var res = Ext.decode(response.responseText);
			if(res.success){
				form.setTitle('<span style="color:red">本次门诊随访病历记录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' 
						+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' 
						+ res.data + '第&nbsp;<span id="dyaTimes">' + form.getForm().findField('times').getValue() 
						+ '</span>&nbsp;次随访记录</span>');
			}else{
				alert('获取病人信息失败');
			}
		}
	});
}
