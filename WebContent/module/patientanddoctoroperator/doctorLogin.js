

var pageCount = null;
var xmlHttp = null;
var isPad = this.parent.location.toString().indexOf('pad')!=-1;

var deptName = null;
var currentDeptName = null;
var grounpName = null;

 //根据医生Id 获得当前责任科室和小组名称  by cheng jiangyu 2011-09-30
function getDoctorDeptmentAndGrounp(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentHISAction.do',
		params:{
			method:'findDoctorDeptAndGroupByDoctorId',
			doctorId:top.USER.id
		},
		sync:true,
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			currentDeptName = list[0].deptName;
			grounpName = list[0].grounpName;
		}
	});	
}

function selectRow(){			
	if(window.XMLHttpRequest){
		xmlHttp = new XMLHttpRequest();
	}else if(window.ActiveXObject){
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	try{
		
		xmlHttp.onreadystatechange = getRow;
		xmlHttp.open("GET", "../../PUBLIC/config/selectRow.xml", false);
		xmlHttp.send();
	}catch(exception){
		
	}
}

function getRow(){
	if(xmlHttp.readyState == 4){
		if(xmlHttp.status == 200 || xmlHttp.status == 0){
			var xmlDOM = xmlHttp.responseXML;
			
			var rootElement = xmlDOM.documentElement;
			
			try{
				var pct = rootElement.getElementsByTagName("pageCount");
				
				if(isPad){
					pageCount = parseInt(pct[0].firstChild.data);
					
					
				}else{
					pageCount = parseInt(pct[1].firstChild.data);
				}
			}catch(exception){
				
			}
		}
	}
}

Ext.onReady(function(){
    getDoctorDeptmentAndGrounp();  // 根据医生Id 获得当前责任科室和小组名称  by cheng jiangyu 2011-09-30
	selectRow();
	var outComList = top.USER['单位'].split("、");//医生所在的科室
	for(var k=0;k<outComList.length;k++){
		if(k != outComList.length -1){
			deptName += outComList[k]+"','";
		}else{
			deptName += outComList[k];
		}
	}
	createSearch();
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'center',
				layout:'fit',
				border:false,
				items:createGrid()
			}
		]
	});
	//liugang 2011-08-06 start 
	//弹出框
	$('#dialog1').dialog({
		title:'咨询科室列表',
		autoOpen:false,
		closeOnEscape:true,
		width:525,
		resizable:true,
		zIndex:10,
		position:['left','top'],
		buttons:{
			'关闭':function(){$(this).dialog("close");}
		}
	});
	
	//弹出框
	$('#dialog2').dialog({
		title:'咨询科室附言',
		autoOpen:false,
		closeOnEscape:true,
		width:525,
		resizable:true,
		zIndex:10,
		position:['left','top'],
		buttons:{
			'关闭':function(){$(this).dialog("close");}
		}
	});
	
		
	//弹出框
	$('#dialog3').dialog({
		title:'转科转组历史记录',
		autoOpen:false,
		closeOnEscape:true,
		width:600,
		resizable:true,
		zIndex:10,
		position:['left','top'],
		buttons:{
			'关闭':function(){$(this).dialog("close");}
		}
	});
	//liugang 2011-08-06 end 
});

