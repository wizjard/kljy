var paId = App.util.getHtmlParameters('paId');
var WEEK_OF_YEAR = 3;
var DAY_OF_YEAR = 6;
var YEAR = 1;
var MONTH = 2;

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
            name: 'beginDate'
        }, {
            name: 'planTime'
        }, {
            name: 'checkItem'
        }, {
            name: 'circle'
        }, {
            name: 'planDate'
        }, {
            name: 'comment'
        }, {
            name: 'days'
        }])
    });
    ds.baseParams = {
        method: 'getPlanningItems',
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
        header: '检查项目',
        dataIndex: 'checkItem',
        renderer: function(value){
            return value == null ? "" : value;
        }
    }, {
        header: '随访计划设置日期',
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
    }, {
        header: '检查周期',
        width:50,
        dataIndex: 'circle',
        renderer: function(value){
            return value;
        }
    }, {
        header: '随访计划日期',
        width:50,
        dataIndex: 'planDate',
        renderer: function(value){
    	if (value) {
            try {
                return new Date(value == null ? "" : value.time).format('Y/m/d')
            } 
            catch (err) {
            }
        }
        }
    }, {
        header: '剩余天数',
        dataIndex: 'days',
        renderer: function(value){
            if(value>7){
            	return value+"天";
            }else if(value>=0){
            	return "<font color='green'>"+value+"天</font>";
            }else{
            	return "<font color='red'>已过期"+(0-value)+"天</font>";
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
			text:'发送短信',
			handler: function(){
	            var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
				 if (ss.length == 0) {
				     alert('请至少选择一条记录。');
				     return;
				 }
				 //var pid=ss[0].get('patient').id;
				 var send=function(id,ctx,callback){
				 	Ext.Ajax.request({
					 	url:App.App_Info.BasePath + '/biomedical.do',
					 	params:{
					 		method:'sendMsg',
					 		id:id,
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
				 	title:'发送短信',
				 	closeAction:'close',
				 	width:400,
				 	height:200,
				 	autoHeight:true,
				 	layout:'anchor',
				 	items:[{
				 		xtype:'textarea',
				 		width:390,
				 		height:160,
				 		value:'亲爱的会员您好，您的随访检查项目“'+ss[0].get("checkItem")+'”安排在'
				 			+new Date(ss[0].get('planDate').time).format('Y年n月j日')
				 			+'，请您按照计划来医院进行检查。'
				 			+'\n——佑安医院会员中心',
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
						 		text:'当前字数:0字'
						 	},{
						 		columnWidth:0.3,
						 		xtype:'label',
						 		width:20,
						 		text:'短信条数:1条'
						 	},{
						 		columnWidth:0.4,
						 		xtype:'label',
						 		width:20,
						 		text:'短信总字数上限:100字'
					 	}]
				 	}],
				 	buttonAlign:'center',
				 	buttons:[
				 		{
				 			text:'发送',
				 			handler:function(){
				 				var win=this.ownerCt;
				 				var value=win.items.get(0).getValue();
				 				alert(value);
				 				if(value&&value.length>0){
				 					send(paId,value,function(data){
				 						if(data.success)
				 							win.close();
				 					});
				 				}else{
				 					alert('信息不能为空。');
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
