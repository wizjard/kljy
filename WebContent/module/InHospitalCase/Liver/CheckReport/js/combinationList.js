var oldOperation = App.util.getHtmlParameters('oldOperation');
var textAreaValue = '';
var historyObj = new Array();//存放导入的组合项目值，此时的值还没有经过处理
var objForAdd = new Array();//存放导入的组合项目值，此时的值还没有经过处理
var store;
var relatingRecord;
var checkProject;
var reportDate;
var hospitalName;
var patientId = App.util.getHtmlParameters('patientId');
var panel1=new Ext.tree.TreePanel({
			id:'panelforHisRec',
			title:'关联历史记录',
			autoScroll:true,
			containerScroll:true,
			height:540,
			width:240,
			copyNode:null,
			hidden:true,
			setCopyNode:function(_node){
				this.copyNode=_node;
			},
			getCopyNode:function(){
				return this.copyNode;
			},
			dataUrl:App.App_Info.BasePath+'/CheckReport/combinationProject.do?method=getCombinationProject&is_check_add=check&patientId='+patientId,
			root:{
				nodeType:'async',
				text:'组合项目',
				draggable:false,
				id:-1
			},
			rootVisible:false,
			listeners:{
				render:function(){
					//this.getRootNode().expand();
					this.expandAll();
				},						
				click:function(_node,_e){
					var returnValue;
					if(_node.text.substr(0, 1) == '!'){
						checkProject = _node.text.substr(1, _node.text.length);
					}else{
						checkProject = _node.text;
						checkProject = checkProject.replace('*', '');
						checkProject = checkProject.replace('~', '');
						checkProject = checkProject.replace('^', '');
					}	
					reportDate =  _node.parentNode.text;
					if(_node.isLeaf()){
						returnValue = treeNodeClick(_node,'check');
					}
					if(returnValue){	
						//把导入的数据放入数组中:一次向数组添加四个元素，分别是“医院名称”、“报告日期”、“检查项目”和相应的子项详细信息
						hospitalName = returnValue.hospitalName;
			//			alert(hospitalName);
						if(store.getCount() > 0){
							var i = 0;
							while(i < historyObj.length){
								if(historyObj[i + 1] == reportDate && historyObj[i + 2] == checkProject) break;
								i = i + 4;
							}
							if(i == historyObj.length){
								historyObj.push(hospitalName, reportDate, checkProject, returnValue.obj);
							}else{
			//					alert("重复记录将被覆盖");
								historyObj[i] = returnValue.hospitalName;
								historyObj[i + 3] = returnValue.obj;
							}																									
						}else{
							historyObj.push(hospitalName, reportDate, checkProject, returnValue.obj);							
						}
			//			alert('导入后长度:' + historyObj.length);					
						//把导入的数据放入“检查报告详细信息”中：需要组合成字符串
						textAreaValue = '';
						var historyObjStr = historyObjToString(historyObj);	
						var objForAddStr = objForAddToString(objForAdd);
			//			alert(historyObjStr + objForAddStr);
						textAreaValue = historyObjStr + objForAddStr;
						Ext.getCmp('checkReportInfo').setValue(textAreaValue);																
						
						//把导入的数据放入“已关联历史记录”中
						if(store.getCount() > 0){
							var i = 0;
							while(i < store.getCount()){
								if(store.getAt(i).data.reportDate == reportDate && store.getAt(i).data.checkProject == checkProject) break;
								i++;
							}
							if(i == store.getCount())
								store.add(new relatingRecord({reportDate:reportDate,checkProject:checkProject}));																
						}else{
							store.add(new relatingRecord({reportDate:reportDate,checkProject:checkProject}));
						}
					}
				}
			}
});

