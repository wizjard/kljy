var countNum;// 查询记录
var newCountNum ;//新增查房记录编号
//增加内容
function createHTML(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DoctorRoundsRecordAction.do',
		params:{
			method:'findDoctorRoundsRecordCountByPatientIdDoctor',
			patientId:patientId,
			//liugang 2011-08-06 start
			doctorId:top.USER.id
			//liugang 2011-08-06 end
		},
		scope:this,
		sync:true,
		success:function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				countNum = Ext.util.JSON.decode(res.data);
			}else{
				alert("登录超时，请重新登录！");
				window.top.location.href =App.App_Info.BasePath+"/module/biomedical/member/loginDoctor.jsp";
			}
		}
	});
	newCountNum=countNum.doctorListCount+1;
	var innerHTML='<div style="background-color:#fff7e6" class="ablock" id="doctor_'+(countNum.doctorListCount+1)+'" style="margin-left:50px"><form id="doctorForm_'+(countNum.doctorListCount+1)+'">'
	+'<input type="hidden" name="id" value="">'
	+'<table width="600" border="0" cellSpacing="0" borderColorDark="#ffffff" cellPadding="2">'
		+'<tr><td class="title" colspan=4 style="text-align:center;color:red">新增查房记录</td></tr>'
		+'<tr>'
			+'<td  width="200"><b>日期：<span name="roundsDate">'+currentTime()+'</span></b></td>'
			+'<td ><div align="center"><select name="roundsTitle"><option value=""></option>'
			+'<option value="首次网上查房记录">首次网上查房记录</option>'+'<option value="日常网上查房记录">日常网上查房记录</option>'+'<option value="阶段小结">阶段小结</option>'
			+'</select></div></td>'
			+'<td width="150"><div align="center"><b>第</b><font color="red"><b>'+(countNum.doctorListCount+1)+'</b></font><b>次查房记录</b></div></td>'
		+'</tr>'
		+'<tr><td colspan="3"><font size="2">内容</font></td></tr>'
		+'<tr><td colspan="3" style="padding-left:30px"><textarea name="roundsContent" style="width:560px;overflow-y:visible" onscroll="this.rows++;" rows="1"></textarea></td></tr>'
		+'</table><table width="600" border="1" cellSpacing="0" borderColorDark="#ffffff" cellPadding="2">'
		+'<tr>'
			+'<td style="text-algin:right;width:500px;padding-left:455px"><font size="2">签名：</font><input readonly name="doctorName" style="width:60px" value="'+top.USER.name+'"/></td>'
		+'</tr>'
		+'<tr><td colspan="3"><font color="red" size="2">本次查房意见仅供参考，如遇病情突然变化或其他特殊情况请您立即到医院就诊。</font></td></tr>'
		+'<tr>'
			+'<td style="text-align:center"><button class="sign" onclick="SaveData('+(countNum.doctorListCount+1)+')" id="showButton">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button class="sign" onclick="backMainPage()">关闭</button></td>'
		+'</tr>'
	+'</table>'
	+'</form></div>';
	return innerHTML;
} 

//编辑内容
function editCreateHtml(oldId,oldRecordNo){
	var innerHTML='<div style="background-color:#fff7e6;" id="doctor_'+oldRecordNo+'"><form>'
	+'<input type="hidden" name="id" value="">'
	+'<table width="600" border="1" cellSpacing="0" cellPadding="2" style="background-color:#EED8AE">'
		+'<tr><td class="title" colspan=4 style="text-align:center;color:red">查房记录</td></tr>'
		+'<tr>'
			+'<td width="200"><b>日期：<span name="roundsDate">'+roundsDate+'</span></b></td>'
			+'<td ><div align="center"><font size="3" ><select name="roundsTitle_'+oldId+'" disabled>'
			+'<option value="首次网上查房记录">首次网上查房记录</option>'+'<option value="日常网上查房记录">日常网上查房记录</option>'+'<option value="阶段小结">阶段小结</option>'
			+'</select></font></div></td>'
			+'<td width="150"><div align="center"><b>第<font color="red">'+oldRecordNo+'</font>次查房记录</b></div></td>'
		+'</tr>'
		+'<tr><td colspan="3" ><font size="2">内容</font></td></tr>'
		+'<tr><td colspan="3" style="padding-left:30px"><textarea name="roundsContent" style="width:560px;overflow-y:visible" onscroll="this.rows++;" rows="1" readonly>'+roundsContent+'</textarea></td></tr>'
		+'</table><table width="600" border="1" cellSpacing="0"  cellPadding="2" style="border-bottom:solid #9BCD9B 1px">'
		+'<tr>'
			+'<td style="text-algin:right;width:500px;padding-left:450px"><font size="2">签名：</font><input readonly name="doctorName" style="width:60px" value="'+top.USER.name+'" readOnly/><button class="sign" onclick="cancleUserName('+oldId+','+oldRecordNo+')">撤销</button></td>'
		+'</tr>'
		+'<tr><td colspan="3"><font color="red" size="2">本次查房意见仅供参考，如遇病情突然变化或其他特殊情况请您立即到医院就诊。</font></td></tr>'
	+'</table>'
	+'</form></div>';
	return innerHTML;
}

