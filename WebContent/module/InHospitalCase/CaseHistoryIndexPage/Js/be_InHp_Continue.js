// 初始化页面
var kid = null;
var pid = null;
$(function() {
	kid = App.util.getHtmlParameters('KID');
	pid = App.util.getHtmlParameters('PID');
	
	$('.tab99').find('input').css('border','none').css('border-bottom','solid 1px #000').attr('readonly',true);
	
	$('.tab6').find('input').css('border','none').css('border-bottom','solid 1px #000');
	
	// 复选框单击事件
	var tr = $(".tab3").find("tr");
	
	$.each(tr,function(i){
		$(this).find('input[type="checkbox"]').click(function(){
			var s = $(this).parent().parent().find('input[type="checkbox"]');
			$.each(s,function(i){
					$(this).attr("checked",false);
			});
			$(this).attr("checked",true);
		});
	});
	initPageButton();
	
	FormUtil.toDateField({el:$('input[name="tdate"]'), dateFormat:"yyyy-MM-dd"});
	FormUtil.toDateField({el:$('input[name="icuTurnInto"]'), dateFormat:"yyyy-MM-dd HH:mm"});
	FormUtil.toDateField({el:$('input[name="icuDropOut"]'), dateFormat:"yyyy-MM-dd HH:mm"});
	FormUtil.toDateField({el:$('input[name="badate"]'), dateFormat:"yyyy-MM-dd"});
	//调用初始化医生字典的方法
	doctorDict();
	//页面初始化赋值
	setValue();
});
// 按钮
function initPageButton() {
	new Ext.Panel({
		renderTo : 'button',
		height : 20,
		tbar : ['->', {
			xtype : 'tbseparator'
		}, {
			text : '保存',
			handler : SaveData
		}, {
			xtype : 'tbseparator'
		}, {
			text : '打印',
			handler : function() {
				location.href = "print/be_InHp_Continue_Print.html?kid=" + kid
						+ "&pid=" + pid;
			}
		}, {
			xtype : 'tbseparator'
		}]
	});
}
//初始化医生字典
function doctorDict(){
		var _input = $('input');
		_input.filter(function(_index){
			return $(this).attr('name').indexOf('_show')!=-1;
		}).each(function(){
			FormUtil.toDictSelect({
				url:App.App_Info.BasePath+'/PUBLIC/Modaldialog/MyDoctorSelect.html',
				hiddenEl:$('input[name="'+$(this).attr('name').replace('_show','')+'"]'),
				showEl:$('input[name="'+$(this).attr('name')+'"]')
			});
		});
	}
//初始化页面赋值
/*function setValue(){
	$.post(
		App.App_Info.BasePath+'/InHosp.do',
		{
			method:'ContinuePage_FindByCaseId',
			caseId:kid,
			patientId:pid
		},
		function(data){
			if(data.success){
				var data = JSON.parse(data.data);
				FormUtil.setFormValues('form',data.doctor);
				
				var tab99 = data.tab99;
				alert(tab99.age_months + "\t" + tab99.age_day + "\t" + tab99.province + "\t" + tab99.city + "\t" + tab99.area);
				var t9 = $(".tab99");
				FormUtil.setFormValues('form',tab99);
				var sysptom = data.sysptom;
				var ztr = $(".tab3").find("tr");
				
				$.each(sysptom,function(i){
					var leaveHospital = this.leaveHospital;
					ztr.filter(function(i){return i>1}).eq(i).find('input[name="sysptom"]').val(this.sysptom);
					ztr.filter(function(i){return i>1}).eq(i).find('input[name="id"]').val(this.id);
					ztr.filter(function(i){return i>1}).eq(i).find('input[name="icd"]').val(this.icd);
					ztr.filter(function(i){return i>1}).eq(i).find('input[name="leaveHospital"]').each(function(){
						if($(this).attr("value")==leaveHospital){
							$(this).attr("checked",true);
						}
					});
				
				});
				var ops = data.ops;
				var str = $(".tab4").find("tr");
				$.each(ops,function(i){
					str.filter(function(i){return i>1}).eq(i).find('input[name="operationNo"]').val(this.operationNo);
					str.filter(function(i){return i>1}).eq(i).find('input[name="tdate"]').val(this.tdate);
					str.filter(function(i){return i>1}).eq(i).find('input[name="surgeryName"]').val(this.surgeryName);
					str.filter(function(i){return i>1}).eq(i).find('input[name="surgeryDocName"]').val(this.surgeryDocName);
					str.filter(function(i){return i>1}).eq(i).find('input[name="surgeryAssistant1"]').val(this.surgeryAssistant1);
					str.filter(function(i){return i>1}).eq(i).find('input[name="surgeryAssistant2"]').val(this.surgeryAssistant2);
					str.filter(function(i){return i>1}).eq(i).find('input[name="anesthesiaMode"]').val(this.anesthesiaMode);
					str.filter(function(i){return i>1}).eq(i).find('input[name="cut"]').val(this.cut);
					str.filter(function(i){return i>1}).eq(i).find('input[name="analgesist"]').val(this.analgesist);
					str.filter(function(i){return i>1}).eq(i).find('input[name="id"]').val(this.id);
				});
				var ward = data.ward;
				var jtr = $(".tab5").find("tr");
				$.each(ward,function(i){
					jtr.filter(function(i){return i>0}).eq(i).find('input[name="icuName"]').val(this.icuName);
					jtr.filter(function(i){return i>0}).eq(i).find('input[name="icuTurnInto"]').val(this.icuTurnInto);
					jtr.filter(function(i){return i>0}).eq(i).find('input[name="icuDropOut"]').val(this.icuDropOut);
					jtr.filter(function(i){return i>0}).eq(i).find('input[name="id"]').val(this.id);
				});
			}
		},
		 'json'
	)
}*/

