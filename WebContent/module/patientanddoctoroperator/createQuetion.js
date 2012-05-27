var countNum;
function createHTML(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PatientConsultingAction.do',
		params:{
			method:'findNumOne'
		},
		scope:this,
		sync:true,
		success:function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				countNum = Ext.util.JSON.decode(res.data);
			}else{
				alert("登录超时，请重新登录！");
				window.top.location.href =App.App_Info.BasePath+"/module/biomedical/member/loginMember.jsp";
			}
		}
	});
	var innerHTML='<form>'
	+'<input type="hidden" name="id" value="">'
	+'<input type="hidden" name="consultingCount" value="">'
	+'<input type="hidden" name="consultingDate" value="">'
	+'<div class="ablock" style="margin-left:50px"><table width="600" border=0 cellSpacing=0 borderColorDark=#ffffff cellPadding=2>'
		//+'<tr><td class="title" width="600" colspan="3"><div align="center"><font size="3" >咨询我的医生</font></div></td></tr>'
		+'<tr>'
			+'<td class="title" style="font-size:12px" width="200"><b>咨询日期：</b><span name="currentTime"><B>'+currentTime()+'</B></span></td>'
			+'<td class="title" width="200" align="center"><font size="3" >咨询我的医生</font></td>'
			+'<td class="title" style="font-size:12px" width="200"><div align="center"><b>第<font color="red" name="couZ">'+countNum.countNum+'</font>次咨询</b></div></td>'
		+'</tr><tr><td class="titletd" colspan="4"><font size="2">一般情况<font></td></tr></table><table width="600" border=0 cellSpacing=0 borderColorDark=#ffffff cellPadding=2>'
		+'<tr>'
			+'<td class="titletd" style="padding-left:50px" width="200"><div style="font-size:12px;">精神状态：'
			+'<select name="healthStatus"><option value="好">好</option><option value="可">可</option>'
			+'<option value="差">差</option><option value="不振">不振</option><option value="萎靡">萎靡</option><option value="无变化">无变化</option></select></div></td>'
			+'<td class="titletd" width="200"><div style="font-size:12px;align:left">食量：'
			+'<select name="diet"><option value="食量无变化">食量无变化</option><option value="食量增加">食量增加</option>'
			+'<option value="食量减少">食量减少</option><option value="食量较好">食量较好</option><option value="禁食水">禁食水</option></select></div></td>'
			+'<td class="titletd" width="200"><div style="font-size:12px;align:left">睡眠：'
			+'<select name="sleep"><option value="无改变">无改变</option><option value="好">好</option>'
			+'<option value="欠佳">欠佳</option><option value="差">差</option></select></div></td>'
		+'</tr>'
		+'<tr>'
			+'<td class="titletd" style="padding-left:50px" width="200" ><div style="font-size:12px;">小　　便：'
			+'<select name="piss" style="width:66px"><option value="正常">正常</option><option value="异常">异常</option>'
			+'</select></div></td>'
			+'<td class="titletd"width="200"><div style="font-size:12px;">大便：'
			+'<select name="defecate" style="width:92px"><option value="正常">正常</option><option value="异常">异常</option></select></div></td>'
			+'<td class="titletd" width="200"><div style="font-size:12px;">体重：'
			+'<select name="weight"><option value="无改变">无改变</option><option value="增加">增加</option>'
			+'<option value="减轻">减轻</option></select></div></td>'
		+'</tr>'
		+'</tr><tr><td class="titletd" colspan="3" style="background-color: #f5f5f5;"><font size="2">异常表现</font></td></tr>'
		+'</tr><tr><td colspan="3" class="titletd" style="padding-left:55px"><textarea name="spiritStatus" style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1"></textarea></td></tr>'
		+'</tr><tr><td colspan="3" class="titletd" style="background-color: #f5f5f5;"><font size="2">用药情况</font></td></tr>'
		+'</tr><tr><td colspan="3" class="titletd" style="padding-left:55px"><textarea name="treatmentStatus" style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1"></textarea></td></tr>'
		+'</tr><tr><td colspan="3" class="titletd" style="background-color: #f5f5f5;"><font size="2">治疗效果</font></td></tr>'
		+'</tr><tr><td colspan="3" class="titletd" style="padding-left:55px"><textarea name="improveStatus" style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1"></textarea></td></tr>'
		+'</tr><tr><td colspan="3" class="titletd" style="background-color: #f5f5f5;"><font size="2">咨询问题</font></td></tr>'
		+'</tr><tr><td colspan="3" class="titletd" style="padding-left:55px"><textarea name="mainProblem" style="width:535px;overflow-y:visible" onscroll="this.rows++;" rows="1"></textarea></td></tr>'
		+'</tr><tr><td colspan="3" class="titletd" style="background-color: #f5f5f5;"><font size="2">上传资料</font></td></tr>'
		+'</tr><tr><td colspan="3" class="contenttd" style="padding-left:44px"><table cellpadding="0" cellspacing="0">'
							+'<tr>'
								+'<td>'
									+'<iframe scrolling="auto" id="uploadFrame1" height="150" width="500"'
										+'frameborder="0"'
										+'src="../../PUBLIC/fileUpload/uploadfile.jsp"></iframe>'
								+'</td>'
							+'</tr>'
						+'</table></td></tr>'
