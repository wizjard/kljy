Ext.onReady(function(){	
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'center',
				layout:'fit',
				border:false,
				listeners:{
					render:function(){
						var div = document.createElement("div");
						div.innerHTML = '<br/><div style="padding:0,0,0,10px;width:820px;text-align:center"><b>预约科室一览表</b></div>'
						+'<div id="id" style="padding:15px,0,0,10px">'+createDateView()+'</div>'
						+'<div style="width:820px;text-align:right"><button onclick="backFirstPage()">返回</button></div>';
						this.body.dom.appendChild(div);
					}
				}
			}
		]
	});
});

//创建日期显示视图
function createDateView(){
	var innerContent = "";
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
		params:{
			method:'findAllSYS_HIS_DepartmentHISEntityMenList',
			name:'flagMen',
			nameValue:1
		},
		sync:true,
		success:function(response){
			innerContent+= '<div style="border:solid 1px #aaaaaa;width:820px" id="submenu"><table cellpadding=0 cellspacing=0 border=0 width=100%>';
			var res = Ext.util.JSON.decode(response.responseText);
			var row = 1;
			var classname;
			for(var i=0,size=res.length;i<size;i++){
				if(i%4 == 0 && i==0){
					innerContent+='<tr>';
				}else if(i%4 == 0){
					innerContent+='</tr><tr>';
					row = row+1;
				}
				if(row%2==1)
					classname = 'jclb_3';
				else
					classname = 'jclb_4';
				innerContent+='<td class="'+classname+'"><a href="appointmentRegistration.html?deptCode='+res[i].deptCode+'&deptName='+res[i].deptName+'">'+res[i].deptName+'</a></td>';
				if(i == res.length -1){
					innerContent+='</tr>';
				}
			}
		}
	});
	return innerContent+'</table></div>';
}

function backFirstPage(){
	window.location.href = "instroduceOut.html";
}




