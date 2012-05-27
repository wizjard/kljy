var PID = top.Member.patientId;
Ext.onReady(function(){
		var loader = new Ext.tree.TreeLoader({
            dataUrl: App.App_Info.BasePath+'/biomedical.do?method=findMemberHealthRecordByPatientId&pid='+PID
        });
        var root = new Ext.tree.AsyncTreeNode({
            id: 'root',
            text: '健康档案列表'
        });

	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'west',
				id:'tree',
				width:160,
				xtype:'treepanel',
				root:root,
	            title: '  健康档案列表',
	            rootVisible: false,
	            autoScroll: true,
	            useArrows: true,
	            border:false,
	            onlyLeafCheckable: true,
	            containerScroll: true,
	            loader: loader,
//	            tbar: [{
//	                text: '  新增',
//	                handler:function(){
//	                	if(window.confirm("您确定要为该会员记录健康档案吗？")){
//	                		var newKid;
//		                	Ext.Ajax.request({
//		                		url:App.App_Info.BasePath+'/biomedical.do?method=saveMemberHealthRecordByPatientId&pid='+PID,
//		                		sync:true,
//		                		success:function(response){
//		                			var result = Ext.decode(response.responseText);
//		                			if(result.success){
//		                			 	var data = Ext.util.JSON.decode(result.data);
//		                			 	newKid = data.kid;
//		                			}
//		                		}
//		                	});
//		                	Ext.getCmp("tree").getRootNode().reload();
//		                	Ext.get('iframe').dom.src='../../../module/InHospitalCase/Liver/InHspRecLook.html?KID='+newKid+'&PID='+PID+'&caseModuleId=liver&back=1';
//	                	}
//	                }
//	            }],
	            listeners:{
	            	render:function(){
	            		root.expand(true,true,function(node){
	            			if(node.childNodes.length>0){
	            				var newKid;
			                	Ext.Ajax.request({
			                		url:App.App_Info.BasePath+'/biomedical.do?method=firstFindMemberHealthRecordByPatientId&pid='+PID,
			                		sync:true,
			                		success:function(response){
			                			var result = Ext.decode(response.responseText);
			                			if(result.success){
			                			 	var data = Ext.util.JSON.decode(result.data);
			                			 	newKid = data.kid;
			                			}
			                		}
			                	});
						    	Ext.get('iframe').dom.src='../../../module/InHospitalCase/Liver/InHspRecLook.html?KID='+newKid+'&PID='+PID+'&caseModuleId=liver&back=1';
						    }
						    else{
						    	Ext.get('iframe').dom.src='';
						    }
	            		});
	            	}
	            }
			},{
				region:'center',
				border:false,
				autoScroll: true,
				listeners:{
					render:function(){
						Ext.DomHelper.append(this.body,{
							id:'iframe',
							tag:'iframe',
							width:'100%',
							height:'100%',
							frameborder:0,
							scrolling:'no',
							src:''
						});
					}
				}
			}
		]
	});
	loadPatSimInfo();
});

function scrollTo(_kid){
	Ext.get('iframe').dom.src='../../../module/InHospitalCase/Liver/InHspRecLook.html?KID='+_kid+'&PID='+PID+'&caseModuleId=liver&back=1';
}

//function loadPatSimInfo(){
//	var _div=Ext.get('PatSimInfo-DIV');
//	if (!_div) {
//		Ext.DomHelper.append(Ext.getBody(), {
//			tag: 'div',
//			id: 'PatSimInfo-DIV'
//		});
//		_div=Ext.get('PatSimInfo-DIV');
//	}
//	_div.load({
//		url:App.App_Info.BasePath+'/module/Patient/PatSimpleInfo.jsp?id='+PID,
//		scripts:true,
//		text:'正在获取病人基本信息......'
//	});
//}