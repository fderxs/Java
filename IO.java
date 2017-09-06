package io;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class IO {
	public static void operate_text(String str_rd, String str_wr){
		try{
			File file_in = new File(str_rd);
			File file_out = new File(str_wr);
			
			if (!file_in.exists()){
				System.out.println("No file or directory with a relative path name " + " of " + str_rd + " exits.");
			}
			else if (file_in.isFile()) {
				System.out.println("A file with name " + str_rd + " exists");
			}
			else if (file_in.isDirectory()){
				System.out.println("A directory with name " + str_rd + " exists");
			}
			
			if (!file_out.exists()){
				file_out.createNewFile();
			}
			else{
				System.out.println("A file/directory with name " + str_wr + " exists!");
			}

			FileInputStream inStream;
			//DataInputStream datainstream;
			inStream = new FileInputStream(file_in);
			//datainstream = new DataInputStream(inStream);
			
			FileOutputStream outStream;
			//DataOutputStream dataoutstream;
			outStream = new FileOutputStream(file_out);
			//dataoutstream = new DataOutputStream(outStream);
			boolean is_space = true;
			int type;
			while ((type = inStream.read()) != -1){
				//char chr = datainstream.readChar();
				char chr = (char) type;
				if (chr >= 'a' && chr <='z' || chr >= 'A' && chr <='Z'){
					//dataoutstream.writeChar(chr);
					outStream.write(type);
					is_space = true;
				}
				else{
					if (is_space == true){
						//dataoutstream.writeChar(' ');
						outStream.write(32);
						is_space = false;
					}
				}
			}
			inStream.close();
			outStream.close();
			
		}
		catch (IOException e){
			System.out.println("I/O error occurred");
		}
		
		
	}
	public static void operator_text(String str){
		
	}

	public static void main(String[] args) {
		operate_text("text.txt", "output.txt");
		
	}

}
