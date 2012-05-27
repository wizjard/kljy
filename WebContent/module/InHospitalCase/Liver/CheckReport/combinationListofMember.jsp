<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo"%>
<%
	MemberInfo mem = (MemberInfo) session.getAttribute("MemberInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>我的住院记录</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_ext.js"></script>

<script type="text/javascript">
		var oldOperation = '';
		var textAreaValue = '';
		var historyObj = new Array();//存放导入的组合项目值，此时的值还没有经过处理
		var objForAdd = new Array();//存放导入的组合项目值，此时的值还没有经过处理
		var flag = 0;//标志是哪个TreePanel进行了操作
		var store;
		var relatingRecord;
		var checkProject;
		var reportDate = 'allDate';
		var hospitalName;
		var KID = 0;
		var patientId = <%=mem.getPatient().getId()%>;
		var assayHistoryRecTree=new Ext.tree.TreePanel({
				id:'assayHistoryRecTree',
				title:'关联历史记录',
				autoScroll:true,
				containerScroll:true,
				height:540,
				width:240,
				copyNode:null,
				hidden:true,
				loader: new Ext.tree.TreeLoader({dataUrl:App.App_Info.BasePath+'/CheckReport/combinationProject.do?method=getCombinationProject&is_check_add=check&patientId='+patientId}),
				root:{
					nodeType:'async',
					text:'组合项目',
					draggable:false,
					id:-1
				},
				rootVisible:false,
				listeners:{
					click:function(_node,_e){
						dealHisRec(_node,_e);
					}
				}
		});
		assayHistoryRecTree.loader.on("beforeload", function(treeLoader, node){
					if(node.text != "allDate" && node.text != "组合项目"){
						reportDate = node.text;
					}
					treeLoader.baseParams.reportDate = reportDate;										
				}); 

		var assayNewRecTree=new Ext.tree.TreePanel({
				id:'assayNewRecTree',
				title:'关联新增',
				autoScroll:true,
				containerScroll:true,
				height:540,
				width:240,
				copyNode:null,
				hidden:true,
				dataUrl:App.App_Info.BasePath+'/CheckReport/combinationProject.do?method=getCombinationProject&is_check_add=add&patientId='+patientId,
				root:{
					nodeType:'async',
					text:'组合项目',
					draggable:false,
					id:-1
				},
				rootVisible:false,
				listeners:{
					expandnode:function(node){
						//this.getRootNode().expand();
						//node.expandAll();
						Ext.each(node.childNodes,function(){
							this.expand();
						});
					},
					click:function(_node,_e){	
						addNewRec(_node,_e);
					}
				}
		});
		assayNewRecTree.loader.on("beforeload", function(treeLoader, node){treeLoader.baseParams.reportDate = reportDate;}); 
		
		var assistanceHistoryRecTree=new Ext.tree.TreePanel({
				id:'assayHistoryRecTree',
				title:'关联历史记录',
				autoScroll:true,
				containerScroll:true,
				height:540,
				width:240,
				copyNode:null,
				hidden:true,
				dataUrl:App.App_Info.BasePath+'/CheckReport/combinationProject.do?method=getAssistanceProject&is_check_add=check&patientId='+patientId,
				root:{
					nodeType:'async',
					text:'组合项目',
					draggable:false,
					id:-1
				},
				rootVisible:false,
				listeners:{
					expandnode:function(node){
						//this.getRootNode().expand();
						//node.expandAll();
						Ext.each(node.childNodes,function(){
							this.expand();
						});
					},						
					click:function(_node,_e){
						dealHisRec(_node,_e);
					}
				}
		});
		
		var assistanceNewRecTree=new Ext.tree.TreePanel({
				id:'assistanceNewRecTree',
				title:'关联新增',
				autoScroll:true,
				containerScroll:true,
				height:540,
				width:240,
				copyNode:null,
				hidden:true,
				dataUrl:App.App_Info.BasePath+'/CheckReport/combinationProject.do?method=getAssistanceProject&is_check_add=add&patientId='+patientId,
				root:{
					nodeType:'async',
					text:'组合项目',
					draggable:false,
					id:-1
				},
				rootVisible:false,
				listeners:{
					expandnode:function(node){						
						Ext.each(node.childNodes,function(){
							this.expand();
						});
					},
					click:function(_node,_e){	
						addNewRec(_node,_e);
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
						tbar:createToolbar()					
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
											deleteFromStore();									
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
									//保存本次操作
									saveOperation();
									
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
			//恢复原始操作	
			setValue();																										
		})

		function createToolbar(){
			return new Ext.Toolbar({items:[
				{
					xtype:'tbseparator'
				},{
					text:'刷新',
					tooltip:'刷新当前住院记录',
					handler:function(){						
		//				Ext.getCmp('assayHistoryRecTree').getRootNode().reload();						
						if(flag != 0){
							if(flag == 1){
								reportDate = 'allDate'
								assayHistoryRecTree.getRootNode().reload();
							}else if(flag == 2){
								assayNewRecTree.root.reload();
							}else if(flag == 3){
								assistanceHistoryRecTree.root.reload();
							}else{
								assistanceNewRecTree.root.reload();
							}
						}
					}
				},{
					xtype:'tbseparator'
				},{
					text:'关联历史记录',
					tooltip:'',
					menu:[
						{
							text:'化验检查',
							handler:function(){
								reportDate = 'allDate';
								if(assayNewRecTree.isVisible()){
									assayNewRecTree.hide();	
								}
								if(assistanceHistoryRecTree.isVisible()){
//									assistanceHistoryRecTree.hide();										
								}
								if(assistanceNewRecTree.isVisible()){
									assistanceNewRecTree.hide();
								}
								assayHistoryRecTree.render('div1');
					//			Ext.getCmp('assayHistoryRecTree').root.reload();							
								assayHistoryRecTree.getRootNode().reload();
								assayHistoryRecTree.show();					
								flag = 1;
							}
						}
	//					,{
	//						text:'辅助检查及病理',
	//						handler:function(){
	//							if(assayHistoryRecTree.isVisible()){
	//								assayHistoryRecTree.hide();	
	//							}
	//							if(assayNewRecTree.isVisible()){
	//								assayNewRecTree.hide();	
	//							}
	//							if(assistanceNewRecTree.isVisible()){
	//								assistanceNewRecTree.hide();
	//							}
	//							assistanceHistoryRecTree.render('div1');
	//							assistanceHistoryRecTree.getRootNode().reload();	
	//							assistanceHistoryRecTree.show();
	//							flag = 3;													
	//						}
	//					},
					]
				},{
					xtype:'tbseparator'
				},{
					text:'关联新增记录',
					tooltip:'',
					menu:[
						{
							text:'化验检查',
							handler:function(){					
								if(assayHistoryRecTree.isVisible()){
									assayHistoryRecTree.hide();	
								}
								if(assistanceHistoryRecTree.isVisible()){
//									assistanceHistoryRecTree.hide();										
								}
								if(assistanceNewRecTree.isVisible()){
									assistanceNewRecTree.hide();
								}
								assayNewRecTree.render('div1');
					//			Ext.getCmp('assayNewRecTree').root.reload();
					//			assayNewRecTree.root.reload();
								assayNewRecTree.getRootNode().reload();	
								assayNewRecTree.show();	
								flag = 2;			
							}
						},{
							text:'辅助检查及病理',
							handler:function(){
								if(assayHistoryRecTree.isVisible()){
									assayHistoryRecTree.hide();
								}
								if(assayNewRecTree.isVisible()){
									assayNewRecTree.hide();
								}
								if(assistanceHistoryRecTree.isVisible()){
					//				assistanceHistoryRecTree.hide();
								}
								assistanceNewRecTree.render('div1');
					//			Ext.getCmp('assayNewRecTree').root.reload();
					//			assayNewRecTree.root.reload();
								assistanceNewRecTree.getRootNode().reload();
								assistanceNewRecTree.show();					
								flag = 4;				
							}
						}
					]
				}]
			});
		}

		function treeNodeClick(_node,is_check_add){
			if(is_check_add == 'check' && _node.text != "该病人相关联的历史记录为空" && flag == 1){
				var params = '?patientId=' + patientId + '&is_check_add=' + is_check_add+ '&itemName=' + _node.text 
							+ '&receiveDate=' + _node.attributes.receiveDate + '&sectionNo=' + _node.attributes.sectionNo 
							+ '&testTypeNo=' + _node.attributes.testTypeNo + '&sampleNo=' + _node.attributes.sampleNo 
							+ '&parItemNo=' + _node.attributes.parItemNo;		
				 return window.showModalDialog('../CheckReport/checkReport.html' + params, '', 'dialogWidth=1000px;dialogHeight=700px');
			}				
			if(is_check_add == 'add' && flag == 2)
				return window.showModalDialog('../CheckReport/addcheckReport.html?patientId=' + patientId +'&childNode=' + _node.text + '&parentNode=' + _node.parentNode.text + '&is_check_add=' + is_check_add, '', 'dialogWidth=1000px;dialogHeight=700px');	
			//辅助检查及病理	
			if(_node.text == 'X线平片' && flag == 4)
				return window.showModalDialog('LabExamination/x-line.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');
			if(_node.text == '心电图' && flag == 4)
				return window.showModalDialog('LabExamination/electrocardiogram.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=350px');	
			if(_node.text == 'B超' && flag == 4)
				return window.showModalDialog('LabExamination/b-super.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');	
			if(_node.text == 'CT' && flag == 4)
				return window.showModalDialog('LabExamination/ct.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');	
			if(_node.text == 'MRI' && flag == 4)
				return window.showModalDialog('LabExamination/mri.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');	
			if(_node.text == '消化道造影' && flag == 4)
				return window.showModalDialog('LabExamination/gastrointestinalContrast.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');	
			if(_node.text == '胃镜' && flag == 4)
				return window.showModalDialog('LabExamination/gastroscope.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');	
			if(_node.text == '结肠镜' && flag == 4)
				return window.showModalDialog('LabExamination/colonoscope.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');	
			if(_node.text == '十二指肠镜' && flag == 4)
				return window.showModalDialog('LabExamination/duodenoscope.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');	
			if(_node.text == '胶囊内镜' && flag == 4)
				return window.showModalDialog('LabExamination/endoscopieCapsulaire.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');
			if(_node.text == '尿素呼气试验' && flag == 4)
				return window.showModalDialog('LabExamination/ureaBreathTest.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');
			if(_node.text == '其他检查' && flag == 4)
				return window.showModalDialog('LabExamination/otherCheck.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');
			if(_node.text == '病理检查' && flag == 4)
				return window.showModalDialog('LabExamination/pathology.html?patientId=' + patientId +'&itemName=' + _node.text, '', 'dialogWidth=700px;dialogHeight=450px');		
		}
		
		function historyObjToString(historyObj){
			var str = '';
			for(i = 3; i < historyObj.length; i = i + 5){
				objToString = historyObj[i - 1] + '(' + historyObj[i - 2] + '):';
				var temp = '';
				if(historyObj[i + 1] == 1){
					for(j = 0; j < historyObj[i].length; j++){
						var proName = historyObj[i][j].data.projectname;
						proName = proName.replace("*", "");
						temp = temp + proName;
						if(historyObj[i][j].data.result !=""){
							temp = temp + " " + historyObj[i][j].data.result;
						}
						var _str = new String(historyObj[i][j].data.result);
						if(_str.search('阳') == -1 && _str.search('阴') == -1){
							if(historyObj[i][j].data.unit !=""){
								temp = temp + " " + historyObj[i][j].data.unit;
							}
						}
						temp = temp + ",";													
					}
					if(temp != ''){
						temp = temp.substr(0, temp.length - 1);
					}
				}
				if(historyObj[i + 1] == 3){
					temp = historyObj[i];
				}				
				objToString = objToString + temp + ';';
				str = str + objToString;								
			}
			return str;
		}
		
		function objForAddToString(objForAdd){
			var str = '';
			for(i = 3; i < objForAdd.length; i = i + 5){
				objToString = objForAdd[i - 1] + '(' + objForAdd[i - 2] + '):';
				var temp = '';
				if(objForAdd[i + 1] == 2){
					for(j = 0; j < objForAdd[i].length; j++){
						temp = temp + objForAdd[i][j].data.projectname;		
						if(objForAdd[i][j].data.result !=""){
							temp = temp + " " + objForAdd[i][j].data.result;
						}
						if(objForAdd[i][j].data.result.search('阳') == -1 && objForAdd[i][j].data.result.search('阴') == -1){
							if(objForAdd[i][j].data.unit !=""){
								temp = temp + " " + objForAdd[i][j].data.unit;
							}
						}					
						temp = temp + ",";	
					}
					if(temp != ''){
						temp = temp.substr(0, temp.length - 1);
					}
				}
				if(objForAdd[i + 1] == 4){
					temp = objForAdd[i];
				}
				
				objToString = objToString + temp + ';';
				str = str + objToString;								
			}
			return str;
		}

		function dealHisRec(_node,_e){	 	
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
				//把导入的数据放入数组中:一次向数组添加五个元素，分别是“医院名称”、“报告日期”、“检查项目”、相应的子项详细信息和标志值flag
				hospitalName = returnValue.hospitalName;
	//			alert(hospitalName);
				pushToArray(hospitalName, reportDate, checkProject, returnValue, historyObj, flag);
				
	//			alert('导入后长度:' + historyObj.length);
				//把导入的数据放入“检查报告详细信息”中：需要组合成字符串
				textAreaValue = '';
				var historyObjStr = historyObjToString(historyObj);	
				var objForAddStr = objForAddToString(objForAdd);
	//			alert(historyObjStr + objForAddStr);
				textAreaValue = historyObjStr + objForAddStr;
				Ext.getCmp('checkReportInfo').setValue(textAreaValue);																
				
				//把导入的数据放入“已关联历史记录”中
				pushToStore(reportDate, checkProject);
			}
		}
		
		function addNewRec(_node,_e){
			var returnValue;
			checkProject = _node.text;
			if(_node.isLeaf()){
				returnValue = treeNodeClick(_node,'add');
			}
			if(returnValue){
				var reportMonth;
				if(flag == 2){
					reportMonth = returnValue.reportDate.getMonth() + 1;
					reportDate = returnValue.reportDate.getFullYear()+'-'+reportMonth+'-'+returnValue.reportDate.getDate();
				}else{
					reportDate = returnValue.reportDate;									
				}			
				
				//把导入的数据放入数组中:一次向数组添加五个元素，分别是“医院名称”、“报告日期”、“检查项目”、相应的子项详细信息和标志值flag
				hospitalName = returnValue.hospitalName;
	//			alert(hospitalName);
				
				pushToArray(hospitalName, reportDate, checkProject, returnValue, objForAdd, flag);
	//			alert('导入后长度:' + historyObj.length);					
				//把导入的数据放入“检查报告详细信息”中：需要组合成字符串
				textAreaValue = '';
				var historyObjStr = historyObjToString(historyObj);	
				var objForAddStr = objForAddToString(objForAdd);
	//			alert(historyObjStr + objForAddStr);
				textAreaValue = historyObjStr + objForAddStr;
				Ext.getCmp('checkReportInfo').setValue(textAreaValue);
				
				//把导入的数据放入“已关联历史记录”中
				pushToStore(reportDate, checkProject);
			}
		}
		
		function pushToArray(hospitalName, reportDate, checkProject, returnValue, arrayInfo, flag){
			if(store.getCount() > 0){		
				var i = 0;
				while(i < arrayInfo.length){
					if(arrayInfo[i + 1] == reportDate && arrayInfo[i + 2] == checkProject) break;
					i = i + 5;
				}
				if(i == arrayInfo.length){
					arrayInfo.push(hospitalName, reportDate, checkProject, returnValue.obj, flag);
				}else{
//					alert("重复记录将被覆盖");
					arrayInfo[i] = returnValue.hospitalName;
					arrayInfo[i + 3] = returnValue.obj;
				}																									
			}else{
				arrayInfo.push(hospitalName, reportDate, checkProject, returnValue.obj, flag);							
			}
		}
		
		function pushToStore(reportDate, checkProject){
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
		
		function recoverOldOperation(){
			if(oldOperation != ""){
				var arr = oldOperation.split("~~~~~");
				//恢复store中的数据
				var temp = arr[0];
				var _arr = temp.split("*****");
				for(i = 0; i < _arr.length; i++){
					var obj = eval('(' + _arr[i] + ')');
					store.add(new relatingRecord(obj));
				}
				//恢复historyObj中的数据
				temp = arr[1];
				_arr = temp.split("*****");
				for(i = 0; i < _arr.length && _arr.length != 1; i = i + 5){
					historyObj.push(_arr[i],_arr[i+1],_arr[i+2]);		
					if(_arr[i+4] == 1){
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
					if(_arr[i+4] == 3){
						historyObj.push(_arr[i+3]);
					}
					historyObj.push(_arr[i+4]);
				}
				//恢复objForAdd中的数据
				temp = arr[2];
				_arr = temp.split("*****");
				for(i = 0; i < _arr.length && _arr.length != 1; i = i + 5){
					objForAdd.push(_arr[i],_arr[i+1],_arr[i+2]);
				
					if(_arr[i+4] == 2){
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
					if(_arr[i+4] == 4){	
						objForAdd.push(_arr[i+3]);
					}
					objForAdd.push(_arr[i+4]);
				}
			}
		}
		
		//从store中删除数据
		function deleteFromStore(){
			var rows = Ext.getCmp("checkReportList_table").getSelectionModel().getSelections();
			for(i = 0; i < rows.length; i++){
				var j;
				for(j = 0; j < historyObj.length; j = j + 5){
					if(historyObj[j + 1] == rows[i].data.reportDate && historyObj[j + 2] == rows[i].data.checkProject) break;
				}
				if(j != historyObj.length){
					historyObj.splice(j, 5);
	//				alert('historyObj移除后长度:' + historyObj.length);
				}												
				
				var k;
				for(k = 0; k < objForAdd.length; k = k + 5){
					if(objForAdd[k + 1] == rows[i].data.reportDate && objForAdd[k + 2] == rows[i].data.checkProject) break;
				}
				if(k != objForAdd.length){
					objForAdd.splice(k, 5);
	//				alert('objForAdd移除后长度:' + objForAdd.length);
				}
				textAreaValue = '';
				store.remove(rows[i]);
			}
		}
		
		//保存本次操作
		function saveOperation(){
			oldOperation = "";
			//保存store数据(store、historyObj和objForAdd之间用“~~~~~”分割，数组内部之间用“*****”分割，项目详细信息的数据之间用“^^^^^”分割)
			store.each(function(relatingRecord){
				oldOperation = oldOperation + "{reportDate:'" + relatingRecord.get('reportDate') + "',checkProject:'" + relatingRecord.get('checkProject') + "'}*****";
			});
			
			if(oldOperation != ""){
				oldOperation = oldOperation.substr(0, oldOperation.length - 5) + "~~~~~";	
				//保存historyObj数据			
				for(i = 0; i < historyObj.length; i = i + 5){
					oldOperation = oldOperation + historyObj[i] + "*****" + historyObj[i+1] + "*****" + historyObj[i+2] + "*****";
					if(historyObj[i+4] == 1){
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
					if(historyObj[i+4] == 3){
						oldOperation = oldOperation + historyObj[i+3];
						if(historyObj[i+3] != ""){
							oldOperation = oldOperation + "*****";
						}
					}
					if(historyObj.length != 0){
						oldOperation = oldOperation + historyObj[i+4] + "*****";
					}
				}				
				if(historyObj.length != 0){
					oldOperation = oldOperation.substr(0, oldOperation.length - 5) + "~~~~~";
				}else{
					oldOperation = oldOperation + "~~~~~";
				}
				//保存objForAdd数据
				for(i = 0; i < objForAdd.length; i = i + 5){
					oldOperation = oldOperation + objForAdd[i] + "*****" + objForAdd[i+1] + "*****" + objForAdd[i+2] + "*****";
					if(objForAdd[i+4] == 2){
						for(j = 0; j <  objForAdd[i+3].length; j++){
							 oldOperation = oldOperation + objForAdd[i+3][j].data.esname + "^^^^^" + objForAdd[i+3][j].data.result + "^^^^^" + 
								     objForAdd[i+3][j].data.refvalue + "^^^^^" + objForAdd[i+3][j].data.unit + "^^^^^"
									 + objForAdd[i+3][j].data.projectname + "^^^^^";
						}
						if(objForAdd[i+3].length != 0){
							oldOperation = oldOperation.substr(0, oldOperation.length - 5) + "*****";
						}
					}
					if(objForAdd[i+4] == 4){
						oldOperation = oldOperation + objForAdd[i+3];
						if(objForAdd[i+3] != ""){
							oldOperation = oldOperation + "*****";
						}
					}
					if(objForAdd.length != 0){
						oldOperation = oldOperation + objForAdd[i+4] + "*****";
					}
				}
				if(objForAdd.length != 0){
					oldOperation = oldOperation.substr(0, oldOperation.length - 5);	
				}
			}
		}
		//获取原始操作的信息
		function setValue(){
			$.post(
				App.App_Info.BasePath+'/InHospitalCase/LiverCaseAction.do',
				{
					method:'LabDiagnosticExamination_findByCaseID',
					id:KID
				},
				function(data){
					if(data.success){
						var _json=JSON.parse(data.data);
						if(_json){
							oldOperation = _json.oldOperation;
							recoverOldOperation();
							var historyObjStr = historyObjToString(historyObj);	
							var objForAddStr = objForAddToString(objForAdd);
							textAreaValue = historyObjStr + objForAddStr;
							Ext.getCmp('checkReportInfo').setValue(textAreaValue);	
						}
					}else{
						alert('页面赋值错误。');
					}
				},
				'json'
			);
		}
</script>
</head>
<body>
</body>
</html>