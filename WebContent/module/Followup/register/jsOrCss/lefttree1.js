var dtree=null;
var treeNodeId=-1;
var indexId=-1;
var rid=App.util.getHtmlParameters('rid')//病人入选课题id
var courseId=App.util.getHtmlParameters('courseId')//课题id
var PatientID=App.util.getHtmlParameters('pid');
var timesText=App.util.getHtmlParameters('timesText');
var kid = App.util.getHtmlParameters('kid');

/*
 * 页面加载完成后执行函数
 */
Ext.onReady(function(){
	var viewport=new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'west',
				title:'科研标本管理',
				id:'west-panel',
                width: 170,
				split:true,
				autoScroll:true,
				html:'<div id="dtree"></div>',
				tbar:[
					{
						xtype:'tbseparator'
					},{
						text:'新增',
						handler:addNode
					},{
						xtype:'tbseparator'
					},{
						text:'删除',handler:deleteNode
					},{
						text:'刷新',handler:function(){
							document.location.reload();
						}
					}
				]
			},{
				region:'center',
				title:'科研标本处理登记表',
				id:'center-panel',
				tbar:[
					{
						xtype:'tbseparator'
					},{
						text:'打印',
						handler:function(){
							document.getElementById('courseRecord').contentWindow.printData();
							//	window.open('srPrintView.html');
						}
					},{
						xtype:'tbseparator'
					},{
						text:'保存',handler:function(){
							document.getElementById('courseRecord').contentWindow.saveData();
						}
					},'->',{
						text:'返回',handler:function(){
							history.go(-1);
						}
					}
				],
				html:'<iframe id="courseRecord" src="" height="100%" width="100%"></iframe>'
			}
		]
	});
	loadDictTree();
});
/*
 * 从服务端获取会诊记录信息并显示到页面上
 */
  function loadDictTree(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/EMR/register.do?method=initPageValue',
		params:{rid:rid},
		success:function(_response,_options){
			var nodes=Ext.util.JSON.decode(_response.responseText);
				//var nodes = Ext.util.JSON.decode(responseJson);
			
				dtree=new dTree('dtree');
				/*dtree.add(
							  -1,
							  'root',
							  '',
							  '_self');*/
							 if(nodes){
				for(var i=0,len=nodes.length;i<len;i++){
					var node=nodes[i];//对象
					var timesText=node.timesText;
					var date=node.dateTime;
					var _date=date.split(" ")[0];
					dtree.add(node.id,
							  -1,
							  '<span style=" font-size:12px">'+_date+'('+timesText+')</span>',
							  'javascript:loadRecordIndex('+node.id+',\''+timesText+'\')',
							  '_self');
				}
				}
				dtree.config.useSelection=true;
				dtree.config.folderLinks=false;	
				dtree.config.useCookies=false;
				document.getElementById('dtree').innerHTML = dtree;
				dtree.openAll();
				loadRecordIndex(nodes[0].id,nodes[0].timesText)
				
	}
	});
	
}
/*
 * 判断是否选中节点并返回节点信息
 */
function isNodeSelected(){
	var sid=dtree.selectedNode;
	if(sid==null||typeof(sid)==''){
		Ext.Msg.alert('信息','请先选中某一节点。');
		return null;
	}
	return dtree.aNodes[sid];
}
/*
 *新增病程记录
 */
function addNode(){
	document.getElementById("courseRecord").src="srRegister.html?id=-1&rid="+rid+"&patientID="+PatientID+"&add=add"+"&random="+new Date().getTime()+"&courseId="+courseId+"&timesText="+timesText + '&kid='+kid;
}
/*
 *删除树节点
 */
function deleteNode(){
	var _sNode=isNodeSelected();
	if(_sNode!=null){
		Ext.Msg.confirm('警告','确实要删除节点？',function(_btn){
			if (_btn == 'yes') {
				Ext.Ajax.request({
					url:App.App_Info.BasePath+'/EMR/register.do?method=deleteRegister',
					params:{id:_sNode.id},
					success:function(_response,_options){
						var responseJson=Ext.util.JSON.decode(_response.responseText);
						if (responseJson.success) {
							loadDictTree();//删除成功后重新布局字典树
							addNode();
						}else{
							Ext.Msg.alert('Error','删除失败。');
						}
					}
				});
			}
		});
	}
}
/*
 * 加载后查看某条记录的信息
 */
function loadRecordIndex(_treeNodeId,Text){
	document.getElementById("courseRecord").src="srRegister.html?id="+_treeNodeId+"&rid="+rid+"&patientID="+PatientID+"&add=no"+"&courseId="+courseId+"&timesText="+Text;
}

