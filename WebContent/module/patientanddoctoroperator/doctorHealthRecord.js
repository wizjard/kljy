var baseUrl=App.App_Info.BasePath+'/PatientHealthRecordAction.do';
var doctorBaseUtil = App.App_Info.BasePath+'/DoctorRoundsRecordAction.do';
var patientId = App.util.getHtmlParameters('id'); 
var patientName= App.util.getHtmlParameters('patientName');
patientName = decodeURI(patientName);

var mobilePhone = App.util.getHtmlParameters('mobilePhone');  //获得某个会员的手机号
mobilePhone = decodeURI(mobilePhone);

var deptName = App.util.getHtmlParameters('deptName');
var hisPId =App.util.getHtmlParameters('hisPId');//HIS中病人ID
//liugang 2011-08-06 start
var isnotUpdate = App.util.getHtmlParameters('isnotUpdate');

//获得医生所在责任科室和小组参数  by cheng jiangyu 2011-9-25
var currentDeptName= App.util.getHtmlParameters('currentDeptName');
currentDeptName = decodeURI(currentDeptName);
var grounpName= App.util.getHtmlParameters('grounpName');
grounpName = decodeURI(grounpName);


//liugang 2011-08-06 end
Ext.onReady(function(){
	layout();
});


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

var weekFlag = 0;//默认为上午
var currentDate=null;//默认为空，即后台处理为当前时间

