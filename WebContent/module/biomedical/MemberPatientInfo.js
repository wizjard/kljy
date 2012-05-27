var deptcodenameList=new Array();//部门列表
var deptcode;//科室编码
var inWard;//入会科室
var grounpName;//会员所在科室的责任小组名称
var deptcodenameGrounpList = new Array();//责任小组List
var deptcodeGrounp;//怎人小组ID
var deptNameOption="<option></option>";
var _id;
var hspCount = App.util.getHtmlParameters('inHspTimes');//住院次数
//2011-12-21添加
var caseId = App.util.getHtmlParameters('caseId');//住院记录ID
var outDate = App.util.getHtmlParameters('outDate');//门诊时间
$(function(){
	getAllDeptment();
	getAllDeptmentGrounp();
	FormUtil.toDateField({el:$('input[name="birthday"]')});//初始化生日选择框
	FormUtil.toDateField({el:$('input[name="inDate"]')});//初始化生日选择框
	FormUtil.initBtnCss();//初始化页面按钮
	initDictFieldList();//初始化字典选项候选列表
	 _id=App.util.getHtmlParameters('id');
	if(_id&&_id>0){
		setFormValue(_id);
	}else{
		getLoginUserInfo();
	}
	
	$('input[name="contacterRelationship"]').click(function(){
		$('select[name="memberStatus"]').hide();
		$('select[name="deptCode"]').hide();
		$('select[name="grounpId"]').hide();
		//$("div:contains('配偶'):parent [id='Sel*']").css("z-index","10").css("position", "absolute");
		//alert($("div:contains('配偶')").html());
		//alert($("div:contains('配偶')").parent().html());
		//alert($("div:contains('配偶')").parent().parent().html());
		$("div:contains('配偶')").parent().bind('mouseleave',function(){
			$('select[name="memberStatus"]').show();
			$('select[name="deptCode"]').show();
	    	$('select[name="grounpId"]').show();
		});
	});
	
	$('input[name="people"]').click(function(){
		$('select[name="memberStatus"]').hide();
		$('select[name="deptCode"]').hide();
		$('select[name="grounpId"]').hide();
		//$("div:contains('配偶'):parent [id='Sel*']").css("z-index","10").css("position", "absolute");
		$("div:contains('汉族')").parent().bind('mouseleave',function(){
			$('select[name="memberStatus"]').show();
			$('select[name="deptCode"]').show();
	    	$('select[name="grounpId"]').show();
		});
	});
	
	$('input[name="province"]').click(function(){
		$('select[name="memberType"]').hide();
		$('select[name="biaoben"]').hide();
		//$("div:contains('配偶'):parent [id='Sel*']").css("z-index","10").css("position", "absolute");
		$("div:contains('北京市')").parent().bind('mouseleave',function(){
			$('select[name="memberType"]').show();
			$('select[name="biaoben"]').show();
		});
	});
	
});

function getLoginUserInfo(){
	$.post(
		App.App_Info.BasePath+'/user.do',
		{
			method:'getLoginUserInfo'
		},
		function(data){
			$('input[name="currentWardCode"]').val(data['belong']);
//			$('input[name="currentWardCodeShow"]').val(data['单位']+' ('+data['机构']+')');
		},
		'json'
	);
}

