package com.swust;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JFrameBizImpl {

	private HtmlHandler htmlHandler = new HtmlHandler();
	private XmlHandler xmlHandler = new XmlHandler();
	private ReadBizImpl readBizImpl = new ReadBizImpl();

	/**
	 * 1 获取“测试模块+测试案例+失败原因(html)+失败原因(xml)+解决方案” 2 输出报告文档
	 * 
	 * @param htmlFilePath
	 *            谷歌测试文档路径
	 * @param xmlFilePath
	 *            方法解决文档路径
	 * @param outputPath
	 *            输出的报告文档的路径
	 */
	public void start(String htmlFilePath, String xmlFilePath, String outputPath) {
		Map<String, Map<String, List<String>>> resultMap = compare(
				htmlFilePath, xmlFilePath);
		try {
			WriteBizImpl.write(htmlFilePath, outputPath, resultMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取“测试模块+测试案例+失败原因(html)+失败原因(xml)+解决方案”
	 * 
	 * @param htmlFilePath
	 *            谷歌测试文档路径
	 * @param xmlFilePath
	 *            方法解决文档路径
	 * @return Map<测试模块, Map<测试案例, List<失败原因(html)+失败原因(xml)+解决方案>>>
	 */
	public Map<String, Map<String, List<String>>> compare(String htmlFilePath,
			String xmlFilePath) {
		// 获取html报告信息
		Map<String, Map<String, String>> wrongItemMap = htmlHandler
				.getIterator(htmlFilePath);
		// 返回的总体信息
		Map<String, Map<String, List<String>>> resultMap = new HashMap<String, Map<String, List<String>>>();
		// 读取解决方案文档信息
		File[] files = readBizImpl.getFiles(xmlFilePath);
		Map<String, List<String>> solutionMap = new HashMap<>();
		for (File file : files) {
			solutionMap = xmlHandler.getIterator(file);
		}
		// 遍历html文档信息
		for (Entry<String, Map<String, String>> htmlMap : wrongItemMap
				.entrySet()) {
			// 测试模块
			String moduleName = htmlMap.getKey();
			// Map<测试案例, List<失败原因(html)+失败原因(xml)+解决方案>
			Map<String, List<String>> map = new HashMap<>();
			// 遍历htmlMap的value值
			for (Entry<String, String> itemHtmlMap : htmlMap.getValue()
					.entrySet()) {
				String testname = itemHtmlMap.getKey();
				String detail = itemHtmlMap.getValue();
				boolean flag = false;
				// List<失败原因(html)+失败原因(xml)+解决方案>
				List<String> list = new ArrayList<>();
				for (Entry<String, List<String>> itemXmlMap : solutionMap
						.entrySet()) {
					// 失败详情
					String itemDetail = itemXmlMap.getKey();
					List<String> xmlList = itemXmlMap.getValue();
					// 测试用例名
					String itemName = xmlList.get(0);
					// 解决方案或者豁免链接/bundleId等
					String itemValue = xmlList.get(1);
					if (testname.equals(itemName)) {
						list.add(detail);
						list.add(itemDetail);
						list.add(itemValue);
						flag = true;
						break;
					}
				}
				// 如果解决方案xml文档没有对应的解决方案，那么输出null值
				if (!flag) {
					list.add(detail);
					list.add("null");
					list.add("null");
				}
				map.put(testname, list);
			}
			resultMap.put(moduleName, map);
		}

		return resultMap;
	}
}
