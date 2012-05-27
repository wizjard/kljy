package com.juncsoft.KLJY.system.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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


public class DownloadAction extends DispatchAction {
	public ActionForward downloadFile(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// response.reset();//可以加也可以不加
	    // response.setContentType("application/x-download");
		String fileNotExistMsg ="您要下载的文件不存在";  //要下载的文件不存在提示信息
		

	     String projectPath = request.getSession().getServletContext().getRealPath("");
	     request.setCharacterEncoding("UTF-8");
	    
	     String fileName = request.getParameter("fileName");
	     String filedownload = projectPath+"\\image\\"+fileName;
	     int _index = filedownload.lastIndexOf(".");
	     String suffix = _index > -1 ? filedownload.substring(_index + 1, filedownload.length()) : "";
	     
	     File downFile = new File(filedownload);
	     
	     if(!downFile.exists()) {
	    	 PrintWriter out  = response.getWriter();
	    	 out.print("<script type='text/javascript'>alert('"+fileNotExistMsg+"')</script>");
	    	return null;
	     }
	     //response.reset();
	     response.setHeader("extension", suffix);
         response.setContentType("application/x-download");	    
         response.setContentLength(Integer.parseInt(downFile.length()+""));
	    // response.setHeader("Content-Disposition","attachment;filename=" + fileName);
	  // if specify attachment, browser prompts for open/save
         response.setHeader("Content-Disposition","attachment; filename=\"" + URLEncoder.encode(fileName,"UTF-8"));
	     
	     OutputStream outp = null;
	     FileInputStream in = null;
	     try
	     {
	         outp = response.getOutputStream();
	         in = new FileInputStream(downFile);

	         byte[] b = new byte[4096];
	         int i = 0;

	         while((i = in.read(b)) > 0)
	         {
	             outp.write(b, 0, i);
	         }
	         outp.flush();
	         //outp
	     }
	     catch(Exception e)
	     {
	         e.printStackTrace();
	     }
	     finally
	     {
	         if(in != null)
	         {
	             in.close();
	          //   in = null;
	         }
	         if(outp != null)
	         {
	             outp.close();
	             //outp = null;
	         }
	     }

		return null;
	}
}
