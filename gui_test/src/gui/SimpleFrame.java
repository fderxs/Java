package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

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

public class SimpleFrame extends JFrame{
	static final int sleepTime = 300;
	int num = 0;
	public textAnalyze text;
	public boolean isOpen = false;
	JTextField textfield;
	JMenuItem openItem;
	JTextArea textAreaBefore, textAreaAfter, textareaAnswer, textArea4;
	JButton button, stopbutton, addrbutton, runbutton;
	JLabel label;
	JComboBox<String> faceCombo3_word1, faceCombo3_word2, faceCombo5_word1, faceCombo5_word2;
	JRadioButton button3, button4, button5, button6;
	Font font_Menu = new Font("SanSerif", Font.BOLD, 40);
	Font font_MenuList = new Font("SanSerif", Font.PLAIN, 30);
	Font textFont = new Font("sanserif", Font.PLAIN, 30);
	Font labelFont = new Font("sanserif", Font.PLAIN, 30);
	
	public SimpleFrame(){
		setResizable(false);
		make_Menu();
		make_operator_frame();
	}
	
	private static void copyFileUsingJava7Files(File source, File dest)
	        throws IOException {    
	        Files.copy(source.toPath(), dest.toPath());
	}
	
	public void make_Menu(){  //制作窗口菜单
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(font_Menu);
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);
		
