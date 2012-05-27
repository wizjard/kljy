var KID = App.util.getHtmlParameters('KID');// 会员编号参数
var outRegno = App.util.getHtmlParameters('outRegno');
var _id = App.util.getHtmlParameters('planId');
var _paId = App.util.getHtmlParameters('paId');
var omcId = App.util.getHtmlParameters('omcId');

var repId = App.util.getHtmlParameters('repId');
var _flag = App.util.getHtmlParameters('type');

var receiveDate = App.util.getHtmlParameters('receiveDate');
var sectionNo = App.util.getHtmlParameters('sectionNo');
var testTypeNo = App.util.getHtmlParameters('testTypeNo');
var sampleNo = App.util.getHtmlParameters('sampleNo');
var parItemNo = App.util.getHtmlParameters('parItemNo');
var itemName = App.util.getHtmlParameters('itemName');

var login = App.util.getHtmlParameters('login'); // add by cheng jiangyu
													// 2011-8-26 会员看自己的随访计划

var patient;
var _patient;

var WEEK_OF_YEAR = '3';
var DAY_OF_YEAR = '6';
var YEAR = '1';
var MONTH_OF_YEAR = '2';

var _beginDate;

Ext.onReady(function() {
	layout();
});

// init patient's information
$.post(App.App_Info.BasePath + '/patient.do', {
	method : 'findById',
	login : login, // add by cheng jiangyu 2011-08-26
	id : _paId
},

function(data) {
	_patient = JSON.parse(data.data);
	// alert(_patient + "|" + _patient.inDate);
		patient = _patient;
		$('input[name="patientName"]').val(_patient.patientName);
		$('input[name="patientNo"]').val(_patient.patientNo);
		$('input[name="patientSex"]').val(_patient.gender == '1' ? '男' : '女');
		$('input[name="patientBrith"]').val(_patient.birthday);
	}, 'json');

function checkDate(b) {
	if (b == "") {
		return true;
	}

	var a = /^\d{4}[-]\d{2}[-]\d{2}$/;
	var c = /^\d{4}[-]\d{1}[-]\d{1}$/;
	var d = /^\d{4}[-]\d{1}[-]\d{2}$/;
	var e = /^\d{4}[-]\d{2}[-]\d{1}$/;
	if (!a.test(b) && !c.test(b) && !e.test(b) && !d.test(b)) {
		alert(b + " 日期格式不正确");
		return false;
	}
	return true;
}

function checkNumType(num, check) {
	if (num == "") {
		return true;
	}

	var no = /^\d{n}$/;
	/*
	 * var nmoth = /^\'月'$/; var nday = /^\'日'$/; var nyear = /^\'年'$/; var
	 * nday2 = /^\'天'$/; var nweek = /^\'周'$/;
	 * 
	 * if(check) { if(!no.test(num) && !nmoth.test(num)&& !nday.test(num)&&
	 * !nyear.test(num) && !nday2.test(num)&& !nweek.test(num)) { return false; } }
	 * else
	 */if (!no.test(num)) {
		alert(num + " 格式不正确");
		return false;
	}
	return true;
}

function refreshItem() {
	_store = Ext.getCmp('grid').getStore();
	_store.load( {
		params : {
			start : 0,
			limit : 20
		}
	});
}