var roundsContent;//上次内容
var roundsDate;
function SaveData(formName){
	var value=FormUtil.getFormValues('#doctorForm_'+formName);
	if(value.roundsTitle == ""){
		alert("请选择查房记录类型内容");
		return;
	}
	if(value.roundsContent == ""){
		alert("请输入本次记录的内容");
		return;
	}
	roundsContent = value.roundsContent;
	roundsDate = value.roundsDate;
	value.patientId = patientId;
	value.doctorId = top.USER.id;
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DoctorRoundsRecordAction.do',
		params:{
			method:'saveOrUpdateDoctorRoundsRecord',
			data:JSON.stringify(value)
		},
		scope:this,
		success:function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				alert("保存成功");
				oldObject = Ext.util.JSON.decode(res.data);
				oldId = oldObject.id;//暂存原来的记录ID
				var div = document.getElementById("doctor_"+(countNum.doctorListCount+1));
				var oldRecordNo = (countNum.doctorListCount+1);
				div.innerHTML=editCreateHtml(oldId,oldRecordNo);
				var selectInnerHtml = document.getElementById("roundsTitle_"+oldId);
				selectInnerHtml.value = oldObject.roundsTitle;
//				if(currentDate == null){
//	      			currentDate = value.roundsDate;
//	      		}
				Ext.getCmp("north_panel").getRootNode().reload();
			}else{
				alert("保存失败");
			}
		}
	});
}

function cancleUserName(this_id,this_recordNo){
	var lastObject;
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DoctorRoundsRecordAction.do',
		params:{
			method:'findDoctorRoundsRecordById',
			id:this_id
		},
		scope:this,
		sync:true,
		success:function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				lastObject = Ext.util.JSON.decode(res.data);
			}
		}
	});
	var innerHTML='<div style="background-color:#fff7e6" id="doctor_'+this_recordNo+'" ><form>'
	+'<input type="hidden" name="id" value="">'
	+'<table width="600" border="0" cellSpacing="0" borderColorDark="#ffffff" cellPadding="2">'
		+'<tr><td colspan=4 class="title" style="color:red">修改查房记录</tr></td>'
		+'<tr>'
			+'<td width="200" style="font-size:12px;"><b>日期：<span name="roundsDate_'+this_id+'">'+lastObject.roundsDate+'</span></b></td>'
			+'<td ><div align="center"><font size="3" ><select name="docRoundsTitle_'+this_id+'">'
			+'<option value="首次网上查房记录">首次网上查房记录</option>'+'<option value="日常网上查房记录">日常网上查房记录</option>'+'<option value="阶段小结">阶段小结</option>'
			+'</select></font></div></td>'
			+'<td width="150" style="font-size:12px;"><div align="center"><b>第</b><font color="red"><b>'+this_recordNo+'</b></font><b>次查房记录</b></div></td>'
		+'</tr>'
		+'<tr><td colspan="3"><font size="2">内容</font></td></tr>'
		+'<tr><td colspan="3" style="padding-left:30px"><textarea name="roundsContent_'+this_id+'" style="width:555px;overflow-y:visible" onscroll="this.rows++;" rows="1">'+lastObject.roundsContent+'</textarea></td></tr>'
		+'</table><table width="600" border="1" cellSpacing="0" borderColorDark="#ffffff" cellPadding="2">'
		+'<tr>'
			+'<td style="text-algin:right;width:500px;padding-left:455px"><font size="2">签名：</font><input readonly name="doctorName" style="width:60px" value="'+top.USER.name+'"/></td>'
		+'</tr>'
		+'<tr><td colspan="3"><font color="red" size="2">本次查房意见仅供参考，如遇病情突然变化或其他特殊情况请您立即到医院就诊。</font></td></tr>'
		+'<tr>'
			+'<td style="text-align:center"><button class="sign" onclick="SaveUpdateData('+this_id+','+this_recordNo+')" id="showButton">保存</button></td>'
		+'</tr>'
	+'</table>'
	+'</form></div>';
	var div = document.getElementById("doctor_"+this_recordNo);
	div.innerHTML=innerHTML;
	var docRoundsTitle = document.getElementById("docRoundsTitle_"+this_id);
	docRoundsTitle.value = lastObject.roundsTitle;
}


function SaveUpdateData(cur_id,cur_recordId){
	if($('textarea[name="roundsContent_'+cur_id+'"]').val() == ""){
		alert("请输入本次记录的内容");
		return;
	}
	var value=FormUtil.getFormValues('form');
	value.id=cur_id;
	value.roundsDate = $('span[name="roundsDate_'+cur_id+'"]').html();
	value.roundsContent = $('textarea[name="roundsContent_'+cur_id+'"]').html();
	value.roundsTitle = $('select[name="docRoundsTitle_'+cur_id+'"]').val();
	value.patientId = patientId;
	value.doctorId = top.USER.id;
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/DoctorRoundsRecordAction.do',
		params:{
			method:'saveOrUpdateDoctorRoundsRecord',
			data:JSON.stringify(value)
		},
		scope:this,
		success:function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				alert("保存成功");
				oldObject = Ext.util.JSON.decode(res.data);
				oldId = oldObject.id;//暂存原来的记录ID
				roundsContent = oldObject.roundsContent;
				roundsDate = oldObject.roundsDate;
				var div = document.getElementById("doctor_"+cur_recordId);
				var oldRecordNo = cur_recordId;
				div.innerHTML=editCreateHtml(oldId,oldRecordNo);
				var selectInnerHtml = document.getElementById("roundsTitle_"+oldId);
				selectInnerHtml.value = oldObject.roundsTitle;
//				if(currentDate == null){
//	      			currentDate = null;
//	      		}
				Ext.getCmp("north_panel").getRootNode().reload();
			}else{
				alert("保存失败");
			}
		}
	});
}

function backMainPage(){
	var div = document.getElementById("id");
	div.innerHTML=createMainHtml();
}

