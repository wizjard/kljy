//接收从服务器返回的值
var responseData={};
var pageArr=[];
//获取InHspRex.html中传入的参数 CommoutUtil在JsUtil.js中定义
var _id=CommonUtil.getHtmlParameters('id');
Ext.onReady(function(){
	getDataFromServer(_id);
	//setPageHeader();
	setValue();
	//push向数组的未尾追加一个或多个元素并返回新的长度
	pageArr.push(document.getElementById('page1Div').innerHTML);
	pageArr.push(document.getElementById('page2Div').innerHTML);
	pageArr.push(document.getElementById('page3Div').innerHTML);
	pageArr.push(document.getElementById('page4Div').innerHTML);
	pageArr.push(document.getElementById('page5Div').innerHTML);
	pageArr.push(document.getElementById('page6Div').innerHTML);
	//JsDispageTool函数的作用？
	var _strs=JsDispageTool(document.getElementById('现病史'),720);
	document.getElementById('现病史').innerHTML=_strs[0];
	document.getElementById('现病史2').innerHTML=_strs[1];
});
function setPageOrder(isOrder){
	if(isOrder){
		document.getElementById('page1Div').innerHTML=pageArr[0];
		document.getElementById('page2Div').innerHTML=pageArr[1];
		document.getElementById('page3Div').innerHTML=pageArr[2];
		document.getElementById('page4Div').innerHTML=pageArr[3];
		document.getElementById('page5Div').innerHTML=pageArr[4];
		document.getElementById('page6Div').innerHTML=pageArr[5];
	}else{
		document.getElementById('page1Div').innerHTML=pageArr[0];
		document.getElementById('page2Div').innerHTML=pageArr[2];
		document.getElementById('page3Div').innerHTML=pageArr[4];
		document.getElementById('page4Div').innerHTML=pageArr[1];
		document.getElementById('page5Div').innerHTML=pageArr[3];
		document.getElementById('page6Div').innerHTML=pageArr[5];
	}
}
function initWordPrint(){
	Ext.Ajax.request({
		url:App.basePath+'/EMR/LiverCaseAction.do',
		params:{method:'print_initWordPrint',id:_id},
		sync:true,
		success:function(_response,_options){
			var m=Ext.util.JSON.decode(_response.responseText);
			if(m.responseStatus==0){
				window.open(App.basePath+'/EMR/LiverCaseAction.do?method=print_downloadTemplete&id='+_id+'&docName=InHspRecTemplete');
			}else{
				Ext.Msg.alert('提示','导出WORD初始化失败。',function(){});
			}
		}
	});
}
function setPageHeader(){
	for(var i=1;i<=6;i++){
		document.getElementById('page'+i+'Header').innerHTML=tableHeaderNode({
			reportName:'入 院 记 录 ',
			patientName:responseData['姓名'],
			admseqNo:responseData['住院次数'],
			currentPageNo:i,
			totalPageNo:6,
			patientNo:responseData['病案号']
		}).innerHTML;
	}
}
function setValue(){
	for(var key in responseData){
		var el=document.getElementById(key);
		if (typeof el == 'undefined' || el == null) 
			continue;
		else {
			var _value=responseData[key];
			//如果值为空
			if(_value==null||_value=='null'||_value.indexOf('null')>=0){
				responseData[key]='';
			}
			if(key.indexOf('初诊')>=0||key.indexOf('确诊')>=0||key.indexOf('订诊')>=0){
				var _temp="";
				for(var i=0,len=responseData[key].length;i<len;i++){
					//charAt(index) 获取指定位置上的字符串
					var _char=responseData[key].charAt(i);
					if(_char==' '){
						_temp+='&nbsp;';
					}else{
						_temp+=_char;
					}
				}
				responseData[key]=_temp;
			}
			el.innerHTML = responseData[key];
			if(key=='现病史'){
				var _div=el.getElementsByTagName('div');
				if(_div.length>0){
					_div=_div[0];
					_div.style.cssText='';
				}
			}
		}
	}
}
function getDataFromServer(_id){
	Ext.Ajax.request({
		url:App.basePath+'/EMR/ChimeCaseAction.do',
		params:{method:'print_getData',id:_id},
		sync:true,
		success:function(_response,_options){
			var m=Ext.util.JSON.decode(_response.responseText);
			if(m.responseStatus==0){
				responseData=Ext.util.JSON.decode(m.responseData);
			}else{
				Ext.Msg.alert('提示','获取打印数据失败。',function(){});
			}
		}
	});
}