var panel2=new Ext.tree.TreePanel({
			id:'panelforAdd',
			title:'关联新增',
			autoScroll:true,
			containerScroll:true,
			height:540,
			width:240,
			copyNode:null,
			hidden:true,
			setCopyNode:function(_node){
				this.copyNode=_node;
			},
			getCopyNode:function(){
				return this.copyNode;
			},
			dataUrl:App.App_Info.BasePath+'/CheckReport/combinationProject.do?method=getCombinationProject&is_check_add=add&patientId='+patientId,
			root:{
				nodeType:'async',
				text:'组合项目',
				draggable:false,
				id:-1
			},
			rootVisible:false,
			listeners:{
				render:function(){
					//this.getRootNode().expand();
					this.expandAll();
				},
				click:function(_node,_e){	
					var returnValue;
					checkProject = _node.text;
					if(_node.isLeaf()){
						returnValue = treeNodeClick(_node,'add');
					}
					if(returnValue){
						var reportMonth = returnValue.reportDate.getMonth() + 1;
						reportDate = returnValue.reportDate.getFullYear()+'-'+reportMonth+'-'+returnValue.reportDate.getDate();
						//把导入的数据放入数组中:一次向数组添加四个元素，分别是“医院名称”、“报告日期”、“检查项目”和相应的子项详细信息
						hospitalName = returnValue.hospitalName;
			//			alert(hospitalName);
						if(store.getCount() > 0){
							var i = 0;
							while(i < objForAdd.length){
								if(objForAdd[i + 1] == reportDate && objForAdd[i + 2] == checkProject) break;
								i = i + 4;
							}
							if(i == objForAdd.length){
								objForAdd.push(hospitalName, reportDate, checkProject, returnValue.obj);
							}else{
			//					alert("重复记录将被覆盖");
								objForAdd[i] = returnValue.hospitalName;
								objForAdd[i + 3] = returnValue.obj;
							}																									
						}else{
							objForAdd.push(hospitalName, reportDate, checkProject, returnValue.obj);							
						}
			//			alert('导入后长度:' + historyObj.length);					
						//把导入的数据放入“检查报告详细信息”中：需要组合成字符串
						textAreaValue = '';
						var historyObjStr = historyObjToString(historyObj);	
						var objForAddStr = objForAddToString(objForAdd);
			//			alert(historyObjStr + objForAddStr);
						textAreaValue = historyObjStr + objForAddStr;
						Ext.getCmp('checkReportInfo').setValue(textAreaValue);								
						
						//把导入的数据放入“已关联历史记录”中
						if(store.getCount() > 0){
							var i = 0;
							while(i < store.getCount()){
								if(store.getAt(i).data.reportDate == reportDate && store.getAt(i).data.checkProject == checkProject) break;
								i++;
							}
							if(i == store.getCount())
								store.add(new relatingRecord({reportDate:reportDate,checkProject:checkProject}));																
						}else{
							store.add(new relatingRecord({reportDate:reportDate,checkProject:checkProject}));
						}						
					}
				}
			}
});

//////////////////////////////////////////////////////////////////
	//注意：从新增列表导入数据后，再从历史记录列表中导入数据会在文本框中出现重复记录，虽然在中间列表中不出现，这点应该改进	
///////////////////////////////////////////////////////


