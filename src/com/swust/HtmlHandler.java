package com.swust;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlHandler {

	/**
	 * 读取错误报告的html文件信息
	 * 
	 * @param filePath
	 *            html文件路径
	 * @return Map<测试模块, Map<测试名,html失败原因>
	 */
	public Map<String, Map<String, String>> getIterator(String filePath) {
		Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String, String>>();
		try {
			File input = new File(filePath);
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			Elements linksTables = doc.getElementsByClass("testdetails");

			for (int i = 0; i < linksTables.size(); i++) {
				Element elementTable = linksTables.get(i);
				// 测试模块名
				String module = elementTable.getElementsByClass("module")
						.get(0).text();
				Map<String, String> map = new HashMap<>();

				Elements elementTestnames = elementTable
						.getElementsByClass("testname");
				Elements elemenFails = elementTable
						.getElementsByClass("failuredetails");
				for (int j = 0; j < elementTestnames.size(); j++) {
					// 测试案例名
					String testname = elementTestnames.get(j).text();
					// 错误细节
					String detail = elemenFails.get(j).text();
					map.put(testname, detail);
				}
				resultMap.put(module, map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
}
