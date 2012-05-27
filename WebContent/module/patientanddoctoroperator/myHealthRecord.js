var baseUrl=App.App_Info.BasePath+'/PatientHealthRecordAction.do';
var doctorBaseUtil = App.App_Info.BasePath+'/DoctorRoundsRecordAction.do';
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
				region:'west',
				border:false,
				layout:'border',
				width:200,
				items:[
					{
						region:'north',
						id:'north_panel',
						title:'医生查房快速定位',
						width:300,
						height:230,
						xtype:'treepanel',
						autoScroll:true,
						containerScroll:true,
						dataUrl:doctorBaseUtil+'?method=doctorHealthRecord_treeNodes',
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
						title:'病人健康记录快速定位',
						width:230,
						xtype:'treepanel',
						autoScroll:true,
						containerScroll:true,
						dataUrl:baseUrl+'?method=patientHealthRecord_treeNodes',
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
						text:'新增健康记录',handler:function(){
							var div = document.getElementById("patientProblem");
							var divIn = document.createElement("div");
							divIn.innerHTML = createHTML();
							div.appendChild(divIn);
							div.style.width=600;
							div.focus();
						}
					}
					, 
					'-', {
			            text: '录入化验检查结果',
			            handler: function(){
			               if(top.USER != undefined){
			               		window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+patientId+'&doctorName='+top.USER.name+'&doctorOrPatient=1', '', 'width=240px;');
			               }else{
			               	    if(window.confirm("您录入的化验检查结果将直接影响到医生对您病情的判断，为保证医生能够做出正确的判断，请务必认真核对小数点的位置、单位及参考值范围等，确保录入内容真实可靠。")){
			               	    	window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+top.Member.patientId+'&patientName='+top.Member.name, '', 'width=240px;');
			               	    }
			               }
			               
			            }		
					}
