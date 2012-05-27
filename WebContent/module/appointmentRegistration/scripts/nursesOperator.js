var deptcode;//科室编码
var doctorId;//诊疗医生HIS中编号
var doctorDate;//诊疗时间即出诊时间
var deptcodenameList=null;//部门列表
var doctorList = null;//医生列表
var dateListList = new Array();
//load first deparment doctorList
function loadDoctorList(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/user.do',
		params:{
			method:'findUserNameByDeptcode',
			deptCode:deptcode
		},
		sync:true,
		success:function(response){
			doctorList = new Array();
			var list = Ext.util.JSON.decode(response.responseText);
			for(var i=0,size=list.length;i<size;i++){
				var doctorListI = new Array();
				doctorListI.push(list[i].hisDoctorId);
				doctorListI.push(list[i].hisDoctorId+"     "+list[i].name);
				doctorList.push(doctorListI);
			}
			doctorId = doctorList[0][0];
		}
	});
}


Ext.onReady(function(){
	getAllDeptment();
	deptcode = deptcodenameList[0][0];//科室编码动态改变加载对应的数据
	loadDoctorList();
	var simpleForm = new Ext.FormPanel({ 
      labelAlign: 'right', 
      broder:false,
      buttonAlign:'right', 
      bodyStyle:'padding:5px', 
      width: 600, 
      frame:true, 
      layout:'column',
      defaults:{
      	border:false,
      	layout:'form'
      },
      items: [
      	{
      		columnWidth:.3,
      		labelWidth:100,
      		items:[
      			{
		      		fieldLabel:'诊疗科室',
		      		xtype:'combo',
		      		mode: 'local',
		      		store: new Ext.data.SimpleStore({
		      			fields:['clinicValue','showClincType'],
		                data: deptcodenameList
		           }),
		      	   displayField: 'showClincType',
		      	   valueField: 'clinicValue',
		      	   value:deptcodenameList[0][1],
		      	   triggerAction: 'all',
		      	   readOnly: true,
		      	   anchor:'100%',
		      	   listeners:{
			      	   	  select:function(){
      						deptcode =this.getValue();
      						loadDoctorList();
      						var _store=Ext.getCmp('doctor').getStore();
//      						_store.removeAll();移除所有值
      						_store.loadData(doctorList);
      						var _storeValue=Ext.getCmp('doctor');
      						_storeValue.setValue(doctorList[0][1]);
      						var div = document.getElementById("id");
							div.innerHTML = createDateView();
			      	   	  }
			      	   }
		      	}
      		]
      	},{
      		columnWidth:.3,
      		labelWidth:100,
      		items:[
      			{
		      		fieldLabel:'诊疗医生',
		      		xtype:'combo',
		      		mode: 'local',
		      		id:'doctor',
		      		store: new Ext.data.SimpleStore({
		      			fields:['clinicValue','showClincType'],
		                data: doctorList
		           }),
		      	   displayField: 'showClincType',
		      	   valueField: 'clinicValue',
		      	   value:doctorList[0][1],
		      	   triggerAction: 'all',
		      	   readOnly: true,
		      	   anchor:'100%',
		      	   listeners:{
			      	   	select:function(){
      						doctorId =this.getValue();
      						var div = document.getElementById("id");
							div.innerHTML = createDateView();
			      	   	}
			      }
		      	}
      		]
      	},{
      		columnWidth:.3,
      		labelWidth:100,
      		items:[
      			{
      				fieldLabel:'选择日期',
		      		xtype:'combo',
		      		mode: 'local',
		      		store: new Ext.data.SimpleStore({
		      			fields:['clinicValue','showClincType'],
		                data: dateListList
		           }),
		      	   displayField: 'showClincType',
		      	   valueField: 'clinicValue',
		      	   value:dateListList[0][1],
		      	   triggerAction: 'all',
		      	   readOnly: true,
		      	   anchor:'100%',
		      		 listeners:{
			      	   	  select:function(){
      						doctorDate =this.getValue(); 
      						var div = document.getElementById("id");
							div.innerHTML = createDateView();
			      	   	  }
			      	   }	      	    
		      	}
      		]
      	}
      ]
	}); 	
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'north',
				layout:'fit',
				border:false,
				height:50,
				items:simpleForm
			},{
				region:'center',
				layout:'fit',
				autoScroll:true,
				border:false,
				listeners:{
					render:function(){
						var innerHTMLOne = '<table border="1" width="800">'
						+'<tr style="height:40px;text-align:center" bgcolor="#4682B4"><td>星期日</td><td>星期一</td><td>星期二</td><td>星期三</td><td>星期四</td><td>星期五</td><td>星期六</td></tr>'
						+'<tr style="height:70px;text-align:center">';
						for(var j=0;j<7;j++){
							innerHTMLOne+='<td width="110" ondblclick="designOneMonth(this,'+j+')">&nbsp;</td>';
						}
						innerHTMLOne+='</tr>'
							+'<tr style="text-align:center;height:30px"><td colspan="7"><button onclick="saveOneMonth()">设置月度排班表</button></td></tr>'
							+'</table>';
						var div = document.createElement("div");
						div.innerHTML = '<div style="padding:25px,0,0,67px">'+innerHTMLOne+'</div>'+'<div id="id" style="padding:5px,0,0,67px">'+createDateView()+'</div>';
						this.body.dom.appendChild(div);
					}
				}
			}
		]
	});
});


