function CaseGrid(){
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'随访次数',dataIndex:'followTimes'},
		{header:'随访周期(月)',dataIndex:'followCycle',renderer:function(value){
			if(value>0){
				return value;
			}else{
				return '';
			}
		}},
		{header:'随访项目',dataIndex:'followContent'},
		{header:'随访通知日期',dataIndex:'noticeDate'},
		{header:'预约日期',dataIndex:'reserveDate'},
		{header:'登记日期',dataIndex:'comeDate',sortable:true}
	]);
	var ds=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/ResearchFollowupAction.do'}),
		reader:new Ext.data.JsonReader({root:''},[
			{name:'id'},
			{name:'patientResearchId'},
			{name:'patientId'},
			{name:'followTimes'},
			{name:'followCycle'},
			{name:'followContent'},
			{name:'noticeDate'},
			{name:'reserveDate'},
			{name:'comeDate'},
			{name:'remarkContent'}
		]),
		sortInfo:{field: 'comeDate', direction: "ASC"},
		groupField:'patientResearchId'
	});
	var _grid={
		ds: ds,   
		cm: cm,
		sm: sm,
		border:false,
		viewConfig:{
			forceFit:true
		}
	};
	return _grid;
}
