package com.juncsoft.KLJY.outoremergency.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.juncsoft.KLJY.outoremergency.dao.OutOrMergencyDoctorDao;
import com.juncsoft.KLJY.outoremergency.entity.OutOrMergencyDoctor;
import com.juncsoft.KLJY.outoremergency.impl.OutOrMergencyDoctorDaoImpl;
import com.juncsoft.KLJY.util.JSONValueProcessor;
import com.juncsoft.KLJY.util.Response;

public class OutOrMergencyDoctorAction extends DispatchAction {
	//liugang 登录医生信息校验
	private OutOrMergencyDoctorDao doctorDao = new OutOrMergencyDoctorDaoImpl();
	
	public ActionForward executeHISDoctorByIdnumberAndPassword(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		Response res = new Response();
		try {
			out = response.getWriter();
			String name = request.getParameter("name");
			OutOrMergencyDoctor outOrMergencyDoctor = doctorDao.executeHISDoctorByIdnumberAndPassword(name);
			res.setData(JSONObject.fromObject(outOrMergencyDoctor,JSONValueProcessor.formatDate("yyyy-MM-dd HH:mm:ss")).toString());
			res.setSuccess(true);
		} catch (IOException e) {
			res.setSuccess(false);
			throw new RuntimeException("调用HIS查找医生信息存储过程出错",e);
		}finally {
			out.println(JSONObject.fromObject(res).toString());
			out.close();
		}
		return null;
	}
}
