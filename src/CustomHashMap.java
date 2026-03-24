
public class CustomHashMap<K, V> implements CustomMap<K,V> {
    private Node<K, V>[] table;
    private int size;
    private int threshold;
    private float loadFactor;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public CustomHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public CustomHashMap(int initCapacity, float loadFactor) {
        this.loadFactor = loadFactor;
        table = new Node[initCapacity];
        threshold = (int) (initCapacity * loadFactor);
    }

    class Node<K, V> {
        int hash;
        K key;
        V value;
        Node<K, V> next;

        public Node(int hash, K key, V value, Node next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int getIndex(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        return hash & (table.length - 1);
        // Я не сделал проверку на соответствие размера массива степени двойки и
        // пропустил перемешку битов, поскольку решил использовать только то, что смогу
        // объяснить сам, если меня спросят. В битах я немного разбираюсь, но оперировать ими
        // и решать в уме конкретные операции мне сложно. Но с return я разобрался.
    }

    public void put(K key, V value) {
        int index = getIndex(key);
        Node<K, V> node = table[index];

        while (node != null) {
            if (node.hash == key.hashCode() &&
                    (node.key == key || node.key.equals(key))) {
                node.value = value;
                return;
            }
            node = node.next;
        }
        table[index] = new Node<>(key.hashCode(), key, value, table[index]);
        size++;
        if (size > threshold) {
            resize();
        }
    }

    public V get(K key) {
        int index = getIndex(key);
        int hash = key.hashCode();
        Node<K, V> node = table[index];
        while (node != null) {
            if (node.hash == hash &&
                    (node.key == key || node.key.equals(key))) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    private void resize() {
        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length * 2];
        threshold = (int) (table.length * loadFactor);

        for (int i = 0; i < oldTable.length; i++) {
            Node<K, V> node = oldTable[i];
            while (node != null) {
                Node<K, V> next = node.next;
                int newIndex = getIndex(node.key);
                node.next = table[newIndex];
                table[newIndex] = node;
                node = next;
            }
        }
    }

    public void remove(K key) {
        int index = getIndex(key);
        int hash = key.hashCode();
        Node<K, V> current = table[index];
        Node<K, V> prev = null;

        while (current != null) {
            if (current.hash == hash &&
                    (current.key == key || current.key.equals(key))) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    public int size() {
        return size;
    }
}

