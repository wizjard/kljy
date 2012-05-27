PicUploadSupport = function(){}

PicUploadSupport.getFilePath = function(){
		var accessPath = window.location.search;
		if(accessPath != null && accessPath != "")
		{
			var start = accessPath.indexOf("=");
			var filePath = accessPath.substring(start+1,accessPath.toString().length);
			if(filePath != null && filePath !="")
			{
				var firstDiv = document.getElementById("first");
				firstDiv.style.display="block";
				firstDiv.firstChild.src="../../"+filePath;
			}
		}
}

PicUploadSupport.checkName = function()
{
		var filePath = document.getElementById("imgFile").value;
		var startNum = filePath.lastIndexOf("\\");
		var fileName = filePath.substring(startNum+1,filePath.toString().length);
		var endNum = fileName.indexOf(".");
		var fileEndName = fileName.substring(endNum+1,fileName.length);
		if("gif" != fileEndName && "jpg" != fileEndName && "png" != fileEndName && "bmp" != fileEndName && "jpeg" != fileEndName)
		{
			window.alert("请上传图片后缀是gif、jpg、png、bmp、jpeg格式的图片");
			document.getElementById("uploadForm").action ="";
			return;
		}
}