package com.juncsoft.KLJY.system.dict.entity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XMLTableUtil {
	@SuppressWarnings("unchecked")
	public List<XMLTable> findAll() {
		List<XMLTable> rst = new ArrayList<XMLTable>();
		SAXBuilder builder = new SAXBuilder();
		InputStream is = XMLTableUtil.class.getClassLoader()
				.getResourceAsStream("com/juncsoft/KLJY/config/SYS_ST_cfg.xml");
		try {
			Document doc = builder.build(is);
			Element root = doc.getRootElement();
			List tables = root.getChildren("Tables");
			Iterator tablesIt = tables.iterator();
			while (tablesIt.hasNext()) {
				XMLTable xmlTable = new XMLTable();
				Element table = (Element) tablesIt.next();
				xmlTable.setTableName(table.getAttributeValue("name"));
				xmlTable.setTableCode(table.getAttributeValue("code"));
				Element keyElement = table.getChild("key");
				xmlTable.setKeyCode(keyElement.getAttributeValue("code"));
				xmlTable.setKeyType(keyElement.getAttributeValue("type"));
				List columns = table.getChild("columns").getChildren();
				Iterator columnIt = columns.iterator();
				while (columnIt.hasNext()) {
					XMLTableColumn xmlColumn = new XMLTableColumn();
					Element column = (Element) columnIt.next();
					xmlColumn.setCode(column.getAttributeValue("code"));
					xmlColumn.setName(column.getAttributeValue("name"));
					xmlColumn.setType(column.getAttributeValue("type"));
					xmlTable.getColumns().add(xmlColumn);
				}
				rst.add(xmlTable);
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return rst;
	}
}
