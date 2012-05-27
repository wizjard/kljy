package com.juncsoft.KLJY.InHospitalCase.CaseMaster.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class CaseCfgUtil {
	private static Document doc;
	static {
		InputStream is = CaseCfgUtil.class.getClassLoader()
				.getResourceAsStream(
						"com/juncsoft/KLJY/config/SYS_WARD_INHSPREC_cfg.xml");
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(is);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		if (is != null)
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	@SuppressWarnings("unchecked")
	public static JSONArray queryMyInHspRecCfg(String ward) throws Exception {
		JSONArray array = new JSONArray();
		try {
			Element root = doc.getRootElement();
			for (Iterator it = root.getChildren().iterator(); it.hasNext();) {
				Element ele = (Element) it.next();
				if (ele.getName().equalsIgnoreCase(ward)) {
					for (Iterator it2 = ele.getChildren("InHspRec").iterator(); it2
							.hasNext();) {
						Element inhsp = (Element) it2.next();
						JSONObject json = new JSONObject();
						json.put("code", inhsp.getChildTextTrim("code"));
						json.put("name", inhsp.getChildTextTrim("name"));
						json.put("url", inhsp.getChildTextTrim("url"));
						json.put("desc", inhsp.getChildTextTrim("desc"));
						array.add(json);
					}
				}
			}
			if (array.size() == 0) {
				for (Iterator it = root.getChild("default").getChildren(
						"InHspRec").iterator(); it.hasNext();) {
					Element inhsp = (Element) it.next();
					JSONObject json = new JSONObject();
					json.put("code", inhsp.getChildTextTrim("code"));
					json.put("name", inhsp.getChildTextTrim("name"));
					json.put("url", inhsp.getChildTextTrim("url"));
					json.put("desc", inhsp.getChildTextTrim("desc"));
					array.add(json);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	public static JSONObject queryInHspRecCfgByCode(String code)
			throws Exception {
		JSONObject json = null;
		try {
			Element root = doc.getRootElement();
			for (Iterator it = root.getChildren().iterator(); it.hasNext();) {
				Element ele = (Element) it.next();
				for (Iterator it2 = ele.getChildren("InHspRec").iterator(); it2
						.hasNext();) {
					Element inhsp = (Element) it2.next();
					if (inhsp.getChildTextTrim("code").equalsIgnoreCase(code)) {
						json = new JSONObject();
						json.put("code", inhsp.getChildTextTrim("code"));
						json.put("name", inhsp.getChildTextTrim("name"));
						json.put("url", inhsp.getChildTextTrim("url"));
						json.put("desc", inhsp.getChildTextTrim("desc"));
						break;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return json;
	}

	public static void main(String[] args) {
		try {
			System.out.println(queryMyInHspRecCfg("S007"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
