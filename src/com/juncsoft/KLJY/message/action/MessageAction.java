package com.juncsoft.KLJY.message.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.List;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.juncsoft.KLJY.message.dao.MessageDao;
import com.juncsoft.KLJY.message.entity.Message;
import com.juncsoft.KLJY.message.impl.MessageDaoImpl;
import com.juncsoft.KLJY.user.entity.User;

public class MessageAction extends DispatchAction {
	MessageDao mDao = new MessageDaoImpl();
	
	
	/*
	 * 医生获得医生所在科室和小组下的会员的所有短信
	 */
	public ActionForward getMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String search = request.getParameter("search");
			String flag = request.getParameter("flag");
			String orderBy = request.getParameter("orderBy");
			String descOrasc = request.getParameter("descOrasc");
			out.println(mDao.getMessageByDid(start,limit,doctorId,search,flag,orderBy,descOrasc));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	/*
	 * 医生获得医生所在科室和小组下的会员的所有短信 导出成excel用
	 */
	public ActionForward creatExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
			     + new String("短信列表.xls".getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String search = request.getParameter("search");
			if(search != null)
				search = URLDecoder.decode(search,"UTF-8"); 
			
			String flag = request.getParameter("flag");
			String orderBy = request.getParameter("orderBy");
			String descOrasc = request.getParameter("descOrasc");
			List<Message> list = mDao.getMessageByDid(doctorId,search,flag,orderBy,descOrasc);

			bis = new BufferedInputStream(this.getInputStream(list));
		    bos = new BufferedOutputStream(out);

		    byte[] buff = new byte[2048];
		    int bytesRead;

		    // Simple read/write loop.
		    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
		    	bos.write(buff, 0, bytesRead);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//out.close();
			if (bis != null)
			     bis.close();
			if (bos != null)
			     bos.close();
		}
		return null;
	}
	
	
	/*
	 * 管理员获得管理员所在科室会员的所有短信
	 */
	public ActionForward getMessageManage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String deptCode = request.getParameter("deptCode"); //管理员登录后所在部门编号
			String search = request.getParameter("search");
			String flag = request.getParameter("flag");
			String orderBy = request.getParameter("orderBy");
			String descOrasc = request.getParameter("descOrasc");
			out.println(mDao.getMessageByManageDecode(start,limit,deptCode,search,flag,orderBy,descOrasc));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/*
	 * 管理员获得管理员所在科室会员的所有短信  导出excel用
	 */
	public ActionForward creatExcelManage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
			     + new String("短信列表.xls".getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			String ids = request.getParameter("ids");  //如果前台页面多选了
			String deptCode = request.getParameter("deptCode");
			String search = request.getParameter("search");
			if(search != null)
				search = URLDecoder.decode(search,"UTF-8"); 
			
			String flag = request.getParameter("flag");
			String orderBy = request.getParameter("orderBy");
			String descOrasc = request.getParameter("descOrasc");
			List<Message> list = null;
			if(ids!=null&&ids.length()>0){ //如果前台页面多选了记录
				list = mDao.getMessageByIds(ids);
			}else{
				list = mDao.getMessageByManageDecode(deptCode, search, flag, orderBy, descOrasc);
			}
			bis = new BufferedInputStream(this.getInputStream(list));
		    bos = new BufferedOutputStream(out);

		    byte[] buff = new byte[2048];
		    int bytesRead;

		    // Simple read/write loop.
		    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
		    	bos.write(buff, 0, bytesRead);
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//out.close();
			if (bis != null)
			     bis.close();
			if (bos != null)
			     bos.close();
		}
		return null;
	}
	
	private InputStream getInputStream(List<Message> list) {
		   HSSFWorkbook wb = new HSSFWorkbook();
		   HSSFSheet sheet = wb.createSheet("sheet1");

		   HSSFRow row = sheet.createRow(0);

		   HSSFCell cell = row.createCell((short) 0);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("序号");

		   cell = row.createCell((short) 1);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("姓名");

		   cell = row.createCell((short) 2);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("性别");

		   cell = row.createCell((short) 3);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("出生日期");
		   
		   cell = row.createCell((short) 4);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("病案号");
		   
		   cell = row.createCell((short) 5);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("发件人");
		   
		   cell = row.createCell((short) 6);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("发送时间");
		   
		   cell = row.createCell((short) 7);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("短信内容");
		   
		   int i=1;
		   for(Message m : list){
			   row = sheet.createRow(i);
			   cell = row.createCell((short) 0);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   cell.setCellValue(i);

			   cell = row.createCell((short) 1);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   cell.setCellValue(m.getMemberInfo().getPatient().getPatientName());

			   cell = row.createCell((short) 2);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.getMemberInfo().getPatient().getGender().equals("1")){
				   cell.setCellValue("男");
			   }else if(m.getMemberInfo().getPatient().getGender().equals("2")){
				   cell.setCellValue("女");
			   }
			   
			   cell = row.createCell((short) 3);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  //会员生日数据库中是Date格式，要转换成yyyy-MM-dd格式
			   cell.setCellValue(sdf.format(m.getMemberInfo().getPatient().getBirthday()));
			   
			   cell = row.createCell((short) 4);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   cell.setCellValue(m.getMemberInfo().getPatient().getPatientNo());
			   
			   cell = row.createCell((short) 5);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.getUser()==null){
				   cell.setCellValue("系统发送");
			   }else{
				   cell.setCellValue(m.getUser().getName()); 
			   }
			   
			   
			   cell = row.createCell((short) 6);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   cell.setCellValue(m.getMessageDate());
			   
			   cell = row.createCell((short) 7);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   cell.setCellValue(m.getMessageContent());
			   
			   i++;
		   }
	
		   ByteArrayOutputStream os = new ByteArrayOutputStream();

		   try {
		    wb.write(os);
		   } catch (IOException e) {
		    e.printStackTrace();
		   }

		   byte[] content = os.toByteArray();
		   InputStream is = new ByteArrayInputStream(content);
		   return is;
		}
}
