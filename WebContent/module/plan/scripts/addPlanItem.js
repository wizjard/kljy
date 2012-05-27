/*
 '-', {
    	            text: '发送短信',
    	            handler: function(){
    					 var pid=_pid;
    					 var send=function(id,ctx,callback){
    					 	Ext.Ajax.request({
    						 	url:App.App_Info.BasePath + '/biomedical.do',
    						 	params:{
    						 		method:'sendMsgByPatientId',
    						 		id:id,
    						 		appendix:'北京佑安医院'+deptName+grounpName, //加在短信内容后面的落款
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
    					 	title:'给【'+patientName+'】发送短信',
    					 	closeAction:'close',
    					 	width:400,
    					 	height:200,
    					 	autoHeight:true,
    					 	layout:'anchor',
    					 	items:[{
    					 		xtype:'textarea',
    					 		width:390,
    					 		height:200,
    					 		value:'患者您好,',
				 		        style:'color:blue',
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
    							 		text:'当前字数:5字'
    							 	},{
    							 		columnWidth:0.3,
    							 		xtype:'label',
    							 		width:20,
    							 		text:'短信条数:1条'
    							 	},{
    							 		columnWidth:0.4,
    							 		xtype:'label',
    							 		width:20
    							 		//text:'短信总字数上限:100字'
    						 	}]
    					 	}],
    					 	buttonAlign:'center',
    					 	buttons:[
    					 		{
    					 			text:'发送',
    					 			handler:function(){
    					 			   if(mobilePhone==''||mobilePhone==null||(mobilePhone!=''&&!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(mobilePhone)))) { //判断如果会员手机号不为空并且不符合手机号格式，要提示
    					 			       alert('会员'+patientName+'手机号码为空或格式不正确');
    					 			       return;
    					 			     }
    					 			   
    					 				var win=this.ownerCt;
						 				var value=win.items.get(0).getValue();
						 				var headAndAppendix = '北京佑安医院'+deptName+grounpName+value;  //自动的短信头和落款 加上短信正文
						 			     if(value&&value.length>5){ 
						 			        if(Math.ceil(headAndAppendix.length/64)>1){   //看发送的短信字数是否超过一条短信
								 			       if(confirm('您确定发送吗？该条短信将分'+Math.ceil(headAndAppendix.length/64)+'条发送')){
								 			           send(pid,value,function(data){
							 							   win.close();
							 					  	   });
								 			       }else{
								 			         return;
								 			       }
						 			         }else{
						 			           send(pid,value,function(data){
						 							   win.close();
						 					    });
						 			         }
						 			       	 
						 			    }else{
						 					 alert('信息正文不能为空。');
						 				 }
    					 			}
    					 		}
    					 	]
    					 }).show();
    	            }
    	        },
*/

var moduleIds = null;  //随访模板Ids

Ext.onReady(function(){
	initTree();
	initGrid();
    layout();
    tree.hide();
    changeLeft();
});

//获得医生所在责任科室和小组参数  by cheng jiangyu 2011-9-25
var deptName= App.util.getHtmlParameters('deptName');
deptName = decodeURI(deptName);
var grounpName= App.util.getHtmlParameters('grounpName');
grounpName = decodeURI(grounpName);

var tree;
var grid;
var editGrid;

var _id= App.util.getHtmlParameters('planId');
var _pid= App.util.getHtmlParameters('pId');
var KID= App.util.getHtmlParameters('KID');
var patientName= App.util.getHtmlParameters('patientName');  //获得某个会员的姓名
patientName = decodeURI(patientName);  

var mobilePhone= App.util.getHtmlParameters('mobilePhone');  //获得某个会员的手机号
mobilePhone = decodeURI(mobilePhone);
	
var WEEK_OF_YEAR = '3';
var DAY_OF_YEAR = '6';
var YEAR = '1';
var MONTH_OF_YEAR = '2';
var itemCount = 0;
var added = [];
var moved = [];
var frameSrc =  "<iframe src='../plan/addPlan.html?KID=" + KID + "&planId=" + _id + "&pid=" + _pid + "'  width='100%' height='50%' scroll='no'></iframe>"
var _planDate;

function refreshItem(moduleIds) {
	moduleIds = moduleIds;
	if(moduleIds!=null){
		addModuleItems(moduleIds); 
	}
	
}

