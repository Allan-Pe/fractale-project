package com.example.cache

import com.example.fractalGenerator.outil.FractalProperties

interface LRUCache<K, V> {
    fun get(key: K): V?
    fun put(key: K, value: V): V
    fun remove(key: K): V?
    fun clear()
}

class Cache<K, T>(val capacity: Int) : LRUCache<K, T> {
    var cache = LinkedHashMap<K, T>(capacity, 0.75f, true)

    override fun get(key: K): T? {
        val actual = cache[key]
        cache.remove(key)
        cache.put(key, actual!!)
        return cache[key]
    }

    fun getFirstElement(): T? {
        return cache.entries.elementAt(0).value
    }


    override fun remove(key: K): T? {
        return cache.remove(key)
    }

    override fun clear() {
        cache.clear()
    }

    override fun put(key: K, value: T): T {
        for ((k, v) in cache) {
            if (k == key) {
                return v
            }
        }
        if (cache.size >= capacity) {
            cache.remove(cache.entries.elementAt(0).key)
        }
        cache[key] = value
        return value
    }

    fun getSize(): Int {
        return cache.size
    }

    fun changeToKey(element: FractalProperties): String {
        return element.toString()
    }

    override fun toString(): String {
        return "Cache($cache)"
    }
}
