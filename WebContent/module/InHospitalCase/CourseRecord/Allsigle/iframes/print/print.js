var kid=App.util.getHtmlParameters('kid');
var cid=App.util.getHtmlParameters('cid');
var LODOP=null;
function CheckLodop(_basePath){
   var oldVersion=LODOP.Version;
       newVerion="5.0.0.3";
   if (oldVersion==null){
	document.write("<h3><font color='#FF00FF'>打印控件未安装!点击这里<a href='"+_basePath+"install_lodop.exe'>执行安装</a>,安装后请刷新页面。</font></h3>");
	if (navigator.appName=="Netscape")
	document.write("<h3><font color='#FF00FF'>（Firefox浏览器用户需先点击这里<a href='"+_basePath+"npActiveXFirefox4x.xpi'>安装运行环境</a>）</font></h3>");
   } else if (oldVersion<newVerion)
	document.write("<h3><font color='#FF00FF'>打印控件需要升级!点击这里<a href='"+_basePath+"install_lodop.exe'>执行升级</a>,升级后请重新进入。</font></h3>");
	
}
function PREVIEW(_title){
	LODOP.PRINT_INIT(_title+"打印任务");
	LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
	LODOP.ADD_PRINT_IMAGE(20,80,70,86,'<img width="70" height="86" src="'+App.App_Info.BasePath+'/PUBLIC/images/youanLogo.jpg"/>');
	LODOP.SET_PRINT_STYLEA(1,"ItemType",1);
	LODOP.ADD_PRINT_TEXT(36,150,597,40,"首都医科大学附属北京佑安医院");
	LODOP.SET_PRINT_STYLEA(2,"FontSize",17);
	LODOP.SET_PRINT_STYLEA(2,"Bold",1);
	LODOP.SET_PRINT_STYLEA(2,"ItemType",1);
	LODOP.ADD_PRINT_TEXT(63,152,594,25,"BeiJing YouAn Hospital,Capital Medical University");
	LODOP.SET_PRINT_STYLEA(3,"Bold",1);
	LODOP.SET_PRINT_STYLEA(3,"ItemType",1);
	LODOP.ADD_PRINT_TEXT(85,315,340,40,_title);
	LODOP.SET_PRINT_STYLEA(4,"FontSize",19);
	LODOP.SET_PRINT_STYLEA(4,"Bold",1);
	LODOP.SET_PRINT_STYLEA(4,"ItemType",1);
	LODOP.ADD_PRINT_TEXT(115,150,100,20,"第"+document.getElementById('inHspTimes').innerText+"次住院");
	LODOP.SET_PRINT_STYLEA(5,"ItemType",1);
	LODOP.ADD_PRINT_TEXT(115,620,150,20,"病案号："+document.getElementById('patientNo').innerText);
	LODOP.SET_PRINT_STYLEA(6,"ItemType",1);
	LODOP.ADD_PRINT_TEXT(100,620,150,20,"姓  名："+document.getElementById('patientName').innerText);
	LODOP.SET_PRINT_STYLEA(7,"ItemType",1);
	LODOP.ADD_PRINT_LINE(129,80,129,750,0,1);
	LODOP.SET_PRINT_STYLEA(8,"ItemType",1);
	LODOP.ADD_PRINT_TEXT(115,343,110,20,"第 # 页 共 & 页");
	LODOP.SET_PRINT_STYLEA(9,"ItemType",2);
	LODOP.ADD_PRINT_LINE(1055,80,1055,750,0,1);
	LODOP.SET_PRINT_STYLEA(10,"ItemType",1);
	LODOP.ADD_PRINT_HTM(135,80,650,900,document.getElementById('table-div').innerHTML);
	LODOP.SET_PRINT_STYLEA(11,"ItemType",0);
	LODOP.SET_PRINT_STYLEA(11,"HOrient",3);
	LODOP.SET_PRINT_STYLEA(11,"VOrient",0);
	LODOP.ADD_PRINT_LINE(20,78,1100,78,0,2);
	LODOP.SET_PRINT_STYLEA(12,"ItemType",1);
	LODOP.PREVIEW();
}
function setValue(_method){
	$.post(
		App.App_Info.BasePath+'/InHospitalCase/CourseRecordingAction.do',
		{
			method:_method,
			id:kid
		},
		function(data){
			if(data.success){
				var json=JSON.parse(data.data);
				$.each(json,function(_key){
					if($('*[name="'+_key+'"]').size()>0)
						$('*[name="'+_key+'"]').html((json[_key]+'').replaceAll(' ','&nbsp;').replaceAll('\n','<p>').replaceAll(',','，'));
				});
			}else{
				alert('获取打印数据失败。');
			}
		},
		'json'
	);
}
