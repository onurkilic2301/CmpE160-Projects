package question;
import java.util.ArrayList;
import java.util.Scanner;

import question.TrieMap;

public class Main {
	
	public static void main(String[] args) {
		TrieMap<String> myMap = new TrieMap<String>(10);
		myMap.insert("cc", "v1");
		myMap.insert("baac","v2");
		myMap.insert("b", "v3");
		myMap.insert("bab","v4");
		myMap.insert("a","v12");
		//testContains("asofdddkgn","");
		//testReturnBfsOrder(myMap);
		//testWordCount("a b c a d e a e e f f g h i k l j m","");
		testUniqueWords("aadlk b c slka d e a e e f f g h i k l j m");
		//testAutocomplete("","");
	}
	static String[] add(String st){
		Scanner s=new Scanner(st);
		ArrayList<String> list=new ArrayList<String>();
		
		while(s.hasNext()){
			list.add(s.next());
		}
		String[] a=new String[list.size()];
		for(int i=0;i<list.size();i++){
			a[i]=list.get(i);
			
		}
		
		return  a;
	}
	static void testReturnBfsOrder(TrieMap<String> myMap){
		ArrayList<String> bfsOrder = myMap.returnBFSOrder();
		for (String str : bfsOrder) System.out.println(str);
	}
	static void testContains(String text,String key){
		System.out.println(TrieMap.contains(text, key)); 
	}

	static void testWordCount(String book,String word){
		System.out.println(TrieMap.wordCount(book,word));
	}

	static void testUniqueWords(String s){
		String[] a=TrieMap.uniqueWords(s);
		for(int i=0;i<a.length;i++)
			System.out.println(a[i]);
	}

	static void testAutocomplete(String hist,String comp){
		String[][] a;
		String[] b=add(hist);
		String[] c=add(comp);
		a=TrieMap.autoComplete(b, c);
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[i].length;j++){
				System.out.println(a[i][j]);
			}
			System.out.println();
		}
	}
}
