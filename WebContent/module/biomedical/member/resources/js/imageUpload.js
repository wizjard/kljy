Ext.onReady(function(){
    layout();
});
function layout(){
    var sm = new Ext.grid.CheckboxSelectionModel({
        singleSelect: true
    });
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
        header: '上传日期',
        dataIndex: 'uploadTime'
    }, {
        header: '文件名称',
        dataIndex: 'fileName'
    }, {
        header: '文件大小',
        dataIndex: 'fileSize'
    }, {
        header: '备注',
        dataIndex: 'memo'
    }]);
    var store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: App.App_Info.BasePath + '/module/biomedical/member.do?method=getMyUploadFiles'
        }),
        reader: new Ext.data.JsonReader({
            root: 'root',
            totalPropertity: 'total'
        }, [{
            name: 'id'
        }, {
            name: 'uploadTime'
        }, {
            name: 'fileName'
        }, {
            name: 'fileSize'
        }, {
            name: 'memo'
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
        title: '文件列表',
        ds: store,
        cm: cm,
        sm: sm,
        height: 350,
        border: false,
        viewConfig: {
            forceFit: true
        },
        tbar: ['-', {
            text: '上传新文件',
            handler: function(){
                var dialog = new Ext.ux.UploadDialog.Dialog({
                    title: '上传文件',
                    url: App.App_Info.BasePath + '/module/biomedical/member.do?method=uploadFiles', //这里我用struts2做后台处理
                    post_var_name: 'file',//这里是自己定义的，默认的名字叫file
                    width: 450,
                    height: 300,
                    minWidth: 450,
                    minHeight: 300,
                    draggable: true,
                    resizable: true,
                    constraintoviewport: true,
                    modal: true,
                    reset_on_hide: false,
                    allow_close_on_upload: true, //关闭上传窗口是否仍然上传文件 
                    upload_autostart: false
                });
                dialog.show();
                dialog.on('uploadsuccess', function(dialog, filename, resp_data, record){
                });
                dialog.on('uploadfailed', function(dialog, filename, resp_data, record){
                });
                dialog.on('uploaderror', function(dialog, filename, resp_data, record){
                });
                dialog.on('uploadcomplete', function(dialog){
                    dialog.hide();
                    Ext.getCmp('grid').getStore().reload();
                });
            }
        }, '-', {
            text: '删除旧文件',
            handler: function(){
                var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
                if (ss.length != 1) {
                    alert('请选择一条记录。');
                    return;
                }
                if (!confirm('确定要删除选中的文件？')) {
                    return;
                }
                Ext.Ajax.request({
                    url: App.App_Info.BasePath + '/module/biomedical/member.do?method=deleteUploadFile',
                    params: {
                        id: ss[0].get('id')
                    },
                    success: function(response){
                        if (Ext.decode(response.responseText).success) {
                            alert('删除成功。');
                            Ext.getCmp('grid').getStore().remove(ss[0]);
                        }
                        else {
                            alert('删除失败。');
                        }
                    }
                });
            }
        }, '-', {
            text: '添加备注信息',
            handler: function(){
                var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
                if (ss.length != 1) {
                    alert('请选择一条记录。');
                    return;
                }
                var win = Ext.getCmp('Memo-win');
                if (!win) {
                    win = new Ext.Window({
                        title: '添加文件备注信息',
                        closable: false,
                        width: 450,
                        autoHeight: true,
                        buttonAlign: 'center',
                        frame: true,
                        layout: 'fit',
                        items: {
                            xtype: 'form',
                            bodyStyle: 'padding-top:5px',
                            labelWidth: 30,
                            labelAlign: 'right',
                            autoHeight: true,
                            border: false,
                            items: [{
                                xtype: 'textarea',
                                height: 300,
                                fieldLabel: '备注',
                                name: 'memo',
                                anchor: '98%'
                            }, {
                                xtype: 'hidden',
                                name: 'id'
                            }]
                        },
                        buttons: [{
                            text: '保存',
                            handler: function(){
                                Ext.Ajax.request({
                                    url: App.App_Info.BasePath + '/module/biomedical/member.do?method=uploadFileMemo',
                                    params: win.items.get(0).getForm().getValues(),
                                    success: function(response){
                                        if (Ext.decode(response.responseText).success) {
                                            ss[0].set('memo', win.items.get(0).getForm().getValues().memo);
                                            win.hide();
                                        }
                                        else {
                                            alert('修改失败。');
                                        }
                                    }
                                });
                            }
                        }, {
                            text: '关闭',
                            handler: function(){
                                win.hide();
                            }
                        }]
                    });
                }
                win.show();
                win.items.get(0).getForm().loadRecord(new Ext.data.Record({
                    id: ss[0].get('id'),
                    memo: ss[0].get('memo')
                }));
            }
        }, '-'],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: store,
            displayInfo: true,
            displayMsg: '显示第<font color="red"> {0} </font>条' +
            '到<font color="red"> {1} </font>条记录，' +
            '一共<font color="red"> {2} </font>条',
            emptyMsg: "没有记录"
        })
    });
    new Ext.Viewport({
        layout: 'fit',
        items: _grid
    });
}
