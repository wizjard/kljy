var url = {};
url.baseUrl = App.App_Info.BasePath + '/GradingDiagAction.do?method=';
url.getChildren = url.baseUrl + 'getChildren';
url.saveDiagItem = url.baseUrl + 'saveDiagItem';
url.isCodeExist = url.baseUrl + 'isCodeExist';
url.deleteDiagItem = url.baseUrl + 'deleteDiagItem';
Ext.ns('GradingDiag.Admin.Tree');
GradingDiag.Admin.Tree = Ext.extend(Ext.tree.TreePanel, {
    initComponent: function(){
        var tree = this;
        var loader = new Ext.tree.TreeLoader({
            dataUrl: url.getChildren
        });
        loader.on('beforeload', function(loader, node){
            loader.baseParams.code = node.id;
        });
        var root = new Ext.tree.AsyncTreeNode({
            id: 'root',
            text: '肝病十二级分类诊断编码表'
        });
        var contextmenu = new Ext.menu.Menu({
            items: [{
                text: '新增',
                handler: tree.addNode.createDelegate(tree)
            }, {
                text: '编辑',
                handler: tree.editNode.createDelegate(tree)
            }, {
                text: '删除',
                handler: tree.deleteNode.createDelegate(tree)
            }]
        });
        Ext.apply(this, {
            root: root,
            rootVisible: true,
            border: false,
            autoScroll: true,
            containerScroll: true,
            loader: loader,
            bodyStyle: 'margin:10px'
        });
        this.on("contextmenu", function(node, e){
            e.preventDefault();
            node.select();
            contextmenu.showAt(e.getXY());
           
        });
        GradingDiag.Admin.Tree.superclass.initComponent.call(this, arguments);
    },
    getSelectNode: function(){
        return this.getSelectionModel().getSelectedNode();
    },
    addNode: function(){
        var node = this.getSelectNode();
        showFormWindow(node, false);
    },
    editNode: function(){
        var node = this.getSelectNode();
        if (node.id == 'root') {
            alert('不能编辑根节点。');
            return;
        }
        showFormWindow(node, true);
    },
    deleteNode: function(){
        var tree = this;
        var node = tree.getSelectNode();
		var parent=node.parentNode;
        if (node.id == 'root') {
            alert('不能删除根节点。');
            return;
        }
        if (!confirm('确定要删除节点？')) 
            return;
        Ext.Ajax.request({
            url: url.deleteDiagItem,
            params: {
                code: node.id
            },
            success: function(response){
                if (Ext.decode(response.responseText).success) {
                    tree.getLoader().load(parent);
					parent.expand();
                }
                else {
                    alert('节点删除失败。');
                }
            }
        });
    }
});
function showFormWindow(node, editable){
    var win = Ext.getCmp('FormWindow');
    if (!win) {
        win = createFormWindow();
    }
    win.show();
    win.editable = editable;
    win.snode = node;
    if (editable) {
        win.items.get(0).getForm().loadRecord(new Ext.data.Record(Ext.apply(node.attributes.data, {
            parent: node.parentNode.id
        })));
    }
    else {
        win.items.get(0).getForm().reset();
        win.items.get(0).getForm().findField('parent').setValue(node.id);
    }
}

function createFormWindow(){
    var form = new Ext.form.FormPanel({
        bodyStyle: 'padding:20px 0 20px 0',
        border: false,
        autoHeight: true,
        labelAlign: 'right',
        labelWidth: 70,
        defaults: {
            xtype: 'textfield',
            anchor: '95%'
        },
        items: [{
            fieldLabel: '上级编码',
            name: 'parent',
            readOnly: true,
            allowBlank: false
        }, {
            fieldLabel: '唯一编码',
            name: 'code',
            allowBlank: false
        }, {
            fieldLabel: '叶子节点',
            name: 'leaf',
            allowBlank: false,
            value: 0
        }, {
            fieldLabel: '显示名称',
            name: 'nodeText',
            allowBlank: false
        }, {
            fieldLabel: '打印名称',
            name: 'printText'
        }, {
            fieldLabel: '多行关联',
            name: 'multiLine'
        }, {
            xtype: 'numberfield',
            fieldLabel: '选择事件',
            allowBlank: false,
			value:0,
            name: 'checkAction'
        }, {
            xtype: 'numberfield',
            fieldLabel: '序号',
            allowBlank: false,
			value:0,
            name: 'orderNo'
        },{
        	xtype:'textarea',
        	height:200,
        	width:400,
            fieldLabel: '诊断标准',
            name: 'standard'
        }]
    });
    return new Ext.Window({
        id: 'FormWindow',
        title: '诊断编辑',
        width: 500,
        autoHeight: true,
        frame: true,
        closable: false,
        items: form,
        buttonAlign: 'center',
        buttons: [{
            text: '保存',
            handler: function(){
                var win = Ext.getCmp('FormWindow');
                var form = win.items.get(0).getForm();
                if (!form.isValid()) {
                    alert('表单未填写完整，不能保存。');
                    return;
                }
                var saveFn = function(){
                    Ext.Ajax.request({
                        url: url.saveDiagItem,
                        params: {
                            data: Ext.encode(form.getValues())
                        },
                        success: function(response){
                            if (Ext.decode(response.responseText).success) {
                                var loader = Ext.getCmp('tree').getLoader();
                                if (win.editable) {
                                    var temp = win.snode.parentNode;
                                    loader.load(temp);
                                    temp.expand();
                                }
                                else {
                                    loader.load(win.snode);
                                    win.snode.expand();
                                }
                                win.hide();
                            }
                            else {
                                alert('保存失败。');
                            }
                        }
                    });
                };
                if (!win.editable) {
                    Ext.Ajax.request({
                        url: url.isCodeExist,
                        params: {
                            code: form.getValues().code
                        },
                        success: function(response){
                            if (Ext.decode(response.responseText).data == 'true') {
                                alert('唯一编辑已存在，请更换编码。');
                            }
                            else {
                                saveFn();
                            }
                        }
                    });
                }
                else {
                    saveFn();
                }
            }
        }, {
            text: '关闭',
            handler: function(){
                Ext.getCmp('FormWindow').hide();
            }
        }]
    });
}

Ext.onReady(function(){
    var tree = new GradingDiag.Admin.Tree({
        id: 'tree'
    });
    tree.render(Ext.getBody());
    tree.root.expand();
});
