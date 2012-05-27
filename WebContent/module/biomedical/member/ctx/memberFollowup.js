function Followup(){
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'门诊时间',dataIndex:'outTime'},
		{header:'门诊病历类型',dataIndex:'outTitle'},
		{header:'门诊科室',dataIndex:'outDeptname'},
//		{header:'是否会员',dataIndex:'outType',renderer:function(value){
//			if(value == 0){
//				return '否';
//			}else if(value == 1){
//				return '是';
//			}
//		}},
		{header:'第几次门诊',dataIndex:'outCount',renderer:function(value){
			if(value > 0){
				return value;
			}
		}},
//		{header:'随访次数',dataIndex:'outFollowTimes',renderer:function(value, meta, record, rowIndex, colIndex, store){
//			if(record.data.outTitle == ""){
//				return "";
//			}else{
//				return value;
//			}
//		}},
//		{header:'下次随访时间',dataIndex:'outPlanDate'},
//		{header:'随访周期(月)',dataIndex:'outFollowCycle',renderer:function(value){
//			if(value > 0){
//				return value;
//			}
//		}},
//		{header:'随访项目',dataIndex:'outFollowContent'},
//		{header:'随访通知日期',dataIndex:'outNoticeDate'},
//		{header:'预约日期',dataIndex:'outReserveDate'},
		{header:'预留标本',dataIndex:'biaoben',renderer:function(value){
			if(value=="0"){
				return '是';
			}else if(value=="1"){
				return '否';
			}else{
				return '';
			}
		}},
		{header:'门诊医生',dataIndex:'outMainDoctor'}
	]);
	var ds=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/OutOrMergencyCaseAction.do'}),
		reader:new Ext.data.JsonReader({root:''},[
			{name:'id'},
			{name:'outCount'},
			{name:'outType'},
			{name:'outTitle'},
			{name:'outDeptname'},
			{name:'outTime'},
			{name:'outFollowTimes'},
			{name:'outFollowCycle'},
			{name:'outFollowContent'},
			{name:'outNoticeDate'},
			{name:'outReserveDate'},
			{name:'outPlanDate'},
			{name:'biaoben'},
			{name:'outMainDoctor'},
			{name:'outRegno'}//门诊记录流水号
		])
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
