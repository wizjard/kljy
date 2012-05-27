$.fn.extend({
	button:function(){
		$(this).css({
			'border':'solid 1px #666',
			'background':'url('+App.App_Info.BasePath+'/PUBLIC/images/btn-bg.gif) center repeat',
			'height':'24px',
			'line-height':'20px',
			'color':'#FFF',
			'font-weight':'bold',
			'padding':'0 3px',
			'margin':'2px 3px',
			'cursor':'pointer'
		}).mouseover(function(){
			$(this).css({
				'background':'url('+App.App_Info.BasePath+'/PUBLIC/images/btn-over-bg.gif) center repeat'
			});
		}).mouseout(function(){
			$(this).css({
				'background':'url('+App.App_Info.BasePath+'/PUBLIC/images/btn-bg.gif) center repeat'
			});
		});
	}
});
function tableHeaderNode(_name,_cs,_pname,_pno){
	var headerObj={
		reportName:'',
		admseqNo:1,
		patientName:'',
		patientNo:''
	};
	
	if(_name) headerObj.reportName=_name;
	if(_cs) headerObj.admseqNo=_cs;
	if(_pname) headerObj.patientName=_pname;
	if(_pno) headerObj.patientNo=_pno;
	
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
	img.src=App.App_Info.BasePath+'/PUBLIC/images/youanLogo.jpg';
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
	txt=document.createTextNode(' 姓 名: '+headerObj.patientName);
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
	txt=document.createTextNode('');//页码留空
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