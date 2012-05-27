/*
 * 查找医生的责任科室和所在小组js  by cheng jiangyu 2011-9-25
 
var deptcodenameList = new Array();		
var deptcode;	
var deptName;
var grounpName;
var deptcodenameGrounpList = new Array();
var deptcodeGrounp ;	
		
//通过医生的ID查找医生所在的责任科室列表
function getAllDeptment(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentHISAction.do',
		params:{
			method:'findDoctorDeptMemberByDoctorId',
			doctorId:top.USER.id
		},
		sync:true,
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			for(var i=0,size=list.length;i<size;i++){
				var deptcodenameList1 = new Array();
				deptcodenameList1.push(list[i].deptCode);
				deptcodenameList1.push(list[i].deptName);
				//deptcodenameList1.push(list[i].grounpName);
				deptcodenameList.push(deptcodenameList1);
			}
			
			//liugang 2011-08-22 start
			if(deptcodenameList != ""){
				deptcode = deptcodenameList[0][0];
				//deptName = deptcodenameList[0][1];
			}else{
				alert("当前医生不属于责任小组医生，没有会员列表");
				return ;
			}
			//liugang 2011-08-22 end
			//科室编码动态改变加载对应的数据
		}
	});
}
	

//查找某个科室下的责任小组
function getAllDeptmentGrounp(){
	deptcodenameGrounpList = new Array();
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentHISAction.do',
		params:{
			method:'findDoctorGrounpByMemberByDoctorId',
			deptCode:deptcode,
			doctorId:top.USER.id
		},
		sync:true,
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			for(var i=0,size=list.length;i<size;i++){
				var deptGrounp = new Array();
				deptGrounp.push(list[i].id);
				deptGrounp.push(list[i].grounpName);
				deptcodenameGrounpList.push(deptGrounp);
			}
			deptcodeGrounp = deptcodenameGrounpList[0][0];
			grounpName = deptcodenameGrounpList[0][1];
		}
	});
}
*/
//----------------------------------------------------------

Ext.onReady(function(){
	// getAllDeptment(); //获得医生责任科室
   // getAllDeptmentGrounp(); //获得医生责任小组
    layout();
});


var KID = App.util.getHtmlParameters('KID');// 会员编号参数
var outRegno = App.util.getHtmlParameters('outRegno');
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

// 获得传过来的当前责任科室和小组参数  用于发短信  by 程江玉 2011-9-30
var deptName = App.util.getHtmlParameters('deptName');
deptName = decodeURI(deptName);
var grounpName = App.util.getHtmlParameters('grounpName');
grounpName = decodeURI(grounpName);

var login = App.util.getHtmlParameters('login'); //add by cheng jiangyu 2011-8-26  会员看自己的随访计划
 //如果是会员登陆 2011-08-30 liugang
if(login!=null&&login=="member"){
	_paId = top.Member.patientId;
}
var patient;

var _method = "";

if(_flag == "checkReport") {
	_method	= "linkReport";
}
else if(_flag == "plan"){
	
}
else if(_flag == "omc"){
	_method	= "linkOMC";
}

var WEEK_OF_YEAR = '3';
var DAY_OF_YEAR = '6';
var YEAR = '1';
var MONTH_OF_YEAR = '2';

var _beginDate;


//init patient's information
$.post(
		App.App_Info.BasePath+'/patient.do',
		{
			method:'findById',
			login:login,
			id:_paId
		},
		
		function(data){
			var _patient=JSON.parse(data.data);
			setTimeout(function(){				
				$('input[name="patientName1"]').val(_patient.patientName);
				$('input[name="patientNo1"]').val(_patient.patientNo);
				$('input[name="patientSex1"]').val(_patient.gender == '1' ? '男' : '女');
				$('input[name="patientBrith1"]').val(_patient.birthday);					
			
			}, 300);
			patient = _patient; 
		},
		'json'
	);

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

