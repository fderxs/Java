package graph_creat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import gui.message_dialog;

public class creat_png extends string_split{
	
	public creat_png() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void print_out(String str, String str_pic){
		try{
			File file_out = new File(str);
			if (!file_out.exists()){
				file_out.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file_out);
			BufferedWriter outstream = new BufferedWriter(fw);
			
			String s = "digraph ha{ ";
			outstream.write(s);
			
			for (int i = 0; i < word_num; i++){
				s = word_list[i] + "; ";
				outstream.write(s);
			}
			
			for (int i = 0; i < word_num; i++){
				for (int j = 0; j < word_num; j++){
					if (w[i][j] >0){
						s = word_list[i] + " -> " + word_list[j] + "[label = \"" + w[i][j] + "\"];";
						outstream.write(s);
					}
				}
			}
			outstream.write("}");
			outstream.close();
			
			File directory = new File("");
			String path = directory.getCanonicalPath();
			
			Runtime run = Runtime.getRuntime();
			run.exec("dot -Tpng " + path + "\\" + str + " -o " + str_pic);
			
			
		}
		catch (IOException e){
			new message_dialog("I/O error occurred", "timg_error", "Error", message_dialog.CONFIRM, null);
		}
	}
}
