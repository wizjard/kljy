package com.juncsoft.KLJY.biomedical.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.InHospitalCase.CaseMaster.entity.InHspCaseMaster;
import com.juncsoft.KLJY.biomedical.dao.MemberChangeWardDao;
import com.juncsoft.KLJY.biomedical.dao.MemberInfoDao;
import com.juncsoft.KLJY.biomedical.dao.OutPatientDao;
import com.juncsoft.KLJY.biomedical.dao.PatientIndexDao;
import com.juncsoft.KLJY.biomedical.entity.MemberChangeWard;
import com.juncsoft.KLJY.biomedical.entity.MemberInfo;
import com.juncsoft.KLJY.biomedical.entity.OutpatientGenerator;
import com.juncsoft.KLJY.biomedical.entity.OutpatientRecord;
import com.juncsoft.KLJY.biomedical.entity.PatientIndex;
import com.juncsoft.KLJY.biomedical.impl.MemberChangeWardDaoImpl;
import com.juncsoft.KLJY.biomedical.impl.MemberInfoImpl;
import com.juncsoft.KLJY.biomedical.impl.OutpatientImpl;
import com.juncsoft.KLJY.biomedical.impl.PatientIndexImpl;
import com.juncsoft.KLJY.biomedical.member.dao.MemberDao;
import com.juncsoft.KLJY.biomedical.member.entity.MemberMsg;
import com.juncsoft.KLJY.biomedical.member.impl.MemberDaoImpl;
import com.juncsoft.KLJY.message.dao.MessageDao;
import com.juncsoft.KLJY.message.entity.Message;
import com.juncsoft.KLJY.message.impl.MessageDaoImpl;
import com.juncsoft.KLJY.patient.dao.PatientDao;
import com.juncsoft.KLJY.patient.entity.Patient;
import com.juncsoft.KLJY.patient.impl.PatientDaoImpl;
import com.juncsoft.KLJY.system.notice.dao.NoticeDAO;
import com.juncsoft.KLJY.system.notice.entity.Notice;
import com.juncsoft.KLJY.system.notice.impl.NoticeDaolmpl;
import com.juncsoft.KLJY.user.entity.User;
import com.juncsoft.KLJY.util.DaoFactory;
import com.juncsoft.KLJY.util.DatabaseUtil;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;
import com.stongnet.sms.http.Sms;
import com.stongnet.sms.http.SmsOperator;

/**
 * 生物医学中心应用Action
 * 
 * @author xl 2010年12月8日10:13:26
 */
public class AppAction extends DispatchAction {
	private PatientIndexDao patDao = new PatientIndexImpl();
	private PatientDao dao = new PatientDaoImpl();
	private MemberInfoDao memDao = new MemberInfoImpl();
	private MessageDao messDao = new MessageDaoImpl();
	private OutPatientDao opDao = new OutpatientImpl();
	private PatientDao patientDao = (PatientDao) DaoFactory.InstanceImplement(PatientDao.class);
	private MemberChangeWardDao changeWardDao = new MemberChangeWardDaoImpl();
	

	private MemberDao memPatDao = new MemberDaoImpl();

	private NoticeDAO noticeDao = new NoticeDaolmpl(); // 公告或者通知dao

