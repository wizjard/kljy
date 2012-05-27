	/*打印表头*/
	function CreateOneFormPage(pageCode,title){
		LODOP.PRINT_INIT("康乐家园-电子病历-病程记录打印");
		var　strBodyStyle　=　"<link　type='text/css'　rel='stylesheet' href='lodop.css'/>";
		LODOP.SET_PRINT_PAGESIZE(2,0,0,'A4');
		LODOP.ADD_PRINT_IMAGE(20,80,50,67,'<img width="50" height="67" src="'+App.App_Info.BasePath+'/PUBLIC/images/youanLogo.jpg"/>');
		LODOP.SET_PRINT_STYLEA(1,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(50,130,400,40,'首都医科大学附属北京佑安医院');
		LODOP.SET_PRINT_STYLEA(2,"Bold",1);
		LODOP.SET_PRINT_STYLEA(2,"FontSize",15);
		LODOP.SET_PRINT_STYLEA(2,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(70,130,400,25,"BeiJing YouAn Hospital,Capital Medical University");
		LODOP.SET_PRINT_STYLEA(3,"FontSize",7);
		LODOP.SET_PRINT_STYLEA(3,"Bold",1);
		LODOP.SET_PRINT_STYLEA(3,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(86,500,1100,40,title);//标题
		LODOP.SET_PRINT_STYLEA(4,"FontSize",17);
		LODOP.SET_PRINT_STYLEA(4,"Bold",1);
		LODOP.SET_PRINT_STYLEA(4,"ItemType",1);
		LODOP.ADD_PRINT_LINE(122,73,122,1050,0,1);//上横线
		LODOP.SET_PRINT_STYLEA(5,"ItemType",1);
		LODOP.ADD_PRINT_LINE(20,73,750,73,0,2);//左边线
		LODOP.SET_PRINT_STYLEA(6,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(112,500,100,20,'第 # 页  共 & 页');
		LODOP.SET_PRINT_STYLEA(7,"ItemType",2);
		LODOP.SET_PRINT_STYLEA(7,"FontSize",10);//以上均为页眉
		LODOP.ADD_PRINT_HTM(125,55,1100,570,document.getElementById(pageCode).innerHTML);
		LODOP.SET_PRINT_STYLEA(8,"ItemType",4);
	};	