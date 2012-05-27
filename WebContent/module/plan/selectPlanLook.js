Ext.onReady(function(){
    layout();
});

var _id= App.util.getHtmlParameters('planId');
var _paId= App.util.getHtmlParameters('paId');
var omcId =  App.util.getHtmlParameters('omcId');

var repId =  App.util.getHtmlParameters('repId');
var _flag =  App.util.getHtmlParameters('type');

var receiveDate =  App.util.getHtmlParameters('receiveDate');
var sectionNo =  App.util.getHtmlParameters('sectionNo');
var testTypeNo =  App.util.getHtmlParameters('testTypeNo');
var sampleNo =  App.util.getHtmlParameters('sampleNo');
var parItemNo = App.util.getHtmlParameters('parItemNo');
var itemName = App.util.getHtmlParameters('itemName');


var _method = (_flag == null || _flag == "") ? "linkOMC" : "linkReport";
var WEEK_OF_YEAR = '3';
var DAY_OF_YEAR = '6';
var YEAR = '1';
var MONTH_OF_YEAR = '2';
//_flag = (_flag == null || _flag == "") ? "add" : _flag;
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
	        paId:_paId
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
	        header: '编号',
	        width:50,
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
	   /* {
	        header: '患者性别',
	        dataIndex: 'patient',
	        renderer: function(value){
	    	    if(value == null) {
	    	       return "";
	    	    }
	 
	            return  value.gender == 1 ? "男" : "女";
	        }
	    },*/ {
	        header: '随访起始日期',
	        width:100,
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
	               return '<font color="#ee9a00">随访已执行</font>';   
	            }
	            else if(value == 2) {
	               return '<font color="green">结果已关联</font>';
	            }
	            else {
	            	return '<font color="red">随访计划中</font>';
	            }
	        }
	    }]);
	   
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
	        }, '-',{
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
	        , '-',{
				text:'日历模式查看随访计划',
				handler:function(){
					var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
					if(ss.length==0){
						alert('请先选择一个会员。');
						return;
					}
					App.util.addNewMainTab('/module/plan/showcalendar.html?id='+ss[0].get('id'),'查看随访日期');
				}
			}/*'-',{
				text:'删除随访计划',
				handler:function(){
					var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
					if(ss.length==0){
						alert('请先选择一条记录。');
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
					
					App.util.addNewMainTab('/module/plan/memPatientInfo.html','会员信息选择');
				}
			}
			*/],
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
	    
	    grid.addListener('rowclick', showItems);
	    
	    function showItems(grid,rowIndex,e) {
	    	var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
	    	_id = ss[0].get('id');   
	        var store = Ext.getCmp('editGrid').getStore();
            store.baseParams.planId = _id;
            store.load({
                params: {
                    start: 0,
                    limit: 20
                }
            });
	    }
	    
    var planItemDS = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: App.App_Info.BasePath + '/plan.do'
        }),
        reader: new Ext.data.JsonReader({
            root: 'root',
            totalProperty: 'total'
        }, [{
            name: 'id'
        }, {
            name: 'itemNo'
        }, {
            name: 'checkItem'
        },  {
            name: 'checkItemName'
        }, {
            name: 'circle'
        }, 
        {
            name: 'circleType'
        },{
            name: 'comment'
        }, {
            name: 'state'
        },{
            name: 'reportDate'
        }])
    });
    
    planItemDS.baseParams = {
            method: 'getAllPlanItems',
            planId: _id,
            start: 0,
            limit: 20
        };

    planItemDS.load({
        params: {
            start: 0,
            limit: 20
        }
    });
    var sm2=new Ext.grid.CheckboxSelectionModel({dateIndex:'rid'});
    sm2.on('rowselect', function(sm, rowIndex, record){
    	//var id = record.get("id");
    	 $.post(
            		App.App_Info.BasePath+'/plan.do',
            		{
            			method:_method,
            			itemId:record.get("id"),
            			omcId:omcId,
            			flag:"add",        			
            			type:_flag,
            			repId:repId,
            			receiveDate:receiveDate,
            			sectionNo:sectionNo,
            			testTypeNo:testTypeNo,
            			sampleNo:sampleNo,
            			itemName:itemName,
            			parItemNo:parItemNo,
            			paId:_paId,
                		start:0,
                		limit:20
            		},
            		function(data){
            			if(data.success){
            				var store = Ext.getCmp('grid').getStore();
        	                store.baseParams.search = '';
        	                store.load({
        	                    params: {
        	                        start: 0,
        	                        limit: 20
        	                    }
        	                });
            			}else {
            				if(data.msg) {
            				   alert(data.msg + "关联错误");
            				}            				
            			}
            		},
            		'json'
            	);
    });

    sm2.on('rowdeselect', function(sm, rowIndex, record){
    	$.post(
        		App.App_Info.BasePath+'/plan.do',
        		{
        			method:_method,
        			itemId:record.get("id"),
        			omcId:omcId,
        			flag:'re',
        			type:_flag,
        			repId:repId,
            		start:0,
            		limit:20
        		},
        		function(data){
        			if(data.success){
        				var store = Ext.getCmp('grid').getStore();
    	                store.baseParams.search = '';
    	                store.load({
    	                    params: {
    	                        start: 0,
    	                        limit: 20
    	                    }
    	                });
    	                
        			}else {
        				if(data.msg) {
        				   alert(data.msg + "关联错误");
        				}            				
        			}
        		},
        		'json'
        	);
    });

    
    var comStore = new Ext.data.SimpleStore({
		fields:['value','text'],
		data:[['0','随访计划中'],['1','随访已执行'],['2','结果已关联']]
	});
    //['0','随访计划未开始'],['1','随访计划阶段中'],['2','随访计划完成']
    var cricleStore = new Ext.data.SimpleStore({
		fields:['value','text'],
		data:[[YEAR,'年'],[MONTH_OF_YEAR,'月'],[WEEK_OF_YEAR,'周'],[DAY_OF_YEAR,'天']]
	}); 
	
    //comStore.load({});
    var editGrid = new Ext.grid.EditorGridPanel({
		id: 'editGrid',
		title: '随访计划项目',
		border: false,
		autoScroll: true,
		height:200,
		columns:[

			new Ext.grid.RowNumberer(),
			sm2,
			{
		        header: '检查项目编号',
		        width:30,
		        dataIndex: 'checkItem',
		       // editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
		        renderer: function(value){
				
		            return value == null ? "" : value.ORDERCODE;
		        }
		    }, {
		        header: '检查项目',
		        width:30,
		        dataIndex: 'checkItem',
		       // editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
		        renderer: function(value){
		    	return value == null ? "" : value.ORDERNAMEK;
		        }
		    }, 
		    {
		        header: '检查周期',
		        width:20,
		        dataIndex: 'circle',
		        editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
		        renderer: function(value){
		    	    return value;
		        }
		    }, 
		    {
		    	header: '周期类型',
		        dataIndex: 'circleType',
		        width:20,
		        editor:new Ext.form.ComboBox({
		        	readOnly:true,
		        	xtype: 'combo',
		        	store:cricleStore,
		        	displayField: 'text',
					valueField: 'value',
					triggerAction: 'all',
		        	mode:'local'
		        }),
		        renderer: function(value){
		    	if(value == YEAR) {
		               return '<font color="#ee9a00">年</font>';   
		            }
		            else if(value == MONTH_OF_YEAR) {
		               return '<font color="green">月</font>';
		            }
		            else if(value == WEEK_OF_YEAR){
		            	return '<font color="red">周</font>';
		            }
		            else if(value == DAY_OF_YEAR) {
		            	return '<font color="blue">天</font>';
		            }else {
		            	return '<font color="grey">请选择</font>';
		            }
		        }
		    }, {
		        header: '备注',
		        width:30,
		        dataIndex: 'comment',
		        editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
		        renderer: function(value){
		    	   return value;
		        }
		    }, {
		        header: '状态',
		        dataIndex: 'state',
		        editor:new Ext.form.ComboBox({
		        	readOnly:true,
		        	xtype: 'combo',
		        	store:comStore,
		        	displayField: 'text',
					valueField: 'value',
					triggerAction: 'all',
		        	mode:'local'
		        }),
		        width:20,
		        renderer: function(value){
	            if(value == 1) {
	                return '<font color="#ee9a00">随访已执行</font>';   
	             }
	             else if(value == 2) {
	                return '<font color="green">结果已关联</font>';
	             }
	             else {
	             	return '<font color="red">随访计划中</font>';
	             }
		        }
		    },{
		        header: '报告时间',
		        dataIndex: 'reportDate',
		        width:20,
		        renderer: function(value){
		    	    return value == null ? "" : value;
		        }
		    }
		],
		sm:sm2,
		ds:planItemDS,
		clicksToEdit:2,
		viewConfig:{
			forceFit:true
		},
		tbar: [{text: '勾选本次随访关联的检查项目'} ],
		 bbar: new Ext.PagingToolbar({
	            pageSize: 20,
	            store: planItemDS,
	            displayInfo: true,
	            displayMsg: '显示第<font color="red"> {0} </font>条' +
	            '到<font color="red"> {1} </font>条记录，' +
	            '一共<font color="red"> {2} </font>条',
	            emptyMsg: "没有记录"
	        })
	});
    
    editGrid.on("afteredit", afterEdit, editGrid);
    editGrid.on("beforeedit", beforeEdit, editGrid);
    var origComment = null;
    var origCircle = null;
    var origState = null;
    
    function afterEdit(obj) {
       var record = obj.record;
       var id = record.get("id");
       var circle = record.get("circle");
       var comment = record.get("comment");
       var state = record.get("state");
       //alert(state);
       if(circle == origCircle && comment == origComment && state == origState) {
          return;
       }
       else {
    	   $.post(
           		App.App_Info.BasePath+'/plan.do',
           		{
           			method:'editPlanItem',
           			planItemId:id, 
               		start:0,
               		limit:20,
               		circle:circle,
               		comment:comment,
               		state:state
           		},
           		function(data){
           			if(data.success){
           				
           				var store = Ext.getCmp('grid').getStore();
    	                store.baseParams.search = '';
    	                store.load({
    	                    params: {
    	                        start: 0,
    	                        limit: 20
    	                    }
    	                });
    	                
    	                var _store=Ext.getCmp('editGrid').getStore();
    	                _store.load({
    	                    params: {
    	                        start: 0,
    	                        limit: 20
    	                    }
    	                });
       			 	
       			 		//_store.load();
           			}else {
           				if(data.msg) {
           				   alert(data.msg);
           				}
           				
           			    var _store=Ext.getCmp('editGrid').getStore();
    	                _store.load({
    	                    params: {
    	                        start: 0,
    	                        limit: 20
    	                    }
    	                });
           			}
           		},
           		'json'
           	);
       }
    }
    
    function beforeEdit(obj) {
    	var record = obj.record;
        var id = record.get("circle");
        origCircle = record.get("circle");
        origComment = record.get("comment");
        origState = record.get("state");
    }
    

   
    new Ext.Viewport({
        layout: 'border',
        items: [{
			region:'west',
			layout:'fit',
            width: 430,
			split:true,
			autoScroll:true,
			items:grid
		},{
			region:'center',
			layout:'fit',
			items:editGrid
		}]
    });
    
    
}


