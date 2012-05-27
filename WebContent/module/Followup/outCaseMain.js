var baseUrl=App.App_Info.BasePath+'/OutOrMergencyCaseAction.do';
var PID=App.util.getHtmlParameters('PID');
var KID=App.util.getHtmlParameters('KID');
var outRegno =App.util.getHtmlParameters('outRegno');
var flag = App.util.getHtmlParameters('flag');
//liugang 2011-08-06 修改
var HISPID = App.util.getHtmlParameters('HISPID');
var ASC='ASC';
var memberType;
var outNameList =new Array();//门诊病历数组
Ext.onReady(function(){
	//自动添加门诊病历类型选项，原则是根据当前医生所在的科室进行自动分配
	var outPanel = Ext.getCmp("addButton");
	var outComList = top.USER['单位'].split("、");//医生所在的科室
	var deptName="";
	for(var k=0;k<outComList.length;k++){
		if(k != outComList.length -1){
			deptName += outComList[k]+"','";
		}else{
			deptName += outComList[k];
		}
	}
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentHISAction.do',
		params:{
			method:'findDepartmentHISEntityByDeptName',
			deptName:deptName
		},
		sync:true,
		scope:this,
		success:function(response){
			var data = Ext.util.JSON.decode(response.responseText);
			if(data.success){
				var result = Ext.util.JSON.decode(data.data);
				for(var key in result){
					outNameList.push(result[key]);
				}
			}
		}
	});
	
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
	var currentTime = year+"-"+month+"-"+day+" "+hours+":"+minute+":"+second;
	return currentTime;
}


function goToPlan() {
	if(!confirm('确定要新增随访计划?'))return;
	App.util.addNewMainTab('/module/plan/addPlanItem.html?pid='+PID,'增加随访计划');
    
	/*var planURL = '/module/plan/plan.html?paId='+PID;
	App.util.addNewMainTab(planURL,'随访计划');*/
}

function linkPlan(omcid) {
	var planURL = App.App_Info.BasePath+'/module/plan/selectPlanForPatient.html?paId='+PID + "&omcId=" + omcid + "&type=omc&KID=" + KID + "&outRegno=" + outRegno;
	 window.open(planURL, 'plan', 'toolbar=no,scrollbar=yes,resizable=yes, top=200, left=100,width=1200,height=500');
	//App.util.addNewMainTab(planURL,'关联随访计划');
}

//liugang 2011-08-11 start
//function designPlan(){
//	var planURL = '/module/plan/selectPlan.html?paId='+PID + "&omcId=" + omcid;
//	App.util.addNewMainTab(planURL,'关联随访计划');
//}