function createGrid(){
    // var _sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true}); 将Checkbox改为Row就没有 复选框  注意：Checkbox的大小写 
	var _sm=new Ext.grid.RowSelectionModel({sigleSelect:true});
	var _cm_normal=[
		new Ext.grid.RowNumberer(),
		// _sm,
		//liugang 2011-08-06 start
		{header:'会员姓名',renderer:function(value, meta, record, rowIndex, colIndex, store){
				if(record.data.reply == "待回复"){
					return "<B>"+record.data.signature+"</B>";
				}else{
					return record.data.signature;
				}
			},width:60,sortable:true},
		{header:'第几次咨询',renderer:function(value, meta, record, rowIndex, colIndex, store){
				if(record.data.reply == "待回复"){
					return "<B>"+record.data.consultingCount+"</B>";
				}else{
					return record.data.consultingCount;
				}
			},width:65,sortable:true},
		{header:'当前责任科室',renderer:function(value, meta, record, rowIndex, colIndex, store){
				if(record.data.reply == "待回复"){
					return "<B>"+record.data.deptName+"</B>";
				}else{
					return record.data.deptName;
				}
			},
			
		width:200,sortable:true},
 		/*
 		{header:'咨询科室',dataIndex:'zixundeptName',renderer:function(value, meta, record, rowIndex, colIndex, store){
 			if(record.data.isNotSend != null){
 				return '<font color="red">'+record.data.zixundeptName+'</font>';
 			}
 		}},
 		*/
		{header:'咨询时间',renderer:function(value, meta, record, rowIndex, colIndex, store){
				if(record.data.reply == "待回复"){
					return "<B>"+record.data.consultingDate+"</B>";
				}else{
					return record.data.consultingDate;
				}
			},width:90,sortable:true},
		//liugang 2011-08-06 start
		{header:'是否转科',width:55,renderer:function(value, meta, record, rowIndex, colIndex, store){
			if(record.data.isNotUpdate == 1){
				if(record.data.reply == "待回复"){
					return "<a href='###' onclick='showSendMessage("+record.data.patientId+")'><B>查看</B></a>";
				}else{
					return "<a href='###' onclick='showSendMessage("+record.data.patientId+")'><B>查看</B></a>";
				}
			}
		}},
		//liugang 2011-08-06 end
		{header:'状态',dataIndex:'reply',renderer:function(value, meta, record, rowIndex, colIndex, store){
			if(record.data.reply == "待回复"){
			    return "<a href='###' onclick='linkToOneQuestion("+record.data.id+","+record.data.patientId+",\""+record.data.hisPId+"\",\""+record.data.currentDeptCode+"\","+record.data.isNotUpdate+",\""+record.data.signature+"\",\""+record.data.mobilePhone+"\")'><B>"+record.data.reply+"</B></a>";
				// return "<a href='###' onclick='linkToOneQuestion("+record.data.id+","+record.data.patientId+",\""+record.data.hisPId+"\",\""+record.data.currentDeptCode+"\","+record.data.isNotUpdate+")'><B>"+record.data.reply+"</B></a>";
			}else{
			    return "<a href='###' onclick='linkToOneQuestion("+record.data.id+","+record.data.patientId+",\""+record.data.hisPId+"\",\""+record.data.currentDeptCode+"\","+record.data.isNotUpdate+",\""+record.data.signature+"\",\""+record.data.mobilePhone+"\")'><B>"+record.data.reply+"</B></a>";
				//return "<a href='###' onclick='linkToOneQuestion("+record.data.id+","+record.data.patientId+",\""+record.data.hisPId+"\",\""+record.data.currentDeptCode+"\","+record.data.isNotUpdate+")'>"+record.data.reply+"</a>";
			}
		},width:50,sortable:true},
		{header:'咨询其他科室',renderer:function(value, meta, record, rowIndex, colIndex, store){
			if(record.data.isNotSend != null){
				if(record.data.reply == "待回复"){
					return "<a href='###' onclick='showAllZiXunDept("+record.data.id+")'><B>查看</B></a>";
				}else{
					return "<a href='###' onclick='showAllZiXunDept("+record.data.id+")'>查看</a>";
				}
			}
		}},
		{header:'咨询附言',renderer:function(value, meta, record, rowIndex, colIndex, store){
			if(record.data.isNotSend != null){
				if(record.data.reply == "待回复"){
					return "<a href='###' onclick='lookMessage("+record.data.id+")'><B>查看附言</B></a>";
				}else{
					return "<a href='###' onclick='lookMessage("+record.data.id+")'>查看附言</a>";
				}
			}
		}}
		//liugang 2011-08-06 start
	];
	var _ra_normal=[
		{name:'id'},
		{name:'patientId'},
		{name:'consultingCount'},
		{name:'deptName'},
		{name:'zixundeptName'},
		{name:'consultingDate'},
		{name:'mobilePhone'},
		{name:'signature'},
		{name:'reply'},
		{name:'hisPId'},
		{name:'isNotSend'},
		{name:'currentDeptCode'},
		{name:'isNotUpdate'}
	];
	var _cfg=window.parent.GridCfg;
	var _url=App.App_Info.BasePath+'/PatientConsultingAction.do';
	var _baseParams={
		method:'findPatientConsultingByDoctor',
		doctorId:top.USER.id,
		flagDai:10,
		flagYi:10,
		flagEnd:10
	};
	if(_cfg){
		_url=_cfg.url?_cfg.url:_url;
		_baseParams=_cfg.baseParams?_cfg.baseParams:_baseParams;
		if(_cfg.cm){
			_cm_normal=_cm_normal.concat(_cfg.cm);
		}
	}
	var _cm=new Ext.grid.ColumnModel(_cm_normal);
	var _ds=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:_url}),
		reader:new Ext.data.JsonReader({root:'root',totalProperty:'total'},_ra_normal)
	});
	_ds.baseParams=_baseParams;
	_ds.load({params:{start:0,limit:25}});
	var _grid=new Ext.grid.GridPanel({
		id:'grid',
		border:false,
		cm:_cm,
		sm:_sm,
		ds:_ds,
		autoScroll: true,
		viewConfig:{
			forceFit:true
		},
		bbar:new Ext.PagingToolbar({
            pageSize: 20,
            store: _ds,
            displayInfo: true,
            displayMsg: '显示第<font color="red"> {0} </font>条' +
            '到<font color="red"> {1} </font>条记录，' +
            '一共<font color="red"> {2} </font>条',
            emptyMsg: "<font color='red'>没有记录</font>"
        }),
        tbar:[
              '-',{
            	  text:'刷新',handler:function(){
            	  	Ext.getCmp('grid').getStore().reload();
              	  }
              }
              , '-', new SearchButton({
                        handler: function(){
                            if (!this.sw) {
                                this.initSw();
                            }
                            this.toggleSw();
                        }
                    })
              ,
               '-',{
            	  text:'待回复',handler:function(){
            	  	var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findPatientConsultingByDoctor',doctorId:top.USER.id,
      					flagDai:0,
						flagYi:10,
						flagEnd:10};
      				_store.load({params:{start:0,limit:25}});
              	  }
              }
               ,
               '-',{
            	  text:'已回复',handler:function(){
            	  	var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findPatientConsultingByDoctor',doctorId:top.USER.id,
      					flagDai:10,
						flagYi:1,
						flagEnd:10};
      				_store.load({params:{start:0,limit:25}});
              	  }
              }
               ,
               '-',{
            	  text:'咨询结束',handler:function(){
            	  	var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findPatientConsultingByDoctor',doctorId:top.USER.id,
      					flagDai:10,
						flagYi:10,
						flagEnd:2};
      				_store.load({params:{start:0,limit:25}});
              	  }
              }
              ,
               '-',{
            	  text:'返回列表',handler:function(){
            	  	var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findPatientConsultingByDoctor',doctorId:top.USER.id,
      					flagDai:10,
						flagYi:10,
						flagEnd:10
					};
      				_store.load({params:{start:0,limit:25}});
              	  }
              },'-', {
                  text: '发送短信',
                  handler: function(){
                       var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
      				 if (ss.length == 0) {
      				     alert('请至少选择一条记录。');
      				     return;
      				 }
      				 var pid = "";    //不支持多选发送短信
      				 var patients = ""; //给哪些病人发短信
      				    pid = ss[0].get('patientId');
      				    patients = ss[0].get('signature');
      				if(ss[0].get('mobilePhone')==''||ss[0].get('mobilePhone')==null||(ss[0].get('mobilePhone')!=''&&!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(ss[0].get('mobilePhone'))))){  //如果没有登记手机号码的病人不为空
					 	 alert("会员"+patients+"手机号码为空或格式不正确");
					 	 return;
				 	}
      				 var send=function(id,ctx,callback){
      				 	Ext.Ajax.request({
      					 	url:App.App_Info.BasePath + '/biomedical.do',
      					 	params:{
      					 		method:'sendMsgByPatientId',
      					 		id:id,
      					 		appendix:'北京佑安医院'+currentDeptName+''+grounpName, //加在短信内容后面的落款
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
      						 		//text:'短信总字数上限:100字'
      					 	}]
      				 	}],
      				 	buttonAlign:'center',
      				 	buttons:[
      				 		{
      				 			text:'发送',
      				 			handler:function(){
      				 				    var win=this.ownerCt;
									 				var value=win.items.get(0).getValue();
									 				var headAndAppendix = '北京佑安医院'+currentDeptName+grounpName+value;  //自动的短信头和落款 加上短信正文
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
	return _grid;
}

function getSelectionRows(){
	return Ext.getCmp('grid').getSelectionModel().getSelections();
}

function linkToOneQuestion(pcid,patientId,hisPId,currentDeptCode,isNotUpdate,patientName,mobilePhone){
	if(isPad){
		window.location.href=App.App_Info.BasePath+'/pad/dConsult/dConsult.html?pcId='+pcid+'&patientId='+patientId+'&hisPId='+hisPId+'&currentDeptCode='+currentDeptCode+"&isnotUpdate="+isNotUpdate;
	}else{
		window.location.href=App.App_Info.BasePath+'/module/patientanddoctoroperator/doctorConsultingOne.html?pcId='+pcid+'&patientId='+patientId+'&hisPId='+hisPId+'&currentDeptCode='+currentDeptCode+"&isnotUpdate="+isNotUpdate+"&patientName="+patientName+'&currentDeptName='+currentDeptName+'&grounpName='+grounpName+'&mobilePhone='+mobilePhone;
	}
}

function createSearch(){
	 SearchButton= Ext.extend(Ext.Button, {
                    text: '高级查询',
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
                                    anchor: '95%'
                                },
                                items: [
                                {
                                    fieldLabel: '姓名',
                                    name: 'name'
                                },
                                {
                                    fieldLabel: '咨询时间',
                                    name: 'writeRecordDate'
                                }, {
                                    xtype: 'panel',
                                    border: false,
                                    html: '<p style="font-size:12px;text-align:center">例：输入2011-07-12或(2010-07-01 2010-07-30)不包括括号。</p>'
                                }
                                ]
                            },
                            buttons: [{
                                text: '高级查询',
                                handler: function(){
                                    var values = o.sw.items.get(0).getForm().getValues();
                                    var store = Ext.getCmp('grid').getStore();
                                    store.baseParams.search = Ext.encode(values);
                                    store.baseParams.doctorId = top.USER.id;  
                                    store.baseParams.flagDai = 10;
                                    store.baseParams.flagYi  = 10;   
                                    store.load({
                                        params: {
                                            start: 0,
                                            limit: 20
                                        }
                                    });
                                    o.sw.items.get(0).getForm().reset(); //用于清空框中的值
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
}

//liugang 2011-08-06 start
function lookMessage(_thisId){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PatientConsultingAction.do',
		params:{
			method:'findAllPatientCousultingMessage',
			id:_thisId
		},
		scope:this,
		sync:true,
		success:function(_response){
			var lists = Ext.util.JSON.decode(_response.responseText);
			if(lists != "" && lists.length > 0)
			{
				var createDia ='<table width="495" border=1 cellSpacing=0 borderColorDark=#ffffff cellPadding=2>';
				for(var i=0,size = lists.length;i<size;i++)
				{
					createDia+='<tr><td class="t1"><center><B>'+lists[i].mesDdate+'</B></center></td><td class="t1"><center><B>'+lists[i].doctorName+'</B><center></td><td class="t1"><center><B>'+lists[i].deptNameGrounpName+'</B><center></td>'
						+'</tr><tr><td colspan="3"><font size="3">'+lists[i].message+'</font></td></tr>';
				}
				createDia+='</table>';
				document.getElementById("dialog2").innerHTML = createDia;
			}else{//liugang 2011-08-06  start
				document.getElementById("dialog2").innerHTML ="暂无附言";
			}//liugang 2011-08-06  end
		}
	});
	FormUtil.showJqueryDialog('dialog2');
}

function showAllZiXunDept(_thisId){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PatientConsultingAction.do',
		params:{
			method:'findAllPatientConsulting',
			id:_thisId
		},
		scope:this,
		sync:true,
		success:function(_response){
			var lists = Ext.util.JSON.decode(_response.responseText);
			if(lists != "" && lists.length > 0)
			{
				var createDia ='<table width="495" border=1 cellSpacing=0 borderColorDark=#ffffff cellPadding=2>'
					+'<tr><td class="t1"><center>序号</center></td><td class="t1"><center>咨询日期</center></td><td class="t1"><center>咨询科室</center></td>'
					+'<td class="t1"><center>状态</center></td></tr>';	
				for(var i=0,size = lists.length;i<size;i++)
				{
					createDia+='<tr><td class="t1"><center>'+lists[i].id+'</center></td><td class="t1"><center>'+lists[i].consultingDate+'<center></td><td class="t1"><center>'+lists[i].deptNameGrounp+'<center></td>'
						+'<td class="t1"><center>'+lists[i].state+'<center></td></tr>';
				}
				createDia+='</table>';
				document.getElementById("dialog1").innerHTML = createDia;
			}
		}
	});
	FormUtil.showJqueryDialog('dialog1');
}

 
function showSendMessage(_thisId){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
		params:{
			method:'findMemberDeptOrGrounpRecordByPatientId',
			patientId:_thisId
		},
		scope:this,
		sync:true,
		success:function(_response){
			var lists = Ext.util.JSON.decode(_response.responseText);
			if(lists != "" && lists.length > 0)
			{
				var createDia ='<table width="570" border=1 cellSpacing=0 borderColorDark=#ffffff cellPadding=2>'
					+'<tr><td class="t1"><center>序号</center></td><td class="t1"><center>转科转组时间</center></td><td class="t1"><center>(原)责任科室</center></td><td class="t1"><center>(现)责任科室</center></td>'
					+'</tr>';	
				for(var i=0,size = lists.length;i<size;i++)
				{
					createDia+='<tr><td class="t1"><center>'+(i+1)+'</center></td><td class="t1"><center>'+lists[i].appDate+'<center></td><td class="t1"><center>'+lists[i].oldDeptCodeG+'<center></td>'
						+'<td class="t1"><center>'+lists[i].DeptCodeG+'<center></td></tr>';
				}
				createDia+='</table>';
				document.getElementById("dialog3").innerHTML = createDia;
			}else{
				document.getElementById("dialog3").innerHTML = "暂无转科转组记录";
			}
		}
	});
	FormUtil.showJqueryDialog('dialog3');
}
//liugang 2011-08-06 end 