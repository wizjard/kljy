<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo"%>
<%
MemberInfo mi = (MemberInfo)session.getAttribute("MemberInfo");
Long id = 0L;
//String patID = "";
if(mi != null){
	id = mi.getPatient().getId();
	//patID= mi.getPatient().getPatientId();
}
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>化验检查结果查看及录入</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_ext.js"></script>

<script type="text/javascript">
var patientId = null;
var title = false;
var tijiaoList;
var guidangList;
var guanliyuan;
var deptName=null;
var grounpName= null;
Ext.onReady(function(){
	args = getQueryStringArgs();
	patientId = args["patientId"] ? args["patientId"] : <%=id%>;
	guanliyuan = args["guanliyuan"];
	title = '化验检查结果';
	if(args["patientId"]){				
		if(args["name"]!='null'){
			title +=  '（患者姓名：'+args["name"]+'，患者编号：'+args["no"]+'）';
		}				
	}
	if(top.USER != undefined){
		Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentHISAction.do',
		params:{
			method:'findDoctorDeptAndGroupByDoctorId',
			doctorId:top.USER.id
		},
		sync:true,
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			if(list[0] != undefined){
				deptName = list[0].deptName;
				grounpName = list[0].grounpName;
			}
		}
	});	
	}
	layout();
});
function layout(){
	var ds = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: App.App_Info.BasePath+'/CheckReport/combinationProject.do'
	        }),
	        reader: new Ext.data.JsonReader({
	            root: 'root',
	            totalProperty: 'total'
	        }, [{
	            name: 'text'
	        }, {
	            name: 'receiveDate'
	        }, {
	            name: 'sectionNo'
	        },  {
	            name: 'testTypeNo'
	        }, {
	            name: 'sampleNo'
	        }, {
	            name: 'parItemNo'
	        }, {
	            name: 'hospital'
	        }, {
	            name: 'isFromOutHispital'   
	        }, {
	            name: 'reportFormforRemoteId'
	        },{
	        	name: 'inputName'
	        },{
	        	name:'sheHeDate'
	        },{
	        	name:'guiDangDate'
	        },{
	        	name:'luRuDate'
	        },{
	        	name:'isPatientOrDoctorWriteZanCun'
	        },{
	        	name:'cheXiaoTrue'
	        },{
	        	name:'patientName'
	        }	        
	        ])
	    }); 
	if(patientId != null && top.USER == undefined&& guanliyuan !=1){
	    tijiaoList = [["","--请选择--"],["是","是"],["否","否"]];	
		guidangList = [["","--请选择--"],["是","是"]];
	    ds.baseParams = {
	        method: 'getMyGrounpCheckReportsForByMember',
	        search: '',
	        patientId:patientId
    	}; 
	}else if(patientId != null && top.USER != undefined&& guanliyuan !=1){
		 tijiaoList = [["","--请选择--"],["是","是"]];	
		 guidangList = [["","--请选择--"],["是","是"],["否","否"]];
		 ds.baseParams = {
	        method: 'getMyGrounpCheckReportsForByMemberDoctor',
	        search: '',
	        patientId:patientId
    	}; 
	}else if(patientId != null && top.USER != undefined&& guanliyuan ==1){
		 tijiaoList = [["","--请选择--"],["是","是"],["否","否"]];	
		 guidangList = [["","--请选择--"],["是","是"],["否","否"]];
		 ds.baseParams = {
	        method: 'getMyGrounpCheckReportsForByMemberGuanLiYuan',
	        search: '',
	        patientId:patientId
    	}; 
	}
	else{
		alert("登录超时,为确保您的信息安全请重新登录！");
		window.top.location.href="../../module/biomedical/member/login.jsp"
		return;
	}
		
    ds.load({
        params: {
            start: 0,
            limit: 20
        }
    });
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
    
    {
        header: '录入时间',
        dataIndex: 'luRuDate',
        sortable : true,
        width: 30,
        renderer: function(value){
            return value;
        }
    }, {
        header: '录入项目',
        dataIndex: 'text',
        sortable : true,
        width: 35,
        renderer: function(value){
            return value;
        }
    }, {
        header: '录入者',
        dataIndex: 'inputName',
        sortable : true,
        width: 25,
        renderer: function(value){
            return value;
        }
    }, {
        header: '检查单位',
        width: 45,
        dataIndex: 'hospital',
        renderer: function(value){
            return value;
        } 
    } , {
        header: '患者提交日期',
        width: 30,
        dataIndex: 'sheHeDate',
        renderer: function(value){
            return value;
        } 
    } , {
        header: '医生归档日期',
        width: 30,
        dataIndex: 'guiDangDate',
        renderer: function(value){
            return value;
        } 
    } , {
        header: '检查结果',
        width: 25,
        sortable : true,
        dataIndex: 'receiveDate',
        renderer: function(value, meta, record, rowIndex, colIndex, store){
			return "<a href='###' onclick='openCheck(\"" + escape(record.data.text) + "\",\"" + record.data.receiveDate + "\",\"" + record.data.sectionNo + "\",\"" + record.data.testTypeNo 
						+ "\",\"" + record.data.sampleNo + "\",\"" + record.data.parItemNo +"\",\"" + record.data.isFromOutHispital 
						+ "\",\"" + record.data.reportFormforRemoteId + "\",\"" + record.data.isPatientOrDoctorWriteZanCun + "\",\"" + record.data.cheXiaoTrue + "\",\"" + record.data.guiDangDate + "\")'>查看</a>";
		}
    }
    ]); 
     
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
                                labelWidth: 80,
                                defaults: {
                                    xtype: 'textfield',
                                    anchor: '90%'
                                },
                                items: [
                                {
                                    fieldLabel: '录入者',
                                    name: 'doctor',
									id:'doctor'
                                },
                                {
                                    fieldLabel: '是否提交审核',
				                    xtype: 'combo',
				                    mode: 'local',
				                    name:'tijiao',
				                    id:'tijiao',
				                    store: new Ext.data.SimpleStore({
										 fields:['clinicValue','showClincType'],
										 data: tijiaoList
									}),
				                    displayField: 'showClincType',
									valueField: 'clinicValue',
				                    triggerAction: 'all',
				                    readOnly: true,
				                    listeners:{
										select:function(){
									      	tijiaoValue =this.getValue();
										}
									} 
                                }, {
                                    fieldLabel: '是否归档',
				                    xtype: 'combo',
				                    mode: 'local',
				                    name:'guidang',
				                    id:'guidang',
				                    store: new Ext.data.SimpleStore({
										 fields:['clinicValue','showClincType'],
										 data: guidangList
									}),
				                    displayField: 'showClincType',
									valueField: 'clinicValue',
				                    triggerAction: 'all',
				                    readOnly: true,
				                    listeners:{
										select:function(){
									      	guidangValue =this.getValue();
										}
									} 
                                }
                                , {
                                    fieldLabel: '录入日期',
                                    name: 'luRuDate',
									id:'luRuDate'
                                }, {
                                    xtype: 'panel',
                                    border: false,
                                    html: '<p style="font-size:10px;color:red;text-align:center">例：(2010-01-02)或(2010-01-01 2010-02-01)不包括括号。</p>'
                                }
                                , {
                                    fieldLabel: '归档日期',
                                    name: 'guidangDate',
									id:'guidangDate'
                                }, {
                                    xtype: 'panel',
                                    border: false,
                                    html: '<p style="font-size:10px;color:red;text-align:center">例：(2010-01-02)或(2010-01-01 2010-02-01)不包括括号。</p>'
                                }
                                ]
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
                                    
                                    o.sw.items.get(0).getForm().reset(); //用于清空框中的值 by cheng jiangyu 2011-9-30
                                    o.sw.hide();
                                    
                                }
                            }, {
                                text: '取消',
                                handler: function(){
                                    o.sw.items.get(0).getForm().reset(); //用于清空框中的值 by cheng jiangyu 2011-9-30
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
                 
    var sm = new Ext.grid.RowSelectionModel({
        singleSelect: true
    });  
    
    var grid;
    if(top.USER != undefined && deptName != null){
	    	grid = new Ext.grid.GridPanel({
	        id: 'grid', 
	        title:title,       
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
	                		id: patientId,
	                        start: 0,
	                        limit: 20
	                    }
	                });
	            }		
			},
			'-', {
	            text: '录入化验检查结果',
	            handler: function(){
	               if(top.USER != undefined){
	               		window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+patientId+'&doctorName='+top.USER.name+'&doctorOrPatient=1', '', 'width=240px;');
	               }else{
	               	    if(window.confirm("您录入的化验检查结果将直接影响到医生对您病情的判断，为保证医生能够做出正确的判断，请务必认真核对小数点的位置、单位及参考值范围等，确保录入内容真实可靠。")){
	               	    	window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+patientId+'&patientName='+top.Member.name, '', 'width=240px;');
	               	    }
	               }
	               
	            }		
			},
			'-', new SearchButton({
	                        handler: function(){
	                            if (!this.sw) {
	                                this.initSw();
	                            }
	                            this.toggleSw();
	                        }
	                    })
	        ,'-', {
	            text: '发送短信',
	            handler: function(){
	            	var patientObject;
	                 Ext.Ajax.request({
						 	url:App.App_Info.BasePath + '/biomedical.do',
						 	params:{
						 		method:'findPatient',
						 		patientId:patientId
						 	},
						 	scope:this,
							sync:true,
						 	success:function(_response){
								var res=Ext.decode(_response.responseText);
								if(res.success){
									patientObject = Ext.util.JSON.decode(res.data);
								}
						 	}
						 });
					 var pid = "";    //支持多选发送短信
					 var patients = ""; //给哪些病人发短信
					    pid = pid + patientObject.memberNo;  
					    patients = patientObject.patientName;
					     if(patientObject.mobilePhone==''||patientObject.mobilePhone== null||(patientObject.mobilePhone!=''&&!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(patientObject.mobilePhone)))){  //如果没有登记手机号码的病人不为空
					 			        alert("会员"+patients+"手机号码为空或格式不正确");
					 			        return;
					 	} 
					    var send=function(id,ctx,callback){
					 	Ext.Ajax.request({
						 	url:App.App_Info.BasePath + '/biomedical.do',
						 	params:{
						 		method:'sendMsg',
						 		id:id,
						 		appendix:'北京佑安医院'+deptName+''+grounpName, 
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
					 	title:'给【'+patients+'】发送短信',
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
							 		text:'短信条数:'+ss.length+'条'
							 	},{
							 		columnWidth:0.4,
							 		xtype:'label',
							 		width:20
							 		// text:'短信总字数上限:'+wordCount+'字'
						 	}]
					 	}],
					 	buttonAlign:'center',
					 	buttons:[
					 		{
					 			text:'发送',
					 			handler:function(){
					 				var win=this.ownerCt;
					 				var value=win.items.get(0).getValue();
					 				var headAndAppendix = '北京佑安医院'+deptName+''+grounpName+''+value;  //自动的短信头和落款 加上短信正文
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
	        } ,'-', {
	            text: '删除',
	            handler: function(){
	                var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
            	  	if(_ss.length>1||_ss.length==0){
            	  		alert('请选择一条记录。');
            	  		return;
            	  	}
            	  	if(_ss[0].data.isPatientOrDoctorWriteZanCun == 11 || _ss[0].data.isPatientOrDoctorWriteZanCun==21){
            	  		alert("此化验单已经提交，不能删除！");
            	  		return;
            	  	}
            	  	if(window.confirm("您确定删除此化验单吗？")){
	            	  		Ext.Ajax.request({
								url:App.App_Info.BasePath+'/CheckReport/combinationProject.do',
								params:{
									method:'deleteReportFormAndReportItems',
									reportFormforRemoteId:_ss[0].data.reportFormforRemoteId,
									receiveDate:_ss[0].data.receiveDate,
									sectionno:_ss[0].data.sectionNo,
									testtypeno:_ss[0].data.testTypeNo,
									sampleNo:_ss[0].data.sampleNo
								},
								sync:true,
								success:function(response){
									var res = Ext.util.JSON.decode(response.responseText);
									if(res.success){
										alert("删除成功");
										Ext.getCmp('grid').getStore().reload();
									}else{
										alert("删除失败");
									}
								}
							});
            	  		}
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
	            emptyMsg: "<font color='red'>没有记录</font>"
	        })
	    });
    }else{
	    	grid = new Ext.grid.GridPanel({
	        id: 'grid', 
	        title:title,       
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
	                		id: patientId,
	                        start: 0,
	                        limit: 20
	                    }
	                });
	            }		
			},
			'-', {
	            text: '录入化验检查结果',
	            handler: function(){
	               if(top.USER != undefined){
	               		window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+patientId+'&doctorName='+top.USER.name+'&doctorOrPatient=1', '', 'width=240px;');
	               }else{
	               		if(window.confirm("您录入的化验检查结果将直接影响到医生对您病情的判断，为保证医生能够做出正确的判断，请务必认真核对小数点的位置、单位及参考值范围等，确保录入内容真实可靠。")){
	               			window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+patientId+'&patientName='+top.Member.name, '', 'width=240px;');
	               		}
	               }
	               
	            }		
			},
			'-', new SearchButton({
	                        handler: function(){
	                            if (!this.sw) {
	                                this.initSw();
	                            }
	                            this.toggleSw();
	                        }
	              })
	        ,'-', {
	            text: '删除',
	            handler: function(){
	                var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
            	  	if(_ss.length>1||_ss.length==0){
            	  		alert('请选择一条记录。');
            	  		return;
            	  	}
            	  	if(_ss[0].data.isPatientOrDoctorWriteZanCun == 11 || _ss[0].data.isPatientOrDoctorWriteZanCun==21){
            	  		alert("此化验单已经提交，不能删除！");
            	  		return;
            	  	}
            	  	if(window.confirm("您确定删除此化验单吗？")){
	            	  		Ext.Ajax.request({
								url:App.App_Info.BasePath+'/CheckReport/combinationProject.do',
								params:{
									method:'deleteReportFormAndReportItems',
									reportFormforRemoteId:_ss[0].data.reportFormforRemoteId,
									receiveDate:_ss[0].data.receiveDate,
									sectionno:_ss[0].data.sectionNo,
									testtypeno:_ss[0].data.testTypeNo,
									sampleNo:_ss[0].data.sampleNo
								},
								sync:true,
								success:function(response){
									var res = Ext.util.JSON.decode(response.responseText);
									if(res.success){
										alert("删除成功");
										Ext.getCmp('grid').getStore().reload();
									}else{
										alert("删除失败");
									}
								}
							});
            	  		}
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
    }
    
    new Ext.Viewport({
        layout: 'fit',
        items: grid
    });
}  
function openCheck(text,receiveDate,sectionNo,testTypeNo,sampleNo,parItemNo,isFromOutHispital,reportFormforRemoteId,isPatientOrDoctorWriteZanCun,cheXiaoTrue,guiDangDate){		
    	var params = '?attempDate='+new Date().getTime()+'&patientId=' + patientId + '&is_check_add=check&is_pad=pad&itemName=' + text 
							+ '&receiveDate=' + receiveDate + '&sectionNo=' + sectionNo 
							+ '&testTypeNo=' + testTypeNo + '&sampleNo=' + sampleNo 
							+ '&parItemNo=' + parItemNo+'&isFromOutHispital='+isFromOutHispital
							+ '&reportFormforRemoteId=' + reportFormforRemoteId+'&isAllowUpdate='+isPatientOrDoctorWriteZanCun+'&cheXiaoTrue='+cheXiaoTrue+'&guiDangDate='+guiDangDate;
		if(isFromOutHispital == 1){//有保存按钮
			window.location ='../InHospitalCase/Liver/CheckReport/modifyCheckReport.html' + params;	
		}else{
			window.location ='../InHospitalCase/Liver/CheckReport/checkReport.html' + params
		}	
				
}  	

function getQueryStringArgs(){
	var qs = (location.search.length)>0 ? location.search.substring(1) : "";
	var args = {};
	var items = qs.split("&");
	var item = null, name = null, value = null;
	for(var i=0; i<items.length; i=i+1){
		item = items[i].split("=");
		name = decodeURIComponent(item[0]);
		value = decodeURIComponent(item[1]);
		args[name] = value;
	}
	return args;
}

</script>
</head>
<body>
</body>
</html>