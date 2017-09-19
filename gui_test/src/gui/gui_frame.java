package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class gui_frame{
	public static void main(String[] args){
		EventQueue.invokeLater(() ->
			{
				JFrame frame = new SimpleFrame();
				frame.setTitle("软工实验一");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			});
		//graph_creat.IO.operate_text("text.txt", "output.txt");
	}
}