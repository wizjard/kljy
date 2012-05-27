var _user=null;
var _KID=App.util.getHtmlParameters('KID');
var _PID=App.util.getHtmlParameters('PID');
var _currentID=null;
Ext.onReady(function(){
	Layoutpage();
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/user.do',
		params:{
			method:'getLoginUserInfo'
		},
		sync:true,
		success:function(_response){
			_user=Ext.decode(_response.responseText);
		}
	});
});
function Layoutpage(){
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'west',
				xtype:'grid',
				id:'grid',
				title:'记录索引',
				split:true,
		        width: 200,
		        minSize: 100,
		        maxSize: 400,
		        collapsible: true,
				border:true,
				autoScroll:true,
				columns:[
					new Ext.grid.RowNumberer(),
					{
						header:'时间',width:100,dataIndex:'time'
					},{
						header:'标题',width:173,dataIndex:'title'
					}
				],
				store:new Ext.data.Store({
					proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do?method=DailyCourseRec_findAllByCaseID&id='+_KID}),
					reader:new Ext.data.JsonReader({root:''},[
						{name:'id'},
						{name:'time'},
						{name:'title'}
					])
				}),
				listeners:{
					'render':function(){
						this.getStore().load();
					},
					'rowdblclick':function(_grid,_rowIndex){
						var _id=_grid.getStore().getAt(_rowIndex).data.id;
						Ext.Ajax.request({
							url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do?method=DailyCourseRec_findByID&id='+_id,
							success:function(_response){
								var _res=Ext.decode(_response.responseText);
								if(_res.success){
									setFormValue(Ext.decode(_res.data));
								}else{
									alert('数据获取失败。');
								}
							}
						});
					}
				},
				tbar:[
					{
						text:'新增',menu:[
							{
								text:'首次病程记录',handler:addNewRecording
							},{
								text:'日常病程记录',handler:addNewRecording
							},{
								text:'上级查房记录',handler:addNewRecording
							},{
								text:'交接班记录',handler:addNewRecording
							},{
								text:'转出记录',handler:addNewRecording
							},{
								text:'转让记录',handler:addNewRecording
							},{
								text:'阶段小结',handler:addNewRecording
							},{
								text:'抢救记录',handler:addNewRecording
							},{
								text:'术后首次病程记录',handler:addNewRecording
							},{
								text:'出院小结',handler:addNewRecording
							}
						]
					},{
						xtype:'tbseparator'
					},{
						text:'删除',handler:function(){
							var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
							if(_ss.length==0){
								alert('请先选择一条记录。');
								return;
							}
							if(!confirm('确定要删除此记录？')){
								return;
							}
							var _id=_ss[0].data.id;
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do?method=DailyCourseRec_delete&id='+_id,
								success:function(_response){
									var _res=Ext.decode(_response.responseText);
									if(_res.success){
										alert('删除成功。');
										Ext.getCmp('grid').getStore().reload();
									}else{
										alert('删除失败。');
									}
								}
							});
						}
					},'-',{
						text:'打印',menu:[
							{
								text:'全部打印',handler:function(){
									window.open('print/main_print.html?KID='+_KID);
								}
							},{
								text:'续打',handler:function(){
									Ext.Msg.prompt('打印序号','请输入需要打印的记录号：',function(_btn,_txt){
										if(_btn='ok'){
											var _nos=_txt.split('-');
											var _start=_nos[0];
											var _end=0;
											if(_nos.length==1){
												_end=_start;
											}else{
												_end=_nos[1];
											}
											if(isNaN(_start)||isNaN(_end)){
												alert('请输入合法的数值。');
												return;
											}
											var _count=Ext.getCmp('grid').getStore().getCount();
											if(!(_start>0&&_start<=_count&&_end>0&&_end<=_count&&_start<=_end)){
												alert('序号不在有效范围内。');
												return;
											}
											window.open('print/main_print.html?KID='+_KID+'&start='+_start+'&end='+_end);
										}
									});
								}
							}
						]
					}
				]
			},{
				region:'center',
				title:'记录编辑',
				id:'center-panel',
				tbar:[
					{
						text:'保存',id:'save-btn',disabled:true,handler:SaveData
					},'->',{
						text:'返回',handler:function(){
							location.href=App.App_Info.BasePath+'/module/InHospitalCase/CaseList.html?id='+_PID;
						}
					},'&nbsp;&nbsp;'
				],
				bbar:[
					'<div id="verifyStatus_show" style="width:22px;padding:0;"></div><input type="hidden" id="verifyStatus" value="0"/>',
					'<div class="item-label">审核签字：</div>',
					'<div id="submiter_show" class="status-div"></div><input type="hidden" id="submiter" value=""/><input type="hidden" id="submitTime" value=""/>',
					{
						text:'签字',id:'submit-btn',disabled:true,pressed:true,handler:function(){
							if(this.text=='签字'){
								if(!confirm('确定要签入您的名字<'+_user.name+'>'))return;
								Ext.fly('submiter').dom.value=_user.id;
								Ext.fly('submitTime').dom.value=new Date().format('Y-m-d H:m');
								Ext.fly('verifyStatus').dom.value=1;
								Ext.fly('checker').dom.value="";
								Ext.fly('checkTime').dom.value="";
							}else if(this.text=='撤销'){
								if(!confirm('确定要撤销提交的审核？'))return;
								Ext.fly('submiter').dom.value=0;
								Ext.fly('submitTime').dom.value='';
								Ext.fly('verifyStatus').dom.value=0;
								Ext.fly('checker').dom.value="";
								Ext.fly('checkTime').dom.value="";
							}else if(this.text=='重签'){
								if(!confirm('确定要重新签入您的名字<'+_user.name+'>'))return;
								Ext.fly('submiter').dom.value=_user.id;
								Ext.fly('submitTime').dom.value=new Date().format('Y-m-d H:m');
								Ext.fly('verifyStatus').dom.value=1;
								Ext.fly('checker').dom.value="";
								Ext.fly('checkTime').dom.value="";
							}
							SaveData();
						}
					},
					'<div id="checker_show" class="status-div"></div><input type="hidden" id="checker" value=""/><input type="hidden" id="checkTime" value=""/>',
					{
						text:'签字',id:'check-btn',disabled:true,pressed:true,handler:function(){
							if (this.text == '签字') {
								if(!confirm('确定要签入您的名字<'+_user.name+'>'))return;
								if(confirm('点击【确定】按钮通过审核，点击【取消】按钮不通过。')){
									Ext.fly('verifyStatus').dom.value=2;
								}else{
									Ext.fly('verifyStatus').dom.value=3;
								}
								Ext.fly('checker').dom.value=_user.id;
								Ext.fly('checkTime').dom.value=new Date().format('Y-m-d H:m');
							}else if(this.text=='撤销'){
								if(!confirm('确定要撤销您对该记录的审核？'))return;
								Ext.fly('verifyStatus').dom.value=1;
								Ext.fly('checker').dom.value="";
								Ext.fly('checkTime').dom.value="";
							}
							SaveData();
						}
					},
					'->',
					'<div class="item-label">创建日期：</div>',
					'<div id="createTime" class="status-div"></div>',
					'<div class="item-label">更新日期：</div>',
					'<div id="modifyTime" class="status-div"></div>'
				],
				listeners:{
					'render':function(){
						Ext.DomHelper.append(this.body,{
							id:'recording-time-title',
							tag:'div',
							html:'<table width="100%" cellpadding=0 border=0 cellspacing=0>'
								+'<tr>'
								+'<td width="200"><span>时间：</span><input id="time" class="time" type="text" readonly/><img src="../../../PUBLIC/images/calendar.bmp" onclick="ShowTimePicker(this)"/></td>'
								+'<td width="*" align="center"><span>标题：</span><input id="title" class="title" type="text" /></td>'
								+'<td width="200">&nbsp;</td>'
								+'</tr>'
								+'</table>'
						});
						Ext.DomHelper.append(this.body,{
							id:'recording-content',
							tag:'div',
							html:'<textarea id="content" name="content"></textarea>'
						});
					}
				}
			}
		]
	});
	layoutRTC();
}
function layoutRTC(){
	var _height=Ext.getCmp('center-panel').getInnerHeight();
	var _width=Ext.getCmp('center-panel').getInnerWidth();
	Ext.get('recording-content').setHeight(_height-30);
	Ext.get('content').setHeight(_height-30);
	Ext.get('content').setWidth(_width-10);
	var editor=new FCKeditor('content',_width-10,_height-30);
	editor.BasePath='../../../PUBLIC/Scripts/fckeditor/';
	editor.ReplaceTextarea();
}
function ShowTimePicker(_this){
	WdatePicker({
		el: Ext.get(_this).prev().dom,
		dateFmt:'yyyy-MM-dd HH:mm'
	});
}
function addNewRecording(){
	var json={
		id:-1,
		time:new Date().format('Y-m-d H:m'),
		title:this.text
	};
	setFormValue(json);
}
function setFormValue(_json){
	var json={
		id:-1,
		time:'',
		title:'',
		content:'',
		submiter:'',
		submiter_show:'',
		submitTime:'',
		checker:'',
		checker_show:'',
		checkTime:'',
		verifyStatus:0,
		verifyStatus_show:'',
		createTime:'',
		modifyTime:'',
		saveAction:false,
		submitAction:false,
		checkAction:false
	};
	Ext.apply(json,_json);
	_currentID=json.id;
	Ext.getCmp('check-btn').setText('签字');
	Ext.getCmp('submit-btn').setText('签字');
	if(json.verifyStatus==0){
		json.saveAction=true;
		json.submitAction=true;
		json.checkAction=false;
	}else if(json.verifyStatus==1){
		json.saveAction=false;
		if(_user.id==json.submiter){
			json.submitAction=true;
			Ext.getCmp('submit-btn').setText('撤销');
		}else{
			json.submitAction=false;
		}
		json.checkAction=true;
	}else if(json.verifyStatus==2){
		json.saveAction=false;
		json.submitAction=false;
		json.checkAction=false;
		if(_user.id==json.checker){
			json.checkAction=true;
			Ext.getCmp('check-btn').setText('撤销');
		}
	}else if(json.verifyStatus==3){
		json.saveAction=false;
		json.submitAction=false;
		json.checkAction=false;
		if(_user.id==json.submiter){
			json.saveAction=true;
			json.submitAction=true;
			Ext.getCmp('submit-btn').setText('重签');
		}
		if(_user.id==json.checker){
			json.checkAction=true;
			Ext.getCmp('check-btn').setText('撤销');
		}
	}
	if(!isNaN(json.submiter)&&json.submiter>0){
		Ext.Ajax.request({
			url:App.App_Info.BasePath+'/common/CommonAction.do',
			params:{
				method:'GetIndependentDictionaryText',
				code:'userName',
				value:json.submiter
			},
			sync:true,
			success:function(_response){
				json.submiter_show=_response.responseText;
			}
		});
	}
	if(!isNaN(json.checker)&&json.checker>0){
		Ext.Ajax.request({
			url:App.App_Info.BasePath+'/common/CommonAction.do',
			params:{
				method:'GetIndependentDictionaryText',
				code:'userName',
				value:json.checker
			},
			sync:true,
			success:function(_response){
				json.checker_show=_response.responseText;
			}
		});
	}
	if(json.verifyStatus==0){
		json.verifyStatus_show='<img width="22" height="22" src="'+App.App_Info.BasePath+'/PUBLIC/images/3D_ball_gray.png" alt="未提交审核"/>';
	}else if(json.verifyStatus==1){
		json.verifyStatus_show='<img width="22" height="22" src="'+App.App_Info.BasePath+'/PUBLIC/images/3D_ball_yellow.png" alt="等待审核"/>';
	}else if(json.verifyStatus==2){
		json.verifyStatus_show='<img width="22" height="22" src="'+App.App_Info.BasePath+'/PUBLIC/images/3D_ball_green.png" alt="通过审核"/>';
	}else if(json.verifyStatus==3){
		json.verifyStatus_show='<img width="22" height="22" src="'+App.App_Info.BasePath+'/PUBLIC/images/3D_ball_red.png" alt="未通过审核"/>';
	}
	Ext.fly('time').dom.value=json.time;
	Ext.fly('title').dom.value=json.title;
	FCKeditorAPI.GetInstance('content').SetData(json.content);
	Ext.fly('submiter').dom.value=json.submiter;
	Ext.fly('submiter_show').update(json.submiter_show);
	Ext.fly('submitTime').dom.value=json.submitTime;
	Ext.fly('checker').dom.value=json.checker;
	Ext.fly('checker_show').update(json.checker_show);
	Ext.fly('checkTime').dom.value=json.checkTime;
	Ext.fly('verifyStatus').dom.value=json.verifyStatus;
	document.getElementById('verifyStatus_show').innerHTML=json.verifyStatus_show;
	Ext.fly('createTime').update(json.createTime);
	Ext.fly('modifyTime').update(json.modifyTime);
	Ext.getCmp('save-btn').setDisabled(!json.saveAction);
	Ext.getCmp('submit-btn').setDisabled(!json.submitAction);
	Ext.getCmp('check-btn').setDisabled(!json.checkAction);
}
function SaveData(){
	var json={};
	json.id=_currentID;
	json.patientId=_PID;
	json.caseId=_KID;
	json.time=Ext.fly('time').dom.value;
	json.title=Ext.fly('title').dom.value;
	json.content=FCKeditorAPI.GetInstance('content').GetXHTML(true);
	json.submiter=Ext.fly('submiter').dom.value;
	json.submitTime=Ext.fly('submitTime').dom.value;
	json.checker=Ext.fly('checker').dom.value;
	json.checkTime=Ext.fly('checkTime').dom.value;
	json.verifyStatus=Ext.fly('verifyStatus').dom.value;
	json.createTime=Ext.fly('createTime').dom.innerText;
	json.modifyTime=Ext.fly('modifyTime').dom.innerText;
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do?method=DailyCourseRec_saveOrUpdate',
		params:{
			data:Ext.encode(json)
		},
		success:function(_response){
			var _res=Ext.decode(_response.responseText);
			if(_res.success){
				alert('保存成功。');
				setFormValue(Ext.decode(_res.data));
				Ext.getCmp('grid').getStore().reload();
			}else{
				alert('保存失败。');
			}
		}
	});
}
