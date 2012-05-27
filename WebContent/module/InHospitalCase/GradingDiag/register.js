var url = {};
url.baseUrl = App.App_Info.BasePath + '/GradingDiagAction.do?method=';
url.getChildren = url.baseUrl + 'getChildren';
Ext.ns('GradingDiag.Register.Tree');
GradingDiag.Register.Tree = Ext.extend(Ext.tree.TreePanel, {
    initComponent: function(){
        var tree = this;
        var loader = new Ext.tree.TreeLoader({
            dataUrl: url.getChildren,
            baseAttrs: {
                uiProvider: Ext.ux.TreeCheckNodeUI
            }
        });
        loader.on('beforeload', function(loader, node){
            loader.baseParams.code = node.id;
        });
        var root = new Ext.tree.AsyncTreeNode({
            id: 'root',
            text: '肝病十二级分类诊断编码表'
        });
        Ext.apply(this, {
            root: root,
            title: '肝病十二级分类诊断编码表',
            rootVisible: false,
            autoScroll: true,
            useArrows: true,
            onlyLeafCheckable: true,
            containerScroll: true,
            loader: loader,
            tbar: [{
                text: '增加',
                iconCls: 'application_form_add',
				handler:tree.add.createDelegate(tree)
            }, '->', {
                text: '取消选择',
                iconCls: 'application_form_edit',
				handler:tree.cancelChecked.createDelegate(tree)
            }]
        });
        GradingDiag.Register.Tree.superclass.initComponent.call(this, arguments);
    },
	getRootLevel:function(node){
		var tempNode = node;
	    var level = null;
	    while (true) {
	        tempNode = tempNode.parentNode;
	        if (tempNode.parentNode.id == 'root') {
	            level = tempNode.text.substr(1, tempNode.text.indexOf('级') - 1);
	            break;
	        }
	    }
	    return parseInt(level);
	},
	validChecked:function(){
		var tree=this;
		//验证级别
		var levels=[];
		Ext.each(this.getChecked(),function(node,i){
			var level=tree.getRootLevel(node);
			if(levels.indexOf(level)==-1)
				levels.push(level);
		});
		if(levels.length==0){
			alert('未选择任何诊断。');
			return false;
		}
		if(levels.indexOf(1)!=-1){
			if(levels.indexOf(2)==-1){
				alert('未选择第2级。');
				return false;
			}
			if(levels.indexOf(3)==-1){
				alert('未选择第3级。');
				return false;
			}
			if(levels.indexOf(4)==-1){
				alert('未选择第4级。');
				return false;
			}
			if(levels.length>4){
				alert('只能选择第1、2、3、4级。');
				return false;
			}
		}else if(levels.indexOf(2)!=-1){
			if(levels.indexOf(1)==-1){
				alert('未选择第1级。');
				return false;
			}
			if(levels.indexOf(3)==-1){
				alert('未选择第3级。');
				return false;
			}
			if(levels.indexOf(4)==-1){
				alert('未选择第4级。');
				return false;
			}
			if(levels.length>4){
				alert('只能选择第1、2、3、4级。');
				return false;
			}
		}else if(levels.indexOf(3)!=-1){
			if(levels.indexOf(1)==-1){
				alert('未选择第1级。');
				return false;
			}
			if(levels.indexOf(2)==-1){
				alert('未选择第2级。');
				return false;
			}
			if(levels.indexOf(4)==-1){
				alert('未选择第4级。');
				return false;
			}
			if(levels.length>4){
				alert('只能选择第1、2、3、4级。');
				return false;
			}
		}else if(levels.indexOf(4)!=-1){
			if(levels.indexOf(1)==-1){
				alert('未选择第1级。');
				return false;
			}
			if(levels.indexOf(2)==-1){
				alert('未选择第2级。');
				return false;
			}
			if(levels.indexOf(3)==-1){
				alert('未选择第3级。');
				return false;
			}
			if(levels.length>4){
				alert('只能选择第1、2、3、4级。');
				return false;
			}
		}else{
			if(levels.length>1){
				alert('只能选择一级诊断。');
				return false;
			}
		}
		return true;
	},
	prepareNode:function(){
		var tree=this;
		var rst=[];
		Ext.each(this.getChecked(),function(node){
			var level=tree.getRootLevel(node);
			if(level==2||level==3||level==4)
				level=1;
			if(!rst[level])
				rst[level]=[];
			rst[level].push(node);
		});
		return rst;
	},
	add:function(){
		var tree=this;
		var nodes=this.prepareNode();
		
		var tree2=Ext.getCmp('tree2');
		Ext.each(nodes,function(ns,i){
			if(!ns)
				return true;
			var root=tree2.getNodeById('diag'+i);
			var sep=new Ext.tree.TreeNode({text:'---------------------------------------------------------------------------------------------------------------------------------------',iconCls:'hide',flag:'sep',cls:'sep'});
			root.expand(true,true);
			root.appendChild(sep);
			Ext.each(ns,function(n){
				var multiLine=n.attributes.data.multiLine;
				if(multiLine.length==0)
					multiLine=n.id
				else
					multiLine+='-'+n.id;
				var pcode=null;
				Ext.each(multiLine.split('-'),function(id){
					tree2.appendNode(sep,pcode,tree.getNodeById(id));
					pcode=id;
				});
			});
		});
	},
	cancelChecked:function(){
		Ext.each(this.getChecked(),function(node){
			node.getUI().toggleCheck(false);
		});
	}
});
Ext.ns('GradingDiag.Register.Tree2');
GradingDiag.Register.Tree2 = Ext.extend(Ext.tree.TreePanel, {
    initComponent: function(){
		var tree=this;
        var children = [{
            text: '第1级',
            id: 'diag1',
            cls: 'hide',
			children:[]
        }, {
            text: '第5级',
            id: 'diag5',
            cls: 'hide',
			children:[]
        }, {
            text: '第6级',
            id: 'diag6',
            cls: 'hide',
			children:[]
        }, {
            text: '第7级',
            id: 'diag7',
            cls: 'hide',
			children:[]
        }, {
            text: '第8级',
            id: 'diag8',
            cls: 'hide',
			children:[]
        }, {
            text: '第9级',
            id: 'diag9',
            cls: 'hide',
			children:[]
        }, {
            text: '第10级',
            id: 'diag10',
            cls: 'hide',
			children:[]
        }, {
            text: '第11级',
            id: 'diag11',
            cls: 'hide',
			children:[]
        }, {
            text: '第12级',
            id: 'diag12',
            cls: 'hide',
			children:[]
        }];
        Ext.apply(this, {
            title: '当前诊断',
            useArrows: true,
            autoScroll: true,
			cls:'tree2',
            containerScroll: true,
            root: {
                text: 'ROOT',
				id:'root2',
                children: children
            },
            rootVisible: false,
			tbar:[
				'诊断时间：',{
					xtype:'datefield',
					width:150,
					readOnly:true,
					format:'Y-m-d'
				},'->',{
					text:'删除',
					iconCls:'application_form_delete',
					handler:tree.deleteNode.createDelegate(tree)
				}
			],
			bbar:[
				'住院医师：',{
					xtype:'textfield',
					width:120
				},{
					text:'签字',
					pressed:true
				},'-','主治医师：',{
					xtype:'textfield',
					width:120
				},{
					text:'签字',
					pressed:true
				},'-','主任/副主任医师：',{
					xtype:'textfield',
					width:120
				},{
					text:'签字',
					pressed:true
				}
			]
        });
        GradingDiag.Register.Tree2.superclass.initComponent.call(this, arguments);
    },
	deleteNode:function(){
		var node=this.getSelectionModel().getSelectedNode();
		if(!node)
			alert('未选择节点。');
		if(node.attributes.flag!='sep')
			alert('请选择分割节点。');
		node.parentNode.removeChild(node,true);
	},
	appendNode:function(root,parentNode,node){
		if(findNodeByCode(node.id,root))
			return;
		var parent=root;
		if(parentNode!=null)
			parent=findNodeByCode(parentNode,root);
		var n=new Ext.tree.TreeNode({
			code:node.id,
			text:node.text,
			flag:'diag',
			iconCls:'hide'
		});
		parent.expand(true,true);
		parent.appendChild(n);
	},
	showCode:function(){
		var fn=function(sep){
			var code=[];
			sep.cascade(function(n){
				if(n.attributes.flag=='diag'&&!n.hasChildNodes()){
					code.push(n.attributes.code);
				}
			});
			return code.join('/');
		}
		var rst=[];
		this.root.cascade(function(n){
			if (n.attributes.flag == 'sep') {
				rst.push(fn(n));
			}
		});
		Ext.get('code').update(rst.join('-'));
	}
});
function findNodeByCode(code,node){
	var rst=null;
	var fn=function(temp){
		temp.eachChild(function(n){
			if(n.hasChildNodes())
				fn(n);
			if(n.attributes.code==code)
				rst=n;
		});
	}
	fn(node);
	return rst;
}
Ext.onReady(function(){
    new Ext.Viewport({
        layout: 'border',
        items: [new GradingDiag.Register.Tree({
            region: 'west',
            width: 300,
            collapsible: true,
            margins: '5 5 5 5',
			id:'tree'
        }), {
            region: 'center',
            layout: 'border',
            border: false,
            items: [new GradingDiag.Register.Tree2({
                region: 'center',
                margins: '5 5 5 0',
                id: 'tree2'
            }), {
                region: 'south',
                title: '诊断编码',
                margins: '0 5 5 0',
                height: 50,
				html:'<div id="code"></div>'
            }]
        }]
    });
});
