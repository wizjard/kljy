var _pid= App.util.getHtmlParameters('pId');
var KID= App.util.getHtmlParameters('KID');

var _id = null; //模板Id
var WEEK_OF_YEAR = '3';
var DAY_OF_YEAR = '6';
var YEAR = '1';
var MONTH_OF_YEAR = '2';

var _beginDate;

Ext.onReady(function(){
    layout();
});

/*var outRegno = App.util.getHtmlParameters('outRegno');
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

var login = App.util.getHtmlParameters('login'); //add by cheng jiangyu 2011-8-26  会员看自己的随访计划
*/

function checkDate(b){
	if(b == "") {
	  return true;
	}
  
    var a = /^\d{4}[-]\d{2}[-]\d{2}$/; 
    var c = /^\d{4}[-]\d{1}[-]\d{1}$/; 
    var d = /^\d{4}[-]\d{1}[-]\d{2}$/;  
    var e = /^\d{4}[-]\d{2}[-]\d{1}$/;
    if(!a.test(b) && !c.test(b)&& !e.test(b)&& !d.test(b)){   
    	alert(b + " 日期格式不正确");  
        return false;   
    }   
    return true;  
}

function checkNumType(num, check) {
   if(num == "") {
      return true;
   }

   var no = /^\d{n}$/;
  /* var nmoth = /^\'月'$/;
   var nday = /^\'日'$/;
   var nyear = /^\'年'$/;
   var nday2 = /^\'天'$/;
   var nweek = /^\'周'$/;
   
   if(check) {
      if(!no.test(num) && !nmoth.test(num)&& !nday.test(num)&& !nyear.test(num)
      && !nday2.test(num)&& !nweek.test(num)) {
         return false;
      }
   }
   else*/ if(!no.test(num)) {   
    	alert(num + " 格式不正确");  
        return false;   
    }   
    return true;
}  

