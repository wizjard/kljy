
var deptcodenameList=new Array();//部门列表
var deptcode;//科室编码
var pid;
var _id;
var _count; //随访次数

var KID= App.util.getHtmlParameters('KID');

$(function(){
	//FormUtil.toDateField({el:$('input[name="enterDate"]')});//初始化生日选择框
	FormUtil.toDateField({el:$('input[name="beginDate"]')});//初始化生日选择框
	//toDate({el:$('input[name="beginDate"]')});
	FormUtil.initBtnCss();//初始化页面按钮
	

	_id= App.util.getHtmlParameters('planId');
	_isAdd = (_id == null||_id.length < 1 || _id == 'null') ? true : false;
	
	pid = App.util.getHtmlParameters('pid');
	_count = App.util.getHtmlParameters('pcount');
	// add
	if(pid && pid > 0) {
	   setPatientInfo(pid);
	  
	   //getAllDeptment();
	}
	// edit
	if(_id&&_id>0){
		//getAllDeptment();
		setFormValue(_id);
		$('input[name="id"]').val(_id);
	}
	else {
		 setPanCount(pid);
		//setTimeout(function(){SaveData(true)}, 300);
		initSave(pid);
	}
});



function checkDate(b){  
    var a = /^\d{4}[-]\d{2}[-]\d{2}$/; 
    var c = /^\d{4}[-]\d{1}[-]\d{1}$/;   
    if(!a.test(b) && !c.test(b)){   
    	alert(b + "随访计划设置日期格式不正确");  
        return false;   
    }   
    return true;  
}  

function setPanCount(pid) {
	if(pid == null || pid < 1) {
		$('input[name="planTime"]').val(1);
		return;
	}
	$.post(
			App.App_Info.BasePath+'/plan.do',
			{
				method:'getPCount',
				pid:pid
			},			
			function(data){
				var pcount = JSON.parse(data.data);
				
				setTimeout(function(){
					if(pcount && pcount.pcount){
						$('textarea[name="planTime"]').val(pcount.pcount + 1);
						$('#titleFlag').text(pcount.pcount + 1);
						planTime=pcount.pcount + 1;
						$('input[name="planTime"]').val(planTime);
					}else{
						$('input[name="planTime"]').val(1);
						$('#titleFlag').text(1);
						planTime=1 ;
						$('input[name="planTime"]').val(planTime);
					}
				}, 300);
				
			},
			'json'
		);
}

function setPatientInfo(id) {
	$.post(
			App.App_Info.BasePath+'/patient.do',
			{
				method:'findById',
				id:id
			},
			
			function(data){
				var _patient=JSON.parse(data.data);
				setTimeout(function(){
				$('input[name="patientName"]').val(_patient.patientName);
				$('input[name="patientNo"]').val(_patient.patientNo);
				$('input[name="sex"]').val(_patient.gender == '1' ? '男' : '女');
				$('input[name="birthday"]').val(_patient.birthday);
				$('input[name="patientId"]').val(_patient.id);

				if(_isAdd) {
					var now = new Date();
					$('input[name="beginDate"]').val(now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate());
				}
				
				$('input[name="diagGrounp"]').val(_patient.diagGrounp);
				}, 300);
			},
			'json'
		);
}

//查找所有的科室
function getAllDeptment(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PatientConsultingAction.do',
		params:{
			method:'findAllSYS_HIS_DepartmentHISEntity',
			name:'inHospitalType',
			nameValue:1
		},
		sync:true,
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			var deptNameOption = "";
			//alert(list.length + "|");
			for(var i=0,size=list.length;i<size;i++){
				var deptcodenameList1 = new Array();
				deptcodenameList1.push(list[i].deptCode);
				deptcodenameList1.push(list[i].deptName);
				deptcodenameList.push(deptcodenameList1);
				deptNameOption+="<option value="+list[i].deptCode+">"+list[i].deptName+"</option>";
			}
			deptcode = deptcodenameList[0][0];//科室编码动态改变加载对应的数据
			$('select[name="deptCode"]').append(deptNameOption);
			
			selectOneDept();
		}
	});
}

function selectOneDept(){
	deptcode = $('select[name="deptCode"]').val();
	$('input[name="deptname"]').val($('select[name="deptCode"]').find("option:selected").text());
	
	getAllDeptmentGrounp();
	
}

function selectTeam() {
	$('input[name="teamname"]').val($('select[name="groupId"]').find("option:selected").text());
}

