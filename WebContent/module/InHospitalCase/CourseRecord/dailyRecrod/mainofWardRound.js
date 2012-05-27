var baseUrl=App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do';
var PID=App.util.getHtmlParameters('PID');
var ASC='ASC';
Ext.onReady(function(){
	layout();
});
function layout(){
	var store=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:baseUrl+'?method=wardRound_findAllByID&id='+PID}),
		reader:new Ext.data.JsonReader({root:''},[
			{name:'id'},
			{name:'patientId'},
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
		]),
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
				dataUrl:baseUrl+'?method=wardRound_treeNodes&id='+PID,
				root:{
					nodeType:'async',
					text:'字典树',
					draggable:false,
					id:-1
				},
				rootVisible:false
			},{
				region:'center',
				id:'center-panel',
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
								text:'转出记录',handler:addNewRecording
							},{
								text:'转入记录',handler:addNewRecording
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
									method:'wardRound_Delete',
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
						text:'保存',handler:SaveData
					},'->',{
						text:'打印',handler:function(){
					//		window.open('print.html?id='+PID);
						}
					},'->'
				],
				bodyStyle:'padding-left:5px;',
				listeners:{
					render:function(){
						var div=document.createElement('div');
						div.id = 'header';
						div.style.width=700;
						div.style.position = 'relative';
						this.body.dom.appendChild(div);
						registrationPageHead(PID,'查房记录','#header');
						
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
	var now=new Date().format('Y-m-d H:m')
	var store=Ext.getCmp('data-view').getStore();
	store.add(new Ext.data.Record({
		id:-1,
		patientId:PID,
		time:now,
		title:title,
		content:'',
		createTime:now,
		submiter:'',
		submitTime:'',
		checker:'',
		checkTime:'',
		verifyStatus:0,
		createTime:now,
		modifyTime:now
	}));
	//store.sort('time',ASC);
	scrollTo(-1);
}
function addEmptyTitleRecording(){
	var title='';
	var now=new Date().format('Y-m-d H:m')
	var store=Ext.getCmp('data-view').getStore();
	store.add(new Ext.data.Record({
		id:-1,
		patientId:PID,
		time:now,
		title:title,
		content:'',
		createTime:now,
		submiter:'',
		submitTime:'',
		checker:'',
		checkTime:'',
		verifyStatus:0,
		createTime:now,
		modifyTime:now
	}));
	//store.sort('time',ASC);
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
		params.method='wardRoundRec_submit';
		params.id=_id;
		params.submiter=top.USER.id;
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
		params.method='wardRound_cancelSubmit';
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
		params.method='wardRound_resubmit';
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
			title:'查房记录审核',
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
								method:'wardRound_check',
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
	Ext.Ajax.request({
		url:baseUrl,
		params:{
			method:'wardRound_Save',
			data:Ext.encode(json)
		},
		success:function(_response){
			if(Ext.decode(_response.responseText).success){
				alert('保存成功。');
				Ext.getCmp('data-view').getStore().reload();
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