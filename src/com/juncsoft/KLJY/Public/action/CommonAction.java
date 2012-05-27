package com.juncsoft.KLJY.Public.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.Public.service.CommonService;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DictionaryUtil;

public class CommonAction extends DispatchAction {
	private CommonService service = new CommonService();

	/**
	 * 获取单表字典候选列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward GetIndependentDictionaryList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String code = request.getParameter("code");
			out.println(DictionaryUtil.getIndependentDictionaryList(code));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 获取单表字典对应值
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward GetIndependentDictionaryText(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String code = request.getParameter("code");
			String value = request.getParameter("value");
			if (value == null || value.length() == 0) {
				out.println("");
			} else {
				out.println(DictionaryUtil.getIndependentDictionaryText(code,
						value));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 获取登录用户所在机构的所有单位，主要用于本院科室的选择
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward QueryMyBelongs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			User user = (User) request.getSession().getAttribute("user");
			if (user != null) {
				String belong = user.getBelong();
				out.println(service.queryMyBelongs(belong));
			} else {
				out.println("[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 获取登录用户所在单位的医生，主要用于本院医生字典的选择
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward QueryMyDoctors(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			User user = (User) request.getSession().getAttribute("user");
			if (user != null) {
				String belong = user.getBelong();
				out.println(service.queryMyDoctors(belong));
			} else {
				out.println("[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 获取诊断字典数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward QueryIlls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String keyword = request.getParameter("keyword");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			out.println(service.queryIlls(keyword, start, limit).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 根据页面代码获取整个页面的公共字典
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward GetPageDictionary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String pageCode = request.getParameter("code");
			out.println(service.getPageDictionary(pageCode).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward GetPageDictionaryNew(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String pageCode = request.getParameter("code");
			out.println(service.getPageDictionaryNew(pageCode).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 根据页面代码及字段名称获取候选列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward GetDictionarys(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String pageCode = request.getParameter("pcode");
			String fldCode = request.getParameter("fcode");
			out.println(service.getDictionarys(pageCode, fldCode).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 自定义查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward GetSelfQueryList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String sql = request.getParameter("sql");
			out.println(service.getSelfQueryList(sql).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 获取公用表头信息
	 */
	public ActionForward GetHeaderInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long pid = null;
			Long cid = null;
			try {
				pid = Long.parseLong(request.getParameter("pid"));
			} catch (Exception e) {
			}
			try {
				cid = Long.parseLong(request.getParameter("cid"));
			} catch (Exception e) {
			}
			out.println(service.GetHeaderInfo(pid, cid));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	/**
	 * 获取公用表头信息(门诊)
	 */
	public ActionForward GetHeaderInfomenzhen(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long pid = null;
			Long cid = null;
			try {
				pid = Long.parseLong(request.getParameter("pid"));
			} catch (Exception e) {
			}
			try {
				cid = Long.parseLong(request.getParameter("cid"));
			} catch (Exception e) {
			}
			out.println(service.getHeaderInfomenzhen(pid, cid));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	/**
	 * 根据病人id获取HIS编码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPatientHisCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long patientId = Long.parseLong(request.getParameter("id"));
			out.println(service.getPatientHisCode(patientId));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	/**
	 * 获取登录用户所在单位的医生，主要用于本院医生字典的选择
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward QueryMyDoctorsNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			User user = (User) request.getSession().getAttribute("user");
			if (user != null) {
				StringBuilder sb1 = new StringBuilder();
				if (user.getUnkown1() != null) {
					sb1.append(user.getUnkown1().trim());
				}
				if (user.getDeptcodename() != null) {
					sb1.append("','" + user.getDeptcodename().trim());
				}
				if (user.getDrdeptname1() != null) {
					sb1.append("','" + user.getDrdeptname1().trim());
				}
				if (user.getDrdeptname2() != null) {
					sb1.append("','" + user.getDrdeptname2().trim());
				}
				if (user.getDrdeptname3() != null) {
					sb1.append("','" + user.getDrdeptname3().trim());
				}
				if (user.getDrdeptname4() != null) {
					sb1.append("','" + user.getDrdeptname4().trim());
				}
				out.println(service.queryMyDoctorsNew(sb1.toString()));
			} else {
				out.println("[]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
}
