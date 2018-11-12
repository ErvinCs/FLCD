import Auxiliary.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of all the variables & constants used in the program.
 * Holds multiple Node<K, V> elements where:
 * @param <K> is the identifier name or constant value
 * @param <V> it's position in the symbol table
 */
public class SymbolTable<K, V> {
    // bucketArray is used to store array of chains
    private ArrayList<Node<K, V>> bucketArray;

    // Current capacity of array list
    private int numBuckets;

    // Current size of array list
    private int size;

    // Constructor (Initializes capacity, size and
    // empty chains.
    public SymbolTable()
    {
        bucketArray = new ArrayList<>();
        numBuckets = 100;
        size = 0;

        // Create empty chains
        for (int i = 0; i < numBuckets; i++)
            bucketArray.add(null);
    }

    public int size() { return size; }
    public boolean isEmpty() { return size() == 0; }

    // This implements hash function to find index
    // for a key
    private int getBucketIndex(K key)
    {
        int hashCode = key.hashCode();
        int index = hashCode % numBuckets;
        return index;
    }

    // Method to remove a given key
    public V remove(K key)
    {
        // Apply hash function to find index for given key
        int bucketIndex = getBucketIndex(key);

        // Get head of chain
        Node<K, V> head = bucketArray.get(bucketIndex);

        // Search for key in its chain
        Node<K, V> prev = null;
        while (head != null)
        {
            // If Key found
            if (head.getKey().equals(key))
                break;

            // Else keep moving in chain
            prev = head;
            head = head.getNext();
        }

        // If key was not there
        if (head == null)
            return null;

        // Reduce size
        size--;

        // Remove key
        if (prev != null)
            prev.setNext(head.getNext());
        else
            bucketArray.set(bucketIndex, head.getNext());

        return head.getValue();
    }

    // Returns value for a key
    public V get(K key)
    {
        // Find head of chain for given key
        int bucketIndex = getBucketIndex(key);
        Node<K, V> head = bucketArray.get(bucketIndex);

        // Search key in chain
        while (head != null)
        {
            if (head.getKey().equals(key))
                return head.getValue();
            head = head.getNext();
        }

        // If key not found
        return null;
    }

    // Adds a key value pair to hash
    public void add(K key, V value)
    {
        // Find head of chain for given key
        int bucketIndex = getBucketIndex(key);
        Node<K, V> head = bucketArray.get(bucketIndex);

        // Check if key is already present
        while (head != null)
        {
            if (head.getKey().equals(key))
            {
                head.setValue(value);
                return;
            }
            head.setNext(head.getNext());
        }

        // Insert key in chain
        size++;
        head = bucketArray.get(bucketIndex);
        Node<K, V> newNode = new Node<K, V>(key, value);
        newNode.setNext(head);
        bucketArray.set(bucketIndex, newNode);

        // If load factor goes beyond threshold, then
        // double hash table size
        if ((1.0*size)/numBuckets >= 0.7)
        {
            ArrayList<Node<K, V>> temp = bucketArray;
            bucketArray = new ArrayList<>();
            numBuckets = 2 * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++)
                bucketArray.add(null);

            for (Node<K, V> headNode : temp)
            {
                while (headNode != null)
                {
                    add(headNode.getKey(), headNode.getValue());
                    headNode = headNode.getNext();
                }
            }
        }
    }

    @Override
    public String toString() {
        String output = "ST:\nKey  Value\n";
        for(Node<K, V> node : this.bucketArray) {
            if (node != null) {
                output += node.toString();
                while (node.getNext() != null) {
                    node = node.getNext();
                    output += node.toString();
                }
            }
        }
        return output;
    }
}
