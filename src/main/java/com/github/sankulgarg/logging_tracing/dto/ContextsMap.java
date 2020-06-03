package com.github.sankulgarg.logging_tracing.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sankul.garg
 *
 * @param <K>
 * @param <V>
 */
public class ContextsMap<K, V> extends HashMap<K, V> implements Map<K, V> {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8353745263277320889L;

	private static final int	MAX_SIZE			= 5;

	@Override
	public V put(K key, V value) {
		if (super.size() <= MAX_SIZE)
			return super.put(key, value);
		return value;
	}
}
