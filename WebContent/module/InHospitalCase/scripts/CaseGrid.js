function CaseGrid(){
	var sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});
	var cm=new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header:'住院次数',dataIndex:'inHspTimes'},
		{header:'病历类型',dataIndex:'caseModuleId',renderer:function(value){
			if(value&&value.length>0){
				Ext.Ajax.request({
					url:App.App_Info.BasePath+'/InHospitalCase/CaseMaster.do',
					params:{
						method:'toInHspRec',
						caseModuleId:value
					},
					sync:true,
					success:function(_response,_options){
						var _res=Ext.util.JSON.decode(_response.responseText);
						if(_res.success){
							var _cfg=Ext.util.JSON.decode(_res.data);
							value=_cfg.name;
						}
					}
				});
				return value;
			}
		}},
		{header:'入院日期',dataIndex:'inHspDate'},
		{header:'入院科室',dataIndex:'inWardCode',renderer:function(value){
			if(value&&value.length>0){
				Ext.Ajax.request({
					url:App.App_Info.BasePath+'/common/CommonAction.do',
					params:{
						method:'GetIndependentDictionaryText',
						code:'belong',
						value:value
					},
					sync:true,
					success:function(_response,_options){
						value=_response.responseText;
					}
				});
				return value;
			}
		}},
		{header:'入院诊断',dataIndex:'inIlls',renderer:function(value){
			if(value&&value.length>0){
				Ext.Ajax.request({
					url:App.App_Info.BasePath+'/common/CommonAction.do',
					params:{
						method:'GetIndependentDictionaryText',
						code:'ills',
						value:value
					},
					sync:true,
					success:function(_response,_options){
						value=_response.responseText;
					}
				});
				return value;
			}
		}},
		{header:'入院状态',dataIndex:'inStatus',renderer:function(value){
			if(value&&value.length>0){
				Ext.Ajax.request({
					url:App.App_Info.BasePath+'/common/CommonAction.do',
					params:{
						method:'GetIndependentDictionaryText',
						code:'inStatus',
						value:value
					},
					sync:true,
					success:function(_response,_options){
						value=_response.responseText;
					}
				});
				return value;
			}
		}},
		{header:'出院日期',dataIndex:'outDate',renderer:function(value){
			if(value&&new String(value).length>0){
				return value.substr(0,10);
			}
		}},
		{header:'出院诊断',dataIndex:'outIlls'},
		{header:'出院科室',dataIndex:'outWardCode',renderer:function(value){
			if(value&&value.length>0){
				Ext.Ajax.request({
					url:App.App_Info.BasePath+'/common/CommonAction.do',
					params:{
						method:'GetIndependentDictionaryText',
						code:'belong',
						value:value
					},
					sync:true,
					success:function(_response,_options){
						value=_response.responseText;
					}
				});
				return value;
			}
		}},
		{header:'负责医生',dataIndex:'responsibleDoc',renderer:function(value){
			if(value&&new String(value).length>0){
				Ext.Ajax.request({
					url:App.App_Info.BasePath+'/common/CommonAction.do',
					params:{
						method:'GetIndependentDictionaryText',
						code:'userName',
						value:value
					},
					sync:true,
					success:function(_response,_options){
						value=_response.responseText;
					}
				});
				return value;
			}
		}},
		{header:'当前科室',dataIndex:'currentWordCode',renderer:function(value){
			if(value&&value.length>0){
				Ext.Ajax.request({
					url:App.App_Info.BasePath+'/common/CommonAction.do',
					params:{
						method:'GetIndependentDictionaryText',
						code:'belong',
						value:value
					},
					sync:true,
					success:function(_response,_options){
						value=_response.responseText;
					}
				});
				return value;
			}
		}}
	]);
	var ds=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/InHospitalCase/CaseMaster.do'}),
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
