var deptName="";
var baseUrl=App.App_Info.BasePath+'/PatientConsultingAction.do';
var deptcodenameList = new Array();


Ext.onReady(function(){
publicCheckState();
	getAllDeptment();
	for(var i=0; i<deptcodenameList.length; i++) {
    	document.getElementById('clinicValue').options.add(new Option(deptcodenameList[i][1], deptcodenameList[i][0]));
	}
	/*
	var viewport = new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'center',
				layout:'fit',
				border:false
				//items:createGrid()
			}
		]
	});
	viewport.hide();*/
});

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
			    if(top.Member.deptCode != list[i].deptCode) {
			    	var deptcodenameList1 = new Array();
			    	deptcodenameList1.push(list[i].deptCode);
			    	deptcodenameList1.push(list[i].deptName);
			    	deptcodenameList.push(deptcodenameList1);
				}
//				deptNameOption+="<option value="+list[i].deptCode+">"+list[i].deptName+"</option>";
			}
			//deptcode = deptcodenameList[0][0];
		}
	});
}
function SaveData(){
    var applicationBacuse = document.getElementById("applicationBacuse").value;
    var clinicValue = document.getElementById("clinicValue").value;
    //var aa ={'id':1,'name':'liugang'};
    var values = {'clinicValue':clinicValue, 
                  'applicationBacuse':applicationBacuse, 
                  'applicationDeptCode':clinicValue,
                  'patientId':top.Member.patientId,
                  'oldDeptCode':top.Member.deptCode,
                  'oldGrounpId':top.Member.grounpId,
                  'flag': 0
                  };
    Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DepartmentGrounpAction.do',
		params:{
			method:'cacheApplicationRecord',
			data:Ext.encode(values)
			},
			success:function(_response){
			var res=Ext.decode(_response.responseText);
			var content = document.getElementById("content");
			if(res.success){
				alert("申请已提交");
				//document.getElementById("stateContent").value="转科中....";
				stateContent.innerText="转科申请中...";
			    content.style.display="none";
			}else{
				alert("申请未能成功提交");
			}
	     }
	 });
}
function publicCheckState(){
    var content = document.getElementById("content");
    var stateContent = document.getElementById("stateContent");
        
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
		    		if(data.applicationDeptCode == data.oldDeptCode){
		    			stateContent.innerText="转组申请中，请完成后再申请...";
		    			content.style.display="none";
		    			window.location.href = App.App_Info.BasePath + '/module/patientanddoctoroperator/patientSendList.html';
			    	}else if(data.applicationDeptCode != data.oldDeptCode)
		    		{
			    		stateContent.innerText="转科申请中，请完成后再申请...";
			    		content.style.display="none";
			    		window.location.href = App.App_Info.BasePath + '/module/patientanddoctoroperator/patientSendList.html';
		    		}
		    	}
	    	}
    	}
    });
}