//					,
//					{
//						xtype:'label',
//						text:'搜索：'
//					}
//					,
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
//						 }
//						}
//						,
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
//						}
					],
				listeners:{
					render:function(){
						var div = document.createElement("div");
						div.innerHTML ='<div id="id">'+createMainHtml()+'</div>';
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
}

//关闭事件
function backMainPage(){
	window.location.href=App.App_Info.BasePath+'/module/patientanddoctoroperator/myHealthRecord.html';
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
		+'</table>'
		+'<div style="margin:15px;margin-left:50px;width:570px;font-size:18px;font-weight:bold;text-align:center;">健康记录/网上查房记录</div>';
	Ext.Ajax.request({
		url:baseUrl,
			params:{
				method:'findPatientHealthRecordByPatientId',
				currentDate:currentDate,
				weekFlag:weekFlag
			},
			sync:true,
			success:function(response){
				var list = Ext.util.JSON.decode(response.responseText);
				if(list.success){
					var data = Ext.util.JSON.decode(list.data);
					if(data != undefined){
						var k = 0;//病人健康记录
						var z = 0; //医生书写查房记录
						for(var i=0,size=data.length;i<size;i++){
							if(data[i].entityName =="PatientHealthRecord"){
								var imagePath="";
								var filePath="";
							/* if(data[i].uploadImage != null&&data[i].uploadImage !="" ){
								if(data[i].uploadImage.lastIndexOf("image") > 0){
									imagePath +='<tr><td class="titletd" colspan="3" style="background-color: #f5f5f5; padding:3px;"><font size="2">上传图片<font></td></tr>'
									+'<tr><td class="contenttd" colspan="3" style="padding-left:44px"><div style="width:535px;font-size:12px; padding:5px;">'+data[i].uploadImage.replace("http://localhost:8080/TCMP/","")+'</div></td></tr>';
								}else{
									//imagePath+='<tr><td colspan="3"></td></tr>';
								}
							} */
						
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
								mainHTML+='<div class="ablock" style="margin-left:50px; width:600px;" id="patientDiv_'+(k+1)+'"><table width="600" border=0 cellspacing=0 borderColorDark=#ffffff cellpadding=0><tr>'
								+'<td width="200" class="title" valign="bottom"><b><span style="font-size:12px;">日期：'+data[i].writeRecordDate+'</span></b></td>'
								+'<td class="title" widht="200" colspan="2" valign="bottom"><div align="center"><font size="3" >我的健康记录</font></div></td>'
								+'<td class="title" width="200" valign="bottom"><div align="center" style="font-size:12px;"><b>第<font color="red" name="couZ">'+(k+1)+'</font>次健康记录</b></div></td>'
								+'</tr><tr><td class="titletd" style="background-color: #f5f5f5; padding:3px;" colspan="4"><font size="2">一般情况</font></td></tr></table><table width="600" border=0 cellspacing=0 borderColorDark=#ffffff cellpadding=0>'
								+'<tr>'
								+'<td class="smalltd" style="padding-left:50px" width="200"><div style="font-size:12px;">精神状态：'
								+'<span style="font-size:12px;">'+data[i].healthStatus+'</span></div></td>'
								+'<td class="smalltd" style=" ;" width="200"><div style="font-size:12px;align:left">食量：'
								+'<span style="font-size:12px;">'+data[i].diet+'</span></div></td>'
								+'<td class="smalltd" style=" ;" width="200"><div style="font-size:12px;align:left">睡眠：'
								+'<span style="font-size:12px;">'+data[i].sleep+'</span></div></td>'
								+'</tr><tr>'
								+'<td class="smalltd" style="padding-left:50px" width="200" ><div style="font-size:12px;">小　　便：'
								+'<span style="font-size:12px;">'+data[i].piss+'</span></div></td>'
								+'<td class="smalltd" class="smalltd"   width="200"><div style="font-size:12px;">大便：'
								+'<span style="font-size:12px;">'+data[i].defecate+'</span></div></td>'
								+'<td class="smalltd" width="200"><div style="font-size:12px;">体重：'
								+'<span style="font-size:12px;">'+data[i].weight+'</span></div></td>'
								+'</tr>'
								+'<tr><td class="titletd" colspan="3" style="background-color: #f5f5f5; padding:3px;"><font size="2">异常表现</font></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea name="spiritStatus" style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+data[i].spiritStatus+'</textarea></div></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="background-color: #f5f5f5; padding:3px;"><font size="2">用药情况<font></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea name="spiritStatus" style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+data[i].treatmentStatus+'</textarea></div></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="background-color: #f5f5f5; padding:3px;"><font size="2">治疗效果<font></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea name="spiritStatus" style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+data[i].improveStatus+'</textarea></div></td></tr>'
								//+'<tr><td colspan="3"></td></tr>'
								+filePath
								+imagePath
								+'</table><table width="600" border=0 cellspacing=0 borderColorDark=#ffffff cellpadding=0>'
							    +'<tr>'
									+'<td class="titletd" style="background-color:#f5f5f5; padding:3px;width:450px;padding-top:2px;padding-bottom:8px;font-size:12px;">责任科室：'+data[i].deptName+'</td>'
								
									+'<td class="titletd" style="background-color:#f5f5f5; padding:3px;text-algin:right;padding-top:2px;padding-bottom:8px;"><font size="2">签名：</font><input readonly name="signature" style="width:70px" value="'+data[i].signature+'"/></td>'
								+'</tr>'
								+'</table></div>'
								+'<div>&nbsp;</div>';
								k++;
							}else if(data[i].entityName =="DoctorRoundsRecord"){
								var optionInnerHtml ='';
								if(data[i].roundsTitle =="首次网上查房记录"){
									optionInnerHtml+='<option value="首次网上查房记录" selected>首次网上查房记录</option>'+'<option value="日常网上查房记录">日常网上查房记录</option>'+'<option value="阶段小结">阶段小结</option>';
								}else if(data[i].roundsTitle =="日常网上查房记录"){
									optionInnerHtml+='<option value="首次网上查房记录">首次网上查房记录</option>'+'<option value="日常网上查房记录" selected>日常网上查房记录</option>'+'<option value="阶段小结">阶段小结</option>';
								}else if(data[i].roundsTitle =="阶段小结"){
									optionInnerHtml+='<option value="首次网上查房记录">首次网上查房记录</option>'+'<option value="日常网上查房记录">日常网上查房记录</option>'+'<option value="阶段小结" selected>阶段小结</option>';
								}
								mainHTML+='<div class="ablock" id="doctor_'+(z+1)+'" style="margin-left:50px; width:600px;">'
								+'<input type="hidden" name="id" value="">'
								+'<input type="hidden" name="roundsDate" value="">'
								+'<table width="600" border="0" cellspacing="0" cellpadding=0 style="background-color:#fff7e6">'
								+'<tr>'
								+'<td class="edittzi" width="200"><b>日期：</b><span name="currentTime"><b>'+data[i].roundsDate+'</b></span></td>'
								+'<td class="editt1" ><div align="center"><font size="3" ><b><select name="roundsTitle_'+data[i].id+'" disabled>'
								+optionInnerHtml
								+'</select></b></font></div></td>'
								+'<td class="editt1" width="150"><div align="center"><b>第<font color="red">'+(z+1)+'</font>次查房记录<B></div></td>'
								+'</tr>'
								+'<tr><td colspan="3" style="padding-left:20px;"><font size="2">内容</font></td></tr>'
								+'<tr><td colspan="3" style="padding-left:20px;"><textarea name="roundsContent" style="width:560px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+data[i].roundsContent+'</textarea></td></tr>'
								+'</table><table width="600" border=0 cellspacing=0  cellpadding=0 style="background-color:#fff7e6;">'
								+'<tr>'
								+'<td style="text-algin:right;width:500px;padding-left:450px;padding-bottom:5px"><font size="2">签名：</font><input readonly name="doctorName" style="width:60px" value="'+data[i].doctorName+'" readOnly/></td>'
								+'</tr>'
								+'<tr><td colspan="3"><font color="red" size="2">本次查房意见仅供参考，如遇病情突然变化或其他特殊情况请您立即到医院就诊。</font></td></tr>'
								+'</table>'
								+'</form></div>'
								+'<div>&nbsp;</div>';
								z++;
							}
						}
					}
				}else{
					alert("登录超时，请重新登录！");
					window.top.location.href =App.App_Info.BasePath+"/module/biomedical/member/loginMember.jsp";
				}
			}
		});
	return mainHTML +'<div id="patientProblem"></div>';
}

var allId =null;
function scrollTo(_id){
	if(allId != null){
		var currentDiv = document.getElementById("patientDiv_"+allId);
		currentDiv.style.width=600 ;
		currentDiv.style.border="solid 1px #dddddd";
	}
	var currentDiv = document.getElementById("patientDiv_"+_id);
	currentDiv.style.width=600 ;
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
