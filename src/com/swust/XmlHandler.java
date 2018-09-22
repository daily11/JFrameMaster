package com.swust;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class XmlHandler {

	/**
	 * 获取常见错误与豁免集合
	 * 
	 * @return Map<失败原因, List<测试名+解决方案>>
	 */
	public Map<String, List<String>> getIterator(File excel) {
		Map<String, List<String>> resultMap = new HashMap<>();
		FileInputStream fis = null;
		try {
			if (excel.isFile() && excel.exists()) { // 判断文件是否存在
				String[] split = excel.getName().split("\\."); // .是特殊字符，需要转义！！！！！
				Workbook wb = null;
				// 根据文件后缀（xls/xlsx）进行判断
				fis = new FileInputStream(excel); // 文件流对象
				if ("xls".equals(split[1])) {
					wb = new HSSFWorkbook(fis);
				} else if ("xlsx".equals(split[1])) {
					wb = WorkbookFactory.create(fis);
				} else {
					Log.sop("文件类型错误!");
					return resultMap;
				}

				// 读取sheet 0【一个表格可能有很多的sheet，默认读取sheet 0】
				Sheet sheet = wb.getSheetAt(0);
				// 第一行是列名，所以不读
				int firstRowIndex = sheet.getFirstRowNum() + 1;
				int lastRowIndex = sheet.getLastRowNum();

				for (int rIndex = firstRowIndex; rIndex < lastRowIndex; rIndex++) { // 遍历行
					// List<测试名+解决方案>
					List<String> list = new ArrayList<>();
					Row row = sheet.getRow(rIndex);
					if (row != null) {
						int firstCellIndex = row.getFirstCellNum();
						int secondCellIndex = firstCellIndex + 1;
						int thirdCellIndex = firstCellIndex + 2;
						int fourthCellIndex = firstCellIndex + 3;

						// 错误测试案例
						Cell cell2 = row.getCell(secondCellIndex);
						// 失败原因
						Cell cell3 = row.getCell(thirdCellIndex);
						// 解决方法/豁免链接+bugId
						Cell cell4 = row.getCell(fourthCellIndex);
						list.add(cell2.toString().trim());
						list.add(cell4.toString().trim());
						resultMap.put(cell3.toString().trim(), list);
					}
				}
			} else {
				Log.sop("找不到指定的文件");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultMap;
	}
}
