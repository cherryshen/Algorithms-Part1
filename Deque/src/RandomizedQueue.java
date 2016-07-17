import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        // construct an empty randomized queue

        this.items = (Item[]) new Object[2];
        this.size = 0;
    }

    public boolean isEmpty() {
        // is the queue empty?
        return this.size == 0;
    }

    public int size() {
        // return the number of items on the queue
        return this.size;
    }

    private void resize(int capacity) {
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) new Object[capacity * 2];
        for (int i = 0; i < this.size; i++) {
            copy[i] = this.items[i];   
        }
        this.items = copy;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null) {
            throw new NullPointerException("item cannot be null");
        }
        if (items.length == this.size) {
            resize(2 * items.length);
        }
        items[size] = item;
        ++this.size;
    }

    public Item dequeue() {
        // remove and return a random item
        if (this.size == 0) {
            throw new NoSuchElementException("items is empty");
        }
        int i = StdRandom.uniform(0, this.size);
        Item temp = items[i];
        if (i == this.size - 1) {
            items[i] = null;
        } else {
            items[i] = items[this.size - 1];
            items[this.size - 1] = null;
        }
        --this.size;
        return temp;
    }

    public Item sample() {
        if (this.size == 0) {
            throw new NoSuchElementException("items is empty");
        }
        // return (but do not remove) a random item
        int i = StdRandom.uniform(0, this.size);
        return items[i];
    }

    public Iterator<Item> iterator() {
        @SuppressWarnings("unchecked")
        Item[] copy = (Item[]) new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            copy[i] = items[i];
        }
        return new RandomQueueIterator(copy);
    }

    private class RandomQueueIterator implements Iterator<Item> {
        private Item[] current;
        private int currentInd;

        RandomQueueIterator(Item[] current) {
            StdRandom.shuffle(current);
            this.current = current;
            this.currentInd = 0;
        }

        @Override
        public boolean hasNext() {
            return currentInd < current.length;
        }

        @Override
        public Item next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("it is null");
            }
            return current[currentInd++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(27);
        rq.enqueue(2);
        rq.enqueue(16);
        rq.enqueue(18);
        rq.enqueue(21);
        rq.enqueue(190);

        for (int i : rq) {
            System.out.println(i);
        }
        
//        Iterator<Integer> it = rq.iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next());
//        }
    }
}

/**
 * Corner cases. throw a java.lang.UnsupportedOperationException if the client
 * calls the remove() method in the iterator; throw a
 * java.util.NoSuchElementException if the client calls the next() method in the
 * iterator and there are no more items to return.
 */
