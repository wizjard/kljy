package com.juncsoft.KLJY.Public.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import net.sf.json.JSONArray;

public class PageDict {
	private Hashtable<String, JSONArray> ht = new Hashtable<String, JSONArray>();

	public void addItem(String fldCode, String value, String text, int orderNo)
			throws Exception {
		JSONArray array = this.getItems(fldCode);
		array.add(new String[] { value, text, orderNo + "" });
		ht.put(fldCode, array);
	}

	public Hashtable<String, JSONArray> getPageDict() {
		sort();
		return this.ht;
	}

	private void sort() {
		Hashtable<String, JSONArray> table = new Hashtable<String, JSONArray>();
		for (Iterator<String> it = ht.keySet().iterator(); it.hasNext();) {
			String code = it.next();
			JSONArray array = ht.get(code);
			array = sortArray(array);
			table.put(code, array);
		}
		this.ht = table;
	}

	private JSONArray sortArray(JSONArray array) {
		List<String[]> list = new ArrayList<String[]>();
		for (Object object : array) {
			JSONArray a = JSONArray.fromObject(object);
			list.add(new String[] { a.getString(0), a.getString(1),
					a.getString(2) });
		}
		Collections.sort(list, new SortClass());
		return JSONArray.fromObject(list);
	}

	private JSONArray getItems(String fldCode) {
		JSONArray array = ht.get(fldCode);
		if (array == null) {
			array = new JSONArray();
		}
		return array;
	}
}

class SortClass implements Comparator<String[]> {

	public int compare(String[] o1, String[] o2) {
		int i1 = Integer.parseInt(o1[2]);
		int i2 = Integer.parseInt(o2[2]);
		if (i1 > i2) {
			return 1;
		} else if (i1 < i2) {
			return -1;
		} else {
			return 0;
		}
	}

}