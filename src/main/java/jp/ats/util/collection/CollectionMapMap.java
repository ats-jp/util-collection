package jp.ats.util.collection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author 千葉 哲嗣
 */
public class CollectionMapMap<K1, K2, V> implements Cloneable {

	private final Map<K1, CollectionMap<K2, V>> map;

	public static <V, K1, K2> CollectionMapMap<K1, K2, V> newInstance() {
		return new CollectionMapMap<K1, K2, V>();
	}

	public static <V, K1, K2> CollectionMapMap<K1, K2, V> newInstance(
			@SuppressWarnings("rawtypes") Class<? extends Map> mapClass) {
		return new CollectionMapMap<K1, K2, V>(mapClass);
	}

	public CollectionMapMap() {
		map = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	public CollectionMapMap(@SuppressWarnings("rawtypes") Class<? extends Map> mapClass) {
		try {
			this.map = mapClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public CollectionMap<K2, V> get(K1 key) {
		CollectionMap<K2, V> sub = map.get(key);
		if (sub == null) {
			sub = createNewMap();
			map.put(key, sub);
		}
		return sub;
	}

	public Set<K1> keySet() {
		return map.keySet();
	}

	public CollectionMap<K2, V> remove(K1 key) {
		CollectionMap<K2, V> sub = map.remove(key);
		if (sub == null)
			return createNewMap();
		return sub;
	}

	public int size() {
		return map.size();
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(K1 key) {
		return map.containsKey(key);
	}

	public Map<K1, CollectionMap<K2, V>> getInnerMap() {
		return Collections.unmodifiableMap(map);
	}

	@Override
	public CollectionMapMap<K1, K2, V> clone() {
		CollectionMapMap<K1, K2, V> clone = newInstance(map.getClass());
		for (Entry<K1, CollectionMap<K2, V>> entry : map.entrySet()) {
			clone.map.put(entry.getKey(), entry.getValue().clone());
		}
		return clone;
	}

	protected CollectionMap<K2, V> createNewMap() {
		return CollectionMap.newInstance();
	}
}
