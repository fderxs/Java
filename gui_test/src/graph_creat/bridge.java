package graph_creat;

import java.util.Random;
import java.util.Stack;  
public class bridge extends string_split{
	private static int[][] A = new int[word_num][word_num]; 
	private static int[][] Path = new int[word_num][word_num];
	private static   int max = 1000;			//设定最大值
	public static String queryBridgeWords(String word1,String word2){
		int t1 = -1,t2 = -1;
		int[] bri = new int[10];
		int brinum = 0;
		String tempstr = "";
		for(int i = 0; i < word_num; i++){
		if(word1.equals(word_list[i]))		//找到第一个词
			t1 = i;
		if(word2.equals(word_list[i]))
			t2 = i;
		}
		if(t1 == -1 | t2 == -1){	
			if(t1 == -1)
				tempstr+= "No word1 in Graph!";
			if(t2 == -1)
				tempstr += "No word2 in Graph!";
			return tempstr;
			
		}
		else {
		for(int i = 0; i< word_num; i++){
			if(w[t1][i]>0 & w[i][t2]>0){
				bri[brinum] = i;
				brinum++;
			}
		}
		if(brinum == 0)
			tempstr += "No bridge words from word1 to word2!" ;
		else if(brinum == 1)
			tempstr =tempstr + "The bridge word from word1 to word2 is: " + word_list[bri[0]];
		else{
			 tempstr +="The bridge words from word1 to word2 are: ";
			for(int i =0;i<brinum-1;i++)
				tempstr = tempstr + word_list[bri[i]] + ",";
			tempstr  = tempstr + "and " + word_list[bri[brinum]];
			
		}
		return tempstr;
		}
	}
	public static String generateNewText(String inputText){
		String[] usertxt ;
		usertxt = inputText.split("\\s+");
		String tempstr = "";
		for(int j = 0; j <usertxt.length-1;j++){
			int t1 = -1,t2 = -1;
			int[] bri = new int[10];
			int brinum = 0;
			for(int i = 0; i < word_num; i++){
			if(usertxt[j].equals(word_list[i]))		//找到第一个词
				t1 = i;
			if(usertxt[j+1].equals(word_list[i]))	//找到第二个词
				t2 = i;
			}
			if(t1 == -1 | t2 == -1){	//不存在单词继续循环
				tempstr += usertxt[j];
				tempstr += " ";
			//	j++;
			}
			else{
				for(int i = 0; i< word_num; i++){
					if(w[t1][i]>0 & w[i][t2]>0){
						bri[brinum] = i;
						brinum++;
					}
			}
				if(brinum == 0){
					tempstr += usertxt[j];
					tempstr += " ";
				//	j++;//不存在桥接词继续循环
				}
				else{
					Random random = new Random();
					tempstr += usertxt[j];
					tempstr += " ";
					tempstr += word_list[bri[random.nextInt(brinum)]];
					tempstr += " ";
			//		j++;
				}
		}
	}
		tempstr += usertxt[usertxt.length-1];
		//System.out.println(tempstr);
		return tempstr;
	}
	public static String calcShortestPath(String word1, String word2){
		String tempstr = "";
		for(int i = 0;i<word_num;i++)
			for(int j = 0 ; j<word_num;j++){
				if(w[i][j] == 0)
					A[i][j] =	max; 
				else
					A[i][j] = 1 ;
				Path[i][j] = -1;
			}
		for(int k=0;k<word_num;k++)
		      for(int i=0;i<word_num;i++)
		         for(int j=0;j<word_num;j++)
		             if(A[i][j]>(A[i][k]+A[k][j])){
		                  A[i][j]=A[i][k]+A[k][j];
		                  Path[i][j]=k;
		              } 
		int t1=-1,t2=-1;
		for(int i = 0; i < word_num; i++){
			if(word1.equals(word_list[i]))		//找到第一个词
				t1 = i;
			if(word2.equals(word_list[i]))
				t2 = i;
			}
	    Stack<String> sk = new Stack<String>();
		if(t2 != -1 & t1 != -1 ){
		sk.push(word2);
		if(t1 != t2){
			if(A[t1][t2] == max)
				return "Out of reach ";
			else{
				int k = t2;
				  k = Path[t1][k];
				while ( k != -1 )
               {
                 
                   sk.push(word_list[k]);
                   k = Path[t1][k];
               }
			}
		}
		sk.push(word1);
		}
		while(!sk.empty()){
		tempstr = tempstr + sk.pop() +" ";
		}
		return tempstr;
	}
	public static String calcShortestPath(String word){
		String tempstr = "";
		int t1 = -1;
		for(int i = 0; i < word_num; i++)
			if(word.equals(word_list[i]))		//找到第一个词
				t1 = i;
		 Stack<String> sk = new Stack<String>();
		
		if(t1 != -1){
		for(int i = 0; i<word_num;i++){
			if(i == t1)
				continue;
			else
			{ if(A[t1][i] == max)
				tempstr  = tempstr + word +" out of reach " + word_list[i] +"\n";
			else{
				sk.push(word_list[i]);
				int k = i;
				  k = Path[t1][k];
				while ( k != -1 )
             {
               
                 sk.push(word_list[k]);
                 k = Path[t1][k];
             }
				sk.push(word);
				while(!sk.empty()){
					tempstr = tempstr + sk.pop() +" ";
					}
				tempstr += "\n";
			}
			}
		}
		}
		
		return tempstr;
	}
	public static String randomWalk(){
		String tmpstr = "";
		int t1;
		Random random = new Random();
		t1 = random.nextInt(word_num);
		int edge_num = 0;	//存储边数
		int[] edge = new int [word_num];	//存储边
		int[][] B = A.clone();
	//	Stack<String> sk = new Stack<String>();
		//sk.push(word_list[t1]);
		tmpstr = tmpstr + word_list[t1] + " ";
		int tmpedge;
		while(true)
		{
			edge_num = 0;
			for(int i =0;i<word_num;i++){
				if(B[t1][i] == 1){
					edge[edge_num] = i;		//在边数组内存入邻接顶点编号
					edge_num++;
				}
				}
			if(edge_num == 0)
				break;
			else{
				tmpedge = edge[random.nextInt(edge_num)];
				B[t1][tmpedge] = max ;
				t1 = tmpedge;
				//sk.push(word_list[t1]);
				tmpstr = tmpstr + word_list[t1] + " ";
				
			}
		}
		return tmpstr;
	}
}

