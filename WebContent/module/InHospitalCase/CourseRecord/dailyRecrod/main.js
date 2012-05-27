var baseUrl=App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do';
var PID=App.util.getHtmlParameters('PID');
var KID=App.util.getHtmlParameters('KID');
var ASC='ASC';
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


var record = Ext.data.Record.create([
             {name:'id'},
             {name:'patientId'},
             {name:'caseId'},
             {name:'time'},
             {name:'title'},
             {name:'content'},
             {name:'createTime'},
             {name:'submiter'},
             {name:'submitTime'},
             {name:'checker'},
             {name:'checkTime'},
             {name:'verifyStatus'},
             {name:'createTime'},
             {name:'modifyTime'},
             {name:'oldOperation'}
             ]);

function layout(){
	var store=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:baseUrl+'?method=DailyCourseRec_findAllByCaseID&id='+KID}),
		reader:new Ext.data.JsonReader({root:''},record),
		sortInfo:{field:'time',direction:'ASC'},
		sortData:function(f, direction){
            direction = direction || 'ASC';   
            var st = this.fields.get(f).sortType;   
            var fn = function(r1, r2 , store){   
                var v1 = st(r1.data[f]), v2 = st(r2.data[f]);   
                if(typeof(v1) == 'string' && typeof(v2) == 'string'){                                 
					var al=Date.parseDate(v1,'Y-m-d H:i');
					var bl=Date.parseDate(v2,'Y-m-d H:i');
                    return al > bl ? 1 : (al < bl ? -1 : 0);   
                }else{
                    return v1 > v2 ? 1 : (v1 < v2 ? -1 : 0);   
                }   
            };
            this.data.sort(direction, fn);   
            if(this.snapshot && this.snapshot != this.data){   
                this.snapshot.sort(direction, fn);   
            }
       },
	   listeners: {
	   	'datachanged': function(){
	   		var _view = Ext.getCmp('data-view');
	   		var _store = _view.getStore();
	   		_store.each(function(_item, _index, _length){
				_index=_index+1;
	   			_item.data['orderNo'] = _index<10?('0'+_index):_index;
	   		});
	   	}
	   }
	});
	store.load();
	var tpl = new Ext.XTemplate(
		'<tpl for=".">',
		'<div class="data-item"><div>',
			'<table width="100%" border=0 cellpadding=0 cellspacing=2 name="table{id}">',
				'<tr>',
					'<td class="time"><input onchange="TimeChange(this)" type="text" name="time" readonly="readonly" value="{time}"/>',
					'<img {readonly} src="../../../../PUBLIC/images/calendar.bmp" style="cursor:pointer" onclick="ShowTimePicker(this)"/></td>',
					'<td class="title"><input {readonly} type="text" name="title" value="{title}" onchange="TitleChange(this)"/></td>',
					'<td class="index">记录号：{orderNo}</td>',
				'</tr>',
				'<tr>',
					'<td class="content" colspan=4><textarea {readonly} name="content" onchange="ContentChange(this)">{content}</textarea></td>',
				'</tr>',
				'<tr><td colspan="3">',
				'<input type="hidden" name="verifyStatus" value="{verifyStatus}"/>',	
				'<input type="hidden" name="submiter" value="{submiter}"/>',
				'<input type="hidden" name="checker" value="checker"/>',
				'<input type="hidden" name="submitTime" value="{submitTime}"/>',
				'<input type="hidden" name="checkTime" value="{checkTime}"/>',
				'<input type="hidden" name="oldOperation" value="{oldOperation}"/>',
				'创建{createTime}更新{modifyTime}&nbsp;',
				'审核状态{verifyStatus_show}&nbsp;&nbsp;&nbsp;&nbsp;',
				'医生签字',
				'<input readonly="readonly" style="width:80px" value="{checker_show}">',
				'<button onclick="CheckSign(this,{id})" class="sign" {check_btn_active}>{check_btn_text}</button>',
				'<input readonly="readonly" style="width:80px" value="{submiter_show}">',
				'<button onclick="SubmitSign(this,{id})" class="sign" {submit_btn_active}>{submit_btn_text}</button>',
				'</td></tr>',
				/*'<tr>',
					'<td class="verifystatus" rowspan=2 valign="top"><input type="hidden" name="verifyStatus" value="{verifyStatus}"/>审核状态：{verifyStatus_show}</td>',
					'<td align="right" colspan=2>医生签字：',
					'<input type="hidden" name="submiter" value="{submiter}"/><input type="hidden" name="submitTime" value="{submitTime}"/><input readonly="readonly" style="width:100px" value="{submiter_show}">&nbsp;<button onclick="SubmitSign(this,{id})" class="sign" {submit_btn_active}>{submit_btn_text}</button>&nbsp;',
					'<input type="hidden" name="checker" value="{checker}"/><input type="hidden" name="checkTime" value="{checkTime}"/><input readonly="readonly" style="width:100px" value="{checker_show}">&nbsp;<button onclick="CheckSign(this,{id})" class="sign" {check_btn_active}>{check_btn_text}</button></td>',
				'</tr>',
				'<tr>',
				'<td colspan=2 height=30 align="right">',
				'<span>创建时间：</span><span style="color:#00F">{createTime}</span><span style="padding-left:3.6em">上次更新时间：</span><span style="color:#00F">{modifyTime}</span></td>',
				'</tr>',*/
			'</table>',
		'</div></div>',
		'</tpl>'
	);
	var dataview=new Ext.DataView({
		id:'data-view',
		store:store,
		tpl:tpl,
		autoHeight:true,
		singleSelect:true,
		overClass:'data-item-over',
        itemSelector:'div.data-item',
        emptyText: 'No record to display',
		prepareData:function(data){
			var USER=top.USER;
			data.submit_btn_text='签字';
			data.check_btn_text='签字';
			data.submit_btn_active='';
			data.check_btn_active='';
			data.readonly='';
			var imgSrc='3D_ball_gray.png';
			var imgAlt='未提交审核';
			if(data.verifyStatus==0){
				data.check_btn_active='disabled';
			}else if(data.verifyStatus==1){
				imgSrc='3D_ball_yellow.png';
				imgAlt='已提交审核';
				data.submit_btn_active='disabled';
				if(USER.id==data.submiter){
					data.submit_btn_active='';
					data.submit_btn_text='撤销';
				}
				data.readonly='readonly="readonly"';
			}else if(data.verifyStatus==2){
				imgSrc='3D_ball_green.png';
				imgAlt='审核通过';
				data.submit_btn_active='disabled';
				data.check_btn_active='disabled';
				if(USER.id=data.checker){
					data.check_btn_active='';
					data.check_btn_text='撤销';
				}
				data.readonly='readonly="readonly"';
			}else if(data.verifyStatus==3){
				imgSrc='3D_ball_red.png';
				imgAlt='审核未通过';
				data.submit_btn_active='disabled';
				data.check_btn_active='disabled';
				if(USER.id=data.checker){
					data.check_btn_active='';
					data.check_btn_text='撤销';
				}
				if(USER.id==data.submiter){
					data.submit_btn_active='';
					data.submit_btn_text='重签';
				}
			}
			data.verifyStatus_show=
				'<img align="absbottom" width="22" height="22" alt="'+imgAlt+'" src="../../../../PUBLIC/images/'+imgSrc+'"/>'+
				'<span style="color:red">'+imgAlt+'</span>';
			if(data.submiter&&data.submiter>0)
				Ext.Ajax.request({
					url:App.App_Info.BasePath+'/common/CommonAction.do',
					params:{
						method:'GetIndependentDictionaryText',
						code:'userName',
						value:data.submiter
					},
					sync:true,
					scope:this,
					success:function(_response){
						data.submiter_show=_response.responseText;
					}
				});
			if(data.checker&&data.checker>0)
				Ext.Ajax.request({
					url:App.App_Info.BasePath+'/common/CommonAction.do',
					params:{
						method:'GetIndependentDictionaryText',
						code:'userName',
						value:data.checker
					},
					sync:true,
					scope:this,
					success:function(_response){
						data.checker_show=_response.responseText;
					}
				});
			return data;
		}
	});
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'west',
				border:false,
				split:true,
				id:'west-panel',
				title:'快速定位',
				width:150,
				xtype:'treepanel',
				collapsible:true,
				autoScroll:true,
				containerScroll:true,
				dataUrl:baseUrl+'?method=DailyCourseRec_treeNodes&id='+KID,
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
					'-',{
						text:'增加',menu:[
							{
								text:'首次病程记录',handler:addNewRecording
							},{
								text:'日常病程记录',handler:addEmptyTitleRecording
							},{
								text:'上级查房记录',handler:addNewRecording
							},{
								text:'交班记录',handler:addNewRecording
							},{
								text:'接班记录',handler:addNewRecording
							},{
								text:'转出记录',handler:addNewRecording
							},{
								text:'转入记录',handler:addNewRecording
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
					},'-',{
						text:'删除',handler:function(){
							var _view=Ext.getCmp('data-view');
							var _indexs=_view.getSelectedIndexes();
							if(_indexs.length==0){
								alert('请先选择一条记录。');
								return;
							}
							var _record=_view.getStore().getAt(_indexs[0]);
							if(_record.data.verifyStatus==1){
								alert('记录已提交审核，无法删除。');
								return;
							}else if(_record.data.verifyStatus==2){
								alert('记录已通过审核，无法删除。');
								return;
							}
							if(!confirm('记录删除后不可恢复，确定要删除？'))	return;
							if(_record.data.id==-1){
								_view.getStore().remove(_record);
								return;
							}
							Ext.Ajax.request({
								url:baseUrl,
								params:{
									method:'DailyCourseRec_Delete',
									id:_record.data.id
								},
								success:function(_response){
									if(Ext.decode(_response.responseText).success){
										Ext.getCmp('west-panel').root.reload();
										_view.getStore().remove(_record);
									}else{
										alert('服务器删除数据失败。');
									}
								}
							});
						}
					},'-',{
						text:'排序 ( ↓ ) ',handler:function(){
							var down='↑';
							var up='↓';
							if(ASC=='ASC'){
								this.setText('排序 ( ↑ )');
								ASC='DESC';
							}else{
								this.setText('排序 ( ↓ )');
								ASC='ASC';
							}
							Ext.getCmp('data-view').getStore().sort('time',ASC);
						}
					},'-',{
						text:'化验检查',handler:LabExamenation
					},'-',{
						text:'化验检查(新版)',handler:ShowLabExamenation
					},'-',{
						text:'保存',handler:SaveData
					}
					,'->',{
						text:'以前打印',handler:function(){
							//liugang 2011-08-31 start 质控一级医生必须签字
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
								params:{
									method:'checkSubmitCourseRecording',
									kid:KID,
									pid:PID
								},
								sync:true,
								scope:this,
								success:function(_response){
									var res=Ext.decode(_response.responseText);
									if(res.success){
										alert("请签字后打印！");
										return;
									}else{
										window.open('print.html?id='+KID);
									}
								}
							});
						}
						//liugang 2011-08-31 end 质控一级医生必须签字
					}
					,'->',{
						text:"新打印",menu:[
						     {text:'全部打印',handler:function(){
							//liugang 2011-08-31 start 质控一级医生必须签字
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
								params:{
									method:'checkSubmitCourseRecording',
									kid:KID,
									pid:PID
								},
								sync:true,
								scope:this,
								success:function(_response){
									var res=Ext.decode(_response.responseText);
									if(res.success){
										alert("请签字后打印！");
										return;
									}else{
										window.open(App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do?method=createHTMLToPDF&id='+KID);
									}
								}
							});
							//liugang 2011-08-31 end 质控一级医生必须签字
					}
						     }
						     ,{
									text:'未满页续打',handler:function(){
									//liugang 2011-08-31 start 质控一级医生必须签字
										Ext.Ajax.request({
											url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
											params:{
												method:'checkSubmitCourseRecording',
												kid:KID,
												pid:PID
											},
											sync:true,
											scope:this,
											success:function(_response){
												var res=Ext.decode(_response.responseText);
												if(res.success){
													alert("请签字后打印！");
													return;
												}else{
													var num=window.prompt('请输入需要续打的记录号，参考格式：3、5-6','');
													if(!num)return;
													var nums=num.split('-');
													if(nums.length<2){
														nums[0]=num;
														nums[1]=num;
													}
													if(isNaN(nums[0])||isNaN(nums[1])){
														alert('输入了非法的数字字符。');
														return;
													}else{
														window.open(App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do?method=createHTMLToPDF&id='+KID+'&nextPrintNum='+num);
													}
												}
											}
										});
									}
									//liugang 2011-08-31 end 质控一级医生必须签字
						     }
						  ]
				},'->',{
						text:'返回',handler:function(){
							//history.go(-1);
							parent.backToHistory();
						}
					},'&nbsp;&nbsp;'
				],
				bodyStyle:'padding-left:5px;',
				listeners:{
					render:function(){
						var div=document.createElement('div');
						/*div.id='page-header';
						div.style.width=700;
						this.body.dom.appendChild(div);
						var rh=new RepoertHeader();
						rh.basePath=App.App_Info.BasePath;
						rh.el=div;
						rh.cid=KID;
						rh.config.title='病程记录';
						rh.create();*/
						div.id = 'header';
						div.style.width=700;
						div.style.position = 'relative';
						this.body.dom.appendChild(div);
						registrationPageHead(KID,'病程记录','#header');
						
						div=document.createElement('div');
						div.id='data-view-div';
						div.style.width=700;
						this.body.dom.appendChild(div);
						dataview.render(div);
					}
				}
			}
		]
	});
}
function addNewRecording(){
	var title=this.text;
	//var now=new Date().format('Y-m-d H:m')
	var store=Ext.getCmp('data-view').getStore();
	store.add(new Ext.data.Record({
		id:-1,
		patientId:PID,
		caseId:KID,
		time:currentTime(),
		title:title,
		content:'',
		createTime:currentTime(),
		submiter:'',
		submitTime:'',
		checker:'',
		checkTime:'',
		verifyStatus:0,
		createTime:currentTime(),
		modifyTime:currentTime()
	}));
//	store.sort('time',ASC);
	scrollTo(-1);
}
function addEmptyTitleRecording(){
	var title='';
	//var now=new Date().format('Y-m-d H:m')
	var store=Ext.getCmp('data-view').getStore();
	store.add(new Ext.data.Record({
		id:-1,
		patientId:PID,
		caseId:KID,
		time:currentTime(),
		title:title,
		content:'',
		createTime:currentTime(),
		submiter:'',
		submitTime:'',
		checker:'',
		checkTime:'',
		verifyStatus:0,
		createTime:currentTime(),
		modifyTime:currentTime()
	}));
//	store.sort('time',ASC);
	scrollTo(-1);
}
function ShowTimePicker(_this){
	var el=Ext.get(_this).prev('input',true);
	if(_this.readonly){
		alert('已提交或通过审核，不能修改。');
		return;
	}
	WdatePicker({el:el,dateFmt:'yyyy-MM-dd HH:mm'});
}
function SubmitSign(_this,_id){
	var _view=Ext.getCmp('data-view');
	var _indexs=_view.getSelectedIndexes();
	var _record=_view.getStore().getAt(_indexs[0]);
	if(_id==-1||$(_this).parent().parent().parent().parent().parent().attr('class').indexOf('modify')!=-1){
		alert('记录未保存，请先点击保存按钮保存数据。');
		return;
	}
	var params={};
	var callback=null;
	if(_this.innerText=='签字'){
		if(!confirm('确定要签入您的名字<'+top.USER.name+'>？'))return;
		params.method='DailyCourseRec_submit';
		params.id=_id;
		params.submiter=top.USER.id;
		alert(top.USER.id);
		callback=function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				alert('成功提交审核。');
				Ext.apply(Ext.getCmp('data-view').getRecord(Ext.get(_this).findParentNode('div.data-item')).data,Ext.decode(res.data));
				Ext.getCmp('data-view').refreshNode(Ext.getCmp('data-view').getSelectedIndexes()[0]);
			}else{
				alert('提交审核失败。');
			}
		}
	}else if(_this.innerText=='撤销'){
		if(!confirm('确定要撤销提交的审核？'))return;
		params.method='DailyCourseRec_cancelSubmit';
		params.id=_id;
		callback=function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				alert('成功撤销提交审核。');
				Ext.apply(Ext.getCmp('data-view').getRecord(Ext.get(_this).findParentNode('div.data-item')).data,Ext.decode(res.data),{submiter_show:''});
				Ext.getCmp('data-view').refreshNode(Ext.getCmp('data-view').getSelectedIndexes()[0]);
			}else{
				alert('提交审核撤销失败。');
			}
		}
	}else if(_this.innerText=='重签'){
		if(!confirm('确定要重新签入您的名字<'+top.USER.name+'>？'))return;
		params.method='DailyCourseRec_resubmit';
		params.id=_id;
		params.submiter=top.USER.id;
		callback=function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				alert('重签成功。');
				Ext.apply(Ext.getCmp('data-view').getRecord(Ext.get(_this).findParentNode('div.data-item')).data,Ext.decode(res.data),{checker_show:'',checkTime:''});
				Ext.getCmp('data-view').refreshNode(Ext.getCmp('data-view').getSelectedIndexes()[0]);
			}else{
				alert('重签失败。');
			}
		}
	}
	Ext.Ajax.request({
		url:baseUrl,
		params:params,
		scope:this,
		success:callback
	});
}
function CheckSign(_this,_id){
	var _view=Ext.getCmp('data-view');
	var _indexs=_view.getSelectedIndexes();
	var _record=_view.getStore().getAt(_indexs[0]);
	if(_id==-1){
		alert('记录未保存，请先点击保存按钮保存数据。');
		return;
	}
	if (_this.innerText == '签字') {
		new Ext.Window({
			title:'病程记录审核',
			frame:true,
			autoHeight:true,
			width:300,
			closable:false,
			closeAction:'close',
			layout:'fit',
			bodyStyle:'padding:10px;background:#FFF',
			buttonAlign:'center',
			buttons:[
				{
					text:'提交',handler:function(){
						var v=this.ownerCt.items.get(0).form.findField('verifyStatus').getValue();
						Ext.Ajax.request({
							url:baseUrl,
							params:{
								method:'DailyCourseRec_check',
								verifyStatus:v,
								id:_id,
								checker:top.USER.id
							},
							scope:this,
							success:function(_response){
								var res=Ext.decode(_response.responseText);
								if(res.success){
									alert('审核成功。');
									this.ownerCt.close();
									Ext.apply(Ext.getCmp('data-view').getRecord(Ext.get(_this).findParentNode('div.data-item')).data,Ext.decode(res.data));
									Ext.getCmp('data-view').refreshNode(Ext.getCmp('data-view').getSelectedIndexes()[0]);
								}else{
									alert('审核失败。');
								}
							}
						});
					}
				},{
					text:'关闭',handler:function(){
						this.ownerCt.close();
					}
				}
			],
			items:{
				xtype:'form',
				labelAlign:'right',
				labelWidth:60,
				autoHeight:true,
				border:false,
				items:[
					{
						xtype:'textfield',
						fieldLabel:'审签人',
						anchor:'90%',
						readOnly:true,
						value:top.USER.name
					},
					new Ext.form.RadioGroup({
						id:'verifyStatus',
						name:'verifyStatus',
						fieldLabel:'审核状态',
						width:120,
						defaults:{
							autoWidth:true
						},
						getValue:function(){
							var v='';
							this.items.each(function(_item){
								if(_item.checked)
									v=_item.inputValue;
							});
							return v;
						},
						items:[
							{boxLabel:'通过',inputValue:2,checked:true,name:'verifyStatus'},
							{boxLabel:'不通过',inputValue:3,checked:false,name:'verifyStatus'}
						]
					})
				]
			}
		}).show();
	}else if(_this.innerText == '撤销'){
		if(!confirm('确定要撤销您的审核'))return;
		Ext.Ajax.request({
			url:baseUrl,
			params:{
				method:'DailyCourseRec_cancelCheck',
				id:_id
			},
			success:function(_response){
				var res=Ext.decode(_response.responseText);
				if(res.success){
					alert('审核撤销成功。');
					Ext.apply(Ext.getCmp('data-view').getRecord(Ext.get(_this).findParentNode('div.data-item')).data,Ext.decode(res.data),{checker_show:''});
					Ext.getCmp('data-view').refreshNode(Ext.getCmp('data-view').getSelectedIndexes()[0]);
				}else{
					alert('审核撤销失败。');
				}
			}
		});
	}
}
function LabExamenation(){
	var _view=Ext.getCmp('data-view');
	var _indexs=_view.getSelectedIndexes();
	if(_indexs.length==0){
		alert('请先选择一条记录。');
		return;
	}
	var _record=_view.getStore().getAt(_indexs[0]);
	Ext.Ajax.request({
		url:baseUrl,
		params:{
			method:'DailyCourseRec_Lab_findByCourseId',
			id:_record.data.id
		},
		success:function(_response,_options){
			var _res=Ext.decode(_response.responseText);
			if(_res.success){
				LabExamenation_show(Ext.decode(_res.data),_indexs,_record);
			}else{
				alert('获取化验检查数据失败。');
			}
		}
	});
}
function LabExamenation_show(_lab,_index,_record){
	var sheight = screen.height/2;
	var swidth = 620;
	var winoption ="dialogHeight:"+sheight+"px;dialogWidth:"+ swidth +"px;status:yes;scroll:yes;resizable:yes;center:yes";
	var returnValue=window.showModalDialog(
		App.App_Info.BasePath+'/module/InHospitalCase/Liver/CaseDetails/LabExamination.html',
		_lab?_lab:{},
		winoption
	);
	if(returnValue){
		var _text=returnValue.text;
		var _value=returnValue.value;
		if(confirm('您是否需要将本次化验检查数据导入到病程记录中？')){
			var _panel=Ext.getCmp('data-view');
			var _table=_panel.el.dom.getElementsByTagName('table')[_index];
			if(_table){
				var _textarea=_table.getElementsByTagName('textarea')[0];
				if(_textarea.value.length>0){
					_textarea.value+='\n'+_text;
				}else{
					_textarea.value=_text;
				}
			}
		}
		var json='';
		var index=0;
		for(var key in _value){
			json+="\""+key+"\":\""+_value[key]+"\",";
			index++;
		}
		json="{"+json.substr(0,json.length-1)+"}";
		Ext.Ajax.request({
			url:baseUrl,
			params:{
				method:'DailyCourseRec_Lab_saveOrUpdate',
				id:_record.data.id,
				data:json
			},
			success:function(_response,_options){
				var _res=Ext.decode(_response.responseText);
				if(_res.success){
					
				}else{
					alert('化验检查数据更新失败。');
				}
			}
		});
	}
}
function ShowLabExamenation(){
	var _view = Ext.getCmp('data-view');
	var _index =_view.getSelectedIndexes();
	if(_index.length == 0){
		alert('请先选择一条记录。');
		return;
	}
	var _record = _view.getStore().getAt(_index[0]);
	
	var returnValue = window.showModalDialog('../../Liver/CheckReport/combinationListofDailyRec.html?patientId=' + PID + '&KID=' + KID + '&dailyRecId=' + _record.data.id,'','dialogWidth=950px;dialogHeight=650px');
	if(returnValue){
		var _text = returnValue.retValue;
		$('input[name="oldOperation"]').val(returnValue.oldOperation);																
		if(confirm('您是否需要将本次化验检查数据导入到病程记录中？')){
			var _panel=Ext.getCmp('data-view');
			var _table=_panel.el.dom.getElementsByTagName('table')[_index];
			if(_table){
				var _textarea=_table.getElementsByTagName('textarea')[0];				
				if(_textarea.value.length>0){
					_textarea.value+='\n'+_text;
				}else{
					_textarea.value=_text;
				}
			}
			ContentChange(_textarea);
		}
	}
}
function SaveData(){
	var json=[];
	var view=Ext.getCmp('data-view');
	$('div.data-item').filter(function(){
		return $(this).children('div').attr('class').indexOf('modify')!=-1;
	}).each(function(){
		view.getRecord(this).data.oldOperation = $('input[name="oldOperation"]').val();
		var _data=view.getRecord(this).data;
		json.push(_data);
	});
	if(json.length==0){
		alert('无数据需要保存。');
		return;
	}
	
	var me = this;
	me.setDisabled(true);
	
	Ext.Ajax.request({
		url:baseUrl,
		params:{
			method:'DailyCourseRec_SaveRate',
			data:Ext.encode(json)
		},
		success:function(_response){
			
			me.setDisabled(false);
			
			if(Ext.decode(_response.responseText).success){
				alert('保存成功。');
				
				$('div.data-item').filter(function(){
					return $(this).children('div').attr('class').indexOf('modify')!=-1 && view.getRecord(this).data.id==-1;
				}).each(function(){
					view.getStore().remove(view.getRecord(this));
				});
				
				var data = Ext.decode(Ext.decode(_response.responseText).data);
				for(var i = 0; i < data.length; i++){
					view.getStore().add(new record({id:data[i].id,patientId:data[i].patientId,
						caseId:data[i].caseId,time:data[i].time,
						title:data[i].title,content:data[i].content,createTime:data[i].createTime,
						submiter:data[i].submiter,submitTime:data[i].submitTime,
						checker:data[i].checker,checkTime:data[i].checkTime,verifyStatus:data[i].verifyStatus,
						createTime:data[i].createTime,modifyTime:data[i].modifyTime,oldOperation:data[i].oldOperation}));
				}
				
				$('div.data-item').each(function(){
					$(this).children('div').removeClass('modify');
				});
				
				Ext.getCmp('west-panel').root.reload();
			}else{
				alert('保存失败。');
			}
		}
	});
}
function TimeChange(_this){
	var _val=_this.value;
	var _div=Ext.get(_this).findParentNode('div.data-item');
	var _record=Ext.getCmp('data-view').getRecord(_div);
	_record.data.time=_val;
	$(_div).children('div').removeClass('modify');
	$(_div).children('div').addClass('modify');
}
function TitleChange(_this){
	var _val=_this.value;
	var _div=Ext.get(_this).findParentNode('div.data-item');
	var _record=Ext.getCmp('data-view').getRecord(_div);
	_record.data.title=_val;
	$(_div).children('div').removeClass('modify');
	$(_div).children('div').addClass('modify');
}
function ContentChange(_this){
	var _val=_this.value;
	var _div=Ext.get(_this).findParentNode('div.data-item');
	var _record=Ext.getCmp('data-view').getRecord(_div);
	_record.data.content=_val;
	$(_div).children('div').removeClass('modify');
	$(_div).children('div').addClass('modify');
}
function scrollTo(_id){
	var _panel=Ext.getCmp('data-view');
	var _scrollNode=_panel.el.dom.parentNode.parentNode;
	var _tables=_panel.el.query('table');
	Ext.each(_tables,function(_item){
		if(_item.name==('table'+_id)){
			var _offsets=Ext.get(_item).getOffsetsTo(_scrollNode);
			_scrollNode.scrollTop+=_offsets[1];
			_panel.select(_item.parentNode.parentNode);
		}
	});
}