function getAllDeptment(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
		params:{
			method:'findAllSYS_HIS_DepartmentHISEntity'
		},
		sync:true,
		success:function(response){
			deptcodenameList =new Array();
			var data = Ext.util.JSON.decode(response.responseText);
			var list = data.department;
			var dateList = data.dateList;
			for(var i=0,size=list.length;i<size;i++){
				var deptcodenameList1 = new Array();
				deptcodenameList1.push(list[i].deptCode);
				deptcodenameList1.push(list[i].deptCode+"   "+list[i].deptName);
				deptcodenameList.push(deptcodenameList1);
			}
			for(var i=0,size=dateList.length;i<size;i++){
				var dateList1 = new Array();
				dateList1.push(dateList[i].dateValue);
				dateList1.push(dateList[i].dateList);
				dateListList.push(dateList1);
			}
			doctorDate = dateListList[0][0];
		}
	});
}

//创建日期显示视图
function createDateView(){
	var weekList=null;
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
		params:{
			method:'findAllDayInWeek',
			date:doctorDate,
			deptCodeId:deptcode,
			doctorId:doctorId
		},
		sync:true,
		success:function(response){
			var data = Ext.util.JSON.decode(response.responseText);
			weekList = data.weekList;
		}
	});
	var start = doctorDate.indexOf('-');
	var monthValue = doctorDate.substring(start+1,start+3);
	if(monthValue.substring(0,1)=="0"){
		monthValue = monthValue.substring(1,2);
	}
	monthValue +="月";
	var innerHTML='<table cellspacing="0" border="1" width="800"><tr style="text-align:center;height:30px" bgcolor="#ABABAB"><td colspan="7"><font color="red"><B>'+monthValue+'</B></font></td></tr>'
		+'<tr style="height:40px;text-align:center;" bgcolor="#4682B4"><td>星期日</td><td>星期一</td><td>星期二</td><td>星期三</td><td>星期四</td><td>星期五</td><td>星期六</td></tr>'
		+weekList
		+'<tr style="text-align:center;height:30px"><td colspan="7"><button onclick="saveData()">日排班表调整</button></td></tr>'
		+'</table>';
	return innerHTML;
}


