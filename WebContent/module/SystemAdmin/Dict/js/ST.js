Ext.onReady(function(){
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'west',
				id:'west-panel',
				title:'字典模块',
				width:180,
				xtype:'treepanel',
				autoScroll:true,
				containerScroll:true,
				dataUrl:App.App_Info.BasePath+'/admin/DictAction.do?method=st_findAll',
				root:{
					nodeType:'async',
					text:'字典树',
					draggable:false,
					id:-1
				},
				rootVisible:false,
				listeners:{
					render:function(){
						this.expandAll();
					},
					dblclick:function(_node){
						TreeNodeDblClick(_node.id);
					}
				}
			},{
				region:'center',
				id:'center-panel',
				title:'数据编辑窗口',
				tbar:[
					{
						text:'增加行',handler:addNewRow
					},{
						xtype:'tbseparator'
					},{
						text:'删除行',handler:deleteRow
					},{
						xtype:'tbseparator'
					},{
						text:'保存修改',handler:saveGridData
					}
				]
			}
		]
	});
});
function TreeNodeDblClick(_tableCode){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/admin/DictAction.do',
		params:{
			method:'st_getTableCfg',
			tableCode:_tableCode
		},
		success:function(_response,_options){
			var _res=Ext.util.JSON.decode(_response.responseText);
			if(_res.success){
				createTableGrid(Ext.util.JSON.decode(_res.data))
			}else{
				alert('表信息获取失败。');
			}
		}
	});
}
function createTableGrid(_table){
	var _oldGrid=Ext.getCmp('grid');
	if(_oldGrid)	_oldGrid.destroy();
	var sm = new Ext.grid.CheckboxSelectionModel({
        singleSelect: false
    });
	var _cm=[new Ext.grid.RowNumberer(),{header:'ID',dataIndex:'ID',hidden:true}];
	var _reader=[{name: 'ID' }];
	Ext.each(_table.columns,function(_item,_index){
		_cm.push({header:_item.name,dataIndex:_item.code,editor:new Ext.form.TextField()});
		_reader.push({name: _item.code });
	});
	_cm=new Ext.grid.ColumnModel(_cm);
	var _ds=new Ext.data.Store({
		pruneModifiedRecords:true,
		proxy:new Ext.data.HttpProxy({url:App.App_Info.BasePath+'/admin/DictAction.do'}),
		reader:new Ext.data.JsonReader({root:'root',totalProperty:'total'},_reader)
	});
	_ds.baseParams={method:'st_getTableValues',tableCode:_table.tableCode};
	_ds.load({params:{start:0,limit:20}});
	var _grid=new Ext.grid.EditorGridPanel({
		id:'grid',
		cm:_cm,
		ds:_ds,
		border:false,
		table:_table,
		viewConfig:{
			forceFit:true
		},
		getTableCfg:function(){
			return this.table;
		},
		sm:sm,
		bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: _ds,
            displayInfo: true,
            displayMsg: '显示第<font color="red"> {0} </font>条' +
            '到<font color="red"> {1} </font>条记录，' +
            '一共<font color="red"> {2} </font>条',
            emptyMsg: "没有记录"
        })
	});
	var _center=Ext.getCmp('center-panel');
	_center.setTitle(_table.tableName+'——数据编辑');
	_grid.setHeight(_center.getInnerHeight());
	_center.add(_grid);
	_center.doLayout();
}
function addNewRow(){
	var _grid=Ext.getCmp('grid');
	if(!_grid)	return;
	var _table=_grid.getTableCfg();
	var _object={ID:-1};
	Ext.each(_table.columns,function(_item,_index){
		_object[_item.code]='';
	});
	var _record=new Ext.data.Record(_object);
	_grid.getStore().insert(0,_record);
}
function deleteRow(){
	var _grid=Ext.getCmp('grid');
	if(!_grid)	return;
	var _selections=_grid.getSelectionModel().getSelections();
	if(_selections.length==0){
		alert('未选择任何行。');
		return;
	}
	var _store=_grid.getStore();
	var _remoteId=[];
	Ext.each(_selections,function(_record,_index){
		if(_record.data.ID!=-1){
			_remoteId.push(_record.data.ID);
		}
	});
	if(!confirm('确定要删除选中的行？'))	return;
	var remoteSuccess=false;
	if(_remoteId.length>0){
		Ext.Ajax.request({
			sync:true,
			url:App.App_Info.BasePath+'/admin/DictAction.do',
			params:{
				method:'st_deleteFromTable',
				tableCode:_grid.getTableCfg().tableCode,
				id:_remoteId.join(',')
			},
			success:function(_response,_options){
				var _res=Ext.util.JSON.decode(_response.responseText);
				if(_res.success){
					remoteSuccess=true;
				}else{
					alert('远程数据删除失败。');
				}
			}
		});
	}else{
		remoteSuccess=true;
	}
	if(remoteSuccess){
		Ext.each(_selections,function(_record,_index){
			_store.remove(_record);
		});
	}
}
function saveGridData(){
	var _grid=Ext.getCmp('grid');
	if(!_grid)	return;
	var _store=_grid.getStore();
	var _records=_store.getModifiedRecords();
	if(_records.length==0){
		alert('无新数据需要更新。');
		return;
	}
	var _data=[];
	Ext.each(_records,function(_item,_index){
		_data.push(_item.data);
	});
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/admin/DictAction.do',
		scope:this,
		params:{
			method:'st_saveOrUpdate',
			tableCode:_grid.getTableCfg().tableCode,
			data:Ext.util.JSON.encode(_data)
		},
		success:function(_response,_options){
			var _res=Ext.util.JSON.decode(_response.responseText);
			if(_res.success){
				alert('数据保存成功。');
				_store.reload();
			}else{
				alert('数据保存失败。');
			}
		}
	});
}
