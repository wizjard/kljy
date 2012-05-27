<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo"%>
<%
MemberInfo mi = (MemberInfo)session.getAttribute("MemberInfo");
Long id = 0L;
//String patID = "";
if(mi != null){
	id = mi.getPatient().getId();
	//patID= mi.getPatient().getPatientId();
}
 %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>化验检查结果查看及录入</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/PUBLIC/Scripts/jsloader_ext.js"></script>

<script type="text/javascript">
var patientId = null;
var title = false;
var tijiaoList;
var guidangList;
var guanliyuan;
Ext.onReady(function(){
	/*
	args = getQueryStringArgs();
	patientId = args["patientId"] ? args["patientId"] : <%=id%>;
	guanliyuan = args["guanliyuan"];
	title = '化验检查结果';
	if(args["patientId"]){				
		if(args["name"]!='null'){
			title +=  '（患者姓名：'+args["name"]+'，患者编号：'+args["no"]+'）';
		}				
	}
	*/
	layout();
});
function layout(){
	var ds = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	            url: App.App_Info.BasePath+'/CheckReport/combinationProject.do'
	        }),
	        reader: new Ext.data.JsonReader({
	            root: 'root',
	            totalProperty: 'total'
	        }, [{
	            name: 'text'
	        }, {
	            name: 'receiveDate'
	        }, {
	            name: 'sectionNo'
	        },  {
	            name: 'testTypeNo'
	        }, {
	            name: 'sampleNo'
	        }, {
	            name: 'parItemNo'
	        }, {
	            name: 'hospital'
	        }, {
	            name: 'isFromOutHispital'   
	        }, {
	            name: 'reportFormforRemoteId'
	        },{
	        	name: 'inputName'
	        },{
	        	name:'sheHeDate'
	        },{
	        	name:'guiDangDate'
	        },{
	        	name:'luRuDate'
	        },{
	        	name:'isPatientOrDoctorWriteZanCun'
	        },{
	        	name:'patientId'
	        },{
	        	name:'cheXiaoFlag'
	        },{
	        	name:'guiDangAuthor'
	        },{
	        	name:'patientName'
	        },{
	        	name:'patientNo'
	        },{
	        	name:'jiBingGrounp'
	        }     
	        ])
	    }); 
	if(top.USER != undefined){
		 tijiaoList = [["","--请选择--"],["是","是"],["否","否"]];	
		 guidangList = [["","--请选择--"],["是","是"],["否","否"]];
		 ds.baseParams = {
	        method: 'getMyGrounpCheckReportsForByMemberGuanLiYuanAll',
	        search: '',
	        deptCode:top.USER.DeptCode
    	}; 
	}
	else{
		alert("登录超时,为确保您的信息安全请重新登录！");
		window.top.location.href="../../module/biomedical/member/login.jsp"
		return;
	}
		
    ds.load({
        params: {
            start: 0,
            limit: 20
        }
    });
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
    {
        header: '姓名',
        dataIndex: 'patientName',
        sortable : true,
        width: 15,
        renderer: function(value){
            return value;
        }
    },{
        header: '病案号',
        dataIndex: 'patientNo',
        sortable : true,
        width: 15,
        renderer: function(value){
            return value;
        }
    },{
        header: '当前疾病分组',
        dataIndex: 'jiBingGrounp',
        sortable : true,
        width: 60,
        renderer: function(value){
            return value;
        }
    },
    {
        header: '录入时间',
        dataIndex: 'luRuDate',
        sortable : true,
        width: 30,
        renderer: function(value){
            return value;
        }
    }, {
        header: '录入项目',
        dataIndex: 'text',
        sortable : true,
        width: 35,
        renderer: function(value){
            return value;
        }
    }, {
        header: '录入者',
        dataIndex: 'inputName',
        sortable : true,
        width: 15,
        renderer: function(value){
            return value;
        }
    }, {
        header: '归档者',
        dataIndex: 'guiDangAuthor',
        sortable : true,
        width: 15,
        renderer: function(value){
        	if(value != "undefined"){
        		return value;
        	}else{
        		return "";
        	}
            
        }
    }, {
        header: '检查单位',
        width: 45,
        dataIndex: 'hospital',
        renderer: function(value){
            return value;
        } 
    } , {
        header: '患者提交日期',
        width: 30,
        dataIndex: 'sheHeDate',
        renderer: function(value){
            return value;
        } 
    } , {
        header: '医生归档日期',
        width: 30,
        dataIndex: 'guiDangDate',
        renderer: function(value){
            return value;
        } 
    } 
    /*
    , {
        header: '撤销记录',
        width: 20,
        dataIndex: 'cheXiaoFlag',
        renderer: function(value){
        	if(value == 1){
        		return '已撤销';
        	}else{
        		
        	}
        } 
    }
    */
    , {
        header: '检查结果',
        width: 25,
        sortable : true,
        dataIndex: 'receiveDate',
        renderer: function(value, meta, record, rowIndex, colIndex, store){
			return "<a href='###' onclick='openCheck(\"" + escape(record.data.text) + "\",\"" + record.data.receiveDate + "\",\"" + record.data.sectionNo + "\",\"" + record.data.testTypeNo 
						+ "\",\"" + record.data.sampleNo + "\",\"" + record.data.parItemNo +"\",\"" + record.data.isFromOutHispital
						+ "\",\"" + record.data.reportFormforRemoteId + "\",\"" + record.data.isPatientOrDoctorWriteZanCun + "\",\"" + record.data.patientId + "\",\"" + record.data.guiDangDate + "\")'>查看</a>";
		}
    }
    ]); 
     
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
                                labelWidth: 80,
                                defaults: {
                                    xtype: 'textfield',
                                    anchor: '90%'
                                },
                                items: [
                                {
                                    fieldLabel: '录入者',
                                    name: 'doctor',
									id:'doctor'
                                },{
                                    fieldLabel: '归档者',
                                    name: 'collecter',
									id:'collecter'
                                },{
                                    fieldLabel: '是否提交审核',
				                    xtype: 'combo',
				                    mode: 'local',
				                    name:'tijiao',
				                    id:'tijiao',
				                    store: new Ext.data.SimpleStore({
										 fields:['clinicValue','showClincType'],
										 data: tijiaoList
									}),
				                    displayField: 'showClincType',
									valueField: 'clinicValue',
				                    triggerAction: 'all',
				                    readOnly: true,
				                    listeners:{
										select:function(){
									      	tijiaoValue =this.getValue();
										}
									} 
                                }, {
                                    fieldLabel: '是否归档',
				                    xtype: 'combo',
				                    mode: 'local',
				                    name:'guidang',
				                    id:'guidang',
				                    store: new Ext.data.SimpleStore({
										 fields:['clinicValue','showClincType'],
										 data: guidangList
									}),
				                    displayField: 'showClincType',
									valueField: 'clinicValue',
				                    triggerAction: 'all',
				                    readOnly: true,
				                    listeners:{
										select:function(){
									      	guidangValue =this.getValue();
										}
									} 
                                }
                                , {
                                    fieldLabel: '录入日期',
                                    name: 'luRuDate',
									id:'luRuDate'
                                }, {
                                    xtype: 'panel',
                                    border: false,
                                    html: '<p style="font-size:10px;color:red;text-align:center">例：(2010-01-02)或(2010-01-01 2010-02-01)不包括括号。</p>'
                                }
                                , {
                                    fieldLabel: '归档日期',
                                    name: 'guidangDate',
									id:'guidangDate'
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
                                    store.baseParams.search = Ext.encode(values);      
                                    store.load({
                                        params: {
                                        	deptCode:top.USER.DeptCode,
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
                 
    var sm = new Ext.grid.RowSelectionModel({
        singleSelect: true
    });    
    var grid = new Ext.grid.GridPanel({
        id: 'grid', 
        title:title,       
        sm: new Ext.grid.CheckboxSelectionModel({
            sigleSelect: true
        }),
        cm: cm,
        ds: ds,
        autoScroll: true,
        viewConfig: {
            forceFit: true
        },
        sm: sm,
        tbar: ['-', {
            text: '返回列表',
            handler: function(){
                var store = Ext.getCmp('grid').getStore();
                store.baseParams.search = '';
                store.load({
                    params: {
                		deptCode: top.USER.DeptCode,
                        start: 0,
                        limit: 20
                    }
                });
            }		
		},
		/*
		'-', {
            text: '录入检验检查',
            handler: function(){
               if(top.USER != undefined){
               		window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+patientId+'&doctorName='+top.USER.name, '', 'width=240px;');
               }else{
               		window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+patientId+'&patientName='+top.Member.name, '', 'width=240px;');
               }
               
            }		
		},
		*/
		'-', new SearchButton({
                        handler: function(){
                            if (!this.sw) {
                                this.initSw();
                            }
                            this.toggleSw();
                        }
                    })
		],
        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: ds,
            displayInfo: true,
            displayMsg: '显示第<font color="red"> {0} </font>条' +
            '到<font color="red"> {1} </font>条记录，' +
            '一共<font color="red"> {2} </font>条',
            emptyMsg: "没有记录"
        })
    });
    new Ext.Viewport({
        layout: 'fit',
        items: grid
    });
}  
function openCheck(text,receiveDate,sectionNo,testTypeNo,sampleNo,parItemNo,isFromOutHispital,reportFormforRemoteId,isPatientOrDoctorWriteZanCun,patientId,guiDangDate){		
    	var params = '?attempDate='+new Date().getTime()+'&patientId=' + patientId + '&is_check_add=check&is_pad=pad&itemName=' + text 
							+ '&receiveDate=' + receiveDate + '&sectionNo=' + sectionNo 
							+ '&testTypeNo=' + testTypeNo + '&sampleNo=' + sampleNo 
							+ '&parItemNo=' + parItemNo+'&isFromOutHispital='+isFromOutHispital
							+ '&reportFormforRemoteId=' + reportFormforRemoteId+'&isAllowUpdate='+isPatientOrDoctorWriteZanCun+'&guiDangDate='+guiDangDate;
		if(isFromOutHispital == 1 && (isPatientOrDoctorWriteZanCun == 21 || isPatientOrDoctorWriteZanCun==11)){//有保存按钮
			window.location ='../InHospitalCase/Liver/CheckReport/WuSavemodifyCheckReport.html' + params+'&chexiao=1';	
		}else if(isFromOutHispital == 1){//有保存按钮
			window.location ='../InHospitalCase/Liver/CheckReport/WuSavemodifyCheckReport.html' + params;	
		}else{
			window.location ='../InHospitalCase/Liver/CheckReport/checkReport.html' + params;	
		}	
				
}  	
/*

function getQueryStringArgs(){
	var qs = (location.search.length)>0 ? location.search.substring(1) : "";
	var args = {};
	var items = qs.split("&");
	var item = null, name = null, value = null;
	for(var i=0; i<items.length; i=i+1){
		item = items[i].split("=");
		name = decodeURIComponent(item[0]);
		value = decodeURIComponent(item[1]);
		args[name] = value;
	}
	return args;

}
	*/
</script>
</head>
<body>
</body>
</html>