package com.juncsoft.KLJY.InHospitalCase.pitemScore.ItemScore.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * 生成Hibernater配置文件类
 * 
 * @author Administrator
 * 
 */
public class GetClassType {
	/**
	 * 生成配置文件
	 * 
	 * @param path
	 *            需生成文件的全称类型 包名+类名
	 * @param tableName
	 *            指定表名
	 * @param id
	 *            指定表中的id列名
	 */
	private void createHibernateXML(String path, String tableName, String id) {
		try {
			Class c = Class.forName(path);
			System.out.println("生成文件的类：" + c);
			Field[] fields = c.getDeclaredFields();
			int count = 0;
			System.out.println("<class name=\"" + c.getSimpleName()
					+ "\" table=\"" + tableName + "\">");
			System.out.println("   <id column=\"" + id + "\" " + "name=\"" + id
					+ "\" >");
			System.out.println("\t<generator class=\"native\"></generator>");
			System.out.println("   </id>");
			for (int i = 0; i < fields.length; i++) {
				String pName = fields[i].getName();
				if (pName == id) {
					continue;
				}
				System.out.println("   <property name=\"" + pName + "\" />");
				count++;
			}
			System.out.println("</class>");
			System.out.println("共计：" + count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成自定义的xml文件
	 * 
	 * @param path
	 * @param tableName
	 */
	private void createField(String path, String tableName, String id) {
		try {
			Class c = Class.forName(path);
			System.out.println("生成XML文件的类：" + c);
			Field[] fields = c.getDeclaredFields();
			int num = -1;
			System.out.println("    <Table code=\"" + tableName + "\">");
			for (int i = 0; i < fields.length; i++) {
				String pName = fields[i].getName();
				Type pType = fields[i].getType();
				String type = pType.toString();
				if (type.lastIndexOf(".") > -1) {
					int start = type.lastIndexOf(".");
					int end = type.length();
					type = type.substring(start + 1, end);
				}
				if (pName == id) {
					System.out.println("         <KeyField code=\"" + id
							+ "\" type=\"" + type + "\" />");
					continue;
				}
				System.out.println("         <Field code=\"" + pName
						+ "\" type=\"" + type + "\" />");
				num++;
			}
			System.out.println("    </Table>\n");

			System.out.println("共执行：" + num + "次！");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String path = "com.juncsoft.KLJY.InHospitalCase.ItemScore.entity.Apacheii";
		String tableName = "t_ItemScore_Apacheii";
		String id = "id";
		GetClassType gc = new GetClassType();
		gc.createHibernateXML(path, tableName, id);
		//gc.createField(path, tableName, id);
	}
}
