var deptCode = App.util.getHtmlParameters('deptCode');

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
						div.innerHTML = '<div id="id" style="padding:25px,0,0,67px">'+createDateView()+'</div>';
						this.body.dom.appendChild(div);
					}
				}
			}
		]
	});
});

//创建日期显示视图
function createDateView(){
	var innerContent = '<div id="submenu">'
					+'<ul><li><a href="appointmentRegistration.html?outOne=1&outTwo=2">诊疗门诊</a></li>'
					+'<li><a href="appointmentRegistration.html?outOne=3&outTwo=4">专家门诊</a></li>'
					+'<li><a href="appointmentRegistration.html?outOne=5&outTwo=6">普通门诊</a></li>'
					+'</ul>'
					+'</div>';
					
	return innerContent;
}





//createDateView方法备份
//	Ext.Ajax.request({
//		url:App.App_Info.BasePath+'/PlanSignOrderAction.do',
//		params:{
//			method:'findBaseSignAPEntity'
//		},
//		sync:true,
//		success:function(response){
//			innerContent+= '<div id="submenu">';
//			var res = Ext.util.JSON.decode(response.responseText);
//			for(var i=0,size=res.length;i<size;i++){
//				
//				innerContent+='<li><a href="">'+res[i].outType+'门诊</a></li>';
//				if(i == res.length -1){
//					innerContent+='</ul>';
//				}
//			}
//		}
//	});