//初始化页面赋值
function setValue(){
	$.post(
		App.App_Info.BasePath+'/InHosp.do',
		{
			method:'ContinuePage_FindByCaseId',
			caseId:kid,
			patientId:pid
		},
		function(data){
			if(data.success){
				var data = JSON.parse(data.data);
				var tab6 = $('.tab6');
				//修改赋值方式    原为FormUtil中的公用赋值方法
				var doctor = data.doctor;
				tab6.find('*[name="id"]').val(doctor.id);
				tab6.find('*[name="kezhuren_show"]').val(doctor.kezhuren_show);
				tab6.find('*[name="directorDoctorId_show"]').val(doctor.directorDoctorId_show);
				tab6.find('*[name="treatDoctorId_show"]').val(doctor.treatDoctorId_show);
				tab6.find('*[name="inhspDoctorId_show"]').val(doctor.inhspDoctorId_show);
				tab6.find('*[name="jinxiu_show"]').val(doctor.jinxiu_show);
				tab6.find('*[name="yanjiusheng_show"]').val(doctor.yanjiusheng_show);
				tab6.find('*[name="shixi_show"]').val(doctor.shixi_show);
				tab6.find('*[name="bianma"]').val(doctor.bianma);
				tab6.find('*[name="bingan"]').val(doctor.bingan);
				tab6.find('*[name="qcyishi"]').val(doctor.qcyishi);
				tab6.find('*[name="qchushi"]').val(doctor.qchushi);
				tab6.find('*[name="badate"]').val(doctor.badate);
				
				//------修改基本信息复制方式   原为调用FormUtil中的公用赋值方法
				var tab99 = data.tab99;
				if(tab99){
					var t9 = $(".tab99");
					var t1 = $('.tab1');
					
					t1.find('*[name="inHspTimes"]').val(tab99.inHspTimes);
					t1.find('*[name="patientNo"]').val(tab99.patientNo);
					
					t9.find('*[name="patientName"]').val(tab99.patientName);
					t9.find('*[name="gender"]').val(tab99.gender);
					t9.find('*[name="age"]').val(tab99.age);
					t9.find('*[name="age_months"]').val(tab99.age_months);
					t9.find('*[name="age_day"]').val(tab99.age_day);
					t9.find('*[name="birthday"]').val(tab99.birthday);
					t9.find('*[name="province"]').val(tab99.province);
					t9.find('*[name="city"]').val(tab99.city);
					t9.find('*[name="area"]').val(tab99.area);
					
					t9.find('*[name="occupation"]').val(tab99.occupation);
					t9.find('*[name="people"]').val(tab99.people);
					t9.find('*[name="nationality"]').val(tab99.nationality);
					t9.find('*[name="marrageStatus"]').val(tab99.marrageStatus);
					t9.find('*[name="birthplace"]').val(tab99.birthplace);
					t9.find('*[name="idNo"]').val(tab99.idNo);
					t9.find('*[name="workUnitAddr"]').val(tab99.workUnitAddr);
					t9.find('*[name="workUnitTel"]').val(tab99.workUnitTel);
					t9.find('*[name="workUnitPostCode"]').val(tab99.workUnitPostCode);
					t9.find('*[name="homeAddr"]').val(tab99.homeAddr);
					t9.find('*[name="homePostCode"]').val(tab99.homePostCode);
					t9.find('*[name="contacterName"]').val(tab99.contacterName);
					t9.find('*[name="contacterRelationship"]').val(tab99.contacterRelationship);
					t9.find('*[name="contacterAdd"]').val(tab99.contacterAdd);
					t9.find('*[name="contacterTel"]').val(tab99.contacterTel);
					t9.find('*[name="inHspDate"]').val(tab99.inHspDate);
					t9.find('*[name="inWardCode"]').val(tab99.inWardCode);
					t9.find('*[name="inWard"]').val(tab99.inWard);
					t9.find('*[name="creditWard"]').val(tab99.creditWard);
					t9.find('*[name="outHspDate"]').val(tab99.outHspDate);
					//alert(tab99.inHspDate + "\t" + tab99.outHspDate);
					t9.find('*[name="outHspWard"]').val(tab99.outHspWard);
					t9.find('*[name="outWard"]').val(tab99.outWard);
					t9.find('*[name="hspDate2"]').val(tab99.hspDate2);
					t9.find('*[name="menzhen"]').val(tab99.menzhen);
					t9.find('*[name="inStatus"]').val(tab99.inStatus);
					t9.find('*[name="inIllsShow"]').val(tab99.inIllsShow);
					t9.find('*[name="queding_checkdate"]').val(tab99.queding_checkdate);
				}
				//----------------------------------------------------------------------------以上部分为本次修改
				var sysptom = data.sysptom;
				var ztr = $(".tab3").find("tr");
				$.each(sysptom,function(i){
					var leaveHospital = this.leaveHospital;
					ztr.filter(function(i){return i>1}).eq(i).find('input[name="sysptom"]').val(this.sysptom);
					ztr.filter(function(i){return i>1}).eq(i).find('input[name="id"]').val(this.id);
					ztr.filter(function(i){return i>1}).eq(i).find('input[name="icd"]').val(this.icd);
					ztr.filter(function(i){return i>1}).eq(i).find('input[name="leaveHospital"]').each(function(){
						if($(this).attr("value")==leaveHospital){
							$(this).attr("checked",true);
						}
					});
				
				});
				var ops = data.ops;
				var str = $(".tab4").find("tr");
				$.each(ops,function(i){
					str.filter(function(i){return i>1}).eq(i).find('input[name="operationNo"]').val(this.operationNo);
					str.filter(function(i){return i>1}).eq(i).find('input[name="tdate"]').val(this.tdate);
					str.filter(function(i){return i>1}).eq(i).find('input[name="surgeryName"]').val(this.surgeryName);
					str.filter(function(i){return i>1}).eq(i).find('input[name="surgeryDocName"]').val(this.surgeryDocName);
					str.filter(function(i){return i>1}).eq(i).find('input[name="surgeryAssistant1"]').val(this.surgeryAssistant1);
					str.filter(function(i){return i>1}).eq(i).find('input[name="surgeryAssistant2"]').val(this.surgeryAssistant2);
					str.filter(function(i){return i>1}).eq(i).find('input[name="anesthesiaMode"]').val(this.anesthesiaMode);
					str.filter(function(i){return i>1}).eq(i).find('input[name="cut"]').val(this.cut);
					str.filter(function(i){return i>1}).eq(i).find('input[name="analgesist"]').val(this.analgesist);
					str.filter(function(i){return i>1}).eq(i).find('input[name="id"]').val(this.id);
				});
				var ward = data.ward;
				var jtr = $(".tab5").find("tr");
				$.each(ward,function(i){
					jtr.filter(function(i){return i>0}).eq(i).find('input[name="icuName"]').val(this.icuName);
					jtr.filter(function(i){return i>0}).eq(i).find('input[name="icuTurnInto"]').val(this.icuTurnInto);
					jtr.filter(function(i){return i>0}).eq(i).find('input[name="icuDropOut"]').val(this.icuDropOut);
					jtr.filter(function(i){return i>0}).eq(i).find('input[name="id"]').val(this.id);
				});
			}
		},
		 'json'
	)
}


