var pid=App.util.getHtmlParameters('id');
var tr = '<tr>' +
			'<td><input name="items" type="checkbox" /></td>' + 
			'<td><input name="count" type="text" style="width:97%;height:100%" class="" /></td>' + 
			'<td><input name="cycle" type="text" style="width:97%;height:100%" class="" /></td>'+ 
			'<td>' + 
				'<table name="subtable" id="subtable" border="1">' + 					
				'</table>' +
			'</td>' +
		 '</tr>';
var subtr = '<tr>' + 
				'<td width="40"><input name="serial" type="text" readonly="readonly" style="width:90%" class="" /></td>' + 
				'<td width="400"><input name="projname" type="text" readonly="readonly" style="width:98%" class="" /></td>' + 
			'</tr>';
var researchTopicId;
Ext.onReady(function(){
	layout();
});
function layout(){
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'入选编号',dataIndex:'serialNum'},
		{header:'课题名称',dataIndex:'researchId',renderer:function(value){
			Ext.Ajax.request({
				url:App.App_Info.BasePath+'/common/CommonAction.do',
				params:{
					method:'GetSelfQueryList',
					sql:'select name from SYS_ResearchTopic where id='+value
				},
				sync:true,
				success:function(_response){
					var data=Ext.util.JSON.decode(_response.responseText);
					if(data.length>0){
						value=data[0].name;
					}
				}
			});
			return value;
		}},
		{header:'课题组别',dataIndex:'subGroup',renderer:function(value,metadata,record){
			Ext.Ajax.request({
				url:App.App_Info.BasePath+'/common/CommonAction.do',
				params:{
					method:'GetSelfQueryList',
					sql:'select name from SYS_ResearchTopic_SubGroup_Rel where code=\''+value+'\' and ResearchTopicID='+record.data.id
				},
				sync:true,
				success:function(_response){
					var data=Ext.util.JSON.decode(_response.responseText);
					if(data.length>0){
						value=data[0].name;
					}
				}
			});
			return value;
		}},
		{header:'入选方式',dataIndex:'random',renderer:function(value){
			if(value==1){
				return '随机分组';
			}else if(value==2){
				return '非随机分组';
			}
			return '';
		}},
		{header:'入选日期',dataIndex:'startDate'},
		{header:'终止日期',dataIndex:'endDate'}
	]);
	var ds=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/ResearchFollowupAction.do'}),
		reader:new Ext.data.JsonReader({root:''},[
			{name:'id'},
			{name:'patientId'},
			{name:'startDate'},
			{name:'endDate'},
			{name:'researchId'},
			{name:'subGroup'},
			{name:'serialNum'},
			{name:'random'},
			{name:'caseId'},
			{name:'followupStart'}
		])
	});
	ds.baseParams={method:'research_findByPatientId',id:pid};
	ds.load();
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'north',
				height:23,
				border:true,
				bodyStyle:'background:url(../../PUBLIC/images/label_line_bg.gif) center repeat;',
				listeners:{
					render:function(){
						this.load({
							url:App.App_Info.BasePath+'/module/Patient/PatSimpleInfo.jsp?id='+pid,
							scripts:true,
							text:'正在获取病人基本信息......'
						});
					}
				}
			},{
				region:'center',
				xtype:'grid',
				id:'grid',
				title:'入选课题列表',
				border:false,
				sm:sm,
				cm:cm,
				ds:ds,
				viewConfig:{
					forceFit:true
				},
				tbar:[
					'-',{
						text:'刷新',handler:function(){
							Ext.getCmp('grid').getStore().reload();
						}
					},'-',{
						text:'新增',handler:function(){
							showEditorWin('新增课题',null);
						}
					},'-',{
						text:'编辑',handler:function(){
							var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
							if(_ss.length==0||_ss.length>1){
								alert('请选择一条记录。');
								return;
							}
							showEditorWin('编辑课题',_ss[0]);
						}
					},'-',{
						text:'删除',handler:function(){
							var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
							if(_ss.length==0||_ss.length>1){
								alert('请选择一条记录。');
								return;
							}
							if(!confirm('确定要删除此记录？'))return;
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/ResearchFollowupAction.do',
								params:{
									method:'research_delete',
									id:_ss[0].data.id
								},
								success:function(_response){
									if(Ext.util.JSON.decode(_response.responseText).success){
										alert('删除成功。');
										Ext.getCmp('grid').getStore().reload();
									}else{
										alert('删除失败。');
									}
								}
							});
						}
					},'-',{
						//=======================================11-14============================
						text:'CRF登记管理',handler:function(){
							var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
							if(_ss.length==0 || _ss.length>1){
								alert('请选择一条记录！');
							}
							location.href=App.App_Info.BasePath + '/module/Followup/RegisterTable/CRFMain.html?rid=' + _ss[0].data.id + '&courseId='+ _ss[0].data.researchId + '&timesText=入选课题登记&pid=' + pid;
						}
					},'-',{
						text:'标本登记管理',handler:function(){
							var _ss=Ext.getCmp('grid').getSelectionModel().getSelections();
							if(_ss.length==0 || _ss.length>1){
								alert('请选择一条记录！');
							}
							location.href=App.App_Info.BasePath + '/module/Followup/register/page/index1.html?rid=' + _ss[0].data.id + '&pid=' + pid + '&courseId=' + _ss[0].data.researchId + '&timesText=入选课题登记';
						}
					},'-',{
						text:'关联化验检查',handler:function(){
							var ss=SelectionRecord();
							if(!ss)return;
							setFollowupPlan(ss, pid);
						}
					},'-'
				]
			}
		]
	});
}
Ext.namespace('Ext.ux');
Ext.ux.ReasearchCombo=Ext.extend(Ext.form.ComboBox,{
	dataUrl:App.App_Info.BasePath+'/common/CommonAction.do',
	mode:'local',
	triggerAction:'all',
	valueField:'value',
	displayField:'text',
	onRender:function(ct,position){
		if(!this.store){
			var url=this.dataUrl;
			var data=[];
			Ext.Ajax.request({
				url:url,
				params:{
					method:'GetSelfQueryList',
					sql:'select id,name from SYS_ResearchTopic'
				},
				sync:true,
				success:function(_response){
					Ext.each(Ext.util.JSON.decode(_response.responseText),function(_item){
						data.push([_item['id'],_item['name']]);
					});
				}
			});
			this.store=new Ext.data.SimpleStore({
				data:data,
				fields:['value','text']
			});
		}
		Ext.ux.ReasearchCombo.superclass.onRender.call(this, ct, position);
	}
});
function showEditorWin(_title,_record){
	var sm1=new Ext.grid.CheckboxSelectionModel();
	var sm2=new Ext.grid.CheckboxSelectionModel();
	new Ext.Window({
		title:_title,
		frame:true,
		closable:false,
		closeAction:'close',
		buttonAlign:'center',
		autoScroll:true,
		width:700,
		autoHeight:true,
		items:[
			{
				xtype:'form',
				title:'基本信息',
				frame:true,
				labelWidth:60,
				labelAlign:'right',
				layout:'column',
				defaults:{
					columnWidth:.5,
					layout:'form',
					bodyStyle:'padding-top:5px',
					defaults:{
						xtype:'textfield',
						anchor:'100%'
					}
				},
				items:[
					{
						items:[
							{
								fieldLabel:'入选编号',
								allowBlank:false,
								name:'serialNum'
							},{
								xtype:'datefield',
								fieldLabel:'起始时间',
								format:'Y-m-d',
								readOnly:true,
								allowBlank:false,
								name:'startDate',
								setValue:function(val){
									if(typeof val=='object'){
										val=val.format('Y-m-d');
									}
									this.el.dom.value=val;
								}
							}
						]
					},{
						items:[
							{
								xtype:'combo',
								fieldLabel:'分组方式',
								hiddenName:'random',
								triggerAction:'all',
								value:1,
								mode:'local',
								readOnly:true,
								valueField:'value',
								displayField:'text',
								store:new Ext.data.SimpleStore({
									data:[[1,'随机分组'],[2,'非随机分组']],
									fields:['value','text']
								})
							},{
								xtype:'datefield',
								fieldLabel:'结束时间',
								format:'Y-m-d',
								readOnly:true,
								allowBlank:false,
								name:'endDate',
								setValue:function(val){
									if(typeof val=='object'){
										val=val.format('Y-m-d');
									}
									this.el.dom.value=val;
								}
							}
						]
					},{
						columnWidth:1,
						items:[
							new Ext.ux.ReasearchCombo({
								fieldLabel:'入选课题',
								hiddenName:'researchId',
								allowBlank:false,
								readOnly:true,
								listeners:{
									select:function(_this,_record,_index){
										this.ownerCt.items.get(1).init();
										researchTopicId = this.getValue();
										while($("#ptable").children().children().size() > 1){
											$("#ptable").children().find('tr:last').remove();
										}
										getPro(researchTopicId);
									}
								}
							}),{
								xtype:'combo',
								fieldLabel:'入选组别',
								hiddenName:'subGroup',
								triggerAction:'all',
								mode:'local',
								readOnly:true,
								allowBlank:false,
								valueField:'value',
								displayField:'text',
								store:new Ext.data.SimpleStore({
									data:[],
									fields:['value','text']
								}),
								init:function(){
									var _combo=this.ownerCt.items.get(0);
									var _data=[];
									if(new String(_combo.getValue()).length>0){
										Ext.Ajax.request({
											url:App.App_Info.BasePath+'/common/CommonAction.do',
											params:{
												method:'GetSelfQueryList',
												sql:'select code,name from SYS_ResearchTopic_SubGroup_Rel where ResearchTopicID='+_combo.getValue()
											},
											sync:true,
											success:function(_response){
												Ext.each(Ext.util.JSON.decode(_response.responseText),function(_item){
													_data.push([_item['code'],_item['name']]);
												});
											}
										});
									}
									this.store=new Ext.data.SimpleStore({
										data:_data,
										fields:['value','text']
									});
								}
							},{
								xtype:'hidden',
								name:'patientId',
								value:pid
							},{
								xtype:'hidden',
								name:'id',
								listeners:{
									render:function(){
										if(_record){
											var _form=this.ownerCt.ownerCt.form;
											_form.loadRecord(_record);
											_form.findField('researchId').fireEvent('select',{});
											_form.findField('subGroup').setValue(_form.findField('subGroup').getValue());
										}
									}
								}
							}
						]
					}
				]
			},{
				xtype:'grid',
				id:'case-grid',
				title:'关联病历',
				height:100,
				autoScroll:true,
				sm:sm1,
				columns:[
					new Ext.grid.RowNumberer(),
					sm1,
					{header:'住院次数',dataIndex:'inHspTimes',width:60},
					{header:'入院日期',dataIndex:'inHspDate',width:100},
					{header:'病历类型',dataIndex:'caseModuleId',width:260,renderer:function(value){
						if(value&&value.length>0){
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/InHospitalCase/CaseMaster.do',
								params:{
									method:'toInHspRec',
									caseModuleId:value
								},
								sync:true,
								success:function(_response,_options){
									var _res=Ext.util.JSON.decode(_response.responseText);
									if(_res.success){
										var _cfg=Ext.util.JSON.decode(_res.data);
										value=_cfg.name;
									}
								}
							});
							return value;
						}
					}}
				],
				store:new Ext.data.Store({
					proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/InHospitalCase/CaseMaster.do'}),
					reader:new Ext.data.JsonReader({root:''},[
						{name:'id'},
						{name:'patientId'},
						{name:'age'},
						{name:'inHspTimes'},
						{name:'inHspDate'},
						{name:'inWardCode'},
						{name:'inIlls'},
						{name:'inStatus'},
						{name:'outDate'},
						{name:'outIlls'},
						{name:'outWardCode'},
						{name: 'responsibleDoc'},
						{name:'currentWordCode'},
						{name:'caseModuleId'}
					]),
					listeners:{
						load:function(_this,_records,_o){
							Ext.getCmp('case-grid').selectRows(_records);
						}
					}
				}),
				selectRows:function(_records){
					if(_record){
						var _id=_record.data.caseId.split(',');
						var _rec=[];
						for(var i=0,len=_id.length;i<len;i++){
							var _i=_id[i];
							if(isNaN(_i)) continue;
							Ext.each(_records,function(_r){
								if(_i==_r.data.id){
									_rec.push(_r);
									return false;
								}
							});
						}
						Ext.getCmp('case-grid').getSelectionModel().selectRecords(_rec,false);
					}
				},
				listeners:{
					render:function(){
						this.getStore().baseParams={method:'queryAllByPatient',id:pid};
						this.getStore().load();
					}
				}
			},new Ext.Panel({
				title:'随访方案',
				closable:false,
				autoScroll:true,
				closeAction:'close',
				width:680,
				height:170,
				layout:'fit',
				tbar:[
					'起始日期',{
						xtype:'datefield',
						format:'Y-m-d',
						width:90,
						allowBlank:false,
						name:'followupStart',
						id:'followupStart',
						listeners:{
							render:function(){
								if(_record){
									this.el.dom.value=_record.data.followupStart
								}
							}
						}
					}
				],
				html:'<table name="ptable" id="ptable" border="1">' +
						'<tr height="20">' +
							'<td width="20"><input name="allinput" type="checkbox" /></td>' +
							'<td width="120"><p align="center">次数</p></td>' +
							'<td width="120"><p align="center">周期</p></td>' +
							'<td width="440"><p align="center">随访项目</p></td>' + 
						'</tr>' +
					'</table>'
			})
		],
		buttons:[
			{
				text:'保存',handler:function(){
					var json={};
					var _items=this.ownerCt.items;
					if(!_items.get(0).form.isValid()){
						alert('请完整填写基本信息后再保存。');
						return;
					}else{
						Ext.apply(json,_items.get(0).form.getValues());
					}
					var _grid=Ext.getCmp('case-grid');
					var _caseId='';
					Ext.each(_grid.getSelectionModel().getSelections(),function(_record){
						_caseId+=_record.data.id+',';
					});
					json.caseId=_caseId.length>0?(_caseId.substr(0,_caseId.length-1)):'';
					if(json.caseId == ""){
						alert('请选择病历');
						return;
					}
					
					json.followupStart=Ext.getCmp('followupStart').getRawValue();
					if(json.followupStart.length==0){
						alert('随访起始时间不能为空。');
						return;
					}
					var _followup=[];
					json.followup=_followup;
					var _json=Ext.util.JSON.encode(json);
					Ext.Ajax.request({
						url:App.App_Info.BasePath+'/ResearchFollowupAction.do',
						params:{
							method:'research_saveOrUpdate',
							data:_json
						},
						scope:this,
						success:function(_response){
							if(Ext.util.JSON.decode(_response.responseText).success){
								alert('保存成功。');
								this.ownerCt.close();
								Ext.getCmp('grid').getStore().reload();
							}else{
								alert('保存失败。');
							}
						}
					});
				}
			},{
				text:'关闭',handler:function(){
					this.ownerCt.close();
				}
			}
		]
	}).show();
}

