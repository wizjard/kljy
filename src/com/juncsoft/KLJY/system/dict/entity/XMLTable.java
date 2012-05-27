package com.juncsoft.KLJY.system.dict.entity;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.juncsoft.KLJY.util.DatabaseUtil;

public class XMLTable {
	private String tableName;
	private String tableCode;
	private String keyCode;
	private String keyType;
	private List<XMLTableColumn> columns;

	public XMLTable() {

	}

	public XMLTable(String tableCode) {
		this.tableCode = tableCode;
		columns = new ArrayList<XMLTableColumn>();
		init();
	}

	@SuppressWarnings("unchecked")
	private void init() {
		SAXBuilder builder = new SAXBuilder();
		InputStream is = XMLTableUtil.class.getClassLoader()
				.getResourceAsStream("com/juncsoft/KLJY/config/SYS_ST_cfg.xml");
		try {
			Document doc = builder.build(is);
			Element root = doc.getRootElement();
			List tables = root.getChildren("table");
			Iterator tablesIt = tables.iterator();
			while (tablesIt.hasNext()) {
				Element table = (Element) tablesIt.next();
				String code = table.getAttributeValue("code");
				if (!code.equalsIgnoreCase(this.tableCode))
					continue;
				this.setTableName(table.getAttributeValue("name"));
				this.setTableCode(table.getAttributeValue("code"));
				Element keyElement = table.getChild("key");
				this.setKeyCode(keyElement.getAttributeValue("code"));
				this.setKeyType(keyElement.getAttributeValue("type"));
				List columns = table.getChild("columns").getChildren();
				Iterator columnIt = columns.iterator();
				while (columnIt.hasNext()) {
					XMLTableColumn xmlColumn = new XMLTableColumn();
					Element column = (Element) columnIt.next();
					xmlColumn.setCode(column.getAttributeValue("code"));
					xmlColumn.setName(column.getAttributeValue("name"));
					xmlColumn.setType(column.getAttributeValue("type"));
					this.getColumns().add(xmlColumn);
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	@SuppressWarnings("unchecked")
	public String getTableNodes() {
		String rst = "";
		JSONArray arr = new JSONArray();
		SAXBuilder builder = new SAXBuilder();
		InputStream is = XMLTableUtil.class.getClassLoader()
				.getResourceAsStream("com/juncsoft/KLJY/config/SYS_ST_cfg.xml");
		try {
			Document doc = builder.build(is);
			Element root = doc.getRootElement();
			List tables = root.getChildren("table");
			Iterator tablesIt = tables.iterator();
			while (tablesIt.hasNext()) {
				JSONObject json = new JSONObject();
				Element table = (Element) tablesIt.next();
				json.put("text", table.getAttributeValue("name"));
				json.put("id", table.getAttributeValue("code"));
				json.put("leaf", true);
				arr.add(json);
			}
			rst = arr.toString();
		} catch (JDOMException e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return rst;
	}

	public void saveOrUpdate(JSONArray rows) throws Exception {
		Connection conn = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			for (Object object : rows) {
				String sql = "";
				JSONObject json = JSONObject.fromObject(object);
				Integer id = Integer.parseInt(json.getString("ID"));
				String fields = "";
				String qs = "";
				for (XMLTableColumn column : this.columns) {
					if (id == null || id == -1) {
						fields += column.getCode() + ",";
						qs += "?,";
					} else {
						fields += column.getCode() + "=?,";
					}
				}
				if (fields.length() > 0)
					fields = fields.substring(0, fields.length() - 1);
				if (qs.length() > 0)
					qs = qs.substring(0, qs.length() - 1);
				if (id == null || id == -1) {
					sql = "insert into " + this.tableCode + "(" + fields
							+ ") values(" + qs + ");";
				} else {
					sql = "update " + this.tableCode + " set " + fields
							+ " where ID=?;";
				}
				sm = conn.prepareStatement(sql);
				for (int i = 1, len = this.columns.size(); i <= len; i++) {
					XMLTableColumn column = this.columns.get(i - 1);
					String type = column.getType();
					String code = column.getCode();
					String value = json.getString(code);
					if (type.equalsIgnoreCase("int")) {
						if (value == null || value.length() == 0)
							sm.setNull(i, Types.INTEGER);
						else
							sm.setInt(i, Integer.parseInt(value));
					} else if (type.equalsIgnoreCase("float")) {
						if (value == null || value.length() == 0)
							sm.setNull(i, Types.FLOAT);
						else
							sm.setFloat(i, Float.parseFloat(value));
					} else if (type.equalsIgnoreCase("date")) {
						String[] dateFormat = new String[] {
								"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
								"yyyy-MM-dd HH", "yyyy-MM-dd" };
						Date date = null;
						for (String str : dateFormat) {
							try {
								date = new SimpleDateFormat(str).parse(value);
								if (date != null)
									break;
							} catch (Exception ee) {
							}
						}
						if (date != null)
							sm.setDate(i, new java.sql.Date(date.getTime()));
						else
							sm.setNull(i, Types.DATE);
					} else {
						if (value == null || value.length() == 0)
							sm.setNull(i, Types.VARCHAR);
						else
							sm.setString(i, value);
					}
				}
				if (id != null && id != -1) {
					sm.setInt(this.columns.size() + 1, id);
				}
				sm.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
	}

	public JSONObject getRowValues(Integer start, Integer limit)
			throws SQLException {
		JSONObject json = new JSONObject();
		Connection conn = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			String selectSql = "select top " + limit + " * from "
					+ this.tableCode + " where ID not in(select top " + start
					+ " ID from " + this.tableCode + " order by ID)order by ID";
			String totalSql = "select count(*) from " + this.tableCode;
			conn = DatabaseUtil.getConnection();
			sm = conn.createStatement();
			rs = sm.executeQuery(totalSql);
			if (rs.next()) {
				json.put("total", rs.getLong(1));
			}
			sm = conn.createStatement();
			rs = sm.executeQuery(selectSql);
			JSONArray array = new JSONArray();
			while (rs.next()) {
				JSONObject row = new JSONObject();
				row.put("ID", rs.getString("ID"));
				for (XMLTableColumn column : this.columns) {
					String code = column.getCode();
					String type = column.getType();
					Object value = "";
					if (type.equalsIgnoreCase("int")) {
						value = rs.getInt(code);
					} else if (type.equalsIgnoreCase("float")) {
						value = rs.getInt(code);
					} else if (type.equalsIgnoreCase("date")) {
						value = rs.getDate(code);
					} else {
						value = rs.getString(code);
					}
					row.put(code, value);
				}
				array.add(row);
			}
			json.put("root", array);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.closeResource(conn, sm, rs);
		}
		return json;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public List<XMLTableColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<XMLTableColumn> columns) {
		this.columns = columns;
	}
}
