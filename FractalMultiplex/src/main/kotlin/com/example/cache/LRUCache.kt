package com.example.cache

interface LRUCache<V> {
    fun get(key: Int): V?
    fun put(key: Int, value: V)
    fun remove(key: Int): V?
    fun clear()
}


class Cache(val capacity: Int) : LRUCache<String> {
    var cache = LinkedHashMap<Int, String>(capacity, 0.75f, true)

    override fun get(key: Int): String? {
        return cache[key]
    }

    fun getLastElement(): String? {
        return cache.entries.lastOrNull()?.value
    }

    fun getElementBefore(key: Int): String?{
        if (cache.size >= 2){
            return cache.entries.elementAt(key - 1).value
        }
        return null
    }

    fun getElementAfter(key: Int): String?{
        if (cache.size >= 2){
            return cache.entries.elementAt(key + 1).value
        }
        return null
    }

    override fun remove(key: Int): String? {
        return cache.remove(key)
    }

    override fun clear() {
        cache.clear()
    }

    override fun put(key: Int, value: String) {

        if (cache.size >= capacity) {
            val leastUsedKey = cache.keys.iterator().next()

            cache.remove(leastUsedKey)
        }
        cache[key] = value
//        val oldestKey = 1
//
//        if(cache.isEmpty()) {
//            cache[1] = value
//        }
//        else if (cache.size + 1 <= capacity){
//            cache.put(cache.size+1, value)
//        }
//        else {
//            cache.remove(oldestKey)
//            cache[oldestKey] = value
//            cache.toSortedMap()
//    }
    }

    override fun toString(): String {
        return "Cache($cache)"
    }


}

fun main() {

    val cache = Cache(3)

    cache.put( 1, "a")
    cache.put( 2, "b")
//    cache.put( 3, "c")
//    cache.put( 4, "d")
//    cache.put( 5, "e")
//    cache.put( 6, "f")

    println(cache.getElementBefore(1))
    println(cache.getElementAfter(0))
    println(cache.toString())

}