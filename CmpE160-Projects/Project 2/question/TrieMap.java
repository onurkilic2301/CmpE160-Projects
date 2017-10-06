package question;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TrieMap<T> extends TrieMapBase<T> {
	Node<T> root;
	int alphabetSize;
	public TrieMap(int alphabetSize){
		this.alphabetSize=alphabetSize;
		root=new Node<T>(alphabetSize, false, null);
	}
	/**
	 * Returns 	true if key appears in text as a substring;
	 * 			false, otherwise
	 * 
	 * Use Trie data structure to solve the problem
	 */
	public static boolean contains(String text, String key) {	

		TrieMap<Integer> tm=new TrieMap(26);
		tm.insert(text, 1);
		return tm.substringSearch(text, key);
	}

	/**
	 * Returns how many times the word in the parameter appears in the book.
	 * Each word in book is separated by a white space. 
	 * 
	 * Use Trie data structure to solve the problem
	 */
	public static int wordCount(String book, String word) {	
		Scanner s=new Scanner(book);
		TrieMap<Integer> tm=new TrieMap(26);
		while(s.hasNext()){
			String str=s.next();
			if(tm.search(str)!=null)
				tm.insert(str, tm.search(str)+1);
			else
				tm.insert(str, 1);
		}
		if(tm.search(word)!=null)
			return tm.search(word);
		else
			return 0;

	}

	/**
	 * Returns the array of unique words in the book given as parameter.
	 * Each word in book is separated by a white space.
	 *  
	 * Use Trie data structure to solve the problem
	 */
	public static String[] uniqueWords(String book) {
		Scanner s=new Scanner(book);
		TrieMap<String> tm=new TrieMap(26);
		while(s.hasNext()){
			String str=s.next();
			tm.insert(str, str);
		}
		ArrayList<String> list=tm.returnBFSOrder();
		String[] a=new String[list.size()];
		for(int i=0;i<list.size();i++){
			a[i]=list.get(i);
		}
		Arrays.sort(a);
		return a;

	}

	/**
	 * Recommends word completions based on the user history.
	 * 
	 * Among all the strings in the user history, the method takes 
	 * those that start with a given incomplete word S, 
	 * sort the words according to their frequencies (how many 
	 * times they are written), and recommend the 3 most frequently written ones.
	 * 
	 * @param userHistory 
	 * 			the words written previously by the user
	 * 
	 * @param incompleteWords 
	 * 			the list of strings to be autocompleted
	 * @return 
	 * 			a Sx3 array that contains the recommendations
	 * 			for each word to be autocompleted.
	 * 
	 * Use Trie data structure to solve the problem
	 */
	public static String[][] autoComplete(String[] userHistory, String[] incompleteWords){
		TrieMap<Integer> tm=new TrieMap(26);
		for(int i=0;i<userHistory.length;i++){
			String str=userHistory[i];
			if(tm.search(str)!=null)
				tm.insert(str, tm.search(str)+1);
			else
				tm.insert(str, 1);
		}
		String[][] arr=new String[incompleteWords.length][3];
		for(int i=0;i<incompleteWords.length;i++){
			ArrayList<Node<Integer>> list=tm.wordsStartsWith(incompleteWords[i]);
			if(list!=null){
				Collections.sort(list);
				for(int j=0;j<3;j++){
					if(!list.isEmpty())
						arr[i][j]=list.remove(list.size()-1).word;
				}
			}
		}

		return arr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(String key, T value) {
		// TODO Auto-generated method stub


		Node<T> temp=root;
		for(int i=0;i<key.length();i++){
			if(temp.children[key.charAt(i)%'a']==null&&i!=key.length()-1){
				temp.children[key.charAt(i)%'a']=new Node<T>(this.alphabetSize, false, null);
				temp.children[key.charAt(i)%'a'].c=key.charAt(i);
				temp.children[key.charAt(i)%'a'].word+=temp.word+key.charAt(i);
				temp=temp.children[key.charAt(i)%'a'];
			}
			else if(temp.children[key.charAt(i)%'a']==null&&i==key.length()-1){
				temp.children[key.charAt(i)%'a']=new Node<T>(this.alphabetSize, true, value);
				temp.children[key.charAt(i)%'a'].c=key.charAt(i);
				temp.children[key.charAt(i)%'a'].word+=temp.word+key.charAt(i);
			}
			else if(temp.children[key.charAt(i)%'a']!=null&&i==key.length()-1){
				temp.children[key.charAt(i)%'a'].isLeaf=true;
				temp.children[key.charAt(i)%'a'].value=value;
			}	
			else{
				temp=temp.children[key.charAt(i)%'a'];
			}
		}
	}

	@Override
	public boolean delete(String key) {
		// TODO Auto-generated method stub

		Node<T> temp= root;
		for(int i=0;i<key.length();i++){
			char c=key.charAt(i);
			if(temp.children[c%'a']==null)
				return false;
			else
				temp=temp.children[c%'a'];
		}
		if(temp.isLeaf){
			temp.value=null;
			temp.isLeaf=false;
			return true;
		}
		else
			return false;

	}

	@Override
	public T search(String key) {
		// TODO Auto-generated method stub

		Node<T> temp=root;
		for(int i=0;i<key.length();i++){
			char c=key.charAt(i);
			if(temp.children[c%'a']==null)
				return null;
			else
				temp=temp.children[c%'a'];
		}
		if(temp.isLeaf)	
			return temp.value;
		else
			return null;


	}

	@Override
	public int nodeCount() {
		// TODO Auto-generated method stub
		if(root==null)
			return 0;
		int counter=0;
		Node<T> temp=root;
		Queue<Node<T>> q= new LinkedList<Node<T>>();
		q.add(root);
		while(!q.isEmpty()){
			temp=q.remove();
			for (int i=0; i<alphabetSize; i++){
				if (temp.children[i] != null){
					q.add(temp.children[i]);
				}
			}
			counter++;
		}
		return counter;
	}

	@Override
	public ArrayList<T> returnBFSOrder() {
		// TODO Auto-generated method stub
		if(root==null)
			return null;
		ArrayList<T> list = new ArrayList<T>();
		Node<T> temp=root;
		Queue<Node<T>> q= new LinkedList<Node<T>>();
		q.add(root);
		while(!q.isEmpty()){
			temp=q.remove();
			for (int i=0; i<alphabetSize; i++){
				if (temp.children[i] != null){
					q.add(temp.children[i]);
				}
			}
			if(temp.isLeaf)
				list.add(temp.value);
		}
		return list;
	}


	public ArrayList<Node<T>> wordsStartsWith(String key){
		if(root==null)
			return null;
		Node<T> temp=root;
		Queue<Node<T>> q= new LinkedList<Node<T>>();
		ArrayList<Node<T>> list=new ArrayList<Node<T>>();
		for(int i=0;i<key.length();i++){
			if(temp.children[key.charAt(i)-'a']==null)
				return null;
			else
				temp=temp.children[key.charAt(i)-'a'];
		}
		q.add(temp);
		while(!q.isEmpty()){
			temp=q.remove();
			for (int i=0; i<alphabetSize; i++){
				if (temp.children[i] != null){
					q.add(temp.children[i]);
				}
			}
			if(temp.isLeaf)	
				list.add(temp);
		}
		return list;
	}
	public boolean substringSearch(String text,String key){
		for(int i=0;i<text.length();i++){
			if(checkSubstring(key))
				return true;
			else
				root=root.children[text.charAt(i)-'a'];
		}
		if(checkSubstring(key))
			return true;
		else
			return false;
	}
	public boolean checkSubstring(String key){
		if(root==null)
			return false;
		Node<T> temp=root;
		for(int i=0;temp!=null&&i<key.length();i++)
			temp=temp.children[key.charAt(i)-'a'];
		if(temp==null)
			return false;
		else
			return true;

	}
}
