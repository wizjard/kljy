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
<title>我的健康记录</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/common.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_ext.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/module/biomedical/member/resources/js/HealthRecord.js"></script>
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
									header : '登记日期',
									width:150,
									dataIndex : 'registDate'
								},
								{
									header : '症状',
									id:'symptomcol',
									dataIndex : 'symptom'
								},
								{
									header : '病情演变',
									width:150,
									dataIndex : 'yanbian'
								}]);
				var store = new Ext.data.Store( {
					proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/module/biomedical/member.do?method=getMyHealthRecords'}),
					reader:new Ext.data.JsonReader({root:'root',totalPropertity:'total'},[
						{name:'id'},
						{name:'registDate'},
						{name:'symptom'},
						{name:'shiliang'},
						{name:'shuimian'},
						{name:'tizhong'},
						{name:'jingshen'},
						{name:'niaoliang'},
						{name:'dabian'},
						{name:'yanbian'},
						{name:'zhiliao'},
						{name:'haozhuan'},
						{name:'jiuzhen'}
					])
				});
				store.load({params:{start:0,limit:20}});
				var _grid=new Ext.grid.GridPanel({
						title:'健康记录',
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
								text:'新增',
								handler:function(){
						    	  var win=Ext.getCmp('Member.HR.RegistForm');
						    	  if(!win){
										win=new Member.HR.RegistForm();
							    	}
							    	win.show();
							    	win.items.get(0).getForm().reset();
								}
							  },'-',{
								text:'编辑',
								handler:function(){
						    	  var ss=_grid.getSelectionModel().getSelections();
						    	  if(ss.length==0){
										alert('请选择一条记录。');
										return;
							      }
						    	  var win=Ext.getCmp('Member.HR.RegistForm');
						    	  if(!win){
										win=new Member.HR.RegistForm();
							    	}
							    	win.show();
							    	win.items.get(0).getForm().loadRecord(new Ext.data.Record(ss[0].data));
								}
							  },'-',{
								text:'删除',
								handler:function(){
						    	  var ss=_grid.getSelectionModel().getSelections();
						    	  if(ss.length==0){
										alert('请选择一条记录。');
										return;
							      }
							      if(confirm()){

								  }
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