package com.swust;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JFrameActivity extends BaseJFrameActivity implements
		ActionListener {
	private static final long serialVersionUID = 1L;
	// html文件路径
	private String htmlFilePath;
	// xml文件路径
	private String xmlFilePath;
	// 输出文档路径
	private String outputPath;
	// 相关的业务逻辑处理类
	private ReadBizImpl readBizImpl = new ReadBizImpl();
	private JFrameBizImpl jFrameBizImpl = new JFrameBizImpl();

	JPanel jPanel;// 面板
	JButton readHtmlJB;
	JButton readXmlJB;
	JButton outputPathJB;
	JButton startJB;

	public JFrameActivity() {
		initView();
		initListener();
		initData();
	}

	private void initView() {
		readHtmlJB = new JButton(StringConfig.BUTTON_READ_HTML);
		readXmlJB = new JButton(StringConfig.BUTTON_READ_XML);
		outputPathJB = new JButton(StringConfig.BUTTON_WRITE_FILEPATH);
		startJB = new JButton(StringConfig.BUTTON_START);
	}

	private void initListener() {
		readHtmlJB.addActionListener(this);
		readXmlJB.addActionListener(this);
		outputPathJB.addActionListener(this);
		startJB.addActionListener(this);
	}

	private void initData() {
		jPanel = new JPanel();
		jPanel.add(readHtmlJB);
		jPanel.add(readXmlJB);
		jPanel.add(outputPathJB);
		jPanel.add(startJB);
		this.setContentPane(jPanel);

		// 设置标题
		this.setTitle("测试指南");
		// 窗体大小
		this.setSize(571, 105);
		// 网格式布局
		getContentPane().setLayout(new GridLayout(1, 4));
		// 显示窗体
		this.setVisible(true);
		// 窗口在屏幕中间显示
		this.setLocationRelativeTo(null);
		// 界面关闭，停止运行程序
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 1 读取html文件； 2 读取xml文件【集合，一次性读入】； 3 指定文档输出路径； 4 开始
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case StringConfig.BUTTON_READ_HTML:
			htmlFilePath = readBizImpl.getFilePath();
			// Log.sop(htmlFilePath);
			break;
		case StringConfig.BUTTON_READ_XML:
			xmlFilePath = readBizImpl.getDirectoryPath();
			// Log.sop(xmlFilePath);
			break;
		case StringConfig.BUTTON_WRITE_FILEPATH:
			outputPath = readBizImpl.getDirectoryPath();
			outputPath += File.separator + "output.html";
			// Log.sop(outputPath);
			break;
		case StringConfig.BUTTON_START:
			if (htmlFilePath == null) {
				JOptionPane.showMessageDialog(null,
						StringConfig.STRING_NOTICE_HTML,
						StringConfig.STRING_NOTICE,
						JOptionPane.INFORMATION_MESSAGE);
			} else if (xmlFilePath == null) {
				JOptionPane.showMessageDialog(null,
						StringConfig.STRING_NOTICE_XML,
						StringConfig.STRING_NOTICE,
						JOptionPane.INFORMATION_MESSAGE);
			} else if (outputPath == null) {
				JOptionPane.showMessageDialog(null,
						StringConfig.STRING_NOTICE_OUTPUT_PATH,
						StringConfig.STRING_NOTICE,
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				jFrameBizImpl.start(htmlFilePath, xmlFilePath, outputPath);
				// 提示报告文档生成！
				JOptionPane.showMessageDialog(null,
						StringConfig.STRING_NOTICE_OUTPUTD_PATH + outputPath,
						StringConfig.STRING_NOTICE,
						JOptionPane.INFORMATION_MESSAGE);
				// 退出程序
				System.exit(0);
			}
			break;
		default:
			break;
		}
	}

}