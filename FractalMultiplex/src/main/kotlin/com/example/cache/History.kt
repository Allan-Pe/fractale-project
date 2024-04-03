package com.example.cache

class History {
    var cache = Cache<ByteArray>(4)

    fun takeSnapshot(): Memento {
        // println("snapshot" + listOrder)
        return Memento(cache.copy())
    }

    fun restore(memento: Memento) {
        cache = memento.cache

    }

    class Memento( cacheSave: Cache<ByteArray>) {
        val cache = cacheSave
    }
}