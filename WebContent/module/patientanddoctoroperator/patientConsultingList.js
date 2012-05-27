var baseUrl=App.App_Info.BasePath+'/PatientConsultingAction.do';
var deptcodenameList = new Array();


Ext.onReady(function(){
	getAllDeptment();
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
	 var sendDept = Ext.extend(Ext.Button, {
                    text: '申请转科',
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
                                  fieldLabel: '转入科室',
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
										}
									}
                                },
                                {
                                    fieldLabel: '申请理由',
				                    xtype: 'textarea',
				                    height: 80,
				                    allowBlank: true,
				                    anchor: '95%',
				                    name: 'applicationBacuse'
                                }
                                ]
                            },
                            buttons: [{
                                text: '保存',
                                handler: function(){
                                    var values = o.sw.items.get(0).getForm().getValues();
                                    values.applicationDeptCode = deptcode;
                                    values.patientId = top.Member.patientId;
                                    values.oldDeptCode = top.Member.deptCode;
                                    values.oldGrounpId = top.Member.grounpId;
                                    Ext.Ajax.request({
										url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
										params:{
											method:'saveApplicationRecord',
											data:Ext.encode(values)
										},
										success:function(_response){
											var res=Ext.decode(_response.responseText);
											if(res.success){
												alert("申请已提交");
												document.getElementById("stateContent").value="转科中....";
											}else{
												alert("申请未能成功提交");
											}
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
                var sendGrounp = Ext.extend(Ext.Button, {
                    text: '申请转组',
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
                                    fieldLabel: '申请理由',
				                    xtype: 'textarea',
				                    height: 80,
				                    allowBlank: true,
				                    anchor: '95%',
				                    name: 'applicationBacuse'
                                }
                                ]
                            },
                            buttons: [{
                                text: '保存',
                                handler: function(){
                                    var values = o.sw.items.get(0).getForm().getValues();
                                    values.applicationDeptCode = top.Member.deptCode;
                                    values.patientId = top.Member.patientId;
                                    values.oldDeptCode = top.Member.deptCode;
                                    values.oldGrounpId = top.Member.grounpId;
                                    Ext.Ajax.request({
										url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
										params:{
											method:'saveApplicationRecord',
											data:Ext.encode(values)
										},
										success:function(_response){
											var res=Ext.decode(_response.responseText);
											if(res.success){
												alert("申请已提交");
												document.getElementById("stateContent").value="转组中....";
											}else{
												alert("申请未能成功提交");
											}
											
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
				dataUrl:baseUrl+'?method=patientConsulting_treeNodes',
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
				tbar:[
					{
						text:'我要提问',handler:function(){
								var div = document.getElementById("patientC");
								var divc = document.createElement("div");
								divc.innerHTML = createHTML();
								div.appendChild(divc);
								div.style.width=600;
								div.focus();
//								findAllSYS_HIS_DepartmentHIS();
							}
					}
					,
//					{
//						xtype:'tbseparator'
//					}
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
//			                data: weekList
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
//					},
					{
						xtype:'tbseparator'
					},
					/**
					new sendDept({
                        handler: function(){
                        	var stateContent = document.getElementById("stateContent");
                        	if(stateContent.value == ""){
	                            if (!this.sw) {
	                                this.initSw();
	                            }
	                            this.toggleSw();
	                        }else{
	                        	alert(stateContent.value+",请等待...");
	                        }
                        }
                    }),
                    {
						xtype:'tbseparator'
					},
					new sendGrounp({
                        handler: function(){
                        	var stateContent = document.getElementById("stateContent");
                        	if(stateContent.value == ""){
                        		if (!this.sw) {
                               		 this.initSw();
	                            }
	                            this.toggleSw();
                        	}else{
                        		alert(stateContent.value+",请等待...");
                        	}
                        }
                    }),*/
                    {
                    	 xtype: 'textfield',
                    	 id:'stateContent',
                     	 anchor: '95%',
                     	 readOnly:true,
                     	 listeners:{
                     	 	render:function(){
			                     publicCheckState();
                     	 	}
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
				],
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
		+'<div style="margin:15px;margin-left:50px;width:570px;font-size:18px;font-weight:bold;text-align:center;">我的咨询记录</div>';
	Ext.Ajax.request({
		url:baseUrl,
			params:{
				method:'findAllPatientConsultingByPatientId',
				pcId:null,
				currentDate:currentDate,
				weekFlag:weekFlag
			},
			sync:true,
			success:function(response){
				var data = Ext.util.JSON.decode(response.responseText);
				if(data.success){
					var list = Ext.util.JSON.decode(data.data);
					if(list.length ==""){
						mainHTML+="<div style='padding-left:50px'>请添加一条咨询问题</div>";
					}else{
						for(var i=0,size=list.length;i<size;i++){
							var imagePath="";
							var filePath="";
//							if(list[i].uploadImage != null&&list[i].uploadImage !="" ){
//								if(list[i].uploadImage.lastIndexOf("image") > 0){
//									imagePath +='<tr><td class="titletd" colspan="3" style="background-color: #f5f5f5;"><font size="2">上传图片<font></td></tr>'
//									+'<tr><td class="contenttd" colspan="3" style="padding-left:44px"><div style="width:535px;font-size:12px;">'+list[i].uploadImage.replace("http://localhost:8080/TCMP/","")+'</div></td></tr>';
//								}
//							}
							
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
								spiritStatus+='<tr><td class="titletd" colspan="3" style="background-color: #f5f5f5;"><font size="2">异常表现</font></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea name="spiritStatus" style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].spiritStatus+'</textarea></div></td></tr>';
							}else{
								spiritStatus+='<tr><td colspan="3"></td></tr>';
							}
						
							var treatmentStatus = "";
							if(list[i].treatmentStatus != ""){
								treatmentStatus +='<tr><td class="titletd" colspan="3" style="background-color: #f5f5f5;"><font size="2">用药情况<font></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea name="spiritStatus" style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].treatmentStatus+'</textarea></div></td></tr>';
							}else{
								treatmentStatus+='<tr><td colspan="3"></td></tr>';
							}
						
							var improveStatus ="";
							if(list[i].improveStatus != ""){
								improveStatus+='<tr><td class="titletd" colspan="3" style="background-color: #f5f5f5;"><font size="2">治疗效果<font></td></tr>'
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;"><textarea name="spiritStatus" style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].improveStatus+'</textarea></div></td></tr>';
							}else{
								improveStatus+='<tr><td colspan="3"></td></tr>';
							}
						
							mainHTML+='<div class="ablock" id="div_'+list[i].consultingCount+'">'
								+'<table width="600" border=0 cellSpacing=0 borderColorDark=#ffffff cellpadding=0><tr>'
								+'<td class="title" width="300"><div align="center"><b>第<font color="red" name="couZ">'+(list[i].consultingCount)+'</font>次咨询记录</b></div></td>'
								+'<td width="300" class="title"><b>日期：<span>'+list[i].consultingDate+'</span></b></td>'
								+'</tr><tr><td class="titletd" colspan="4"><font size="2">一般情况</font></td></tr></table><table width="600" border=0 cellSpacing=0 borderColorDark=#ffffff cellpadding=0>'
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
								+'<tr><td class="titletd" colspan="3" style="padding-left:55px"><div style="width:535px;font-size:12px;color:red"><textarea name="spiritStatus" style="width:530px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].mainProblem+'</textarea></div></td></tr>'
//								+'<tr><td colspan="3"></td></tr>'
								+filePath
								+imagePath
								+'</table><table width="600" border=0 cellSpacing=0 borderColorDark=#ffffff cellpadding=0>'
								+'<tr>'
								+'<td width="600%" colspan="2" class="titletd"><font size="2" color="red">责任科室：&nbsp;</font>'
								+'<span style="font-size:12px;color:#00688B">'+list[i].nameAndGrounp+'</span></td>'
								+'</tr>'
								+'<tr><td width="600" colspan="2" class="titletd"><div style="width:500px;float:right;padding-right:25px;text-align:right;"><a href="###" style="font-size:12px;color:#00688B;text-decoration:none"'
								+'onclick="showMessage('+list[i].consultingCount+',this,'+list[i].id+')">收起回复(<span id="countAll'+list[i].consultingCount+'">'+list[i].count+'</span>条)</a></div>'
//								+'<span style="padding-left:80px"><a href="###" style="font-size:12;color:#00688B;text-decoration:none"'
//								+'onclick="flushMessage('+list[i].consultingCount+','+list[i].id+')">刷新回复列表</a></span>'
								+'<td></tr>'
								+'<tr><td class="replytd" colspan="2" id="message'+list[i].consultingCount+'">';
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
												+'</td><td><table width="545">'
												+'<tr><td style="font-size:15px;color:#00688B" width=75>&nbsp;'+list[i].drAndpqName+'</td><td width=455 style="font-size:12px;">'+list[i].drAndpqDate+'</td></tr>'
												+'<tr><td colspan="2" style="font-size:12px"><textarea name="message" style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+list[i].drAndpqContent+'</textarea></td></tr>'
												+'</table></td></tr></table>';
											}
										}
									});
								mainHTML+='</td></tr>';
								if(list[i].pcMeetingState != 1){
									mainHTML+='<tr><td class="replytd" style="padding-bottom:8px;padding-top:3px;" colspan="2"><textarea name="message" style="width:462px;overflow-y:visible" onscroll="this.rows++;" rows="1"></textarea>'
									+'<button class="sign" style="height:26px" onclick="submitSaveData('+list[i].consultingCount+',this,'+list[i].id+')">留言</button>'
									+'<button class="sign" style="height:26px;width:80px" onclick="flushMessage('+list[i].consultingCount+','+list[i].id+')">最新消息刷新</button>'
									+'</td></tr>';
								}
								mainHTML+='</table></div><div>&nbsp;</div>';
							}
						}
					}else{
						alert("登录超时，请重新登录！");
						window.top.location.href =App.App_Info.BasePath+"/module/biomedical/member/loginMember.jsp";
					}
				}
			});
		return mainHTML+'<div id="patientC"></div>';
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
				content:prevTextarea,
				date:currentTime()
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
		currentDiv.style.border="solid 1px #dddddd";
	}
	var currentDiv = document.getElementById("div_"+_id);
	currentDiv.style.width=600 ;
	currentDiv.style.border="2px solid #ff8866";
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
//				deptNameOption+="<option value="+list[i].deptCode+">"+list[i].deptName+"</option>";
			}
			deptcode = deptcodenameList[0][0];//科室编码动态改变加载对应的数据
//			$('select[name="deptCode"]').append(deptNameOption);
		}
	});
}


function publicCheckState(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
		params:{
			method:'findCurrentState',
			id:top.Member.patientId
		},
		scope:this,
		success:function(_response){
			var _res=Ext.util.JSON.decode(_response.responseText);
			if(_res.success){
			var data = Ext.util.JSON.decode(_res.data);
			if(data !=null){
				var stateContent = document.getElementById("stateContent");
				if(data.applicationDeptCode == data.oldDeptCode){
					stateContent.value="转组申请中...";
				}else if(data.applicationDeptCode != data.oldDeptCode)
				{
					stateContent.value="转科申请中...";
				}
			}
		}
	}
 });
}