// 打印
function print() {
	var id = $('.tab6').find('*[name="id"]').val();
	if(id!="-1"){
		window.open(App.App_Info.BasePath+ '/module/InHospitalCase/CaseHistoryIndexPage/be_InHp_Continue_Print.html');
	}else{
		alert('请先保存后再打印!');
	}
}

// 保存
function SaveData() {
	var json={};
	var data = [];
	//取得出院情况部分的值
	var ztr = $(".tab3").find("tr");
	ztr.filter(function(i){return i>1}).each(function(){
		var s ='';
		$.each($(this).children(),function(){
			 if($(this).find('*[name="leaveHospital"]').attr("checked")){
				s = $(this).find('*[name="leaveHospital"]').val();
			 }
		});
		data.push({
			id:$(this).find('*[name="id"]').val(),
			caseId:kid,
			sysptom:$(this).find('*[name="sysptom"]').val(),
			leaveHospital:s,
			icd:$(this).find('input[name="icd"]').val()
		});
	});
	json.sysptom=data;
	data=[];
	//取得手术部分的值
	var str = $(".tab4").find("tr");
	str.filter(function(i){return i>1}).each(function(){
		data.push({
			id:$(this).find('*[name="id"]').val(),
			caseId:kid,
			operationNo:$(this).find('*[name="operationNo"]').val(),
			tdate:$(this).find('*[name="tdate"]').val(),
			surgeryName:$(this).find('*[name="surgeryName"]').val(),
			surgeryDocName:$(this).find('*[name="surgeryDocName"]').val(),
			surgeryAssistant1:$(this).find('*[name="surgeryAssistant1"]').val(),
			surgeryAssistant2:$(this).find('*[name="surgeryAssistant2"]').val(),
			anesthesiaMode:$(this).find('*[name="anesthesiaMode"]').val(),
			cut:$(this).find('*[name="cut"]').val(),
			analgesist:$(this).find('*[name="analgesist"]').val()
		});
	});
	json.ops=data;
	data=[];
	//取出重症监护室部分的值
	var jtr = $(".tab5").find("tr");
	jtr.filter(function(i){return i>0}).each(function(){
		data.push({
			id:$(this).find('*[name="id"]').val(),
			caseId:kid,
			icuName:$(this).find('*[name="icuName"]').val(),
			icuTurnInto:$(this).find('*[name="icuTurnInto"]').val(),
			icuDropOut:$(this).find('*[name="icuDropOut"]').val()
		});
	});
	json.ward=data;
	data=[];
	//取出医生签字部分的值
	var ystr = $(".tab6");
	//$('input[name="state"]').val(1);
	//alert(ystr.find('input[name="id"]').val());
		data.push({
			id:ystr.find('*[name="id"]').val(),
			caseId:kid,
			kezhuren_show:ystr.find('*[name="kezhuren_show"]').val(),
			directorDoctorId_show:ystr.find('*[name="directorDoctorId_show"]').val(),
			treatDoctorId_show:ystr.find('*[name="treatDoctorId_show"]').val(),
			inhspDoctorId_show:ystr.find('*[name="inhspDoctorId_show"]').val(),
			jinxiu_show:ystr.find('*[name="jinxiu_show"]').val(),
			yanjiusheng_show:ystr.find('*[name="yanjiusheng_show"]').val(),
			shixi_show:ystr.find('*[name="shixi_show"]').val(),
			bianma:ystr.find('*[name="bianma"]').val(),
			bingan:ystr.find('*[name="bingan"]').val(),
			qcyishi:ystr.find('*[name="qcyishi"]').val(),
			qchushi:ystr.find('*[name="qchushi"]').val(),
			badate:ystr.find('*[name="badate"]').val(),
			state:'1'
		});
	json.doctor=data
	var data=JSON.stringify(json);
	$.post(
		App.App_Info.BasePath+'/InHosp.do',
		{
			method:'ContinuePage_SaveOrUpdate',
			state:$('input[name="state"]').val(),
			data:data
		},function(data){
			alert('保存成功');
			location.reload();
		},
		'json'
	)
	
}