//将模板中的项目导入为检查项目
function addModuleItems(moduleIds){
    	$.post(
		App.App_Info.BasePath+'/plan.do',
		{
			method:'getCheckItemsByModule',
			moduleIds:moduleIds 
		},
		function(response){
			if(response.success){
			    var gstore = Ext.getCmp('grid').getStore();
			    var editStore=Ext.getCmp('editGrid').getStore();
			    var CheckItem = gstore.recordType;
		        var PlanItem = editStore.recordType;
		        var id_a;
		        var ORDERNAMEK_a;
		        var ORDERCODE_a;
		        var p;
			    var data = JSON.parse(response.data);
			    //alert(data);
				if(data != undefined){
					for(var i=0,size=data.length;i<size;i++){
						ORDERCODE_a = data[i].checkItemCode;
			            ORDERNAMEK_a = data[i].checkItemName;
			            id_a = data[i].checkId;
			            p = new PlanItem({		                             	
				            checkItemCode: ORDERCODE_a,
			               	checkItemName: ORDERNAMEK_a,
			               	checkId:id_a,
			               	comment: data[i].comment,
			               	reportDate: "",
			               	circle: data[i].circle,
			               	circleType: data[i].circleType,
			               	crossd:7,
			               	state:0,
			               	planDate:_planDate
			            });
			            //if(editStore.find("checkItemCode",ORDERCODE_a ,0) < 0 && editStore.find("checkItemName",ORDERNAMEK_a ,0)  < 0) {
			            if(editStore.find("checkItemCode",ORDERCODE_a ,0) < 0) {
				             editStore.insert(0, p);
			                 itemCount = itemCount + 1;
			                 added.push(p);
			            }else {
			            	alert(ORDERNAMEK_a + " 检查项目已存在");
			            }
					}
					editStore.applySort();
				}
			}
		},
			'json'
			)
}


function getItemCount() {
   return	 Ext.getCmp('editGrid').getStore().getCount();
}

