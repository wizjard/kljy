var caseId=App.util.getHtmlParameters('id');
var addsyid = "";
var _statuOptions = "<option>&nbsp</option>";
var _flag = "";
var hidsy_id = "";
var _id = "";
//自动加载页面
$(function () {
//加载状态
	$.post("../followVisit/yanxingbinAction.do", {method:"findT_state"}, function (data) {
		for (var i = 0, len = data.length; i < len; i++) {
			var _item = data[i];
			_statuOptions += "<option value=\"" + _item.t_id + "\">" + _item.t_statu + "</option>";
		}
	}, "json");
//加载查询症状	
	$.post("../followVisit/yanxingbinAction.do", {method:"findZhengzuangtizheng",id:caseId}, function (data) {
		var _table = $("#listtable");
		if (data.length !== 0) {
			$("<tr align=center><td>\u75c7\u72b6</td><td>\u4e0a\u6b21\u767b\u8bb0\u65f6\u95f4:<span id=\"time\">" + data[0].time.substr(0, 10) + "</span></td><td>\u672c\u6b21\u767b\u8bb0\u65f6\u95f4:" + new Date().format('yyyy-MM-dd') + "</td><td>\u64cd\u4f5c</td></tr>").appendTo(_table);
		} else {
			$("<tr align=center><td>\u75c7\u72b6</td><td>\u6ca1\u6709\u4e0a\u6b21\u8bb0\u5f55</td><td>\u65f6\u95f4:" + new Date().format('yyyy-MM-dd') + "</td><td>\u64cd\u4f5c</td></tr>").appendTo(_table);
		}
		$.each(data, function (i) {
			var _item = this;
			var sum = 0;
			sum += i;
			hidsy_id = "id" + new Date().getTime() + sum;
			if("无"===_item.sy_text){
			var show_text="";
			}else{
			show_text=":"+_item.sy_text;
			}
			if (_item.statu != "\u6d88\u5931") {
				$("<tr class=\"tr_id\" align=center ><td><input type=\"hidden\" name=\"" + sum + "\" id=\"" + sum + "\" value=\""+_item.sy_id+"\"</input>" + _item.name +show_text+"<input type=\"hidden\" name=\"hid\" id=\"hid\" value=\"" + _item.t_id + "\"</input>"+
			"<input type=\"hidden\" ></input>"+
			"<input type=\"hidden\" value='"+_item.sy_text+"' ></input>"+
				"</td><td>" + _item.statu + "</td><td><select name=\"statuSelect\" style=\"width:65px\">" + _statuOptions + "</select></td><td>&nbsp</td></tr>").appendTo(_table);
			}
		});
	}, "json");
});

function ShowDisplay(name,_this){
var _url=$(_this).parent().data('url');
if(null!==_url){
	var ReturnValue2 = window.showModalDialog("show/"+_url, null, "dialogWidth=526px;dialogHeight=500px");
	if(ReturnValue2){
	$("#"+name).val(ReturnValue2.toString());
	}
}else
{
alert("加载出错，没有对应的url")
}
}
//弹出病症页面
function ShowIllsDictWin(id, name) {
	var ReturnValue = window.showModalDialog("gett_symptom.html", null, "dialogWidth=380px;dialogHeight=500px");
	if (ReturnValue) {
		var valueItem = ReturnValue.toString().split(",");
		var _id = valueItem[0];
		var _name = valueItem[1];
		var _url=valueItem[2];
		if(""===_url){
			$("#div"+hidsy_id).css('display','none');
			$("#show"+hidsy_id).val("无");
		}else{
		 $("#show"+hidsy_id).val("")
			$("#div"+hidsy_id).css('display','block');
			$("#div"+hidsy_id).css('float','left');
			$("#div"+hidsy_id).data('url',_url);
		}
		var flag = 1;
  //判断是否有相同的名称
		$.each($("tr.tr_id"), function () {
			var _tds = this.getElementsByTagName("td");
			var _ids = _tds[0].getElementsByTagName("input")[1].value;
			if (_ids === _id) {
				alert("\u201c" + _name + "\u201d\u5df2\u7ecf\u5b58\u5728\uff01");
				ShowIllsDictWin(id, name);
				flag = 2;
				return false;
			}
		});
		if (flag === 1) {
			$("#" + id + "").val(_id);
			$("#" + name + "").val(_name);
			
		}
	}
}
//提交
function SubmitData() {
	var _submit = [];
	var mark = document.getElementById("remarkid").value;//标记
	$.each($("tr.tr_id"), function () {
	var _tds = this.getElementsByTagName("td");
		_json = {
		hidsy_id:_tds[0].getElementsByTagName("input")[0].id, 
		syid:_tds[0].getElementsByTagName("input")[0].value, 
		t_symptom_ID:_tds[0].getElementsByTagName("input")[1].value, 
		t_state_ID:_tds[2].getElementsByTagName("select")[0].value,
		tp_text:_tds[0].getElementsByTagName("input")[3].value};
		_submit.push(_json);
	});
	
	$.post("../followVisit/yanxingbinAction.do", {method:"saveZhengzuangtizheng", array:JSON.stringify(_submit), mark:JSON.stringify(mark),id:caseId,}, function (data) {
		if (data[2].success) {
			$.each(data[0], function () {
				var _item = this;
				$("#" + _item.hidsy_id + "").val(_item.syid);
			});
			$("#remarkid").val(data[1]);
			alert("\u6570\u636e\u4fdd\u5b58\u6210\u529f\u3002");
		} else {
			alert("\u6709\u7a7a\u503c\uff0c\u6570\u636e\u4fdd\u5b58\u5931\u8d25\u3002");
		}
	}, "json");
}


