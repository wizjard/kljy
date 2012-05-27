var id=App.util.getHtmlParameters('id');
var currentPage=1;//当前页
$(function () {
getData(currentPage);
});
function goPage(){
location.href='../followVisit/zhengzuangtizheng.html?id='+id;
}
function getColor(_c){
	_c.style.backgroundColor='#D4E0F2';
}

function setColor(_c){
	_c.style.backgroundColor='';
}
function getData(currentPage){
	$.post(
	"../followVisit/yanxingbinAction.do",
	{method:"findZhengZuang",currentPage:currentPage,id:id},
	function(data){
	currentPage=data[2];
	var _table = $("#listtable");
$("<tr onMouseOver='getColor(this)' onMouseOut='setColor(this)' align=center><td>状态</td>"+data[0]+"</tr>"+data[1]).appendTo(_table);
	},
"json");
}