//		+'</tr><tr><td colspan="3" class="titletd" style="background-color: #f5f5f5;"><font size="2">上传图片<font></td></tr>'
//		+'</tr><tr><td colspan="3" class="contenttd" style="padding-left:44px"><table cellpadding="0" cellspacing="0">'
//							+'<tr>'
//								+'<td>'
//									+'<iframe scrolling="no" id="uploadFrame2" height="150" width="400"'
//										+'frameborder="0"'
//										+'src="../../PUBLIC/fileUpload/uploadPicture.jsp"></iframe>'
//								+'</td>'
//							+'</tr>'
//						+'</table></td></tr>'
		+'</table><table width="600" border=0 cellSpacing=0 borderColorDark=#ffffff cellPadding=2>'
	    +'<tr>'
			+'<td class="titletd" style="width:65px"><font size="2">责任科室：<font></td>'
			+'<td class="titletd" style="text-align:left"><input type="text" style="width:260px" name="deptCode" value="'+countNum.dapartmentAndgrounp+'" readOnly/></td>'
		+'</tr>'
		+'</table><table width="600" border=0 cellSpacing=0 borderColorDark=#ffffff cellPadding=2>'
		+'<tr>'
			+'<td class="titletd" style="background-color:#f5f5f5;text-algin:right;width:600px;padding-left:450px"><font size="2">签名：<font><input readonly name="signature" style="width:70px" value="'+countNum.memberName+'"/></td>'
		+'</tr>'
		+'<tr>'
			+'<td class="titletd" style="background-color:#f5f5f5;text-align:center"><button  onclick="SaveData()">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button  onclick="backMainPage()">关闭</button></td>'
		+'</tr>'
	+'</table></div>'
	+'</form>';
	return innerHTML;
}


function SaveData(){
	if($('textarea[name="mainProblem"]').val() == ""){
		alert("请输入所要咨询的问题");
		return;
	}
	var value=FormUtil.getFormValues('form');
	value.consultingDate = $('span[name="currentTime"]').html().replace("<B>","").replace("</B>","");
	value.consultingCount = countNum.countNum;
	
	var iframeFile = document.getElementById("uploadFrame1");
//	var iframeImage = document.getElementById("uploadFrame2");
	
//	var iframe = document.getElementById("uploadFrame2");
//	var picDiv = iframe.contentWindow.document.getElementById("picDiv");
	
//	if(picDiv!=null){
//	   var picturePath = iframe.contentWindow.document.getElementById("picDiv").src;
//	   var start = picturePath.indexOf("image");
//	   var picPath = picturePath.substring(start,picturePath.length);
//	  	value.uploadImage="<img src='../../"+picPath+"' style='width:400px;height:150px'/>";  //图片的路径
//	}
	if(iframeFile.contentWindow.document.getElementById("fileUploadStr")!=null){
	  	value.uploadFile = iframeFile.contentWindow.document.getElementById("fileUploadStr").value; //附件的路径
	}
	
	value.healthStatus = $('select[name="healthStatus"]').val();
	value.diet = $('select[name="diet"]').val();
	value.sleep = $('select[name="sleep"]').val();
	value.piss = $('select[name="piss"]').val();
	value.defecate = $('select[name="defecate"]').val();
	value.weight = $('select[name="weight"]').val();
	Ext.Ajax.request({
		url:baseUrl,
		params:{
			method:'saveOrUpdate',
			data:JSON.stringify(value)
		},
		scope:this,
		success:function(_response){
			var res=Ext.decode(_response.responseText);
			if(res.success){
				alert("保存成功");
				window.location.href=App.App_Info.BasePath+'/module/patientanddoctoroperator/patientConsultingList.html';
			}else{
				alert("登录超时，请重新登录！");
				window.top.location.href =App.App_Info.BasePath+"/module/biomedical/member/login.jsp";
			}
		}
	});
}

//<img style="left:0;top:0;width:50px;height:67px;position:absolute;display:block;" src="../../PUBLIC/images/youanLogo.jpg"/><div style="left:50px;top:28px;width:80%;font-size:18px;font-weight:bold;position:absolute;">首都医科大学附属北京佑安医院</div>'
//	+'<div style="left:50px;top:50px;width:80%;font-size:11px;font-weight:bold;position:absolute;">Beijing YouAn Hospital,Capital Medical University</div>'
//	+'<div style="padding-top:90px"></div>