package com.juncsoft.KLJY.patientanddoctoroperator.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.patientanddoctoroperator.dao.PatientConsultingDao;
import com.juncsoft.KLJY.patientanddoctoroperator.entity.PatientConsulting;
import com.juncsoft.KLJY.patientanddoctoroperator.impl.PatientConsultingImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

/**
 * 2011-05-31
 * 
 * @author liugang
 * 
 */
public class PatientConsultingAction extends DispatchAction {

	private PatientConsultingDao dao = new PatientConsultingImpl();

	/**
	 * 医生登录显示病人的咨询问题
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findPatientConsultingByDoctor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String search = request.getParameter("search");
			JSONObject json = null;
			if (search != null) {
				json = JSONObject.fromObject(search);
			}
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			int flagDai = Integer.parseInt(request.getParameter("flagDai"));
			int flagYi = Integer.parseInt(request.getParameter("flagYi"));
			int flagEnd = Integer.parseInt(request.getParameter("flagEnd"));
			out.println(dao.findPatientConsultingByDoctor(doctorId, start,
					limit, flagDai, flagYi,flagEnd, json).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward findPatientConsultingByPatient(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			int start = Integer.parseInt(request.getParameter("start"));
			int limit = Integer.parseInt(request.getParameter("limit"));
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			int flagWeiHui = Integer.parseInt(request
					.getParameter("flagWeiHui"));
			int flagWeiDu = Integer.parseInt(request.getParameter("flagWeiDu"));
			int flagYiDu = Integer.parseInt(request.getParameter("flagYiDu"));
			if (mem != null) {
				out.println(dao
						.findPatientConsultingByPatient(mem.getPatient()
								.getId(), start, limit, flagWeiHui, flagWeiDu,
								flagYiDu));
			} else {
				JSONObject json = null;
				out.println(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 外网查找医院的所有科室
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findAllSYS_HIS_DepartmentHISEntity(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String name = request.getParameter("name");
			Long nameValue = Long.parseLong(request.getParameter("nameValue"));
			String data = JSONArray.fromObject(
					dao.findAllSYS_HIS_DepartmentHISEntity(name, nameValue),// 直接查找所有的科室
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveOrUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String data = request.getParameter("data");
			PatientConsulting patientConsulting = (PatientConsulting) JSONObject
					.toBean(JSONObject.fromObject(data),
							PatientConsulting.class);
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			if (mem == null) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.removeAttribute("MemberInfo");
					session.invalidate();
				}
				res.setSuccess(false);
			} else {
				patientConsulting.setPatientId(mem.getPatient().getId());
				patientConsulting.setDeptCode(mem.getDeptCode());
				patientConsulting.setGrounpId(mem.getGrounpId());
				if (patientConsulting.getId() == null) {
					dao.savePatientConsulting(patientConsulting);
				} else {
					dao.updatePatientConsulting(patientConsulting);
				}
				res.setSuccess(true);
			}
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
	 * 查找某个病人的所有咨询问题
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findAllPatientConsultingByPatientId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			if (mem == null) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.removeAttribute("MemberInfo");
					session.invalidate();
				}
				res.setSuccess(false);
			} else {
				String pcFirst = request.getParameter("pcId");
				String para = request.getParameter("para");
				Long pcId = null;
				if (pcFirst != null) {
					pcId = Long.parseLong(pcFirst);
				}
				Long intoPara = null;
				if (para != null) {
					intoPara = Long.parseLong(para);
				}
				String weekString = request.getParameter("weekFlag");
				String currentDate = request.getParameter("currentDate");
				Date date = null;
				int weekFlag = 0;
				if (weekString != null) {
					weekFlag = Integer.parseInt(weekString);
				}
				if (currentDate != null) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					date = sdf.parse(currentDate);
				}
				String data = JSONArray.fromObject(
						dao.findAllPatientConsultingByPatientId(mem
								.getPatient().getId(), pcId, intoPara, date,
								weekFlag),
						JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
						.toString();
				res.setData(data);
				res.setSuccess(true);
				out.println(JSONObject.fromObject(res));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward patientConsulting_treeNodes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			if (mem == null) {
				JSONArray json = null;
				out.println(json);
			} else {
				String weekString = request.getParameter("weekFlag");
				String currentDate = request.getParameter("currentDate");
				Date date = null;
				int weekFlag = 0;
				if (weekString != null) {
					weekFlag = Integer.parseInt(weekString);
				}
				if (currentDate != null) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					date = sdf.parse(currentDate);
				}
				out.println(dao.patientConsulting_treeNodes(mem.getPatient()
						.getId(), date, weekFlag));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward findNumOne(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			MemberInfo mem = (MemberInfo) request.getSession().getAttribute(
					"MemberInfo");
			if (mem == null) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.removeAttribute("MemberInfo");
					session.invalidate();
				}
				res.setSuccess(false);
			} else {
				Map mp = dao.findNumOne(mem, mem.getPatient().getId());
				mp.put("memberName", mem.getPatient().getPatientName());
				res.setData(JSONObject.fromObject(mp,
						JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
						.toString());
				res.setSuccess(true);
			}

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
	 * 医生回复病人咨询问题
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findAllPatientConsultingBypcID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Long pcId = Long.parseLong(request.getParameter("pcId"));
			String weekString = request.getParameter("weekFlag");
			String currentDate = request.getParameter("currentDate");
			Date date = null;
			int weekFlag = 0;
			if (weekString != null) {
				weekFlag = Integer.parseInt(weekString);
			}
			if (currentDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat();
				date = sdf.parse(currentDate);
			}
			String data = JSONArray.fromObject(
					dao.findAllPatientConsultingByPatientId(null, pcId, null,
							date, weekFlag),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward sendToOtherDeparment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			// Long pcid = Long.parseLong(request.getParameter("pcaId"));
			// String deptCode = request.getParameter("deptcode");
			// Long deptcodeGrounp =
			// Long.parseLong(request.getParameter("deptcodeGrounp"));
			String data = request.getParameter("data");
			JSONObject mp = JSONObject.fromObject(data);
			res.setData(dao.sendToOtherDeparment(Long.parseLong(mp.get("pcaId")
					.toString()), mp.get("deptcode").toString(), Long
					.parseLong(mp.get("deptcodeGrounp").toString()))
					+ "");
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
	 * 咨询医生查看病人的咨询记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward patientConsulting_treeNodesDoctorOnly(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			String weekString = request.getParameter("weekFlag");
			String currentDate = request.getParameter("currentDate");
			Date date = null;
			int weekFlag = 0;
			if (weekString != null) {
				weekFlag = Integer.parseInt(weekString);
			}
			if (currentDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(currentDate);
			}
			out.println(dao.patientConsulting_treeNodes(patientId, date,
					weekFlag));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 咨询医生查看某个会员的所有咨询问题
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findAllPatientConsultingByPatientIdDoctorOnly(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			String pcFirst = request.getParameter("pcId");
			String para = request.getParameter("para");
			Long pcId = null;
			if (pcFirst != null) {
				pcId = Long.parseLong(pcFirst);
			}
			Long intoPara = null;
			if (para != null) {
				intoPara = Long.parseLong(para);
			}
			String weekString = request.getParameter("weekFlag");
			String currentDate = request.getParameter("currentDate");
			Date date = null;
			int weekFlag = 0;
			if (weekString != null) {
				weekFlag = Integer.parseInt(weekString);
			}
			if (currentDate != null) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				date = sdf.parse(currentDate);
			}
			String data = JSONArray.fromObject(
					dao.findAllPatientConsultingByPatientId(patientId, pcId,
							intoPara, date, weekFlag),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			res.setData(data);
			res.setSuccess(true);
			out.println(JSONObject.fromObject(res));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 咨询医生查看某个会员的所有咨询问题
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findPatientConsultingById(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long id = Long.parseLong(request.getParameter("id"));
			String data = JSONObject.fromObject(
					dao.findPatientConsultingById(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss"))
					.toString();
			res.setData(data);
			res.setSuccess(true);
			out.println(JSONObject.fromObject(res));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 关闭会诊
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updatePatientConsultingStateMeeting(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long id = Long.parseLong(request.getParameter("pcaId"));
			res.setSuccess(dao.updatePatientConsultingStateMeeting(id));
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
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findNoRead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String search = request.getParameter("search");
			JSONObject json = null;
			if (search != null) {
				json = JSONObject.fromObject(search);
			}
			String doctorIdS = request.getParameter("doctorId");
			if (doctorIdS != null) {
				Long doctorId = Long.parseLong(doctorIdS);
				int flagDai = Integer.parseInt(request.getParameter("flagDai"));
				int flagYi = Integer.parseInt(request.getParameter("flagYi"));
				res.setData(dao.findNoRead(doctorId, flagDai, flagYi, json)
						+ "");
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

	public ActionForward findCountPatientConsultingByPatient(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			res
					.setData(dao.findCountPatientConsultingByPatient(patientId)
							+ "");
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

	public ActionForward checkIsNotZe(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String deptCode = request.getParameter("oldDeptCode");
			String doctorIdS = request.getParameter("id");
			if (doctorIdS != null) {
				Long doctorId = Long.parseLong(doctorIdS);
				res.setData(dao.checkIsNotZe(doctorId, deptCode) + "");
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

	public ActionForward updateState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long pcId = Long.parseLong(request.getParameter("id"));
			dao.updateState(pcId);
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

	public ActionForward updateApplicationBacuse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String data = request.getParameter("data");
			JSONObject mp = JSONObject.fromObject(data);
			dao.updateApplicationBacuse(Long.parseLong(mp.get("pcaId")
					.toString()), mp.get("applicationBacuse").toString());
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

	public ActionForward savaPatientConsultingMessage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String message = request.getParameter("message");
			JSONObject mp = JSONObject.fromObject(message);
			Long pcId = Long.parseLong(request.getParameter("pcId"));
			Long doctorId = Long.parseLong(request.getParameter("doctorId"));
			dao.savaPatientConsultingMessage(pcId, doctorId, mp.get(
					"applicationBacuse").toString());
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

	public ActionForward findAllPatientConsulting(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long pcId = Long.parseLong(request.getParameter("id"));
			String data = JSONArray.fromObject(
					dao.findAllPatientConsulting(pcId)).toString();
			out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward findAllPatientCousultingMessage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long pcId = Long.parseLong(request.getParameter("id"));
			String data = JSONArray.fromObject(
					dao.findAllPatientCousultingMessage(pcId),
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
	
	/**
	 * 撤销转发的咨询
	 */
	public ActionForward cancelAllSendPatientCousulting(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long pcId = Long.parseLong(request.getParameter("id"));
			dao.cancelAllSendPatientCousulting(pcId);
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
	
	
	public ActionForward findAllPatientConsultingByCancel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			Long pcId = Long.parseLong(request.getParameter("id"));
			String data = JSONArray.fromObject(
					dao.findAllPatientConsultingByCancel(pcId)).toString();
			out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
}
