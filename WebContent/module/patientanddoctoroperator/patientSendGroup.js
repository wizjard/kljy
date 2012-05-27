var deptName="";
var baseUrl=App.App_Info.BasePath+'/PatientConsultingAction.do';
var deptcodenameList = new Array();


Ext.onReady(function(){
publicCheckState();
	
	
});
/*
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
			for(var i=0,size=list.length;i<size;i++){
				var deptcodenameList1 = new Array();
				deptcodenameList1.push(list[i].deptCode);
				deptcodenameList1.push(list[i].deptName);
				deptcodenameList.push(deptcodenameList1);
//				deptNameOption+="<option value="+list[i].deptCode+">"+list[i].deptName+"</option>";
			}
			deptcode = deptcodenameList[0][0];
		}
	});
}*/
function SaveData(){
    var applicationBacuse = document.getElementById("applicationBacuse").value;
    //var clinicValue = document.getElementById("clinicValue").value;
    //var aa ={'id':1,'name':'liugang'};
    var values = {//'clinicValue':clinicValue, 
                  'applicationBacuse':applicationBacuse, 
                  //'applicationDeptCode':deptcode,
                  'patientId':top.Member.patientId,
                  'oldDeptCode':top.Member.deptCode,
                  'oldGrounpId':top.Member.grounpId
                  };
    Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
		params:{
			method:'saveApplicationRecord',
			data:Ext.encode(values)
			},
			success:function(_response){
			var res=Ext.decode(_response.responseText);
			var content = document.getElementById("content");
			if(res.success){
				alert("申请已提交");
				//document.getElementById("stateContent").value="转科中....";
				stateContent.innerText="转组申请中...";
			    content.style.display="none";
			}else{
				alert("申请未能成功提交");
			}
	     }
	 });
}
function publicCheckState(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
		params:{
			method:'findCurrentState',
			id:top.Member.patientId
		},
		scope:this,
		success:function(_response){
			var _res=Ext.util.JSON.decode(_response.responseText);
			if(_res.success){
			var data = Ext.util.JSON.decode(_res.data);
			if(data !=null){
				var stateContent = document.getElementById("stateContent");
				var content = document.getElementById("content");
				if(data.applicationDeptCode == data.oldDeptCode){
					stateContent.innerText="转组申请中，请完成后再申请...";
					content.style.display="none";
					//alert(stateContent.value);
					window.location.href = App.App_Info.BasePath + '/module/patientanddoctoroperator/patientSendList.html';
				}else if(data.applicationDeptCode != data.oldDeptCode)
				{
					stateContent.innerText="转科申请中，请完成后再申请...";
					content.style.display="none";
					//alert(stateContent.value);
					window.location.href = App.App_Info.BasePath + '/module/patientanddoctoroperator/patientSendList.html';
				}
			}
		}
	}
 });
}