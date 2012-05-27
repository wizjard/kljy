Ext.onReady(function(){
	Ext.QuickTips.init();
	new Ext.Viewport({
		layout:'fit',
		items:Ext.apply(CreatePatientCard(),{
			xtype:'grid',
			title:'病人列表',
			tbar:[
				{
					xtype:'tbseparator'
				},{
					text:'&nbsp;刷新&nbsp;',
					tooltip:'刷新当前列表',
					handler:function(){
						Ext.getCmp('patient-list-grid').getStore().reload();
					}
				},{
					xtype:'tbseparator'
				},{
					text:'基本信息',
					tooltip:'编辑病人基本信息',
					handler:function(){
						var _data=getSelections();
						if(!_data)	return;
						App.util.addNewMainTab('/module/Patient/PatientInfo.html?id='+_data.data.id,'基本信息('+_data.data.patientName+')');
					}
				},{
					xtype:'tbseparator'
				},{
					text:'住院记录',
					tooltip:'进入病人住院记录页面',
					handler:function(){
						var _data=getSelections();
						if(!_data)	return;
						App.util.addNewMainTab('/module/InHospitalCase/CaseList.html?id='+_data.data.id,'住院记录('+_data.data.patientName+')');
					}
				},{
					xtype:'tbseparator'
				},'->',{
					xtype:'tbseparator'
				},{
					xtype:'textfield',
					width:150,
					emptyText:'病人姓名或病案号',
					id:'search-keyword'
				},{ 
					text:'搜索病人',
					tooltip:'根据病人姓名或病案号搜索病人',
					handler:function(){
						var _val=Ext.getCmp('search-keyword').getValue().trim();
						if(_val.length==0){
							alert('关键字不能为空。');
							return;
						}
						var _store=Ext.getCmp('patient-list-grid').getStore();
						_store.baseParams={method:'searchByNameOrNo',keyword:_val};
						_store.load({params:{start:0,limit:20}});
					}
				},{
					xtype:'tbseparator'
				},{
					text:'&nbsp;全部&nbsp;',
					tooltip:'显示当前或上次在本科住院全部病人',
					handler:function(){
						var _store=Ext.getCmp('patient-list-grid').getStore();
						_store.baseParams={method:'queryAll'};
						_store.load({params:{start:0,limit:20}});
					}
				},{
					xtype:'tbseparator'
				},{
					text:'新增病人',
					tooltip:'为本科新增一个病人,保存成功后请刷新列表显示。',
					handler:function(){
						App.util.addNewMainTab('/module/Patient/PatientInfo.html?id=-1','新增病人');
					}
				},{
					xtype:'tbseparator'
				}
			],
			listeners:{
				render:function(){
					this.getStore().baseParams={method:'queryAll'};
					this.getStore().load({params:{start:0,limit:20}});
				}
			}
		})
	});
});
function getSelections(){
	var _ss=Ext.getCmp('patient-list-grid').getSelectionModel().getSelections();
	if(_ss.length==0){
		alert('请选择一条记录。');
		return null;
	}
	return _ss[0];
}