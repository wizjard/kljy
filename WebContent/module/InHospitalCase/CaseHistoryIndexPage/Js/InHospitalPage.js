	var ch = ['leaveHospital1','leaveHospital2','leaveHospital3','leaveHospital4','leaveHospital5','leaveHospital6','leaveHospital7','leaveHospital8'];
	/*初始化页面*/
	function initPage(pid,kid){
		$('button').mouseover(function(){
			$(this).addClass('btn-hover');
		}).mouseout(function(){
			$(this).removeClass('btn-hover');
		});
		
		/*初始化页面数据*/
		findInHospByCaseId(kid);
		
		/*checkbox事件*/
		$.each(ch,function(){
			$('input[name="'+this+'"]').click(function(){
				var $checked = $(this).attr('checked');
				$('input[name="'+$(this).attr('name')+'"]').attr('checked',false);
				if($checked){
					$(this).attr('checked',true);
				}else{
					$(this).attr('checked',false);
				}
			});
		});
	}	

	/*初始化日期控件*/
	function initTime() {
		FormUtil.toDateField({el:$('input[name="birthday"]'), dateFormat:"yyyy-MM-dd"});
		FormUtil.toDateField({el:$('input[name="inHspDate"]'), dateFormat:"yyyy-MM-dd HH:mm"});
		FormUtil.toDateField({el:$('input[name="outHspDate"]'), dateFormat:"yyyy-MM-dd HH:mm"});
		FormUtil.toDateField({el:$('input[name="queding_checkdate"]'), dateFormat:"yyyy-MM-dd"});
		FormUtil.toDateField({el:$('input[name="badate"]'), dateFormat:"yyyy-MM-dd HH:mm"});
		FormUtil.toDateField({el:$('input[name="tdate"]'), dateFormat:"yyyy-MM-dd"});
		FormUtil.toDateField({el:$('input[name="tdate2"]'), dateFormat:"yyyy-MM-dd"});
		FormUtil.toDateField({el:$('input[name="tdate3"]'), dateFormat:"yyyy-MM-dd"});
		FormUtil.toDateField({el:$('input[name="tdate4"]'), dateFormat:"yyyy-MM-dd"});
		FormUtil.toDateField({el:$('input[name="tdate5"]'), dateFormat:"yyyy-MM-dd"});
		FormUtil.toDateField({el:$('input[name="icuTurnInto1"]'), dateFormat:"yyyy-MM-dd HH:mm"});
		FormUtil.toDateField({el:$('input[name="icuTurnInto2"]'), dateFormat:"yyyy-MM-dd HH:mm"});
		FormUtil.toDateField({el:$('input[name="icuTurnInto3"]'), dateFormat:"yyyy-MM-dd HH:mm"});
		FormUtil.toDateField({el:$('input[name="icuTurnInto4"]'), dateFormat:"yyyy-MM-dd HH:mm"});
		FormUtil.toDateField({el:$('input[name="icuDropOut1"]'), dateFormat:"yyyy-MM-dd HH:mm"});
		FormUtil.toDateField({el:$('input[name="icuDropOut2"]'), dateFormat:"yyyy-MM-dd HH:mm"});
		FormUtil.toDateField({el:$('input[name="icuDropOut3"]'), dateFormat:"yyyy-MM-dd HH:mm"});
		FormUtil.toDateField({el:$('input[name="icuDropOut4"]'), dateFormat:"yyyy-MM-dd HH:mm"});
		FormUtil.toDateField({el:$('input[name="deliveryTime"]'), dateFormat:"yyyy-MM-dd HH:mm"});
	}
	
	/*初始化医生字典*/
	function doctorDict(){
		var _input = $('input');
		_input.filter(function(_index){
			return $(this).attr('name').indexOf('_show')!=-1;
		}).each(function(){
			FormUtil.toDictSelect({
				url:App.App_Info.BasePath+'/PUBLIC/Modaldialog/MyDoctorSelect.html',
				hiddenEl:$('input[name="'+$(this).attr('name').replace('_show','')+'"]'),
				showEl:$('input[name="'+$(this).attr('name')+'"]')
			});
		});
	}
	
	/*查询页面值*/
	function findInHospByCaseId(kid){
		$.post(
			App.App_Info.BasePath+'/InHosp.do',
			{
				method:'InHospById',
				caseId:kid
			},function(data){
				if(data.success){
					var _json = JSON.parse(data.data);
					if(_json){
						FormUtil.setFormValues('form',_json);
						$('input[type="checkbox"]').each(function(){
							var _va = _json[this.name];
							if($(this).val()==_va){
								$(this).attr('checked',true);
							}					
						});
					}
				}
			},'json'
		);
	}
	
	/*保存页面值*/
	function SaveData(){
		var _json = FormUtil.getFormValues('#data');
		$('input[type="checkbox"]').each(function(){
			_json[this.name] = $('input[name="'+this.name+'"][checked]').val();
		});	
		$.post(
			App.App_Info.BasePath+'/InHosp.do',
			{
				method:'InHospSaveOrUpdate',
				data:JSON.stringify(_json)
			},function(data){
				alert(data.success);
				if(data.success){
					var _json = JSON.parse(data.data);
					FormUtil.setFormValues('#data',_json);
					alert("保存成功！");
				}else{
					alert("保存失败！");
				}
			},'json'
		);
	}

	/*初始化页面数据-查询病人基本信息*/
	function initPagePre(caseId){
		$.ajax(
				{
					type:'post',
					url:App.App_Info.BasePath+'/InHosp.do' + '?method=getPageInfoByCaseId&caseId=' + caseId,
					async:false,
					dataType:'json',
					success:function(data){
						if(data.success){
							var _json = JSON.parse(data.data);
							FormUtil.setFormValues('#data',_json);
						}else {
							alert("初始化页面数据失败！");
						}
					}
				}	
		);
	}
	
	/**
	 * 打印表头
	 * title 打印标题
	 */
	function createHead(title, code){
		var text = code;
		LODOP.PRINT_INIT(title);
		LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
		LODOP.ADD_PRINT_TEXT(20,80,400,40,"医疗机构代码：0 0 6 7 0 5 1 1 0 1 0 6 2 1 0 2 1 1");
		LODOP.ADD_PRINT_TEXT(20,450,400,40,"医疗保险手册(卡)号：" + text);
		LODOP.SET_PRINT_STYLEA(2,"FontSize",9);
		LODOP.SET_PRINT_STYLEA(2,"ItemType",0);
		LODOP.SET_PRINT_STYLEA(2,"HOrient",3);
		LODOP.SET_PRINT_STYLEA(2,"VOrient",0);
		LODOP.ADD_PRINT_IMAGE(45,80,50,67,'<img width="50" height="67" src="'+App.App_Info.BasePath+'/PUBLIC/images/youanLogo.jpg"/>');
		LODOP.SET_PRINT_STYLEA(2,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(80,130,400,40,"首都医科大学附属北京佑安医院");
		LODOP.SET_PRINT_STYLEA(4,"FontSize",13);
		LODOP.SET_PRINT_STYLEA(4,"Bold",1);
		LODOP.SET_PRINT_STYLEA(4,"ItemType",1);
		LODOP.ADD_PRINT_TEXT(100,130,400,40,"BeiJing YouAn Hospital, Capital Medical University");
		LODOP.SET_PRINT_STYLEA(5,"FontSize",6);
		LODOP.SET_PRINT_STYLEA(5,"Bold",1);
		LODOP.SET_PRINT_STYLEA(5,"ItemType",1);
	}
	
	function createHeadTwo(title){
		LODOP.PRINT_INIT(title);
		LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
		
	}
