package sample.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BiMap<K, V> {
    private Map<K, V> objects;
    private Map<V, K> keys;

    public <K, V>BiMap()
    {
        objects = new <K, V>HashMap();
        keys = new <V, K>HashMap();
    }

    public void put(K key, V value)
    {
        objects.put(key, value);
        keys.put(value, key);
    }

    public void removeKey(K key)
    {
        V value = objects.remove(key);
        keys.remove(value);
    }
    public void removeValue(V value)
    {
        K key = keys.remove(value);
        objects.remove(key);
    }

    public K getKey(V value)
    {
        return keys.get(value);
    }
    public V getValue(K key)
    {
        return objects.get(key);
    }
    public Set<K> getKeys()
    {
        return objects.keySet();
    }
    public Set<V> getValues()
    {
        return keys.keySet();
    }
    public boolean ContainsKey(K key)
    {
        return objects.containsKey(key);
    }
}
