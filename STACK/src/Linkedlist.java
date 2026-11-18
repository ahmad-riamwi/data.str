public class Linkedlist<T extends Comparable<? super T>> {

    Node<T> head;

    public Linkedlist() {
        head = null;
    }

    public void addFirst(T data) {
    	Node<T> nono = new Node<>(data);

        nono.next = head;
        head = nono;
    }

    public void print() {
    	Node<T> curr = head;

        System.out.print("first-->");

        while (curr != null) {
            System.out.print(curr.data + "-->");
            curr = curr.next;
        }

        System.out.println("null");
    }

    public Node<T> find(T data) {
    	Node<T> curr = head;

        while (curr != null) {
            if (data.compareTo(curr.data) == 0)
                return curr;

            curr = curr.next;
        }

        return null;
    }

    public Node<T> deleat(T tar) {
    	Node<T> curr = head;
    	Node<T> prev = null;

        while (curr != null) {
            if (tar.compareTo(curr.data) == 0) {

                if (prev == null)
                    head = curr.next;
                else
                    prev.next = curr.next;

                return curr;
            }

            prev = curr;
            curr = curr.next;
        }

        return null;
    }

    public int len() {
        int count = 0;
        Node<T> curr = head;

        while (curr != null) {
            curr = curr.next;
            count++;
        }

        return count;
    }

    public void addlast(T data) {
    	Node<T> nono = new Node<>(data);

        if (head == null) {
            head = nono;
            return;
        }

        Node<T> curr = head;

        while (curr.next != null) {
            curr = curr.next;
        }

        curr.next = nono;
    }

    // Post Lab 3 Linked List

    public void addSorted(T data) {
    	Node<T> nono = new Node<>(data);

    	Node<T> curr = head;
    	Node<T> prev = null;

        while (curr != null && data.compareTo(curr.data) > 0) {
            prev = curr;
            curr = curr.next;
        }

        nono.next = curr;

        if (prev == null)
            head = nono;
        else
            prev.next = nono;
    }

    public Node<T> deleatSorted(T data) {
    	Node<T> curr = head;
    	Node<T> prev = null;

        while (curr != null && data.compareTo(curr.data) > 0) {
            prev = curr;
            curr = curr.next;
        }

        if (curr != null && data.compareTo(curr.data) == 0) {

            if (prev == null)
                head = curr.next;
            else
                prev.next = curr.next;

            return curr;
        }

        return null;
    }

    private int binery(int ans, Node<T> curr) {
        if (curr != null) {
            ans = (ans * 10) + (Integer) curr.data;
            return binery(ans, curr.next);
        }

        return ans;
    }

    public int binery() {
        return binery(0, head);
    }

    private int sum(int ans, Node<T> curr) {
        if (curr != null) {
            ans = ans + (Integer) curr.data;
            return sum(ans, curr.next);
        }

        return ans;
    }

    public int sum() {
        return sum(0, head);
    }

    private int count(Node<T> curr, T tar) {
        if (curr != null) {
            if (tar.compareTo(curr.data) == 0)
                return 1 + count(curr.next, tar);

            return count(curr.next, tar);
        }

        return 0;
    }

    public int count(T tar) {
        return count(head, tar);
    }

    private void d(Node<T> curr, Node<T> prev, int n) {
        if (curr != null) {
            if (n % 2 == 0) {
                prev.next = curr.next;
                d(curr.next, prev, n + 1);
            } else {
                d(curr.next, curr, n + 1);
            }
        }
    }

    public void d() {
        if (head != null)
            d(head.next, head, 2);
    }

    private void rem(Node<T> curr) {
        if (curr != null && curr.next != null) {

            if (curr.data.compareTo(curr.next.data) == 0) {
                curr.next = curr.next.next;
                rem(curr);
            } else {
                rem(curr.next);
            }
        }
    }

    public void rem() {
        rem(head);
    }

    private int even(Node<T> curr) {
        if (curr != null) {
            if ((Integer) curr.data % 2 == 0)
                return 1 + even(curr.next);

            return even(curr.next);
        }

        return 0;
    }

    public int even() {
        return even(head);
    }

    private int b(Node<T> curr, int n) {
        if (curr != null) {
            n = n * 2 + (Integer) curr.data;
            return b(curr.next, n);
        }

        return n;
    }

    public int b() {
        return b(head, 0);
    }
    public boolean isEmpty() {
    	
    	if(head.data==null)
    		return true;
    	return false;
    }
}