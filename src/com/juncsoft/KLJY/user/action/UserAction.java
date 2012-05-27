package com.juncsoft.KLJY.user.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.config.JunDBPool;
import com.juncsoft.KLJY.outoremergency.dao.OutOrMergencyDoctorDao;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyDoctor;
import com.juncsoft.KLJY.outoremergency.impl.OutOrMergencyDoctorDaoImpl;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.user.dao.UserDao;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.MD5;
import com.juncsoft.KLJY.util.Response;

public class UserAction extends DispatchAction {
	private JunDBPool  pool = new JunDBPool();
	private UserDao dao = (UserDao) DaoFactory.InstanceImplement(UserDao.class);
	
	//liugang 登录医生信息校验
	private OutOrMergencyDoctorDao doctorDao = new OutOrMergencyDoctorDaoImpl();
	
	/**
	 * 检查用户账号的可用性
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkAccountIsUsed(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String account = request.getParameter("account");
			res.setData(dao.checkAccountIsUsed(account) + "");
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
	 * 用户注册
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward register(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			String authNo = json.getString("authNo");
			String sessionAuthNo = (String) request.getSession().getAttribute(
					"AuthCode");
			if (sessionAuthNo == null) {
				res.setMsg("注册失败。<验证码超时，请重新获取验证码>");
				res.setSuccess(false);
			} else if (authNo.equalsIgnoreCase(sessionAuthNo)) {
				User user = (User) JSONObject.toBean(json, User.class);
				user.setValid(0);
				Date date = new Date();
				user.setCreateDate(date);
				user.setModifyDate(date);
				user.setPassword(new MD5().getMD5ofStr(json
						.getString("password")));
				user = dao.register(user);
				res.setMsg("恭喜您，注册成功。");
				res.setSuccess(true);
			} else {
				res.setMsg("注册失败。<验证码输入有误>");
				res.setSuccess(false);
			}
		} catch (Exception e) {
			res.setMsg("注册失败。<服务器逻辑错误>");
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	/**
	 * 用户登录
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
		String account = request.getParameter("account");
		try {
			String password = request.getParameter("password");
			String firstChar = account.substring(0, 1);
			byte[] bytes = firstChar.getBytes();
			int i = bytes.length;// i为字节长度
			int j = firstChar.length();// j为
			// if(Pattern.compile("[0-9]*").matcher(firstChar).matches()){
			// System.out.println("数字");
			// }else if(i==j){
			// System.out.println("英文");
			// }else{
			// System.out.println("中文");
			// }
			User user = null;// 原来的系统
			if (Pattern.compile("[0-9]*").matcher(firstChar).matches()) {
				System.out.println("login his ----------- login his --------------");
				user = dao.loginHis(res, account, password);
				request.getSession().setAttribute("user", user);
			} else {
				System.out.println("login  ----------- login --------------");
				user = dao.login(res, account, password);
				request.getSession().setAttribute("user", user);
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

	/**
	 * HIS用户登录
	 */
	public ActionForward login_his(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String DoctorID = request.getParameter("DoctorID");
			String WardCode = request.getParameter("WardCode");
			String PatientID = request.getParameter("PatientID");
			String Admseqno = request.getParameter("Admseqno");
			User user = null;
			Patient patient = null;
			HisService service = new HisService();
			if (PatientID == null || PatientID.length() == 0
					|| PatientID.equalsIgnoreCase("null")) {
				user = service.login_user(DoctorID, WardCode);
			} else {
				Map<String, Object> result = service.login(DoctorID, WardCode,
						PatientID, Admseqno);
				user = (User) result.get("u");
				patient = (Patient) result.get("p");
			}
			String url = "";
			if (user == null) {
				response.sendRedirect(request.getContextPath()
						+ "/PUBLIC/CommonPage/login_fail.jsp");
			} else {
				request.getSession().setAttribute("user", user);
				if (patient == null) {
					url = "?user=" + user.getId();
				} else {
					url = "?user=" + user.getId() + "&p=" + patient.getId();
				}
				response.sendRedirect(request.getContextPath()
						+ "/mainface/main.html" + url);
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath()
					+ "/PUBLIC/CommonPage/login_fail.jsp");
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 获取登录用户信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLoginUserInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			out.println(dao.getLoginUserInfo(request).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward getLoginUserInfoByAccount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String account = request.getParameter("account");
			out.println(dao.getLoginUserInfoByAccount(account).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	/**
	 * 获取登录用户菜单信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMyModules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			User user = (User) request.getSession().getAttribute("user");
			if (user == null) {
				res.setSuccess(false);
				res.setMsg("未登录或登录超时...");
			} else if (user.getRole() == null || user.getRole().length() == 0) {
				res.setSuccess(false);
				res.setMsg("未给用户分配相应的角色...");
			} else {
				res.setData(dao.getMyModules(user).toString());
				res.setSuccess(true);
			}
		} catch (Exception e) {
			res.setSuccess(false);
			res.setMsg("用户菜单初始化失败...");
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
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
					session.removeAttribute("user");
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
			Long id = Long.parseLong(request.getParameter("id"));
			String password = request.getParameter("password");
			res.setData(dao.validOldPassword(id, password) + "");
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
	public ActionForward validHisOldPassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			String password = request.getParameter("password");
			res.setData(dao.validHisOldPassword(id, password) + "");
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
			Long id = Long.parseLong(request.getParameter("id"));
			String password = request.getParameter("password");
			dao.changePassword(id, password);
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
	 * 2011-12-12 修改his密码
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeHisPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			String password = request.getParameter("password");
			dao.changeHisPassword(id, password);
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
	 * 用户登录提供给全院医生查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loginSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		String account = request.getParameter("account");
		String patientId = request.getParameter("PatientID");
		try {
			String password = request.getParameter("password");
			User user = dao.login(res, account, password);
			request.getSession().setAttribute("user", user);
		} catch (Exception e) {
			res.setMsg("登录失败。<服务器逻辑错误>");
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			if ("looksearch".equals(account)) {
				response.sendRedirect(request.getContextPath()
						+ "/mainface/mainSearch.html?id=" + patientId);
				out.close();
			}
			return null;
		}
	}

	// 预约挂号根据科室查找医生列表
	public ActionForward findUserNameByDeptcode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String deptCode = request.getParameter("deptCode");
			String data = JSONArray.fromObject(
					dao.findUserNameByDeptcode(deptCode),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		return null;
	}
}