function show(_this,_day,weekDay){
	var div =_day+'&nbsp;&nbsp;<select width="30" height="40" name="selectOption" multiple><option></option>';
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
		params:{method:'findBaseSignAPEntity'},
		sync:true,
		success:function(response){
			var data = Ext.util.JSON.decode(response.responseText);
			for(var i=0;i<data.length;i++){
				var ap = null;
				if(data[i].timeAP=="A"){
					ap="上午";
				}else{
					ap="下午";
				}
				div+='<option value='+_day+'、'+data[i].id+'、'+weekDay+'>'+ap+data[i].outType
					+'</option>';
			}
		}
	});
	_this.innerHTML = div+'</select>';
	_this.width="110";
}

var selectOptionList =[];

function saveData(_value){
	var optionList = document.getElementsByName("selectOption");
	if(optionList.length>0){
		for(var i=0;i<optionList.length;i++){
			var objOption = optionList[i];
			var lengthNum =1;
			for(var j=0;j<objOption.options.length;j++){
				if(objOption.options[j].selected){
					if(lengthNum >= 3){
						alert("当天设置的出诊情况最多只能为两种");
						return;
					}else{
						if(objOption.options[j].value != ""){
							lengthNum ++;
							selectOptionList.push(objOption.options[j].value);
						}
					}
				}
			}
		}
	}
	if(selectOptionList.length == 0){
		alert("没有数据需要保存");
		var div = document.getElementById("id");
		div.innerHTML = createDateView();
		return;
	}
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
		params:{
			method:'savePlanSignOrderEntity',
			doctorId:doctorId,
			deptCodeId:deptcode,
			currentDate:doctorDate,
			data:Ext.encode(selectOptionList)
		},
		sync:true,
		success:function(response){
			var res = Ext.util.JSON.decode(response.responseText);
			if(res.success){
				alert("保存成功");
				var div = document.getElementById("id");
				div.innerHTML = createDateView();
			}else{
				alert("保存失败");
			}
		}
	});
	selectOptionList =[];
}

//设置一周，同步一月
function designOneMonth(_this,_week){
	var oneMonthString = '&nbsp;&nbsp;<select width="30" height="40" name="selectOneMonthOption" multiple><option></option>';
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
		params:{method:'findBaseSignAPEntity'},
		sync:true,
		success:function(response){
			var data = Ext.util.JSON.decode(response.responseText);
			for(var i=0;i<data.length;i++){
				var ap = null;
				if(data[i].timeAP=="A"){
					ap="上午";
				}else{
					ap="下午";
				}
				oneMonthString+='<option value='+data[i].id+'、'+_week+'>'+ap+data[i].outType
					+'</option>';
			}
		}
	});
	oneMonthString +='</select>';
	_this.innerHTML = oneMonthString;
	_this.width="110";
}

//设置一周，同步一个月
var selectOneMonthOptionList = [];
function saveOneMonth(){
	var optionMonthList = document.getElementsByName("selectOneMonthOption");
	if(optionMonthList.length>0){
		for(var i=0;i<optionMonthList.length;i++){
			var objOption = optionMonthList[i];
			var lengthNum =1;
			for(var j=0;j<objOption.options.length;j++){
				if(objOption.options[j].selected){
					if(lengthNum >= 3){
						alert("当天设置的出诊情况最多只能为两种");
						return;
					}else{
						if(objOption.options[j].value != ""){
							lengthNum ++;
							selectOneMonthOptionList.push(objOption.options[j].value);
						}
					}
				}
			}
		}
	}
	if(selectOneMonthOptionList.length == 0){
		alert("没有数据需要保存");
		var div = document.getElementById("id");
		div.innerHTML = createDateView();
		return;
	}
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
		params:{
			method:'savePlanSignOrderOneMonthEntity',
			doctorId:doctorId,
			deptCodeId:deptcode,
			currentDate:doctorDate,
			data:Ext.encode(selectOneMonthOptionList)
		},
		sync:true,
		success:function(response){
			var res = Ext.util.JSON.decode(response.responseText);
			if(res.success){
				alert("保存成功");
				var div = document.getElementById("id");
				div.innerHTML = createDateView();
			}else{
				alert("保存失败");
			}
		}
	});
	selectOneMonthOptionList = [];
}