//插入
$(function () {
	$("input[name=insert]").click(function () {
		hidsy_id = new Date().getTime();
		var hid_id = hidsy_id + 2;
		var _name = hidsy_id + 3;
		var _display=hidsy_id+4;
		var _table = $("#listtable");
		$("<tr class=\"tr_id\" align=center >"+
		"<td><div style='float:left'><input type=\"hidden\" name=\"hidsy_id\" id=\"" + hidsy_id + "\" value=\"\"</input>" + 
		"<input type=\"hidden\" name=\"hid\" id=\"" + hid_id + "\" value=\"\"</input>" +
	 " <input name=\"_id\" id=\"" + _name + "\" value=\"\" readonly=\"true\"></input>" + 
	 "<img src=\"../PUBLIC/images/sign.gif\"" + " style=\"cursor:pointer\" " + " onclick=\"ShowIllsDictWin(" + hid_id + "," + _name + ")\"/> " +
	"</div>"+
		   " <div id='div"+hidsy_id+"' style='display:none;'><input name=\"\" id='show"+hidsy_id+"'\"" + _name + "\" value=\"\" readonly=\"true\"></input>" + 
		 "<img src=\"../PUBLIC/images/sign.gif\"" + " style=\"cursor:pointer\" " + " onclick=\"ShowDisplay('show"+ hidsy_id+"',this)\"/> " +
		  "</div>"+
	  "</td><td>&nbsp</td><td><select style=\"width:65px\">" + _statuOptions + "</td><td><input type=\"button\" name=\"del\" id=\"del\" onclick=\"delRow(this," + hidsy_id + ")\" value=\"\u5220\u9664\" /></td></tr>").appendTo(_table);
	});
});
//删除一行
function delRow(_this, _id) {
	var _delId = document.getElementById(_id).value;
	if (_delId !== "") {
		delDate(_delId);
	}
	if (window.confirm("\u786e\u5b9a\u5220\u9664")) {
		_this.parentNode.parentNode.parentNode.removeChild(_this.parentNode.parentNode);
	}
}
//删除数据				
function delDate(_delId) {
	$.post("../followVisit/yanxingbinAction.do", {method:"delTr", _delId:_delId}, function (data) {
		if (data.success) {
			alert('删除数据成功。');
		} else {
			alert("\u5220\u9664\u6570\u636e\u5931\u8d25\u3002");
		}
	}, "json");
}

//获取症状
function rowopeclsClickClose(_this, _flag) {
	var _index = 1;
	$.each($("#listtable tr:eq(0) p.rowopecls"), function (_i, _item) {
		if (this == _this) {
			_index = _i + 1;
		}
	});
	$.each($("#listtable tr"), function (_id, _item) {
		if (_id < 3) {
			return true;
		}
		$.each($(this).children("td"), function (_inde, _td) {
			if (_inde == _index) {
				if (_flag) {
					_td.childNodes[0].style.display = "none";
					if (_td.childNodes.length > 1) {
						_td.childNodes[1].style.display = "block";
					} else {
						_td.innerHTML += "<p class=\"xiegangcls\">&nbsp;</p>";
					}
				} else {
					_td.childNodes[0].style.display = "block";
					_td.childNodes[1].style.display = "none";
				}
			}
		});
	});
}
