var deptCode = App.util.getHtmlParameters('deptCode');
var deptName = decodeURIComponent(App.util.getHtmlParameters('deptName'));
var outTypeIdList = '1,2';

Ext.onReady(function(){	
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'center',
				layout:'fit',
				border:false,
				autoScroll: true,
				listeners:{
					render:function(){
						var div = document.createElement("div");
						div.innerHTML = '<div id="id" style="padding:0,0,0,0">'+createDateView()+'</div>';
						this.body.dom.appendChild(div);
					}
				}
			}
		]
	});
});

function getOutTypeIdList(_this,_num1,_num2){
	//_this.parentNode.style.backgroundColor="red";
	outTypeIdList="";
	outTypeIdList+=_num1+','+_num2;
	var divId = document.getElementById("id");
	divId.innerHTML = createDateView();;
}

//创建日期显示视图 
function createDateView(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
		params:{
			method:'findAllDoctorNursesFour',
			deptCodeId:deptCode,
			outTypeIdList:Ext.encode(outTypeIdList)
		},
		sync:true,
		success:function(response){
			var res = Ext.util.JSON.decode(response.responseText);
			innerContent=res.weekList;
		}
	});
	var innerContentAll ='<div class="yygh">'
					+'<table cellpadding="0" cellspacing="0" width="100%" class="tylb">'
					+'<tr>'
					+'<td class="jclb_1">预约科室：'+deptName+'</td>'
					+'<td class="jclb_2"><button onclick="backFirstPage()">返回</button></td>'
					//liugang 2011-08-10 start
					+'<tr><td><div id="submenu"></td></tr>'
					//liugang 2011-08-10 end
					+'<tr><td colspan="2" class="jclb_5">'
					+'<button onclick="getOutTypeIdList(this,1,2)">普通门诊</button>&nbsp;&nbsp;&nbsp;&nbsp;'
					+'<button onclick="getOutTypeIdList(this,3,4)">专家门诊</button>&nbsp;&nbsp;&nbsp;&nbsp;'
					+'<button onclick="getOutTypeIdList(this,5,6)">会员门诊</button>&nbsp;&nbsp;&nbsp;&nbsp;'
					+'</td></tr></table><br/>' 
	+'<table cellpadding="0" cellspacing="0" width="100%" class="tylb">'
	+'<tr class="blue"><td width="8%" class="jclb">医生</td><td width="8%" class="jclb">职称</td>'+innerContent
	+'</table>';
	return innerContentAll;
}

function showPlanSignOrderPatientEntity(_this1,_this2,_this3){
	var innerContent="";
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
		params:{
			method:'findPlanSignOrderPatient',
			planDate:_this3,
			bsAPId:_this2,
			deptCode:deptCode,
			doctorId:_this1
		},
		sync:true,
		success:function(response){
			var res = Ext.util.JSON.decode(response.responseText);
			innerContent=res.returnString;
		}
	});
	document.getElementById("messages").innerHTML=innerContent;
	document.getElementById("cover").style.display ="block";
}


//关闭按钮
function hidden(){
	document.getElementById("cover").style.display="none";
}

//保存会员预约数据
function savePlanSignOrderPatientData(_this1,_this2,_this3,_this4,_this5){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
		params:{
			method:'savePlanSignOrderPatientEntity',
			doctorId:_this1,
			deptCode:_this2,
			bsAPId:_this3,
			bsTSId:_this4,
			planDate:_this5
		},
		sync:true,
		success:function(response){
			var res = Ext.util.JSON.decode(response.responseText);
			if(res.success){
				alert("预约成功");
				hidden();
				window.location.href=App.App_Info.BasePath+'/module/appointmentRegistration/patientPlanOrder.html';
			}else{
				if(res.msg == "isHave"){
					alert("您在当天已有成功的预约挂号");
					hidden();
				}else{
					alert("登录超时，请重新登录！");
					window.top.location.href =App.App_Info.BasePath+"/module/biomedical/member/loginMember.jsp";
				}
			}
		}
	});
}

function backFirstPage(){
	window.location.href = "departmentList.html";
}


