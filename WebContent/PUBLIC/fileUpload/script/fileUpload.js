FileUploadSupport = function(){}

FileUploadSupport.getFilePath = function(){
		var accessPath = window.location.search;
		if(accessPath != null && accessPath != "")
		{
			var start = accessPath.indexOf("=");
			var filePath = accessPath.substring(start+1,accessPath.toString().length);
			if(filePath != null && filePath !="")
			{
				var firstDiv = document.getElementById("first");
				firstDiv.style.display="block";
				firstDiv.firstChild.href="../../"+filePath;
				var fileEndNameStart = filePath.lastIndexOf("/");
				var fileEndName = filePath.substring(fileEndNameStart+1,filePath.length);
				var endNum = fileEndName.indexOf(".");
				var fileName = fileEndName.substring(0,endNum);
				firstDiv.firstChild.innerHTML="附件："+fileName;
			}
		}
}

FileUploadSupport.checkName = function()
{
		var filePath = document.getElementById("imgFile").value;
		var startNum = filePath.lastIndexOf("\\");
		var fileName = filePath.substring(startNum+1,filePath.toString().length);
		var endNum = fileName.indexOf(".");
		var fileEndName = fileName.substring(endNum+1,fileName.length);
		if("zip" != fileEndName && "rar" != fileEndName)
		{
			window.alert("请上传文件后缀是rar或者zip格式的文件");
			document.getElementById("uploadForm").action ="";
			return;
		}
}