Ext.onReady(function(){	
	relatingRecord=Ext.data.Record.create([
		{name:'reportDate'},
		{name:'checkProject'}
	]);
	store=new Ext.data.Store({
		proxy:new Ext.data.MemoryProxy(),
		reader:new Ext.data.ArrayReader({},relatingRecord)
	});
	var sm=new Ext.grid.CheckboxSelectionModel({
		sigleSelect : true
	});   
	new Ext.Viewport({
		layout:"border",
		items:[
			{
				region:"west",
				width:240,
				title:"关联检查报告",
				html:'<div style="width:240px;overflow:auto" id="div1"></div>',
				tbar:[
					{
						pressed:true,
						text:'关联历史记录',
						handler:function(){
							panel1.render('div1');
							panel2.render('div1');
							Ext.getCmp('panelforHisRec').root.reload();										
							panel2.hide();	
							panel1.show();						
						}
					},{
						xtype:"tbseparator"
					},{
						pressed:true,
						text:'关联新增',
						handler:function(){
							panel2.render('div1');
							panel1.render('div1');	
							Ext.getCmp('panelforAdd').root.reload();
							panel1.hide();	
							panel2.show();				
						}
					}
				]
			},{
				region:"center",
				title:"已经关联的历史记录",
				width:180,											
				items:[
					{
						xtype:'panel',
						layout:'column',
						tbar:[
							{
								xtype:"tbfill"
							},{
								pressed:true,
								text:'从列表中移除',
								handler:function(){
									var rows = Ext.getCmp("checkReportList_table").getSelectionModel().getSelections();
									for(i = 0; i < rows.length; i++){
										var j;
										for(j = 0; j < historyObj.length; j = j + 4){
											if(historyObj[j + 1] == rows[i].data.reportDate && historyObj[j + 2] == rows[i].data.checkProject) break;
										}
										if(j != historyObj.length){
											historyObj.splice(j, 4);
				//							alert('historyObj移除后长度:' + historyObj.length);
										}												
										
										var k;
										for(k = 0; k < objForAdd.length; k = k + 4){
											if(objForAdd[k + 1] == rows[i].data.reportDate && objForAdd[k + 2] == rows[i].data.checkProject) break;
										}
										if(k != objForAdd.length){
											objForAdd.splice(k, 4);
				//							alert('objForAdd移除后长度:' + objForAdd.length);
										}																								
										textAreaValue = '';
										store.remove(rows[i]);
									}
									historyObjStr = historyObjToString(historyObj);	
									objForAddStr = objForAddToString(objForAdd);
									textAreaValue = historyObjStr + objForAddStr;
									Ext.getCmp('checkReportInfo').setValue(textAreaValue);										
								}
							}
						],
						items:[
							{
								id:"checkReportList_table",
								xtype: "editorgrid",	
								height:600,										
								sm:sm,
								store:store,
							    columns: [
							    	sm,
							        {header: "报告日期", dataIndex: 'reportDate',width:90},
							        {header: "检查项目", dataIndex: 'checkProject',width:90}
							    ]
							}
						]
					}
				]
			},{
				region:"east",
				width:480,
				title:"检查报告详细信息",
				tbar:[
					{
						xtype:"tbfill"
					},{
						pressed:true,
						text:'确定',
						handler:function(){
							var retValue = Ext.getCmp("checkReportInfo").getValue();
							oldOperation = "";
							store.each(function(relatingRecord){
								oldOperation = oldOperation + "{reportDate:'" + relatingRecord.get('reportDate') + "',checkProject:'" + relatingRecord.get('checkProject') + "'}*****";
							});
							if(oldOperation != ""){
								oldOperation = oldOperation.substr(0, oldOperation.length - 5) + "~~~~~";
								
								for(i = 0; i < historyObj.length; i = i + 4){
									oldOperation = oldOperation + historyObj[i] + "*****" + historyObj[i+1] + "*****" + historyObj[i+2] + "*****";
									for(j = 0; j <  historyObj[i+3].length; j++){
										 oldOperation = oldOperation + historyObj[i+3][j].data.esname + "^^^^^" + historyObj[i+3][j].data.result + "^^^^^" + 
											     historyObj[i+3][j].data.refvalue + "^^^^^" + historyObj[i+3][j].data.unit + "^^^^^" + 
												 historyObj[i+3][j].data.historyResult + "^^^^^" + historyObj[i+3][j].data.historyRatio + "^^^^^"
												 + historyObj[i+3][j].data.projectname + "^^^^^";
									}
									if(historyObj[i+3].length != 0){
										oldOperation = oldOperation.substr(0, oldOperation.length - 5) + "*****";
									}
								}
								if(historyObj.length != 0){
									oldOperation = oldOperation.substr(0, oldOperation.length - 5) + "~~~~~";
								}else{
									oldOperation = oldOperation + "~~~~~";
								}
							
								for(i = 0; i < objForAdd.length; i = i + 4){
									oldOperation = oldOperation + objForAdd[i] + "*****" + objForAdd[i+1] + "*****" + objForAdd[i+2] + "*****";
									for(j = 0; j <  objForAdd[i+3].length; j++){
										 oldOperation = oldOperation + objForAdd[i+3][j].data.esname + "^^^^^" + objForAdd[i+3][j].data.result + "^^^^^" + 
											     objForAdd[i+3][j].data.refvalue + "^^^^^" + objForAdd[i+3][j].data.unit + "^^^^^"
												 + objForAdd[i+3][j].data.projectname + "^^^^^";
									}
									if(objForAdd[i+3].length != 0){
										oldOperation = oldOperation.substr(0, oldOperation.length - 5) + "*****";
									}
								}
								if(objForAdd.length != 0){
									oldOperation = oldOperation.substr(0, oldOperation.length - 5);
								}
							}
							
							window.returnValue={
								retValue:retValue,
								oldOperation:oldOperation
							};
							window.close();
						}
					},{
						xtype:"tbseparator"
					},{
						pressed:true,
						text:'取消',
						handler:function(){
							window.close();
						}
					}
				],
				items:[
					{
						xtype:"panel",
						title:"",
						id:"checkReportInfo"
					},{
						height:550,
						width:480,
						xtype:"textarea",
						cls:"textarea",
						labelWidth:0,										
						labelSeparator:'',
						id:"checkReportInfo"
					}
				]
			}
		]
	});

	if(oldOperation != ""){
		var arr = oldOperation.split("~~~~~");
		var temp = arr[0];
		var _arr = temp.split("*****");
		for(i = 0; i < _arr.length; i++){
			var obj = eval('(' + _arr[i] + ')');
			store.add(new relatingRecord(obj));
		}
		
		temp = arr[1];
		_arr = temp.split("*****");
		for(i = 0; i < _arr.length && _arr.length != 1; i = i + 4){
			historyObj.push(_arr[i],_arr[i+1],_arr[i+2]);

			var subProInfo = _arr[i+3].split("^^^^^");
			var subPro = "[";
			for(j = 0; j < subProInfo.length; j = j + 7){
				var _item = '{data:{esname:"' + subProInfo[j] + '",result:"' + subProInfo[j+1] 
						+ '",refvalue:"' + subProInfo[j+2] + '",unit:"' + subProInfo[j+3] 
						+ '",historyResult:"' + subProInfo[j+4] + '",historyRatio:"' + subProInfo[j+5]
						+ '",projectname:"' + subProInfo[j+6] + '"}},';
				subPro = subPro + _item;
			}
			subPro = subPro.substr(0, subPro.length -1) + "]";
			historyObj.push(eval('(' + subPro + ')'));
		}
		
		temp = arr[2];
		_arr = temp.split("*****");
		for(i = 0; i < _arr.length && _arr.length != 1; i = i + 4){
			objForAdd.push(_arr[i],_arr[i+1],_arr[i+2]);
		
			var subProInfo = _arr[i+3].split("^^^^^");               
			var subPro = "[";
				for(j = 0; j < subProInfo.length; j = j + 5){
				var _item = '{data:{esname:"' + subProInfo[j] + '",result:"' + subProInfo[j+1] 
						+ '",refvalue:"' + subProInfo[j+2] + '",unit:"' + subProInfo[j+3]
						+ '",projectname:"' + subProInfo[j+4] + '"}},';
				subPro = subPro + _item;
			}
			subPro = subPro.substr(0, subPro.length -1) + "]";
			objForAdd.push(eval('(' + subPro + ')'));
		}
	}

	var historyObjStr = historyObjToString(historyObj);	
	var objForAddStr = objForAddToString(objForAdd);
//	alert(historyObjStr + objForAddStr);
	textAreaValue = historyObjStr + objForAddStr;
	Ext.getCmp('checkReportInfo').setValue(textAreaValue);				
});


