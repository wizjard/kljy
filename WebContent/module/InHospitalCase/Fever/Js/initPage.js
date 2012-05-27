	
	var maxHeight=0;
	//子窗口调用,根据iframe高度设置panel高度函数
	function setPanelHeight(_iframeHeight){
		if(_iframeHeight>maxHeight){
			maxHeight=_iframeHeight;
			Ext.getCmp('con-panel').setHeight(730+_iframeHeight-391);
		}
	}
	
	/*构造组合页面函数*/
	function loadPage(tarGet){
		var main=new Ext.Panel({
		el:'container',
		id:'con-panel',
		width:785,
		height:730,
		collapsible:true,
		titleCollapse:true,
		title:'现病史-详细',
		layout:'border',
		items:[
		  {
			region:'north',
			height:25,
			tbar:[
			      {
					text:'新增记录',iconCls:'table_add',handler:function(){
			    	 	var panel=addTab(-1,null,tarGet);
			    	  	var tab=Ext.getCmp('tabpanel');
			    	  	tab.setActiveTab(panel);
					}
				  },'-',{
					text:'删除当前记录',iconCls:'table_delete',handler:function(){
					  //deleteTab();
					}
				  }
			]
		  },{
			region:'center',
			xtype:'tabpanel',
			id:'tabpanel',
			height:maxHeight,
			activeTab:0,
			defaults:{
				autoScroll:true
			},
			items:[
			       {
					title:'首次发病记录',
					data:null,
					setData:function(_d){
						this.data=_d;
					},
					getData:function(){
						return this.data;
					},
					html:'<iframe myId="-1" frameborder=0 scroll=no width="100%" height="100%" src="'+tarGet+'?KID=-1"></iframe>',
					listeners:{
						'render':function(){
							//getDataFromSever(this);
						}
					}
				}
			]
		  },{
			region:'south',
			title:'目前状况',
			height:250,
			listeners:{
				'render':function(){
					Ext.get('table').appendTo(this.body);
				}
			},
			bbar:[
			      '->',{
					text:'组合内容',iconCls:'table_save',handler:function(){
						var textarea='';
						Ext.getCmp('tabpanel').items.each(function(item,i){
							alert('item：'+JSON.stirngify(item)+'\r'+i);
							var data=null;
							if(item.body){
								alert('执行item.bodty');
								var iframe = item.body.query('iframe');
								alert(iframe.html());
								if(iframe.length==0){
									
								}else{
									alert('获取页面组合值！');
									data = iframe[0].contentWindow.getPageValues();
								}
							}else{
								data=item.getData();
							}
							if(data.composeContent.length>0){
								if(i==0)
									textarea+=data.composeContent+'\n';
								else
									textarea+='    '+data.composeContent+'\n';
							}
						});
						var cstatus=ComposeCurrentStatus();
						if(cstatus.length>0){
							textarea+=cstatus+'\n';
						}
						if(textarea.length>0)
							$('*[name="content"]').val(textarea.substr(0,textarea.length-1));
						Ext.getCmp('con-panel').collapse(true);
					}
				  },'&nbsp;'
			]
		  }
		]
		});
		main.render();
	}

	function addTab(id,data,tarGet){
		var panel=new Ext.Panel({
			title:'新增',
			data:data,
			setData:function(_d){
				this.data=_d;
			},
			getData:function(){
				return this.data;
			},
			html:'<iframe myId="'+id+'" frameborder=0 scroll=no width="100%" height="100%" src="'+tarGet+'"?KID='+id+'"></iframe>'
		});
		var tab=Ext.getCmp('tabpanel');
		tab.add(panel);
	  	reNameTab();
	  	return panel;
	}
	
	function reNameTab(){
		var tab=Ext.getCmp('tabpanel');
		tab.items.each(function(item,i){
			if(i>0){
				item.setTitle('发病记录'+(i+1));
			}
		});
	}
	
	function deleteTab(){
		var tab=Ext.getCmp('tabpanel');
		var panel=tab.getActiveTab();
		if(panel.title=='首次发病记录'){
			alert('不能删除首次记录。');
			return;
		}
		var iframe=panel.body.query('iframe')[0];
		var id=iframe.myId;
		if(!confirm('确定要删除当前显示的记录？'))return;
		if(id==-1){
			tab.remove(panel,true);
			tab.setActiveTab(tab.items.get(tab.items.length-1));
			reNameTab();
		}else{
			$.post(
				App.App_Info.BasePath+'/InHospitalCase/Fever.do',
				{
					method:'PreDeleteById',
					id:id
				},function(data){
					if(data.success){
						tab.remove(panel,true);
						tab.setActiveTab(tab.items.get(tab.items.length-1));
						reNameTab();
					}else{
						alert('服务端删除数据出错。');
					}
				},'json');
		}
	}
	
	function ComposeCurrentStatus(){
		var json = FormUtil.getFormValues('#table');
		var rst='';
		var tempValue='';
		rst+='    患者自发病以来，';
		rst+='精神'+getRowValue('spiritStatu',json.spiritStatu)+'，';
		tempValue=json.positiveSysptom;
		tempValue=tempValue.length>0?('无'+tempValue+'，'):'';
		rst+=tempValue;
		rst+='食量'+getRowValue('eatVolume',json.eatVolume)+'，';
		rst+='睡眠'+getRowValue('sleep',json.sleep)+'，';
		rst+='小便'+getRowValue('piss',json.piss)+'，';
		rst+='大便'+getRowValue('excrement',json.excrement)+'，';
		tempValue=getRowValue('bodyWeight',json.bodyWeight);
		if(tempValue=='无变化'){
			rst+='体重无变化，';
		}else if(tempValue=='增加'){
			tempValue=json.bodyWeight_time+getRowValue('bodyWeight_timeUnit',json.bodyWeight_timeUnit)+'增加'+json.bodyWeight_kg+'Kg，';
			rst+='体重'+tempValue;
		}else if(tempValue=='减轻'){
			tempValue=json.bodyWeight_time+getRowValue('bodyWeight_timeUnit',json.bodyWeight_timeUnit)+'减轻'+json.bodyWeight_kg+'Kg，';
			rst+='体重'+tempValue;
		}
		rst= rst.substr(0,rst.length-1)+'。';
		return rst;
	}

