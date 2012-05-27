
var deptName = null;  
var currentDeptName = null; //责任科室名称
var grounpName = null; //所在小组名称

var pageCount = null;
var xmlHttp = null;
var isPad = this.parent.location.toString().indexOf('pad')!=-1;

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

var SearchButton=null;
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
    // var _sm=new Ext.grid.CheckBoxSelectionModel({sigleSelect:true}); 将CheckBox改为Row就没有 复选框
	var _sm=new Ext.grid.RowSelectionModel({sigleSelect:true});  
	var _cm_normal=[
		new Ext.grid.RowNumberer(),
		//_sm,   注释掉这个表示不显示复选框那一列
		{header:'最新健康记录',dataIndex:'writeRecordDate',width:130,sortable: true},
		{header:'健康记录总数',dataIndex:'healthRecordCount',width:120,sortable: true,renderer:function(value, meta, record, rowIndex, colIndex, store){
			return '<font color="red">'+value+'</font>';
		}},
		{header:'查房状态',dataIndex:'typeFlag',width:85,sortable: true,renderer:function(value, meta, record, rowIndex, colIndex, store){
		//liugang 2011-08-06 start 
				if(value=="未查房"){
					return '<font color="red">'+record.data.typeFlag+'</font>';
				}else{
					return record.data.typeFlag;
				}
		//liugang 2011-08-06 end 
		}},
		{header:'是否转科',width:85,sortable: true,renderer:function(value, meta, record, rowIndex, colIndex, store){
		//liugang 2011-08-06 start 
			if(record.data.isNotUpdate == 1){
				return '<a href="###" onclick="showSendMessage('+record.data.id+')">查看</a>';
			}
		//liugang 2011-08-06 end 
		}},
		{header:'姓名',dataIndex:'patientName',width:80,sortable: true},
		{header:'病案号',dataIndex:'patientNo',width:80,sortable:true},
		{header:'性别',dataIndex:'gender',width:50},
		{header:'出生日期',dataIndex:'birthday',width:90,sortable:true},
//		{header:'籍贯',dataIndex:'province0'},
//		{header:'职业',dataIndex:'occupation0'},
		{header:'婚姻状态',dataIndex:'marrageStatus0'},
		{header:'家庭电话',dataIndex:'homeTel'},
		{header:'联系人姓名',dataIndex:'contacterName'},
		{header:'联系人电话',dataIndex:'contacterTel'}
	];
	var _ra_normal=[
					{name:'id'},
					{name:'healthRecordCount'},
					{name:'writeRecordDate'},
					{name:'typeFlag'},
					{name:'patientNo'},
					{name:'patientName'},
					{name:'gender'},
					{name:'birthday'},
					{name:'province0'},
					{name:'occupation0'},
					{name:'homeTel'},
					{name:'marrageStatus0'},
					{name:'contacterName'},
					{name:'contacterTel'}, 
					{name:'mobilePhone'}, 
					{name:'hisPId'},
					{name:'isNotUpdate'}//liugang 2011-08-06 end 
	];
	var _cfg=window.parent.GridCfg;
	var _url=App.App_Info.BasePath+'/PatientHealthRecordAction.do';
	var _baseParams={
		method:'findPatientHealthRecordList',
		doctorId:top.USER.id,
		weiCha:10,
		yiCha:10
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
	_ds.load({params:{start:0,limit:15}});
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
              },'-',{
            	  text:'网上查房记录',handler:function(){
            	  	var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
            	  	if(_ss.length>1||_ss.length==0){
            	  		alert('请选择一条会员记录。');
            	  		return;
            	  	}
            	  	//liugang 2011-08-06 start 

            	  	if(isPad){
            	  		location.href=App.App_Info.BasePath+"/pad/dCheckRoom/dCheck.html?id="+_ss[0].data.id+"&deptName="+deptName+"&hisPId="+_ss[0].data.hisPId+"&isnotUpdate="+_ss[0].data.isNotUpdate;
            	  	}else{
            	  		location.href=App.App_Info.BasePath+"/module/patientanddoctoroperator/doctorHealthRecord.html?id="+_ss[0].data.id+"&deptName="+deptName+"&hisPId="+_ss[0].data.hisPId+"&isnotUpdate="+_ss[0].data.isNotUpdate+"&patientName="+_ss[0].get("patientName")+'&currentDeptName='+currentDeptName+'&grounpName='+grounpName+'&mobilePhone='+_ss[0].get('mobilePhone');
            	  	}
					

					//liugang 2011-08-06 end 
              	  }
              }, '-', new SearchButton({
                        handler: function(){
                            if (!this.sw) {
                                this.initSw();
                            }
                            this.toggleSw();
                        }
                    })
              ,'-',{
            	  text:'未查房',handler:function(){
            	  	var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findPatientHealthRecordList',doctorId:top.USER.id,
      					weiCha:0,
						yiCha:10
						};
      				_store.load({params:{start:0,limit:15}});
              	  }
              }
              ,'-',{
            	  text:'已查房',handler:function(){
            	  	var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findPatientHealthRecordList',doctorId:top.USER.id,
      					weiCha:10,
						yiCha:1
						};
      				_store.load({params:{start:0,limit:15}});
              	  }
              }
              ,'-',{
            	  text:'返回列表',handler:function(){
            	  	var _store=Ext.getCmp('grid').getStore();
      				_store.baseParams={method:'findPatientHealthRecordList',doctorId:top.USER.id,
      					weiCha:10,
						yiCha:10
						};
      				_store.load({params:{start:0,limit:15}});
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
      				    pid = ss[0].get('id');
      				    patients = ss[0].get('patientName');
      				 if(ss[0].get('mobilePhone')==''||ss[0].get('mobilePhone')==null||(ss[0].get('mobilePhone')!=''&&!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(ss[0].get('mobilePhone'))))){  //如果没有登记手机号码的病人不为空
				 			        alert("会员"+patients+" 手机号码为空或格式不正确");
				 			        return;
				     }
      				 var send=function(id,ctx,callback){
      				 	Ext.Ajax.request({
      					 	url:App.App_Info.BasePath + '/biomedical.do',
      					 	params:{
      					 		method:'sendMsgByPatientId',
      					 		id:id,
      					 		appendix:'北京佑安医院'+currentDeptName+grounpName, //加在短信内容后面的落款
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
                                },{
                                    fieldLabel: '病案号',
                                    name: 'patientNo'
                                },
                                {
                                    fieldLabel: '健康记录时间',
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
                                    store.baseParams.weiCha = 10;
                                    store.baseParams.yiCha  = 10;   
                                    store.load({
                                        params: {
                                            start: 0,
                                            limit: 15
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
}

//liugang 2011-08-06 start 
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
				document.getElementById("dialog1").innerHTML = createDia;
			}else{
				document.getElementById("dialog1").innerHTML = "暂无转科转组记录";
			}
		}
	});
	FormUtil.showJqueryDialog('dialog1');
}
//liugang 2011-08-06 end 
