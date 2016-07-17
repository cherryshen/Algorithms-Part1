import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;

    // construct an empty deque

    public Deque() {
        this.first = null;
        this.last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.first == null;
    }

    // return the number of items on the deque
    public int size() {
        Node<Item> current = this.first;
        int size = 0;
        while (current != null) {
            ++size;
            current = current.next;
        }
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("item cannot be null");
        }
        Node<Item> newItem = new Node<Item>(item);
        if (this.first == null) {
            this.first = newItem;
            this.last = newItem;
        } else {
            Node<Item> temp = this.first;
            temp.prev = newItem;
            this.first = newItem;
            newItem.next = temp;
        }
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("item cannot be null");
        }
        Node<Item> newItem = new Node<Item>(item);
        if (this.first == null) {
            this.first = newItem;
            this.last = newItem;
        } else {
            Node<Item> temp = this.last;
            newItem.prev = temp;
            this.last = newItem;
            temp.next = newItem;
        }
    }

    public Item removeFirst() { // remove and return the item from the front
        if (this.isEmpty()) {
            throw new NoSuchElementException("it is empty");
        }
        Node<Item> temp = this.first;

        if (this.first == this.last) {
            this.first = null;
            this.last = null;
        } else {
            this.first = this.first.next;
            this.first.prev = null;
        }
        return temp.data;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("it is empty");
        }
        Node<Item> temp = this.last;
        if (this.first == this.last) {
            this.first = null;
            this.last = null;
        } else {

            this.last = this.last.prev;
            this.last.next = null;

        }
        return temp.data;
    }

    public Iterator<Item> iterator() { // return an iterator over items in order
        return new NodeIterator(this.first); // from front to end
    }

    private class NodeIterator implements Iterator<Item> {
        private Node<Item> current;

        NodeIterator(Node<Item> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("no more items left");
            }
            Node<Item> temp = this.current;
            this.current = this.current.next;
            return temp.data;
        
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private static class Node<Item> {
        private Item data;
        private Node<Item> next;
        private Node<Item> prev;

        Node(Item data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    public static void main(String[] args) { // unit testing
        Deque<Integer> deque = new Deque<Integer>();

        deque.addFirst(0);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        
        for (Integer i : deque) {
            System.out.println(i);
        }
    }
}
