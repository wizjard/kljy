function currentTime()
{
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	if(month < 10){
		month = "0"+month;
	}
	var day = date.getDate();
	if(day <10){
		day = "0"+day;
	}
	var hours = date.getHours();
	if(hours <10)
	{
		hours = "0"+hours;
	}
	var minute = date.getMinutes();
	if(minute <10)
	{
		minute = "0"+minute;
	}
	var second = date.getSeconds();
	if(second <10){
		second ="0"+second;
	}
	var currentTime = year+"-"+month+"-"+day;
	return currentTime;
}

Ext.onReady(function(){
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'center',
				layout:'fit',
				border:false,
				items:createGrid()
			}
		]
	});
});

function createGrid(){
//	var _sm=new Ext.grid.CheckboxSelectionModel({sigleSelect:true});选中多行
	 var _sm = new Ext.grid.RowSelectionModel({singleSelect: true}); //选中一行
	var _cm_normal=[
		new Ext.grid.RowNumberer(),
		//_sm,
		{header:'编号',dataIndex:'bianhao'},
		{header:'会员姓名',dataIndex:'patientName'},
		{header:'性别',dataIndex:'gender'},
		{header:'联系电话',dataIndex:'homeTel'},
		{header:'预约科室',dataIndex:'deptName'},
		{header:'预约医生',dataIndex:'doctorName'},
		{header:'预约时间',dataIndex:'planDate'},
		{header:'预约类型',dataIndex:'bsAPId'},
		{header:'门诊时间',dataIndex:'bsTSId'}
	];
	var _ra_normal=[
		{name:'id'},
		{name:'patientName'},
		{name:'gender'},
		{name:'homeTel'},
		{name:'deptName'},
		{name:'doctorName'},
		{name:'planDate'},
		{name:'bsAPId'},
		{name:'bsTSId'},
		{name:'bianhao'}
	];
	var _cfg=window.parent.GridCfg;
	var _url=App.App_Info.BasePath+'/PlanSignOrderAction.do';
	var _baseParams={
		method:'findGrounpPlanSignOrderPatientList',
		doctorId:top.USER.account
	};
	if(_cfg){
		_url=_cfg.url?_cfg.url:_url;
		_baseParams=_cfg.baseParams?_cfg.baseParams:_baseParams;
		if(_cfg.cm){
			_cm_normal=_cm_normal.concat(_cfg.cm);
		}
	}
	var _cm=new Ext.grid.ColumnModel(_cm_normal);
	var _ds=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({url:_url}),
		reader:new Ext.data.JsonReader({root:'root',totalProperty:'total'},_ra_normal)
	});
	_ds.baseParams=_baseParams;
	_ds.load({params:{start:0,limit:15}});
	 var SearchButton = Ext.extend(Ext.Button, {
                    text: '查询',
                    sw: null,
                    toggleSw: function(){
                        if (this.sw.isVisible()) {
                            this.sw.hide();
                        }
                        else {
                            this.sw.show();
                            this.setSwPosition();
                        }
                    },
                    /*Init Search Window*/
                    initSw: function(){
                        var o = this;
                        o.sw = new Ext.Window({
                            draggable: false,
                            resizable: false,
                            width: 300,
                            autoHeight: true,
                            closable: false,
                            buttonAlign: 'center',
                            frame: true,
                            items: {
                                xtype: 'form',
                                border: false,
                                bodyStyle: 'padding-top:5px',
                                labelAlign: 'right',
                                labelWidth: 60,
                                defaults: {
                                    xtype: 'textfield',
                                    anchor: '95%'
                                },
                                items: [
                                
                                {
                                    fieldLabel: '预约时间',
                                    name: 'planDate',
									id:'planDate'
                                }, {
                                    xtype: 'panel',
                                    border: false,
                                    html: '<p style="font-size:10px;color:red;text-align:center">例：(2010-01-02)或(2010-01-01 2010-02-01)不包括括号。</p>'
                                }
                                ]
                            },
                            buttons: [{
                                text: '查询',
                                handler: function(){
                                    var values = o.sw.items.get(0).getForm().getValues();
                                    var store = Ext.getCmp('grid').getStore();
                                    doctorId:top.USER.account
                                    store.baseParams.search = Ext.encode(values);      
                                    store.load({
                                        params: {
                                            start: 0,
                                            limit: 20
                                        }
                                    });
                                    o.sw.items.get(0).getForm().reset(); //用于清空框中的值 by cheng jiangyu 2011-9-30
                                    o.sw.hide();
                                    
                                }
                            }, {
                                text: '取消',
                                handler: function(){
                                    o.sw.items.get(0).getForm().reset(); //用于清空框中的值 by cheng jiangyu 2011-9-30
                                    o.sw.hide();
                                }
                            }]
                        });
                    },
                    /*ReSet Window's Position*/
                    setSwPosition: function(){
                        var el = this.el.dom;
                        var pos = getElementPos(el);
                        var oPos = [pos.x, pos.y];
                        var oSize = {
                            width: el.offsetWidth,
                            height: el.offsetHeight
                        };
                        var bodySize = Ext.getBody().getSize();
                        var sSize = this.sw.getSize();
                        var x = oPos[0];
                        var y = oPos[1] + oSize.height;
                        if ((x + sSize.width) > bodySize.width) {
                            x = x - sSize.width + oSize.width;
                        }
                        if ((y + sSize.height) > bodySize.height) {
                            y = y - sSize.height - oSize.height;
                        }
                        this.sw.setPosition(x, y);
                    }
                });
	var _grid=new Ext.grid.GridPanel({
		id:'grid',
		border:false,
		cm:_cm,
		sm:_sm,
		ds:_ds,
		autoScroll: true,
		viewConfig:{
			forceFit:true
		},
		bbar:new Ext.PagingToolbar({
            pageSize: 20,
            store: _ds,
            displayInfo: true,
            displayMsg: '显示第<font color="red"> {0} </font>条' +
            '到<font color="red"> {1} </font>条记录，' +
            '一共<font color="red"> {2} </font>条',
            emptyMsg: "<font color='red'>没有记录</font>"
        }),
        tbar:[
              '-',{
            	  text:'刷新',handler:function(){
            	  	 var store = Ext.getCmp('grid').getStore();
                            store.baseParams={
                               	  method:'findGrounpPlanSignOrderPatientList',
								  doctorId:top.USER.account
								};
                            store.load({
                                params: {
                                    start: 0,
                                    limit: 20
                                }
                            });
              	  }
              },
              '-', new SearchButton({
                        handler: function(){
                            if (!this.sw) {
                                this.initSw();
                            }
                            this.toggleSw();
                        }
                    })
        ]
	});
	return _grid;
}

function getSelectionRows(){
	return Ext.getCmp('grid').getSelectionModel().getSelections();
}

function linkToOneQuestion(pcid){
	window.location.href=App.App_Info.BasePath+'/module/patientanddoctoroperator/doctorConsultingOne.html?pcId='+pcid;
}