	/**
	 * 诊断专用选择窗口
	 * @param {Object} _display
	 * @param {Object} _hidden
	 */
	function ShowIllsDictWin(num){
		//肝病字典路径
		var path="../dictionary/diagnosisSelect.html";
		if(num==1){
			path="../Liver/dictionary/diagnosisSelect.html";
		}
		var returnObj=window.showModalDialog(path,null,
			'dialogWidth=400px;dialogHeight=500px');
		if (returnObj) {
			if(returnObj.cls){
				returnObj.text='';
				returnObj.value='';
			}
			$("input[name='diagnosis']").val(returnObj.text);
		}
	}
	
	/*传染病字典*/
	function showContagionDict(_this,num){
		//肝病 字典路径
		var path="../dictionary/pastHistoryDict.html";
		if(num==1){
			//甲流字典路径
			path="../Liver/dictionary/pastHistoryDict.html";
		}
		var returnObj = window.showModalDialog(path,null,'dialogWidth=400px;dialogHeight=500px');
		if(returnObj){
			if(returnObj.cls){
				returnObj.text='';
				returnObj.value='';
			}
			$(_this).prev().val(returnObj.text);
		}
	}
	
	/*打开流行史-旅行史窗口*/
	function openTraFlag(_this,pid,kid){
		var ob = window.showModalDialog('../H1N1/childPage/epidemVehicle.html?pid='+pid+'&kid='+kid,null,'dialogWidth=400px;dialogHeight=450px')
		if(ob){
			$(_this).prev().val(ob);
		}
		/*var re='';
		$.each(ob,function(key){
			if(key=='re'){
				re = ob[key];
			}
		})
		$(_this).prev().val(returnObj);*/
	}
