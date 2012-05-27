function PrintObject(){
	var po=this;
	this.printName='';
	this.headerTitle=po.printName;
	
	var initPrintTask=function(){
		LODOP.PRINT_INIT(po.printName);
		LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
	}
	var createHeader=function(){
		LODOP.ADD_PRINT_IMAGE(20,80,50,67,'<img width="50" height="67" src="'+App.App_Info.BasePath+'/PUBLIC/images/youanLogo.jpg"/>');
		LODOP.SET_PRINT_STYLEA(1,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(50,130,400,40,"首都医科大学附属北京佑安医院");
		LODOP.SET_PRINT_STYLEA(2,"FontSize",15);
		LODOP.SET_PRINT_STYLEA(2,"Bold",1);
		LODOP.SET_PRINT_STYLEA(2,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(70,130,400,25,"BeiJing YouAn Hospital,Capital Medical University");
		LODOP.SET_PRINT_STYLEA(3,"FontSize",7);
		LODOP.SET_PRINT_STYLEA(3,"Bold",1);
		LODOP.SET_PRINT_STYLEA(3,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(85,315,340,40,"  " + po.printName);
		LODOP.SET_PRINT_STYLEA(4,"FontSize",19);
		LODOP.SET_PRINT_STYLEA(4,"Bold",1);
		LODOP.SET_PRINT_STYLEA(4,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(115,150,100,20,"第"+ $('#pInhspTimes').text() +"次住院");
		LODOP.SET_PRINT_STYLEA(5,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(115,620,150,20,"病案号："+ $('#pNo').text() + "");
		LODOP.SET_PRINT_STYLEA(6,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(100,620,150,20,"姓  名："+ $('#pName').text()+"" );
		LODOP.SET_PRINT_STYLEA(7,"ItemType",1);
	  LODOP.ADD_PRINT_LINE(129,80,129,750,0,1);
		LODOP.SET_PRINT_STYLEA(8,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(115,343,110,20,"      第 # 页    ");
		LODOP.SET_PRINT_STYLEA(9,"ItemType",2);
	}
	var createFooter=function(){
		LODOP.ADD_PRINT_LINE(1055,80,1055,750,0,1);
		LODOP.SET_PRINT_STYLEA(11,"ItemType",1);
		LODOP.ADD_PRINT_LINE(20,78,1100,78,0,2);
		LODOP.SET_PRINT_STYLEA(12,"ItemType",1);
	}
	var createContent=function(_contents){
		LODOP.ADD_PRINT_HTM(135,80,650,900,_contents);
		LODOP.SET_PRINT_STYLEA(10,"ItemType",0);
		LODOP.SET_PRINT_STYLEA(10,"HOrient",3);
		LODOP.SET_PRINT_STYLEA(10,"VOrient",0);
	}
	this.Print=function(_contents){
		initPrintTask();
		createHeader();
		createContent(_contents);
		createFooter();
		LODOP.PREVIEW();
	}
	this.DoublePrint=function(_contents){
		initPrintTask();
		createHeader();
		createContent(_contents);
		createFooter();
		LODOP.SET_PRINT_MODE("DOUBLE_SIDED_PRINT",true);
		LODOP.PREVIEW();
	}
	this.NextPrint=function(_contents){
		initPrintTask();
		
		LODOP.ADD_PRINT_LINE(1055,80,1055,750,0,1);
		LODOP.SET_PRINT_STYLEA(1,"FontColor",'#FFF');
		LODOP.SET_PRINT_STYLEA(1,"ItemType",1);
		
		LODOP.ADD_PRINT_LINE(20,78,1100,78,0,2);
		LODOP.SET_PRINT_STYLEA(2,"FontColor",'#FFF');
		LODOP.SET_PRINT_STYLEA(2,"ItemType",1);
		
		LODOP.ADD_PRINT_LINE(129,80,129,750,0,1);
		LODOP.SET_PRINT_STYLEA(3,"FontColor",'#FFF');
		LODOP.SET_PRINT_STYLEA(3,"ItemType",1);
		
		LODOP.ADD_PRINT_HTM(135,80,650,900,_contents);
		LODOP.SET_PRINT_STYLEA(4,"ItemType",0);
		LODOP.SET_PRINT_STYLEA(4,"HOrient",3);
		LODOP.SET_PRINT_STYLEA(4,"VOrient",0);
		
//		createContent(_contents);
		LODOP.PREVIEW();
	}
	this.CreateContent=function(){
		var html='';
		var PatientInfo=$('#PatientInfo').html();
		html+=PatientInfo;
		var CaseHistory=$('#CaseHistory').html();
		html+=CaseHistory;
		var PhysicalExamination=$('#PhysicalExamination').html();
		if(PhysicalExamination&&PhysicalExamination.length>0){
			html+=PhysicalExamination;
		}
		var TCM4Diag=$('#TCM4Diag').html();
		if(TCM4Diag&&TCM4Diag.length>0){
			html+=TCM4Diag;
		}
		var LabExamination=$('#LabExamination').html();
		if(LabExamination&&LabExamination.length>0){
			html+=LabExamination;
		}
		var SpecialExamination=$('#SpecialExamination').html();
		if(SpecialExamination&&SpecialExamination.length>0){
			html+=SpecialExamination;
		}
		var Diagnosis=$('#Diagnosis').html();
		html+=Diagnosis;
		var RevisedDiagnosis=$('#RevisedDiagnosis').html();
		if($('td[name="dz_diagnosis"]').html()=='&nbsp;'){
			RevisedDiagnosis='';
		}
		html+=RevisedDiagnosis;
		return html;
	}
	this.singlePageData = function(_contents){
		initPrintTask();
		createHeader();
		createContent(_contents);
		createFooter();
		LODOP.PREVIEW();
	}
}
function pageInit(_CaseType,_printName){
		LODOP=document.getElementById("LODOP");
		CheckLodop(App.App_Info.BasePath+'/PUBLIC/Scripts/Lodop/');
		setValue({
			method:'GetPrintData',
			id:App.util.getHtmlParameters('id'),
			CaseType:_CaseType
		});
		var po=new PrintObject();
		po.printName=_printName;
		$('#all-double-none').click(function(){
			po.Print(po.CreateContent());
		});
		$('#all-double').click(function(){
			po.DoublePrint(po.CreateContent());
		});
		$('#next-Diagnosis').click(function(){
			$('table.conTable').addClass('white');
			$('*[name="queding_diagnosis"],*[name="queding_diagnosis_time"]').addClass('black');
			po.NextPrint(po.CreateContent());
			$('table.conTable').removeClass('white');
			$('*[name="queding_diagnosis"],*[name="queding_diagnosis_time"]').removeClass('black');
		});
		
		$('#next-Diagnosis2').click(function(){
			$('table.conTable').addClass('red');
			$('*[name="queding_diagnosis"],*[name="queding_diagnosis_time"]').addClass('black');
			po.NextPrint(po.CreateContent());
			$('table.conTable').removeClass('red');
			$('*[name="queding_diagnosis"],*[name="queding_diagnosis_time"]').removeClass('black');
		});
		
		$('#next-RevisedDiagnosis').click(function(){
			$('table.conTable').addClass('white');
			$('#RevisedDiagnosis table').addClass('black');
			po.NextPrint(po.CreateContent());
			$('table.conTable').removeClass('white');
			$('#RevisedDiagnosis table').removeClass('black');
		});
		
		$('#next-RevisedDiagnosis2').click(function(){
			$('table.conTable').addClass('red');
			$('#RevisedDiagnosis table').addClass('black');
			po.NextPrint(po.CreateContent());
			$('table.conTable').removeClass('red');
			$('#RevisedDiagnosis table').removeClass('black');
		});
}

	/**
	 * 单独页面打印页面调用函数
	 */
	function singlePageInit(id,caseType,_printName){
		LODOP=document.getElementById("LODOP");
		CheckLodop(App.App_Info.BasePath+'/PUBLIC/Scripts/Lodop/');
		setSinglePageValues({method:'getSinglePagePrintData',caseType:caseType,id:id},_printName);
		var po=new PrintObject();
		po.printName=_printName;
		$('#singlePage').click(function(){
			po.singlePageData($('#table-div').html());
		});
	}
