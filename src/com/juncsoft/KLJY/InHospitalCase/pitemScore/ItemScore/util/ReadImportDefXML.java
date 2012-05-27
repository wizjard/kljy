package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.juncsoft.KLJY.InHospitalCase.pitemScore.hisInport.entity.Field;
import com.juncsoft.KLJY.InHospitalCase.pitemScore.hisInport.entity.Table;

public class ReadImportDefXML {
	private static Document doc;
	static {
		SAXBuilder builder = new SAXBuilder();
		try {
			InputStream is = ReadImportDefXML.class
					.getClassLoader()
					.getResourceAsStream(
							"com/juncsoft/KLJY/InHospitalCase/xieliang/ItemScore/util/importDef.xml");
			doc = builder.build(is);
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取配置文件
	 * 
	 * @return Table的集合
	 */
	public static List<Table> GetConfigDef() {
		List<Table> rst = new ArrayList<Table>();
		Element root = doc.getRootElement();
		List tables = root.getChildren("TABLE");
		Iterator tablesit = tables.iterator();
		Table table;
		while (tablesit.hasNext()) {
			table = new Table();
			Element tableRoot = (Element) tablesit.next();

			table.setTableName(tableRoot.getAttributeValue("name"));
			table.setTableCode(tableRoot.getAttributeValue("code"));
			table.setSourceTableCode(tableRoot.getAttributeValue("SourceCode"));

			Element keyElement = tableRoot.getChild("KEY_FIELD");
			if (keyElement != null) {
				table.setKeyName(keyElement.getAttributeValue("name"));
				table.setKeyCode(keyElement.getAttributeValue("code"));
				table.setKeyType(keyElement.getAttributeValue("type"));
				table.setSourceKeyCode(keyElement
						.getAttributeValue("SourceCode"));
				table.setKeySize(Integer.parseInt(keyElement
						.getAttributeValue("size")));
			}

			Element del = tableRoot.getChild("DEL_CONTROL");
			if (del != null) {
				table.setDelControl(del.getAttributeValue("SqlText"));
			}

			List fields = tableRoot.getChild("OTHERFIELDS")
					.getChildren("FIELD");
			Iterator fieldsit = fields.iterator();
			while (fieldsit.hasNext()) {

				Element field = (Element) fieldsit.next();
				Field f = new Field();
				f.setName(field.getAttributeValue("name"));
				f.setCode(field.getAttributeValue("code"));
				f.setType(field.getAttributeValue("type"));
				f.setSize(Integer.parseInt(field.getAttributeValue("size")));
				f.setSourceFieldCode(field.getAttributeValue("SourceCode"));
				table.addField(f);
			}

			rst.add(table);
		}
		return rst;
	}

	/**
	 * 根据表名返回配置文件中的表定义
	 */
	public static Table getTable(String tableCode) {
		List<Table> tables = GetConfigDef();
		Table table = null;
		for (Table t : tables) {
			if (tableCode.equalsIgnoreCase(t.getTableCode())) {
				table = t;
			}
		}
		return table;

	}

	public static void main(String[] args) {
		System.out.println(getTable("ZD_MeasureUnit").getKeyName());
	}
}
