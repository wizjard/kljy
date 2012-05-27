package com.juncsoft.KLJY.membergrounp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.membergrounp.cor.AdminCorRequest;
import com.juncsoft.KLJY.membergrounp.cor.ConcreteHandler;
import com.juncsoft.KLJY.membergrounp.cor.CorRequest;
import com.juncsoft.KLJY.membergrounp.cor.DoctorCorRequest;
import com.juncsoft.KLJY.membergrounp.cor.MemberCorRequest;
import com.juncsoft.KLJY.membergrounp.dao.DepartmentGrounpDao;
import com.juncsoft.KLJY.membergrounp.entity.DepartmentGrounp;
import com.juncsoft.KLJY.membergrounp.entity.MemberApplicationRecord;
import com.juncsoft.KLJY.membergrounp.impl.DepartmentGrounpImpl;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

/**
 * 会员责任小组
 * 
 * @author liugang
 * 
 */
public class DepartmentGrounpAction extends DispatchAction {

	private DepartmentGrounpDao dao = new DepartmentGrounpImpl();

	/**
	 * 根据科室ID查找科室下的责任小组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findDepartmentGrounpByDeptId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String deptId = request.getParameter("deptCode");
			String data = JSONArray.fromObject(
					dao.findDepartmentGrounpByDeptId(deptId)).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 获取当前管理员即科主任所在科室下的所有小组及成员分布
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findCurrentDoctorDeptmentById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Long grounpId = Long.parseLong(request.getParameter("grounpId"));
			String data = JSONArray.fromObject(
					dao.findCurrentDoctorDeptmentById(grounpId)).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 获取当前管理员即科主任所在科室下的所有小组及成员分布
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findCurrentGrounpList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String data = JSONArray.fromObject(
					dao.findCurrentGrounpList(doctorId)).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 保存模块
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveDepartmentGrounp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONArray array = JSONArray
					.fromObject(request.getParameter("data"));
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			for (Object object : array) {
				DepartmentGrounp department = (DepartmentGrounp) JSONObject
						.toBean(JSONObject.fromObject(object),
								DepartmentGrounp.class);
				if (department.getId() == -1) {
					dao.saveDepartmentGrounp(doctorId, department);
				} else {
					dao.updateDepartmentGrounp(department);
				}
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
	 * 删除模块
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteGrounpUpdateOther(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			String deptCode = request.getParameter("deptCode");
			dao.deleteGrounpUpdateOther(id, deptCode);
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
	 * 获取子模块列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	// public ActionForward getChildModules(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// PrintWriter out = response.getWriter();
	// try {
	// String parentModuleCode = request.getParameter("ModuleCode");
	// out.println(JSONArray.fromObject(
	// moduleDao.getChildModules(parentModuleCode)).toString());
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// out.close();
	// }
	// return null;
	// }
	/**
	 * 申请转科内容记录
	 */
	public ActionForward saveApplicationRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			MemberApplicationRecord memApp = (MemberApplicationRecord) JSONObject
					.toBean(JSONObject.fromObject(data),
							MemberApplicationRecord.class);
			memApp.setApplicationStateContent("申请中");
			memApp.setResult(0);// 转科结果初始化进行中。。。。
			memApp.setAutoFlag(0);// 会员发起，初始化为0，仅责任科室可以看到
			memApp.setCurrentFlag(0);// 会员发起，初始化为0，表示责任科室意见
			memApp.setApplicationFlag(0);// 会员发起，初始化为0，仅申请科室可以看到
			dao.saveApplicationRecord(memApp);
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
	
