package com.example.cache

import io.pebbletemplates.pebble.node.CacheNode

interface LRUCache<V> {
    fun get(key: Int): V?
    fun put(value: V)
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

    override fun put(value: T) {
        var key = cache.size + 1
        println(key)
        if (cache.size >= capacity) {
            val leastUsedKey = cache.keys.iterator().next()
            cache.remove(leastUsedKey)
            key = leastUsedKey
        }
        cache[key] = value
    }

    fun getSize(): Int {
        return cache.size
    }

    fun copy(): Cache<T> {
        val copiedCache = Cache<T>(capacity)
        copiedCache.cache.putAll(cache)
        return copiedCache
    }

    override fun toString(): String {
        return "Cache($cache)"
    }


}


class HistoryHandler(){
    var history = History()
    var stateHistory = mutableListOf(History.Memento(Cache<ByteArray>(4)))
    var curentIndex= 1
    var activeIndex= stateHistory.size-1

    fun addElementToCache(element: ByteArray){
        if (activeIndex < stateHistory.size - 1) {
            stateHistory = stateHistory.slice(0..activeIndex).toMutableList()
            history.restore(stateHistory.last())
        }
        history.cache.put(element)
        stateHistory.add(history.takeSnapshot())
        curentIndex++
        activeIndex++
    }

    fun undo(){
        if (activeIndex <= 0) (return)
        activeIndex -= 1
        curentIndex -= 1
        history.restore(stateHistory[activeIndex])
    }

    fun redo(){
        if (stateHistory.size <= 0 || activeIndex >= stateHistory.size - 1) (return println("redo fail"))
        activeIndex += 1
        curentIndex += 1
        history.restore(stateHistory[activeIndex])
    }

    fun getAll(){
        println("history :${history.cache}")
        println("stateHistory :${stateHistory}")
        println("curentIndex :$curentIndex")
        println("activeIndex :${activeIndex}")

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