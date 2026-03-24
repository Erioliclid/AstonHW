package HW1;

public interface CustomMap<K,V> {
    void put(K key, V value);
    V get(K key);
    void remove(K key);
    int size();

}
