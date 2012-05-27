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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/common.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_ext.js"></script>
<script type="text/javascript">
	var pid=<%=mem.getPatient().getId()%>;
	Ext
			.onReady(function() {
				var sm = new Ext.grid.CheckboxSelectionModel( {
					singleSelect : true
				});
				var cm = new Ext.grid.ColumnModel(
						[
								new Ext.grid.RowNumberer(),
								sm,
								{
									header : '住院次数',
									dataIndex : 'inHspTimes'
								},
								{
									header : '入院日期',
									dataIndex : 'inHspDate'
								},
								{
									header : '入院科室',
									dataIndex : 'inWardCode',
									renderer : function(value) {
										if (value && value.length > 0) {
											Ext.Ajax
													.request( {
														url : App.App_Info.BasePath + '/common/CommonAction.do',
														params : {
															method : 'GetIndependentDictionaryText',
															code : 'belong',
															value : value
														},
														sync : true,
														success : function(
																_response,
																_options) {
															value = _response.responseText;
														}
													});
											return value;
										}
									}
								},
								{
									header : '入院诊断',
									dataIndex : 'inIlls',
									renderer : function(value) {
										if (value && value.length > 0) {
											Ext.Ajax
													.request( {
														url : App.App_Info.BasePath + '/common/CommonAction.do',
														params : {
															method : 'GetIndependentDictionaryText',
															code : 'ills',
															value : value
														},
														sync : true,
														success : function(
																_response,
																_options) {
															value = _response.responseText;
														}
													});
											return value;
										}
									}
								},
								{
									header : '入院状态',
									dataIndex : 'inStatus',
									renderer : function(value) {
										if (value && value.length > 0) {
											Ext.Ajax
													.request( {
														url : App.App_Info.BasePath + '/common/CommonAction.do',
														params : {
															method : 'GetIndependentDictionaryText',
															code : 'inStatus',
															value : value
														},
														sync : true,
														success : function(
																_response,
																_options) {
															value = _response.responseText;
														}
													});
											return value;
										}
									}
								},
								{
									header : '出院日期',
									dataIndex : 'outDate',
									renderer : function(value) {
										if (value
												&& new String(value).length > 0) {
											return value.substr(0, 10);
										}
									}
								},
								{
									header : '出院诊断',
									dataIndex : 'outIlls'
								},
								{
									header : '出院科室',
									dataIndex : 'outWardCode',
									renderer : function(value) {
										if (value && value.length > 0) {
											Ext.Ajax
													.request( {
														url : App.App_Info.BasePath + '/common/CommonAction.do',
														params : {
															method : 'GetIndependentDictionaryText',
															code : 'belong',
															value : value
														},
														sync : true,
														success : function(
																_response,
																_options) {
															value = _response.responseText;
														}
													});
											return value;
										}
									}
								},
								{
									header : '负责医生',
									dataIndex : 'responsibleDoc',
									renderer : function(value) {
										if (value
												&& new String(value).length > 0) {
											Ext.Ajax
													.request( {
														url : App.App_Info.BasePath + '/common/CommonAction.do',
														params : {
															method : 'GetIndependentDictionaryText',
															code : 'userName',
															value : value
														},
														sync : true,
														success : function(
																_response,
																_options) {
															value = _response.responseText;
														}
													});
											return value;
										}
									}
								}]);
				var store = new Ext.data.Store( {
					proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/module/biomedical/member.do?method=getInHspCase'}),
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
					])
				});
				store.load();
				var _grid=new Ext.grid.GridPanel({
						title:'住院记录',
						ds: store,   
						cm: cm,
						sm: sm,
						border:false,
						viewConfig:{
							forceFit:true
						},
						tbar:[
						      '-',{
								text:'入院记录',
								handler:function(){
						    	  var ss=_grid.getSelectionModel().getSelections();
						    	  if(ss.length==0){
										alert('请选择一条记录。');
										return;
							      }
							      var kid=ss[0].get('id');
							     /* Ext.Ajax.request({
										url:App.App_Info.BasePath+'/InHospitalCase/CaseMaster.do',
										params:{
											method:'toInHspRec',
											caseModuleId:ss[0].get('caseModuleId')
										},
										success:function(_response,_options){
											var _res=Ext.util.JSON.decode(_response.responseText);
											if(_res.success){
												var _cfg=Ext.util.JSON.decode(_res.data);
												var href=_cfg.url+'?KID='+kid+'&PID='+pid;
												parent.loadNewTab(href,'入院记录');
											}else{
												alert('获取入院记录信息失败。');
											}
										}
									});*/
									window.open(App.App_Info.BasePath+'/module/biomedical/member/ctx/caseview/Liver.jsp?id='+kid);
								}
							  },'-',{
								text:'出院记录',
								handler:function(){
						    	  var ss=_grid.getSelectionModel().getSelections();
						    	  if(ss.length==0){
										alert('请选择一条记录。');
										return;
							      }
							      var kid=ss[0].get('id');
							      window.open(App.App_Info.BasePath+'/module/biomedical/member/ctx/caseview/OutHspPrintView.html?id='+kid);
								}
							  },'-'
						]
					});
				new Ext.Viewport({
					layout:'fit',
					items:_grid
				}); 
			});
</script>
</head>
<body>
</body>
</html>