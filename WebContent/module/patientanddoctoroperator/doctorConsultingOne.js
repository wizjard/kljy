var baseUrl=App.App_Info.BasePath+'/PatientConsultingAction.do';
var pcaId = App.util.getHtmlParameters('pcId');
var patientId = App.util.getHtmlParameters('patientId');

var patientName= App.util.getHtmlParameters('patientName');
patientName = decodeURI(patientName);

var mobilePhone= App.util.getHtmlParameters('mobilePhone');  //获得某个会员的手机号
mobilePhone = decodeURI(mobilePhone);

var hisPId = App.util.getHtmlParameters('hisPId');
var currentDeptCode= App.util.getHtmlParameters('currentDeptCode');//当前科室
//liugang 2011-08-09 start
var isnotUpdate = App.util.getHtmlParameters('isnotUpdate');//是否转科
//liugang 2011-08-09 end

var deptcodenameList=new Array();//部门列表
var deptcode;//科室编码
var inWard;//入会科室
var deptcodenameGrounpList = new Array();//责任小组List
var deptcodeGrounp;//责任小组ID
var zixunDept;//咨询科室
var zeRerDept;//责任科室
var isOrnotSend;//咨询是否已经转发
var isCloseMeeting = false;//是否关闭当前咨询
var checkIsNotCloseMeeting = false;
var oldDeptCode;//会员所在的责任科室CODE
var checkCurrentDept = false;

//获得医生所在责任科室和小组参数  by cheng jiangyu 2011-9-25
var currentDeptName= App.util.getHtmlParameters('currentDeptName');
currentDeptName = decodeURI(currentDeptName);
var grounpName= App.util.getHtmlParameters('grounpName');
grounpName = decodeURI(grounpName);

