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
<title>网上咨询医生</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/common.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_ext.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/module/biomedical/member/resources/js/SeeDoctor.js"></script>
<script type="text/javascript">
	var pid=<%=mem.getPatient().getId()%>;
	var currentWard='<%=mem.getCurrentWard()%>';
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
									header : '申请日期',
									width:150,
									dataIndex : 'askTime'
								},
								{
									header : '申请科室',
									dataIndex : 'ward'
								},
								{
									header : '症状',
									id:'symptomcol',
									dataIndex : 'hr',
									renderer:function(value){
										if(value)
											return value.symptom;
									}
								},
								{
									header : '回复日期',
									width:150,
									dataIndex : 'answerDate'
								},{
									header : '回复医生',
									dataIndex : 'doctor'
								}]);
				var store = new Ext.data.Store( {
					proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/module/biomedical/member.do?method=getMemberMsgs'}),
					reader:new Ext.data.JsonReader({root:'root',totalPropertity:'total'},[
						{name:'id'},
						{name:'askTime'},
						{name:'ask'},
						{name:'ward'},
						{name:'answerDate'},
						{name:'answer'},
						{name:'hr'},
						{name:'mem'},
						{name:'doctor'}
					])
				});
				store.load({params:{start:0,limit:20}});
				var _grid=new Ext.grid.GridPanel({
						title:'申请记录',
						id:'grid',
						ds: store,   
						cm: cm,
						sm: sm,
						autoExpandColumn:'symptomcol',
						border:false,
						viewConfig:{
							forceFit:true
						},
						bbar:new Ext.PagingToolbar({
				            pageSize: 20,
				            store: store,
				            displayInfo: true,
				            displayMsg: '显示第<font color="red"> {0} </font>条' +
				            '到<font color="red"> {1} </font>条记录，' +
				            '一共<font color="red"> {2} </font>条',
				            emptyMsg: "没有记录"
				        }),
						tbar:[
						      '-',{
								text:'申请咨询医生',
								handler:function(){
						    	  ask();
								}
							  },'-',{
									text:'查看医生回复',
									handler:function(){
							    	  answer();
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