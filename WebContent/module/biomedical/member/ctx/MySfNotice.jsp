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
									header : '当前分组',
									width:150,
									dataIndex : 'groupName'
								},
								{
									header : '检查内容',
									dataIndex : 'checkContent'
								},
								{
									header : '通知日期',
									dataIndex : 'planDate'
								},
								{
									header : '预约日期',
									dataIndex : 'reserveDate'
								},
								{
									header : '短信内容',
									dataIndex : 'smsContent',
									renderer:function(value,meta,cols,rows){
										return '<a href="javascript:seeSmsContent('+rows+')" >查看</a>';
									}
								}]);
				var store = new Ext.data.Store( {
					proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/module/biomedical/member.do?method=getMyNotices'}),
					reader:new Ext.data.JsonReader({root:'root',totalPropertity:'total'},[
						{name:'id'},
						{name:'patient'},
						{name:'startDate'},
						{name:'groupName'},
						{name:'cycle'},
						{name:'checkContent'},
						{name:'planDate'},
						{name:'noticeDate'},
						{name:'reserveDate'},
						{name:'smsContent'},
						{name:'memo'}
					])
				});
				store.load({params:{start:0,limit:20}});
				var _grid=new Ext.grid.GridPanel({
						title:'我的随访通知',
						id:'grid',
						ds: store,   
						cm: cm,
						sm: sm,
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
				        })
					});
				new Ext.Viewport({
					layout:'fit',
					items:_grid
				}); 
			});
function seeSmsContent(rows){
	var record=Ext.getCmp('grid').getStore().getAt(rows);
	new Ext.Window({
		title: '通知短信',
        closable: true,
        width: 450,
        height: 400,
        frame: true,
        layout:'fit',
        items:new Ext.form.TextArea({
			border:false,
			value:record.get('smsContent')
        })
	}).show();
}
</script>
</head>
<body>
</body>
</html>