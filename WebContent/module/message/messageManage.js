
var grounpName;
var deptcode = top.USER['DeptCode'];
var deptcodenameGrounpList = new Array();
var deptcodeGrounp;
var deptName;
var searchObj;
Ext.onReady(function(){
    layout();
});
function layout(){
    var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: App.App_Info.BasePath + '/message.do'
        }),
        reader: new Ext.data.JsonReader({
            root: 'root',
            totalProperty: 'total'
        }, [{
            name: 'id'
        }, {
            name: 'memberInfo'
        }, {
            name: 'user'
        },  {
            name: 'messageDate'
        },/* {
            name: 'circle'
        },*/ {
            name: 'messageContent'
        }/*, {
            name: 'enterDate'
        }, {
            name: 'deptname'
        }, {
            name: 'teamname'
        }*/])
    });
    ds.baseParams = {
            method: 'getMessageManage',
			deptCode:top.USER['DeptCode']  //管理员登录后所在部门编号
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
    
    Ext.QuickTips.init();
    
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
        sm,
        {
        header: '姓名',
        dataIndex: 'memberInfo',
        width:35,
        renderer: function(value){
            return value == null ? "" : value.patient.patientName;
        }
    }, {
        header: '性别',
        width:25,
        dataIndex: 'memberInfo',
        renderer: function(value){
            return value == null ? "" : (value.patient.gender == '1' ? '男' : '女');
        }
    }, {
        header: '出生日期',
        width:50,
        dataIndex: 'memberInfo',
        renderer: function(value){
    	if (value) {
    		if(typeof(value) == "string") {
    		   return value;
    		}
            try {
                return new Date(value == null ? "" : value.patient.birthday.time).format('Y-m-d')
            } 
            catch (err) {
            }
        }
        }
    }, 
    {
        header: '病案号',
        width:40,
        dataIndex: 'memberInfo',
        renderer: function(value){
    	    if(value == null) {
    	       return "";
    	    }
    	    return value == null ? "" : (value.patient.patientNo);
        }
    }, {
        header: '发送人',
        dataIndex: 'user',
        width:60,
        renderer: function(value){
            if (value) {
                try {
                	return value == null||value == 'null' ? '系统发送' : (value.name);
                 } 
                catch (err) {
                }
            }else{
                   return '系统发送';
            }
        }
    }, {
        header: '发送时间',
        dataIndex: 'messageDate',
        sortable:true,
        width:60,
        renderer: function(value, cell, record){
    	if (value) {
    		if(typeof(value) == "string") {
    		   return value;
    		}
            try {
                return new Date(value == null ? "" : value.time).format('Y-m-d H:i:s')
            } 
            catch (err) {
            }
        }
        }
    }, {
        header: '短信内容',
        width:300,
        dataIndex: 'messageContent',
        renderer: function(data,metadata,record,rowIndex,columnIndex,store){
        	  metadata.attr='ext:qtip="'+data+'"';
        	  return data;
//            if (value) {
//            	return value == null ? "" : (value);
//            }
        }
    }]);
    
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
            getAllDeptmentGrounp();
            var o = this;
            o.sw = new Ext.Window({
                draggable: false,
                resizable: false,
                width: 320,
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
                        fieldLabel: '年龄',  
                        name: 'age'
                    }, {
                        xtype: 'panel',
                        border: false,
                        html: '<p style="font-size:12px;color:red;text-align:center">年龄格式例：>60或<60或60</p>'
                    }, {
                        fieldLabel: '病案号',
                        name: 'patientNo'
                    }, {
                        fieldLabel: '用户名',
                        name: 'account'
                    }/*,{
                        fieldLabel: '入院日期',   // 2010-01-02)或(2010-01-01 2010-02-01
                        name: 'inDate'
                    }*/,{
                        fieldLabel: '发送时间',
                        name: 'messageDateBegin'
                    }/*, {
                        fieldLabel: '发送结束时间',
                        name: 'messageDateEnd'
                    }*/, {
                        xtype: 'panel',
                        border: false,
                        html: '<p style="font-size:12px;color:red;text-align:center">时间格式例：2010-01-01或2010-01-01 2010-11-01</p>'
                    }/*,{
                        fieldLabel: '责任科室',
	                    xtype: 'combo',
	                    mode: 'local',
	                    store: new Ext.data.SimpleStore({
							 fields:['clinicValue','showClincType'],
							 data: deptcodenameList
						}),
	                    displayField: 'showClincType',
						valueField: 'clinicValue',
	                    triggerAction: 'all',
	                    readOnly: true,
	                    listeners:{
							select:function(){
						      	deptcode =this.getValue();
						      	getAllDeptmentGrounp();
						      	var _store=Ext.getCmp('grounp').getStore();
	      						_store.loadData(deptcodenameGrounpList);
	      						var _storeValue=Ext.getCmp('grounp');
	      						_storeValue.setValue(deptcodenameGrounpList[0][1]);
							}
						}
                    }*/
                    ,{
                        fieldLabel: '责任小组',
	                    xtype: 'combo',
	                    mode: 'local',
	                    name:'grounpId',
	                    id:'grounp',
	                    store: new Ext.data.SimpleStore({
							 fields:['clinicValue','showClincType'],
							 data: deptcodenameGrounpList
						}),
	                    displayField: 'showClincType',
						valueField: 'clinicValue',
						value:grounpName,
	                    triggerAction: 'all',
	                    readOnly: true,
	                    listeners:{
							select:function(){
						      	deptcodeGrounp =this.getValue();
							}
						} 
                    } 
                    ]
                },
                buttons: [{
                    text: '查询',
                    handler: function(){
                        var values = o.sw.items.get(0).getForm().getValues();
                        values.deptCode = deptcode;
                        values.grounpId=deptcodeGrounp;
                        var store = Ext.getCmp('grid').getStore();
                        searchObj = Ext.encode(values);
                        store.baseParams.search = searchObj; 
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
                        o.sw.items.get(0).getForm().reset();  //用于清空框中的值 by cheng jiangyu 2011-9-30
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
    
    var grid = new Ext.grid.GridPanel({
        id: 'grid',
        title: '短信列表',
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
        ,'-', new SearchButton({
            handler: function(){
            if (!this.sw) {
                this.initSw();
            }
            this.toggleSw();
        }
    }),'-',{
            text: '发送短信',
            handler: function(){
                 var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
				 if (ss.length == 0) {
				     alert('请至少选择一条记录。');
				     return;
				 }
				 var pid = "";    //支持多选发送短信
				 var patients = ""; //给哪些病人发短信
				 var patientsNoPhoneFormat =""; //哪些病人登记的手机号码格式不正确  用正则表达式判断
				 var patientsValue = ""; //哪些病人手机号码正确
				 for(var i=0,size=ss.length;i<size;i++){
				     patients = patients + ss[i].get('memberInfo').patient.patientName+",";
				     if(ss[i].get('memberInfo').patient.mobilePhone==''||ss[i].get('memberInfo').patient.mobilePhone==null||(ss[i].get('memberInfo').patient.mobilePhone!=''&&!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(ss[i].get('memberInfo').patient.mobilePhone))))  //判断如果会员手机号不为空并且不符合手机号格式，要提示
				           patientsNoPhoneFormat = patientsNoPhoneFormat + ss[i].get('memberInfo').patient.patientName+","; 
				     else{
					     patientsValue = patientsValue + ss[i].get('memberInfo').patient.patientName+",";
					     pid = pid + ss[i].get('memberInfo').memberNo+",";  //会员标示 memberNo
				     }      
				 }
				 patients = patients.substring(0,patients.length-1);
				 if(patientsNoPhoneFormat!=''&&patientsNoPhoneFormat.length>0)
				   patientsNoPhoneFormat = patientsNoPhoneFormat.substring(0,patientsNoPhoneFormat.length-1);
				 if(patientsValue!=''&&patientsValue.length>0)
				    patientsValue = patientsValue.substring(0,patientsValue.length-1);  
				    var send=function(id,ctx,callback){
				 	Ext.Ajax.request({
					 	url:App.App_Info.BasePath + '/biomedical.do',
					 	params:{
					 		method:'sendMsg',
					 		id:id,
					 		appendix:'北京佑安医院'+top.USER.单位, //加在短信内容后面的落款
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
				 	title:'给【'+patientsValue+'】发送短信',
				 	closeAction:'close',
				 	width:400,
				 	height:200,
				 	autoHeight:true,
				 	layout:'anchor',
				 	items:[{
				 		xtype:'textarea',
				 		width:400,
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
				 			  if(patientsNoPhoneFormat!=''&&patientsNoPhoneFormat.length>0&&patientsNoPhoneFormat.length==patients.length){  //选中的所有会员的手机号码都格式不正确或为空
						 			       alert("会员"+patientsNoPhoneFormat+"手机号码为空或格式不正确");
						 			       return;
					 		  }else if(patientsNoPhoneFormat!=''&&patientsNoPhoneFormat.length>0&&patientsNoPhoneFormat.length<patients.length){
					 			      if(confirm("会员"+patientsNoPhoneFormat+"手机号码为空或格式不正确,您确定要给其他会员发送吗？")){
					 			            var win=this.ownerCt;
							 				var value=win.items.get(0).getValue();
							 				var headAndAppendix = '北京佑安医院'+top.USER.单位;  //自动的短信头和落款 加上短信正文
							 			     if(value&&value.length>5){ 
							 			        if(Math.ceil(headAndAppendix.length/64)>1){   //看发送的短信字数是否超过一条短信
									 			       if(confirm('您确定发送吗？该条短信将分'+Math.ceil(headAndAppendix.length/64)+'条发送')){
									 			       	   win.close();
									 			           send(pid,value,function(data){
								 					  	   });
									 			       }else{
									 			         return;
									 			       }
							 			         }
							 			       	 else{
							 			       	 	win.close();
							 			       	    send(pid,value,function(data){
							 							  });
							 			       	 }
							 			     }else{
							 					 alert('信息正文不能为空。');
							 				 }
					 			      }
					 			  }
					 			  
					 			    var win=this.ownerCt;
					 				var value=win.items.get(0).getValue();
					 				var headAndAppendix = '北京佑安医院'+top.USER.单位+value;  //自动的短信头和落款 加上短信正文
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
        },'-', {
            text: '导出电子表格',
            handler: function(){
                var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
                var url=App.App_Info.BasePath+'/message.do?method=creatExcelManage';
                if(ss.length>0){  //如果选择了记录
                     var ids = "";    //短信ids
				     for(var i=0,size=ss.length;i<size;i++){
				        ids = ids + ss[i].get('id')+",";  //拼接短信id
				     }
				     ids = ids.substring(0,ids.length-1);
				     url += '&ids='+ids;
                }else{  //如果没有选择记录
                    url += '&deptCode='+top.USER['DeptCode'];
		            if(searchObj)
		        	  url += ('&search='+encodeURIComponent(encodeURIComponent(searchObj)));
		        }
		    	window.open(url);
		    	searchObj = null;
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
    new Ext.Viewport({
        layout: 'fit',
        items: grid
    });
}

var deptcodenameList = new Array();		
	
//通过管理员的deptCode查找管理员所在的小组列表
function getAllDeptmentGrounp(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentHISAction.do',
		params:{
			method:'findManageDeptGroupBydeptCode',
			deptCode:top.USER['DeptCode']
		},
		sync:true,
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			var deptGrounp = new Array();
			deptGrounp.push(null);
			deptGrounp.push("--请选择--");
			deptcodenameGrounpList.push(deptGrounp);
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
	
