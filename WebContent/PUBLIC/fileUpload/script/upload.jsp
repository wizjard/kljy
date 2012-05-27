<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="java.io.*"%>
<%@page import="java.net.URLEncoder"%>

<%
	String uploadType = request.getParameter("uploadType");
	String contextPath = request.getContextPath();
	//截取当前访问路径 例：http://localhost:8080/
	String requestUrI = request.getRequestURI();
	String requestUrl = request.getRequestURL().toString().replace(
			requestUrI, "");

	String SavePath = request.getSession().getServletContext()
			.getRealPath("/")
			+ "image\\";
	String SaveUrl = contextPath + "/image/";
	//System.out.println("li==="+SaveUrl);
	//System.out.println("c--->" + contextPath + "----->" + SavePath
			//+ "---->" + SaveUrl);
	String[] ExtArr = new String[] { ".gif", ".jpg", ".png", ".bmp",
			".jpeg", ".rar", ".zip" };
	String id = "";
	int MaxSize = 4000000;
	String Msg1 = "上传文件大小超过限制。";
	String Msg2 = "上传文件的扩展名不被允许。";
	String Msg3 = "文件上传失败。";
	String Msg = Msg3;
	String FileWidth = null;
	String FileHeight = null;
	String FileBorder = null;
	String FileTitle = null;
	String FileTitle2 = null;
	String FileAlign = null;
	String FileHspace = null;
	String FileVspace = null;
	String fileNameJs = null;//文件名称

	String resultFilePath = null;//存放文件路径  

	Date dt = new Date();
	Random random = new Random();
	random.nextInt();
	String FileNameAuto = String.format("%X_%X", new Object[] {
			new Integer((int) (dt.getTime())),
			new Integer(random.nextInt()) });

	String FilePath = null;
	String FileUrl = null;
	DiskFileUpload fu = new DiskFileUpload();
	fu.setSizeMax(MaxSize); //   
	fu.setSizeThreshold(4096);
	fu.setRepositoryPath("d:/");
	List fileItems = fu.parseRequest(request);
	//System.out.println(fileItems);
	Iterator iter = fileItems.iterator();
	while (iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		String filePathJs = item.getName();
		int startNum = filePathJs.lastIndexOf("\\");
		
		fileNameJs = filePathJs.substring(startNum+1,filePathJs.length());
		int endName = fileNameJs.indexOf(".");
		
		FileNameAuto = fileNameJs.substring(0,endName);
		String fieldName = item.getFieldName();
		if (item.getFieldName().equals("id")) {
			id = item.getString();
		}
		if (!item.isFormField()) {
			String name = item.getName();
			long size = item.getSize();
			if ((name == null || name.equals("")) && size == 0)
				continue;
			if (size > MaxSize) {
				Msg = Msg1;
				break;
			}
			int pos = name.lastIndexOf(".");
			String ext = name.substring(pos);
			boolean b = false;
			for (int m = 0; m < ExtArr.length; m++) {
				if (ExtArr[m].equalsIgnoreCase(ext)) {
					b = true;
					break;
				}
			}
			if (b == false) {
				Msg = Msg2;
				break;
			}
			FilePath = SavePath + FileNameAuto + ext;
			//System.out.println(FilePath + "  " + SavePath + "   "
					//+ FileNameAuto + "  " + ext);
			FileUrl = SaveUrl + FileNameAuto + ext;
			resultFilePath = requestUrl + FileUrl;//文件最后的相对路径（服务器端）

			File dirFile = new File(SavePath);
			if (!(dirFile.exists()) && !(dirFile.isDirectory())) {
				boolean creadok = dirFile.mkdirs();
				if (creadok) {
					System.out.println("ok:创建文件夹成功！ ");
				} else {
					System.out.println("err:创建文件夹失败！ ");
				}
			}
			java.io.File f = new java.io.File(FilePath);
			item.write(f);
		} else {
			String fieldValue = item.getString();
			if ("imgWidth".equals(fieldName)) {
				FileWidth = fieldValue;
			} else if ("imgHeight".equals(fieldName)) {
				FileHeight = fieldValue;
			} else if ("imgBorder".equals(fieldName)) {
				FileBorder = fieldValue;
			} else if ("imgTitle".equals(fieldName)) {
				FileTitle = fieldValue;
			} else if ("imgTitle2".equals(fieldName)) {
				FileTitle2 = URLDecoder.decode(fieldValue, "UTF-8");
			} else if ("imgAlign".equals(fieldName)) {
				FileAlign = fieldValue;
			} else if ("imgHspace".equals(fieldName)) {
				FileHspace = fieldValue;
			} else if ("imgVspace".equals(fieldName)) {
				FileVspace = fieldValue;
			}
		}
	}
	String test = new String(fileNameJs.getBytes("GBK"),"ISO-8859-1");
	if(uploadType != null)
	{
		response.sendRedirect("../../../PUBLIC/fileUpload/uploadPicture.html?filePath=image/"+test);
	}
	else
	{
		response.sendRedirect("../../../PUBLIC/fileUpload/uploadfile.html?filePath=image/"+test);
	}
%>