function layout(){
//	var weekList = [['0','上一周'],['1','下一周']];
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'north',
				border:false,
				height:23,
				html:'<div id="PatSimInfo-DIV"></div>'
			},
			{
			
				region:'west',
				border:false,
				layout:'border',
				width:200,
				items:[
					{
						region:'north',
						id:'north_panel',
						title:'医生查房快速定位',
						autoScroll:true,
						width:300,
						height:230,
						xtype:'treepanel',
						
						containerScroll:true,
						loader:new Ext.tree.TreeLoader({dataUrl:doctorBaseUtil,baseParams:{
							method:'doctorRoundsRecord_treeNodes',
							patientId:patientId,
							doctorId:top.USER.id
						}}),
						root:{
							nodeType:'async',
							text:'字典树',
							draggable:false,
							id:-1
						},
						rootVisible:false
					}
					,
					{
						region:'center',
						id:'south_panel',
						autoScroll:true,
						title:'病人健康记录快速定位',
						width:300,
						height:230,
						xtype:'treepanel',
						
						containerScroll:true,
						loader:new Ext.tree.TreeLoader({dataUrl:baseUrl,baseParams:{
							method:'patientHealthRecord_treeNodesDoctor',
							patientId:patientId,
							doctorId:top.USER.id
						}}),
						root:{
							nodeType:'async',
							text:'字典树',
							draggable:false,
							id:-1
						},
						rootVisible:false
					}
				]
			}
			,
			{
				region:'center',
				xtype:'panel',
				border:false,
				layout:'fit',
				autoScroll:true,
				tbar:[ 
					{
						text:'新增查房记录',handler:function(){
						//liugang 2011-10-09 如果此时处于转科或者转组中，不能进行查房和网上咨询
						
										//liugang 2011-08-06 start	
										if(isnotUpdate != 1){
											var butt = document.getElementById("showButton");
											if(butt == null){
												var divs = document.getElementById("doctorAllList");
												var div = document.createElement("div");
												div.innerHTML = createHTML();
												divs.appendChild(div);
												divs.style.width=600;
												divs.focus();
											}else{
												alert("请先保存上次查房后再新增");
												return ;
											}
										}else{
											alert("此会员已经转科，无需查房");
											return ;
										}	
									//liugang 2011-08-06 end	
									}
							
					},
//					{
//						xtype:'tbseparator'
//					},
//					{
//						xtype:'label',
//						text:'搜索：'
//					},
//					{
//						xtype:'datefield',
//						anchor:'90%',
//						width:120,	
//						name:'receiveDate',
//						id:'receiveDate',
//						emptyText:'时间搜索',
//						listeners:{
//							select:function(){
//								currentDate = this.getValue().format("Y-m-d H:i:s");
//								Ext.getCmp("north_panel").getRootNode().reload();
//								Ext.getCmp("south_panel").getRootNode().reload();
//								var div = document.getElementById("id");
//								div.innerHTML=createMainHtml();
//							}
//						}
//						},
//						{
//							xtype:'tbseparator'
//						},
//						{
//							columnWidth:.2,
//	      					labelWidth:100,
//							xtype:"combo",
//							width:100,									
//							id:'seek',
//		                    typeAhead: true,
//		                    store: new Ext.data.SimpleStore({
//				      			fields:['clinicValue','showClincType'],
//				                data: weekList
//			           		}),
//			           		displayField: 'showClincType',
//			      	   		valueField: 'clinicValue',
//		                    mode:'local',
//		                    readOnly: true,
//		                    value:weekList[0][1],
//		                    triggerAction: 'all',
//		                    listeners:{
//				      	   	  select:function(){
//	      						weekFlag =this.getValue(); 
//	      						if(currentDate == null){
//	      							currentDate = currentTime();
//	      						}
//	      						Ext.getCmp("north_panel").getRootNode().reload();
//								Ext.getCmp("south_panel").getRootNode().reload();
//	      						var div = document.getElementById("id");
//								div.innerHTML=createMainHtml();
//				      	   	  }
//				      	   }
//						},
						/* {
							xtype:'tbseparator'
						},*/'-', {
				            text: '发送短信',
				            handler: function(){
								 var pid=patientId;
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
									 			if(mobilePhone==''||mobilePhone==null||(mobilePhone!=''&&!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(mobilePhone)))) { //判断如果会员手机号不为空并且不符合手机号格式，要提示
			    					 			         alert('会员'+patientName+'手机号码为空或格式不正确');
			    					 			         return;
			    					 			       }
								 			
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
				        },'-',{
							text:'综合查询',
							menu:[
								{
									text:'放射影像结果',
									handler:function(){
												App.util.addNewMainTab('/module/checkreport/pacs.html?id='+ hisPId+'&look=1','放射影像结果');
									}
								},{
									text:'综合影像结果',
									handler:function(){
												App.util.addNewMainTab('/module/checkreport/maidi.html?id='+ hisPId+'&look=1','综合影像结果');
									}
								}
								,{
									text:'化验检查结果',
									handler:function(){
										window.open('../InHospitalCase/Liver/CheckReport/combinationList.html?patientId='+patientId+'&KID=0');	
									}
								}
							]
						}
						,'-',{
							text:'查看会员原科室健康记录',handler:function(){
								if(isnotUpdate != 1){
									alert("该会员没有原科室健康记录！");
									return ;
								}
								App.util.addNewMainTab('/module/patientanddoctoroperator/patientHistoryHealthList.html?id='+patientId,'查看会员原科室健康记录');
							}
						}
						,'-',{
							text:'返回',handler:function(){
								window.location.href =App.App_Info.BasePath+'/module/patientanddoctoroperator/doctorSearchHealthRecordList.html';
							}
						}
					],
				listeners:{
					render:function(){
						var div=document.createElement('div');
						div.innerHTML = '<div id="id">'+createMainHtml()+'</div>'
						this.body.dom.appendChild(div);
					}
				}
			}
		]
	});
	Ext.getCmp('north_panel').loader.on("beforeload", function(treeLoader, node){
		treeLoader.baseParams.currentDate = currentDate;
		treeLoader.baseParams.weekFlag = weekFlag;
	});
	Ext.getCmp('south_panel').loader.on("beforeload", function(treeLoader, node){
		treeLoader.baseParams.currentDate = currentDate;
		treeLoader.baseParams.weekFlag = weekFlag;
	});
	loadPatSimInfo();
}

