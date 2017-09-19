package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;

public class message_dialog extends JFrame{
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screensize = kit.getScreenSize();
	public static final int CONFIRM_CANCEL = 0;
	public static final int CONFIRM = 1;
	int screenWidth = screensize.width;
	int screenHeight = screensize.height;
	
	public message_dialog(String text, String image, String title, int style, ActionListener listener){
		setTitle(title);
		setSize(screenWidth / 6, screenHeight / 6);
		setLocation(screenWidth / 12 * 5, screenHeight / 12 * 5);
		setVisible(true);
		
		String imageName = new String();
		if (image.equals("alert"))
			imageName = "pic\\timg_alert.png";
		else if (image.equals("error"))
			imageName = "pic\\timg_error.png";
		else if (image.equals("right"))
			imageName = "pic\\timg_right.png";
		
		JLabel label=new JLabel(text ,JLabel.CENTER);
	    Icon icon=new ImageIcon(imageName);
	    label.setIcon(icon);
	    label.setFont(new Font("SanSerif", Font.BOLD, 40));
	    label.setOpaque(true);
		add(label,BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		if (style == CONFIRM){
			JButton button = new JButton("确定");
			button.setFont(new Font("SanSerif", Font.BOLD, 30));
			button.addActionListener(event -> {
				 dispose();
			});
			buttonPanel.add(button);
		}
		else if (style == CONFIRM_CANCEL){
			JButton buttonCancel = new JButton("取消");
			buttonCancel.setFont(new Font("SanSerif", Font.BOLD, 30));
			buttonCancel.addActionListener(event -> {
				 dispose();
			});
			buttonPanel.add(buttonCancel);
			
			JButton buttonConfirm = new JButton("确定");
			buttonConfirm.setFont(new Font("SanSerif", Font.BOLD, 30));
			buttonConfirm.addActionListener(listener);
			buttonPanel.add(buttonConfirm);
		}
		
		add(buttonPanel, BorderLayout.SOUTH);
	}
}