function layout(){
	var store=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:baseUrl}),
		reader:new Ext.data.JsonReader({root:''},[
			{name:'id'},
			{name:'patientId'},
			{name:'outWriteTime'},
			{name:'outTitle'},
			{name:'outContent'},
			{name:'outSubmiter'},
			{name:'outSubmitTime'},
			{name:'outChecker'},
			{name:'outCheckTime'},
			{name:'outVerifyStatus'},
			{name:'outOldOperation'},
			{name:'outModifyTime'},
			{name:'biaoben'},
			{name:'outSuiFangStartTime'},
			{name:'groupName'},
			{name:'outFollowCycle'},
			{name:'outFollowContent'},
			{name:'memo'},
			{name:'suiFangFlag'},
			{name:'outWriteTime'},
			{name:'outSuiFangStartTime'},
			{name:'outMainDoctor'},
			{name:'outTime'},
			{name:'actdate'} 
		]),
		sortInfo:{field:'actdate',direction:'ASC'},
		sortData:function(f, direction){
            direction = direction || 'ASC';   
            var st = this.fields.get(f).sortType;   
            var fn = function(r1, r2 , store){   
                var v1 = st(r1.data[f]), v2 = st(r2.data[f]);   
                if(typeof(v1) == 'string' && typeof(v2) == 'string'){                                 
					var al=Date.parseDate(v1,'Y-m-d');
					var bl=Date.parseDate(v2,'Y-m-d');
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
	   			_item.data['orderNo'] = _index<10?(_index):_index;
	   			
	   		});
	   	},
	   	'load':function(){//页面初始化 完成 进行
	   		var view=Ext.getCmp('data-view');
	   		$('div.data-item').each(function(){
				if($(this).find('select').eq(0).html() == ""){
					if(outNameList.length>0){
						outName = outNameList[0];
						for(var i=0;i<outNameList.length;i++){
							$(this).find('select').eq(0).append('<option value='+outNameList[i]+'>'+outNameList[i]+'</option>');
						}
					}
					if(view.getRecord(this).data.outTitle == "" || view.getRecord(this).data.outTitle ==null){
						$(this).find('select').eq(0).attr('value',outNameList[0]);
						Ext.getCmp('data-view').getRecord(this).data.outTitle=outNameList[0];
					}else{
						$(this).find('select').eq(0).attr('value',view.getRecord(this).data.outTitle);
					}
				}
				if(view.getRecord(this).data.biaoben =="" || view.getRecord(this).data.biaoben ==null){
					$(this).find('select').eq(1).attr('value',"0");
					Ext.getCmp('data-view').getRecord(this).data.biaoben="0";
				}else{
					$(this).find('select').eq(1).attr('value',view.getRecord(this).data.biaoben);
				}
				if(view.getRecord(this).data.outFollowCycle <=0){
					Ext.getCmp('data-view').getRecord(this).data.suiFangFlag ="false";
					$(this).find('tr').eq(4).children("td").css("display",'none');
					$(this).find('tr').eq(5).children("td").css("display",'none');
					$(this).find('tr').eq(5).children("td").find('input').eq(0).val("");
					$(this).find('tr').eq(6).children("td").css("display",'none');
					$(this).find('tr').eq(7).children("td").next().css("padding-left",'510px');
				}
				if(view.getRecord(this).data.outSubmiter > 0){
					$(this).find('select').eq(0).attr('disabled',true);
					$(this).find('select').eq(1).attr('disabled',true);
				}
				if(view.getRecord(this).data.outWriteTime == "" || view.getRecord(this).data.outWriteTime == null){
					$(this).find('input').eq(0).val(currentTime());
					Ext.getCmp('data-view').getRecord(this).data.outWriteTime=currentTime(); 
				}
				if(view.getRecord(this).data.outMainDoctor !=""){
					$(this).find('input[type="button"]').eq(0).hide();
					$(this).find('tr').eq(3).children("td").css("display",'none');
				}
			});
	   	}
	   }
	});
	store.baseParams={method:'findAllOutOrMergencyCase',id:PID,outNameList:outNameList,outRegno:outRegno};
	store.load();
	var tpl = new Ext.XTemplate(
	
		'<tpl for=".">',
		//苏勇 加入onclick
		'<div class="data-item" onclick="talert({id})"><div>',
			'<table width="100%" border=0 cellpadding=0 cellspacing=2 name="table{id}">',
				'<tr>',
					'<td><b>&nbsp;&nbsp;第{orderNo}次门诊</b></td>',
					'<td><b>&nbsp;&nbsp;挂号时间:{actdate}</b></td>',
					'<td><select {readonly} name="outTitle" value="{outTitle}" onchange="TitleChange(this)"></select></td>',
					'<td align="right">记录时间：<input type="text" name="outWriteTime" readonly="readonly" value="{outTime}" onchange="TimeChange(this)"/>',
					'<img {readonly} src="../../PUBLIC/images/calendar.bmp" style="cursor:pointer" onclick="ShowTimePicker(this)"/>',
					'</td>',
				'</tr>',
				'<tr><td colspan="2" style="padding-left:5px"><a href="" onclick="linkPlan({id})"><font color="red"><B>执行随访计划</B></font></a></td></tr>',
				'<tr>',
					'<td class="content" colspan=4><textarea {readonly} name="outContent" onchange="ContentChange(this)">{outContent}</textarea></td>',
				'</tr>',
				'<tr><td colspan="2" style="padding-left:5px">预留标本：<select name="biaoben" {readonly} onchange="biaoben(this)" value="{biaoben}"><option value="0">是</option><option value="1">否</option></select></td></tr>',
			'</table>',
			'<table width="100%" border=0 cellpadding=0 cellspacing=2 name="tableSuiFang{id}">',
			'<tr><td colspan="2" style="padding-left:5px"><a href="" onclick="goToPlan()"><input type="hidden" name="suiFangFlag" value="{suiFangFlag}" /><font color="red"><B>设置下次随访</B></font></a></td></tr>',
			'<tr><td>&nbsp;起始日期：<input type="text" name="outSuiFangStartTime" {readonly} value="{outSuiFangStartTime}" /></td>',
			'<td>会员组别：<input type="text" name="groupName" value="{groupName}" {readonly} /><input type="button" name="xiugai" value="修改组别" onclick="selectGroup(this)" style="height:20px;align:center"/></td>',
			'</tr>',
			'<tr><td>&nbsp;随访周期：<input type="text" name="outFollowCycle" {readonly} style="width:15px" value="{outFollowCycle}" />月</td><td>检查项目：<input type="text" {readonly} name="outFollowContent" style="width:360px" value="{outFollowContent}" onchange="outFollowContent(this)" /></td></tr>',
//			'<tr><td colspan="2" style="padding-left:24px">&nbsp;备注：<input type="textarea" {readonly} name="memo" value="{memo}" style="width:588px;" onchange="memo(this)"/></td></tr>',
			//'<tr><td colspan="2" style="padding-left:5px"><a href="" onclick="goToPlan({id})"><font color="red"><B>设置下次随访</B></font></a></td></tr>',
			'<tr><td colspan="2" style="padding-left:5px"><a href="" onclick="goToPlan()"><font color="red"><B>设置下次随访</B></font></a></td></tr>',
			'<tr><td></td><td style="padding-left:500px">',
				'<input type="hidden" name="outVerifyStatus" value="{outVerifyStatus}"/>',	
				'<input type="hidden" name="outSubmiter" value="{outSubmiter}"/>',
				'<input type="hidden" name="outChecker" value="outChecker"/>',
				'<input type="hidden" name="outSubmitTime" value="{outSubmitTime}"/>',
				'<input type="hidden" name="outCheckTime" value="{outCheckTime}"/>',
				'<input type="hidden" name="outOldOperation" value="{outOldOperation}"/>',
				'<input type="hidden" name="outModifyTime" value="{outModifyTime}"/>',
				//'创建{outCreateTime}更新{outModifyTime}&nbsp;',
				//'审核状态{verifyStatus_show}&nbsp;&nbsp;&nbsp;&nbsp;',
				'医生签字',
				//'<input readonly="readonly" style="width:80px" value="{checker_show}">',
				//'<button onclick="CheckSign(this,{id})" class="sign" {check_btn_active}>{check_btn_text}</button>',
				'<input readonly="readonly" style="width:72px;" value="{submiter_show}">',
				'<button onclick="SubmitSign(this,{id})" class="sign" {submit_btn_active}>{submit_btn_text}</button>',
				'</td></tr>',
			'</table>',
		'</div></div>',
		'</tpl>'
	);
	var dataview=new Ext.DataView({
		id:'data-view',
		store:store,
		tpl:tpl,
		autoHeight:true,
		autoWidth:true,
		autoscroll:true,
		singleSelect:true,
		overClass:'data-item-over',
        itemSelector:'div.data-item',
        emptyText: 'No record to display',
		prepareData:function(data,recordIndex){
			var USER=top.USER;
			data.submit_btn_text='签字';
			data.check_btn_text='签字';
			data.submit_btn_active='';
			data.check_btn_active='';
			data.readonly='';
			var imgSrc='3D_ball_gray.png';
			var imgAlt='未提交审核';
			if(data.outVerifyStatus==0){
				data.check_btn_active='disabled';
			}else if(data.outVerifyStatus==1){
				imgSrc='3D_ball_yellow.png';
				imgAlt='已提交审核';
				data.submit_btn_active='disabled';
				if(USER.id==data.outSubmiter){
					data.submit_btn_active='';
					data.submit_btn_text='撤销';
				}
				data.readonly='readonly="readonly"';
			}else if(data.outVerifyStatus==2){
				imgSrc='3D_ball_green.png';
				imgAlt='审核通过';
				data.submit_btn_active='disabled';
				data.check_btn_active='disabled';
				if(USER.id=data.outChecker){
					data.check_btn_active='';
					data.check_btn_text='撤销';
				}
				data.readonly='readonly="readonly"';
			}else if(data.outVerifyStatus==3){
				imgSrc='3D_ball_red.png';
				imgAlt='审核未通过';
				data.submit_btn_active='disabled';
				data.check_btn_active='disabled';
				if(USER.id=data.outChecker){
					data.check_btn_active='';
					data.check_btn_text='撤销';
				}
				if(USER.id==data.outSubmiter){
					data.submit_btn_active='';
					data.submit_btn_text='重签';
				}
			}
			
			
			data.verifyStatus_show=
				'<img align="absbottom" width="22" height="22" alt="'+imgAlt+'" src="../../PUBLIC/images/'+imgSrc+'"/>'+
				'<span style="color:red">'+imgAlt+'</span>';
			if(data.outSubmiter&&data.outSubmiter>0){
				data.submiter_show=data.outMainDoctor;
			}
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
				dataUrl:baseUrl+'?method=outOrMergencyCase_treeNodes&id='+PID,
				
				root:{
					nodeType:'async',
					text:'字典树',
					draggable:false,
					id:-1
				},
				rootVisible:false
			},{
				region:'center',
				id:'outPanel',
				xtype:'panel',
				border:false,
				layout:'fit',
				autoScroll:true,
				tbar:[
					'-',{
						text:'化验检查',handler:ShowLabExamenation
					},'-',{
						text:'保存',handler:SaveData
					},'-',{
					
						text:'打印',handler:function(){
						
						
							if(printId == null){
								alert("请先点击需要打印的记录！");
								return false;
							}
							//liugang 2011-08-31 start 质控一级医生必须签字
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/OutOrMergencyCaseAction.do',
								params:{
									method:'checkIsSubmiter',
									id:printId
								},
								sync:true,
								scope:this,
								success:function(_response){
									var res=Ext.decode(_response.responseText);
									if(res.success){
										alert("请签字后打印！");
										return;
									}else{
										window.open(App.App_Info.BasePath+'/OutOrMergencyCaseAction.do?method=outcase_Print&printId='+printId+"&PID="+PID);
									}
								}
							});
						}
					},					
					'->',{
						text:'返回',handler:function(){
						//liugang 2011-08-06 修改
							location.href=App.App_Info.BasePath+'/module/Followup/outCaseList.html?id='+PID+'&HISPID='+HISPID+'&flag='+flag;
						}
					},'&nbsp;&nbsp;'
				],
				bodyStyle:'padding-left:5px;',
				listeners:{
					render:function(){
						var div=document.createElement('div');
						div.id = 'header';
						div.style.width=700;
						div.style.position = 'relative';
						this.body.dom.appendChild(div);

						registrationPageHead(PID,'门诊记录','#header');
						
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
function addNewRecording(outName){
	var store=Ext.getCmp('data-view').getStore();
	store.add(new Ext.data.Record({
		id:-1,
		patientId:PID,
		outWriteTime:currentTime(),
		outTitle:outName,
		outContent:'',
		outSubmiter:'',
		outSubmitTime:'',
		outChecker:'',
		outCheckTime:'',
		outModifyTime:'',
		outVerifyStatus:0,
		biaoben:0,
		outSuiFangStartTime:currentTime(),
		groupName:'',
		outFollowCycle:'',
		outFollowContent:'',
		memo:'',
		suiFangFlag:'false',
		outWriteTime:currentTime(),
		outSuiFangStartTime:currentTime()
	}));
	scrollTo(-1);
}

function ShowTimePicker(_this){
	var el=Ext.get(_this).prev('input',true);
	if(_this.readonly){
		alert('已提交或通过审核，不能修改。');
		return;
	}
	WdatePicker({el:el,dateFmt:'yyyy-MM-dd HH:mm:ss'});
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
		params.method='updateSubmiterById';
		params.id=_id;
		params.outSubmiter=top.USER.id;
		callback=function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				alert('成功提交审核。');
				Ext.apply(Ext.getCmp('data-view').getRecord(Ext.get(_this).findParentNode('div.data-item')).data,Ext.decode(res.data));
				Ext.getCmp('data-view').refreshNode(Ext.getCmp('data-view').getSelectedIndexes()[0]);
				var outName;
		   		var view=Ext.getCmp('data-view');
		   		$('div.data-item').each(function(){
					if($(this).find('select').eq(0).html() == ""){
						if(outNameList.length>0){
							outName = outNameList[0];
							for(var i=0;i<outNameList.length;i++){
								$(this).find('select').eq(0).append('<option value='+outNameList[i]+'>'+outNameList[i]+'</option>');
							}
						}
						$(this).find('select').eq(0).attr('value',view.getRecord(this).data.outTitle);
						$(this).find('select').eq(0).attr('disabled',true);
					}
					$(this).find('select').eq(1).attr('value',view.getRecord(this).data.biaoben);
					$(this).find('select').eq(1).attr('disabled',true);
					$(this).find('input[type="button"]').eq(0).hide();
					$(this).find('tr').eq(3).children("td").css("display",'none');
					if(view.getRecord(this).data.outFollowCycle == "" || view.getRecord(this).data.outFollowCycle == null || view.getRecord(this).data.outFollowCycle == undefined){
						$(this).find('tr').eq(4).children("td").css("display",'none');
						$(this).find('tr').eq(5).children("td").css("display",'none');
						$(this).find('tr').eq(6).children("td").css("display",'none');
						$(this).find('tr').eq(7).children("td").next().css("padding-left",'510px');
					}
				});
			}else{
				alert('提交审核失败。');
			}
		}
	}else if(_this.innerText=='撤销'){
		if(!confirm('确定要撤销提交的审核？'))return;
		params.method='updateSubmiterById_cancelSubmit';
		params.id=_id;
		callback=function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				alert('成功撤销提交审核。');
				Ext.apply(Ext.getCmp('data-view').getRecord(Ext.get(_this).findParentNode('div.data-item')).data,Ext.decode(res.data),{submiter_show:''});
				Ext.getCmp('data-view').refreshNode(Ext.getCmp('data-view').getSelectedIndexes()[0]);
				var outName;
		   		var view=Ext.getCmp('data-view');
		   		$('div.data-item').each(function(){
					if($(this).find('select').eq(0).html() == ""){
						if(outNameList.length>0){
							outName = outNameList[0];
							for(var i=0;i<outNameList.length;i++){
								$(this).find('select').eq(0).append('<option value='+outNameList[i]+'>'+outNameList[i]+'</option>');
							}
						}
						$(this).find('select').eq(0).attr('value',view.getRecord(this).data.outTitle);
					}
					$(this).find('select').eq(1).attr('value',view.getRecord(this).data.biaoben);
					if(view.getRecord(this).data.outFollowCycle == "" || view.getRecord(this).data.outFollowCycle == null || view.getRecord(this).data.outFollowCycle == undefined){
						$(this).find('tr').eq(4).children("td").css("display",'none');
						$(this).find('tr').eq(5).children("td").css("display",'none');
						$(this).find('tr').eq(6).children("td").css("display",'none');
						$(this).find('tr').eq(7).children("td").next().css("padding-left",'510px');
					}
				});
			}else{
				alert('提交审核撤销失败。');
			}
		}
	}else if(_this.innerText=='重签'){
		if(!confirm('确定要重新签入您的名字<'+top.USER.name+'>？'))return;
		params.method='updateOutOrMergencyCourse_resubmit';
		params.id=_id;
		params.outSubmiter=top.USER.id;
		callback=function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				alert('重签成功。');
				Ext.apply(Ext.getCmp('data-view').getRecord(Ext.get(_this).findParentNode('div.data-item')).data,Ext.decode(res.data),{checker_show:'',outCheckTime:''});
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
function talert(_id){
	printId=_id;
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
			title:'门诊记录审核',
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
						var v=this.ownerCt.items.get(0).form.findField('outVerifyStatus').getValue();
						Ext.Ajax.request({
							url:baseUrl,
							params:{
								method:'updateOutOrMergencyCourse_check',
								outVerifyStatus:v,
								id:_id,
								outChecker:top.USER.id
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
						id:'outVerifyStatus',
						name:'outVerifyStatus',
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
							{boxLabel:'通过',inputValue:2,checked:true,name:'outVerifyStatus'},
							{boxLabel:'不通过',inputValue:3,checked:false,name:'outVerifyStatus'}
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
		if(confirm('您是否需要将本次化验检查数据导入到门诊记录中？')){
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
	
	var returnValue = window.showModalDialog('../InHospitalCase/Liver/CheckReport/combinationListofOutCaseMain.html?patientId=' + PID + '&KID=' + KID + '&dailyRecId=' + _record.data.id,'','dialogWidth=950px;dialogHeight=650px');
	if(returnValue){
		var _text = returnValue.retValue;
		$('input[name="outOldOperation"]').val(returnValue.outOldOperation);																
		if(confirm('您是否需要将本次化验检查数据导入到门诊记录中？')){
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
		view.getRecord(this).data.outOldOperation = $('input[name="outOldOperation"]').val();
		var _data=view.getRecord(this).data;
//		if($('textarea[name="outContent"]').val() == ""){
//			alert("请填写本次门诊病历");
//			return;
//		}
		if(_data.suiFangFlag == "true" && $('input[name="groupName"]').val() == ""){
			alert("请设置随访组别");
			return;
		}
		_data.outSubmiter ='';
		_data.outSubmitTime ='';
		_data.outChecker ='';
		_data.outCheckTime ='';
		json.push(_data);
	});
	if(json.length==0){
		alert('无数据需要保存。');
		return;
	}
	Ext.Ajax.request({
		url:baseUrl,
		params:{
			method:'save',
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
	_record.data.outWriteTime=_val;
	$(_div).children('div').removeClass('modify');
	$(_div).children('div').addClass('modify');
}
function TitleChange(_this){
	var _val=_this.value;
	var _div=Ext.get(_this).findParentNode('div.data-item');
	var _record=Ext.getCmp('data-view').getRecord(_div);
	_record.data.outTitle=_val;
	$(_div).children('div').removeClass('modify');
	$(_div).children('div').addClass('modify');
}
function ContentChange(_this){
	var _val=_this.value;
	var _div=Ext.get(_this).findParentNode('div.data-item');
	var _record=Ext.getCmp('data-view').getRecord(_div);
	_record.data.outContent=_val;
	$(_div).children('div').removeClass('modify');
	$(_div).children('div').addClass('modify');
}

var printId = null;
function scrollTo(_id){

	printId = _id;
	var _panel=Ext.getCmp('data-view');
	var _scrollNode=_panel.el.dom.parentNode.parentNode;
	var _tables=_panel.el.query('table');
	Ext.each(_tables,function(_item){
		if(_item.name==('table'+_id)){
			var _offsets=Ext.get(_item).getOffsetsTo(_scrollNode);
			_scrollNode.scrollTop+=_offsets[1];
			_panel.select(_item.parentNode.parentNode);
		}
		if(_item.name==('tableSuiFang'+_id)){
			var _offsets=Ext.get(_item).getOffsetsTo(_scrollNode);
			_scrollNode.scrollTop+=_offsets[1];
			_panel.select(_item.parentNode.parentNode);
		}
		if($(this).find('select').eq(0).html() == ""){
			var outName;
			if(outNameList.length>0){
				outName = outNameList[0];
				for(var i=0;i<outNameList.length;i++){
					$(this).find('select').eq(0).append('<option value='+outNameList[i]+'>'+outNameList[i]+'</option>');
				}
			}
			var _div=Ext.get(this).findParentNode('div.data-item');
			var _record=Ext.getCmp('data-view').getRecord(_div);
			_record.data.outTitle = outName;
		}
	});
}

function ShowTime(_this){
	var el=Ext.get(_this).prev('input',true);
	WdatePicker({el:el,dateFmt:'yyyy-MM-dd HH:mm'});
}
//选择组别
function selectGroup(_this){
	var _div=Ext.get(_this).findParentNode('div.data-item');
	var _returnObj = window.showModalDialog('../../module/biomedical/Group2.html', 'dialogWidth=400px;dialogHeight=600px');
	$(_this).prev().val(_returnObj.name);
	$(_this).parent().parent().next().find('input[name="outFollowCycle"]').eq(0).val(_returnObj.cycle);//此处不能随访周期命名是由上个页面决定
	$(_this).parent().parent().next().find('input[name="outFollowContent"]').eq(0).val(_returnObj.content);
	var _div=Ext.get(_this).findParentNode('div.data-item');
	var _record=Ext.getCmp('data-view').getRecord(_div);
	_record.data.groupName = _returnObj.name;
	_record.data.outFollowCycle = _returnObj.cycle;
	_record.data.outFollowContent = _returnObj.content;
	_record.data.suiFangFlag = "true";
	$(_div).children('div').removeClass('modify');
	$(_div).children('div').addClass('modify');
}

//随访项目
function outFollowContent(_this){
	var _val=_this.value;
	var _div=Ext.get(_this).findParentNode('div.data-item');
	var _record=Ext.getCmp('data-view').getRecord(_div);
	_record.data.outFollowContent=_val;
	$(_div).children('div').removeClass('modify');
	$(_div).children('div').addClass('modify');
}
//备注
function memo(_this){
	var _val=_this.value;
	var _div=Ext.get(_this).findParentNode('div.data-item');
	var _record=Ext.getCmp('data-view').getRecord(_div);
	_record.data.memo=_val;
	$(_div).children('div').removeClass('modify');
	$(_div).children('div').addClass('modify');
}

//标本 
function biaoben(_this){
	var _val=_this.value;
	var _div=Ext.get(_this).findParentNode('div.data-item');
	var _record=Ext.getCmp('data-view').getRecord(_div);
	
	var suiFangFlag= _record.data.suiFangFlag;
	_record.data.biaoben=_val;
	_record.data.suiFangFlag=suiFangFlag;
	$(_div).children('div').removeClass('modify');
	$(_div).children('div').addClass('modify');
}

var showOrNot = false;

function displayOrNone(_this){
	var _div=Ext.get(_this).findParentNode('div.data-item');
	var _record=Ext.getCmp('data-view').getRecord(_div);
	var outSubmiter = $(_this).parent().parent().next().next().next().next().children("td").next().find("input").eq(7).val();
	if(outSubmiter != "" && outSubmiter != null){
		showOrNot = false;
		_record.data.suiFangFlag = "false";
		$(_this).parent().parent().next().children("td").css("display",'none');
		$(_this).parent().parent().next().next().children("td").css("display",'none');
		$(_this).parent().parent().next().next().next().children("td").css("display",'none');
		$(_this).parent().parent().next().next().next().next().children("td").next().css("padding-left",'510px');
	}else{
		if(showOrNot){
			showOrNot = false;
			_record.data.suiFangFlag = "false";
			$(_this).parent().parent().next().children("td").css("display",'none');
			$(_this).parent().parent().next().next().children("td").css("display",'none');
			$(_this).parent().parent().next().next().next().children("td").css("display",'none');
			$(_this).parent().parent().next().next().next().next().children("td").next().css("padding-left",'510px');
		}else{
			showOrNot = true;
			_record.data.suiFangFlag = "true";
			var timeNow = $(_this).parent().parent().parent().parent().prev().find("input").eq(0).val();
			$(_this).parent().parent().next().children("td").find("input").eq(0).val(timeNow);
			$(_this).parent().parent().next().children("td").css("display",'block');
			$(_this).parent().parent().next().next().children("td").css("display",'block');
			$(_this).parent().parent().next().next().next().children("td").css("display",'block');
			$(_this).parent().parent().next().next().next().next().children("td").next().css("padding-left",'260px');
		}
	}
	$(_div).children('div').removeClass('modify');
	$(_div).children('div').addClass('modify');
}