//加载页面数据
function onloadPageData() {
	var id = App.util.getHtmlParameters("id");
	$.post(
		App.App_Info.BasePath + "/admin/notice.do", 
		{method:"findById", id:id},
		function (data) {
			if (data.success) {
				var noticess = JSON.parse(data.data);
				var noticeNam = document.getElementById("noticeNam");
				noticeNam.innerHTML = noticess.noticeNam;
				var noticept = document.getElementById("personAndTime");
				noticept.innerHTML = "发布人："+	noticess.noticeAuthor+"&nbsp;&nbsp;&nbsp;&nbsp;发布时间："+noticess.noticeTim;		
				var noticeContent = document.getElementById("noticeContent");
				noticeContent.innerHTML = noticess.noticeContent.replaceAll('http://localhost:8090/TCMP',App.App_Info.BasePath)+"<br /><br />"+noticess.filePathList+"<br />";
			} 
	}, "json");
}
$(function(){
	onloadPageData();
});