	/**
	 * 取得全部病人的方法，带过滤功能
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllPatients(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			List<Criterion> querys = new ArrayList<Criterion>();
			String search = request.getParameter("search");
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				String value = "";
				if (obj.containsKey("name")) {
					value = obj.getString("name");
					if (value.length() > 0) {
						querys.add(Restrictions.like("name", value,
								MatchMode.ANYWHERE));
					}
				}
				if (obj.containsKey("patNo")) {
					value = obj.getString("patNo");
					if (value.length() > 0) {
						querys.add(Restrictions.like("patNo", value,
								MatchMode.ANYWHERE));
					}
				}
				if (obj.containsKey("inWard")) {
					value = obj.getString("inWard");
					if (value.length() > 0) {
						querys.add(Restrictions.like("inWard", value,
								MatchMode.ANYWHERE));
					}
				}
				if (obj.containsKey("inDate")) {
					value = obj.getString("inDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							querys.add(Restrictions.between("inDate", d[0],
									d[1]));
						}
					}
				}
			}
			List<PatientIndex> pats = patDao.getAll(start, limit, querys
					.toArray(new Criterion[] {}));
			long total = patDao.getTotal(querys.toArray(new Criterion[] {}));
			JSONObject object = new JSONObject();
			object.put("root", pats);
			object.put("total", total);
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 会员信息更新操作
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOrUpdateMember(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject object = JSONObject.fromObject(request
					.getParameter("data"));
			MemberInfo member = (MemberInfo) JSONObject.toBean(object,
					MemberInfo.class);
			Patient pat = new Patient();
			pat.setId(Long.parseLong(object.getString("patientId")));
			member.setPatient(pat);
			String memberNo = member.getMemberNo();
			if (memberNo != null && memberNo.length() > 0) {
				MemberInfo m = memDao.get(member.getMemberNo());
				m.setAccount(member.getAccount());
				m.setMemberStatus(member.getMemberStatus());
				m.setInWard(member.getInWard());
				m.setInDate(member.getInDate());
				m.setPassword(member.getPassword());
				m.setMemberType(member.getMemberType());
				m.setMemo(member.getMemo());
				m.setBiaoben(member.getBiaoben());
				m.setInHspTimes(member.getInHspTimes());
				m.setInDoctor(member.getInDoctor());
				m.setInWardCode(member.getInWardCode());
				memDao.update(m);
			} else {
				member.setCurrentWard(member.getInWard());
				memberNo = memDao.save(member);
			}
			res.setData(memberNo);
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
	 * 根据会员编号获取会员信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMember(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String memberNo = request.getParameter("memberNo");
			JSONObject mem = JSONObject.fromObject(memDao.get(memberNo),
					JSONValueProcessor.cycleExcludel(
							new String[] { "patient" }, "yyyy-MM-dd"));
			res.setData(mem.toString());
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

	public ActionForward getMemberByPatientId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String patientId = request.getParameter("patientId");
			JSONObject mem = JSONObject.fromObject(memDao.getByPatientId(Long
					.parseLong(patientId)), JSONValueProcessor.cycleExcludel(
					new String[] { "cm" }, "yyyy-MM-dd"));
			res.setData(mem.toString());
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
	 * 检查会员账号是否存在
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward isAccountExits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String account = request.getParameter("account");
			res.setData(memDao.isAccountExits(account) + "");
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
	 * 取得全部会员的方法，带过滤功能
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllMembers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			List<Criterion> querys = new ArrayList<Criterion>();
			String search = request.getParameter("search");
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				String value = "";
				if(obj.containsKey("doctorId")){
					value= obj.getString("doctorId");
				}
				if (obj.containsKey("name")) {
					value = obj.getString("name");
					if (value.length() > 0) {
						List<Patient> pats = memDao
								.getPatients(Restrictions
										.or(Restrictions.like("patientName",
												value, MatchMode.ANYWHERE),
												Restrictions.like("patientNo",
														value,
														MatchMode.ANYWHERE)));
						querys.add(Restrictions.in("patient", pats));
					}
				}
				if (obj.containsKey("account")) {
					value = obj.getString("account");
					if (value.length() > 0) {
						querys.add(Restrictions.like("account", value,
								MatchMode.ANYWHERE));
					}
				}
				if (obj.containsKey("currentGroup")) {
					value = obj.getString("currentGroup");
					if (value.length() > 0) {
						querys.add(Restrictions.like("currentGroup", value,
								MatchMode.ANYWHERE));
					}
				}
				if (obj.containsKey("inDate")) {
					value = obj.getString("inDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							querys.add(Restrictions.between("inDate", d[0],
									d[1]));
						}
					}
				}
				if (obj.containsKey("deptName")) {
					value = obj.getString("deptName");
					if (value.length() > 0) {
						querys.add(Restrictions
								.in("deptName", value.split(",")));
					}
				}
				if (obj.containsKey("grounpName")) {
					value = obj.getString("grounpName");
					if (value.length() > 0) {
						querys.add(Restrictions
								.in("grounpName", value.split(",")));
					}
				}
			}
			List<MemberInfo> pats = memDao.getAll(start, limit, querys
					.toArray(new Criterion[] {}));
			long total = memDao.getTotal(querys.toArray(new Criterion[] {}));
			JSONObject object = new JSONObject();
			object.put("root", pats);
			object.put("total", total);
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 获取病人门诊随访列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOutPatientRecords(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("patientId"));
			out.print(JSONArray.fromObject(opDao.getAll(id), JSONValueProcessor
					.cycleExcludel(new String[] { "patient" }, "yyyy/MM/dd")));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 获取病人门诊随访记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOutPatientRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("recordId"));
			res.setData(JSONObject.fromObject(
					opDao.get(id),
					JSONValueProcessor.cycleExcludel(
							new String[] { "patient" }, "yyyy-MM-dd"))
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
	 * 保存随访生成记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveGenerator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			OutpatientGenerator gene = (OutpatientGenerator) JSONObject.toBean(
					JSONObject.fromObject(request.getParameter("data")),
					OutpatientGenerator.class);
			Patient pat = new Patient();
			pat.setId(patientId);
			gene.setPatient(pat);
			Long geneId = opDao.saveGenerator(gene);
			res.setData(geneId.toString());
			String recordId = request.getParameter("recordId");
			if (recordId != null) {
				OutpatientRecord record = opDao.get(Long.parseLong(recordId));
				gene.setId(geneId);
				record.setGenerator2(gene);
				opDao.update(record);
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
	 * 更新随访生成记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateGenerator(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			OutpatientGenerator gene = (OutpatientGenerator) JSONObject.toBean(
					JSONObject.fromObject(request.getParameter("data")),
					OutpatientGenerator.class);
			OutpatientGenerator update = opDao.getGenerator(gene.getId());
			update.setStartDate(gene.getStartDate());
			update.setGroupName(gene.getGroupName());
			update.setCycle(gene.getCycle());
			update.setCheckContent(gene.getCheckContent());
			update.setMemo(gene.getMemo());
			opDao.updateGenerator(update);
			String recordId = request.getParameter("recordId");
			if (recordId != null) {
				OutpatientRecord record = opDao.get(Long.parseLong(recordId));
				record.setGenerator2(update);
				opDao.update(record);
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
	 * 更新随访记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateOutpatientRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			OutpatientRecord record = (OutpatientRecord) JSONObject.toBean(
					JSONObject.fromObject(request.getParameter("data")),
					OutpatientRecord.class);
			OutpatientRecord update = opDao.get(record.getId());
			update.setComeDate(record.getComeDate());
			update.setZhusu(record.getZhusu());
			update.setTimes(record.getTimes());
			update.setXianbingshi(record.getXianbingshi());
			update.setQitabingshi(record.getQitabingshi());
			update.setTige(record.getTige());
			update.setFuzhu(record.getFuzhu());
			update.setZhenduan(record.getZhenduan());
			update.setJianyi(record.getJianyi());
			update.setYisheng(record.getYisheng());
			update.setBeizhu(record.getBeizhu());
			update.setBiaoben(record.getBiaoben());
			opDao.update(update);
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
	 * 随访提醒列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllOutpatientGenerators(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			List<Criterion> querys = new ArrayList<Criterion>();
			String search = request.getParameter("search");
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				String value = "";
				if (obj.containsKey("name")) {
					value = obj.getString("name");
					if (value.length() > 0) {
						List<Patient> pats = memDao
								.getPatients(Restrictions
										.or(Restrictions.like("patientName",
												value, MatchMode.ANYWHERE),
												Restrictions.like("patientNo",
														value,
														MatchMode.ANYWHERE)));
						querys.add(Restrictions.in("patient", pats));
					}
				}
				if (obj.containsKey("planDate")) {
					value = obj.getString("planDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							querys.add(Restrictions.between("planDate", d[0],
									d[1]));
						}
					}
				}
				if (obj.containsKey("startDate")) {
					value = obj.getString("startDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							querys.add(Restrictions.between("startDate", d[0],
									d[1]));
						}
					}
				}
				if (obj.containsKey("groupName")) {
					value = obj.getString("groupName");
					if (value.length() > 0) {
						querys.add(Restrictions.like("groupName", value,
								MatchMode.ANYWHERE));
					}
				}
			}
			List<OutpatientGenerator> pats = opDao.getAllGenerator(start,
					limit, querys.toArray(new Criterion[] {}));
			long total = opDao.getTotalGenerator(querys
					.toArray(new Criterion[] {}));
			JSONObject object = new JSONObject();
			object.put("root", pats);
			object.put("total", total);
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 更新随访通知信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward noticeUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			String memo = request.getParameter("memo");
			String reserveDate = request.getParameter("reserveDate");
			String noticeDate = request.getParameter("noticeDate");
			OutpatientGenerator gene = opDao.getGenerator(id);
			gene.setMemo(memo);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if (reserveDate != null && reserveDate.length() > 0) {
				gene.setReserveDate(format.parse(reserveDate));
			} else {
				gene.setReserveDate(null);
			}
			if (noticeDate != null && noticeDate.length() > 0) {
				gene.setNoticeDate(format.parse(noticeDate));
			} else {
				gene.setNoticeDate(null);
			}
			opDao.updateGenerator(gene);
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
	 * 新增会员入会病历
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveMemberCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			InHspCaseMaster master;
			String memberNo = request.getParameter("memberNo");
			MemberInfo mem = memDao.get(memberNo);
			if (mem.getCm() == null) {
				master = new InHspCaseMaster();
				master.setCaseModuleId("liver");
				master.setFlag(1);
				master.setPatientId(mem.getPatient().getId());
				mem.setCm(memDao.saveMemberCase(master));
				memDao.update(mem);
			}
			master = mem.getCm();
			res.setData(JSONObject.fromObject(master).toString());
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
	 * 导出会员随访信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward memberDataAnalysis(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			List<Criterion> querys = new ArrayList<Criterion>();
			String search = request.getParameter("search");
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				String value = "";
				if (obj.containsKey("name")) {
					value = obj.getString("name");
					if (value.length() > 0) {
						List<Patient> pats = memDao
								.getPatients(Restrictions
										.or(Restrictions.like("patientName",
												value, MatchMode.ANYWHERE),
												Restrictions.like("patientNo",
														value,
														MatchMode.ANYWHERE)));
						querys.add(Restrictions.in("patient", pats));
					}
				}
				if (obj.containsKey("account")) {
					value = obj.getString("account");
					if (value.length() > 0) {
						querys.add(Restrictions.like("account", value,
								MatchMode.ANYWHERE));
					}
				}
				if (obj.containsKey("currentGroup")) {
					value = obj.getString("currentGroup");
					if (value.length() > 0) {
						querys.add(Restrictions.like("currentGroup", value,
								MatchMode.ANYWHERE));
					}
				}
				if (obj.containsKey("inDate")) {
					value = obj.getString("inDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							querys.add(Restrictions.between("inDate", d[0],
									d[1]));
						}
					}
				}
			}
			JSONObject object = new JSONObject();
			object.put("root", memDao.memberDataAnalysis(start, limit, querys
					.toArray(new Criterion[] {})));
			object.put("total", memDao.getTotal(querys
					.toArray(new Criterion[] {})));
			out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 导出会员随访信息到Excel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward memberDataAnalysisToExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			List<Criterion> querys = new ArrayList<Criterion>();
			String search = request.getParameter("search");
			if (search != null && search.length() > 0) {
				JSONObject obj = JSONObject.fromObject(search);
				String value = "";
				if (obj.containsKey("name")) {
					value = obj.getString("name");
					if (value.length() > 0) {
						List<Patient> pats = memDao
								.getPatients(Restrictions
										.or(Restrictions.like("patientName",
												value, MatchMode.ANYWHERE),
												Restrictions.like("patientNo",
														value,
														MatchMode.ANYWHERE)));
						querys.add(Restrictions.in("patient", pats));
					}
				}
				if (obj.containsKey("account")) {
					value = obj.getString("account");
					if (value.length() > 0) {
						querys.add(Restrictions.like("account", value,
								MatchMode.ANYWHERE));
					}
				}
				if (obj.containsKey("currentGroup")) {
					value = obj.getString("currentGroup");
					if (value.length() > 0) {
						querys.add(Restrictions.like("currentGroup", value,
								MatchMode.ANYWHERE));
					}
				}
				if (obj.containsKey("inDate")) {
					value = obj.getString("inDate");
					if (value.length() > 0) {
						String[] temp = value.split(" ");
						DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date[] d = { null, null };
						if (temp.length == 1) {
							Date t = format.parse(temp[0]);
							d[0] = t;
							d[1] = new Date(t.getTime() + 3600 * 24 * 1000);
						} else if (temp.length == 2) {
							d[0] = format.parse(temp[0]);
							d[1] = format.parse(temp[1]);
						}
						if (d[0] != null && d[1] != null) {
							querys.add(Restrictions.between("inDate", d[0],
									d[1]));
						}
					}
				}
			}

			String _temp = memDao.getTotal(querys.toArray(new Criterion[] {}))
					+ "";
			JSONArray array = memDao.memberDataAnalysis(0, Integer
					.parseInt(_temp), querys.toArray(new Criterion[] {}));
			String tempPath = request.getRealPath("/")
					+ "\\module\\biomedical\\temp\\";
			res.setData(memDao.memberDataAnalysisToExcel(array, tempPath));
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

	public ActionForward getPatientInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			PatientDao dao = new PatientDaoImpl();
			Patient pat = dao.findById(id);
			res.setData(pat.getPatientName());
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

	public ActionForward ChangeWard_GetByMemNo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			out.println(JSONArray.fromObject(changeWardDao.getByMemNo(request
					.getParameter("memNo")), JSONValueProcessor.cycleExcludel(
					new String[] {}, "yyyy-MM-dd")));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward ChangeWard_SaveOrUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			JSONObject data = JSONObject.fromObject(request
					.getParameter("data"));
			MemberChangeWard mc = (MemberChangeWard) JSONObject.toBean(data,
					MemberChangeWard.class);
			if (mc.getId() == null || mc.getId() <= 0) {
				changeWardDao.save(mc);
			} else {
				changeWardDao.update(mc);
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

	public ActionForward ChangeWard_Delete(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			MemberChangeWard mc = new MemberChangeWard();
			mc.setId(id);
			changeWardDao.delete(mc);
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
	 * 根据会员编号查询我的健康记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMyHealthRecords2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String memNo = request.getParameter("memNo");
			MemberInfo mem = new MemberInfoImpl().get(memNo);
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			JSONObject object = new JSONObject();
			object.put("root", JSONArray.fromObject(memPatDao.getHealthRecords(
					start, limit, Restrictions.eq("mem", mem)),
					JSONValueProcessor.cycleExcludel(new String[] { "mem" },
							"yyyy-MM-dd")));
			object.put("total", memPatDao.getHealthRecordTotal(Restrictions.eq(
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
	 * 根据会员编号查询网上咨询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMemberMsgs2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String ward = request.getParameter("ward");
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			Integer flag = Integer.parseInt(request.getParameter("flag"));
			out.println(memPatDao.getMemberMsgs2(start, limit, ward, flag));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 医生回复网上咨询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward answerMemberMsg(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String doctor = request.getParameter("doctor");
			String answer = request.getParameter("answer");
			Long id = Long.parseLong(request.getParameter("id"));
			MemberMsg msg = memPatDao.getMemberMsg(id);
			msg.setDoctor(doctor);
			msg.setAnswer(answer);
			msg.setAnswerDate(new Date());
			memPatDao.saveOrUpdateMemberMsg(msg);
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
	 * 医生获取会员网上上传资料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMemberFileUpload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			String memberNo = request.getParameter("memberNo");
			MemberInfo mem = new MemberInfo();
			mem.setMemberNo(memberNo);
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			JSONObject object = new JSONObject();
			object.put("root", JSONArray.fromObject(new MemberDaoImpl()
					.getMemberUploadFileByMember(mem, start, limit),
					JSONValueProcessor.cycleExcludel(new String[] { "mem" },
							"yyyy-MM-dd")));
			object.put("total", new MemberDaoImpl()
					.getMemberUploadFileByMemberTotal(mem));
			out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward getSixNoticeList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String searchPara = request.getParameter("typeName");
			List<Notice> noticeList = noticeDao.getSixNoticeList(limit,
					searchPara);
			String data = JSONArray.fromObject(noticeList).toString();
			out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward getMemberByDoctorId(ActionMapping mapping, ActionForm form,
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
			out.println(memDao.getMemberByDoctorId(start,limit,doctorId,search,flag,orderBy,descOrasc));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	
	//如果前台传的是patientId  用于随访等页面的发送短信调用
	public ActionForward sendMsgByPatientId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Response res = new Response();
		PrintWriter out = response.getWriter();
		User user = (User) request.getSession().getAttribute("user");
		
		if(user==null){
			res.setMsg("登录超时，请重新登录");
			res.setSuccess(false);
			return null;
		}
		
		Message message = new Message();    // Message object;
		Date date = new Date();             // Date of message sent;
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //format the messageDate;
		String dateStr = sdf.format(date);    //convert messageDate to a string;
		StringBuffer msgBuffer = new StringBuffer("");  //短信内容：由开头  正文 和 落款组成
		String msgContent = request.getParameter("content");  //获得前台传过来的短信正文
		String appendix = request.getParameter("appendix");  //获得前台传过来的落款
		Session session = null;
		Transaction tran = null;
		
		if(request.getParameter("id").contains(",")){  //如果是多选发送短信
			int k=0;  //判断是否都发送成功
			StringBuffer noMobilePatientName = new StringBuffer();
			String ids = request.getParameter("id");  //如果前台传的是patientId 那么要先由patientId获得memberNo
			String idArr[] = ids.split(",");
			try {
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
				for(int i=0;i<idArr.length;i++){
					String memberNo = "";
					MemberInfo mem = null;
							 List memList = dao.findMemberNoPatientId(idArr[i],session);
							 if(memList==null||memList.size()==0){  //如果该病人不是会员   则不给他发短信
								 continue;
							 }else if(memList!=null&&memList.size()>0){
							 memberNo = memList.get(0).toString();  //获得会员Id 标识
							 mem = dao.findMemberInfoByMemNo(memberNo.toString(),session);  //获得会员实体
							 }
					if (mem.getPatient().getMobilePhone()!= null && mem.getPatient().getMobilePhone().length() > 0) {
						Sms sms = new Sms();
						sms.phone = mem.getPatient().getMobilePhone();
						msgBuffer.append(msgContent).append(" ").append(appendix);  //将短信开头，短信正文和短信落款拼接起来
						sms.content = msgBuffer.toString();
						SmsOperator smsOp = SmsOperator.getInstance();
						smsOp.sendSms("101100-SJB-HUAX-566466", "ALQPPJJI",new Sms[] { sms });
						k++;  //每发送成功一次，k +1
						
						message.setMessageContent(msgBuffer.toString()); //set message.messageContent;
						message.setMessageDate(date);        //set message.messageDate;
						message.setMemberInfo(mem);             //set message.memberInfo;
						message.setUser(user);
						messDao.addMessage(message,session);
					} else {
						noMobilePatientName.append(mem.getPatient().getPatientName()).append(",");  //将没有登记手机的病人姓名记录起来
					}
				}
				if(k==idArr.length){  //如果最后k的值为病人id数组长度  表示所有病人的短信都发送成功
					res.setMsg("所有信息发送成功。");
					res.setSuccess(true);
				}else{
					res.setMsg("会员  "+noMobilePatientName.substring(0, noMobilePatientName.length()-1)+" 未登记手机信息。");
					res.setSuccess(false);
				}
			} catch (Exception e) {
				res.setMsg("后台系统错误。");
				res.setSuccess(false);
				if(tran != null){
					tran.rollback();
				}
				e.printStackTrace();
			} finally {
				DatabaseUtil.closeResource(session);
				out.println(JSONObject.fromObject(res).toString());
				out.close();
			}
			
		}else{   //如果只是单选发送短信
			try {
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
				String patientId = request.getParameter("id");
				MemberInfo mem = null;
				String memberNo ="";
				List memList = dao.findMemberNoPatientId(patientId,session);
				if(memList!=null&&memList.size()>0){
					memberNo = memList.get(0).toString();  //获得会员Id 标识
					mem = dao.findMemberInfoByMemNo(memberNo,session);  //获得会员实体
				}
				if (mem.getPatient().getMobilePhone() != null && mem.getPatient().getMobilePhone().length() > 0) {
					Sms sms = new Sms();
					sms.phone = mem.getPatient().getMobilePhone();
					msgBuffer.append(msgContent).append(" ").append(appendix);  //将短信开头，短信正文和短信落款拼接起来
					sms.content = msgBuffer.toString();
					SmsOperator smsOp = SmsOperator.getInstance();
					smsOp.sendSms("101100-SJB-HUAX-566466", "ALQPPJJI",new Sms[] { sms });
					//插入到message数据表中
					message.setMessageContent(msgBuffer.toString()); //set message.messageContent;
					message.setMessageDate(date);        //set message.messageDate;
					message.setMemberInfo(mem);             //set message.memberInfo;
					message.setUser(user);
					messDao.addMessage(message,session);
					
					res.setMsg("信息发送成功。");
					res.setSuccess(true);
				} /*else {
					res.setMsg("会员未登记手机信息。");
					res.setSuccess(false);
				}*/
			} catch (Exception e) {
				res.setMsg("后台系统错误。");
				res.setSuccess(false);
				if(tran != null){
					tran.rollback();
				}
				e.printStackTrace();
			} finally {
				session = DatabaseUtil.getSession();
				out.println(JSONObject.fromObject(res).toString());
				out.close();
			}
		}
		return null;
	}
	
	
	//如果前台传的是memberNo  用于我组会员等页面的发送短信调用
	public ActionForward sendMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Response res = new Response();
		PrintWriter out = response.getWriter();
		User user = (User) request.getSession().getAttribute("user");
		
		if(user==null){
			res.setMsg("登录超时，请重新登录");
			res.setSuccess(false);
			return null;
		}
		
		Date date = new Date();             // Date of message sent;
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //format the messageDate;
		String dateStr = sdf.format(date);    //convert messageDate to a string;
		StringBuffer msgBuffer = null;  //短信内容：由开头  正文 和 落款组成
		String msgContent = request.getParameter("content");  //获得前台传过来的短信正文
		String appendix = request.getParameter("appendix");  //获得前台传过来的落款
		Session session = null;
		Transaction tran = null;
		if(request.getParameter("id").contains(",")){  //如果是多选发送短信
			int k=0;  //判断是否都发送成功
			StringBuffer noMobilePatientName = new StringBuffer();
			String ids = request.getParameter("id");  //会员唯一标识    memberNo
			String idArr[] = ids.split(",");
			try {
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
				for(int i=0;i<idArr.length;i++){
					MemberInfo mem = dao.findMemberInfoByMemNo(idArr[i],session);
					if (mem.getPatient().getMobilePhone() != null && mem.getPatient().getMobilePhone().length() > 0) {
						Sms sms = new Sms();
						Message message = new Message();    //Message object;
						sms.phone = mem.getPatient().getMobilePhone();
						msgBuffer = new StringBuffer("");
						msgBuffer.append(msgContent).append(" ").append(appendix);  //将短信开头，短信正文和短信落款拼接起来
						sms.content = msgBuffer.toString();
						SmsOperator smsOp = SmsOperator.getInstance();
						smsOp.sendSms("101100-SJB-HUAX-566466", "ALQPPJJI",new Sms[] { sms });
						k++;  //每发送成功一次，k +1
						
						message.setMessageContent(msgBuffer.toString()); //set message.messageContent;
						message.setMessageDate(date);        //set message.messageDate;
						message.setMemberInfo(mem);             //set message.memberInfo;
						message.setUser(user);
						messDao.addMessage(message,session);
					} else {
						noMobilePatientName.append(mem.getPatient().getPatientName()).append(",");  //将没有登记手机的病人姓名记录起来
					}
				}
				if(k==idArr.length){  //如果最后k的值为病人id数组长度  表示所有病人的短信都发送成功
					res.setMsg("所有信息发送成功。");
					res.setSuccess(true);
				}else{
					res.setMsg("会员  "+noMobilePatientName.substring(0, noMobilePatientName.length()-1)+" 未登记手机信息。");
					res.setSuccess(false);
				}
				tran.commit();
			} catch (Exception e) {
				res.setMsg("后台系统错误。");
				res.setSuccess(false);
				if(tran != null){
					tran.rollback();
				}
				e.printStackTrace();
			} finally {
				DatabaseUtil.closeResource(session);
				out.println(JSONObject.fromObject(res).toString());
				out.close();
			}
			
		}else{   //如果只是单选发送短信
			try {
				session = DatabaseUtil.getSession();
				tran = session.beginTransaction();
				String memNo = request.getParameter("id");
				MemberInfo mem = dao.findMemberInfoByMemNo(memNo,session);
				if (mem.getPatient().getMobilePhone() != null && mem.getPatient().getMobilePhone().length() > 0) {
					Message message = new Message();    //Message object;
					Sms sms = new Sms();
					sms.phone = mem.getPatient().getMobilePhone();
					msgBuffer = new StringBuffer("");
					msgBuffer.append(msgContent).append(" ").append(appendix);  //将短信开头，短信正文和短信落款拼接起来
					sms.content = msgBuffer.toString();
					SmsOperator smsOp = SmsOperator.getInstance();
					smsOp.sendSms("101100-SJB-HUAX-566466", "ALQPPJJI",new Sms[] { sms });
					
					//插入到message数据表中
					message.setMessageContent(msgBuffer.toString()); //set message.messageContent;
					message.setMessageDate(date);        //set message.messageDate;
					message.setMemberInfo(mem);             //set message.memberInfo;
					message.setUser(user);
					messDao.addMessage(message,session);
					
					res.setMsg("信息发送成功。");
					res.setSuccess(true);
				} /*else {
					res.setMsg("会员未登记手机信息。");
					res.setSuccess(false);
				}*/
				tran.commit();
			} catch (Exception e) {
				res.setMsg("后台系统错误。");
				res.setSuccess(false);
				if(tran != null){
					tran.rollback();
				}
				e.printStackTrace();
			} finally {
				DatabaseUtil.closeResource(session);
				out.println(JSONObject.fromObject(res).toString());
				out.close();
			}
		}
		return null;
	}
	
	
	public ActionForward getMemberByDeptCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String deptCode = request.getParameter("currentWard");
			String search = request.getParameter("search");
			String flag = request.getParameter("flag");
			String descOrasc = request.getParameter("descOrasc");
			out.println(memDao.getMemberByDeptCode(start,limit,deptCode,search,flag,descOrasc));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward deleteMemberById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Response res = new Response();
		PrintWriter out = response.getWriter();
		try {
			String memberNo = request.getParameter("memberNo");
			memDao.deleteMemberById(memberNo);
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
	
	
	/*
	 * 管理员获得医生或管理员所在科室会员的所有会员  导出excel用
	 */
	public ActionForward creatExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
			     + new String("会员列表.xls".getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			String doctorId = request.getParameter("doctorId");
			String ids = request.getParameter("ids");  //如果前台页面多选了
			String deptCode = request.getParameter("currentWard"); //科室编号
			String search = request.getParameter("search");  //查询条件
			if(search != null)
				search = URLDecoder.decode(search,"UTF-8"); 
			
			String flag = request.getParameter("flag");
			String orderBy = request.getParameter("orderBy");
			String descOrasc = request.getParameter("descOrasc");
			List<Map> list = null;
			
			if(doctorId!=null){
				list = memDao.getAllMemberByDoctorId(Long.parseLong(doctorId),search,flag,orderBy,descOrasc);
			}else if(ids!=null&&ids.length()>0){ //如果前台页面多选了记录
				list = memDao.getAllMemberByDeptCode(ids);
			}else{
				list = memDao.getAllMemberByDeptCode(deptCode,search,flag,descOrasc);
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
	

	/*
	 * 管理员获得医生或管理员所在科室会员的所有会员  导出excel用 
	 */
	public ActionForward creatExcelAllMember(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
			     + new String("会员列表.xls".getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			String doctorId = request.getParameter("doctorId");
			String ids = request.getParameter("ids");  //如果前台页面多选了
			String deptCode = request.getParameter("currentWard"); //科室编号
			String search = request.getParameter("search");  //查询条件
			if(search != null)
				search = URLDecoder.decode(search,"UTF-8"); 
			
			String flag = request.getParameter("flag");
			String orderBy = request.getParameter("orderBy");
			String descOrasc = request.getParameter("descOrasc");
			List<Map> list = null;
			
			/*if(doctorId!=null){
				list = memDao.getAllMemberByDoctorId(Long.parseLong(doctorId),search,flag,orderBy,descOrasc);
			}else if(ids!=null&&ids.length()>0){ //如果前台页面多选了记录
				list = memDao.getAllMemberByDeptCode(ids);
			}else{
				list = memDao.getAllMemberByDeptCode(deptCode,search,flag,descOrasc);
			}*/
			list =memDao.getAllMemberForYuanzhang(search,flag,descOrasc);
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

	
	
	
	
	private InputStream getInputStream(List<Map> list) {
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
		   cell.setCellValue("病案号");

		   cell = row.createCell((short) 3);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("是否有随访计划");
		   
		   cell = row.createCell((short) 4);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("入会科室");
		   
		   cell = row.createCell((short) 5);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("当前责任科室");
		   
		   cell = row.createCell((short) 6);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("当前责任小组");
		   
		   cell = row.createCell((short) 7);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("入会日期");
		   
		   cell = row.createCell((short) 8);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("预留标本");
		   
		   cell = row.createCell((short) 9);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("当前疾病分组");
		   
		   cell = row.createCell((short) 10);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("X线号");
		   
		   cell = row.createCell((short) 11);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("CT号");
		   
		   cell = row.createCell((short) 12);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("MRI号");
		   
		   cell = row.createCell((short) 13);
		   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		   cell.setCellValue("病理号");
		   
		   int i=1;
		   for(Map m : list){
			   row = sheet.createRow(i);
			   cell = row.createCell((short) 0);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   cell.setCellValue(i);

			   cell = row.createCell((short) 1);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.get("patientName")!=null&&!"".equals(m.get("patientName").toString()))
			     cell.setCellValue(m.get("patientName").toString());

			   cell = row.createCell((short) 2);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.get("patientNo")!=null&&!"".equals(m.get("patientNo").toString()))
			    cell.setCellValue(m.get("patientNo").toString());
			   
			   
			   cell = row.createCell((short) 3);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  //会员生日数据库中是Date格式，要转换成yyyy-MM-dd格式
				   if(m.get("pcount")!=null&&!"".equals(m.get("pcount").toString())&&Integer.parseInt(m.get("pcount").toString())>0){
					   cell.setCellValue("有随访");
				   }else{
					   cell.setCellValue("无随访");
				   }
			  
			   cell = row.createCell((short) 4);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.get("inWard")!=null&&!"".equals(m.get("inWard").toString()))
			     cell.setCellValue(m.get("inWard").toString());
			   
			   cell = row.createCell((short) 5);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.get("deptName")!=null&&!"".equals(m.get("deptName").toString()))
			     cell.setCellValue(m.get("deptName").toString());

			   cell = row.createCell((short) 6);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.get("grounpName")!=null&&!"".equals(m.get("grounpName").toString()))
			      cell.setCellValue(m.get("grounpName").toString());
			   
			   cell = row.createCell((short) 7);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.get("inDate")!=null&&!"".equals(m.get("inDate").toString()))
			     cell.setCellValue(m.get("inDate").toString());
			   
			   cell = row.createCell((short) 8);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(Integer.parseInt(m.get("biaoben").toString())==1){
				   cell.setCellValue("是");
			   }else{
				   cell.setCellValue("否");
			   }
			   
			   
			   cell = row.createCell((short) 9);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.get("currentGroup")!=null&&!"".equals(m.get("currentGroup").toString()))
				   cell.setCellValue(m.get("currentGroup").toString());
			   
			   cell = row.createCell((short) 10);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.get("xNo")!=null&&!"".equals(m.get("xNo").toString()))
				   cell.setCellValue(m.get("xNo").toString());
			   
			   cell = row.createCell((short) 11);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.get("ctNo")!=null&&!"".equals(m.get("ctNo").toString()))
				   cell.setCellValue(m.get("ctNo").toString());
			   
			   cell = row.createCell((short) 12);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.get("mriNo")!=null&&!"".equals(m.get("mriNo").toString()))
				   cell.setCellValue(m.get("mriNo").toString());
			   
			   cell = row.createCell((short) 13);
			   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			   if(m.get("blNo")!=null&&!"".equals(m.get("blNo").toString()))
				   cell.setCellValue(m.get("blNo").toString());
			   
			   
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
	
	public ActionForward findPatient(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long patientId = Long.parseLong(request.getParameter("patientId"));
			res.setData(JSONObject.fromObject(memDao.findPatient(patientId)).toString());
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
	
	public ActionForward getYWMemberByDeptCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Integer start = Integer.parseInt(request.getParameter("start"));
			Integer limit = Integer.parseInt(request.getParameter("limit"));
			String search = request.getParameter("search");
			String flag = request.getParameter("flag");
			String descOrasc = request.getParameter("descOrasc");
			out.println(memDao.getYWMemberByDeptCode(start,limit,search,flag,descOrasc));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward findMemberHealthRecordByPatientId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		try {
			Long id = Long.parseLong(request.getParameter("pid"));
			String data = JSONArray.fromObject(
					memDao.findMemberHealthRecordByPatientId(id),
					JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm"))
					.toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
	
	public ActionForward firstFindMemberHealthRecordByPatientId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try{
			Long id = Long.parseLong(request.getParameter("pid"));
			res.setData(JSONObject.fromObject(memDao.firstFindMemberHealthRecordByPatientId(id)).toString());
			res.setSuccess(true);
		}catch(Exception e){
			res.setSuccess(false);
			e.printStackTrace();
		}finally{
			out.println(JSONObject.fromObject(res));
			out.close();
		}
		return null;

	}
}
