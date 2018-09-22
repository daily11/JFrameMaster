package com.swust;

import java.io.File;
import java.io.FileFilter;

import javax.swing.JFileChooser;

public class ReadBizImpl {

	/**
	 * 读取文件路径
	 * 
	 * @return
	 */
	public String getFilePath() {
		JFileChooser jfc = new JFileChooser();// 文件选择器
		jfc.setCurrentDirectory(new File("E://"));// 文件选择器的初始目录定为d盘
		jfc.setFileSelectionMode(0);// 设定只能选择到文件
		int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
		if (state == 1) {
			return null;// 撤销则返回
		} else {
			File f = jfc.getSelectedFile();// f为选择到的文件
			// Log.sop(f.getAbsolutePath());
			return f.getAbsolutePath();
		}
		// TODO 判断是否为html文件
	}

	/**
	 * 读取目录路径
	 * 
	 * @return
	 */
	public String getDirectoryPath() {
		JFileChooser jfc = new JFileChooser();// 文件选择器
		jfc.setCurrentDirectory(new File("E://"));// 文件选择器的初始目录定为d盘
		jfc.setFileSelectionMode(1);// 设定只能选择到目录
		int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
		if (state == 1) {
			return null;// 撤销则返回
		} else {
			File f = jfc.getSelectedFile();// f为选择到的文件
			// Log.sop(f.getAbsolutePath());
			return f.getAbsolutePath();
		}
		// TODO 判断是否为xml文件目录
	}

	/**
	 * 获取文件目录下的所有文件路径【过滤文件格式，必须为“xls”或“xlsx”格式】
	 * 
	 * @param path
	 *            文件目录路径
	 * @return
	 */
	public File[] getFiles(String directoryPath) {
		File file = new File(directoryPath);
		File[] files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File listFile) {
				if (listFile.getName().endsWith("xls")
						|| listFile.getName().endsWith("xlsx"))
					return true;
				else
					return false;
			}
		});
		return files;
	}
}
