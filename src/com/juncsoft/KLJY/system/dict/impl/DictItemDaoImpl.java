package com.juncsoft.KLJY.system.dict.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.juncsoft.KLJY.system.dict.dao.DictItemDao;
import com.juncsoft.KLJY.system.dict.entity.DictItem;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class DictItemDaoImpl implements DictItemDao {

	@SuppressWarnings("unchecked")
	public List<DictItem> findByIndexId(Long indexId) throws Exception {
		List<DictItem> list = new ArrayList<DictItem>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery(
					"from DictItem where fldCodeId=? order by orderNo")
					.setLong(0, indexId).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public void delete(List<DictItem> items) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			for (DictItem item : items) {
				session.delete(item);
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
