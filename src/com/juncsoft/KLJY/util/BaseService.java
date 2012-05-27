package com.juncsoft.KLJY.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.juncsoft.KLJY.util.DatabaseUtil;

/**
 * 公共的底层方法，用于基本的增删改查业务
 * @author liugang
 *
 */
public class BaseService {
	
	/**
	 * 新增公用方法
	 * @param obj
	 * @return
	 */
	public Object saveObject(Object obj){
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.save(obj);
			tran.commit();
		} catch (Exception e) {
			if(null != tran)
			{
				tran.rollback();
			}
			throw new RuntimeException("新增方法出错",e);
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return obj;
	}
	
	/**
	 * 删除公用方法
	 * @param listId
	 */
	public void deleteObject(List listId){
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			for(int i=0,size=listId.size();i<size;i++)
			{
				Object obj = session.load(Object.class, new Integer(listId.get(i).toString()));
				session.delete(obj);
			}
			tran.commit();
		} catch (Exception e) {
			if(null != tran)
			{
				tran.rollback();
			}
			throw new RuntimeException("删除方法出错", e);
		}finally{
			DatabaseUtil.closeResource(session);
		}
	}
	
	/**
	 * 更新公用方法
	 * @param obj
	 * @return
	 */
	public Object updateObject(Object obj){
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.merge(obj);
			tran.commit();
		} catch (Exception e) {
			if(null != tran)
			{
				tran.rollback();
			}
			throw new RuntimeException("更新方法出错", e);
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return obj;
	}
	
	public List findAllObject(String hql){
		Session session = null;
		Transaction tran = null;
		List list = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery(hql).list();
			tran.commit();
		} catch (Exception e) {
			if(tran != null)
			{
				tran.rollback();
			}
			throw new RuntimeException("查找列表出错", e);
		}finally{
			DatabaseUtil.closeResource(session);
		}
		return list;
	}
}
