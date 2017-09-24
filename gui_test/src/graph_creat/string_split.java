package graph_creat;

public class string_split{
	public final static int MAXN = 100;
	public String str = "";
	public String initialText = "";
	public String[] word_list = new String[MAXN];
	public String[] IOtext;
	public int[][] w = new int[MAXN][MAXN];
	public int word_num;
	public int[][] A = new int[MAXN][MAXN]; 
	public int[][] Path = new int[MAXN][MAXN];
	
	public string_split() {
		// TODO Auto-generated constructor stub
	}

	public void split(){
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
	}
	
	public void make_gragh(){
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