		openItem = new JMenuItem("Open      ");
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
			message_dialog message = new message_dialog();
			message.showdialog("确定要退出本程序吗？", "alert", "Alert", message_dialog.CONFIRM_CANCEL);
			System.exit(0);
		});
	}

	public void make_operator_frame(){
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		//-----------------------------------------------------------------------------//
		//源文本区域
	
		JLabel leftLabel = new JLabel("源文本内容：");
		leftLabel.setFont(labelFont);
		add(leftLabel, new GBC(0,0,3,1).setAnchor(GBC.WEST).setInsets(5, 10, 0, 0));
		
		textAreaBefore = new JTextArea(8,30);
		textAreaBefore.setFont(textFont);
		JScrollPane scrollPane = new JScrollPane(textAreaBefore);
		textAreaBefore.setLineWrap(true);
		textAreaBefore.setEditable(false);
		add(scrollPane, new GBC(0,1,3,1).setInsets(0, 10, 10, 10));
		
		//-----------------------------------------------------------------------------//
		//处理完成文本区域
		
		JLabel rightLabel = new JLabel("处理完成文本内容：");
		rightLabel.setFont(labelFont);
		add(rightLabel, new GBC(0,2,3,1).setAnchor(GBC.WEST).setInsets(5, 10, 0, 0));
		
		textAreaAfter = new JTextArea(8,30);
		textAreaAfter.setFont(textFont);
		JScrollPane scrollPane2 = new JScrollPane(textAreaAfter);
		textAreaAfter.setEditable(false);
		textAreaAfter.setLineWrap(true);
		add(scrollPane2, new GBC(0,3,3,1).setInsets(0, 10, 10, 10));
		
		//-----------------------------------------------------------------------------//
		//生成按钮
		
		button = new JButton("保存图片");
		button.setFont(labelFont);
		button.setEnabled(false);
		button.addActionListener(event -> {
			String addr = textfield.getText();
			addr = addr + "//graph.png";
			File dest = new File(addr);
			File source = new File("graph.png");
			try {
				copyFileUsingJava7Files(source, dest);
			} catch (IOException e) {
				System.exit(0);
			}
			message_dialog message = new message_dialog();
			message.showdialog("图片生成成功！", "right", "Right", message_dialog.CONFIRM);
		});
		add(button, new GBC(4, 4, 1, 1).setAnchor(GBC.WEST).setInsets(10, 40, 10, 10));
		
		JPanel buttonPanel = new JPanel();
		runbutton = new JButton("    运行    ");
		runbutton.setFont(labelFont);
		runbutton.setEnabled(false);
		runbutton.addActionListener(event -> {
			if (button3.isSelected()){
				textareaAnswer.setText("");
				String word1 = (String) faceCombo3_word1.getSelectedItem();
				String word2 = (String) faceCombo3_word2.getSelectedItem();
				if (word1.equals("") || word2.equals("")){
					message_dialog message = new message_dialog();
					message.showdialog("输入单词不能为空！", "error", "Error", message_dialog.CONFIRM);
				}
				else if (word1.equals(word2)){
					message_dialog message = new message_dialog();
					message.showdialog("输入单词相同！", "error", "Error", message_dialog.CONFIRM);
				}
				else{
					textareaAnswer.append(text.queryBridgeWords(word1,word2));
					printdot3();
				}
					
			}
			else if (button4.isSelected()){
				textareaAnswer.setText("");
				boolean ptext = true;
				String text4 = textArea4.getText();
				char chr;
				StringBuilder strBuilder = new StringBuilder(text4);
				for (int i = 0; i < text4.length(); i++)
				{
					chr = text4.charAt(i);
					if (chr >= 'A' && chr <= 'Z')
						strBuilder.setCharAt(i, (char) (chr + 32));
					else if ((chr < 'a' || chr >'z') && chr != ' '){
						message_dialog message = new message_dialog();
						message.showdialog("输入字符串不合法！", "error", "Error", message_dialog.CONFIRM);
						ptext = false;
						break;
					}
				}
				if (ptext)	
					textareaAnswer.append(text.generateNewText(text4));
			}
			else if (button5.isSelected()){
				textareaAnswer.setText("");
				String word1 = (String) faceCombo5_word1.getSelectedItem();
				String word2 = (String) faceCombo5_word2.getSelectedItem();
				if (word1.equals("") && word2.equals("")){
					message_dialog message = new message_dialog();
					message.showdialog("输入单词不能为空！", "error", "Error", message_dialog.CONFIRM);
				}
				else if (word1.equals(word2)){
					message_dialog message = new message_dialog();
					message.showdialog("输入单词相同！", "error", "Error", message_dialog.CONFIRM);
				}
				else if (word1.equals("") && !word2.equals(""))
					textareaAnswer.append(text.calcShortestPath(word2));
				else if (!word1.equals("") && word2.equals(""))
					textareaAnswer.append(text.calcShortestPath(word1));
				else{
					textareaAnswer.append(text.calcShortestPath(word1,word2));
					printdot5();
				}
			}
			else if (button6.isSelected()){
				textareaAnswer.setText("");
				textareaAnswer.append(text.randomWalk());
			}
		});
		buttonPanel.add(runbutton);
		add(buttonPanel, new GBC(0, 12, 5, 1).setAnchor(GBC.CENTER).setInsets(10, 10, 20, 10));
		
		stopbutton = new JButton("停止运行");
		stopbutton.setFont(labelFont);
		stopbutton.setEnabled(false);
		stopbutton.addActionListener(event -> {
			textAreaBefore.setText("");
			textAreaAfter.setText("");
			button.setEnabled(false);
			stopbutton.setEnabled(false);
			runbutton.setEnabled(false);
			addrbutton.setEnabled(false);
			textfield.setText("");
			textfield.setEditable(false);
			label.setIcon(new ImageIcon("clear.png"));
			openItem.setEnabled(true);
			faceCombo3_word1.removeAllItems();
			faceCombo3_word2.removeAllItems();
			faceCombo5_word1.removeAllItems();
			faceCombo5_word2.removeAllItems();
			
			faceCombo3_word1.setEnabled(false);
			faceCombo3_word2.setEnabled(false);
			faceCombo5_word1.setEnabled(false);
			faceCombo5_word2.setEnabled(false);
			
			textArea4.setText("");
			textArea4.setEnabled(false);
			
			textareaAnswer.setText("");
			
			button3.setEnabled(false);
			button4.setEnabled(false);
			button5.setEnabled(false);
			button6.setEnabled(false);
		});
		buttonPanel.add(stopbutton);
		
		addrbutton = new JButton("选择路径");
		addrbutton.setFont(labelFont);
		addrbutton.setEnabled(false);
		addrbutton.addActionListener(event -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File(".."));
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showOpenDialog(SimpleFrame.this);
			if (result == JFileChooser.APPROVE_OPTION){
				String addrName = chooser.getSelectedFile().getPath();
				textfield.setText(addrName);
			}
		});
		add(addrbutton,new GBC(3,4,1,1).setAnchor(GBC.CENTER).setInsets(10, 10, 10, 10));
		
		//-----------------------------------------------------------------------------//
		//图片
		
		label = new JLabel();
		Icon icon=new ImageIcon("clear.png");
		label.setIcon(icon);
		add(label, new GBC(3,0,2,4).setAnchor(GBC.CENTER).setInsets(10));
		
		//-----------------------------------------------------------------------------//
		//路径保存
		textfield = new JTextField("",30);
		textfield.setFont(textFont);
		textfield.setEditable(false);
		add(textfield, new GBC(0, 4, 3, 1).setInsets(10, 10, 10, 10));
		
		make_function_frame();

		pack();
	}
	
	public void make_function_frame(){  //功能界面部分
		ButtonGroup group = new ButtonGroup();
		
		//-----------------------------------------------------------------------------//
		//功能三
		button3 = new JRadioButton("查询桥接词");
		button3.setFont(labelFont);
		button3.setEnabled(false);
		button3.addActionListener(event -> {
			runbutton.setEnabled(true);
			label.setIcon(new ImageIcon("graph.png"));
			faceCombo3_word1.setEnabled(true);
			faceCombo3_word2.setEnabled(true);
			
			textArea4.setEnabled(false);
			
			faceCombo5_word1.setEnabled(false);
			faceCombo5_word2.setEnabled(false);
			textareaAnswer.setText("");
		});
		group.add(button3);
		add(button3, new GBC(0,5,2,1).setAnchor(GBC.WEST).setInsets(5, 10, 0, 0));
		
		faceCombo3_word1 = new JComboBox<>();
		faceCombo3_word1.setFont(textFont);
		faceCombo3_word1.addItem("                               ");
		faceCombo3_word1.setEnabled(false);
		add(faceCombo3_word1, new GBC(0,6,1,1).setAnchor(GBC.CENTER).setInsets(5, 10, 20, 0));
		
		faceCombo3_word2 = new JComboBox<>();
		faceCombo3_word2.setFont(textFont);
		faceCombo3_word2.addItem("                               ");
		faceCombo3_word2.setEnabled(false);
		add(faceCombo3_word2, new GBC(1,6,1,1).setAnchor(GBC.CENTER).setInsets(5, 10, 20, 0));
		
		//-----------------------------------------------------------------------------//
		//功能四
		button4 = new JRadioButton("根据bridge word生成新文本");
		button4.setFont(labelFont);
		button4.setEnabled(false);
		button4.addActionListener(event -> {
			runbutton.setEnabled(true);
			label.setIcon(new ImageIcon("graph.png"));
			faceCombo3_word1.setEnabled(false);
			faceCombo3_word2.setEnabled(false);
			
			textArea4.setEnabled(true);
			
			faceCombo5_word1.setEnabled(false);
			faceCombo5_word2.setEnabled(false);
			textareaAnswer.setText("");
		});
		group.add(button4);
		add(button4, new GBC(0,7,2,1).setAnchor(GBC.WEST).setInsets(5, 10, 0, 0));
		
		textArea4 = new JTextArea(3,23);
		textArea4.setFont(textFont);
		JScrollPane scrollPane = new JScrollPane(textArea4);
		textArea4.setLineWrap(true);
		textArea4.setEditable(true);
		textArea4.setEnabled(false);
		add(scrollPane, new GBC(0,8,2,1).setInsets(5, 10, 20, 10));
		
		//-----------------------------------------------------------------------------//
		//功能五
		button5 = new JRadioButton("计算两个单词之间的最短路径");
		button5.setFont(labelFont);
		button5.setEnabled(false);
		button5.addActionListener(event -> {
			runbutton.setEnabled(true);
			label.setIcon(new ImageIcon("graph.png"));
			faceCombo3_word1.setEnabled(false);
			faceCombo3_word2.setEnabled(false);
			
			textArea4.setEnabled(false);
			
			faceCombo5_word1.setEnabled(true);
			faceCombo5_word2.setEnabled(true);
			textareaAnswer.setText("");
		});
		group.add(button5);
		add(button5, new GBC(0,9,2,1).setAnchor(GBC.WEST).setInsets(5, 10, 0, 0));
		
		faceCombo5_word1 = new JComboBox<>();
		faceCombo5_word1.setFont(textFont);
		faceCombo5_word1.addItem("                               ");
		faceCombo5_word1.setEnabled(false);
		add(faceCombo5_word1, new GBC(0,10,1,1).setAnchor(GBC.CENTER).setInsets(5, 10, 20, 0));
		
		faceCombo5_word2 = new JComboBox<>();
		faceCombo5_word2.setFont(textFont);
		faceCombo5_word2.addItem("                               ");
		faceCombo5_word2.setEnabled(false);
		add(faceCombo5_word2, new GBC(1,10,1,1).setAnchor(GBC.CENTER).setInsets(5, 10, 20, 0));
		
		//-----------------------------------------------------------------------------//
		//功能六
		button6 = new JRadioButton("随机游走");
		button6.setFont(labelFont);
		button6.setEnabled(false);
		button6.addActionListener(event -> {
			runbutton.setEnabled(true);
			label.setIcon(new ImageIcon("graph.png"));
			faceCombo3_word1.setEnabled(false);
			faceCombo3_word2.setEnabled(false);
			
			textArea4.setEnabled(false);
			
			faceCombo5_word1.setEnabled(false);
			faceCombo5_word2.setEnabled(false);
			textareaAnswer.setText("");
		});
		group.add(button6);
		add(button6, new GBC(0,11,2,1).setAnchor(GBC.WEST).setInsets(5, 10, 10, 0));
		
		//-----------------------------------------------------------------------------//
		//答案框
		textareaAnswer = new JTextArea(10,24);
		textareaAnswer.setFont(textFont);
		JScrollPane scrollPane2 = new JScrollPane(textareaAnswer);
		textareaAnswer.setLineWrap(true);
		textareaAnswer.setEditable(false);
		add(scrollPane2, new GBC(2,5,3,7).setAnchor(GBC.WEST).setInsets(0, 10, 10, 10));
	}
	
	public void run(String name){
		text = new textAnalyze(name);
		if (text.CORRECT == true){
			textAreaBefore.append(text.initialText);
			textAreaAfter.append(text.str);
			button.setEnabled(true);
			stopbutton.setEnabled(true);
			addrbutton.setEnabled(true);
			textfield.setEditable(true);
			File fileAddr = new File("");
			try{
				textfield.setText(fileAddr.getCanonicalPath());
			}
			catch(Exception e){}
			
			File file = new File("graph.png");
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.exit(0);
			}
			label.setIcon(new ImageIcon("graph.png"));
			
			faceCombo3_word1.setEnabled(true);
			faceCombo3_word1.setEditable(true);
			faceCombo3_word1.removeAllItems();
			for (int i = 0; i < text.word_num; i++)
				faceCombo3_word1.addItem(text.word_list[i]);
			
			faceCombo3_word2.setEnabled(true);
			faceCombo3_word2.setEditable(true);
			faceCombo3_word2.removeAllItems();
			for (int i = 0; i < text.word_num; i++)
				faceCombo3_word2.addItem(text.word_list[i]);
			
			textArea4.setEnabled(true);
			
			faceCombo5_word1.setEnabled(true);
			faceCombo5_word1.setEditable(true);
			faceCombo5_word1.removeAllItems();
			for (int i = 0; i < text.word_num; i++)
				faceCombo5_word1.addItem(text.word_list[i]);
			
			faceCombo5_word2.setEnabled(true);
			faceCombo5_word2.setEditable(true);
			faceCombo5_word2.removeAllItems();
			for (int i = 0; i < text.word_num; i++)
				faceCombo5_word2.addItem(text.word_list[i]);
			
			textareaAnswer.setEnabled(true);
			
			button3.setEnabled(true);
			button4.setEnabled(true);
			button5.setEnabled(true);
			button6.setEnabled(true);
		}
		else{
			openItem.setEnabled(true);
			message_dialog message = new message_dialog();
			message.showdialog("文本文件内容过长！", "error", "Error", message_dialog.CONFIRM);
		}
	}
	
	public void printdot3(){
		try{
			File file_out = new File("function3.dot");
			if (!file_out.exists()){
				file_out.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file_out);
			BufferedWriter outstream = new BufferedWriter(fw);
			
			String s = "digraph ha{ ";
			outstream.write(s);
			
			for (int i = 0; i < text.word_num; i++)
				if (text.firstword == i || text.secondword == i){
					s = text.word_list[i] + " [style = filled, color = green]; ";
					outstream.write(s);
				}
				else if( text.p[i]){
					s = text.word_list[i] + " [style = filled, color = lightblue]; ";
					outstream.write(s);
				}
				else{
					s = text.word_list[i] + "; ";
					outstream.write(s);
				}
			
			for (int i = 0; i < text.word_num; i++){
				for (int j = 0; j < text.word_num; j++){
					if (text.w[i][j] > 0){
						if (text.firstword == i  && text.p[j] || text.p[i] && text.secondword == j){
							s = text.word_list[i] + " -> " + text.word_list[j] + "[label = \"" + text.w[i][j] + "\", color = blue];";
							outstream.write(s);
						}
						else{
							s = text.word_list[i] + " -> " + text.word_list[j] + "[label = \"" + text.w[i][j] + "\"];";
							outstream.write(s);
						}
					}
				}
			}
			outstream.write("}");
			outstream.close();
			
			File directory = new File("");
			String path = directory.getCanonicalPath();
			
			Runtime run = Runtime.getRuntime();
			run.exec("dot -Tpng " + path + "\\" + "function3.dot" + " -o " + String.valueOf(++num) + "function3.png");
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.exit(0);
			}
			
			label.setIcon(new ImageIcon(String.valueOf(num) + "function3.png"));
			File file = new File(String.valueOf(num) + "function3.png");
			if (file.exists()) file.delete();
			if (file_out.exists()) file_out.delete();
			
		}
		catch (IOException e){
			message_dialog message = new message_dialog();
			message.showdialog("I/O error occurred", "error", "Error", message_dialog.CONFIRM);
		}
	}	
	public void printdot5(){
		try{
			File file_out = new File("function5.dot");
			if (!file_out.exists()){
				file_out.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file_out);
			BufferedWriter outstream = new BufferedWriter(fw);
			
			String s = "digraph ha{ ";
			outstream.write(s);
			
			for (int i = 0; i < text.word_num; i++)
				if (text.firstword == i || text.secondword == i){
					s = text.word_list[i] + " [style = filled, color = green]; ";
					outstream.write(s);
				}
				else if( text.p[i]){
					s = text.word_list[i] + " [style = filled, color = lightblue]; ";
					outstream.write(s);
				}
				else{
					s = text.word_list[i] + "; ";
					outstream.write(s);
				}
			
			for (int i = 0; i < text.word_num; i++){
				for (int j = 0; j < text.word_num; j++){
					if (text.w[i][j] > 0){
						if (text.p[i] && text.p[j]){
							s = text.word_list[i] + " -> " + text.word_list[j] + "[label = \"" + text.w[i][j] + "\", color = red];";
							outstream.write(s);
						}
						else{
							s = text.word_list[i] + " -> " + text.word_list[j] + "[label = \"" + text.w[i][j] + "\"];";
							outstream.write(s);
						}
					}
				}
			}
			outstream.write("}");
			outstream.close();
			
			File directory = new File("");
			String path = directory.getCanonicalPath();
			
			Runtime run = Runtime.getRuntime();
			run.exec("dot -Tpng " + path + "\\" + "function5.dot" + " -o " + String.valueOf(++num) + "function5.png");
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.exit(0);
			}
			
			label.setIcon(new ImageIcon(String.valueOf(num) + "function5.png"));
			File file = new File(String.valueOf(num) + "function5.png");
			if (file.exists()) file.delete();
			if (file_out.exists()) file_out.delete();
			
		}
		catch (IOException e){
			message_dialog message = new message_dialog();
			message.showdialog("I/O error occurred", "error", "Error", message_dialog.CONFIRM);
		}
	}	
}