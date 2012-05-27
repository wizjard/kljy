package com.juncsoft.KLJY.system.dict.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.juncsoft.KLJY.system.dict.dao.DictModuleDao;
import com.juncsoft.KLJY.system.dict.entity.DictFldcode;
import com.juncsoft.KLJY.system.dict.entity.DictFldcodeEx;
import com.juncsoft.KLJY.system.dict.entity.DictItem;
import com.juncsoft.KLJY.system.dict.entity.DictItemEx;
import com.juncsoft.KLJY.system.dict.entity.DictModule;
import com.juncsoft.KLJY.system.dict.entity.DictModuleEx;
import com.juncsoft.KLJY.system.dict.entity.TreeNode;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class DictModuleDaoImpl implements DictModuleDao {

	@SuppressWarnings("unchecked")
	public List<DictModule> queryAllModules() throws Exception {
		List<DictModule> list = null;
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createQuery("from DictModule order by id").list();
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
	public String queryAllModulesExtTree() throws Exception {
		String rst = "";
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<DictModule> list = session.createQuery(
					"from DictModule where pid=-1").list();
			if (list.size() > 0) {
				for (DictModule module : list) {
					TreeNode node = new TreeNode();
					node.setId(module.getId().toString());
					node.setText(module.getName());
					iteratorTreeNode(node);
					nodes.add(node);
				}
				rst = JSONArray.fromObject(nodes).toString();
			} else {
				rst = "[]";
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return rst;
	}

	@SuppressWarnings("unchecked")
	private void iteratorTreeNode(TreeNode node) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			String sql = "from DictModule where pid=" + node.getId();
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<DictModule> list = session.createQuery(sql).list();
			if (list.size() > 0) {
				node.setLeaf(false);
				for (DictModule module : list) {
					TreeNode n = new TreeNode();
					n.setId(module.getId().toString());
					n.setText(module.getName());
					iteratorTreeNode(n);
					node.getChildren().add(n);
				}
			} else {
				node.setLeaf(true);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	public void treeModule_delete(Long id) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			DictModule module = (DictModule) DatabaseUtil.findById(
					DictModule.class, id);
			String sql = "";
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			// step1:删除当前节点
			session.delete(module);
			treeModule_deleteEx(module, session, tran);
			// step2:选出此节点的子节点，循环删除
			sql = "from DictModule where pid=?";
			List<DictModule> modules = session.createQuery(sql).setLong(0, id)
					.list();
			for (DictModule subModule : modules) {
				session.delete(subModule);
				treeModule_deleteEx(subModule, session, tran);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	private void treeModule_deleteEx(DictModule module, Session session,
			Transaction tran) throws Exception {
		try {
			List<DictFldcode> codes = session.createQuery(
					"from DictFldcode where moduleId=?").setLong(0,
					module.getId()).list();
			new DictFldCodeDaoImpl().delete(codes);
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public void copyNode2Node(Long oldId, Long newId) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<DictFldcode> codes = session.createQuery(
					"from DictFldcode where moduleId=?").setLong(0, oldId)
					.list();
			for (DictFldcode code : codes) {
				DictFldcode newCode = new DictFldcode();
				newCode.setModuleId(newId);
				newCode.setCode(code.getCode());
				newCode.setComment(code.getComment());
				newCode.setName(code.getName());
				newCode.setId((Long) session.save(newCode));
				copyNode2NodeEx(code, newCode, session);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	private void copyNode2NodeEx(DictFldcode oldCode, DictFldcode newCode,
			Session session) throws Exception {
		try {
			List<DictItem> items = session.createQuery(
					"from DictItem where fldCodeId=?").setLong(0,
					oldCode.getId()).list();
			for (DictItem item : items) {
				DictItem newItem = new DictItem();
				newItem.setFldCodeId(newCode.getId());
				newItem.setComment(item.getComment());
				newItem.setOrderNo(item.getOrderNo());
				newItem.setText(item.getText());
				newItem.setValue(item.getValue());
				session.save(newItem);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void exportNode(String path, Long id) throws Exception {
		DictModuleEx module = new DictModuleEx();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			module = (DictModuleEx) session.get(DictModuleEx.class, id);
			exportNode_module(session, module);
			exportNode_fldCode_item(session, module);
			writeObject(module, path);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	@SuppressWarnings("unchecked")
	private void exportNode_module(Session session, DictModuleEx module)
			throws Exception {
		try {
			List<DictModuleEx> ex = session.createQuery(
					"from DictModuleEx where pid=?").setLong(0, module.getId())
					.list();
			module.setChildrens(ex);
			for (DictModuleEx m : ex) {
				exportNode_module(session, m);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	private void exportNode_fldCode_item(Session session, DictModuleEx module)
			throws Exception {
		try {
			List<DictFldcodeEx> fldCodes = session.createQuery(
					"from DictFldcodeEx where moduleId=?").setLong(0,
					module.getId()).list();
			for (DictFldcodeEx code : fldCodes) {
				List<DictItemEx> items = session.createQuery(
						"from DictItemEx where fldCodeId=?").setLong(0,
						code.getId()).list();
				code.setItems(items);
			}
			module.setFldCodes(fldCodes);
			for (DictModuleEx child : module.getChildrens()) {
				exportNode_fldCode_item(session, child);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void writeObject(DictModuleEx module, String path1)
			throws Exception {
		String path = "";
		path = this.getClass().getClassLoader().getResource("").getPath();
		path += "com/juncsoft/KLJY/system/dict/resources/" + path1;
		try {
			FileOutputStream serialOut = new FileOutputStream(path);
			ObjectOutputStream objectOut = new ObjectOutputStream(serialOut);
			objectOut.writeObject(module);
			objectOut.close();
			serialOut.close();
		} catch (Exception e) {
			throw e;
		}
	}

	public static void main(String[] args) {
		DictModuleDaoImpl impl = new DictModuleDaoImpl();
		try {
			impl.exportNode("node.ser", new Long(9));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void importNode(String path1, Long pid) throws Exception {
		Session session = null;
		Transaction tran = null;
		String path = "";
		path = this.getClass().getClassLoader().getResource("").getPath();
		path += "com/juncsoft/KLJY/system/dict/resources/" + path1;
		try {
			File serialFile = new File(path);
			FileInputStream serialIn = new FileInputStream(serialFile);
			ObjectInputStream objectIn = new ObjectInputStream(serialIn);
			DictModuleEx module = (DictModuleEx) objectIn.readObject();
			objectIn.close();
			serialIn.close();
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			List<DictModuleEx> list = session.createQuery(
					"from DictModuleEx where code=?").setString(0,
					module.getCode()).list();
			if (list.size() > 0) {
				DictModuleEx old = list.get(0);
				this.treeModule_delete(old.getId());
			}
			importNode_module(session, module, pid);
			tran.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	private void importNode_module(Session session, DictModuleEx module,
			Long pid) throws Exception {
		try {
			module.setPid(pid);
			Long id = (Long) session.save(module);
			List<DictFldcodeEx> fldCodes = module.getFldCodes();
			for (DictFldcodeEx fldCode : fldCodes) {
				fldCode.setModuleId(id);
				Long codeid = (Long) session.save(fldCode);
				List<DictItemEx> items = fldCode.getItems();
				for (DictItemEx item : items) {
					item.setFldCodeId(codeid);
					session.save(item);
				}
			}
			List<DictModuleEx> children = module.getChildrens();
			for (DictModuleEx child : children) {
				importNode_module(session, child, id);
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
