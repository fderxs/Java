package graph_creat;

public class string_split extends IO{
	private static int max_num = 400;
	public static String[] word_list = new String[max_num];
	public static String[] IOtext = IO.str.split(" ");
	public static int[][] w = new int[max_num][max_num];
	public static int word_num;
	
	public static void split(){
		word_num = 0;
		for (int i = 0; i < IOtext.length; i++){
			boolean p = true;
			for (int j = 0; j < word_num; j++){
				if (IOtext[i].equals(word_list[j])){
					p = false;
					break;
				}
			}
			if (p == true){
				word_list[word_num++] = IOtext[i];
			}	
		}
		for (int i = 0; i < word_num; i++){
			System.out.println(word_list[i]);
		}
	}
	
	public static void make_gragh(){
		int t1 = 0, t2 = 0;
		for (int i = 0; i < IOtext.length - 1; i++){
			for (int j = 0; j < word_list.length; j++){
				if (IOtext[i].equals(word_list[j]))
					t1 = j;
				if (IOtext[i + 1].equals(word_list[j]))
					t2 = j;
			}
			++w[t1][t2];
		}
	}
}