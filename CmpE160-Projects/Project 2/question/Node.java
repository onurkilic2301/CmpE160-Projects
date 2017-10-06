package question;

public class Node<T> implements Comparable {
	String word="";
	boolean isLeaf;
	char c;
	T value;
	Node<T>[] children;
	
	public Node(int alphabetSize,boolean isLeaf,T value){
		this.isLeaf=isLeaf;
		children =new Node[alphabetSize];
		this.value=value;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Node<T> node=(Node<T>)o;
		if(node!=null&&node.value instanceof Integer){
			if((int)value-(int)node.value!=0)
				return (int)value-(int)node.value;
			else
				return (-1)*this.word.compareTo(node.word);
		}
		else 
			return 0;
	}
	
}
