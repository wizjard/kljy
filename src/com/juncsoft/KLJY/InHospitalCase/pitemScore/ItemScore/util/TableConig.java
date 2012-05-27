package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Field;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.entity.Table;

public class TableConig {
	private static Document doc;
	static {
		SAXBuilder builder = new SAXBuilder();
		try {
			InputStream is = ReadImportDefXML.class
					.getClassLoader()
					.getResourceAsStream(
							"com/juncsoft/KLJY/InHospitalCase/pitemScore/ItemScore/util/tableConfig.xml");
			doc = builder.build(is);
			is.close();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param tableCode
	 *            relTable
	 * @return
	 */
	public static Table getTableConfig(String tableCode) {
		Table table = null;
		Element root = doc.getRootElement();
		List tables = root.getChildren("Table");
		Iterator it = tables.iterator();
		while (it.hasNext()) {
			Element tableRoot = (Element) it.next();
			String code = tableRoot.getAttributeValue("code");
			if (code.equalsIgnoreCase(tableCode)) {
				table = getSingleTable(tableRoot);
			} else {
				continue;
			}
		}
		return table;
	}

	private static Table getSingleTable(Element tableRoot) {
		Table table = new Table();
		String tableCode = tableRoot.getAttributeValue("code");
		table.setTableCode(tableCode);
		Element keyFieldEl = tableRoot.getChild("KeyField");
		String keyFieldCode = keyFieldEl.getAttributeValue("code");
		String keyFieldType = keyFieldEl.getAttributeValue("type");
		table.setKeyField(keyFieldCode);
		table.setKeyFieldType(keyFieldType);
		List fieldsList = tableRoot.getChildren("Field");
		Iterator it = fieldsList.iterator();
		while (it.hasNext()) {
			Element fieldEl = (Element) it.next();
			Field fld = new Field();
			String fldCode = fieldEl.getAttributeValue("code");
			String fldType = fieldEl.getAttributeValue("type");
			fld.setFieldCode(fldCode);
			fld.setFieldType(fldType);
			table.addField(fld);
		}
		return table;
	}

	public static void createTable(String tableCode) {
		Element root = doc.getRootElement();
		List tables = root.getChildren("Table");
		Iterator it = tables.iterator();
		String sql = "";
		while (it.hasNext()) {
			Element tableRoot = (Element) it.next();
			String code = tableRoot.getAttributeValue("code");
			if (code.equalsIgnoreCase(tableCode)) {
				List fieldsList = tableRoot.getChildren("Field");
				Iterator its = fieldsList.iterator();
				while (its.hasNext()) {
					Element fieldEl = (Element) its.next();
					String fldCode = fieldEl.getAttributeValue("code");
					String fldType = fieldEl.getAttributeValue("type");
					String size = fieldEl.getAttributeValue("size");
					if (fldType.equalsIgnoreCase("int")) {
						sql += "alter table " + tableCode + " add " + fldCode
								+ " int;";
					} else if (fldType.equalsIgnoreCase("string")) {
						if (size == null) {
							sql += "alter table " + tableCode + " add "
									+ fldCode + " varchar(50);";
						} else {
							sql += "alter table " + tableCode + " add "
									+ fldCode + " varchar(" + size + ");";
						}

					} else if (fldType.equalsIgnoreCase("date")) {
						sql += "alter table " + tableCode + " add " + fldCode
								+ " datetime;";
					} else if (fldType.equalsIgnoreCase("float")) {
						sql += "alter table " + tableCode + " add " + fldCode
								+ " float;";
					}
				}
			} else {
				continue;
			}
		}
		System.out.println(sql);
	}

	public static void main(String[] args) {
		createTable("ItemScore_LFunction");
	}
}
