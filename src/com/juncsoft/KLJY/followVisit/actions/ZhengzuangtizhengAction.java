package com.juncsoft.KLJY.followVisit.actions;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.followVisit.dao.ZhengzuangtizhengDao;
import com.juncsoft.KLJY.followVisit.entity.Ptsymptomst;
import com.juncsoft.KLJY.followVisit.entity.Symptomst;
import com.juncsoft.KLJY.followVisit.impl.ZhengzuangtizhengDaoImpl;
import com.juncsoft.KLJY.util.Response;

public class ZhengzuangtizhengAction extends DispatchAction {
	private ZhengzuangtizhengDao zDao = new ZhengzuangtizhengDaoImpl();
	public ActionForward findZhengzuangtizheng(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		String date = null;
		try {
			Integer p_id = Integer.parseInt(request.getParameter("id"));
			List pList = zDao.findSymptomList(p_id);
			String data = JSONArray.fromObject(pList).toString();
			out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward findT_state(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		String date = null;
		try {
			List tList = zDao.findT_state();
			String data = JSONArray.fromObject(tList).toString();
			out.print(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}

	public ActionForward findT_symptom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		String date = null;
		Response msg = new Response();
		try {
			List tList = zDao.findT_symptom();
			String data = JSONArray.fromObject(tList).toString();
			msg.setData(data);
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setSuccess(false);
			e.printStackTrace();
		} finally {
			out.println(JSONObject.fromObject(msg).toString());
			out.close();
		}
		return null;
	}

	public ActionForward saveZhengzuangtizheng(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response m = new Response();
		List allList = new ArrayList();
		List list = new ArrayList();
		Symptomst symptomst = new Symptomst();
		Integer ptid = null;
		m.setSuccess(true);
		boolean flag = false;
		boolean b = false;
		boolean o = false;
		 Integer caseId = Integer.parseInt(request.getParameter("id"));
		try {
			String array = request.getParameter("array");
			String mark = request.getParameter("mark");
			if ("\"isfirst\"".equals(mark)) {
				flag = true;
				b = true;
			}
			JSONArray arr = JSONArray.fromObject(array);
			ptid = zDao.getLassptid(caseId);// �����׳�?
			if (!"[]".equals(array)) {
				for (Object object : arr) {
					JSONObject json = JSONObject.fromObject(object);
					String t_symptom_ID = json.getString("t_symptom_ID");
					String t_state_ID = json.getString("t_state_ID");
					String tp_text = json.getString("tp_text");
					if ("null".equals(t_symptom_ID)
							|| "null".equals(t_state_ID)
							|| "null".equals(tp_text)) {
						o = true;
						break;
					}
				}
				if (o == false) {
					for (Object object : arr) {
						Hashtable<String, String> ht = new Hashtable<String, String>();
						JSONObject json = JSONObject.fromObject(object);
						String hidsy_id = json.getString("hidsy_id");// 放主键的位置
						String _syid = json.getString("syid");// 主键
						String t_symptom_ID = json.getString("t_symptom_ID");
						String t_state_ID = json.getString("t_state_ID");
						String tp_text = json.getString("tp_text");
						Integer tsy_id = new Integer(t_symptom_ID);
						Integer tid = new Integer(t_state_ID);
						if (flag == true) {// �㱣��
							Ptsymptomst ptsymptomst = new Ptsymptomst();
							ptsymptomst.setPid(caseId);// ����ID
							ptsymptomst.setPtimes(new Date());// �Ǽ�ʱ��
							ptid = zDao.savePtsymptomst(ptsymptomst);//
							flag = false;
						}
						if ("".equals(_syid) || _syid == null
								|| "null".endsWith(_syid) || b == true) {
							symptomst.setPt_id(ptid);
							symptomst.setT_symptom_ID(tsy_id);
							symptomst.setT_state_ID(tid);
							symptomst.setSy_text(tp_text);
							Integer syid = zDao.saveSymptom(symptomst);
							ht.put("hidsy_id", hidsy_id);
							ht.put("syid", syid.toString());
							list.add(ht);
						} else {
							symptomst = zDao.findSymptom(new Integer(_syid)
									.intValue());
							symptomst.setPt_id(ptid);
							symptomst.setT_symptom_ID(tsy_id);
							symptomst.setT_state_ID(tid);
							symptomst.setSy_text(tp_text);
							zDao.updateSymptom(symptomst);
						}

					}
				} else {
					m.setSuccess(false);
				}

			} else {
				m.setSuccess(false);
			}
		} catch (Exception e) {
			m.setSuccess(false);
			e.printStackTrace();
		} finally {
			// out.println(JSONObject.fromObject(m).toString());
			allList.add(list);
			allList.add("nofirst");
			allList.add(m);
			String data = JSONArray.fromObject(allList).toString();
			out.print(data);
			out.close();
		}
		return null;
	}

	public ActionForward delTr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response  res =new Response();
		Symptomst symptomst = new Symptomst();
		try {
			String syid = request.getParameter("_delId");
			Integer sy_id = new Integer(syid).intValue();
			symptomst = zDao.findSymptom(sy_id);
			zDao.deleteSymptom(symptomst);
			res.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			res.setSuccess(false);
		} finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();

		}
		return null;
	}

	public ActionForward findZhengZuang(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res =new Response();
		String getStatu = null;
		List list =new ArrayList();
		List getTimelist = null;
		List getZhengZuanglist = null;
		StringBuffer tr = new StringBuffer();
		StringBuffer td_time = new StringBuffer();
		try {
			int caseId=Integer.parseInt(request.getParameter("id"));
			getTimelist = zDao.findCount(caseId);// 
			if(getTimelist.size()>0){
			getZhengZuanglist = zDao.findZhengZuangName(caseId);//
			int allRecorders = getZhengZuanglist.size();// 多少条记录
			int currentPage = 1;// 当前所在页
			currentPage=new Integer(request.getParameter("currentPage")).intValue();
			int upPage = currentPage - 1;// 上一页
			int downPage = currentPage + 1;// 上一页
			int lineSize = allRecorders;// 每页显示的记录数
			int allPages = (allRecorders + lineSize - 1) / lineSize;// 总页数
			for (int j = currentPage - 1; j < getTimelist.size(); j++) {
				td_time.append("<td>"
						+ ((Ptsymptomst) getTimelist.get(j)).getPtimes()
								.toString().substring(0, 10) + "</td>");
			}
			for (int i = 0; i < getZhengZuanglist.size(); i++) {
				StringBuffer td_temp = new StringBuffer();
				Integer tmid = new Integer(((Hashtable) getZhengZuanglist
						.get(i)).get("t_symptom_ID").toString());
				tr.append("<tr  onMouseOver='getColor(this)' onMouseOut='setColor(this)' align=center align=center><td>"
						+ ((Hashtable) getZhengZuanglist.get(i))
								.get("symptomName")+":"
						+ ((Hashtable) getZhengZuanglist.get(i)).get("sy_text")
						+ "</td>");
				for (int y = currentPage - 1; y < getTimelist.size(); y++) {
					Integer pt_id = ((Ptsymptomst) getTimelist.get(y))
							.getPt_id();
					getStatu = zDao.findZhengZuang(caseId, pt_id, tmid);// (����id,�Ǽ�id,��֢id)
					if (null == getStatu) {
						getStatu = "&nbsp";
					}
					td_temp.append("<td>" + getStatu + "</td>");
				}
				tr.append(td_temp);
				tr.append("</tr>");
			}
			list.add(td_time.toString());
			list.add(tr.toString());
			list.add(currentPage);
			}else{
				list.add("<td></td>");
				list.add("<tr><td>没有记录</td></tr>");
				list.add(1);
			}
			String data = JSONArray.fromObject(list).toString();
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return null;
	}
}
