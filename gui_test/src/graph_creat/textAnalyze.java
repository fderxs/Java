package graph_creat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import gui.message_dialog;

public class textAnalyze extends creat_png{
	public textAnalyze(String name){
		try{
			File file_in = new File(name);
			
			FileInputStream inStream;
			inStream = new FileInputStream(file_in);
			
			boolean is_space = true;
			int type;
			while ((type = inStream.read()) != -1){
				char chr = (char) type;
				initialText = initialText + chr;
				if (chr >= 'a' && chr <='z' || chr >= 'A' && chr <='Z'){
					if (chr >= 'A' && chr <='Z') type += 32;
					str =str + (char)type;
					is_space = true;
				}
				else{
					if (is_space == true){
						str = str + (char)32;
						is_space = false;
					}
				}
			}
			inStream.close();
			IOtext =  str.split(" ");
			split();
			make_gragh();
			print_out("graph.dot", "graph.png");
		}
		catch (IOException e){
			new message_dialog("I/O error occurred", "timg_error", "Error", message_dialog.CONFIRM, null);
		}
		
	}
}