//???????
function getPro(researchTopicId){
	$.getJSON(App.App_Info.BasePath + '/ResearchFollow/FollowSetUp.do?method=getFollopupPro&researchTopicId=' + researchTopicId + '&date=' + new Date(), function(data){
		for(var i = 0; i < data.length; i++){
			addTr(data[i]);		
		}
	});
}
//????
function addTr(trInfo){
	$("#ptable").append(tr);					
	$('input[name="items"]').click(function(){
		var item = $('input[name="items"]').filter(function(){
			return $(this).attr('checked')==true;
	    });
	    if(item.size() == 0){
	    	$('input[name="allinput"]').attr('checked', false);
	    }else{
	    	$('input[name="allinput"]').attr('checked', true);
	    }
	});
	if(trInfo != null){
		$("#ptable").children().find('tr:last td:eq(1)').children().val(trInfo.followTimes);
		$("#ptable").children().find('tr:last td:eq(2)').children().val(trInfo.followCycle);
		
		var item = $("#ptable").children().find('tr:last td:first').children();
		var combinationName = trInfo.followContent;
		addorRecoverPro(combinationName, item);
	}
}

function addorRecoverPro(combinationName, item){
	var arr = combinationName.split("*~^");
	for(var i = 0; i < arr.length; i++){
		addtoSubTable(item, arr[i]);
	}
}

function addtoSubTable(item, proName){
	item.parent().next().next().next().children().append(subtr);
	if(item.parent().next().next().next().children().children().children().size() == 1){
		item.parents('tr').find('td:eq(3) tr:last td:first').children().val(1);
	}else{
		var prevValue = item.parents('tr').find('td:eq(3) tr:last').prev().children().children().val();
		item.parents('tr').find('td:eq(3) tr:last td:first').children().val(Number(prevValue) + 1);	
	}
	if(proName != null){
		item.parents('tr').find('td:eq(3) tr:last td:last').children().val(proName);
	}	
}
function SelectionRecord(){
	var ss=Ext.getCmp('grid').getSelectionModel().getSelections();
	if(ss.length==0){
		alert('至少选择一条记录。');
		return null;
	}else{
		return ss[0];
	}
}