Ext.onReady(function(){
	getAllDeptment();
	var sendGrounp = Ext.extend(Ext.Button, {
                    text: '网上咨询申请',
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
										fieldLabel: '咨询科室',
					                    xtype: 'combo',
					                    mode: 'local',
					                    id:'deptNameObject',
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
									},
									{
					                	fieldLabel: '咨询小组',
					                    xtype: 'combo',
					                    mode: 'local',
					                    id:'grounp',
					                    store: new Ext.data.SimpleStore({
											 fields:['clinicValue','showClincType'],
											 data: deptcodenameGrounpList
										}),
					                    displayField: 'showClincType',
										valueField: 'clinicValue',
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
                                text: '保存',
                                handler: function(){
                                	if(deptcodeGrounp == undefined){
										alert("请先选择咨询科室和小组");
										return ;
									}
									var values = o.sw.items.get(0).getForm().getValues();
									values.pcaId = pcaId;
									values.deptcode=deptcode;
									values.deptcodeGrounp=deptcodeGrounp;
									 Ext.Ajax.request({
										url:App.App_Info.BasePath+'/PatientConsultingAction.do',
										params:{
											method:'sendToOtherDeparment',
											data:Ext.encode(values)
										},
										sync:true,
										success:function(response){
											var res = Ext.util.JSON.decode(response.responseText);
											if(res.success){
												var data = Ext.util.JSON.decode(res.data);
												if(data){
													alert("会员咨询转发成功");
													var allId = document.getElementById("id");
													allId.innerHTML=createMainHtml();
													o.sw.hide();
												}else{
													alert("当前科室已经是咨询科室,无需再次转发");
													return;
												}
											}else{
												alert("会员咨询转发失败");
												o.sw.hide();
											}
										}
									});
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
                
    //咨询附言
    var sendMessage = Ext.extend(Ext.Button, {
                    text: '咨询附言',
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
	                                    fieldLabel: '附言框',
					                    xtype: 'textarea',
					                    height: 80,
					                    allowBlank:false,
					                    anchor: '95%',
					                    name: 'applicationBacuse'
	                                }
                                ]
                            },
                            buttons: [{
                                text: '保存',
                                handler: function(){
									var values = o.sw.items.get(0).getForm().getValues();
									 Ext.Ajax.request({
										url:App.App_Info.BasePath+'/PatientConsultingAction.do',
										params:{
											method:'savaPatientConsultingMessage',
											message:Ext.encode(values),
											pcId:pcaId,
											doctorId:top.USER.id
										},
										sync:true,
										success:function(response){
											var res = Ext.util.JSON.decode(response.responseText);
											if(res.success){
												alert("保存咨询附言成功");												
											}else{
												alert("保存咨询附言失败");		
											}
											o.sw.hide();
										}
									});
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
    
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'north',
				border:false,
				height:23,
				html:'<div id="PatSimInfo-DIV"></div>'
			},{
				title:'选择操作',
				region:'west',
				width:115,
				split:true,
				collapsible:true,
				xtype:'treepanel',
				autoScroll:true,
				containerScroll:true,
				dataUrl:'WAIEMR_TREE_CFG.json',
				root:{
					nodeType:'async',
					text:'字典树',
					draggable:false,
					id:-1
				},
				rootVisible:false,
				listeners:{
					render:function(){
						this.expandAll();
					}
				}
			},
			{
				region:'center',
				xtype:'panel',
				border:true,
				layout:'fit',
				autoScroll:true,				
				tbar:[
						{
							xtype:'tbseparator'
						},
						new sendGrounp({
		                    handler:function(){
		                    	 if(isnotUpdate == 1){
		                    	 	alert("当前会员已经转科，该咨询不能再转发");
//		                    	 	if(window.confirm("当前会员已经转科，您确定要转发咨询？")){
//							   	 		if (!this.sw) {
//					                         this.initSw();
//					                     }
//					                     this.toggleSw();
//							     	}
									return;
		                    	 }else{
		                    	 	if(!checkIsNotZeRen()){
		                    	 		alert("仅责任科室可以转发咨询！");
		                    	 		return;
			                    	 }
			                    	 if(isCloseMeeting){
			                    	 	alert("咨询已经结束，不能再转发咨询！");
			                    	 	return;
			                    	 }
				                     if (!this.sw) {
				                         this.initSw();
				                     }
				                     this.toggleSw();
			                     }
		                       }
	                       }),
						'-',
						new sendMessage({
		                    handler:function(){
		                    //liugang 2011-08-06 修改 Start
		                    	$('textarea[name="applicationBacuse"]').val("");
		                    	if(isOrnotSend == null){
		                    		alert("咨询科室为本科室，无需填写咨询附言！");
		                    	 	return;
		                    	}
		                    	 if(isCloseMeeting){
		                    	 	alert("咨询已经结束，无需再保存咨询附言！");
		                    	 	return;
		                    	 }
			                     if (!this.sw) {
			                         this.initSw();
			                     }
			                     this.toggleSw();
		                       }
	                       }),'-', {
					            text: '发送短信',
					            handler: function(){
									 var pid=patientId;
									 if(mobilePhone==''||mobilePhone==null||(mobilePhone!=''&&!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(mobilePhone)))) { //判断如果会员手机号不为空并且不符合手机号格式，要提示
		    					 		 alert('会员'+patientName+'手机号码为空或格式不正确');
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
									 	title:'给【'+patientName+'】发送短信',
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
									 				var headAndAppendix = '患者您好,北京佑安医院'+currentDeptName+grounpName+value;  //自动的短信头和落款 加上短信正文
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
					        },'-',
						{
							text:'结束咨询',handler:function(){
							//liugang 2011-08-06 start
								 if(!checkIsNotZeRen()){
		                    	 	alert("仅责任科室可以结束咨询！");
		                    	 	return;
		                    	 }
//								if(isOrnotSend == null){
//		                    		alert("咨询科室为本科室，无需结束咨询！");
//		                    	 	return;
//		                    	}
							//liugang 2011-08-06 start
							    if(checkIsNotCloseMeeting){
									alert("当前无需关闭咨询");
									return ;							    
							    }
							    if(window.confirm("您确定要结束本次咨询？")){
							   	 	closeMeetting();
							    }
								
							}
						}
						,'-',
						{
							text:'撤销转发咨询',handler:function(){
								if(!checkIsNotZeRen()){
		                    	 	alert("仅责任科室可以撤销转发咨询！");
		                    	 	return;
		                    	}
		                    	if(isCloseMeeting){
		                    	 	alert("咨询已经结束，不能撤销！");
		                    	 	return;
		                    	 }
								if(isOrnotSend == null){
									alert("当前咨询没有被转发，无需撤销");
									return ;							    
							    }
							    Ext.Ajax.request({
									url:App.App_Info.BasePath+'/PatientConsultingAction.do',
									params:{
										method:'findAllPatientConsultingByCancel',
										id:pcaId
									},
									scope:this,
									sync:true,
									success:function(_response){
										var lists = Ext.util.JSON.decode(_response.responseText);
										if(lists != "" && lists.length > 0)
										{
											alert("咨询科室已回复，不能撤销");
											return ;
										}else{
											if(window.confirm("您确定要撤销所有的转发咨询吗？")){
												Ext.Ajax.request({
													url:App.App_Info.BasePath+'/PatientConsultingAction.do',
													params:{
														method:'cancelAllSendPatientCousulting',
														id:pcaId
													},
													sync:true,
													success:function(response){
														var res = Ext.util.JSON.decode(response.responseText);
														if(res.success){
															alert("转发咨询撤销成功");	
															var allId = document.getElementById("id");
															allId.innerHTML=createMainHtml();										
														}else{
															alert("转发咨询撤销失败");		
														}
													}
												});
											}
										}
									}
								});
							}
						}
						,'-',
						{
							text:'返回',handler:function(){
								window.location.href=App.App_Info.BasePath+'/module/patientanddoctoroperator/doctorLogin.html';
							}
						}
					],
				listeners:{
					render:function(){
						var div = document.createElement("div");
						div.innerHTML = '<div id="id">'+createMainHtml()+'</div>';
						this.body.dom.appendChild(div);
					}
				}	
			}
		]
	});
	loadPatSimInfo();
});



//查找所有的科室
function getAllDeptment(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PatientConsultingAction.do',
		params:{
			method:'findAllSYS_HIS_DepartmentHISEntity',
			name:'inHospitalType',
			nameValue:1
		},
		sync:true,
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			for(var i=0,size=list.length;i<size;i++){
				var deptcodenameList1 = new Array();
				deptcodenameList1.push(list[i].deptCode);
				deptcodenameList1.push(list[i].deptName);
				deptcodenameList.push(deptcodenameList1);
			}
			deptcode = deptcodenameList[0][0];//科室编码动态改变加载对应的数据
		}
	});
}

