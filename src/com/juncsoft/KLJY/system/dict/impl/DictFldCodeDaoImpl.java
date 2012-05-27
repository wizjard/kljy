package com.juncsoft.KLJY.system.dict.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.system.dict.dao.DictFldCodeDao;
import com.juncsoft.KLJY.system.dict.entity.DictFldcode;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class DictFldCodeDaoImpl implements DictFldCodeDao {

	@SuppressWarnings("unchecked")
	public List<DictFldcode> findByModuleId(Long moduleId) throws Exception {
		List<DictFldcode> list = new ArrayList<DictFldcode>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery(
					"from DictFldcode where moduleId=? order by code").setLong(
					0, moduleId).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public void delete(List<DictFldcode> codes) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			for (DictFldcode code : codes) {
				session.createQuery("delete from DictItem where fldCodeId=?")
						.setLong(0, code.getId()).executeUpdate();
				session.delete(code);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

}
