package com.juncsoft.KLJY.system.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.commons.fileupload.*;

import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;



public class UploadAction extends DispatchAction {
	
	//点击移除附件后执行的Action
	public ActionForward refreshFiled(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		String filed = request.getParameter("filed");     //原来已上传的附件文件名
		String refiled = request.getParameter("refiled");  //要删除的附件文件名
		
		if(filed != null && filed.length() > 0) {
			/*try {
				//filed = URLDecoder.decode(filed,"utf-8");
				filed = new String(filed.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			filed = filed.replaceAll(refiled + "\\*", "");	
		}
		request.setAttribute("newFileNameStr", filed);
		String uploadType = request.getParameter("uploadType");
		String mappingStr="";
		if(uploadType != null){   //如果是上传图片
			mappingStr = "uploadImage";
		}else{  //如果是上传文件
			mappingStr = "uploadFile";
		}
		return mapping.findForward(mappingStr);	    
	}
	
	//上传附件
	public ActionForward uploadMutiFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter out  = response.getWriter();
		String uploadType = request.getParameter("uploadType");
		String mappingStr="";
		if(uploadType != null){   //如果是上传图片
			mappingStr = "uploadImage";
		}else{  //如果是上传文件
			mappingStr = "uploadFile";
		}
		
		StringBuffer newFileNameBuffer = new StringBuffer();  //拼接后的所有文件名字符串
		String filed = request.getParameter("filed");  //获得前面已经上传的的文件名字符串
		/*if(filed != null && filed.length() > 0) {    
			filed = new String(filed.getBytes("ISO-8859-1"), "UTF-8");
		}*/
		String savePath = request.getSession().getServletContext().getRealPath("")+ "\\image\\";
			String[] ExtArr = new String[] { ".gif", ".jpg",".JPG", ".png",".PNG", ".bmp",".BMP",".jpeg",".JPEG", ".rar", ".RAR",".zip",".ZIP", ".doc", ".docx", ".xls", ".xlsx" , ".pdf",".txt"};
			long maxSize = 4194304;  //限制上传文件大小为4M
			String Msg1 = "上传文件大小超过限制。";
			String Msg2 = "上传文件的扩展名不被允许。";
			String Msg = null;
			DiskFileUpload fu = new DiskFileUpload();
			fu.setHeaderEncoding("utf-8");
			fu.setSizeMax(maxSize); //   
			fu.setSizeThreshold(4096);
			fu.setRepositoryPath("d:/");
			
			String fileLarge ="提示信息：您上传的文件超过"+maxSize/(1024*1024)+"M";  //文件过大错误提示信息
		try {
			List fileItems = fu.parseRequest(request);
			Iterator iter = fileItems.iterator();
			//HashMap storeMap = new HashMap();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				String filePath = item.getName();
				long fileSize = item.getSize();
				/*if(filePath == null ||filePath.length() < 1 || 
				   !"uploadFile".equalsIgnoreCase(item.getFieldName()) || item.isFormField()){
				   continue;
				}*/
				if(filePath == null||"".equals(filePath)) {
					   continue;	
					}
				if(fileSize > maxSize) {
				   Msg = Msg1;
				   request.setAttribute("newFileNameStr", filed);
				   request.setAttribute("erroMsg", fileLarge);
				   return mapping.findForward(mappingStr);	     
				}
				String fileStu = filePath.substring(filePath.lastIndexOf("."));
				boolean isAllow = false;
				for (int m = 0; m < ExtArr.length; m++) {
					if (ExtArr[m].equalsIgnoreCase(fileStu)) {
						isAllow = true;
						break;
					}
				}
				if (isAllow == false) {
					Msg = Msg2;
					request.setAttribute("erroMsg","请上传文件后缀是rar或者zip或者图片、WORD、TXT、EXCEL、PDF格式的文件");
					return mapping.findForward(mappingStr);	 
				}
				int startNum = filePath.lastIndexOf("\\");
				String fileName = filePath.substring(startNum + 1, filePath.length());  //原来的文件名
				//String[] fileNameArr = fileName.split("\\.");  //注意split .正则表达式中的用法
				int end = fileName.lastIndexOf(".");
				String newFileName = fileName.substring(0, end).concat("_").concat(String.valueOf(new Date().getTime())).concat(".").concat(fileName.substring(end+1));  //加了时间戳后的新文件名
				
				newFileNameBuffer.append(newFileName).append("*");  //拼接后的所有文件名字符串
				
				File dirFile = new File(savePath);
				if (!(dirFile.exists()) && !(dirFile.isDirectory())) {
					boolean creadok = dirFile.mkdirs();
					if (creadok) {
						//System.out.println("ok:创建文件夹成功！ ");
					} else {
						//System.out.println("err:创建文件夹失败！ ");
					}
				}
				java.io.File f = new java.io.File(savePath + newFileName);
				item.write(f);
			}
			
			if(filed != null && filed.length() > 0) {    
				filed = filed.concat(newFileNameBuffer.toString());	  //将前面已经上传的文件名拼接这次上传的文件名
				request.setAttribute("newFileNameStr", filed);
			}else{
				request.setAttribute("newFileNameStr", newFileNameBuffer.toString());
			}
		}
		catch(Exception e) {
			//e.printStackTrace();
			request.setAttribute("newFileNameStr", filed);
			request.setAttribute("erroMsg", fileLarge);
			return mapping.findForward(mappingStr);	
		}
		
		return mapping.findForward(mappingStr);	     
	}
	
	
	
/*
 * 以前写的 
 */
	public ActionForward uploadMethod(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String contextPath = request.getContextPath();
			//截取当前访问路径 例：http://localhost:8080/
			String requestUrI = request.getRequestURI();
			String requestUrl = request.getRequestURL().toString().replace(
					requestUrI, "");
	
			String SavePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ "image\\";
			String SaveUrl = contextPath + "/image/";
			System.out.println("li==="+SaveUrl);
			System.out.println("c--->" + contextPath + "----->" + SavePath
					+ "---->" + SaveUrl);
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
			List fileItems=null;
			try {
				fileItems = fu.parseRequest(request);
			} catch (FileUploadException e1) {
				e1.printStackTrace();
			}
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				String filePathJs = item.getName();
				System.out.println("liugang==="+filePathJs);
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
					System.out.println(FilePath + "  " + SavePath + "   "
							+ FileNameAuto + "  " + ext);
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
					try {
						item.write(f);
					} catch (Exception e) {
						e.printStackTrace();
					}
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
						try {
							FileTitle2 = URLDecoder.decode(fieldValue, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					} else if ("imgAlign".equals(fieldName)) {
						FileAlign = fieldValue;
					} else if ("imgHspace".equals(fieldName)) {
						FileHspace = fieldValue;
					} else if ("imgVspace".equals(fieldName)) {
						FileVspace = fieldValue;
					}
				}
			}
			Map mp = new HashMap();
			mp.put("fileNameJs", fileNameJs);
			String data = JSONArray.fromObject(mp).toString();
			out.println(data);

		}catch(Exception e){
			throw new RuntimeException("执行上传文件出错", e);
		}finally{
			out.close();
		}
		return null;
	}
}
