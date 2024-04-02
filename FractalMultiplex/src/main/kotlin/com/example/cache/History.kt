package com.example.cache

class History {
    var cache = Cache<String>(4)

    fun takeSnapshot(): Memento {
        // println("snapshot" + listOrder)
        return Memento(cache.copy())
    }

    fun restore(memento: Memento) {
        cache = memento.cache

    }

    class Memento( cacheSave: Cache<String>) {
        val cache = cacheSave
    }
}