function tableHeaderNode(headerObj){
	var div=document.createElement('div');//表头容器
	var table=document.createElement('table');
	table.setAttribute('width','100%');
	table.setAttribute('border','0');
	table.setAttribute('cellpadding','0');
	table.setAttribute('cellspacing','0');
	var tr=document.createElement('tr');
	var td=document.createElement('td');//LOGO
	td.setAttribute('width','55');
	var img=document.createElement('img');//LOGO
	img.style.cssText='width:55px;height:70px;';
	img.src='../../../images/youanLogo.jpg';
	td.appendChild(img);
	tr.appendChild(td);
	td=document.createElement('td');
	td.setAttribute('width','*');
	td.setAttribute('valign','bottom');
	var p=document.createElement('p');//医院名称-中文
	p.className='headTitleCN';
	var txt=document.createTextNode('首都医科大学附属北京佑安医院');
	p.appendChild(txt);
	td.appendChild(p);
	p=document.createElement('p');//医院名称-英文
	p.className='headTitleEN';
	txt=document.createTextNode('BeiJing YouAn Hospital,Capital Medical University');
	p.appendChild(txt);
	td.appendChild(p);
	tr.appendChild(td);
	td=document.createElement('td');//空白
	td.setAttribute('width','55');
	txt=document.createTextNode(' ');
	td.appendChild(txt);
	tr.appendChild(td);
	table.appendChild(tr);
	div.appendChild(table);
	table=document.createElement('table');
	table.setAttribute('width','100%');
	table.setAttribute('border','0');
	table.setAttribute('cellpadding','0');
	table.setAttribute('cellspacing','0');
	tr=document.createElement('tr');
	td=document.createElement('td');//空白
	td.setAttribute('width','55');
	txt=document.createTextNode(' ');
	td.appendChild(txt);
	tr.appendChild(td);
	td=document.createElement('td');//空白
	td.setAttribute('width','45');
	txt=document.createTextNode(' ');
	td.appendChild(txt);
	tr.appendChild(td);
	td=document.createElement('td');//报表类型
	td.setAttribute('width','*');
	td.setAttribute('align','center');
	td.setAttribute('valign','bottom');
	td.className='headTitle';
	txt=document.createTextNode(headerObj.reportName);
	td.appendChild(txt);
	tr.appendChild(td);
	td=document.createElement('td');//姓名
	td.setAttribute('width','150');
	td.setAttribute('valign','bottom');
	td.className='smallFont';
	td.style.cssText='padding-left:50px;';
	txt=document.createTextNode('姓 名: '+headerObj.patientName);
	td.appendChild(txt);
	tr.appendChild(td);
	table.appendChild(tr);
	tr=document.createElement('tr');
	td=document.createElement('td');//空白
	txt=document.createTextNode(' ');
	td.appendChild(txt);
	tr.appendChild(td);
	td=document.createElement('td');//住院次数
	td.className='smallFont';
	txt=document.createTextNode('第 '+headerObj.admseqNo+' 次住院');
	td.appendChild(txt);
	tr.appendChild(td);
	td=document.createElement('td');//页码
	td.setAttribute('align','center');
	td.className='smallFont';
	txt=document.createTextNode('第 '+headerObj.currentPageNo+' 页，共 '+headerObj.totalPageNo+' 页');
	td.appendChild(txt);
	tr.appendChild(td);
	td=document.createElement('td');//病案号
	td.style.cssText='padding-left:50px;';
	td.className='smallFont';
	txt=document.createTextNode('病案号: '+headerObj.patientNo);
	td.appendChild(txt);
	tr.appendChild(td);
	table.appendChild(tr);
	div.appendChild(table);
	return div;
}