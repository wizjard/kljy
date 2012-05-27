var baseUrl=App.App_Info.BasePath+'/PatientHealthRecordAction.do';
var doctorBaseUtil = App.App_Info.BasePath+'/DoctorRoundsRecordAction.do';
var patientId = App.util.getHtmlParameters('id'); 

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
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'center',
				xtype:'panel',
				border:false,
				layout:'fit',
				autoScroll:true,
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
	+'<div style="left:295px;top:67px;width:100%;font-size:18px;font-weight:bold;position:absolute;">健康记录/网上查房记录</div>'
	+'<div style="padding-top:90px"></div>';
	Ext.Ajax.request({
		url:baseUrl,
			params:{
				method:'findPatientHealthRecordHistory',
				patientId:patientId,
				doctorId:top.USER.id
			},
			sync:true,
			success:function(response){
				var list = Ext.util.JSON.decode(response.responseText);
				if(list.success){
					var data = Ext.util.JSON.decode(list.data);
					if(data != undefined){
						var k = 0; //病人健康记录
						var z = 0; //医生书写查房记录
						for(var i=0;i<data.length;i++){
							if(data[i].entityName =="PatientHealthRecord"){
								var imagePath="";
								var filePath="";
							
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
								
								
								mainHTML+='<div class="ablock" style="margin-left:50px" id="patientDiv_'+(k+1)+'"><table width="600" border=0 cellSpacing="0" borderColorDark="#ffffff" cellPadding="2"><tr>'
								+'<td class="title" width="200" class="tzi"><b>日期：<span name="writeRecordDate">'+data[i].writeRecordDate+'</b></span></td>'
								+'<td class="title" widht="200" colspan="2"><div align="center">&nbsp;&nbsp;&nbsp;&nbsp;<B><font color="red" size="3">'+data[i].signature+'健康记录</font></B>&nbsp;&nbsp;&nbsp;&nbsp;</div></td>'
								+'<td class="title" width="200"><div align="center"><b>第<font color="red" name="couZ">'+(k+1)+'</font>次健康记录</b></div></td>'
								+'</tr><tr><td class="titletd" colspan="4"><font size="2">目前状况</font></td></tr></table>'
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
								+'<tr><td class="titletd" colspan="3"><font size="2">健康情况</font></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+data[i].spiritStatus+'</textarea></div></td></tr>'
								+'<tr><td class="titletd" colspan="3"><font size="2">治疗情况</font></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+data[i].treatmentStatus+'</textarea></div></td></tr>'
								+'<tr><td class="titletd" colspan="3"><font size="2">病情转归</font></td></tr>'
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
									+'<td style="text-algin:right;width:500px;padding-left:450px"><font size="2">签名：</font><input readonly name="doctorName" style="width:60px" value="'+data[i].doctorName+'" readOnly/><button class="sign" onclick="cancleUserName('+data[i].id+','+(z+1)+')">æ¤é</button></td>'
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

//çäººå¥åº·è®°å½å¿«éå®ä½
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

//å»çæ¥æ¿è®°å½å¿«éå®ä½
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
		text:'正在加载病人基本信息......'
	});
}
