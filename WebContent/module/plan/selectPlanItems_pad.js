
var WEEK_OF_YEAR = '3';
var DAY_OF_YEAR = '6';
var YEAR = '1';
var MONTH_OF_YEAR = '2';
var patient = App.util.getHtmlParameters('patient');
var _flag =  App.util.getHtmlParameters('_flag');
var planId =  App.util.getHtmlParameters('planId');
var circle = App.util.getHtmlParameters('circle');
var circleType = App.util.getHtmlParameters('circleType');
var login = App.util.getHtmlParameters('login'); // add by cheng jiangyu  2011-8-26 会员看自己的随访计划
var beginDate = App.util.getHtmlParameters('beginDate');
/*beginDate = decodeURI(beginDate);
alert(beginDate);*/
var planDate = App.util.getHtmlParameters('planDate');
//planDate = decodeURI(planDate);

var _method = "";
if(_flag == "checkReport") {
	_method	= "linkReport";
}
else if(_flag == "plan"){
	
}
else if(_flag == "omc"){
	_method	= "linkOMC";
}


Ext.onReady(function(){
    layout();
    $('input[name="itemPlan"]').val((planDate== null || planDate=="") ? "" :　planDate);            
	$('input[name="itemBegin"]').val((beginDate == null || beginDate=="") ? "" :　beginDate);
	var ctype = circleType;
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
	$('input[name="itemCircle"]').val(circle+ "" + ctype);
});


function refreshItem() {
	var _store=Ext.getCmp('editGrid').getStore();
    _store.load({
        params: {
            start: 0,
            limit: 20
        }
    });
    $('input[name="itemPlan"]').val((planDate== null || planDate=="") ? "" :　planDate);            
	$('input[name="itemBegin"]').val((beginDate == null || beginDate=="") ? "" :　beginDate);
	var ctype = circleType;
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
	$('input[name="itemCircle"]').val(circle+ "" + ctype);
}