function changeLeft() {
	 new Ext.Viewport({
	        layout: 'border',
	        items: [{
				region:'west',
				id:'treeIt',
				layout:'fit',
	            width: 450,
				split:true,
				autoScroll:true,
				items:[grid,tree]		
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

function checkNumType1(num, check) {
	   if(num == "") {
	      return false;
	   }
	   //liugang 修改，可以输入所有的正整数
	    var no = /^[1-9]\d*$/;
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
        	   $.post(
        			   App.App_Info.BasePath + '/plan.do',
        			   { data: Ext.util.JSON.encode(json), 
                    	   method:"updatePlanItem",
                    	   pid:_pid,
                    	   add: Ext.util.JSON.encode(add), 
                    	   move: Ext.util.JSON.encode(move), 
               			    planId:_id, 
               			    isAdd:isAdd,
                       		start:0,
                       		limit:20
                       		},
                       		function(data){
                    			if(data.success){
                    				
                    				 alert("操作成功！" + data.msg);
                    				 //window.close();
                    			}
                    			else {
                    				alert("操作失败，请稍后再试！");
                    			}
                       		}
                       		,
                    		'json'
                    	);
        		
           }
           else {
             //  Ext.Msg.alert("警告", "没有任何操作改变！");
           }
       }

    function initTree() {
    	var url = {};
    	url.baseUrl = App.App_Info.BasePath + '/plan.do?method=';
    	url.getChildren = url.baseUrl + 'getCheckChildren';
    	Ext.ns('Plan.Check.Tree');
    	Plan.Check.Tree = Ext.extend(Ext.tree.TreePanel, {
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
    	      
    	        Ext.apply(this, {
    	            root: root,
    	            rootVisible: false,
    	            border: false,
    	            autoScroll: true,
    	            containerScroll: true,
    	            loader: loader,
    	            title: '检查项目列表',
    	            tbar: [ /*{
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
    			  },*/'->',{
    				text:'转换显示模式',
    				tooltip:'转换显示模式',
    				handler:function(){
    				  //alert(Ext.getCmp('treeIt').items + "|" + grid);
    				  tree.hide();
    				  grid.show();
    				 // Ext.getCmp('treeIt').items.remove(tree);
    				  Ext.getCmp('treeIt').layout = "fit";
    				  //changeLeft(grid);
    				  //tree.hide();
    				  //grid.show();
    				}
    			  },{
    				xtype:'tbseparator'
    			  },{
    		            text: '增加检查项目',
    		            handler: function(){
    				  
    				    var ss = Ext.getCmp('tree').getSelectNode();
    				    /*多选测试
    				    var ds = tree.getChecked();
    			        alert(ds);
    			        */
    		            if (ss== null) {
    		                alert('请至少选择一条记录。');
    		                return;
    		            }
    		            
    				    var editStore=Ext.getCmp('editGrid').getStore();

    			        var PlanItem = editStore.recordType;
    			        var id_a;
    			        var ORDERNAMEK_a;
    			        var ORDERCODE_a;
    			        var p;
    			        
    		          //  for(var i = 0; i < ss.length; i++) {
    			        id_a = ss.id;
    		              ORDERNAMEK_a = ss.text;
    		              
    		              if(id_a.indexOf("_") > 0) {
    		            	  ORDERCODE_a = id_a.substring(id_a.indexOf("_") + 1, id_a.length);
    		            	  id_a = id_a.substring(0, id_a.indexOf("_"));
    		            	   
    		              }
    		              else {
    		            	  ORDERCODE_a =  ss.id;
    		            	  
    		              }
    		              
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
    		            
    		            editStore.applySort();

    		            }
    		        }
    		        ],
    	            bodyStyle: 'margin:10px'
    	        });
    	       
    	        Plan.Check.Tree.superclass.initComponent.call(this, arguments);
    	    },
    	    getSelectNode: function(){
    	        return this.getSelectionModel().getSelectedNode();
    	    }
    	});
    	
    	 tree = new Plan.Check.Tree({
    	        id: 'tree'
    	    });
    	    tree.render(Ext.getBody());
    	    tree.root.expand();
    	    tree.on("dblclick", function(node) {
    	            //var ds = tree.getChecked();
    	            //alert(node);
    	            if(!node.isLeaf()) {
    	                //alert(node.isLeaf());
    	                return;
    	            }
    	            
    			    var editStore=Ext.getCmp('editGrid').getStore();
    			    var PlanItem = editStore.recordType;
    			    var id_a;
    			    var ORDERNAMEK_a;
    			    var ORDERCODE_a;
    			    var p;
    			    id_a = node.id;
    		        ORDERNAMEK_a = node.text;
    		        if(id_a.indexOf("_") > 0) {
    		            ORDERCODE_a = id_a.substring(id_a.indexOf("_") + 1, id_a.length);
    		            id_a = id_a.substring(0, id_a.indexOf("_"));
    		        }
    		        else {
    		            ORDERCODE_a =  node.id;    	  
    		        }
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
    		            //if(checked==false) {
    		                //alert(checked);
    		                //editStore.remove(p);
    		                
    		                //alert(checked);
    		                //editStore.remove(p);
    		                //moved.push(p);
    		           // }
    		            alert(ORDERNAMEK_a + " 检查项目已存在");
    		        }
    		        editStore.applySort();
    	        });
    }

    function initGrid() {
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
        var sm = new Ext.grid.CheckboxSelectionModel({
            singleSelect: false
        });
        var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), new Ext.grid.CheckboxSelectionModel({
            singleSelect: false
        }),
       // {
        //    header: '选择',
       //     dataIndex: 'ORDERCODE',
      //      width:10,
     //       renderer: function(value){
        	   // return '<input type="checkbox" name="ordercode" value="' + value + '" />';
     //   	    return value;
     //       }
      //  }, 
        {
            header: '检查项目名称',
            dataIndex: 'ORDERNAMEK',
            renderer: function(value){
        	return value;
            }
        }]);


        grid = new Ext.grid.GridPanel({
            id: 'grid',
            title: '检查项目列表',
            sm: sm,
            cm: cm,
            ds: ds,
            autoScroll: true,
            viewConfig: {
                forceFit: true
            },
            listeners : {
                'celldblclick' : function (thiz, row, col, e) {
                    if(col==2) {
                       var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
    			    
    			    //var cs = Ext.getCmp('grid').getAllCheckItems();
    			    //alert(cs.length);
    			    //var a = $('input[name="ordercode"]:checked');
    			    //alert(a.length);
    			    
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
                        
    	            }
    	            editStore.applySort();
                    }
                }
            },
            //sm: sm,
            tbar: [ {
    				xtype:'textfield',
    				width:95,
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
    				text:'转换显示模式',
    				tooltip:'转换显示模式',
    				handler:function(){
    			  grid.hide();
				  tree.show();
    				}
    			  },{
    			xtype:'tbseparator'
    		  },{
    	            text: '增加检查项目',
    	            handler: function(){
    			  
    			    var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
    			    
    			    //var cs = Ext.getCmp('grid').getAllCheckItems();
    			    //alert(cs.length);
    			    //var a = $('input[name="ordercode"]:checked');
    			    //alert(a.length);
    			    
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
                        
    	            }
    	            editStore.applySort();
    	
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
        
       // grid.hide();
    }
    
function layout(){
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

    /*
     * 保存模板弹出框
     */
    var SearchButton = Ext.extend(Ext.Button, {
        text: '保存为模板',
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
                    items: [
                    {
                        fieldLabel: '模板名称',
                        name: 'moduleName'
                    },{
                        xtype: 'panel',
                        border: false,
                        html: ''
                    }
                    ]
                },
                buttons: [{
                    text: '保存',
                    handler: function(){
                        var values = o.sw.items.get(0).getForm().getValues();
                        var moduleName = values.moduleName;
         			   if(moduleName==null||moduleName==""){
         				   alert('请输入检查项目模板名称');
         				   return; 
         			   }
         				var _store = Ext.getCmp('editGrid').getStore();
         				if(_store.getCount()==0){
         					alert('您还没有选择任何检查项目');
         					return;
         				}
         				var selectCheckItemIdArr = [];  //选中的检查项目Id用数组存放
         				for(var i=0;i<_store.getCount();i++){
         					selectCheckItemIdArr.push(_store.getAt(i).data);
         				}
         				Ext.Ajax.request({
         					url:App.App_Info.BasePath+'/plan.do',
         					params:{
         						method:'addPlanModule',
         						moduleName:moduleName,
         						checkItemsIdArr:Ext.util.JSON.encode(selectCheckItemIdArr)
         					},
         					success:function(_response){
         						if(Ext.decode(_response.responseText).success){
         							alert('添加成功。');
         							Ext.getCmp('grid').getStore().reload();
         							var _store=Ext.getCmp('editGrid').getStore();
         						    _store.load({
         						        params: {
         						            start: 0,
         						            limit: 20
         						        }
         						    });
         						}else{
         							alert('添加失败。');
         						}
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


     editGrid = new Ext.grid.EditorGridPanel({
		id: 'editGrid',
		title: '随访计划项目',
		border: false,
		autoScroll: true,

		cm:new Ext.grid.ColumnModel([
			//new Ext.grid.RowNumberer(),
			/*
			{
		        header: '检查项目编号',
		        dataIndex: 'checkItemCode',
		        width:30,
		       // editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
		        renderer: function(value, cell, record) {				
		           // return (value == null || value.ORDERCODE == null || value.ORDERCODE == "") ? record.data.checkItemCode : value.ORDERCODE;
				return value;
		        }
		    },
		    */
		    {
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
		        width:25,
		        renderer: function(value){
		    	    return value;
		        }
		    }, 
		    {
		        header: '单位',
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
		            	return '<font color="#EE0000">周</font>';
		            }
		            else if(value == DAY_OF_YEAR) {
		            	return '<font color="blue">天</font>';
		            }else {
		            	return '<font color="grey">请选择</font>';
		            }
		        }
		    },{
		        header: '计划来访日期',
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
		        header: '随访误差天数',
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
	    	    	return "<font color='#0968F7'>提前来访</font>";
	    	    }
	    	    else if(value == 2) {
	    	    	return "<font color='#0968F7'>按期来访</font>";
	    	    }
	    	    else if(value == 3) {
	    	    	return "<font color='#0968F7'>超期来访</font>";
	    	    }
	    	    else if(value == 4) {
	    	    	return "<font color='green'>随访结束</font>";
	    	    }
	    	    else if(value == 5) {
	    	    	return "<font color='#B8B8B8'>超期未来访</font>";
	    	    }		    	    
	    	    else {
	    	    	return "<font color='#EE0000'>随访计划中</font>";
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
        },'-', new SearchButton({
            handler: function(){
            if (!this.sw) {
                this.initSw();
            }
            this.toggleSw();
        }
    })
     ,'-',{
			text:'引用模板',
			tooltip:'我的随访模板',
			handler:function(){
        	  var url = App.App_Info.BasePath +'/module/plan/selectModule.html';
        	  window.open(url, 'module', 'toolbar=no,scrollbar=yes, top=200, left=100,width=1200,height=500');
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
	  },{			
		  xtype:'textfield',
			width:60,
			emptyText:'前后天数',
			id:'dday'
		  },{
	text:'更改随访误差天数',
	tooltip:'更改随访误差天数',
	handler:function(){
		  
		var _val2=Ext.getCmp('dday').getValue().trim();
		if(!checkNumType1(_val2)) {
		   return;
		}
		
		var _store=Ext.getCmp('editGrid').getStore();
		var PlanItem = _store.recordType;
		_store.each(function(record, index, st){
			var isChange = false;
			
			if(record.data.crossd != _val2) {
				record.data.crossd = _val2;
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
}

