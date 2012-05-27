﻿var args;
var pid;
var title;
//liugang 2011-08-11  start
var look = App.util.getHtmlParameters('look');
//liugang 2011-08-11  end
var type= App.util.getHtmlParameters('type');
var flag= App.util.getHtmlParameters('flag');
var data =  App.util.getHtmlParameters('data');
var link = App.util.getHtmlParameters('link'); //看看是不是要执行关联动作，如果该参数为空，则仅仅为查看，隐藏'关联'两个字


Ext.onReady(function(){
	args = getQueryStringArgs();
	pid = args["id"] ? args["id"] : "";
	title = '放射影像检查结果';
	//liugang 2011-08-11  start
	if(look != 1){
	//liugang 2011-08-11  end
	if(pid != ""){
		title +=  '（患者姓名：'+args["name"]+'，患者编号：'+args["no"]+'）'
	}
	};//liugang 2011-08-11  start
	layout();
});
function layout(){
    var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: App.App_Info.BasePath + '/checkReport.do'
        }),
        reader: new Ext.data.JsonReader({
            root: 'root',
            totalProperty: 'total'
        }, [{
            name: 'id'
        }, {
            name: 'checkOrderNum'
        }, {
            name: 'patient'
        },  {
            name: 'checkDate'
        }, {
            name: 'checkItem'
        }, {
            name: 'checkPart'
        }, {
            name: 'reportDate'
        }, {
            name: 'reportDoctor'
        }, {
            name: 'reportOrderNum'
        }, {
            name: 'imageView'
        }, {
            name: 'reportResult'
        },{
        	name:'isFromOutHospital'
        }
        ])
    });
    ds.baseParams = {
        method: 'getCheckReports',
        search: ''
    };
    ds.load({
        params: {
            id: pid,
            start: 0,
            limit: 20
        }
    });
    var sm = new Ext.grid.RowSelectionModel({
        singleSelect: false
    });
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),{
        header: '检查日期',
        dataIndex: 'checkDate',
         renderer: function(value, meta, record, rowIndex, colIndex, store){
        	if(record.data.guiDangDate != ""){
        		return "<font color='red'>"+record.data.checkDate+"</font>";	
        	}else{
        		 return record.data.checkDate;
        	}
           
        }
    }, {
        header: '检查项目',
        dataIndex: 'checkItem',
        renderer: function(value, meta, record, rowIndex, colIndex, store){
            if(record.data.guiDangDate != ""){
        		return "<font color='red'>"+record.data.checkItem+"</font>";	
        	}else{
        		 return record.data.checkItem;
        	}
        }
    }, {
        header: '检查部位',
        dataIndex: 'checkPart',
        renderer: function(value, meta, record, rowIndex, colIndex, store){
            if(record.data.guiDangDate != ""){
        		return "<font color='red'>"+record.data.checkPart+"</font>";	
        	}else{
        		return record.data.checkPart;
        	}
        }
    }, {
        header: '报告时间',
        dataIndex: 'reportDate',
        renderer: function(value, meta, record, rowIndex, colIndex, store){
            if(record.data.guiDangDate != ""){
        		return "<font color='red'>"+record.data.reportDate+"</font>";	
        	}else{
        		return record.data.reportDate;
        	}
        }
    }, {
        header: '查看报告',
        dataIndex: 'reportDate',
        renderer: function(value, meta, record, rowIndex, colIndex, store){
            return "<a href='###' onclick='showReport("+record.data.id+")'>查看/关联检查</a>";
        }
    }, {
        header: '报告医生',
        dataIndex: 'reportDoctor',
        renderer: function(value, meta, record, rowIndex, colIndex, store){
            if(record.data.guiDangDate != ""){
        		return "<font color='red'>"+record.data.reportDoctor+"</font>";	
        	}else{
        		return record.data.reportDoctor;
        	}
        }
    }, {
        header: '流水号',
        dataIndex: 'reportOrderNum',
        renderer: function(value, meta, record, rowIndex, colIndex, store){
            if(record.data.guiDangDate != ""){
        		return "<font color='red'>"+record.data.reportOrderNum+"</font>";	
        	}else{
        		return record.data.reportOrderNum;
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
                        		id: args["id"],
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
    
    var grid = new Ext.grid.GridPanel({
        id: 'grid',
        title: title,
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
                		id: args["id"],
                        start: 0,
                        limit: 20
                    }
                });
            }
        
		},'-',{
			text:'查看检查报告',
			handler:function(){
			var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
				if (ss.length == 0) {
									alert('请先选择一条记录。');
									return;
				}
				//liugang 2011-08-11  start
				App.util.addNewMainTab('/module/checkreport/reportsheet_pacs.jsp?id='+ss[0].get('id')+'&look='+look,'检查报告');
				//liugang 2011-08-11  end

			}
		},'-',{
	            text: '关 联',
	            handler: function(){
			      if(link==null || link == "null"){
			    	  alert('此页面仅提供查看功能，请进入随访页面进行关联!');
			    	  return;
			      } 
			      var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
				  if (ss.length == 0) {
						alert('请先选择一条记录。');
						return;
				    }
					  Ext.Ajax.request({
							url:App.App_Info.BasePath+'/plan.do',
							params:{
								method:'linkReport',
								repId:ss[0].get('id'),
								flag:flag,
							    data:data,
							    type:type
							},
							success:function(_response){
								if(confirm("关联成功")){
									window.opener.refreshItem();
									window.close();
								}
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
            emptyMsg: "<font color='red'>没有记录</font>"
        })
    });
    new Ext.Viewport({
        layout: 'fit',
        items: grid
    });
}


function getQueryStringArgs(){
	var qs = (location.search.length)>0 ? location.search.substring(1) : "";
	var args = {};
	var items = qs.split("&");
	var item = null, name = null, value = null;
	for(var i=0; i<items.length; i++){
		item = items[i].split("=");
		name = decodeURIComponent(item[0]);
		value = decodeURIComponent(item[1]);
		args[name] = value;
	}
	return args;
}

//liugang 2011-08-11 start
function showReport(_thisId){
	App.util.addNewMainTab('/module/checkreport/reportsheet_pacs.jsp?id='+_thisId+'&look='+look,'检查报告');
}