function treeNodeClick(_node,is_check_add){
	if(is_check_add == 'check' && _node.text != "该病人相关联的历史记录为空")
		 return window.showModalDialog('../CheckReport/checkReport.html?patientId=' + patientId +'&childNode=' + _node.text + '&parentNode=' + _node.parentNode.text + '&is_check_add=' + is_check_add, '', 'dialogWidth=1000px;dialogHeight=700px');
	if(is_check_add == 'add')
		return window.showModalDialog('../CheckReport/addcheckReport.html?patientId=' + patientId +'&childNode=' + _node.text + '&parentNode=' + _node.parentNode.text + '&is_check_add=' + is_check_add, '', 'dialogWidth=1000px;dialogHeight=700px');	
}

function showCheckReportInfo(){
	Ext.getCmp('checkReportInfo').setValue(textAreaValue);
}

function historyObjToString(historyObj){
	var str = '';
	for(i = 3; i < historyObj.length; i = i + 4){
		objToString = historyObj[i - 1] + '(' + historyObj[i - 2] + '):';
		var temp = '';
		for(j = 0; j < historyObj[i].length; j++){
//			alert(historyObj[i][j].data.projectname);
			if(j < historyObj[i].length - 1){
				temp = temp + historyObj[i][j].data.projectname + " " + 
					   historyObj[i][j].data.esname + " " + historyObj[i][j].data.result + historyObj[i][j].data.unit + " " + 
					   historyObj[i][j].data.refvalue + " " + 
					   historyObj[i][j].data.historyResult + " " + historyObj[i][j].data.historyRatio
					   + ",";
			}else{
				temp = temp + historyObj[i][j].data.projectname + " " + 
					   historyObj[i][j].data.esname + " " + historyObj[i][j].data.result + historyObj[i][j].data.unit + " " + 
					   historyObj[i][j].data.refvalue + " " + 
					   historyObj[i][j].data.historyResult + " " + historyObj[i][j].data.historyRatio;
			}										
		}
		objToString = objToString + temp + ';';
		str = str + objToString;								
	}
	return str;
};

function objForAddToString(objForAdd){
	var str = '';
	for(i = 3; i < objForAdd.length; i = i + 4){
		objToString = objForAdd[i - 1] + '(' + objForAdd[i - 2] + '):';
		var temp = '';
		for(j = 0; j < objForAdd[i].length; j++){
//			alert(objForAdd[i][j].data.projectname);
			if(j < objForAdd[i].length - 1){
				temp = temp + objForAdd[i][j].data.projectname + " " + 
					   objForAdd[i][j].data.esname + " " +  objForAdd[i][j].data.result + objForAdd[i][j].data.unit + " " + 
					   objForAdd[i][j].data.refvalue + ",";
			}else{
				temp = temp + objForAdd[i][j].data.projectname + " " + 
					   objForAdd[i][j].data.esname + " " +  objForAdd[i][j].data.result + objForAdd[i][j].data.unit + " " + 
					   objForAdd[i][j].data.refvalue;
			}										
		}
		objToString = objToString + temp + ';';
		str = str + objToString;								
	}
	return str;
};