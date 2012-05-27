/* 科研标本登记JS*/
var id = App.util.getHtmlParameters('id');//id
var patientID = App.util.getHtmlParameters('patientID');
var rid = App.util.getHtmlParameters('rid');//病人入选课题id
var courseId = App.util.getHtmlParameters('courseId');//课题id
var add = App.util.getHtmlParameters('add');
var timesText = App.util.getHtmlParameters('timesText');
var kid = App.util.getHtmlParameters('kid');
//var responseData={};
Ext.onReady(function(){
		pageInfo();
		if(add=='add'){
			initDefaultData();
		}else {
			initPageValue();
		}
});

//编写页面表单的函数
function pageInfo(){
		var _form = new Ext.form.FormPanel({
   		id:'registerForm',
   		border:false,
   		frame:true,
   		layout:'column',//定义列的布局
   		width:650,
   		labelWidth:80,
   		labelAlign:'right', //相当于align="right" 让文字右对齐
   		items:[
   			{//第一行第一列  由columnWidth为1时确定一行
   				columnWidth:1,
   				html:'<div   align="center" style="font-size:12px"><br><h3>科研标本处理登记表</h3><br></div>'
   			}
   			,{
   				layout:'form',
   				items:[{
						xtype:'datefield',
			   			fieldLabel:'登记日期',
			   			format:'Y-m-d',
			   			readOnly:'true',
			   			name:'dateTime'
		  			}]
   			},{
   				columnWidth:1,//第二列占总宽的50%
   				layout:'form',
   				items:[{
					xtype:'textfield',
		   			fieldLabel:'专项名称',
		   			name:'specialName', 
		   			anchor:'85%'
		  			}]
   			},
   			{
   				columnWidth:1,
   				layout:'form',
   				items:[{
					xtype:'textfield',
		   			fieldLabel:'课题名称',
		   			name:'name', 
		   			anchor:'85%'
		  			}]
   			},
	   		{//第二行第一列
   				columnWidth:.5,//第二列占总宽的50%
   				layout:'form',
   				items:[
   					{
   					xtype:'textfield',
			   			fieldLabel:'课题负责人',
			   			name:'responser'
			   		  
			   		},{
			   			 xtype:'textfield',
			   			fieldLabel:'病人姓名',
			   			name:'patientName'
			   			
			   		},{
			   			xtype:'textfield',
			   			fieldLabel:'诊断',
			   			name:'diacrisis'
			   		},{
			   			xtype:'textfield',
			   			fieldLabel:'数量',
			   			name:'number'
			   		},{
			   			xtype:'textfield',
			   			fieldLabel:'送标本科室',
			   			name:'belong'
			   		}
   				]
   			},{//第二行第二列
   				columnWidth:.5,
   				layout:'form',
   				items:[
   					{
			   			xtype:'textfield',
			   			fieldLabel:'科室',
			   			name:'scientificRoom'
			   		},
			   		{
			   			
			   				xtype:'textfield',
			   			fieldLabel:'病案号',
			   			name:'patientNo'
			   			
			   		},
			   		{
			   			xtype:'textfield',
			   			fieldLabel:'标本类型',
			   			name:'sampleType'
   					},
   					{
			   			xtype:'textfield',
			   			fieldLabel:'送标本人',
			   			name:'giveSampleP'
			   		},{
			   			xtype:'textfield',
			   			fieldLabel:'备注',
			   			name:'remarks'
			   		},{
						xtype:'hidden',
						name:'id',
						//fieldLabel:'ID',
						id:'id'
					}
   				]
   			},{//第三行一列
   				columnWidth:1,
   				html:'<br>注解：<br>&nbsp;&nbsp;1.每科室请按课题分类填写。<br>&nbsp;&nbsp;2.采血管上请注明姓名、病例号、诊断。<br>&nbsp;&nbsp;3.本科室常规每10ml全血分2管血浆(1.2-1.5ml/管)、2管pbmc，如有特殊要求请在备注中注明。</li></ol>'
   			}
   		]
   	});

		new Ext.Viewport({
   		layout:'border',
   		items:[{
   				region:'center',
   				id:'center-panel',
   				border:false,
				autoScroll:true,
				items:_form
   			}
   		]
   	});
}

/*初始化页面数据*/
function initDefaultData(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/EMR/register.do?method=initPageData&patientID='+patientID+'&researId='+courseId,
		success:function(_response,_options){
				var _m = Ext.util.JSON.decode(_response.responseText);
					if(_m){
						for(var key in _m){  //给页面中的文本框赋值
							Ext.getCmp('registerForm').form.findField(key).setValue(_m[key]);
						}
					}else{
						Ext.Msg.alert('提示','页面数据加载失败。',function(){});
					}
			}
		})
	}
	
	function initPageValue(){
		Ext.Ajax.request({
			url:App.App_Info.BasePath+'/EMR/register.do?method=findRegister',
			params:{id:id},
   			success:function(_response,_options){
   				var _m = Ext.util.JSON.decode(_response.responseText);
   				if(_m.success){
   					var object=Ext.decode(_m.data);
   					for(var key in object){
   						if(Ext.getCmp('registerForm').form.findField(key)){
   							//alert(key+"="+Ext.getCmp('registerForm').form.findField(key)+"  "+object[key]);
   							if(key=='dateTime'){
   							var dateTime=object[key].split(" ")[0]
   								Ext.getCmp('registerForm').form.findField(key).el.dom.value=dateTime;
   							}else{
   								Ext.getCmp('registerForm').form.findField(key).setValue(object[key]);
   							}
   						}
   					}	
   				}else {
   					Ext.Msg.alert('提示','页面数据加载失败！',function(){});
   				}
   			}
		})
	}
	function saveData(){	
		var json=Ext.getCmp('registerForm').form.getValues();
		if(json.dateTime==''){
			alert('请输入登记日期')
			return;
		}
		var _obj=Ext.encode(json);
		Ext.Ajax.request({
			url:App.App_Info.BasePath+'/EMR/register.do?method=saveOrUpdaeRegister',
			params:{
				data:_obj,
				pid:patientID,
				rid:rid,
				timesText:timesText
			},
			success:function(_response,_options){
				var _m = Ext.util.JSON.decode(_response.responseText);
					if(_m.success){
						alert("保存成功！");
					}else{
						alert("保存失败！");
					}
				}	
			});
		}
		function printData(){
			window.open('srPrintView.html');
		}
								