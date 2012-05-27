Ext.onReady(function(){
    layout();
});
 
var _id= App.util.getHtmlParameters('planId');
var _pid= App.util.getHtmlParameters('pId');
var KID= App.util.getHtmlParameters('KID');

var WEEK_OF_YEAR = '3';
var DAY_OF_YEAR = '6';
var YEAR = '1';
var MONTH_OF_YEAR = '2';
var itemCount = 0;
var added = [];
var moved = [];
var frameSrc =  "<iframe src='../plan/addPlan.html?KID=" + KID + "&planId=" + _id + "&pid=" + _pid + "'  width='100%' height='100%' scroll='no'></iframe>"
var _planDate;


function getItemCount() {
   return	 Ext.getCmp('editGrid').getStore().getCount();
}

function checkNumType1(num, check) {
	   if(num == "") {
	      return false;
	   }

	   var no = /^\d$/;
        if(!no.test(num)) {   
	    	alert(num + " 格式不正确");  
	        return false;   
	    }   
	    return true;
	}  


// 发送数据到服务器端进行更新
    function updatePlanData(isAdd) {
	
        var modified = Ext.getCmp('editGrid').getStore().modified.slice(0);
        var move = [];

        Ext.each(moved, function(item){
        	move.push(item.data);
          
        });
        
           var json = [];
           var plaId;
           Ext.each(modified, function(item){
             json.push(item.data);             
           });
           
           var add = []; 
           
           Ext.each(added, function(item){
               add.push(item.data);               
             });
           
           if (json.length > 0 || add.length > 0 || move.length > 0) {
              
               Ext.Ajax.request({
                   url: App.App_Info.BasePath + '/plan.do',
                   params: { data: Ext.util.JSON.encode(json), 
            	   method:"updatePlanItem",
            	   add: Ext.util.JSON.encode(add), 
            	   move: Ext.util.JSON.encode(move), 
       			    planId:_id, 
       			 isAdd:isAdd,
               		start:0,
               		limit:20
               		},
                   method: "POST",
                   success: function(response) {
                       Ext.Msg.alert("信息", "操作成功！", function() { window.close(); });
                   },
                   failure: function(response) {
                       Ext.Msg.alert("警告", "操作失败，请稍后再试！");
                   }
               });
           }
           else {
             //  Ext.Msg.alert("警告", "没有任何操作改变！");
           }
       }


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
        },{
            name: 'ORDERCODE'
        },  {
            name: 'ORDERNAMEK'
        }, {
            name: 'circle'
        }])
    });
    
    ds.baseParams = {
        method: 'getAllCheckItems',
        ordername:'',
        search: ''
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
        header: '检查项目编号',
        dataIndex: 'ORDERCODE',
        width:50,
        renderer: function(value){
    	return value;
        }
    }, {
        header: '检查项目名称',
        dataIndex: 'ORDERNAMEK',
        renderer: function(value){
    	return value;
        }
    }/*, {
        header: '默认周期',
        dataIndex: 'circle',
        width:30,
        renderer: function(value){
    	   return value+"个月";
        }
    }*/]);

    var grid = new Ext.grid.GridPanel({
        id: 'grid',
        title: '检查项目列表',
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
        tbar: [ {
				xtype:'textfield',
				width:100,
				emptyText:'检查项目名称',
				id:'order-name'
			  },{
			text:'搜索随访检查项目',
			tooltip:'搜索随访检查项目',
			handler:function(){
				var _val2=Ext.getCmp('order-name').getValue().trim();
				var _store=Ext.getCmp('grid').getStore();
				_store.baseParams = {
						method:'getAllCheckItems',ordername:_val2
				    };
				_store.load({
				        params: {
				            start: 0,
				            limit: 20
				        }
				    });
			}
		  },'->',{
			xtype:'tbseparator'
		  },{
	            text: '增加检查项目',
	            handler: function(){
			  
			    var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
	            if (ss.length == 0) {
	                alert('请至少选择一条记录。');
	                return;
	            }
	            
	            var gstore = Ext.getCmp('grid').getStore();
			    var editStore=Ext.getCmp('editGrid').getStore();
			   
			    var CheckItem = gstore.recordType;
		        var PlanItem = editStore.recordType;
		        var id_a;
		        var ORDERNAMEK_a;
		        var ORDERCODE_a;
		        var p;
		        
	            for(var i = 0; i < ss.length; i++) {
	              id_a = ss[i].get("id");
	              ORDERNAMEK_a = ss[i].get("ORDERNAMEK");
	              ORDERCODE_a = ss[i].get("ORDERCODE");
	            	/*var ch = new CheckItem({
	            		id:ss[i].get("id"),
	            		ORDERCODE:ss[i].get("ORDERCODE"),
	            		ORDERNAMEK:ss[i].get("ORDERNAMEK"),
	            		circle:ss[i].get("circle")	            		  
	            	});*/
	            	
	              
	              p = new PlanItem({		                             	
                   	
   	            	checkItemCode: ORDERCODE_a,
                   	checkItemName: ORDERNAMEK_a,
                   	checkId:id_a,
                   	
                   	comment: "",
                   	reportDate: "",
                   	circle: 3,
                   	circleType: MONTH_OF_YEAR,
                   	crossd:7,
                   	state:0,
                   	planDate:_planDate
                   });
	            
	            if(editStore.find("checkItemCode",ORDERCODE_a ,0) < 0 && editStore.find("checkItemName",ORDERNAMEK_a ,0)  < 0) {

		             editStore.insert(0, p);
	                 itemCount = itemCount + 1;
	                 //Ext.getCmp('editGrid').getStore().modified.push(p);
	                 added.push(p);
		               
	            }
	            else {
	            	alert(ORDERNAMEK_a + " 检查项目已存在");
	            }
	             
	              
	              /*$.post(
		            		App.App_Info.BasePath+'/plan.do',
		            		{
		            			method:'checkPlanItem',
		            			planId:_id, 
		                		start:0,
		                		limit:20,
		                		checkId:id_a
		            		},
		            		function(data){
		            			if(data.success){
		            				alert(ORDERCODE_a + "|" +ORDERNAMEK_a);
		            				 p.planDate = data.success; 
		                             //Ext.getCmp('editGrid').stopEditing();
		                             editStore.insert(0, p);
		                             itemCount = itemCount + 1;
		                             //Ext.getCmp('editGrid').getStore().modified.push(p);
		                             added.push(p);
		                             p = null;
		                             //Ext.getCmp('editGrid').startEditing(0, 0);
		                            // editStore.reload();
		            			}else if(data.msg){
		            				alert("此检查项目已存在：" + ch.get("ORDERNAMEK"));
		            			
		            			}
		            		},
		            		'json'
		            	);*/
                   
                    
	            }
	            
	            editStore.applySort();
			  
	        	/*if(_id == null || _id < 1) {
	        	   alert("请输入随访基本信息并保存");
	        	   return;
	        	}
	        	
	        	var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
	            if (ss.length == 0) {
	                alert('请至少选择一条记录。');
	                return;
	            }
	            
	            for(var i = 0; i < ss.length; i++) {
		            $.post(
		            		App.App_Info.BasePath+'/plan.do',
		            		{
		            			method:'addPlanItem',
		            			planId:_id, 
		                		start:0,
		                		limit:20,
		                		checkId:ss[i].get('id')
		            		},
		            		function(data){
		            			if(data.success){
		            				var _store=Ext.getCmp('editGrid').getStore();
		            				_store.baseParams = {
		            				            method: 'getAllPlanItems',
		            				            planId: _id,            
		            				            search: ''
		            				        };

		            				_store.load({
		            				        params: {
		            				            start: 0,
		            				            limit: 20
		            				        }
		            				    });
		            				itemCount = itemCount + 1;
		            			}else if(data.msg){
		            				alert(data.msg);
		            			}
		            		},
		            		'json'
		            	);
	              }*/
	            }
	        }
	        ],
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
    var planItemDS = new Ext.data.Store({
    	pruneModifiedRecords:true,
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
            name: 'checkItemCode'
        }, {
            name: 'circle'
        }, {
            name: 'circleType'
        }
        , {
            name: 'comment'
        }, {
            name: 'state'
        },{
            name: 'reportDate'
        },{
        	name:'crossd'
        },{
        	name:'planDate'
        },{
        	name:'visitState'
        }
        ,{
        	name:'checkId'
        }])
    });
    
    planItemDS.baseParams = {
            method: 'getAllPlanItems',
            planId: _id,            
            search: ''
        };

    planItemDS.load({
        params: {
            start: 0,
            limit: 20
        }
    });
    setTimeout(function(){itemCount = planItemDS.getCount();}, 300);
    
    var sm2=new Ext.grid.CheckboxSelectionModel();
    
    var comStore = new Ext.data.SimpleStore({
		fields:['value','text'],
		data:[['0','随访计划中'],['1','提前来访'],['2','按期来访'],['3','超期来访'],['4','随访结束'],['5','超期未来访']]
	});
    
    //['0','随访计划未开始'],['1','随访计划阶段中'],['2','随访计划完成']
    var cricleStore = new Ext.data.SimpleStore({
		fields:['value','text'],
		data:[[-1,'请选择'],[YEAR,'年'],[MONTH_OF_YEAR,'月'],[WEEK_OF_YEAR,'周'],[DAY_OF_YEAR,'天']]
	}); 
	
    //comStore.load({});
    var editGrid = new Ext.grid.EditorGridPanel({
		id: 'editGrid',
		title: '随访计划项目',
		border: false,
		autoScroll: true,

		cm:new Ext.grid.ColumnModel([
			//new Ext.grid.RowNumberer(),

			{
		        header: '检查项目编号',
		        dataIndex: 'checkItemCode',
		        width:30,
		       // editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
		        renderer: function(value, cell, record) {				
		           // return (value == null || value.ORDERCODE == null || value.ORDERCODE == "") ? record.data.checkItemCode : value.ORDERCODE;
				return value;
		        }
		    }, {
		        header: '检查项目',
		        dataIndex: 'checkItem',
		        width:60,
		       // editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
		        renderer: function(value, cell, record){
		    	return (value == null || value.ORDERNAMEK == null || value.ORDERNAMEK == "") ? record.data.checkItemName : value.ORDERNAMEK;
		        }
		    }, 
		    {
		        header: '检查周期',
		        dataIndex: 'circle',
		        editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
		        width:20,
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
		    },{
		        header: '计划来访时间',
		        dataIndex: 'planDate',
		        width:30,
		        renderer: function(value){
		    	
		    	if (value) {
		    		if(typeof(value) == "string") {
		    		   return value;
		    		}
		    		
	                try {
	                    return new Date(value == null ? "" : value.time).format('Y-m-d')
	                } 
	                catch (err) {
	                }
		        }
		        }
		    },{
		        header: '来访前后天数',
		        dataIndex: 'crossd',
		        width:30,
		        editor:  new Ext.form.NumberField({  
	                fieldLabel:'天数',  
	                decimalPrecision:0,                 //精确到小数点后2位(执行4舍5入)  
	                allowDecimals:false,                //允许输入小数  
	                nanText:'请输入有效天数',  
	                allowNegative:false 
	            }),
		        renderer: function(value){
		    	    return (value == null||value == "") ? "7" : value;
		        }
		    }, {
		        header: '状态',
		        dataIndex: 'visitState',
		        width:30,
		        editor:new Ext.form.ComboBox({
		        	readOnly:true,
		        	xtype: 'combo',
		        	store:comStore,
		        	displayField: 'text',
					valueField: 'value',
					triggerAction: 'all',
		        	mode:'local'
		        }),
		        renderer: function(value){
		    	if(value == 1) {
	    	    	return "<font color='blue'>提前来访</font>";
	    	    }
	    	    else if(value == 2) {
	    	    	return "<font color='blue'>按期来访</font>";
	    	    }
	    	    else if(value == 3) {
	    	    	return "<font color='blue'>超期来访</font>";
	    	    }
	    	    else if(value == 4) {
	    	    	return "<font color='green'>随访结束</font>";
	    	    }
	    	    else if(value == 5) {
	    	    	return "<font color='#B8B8B8'>超期未来访</font>";
	    	    }		    	    
	    	    else {
	    	    	return "<font color='red'>随访计划中</font>";
	    	    }
		        }
		    },{
		        header: '报告时间',
		        dataIndex: 'reportDate',
		        
		        width:30,
		        renderer: function(value){
		    	    return value == null ? "" : value;
		        }
		    },
		    {
		        header: '备注',
		        dataIndex: 'comment',
		        width:50,
		        editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
		        renderer: function(value){
		    	   return value;
		        }
		    }
		]),
		sm:sm2,
		ds:planItemDS,
		clicksToEdit:2,
		viewConfig:{
			forceFit:true
		},
		tbar: [ '-',{
            text: '删除检查项目',
            handler: function(){
        	var ss = Ext.getCmp('editGrid').getSelectionModel().getSelections();
            if (ss.length == 0) {
                alert('请至少选择一条记录。');
                return;
            }
            
            Ext.Msg.confirm('信息', '是否删除当前记录？', function(btn){  
                if (btn == 'yes') {  
                      
                    for(var i = 0; i < ss.length; i++) { 
                        Ext.getCmp('editGrid').getStore().remove(ss[i]);
                        //Ext.getCmp('editGrid').getStore().modified.remove(ss[i]);
                        itemCount = itemCount - 1;
                        moved.push(ss[i]);
                    }
                }  
            });
            
           
            }
        },'->',{
			xtype:'textfield',
			width:60,
			emptyText:'周期',
			id:'cricleI'
		  },{			
				width:80,
				xtype: 'combo',
				id:'cricleIT',
				readOnly:true,
	        	xtype: 'combo',
	        	store:cricleStore,
	        	displayField: 'text',
				valueField: 'value',
				triggerAction: 'all',
				emptyText:'请选择周期类型',
	        	mode:'local'
			  },{
		text:'更改所有周期',
		tooltip:'更改所有周期',
		handler:function(){
			  
			var _val2=Ext.getCmp('cricleI').getValue().trim();
			if(!checkNumType1(_val2)) {
			   return;
			}
			
			var _store=Ext.getCmp('editGrid').getStore();
			var PlanItem = _store.recordType;
			_store.each(function(record, index, st){
				var isChange = false;
				
				if(record.data.circle != _val2) {
					record.data.circle = _val2;
					isChange = true;
				}
				
				if((record.data.circleType != Ext.getCmp('cricleIT').getValue()) && Ext.getCmp('cricleIT').getValue() != -1) {
					record.data.circleType = Ext.getCmp('cricleIT').getValue();
					isChange = true;
				}
				
				if(isChange) {
			    	record.commit();  
				   _store.modified.push(record);
				}
				}); 
			
		}
	  }],
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
   
    new Ext.Viewport({
        layout: 'border',
        items: [{
			region:'west',
			layout:'fit',
            width: 450,
			split:true,
			autoScroll:true,
			items:grid		
		},{
			region:'center',
			layout:'fit',
			split:true,
			autoScroll:true,
			items: {
			layout:"border",
			items:[{region:'center',
			layout:'fit',
			items:editGrid
			},{
				region:'south',
				layout:'fit',
				split:true,
				height: 250,
				html:frameSrc
			}]			
		}}]
    });
}
