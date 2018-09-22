package com.swust;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WriteBizImpl {

	public static void write(String htmlFilePath, String outputPath,
			Map<String, Map<String, List<String>>> resultMap) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(new File(
				htmlFilePath)));
		FileWriter bw = new FileWriter(new File(outputPath));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String htmlStr = sb.toString();
		String[] arr = htmlStr.split("<br>");
		bw.write(arr[0]);
		bw.flush();
		bw.write("<br>");
		bw.flush();
		bw.write(arr[1]);
		bw.flush();
		bw.write("<br>");
		bw.flush();

		bw.write("<div>");
		bw.flush();

		for (Entry<String, Map<String, List<String>>> entrySet : resultMap
				.entrySet()) {
			String module = entrySet.getKey();
			// Log.sop("module--->" + module);
			StringBuilder stringBuilder = writeTitle(module);

			for (Entry<String, List<String>> resultEntry : entrySet.getValue()
					.entrySet()) {
				String testname = resultEntry.getKey();
				List<String> list = resultEntry.getValue();
				String htmlDetail = list.get(0);
				String xmlDetail = list.get(1);
				String solution = list.get(2);
				// Log.sop("------>testname=" + testname);
				// Log.sop("------>list=" + list);
				// ---------------------------------------------------------
				stringBuilder = writeContent(stringBuilder, testname,
						htmlDetail, xmlDetail, solution);
				// ----------------------------------------------------------
			}
			bw.write(writeLast(stringBuilder));
			bw.flush();
		}

		bw.write("<div>");
		bw.flush();

		bw.write(arr[3]);
		bw.flush();

		bw.write("</body>" + "</html>");
		bw.flush();
	}

	public static StringBuilder writeTitle(String module) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table class=\"testdetails\"><tr>"
				+ "<td class='module' colspan='5'><a name=\"armeabi-v7a%C2%A0CtsAppSecurityHostTestCases\">");
		sb.append(module);
		sb.append("</a></td></tr><tr><th width=\"30%\">Test</th><th width=\"5%\">Result</th>"
				+ "<th>htmlDetail</th><th>xmlDetail</th><th>Solution</th></tr>");
		return sb;
	}

	public static StringBuilder writeContent(StringBuilder sb, String testname,
			String htmlDetail, String xmlDetail, String solution) {
		sb.append("<tr><td class=\"testname\">");
		sb.append(testname);
		sb.append("</td><td class=\"failed\"><div style=\"text-align: center; margin-left:auto; margin-right:auto;\">fail</div>");
		sb.append("</td><td class=\"failuredetails\"><div class=\"details\">");
		sb.append(htmlDetail);
		sb.append("</td><td class=\"failuredetails\"><div class=\"details\">");
		sb.append(xmlDetail);
		sb.append("</td><td class=\"failuredetails\"><div class=\"details\">");
		sb.append(solution);
		sb.append("</div></td></tr>");
		return sb;
	}

	public static String writeLast(StringBuilder sb) {
		sb.append("</table>");
		return sb.toString();
	}
}