//关闭事件
function backMainPage(){
	window.location.href = App.App_Info.BasePath+'/module/patientanddoctoroperator/doctorHealthRecord.html';
}

//下载事件
function download(fileName){
	document.getElementById("fileName").value = fileName;
	document.getElementById("downloadForm").submit();
}

function createMainHtml(){
	var mainHTML = '<img style="left:0;top:0;width:50px;height:67px;position:absolute;display:block;" src="../../PUBLIC/images/youanLogo.jpg"/><div style="left:50px;top:28px;width:80%;font-size:18px;font-weight:bold;position:absolute;">首都医科大学附属北京佑安医院</div>'
	+'<div style="left:50px;top:50px;width:80%;font-size:11px;font-weight:bold;position:absolute;">Beijing YouAn Hospital,Capital Medical University</div>'
	+'<div style="left:295px;top:67px;width:100%;font-size:18px;font-weight:bold;position:absolute;">网上查房记录</div>'
	+'<div style="padding-top:90px"></div>';
	Ext.Ajax.request({
		url:baseUrl,
			params:{
				method:'findPatientHealthRecordByPatientIdDoctor',
				patientId:patientId,
				currentDate:currentDate,
				weekFlag:weekFlag,
//				deptName:deptName,
				doctorId:top.USER.id
			},
			sync:true,
			success:function(response){
				var list = Ext.util.JSON.decode(response.responseText);
				if(list.success){
					var data = Ext.util.JSON.decode(list.data);
					if(data != undefined){
						var k = 0;//病人健康记录
						var z = 0;//医生书写查房记录
						for(var i=0;i<data.length;i++){
							if(data[i].entityName =="PatientHealthRecord"){
								var imagePath="";
								var filePath="";
//							if(data[i].uploadImage != null&&data[i].uploadImage !="" ){	
//								if(data[i].uploadImage.lastIndexOf("image") > 0){
//									imagePath +='<tr><td colspan="3" style="background-color: #f5f5f5;"><font size="2">上传图片<font></td></tr>'
//									+'<tr><td colspan="3" style="padding-left:44px"><div style="width:535px;font-size:12px;">'+data[i].uploadImage.replace("http://localhost:8080/TCMP/","")+'</div></td></tr>';
//								}else{
//									//imagePath+='<tr><td colspan="3"></td></tr>';
//								}
//							}
							
							if(data[i].uploadFile !="" && data[i].uploadFile != null){	
								var uploadFileArr = data[i].uploadFile.split("\*");
								if(data[i].uploadFile.toLocaleLowerCase().indexOf(".rar") > 0 || data[i].uploadFile.toLocaleLowerCase().indexOf(".zip") > 0
										|| data[i].uploadFile.toLocaleLowerCase().indexOf(".doc") > 0|| data[i].uploadFile.toLocaleLowerCase().indexOf(".xls") > 0|| data[i].uploadFile.toLocaleLowerCase().indexOf(".pdf") > 0
										|| data[i].uploadFile.toLocaleLowerCase().indexOf(".xlsx") > 0|| data[i].uploadFile.toLocaleLowerCase().indexOf(".jpg") > 0|| data[i].uploadFile.toLocaleLowerCase().indexOf(".gif") > 0
										|| data[i].uploadFile.toLocaleLowerCase().indexOf(".bmp") > 0|| data[i].uploadFile.toLocaleLowerCase().indexOf(".jpeg") > 0||data[i].uploadFile.toLocaleLowerCase().indexOf(".png") > 0||data[i].uploadFile.toLocaleLowerCase().indexOf(".txt") > 0
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
								
							/*	if(data[i].uploadFile.toLocaleLowerCase().indexOf(".rar")>0 || data[i].uploadFile.toLocaleLowerCase().indexOf(".zip")>0){
									filePath+='<tr><td colspan="3" style="background-color: #f5f5f5;"><font size="2">上传资料<font></td></tr>'		
									+'<tr><td colspan="3" style="padding-left:44px"><div style="width:535px;font-size:12px;">'+data[i].uploadFile+'</div></td></tr>';
								}else{
									filePath+='<tr><td colspan="3"></td></tr>';
								} */
								
								
								mainHTML+='<div class="ablock" style="margin-left:50px" id="patientDiv_'+(k+1)+'"><table width="600" border=0 cellSpacing="0" borderColorDark="#ffffff" cellPadding="2"><tr>'
								+'<td class="title" width="200" class="tzi"><b>日期：<span name="writeRecordDate">'+data[i].writeRecordDate+'</b></span></td>'
								+'<td class="title" widht="200" colspan="2"><div align="center">&nbsp;&nbsp;&nbsp;&nbsp;<B><font color="red" size="3">'+data[i].signature+'健康记录</font></B>&nbsp;&nbsp;&nbsp;&nbsp;</div></td>'
								+'<td class="title" width="200"><div align="center"><b>第<font color="red" name="couZ">'+(k+1)+'</font>次健康记录</b></div></td>'
								+'</tr><tr><td class="titletd" colspan="4"><font size="2">一般情况</font></td></tr></table>'
								+'<table width="600" border=0 cellSpacing="0" borderColorDark="#ffffff" cellPadding="2">'
								+'<tr>'
								+'<td class="smalltd" style="padding-left:50px" width="200"><div style="font-size:12px;">精神状态：'
								+'<span style="font-size:12px;">'+data[i].healthStatus+'</span></div></td>'
								+'<td class="smalltd" width="200"><div style="font-size:12px;align:left">食量：'
								+'<span style="font-size:12px;">'+data[i].diet+'</span></div></td>'
								+'<td class="smalltd" width="200"><div style="font-size:12px;align:left">睡眠：'
								+'<span style="font-size:12px;">'+data[i].sleep+'</span></div></td>'
								+'</tr><tr>'
								+'<td class="smalltd" style="padding-left:50px" width="200" ><div style="font-size:12px;">小　　便：'
								+'<span style="font-size:12px;">'+data[i].piss+'</span></div></td>'
								+'<td class="smalltd" width="200"><div style="font-size:12px;">大便：'
								+'<span style="font-size:12px;">'+data[i].defecate+'</span></div></td>'
								+'<td class="smalltd" width="200"><div style="font-size:12px;">体重：'
								+'<span style="font-size:12px;">'+data[i].weight+'</span></div></td>'
								+'</tr>'
								+'<tr><td class="titletd" colspan="3"><font size="2">异常表现</font></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+data[i].spiritStatus+'</textarea></div></td></tr>'
								+'<tr><td class="titletd" colspan="3"><font size="2">用药情况</font></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+data[i].treatmentStatus+'</textarea></div></td></tr>'
								+'<tr><td class="titletd" colspan="3"><font size="2">治疗效果</font></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+data[i].improveStatus+'</textarea></div></td></tr>'
								+filePath
								+imagePath
								+'</table>'
								+'<table width="600" border="0" cellSpacing="0" borderColorDark="#ffffff" cellPadding="2">'
							    +'<tr>'
									+'<td class="titletd" style="width:70px"><font size="2">责任科室：</font></td>'
									+'<td class="titletd" style="text-align:left"><span style="font-size:12px;">'+data[i].deptName+'</span></td>'
								+'</tr>'
								+'</table><table width="600" border="0" cellSpacing="0" borderColorDark="#ffffff" cellPadding="2">'
								+'<tr>'
									+'<td class="titletd" style="text-algin:right;width:600px;padding-left:450px"><font size="2">签名：</font><input readonly name="signature" style="width:70px" value="'+data[i].signature+'"/></td>'
								+'</tr>'
								+'</table></div><div>&nbsp;</div>';
								k++;
							}else{
								var optionInnerHtml ='';
								if(data[i].roundsTitle =="首次网上查房记录"){
									optionInnerHtml+='<option value="首次网上查房记录" selected>首次网上查房记录</option>'+'<option value="日常网上查房记录">日常网上查房记录</option>'+'<option value="阶段小结">阶段小结</option>';
								}else if(data[i].roundsTitle =="日常网上查房记录"){
									optionInnerHtml+='<option value="首次网上查房记录">首次网上查房记录</option>'+'<option value="日常网上查房记录" selected>日常网上查房记录</option>'+'<option value="阶段小结">阶段小结</option>';
								}else if(data[i].roundsTitle =="阶段小结"){
									optionInnerHtml+='<option value="首次网上查房记录">首次网上查房记录</option>'+'<option value="日常网上查房记录">日常网上查房记录</option>'+'<option value="阶段小结" selected>阶段小结</option>';
								}
								mainHTML+='<div class="ablock" id="doctor_'+(z+1)+'" style="margin-left:50px;"><form id="doctorForm_'+(z+1)+'">'
									+'<input type="hidden" name="id" value="">'
									+'<input type="hidden" name="roundsDate" value="">'
									+'<table width="600" border="0" cellSpacing="0" cellPadding="2" style="background-color:#fff7e6">'
									+'<tr><td class="title" colspan=4 style="color:red">查房记录</td></tr>'
									+'<tr>'
									+'<td class="edittzi" width="200"><b>日期：</b><span name="currentTime"><b>'+data[i].roundsDate+'</b></span></td>'
									+'<td class="editt1" ><div align="center"><select name="roundsTitle_'+data[i].id+'" disabled>'
									+optionInnerHtml
									+'</select></div></td>'
									+'<td class="editt1" width="150"><div align="center"><b>第<font color="red">'+(z+1)+'</font>次查房记录</b></div></td>'
									+'</tr>'
									+'<tr><td colspan="3" ><font size="2">内容</font></td></tr>'
									+'<tr><td colspan="3" style="padding-left:30px"><textarea name="roundsContent" style="width:560px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+data[i].roundsContent+'</textarea></td></tr>'
									+'</table><table width="600" border="0" cellSpacing="0"  cellPadding="2" style="background-color:#fff7e6;">'
									+'<tr>'
									+'<td style="text-algin:right;width:500px;padding-left:450px"><font size="2">签名：</font><input readonly name="doctorName" style="width:60px" value="'+data[i].doctorName+'" readOnly/><button class="sign" onclick="cancleUserName('+data[i].id+','+(z+1)+')">撤销</button></td>'
									+'</tr>'
									+'<tr><td colspan="3"><font color="red" size="2">本次查房意见仅供参考，如遇病情突然变化或其他特殊情况请您立即到医院就诊。</font></td></tr>'
									+'</table>'
									+'</form></div><div>&nbsp;</div>';
									z++;
							}
					}
					mainHTML+='<div id="doctorAllList"></div>';
				}
			}
		}
	});
	return mainHTML;
}

//病人健康记录快速定位
var allId =null;
function scrollTo(_id){
	if(allId != null){
		var currentDiv = document.getElementById("patientDiv_"+allId);
		currentDiv.style.width=600 ;
		currentDiv.style.border="solid 1px #dddddd";
	}
	var currentDiv = document.getElementById("patientDiv_"+_id);
	currentDiv.style.width=600;
	currentDiv.style.border="2px solid #ff8866";
	currentDiv.focus();
	allId=_id;
}

//医生查房记录快速定位
var doctorAllId = null;
function scrollToDoctor(_id){
	if(doctorAllId != null){
		var currentDiv = document.getElementById("doctor_"+doctorAllId);
		currentDiv.style.border="solid 1px #dddddd";
		currentDiv.style.width=600 ;
	}
	var currentDiv = document.getElementById("doctor_"+_id);
	currentDiv.style.border="2px solid #ff8866";
	currentDiv.style.width=600;
	currentDiv.focus();
	doctorAllId=_id;
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
