
public class Stack<T extends Comparable<T>> {

	Linkedlist<T> lolo = new Linkedlist<>();
	

	public void push(T data) {
	lolo.addFirst(data);
	
	}
	public boolean isEmpty() {
		return  lolo.isEmpty();
		
	}
	public T peek() {
	if(! lolo.isEmpty())
	return lolo.head.data;
	return null;
	}
	public T pop() {
	    if (!lolo.isEmpty()) {
	        T data = lolo.head.data;
	        lolo.head = lolo.head.next;
	        return data;
	    } else {
	        System.out.println("Stack is empty");
	        return null;
	    }
	}
public void clear() {
	lolo.head=null;
	
	
	
}	public void print() {
	
	//lolo.print();
	
	System.out.print("head-->");
	Node<T> curr=lolo.head;
	
	while (curr!= null) {
		System.out.print(curr+"-->");
		curr=curr.next;
	}
	System.out.println("null");

}


}

