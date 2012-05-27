var showDate//随访计划时期
var dateListList = new Array();
var paId;
var itemList;
var itemdiv = document.getElementById("itemdiv");
var timeoutId;
var isPad = this.parent.location.toString().indexOf('pad')!=-1;
var oheight;
Ext.onReady(function(){
	paId = App.util.getHtmlParameters('paId');
	getshowDate();
	if(dateListList.length==0){
		alert("尚未安排该患者随访检查项目");
		return;
	}
	//createItemTable();
	var simpleForm = new Ext.FormPanel({ 
	      labelAlign: 'right', 
	      broder:false,
	      buttonAlign:'right', 
	      bodyStyle:'padding:5px', 
	      width: 600, 
	      frame:true, 
	      layout:'column',
	      defaults:{
	      	border:false,
	      	layout:'form'
	      },
	      items: [
	      	{
	      		columnWidth:.3,
	      		labelWidth:100,
	      		items:[
	      			{
	      				fieldLabel:'请选择月份',
			      		xtype:'combo',
			      		mode: 'local',
			      		store: new Ext.data.SimpleStore({
			      			fields:['clinicValue','showClincType'],
			                data: dateListList
			           }),
			      	   displayField: 'showClincType',
			      	   valueField: 'clinicValue',
			      	   value:dateListList[0][1],
			      	   triggerAction: 'all',
			      	   readOnly: true,
			      	   anchor:'100%',
			      		 listeners:{
				      	   	  select:function(){
	      						showDate =this.getValue(); 
	      						var div = document.getElementById("id");
								div.innerHTML = createDateView();
								//createItemTable();
				      	   	  }
				      	   }	      	    
			      	}
	      		]
	      	}
	      ]
		}); 	
	new Ext.Viewport({
		layout:'border',
		items:[
			{
				region:'north',
				layout:'fit',
				border:false,
				height:50,
				items:simpleForm
			},{
				region:'center',
				layout:'fit',
				autoScroll:true,
				border:false,
				listeners:{
					render:function(){
						var div = document.createElement("div");
						div.innerHTML = '<div id="id" style="padding:5px,0,0,67px">'+createDateView()+'</div>';
						var tldiv = document.createElement("div");
						tldiv.id = "tl";
						tldiv.innerHTML = '<div id="redblock"></div><div class="tltext">建议随访日期</div>'
							+'<div id="blueblock"></div><div class="tltext">实际就诊日期</div>'
							+'<div id="greenblock"></div><div class="tltext">本次随访已完成，数据已关联</div>';
						this.body.dom.appendChild(div);
						this.body.dom.appendChild(tldiv);
					}
				}
			}
		]
	});
	
	oheight =  window.document.height;
});

function getshowDate(){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/plan.do',
		params:{
			method:'findPlanDate',
			paId:paId
		},
		sync:true,
		success:function(response){
			var data = Ext.util.JSON.decode(response.responseText);
			var dateList = data.dateList;
			for(var i=0,size=dateList.length;i<size;i++){
				var dateList1 = new Array();
				dateList1.push(dateList[i].dateValue);
				dateList1.push(dateList[i].dateList);
				dateListList.push(dateList1);
			}
			if(dateListList.length==0){
				return;
			}
			showDate = dateListList[0][0];
		}
	});
}

//创建日期显示视图
function createDateView(){
	var weekList=null;
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/plan.do',
		params:{
			method:'findAllDayInWeek',
			date:showDate,
			paId:paId
		},
		sync:true,
		success:function(response){
			var data = Ext.util.JSON.decode(response.responseText);
			weekList = data.weekList;
		}
	});
	var start = showDate.indexOf('-');
	var monthValue = showDate.substring(start+1,start+3);
	if(monthValue.substring(0,1)=="0"){
		monthValue = monthValue.substring(1,2);
	}
	
	if(monthValue.indexOf("-") > -1) {
		monthValue = monthValue.substring(0, monthValue.indexOf("-"));
	}
	//alert(showDate + "|" + monthValue);
	monthValue +="月";
	var innerHTML='<table id="calender" cellspacing="0" border="1" width="800" style="margin-left:20px"><tr style="text-align:center;height:30px" bgcolor="#ABABAB"><td colspan="7"><font color="red"><B>'+monthValue+'</B></font></td></tr>'
		+'<tr style="height:40px;text-align:center;" bgcolor="#4682B4"><td>星期日</td><td>星期一</td><td>星期二</td><td>星期三</td><td>星期四</td><td>星期五</td><td>星期六</td></tr>'
		+weekList
		+'</table>';
	return innerHTML;
}

function createItemTable(dateOfMonth,visit){
	Ext.Ajax.request({
		url:App.App_Info.BasePath+'/plan.do',
		params:{
			method:'findItemsInDateByPatient',
			date:showDate,
			paId:paId,
			visit:visit,
			dateOfMonth:dateOfMonth
		},
		sync:true,
		success:function(response){
			itemList = response.responseText;
			itemdiv.innerHTML = itemList;
		}
	});
}


function showItems(event,visit){
	event = getEvent(event);
	var target = getTarget(event);
	var dateOfMonth = target.firstChild.nodeValue;
	createItemTable(dateOfMonth,visit);
	itemdiv.style.height = '250px';
	//======================================It's changed here===========2011-8-8================
	var left = event.clientX>150 ? 140 : event.clientX-10;
	var top = event.clientY>320 ? 310 : event.clientY-10;
	itemdiv.style.left = left+"px";//target.offsetLeft+50+"px";
	itemdiv.style.top = top+"px";//target.offsetTop+20+"px";
	//=========================================End of change====================================
	itemdiv.style.display = "block";
	
	if(isPad){
		window.parent.document.getElementById('mainFrame').height = oheight;
		setTimeout('itemdiv.style.height = itemdiv.scrollHeight+"px"',200);
		setTimeout('window.parent.document.getElementById("mainFrame").height=itemdiv.scrollHeight+400+"px"',500);
	}
//	var tn = document.createTextNode(itemList.toString());
//	itemdiv.replaceChild(tn, itemdiv.firstChild);
}

function hiddenItems(event){
	clearTimeout(timeoutId);
	timeoutId = setTimeout(function(){itemdiv.style.display = "none";}, 1000);
}

function onOverTable(event){
	clearTimeout(timeoutId);
}

function onOutTable(event){
	clearTimeout(timeoutId);
	timeoutId = setTimeout(function(){itemdiv.style.display = "none";}, 1000);
}

var selectOptionList =[];

function getEvent(event){
	return event?event:window.event;
}
function getTarget(event){
	return event.target || event.srcElement;
}
