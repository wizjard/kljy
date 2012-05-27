var pid=App.util.getHtmlParameters('PID');
var kid=App.util.getHtmlParameters('KID');
var title=decodeURIComponent(App.util.getHtmlParameters('title'));
var entity=App.util.getHtmlParameters('entity');
var thisId=null;
Ext.onReady(function(){
	new Ext.Viewport({
		layout:'fit',
		items:{
			title:title,
			id:'main',
			autoScroll:true,
			tbar:[
				'-',{
					text:'索引',id:'index-btn',menu:[]
				},'-','->','-',{
					text:'打印',id:'print-btn',handler:function(){
						document.getElementById('iframe').contentWindow.print();
					}
				},'-',{
					text:'返回',handler:function(){
						history.go(-1);
						//location.href='../../CaseList.html?id='+pid;
					}
				},'-'
			],
			html:'',
			bbar:[
				'<img id="verifyImg" widht="18" height="18" src="'+App.App_Info.BasePath+'/PUBLIC/images/3D_ball_gray.png" alt="未提交审核"/>&nbsp;&nbsp;',
				'<div id="verifyTxt"></div>',
				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;医生签字：',{
					xtype:'textfield',
					id:'submiter_name',
					readOnly:true,
					width:100
				},{
					text:'签字',id:'submiter-btn',handler:function(){
						if(thisId&&thisId>0){
							var params={};
							if(this.text=='签字'){
								if(!confirm('签字后记录将不可修改。确定要签字<'+top.USER.name+'>？'))
									return;
								params={
									submiter:top.USER.id,
									checker:-1,
									verifyStatus:1
								}
							}else if(this.text=='撤销'){
								if(!confirm('确定要撤销提交的审核？'))
									return;
								params={
									submiter:-1,
									checker:-1,
									verifyStatus:0
								}
							}else if(this.text=='重签'){
								if(!confirm('签字后记录将不可修改。确定要重新签字<'+top.USER.name+'>？'))
									return;
								params={
									submiter:top.USER.id,
									checker:-1,
									verifyStatus:1
								}
							}
							Ext.apply(params,{
								method:'public_verify',
								entity:entity,
								id:thisId
							});
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
								params:params,
								success:function(_response){
									var res=Ext.decode(_response.responseText);
									if(res.success){
										alert('审核操作成功。');
										var data=Ext.decode(res.data);
										changeVerifyStatus(data);
									}else{
										alert('更新审核信息失败。');
									}
								}
							});
						}else{
							alert('当前数据未保存，请先 保存数据后再审核。');
						}
					}
				},'-',{
					xtype:'textfield',
					readOnly:true,
					id:'checker_name',
					width:100
				},{
					text:'签字',id:'checker-btn',handler:function(){
						var params={};
						if(this.text=='签字'){
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
											params.checker=top.USER.id;
											params.verifyStatus=v;
											params.submiter=document.getElementsByName('submiter')[0].value;
											Ext.apply(params,{
												method:'public_verify',
												entity:entity,
												id:thisId
											});
											Ext.Ajax.request({
												url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
												params:params,
												scope:this,
												success:function(_response){
													var res=Ext.decode(_response.responseText);
													if(res.success){
														alert('审核操作成功。');
														var data=Ext.decode(res.data);
														changeVerifyStatus(data);
														this.ownerCt.close();
													}else{
														alert('更新审核信息失败。');
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
							return;
						}else if(this.text=='撤销'){
							if(!confirm('确定要撤销审核？'))return;
							params.checker=-1;
							params.verifyStatus=1;
						}
						params.submiter=document.getElementsByName('submiter')[0].value;
						Ext.apply(params,{
							method:'public_verify',
							entity:entity,
							id:thisId
						});
						Ext.Ajax.request({
							url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
							params:params,
							success:function(_response){
								var res=Ext.decode(_response.responseText);
								if(res.success){
									alert('审核操作成功。');
									var data=Ext.decode(res.data);
									changeVerifyStatus(data);
								}else{
									alert('更新审核信息失败。');
								}
							}
						});
					}
				}
			],
			listeners:{
				render:function(){
					var url=createIndex();
					this.body.dom.innerHTML=
					'<iframe id="iframe" src="'+url+'" width="100%" height="100%" frameborder="0" srcoll="true"></iframe>'+
					'<input type="hidden" name="submiter"/>'+
					'<input type="hidden" name="checker"/>'+
					'<input type="hidden" name="verifyStatus"/>';
				}
			}
		}
	});
});
function createIndex(){
	var url='';
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
		params:{
			method:'public_mainMenu',
			kid:kid,
			entityName:entity
		},
		sync:true,
		success:function(_response){
			var btn=Ext.getCmp('index-btn');
			btn.menu=new Ext.menu.Menu({items:[]});
			btn.menu.add(new Ext.menu.Item({text:'新增',id:'-1',handler:indexClick}));
			var res=Ext.decode(_response.responseText);
			if(res.success){
				var menus=Ext.decode(res.data);
				Ext.each(menus,function(_item){
					btn.menu.add(new Ext.menu.Item({text:_item.time,id:_item.id+"",handler:indexClick}));
				});
				var panel=Ext.getCmp('main');
				var t=title;
				if(btn.menu.items.length==1){
					t=title+'(新增)';
					url='iframes/'+entity+'.html?pid='+pid+'&kid='+kid+'&this=-1&timestamp='+new Date().getTime();
				}else{
					var lastMenu=btn.menu.items.get(btn.menu.items.length-1);
					t=title+'('+lastMenu.text+')';
					url='iframes/'+entity+'.html?pid='+pid+'&kid='+kid+'&this='+lastMenu.id+'&timestamp='+new Date().getTime();
				}
				panel.setTitle(t);
			}else{
				alert('初始化索引菜单失败。');
			}
		}
	});
	return url;
}
function setAuth(data){
	thisId=data.id;
	Ext.getCmp('main').setTitle(data.title);
	changeVerifyStatus(data);
}
function changeVerifyStatus(data){
	var user=top.USER;
	document.getElementsByName('submiter')[0].value=data.submiter;
	document.getElementsByName('checker')[0].value=data.checker;
	document.getElementsByName('verifyStatus')[0].value=data.verifyStatus;
	if(data.submiter>0){
		Ext.Ajax.request({
			url:App.App_Info.BasePath+'/common/CommonAction.do',
			params:{
				method:'GetIndependentDictionaryText',
				code:'userName',
				value:data.submiter
			},
			sync:false,
			success:function(_response){
				Ext.getCmp('submiter_name').setValue(_response.responseText);
			}
		});
	}else{
		Ext.getCmp('submiter_name').setValue('');
	}
	if(data.checker>0){
		Ext.Ajax.request({
			url:App.App_Info.BasePath+'/common/CommonAction.do',
			params:{
				method:'GetIndependentDictionaryText',
				code:'userName',
				value:data.checker
			},
			sync:false,
			success:function(_response){
				Ext.getCmp('checker_name').setValue(_response.responseText);
			}
		});
	}else{
		Ext.getCmp('checker_name').setValue('');
	}
	var submiterBtn={
		text:'签字',
		disabled:false
	};
	var checkerBtn={
		text:'签字',
		disabled:false
	};
	var saveBtnDisabled=true;
	if(data.verifyStatus==0){
		document.getElementById('verifyImg').src=App.App_Info.BasePath+'/PUBLIC/images/3D_ball_gray.png';
		document.getElementById('verifyImg').alt='未提交审核';
		document.getElementById('verifyTxt').innerText='未提交审核';
		checkerBtn.disabled=true;
		saveBtnDisabled=false;
	}else if(data.verifyStatus==1){
		document.getElementById('verifyImg').src=App.App_Info.BasePath+'/PUBLIC/images/3D_ball_yellow.png';
		document.getElementById('verifyImg').alt='已提交审核';
		document.getElementById('verifyTxt').innerText='已提交审核';
		if(data.submiter==user.id){
			submiterBtn.text='撤销';
			submiterBtn.disabled=false;
		}else{
			submiterBtn.disabled=true;
		}
	}else if(data.verifyStatus==2){
		document.getElementById('verifyImg').src=App.App_Info.BasePath+'/PUBLIC/images/3D_ball_green.png';
		document.getElementById('verifyImg').alt='审核通过';
		document.getElementById('verifyTxt').innerText='审核通过';
		submiterBtn.disabled=true;
		checkerBtn.disabled=true;
		if(data.checker==user.id){
			checkerBtn.text='撤销';
			checkerBtn.disabled=false;
		}
	}else if(data.verifyStatus==3){
		document.getElementById('verifyImg').src=App.App_Info.BasePath+'/PUBLIC/images/3D_ball_red.png';
		document.getElementById('verifyImg').alt='审核未通过';
		document.getElementById('verifyTxt').innerText='审核未通过';
		submiterBtn.disabled=true;
		checkerBtn.disabled=true;
		if(data.checker==user.id){
			checkerBtn.text='撤销';
			checkerBtn.disabled=false;
		}
		if(data.submiter==user.id){
			submiterBtn.disabled=false;
			submiterBtn.text='重签';
			saveBtnDisabled=false;
		}
	}
	Ext.getCmp('submiter-btn').setText(submiterBtn.text);
	Ext.getCmp('submiter-btn').setDisabled(submiterBtn.disabled);
	Ext.getCmp('checker-btn').setText(checkerBtn.text);
	Ext.getCmp('checker-btn').setDisabled(checkerBtn.disabled);
}
function indexClick(){
	thisId=this.id;
	if(this.id==-1){
		setAuth({
			title:title+'(新增)',
			submiter:-1,
			checker:-1,
			verifyStatus:0
		});
	}
	var url='iframes/'+entity+'.html?pid='+pid+'&kid='+kid+'&this='+this.id+'&timestamp='+new Date().getTime();
	document.getElementById('iframe').src=url;
}
