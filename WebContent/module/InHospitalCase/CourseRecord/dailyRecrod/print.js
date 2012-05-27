/**打印功能实现代码**/
var LODOP=document.getElementById("LODOP");//符合DTD规范
/**
 * 续打功能代码
 */
function PrintA(){
	var continuedNum=Ext.get('continuedNum').dom.value.trim();
	if(continuedNum.length==0){
		alert('未配置续打条目。');
		return;
	}
	getPrintData({continued:true,caseId:KID,continuedNum:continuedNum},function(data){
		data=ProcessData(data);
		CreateCommonPart(data.name,data.no,data.times,data.html,true);
		LODOP.PREVIEW();
	});
}
/**
 * 全部打印功能代码
 */
function PrintB(){
	getPrintData({continued:false,caseId:KID},function(data){
		data=ProcessData(data);
		CreateCommonPart(data.name,data.no,data.times,data.html,false);
		LODOP.PREVIEW();
	});
}
function ProcessData(data){
	var style=data.html.substr(0,data.html.indexOf('/style')+7);
	var rowOffset=Ext.get('rowOffset').dom.value.trim();
	var charOffset=Ext.get('charOffset').dom.value.trim();
	Ext.state.Manager.set('CourseRecord_rowOffset_'+KID,rowOffset);
	Ext.state.Manager.set('CourseRecord_charOffset_'+KID,charOffset);
	if((!isNaN(rowOffset)&&rowOffset!=0)||(!isNaN(charOffset)&&charOffset!=0)){
		var div=Ext.get('temp_div');
		if(!div){
			div=Ext.DomHelper.append(document.body,{
				tag:'display:none',
				id:'temp_div'
			});
		}else{
			div=div.dom;
		}
		div.innerHTML=data.html;
		var breaks=Ext.get(div).query('div');
		var table=null;
		if(breaks.length>0){
			table=Ext.get(breaks[breaks.length-1]).next('table',true);
		}else{
			table=Ext.get(div).query('table')[0];
		}
		var temp=0;
		if(!isNaN(rowOffset)&&rowOffset!=0){
			temp=parseInt(rowOffset)*39;
		}
		if(!isNaN(charOffset)&&charOffset!=0){
			temp+=parseInt(charOffset);
		}
		var td=table.getElementsByTagName('tr')[1].getElementsByTagName('td')[0];
		if(temp>0){
			for(var i=0;i<temp;i++){
				td.innerHTML='　'+td.innerHTML;
			}
		}
		if(temp<0){
			var str=td.innerHTML;
			td.innerHTML=str.substr(-temp,str.length);
		}
		data.html=style+div.innerHTML;
	}
	return data;
}
/**
 * 从服务器获取打印数据
 * @param {Object} cfg{continued:false,caseId:0,continuedNum:0} 提供的参数
 * @param {Object} callback 回调函数
 */
function getPrintData(cfg,callback){
	if(!cfg){
		cfg={};
	}
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
		params:Ext.apply(cfg,{
			method:'DailyCourseRec_NewPrint'
		}),
		success:function(response){
			var res=Ext.decode(response.responseText);
			if(res.success){
				callback(Ext.decode(res.data));
			}else{
				alert('获取打印数据失败。');
			}
		}
	});
}
/**
 * 创建打印表头
 */
function CreateCommonPart(name,no,times,html,isContinue){
	LODOP.PRINT_INIT("西医连续页病程记录_打印任务V1");
	LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
	
	if(isContinue){
		LODOP.ADD_PRINT_LINE('37mm','15mm','37mm','195mm',0,1);
		LODOP.SET_PRINT_STYLEA(1,"FontColor",'#FFF');
		LODOP.SET_PRINT_STYLEA(1,"ItemType",1);
		
		LODOP.ADD_PRINT_LINE('280mm','15mm','280mm','195mm',0,1);
		LODOP.SET_PRINT_STYLEA(2,"FontColor",'#FFF');
		LODOP.SET_PRINT_STYLEA(2,"ItemType",1);
		
		LODOP.ADD_PRINT_LINE('9mm','15mm','285mm','15mm',0,2);
		LODOP.SET_PRINT_STYLEA(3,"FontColor",'#FFF');
		LODOP.SET_PRINT_STYLEA(3,"ItemType",1);
		
		LODOP.ADD_PRINT_HTM('40mm','16mm','180mm','900px',html);
		LODOP.SET_PRINT_STYLEA(4,"ItemType",0);
		LODOP.SET_PRINT_STYLEA(4,"HOrient",3);
		LODOP.SET_PRINT_STYLEA(4,"VOrient",0);
	}else{
		var imgHtml='<img width="50" height="67" src="'+App.App_Info.BasePath+'/PUBLIC/images/youanLogo.jpg"/>';
		LODOP.ADD_PRINT_IMAGE('10mm','15mm','14mm','18mm',imgHtml);
		LODOP.SET_PRINT_STYLEA(1,"ItemType",1);
		LODOP.ADD_PRINT_TEXT('17mm','28mm','80mm','6mm',"首都医科大学附属北京佑安医院");
		LODOP.SET_PRINT_STYLEA(2,"FontSize",15);
		LODOP.SET_PRINT_STYLEA(2,"Bold",1);
		LODOP.SET_PRINT_STYLEA(2,"ItemType",1);
		LODOP.ADD_PRINT_TEXT('23mm','28mm','80mm','3mm',"BeiJing YouAn Hospital,Capital Medical University");
		LODOP.SET_PRINT_STYLEA(3,"FontSize",7);
		LODOP.SET_PRINT_STYLEA(3,"Bold",1);
		LODOP.SET_PRINT_STYLEA(3,"ItemType",1);
		LODOP.ADD_PRINT_TEXT('26mm','77mm','70mm','9mm',"病 程 记 录");
		LODOP.SET_PRINT_STYLEA(4,"FontSize",19);
		LODOP.SET_PRINT_STYLEA(4,"Bold",1);
		LODOP.SET_PRINT_STYLEA(4,"ItemType",1);
		
		LODOP.ADD_PRINT_TEXT('33mm','30mm','20mm','4mm',"第 "+times+" 次住院");
		LODOP.SET_PRINT_STYLEA(5,"ItemType",1);
		LODOP.ADD_PRINT_TEXT('33mm','162mm','40mm','4mm',"病案号："+no);
		LODOP.SET_PRINT_STYLEA(6,"ItemType",1);
		LODOP.ADD_PRINT_TEXT('29mm','162mm','40mm','4mm',"姓  名：" +name);
		LODOP.SET_PRINT_STYLEA(7,"ItemType",1);
		LODOP.ADD_PRINT_TEXT('33mm','90mm','30mm','4mm',"第 # 页 ");
		LODOP.SET_PRINT_STYLEA(8,"ItemType",2);
		
		LODOP.ADD_PRINT_LINE('37mm','15mm','37mm','195mm',0,1);
		LODOP.SET_PRINT_STYLEA(9,"ItemType",1);
		LODOP.ADD_PRINT_LINE('280mm','15mm','280mm','195mm',0,1);
		LODOP.SET_PRINT_STYLEA(10,"ItemType",1);
		LODOP.ADD_PRINT_LINE('9mm','15mm','285mm','15mm',0,2);
		LODOP.SET_PRINT_STYLEA(11,"ItemType",1);
		LODOP.ADD_PRINT_HTM('40mm','16mm','180mm','900px',html);
		LODOP.SET_PRINT_STYLEA(12,"ItemType",0);
		LODOP.SET_PRINT_STYLEA(12,"HOrient",3);
		LODOP.SET_PRINT_STYLEA(12,"VOrient",0);
	}
}
