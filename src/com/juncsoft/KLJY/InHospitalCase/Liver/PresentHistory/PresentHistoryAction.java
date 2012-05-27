package com.juncsoft.KLJY.InHospitalCase.Liver.PresentHistory;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class PresentHistoryAction extends DispatchAction {
	private PresentHistoryService service = new PresentHistoryService();

	public ActionForward findContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long key = Long.parseLong(request.getParameter("caseId"));
			PresentIllnessHistoryN n = service.findContent(key);
			res.setData(JSONObject.fromObject(
					n,
					JSONValueProcessor.cycleExcludel(
							new String[] { "n", "nx" }, "")).toString());
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

	public ActionForward saveOrUpdateContent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			String data = request.getParameter("data");
			JSONObject object = JSONObject.fromObject(data);
			PresentIllnessHistoryN n = (PresentIllnessHistoryN) JSONObject
					.toBean(object, PresentIllnessHistoryN.class);
			List<PresentIllnessHistoryNx> nxs = new ArrayList<PresentIllnessHistoryNx>();
			for (Object obj : object.getJSONArray("nxs")) {
				JSONObject thisObj = JSONObject.fromObject(obj);
				PresentIllnessHistoryNx nx = (PresentIllnessHistoryNx) JSONObject
						.toBean(thisObj, PresentIllnessHistoryNx.class);
				if (nx.getId() == -1)
					nx.setId(null);
				nx.setN(n);
				if (thisObj.has("grids")) {
					List<TreatGrid> tgs = new ArrayList<TreatGrid>();
					for (Object o : thisObj.getJSONArray("grids")) {
						TreatGrid grid = (TreatGrid) JSONObject.toBean(
								JSONObject.fromObject(o), TreatGrid.class);
						if (grid.getId() == -1)
							grid.setId(null);
						grid.setNx(nx);
						tgs.add(grid);
					}
					nx.setGrids(tgs);
				}
				nxs.add(nx);
			}
			n.setNxs(nxs);
			Long id = service.saveOrUpdateContent(n);
			res.setData(id.toString());
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

	public ActionForward deletePresentIllnessHistoryNx(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			service.deletePresentIllnessHistoryNx(id);
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

	public ActionForward deleteTreatGrid(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("id"));
			service.deleteTreatGrid(id);
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

	public ActionForward viewOldPre(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		Response res = new Response();
		try {
			Long id = Long.parseLong(request.getParameter("KID"));
			res.setData(service.viewOldPre(id));
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
