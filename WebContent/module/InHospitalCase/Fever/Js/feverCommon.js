		var dbclickedSpan = null;
		var oldOperations = parent.getOldOperations();
		var retmainSymptomInfo = '';//在子页面要查的值
		var retmainSymptomArray = [];
		var itemName = "symptomInfo";
		var itemValue = "";
		var itemreturnValue = null;
		var textAreaValue = '';
		var symptomInfo;
		var retToPreMainSymptomInfo = '';
		var symptomArray = new Array();//存放导入的组合项目值
		var store;
		var addNo=0;//病人移除列表用，标志用户往列表中增加过几次，不包括移除操作
//移除列表和数据
		function removeFromList(){
			var rows = Ext.getCmp("symptomItemList").getSelectionModel().getSelections();											
			for(var i = 0; i < rows.length; i++){
				var j;
				for(j = 0; j < symptomArray.length; j = j + 3){
					if(symptomArray[j] == rows[i].data.serialNo && symptomArray[j + 1] == rows[i].data.symptomItem) break;
				}
				if(j != symptomArray.length){
					symptomArray.splice(j, 3);												
				}
				store.remove(rows[i]);
												
				var k = 0;
				for(; k < retmainSymptomArray.length; k = k + 2){
					if(retmainSymptomArray[k] == rows[i].data.serialNo) break;
				}
				retmainSymptomArray.splice(k, 2);
			}
											
			retmainSymptomInfo = "";
			i = 0;
			for(; i < retmainSymptomArray.length; i = i + 2){
				retmainSymptomInfo += retmainSymptomArray[i+1];
			}
			Ext.getCmp("symptomItemList").view.refresh();
			textAreaValue = symptomArrayToString(symptomArray);		
			$('#sysmptomContext').html(textAreaValue);
			$('#sysmptomContext').find('input').each(function(){
				$(this).keyup(function(){
					checkLength(this);
				});
				checkLength(this);
				$(this).change(function(){
					changeText(this);
				});
			});	
		}
		//预览
		function showFinalResult(){
			var finalResult = '';
			var rows = Ext.getCmp("symptomItemList").getSelectionModel().getSelections();
			$('#sysmptomContext').children().each(function(){
				var children = this.childNodes;
				var text = '';
				for(var i =0 ; i < children.length; i++){
					if(children[i].nodeName == "SPAN") continue;
					if(children[i].nodeType==3){
						text+=children[i].nodeValue;
					}else if(children[i].nodeType==1){
						text+=children[i].value;
					}
				}
				
				var j = 0;											
				for(; j < rows.length; j++){
					if(rows[j].data.serialNo == $(this).attr('name')) break;
				}
				if(j < rows.length){
					finalResult += '\n' + text;
				}else{
					finalResult += text;
				}
			});
			return finalResult;
		}
		function setOldOperations(){
			if(oldOperations != ""){
				var arr = oldOperations.split("~");
	  			var temp = arr[0];
	  			var _arr = temp.split("*");
				var i = 0;
	  			for(; i < _arr.length; i++){
	  				var obj = eval('(' + _arr[i] + ')');
	  				store.add(new symptomInfo(obj));
	  			}
	  				
	  			temp = arr[1];
	  			_arr = temp.split("*");
	  			for(i = 0; i < _arr.length; i = i + 3){
	  				symptomArray.push(_arr[i], _arr[i+1], _arr[i+2]);
	  			}
	  			//把相应的信息放入“现病史组合内容”中
				textAreaValue = symptomArrayToString(symptomArray);	
				$('#sysmptomContext').html(textAreaValue);
				$('#sysmptomContext').find('input').each(function(){
					$(this).keyup(function(){
						checkLength(this);
					});
					checkLength(this);						
					$(this).change(function(){
						changeText(this);
					});
				});		
			}
		}
		//验证有没有未选的
		function saveOldOperations(){
			oldOperations = "";
			var uncheckedInfo = "";
			store.each(function(symptomInfo){
				oldOperations = oldOperations + "{serialNo:'" +  symptomInfo.get('serialNo') + "',symptomItem:'" + symptomInfo.get('symptomItem') + "'}*";
			});
			if(oldOperations != ""){
				oldOperations = oldOperations.substr(0, oldOperations.length - 1) + "~";
			}	
												
			for(var j = 0; j < symptomArray.length; j = j + 3){
				oldOperations = oldOperations + symptomArray[j] + "*" + symptomArray[j+1] + "*" + symptomArray[j+2] + "*";
			}
			if(symptomArray.length != 0){
				oldOperations = oldOperations.substr(0, oldOperations.length - 1) + "~";
			}
									
			for(var k = 0; k < retmainSymptomArray.length; k = k + 2){
				oldOperations = oldOperations + retmainSymptomArray[k] + "*" + retmainSymptomArray[k+1] + "*";
			}
									
			if(oldOperations != ""){
				oldOperations = oldOperations.substr(0, oldOperations.length - 1);
			}
			return oldOperations; 
		}
		
		function symptomArrayToString(symptomArray){
			var str = "";
			for(var i = 0; i < symptomArray.length; i = i + 3){
				str = str + symptomArray[i+2];
			}
			return str;
		}
		
		
		//设置返回值
		function setReturnValue(span){
			var spanProperty = "";
			if(span){
				spanProperty = itemValue.replace('onclick', 'name="' + $(span).attr("name") + '" onclick');
				//获得所选记录在数组中的位置
				var _pos;
				for(_pos = 0; _pos < store.getCount(); _pos++){
					if(store.getAt(_pos).get("serialNo") == $(span).attr("name")) break;
				}
				symptomArray.splice(_pos * 3 + 2, 1);
				symptomArray.splice(_pos * 3 + 2, 0, spanProperty);
				textAreaValue = symptomArrayToString(symptomArray);	
				
				$('#sysmptomContext').html('');
				$('#sysmptomContext').html(textAreaValue);
					
				$('#sysmptomContext').find('input').each(function(){
					$(this).keyup(function(){
						checkLength(this);
					});
					checkLength(this);
					
					$(this).change(function(){
						changeText(this);
					});
				});	
				return;
			}
			addNo = addNo + 1;																									
			//在中间的记录列表中添加相应的信息
			var symItem = Ext.getCmp("symptomItemList").getSelectionModel().getSelections();
			var index = store.indexOf(symItem[0]);
			//给返回的值加上name属性值为：所在数组中的位置
			spanProperty = itemValue.replace('onclick', 'name="' + addNo + '" onclick');	
			//alert(spanProperty);			
			if(index != -1){
				//获得所选记录在数组中的位置
				var pos;
				for(pos = 0; pos < symptomArray.length; pos++){
					if(symptomArray[pos] == symItem[0].data.serialNo && symptomArray[pos + 1] == symItem[0].data.symptomItem) break;
				}
				//把导入的数据放入数组的指定位置处,一次向数组添加两个元素，分别是“序号”、“字段”和相应的需要在右边文本框中显示的信息
				symptomArray.splice(pos, 0, addNo, itemName, spanProperty);
				store.insert(index, new symptomInfo({serialNo:addNo,symptomItem:itemName}));
			}else{
				//把导入的数据放入数组中,一次向数组添加两个元素，分别是“序号”、“字段”和相应的需要在右边文本框中显示的信息						
				symptomArray.push(addNo, itemName, spanProperty);	
								
				store.add(new symptomInfo({serialNo:addNo,symptomItem:itemName}));
			}
			
			Ext.getCmp("symptomItemList").view.refresh();
			//把相应的信息放入“现病史组合内容”中
			textAreaValue = symptomArrayToString(symptomArray);
			$('#sysmptomContext').html('');
			$('#sysmptomContext').html(textAreaValue);
			
			$('#sysmptomContext').find('input').each(function(){
				$(this).keyup(function(){
					checkLength(this);
				});
				checkLength(this);
				
				$(this).change(function(){
					changeText(this);
				});
			});
		}
		
		function checkLength(inputInfo) {
			var inputLen = $(inputInfo).val().length;			
			if(inputLen > 0){
				$(inputInfo).width(inputLen * 14);
			}else{
				$(inputInfo).width(inputLen * 14 + 14);
			}
		} 

		function changeText(inputChanged){   
			var spanProperty = $(inputChanged).parent();
			spanProperty.wrap("<div id='spanwrap'></div>");
			spanProperty = $('#spanwrap').html();
			$('#spanwrap').replaceWith($('#spanwrap').html());
			//获得所选记录在数组中的位置
			var _pos;
			for(_pos = 0; _pos < store.getCount(); _pos++){
				if(store.getAt(_pos).get("serialNo") == $(inputChanged).parent().attr("name")) break;
			}
			symptomArray.splice(_pos * 3 + 2, 1);
			symptomArray.splice(_pos * 3 + 2, 0, spanProperty);
			
			$('#sysmptomContext').find('input').each(function(){
				$(this).keyup(function(){
					checkLength(this);
				});
				checkLength(this);
				
				$(this).change(function(){
					changeText(this);
				});
			});
		}
		function setPreHistorySymptom(){
			//循环遍历组合内容
			$('#sysmptomContext').children().each(function(){
				//获取span：name的值
				var _addNo = $(this).attr('name');
				var _pos = 0;
				for(; _pos < store.getCount(); _pos++){
					if(store.getAt(_pos).get("serialNo") == _addNo) break;
				}
				
				var _symptomObj = null;
				var _symptomName = store.getAt(_pos).get("symptomItem");
				
				if(_symptomName == "发病情况"){
					var item=0+'|'+$(this).find('SPAN').eq(0).text()+";";
					parent.indexArray.push(item);
				}
				if(_symptomName == "主要症状（必填项）"){
					var item=1+'|'+$(this).find('SPAN').eq(0).text()+";";
					parent.indexArray.push(item);
				}
				if(_symptomName == "次要症状"){
					var item=2+'|'+$(this).find('SPAN').eq(0).text()+";";
					parent.indexArray.push(item);
				}
				if(_symptomName == "重要阴性症状"){
					var item=3+'|'+$(this).find('SPAN').eq(0).text()+";";
					parent.indexArray.push(item);
				}
				if(_symptomName == "伴随症状"){
					var item=4+'|'+$(this).find('SPAN').eq(0).text()+";";
					parent.indexArray.push(item);
				}
				if(_symptomName == "诊疗经过"){
					var item=5+'|'+$(this).find('SPAN').eq(0).text()+";";
					parent.indexArray.push(item);
				}
				if(_symptomName == "症状病情演变"){
					var item=6+'|'+$(this).find('SPAN').eq(0).text()+";";
					parent.indexArray.push(item);
				}
				if(_symptomName == "目前状况"){
					var item=7+'|'+$(this).find('SPAN').eq(0).text()+";";
					parent.indexArray.push(item);
				}
				
				if(_symptomName == "其它疾病情况"){
					var item=8+'|'+$(this).find('SPAN').eq(0).text()+";";
					//alert(item);
					parent.indexArray.push(item);
				}
				
				
				
			});
		}