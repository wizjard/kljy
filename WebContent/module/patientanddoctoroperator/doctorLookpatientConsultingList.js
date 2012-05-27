var PID = App.util.getHtmlParameters('PID');
var baseUrl=App.App_Info.BasePath+'/PatientConsultingAction.do';

Ext.onReady(function(){
	layout();
});

function findAllSYS_HIS_DepartmentHIS(){
	Ext.Ajax.request({
		url:baseUrl,
		params:{
			method:'findAllSYS_HIS_DepartmentHISEntity'
		},
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			var innerOptions='';
			for(var i=0,size=list.length;i<size;i++){
				innerOptions +='<option value='+list[i].deptCode+'>'+list[i].deptName+'</option>';
			}
			$('select[name="deptCode"]').append(innerOptions);
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

var weekFlag = 0;//默认为上午
var currentDate=null;//默认为空，即后台处理为当前时间


function layout(){
	var weekList = [['0','上一周'],['1','下一周']];
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'west',
				border:false,
				split:true,
				id:'west_panel',
				title:'快速定位',
				width:160,
				xtype:'treepanel',
				collapsible:true,
				autoScroll:true,
				containerScroll:true,
				dataUrl:baseUrl+'?method=patientConsulting_treeNodesDoctorOnly&patientId='+PID,
				root:{
					nodeType:'async',
					text:'字典树',
					draggable:false,
					id:-1
				},
				rootVisible:false
			},{
				region:'center',
				xtype:'panel',
				border:false,
				layout:'fit',
				autoScroll:true,
//				tbar:[
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
//								Ext.getCmp("west_panel").getRootNode().reload();
//								var div = document.getElementById("id");
//								div.innerHTML=createMainHtml();
//							}
//						}
//					},
//					{
//						xtype:'tbseparator'
//					},
//					{
//						columnWidth:.2,
//      					labelWidth:100,
//						xtype:"combo",
//						width:100,									
//						id:'seek',
//	                    typeAhead: true,
//	                    store: new Ext.data.SimpleStore({
//			      			fields:['clinicValue','showClincType'],
//			                list: weekList
//		           		}),
//		           		displayField: 'showClincType',
//		      	   		valueField: 'clinicValue',
//	                    mode:'local',
//	                    readOnly: true,
//	                    value:weekList[0][1],
//	                    triggerAction: 'all',
//	                    listeners:{
//			      	   	  select:function(){
//      						weekFlag =this.getValue(); 
//      						if(currentDate == null){
//      							currentDate = currentTime();
//      						}
//      						Ext.getCmp("west_panel").getRootNode().reload();
//      						var div = document.getElementById("id");
//							div.innerHTML=createMainHtml();
//			      	   	  }
//			      	   }
//					}
//				],
				listeners:{
					render:function(){
						var div = document.createElement('div');
						div.innerHTML = '<div id="id">'+createMainHtml()+'</div>';
						this.body.dom.appendChild(div);
					}
				}
			}
		]
	});
	Ext.getCmp('west_panel').loader.on("beforeload", function(treeLoader, node){
		treeLoader.baseParams.currentDate = currentDate;
		treeLoader.baseParams.weekFlag = weekFlag;
	}); 
}

//关闭事件
function backMainPage(){
	window.location.href=App.App_Info.BasePath+'/module/patientanddoctoroperator/patientConsultingList.html';
}