var showGrounp = 0;
function setFormValue(_id){
	$.post(
		App.App_Info.BasePath+'/patient.do',
		{
			method:'findById',
			id:_id
		},
		function(data){
			if(data.success){
				var _patient=JSON.parse(data.data);
				FormUtil.setFormValues('form',_patient);
//				$('input[name="currentWardCodeShow"]').val(
//					FormUtil.getIndependentDictionaryText('belong',$('input[name="currentWardCode"]').val())+' ('+
//					FormUtil.getIndependentDictionaryText('parent_belong',$('input[name="currentWardCode"]').val())+')')
					$('input[name="inWard"]').val(top.USER.单位);
					$('input[name="inDoctor"]').val(top.USER.name);
					$('input[name="inDate"]').val(new Date().format('Y-m-d'));
					$('input[name="account"]').val(_patient.patientId);
					$('input[name="password"]').val(_patient.idNo);
					$('input[name="outDeptCode"]').val(_patient.outDeptCode);
					if(hspCount != null){
						$('input[name="inHspTimes"]').val(hspCount);
					}
					if(outDate != null){
						$('input[name="inOutTimesDate"]').val(outDate);
					}
			}else{
				alert('提取病人信息失败。');
			}
		},
		'json'
	);
	Ext.Ajax.request({
			url:App.App_Info.BasePath + '/biomedical.do',
			params:{
				method:'getMemberByPatientId',
				patientId:_id
			},
			success:function(response){
				var res=Ext.decode(response.responseText);
				if(res.success){
					var data=Ext.decode(res.data);
					if(!data)
						data={};
					Ext.apply(data,{patientId:_id});
					$('input[name="memberNo"]').val(data.memberNo);
					$('input[name="inDate"]').val(data.inDate);
					$('input[name="account"]').val(data.account);
					$('input[name="password"]').val(data.password);
					$('select[name="memberStatus"]').val(data.memberStatus);
					$('select[name="memberType"]').val(data.memberType);
					$('input[name="inWard"]').val(data.inWard);
					$('select[name="biaoben"]').val(data.biaoben);
					$('select[name="deptCode"]').val(data.deptCode);
					deptcode = data.deptCode;
					getAllDeptmentGrounp();
					showGrounp = data.grounpId;
					findGrounpName(showGrounp);
					$('input[name="inDoctor"]').val(data.inDoctor);
//					$('input[name="inHspTimes"]').val(data.inHspTimes);
					if(data.inHspTimes != null){
						$('input[name="inHspTimes"]').val(data.inHspTimes);
					}
					if(data.inOutTimesDate != null){
						$('input[name="inOutTimesDate"]').val(data.inOutTimesDate);
					}
					$('input[name="memo"]').val(data.memo);
					$('select[name="grounpId"]').val(data.grounpName);
					if(data.memberNo != null){
						$('select[name="biaoben"]').attr('disabled',true);
						$('select[name="deptCode"]').attr('disabled',true);
						$('select[name="grounpId"]').attr('disabled',true);
					}
				}else{
					alert('获取病人会员信息失败。');
				}
			}
		});
}
function initDictFieldList(){
	var _el=$('input[name="gender"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('gender_gb'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="idType"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('idType'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="nationality"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('nationality'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="province"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('province'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="people"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('people'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="occupation"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('occupation'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="marrageStatus"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('marrageStatus'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="educationLv"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('educationLv'));
	FormUtil.toCombobox({el:_el});
	_el=$('input[name="contacterRelationship"]');
	_el.data('options',FormUtil.getIndependentDictionaryList('relationship'));
	FormUtil.toCombobox({el:_el});
}


function SaveData(){
	var _values=FormUtil.getFormValues('form');
	
	if(!ValidForm(_values))	return;
    _values.inWardCode = top.USER.DeptCode
    //liugang2011-12-21 新增复制新增病历
    _values.caseId = caseId;
	var _data=JSON.stringify(_values);
	$.post(
		App.App_Info.BasePath+'/patient.do',
		{
			method:'saveOrUpdatePM',
			data:_data
		},
		function(data){
			if(data.success){
				var _patient=JSON.parse(data.data);
				$('input[name="id"]').val(_patient['id']);
				$('input[name="createDate"]').val(_patient['createDate']);
				$('input[name="modifyDate"]').val(_patient['modifyDate']);
				alert('保存成功。');
				parent.closeTabPanel();
//				loadPatSimInfo();
//				alert("adsfasdf");
//				closeTabPanel();
			}else{
				alert('保存失败。');
			}
		},
		'json'
	);
}
function ValidForm(_values){
	var notNulls=[
//		['patientNo','病案号'],
		['patientName','姓名'],
		['gender','性别'],
		['birthday','出生日期'],
		['idType','证件类型'],
		['idNo','证件号码'],
		['nationality','国籍'],
		['people','民族'],
//		['province','籍贯'],
//		['occupation','职业'],
//		['marrageStatus','婚姻状况'],
		['mobilePhone','手机'],
		['homeTel','家庭电话'],
		['homeAddr','家庭地址'],
		['contacterName','联系人姓名'],
		['contacterTel','联系人电话'],
		['contacterRelationship','与患者关系'],
//		['currentWardCode','当前或上次所在科室'],
		['inDate','入会日期'],
		['account','用户名'],
		['password','密码'],
		['memberStatus','会员状态'],
		['memberType','会员类型'],
		['inWard','入会科室'],
		['deptCode','责任科室'],
		['inDoctor','入会医生'],
		['grounpId','责任小组']
//		['inHspTimes','住院次数']		
	];
	var _return=true;
	$.each(notNulls,function(){
		var _val=_values[this[0]];
		if(_val.length==0){
			alert('< '+this[1]+' > 不能为空。');
			_return=false;
			return false;
		}
	});
	return _return;
}

function selectOneDept(){
	deptcode = $('select[name="deptCode"]').val();
	getAllDeptmentGrounp();
	if($('select[name="grounpId"]').html() != ""){
		document.getElementById("grounpShow").style.display="block";
		Ext.Ajax.request({
			url:App.App_Info.BasePath+'/DepartmentHISAction.do',
			params:{
				method:'findDoctornameByGrounpId',
				grounpId:$('select[name="grounpId"]').val()
			},
			sync:true,
			success:function(response){
				var res = Ext.util.JSON.decode(response.responseText);
				document.getElementById("grounpAllName").innerHTML = res.doctorNameList;
			}
		});
	}
}

function findGrounpName(_num){
	if(_num > 0){
		if($('select[name="grounpId"]').html() != ""){
			document.getElementById("grounpShow").style.display="block";
			Ext.Ajax.request({
				url:App.App_Info.BasePath+'/DepartmentHISAction.do',
				params:{
					method:'findDoctornameByGrounpId',
					grounpId:_num
				},
				sync:true,
				success:function(response){
					var res = Ext.util.JSON.decode(response.responseText);
					document.getElementById("grounpAllName").innerHTML = res.doctorNameList;
				}
			});
		}
//		$('select[name="grounpId"]').val(_num);
	}else{
		if($('select[name="grounpId"]').html() != ""){
			document.getElementById("grounpShow").style.display="block";
			Ext.Ajax.request({
				url:App.App_Info.BasePath+'/DepartmentHISAction.do',
				params:{
					method:'findDoctornameByGrounpId',
					grounpId:$('select[name="grounpId"]').val()
				},
				sync:true,
				success:function(response){
					var res = Ext.util.JSON.decode(response.responseText);
					document.getElementById("grounpAllName").innerHTML = res.doctorNameList;
				}
			});
		}
	}
	
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
			for(var i=0,size=list.length;i<size;i++){
				var deptcodenameList1 = new Array();
				deptcodenameList1.push(list[i].deptCode);
				deptcodenameList1.push(list[i].deptName);
				deptcodenameList.push(deptcodenameList1);
				deptNameOption+="<option value="+list[i].deptCode+">"+list[i].deptName+"</option>";
			}
			deptcode = deptcodenameList[0][0];//科室编码动态改变加载对应的数据
			$('select[name="deptCode"]').append(deptNameOption);
		}
	});
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
			$('select[name="grounpId"]').html(deptNameGrounp);
			//liugang 2011-08-22 start
			if(deptcodenameGrounpList != ""){
				deptcodeGrounp = deptcodenameGrounpList[0][0];
				grounpName = deptcodenameGrounpList[0][1];
			}
			//liugang 2011-08-22 start
		}
	});
}