//查找某个科室下的责任小组
function getAllDeptmentGrounp(){
	deptcodenameGrounpList = new Array();
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
		params:{
			method:'findDepartmentGrounpByDeptId',
			deptCode:deptcode
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


//liugang获取当前时间
function currentTime()
{
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	if(month < 10){
		month = "0"+month;
	}
	var day = date.getDate();
	if(day <10){
		day = "0"+day;
	}
	var hours = date.getHours();
	if(hours <10)
	{
		hours = "0"+hours;
	}
	var minute = date.getMinutes();
	if(minute <10)
	{
		minute = "0"+minute;
	}
	var second = date.getSeconds();
	if(second <10){
		second ="0"+second;
	}
	var currentTime = year+"-"+month+"-"+day+" "+hours+":"+minute+":"+second;
	return currentTime;
	
}

//下载事件
function download(fileName){
	document.getElementById("fileName").value = fileName;
	document.getElementById("downloadForm").submit();
}

function createMainHtml(){
	var mainHTML = '<table width=600 border=0 cellspacing=0 cellpadding=0>'
		+'<tr><td align="center" width=60><img width=50 src="../../PUBLIC/images/youanLogo.jpg"/></td>'
		+'<td valign="bottom">'
		+'<div style="font-size:18px;font-weight:bold;">首都医科大学附属北京佑安医院</div>'
		+'<div style="font-size:11px;font-weight:bold;">Beijing YouAn Hospital,Capital Medical University</div>'
		+'</tr>'
		+'</table>';
	Ext.Ajax.request({
		url:baseUrl,
			params:{
				method:'findAllPatientConsultingBypcID',
				pcId:pcaId
			},
			sync:true,
			success:function(response){
				var list = Ext.util.JSON.decode(response.responseText);
				for(var i=0,size=list.length;i<size;i++){
					var imagePath="";
					var filePath="";
					if(list[i].pcMeetingState == 1){
						isCloseMeeting = true;
					}
					isOrnotSend = list[i].isNotSend;//隐藏是否转发判断条件
//					if(list[i].uploadImage !=null && list[i].uploadImage != ""){
//						if(list[i].uploadImage.lastIndexOf("image") > 0){
//							imagePath +='<tr><td class="titletd" colspan="3"><font size="2">上传图片<font></td></tr>'
//							+'<tr><td colspan="3" style="padding-left:44px"><div style="width:530px;font-size:12px;">'+list[i].uploadImage.replace("http://localhost:8080/TCMP/","")+'</div></td></tr>';
//						}else{
//							//imagePath+='<tr><td colspan="3"></td></tr>';
//						}
//					}
					
					if(list[i].uploadFile !="" && list[i].uploadFile != null){	
								var uploadFileArr = list[i].uploadFile.split("\*");
								if(list[i].uploadFile.toLocaleLowerCase().indexOf(".rar") > 0 || list[i].uploadFile.toLocaleLowerCase().indexOf(".zip") > 0
										|| list[i].uploadFile.toLocaleLowerCase().indexOf(".doc") > 0|| list[i].uploadFile.toLocaleLowerCase().indexOf(".xls") > 0|| list[i].uploadFile.toLocaleLowerCase().indexOf(".pdf") > 0
										|| list[i].uploadFile.toLocaleLowerCase().indexOf(".xlsx") > 0|| list[i].uploadFile.toLocaleLowerCase().indexOf(".jpg") > 0|| list[i].uploadFile.toLocaleLowerCase().indexOf(".gif") > 0
										|| list[i].uploadFile.toLocaleLowerCase().indexOf(".bmp") > 0|| list[i].uploadFile.toLocaleLowerCase().indexOf(".jpeg") > 0||list[i].uploadFile.toLocaleLowerCase().indexOf(".png") > 0||list[i].uploadFile.toLocaleLowerCase().indexOf(".txt") > 0
								){
									filePath+='<tr><td colspan="3" style="background-color: #f5f5f5;"><font size="2">上传资料<font></td></tr>'		
									+'<tr><td colspan="3" height="auto" style="padding-left:44px">'
									+'<ol>';
									for(var j=0;j<uploadFileArr.length;j++){
										if(uploadFileArr[j]!=""){
											var end = uploadFileArr[j].lastIndexOf("_");
											var displayName = uploadFileArr[j].substring(0,end);
											var dianEnd = uploadFileArr[j].lastIndexOf(".");
											var ext = uploadFileArr[j].substring(dianEnd+1);
											var displayFileName = displayName+"."+ext;
											filePath+='<li><a href="javascript:download(\''+uploadFileArr[j]+'\')">'+displayFileName+'</a></li>' 
										}
									}
									filePath+='</ol></td></tr>';
								}else{
									//filePath+='<tr><td colspan="3"></td></tr>';
								}
							}
					
			
					var spiritStatus = "";
					if(list[i].spiritStatus != ""){
						spiritStatus+='<tr><td class="titletd" colspan="3"><font size="2">异常表现</font></td></tr>'
						+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:530px;font-size:12px;"><textarea style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].spiritStatus+'</textarea></div></td></tr>';
					}else{
						spiritStatus+='<tr><td colspan="3"></td></tr>';
					}
						
					var treatmentStatus = "";
					if(list[i].treatmentStatus != ""){
						treatmentStatus +='<tr><td class="titletd" colspan="3"><font size="2">用药情况<font></td></tr>'
						+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:530px;font-size:12px;"><textarea style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].treatmentStatus+'</textarea></div></td></tr>';
					}else{
						treatmentStatus+='<tr><td colspan="3"></td></tr>';
					}
						
					var improveStatus ="";
					if(list[i].improveStatus != ""){
						improveStatus+='<tr><td class="titletd" colspan="3"><font size="2">治疗效果<font></td></tr>'
						+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:530px;font-size:12px;"><textarea style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].improveStatus+'</textarea></div></td></tr>';
					}else{
						improveStatus+='<tr><td colspan="3"></td></tr>';
					}
					mainHTML+='<div class="ablock" style="margin-left:50px;margin-top:10px;" id="div_'+list[i].consultingCount+'"><table width="600" border=0 cellSpacing=0 borderColorDark=#ffffff cellPadding=2><tr>'
							+'<td colspan=4 class="title">&nbsp;</td></tr><tr>'
							+'<td width="200" class="titletd"><b>日期：<span>'+list[i].consultingDate+'</span></b></td>'
							+'<td class="titletd" widht="200" colspan="2"><div align="center"><font size="3" >&nbsp;&nbsp;&nbsp;&nbsp;<B>【'+list[i].patientName+'】的咨询</B>&nbsp;&nbsp;&nbsp;&nbsp;</font></div></td>'
							+'<td class="titletd" width="200"><div align="center"><b>第<font color="red" name="couZ">'+(list[i].consultingCount)+'</font>次咨询记录</b></div></td>'
							+'</tr><tr><td class="titletd" colspan="4"><font size="2">一般情况</font></td></tr></table><table width="600" border=0 cellSpacing=0 borderColorDark=#ffffff cellPadding=2>'
							+'<tr>'
							+'<td class="smalltd" style="padding-left:50px" width="200"><div style="font-size:12px;">精神状态：'
							+'<span style="font-size:12px;">'+list[i].healthStatus+'</span></div></td>'
							+'<td class="smalltd" width="200"><div style="font-size:12px;align:left">食量：'
							+'<span style="font-size:12px;">'+list[i].diet+'</span></div></td>'
							+'<td class="smalltd" width="200"><div style="font-size:12px;align:left">睡眠：'
							+'<span style="font-size:12px;">'+list[i].sleep+'</span></div></td>'
							+'</tr><tr>'
							+'<td class="smalltd" style="padding-left:50px" width="200" ><div style="font-size:12px;">小　　便：'
							+'<span style="font-size:12px;">'+list[i].piss+'</span></div></td>'
							+'<td class="smalltd" width="200"><div style="font-size:12px;">大便：'
							+'<span style="font-size:12px;">'+list[i].defecate+'</span></div></td>'
							+'<td class="smalltd" width="200"><div style="font-size:12px;">体重：'
							+'<span style="font-size:12px;">'+list[i].weight+'</span></div></td>'
							+'</tr>'
							+spiritStatus
							+treatmentStatus
							+improveStatus
							+'<tr><td class="titletd" colspan="3" style="background-color: #f5f5f5;"><font size="2">咨询问题<font></td></tr>'
							+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:530px;font-size:12px;color:red"><textarea style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].mainProblem+'</textarea></div></td></tr>'
							//+'<tr><td colspan="3"></td></tr>'
							+filePath
							+imagePath
							+'</table><table width="600" border=0 cellSpacing=0 borderColorDark=#ffffff cellPadding=2>';
							oldDeptCode = list[i].deptCode;
							zixunDept = list[i].zixundeptName;	
							zeRerDept = list[i].nameAndGrounp;
							mainHTML+='<tr>'
							+'<td class="titletd" colspan="2"><font size="2">责任科室：</font>'
							+'<span style="font-size:12px;color:#00688B">'+list[i].nameAndGrounp+'</span></td>'
							+'</tr>';
							if(list[i].pcMeetingState == 1){
								checkIsNotCloseMeeting = true;//表示咨询已经结束
							}
							if(zixunDept != null && zixunDept.length > 0){
								if(zixunDept[0] != list[i].nameAndGrounp){
									for(var zx=0;zx<zixunDept.length;zx++){
										mainHTML+='<tr>'
											+'<td class="titletd" colspan="2"><font size="2">咨询科室：</font>'
											+'<span style="font-size:12px;color:#00688B">'+zixunDept[zx]+'</span></td>'
											+'</tr>'
									}
								}
							}
							mainHTML+='<tr><td class="titletd" colspan="2"><span style="padding-left:500px"><a href="###" style="font-size:12;color:#00688B;text-decoration:none"'
							+'onclick="showMessage('+list[i].consultingCount+',this,'+list[i].id+')">收起回复(<span id="countAll'+list[i].consultingCount+'">'+list[i].count+'</span>条)</a></span>'
//							+'<span style="padding-left:80px"><a href="###" style="font-size:12;color:#00688B;text-decoration:none"'
//							+'onclick="flushMessage('+list[i].consultingCount+','+list[i].id+')">刷新回复列表</a></span>'
//							+'<span style="padding-left:80px"><a href="###" style="font-size:12;color:#00688B;text-decoration:none"'
//							+'onclick="cancelMessage(this,'+list[i].id+')">取消回复</a></span>'
							+'</td></tr>'
							+'<tr><td class="replytd" colspan="2" id="message'+list[i].consultingCount+'">';
							var imag = "";
							Ext.Ajax.request({
									url:App.App_Info.BasePath+'/DoctorReplyRecordAndPatientQuestionsAction.do',
									params:{
										method:'findAllDoctorReplyRecordAndPatientQuestionsByPCId',
										pcId:list[i].id
									},
									sync:true,
									success:function(response){
										var list = Ext.util.JSON.decode(response.responseText);
											for(var i=0,size=list.length;i<size;i++){
											var imag = "";
											if(list[i].drAndpqFlag == 0){
												imag+='<img src="image/mandoctor.jpg" style="width:45px;height:52px">';
											}else{
												imag+='<img src="image/Patient.png" style="width:45px;height:52px">';
											}
												mainHTML+='<table><tr><td style="width:45px;" valign="top">'
													+imag
												+'</td><td>'
												+'<table width="545">'
													+'<tr><td style="font-size:15px;color:#00688B" width=75>&nbsp;'+list[i].drAndpqName+'</td><td width=455 style="font-size:12px;">'+list[i].drAndpqDate+'</td></tr>'
													+'<tr><td colspan="2" style="font-size:12px"><textarea name="message" readonly style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1">'+list[i].drAndpqContent+'</textarea></td></tr>'
													+'</table></td></tr></table>';
											}
									}
							});
							+'</td></tr>';
							//liugang 2011-08-16 因为当时给责任科室留，当咨询结束后，咨询科室和责任科室仍然可以交流。
//							if(oldDeptCode != currentDeptCode){//先判断是否是责任科室
								if(list[i].pcMeetingState != 1){
									mainHTML+='<tr id="shoeMe"><td class="replytd" colspan="2"><textarea name="message" style="width:470px;" class="textarea"></textarea><button class="sign" style="height:26px" onclick="submitSaveData('+list[i].consultingCount+',this,'+list[i].id+')">留言</button>'
									+'<button class="sign" style="height:26px;width:80px" onclick="flushMessage('+list[i].consultingCount+','+list[i].id+')">最新消息刷新</button></td></tr>'
									+'<tr id="shoeMe1"><td colspan=2>';
								}
//							}else{
//								mainHTML+='<tr id="shoeMe"><td class="replytd" colspan="2"><textarea name="message" style="width:470px;" class="textarea"></textarea><button class="sign" style="height:26px" onclick="submitSaveData('+list[i].consultingCount+',this,'+list[i].id+')">留言</button>'
//									+'<button class="sign" style="height:26px;width:80px" onclick="flushMessage('+list[i].consultingCount+','+list[i].id+')">最新消息刷新</button></td></tr>'
//									+'<tr id="shoeMe1"><td colspan=2>';
//							}
							mainHTML+='</table></div>';
					}
			}
		});
	return mainHTML;
}

function showMessage(num,_this,pcid){
	var messageThis = document.getElementById("message"+num);
	if(_this.innerHTML.indexOf("收起回复") == 0){
		messageThis.innerHTML = "";
		_this.innerHTML = "打开"+_this.innerHTML.replace("收起","");
	}else if(_this.innerHTML.indexOf("打开回复") == 0){
		var showAllMessage ='';
		Ext.Ajax.request({
			url:App.App_Info.BasePath+'/DoctorReplyRecordAndPatientQuestionsAction.do',
			params:{
				method:'findAllDoctorReplyRecordAndPatientQuestionsByPCId',
				pcId:pcid
			},
			sync:true,
			success:function(response){
				var list = Ext.util.JSON.decode(response.responseText);
					for(var i=0,size=list.length;i<size;i++){
					var imag = "";
					if(list[i].drAndpqFlag == 0){
						imag+='<img src="image/mandoctor.jpg" style="width:45px;height:52px">';
					}else{
						imag+='<img src="image/Patient.png" style="width:45px;height:52px">';
					}
						showAllMessage+='<table><tr><td style="width:45px;" valign="top">'
							+imag
						+'</td><td>'
						+'<table width="545" style="background-color: #D1EEEE;border-bottom:solid #AAAAAA 1px">'
							+'<tr><td style="font-size:15px;color:#00688B" width=75>&nbsp;'+list[i].drAndpqName+'</td><td width=455 style="font-size:12px;">'+list[i].drAndpqDate+'</td></tr>'
							+'<tr><td colspan="2" style="font-size:12px"><textarea name="message" readonly style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1">'+list[i].drAndpqContent+'</textarea></td></tr>'
							+'</table></td></tr></table>';
					}
			}
		});
		messageThis.innerHTML = showAllMessage;
		_this.innerHTML = "收起"+_this.innerHTML.replace("打开","");
	}
}

function submitSaveData(num,_this,pcid){
//liugang 2011-08-09 start
	 if(isnotUpdate == 1){
	 	if(window.confirm("当前会员已经转科，您确定要继续回复吗？")){
	 		var prevTextarea = $(_this).prev().val();
			if(prevTextarea == ""){
				alert("请输入回复内容后提交");
				return;
			}
			var tableNode = document.createElement("div");
			var showAllMessage ="";
			Ext.Ajax.request({
					url:App.App_Info.BasePath+'/DoctorReplyRecordAndPatientQuestionsAction.do',
					params:{
						method:'saveDoctorReplyRecordAndPatientQuestions',
						pcId:pcid,
						content:prevTextarea,
						date:currentTime()
		//				zixunDept:zixunDept,
		//				zeRerDept:zeRerDept
					},
					sync:true,
					success:function(response){
						var data = Ext.util.JSON.decode(response.responseText);
						if(data.success){
							var obj = Ext.util.JSON.decode(data.data);
							var imag = "";
							if(obj.drAndpqFlag == 0){
								imag+='<img src="image/mandoctor.jpg" style="width:45px;height:52px">';
							}else{
								imag+='<img src="image/Patient.png" style="width:45px;height:52px">';
							}
							showAllMessage+='<table><tr><td style="width:45px;" valign="top">'
									+imag
								+'</td><td>'
								+'<table width="545" style="background-color: #D1EEEE;border-bottom:solid #AAAAAA 0.5px">'
								+'<tr><td style="font-size:15px;color:#00688B" width=75>&nbsp;'+obj.drAndpqName+'</td><td width=455 style="font-size:12px;">'+obj.drAndpqDate+'</td></tr>'
								+'<tr><td colspan="2" style="font-size:12px"><textarea name="message" readonly style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1">'+obj.drAndpqContent+'</textarea></td></tr>'
								+'</table></td></tr></table>';
						}else{
							alert("登录超时，请重新登录！");
							window.top.location.href =App.App_Info.BasePath+"/module/biomedical/member/loginDoctor.jsp";
						}
					}
			});
			var messageThis = document.getElementById("message"+num);
			tableNode.innerHTML =showAllMessage;
			messageThis.appendChild(tableNode);
			var nerCount = document.getElementById("countAll"+num);
			nerCount.innerHTML = parseInt(nerCount.innerHTML)+1;
			$(_this).prev().val("");
			 	}
	 }else{
	 	var prevTextarea = $(_this).prev().val();
		if(prevTextarea == ""){
			alert("请输入回复内容后提交");
			return;
		}
		var tableNode = document.createElement("div");
		var showAllMessage ="";
		Ext.Ajax.request({
				url:App.App_Info.BasePath+'/DoctorReplyRecordAndPatientQuestionsAction.do',
				params:{
					method:'saveDoctorReplyRecordAndPatientQuestions',
					pcId:pcid,
					content:prevTextarea,
					date:currentTime()
	//				zixunDept:zixunDept,
	//				zeRerDept:zeRerDept
				},
				sync:true,
				success:function(response){
					var data = Ext.util.JSON.decode(response.responseText);
					if(data.success){
						var obj = Ext.util.JSON.decode(data.data);
						var imag = "";
						if(obj.drAndpqFlag == 0){
							imag+='<img src="image/mandoctor.jpg" style="width:45px;height:52px">';
						}else{
							imag+='<img src="image/Patient.png" style="width:45px;height:52px">';
						}
						showAllMessage+='<table><tr><td style="width:45px;" valign="top">'
								+imag
							+'</td><td>'
							+'<table width="545" style="background-color: #D1EEEE;border-bottom:solid #AAAAAA 0.5px">'
							+'<tr><td style="font-size:15px;color:#00688B" width=75>&nbsp;'+obj.drAndpqName+'</td><td width=455 style="font-size:12px;">'+obj.drAndpqDate+'</td></tr>'
							+'<tr><td colspan="2" style="font-size:12px"><textarea name="message" readonly style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1">'+obj.drAndpqContent+'</textarea></td></tr>'
							+'</table></td></tr></table>';
					}else{
						alert("登录超时，请重新登录！");
						window.top.location.href =App.App_Info.BasePath+"/module/biomedical/member/loginDoctor.jsp";
					}
				}
		});
		var messageThis = document.getElementById("message"+num);
		tableNode.innerHTML =showAllMessage;
		messageThis.appendChild(tableNode);
		var nerCount = document.getElementById("countAll"+num);
		nerCount.innerHTML = parseInt(nerCount.innerHTML)+1;
		$(_this).prev().val("");
	 }
	//liugang 2011-08-09 start
}

//刷新回复列表
function flushMessage(num,pcid){
	var messageThis = document.getElementById("message"+num);
	var showAllMessage ='';
		Ext.Ajax.request({
			url:App.App_Info.BasePath+'/DoctorReplyRecordAndPatientQuestionsAction.do',
			params:{
				method:'findAllDoctorReplyRecordAndPatientQuestionsByPCId',
				pcId:pcid
			},
			sync:true,
			success:function(response){
				var list = Ext.util.JSON.decode(response.responseText);
					for(var i=0,size=list.length;i<size;i++){
					var imag = "";
					if(list[i].drAndpqFlag == 0){
						imag+='<img src="image/mandoctor.jpg" style="width:45px;height:52px">';
					}else{
						imag+='<img src="image/Patient.png" style="width:45px;height:52px">';
					}
						showAllMessage+='<table><tr><td style="width:45px;" valign="top">'
							+imag
						+'</td><td>'
						+'<table width="545" style="background-color: #D1EEEE;border-bottom:solid #AAAAAA 1px">'
							+'<tr><td style="font-size:15px;color:#00688B" width=75>&nbsp;'+list[i].drAndpqName+'</td><td width=455 style="font-size:12px;">'+list[i].drAndpqDate+'</td></tr>'
							+'<tr><td colspan="2" style="font-size:12px"><textarea name="message" readonly style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1">'+list[i].drAndpqContent+'</textarea></td></tr>'
							+'</table></td></tr></table>';
					}
			}
		});
		messageThis.innerHTML = showAllMessage;
}

function cancelMessage(_this,pcid){
	Ext.Ajax.request({
			url:App.App_Info.BasePath+'/DoctorReplyRecordAndPatientQuestionsAction.do',
			params:{
				method:'cancelMessageByDoctorOperation',
				pcId:pcid
			},
			sync:true,
			success:function(response){
				var data = Ext.util.JSON.decode(response.responseText);
				if(data.success){
					alert("设置取消回复成功");
					_this.innerHTML ="";
				}else{
					alert("请先做出提示回复，再去设置取消回复");
				}
			}
		});
}

function loadPatSimInfo(){
	var _div=Ext.get('PatSimInfo-DIV');
	if (!_div) {
		Ext.DomHelper.append(Ext.getBody(), {
			tag: 'div',
			id: 'PatSimInfo-DIV'
		});
		_div=Ext.get('PatSimInfo-DIV');
	}
	_div.load({
		url:App.App_Info.BasePath+'/module/Patient/PatSimpleInfo.jsp?timestamp='+new Date().getTime()+'&id='+patientId,
		scripts:true,
		text:'正在获取病人基本信息......'
	});
}


//左侧树上的所有操作
Do={
	PatientInfo:function(){//病人基本信息
		if(oldDeptCode != currentDeptCode){//先判断是否是责任科室
			if(!isCloseMeeting){
				App.util.addNewMainTab('/module/Patient/PatientInfo.html?id='+patientId,'病人基本信息');
			}else{
				alert("咨询已结束，不能再查看病人的基本信息");
			}
		}else{
			App.util.addNewMainTab('/module/Patient/PatientInfo.html?id='+patientId,'病人基本信息');
		}	
		
	},
	MemberInfo:function(){//会员入会信息
		if(oldDeptCode != currentDeptCode){//先判断是否是责任科室
			if(!isCloseMeeting){
				App.util.addNewMainTab('/module/biomedical/MemberInfoForm.html?id='+patientId,'会员基本信息');
			}else{
				alert("咨询已结束，不能再查看会员入会信息");
			}
		}else{
			App.util.addNewMainTab('/module/biomedical/MemberInfoForm.html?id='+patientId,'会员基本信息');
		}	
	},
	HealthRecord:function(){//健康记录
		if(oldDeptCode != currentDeptCode){//先判断是否是责任科室
			if(!isCloseMeeting){
				App.util.addNewMainTab('/module/patientanddoctoroperator/doctorLookHealthRecord.html?PID='+patientId,'会员健康记录列表');
			}else{
				alert("咨询已结束，不能再查看会员健康记录");
			}
		}else{
			App.util.addNewMainTab('/module/patientanddoctoroperator/doctorLookHealthRecord.html?PID='+patientId,'会员健康记录列表');
		}		
	},
	ConsultRecord:function(){//咨询记录
		if(oldDeptCode != currentDeptCode){//先判断是否是责任科室
			if(!isCloseMeeting){
				App.util.addNewMainTab('/module/patientanddoctoroperator/doctorLookpatientConsultingList.html?PID='+patientId,'会员咨询记录列表');
			}else{
				alert("咨询已结束，不能再查看会员咨询记录");
			}
		}else{
			App.util.addNewMainTab('/module/patientanddoctoroperator/doctorLookpatientConsultingList.html?PID='+patientId,'会员咨询记录列表');
		}		
	},
	LisResult:function(){//LIS
		if(oldDeptCode != currentDeptCode){//先判断是否是责任科室
			if(!isCloseMeeting){
				window.open('../InHospitalCase/Liver/CheckReport/combinationList.html?patientId='+patientId+'&KID=0');
			}else{
				alert("咨询已结束，不能再查看会员临床检验检查结果");
			}	
		}else{
			window.open('../InHospitalCase/Liver/CheckReport/combinationList.html?patientId='+patientId+'&KID=0');
		}		
	}
	,
	BChaoResult:function(){//B超
		if(oldDeptCode != currentDeptCode){//先判断是否是责任科室
			if(!isCloseMeeting){
			    App.util.addNewMainTab('/module/checkreport/pacs.html?id='+ hisPId+'&look=1','放射影像检查');
//			      + '&no=' + patient.patientNo + "&name=" + patient.patientName
			}else{
				alert("咨询已结束，不能再查看会员放射影像结果");
			}
		}else{
			 App.util.addNewMainTab('/module/checkreport/pacs.html?id='+ hisPId+'&look=1','放射影像检查');
		}		
	},
	PACSResult:function(){
		if(oldDeptCode != currentDeptCode){//先判断是否是责任科室
			if(!isCloseMeeting){
				 App.util.addNewMainTab('/module/checkreport/maidi.html?id='+ hisPId+'&look=1','综合影像检查');
			}else{
				alert("咨询已结束，不能再查看会员综合影像结果");
			}
		}else{
			 App.util.addNewMainTab('/module/checkreport/maidi.html?id='+ hisPId+'&look=1','综合影像检查');
		}		
	},
	InHspResult:function(){
		if(oldDeptCode != currentDeptCode){//先判断是否是责任科室
			if(!isCloseMeeting){
				App.util.addNewMainTab('/module/biomedical/member/ctx/CaseListZixun.html?id='+patientId,'入出院记录');
			}else{
				alert("咨询已结束，不能再查看会员入出院记录");
			}
		}else{
			App.util.addNewMainTab('/module/biomedical/member/ctx/CaseListZixun.html?id='+patientId,'入出院记录');
		}		
	},
	outResult:function(){
		if(oldDeptCode != currentDeptCode){//先判断是否是责任科室
			if(!isCloseMeeting){
				App.util.addNewMainTab('/module/biomedical/member/ctx/memberOutCaseList.html?id='+patientId,'门诊病历');
			}else{
				alert("咨询已结束，不能再查看会员门诊病历记录");
			}
		}else{
			App.util.addNewMainTab('/module/biomedical/member/ctx/memberOutCaseList.html?id='+patientId,'门诊病历');
		}	
	}
}

//关闭咨询
function closeMeetting(){
	Ext.Ajax.request({
		url:baseUrl,
		params:{
			method:'updatePatientConsultingStateMeeting',
			pcaId:pcaId
		},
		success:function(response){
			var res = Ext.util.JSON.decode(response.responseText);
			if(res.success){
				isCloseMeeting = true;
				checkIsNotCloseMeeting = true;
				alert("本次咨询结束成功");
//				if(oldDeptCode != currentDeptCode){
					var shoeMe = document.getElementById("shoeMe");
					var shoeMe1 = document.getElementById("shoeMe1");
					shoeMe.style.display = "none";
					shoeMe1.style.display = "none";
//				}
			}else{
				alert("本次咨询结束失败");
			}
		}
	});
}

function checkIsNotZeRen(){
	var result;
	Ext.Ajax.request({
		url:baseUrl,
		params:{
			method:'checkIsNotZe',
			id:top.USER.id,
			oldDeptCode:oldDeptCode
		},
		sync:true,
		success:function(response){
			var res = Ext.util.JSON.decode(response.responseText);
			if(res.success){
				var data = Ext.util.JSON.decode(res.data);
				result = data; 
			}
		}
	});
	return result;
}
