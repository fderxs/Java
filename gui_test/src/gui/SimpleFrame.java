package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;



import javax.swing.filechooser.FileFilter;

import graph_creat.textAnalyze;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

class exitListener implements ActionListener{
	public void actionPerformed(ActionEvent event) {
		System.exit(0);
    }
}

public class SimpleFrame extends JFrame{
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screensize = kit.getScreenSize();
	int screenWidth = screensize.width;
	int screenHeight = screensize.height;
	boolean isOpen = false;
	JTextArea textAreaBefore;
	JTextArea textAreaAfter;
	JButton button;
	JButton stopbutton;
	JLabel label;
	
	public SimpleFrame(){
		setLocation(screenWidth / 4, screenHeight / 4);
		setResizable(false);
		make_Menu();
		make_operator_frame();
	}
	
	public void make_Menu(){  //制作窗口菜单
		Font font_Menu = new Font("SanSerif", Font.BOLD, 40);
		Font font_MenuList = new Font("SanSerif", Font.PLAIN, 30);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(font_Menu);
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);
		
		JMenuItem openItem = new JMenuItem("Open      ");
		openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		openItem.setFont(font_MenuList);
		fileMenu.add(openItem);
		openItem.addActionListener(even -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("."));
			int result = chooser.showOpenDialog(SimpleFrame.this);
			if (result == JFileChooser.APPROVE_OPTION){
				isOpen = true;
				openItem.setEnabled(false);
				String name = chooser.getSelectedFile().getPath();
				run(name);
			}
		});
		
		JMenuItem exitItem = new JMenuItem("Exit      ");
		exitItem.setFont(font_MenuList);
		fileMenu.add(exitItem);
		exitItem.addActionListener(event -> {
			new message_dialog("确定要退出本程序吗？", "alert", "Alert", message_dialog.CONFIRM_CANCEL, new exitListener());
		});
	}

	public void make_operator_frame(){
		Font textFont = new Font("sanserif", Font.PLAIN, 30);
		Font labelFont = new Font("sanserif", Font.PLAIN, 30);
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		//-----------------------------------------------------------------------------//
		//源文本区域
	
		JLabel leftLabel = new JLabel("源文本内容：");
		leftLabel.setFont(labelFont);
		add(leftLabel, new GBC(0,0).setAnchor(GBC.WEST).setInsets(5, 10, 0, 0));
		
		textAreaBefore = new JTextArea(8,30);
		textAreaBefore.setFont(textFont);
		JScrollPane scrollPane = new JScrollPane(textAreaBefore);
		textAreaBefore.setLineWrap(true);
		textAreaBefore.setEditable(false);
		add(scrollPane, new GBC(0,1).setInsets(0, 10, 10, 10));
		
		//-----------------------------------------------------------------------------//
		//处理完成文本区域
		
		JLabel rightLabel = new JLabel("处理完成文本内容：");
		rightLabel.setFont(labelFont);
		add(rightLabel, new GBC(0,2).setAnchor(GBC.WEST).setInsets(5, 10, 0, 0));
		
		textAreaAfter = new JTextArea(8,30);
		textAreaAfter.setFont(textFont);
		JScrollPane scrollPane2 = new JScrollPane(textAreaAfter);
		textAreaAfter.setEditable(false);
		textAreaAfter.setLineWrap(true);
		add(scrollPane2, new GBC(0,3).setInsets(0, 10, 10, 10));
		
		//-----------------------------------------------------------------------------//
		//生成按钮
		
		button = new JButton("生成图片");
		button.setFont(labelFont);
		button.setEnabled(false);
		add(button, new GBC(1, 4, 1, 1).setAnchor(GBC.CENTER).setInsets(10, 40, 20, 10));
		
		stopbutton = new JButton("停止运行");
		stopbutton.setFont(labelFont);
		stopbutton.setEnabled(false);
		add(stopbutton, new GBC(2, 4, 1, 1).setAnchor(GBC.CENTER).setInsets(10, 10, 20, 10));
		
		//-----------------------------------------------------------------------------//
		//图片
		
		label = new JLabel();
		Icon icon=new ImageIcon("clear.png");
		label.setIcon(icon);
		add(label, new GBC(1,0,2,4).setAnchor(GBC.CENTER).setInsets(10));

		pack();
	}
	
	public void run(String name){
		textAnalyze text = new textAnalyze(name);
		textAreaBefore.append(text.initialText);
		textAreaAfter.append(text.str);
		button.setEnabled(true);
		stopbutton.setEnabled(true);
		File file = new File("graph.png");
		while (!file.exists())
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.exit(0);
			}
		label.setIcon(new ImageIcon("graph.png"));
	}
}