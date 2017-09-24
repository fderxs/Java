package graph_creat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import gui.message_dialog;

public class textAnalyze extends bridge{
	public boolean CORRECT = true;
	
	public textAnalyze(String name){
		try{
			File file_in = new File(name);
			
			FileInputStream inStream;
			inStream = new FileInputStream(file_in);
			
			boolean is_space = true;
			int type;
			int total = 0;
			while ((type = inStream.read()) != -1){
				if (++total >= MAXN) CORRECT = false;
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
			if (CORRECT){
				IOtext =  str.split(" ");
				split();
				make_gragh();
				print_out("graph.dot", "graph.png");
			}
		}
		catch (IOException e){
			message_dialog message = new message_dialog();
			message.showdialog("I/O error occurred", "error", "Error", message_dialog.CONFIRM);
		}
	}
}
