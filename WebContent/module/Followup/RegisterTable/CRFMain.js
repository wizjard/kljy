var dtree=null;
var treeNodeId=-1;
var indexId=-1;
var treeUrl='';
var rid=App.util.getHtmlParameters('rid')//病人入选课题id
var courseId=App.util.getHtmlParameters('courseId')//课题id
var pid=App.util.getHtmlParameters('pid');
var timesText=App.util.getHtmlParameters('timesText');
var kid = App.util.getHtmlParameters('kid');
 /*addNode函数新增参数pid：病人id 查询病人基本信息*/

/*
 * 页面加载完成后执行函数
 */
Ext.onReady(function(){
	var viewport=new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'west',
				title:'CRF管理',
				id:'west-panel',
                width: 170,
				split:true,
				autoScroll:true,
				html:'<div id="dtree"></div>',
				tbar:[
				{
			text:'查询',
			menu:[],
					listeners:{
						render:function(){
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/common/CommonAction.do',
								params:{
									method:'GetSelfQueryList',
									sql:'select CODE,NAME,URL from SYS_ZD_CRF '+
									'where CODE in(select CRFCode from SYS_ResearchTopic_CRF_Rel '+
									'where ResearchTopicID=(select id from SYS_ResearchTopic '+
									'where id=(select researchId from t_Patient_Research where id='+
									rid+')))'
								},
								scope:this,
								success:function(_response){
									var menu=new Ext.menu.Menu();
									Ext.each(Ext.util.JSON.decode(_response.responseText),function(_item){
										menu.add({
											text:_item.NAME,
											code:_item.CODE,
											url:_item.URL,
											handler:function(){
											addNode(_item.URL);
											loadDictTree(rid,_item.URL,_item.CODE);
											
											}
										});
									});
									this.menu=menu;
								}
							});
						}
					}}
				,{
						xtype:'tbseparator'
					},{
			text:'登记',
			menu:[],
					listeners:{
						render:function(){
							Ext.Ajax.request({
								url:App.App_Info.BasePath+'/common/CommonAction.do',
								params:{
									method:'GetSelfQueryList',
									sql:'select CODE,NAME,URL from SYS_ZD_CRF '+
									'where CODE in(select CRFCode from SYS_ResearchTopic_CRF_Rel '+
									'where ResearchTopicID=(select id from SYS_ResearchTopic '+
									'where id=(select researchId from t_Patient_Research where id='+
									rid+')))'
								},
								scope:this,
								success:function(_response){
									var menu=new Ext.menu.Menu();
									Ext.each(Ext.util.JSON.decode(_response.responseText),function(_item){
										menu.add({
											text:_item.NAME,
											code:_item.CODE,
											url:_item.URL,
											handler:function(){
												addNode(_item.URL);
												loadDictTree(rid,_item.URL,_item.CODE);
											}
										});
									});
									this.menu=menu;
								}
							});
						}
					}},{
						xtype:'tbseparator'
					},{
						text:'删除',handler:deleteNode
					}
				]
			},{
				region:'center',
				title:'CRF登记表',
				id:'center-panel',
				tbar:[
					{
						xtype:'tbseparator'
					},{
						text:'保存',handler:function(){
							document.getElementById('courseRecord').contentWindow.SaveData();
							loadDictTreeEx();
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
});
/*
 * 从服务端获取会诊记录信息并显示到页面上
 */
 function loadDictTree(rid,url,code){
  
  if(code=='ZYZHJF'){
  	table='TCMSysptomScore'
  }
  if(code=='MXGBLB'){
  	table='ChronicLiver'
  }
  if(code=='JKDCJB'){
  	table='HealthSurvey'
  }
  if(code=='ZHILIAO'){
  	table='Zhiliao'
  }
  treeUrl=url;
  loadDictTreeEx();
}
function loadDictTreeEx(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/ResearchFollowupAction.do?method=findCRF',
		params:{sql:'from '+table+' where ketiId='+rid},
		success:function(_response,_options){
			var nodes=Ext.util.JSON.decode(_response.responseText);
			var str=JSON.stringify(nodes)
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
							  'javascript:loadRecordIndex('+node.id+',\''+treeUrl+'\')',
							  '_self');
				}
				}
				dtree.config.useSelection=true;
				dtree.config.folderLinks=false;	
				dtree.config.useCookies=false;
				document.getElementById('dtree').innerHTML = dtree;
				dtree.openAll();
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
 *新增
 */
function addNode(url){
	//alert("addNode函数！" + "id："+ id + "  rid：" + rid + "  timesText=" + timesText + "  pid=" + pid );
	document.getElementById("courseRecord").src = App.App_Info.BasePath+url+'?id=-1&rid='+rid+'&timesText='+timesText+'&pid='+pid+'&kid=' + kid;
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
					url:App.App_Info.BasePath+'/ResearchFollowupAction.do?method=delReserchCRF',
					params:{sql:'delete '+table+' where id='+_sNode.id},
					success:function(_response,_options){
						var responseJson=Ext.util.JSON.decode(_response.responseText);
						if (responseJson.success) {
						alert('删除成功');
						loadDictTreeEx();
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
function loadRecordIndex(_treeNodeId,url){
	//alert("URL："+App.App_Info.BasePath+url+'?id='+_treeNodeId +'&pid=' + pid + '&kid=' + kid);
	document.getElementById("courseRecord").src=App.App_Info.BasePath+url+'?id='+_treeNodeId + '&pid='+pid + '&kid=' + kid;
}
