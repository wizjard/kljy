package com.juncsoft.KLJY.system.MemberGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.juncsoft.KLJY.util.DatabaseUtil;

public class MPService {
	private final String tableDefin = "SYS_MemberGroup_TableFldDefin";
	private final String table = "SYS_MemberGroup_Table";
	private final String treeFld = "SYS_MemberGroup_TreeFld_Rel";

	public JSONArray td_findAllFlds() throws Exception {
		JSONArray root = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + this.tableDefin + " order by id";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject object = new JSONObject();
				object.put("id", rs.getLong("id"));
				object.put("fldType", rs.getString("fldType"));
				object.put("fldName", rs.getString("fldName"));
				object.put("fldCode", rs.getString("fldCode"));
				object.put("strLen", rs.getInt("strLen"));
				object.put("dateFormat", rs.getString("dateFormat"));
				object.put("comment", rs.getString("comment"));
				object.put("allowEdit", rs.getInt("allowEdit"));
				root.add(object);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return root;
	}

	public void td_saveOrUpdate(JSONObject object) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Long id = Long.parseLong(object.getString("id"));
			String fldType = object.getString("fldType");
			String fldName = object.getString("fldName");
			String fldCode = object.getString("fldCode");
			String dateFormat = object.getString("dateFormat");
			String comment = object.getString("comment");
			int allowEdit = Integer.parseInt(object.getString("allowEdit"));
			int strLen = 0;
			try {
				strLen = Integer.parseInt(object.getString("strLen"));
			} catch (Exception e) {
			}
			String sql = "";
			String alterSql = "";
			if (id == -1) {
				fldCode = "s_" + new Date().getTime()
						+ (int) Math.ceil(Math.random() * 10000);
				sql = "insert into "
						+ this.tableDefin
						+ "(fldType,fldName,fldCode,strLen,dateFormat,comment,allowEdit)values(?,?,?,?,?,?,?)";
				alterSql = "ALTER TABLE " + this.table + " ADD " + fldCode;
			} else {
				sql = "update "
						+ this.tableDefin
						+ " set fldType=?,fldName=?,fldCode=?,strLen=?,dateFormat=?,comment=?,allowEdit=? where id="
						+ id;
				alterSql = "ALTER TABLE " + this.table + " ALTER COLUMN "
						+ fldCode;
			}
			if (fldType.equalsIgnoreCase("int")) {
				alterSql += " int;";
			} else if (fldType.equalsIgnoreCase("varchar")) {
				alterSql += " varchar(" + strLen + ");";
			} else if (fldType.equalsIgnoreCase("float")) {
				alterSql += " float;";
			} else if (fldType.equalsIgnoreCase("datetime")) {
				alterSql += " datetime;";
			} else if (fldType.equalsIgnoreCase("ntext")) {
				alterSql += " ntext;";
			}
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, fldType);
			ps.setString(2, fldName);
			ps.setString(3, fldCode);
			ps.setInt(4, strLen);
			ps.setString(5, dateFormat);
			ps.setString(6, comment);
			ps.setInt(7, allowEdit);
			ps.executeUpdate();
			System.out.println(alterSql);
			ps = conn.prepareStatement(alterSql);
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
	}

	public void td_delete(String id) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select fldCode from " + this.tableDefin
					+ " where id in(" + id + ");";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<String> fldCode = new Vector<String>();
			while (rs.next()) {
				fldCode.add(rs.getString(1));
			}
			sql = "delete from " + this.tableDefin + " where id in(" + id + ")";
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			sql = "";
			for (String str : fldCode) {
				sql += "ALTER TABLE " + this.table + " DROP COLUMN " + str
						+ ";";
			}
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
	}

	public JSONArray tree_findAll() throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select id,name,orderNo,pid from SYS_MemberGroup_Tree where pid=-1 order by orderNo";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject object = new JSONObject();
				Long id = rs.getLong(1);
				object.put("id", id + ">" + rs.getInt(3) + ">" + rs.getInt(4));
				object.put("text", rs.getString(2));
				object.put("leaf", true);
				// object.put("children", tree_findAll_children(id));
				array.add(object);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return array;
	}

	public JSONArray tree_saveOrUpdate(Long id, Long pid, String name,
			int orderNo) throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "";
			if (id == -1) {
				sql = "insert into SYS_MemberGroup_Tree(pid,name,orderNo)values(?,?,?)";
			} else {
				sql = "update SYS_MemberGroup_Tree set pid=?,name=?,orderNo=? where id="
						+ id;
			}
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, pid);
			ps.setString(2, name);
			ps.setInt(3, orderNo);
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return array;
	}

	public JSONArray treeFld_findAll(Long treeId) throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select a.id,b.fldName,b.fldType,a.orderNo,a.comment,b.allowEdit,a.fldId,b.fldCode,b.dateFormat from "
					+ this.treeFld
					+ " as a left outer join "
					+ this.tableDefin
					+ " as b on a.fldId=b.id where a.treeId="
					+ treeId
					+ " order by orderNo";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject object = new JSONObject();
				object.put("id", rs.getLong(1));
				object.put("fldName", rs.getString(2));
				object.put("fldType", rs.getString(3));
				object.put("orderNo", rs.getInt(4));
				object.put("comment", rs.getString(5));
				object.put("allowEdit", rs.getInt(6));
				object.put("fldId", rs.getLong(7));
				object.put("fldCode", rs.getString(8));
				object.put("dateFormat", rs.getString(9));
				array.add(object);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return array;
	}

	public void treeFld_saveOrUpdate(JSONObject object) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Long id = Long.parseLong(object.getString("id"));
			Long treeId = Long.parseLong(object.getString("treeId"));
			Long fldId = Long.parseLong(object.getString("fldId"));
			Integer orderNo = 0;
			try {
				orderNo = Integer.parseInt(object.getString("orderNo"));
			} catch (Exception e) {
			}
			String comment = object.getString("comment");
			String sql = "";
			if (id == -1) {
				sql = "insert into " + this.treeFld
						+ "(treeId,fldId,orderNo,comment)values(?,?,?,?)";
			} else {
				sql = "update " + this.treeFld
						+ " set treeId=?,fldId=?,orderNo=?,comment=? where id="
						+ id;
			}
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, treeId);
			ps.setLong(2, fldId);
			ps.setInt(3, orderNo);
			ps.setString(4, comment);
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, null);
		}
	}

	public void treeFld_delete(String id) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement("delete from " + this.treeFld
					+ " where id in(" + id + ")");
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, null);
		}
	}

	@SuppressWarnings("unused")
	private JSONArray tree_findAll_children(Long id) throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select id,name,orderNo,pid from SYS_MemberGroup_Tree where pid="
					+ id + " order by orderNo";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject object = new JSONObject();
				object.put("id", rs.getLong(1) + ">" + rs.getInt(3) + ">"
						+ rs.getInt(4));
				object.put("text", rs.getString(2));
				object.put("leaf", true);
				array.add(object);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return array;
	}

	public void tree_delete(Long id) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			ps = conn
					.prepareStatement("delete from SYS_MemberGroup_Tree where id=? or pid=?");
			ps.setLong(1, id);
			ps.setLong(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
	}

	public JSONArray content_findAll(JSONArray fldCfg, Long treeId)
			throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<String[]> fc = new ArrayList<String[]>();
			for (int i = 0, len = fldCfg.size(); i < len; i++) {
				String[] str = new String[3];
				str[0] = fldCfg.getJSONObject(i).getString("fldCode");
				str[1] = fldCfg.getJSONObject(i).getString("fldType");
				try {
					str[2] = fldCfg.getJSONObject(i).getString("dateFormat");
				} catch (Exception ee) {
					str[2] = "yyyy-MM-dd";
				}
				fc.add(str);
			}
			String sql = "select * from " + this.table
					+ " where treeId=? order by id";
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, treeId);
			rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject object = new JSONObject();
				object.put("id", rs.getLong("id"));
				object.put("treeId", treeId);
				for (String[] cfg : fc) {
					if (cfg[0].equalsIgnoreCase("int")) {
						object.put(cfg[0], rs.getInt(cfg[0]));
					} else if (cfg[0].equalsIgnoreCase("float")) {
						object.put(cfg[0], rs.getFloat(cfg[0]));
					} else if (cfg[0].equalsIgnoreCase("datetime")) {
						Date date = rs.getDate(cfg[0]);
						if (date != null) {
							object.put(cfg[0], new SimpleDateFormat(cfg[2])
									.format(date));
						} else {
							object.put(cfg[0], "");
						}
					} else {
						object.put(cfg[0], rs.getString(cfg[0]));
					}
				}
				array.add(object);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
		return array;
	}

	public void content_saveOrUpdate(JSONObject object, JSONArray fldCfg)
			throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			List<String[]> fc = new ArrayList<String[]>();
			String insertfields = "";
			String insertqs = "";
			String setfields = "";
			for (int i = 0, len = fldCfg.size(); i < len; i++) {
				String[] str = new String[3];
				str[0] = fldCfg.getJSONObject(i).getString("fldCode");
				str[1] = fldCfg.getJSONObject(i).getString("fldType");
				try {
					str[2] = fldCfg.getJSONObject(i).getString("dateFormat");
				} catch (Exception ee) {
					str[2] = "yyyy-MM-dd";
				}
				insertfields += str[0] + ",";
				insertqs += "?,";
				setfields += str[0] + "=?,";
				fc.add(str);
			}
			Long id = Long.parseLong(object.getString("id"));
			Long treeId = Long.parseLong(object.getString("treeId"));
			insertfields += "treeId";
			insertqs += "?";
			setfields += "treeId=?";
			String sql = "";
			if (id == -1) {
				sql = "insert into " + this.table + "(" + insertfields
						+ ")values(" + insertqs + ");";
			} else {
				sql = "update " + this.table + " set " + setfields
						+ " where id=" + id;
			}
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0, len = fc.size(); i < len; i++) {
				if (fc.get(i)[1].equalsIgnoreCase("int")) {
					Integer temp = 0;
					try {
						temp = Integer.parseInt(object.getString(fc.get(i)[0]));
					} catch (Exception ee) {
					}
					ps.setInt(i + 1, temp);
				} else if (fc.get(i)[1].equalsIgnoreCase("float")) {
					Float temp = new Float(0);
					try {
						temp = Float.parseFloat(object.getString(fc.get(i)[0]));
					} catch (Exception ee) {
					}
					ps.setFloat(i + 1, temp);
				} else if (fc.get(i)[1].equalsIgnoreCase("date")) {
					java.sql.Date date = null;
					try {
						date = new java.sql.Date(new SimpleDateFormat(
								fc.get(i)[2]).parse(
								object.getString(fc.get(i)[0])).getTime());
					} catch (Exception ee) {
					}
					ps.setDate(i + 1, date);
				} else {
					ps.setString(i + 1, object.getString(fc.get(i)[0]));
				}
			}
			ps.setLong(fc.size() + 1, treeId);
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, rs);
		}
	}

	public void content_delete(Long id) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "delete from " + this.table + " where id=" + id;
			conn = DatabaseUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, ps, null);
		}
	}

	@SuppressWarnings("unchecked")
	public List<MemberGroupTpl> tpl_findByGroupId(Long id) throws Exception {
		List<MemberGroupTpl> list = new ArrayList<MemberGroupTpl>();
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			list = session.createCriteria(MemberGroupTpl.class).add(
					Restrictions.eq("groupId", id)).list();
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return list;
	}

	public MemberGroupTpl tpl_saveOrUpdate(MemberGroupTpl tpl) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			if (tpl.getId() == null || tpl.getId() < 0) {
				tpl.setId((Long) session.save(tpl));
			} else {
				session.update(tpl);
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
		return tpl;
	}

	public void tpl_delete(MemberGroupTpl tpl) throws Exception {
		Session session = null;
		Transaction tran = null;
		try {
			session = DatabaseUtil.getSession();
			tran = session.beginTransaction();
			session.delete(tpl);
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			throw e;
		} finally {
			DatabaseUtil.closeResource(session);
		}
	}

	public JSONArray MemberGroup_findAll() throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select id,name from SYS_MemberGroup_Tree order by orderNo";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			rs = sm.executeQuery();
			while (rs.next()) {
				JSONObject object = new JSONObject();
				object.put("id", rs.getLong(1));
				object.put("text", rs.getString(2));
				JSONArray fldCfg = this.treeFld_findAll(rs.getLong(1));
				List<String[]> fc = new ArrayList<String[]>();
				for (int i = 0, len = fldCfg.size(); i < len; i++) {
					String[] str = new String[4];
					str[0] = fldCfg.getJSONObject(i).getString("fldCode");
					str[1] = fldCfg.getJSONObject(i).getString("fldType");
					str[3] = fldCfg.getJSONObject(i).getString("fldName");
					try {
						str[2] = fldCfg.getJSONObject(i)
								.getString("dateFormat");
					} catch (Exception ee) {
						str[2] = "yyyy-MM-dd";
					}
					fc.add(str);
				}
				object.put("columns", fc);
				object.put("children", MemberGroup_findAll_subGroup(rs
						.getLong(1), fc));
				array.add(object);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return array;
	}

	private JSONArray MemberGroup_findAll_subGroup(Long treeId,
			List<String[]> fc) throws Exception {
		JSONArray array = new JSONArray();
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			String sql = "select * from SYS_MemberGroup_Table where treeId=? order by id";
			conn = DatabaseUtil.getConnection();
			sm = conn.prepareStatement(sql);
			sm.setLong(1, treeId);
			rs = sm.executeQuery();
			while (rs.next()) {
				JSONObject object = new JSONObject();
				object.put("id", rs.getLong("id"));
				for (String[] str : fc) {
					if (str[1].equalsIgnoreCase("int")) {
						object.put(str[0], rs.getInt(str[0]));
					} else if (str[1].equalsIgnoreCase("float")) {
						object.put(str[0], rs.getFloat(str[0]));
					} else {
						object.put(str[0], rs.getString(str[0]));
					}
				}
				array.add(object);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return array;
	}

	public static void main(String[] args) {
		try {
			System.out
					.println(new MPService().MemberGroup_findAll().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}