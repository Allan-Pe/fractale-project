package com.example.cache

import io.pebbletemplates.pebble.node.CacheNode


interface LRUCache<V> {
    fun get(key: Int): V?
    fun put(key: Int, value: V)
    fun remove(key: Int): V?
    fun clear()
}


class Cache<T>(val capacity: Int) : LRUCache<T> {
    var cache = LinkedHashMap<Int, T>(capacity, 0.75f, true)

    override fun get(key: Int): T? {
        return cache[key]
    }

    fun getLastElement(): T? {
        return cache.entries.lastOrNull()?.value
    }

    fun getElementBefore(key: Int): T?{
        if (cache.size >= 2){
            return cache.entries.elementAt(cache.size - 2).value
        }
        return null
    }

    fun getElementAfter(key: Int): T?{
        if (cache.size >= 2 || key+1 < cache.size){
            return cache.entries.elementAt(key + 1).value
        }
        return null
    }

    override fun remove(key: Int): T? {
        return cache.remove(key)
    }

    override fun clear() {
        cache.clear()
    }

    override fun put(key: Int, value: T) {
        if (cache.size >= capacity) {
            println(cache.keys.iterator().next())
            val leastUsedKey = cache.keys.iterator().next()

            cache.remove(leastUsedKey)
        }
        cache[key] = value
    }

    override fun toString(): String {
        return "Cache($cache)"
    }


}

fun main() {

    val cache = Cache<String>(3)
    cache.clear()

    cache.put( 1, "a")
    cache.put( 2, "b")
    cache.put( 3, "c")

    cache.put( 4, "d")
    cache.put( 5, "e")
    cache.put( 6, "f")
    cache.put( 7, "toto")

    println(cache.getLastElement())
    println(cache.toString())
    println(cache.getElementBefore(2))
    println(cache.getElementAfter(1))

}