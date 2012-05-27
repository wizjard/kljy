
function CreateNotice(){
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'会员姓名',dataIndex:'patientName'},
		{header:'病案号',dataIndex:'patientNo'},
		{header:'患者编号',dataIndex:'id'},
		{header:'入会时间',dataIndex:'inDate',renderer:renderDate('Y-m-d')},
		{header:'入会科室',dataIndex:'inWard'},
		{header:'当前科室',dataIndex:'deptName'},
		{
                    header: '入会分组',
                    dataIndex: 'groupName'
        },
		{header:'当前分组',dataIndex:'currentGroup'},
		{
			header:'咨询次数',
			dataIndex:'accessCount',
			renderer: function(value, meta, record, rowIndex, colIndex, store){
                        return '<a href="javascript:test(' + value + ');">'+value+'</a>';
           }
		},
		{
		    header:'医生回复次数',dataIndex:'answerberCount',
		    renderer: function(value, meta, record, rowIndex, colIndex, store){
		        return '<a href="javascript:answercount(' +value+ ');">'+value+'</a>';
		    }
		},
		{header:'网上查房数',dataIndex:'contentberCount',
		   renderer: function(value, meta, record, rowIndex, colIndex, store){
		      return '<a href="javascript:contentCount(' +value+','+record.data.id+ ')">'+value+'</a>';
		   }
		  },
		{header:'入会医生',dataIndex:'inDoctor'},
		{header:'随访医生',dataIndex:'doctor'}
	]);
	var ds=new Ext.data.Store({
			proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/MemberSearchTotalAction.do'}),
			reader:new Ext.data.JsonReader({root:'root',totalProperty:'total'},
				[
					{name:'patientName'},
					{name:'patientNo'},
					{name:'id'},
					{name:'inDate'},
					{name:'inWard'},
					{name:'deptName'},
					{name:'groupName'},
					{name:'currentGroup'},
					{name:'accessCount'},
					{name:'answerberCount'},
					{name:'contentberCount'},
					{name:'inDoctor'},
					{name:'doctor'}
				]
			)
		});
	ds.baseParams={method:'searchMemberByCondition'};
	var _grid={
		id:'notice-list-grid',
		border:false,
		sm:sm,
		cm:cm,
		ds:ds,
		viewConfig:{
			forceFit:true
		},
		bbar:new Ext.PagingToolbar({
            pageSize: 20,
            store: ds,
            displayInfo: true,
            displayMsg: '显示第<font color="red"> {0} </font>条' +
            '到<font color="red"> {1} </font>条记录，' +
            '一共<font color="red"> {2} </font>条',
            emptyMsg: "没有记录"
        })
	};
	function renderDate(format) {
	    return function(v) {
	        var JsonDateValue;
	        if (Ext.isEmpty(v))
	            return '';
	        else if (Ext.isEmpty(v.time))
	            JsonDateValue = new Date(v);
	        else
	            JsonDateValue = new Date(v.time);
	        return JsonDateValue.format(format || 'Y-m-d');
	    };
	};
	return _grid;
}

function test(){
	App.util.addNewMainTab('/module/biomedical/ward/DoctorAnswer.html','网上咨询');
}
function answercount(){
    App.util.addNewMainTab('/module/biomedical/ward/DoctorAnswer.html','医生回复次数');
}
function contentCount(value,pid){
    App.util.addNewMainTab('/module/InHospitalCase/CourseRecord/dailyRecrod/mainofWardRound.html?PID='+pid,'网上查房');
}
