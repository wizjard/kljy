package com.juncsoft.KLJY.biomedical.member.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.biomedical.member.dao.MemberDao;
import com.juncsoft.KLJY.biomedical.member.entity.HealthRecord;
import com.juncsoft.KLJY.biomedical.member.entity.MemberMsg;
import com.juncsoft.KLJY.biomedical.member.entity.MemberUploadFile;
import com.juncsoft.KLJY.biomedical.member.impl.MemberDaoImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class MemberAction extends DispatchAction {
	private MemberDao dao = new MemberDaoImpl();

	/**
	 * 会员用户登录方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			MemberInfo mem = dao.login(account, password);
			if (mem == null) {
				res.setSuccess(false);
				res.setMsg("登录失败。");
			} else {
				res.setSuccess(true);
				request.getSession().setAttribute("MemberInfo", mem);
//				response.sendRedirect(request.getContextPath()
//						+ "/module/biomedical/member/main.html");
			}
		} catch (Exception e) {
			res.setMsg("登录失败。<服务器逻辑错误>");
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	
	/*
	 * 会员找回用户名和密码
	 */
	public ActionForward findUserNameAndPwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		res.setSuccess(true);
		String idNo = request.getParameter("idNo").trim();
		String patientName = request.getParameter("patientName").trim();
		MemberInfo mem = dao.findUserNameAndPwd(idNo, patientName);
		if (mem == null) {
			res.setSuccess(false);
		} else {
			res.setData(mem.getAccount().concat(",").concat(mem.getPassword()));
		}
		out.println(JSONObject.fromObject(res).toString());
		out.close();
		return null;
	}
	
	/**
	 * 查询住院病历
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInHspCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			out.println(JSONArray.fromObject(
					dao.getInHspCase(mem.getPatient()), JSONValueProcessor
							.formatDate("yyyy-MM-dd HH:mm")));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 查询我的健康记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMyHealthRecords(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			JSONObject object = new JSONObject();
			object.put("root", JSONArray.fromObject(dao.getHealthRecords(start,
					limit, Restrictions.eq("mem", mem)), JSONValueProcessor
					.cycleExcludel(new String[] { "mem" }, "yyyy-MM-dd")));
			object.put("total", dao.getHealthRecordTotal(Restrictions.eq("mem",
					mem)));
			out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}


	/**
	 * 保存及更新我的健康记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveMyHealthRecords(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			HealthRecord record = (HealthRecord) JSONObject.toBean(JSONObject
					.fromObject(request.getParameter("data")),
					HealthRecord.class);
			record.setMem(mem);
			if (record.getId() != null && record.getId() > 0) {
				dao.updateHealthRecord(record);
			} else {
				record.setId(dao.saveHealthRecord(record));
			}
			res.setSuccess(true);
			res.setData(record.getId().toString());
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}

	/**
	 * 删除我的健康记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteMyHealthRecords(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			dao.deleteHealthRecord(dao.getHealthRecord(id));
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}

	/**
	 * 获取我的健康记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMyHealthRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(
					dao.getHealthRecord(id),
					JSONValueProcessor.cycleExcludel(new String[] { "mem" },
							"yyyy-MM-dd")).toString());
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}

	/**
	 * 网上咨询医生列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMemberMsgs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			JSONObject object = new JSONObject();
			object.put("total", dao.getMemberMsgsTotal(Restrictions.eq("mem",
					mem)));
			object
					.put("root", JSONArray
							.fromObject(dao.getMemberMsgs(start, limit,
									Restrictions.eq("mem", mem)),
									JSONValueProcessor.cycleExcludel(
											new String[] { "mem" },
											"yyyy-MM-dd HH:mm")));
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 提交网上咨询医生
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveMemberMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			MemberMsg msg = new MemberMsg();
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			msg.setMem(mem);
			String ask = request.getParameter("ask");
			msg.setAsk(ask);
			String hrId = request.getParameter("healthRecordId");
			if (hrId != null && hrId.length() > 0) {
				Long healthRecordId = Long.parseLong(hrId);
				msg.setHr(dao.getHealthRecord(healthRecordId));
			}
			msg.setWard(request.getParameter("ward"));
			dao.saveOrUpdateMemberMsg(msg);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	/**
	 * 查询我的上传文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMyUploadFiles(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			JSONObject object = new JSONObject();
			object.put("root", JSONArray.fromObject(dao.getMemberUploadFiles(
					start, limit, Restrictions.eq("mem", mem)),
					JSONValueProcessor.cycleExcludel(new String[] { "mem" },
							"yyyy-MM-dd HH:mm")));
			object.put("total", dao.getMemberUploadFilesTotal(Restrictions.eq(
					"mem", mem)));
			out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public ActionForward uploadFiles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		try {
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			String rootPath = request.getRealPath("/");
			String tempPath = rootPath
					+ "\\module\\biomedical\\member\\resources\\temp\\";
			String uploadPath = rootPath
					+ "\\module\\biomedical\\member\\resources\\upload\\";
			DiskFileUpload fu = new DiskFileUpload();
			fu.setSizeThreshold(1024 * 5);
			fu.setRepositoryPath(tempPath);
			List fileItems = fu.parseRequest(request);
			Iterator it = fileItems.iterator();
			while (it.hasNext()) {
				FileItem file = (FileItem) it.next();
				if (!file.isFormField()) {
					String name = new String(file.getName().getBytes("GBK"),
							"utf-8");
					long size = file.getSize();
					if (name == null || name.length() == 0 || size == 0)
						continue;
					MemberUploadFile upload = new MemberUploadFile();
					upload.setFileName(name);
					String sizes = "";
					if (size < 1024) {
						sizes = size + "b";
					} else if (size >= 1024 && size < 1024 * 1024) {
						sizes = size / 1024.00 + "K";
					} else if (size >= 1024 * 1024) {
						sizes = size / (1024.00 * 1024) + "M";
					}
					upload.setFileSize(sizes);
					upload.setMem(mem);
					String saveName = mem.getMemberNo()
							+ new Date().getTime()
							+ name.substring(name.lastIndexOf("."), name
									.length());
					upload.setFileSaveName(saveName);
					file.write(new File(uploadPath + saveName));
					dao.saveMemberUploadFile(upload);
					out.write("{success:true}");
				}
			}
		} catch (Exception e) {
			out.write("{success:false}");
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 备注文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadFileMemo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			String memo = request.getParameter("memo");
			MemberUploadFile file = dao.getMemberUploadFile(id);
			file.setMemo(memo);
			dao.updateMemberUploadFile(file);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}

	/**
	 * 删除文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward deleteUploadFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			MemberUploadFile file = dao.getMemberUploadFile(id);
			String rootPath = request.getRealPath("/");
			String uploadPath = rootPath
					+ "\\module\\biomedical\\member\\resources\\upload\\";
			File fil = new File(uploadPath + file.getFileSaveName());
			if (fil.exists()) {
				fil.delete();
			}
			dao.deleteMemberUploadFile(file);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;
	}

	/**
	 * 获取我的随访通知
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMyNotices(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			out.println(dao.getMyNotices(mem, start, limit).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	/**
	 * 会员登录获取相应信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLoginMemberInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			out.println(dao.getLoginMemberInfo(request).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 退出登录并销毁session
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			try {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.removeAttribute("MemberInfo");
					session.invalidate();
				}
			} catch (Exception e) {
			}
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	/**
	 * 修改密码-验证用户旧密码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validOldPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String memberNo = request.getParameter("memberNo");
			String password = request.getParameter("password");
			res.setData(dao.validOldPassword(memberNo, password) + "");
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	/**
	 * 修改密码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String memberNo = request.getParameter("memberNo");
			String password = request.getParameter("password");
			dao.changePassword(memberNo, password);
			res.setSuccess(true);
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	/**
	 * 传递病人Pid直接查询住院病历
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInHspCasePid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long pid = Long.parseLong(request.getParameter("pid"));
			out.println(JSONArray.fromObject(
					dao.getInHspCasePid(pid), JSONValueProcessor
							.formatDate("yyyy-MM-dd HH:mm")));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	/*
	 * 会员找回用户名和密码
	 */
//	public ActionForward findUserNameAndPwd(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		PrintWriter out = response.getWriter();
//		Response res = new Response();
//		res.setSuccess(true);
//		String idNo = request.getParameter("idNo").trim();
//		String patientName = request.getParameter("patientName").trim();
//		MemberInfo mem = dao.findUserNameAndPwd(idNo, patientName);
//		if (mem == null) {
//			res.setSuccess(false);
//		} else {
//			res.setData(mem.getAccount().concat(",").concat(mem.getPassword()));
//		}
//		out.println(JSONObject.fromObject(res).toString());
//		out.close();
//		return null;
//	}
}