function createMainHtml(){
	var mainHTML = '<img style="left:0;top:0;width:50px;height:67px;position:absolute;display:block;" src="../../PUBLIC/images/youanLogo.jpg"/><div style="left:50px;top:28px;width:80%;font-size:18px;font-weight:bold;position:absolute;">首都医科大学附属北京佑安医院</div>'
	+'<div style="left:50px;top:50px;width:80%;font-size:11px;font-weight:bold;position:absolute;">Beijing YouAn Hospital,Capital Medical University</div>'
	+'<div style="padding-top:90px"></div>';
	Ext.Ajax.request({
		url:baseUrl,
			params:{
				method:'findAllPatientConsultingByPatientIdDoctorOnly',
				pcId:null,
				currentDate:currentDate,
				weekFlag:weekFlag,
				patientId:PID
			},
			sync:true,
			success:function(response){
				var data = Ext.util.JSON.decode(response.responseText);
				if(data.success){
					var list = Ext.util.JSON.decode(data.data);
						if(list.length ==0){
							mainHTML +="暂无历史记录";
						}else{
							for(var i=0,size=list.length;i<size;i++){
							var imagePath="";
							var filePath="";
							
//						if(list[i].uploadImage != null&&list[i].uploadImage !="" ){	
//							if(list[i].uploadImage.lastIndexOf("image") > 0){
//								imagePath +='<tr><td colspan="3" style="background-color: #f5f5f5;"><font size="2">上传图片<font></td></tr>'
//								+'<tr><td colspan="3" style="padding-left:44px"><div style="width:535px;font-size:12px;">'+list[i].uploadImage.replace("http://localhost:8080/TCMP/","")+'</div></td></tr>';
//							}else{
//								imagePath+='<tr><td colspan="3"></td></tr>';
//							}
//						}
							
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
								spiritStatus+='<tr><td colspan="3" style="background-color: #f5f5f5;"><font size="2">健康情况</font></td></tr>'
								+'<tr><td colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].spiritStatus+'</textarea></div></td></tr>';
							}else{
								spiritStatus+='<tr><td colspan="3"></td></tr>';
							}
						
							var treatmentStatus = "";
							if(list[i].treatmentStatus != ""){
								treatmentStatus +='<tr><td colspan="3" style="background-color: #f5f5f5;"><font size="2">治疗情况<font></td></tr>'
								+'<tr><td colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].treatmentStatus+'</textarea></div></td></tr>';
							}else{
								treatmentStatus+='<tr><td colspan="3"></td></tr>';
							}
						
							var improveStatus ="";
							if(list[i].improveStatus != ""){
								improveStatus+='<tr><td colspan="3" style="background-color: #f5f5f5;"><font size="2">病情转归<font></td></tr>'
								+'<tr><td colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].improveStatus+'</textarea></div></td></tr>';
							}else{
								improveStatus+='<tr><td colspan="3"></td></tr>';
							}
						
							mainHTML+='<div style="padding-left:50px" id="div_'+list[i].consultingCount+'"><table width="600" border=1 cellSpacing=0 borderColorDark=#ffffff cellPadding=2><tr>'
								+'<td width="200" class="tzi"><b>日期：<span>'+list[i].consultingDate+'</span></b></td>'
								+'<td class="t1" widht="200" colspan="2"><div align="center"><font size="3" >&nbsp;&nbsp;&nbsp;&nbsp;<B>我的咨询</B>&nbsp;&nbsp;&nbsp;&nbsp;</font></div></td>'
								+'<td class="t1" width="200"><div align="center"><b>第<font color="red" name="couZ">'+(list[i].consultingCount)+'</font>次咨询记录</b></div></td>'
								+'</tr><tr><td colspan="4"><font size="2">目前状况</font></td></tr></table><table width="600" border=1 cellSpacing=0 borderColorDark=#ffffff cellPadding=2>'
								+'<tr>'
								+'<td style="background-color:#f5f5f5;padding-left:50px" width="200"><div style="font-size:12px;">精神状态：'
								+'<span style="font-size:12px;">'+list[i].healthStatus+'</span></div></td>'
								+'<td style="background-color: #f5f5f5;" width="200"><div style="font-size:12px;align:left">食量：'
								+'<span style="font-size:12px;">'+list[i].diet+'</span></div></td>'
								+'<td style="background-color: #f5f5f5;" width="200"><div style="font-size:12px;align:left">睡眠：'
								+'<span style="font-size:12px;">'+list[i].sleep+'</span></div></td>'
								+'</tr><tr>'
								+'<td style="background-color: #f5f5f5;padding-left:50px" width="200" ><div style="font-size:12px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;小便：'
								+'<span style="font-size:12px;">'+list[i].piss+'</span></div></td>'
								+'<td style="background-color: #f5f5f5" width="200"><div style="font-size:12px;">大便：'
								+'<span style="font-size:12px;">'+list[i].defecate+'</span></div></td>'
								+'<td style="background-color: #f5f5f5" width="200"><div style="font-size:12px;">体重：'
								+'<span style="font-size:12px;">'+list[i].weight+'</span></div></td>'
								+'</tr>'
								+spiritStatus
								+treatmentStatus
								+improveStatus
								+'<tr><td colspan="3" style="background-color: #f5f5f5;"><font size="2">咨询问题<font></td></tr>'
								+'<tr><td colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;color:red"><textarea style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].mainProblem+'</textarea></div></td></tr>'
								+'<tr><td colspan="3"></td></tr>'
								+filePath
								+imagePath
								+'</table><table width="600" border=1 cellSpacing=0 borderColorDark=#ffffff cellPadding=2>'
								+'<tr>'
								+'<td style="background-color: #f5f5f5;" colspan="2"><font size="2" color="red">责任科室：&nbsp;</font>'
								+'<span style="font-size:12px;color:#00688B">'+list[i].nameAndGrounp+'</span></td>'
								+'</tr>'
								+'<tr><td colspan="2"><span style="padding-left:500px"><a href="###" style="font-size:12;color:#00688B;text-decoration:none"'
								+'onclick="showMessage('+list[i].consultingCount+',this,'+list[i].id+')">收起回复(<span id="countAll'+list[i].consultingCount+'">'+list[i].count+'</span>条)</a></span>'
//								+'<span style="padding-left:80px"><a href="###" style="font-size:12;color:#00688B;text-decoration:none"'
//								+'onclick="flushMessage('+list[i].consultingCount+','+list[i].id+')">刷新回复列表</a></span>'
								+'<td></tr>'
								+'<tr><td colspan="2" id="message'+list[i].consultingCount+'">'
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
												+'</td><td><table width="545" style="background-color: #D1EEEE;border-bottom:solid #AAAAAA 1px">'
												+'<tr><td style="font-size:15px;color:#00688B" width=75>&nbsp;'+list[i].drAndpqName+'</td><td width=455 style="font-size:12px;">'+list[i].drAndpqDate+'</td></tr>'
												+'<tr><td colspan="2" style="font-size:12px"><textarea name="message" style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].drAndpqContent+'</textarea></td></tr>'
												+'</table></td></tr></table>';
											}
										}
									});
								
								mainHTML+='</td></tr>'
								//+'<tr><td colspan="2"><textarea name="message" style="width:470px;" class="textarea"></textarea>'
								//+'<button class="sign" style="height:26px" onclick="submitSaveData('+list[i].consultingCount+',this,'+list[i].id+')">回复</button>'
								//+'<button class="sign" style="height:26px;width:80px" onclick="flushMessage('+list[i].consultingCount+','+list[i].id+')">最新消息刷新</button>'
								//+'</td></tr>'
								+'<tr><td colspan=2><hr color="red" noshade size="1"></td></tr>'
								+'</table></div>';
						}
					}
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
						+'</td><td><table width="545" style="background-color: #D1EEEE;border-bottom:solid #AAAAAA 1px">'
						+'<tr><td style="font-size:15px;color:#00688B" width=75>&nbsp;'+list[i].drAndpqName+'</td><td width=455 style="font-size:12px;">'+list[i].drAndpqDate+'</td></tr>'
						+'<tr><td colspan="2" style="font-size:12px"><textarea name="message" style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].drAndpqContent+'</textarea></td></tr>'
						+'</table></td></tr></table>';
					}
				}
			});
		messageThis.innerHTML = showAllMessage;
		_this.innerHTML = "收起"+_this.innerHTML.replace("打开","");;
	}
}

function submitSaveData(num,_this,pcid){
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
				content:prevTextarea
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
						+'<table width="545" style="background-color: #D1EEEE;border-bottom:solid #AAAAAA 1px">'
						+'<tr><td style="font-size:15px;color:#00688B" width=75>&nbsp;'+obj.drAndpqName+'</td><td width=455 style="font-size:12px;">'+obj.drAndpqDate+'</td></tr>'
						+'<tr><td colspan="2" style="font-size:12px"><textarea name="message" readonly style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1">'+obj.drAndpqContent+'</textarea></td></tr>'
						+'</table></td></tr></table>';
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

var allId =null;
function scrollTo(_id){
	if(allId != null){
		var currentDiv = document.getElementById("div_"+allId);
		currentDiv.style.width=600 ;
		currentDiv.style.backgroundColor = "#FFFFFF";
	}
	var currentDiv = document.getElementById("div_"+_id);
	currentDiv.style.width=600 ;
	currentDiv.style.backgroundColor = "#CDCDB4";
	currentDiv.focus();
	allId=_id;
}

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