function refreshItem() {
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
	            name: 'planId'
	        }, {
	            name: 'planTime'
	        }, {
	            name: 'circleType'
	        },  {
	            name: 'beginDate'
	        }, {
	            name: 'circle'
	        }, {
	            name: 'state'
	        }, {
	            name: 'stateStr'
	        }, {
	            name: 'planDate'
	        }, {
	            name: 'visitState'
	        }, {
	            name: 'resultState'
	        },{
	        	name:'visitDate'
	        }])
	    });
	    ds.baseParams = {
	        method: 'getPlansGroup',
	        search: '',
	        login:login, //add by cheng jiangyu 2011-8-26
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
	    //cm表示ClumnModel，定义表格的表头
	    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),  {
	        header: '编号',
	        dataIndex: 'planTime',
	        sortable:true,
	        width:25,
	        renderer: function(value){
	            return "第" +  value + "次";
	        }
	    }, {
	        header: '随访计划设置日期',
	        dataIndex: 'beginDate',
	        sortable:true,
	        width:40,
	        renderer: function(value){
	            if (value) {
	                try {
	                    return new Date(value == null ? "" : value.time).format('Y-m-d')
	                } 
	                catch (err) {
	                }
	            }
	        }
	    }/*,{
	        header: '周期',
	        dataIndex: 'circle',
	        width:20,
	        renderer: function(value){
	            return value;
	        }
	    }*/,{
	        header: '周期',
	        dataIndex: 'circleType',
	        
	        width:25,
	        renderer: function(value, cell, record){
	    	    var re = "";
	    	    if(value == YEAR) {
	    	    	re = '<font color="#ee9a00">年</font>';   
	            }
	            else if(value == MONTH_OF_YEAR) {
	            	re = '<font color="green">月</font>';
	            }
	            else if(value == WEEK_OF_YEAR){
	            	re = '<font color="red">周</font>';
	            }
	            else if(value == DAY_OF_YEAR) {
	            	re = '<font color="blue">天</font>';
	            }else {
	            	re = '<font color="grey">请选择</font>';
	            }
	    	
	    	return record.data.circle + " " + re;
	        }
	    },{
	        header: '计划来访日期',
	        dataIndex: 'planDate',
	        sortable:true,
	        width:40,
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
	        header: '实际来访日期',
	        dataIndex: 'visitDate',
	        width:45,
	        sortable:true,
	        renderer: function(value){
	        if (value) {
               return value;
	        }
	    }
	    }, {
	        header: '随访状态',
	        dataIndex: 'visitState',
	        width:35,
	        sortable:true,
	        renderer: function(value, cell, record){
		    	if(value == 1) {
	    	    	//return "<font color='#CDCD00'>提前来访</font>";
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
	    },  {
	        header: '检查执行情况',
	        dataIndex: 'stateStr',
	        width:65,
	        sortable:true,
	        renderer: function(value, cell, record){
	           return value;
	        }
	    },  {
	        header: '关联结果',
	        dataIndex: 'resultState',
	        width:65,
	        sortable:true,
	        renderer: function(value, cell, record){
	           return value;
	        }
	    }]);

	    
	    var oneTbar=new Ext.Toolbar({

	    	 items:['-', {
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
		        },'-',{
					text:'日历查看随访计划',
					handler:function(){	
//		        	if(login!=null&&login=="member"){  //如果是会员登陆  add by cheng jiangyu 2011-08-26
//		        		_paId = patient.id;
//		        	}
		        	window.open(App.App_Info.BasePath + '/module/plan/showcalendar.html?paId='+_paId,'日历模式查看随访计划','width=950px,height=650px,resizable=yes');
					//App.util.addNewMainTab('/module/plan/showcalendar.html?paId='+_paId,'日历模式查看随访计划');
					}
				},'-',{
						xtype:'textfield',
						width:60,
						emptyText:'随访编号',
						id:'order-name'
					  },
					  {
							xtype:'textfield',
							width:150,
							emptyText:'随访计划设置日期(2010-08-08)',
							id:'beginDate'
						  },
					  {
							xtype:'textfield',
							width:60,
							emptyText:'随访周期',
							id:'cricleT'
						  },
					  {
							xtype:'textfield',
							width:150,
							emptyText:'实际来访日期(2010-08-08)',
							id:'visitD'
						  },{
					text:'搜索随访',
					tooltip:'搜索随访',
					handler:function(){
						var _val2=Ext.getCmp('order-name').getValue().trim();
						var _valDate=Ext.getCmp('beginDate').getValue().trim();
						var _visitD=Ext.getCmp('visitD').getValue().trim();
						var _cricleT = Ext.getCmp('cricleT').getValue().trim();
						
						if(!checkDate(_valDate)) {
						   Ext.getCmp('beginDate').setValue("");
						   return;
						}
						
						if(!checkDate(_visitD)) {
						Ext.getCmp('visitD').setValue("");
						   return;
						}
						
						/*if(!checkNumType(_val2)) {
						   return;
						}*/
						
						
						var _store=Ext.getCmp('grid').getStore();
						_store.baseParams = {
								method: 'getPlansGroup',
						        search: '',
						        login:login,  //edit by cheng jiangyu 2011-08-26
						        paId:_paId
						        
						    };
						_store.load({
						        params: {
						            start: 0,
						            limit: 20,
						            visitDate:_visitD,
						        cricleT:_cricleT,
								planTime:_val2,
								beginDate:_valDate
						        }
						    });
				
					}
				  }

	    	 ] });
	    var twoTbar=new Ext.Toolbar({
	    	  items:[ '-',{
					text:'删除随访计划',
					handler:function(){
						var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
						if(ss.length==0){
							alert('请先选择一条记录。');
							return;
						}
						
						if(ss[0].get('state') == 1 || ss[0].get('state') == 2) {
							alert('随访已执行，不能删除');
							return;
						}
						
						if(!confirm('确定要删除选中记录?'))return;
						Ext.Ajax.request({
							url:App.App_Info.BasePath+'/plan.do',
							params:{
								method:'deletePlan',
								id:ss[0].get('planId'),
								cricleD:ss[0].get('circle'),
								cricleTD:ss[0].get('circleType')
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
				},'-',{
					text:'编辑随访计划',
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
						App.util.addNewMainTab('/module/plan/addPlanItem.html?planId='+ss[0].get('planId') + "&KID=" + KID+'&patientName='+patient.patientName+'&deptName='+deptName+'&grounpName='+grounpName+'&mobilePhone='+patient.mobilePhone,'随访计划信息');
					}
				},'-',{
					text:'增加随访计划',
					handler:function(){
					if(!confirm('确定要增加随访计划?'))return;
						App.util.addNewMainTab('/module/plan/addPlanItem.html?pid='+_paId + "&KID=" + KID+'&patientName='+patient.patientName+'&deptName='+deptName+'&grounpName='+grounpName+'&mobilePhone='+patient.mobilePhone,'增加随访计划');
					}
				}, '-', {
		            text: '发送短信',
		            handler: function(){
						 var pid=patient.id;
						  if(patient.mobilePhone==''||patient.mobilePhone==null||(patient.mobilePhone!=''&&!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(patient.mobilePhone)))) { //判断如果会员手机号不为空并且不符合手机号格式，要提示
    					 			       alert('会员'+patient.patientName+'手机号码为空或格式不正确');
    					 			       return;
    					  }
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
						 	title:'给【'+patient.patientName+'】发送短信',
						 	closeAction:'close',
						 	width:400,
						 	height:200,
						 	autoHeight:true,
						 	layout:'anchor',
						 	items:[{
						 		xtype:'textarea',
						 		width:390,
						 		height:160,
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
								 		//text:'短信总字数上限:'+wordCount+'字'
							 	}]
						 	}],
						 	buttonAlign:'center',
						 	buttons:[
						 		{
						 			text:'发送',
						 			handler:function(){
						 				var win=this.ownerCt;
						 				var value=win.items.get(0).getValue();
						 				var headAndAppendix = '北京佑安医院'+deptName+grounpName+value;  //自动的短信头和落款 加上短信正文
						 			     if(value&&value.length>5){ 
						 			        if(Math.ceil(headAndAppendix.length/64)>1){   //看发送的短信字数是否超过一条短信
								 			       if(confirm('您确定发送吗？该条短信将分'+Math.ceil(headAndAppendix.length/64)+'条发送')){
								 			       win.close();
								 			           send(pid,value,function(data){
							 					  	   });
								 			       }else{
								 			         return;
								 			       }
						 			         }else{
						 			         win.close();
						 			            send(pid,value,function(data){
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
		        }
	    	]
	    });
	    
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
	        tbar: ['<font color="blue">姓名</font>：', '<font color="red"><input type="text" size="7" name="patientName1" readOnly="true"></font>',
	               '<font color="blue">病案号</font>：', '<font color="red"><input type="text" size="7" name="patientNo1" readOnly="true"></font>',
	               '<font color="blue">性别</font>：', '<font color="red"><input type="text" size="3" name="patientSex1" readOnly="true"></font>',
	               '<font color="blue">生日</font>：', '<font color="red"><input type="text" size="10" name="patientBrith1" readOnly="true"></font>'
],

			  listeners : {
			  'render' : function(){
			  oneTbar.render(this.tbar); // add one tbar
			  if(_flag != "omc") {
			     twoTbar.render(this.tbar);
			  }
			  }
			  },
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
	    if(login!=null&&login=='member'){  //如果是会员登陆则隐藏两列  并且工具条不显示
	    	grid.getColumnModel().setHidden(2,true);
	    	grid.getColumnModel().setHidden(3,true);
	    	twoTbar.hide();
	    }
	    
	    function showItems(grid,rowIndex,e) {
	    	var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
	    	// patient = ss[0].get('patient');
	    	_id = ss[0].get('planId');    
	    	_beginDate = ss[0].get('beginDate');
	        var store = Ext.getCmp('editGrid').getStore();
            store.baseParams.planId = _id;
            store.load({
                params: {
            	    circle:ss[0].get('circle'),
            	    circleType:ss[0].get('circleType'),
                    start: 0,
                    limit: 20
                }
            });
            
            $('input[name="itemPlan"]').val((ss[0].get('planDate') == null || ss[0].get('planDate')=="") ? "" :　new Date( ss[0].get('planDate').time).format('Y-m-d'));            
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
			$('input[name="itemCircle"]').val(ss[0].get('circle') + "" + ctype);
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
            planId: _id
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
		    	    	return "<font color='#0968F7'>提前来访</font>";
		    	    }
		    	    else if(value == 2) {
		    	    	return "<font color='#0968F7'>按期来访</font>";
		    	    }
		    	    else if(value == 3) {
		    	    	return "<font color='#0968F7'>超期来访</font>";
		    	    	//return "<font color='#CDCD00'>超期来访</font>";
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
		 tbar: ['-','<font color="blue">随访计划设置日期</font>：', '<font color="red"><input type="text" size="12" name="itemBegin" readOnly=true></font>',
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
	          }	,'->','-',{
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
      		 items:['-', {
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
      	       }},"->",{
       	         text: "取消关联",
       	         id:"linkCheckResult",
       	         handler: function() {
       	     	  var ss=Ext.getCmp('editGrid').getSelectionModel().getSelections();
       					if(ss.length==0){
       						alert('请先选择一条记录。');
       						return;
       					}
       					var checkSelect = new Array();
       					for(var i=0;i<ss.length;i++){
       						if((ss[i].get('reportDate')== null || ss[i].get('reportDate')=='')){
           				     	alert("您选择的项目还没有关联");
           						return;
           				     }else{
           						checkSelect[i] = ss[i].get('id');
           				     }
       					}
       					
       					Ext.Ajax.request({
							url:App.App_Info.BasePath+'/plan.do',
							params:{
								method:'linkReport',
								flag:"re",
							    data:Ext.util.JSON.encode(checkSelect)
							},
							success:function(_response){
								if(Ext.decode(_response.responseText).success){
									alert("已取消关联");
									Ext.getCmp('grid').getStore().reload();
									var _store=Ext.getCmp('editGrid').getStore();
								    _store.load({
								        params: {
								            start: 0,
								            limit: 20
								        }
								    });
								}else{
									alert('取消关联失败');
								}
							}
						});
       					
                    }
                }
       	,'-',{
   	         text: "关联检查结果",
   	         id:"linkCheckResult",
   	         handler: function() {
   	     	  var ss=Ext.getCmp('editGrid').getSelectionModel().getSelections();
   			  if(ss.length==0){
   					alert('请先选择一条记录。');
   					return;
   			  }
   			  var checkSelect = new Array();
   			  if((ss[0].get('state') != 1 && ss[0].get('state') != 2)){
   				    if(!confirm("实际未来访，是否要关联检查结果？")){
   				     	return;
   				     }
   			  }
   			  //刘刚修改2011-12-13
   			  for(var i=0;i<ss.length;i++){
   				  checkSelect[i] = ss[i].get('id');
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
   	     	if(!confirm("实际未来访，是否要关联检查结果？")){
   				return;
   	     	}
   	     }
        if(2==ss[0].get('state') && !confirm("已存在关联结果，是否覆盖以前的关联？")){
        	return;
        }
        //刘刚修改2011-12-13
        var checkSelect = new Array();
   		for(var i=0;i<ss.length;i++){
   			checkSelect[i] = ss[i].get('id');
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
   	            //刘刚修改2011-12-13
		        var checkSelect = new Array();
		   		for(var i=0;i<ss.length;i++){
		   			checkSelect[i] = ss[i].get('id');
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
    
  // editGrid.on("afteredit", afterEdit, editGrid);
  /*
	 * editGrid.on("beforeedit", beforeEdit, editGrid); var origComment = null;
	 * var origCircle = null; var origState = null;
	 * 
	 * function afterEdit(obj) { var record = obj.record; var id =
	 * record.get("id"); var circle = record.get("circle"); var comment =
	 * record.get("comment"); var state = record.get("state"); //alert(state);
	 * if(circle == origCircle && comment == origComment && state == origState) {
	 * return; } else { $.post( App.App_Info.BasePath+'/plan.do', {
	 * method:'editPlanItem', planItemId:id, start:0, limit:20, circle:circle,
	 * comment:comment, state:state }, function(data){ if(data.success){
	 * 
	 * var store = Ext.getCmp('grid').getStore(); //store.baseParams.search =
	 * ''; store.load({ params: { start: 0, limit: 20 } });
	 * 
	 * var _store=Ext.getCmp('editGrid').getStore(); _store.load({ params: {
	 * start: 0, limit: 20 } });
	 * 
	 * //_store.load(); }else { if(data.msg) { alert(data.msg); }
	 * 
	 * var _store=Ext.getCmp('editGrid').getStore(); _store.load({ params: {
	 * start: 0, limit: 20 } }); } }, 'json' ); } }
	 * 
	 * function beforeEdit(obj) { var record = obj.record; var id =
	 * record.get("circle"); origCircle = record.get("circle"); origComment =
	 * record.get("comment"); origState = record.get("state"); }
	 */

   
    new Ext.Viewport({
        layout: 'border',
        items: [{
			region:'west',
			layout:'fit',
            width: 700,
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