//查找某个科室下的责任小组
function getAllDeptmentGrounp(){
	deptcodenameGrounpList = new Array();
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
		params:{
			method:'findDepartmentGrounpByDeptId',
			deptCode:deptcode
		},
		sync:true,
		success:function(response){
			var list = Ext.util.JSON.decode(response.responseText);
			var deptNameGrounp="";
			for(var i=0,size=list.length;i<size;i++){
				var deptGrounp = new Array();
				deptGrounp.push(list[i].id);
				deptGrounp.push(list[i].grounpName);
				deptcodenameGrounpList.push(deptGrounp);
				deptNameGrounp+="<option value="+list[i].id+">"+list[i].grounpName+"</option>";
			}
			$('select[name="groupId"]').html(deptNameGrounp);
			selectTeam();
			deptcodeGrounp = deptcodenameGrounpList[0][0];
			grounpName = deptcodenameGrounpList[0][1];
		}
	});
}

function setFormValue(_id){
	$.post(
			App.App_Info.BasePath+'/plan.do',
			{
				method:'findPlanById',
				id:_id
			},
			function(data){
				if(data.success){
					var _patient=JSON.parse(data.data);
					
					FormUtil.setFormValues('form',_patient);
					
					pid = _patient.patient.id;
					setPatientInfo(_patient.patient.id);
					window.parent._planDate = data.msg;
					window.parent._pid = _patient.patient.id;
					
				}else{
					alert('提取随访计划信息失败。');
				}
			},
			'json'
		);
}

function initSave(pidt) {
	if(pidt == null || pidt < 1) {
	   return;
	}
	
	$.post(
			App.App_Info.BasePath+'/plan.do',
			{
				method:'saveOrUpdate',
				patid : pidt
			},
			function(data){
				if(data.success){
					var _patient=JSON.parse(data.data);
					//alert(_patient + "|" + _patient.data + "=1=" + _patient.state + "|" + _patient['createDate']);
					$('input[name="id"]').val(_patient['id']);
						_id= _patient['id'];
						window.parent._id = _id;
						window.parent._pid = pid;
						window.parent._planDate = data.msg; 
				}else{
						alert('保存失败。');
				}
			},
		'json'
	);
}

function SaveData(init){
	
	//获取Form表单的信息
	var _values=FormUtil.getFormValues('form');
	
	if(!ValidForm(_values))	return;
	//if(!checkDate(document.getElementById("beginDate").value))	return;
	
	var _data= JSON.stringify(_values);
	$.post(
		App.App_Info.BasePath+'/plan.do',
		{
			method:'saveOrUpdate',
			data:_data,
			isAdd:_isAdd
		},
		function(data){
			if(data.success){
				var _patient=JSON.parse(data.data);
				//alert(_patient + "|" + _patient.data + "=1=" + _patient.state + "|" + _patient['createDate']);
				$('input[name="id"]').val(_patient['id']);
				$('input[name="createDate"]').val(_patient['createDate']);
				$('input[name="modifyDate"]').val(_patient['modifyDate']);

				if(init) {

				}
				else {
					if(window.parent.getItemCount() < 1) {
					   alert("请增加随访项目");
					   return;
					}
					
					
					window.parent.updatePlanData(_isAdd);
					setTimeout(function(){
						window.parent.parent.closeTabPanel('/module/plan/selectPlan.html?paId='+ pid + "&type=plan&KID=" + KID);
						App.util.addNewMainTab('/module/plan/selectPlan.html?paId='+ pid+ "&type=plan&KID=" + KID,'随访计划');

						// 编辑
						if(!_isAdd) {
						//alert("更新成功！");
						    window.parent.parent.closeTabPanel('/module/plan/addPlanItem.html?planId='+_id + "&KID=" + KID);
						}
						// 新增
						else {
							//alert("新增成功！");
							window.parent.parent.closeTabPanel('/module/plan/addPlanItem.html?pid='+pid + "&KID=" + KID);
						}  
					}, 300);
				}
				
			}else{
				alert('保存失败。');
			}
		},
		'json'
	);
}
function ValidForm(_values){
	var notNulls=[
		['planTime','随访次数'],
		['patientName','姓名'],
		['beginDate','随访计划设置日期'],
		['cricle','随访周期']
	
	];
	var _return=true;
	$.each(notNulls,function(){
		var _val=_values[this[0]];
		
		if(_val == null) {
		   return true;
		}
		
		if(_val.length==0){
			alert('< '+this[1]+' > 不能为空。');
			_return=false;
			return false;
		}
	});
	return _return;
}
