<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.juncsoft.KLJY.biomedical.entity.MemberInfo"%>
<%
MemberInfo mi = (MemberInfo)session.getAttribute("MemberInfo");
Long id = 0l;
if(mi != null){
	id = mi.getPatient().getId();
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

Ext.onReady(function(){		
	args = getQueryStringArgs();
	patientId = args["patientId"] ? args["patientId"] : <%=id%>;
	title = '化验检查结果';
	if(args["patientId"]){				
		if(args["name"]!='null'){
			title +=  '（患者姓名：'+args["name"]+'，患者编号：'+args["no"]+'）';
		}				
	}	
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
        	name: 'isPatientOrDoctorWriteZanCun'
        },{
        	name:'guiDangDate'
        }
        ])
    });   
    ds.baseParams = {
        method: 'getCheckReportsForPad',
        search: '',
        id: patientId
    };
    ds.load({
        params: {
            start: 0,
            limit: 20
        }
    });
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),{
        header: '检查日期',
        dataIndex: 'receiveDate',
        sortable : true,
        width: 15,
        renderer: function(value, meta, record, rowIndex, colIndex, store){
        	if(record.data.guiDangDate != ""){
        		return "<font color='red'>"+record.data.receiveDate+"</font>";	
        	}else{
        		return record.data.receiveDate;
        	}
        }
    }, {
        header: '检查项目',
        dataIndex: 'text',
        sortable : true,
        width: 35,
        renderer: function(value, meta, record, rowIndex, colIndex, store){
        	if(record.data.guiDangDate != ""){
        		return "<font color='red'>"+record.data.text+"</font>";	
        	}else{
        		return record.data.text;
        	}
        }
    }, {
        header: '检查结果',
        width: 25,
        sortable : true,
        dataIndex: 'receiveDate',
        renderer: function(value, meta, record, rowIndex, colIndex, store){
			return "<a href='###' onclick='openCheck(\"" + escape(record.data.text) + "\",\"" + record.data.receiveDate + "\",\"" + record.data.sectionNo + "\",\"" + record.data.testTypeNo 
						+ "\",\"" + record.data.sampleNo + "\",\"" + record.data.parItemNo +"\",\"" + record.data.isFromOutHispital
						+ "\",\"" + record.data.reportFormforRemoteId + "\",\"" + record.data.isPatientOrDoctorWriteZanCun + "\",\"" + record.data.guiDangDate + "\")'>查看</a>";
		}
    }, {
        header: '检查单位',
        width: 25,
        dataIndex: 'hospital',
        renderer: function(value, meta, record, rowIndex, colIndex, store){
        	if(record.data.guiDangDate != ""){
        		return "<font color='red'>"+record.data.hospital+"</font>";	
        	}else{
        		return record.data.hospital;
        	}
        }   
    }]);  
      
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
                		id: patientId,
                        start: 0,
                        limit: 20
                    }
                });
            }		
		},
		'-', {
            text: '录入化验检查结果',
            handler: function(){
               if(top.Member == undefined){
               		window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+patientId+'&doctorName='+top.USER.name+"&doctorOrPatient=1", '', 'width=240px;');
               }else{
               		if(window.confirm("您录入的化验检查结果将直接影响到医生对您病情的判断，为保证医生能够做出正确的判断，请务必认真核对小数点的位置、单位及参考值范围等，确保录入内容真实可靠。")){
               			window.open('../InHospitalCase/Liver/CheckReport/inputCombinationList.html?patientId='+patientId+'&patientName='+top.Member.name, '', 'width=240px;');
               		}
               }
               
            }		
		}
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
function openCheck(text,receiveDate,sectionNo,testTypeNo,sampleNo,parItemNo,isFromOutHispital,reportFormforRemoteId,isPatientOrDoctorWriteZanCun,guiDangDate){		
    	var params = '?patientId=' + patientId + '&is_check_add=check&is_pad=pad&itemName=' + text 
							+ '&receiveDate=' + receiveDate + '&sectionNo=' + sectionNo 
							+ '&testTypeNo=' + testTypeNo + '&sampleNo=' + sampleNo 
							+ '&parItemNo=' + parItemNo+'&isFromOutHispital='+isFromOutHispital
							+ '&reportFormforRemoteId=' + reportFormforRemoteId+'&isAllowUpdate='+isPatientOrDoctorWriteZanCun+'&guiDangDate='+guiDangDate;
		if(isFromOutHispital == 1){//有保存按钮
			window.location ='../InHospitalCase/Liver/CheckReport/modifyCheckReport.html' + params+'&attempDate='+new Date();	
		}else{
			window.location ='../InHospitalCase/Liver/CheckReport/checkReport.html' + params+'&attempDate='+new Date();	
		}	
				
}  	
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
</script>
</head>
<body>
</body>
</html>