function layout() {
	var ds = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : App.App_Info.BasePath + '/plan.do'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'root',
			totalProperty : 'total'
		}, [ {
			name : 'planId'
		}, {
			name : 'planTime'
		}, {
			name : 'circleType'
		}, {
			name : 'beginDate'
		}, {
			name : 'circle'
		}, {
			name : 'state'
		}, {
			name : 'stateStr'
		}, {
			name : 'planDate'
		}, {
			name : 'visitState'
		}, {
			name : 'resultState'
		}, {
			name : 'visitDate'
		} ])
	});
	ds.baseParams = {
		method : 'getPlansGroup',
		search : '',
		login : login, // add by cheng jiangyu 2011-8-26
		paId : _paId
	};
	ds.load( {
		params : {
			start : 0,
			limit : 20
		}
	});
	var sm = new Ext.grid.RowSelectionModel( {
		singleSelect : false
	});
	var cm = new Ext.grid.ColumnModel( [
			new Ext.grid.RowNumberer(),
			{
				header : '编号',
				dataIndex : 'planTime',
				sortable : true,
				width : 25,
				renderer : function(value) {
					return "第" + value + "次";
				}
			},
			{
				header : '随访计划设置日期',
				dataIndex : 'beginDate',
				sortable : true,
				width : 40,
				renderer : function(value) {
					if (value) {
						try {
							return new Date(value == null ? "" : value.time)
									.format('Y-m-d')
						} catch (err) {
						}
					}
				}
			}/*
				 * ,{ header: '周期', dataIndex: 'circle', width:20, renderer:
				 * function(value){ return value; } }
				 */,
			{
				header : '周期',
				dataIndex : 'circleType',

				width : 25,
				renderer : function(value, cell, record) {
					var re = "";
					if (value == YEAR) {
						re = '<font color="#ee9a00">年</font>';
					} else if (value == MONTH_OF_YEAR) {
						re = '<font color="green">月</font>';
					} else if (value == WEEK_OF_YEAR) {
						re = '<font color="red">周</font>';
					} else if (value == DAY_OF_YEAR) {
						re = '<font color="blue">天</font>';
					} else {
						re = '<font color="grey">请选择</font>';
					}

					return record.data.circle + " " + re;
				}
			},
			{
				header : '计划访问日期',
				dataIndex : 'planDate',
				sortable : true,
				width : 40,
				renderer : function(value) {
					if (value) {
						try {
							return new Date(value == null ? "" : value.time)
									.format('Y-m-d')
						} catch (err) {
						}
					}
				}
			}, {
				header : '实际来访日期',
				dataIndex : 'visitDate',
				width : 45,
				sortable : true,
				renderer : function(value) {
					if (value) {
						return value;
					}
				}
			}, {
				header : '随访状态',
				dataIndex : 'visitState',
				width : 35,
				renderer : function(value, cell, record) {
					if (value == 1) {
						return "<font color='#CDCD00'>提前来访</font>";
					} else if (value == 2) {
						return "<font color='#CDCD00'>按期来访</font>";
					} else if (value == 3) {
						return "<font color='#CDCD00'>超期来访</font>";
					} else if (value == 4) {
						return "<font color='green'>随访结束</font>";
					} else if (value == 5) {
						return "<font color='#B8B8B8'>超期未来访</font>";
					} else {
						return "<font color='red'>随访计划中</font>";
					}
				}
			}, {
				header : '检查执行情况',
				dataIndex : 'stateStr',
				width : 65,
				renderer : function(value, cell, record) {
					return value;
				}
			}, {
				header : '关联结果',
				dataIndex : 'resultState',
				width : 65,
				renderer : function(value, cell, record) {
					return value;
				}
			} ]);

	var oneTbar = new Ext.Toolbar( {

		items : [
				'-',
				{
					text : '返回列表',
					handler : function() {
						var store = Ext.getCmp('grid').getStore();
						store.baseParams.search = '';
						store.load( {
							params : {
								start : 0,
								limit : 20
							}
						});
					}
				},
				'-',
				{
					text : '日历查看随访计划',
					handler : function() {
						if (login != null && login == "member") { // 如果是会员登陆
																	// add by
																	// cheng
						// jiangyu 2011-08-26
					_paId = patient.id;
				}
				window.open(App.App_Info.BasePath
						+ '/module/plan/showcalendar.html?paId=' + _paId,
						'日历模式查看随访计划', 'width=950px;height=650px');
				// App.util.addNewMainTab('/module/plan/showcalendar.html?paId='+_paId,'日历模式查看随访计划');
			}
				},
				'-',
				{
					xtype : 'textfield',
					width : 60,
					emptyText : '随访编号',
					id : 'order-name'
				},
				{
					xtype : 'textfield',
					width : 150,
					emptyText : '随访计划设置日期(2010-08-08)',
					id : 'beginDate'
				},
				{
					xtype : 'textfield',
					width : 60,
					emptyText : '随访周期',
					id : 'cricleT'
				},
				{
					xtype : 'textfield',
					width : 150,
					emptyText : '实际来访日期(2010-08-08)',
					id : 'visitD'
				},
				{
					text : '搜索随访',
					tooltip : '搜索随访',
					handler : function() {
						var _val2 = Ext.getCmp('order-name').getValue().trim();
						var _valDate = Ext.getCmp('beginDate').getValue()
								.trim();
						var _visitD = Ext.getCmp('visitD').getValue().trim();
						var _cricleT = Ext.getCmp('cricleT').getValue().trim();

						if (!checkDate(_valDate)) {
							Ext.getCmp('beginDate').setValue("");
							return;
						}

						if (!checkDate(_visitD)) {
							Ext.getCmp('visitD').setValue("");
							return;
						}

						/*
						 * if(!checkNumType(_val2)) { return; }
						 */

						var _store = Ext.getCmp('grid').getStore();
						_store.baseParams = {
							method : 'getPlansGroup',
							search : '',
							login : login, // edit by cheng jiangyu
							// 2011-08-26
							paId : _paId

						};
						_store.load( {
							params : {
								start : 0,
								limit : 20,
								visitDate : _visitD,
								cricleT : _cricleT,
								planTime : _val2,
								beginDate : _valDate
							}
						});

					}
				}

		]
	});
	var twoTbar = new Ext.Toolbar(
			{
				items : [
						'-',
						{
							text : '删除随访计划',
							handler : function() {
								var ss = Ext.getCmp('grid').getSelectionModel()
										.getSelections();
								if (ss.length == 0) {
									alert('请先选择一条记录。');
									return;
								}

								if (ss[0].get('state') == 1
										|| ss[0].get('state') == 2) {
									alert('随访已执行，不能删除');
									return;
								}

								if (!confirm('确定要删除选中记录?'))
									return;
								Ext.Ajax
										.request( {
											url : App.App_Info.BasePath + '/plan.do',
											params : {
												method : 'deletePlan',
												id : ss[0].get('planId')
											},
											success : function(_response) {
												if (Ext
														.decode(_response.responseText).success) {
													alert('删除成功。');
													Ext.getCmp('grid')
															.getStore()
															.reload();
													var _store = Ext.getCmp(
															'editGrid')
															.getStore();
													_store.load( {
														params : {
															start : 0,
															limit : 20
														}
													});
												} else {
													alert('删除失败。');
												}
											}
										});
							}

						},
						'-',
						{
							text : '编辑随访计划',
							handler : function() {
								var ss = Ext.getCmp('grid').getSelectionModel()
										.getSelections();
								if (ss.length == 0) {
									alert('请先选择一条记录。');
									return;
								}
								if (ss[0].get('state') == 1
										|| ss[0].get('state') == 2) {
									alert('随访已执行，不能编辑');
									return;
								}
								App.util.addNewMainTab(
										'/module/plan/addPlanItem.html?planId='
												+ ss[0].get('planId')

												+ "&KID=" + KID, '随访计划信息');
							}
						},
						'-',
						{
							text : '增加随访计划',
							handler : function() {
								if (!confirm('确定要新增随访计划?'))
									return;
								App.util.addNewMainTab(
										'/module/plan/addPlanItem.html?pid='
												+ _paId + "&KID=" +

												KID, '增加随访计划');
							}
						} ]
			});

	var grid = new Ext.grid.GridPanel(
			{
				id : 'grid',
				title : '随访计划列表',
				sm : new Ext.grid.CheckboxSelectionModel( {
					sigleSelect : true
				}),
				cm : cm,
				ds : ds,
				autoScroll : true,
				viewConfig : {
					forceFit : true,
					enableRowBody : true,
					getRowClass : function(record, rowIndex, p, ds) {
						var plid = record.data.planTime;

						var classNmae = "";

						if (plid == 0) {
							classNmae = "baseRow";
						} else if (plid % 2 != 0) {
							classNmae = "";
						} else {
							classNmae = "ouRow";
						}
						return classNmae;

					}
				},
				sm : sm,
				tbar : [
						'<font color="blue">姓名</font>：',
						'<font color="red"><input type="text" size="7" name="patientName"></font>',
						'<font color="blue">病案号</font>：',
						'<font color="red"><input type="text" size="7" name="patientNo"></font>',
						'<font color="blue">性别</font>：',
						'<font color="red"><input type="text" size="3" name="patientSex"></font>',
						'<font color="blue">生日</font>：',
						'<font color="red"><input type="text" size="10" name="patientBrith"></font>' ],

				listeners : {
					'render' : function() {
						oneTbar.render(this.tbar); // add one tbar
						if (_flag != "omc") {
							twoTbar.render(this.tbar);
						}
					}
				},
				bbar : new Ext.PagingToolbar(
						{
							pageSize : 20,
							store : ds,
							displayInfo : true,
							displayMsg : '显示第<font color="red"> {0} </font>条' + '到<font color="red"> {1} </font>条记录，' + '一共<font color="red"> {2} </font>条',
							emptyMsg : "没有记录"
						})
			});
	if (login != null && login == 'member') { // 如果是会员登陆则隐藏两列 并且工具条不显示
		grid.getColumnModel().setHidden(2, true);
		grid.getColumnModel().setHidden(3, true);
		twoTbar.hide();
	}

	grid.addListener('rowclick', showItems);

	function showItems(grid, rowIndex, e) {
		var ss = Ext.getCmp('grid').getSelectionModel().getSelections();
		// patient = ss[0].get('patient');
		_id = ss[0].get('planId');
		var beginDate = ss[0].get('beginDate');
		beginDate = new Date(beginDate.time).format('Y-m-d');
		var planDate = ss[0].get('planDate');
		planDate = new Date(planDate.time).format('Y-m-d');
		// var store = Ext.getCmp('editGrid').getStore();
		// store.baseParams.planId = _id;
		var circle = ss[0].get('circle');
		var ctype = ss[0].get('circleType');

		/*
		 * $('input[name="itemPlan"]').val((ss[0].get('planDate') == null ||
		 * ss[0].get('planDate')=="") ? "" : new Date(
		 * ss[0].get('planDate').time).format('Y-m-d'));
		 * $('input[name="itemBegin"]').val((_beginDate == null ||
		 * _beginDate=="") ? "" : new Date( _beginDate.time).format('Y-m-d'));
		 * if(ctype == YEAR) { ctype= '年'; } else if(ctype == MONTH_OF_YEAR) {
		 * ctype= '月'; } else if(ctype == WEEK_OF_YEAR){ ctype= '周'; } else
		 * if(ctype == DAY_OF_YEAR) { ctype= '天'; }
		 * $('input[name="itemCircle"]').val(ss[0].get('circle') + "" + ctype);
		 */
		window.location.href = App.App_Info.BasePath
				+ '/module/plan/selectPlanItems_pad.html?planId=' + _id
				+ '&circle=' + circle + '&circleType=' + ctype + '&_flag='
				+ _flag + '&login=' + login + '&patient=' + patient
				+ '&beginDate='+beginDate+'&planDate='+planDate;
		/*
		 * store.load({ params: { circle:ss[0].get('circle'),
		 * circleType:ss[0].get('circleType'), start: 0, limit: 20 } });
		 * 
		 * $('input[name="itemPlan"]').val((ss[0].get('planDate') == null ||
		 * ss[0].get('planDate')=="") ? "" : new Date( ss
		 * 
		 * [0].get('planDate').time).format('Y-m-d'));
		 * $('input[name="itemBegin"]').val((_beginDate == null ||
		 * _beginDate=="") ? "" : new Date(
		 * 
		 * _beginDate.time).format('Y-m-d')); var ctype =
		 * ss[0].get('circleType') if(ctype == YEAR) { ctype= '年'; } else
		 * if(ctype == MONTH_OF_YEAR) { ctype= '月'; } else if(ctype ==
		 * WEEK_OF_YEAR){ ctype= '周'; } else if(ctype == DAY_OF_YEAR) { ctype=
		 * '天'; } $('input[name="itemCircle"]').val(ss[0].get('circle') + "" +
		 * ctype);
		 */
	}

	new Ext.Viewport( {
		layout : 'border',
		items : [ {
			region : 'center',
			layout : 'fit',
			width : 700,
			split : true,
			autoScroll : true,
			items : grid
		} ]
	});

}
