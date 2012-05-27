package com.juncsoft.KLJY.system.module.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.juncsoft.KLJY.system.module.dao.SysModuleDao;
import com.juncsoft.KLJY.system.module.entity.SysModule;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class SysModuleDaoImpl implements SysModuleDao {

	@SuppressWarnings("unchecked")
	public List<SysModule> getBigModules() throws Exception {
		List<SysModule> list = new ArrayList<SysModule>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(SysModule.class).add(
					Restrictions.or(Restrictions.isNull("parentModuleCode"),
							Restrictions.eq("parentModuleCode", ""))).addOrder(
					Order.asc("orderNo")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<SysModule> getChildModules(String parentModuleCode)
			throws Exception {
		List<SysModule> list = new ArrayList<SysModule>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(SysModule.class).add(
					Restrictions.eq("parentModuleCode", parentModuleCode))
					.addOrder(Order.asc("orderNo")).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public JSONArray getJSONModules() throws Exception {
		JSONArray array = new JSONArray();
		try {
			List<SysModule> modules1 = this.getBigModules();
			for (SysModule module1 : modules1) {
				JSONObject object = new JSONObject();
				object.put("title", module1.getModuleName());
				object.put("code", module1.getModuleCode());
				JSONArray children = new JSONArray();
				for (SysModule module : this.getChildModules(module1
						.getModuleCode())) {
					JSONObject child = new JSONObject();
					child.put("title", module.getModuleName());
					child.put("code", module.getModuleCode());
					child.put("icon", module.getIcon());
					child.put("action", module.getOnClick());
					children.add(child);
				}
				object.put("children", children);
				array.add(object);
			}
		} catch (Exception e) {
			throw e;
		}
		return array;
	}

}