/*function refreshItem() {
	var _store=Ext.getCmp('editGrid').getStore();
    _store.load({
        params: {
            start: 0,
            limit: 20
        }
    });
    
    _store=Ext.getCmp('grid').getStore();
    _store.load({
        params: {
            start: 0,
            limit: 20
        }
    });
}*/

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
	            name: 'moduleName'
	        },  {
	            name: 'createDate'
	        }])
	    });
	    ds.baseParams = {
	        method: 'getAllModules'
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
	    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),  {
	        header: '模板名称',
	        dataIndex: 'moduleName',
	        sortable:true,
	        width:25,
	        editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
	        renderer: function(value){
	    	   return value;
	        }
	    }, {
	        header: '模板创建日期',
	        dataIndex: 'createDate',
	        sortable:true,
	        width:40
	    }]);
	    
	    
	    
	    var oneTbar=new Ext.Toolbar({
	    	  items:[ '-', {
		            text: '返回列表',
		            handler: function(){
		                var store = Ext.getCmp('grid').getStore();
		                Ext.getCmp('moduleName').setValue("");
		                Ext.getCmp('createDate').setValue("");
		                store.load({
		                    params: {
		                        start: 0,
		                        limit: 20
		                    }
		                });
		            }
		        },'-',{
					text:'删除随访模板',
					handler:function(){
						var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
						if(ss.length==0){
							alert('请先选择一条记录。');
							return;
						}
						var checkSelect = new Array();
						for(var i = 0; i < ss.length; i++) {
							checkSelect[i]=ss[i].get("id");
						}
							
						if(!confirm('确定要删除选中记录?'))return;
						Ext.Ajax.request({
							url:App.App_Info.BasePath+'/plan.do',
							params:{
								method:'deleteModule',
								ids:Ext.util.JSON.encode(checkSelect)
							},
							success:function(_response){
								if(Ext.decode(_response.responseText).success){
									alert('删除成功。');
									Ext.getCmp('grid').getStore().reload();
									var _store=Ext.getCmp('editGrid').getStore();
								    _store.load({
								        params: {
								            start: 0,
								            limit: 20
								        }
								    });
								}else{
									alert('删除失败。');
								}
							}
						});
					}

				}/*,'-',{
					text:'编辑随访模板',
					handler:function(){
						var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
						if(ss.length==0){
							alert('请先选择一条记录。');
							return;
						}
						if(ss[0].get('state') == 1 || ss[0].get('state') == 2) {
							alert('随访已执行，不能编辑');
							return;
						}
						App.util.addNewMainTab('/module/plan/addPlanItem.html?planId='+ss[0].get('planId') + "&KID=" + KID,'随访计划信息');
					}
				}*/,'-',{
					xtype:'textfield',
					width:60,
					emptyText:'模板名称',
					id:'moduleName'
				  },
				  {
						xtype:'textfield',
						width:150,
						emptyText:'模板创建日期(2010-08-08)',
						id:'createDate'
					  },
				 {
				text:'搜索随访模板',
				tooltip:'搜索随访模板',
				handler:function(){
					var moduleName = Ext.getCmp('moduleName').getValue().trim();
					var createDate = Ext.getCmp('createDate').getValue().trim();
					
					var _store=Ext.getCmp('grid').getStore();
					
					_store.load({
					        params: {

				        moduleName:moduleName,  //edit by cheng jiangyu 2011-09-05
				        createDate:createDate,
					            start: 0,
					            limit: 20
					        }
					    });
			
				}
			  },'-'
	    	]
	    });
	    
	    var grid = new Ext.grid.EditorGridPanel({
	        id: 'grid',
	        title: '随访模板列表',
	        sm: new Ext.grid.CheckboxSelectionModel({
	            sigleSelect: false
	        }),
	        cm: cm,
	        ds: ds,
	        autoScroll: true,
	        //frame:true,
	        //clicksToEdit:1,
	        viewConfig: {
	            forceFit: true,
	            enableRowBody:true,
	            getRowClass:function(record,rowIndex,p,ds) {
	               var plid = record.data.planTime; 
	              
                   var classNmae ="";
	               
	    	       if(plid == 0) {
	    	    	   classNmae="baseRow";
	    	       }
	    	       else if(plid%2 != 0) {
	            	   classNmae="";
	    	       }
	               else {
	            	   classNmae="ouRow";
	               }
	    	       return classNmae;
	             
	             }
	        },
	        sm: sm,
			tbar:oneTbar,  /*listeners : {
			  'render' : function(){
			  oneTbar.render(this.tbar); // add one tbar
			  }
			  },*/
			clicksToEdit:2,
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
	    
	   /* grid.on("afterEdit",afteredit,grid);
	    function afteredt(e){
	    	var record = e.record;
	    	var store = Ext.getCmp('grid').getStore();
            store.load({
                params: {
                    start: 0,
                    limit: 20
                }
            });
	    	store.load();
	    }*/
	    
	  //grid 的afterEdit事件   by cheng jiangyu 2011-09-07
	     grid.on("afteredit",afterEditGrid,grid);
	    function afterEditGrid(e){
	    	if(!confirm("是否修改?")){
	    		e.value = e.originalValue;
	    		return;
	    	}
	    	var record = e.record;  //当前被修改的行
	    	var store = Ext.getCmp('grid').getStore();
	    	var field = e.field;  //正在被编辑的字段名
	    	var value = null;
	    	if(field=="moduleName"){
	    		value = record.get("moduleName");
	    	}
	    	var id = record.get("id");
	    	
	    	Ext.Ajax.request({
				url:App.App_Info.BasePath+'/plan.do',
				params:{
					method:'updateModule',
					id:id,
					field:field,
					value:value
				},
				success:function(_response){
					/*if(Ext.decode(_response.responseText).success){
						alert('编辑成功。');
						var _store=Ext.getCmp('grid').getStore();
					    _store.load({
					        params: {
					            start: 0,
					            limit: 20
					        }
					    });
					}else{
						alert('编辑失败。');
					}*/
				}
			});
	    }
	    
	    grid.addListener('rowclick', showItems);
	    function showItems(grid,rowIndex,e) {
	    	var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
	    	_id = ss[0].get('id');    
	        var store = Ext.getCmp('editGrid').getStore();
            store.baseParams.moduleId = _id;
            store.load({
                params: {
                    start: 0,
                    limit: 20
                }
            });
            
           
          /*  $('input[name="itemPlan"]').val((ss[0].get('planDate') == null || ss[0].get('planDate')=="") ? "" :　new Date( ss[0].get('planDate').time).format('Y-m-d'));            
			$('input[name="itemBegin"]').val((_beginDate == null || _beginDate=="") ? "" :　new Date( _beginDate.time).format('Y-m-d'));
			var ctype = ss[0].get('circleType')
			   if(ctype == YEAR) {
				   ctype= '年';   
	            }
	            else if(ctype == MONTH_OF_YEAR) {
	            	ctype= '月';
	            }
	            else if(ctype == WEEK_OF_YEAR){
	            	ctype= '周';
	            }
	            else if(ctype == DAY_OF_YEAR) {
	            	ctype= '天';
	            }
			$('input[name="itemCircle"]').val(ss[0].get('circle') + "" + ctype);*/
	    }
	    
    var moduleItemDS = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: App.App_Info.BasePath + '/plan.do'
        }),
        reader: new Ext.data.JsonReader({
            root: 'root',
            totalProperty: 'total'
        }, [{
            name: 'id'
        },  {
            name: 'checkItemName'
        }, {
            name: 'circle'
        }, {
            name: 'circleType'
        },{
            name: 'comment'
        },{
            name: 'checkId'
        },{
            name: 'checkItemCode'
        }])
    });
    
    moduleItemDS.baseParams = {
            method: 'getAllModuleItems',
            moduleId: _id
        };

    moduleItemDS.load({
        params: {
            start: 0,
            limit: 20
        }
    });
    var sm2=new Ext.grid.CheckboxSelectionModel({dateIndex:'rid'});
    var checkSelect = new Array();
    sm2.on('rowselect', function(sm, rowIndex, record){
    	var reId = record.get("id");
    	checkSelect[reId] = reId;
    	return;
    });
    sm2.on('rowdeselect', function(sm, rowIndex, record){
    	checkSelect.remove(record.get("id"));
    	return;
    	
    });
    
    var iTbar = new Ext.Toolbar({
 		 items:['-', {
 	       text: "删除模板项目",
 	       handler: function() {
 			var ss=Ext.getCmp('editGrid').getSelectionModel().getSelections();
			if(ss.length==0){
				alert('请先选择一条记录。');
				return;
			}
			if(!confirm('确定要删除选中记录?'))return;
			
			var moduleItemIds = new Array();
			for(var i = 0; i < ss.length; i++) {
				moduleItemIds[i]=ss[i].get("id");
			}
			Ext.Ajax.request({
				url:App.App_Info.BasePath+'/plan.do',
				params:{
					method:'deleteModuleItems',
					moduleId:_id,
					moduleItemIds:Ext.util.JSON.encode(moduleItemIds)
				},
				success:function(_response){
					if(Ext.decode(_response.responseText).success){
						alert('删除成功。');
						Ext.getCmp('grid').getStore().reload();
						var _store=Ext.getCmp('editGrid').getStore();
					    _store.load({
					        params: {
					            start: 0,
					            limit: 20
					        }
					    });
					}else{
						alert('删除失败。');
					}
				}
			});
 	      }},'->',{
				text:'将该模板添加至随访项目',
				handler:function(){
					var ss=Ext.getCmp('editGrid').getSelectionModel().getSelections();
					if(ss.length==0){
						alert('请先选择一条记录。');
						return;
					}
					var checkSelect = new Array();
					for(var i = 0; i < ss.length; i++) {
						checkSelect[i]=ss[i].get("id");
					}
					window.opener.refreshItem(Ext.util.JSON.encode(checkSelect));
					//window.open(App.App_Info.BasePath +'/module/plan/addPlanItem.html?pid='+_pid + '&KID=' + KID+'&moduleIds='+Ext.util.JSON.encode(checkSelect),'增加随访计划');
					window.close();
				}
			}
 ]
});

    
    var comStore = new Ext.data.SimpleStore({
		fields:['value','text'],
		data:[['0','随访计划中'],['1','随访执行中'],['2','结果已关联']]
	});
    // ['0','随访计划未开始'],['1','随访计划阶段中'],['2','随访计划完成']
    var cricleStore = new Ext.data.SimpleStore({
		fields:['value','text'],
		data:[[YEAR,'年'],[MONTH_OF_YEAR,'月'],[WEEK_OF_YEAR,'周'],[DAY_OF_YEAR,'天']]
	}); 
	
    // comStore.load({});
    var editGrid = new Ext.grid.EditorGridPanel({
		id: 'editGrid',
		title: '随访模板项目',
		border: false,
		autoScroll: true,

		columns:[

			new Ext.grid.RowNumberer(),
			sm2,
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
		        dataIndex: 'checkItemName',
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
		],
		sm:sm2,
		ds:moduleItemDS,
		clicksToEdit:2,
		viewConfig:{
			forceFit:true
		},
		tbar:iTbar,
		 bbar: new Ext.PagingToolbar({
	            pageSize: 20,
	            store: moduleItemDS,
	            displayInfo: true,
	            displayMsg: '显示第<font color="red"> {0} </font>条' +
	            '到<font color="red"> {1} </font>条记录，' +
	            '一共<font color="red"> {2} </font>条',
	            emptyMsg: "没有记录"
	        })
	});
    
    //editGrid 的afterEdit事件
     editGrid.on("afteredit",afterEdit,editGrid);
    function afterEdit(e){
    	if(!confirm("是否修改?")){
	    	e.value = e.originalValue;
    		return;
    	}
    	var record = e.record;  //当前被修改的行
    	var field = e.field;  //正在被编辑的字段名
    	var value = null;
    	if(field=="circle"){
    		value = record.get("circle");
    	}else if(field=="circleType"){
    		value = record.get("circleType");
    	}
    	var id = record.get("id");
    	var store = Ext.getCmp('editGrid').getStore();
        
    	Ext.Ajax.request({
			url:App.App_Info.BasePath+'/plan.do',
			params:{
				method:'updateModuleItem',
				id:id,
				field:field,
				value:value
			},
			success:function(_response){
				/*if(Ext.decode(_response.responseText).success){
					alert('编辑成功。');
					Ext.getCmp('grid').getStore().reload();
					var _store=Ext.getCmp('editGrid').getStore();
				    _store.load({
				        params: {
				            start: 0,
				            limit: 20
				        }
				    });
				}else{
					alert('编辑失败。');
				}*/
			}
		});
    }
   
  // 发送数据到服务器端进行更新
   /* function updateData(modified) {
           var json = [];
		 * for(var i = 0; i < checkSelect.length; i++) {
		 * json.push(checkSelect[i]); }
		 
           if (checkSelect.length > 0) {
               Ext.Ajax.request({
                   url: App.App_Info.BasePath + '/plan.do',
                   params: { data: Ext.util.JSON.encode(checkSelect), 
            	   method:_method,
       			
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
       			outRegno:outRegno,
       			KID:KID,
       			paId:_paId,
           		start:0,
           		limit:20},
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
               Ext.Msg.alert("警告", "没有任何操作改变！");
           }
       }*/
    
    new Ext.Viewport({
        layout: 'border',
        items: [{
			region:'west',
			layout:'fit',
            width: 600,
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