function layout(){
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
	        },{
	        	name: 'resultURL'
	        },{
	        	name : 'visitDate'
	        },{
	        	name:'planDate'
	        },{
	        	name:'visitState'
	        },{
	        	name:'recordURL'
	        }, {
	            name: 'checkItemName'
	        } ,{
	            name: 'checkItemCode'
	        }])
	    });
	    
	    planItemDS.baseParams = {
	            method: 'getAllPlanItems',
	            planId: planId,
	            circle:circle,
	            circleType:circleType
	        };
	
	    planItemDS.load({
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
			title: '随访计划项目',
			border: false,
			autoScroll: true,
	
			columns:[
				new Ext.grid.RowNumberer(),
				sm2,
				/*{
			        header: '检查项目编号',
			        dataIndex: 'checkItemCode',
			        width:40,
			       // editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
			        renderer: function(value){
			            return value == null ? "" : value;
			        }
			    }, */{
			        header: '检查项目',
			        dataIndex: 'checkItemName',
			        width:60,
			       // editor:new Ext.grid.GridEditor(new Ext.form.TextField()),
			        renderer: function(value){
			    	return value == null ? "" : value;
			        }
			    }, 
			    {
			        header: '实际来访日期',
			        dataIndex: 'visitDate',
			        sortable:true,
			        width:40,
			        /* editor:new Ext.grid.GridEditor(new Ext.form.TextField()), */
			        renderer: function(value){
			    	if (value) {
		                try {
		                    return new Date(value == null ? "" : value.time).format('Y-m-d')
		                } 
		                catch (err) {
		                }
		            }
			        }
			    },{
			        header: '随访状态',
			        dataIndex: 'visitState',
			        width:40,
			        
			        renderer: function(value){		    	
			    	    if(value == 1) {
			    	    	return "<font color='#CDCD00'>提前来访</font>";
			    	    }
			    	    else if(value == 2) {
			    	    	return "<font color='#CDCD00'>按期来访</font>";
			    	    }
			    	    else if(value == 3) {
			    	    	return "<font color='#CDCD00'>超期来访</font>";
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
			    },
			     {
			        header: '报告时间',
			        dataIndex: 'reportDate',
			        sortable:true,
			        width:50,
			        renderer: function(value){
			    	    return value == null ? "" : value;
			        }
			    },{
			        header: '备注',
			        dataIndex: 'comment',
			        width:60,
			        renderer: function(value){
			    	   return value;
			        }
			    }
			],
			sm:sm2,
			ds:planItemDS,
			clicksToEdit:2,
			viewConfig:{
				forceFit:true
			},
			 tbar: ['-','<font color="blue">计划起始日期</font>：', '<font color="red"><input type="text" size="12" name="itemBegin" readOnly=true></font>',
		               '<font color="blue">计划随访日期</font>：', '<font color="red"><input type="text" size="12" name="itemPlan" readOnly=true></font>',
		               '<font color="blue">周期</font>：', '<font color="red"><input type="text" size="5" name="itemCircle" readOnly=true></font>'
		               
	],
	
				  listeners : {
				  'render' : function(){
				    iTbar.render(this.tbar); 
				    if(iTbar_new!=null){
						iTbar_new.render(this.tbar); 
					}
				  }
				  },
	
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
	    
	    
	    var iTbar= (_flag == "omc")? new Ext.Toolbar({
	   	 items:[
				'<font color="">请勾选关联随访项目</font>','-', {
		            text: "保存关联",
		            handler: function() {
		                var modified = Ext.getCmp('editGrid').getStore().modified;
		                  updateData(modified);
		            }
		          }	,'->',{
		              text: "查看门诊记录",
		              handler: function() {
		        	    var ss=Ext.getCmp('editGrid').getSelectionModel().getSelections();
						if(ss.length==0){
							alert('请先选择一条记录。');
							return;
						}
		                
						var reportURL = ss[0].get('recordURL');
						// alert(reportURL + "|" + ss[0].get('state'));
						if((ss[0].get('state') != 2 && ss[0].get('state') != 1) || reportURL == null || reportURL.length < 1) {
						   alert('无门诊记录');
						   return;
						}
						
						//window.open(App.App_Info.BasePath + "/module/Followup/" + reportURL,'门诊记录','width=950px;height=650px');
						App.util.addNewMainTab("/module/Followup/" + reportURL,'门诊记录');
		              }},'-',{
		              text: "查看报告",
		              handler: function() {
		        	    var ss=Ext.getCmp('editGrid').getSelectionModel().getSelections();
						if(ss.length==0){
							alert('请先选择一条记录。');
							return;
						}
		                
						var reportURL = ss[0].get('resultURL');
						// alert(reportURL + "|" + ss[0].get('state'));
						if(ss[0].get('state') != 2 || reportURL == null || reportURL.length < 1) {
						   alert('无报告');
						   return;
						}
						
						App.util.addNewMainTab(reportURL,'检查结果');
		              }}
	
	   	 ] }) : new Ext.Toolbar({
	   		 items:['-',{
	       text: "查看门诊记录",
	       handler: function() {
	 	    var ss=Ext.getCmp('editGrid').getSelectionModel().getSelections();
				if(ss.length==0){
					alert('请先选择一条记录。');
					return;
				}
	         
				var reportURL = ss[0].get('recordURL');
				// alert(reportURL + "|" + ss[0].get('state'));
				if((ss[0].get('state') != 2 && ss[0].get('state') != 1) || reportURL == null || reportURL.length < 1) {
				   alert('无门诊记录');
				   return;
				}
				//window.open(App.App_Info.BasePath + "/module/Followup/" + reportURL,'门诊记录','width=950px;height=650px');
				App.util.addNewMainTab("/module/Followup/" + reportURL,'门诊记录');
	       }},'-',{
	       text: "查看报告",
	       handler: function() {
	 	    var ss=Ext.getCmp('editGrid').getSelectionModel().getSelections();
				if(ss.length==0){
					alert('请先选择一条记录。');
					return;
				}
	         
				var reportURL = ss[0].get('resultURL');
				// alert(reportURL + "|" + ss[0].get('state'));
				if(ss[0].get('state') != 2 || reportURL == null || reportURL.length < 1) {
				   alert('无报告');
				   return;
				}
				
				App.util.addNewMainTab(reportURL,'检查结果');
	       }}]
	   	 });
	   	 
	   	 var iTbar_new =null;
	   	if(_flag != "omc"&&login==null){  //add by cheng jiangyu 2011-08-26  如果是医生登陆，则增加关联的三个按钮
	   		//iTbar.hide();
	   		/*Ext.getCmp("linkCheckResult").hide();
	   		Ext.getCmp("linkPacs").hide();
	   		Ext.getCmp("linkMaidi").hide();*/
	    	/*iTbar.items.get(0).hide();
	    	iTbar.items.get(1).hide();
	    	iTbar.items.get(2).hide();*/
	   		iTbar_new = new Ext.Toolbar({
	      		 items:['-',   
	        		      {
	   	         text: "关联检查结果",
	   	         id:"linkCheckResult",
	   	         handler: function() {
	   	     	  var ss=Ext.getCmp('editGrid').getSelectionModel().getSelections();
	   					if(ss.length==0){
	   						alert('请先选择一条记录。');
	   						return;
	   					}
	   					
	   					if((ss[0].get('state') != 1 && ss[0].get('state') != 2)){
	   				     	if(!confirm("实际未来访，是否要关联检查结果？"))
	   						return;
	   				     }
	   				  
	   	     	  window.open('../InHospitalCase/Liver/CheckReport/combinationList.html?flag=add&patientId='+ _paId + '&KID=' +KID+ '&data='+ Ext.util.JSON.encode(checkSelect)+'&link=true','','width=950px;height=650px');
	                }
	            }
	   	,'-', {
	        text: "关联放射影像结果",
	        id:"linkPacs",
	       handler: function() {
	   	var sure;
	   		var ss=Ext.getCmp('editGrid').getSelectionModel().getSelections();
	   		if(ss.length==0){
	   			alert('请先选择一条记录。');
	   			return;
	   		}
	   		if((ss[0].get('state') != 1 && ss[0].get('state') != 2)){
	   	     	if(!confirm("实际未来访，是否要关联检查结果？"))
	   			return;
	   	     }
	        if(2==ss[0].get('state') && !confirm("已存在关联结果，是否覆盖以前的关联？")){
	        	return;
	        }
	        var returnValue = window.open('../checkreport/pacs.html?id=' + patient.patientId 
	        		+ '&type=pacs'
	        		+ '&flag=add'
	        		+ '&data=' + Ext.util.JSON.encode(checkSelect)
	        		+ '&name=' + patient.patientName
	        		+ '&no=' + patient.patientNo
	        		+ '&link=true','','dialogWidth=950px;dialogHeight=650px');
	        }
	      }
	   	,'-', {
	        text: "关联综合影像结果",
	        id:"linkMaidi",
	        handler: function() {
	   	            var ss=Ext.getCmp('editGrid').getSelectionModel().getSelections();
	   				if(ss.length==0){
	   					alert('请先选择一条记录。');
	   					return;
	   				}
	   				
	   				if((ss[0].get('state') != 1 && ss[0].get('state') != 2)){
	   			     	if(!confirm("实际未来访，是否要关联检查结果？"))
	   					return;
	   			     }
	   	            if(2==ss[0].get('state') && !confirm("已存在关联结果，是否覆盖以前的关联？")){
	   	            	return;
	   	            }
	   	            var returnValue = window.open('../checkreport/maidi.html?id=' + patient.patientId 
	   	            		+ '&type=maidi'
	   	            		+ '&flag=add'
	   	            		+ '&data=' + Ext.util.JSON.encode(checkSelect)
	   	            		+ '&name=' + patient.patientName
	   	            		+ '&no=' + patient.patientNo
	   	            		+ '&link=true','','dialogWidth=950px;dialogHeight=650px');
	        }
	      }]
	     });
	    }
	   	
	   	new Ext.Viewport({
	        layout: 'border',
	        items: [{
				region:'center',
				layout:'fit',
	            width: 700,
				split:true,
				autoScroll:true,
				items:editGrid
			}]
	    });
}
   	
 // 发送数据到服务器端进行更新
    function updateData(modified) {
           var json = [];
      /*
		 * for(var i = 0; i < checkSelect.length; i++) {
		 * json.push(checkSelect[i]); }
		 */
           
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
       }