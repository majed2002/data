
 class Node<T>{
	 
	 public T data;
	 public Node<T> next;
	 public Node (T value) {
		 data=value;
		 next=null;
	 }
 }
public class LinkedList<T> implements List<T> {
	
	private Node<T> head;
	private Node<T> current;
	
	public LinkedList() {
		head=null;
		current =null;
		
	}
	@Override
	public boolean empty() {
		if(head==null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean full() {
		return false;
	}

	@Override
	public void findFirst() {
		current =head;

	}

	@Override
	public void findNext() {
		current=current.next;

	}

	@Override
	public boolean last() {
		if(current.next==null)
			return true;
		
		return false;
	}

	@Override
	public T retrieve() {
		return current.data;
	}

	@Override
	public void update(T e) {
		current.data=e;

	}

	@Override
	public void insert(T e) {
		Node<T> p = new Node<T>(e);
		if(head == null) {
			head=p;
			current=p;
		}
		
		else {
		p.next=current.next;
		current.next=p;
		current=p;
		}

	}

	@Override
	public void remove() {
		if(current == head) {
			head=head.next;
			current=current.next;
		}
		else {
		Node<T>p=head;
		while(p.next!=current)
			p=p.next;
		
		p.next=current.next;
		if(current.next != null)
			current=current.next;
		current=head;
		}
	}

}