	public ActionForward cacheApplicationRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			MemberApplicationRecord memApp = (MemberApplicationRecord)JSONObject.toBean(JSONObject.fromObject(data),
					MemberApplicationRecord.class);
			MemberInfo memberInfo = (MemberInfo)request.getSession().getAttribute("MemberInfo");
			User user = (User)request.getSession().getAttribute("user");
			ConcreteHandler concrete = new ConcreteHandler();
			CorRequest correquest = null;
			if(memberInfo != null) {
				correquest = new MemberCorRequest();
			}
			else if(user!=null) {
		    	Pattern pattern = Pattern.compile("[0-9]*");
		    	Matcher isNum = pattern.matcher(user.getRole());
		    	Long id;
		    	if(request.getParameter("id")!=null) {
		    		id = Long.parseLong(request.getParameter("id"));
		    		memApp.setId(id);
		    	}
		    	int state ;
		    	if(request.getParameter("state")!=null) {
		    		state = Integer.parseInt(request.getParameter("state"));
		    		memApp.setFlag(state);
		    	}
				//JSONObject json = JSONObject.fromObject(request
				//		.getParameter("data"));
				//String because = json.get("applicationBacuse").toString();
				//memApp.setCurrentDeptBecause(because);
				
		    	if(isNum.matches()) {
		    		correquest = new DoctorCorRequest();
				} else {
					correquest = new AdminCorRequest();
				}
			} else {
				System.out.println("缺少登录状态,要重新登录");
				return null;
			}
			concrete.handlerRequest(correquest, memApp);
			res.setSuccess(true);
		} catch(Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}

	public ActionForward findListApplicationRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			// Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String deptCode = request.getParameter("deptCode");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			int flag = Integer.parseInt(request.getParameter("flag"));
			out.println(dao.findListApplicationRecord(deptCode, flag, start,
					limit).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	public ActionForward findDeptListApplicationRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			// Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			String deptCode = request.getParameter("deptCode");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			int flag = Integer.parseInt(request.getParameter("flag"));
			String[] depts = deptCode.split(",");
			out.println(dao.findListApplicationRecord(depts, flag, start,
					limit).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	//用户转科转组
	public ActionForward findUserListApplicationRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			// Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			MemberInfo mem = (MemberInfo)request.getSession().getAttribute("MemberInfo");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			int flag = Integer.parseInt(request.getParameter("flag"));
			out.println(dao.findListApplicationRecord(mem.getDeptCode(), flag, start,
					limit).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	//用户转科记录列表
	public ActionForward findUserHistoryApplicationRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			out.println(dao.findUserHistoryApplicationRecord(patientId).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	/**
	 * 更新申请转科内容记录
	 */
	public ActionForward updateApplicationRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			JSONObject json = JSONObject.fromObject(request
					.getParameter("data"));
			String because = json.get("applicationBacuse").toString();
			Long grouId = null;
			if (request.getParameter("deptcodeGrounp") != null) {
				String grounpId = request.getParameter("deptcodeGrounp");
				if (!"".equals(grounpId)) {
					grouId = Long.parseLong(grounpId);
				}
			}
			int state = Integer.parseInt(request.getParameter("state"));
			dao.updateApplicationRecord(id, because, state, grouId);
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
	 * 转科转组信息查看
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findOneMessageById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(dao.findOneMessageById(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString());
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
	 * 填充是转科还是转组状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward findCurrentState(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			res.setData(JSONObject.fromObject(dao.findCurrentState(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString());
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

	public ActionForward findAllDeptDoctorList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String deptCode = request.getParameter("deptCode");
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			out.println(dao.findAllDeptDoctorList(deptCode, start, limit)
					.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward findMemberDeptOrGrounpRecordByPatientId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			String data = JSONArray.fromObject(
					dao.findMemberDeptOrGrounpRecordByPatientId(patientId),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	// liugang 2011-08-08 start
	/**
	 * 新增责任小组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addNewGrounp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String data = request.getParameter("data");
			JSONObject json = JSONObject.fromObject(data);
			dao.addNewGrounp(json.get("deptcode").toString(), json.get(
					"grounpName").toString());
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

	// liugang 2011-08-08 end

	// 2011-08-22 start 新增责任小组添加会员
	public ActionForward findDoctorInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String data = request.getParameter("data");
			JSONObject json = JSONObject.fromObject(data);
			res.setData(JSONObject.fromObject(
					dao.findUserByHisUserId(json.get("doctorId").toString()))
					.toString());
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

	public ActionForward findOneUserInsertOneGrounp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String data = request.getParameter("data");
			JSONObject json = JSONObject.fromObject(data);
			Long doctorId = Long.parseLong(json.get("doctorId").toString());
			Long grounpId = Long.parseLong(json.get("grounpId").toString());
			dao.findOneUserInsertOneGrounp(grounpId, doctorId);
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

	public ActionForward deleteMemberDoctor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			dao.deleteMemberDoctor(doctorId);
			res.setSuccess(true);
		} catch (IOException e) {
			res.setSuccess(false);
			e.printStackTrace();
		}finally{
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	

	public ActionForward checkUserIsOrNotInOtherGrounp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String data = request.getParameter("data");
			JSONObject json = JSONObject.fromObject(data);
			res.setData(JSONObject.fromObject(
					dao.checkUserIsOrNotInOtherGrounp(Long.parseLong(json.get("doctorId").toString())))
					.toString());
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
	

	public ActionForward checkUserInOneGrounpCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long grounpId = Long.parseLong(request.getParameter("grounpId"));
			res.setSuccess(dao.checkUserInOneGrounpCount(grounpId));
		} catch (Exception e) {
			res.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
	
	public ActionForward deleteGrounp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long grounpId = Long.parseLong(request.getParameter("grounpId"));
			dao.deleteGrounp(grounpId);
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
}
