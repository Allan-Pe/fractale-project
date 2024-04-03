package com.example.cache

import com.example.fractalGenerator.outil.FractalProperties
import io.pebbletemplates.pebble.node.CacheNode
interface LRUCache<K,V> {
    fun get(key: K): V?
    fun put(key: K,value: V): V
    fun remove(key: K): V?
    fun clear()
}


class Cache<K,T>(val capacity: Int) : LRUCache<K,T> {
    var cache = LinkedHashMap<K, T>(capacity, 0.75f, true)

    override fun get(key: K): T? {
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

    override fun remove(key: K): T? {
        return cache.remove(key)
    }

    override fun clear() {
        cache.clear()
    }

    override fun put(key : K,value: T): T {
        for ((k,v) in cache){
            if(k == key){
                return v
            }
        }
        if (cache.size >= capacity) {
            val leastUsedKey = cache.keys.iterator().next()
            cache.remove(leastUsedKey)
        }
        cache[key] = value
        return value
    }

    fun getSize(): Int {
        return cache.size
    }

    fun changeToKey(element: FractalProperties): String {

        println("${element.width}-${element.height}-${element.centerX}-${element.centerY}-${element.scale}-${element.maxIterations}")
         return element.toString()
    }



    override fun toString(): String {
        return "Cache($cache)"
    }


}



fun main() {

//    val cache = Cache<String>(3)
//    cache.clear()
//
//    cache.put( 1, "a")
//    cache.put( 2, "b")
//    cache.put( 3, "c")
//
//    cache.put( 4, "d")
//    cache.put( 5, "e")
//    cache.put( 6, "f")
//    cache.put( 7, "toto")
//
//    println(cache.getLastElement())
//    println(cache.toString())
//    println(cache.getElementBefore(2))
//    println(cache.getElementAfter(1))

//    val historique = HistoryHandler()
//    historique.addElementToCache("element1")
//    historique.addElementToCache("element2")
//    historique.addElementToCache("element3")
//    historique.addElementToCache("element4")
//    historique.getAll()
//    historique.undo()
//    historique.undo()
//    historique.getAll()
//    historique.addElementToCache("element4")
//    historique.getAll()
//    historique.redo()









}