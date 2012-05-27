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
        login:'mem'
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
               return '<font color="#ee9a00">随访计划中</font>';   
            }
            else if(value == 2) {
               return '<font color="green">随访计划完成</font>';
            }
            else {
            	return '<font color="red">随访计划未开始</font>';
            }
        }
    }]);
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
                    },  {
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
                        o.sw.hide();
                    }
                }, {
                    text: '取消',
                    handler: function(){
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
    var titleModify='随访计划列表';
	if(this.parent.location.toString().indexOf('pad')!= -1){//如果访问终端是pad
		titleModify=false;					
	}
    var grid = new Ext.grid.GridPanel({
        id: 'grid',
        title: titleModify,
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
			text:'查看随访日期',
			handler:function(){
				var ss=Ext.getCmp('grid').getStore().getAt(0);  //获得数据的第一条记录数据
				/*if(ss.length==0){
					alert('请先选择一条随访计划。');
					return;
				}*/
				App.util.addNewMainTab('/module/plan/showcalendar.html?paId='+ss.get('patient').id,'查看随访日期');
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


